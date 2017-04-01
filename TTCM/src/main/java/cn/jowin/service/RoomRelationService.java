package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.RoomRelation;

public interface RoomRelationService {
	RoomRelation saveRoomRelation(String roomId,String meetingId);
	List<String> findRoomByRoomId(String roomId);
	List<String> findRoomByMeetingId(String meetingId);
	RoomRelation findRoomByRoomRelation(RoomRelation roomRelation);
	void deleteRoomRelation(String meetingId);
	//用于删除房间时需要同时删除所以关系表和相关会议
	void deleteRoomRelationByRoomId(String roomId);
	
	void updateRoomRelationByMeetingId(String meetingId, String roomId);
	void updateIsDelete(String meetingId,Integer isDelete);
	
	
	
	
	
}
