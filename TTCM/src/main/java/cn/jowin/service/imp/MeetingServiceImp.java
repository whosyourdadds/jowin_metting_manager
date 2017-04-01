package cn.jowin.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jowin.dao.MeetingDao;
import cn.jowin.dao.RoomDao;
import cn.jowin.dao.UserDao;
import cn.jowin.entity.Meeting;
import cn.jowin.entity.MeetingRelation;
import cn.jowin.entity.Room;
import cn.jowin.entity.RoomRelation;
import cn.jowin.entity.User;
import cn.jowin.service.MeetingRelationService;
import cn.jowin.service.MeetingService;
import cn.jowin.service.RoomRelationService;
import cn.jowin.service.exception.ServiceException;
import cn.jowin.util.ReturnIdList;
import cn.jowin.util.task.SendMailTask;

@Service("meetingService")
public class MeetingServiceImp implements MeetingService {

	@Resource
	private MeetingDao meetingDao;
	@Resource
	private UserDao userDao;
	
	@Resource
	private RoomDao roomDao;
	
	@Resource
	private MeetingRelationService meetingRelationService;
	
	@Resource
	private RoomRelationService roomRelationService;
	
	private static final String START = "start";
	private static final String END = "end";
	
	private static final String DELETE = "3";
	private static final String OUT_DATE = "2";
	private static final String IN_DATE = "1";
	
	@Transactional
	public Meeting findMeetingById(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		
		Meeting meeting = meetingDao.findMeetingById(meetingId);
		if(meeting == null){
			throw new ServiceException("没有该会议2");
		}
		return meeting;
	}

	@Transactional
	public Meeting findMeetingBySubject(String subject) {
		if(subject==null||subject.trim().isEmpty()){
			throw new ServiceException("会议主题不能为空");
		}
		
		Meeting meeting = meetingDao.findMeetingBySubject(subject);
		if(meeting == null){
			throw new ServiceException("没有该会议3");
		}
		return meeting;
	}
	
	//这个方法主要找到时间在这之间的会议
	@Transactional
	public List<Meeting> findMeetingByTime(Long startTime, Long endTime,String jud) {
//		System.out.println("--------");
		if(startTime==null){
			throw new ServiceException("开始时间不能为空");
		}
		if(endTime==null){
			throw new ServiceException("结束时间不能为空");
		}
		if(jud==null){
			throw new ServiceException("jud不能为空");
		}
		Map<String,Long> time = new HashMap<String, Long>();
		time.put("startTime", startTime);
		time.put("endTime", endTime);
		
		List<Meeting> meetingList;
		if(START.equals(jud)){
			System.out.println("start");
			meetingList = meetingDao.findMeetingByTime(time);
		}else{
			System.out.println("end");
			meetingList = meetingDao.findMeetingByEndTime(time);
		}
//		System.out.println("-----------");
		return meetingList;
	}
	
	
	
