package com.kp.cms.forms.hostel;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.StudentReqStatusTO;

public class StudentReqStatusForm extends BaseActionForm{
	
	private String method;
	private StudentReqStatusTO studentDetailsTOList;
	private List<StudentReqStatusTO> list;
	private String hlId;
	
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public StudentReqStatusTO getStudentDetailsTOList() {
		return studentDetailsTOList;
	}
	public void setStudentDetailsTOList(StudentReqStatusTO studentDetailsTOList) {
		this.studentDetailsTOList = studentDetailsTOList;
	}
	public List<StudentReqStatusTO> getList() {
		return list;
	}
	public void setList(List<StudentReqStatusTO> list) {
		this.list = list;
	}
	public String getHlId() {
		return hlId;
	}
	public void setHlId(String hlId) {
		this.hlId = hlId;
	}

	public void resetFields(){
		this.hlId=null;
	}
	
	
}