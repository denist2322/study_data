package com.mysite.sbb33.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestSocketController {
    @RequestMapping("/testSocket")
    public String test(){
        return "test.html";
    }
}
