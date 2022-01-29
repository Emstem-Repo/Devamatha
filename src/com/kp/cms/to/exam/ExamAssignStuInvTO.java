package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamAssignStuInvTO implements Serializable,Comparable<ExamAssignStuInvTO> {

	private int id;
	private int invigilatorId;
	private int employeeId;
	private int examId;
	private String invigilatorType;
	private boolean isCheckedDummy;
	private String isChecked;

	public ExamAssignStuInvTO(int id, int employeeId, int examId,
			String invigilatorType, boolean isCheckedDummy,String isChecked) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.examId = examId;
		this.invigilatorType = invigilatorType;
		this.isCheckedDummy = isCheckedDummy;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvigilatorId() {
		return invigilatorId;
	}

	public void setInvigilatorId(int invigilatorId) {
		this.invigilatorId = invigilatorId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getInvigilatorType() {
		return invigilatorType;
	}

	public void setInvigilatorType(String invigilatorType) {
		this.invigilatorType = invigilatorType;
	}

	public void setIsCheckedDummy(boolean isCheckedDummy) {
		this.isCheckedDummy = isCheckedDummy;
	}

	public boolean getIsCheckedDummy() {
		return isCheckedDummy;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getIsChecked() {
		return isChecked;
	}

	@Override
	public int compareTo(ExamAssignStuInvTO arg0) {
		if(arg0!=null && this!=null && arg0.getInvigilatorType()!=null
				 && this.getInvigilatorType()!=null){
			return this.getInvigilatorType().compareTo(arg0.getInvigilatorType());
		}else
		return 0;
	}

}
