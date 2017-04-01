package cn.jowin.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jowin.entity.Meeting;
import cn.jowin.entity.MeetingRelation;
import cn.jowin.entity.Room;
import cn.jowin.entity.User;
//import cn.jowin.service.CompanyService;
import cn.jowin.service.MeetingRelationService;
import cn.jowin.service.MeetingService;
import cn.jowin.service.RoomRelationService;
import cn.jowin.service.RoomService;
import cn.jowin.service.UserService;
import cn.jowin.util.SendMail;
import cn.jowin.web.JsonResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/menus")
public class MenusController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2931202984622518473L;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoomService roomService;
	
//	@Autowired
//	private CompanyService companyService;
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private MeetingRelationService meetingRelationService;
	
	@Autowired
	private RoomRelationService roomRelationService;
	
	Logger logger  =  Logger.getLogger(MenusController.class);
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	@RequestMapping("/userApp.do")
	@ResponseBody
	public JsonResult<List<User>> selectApplication(String companyId){
		try {
			List<User> list = userService.userApply(companyId);
			return new JsonResult<List<User>>(list);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<User>>(e.getMessage());
		}
	}
	
	@RequestMapping("/agree.do")
	@ResponseBody
	public JsonResult<User> agreeUser(String userId){
		try {
			User user = userService.agreeUser(userId);
			return new JsonResult<User>(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<User>(e.getMessage());
		}
	}
	
	@RequestMapping("/refuse.do")
	@ResponseBody
	public JsonResult<StringBuffer> refuseUser(String userId){
		try {
			StringBuffer s=userService.refuseUser(userId);
			return new JsonResult<StringBuffer>(s);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<StringBuffer>(e.getMessage());
		}
	}
	
	@RequestMapping("/findRoom.do")
	@ResponseBody
	public JsonResult<List<Room>> findRoom(String companyId){
		try {
			List<Room> roomList = roomService.findRoomByCompanyId(companyId);
			return new JsonResult<List<Room>>(roomList);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Room>>(e.getMessage());
		}
	}
	
	
//	该方法本来用于增加房间，
//	但是后来发现在创建CompanyRegistController中有创建房间方法
//	@RequestMapping("/addRoom.do")
//	@ResponseBody
//	public JsonResult<Room> addRoom(String userId ,String companyId){
//		try {
//			Room room = null;
//			return new JsonResult<Room>(room);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Room>(e.getMessage());
//		}
//	}
	
	@RequestMapping("/deleteRoom.do")
	@ResponseBody
	public JsonResult<Room> deleteRoomById(String roomId){
		try {
			Room room = roomService.deleteRoomById(roomId);
			return new JsonResult<Room>(room);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Room>(e.getMessage());
		}
	}
	
	@RequestMapping("/updateRoom.do")
	@ResponseBody
	public JsonResult<Room> updateRoomById (String roomId,String roomName,
			String roomPhone,Integer maxUser,String roomPassword,String roomAddress){
		try {
			Room room = roomService.updateRoomById(roomId, roomName, roomPhone,maxUser , roomPassword, roomAddress);
			return new JsonResult<Room>(room);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Room>(e.getMessage());
		}
	}
	
	//查询数据库中所有用户，作为自动完成功能的数据
	@RequestMapping("/userData.do")
	@ResponseBody
	public JsonResult<List<User>> findAll(){
		try {
			List<User> userList=userService.findAll();
			return new JsonResult<List<User>>(userList);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<User>>(e.getMessage());
		}
	}
	
	@RequestMapping("/findMeeting.do")
	@ResponseBody
	public JsonResult<List<Object>> findMeetingByUserId(String userId){
		try {
			List<MeetingRelation> mrList = meetingRelationService.findMeetingByUserId(userId);
			List<Meeting> meetingList = new ArrayList<Meeting>();
			List<String> meetingIdList = new ArrayList<String>();
			List<Object> obj = new ArrayList<Object>();
			for(MeetingRelation mr:mrList){
				Meeting meeting = meetingService.findMeetingById(mr.getMeetingId());
				
				if(meeting.getRecord()==1){
					if(System.currentTimeMillis()>meeting.getEndTime().longValue()){
						meetingService.updateRecordMeeting(meeting.getMeetingId(),2);
						continue;
					}
					meetingList.add(meeting);
					meetingIdList.add(meeting.getMeetingId());
				}
			}
			obj.add(meetingList);
			obj.add(meetingIdList);
			return new JsonResult<List<Object>>(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Object>>(e.getMessage());
		}
	}
	
	@RequestMapping("/findInTime.do")
	@ResponseBody
	public JsonResult<List<Room>> findRoomInTime(String companyId,String startDate,String endDate,Integer delayTime,String meetingId){
		try {
//			System.out.println("------"+meetingId);
			List<Room> rooms=roomService.findRoomByCompanyId(companyId);
			List<Room> roomList= checkRoomInTime(rooms,startDate,endDate,delayTime,meetingId);
			
			return new JsonResult<List<Room>>(roomList);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Room>>(e.getMessage());
		}
	}
//	checkRoomInTime
	public List<Room> checkRoomInTime(List<Room> rooms,String startDate,String endDate,Integer delay,String meetingId){
		List<Room> roomList = new ArrayList<Room>();
		for(Room room:rooms){
			//如果为空闲状态说明该房间没有会议预约和占用，则直接显示
			if("空闲".equals(room.getRoomStatus())){
				roomList.add(room);
				
			}else{
				/*
				 * 如果为其他状态，则先要获取该房间ID查询出会议，
				 * 看该时间段是否有会议已经预约，如果没有，则显示，
				 * 如果有，则不显示，
				 * 如果这个时间段都没有房间空闲，则显示该时间段没有房间处于空闲状态
				 */
				try {
					List<String> meetingIdList=roomRelationService.findRoomByRoomId(room.getRoomId());
//					System.out.println(meetingIdList);
					
					if(meetingIdList==null||meetingIdList.isEmpty()){
						roomList.add(room);
						continue;
					}
					
					List<Meeting> meetingList =new  ArrayList<Meeting>();
					for (String mId:meetingIdList) {
						Meeting meeting = meetingService.findMeetingById(mId);
//						if(meeting.getRecord()==3){
//							continue;
//						}
//						System.out.println(meeting);
						meetingList.add(meeting);
					}
					int index = 0;
					for(Meeting meeting:meetingList){
						//已经加上延迟时间
						Long startTime = meeting.getStartTime();
						Long delayTime = meeting.getDelayTime();
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
//						System.out.println("251row---"+startDate);
						Long sDate = sdf1.parse(startDate).getTime();
						Long eDate = sdf1.parse(endDate).getTime();
//						System.out.println("---254"+meetingId);
//						System.out.println(meeting.getMeetingId());
//						System.out.println(meeting.getMeetingId().equals(meetingId));
						if(!meeting.getMeetingId().equals(meetingId)){
							if((sDate.longValue()<startTime.longValue()&&(startTime.longValue()< eDate.longValue()&& eDate.longValue()<=delayTime.longValue()))||
								((delayTime.longValue()>sDate.longValue()&&sDate.longValue()>=startTime.longValue())&&eDate.longValue()>delayTime.longValue())||
								(sDate.longValue()<startTime.longValue()&&eDate.longValue()>delayTime.longValue())||
								(sDate.longValue()>=startTime.longValue()&&eDate.longValue()<=delayTime.longValue())){
								if(meeting.getRecord()==1){
									++index;
									
								}
//								System.out.println("------进入了"+meeting.getRecord());
							}
						}
					}
					if(index==0){
						roomList.add(room);
//						System.out.println("------添加了");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("未知错误请重试");
				}
				
			}
		}
		return roomList;
	}
	
	@RequestMapping("/checkMeeting.do")
	@ResponseBody
	public JsonResult<List<Room>> checkMeetingFound(
			String roomId,String startDate,String endDate,Integer delayTime,String meetingId){
		try {
//			System.out.println(roomId);
//			System.out.println("289-----------");
			Room room =roomService.findRoomById(roomId);
			List<Room> rooms = new ArrayList<Room>();
			rooms.add(room);
//			System.out.println(meetingId);
			rooms=checkRoomInTime(rooms, startDate, endDate,delayTime,meetingId);
			
			return new JsonResult<List<Room>>(rooms);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Room>>(e.getMessage());
		}
	}
	

	@RequestMapping("/addMeeting.do") 
    @ResponseBody  
    public JsonResult<Meeting> addMeeting(String attendeeUserId,String hostUserId,
    		String VIPUserId,String startDate, String endDate,String GMT,
    		String repeat,String subject,String description,Long delayTime,
    		String meetingFile,Integer limit,String roomId) { 
		try {
			String[] aid = attendeeUserId.split(",");
			String[] hid = hostUserId.split(",");
			String[] vid = VIPUserId.split(",");
			
			Room room = roomService.findRoomById(roomId);
			List<Object> list = meetingService.saveMeeting(aid, hid, vid, startDate, endDate,
					GMT, repeat, subject, description, delayTime, meetingFile, limit, room);
			Meeting meeting = (Meeting) list.get(0);
			JsonResult<Meeting> meetingJson = new JsonResult<Meeting>(meeting);
			if(meetingJson.getState()==0){
				List<MeetingRelation> mrList = (List<MeetingRelation>) list.get(1);
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
				Date date = new Date(meeting.getStartTime());
				for (MeetingRelation mr:mrList) {
					
					if (!(mr.getUserEmail()==null||mr.getUserEmail().trim().isEmpty())) {
						new SendMail().sendMailHTML("18702104117@163.com", mr.getUserEmail(),
								("您有一个新的会议:<br>     主题为:" + meeting.getSubject() + "<br>将于" + sdf1.format(date) + "开始"));
						
					}
				}
			}
//			System.out.println(meeting);
			return meetingJson;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Meeting>(e.getMessage());
		}
    }
	@RequestMapping("/updateMeeting.do") 
    @ResponseBody  
    public JsonResult<Meeting> updateMeeting(String attendeeUserId,String hostUserId,
    		String VIPUserId,String startDate, String endDate,String GMT,
    		String repeat,String subject,String description,Long delayTime,
    		String meetingFile,Integer limit,String roomId,String meetingId) {
		try {
			String[] aid = attendeeUserId.split(",");
			String[] hid = hostUserId.split(",");
			String[] vid = VIPUserId.split(",");
			Room room = roomService.findRoomById(roomId);
			Meeting meeting = meetingService.updateMeeting(meetingId,aid, hid, vid, startDate, endDate,
					GMT, repeat, subject, description, delayTime, meetingFile, limit, room);
			
			return new JsonResult<Meeting>(meeting);
		}catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Meeting>(e.getMessage());
		}
		
	}
	
	/**
	 * 已废弃
	 * @param roomId
	 * @param delayTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/findRoomName.do")
	@ResponseBody
	public JsonResult<List<String>> findRoomName(String roomId,Long delayTime,Long startTime,Long endTime){
		try {
			List<String> data = new ArrayList<String>();
			Room room = roomService.findRoomById(roomId);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
			Date date = new Date();
			date.setTime(delayTime);
			String dTime = sdf1.format(date);
			date.setTime(startTime);
			String sTime = sdf1.format(date);
			date.setTime(endTime);
			String eTime = sdf1.format(date);
			data.add(room.getRoomName());
			data.add(sTime);
			data.add(eTime);
			data.add(dTime);
			return new JsonResult<List<String>>(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<String>>(e.getMessage());
		}
	}
	
//彻底删除会议方法还没用到	
//	@RequestMapping("/deleteMeeting.do")
//	@ResponseBody
//	public JsonResult<Meeting> deleteMeeting(String meetingId){
//		try {
//			Meeting meeting = meetingService.deleteMeeting(meetingId);
//			return new JsonResult<Meeting>(meeting);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Meeting>(e.getMessage());
//		}
//	}
	
	@RequestMapping("/updateRecordMeeting.do")
	@ResponseBody
	public JsonResult<Meeting> updateRecordMeeting(String meetingId,Integer record){
		try {
			Meeting meeting = meetingService.updateRecordMeeting(meetingId,record);
			return new JsonResult<Meeting>(meeting);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<Meeting>(e.getMessage());
		}
	}
	
	
	@RequestMapping("/findMessage.do")
	@ResponseBody
	public JsonResult<List<Object>> findRoomMessage(String roomId,String meetingId){
		try {
			List<Object> objList = new ArrayList<Object>();
			Room room=roomService.findRoomById(roomId);
			objList.add(room);
			List<MeetingRelation> mrList = meetingRelationService.findMeetingByMeetingId(meetingId);
			objList.add(mrList);
			return new JsonResult<List<Object>>(objList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Object>>(e.getMessage());
		}
	}
	
	
	@RequestMapping("/MeetingSchedule.do")
	@ResponseBody
	public JsonResult<List<Meeting>> MeetingSchedule (String userId,Long date){
		try {
			//返回的是一个除去被删除的会议
			List<Meeting> meetingList = meetingService.findMeetingByNotEqualRecord();
			List<Meeting> mList = new ArrayList<Meeting>();
			//写一个在date时间范围之内的
			for(Meeting meeting: meetingList){
				//这个其实只要判断一个，但是为了严谨就判断了2个
					
					Long mStart = meeting.getStartTime();
					Long mEnd = meeting.getEndTime();
					Long endDate = date+86400000;
					if(mStart.longValue()>=date.longValue()){
						
						if(mStart.longValue()<endDate.longValue()){
							mList.add(meeting);
						}
						
					}else if(mEnd.longValue()>date.longValue()){
						mList.add(meeting);
					}
						
			}
			
			
			return new JsonResult<List<Meeting>>(mList);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Meeting>>(e.getMessage());
		}
	}
	
	
	/**
	 * 拒绝会议，删除用户和该会议的相关关联
	 * @param meetingId
	 * @param userId
	 * @return 删除的会议关联对象
	 */
	@RequestMapping("/rejectMeeting.do")
	@ResponseBody
	public JsonResult<MeetingRelation> rejectMeeting(String meetingId , String userId){
		try{
			
			MeetingRelation mr = meetingRelationService.deleteMeetingRelation(meetingId, userId);
			
			return new JsonResult<MeetingRelation>(mr);
		}catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<MeetingRelation>(e.getMessage());
		}
	}
	
	//由于修改过数据库字段，获取不到主持人和参会人信息了，所以这样修改
	@RequestMapping("/findAttendee.do")
	@ResponseBody
	public JsonResult<List<List<String>>> findAttendee(String meetingIdList){
		try{
			String[] midList = meetingIdList.split(",");
//			System.out.println(midList.toString());
			List<List<String>> attendeesAndHosts = new ArrayList<List<String>>();
			for(String mid:midList){
//				System.out.println(mid);
				List<String> list = new ArrayList<String>();
				String attendee = null;
				String host = null;
				String vip = null;
				List<MeetingRelation> mrList = meetingRelationService.findMeetingByMeetingId(mid);
				for(MeetingRelation mr :mrList){
					
					if(attendee==null||attendee.trim().isEmpty()){
						if(mr.getUserName()==null||mr.getUserName().trim().isEmpty()){
						}else{
							attendee = mr.getUserName();
						}
					}else{
						if(mr.getUserName()==null||mr.getUserName().trim().isEmpty()){
						}else{
							attendee = attendee+","+mr.getUserName();
						}
					}
					
					if(host==null||host.trim().isEmpty()){
						if(mr.getHostUser()==null||mr.getHostUser().trim().isEmpty()){
						}else{
							host = mr.getUserName();
						}
					}else{
						if(mr.getHostUser()==null||mr.getHostUser().trim().isEmpty()){
						}else{
							host = host+","+mr.getUserName();
						}
					}
					
					if(vip==null||vip.trim().isEmpty()){
						if(mr.getVipUser()==null||mr.getVipUser().trim().isEmpty()){
						}else{
							vip = mr.getUserName();
						}
					}else{
						if(mr.getVipUser()==null||mr.getVipUser().trim().isEmpty()){
						}else{
							vip = vip+","+mr.getUserName();
						}
					}
					
				}
				list.add(attendee);
				list.add(host);
				list.add(vip);
				attendeesAndHosts.add(list);
			}
			
		
//			System.out.println(attendees.toString());
//			System.out.println(hosts.toString());
			return new JsonResult<List<List<String>>>(attendeesAndHosts);
		}catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<List<String>>>(e.getMessage());
		}
	}
	
	
	
