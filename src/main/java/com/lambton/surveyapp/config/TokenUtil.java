/**
 * &copyright upski international
 */
package com.lambton.surveyapp.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * @author Jijo Raju
 * @Since May 30, 2021 11:55:20 AM
 *
 */

@Component
public class TokenUtil {

	@Value("${overseas.app.jwt.issuer}")
	private String issuer;

	@Value("${overseas.app.jwt.secret.key}")
	private String secretKey;

	public String createAuthorizationToken(String id, String username) {
		try {
			return JWT.create().withSubject(username).withClaim("id", id).withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).withIssuer(issuer)
					.sign(Algorithm.HMAC512(secretKey));

		}
		catch (JWTCreationException exception) {
			throw new JWTCreationException("Unable to create", exception);
		}
	}

	public boolean validateToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).withIssuer(issuer).build();
			verifier.verify(token);
			return true;
		}

		catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Unauthorized");
		}
	}

	public String getUsername(String token) {
		return JWT.require(Algorithm.HMAC512(secretKey)).withIssuer(issuer).build().verify(token).getSubject();
	}

}
