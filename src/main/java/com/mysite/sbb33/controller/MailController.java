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
    public String dispMail() {
        return "user/mail";
    }

    @PostMapping("/mail")
    @ResponseBody
    public String execMail(MailDto mailDto) {
        mailService.mailSimpleSend(mailDto);
//        mailService.mailSend(mailDto);
        return """
                <script>
                alert("메일을 성공적으로 보냈습니다.");
                history.back();
                </script>
                """;
    }

}