package com.kp.cms.forms.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EligibilityCriteriaTO;

public class EligibilityCriteriaForm extends BaseActionForm{
	private int id;
	private String totalPercentage;
	private String percentageWithoutLanguage;
	private String[] selectedSubjects;
	private Map<Integer, String> subjectMap;
	private EligibilityCriteriaTO eligibilityCriteriaTO;
	private String perWithoutMandatory;
	
	
	public int getId() {
		return id;
	}
	public String getTotalPercentage() {
		return totalPercentage;
	}
	public String getPercentageWithoutLanguage() {
		return percentageWithoutLanguage;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}
	public void setPercentageWithoutLanguage(String percentageWithoutLanguage) {
		this.percentageWithoutLanguage = percentageWithoutLanguage;
	}
	public String[] getSelectedSubjects() {
		return selectedSubjects;
	}
	public void setSelectedSubjects(String[] selectedSubjects) {
		this.selectedSubjects = selectedSubjects;
	}
	
	public EligibilityCriteriaTO getEligibilityCriteriaTO() {
		return eligibilityCriteriaTO;
	}
	public void setEligibilityCriteriaTO(EligibilityCriteriaTO eligibilityCriteriaTO) {
		this.eligibilityCriteriaTO = eligibilityCriteriaTO;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		selectedSubjects = null;
		totalPercentage = null;
		percentageWithoutLanguage = null;
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setYear(null);
		subjectMap = null;
		id = 0;
		perWithoutMandatory = "no";
		
	}	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getPerWithoutMandatory() {
		return perWithoutMandatory;
	}
	public void setPerWithoutMandatory(String perWithoutMandatory) {
		this.perWithoutMandatory = perWithoutMandatory;
	}

	
}
