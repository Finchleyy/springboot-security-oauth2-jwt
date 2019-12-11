package com.ypw.feign;

import com.ypw.pojo.OauthInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yupengwu
 */
@FeignClient(value = "oauth-service", fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {

    /**
     * 从授权服务获取 token
     *
     * @param authorization
     * @param type
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/oauth/token")
    OauthInfo getToken(@RequestHeader(value = "Authorization") String authorization,
                       @RequestParam("grant_type") String type,
                       @RequestParam("username") String username,
                       @RequestParam("password") String password);
}