package com.pazarfy.ws.file;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class FileCleanupService {
	
	@Autowired
	FileRepository fileRepo;
	
	@Autowired
	FileService fileService;
	
	
	@Scheduled(fixedRate = 1 * 60 * 60 * 1000)
	public void cleanupStorage() {
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (1 * 60 * 60 * 1000));
		List<FileAttachment> filesToBeDeleted = fileRepo.findByDateBeforeAndFeedIsNull(twentyFourHoursAgo);
		for(FileAttachment file : filesToBeDeleted) {
			fileService.deleteAttachmentFile(file.getName());
			fileRepo.deleteById(file.getId());
			System.out.print("file clean"  + file.getName());
		}
		
	}

}
