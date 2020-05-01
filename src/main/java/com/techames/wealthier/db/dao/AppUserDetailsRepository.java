package com.techames.wealthier.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techames.wealthier.db.model.AppUserDetails;

public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, Integer>{

	AppUserDetails findByUsername(String userName);

}
