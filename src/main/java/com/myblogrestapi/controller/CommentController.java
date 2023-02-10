package com.myblogrestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    } 

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    
    //localhost:8080/api/post/1/comments
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable("postId") long postId){
    	List<CommentDto> dto = commentService.getCommentByPostId(postId);
    	return dto;
    }
    
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
    		                                        @PathVariable("id") long id,
    		                                        @RequestBody CommentDto commentDto){
    	
    	CommentDto dto = commentService.updateComment(postId, id, commentDto);
    	
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
    		                                    @PathVariable("id") long commentId){
    	
    	commentService.deleteComment(postId, commentId);
    	return new ResponseEntity<>("Comment is deleted!", HttpStatus.OK);
    }
}

