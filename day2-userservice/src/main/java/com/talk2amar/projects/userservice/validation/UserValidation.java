package com.talk2amar.projects.userservice.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.talk2amar.projects.userservice.model.UserModel;

@Component
public class UserValidation {

	private static final String EMPTY_NAME = "Please provide a name";
	private static final String EMPTY_EMAIL = "Please provide a email";
	private static final int DEFAULT_LENGTH = 50;
	private static final int MIN_LENGTH = 5;
	private static final int PASSWORD_MAX_LENGTH = 20;
	private static final String NAME_INVALID_LENGTH = "Name should not be more than "+DEFAULT_LENGTH+" characters";
	private static final String EMAIL_INVALID_LENGTH = "Email should not be more than "+DEFAULT_LENGTH+" characters";
	private static final String EMPTY_PASSWORD = "Please provide a Password";
	private static final String PASSWORD_INVALID_LENGTH = "Password should not be more than "+PASSWORD_MAX_LENGTH+" characters and should not be less than "+MIN_LENGTH+" characters";
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final String INVALID_EMAIL = "Invalid Email";

	
	public String isValidUser(UserModel userModel, boolean isUpdateRequest) {
	
		int name_length = 0;
		int email_length = 0;
		int password_length = 0;
		
		
		if(userModel.getName()!=null) {
			userModel.setName(userModel.getName().trim());
			name_length = userModel.getName().length();
		}
		
		if(userModel.getName()==null || name_length<=0) {
			return EMPTY_NAME;
		} else if(name_length>50){
			return NAME_INVALID_LENGTH;
		}
		
		
		if(!isUpdateRequest) {
			if(userModel.getEmail()!=null) {
				userModel.setEmail(userModel.getEmail().trim());
				email_length = userModel.getEmail().length();
			}
			
			if(userModel.getPassword()!=null)  {
				userModel.setPassword(userModel.getPassword().trim());
				password_length = userModel.getPassword().length();
			}
		
			if(userModel.getEmail()==null || email_length<=0) {
				return EMPTY_EMAIL;
			} else if(email_length>50) {
				return EMAIL_INVALID_LENGTH;
			} else if (!validate(userModel.getEmail())) {
				return INVALID_EMAIL;
			}
			
			if(userModel.getPassword()==null || password_length<=0) {
				return EMPTY_PASSWORD;
			} else if(password_length<5 || password_length>20) {
				return PASSWORD_INVALID_LENGTH;
			}
		}
		return null;
	}
	
	private boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}
