package com.employee.Employee.Managment.config;


import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.employee.Employee.Managment.entities.EmailData;
import com.employee.Employee.Managment.entities.User;
import com.employee.Employee.Managment.entities.Vendor;
import com.employee.Employee.Managment.model.UserDto;
import com.employee.Employee.Managment.repos.EmailDataRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

@Service
public class EmailService {

	@Value("${gmail}")
	private String from;

	@Value("${password.email}")
	private String password;

	@Value("${username.email}")
	private String username;
	@Value("${receiver.email}")
	private String receiverEmail;
	@Value("${localhost}")
	private String localhost;


@Autowired
private EmailDataRepo emailDataRepo;

	final static Logger logger = LoggerFactory.getLogger(EmailService.class);






	private String getBody(String unsubscribeLink) {
		String[] greetings = { "Hi", "Hello", "Hey" };
		String[] services = { "service", "platform", "tool" };
		String[] endings = { "Thank you for your attention.", "We hope this information is useful to you.",
				"Let us know if you have any questions." };

		Random random = new Random();
		String greeting = greetings[random.nextInt(greetings.length)];
		String service = services[random.nextInt(services.length)];
		String ending = endings[random.nextInt(endings.length)];

		return "<div style=\"max-width:550px; padding:18px; border:1px solid #dadada; -webkit-border-radius:10px; -moz-border-radius:10px; border-radius:10px; font-family:Arial, Helvetica, sans-serif; font-size:15px; color:#495057;\">"
				+ "<p style=\"font-size:17px; font-weight:bold;\">" + greeting + ",</p>" + "<p>Thank you for using our "
				+ service + ". Attached are the files you uploaded to our website.</p>"
				+ "<p>If you prefer not to receive future emails from us, please <a href=\"" + unsubscribeLink
				+ "\">unsubscribe here</a>.</p>" + "<p>" + ending + "</p>" + "<p>Best regards,<br>Data Courier</p>"
				+ "</div>";
	}

	private String getBody(String userName, String unsubscribeLink, String companyName) {
		return "<div style=\"max-width:550px; padding:18px; border:1px solid #dadada; -webkit-border-radius:10px; -moz-border-radius:10px; border-radius:10px; font-family:Arial, Helvetica, sans-serif; font-size:15px; color:#495057;\">"
				+ "<p style=\"font-size:17px; font-weight:bold;\">Dear " + userName + ",</p>"
				+ "<p>Thank you for using our service. Attached are the files you uploaded to our website.</p>"
				+ "<p>If you prefer not to receive future emails from us, please <a href=\"" + unsubscribeLink
				+ "\">unsubscribe here</a>.</p>" + "<p>Best regards,<br>" + companyName + "</p>" + "</div>";
	}

	@Async
	public static String extractNameFromEmail(String emailAddress) {
		try {
			InternetAddress internetAddress = new InternetAddress(emailAddress);
			String personal = internetAddress.getPersonal();
			if (personal != null && !personal.isEmpty()) {
				return personal;
			} else {
				// If personal name is not available, return the username part before '@'
				String username = emailAddress.substring(0, emailAddress.indexOf('@'));
				// Convert the first letter to uppercase and the rest to lowercase
				return username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
			}
		} catch (Exception e) {
			logger.error("extractNameFromEmail::", e);
		}
		return "";
	}
	@Async
public void sendEmailForRegister(User user) throws UnsupportedEncodingException {
	Properties props = new Properties();
	props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
	props.put("mail.smtp.port", "587"); // TLS Port
	props.put("mail.smtp.auth", "true"); // enable authentication
	props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

	// create Authenticator object to pass in Session.getInstance argument
	Authenticator auth = new Authenticator() {
		// override the getPasswordAuthentication method
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(from, password);
		}
	};
	Session session = Session.getInstance(props, auth);
	
		String subject = "{clientName} Verify your e-mail to complete your credmarg sign-up";
		subject = subject.replace("{clientName}", user.getName());

