package com.mysite.sbb33.controller;

import com.mysite.sbb33.service.MailService;
import com.mysite.sbb33.vo.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "user/join";
    }

    @PostMapping("/confirm")
    public String confirm(MailDto mailDto) {
        if (mailDto.getAuthentication().equals(mailDto.getConfirmAuthentication())) {
            mailDto.setSuccess("Success");
            mailDto.setFail(null);
            return "user/join";

        }
        mailDto.setSuccess(null);
        mailDto.setFail("Fail");
        return "user/join";
    }
}