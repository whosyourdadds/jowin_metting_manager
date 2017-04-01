package cn.jowin.entity;

import java.io.Serializable;

public class RoomRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5775993586724449184L;

	private String meetingId;
	private String roomId;
	private Long updateTime;
	/**
	 * 该变量和meetingRelation一样
	 */
	private String isDelete;
	
	
	public RoomRelation(String meetingId, String roomId, Long updateTime, String isDelete) {
		super();
		this.meetingId = meetingId;
		this.roomId = roomId;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "RoomRelation [meetingId=" + meetingId + ", roomId=" + roomId + ", updateTime=" + updateTime
				+ ", isDelete=" + isDelete + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isDelete == null) ? 0 : isDelete.hashCode());
		result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		RoomRelation other = (RoomRelation) obj;
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
		if (roomId == null) {
			if (other.roomId != null)
				return false;
		} else if (!roomId.equals(other.roomId))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	public RoomRelation() {
		super();
	}
	
}
