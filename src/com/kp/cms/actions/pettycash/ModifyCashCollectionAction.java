package com.kp.cms.actions.pettycash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.pettycash.ModifyCashCollectionForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.pettycash.CashCollectionHandler;
import com.kp.cms.handlers.pettycash.ModifyCashCollectionHandler;
import com.kp.cms.helpers.pettycash.ModifyCashCollectionHelper;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ModifyCashCollectionAction extends BaseDispatchAction {
	private static final Log log = LogFactory
			.getLog(ModifyCashCollectionAction.class);

	public ActionForward initModifyCashCollection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Inside initCashCollection method of CashCollectionAction class..");

		ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		modifyCashCollectionForm.reset();
		modifyCashCollectionForm.setPrintReceipt("false");
		try {

		} catch (Exception e) {
			log.error("error occured in initCashCollection");
			String msg = super.handleApplicationException(e);
			modifyCashCollectionForm.setErrorMessage(msg);
			modifyCashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		log.info("leaving the initCashCollection method in CashCollectionAction class..");
		return mapping.findForward(CMSConstants.INIT_MODIFY_CASH_COLLECTION);
	}

	public ActionForward getReceiptDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Inside getReceiptDetails method of CashCollectionAction class..");
		
		ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		modifyCashCollectionForm.setPrintReceipt("false");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = modifyCashCollectionForm.validate(mapping, request);
		List<CashCollectionTO> cashCollectionToList = null;
		try {
			if(errors.isEmpty()){
				cashCollectionToList = ModifyCashCollectionHandler.getinstance()
				.getDetailsToFill(modifyCashCollectionForm);
				if(cashCollectionToList!=null && !cashCollectionToList.isEmpty())
				{
				setDataToForm(modifyCashCollectionForm, cashCollectionToList);
				List<AccountHeadTO> originalAccountHeadList = modifyCashCollectionForm.getAccountHeadListToDisplay();
				modifyCashCollectionForm.setOriginalAccountHeadList(originalAccountHeadList);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_RECEIPTNUMBERNOTVALID));
					saveMessages(request, messages);
					modifyCashCollectionForm.reset();
					return mapping.findForward(CMSConstants.INIT_MODIFY_CASH_COLLECTION);
				}
				List<CashCollectionTO> accNameWithCodeList = ModifyCashCollectionHandler
				.getinstance().getAccountNameWithCodeToList(
						modifyCashCollectionForm);
		
					if(!accNameWithCodeList.isEmpty())
					{
					modifyCashCollectionForm.setAccNameWithCode(accNameWithCodeList);
					}
					else
					{
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTYCASH_NOVALIDDATA));
						saveMessages(request, messages);
						modifyCashCollectionForm.reset();
						return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
					}
			}
			else{
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_MODIFY_CASH_COLLECTION);
			}
		} catch (Exception e) {
			log.error("error occured in initCashCollection");
			String msg = super.handleApplicationException(e);
			modifyCashCollectionForm.setErrorMessage(msg);
			modifyCashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		log.info("leaving the getReceiptDetails method in CashCollectionAction class..");
		return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
	}

	public ActionForward getAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		modifyCashCollectionForm.setPrintReceipt("false");
		

		log.info("Inside getAmount method of ModifycashCollectionAction class..");
		try {
			AccountHeadTO accountTo = null;
			accountTo = ModifyCashCollectionHandler.getinstance().getAmount(
					modifyCashCollectionForm);
			if(accountTo.getAmount()==null || accountTo.getAmount().isEmpty())
			{
				modifyCashCollectionForm.setAmount(accountTo.getAmount());
				modifyCashCollectionForm.setIsfixed("false");
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTICASH_NOAMOUNT));
				saveMessages(request, messages);	
				return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
			}
			modifyCashCollectionForm.setAmount(accountTo.getAmount());
			modifyCashCollectionForm.setIsfixed(accountTo.getIsFixed());
		} catch (Exception e) {
			log.error("error occured in initCashCollection");
			String msg = super.handleApplicationException(e);
			modifyCashCollectionForm.setErrorMessage(msg);
			modifyCashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
	}

	public void setDataToForm(
			ModifyCashCollectionForm modifyCashCollectionForm,
			List<CashCollectionTO> cashCollectionToList) throws Exception {
		modifyCashCollectionForm = ModifyCashCollectionHelper.getInstance()
				.setAllDataToForm(modifyCashCollectionForm,
						cashCollectionToList);

	}

	public void setAccountNameWithCodeToList(
			ModifyCashCollectionForm modifyCashCollectionform) throws Exception {
		log.info("Inside setAccountNameWithCodeToList method of CashCollectionAction class..");
		List<CashCollectionTO> accNameWithCodeList = ModifyCashCollectionHandler
				.getinstance().getAccountNameWithCodeToList(
						modifyCashCollectionform);

		modifyCashCollectionform.setAccNameWithCode(accNameWithCodeList);
		log.info("leaving the setAccountNameWithCodeToList method in CashCollectionAction class..");
	}

	public ActionForward prepareFineList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into prepareFineList ");
		ActionMessages messages = new ActionMessages();
		
		ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		modifyCashCollectionForm.setPrintReceipt("false");
		ActionErrors errors = modifyCashCollectionForm.validate(mapping, request);
		
		String amount="0.0";
		if(modifyCashCollectionForm.getAmount()!=null && !modifyCashCollectionForm.getAmount().isEmpty())
		{
			if(!CommonUtil.isValidDecimal(modifyCashCollectionForm.getAmount())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.petticash.amount.proper"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
			}		
		}
		setAllFines(modifyCashCollectionForm);
		List<AccountHeadTO> accountList = modifyCashCollectionForm.getAccountHeadListToDisplay();
		try {
			if (errors.isEmpty()) {
				Map<Integer, AccountHeadTO> itemMap = modifyCashCollectionForm
						.getAccountHeadMap();
				Set<Integer> accIDSet = modifyCashCollectionForm.getAccIDSet();
				
				if (accountList == null || accountList.isEmpty()) {
					accountList = new ArrayList<AccountHeadTO>();
				}
				if(!accIDSet.contains(Integer.valueOf(modifyCashCollectionForm.getAccountId())) && 
						!itemMap.containsKey(Integer.valueOf(modifyCashCollectionForm.getAccountId())))
				{
					
				
					
					accIDSet.add(Integer.valueOf(modifyCashCollectionForm.getAccountId()));
					AccountHeadTO collectionTO = ModifyCashCollectionHelper.getInstance().getAccountheadtoAdd(modifyCashCollectionForm.getAccountId());
					if(modifyCashCollectionForm.getAmount()!=null && !modifyCashCollectionForm.getAmount().isEmpty())
					{
					collectionTO.setAmount(modifyCashCollectionForm.getAmount().trim());
					}
					else
					{
						collectionTO.setAmount("0.00");
					}
					collectionTO.setDetails(modifyCashCollectionForm.getDetails());
					itemMap.put(Integer.valueOf(modifyCashCollectionForm.getAccountId()),collectionTO );
					accountList.add(collectionTO);
					amount = CashCollectionHandler.getinstance()
							.getTotalAmount(accountList);
					collectionTO.setTotal(amount);
					modifyCashCollectionForm.setAccountId(null);
					modifyCashCollectionForm.setTotal(amount);
				} else
				{
					
					amount = CashCollectionHandler.getinstance().getTotalAmount(accountList);
					modifyCashCollectionForm.setTotal(amount);
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PETTICASH_TRYING_ALREADY_EXISTING));
					saveMessages(request, messages);
					modifyCashCollectionForm.setAmount(null);
					return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
				}

			}

			modifyCashCollectionForm.setAccountHeadListToDisplay(accountList);
			modifyCashCollectionForm.setAmount(null);
			modifyCashCollectionForm.setDetails(null);
		} catch (Exception e) {
		
			String msg = super.handleApplicationException(e);
			modifyCashCollectionForm.setErrorMessage(msg);
			modifyCashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into prepareFineList");
		return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
	}

	public ActionForward deleteReceiptNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		modifyCashCollectionForm.setPrintReceipt("false");
		log.info("Inside deleteReceiptNumber method of ..");

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		List<AccountHeadTO> fineList = modifyCashCollectionForm.getAccountHeadListToDisplay();
		try {
			setUserId(request, modifyCashCollectionForm);
			String userId = modifyCashCollectionForm.getUserId();
			String accid = modifyCashCollectionForm.getAccountId();
			int pcReceiptId =0;
			if(modifyCashCollectionForm.getRecNoResult()!=null && !StringUtils.isEmpty(modifyCashCollectionForm.getRecNoResult()) && StringUtils.isNumeric(modifyCashCollectionForm.getRecNoResult()))
				pcReceiptId = Integer.parseInt(modifyCashCollectionForm.getRecNoResult());
			Integer sizeOriginal = fineList.size();
			fineList = CashCollectionHandler.getinstance().deleteReceiptNumber(
					userId, accid, fineList, pcReceiptId);

			Integer sizeAfterDelete = fineList.size();
			/**
			 * If delete operation is success then add the success message. Else
			 * add the appropriate error message
			 */
			if (sizeAfterDelete == sizeOriginal - 1) {
				modifyCashCollectionForm.setTotal(CashCollectionHandler
						.getinstance().getTotalAmount(fineList));
				modifyCashCollectionForm.setAccountHeadListToDisplay(fineList);
				modifyCashCollectionForm.getAccountHeadMap().remove(Integer.valueOf(modifyCashCollectionForm.getAccountId()));
				modifyCashCollectionForm.getAccIDSet().remove(Integer.valueOf(modifyCashCollectionForm.getAccountId()));
				modifyCashCollectionForm.setAccountId(null);
				messages.add(CMSConstants.MESSAGES, new ActionMessage(
						CMSConstants.PETTICASH_CASHCOLLECTION_DELETE_SUCCESS));
				saveMessages(request, messages);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.PETTICASH_CASHCOLLECTION_DELETE_FAILED));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("error occured in deleteReceiptNumber");
			String msg = super.handleApplicationException(e);
			modifyCashCollectionForm.setErrorMessage(msg);
			modifyCashCollectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);

	}

	public void setAllFines(ModifyCashCollectionForm modifyCashCollectionForm)
			throws Exception {

		List<CashCollectionTO> fineList = modifyCashCollectionForm
				.getAccNameWithCode();
		Map<Integer, CashCollectionTO> fineMap = new HashMap<Integer, CashCollectionTO>();
		if (fineList != null && !fineList.isEmpty()) {
			Iterator<CashCollectionTO> collectionIt = fineList.iterator();
			while (collectionIt.hasNext()) {
				CashCollectionTO collectionTo = collectionIt.next();
				fineMap.put(collectionTo.getId(), collectionTo);
			}
		}
		modifyCashCollectionForm.setAccountMap(fineMap);
	}

	public ActionForward updateCashCollection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		setUserId(request, modifyCashCollectionForm);
		log.info("Inside saveandPrintCashCollection method");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		modifyCashCollectionForm.setPrintReceipt("false");
		errors = validateAccountList(modifyCashCollectionForm, errors);
		boolean isUpdated = false;
		try {
			if(errors.isEmpty()){			
				isUpdated = ModifyCashCollectionHandler.getinstance().updateCashCollection(modifyCashCollectionForm);
				if (isUpdated) {
					if(modifyCashCollectionForm.getFlag().equals("true")){
					modifyCashCollectionForm.setPrintReceipt("true");
					}
					else{
						modifyCashCollectionForm.setPrintReceipt("false");
					}
					messages.add(CMSConstants.MESSAGES, new ActionMessage(
							CMSConstants.PETTYCASH_CASHCOLLECTION_UPDATE_SUCCESS));
					saveMessages(request, messages);
					modifyCashCollectionForm.reset();
					modifyCashCollectionForm.setAccountList(null);
					modifyCashCollectionForm.setAccountHeadListToDisplay(null);
					return mapping.findForward(CMSConstants.INIT_MODIFY_CASH_COLLECTION);
				} else {
					errors.add(CMSConstants.ERROR, new ActionError(
							CMSConstants.PETTYCASH_CASHCOLLECTION_UPDATE_FAILED));
					saveErrors(request, errors);
					// assignListToForm(feeBillNumberForm);
				}
			}
		} catch (Exception e) {
			log.error("error occured in deleteReceiptNumber");
			//String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.GET_CASH_COLLECTION_FOR_EDIT);
	}
	
	private ActionErrors validateAccountList(
			ModifyCashCollectionForm modifyCashCollectionForm,
			ActionErrors errors)throws Exception {
		if(modifyCashCollectionForm.getAccountHeadListToDisplay().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.petticash.account.list.empty"));
		}
		return errors;
	}

	public ActionForward printReceiptAfterSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ActionErrors errors = new ActionErrors();
		 	log.debug("Entering dispalyReceipt of CashCollectionAction ");
		 	ModifyCashCollectionForm modifyCashCollectionForm = (ModifyCashCollectionForm) form;
		 	try {
		 		ModifyCashCollectionHandler.getinstance().getPrintWhileSaveInModify(modifyCashCollectionForm, request);
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
		 		session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, modifyCashCollectionForm.getLogo());
		 	} catch(Exception e) {
		 		if(e instanceof DataNotFoundException){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
					addErrors(request, errors);
				}
		 		else{
					String msg = super.handleApplicationException(e);
					modifyCashCollectionForm.setErrorMessage(msg);
					modifyCashCollectionForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
		 		}
		 	}
		 	log.debug("Exiting dispalyReceipt of CashCollectionAction ");
		 	return mapping.findForward(CMSConstants.PRINT_RECEIPT_FOR_MODIFY);
		 	
	}	

}
