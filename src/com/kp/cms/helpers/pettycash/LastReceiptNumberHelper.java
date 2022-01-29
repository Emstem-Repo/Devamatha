package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.pettycash.LastRceiptNumberTo;

public class LastReceiptNumberHelper {
	private static Log log = LogFactory.getLog(LastReceiptNumberHelper.class);
	public static volatile LastReceiptNumberHelper receiptNumberHelper = null;
	
	public static LastReceiptNumberHelper getInstance()
	{
		if(receiptNumberHelper==null)
		{
			receiptNumberHelper = new LastReceiptNumberHelper();
		return receiptNumberHelper;
		}
		return receiptNumberHelper;
	}
	
	public List<LastRceiptNumberTo> covertBoListtoToList(List<PcReceiptNumber> receiptNumberList) throws Exception
	{
		log.info("Start of covertBoListtoToList of LastReceiptNumberHelper");
		List<LastRceiptNumberTo> receiptNoToList = new ArrayList<LastRceiptNumberTo>();
		if(receiptNumberList!=null && !receiptNumberList.isEmpty())
		{
		Iterator<PcReceiptNumber> iterator = receiptNumberList.iterator();
		LastRceiptNumberTo receiptTO;
		while (iterator.hasNext()) {
			
			receiptTO = new LastRceiptNumberTo();
			PcReceiptNumber receiptNumberBo = iterator.next();
			
			receiptTO.setId(receiptNumberBo.getId());
			if(receiptNumberBo.getPcFinancialYear()!=null && receiptNumberBo.getPcFinancialYear().getIsActive()!=null
			&& receiptNumberBo.getPcFinancialYear().getIsActive()&& receiptNumberBo.getPcFinancialYear().getFinancialYear()!=null)
			{
				receiptTO.setAcademicYear(receiptNumberBo.getPcFinancialYear().getFinancialYear());
			}
			if(receiptNumberBo.getStartingNumber()!=null && !receiptNumberBo.getStartingNumber().isEmpty())
			{
				receiptTO.setReceiptNo(receiptNumberBo.getStartingNumber());
			}
			
			receiptNoToList.add(receiptTO);
		}
		}
		log.info("exiting covertBoListtoToList of LastReceiptNumberHelper");
		return receiptNoToList;
	}
	
	
	
