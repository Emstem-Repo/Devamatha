package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class PhdInternalGuideBo implements Serializable {
    
	private int id;
	private Employee employeeId;
	private DisciplineBo disciplineId;
	private Date dateAward;
	private String empanelmentNo;
	private int noMphilScolars;
	private int noPhdScolars;
	private int noPhdScolarOutside;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public PhdInternalGuideBo() {
	}

	public PhdInternalGuideBo(int id,Employee employeeId,DisciplineBo disciplineId,Date dateAward,String empanelmentNo,
			int noMphilScolars,int noPhdScolars,int noPhdScolarOutside,
			Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		
		this.id = id;
		this.employeeId=employeeId;
		this.disciplineId=disciplineId;
		this.dateAward=dateAward;
		this.empanelmentNo=empanelmentNo;
		this.noMphilScolars=noMphilScolars;
		this.noPhdScolars=noPhdScolars;
		this.noPhdScolarOutside=noPhdScolarOutside;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}

	public DisciplineBo getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(DisciplineBo disciplineId) {
		this.disciplineId = disciplineId;
	}

	public Date getDateAward() {
		return dateAward;
	}

	public void setDateAward(Date dateAward) {
		this.dateAward = dateAward;
	}

	public String getEmpanelmentNo() {
		return empanelmentNo;
	}

	public void setEmpanelmentNo(String empanelmentNo) {
		this.empanelmentNo = empanelmentNo;
	}

	public int getNoMphilScolars() {
		return noMphilScolars;
	}

	public void setNoMphilScolars(int noMphilScolars) {
		this.noMphilScolars = noMphilScolars;
	}

	public int getNoPhdScolars() {
		return noPhdScolars;
	}

	public void setNoPhdScolars(int noPhdScolars) {
		this.noPhdScolars = noPhdScolars;
	}

	public int getNoPhdScolarOutside() {
		return noPhdScolarOutside;
	}

	public void setNoPhdScolarOutside(int noPhdScolarOutside) {
		this.noPhdScolarOutside = noPhdScolarOutside;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	

}
