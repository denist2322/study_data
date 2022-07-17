package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.service.ArticleService;
import com.mysite.sbb33.vo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;

    //C
    @RequestMapping("doWrite")
    @ResponseBody
    public String doWrite(String title, String body){
        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }

        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }

        articleService.doWrite(title, body);
        return "게시물 생성이 완료되었습니다.";

    }

    //R
    @RequestMapping("list")
    @ResponseBody
    public List<Article> showList(){
        List<Article> articles = articleService.getLists();
        return articles;
    }

    @RequestMapping("detail")
    @ResponseBody
    public Article showDetail(Long id){
        Article article = articleService.getList(id);

        return article;
    }
    //U
    @RequestMapping("doModify")
    @ResponseBody
    public String doModify(Long id, String title, String body){
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

        articleService.doModify(id, title, body);

        return "게시물 수정이 완료되었습니다. :)";
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
