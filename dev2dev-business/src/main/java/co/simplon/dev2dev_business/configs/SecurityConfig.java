package co.simplon.dev2dev_business.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@Profile("!prod")
public class SecurityConfig {
    @Value("${dev2dev-business.cors.allowedOrigin}")
    private String allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(allowedOrigins).allowedMethods("POST","GET","PATCH","DELETE");
	    }
	};
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((req) -> req.requestMatchers(HttpMethod.GET, "/accounts/verify", "/articles/share").permitAll()
				.requestMatchers(HttpMethod.POST, "sandbox-rss/api/v1/provider").permitAll() //laissez ici c'est pour les tests (flemme créer un token each time)
			.requestMatchers(HttpMethod.POST, "/accounts", "/accounts/login", "accounts/verification-code"
				)
			.anonymous())
		.authorizeHttpRequests((reqs) -> reqs.anyRequest().authenticated())
		.oauth2ResourceServer((srv) -> srv.jwt(Customizer.withDefaults())).build();
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
	return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(DataAccessException ex, Object object,
	    HttpHeaders httpHeaders, HttpStatus conflict, WebRequest request) {
	return null;
    }

}
