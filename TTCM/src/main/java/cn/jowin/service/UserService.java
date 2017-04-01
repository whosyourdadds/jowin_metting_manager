package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.User;

public interface UserService {
	
	User findUserById(String userId);
	
	
	
	//普通用户创建
	User registUser(
			String companyName,String name, 
			String email, String modile, String phone, String password,String job);
	//管理员创建
	User adminRegistUser(
			String name,String email,
			String modile,String phone,
			String password,String companyId,String job);
	User loginCheck(String modile ,String password);
	//查询公司的用户申请
	List<User> userApply( String companyId);
	//同意申请
	User agreeUser(String userId);
	//拒绝申请
	StringBuffer refuseUser(String userId);
	//查询公司所有用户
	List<User> findAll();
}
