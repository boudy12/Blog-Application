package com.blog.blogServer.controller;

import com.blog.blogServer.model.Post;
import com.blog.blogServer.service.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    
    private PostService postService;

    @Autowired
    public SearchController(PostService postService) {
    	this.postService = postService;
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search-page";
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String title, Model model) {
        List<Post> posts = postService.searchByName(title);
        model.addAttribute("posts", posts);
        return "search-page";
    }
    

}
