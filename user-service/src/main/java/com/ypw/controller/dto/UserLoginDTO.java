package com.ypw.controller.dto;

import com.ypw.dao.pojo.User;
import com.ypw.pojo.OauthInfo;
import lombok.Data;

/**
 * @author yupengwu
 */
@Data
public class UserLoginDTO {
    OauthInfo oauthInfo;
    User user;
}
