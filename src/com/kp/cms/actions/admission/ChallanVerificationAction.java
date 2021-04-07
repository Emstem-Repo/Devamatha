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
import com.kp.cms.forms.admission.ChallanVerificationForm;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm;
import com.kp.cms.handlers.admission.ChallanVerificationHandler;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.handlers.admission.UncheckGeneratedSmartCardHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;

public class ChallanVerificationAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(ChallanVerificationAction.class);
	
	/**
	 * Method to redirect to ChallanVerificationInit.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initChallanVerification(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ChallanVerificationAction Batch input");
		ChallanVerificationForm challanVerificationForm = (ChallanVerificationForm) form;
		challanVerificationForm.resetFields();
		log.info("Exit ChallanVerificationAction Batch input");
		
		return mapping.findForward(CMSConstants.CHALLAN_VERIFICATION_INIT);
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
		
		log.info("Entered ChallanVerificationAction - getStudentInfo");
		
		ChallanVerificationForm challanForm = (ChallanVerificationForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		validateChallanForm(errors, challanForm);
		if (errors.isEmpty()) {
			try {
				 List<StudentTO> studentsData = ChallanVerificationHandler.getInstance().getStudentsInfo(challanForm);
				if (studentsData==null || studentsData.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					challanForm.resetFields();
					log.info("Exit ChallanVerificationAction - getStudentInfo size 0");
					return mapping.findForward(CMSConstants.CHALLAN_VERIFICATION_INIT);
				} 
				challanForm.setStudentInfoList(studentsData);
				saveErrors(request, errors);
				saveMessages(request, messages);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				challanForm.setErrorMessage(msg);
				challanForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			challanForm.resetFields();
			log.info("Exit ChallanVerificationAction - getStudentInfo errors not empty ");
			return mapping.findForward(CMSConstants.CHALLAN_VERIFICATION_INIT);
		}
		
		log.info("Exit ChallanVerificationAction - getStudentInfo");
		return mapping.findForward(CMSConstants.CHALLAN_VERIFICATION_INIT);
	}

	/**
	 * validating the mandatory fields in the form
	 * @param errors
	 * @param challanForm
	 * @throws Exception
	 */
	private void validateChallanForm(ActionErrors errors,ChallanVerificationForm challanForm) throws Exception {
		if(challanForm.getChallanDate()!=null && !challanForm.getChallanDate().trim().isEmpty()
				&& challanForm.getApplicationNo()!=null && !challanForm.getApplicationNo().trim().isEmpty()){
			errors.add("errors", new ActionError("knowledgepro.admission.challanVerification.both.not.required"));
		}
		else if ((challanForm.getChallanDate()==null || challanForm.getChallanDate().trim().isEmpty())
				&& (challanForm.getApplicationNo()==null || challanForm.getApplicationNo().trim().isEmpty())){
			errors.add("errors", new ActionError("knowledgepro.admission.challanVerification.required"));
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
	public ActionForward verifyChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ChallanVerificationAction - verifyChallan");
		
		ChallanVerificationForm challanForm = (ChallanVerificationForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, challanForm);
		if (errors.isEmpty()) {
			try {
				boolean contains= validateMethod(challanForm);
				if (!contains) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.uncheckFlag.notSelected"));
					saveErrors(request, errors);
					log.info("Exit ChallanVerificationAction - no students checked");
					return mapping.findForward(CMSConstants.CHALLAN_VERIFICATION_INIT);
				} 
				boolean setFlag = ChallanVerificationHandler.getInstance().updateFlag(challanForm);
				if(setFlag){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.challanVerification.verified.successfully"));
					saveMessages(request, messages);
					log.info("Exit ChallanVerificationAction - verifyChallan");
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.uncheckFlag.failure"));
					saveErrors(request, errors);
					log.info("Exit ChallanVerificationAction - update failure");
				}
				challanForm.resetFields();
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				challanForm.setErrorMessage(msg);
				challanForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} 
		
		log.info("Exit ChallanVerificationAction - verifyChallan");
		return mapping.findForward(CMSConstants.CHALLAN_VERIFICATION_INIT);
	}
	/**
	 * Validating the list to check if atleast one student is been selected
	 * @param uncheckForm
	 * @throws Exception
	 */
	private boolean validateMethod(ChallanVerificationForm challanForm) throws Exception{
		// TODO Auto-generated method
		boolean contains=false;
		if(challanForm.getStudentInfoList()!=null && !challanForm.getStudentInfoList().isEmpty()){
		List<StudentTO> toList=challanForm.getStudentInfoList();
		Iterator<StudentTO> itr=toList.iterator();
		while (itr.hasNext()) {
			StudentTO studentTO = (StudentTO) itr.next();
			if(studentTO.getChecked1()!=null && studentTO.getChecked1().equalsIgnoreCase("On")){
				contains=true;
			}
		}
		}
		return contains;
	}
}
