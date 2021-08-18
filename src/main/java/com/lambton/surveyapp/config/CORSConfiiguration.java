/**
 * &copyright upski international
 */
package com.lambton.surveyapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jijo Raju
 * @Since Jun 8, 2021 7:24:06 PM
 *
 */
@Configuration
public class CORSConfiiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000", "https://localhost:3000",
						"http://surveyapp.ap-south-1.elasticbeanstalk.com")
				.allowedMethods("GET", "POST", "OPTIONS").allowedHeaders("*")
				.exposedHeaders("Set-Cookie", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
				.allowCredentials(true).maxAge(3600);
	}

}
