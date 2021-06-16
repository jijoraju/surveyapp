/**
 * &copyright upski international
 */
package com.lambton.surveyapp.config;

import static java.util.List.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasLength;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lambton.surveyapp.db.repository.UserRepository;

/**
 * @author Jijo Raju
 * @Since May 30, 2021 11:39:39 AM
 *
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!hasLength(token) || !token.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		token = token.replace("Bearer ", "");
		if (!tokenUtil.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		UserDetails userDetails = userRepository.findByUsername(tokenUtil.getUsername(token)).orElse(null);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of()));
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
