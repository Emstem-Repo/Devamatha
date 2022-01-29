package com.kp.cms.to.admin;

import java.util.Date;
import java.util.List;

/**
 * Manages Transaction related to Religion.
 * @author prashanth.mh
 */
public class ReligionTO {
	private int religionId;
	private String religionName;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;	

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	private List<ReligionSectionTO> subreligions;
	public int getReligionId() {
		return religionId;
	}
	public void setReligionId(int religionId) {
		this.religionId = religionId;
	}
	public String getReligionName() {
		return religionName;
	}
	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}
	public List<ReligionSectionTO> getSubreligions() {
		return subreligions;
	}
	public void setSubreligions(List<ReligionSectionTO> subreligions) {
		this.subreligions = subreligions;
	}
}
