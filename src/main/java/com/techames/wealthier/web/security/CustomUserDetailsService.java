/**
 * 
 */
package com.techames.wealthier.web.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techames.wealthier.db.dao.AppUserDetailsRepository;
import com.techames.wealthier.db.model.AppUserDetails;

import lombok.extern.slf4j.Slf4j;



/**
 * @author Siva
 *
 */
@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService
{

	@Autowired
	private AppUserDetailsRepository appUserDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		AppUserDetails user = appUserDetailsRepository.findByUsername(userName);
		log.info("User got {} ", user);
		if(user == null){
			throw new UsernameNotFoundException("Email "+userName+" not found");
		}
		return user;
	}

}
