package com.mysite.sbb33.controller;

import com.mysite.sbb33.service.MailService;
import com.mysite.sbb33.vo.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class MailController {
    private final MailService mailService;

    @GetMapping("/mail")
    public String dispMail(MailDto mailDto) {
        return "user/mail";
    }

    @PostMapping("/mail")
    public String execMail(MailDto mailDto) {
        mailService.mailSimpleSend(mailDto);
//        mailService.mailSend(mailDto);
        return "user/mail";
    }

    @PostMapping("/confirm")
    @ResponseBody
    public String confirm(MailDto mailDto) {
        if (mailDto.getAuthentication().equals(mailDto.getConfirmAuthentication())) {
            return """
                    <script>
                    alert("인증이 완료되었습니다.");
                    window.close();
                    </script>
                    """;

        }
        return """
                <script>
                alert("번호가 맞지 않습니다.");
                location.replace("/mail");
                </script>
                """;
    }
}