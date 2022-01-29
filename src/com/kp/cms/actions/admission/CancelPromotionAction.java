package com.kp.cms.actions.admission;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CancelPromotionForm;
import com.kp.cms.handlers.admission.CancelAdmissionHandler;
import com.kp.cms.to.admission.CancelPromotionTo;

public class CancelPromotionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CancelPromotionAction.class);
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initCancelPromotion(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	CancelPromotionForm cancelPromotionForm = (CancelPromotionForm)form;
	setUserId(request, cancelPromotionForm);
	try{
		cancelPromotionForm.setRegisterNo(null);
		List<CancelPromotionTo> list= null;
		cancelPromotionForm.setCancelPromotionTo(list);
	}catch (Exception e) {
		log.error("error in initCancelPromotion...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			cancelPromotionForm.setErrorMessage(msg);
			cancelPromotionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_CANCEL_PROMOTION);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward searchCancelPromotion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	CancelPromotionForm cancelPromotionForm = (CancelPromotionForm)form;
	setUserId(request, cancelPromotionForm);
	ActionErrors errors = new ActionErrors();
	try{
		if(cancelPromotionForm.getRegisterNo()==null || cancelPromotionForm.getRegisterNo().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.admission.cancelPromotion.isrequired"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CANCEL_PROMOTION);
		}
		List<CancelPromotionTo> cancelPromotionTos =CancelAdmissionHandler.getInstance().searchCancelPromotion(cancelPromotionForm);
		if(!cancelPromotionTos.isEmpty()){
			cancelPromotionForm.setCancelPromotionTo(cancelPromotionTos);
		}else{
			errors.add("error", new ActionError("knowledgepro.admission.cancelPromotion.norecord"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CANCEL_PROMOTION);
		}
		
	} catch (ApplicationException e) {
		log.error("error in searchCancelPromotion...", e);
		String msg = super.handleApplicationException(e);
		cancelPromotionForm.setErrorMessage(msg);
		cancelPromotionForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception e) {
		log.error("error in searchCancelPromotion...", e);
		throw e;

	}
	log.info("exit searchCancelPromotion..");
	return mapping.findForward(CMSConstants.INIT_CANCEL_PROMOTION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward cancelPromotion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
	CancelPromotionForm cancelPromotionForm = (CancelPromotionForm)form;
	setUserId(request, cancelPromotionForm);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	@SuppressWarnings("unused")
	boolean isCancelled;
	try{
		int stuId= 0;
		int semNo= 0;
		int admappln=0;
		Iterator<CancelPromotionTo> iterator = cancelPromotionForm.getCancelPromotionTo().iterator();
		while (iterator.hasNext()) {
			CancelPromotionTo cancelPromotionTo = (CancelPromotionTo) iterator .next();
			stuId=cancelPromotionTo.getStudentId();
			semNo=cancelPromotionTo.getSemesterNo()-1;
			admappln = cancelPromotionTo.getAdmapplnId();
		}
		CancelPromotionTo promotionTo=CancelAdmissionHandler.getInstance().getPreviousClassDetails(stuId,semNo);
		//CancelPromotionTo CanProTo=CancelAdmissionHandler.getInstance().getClassSchemeWiseDetails(promotionTo);
		isCancelled=CancelAdmissionHandler.getInstance().cancelPromotionDetails(admappln,stuId,promotionTo,semNo);
		if(isCancelled){
				ActionMessage actionMessage = new ActionMessage("knowledgepro.admission.cancelpromotion.updatesuccess");
				messages.add("messages", actionMessage);
				saveMessages(request, messages);
				cancelPromotionForm.setRegisterNo(null);
				List<CancelPromotionTo> list= null;
				cancelPromotionForm.setCancelPromotionTo(list);
			}else{
				errors.add("error", new ActionError("knowledgepro.admission.cancelpromotion.updatefailure"));
				saveErrors(request, errors);
			}
	}catch (Exception e) {
		log.error("error in final submit of department page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			cancelPromotionForm.setErrorMessage(msg);
			cancelPromotionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	log.info("exit cancelPromotion..");
	return mapping.findForward(CMSConstants.INIT_CANCEL_PROMOTION);
	}
}
