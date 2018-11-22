package com.talk2amar.projects.userservice.service;

import java.util.List;

import com.talk2amar.projects.userservice.model.UserModel;

public interface UserAppService {

	public boolean isEmailAlreadExists(String email);

	public void insertUser(UserModel userModel);

	public List<UserModel> getUsers();

	public UserModel getUser(String id);

	public boolean isUserExistsById(String id);

	public void updateUser(UserModel user,String id);

	public void deleteUser(String id);
}
