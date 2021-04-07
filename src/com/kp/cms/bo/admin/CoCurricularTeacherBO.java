package com.kp.cms.bo.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CoCurricularTeacherBO implements java.io.Serializable  {
	private int id;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Users users;
	private Set<CoCurricularTeacherSubjectsBO> coCurricularTeacherSubjectsBO = new HashSet<CoCurricularTeacherSubjectsBO>(0);
    
	public CoCurricularTeacherBO()
	{
		//no arg constructor 
	}
	public CoCurricularTeacherBO(int id)
	{
		this.id = id;
	}
	public CoCurricularTeacherBO(int id,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate,Boolean isActive
			,Users users,Set<CoCurricularTeacherSubjectsBO> coCurricularTeacherSubjectsBO)
	{
		this.id  = id;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.users = users;
		this.coCurricularTeacherSubjectsBO = coCurricularTeacherSubjectsBO;
		
		
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
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Set<CoCurricularTeacherSubjectsBO> getCoCurricularTeacherSubjectsBO() {
		return coCurricularTeacherSubjectsBO;
	}
	public void setCoCurricularTeacherSubjectsBO(
			Set<CoCurricularTeacherSubjectsBO> coCurricularTeacherSubjectsBO) {
		this.coCurricularTeacherSubjectsBO = coCurricularTeacherSubjectsBO;
	}
	
	
	
}
