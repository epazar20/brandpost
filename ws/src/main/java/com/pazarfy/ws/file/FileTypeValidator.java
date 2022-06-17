package com.pazarfy.ws.file;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {
	


	@Autowired
	FileService fileservice;
	
	String[] types;
	
	@Override
	public void initialize(FileType constraintAnnotation) {
		types = constraintAnnotation.types();
	}


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.isEmpty())
			return true;
		
		if(fileservice.detect(value).contains("jpeg") || fileservice.detect(value).contains("png"))
		{
			return true;
		}
		
		String genTypes = Arrays.asList(types).stream().collect(Collectors.joining(", "));
		context.disableDefaultConstraintViolation();
		HibernateConstraintValidatorContext hcv = context.unwrap(HibernateConstraintValidatorContext.class);
		hcv.addMessageParameter("types", genTypes);
		hcv.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
		
		return false;
	}

}
