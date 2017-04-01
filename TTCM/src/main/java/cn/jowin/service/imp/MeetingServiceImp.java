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
			throw new ServiceException("����Id����Ϊ��");
		}
		
		Meeting meeting = meetingDao.findMeetingById(meetingId);
		if(meeting == null){
			throw new ServiceException("û�иû���2");
		}
		return meeting;
	}

	@Transactional
	public Meeting findMeetingBySubject(String subject) {
		if(subject==null||subject.trim().isEmpty()){
			throw new ServiceException("�������ⲻ��Ϊ��");
		}
		
		Meeting meeting = meetingDao.findMeetingBySubject(subject);
		if(meeting == null){
			throw new ServiceException("û�иû���3");
		}
		return meeting;
	}
	
	//���������Ҫ�ҵ�ʱ������֮��Ļ���
	@Transactional
	public List<Meeting> findMeetingByTime(Long startTime, Long endTime,String jud) {
//		System.out.println("--------");
		if(startTime==null){
			throw new ServiceException("��ʼʱ�䲻��Ϊ��");
		}
		if(endTime==null){
			throw new ServiceException("����ʱ�䲻��Ϊ��");
		}
		if(jud==null){
			throw new ServiceException("jud����Ϊ��");
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
		
		Meeting meeting = new Meeting(meetingId,room.getRoomId(),attendees.get(0).getId()/*������Id*/,sDate,eDate,subject,description,GMT,null,null,null,null,repeat,sDate-(1000*60*30),System.currentTimeMillis(),delayTime,record,null);
		List<Object> list = new ArrayList<Object>();
		list.add(meeting);
		List<MeetingRelation> mrList = new ArrayList<MeetingRelation>();
		meetingDao.saveMeeting(meeting);
		Meeting meetingCheck = meetingDao.findMeetingById(meetingId);
		if(!meeting.equals(meetingCheck)){
			throw new ServiceException("ԤԼ����ʧ��1");
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
				throw new ServiceException("ԤԼ����ʧ��");
			}
			mrList.add(mr);
//			++index;
		}
		RoomRelation rr = roomRelationService.saveRoomRelation(room.getRoomId(), meetingId);
		if(roomRelationService.findRoomByRoomRelation(rr)==null){
			throw new ServiceException("ԤԼ����ʧ��2");
		}
		room.setRoomStatus("ԤԼ");
		roomDao.updateRoomById(room);
		if(!room.equals(roomDao.findRoomById(room.getRoomId()))){
			throw new ServiceException("ԤԼ����ʧ��3");
		}
		list.add(mrList);
		return list;
	}
	/**
	 * ���µķ������޸�û�й�����ϵ��meeting��Ϣ
	 * @param meeting
	 * @return
	 */
//	@Transactional
//	public Meeting updateMeeting2(Meeting meeting){
//		if(meeting==null){
//			throw new ServiceException("û�л�����Ϣ");
//		}
//		meetingDao.updateMeeting(meeting);
//		Meeting meetingCheck  = meetingDao.findMeetingById(meeting.getMeetingId());
//		if(!meetingCheck.equals(meeting)){
//			throw new ServiceException("�����޸�ʧ��");
//		}
//		return meeting;
//	}

	/**
	 * ���·�����ɾ����غ����±���
	 */
	@Transactional
	public Meeting updateMeeting(String meetingId, String[] attendeeUserId, String[] hostUserId, String[] VIPUserId,
			String startDate, String endDate, String GMT, String repeat, String subject, String description,
			Long delayTime, String meetingFile, Integer limit, Room room) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����ID����Ϊ��");
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
			throw new ServiceException("�޸Ļ���ʧ��");
		}
		Date date2 = new Date(date.getTime()+86400000);
