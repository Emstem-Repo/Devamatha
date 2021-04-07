package com.kp.cms.bo.admission;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.to.admin.ProgramTypeTO;



/**
 * Course generated by hbm2java
 */
public class PgAdmSubjectForRank implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private ProgramType programType;
	private Program program;
	private Course course;
	private UGCoursesBO uGCoursesBO;
	private ProgramTypeTO programTypeTO;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	public ProgramType getProgramType() {
		return programType;
	}
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public UGCoursesBO getuGCoursesBO() {
		return uGCoursesBO;
	}
	public void setuGCoursesBO(UGCoursesBO uGCoursesBO) {
		this.uGCoursesBO = uGCoursesBO;
	}
	public ProgramTypeTO getProgramTypeTO() {
		return programTypeTO;
	}
	public void setProgramTypeTO(ProgramTypeTO programTypeTO) {
		this.programTypeTO = programTypeTO;
	}


}
	
	