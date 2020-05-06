/**
 * 
 */
package com.techames.wealthier.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.Role;
import com.techames.wealthier.web.security.service.SecurityService;
import com.techames.wealthier.web.security.service.UserValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
@Slf4j
public class UserController extends JCartAdminBaseController {
	private static final String viewPrefix = "users/";
	@Autowired
	protected SecurityService securityService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	protected PasswordEncoder passwordEncoder;

	List<String> permissions = getPermssions();

	private List<String> getCRUDPermissionOf(String entity) {
		String create = entity + "_" + "CREATE";
		String read = entity + "_" + "READ";
		String update = entity + "_" + "UPDATE";
		String delete = entity + "_" + "DELETE";
		return Arrays.asList(create, read, update, delete);
	}
	
	private List<String> getPermssions() {
		List<String> list = new ArrayList<>();
		list.addAll(getCRUDPermissionOf("CREDIT"));
		list.addAll(getCRUDPermissionOf("DEBIT"));
		list.addAll(getCRUDPermissionOf("USER"));
		list.addAll(getCRUDPermissionOf("ROLE"));
		list.addAll(getCRUDPermissionOf("PERMISSION"));
		list.addAll(getCRUDPermissionOf("DASHBOARD"));
		list.addAll(getCRUDPermissionOf("REPORT"));
		list.addAll(getCRUDPermissionOf("CLIENT"));
		return list;
	}
	
	@PostConstruct
	public void init() {
		createUserIfNot("admin@techames.com");
	}

	private void createUserIfNot(String username) {
		AppUserDetails appUser = securityService.findAppUserDetailsByEmail(username);
		List<String> adminPermList = getAdminPermList();
		if(appUser != null) {
			log.info("Updating password {} ", appUser);
			appUser.setPermissions(String.join(",", adminPermList));
			appUser.setPassword(passwordEncoder.encode("admin"));
			securityService.updateAppUserDetails(appUser);
		} else {
			appUser = new AppUserDetails();
			appUser.setUsername(username);
			appUser.setPassword(passwordEncoder.encode("admin"));
			appUser.setPermissions(String.join(",", adminPermList));
			securityService.createAppUserDetails(appUser);
		}
	}

	private List<String> getAdminPermList() {
		List<String> list = new ArrayList<>();
		list.addAll(getCRUDPermissionOf("USER"));
		list.addAll(getCRUDPermissionOf("ROLE"));
		list.addAll(getCRUDPermissionOf("PERMISSION"));
		return list;
	}

	@Override
	protected String getHeaderTitle() {
		return "Manage Users";
	}

	@ModelAttribute("rolesList")
	public List<Role> rolesList() {
		return securityService.getAllRoles();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(Model model) {
		List<AppUserDetails> list = securityService.getAllAppUserDetailss();
		model.addAttribute("users", list);
		model.addAttribute("permissions", permissions);
		return viewPrefix + "users";
	}

	@RequestMapping(value = "/users/new", method = RequestMethod.GET)
	public String createUserForm(Model model) {
		AppUserDetails user = new AppUserDetails();
		model.addAttribute("user", user);
		model.addAttribute("permissions", permissions);
		// model.addAttribute("rolesList",securityService.getAllRoles());

		return viewPrefix + "create_user";
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute("user") AppUserDetails user, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {

		log.info("User ser {}", user);
		userValidator.validate(user, result);

		/*
		 * if(result.hasErrors()){ return viewPrefix+"create_user"; }
		 */
		String password = user.getPassword();
		String encodedPwd = passwordEncoder.encode(password);
		user.setPassword(encodedPwd);
		AppUserDetails persistedUser = securityService.createAppUserDetails(user);
		log.debug("Created new User with id : {} and name : {}", persistedUser.getId(), persistedUser.getUsername());
		redirectAttributes.addFlashAttribute("info", "User created successfully");
		return "redirect:/users";
	}

	/*
	 * @RequestMapping(value="/users/{id}", method=RequestMethod.GET) public String
	 * editUserForm(@PathVariable Integer id, Model model) { AppUserDetails user =
	 * securityService.getAppUserDetailsById(id); Map<Integer, Role> assignedRoleMap
	 * = new HashMap<>(); List<Role> roles = user.getRoles(); for (Role role :
	 * roles) { assignedRoleMap.put(role.getId(), role); } List<Role> userRoles =
	 * new ArrayList<>(); List<Role> allRoles = securityService.getAllRoles(); for
	 * (Role role : allRoles) { if(assignedRoleMap.containsKey(role.getId())){
	 * userRoles.add(role); } else { userRoles.add(null); } }
	 * user.setRoles(userRoles); model.addAttribute("user",user);
	 * //model.addAttribute("rolesList",allRoles); return viewPrefix+"edit_user"; }
	 */

	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") AppUserDetails user, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return viewPrefix + "edit_user";
		}
		AppUserDetails persistedUser = securityService.updateAppUserDetails(user);
		log.debug("Updated user with id : {} and name : {}", persistedUser.getId(), persistedUser.getUsername());
		redirectAttributes.addFlashAttribute("info", "User updates successfully");
		return "redirect:/users";
	}

}
