/**
 * &copyright upski international
 */
package com.lambton.surveyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jijo Raju
 * @Since Jul 21, 2021 2:17:17 PM
 *
 */

@Controller
public class WebController {

	@GetMapping({ "/" })
	public String redirectToHome() {
		return "redirect:/web/";
	}

	@GetMapping({ "/web/", "/web/**" })
	public String getHome() {
		return "index";
	}

}
