package com.yurii.blog.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yurii.blog.entity.*;
import com.yurii.blog.enums.*;
import com.yurii.blog.repositories.*;

@RestController
public class ArticleController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@GetMapping("/articles")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
	
	@GetMapping("/articles/{color}/users")
    public Set<User> getUsersWithArticleColor(@PathVariable Color color) {
        return articleRepository.findAll().stream().filter(article -> article.getColor() == color).map(Article::getUser).collect(Collectors.toSet());
    }
	
	@GetMapping("/articles/author/{author_id}")
    public List<Article> getAllArticlesByAuthorId(@PathVariable (value = "author_id") Long author_id) {
        return articleRepository.findAll().stream().filter(article->article.getUser().getId() == author_id).collect(Collectors.toList());
    }

    @PostMapping("/articles/{author_id}")
    public Article createArticle(@PathVariable (value = "author_id") Long author_id, Article article) {
        article.setUser(userRepository.findById(author_id).orElseThrow(() -> new RuntimeException("User with id " + author_id + " not found")));
        return articleRepository.save(article);
    }
    
    @PutMapping("/articles/{id}")
    public Article updateArticle(@PathVariable Long id, Article requestArticle) {
    	return articleRepository.findById(id).map(article -> {
    		article.setText(requestArticle.getText());
    		article.setColor(requestArticle.getColor());
    		article.setUser(requestArticle.getUser());
    		return articleRepository.save(article);
    	}).orElseThrow(() -> new RuntimeException("Article with id " + id + " not found"));
    }
    
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return articleRepository.findById(id).map(article -> {
        	articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("Article with id " + id + " not found"));
    }
}
