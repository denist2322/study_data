package com.mysite.sbb33.service;

import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void doJoin(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());

        userRepository.save(user);
    }

    public boolean findEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean comfirmLogined(HttpSession session) {
        boolean islogined = false;

        if (session.getAttribute("loginedUserId") != null) {
            islogined = true;
        }
        return islogined;
    }

    public long getLoginedId(HttpSession session) {
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }
        return loginedUserId;
    }

    public boolean isLogined(HttpSession session) {
        return comfirmLogined(session);
    }

    public User getUserByEmail(String email) {
        Optional<User> opUser = userRepository.findByEmail(email);
        User user = opUser.orElse(null);

        return user;
    }

    public User getUserById(long loginedId) {
        Optional<User> opUser = userRepository.findById(loginedId);
        User user = opUser.orElse(null);

        return user;
    }

    public void setLogin(User user, HttpSession session) {
        session.setAttribute("loginedUserId", user.getId());
    }

    public void removeLogin(HttpSession session) {
        session.removeAttribute("loginedUserId");
    }


}
