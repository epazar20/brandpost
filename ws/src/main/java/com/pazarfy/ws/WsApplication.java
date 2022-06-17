package com.pazarfy.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.pazarfy.ws.feed.Feed;
import com.pazarfy.ws.feed.FeedService;
import com.pazarfy.ws.user.IUserService;
import com.pazarfy.ws.user.Users;
//(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}
	
	
	@Bean
	@Profile("prod")
	CommandLineRunner createInitialUser(IUserService service, FeedService feedService)
	{
		//lambda function
		return (args) -> {
			
			for(int i=0 ; i< 50 ;i++)
			{
				Users user = new Users();
				user.setUsername("AACAR" + i);// user
				user.setDisplayname("display" +i );
				user.setPassword("NP321");// Password123*
//				try {
//					if (service.findByUsername(user.getUsername()) == null) {
//						service.CreateUser(user);
//					}
//				} catch (Exception ex) {
					service.CreateUser(user);
//				}
				
			}
					
			for(int i=0 ; i< 50 ;i++)
			{
				Feed feed = new Feed();
				feed.setContent("Feed " + i);
				feedService.saveFeed(feed);
				
			}

		};
		
		
//return new CommandLineRunner() {
//			
//			@Override
//			public void run(String... args) throws Exception {
//				
//				Users user = new Users();
//				user.setUsername("username1");
//				user.setDisplayname("display");
//				user.setPassword("Password123*");
//				
//				if(service.findByUsername(user.getUsername()) == null )
//				{
//					service.CreateUser(user);
//				}
//			}
//		};
	}

}
