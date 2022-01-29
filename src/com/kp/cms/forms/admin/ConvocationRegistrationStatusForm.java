package com.kp.cms.forms.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.impl.xb.xmlschema.BaseAttribute;

import com.kp.cms.forms.BaseActionForm;

public class ConvocationRegistrationStatusForm extends BaseActionForm{
	private String registerNo;
	private String year;
	private int studentId;
	private String studentRegistration;
	private String guestPassCount;
	private boolean flag;
	private String passNo1;
	private String passNo2;
	private boolean flag1;
	private boolean flag2;
	private int convocationRegistrationId;
	private String courseName;
	private int courseid;
	private String cdate;
	private String timeType;
	
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentRegistration() {
		return studentRegistration;
	}
	public void setStudentRegistration(String studentRegistration) {
		this.studentRegistration = studentRegistration;
	}
	public String getGuestPassCount() {
		return guestPassCount;
	}
	public void setGuestPassCount(String guestPassCount) {
		this.guestPassCount = guestPassCount;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public void resetFields() {
		this.registerNo=null;
		this.year=null;
		this.flag=false;
		this.studentId=0;
		this.studentRegistration=null;
		this.guestPassCount=null;
		this.flag1=false;
		this.flag2=false;
		this.convocationRegistrationId=0;
		this.passNo1=null;
		this.passNo2=null;
		this.courseName=null;
		this.courseid=0;
		this.cdate=null;
		this.timeType=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getPassNo1() {
		return passNo1;
	}
	public void setPassNo1(String passNo1) {
		this.passNo1 = passNo1;
	}
	public String getPassNo2() {
		return passNo2;
	}
	public void setPassNo2(String passNo2) {
		this.passNo2 = passNo2;
	}
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}
	public boolean isFlag2() {
		return flag2;
	}
	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}
	public int getConvocationRegistrationId() {
		return convocationRegistrationId;
	}
	public void setConvocationRegistrationId(int convocationRegistrationId) {
		this.convocationRegistrationId = convocationRegistrationId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	
	

}
