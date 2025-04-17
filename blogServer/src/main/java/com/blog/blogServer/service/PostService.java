package com.blog.blogServer.service;

import java.util.List;

import com.blog.blogServer.model.Post;

public interface PostService {

	Post save(Post post);
	void updatePost(int id,Post post);
	List<Post> allPost();
	Post getPostById(int id);
	Post likePost(int id);
	void deletePostById(int id);
	List<Post> searchByName(String title);
}
