package cn.jowin.entity;

import java.io.Serializable;

public class Room implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6800940901174655061L;
	private	String roomId;
	private	String meetingId;
	private	String companyId;
	private	String userId;
	private	String deviceId;
	private	String roomName;
	private	String roomPhone;
	private	String video;
	private	String roomStatus;
	private	String ciscoIp;
	private	String roomPassword;
	private	String roomAddress;
	private	String domain;
	private	Long   updateTime;
	private	String temperature;//สาฮย
	private	String hasBody;//?
	private	String longitude_and_latitude;
	private	String 	angency;
	private	String account;
	private Integer maxUser;
	
	
	

	
	
	public Integer getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}
	public Room(String roomId, String meetingId, String companyId, String userId, String deviceId, String roomName,
			String roomPhone, String video, String roomStatus, String ciscoIp, String roomPassword, String roomAddress,
			String domain, Long updateTime, String temperature, String hasBody, String longitude_and_latitude,
			String angency, String account, Integer maxUser) {
		super();
		this.roomId = roomId;
		this.meetingId = meetingId;
		this.companyId = companyId;
		this.userId = userId;
		this.deviceId = deviceId;
		this.roomName = roomName;
		this.roomPhone = roomPhone;
		this.video = video;
		this.roomStatus = roomStatus;
		this.ciscoIp = ciscoIp;
		this.roomPassword = roomPassword;
		this.roomAddress = roomAddress;
		this.domain = domain;
		this.updateTime = updateTime;
		this.temperature = temperature;
		this.hasBody = hasBody;
		this.longitude_and_latitude = longitude_and_latitude;
		this.angency = angency;
		this.account = account;
		this.maxUser = maxUser;
	}
	public String getLongitude_and_latitude() {
		return longitude_and_latitude;
	}
	public void setLongitude_and_latitude(String longitude_and_latitude) {
		this.longitude_and_latitude = longitude_and_latitude;
	}
	public String getAngency() {
		return angency;
	}
	public void setAngency(String angency) {
		this.angency = angency;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
//		System.out.println("----"+account);
		this.account = account;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomPhone() {
		return roomPhone;
	}
	public void setRoomPhone(String roomPhone) {
		this.roomPhone = roomPhone;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	public String getCiscoIp() {
		return ciscoIp;
	}
	public void setCiscoIp(String ciscoIp) {
		this.ciscoIp = ciscoIp;
	}
	public String getRoomPassword() {
		return roomPassword;
	}
	public void setRoomPassword(String roomPassword) {
		this.roomPassword = roomPassword;
	}
	public String getRoomAddress() {
		return roomAddress;
	}
	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHasBody() {
		return hasBody;
	}
	public void setHasBody(String hasBody) {
		this.hasBody = hasBody;
	}
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", meetingId=" + meetingId + ", companyId=" + companyId + ", userId=" + userId
				+ ", deviceId=" + deviceId + ", roomName=" + roomName + ", roomPhone=" + roomPhone + ", video=" + video
				+ ", roomStatus=" + roomStatus + ", ciscoIp=" + ciscoIp + ", roomPassword=" + roomPassword
				+ ", roomAddress=" + roomAddress + ", domain=" + domain + ", updateTime=" + updateTime
				+ ", temperature=" + temperature + ", hasBody=" + hasBody + ", longitude_and_latitude="
				+ longitude_and_latitude + ", angency=" + angency + ", account=" + account + ", maxUser=" + maxUser
				+ "]";
	}
	
	public Room() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((angency == null) ? 0 : angency.hashCode());
		result = prime * result + ((ciscoIp == null) ? 0 : ciscoIp.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((hasBody == null) ? 0 : hasBody.hashCode());
		result = prime * result + ((longitude_and_latitude == null) ? 0 : longitude_and_latitude.hashCode());
		result = prime * result + ((maxUser == null) ? 0 : maxUser.hashCode());
		result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
		result = prime * result + ((roomAddress == null) ? 0 : roomAddress.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
		result = prime * result + ((roomPassword == null) ? 0 : roomPassword.hashCode());
		result = prime * result + ((roomPhone == null) ? 0 : roomPhone.hashCode());
		result = prime * result + ((roomStatus == null) ? 0 : roomStatus.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((video == null) ? 0 : video.hashCode());
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
		Room other = (Room) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (angency == null) {
			if (other.angency != null)
				return false;
		} else if (!angency.equals(other.angency))
			return false;
		if (ciscoIp == null) {
			if (other.ciscoIp != null)
				return false;
		} else if (!ciscoIp.equals(other.ciscoIp))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (hasBody == null) {
			if (other.hasBody != null)
				return false;
		} else if (!hasBody.equals(other.hasBody))
			return false;
		if (longitude_and_latitude == null) {
			if (other.longitude_and_latitude != null)
				return false;
		} else if (!longitude_and_latitude.equals(other.longitude_and_latitude))
			return false;
		if (maxUser == null) {
			if (other.maxUser != null)
				return false;
		} else if (!maxUser.equals(other.maxUser))
			return false;
		if (meetingId == null) {
			if (other.meetingId != null)
				return false;
		} else if (!meetingId.equals(other.meetingId))
			return false;
		if (roomAddress == null) {
			if (other.roomAddress != null)
				return false;
		} else if (!roomAddress.equals(other.roomAddress))
			return false;
		if (roomId == null) {
			if (other.roomId != null)
				return false;
		} else if (!roomId.equals(other.roomId))
			return false;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		if (roomPassword == null) {
			if (other.roomPassword != null)
				return false;
		} else if (!roomPassword.equals(other.roomPassword))
			return false;
		if (roomPhone == null) {
			if (other.roomPhone != null)
				return false;
		} else if (!roomPhone.equals(other.roomPhone))
			return false;
		if (roomStatus == null) {
			if (other.roomStatus != null)
				return false;
		} else if (!roomStatus.equals(other.roomStatus))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		return true;
	}
	public String getRoomAddresss() {
		return roomAddress;
	}
	public void setRoomAddresss(String roomAddress) {
		this.roomAddress = roomAddress;
	}
	
}	
