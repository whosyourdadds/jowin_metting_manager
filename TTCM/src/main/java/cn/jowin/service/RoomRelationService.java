package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.RoomRelation;

public interface RoomRelationService {
	RoomRelation saveRoomRelation(String roomId,String meetingId);
	List<String> findRoomByRoomId(String roomId);
	List<String> findRoomByMeetingId(String meetingId);
	RoomRelation findRoomByRoomRelation(RoomRelation roomRelation);
	void deleteRoomRelation(String meetingId);
	//����ɾ������ʱ��Ҫͬʱɾ�����Թ�ϵ�����ػ���
	void deleteRoomRelationByRoomId(String roomId);
	
	void updateRoomRelationByMeetingId(String meetingId, String roomId);
	void updateIsDelete(String meetingId,Integer isDelete);
	
	
	
	
	
}
