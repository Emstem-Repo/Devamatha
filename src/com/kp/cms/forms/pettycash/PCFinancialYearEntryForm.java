package com.kp.cms.forms.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.PCFinancialYearTO;

public class PCFinancialYearEntryForm extends BaseActionForm {	

	private static final long serialVersionUID = 1L;
	private int id;
	private String pcFinancialYear;
	private String pcFinancialYearSel;	
	private String isCurrent="false";
	private String isActive;
	private String method;
	private List<PCFinancialYearTO> pcFinancialYearList;
	private PCFinancialYearTO pcFinancialYearTO;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPcFinancialYear() {
		return pcFinancialYear;
	}

	public void setPcFinancialYear(String pcFinancialYear) {
		this.pcFinancialYear = pcFinancialYear;
	}

	public String getPcFinancialYearSel() {
		return pcFinancialYearSel;
	}

	public void setPcFinancialYearSel(String pcFinancialYearSel) {
		this.pcFinancialYearSel = pcFinancialYearSel;
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

	public List<PCFinancialYearTO> getPcFinancialYearList() {
		return pcFinancialYearList;
	}

	public void setPcFinancialYearList(List<PCFinancialYearTO> pcFinancialYearList) {
		this.pcFinancialYearList = pcFinancialYearList;
	}
	
	public PCFinancialYearTO getPcFinancialYearTO() {
		return pcFinancialYearTO;
	}

	public void setPcFinancialYearTO(PCFinancialYearTO pcFinancialYearTO) {
		this.pcFinancialYearTO = pcFinancialYearTO;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		String formName=request.getParameter(CMSConstants.FORMNAME);
		ActionErrors errors=super.validate(mapping, request,formName);
		return errors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request){
		super.reset(mapping, request);
	}
	
	public void clear() {
		this.pcFinancialYear=null;
		this.isActive=null;
		this.pcFinancialYearList=null;
		this.pcFinancialYearTO=null;
	}
}
