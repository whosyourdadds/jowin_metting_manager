package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.MeetingRelation;

public interface MeetingRelationService {
	MeetingRelation saveMeetingRelation(String userId, String meetingId,String hostUser,String vipUser);
	List<MeetingRelation> findMeetingByUserId(String userId);
	List<MeetingRelation> findMeetingByMeetingId(String meetingId);
	MeetingRelation findMeetingByMeetingRelation(MeetingRelation meetingRelation);
	MeetingRelation deleteMeetingRelation(String meetingId, String userId);
//	û�õ������ܻ����
//	void updateMeetingRelationByMeetingId(String userId, String meetingId,String hostUser,String vipUser);
	
	void updateIsDelete(String meetingId,Integer isDelete);
	//����ɾ������ʱ��Ҫͬʱɾ�����Թ�ϵ�����ػ���
	void deleteMeetingRelationByMeetingId(String meetingId);
	
	
	
}
