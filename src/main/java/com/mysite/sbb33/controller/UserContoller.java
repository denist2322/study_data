package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String email, String password, String name){
        if(Ut.empty(email)){
            return "이메일을 입력해주세요 :)";
        }

        if(userRepository.existsByEmail(email)){
            return "이미 존재하는 이메일입니다.";
        }

        if(Ut.empty(password)){
            return "비밀번호를 입력해주세요 :)";
        }

        if(Ut.empty(name)){
            return "이름을 입력해주세요 :)";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());

        userRepository.save(user);

        return "회원가입이 완료되었습니다. :)";

    }
}
