package com.example.talent_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TalentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentApiApplication.class, args);
	}

	@Configuration
	public static class corsConfigurer implements WebMvcConfigurer {

		private static final String[] ALLOWED_ORIGINS = {
				"http://localhost:5173",
				"http://127.0.0.1:5173"
		};

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins(ALLOWED_ORIGINS)
					.allowedMethods("GET", "POST", "PUT", "DELETE");
		}
	}

}
