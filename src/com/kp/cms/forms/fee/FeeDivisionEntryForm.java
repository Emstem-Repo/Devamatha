package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeDivisionTO;

public class FeeDivisionEntryForm extends BaseActionForm {
	
	private String divisionName;	
	
	private String divisionId;
	
	private String divisionNameOriginal;
	
	private String method;
	
	

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	private List<FeeDivisionTO> feeDivisionToList;	
	

	public List<FeeDivisionTO> getFeeDivisionToList() {
		return feeDivisionToList;
	}

	public void setFeeDivisionToList(List<FeeDivisionTO> feeDivisionToList) {
		this.feeDivisionToList = feeDivisionToList;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	
	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionNameOriginal() {
		return divisionNameOriginal;
	}

	public void setDivisionNameOriginal(String divisionNameOriginal) {
		this.divisionNameOriginal = divisionNameOriginal;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void clearFields() {
		divisionId = null;
		divisionName = null;
		divisionNameOriginal = null;
	}


}
