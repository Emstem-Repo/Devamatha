package com.kp.cms.actions.examallotment;

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
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.examallotment.ExamInviligatorAllotmentForm;
import com.kp.cms.handlers.examallotment.ExamInviligatorAllotmentHandler;
import com.kp.cms.to.examallotment.InvigilatorsDatewiseExemptionTO;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;

/**
 * @author Christ
 *
 */
public class ExamInviligatorAllotmentAction extends BaseDispatchAction {
	
		private static final Log log = LogFactory.getLog(ExamInviligatorAllotmentAction.class);
		ExamInviligatorAllotmentHandler handler=ExamInviligatorAllotmentHandler.getInstance();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		/**
		 * setting the requested data to request 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initInvigilatorAllotment(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInviligatorAllotmentForm invForm=(ExamInviligatorAllotmentForm)form;
			invForm.resetFields();
			setRequestedDataToForm(invForm);
			setUserId(request,invForm);
			return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
		}
		
		private void setRequestedDataToForm(ExamInviligatorAllotmentForm invForm) throws Exception{
			handler.getInitialData(invForm);
			
		}
		
		
		public ActionForward InvigilatorAllotment(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInviligatorAllotmentForm invForm=(ExamInviligatorAllotmentForm)form;
			errors.clear();
			messages.clear();
			ActionErrors errors=invForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					boolean isUpdated =handler.getTeachersAllotment(invForm,errors);
					if(isUpdated){
						//if update is success.
						
						ActionMessage message = new ActionMessage(CMSConstants.INVILIGATOR_TEACHER_ALLOTMENT_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						invForm.resetFields();
						setRequestedDataToForm(invForm);
						return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
					}
					if(!isUpdated){
						//if update is failure.
						ActionMessage error = new ActionMessage(CMSConstants.INVILIGATOR_TEACHER_ALLOTMENT_FAILURE);
						errors.add("messages", error);
						saveErrors(request, errors);
						//saveMessages(request, messages);
						invForm.resetFields();
					}
					
					
					return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
				}else{
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
				}
					
				} catch (Exception e) {
					if(e instanceof DataNotFoundException) {
			    		setRequestedDataToForm(invForm);
			    		saveErrors(request,errors);
			    		return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
					}
					else if (e instanceof ApplicationException) {
							String msg = super.handleApplicationException(e);
							invForm.setErrorMessage(msg);
							invForm.setErrorStack(e.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						} else {
							throw e;
						}
					}
			}
		
		public ActionForward InvigilatorAllotmentAvailableList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInviligatorAllotmentForm invForm=(ExamInviligatorAllotmentForm)form;
			setUserId(request,invForm);
			List<InvigilatorsForExamTo> availableList =handler.getAvailableTeachersList(invForm,errors);
			if(availableList==null || availableList.isEmpty()){
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.teacher.allotment.teacher.not.available.list.empty"));
				saveErrors(request,errors);
	    		setRequestedDataToForm(invForm);
	    		return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
			}else
			{
				invForm.setAvailableList(availableList);
				return mapping.findForward(CMSConstants.VERIFY_TEACHER_AVAILABLE);
			}
		}
		
		public ActionForward InvigilatorAllotmentExemptedList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInviligatorAllotmentForm invForm=(ExamInviligatorAllotmentForm)form;
			setUserId(request,invForm);
			errors.clear();
			messages.clear();
			ActionErrors errors=invForm.validate(mapping, request);
			try 
			{
				
				if (errors.isEmpty()) {
		
					List<InvigilatorsDatewiseExemptionTO> exemptedList =handler.getExemptedTeachersList(invForm,errors);
					if( exemptedList == null){
						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
							message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
							messages.add("messages", message);
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.VERIFY_TEACHER_EXEMPTION_DATEWISE);
					}else
					{
							invForm.setDatewiseExemptionList(exemptedList);
							return mapping.findForward(CMSConstants.VERIFY_TEACHER_EXEMPTION_DATEWISE);
					}
					
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
				}
			}catch(Exception e){
				if(e instanceof DataNotFoundException) {
		    		saveErrors(request,errors);
		    		setRequestedDataToForm(invForm);
		    		return mapping.findForward(CMSConstants.INIT_EXAM_INVIGILATOR_ALLOTMENT);
				}
				else if (e instanceof ApplicationException) {
						String msg = super.handleApplicationException(e);
						invForm.setErrorMessage(msg);
						invForm.setErrorStack(e.getMessage());
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					} else {
						throw e;
					}
			}
		}
}
