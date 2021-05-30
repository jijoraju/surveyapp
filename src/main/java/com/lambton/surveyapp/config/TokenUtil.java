/**
 * &copyright upski international
 */
package com.lambton.surveyapp.config;

import java.util.Date;

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

public interface TokenUtil {

	public static String createAuthorizationToken(String id, String username, String issuer, String secretKey) {
		try {
			return JWT.create().withSubject(username).withClaim("id", id).withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).withIssuer(issuer)
					.sign(Algorithm.HMAC512(secretKey));

		}
		catch (JWTCreationException exception) {
			throw new JWTCreationException("Unable to create", exception);
		}
	}

	public static boolean validateToken(String token, String issuer, String secretKey) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).withIssuer(issuer).build();
			verifier.verify(token);
			return true;
		}

		catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Unauthorized");
		}
	}

	public static String getUsername(String token, String issuer, String secretKey) {
		return JWT.require(Algorithm.HMAC512(secretKey)).withIssuer(issuer).build().verify(token).getSubject();
	}

}
