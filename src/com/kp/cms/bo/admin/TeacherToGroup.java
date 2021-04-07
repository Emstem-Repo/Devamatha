package com.kp.cms.bo.admin;

import java.io.Serializable;
import java.util.Date;

public class TeacherToGroup implements Serializable{
	
	private int id;
	private Roles rolesId;
	private Users usersId;
	private String createdby;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	public TeacherToGroup()
	{
		
	}
	public TeacherToGroup(int id,Roles rolesId,Users usersId,String createdby,Date createdDate,
			                  String modifiedBy,Date lastModifiedDate,Boolean isActive){
		this.id=id;
		this.rolesId=rolesId;
		this.usersId=usersId;
		this.createdby=createdby;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.lastModifiedDate=lastModifiedDate;
		this.isActive=isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
   public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public Roles getRolesId() {
		return rolesId;
	}
	public void setRolesId(Roles rolesId) {
		this.rolesId = rolesId;
	}
	public Users getUsersId() {
		return usersId;
	}
	public void setUsersId(Users usersId) {
		this.usersId = usersId;
	}

	
}
