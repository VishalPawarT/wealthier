package com.techames.wealthier.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "permission")
public class Permission {

	    @Id
	    private Integer id;
	    private String name;
	
}
