package com.kp.cms.forms.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.pettycash.LastRceiptNumberTo;

public class LastReceiptNumberForm extends BaseActionForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String receiptNo;
	private String academicYear;
	private int id;
	private String method;
	private List<LastRceiptNumberTo> receiptNumberList;
	public List<FinancialYearTO> financilYearList;
	

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getId() {
		return id;
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

	public List<LastRceiptNumberTo> getReceiptNumberList() {
		return receiptNumberList;
	}

	public void setReceiptNumberList(List<LastRceiptNumberTo> receiptNumberList) {
		this.receiptNumberList = receiptNumberList;
	}

	public List<FinancialYearTO> getFinancilYearList() {
		return financilYearList;
	}

	public void setFinancilYearList(List<FinancialYearTO> financilYearList) {
		this.financilYearList = financilYearList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clear()
	{
		this.receiptNo=null;
		this.academicYear=null;
	}

}
