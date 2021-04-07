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
import com.kp.cms.forms.admission.CopyClassesForm;
import com.kp.cms.handlers.admission.CopyClassesHandler;
import com.kp.cms.to.admission.CopyClassesTO;
import com.kp.cms.utilities.CurrentAcademicYear;

	public class CopyClassesAction extends BaseDispatchAction{
		private static Log log = LogFactory.getLog(CopyClassesAction.class);
		
		public ActionForward initCopyClasses(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside initCopyClassesAction in Action");
			 CopyClassesForm copyClassesForm = (CopyClassesForm) form;
			try{
				copyClassesForm.clear();
				setUserId(request, copyClassesForm);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				copyClassesForm.setErrorMessage(msg);
				copyClassesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.COPY_CLASSES);
		}
		
		
		public ActionForward searchCopyClasses(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside searchCopyClasses in Action");
			 CopyClassesForm copyClassesForm = (CopyClassesForm) form;
			  ActionErrors errors = copyClassesForm.validate(mapping, request);
			validateYear(errors, copyClassesForm);
			setUserId(request, copyClassesForm);
			List<CopyClassesTO> classesList=null;
			try{
				if(errors.isEmpty()){
				if (copyClassesForm.getFromYear() == null) {
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					// code by hari
					int year = CurrentAcademicYear.getInstance().getAcademicyear();
					if (year != 0) {
						currentYear = year;
					}// end
					classesList = CopyClassesHandler.getInstance().getClassesByYear(
							currentYear);
				} else {
					int year = Integer.parseInt(copyClassesForm.getFromYear());
					classesList = CopyClassesHandler.getInstance().getClassesByYear(
							year);
				}
				copyClassesForm.setClassesList(classesList);
				}else{
					saveErrors(request, errors);
				}
				
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				copyClassesForm.setErrorMessage(msg);
				copyClassesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.COPY_CLASSES);
		}
		
		
		public ActionForward copySelectedClasses(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response){
			log.debug("inside copySelectedClasses in Action");
			 CopyClassesForm copyClassesForm = (CopyClassesForm) form;
			 ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			boolean isCopied=false;
			try{
				setUserId(request, copyClassesForm);
				int toYear =0;
				if(copyClassesForm.getToYear()!=null){
					 toYear = Integer.parseInt(copyClassesForm.getToYear());
				}
				List<CopyClassesTO> classesList=copyClassesForm.getClassesList();
				isCopied=CopyClassesHandler.getInstance().saveClasses(classesList,toYear,copyClassesForm);
				if(isCopied){
					 message = new ActionMessage("knowledgepro.admission.copyClasses.success");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					copyClassesForm.clear();
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.copyClasses.failure"));
					saveErrors(request, errors);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				copyClassesForm.setErrorMessage(msg);
				copyClassesForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.COPY_CLASSES);
		}
		
		public void validateYear(ActionErrors errors,CopyClassesForm copyClassesForm){
			if(copyClassesForm.getToYear()!=null && !copyClassesForm.getToYear().isEmpty() && copyClassesForm.getFromYear()!=null && !copyClassesForm.getFromYear().isEmpty()){
			if(Integer.parseInt(copyClassesForm.getToYear())< Integer.parseInt(copyClassesForm.getFromYear())){
				errors.add("error", new ActionError("knowledgepro.admission.validate.year"));
			}
			if(copyClassesForm.getToYear().equals(copyClassesForm.getFromYear())){
				errors.add("error", new ActionError("knowledgepro.admission.validate.equalyear"));
			}
		}
	}
}
