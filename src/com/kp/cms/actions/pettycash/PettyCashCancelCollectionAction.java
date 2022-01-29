package com.kp.cms.actions.pettycash;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.CashCollectionForm;
import com.kp.cms.handlers.pettycash.CashCollectionHandler;
import com.kp.cms.handlers.pettycash.PettyCashCancelCashCollectionHandler;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.pettycash.PettyCashCancelCashCollectionTO;

@SuppressWarnings("deprecation")
public class PettyCashCancelCollectionAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(PettyCashCancelCollectionAction.class);
	
	public ActionForward initCancelCashCollection(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		
		CashCollectionForm cashCollectionForm=(CashCollectionForm)form;
		cashCollectionForm.clearAll();
		setRequestedDatatoForm(cashCollectionForm);
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_CANCEL_CASHCOLLECTION);
	
	}
	
	
	public ActionForward retrieveCashCollection(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ActionMessages messages = new ActionMessages();
		BigDecimal totalAmount=new BigDecimal(0);
		CashCollectionForm cashCollectionForm=(CashCollectionForm)form;
		ActionErrors errors=cashCollectionForm.validate(mapping, request);
		try
		{
		if(errors.isEmpty()){
		List<PettyCashCancelCashCollectionTO> pcCashCollectionListTO=null;
		pcCashCollectionListTO=PettyCashCancelCashCollectionHandler.getInstance().getAllCashCollectionByReceiptNumber(cashCollectionForm.getNumber(),cashCollectionForm.getFinYear());
		if(pcCashCollectionListTO!=null && !pcCashCollectionListTO.isEmpty())
		{
		totalAmount=cashCollectionTotalAmount(pcCashCollectionListTO);
		}
		else
		{
			ActionMessage message = new ActionMessage("knowledgepro.pettycash.no.active.record",
					cashCollectionForm.getNumber());
			messages.add("messages", message);
			saveMessages(request, messages);
			cashCollectionForm.clearAll();
			setRequestedDatatoForm(cashCollectionForm);
			return mapping.findForward(CMSConstants.PETTYCASH_INIT_CANCEL_CASHCOLLECTION);
		}
		cashCollectionForm=PettyCashCancelCashCollectionHandler.getInstance().getCashCollectionByReceiptNumber(cashCollectionForm,cashCollectionForm);
		if(cashCollectionForm.getId()!=null && !cashCollectionForm.getId().equals(""))
		{
			cashCollectionForm.setTotalAmount(totalAmount);
			
			if(pcCashCollectionListTO!=null && !pcCashCollectionListTO.isEmpty())
			{
				cashCollectionForm.setPcCashCollectionListTO(pcCashCollectionListTO);
			}
			
			return mapping.findForward(CMSConstants.PETTYCASH_RETRIEVE_CANCEL_CASHCOLLECTION);
		}
		}else{
			saveErrors(request,errors);
			setRequestedDatatoForm(cashCollectionForm);
			return mapping.findForward(CMSConstants.PETTYCASH_INIT_CANCEL_CASHCOLLECTION);
		}
			
		}catch (Exception e) {
			log.error("error in loading pettyCashCancelCashCollection...", e);
				String msg = super.handleApplicationException(e);
				cashCollectionForm.setErrorMessage(msg);
				cashCollectionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		cashCollectionForm.clearAll();
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_CANCEL_CASHCOLLECTION);
	}
	
	
	public ActionForward manageCashCollection(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		CashCollectionForm cashCollectionForm=(CashCollectionForm)form;
		boolean isDeleted=false;
		
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = cashCollectionForm.validate(mapping, request);
		try
		{
			if(errors.isEmpty())
			{
			setUserId(request, cashCollectionForm);
			
				if(cashCollectionForm.getNumber()!=null && !cashCollectionForm.getNumber().equals("") )
				{	
					isDeleted= PettyCashCancelCashCollectionHandler.getInstance().manageCashCollection(cashCollectionForm);
					cashCollectionForm.clearAll();
				}
				
				if (isDeleted) 
				{
					ActionMessage message = new ActionMessage("knowledgepro.pettycash.cancelcashcollection.success",
							cashCollectionForm.getNumber());
					messages.add("messages", message);
					saveMessages(request, messages);
					
				} else {
					errors.add("error", new ActionError("knowledgepro.pettycash.cancelcashcollection.fail",
							cashCollectionForm.getNumber()));
					saveErrors(request,errors);
				}
			}
			else
			{
				saveErrors(request,errors);
				return mapping.findForward("retrieveCashCollection");
			}
			setRequestedDatatoForm(cashCollectionForm);
		}catch (Exception e) {
			log.error("error occured in cancelReceiptNumber");
			String msg = super.handleApplicationException(e);
			cashCollectionForm.setErrorMessage(msg);
			cashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		return mapping.findForward(CMSConstants.PETTYCASH_INIT_CANCEL_CASHCOLLECTION);
	}
	
	
	
	public BigDecimal cashCollectionTotalAmount(List<PettyCashCancelCashCollectionTO> pcCashCollectionListTO)
	{
		 BigDecimal amount=new BigDecimal(0);
		
		for(PettyCashCancelCashCollectionTO PettyCashCancelCashCollectionTO:pcCashCollectionListTO)
		{
			amount=amount.add(PettyCashCancelCashCollectionTO.getAmount());
		}
			return amount;
	}
		
 		public void setRequestedDatatoForm(CashCollectionForm cashCollectionForm) throws Exception{
 			List<FinancialYearTO> finanicalyearList=null;
 			finanicalyearList = CashCollectionHandler.getinstance().getFinancialYearList();
 			cashCollectionForm.setFinancilYearList(finanicalyearList);
 			int yearId=PettyCashCancelCashCollectionHandler.getInstance().getCurrentFinancialYear();
 	 		if(yearId!=0)
 	 		cashCollectionForm.setFinYearId(String.valueOf(yearId));
 		}
 		
 	/*	public void setRequestedDatatoForm(CashCollectionForm cashCollectionForm) throws Exception{
 			List<FinancialYearTO> finanicalyearList=null;
 			finanicalyearList = CashCollectionHandler.getinstance().getFinancialYearList();
 			cashCollectionForm.setFinancilYearList(finanicalyearList);
 			int year=PettyCashCancelCashCollectionHandler.getInstance().getCurrentFinancialYear();
 	 		if(year!=0)
 	 		cashCollectionForm.setAcademicYear(String.valueOf(year));
 		}*/

	}


