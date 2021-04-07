package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.ComplaintsForm;
import com.kp.cms.forms.hostel.VisitorInfoForm;
import com.kp.cms.handlers.hostel.ComplaintsHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactionsimpl.hostel.ComplaintsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ComplaintsAction extends BaseDispatchAction{
	
	private static final Log  log =  LogFactory.getLog(ComplaintsAction.class);
	
	private static final String  DATEFORMAT = "dd/MM/yyyy"; 
	
	/**
	 * This method is used to display the complaints page.
	 * @param  mapping
	 * @param  form
	 * @param  request
	 * @param  response
	 * @return
	 * @throws  Exception
	 */
	
	public ActionForward initComplaints(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,  HttpServletResponse response)
			throws Exception {
		log.info("entering of initComplaints in ComplaintsAction class...");
		ComplaintsForm complaintsForm = (ComplaintsForm)form;
		try {
 			 getComplaintTypeList(complaintsForm);
			 setHostelListToRequest(request, complaintsForm);
			 getComplaintsDetails(complaintsForm);
		} catch (BusinessException businessException) {
			  log.error("error occured in initComplaints of ComplaintsAction class.",businessException);
			  String  msgKey = super.handleBusinessException(businessException);
			  complaintsForm.setErrorMessage(msgKey);
			  complaintsForm.setErrorStack(businessException.getMessage());
			  return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			  log.error("error occured in initComplaints of ComplaintsAction class.",exception);
			  String msg = super.handleApplicationException(exception);
			  complaintsForm.setErrorMessage(msg);
			  complaintsForm.setErrorStack(exception.getMessage());
			  return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		  log.info("exit of initComplaints in ComplaintsAction class...");
		  return mapping.findForward(CMSConstants.HOSTEL_COMPLAINTS);
	}
	
	/**
	 * This method is used to save the complaints to database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitComplaints(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of submitComplaints in ComplaintsAction class...");
		ComplaintsForm complaintsForm = (ComplaintsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		validateComplaints(complaintsForm,errors);
		validateFormSpecialCharacter(complaintsForm, errors, request);
		boolean isDuplicate;
		if (errors.isEmpty()) {	
			try {
				 setUserId(request, complaintsForm);
				 complaintsForm.setLogDate(CommonUtil.formatDate(new Date(),DATEFORMAT));
				 isDuplicate=ComplaintsTransactionImpl.getInstance().duplicateStudentCheck(complaintsForm);
				 if(isDuplicate)
				 {
					 errors.add("errors", new ActionError(CMSConstants.COMPLAINT_DETAILS_EXISTS));
					 saveErrors(request, errors);
				 }else 
				 if(errors.isEmpty())
				 {
				HlApplicationForm hlApplicationFormBo = ComplaintsTransactionImpl.getInstance().StudentCheck(complaintsForm);
				if(hlApplicationFormBo == null)
				{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_COMPLAINTS_REQUISITION_NOTPROPER));
					saveErrors(request, errors);
				}
				else {
				 boolean isAdded = ComplaintsHandler.getInstance().saveComplaintDetails(complaintsForm,hlApplicationFormBo);
				 if(isAdded){
					 ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_COMPLAINTS_SUCCESS);// Adding success message.
					 messages.add("messages", message);
					 saveMessages(request, messages);
					 complaintsForm.reset(mapping, request);
				}else{
					ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_COMPLAINTS_FAILURE);// Adding failure message.
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				}
			}
		} catch (BusinessException businessException) {
			 log.info("Exception submitComplaints");
			
			 String msgKey = super.handleBusinessException(businessException);
			 ActionMessage message = new ActionMessage(msgKey);
			 messages.add(CMSConstants.MESSAGES, message);
			
			  return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			
			 String msg = super.handleApplicationException(exception);
			 complaintsForm.setErrorMessage(msg);
			 complaintsForm.setErrorStack(exception.getMessage());
			 return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			 addErrors(request, errors);
			 getComplaintTypeList(complaintsForm);
			 getComplaintsDetails(complaintsForm);
			 setHostelListToRequest(request, complaintsForm);
			 return mapping.findForward(CMSConstants.HOSTEL_COMPLAINTS);
		}
		getComplaintTypeList(complaintsForm);
		getComplaintsDetails(complaintsForm);
		setHostelListToRequest(request, complaintsForm);
		log.info("exit of submitComplaints in ComplaintsAction class...");
		return mapping.findForward(CMSConstants.HOSTEL_COMPLAINTS);
	}
	
	/**
	 * @param complaintsForm
	 * @param errors
	 * This method is used to validate required fields
	 */

	private void validateComplaints(ComplaintsForm complaintsForm,
			ActionErrors errors) {
		if (errors == null)
			errors =  new ActionErrors();
		if(!CommonUtil.checkForEmpty(complaintsForm.getComplaintType())){
			
				errors.add("error", new ActionError(
						"knowledgepro.admin.HlComplaintType.required"));
				}
		if(!CommonUtil.checkForEmpty(complaintsForm.getDescription())){
			
					errors.add("error", new ActionError(
							"knowledgepro.admin.tc.dec.required"));
				}
		if(!CommonUtil.checkForEmpty(complaintsForm.getHostelName())){
			errors.add("error", new ActionError("knowledgepro.hostel.name.required"));
		}
		if(!CommonUtil.checkForEmpty(complaintsForm.getRequisitionNo())){
			errors.add("errors", new ActionError("knowledgepro.hostel.reqno.view.required"));
		}
	
		}
	
	/**
	 * This method is used to load the required data to form.
	 * @param complaintsForm
	 * @throws Exception
	 */
	
	public void getComplaintTypeList(ComplaintsForm complaintsForm) throws Exception{
		 log.info("entering of getComplaintTypeList in ComplaintsAction class...");
		 
		 List<ComplaintsTO>  complaintsList = ComplaintsHandler.getInstance().getComplaintsList();
		 complaintsForm.setComplaintsList(complaintsList);
		 complaintsForm.setLogDate(CommonUtil.formatDate(new Date(),DATEFORMAT));
		
		log.info("exit of getComplaintTypeList in ComplaintsAction class...");
	}
	
	/**
	 * used to set hostel list to request
	 * @param request
	 * @param complaintsForm
	 * @throws Exception
	 */
	public void setHostelListToRequest(HttpServletRequest request,ComplaintsForm complaintsForm) throws Exception {
		log.debug("inside setHostelListToRequest");
		 List<HostelTO> hostelList =HostelEntryHandler.getInstance().getHostelDetails();
		complaintsForm.setHostelList(hostelList);
	}
	
	/**
	 * used to get complaints Details
	 * @param complaintsForm
	 * @throws Exception
	 */
	public void getComplaintsDetails(ComplaintsForm complaintsForm) throws Exception
	{
		List<ComplaintsTO> complaintsListTo = ComplaintsHandler.getInstance().getComplaintsDetails();
		complaintsForm.setComplaintsListTo(complaintsListTo);
	}
	
	/**
	 * used to delete the complaints
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteComplaintsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("inside deleteComplaintsDetails");
		ComplaintsForm complaintsForm = (ComplaintsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, complaintsForm);
			String userId = complaintsForm.getUserId();
			int id = complaintsForm.getId();
			boolean isDeleted;
			isDeleted = ComplaintsHandler.getInstance()
					.deleteComplaintsDetails(id, userId);

			/**
			 * If delete operation is success then add the success message. Else
			 * add the appropriate error message
			 */
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(
						CMSConstants.COMPLAINT_DETAILS_DELETED_SUCCESS));
				saveMessages(request, messages);
				getComplaintTypeList(complaintsForm);
				 getComplaintsDetails(complaintsForm);
				 setHostelListToRequest(request, complaintsForm);
			} else {
				errors.add(CMSConstants.ERRORS, new ActionError(
						CMSConstants.COMPLAINT_DETAILS_DELETED_FAILED));
				saveErrors(request, errors);
				getComplaintTypeList(complaintsForm);
				 getComplaintsDetails(complaintsForm);
				 setHostelListToRequest(request, complaintsForm);
			}
		} catch (Exception e) {
			log.error("error in deleting Exception Details");
			String msgKey = super.handleApplicationException(e);
			complaintsForm.setErrorMessage(msgKey);
			complaintsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Existing from deleting complaints details");
		return mapping.findForward(CMSConstants.HOSTEL_COMPLAINTS);

	}
	
	private void validateFormSpecialCharacter(ComplaintsForm complaintsForm, ActionErrors errors, HttpServletRequest request)throws Exception
	{
		if(complaintsForm.getTitle() != null && !complaintsForm.getTitle().isEmpty() && nameValidate(complaintsForm.getTitle()))
		{
			errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","TitleOrSubject"));
		}
	}
	
	 /**
	 * special character validation
	 * 
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name) {
		boolean result = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\s \t \\/ \\( \\) ]+");

		Matcher matcher = pattern.matcher(name);
		result = matcher.find();
		return result;

	}
}