package cn.jowin.dao;

import java.util.List;
import java.util.Map;

import cn.jowin.entity.MeetingRelation;

public interface MeetingRelationDao {
	void saveMeetingRelation(MeetingRelation meetingRelation);
	List<MeetingRelation> findMeetingByUserId(String userId);
	List<MeetingRelation> findMeetingByMeetingId(String meetingId);
	MeetingRelation findMeetingByMeetingRelation(MeetingRelation meetingRelation);
	void deleteMeetingRelation(Map<String,String> idAll);
	void updateMeetingRelationByMeetingId(MeetingRelation meetingRelation);
	void updateIsDelete(Map<String,String> data);
	void deleteMeetingRelationByMeetingId(String meetingId);
	
	
}
