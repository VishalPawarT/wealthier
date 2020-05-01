package com.techames.wealthier.web.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techames.wealthier.db.dao.AppUserDetailsRepository;
import com.techames.wealthier.db.dao.PermissionRepository;
import com.techames.wealthier.db.dao.RoleRepository;
import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.Permission;
import com.techames.wealthier.db.model.Role;;

@Service
@Transactional
public class SecurityService
{
	@Autowired AppUserDetailsRepository appUserDetailsRepository;
	@Autowired PermissionRepository permissionRepository;
	@Autowired RoleRepository roleRepository;
	
	public AppUserDetails findAppUserDetailsByEmail(String AppUserDetailsName)
	{
		return appUserDetailsRepository.findByUsername(AppUserDetailsName);
	}
	
	public String resetPassword(String email)
	{
		AppUserDetails appUserDetails = findAppUserDetailsByEmail(email);
		if(appUserDetails == null)
		{
			throw new RuntimeException("Invalid email address");
		}
		String uuid = UUID.randomUUID().toString();
		appUserDetails.setValidationToken(uuid);
		appUserDetailsRepository.save(appUserDetails);
		return uuid;
	}

	public void updatePassword(String email, String token, String password)
	{
		AppUserDetails AppUserDetails = findAppUserDetailsByEmail(email);
		if(AppUserDetails == null)
		{
			throw new RuntimeException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(AppUserDetails.getValidationToken())){
			throw new RuntimeException("Invalid password reset token");
		}
		AppUserDetails.setPassword(password);
		AppUserDetails.setValidationToken(null);
	}

	public boolean verifyPasswordResetToken(String email, String token)
	{
		AppUserDetails AppUserDetails = findAppUserDetailsByEmail(email);
		if(AppUserDetails == null)
		{
			throw new RuntimeException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(AppUserDetails.getValidationToken())){
			return false;
		}
		return true;
	}
	
	public List<Permission> getAllPermissions() {
		return permissionRepository.findAll();
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleByName(String roleName)
	{
		return roleRepository.findByName(roleName);
	}
	
	/*
	 * public Role createRole(Role role) { Role roleByName =
	 * getRoleByName(role.getName()); if(roleByName != null){ throw new
	 * RuntimeException("Role "+role.getName()+" already exist"); } List<Permission>
	 * persistedPermissions = new ArrayList<>(); List<Permission> permissions =
	 * role.getPrivileges(); if(permissions != null){ for (Permission permission :
	 * permissions) { if(permission.getId() != null) {
	 * persistedPermissions.add(permissionRepository.findById(permission.getId()).
	 * get()); } } }
	 * 
	 * role.setPrivileges(persistedPermissions); return roleRepository.save(role); }
	 * 
	 * public Role updateRole(Role role) { Role persistedRole =
	 * getRoleById(role.getId()); if(persistedRole == null){ throw new
	 * RuntimeException("Role "+role.getId()+" doesn't exist"); }
	 * persistedRole.setDescription(role.getDescription()); List<Permission>
	 * updatedPermissions = new ArrayList<>(); List<Permission> permissions =
	 * role.getPrivileges(); if(permissions != null){ for (Permission permission :
	 * permissions) { if(permission.getId() != null) {
	 * updatedPermissions.add(permissionRepository.findById(permission.getId()).get(
	 * )); } } } persistedRole.setPrivileges(updatedPermissions); return
	 * roleRepository.save(persistedRole); }
	 */
	public Role getRoleById(Integer id) {
		return roleRepository.findById(id).get();
	}
	
	public AppUserDetails getAppUserDetailsById(Integer id)
	{
		return appUserDetailsRepository.findById(id).get();
	}
	
	public List<AppUserDetails> getAllAppUserDetailss() {
		return appUserDetailsRepository.findAll();
	}
	
	public AppUserDetails createAppUserDetails(AppUserDetails AppUserDetails)
	{
		AppUserDetails AppUserDetailsByEmail = findAppUserDetailsByEmail(AppUserDetails.getUsername());
		if(AppUserDetailsByEmail != null){
			throw new RuntimeException("Email "+AppUserDetails.getUsername() +" already in use");
		}
		List<Role> persistedRoles = new ArrayList<>();
		/*
		 * List<Role> roles = AppUserDetails.getRoles(); if(roles != null){ for (Role
		 * role : roles) { if(role.getId() != 0) {
		 * persistedRoles.add(roleRepository.findById(role.getId()).get()); } } }
		 * AppUserDetails.setRoles(persistedRoles)
		 */;
		
		return appUserDetailsRepository.save(AppUserDetails);
	}
	
	public AppUserDetails updateAppUserDetails(AppUserDetails AppUserDetails)
	{
		AppUserDetails persistedAppUserDetails = getAppUserDetailsById(AppUserDetails.getId());
		if(persistedAppUserDetails == null){
			throw new RuntimeException("AppUserDetails "+AppUserDetails.getId()+" doesn't exist");
		}
		
		List<Role> updatedRoles = new ArrayList<>();
		/*
		 * List<Role> roles = AppUserDetails.getRoles(); if(roles != null){ for (Role
		 * role : roles) { if(role.getId() != 0) {
		 * updatedRoles.add(roleRepository.findById(role.getId()).get()); } } }
		 * persistedAppUserDetails.setRoles(updatedRoles);
		 */
		return appUserDetailsRepository.save(persistedAppUserDetails);
	}

}
