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
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showWrite(ArticleWriteForm articleWriteForm, HttpSession session, Model model){

        boolean islogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            islogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(!islogined){
            model.addAttribute("msg","로그인후 이용해주세요");
            model.addAttribute("replaceUri", "list");

            return "common/js";
        }

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

        if(!islogined){
            model.addAttribute("msg", "로그인 부터 하세요.");
            model.addAttribute("historyBack","true");

            return "common/js";
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
    public String doModify(Long id, String title, String body, HttpSession session){
        boolean islogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            islogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(!islogined){
            return """
                <script>
                alert("로그인부터 해주세요. :)");
                location.replace("detail?id=%d")
                </script>
                """.formatted(id);
        }

        Article article = articleService.getList(id);

        if(!article.getId().equals(loginedUserId)){
            return """
                <script>
                alert("권한이 없습니다. :)");
                location.replace("detail?id=%d")
                </script>
                """.formatted(id);
        }

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
    public String doDelete(Long id, HttpSession session, Model model){

        boolean islogined = false;
        long loginedUserId = 0;

        if (session.getAttribute("loginedUserId") != null) {
            islogined = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(!islogined){

            return """
                <script>
                alert("로그인부터 하세요. :)");
                history.back();
                </script>
                """;
        }

        Article article = articleService.getList(id);


        if(article.getUser().getId() != loginedUserId) {
            return """
                <script>
                alert("권한이 없습니다. :)");
                location.replace("list");
                </script>
                """;
        }



//        // 사실 필요없음
//        if(!articleService.findList(id)){
//            return "%d번 게시물은 없습니다.".formatted(id);
//        }
//        // 여기까지

        articleService.doDelete(id);

        return """
                <script>
                alert("게시물이 삭제되었습니다, :)");
                location.replace("list");
                </script>
                """;
    }

    @GetMapping("/posts/search")
    public String search(String keyword, Model model) {
        List<Article> articles= articleService.searchTitle(keyword);
        model.addAttribute("articles", articles);
        return "article/list.html";
    }
}
