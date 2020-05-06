package com.techames.wealthier.web.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techames.wealthier.db.dao.AppUserDetailsRepository;
import com.techames.wealthier.db.model.AppUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomAuthentication implements AuthenticationProvider {
   
	@Autowired
	private AppUserDetailsRepository appUserDetailsRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		   	String user = authentication.getName();
	        String password = authentication.getCredentials().toString();
	        log.info("User name {} ", user);
	        AppUserDetails appUser = appUserDetailsRepository.findByUsername(user);
	        log.info("App user got {} {}", appUser, encoder.encode(password));
	        if (encoder.matches(password, appUser.getPassword())) {           // replace your custom code here for custom authentication
	            return new UsernamePasswordAuthenticationToken(appUser, appUser.getPassword(), Collections.EMPTY_LIST);
	        } else {
	            throw new BadCredentialsException("External system authentication failed");
	        }
	}
}