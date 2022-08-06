package com.mysite.sbb33.controller;

import com.mysite.sbb33.service.FileUploadService;
import com.mysite.sbb33.vo.ArticleWriteForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @GetMapping("/multi-file")
    public String showMultiForm() {

        return "user/File.html";
    }

    @PostMapping("/multi-file")
    public String multiFileUpload(@RequestParam("multiFile") List<MultipartFile> multiFileList, ArticleWriteForm articleWriteForm, HttpServletRequest request, HttpSession session, Model model) throws InterruptedException {

        // 받아온것 출력 확인
        fileUploadService.doUpload(articleWriteForm, multiFileList, request, session);

        return "user/File.html";
    }
}
