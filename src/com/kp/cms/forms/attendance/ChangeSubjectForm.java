package com.kp.cms.forms.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.ChangeSubjectTo;
import com.kp.cms.to.exam.KeyValueTO;

public class ChangeSubjectForm extends  BaseActionForm {
	private String fromDate;
	private String toDate;
	private String subjectCode;
	private String changedSubject;
	private List<ChangeSubjectTo> subjectList;
	private boolean flag;
	private List<AttendanceTO> classList1;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
	public void resetFields(){
		this.fromDate=null;
		this.toDate=null;
		this.subjectCode=null;
		this.flag=false;
		this.subjectList=null;
		this.changedSubject=null;
		this.classList1=null;
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
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	public String getChangedSubject() {
		return changedSubject;
	}
	public void setChangedSubject(String changedSubject) {
		this.changedSubject = changedSubject;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<ChangeSubjectTo> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<ChangeSubjectTo> subjectList) {
		this.subjectList = subjectList;
	}
	public List<AttendanceTO> getClassList1() {
		return classList1;
	}
	public void setClassList1(List<AttendanceTO> classList1) {
		this.classList1 = classList1;
	}
		

}
