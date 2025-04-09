package co.simplon.dev2dev_business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAsync
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Dev2devBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dev2devBusinessApplication.class, args);
	}

}
