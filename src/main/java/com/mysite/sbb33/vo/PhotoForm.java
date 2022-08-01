package com.mysite.sbb33.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PhotoForm {
    @NotEmpty(message = "이미지 업로드하세요 ㅡㅡ;;")
    private List<MultipartFile> multiFileList;
    private String fileContent;
}
