package com.niraj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niraj.entity.UserMaster;

@Repository
public interface IUserMasterRepo extends JpaRepository<UserMaster, Integer> {
	
	public UserMaster findByEmailAndPassword(String user,String pwd);
	public UserMaster findByNameAndEmail(String name,String email);
	

}