	@Transactional
	public List<Object> saveMeeting(String[] attendeeUserId,
			String[] hostUserId,String[] VIPUserId , String startDate,
			String endDate,String GMT, String repeat,String subject,String description,
			Long delayTime,String meetingFile,Integer limit, Room room) {
		
		Map<String,Object> map=saveOrUpdateJudge(attendeeUserId,  hostUserId,  VIPUserId,
				 startDate, endDate, GMT, repeat, subject, description,
				delayTime, meetingFile, limit, room);
		Long sDate=(Long) map.get("sDate");
		Long eDate=(Long)map.get("eDate");
		List<User> attendees = (List<User>) map.get("attendees");
		delayTime = (Long) map.get("delayTime");
		GMT = (String) map.get("GMT");
		repeat = (String) map.get("repeat");
		description = (String) map.get("description");
		limit = (Integer) map.get("limit");
		Integer record =Integer.parseInt(IN_DATE);
		String meetingId = UUID.randomUUID().toString();
		if(System.currentTimeMillis()>eDate.longValue()){
			record =Integer.parseInt(OUT_DATE);
		}
		hostUserId= ReturnIdList.RestoreIdList(hostUserId);
		VIPUserId=ReturnIdList.RestoreIdList(VIPUserId);
		
		Meeting meeting = new Meeting(meetingId,room.getRoomId(),attendees.get(0).getId()/*创建人Id*/,sDate,eDate,subject,description,GMT,null,null,null,null,repeat,sDate-(1000*60*30),System.currentTimeMillis(),delayTime,record,null);
		List<Object> list = new ArrayList<Object>();
		list.add(meeting);
		List<MeetingRelation> mrList = new ArrayList<MeetingRelation>();
		meetingDao.saveMeeting(meeting);
		Meeting meetingCheck = meetingDao.findMeetingById(meetingId);
		if(!meeting.equals(meetingCheck)){
			throw new ServiceException("预约会议失败1");
		}
//		int index = 0;
		for(User user:attendees){
			String hostUser=null;
			String vipUser=null;
			for(int i = 0;i<VIPUserId.length;i++){
				if(user.getId().equals(VIPUserId[i])){
					vipUser=VIPUserId[i];
					break;
				}
			}
			for(int i = 0;i<hostUserId.length;i++){
				if(user.getId().equals(hostUserId[i])){
					hostUser=hostUserId[i];
					break;
				}
			}
			MeetingRelation mr =meetingRelationService.saveMeetingRelation(user.getId(), meetingId,hostUser,vipUser);
			if(meetingRelationService.findMeetingByMeetingRelation(mr)==null){
				throw new ServiceException("预约会议失败");
			}
			mrList.add(mr);
//			++index;
		}
		RoomRelation rr = roomRelationService.saveRoomRelation(room.getRoomId(), meetingId);
		if(roomRelationService.findRoomByRoomRelation(rr)==null){
			throw new ServiceException("预约会议失败2");
		}
		room.setRoomStatus("预约");
		roomDao.updateRoomById(room);
		if(!room.equals(roomDao.findRoomById(room.getRoomId()))){
			throw new ServiceException("预约会议失败3");
		}
		list.add(mrList);
		return list;
	}
	/**
	 * 以下的方法是修改没有关联关系的meeting信息
	 * @param meeting
	 * @return
	 */
//	@Transactional
//	public Meeting updateMeeting2(Meeting meeting){
//		if(meeting==null){
//			throw new ServiceException("没有会议信息");
//		}
//		meetingDao.updateMeeting(meeting);
//		Meeting meetingCheck  = meetingDao.findMeetingById(meeting.getMeetingId());
//		if(!meetingCheck.equals(meeting)){
//			throw new ServiceException("会议修改失败");
//		}
//		return meeting;
//	}

	/**
	 * 以下方法是删除相关后重新保存
	 */
	@Transactional
	public Meeting updateMeeting(String meetingId, String[] attendeeUserId, String[] hostUserId, String[] VIPUserId,
			String startDate, String endDate, String GMT, String repeat, String subject, String description,
			Long delayTime, String meetingFile, Integer limit, Room room) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议ID不能为空");
		}
		Map<String,Object> map=saveOrUpdateJudge(attendeeUserId,  hostUserId,  VIPUserId,
				 startDate, endDate, GMT, repeat, subject, description,
				delayTime, meetingFile, limit, room);
		
		Long sDate=(Long) map.get("sDate");
		Long eDate=(Long)map.get("eDate");
		List<User> attendees = (List<User>) map.get("attendees");
		delayTime = (Long) map.get("delayTime");
		GMT = (String) map.get("GMT");
		repeat = (String) map.get("repeat");
		description = (String) map.get("description");
		limit = (Integer) map.get("limit");
		
		Integer record = Integer.parseInt(IN_DATE);
		if(System.currentTimeMillis()>eDate){
			record = Integer.parseInt(OUT_DATE);
		}
		
		hostUserId= ReturnIdList.RestoreIdList(hostUserId);
		VIPUserId=ReturnIdList.RestoreIdList(VIPUserId);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		
		Date date = new Date();
		String str = sdf1.format(date)+"00:00:00";
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			throw new ServiceException("修改会议失败");
		}
		Date date2 = new Date(date.getTime()+86400000);
