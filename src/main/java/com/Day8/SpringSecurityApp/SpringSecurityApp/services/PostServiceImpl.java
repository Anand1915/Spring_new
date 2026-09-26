package com.Day8.SpringSecurityApp.SpringSecurityApp.services;

import com.Day8.SpringSecurityApp.SpringSecurityApp.Repositories.PostRepository;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.PostDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.PostEntity;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import com.Day8.SpringSecurityApp.SpringSecurityApp.exceptions.ResourseNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(postEntity ->
                        modelMapper.map(
                                postEntity,
                                PostDto.class
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {

        PostEntity postEntity =
                modelMapper.map(
                        inputPost,
                        PostEntity.class
                );

        return modelMapper.map(
                postRepository.save(postEntity),
                PostDto.class
        );
    }

    @Override
    public PostDto getPostById(Long postId) {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        log.info("USER LOGGED IN: {}", user);

        PostEntity postEntity =
                postRepository.findById(postId)
                        .orElseThrow(() ->
                                new ResourseNotFound(
                                        "PostId not found " + postId
                                ));

        return modelMapper.map(
                postEntity,
                PostDto.class
        );
    }

    @Override
    public PostDto updatepost(
            PostDto inputpost,
            Long postId
    ) {

        PostEntity olderPost =
                postRepository.findById(postId)
                        .orElseThrow(() ->
                                new ResourseNotFound("Post not found"));

        inputpost.setId(postId);

        modelMapper.map(
                inputpost,
                olderPost
        );

        PostEntity savedPost =
                postRepository.save(olderPost);

        return modelMapper.map(
                savedPost,
                PostDto.class
        );
    }
}