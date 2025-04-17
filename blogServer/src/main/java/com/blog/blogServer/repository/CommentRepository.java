package com.blog.blogServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.blogServer.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	 List<Comment> findByPostId(Long postId);
}
