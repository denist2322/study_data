package com.mysite.sbb33.repository;

import com.mysite.sbb33.vo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.title like %:kw%")
    List<Article> findByTitleKeyword(@Param("kw") String keyword);
}
