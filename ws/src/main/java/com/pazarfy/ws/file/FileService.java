package com.pazarfy.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pazarfy.ws.configuration.AppConfig;

@Service
public class FileService {
	
//	@Value("${upload-path}")
//	String uploadPath;
	
	AppConfig config;
	
	Tika tika;
	
	Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	public FileService(AppConfig config) {
		super();
		this.config = config;
		this.tika = new Tika();
	}

	public String base64toFile(String image) throws IOException {
		String filename = generateId();
		File file= new File(config.getUploadPath() +filename);
		OutputStream stream = new FileOutputStream(file);
		logger.info(detect(image));
		stream.write(Base64.getDecoder().decode(image));
		stream.close();
		return filename;
	}

	private String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void deleteFile(String oldImg) {
		if(oldImg == null)
		{
			return;
		}
		try {
			Files.deleteIfExists(Paths.get(config.getUploadPath(),oldImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String detect(String base64)
	{
		return tika.detect(Base64.getDecoder().decode(base64));
	}

}
