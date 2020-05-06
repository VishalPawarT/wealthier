package com.techames.wealthier.web.security.service;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.techames.wealthier.db.model.AppUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("permissionSecurityService")
@RequiredArgsConstructor
@Slf4j
public class PermissionSecurityService {
	
    public boolean hasPrivilege(String privilege) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final HttpSession session = attr.getRequest().getSession(false);
        if (auth != null && auth.isAuthenticated() && session != null) {
        	
        	AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("currentUser got in permission handler {} ", currentUser);
        	if(currentUser == null) {
            	log.error("User got null from session {}");
            }
        	log.info("privilege got {} ",privilege);
            if(currentUser.getPermissions().contains(privilege)) {
            	return true;
            }
			/*
			 * Iterator<String> roles = currentUser.getRoles().iterator();
			 * 
			 * while(roles.hasNext()) { String role = roles.next();
			 * log.info("role got {} and privilages {} ", role);
			 * if(role.equalsIgnoreCase(privilege)) { return true; } }
			 */
        }
        return false;
    }
    
    public boolean hasRole(String role) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final HttpSession session = attr.getRequest().getSession(false);
        if (auth != null && auth.isAuthenticated() && session != null) {
            final String currentRole = (String) session.getAttribute("role");
            log.info("Current role got {} {} {}", currentRole, role, role.equalsIgnoreCase(currentRole));
            return role.equalsIgnoreCase(currentRole);
           
        }
        return false;
    }
    
    public boolean isURLOpen(boolean open) {
        return open;
    }
    
    
    
}