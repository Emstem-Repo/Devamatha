package com.kp.cms.actions.fee;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.fee.InstallmentPaymentForm;
import com.kp.cms.handlers.fee.InstallmentPaymentHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.fee.InstallmentPaymentTO;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class InstallmentPaymentAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(InstallmentPaymentAction.class);
/**
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @returns initializes installment payment
 * @throws Exception
 */
public ActionForward initInstallmentPayment(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("Entering into initInstallmentPayment of InstallmentPaymentAction");
	InstallmentPaymentForm paymentForm = (InstallmentPaymentForm)form;
	paymentForm.clear();
	log.info("Leaving into initInstallmentPayment of InstallmentPaymentAction");
	return mapping.findForward(CMSConstants.FEE_INSTALLMENT_PAYMENT);
}

/**
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @returns initializes installment payment details
 * Also displays the old fee payment details along with the required fields
 * @throws Exception
 */
public ActionForward initInstallmentPaymentDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("Entering into initInstallmentPaymentDetails of InstallmentPaymentAction");
	InstallmentPaymentForm paymentForm = (InstallmentPaymentForm)form;
	ActionErrors errors = new ActionErrors();
	errors = paymentForm.validate(mapping, request);
	errors = validateApplicationNo(paymentForm, errors);
	try {
		if (errors.isEmpty()) {
			List<InstallmentPaymentTO> paymentTOList = InstallmentPaymentHandler.getInstance().getStudentPaymentDetails(paymentForm);
			if(paymentTOList!=null && !paymentTOList.isEmpty()){
				paymentForm.setPaymentTOList(paymentTOList);
				allPaymentTypeToForm(paymentForm);
				return mapping.findForward(CMSConstants.FEE_INSTALLMENT_PAYMENT_DETAILS);
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
			}
		}
	} catch (Exception e) {
		log.error("Error occured at initInstallmentPaymentDetails of InstallmentPaymentAction",e);
		String msg = super.handleApplicationException(e);
		paymentForm.setErrorMessage(msg);
		paymentForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	log.info("Leaving into initInstallmentPaymentDetails of InstallmentPaymentAction");
	addErrors(request, errors);
	return mapping.findForward(CMSConstants.FEE_INSTALLMENT_PAYMENT);
}
/**
 * 
 * @param paymentForm
 * @param errors
 * @returns errors for invalid application No.
 */
private ActionErrors validateApplicationNo(InstallmentPaymentForm paymentForm,
		ActionErrors errors)throws Exception {
	log.info("Entering into validateApplicationNo of InstallmentPaymentAction");
	if(paymentForm.getSearchBy() != null && !paymentForm.getSearchBy().isEmpty()
		&& paymentForm.getSearchByValue() != null && !paymentForm.getSearchByValue().isEmpty()){
		if(paymentForm.getSearchBy().equals("1") && !StringUtils.isNumeric(paymentForm.getSearchByValue())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_APPLICATION_NO_INTEGER));
		}
	}
	log.info("Leaving into validateApplicationNo of InstallmentPaymentAction");
	return errors;
}

/**
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * Submits the installment payment details
 * @throws Exception
 */
public ActionForward submitInstallMentPaymentDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("Entering into submitInstallMentPaymentDetails of InstallmentPaymentAction");
	InstallmentPaymentForm paymentForm = (InstallmentPaymentForm)form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	errors = validateInstallmentPaymentDetails(paymentForm, errors);
	try {
		if (errors.isEmpty()) {
			boolean isInstallmentPaid;
			isInstallmentPaid = InstallmentPaymentHandler.getInstance().submitInstallMentPaymentDetails(paymentForm);
			if (isInstallmentPaid) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.FEE_INSTALLMENT_PAYMENT_SUCCESS));
				addMessages(request, messages);
				paymentForm.clear();
				return mapping.findForward(CMSConstants.FEE_INSTALLMENT_PAYMENT);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAYMENT_FAILURE));
			}
		}
	} catch (Exception e) {
		log.error("Error occured at submitInstallMentPaymentDetails of InstallmentPaymentAction",e);
		String msg = super.handleApplicationException(e);
		paymentForm.setErrorMessage(msg);
		paymentForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	addErrors(request, errors);
	log.info("Leaving into submitInstallMentPaymentDetails of InstallmentPaymentAction");
	return mapping.findForward(CMSConstants.FEE_INSTALLMENT_PAYMENT_DETAILS);
}
/**
 * 
 * @param paymentForm
 * @param errors
 * @returns validation for properties
 * @throws Exception
 */
