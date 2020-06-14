package com.hujunchina.starter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hujunchina@outlook.com
 */
@Controller("/")
public class MainController {

    @GetMapping("/index")
    public String index(){
        return "Hello Spring!";
    }
}
