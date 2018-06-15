package com.yurii.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yurii.blog.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	
}
