package com.niraj.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER_MASTER")
public class UserMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(length = 20)
	private String name;
	@Column(length = 30)
	private String email;
	private String password;
	private String mobileNo;
	@Column(length = 10)
	private String gender;
	private LocalDate dob;
	@Column(length = 20)
	private Long adharNo;
	private String active_sw;
	
	@Column(insertable =true,updatable = false)
	private LocalDate createdOn;
	@Column(insertable =false,updatable = true)
	private LocalDate updatedOn;
	
	@Column(length = 20)
	private String createdBy;
	@Column(length = 20)
	private String updatedBy;
	
	
}
