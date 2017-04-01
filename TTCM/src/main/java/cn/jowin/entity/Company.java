package cn.jowin.entity;

import java.io.Serializable;

public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5310084790703869800L;
	private	String companyId;
	private	String roomId;
	private String userId;
	private	String companyName;
	private String companyAddress;
	private	String companyPhone;
	private String companyFax;
	private String companyEmail;
	private String companyWebsite;
	private Long 	updateTime;
	private String logo;
	private String activate;
	private String subdomain;
	public Company(String companyId, String roomId, String userId, String companyName, String companyAddress,
			String companyPhone, String companyFax, String companyEmail, String companyWebsite, Long updateTime,
			String logo, String activate, String subdomain) {
		super();
		this.companyId = companyId;
		this.roomId = roomId;
		this.userId = userId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyPhone = companyPhone;
		this.companyFax = companyFax;
		this.companyEmail = companyEmail;
		this.companyWebsite = companyWebsite;
		this.updateTime = updateTime;
		this.logo = logo;
		this.activate = activate;
		this.subdomain = subdomain;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getActivate() {
		return activate;
	}
	public void setActivate(String activate) {
		this.activate = activate;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	
	public Company() {
		super();
	}
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", roomId=" + roomId + ", userId=" + userId + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress + ", companyPhone=" + companyPhone
				+ ", companyFax=" + companyFax + ", companyEmail=" + companyEmail + ", companyWebsite=" + companyWebsite
				+ ", updateTime=" + updateTime + ", logo=" + logo + ", activate=" + activate + ", Subdomain="
				+ subdomain + "]";
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyFax() {
		return companyFax;
	}
	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
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
		result = prime * result + ((subdomain == null) ? 0 : subdomain.hashCode());
		result = prime * result + ((activate == null) ? 0 : activate.hashCode());
		result = prime * result + ((companyAddress == null) ? 0 : companyAddress.hashCode());
		result = prime * result + ((companyEmail == null) ? 0 : companyEmail.hashCode());
		result = prime * result + ((companyFax == null) ? 0 : companyFax.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((companyPhone == null) ? 0 : companyPhone.hashCode());
		result = prime * result + ((companyWebsite == null) ? 0 : companyWebsite.hashCode());
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
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
		Company other = (Company) obj;
		if (subdomain == null) {
			if (other.subdomain != null)
				return false;
		} else if (!subdomain.equals(other.subdomain))
			return false;
		if (activate == null) {
			if (other.activate != null)
				return false;
		} else if (!activate.equals(other.activate))
			return false;
		if (companyAddress == null) {
			if (other.companyAddress != null)
				return false;
		} else if (!companyAddress.equals(other.companyAddress))
			return false;
		if (companyEmail == null) {
			if (other.companyEmail != null)
				return false;
		} else if (!companyEmail.equals(other.companyEmail))
			return false;
		if (companyFax == null) {
			if (other.companyFax != null)
				return false;
		} else if (!companyFax.equals(other.companyFax))
			return false;
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
		if (companyPhone == null) {
			if (other.companyPhone != null)
				return false;
		} else if (!companyPhone.equals(other.companyPhone))
			return false;
		if (companyWebsite == null) {
			if (other.companyWebsite != null)
				return false;
		} else if (!companyWebsite.equals(other.companyWebsite))
			return false;
		if (logo == null) {
			if (other.logo != null)
				return false;
		} else if (!logo.equals(other.logo))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	
} 
