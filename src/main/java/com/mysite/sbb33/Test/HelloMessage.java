package com.mysite.sbb33.Test;

import lombok.Data;

@Data
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }
}
