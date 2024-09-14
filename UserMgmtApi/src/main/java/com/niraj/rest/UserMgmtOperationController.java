package com.niraj.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niraj.bindings.ActivateUser;
import com.niraj.bindings.LoginCredentials;
import com.niraj.bindings.RecoverPassword;
import com.niraj.bindings.UserAccount;
import com.niraj.service.IUserMgmtService;

@RestController
@RequestMapping("/user-api")
public class UserMgmtOperationController {

	@Autowired
	IUserMgmtService iUserMgmtService;

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody UserAccount userAccount) {

		try {
			System.out.println("User Account :::::::::" + userAccount);
			String resultMsg = iUserMgmtService.registerUser(userAccount);
			return new ResponseEntity<String>(resultMsg, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/activate")
	public ResponseEntity<String> activateUser(@RequestBody ActivateUser activateUser) {

		try {
			String resultMsg = iUserMgmtService.activateUser(activateUser);
			return new ResponseEntity<String>(resultMsg, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginCredentials loginCredentials) {

		try {
			String resultMsg = iUserMgmtService.loginUser(loginCredentials);
			return new ResponseEntity<String>(resultMsg, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/report")
	public ResponseEntity<?> listAllUser() {

		try {
			List<UserAccount> allUser = iUserMgmtService.listAllUser();
			return new ResponseEntity<List<UserAccount>>(allUser, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("find/{id}")
	public ResponseEntity<?> showUserById(@PathVariable Integer id) {

		try {
			UserAccount userAccount = iUserMgmtService.showUserByUserById(id);
			return new ResponseEntity<UserAccount>(userAccount, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("find/{email}/{name}")
	public ResponseEntity<?> showUserByEmailAndName(@PathVariable String name, String email) {

		try {
			UserAccount userAccount = iUserMgmtService.showUserByUserByEmail(email, name);

			return new ResponseEntity<UserAccount>(userAccount, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update")
	public ResponseEntity<String> updateUserDetails(@RequestBody UserAccount userAccount) {

		try {
			String result = iUserMgmtService.updateUser(userAccount);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> updateUserDetails(@PathVariable Integer id) {

		try {
			String result = iUserMgmtService.deleteByUserId(id);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PatchMapping("/changeStatus/{id}/{status}")
	public ResponseEntity<String> changeStatus(@PathVariable Integer id, @PathVariable String status) {

		try {
			String result = iUserMgmtService.changeUserStatus(id, status);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/recoverPassword")
	public ResponseEntity<String> recoverPassword(@RequestBody RecoverPassword recover) {
		try {
			System.out.println("Recover password :::::::::::::::::::::::");
			String result = iUserMgmtService.recoverPassword(recover);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
