package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.PhdVoucherNumberTO;

public class PhdVoucherNumberForm extends BaseActionForm {	

	private int id;
	private String financialYear;
	private String startNo;
	private String currentNo;
	private String currentYear;
	private List<PhdVoucherNumberTO> phdVoucherNumberList;
	

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		String formName=request.getParameter(CMSConstants.FORMNAME);
		ActionErrors errors=super.validate(mapping, request,formName);
		return errors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request){
		super.reset(mapping, request);
	}
	
	public void clearPage() {
		this.id=0;
		this.financialYear=null;
		this.startNo=null;
		this.currentNo=null;
		this.currentYear="false";
		this.phdVoucherNumberList=null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public String getCurrentNo() {
		return currentNo;
	}

	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}
	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public List<PhdVoucherNumberTO> getPhdVoucherNumberList() {
		return phdVoucherNumberList;
	}

	public void setPhdVoucherNumberList(
			List<PhdVoucherNumberTO> phdVoucherNumberList) {
		this.phdVoucherNumberList = phdVoucherNumberList;
	}
}
