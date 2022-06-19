package com.pazarfy.ws.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pazarfy.ws.dto.UserUpdateVM;
import com.pazarfy.ws.error.NotFoundException;
import com.pazarfy.ws.file.FileService;
import com.pazarfy.ws.shared.CurrentUser;

@Service
public class UserService implements IUserService {

	IUserRepository repository;

	PasswordEncoder encoder;
	
	FileService fileservice;

	@Autowired
	public UserService(IUserRepository repository, FileService fileservice ) {
		super();
		this.repository = repository;
		this.encoder = new BCryptPasswordEncoder();
		this.fileservice = fileservice;
	}

	@Override
	public void CreateUser(Users user) {

		user.setPassword(encoder.encode(user.getPassword()));
		repository.save(user);

	}

	@Override
	public Users findByUsername(String username) {
		Users user = repository.findByUsername(username);
		if(user == null)
			throw new NotFoundException();
		
		return user;
	}

	@Override
	public Page<Users> getUsers(Pageable page, Users user, int type) {

		if (user != null) {
			if(type > -1)
			{
				return repository.findByUsertypeAndUsernameNot(type,user.getUsername(),page);
			}
			
			return repository.findByUsernameNot(user.getUsername(),page);
		}
		
		if(type > -1)
		{
			return repository.findByUsertype(type,page);
		}

		return repository.findAll(page);
	}

	@Override
	public Users updateByUser(String username, UserUpdateVM data) {
		Users inDB = findByUsername(username);
		inDB.setDisplayname(data.getDisplayname());
		String oldImg = inDB.getImage();
		if(data.getImage() != null && data.getImage().length()>0)
		{
			//inDB.setImage(data.getImage());
			try {
				String filename = fileservice.base64toFile(data.getImage());
				inDB.setImage(filename);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fileservice.deleteProfileImage(oldImg);
		}
		
		return repository.save(inDB);	
	}

	@Override
	public void delete(String username) {
		Users inDb = repository.findByUsername(username);
		fileservice.deleteAllSources(inDb);
		repository.delete(inDb);
		//repository.deleteByUsername(username);
	}

	

}
