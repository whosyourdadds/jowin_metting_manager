package cn.jowin.service;

import java.util.List;

import cn.jowin.entity.Room;

public interface RoomService {
	Room regist(
			String companyId,String userId,
			String roomName,String roomPhone,Integer maxUser,
			String roomPassword,String roomAddress
			);
	
	List<Room> findRoomByCompanyId(String companyId);
	Room deleteRoomById(String roomId);
	Room updateRoomById(String roomId,String roomName,
			String roomPhone,Integer maxUser,String roomPassword,String roomAddress);
	Room findRoomById(String roomId);
	Room findRoomByName(String roomName);
}
