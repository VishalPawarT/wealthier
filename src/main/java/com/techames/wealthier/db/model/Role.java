package com.techames.wealthier.db.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@Column(name = "id")
    private int id;
    private String name;
    //private List<Permission> privileges;
    private String description;
    
}