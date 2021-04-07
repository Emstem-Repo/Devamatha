package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeBillNumberTO;

public class FeeBillNumberForm extends BaseActionForm{
	
	private String billNo;
	private String academicYear;
	private int id;
	private String method;
	private String previousYear;
	private List<FeeBillNumberTO> feeBillNumberList;
	
	public List<FeeBillNumberTO> getFeeBillNumberList() {
		return feeBillNumberList;
	}
	public void setFeeBillNumberList(List<FeeBillNumberTO> feeBillNumberList) {
		this.feeBillNumberList = feeBillNumberList;
	}
	public int getId() {
		return id;
	}
	public String getPreviousYear() {
		return previousYear;
	}
	public void setPreviousYear(String previousYear) {
		this.previousYear = previousYear;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clear()
	{
		this.academicYear=null;
		this.billNo=null;
	}

}
