package com.kp.cms.actions.exam;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamGracingForm;
import com.kp.cms.handlers.exam.ExamGracingHandler;
import com.kp.cms.to.exam.ExamGracingTo;

public class ExamGracingAction extends BaseDispatchAction {
	
	public ActionForward intAssigningGracing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		 ExamGracingForm graceForm = (ExamGracingForm) form;
		 try{
			setUserId(request, graceForm);
			graceForm.resetAll();
		 }catch (Exception e) {
				String msg = super.handleApplicationException(e);
				graceForm.setErrorMessage(msg);
				graceForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 return mapping.findForward(CMSConstants.INIT_ASSIGN_GRACING);
	}
	public ActionForward assigningGracing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ExamGracingForm graceForm = (ExamGracingForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = graceForm.validate(mapping, request);
		setUserId(request, graceForm);
		boolean isAssigned = false;
		boolean isStudent = false;
		boolean existing = false;
		try{
		  if(errors.isEmpty()){
			  isStudent = ExamGracingHandler.getInstance().checkStudent(graceForm);
			  if(isStudent){
				  existing = ExamGracingHandler.getInstance().alreadyExist(graceForm);
				  if(!existing){
					  isAssigned = ExamGracingHandler.getInstance().assignGracing(graceForm); 
					  if(isAssigned){
							messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.gracing.assign.success"));
							saveMessages(request, messages);
					  }
				  }else{
					  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.gracing.already.assigned"));
				  }
			  }else{
				  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.gracing.invalid.student"));
			  }
		  }
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			graceForm.setErrorMessage(msg);
			graceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		saveErrors(request, errors);
		 return mapping.findForward(CMSConstants.INIT_ASSIGN_GRACING);
	}
	public ActionForward intGracingProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		 ExamGracingForm graceForm = (ExamGracingForm) form;
		 try{
			 setUserId(request, graceForm);
			graceForm.resetAll();
		 }catch (Exception e) {
				String msg = super.handleApplicationException(e);
				graceForm.setErrorMessage(msg);
				graceForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 return mapping.findForward(CMSConstants.INIT_PROCESS_GRACING);
	}
	 public ActionForward gracingProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                                                                throws Exception{
		 ExamGracingForm graceForm = (ExamGracingForm) form;
		 setUserId(request, graceForm);
		 ActionMessages messages = new ActionMessages();
		 List<ExamGracingTo> studentList = new LinkedList<ExamGracingTo>();
		 ActionErrors errors = graceForm.validate(mapping, request);
		 try{
			 if(errors.isEmpty()){

				 boolean isProcessed = false;
				 studentList = ExamGracingHandler.getInstance().getSudentList(graceForm);
				 if(studentList==null || studentList.size()<=0){
					 errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.gracing.no.data.for.processing"));
				 }
				 else{
					isProcessed =  ExamGracingHandler.getInstance().gracingProcess(graceForm, studentList);
					if(isProcessed){
						 messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.gracing.processed.succesfully"));
						 saveMessages(request, messages);
					}else{
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.gracing.processed.failde"));
					}
				 }
				 
			 
			 }
			 
		 }catch (Exception e) {
				String msg = super.handleApplicationException(e);
				graceForm.setErrorMessage(msg);
				graceForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 saveErrors(request, errors);
		 return mapping.findForward(CMSConstants.INIT_PROCESS_GRACING);
    } 
}
