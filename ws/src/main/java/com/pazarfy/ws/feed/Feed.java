package com.pazarfy.ws.feed;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Feed {
	
	@Id
	@GeneratedValue
	Long id;
	
	@Size(min=4,max=1000,message = "{pazarfy.feed.validation.constraints.Size.message}")
	@Column(length = 1000)
	String content;
	
	
	LocalDateTime createddate;

}
