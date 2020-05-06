package com.techames.wealthier.web.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techames.wealthier.db.dao.DebitDetailsHome;
import com.techames.wealthier.db.dao.UserDefinedClientsHome;
import com.techames.wealthier.db.dao.UserDefinedSourcesHome;
import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.DebitDetails;
import com.techames.wealthier.db.model.Role;
import com.techames.wealthier.db.model.UserDefinedClients;
import com.techames.wealthier.db.model.UserDefinedSources;
import com.techames.wealthier.web.security.SecurityUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Siva
 *
 */
@Controller
//@Secured(SecurityUtil.MANAGE_CATEGORIES)
@Slf4j
public class DebitManagementController extends JCartAdminBaseController
{
	private static final String viewPrefix = "debit_management/";
	
	
	@Autowired DebitDetailsHome debitDetailsHome;
	
	@Autowired UserDefinedSourcesHome userDefinedSourcesHome;
	
	@Autowired UserDefinedClientsHome userDefinedClilentsHome;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Credit Transaction";
	}
	
	@RequestMapping(value="/debit_management", method=RequestMethod.GET)
	@PreAuthorize("@permissionSecurityService.hasPrivilege('DEBIT_READ')")
	public String listCategories(Model model) {
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//Role role = currentUser.getRoles().get(0);
		List<DebitDetails> list = null;
		if (currentUser.getUserType() == 3) {
			list = debitDetailsHome.getAllDebitTransactionDetailsByUserId(currentUser.getId());
			model.addAttribute("debitList",list);
		} else {
			list = debitDetailsHome.getAllDebitTransactionDetails(currentUser.getAccountId());
			model.addAttribute("debitList",list);
		}
		return viewPrefix+"debit_management";
	}
	
	@RequestMapping(value="/debit_management/new", method=RequestMethod.GET)
	public String createCategoryForm(Model model) {
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DebitDetails category = new DebitDetails();
		List<UserDefinedSources> sources = userDefinedSourcesHome.getAllSourcesByAccountId(currentUser.getAccountId());
		List<UserDefinedClients> userDefinedClientList = userDefinedClilentsHome.getAllClientsByAccountIdAndType(currentUser.getAccountId(),1,3);
		
		model.addAttribute("udClientList",userDefinedClientList);
		model.addAttribute("sourceList", sources);
		model.addAttribute("category",category);
		return viewPrefix+"create_debit_transaction";
	}

	@RequestMapping(value="/debit_management", method=RequestMethod.POST)
	public String createCategory(@Valid @ModelAttribute("debitDetails") DebitDetails debitDetails, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(result.hasErrors()){
			return viewPrefix+"create_debit_transaction";
		}
		
		int clientId = Integer.parseInt(debitDetails.getDebTransactionMoneyGiver());
		
		var info = userDefinedClilentsHome.findById(clientId);
		
		debitDetails.setDebClientId(clientId);
		debitDetails.setDebTransactionMoneyGiver(info.getUdClientName());
		debitDetails.setDebTransactionUserId(currentUser.getId());
		debitDetails.setDebTransactionAccountId(currentUser.getAccountId());
		log.info("credit transaction information got {} ", debitDetails);
		debitDetailsHome.persist(debitDetails);
		redirectAttributes.addFlashAttribute("info", "Category created successfully");
		return "redirect:/debit_management/new";
	}
	
	@RequestMapping(value="/debit_management/{id}", method=RequestMethod.GET)
	public String editCategoryForm(@PathVariable Integer id, Model model) {
		/*Category category = catalogService.getCategoryById(id);
		model.addAttribute("category",category);*/
		return viewPrefix+"edit_debit_transaction";
	}
	
/*	@RequestMapping(value="/debit_management/{id}", method=RequestMethod.POST)
	public String updateCategory(Category category, Model model, RedirectAttributes redirectAttributes) {
	`	Category persistedCategory = catalogService.updateCategory(category);
		log.debug("Updated category with id : {} and name : {}", persistedCategory.getId(), persistedCategory.getName());
		redirectAttributes.addFlashAttribute("info", "Category updated successfully");
		return "redirect:/debit_management";
	}*/

}
