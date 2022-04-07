package com.ps.cff.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name ="ROLES")
public class Roles implements Serializable {

	/*
	 * 
	 * Role_id created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name=" ROLE_ID")
	private int roleId;
	
	/*
	 * 
	 * Role created
	 */
	@Column(name="ROLE")
	@NotNull
	private String role;
	
	/*
	 * 
	 * User collection created
	 */
	@OneToMany
	@JoinColumn(name="role_Id", referencedColumnName = "role_Id")
	private Set<User> user;
}
