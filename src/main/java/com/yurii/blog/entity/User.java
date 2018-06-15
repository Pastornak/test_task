package com.yurii.blog.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id; 
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private int age;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Article> articles;
	
	private User(){}
	
	public User(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}
	
	public Set<Article> getArticles() {
        return articles;
    }

	@Override
	public String toString(){
		return "Name: " + name + ", age: " + age + ", id: " + id;
	}
}
