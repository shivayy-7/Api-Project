package com.myblogrestapi.services;

import java.util.List;

import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.utils.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}




