package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.service.ArticleService;
import com.mysite.sbb33.service.UserService;
import com.mysite.sbb33.vo.Article;
import com.mysite.sbb33.vo.ArticleWriteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    //C
    @RequestMapping("write")
    public String showWrite(ArticleWriteForm articleWriteForm){
        return "article/write";
    }


//    @RequestMapping("doWrite")
//    @ResponseBody
//    public String doWrite(String title, String body, Model model){
//        if(Ut.empty(title)){
//            return "제목을 입력해주세요.";
//        }
//
//        if(Ut.empty(body)){
//            return "내용을 입력해주세요.";
//        }
//
//        articleService.doWrite(title, body);
//        return """
//                <script>
//                alert("게시물 생성이 완료되었습니다.");
//                location.replace("list");
//                </script>
//                """;
//    }

    @RequestMapping("doWrite")
    public String doWrite(@Valid ArticleWriteForm articleWriteForm, BindingResult bindingResult, Model model, HttpSession session) {
        boolean islogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            islogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(bindingResult.hasErrors()){
            return "article/write";
        }
        articleService.doWrite(loginedUserId, articleWriteForm.getTitle(), articleWriteForm.getBody());
        model.addAttribute("msg", "게시물이 생성되었습니다.");
        model.addAttribute("replaceUri","list");

        return "common/js";

    }

    //R
    @RequestMapping("list")
    public String showList(Model model) {
        List<Article> articles = articleService.getLists();
        model.addAttribute("articles",articles);
        return "article/list";
    }

    @RequestMapping("detail")
    public String showDetail(Long id, Model model){
        Article article = articleService.getList(id);
        model.addAttribute("article",article);
        return "article/detail";
    }
    //U
    @RequestMapping("modify")
    public String showModify(Long id, Model model){
        Article article = articleService.getList(id);

        model.addAttribute("article", article);

        return "article/modify";
    }

    @RequestMapping("doModify")
    @ResponseBody
    public String doModify(Long id, String title, String body){
        // 사실 필요 없음
        if(id == null){
            return "게시물번호를 입력해주세요";
        }

        if(!articleService.findList(id)){
            return "게시물이 없습니다.";
        }

        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }

        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }
        // 여기까지

        articleService.doModify(id, title, body);

        return """
                <script>
                alert("게시글 수정이 완료되었습니다. :)");
                location.replace("detail?id=%d")
                </script>
                """.formatted(id);
    }
    //D
    @RequestMapping("doDelete")
    @ResponseBody
    public String doDelete(Long id){
        if(!articleService.findList(id)){
            return "%d번 게시물은 없습니다.".formatted(id);
        }

        articleService.doDelete(id);

        return "%d번 게시물을 삭제했습니다.".formatted(id);
    }
}
