package com.kp.cms.to.phd;

import java.io.Serializable;

public class PhdInternalGuideTO implements Serializable{
	/**
	 * Dec 23, 2009
	 * Created By 9Elements Team
	 */
	
	private int id;
	private String employeeId;
	private String disciplineId;
	private String employeeName;
	private String disciplineName;
	private String dateOfAward;
	private String empanelmentNo;
	private int noMphilScolars;
	private int noPhdScolars;
	private int noPhdScolarOutside;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDisciplineId() {
		return disciplineId;
	}
	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}
	public String getDateOfAward() {
		return dateOfAward;
	}
	public void setDateOfAward(String dateOfAward) {
		this.dateOfAward = dateOfAward;
	}
	public String getEmpanelmentNo() {
		return empanelmentNo;
	}
	public void setEmpanelmentNo(String empanelmentNo) {
		this.empanelmentNo = empanelmentNo;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDisciplineName() {
		return disciplineName;
	}
	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
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
	
	
	
	
	
}
