package cn.jowin.service.imp;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jowin.dao.CompanyDao;
import cn.jowin.dao.UserDao;
import cn.jowin.entity.Company;
import cn.jowin.entity.User;
import cn.jowin.service.UserService;
import cn.jowin.service.exception.NameOrPasswordException;
import cn.jowin.service.exception.ServiceException;
import cn.jowin.util.Md5;

@Service("userService")
public class UserServiceImp implements UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private CompanyDao companyDao;
	
	@Transactional
	public User registUser(String companyName,String name, String email, String modile, String phone, String password,String job) {
		if(companyName==null||companyName.trim().isEmpty()){
			throw new ServiceException("��˾���Ʋ���Ϊ��");
		}
		if(name==null||name.trim().isEmpty()){
			throw new ServiceException("��������Ϊ��");
		}
		if(email==null||email.trim().isEmpty()){
			throw new ServiceException("���䲻��Ϊ��");
		}
		if(modile==null||modile.trim().isEmpty()){
			throw new ServiceException("�ֻ�����Ϊ��");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("���벻��Ϊ��");
		}
		Company company = companyDao.findCompanyByName(companyName.trim());
		if(company ==null){
			throw new ServiceException("��˾�����ڻ�˾������");
		}
		
		String companyId = company.getCompanyId();
		
		String uuid = UUID.randomUUID().toString();
		String pwd = Md5.saltMd5(password);
		User user1 = userDao.findUserByModile(modile);
		if(user1 != null){
			throw new ServiceException("���ֻ����Ѵ���");
		}
		User user = new User(uuid,name,email,modile,
				phone,pwd,null,companyId,null,null,null,job,companyName);
		userDao.saveUser(user);
		
		
		
		return user;
	}
	
	@Transactional
	public User adminRegistUser(
			String name, String email, String modile, String phone, 
			String password,String companyId,String job) {
		
		if(name==null || name.trim().isEmpty()){
			throw new ServiceException("��������Ϊ��");
		}
		if(email==null||email.trim().isEmpty()){
			throw new ServiceException("���䲻��Ϊ��");
		}
		if(modile==null||modile.trim().isEmpty()){
			throw new ServiceException("�ֻ�����Ϊ��");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("���벻��Ϊ��");
		}
		if(companyId==null||companyId.trim().isEmpty()){
			throw new ServiceException("��˾ID����Ϊ��");
		}
		String uuid = UUID.randomUUID().toString();
		String pwd = Md5.saltMd5(password);
		String companyName = companyDao.findCompanyById(companyId).getCompanyName();
		User user = new User(
				uuid, name, email, modile, phone, pwd,"1",companyId,null,null,null,job,companyName);
		userDao.saveUser(user);
		
		
		
		return user;
	}
	
	@Transactional
	public User loginCheck(String modile, String password) {
		if(modile==null||modile.trim().isEmpty()){
			throw new ServiceException("�ֻ��Ų���Ϊ��");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("���벻��Ϊ��");
		}
		User user = userDao.findUserByModile(modile);
		password = Md5.saltMd5(password);
		if(user == null || !(user.getPassword().equals(password))){
			throw new NameOrPasswordException("�˺Ż��������");
		}
		if(user.getIsAdminstrator()==null || 
				user.getIsAdminstrator().trim().isEmpty()){
			throw new ServiceException("�ȴ�����Աͬ��");
		}
		return user;
	}
	
	@Transactional
	public List<User> userApply(String companyId) {
		
		if(companyId==null||companyId.trim().isEmpty()){
			throw new ServiceException("��˾ID����Ϊ��");
		}
		List<User> userList = userDao.findAppUserByCompanyId(companyId);
		//���Ϊ��˵����ǰ��˾û���û�
		return userList;
	}
	
	@Transactional
	public User agreeUser(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("�û�Id����Ϊ�ղ���Ϊ��");
		}
		User user= userDao.findUserById(userId);
		if(user==null){
			throw new ServiceException("�û�������");
		}
		
		user.setIsAdminstrator("2");
		userDao.setIsAdminstrator(user);
		User userCheck= userDao.findUserById(userId);
		if(!userCheck.equals(user)){
			throw new ServiceException("ͬ��ʧ�ܣ�������");
		}
		return user;
	}
	
	@Transactional
	public StringBuffer  refuseUser(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("�û�Id����Ϊ�ղ���Ϊ��");
		}
		
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new ServiceException("�û�������");
		}
		userDao.refuseUserDelete(user);
		user = userDao.findUserById(userId);
		if(user!=null){
			throw new ServiceException("ɾ���û�ʧ��");
		}
		StringBuffer s= new StringBuffer("�Ѿܾ�");
		return s;
	}

	@Transactional
	public List<User> findAll() {
		List<User> userList= userDao.findAll();
		return userList;
	}
	
	@Transactional
	public User findUserById(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("�û�Id����Ϊ�ղ���Ϊ��");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new ServiceException("��ѯ�û�ʧ��");
		}
		
		
		return user;
	}

}
