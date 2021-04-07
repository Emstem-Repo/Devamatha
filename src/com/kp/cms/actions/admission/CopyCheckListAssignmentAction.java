package com.kp.cms.actions.admission;

import java.util.Calendar;
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
import com.kp.cms.forms.admission.CopyCheckListAssignmentForm;
import com.kp.cms.handlers.admission.CopyCheckListAssignmentHandler;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.utilities.CurrentAcademicYear;

	public class CopyCheckListAssignmentAction extends BaseDispatchAction{
		private static final Log log = LogFactory.getLog(CopyCheckListAssignmentAction.class);
		public ActionForward initCopyCheckList(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside initCopyCheckList in Action");
			 CopyCheckListAssignmentForm copyCheckListAssignmentForm = (CopyCheckListAssignmentForm) form;
			try{
				copyCheckListAssignmentForm.clear();
				setUserId(request, copyCheckListAssignmentForm);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				copyCheckListAssignmentForm.setErrorMessage(msg);
				copyCheckListAssignmentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.COPY_CHECKLIST);
		}
		
		
		public ActionForward searchCopyCheckList(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside searchCopyCheckList in Action");
			CopyCheckListAssignmentForm copyCheckListAssignmentForm = (CopyCheckListAssignmentForm) form;
			  ActionErrors errors = copyCheckListAssignmentForm.validate(mapping, request);
			validateYear(errors, copyCheckListAssignmentForm);
			setUserId(request, copyCheckListAssignmentForm);
			List<CheckListTO> checkList=null;
			try{
				if(errors.isEmpty()){
				if (copyCheckListAssignmentForm.getFromYear() == null) {
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					// code by hari
					int year = CurrentAcademicYear.getInstance().getAcademicyear();
					if (year != 0) {
						currentYear = year;
					}// end
					checkList = CopyCheckListAssignmentHandler.getInstance().getCheckListByYear(currentYear,copyCheckListAssignmentForm);
				} else {
					int year = Integer.parseInt(copyCheckListAssignmentForm.getFromYear());
					checkList = CopyCheckListAssignmentHandler.getInstance().getCheckListByYear(year,copyCheckListAssignmentForm);
				}
				if (checkList != null) {
					copyCheckListAssignmentForm.setBackupCheckList(checkList);
				}
				}else{
					saveErrors(request, errors);
				}
				
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				copyCheckListAssignmentForm.setErrorMessage(msg);
				copyCheckListAssignmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.COPY_CHECKLIST);
		}
		
		public void validateYear(ActionErrors errors,CopyCheckListAssignmentForm copyCheckListAssignmentForm){
			if(copyCheckListAssignmentForm.getToYear()!=null && !copyCheckListAssignmentForm.getToYear().isEmpty() && copyCheckListAssignmentForm.getFromYear()!=null && !copyCheckListAssignmentForm.getFromYear().isEmpty()){
			if(Integer.parseInt(copyCheckListAssignmentForm.getToYear())< Integer.parseInt(copyCheckListAssignmentForm.getFromYear())){
				errors.add("error", new ActionError("knowledgepro.admission.validate.year"));
			}
			if(copyCheckListAssignmentForm.getToYear().equals(copyCheckListAssignmentForm.getFromYear())){
				errors.add("error", new ActionError("knowledgepro.admission.validate.equalyear"));
			}
		}
	}
		
		public ActionForward copyCheckList(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside copyCheckList in Action");
			CopyCheckListAssignmentForm copyCheckListAssignmentForm = (CopyCheckListAssignmentForm) form;
			 ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			setUserId(request, copyCheckListAssignmentForm);
			ActionMessage message = null;
			boolean isCopied=false;
			try{
				isCopied=CopyCheckListAssignmentHandler.getInstance().copyCheckList(copyCheckListAssignmentForm);
				if(isCopied){
					 message = new ActionMessage("knowledgepro.admission.copyClasses.success");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					copyCheckListAssignmentForm.clear();
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.copyClasses.failure"));
					saveErrors(request, errors);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				copyCheckListAssignmentForm.setErrorMessage(msg);
				copyCheckListAssignmentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.COPY_CHECKLIST);
		}
}
