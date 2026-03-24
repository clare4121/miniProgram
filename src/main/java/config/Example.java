//package config;
//
//import com.cloudbase.CloudBaseClient;
//import com.google.gson.JsonObject;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Example {
//    public static void main(String[] args) {
//        CloudBaseClient cloudbase = new CloudBaseClient();
//
//        // 调用云函数
//        Map<String, Object> data = new HashMap<>();
//        // data.put("key", "value"); // 可选参数
//
//        JsonObject result = cloudbase.request("POST", "/v1/functions/scfjavahelloworld", data);
//
//        if (result != null) {
//            System.out.println("云函数调用结果: " + result);
//        }
//    }
//}