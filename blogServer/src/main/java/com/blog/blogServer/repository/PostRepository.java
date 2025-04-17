package com.blog.blogServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.blogServer.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

	List<Post> findAllByTitleContaining(String title);

}
