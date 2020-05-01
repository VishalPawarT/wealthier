/**
 * 
 */
package com.techames.wealthier.web.security.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.techames.wealthier.db.model.AppUserDetails;

/**
 * @author Siva
 *
 */
@Component
public class UserValidator implements Validator
{
	@Autowired protected MessageSource messageSource;
	@Autowired protected SecurityService securityService;
	
	@Override
	public boolean supports(Class<?> clazz)
	{
		return AppUserDetails.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors)
	{
		AppUserDetails user = (AppUserDetails) target;
		String email = user.getUsername();
		AppUserDetails userByEmail = securityService.findAppUserDetailsByEmail(email);
		if(userByEmail != null){
			errors.rejectValue("email", "error.exists", new Object[]{email}, "Email "+email+" already in use");
		}
	}
	
	
}
