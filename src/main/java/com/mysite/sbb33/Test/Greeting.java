package com.mysite.sbb33.Test;

import lombok.Data;

@Data
public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }
}
