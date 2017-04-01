package cn.jowin.web.controller;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jowin.entity.User;
import cn.jowin.service.UserService;
import cn.jowin.util.Md5;
import cn.jowin.web.JsonResult;

@Controller
@RequestMapping("/login")
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7261606688764068331L;
	
	@Autowired
	private UserService userService;
	
	Logger logger  =  Logger.getLogger(LoginController.class);
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@RequestMapping("/check.do")
	@ResponseBody
	public JsonResult<User> login(String modile,String password,HttpServletRequest request,
			HttpServletResponse response){
		try {
			User user = userService.loginCheck(modile, password);
			System.out.println("-----");
			// 保存cookie token
			// 利用UserAgent 创建Token
			// User-Agent
			String userAgent = request.getHeader("User-Agent");
			long now = System.currentTimeMillis();
			String token = Md5.saltMd5(userAgent + now);
			Cookie cookie = new Cookie("token", now + "|" + token);
			cookie.setPath("/");
			response.addCookie(cookie);
			return new JsonResult<User>(user);
		} catch (Exception e) {
			return new JsonResult<User>(e.getMessage());
		}
	}
}
