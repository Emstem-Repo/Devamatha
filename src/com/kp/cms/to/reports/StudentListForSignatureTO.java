package com.kp.cms.to.reports;

import java.util.List;

import com.kp.cms.to.admin.SingleFieldMasterTO;

public class StudentListForSignatureTO {
	private String rollOrRegNo;
	private String studentName;
	private int slNo;
	private List<SingleFieldMasterTO> columnName;
	
	
	public String getRollOrRegNo() {
		return rollOrRegNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setRollOrRegNo(String rollOrRegNo) {
		this.rollOrRegNo = rollOrRegNo;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public List<SingleFieldMasterTO> getColumnName() {
		return columnName;
	}

	public void setColumnName(List<SingleFieldMasterTO> columnName) {
		this.columnName = columnName;
	}

	

}
