package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.User;

public interface UserService {
	
	User findUserById(String userId);
	
	
	
	//��ͨ�û�����
	User registUser(
			String companyName,String name, 
			String email, String modile, String phone, String password,String job);
	//����Ա����
	User adminRegistUser(
			String name,String email,
			String modile,String phone,
			String password,String companyId,String job);
	User loginCheck(String modile ,String password);
	//��ѯ��˾���û�����
	List<User> userApply( String companyId);
	//ͬ������
	User agreeUser(String userId);
	//�ܾ�����
	StringBuffer refuseUser(String userId);
	//��ѯ��˾�����û�
	List<User> findAll();
}
