package com.blog.blogServer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.blogServer.model.Post;
import com.blog.blogServer.service.PostService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;



@Controller
public class PostController {

	private PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	

	@GetMapping("/createpostform")
	public String createPostForm(Model model) {
	    model.addAttribute("post", new Post());
	    return "create-post";  
	}
	
	@PostMapping("/createpostform")
	public String createPost(@ModelAttribute("post") Post post,Model model) {
		
	    if (post.getTitle() == null || post.getTitle().isEmpty() || 
	            post.getContent() == null || post.getContent().isEmpty() || 
	            post.getPostedBy() == null || post.getPostedBy().isEmpty()) {
	            model.addAttribute("message", "All fields are required!");
	            return "create-post";  
	        }
		post.setId(0);
		Post createdPost = postService.save(post);
		model.addAttribute("message","Post added Successfuly");
		return "redirect:/home";
	}
	
	@PostMapping("/post")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		post.setId(0);
		Post createdPost = postService.save(post);
		return new ResponseEntity<>(createdPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		List<Post> posts = postService.allPost();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // اختر الصيغة المناسبة
	    for (Post post : posts) {
	        String dateString = post.getCreatedAtString();  // إذا كان لديك التاريخ كـ String
	        if (dateString != null) {
	            // تحويل String إلى LocalDateTime
	            LocalDateTime date = LocalDateTime.parse(dateString, formatter);
	            post.setCreatedAt(date);  // تعيينه كـ LocalDateTime
	        }
	    }
		model.addAttribute("posts",posts);
		
		return "home";
	}
	
	@GetMapping("/post/{id}")
	public String getPost(@PathVariable int id, Model model) {
	    Post post = postService.getPostById(id);
	    model.addAttribute("post", post);
		return "post-page";
	}
	
	@PostMapping("/post/{id}/like")
	public String likePost(@PathVariable int id, Model model) {
	    Post post = postService.likePost(id);
	    model.addAttribute("post", post);
		return "post-page";
	}
	
	@PostMapping("/post/{id}/delete")
	public String deletePost(@PathVariable int id) {
		postService.deletePostById(id);
		return "redirect:/home"; 
	}
	
	@GetMapping("/post/edit/{id}")
	public String editPostForm(@PathVariable int id, Model model) {
	    Post post = postService.getPostById(id);
	    model.addAttribute("post", post);
	    return "edit-post";
	}
	
	@PostMapping("/post/update/{id}")
	public String updatePost(@PathVariable int id, @ModelAttribute Post updatedPost) {
	    postService.updatePost(id, updatedPost);
	    return "redirect:/post/" + id; // رجعه للبوست بعد التعديل
	}
}
