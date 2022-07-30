package com.mysite.sbb33.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailDto {
    private String address;
    private String title;
    private String message;
}
