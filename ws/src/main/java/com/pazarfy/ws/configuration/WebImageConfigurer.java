package com.pazarfy.ws.configuration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebImageConfigurer implements WebMvcConfigurer{
	
//	@Value("${upload-path}")
//	String uploadPath;
	
	@Autowired
	AppConfig config;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/images/**").addResourceLocations("file:./" + config.getUploadPath())
		.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
	}
	
	
	@Bean
	CommandLineRunner createStorageDirectories() {
		return (args) -> {
			createFolder(config.getUploadPath());
			createFolder(config.getProfileStoragePath());
			createFolder(config.getAttachmentStoragePath());
		};
	}

	private void createFolder(String path) {
		File folder = new File(path);
		boolean folderExist = folder.exists() && folder.isDirectory();
		if(!folderExist) {
			folder.mkdir();
		}
	}

}
