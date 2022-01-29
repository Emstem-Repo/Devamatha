package com.kp.cms.actions.exam;

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
import com.kp.cms.forms.exam.ExamRevaluationStatusUpdateForm;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.handlers.exam.DownloadHallTicketHandler;
import com.kp.cms.handlers.exam.ExamRevaluationStatusUpdateHandler;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;

public class ExamRevaluationStatusUpdateAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(ExamRevaluationStatusUpdateAction.class);
	
	/**
	 * Method to set the required data to the form to display it in initRevaluationStatusUpdate
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRevaluationStatusUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initRevaluationStatusUpdate Batch input");
		ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm = (ExamRevaluationStatusUpdateForm) form;
		examRevaluationStatusUpdateForm.resetFields();
		log.info("Exit initRevaluationStatusUpdate Batch input");
		return mapping.findForward(CMSConstants.EXAM_REV_STATUS_UPDATE_INIT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentRevaluationDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entered getStudentRevaluationDetails method");
		ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm=(ExamRevaluationStatusUpdateForm)form;
		ActionErrors errors=examRevaluationStatusUpdateForm.validate(mapping, request);
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			examRevaluationStatusUpdateForm.resetFields();
		}
		else {
			try {
				List<ExamRevaluationApplicationTO> examRevaluationApplicationTOs=ExamRevaluationStatusUpdateHandler.getInstance().getStudentsRevaluationApp(examRevaluationStatusUpdateForm);
				if(examRevaluationApplicationTOs.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					examRevaluationStatusUpdateForm.resetFields();
					log.info("Exit ExamRevaluationStatusUpdateAction - getStudentRevaluationDetails size 0");
				}
				else examRevaluationStatusUpdateForm.setRevAppToList(examRevaluationApplicationTOs);
				
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				examRevaluationStatusUpdateForm.setErrorMessage(msg);
				examRevaluationStatusUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving getStudentRevaluationDetails method");
		 return mapping.findForward(CMSConstants.EXAM_REV_STATUS_UPDATE_INIT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRevaluationStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entered updateRevaluationStatus method");
		ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm=(ExamRevaluationStatusUpdateForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		validateForm(examRevaluationStatusUpdateForm,errors);
		setUserId(request, examRevaluationStatusUpdateForm);
		if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
 			return mapping.findForward(CMSConstants.EXAM_REV_STATUS_UPDATE_INIT);
		}
		else {
			try {
				boolean isUpdated=ExamRevaluationStatusUpdateHandler.getInstance().updateNewStatus(examRevaluationStatusUpdateForm);
				if(isUpdated){
					ExamRevaluationStatusUpdateHandler.getInstance().sendMailToStudent(examRevaluationStatusUpdateForm);
					
					String mobileNo="91";
					if(examRevaluationStatusUpdateForm.getMobileNo()!=null)
						mobileNo=mobileNo+examRevaluationStatusUpdateForm.getMobileNo();
					
					if(mobileNo.length()==12){
						UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_REVALUATION_STATUS_TEMPLATE,null);
					}
					
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_UPDATE_SUCCESS));
					saveMessages(request, messages);
					examRevaluationStatusUpdateForm.resetFields();
					log.info("Exit ExamRevaluationStatusUpdateAction - updateRevaluationStatus success");
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_UPDATE_FAILURE));
					examRevaluationStatusUpdateForm.resetFields();
					log.info("Exit ExamRevaluationStatusUpdateAction - updateRevaluationStatus failure");
				}
				
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				examRevaluationStatusUpdateForm.setErrorMessage(msg);
				examRevaluationStatusUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving updateRevaluationStatus method");
		 return mapping.findForward(CMSConstants.EXAM_REV_STATUS_UPDATE_INIT);
	}

	/**
	 * @param examRevaluationStatusUpdateForm
	 */
	private void validateForm(ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm,ActionErrors errors) {
		if(examRevaluationStatusUpdateForm.getRevAppToList()!=null && !examRevaluationStatusUpdateForm.getRevAppToList().isEmpty()){
			Iterator<ExamRevaluationApplicationTO> itr=examRevaluationStatusUpdateForm.getRevAppToList().iterator();
			boolean newStatusExists=false;
			while (itr.hasNext()) {
				ExamRevaluationApplicationTO examRevaluationApplicationTO = (ExamRevaluationApplicationTO) itr.next();
				if(examRevaluationApplicationTO.getNewStatus()!=null && !examRevaluationApplicationTO.getNewStatus().isEmpty()){
					newStatusExists=true;
					if(examRevaluationApplicationTO.getNewStatus().length()>200){
						if(errors.get(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_LENGTH)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_LENGTH).hasNext())
						errors.add(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_LENGTH, new ActionError(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_LENGTH));
					}
				}
			}
			if(!newStatusExists)
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_REVAPP_STATUS_REQD));	
		}
	}
}
