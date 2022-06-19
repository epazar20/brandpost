package com.pazarfy.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.pazarfy.ws.dto.FeedVM;
import com.pazarfy.ws.feed.Feed;
import com.pazarfy.ws.feed.FeedService;
import com.pazarfy.ws.interaction.UserInteraction;
import com.pazarfy.ws.interaction.UserInteractionRepository;
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
	CommandLineRunner createInitialUser(IUserService service, FeedService feedService,
			UserInteractionRepository interaction) {
		// lambda function
		return (args) -> {

			try {
				Users user0 = service.findByUsername("AACAR0");

			} catch (Exception e) {
				for (int i = 0; i < 50; i++) {
					Users user = new Users();
					user.setUsername("AACAR" + i);// user
					user.setDisplayname("Adım" + i + " ACAR");
					user.setPassword("NP321");// Password123*
					user.setUsertype(i % 2);// kullanıcı:1 Influencer:2
					service.CreateUser(user);

					for (int j = 0; j < 5; j++) {
						FeedVM feed = new FeedVM();
						feed.setContent(user.getUsername() + " mesajım" + j + ": Merhaba");
						feedService.saveFeed(feed, user);

					}

					if (i % 2 == 1) {
						UserInteraction uinf = new UserInteraction();
						uinf.setUserid((long) i);
						uinf.setInfluencerid((long) i - 1);
						interaction.save(uinf);
						uinf = new UserInteraction();
						uinf.setUserid((long) i);
						uinf.setInfluencerid((long) i + 1);
						interaction.save(uinf);

					}

				}
			}

		};

		// return new CommandLineRunner() {
		//
		// @Override
		// public void run(String... args) throws Exception {
		//
		// Users user = new Users();
		// user.setUsername("username1");
		// user.setDisplayname("display");
		// user.setPassword("Password123*");
		//
		// if(service.findByUsername(user.getUsername()) == null )
		// {
		// service.CreateUser(user);
		// }
		// }
		// };
	}

}
