package com.techames.wealthier.web.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;

import com.techames.wealthier.db.dao.UserDefinedClientsHome;
import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.UserDefinedClients;
import com.techames.wealthier.utils.CommonUtility;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Siva
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_ORDERS)
@Slf4j
public class UserDefineClientManagementController extends JCartAdminBaseController
{
	private static final String viewPrefix = "clients/";

	//@Autowired protected EmailService emailService;
	@Autowired private TemplateEngine templateEngine;
	
	@Autowired CommonUtility cmUtility;
	
	@Autowired private UserDefinedClientsHome userDefineClientHome;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Orders";
	}
	
	
	@RequestMapping(value="/clients", method=RequestMethod.GET)
	public String listOrders(Model model) {
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserDefinedClients> sourceList = userDefineClientHome.getAllClientsByAccountId(currentUser.getAccountId());	
		model.addAttribute("sourceList", sourceList);
		return viewPrefix+"clients";
	}
	
	@RequestMapping(value="/clients/new", method=RequestMethod.GET)
	public String createCategoryForm(Model model) {
		UserDefinedClients client = new UserDefinedClients();
		model.addAttribute("client", client);
		return viewPrefix+"create_new_client";
	}
	
	@RequestMapping(value="/clients", method=RequestMethod.POST)
	public String createCategory(@Valid @ModelAttribute("client") UserDefinedClients client, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(result.hasErrors()){
			return viewPrefix+"create_client";
		}
		int newClientId = cmUtility.getUniqueNumber();
		while (userDefineClientHome.findById(newClientId) != null) {
			newClientId= cmUtility.getUniqueNumber();
		}
		client.setUdClientId(cmUtility.getUniqueNumber());
		client.setUdClientAccountId(currentUser.getAccountId());
		client.setUdClientUserId(currentUser.getId());
		log.info("new client information got {} ", client);
		userDefineClientHome.persist(client);
		redirectAttributes.addFlashAttribute("info", "Category created successfully");
		return "redirect:/clients";
	}
	
}