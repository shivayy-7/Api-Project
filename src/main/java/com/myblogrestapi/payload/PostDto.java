package com.myblogrestapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
	
    private long id;
    
    @NotNull
    @Size(min= 2, message="Post title should have at least 2 characters")
    private String title;
    
    @NotNull
    @Size(min= 10, message="Post description should have at least 10 characters or more")
    private String description;
    
    @NotNull
    @NotEmpty
    private String content;
    

}

