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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.techames.wealthier.db.dao.UserDefinedSourcesHome;
import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.UserDefinedSources;
import com.techames.wealthier.web.security.SecurityUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Siva
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_ORDERS)
@Slf4j
public class SourceController extends JCartAdminBaseController
{
	private static final String viewPrefix = "sources/";

	//@Autowired protected EmailService emailService;
	@Autowired private TemplateEngine templateEngine;
	
	@Autowired private UserDefinedSourcesHome userSourceHome;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Orders";
	}
	
	
	@RequestMapping(value="/sources", method=RequestMethod.GET)
	public String listOrders(Model model) {
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserDefinedSources> sourceList = userSourceHome.getAllSourcesByAccountId(currentUser.getAccountId());	
		model.addAttribute("sourceList", sourceList);
		return viewPrefix+"sources";
	}
	
	@RequestMapping(value="/sources/new", method=RequestMethod.GET)
	public String createCategoryForm(Model model) {
		UserDefinedSources source = new UserDefinedSources();
		model.addAttribute("source", source);
		return viewPrefix+"create_new_source";
	}
	
	@RequestMapping(value="/sources", method=RequestMethod.POST)
	public String createCategory(@Valid @ModelAttribute("source") UserDefinedSources source, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(result.hasErrors()){
			return viewPrefix+"create_source";
		}
		source.setAccountId(currentUser.getAccountId());
		source.setUserId(currentUser.getId());
		log.info("new source information got {} ", source);
		userSourceHome.persist(source);
		redirectAttributes.addFlashAttribute("info", "Category created successfully");
		return "redirect:/sources";
	}
	
}