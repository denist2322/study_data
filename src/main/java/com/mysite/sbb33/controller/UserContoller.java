package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String email, String password, String name) {
        if (Ut.empty(email)) {
            return "이메일을 입력해주세요 :)";
        }

        if (userRepository.existsByEmail(email)) {
            return "이미 존재하는 이메일입니다.";
        }

        if (Ut.empty(password)) {
            return "비밀번호를 입력해주세요 :)";
        }

        if (Ut.empty(name)) {
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

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpServletRequest req, HttpServletResponse res) {
        if (Ut.empty(email)) {
            return "이메일을 입력해주세요.";
        }

        if (Ut.empty(password)) {
            return "비밀번호를 입력해주세요.";
        }

        if (!userRepository.existsByEmail(email)) {
            return "이메일이 존재하지 않습니다.";
        }

        Optional<User> opUser = userRepository.findByEmail(email);
        User user = opUser.get();

        if (!user.getPassword().equals(password)) {
            return "비밀번호가 일치하지 않습니다.";
        }

        Cookie cookie = new Cookie("loginedUserId", user.getId() + "");
        res.addCookie(cookie);

        return "%s님 환영합니다 :)".formatted(user.getName());

    }

    @RequestMapping("/me")
    @ResponseBody
    public User showMe(HttpServletRequest req) {
        boolean isLogined = false;
        long loginedUserId = 0;

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginedUserId")) {
                    isLogined = true;
                    loginedUserId = Long.parseLong(cookie.getValue());
                }
            }
        }

        if (isLogined == false) {
            return null;
        }

        Optional<User> user = userRepository.findById(loginedUserId);

        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }
}
