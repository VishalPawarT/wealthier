/**
 * 
 */
package com.techames.wealthier.web.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techames.wealthier.db.dao.CreditDetailsHome;
import com.techames.wealthier.db.dao.UserDefinedClientsHome;
import com.techames.wealthier.db.dao.UserDefinedSourcesHome;
import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.CreditDetails;
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
public class CreditManagementController extends JCartAdminBaseController {
	private static final String viewPrefix = "credit_management/";

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	CreditDetailsHome creditDetailsHome;

	@Autowired
	UserDefinedSourcesHome userDefinedSourcesHome;

	@Autowired
	UserDefinedClientsHome userDefinedClilentsHome;

	@Override
	protected String getHeaderTitle() {
		return "Manage Credit Transaction";
	}

	@RequestMapping(value = "/credit_management", method = RequestMethod.GET)
	@PreAuthorize("@permissionSecurityService.hasPrivilege('CREDIT_READ')")
	public String listCategories(Model model) throws JsonProcessingException {
		AppUserDetails currentUser = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		// Role role = currentUser.getRoles().get(0);
		List<UserDefinedClients> userDefinedClientList = userDefinedClilentsHome
				.getAllClientsByAccountIdAndType(currentUser.getAccountId(), 1, 3);
		List<CreditDetails> list = null;
		if (currentUser.getUserType() == 3) {
			list = creditDetailsHome.getAllCreditTransactionDetailsByUserId(currentUser.getId());
			model.addAttribute("creditList", list);
		} else {
			list = creditDetailsHome.getAllCreditTransactionDetails(currentUser.getAccountId());
			model.addAttribute("creditList", list);
		}
		String creditJson = null;
		if (list != null) {
			Map<Integer, List<CreditDetails>> map = list.stream()
					.collect(Collectors.groupingBy(CreditDetails::getCrTransactionId));
			creditJson = mapper.writeValueAsString(map);
		}
		model.addAttribute("creditJson", creditJson);
		log.info("List got for credit details {} with clients {}  ", list, userDefinedClientList);
		return viewPrefix + "credit_management";
	}

	@RequestMapping(value = "/credit_management/new", method = RequestMethod.GET)
	@PreAuthorize("@permissionSecurityService.hasPrivilege('CREDIT_CREATE')")
	public String createCategoryForm(Model model) {
		AppUserDetails currentUser = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<UserDefinedSources> sources = userDefinedSourcesHome.getAllSourcesByAccountId(currentUser.getAccountId());
		List<UserDefinedClients> userDefinedClientList = userDefinedClilentsHome
				.getAllClientsByAccountIdAndType(currentUser.getAccountId(), 1, 3);
		CreditDetails category = new CreditDetails();
		model.addAttribute("udClientList", userDefinedClientList);
		model.addAttribute("sourceList", sources);
		model.addAttribute("category", category);
		return viewPrefix + "create_credit_transaction";
	}

	@RequestMapping(value = "/credit_management", method = RequestMethod.POST)
	@PreAuthorize("@permissionSecurityService.hasPrivilege('CREDIT_CREATE')")
	public String createCategory(@Valid @ModelAttribute("credit_transaction") CreditDetails creditDetails,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {

		AppUserDetails currentUser = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (result.hasErrors()) {
			return viewPrefix + "create_credit_transaction";
		}

		int clientId = Integer.parseInt(creditDetails.getCrTransactionMoneyGiver());

		var info = userDefinedClilentsHome.findById(clientId);

		creditDetails.setCrClientId(clientId);
		creditDetails.setCrTransactionMoneyGiver(info.getUdClientName());
		creditDetails.setCrTransactionUserId(currentUser.getId());
		creditDetails.setCrTransactionAccountId(currentUser.getAccountId());
		log.info("credit transaction information got {} ", creditDetails);
		creditDetailsHome.persist(creditDetails);
		redirectAttributes.addFlashAttribute("info", "Category created successfully");
		return "redirect:/credit_management/new";
	}

	@RequestMapping(value = "/credit_management/{id}", method = RequestMethod.GET)
	public String editCategoryForm(@PathVariable Integer id, Model model) {
		/*
		 * Category category = catalogService.getCategoryById(id);
		 * model.addAttribute("category",category);
		 */
		return viewPrefix + "edit_credit_transaction";
	}

	/*
	 * @RequestMapping(value="/credit_management/{id}", method=RequestMethod.POST)
	 * public String updateCategory(Category category, Model model,
	 * RedirectAttributes redirectAttributes) { ` Category persistedCategory =
	 * catalogService.updateCategory(category);
	 * log.debug("Updated category with id : {} and name : {}",
	 * persistedCategory.getId(), persistedCategory.getName());
	 * redirectAttributes.addFlashAttribute("info",
	 * "Category updated successfully"); return "redirect:/credit_management"; }
	 */

	@RequestMapping(value = "/passCreditTransaction/{id}", method = RequestMethod.GET)
	public String passCreditTransaction(@PathVariable Integer id, Model model) {
		AppUserDetails currentUser = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		creditDetailsHome.changeCreditTransactionStatus(currentUser.getAccountId(), id, true);
		return "redirect:/credit_management";
	}

}
