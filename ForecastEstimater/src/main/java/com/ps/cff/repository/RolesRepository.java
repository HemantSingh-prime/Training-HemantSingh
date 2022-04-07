package com.ps.cff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.cff.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

}
