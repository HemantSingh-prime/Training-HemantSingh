package com.ps.cff.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name= "USER")
//@NamedQuery(name="User.findByUserName",
//            query = "Select u from User u where u.user_name=:userName"
//            )
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 
	 * ID created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "ID")
	private int id;
	
	/*
	 * 
	 * USER NAME created
	 */
	@Column(name= "USER_NAME")
	@NotNull
	private String userName;
	/*
	 * 
	 * PASSWORD created
	 */
	@Column(name= "PASSWORD")
	@NotNull
	private String password;
	
	/*
	 * 
	 * ROLE_ID created
	 */
	@Column(name=" ROLE_ID")
	private int roleId;
}
