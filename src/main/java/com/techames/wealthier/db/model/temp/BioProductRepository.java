package com.techames.wealthier.db.model.temp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.Data;

public interface BioProductRepository extends JpaRepository<BioProduct, Integer> {

	
}
