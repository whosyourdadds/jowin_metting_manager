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
	//��ѯ�÷����д��ڿ�ʼʱ��Ļ���
	List<Meeting> findMeetingByRoomIdAndStartTime(String roomId,Long startTime);
	List<Meeting> findMeetingByTime(Long startTime ,Long endTime,String jud);
	
//	Meeting deleteMeeting(String meetingId); �÷���Ϊ����ɾ��
	Meeting updateMeeting(String meetingId,String[] attendeeUserId,
			String[] hostUserId,String[] VIPUserId , String startDate,
			String endDate,String GMT, String repeat,String subject,String description,
			Long delayTime,String meetingFile,Integer limit, Room room);
	
	Meeting updateRecordMeeting(String meetingId,Integer record);
	Meeting updateIsEmailMeeting(Meeting meeting);
	List<Meeting> findMeetingByNotEqualRecord();
	List<Meeting> findMeetingByRecord(Integer record);
	
	//����ɾ������ʱ��Ҫͬʱɾ�����Թ�ϵ�����ػ���
	void deleteMeetingByRoomId(String roomId);
	
//	public Meeting updateMeeting2(Meeting meeting);
	
}
