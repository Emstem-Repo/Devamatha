package com.kp.cms.to.admin;

import java.io.Serializable;

public class DepartmentEntryTO implements Comparable<DepartmentEntryTO>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5226942588222288303L;
	private int id;
	private String name;
	private int empStreamId;
	private String empStreamName;
	private String isAcademic;
	private String code;
	private String webId;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmpStreamId(int empStreamId) {
		this.empStreamId = empStreamId;
	}
	public int getEmpStreamId() {
		return empStreamId;
	}
	public void setEmpStreamName(String empStreamName) {
		this.empStreamName = empStreamName;
	}
	public String getEmpStreamName() {
		return empStreamName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsAcademic() {
		return isAcademic;
	}
	public void setIsAcademic(String isAcademic) {
		this.isAcademic = isAcademic;
	}
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int compareTo(DepartmentEntryTO obj) {
		if(obj!=null &&  obj.getName()!=null ){
			return this.getName().compareTo(obj.getName());
	}else
		return 0;
}
	

}
