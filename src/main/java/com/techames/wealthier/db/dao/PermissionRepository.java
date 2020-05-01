package com.techames.wealthier.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techames.wealthier.db.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{

}
