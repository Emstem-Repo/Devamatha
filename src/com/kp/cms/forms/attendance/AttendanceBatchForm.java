package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;

public class AttendanceBatchForm extends BaseActionForm {

	private static final long serialVersionUID = 1L;
	private String[] classesId;
	private String subjectId;
	private String attendanceType;
	private String activityAttendance;
	private Map<Integer,String> attendanceTypes;
	private Map<Integer,String> activityMap;
	private String activityName;
	private String attTypeName;
	List<CreatePracticalBatchTO> batchList;
	private String className;
	private String subjectName;
	private String batchName;
	private String regNoFrom;
	private String regNoTo;
	private String batchId;
	List<CreatePracticalBatchTO> studentList;
	private int halfLength;
	private boolean checkBoxSelectAll;
	
	public String[] getClassesId() {
		return classesId;
	}
	public void setClassesId(String[] classesId) {
		this.classesId = classesId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}
	public String getActivityAttendance() {
		return activityAttendance;
	}
	public void setActivityAttendance(String activityAttendance) {
		this.activityAttendance = activityAttendance;
	}
	
	public Map<Integer, String> getAttendanceTypes() {
		return attendanceTypes;
	}
	public void setAttendanceTypes(Map<Integer, String> attendanceTypes) {
		this.attendanceTypes = attendanceTypes;
	}
	public Map<Integer, String> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(Map<Integer, String> activityMap) {
		this.activityMap = activityMap;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAttTypeName() {
		return attTypeName;
	}
	public void setAttTypeName(String attTypeName) {
		this.attTypeName = attTypeName;
	}
	public List<CreatePracticalBatchTO> getBatchList() {
		return batchList;
	}
	public void setBatchList(List<CreatePracticalBatchTO> batchList) {
		this.batchList = batchList;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public List<CreatePracticalBatchTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<CreatePracticalBatchTO> studentList) {
		this.studentList = studentList;
	}
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
		this.classesId=null;
		this.subjectId=null;
		this.attendanceType=null;
		this.activityAttendance=null;
		this.activityName=null;
		this.attTypeName=null;
		this.batchList=null;
		this.className=null;
		this.subjectName=null;
		this.batchName=null;
		this.regNoFrom=null;
		this.regNoTo=null;
		this.batchId=null;
	}
	/**
	 * 
	 */
	public void clearFields() {
		this.setBatchId(null);
		this.setBatchName(null);
		this.setRegNoFrom(null);
		this.setRegNoTo(null);
		this.setStudentList(null);
	}
	public boolean isCheckBoxSelectAll() {
		return checkBoxSelectAll;
	}
	public void setCheckBoxSelectAll(boolean checkBoxSelectAll) {
		this.checkBoxSelectAll = checkBoxSelectAll;
	}
	
}
