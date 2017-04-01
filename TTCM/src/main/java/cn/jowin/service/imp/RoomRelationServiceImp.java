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
			throw new ServiceException("����Id����Ϊ��");
		}
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		RoomRelation rr = new RoomRelation(meetingId, roomId, System.currentTimeMillis(),null);
		RoomRelation rrCheck = roomRelationDao.findRoomByRoomRelation(rr);
		if(rr.equals(rrCheck)){
			throw new ServiceException("��Ϣ�Ѿ�����");
		}
		roomRelationDao.saveRoomRelation(rr);
		RoomRelation roomRelation = roomRelationDao.findRoomByRoomRelation(rr);
		if(roomRelation==null){
			throw new ServiceException("�����ϵ����ʧ��");
		}
		return rr;
	}

	@Transactional
	public List<String> findRoomByRoomId(String roomId) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��22");
		}
		List<String> rrList = roomRelationDao.findRoomByRoomId(roomId);
		return rrList;
	}

	@Transactional
	public List<String> findRoomByMeetingId(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��22");
		}
		List<String> rrList = roomRelationDao.findRoomByMeetingId(meetingId);
		if(rrList==null){
			throw new ServiceException("��Ϣ������2");
		}
		return rrList;
	}
	
	@Transactional
	public RoomRelation findRoomByRoomRelation(RoomRelation roomRelation) {
		if(roomRelation.getMeetingId()==null){
			throw new ServiceException("�����ϵ��id����Ϊ��1");
		}
		if(roomRelation.getRoomId()==null){
			throw new ServiceException("�����ϵ��id����Ϊ��2");
		}
		RoomRelation rr= roomRelationDao.findRoomByRoomRelation(roomRelation);
		return rr;
	}

	@Transactional
	public void deleteRoomRelation(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		List<String> roomIdList = roomRelationDao.findRoomByMeetingId(meetingId);
		if(roomIdList==null||roomIdList.isEmpty()){
			throw new ServiceException("û���ҵ��������");
		}
		Map<String,String>idAll = new HashMap<String,String>();
		for(String roomId:roomIdList){
			idAll.put("roomId", roomId);
			idAll.put("meetingId", meetingId);
			roomRelationDao.deleteRoomRelation(idAll);
			RoomRelation rr= new RoomRelation(meetingId,roomId,null,null);
			RoomRelation rrCheck = findRoomByRoomRelation(rr);
			if(rrCheck!=null||rr.equals(rrCheck)){
				throw new ServiceException("ɾ��ʧ��");
			}
		}
		System.out.println("ɾ�������ϵ��ɹ�");
	}

	
	@Transactional
	public void updateRoomRelationByMeetingId(String meetingId, String roomId) {
		RoomRelation rr = new RoomRelation(meetingId, roomId, System.currentTimeMillis(),null);
		List<String> roomIdCheck=roomRelationDao.findRoomByMeetingId(meetingId);
		roomRelationDao.updateRoomRelationByMeetingId(rr);
		for(String roomID:roomIdCheck){
			if(!roomID.equals(roomId)){
				throw new ServiceException("�����ϵ���޸�ʧ��");
			}
		}
	}

	@Transactional
	public void updateIsDelete(String meetingId,Integer isDelete) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}

		Map<String,String> data = new HashMap<String, String>();
		data.put("meetingId", meetingId);
		data.put("isDelete", String.valueOf(isDelete));
		roomRelationDao.updateIsDelete(data);
		List<RoomRelation> rrList=roomRelationDao.findRoomRelationByMeetingId(meetingId);
		if(rrList==null||rrList.isEmpty()){
			throw new ServiceException("û���ҵ��������");
		}
		for(RoomRelation rr:rrList){
			if(rr.getIsDelete()==null||rr.getIsDelete().isEmpty()){
				throw new ServiceException("�����ϵ��ɾ��ʧ��");
			}
		}
	}
	
	@Transactional
	public void deleteRoomRelationByRoomId(String roomId) {
		if(roomId==null||roomId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		roomRelationDao.deleteRoomRelationByRoomId(roomId);
		List<String> meetingIdList = roomRelationDao.findRoomByRoomId(roomId);
		if(!(meetingIdList== null || meetingIdList.isEmpty())){
			throw new ServiceException("�����ϵ��ɾ��ʧ��");
		}
		
	}
	
	
	
	
	

}
