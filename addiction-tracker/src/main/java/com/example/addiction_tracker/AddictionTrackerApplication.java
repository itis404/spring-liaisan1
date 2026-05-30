package com.example.addiction_tracker;

import com.example.addiction_tracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class AddictionTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddictionTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner createAdmin(UserService userService) {
		return args -> {
			if (userService.findByUsername("admin").isEmpty()) {
				var admin = userService.register("admin", "admin@admin.com", "admin123");
				admin.setRole("ROLE_ADMIN");
				userService.save(admin);
			}
		};
	}
}