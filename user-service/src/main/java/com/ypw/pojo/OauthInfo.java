package com.ypw.pojo;

import lombok.Data;

/**
 * 授权信息
 *
 * @author yupengwu
 */
@Data
public class OauthInfo {
    private String access_token, token_type, refresh_token, scope, jti;
    private int expires_in;
    //set和 get
}