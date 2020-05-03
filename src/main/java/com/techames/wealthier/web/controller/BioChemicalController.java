package com.techames.wealthier.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techames.wealthier.db.model.temp.BioProduct;
import com.techames.wealthier.db.model.temp.BioProductCategory;
import com.techames.wealthier.db.model.temp.BioProductCategoryRepository;
import com.techames.wealthier.db.model.temp.BioProductRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/biocreation")
public class BioChemicalController {

	private final String viewPrefix = "biocreation";
	
	@Autowired
	private BioProductCategoryRepository bioProductCategoryRepository;
	

	@Autowired
	private BioProductRepository bioProductRepository;
	
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public String getCategoryPage() {
		return viewPrefix + "/category";
	}
	
	@RequestMapping(value="/category", method=RequestMethod.POST)
	public String postCategory(@ModelAttribute("form") BioProductCategory cat) {
		bioProductCategoryRepository.save(cat);
		return viewPrefix + "/category";
	}
	
	@RequestMapping(value="/product", method=RequestMethod.GET)
	public String getProductPage(Model model) {
		model.addAttribute("categories", bioProductCategoryRepository.findAll());
		return viewPrefix + "/product";
	}
	
	@RequestMapping(value="/product", method=RequestMethod.POST)
	public String postCategory(@ModelAttribute("form") BioProduct cat) {
		String[] result = cat.getProduct_category_name().split("XXX");
		cat.setProduct_category_name(result[1]);
		cat.setProduct_category_id(Integer.parseInt(result[0]));
		bioProductRepository.save(cat);
		return viewPrefix + "/product";
	}
	
}
