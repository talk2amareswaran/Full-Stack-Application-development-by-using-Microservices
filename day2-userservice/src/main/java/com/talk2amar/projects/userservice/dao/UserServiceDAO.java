package com.talk2amar.projects.userservice.dao;

import java.util.List;

import com.talk2amar.projects.userservice.model.UserModel;

public interface UserServiceDAO {

	public boolean isEmailAlreadyExists(String email);

	public void insertUser(UserModel userModel);

	public List<UserModel> getUsers();

	public UserModel getUser(String id);

	public boolean isUserExistsById(String id);

	public void updateUser(UserModel user, String id);

	public void deleteUser(String id);
}
