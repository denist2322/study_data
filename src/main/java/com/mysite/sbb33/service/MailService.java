package com.mysite.sbb33.service;

import com.mysite.sbb33.vo.MailDto;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "no_repy@boki.com";

    @Async
    public void mailSimpleSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        String msg = mailDto.getMessage() + "\n인증번호는 " + RandomStringUtils.randomAlphanumeric(6) + "입니다";
        message.setTo(mailDto.getAddress());
//        message.setFrom(MailService.FROM_ADDRESS); // 구글 정책 변경으로 설정한 gmail로 가게됨
        message.setSubject(mailDto.getTitle());
        message.setText(msg);
        mailSender.send(message);
    }

    @Async
    public void justSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}
