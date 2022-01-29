package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.to.fee.PrintChalanTO;

public class BulkChallanPrintForm extends BaseActionForm {
	private String fromDate;
	private String toDate;
	private boolean print;
	private List<FeePaymentTO> feeToList;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public List<FeePaymentTO> getFeeToList() {
		return feeToList;
	}
	public void setFeeToList(List<FeePaymentTO> feeToList) {
		this.feeToList = feeToList;
	}
	public void resetFields() {
		this.fromDate=null;
		this.toDate=null;
		this.feeToList=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
}
