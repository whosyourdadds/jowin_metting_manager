package cn.jowin.entity;

import java.io.Serializable;

public class Power implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9092967557525634550L;
	private	String powerId;
	private	String userId;
	private	String foundRoom;
	private	String applyForUser;
	private	String updateRoom;
	private	String updateCompany;
	private	String setAdminstator;
	public String getPowerId() {
		return powerId;
	}
	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFoundRoom() {
		return foundRoom;
	}
	public void setFoundRoom(String foundRoom) {
		this.foundRoom = foundRoom;
	}
	public String getApplyForUser() {
		return applyForUser;
	}
	public void setApplyForUser(String applyForUser) {
		this.applyForUser = applyForUser;
	}
	public String getUpdateRoom() {
		return updateRoom;
	}
	public void setUpdateRoom(String updateRoom) {
		this.updateRoom = updateRoom;
	}
	public String getUpdateCompany() {
		return updateCompany;
	}
	public void setUpdateCompany(String updateCompany) {
		this.updateCompany = updateCompany;
	}
	public String getSetAdminstator() {
		return setAdminstator;
	}
	public void setSetAdminstator(String setAdminstator) {
		this.setAdminstator = setAdminstator;
	}
	public Power(String powerId, String userId, String foundRoom, String applyForUser, String updateRoom,
			String updateCompany, String setAdminstator) {
		super();
		this.powerId = powerId;
		this.userId = userId;
		this.foundRoom = foundRoom;
		this.applyForUser = applyForUser;
		this.updateRoom = updateRoom;
		this.updateCompany = updateCompany;
		this.setAdminstator = setAdminstator;
	}
	public Power() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applyForUser == null) ? 0 : applyForUser.hashCode());
		result = prime * result + ((foundRoom == null) ? 0 : foundRoom.hashCode());
		result = prime * result + ((powerId == null) ? 0 : powerId.hashCode());
		result = prime * result + ((setAdminstator == null) ? 0 : setAdminstator.hashCode());
		result = prime * result + ((updateCompany == null) ? 0 : updateCompany.hashCode());
		result = prime * result + ((updateRoom == null) ? 0 : updateRoom.hashCode());
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
		Power other = (Power) obj;
		if (applyForUser == null) {
			if (other.applyForUser != null)
				return false;
		} else if (!applyForUser.equals(other.applyForUser))
			return false;
		if (foundRoom == null) {
			if (other.foundRoom != null)
				return false;
		} else if (!foundRoom.equals(other.foundRoom))
			return false;
		if (powerId == null) {
			if (other.powerId != null)
				return false;
		} else if (!powerId.equals(other.powerId))
			return false;
		if (setAdminstator == null) {
			if (other.setAdminstator != null)
				return false;
		} else if (!setAdminstator.equals(other.setAdminstator))
			return false;
		if (updateCompany == null) {
			if (other.updateCompany != null)
				return false;
		} else if (!updateCompany.equals(other.updateCompany))
			return false;
		if (updateRoom == null) {
			if (other.updateRoom != null)
				return false;
		} else if (!updateRoom.equals(other.updateRoom))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Power [powerId=" + powerId + ", userId=" + userId + ", foundRoom=" + foundRoom + ", applyForUser="
				+ applyForUser + ", updateRoom=" + updateRoom + ", updateCompany=" + updateCompany + ", setAdminstator="
				+ setAdminstator + "]";
	}
	
	
}
