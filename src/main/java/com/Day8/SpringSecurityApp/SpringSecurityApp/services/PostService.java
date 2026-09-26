package com.Day8.SpringSecurityApp.SpringSecurityApp.services;

import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService
{

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto inputPost);


    PostDto getPostById(Long postId);

    PostDto updatepost(PostDto inputpost, Long postId);
}
