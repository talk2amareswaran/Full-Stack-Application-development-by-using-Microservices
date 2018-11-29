package com.talk2amar.projects.userservice.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.talk2amar.projects.userservice.model.AppResponse;
import com.talk2amar.projects.userservice.model.UserModel;
import com.talk2amar.projects.userservice.service.UserAppService;
import com.talk2amar.projects.userservice.utils.AppConstants;
import com.talk2amar.projects.userservice.utils.Utilities;
import com.talk2amar.projects.userservice.validation.UserValidation;

@RestController
public class UserRestController {
	
	@Autowired
	UserValidation userValidation;
	@Autowired
	UserAppService userAppService;
	@Autowired
	Utilities utilities;
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@RequestMapping(value=AppConstants.USER_SERVICE_API, method=RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody UserModel userModel) {
		
		String errorMessage = userValidation.isValidUser(userModel, false);

		if(errorMessage!=null) {
			logger.warn("create user error message:"+errorMessage);
			return new ResponseEntity<>(new AppResponse(AppConstants.BAD_REQUEST,errorMessage), HttpStatus.BAD_REQUEST);
		}
		
		if(userAppService.isEmailAlreadExists(userModel.getEmail())) {
			logger.warn("Email already exists");
			return new ResponseEntity<>(new AppResponse(AppConstants.CONFLICT, AppConstants.EMAIL_ALREADY_EXISTS_MSG), HttpStatus.CONFLICT);
		}
		
		userAppService.insertUser(userModel);
		logger.info("User created successfully");
		
		return new ResponseEntity<>(new AppResponse(AppConstants.CREATED, AppConstants.USER_CREATE_SUCCESS_MSG), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value=AppConstants.USER_SERVICE_API, method=RequestMethod.GET)
	public ResponseEntity<Object> getAllUsers() {
		List<UserModel> usersList = userAppService.getUsers();
		if(usersList==null || usersList.isEmpty()) {
			logger.warn("users not found");
			return new ResponseEntity<>(new AppResponse(AppConstants.NOT_FOUND,AppConstants.NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(usersList, HttpStatus.OK);
	}
	
	@RequestMapping(value=AppConstants.USER_SERVICE_API_PATH_PARAM, method=RequestMethod.GET)
	public ResponseEntity<Object> getUser(@PathVariable("id") String id) {
		UserModel user = userAppService.getUser(id);
		
		if(user.getId()<=0) {
			logger.warn("user id not found");
			return new ResponseEntity<>(new AppResponse(AppConstants.NOT_FOUND,AppConstants.NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value=AppConstants.USER_SERVICE_API_PATH_PARAM, method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable("id") String id) {
		
		if(!userAppService.isUserExistsById(id)) {
			logger.warn("user id not found while trying to delete");
			return new ResponseEntity<>(new AppResponse(AppConstants.NOT_FOUND,AppConstants.NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		}
	
		userAppService.deleteUser(id);
		logger.info("user deleted successfully");
		
		return new ResponseEntity<>(new AppResponse(AppConstants.OK,AppConstants.DELETE_SUCCESS_MSG), HttpStatus.OK);
			
	}
	
	
	@RequestMapping(value=AppConstants.USER_SERVICE_API_PATH_PARAM, method=RequestMethod.PUT)
	public ResponseEntity<Object> updateUser(@RequestBody UserModel requestUser,@PathVariable("id") String id) {
		
		UserModel user = userAppService.getUser(id);
		if(user.getId()<=0) {
			logger.warn("user id not found while updating");
			return new ResponseEntity<>(new AppResponse(AppConstants.NOT_FOUND,AppConstants.NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		}
		
		utilities.updateUserModel(requestUser, user);
		
		
		String errorMessage = userValidation.isValidUser(user, true);
		
		if(errorMessage!=null) {
			logger.warn("error message while updating the user:"+errorMessage);
			return new ResponseEntity<>(new AppResponse(AppConstants.BAD_REQUEST,errorMessage), HttpStatus.BAD_REQUEST);
		}
		
		userAppService.updateUser(user, id);
		logger.info("User updated successfully");
		
		return new ResponseEntity<>(new AppResponse(AppConstants.OK,AppConstants.UPDATE_SUCCESS_MSG), HttpStatus.OK);
			
	}	
}