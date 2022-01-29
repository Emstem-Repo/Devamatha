package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ModerationMarksEntryTo;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.StudentMarkDetailsTO;

public class ModerationMarksEntryForm extends BaseActionForm {
	
	private String examType;
	private String examId;
	private String schemeNo;
	private String markType;
	private String isPrevious;
	Map<Integer, String> examMap;
	Map<String, String> markTypeMap;
	private String examName;
	private String regNo;
	private String rollNo;
	private String studentName;
	private int studentId;
	private int count;
	private String marksCardNo;
	private String entryType;
	
	List<ModerationMarksEntryTo> marksList;
	List<StudentMarkDetailsTO> oldMarksList;
	private String verifyPassword;
	private Boolean verifyGracing;
	private String subjectname;
	
	
	
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getMarkType() {
		return markType;
	}
	public void setMarkType(String markType) {
		this.markType = markType;
	}
	
	public String getIsPrevious() {
		return isPrevious;
	}
	public void setIsPrevious(String isPrevious) {
		this.isPrevious = isPrevious;
	}
	
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public Map<String, String> getMarkTypeMap() {
		return markTypeMap;
	}
	public void setMarkTypeMap(Map<String, String> markTypeMap) {
		this.markTypeMap = markTypeMap;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ModerationMarksEntryTo> getMarksList() {
		return marksList;
	}
	public void setMarksList(List<ModerationMarksEntryTo> marksList) {
		this.marksList = marksList;
	}
	public List<StudentMarkDetailsTO> getOldMarksList() {
		return oldMarksList;
	}
	public void setOldMarksList(List<StudentMarkDetailsTO> oldMarksList) {
		this.oldMarksList = oldMarksList;
	}
	public String getMarksCardNo() {
		return marksCardNo;
	}
	public void setMarksCardNo(String marksCardNo) {
		this.marksCardNo = marksCardNo;
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
	public String getVerifyPassword() {
		return verifyPassword;
	}
	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}
	/**
	 * 
	 */
	public void resetFields() {
		 this.examType=null;
		 this.examId=null;
		 this.schemeNo=null;
		 this.markType=null;
		 this.isPrevious="no";
		 this.examName=null;
		 this.regNo=null;
		 this.rollNo=null;
		 this.entryType=null;
		 this.studentName=null;
		 this.verifyGracing=false;
		 super.setAcademicYear(null);
	}

	/**
	 * 
	 */
	public void resetFields1() {
		this.studentName=null;
		this.studentId=0;
		this.marksList=null;
		this.marksCardNo=null;
		this.verifyGracing=false;
	}
	public Boolean getVerifyGracing() {
		return verifyGracing;
	}
	public void setVerifyGracing(Boolean verifyGracing) {
		this.verifyGracing = verifyGracing;
	}
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public String getEntryType() {
		return entryType;
	}
}