private ActionErrors validateInstallmentPaymentDetails(
		InstallmentPaymentForm paymentForm, ActionErrors errors)throws Exception {
		log.info("Entering into validateInstallmentPaymentDetails of InstallmentPaymentAction");
		if(paymentForm.getDate()==null || paymentForm.getDate().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAYMENT_DATE));
		}
		if(paymentForm.getDate()!=null && !paymentForm.getDate().isEmpty()){
		boolean isValidDateFormat;
		isValidDateFormat = CommonUtil.isValidDate(paymentForm.getDate());
			if(!isValidDateFormat){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));		
			}
		}
		if(paymentForm.getPaymentTOList()!=null){
			Iterator<InstallmentPaymentTO>iterator = paymentForm.getPaymentTOList().iterator();
			while (iterator.hasNext()) {
				InstallmentPaymentTO installmentPaymentTO = iterator.next();
				if(installmentPaymentTO.getPayMentTypeId()==null || installmentPaymentTO.getPayMentTypeId().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAYMENT_TYPE));
				}
				if(installmentPaymentTO.getFinancialYear()==null || installmentPaymentTO.getFinancialYear().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_FINANCIAL_YEAR));
				}
				if(installmentPaymentTO.getReferenceNo()==null || installmentPaymentTO.getReferenceNo().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_REFERENCE_NO));
				}
				if(installmentPaymentTO.getReferenceNo()!=null  && !installmentPaymentTO.getReferenceNo().isEmpty()){
					if(!StringUtils.isAlphanumeric(installmentPaymentTO.getReferenceNo())){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_REFERENCE_NO_ALPHANUMERIC));
					}
				}
				if(installmentPaymentTO.getTotalAmount()==null || installmentPaymentTO.getTotalAmount().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_TOTAL_AMOUNT));
				}
				if(installmentPaymentTO.getTotalAmount()!=null  && !installmentPaymentTO.getTotalAmount().isEmpty()){
					if(!StringUtils.isNumeric(installmentPaymentTO.getTotalAmount())){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_TOTAL_AMOUNT_INTEGER));
					}
					if(CommonUtil.isValidDecimal(installmentPaymentTO.getTotalAmount()) && installmentPaymentTO.getInstallMentAmount()!=null
					&& !installmentPaymentTO.getInstallMentAmount().isEmpty()){
						double totalAmount = Double.valueOf(installmentPaymentTO.getTotalAmount());
						double installmentAmount = Double.valueOf(installmentPaymentTO.getInstallMentAmount());
						if(totalAmount > installmentAmount){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_TOTAL_AMOUNT_GREATER));
						}
					}
				}
					double amountPaidTotal = 0.0;
					if(installmentPaymentTO.getAccountList()!=null){
						Iterator<AccountHeadTO> accountIterator = installmentPaymentTO.getAccountList().iterator();
						int count = 0;
						while (accountIterator.hasNext()) {
							AccountHeadTO accountHeadTO = accountIterator.next();
							if(accountHeadTO.getPaidAmount()==null || accountHeadTO.getPaidAmount().isEmpty()){
								//errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAID_AMOUNT));
								count++;
							}
							if(accountHeadTO.getPaidAmount()!=null || !accountHeadTO.getPaidAmount().isEmpty()){
								if(!CommonUtil.isValidDecimal(accountHeadTO.getPaidAmount())){
									errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAID_AMOUNT_INTEGER));
								}
							}
							if(!accountHeadTO.getPaidAmount().isEmpty() && !accountHeadTO.getBalanceAmount().isEmpty()
							&& CommonUtil.isValidDecimal(accountHeadTO.getPaidAmount()) && CommonUtil.isValidDecimal(accountHeadTO.getBalanceAmount())){
								double balanceAmount = Double.valueOf(accountHeadTO.getBalanceAmount());
								double paidAmount = Double.valueOf(accountHeadTO.getPaidAmount());
								amountPaidTotal = amountPaidTotal + paidAmount;
								if(paidAmount > balanceAmount){
									errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAID_AMOUNT_GREATER));
								}
							}
						}
						if(count == installmentPaymentTO.getAccountList().size()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_PAID_AMOUNT));
						}
						if(!installmentPaymentTO.getTotalAmount().isEmpty() && StringUtils.isNumeric(installmentPaymentTO.getTotalAmount()) &&
							amountPaidTotal!= Double.valueOf(installmentPaymentTO.getTotalAmount())){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_INSTALLMENT_TOTAL_AMOUNT_INVALID));
						}
					}
			}
		}
		log.info("Leaving into validateInstallmentPaymentDetails of InstallmentPaymentAction");
	return errors;
}
/**
 * 
 * @param form
 * @throws Exception
 * Gets all payment types and set to form
 */
public void allPaymentTypeToForm(ActionForm form) throws Exception
{	
	log.info("Inside of allPaymentTypeToForm");
	InstallmentPaymentForm paymentForm = (InstallmentPaymentForm)form;		
	List<SingleFieldMasterTO> paymentTypeList = InstallmentPaymentHandler.getInstance().getAllPaymentType();
	paymentForm.setPaymentTypeList(paymentTypeList);
	log.info("End of allPaymentTypeToForm");	
}

}
