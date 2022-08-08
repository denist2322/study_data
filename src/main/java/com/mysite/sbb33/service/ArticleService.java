package com.mysite.sbb33.service;

import com.mysite.sbb33.repository.ArticleRepository;
import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.vo.Article;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    public Article doWrite(long loginedUserId, String title, String body) {
        Article article = new Article();
        article.setRegDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        User user = userRepository.findById(loginedUserId).get();
        article.setUser(user);

        articleRepository.save(article);
        return article;
    }

    public List<Article> getLists() {
        return articleRepository.findAll();
    }

    public Article getList(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public boolean findList(Long id) {
        return articleRepository.existsById(id);
    }

    public void doModify(Long id, String title, String body) {
        Article article = getList(id);

        article.setTitle(title);
        article.setBody(body);
        article.setUpdateDate(LocalDateTime.now());

        articleRepository.save(article);
    }

    public void doDelete(Long id) {
        articleRepository.deleteById(id);
    }

    // 아래 어노테이션을 쓰면 만약 sql 오류가 발생해도 에러가 발생하지 않고 롤백함.
    @Transactional
    public List<Article> searchTitle(String keyword){
        List<Article> articles = articleRepository.findByTitleKeyword(keyword);
        return articles;
    }
}
