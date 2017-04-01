package cn.jowin.web.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jowin.entity.User;
import cn.jowin.service.UserService;
import cn.jowin.web.JsonResult;

@Controller
@RequestMapping("/user")
public class UserRegistController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970438642466431944L;
	
	@Autowired
	private UserService userService;
	
	
	Logger logger  =  Logger.getLogger(UserRegistController.class);
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult<User> login(String companyName,String name,String email,String modile,String phone,String password,String job){
		try {
//			System.out.println(modile);
			User user = userService.registUser(companyName, name, email, modile, phone, password,job);
			return new JsonResult<User>(user);
		} catch (Exception e) {
			return new JsonResult<User>(e.getMessage());
		}
	}
}
