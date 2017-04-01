package cn.jowin.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jowin.dao.MeetingRelationDao;
import cn.jowin.entity.MeetingRelation;
import cn.jowin.entity.User;
import cn.jowin.service.MeetingRelationService;
import cn.jowin.service.UserService;
import cn.jowin.service.exception.ServiceException;
@Service("meetingRelationService")
public class MeetingRelationServiceImp implements MeetingRelationService {

	@Resource
	private MeetingRelationDao meetingRelationDao;
	
	@Resource
	private UserService userService;
	
	
	
	@Transactional
	public MeetingRelation saveMeetingRelation(String userId, String meetingId,String hostUser,String vipUser) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("�û�Id����Ϊ��");
		}
		User user = userService.findUserById(userId);
		
		MeetingRelation meetingRelation = new MeetingRelation(userId,meetingId,System.currentTimeMillis(),hostUser,vipUser,null,user.getName(),user.getEmail(),user.getModile());;
		MeetingRelation meetingRelationCheck=meetingRelationDao.findMeetingByMeetingRelation(meetingRelation);
		if(meetingRelationCheck!=null){
			return meetingRelation;
		}
		meetingRelationDao.saveMeetingRelation(meetingRelation);
		MeetingRelation mr = meetingRelationDao.findMeetingByMeetingRelation(meetingRelation);
		if(mr==null){
				throw new ServiceException("����ʧ��2");
		}
		return meetingRelation;
	}

	@Transactional
	public List<MeetingRelation> findMeetingByUserId(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("�û�Id����Ϊ��");
		}
		
		List<MeetingRelation> mr = meetingRelationDao.findMeetingByUserId(userId);
		if(mr==null){
			throw new ServiceException("���û�û�л���");
		}
		return mr;
	}

	@Transactional
	public List<MeetingRelation> findMeetingByMeetingId(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		List<MeetingRelation> mr = meetingRelationDao.findMeetingByMeetingId(meetingId);
		if(mr == null){
			throw new ServiceException("û�иû���");
		}
		return mr;
	}
	
	@Transactional
	public MeetingRelation findMeetingByMeetingRelation(MeetingRelation meetingRelation) {
		if(meetingRelation.getMeetingId()==null){
			throw new ServiceException("�����ϵ��id����Ϊ��1");
		}
		if(meetingRelation.getUserId()==null){
			throw new ServiceException("�����ϵ��id����Ϊ��2");
		}
		MeetingRelation mr= meetingRelationDao.findMeetingByMeetingRelation(meetingRelation);
		return mr;
	}
	
	@Transactional
	public MeetingRelation deleteMeetingRelation( String meetingId ,String userId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("�û�Id����Ϊ��");
		}
		MeetingRelation meetingRelation = new MeetingRelation();
		meetingRelation.setUserId(userId);
		meetingRelation.setMeetingId(meetingId);
		
		MeetingRelation mr = meetingRelationDao.findMeetingByMeetingRelation(meetingRelation);
		Map<String,String>idAll = new HashMap<String,String>();
		idAll.put("userId", userId);
		idAll.put("meetingId", meetingId);
		meetingRelationDao.deleteMeetingRelation(idAll);
		MeetingRelation mrCheck = meetingRelationDao.findMeetingByMeetingRelation(meetingRelation);
		
		if(mrCheck != null|| mr.equals(mrCheck)){
			throw new ServiceException("����û���ϵɾ��ʧ��");
		}
		System.out.println("ɾ�������ϵ��ɹ�");
		return mr;
	}
	
	//û�õ�,���ܻ����
//	@Transactional
//	public void updateMeetingRelationByMeetingId(String userId, String meetingId,String hostUser,String vipUser) {
//		MeetingRelation meetingRelation = new MeetingRelation(userId, meetingId, System.currentTimeMillis(), hostUser, vipUser,null);
//		List<MeetingRelation> mrList=meetingRelationDao.findMeetingByMeetingId(meetingId);
//		
//	}

	@Transactional
	public void updateIsDelete(String meetingId ,Integer isDelete) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		
		Map<String,String> data = new HashMap<String, String>();
		data.put("meetingId", meetingId);
		data.put("isDelete", String.valueOf(isDelete));
		meetingRelationDao.updateIsDelete(data);
		List<MeetingRelation> mrList=meetingRelationDao.findMeetingByMeetingId(meetingId);
		if(mrList==null||mrList.isEmpty()){
			throw new ServiceException("û���ҵ��������");
		}
		for(MeetingRelation mr:mrList){
			if(mr.getIsDelete()==null||mr.getIsDelete().isEmpty()){
				throw new ServiceException("�����ϵ��ɾ��ʧ��");
			}
		}
		
	}

	@Transactional
	public void deleteMeetingRelationByMeetingId(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("����Id����Ϊ��");
		}
		meetingRelationDao.deleteMeetingRelationByMeetingId(meetingId);
		
		List<MeetingRelation> mrList=meetingRelationDao.findMeetingByMeetingId(meetingId);
		
		if(!mrList.isEmpty()){
			throw new ServiceException("�����ϵ��ɾ��ʧ��");
		}
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
