package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.bo.admin.HlFeePayment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelPaymentSlipForm;

public interface IHostelPaymentSlipTransaction {
	
	public HlApplicationForm getHlapplicationByQuery(String query) throws Exception;
	public List<HlDamage> getHostelDamageByHlApplicationFormId(int hlApplicationFormId,int hostelId)throws Exception;
	public HlApplicationForm getHlApplicationFormByBillNo(Integer billNo)throws ApplicationException;
	public boolean saveHlApplicationForm(HlApplicationForm hlApplicationForm) throws ApplicationException;
	public Integer getMaxBillNoFromHlApplicationForm()throws ApplicationException;
	public boolean saveHlFeePayment(HlFeePayment hlfeePayment) throws ApplicationException;
	public List<HlApplicationForm> getHlApplicationForm(String query)throws Exception;
	public List<HlDamage> getHostelDamageByQuery(String query) throws Exception;
	public boolean isValidBillNo(String billNo) throws Exception;
	public boolean markFinePaid(HostelPaymentSlipForm hostelPaymentSlipForm) throws Exception;	
	
}


