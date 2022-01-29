package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Jan 16, 2010
 * Created By 9Elements Team
 */
public class UsersUtilBO extends ExamGenBO {
	
	private int employeeId;
	private int isTeachingStaff;
	
	private EmployeeUtilBO employeeUtilBO;
	
	

	public UsersUtilBO(int employeeId, int isTeachingStaff, String userId) {
		super();
		this.employeeId = employeeId;
		this.isTeachingStaff = isTeachingStaff;
		this.createdBy = userId;
		this.createdDate = new Date();
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeUtilBO getEmployeeUtilBO() {
		return employeeUtilBO;
	}

	public void setEmployeeUtilBO(EmployeeUtilBO employeeUtilBO) {
		this.employeeUtilBO = employeeUtilBO;
	}

	public int getIsTeachingStaff() {
		return isTeachingStaff;
	}

	public void setIsTeachingStaff(int isTeachingStaff) {
		this.isTeachingStaff = isTeachingStaff;
	}
	
	
}
