package config.tencent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class CloudBaseClient {
    private final String envId;
    private final String accessToken;
    private final String baseUrl;
    private final OkHttpClient client;
    private final Gson gson;
    private final Map<String, String> defaultHeaders;

    public CloudBaseClient() {
        // 加载环境变量
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.envId = dotenv.get("CLOUDBASE_ENV_ID");
        this.accessToken = dotenv.get("CLOUDBASE_ACCESS_TOKEN");
        this.baseUrl = "https://" + envId + ".api.tcloudbasegateway.com";

        this.client = new OkHttpClient();
        this.gson = new Gson();

        // 设置默认请求头
        this.defaultHeaders = new HashMap<>();
        this.defaultHeaders.put("Content-Type", "application/json");
        this.defaultHeaders.put("Accept", "application/json");
        this.defaultHeaders.put("Authorization", "Bearer " + accessToken);
    }

    public String getEnvId() {
        return envId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Gson getGson() {
        return gson;
    }

    /**
     * 统一的HTTP请求方法
     *
     * @param method 请求方法 (GET, POST, PUT, PATCH, DELETE)
     * @param path API路径 (如 /v1/rdb/rest/table_name)
     * @param body 请求体 (可选)
     * @param customHeaders 自定义请求头 (可选)
     * @return 响应数据或null
     */
    public JsonObject request(String method, String path, Object body, Map<String, String> customHeaders) {
        try {
            String url = baseUrl + path;

            // 构建请求头
            Headers.Builder headersBuilder = new Headers.Builder();
            defaultHeaders.forEach(headersBuilder::add);
            if (customHeaders != null) {
                customHeaders.forEach(headersBuilder::add);
            }

            // 构建请求体
            RequestBody requestBody = null;
            if (body != null) {
                String jsonBody = gson.toJson(body);
                requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json"));
            } else if (method.equals("POST") || method.equals("PUT") || method.equals("PATCH")) {
                requestBody = RequestBody.create("", MediaType.parse("application/json"));
            }

            // 构建请求
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .headers(headersBuilder.build())
                    .method(method, requestBody);

            // 发送请求
            try (Response response = client.newCall(requestBuilder.build()).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("请求失败: " + response.code() + " " + response.message());
                    return null;
                }

                // 如果响应为空，返回成功标识
                String responseBody = response.body().string();
                if (responseBody == null || responseBody.isEmpty()) {
                    JsonObject result = new JsonObject();
                    result.addProperty("success", true);
                    return result;
                }

                return gson.fromJson(responseBody, JsonObject.class);
            }
        } catch (IOException e) {
            System.err.println("请求失败: " + e.getMessage());
            return null;
        }
    }

    public JsonObject request(String method, String path, Object body) {
        return request(method, path, body, null);
    }

    public JsonObject request(String method, String path) {
        return request(method, path, null, null);
    }
}