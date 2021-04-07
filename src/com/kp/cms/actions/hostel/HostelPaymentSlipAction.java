package com.kp.cms.actions.hostel;

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
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelPaymentSlipForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelPaymentSlipHandler;
import com.kp.cms.to.hostel.HostelPaymentSlipTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelPaymentSlipTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelPaymentSlipTransactionImpl;

public class HostelPaymentSlipAction extends BaseDispatchAction{
	
	private static Log log = LogFactory.getLog(HostelPaymentSlipAction.class);
	
	public ActionForward initHostelPaymentSlip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		log.info("start  initHostelPaymentSlip ");
		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm) form;
		hostelPaymentSlipForm.resetFields();
		setAllHostelsToForm(hostelPaymentSlipForm);
		setUserId(request, hostelPaymentSlipForm);
		log.info("exit initHostelPaymentSlip");
		return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_INIT_PAGE);
	}
	
	public void setAllHostelsToForm(HostelPaymentSlipForm hostelPaymentSlipForm)throws Exception {
		log.info("start setAllHostelsToForm of HostelPaymentSlipAction");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		hostelPaymentSlipForm.setHostelTOList(hostelList);
		log.info("exit setAllHostelsToForm of HostelPaymentSlipAction");
	}
	
	public ActionForward getBillNumbers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		log.info("start  hostelPaymentSlip of HostelPaymentSlipAction");
		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm)form;
		 ActionErrors errors = hostelPaymentSlipForm.validate(mapping, request);
		validateHostelPaymentSlip(hostelPaymentSlipForm,errors);
		try { 
			if (errors.isEmpty()) { 
				List<HostelPaymentSlipTO> billNumberList = HostelPaymentSlipHandler.getInstance().getBillNumbersTO(hostelPaymentSlipForm);
				if (billNumberList.size() == 0) 
				{
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_INIT_PAGE);
				}
				else 
				{
					hostelPaymentSlipForm.setBillNumberList(billNumberList);
					return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_BILL_NUMBER_PAGE);
				}
			} 
			else {
				saveErrors(request, errors);
				setAllHostelsToForm(hostelPaymentSlipForm);
				log.info("exit setAllHostelsToForm Hostel Damage with errors");
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_INIT_PAGE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelPaymentSlipForm.setErrorMessage(msg);
			hostelPaymentSlipForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	
	public ActionForward getHostelPaymentDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		log.info("start  hostelPaymentSlip of HostelPaymentSlipAction");
		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm)form;
		ActionErrors errors = hostelPaymentSlipForm.validate(mapping, request);
		validateHostelPaymentSlip(hostelPaymentSlipForm,errors);
		try { 
			if (errors.isEmpty()) { 
				HostelPaymentSlipTO paymentSlipTO = HostelPaymentSlipHandler.getInstance( )
						.getHostelPaymentSlipTO(hostelPaymentSlipForm);
				if (paymentSlipTO == null) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_BILL_NUMBER_PAGE);
				} else {
					
					hostelPaymentSlipForm.setHostelPaymentSlipTO(paymentSlipTO);
					/*if(paymentSlipTO.getFeeDetailsTO().size()<1){
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));	
					}*/
					log.info("exit setAllHostelsToForm");
					return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_PAGE);
				}
			} 
			else {
				saveErrors(request, errors);
				setAllHostelsToForm(hostelPaymentSlipForm);
				log.info("exit setAllHostelsToForm Hostel Damage with errors");
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_INIT_PAGE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelPaymentSlipForm.setErrorMessage(msg);
			hostelPaymentSlipForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	
	private void validateHostelPaymentSlip(HostelPaymentSlipForm hostelPaymentSlipForm,
			ActionErrors errors) {
		if((hostelPaymentSlipForm.getApplicationNo().trim()==null || hostelPaymentSlipForm.getApplicationNo().trim().isEmpty()) && 
			(hostelPaymentSlipForm.getRegisterNo().trim()==null || hostelPaymentSlipForm.getRegisterNo().trim().isEmpty())
			&& (hostelPaymentSlipForm.getStaffId().trim()==null || hostelPaymentSlipForm.getStaffId().trim().isEmpty())
			&&(hostelPaymentSlipForm.getRollNo().trim()==null || hostelPaymentSlipForm.getRollNo().trim().isEmpty())){
			if (errors.get(CMSConstants.APPLICATION_REG_ROLLNO_STAFF) != null&& !errors.get(CMSConstants.APPLICATION_REG_ROLLNO_STAFF).hasNext()) {
				errors.add(CMSConstants.APPLICATION_REG_ROLLNO_STAFF,new ActionError(CMSConstants.APPLICATION_REG_ROLLNO_STAFF));
			}
		}
	}
	
	public ActionForward getChallanPrintData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{

		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(isCancelled(request))
			{
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_BILL_NUMBER_PAGE);
			}
			if (errors.isEmpty()) {
				
			//HostelPaymentSlipHandler.getInstance( ).setBillNoToHlApplicationForm(hostelPaymentSlipForm);	
			ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_PAYMENT_PRINT_CHALLAN_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_PAGE);
			} 
			else {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_PAGE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelPaymentSlipForm.setErrorMessage(msg);
			hostelPaymentSlipForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	public ActionForward printChallan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			if (errors.isEmpty()) {
				
			   HostelPaymentSlipHandler.getInstance( ).setBillNoToHlApplicationForm(hostelPaymentSlipForm);
			   return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_PRINT_CHALLAN);
			} 
			else {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_SLIP_PAGE);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelPaymentSlipForm.setErrorMessage(msg);
			hostelPaymentSlipForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	public ActionForward hostelPaymentMarkForPaidInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm) form;
		hostelPaymentSlipForm.resetFields2();
		return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_MARK_FOR_PAID_INIT);
	}
	
	public ActionForward hostelPaymentMarkForPaid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		HostelPaymentSlipForm hostelPaymentSlipForm = (HostelPaymentSlipForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelPaymentSlipForm.validate(mapping, request);
//		validateBillNo(hostelPaymentSlipForm,errors);
		boolean ispaid=false;
		try {
			if (errors.isEmpty()) {
				if(hostelPaymentSlipForm.getSearchBy().equals("1")){
					HlApplicationForm hlApplicationForm=HostelPaymentSlipHandler.getInstance().getHlApplicationForm(hostelPaymentSlipForm);
					if(hlApplicationForm!=null)
					{
						if(hlApplicationForm.getIsHostelFeePaid()==null)
						{	
							ispaid=HostelPaymentSlipHandler.getInstance().setHostelFeePaidInHlApplicationForm(hlApplicationForm);
						}
						else
						{
							errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_PAYMENT_NO_RECORD_FOUND));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_MARK_FOR_PAID_INIT);
						}
					}
					else
					{
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_PAYMENT_NO_RECORD_FOUND));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_MARK_FOR_PAID_INIT);
					}
				}else{
					IHostelPaymentSlipTransaction transaction=HostelPaymentSlipTransactionImpl.getInstance();
					boolean isValid=transaction.isValidBillNo(hostelPaymentSlipForm.getBillNo());
					if(!isValid){
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_PAYMENT_NO_RECORD_FOUND));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_MARK_FOR_PAID_INIT);
					}
					ispaid=transaction.markFinePaid(hostelPaymentSlipForm);
				}
				if(ispaid){
					ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_PAYMENT_MARK_AS_PAID);
					messages.add("messages", message);
					saveMessages(request, messages);
					hostelPaymentSlipForm.reset(mapping, request);
					hostelPaymentSlipForm.resetFields2();
				}
				/*boolean markAsPaid=HostelPaymentSlipHandler.getInstance().ifAlreadyMarkAsPaid(hostelPaymentSlipForm);
				if(markAsPaid){
					ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_PAYMENT_ALREADY_MARK_AS_PAID);
					messages.add("messages", message);
					saveMessages(request, messages);
					hostelPaymentSlipForm.reset(mapping, request);
					hostelPaymentSlipForm.resetFields2();
				}*/
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_MARK_FOR_PAID);
			} 
			else {
				saveErrors(request, errors);
				log.info("exit hostelPaymentMarkForPaid  with errors");
				return mapping.findForward(CMSConstants.HOSTEL_PAYMENT_MARK_FOR_PAID);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hostelPaymentSlipForm.setErrorMessage(msg);
			hostelPaymentSlipForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	
	private void validateBillNo(HostelPaymentSlipForm hostelPaymentSlipForm,ActionErrors errors)throws Exception {
			
		if(hostelPaymentSlipForm.getBillNo().trim()!=null && !hostelPaymentSlipForm.getBillNo().trim().isEmpty()&& StringUtils.isNumeric(hostelPaymentSlipForm.getBillNo().trim())){
			
			if(hostelPaymentSlipForm.getBillNo().length()!=5)
			{
				if (errors.get(CMSConstants.HOSTEL_PAYMENT_BILL_NO_INVALID) != null&& !errors.get(CMSConstants.HOSTEL_PAYMENT_BILL_NO_INVALID).hasNext()) {
					errors.add(CMSConstants.HOSTEL_PAYMENT_BILL_NO_INVALID,new ActionError(CMSConstants.HOSTEL_PAYMENT_BILL_NO_INVALID));
				}
			}
		}
	}
		
	
}
