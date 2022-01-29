package com.kp.cms.actions.pettycash;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.pettycash.CashCollectionForm;
import com.kp.cms.handlers.pettycash.CashCollectionHandler;
import com.kp.cms.helpers.pettycash.CashCollectionHelper;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CashCollectionAction extends BaseDispatchAction{

	/*
	 * 
	 */
	private static final Log log = LogFactory.getLog(CashCollectionAction.class);
	Integer receiptNumber=null;
	List <CashCollectionTO> accNameWithCodeList = null;
	/**
	 * setting the requested data to cashCollection.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCashCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Inside initCashCollection method of CashCollectionAction class..");	
//		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		CashCollectionForm cashCollectionForm = (CashCollectionForm) form;
		cashCollectionForm.setPrintReceipt("false");
		cashCollectionForm.clear();
		cashCollectionForm.setAmount(null);
		cashCollectionForm.setAccIDSet(null);
		cashCollectionForm.setAppNo("3");
		try
		{
			getCurrentTime(cashCollectionForm);
			setUserId(request, cashCollectionForm);
			receiptNumber=setReceiptNumber();
			if(receiptNumber!=null)
			{
				synchronized (receiptNumber) {
					cashCollectionForm.setNumber(receiptNumber.toString());	
				}
			
			}
			else
			{
				receiptNumber=0;
				cashCollectionForm.setNumber(receiptNumber.toString());
				messages.add(CMSConstants.MESSAGES, new ActionError(CMSConstants.PETTYCASH_THERE_IS_NO_ACTIVE_RECORDS));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);	
			}

			accNameWithCodeList=setAccountNameWithCodeToList(cashCollectionForm);
			if(accNameWithCodeList!=null && !accNameWithCodeList.isEmpty())
			{
				cashCollectionForm.setAccNameWithCode(accNameWithCodeList);
			}
			else
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_NAME_CODE_EMPTY));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);		
			}
		
		}
		catch (Exception e) {
			log.error("error occured in initCashCollection",e);
			String msg = super.handleApplicationException(e);
			cashCollectionForm.setErrorMessage(msg);
			cashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("leaving the initCashCollection method in CashCollectionAction class..");
		return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
	}
	
	/**
	 * getting the recieptNumber from the database
	 * @return
	 * @throws Exception
	 */
	public Integer setReceiptNumber() throws Exception	{
		Integer receiptNumber =null;
		log.info("Inside setReceiptNumber method of CashCollectionAction class..");
		receiptNumber =CashCollectionHandler.getinstance().getLastReceiptNumber();
		log.info("leaving the setReceiptNumber method in CashCollectionAction class..");
		return receiptNumber;	
	}
	
	/**
	 * getting the list of cashCollectionTo to display in the input jsp
	 * @param cashCollectionForm
	 * @return
	 * @throws Exception
	 */
	public List <CashCollectionTO> setAccountNameWithCodeToList(CashCollectionForm cashCollectionForm)throws Exception
	{
		log.info("Inside setAccountNameWithCodeToList method of CashCollectionAction class..");
		List <CashCollectionTO> accNameWithCodeList = CashCollectionHandler.getinstance().getAccountNameWithCodeToList(cashCollectionForm);
		log.info("leaving the setAccountNameWithCodeToList method in CashCollectionAction class..");
		return accNameWithCodeList;
		
	}
	
	
	/**
	 * getting the Amount for input selected Account Heads
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		cashCollectionForm.setPrintReceipt("false");
		
		log.info("Inside getAmount method of CashCollectionAction class..");
		try
		{
		AccountHeadTO accountTo=null;	
		accountTo=CashCollectionHandler.getinstance().getAmount(cashCollectionForm);
		cashCollectionForm.setAmount(accountTo.getAmount());
		cashCollectionForm.setIsfixed(accountTo.getIsFixed());
		}
		catch(Exception e)
		{
		log.error("error occured in initCashCollection",e);
		String msg = super.handleApplicationException(e);
		cashCollectionForm.setErrorMessage(msg);
		cashCollectionForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
	}
	

	public ActionForward prepareFineList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into prepareFineList ");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		CashCollectionForm cashcollectionForm = (CashCollectionForm)form;
		cashcollectionForm.setPrintReceipt("false");
		BaseActionForm baseForm=(BaseActionForm)request.getSession().getAttribute("baseActionForm");
			if(!cashcollectionForm.getPrintReceipt().isEmpty() && 
			cashcollectionForm.getPrintReceipt().equals("true")){
				cashcollectionForm.setAccountList(null);
				cashcollectionForm.setPrintReceipt("false");
			}
		if(baseForm == null)
		{
			messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTICASH_SELECT_ACCOUNTCODE));
			saveMessages(request, messages);	
			cashcollectionForm.setAppRegRollno(cashcollectionForm.getAppRegRollno());
			cashcollectionForm.setAppNo(cashcollectionForm.getAppNo());
			return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);	
		}
		else
		{
			cashcollectionForm.setAccId(Integer.valueOf(baseForm.getAccoId()).toString());
			
		}		
		if(cashcollectionForm.getAmount()!=null && !cashcollectionForm.getAmount().isEmpty())
		{
			if(!CommonUtil.isValidDecimal(cashcollectionForm.getAmount())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.petticash.amount.proper"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
			}		
		}
		setAllFines(cashcollectionForm);
		List<CashCollectionTO> accountList =cashcollectionForm.getAccountList();
		
		String amount="0.0";
		try
		{
			if(errors.isEmpty()){
				//Added by dilip
				if(cashcollectionForm.getIsPettyCash().equalsIgnoreCase("ApplicationIssue")){
					CashCollectionHandler.getinstance().getAccountNameWithCodeToList(cashcollectionForm);
				}
				//Add end
				Map<Integer, CashCollectionTO> itemMap = cashcollectionForm.getAccountMap();
				Set<Integer> accIDSet = cashcollectionForm.getAccIDSet();
				if(accIDSet==null || accIDSet.isEmpty()){
					accIDSet = new HashSet<Integer>();
					
				}
				if(accountList==null || accountList.isEmpty()){
					accountList = new ArrayList<CashCollectionTO>();
				}
				if(!accIDSet.contains(Integer.valueOf(cashcollectionForm.getAccId())) && 
					itemMap.containsKey(Integer.valueOf(cashcollectionForm.getAccId())))
				{
					CashCollectionTO  collectionTO = itemMap.get(Integer.valueOf(cashcollectionForm.getAccId()));
					//collectionTO.setId(null);
					if(cashcollectionForm.getAmount()!=null && !cashcollectionForm.getAmount().isEmpty()){
						collectionTO.setAmount(cashcollectionForm.getAmount().trim());
					}
					else{
						collectionTO.setAmount("0.00");
					}
					collectionTO.setDetails(cashcollectionForm.getDetails());
					accountList.add(collectionTO);
					amount = CashCollectionHandler.getinstance().getTotalAmountForcashCollection(accountList);
					accIDSet.add(Integer.valueOf(cashcollectionForm.getAccId()));
					
					collectionTO.setTotal(amount);
					
					cashcollectionForm.setTotal(amount);
					cashcollectionForm.setAccIDSet(accIDSet);
					cashcollectionForm.setName(cashcollectionForm.getName());
					cashcollectionForm.setAppRegRollno(cashcollectionForm.getAppRegRollno());
					cashcollectionForm.setAppNo(cashcollectionForm.getAppNo());
				}
				else
				{
					amount = CashCollectionHandler.getinstance().getTotalAmountForcashCollection(accountList);
					cashcollectionForm.setTotal(amount);
					cashcollectionForm.setName(cashcollectionForm.getName());
					cashcollectionForm.setAppRegRollno(cashcollectionForm.getAppRegRollno());
					cashcollectionForm.setAppNo(cashcollectionForm.getAppNo());
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTICASH_TRYING_ALREADY_EXISTING));
					addErrors(request, errors);
					//Added by dilip
					if(cashcollectionForm.getIsPettyCash().equalsIgnoreCase("ApplicationIssue")){
						accNameWithCodeList=setAccountNameWithCodeToList(cashcollectionForm);
						if(accNameWithCodeList!=null && !accNameWithCodeList.isEmpty())
						{
							cashcollectionForm.setAccNameWithCode(accNameWithCodeList);
						}				
					}
					//Add end
					return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
					
				}
				
			}
			cashcollectionForm.setName(cashcollectionForm.getName());
			cashcollectionForm.setAppRegRollno(cashcollectionForm.getAppRegRollno());
			cashcollectionForm.setAppNo(cashcollectionForm.getAppNo());
			cashcollectionForm.setAccountList(accountList);
			cashcollectionForm.setAmount(null);
			cashcollectionForm.setAccountId(null);
			cashcollectionForm.setFineType(null);
			cashcollectionForm.setDetails(null);
		}
		catch (Exception e) {
			e.printStackTrace();
			String msg = super.handleApplicationException(e);
			cashcollectionForm.setErrorMessage(msg);
			cashcollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving prepareFineList");
		//Added by dilip
		if(cashcollectionForm.getIsPettyCash().equalsIgnoreCase("ApplicationIssue")){
			accNameWithCodeList=setAccountNameWithCodeToList(cashcollectionForm);
			if(accNameWithCodeList!=null && !accNameWithCodeList.isEmpty())
			{
				cashcollectionForm.setAccNameWithCode(accNameWithCodeList);
			}				
		}
		//Add end
		return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
	
	}
	
	public void setAllFines(CashCollectionForm cashCollectionForm)throws Exception{
		
		List<CashCollectionTO> fineList = cashCollectionForm.getAccNameWithCode();
		Map<Integer,CashCollectionTO> fineMap = new HashMap<Integer, CashCollectionTO>();
		if(fineList!=null && !fineList.isEmpty())
		{
			Iterator<CashCollectionTO> collectionIt = fineList.iterator();
			while(collectionIt.hasNext())
			{
				CashCollectionTO collectionTo = collectionIt.next();
				fineMap.put(collectionTo.getId(), collectionTo);
			}
		}
		cashCollectionForm.setAccountMap(fineMap);
	}
	/**
	 * deleting the Receipt Number from the database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteReceiptNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		cashCollectionForm.setPrintReceipt("false");
		log.info("Inside deleteReceiptNumber method of CashCollectionAction class..");	
		
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			try {
				setUserId(request, cashCollectionForm);
				String userId=cashCollectionForm.getUserId();
				String accid =cashCollectionForm.getAccId() ;
				
				List<CashCollectionTO> fineList = cashCollectionForm.getAccountList();
				Integer sizeOriginal =fineList.size();
			//	boolean isDeleted;
				fineList =CashCollectionHandler.getinstance().deleteReceiptNumberForCashCollection(userId, accid,fineList);
				
				
				Integer sizeAfterDelete=fineList.size();
				/**
				 * If delete operation is success then add the success message.
				 * Else add the appropriate error message
				 */
				if (sizeAfterDelete==sizeOriginal-1) {
					cashCollectionForm.setTotal(CashCollectionHandler.getinstance().getTotalAmountForcashCollection(fineList));
					cashCollectionForm.setAccountList(fineList);
					Set<Integer> accIdSet = cashCollectionForm.getAccIDSet();
					//boolean result = accIdSet.remove(Integer.valueOf(cashCollectionForm.getAccountId()));
					accIdSet.remove(Integer.valueOf(cashCollectionForm.getAccId()));
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTICASH_CASHCOLLECTION_DELETE_SUCCESS));
					saveMessages(request, messages);
					
				}
				else 
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTICASH_CASHCOLLECTION_DELETE_FAILED));
					saveErrors(request, errors);
					
				}
			}
		catch(Exception e)
		{
		log.error("error occured in deleteReceiptNumber");
		String msg = super.handleApplicationException(e);
		cashCollectionForm.setErrorMessage(msg);
		cashCollectionForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//Added by dilip
		if(cashCollectionForm.getIsPettyCash().equalsIgnoreCase("ApplicationIssue")){
			accNameWithCodeList=setAccountNameWithCodeToList(cashCollectionForm);
			if(accNameWithCodeList!=null && !accNameWithCodeList.isEmpty())
			{
				cashCollectionForm.setAccNameWithCode(accNameWithCodeList);
			}				
		}
		//Add end
		return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);

	}
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
		
	}	
	/*
	 * This method sets the current time in hours and minutes in the form 
	 */
	public void getCurrentTime(CashCollectionForm cashCollectionForm)throws Exception
	{
		log.info("Inside setCurrentTime method of CashCollectionAction class..");
		CashCollectionTO collectionTo = CashCollectionHelper.getInstance().getCurrentTimenadDate(cashCollectionForm);
		
		cashCollectionForm.setPaidDate(collectionTo.getPaidDate());
		cashCollectionForm.setHour(collectionTo.getHour());
		cashCollectionForm.setMinutes(collectionTo.getMinutes());
		
		log.info("leaving the setCurrentTime method in CashCollectionAction class..");
		
	}

	
	public ActionForward saveCashCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
		log.info("Inside saveCashCollection method of CashCollectionAction class..");	
		CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		//cashCollectionForm.setPrintReceipt("false");
		//cashCollectionForm.setAccountList(null);
		ActionErrors errors =cashCollectionForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		if(!cashCollectionForm.getPrintReceipt().isEmpty() && 
			cashCollectionForm.getPrintReceipt().equals("true")){
			cashCollectionForm.setAccountList(null);
			cashCollectionForm.setPrintReceipt("false");
		}
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
		int finId = cashCollectionTransaction.getCurrentFinancialYear();
		if(finId<0){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.pc.financialyear.exists.required"));
		}
		if(cashCollectionForm.getPaidDate()!=null && !cashCollectionForm.getPaidDate().isEmpty()){
			errors = validateDate(cashCollectionForm.getPaidDate().trim(), errors);
		}
		if(cashCollectionForm.getNumber()!=null && !cashCollectionForm.getNumber().isEmpty()){
			errors = validateReceiptNumber(cashCollectionForm.getNumber().trim(), errors);
		}
		if(cashCollectionForm.getAccountList()==null || cashCollectionForm.getAccountList().isEmpty())
		{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTICASH_NODTAILS_SELECTED));
		}
		if(cashCollectionForm.getIsPettyCash().equalsIgnoreCase("ApplicationIssue")){
			if(cashCollectionForm.getAppRegRollno().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.studentClassSubjectDetails.bothRegNoAndApplnNoNotRequired"));
			}
		}
		
		if (errors.isEmpty())
		{
			setUserId(request, cashCollectionForm);
		
			try {
				Integer recNo = CashCollectionHandler.getinstance().saveCashCollection(cashCollectionForm);
				if(recNo>0)
				{
				cashCollectionForm.clear();
				cashCollectionForm.setOldRecNo(recNo.toString());
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTICASH_CASHCOLLECTION_SAVE_SUCCESS,recNo.toString()));
				saveMessages(request, messages);
				cashCollectionForm.setAccountId(null);
				//cashCollectionForm.setAccountIdSet(null);
				cashCollectionForm.setAccIDSet(null);
				cashCollectionForm.setAppNo("3");
				cashCollectionForm.setAmount(null);
				//cashCollectionForm.setAccountList(null);
				getCurrentTime(cashCollectionForm);
				setUserId(request, cashCollectionForm);
				
				receiptNumber=setReceiptNumber();
				if(receiptNumber!=null)
				{
					synchronized (receiptNumber) {
						cashCollectionForm.setNumber(receiptNumber.toString());	
					}
				}
				else
				{
					receiptNumber=0;
					cashCollectionForm.setNumber(receiptNumber.toString());
					messages.add(CMSConstants.MESSAGES, new ActionError(CMSConstants.PETTYCASH_THERE_IS_NO_ACTIVE_RECORDS));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);	
				}
				accNameWithCodeList=setAccountNameWithCodeToList(cashCollectionForm);
				if(accNameWithCodeList!=null && !accNameWithCodeList.isEmpty())
				{
					cashCollectionForm.setAccNameWithCode(accNameWithCodeList);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_NAME_CODE_EMPTY));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);		
				}
				if(cashCollectionForm.getFlag().equals("false")){
					cashCollectionForm.setPrintReceipt("false");
					}
				else{
				cashCollectionForm.setPrintReceipt("true");
				}
				//CashCollectionHandler.getinstance().getPrintWhileSave(cashCollectionForm, request);
				addErrors(request, errors);
				cashCollectionForm.setAccountList(null);
				return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
			}
				else 
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PETTICASH_CASHCOLLECTION_SAVE_FAILED));
					saveErrors(request, errors);
					
				}
			}
			catch(DataNotFoundException e) {
//				 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_BILLNO_NOTPRESENT));
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.PETTYCASH_NORECORD));
	    		saveErrors(request,errors);
	    		setFinancialYearList(cashCollectionForm);
	    		cashCollectionForm.setPrintReceipt("false");
	    		return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
		 	}
			catch (Exception e) {
				log.error("error occured in saveCashCollection");
				String msg = super.handleApplicationException(e);
				cashCollectionForm.setErrorMessage(msg);
				cashCollectionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);	
			}
		}
			addErrors(request, errors);
			//Added by dilip
			if(cashCollectionForm.getIsPettyCash().equalsIgnoreCase("ApplicationIssue")){
				accNameWithCodeList=setAccountNameWithCodeToList(cashCollectionForm);
				if(accNameWithCodeList!=null && !accNameWithCodeList.isEmpty())
				{
					cashCollectionForm.setAccNameWithCode(accNameWithCodeList);
				}				
			}
			//Add end
			return mapping.findForward(CMSConstants.DISPLAY_CASH_COLLECTION);
	}
			
	private ActionErrors validateReceiptNumber(String receiptNumber,
			ActionErrors errors) throws Exception{
		if(receiptNumber!=null && !receiptNumber.isEmpty() && receiptNumber.equals("0")){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.pettycash.receiptno.invalid"));
		}
	
		return errors;
	}

	/**
	 * 		
	 * @param cashCollectionForm
	 * @param errors
	 * @return
	 */
	private ActionErrors validateDate(
			String paidDate, ActionErrors errors) throws Exception{
		
		boolean isValidDateFormat;
		isValidDateFormat = CommonUtil.isValidDate(paidDate);
		if(!isValidDateFormat){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));		
		}
		else if(isValidDateFormat){
			String formattedString=CommonUtil.ConvertStringToDateFormat(paidDate, CMSConstants.DEST_DATE,CMSConstants.DEST_DATE_MM_DD_YYYY);
			Date date = new Date(formattedString);
			Date curdate = new Date();
			boolean result;
			if (date.compareTo(curdate) == 1){
				result = false;
			}
			else{
				result = true;
			}
			if(!result){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.pettycash.paidDate.future"));
			}
		}
		log.info("Leaving into validateDate StockTransferAction");
		return errors;
	}

	/*
	 * Printing the receipt generated related actions
	 */
	public ActionForward initReprintReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering initReprintReceipt of CashCollectionAction  ");
		 	CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		 	try {
		 		cashCollectionForm.setPrintReceipt("false");
		 		setFinancialYearList(cashCollectionForm);
		 		cashCollectionForm.setNumber(null);
		 		cashCollectionForm.setLogo(null);
		 		
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				cashCollectionForm.setErrorMessage(msg);
				cashCollectionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("exiting initReprintReceipt of CashCollectionAction ");		 	
		 	return mapping.findForward(CMSConstants.PETTYCASH_REPRINTRECEIPT);
	}	
	
	public ActionForward getReprintReceiptData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering reprintChallan ");
		 	CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		 	
		 	ActionMessages errors = cashCollectionForm.validate(mapping, request);
		 	try {
		 		
		 		if(errors.isEmpty()){
		 			CashCollectionHandler.getinstance().getReceiptDetails(cashCollectionForm, request);
		 			cashCollectionForm.setPrintReceipt(CMSConstants.TRUE);
		 			HttpSession session= request.getSession(false);
			 		/*	Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
					if(organisation!=null){
						// set photo to session
						if(organisation.getLogoContentType()!=null){
							HttpSession session= request.getSession(false);
							if(session!=null){
								if(organisation.getLogo()!=null)
								session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
							}
						}
					}	*/
			 		session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, cashCollectionForm.getLogo());
			 		cashCollectionForm.setLogo(null);
		 		} else {
		 			saveErrors(request, errors);
		 			cashCollectionForm.setPrintReceipt("false");
		 			setFinancialYearList(cashCollectionForm);
		 			return mapping.findForward(CMSConstants.PETTYCASH_REPRINTRECEIPT);
		 		}
		 	} catch(DataNotFoundException e) {
//				 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_BILLNO_NOTPRESENT));
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.PETTYCASH_NORECORD));
	    		saveErrors(request,errors);
	    		setFinancialYearList(cashCollectionForm);
	    		cashCollectionForm.setPrintReceipt("false");
	    		return mapping.findForward(CMSConstants.PETTYCASH_REPRINTRECEIPT);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				cashCollectionForm.setErrorMessage(msg);
				cashCollectionForm.setErrorStack(e.getMessage());
				cashCollectionForm.setPrintReceipt("false");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving reprintChallan ");
		 	setFinancialYearList(cashCollectionForm);
		 	return mapping.findForward(CMSConstants.PETTYCASH_REPRINTRECEIPT);
	}
	
	public ActionForward dispalyReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering dispalyReceipt of CashCollectionAction ");
		 	CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		 	try {
		 		CashCollectionHandler.getinstance().getReceiptDetails(cashCollectionForm, request);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				cashCollectionForm.setErrorMessage(msg);
				cashCollectionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Exiting dispalyReceipt of CashCollectionAction ");
		 	return mapping.findForward(CMSConstants.PRINT_RECEIPT);
		 	
	}	
	public ActionForward printReceiptAfterSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering dispalyReceipt of CashCollectionAction ");
		 	CashCollectionForm cashCollectionForm =(CashCollectionForm)form;
		 	try {
		 		CashCollectionHandler.getinstance().getPrintWhileSave(cashCollectionForm, request);
		 		HttpSession session= request.getSession(false);
		 		/*	Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if(organisation!=null){
					// set photo to session
					if(organisation.getLogoContentType()!=null){
						HttpSession session= request.getSession(false);
						if(session!=null){
							if(organisation.getLogo()!=null)
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						}
					}
				}	*/
		 		session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, cashCollectionForm.getLogo());
		 		cashCollectionForm.setLogo(null);
		 		if(CMSConstants.LINK_FOR_CJC){
					session.setAttribute("linkForCjc",true);
				}else{
					session.setAttribute("linkForCjc", false);
				}
		 		//session.setAttribute("linkForCjc",true);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				cashCollectionForm.setErrorMessage(msg);
				cashCollectionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Exiting dispalyReceipt of CashCollectionAction ");
		 	return mapping.findForward(CMSConstants.PRINT_RECEIPT);
		 	
	}	
		
	public void setFinancialYearList(CashCollectionForm cashCollectionForm) throws Exception
	{
		log.info("Start of setFinancialYearList of CashCollectionAction");
		List<FinancialYearTO> finanicalyearList=null;
		finanicalyearList = CashCollectionHandler.getinstance().getFinancialYearList();
		cashCollectionForm.setFinancilYearList(finanicalyearList);
		int yearid=CashCollectionHandler.getinstance().getCurrentFinancialYear();
 		if(yearid!=0)
 		cashCollectionForm.setFinYearId(String.valueOf(yearid));
		log.info("Existing setFinancialYearList of CashCollectionAction");
	}

}
