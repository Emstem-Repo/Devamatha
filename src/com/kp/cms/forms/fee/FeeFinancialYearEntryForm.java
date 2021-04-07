package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeFinancialYearTO;

public class FeeFinancialYearEntryForm extends BaseActionForm {	

	private static final long serialVersionUID = 1L;
	private int id;
	private String feeFinancialYear;
	private String feeFinancialYearSel;	
	private String isCurrent="false";
	private String isActive;
	private String method;
	private List<FeeFinancialYearTO> feeFinancialYearList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFeeFinancialYear() {
		return feeFinancialYear;
	}

	public void setFeeFinancialYear(String feeFinancialYear) {
		this.feeFinancialYear = feeFinancialYear;
	}

	public String getFeeFinancialYearSel() {
		return feeFinancialYearSel;
	}

	public void setFeeFinancialYearSel(String feeFinancialYearSel) {
		this.feeFinancialYearSel = feeFinancialYearSel;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<FeeFinancialYearTO> getFeeFinancialYearList() {
		return feeFinancialYearList;
	}

	public void setFeeFinancialYearList(
			List<FeeFinancialYearTO> feeFinancialYearList) {
		this.feeFinancialYearList = feeFinancialYearList;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		String formName=request.getParameter(CMSConstants.FORMNAME);
		ActionErrors errors=new ActionErrors();
		errors=super.validate(mapping, request,formName);
		return errors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request){
		super.reset(mapping, request);
	}
	
	public void clear() {
		this.feeFinancialYear=null;
		this.isActive=null;
	}
}
