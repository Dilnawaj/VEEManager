package com.employee.Employee.Managment.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class JavaHelper {
	private static final String DATE_FORMAT= "EEE MMM dd HH:mm:ss zzz yyyy";
		public static Date getCurrentDate() {
		return new Date();
	}

		public static Date dateStringToDate(String dateStr) throws ParseException {
			System.out.println("your date is"+dateStr);
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			return format.parse(dateStr);
		}

			public static Integer getDiffInMinutes(Date startDate, Date endDate) {
		try {
			long diff = endDate.getTime() - startDate.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			return (int) diffMinutes;
		} catch (Exception e) {
			return 0;
		}
	}

			public static String getBcrypt(String text) {
				return BCrypt.hashpw(text, BCrypt.gensalt(10));
			}

			public static boolean checkPassword(String plainPassword, String encryptedPassword) {
				return BCrypt.checkpw(plainPassword, encryptedPassword);
			}

			public static boolean checkPassword(String plainPassword, String encryptedPassword, boolean validatePassword) {
				return validatePassword ? checkPassword(plainPassword, encryptedPassword) : true;
			}
}
