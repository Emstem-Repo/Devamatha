package com.kp.cms.forms.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.LastSlipNumberTo;
import com.kp.cms.to.pettycash.FinancialYearTO;

public class LastSlipNumberForm extends BaseActionForm{
	
	
	public static final long serialVersionUID = 1L;
	private String slipNo;
	private String academicYear;
	private int id;
	private String method;
	private List<LastSlipNumberTo> slipNumberList;
	public List<FinancialYearTO> financilYearList;
	
	/**
	 * @return the slipNo
	 */
	public String getSlipNo() {
		return slipNo;
	}
	/**
	 * @param slipNo the slipNo to set
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	/**
	 * @return the academicYear
	 */
	public String getAcademicYear() {
		return academicYear;
	}
	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the slipNumberList
	 */
	public List<LastSlipNumberTo> getSlipNumberList() {
		return slipNumberList;
	}
	/**
	 * @param slipNumberList the slipNumberList to set
	 */
	public void setSlipNumberList(List<LastSlipNumberTo> slipNumberList) {
		this.slipNumberList = slipNumberList;
	}
	/**
	 * @return the financilYearList
	 */
	public List<FinancialYearTO> getFinancilYearList() {
		return financilYearList;
	}
	/**
	 * @param financilYearList the financilYearList to set
	 */
	public void setFinancilYearList(List<FinancialYearTO> financilYearList) {
		this.financilYearList = financilYearList;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) 
	{
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
		
	}
	
	public void clear()
	{
		this.slipNo = null;
		this.academicYear = null; 
	}

}
