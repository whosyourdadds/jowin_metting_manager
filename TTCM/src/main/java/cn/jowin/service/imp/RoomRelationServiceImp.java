package cn.jowin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jowin.dao.RoomRelationDao;
import cn.jowin.entity.RoomRelation;
import cn.jowin.service.RoomRelationService;
import cn.jowin.service.exception.ServiceException;

@Service("roomRelationService")
public class RoomRelationServiceImp implements RoomRelationService {
	
	@Resource
	private RoomRelationDao roomRelationDao;

	@Transactional
	public RoomRelation saveRoomRelation(String roomId,String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("房间Id不能为空");
		}
		RoomRelation rr = new RoomRelation(meetingId, roomId, System.currentTimeMillis(),null);
		RoomRelation rrCheck = roomRelationDao.findRoomByRoomRelation(rr);
		if(rr.equals(rrCheck)){
			throw new ServiceException("信息已经存在");
		}
		roomRelationDao.saveRoomRelation(rr);
		RoomRelation roomRelation = roomRelationDao.findRoomByRoomRelation(rr);
		if(roomRelation==null){
			throw new ServiceException("房间关系表保存失败");
		}
		return rr;
	}

	@Transactional
	public List<String> findRoomByRoomId(String roomId) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("房间Id不能为空22");
		}
		List<String> rrList = roomRelationDao.findRoomByRoomId(roomId);
		return rrList;
	}

	@Transactional
	public List<String> findRoomByMeetingId(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空22");
		}
		List<String> rrList = roomRelationDao.findRoomByMeetingId(meetingId);
		if(rrList==null){
			throw new ServiceException("信息不存在2");
		}
		return rrList;
	}
	
	@Transactional
	public RoomRelation findRoomByRoomRelation(RoomRelation roomRelation) {
		if(roomRelation.getMeetingId()==null){
			throw new ServiceException("房间关系表id不能为空1");
		}
		if(roomRelation.getRoomId()==null){
			throw new ServiceException("房间关系表id不能为空2");
		}
		RoomRelation rr= roomRelationDao.findRoomByRoomRelation(roomRelation);
		return rr;
	}

	@Transactional
	public void deleteRoomRelation(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		List<String> roomIdList = roomRelationDao.findRoomByMeetingId(meetingId);
		if(roomIdList==null||roomIdList.isEmpty()){
			throw new ServiceException("没有找到会议关联");
		}
		Map<String,String>idAll = new HashMap<String,String>();
		for(String roomId:roomIdList){
			idAll.put("roomId", roomId);
			idAll.put("meetingId", meetingId);
			roomRelationDao.deleteRoomRelation(idAll);
			RoomRelation rr= new RoomRelation(meetingId,roomId,null,null);
			RoomRelation rrCheck = findRoomByRoomRelation(rr);
			if(rrCheck!=null||rr.equals(rrCheck)){
				throw new ServiceException("删除失败");
			}
		}
		System.out.println("删除房间关系表成功");
	}

	
	@Transactional
	public void updateRoomRelationByMeetingId(String meetingId, String roomId) {
		RoomRelation rr = new RoomRelation(meetingId, roomId, System.currentTimeMillis(),null);
		List<String> roomIdCheck=roomRelationDao.findRoomByMeetingId(meetingId);
		roomRelationDao.updateRoomRelationByMeetingId(rr);
		for(String roomID:roomIdCheck){
			if(!roomID.equals(roomId)){
				throw new ServiceException("房间关系表修改失败");
			}
		}
	}

	@Transactional
	public void updateIsDelete(String meetingId,Integer isDelete) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}

		Map<String,String> data = new HashMap<String, String>();
		data.put("meetingId", meetingId);
		data.put("isDelete", String.valueOf(isDelete));
		roomRelationDao.updateIsDelete(data);
		List<RoomRelation> rrList=roomRelationDao.findRoomRelationByMeetingId(meetingId);
		if(rrList==null||rrList.isEmpty()){
			throw new ServiceException("没有找到会议关联");
		}
		for(RoomRelation rr:rrList){
			if(rr.getIsDelete()==null||rr.getIsDelete().isEmpty()){
				throw new ServiceException("房间关系表删除失败");
			}
		}
	}
	
	@Transactional
	public void deleteRoomRelationByRoomId(String roomId) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("房间Id不能为空");
		}
		roomRelationDao.deleteRoomRelationByRoomId(roomId);
		List<String> meetingIdList = roomRelationDao.findRoomByRoomId(roomId);
		if(!(meetingIdList== null || meetingIdList.isEmpty())){
			throw new ServiceException("房间关系表删除失败");
		}
		
	}
	
	
	
	
	

}
