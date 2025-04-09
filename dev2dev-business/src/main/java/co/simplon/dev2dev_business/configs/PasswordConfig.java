package co.simplon.dev2dev_business.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!prod")
public class PasswordConfig {
    @Value("${dev2dev-business.bcrypt.rounds}")
    private int rounds;

    @Bean
    public PasswordEncoder encoder() {
	return new BCryptPasswordEncoder(rounds);
    }
}
