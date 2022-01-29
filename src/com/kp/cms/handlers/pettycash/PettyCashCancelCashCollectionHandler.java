package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.forms.pettycash.CashCollectionForm;
import com.kp.cms.helpers.pettycash.PettyCashCancelCashCollectionHelper;
import com.kp.cms.to.pettycash.PettyCashCancelCashCollectionTO;
import com.kp.cms.transactions.pettycash.IPettyCashCancelCashCollectionTransaction;
import com.kp.cms.transactionsimpl.pettycash.PettyCashCancelCashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class PettyCashCancelCashCollectionHandler {
	
	private static final Log log=LogFactory.getLog(PettyCashCancelCashCollectionHandler.class);
	private static volatile PettyCashCancelCashCollectionHandler pcCancelCashCollection=null;
	PettyCashCancelCashCollectionTO pcCashCancelCollectionTO=null;
	PettyCashCancelCashCollectionHelper  pcCashCancelCashCollectionHelper=null;
	public static PettyCashCancelCashCollectionHandler getInstance()
	{
		if(pcCancelCashCollection==null)
		{
			pcCancelCashCollection=new PettyCashCancelCashCollectionHandler();
			return pcCancelCashCollection;
		}
		return pcCancelCashCollection;
	}
	IPettyCashCancelCashCollectionTransaction pettyCashCancelCollectionTransactionImpl= PettyCashCancelCashCollectionTransactionImpl.getInstance();
	
	public List<PettyCashCancelCashCollectionTO> getAllCashCollectionByReceiptNumber(String number,String financialYearId)throws Exception
	{
		log.info("entering into getAllCashCollectionByReceiptNumber in PettyCashCancelCashCollectionHandler class..");
		List<PettyCashCancelCashCollectionTO> pcReceiptsListTO=new ArrayList<PettyCashCancelCashCollectionTO>();
		List<PcReceipts> PcReceiptList=pettyCashCancelCollectionTransactionImpl.getAllCashCollectionByReceiptNumber(number,financialYearId);
		for(PcReceipts pcReceipts:PcReceiptList)
		{
			pcCashCancelCollectionTO=new PettyCashCancelCashCollectionTO();
			pcCashCancelCollectionTO.setId(pcReceipts.getId());
						
			if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getPcAccountHead().getAccCode()!=null && !pcReceipts.getPcAccountHead().getAccCode().equals(""))
			{
			pcCashCancelCollectionTO.setAccCode(pcReceipts.getPcAccountHead().getAccCode());
			}
			if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getPcAccountHead().getAccName()!=null && pcReceipts.getPcAccountHead()!=null && !pcReceipts.getPcAccountHead().equals(""))
			{
			pcCashCancelCollectionTO.setAccName(pcReceipts.getPcAccountHead().getAccName());
			}
			if(pcReceipts.getPcAccountHead()!=null && pcReceipts.getAmount()!=null)
			{
			pcCashCancelCollectionTO.setAmount(pcReceipts.getAmount());
			}
			pcReceiptsListTO.add(pcCashCancelCollectionTO);
		}
		log.info("leaving from getAllCashCollectionByReceiptNumber in PettyCashCancelCashCollectionHandler class..");
		return pcReceiptsListTO;
	}
	
	public CashCollectionForm getCashCollectionByReceiptNumber(CashCollectionForm cashCollectionForm,CashCollectionForm cashCollectionForm1) throws Exception
	{
		log.info("entering into getCashCollectionByReceiptNumber in PettyCashCancelCashCollectionHandler class..");
		PcReceipts pcReceipts=pettyCashCancelCollectionTransactionImpl.getCashCollectionByReceiptNumber(cashCollectionForm.getNumber(),cashCollectionForm1.getFinYear());
		if(pcReceipts!=null)
		{
		String paidDateTime=CommonUtil.formatDate(pcReceipts.getPaidDate(), "dd/MM/yyyy hh:MM:ss");
		String[] dateTime;
		
		dateTime=paidDateTime.split(" ");
		cashCollectionForm.setPaidDate(dateTime[0]);
		cashCollectionForm.setPaidTime(dateTime[1]);
		cashCollectionForm.setAcademicYear(pcReceipts.getAcademicYear().toString());
		if(pcReceipts.getAmount()!=null)
		{
		cashCollectionForm.setAmount(pcReceipts.getAmount().toString());
		}
		if(pcReceipts.getName()!=null && !pcReceipts.getName().equals(""))
		{
		cashCollectionForm.setName(pcReceipts.getName());
		}
		if(pcReceipts.getRefNo()!=null && !pcReceipts.getRefNo().equals(""))
		{
		cashCollectionForm.setRefNo(pcReceipts.getRefNo());
		}
		if(pcReceipts.getRefType()!=null && !pcReceipts.getRefType().equals(""))
		{
		cashCollectionForm.setRefType(pcReceipts.getRefType());
		}
		cashCollectionForm.setId(String.valueOf(pcReceipts.getId()));
		cashCollectionForm.setCancelComments(pcReceipts.getCancelComments());
		cashCollectionForm.setIsCancelled(pcReceipts.getIsCancelled().toString());
		}
		else
		{
			cashCollectionForm.clear();
		}
		log.info("leaving from getCashCollectionByReceiptNumber in PettyCashCancelCashCollectionHandler class..");
		return cashCollectionForm;
		
	}
	
	
	public boolean manageCashCollection(CashCollectionForm cashCollectionForm) throws Exception
	{
		
		log.info("Start of manageCashCollection");
	//	PcReceipts pcReceipts=pettyCashCancelCollectionTransactionImpl.getCashCollectionByReceiptNumber(cashCollectionForm.getNumber(),cashCollectionForm.getAcademicYear());
		PcReceipts pcReceipts=pettyCashCancelCollectionTransactionImpl.getCashCollectionByReceiptNumber(cashCollectionForm.getNumber(),cashCollectionForm.getFinYear());
		pcReceipts.setModifiedBy(cashCollectionForm.getUserId());
		pcReceipts.setLastModifiedDate(new Date());
		pcReceipts.setIsCancelled(true);
		pcReceipts.setCancelComments(cashCollectionForm.getCancelComments());
		pettyCashCancelCollectionTransactionImpl.manageCashCollection(pcReceipts);
		log.info("End of manageCashCollection");
		return true;
	}

	public int getCurrentFinancialYear() throws Exception{
			return pettyCashCancelCollectionTransactionImpl.getCurrentFinancialYear();
	}
}
