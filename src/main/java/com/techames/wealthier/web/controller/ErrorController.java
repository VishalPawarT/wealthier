/**
 * 
 */
package com.techames.wealthier.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class ErrorController
{
	private static final String viewPrefix = "error/";
	
	@RequestMapping("/403")
	public String accessDenied()
	{
		return viewPrefix+"accessDenied";
	}
	
}
