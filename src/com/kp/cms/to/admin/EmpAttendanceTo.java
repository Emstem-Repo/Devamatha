package com.kp.cms.to.admin;

// Generated Sep 22, 2009 4:21:49 PM by Hibernate Tools 3.2.0.b9


/**
 * EmpAttendance generated by hbm2java
 */
public class EmpAttendanceTo implements java.io.Serializable,Comparable<EmpAttendanceTo> {

	private Integer id;
	private Integer employee;
	private String inTime;
	private String outTime;
	private String attendanceDate;
	private String empCode;
	private String fingerPrintId;
	private String empName;
	
	public EmpAttendanceTo() {
	}

	public EmpAttendanceTo(Integer id) {
		this.id = id;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getEmployee() {
		return employee;
	}

	public void setEmployee(Integer employee) {
		this.employee = employee;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFingerPrintId() {
		return fingerPrintId;
	}

	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public int compareTo(EmpAttendanceTo arg0) {
		if(arg0!=null && this!=null && arg0.getAttendanceDate()!=null 
				&& this.getAttendanceDate()!=null){
			
				return arg0.getAttendanceDate().compareTo(this.getAttendanceDate());
		}else
		return 0;
	}
	
	

}
