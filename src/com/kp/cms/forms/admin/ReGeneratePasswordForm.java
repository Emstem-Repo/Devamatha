package com.kp.cms.forms.admin;

import com.kp.cms.forms.BaseActionForm;

public class ReGeneratePasswordForm extends BaseActionForm {
	
	private boolean studentMailid;
	private boolean studentRollNo;
	private boolean studentRegNo;
	private boolean sameUseridPassword;
	private String sendMail;
	private String registerNoEntry;
	
	public boolean isStudentMailid() {
		return studentMailid;
	}
	public void setStudentMailid(boolean studentMailid) {
		this.studentMailid = studentMailid;
	}
	public boolean isStudentRollNo() {
		return studentRollNo;
	}
	public void setStudentRollNo(boolean studentRollNo) {
		this.studentRollNo = studentRollNo;
	}
	public boolean isStudentRegNo() {
		return studentRegNo;
	}
	public void setStudentRegNo(boolean studentRegNo) {
		this.studentRegNo = studentRegNo;
	}
	public boolean isSameUseridPassword() {
		return sameUseridPassword;
	}
	public void setSameUseridPassword(boolean sameUseridPassword) {
		this.sameUseridPassword = sameUseridPassword;
	}
	public String getSendMail() {
		return sendMail;
	}
	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}
	public String getRegisterNoEntry() {
		return registerNoEntry;
	}
	public void setRegisterNoEntry(String registerNoEntry) {
		this.registerNoEntry = registerNoEntry;
	}
	
	public void resetFields(){
		this.registerNoEntry=null;
		this.sameUseridPassword=false;
		this.studentMailid=false;
		this.studentRollNo=false;
		this.studentRegNo=false;
		this.sendMail=null;
	}
}
