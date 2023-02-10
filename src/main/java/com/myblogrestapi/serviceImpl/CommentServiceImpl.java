package com.myblogrestapi.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myblogrestapi.entity.Comment;
import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.ResourceNotFoundException;
import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.repositories.CommentRepository;
import com.myblogrestapi.repositories.PostRepository;
import com.myblogrestapi.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    
    public CommentServiceImpl(CommentRepository commentRepository, 
                              PostRepository postRepository,
                              ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

    	// retrieve post entity by id
    	Post post = postRepository.findById(postId).orElseThrow(
    			() -> new ResourceNotFoundException("Post", "id", postId));
    	
        Comment comment = mapToEntity(commentDto);

        // set post to comment entity
        comment.setPost(post);

        // comment entity to DB
        Comment newComment =  commentRepository.save(comment);

        return mapToDTO(newComment);
    }

        private CommentDto mapToDTO(Comment comment){
         CommentDto commentDto = mapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
    	Comment comment = mapper.map(commentDto, Comment.class);
//       Comment comment = new Comment();
//       comment.setId(commentDto.getId());
//       comment.setName(commentDto.getName());
//       comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return  comment;
    }

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("post", "id", postId));
		
		Comment comment = commentRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("comment", "id", id)
		);
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
        		() -> new ResourceNotFoundException("post", "id", postId)
        		);		
        
        Comment comment = commentRepository.findById(commentId).orElseThrow(
        		()-> new ResourceNotFoundException("comment", "id", commentId)
        		);
        commentRepository.deleteById(commentId);
	}

}
