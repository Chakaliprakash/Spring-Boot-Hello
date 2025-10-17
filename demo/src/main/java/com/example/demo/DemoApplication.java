package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
@Bean
CommandLineRunner init(UserRepository repo) {
    return args -> {
        if (repo.findByUsername("PrakashChakali") == null) {
            com.example.demo.model.User user = new com.example.demo.model.User();
            user.setUsername("PrakashChakali");
            user.setPassword("Prakash123."); 
            repo.save(user);
            System.out.println("User created");
            
        }
    };
}

}
