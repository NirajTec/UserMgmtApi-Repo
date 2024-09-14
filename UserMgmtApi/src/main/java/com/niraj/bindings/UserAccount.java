package com.niraj.bindings;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
	
	
    private Integer userId;
	private String name;
	private String email;
	private String mobileNo;
	private String gender="Female";
	private LocalDate dob=LocalDate.now();
	private Long adharNo;
	
	
	
}
