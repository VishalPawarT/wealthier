package com.techames.wealthier.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techames.wealthier.db.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByName(String roleName);

}
