package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class ChallanVerificationForm extends BaseActionForm {
	private String challanDate;
	private String applicationNo;
	private List<StudentTO> studentInfoList;
	
	
	public String getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	
	public List<StudentTO> getStudentInfoList() {
		return studentInfoList;
	}
	public void setStudentInfoList(List<StudentTO> studentInfoList) {
		this.studentInfoList = studentInfoList;
	}
	public void resetFields() {
		this.challanDate=null;
		this.applicationNo=null;
		this.setStudentInfoList(null);
		
	}

}
