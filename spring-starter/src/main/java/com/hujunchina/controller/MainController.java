package com.hujunchina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hujunchina@outlook.com
 * @date 2020-06-14
 *
 * 当使用 @Controller 时无法访问，因为String返回的是视图的文件名
 * 需要使用 @RestController 返回自带 json 格式的数据！！
 */
@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/index")
    public String index(){
        return "Hello Spring!";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello SpringBoot !";
    }
}
