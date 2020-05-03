package com.techames.wealthier.db.model.temp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bio_product")
@Data
public class BioProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;
	
	private int product_category_id;
	private String product_category_name;
	
	@Column(unique = true)
	private String product_name;
	private String product_image;
	private String product_info;
	
}
