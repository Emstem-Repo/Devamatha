package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.forms.pettycash.LastReceiptNumberForm;
import com.kp.cms.helpers.pettycash.LastReceiptNumberHelper;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.pettycash.LastRceiptNumberTo;
import com.kp.cms.transactions.pettycash.ILastReceiptnumbertransaction;
import com.kp.cms.transactionsimpl.pettycash.LastRceiptNumberTransactionImpl;

public class LastrceiptNumberHandler {

	
	private static final Log log = LogFactory.getLog(LastrceiptNumberHandler.class);
	
	 ILastReceiptnumbertransaction transaction=new LastRceiptNumberTransactionImpl(); 
	
	private LastrceiptNumberHandler()
	{
		
	}
		
		private static volatile LastrceiptNumberHandler receiptNumberHandler;
		
		
		public static LastrceiptNumberHandler getInstance()
		{
			if(receiptNumberHandler==null)
			{
				receiptNumberHandler = new LastrceiptNumberHandler();
				return receiptNumberHandler;
			}
			return receiptNumberHandler;
			
		}
		 public List<LastRceiptNumberTo> getReceiptNumberDetails()throws Exception{
			log.info("Start of getReceiptNumberDetails of LastrceiptNumberHandler");
			if(transaction!=null)
			{
				List<PcReceiptNumber> receiptNumberListBo = transaction.getReceiptnumberdetails();
				if(receiptNumberListBo!=null)
				{
				return LastReceiptNumberHelper.getInstance().covertBoListtoToList(receiptNumberListBo);	
				}
			}
			log.info("Existing getReceiptNumberDetails of LastrceiptNumberHandler");
			return new ArrayList<LastRceiptNumberTo>();	
		}
		
		
		 
		 public List<FinancialYearTO> getFinancialYearList() throws Exception
			{
			 log.info("Start of getFinancialYearList of LastrceiptNumberHandler");
				List<PcFinancialYear> finanicalYearListBo = transaction.getFinancialYearList();
				if(finanicalYearListBo!=null)
				{
					return LastReceiptNumberHelper.getInstance().convertToListToBo(finanicalYearListBo);		
				}
				log.info("Existing getFinancialYearList of LastrceiptNumberHandler");
				return new ArrayList<FinancialYearTO>();	
			}	 
		 
		 
		 
		 /**
			 * Check for duplicate on bill No and year
			 */
			public PcReceiptNumber getLastReceiptNumberYear(int year) throws Exception
			{
				log.info("Start of getLastReceiptNumberYear of LastrceiptNumberHandler");
				if(transaction!=null)
				{
					return transaction.getLastReceiptNumberYear(year);			
				}
				log.info("Exiting getLastReceiptNumberYear of LastrceiptNumberHandler");
				return new PcReceiptNumber();
			}
		 
			
			
			/**
			 * Used in add
			 */
			public boolean addLastReceiptNumber(LastReceiptNumberForm receiptNumberForm) throws Exception
			{
				log.info("Start of addLastReceiptNumber of LastrceiptNumberHandler");
				LastRceiptNumberTo lastreceiptnumberTo = new LastRceiptNumberTo();
				lastreceiptnumberTo.setReceiptNo(receiptNumberForm.getReceiptNo());
				lastreceiptnumberTo.setCreatedBy(receiptNumberForm.getUserId());
				lastreceiptnumberTo.setModifiedBy(receiptNumberForm.getUserId());
				lastreceiptnumberTo.setAcademicYear(receiptNumberForm.getAcademicYear());
					if(lastreceiptnumberTo!=null){
						PcReceiptNumber pcReceiptNumber=LastReceiptNumberHelper.getInstance().populateTotoBO(lastreceiptnumberTo);
						if(pcReceiptNumber!=null && transaction!=null){
							return transaction.addLastReceiptNumber(pcReceiptNumber);
						}
					}
				log.info("Exiting addLastReceiptNumber of LastrceiptNumberHandler");
				return false;
			}
			public boolean deleteLastReceiptNumber(int id, String userId) throws Exception
			{
				log.info("Start of deleteLastReceiptNumber of LastrceiptNumberHandler");
				if(transaction!=null){
				return transaction.deleteLastReceiptNumber(id, userId);
				}
				log.info("Exiting deleteLastReceiptNumber of LastrceiptNumberHandler");
				return false;
			}
			
			
			
			/**
			 * Used in reactivation
			 */
			public boolean reActivateLastReceiptNumber(int year, String userId)throws Exception
			{
				log.info("Start of reActivateLastReceiptNumber of LastrceiptNumberHandler");
				if(transaction!=null)
				{
					return transaction.reActivateLastReceiptNumber(year, userId);
				}
				log.info("Exiting reActivateLastReceiptNumber of LastrceiptNumberHandler");
				return false;
			}
			
			
			/**
			 * Get feeBill details on IS
			 */
			
