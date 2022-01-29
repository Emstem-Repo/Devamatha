package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.phd.PhdFeePaymentStatusTO;

public class PhdFeePaymentStatusForm extends BaseActionForm{
	
	private int id;
	private String registerNo;
	private int studentId;
	private String studentName;
    private String courseId;
    private String courseName;
	private String programTypeId;
	private String billNo;
	private String billDate;
	private String amount;
	private String feePaidOn;
	private String batch;
	private String year;
	private String concession;
	private String tempChecked;
	private String checked;
    private String[] selectedcourseId;
    private List<PhdFeePaymentStatusTO> studentDetailsList;
    private List<CourseTO> courseList;
    private List<ProgramTypeTO> programTypeList;
    private List<String> messageList;
    private int currentProgramType;
  
  
public void clearList() {
	this.id=0;
	this.selectedcourseId=null;
	super.setCourseId(null);
	super.setAcademicYear(null);
	this.year=null;
	this.batch=null;
	this.programTypeId=null;
	this.studentDetailsList=null;
                      }
public void clearList1() {
	super.setCourseId(null);
	this.studentDetailsList=null;
	this.currentProgramType=0;
                      }
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request){
	String formName = request.getParameter("formName");
	ActionErrors actionErrors = super.validate(mapping, request, formName);
	return actionErrors;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getRegisterNo() {
	return registerNo;
}
public void setRegisterNo(String registerNo) {
	this.registerNo = registerNo;
}
public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public String getStudentName() {
	return studentName;
}
public void setStudentName(String studentName) {
	this.studentName = studentName;
}
public String getCourseId() {
	return courseId;
}
public void setCourseId(String courseId) {
	this.courseId = courseId;
}
public String getCourseName() {
	return courseName;
}
public void setCourseName(String courseName) {
	this.courseName = courseName;
}
public String getProgramTypeId() {
	return programTypeId;
}
public void setProgramTypeId(String programTypeId) {
	this.programTypeId = programTypeId;
}
public String getBillNo() {
	return billNo;
}
public void setBillNo(String billNo) {
	this.billNo = billNo;
}
public String getBillDate() {
	return billDate;
}
public void setBillDate(String billDate) {
	this.billDate = billDate;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getFeePaidOn() {
	return feePaidOn;
}
public void setFeePaidOn(String feePaidOn) {
	this.feePaidOn = feePaidOn;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getBatch() {
	return batch;
}
public void setBatch(String batch) {
	this.batch = batch;
}
public String getTempChecked() {
	return tempChecked;
}
public void setTempChecked(String tempChecked) {
	this.tempChecked = tempChecked;
}
public String getChecked() {
	return checked;
}
public void setChecked(String checked) {
	this.checked = checked;
}
public String[] getSelectedcourseId() {
	return selectedcourseId;
}
public void setSelectedcourseId(String[] selectedcourseId) {
	this.selectedcourseId = selectedcourseId;
}
public List<PhdFeePaymentStatusTO> getStudentDetailsList() {
	return studentDetailsList;
}
public void setStudentDetailsList(List<PhdFeePaymentStatusTO> studentDetailsList) {
	this.studentDetailsList = studentDetailsList;
}
public List<CourseTO> getCourseList() {
	return courseList;
}
public void setCourseList(List<CourseTO> courseList) {
	this.courseList = courseList;
}
public List<ProgramTypeTO> getProgramTypeList() {
	return programTypeList;
}
public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
	this.programTypeList = programTypeList;
}
public List<String> getMessageList() {
	return messageList;
}
public void setMessageList(List<String> messageList) {
	this.messageList = messageList;
}
public String getConcession() {
	return concession;
}
public void setConcession(String concession) {
	this.concession = concession;
}
public int getCurrentProgramType() {
	return currentProgramType;
}
public void setCurrentProgramType(int currentProgramType) {
	this.currentProgramType = currentProgramType;
}


}
