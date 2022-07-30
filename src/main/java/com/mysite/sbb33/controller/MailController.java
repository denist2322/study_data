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
    public void execMail(MailDto mailDto) {
        mailDto.setTitle("본인인증 메일입니다.");
        mailDto.setMessage("아래 인증번호를 홈페이지에서 기입해주세요.");
        mailService.mailSimpleSend(mailDto);
//        mailService.mailSend(mailDto);
    }

    @GetMapping("/modal")
    public String disModal() {
        return "user/modal";
    }
}