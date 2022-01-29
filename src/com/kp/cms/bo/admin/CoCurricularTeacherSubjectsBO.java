package com.kp.cms.bo.admin;

import java.util.Date;

public class CoCurricularTeacherSubjectsBO implements java.io.Serializable{
	private int id;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private CoCurricularTeacherBO coCurricularTeacherBO;
	private Activity activity;
	
	public CoCurricularTeacherSubjectsBO()
	{
		
	}
	public CoCurricularTeacherSubjectsBO(int id)
	{
		this.id = id;
	}
	public CoCurricularTeacherSubjectsBO(int id,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate,
			Boolean isActive,CoCurricularTeacherBO coCurricularTeacherBO,Activity activity)
	{
		this.id =  id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.coCurricularTeacherBO = coCurricularTeacherBO;
		this.activity = activity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public CoCurricularTeacherBO getCoCurricularTeacherBO() {
		return coCurricularTeacherBO;
	}
	public void setCoCurricularTeacherBO(CoCurricularTeacherBO coCurricularTeacherBO) {
		this.coCurricularTeacherBO = coCurricularTeacherBO;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
}
