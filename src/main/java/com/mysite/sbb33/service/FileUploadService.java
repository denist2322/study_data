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
        String root = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadFiles";

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

        // 파일업로드
        try {
            for (int i = 0; i < multiFileList.size(); i++) {
                File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
                multiFileList.get(i).transferTo(uploadFile);
            }
            uploadDB(fileList, session, articleWriteForm);
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

    private void uploadDB(List<Map<String, String>> fileName, HttpSession session, ArticleWriteForm articleWriteForm) {

        Article aritcle = articleService.doWrite((long) session.getAttribute("loginedUserId"), articleWriteForm.getTitle(), articleWriteForm.getBody());
        for (Map<String, String> file : fileName) {
            Files files = new Files();
            files.setFilename(file.get("changeFile"));
            files.setArticle(aritcle);
            filesRepository.save(files);

        }
    }
}
