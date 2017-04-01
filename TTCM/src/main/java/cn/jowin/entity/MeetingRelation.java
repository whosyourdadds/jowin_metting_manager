package cn.jowin.entity;

import java.io.Serializable;

public class MeetingRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3830200804365184573L;
	
	
	private String userId;
	private String meetingId;
	private Long updateTime;
	private String hostUser;
	private String vipUser;
	/**
	 * 这个变量判断是否删除，没删除则为NULL，删除则有值
	 */
	private String isDelete;
	
	private String userName;
	private String userEmail;
	private String userModile;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserModile() {
		return userModile;
	}
	public void setUserModile(String userModile) {
		this.userModile = userModile;
	}
	public MeetingRelation(String userId, String meetingId, Long updateTime, String hostUser, String vipUser,
			String isDelete, String userName, String userEmail, String userModile) {
		super();
		this.userId = userId;
		this.meetingId = meetingId;
		this.updateTime = updateTime;
		this.hostUser = hostUser;
		this.vipUser = vipUser;
		this.isDelete = isDelete;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userModile = userModile;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getHostUser() {
		return hostUser;
	}
	public void setHostUser(String hostUser) {
		this.hostUser = hostUser;
	}
	public String getVipUser() {
		return vipUser;
	}
	public void setVipUser(String vipUser) {
		this.vipUser = vipUser;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public MeetingRelation() {
		super();
	}
	
	@Override
	public String toString() {
		return "MeetingRelation [userId=" + userId + ", meetingId=" + meetingId + ", updateTime=" + updateTime
				+ ", hostUser=" + hostUser + ", vipUser=" + vipUser + ", isDelete=" + isDelete + ", userName="
				+ userName + ", userEmail=" + userEmail + ", userModile=" + userModile + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostUser == null) ? 0 : hostUser.hashCode());
		result = prime * result + ((isDelete == null) ? 0 : isDelete.hashCode());
		result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userModile == null) ? 0 : userModile.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((vipUser == null) ? 0 : vipUser.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeetingRelation other = (MeetingRelation) obj;
		if (hostUser == null) {
			if (other.hostUser != null)
				return false;
		} else if (!hostUser.equals(other.hostUser))
			return false;
		if (isDelete == null) {
			if (other.isDelete != null)
				return false;
		} else if (!isDelete.equals(other.isDelete))
			return false;
		if (meetingId == null) {
			if (other.meetingId != null)
				return false;
		} else if (!meetingId.equals(other.meetingId))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userModile == null) {
			if (other.userModile != null)
				return false;
		} else if (!userModile.equals(other.userModile))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (vipUser == null) {
			if (other.vipUser != null)
				return false;
		} else if (!vipUser.equals(other.vipUser))
			return false;
		return true;
	}
	
	
	
}
