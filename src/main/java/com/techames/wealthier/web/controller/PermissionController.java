/**
 * 
 */
package com.techames.wealthier.web.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techames.wealthier.db.dao.PermissionRepository;
import com.techames.wealthier.db.model.Permission;
import com.techames.wealthier.web.security.SecurityUtil;

/**
 * @author Siva
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends JCartAdminBaseController
{
	private static final String viewPrefix = "permissions/";
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Permissions";
	}
	
	@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public String listPermissions(Model model) {
		List<Permission> list = permissionRepository.findAll();
		model.addAttribute("permissions",list);
		return viewPrefix+"permissions";
	}

}
