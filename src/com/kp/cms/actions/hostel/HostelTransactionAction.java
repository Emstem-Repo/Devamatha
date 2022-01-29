package com.kp.cms.actions.hostel;

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
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.forms.hostel.HostelTransactionForm;
import com.kp.cms.handlers.hostel.HlAdmissionHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelTransactionHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelTransactionTo;

public class HostelTransactionAction extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(HostelTransactionAction.class);
	
	
	public ActionForward initHostelTransaction(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHostelTransaction method in HostelTransactionAction class.");
		HostelTransactionForm hlTransactionForm=(HostelTransactionForm) form;
		hlTransactionForm.resetFields();
		setHostelToRequest(request,hlTransactionForm);
		log.debug("Leaving initHostelTransaction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_TRANSACTION);
	}
	public void setHostelToRequest(HttpServletRequest request, HostelTransactionForm hlTransactionForm)throws Exception
	{
			log.debug("start setHostelEntryDetailsToRequest");
			List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
			hlTransactionForm.setHostelList(hostelList);
			log.debug("exit setHostelEntryDetailsToRequest");
	}
	
	public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering getStudentDetails");
		HostelTransactionForm hlTransactionForm=(HostelTransactionForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = hlTransactionForm.validate(mapping, request);
		validateStudentData(hlTransactionForm,errors,request);
		if(errors.isEmpty()){
		try {
			if(((hlTransactionForm.getRegno()==null) && (hlTransactionForm.getRegno().isEmpty()))){
			boolean isValidStudent=HostelTransactionHandler.getInstance().checkValidStudentRegNo(hlTransactionForm);
			if(!isValidStudent){
				errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Register No"));
				saveErrors(request, errors);
				setHostelToRequest(request,hlTransactionForm);	
				return mapping.findForward(CMSConstants.INIT_HOSTEL_TRANSACTION);
				}
			}
			List<HostelTransactionTo> studentDetailList =HostelTransactionHandler.getInstance().getstudentDetails(hlTransactionForm);
			if (studentDetailList == null || studentDetailList.isEmpty()) {
				ActionMessage message = new ActionMessage("knowledgepro.hostel.transaction.student.not.checked.in");
				messages.add("messages", message);
				saveMessages(request, messages);
			} 
			hlTransactionForm.setStudentList(studentDetailList);
		} catch (Exception e) {
			log.error("error submit getStudentDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hlTransactionForm.setErrorMessage(msg);
				hlTransactionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				hlTransactionForm.setErrorMessage(msg);
				hlTransactionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		}else{
			saveErrors(request, errors);
			log.info("Exit from getStudentDetails");
			return mapping.findForward(CMSConstants.INIT_HOSTEL_TRANSACTION);
		}
		
		log.debug("Action class. exit getStudentDetails ");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_TRANSACTION);
	}
	
	public ActionForward transactionImages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering transactionImages");
		
		log.debug(" exit transactionImages ");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_TRANSACTION_IMAGES);
	}
	public void validateStudentData(HostelTransactionForm hlTransactionForm,ActionErrors errors,HttpServletRequest request){
		if( ( (hlTransactionForm.getStudentName()==null) || (hlTransactionForm.getStudentName().isEmpty())) && ((hlTransactionForm.getRegno()==null) || (hlTransactionForm.getRegno().isEmpty())) &&((hlTransactionForm.getHostelName()==null) || (hlTransactionForm.getHostelName().isEmpty())) ){
			errors.add("error", new ActionError("knowledgepro.hostel.transaction.required.student.data"));
		}
		saveErrors(request, errors);
	}
}
