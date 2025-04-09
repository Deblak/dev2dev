package co.simplon.dev2dev_business.configs;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
@Profile("!prod")
public class JwtConfig {
    @Value("${dev2dev-business.jwt.secret}")
    private String secret;

    @Value("${dev2dev-business.jwt.exp}")
    private Long exp;

    @Value("${dev2dev-business.jwt.issuer}")
    private String issuer;

    @Bean
    JwtProvider jwtProvider() {
	Algorithm algorithm = Algorithm.HMAC256(secret);
	return new JwtProvider(algorithm, exp, issuer);
    }

    @Bean
    JwtDecoder jwtDecoder() {
	SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");
	NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	OAuth2TokenValidator<Jwt> validator = JwtValidators.createDefaultWithIssuer(issuer);
	return decoder;
    }
}
