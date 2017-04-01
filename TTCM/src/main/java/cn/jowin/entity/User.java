package cn.jowin.entity;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1398408900319287222L;
	
	private	String id;
	private	String name;
	private	String email;
	private	String modile;
	private	String phone;
	private	String password;
	private	String isAdminstrator;
	private	String companyId;
	private	String meetingId;
	private	String powerId;
	private Long   updateTime;
	private String job;
	private String companyName;
	public User(String id, String name, String email, String modile, String phone, String password,
			String isAdminstrator, String companyId, String meetingId, String powerId, Long updateTime, String job,
			String companyName) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.modile = modile;
		this.phone = phone;
		this.password = password;
		this.isAdminstrator = isAdminstrator;
		this.companyId = companyId;
		this.meetingId = meetingId;
		this.powerId = powerId;
		this.updateTime = updateTime;
		this.job = job;
		this.companyName = companyName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAdminstrator == null) ? 0 : isAdminstrator.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
		result = prime * result + ((modile == null) ? 0 : modile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((powerId == null) ? 0 : powerId.hashCode());
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
		User other = (User) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdminstrator == null) {
			if (other.isAdminstrator != null)
				return false;
		} else if (!isAdminstrator.equals(other.isAdminstrator))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (meetingId == null) {
			if (other.meetingId != null)
				return false;
		} else if (!meetingId.equals(other.meetingId))
			return false;
		if (modile == null) {
			if (other.modile != null)
				return false;
		} else if (!modile.equals(other.modile))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (powerId == null) {
			if (other.powerId != null)
				return false;
		} else if (!powerId.equals(other.powerId))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", modile=" + modile + ", phone=" + phone
				+ ", password=" + password + ", isAdminstrator=" + isAdminstrator + ", companyId=" + companyId
				+ ", meetingId=" + meetingId + ", powerId=" + powerId + ", updateTime=" + updateTime + ", job=" + job
				+ ", companyName=" + companyName + "]";
	}
	public User() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getModile() {
		return modile;
	}
	public void setModile(String modile) {
		this.modile = modile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsAdminstrator() {
		return isAdminstrator;
	}
	public void setIsAdminstrator(String isAdminstrator) {
		this.isAdminstrator = isAdminstrator;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getPowerId() {
		return powerId;
	}
	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
}
