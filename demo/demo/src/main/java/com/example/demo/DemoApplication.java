package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
@Bean
CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
    return args -> {
        if (repo.findByUsername("admin") == null) {
            var user = new com.example.demo.model.User();
            user.setUsername("admin");
            user.setPassword(encoder.encode("password"));
            repo.save(user);
        }
    };
}
