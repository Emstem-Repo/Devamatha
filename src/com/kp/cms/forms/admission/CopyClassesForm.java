package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CopyClassesTO;

public class CopyClassesForm extends BaseActionForm{
	
	private int id;
	private String fromYear;
	private String toYear;
	private List<CopyClassesTO> classesList;
	private String method;
	private String selectedCandidates[];
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<CopyClassesTO> getClassesList() {
		return classesList;
	}
	public void setClassesList(List<CopyClassesTO> classesList) {
		this.classesList = classesList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String[] getSelectedCandidates() {
		return selectedCandidates;
	}
	public void setSelectedCandidates(String[] selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
	public String getFromYear() {
		return fromYear;
	}
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	public String getToYear() {
		return toYear;
	}
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	/**
	 * This method is used to reset the formbean properties
	 */
	public void clear() {
		this.fromYear = null;
		this.toYear = null;
		this.id = 0;
		this.method = null;
		this.classesList=null;
		super.clear();
	}

}
