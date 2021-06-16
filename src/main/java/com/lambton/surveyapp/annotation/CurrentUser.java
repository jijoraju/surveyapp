/**
 * &copyright upski international
 */
package com.lambton.surveyapp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * @author Jijo Raju
 * @Since Jun 16, 2021 8:28:24 PM
 *
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
