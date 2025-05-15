package co.simplon.dev2dev_business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
public class Dev2devBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dev2devBusinessApplication.class, args);
	}

}
