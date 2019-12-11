package com.ypw.feign;

import com.ypw.pojo.OauthInfo;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements AuthServiceClient {

    @Override
    public OauthInfo getToken(String authorization, String type, String username, String password) {
        // TODO Auto-generated method stub
        return null;
    }

}