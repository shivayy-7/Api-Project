package com.myblogrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogrestapi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}

