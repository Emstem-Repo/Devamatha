package com.kp.cms.actions.hostel;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelReservationcancellationForm;
import com.kp.cms.handlers.hostel.HostelReservationCancellationHadler;

public class HostelReservationCancellationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(HostelReservationCancellationAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initReservationCancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initViewRequisitions. of ViewReqStatusAction");
		HostelReservationcancellationForm hostelReservationcancellationForm = (HostelReservationcancellationForm) form;
		try {
			hostelReservationcancellationForm.resetFields( mapping,request);
			setUserId(request, hostelReservationcancellationForm);

		} catch (Exception e) {
			log.error(
					"Error in initViewRequisitions in ViewReqStatusAction",
					e);
			String msg = super.handleApplicationException(e);
			hostelReservationcancellationForm.setErrorMessage(msg);
			hostelReservationcancellationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("leaving into initViewRequisitions. of ViewReqStatusAction");
		return mapping.findForward(CMSConstants.HOSTEL_RESERV_CANCEL);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelResrevation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitViewRequisitions. of ViewRequisitionsAction");	
		HostelReservationcancellationForm hostelReservationcancellationForm = (HostelReservationcancellationForm)form;
		ActionMessages messages = new ActionMessages();
		ActionMessages errors = hostelReservationcancellationForm.validate(mapping, request);

			try {
				if(errors.isEmpty()){
					HlApplicationForm hlApplicationForm=HostelReservationCancellationHadler.getInstance().getHlApplicationFormByRequistionNo(hostelReservationcancellationForm);
					if(hlApplicationForm==null){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						log.info("Exit cancelResrevation size 0");
						return mapping.findForward(CMSConstants.HOSTEL_RESERV_CANCEL);
					}
					boolean value =HostelReservationCancellationHadler.getInstance().cancelReservation(hostelReservationcancellationForm,request);
					if(value){	
						ActionMessage message = new ActionMessage(CMSConstants.CANCEL_REQUISITION);
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						//	errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CANCEL_REQUISITION));
						saveErrors(request, errors);
						hostelReservationcancellationForm.resetFields(mapping, request);
						return mapping.findForward(CMSConstants.HOSTEL_RESERV_CANCEL);
					}
					else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						hostelReservationcancellationForm.resetFields(mapping, request);
										}
				}
				else{
					addErrors(request, errors);
					//Sets programType and program to form bean
					return mapping.findForward(CMSConstants.HOSTEL_RESERV_CANCEL);
				}
				}
			catch (Exception e) {
				e.printStackTrace();
				log.error("Error in showRequisitions in ViewRequisitionsAction",e);
				String msg = super.handleApplicationException(e);
				hostelReservationcancellationForm.setErrorMessage(msg);
				hostelReservationcancellationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.HOSTEL_RESERV_CANCEL);

	}	
	}		
		
	

