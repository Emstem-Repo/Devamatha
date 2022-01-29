package com.kp.cms.transactions.phd;

import java.util.List;

import com.kp.cms.forms.phd.PhdFeePaymentStatusForm;


public interface IPhdFeePaymentStatus {
	
	public List<Object[]> getFeePaymentStatus(PhdFeePaymentStatusForm feePaymentStatusForm) throws Exception;

	public List<Object[]> getCoursesByProgramTypes(String programTypeId) throws Exception;

	public String getcurrency(int parseInt);

}