//		System.out.println("UPDATE");
//		System.out.println(attendees.get(0).getId());
		
		Meeting checkMeeting = meetingDao.findMeetingById(meetingId);
		Meeting meeting = new Meeting(meetingId,room.getRoomId(),attendees.get(0).getId(),sDate,eDate,subject,description,GMT,null,null,null,null,repeat,sDate-(1000*60*30),System.currentTimeMillis(),delayTime,record,checkMeeting.getIsEmail());
		
		
		if(checkMeeting==null){
			throw new ServiceException("会议ID错误");
		}
		//房间or会议关系表
		if(!room.getRoomId().equals(checkMeeting.getRoomId())){
			//删除关系表，重新保存新的关系表
			roomRelationService.deleteRoomRelation(meetingId);
			roomRelationService.saveRoomRelation(room.getRoomId(), meetingId);
		}
		
		//会议or用户关系表，删光关系表，重新保存新的关系
		//如果之前的参会人和修改后的参会人不一样,就往下判断
		meetingRelationService.deleteMeetingRelationByMeetingId(meetingId);
		List<MeetingRelation> mrList = new ArrayList<MeetingRelation>();
		for(User user:attendees){
			String hostUser=null;
			String vipUser=null;
			for(int i = 0;i<VIPUserId.length;i++){
				if(user.getId().equals(VIPUserId[i])){
					vipUser=VIPUserId[i];
					break;
				}
			}
			for(int i = 0;i<hostUserId.length;i++){
				if(user.getId().equals(hostUserId[i])){
					hostUser=hostUserId[i];
					break;
				}
			}
			MeetingRelation mr =meetingRelationService.saveMeetingRelation(user.getId(), meetingId,hostUser,vipUser);
			if(meetingRelationService.findMeetingByMeetingRelation(mr)==null){
				throw new ServiceException("预约会议失败");
			}
			mrList.add(mr);
		}
		
		
		
		meetingDao.updateMeeting(meeting);
		meeting = meetingDao.findMeetingById(meetingId);
		if(meeting.equals(checkMeeting)){
			throw new ServiceException("修改会议失败");
		}
		String msg;
		if(!(checkMeeting.getStartTime().longValue()==sDate&&checkMeeting.getEndTime().longValue()==eDate)){
			if(date.getTime()<sDate.longValue()&&sDate.longValue()<=date2.getTime()){
				Timer timer = new Timer();
				msg = "您有一场会议即将开始!<br>会议:"+subject+"  开始时间:"+sdf.format(new Date(sDate));
				timer.schedule(new SendMailTask(mrList,msg,timer,meeting.getMeetingId(), meeting.getStartTime(),START), new Date(sDate-1800000));
			}
			if(date.getTime()<eDate.longValue()&&eDate.longValue()<=date2.getTime()){
				Timer timer1 = new Timer();
				msg ="您的会议即将结束!<br>如果需要延迟";
				timer1.schedule(new SendMailTask(mrList,msg,timer1,meeting.getMeetingId(), meeting.getEndTime(), END), new Date(eDate-1800000));
			}
		}
		
		return meeting;
	}
	
	/**
	 * 这个方法是判断会议保存还是修改，目的是减小代码体积，
	 * 这些代码是会议保存和修改相同的代码。
	 * @return Map内放的是用户输入的会议数据
	 */
	@Transactional
	public Map<String , Object> saveOrUpdateJudge(String[] attendeeUserId, String[] hostUserId, String[] VIPUserId,
			String startDate, String endDate, String GMT, String repeat, String subject, String description,
			Long delayTime, String meetingFile, Integer limit, Room room){
		
		if(subject==null||subject.trim().isEmpty()){
			throw new ServiceException("会议主题不能为空");
		}
		if(GMT==null||GMT.trim().isEmpty()){
			//时区如果为空，默认+8区
			GMT = "+8";
		}
		if(repeat==null||repeat.trim().isEmpty()){
			//重复为空时，默认为否
			repeat = "否";
		}
		if(startDate==null){
			throw new ServiceException("开始时间不能为空");
		}
		if(endDate==null){
			throw new ServiceException("结束时间不能为空");
		}
		if(description==null){
			description="";
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		Long sDate;
		Long eDate;
		try {
			sDate = sdf1.parse(startDate).getTime();
			eDate = sdf1.parse(endDate).getTime();
			if(delayTime==null){
				//延迟时间时间为空时，默认为结束时间
				delayTime=eDate+(1000*60*30);
			}
			delayTime= eDate+(1000*60*delayTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发现一个未知错误请重试！");
		}
		
		
		if(room.getRoomId()==null||room.getRoomId().trim().isEmpty()){
			throw new ServiceException("房间ID不能为空");
		}
		if(limit==null){
			limit = 0;
		}
		List<User> attendees = new ArrayList<User>();
//		List<User> hosts = new ArrayList<User>();
//		List<User> VIPs = new ArrayList<User>();
//		String host = "";
//		String vip = "";
//		System.out.println("saveOrUpdateJudge");
//		System.out.println(attendeeUserId);
		for(int i = 0;i<attendeeUserId.length;i++){
			
			attendeeUserId[i]=attendeeUserId[i].replace("[", "");
			attendeeUserId[i]=attendeeUserId[i].replace("]", "");
			attendeeUserId[i]=attendeeUserId[i].replace("\"", "");
			User user = userDao.findUserById(attendeeUserId[i]);
			attendees.add(user);
			for(int j = 0; j<hostUserId.length;j++){
				hostUserId[j]=hostUserId[j].replace("[", "");
				hostUserId[j]=hostUserId[j].replace("]", "");
				hostUserId[j]=hostUserId[j].replace("\"", "");
			}
//					if(attendeeUserId[i].equals(hostUserId[j])){
//						System.out.println("进入if");
//						hosts.add(user);
////						if(host.trim().isEmpty()){
////							host = user.getName();
////						}else{
////							host = host+","+user.getName();
////							System.out.println("第二个主持人");
////						}
//					}
//			}
//				for(int j = 0; j<VIPUserId.length;j++){
//					VIPUserId[j]=VIPUserId[j].replace("[", "");
//					VIPUserId[j]=VIPUserId[j].replace("]", "");
//					VIPUserId[j]=VIPUserId[j].replace("\"", "");
//					if(attendeeUserId[i].equals(VIPUserId[j])){
//						VIPs.add(user);
////						if(vip.trim().isEmpty()){
////							vip =user.getName();
////						}else{
////							vip =vip+ ","+user.getName();
////						}
//					}
//				}
				
		}
		
//		String attendee = "";
//		String attendeeModile = "";
//		String attendeeEmail = "";
//		
//		for(User user:attendees){
//			if(attendee.trim().isEmpty()){
//				attendee = user.getName();
//			}else{
//				attendee = attendee+","+user.getName();
//			}
//			if(attendeeEmail.trim().isEmpty()){
//				attendeeEmail = user.getEmail();
//			}else{
//				attendeeEmail = attendeeEmail+","+user.getEmail();
//			}
//			if(attendeeModile.trim().isEmpty()){
//				attendeeModile = user.getModile();
//			}else{
//				attendeeModile = attendeeModile+","+user.getModile();
//			}
//		}
			
		Map<String , Object> map = new HashMap<String , Object>();	
		
		map.put("attendees", attendees);
		map.put("sDate",sDate );
		map.put("eDate",eDate );
//		map.put("host",host );
//		map.put("attendee", attendee);
//		map.put("attendeeEmail",attendeeEmail );
//		map.put("attendeeModile",attendeeModile );
//		map.put("vip",vip );
		map.put("delayTime",delayTime );
		map.put("description",description );
		map.put("repeat",repeat );
		map.put("GMT",GMT );
		map.put("limit",limit );
		
		//description,repeat,GMT,limit
//		attendees.get(0).getId(),sDate,eDate,host,attendee,attendeeEmail,attendeeModile,vip，delayTime
		
		return map;
	}

	//彻底删除会议，还没用到
//	@Transactional
//	public Meeting deleteMeeting(String meetingId) {
//		if(meetingId==null||meetingId.trim().isEmpty()){
//			throw new ServiceException("会议ID不能为空");
//		}
//		Meeting meeting = findMeetingById(meetingId);
//		if(meeting==null){
//			throw new ServiceException("没有该会议");
//		}
//		meetingRelationService.deleteMeetingRelationByMeetingId(meeting.getMeetingId());
//		roomRelationService.deleteRoomRelation(meeting.getMeetingId());
//		meetingDao.deleteMeeting(meeting.getMeetingId());
//	
//		Meeting meetingCheck = meetingDao.findMeetingById(meeting.getMeetingId());
//		if(meeting.equals(meetingCheck)||meetingCheck!=null){
//			throw new ServiceException("删除会议失败");
//		}
//		return meeting;
//	}
	
	
	/**
	 * 这个是修改方法，是将record改成删除或者过期
	 */
	@Transactional
	public Meeting updateRecordMeeting(String meetingId,Integer record) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议ID不能为空");
		}
		Meeting meeting = findMeetingById(meetingId);
		if(meeting==null){
			throw new ServiceException("没有该会议");
		}
		//record!=DELETE
		if(record==Integer.parseInt(DELETE)){
			meeting.setRecord(record);
			meetingRelationService.updateIsDelete(meetingId,3);
			roomRelationService.updateIsDelete(meetingId,3);
			meetingDao.updateRecordMeeting(meeting);
			Meeting meetingCheck = findMeetingById(meetingId);
			if(!meetingCheck.equals(meeting)){
				throw new ServiceException("会议删除失败");
			}
		}
		if(record==Integer.parseInt(OUT_DATE)){
			meeting.setRecord(record);
			meetingRelationService.updateIsDelete(meetingId,2);
			roomRelationService.updateIsDelete(meetingId,2);
			meetingDao.updateRecordMeeting(meeting);
			Meeting meetingCheck = findMeetingById(meetingId);
			if(!meetingCheck.equals(meeting)){
				throw new ServiceException("会议过期失败");
			}
		}
		return meeting;
	}

	/*
	 * 这个方法是查询record不等于删除状态的所有会议，
	 * 如果这个meeting为空，说明没有会议
	 * (non-Javadoc)
	 * @see cn.jowin.service.MeetingService#findMeetingByNotEqualRecord()
	 */
	@Transactional
	public List<Meeting> findMeetingByNotEqualRecord() {

		List<Meeting> meetingList = meetingDao.findMeetingByNotEqualRecord();
		return meetingList;
	}

	@Transactional
	public List<Meeting> findMeetingByRecord(Integer record) {
		if(record==null){
			throw new ServiceException("record不能为空");
		}
		List<Meeting> meetingList = meetingDao.findMeetingByRecord(record);
		//如果meeting为空，说明没有该状态下的会议
		return meetingList;
	}

	@Transactional
	public void deleteMeetingByRoomId(String roomId) {
		if(roomId==null){
			throw new ServiceException("房间ID不能为空");
		}
		meetingDao.deleteMeetingByRoomId(roomId);
		List<Meeting> meetingList = meetingDao.findMeetingByRoomId(roomId);
		if(!(meetingList==null||meetingList.isEmpty())){
			throw new ServiceException("会议删除失败");
		}
		
		
	}

	@Transactional
	public List<Meeting> findMeetingByRoomIdAndStartTime(String roomId, Long startTime) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("房间ID不能为空");
		}
		if(startTime==null){
			throw new ServiceException("开始时间不能为空");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("roomId", roomId);
		map.put("startTime", startTime);
		List<Meeting> meetingList = meetingDao.findMeetingByRoomIdAndStartTime(map);
		
		
		return meetingList;
	}

	@Transactional
	public Meeting updateIsEmailMeeting(Meeting meeting) {
		if(meeting==null){
			throw new ServiceException("会议不能为空");
		}
		if(meeting.getMeetingId()==null||meeting.getMeetingId().equals("")){
			throw new ServiceException("会议ID不能为空");
		}
		
		meetingDao.updateIsEmailMeeting(meeting);
		Meeting meetingCheck = meetingDao.findMeetingById(meeting.getMeetingId());
		if(!(meetingCheck==meeting||meetingCheck.equals(meeting))){
			throw new ServiceException("修改isEmail失败");
		}
		return meetingCheck;
	}
	

}
