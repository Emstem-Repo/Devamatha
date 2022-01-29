package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamEndDateTo;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;

public class ExamEndDateForm extends BaseActionForm{
	private static final long serialVersionUID = 1L;
	private int id;
	private String examId;
	private String endDate;
	private Map<Integer,String> examNameList;
	private String mode;
	private List<ExamEndDateTo> toList;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<ExamEndDateTo> getToList() {
		return toList;
	}
	public void setToList(List<ExamEndDateTo> toList) {
		this.toList = toList;
	}
	/**
	 * 
	 */
	public void resetFields(){
		this.examId=null;
		this.endDate=null;
		this.id=0;
		this.mode="add";
		this.toList=null;
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

}
