package com.kp.cms.actions.fee;

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
import com.kp.cms.actions.inventory.CounterMasterAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.ConcessionSlipBooksForm;
import com.kp.cms.handlers.fee.ConcessionSlipBooksHandler;
import com.kp.cms.to.fee.FeeVoucherTO;

@SuppressWarnings("deprecation")
public class ConcessionSlipBooksAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CounterMasterAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         
	 * @throws Exception
	 */
	public ActionForward initConcessionSlipBooks(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initConcessionSlipBooks ");
		ConcessionSlipBooksForm slipBooksForm = (ConcessionSlipBooksForm) form;
		initFields(slipBooksForm);
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setSlipBookListRequest(request);
			setUserId(request, slipBooksForm);
		} catch (Exception e) {
			log.error("error initConcessionSlipBooks...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				slipBooksForm.setErrorMessage(msg);
				slipBooksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initConcessionSlipBooks ");
	
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new  concession slip book
	 * @return to mapping 
	 * @throws Exception
	 */
	public ActionForward addConcSlipBooks(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addConcSlipBooks Action");
		ConcessionSlipBooksForm slipBooksForm = (ConcessionSlipBooksForm) form;
		slipBooksForm.setId(0);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = slipBooksForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(isCancelled(request)){
				ConcessionSlipBooksHandler.getInstance().setCurrentfinancialYear(slipBooksForm);
				slipBooksForm.setType(null);
				slipBooksForm.setBookNo(null);
				slipBooksForm.setStartingNo(null);
				slipBooksForm.setEndingNo(null);
				slipBooksForm.setStartPrefix(null);
				slipBooksForm.setEndPrefix(null);
				setSlipBookListRequest(request);
				return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setSlipBookListRequest(request);
				return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
			}
			int startNo = 0;
			int endNo = 0;
			if(slipBooksForm.getStartingNo()!= null && !slipBooksForm.getStartingNo().trim().isEmpty()){
				startNo = 	Integer.parseInt(slipBooksForm.getStartingNo());
			}
			if(slipBooksForm.getEndingNo()!= null && !slipBooksForm.getEndingNo().trim().isEmpty()){
				endNo = Integer.parseInt(slipBooksForm.getEndingNo());
			}
			if(endNo < startNo){
				setSlipBookListRequest(request);
				errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.end.greater.start"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
			}
			
			isAdded = ConcessionSlipBooksHandler.getInstance().addConcSlipBooks(slipBooksForm, "add");
			setSlipBookListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.fee.concession.slip..book.exists", slipBooksForm.getBookNo()));
			saveErrors(request, errors);
			setSlipBookListRequest(request);
			return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.CONCESSION_SLIP_BOOKS_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setSlipBookListRequest(request);
			return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
		} catch (Exception e) {
			log.error("error in final submit of addConcSlipBooks type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				slipBooksForm.setErrorMessage(msg);
				slipBooksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.fee.concession.slip.book.addsuccess", slipBooksForm.getBookNo());
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(slipBooksForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.addfailure", slipBooksForm.getBookNo()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addCounterMaster Action");
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
	}	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update concession slip book
	 * @return to mapping 
	 * @throws Exception
	 */
	public ActionForward updateConcSlipBooks(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside updateConcSlipBooks Action");
		ConcessionSlipBooksForm slipBooksForm = (ConcessionSlipBooksForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = slipBooksForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setSlipBookListRequest(request);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
			}
			int startNo = 0;
			int endNo = 0;
			if(slipBooksForm.getStartingNo()!= null && !slipBooksForm.getStartingNo().trim().isEmpty()){
				startNo = 	Integer.parseInt(slipBooksForm.getStartingNo());
			}
			if(slipBooksForm.getEndingNo()!= null && !slipBooksForm.getEndingNo().trim().isEmpty()){
				endNo = Integer.parseInt(slipBooksForm.getEndingNo());
			}
			if(endNo < startNo){
				setSlipBookListRequest(request);
				errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.end.greater.start"));
				saveErrors(request, errors);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
			}
			
			isUpdated = ConcessionSlipBooksHandler.getInstance().addConcSlipBooks(slipBooksForm, "edit");
			setSlipBookListRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.fee.concession.slip..book.exists", slipBooksForm.getBookNo()));
			saveErrors(request, errors);
			setSlipBookListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, "edit");
			return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.CONCESSION_SLIP_BOOKS_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setSlipBookListRequest(request);
			request.setAttribute(CMSConstants.OPERATION, "edit");
			return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
		} catch (Exception e) {
			log.error("error in final submit of addConcSlipBooks type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				slipBooksForm.setErrorMessage(msg);
				slipBooksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.fee.concession.slip.book.addsuccess", slipBooksForm.getBookNo());
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(slipBooksForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.addfailure", slipBooksForm.getBookNo()));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving addCounterMaster Action");
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
	}	
	
	/**
	 * 
	 * @param slipBooksForm
	 */
	public void initFields(ConcessionSlipBooksForm slipBooksForm){
		slipBooksForm.setBookNo(null);
		slipBooksForm.setStartingNo(null);
		slipBooksForm.setEndingNo(null);
		slipBooksForm.setType(null);
		slipBooksForm.setStartPrefix(null);
		slipBooksForm.setEndPrefix(null);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the slipBookList to Request used to display
	 *            disList record on UI.
	 * @throws Exception
	 */
	public void setSlipBookListRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setSlipBookListRequest");
		List<FeeVoucherTO> feeVoucherList = ConcessionSlipBooksHandler.getInstance().getConcessionSlipBookDetails();
		request.setAttribute("feeVoucherList", feeVoucherList);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the books
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSlipBooks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteSlipBooks");
		ConcessionSlipBooksForm slipBooksForm = (ConcessionSlipBooksForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (slipBooksForm.getId() != 0) {
				int id = slipBooksForm.getId();
				isDeleted = ConcessionSlipBooksHandler.getInstance().deleteSlipBook(id, false, slipBooksForm); 
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
				slipBooksForm.setErrorMessage(msg);
				slipBooksForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setSlipBookListRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.fee.concession.slip.book.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(slipBooksForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteSlipBooks");
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the slip book
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateSlipBooks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateSlipBooks");
		ConcessionSlipBooksForm slipBooksForm = (ConcessionSlipBooksForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (slipBooksForm.getDuplId() != 0) {
				int id = slipBooksForm.getDuplId();  //setting id for activate
				isActivated = ConcessionSlipBooksHandler.getInstance().deleteSlipBook(id, true, slipBooksForm); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.CONCESSION_SLIP_BOOKS_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setSlipBookListRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.CONCESSION_SLIP_BOOKS_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(slipBooksForm);
		}
		log.debug("leaving activateSlipBooks");
		return mapping.findForward(CMSConstants.CONCESSION_SLIP_BOOKS);
	}
	
	
}
