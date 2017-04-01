package cn.jowin.service.imp;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jowin.dao.RoomDao;
import cn.jowin.entity.Room;
import cn.jowin.service.MeetingRelationService;
import cn.jowin.service.MeetingService;
import cn.jowin.service.RoomRelationService;
import cn.jowin.service.RoomService;
import cn.jowin.service.exception.ServiceException;
import cn.jowin.util.Md5;

@Service("roomService")
public class RoomServiceImp implements RoomService {
	
	@Resource
	private RoomDao roomDao;
	
	@Resource
	private MeetingRelationService meetingRelationService;
	
	@Resource
	private RoomRelationService roomRelationService;
	
	@Resource 
	private MeetingService meetingService;
	
	@Transactional
	public Room regist(String companyId,String userId,String roomName, String roomPhone, Integer maxUser, String roomPassword, String roomAddress) {
		if(companyId==null||companyId.trim().isEmpty()){
			throw new ServiceException("公司ID不能为空");
		}if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户(创建人)ID不能为空");
		}
		if(roomName==null||roomName.trim().isEmpty()){
			throw new ServiceException("房间名称不能为空");
		}
		if(maxUser==null){
			maxUser=0;
		}
		if(roomAddress==null||roomAddress.trim().isEmpty()){
			throw new ServiceException("房间地址不能为空");
		}
		String uuid=UUID.randomUUID().toString();
		String pwd = roomPassword;
		if(!(roomPassword == null ||roomPassword.trim().isEmpty())){
			pwd = Md5.saltMd5(roomPassword);
		}
		Room room = new Room(uuid, null, companyId, userId, 
				null, roomName, roomPhone, null, "空闲", 
				null, pwd, roomAddress, null, null, null, null,
				null,null,null,maxUser);
		roomDao.saveRoom(room);
		return room;
	}
	
	
	/**
	 * 删除房间需要修改
	 */
	@Transactional
	public Room deleteRoomById(String roomId) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("房间ID不能为空");
		}
		Room room = roomDao.findRoomById(roomId);
		if(room==null){
			throw new ServiceException("没有该房间");
		}
		List<String> meetingIdList = roomRelationService.findRoomByRoomId(roomId);
		for(String meetingId:meetingIdList){
			meetingRelationService.deleteMeetingRelationByMeetingId(meetingId);
		}
		
		roomRelationService.deleteRoomRelationByRoomId(roomId);
		meetingService.deleteMeetingByRoomId(roomId);
		roomDao.deleteRoomById(roomId);
		room = roomDao.findRoomById(roomId);
		
		if(room!=null){
			throw new ServiceException("删除房间失败，请重试");
		}
		return room;
	}
	
	@Transactional
	public Room updateRoomById(String roomId, String roomName, String roomPhone, 
			Integer maxUser, String roomPassword,String roomAddress) {
		if(roomId==null){
			throw new ServiceException("房间Id不能为空");
		}
		
		Room room = roomDao.findRoomById(roomId);
		if(room==null){
			throw new ServiceException("房间Id不存在");
		}
		int index= 0;
		if(!(roomName==null||roomName.trim().isEmpty())){
			room.setRoomName(roomName);
			++index;
		}
		if(!(roomPhone==null||roomPhone.trim().isEmpty())){
			room.setRoomPhone(roomPhone);
			++index;
		}
//		System.out.println(maxUser);
//		System.out.println(room.getMaxUser()!= maxUser);
		if(room.getMaxUser()!= maxUser){
			if(maxUser==null){
				maxUser=0;
			}
			room.setMaxUser(maxUser);
			++index;
		}
		if(!(roomPassword==null||roomPassword.trim().isEmpty())){
			room.setRoomPassword(roomPassword);
			++index;
		}
		if(!(roomAddress==null||roomAddress.trim().isEmpty())){
			room.setRoomAddress(roomAddress);
			++index;
		}
		if(index==0){
			System.out.println("房间没被修改");
			return room;
		}
		
		room.setUpdateTime(System.currentTimeMillis());
		roomDao.updateRoomById(room);
		Room roomCheck = roomDao.findRoomById(roomId);
		if(!roomCheck.equals(room)){
			throw new ServiceException("房间修改失败");
		}
		
		return room;
	}
	
	
	@Transactional
	public List<Room> findRoomByCompanyId(String companyId) {
		if(companyId==null || companyId.trim().isEmpty()){
			throw new ServiceException("公司ID不能为空");
		}
		List<Room> list = roomDao.findRoomByCompanyId(companyId);
		if(list == null){
			throw new ServiceException("没有房间请创建或该公司不存在");
		}
		return list;
	}
	
	@Transactional
	public Room findRoomById(String roomId) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("房间ID不能为空");
		}
		Room room = roomDao.findRoomById(roomId);
		if(room ==null){
			throw new ServiceException("没有查询到该房间");
		}
		
		return room;
	}
	
	@Transactional
	public Room findRoomByName(String roomName) {
		if(roomName==null||roomName.trim().isEmpty()){
			throw new ServiceException("房间名字不能为空");
		}
		Room room = roomDao.findRoomByName(roomName);
		if(room ==null){
			throw new ServiceException("没有查询到该房间");
		}
		
		return room;
	}

}
