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
			throw new ServiceException("会议Id不能为空");
		}
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户Id不能为空");
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
				throw new ServiceException("保存失败2");
		}
		return meetingRelation;
	}

	@Transactional
	public List<MeetingRelation> findMeetingByUserId(String userId) {
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户Id不能为空");
		}
		
		List<MeetingRelation> mr = meetingRelationDao.findMeetingByUserId(userId);
		if(mr==null){
			throw new ServiceException("该用户没有会议");
		}
		return mr;
	}

	@Transactional
	public List<MeetingRelation> findMeetingByMeetingId(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		List<MeetingRelation> mr = meetingRelationDao.findMeetingByMeetingId(meetingId);
		if(mr == null){
			throw new ServiceException("没有该会议");
		}
		return mr;
	}
	
	@Transactional
	public MeetingRelation findMeetingByMeetingRelation(MeetingRelation meetingRelation) {
		if(meetingRelation.getMeetingId()==null){
			throw new ServiceException("会议关系表id不能为空1");
		}
		if(meetingRelation.getUserId()==null){
			throw new ServiceException("会议关系表id不能为空2");
		}
		MeetingRelation mr= meetingRelationDao.findMeetingByMeetingRelation(meetingRelation);
		return mr;
	}
	
	@Transactional
	public MeetingRelation deleteMeetingRelation( String meetingId ,String userId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户Id不能为空");
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
			throw new ServiceException("相关用户关系删除失败");
		}
		System.out.println("删除会议关系表成功");
		return mr;
	}
	
	//没用到,可能会废弃
//	@Transactional
//	public void updateMeetingRelationByMeetingId(String userId, String meetingId,String hostUser,String vipUser) {
//		MeetingRelation meetingRelation = new MeetingRelation(userId, meetingId, System.currentTimeMillis(), hostUser, vipUser,null);
//		List<MeetingRelation> mrList=meetingRelationDao.findMeetingByMeetingId(meetingId);
//		
//	}

	@Transactional
	public void updateIsDelete(String meetingId ,Integer isDelete) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		
		Map<String,String> data = new HashMap<String, String>();
		data.put("meetingId", meetingId);
		data.put("isDelete", String.valueOf(isDelete));
		meetingRelationDao.updateIsDelete(data);
		List<MeetingRelation> mrList=meetingRelationDao.findMeetingByMeetingId(meetingId);
		if(mrList==null||mrList.isEmpty()){
			throw new ServiceException("没有找到会议关联");
		}
		for(MeetingRelation mr:mrList){
			if(mr.getIsDelete()==null||mr.getIsDelete().isEmpty()){
				throw new ServiceException("会议关系表删除失败");
			}
		}
		
	}

	@Transactional
	public void deleteMeetingRelationByMeetingId(String meetingId) {
		if(meetingId==null||meetingId.trim().isEmpty()){
			throw new ServiceException("会议Id不能为空");
		}
		meetingRelationDao.deleteMeetingRelationByMeetingId(meetingId);
		
		List<MeetingRelation> mrList=meetingRelationDao.findMeetingByMeetingId(meetingId);
		
		if(!mrList.isEmpty()){
			throw new ServiceException("会议关系表删除失败");
		}
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
