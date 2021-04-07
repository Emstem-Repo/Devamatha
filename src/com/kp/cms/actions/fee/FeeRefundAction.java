package com.kp.cms.actions.fee;

import java.util.Map;

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
import com.kp.cms.bo.fees.FeeRefund;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.fee.FeeRefundForm;
import com.kp.cms.handlers.fee.FeeRefundHandler;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class FeeRefundAction extends BaseDispatchAction {
	
	private static Log log = LogFactory.getLog(FeeRefundAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeRefund(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				FeeRefundForm refundForm=(FeeRefundForm) form;
				refundForm.reset();
				log.info("initFeeRefund Method Called");
		  try{
			    setRequiredDataToForm(refundForm);
		    }catch (Exception e) {
		    	log.error("error in editing venueDetails...", e);
				String msg = super.handleApplicationException(e);
				refundForm.setErrorMessage(msg);
				refundForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
		
		return mapping.findForward(CMSConstants.INIT_FEE_REFUND);
	}
	
	/**
	 * @param refundForm
	 * @throws Exception
	 */
	public void setRequiredDataToForm(FeeRefundForm refundForm) throws Exception{
		Map<Integer, String> paymentModeMap=FeeRefundHandler.getInstance().getPayMentModeMap();
		refundForm.setPaymentModeMap(paymentModeMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentByChallanNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeRefundForm refundForm=(FeeRefundForm) form;
		ActionErrors errors=new ActionErrors();
		setUserId(request, refundForm);
		try {
			boolean isNext=FeeRefundHandler.getInstance().getStudentDetailsByChallanNo(refundForm);
			if(isNext){
				if(refundForm.getStudentList()==null){
				boolean isExist=FeeRefundHandler.getInstance().getRefunDetailsIfExist(refundForm);
				if(isExist){
					request.setAttribute("operation", "alreadyExistRecord");
				}else{
					request.setAttribute("operation", "continueToSavingRecord");
				}
				}else{
					request.setAttribute("operation", "continueToSavingRecord");
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.fee.refund.error.occured.student"));
				addErrors(request, errors);
				refundForm.reset();
			}
		
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			refundForm.setErrorMessage(msg);
			refundForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FEE_REFUND);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitStudentRefundAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeRefundForm refundForm=(FeeRefundForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, refundForm);
		String mode="Add";
		try {
			boolean isSaved=FeeRefundHandler.getInstance().saveOrUpdateFeeRefundAmount(refundForm,mode);
			if(isSaved){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.fee.refund.save.successfully"));
				saveMessages(request, messages);
				refundForm.reset();
				setRequiredDataToForm(refundForm);
			}else{
				errors.add("error", new ActionError("knowledgepro.fee.refund.addFailure"));
				addErrors(request, errors);
				refundForm.reset();
				setRequiredDataToForm(refundForm);
			}
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			refundForm.setErrorMessage(msg);
			refundForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FEE_REFUND);
	}

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkChallanNoAlreadyExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeRefundForm refundForm=(FeeRefundForm) form;
		setUserId(request, refundForm);
		try {
			FeeRefund refund=FeeRefundHandler.getInstance().checkChallanNoAlreadyExist(refundForm);
			if(refund!=null){
				request.setAttribute("refundAmount", String.valueOf(refund.getRefundAmount()));
				request.setAttribute("refundMode", String.valueOf(refund.getRefundMode().getId()));
				request.setAttribute("refundDate", CommonUtil.formatDates(refund.getRefundDate()));
				request.setAttribute("refundId", String.valueOf(refund.getId()));
				request.setAttribute("operation", "alreadyExistRecord");
			}else{
				request.setAttribute("msg", "no record found");
			}
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
		}
		return mapping.findForward("getFeeRefundDetailsByAjax");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStudentRefundAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeRefundForm refundForm=(FeeRefundForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, refundForm);
		String mode="Update";
		try {
			boolean isUpdated=FeeRefundHandler.getInstance().saveOrUpdateFeeRefundAmount(refundForm,mode);
			if(isUpdated){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.fee.refund.updated.successfully"));
				saveMessages(request, messages);
				refundForm.reset();
				setRequiredDataToForm(refundForm);
			}else{
				errors.add("error", new ActionError("knowledgepro.fee.refund.update.failure"));
				addErrors(request, errors);
				refundForm.reset();
				setRequiredDataToForm(refundForm);
			}
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			refundForm.setErrorMessage(msg);
			refundForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FEE_REFUND);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFeeRefundDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeRefundForm refundForm=(FeeRefundForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, refundForm);
		String mode="Delete";
		try {
			boolean isDeleted=FeeRefundHandler.getInstance().deleteFeeRefundAmount(refundForm,mode);
			if(isDeleted){
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.fee.refund.deleted.successfully"));
				saveMessages(request, messages);
				refundForm.reset();
				setRequiredDataToForm(refundForm);
			}else{
				errors.add("error", new ActionError("knowledgepro.fee.refund.delete.failure"));
				addErrors(request, errors);
				refundForm.reset();
				setRequiredDataToForm(refundForm);
			}
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			refundForm.setErrorMessage(msg);
			refundForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FEE_REFUND);
	}
}
