package com.myblogrestapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogrestapi.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment>findByPostId(long postId);

}