//		System.out.println("UPDATE");
//		System.out.println(attendees.get(0).getId());
		
		Meeting checkMeeting = meetingDao.findMeetingById(meetingId);
		Meeting meeting = new Meeting(meetingId,room.getRoomId(),attendees.get(0).getId(),sDate,eDate,subject,description,GMT,null,null,null,null,repeat,sDate-(1000*60*30),System.currentTimeMillis(),delayTime,record,checkMeeting.getIsEmail());
		
		
		if(checkMeeting==null){
			throw new ServiceException("����ID����");
		}
		//����or�����ϵ��
		if(!room.getRoomId().equals(checkMeeting.getRoomId())){
			//ɾ����ϵ�����±����µĹ�ϵ��
			roomRelationService.deleteRoomRelation(meetingId);
			roomRelationService.saveRoomRelation(room.getRoomId(), meetingId);
		}
		
		//����or�û���ϵ��ɾ���ϵ�����±����µĹ�ϵ
		//���֮ǰ�Ĳλ��˺��޸ĺ�Ĳλ��˲�һ��,�������ж�
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
				throw new ServiceException("ԤԼ����ʧ��");
			}
			mrList.add(mr);
		}
		
		
		
		meetingDao.updateMeeting(meeting);
		meeting = meetingDao.findMeetingById(meetingId);
		if(meeting.equals(checkMeeting)){
			throw new ServiceException("�޸Ļ���ʧ��");
		}
		String msg;
		if(!(checkMeeting.getStartTime().longValue()==sDate&&checkMeeting.getEndTime().longValue()==eDate)){
			if(date.getTime()<sDate.longValue()&&sDate.longValue()<=date2.getTime()){
				Timer timer = new Timer();
				msg = "����һ�����鼴����ʼ!<br>����:"+subject+"  ��ʼʱ��:"+sdf.format(new Date(sDate));
				timer.schedule(new SendMailTask(mrList,msg,timer,meeting.getMeetingId(), meeting.getStartTime(),START), new Date(sDate-1800000));
			}
			if(date.getTime()<eDate.longValue()&&eDate.longValue()<=date2.getTime()){
				Timer timer1 = new Timer();
				msg ="���Ļ��鼴������!<br>�����Ҫ�ӳ�";
				timer1.schedule(new SendMailTask(mrList,msg,timer1,meeting.getMeetingId(), meeting.getEndTime(), END), new Date(eDate-1800000));
			}
		}
		
		return meeting;
	}
	
	/**
	 * ����������жϻ��鱣�滹���޸ģ�Ŀ���Ǽ�С���������
	 * ��Щ�����ǻ��鱣����޸���ͬ�Ĵ��롣
	 * @return Map�ڷŵ����û�����Ļ�������
	 */
	@Transactional
	public Map<String , Object> saveOrUpdateJudge(String[] attendeeUserId, String[] hostUserId, String[] VIPUserId,
			String startDate, String endDate, String GMT, String repeat, String subject, String description,
			Long delayTime, String meetingFile, Integer limit, Room room){
		
		if(subject==null||subject.trim().isEmpty()){
			throw new ServiceException("�������ⲻ��Ϊ��");
		}
		if(GMT==null||GMT.trim().isEmpty()){
			//ʱ�����Ϊ�գ�Ĭ��+8��
			GMT = "+8";
		}
		if(repeat==null||repeat.trim().isEmpty()){
			//�ظ�Ϊ��ʱ��Ĭ��Ϊ��
			repeat = "��";
		}
		if(startDate==null){
			throw new ServiceException("��ʼʱ�䲻��Ϊ��");
		}
		if(endDate==null){
			throw new ServiceException("����ʱ�䲻��Ϊ��");
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
				//�ӳ�ʱ��ʱ��Ϊ��ʱ��Ĭ��Ϊ����ʱ��
				delayTime=eDate+(1000*60*30);
			}
			delayTime= eDate+(1000*60*delayTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("����һ��δ֪���������ԣ�");
		}
		
		
		if(room.getRoomId()==null||room.getRoomId().trim().isEmpty()){
			throw new ServiceException("����ID����Ϊ��");
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
//						System.out.println("����if");
//						hosts.add(user);
////						if(host.trim().isEmpty()){
////							host = user.getName();
////						}else{
////							host = host+","+user.getName();
////							System.out.println("�ڶ���������");
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
//		attendees.get(0).getId(),sDate,eDate,host,attendee,attendeeEmail,attendeeModile,vip��delayTime
		
		return map;
	}

	//����ɾ�����飬��û�õ�
//	@Transactional
//	public Meeting deleteMeeting(String meetingId) {
//		if(meetingId==null||meetingId.trim().isEmpty()){
//			throw new ServiceException("����ID����Ϊ��");
//		}
//		Meeting meeting = findMeetingById(meetingId);
//		if(meeting==null){
//			throw new ServiceException("û�иû���");
//		}
//		meetingRelationService.deleteMeetingRelationByMeetingId(meeting.getMeetingId());
//		roomRelationService.deleteRoomRelation(meeting.getMeetingId());
//		meetingDao.deleteMeeting(meeting.getMeetingId());
//	
//		Meeting meetingCheck = meetingDao.findMeetingById(meeting.getMeetingId());
//		if(meeting.equals(meetingCheck)||meetingCheck!=null){
//			throw new ServiceException("ɾ������ʧ��");
//		}
//		return meeting;
//	}
	
	
	/**
	 * ������޸ķ������ǽ�record�ĳ�ɾ�����߹���
	 */
	@Transactional
	public Meeting updateRecordMeeting(String meetingId,Integer record) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����ID����Ϊ��");
		}
		Meeting meeting = findMeetingById(meetingId);
		if(meeting==null){
			throw new ServiceException("û�иû���");
		}
		//record!=DELETE
		if(record==Integer.parseInt(DELETE)){
			meeting.setRecord(record);
			meetingRelationService.updateIsDelete(meetingId,3);
			roomRelationService.updateIsDelete(meetingId,3);
			meetingDao.updateRecordMeeting(meeting);
			Meeting meetingCheck = findMeetingById(meetingId);
			if(!meetingCheck.equals(meeting)){
				throw new ServiceException("����ɾ��ʧ��");
			}
		}
		if(record==Integer.parseInt(OUT_DATE)){
			meeting.setRecord(record);
			meetingRelationService.updateIsDelete(meetingId,2);
			roomRelationService.updateIsDelete(meetingId,2);
			meetingDao.updateRecordMeeting(meeting);
			Meeting meetingCheck = findMeetingById(meetingId);
			if(!meetingCheck.equals(meeting)){
				throw new ServiceException("�������ʧ��");
			}
		}
		return meeting;
	}

	/*
	 * ��������ǲ�ѯrecord������ɾ��״̬�����л��飬
	 * ������meetingΪ�գ�˵��û�л���
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
			throw new ServiceException("record����Ϊ��");
		}
		List<Meeting> meetingList = meetingDao.findMeetingByRecord(record);
		//���meetingΪ�գ�˵��û�и�״̬�µĻ���
		return meetingList;
	}

	@Transactional
	public void deleteMeetingByRoomId(String roomId) {
		if(roomId==null){
			throw new ServiceException("����ID����Ϊ��");
		}
		meetingDao.deleteMeetingByRoomId(roomId);
		List<Meeting> meetingList = meetingDao.findMeetingByRoomId(roomId);
		if(!(meetingList==null||meetingList.isEmpty())){
			throw new ServiceException("����ɾ��ʧ��");
		}
		
		
	}

	@Transactional
	public List<Meeting> findMeetingByRoomIdAndStartTime(String roomId, Long startTime) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("����ID����Ϊ��");
		}
		if(startTime==null){
			throw new ServiceException("��ʼʱ�䲻��Ϊ��");
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
			throw new ServiceException("���鲻��Ϊ��");
		}
		if(meeting.getMeetingId()==null||meeting.getMeetingId().equals("")){
			throw new ServiceException("����ID����Ϊ��");
		}
		
		meetingDao.updateIsEmailMeeting(meeting);
		Meeting meetingCheck = meetingDao.findMeetingById(meeting.getMeetingId());
		if(!(meetingCheck==meeting||meetingCheck.equals(meeting))){
			throw new ServiceException("�޸�isEmailʧ��");
		}
		return meetingCheck;
	}
	

}
