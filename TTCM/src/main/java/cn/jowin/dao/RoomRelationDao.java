package cn.jowin.dao;

import java.util.List;
import java.util.Map;

import cn.jowin.entity.RoomRelation;

public interface RoomRelationDao {
	void saveRoomRelation(RoomRelation roomRelation);
	List<String> findRoomByRoomId(String roomId);
	List<String> findRoomByMeetingId(String meetingId);
	List<RoomRelation> findRoomRelationByMeetingId(String meetingId);
	RoomRelation findRoomByRoomRelation(RoomRelation roomRelation);
	void deleteRoomRelation(Map<String,String> idAll);
	void updateRoomRelationByMeetingId(RoomRelation roomRelation);
	void updateIsDelete(Map<String,String> data);
	void deleteRoomRelationByRoomId(String roomId);
}
