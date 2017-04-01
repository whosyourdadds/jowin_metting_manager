package cn.jowin.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
public class Meeting implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1512318529825104460L;
	
	
	
	private String meetingId;
	private String roomId;
	private String userId;
	private Long startTime;
	private Long endTime;
	
	/**
	 * 会议主题
	 */
	private String subject;
	private String description;
	private String timezone;
	private Long startDateTime;
	private Long endDateTime;
	private String hostuserId;
	private String roomlink;
	private String recurrence;
	private Long reminder;
	private Long updateTime;
	private Long delayTime;
	
	/**
	 * 该变量为记录，分别对应：
	 * 			DELETE = 3;
	 *			OUT_DATE = 2;
	 *			IN_DATE = 1;
	 * @record
	 */
	private Integer record;
	
	/**
	 * 判断是否发送邮件
	 * 值为空则表示没有
	 */
	private String isEmail;
	
	
	
	public Meeting(String meetingId, String roomId, String userId, Long startTime, Long endTime, String subject,
			String description, String timezone, Long startDateTime, Long endDateTime, String hostuserId,
			String roomlink, String recurrence, Long reminder, Long updateTime, Long delayTime, Integer record,
			String isEmail) {
		super();
		this.meetingId = meetingId;
		this.roomId = roomId;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.subject = subject;
		this.description = description;
		this.timezone = timezone;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.hostuserId = hostuserId;
		this.roomlink = roomlink;
		this.recurrence = recurrence;
		this.reminder = reminder;
		this.updateTime = updateTime;
		this.delayTime = delayTime;
		this.record = record;
		this.isEmail = isEmail;
	}
	public String getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}
	public Integer getRecord() {
		return record;
	}
	public void setRecord(Integer record) {
		this.record = record;
	}
	public Long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}
	@Override
	public String toString() {
		return "Meeting [meetingId=" + meetingId + ", roomId=" + roomId + ", userId=" + userId + ", startTime="
				+ startTime + ", endTime=" + endTime + ", subject=" + subject + ", description=" + description
				+ ", timezone=" + timezone + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime
				+ ", hostuserId=" + hostuserId + ", roomlink=" + roomlink + ", recurrence=" + recurrence + ", reminder="
				+ reminder + ", updateTime=" + updateTime + ", delayTime=" + delayTime + ", record=" + record
				+ ", isEmail=" + isEmail + "]";
	}
	
	public Meeting() {
		super();
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public Long getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Long startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Long getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Long endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getHostuserId() {
		return hostuserId;
	}
	public void setHostuserId(String hostuserId) {
		this.hostuserId = hostuserId;
	}
	public String getRoomlink() {
		return roomlink;
	}
	public void setRoomlink(String roomlink) {
		this.roomlink = roomlink;
	}
	public String getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
	public Long getReminder() {
		return reminder;
	}
	public void setReminder(Long reminder) {
		this.reminder = reminder;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delayTime == null) ? 0 : delayTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((hostuserId == null) ? 0 : hostuserId.hashCode());
		result = prime * result + ((isEmail == null) ? 0 : isEmail.hashCode());
		result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
		result = prime * result + ((record == null) ? 0 : record.hashCode());
		result = prime * result + ((recurrence == null) ? 0 : recurrence.hashCode());
		result = prime * result + ((reminder == null) ? 0 : reminder.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
		result = prime * result + ((roomlink == null) ? 0 : roomlink.hashCode());
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((timezone == null) ? 0 : timezone.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Meeting other = (Meeting) obj;
		if (delayTime == null) {
			if (other.delayTime != null)
				return false;
		} else if (!delayTime.equals(other.delayTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (hostuserId == null) {
			if (other.hostuserId != null)
				return false;
		} else if (!hostuserId.equals(other.hostuserId))
			return false;
		if (isEmail == null) {
			if (other.isEmail != null)
				return false;
		} else if (!isEmail.equals(other.isEmail))
			return false;
		if (meetingId == null) {
			if (other.meetingId != null)
				return false;
		} else if (!meetingId.equals(other.meetingId))
			return false;
		if (record == null) {
			if (other.record != null)
				return false;
		} else if (!record.equals(other.record))
			return false;
		if (recurrence == null) {
			if (other.recurrence != null)
				return false;
		} else if (!recurrence.equals(other.recurrence))
			return false;
		if (reminder == null) {
			if (other.reminder != null)
				return false;
		} else if (!reminder.equals(other.reminder))
			return false;
		if (roomId == null) {
			if (other.roomId != null)
				return false;
		} else if (!roomId.equals(other.roomId))
			return false;
		if (roomlink == null) {
			if (other.roomlink != null)
				return false;
		} else if (!roomlink.equals(other.roomlink))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (timezone == null) {
			if (other.timezone != null)
				return false;
		} else if (!timezone.equals(other.timezone))
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
		return true;
	}
	
}
