package com.niraj.service;

import java.util.List;

import com.niraj.bindings.ActivateUser;
import com.niraj.bindings.LoginCredentials;
import com.niraj.bindings.RecoverPassword;
import com.niraj.bindings.UserAccount;

public interface IUserMgmtService {

	public String registerUser(UserAccount userAccount) throws Exception;

	public String activateUser(ActivateUser activateUser);

	public String loginUser(LoginCredentials loginCredentials);

	public List<UserAccount> listAllUser();

	public UserAccount showUserByUserById(Integer id);

	public UserAccount showUserByUserByEmail(String email, String name);

	public String updateUser(UserAccount userAccount);

	public String deleteByUserId(Integer id);

	public String changeUserStatus(Integer id, String status);

	public String recoverPassword(RecoverPassword recoverPassword)  throws Exception;

}
