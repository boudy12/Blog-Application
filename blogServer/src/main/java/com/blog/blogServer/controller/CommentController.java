package com.blog.blogServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.blogServer.service.CommentService;

@Controller
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	@PostMapping("/add/{postId}")
	public String createComment(@PathVariable int postId,@RequestParam("postedBy") String postedBy,@RequestParam("content") String content) {
		commentService.createComment(postId, postedBy, content);
		return "redirect:/post/" + postId;
	}
}
