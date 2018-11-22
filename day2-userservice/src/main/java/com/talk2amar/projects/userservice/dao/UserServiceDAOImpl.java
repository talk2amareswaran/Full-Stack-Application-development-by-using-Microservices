package com.talk2amar.projects.userservice.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.talk2amar.projects.userservice.model.UserModel;

@Repository
public class UserServiceDAOImpl implements UserServiceDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String INITIAL_RECORD = "INITIAL_RECORD";
	private static final String EMAIL_ALREADY_EXISTS_QUERY = "select count(id) from users where email=?";
	private static final String USER_INSERT_QUERY = "insert into users (name, email, password, createddate, createdby, lastupdateddate, lastupdatedby) values (?,?,?,?,?,?,?)";
	private static final String GET_USERS_SQL_QUERY = "select id, name, email, createddate, createdby, lastupdateddate, lastupdatedby from users";

	private static final String CREATED_BY = "createdby";
	private static final String CREATED_DATE = "createddate";
	private static final String LAST_UPDATED_BY = "lastupdatedby";
	private static final String LAST_UPDATED_DATE = "lastupdateddate";
	private static final String USER_ID = "id";
	private static final String NAME = "name";
	private static final String EMAIL = "email";

	private static final String SELECT_USER_QUERY = "select id, name, email, createddate, createdby, lastupdateddate, lastupdatedby from users where id=?";

	private static final String USER_ID_EXISTS_QUERY = "select count(id) from users where id=?";

	private static final String UPDATE_USER_SQL_QUERY = "update users set name=?, lastupdateddate=?, lastupdatedby=? where id=?";

	private static final String DELETE_SQL_QUERY = "delete from users where id=?";
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	@Override
	public boolean isEmailAlreadyExists(String email) {
		return jdbcTemplate.queryForObject(EMAIL_ALREADY_EXISTS_QUERY, new Object[] { email }, Integer.class) > 0;
	}

	@Override
	public void insertUser(UserModel userModel) {
		jdbcTemplate.update(USER_INSERT_QUERY,
				new Object[] { userModel.getName(), userModel.getEmail(), passwordEncoder.encode(userModel.getPassword()),
						System.currentTimeMillis() / 1000, INITIAL_RECORD, System.currentTimeMillis() / 1000,
						INITIAL_RECORD });

	}

	@Override
	public List<UserModel> getUsers() {
		Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_USERS_SQL_QUERY);
		List<UserModel> userModelList = new ArrayList<>();
		rows.stream().map((row) -> {
			UserModel userModel = new UserModel();
			userModel.setCreatedby((String) row.get(CREATED_BY));
			userModel.setCreateddate(String.valueOf(row.get(CREATED_DATE)));
			userModel.setId((Integer) row.get(USER_ID));
			userModel.setLastupdatedby((String) row.get(LAST_UPDATED_BY));
			userModel.setLastupdateddate(String.valueOf(row.get(LAST_UPDATED_DATE)));
			userModel.setName((String) row.get(NAME));
			userModel.setEmail((String) row.get(EMAIL));
			return userModel;
		}).forEach((ss) -> {
			userModelList.add(ss);
		});
		return userModelList;

	}

	@Override
	public UserModel getUser(String id) {
		UserModel userModel = new UserModel();
		try {
			jdbcTemplate.queryForObject(SELECT_USER_QUERY, new Object[] { id }, (ResultSet rs, int rowNum) -> {
				userModel.setCreatedby(rs.getString(CREATED_BY));
				userModel.setCreateddate(rs.getString(CREATED_DATE));
				userModel.setId(rs.getInt(USER_ID));
				userModel.setLastupdatedby(rs.getString(LAST_UPDATED_BY));
				userModel.setLastupdateddate(rs.getString(LAST_UPDATED_DATE));
				userModel.setName((String) rs.getString(NAME));
				userModel.setEmail((String) rs.getString(EMAIL));
				return userModel;
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userModel;
	}

	@Override
	public boolean isUserExistsById(String id) {
		return jdbcTemplate.queryForObject(USER_ID_EXISTS_QUERY, new Object[] {id}, Integer.class)>0;
	}

	@Override
	public void updateUser(UserModel user, String id) {
		jdbcTemplate.update(UPDATE_USER_SQL_QUERY, new Object[] {user.getName(), System.currentTimeMillis()/1000, INITIAL_RECORD, id});
		
	}

	@Override
	public void deleteUser(String id) {
		jdbcTemplate.update(DELETE_SQL_QUERY, new Object[] {id});
	}

}
