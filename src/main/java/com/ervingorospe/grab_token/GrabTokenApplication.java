package com.ervingorospe.grab_token;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GrabTokenApplication {

	public static void main(String[] args) {
		// Load .env file
		String isKubernetes = System.getenv("KUBERNETES_SERVICE_HOST");

		if (isKubernetes == null) {
			// Running locally (IntelliJ)
			Dotenv dotenv = Dotenv.configure().load();
			System.setProperty("DB_URL", dotenv.get("DB_URL"));
			System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
			System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
			System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));
			System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
			System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));
			System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
			System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
		} else {
			// Running in Kubernetes (use environment variables)
			System.setProperty("DB_URL", System.getenv("DB_URL"));
			System.setProperty("DB_USERNAME", System.getenv("DB_USERNAME"));
			System.setProperty("DB_PASSWORD", System.getenv("DB_PASSWORD"));
			System.setProperty("DB_DRIVER", System.getenv("DB_DRIVER"));
			System.setProperty("MAIL_HOST", System.getenv("MAIL_HOST"));
			System.setProperty("MAIL_PORT", System.getenv("MAIL_PORT"));
			System.setProperty("MAIL_USERNAME", System.getenv("MAIL_USERNAME"));
			System.setProperty("MAIL_PASSWORD", System.getenv("MAIL_PASSWORD"));
		}

		SpringApplication.run(GrabTokenApplication.class, args);
	}

}