	public List<FinancialYearTO> convertToListToBo(List<PcFinancialYear> finanicalYearListBo) throws Exception
	{
		log.info("Start of convertToListToBo of LastReceiptNumberHelper");
		List<FinancialYearTO> financialYearTolist = new ArrayList<FinancialYearTO>();
		if(finanicalYearListBo!=null && !finanicalYearListBo.isEmpty() )
		{	
		Iterator<PcFinancialYear> it = finanicalYearListBo.iterator();
			while(it.hasNext())
			{
			PcFinancialYear pcfinancailYear = it.next();
			FinancialYearTO yearTo = new FinancialYearTO();
			yearTo.setId(pcfinancailYear.getId());
			yearTo.setFinancialYear(pcfinancailYear.getFinancialYear());
			financialYearTolist.add(yearTo);
			}
		}
		log.info("Exiting convertToListToBo of LastReceiptNumberHelper");
		return financialYearTolist;
	}
	
	
	public PcReceiptNumber populateTotoBO(LastRceiptNumberTo lastReceiptNumberTO)throws Exception
	{
		log.info("Start of populateTotoBO of LastReceiptNumberHelper");
		PcReceiptNumber pcreceiptNumber=new PcReceiptNumber();
		if(lastReceiptNumberTO!=null){
			pcreceiptNumber.setStartingNumber(String.valueOf(lastReceiptNumberTO.getReceiptNo()));
	
		PcFinancialYear financialYear = new PcFinancialYear();
		financialYear.setId(Integer.parseInt(lastReceiptNumberTO.getAcademicYear()));
		pcreceiptNumber.setPcFinancialYear(financialYear);
		pcreceiptNumber.setCreatedBy(lastReceiptNumberTO.getCreatedBy());
		pcreceiptNumber.setModifiedBy(lastReceiptNumberTO.getModifiedBy());
		pcreceiptNumber.setCreatedDate(new Date());
		pcreceiptNumber.setLastModifiedDate(new Date());
		pcreceiptNumber.setIsActive(true);
		pcreceiptNumber.setCurrentNo(Integer.parseInt(lastReceiptNumberTO.getReceiptNo()));
	}
		log.info("Exiting populateTotoBO of LastReceiptNumberHelper");
		return pcreceiptNumber;
	}
	
	
	/**
	 * Used in editing
	 */
	public LastRceiptNumberTo populateBotoTOEdit(PcReceiptNumber receiptNumber)throws Exception
	{
		log.info("Start of populateBotoTOEdit of LastReceiptNumberHelper");
		LastRceiptNumberTo lastRceiptNumberTo=new LastRceiptNumberTo();
		if(receiptNumber!=null){
			lastRceiptNumberTo.setReceiptNo(receiptNumber.getStartingNumber()!=null ? receiptNumber.getStartingNumber():null);
			if(receiptNumber.getPcFinancialYear()!=null){
				if(receiptNumber.getPcFinancialYear().getFinancialYear()!=null){
					lastRceiptNumberTo.setAcademicYear(receiptNumber.getPcFinancialYear().getFinancialYear());
				}
				lastRceiptNumberTo.setFinacialYearID(receiptNumber.getPcFinancialYear().getId());
			}
			lastRceiptNumberTo.setId(receiptNumber.getId());
		}
		log.info("Exiting populateBotoTOEdit of LastReceiptNumberHelper");
		return lastRceiptNumberTo;
	}
	
	
	/**
	 * Used in update
	 */
	public PcReceiptNumber populateTotoBOUpdate(LastRceiptNumberTo lastRceiptNumberTo)throws Exception
	{
		log.info("Start of populateTotoBOUpdate of LastReceiptNumberHelper");
		PcReceiptNumber pcReceiptNumber = null;
		pcReceiptNumber=new PcReceiptNumber();
		pcReceiptNumber.setId(lastRceiptNumberTo.getId());
		pcReceiptNumber.setStartingNumber(String.valueOf(lastRceiptNumberTo.getReceiptNo()));
		PcFinancialYear financialYear = new PcFinancialYear();
		financialYear.setId(Integer.parseInt(lastRceiptNumberTo.getAcademicYear()));
		pcReceiptNumber.setPcFinancialYear(financialYear);
		pcReceiptNumber.setIsActive(true);
		pcReceiptNumber.setModifiedBy(lastRceiptNumberTo.getModifiedBy());
		pcReceiptNumber.setLastModifiedDate(new Date());
//		pcReceiptNumber.setCurrentNo(Integer.parseInt(lastRceiptNumberTo.getReceiptNo()));
		log.info("End of populateTotoBOUpdate of LastReceiptNumberHelper");
		return pcReceiptNumber;
	}	
	
/*	public PcReceiptNumber populatePcReceiptNumberDataFromFormToSave(LastReceiptNumberForm lastRecNumberForm,String mode)throws Exception 
	{
		log.debug("inside populatePcReceiptNumberDataFromForm in Helper");
		IReceiptNumberTransaction receipTnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		PcFinancialYear pcfinancialYear=null;
		if(lastRecNumberForm.getFinYearId()!=null)
		{
		pcfinancialYear = receipTnumberTransaction.getFinancialYear(lastRecNumberForm.getFinYearId());
		}
		else
		{
			pcfinancialYear = receipTnumberTransaction.getFinancialYear(lastRecNumberForm.getFinancialYear());
		}
		
		PcReceiptNumber pcReceiptnumber = new PcReceiptNumber();
		if("Add".equalsIgnoreCase(mode))
		{if 
			(pcfinancialYear!=null)
			pcReceiptnumber.setPcFinancialYear(pcfinancialYear);
			pcReceiptnumber.setStartingNumber(lastRecNumberForm.getLastReceiptNo());
			
			pcReceiptnumber.setIsActive(true);	
		}
		else{
				
				PcFinancialYear year = new PcFinancialYear();
				year.setId(lastRecNumberForm.getFinYearId());
				pcReceiptnumber.setPcFinancialYear(year);
				pcReceiptnumber.setStartingNumber(lastRecNumberForm.getLastReceiptNo());
				
				pcReceiptnumber.setIsActive(true);	
			}	
			
		
		String financeYear=pcfinancialYear.getFinancialYear();
		String exactYear="";
		if(financeYear!=null){
			if(financeYear.lastIndexOf("-")!=-1)
			{
				exactYear=financeYear.substring(0, financeYear.lastIndexOf("-"));
			}
		}
		int currentFinancialyear =Integer.parseInt(exactYear);

		PcReceiptNumber pcReceiptnumber = new PcReceiptNumber();
		if("Add".equalsIgnoreCase(mode))
		{
			int prevfinanicalYear = currentFinancialyear - 1;
			PcReceiptNumber previouRcreceipt = receipTnumberTransaction.getPreviouYearReceiptNumberDetails(prevfinanicalYear);
			
	
				if(previouRcreceipt!=null)
				{
				int prevEndingNo = Integer.parseInt(previouRcreceipt.getEndingNumber());
				Integer thisYearStartingNo = prevEndingNo+1;
				pcReceiptnumber.setStartingNumber(thisYearStartingNo.toString());
				pcReceiptnumber.setEndingNumber(lastRecNumberForm.getLastReceiptNo());
				pcReceiptnumber.setPcFinancialYear(pcfinancialYear);
				pcReceiptnumber.setIsActive(true);
				}
				else
				{
					pcReceiptnumber.setStartingNumber("1");
					pcReceiptnumber.setEndingNumber(lastRecNumberForm.getLastReceiptNo());
					pcReceiptnumber.setPcFinancialYear(pcfinancialYear);
					pcReceiptnumber.setIsActive(true);	
				}	
		}
		else
		{
			PcFinancialYear pcFinancialYear = new PcFinancialYear();
			pcFinancialYear.setId(lastRecNumberForm.getFinYearId());
			
			pcReceiptnumber.setId(lastRecNumberForm.getReceiptNumberId());
			pcReceiptnumber.setPcFinancialYear(pcFinancialYear);
			pcReceiptnumber.setEndingNumber(lastRecNumberForm.getLastReceiptNo());
			pcReceiptnumber.setModifiedBy(lastRecNumberForm.getUserId());
			pcReceiptnumber.setLastModifiedDate(new Date());
			
			pcReceiptnumber.setIsActive(true);
		}
		log.debug("leaving populatePcReceiptNumberDataFromForm in Helper");
		return pcReceiptnumber;
	}
	
	public PcFinancialYear checkForValidYear(LastReceiptNumberForm form) throws Exception
	{
		IReceiptNumberTransaction receipTnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		List<ReceiptNumberTo> pcreceiptslist = form.getReceiptNumberList();
		Iterator<ReceiptNumberTo> listIt = pcreceiptslist.iterator();
		PcFinancialYear pcFinanCialYear=null;
		String currentYear = form.getFinancialYear();
		while(listIt.hasNext())
		{
			ReceiptNumberTo numberTo = listIt.next();
			String yearFromTo = numberTo.getPcFinancialYear().getFinancialYear();
			if(currentYear.equals(yearFromTo))
			{
			//pcFinanCialYear = receipTnumberTransaction.getFinancialYear(currentYear);
			return pcFinanCialYear;
			}
			
		}
		return pcFinanCialYear;
	}
	
	public List<FinancialYearTO> convertToListToBo(List<PcFinancialYear> finanicalYearListBo) throws Exception
	{
		Iterator<PcFinancialYear> it = finanicalYearListBo.iterator();
		PcFinancialYear pcfinancailYear=null;
		List<FinancialYearTO> financialYearTolist = new ArrayList<FinancialYearTO>();
		
		while(it.hasNext())
		{
			pcfinancailYear = it.next();
			FinancialYearTO yearTo = new FinancialYearTO();
			yearTo.setId(pcfinancailYear.getId());
			yearTo.setCreatedBy(pcfinancailYear.getCreatedBy());
			yearTo.setCreatedDate(pcfinancailYear.getCreatedDate());
			yearTo.setFinancialYear(pcfinancailYear.getFinancialYear());
			yearTo.setIsActive(pcfinancailYear.getIsActive());
			yearTo.setIsCurrent(pcfinancailYear.getIsCurrent());
			yearTo.setLastModifiedDate(pcfinancailYear.getLastModifiedDate());
			yearTo.setModifiedBy(pcfinancailYear.getModifiedBy());
			
			financialYearTolist.add(yearTo);
		}
		
		return financialYearTolist;
	}
	
	public String getQueryString(PcFinancialYear pcfinancialYear) throws Exception
	{
	StringBuffer queryString = new StringBuffer();
	
	
	if(pcfinancialYear.getIsCurrent()==true)
	{
		queryString.append("update the table PcReceipts where");
	}
		return null;
	}
	
	public boolean checkYearAlreadyPresent(List<Object> yearList,LastReceiptNumberForm lastReceiptNumberForm) throws Exception
	{
		boolean isThere =false;
		IReceiptNumberTransaction receipTnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		PcFinancialYear pcfinancialYear = receipTnumberTransaction.getFinancialYear(lastReceiptNumberForm.getFinYearId());
		String eneterdFinYear = pcfinancialYear.getFinancialYear();
		Iterator<Object> yearIt =yearList.iterator();
		while(yearIt.hasNext())
		{
			String yearInTheList =(String)yearIt.next();
			if(eneterdFinYear.equals(yearInTheList))
			{
				isThere = true;
				break;
			}
		}
		
		
		return isThere;
		
	}
	
	public PcReceiptNumber populatePcReceiptNumberDataFromFormForDuplicateCheck(LastReceiptNumberForm lastRecNumberForm,String mode)throws Exception 
	{
		log.debug("inside populatePcReceiptNumberDataFromFormForDuplicateCheck in Helper");
		IReceiptNumberTransaction receipTnumberTransaction = ReceiptNumberTransactionImpl.getInstance();
		PcFinancialYear pcfinancialYear=null;
		if(lastRecNumberForm.getFinYearId()!=null)
		{
		pcfinancialYear = receipTnumberTransaction.getFinancialYear(lastRecNumberForm.getFinYearId());
		}
		else
		{
			pcfinancialYear = receipTnumberTransaction.getFinancialYear(lastRecNumberForm.getFinancialYear());
		}
		PcReceiptNumber pcReceiptnumber = new PcReceiptNumber();
		if("Add".equalsIgnoreCase(mode))
		{
		if(pcfinancialYear!=null)
		pcReceiptnumber.setPcFinancialYear(pcfinancialYear);
		pcReceiptnumber.setStartingNumber(lastRecNumberForm.getLastReceiptNo());
		pcReceiptnumber.setIsActive(true);
		}
		else
		{	if(pcfinancialYear!=null)
			pcReceiptnumber.setPcFinancialYear(pcfinancialYear);
			pcReceiptnumber.setStartingNumber(lastRecNumberForm.getLastReceiptNo());
			pcReceiptnumber.setId(lastRecNumberForm.getReceiptNumberId());
			pcReceiptnumber.setIsActive(true);
		}
		log.debug("leaving populatePcReceiptNumberDataFromFormForDuplicateCheck in Helper");
		return pcReceiptnumber;
	
}*/

}
