package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.ExceptionTypeTO;

@SuppressWarnings("serial")
public class ExceptionTypesForm extends BaseActionForm {
	
	
	private int id;
	private int oldId;
	private String exceptionType;
	private String exceptionShortName;
	private List<ExceptionTypeTO> listExceptionType;

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionShortName(String exceptionShortName) {
		this.exceptionShortName = exceptionShortName;
	}

	public String getExceptionShortName() {
		return exceptionShortName;
	}

	public void setListExceptionType(List<ExceptionTypeTO> listExceptionType) {
		this.listExceptionType = listExceptionType;
	}

	public List<ExceptionTypeTO> getListExceptionType() {
		return listExceptionType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public int getOldId() {
		return oldId;
	}

	public void setOldId(int oldId) {
		this.oldId = oldId;
	}

	/**
	 * 
	 */
	public void resetFields() {
		this.exceptionType=null;
		this.exceptionShortName=null;
		this.oldId=0;
		this.listExceptionType=null;
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
