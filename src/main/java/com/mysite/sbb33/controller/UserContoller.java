package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.service.UserService;
import com.mysite.sbb33.vo.MailDto;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserContoller {
    @Autowired
    private UserService userService;


    @RequestMapping("join")
    public String showLogin(MailDto mailDto) {
        return "user/join";
    }

    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String email, String password, String name) {
        if (Ut.empty(email)) {
            return "이메일을 입력해주세요 :)";
        }

        if (userService.findEmail(email)) {
            return "이미 존재하는 이메일입니다.";
        }

        if (Ut.empty(password)) {
            return "비밀번호를 입력해주세요 :)";
        }

        if (Ut.empty(name)) {
            return "이름을 입력해주세요 :)";
        }

        userService.doJoin(email, password, name);

        return "회원가입이 완료되었습니다. :)";

    }

    @RequestMapping("login")
    public String showLogin(HttpSession session, Model model) {
        boolean isLogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            isLogined = true;
            loginedUserId = (long) session.getAttribute("loginedUserId");
        }

        if (isLogined) {
            model.addAttribute("msg", "이미 로그인 되어 있습니다.");
            model.addAttribute("historyBack", "true");

            return "common/js";
        }

        return "user/login";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpSession session) {

        boolean isLogined = userService.isLogined(session);

        if (isLogined) {
            return """
                    <script>
                    alert("이미 로그인이 되어 있습니다.");
                    location.replace("/");
                    </script>
                    """;
        }

        if (Ut.empty(email)) {
            return """
                    <script>
                    alert("이메일을 입력해주세요.");
                    history.back();
                    </script>
                    """;
        }

        if (Ut.empty(password)) {
            return """
                    <script>
                    alert("비밀번호를 입력해주세요.");
                    history.back();
                    </script>
                    """;
        }

        if (!userService.findEmail(email)) {
            return """
                    <script>
                    alert("이메일이 존재하지 않습니다.");
                    history.back();
                    </script>
                    """;
        }

        User user = userService.getUserByEmail(email);


        if (!user.getPassword().equals(password)) {
            return """
                    <script>
                    alert("비밀번호가 일치하지 않습니다.");
                    history.back();
                    </script>
                    """;
        }

        userService.setLogin(user, session);

        return """
                <script>
                alert("%s님 환영합니다.");
                location.replace("/");
                </script>
                """.formatted(user.getName());

    }

    @RequestMapping("/doLogout")
    @ResponseBody
    public String doLogout(HttpSession session) {

        boolean isLogined = userService.isLogined(session);

        if (!isLogined) {
            return """
                    <script>
                    alert("이미 로그아웃 되었습니다.");
                    history.back();
                    </script>
                    """;
        }

        userService.removeLogin(session);

        return """
                    <script>
                    alert("로그아웃 되었습니다.");
                    history.back();
                    </script>
                    """;

    }


    @RequestMapping("/me")
    @ResponseBody
    public User showMe(HttpSession session) {
        boolean isLogined = userService.isLogined(session);

        if (isLogined == false) {
            return null;
        }

        User user = userService.getUserById(userService.getLoginedId(session));

        if (user == null) {
            return null;
        }

        return user;
    }

}
