package com.mysite.sbb33.controller;

import com.mysite.sbb33.service.ArticleService;
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
    private final ArticleService articleService;

    @GetMapping("/multi-file")
    public String showMultiForm() {

        return "user/File.html";
    }

    @PostMapping("/multi-file")
    public String multiFileUpload(@RequestParam("multiFile") List<MultipartFile> multiFileList, ArticleWriteForm articleWriteForm, HttpServletRequest request, HttpSession session, Model model) throws InterruptedException {
        if (session.getAttribute("loginedUserId") == null) {
            // 로그인 안됬으면 경고창 출력하고 뒤로 돌아감
            model.addAttribute("msg", "로그인 부터 하세요.");
            model.addAttribute("historyBack", true);
            return "common/js";
        }
        try {
            fileUploadService.doUpload(articleWriteForm, multiFileList, request, session);

        } catch(Exception e) {
            articleService.doWrite((long) session.getAttribute("loginedUserId"), articleWriteForm.getTitle(), articleWriteForm.getBody());
        }
        return "user/File.html";
    }
}
