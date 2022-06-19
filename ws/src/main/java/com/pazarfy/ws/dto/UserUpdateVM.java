package com.pazarfy.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pazarfy.ws.file.FileType;

import lombok.Data;

@Data
public class UserUpdateVM {
	
	@NotNull(message = "{pazarfy.displayname.validation.constraints.NotNull.message}")
	@Size(min=4,max=255,message = "{pazarfy.displayname.validation.constraints.Size.message}")
	String displayname; 
	
	@FileType(message = "{pazarfy.image.validation.constraints.filetype.message}", types = { "jpeg","png" } )
	String image;

}
