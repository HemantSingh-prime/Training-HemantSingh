package com.ps.cff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.cff.entity.AccountData;

/**
 * 
 * @author Hemant
 *
 */
@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Long> {

}
