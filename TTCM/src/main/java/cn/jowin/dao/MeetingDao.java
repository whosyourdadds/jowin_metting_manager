package cn.jowin.dao;

import java.util.List;
import java.util.Map;

import cn.jowin.entity.Meeting;

public interface MeetingDao {
	void saveMeeting(Meeting meeting);
	Meeting findMeetingById(String meetingId);
	Meeting findMeetingBySubject(String subject);
	List<Meeting> findMeetingByTime(Map<String,Long> time);
	List<Meeting> findMeetingByEndTime(Map<String,Long> time);
	void deleteMeeting(String meetingId);
	void deleteMeetingByRoomId(String roomId);
	void updateMeeting(Meeting meeting);
	void updateRecordMeeting(Meeting meeting);
	void updateIsEmailMeeting(Meeting meeting);
	List<Meeting> findMeetingByNotEqualRecord();
	List<Meeting> findMeetingByRecord(Integer record);
	List<Meeting> findMeetingByRoomId(String roomId);
	List<Meeting> findMeetingByRoomIdAndStartTime(Map<String,Object> map);
}
