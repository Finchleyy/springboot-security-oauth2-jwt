package com.ypw.service;

import com.ypw.common.BPwdEncoderUtil;
import com.ypw.controller.dto.UserLoginDTO;
import com.ypw.dao.UserDao;
import com.ypw.dao.pojo.User;
import com.ypw.feign.AuthServiceClient;
import com.ypw.pojo.OauthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yupengwu
 */
@Service
public class UserServiceDetail {

    @Autowired
    private UserDao userRepository;

    @Autowired
    AuthServiceClient client;

    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        return userRepository.save(user);
    }

    public UserLoginDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!BPwdEncoderUtil.matches(password, user.getPassword())) {
            throw new RuntimeException("用户密码不对");
        }
        //dXNlci1zZXJ2aWNlOjEyMzQ1Ng== 是 user-service:123456的 base64编码
        OauthInfo jwt = client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", username, password);
        if (jwt == null) {
            throw new RuntimeException("用户Token有问题");
        }
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUser(user);
        dto.setOauthInfo(jwt);
        return dto;
    }

}