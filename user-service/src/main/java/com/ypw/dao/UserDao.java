package com.ypw.dao;

import com.ypw.dao.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yupengwu
 */
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * @param username
     * @return
     */
    User findByUsername(String username);
}