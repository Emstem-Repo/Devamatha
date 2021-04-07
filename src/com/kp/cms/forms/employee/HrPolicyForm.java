package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.EmpHrPolicy;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

public class HrPolicyForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String policyName;
	private FormFile formFile;
	private String policyId;
	private List<EmpHrPolicy> policiesList;

	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public FormFile getFormFile() {
		return formFile;
	}
	public void setFormFile(FormFile formFile) {
		this.formFile = formFile;
	}
	public List<EmpHrPolicy> getPoliciesList() {
		return policiesList;
	}
	public void setPoliciesList(List<EmpHrPolicy> policiesList) {
		this.policiesList = policiesList;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.formFile = null;
		this.policyName = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}