package com.kp.cms.forms.fee;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class FeePaidForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private List<FeePaymentTO> feePaymentList;
	private String divisionid;
	private String paidDate;
	
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the feePaymentList
	 */
	public List<FeePaymentTO> getFeePaymentList() {
		return feePaymentList;
	}
	/**
	 * @param feePaymentList the feePaymentList to set
	 */
	public void setFeePaymentList(List<FeePaymentTO> feePaymentList) {
		this.feePaymentList = feePaymentList;
	}
	
	public String getDivisionid() {
		return divisionid;
	}
	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}
	
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	/**
	 * 
	 */
	public void clear() {
		this.startDate = null;
		this.endDate = null;
		this.feePaymentList = null;
		this.divisionid = null;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		if(actionErrors.isEmpty()) {
			Date startDate = CommonUtil.ConvertStringToDate(this.startDate);
			Date endDate = CommonUtil.ConvertStringToDate(this.endDate);
			Date curdate = new Date();
// Removed the Future date validation in Fee Pay screen
			/*		if ((startDate.compareTo(curdate) == 1) ||(endDate.compareTo(curdate) == 1)) {
				actionErrors.add("error", new ActionError(CMSConstants.FEE_NO_FUTURE_DATE_));
			}
			*/
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				actionErrors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		
	return actionErrors;
	}

}
