package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmployeeSearchTO;

public class EmployeeSearchForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String method;
	private String searchFor;
	private String searchBy;
	private List<EmployeeSearchTO> empSearchList;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSearchFor() {
		return searchFor;
	}
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public List<EmployeeSearchTO> getEmpSearchList() {
		return empSearchList;
	}
	public void setEmpSearchList(List<EmployeeSearchTO> empSearchList) {
		this.empSearchList = empSearchList;
	}
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.searchFor = null;
		this.searchBy = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
