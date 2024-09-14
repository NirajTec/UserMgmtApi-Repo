package com.niraj.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmailMessage(String toMail, String subject, String body) {
		boolean sendMailStatus = false;
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setSentDate(new Date());
			mimeMessageHelper.setText(body,true);
			javaMailSender.send(mimeMessage);
			sendMailStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sendMailStatus;
	}

}