			public LastRceiptNumberTo getLastReceiptNumberDetailsonId(int id)throws Exception
			{
				log.info("Start of getLastReceiptNumberDetailsonId of FeeBillNumberHandler");
				if(transaction!=null){
					PcReceiptNumber receiptNumber=transaction.getLastReceiptNumberDetailsonId(id);
					if(receiptNumber!=null){
						return LastReceiptNumberHelper.getInstance().populateBotoTOEdit(receiptNumber);
					}
				}
				log.info("Exiting getLastReceiptNumberDetailsonId of FeeBillNumberHandler");
				return new LastRceiptNumberTo();
			}
			
			
			public boolean updateLastReceiptNumber(LastReceiptNumberForm lastReceiptnumberForm)throws Exception{
				log.info("Start of updateLastReceiptNumber of FeeBillNumberHandler");
				LastRceiptNumberTo lastReceiptNumberTO=new LastRceiptNumberTo();
				if(lastReceiptnumberForm!=null){
					lastReceiptNumberTO.setId(lastReceiptnumberForm.getId());
					lastReceiptNumberTO.setReceiptNo(lastReceiptnumberForm.getReceiptNo());
					lastReceiptNumberTO.setAcademicYear(lastReceiptnumberForm.getAcademicYear());
					lastReceiptNumberTO.setModifiedBy(lastReceiptnumberForm.getUserId());
				}
				if(lastReceiptNumberTO!=null){
					PcReceiptNumber pcReceiptNumber=LastReceiptNumberHelper.getInstance().populateTotoBOUpdate(lastReceiptNumberTO);
					if(transaction!=null && pcReceiptNumber!=null)
					{
						return transaction.updateLastReceiptNumber(pcReceiptNumber);
					}
				}
				log.info("Exiting updateLastReceiptNumber of FeeBillNumberHandler");
				return false;
			}
			
			
/*		 
	public boolean addLastReceiptNumber(LastReceiptNumberForm lastReceiptNumberForm,String mode) throws Exception
	{
		log.debug("inside addLastReceiptNumber");
		//ISubReligionTransaction iSubReligionTransaction = SubReligionTransactionImpl.getInstance();
		IReceiptNumberTransaction receiptnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		boolean isAdded = false;
		PcReceiptNumber duplPcReceiptNumber = ReceiptNumberHelper.getInstance().populatePcReceiptNumberDataFromFormForDuplicateCheck(lastReceiptNumberForm,mode);
		//PcReceiptNumber duplPcReceiptNumber = ReceiptNumberHelper.getInstance().populatePcReceiptNumberDataFromForm(lastReceiptNumberForm,mode);
		
		
		//duplPcReceiptNumber is using to check the duplication. if there is duplication then it will return object
		
		duplPcReceiptNumber = receiptnumberTransaction.isPcReceiptnumberDuplicated(duplPcReceiptNumber,mode);
		
		if (duplPcReceiptNumber != null && duplPcReceiptNumber.getIsActive()) {
			throw new DuplicateException();
		} else if (duplPcReceiptNumber != null && !duplPcReceiptNumber.getIsActive()) {
			lastReceiptNumberForm.setDuplId(duplPcReceiptNumber.getId());
			throw new ReActivateException();
		}
		PcReceiptNumber pcReceiptNumber = new PcReceiptNumber();
		//this method will set all the form data to pcReceiptNumber for saving
		pcReceiptNumber = ReceiptNumberHelper.getInstance().populatePcReceiptNumberDataFromFormToSave(lastReceiptNumberForm,mode);  
									
		if ("Add".equalsIgnoreCase(mode)) {
			pcReceiptNumber.setCreatedDate(new Date());
			pcReceiptNumber.setLastModifiedDate(new Date());
			pcReceiptNumber.setCreatedBy(lastReceiptNumberForm.getUserId());
			pcReceiptNumber.setModifiedBy(lastReceiptNumberForm.getUserId());
		} else // is edit
		{
			pcReceiptNumber.setLastModifiedDate(new Date());
			pcReceiptNumber.setModifiedBy(lastReceiptNumberForm.getUserId());
		}
		isAdded = receiptnumberTransaction.addReceiptNumber(pcReceiptNumber, mode);
		log.debug("inside addSubReligion");
		return isAdded;
	}
	
	public boolean deleteReceiptNumber( int receiptNoId, Boolean activate, LastReceiptNumberForm lastReceiptNumberForm) throws Exception
	{
		log.debug("inside deleteReceiptnumber in Handler");
		IReceiptNumberTransaction receiptnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		
		boolean result = receiptnumberTransaction.deleteReceiptNumber(receiptNoId, activate, lastReceiptNumberForm);
		//same method is calling for activate & de-activate. so activate param is used
		log.debug("leaving deleteReceiptnumber in Handler");
		return result;
	}
	
	public int getMaxReceiptNumber()throws Exception{
		IReceiptNumberTransaction receiptnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		
		int result = receiptnumberTransaction.maxReceiptNumber();
		return result;
	}
	public  boolean financialyearList(LastReceiptNumberForm lastReceiptnumberForm)throws Exception{
		boolean isThere=false;
		List<Object> financialYearList=null;
		IReceiptNumberTransaction receiptnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		financialYearList = receiptnumberTransaction.financialYearList();
		isThere = ReceiptNumberHelper.getInstance().checkYearAlreadyPresent(financialYearList,lastReceiptnumberForm);
		
		return isThere;
	}
	public boolean checkYearIsCurrent(LastReceiptNumberForm lastReceiptNumberForm)throws Exception
	{
		boolean isCurrent=false;
		IReceiptNumberTransaction receiptNumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		//getting the curent financail year
		PcFinancialYear pcfinancialYear = receiptNumberTransaction.getFinancialYear();
		//String userFinanicalYear = lastReceiptNumberForm.getFinancialYear();
		//int finYearId = lastReceiptNumberForm.getFinYearId();
		//IReceiptNumberTransaction receiptnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		List<FinancialYearTO> finanCialYearList = lastReceiptNumberForm.getFinancilYearList();
		//Iterator<FinancialYearTO> finIt = finanCialYearList.listIterator();
		lastReceiptNumberForm.setFinancialYear(String.valueOf(pcfinancialYear.getId()));
		List<FinancialYearTO> listYears = lastReceiptNumberForm.getFinancilYearList();

	if(lastReceiptNumberForm.getFinancialYear().equals(pcfinancialYear.getFinancialYear())){
		isCurrent = true;
	}

		
		return isCurrent;
	}*/
}
