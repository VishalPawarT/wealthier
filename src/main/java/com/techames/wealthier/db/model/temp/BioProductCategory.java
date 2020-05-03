package com.techames.wealthier.db.model.temp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bio_product_category")
@Data
public class BioProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_category_id;
	
	@Column(unique = true)
	private String product_category_name;
	private String product_category_info;
	
}
