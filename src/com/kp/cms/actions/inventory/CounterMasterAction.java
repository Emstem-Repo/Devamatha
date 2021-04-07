package com.kp.cms.actions.inventory;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.CounterMasterForm;
import com.kp.cms.handlers.inventory.CounterMasterHandler;
import com.kp.cms.handlers.inventory.QuotationHandler;
import com.kp.cms.to.inventory.InvCounterTO;
import com.kp.cms.transactions.inventory.ICounterMasterTransaction;
import com.kp.cms.transactionsimpl.inventory.CounterMasterTransactionImpl;

@SuppressWarnings("deprecation")
public class CounterMasterAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(CounterMasterAction.class);

	/**
	 * setting counterList
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         COUNTER_MASTER
	 * @throws Exception
	 */
	public ActionForward initCounterMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initCounterMaster ");
		CounterMasterForm counterForm = (CounterMasterForm) form;
		initFields(counterForm);
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			counterForm.setInvCompanyList(QuotationHandler.getInstance().getCompany());
			setCounterListRequest(request);
			setUserId(request, counterForm);
		} catch (Exception e) {
			log.error("error initCounterMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				counterForm.setErrorMessage(msg);
				counterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initCounterMaster ");
	
		return mapping.findForward(CMSConstants.COUNTER_MASTER);
	}
	
	/**
	 * 
	 * @param request
	 *            This method sets the counterList to Request used to display
	 *            disList record on UI.
	 * @throws Exception
	 */
	public void setCounterListRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setCounterListRequest");
		List<InvCounterTO> counterList = CounterMasterHandler.getInstance().getCounterMasterDetails();
		request.setAttribute("counterList", counterList);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  counter
	 * @return to mapping COUNTER_MASTER
	 * @throws Exception
	 */
	public ActionForward addCounterMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addCounterMaster Action");
		CounterMasterForm counterMasterForm = (CounterMasterForm) form;
		counterMasterForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = counterMasterForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			 if(counterMasterForm.getType()!=null && !counterMasterForm.getType().trim().isEmpty() && counterMasterForm.getType().equalsIgnoreCase(CMSConstants.PURCHASE_ORDER_COUNTER)){
				 if(counterMasterForm.getCompanyId()==null || counterMasterForm.getCompanyId().isEmpty()){
					 errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.InvCompany.required"));
				 }
			 }
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setCounterListRequest(request);
				//space should not get added in the table
				return mapping.findForward(CMSConstants.COUNTER_MASTER);
			}
			isAdded = CounterMasterHandler.getInstance().addCounterMaster(counterMasterForm, "add"); 
			setCounterListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.inventory.counter.master.exists", counterMasterForm.getType()));
			saveErrors(request, errors);
			setCounterListRequest(request);
			return mapping.findForward(CMSConstants.COUNTER_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.COUNTER_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setCounterListRequest(request);
			return mapping.findForward(CMSConstants.COUNTER_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of disciplinary type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				counterMasterForm.setErrorMessage(msg);
				counterMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.inventory.counter.master.addsuccess", counterMasterForm.getType());
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(counterMasterForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.inventory.counter.master.addfailure", counterMasterForm.getType()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addCounterMaster Action");
		return mapping.findForward(CMSConstants.COUNTER_MASTER);
	}
	/**
	 * initialize
	 * @param counterMasterForm
	 */
	
	public void initFields(CounterMasterForm counterMasterForm){
		counterMasterForm.setType(null);
		counterMasterForm.setStartNo(null);
		counterMasterForm.setPrefix(null);
		counterMasterForm.setIsEdit(false);
		counterMasterForm.setCurrentNo(null);
		counterMasterForm.setYear(null);
		counterMasterForm.setCompanyId(null);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will edit the existing counter
	 * @return to mapping COUNTER_MASTER
	 * @throws Exception
	 */
	public ActionForward updateCounterMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside updateCounterMaster Action");
		CounterMasterForm counterMasterForm = (CounterMasterForm) form;
		ActionMessages messages = new ActionMessages();
		if(isCancelled(request)){
			if (counterMasterForm.getId() != 0) {
				 int id = counterMasterForm.getId();
				 CounterMasterHandler.getInstance().editCounter(id,counterMasterForm);
				 setCounterListRequest(request);
				 request.setAttribute("operation", "edit");
				 return mapping.findForward(CMSConstants.COUNTER_MASTER);
			}
		}
		ActionErrors errors = counterMasterForm.validate(mapping, request);
		
		if(errors.isEmpty())
		validateCounterMaster(counterMasterForm,errors);
		boolean isUpdated = false;
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setCounterListRequest(request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.COUNTER_MASTER);
			}
			isUpdated = CounterMasterHandler.getInstance().addCounterMaster(counterMasterForm, "edit"); 
			setCounterListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.inventory.counter.master.exists", counterMasterForm.getType()));
			saveErrors(request, errors);
			setCounterListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, "edit");
			return mapping.findForward(CMSConstants.COUNTER_MASTER);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.COUNTER_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setCounterListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, "edit");
			return mapping.findForward(CMSConstants.COUNTER_MASTER);
		} catch (Exception e) {
			log.error("error in final submit of counter master type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				counterMasterForm.setErrorMessage(msg);
				counterMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.inventory.counter.master.updatesuccess", counterMasterForm.getType());
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(counterMasterForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.inventory.counter.master.updatefailure", counterMasterForm.getType()));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving updateCounterMaster Action");
		return mapping.findForward(CMSConstants.COUNTER_MASTER);
	}	

	/**
	 * @param counterMasterForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateCounterMaster(CounterMasterForm counterMasterForm,
			ActionErrors errors) throws Exception {
		if(counterMasterForm.getCurrentNo()!=null && !counterMasterForm.getCurrentNo().isEmpty()){
			int currentNo=Integer.parseInt(counterMasterForm.getCurrentNo());
			int startNo=Integer.parseInt(counterMasterForm.getStartNo());
			if(startNo>currentNo){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.currentno.startno.greater"));
			}else{
				boolean isExists=CounterMasterHandler.getInstance().checkCurrentNoExists(counterMasterForm);
				if(isExists){
					errors.add(CMSConstants.ERROR,new ActionError("errors.isExists"));
				}
			}
		}else{
			errors.add(CMSConstants.ERROR,new ActionError("errors.required","Current No"));
		}
	 if(counterMasterForm.getType()!=null && !counterMasterForm.getType().trim().isEmpty() && counterMasterForm.getType().equalsIgnoreCase(CMSConstants.PURCHASE_ORDER_COUNTER)){
			 if(counterMasterForm.getCompanyId()==null || counterMasterForm.getCompanyId().isEmpty()){
				 errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.InvCompany.required"));
			 }
		 }
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the counter
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCounter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteCounter");
		CounterMasterForm counterMasterForm = (CounterMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (counterMasterForm.getId() != 0) {
				int id = counterMasterForm.getId();
				isDeleted = CounterMasterHandler.getInstance().deleteCounter(id, false, counterMasterForm);
			}
		} catch (Exception e) {
			log.error("error in deleteCounter...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				counterMasterForm.setErrorMessage(msg);
				counterMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setCounterListRequest(request);;
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.inventory.counter.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(counterMasterForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.inventory.counter.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteCounter");
		return mapping.findForward(CMSConstants.COUNTER_MASTER);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the counter master
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateCounter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateCounter");
		CounterMasterForm counterMasterForm = (CounterMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (counterMasterForm.getDuplId() != 0) {
				int id = counterMasterForm.getDuplId();  //setting id for activate
				isActivated = CounterMasterHandler.getInstance().deleteCounter(id, true, counterMasterForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.COUNTER_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setCounterListRequest(request);;
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.COUNTER_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(counterMasterForm);
		}
		log.debug("leaving activateCounter");
		return mapping.findForward(CMSConstants.COUNTER_MASTER);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the counter
	 * @return
	 * @throws Exception
	 */
	public ActionForward editCounter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteCounter");
		CounterMasterForm counterMasterForm = (CounterMasterForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			if (counterMasterForm.getId() != 0) {
				 int id = counterMasterForm.getId();
				 CounterMasterHandler.getInstance().editCounter(id,counterMasterForm);
				 setCounterListRequest(request);
				 request.setAttribute("operation", "edit");
			}
		} catch (Exception e) {
			log.error("error in deleteCounter...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				counterMasterForm.setErrorMessage(msg);
				counterMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("leaving deleteCounter");
		return mapping.findForward(CMSConstants.COUNTER_MASTER);
	}
}
