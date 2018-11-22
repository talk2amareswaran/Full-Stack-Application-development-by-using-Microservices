package com.talk2amar.projects.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talk2amar.projects.userservice.dao.UserServiceDAO;
import com.talk2amar.projects.userservice.model.UserModel;

@Service
@Transactional
public class UserAppServiceImpl implements UserAppService {

	@Autowired
	UserServiceDAO userServiceDAO;
	
	@Override
	public boolean isEmailAlreadExists(String email) {
		return userServiceDAO.isEmailAlreadyExists(email);
	}

	@Override
	public void insertUser(UserModel userModel) {
		userServiceDAO.insertUser(userModel);
		
	}

	@Override
	public List<UserModel> getUsers() {
		return userServiceDAO.getUsers();
	}

	@Override
	public UserModel getUser(String id) {
		return userServiceDAO.getUser(id);
	}

	@Override
	public boolean isUserExistsById(String id) {
		return userServiceDAO.isUserExistsById(id);
	}

	@Override
	public void updateUser(UserModel user, String id) {
		userServiceDAO.updateUser(user, id);
		
	}

	@Override
	public void deleteUser(String id) {
		userServiceDAO.deleteUser(id);
		
	}

}
