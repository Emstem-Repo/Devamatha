package com.kp.cms.handlers.fee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.forms.fee.FeeBillNumberForm;
import com.kp.cms.helpers.fee.FeeBillNumberHelper;
import com.kp.cms.to.fee.FeeBillNumberTO;

import com.kp.cms.transactions.fee.IFeeBillNumberTransaction;
import com.kp.cms.transactionsimpl.fee.FeeBillNumberTransactionImpl;


public class FeeBillNumberHandler {
	
private static final Log log = LogFactory.getLog(FeeBillNumberHandler.class);
	
	private static FeeBillNumberHandler feeBillNumberHandler = null;
	IFeeBillNumberTransaction transaction=new FeeBillNumberTransactionImpl(); 

	private FeeBillNumberHandler() {
	}

	public static FeeBillNumberHandler getInstance() {
		if (feeBillNumberHandler == null) {
			feeBillNumberHandler = new FeeBillNumberHandler();
		}
		return feeBillNumberHandler;
	}
	
	public List<FeeBillNumberTO> getFeeBillNumberDetails()throws Exception
	{
		log.info("Start of getFeeBillNumberDetails of FeeBillNumberHandler");
		if(transaction!=null){
		List<FeeBillNumber> feeList=transaction.getFeeBillNumberDetails();
			if(feeList!=null){
				return FeeBillNumberHelper.getInstance().poupulateBOtoTO(feeList);
			}
		}
		log.info("End of getFeeBillNumberDetails of FeeBillNumberHandler");
		return new ArrayList<FeeBillNumberTO>();		
	}
	
	/**
	 * Used in add
	 */
	public boolean addFeeBillNumber(FeeBillNumberForm billNumberForm) throws Exception
	{
		log.info("Start of addFeeBillNumber of FeeBillNumberHandler");
		FeeBillNumberTO feeBillNumberTO=new FeeBillNumberTO();
			feeBillNumberTO.setBillNo(billNumberForm.getBillNo());
			feeBillNumberTO.setCreatedBy(billNumberForm.getUserId());
			feeBillNumberTO.setModifiedBy(billNumberForm.getUserId());
			feeBillNumberTO.setAcademicYear(billNumberForm.getAcademicYear());
			if(feeBillNumberTO!=null){
				FeeBillNumber feeBillNumber=FeeBillNumberHelper.getInstance().populateTotoBO(feeBillNumberTO);
				if(feeBillNumber!=null && transaction!=null){
					return transaction.addFeeBillNumber(feeBillNumber);
				}
			}
		log.info("End of addFeeBillNumber of FeeBillNumberHandler");
		return false;
	}
	
	/**
	 * Used while deleting
	 */
	
	public boolean deleteFeeBillNumber(int id, String userId) throws Exception
	{
		log.info("Start of deleteFeeBillNumber of FeeBillNumberHandler");
		if(transaction!=null){
		return transaction.deleteFeeBillNumber(id, userId);
		}
		log.info("End of deleteFeeBillNumber of FeeBillNumberHandler");
		return false;
	}
	/**
	 * Check for duplicate on bill No and year
	 */
	public FeeBillNumber getFeeBillNoYear(int year) throws Exception
	{
		log.info("Start of getFeeBillNoYear of FeeBillNumberHandler");
		if(transaction!=null)
		{
			return transaction.getFeeBillNoYear(year);			
		}
		log.info("End of getFeeBillNoYear of FeeBillNumberHandler");
		return new FeeBillNumber();
	}
	/**
	 * Used in reactivation
	 */
	public boolean reActivateFeeBillNumber(int year, String userId)throws Exception
	{
		log.info("Start of reActivateFeeBillNumber of FeeBillNumberHandler");
		if(transaction!=null)
		{
			return transaction.reActivateFeeBillNumber(year, userId);
		}
		log.info("End of reActivateFeeBillNumber of FeeBillNumberHandler");
		return false;
	}
	
	/**
	 * Get feeBill details on IS
	 */
	
	public FeeBillNumberTO getFeeBillNumberDetailsonId(int id)throws Exception
	{
		log.info("Start of getFeeBillNumberDetailsonId of FeeBillNumberHandler");
		if(transaction!=null){
			FeeBillNumber billNumber=transaction.getFeeBillNumberDetailsonId(id);
			if(billNumber!=null){
				return FeeBillNumberHelper.getInstance().populateBotoTOEdit(billNumber);
			}
		}
		log.info("End of getFeeBillNumberDetailsonId of FeeBillNumberHandler");
		return new FeeBillNumberTO();
	}
	/**
	 * Used while updating
	 */
	public boolean updateFeeBillNumber(FeeBillNumberForm feeBillNumberForm)throws Exception{
		log.info("Start of updateFeeBillNumber of FeeBillNumberHandler");
		FeeBillNumberTO billNumberTO=new FeeBillNumberTO();
		if(feeBillNumberForm!=null){
			billNumberTO.setId(feeBillNumberForm.getId());
			billNumberTO.setBillNo(feeBillNumberForm.getBillNo());
			billNumberTO.setAcademicYear(feeBillNumberForm.getAcademicYear());
			billNumberTO.setModifiedBy(feeBillNumberForm.getUserId());
		}
		if(billNumberTO!=null){
			FeeBillNumber billNumber=FeeBillNumberHelper.getInstance().populateTotoBOUpdate(billNumberTO);
			if(transaction!=null && billNumber!=null)
			{
				return transaction.updateFeeBillNumber(billNumber);
			}
		}
		log.info("End of updateFeeBillNumber of FeeBillNumberHandler");
		return false;
	}
}
