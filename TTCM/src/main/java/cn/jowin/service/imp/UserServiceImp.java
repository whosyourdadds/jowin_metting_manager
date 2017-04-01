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
			throw new ServiceException("公司名称不能为空");
		}
		if(name==null||name.trim().isEmpty()){
			throw new ServiceException("姓名不能为空");
		}
		if(email==null||email.trim().isEmpty()){
			throw new ServiceException("邮箱不能为空");
		}
		if(modile==null||modile.trim().isEmpty()){
			throw new ServiceException("手机不能为空");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		Company company = companyDao.findCompanyByName(companyName.trim());
		if(company ==null){
			throw new ServiceException("公司不存在或公司名错误");
		}
		
		String companyId = company.getCompanyId();
		
		String uuid = UUID.randomUUID().toString();
		String pwd = Md5.saltMd5(password);
		User user1 = userDao.findUserByModile(modile);
		if(user1 != null){
			throw new ServiceException("该手机号已存在");
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
			throw new ServiceException("姓名不能为空");
		}
		if(email==null||email.trim().isEmpty()){
			throw new ServiceException("邮箱不能为空");
		}
		if(modile==null||modile.trim().isEmpty()){
			throw new ServiceException("手机不能为空");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		if(companyId==null||companyId.trim().isEmpty()){
			throw new ServiceException("公司ID不能为空");
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
			throw new ServiceException("手机号不能为空");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		User user = userDao.findUserByModile(modile);
		password = Md5.saltMd5(password);
		if(user == null || !(user.getPassword().equals(password))){
			throw new NameOrPasswordException("账号或密码错误");
		}
		if(user.getIsAdminstrator()==null || 
				user.getIsAdminstrator().trim().isEmpty()){
			throw new ServiceException("等待管理员同意");
		}
		return user;
	}
	
	@Transactional
	public List<User> userApply(String companyId) {
		
		if(companyId==null||companyId.trim().isEmpty()){
			throw new ServiceException("公司ID不能为空");
		}
		List<User> userList = userDao.findAppUserByCompanyId(companyId);
		//如果为空说明当前公司没有用户
		return userList;
	}
	
	@Transactional
	public User agreeUser(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户Id不能为空不能为空");
		}
		User user= userDao.findUserById(userId);
		if(user==null){
			throw new ServiceException("用户不存在");
		}
		
		user.setIsAdminstrator("2");
		userDao.setIsAdminstrator(user);
		User userCheck= userDao.findUserById(userId);
		if(!userCheck.equals(user)){
			throw new ServiceException("同意失败，请重试");
		}
		return user;
	}
	
	@Transactional
	public StringBuffer  refuseUser(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户Id不能为空不能为空");
		}
		
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new ServiceException("用户不存在");
		}
		userDao.refuseUserDelete(user);
		user = userDao.findUserById(userId);
		if(user!=null){
			throw new ServiceException("删除用户失败");
		}
		StringBuffer s= new StringBuffer("已拒绝");
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
			throw new ServiceException("用户Id不能为空不能为空");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new ServiceException("查询用户失败");
		}
		
		
		return user;
	}

}
