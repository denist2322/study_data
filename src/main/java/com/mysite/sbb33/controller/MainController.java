package com.mysite.sbb33.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/test")
    @ResponseBody
    public String showMain(){
        return "하이";
    }

    @RequestMapping("/")
    public String rootMain(){
        return "redirect:article/list";
    }
}
