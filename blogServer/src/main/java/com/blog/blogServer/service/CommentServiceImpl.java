package com.blog.blogServer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogServer.model.Comment;
import com.blog.blogServer.model.Post;
import com.blog.blogServer.repository.CommentRepository;
import com.blog.blogServer.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}
	
	
	@Override
	public Comment createComment(int postId, String postedBy,String content) {
		
		Optional<Post> optionalPost = postRepository.findById(postId);
		if(optionalPost.isPresent()) {
			Comment comment = new Comment();
			comment.setPost(optionalPost.get());
			comment.setCreatedAt(LocalDateTime.now());
			comment.setAuthorName(postedBy);
			comment.setContent(content);
			return commentRepository.save(comment);
		}
		throw new EntityNotFoundException("post not found");
	}
}
