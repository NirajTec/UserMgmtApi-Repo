package com.niraj.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.niraj.bindings.ActivateUser;
import com.niraj.bindings.LoginCredentials;
import com.niraj.bindings.RecoverPassword;
import com.niraj.bindings.UserAccount;
import com.niraj.entity.UserMaster;
import com.niraj.repo.IUserMasterRepo;
import com.niraj.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements IUserMgmtService {

	@Autowired
	private IUserMasterRepo iUserMasterRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private Environment env;

	@Override
	public String registerUser(UserAccount userAccount) throws Exception {
		UserMaster userMaster = new UserMaster();
		BeanUtils.copyProperties(userAccount, userMaster);
		userMaster.setPassword(generateRandomPassword(6));
		userMaster.setActive_sw("INACTIVE");
		UserMaster userMasterSaved = iUserMasterRepo.save(userMaster);
		// send Email
		String subject = "User Registartion Sucess";
		String body = readEmailMessageBody(env.getProperty("mailbody.registerUser.location"), userAccount.getName(),
				userMasterSaved.getPassword());
		emailUtils.sendEmailMessage(userMasterSaved.getEmail(), subject, body);
		return userMasterSaved != null ? "User Saved with User Id :" + userMasterSaved.getUserId() + ". Check Email for Temporary Password "
				: "User Failed to Save";
	}

	@Override
	public String activateUser(ActivateUser activateUser) {
		UserMaster userMaster = new UserMaster();
		userMaster.setEmail(activateUser.getEmail());
		userMaster.setPassword(activateUser.getTempPassword());
		Example<UserMaster> exampleList = Example.of(userMaster);
		List<UserMaster> userMasterList = iUserMasterRepo.findAll(exampleList);
		if (userMasterList != null) {
			UserMaster master = userMasterList.get(0);
			master.setActive_sw("ACTIVE");
			master.setPassword(activateUser.getConfirmPassword());
			UserMaster updatedEntity = iUserMasterRepo.save(master);
			return updatedEntity != null ? "User is Activated with the New Password" : "User doesnot exits";
		}
		return null;
	}

	@Override
	public String loginUser(LoginCredentials loginCredentials) {
		UserMaster userMaster = new UserMaster();
		BeanUtils.copyProperties(loginCredentials, userMaster);
		Example<UserMaster> master = Example.of(userMaster);
		List<UserMaster> listOfUserMaster = iUserMasterRepo.findAll();
		if (listOfUserMaster.size() == 0) {
			return "Invalid Credentials";
		} else {
			UserMaster user = listOfUserMaster.get(0);
			if (user.getActive_sw().equalsIgnoreCase("ACTIVE")) {
				return "Valid Credentials and Login Succesfull";
			} else {
				return "User Account is not Active ";
			}
		}
	}

	@Override
	public List<UserAccount> listAllUser() {
		List<UserAccount> allUserAccount = iUserMasterRepo.findAll().stream().map(entity -> {
			UserAccount userAccount = new UserAccount();
			BeanUtils.copyProperties(entity, userAccount);
			return userAccount;
		}).toList();

		return allUserAccount;

		/*
		 * List<UserMaster> allUser=iUserMasterRepo.findAll(); List<UserAccount>
		 * listAllUser=new ArrayList<>(); allUser.forEach(entity->{ UserAccount user=new
		 * UserAccount(); BeanUtils.copyProperties(entity, user); listAllUser.add(user);
		 * }); return listAllUser;
		 */
	}

	@Override
	public UserAccount showUserByUserById(Integer id) {
		Optional<UserMaster> opt = iUserMasterRepo.findById(id);
		if (opt.isPresent()) {
			UserAccount userAccount = new UserAccount();
			BeanUtils.copyProperties(userAccount, opt.get());
			return userAccount;
		}
		return null;
	}

	@Override
	public UserAccount showUserByUserByEmail(String email, String name) {
		UserMaster master = iUserMasterRepo.findByNameAndEmail(name, email);
		if (master != null) {
			UserAccount userAccount = new UserAccount();
			BeanUtils.copyProperties(master, userAccount);
			return userAccount;
		}

		return null;
	}

	@Override
	public String updateUser(UserAccount userAccount) {

		Optional<UserMaster> opt = iUserMasterRepo.findById(userAccount.getUserId());
		if (opt.isPresent()) {
			UserMaster master = opt.get();
			BeanUtils.copyProperties(userAccount, master);
			iUserMasterRepo.save(master);
			return "User Details are updated ";

		}

		return "User Details are not updated ";
	}

	@Override
	public String deleteByUserId(Integer id) {

		Optional<UserMaster> opt = iUserMasterRepo.findById(id);
		if (opt.isPresent()) {
			iUserMasterRepo.deleteById(id);
			return "User Deleted Succesfully";
		}
		return "User not found for Deletion";
	}

	@Override
	public String changeUserStatus(Integer id, String status) {
		Optional<UserMaster> opt = iUserMasterRepo.findById(id);
		if (opt.isPresent()) {
			UserMaster master = opt.get();
			master.setActive_sw(status);
			iUserMasterRepo.save(master);
			return "Status Changed  Succesfully";
		}
		return "Failed to  Changed  Status";
	}

	@Override
	public String recoverPassword(RecoverPassword recoverPassword) throws Exception {

		UserMaster master = iUserMasterRepo.findByNameAndEmail(recoverPassword.getName(), recoverPassword.getEmail());
		if (master != null) {
			String pwd = master.getPassword();
			// send mail to customer for sending password in mail
			String subject = "Password Recovery";
			String body = readEmailMessageBody(env.getProperty("mailbody.recoverPassword.location"), master.getName(),
					pwd);
			emailUtils.sendEmailMessage(master.getEmail(), subject, body);

			return pwd;
		}
		return "User and Email Not Found";
	}

	private String generateRandomPassword(int length) {
		String aplhaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(aplhaNumeric.length());
			char randomChar = aplhaNumeric.charAt(index);
			sb.append(randomChar);
		}
		String randomString = sb.toString();
		return randomString;
	}

	private String readEmailMessageBody(String fileName, String fullName, String pwd) throws Exception {
		String mailBody = null;
		String url = "";
		try (FileReader filReader = new FileReader(fileName); BufferedReader bf = new BufferedReader(filReader)) {
			StringBuffer sf = new StringBuffer();
			String line = null;
			do {
				line = bf.readLine();
				if(line!=null)
				sf.append(line);

			} while (line != null);
			mailBody = sf.toString();
			mailBody=mailBody.replace("{FULL_NAME}", fullName);
			mailBody=mailBody.replace("{PASSWORD}", pwd);
			mailBody=mailBody.replace("{URL}", url);
			
			System.out.println("mailBody "+mailBody);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return mailBody;
	}

}
