package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.to.exam.KeyValueTO;

public class NewInternalMarksSupplementaryForm extends BaseActionForm {
	
	private String examId;
	private String schemeNo;
	private String registerNo;
	private String rollNo;
	private List<KeyValueTO> examList;
	private Map<Integer, String> courseMap;
	private Map<String, String> schemeMap;
	List<ExamInternalMarksSupplementaryTO> stuMarkList;
	private String examName;
	private String courseName;
	private int studentId;
	private String originalSchemeNo;
	private String msg;
	
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
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public List<KeyValueTO> getExamList() {
		return examList;
	}
	public void setExamList(List<KeyValueTO> examList) {
		this.examList = examList;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public Map<String, String> getSchemeMap() {
		return schemeMap;
	}
	public void setSchemeMap(Map<String, String> schemeMap) {
		this.schemeMap = schemeMap;
	}
	public List<ExamInternalMarksSupplementaryTO> getStuMarkList() {
		return stuMarkList;
	}
	public void setStuMarkList(List<ExamInternalMarksSupplementaryTO> stuMarkList) {
		this.stuMarkList = stuMarkList;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
		super.setCourseId(null);
		this.schemeNo=null;
		this.registerNo=null;
		this.rollNo=null;
		this.examId=null;
		this.examList=null;
		this.examName=null;
		this.courseName=null;
		this.studentId=0;
		this.msg=null;
	}
	public void resetRegisterNoOnly() {
		this.registerNo=null;
		this.studentId=0;
		this.msg=null;
	}
	public String getOriginalSchemeNo() {
		return originalSchemeNo;
	}
	public void setOriginalSchemeNo(String originalSchemeNo) {
		this.originalSchemeNo = originalSchemeNo;
	}
}
