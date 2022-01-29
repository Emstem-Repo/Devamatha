package com.kp.cms.actions.exam;

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
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.TempHallTicketOrIDCardForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.TempHallticketOrIDCardHandler;

public class TempHallTicketOrIDCardAction extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(TempHallTicketOrIDCardAction.class);
	private static final String PHOTOBYTES = "PhotoBytes";
	/**
	 * initBiometric() to display initial fields
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTempHallTicketOrIDCard(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.debug("Entering initTempHallTicketOrIDCard ");
		TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm = (TempHallTicketOrIDCardForm) form;
		tempHallTicketOrIDCardForm.setRegisterNO(null);
		tempHallTicketOrIDCardForm.setPrintHallTicketPage(null);
		tempHallTicketOrIDCardForm.setPrintIDCardPage(null);
		cleanupEditSessionData(request);
		clear(tempHallTicketOrIDCardForm);
		return mapping.findForward(CMSConstants.INIT_TEMPHALLTICKET_IDCARD);
	}
	public ActionForward printHallTicket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm = (TempHallTicketOrIDCardForm) form;
		ActionErrors errors = new ActionErrors();
		clear(tempHallTicketOrIDCardForm);
		cleanupEditSessionData(request);
		try {
			if(tempHallTicketOrIDCardForm.getRegisterNO()!=null && !tempHallTicketOrIDCardForm.getRegisterNO().isEmpty()){
				Boolean b=CommonAjaxHandler.getInstance().checkChildRegisterNo(tempHallTicketOrIDCardForm.getRegisterNO());
				if(b){
						setUserId(request, tempHallTicketOrIDCardForm);
						HttpSession session=request.getSession();
						TempHallticketOrIDCardHandler.getInstance().printHallticket(tempHallTicketOrIDCardForm);
						if(tempHallTicketOrIDCardForm.getPhotoBytes()!=null){
							session.setAttribute(TempHallTicketOrIDCardAction.PHOTOBYTES, tempHallTicketOrIDCardForm.getPhotoBytes());
						}
						tempHallTicketOrIDCardForm.setRegisterNO(null);
						tempHallTicketOrIDCardForm.setPrintHallTicketPage("true");
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.print.valid.registerNo"));
					saveErrors(request, errors);
				}
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.UnvRegEntry.reg_entry"));
				saveErrors(request, errors);
			}
				return mapping.findForward(CMSConstants.INIT_TEMPHALLTICKET_IDCARD);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			tempHallTicketOrIDCardForm.setErrorMessage(msg);
			tempHallTicketOrIDCardForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	public ActionForward printHallTicketDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm = (TempHallTicketOrIDCardForm) form;
		tempHallTicketOrIDCardForm.setPrintHallTicketPage(null);
		return mapping.findForward(CMSConstants.PRINT_HALLTICKET);
	}
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(TempHallTicketOrIDCardAction.PHOTOBYTES) != null)
				session.removeAttribute(TempHallTicketOrIDCardAction.PHOTOBYTES);
		}
	}
public void clear(TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm){
	tempHallTicketOrIDCardForm.setRegNo(null);
	tempHallTicketOrIDCardForm.setClassName(null);
	tempHallTicketOrIDCardForm.setDate(null);
	tempHallTicketOrIDCardForm.setNameOfStudent(null);
	tempHallTicketOrIDCardForm.setPhotoBytes(null);
}
public ActionForward printIDCard(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm = (TempHallTicketOrIDCardForm) form;
	ActionErrors errors = new ActionErrors();
	clear(tempHallTicketOrIDCardForm);
	cleanupEditSessionData(request);
	try {
		if(tempHallTicketOrIDCardForm.getRegisterNO()!=null && !tempHallTicketOrIDCardForm.getRegisterNO().isEmpty()){
		Boolean b=CommonAjaxHandler.getInstance().checkChildRegisterNo(tempHallTicketOrIDCardForm.getRegisterNO());
			if(b){
				setUserId(request, tempHallTicketOrIDCardForm);
				HttpSession session=request.getSession();
				TempHallticketOrIDCardHandler.getInstance().printHallticket(tempHallTicketOrIDCardForm);
				if(tempHallTicketOrIDCardForm.getPhotoBytes()!=null){
					session.setAttribute(TempHallTicketOrIDCardAction.PHOTOBYTES, tempHallTicketOrIDCardForm.getPhotoBytes());
				}
					tempHallTicketOrIDCardForm.setRegisterNO(null);
					tempHallTicketOrIDCardForm.setPrintIDCardPage("true");
				}else{
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.print.valid.registerNo"));
						saveErrors(request, errors);
			}
		}else{
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.UnvRegEntry.reg_entry"));
			saveErrors(request, errors);
		}
			return mapping.findForward(CMSConstants.INIT_TEMPHALLTICKET_IDCARD);

	} catch (Exception exception) {
		String msg = super.handleApplicationException(exception);
		tempHallTicketOrIDCardForm.setErrorMessage(msg);
		tempHallTicketOrIDCardForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	
}
public ActionForward printIDCardDetails(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm = (TempHallTicketOrIDCardForm) form;
	tempHallTicketOrIDCardForm.setPrintIDCardPage(null);
	return mapping.findForward(CMSConstants.PRINT_IDCARD);
}

}
