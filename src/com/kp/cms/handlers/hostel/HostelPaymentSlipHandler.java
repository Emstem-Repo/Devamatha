package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.bo.admin.HlFeePayment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelPaymentSlipForm;
import com.kp.cms.helpers.hostel.HostelPaymentSlipHelper;
import com.kp.cms.to.hostel.HostelPaymentSlipTO;
import com.kp.cms.transactions.hostel.IHostelPaymentSlipTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelPaymentSlipTransactionImpl;

public class HostelPaymentSlipHandler {
	
	/**
	 * Singleton object of HostelPaymentSlipHandler
	 */
	private static volatile HostelPaymentSlipHandler hostelPaymentSlipHandler = null;
	private static final Log log = LogFactory.getLog(HostelPaymentSlipHandler.class);

	private HostelPaymentSlipHandler() {

	}

	/**
	 * return singleton object of HostelPaymentSlipHandler.
	 * 
	 * @return
	 */
	public static HostelPaymentSlipHandler getInstance() {
		if (hostelPaymentSlipHandler == null) {
			hostelPaymentSlipHandler = new HostelPaymentSlipHandler();
		}
		return hostelPaymentSlipHandler;
	}

	public HostelPaymentSlipTO getHostelPaymentSlipTO(HostelPaymentSlipForm hostelPaymentSlipForm)
			throws Exception {
		HostelPaymentSlipHelper helper = HostelPaymentSlipHelper.getInstance();
		IHostelPaymentSlipTransaction transaction =HostelPaymentSlipTransactionImpl.getInstance();
		HostelPaymentSlipTO hostelPaymentSlipTO = null;
		if(hostelPaymentSlipForm.getSearchBy().equals("1")){
			String query = helper.getHostelPaymentSlipQuery(hostelPaymentSlipForm);
			HlApplicationForm hlApplicationForm = transaction.getHlapplicationByQuery(query);
			if (hlApplicationForm != null) {
				hostelPaymentSlipTO = helper.convertBOtoTO(hlApplicationForm,hostelPaymentSlipForm);
			}
		}else{
			String query=helper.getHostelPaymentSlipQueryForFine(hostelPaymentSlipForm);
			List<HlDamage> hlDamages=transaction.getHostelDamageByQuery(query);
			if(hlDamages!=null && !hlDamages.isEmpty()){
				hostelPaymentSlipTO= helper.convertBotoToForFine(hlDamages,hostelPaymentSlipForm);
			}
		}
		return hostelPaymentSlipTO;
	}
    
	
	public HlApplicationForm getHlApplicationForm(HostelPaymentSlipForm hostelPaymentSlipForm)throws Exception {
	
		IHostelPaymentSlipTransaction transaction = new HostelPaymentSlipTransactionImpl();
		HlApplicationForm hlApplicationForm=null;
		if(hostelPaymentSlipForm.getBillNo().trim()!=null && !hostelPaymentSlipForm.getBillNo().trim().isEmpty())
		{
			Integer billNo=Integer.parseInt(hostelPaymentSlipForm.getBillNo());
	        hlApplicationForm=transaction.getHlApplicationFormByBillNo(billNo);
		}
	
		return hlApplicationForm;
	}
	public boolean setHostelFeePaidInHlApplicationForm(HlApplicationForm hlApplicationForm)throws Exception{
		
		IHostelPaymentSlipTransaction transaction = new HostelPaymentSlipTransactionImpl();
			if(hlApplicationForm!=null)
			{
				hlApplicationForm.setIsHostelFeePaid(true);
				boolean hlApplicationFormSaved=transaction.saveHlApplicationForm(hlApplicationForm);
				if(hlApplicationFormSaved)
				{	
					return true;
				}
			}
		return false;
		
	}
	
	
	public void setBillNoToHlApplicationForm(HostelPaymentSlipForm hostelPaymentSlipForm)throws Exception
	{
		HostelPaymentSlipHelper helper = HostelPaymentSlipHelper.getInstance();
		String query = helper.getHostelPaymentSlipQuery(hostelPaymentSlipForm);
		IHostelPaymentSlipTransaction transaction = new HostelPaymentSlipTransactionImpl();
		HlApplicationForm hlApplicationForm = transaction.getHlapplicationByQuery(query);
		if(hlApplicationForm.getBillNo()==null || hlApplicationForm.getBillNo()==0){
				if(hostelPaymentSlipForm.getBillNo().trim()!=null && !hostelPaymentSlipForm.getBillNo().trim().isEmpty()){
				Integer billNo=Integer.parseInt(hostelPaymentSlipForm.getBillNo());
				hlApplicationForm.setBillNo(billNo);
			}
		}
	}
	
	public List<HostelPaymentSlipTO> getBillNumbersTO(HostelPaymentSlipForm hostelPaymentSlipForm)throws Exception 
	{
		HostelPaymentSlipHelper helper = HostelPaymentSlipHelper.getInstance();
		IHostelPaymentSlipTransaction transaction = new HostelPaymentSlipTransactionImpl();
		List<HostelPaymentSlipTO> hostelPaymentSlipTO = null;
		if(hostelPaymentSlipForm.getSearchBy().equals("1")){
			String query = helper.getSearchQuery(hostelPaymentSlipForm);
			List<HlApplicationForm> hlApplicationForm = transaction.getHlApplicationForm(query);
			if (hlApplicationForm != null) {
				hostelPaymentSlipTO = helper.getBillNumbersTo(hlApplicationForm);
			}
		}else{
			String query=helper.getsearchQueryForFine(hostelPaymentSlipForm);
			List<HlDamage> hlDamage=transaction.getHostelDamageByQuery(query);
			if(hlDamage!=null){
				hostelPaymentSlipTO=helper.getBillNumbersToByFine(hlDamage);
			}
		}
		return hostelPaymentSlipTO;
	}	
}