		String htmlContent = "<html><body><div style=\"max-width:550px ; padding : 18px ; border : 1px solid #dadada ; -webkit-border-radius : 10px; -moz-border-radius :10px ; border-radius : 10px; font-family: Arial, Helvetica, sans-serif; font-size : 15px; color:#495057; \"><p style=\"font-size : 17px ; font-weight : bold;\"> Dear {clientName},</p><p style=\"\">Thank you for registering with our platform. Please click on the link below to activate your account.Activation Link will be expired within 15 minutes.</p><table style=\"cursor: pointer;\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\"><tbody><tr><td valign=\"middle\" align=\"center\" style=\"border:none; background-color: #dc3545; -webkit-border-radius: 10px; -moz-border-radius: 10px; border-radius: 10px; padding-top: 15px; padding-bottom: 15px; padding-right: 20px; padding-left: 20px;\"><a href=\"{activationLink}\" style=\"color: #ffffff; text-decoration: none; font-family: Helvetica, Arial, sans-serif; font-size: 18px; line-height: 135%; font-weight: normal; border:none; display: block;\" target=\"_blank;\">Activate Account</a></td></tr></tbody></table><p>If you did not register with us, please disregard this email.</p><p>Need any assistance? Please contact our support team at <a href=\"mailto:officialbloggerhub@gmail.com\" style=\"color: #3b5de7; cursor: pointer\">Credmarg@gmail.com</a></p><p style=\"line-height: 30px; color: #303030 display: inline-block;\"><div><div><span>Regards,</span><br/><span style=\"font-size: 18px; color: #303030\">Credmarg Team</span></div><div><img style=\"height: 70px; margin-top: 10px;\" src=\"{companyLogo}\" height=\"70\"></div></div></p></div></body></html>";

		String link = localhost + "resetpassword?code=" + user.getVerificationCode();
		htmlContent = htmlContent.replace("{activationLink}", link);
		htmlContent = htmlContent.replace("{clientName}", user.getName());
		htmlContent = htmlContent.replace("{expirytime}", "15");
		htmlContent = htmlContent.replace("{companyLogo}", "cid:part1.01030607.06070005@gmail.com");
		 EmailUtil.sendAttachmentEmail(session, user.getEmail(), subject, htmlContent);
	

	}
	@Async
	public void sendEmailForVendors(String email, String upi, Vendor user) throws UnsupportedEncodingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
			String subject = "Sending payments to vendor {clientName} at {upi}";
			subject = subject.replace("{clientName}", user.getName());
			subject = subject.replace("{upi}",upi);
			String htmlContent = "<html><body><div style=\"max-width:550px; padding: 18px; border: 1px solid #dadada; -webkit-border-radius: 10px; -moz-border-radius: 10px; border-radius: 10px; font-family: Arial, Helvetica, sans-serif; font-size: 15px; color: #495057;\"><p style=\"font-size: 17px; font-weight: bold;\">Hello Vendor {clientName},</p><p>Your UPI is: {upi}</p><p>Need any assistance? Please contact our support team at <a href=\"mailto:officialcredmarg@gmail.com\" style=\"color: #3b5de7; cursor: pointer;\">Credmarg Team</a></p><p style=\"line-height: 30px; color: #303030;\">Regards,<br/><span style=\"font-size: 18px; color: #303030;\">Credmarg Team</span></p><div><img style=\"height: 70px; margin-top: 10px;\" src=\"{companyLogo}\" height=\"70\"></div></div></body></html>";

			htmlContent = htmlContent.replace("{upi}", upi);
			htmlContent = htmlContent.replace("{clientName}", user.getName());
		saveDataToEmail( email,  upi,  user,subject,user.getUser().getUserId());
			 EmailUtil.sendAttachmentEmail(session, user.getEmailAddress(), subject, htmlContent);
		
	}

	private void saveDataToEmail(String email, String upi, Vendor user, String subject,Long userId) {
		EmailData emailData = new EmailData();
		emailData.setEmail(email);
		emailData.setName(user.getName());
		emailData.setSubject(subject);
		emailData.setUpi(upi);
		emailData.setUserId(userId);
		emailDataRepo.save(emailData);
		
	}
	

	





}
