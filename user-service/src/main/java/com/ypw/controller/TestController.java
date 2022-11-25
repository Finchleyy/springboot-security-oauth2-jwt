package com.ypw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hongmeng
 * @date 2022/11/24
 */
@RestController
public class TestController {

    @GetMapping("/test/biz")
    public String testBiz() {
        return "SUCCESS";
    }
}
