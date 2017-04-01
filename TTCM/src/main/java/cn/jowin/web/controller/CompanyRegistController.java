package cn.jowin.web.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jowin.entity.Company;
import cn.jowin.entity.Room;
import cn.jowin.entity.User;
import cn.jowin.service.CompanyService;
import cn.jowin.service.RoomService;
import cn.jowin.service.UserService;
import cn.jowin.web.JsonResult;
@Controller
@RequestMapping("/companyRegist")
public class CompanyRegistController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2716315707578841011L;
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoomService roomService;
	
	
	Logger logger  =  Logger.getLogger(CompanyRegistController.class);
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@RequestMapping("/company.do")
	@ResponseBody
	public JsonResult<Company> regist(String companyName,
			String companyAddress, 
			String companyPhone,
			String companyEmail,
			String companyFax,
			String companyWebsite){
		try {
			Company company = companyService.registCompany(companyName, companyAddress, companyPhone, companyEmail,
					companyFax, companyWebsite);
			return new JsonResult<Company>(company);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Company>(e.getMessage());
		}
	}
	
	/**
	 * 存放管理员信息
	 */
//	private User admin;
	
	@RequestMapping("/admin.do")
	@ResponseBody
	public JsonResult<User> adminRegist(
			String name,String email,
			String modile,String phone,
			String password,String companyId,String job){
		try {
//			companyId = cp.getCompanyId();
			User user = userService.adminRegistUser(name, email, modile, phone, password, companyId,job);
//			admin = user;
			return new JsonResult<User>(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<User>(e.getMessage());
		}
	}
	
	
	@RequestMapping("/roomFound.do")
	@ResponseBody
	public JsonResult<Room> roomFound(
			String companyId,String userId,String roomName,String roomPhone,Integer maxUser,
			String roomPassword,String roomAddress){
		try {
			//roomName=new String(roomName.getBytes("iso-8859-1"),"utf-8");
			//System.out.println("---roomName:");
			Room room = roomService.regist(companyId, userId, roomName, roomPhone, maxUser, roomPassword, roomAddress);
			return new JsonResult<Room>(room);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Room>(e.getMessage());
		}
	}
	
	@RequestMapping("/adminCancel.do")
	@ResponseBody
	public JsonResult  deleteCompantByIdRegist(String companyId){
		try {
//			companyId = cp.getCompanyId();
			companyService.deleteCompanyRegistById(companyId);
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage());
		}
	}
}
