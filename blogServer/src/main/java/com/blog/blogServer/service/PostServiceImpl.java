package com.blog.blogServer.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogServer.model.Post;
import com.blog.blogServer.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Post save(Post post) {
		post.setLikeCount(0);
		post.setViewCount(0);
		post.setCreatedAt(LocalDateTime.now());
		Post thePost = postRepository.save(post);
		return thePost;
	}

	@Override
	public List<Post> allPost() {
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(int id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			post.setViewCount(post.getViewCount() +1);
			post.getComments().size();
			postRepository.save(post);
			return post;
		}else {
			throw new EntityNotFoundException("Post not found !! ");
		}
	}

	@Override
	public Post likePost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		if(optionalPost.isPresent()) {
			Post post = optionalPost.get();
			post.setLikeCount(post.getLikeCount()+1);
			postRepository.save(post);
			return post;
		}else {
			throw new EntityNotFoundException("Post not found !! ");
		}
	}

	@Override
	public List<Post> searchByName(String title) {
		
		return postRepository.findAllByTitleContaining(title);
	}

	@Override
	public void deletePostById(int id) {
		postRepository.deleteById(id);
	}

	@Override
	public void updatePost(int id,Post post) {
	    Post existingPost = postRepository.findById(id).orElseThrow();
	    existingPost.setTitle(post.getTitle());
	    existingPost.setContent(post.getContent());
	    existingPost.setTags(post.getTags());
	    existingPost.setImg(post.getImg());

	    postRepository.save(existingPost);
	}
	
}
