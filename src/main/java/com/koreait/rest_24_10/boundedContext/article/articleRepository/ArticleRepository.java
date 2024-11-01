package com.koreait.rest_24_10.boundedContext.article.articleRepository;

import com.koreait.rest_24_10.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByIdDesc();
}