package com.minprogram.infra.wx;

import com.minprogram.infra.wx.dto.WxCode2SessionResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class WxApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.wx.app-id}")
    private String appId;

    @Value("${app.wx.app-secret}")
    private String appSecret;

    public WxCode2SessionResp code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        try {
            return restTemplate.getForObject(url, WxCode2SessionResp.class);
        } catch (RestClientException ex) {
            return null;
        }
    }
}
