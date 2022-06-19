package com.pazarfy.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pazarfy.ws.configuration.AppConfig;
import com.pazarfy.ws.user.Users;


@Service
public class FileService {
	
//	@Value("${upload-path}")
//	String uploadPath;
	
	AppConfig config;
	
	Tika tika;
	
	FileRepository fileRepo;
	
	Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	public FileService(AppConfig config, FileRepository fileRepo) {
		super();
		this.config = config;
		this.tika = new Tika();
		this.fileRepo= fileRepo;
	}

	public String base64toFile(String image) throws IOException {
		String filename = generateId();
		File file= new File(config.getProfileStoragePath() + "/" + filename);
		OutputStream stream = new FileOutputStream(file);
		logger.info(detect(image));
		stream.write(Base64.getDecoder().decode(image));
		stream.close();
		return filename;
	}

	private String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void deleteProfileImage(String oldImageName) {
		if(oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(config.getProfileStoragePath(), oldImageName));
	}
	
	public void deleteAttachmentFile(String oldImageName) {
		if(oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(config.getAttachmentStoragePath(), oldImageName));
	}
	
	private void deleteFile(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String detect(String base64)
	{
		return tika.detect(Base64.getDecoder().decode(base64));
	}
	

	public String detectType(byte[] arr) {
		return tika.detect(arr);
	}
	
	public String generateRandomName() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		File target = new File(config.getAttachmentStoragePath() + "/" + fileName);
		String fileType = null;
		try {
			byte[] arr = multipartFile.getBytes();
			OutputStream outputStream = new FileOutputStream(target);
			outputStream.write(arr);
			outputStream.close();
			fileType = detectType(arr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileAttachment attachment = new FileAttachment();
		attachment.setName(fileName);
		attachment.setDate(new Date());
		attachment.setFileType(fileType);
		return fileRepo.save(attachment);
	}

	public void deleteAllSources(Users inDb) {
		
		deleteProfileImage(inDb.getImage());
		List<FileAttachment>  filesRemoved =fileRepo.findByFeedUser(inDb);
		for(FileAttachment file : filesRemoved)
		{
			deleteAttachmentFile(file.getName());
		}
		
	}

}
