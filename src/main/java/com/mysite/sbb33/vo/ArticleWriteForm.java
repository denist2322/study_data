package com.mysite.sbb33.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ArticleWriteForm {

    @NotEmpty(message = "제목입력 하세요 :)")
    private String title;

    @NotEmpty(message = "내용입력 하세요 :)")
    private String body;

}
