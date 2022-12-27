package com.ll.exam.fileUpload1205.app.article.repository;

import com.ll.exam.fileUpload1205.app.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
