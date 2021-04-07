package com.kp.cms.actions.admission;

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
import com.kp.cms.exceptions.CurriculumSchemeNotFoundException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.FreeShipException;
import com.kp.cms.exceptions.RegNumNotExistException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.admission.StudentClassAndSubjectDetailsForm;
import com.kp.cms.handlers.admission.StudentClassAndSubjectDetailsHandler;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.to.admin.StudentTO;

public class StudentClassAndSubjectDetailsAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(GensmartCardDataAction.class);
	
	/**
	 * Method to set the required data to the form to display it in StudentClassAndSubjectDetailsActionInit.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentClassAndSubjectDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered StudentClassAndSubjectDetailsAction Batch input");
		StudentClassAndSubjectDetailsForm objForm=(StudentClassAndSubjectDetailsForm)form;
		objForm.resetFields();
		log.info("Exit StudentClassAndSubjectDetailsAction input");
		
		return mapping.findForward(CMSConstants.STUDENT_CLASS_SUBJECT_DETAILS_INIT);
	}

	public ActionForward getStudentsClassSubjects(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered getStudentsClassSubjects Batch input");
		
		StudentClassAndSubjectDetailsForm stuForm= (StudentClassAndSubjectDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		try {
	 		
	 		int count = 0;
	 		if(stuForm.getRegNo() != null && stuForm.getRegNo().length() !=0){
	 			count = count + 1;
	 		}
	 		if(stuForm.getApplnNo() !=null && stuForm.getApplnNo().length() !=0){
	 			count = count + 1;
	 		}
	 		else if(stuForm.getApplnNo().length() == 0 && stuForm.getRegNo().length() == 0){
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_APPORREGI_REQUIRED));
		 		saveErrors(request, errors);
	    		return mapping.findForward(CMSConstants.STUDENT_CLASS_SUBJECT_DETAILS_INIT);
		 		
		 	} 
	 		else if (count > 1 ) {
		 		errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.studentClassSubjectDetails.bothRegNoAndApplnNoNotRequired"));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	}
		 	StudentTO stuTO= StudentClassAndSubjectDetailsHandler.getInstance().getStudentClassandSubjects(stuForm);
		 	if(stuTO!=null){
		 		stuForm.setStudent(stuTO);
		 		stuForm.setStudentAvailable(true);
		 		if(stuTO.isPreviousHistoryAvailable()){
		 			stuForm.setStudentPreviousDetails(true);
		 		}
		 			
		 	}
		 	else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					log.info("Exit StudentClassAndSubjectDetailsAction - getStudentsClassSubjects size 0");
					return mapping.findForward(CMSConstants.STUDENT_CLASS_SUBJECT_DETAILS_INIT);
		 	}
		 	
	 	} catch(Exception e) {
			String msg = super.handleApplicationException(e);
			stuForm.setErrorMessage(msg);
			stuForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
		return mapping.findForward(CMSConstants.STUDENT_CLASS_SUBJECT_DETAILS_INIT);
	}
}
