package com.Day8.SpringSecurityApp.SpringSecurityApp.controllers;

import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.PostDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts(){
      return postService.getAllPosts();
    }

       @PostMapping
public PostDto CreateNewPost(@RequestBody PostDto inputpost)
{
 return postService.createNewPost(inputpost);
  }

@GetMapping("/{postId}")
  public PostDto getPostById(@PathVariable Long postId){

    return  postService.getPostById(postId);
  }

  @PutMapping({"{postId}"})

    public PostDto updatePost(@RequestBody PostDto inputpost, @PathVariable Long postId){

        return postService.updatepost(inputpost,postId);
  }



}