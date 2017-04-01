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
	
	
//	�÷��������������ӷ��䣬
//	���Ǻ��������ڴ���CompanyRegistController���д������䷽��
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
	
	//��ѯ���ݿ��������û�����Ϊ�Զ���ɹ��ܵ�����
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
			//���Ϊ����״̬˵���÷���û�л���ԤԼ��ռ�ã���ֱ����ʾ
			if("����".equals(room.getRoomStatus())){
				roomList.add(room);
				
			}else{
				/*
				 * ���Ϊ����״̬������Ҫ��ȡ�÷���ID��ѯ�����飬
				 * ����ʱ����Ƿ��л����Ѿ�ԤԼ�����û�У�����ʾ��
				 * ����У�����ʾ��
				 * ������ʱ��ζ�û�з�����У�����ʾ��ʱ���û�з��䴦�ڿ���״̬
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
						//�Ѿ������ӳ�ʱ��
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
//								System.out.println("------������"+meeting.getRecord());
							}
						}
					}
					if(index==0){
						roomList.add(room);
//						System.out.println("------�����");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("δ֪����������");
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
								("����һ���µĻ���:<br>     ����Ϊ:" + meeting.getSubject() + "<br>����" + sdf1.format(date) + "��ʼ"));
						
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
	 * �ѷ���
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
	
//����ɾ�����鷽����û�õ�	
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
			//���ص���һ����ȥ��ɾ���Ļ���
			List<Meeting> meetingList = meetingService.findMeetingByNotEqualRecord();
			List<Meeting> mList = new ArrayList<Meeting>();
			//дһ����dateʱ�䷶Χ֮�ڵ�
			for(Meeting meeting: meetingList){
				//�����ʵֻҪ�ж�һ��������Ϊ���Ͻ����ж���2��
					
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
	 * �ܾ����飬ɾ���û��͸û������ع���
	 * @param meetingId
	 * @param userId
	 * @return ɾ���Ļ����������
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
	
	//�����޸Ĺ����ݿ��ֶΣ���ȡ���������˺Ͳλ�����Ϣ�ˣ����������޸�
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
//						 * ���booleanֵ���ж��ʼ����ͳɹ�����ʧ�ܣ�������2��������
//						 * 1.����ʧ�ܲ����ٴη����ʼ�
//						 * 2.����ʧ�ܻ����·���ֱ�����ͳɹ�
//						 * Ŀǰ�õ��ǵ�һ������
//						 */
//						boolean flag = send.sendMailHTML("18702104117@163.com",
//								mr.getUserEmail(), 
//								"����һ�����鼴����ʼ!<br>����:"+meeting.getSubject()+
//								"  ��ʼʱ��:"+sdf1.format(new Date(meeting.getStartTime())));
//						
//					}
//					
//				}
//				//������Ϻ��޸�meeting��isEmailֵΪ1
//				meeting.setIsEmail("1");
//				meetingService.updateMeeting2(meeting);
//				
//			}else{
//				//�����ж��¸�ʱ�����û�л���ԤԼ
//				List<Meeting> meetingList = meetingService.findMeetingByRoomIdAndStartTime(meeting.getRoomId(), meeting.getStartTime());
//				String msg = "���Ļ��鼴������!<br>�����Ҫ�ӳ�";
//				for(Meeting m:meetingList){
//					Long mStart = m.getStartTime();
//					Long mEnd = meeting.getEndTime()+3600000;
//					if(mEnd.longValue()>mStart.longValue()){
//						msg="���Ļ��鼴������!<br>�¸�ʱ����Ѿ�����ԤԼ���޷��ӳ�!";
//						break;
//					}
//				}
//				
//				for(MeetingRelation mr :mrList){
//					if(!(mr.getUserEmail()==null||mr.getUserEmail().trim().isEmpty())){
//						/*
//						 * ֪ͨ�û����鼴���������������û�л���ԤԼ�����ṩ�ӳٰ�ť��
//						 * ��������л������ʾ���¸�ʱ����Ѿ��л���ԤԼ�������������
//						 */
//						boolean flag = send.sendMailHTML("18702104117@163.com",
//								mr.getUserEmail(), msg);
//						
//					}
//					
//				}
//				
//				//������Ϻ��޸�meeting��isEmailֵΪ1
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
