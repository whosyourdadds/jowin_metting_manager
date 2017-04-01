package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.MeetingRelation;

public interface MeetingRelationService {
	MeetingRelation saveMeetingRelation(String userId, String meetingId,String hostUser,String vipUser);
	List<MeetingRelation> findMeetingByUserId(String userId);
	List<MeetingRelation> findMeetingByMeetingId(String meetingId);
	MeetingRelation findMeetingByMeetingRelation(MeetingRelation meetingRelation);
	MeetingRelation deleteMeetingRelation(String meetingId, String userId);
//	没用到，可能会废弃
//	void updateMeetingRelationByMeetingId(String userId, String meetingId,String hostUser,String vipUser);
	
	void updateIsDelete(String meetingId,Integer isDelete);
	//用于删除房间时需要同时删除所以关系表和相关会议
	void deleteMeetingRelationByMeetingId(String meetingId);
	
	
	
}
