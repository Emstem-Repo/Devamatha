package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyExemptionTo;

public class ExamInvigilatorDutyExemptionForm extends BaseActionForm{
	private Map<Integer,String> deanaryMap;
	private Map<Integer,String> deptMap;
	private Map<Integer,String> workLocationMap;
	private Map<Integer,String> exemptionMap;
	private String deanaryId;
	private String deptId;
	private String exemptionId;
	private List<ExamInvigilatorDutyExemptionTo> list;
	private Map<Integer,Integer> examInvigilatorsDutyExemptionMap;
	
	
	public String getExemptionId() {
		return exemptionId;
	}
	public void setExemptionId(String exemptionId) {
		this.exemptionId = exemptionId;
	}
	public Map<Integer, String> getExemptionMap() {
		return exemptionMap;
	}
	public void setExemptionMap(Map<Integer, String> exemptionMap) {
		this.exemptionMap = exemptionMap;
	}
	public Map<Integer, String> getDeanaryMap() {
		return deanaryMap;
	}
	public void setDeanaryMap(Map<Integer, String> deanaryMap) {
		this.deanaryMap = deanaryMap;
	}
	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	public String getDeanaryId() {
		return deanaryId;
	}
	public void setDeanaryId(String deanaryId) {
		this.deanaryId = deanaryId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<ExamInvigilatorDutyExemptionTo> getList() {
		return list;
	}
	public void setList(List<ExamInvigilatorDutyExemptionTo> list) {
		this.list = list;
	}
	public Map<Integer, Integer> getExamInvigilatorsDutyExemptionMap() {
		return examInvigilatorsDutyExemptionMap;
	}
	public void setExamInvigilatorsDutyExemptionMap(
			Map<Integer, Integer> examInvigilatorsDutyExemptionMap) {
		this.examInvigilatorsDutyExemptionMap = examInvigilatorsDutyExemptionMap;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
