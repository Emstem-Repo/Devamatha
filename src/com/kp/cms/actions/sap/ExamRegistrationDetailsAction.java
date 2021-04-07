package com.kp.cms.actions.sap;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.kp.cms.actions.admission.NewStudentCertificateCourseAction;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.sap.ExamRegistrationFeeAmount;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.handlers.admission.NewStudentCertificateCourseHandler;
import com.kp.cms.handlers.sap.ExamRegistrationDetailsHandler;
import com.kp.cms.to.sap.ExamRegistrationDetailsTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamRegistrationDetailsAction extends BaseDispatchAction{
	public static final String FROM_DATEFORMAT="dd/MM/yyyy";
	public static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(ExamRegistrationDetailsAction.class);
	
/** Initial method for Registering the Sap Exam.
 * get the worklocations according to alphabatic and set first one as a default select.
 * By default select worklocation ,get the SAP Exam Schedules on future dates.
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initExamRegistrationDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 		throws Exception {
		ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
		objForm.reset();
		setUserId(request, objForm);// setting the userId to Form
		HttpSession session = request.getSession(true);
		objForm.setStudentId(Integer.parseInt(session.getAttribute("studentId") .toString()));
		ActionErrors errors = new ActionErrors();
		try {
			/*------------------Check whether the Student is already registration for Exam-------------------*/
			boolean isExist = ExamRegistrationDetailsHandler.getInstance() .checkIsAlreadyRegisteredForExam(objForm);
			if (isExist) {
				errors.add("error", new ActionError( "knowledgepro.exam.registration.already.exists"));
				saveErrors(request, errors);
				return mapping .findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS_ALREADY_EXIST);
			} else {
				int subjectId = Integer.parseInt(CMSConstants.UPLOAD_SAP_MARKS_UPLOAD_SUBJECT_ID);
				int sapMinMarks = Integer .parseInt(CMSConstants.SAP_EXAM_MIN_MARKS);
				int sapMarksOfStudent = ExamRegistrationDetailsHandler .getInstance().getSAPMarksForStudent(subjectId,objForm.getStudentId());
				if (sapMarksOfStudent > sapMinMarks) {
					objForm.setErrorMessage("You have already completed the SAP course");
					return mapping .findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS_ALREADY_EXIST);
				}
			}
			/*------------------get the worklocations, which is available in ExamVenue------------*/
			Map<Integer, String> workLocationMap = ExamRegistrationDetailsHandler .getInstance().getWorkLocationMap(objForm);
			objForm.setWorkLocationMap(workLocationMap);
			// set the first worklocation as a default one and get the details
			// related to the worklocation.
			setDisplaySessionDetails(objForm, errors, request);
			objForm.setOnline(true); // make the isOnline property as a true .
			
			String examType = request.getParameter("propertyName");
			if(examType!=null && !examType.isEmpty()){
				objForm.setCheckRegOrSupp(examType);
			}
			ExamRegistrationDetailsHandler.getInstance().getFeeAmount(objForm,objForm.getCheckRegOrSupp());
		} catch (Exception e) {
			log.error("Error in initExamRegistrationDetails" + e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS);
}
/**
 * @param objForm
 * @param errors 
 * @param request 
 * @throws Exception
 */
	private void setDisplaySessionDetails(ExamRegistrationDetailsForm objForm,
			ActionErrors errors, HttpServletRequest request) throws Exception {
		Map<String, List<ExamRegistrationDetailsTo>> examRegDetailsMap = ExamRegistrationDetailsHandler
				.getInstance().getDateAndSessionsOfWorkLocation(objForm);
		if(objForm.getWorkLocationId()>0){
			if (examRegDetailsMap != null && !examRegDetailsMap.isEmpty()) {
				objForm.setDateSessionMap(examRegDetailsMap);
			} else {
				objForm.setDateSessionMap(null);
				objForm.setMessage("message");
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
			}
		}else{
			objForm.setDateSessionMap(null);
		}
	}