//	@RequestMapping("/remind.do")
//	@ResponseBody
//	public JsonResult<Boolean> remind ( String meetingJson,Integer tail){
//		try{
////			String meeting1 = request.getParameter("meeting");
//			JSONObject  jobj = JSONObject.fromObject(meetingJson);
//			Meeting meeting = (Meeting)JSONObject.toBean(jobj,Meeting.class);
//			System.out.println(meeting);
//			System.out.println(tail);
//			List<MeetingRelation> mrList = meetingRelationService.findMeetingByMeetingId(meeting.getMeetingId());
//			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
//			SendMailTask send = new SendMailTask();
//			if(tail==1){
//				
//				for(MeetingRelation mr :mrList){
//					if(!(mr.getUserEmail()==null||mr.getUserEmail().trim().isEmpty())){
//						/*
//						 * 这个boolean值是判断邮件发送成功还是失败，现在有2个方案，
//						 * 1.发送失败不会再次发送邮件
//						 * 2.发送失败会重新发送直到发送成功
//						 * 目前用的是第一个方案
//						 */
//						boolean flag = send.sendMailHTML("18702104117@163.com",
//								mr.getUserEmail(), 
//								"您有一场会议即将开始!<br>会议:"+meeting.getSubject()+
//								"  开始时间:"+sdf1.format(new Date(meeting.getStartTime())));
//						
//					}
//					
//				}
//				//发送完毕后修改meeting的isEmail值为1
//				meeting.setIsEmail("1");
//				meetingService.updateMeeting2(meeting);
//				
//			}else{
//				//这里判断下个时间段有没有会议预约
//				List<Meeting> meetingList = meetingService.findMeetingByRoomIdAndStartTime(meeting.getRoomId(), meeting.getStartTime());
//				String msg = "您的会议即将结束!<br>如果需要延迟";
//				for(Meeting m:meetingList){
//					Long mStart = m.getStartTime();
//					Long mEnd = meeting.getEndTime()+3600000;
//					if(mEnd.longValue()>mStart.longValue()){
//						msg="您的会议即将结束!<br>下个时间段已经有人预约，无法延迟!";
//						break;
//					}
//				}
//				
//				for(MeetingRelation mr :mrList){
//					if(!(mr.getUserEmail()==null||mr.getUserEmail().trim().isEmpty())){
//						/*
//						 * 通知用户会议即将结束，如果后面没有会议预约，则提供延迟按钮，
//						 * 如果后面有会议就提示，下个时间段已经有会议预约，请您尽快结束
//						 */
//						boolean flag = send.sendMailHTML("18702104117@163.com",
//								mr.getUserEmail(), msg);
//						
//					}
//					
//				}
//				
//				//发送完毕后修改meeting的isEmail值为1
//				meeting.setIsEmail("2");
//				meetingService.updateMeeting2(meeting);
//			}
//			
//			
//			return new JsonResult<Boolean>();
//		}catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Boolean>(e.getMessage());
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
