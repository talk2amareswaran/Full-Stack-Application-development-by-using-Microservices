package com.talk2amar.projects.userservice.utils;

import org.springframework.stereotype.Component;

import com.talk2amar.projects.userservice.model.UserModel;

@Component
public class Utilities {

	public void updateUserModel(UserModel requestModel, UserModel oldModel) {
		if(requestModel.getName()!=null)
			oldModel.setName(requestModel.getName());
	}
}
