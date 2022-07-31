package com.mysite.sbb33.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MailDto {
    private String email;
    private String title;
    private String message;
    private String authentication;
    private String confirmAuthentication;
}
