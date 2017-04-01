package cn.jowin.dao;

import java.util.List;

import cn.jowin.entity.User;

public interface UserDao {
	void saveUser (User user);
	User findUserById (String id);
	User findUserByModile (String name);
	List<User> findUserByCompanyId(String companyId);
	List<User> findAppUserByCompanyId(String companyId);
	void setIsAdminstrator (User user);
	void refuseUserDelete(User user);
	List<User> findAll();
}
