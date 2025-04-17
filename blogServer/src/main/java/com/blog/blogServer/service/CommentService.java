package com.blog.blogServer.service;

import com.blog.blogServer.model.Comment;

public interface CommentService {

	 Comment createComment(int postId, String postedBy,String content);
}
