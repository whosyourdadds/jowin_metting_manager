package cn.jowin.service;

import java.util.List;
import java.util.Map;

import cn.jowin.entity.Meeting;
import cn.jowin.entity.Room;

public interface MeetingService {
	
	
	List<Object> saveMeeting(
			String[] attendeeUserId,
			String[] hostUserId,String[] VIPUserId , String startDate,
			String endDate,String GMT, String repeat,String subject,String description,
			Long delayTime,String meetingFile,Integer limit, Room roomList
			);
	Meeting findMeetingById(String meetingId);
	Meeting findMeetingBySubject(String subject);
	//查询该房间中大于开始时间的会议
	List<Meeting> findMeetingByRoomIdAndStartTime(String roomId,Long startTime);
	List<Meeting> findMeetingByTime(Long startTime ,Long endTime,String jud);
	
//	Meeting deleteMeeting(String meetingId); 该方法为彻底删除
	Meeting updateMeeting(String meetingId,String[] attendeeUserId,
			String[] hostUserId,String[] VIPUserId , String startDate,
			String endDate,String GMT, String repeat,String subject,String description,
			Long delayTime,String meetingFile,Integer limit, Room room);
	
	Meeting updateRecordMeeting(String meetingId,Integer record);
	Meeting updateIsEmailMeeting(Meeting meeting);
	List<Meeting> findMeetingByNotEqualRecord();
	List<Meeting> findMeetingByRecord(Integer record);
	
	//用于删除房间时需要同时删除所以关系表和相关会议
	void deleteMeetingByRoomId(String roomId);
	
//	public Meeting updateMeeting2(Meeting meeting);
	
}
