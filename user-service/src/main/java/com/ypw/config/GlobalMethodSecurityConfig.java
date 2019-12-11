package com.ypw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 开启方法级别安全验证
 *
 * @author yupengwu
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig {
}