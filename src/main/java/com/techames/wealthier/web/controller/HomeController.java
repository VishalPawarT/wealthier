/**
 * 
 */
package com.techames.wealthier.web.controller;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techames.wealthier.db.dao.CreditDetailsHome;
import com.techames.wealthier.db.dao.DebitDetailsHome;
import com.techames.wealthier.db.dao.JsonResponse;
import com.techames.wealthier.db.dao.UserDefinedSourcesHome;
import com.techames.wealthier.db.model.AppUserDetails;
import com.techames.wealthier.db.model.DailyBalanceSheet;
import com.techames.wealthier.db.model.UserDefinedSources;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Slf4j
@Controller
public class HomeController extends JCartAdminBaseController
{	
	
	@Autowired CreditDetailsHome creditHome;
	
	@Autowired DebitDetailsHome debitDetailsHome;
	
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@Autowired UserDefinedSourcesHome userDefinedSourcesHome;
	
	@Override
	protected String getHeaderTitle() {
		return "Home";
	}
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		double totalCreditAmount = 0;
		double totalDebitAmount = 0;
		try {
			totalCreditAmount = creditHome.getTotalCreditAmountForUser(currentUser.getAccountId());
			totalDebitAmount = debitDetailsHome.getTotalDebitAmountForUser(currentUser.getAccountId());
		} catch(Exception e) {
			log.info("exception while fetching info {} ", e );
		}
		model.addAttribute("totalCreditAmount", totalCreditAmount);
		model.addAttribute("totalDebitAmount", totalDebitAmount);
		return "home";
	}
	
	@RequestMapping("/getGraph")
	public @ResponseBody String getGraph(@RequestParam("transType") String transType) throws JsonProcessingException
	{
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String res = null;
		if(transType.equals("cr")) {
			List<Object> creditDetailsList = creditHome.getAllCreditTransactionDetailsByDay(currentUser.getAccountId()); 
			List<JsonResponse> list = new ArrayList<>();
			for(Object obj : creditDetailsList) {
				JsonResponse json = new JsonResponse();
				Object[] obj2 = (Object[]) obj;
				json.setAmount((double) obj2[1]);
				json.setDate((Date) obj2[0]);
				list.add(json);
			}
			res = jsonMapper.writeValueAsString(list);
		} else {
			List<Object> creditDetailsList = debitDetailsHome.getAllDebitTransactionDetailsByDay(currentUser.getAccountId()); 
			List<JsonResponse> list = new ArrayList<>();
			for(Object obj : creditDetailsList) {
				JsonResponse json = new JsonResponse();
				Object[] obj2 = (Object[]) obj;
				json.setAmount((double) obj2[1]);
				json.setDate((Date) obj2[0]);
				list.add(json);
			}
			res = jsonMapper.writeValueAsString(list);
		}
		return res;
	}
	
	@RequestMapping("/reports")
	public String getReports(Model model)
	{
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DailyBalanceSheet> dailyBalanceSheetList = creditHome.getDailyBalanceSheetByAccountId(currentUser.getAccountId());
		//List<DailyBalanceSheet> dailyBalanceSheetList = creditHome.getDailyBalanceSheet(currentUser.getId());
		List<UserDefinedSources> sourceList = userDefinedSourcesHome.getAllSourcesByAccountId(currentUser.getAccountId()); 
		model.addAttribute("sourceList", sourceList);
		model.addAttribute("dbSheetList", dailyBalanceSheetList);
		return "reports/reports";
	}

	@RequestMapping("/getSourceWiseReports")
	public @ResponseBody String getSourceWiseReports(@RequestParam("sourceInfo") String sourceInfo) throws JsonProcessingException
	{
		String[] sourceInfoArr = sourceInfo.split("XXX");
		AppUserDetails currentUser = (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DailyBalanceSheet> dailyBalanceSheetList = creditHome.getDailyBalanceSheetBySourceId(currentUser.getId(), Integer.valueOf(sourceInfoArr[0]), sourceInfoArr[1]);
		return jsonMapper.writeValueAsString(dailyBalanceSheetList);
   }

}