/** Method for get the Sessions as per the dates for the selected workLocation.
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getDisplayDateAndSession(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
 throws Exception {
		ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		objForm.setIsPrint("false");
		objForm.setMessage(null);
		try {
			setDisplaySessionDetails(objForm, errors, request);
		} catch (Exception e) {
			log.error("Error in getDisplayDateAndSession" + e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			if(!objForm.isOnline()){
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else{
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		}
		if (!objForm.isOnline()) {
			return mapping .findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS_OFFLINE);
		} else {
			return mapping .findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS);
		}
	}
/** Method for enter the smartcard details for make payment of selected session.
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward selectDateSession(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
 throws Exception {
		ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
		objForm.resetStudentDetails();
		HttpSession session = request.getSession(true);
		try {
			ExamRegistrationDetailsHandler.getInstance().allotVenueForStudent( objForm);
			ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl .getInstance();
			int finId = cashCollectionTransaction.getCurrentFinancialYear();
			objForm.setFinancialYear(finId);
		} catch (Exception e) {
			log.error("Error in selectDateSession" + e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			if(!objForm.isOnline()){
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else{
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		}
		if (!objForm.isOnline()) {
			return mapping .findForward(CMSConstants.EXAM_REGISTRATION_DETAILS_WITH_OFFLINE);
		} else {
			ISingleFieldMasterTransaction txn = SingleFieldMasterTransactionImpl .getInstance();
			Student student = (Student) txn.getMasterEntryDataById(Student.class, objForm.getStudentId());
			ExamRegistrationDetailsHandler.getInstance().getStudentDetails( student,objForm,session);
			return mapping .findForward(CMSConstants.EXAM_REGISTRATION_PROCEED_WITH_SMARTCARD_DETAILS);
		}
	}
/**Method for verifying whether the entered smartcard details are correct or not.
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward verifySmartCardDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			boolean isValidSmartCard = ExamRegistrationDetailsHandler .getInstance().verifySmartCard(objForm);
			if (!isValidSmartCard) {
				errors .add( CMSConstants.ERROR, new ActionError( "knowledgepro.admission.empty.err.message",
										"Please Enter the valid last 5 digits of your smart card number"));
			}
			if (objForm.getDob() != null) {
				if (!objForm.getDob() .equalsIgnoreCase(objForm.getOriginalDob())) errors.add(CMSConstants.ERROR, new ActionError(
							"knowledgepro.admission.empty.err.message", "Please Enter Valid Date Of Birth"));
			}

			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping .findForward(CMSConstants.EXAM_REGISTRATION_PROCEED_WITH_SMARTCARD_DETAILS);
			}
		} catch (Exception e) {
			log.error("Error in verifySmartCardDetails" + e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.EXAM_REGISTRATION_SMART_CARD_ENQUIRY);
}
/** After Accept the agree ,method for save the details and detect the feeamount.
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward verifyAndSaveExamRegistration(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm)form;
	ActionErrors errors = new ActionErrors();
	try{
		boolean isAdded = ExamRegistrationDetailsHandler.getInstance().saveExamRegistrationDetails(objForm);
		if(isAdded){
			/* get the subjectGroupId and store in ApplicantSubjectGroup*/
			 ExamRegistrationDetailsHandler.getInstance().saveSAPSubjectGroup(objForm);
			 /*---------------------------------------------------------*/
			 return mapping.findForward(CMSConstants.DISPLAY_HALLTICKET_LINK);
		}else{
			String msg=objForm.getMessage();
			if(msg==null || msg.isEmpty()){
				msg="Payment Failed";
				}
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Exam Registration submission was not successfull, Reason:"+msg));
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
				saveErrors(request, errors);
		}
	}catch (DuplicateException duplicateException) {
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","You have already Registered for SAP Exam"));
			saveErrors(request, errors);
			String msg="";
			msg="Payment Failed";
			objForm.setMessage(msg);
			return mapping.findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS);
	}
	catch (Exception e) {
		log.error("Error in verifyAndSaveExamRegistration"+e.getMessage());
		String msg = super.handleApplicationException(e);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward printHallticket(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm)form;
	String venueName= objForm.getVenueName();
	objForm.setVenueName(null);
	objForm.setVenueName(venueName+"  ("+objForm.getWorkLocationName()+")");
	return mapping.findForward(CMSConstants.DOWNLOAD_HALLTICKET);
}
/**init method of Offline SAP Exam Registration .
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initOfflineSAPExamRegistration(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
		objForm.reset();
		setUserId(request, objForm);// setting the userId to Form
		ActionErrors errors = new ActionErrors();
		try {
			/*------------------get the worklocations, which is available in ExamVenue------------*/
			Map<Integer, String> workLocationMap = ExamRegistrationDetailsHandler .getInstance().getWorkLocationMap(objForm);
			objForm.setWorkLocationMap(workLocationMap);
			// set the first worklocation as a default one and get the details // related to the worklocation.
			setDisplaySessionDetails(objForm, errors, request);
			ExamRegistrationDetailsHandler.getInstance().getFeeAmount(objForm);
		} catch (Exception e) {
			log.error("Error in initOfflineSAPExamRegistration" + e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS_OFFLINE);
}
/**save the offline SAP Exam Registration Details 
 * get print hallticket
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward saveOfflineSAPExamRegistrationDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
 		throws Exception {
		ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		HttpSession session = request.getSession(true);
		session.setAttribute("PhotoBytes", null);
		try {
			boolean isAdded = ExamRegistrationDetailsHandler.getInstance() .saveOfflineSAPExamRegistraionDetails(objForm,session);
			if (isAdded) {
				/* get the subjectGroupId and store in ApplicantSubjectGroup for offline SAP Exam Registration*/
				 ExamRegistrationDetailsHandler.getInstance().saveSAPSubjectGroup(objForm);
				 /*---------------------------------------------------------*/
				message = new ActionMessage( "knowledgepro.sap.registration.sucessful", objForm .getRegNo());
				messages.add("messages", message);
				saveMessages(request, messages);
				setDisplaySessionDetails(objForm,errors,request);
				return mapping.findForward(CMSConstants.INIT_EXAM_REGISTRATION_DETAILS_OFFLINE);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.admission.empty.err.message", "SAP Exam Registration is Failed For RegNo: "
								+ objForm.getRegNo()));
				saveErrors(request, errors);
			}
		} catch (DuplicateException duplicateException) {
			errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.admission.empty.err.message", "SAP Exam Registration is already Completed for RegNo: "
									+ objForm.getRegNo()));
			errors.add("error", new ActionError("knowledgepro.sap.exam.registration.download.hallticket"));
				saveErrors(request, errors);
		} catch (ApplicationException applicationException) { 
			errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.admission.empty.err.message", "Register Number is Invalid"));
				saveErrors(request, errors);
		} catch (BusinessException businessException) { 
			errors.add(CMSConstants.ERROR, new ActionError( "knowledgepro.admission.empty.err.message", objForm.getRegNo()+" had already completed the SAP course"));
			saveErrors(request, errors);
		}
		catch (Exception e) {
			log.error("Error in saveOfflineSAPExamRegistrationDetails" + e.getMessage());
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			objForm.setIsPrint(null);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		objForm.setNetAmount(objForm.getTempNetAmount());
		objForm.setIsPrint(null);
		return mapping .findForward(CMSConstants.EXAM_REGISTRATION_DETAILS_WITH_OFFLINE);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward printReceiptForSAPRegistration(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	//ExamRegistrationDetailsForm objForm = (ExamRegistrationDetailsForm) form;
	return mapping .findForward(CMSConstants.EXAM_REGISTRATION_DETAILS_RECEIPT_NUMBER);
}
}
