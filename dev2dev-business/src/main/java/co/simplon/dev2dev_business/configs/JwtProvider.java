package co.simplon.dev2dev_business.configs;

import java.time.Instant;
import java.util.Set;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtProvider {
    private final Algorithm algorithm;
    private final Long exp;
    private final String issuer;

    protected JwtProvider(Algorithm algorithm, Long exp, String issuer) {
	this.algorithm = algorithm;
	this.exp = exp;
	this.issuer = issuer;
    }

    public String create(String subject, Set<String> roleNames) {
	Instant issuedAt = Instant.now();
	String[] rolesArray = roleNames.stream().map(role -> "ROLE_" + role).toArray(String[]::new);

	Builder builder = JWT.create().withIssuedAt(issuedAt).withSubject(subject).withIssuer(issuer)
		.withArrayClaim("roles", rolesArray);

	if (exp > -1) {
	    Instant expiredAt = issuedAt.plusSeconds(exp);
	    builder.withExpiresAt(expiredAt);
	}

	return builder.sign(algorithm);
    }

}
