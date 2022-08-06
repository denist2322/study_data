package com.mysite.sbb33.service;

import com.mysite.sbb33.repository.FilesRepository;
import com.mysite.sbb33.vo.Article;
import com.mysite.sbb33.vo.ArticleWriteForm;
import com.mysite.sbb33.vo.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FilesRepository filesRepository;
    private final ArticleService articleService;

    @Async
    public void doUpload(ArticleWriteForm articleWriteForm, List<MultipartFile> multiFileList, HttpServletRequest request, HttpSession session) {
        // path 가져오기
        String frontPath = "C:\\work\\intellij_project\\sbb33_study - 복사본\\src\\main\\resources\\static\\";
        String root = frontPath + "uploadFiles";

        File fileCheck = new File(root);

        if (!fileCheck.exists()) fileCheck.mkdirs();


        List<Map<String, String>> fileList = new ArrayList<>();

        for (int i = 0; i < multiFileList.size(); i++) {
            String originFile = multiFileList.get(i).getOriginalFilename();
            String ext = originFile.substring(originFile.lastIndexOf("."));
            String changeFile = UUID.randomUUID().toString() + ext;


            Map<String, String> map = new HashMap<>();
            map.put("originFile", originFile);
            map.put("changeFile", changeFile);

            fileList.add(map);
        }


        System.out.println(fileList);
        System.out.println(root);
        List<String> fileName = new ArrayList<>();
        // 파일업로드
        try {
            for (int i = 0; i < multiFileList.size(); i++) {
                String path = fileList.get(i).get("changeFile");
                fileName.add(path);
                File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
                multiFileList.get(i).transferTo(uploadFile);
            }
            uploadDB(fileName, session, articleWriteForm);
            System.out.println("다중 파일 업로드 성공!");

        } catch (IllegalStateException | IOException e) {
            System.out.println("다중 파일 업로드 실패 ㅠㅠ");
            // 만약 업로드 실패하면 파일 삭제
            for (int i = 0; i < multiFileList.size(); i++) {
                new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
            }
            e.printStackTrace();
        }
    }

    private void uploadDB(List<String> fileName, HttpSession session, ArticleWriteForm articleWriteForm) {
        if (session.getAttribute("loginedUserId") != null) {
            Article aritcle = articleService.doWrite((long) session.getAttribute("loginedUserId"), articleWriteForm.getTitle(), articleWriteForm.getBody());
            for (String file : fileName) {
                Files files = new Files();
                files.setFilename(file);
                files.setArticle(aritcle);
                filesRepository.save(files);
            }
        }
    }
}
