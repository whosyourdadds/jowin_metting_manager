package cn.jowin.dao;

import java.util.List;

import cn.jowin.entity.Room;

public interface RoomDao {
	void saveRoom(Room room);
	Room findRoomById(String roomId);
	Room findRoomByName(String roomName);
	List<Room> findRoomByCompanyId (String companyId);
	void deleteRoomById(String roomId);
	void updateRoomById(Room room);
	
}
