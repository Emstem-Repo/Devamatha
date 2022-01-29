package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;

public class ExamRevaluationStatusUpdateForm extends BaseActionForm {
	 private String registerNo;
	 private List<ExamRevaluationApplicationTO> revAppToList;
	 private String contactEmail;
	 private String studentName;
	 private String courseName;
	 private String semType;
	 private String termNo;
	 private String mobileNo;
	 
	
	public String getRegisterNo() {
		return registerNo;
	}
	
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public List<ExamRevaluationApplicationTO> getRevAppToList() {
		return revAppToList;
	}

	public void setRevAppToList(List<ExamRevaluationApplicationTO> revAppToList) {
		this.revAppToList = revAppToList;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSemType() {
		return semType;
	}

	public void setSemType(String semType) {
		this.semType = semType;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void resetFields() {
		this.registerNo=null;
		this.revAppToList=null;
	}
}
