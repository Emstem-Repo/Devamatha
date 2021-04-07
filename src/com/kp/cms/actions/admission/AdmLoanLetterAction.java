package com.kp.cms.actions.admission;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.admission.AdmLoanLetterForm;
import com.kp.cms.handlers.admission.AdmLoanLetterHandler;
import com.kp.cms.to.admission.AdmLoanLetterDetailsTO;

public class AdmLoanLetterAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(AdmLoanLetterAction.class);
	/**
	 * Method to redirect to AdmLoanLetter.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmLoanLetter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered AdmLoanLetterAction Batch input");
		AdmLoanLetterForm admLoanLetterForm = (AdmLoanLetterForm) form;
		admLoanLetterForm.resetFields();
		log.info("Exit AdmLoanLetterAction Batch input");
		
		return mapping.findForward(CMSConstants.ADM_LOAN_LETTER);
	}
	
	/**
	 * Method to get the Students info according to the input
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered AdmLoanLetterAction - getStudentInfo");
		
		AdmLoanLetterForm letterForm = (AdmLoanLetterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		validateLetterForm(errors, letterForm);
		if (errors.isEmpty()) {
			try {
			 List<AdmLoanLetterDetailsTO> studentsData = AdmLoanLetterHandler.getInstance().getStudentsInfo(letterForm);
				if (studentsData==null || studentsData.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
//					letterForm.resetFields();
					letterForm.clearList();
					log.info("Exit AdmLoanLetterAction - getStudentInfo size 0");
					return mapping.findForward(CMSConstants.ADM_LOAN_LETTER);
				} 
				letterForm.setStudentInfoList(studentsData);
				saveErrors(request, errors);
				saveMessages(request, messages);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				letterForm.setErrorMessage(msg);
				letterForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			letterForm.resetFields();
			log.info("Exit AdmLoanLetterAction - getStudentInfo errors not empty ");
			return mapping.findForward(CMSConstants.ADM_LOAN_LETTER);
		}
		
		log.info("Exit AdmLoanLetterAction - getStudentInfo");
		return mapping.findForward(CMSConstants.ADM_LOAN_LETTER);
	}
	
	/**
	 * validating the mandatory fields in the form
	 * @param errors
	 * @param letterForm
	 * @throws Exception
	 */
	private void validateLetterForm(ActionErrors errors,AdmLoanLetterForm letterForm) throws Exception {
		if ((letterForm.getAdmittedDate()==null || letterForm.getAdmittedDate().trim().isEmpty())
				&& (letterForm.getApplicationNo()==null || letterForm.getApplicationNo().trim().isEmpty())
				&& (letterForm.getRegisterNo()==null || letterForm.getRegisterNo().trim().isEmpty())){
			errors.add("errors", new ActionError("knowledgepro.admission.loanletter.required"));
		}
	}
	
	/**
	 * Method to set the verified flag in database for selected students
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLoanLetter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered AdmLoanLetterAction - verifyLoanLetter");
		
		AdmLoanLetterForm letterForm = (AdmLoanLetterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, letterForm);
		if (errors.isEmpty()) {
			try {

				boolean setFlag = AdmLoanLetterHandler.getInstance().addLoan(letterForm);
				if(setFlag){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.loanletter.success"));
					saveMessages(request, messages);
					log.info("Exit AdmLoanLetterAction - verifyLoanLetter");
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.loanletter.failed"));
					saveErrors(request, errors);
					log.info("Exit AdmLoanLetterAction - update failure");
				}
				letterForm.resetFields();
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				letterForm.setErrorMessage(msg);
				letterForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} 
		
		log.info("Exit AdmLoanLetterAction - verifyLoanLetter");
		return mapping.findForward(CMSConstants.ADM_LOAN_LETTER);
	}
	
	/**
	 * Validating the list to check if atleast one student is been selected
	 * @param uncheckForm
	 * @throws Exception
	 */
	private boolean validateMethod(AdmLoanLetterForm letterForm) throws Exception{
		// TODO Auto-generated method
		boolean contains=false;
		if(letterForm.getStudentInfoList()!=null && !letterForm.getStudentInfoList().isEmpty()){
		List<AdmLoanLetterDetailsTO> toList=letterForm.getStudentInfoList();
		Iterator<AdmLoanLetterDetailsTO> itr=toList.iterator();
		while (itr.hasNext()) {
			AdmLoanLetterDetailsTO admLoanLetterDetailsTO = (AdmLoanLetterDetailsTO) itr.next();
			if(admLoanLetterDetailsTO.getChecked1()!=null && admLoanLetterDetailsTO.getChecked1().equalsIgnoreCase("On")){
				contains=true;
			}
		}
		}
		return contains;
	}

}
