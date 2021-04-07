package com.kp.cms.actions.usermanagement;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.actions.admission.NewStudentCertificateCourseAction;
import com.kp.cms.actions.admission.OnlineApplicationAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.InternalExamGrievanceBo;
import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.exam.ConsolidatedSubjectStreamForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.AttendanceGraphHandler;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.OnlineApplicationHandler;
import com.kp.cms.handlers.admission.StudentCertificateCourseHandler;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.handlers.exam.CertificateMarksCardHandler;
import com.kp.cms.handlers.exam.ConsolidatedSubjectStreamHandler;
import com.kp.cms.handlers.exam.DownloadHallTicketHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamMidsemRepeatHandler;
import com.kp.cms.handlers.exam.GrievanceStudentHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSupplementaryImpApplicationHandler;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.handlers.hostel.HostelLeaveHandler;
import com.kp.cms.handlers.reports.CiaOverallReportHandler;
import com.kp.cms.handlers.serviceLearning.DepartmentNameEntryHandler;
import com.kp.cms.handlers.serviceLearning.ProgrammeEntryHandler;
import com.kp.cms.handlers.serviceLearning.ServiceLearningMarksEntryHandler;
import com.kp.cms.handlers.usermanagement.LoginHandler;
import com.kp.cms.handlers.usermanagement.StudentLoginHandler;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.attendance.StudentLeaveTO;
import com.kp.cms.to.exam.ClearanceCertificateTO;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;
import com.kp.cms.to.exam.GradeClassDefinitionTO;
import com.kp.cms.to.exam.GrievanceStatusTo;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.InternalMarkCardTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.ShowMarksCardTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningActivityTo;
import com.kp.cms.to.serviceLearning.ServiceLearningMarksEntryTo;
import com.kp.cms.transactions.admin.StudentLoginTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactions.serviceLearning.IServiceLearningMarksEntryTransaction;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.transactionsimpl.serviceLearning.ServiceLearningMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.EncryptUtil;
import com.kp.cms.utilities.MarkComparator;
import com.kp.cms.utilities.PropertyUtil;
import com.sun.corba.se.spi.orbutil.fsm.Action;


/**
 * @author christ
 *
 */
@SuppressWarnings("deprecation")
public class StudentLoginAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentLoginAction.class);
	private LoginForm loginForm;	


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward studentLoginActionNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = loginForm.validate(mapping, request);
		HttpSession session = request.getSession(true);
		loginForm.clearMideSemExam();
		session.setAttribute("USERNAME", loginForm.getUserName());
		if (errors.isEmpty()) {
			try {
				loginForm.setEncryptedPassword(EncryptUtil.getInstance().encryptDES(loginForm.getPassword()));
				StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;

				if (studentLogin == null) {
					message = new ActionMessage("knowledgepro.admin.validusername");
					messages.add("messages", message);
					addErrors(request, messages);
					loginForm.resetFields();
					if(CMSConstants.LINK_FOR_CJC)
					{ 
						return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
					}else
					{
						return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
					}
				} else {
					//					setNewsData(loginForm); data added at server startup in StudentLoginLogo servlet
					loginForm.setDescription(CMSConstants.NEWS_DESCRIPTION);
					setDataToForm(loginForm,request);

					StudentLoginHandler.getInstance().setStudentDetailsToForm(studentLogin,session,loginForm);

					// this method used to display the student links based on some conditions

					displayStudentLoginLinks(studentLogin,session,loginForm);
					/* code added by sudhir */
					if(studentLogin.getStudent()!=null && studentLogin.getStudent().getAdmAppln()!=null){
						session.setAttribute("admApplnId", studentLogin.getStudent().getAdmAppln());
					}else{
						session.setAttribute("admApplnId", null);
					}
					/*added code ends here */


					//----------added by jismy for Hall Ticket
					String studentid = (String) session.getAttribute("studentid");
					int classId = 0;
					if(studentLogin.getStudent() != null && studentLogin.getStudent().getClassSchemewise() != null && 
							studentLogin.getStudent().getClassSchemewise().getClasses() != null && studentLogin.getStudent().getClassSchemewise().getClasses().getId() != 0){
						classId=studentLogin.getStudent().getClassSchemewise().getClasses().getId();
					}

					session.setAttribute("stuRegNo", loginForm.getRegNo());

					int examId = 0;
					if(classId > 0){
						examId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(classId, loginForm, "Hall Ticket");
					}
					session.setAttribute("examID", examId);
					loginForm.setExamId(examId);
					ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO = null;
					if(examId>0){
						examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentid), classId, examId, "H");  
					}
					boolean isBlockedStudent = false;
					if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
						isBlockedStudent = true;
					}
					boolean isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(classId, examId, "Hall Ticket", false);
					if(classId > 0 && examId > 0 && isDateValid){
						session.setAttribute("showHallTicket", true);
					}
					else{
						session.setAttribute("showHallTicket", false);
					}
					if(isBlockedStudent){
						session.setAttribute("isHallTicketBlockedStudent", true);
						session.setAttribute("hallTicketBlockReason", examBlockUnblockHallTicketBO.getBlockReason());
						session.setAttribute("blockHallTicketId", examBlockUnblockHallTicketBO.getId());
						boolean isLessPercentage=LoginHandler.getInstance().checkStudentAttendancePercentage(studentid,classId);
						session.setAttribute("isLessPercentage", isLessPercentage);
					}else{
						session.setAttribute("isHallTicketBlockedStudent", false);
					}
					session.removeAttribute("agreement");
					//Change By manu,1st check condition
					ExamFooterAgreementBO agreementBO=null;
					if(loginForm.getAgreementId()>0){
						agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
					}
					if(agreementBO!= null && agreementBO.getDescription()!= null){
						session.setAttribute("agreement", agreementBO.getDescription());
					}
					else{
						session.setAttribute("agreement", null);
					}
					//Added By Manu For Repeat MidSem Exam Apply Link
					if(loginForm.getTermNo()>1){
						session.setAttribute("previousAttendance", true);
					}else{
						session.setAttribute("previousAttendance", false);
					}
					loginForm.clearMideSemExam();
					session.setAttribute("linkForRepeatExamsApplication", false);
					session.setAttribute("linkForRepeatExamsFeePayment",false);
					session.setAttribute("linkFordownloadHallTicket", false);

					ExamMidsemRepeatSetting checkForApplicationDate=ExamMidsemRepeatHandler.getInstance().checkForApplicationDate(loginForm);
					String linkForRepeatExam=null;		
					if(checkForApplicationDate!=null) {
						linkForRepeatExam=ExamMidsemRepeatHandler.getInstance().checkForRepeatApplicationExam(studentLogin.getStudent().getId(),checkForApplicationDate.getMidSemExamId().getId(),loginForm);
						if(linkForRepeatExam.equalsIgnoreCase("validData"))
							session.setAttribute("linkForRepeatExamsApplication", true);
						else if(linkForRepeatExam.equalsIgnoreCase("isApproved"))
							session.setAttribute("linkForRepeatExamsFeePayment",true);
					}

					if(linkForRepeatExam!=null && linkForRepeatExam.equalsIgnoreCase("notValidData")){

						ExamMidsemRepeatSetting checkForFeesDate=ExamMidsemRepeatHandler.getInstance().checkForFeePaymentDate(loginForm);
						if(checkForFeesDate!=null) {
							linkForRepeatExam=ExamMidsemRepeatHandler.getInstance().checkForRepeatFeesPaymentExam(studentLogin.getStudent().getId(),checkForFeesDate.getMidSemExamId().getId(),loginForm);
							if(linkForRepeatExam.equalsIgnoreCase("hallTicket"))
								session.setAttribute("linkFordownloadHallTicket", true);
							else if(linkForRepeatExam.equalsIgnoreCase("isApproved"))
								session.setAttribute("linkForRepeatExamsFeePayment",true);
						}
					}
					/*ExamMidsemRepeatSetting checkForFeesDate=ExamMidsemRepeatHandler.getInstance().checkForFeePaymentDate(loginForm);
						if(checkForFeesDate!=null) {
						  linkForRepeatExam=ExamMidsemRepeatHandler.getInstance().checkForRepeatFeesPaymentExam(studentLogin.getStudent().getId(),checkForFeesDate.getMidSemExamId().getId(),loginForm);
						 if(linkForRepeatExam.equalsIgnoreCase("hallTicket"))
						 session.setAttribute("linkFordownloadHallTicket", true);
						}*/
					//endManu
					//----------------------
					showSupplementaryHallTcket( session,  classId,  loginForm, studentid);
					//------------
					loginForm.setRevaluationRegClassId(null);
					loginForm.setRevaluationSupClassId(null);

					// for supplementary revaluation application 

					showSupplementaryRevaluationApplication( session,  classId,  loginForm, studentid);

					//------show CIA Overall Report

					ExamPublishExamResultsBO examPublishExamResultsBO =  CiaOverallReportHandler.getInstance().getPublishedClassId(Integer.parseInt(studentid), classId);
					if(examPublishExamResultsBO!= null && examPublishExamResultsBO.getClassId() > 0){
						session.setAttribute("showOverallReport", true);
					}
					else{
						session.setAttribute("showOverallReport", false);	
					}
					if(loginForm.getProgramTypeId() != null &&  CMSConstants.UG_ID.contains(Integer.parseInt(loginForm.getProgramTypeId()))){
						session.setAttribute("birtUg", true);
					}else if(loginForm.getProgramTypeId() != null && CMSConstants.PG_ID.contains(Integer.parseInt(loginForm.getProgramTypeId()))){
						session.setAttribute("birtUg", false);
					}

					if(studentLogin.getStudent().getAdmAppln() != null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId()!= null 
							&& session.getAttribute("birtUg") != null ){
						boolean isUg=(Boolean)session.getAttribute("birtUg");
						if(isUg && Integer.parseInt(loginForm.getYear())>=2011 && loginForm.getTermNo()<=4){
							/* code added by sudhir */
							boolean isMandatoryCourse = StudentLoginHandler.getInstance().checkCourseIsMandatory(studentLogin.getStudent().getAdmAppln());
							if(isMandatoryCourse){
								/* code added by sudhir */
								session.setAttribute("showCertCourse", true);
								session.setAttribute("courseId", studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
								session.setAttribute("studentId",studentLogin.getStudent().getId());
								/*int completedSubCount = StudentCertificateCourseHandler.getInstance().getCompletedCourseCount(Integer.parseInt(studentid),studentLogin.getStudent().getAdmAppln());
								int pendingCount = 0;
								if(2-completedSubCount > 0){
									pendingCount = 2-completedSubCount;
								}
								session.setAttribute("pendingCount", pendingCount);
								session.setAttribute("completedSubCount", completedSubCount);*/}else{
									session.setAttribute("showCertCourse", false);
								}
						}else{
							session.setAttribute("showCertCourse", false);
						}
					}else{
						session.setAttribute("showCertCourse", false);
					}

					if(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId()!= null){
						session.setAttribute("stuCourseId", String.valueOf(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId()));
					}
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName() != null){
						session.setAttribute("STUNAME",studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName());
					}
					// added for print challan link

					boolean isSmartCardMode=StudentLoginHandler.getInstance().getStudentPaymentMode(studentLogin.getStudent().getId(),loginForm);
					if(CMSConstants.LINK_FOR_CHALLAN_PRINT && isSmartCardMode){
						session.setAttribute("linkForPrintChallan", true);
					}else{
						session.setAttribute("linkForPrintChallan", false);
					}
					session.setAttribute("studentId",studentLogin.getStudent().getId());

					int groupId=StudentLoginHandler.getInstance().getStudentGroupIdForCourse(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
					if(groupId>0){
						String detainQuery="select s.student.id from ExamStudentDetentionRejoinDetails s " +
								"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null) and s.student.id="+studentLogin.getStudent().getId();
						List list=PropertyUtil.getInstance().getListOfData(detainQuery);
						if(list==null || list.isEmpty()){
							session.setAttribute("linkForCertificateCourse", true);
							session.setAttribute("studentRegNo",studentLogin.getStudent().getRegisterNo());
							if(studentLogin.getStudent().getClassSchemewise()!=null)
								session.setAttribute("studentsemNo",studentLogin.getStudent().getClassSchemewise().getClasses().getTermNumber());
						}
					}else
						session.setAttribute("linkForCertificateCourse", false);

					// code added by sudhir
					// checking in database whether the classid is their for today date to appear the faculty Feedback link 
					if(studentLogin.getStudent().getClassSchemewise()!=null){
						session.setAttribute("classSchemeWiseId", String.valueOf(studentLogin.getStudent().getClassSchemewise().getId()));
					}else{
						session.setAttribute("classSchemeWiseId",null);
					}
					session.setAttribute("ClassId", String.valueOf(studentLogin.getStudent().getClassSchemewise().getClasses().getId()));
					String classid = session.getAttribute("ClassId").toString();
					/*newly added */
					Map<Integer,String> specializationIds = StudentLoginHandler.getInstance().getSpecializationIds(loginForm);

					/*-----------*/
					boolean isFacultyFeedbackAvailable = StudentLoginHandler.getInstance().isFacultyFeedbackAvailable(Integer.parseInt(classid),session,specializationIds);
					if(isFacultyFeedbackAvailable){
						session.setAttribute("studentFacultyFeedback", true);
					}else{
						session.setAttribute("studentFacultyFeedback", false);
					}
					/*--------------- SAP EXAM REGISTRATION LINK DISPLAY--------------------------------- */
					//						if the student is passed do not display the link . if failed display link as SAP Supplementry Exam.
					UploadSAPMarksBo bo=StudentLoginHandler.getInstance().getSAPExamResuls(studentid);
					if(bo!=null){
						if(bo.getStatus().equalsIgnoreCase("PASS") || bo.getStatus().equalsIgnoreCase("PASSED")){
							loginForm.setStatus("PASS");
						}else if(bo.getStatus().equalsIgnoreCase("Fail")|| bo.getStatus().equalsIgnoreCase("Failed")){
							loginForm.setStatus("FAIL");
						}
					}else{
						loginForm.setStatus("ALLOW");
					}
					/*---------------SAP EXAM REGISTRATION LINK DISPLAY Code ends Here  */
					if(studentLogin.getIsTempPassword()){
						session.setAttribute("tempPasswordLogin", true);
						return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS_PASSCHANGE);
					}else {	
						session.setAttribute("tempPasswordLogin", false);


						if(CMSConstants.LINK_FOR_CJC)
						{ 
							return mapping.findForward(CMSConstants.STUDENT_LOGIN_SUCCESS_CJC);
						}else
						{
							return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS);
						}
					}	
				}
			}catch(Exception e) {
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			}
		}else {
			addErrors(request, errors);				
			if(CMSConstants.LINK_FOR_CJC)
			{ 
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
			}else
			{
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			}
		}

	}

	/**
	 * @param studentLogin
	 * @param session
	 * @param loginForm 
	 * @throws Exception
	 */
	private void displayStudentLoginLinks(StudentLogin studentLogin, HttpSession session, LoginForm loginForm) throws Exception{


		// set data to session

		session.setAttribute("ChangePassword",CMSConstants.STUDENT_CHANGE_PASSWORD);
		session.setAttribute("LogoBytes", CMSConstants.LOGIN_LOGO);

		session.setAttribute("uid", String.valueOf(studentLogin.getId()));
		session.setAttribute("studentid", String.valueOf(studentLogin.getStudent().getId()));
		session.setAttribute("username", studentLogin.getUserName());
		session.setMaxInactiveInterval(CMSConstants.MAX_SESSION_INACTIVE_TIME);

		if(studentLogin.getRemarks() != null && !studentLogin.getRemarks().trim().isEmpty())
			session.setAttribute("studentAchievement", studentLogin.getRemarks());
		else
			session.setAttribute("studentAchievement", null);

		session.setAttribute("showAttendanceRep", true);
		// Code Added by Mary Job----- For Holistic Supplementary Exam------Link
		if(studentLogin.getStudent()!=null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId()!=null){
			int programId=studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId();
			//if(programId!=4 && programId!=94 && programId!=81 && programId!=82 && programId!=74)	
			if(programId==3)
				session.setAttribute("linkForHolisticExam", true);
			else 
				session.setAttribute("linkForHolisticExam", false);

		}
		//	session.setAttribute("linkForHolisticExam", true);

		if(CMSConstants.SHOW_LINK.equalsIgnoreCase("true"))
			session.setAttribute("showLinks", true);
		else
			session.setAttribute("showLinks",false);

		if(CMSConstants.LINK_FOR_CJC){
			session.setAttribute("linkForCjc",true);
		}else{
			session.setAttribute("linkForCjc", false);
		}

		//Hostel Leave application 
		boolean hostelLinks = HostelLeaveHandler.getInstance().checkStudentDetails(studentLogin.getStudent().getId());
		if(hostelLinks){
			session.setAttribute("hostelLinks", true);
		}else{
			session.setAttribute("hostelLinks", false);
		}

		boolean isPrintAvailable=StudentLoginHandler.getInstance().checkOnlineReciepts(studentLogin.getStudent().getId());
		if(isPrintAvailable)
			session.setAttribute("linkForOnlineReciepts", true);
		else
			session.setAttribute("linkForOnlineReciepts", false);


		boolean finalStudent=false;
		if(studentLogin.getStudent() != null && studentLogin.getStudent().getId() !=0){
			if(CMSConstants.OPEN_HONOURSCOURS_LINK){
				if(studentLogin.getStudent().getClassSchemewise().getClasses().getTermNumber() == 4){
					boolean display = StudentLoginHandler.getInstance().checkHonoursCourse(studentLogin.getStudent().getId(),studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
					session.setAttribute("honoursLink", display);
				}else{
					session.setAttribute("honoursLink", false);
				}
			}else{
				session.setAttribute("honoursLink", false);
			}
			if(CMSConstants.CONVOCATION_REGISTRATION){
				finalStudent=CMSConstants.CONVOCATION_REGISTRATION;
			}
		}
		//added by mahi
		session.setAttribute("linkForConsolidateMarksCard", false);
		if(finalStudent){
			boolean studentIsFinalYearOrNot=LoginHandler.getInstance().checkStudentIsFinalYearOrNot(loginForm,session);
			if(studentIsFinalYearOrNot){
				session.setAttribute("finalYearStudent", true);	
				if(CMSConstants.LINK_FOR_CONSOLIDATE_MARKS_CARD){
					session.setAttribute("linkForConsolidateMarksCard", true);
				}
			}else{
				session.setAttribute("finalYearStudent", false);	
			}
		}else{
			boolean studentIsFinalYearOrNot=LoginHandler.getInstance().checkStudentIsFinalYearOrNot(loginForm,session);
			if(studentIsFinalYearOrNot){
				if(CMSConstants.LINK_FOR_CONSOLIDATE_MARKS_CARD){
					session.setAttribute("linkForConsolidateMarksCard", true);
				}
			}
		}
	}

	/**isDateValid
	 * 
	 * @param mappingisDateValid
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward studentLoginAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the studentLoginAction");
		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = loginForm.validate(mapping, request);
		//if(!loginForm.getUserName().equalsIgnoreCase("User Name") && !loginForm.getPassword().equalsIgnoreCase("Password")){
		HttpSession session = request.getSession(true);
		loginForm.clearMideSemExam();
		loginForm.setIsSupplImpAppDisplay(true);
		session.setAttribute("USERNAME", loginForm.getUserName());
		if (errors.isEmpty()) {
			try {
				//loginForm.setEncryptedPassword(EncryptUtil.getInstance().encrypt(loginForm.getPassword()));
				loginForm.setEncryptedPassword(EncryptUtil.getInstance().encryptDES(loginForm.getPassword()));
				System.out.println(EncryptUtil.getInstance().decryptDES("sEWWYA00heOesReMTNEHeQ=="));
				StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;

				if (studentLogin == null) {
					message = new ActionMessage("knowledgepro.admin.validusername");
					messages.add("messages", message);
					addErrors(request, messages);
					loginForm.resetFields();
					if(CMSConstants.LINK_FOR_CJC)
					{ 
						return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
					}else
					{
						return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
					}

				} else {
					//					List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
					if(studentLogin.getStudent().getClassSchemewise().getClasses().getCourse().getProgram().getProgramType()!=null) {
						loginForm.setProgramTypeId(String.valueOf(studentLogin.getStudent().getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId()));
					}
					loginForm.setPhotoEdited(studentLogin.getStudent().getAdmAppln().getPersonalData().getIsPhotoEdited());
					loginForm.setPDataEdited(studentLogin.getStudent().getAdmAppln().getPersonalData().getIsPDataEdited());
					if(loginForm.getProgramTypeId().equalsIgnoreCase("1"))
						session.setAttribute("programType","UG");
					else
						session.setAttribute("programType","PG");

					setNewsData(loginForm);
					setDataToForm(loginForm,request);
					AdmAppln admAppln = studentLogin.getStudent().getAdmAppln();
					/* code added by sudhir */
					if(admAppln!=null && !admAppln.toString().isEmpty()){
						session.setAttribute("admApplnId",admAppln.getId());
					}else{
						session.setAttribute("admApplnId", null);
					}
					/*added code ends here */
					// display achievements in notification panel
					if(studentLogin.getRemarks() != null && !studentLogin.getRemarks().trim().isEmpty())
						session.setAttribute("studentAchievement", studentLogin.getRemarks());
					else
						session.setAttribute("studentAchievement", null);
					//	if(CMSConstants.ATTENDANCE_DISP_COURSE_LIST.contains(admAppln.getCourseBySelectedCourseId().getId())){
					session.setAttribute("showAttendanceRep", true);
					//	}
					//	else{
					//		session.setAttribute("showAttendanceRep", false);
					//	}
					loginForm.setDisplaySem1and2("false");

					// set photo to session
					// all the photos moved to one folder.

					String STUDENT_IMAGE = "images/StudentPhotos/"+studentLogin.getStudent().getId()+".jpg?"+studentLogin.getStudent().getAdmAppln().getLastModifiedDate();
					session.setAttribute("STUDENT_IMAGE", STUDENT_IMAGE);

					/*if(admAppln.getApplnDocs()!=null){
						Iterator<ApplnDoc> docItr=admAppln.getApplnDocs().iterator();
						CMSConstants.STUDENT_IMAGE="";
						while (docItr.hasNext()) {
							ApplnDoc doc = docItr.next();
							if(doc.getName()!=null && doc.getIsPhoto() && session != null){
									session.setAttribute("PhotoBytes", doc.getDocument());
								 FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+"images/StudentPhotos/"+studentLogin.getStudent().getId()+".jpg");
								 fos.write(doc.getDocument());
								 fos.close();
								 CMSConstants.STUDENT_IMAGE = "images/StudentPhotos/"+studentLogin.getStudent().getId()+".jpg";
							}
						}
					}*/
					if (admAppln.getApplnDocs() != null) {
						Iterator<ApplnDoc> docItr = admAppln.getApplnDocs()
								.iterator();
						while (docItr.hasNext()) {
							ApplnDoc doc = docItr.next();
							if (doc.getName() != null && doc.getIsPhoto()
									&& session != null
									&& doc.getDocument() != null) {
								session.setAttribute("PhotoBytes",doc.getDocument());
								FileOutputStream fos = new FileOutputStream(
										request.getRealPath("")
										+ "/images/StudentPhotos/"
										+ studentLogin.getStudent()
										.getId() + ".jpg");
								fos.write(doc.getDocument());
								fos.close();
							}
						}
					}
					if(studentLogin.getStudent().getAdmAppln().getAppliedYear() != null && studentLogin.getStudent().getAdmAppln().getAppliedYear()>0) {
						loginForm.setBatch(String.valueOf(studentLogin.getStudent().getAdmAppln().getAppliedYear()));
					}
					//List<LoginTransactionTo> loginTransactionList = LoginHandler.getInstance().getAccessableModules(loginForm);
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth() != null) {
						loginForm.setDateOfBirth(CommonUtil.getStringDate(studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()));
					}
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth() != null) {
						loginForm.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(studentLogin.getStudent().getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
					}
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail() != null){
						loginForm.setContactMail(studentLogin.getStudent().getAdmAppln().getPersonalData().getEmail());
					}
					if(studentLogin.getStudent().getBankAccNo()!=null){
						loginForm.setBankAccNo(studentLogin.getStudent().getBankAccNo());
					}
					if(studentLogin.getStudent().getRegisterNo()!=null){
						loginForm.setRegNo(studentLogin.getStudent().getRegisterNo());
					}
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getUniversityEmail()!=null){
						loginForm.setUnivEmailId(studentLogin.getStudent().getAdmAppln().getPersonalData().getUniversityEmail());
					}

					//Get the student Name, Father Name, Mother Name and Class Name. Set all to form
					if(studentLogin.getStudent()!=null && studentLogin.getStudent().getAdmAppln()!=null
							&& studentLogin.getStudent().getAdmAppln().getPersonalData()!=null){
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getFatherName()!=null){
							loginForm.setFatherName(studentLogin.getStudent().getAdmAppln().getPersonalData().getFatherName());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getMotherName()!=null){
							loginForm.setMotherName(studentLogin.getStudent().getAdmAppln().getPersonalData().getMotherName());
						}
						String studentName = "";
						String phoneNo = "";
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null){
							studentName = studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName();
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null){
							studentName = studentName + " " + studentLogin.getStudent().getAdmAppln().getPersonalData().getMiddleName();
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getLastName()!=null){
							studentName = studentName + " " + studentLogin.getStudent().getAdmAppln().getPersonalData().getLastName();
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null){
							loginForm.setCurrentAddress1(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine1());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null){
							loginForm.setCurrentAddress2(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressLine2());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null){
							loginForm.setCurrentCity(studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByCurrentAddressCityId());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null && studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!=null){
							loginForm.setCurrentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()==null && studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers().isEmpty()){
							loginForm.setCurrentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressStateOthers());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null){
							loginForm.setCurrentPincode(studentLogin.getStudent().getAdmAppln().getPersonalData().getCurrentAddressZipCode());
						}
						//------------------------permanent Address--------------------------------------------------------------
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null){
							loginForm.setPermanentAddress1(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine1());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null){
							loginForm.setPermanentAddress2(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressLine2());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!=null){
							loginForm.setPermanentCity(studentLogin.getStudent().getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null && studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName()!=null){
							loginForm.setPermanentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()==null && studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null && !studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers().isEmpty()){
							loginForm.setPermanentState(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressStateOthers());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressZipCode()!=null){
							loginForm.setPermanentPincode(studentLogin.getStudent().getAdmAppln().getPersonalData().getPermanentAddressZipCode());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getNationality()!=null && studentLogin.getStudent().getAdmAppln().getPersonalData().getNationality().getName()!=null){
							loginForm.setNationality(studentLogin.getStudent().getAdmAppln().getPersonalData().getNationality().getName());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getBloodGroup()!=null){
							loginForm.setBloodGroup(studentLogin.getStudent().getAdmAppln().getPersonalData().getBloodGroup());
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo1()!=null){
							phoneNo = studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo1();
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo2()!=null){
							phoneNo = phoneNo + "-" + studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo2();
						}
						if(studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo3()!=null){
							phoneNo = phoneNo + "-" + studentLogin.getStudent().getAdmAppln().getPersonalData().getPhNo3();
						}

						/*if (studentLogin.getStudent().getAdmAppln().getPersonalData()!=null) {
							PersonalData personalData = studentLogin.getStudent().getAdmAppln().getPersonalData();
							PersonalDataTO personalDataTO = LoginHandler.getInstance().convertBOTONew(personalData, loginForm);
							loginForm.setPersonalData(personalDataTO);
						}*/
						//-----------------------------------------------------------------------------------------------------------
						loginForm.setPhNo1(phoneNo);
						loginForm.setStudentName(studentName);
						session.setAttribute("studentName", studentName);
						loginForm.setStudentId(studentLogin.getStudent().getId());
						loginForm.setCourseId(String.valueOf(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId()));
						loginForm.setYear(Integer.toString(studentLogin.getStudent().getAdmAppln().getAppliedYear()));
						loginForm.setEnteredDob(null);
					}
					if(studentLogin.getStudent()!=null && studentLogin.getStudent().getClassSchemewise()!=null 
							&& studentLogin.getStudent().getClassSchemewise().getClasses()!=null && studentLogin.getStudent().getClassSchemewise().getClasses().getName()!=null){
						loginForm.setClassName(studentLogin.getStudent().getClassSchemewise().getClasses().getName());
						loginForm.setClassId(String.valueOf(studentLogin.getStudent().getClassSchemewise().getClasses().getId()));
						loginForm.setTermNo(studentLogin.getStudent().getClassSchemewise().getClasses().getTermNumber());
					}
					// Code Added by Mary Job----- For Holistic Supplementary Exam------Link

					if(studentLogin.getStudent()!=null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId()!=null){
						int programId=studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId();
						//if(programId!=3 && programId!=4 && programId!=94 && programId!=81 && programId!=82 && programId!=74)	
						if(programId==3)
							session.setAttribute("linkForHolisticExam", true);
						else 
							session.setAttribute("linkForHolisticExam", false);

					}

					if(studentLogin.getStudent()!=null && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId()!=null){
						int programTypeId=studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId();
						// commented by Nagarjuna.
						// Consolidate Marks Card link should be displayed for all the courses 

						/*if(CMSConstants.UG_ID.contains(programTypeId)){
							session.setAttribute("birtUg", true);
							if(CMSConstants.LINK_FOR_CONSOLIDATE_MARKS_CARD && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId() != 18){
								if(studentLogin.getStudent().getAdmAppln().getAppliedYear()==2009 && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()!=4
										&& studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()!=4
										&& studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()!=17
										&& studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()!=34)
									session.setAttribute("linkForConsolidateMarksCard", true);
								else if(studentLogin.getStudent().getAdmAppln().getAppliedYear()==2008 && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()==10)
									session.setAttribute("linkForConsolidateMarksCard", true);
								else
									session.setAttribute("linkForConsolidateMarksCard", false);
							}else{
								session.setAttribute("linkForConsolidateMarksCard", false);
							}
						}else if(CMSConstants.PG_ID.contains(programTypeId)){
							session.setAttribute("birtUg", false);
							if(CMSConstants.LINK_FOR_CONSOLIDATE_MARKS_CARD && ((studentLogin.getStudent().getAdmAppln().getAppliedYear()==2009 && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()==56) ||
									(studentLogin.getStudent().getAdmAppln().getAppliedYear()==2010 && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()!=56 && studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()!=3))){
								session.setAttribute("linkForConsolidateMarksCard", true);
							}else{
								session.setAttribute("linkForConsolidateMarksCard", false);
							}
						}*/
						loginForm.setProgramTypeId(String.valueOf(programTypeId));
					}

					if(CMSConstants.SHOW_LINK.equalsIgnoreCase("true"))
						session.setAttribute("showLinks", true);
					else
						session.setAttribute("showLinks",false);

					List<OrganizationTO> organisations=OrganizationHandler.getInstance().getOrganizationDetails();
					if(organisations!=null){
						Iterator<OrganizationTO> orgItr=organisations.iterator();
						while (orgItr.hasNext()) {
							OrganizationTO orgTO = (OrganizationTO) orgItr.next();
							session.setAttribute("ChangePassword",orgTO.getChangePassword());
							if(orgTO.getLogo()!=null)
								session.setAttribute("LogoBytes", orgTO.getLogo());
						}
					}

					session.setAttribute("uid", String.valueOf(studentLogin.getId()));
					session.setAttribute("studentid", String.valueOf(studentLogin.getStudent().getId()));
					session.setAttribute("username", studentLogin.getUserName());
					session.setMaxInactiveInterval(CMSConstants.MAX_SESSION_INACTIVE_TIME);


					//----------added by jismy for Hall Ticket
					String studentid = (String) session.getAttribute("studentid");
					int classId = DownloadHallTicketHandler.getInstance().getClassId(Integer.parseInt(studentid), loginForm);
					session.setAttribute("CurrentClassId", classId);
					session.setAttribute("stuRegNo", loginForm.getRegNo());
					int supplExamId =0;
					supplExamId =showSupplementaryHallTcket( session,  classId,  loginForm, studentid);
					//int marksCardClassId = DownloadHallTicketHandler.getInstance().getClassIds(Integer.parseInt(studentid), classId, false, "Marks Card");
					int grievanceClassId = DownloadHallTicketHandler.getInstance().getClassIdsForGrivance(Integer.parseInt(studentid), classId, false, "Grievance Form");
					int examId = 0;
					if(classId > 0){
						examId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(classId, loginForm, "Hall Ticket");
					}
					//pavani for grievance
					int grivExamId = 0;
					if(grievanceClassId > 0){
						grivExamId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(grievanceClassId, loginForm, "Grievance Form");
					}
					boolean isGrievanceDateValid = DownloadHallTicketHandler.getInstance().isDateValid(grievanceClassId, grivExamId, "Grievance Form", false);
					if(grievanceClassId > 0 && grivExamId > 0 && isGrievanceDateValid){
						session.setAttribute("showGievanceForm", true);
					}
					else{
						session.setAttribute("showGievanceForm", false);
					}
					int consolidateClassId = DownloadHallTicketHandler.getInstance().getClassIdsForGrivance(Integer.parseInt(studentid), classId, false, "Consolidate MarksCard");
					int consolidateExamId=0;
					if(consolidateClassId > 0){
						consolidateExamId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(consolidateClassId, loginForm, "Consolidate MarksCard");
					}
					boolean isConsolidateMarksDateValid = DownloadHallTicketHandler.getInstance().isDateValid(consolidateClassId, consolidateExamId, "Consolidate MarksCard", false);
					if(consolidateClassId > 0 && consolidateExamId > 0 && isConsolidateMarksDateValid){
						session.setAttribute("showConsolidateMarksCard", true);
					}
					else{
						session.setAttribute("showConsolidateMarksCard", false);
					}

					int internalGrivClassId = DownloadHallTicketHandler.getInstance().getClassIdsForGrivance(Integer.parseInt(studentid), classId, false, "Internal Grievance Form");
					int internalGrivExamId=0;
					if(internalGrivClassId > 0){
						session.setAttribute("InternalGrivClassId", internalGrivClassId);
						internalGrivExamId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(internalGrivClassId, loginForm, "Internal Grievance Form");
					}
					boolean isInternalMarksDateValid = DownloadHallTicketHandler.getInstance().isDateValid(internalGrivClassId, internalGrivExamId, "Internal Grievance Form", false);
					if(internalGrivClassId > 0 && internalGrivExamId > 0 && isInternalMarksDateValid){
						session.setAttribute("showInternalGrivMarksCard", true);
					}
					else{
						session.setAttribute("showInternalGrivMarksCard", false);
					}

					// vinodha for students writing regular exam in next class.
					// hall ticket for previous class after promoting to next class
					if(examId==0){
						classId = DownloadHallTicketHandler.getInstance().getPreClassId(Integer.parseInt(studentid), loginForm);
						examId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(classId, loginForm, "Hall Ticket");
					}

					session.setAttribute("examID", examId);
					loginForm.setExamId(examId);
					ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentid), classId, examId, "H");  
					boolean isBlockedStudent = false;
					if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
						isBlockedStudent = true;
					}
					boolean isLessPercentage = false;
					boolean isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(classId, examId, "Hall Ticket", false);
					if(classId > 0 && examId > 0 && isDateValid){
						session.setAttribute("showHallTicket", true);
					}
					else{
						session.setAttribute("showHallTicket", false);
					}

					if(isBlockedStudent){
						session.setAttribute("isHallTicketBlockedStudent", true);
						session.setAttribute("hallTicketBlockReason", examBlockUnblockHallTicketBO.getBlockReason());
						session.setAttribute("blockHallTicketId", examBlockUnblockHallTicketBO.getId());
						isLessPercentage=LoginHandler.getInstance().checkStudentAttendancePercentage(studentid,classId);
						session.setAttribute("isLessPercentage", isLessPercentage);
					}
					else{
						session.setAttribute("isHallTicketBlockedStudent", false);
					}
					/**----total attendance < 75 % hallticket disable ---
						boolean isUg=(Boolean)session.getAttribute("birtUg");
					 **/
					boolean isUgActive = loginForm.getProgramTypeId().equalsIgnoreCase("1");
					if(isUgActive)
					{
						isLessPercentage = LoginHandler.getInstance().checkStudentAttendancePercentage(studentid,classId);
						if(!isLessPercentage){
							boolean isCondonationPaid=LoginHandler.getInstance().isCondonationPaid(Integer.parseInt(studentid), classId);
							if(isCondonationPaid)
								loginForm.setIsAttendanceShortage(false);
							else
								loginForm.setIsAttendanceShortage(!isLessPercentage);
						}
						else
							loginForm.setIsAttendanceShortage(!isLessPercentage);
					}
					//					else
					//					{
					//						loginForm.setIsAttendanceShortage(false);
					//					}
					session.removeAttribute("agreement");
					//Change By manu,1st check condition
					ExamFooterAgreementBO agreementBO=null;
					if(loginForm.getAgreementId()>0){
						agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
					}
					if(agreementBO!= null && agreementBO.getDescription()!= null){
						session.setAttribute("agreement", agreementBO.getDescription());
					}
					else{
						session.setAttribute("agreement", null);
					}

					//----------------------
					if(supplExamId==0)
						showSupplementaryHallTcket( session,  classId,  loginForm, studentid);
					//------------
					//showSupplementaryHallTcket( session,  classId,  loginForm, studentid);
					loginForm.setRevaluationRegClassId(null);
					loginForm.setRevaluationSupClassId(null);

					// for supplementary revaluation application 

					showSupplementaryRevaluationApplication( session,  classId,  loginForm, studentid);


					int ciaCurClassId = CiaOverallReportHandler.getInstance().getClassId(Integer.parseInt(studentid));
					ExamPublishExamResultsBO examPublishExamResultsBO =  CiaOverallReportHandler.getInstance().getPublishedClassId(Integer.parseInt(studentid), ciaCurClassId);
					if(examPublishExamResultsBO!= null && examPublishExamResultsBO.getClassId() > 0){
						session.setAttribute("showOverallReport", true);
					}
					else{
						session.setAttribute("showOverallReport", false);	
					}
					if(loginForm.getProgramTypeId() != null &&  CMSConstants.UG_ID.contains(Integer.parseInt(loginForm.getProgramTypeId()))){
						session.setAttribute("birtUg", true);
					}else if(loginForm.getProgramTypeId() != null && CMSConstants.PG_ID.contains(Integer.parseInt(loginForm.getProgramTypeId()))){
						session.setAttribute("birtUg", false);
					}

					if(admAppln!= null && admAppln.getCourseBySelectedCourseId()!= null && session.getAttribute("birtUg") != null /*&&
							admAppln.getCourseBySelectedCourseId().getProgram().getStream()!= null && !admAppln.getCourseBySelectedCourseId().getProgram().getStream().trim().isEmpty()*/){
						boolean isUg=(Boolean)session.getAttribute("birtUg");
						if(isUg && Integer.parseInt(loginForm.getYear())>=2011 && loginForm.getTermNo()<=4){
							/* code added by sudhir*/
							boolean isMandatoryCourse = StudentLoginHandler.getInstance().checkCourseIsMandatory(admAppln);
							if(isMandatoryCourse){
								/* code added by sudhir */
								session.setAttribute("showCertCourse", true);
								session.setAttribute("courseId", studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
								session.setAttribute("studentId",studentLogin.getStudent().getId());
								/*int completedSubCount = StudentCertificateCourseHandler.getInstance().getCompletedCourseCount(Integer.parseInt(studentid),studentLogin.getStudent().getAdmAppln());
								int pendingCount = 0;
								if(2-completedSubCount > 0){
									pendingCount = 2-completedSubCount;
								}
								session.setAttribute("pendingCount", pendingCount);
								session.setAttribute("completedSubCount", completedSubCount);*/}else{
									session.setAttribute("showCertCourse", false);
								}
						}else{
							session.setAttribute("showCertCourse", false);
						}
					}
					else{
						session.setAttribute("showCertCourse", false);
					}
					//added by priyatham start
					if(CMSConstants.LINK_FOR_CJC){
						session.setAttribute("linkForCjc",true);
					}else{
						session.setAttribute("linkForCjc", false);
					}
					//					priyatham end
					//code added by sudhir
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null){
						loginForm.setMobileNo(studentLogin.getStudent().getAdmAppln().getPersonalData().getMobileNo2());
					}
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getId()!=0){
						loginForm.setPersonalDateId(studentLogin.getStudent().getAdmAppln().getPersonalData().getId());
					}
					//
					if(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId()!= null){
						session.setAttribute("stuCourseId", String.valueOf(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId()));
					}
					if(studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName() != null){
						session.setAttribute("STUNAME",studentLogin.getStudent().getAdmAppln().getPersonalData().getFirstName());
					}
					// added for print challan link

					boolean isSmartCardMode=StudentLoginHandler.getInstance().getStudentPaymentMode(studentLogin.getStudent().getId(),loginForm);
					if(CMSConstants.LINK_FOR_CHALLAN_PRINT && isSmartCardMode){
						session.setAttribute("linkForPrintChallan", true);
					}else{
						session.setAttribute("linkForPrintChallan", false);
					}
					session.setAttribute("studentId",studentLogin.getStudent().getId());

					//					if(studentLogin.getStudent().getAdmAppln().getAppliedYear()!=null && studentLogin.getStudent().getAdmAppln().getAppliedYear()>=2011){
					int groupId=StudentLoginHandler.getInstance().getStudentGroupIdForCourse(studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
					if(groupId>0){
						String detainQuery="select s.student.id from ExamStudentDetentionRejoinDetails s " +
								"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null) and s.student.id="+studentLogin.getStudent().getId();
						List list=PropertyUtil.getInstance().getListOfData(detainQuery);
						if(list==null || list.isEmpty()){
							session.setAttribute("linkForCertificateCourse", true);
							session.setAttribute("studentRegNo",studentLogin.getStudent().getRegisterNo());
							if(studentLogin.getStudent().getClassSchemewise()!=null)
								session.setAttribute("studentsemNo",studentLogin.getStudent().getClassSchemewise().getClasses().getTermNumber());
						}
					}else
						session.setAttribute("linkForCertificateCourse", false);
					/*}else{
						session.setAttribute("linkForCertificateCourse", false);
					}*/
					boolean isPrintAvailable=StudentLoginHandler.getInstance().checkOnlineReciepts(studentLogin.getStudent().getId());
					if(isPrintAvailable)
						session.setAttribute("linkForOnlineReciepts", true);
					else
						session.setAttribute("linkForOnlineReciepts", false);
					// code added by sudhir
					// checking in database whether the classid is their for today date to appear the faculty Feedback link 
					if(studentLogin.getStudent().getClassSchemewise()!=null){
						session.setAttribute("classSchemeWiseId", String.valueOf(studentLogin.getStudent().getClassSchemewise().getId()));
					}else{
						session.setAttribute("classSchemeWiseId",null);
					}
					session.setAttribute("ClassId", String.valueOf(studentLogin.getStudent().getClassSchemewise().getClasses().getId()));
					String classid = session.getAttribute("ClassId").toString();
					/*newly added */
					Map<Integer,String> specializationIds = StudentLoginHandler.getInstance().getSpecializationIds(loginForm);

					/*-----------*/
					boolean isFacultyFeedbackAvailable = StudentLoginHandler.getInstance().isFacultyFeedbackAvailable(Integer.parseInt(classid),session,specializationIds);
					if(isFacultyFeedbackAvailable){
						session.setAttribute("studentFacultyFeedback", true);
					}else{
						session.setAttribute("studentFacultyFeedback", false);
					}
					boolean finalStudent=false;
					boolean isStudentPhotoUpload=false;
					if(studentLogin.getStudent() != null && studentLogin.getStudent().getId() !=0){
						Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
						if(organisation!=null){
							if(organisation.getOpenHonoursCourseLink()){
								if(studentLogin.getStudent().getClassSchemewise().getClasses().getTermNumber() == 4){
									boolean display = StudentLoginHandler.getInstance().checkHonoursCourse(studentLogin.getStudent().getId(),studentLogin.getStudent().getAdmAppln().getCourseBySelectedCourseId().getId());
									session.setAttribute("honoursLink", display);
								}else{
									session.setAttribute("honoursLink", false);
								}
							}else{
								session.setAttribute("honoursLink", false);
							}
							if(organisation.getConvocationRegistration()!=null){
								finalStudent=organisation.getConvocationRegistration();
							}
							if(organisation.getStudentPhotoUpload()!=null){
								isStudentPhotoUpload=organisation.getStudentPhotoUpload();
							}
						}else{
							session.setAttribute("honoursLink", false);
						}
					}
					//Added By Manu For Repeat MidSem Exam Apply Link
					if(loginForm.getTermNo()>1){
						session.setAttribute("previousAttendance", true);
					}else{
						session.setAttribute("previousAttendance", false);
					}
					loginForm.clearMideSemExam();
					session.setAttribute("linkForRepeatExamsApplication", false);
					session.setAttribute("linkForRepeatExamsFeePayment",false);
					session.setAttribute("linkFordownloadHallTicket", false);


					ExamMidsemRepeatSetting checkForApplicationDate=ExamMidsemRepeatHandler.getInstance().checkForApplicationDate(loginForm);
					ExamMidsemRepeatSetting checkForFeesDate=ExamMidsemRepeatHandler.getInstance().checkForFeePaymentDate(loginForm);
					String linkForRepeatExam=null;
					String FeePaid=null;
					//	String hallTicket=null;
					if(checkForFeesDate!=null)
					{
						FeePaid=ExamMidsemRepeatHandler.getInstance().checkForRepeatFeesPaymentExam(studentLogin.getStudent().getId(),checkForFeesDate.getMidSemExamId().getId(),loginForm);
					}//hallTicket= ExamMidsemRepeatHandler.getInstance().checkForRepeatHallticket(studentLogin.getStudent().getId(),loginForm);
					Date todayDate = new Date();
					Date feeEndDate=null;
					Date FeeEnddate=null;
					Date TodayDate=null;
					if(loginForm.getFeeEndDate()!=null && !loginForm.getFeeEndDate().isEmpty()){
						feeEndDate=CommonUtil.ConvertStringToDate(loginForm.getFeeEndDate());
						FeeEnddate = CommonUtil.ConvertStringToDate(CommonUtil.formatDates(feeEndDate));
						TodayDate = CommonUtil.ConvertStringToDate(CommonUtil.formatDates(todayDate));
					}
					if(checkForApplicationDate!=null) {
						linkForRepeatExam=ExamMidsemRepeatHandler.getInstance().checkForRepeatApplicationExam(studentLogin.getStudent().getId(),checkForApplicationDate.getMidSemExamId().getId(),loginForm);
						if(linkForRepeatExam.equalsIgnoreCase("validData"))
							session.setAttribute("linkForRepeatExamsApplication", true);
						else if(linkForRepeatExam.equalsIgnoreCase("isApproved") && ((FeeEnddate!=null && TodayDate!=null) &&  FeeEnddate.after(TodayDate)))
							session.setAttribute("linkForRepeatExamsFeePayment",true);
						else if(linkForRepeatExam.equalsIgnoreCase("isApproved") && ((FeeEnddate!=null && TodayDate!=null) && FeeEnddate.equals(TodayDate)))
							session.setAttribute("linkForRepeatExamsFeePayment",true);
						if(FeePaid!=null && !FeePaid.isEmpty()){
							if(FeePaid.equalsIgnoreCase("hallTicket"))
								session.setAttribute("linkFordownloadHallTicket", true);
						}
					}
					else
					{	
						if(checkForFeesDate!=null){
							if(FeePaid!=null && FeePaid.equalsIgnoreCase("hallTicket"))
								session.setAttribute("linkFordownloadHallTicket", true); 
							else if(FeePaid!=null && FeePaid.equalsIgnoreCase("isApproved"))
								session.setAttribute("linkForRepeatExamsFeePayment",true);
						}
					}
					//endManu
					//added by mahi
					session.setAttribute("linkForConsolidateMarksCard", false);
					if(finalStudent){
						boolean studentIsFinalYearOrNot=LoginHandler.getInstance().checkStudentIsFinalYearOrNot(loginForm,session);
						if(studentIsFinalYearOrNot){
							session.setAttribute("finalYearStudent", true);	
							if(CMSConstants.LINK_FOR_CONSOLIDATE_MARKS_CARD){
								session.setAttribute("linkForConsolidateMarksCard", true);
							}
						}else{
							session.setAttribute("finalYearStudent", false);	
						}
					}else{
						boolean studentIsFinalYearOrNot=LoginHandler.getInstance().checkStudentIsFinalYearOrNot(loginForm,session);
						if(studentIsFinalYearOrNot){
							if(CMSConstants.LINK_FOR_CONSOLIDATE_MARKS_CARD){
								session.setAttribute("linkForConsolidateMarksCard", true);
							}
						}
					}

					//code added by chandra for checking SAP Resuls Link
					if(studentLogin.getStudent() != null && studentLogin.getStudent().getId() !=0){
						boolean isApplied=StudentLoginHandler.getInstance().isAppliedForSAPExam(studentLogin.getStudent().getId());
						if(isApplied){
							session.setAttribute("sapResultLinks", true);
						}else{
							session.setAttribute("sapResultLinks", false);
						}

					}
					//Hostel Leave application 
					boolean hostelLinks = HostelLeaveHandler.getInstance().checkStudentDetails(studentLogin.getStudent().getId());
					if(hostelLinks){
						session.setAttribute("hostelLinks", true);
					}else{
						session.setAttribute("hostelLinks", false);
					}
					/*--------------- SAP EXAM REGISTRATION LINK DISPLAY--------------------------------- */
					//					if the student is passed do not display the link . if failed display link as SAP Supplementry Exam.
					UploadSAPMarksBo bo=StudentLoginHandler.getInstance().getSAPExamResuls(studentid);
					if(bo!=null){
						if(bo.getStatus().equalsIgnoreCase("PASS") || bo.getStatus().equalsIgnoreCase("PASSED")){
							loginForm.setStatus("PASS");
						}else if(bo.getStatus().equalsIgnoreCase("Fail") || bo.getStatus().equalsIgnoreCase("Failed")){
							loginForm.setStatus("FAIL");
						}
					}else{
						loginForm.setStatus("ALLOW");
					}
					/*---------------SAP EXAM REGISTRATION LINK DISPLAY Code ends Here  */

					//added by mahi start
					loginForm.setServerDownMessage(null);
					String maintenanceMessage =  MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
					if(maintenanceMessage!=null){
						loginForm.setServerDownMessage(maintenanceMessage);
						session.setAttribute("serverDownMessage", maintenanceMessage);
					}
					//end
					if(isStudentPhotoUpload){
						boolean finalYearStudent=LoginHandler.getInstance().checkStudentIsFinalYearOrNot(loginForm,session);
						if(finalYearStudent){
							loginForm.setStudentPhotoUpload(true);
						}else{
							loginForm.setStudentPhotoUpload(false);
						}
					}else{
						loginForm.setStudentPhotoUpload(false);
					}
					//Basim addded for examIds for publishing multiple exam 
					List<Integer> examIds=NewSupplementaryImpApplicationHandler.getInstance().checkSuppImpAppAvailable(loginForm.getStudentId());

					//Basim added for improvement

					if(examIds.isEmpty() || examIds==null){
						examIds=NewSupplementaryImpApplicationHandler.getInstance().checkSuppImpAppAvailable1(loginForm.getStudentId());
						if(examIds.isEmpty() || examIds==null){
							session.setAttribute("isSupplyImpAppDisplay", false);
							session.setAttribute("examIdList", null);
						}
						else{
							session.setAttribute("examIdList", examIds);
							session.setAttribute("isSupplyImpAppDisplay", true);
						}
					}
					else
					{
						session.setAttribute("examIdList", examIds);
						session.setAttribute("isSupplyImpAppDisplay", true);
					}

					/**
					 * By Arun Sudhakaran for showing links for application for Internal Redo (28/07/17)
					 */

					List<Integer> internalRedoExamIds = NewSupplementaryImpApplicationHandler.getInstance().checkInternalRedoAppAvailable(loginForm.getStudentId());
					if(internalRedoExamIds.isEmpty() || internalRedoExamIds==null){

						session.setAttribute("isInternalRedoImpAppDisplay", false);
						session.setAttribute("internalRedoExamIds", null);
					}
					else {

						session.setAttribute("isInternalRedoImpAppDisplay", true);
						session.setAttribute("internalRedoExamIds", internalRedoExamIds);							
					}

					//added by vishnu/raghu
					String sectionForAtt = "";

					sectionForAtt =  request.getParameter("attendanceSession");
					if(sectionForAtt==null)
					{
						sectionForAtt = "am";
					}
					String jsonChart = AttendanceGraphHandler.getInstance().getAttendanceGraph(sectionForAtt,loginForm);
					session.setAttribute("jsonChart", jsonChart);
					session.setAttribute("sectionForAtt", sectionForAtt);


					//raghu write new regular app
					// current classId(as it is overrided with the previous classId)
					classId = DownloadHallTicketHandler.getInstance().getClassId(Integer.parseInt(studentid), loginForm);

					if (classId > 0) {
						examId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(classId, loginForm,"Regular Application");
					}

					loginForm.setRegAppExamId(""+examId);
					examBlockUnblockHallTicketBO = null;
					if (examId > 0) {
						examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentid), classId,examId, "A");
					}
					boolean isBlockedStudentRegularApp = false;
					if (examBlockUnblockHallTicketBO != null && examBlockUnblockHallTicketBO.getId() > 0) {
						isBlockedStudentRegularApp = true;
					}
					isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(classId, examId,"Regular Application", false);
					if (classId > 0 && examId > 0 && isDateValid) {
						session.setAttribute("showRegApp", true);
					} else {
						session.setAttribute("showRegApp", false);
					}
					if (isBlockedStudentRegularApp) {
						session.setAttribute("isBlockedStudentRegularApp",true);


					} else {
						session.setAttribute("isBlockedStudentRegularApp",false);
					}

					// Ashwini for revaluation

					int revExamId=0;
					int revClassId =0;
					int schemeNo =0;

					revClassId = DownloadHallTicketHandler.getInstance().getClassIdForRevaluation(Integer.parseInt(studentid), loginForm);
					revExamId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(revClassId, loginForm,"Revaluation/Scrutiny");
					schemeNo = loginForm.getTermNo();
					loginForm.setRevClassId(revClassId);
					// bring old class id
					if(revExamId==0){
						List<Integer> prevClassIds = new ArrayList<Integer>();
						int prevClassIdForRev = DownloadHallTicketHandler.getInstance().getPreClassId(Integer.parseInt(studentid), loginForm);
						prevClassIds.add(prevClassIdForRev);

						INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
						List<Object> revalexamIds=transaction.checkRevaluationAppAvailable(prevClassIds);


						if(revalexamIds.size()>0){
							Iterator<Object> itr = revalexamIds.iterator();
							while(itr.hasNext()){
								Object[] obj = (Object[]) itr.next();
								revExamId = Integer.parseInt(obj[0].toString());
								revClassId = Integer.parseInt(obj[1].toString());
								schemeNo = Integer.parseInt(obj[2].toString());

							}
						}
					}


					session.setAttribute("revclassId",revClassId);
					session.setAttribute("revAppExamId", revExamId);
					session.setAttribute("prevSemNo", schemeNo);
					loginForm.setRevClassId(revClassId);
					examBlockUnblockHallTicketBO = null;
					if (revExamId > 0) {
						examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentid), revClassId,revExamId, "A");
					}

					isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(revClassId, revExamId,"Revaluation/Scrutiny", false);
					if (revClassId > 0 && revExamId > 0 && isDateValid) {
						session.setAttribute("showRevApp", true);
					} else {
						session.setAttribute("showRevApp", false);
					}


					session.setAttribute("isStudent", "1");	
					boolean isPublishedit=LoginHandler.getInstance().checkPublish(classId,studentid);
					if(isPublishedit){
						session.setAttribute("studentEdit", true);
					}else {
						session.setAttribute("studentEdit", false);
					}

					//end by mahi
					if(studentLogin.getIsTempPassword()){
						session.setAttribute("tempPasswordLogin", true);
						return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS_PASSCHANGE);
					}else {	
						session.setAttribute("tempPasswordLogin", false);

						if(CMSConstants.LINK_FOR_CJC)
						{ 
							return mapping.findForward(CMSConstants.STUDENT_LOGIN_SUCCESS_CJC);
						}else
						{
							return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS);
						}
					}
				}
			} catch (ApplicationException e) {
				log.debug("leaving the studentLoginAction with exception");
				e.printStackTrace();
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			} catch(Exception e) {
				log.debug("leaving the studentLoginAction with exception");
				e.printStackTrace();
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
				//throw e;
			}
		} else {
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);		
			if(CMSConstants.LINK_FOR_CJC)
			{ 
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
			}else
			{
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			}
		}
	}

	/**
	 * @param session
	 * @param classId
	 * @param loginForm
	 * @param studentid
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void showRegularMarksCard(HttpSession session, int classId,
			LoginForm loginForm, String studentId) throws NumberFormatException, Exception {
		List<ShowMarksCardTO> regMarksCard=new ArrayList<ShowMarksCardTO>();
		int examId = 0;
		int count=1;
		List<Integer> classList = DownloadHallTicketHandler.getInstance().getRegularClassIds(Integer.parseInt(studentId), classId, true, "Marks Card");
		if(classList!=null && !classList.isEmpty()){
			for (Iterator iterator = classList.iterator(); iterator.hasNext();) {
				ShowMarksCardTO to=new ShowMarksCardTO();
				to.setCount(count);
				count+=1;
				Integer marksCardClassId = (Integer) iterator.next();
				if(marksCardClassId > 0){
					examId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(marksCardClassId, loginForm, "Marks Card");
				}
				Integer semesterYearNo = DownloadHallTicketHandler.getInstance().getTermNumber(marksCardClassId);
				// Code commented by Balaji
				//				session.setAttribute("supMCClassId", marksCardClassId);
				//				session.setAttribute("supMCsemesterYearNo", semesterYearNo);
				//				session.setAttribute("supMCexamID", examId);

				to.setRegMCClassId(marksCardClassId);
				to.setRegMCsemesterYearNo(semesterYearNo);
				to.setRegMCexamID(examId);

				session.setAttribute("examIDForMCard", examId);
				loginForm.setMarksCardClassId(marksCardClassId);
				loginForm.setSemesterYearNo(semesterYearNo);


				ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentId), marksCardClassId, examId, "M");  
				boolean isBlockedStudent = false;
				if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
					isBlockedStudent = true;
				}
				boolean isExcluded = DownloadHallTicketHandler.getInstance().getIsExcluded(Integer.parseInt(studentId), examId);
				boolean isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(marksCardClassId, examId, "Marks Card", false);
				//boolean isAppeared = 
				DownloadHallTicketHandler.getInstance().isAppeared(Integer.parseInt(studentId), examId,marksCardClassId,"null");
				if(marksCardClassId > 0 && examId > 0 && isDateValid && !isExcluded){
					//					session.setAttribute("showSupMC", true);
					to.setShowRegMC(true);
				}
				else{
					//					session.setAttribute("showSupMC", false);
					to.setShowRegMC(false);
				}
				if(isBlockedStudent){
					//					session.setAttribute("isSupMCBlockedStudent", true);
					//					session.setAttribute("supMCBlockReason", examBlockUnblockHallTicketBO.getBlockReason());
					to.setRegMCBlockedStudent(true);
					to.setRegMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
				}
				else{
					//					session.setAttribute("isSupMCBlockedStudent", false);
					to.setRegMCBlockedStudent(false);
				}	
				if(to.isShowRegMC()){
					regMarksCard.add(to);
				}
				//Change By manu,1st check condition
				ExamFooterAgreementBO agreementBO=null;
				if(loginForm.getAgreementId()>0){
					agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
				}
				if(agreementBO!= null && agreementBO.getDescription()!= null){
					session.setAttribute("marksCardAgreement", agreementBO.getDescription());
				}
				else{
					session.setAttribute("marksCardAgreement", null);
				}
			}
			session.setAttribute("regMarksCard", regMarksCard);
		}
	}

	public ActionForward studentLoginLoadData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		if(CMSConstants.LINK_FOR_CJC)
		{ 
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SUCCESS_CJC);
		}else
		{
			return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS);
		}
	}
	public ActionForward studentLoginHallTicketAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return mapping.findForward(CMSConstants.STUDENTLOGIN_AGREEMENT);
	}

	/**
	 * performs logout action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward studentLogoutAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		LoginForm loginForm = (LoginForm) form;

		HttpSession session= request.getSession(false);
		try {
			setNewsData(loginForm);
			boolean studnetLoginNew=false;
			if(session.getAttribute("studnetLoginNew")!=null && !session.getAttribute("studnetLoginNew").toString().isEmpty()){
				studnetLoginNew=(Boolean)session.getAttribute("studnetLoginNew");	
			}
			if (session != null) {
				session.invalidate();
			}
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				session= request.getSession(true);
				if(session.getAttribute("username")!=null){
					session.removeAttribute("username");
				}
				// set photo to session
				if(!studnetLoginNew){
					session.setAttribute("studnetLoginNew",false);
					if(organisation.getLogoContentType()!=null){
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
						}
					}
				}else{
					session.setAttribute("studnetLoginNew",true);
					if(organisation.getLogoContentType1()!=null){
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo1());
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar1());
						}
					}
				}
			}	
			loginForm.setServerDownMessage(null);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if(CMSConstants.LINK_FOR_CJC)
		{ 
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_LOGOUT_SUCCESS_CJC);
		}else
		{
			return mapping.findForward(CMSConstants.STUDENTLOGOUT_SUCCESS);
		}
	}

	public void setNewsData(LoginForm loginForm) throws Exception {
		StringBuffer description = new StringBuffer();
		NewsEventsTO eventsTO = null;
		try {
			List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance()
					.getNewsEvents(CMSConstants.LOGIN_STUDENT);
			Iterator<NewsEventsTO> itr = newsEventsList.iterator();
			while (itr.hasNext()) {
				eventsTO = (NewsEventsTO) itr.next();
				description.append(eventsTO.getName()).append("<br/><br/>");
			}
			loginForm.setDescription(description.toString());

		}catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	public ActionForward studentLoginMarksCardAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_AGREEMENT);
	}
	@SuppressWarnings("unchecked")
	public ActionForward studentLoginHallTicketBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		LoginForm loginForm=(LoginForm)form;
		if(loginForm.isSupHallTicket() && loginForm.getCount()>0){
			HttpSession session=request.getSession(false);
			List<ShowMarksCardTO> list=(ArrayList<ShowMarksCardTO>) session.getAttribute("supHallTicketList");
			for (ShowMarksCardTO to : list) {
				if(to.getCount()==loginForm.getCount()){
					session.setAttribute("hallTicketBlockReason",to.getSupMCBlockReason());
				}
			}
		}
		return mapping.findForward(CMSConstants.STUDENTLOGIN_HALL_TICKET_BLOCK_MES);
	}
	public ActionForward studentLoginMarksCardBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_BLOCK_MES);
	}
	/**
	 * 
	 * @param session
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public int showSupplementaryHallTcket(HttpSession session, int classId, LoginForm loginForm, String studentId) throws NumberFormatException, Exception{

		Map<Integer,List<Integer>> examClassMap=new HashMap<Integer, List<Integer>>();
		List<Integer> suppHallTcktClassIds=DownloadHallTicketHandler.getInstance().getSupplementaryClassIds(Integer.parseInt(studentId), classId, true, "Hall Ticket");
		List<Integer> classIds=null;
		String exams = "";
		int suppExamId=0;
		for (Integer hallTcktClassId : suppHallTcktClassIds) {
			exams = "";
			if(hallTcktClassId > 0){
				exams = DownloadHallTicketHandler.getInstance().getExamIdByClassIdForSupHallTicket(hallTcktClassId, loginForm, "Hall Ticket");
			}
			if(!exams.isEmpty()){
				String[] examsId=exams.split(",");
				for (String examId : examsId) {
					suppExamId = Integer.parseInt(examId);
					if(examClassMap.containsKey(Integer.parseInt(examId))){
						classIds=examClassMap.remove(Integer.parseInt(examId));
					}else
						classIds=new ArrayList<Integer>();
					classIds.add(hallTcktClassId);
					examClassMap.put(Integer.parseInt(examId),classIds);
				}
			}
		}

		List<ShowMarksCardTO> supHallTicketList=new ArrayList<ShowMarksCardTO>();
		// map key is examId and value is list of classIds
		int count=1;
		int examId=0;
		for (Map.Entry<Integer, List<Integer>> entry : examClassMap.entrySet()) {
			examId=entry.getKey();
			ShowMarksCardTO to=new ShowMarksCardTO();
			to.setSupMCexamID(examId);
			to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId,"ExamDefinitionBO",true,"name"));
			to.setCnt(count);
			count+=1;
			List<Integer> classesId=new ArrayList<Integer>();
			for (Integer hallTcktClassId : entry.getValue()) {
				ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentId), hallTcktClassId, examId, "H");  
				boolean isBlockedStudent = false;
				if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
					isBlockedStudent = true;
				}
				boolean isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(hallTcktClassId, examId, "Hall Ticket", true);
				boolean isAppeared = DownloadHallTicketHandler.getInstance().isAppeared(Integer.parseInt(studentId), examId,hallTcktClassId,"hallTicket");
				if(hallTcktClassId > 0 && examId > 0 && isDateValid && isAppeared){
					classesId.add(hallTcktClassId);
					to.setShowSupMC(true);
				}
				if(isBlockedStudent){
					to.setSupMCBlockedStudent(true);
					to.setSupMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
				}
				//Change By manu,1st check condition
				ExamFooterAgreementBO agreementBO=null;
				if(loginForm.getAgreementId()>0){
					agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
				}
				if(agreementBO!= null && agreementBO.getDescription()!= null){
					to.setSupHallTicketagreement(agreementBO.getDescription());
				}
			}
			to.setClassIds(classesId);
			if(to.isShowSupMC()){
				supHallTicketList.add(to);
			}
		}
		session.setAttribute("supHallTicketList", supHallTicketList);
		return suppExamId;

	}

	@SuppressWarnings("unchecked")
	public ActionForward studentLoginHallTicketSuplementaryAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		LoginForm loginForm=(LoginForm)form;
		if(loginForm.getCount()>0){
			HttpSession session=request.getSession(false);
			List<ShowMarksCardTO> list=(ArrayList<ShowMarksCardTO>) session.getAttribute("supHallTicketList");
			for (ShowMarksCardTO to : list) {
				if(to.getCnt()==loginForm.getCount()){
					session.setAttribute("agreement",to.getSupHallTicketagreement());
				}
			}
		}
		return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_HALL_AGREEMENT);
	}


	/**
	 * 
	 * @param session
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("unchecked")
	public void showSupplementaryMarksCard(HttpSession session, int classId, LoginForm loginForm, String studentId) throws NumberFormatException, Exception{
		List<ShowMarksCardTO> supMarksCard=new ArrayList<ShowMarksCardTO>();
		int examId = 0;
		int count=1;
		List<Integer> classList = DownloadHallTicketHandler.getInstance().getSupplementaryClassIds(Integer.parseInt(studentId), classId, true, "Marks Card");
		if(classList!=null && !classList.isEmpty()){
			for (Iterator iterator = classList.iterator(); iterator.hasNext();) {
				ShowMarksCardTO to=new ShowMarksCardTO();
				to.setCnt(count);
				count+=1;
				Integer marksCardClassId = (Integer) iterator.next();
				if(marksCardClassId > 0){
					examId = DownloadHallTicketHandler.getInstance().getExamIdByClassIdForSupMarksCard(marksCardClassId, loginForm, "Marks Card");
				}
				Integer semesterYearNo = DownloadHallTicketHandler.getInstance().getTermNumber(marksCardClassId);
				// Code commented by Balaji
				//				session.setAttribute("supMCClassId", marksCardClassId);
				//				session.setAttribute("supMCsemesterYearNo", semesterYearNo);
				//				session.setAttribute("supMCexamID", examId);

				to.setSupMCClassId(marksCardClassId);
				to.setSupMCsemesterYearNo(semesterYearNo);
				to.setSupMCexamID(examId);

				loginForm.setSupMCClassId(marksCardClassId);
				loginForm.setSupMCexamID(examId);
				loginForm.setSupMCsemesterYearNo(semesterYearNo);

				ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentId), marksCardClassId, examId, "M");  
				boolean isBlockedStudent = false;
				if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
					isBlockedStudent = true;
				}
				boolean isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(marksCardClassId, examId, "Marks Card", true);
				boolean isAppeared = DownloadHallTicketHandler.getInstance().isAppeared(Integer.parseInt(studentId), examId,marksCardClassId,"marksCard");
				if(marksCardClassId > 0 && examId > 0 && isDateValid && isAppeared){
					//					session.setAttribute("showSupMC", true);
					to.setShowSupMC(true);
				}
				else{
					//					session.setAttribute("showSupMC", false);
					to.setShowSupMC(false);
				}
				if(isBlockedStudent){
					//					session.setAttribute("isSupMCBlockedStudent", true);
					//					session.setAttribute("supMCBlockReason", examBlockUnblockHallTicketBO.getBlockReason());
					to.setSupMCBlockedStudent(true);
					to.setSupMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
				}
				else{
					//					session.setAttribute("isSupMCBlockedStudent", false);
					to.setSupMCBlockedStudent(false);
				}	
				if(to.isShowSupMC()){
					supMarksCard.add(to);
				}
			}
			session.setAttribute("supMarksCard", supMarksCard);
		}

	}

	public ActionForward studentLoginSupMarksCardBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		int count=Integer.parseInt(request.getParameter("count"));
		request.setAttribute("count",count);
		return mapping.findForward(CMSConstants.STUDENTLOGIN_SUPPLY_MARKS_CARD_BLOCK_MES);
	}

	public ActionForward getHallTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		HallTicketTo hallTickets=DownloadHallTicketHandler.getInstance().getHallticketForStudent(loginForm);
		if(hallTickets==null){
			request.setAttribute("isNull", true);
		}
		else
			request.setAttribute("isNull", false);
		loginForm.setHallTicket(hallTickets);
		return mapping.findForward(CMSConstants.STUDENTLOGIN_HALL_TICKET);
	}

	public ActionForward printHallTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		//LoginForm loginForm = (LoginForm) form;
		return mapping.findForward(CMSConstants.STUDENTLOGIN_HALL_TICKET_PRINT);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		LoginForm loginForm = (LoginForm) form;
		MarksCardTO marksCardTo=null;
		boolean isUg=false;
		if(request.getSession().getAttribute("birtUg")!=null)
			isUg=(Boolean)request.getSession().getAttribute("birtUg");

		if(isUg){
			marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForUG(loginForm,request);
		}else{
			marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
		}
		if(loginForm.getBatch()!=null && loginForm.getProgramTypeId()!=null){
			if(loginForm.getBatch().equalsIgnoreCase("2012") && loginForm.getProgramTypeId().equalsIgnoreCase("3"))
				loginForm.setDisplaySem1and2("true");
		}
		loginForm.setMarksCardTo(marksCardTo);
		if(loginForm.getCourseId()!=null && !loginForm.getCourseId().equalsIgnoreCase("18")){
			IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
			List<ExamFooterAgreementBO> footer=transaction.getFooterDetails(loginForm.getProgramTypeId(),"M",loginForm.getClassId());
			if(footer!=null && !footer.isEmpty()){
				ExamFooterAgreementBO obj=footer.get(0);
				if(obj.getDescription()!=null)
					loginForm.setDescription1(obj.getDescription());
			}else{
				loginForm.setDescription1(null);
			}
		}else{
			loginForm.setDescription1(CMSConstants.MARKS_CARD_DESCRIPTION);
		}
		if(isUg){
			return mapping.findForward(CMSConstants.STUDENTLOGIN_UG_MARKS_CARD);
		}else{
			return mapping.findForward(CMSConstants.STUDENTLOGIN_PG_MARKS_CARD);
		}
	}


	public ActionForward printMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		boolean isUg=false;
		if(request.getSession().getAttribute("birtUg")!=null)
			isUg=(Boolean)request.getSession().getAttribute("birtUg");

		if(isUg){
			return mapping.findForward(CMSConstants.PRINT_UG_MARKS_CARD);
		}else{
			return mapping.findForward(CMSConstants.PRINT_PG_MARKS_CARD);
		}
	}


	public ActionForward getSuppHallTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		HallTicketTo hallTickets=DownloadHallTicketHandler.getInstance().getSupHallticketForStudent(loginForm,request);
		loginForm.setHallTicket(hallTickets);
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		List<ExamFooterAgreementBO> footer=transaction.getFooterDetails(loginForm.getProgramTypeId(),"H",loginForm.getClassId());
		if(footer!=null && !footer.isEmpty()){
			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				loginForm.setDescription1(obj.getDescription());
		}else{
			loginForm.setDescription1(null);
		}
		return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_HALL_TICKET);
	}

	public ActionForward printSuppHallTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		//LoginForm loginForm = (LoginForm) form;
		return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_HALL_TICKET_PRINT);
	}



	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentSupMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		LoginForm loginForm = (LoginForm) form;
		MarksCardTO marksCardTo=null;
		boolean isUg=false;
		if(request.getSession().getAttribute("birtUg")!=null)
			isUg=(Boolean)request.getSession().getAttribute("birtUg");

		if(isUg){
			marksCardTo=DownloadHallTicketHandler.getInstance().getSupMarksCardForUG(loginForm,request);
		}else{
			marksCardTo=DownloadHallTicketHandler.getInstance().getSupMarksCardForPG(loginForm,request);
		}
		loginForm.setMarksCardTo(marksCardTo);
		IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
		List<ExamFooterAgreementBO> footer=transaction.getFooterDetails(loginForm.getProgramTypeId(),"M", loginForm.getClassId());
		if(footer!=null && !footer.isEmpty()){
			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				loginForm.setDescription1(obj.getDescription());
		}else{
			loginForm.setDescription1(null);
		}
		if(isUg){
			return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_UG_MARKS_CARD);
		}else{
			return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_PG_MARKS_CARD);
		}
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printSupMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		//LoginForm loginForm = (LoginForm) form;
		boolean isUg=false;
		if(request.getSession().getAttribute("birtUg")!=null)
			isUg=(Boolean)request.getSession().getAttribute("birtUg");

		if(isUg){
			return mapping.findForward(CMSConstants.PRINT_SUP_UG_MARKS_CARD);
		}else{
			return mapping.findForward(CMSConstants.PRINT_SUP_PG_MARKS_CARD);
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnHomePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		LoginForm loginForm = (LoginForm)form;
		HttpSession session= request.getSession(false);
		String stuId=(String) request.getSession().getAttribute("studentid");
		if (stuId==null) {
			stuId=String.valueOf(loginForm.getStudentId());
		}

		Student student = LoginHandler.getInstance().getStudentObj(stuId);
		AdmAppln admAppln = student.getAdmAppln();
		if (admAppln.getApplnDocs() != null) {
			Iterator<ApplnDoc> docItr = admAppln.getApplnDocs()
					.iterator();
			while (docItr.hasNext()) {
				ApplnDoc doc = docItr.next();
				if (doc.getName() != null && doc.getIsPhoto()
						&& session != null
						&& doc.getDocument() != null) {
					session.setAttribute("PhotoBytes",doc.getDocument());
					FileOutputStream fos = new FileOutputStream(
							request.getRealPath("")
							+ "/images/StudentPhotos/"
							+ student
							.getId() + ".jpg");
					fos.write(doc.getDocument());
					fos.close();
				}
			}
		}

		if(student.getAdmAppln().getAppliedYear() != null && student.getAdmAppln().getAppliedYear()>0) {
			loginForm.setBatch(String.valueOf(student.getAdmAppln().getAppliedYear()));
		}
		if(student.getAdmAppln().getPersonalData().getDateOfBirth() != null) {
			loginForm.setDateOfBirth(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()));
		}
		if(student.getAdmAppln().getPersonalData().getDateOfBirth() != null) {
			loginForm.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
		}
		if(student.getAdmAppln().getPersonalData().getEmail() != null){
			loginForm.setContactMail(student.getAdmAppln().getPersonalData().getEmail());
		}
		if(student.getBankAccNo()!=null){
			loginForm.setBankAccNo(student.getBankAccNo());
		}
		if(student.getRegisterNo()!=null){
			loginForm.setRegNo(student.getRegisterNo());
		}
		if(student.getAdmAppln().getPersonalData().getUniversityEmail()!=null){
			loginForm.setUnivEmailId(student.getAdmAppln().getPersonalData().getUniversityEmail());
		}
		if(student.getAdmAppln().getPersonalData().getMobileNo2()!=null){
			loginForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
		}
		//Get the student Name, Father Name, Mother Name and Class Name. Set all to form
		if(student!=null && student.getAdmAppln()!=null
				&& student.getAdmAppln().getPersonalData()!=null){
			if(student.getAdmAppln().getPersonalData().getFatherName()!=null){
				loginForm.setFatherName(student.getAdmAppln().getPersonalData().getFatherName());
			}
			if(student.getAdmAppln().getPersonalData().getMotherName()!=null){
				loginForm.setMotherName(student.getAdmAppln().getPersonalData().getMotherName());
			}
			String studentName = "";
			String phoneNo = "";
			if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
				studentName = student.getAdmAppln().getPersonalData().getFirstName();
			}
			if(student.getAdmAppln().getPersonalData().getMiddleName()!=null){
				studentName = studentName + " " + student.getAdmAppln().getPersonalData().getMiddleName();
			}
			if(student.getAdmAppln().getPersonalData().getLastName()!=null){
				studentName = studentName + " " + student.getAdmAppln().getPersonalData().getLastName();
			}
			if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null){
				loginForm.setCurrentAddress1(student.getAdmAppln().getPersonalData().getCurrentAddressLine1());
			}
			if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null){
				loginForm.setCurrentAddress2(student.getAdmAppln().getPersonalData().getCurrentAddressLine2());
			}
			if(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null){
				loginForm.setCurrentCity(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId());
			}
			if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null && student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!=null){
				loginForm.setCurrentState(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName());
			}
			if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()==null && student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().isEmpty()){
				loginForm.setCurrentState(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers());
			}
			if(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null){
				loginForm.setCurrentPincode(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode());
			}
			//------------------------permanent Address--------------------------------------------------------------
			if(student.getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null){
				loginForm.setPermanentAddress1(student.getAdmAppln().getPersonalData().getPermanentAddressLine1());
			}
			if(student.getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null){
				loginForm.setPermanentAddress2(student.getAdmAppln().getPersonalData().getPermanentAddressLine2());
			}
			if(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!=null){
				loginForm.setPermanentCity(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
			}
			if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null && student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName()!=null){
				loginForm.setPermanentState(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName());
			}
			if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()==null && student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers().isEmpty()){
				loginForm.setPermanentState(student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers());
			}
			if(student.getAdmAppln().getPersonalData().getPermanentAddressZipCode()!=null){
				loginForm.setPermanentPincode(student.getAdmAppln().getPersonalData().getPermanentAddressZipCode());
			}
			if(student.getAdmAppln().getPersonalData().getNationality()!=null && student.getAdmAppln().getPersonalData().getNationality().getName()!=null){
				loginForm.setNationality(student.getAdmAppln().getPersonalData().getNationality().getName());
			}
			if(student.getAdmAppln().getPersonalData().getBloodGroup()!=null){
				loginForm.setBloodGroup(student.getAdmAppln().getPersonalData().getBloodGroup());
			}
			if(student.getAdmAppln().getPersonalData().getPhNo1()!=null){
				phoneNo = student.getAdmAppln().getPersonalData().getPhNo1();
			}
			if(student.getAdmAppln().getPersonalData().getPhNo2()!=null){
				phoneNo = phoneNo + "-" + student.getAdmAppln().getPersonalData().getPhNo2();
			}
			if(student.getAdmAppln().getPersonalData().getPhNo3()!=null){
				phoneNo = phoneNo + "-" + student.getAdmAppln().getPersonalData().getPhNo3();
			}
			//-----------------------------------------------------------------------------------------------------------
			loginForm.setPhNo1(phoneNo);
			loginForm.setStudentName(studentName);
			session.setAttribute("studentName", studentName);
			loginForm.setStudentId(student.getId());
			loginForm.setCourseId(String.valueOf(student.getAdmAppln().getCourseBySelectedCourseId().getId()));
			loginForm.setYear(Integer.toString(student.getAdmAppln().getAppliedYear()));
			loginForm.setEnteredDob(null);
		}
		if(student!=null && student.getClassSchemewise()!=null 
				&& student.getClassSchemewise().getClasses()!=null && student.getClassSchemewise().getClasses().getName()!=null){
			loginForm.setClassName(student.getClassSchemewise().getClasses().getName());
			loginForm.setClassId(String.valueOf(student.getClassSchemewise().getClasses().getId()));
			loginForm.setTermNo(student.getClassSchemewise().getClasses().getTermNumber());
		}



		if(CMSConstants.LINK_FOR_CJC)
		{ 
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SUCCESS_CJC);
		}else
		{
			return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS);
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward hallTicketClearanceCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		ClearanceCertificateTO cto=DownloadHallTicketHandler.getInstance().getCleareanceCertificateForStudent(loginForm.getBlockId());
		loginForm.setCto(cto);
		return mapping.findForward(CMSConstants.HALLTICKET_CLEARANCE_CERTIFICATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printHallTicketClearanceCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the getHallTicket");
		return mapping.findForward(CMSConstants.HALLTICKET_CLEARANCE_CERTIFICATE_PRINT);
	}

	public ActionForward marksCardClearanceCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		ClearanceCertificateTO cto=DownloadHallTicketHandler.getInstance().getCleareanceCertificateForStudent(loginForm.getBlockId());
		loginForm.setCto(cto);
		return mapping.findForward(CMSConstants.MARKS_CARD_CLEARANCE_CERTIFICATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMarksCardClearanceCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the getHallTicket");
		return mapping.findForward(CMSConstants.MARKS_CARD_CLEARANCE_CERTIFICATE_PRINT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitMobileNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the submitMobileNo");
		LoginForm loginForm = (LoginForm) form;
		setUserId(request, loginForm);
		//boolean isUpdate=false;
		try{
			String mobileNo=loginForm.getMobileNo();
			String userId=loginForm.getUserId();
			int personalId=loginForm.getPersonalDateId();
			//isUpdate=
			StudentLoginHandler.getInstance().saveMobileNo(mobileNo,userId,personalId);
			loginForm=StudentLoginHandler.getInstance().getMobileNo(loginForm);

		}catch (ApplicationException e) {
			log.debug("leaving the studentLoginAction with exception");
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch(Exception e) {
			log.debug("leaving the studentLoginAction with exception");
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			//throw e;
		}

		if(CMSConstants.LINK_FOR_CJC)
		{ 
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SUCCESS_CJC);
		}else
		{
			return mapping.findForward(CMSConstants.STUDENTLOGIN_SUCCESS);
		}

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward help(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		// TODO Auto-generated method stub
		return mapping.findForward(CMSConstants.STUDENTLOGIN_HELP);
	}
	/**
	 * Dispaly's the list of challan's generated through smart card mode for the previous month for the student
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeChallanPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		LoginForm loginForm=(LoginForm)form;
		ActionErrors errors = new ActionErrors();
		StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;
		boolean smartCardMode=StudentLoginHandler.getInstance().getStudentPaymentMode(studentLogin.getStudent().getId(),loginForm);
		if(!smartCardMode){
			errors.add("error",new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.STUDENTLOGIN_PRINTCHALLAN_LIST);
	}

	/**
	 * print's challan for student
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printFeeChallan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

		log.debug("Entering printFeeChallan-studentLogin ");
		LoginForm loginForm = (LoginForm)form;
		try {
			StudentLoginHandler.getInstance().getChallanData(loginForm, request);

			//	 		FeePaymentHandler.getInstance().copyPrintChallenData(feePaymentForm);
		} catch(Exception e) {
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		if(loginForm.getIsSinglePrint()){
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SINGLE_PRINTCHALLAN);
		}
		else
		{
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_PRINTCHALLAN);
		}

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward certificateMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		ConsolidateMarksCardTO to=CertificateMarksCardHandler.getInstance().getStudentCertificateMarksCard(loginForm.getStudentId());
		if(to==null){
			return mapping.findForward(CMSConstants.CERTIFICATE_MARKS_CARD_NOT_AVALIABLE);
		}
		loginForm.setConsolidateMarksCardTO(to);
		return mapping.findForward(CMSConstants.CERTIFICATE_MARKS_CARD);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printCertificateMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		return mapping.findForward(CMSConstants.STUDENTLOGIN_CERTIFICATE_MARKS_CARD_PRINT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("Entered ScoreSheetAction - getCandidates");
		ActionErrors errors = new ActionErrors();
		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		errors = loginForm.validate(mapping, request);
		validateDDdetails(loginForm, errors);
		String forward=getMarksCardForwardName(loginForm.getMarksCardType());
		if (errors.isEmpty()) {
			try {
				//SmartCard Payment Code.....

				loginForm.setPrintCertificateReq(false);
				String msg="";
				boolean isSaved=StudentLoginHandler.getInstance().saveSupplementaryApplicationForStudentLogin(loginForm);
				msg=loginForm.getMsg();
				if(isSaved){
					//handler.sendSMSToStudent(loginForm.getStudentId(),CMSConstants.CERTIFICATE_APPLICATION_SMS_TEMPLATE);
					DownloadHallTicketHandler.getInstance().sendMailToStudent(loginForm);

					String mobileNo="91";
					if(loginForm.getMobileNo()!=null)
						mobileNo=mobileNo+loginForm.getMobileNo();

					if(mobileNo.length()==12){
						UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_REVALUATION_TEMPLATE,null);
					}
					ActionMessage message = new ActionMessage("KnowledgePro.Revaluation.added.successfully");
					messages.add("messages", message);
					saveMessages(request, messages);
					loginForm.clearRevaluation();
					MarksCardTO marksCardTo=null;
					if(loginForm.getBatch()!=null && loginForm.getProgramTypeId()!=null){
						if(loginForm.getBatch().equalsIgnoreCase("2012") && loginForm.getProgramTypeId().equalsIgnoreCase("3"))
							loginForm.setDisplaySem1and2("true");
					}
					if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_UG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForUG(loginForm,request);
					}else if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_PG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
					}else if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_SUP_UG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
					}else if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_SUP_PG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
					}
					loginForm.setMarksCardTo(marksCardTo);
					//messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.certificate.request.success.online"));
					//saveMessages(request, messages);
					//below is code for  Receipt print.......
					String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(loginForm.getOnlinePaymentId(),request);
					loginForm.resetFields();
					loginForm.setPrintData(printData);
					loginForm.setPrintCertificateReq(true);
					setDataToForm(loginForm,request);
				}else{
					if(msg==null || msg.isEmpty()){
						msg="Payment Failed";
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Revaluation/Retotalling Application was not successfull, Reason:"+msg));
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
					saveErrors(request, errors);
				}

				//

				/*boolean isUpdate=DownloadHallTicketHandler.getInstance().saveRevaluationData(loginForm);
				if(isUpdate){
					DownloadHallTicketHandler.getInstance().sendMailToStudent(loginForm);

					String mobileNo="91";
					if(loginForm.getMobileNo()!=null)
						mobileNo=mobileNo+loginForm.getMobileNo();

					if(mobileNo.length()==12){
						UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_REVALUATION_TEMPLATE,null);
					}

					ActionMessage message = new ActionMessage("KnowledgePro.Revaluation.added.successfully");
					messages.add("messages", message);
					saveMessages(request, messages);
					loginForm.clearRevaluation();
					MarksCardTO marksCardTo=null;
					if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_UG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForUG(loginForm,request);
					}else if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_PG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
					}else if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_SUP_UG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
					}else if(forward.equalsIgnoreCase(CMSConstants.STUDENTLOGIN_SUP_PG_MARKS_CARD)){
						marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
					}
					loginForm.setMarksCardTo(marksCardTo);
				}*/
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(exception.getMessage());
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENTLOGIN_REVALUATION_PAGE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");

		return mapping.findForward(forward);
	}

	/**
	 * @param marksCardType
	 * @return
	 */
	private String getMarksCardForwardName(String marksCardType) throws Exception {
		String forwardName="";
		if(marksCardType.equalsIgnoreCase("regUg")){
			forwardName=CMSConstants.STUDENTLOGIN_UG_MARKS_CARD;
		}else if(marksCardType.equalsIgnoreCase("regPg")){
			forwardName=CMSConstants.STUDENTLOGIN_PG_MARKS_CARD;
		}else if(marksCardType.equalsIgnoreCase("supUg")){
			forwardName=CMSConstants.STUDENTLOGIN_SUP_UG_MARKS_CARD;
		}else if(marksCardType.equalsIgnoreCase("supPg")){
			forwardName=CMSConstants.STUDENTLOGIN_SUP_PG_MARKS_CARD;
		}
		return forwardName;
	}

	/**
	 * @param loginForm
	 * @param errors
	 */
	private void validateDDdetails(LoginForm loginForm, ActionErrors errors) throws Exception{
		if (loginForm.getDdDate()!=null && !StringUtils.isEmpty(loginForm.getDdDate())) {
			if(CommonUtil.isValidDate(loginForm.getDdDate())){
				boolean	isValid = AdmissionFormAction.validatefutureDate(loginForm.getDdDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
				}
			}
		}
		if(errors.isEmpty()){
			boolean isValid=DownloadHallTicketHandler.getInstance().checkValidDDdetails(loginForm);
			if(!isValid)
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","DD Details Already Exists"));
		}

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitRevaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("Entered ScoreSheetAction - getCandidates");

		LoginForm loginForm = (LoginForm) form;
		ActionErrors errors = new ActionErrors();
		if (errors.isEmpty()) {
			try {
				List<SubjectTO> mainList=new ArrayList<SubjectTO>();
				MarksCardTO to=loginForm.getMarksCardTo();
				Map<String,List<SubjectTO>> subMap=to.getSubMap();
				if(subMap!=null && !subMap.isEmpty()){
					for (Map.Entry<String,List<SubjectTO>> entry : subMap.entrySet()) {
						List<SubjectTO> subList=entry.getValue();
						mainList.addAll(subList);
					}
				}

				Map<String,List<SubjectTO>> addOnCoursesubMap=to.getAddOnCoursesubMap();
				if(addOnCoursesubMap!=null && !addOnCoursesubMap.isEmpty()){
					for (Map.Entry<String,List<SubjectTO>> entry : addOnCoursesubMap.entrySet()) {
						List<SubjectTO> subList=entry.getValue();
						mainList.addAll(subList);
					}
				}
				Map<String,List<SubjectTO>> nonElectivesubMap=to.getNonElectivesubMap();
				if(nonElectivesubMap!=null && !nonElectivesubMap.isEmpty()){
					for (Map.Entry<String,List<SubjectTO>> entry : nonElectivesubMap.entrySet()) {
						List<SubjectTO> subList=entry.getValue();
						mainList.addAll(subList);
					}
				}
				to.setMainList(mainList);
				loginForm.setMarksCardTo(to);
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(exception.getMessage());
			}
		} else {
			addErrors(request, errors);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENTLOGIN_REVALUATION_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backToMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		String forward=getMarksCardForwardName(loginForm.getMarksCardType());
		return mapping.findForward(forward);
	}	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOnlineRecieptsForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the initOnlineRecieptsForStudentLogin");
		LoginForm loginForm= (LoginForm) form;
		List<OnlinePaymentRecieptsTo> paymentList=StudentLoginHandler.getInstance().getOnlinePaymentReciepts(loginForm.getStudentId(),request);
		Collections.sort(paymentList);
		loginForm.setPaymentList(paymentList);
		log.debug("Exit  from initOnlineRecieptsForStudentLogin");
		return mapping.findForward(CMSConstants.INIT_ONLINE_RECIEPTS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printOnlineRecieptsForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the initOnlineRecieptsForStudentLogin");
		LoginForm loginForm= (LoginForm) form;
		List<OnlinePaymentRecieptsTo> paymentList=loginForm.getPaymentList();
		for (OnlinePaymentRecieptsTo to : paymentList) {
			if(to.getCount()==loginForm.getCount()){
				request.setAttribute("MSG",to.getMsg());
			}
		}
		log.debug("Exit  from initOnlineRecieptsForStudentLogin");
		return mapping.findForward(CMSConstants.PRINT_ONLINE_RECIEPTS);
	}

	public ActionForward initMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the initOnlineRecieptsForStudentLogin");
		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = loginForm.validate(mapping, request);
		cleanUpPageData(loginForm);
		if(request.getSession().getAttribute("marksCardBlockReason")!=null){
			request.getSession().removeAttribute("marksCardBlockReason");
		}
		//session.setAttribute("regMarksCard", regMarksCard);
		if(request.getSession().getAttribute("regMarksCard")!=null){
			request.getSession().removeAttribute("regMarksCard");
		}
		if(request.getSession().getAttribute("supMarksCard")!=null){
			request.getSession().removeAttribute("supMarksCard");
		}

		if(request.getSession().getAttribute("marksCardAgreement")!=null){
			request.getSession().removeAttribute("marksCardAgreement");
		}
		if (errors.isEmpty()) {
			try {
				StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;

				if (studentLogin == null) {
					message = new ActionMessage("knowledgepro.admin.validusername");
					messages.add("messages", message);
					addErrors(request, messages);
					loginForm.resetFields();
					if(CMSConstants.LINK_FOR_CJC)
					{ 
						return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
					}else
					{
						return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
					}
				} else {		
					@SuppressWarnings("unused")
					List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
					// no need to excute this code if student is detained
					//if(!listOfDetainedStudents.contains(loginForm.getStudentId())){
					loginForm.setExamType("Regular");
					List<MarksCardTO> SemExamListTo=(DownloadHallTicketHandler.getInstance().getExamSemList(loginForm));
					if(SemExamListTo!=null)	
					{
						Collections.sort(SemExamListTo,new MarkComparator());
						loginForm.setRegularExamList(SemExamListTo);
					}
					//}
					//Sup Marks Card
					List<MarksCardTO> SupSemList=(DownloadHallTicketHandler.getInstance().getSupExamSemList(loginForm));
					if(SupSemList!= null)
						Collections.sort(SupSemList,new MarkComparator());
					loginForm.setSuppExamList(SupSemList);
					loginForm.setIsGrievance(false);
					log.debug("Exit  from initOnlineRecieptsForStudentLogin");
					return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
				}	
			} catch (ApplicationException e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch(Exception e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				//throw e;
			}
		} else {
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			if(CMSConstants.LINK_FOR_CJC)
			{ 
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
			}else
			{
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			}
		}
	}


	public ActionForward MarksCardDisplay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginForm loginForm = (LoginForm) form;
		ActionErrors errors = loginForm.validate(mapping, request);
		HttpSession session = request.getSession(true);
		session.setAttribute("USERNAME", loginForm.getUserName());
		if (errors.isEmpty()) {
			try {
				LoginHandler.getInstance().isValiedStudentUser(loginForm) ;
				String studentid = String.valueOf(loginForm.getStudentId());
				loginForm.setRevaluationRegClassId(null);
				int examId = 0;
				//	int classId = DownloadHallTicketHandler.getInstance().getClassId(Integer.parseInt(studentid), loginForm);
				List<ShowMarksCardTO> regMarksCard=new ArrayList<ShowMarksCardTO>();
				List<MarksCardTO> regularList = loginForm.getRegularExamList();
				examId = Integer.parseInt(loginForm.getRegularExamId().substring(0, loginForm.getRegularExamId().indexOf('_')));
				if (regularList != null) {
					Iterator<MarksCardTO> iterator = regularList.iterator();
					while (iterator.hasNext()) {
						MarksCardTO mkTo = iterator.next();
						ShowMarksCardTO to=new ShowMarksCardTO();
						if (mkTo != null) {
							if(mkTo.getNewExamId().equalsIgnoreCase(loginForm.getRegularExamId())){

								Integer semesterYearNo = Integer.parseInt(mkTo.getSemNo());
								Integer classId = Integer.parseInt(mkTo.getClassId());
								session.setAttribute("CLASSID", classId);
								to.setRegMCsemesterYearNo(semesterYearNo);
								to.setRegMCexamID(examId);
								to.setRegMCClassId(classId);
								loginForm.setExamIDForMCard(examId);
								loginForm.setMarksCardClassId(classId);
								/*if(loginForm.getBatch()!=null && loginForm.getProgramTypeId()!=null){
						if(loginForm.getBatch().equalsIgnoreCase("2012") && loginForm.getProgramTypeId().equalsIgnoreCase("3"))
							loginForm.setDisplaySem1and2("true");
					}*/

								loginForm.setSemesterYearNo(semesterYearNo);
								boolean isBlockedStudent = false;
								ExamFooterAgreementBO agreementBO = null;
								if(classId > 0){
									examId = DownloadHallTicketHandler.getInstance().getExamIdByClassId(classId, loginForm, "Marks Card");
								}
								ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO= null;
								examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentid), classId, examId, "M");  
								isBlockedStudent = false;
								if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
									isBlockedStudent = true;
									to.setRegMCBlockedStudent(true);
									to.setRegMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
									session.setAttribute("marksCardBlockReason", examBlockUnblockHallTicketBO.getBlockReason());
									session.setAttribute("marksCardBlockId", examBlockUnblockHallTicketBO.getId());
									/*	errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.STUDENTLOGIN_MARKSCARD_BLOCK_ERROR));
						saveErrors(request, errors);*/
									return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_BLOCK_MES);
								}
								else 
								{  //Change By manu,1st check condition
									if(loginForm.getAgreementId()>0){
										agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
									}
									if(agreementBO!= null && agreementBO.getDescription()!= null){
										session.setAttribute("marksCardAgreement", agreementBO.getDescription());

										if(!loginForm.isAgreementAccepted())
										{
											loginForm.setAgreementAccepted(true);
											return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_AGREEMENT_NEW);
										}
									}
									else{
										session.setAttribute("marksCardAgreement", null);
									}
									if((agreementBO!=null && loginForm.isAgreementAccepted())|| (agreementBO==null && !loginForm.isAgreementAccepted()))
									{
										to.setRegMCBlockedStudent(false);
										boolean isExcluded = DownloadHallTicketHandler.getInstance().getIsExcluded(Integer.parseInt(studentid), examId);
										//isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(classId, examId, "Marks Card", false);
										//boolean isAppeared = DownloadHallTicketHandler.getInstance().isAppeared(Integer.parseInt(studentid), examId,classId);
										//if(classId > 0 && examId > 0 && isDateValid && !isExcluded){
										if(classId > 0 && examId > 0 && !isExcluded){
											MarksCardTO marksCardTo=null;
											boolean isUg=false;
											if(request.getSession().getAttribute("birtUg")!=null)
												isUg=(Boolean)request.getSession().getAttribute("birtUg");
											if(isUg){
												marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForUG(loginForm,request);
											}else{
												marksCardTo=DownloadHallTicketHandler.getInstance().getMarksCardForPG(loginForm,request);
											}
											to.setShowRegMC(true);
											loginForm.setMarksCardTo(marksCardTo);
											if(loginForm.getCourseId()!=null && !loginForm.getCourseId().equalsIgnoreCase("18")){
												IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
												List<ExamFooterAgreementBO> footer=transaction.getFooterDetails(loginForm.getProgramTypeId(),"M", loginForm.getClassId());
												byte[] doc = transaction.getDocument(loginForm.getStudentId());
												if(doc!=null){
													session.setAttribute("PhotoBytes",doc);
												}
												if(footer!=null && !footer.isEmpty()){
													ExamFooterAgreementBO obj=footer.get(0);
													if(obj.getDescription()!=null)
														loginForm.setDescription1(obj.getDescription());
												}else{
													loginForm.setDescription1(null);
												}
											}else{
												loginForm.setDescription1(CMSConstants.MARKS_CARD_DESCRIPTION);
											}
											if(isUg){
												return mapping.findForward(CMSConstants.STUDENTLOGIN_UG_MARKS_CARD);
											}else{
												return mapping.findForward(CMSConstants.STUDENTLOGIN_PG_MARKS_CARD);
											}
										}else{
											to.setShowRegMC(false);
											errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
											saveErrors(request, errors);
											return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
										}
									}
								}
								/*if(isBlockedStudent){
							to.setRegMCBlockedStudent(true);
							to.setRegMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
							session.setAttribute("marksCardBlockReason", examBlockUnblockHallTicketBO.getBlockReason());
							session.setAttribute("marksCardBlockId", examBlockUnblockHallTicketBO.getId());
						}
						else{

						}	*/

								if(to.isShowRegMC()){
									regMarksCard.add(to);
								}else if(!to.isShowRegMC() && isBlockedStudent){
									regMarksCard.add(to);
								}
							}

							session.setAttribute("regMarksCard", regMarksCard);
						}
					}
				}

				return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);	
			}catch (ApplicationException e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch(Exception e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				//throw e;
			}
		}else {
			log.debug("leaving the studentLoginAction with errors");
			cleanUpPageData(loginForm);
			//addErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
		}


	}

	public ActionForward SupplementaryMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		LoginForm loginForm = (LoginForm) form;
		ActionErrors errors = loginForm.validate(mapping, request);
		HttpSession session = request.getSession(true);
		session.setAttribute("USERNAME", loginForm.getUserName());
		if (errors.isEmpty()) {
			try {

				//StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;
				String studentId = String.valueOf(loginForm.getStudentId());
				loginForm.setRevaluationSupClassId(null);
				List<ShowMarksCardTO> supMarksCard=new ArrayList<ShowMarksCardTO>();
				int examId = 0;
				List<MarksCardTO> supList = loginForm.getSuppExamList();
				String suppExamId =  loginForm.getSuppExamId();

				examId = Integer.parseInt(loginForm.getSuppExamId().substring(0, suppExamId.indexOf("-")));
				if (supList != null) {
					Iterator<MarksCardTO> iterator = supList.iterator();
					while (iterator.hasNext()) {
						MarksCardTO mkTo = iterator.next();
						ShowMarksCardTO to=new ShowMarksCardTO();
						if (mkTo != null) {
							if(Integer.parseInt(mkTo.getExamId())==examId){
								if(mkTo.getNewExamId().equalsIgnoreCase(loginForm.getSuppExamId())){
									Integer semesterYearNo = Integer.parseInt(mkTo.getSemNo());
									Integer classId = Integer.parseInt(mkTo.getClassId());
									to.setSupMCsemesterYearNo(semesterYearNo);
									to.setSupMCexamID(examId);
									to.setSupMCClassId(classId);
									loginForm.setSupMCexamID(examId);
									loginForm.setSupMCClassId(classId);
									loginForm.setSupMCsemesterYearNo(semesterYearNo);
									boolean isBlockedStudent = false;
									if(classId > 0){
										examId = DownloadHallTicketHandler.getInstance().getExamIdByClassIdForSupMarksCard(classId, loginForm, "Marks Card");
									}
									ExamFooterAgreementBO agreementBO = null;
									ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO= null;
									examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentId), classId, examId, "M");  
									isBlockedStudent = false;
									if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
										isBlockedStudent = true;
										to.setSupMCBlockedStudent(true);
										to.setSupMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
										return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_BLOCK_MES);
									}
									else 
									{   

										//Change By manu,1st check condition
										if(loginForm.getAgreementId()>0){
											agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
										}
										if(agreementBO!= null && agreementBO.getDescription()!= null){
											session.setAttribute("marksCardAgreement", agreementBO.getDescription());
											if(!loginForm.isAgreementAccepted())
											{
												loginForm.setAgreementAccepted(true);
												return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_AGREEMENT_SUPP);
											}
										}
										else{
											session.setAttribute("marksCardAgreement", null);
										}
										if((agreementBO!=null && loginForm.isAgreementAccepted())|| (agreementBO==null && !loginForm.isAgreementAccepted()))
										{
											//isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(classId, examId, "Marks Card", true);
											boolean isAppeared = DownloadHallTicketHandler.getInstance().isAppeared(Integer.parseInt(studentId), examId,classId,"marksCard");
											//if(classId > 0 && examId > 0 && isDateValid && isAppeared){
											if(classId > 0 && examId > 0 && isAppeared){
												loginForm.setExamId(examId);//azr
												to.setShowSupMC(true);
												MarksCardTO marksCardTo=null;
												boolean isUg=false;
												if(request.getSession().getAttribute("birtUg")!=null)
													isUg=(Boolean)request.getSession().getAttribute("birtUg");
												if(isUg){
													marksCardTo=DownloadHallTicketHandler.getInstance().getSupMarksCardForUG(loginForm,request);
												}else{
													marksCardTo=DownloadHallTicketHandler.getInstance().getSupMarksCardForPG(loginForm,request);
												}
												loginForm.setMarksCardTo(marksCardTo);
												IDownloadHallTicketTransaction transaction= new DownloadHallTicketTransactionImpl();
												List<ExamFooterAgreementBO> footer=transaction.getFooterDetails(loginForm.getProgramTypeId(),"M", loginForm.getClassId());
												if(footer!=null && !footer.isEmpty()){
													ExamFooterAgreementBO obj=footer.get(0);
													if(obj.getDescription()!=null)
														loginForm.setDescription1(obj.getDescription());
												}else{
													loginForm.setDescription1(null);
												}
												if(isUg){
													return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_UG_MARKS_CARD);
												}else{
													return mapping.findForward(CMSConstants.STUDENTLOGIN_SUP_PG_MARKS_CARD);
												}
											}else{
												to.setShowSupMC(false);
												errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
												saveErrors(request, errors);
												return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
											}
										}
									}
									if(to.isShowSupMC()){
										supMarksCard.add(to);
									}
									else if(!to.isShowSupMC() && isBlockedStudent){
										supMarksCard.add(to);
									}
								}
							}
							session.setAttribute("supMarksCard", supMarksCard);
						}

					}
				}
				cleanUpPageData(loginForm);
				log.debug("Exit  from initOnlineRecieptsForStudentLogin");
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);

			}catch (ApplicationException e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch(Exception e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				//throw e;
			}
		}else {
			log.debug("leaving the studentLoginAction with errors");
			cleanUpPageData(loginForm);
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
		}
	}
	public ActionForward studentLoginMarksCardAgreementSupp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_AGREEMENT_SUPP);
	}

	public ActionForward studentLoginMarksCardAgreementNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return mapping.findForward(CMSConstants.STUDENTLOGIN_MARKS_CARD_AGREEMENT_NEW);
	}

	private void cleanUpPageData(LoginForm loginform) {
		loginform.setRegularExamId(null);
		loginform.setSuppExamId(null);
		loginform.setExamIDForMCard(0);
		loginform.setSupMCexamID(0);
		loginform.setSupMCClassId(0);
		loginform.setExamType("Regular");
		loginform.setAgreementAccepted(false);
		loginform.setAgreementId(0);
		loginform.setRevaluationRegClassId(null);
		loginform.setRevaluationSupClassId(null);
		loginform.setSmartCardNo(null);
		loginform.setAmount(null);
		loginform.setEnteredDob(null);
		//loginform.setDateOfBirth(null);
	}

	@SuppressWarnings("unchecked")
	public  void setDataToForm(LoginForm loginform, HttpServletRequest request)throws Exception {
		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
		int finId = cashCollectionTransaction.getCurrentFinancialYear();
		if(finId<=0){
			loginform.setFeesNotConfigured(true);
		}else{
			String query="from OnlineBillNumber o where o.pcFinancialYear.id = "+finId+" and o.isActive = 1";
			INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
			List list1=txn.getDataForQuery(query);
			if(list1==null || list1.isEmpty())
				loginform.setFeesNotConfigured(true);
			loginform.setFinId(finId);

		}
	}


	public ActionForward calculateAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered calculateAmount ------------------");
		LoginForm loginform = (LoginForm)form;
		ActionErrors errors = loginform.validate(mapping, request);
		setUserId(request,loginform);
		if (errors.isEmpty()) {
			double amount=0;
			try {	
				if(loginform.getAmount()!=null && !loginform.getAmount().isEmpty()){
					amount = Double.parseDouble(loginform.getAmount());
				}
				if(amount==0){

					if (errors.get(CMSConstants.KNOWLEDGEPRO_REVALUATION_NORECORD_SELECTED) != null
							&& !errors.get(CMSConstants.KNOWLEDGEPRO_REVALUATION_NORECORD_SELECTED)
							.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_REVALUATION_NORECORD_SELECTED);
						errors.add(CMSConstants.KNOWLEDGEPRO_REVALUATION_NORECORD_SELECTED, error);
					}
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.STUDENTLOGIN_REVALUATION_PAGE);
				}
				loginform.setTotalFees(amount);	
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				loginform.setErrorMessage(msg);
				loginform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit calculateAmount--------------------------------------- ");
			return mapping.findForward(CMSConstants.STUDENTLOGIN_REVALUATION_PAGE);
		}
		log.info("Entered calculateAmount---------------------------------------");
		return mapping.findForward(CMSConstants.REVALUATION_STUDENT_VEIRFY_SMART);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyStudentSmartCardForStudentLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginForm loginform = (LoginForm)form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		setUserId(request,loginform);//setting the userId to Form
		try {
			boolean isValidSmartCard=StudentLoginHandler.getInstance().verifySmartCard(loginform.getSmartCardNo(),loginform.getStudentId());
			if(!isValidSmartCard){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
			}
			if(loginform.getEnteredDob()!=null){
				if(!loginform.getEnteredDob().equalsIgnoreCase(loginform.getDob()))
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter Valid Date Of Birth"));
			}

			if(!errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.REVALUATION_STUDENT_VEIRFY_SMART);
			}

		}catch (ReActivateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.REVALUATION_STUDENT_VEIRFY_SMART);
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			loginform.setErrorMessage(msg);
			loginform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}

		return mapping.findForward(CMSConstants.REVALUATION_STUDENT_VEIRFY_SMART_1);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initConvocationRegistration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		LoginForm loginForm=(LoginForm) form;
		loginForm.reset1();
		loadConvocationRegistration(loginForm,request);
		if(loginForm.getConvocationId()!=0)
		{
			if(loginForm.getGuestPassRequired()!=null && loginForm.getGuestPassRequired()==true)
			{
				loginForm.setConvocationRelation(true);
			}
		}
		else{
			loginForm.setConvocationRelation(false);
		}
		return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveConvocationRegistration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		LoginForm loginForm=(LoginForm) form;
		ActionMessages messages=new ActionMessages();
		HttpSession session=request.getSession();
		setUserId(request, loginForm);
		ActionErrors errors=loginForm.validate(mapping, request);
		boolean isAdded=false;
		if(errors.isEmpty())
		{
			if(loginForm.getGuestPassRequired()==true)
			{
				if(loginForm.getRelationshipWithGuest().isEmpty())
				{
					errors.add("error", new ActionError("knowledgepro.convocation.guestpass.relationship.required"));
					saveErrors(request, errors);
					if(loginForm.getGuestPassRequired()!=null && loginForm.getGuestPassRequired()==true)
					{
						loginForm.setConvocationRelation(true);
					}
					else
					{
						loginForm.setConvocationRelation(false);
					}
					return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
				}
			}
			try {
				isAdded=LoginHandler.getInstance().saveConvocationRegistration(loginForm,session);
				if(isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.convocation.registration.success.message");// Adding success message.
					messages.add("messages", message);
					saveMessages(request, messages);
					if(loginForm.getGuestPassRequired()!=null && loginForm.getGuestPassRequired()==true)
					{
						loginForm.setConvocationRelation(true);
					}
					else
					{
						loginForm.setConvocationRelation(false);
					}
					return mapping.findForward(CMSConstants.SUCCESS_CONVOCATION_REGISTRATION);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.convocation.registration.failure.message");// Adding failure message.
					messages.add("messages", message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
				}
			} catch (Exception e) {
				log.error("Error in getCertificateCourses"+e.getMessage());
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		}
		else
		{
			addErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
	}

	/**
	 * @param loginForm
	 * @param request
	 * @throws Exception
	 */
	public void loadConvocationRegistration(LoginForm loginForm,HttpServletRequest request) throws Exception
	{
		HttpSession session=request.getSession();
		LoginHandler.getInstance().loadConvocationRegistration(loginForm,session);
	}

	public ActionForward initSapRegistration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		LoginForm loginForm=(LoginForm) form;
		boolean exist=false;
		setUserId(request, loginForm);
		loginForm.setSapRegExist(false);
		try {
			exist=LoginHandler.getInstance().SapRegistrationLoad(loginForm);
			if(exist)
			{ 
				loginForm.setSapRegExist(true);
				return mapping.findForward(CMSConstants.INIT_SAP_REGISTRATION);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.INIT_SAP_REGISTRATION);

	}
	public ActionForward saveSapRegistration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		LoginForm loginForm=(LoginForm) form;
		ActionMessages messages=new ActionMessages();
		HttpSession session=request.getSession();
		setUserId(request, loginForm);
		ActionErrors errors=loginForm.validate(mapping, request);
		loginForm.setSapRegExist(false);
		boolean isAdded=false;
		boolean exist=false;
		try
		{
			if(errors.isEmpty())
			{
				exist=LoginHandler.getInstance().SapRegistrationLoad(loginForm);
				if(exist)
				{ 
					loginForm.setSapRegExist(true);
					return mapping.findForward(CMSConstants.INIT_SAP_REGISTRATION);
				}else
				{
					isAdded=LoginHandler.getInstance().saveSapRegistration(loginForm,session);
					if(isAdded)
					{
						LoginHandler.getInstance().sendMailToAdmin(loginForm);
						LoginHandler.getInstance().sendSMSToStudent(loginForm);
						ActionMessage message = new ActionMessage("knowledgepro.sap.registration.success.message");// Adding success message.
						messages.add("messages", message);
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.INIT_SAP_REGISTRATION);
					}else{
						ActionMessage message = new ActionMessage("knowledgepro.sap.registration.failure.message");// Adding failure message.
						messages.add("messages", message);
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.INIT_SAP_REGISTRATION);
					}
				}
			}
			else
			{
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}

		return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
	}
	public ActionForward StudentDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		/*@SuppressWarnings("unused")
		LoginForm loginForm=(LoginForm) form;
		 */		
		return mapping.findForward(CMSConstants.STUDENT_DETAILS_DISPLAY);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward displaySAPpResuls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		LoginForm loginForm=(LoginForm) form;
		loginForm.setStatus(null);
		HttpSession session = request.getSession(true);
		loginForm.setSapRegExist(false);
		loginForm.setStatusIsPass(false);
		loginForm.setStatusIsFail(false);
		loginForm.setStatusIsIsOther(false);
		try {
			String studentid = (String) session.getAttribute("studentid");
			UploadSAPMarksBo bo=StudentLoginHandler.getInstance().getSAPExamResuls(studentid);
			if(bo.getStatus()!=null && !bo.getStatus().isEmpty()){
				if(bo.getStatus().equalsIgnoreCase("PASS")){
					loginForm.setStatusIsPass(true);
				}else if(bo.getStatus().equalsIgnoreCase("Fail")){
					loginForm.setStatusIsFail(true);
				}else{
					loginForm.setStatusIsIsOther(true);
				}
				loginForm.setStatus((bo.getStatus()).toUpperCase());
				loginForm.setSapExamDate(CommonUtil.formatSqlDate(bo.getDate().toString()));
				request.setAttribute("status", bo.getStatus());
			}
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_SAP_EXAM_RESULS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward certificateCourseStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		try {
			List<StudentMarkDetailsTO> list=StudentCertificateCourseHandler.getInstance().getCompletedCourseCount111(loginForm.getStudentId(),Integer.parseInt(loginForm.getCourseId()),request,loginForm.getTermNo());
			loginForm.setStudentMarkDetailsTOList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.CERTIFICATE_COURSE_STATUS);
	}

	// vinodha
	public ActionForward initInternalMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		HttpSession session = request.getSession();		
		//loginForm.clear();
		//loginForm.reset2();
		String studentid = (String) session.getAttribute("studentid");
		loginForm.setStudentId(Integer.parseInt(studentid));
		Map<Integer, Integer> schemeMap = new HashMap<Integer, Integer>();
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		schemeMap.put(1, 1);
		schemeMap.put(2, 2);
		schemeMap.put(3, 3);
		schemeMap.put(4, 4);
		if(loginForm.getProgramTypeId().equalsIgnoreCase("1"))
		{
			schemeMap.put(5, 5);
			schemeMap.put(6, 6);
		}
		//         schemeMap.put(7, 7);
		//         schemeMap.put(8, 8);
		//         schemeMap.put(9, 9);
		//         schemeMap.put(10, 10);       
		loginForm.setSchemeMap(schemeMap);
		try{
			//subjectMap = StudentLoginHandler.getInstance().getSubjectsListForStudent(loginForm);
			//loginForm.setSubjectMap(subjectMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK); 
	}	

	// vinodha
	public ActionForward getSubjectsListForStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		HttpSession session = request.getSession();
		loginForm.setSubjectMap(null);
		String studentid = (String) session.getAttribute("studentid");
		int admApplnId = (Integer) session.getAttribute("admApplnId");
		loginForm.setStudentId(Integer.parseInt(studentid));
		loginForm.setApplnNo(String.valueOf(admApplnId));
		Map<Integer, Integer> schemeMap = new HashMap<Integer, Integer>();
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		schemeMap.put(1, 1);
		schemeMap.put(2, 2);
		schemeMap.put(3, 3);
		schemeMap.put(4, 4);
		if(loginForm.getProgramTypeId().equalsIgnoreCase("1"))
		{
			schemeMap.put(5, 5);
			schemeMap.put(6, 6);      	 
		}
		//schemeMap.put(7, 7);
		// schemeMap.put(8, 8);
		// schemeMap.put(9, 9);
		//schemeMap.put(10, 10);       
		loginForm.setSchemeMap(schemeMap);
		try{
			subjectMap = StudentLoginHandler.getInstance().getSubjectsListForStudent(loginForm);
			loginForm.setSubjectMap(subjectMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK); 
	}	
	public ActionForward displayInternalMarkForStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		if(errors.isEmpty()){
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			loginForm.setStudentId(Integer.parseInt(studentid));
			loginForm.setCourseId(courseId);
			MarksCardTO to = StudentLoginHandler.getInstance().getStudentInternalMarksStudentLogin(loginForm);
			if(to!=null){
				loginForm.setMarksCardTo(to);				
			}else{
				loginForm.setMarksCardTo(to);
				errors.add("error",new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK_RESULT);
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK);
		}		
	}
	public ActionForward initGrievance(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the initOnlineRecieptsForStudentLogin");
		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = loginForm.validate(mapping, request);
		cleanUpPageData(loginForm);
		if(request.getSession().getAttribute("marksCardBlockReason")!=null){
			request.getSession().removeAttribute("marksCardBlockReason");
		}
		//session.setAttribute("regMarksCard", regMarksCard);
		if(request.getSession().getAttribute("regMarksCard")!=null){
			request.getSession().removeAttribute("regMarksCard");
		}
		if(request.getSession().getAttribute("supMarksCard")!=null){
			request.getSession().removeAttribute("supMarksCard");
		}

		if(request.getSession().getAttribute("marksCardAgreement")!=null){
			request.getSession().removeAttribute("marksCardAgreement");
		}
		if (errors.isEmpty()) {
			try {
				loginForm.setIsGrievance(true);
				StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;

				if (studentLogin == null) {
					message = new ActionMessage("knowledgepro.admin.validusername");
					messages.add("messages", message);
					addErrors(request, messages);
					loginForm.resetFields();
					if(CMSConstants.LINK_FOR_CJC)
					{ 
						return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
					}else
					{
						return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
					}
				} else {		
					@SuppressWarnings("unused")
					List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
					// no need to excute this code if student is detained
					//if(!listOfDetainedStudents.contains(loginForm.getStudentId())){
					loginForm.setExamType("Regular");
					List<MarksCardTO> SemExamListTo=(DownloadHallTicketHandler.getInstance().getExamSemListForGrievance(loginForm));
					if(SemExamListTo!=null)	
					{
						Collections.sort(SemExamListTo,new MarkComparator());
						loginForm.setRegularExamList(SemExamListTo);
					}
					//}
					//Sup Marks Card
					List<MarksCardTO> SupSemList=(DownloadHallTicketHandler.getInstance().getSupExamSemListForGrievance(loginForm));
					if(SupSemList!= null)
						Collections.sort(SupSemList,new MarkComparator());
					loginForm.setSuppExamList(SupSemList);

					log.debug("Exit  from initOnlineRecieptsForStudentLogin");
					return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
				}	
			} catch (ApplicationException e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch(Exception e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				//throw e;
			}
		} else {
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			if(CMSConstants.LINK_FOR_CJC)
			{ 
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
			}else
			{
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			}
		}
	}
	public ActionForward submitGrievanceForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("Entered ScoreSheetAction - getCandidates");

		LoginForm loginForm = (LoginForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(true);
		if (errors.isEmpty()) {
			try {String semNo ="";
			String eid = "";
			int examId=0;
			if(!loginForm.getExamType().equalsIgnoreCase("Supplementary")){
				examId = Integer.parseInt(loginForm.getRegularExamId().substring(0, loginForm.getRegularExamId().indexOf('_')));
				String str = loginForm.getRegularExamId();
				String[] a= str.split("_");
				eid = a[0].toString();
				semNo = a[1].toString();
				loginForm.setSchemeNo(semNo);
			}else{
				examId = Integer.parseInt(loginForm.getSuppExamId().substring(0, loginForm.getSuppExamId().indexOf('-')));	
				String str = loginForm.getSuppExamId();
				String[] a= str.split("-");
				eid = a[0].toString();
				semNo = a[1].toString();
				loginForm.setSchemeNo(semNo);
			}
			List<GrievanceRemarkBo> grievanceRemarkBoList = new ArrayList<GrievanceRemarkBo>();
			GrievanceRemarkBo grievanceRemarkBo; 
			List<SubjectTO> StudentTOList1=loginForm.getMarksCardTo().getSubjectTOList();
			Iterator<SubjectTO> itr = StudentTOList1.iterator();
			Integer curentNo=0;
			GrievanceNumber grievanceNumber = StudentLoginHandler.getInstance().getGrievancePrifixByYear(loginForm.getYear());
			if(grievanceNumber!=null){
				curentNo = grievanceNumber.getCurrentNo();
			}
			int studentId=0;
			while(itr.hasNext()){
				SubjectTO subjectTO = itr.next();
				GrievanceRemarkBo dupgrievanceRemarkBo = StudentLoginHandler.getInstance().checkDuplicates(examId,loginForm.getStudentId(),subjectTO.getId());
				if(dupgrievanceRemarkBo!=null && (subjectTO.getGrievanceRemark()!=null && !subjectTO.getGrievanceRemark().trim().equalsIgnoreCase(""))){
					errors.add("error", new ActionError("knowledgepro.exam.grievanceremark.already.exists",dupgrievanceRemarkBo.getSubject().getName()));
					saveErrors(request, errors);
				}
				if(dupgrievanceRemarkBo==null && errors.isEmpty() && (subjectTO.getGrievanceRemark()!=null && !subjectTO.getGrievanceRemark().trim().equalsIgnoreCase(""))){
					if(subjectTO.getGrievanceRemark()!=null && !subjectTO.getGrievanceRemark().trim().equalsIgnoreCase("")){
						Student student = new Student();
						Subject subject = new Subject();
						Classes classes = new Classes();
						grievanceRemarkBo = new GrievanceRemarkBo();
						ExamDefinition examDefinition = new ExamDefinition();

						if(subjectTO.getCiaMarksAwarded()!=null){
							grievanceRemarkBo.setCiaAwardedMarks(subjectTO.getCiaMarksAwarded());
						}else if(subjectTO.getPracticalCiaMarksAwarded()!=null){
							grievanceRemarkBo.setCiaAwardedMarks(subjectTO.getPracticalCiaMarksAwarded());
						}
						if(subjectTO.getEseMarksAwarded()!=null){
							grievanceRemarkBo.setEseAwardedMarks(subjectTO.getEseMarksAwarded());
						}else if(subjectTO.getPracticalEseMarksAwarded()!=null){
							grievanceRemarkBo.setEseAwardedMarks(subjectTO.getPracticalEseMarksAwarded());
						}
						if(subjectTO.getTotalMarksAwarded()!=null){
							grievanceRemarkBo.setTotalAwardedMarks(subjectTO.getTotalMarksAwarded());	
						}else if(subjectTO.getPracticalTotalMarksAwarded()!=null){
							grievanceRemarkBo.setTotalAwardedMarks(subjectTO.getPracticalTotalMarksAwarded());	
						}


						grievanceRemarkBo.setComment(subjectTO.getGrievanceRemark());
						grievanceRemarkBo.setSemNo(semNo);

						student.setId(loginForm.getStudentId());
						subject.setId(subjectTO.getId());
						examDefinition.setId(examId);
						Integer clsid = (Integer) session.getAttribute("CLASSID");
						classes.setId(clsid);
						grievanceRemarkBo.setSubject(subject);
						grievanceRemarkBo.setExamDefinition(examDefinition);
						grievanceRemarkBo.setClasses(classes);
						grievanceRemarkBo.setStudent(student);
						Integer empId = StudentLoginHandler.getInstance().getHodIdBySubjectId(subjectTO.getId());
						if(empId!=null){
							grievanceRemarkBo.setHodId(String.valueOf(empId));
						}
						grievanceRemarkBo.setIsHodApproved(false);
						grievanceRemarkBo.setIsHodPending(true);
						grievanceRemarkBo.setIsHodRejected(false);
						grievanceRemarkBo.setIsControlerApproved(false);
						grievanceRemarkBo.setIsControlerPending(true);
						grievanceRemarkBo.setIsControlerRejected(false);
						grievanceRemarkBo.setCreatedDate(new Date());
						grievanceRemarkBo.setLastModifiedDate(new Date());
						GrievanceRemarkBo grievanceRemarkBO = StudentLoginHandler.getInstance().getGrievanceNoByStuentId(loginForm.getStudentId());

						if(grievanceRemarkBO!=null && (grievanceRemarkBo.getStudent().getId() == loginForm.getStudentId())){

							grievanceRemarkBo.setGrievanceSerialNo(grievanceRemarkBO.getGrievanceSerialNo());
							studentId = grievanceRemarkBO.getStudent().getId();
						}else{
							if(grievanceNumber!=null)
								grievanceRemarkBo.setGrievanceSerialNo(grievanceNumber.getPrefix()+String.valueOf(curentNo));
						}

						grievanceRemarkBoList.add(grievanceRemarkBo);
					}
				}
			}
			boolean isSaved=false;
			boolean isUpdated=false;
			if(grievanceRemarkBoList.size()!=0){
				if(studentId!=loginForm.getStudentId() && grievanceNumber!=null){
					isUpdated = StudentLoginHandler.getInstance().updateGrievanceMaster(grievanceNumber,curentNo);
				}
				isSaved = StudentLoginHandler.getInstance().saveGrievance(grievanceRemarkBoList);
				if(isSaved){
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_GRIEVANCE_ADDED_SUCCESSFULLY);
					messages.add("messages", message);
					saveMessages(request, messages);

				}else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_GRIEVANCE_ADDED_FAILED));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				System.out.println("------------------");
			}



			List<SubjectTO> mainList=new ArrayList<SubjectTO>();
			MarksCardTO to=loginForm.getMarksCardTo();
			Map<String,List<SubjectTO>> subMap=to.getSubMap();
			if(subMap!=null && !subMap.isEmpty()){
				for (Map.Entry<String,List<SubjectTO>> entry : subMap.entrySet()) {
					List<SubjectTO> subList=entry.getValue();
					mainList.addAll(subList);
				}
			}

			Map<String,List<SubjectTO>> addOnCoursesubMap=to.getAddOnCoursesubMap();
			if(addOnCoursesubMap!=null && !addOnCoursesubMap.isEmpty()){
				for (Map.Entry<String,List<SubjectTO>> entry : addOnCoursesubMap.entrySet()) {
					List<SubjectTO> subList=entry.getValue();
					mainList.addAll(subList);
				}
			}
			Map<String,List<SubjectTO>> nonElectivesubMap=to.getNonElectivesubMap();
			if(nonElectivesubMap!=null && !nonElectivesubMap.isEmpty()){
				for (Map.Entry<String,List<SubjectTO>> entry : nonElectivesubMap.entrySet()) {
					List<SubjectTO> subList=entry.getValue();
					mainList.addAll(subList);
				}
			}
			to.setMainList(mainList);
			loginForm.setMarksCardTo(to);
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(exception.getMessage());
			}
		} else {
			addErrors(request, errors);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD);
	}

	public ActionForward initGrievanceStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("Entered ScoreSheetAction - getCandidates");

		LoginForm loginForm = (LoginForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession(true);
		if (errors.isEmpty()) {
			try {
				List<GrievanceStatusTo> grievanceStatusToList = StudentLoginHandler.getInstance().getGrievanceList(loginForm);

				loginForm.setGrievanceStatusToList(grievanceStatusToList);

				/*List<SubjectTO> mainList=new ArrayList<SubjectTO>();
				MarksCardTO to=loginForm.getMarksCardTo();
				Map<String,List<SubjectTO>> subMap=to.getSubMap();
				if(subMap!=null && !subMap.isEmpty()){
					for (Map.Entry<String,List<SubjectTO>> entry : subMap.entrySet()) {
						List<SubjectTO> subList=entry.getValue();
						mainList.addAll(subList);
					}
				}

				Map<String,List<SubjectTO>> addOnCoursesubMap=to.getAddOnCoursesubMap();
				if(addOnCoursesubMap!=null && !addOnCoursesubMap.isEmpty()){
					for (Map.Entry<String,List<SubjectTO>> entry : addOnCoursesubMap.entrySet()) {
						List<SubjectTO> subList=entry.getValue();
						mainList.addAll(subList);
					}
				}
				Map<String,List<SubjectTO>> nonElectivesubMap=to.getNonElectivesubMap();
				if(nonElectivesubMap!=null && !nonElectivesubMap.isEmpty()){
					for (Map.Entry<String,List<SubjectTO>> entry : nonElectivesubMap.entrySet()) {
						List<SubjectTO> subList=entry.getValue();
						mainList.addAll(subList);
					}
				}
				to.setMainList(mainList);
				loginForm.setMarksCardTo(to);*/
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(exception.getMessage());
			}
		} else {
			addErrors(request, errors);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_GRIEVANCE_STATUS);
	}
	public ActionForward printGrievanceForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		List<GrievanceStudentTo> studentList = StudentLoginHandler.getInstance().getStudentDetails(loginForm);
		List<GrievanceStatusTo> grievanceStatusToList = StudentLoginHandler.getInstance().getGrievanceList(loginForm);
		loginForm.setGrievanceStudentToList(studentList);
		loginForm.setGrievanceStatusToList(grievanceStatusToList);
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_GRIEVANCE_STATUS_PRINT);
	}
	/*public ActionForward certificateMarksCardNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		ConsolidateMarksCardTO to=CertificateMarksCardHandler.getInstance().getStudentCertificateMarksCard(loginForm.getStudentId());
		if(to==null){
			return mapping.findForward(CMSConstants.CERTIFICATE_MARKS_CARD_NOT_AVALIABLE);
		}
		loginForm.setConsolidateMarksCardTO(to);
		return mapping.findForward(CMSConstants.CERTIFICATE_MARKS_CARD_NEW);
	} */

	public ActionForward certificateMarksCardNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;
		List<ConsolidateMarksCardTO> toList=CertificateMarksCardHandler.getInstance().getStudentCertificateMarksCardSemester(loginForm.getStudentId(),loginForm);
		loginForm.setConsolidateMarksCardTOList(toList);
		List<ConsolidateMarksCardTO> toList1=CertificateMarksCardHandler.getInstance().getStudentCertificateMarksCardForProgramme(loginForm.getStudentId(),loginForm);
		loginForm.setConsolidateMarksCardTOList1(toList1);
		if(toList==null){
			return mapping.findForward(CMSConstants.CERTIFICATE_MARKS_CARD_NOT_AVALIABLE);
		}

		return mapping.findForward(CMSConstants.CERTIFICATE_MARKS_CARD_RESULT);

	}

	public ActionForward printCertificateMarksCardNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;

		//if(loginForm.getResultType().equalsIgnoreCase("ProgramPart")){
		//	return mapping.findForward(CMSConstants.PRINT_PROGRAMPART_RESULT_CERTIFICATE_MARKS_CARD);
		//}else{
		return mapping.findForward(CMSConstants.PRINT_SEMESTER_RESULT_MARKS_CARD);
		//}
	}
	public int showSupplementaryRevaluationApplication(HttpSession session, int classId, LoginForm loginForm, String studentId) throws NumberFormatException, Exception{

		Map<Integer,List<Integer>> examClassMap=new HashMap<Integer, List<Integer>>();
		List<Integer> suppHallTcktClassIds=DownloadHallTicketHandler.getInstance().getSupplementaryClassIds(Integer.parseInt(studentId), classId, true, "Revaluation/Scrutiny");
		List<Integer> classIds=null;
		String exams = "";
		int suppExamId=0;
		for (Integer hallTcktClassId : suppHallTcktClassIds) {
			exams = "";
			if(hallTcktClassId > 0){
				exams = DownloadHallTicketHandler.getInstance().getExamIdByClassIdForSupHallTicket(hallTcktClassId, loginForm, "Revaluation/Scrutiny");
			}
			if(!exams.isEmpty()){
				String[] examsId=exams.split(",");
				for (String examId : examsId) {
					suppExamId = Integer.parseInt(examId);
					if(examClassMap.containsKey(Integer.parseInt(examId))){
						classIds=examClassMap.remove(Integer.parseInt(examId));
					}else
						classIds=new ArrayList<Integer>();
					classIds.add(hallTcktClassId);
					examClassMap.put(Integer.parseInt(examId),classIds);
				}
			}
		}

		List<ShowMarksCardTO> supList=new ArrayList<ShowMarksCardTO>();
		// map key is examId and value is list of classIds
		int count=1;
		int examId=0;
		for (Map.Entry<Integer, List<Integer>> entry : examClassMap.entrySet()) {
			examId=entry.getKey();
			List<Integer> classIdList = entry.getValue();
			ShowMarksCardTO to=new ShowMarksCardTO();
			to.setSupMCexamID(examId);
			to.setSupMCClassId(classIdList.get(0));
			to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId,"ExamDefinitionBO",true,"name"));
			to.setCnt(count);
			count+=1;
			List<Integer> classesId=new ArrayList<Integer>();
			for (Integer hallTcktClassId : entry.getValue()) {
				ExamBlockUnblockHallTicketBO examBlockUnblockHallTicketBO = DownloadHallTicketHandler.getInstance().isBlockedStudent(Integer.parseInt(studentId), hallTcktClassId, examId, "H");  
				boolean isBlockedStudent = false;
				if(examBlockUnblockHallTicketBO!= null && examBlockUnblockHallTicketBO.getId() > 0){
					isBlockedStudent = true;
				}
				boolean isDateValid = DownloadHallTicketHandler.getInstance().isDateValid(hallTcktClassId, examId, "Revaluation/Scrutiny", true);
				boolean isAppeared = DownloadHallTicketHandler.getInstance().isAppeared(Integer.parseInt(studentId), examId,hallTcktClassId,"Revaluation/Scrutiny");
				if(hallTcktClassId > 0 && examId > 0 && isDateValid && isAppeared){
					classesId.add(hallTcktClassId);
					to.setShowSupRevalAppln(true);
				}
				if(isBlockedStudent){
					to.setSupMCBlockedStudent(true);
					to.setSupMCBlockReason(examBlockUnblockHallTicketBO.getBlockReason());
				}
				//Change By manu,1st check condition
				ExamFooterAgreementBO agreementBO=null;
				if(loginForm.getAgreementId()>0){
					agreementBO = DownloadHallTicketHandler.getInstance().getAgreement(loginForm.getAgreementId());
				}
				if(agreementBO!= null && agreementBO.getDescription()!= null){
					to.setSupHallTicketagreement(agreementBO.getDescription());
				}
			}
			to.setClassIds(classesId);
			if(to.getShowSupRevalAppln()){
				supList.add(to);
			}
		}
		session.setAttribute("supRevalApplnList", supList);
		return suppExamId;

	}
	public ActionForward initInternalMarksGrievance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		HttpSession session = request.getSession();		
		loginForm.clear();
		loginForm.reset2();
		String studentid = (String) session.getAttribute("studentid");
		loginForm.setStudentId(Integer.parseInt(studentid));
		Map<Integer, Integer> schemeMap = new HashMap<Integer, Integer>();
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		schemeMap.put(1, 1);
		schemeMap.put(2, 2);
		schemeMap.put(3, 3);
		schemeMap.put(4, 4);
		if(loginForm.getProgramTypeId().equalsIgnoreCase("1"))
		{
			schemeMap.put(5, 5);
			schemeMap.put(6, 6);
		}
		//         schemeMap.put(7, 7);
		//         schemeMap.put(8, 8);
		//         schemeMap.put(9, 9);
		//         schemeMap.put(10, 10);       
		loginForm.setSchemeMap(schemeMap);
		try{
			//subjectMap = StudentLoginHandler.getInstance().getSubjectsListForStudent(loginForm);
			//loginForm.setSubjectMap(subjectMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK_GRIEVANCE); 
	}
	public ActionForward displayInternalMarkForStudentGrievance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		if(errors.isEmpty()){
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			loginForm.setStudentId(Integer.parseInt(studentid));
			loginForm.setCourseId(courseId);
			String classid = session.getAttribute("InternalGrivClassId").toString();
			List<InternalMarkCardTo> subjectTO = new ArrayList<InternalMarkCardTo>();
			MarksCardTO to = StudentLoginHandler.getInstance().getStudentInternalMarksStudentLoginGrievance(loginForm,classid);
			List<SubjectTO> internalMarkCardTo = to.getMainList(); 
			Iterator<SubjectTO> itr1 = internalMarkCardTo.iterator();
			while(itr1.hasNext()){
				SubjectTO to1 = itr1.next();
				InternalMarkCardTo ito = new InternalMarkCardTo();
				if(to1.getExamName().equalsIgnoreCase("Internal_I")){
					ito.setInternal1Mark(to1.getConvertedMark());
				}
				if(to1.getExamName().equalsIgnoreCase("Internal_II")){
					ito.setInternal2Mark(to1.getConvertedMark());
				}
				if(to1.getExamName().equalsIgnoreCase("Seminar")){
					ito.setSeminarMark(to1.getConvertedMark());
				}
				if(to1.getExamName().equalsIgnoreCase("Assignment")){
					ito.setAssignmentMark(to1.getConvertedMark());
				}
				subjectTO.add(ito);
			}
			if(to!=null){
				loginForm.setMarksCardTo(to);				
			}else{
				loginForm.setMarksCardTo(to);
				errors.add("error",new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK_RESULT_GRIEVANCE);
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK);
		}		
	}

	public ActionForward saveInternalGrievance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		String classid = session.getAttribute("InternalGrivClassId").toString();
		if(errors.isEmpty()){
			List<InternalExamGrievanceBo> internalExamGrievanceBoList = new ArrayList<InternalExamGrievanceBo>();
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			loginForm.setStudentId(Integer.parseInt(studentid));
			loginForm.setCourseId(courseId);
			List<SubjectTO> subjectTOList = loginForm.getMarksCardTo().getMainList(); 
			Iterator<SubjectTO> itr1 = subjectTOList.iterator();
			while(itr1.hasNext()){
				SubjectTO subjectTO = itr1.next();
				Student student = new Student();
				Subject subject = new Subject();
				Classes classes = new Classes();
				ExamDefinition examDefinition = new ExamDefinition();
				InternalExamGrievanceBo bo = new InternalExamGrievanceBo();
				if(subjectTO.getGrievanceRemark()!=null && !subjectTO.getGrievanceRemark().trim().equalsIgnoreCase("")){
					bo.setComment(subjectTO.getGrievanceRemark());
					if(subjectTO.getExamName().equalsIgnoreCase("Internal_I")){
						bo.setInternal1Mark(subjectTO.getConvertedMark());
					}
					if(subjectTO.getExamName().equalsIgnoreCase("Internal_II")){
						bo.setInternal2Mark(subjectTO.getConvertedMark());
					}
					if(subjectTO.getExamName().equalsIgnoreCase("Seminar")){
						bo.setSeminarMark(subjectTO.getConvertedMark());
					}
					if(subjectTO.getExamName().equalsIgnoreCase("Assignment")){
						bo.setAssignmentMark(subjectTO.getConvertedMark());
					}
					student.setId(Integer.parseInt(studentid));
					bo.setStudent(student);
					subject.setId(subjectTO.getId());
					examDefinition.setId(Integer.parseInt(loginForm.getInternalExamId()));
					classes.setId(Integer.parseInt(classid));
					//bo.setSubject(subject);
					bo.setExamDefinition(examDefinition);
					bo.setClasses(classes);
					Integer empId = StudentLoginHandler.getInstance().getHodIdBySubjectId(subjectTO.getId());
					if(empId!=null){
						bo.setHodId(String.valueOf(empId));
					}
					bo.setSemNo(String.valueOf(loginForm.getTermNo()));
					bo.setIsHodApproved(false);
					bo.setIsHodPending(true);
					bo.setIsHodRejected(false);
					bo.setIsControlerApproved(false);
					bo.setIsControlerPending(true);
					bo.setIsControlerRejected(false);
					bo.setCreatedDate(new Date());
					bo.setLastModifiedDate(new Date());
					internalExamGrievanceBoList.add(bo);

				}

			}
			boolean isSaved=false;
			if(internalExamGrievanceBoList.size()!=0){
				isSaved = StudentLoginHandler.getInstance().saveInternalGrievance(internalExamGrievanceBoList);
			}


			/*MarksCardTO to = StudentLoginHandler.getInstance().getStudentInternalMarksStudentLoginGrievance(loginForm,courseId);
			if(to!=null){
				loginForm.setMarksCardTo(to);				
			}else{
				loginForm.setMarksCardTo(to);
				errors.add("error",new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
			}*/
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK_RESULT_GRIEVANCE);
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK);
		}		
	}
	public ActionForward initInternalGrievance(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering the initOnlineRecieptsForStudentLogin");
		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		cleanUpPageData(loginForm);
		if(request.getSession().getAttribute("marksCardBlockReason")!=null){
			request.getSession().removeAttribute("marksCardBlockReason");
		}
		//session.setAttribute("regMarksCard", regMarksCard);
		if(request.getSession().getAttribute("regMarksCard")!=null){
			request.getSession().removeAttribute("regMarksCard");
		}
		if(request.getSession().getAttribute("supMarksCard")!=null){
			request.getSession().removeAttribute("supMarksCard");
		}

		if(request.getSession().getAttribute("marksCardAgreement")!=null){
			request.getSession().removeAttribute("marksCardAgreement");
		}
		if (errors.isEmpty()) {
			try {
				loginForm.setIsGrievance(true);
				StudentLogin studentLogin = LoginHandler.getInstance().isValiedStudentUser(loginForm) ;

				if (studentLogin == null) {
					message = new ActionMessage("knowledgepro.admin.validusername");
					messages.add("messages", message);
					addErrors(request, messages);
					loginForm.resetFields();
					if(CMSConstants.LINK_FOR_CJC)
					{ 
						return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
					}else
					{
						return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
					}
				} else {		
					@SuppressWarnings("unused")
					List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
					// no need to excute this code if student is detained
					//if(!listOfDetainedStudents.contains(loginForm.getStudentId())){
					loginForm.setExamType("Internal");
					String classid = session.getAttribute("InternalGrivClassId").toString();
					List<MarksCardTO> SemExamListTo=(DownloadHallTicketHandler.getInstance().getExamSemListForInternalGrievance(loginForm,classid));
					if(SemExamListTo!=null)	
					{
						//Collections.sort(SemExamListTo,new MarkComparator());
						loginForm.setRegularExamList(SemExamListTo);
					}
					//}
					//Sup Marks Card
					/*List<MarksCardTO> SupSemList=(DownloadHallTicketHandler.getInstance().getSupExamSemListForGrievance(loginForm));
					if(SupSemList!= null)
						Collections.sort(SupSemList,new MarkComparator());
					loginForm.setSuppExamList(SupSemList);*/

					log.debug("Exit  from initOnlineRecieptsForStudentLogin");
					return mapping.findForward(CMSConstants.STUDENT_LOGIN_MARKSCARD_GRIEVANCE);
				}	
			} catch (ApplicationException e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch(Exception e) {
				log.debug("leaving the studentLoginAction with exception");
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
				//throw e;
			}
		} else {
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			if(CMSConstants.LINK_FOR_CJC)
			{ 
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_FAILURE_CJC);
			}else
			{
				return mapping.findForward(CMSConstants.STUDENTLOGIN_FAILURE);
			}
		}
	}



	public ActionForward initServiceLearningActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		if(errors.isEmpty()){
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			loginForm.setStudentId(Integer.parseInt(studentid));
			loginForm.setCourseId(courseId);
			//List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHandler.getInstance().getProgrammeNameEntry();

			List<ProgrammeEntryTo> programmeEntryToList = StudentLoginHandler.getInstance().getProgrammeNameEntry();
			loginForm.setProgrammeEntryToList(programmeEntryToList);

			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			loginForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			if(loginForm.getProgramId()!=null){
				Map<Integer, String> programeMap =CommonAjaxHandler.getInstance().getProgramesByDepartmentId(loginForm.getProgramId());
				loginForm.setProgrameMap(programeMap);
			}
			List<ServiceLearningActivityTo> serviceLearningActivityToList = StudentLoginHandler.getInstance().convertBotoTo(loginForm);
			loginForm.setServiceLearningActivityToList(serviceLearningActivityToList);

			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_ACTIVITY);
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK);
		}		
	}

	public ActionForward submitServiceLearningActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		boolean isSaved=false;
		boolean isDuplicate=false;
		if((loginForm.getStartHours().trim().equals("00") || loginForm.getStartHours().trim().equals("0")) && (loginForm.getStartMins().trim().equals("00") && loginForm.getStartMins().trim().equals("0")))
		{
			errors.add("error", new ActionError("knowledgepro.new.scheduled.hours.invalid"));
		}
		if(loginForm.getStartHours()!=null && !loginForm.getStartHours().isEmpty())
		{
			Integer smsH=Integer.parseInt(loginForm.getStartHours());
			if(smsH>24)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.hours.invalid"));
			}
		}
		if(loginForm.getStartMins()!=null && !loginForm.getStartMins().isEmpty())
		{
			Integer smsM=Integer.parseInt(loginForm.getStartMins());
			if(smsM>59)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.mins.invalid"));
			}
		}
		if(loginForm.getStartMins().trim().equals("00") || loginForm.getStartHours().trim().equals("0"))
		{
			errors.add("error", new ActionError("knowledgepro.new.scheduled.mins.invalid"));

		}
		if(errors.isEmpty()){
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			String classid = session.getAttribute("CurrentClassId").toString();
			ServiceLearningActivityBo serviceLearningActivityBo = new ServiceLearningActivityBo();
			DepartmentNameEntry departmentNameEntry = new DepartmentNameEntry();
			ProgrammeEntryBo programmeEntryBo = new ProgrammeEntryBo();
			Classes  classes = new Classes();
			Student student = new Student();
			String time=null;
			String timeH=null;
			String timeM=null;
			int timeMt = 0;
			Iterator<ProgrammeEntryTo> itr = (loginForm.getProgrammeEntryToList()).iterator();
			while(itr.hasNext()){
				ProgrammeEntryTo to = itr.next();
				String id =to.getId();
				if(Integer.parseInt(loginForm.getProgramId())==Integer.parseInt(id)){
					if(to.getMaxHrs()!=null){
						time=to.getMaxHrs();
						timeH=time.substring(0,2);
						timeM=time.substring(3,5);
						timeMt=Integer.parseInt(timeM);
					}
					//maxHrs = Integer.parseInt(to.getMaxHrs());
					serviceLearningActivityBo.setLearningAndActivity(loginForm.getLearningAndOutcome());
					//serviceLearningActivityBo.setAttendedHours(loginForm.getAttendedHours());
					if(loginForm.getStartHours()!=null && !loginForm.getStartHours().isEmpty() &&
							loginForm.getStartMins()!=null && !loginForm.getStartMins().isEmpty())
					{
						String hrs="";
						String mins="";
						if(loginForm.getStartHours().length()==1){
							hrs = "0"+loginForm.getStartHours();
						}else{
							hrs = loginForm.getStartHours();
						}
						if(loginForm.getStartMins().length()==1){
							mins = "0"+loginForm.getStartMins();
						}else{
							mins = loginForm.getStartMins();
						}
						serviceLearningActivityBo.setAttendedHours(CommonUtil.getTime(hrs, mins));
					}
					if(loginForm.getProgramId()!=null)
						programmeEntryBo.setId(Integer.parseInt(loginForm.getProgramId()));
					if(loginForm.getDepartmentId()!=null)
						departmentNameEntry.setId(Integer.parseInt(to.getDepartmentNameEntryTo().getId()));
					student.setId(Integer.parseInt(studentid));
					serviceLearningActivityBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningActivityBo.setDepartmentNameEntry(departmentNameEntry);
					serviceLearningActivityBo.setCreatedDate(new Date());
					serviceLearningActivityBo.setLastModifiedDate(new Date());
					serviceLearningActivityBo.setStudent(student);
					classes.setId(Integer.valueOf(classid));
					serviceLearningActivityBo.setClasses(classes);
					serviceLearningActivityBo.setStartDate(CommonUtil.ConvertStringToSQLDate(to.getStartDate()));
					serviceLearningActivityBo.setEndDate(CommonUtil.ConvertStringToSQLDate(to.getEndDate()));
					//serviceLearningActivityBo.setMaxHours(String.valueOf(maxHrs));
					loginForm.setStudentId(Integer.parseInt(studentid));
					loginForm.setCourseId(courseId);
				}
			}

			isDuplicate = StudentLoginHandler.getInstance().isDupServiceLearningActivity(serviceLearningActivityBo);
			if(!isDuplicate){
				String times=serviceLearningActivityBo.getAttendedHours().toString();
				String timeHs=times.substring(0,2);
				String timeMs=times.substring(3,5);
				if((timeH.equalsIgnoreCase(timeHs) && Integer.parseInt(timeMs)> Integer.parseInt(timeM)) || Integer.parseInt(timeH)< Integer.parseInt(timeHs) ){
					errors.add("error", new ActionError("knowledgepro.servicelearing.activity.maxHrs",time));
					saveErrors(request, errors);
					List<ServiceLearningActivityTo> serviceLearningActivityToList = StudentLoginHandler.getInstance().convertBotoTo(loginForm);
					loginForm.setServiceLearningActivityToList(serviceLearningActivityToList);
					return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_ACTIVITY);
				}else{
					isSaved = StudentLoginHandler.getInstance().saveServiceLearningActivity(serviceLearningActivityBo);
					if(isSaved){
						ActionMessage message = new ActionMessage("knowledgepro.servicelearing.activity.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						loginForm.setLearningAndOutcome(null);
						loginForm.setStartHours(null);
						loginForm.setStartMins(null);
						loginForm.setDepartmentId(null);
						loginForm.setProgramId(null);
					}else{
						errors.add("error", new ActionError("knowledgepro.servicelearing.programmename.addfailure"));
						saveErrors(request, errors);
					}
				}
			}else{
				if(isDuplicate){
					errors.add("error", new ActionError("knowledgepro.servicelearing.activity.exists"));
					saveErrors(request, errors);
					List<ServiceLearningActivityTo> serviceLearningActivityToList = StudentLoginHandler.getInstance().convertBotoTo(loginForm);
					loginForm.setServiceLearningActivityToList(serviceLearningActivityToList);
					return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_ACTIVITY);
				}

			}
			List<ServiceLearningActivityTo> serviceLearningActivityToList = StudentLoginHandler.getInstance().convertBotoTo(loginForm);
			loginForm.setServiceLearningActivityToList(serviceLearningActivityToList);

			List<DepartmentNameEntryTo> departmentNameEntryToList = DepartmentNameEntryHandler.getInstance().getDepartmentNames();
			loginForm.setDepartmentNameEntryToList(departmentNameEntryToList);
			if(loginForm.getProgramId()!=null){
				Map<Integer, String> programeMap =CommonAjaxHandler.getInstance().getProgramesByDepartmentId(loginForm.getProgramId());
				loginForm.setProgrameMap(programeMap);
			}

			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_ACTIVITY);
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_ACTIVITY);
		}		
	}

	public ActionForward serviceLearningActivityRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		if(errors.isEmpty()){
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			loginForm.setStudentId(Integer.parseInt(studentid));
			loginForm.setCourseId(courseId);
			List<ServiceLearningMarksEntryTo> serviceLearningmarksEntryToList=StudentLoginHandler.getInstance().getStudentDetailsById(loginForm);
			loginForm.setServiceLearningMarksEntryToList(serviceLearningmarksEntryToList);
			if(serviceLearningmarksEntryToList.size()!=0){

				return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_RECORD);
			}else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_LOGIN_SERVICE_LEARNING_RECORD);
			}
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK);
		}		
	}

	public ActionForward printServiceLearningRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		log.debug("Entering the getHallTicket");
		LoginForm loginForm = (LoginForm) form;		
		ActionErrors errors=loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		if(errors.isEmpty()){
			String studentid = (String) session.getAttribute("studentid");
			String courseId = (String) session.getAttribute("stuCourseId");
			loginForm.setStudentId(Integer.parseInt(studentid));
			loginForm.setCourseId(courseId);
			List<ServiceLearningMarksEntryTo> serviceLearningmarksEntryToList=StudentLoginHandler.getInstance().getStudentDetailsById(loginForm);
			loginForm.setServiceLearningMarksEntryToList(serviceLearningmarksEntryToList);

			return mapping.findForward(CMSConstants.PRINT_STUDENT_LOGIN_SERVICE_LEARNING_RECORD);
		}else{
			log.debug("leaving the studentLoginAction with errors");
			addErrors(request, errors);	
			return mapping.findForward(CMSConstants.STUDENT_LOGIN_INTERNAL_MARK);
		}		
	}
	 public ActionForward personalDataEdit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        try {
	            final String studentid = (String)session.getAttribute("studentid");
	            final Integer courseId = (Integer)session.getAttribute("CurrentClassId");
	            loginForm.setCourseId(String.valueOf(courseId));
	            final Student student = LoginHandler.getInstance().getStudentObj(studentid);
	            final PersonalData personalData = student.getAdmAppln().getPersonalData();
	            final PersonalDataTO personalDataTO = LoginHandler.getInstance().convertBOTO(personalData, loginForm, student);
	            final EdnQualificationTO ednQualificationTo = LoginHandler.getInstance().getEdnQualificationTO(personalDataTO, loginForm, student);
	            loginForm.setPersonalData(personalDataTO);
	            final List<AdmittedThroughTO> admittedList = (List<AdmittedThroughTO>)AdmittedThroughHandler.getInstance().getAdmittedThrough();
	            loginForm.setAdmittedThroughList((List)admittedList);
	            loginForm.setIsUpdated(Boolean.valueOf(false));
	            if (ednQualificationTo != null) {
	                loginForm.setEdnQualification(ednQualificationTo);
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("personalDataEdit");
	    }
	    
	    public ActionForward savePersonalData(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        ActionMessages errors = new ActionMessages();
	        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
	        try {
	            final String studentid = (String)session.getAttribute("studentid");
	            final Student student = LoginHandler.getInstance().getStudentObj(studentid);
	            final AdmApplnTO admapplnTO = loginForm.getApplicantDetails();
	            final AdmAppln admAppln = student.getAdmAppln();
	            final AdmittedThrough admTrogh = new AdmittedThrough();
	            if (admapplnTO.getAdmissionNumber() != null && !admapplnTO.getAdmissionNumber().isEmpty()) {
	                admAppln.setAdmissionNumber(admapplnTO.getAdmissionNumber());
	            }
	            if (admapplnTO.getAdmittedThroughId() != null && !admapplnTO.getAdmittedThroughId().isEmpty()) {
	                admTrogh.setId(Integer.parseInt(admapplnTO.getAdmittedThroughId()));
	                admAppln.setAdmittedThrough(admTrogh);
	            }
	            student.setAdmAppln(admAppln);
	            final PersonalData personalData = student.getAdmAppln().getPersonalData();
	            final PersonalDataTO dataTO = loginForm.getPersonalData();
	            if (dataTO != null && dataTO.getDob() != null && !StringUtils.isEmpty(dataTO.getDob())) {
	                if (CommonUtil.isValidDate(dataTO.getDob())) {
	                    final boolean isValid = OnlineApplicationAction.validatefutureDate(dataTO.getDob());
	                    if (!isValid && errors.get("admissionFormForm.dob.large") != null && !errors.get("admissionFormForm.dob.large").hasNext()) {
	                        errors.add("admissionFormForm.dob.large", (ActionMessage)new ActionError("admissionFormForm.dob.large"));
	                    }
	                }
	                else if (errors.get("admissionFormForm.dateOfBirth.invalid") != null && !errors.get("admissionFormForm.dateOfBirth.invalid").hasNext()) {
	                    errors.add("admissionFormForm.dateOfBirth.invalid", (ActionMessage)new ActionError("admissionFormForm.dateOfBirth.invalid"));
	                }
	            }
	            final CandidateMarkTO detailmark = loginForm.getDetailMark();
				if (Integer.parseInt(loginForm.getProgramTypeId())==2) {
					if (loginForm.getDetailMark() == null && errors.get("admissionFormForm.education.detailMark.mandatory") != null) {
						final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
						errors.add("admissionFormForm.education.detailMark.mandatory", error);
					}
					if (loginForm.getDetailMark() != null && detailmark.getTotalMarksCGPA() != null && detailmark.getTotalMarksCGPA().equalsIgnoreCase("")) {
						final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
						errors.add("admissionFormForm.education.detailMark.mandatory", error);
					}
					
				}else{
				if (loginForm.getDetailMark() == null && errors.get("admissionFormForm.education.detailMark.mandatory") != null) {
					final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
					errors.add("admissionFormForm.education.detailMark.mandatory", error);
				}
				if (loginForm.getDetailMark() != null && detailmark.getTotalMarks() != null && detailmark.getTotalMarks().equalsIgnoreCase("")) {
					final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
					errors.add("admissionFormForm.education.detailMark.mandatory", error);
				}
			}
	            errors = this.validateEditEducationDetails(errors, loginForm);
	            if (errors != null && !errors.isEmpty()) {
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataEdit");
	            }
	            final Set<EdnQualification> qualificationSet = (Set<EdnQualification>)LoginHandler.getInstance().updatePlusTwoMarks(student, loginForm, personalData);
	            personalData.setEdnQualifications((Set)qualificationSet);
	            final boolean isUpdated = LoginHandler.getInstance().savePersonalData(loginForm, dataTO, personalData, student, request);
	            loginTransaction.SaveAdmAppl(admAppln);
	            if (isUpdated) {
	                loginForm.setPDataEdited(true);
	                loginForm.setIsUpdated(Boolean.valueOf(true));
	                return mapping.findForward("personalDataEdit");
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("personalDataEdit");
	    }
	    
	    public ActionForward editStudentPhoto(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        loginForm.setIsUpdated(Boolean.valueOf(false));
	        return mapping.findForward("studenteditphoto");
	    }
	    
	    public ActionForward saveStudentPhoto(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(false);
	        final ActionMessages errors = new ActionMessages();
	        final ActionMessages messages = new ActionMessages();
	        final ILoginTransaction loginTransaction = (ILoginTransaction)new LoginTransactionImpl();
	        try {
	            this.validateEditDocumentSizeOnline(loginForm, errors, request);
	            setUserId(request, loginForm);
	            if (errors != null && !errors.isEmpty()) {
	                this.saveErrors(request, errors);
	                return mapping.findForward("studenteditphoto");
	            }
	            final Student student = LoginHandler.getInstance().getStudentObj(String.valueOf(loginForm.getStudentId()));
	            final boolean isUpdated = LoginHandler.getInstance().uploadPhoto(loginForm, student);
	            if (isUpdated) {
	                final PersonalData data = student.getAdmAppln().getPersonalData();
	                data.setIsPhotoEdited(true);
	                loginForm.setPhotoEdited(true);
	                loginTransaction.savePersonalData(data);
	                final ActionMessage message = new ActionMessage("knowledgepro.admin.publish.student.photo.updated");
	                messages.add("messages", message);
	                this.saveMessages(request, messages);
	                loginForm.setIsUpdated(Boolean.valueOf(true));
	                return mapping.findForward("studenteditphoto");
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("studenteditphoto");
	    }
	    
	    private void validateEditDocumentSizeOnline(final LoginForm loginForm, final ActionMessages errors, final HttpServletRequest request) throws Exception {
	        final InputStream propStream = OnlineApplicationAction.class.getResourceAsStream("/resources/application.properties");
	        int maXSize = 0;
	        int maxPhotoSize = 0;
	        final Properties prop = new Properties();
	        try {
	            prop.load(propStream);
	            maXSize = Integer.parseInt(prop.getProperty("knowledgepro.upload.maxSize"));
	            maxPhotoSize = Integer.parseInt(prop.getProperty("knowledgepro.upload.maxPhotoSize"));
	        }
	        catch (IOException ex) {}
	        final FormFile file = loginForm.getStudentPhoto();
	        if (file != null) {
	            final String filename = file.getFileName();
	            if (!StringUtils.isEmpty(filename) && filename.length() > 30 && errors.get("admissionFormForm.attachment.filename.large") != null && !errors.get("admissionFormForm.attachment.filename.large").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.attachment.filename.large");
	                errors.add("admissionFormForm.attachment.filename.large", error);
	            }
	        }
	        if (file != null) {
	            try {
	                if (!StringUtils.isEmpty(file.getFileName())) {
	                    final Image img = ImageIO.read(file.getInputStream());
	                    if (img == null && errors.get("admissionFormForm.online.photo.invalid") != null && !errors.get("admissionFormForm.online.photo.invalid").hasNext()) {
	                        errors.add("error", (ActionMessage)new ActionError("admissionFormForm.online.photo.invalid"));
	                    }
	                }
	            }
	            catch (Exception e) {
	                if (errors.get("admissionFormForm.online.photo.invalid") != null && !errors.get("admissionFormForm.online.photo.invalid").hasNext()) {
	                    errors.add("error", (ActionMessage)new ActionError("admissionFormForm.online.photo.invalid"));
	                }
	            }
	            final boolean remove = this.validateImageHeightWidth(file.getFileData(), file.getFileName(), file.getContentType(), errors, request);
	            if (remove) {
	                loginForm.setRemove(remove);
	            }
	            if ((maxPhotoSize < file.getFileSize() || (file.getFileSize() != 0 && 30720 > file.getFileSize())) && errors.get("admissionFormForm.attachment.maxPhotoSize") != null && !errors.get("admissionFormForm.attachment.maxPhotoSize").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.attachment.maxPhotoSize");
	                errors.add("admissionFormForm.attachment.maxPhotoSize", error);
	            }
	            if (file.getFileName() != null && !StringUtils.isEmpty(file.getFileName().trim())) {
	                String extn = "";
	                final int index = file.getFileName().lastIndexOf(".");
	                if (index != -1) {
	                    extn = file.getFileName().substring(index, file.getFileName().length());
	                }
	                if (!extn.isEmpty() && !extn.equalsIgnoreCase(".jpg") && errors.get("admissionFormForm.online.photo.invalid") != null && !errors.get("admissionFormForm.online.photo.invalid").hasNext()) {
	                    errors.add("error", (ActionMessage)new ActionError("admissionFormForm.online.photo.invalid"));
	                }
	            }
	        }
	        else if (file != null && maXSize < file.getFileSize()) {
	            if (errors.get("admissionFormForm.attachment.maxfileSize") != null && !errors.get("admissionFormForm.attachment.maxfileSize").hasNext()) {
	                final ActionMessage error2 = new ActionMessage("admissionFormForm.attachment.maxfileSize");
	                errors.add("admissionFormForm.attachment.maxfileSize", error2);
	            }
	        }
	        else if (file == null) {
	            final ActionMessage error2 = new ActionMessage("admissionFormForm.attachment.photoupload");
	            errors.add("admissionFormForm.attachment.photoupload", error2);
	        }
	    }
	    
	    private boolean validateImageHeightWidth(final byte[] fileData, final String fileName, final String contentType, final ActionMessages errors, final HttpServletRequest request) throws Exception {
	        boolean remove = false;
	        if (fileData != null && fileName != null && !StringUtils.isEmpty(fileName) && contentType != null && !StringUtils.isEmpty(contentType)) {
	            File file = null;
	            String filePath = request.getRealPath("");
	            filePath = String.valueOf(filePath) + "//TempFiles//";
	            final File file2 = new File(String.valueOf(filePath) + fileName);
	            file = new File(fileName);
	            String path = file.getAbsolutePath();
	            Image image = Toolkit.getDefaultToolkit().getImage(path);
	            ImageIcon icon = new ImageIcon(image);
	            int height = icon.getIconHeight();
	            int width = icon.getIconWidth();
	            if (width > 600 || height > 600) {
	                remove = true;
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.online.photo.dimension.size"));
	            }
	            if (file.exists()) {
	                file.delete();
	            }
	            path = file2.getAbsolutePath();
	            image = Toolkit.getDefaultToolkit().getImage(path);
	            icon = new ImageIcon(image);
	            height = icon.getIconHeight();
	            width = icon.getIconWidth();
	            if (width > 600 || height > 600) {
	                remove = true;
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.online.photo.dimension.size"));
	            }
	            if (file2.exists()) {
	                file2.delete();
	            }
	        }
	        return remove;
	    }
	    
	    public ActionForward editStudentPhotoInLogin(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(false);
	        loginForm.setIsUpdated(Boolean.valueOf(false));
	        session.setAttribute("stuId", (Object)loginForm.getStudentId());
	        session.setAttribute("courseId", (Object)loginForm.getCourseId());
	        session.setAttribute("photoEdited", (Object)loginForm.isPhotoEdited());
	        session.setAttribute("sLogin", (Object)true);
	        return mapping.findForward("editstudentphoto");
	    }
	    
	    public ActionForward getStudentTimeTable(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        StudentLoginAction.log.debug((Object)"Enter the getStudentTimeTable");
	        final LoginForm loginForm = (LoginForm)form;
	        final ActionErrors errors = loginForm.validate(mapping, request);
	        final ActionMessages messages = new ActionMessages();
	        Label_0279: {
	            if (errors.isEmpty()) {
	                try {
	                    final List<StudentLoginTO> classTos = (List<StudentLoginTO>)StudentLoginHandler.getInstance().getTimeTableForInputClass(loginForm);
	                    if (classTos == null || classTos.isEmpty()) {
	                        if (loginForm.getMsg() != null && !loginForm.getMsg().isEmpty()) {
	                            errors.add("error", new ActionError("knowledgepro.admission.empty.err.message", (Object)loginForm.getMsg()));
	                        }
	                        else {
	                            errors.add("error", new ActionError("knowledgepro.admission.norecordsfound"));
	                        }
	                        this.saveErrors(request, errors);
	                        this.setRequiredDatatoForm(loginForm);
	                        StudentLoginAction.log.info((Object)"Exit Interview Batch Result - getSelectedCandidates size 0");
	                        return mapping.findForward("studenttimetable");
	                    }
	                    loginForm.setClassTos((List)classTos);
	                    loginForm.setTtClassHistoryExists(StudentLoginHandler.getInstance().checkTimeTableClassHistory(loginForm.getClassId()));
	                    loginForm.setClassName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(loginForm.getClassId()), "ClassSchemewise", false, "classes.name"));
	                    break Label_0279;
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                    final String msg = super.handleApplicationException(e);
	                    loginForm.setErrorMessage(msg);
	                    loginForm.setErrorStack(e.getMessage());
	                    return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
	                }
	            }
	            this.addErrors(request, (ActionMessages)errors);
	            this.setRequiredDatatoForm(loginForm);
	            StudentLoginAction.log.info((Object)"Exit TimeTableForClassAction - getPeriods errors not empty ");
	        }
	        StudentLoginAction.log.info((Object)"Entered TimeTableForClassAction - getPeriods");
	        return mapping.findForward("studenttimetable");
	    }
	    
	    private void setRequiredDatatoForm(final LoginForm loginForm) throws Exception {
	        final Calendar calendar = Calendar.getInstance();
	        int currentYear = calendar.get(1);
	        final int year = CurrentAcademicYear.getInstance().getAcademicyear();
	        if (year != 0) {
	            currentYear = year;
	        }
	        if (loginForm.getYear() != null && !loginForm.getYear().isEmpty()) {
	            currentYear = Integer.parseInt(loginForm.getYear());
	        }
	        final Map<Integer, String> classMap = (Map<Integer, String>)CommonAjaxHandler.getInstance().getClassesForAttendanceByYear(currentYear, loginForm.getEvenOrOdd());
	        loginForm.setClassMap((Map)classMap);
	        final IAttendanceEntryTransaction transaction = (IAttendanceEntryTransaction)AttendanceEntryTransactionImpl.getInstance();
	        final boolean isStaff = transaction.checkIsStaff(loginForm.getUserId());
	        if (isStaff) {
	            final String[] selectedTeachers = { loginForm.getUserId() };
	            loginForm.setIsStaff(Boolean.valueOf(true));
	        }
	        else {
	            loginForm.setIsStaff(Boolean.valueOf(false));
	        }
	        final String teacherName = transaction.getTeacherName(Integer.parseInt(loginForm.getUserId()));
	        loginForm.setEmpName(teacherName);
	    }
	    
	    public ActionForward feePayemtOnlineStudent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        loginForm.setIsUpdated(Boolean.valueOf(false));
	        session.setAttribute("stuId", (Object)loginForm.getStudentId());
	        session.setAttribute("courseId", (Object)loginForm.getCourseId());
	        final String studentid = (String)session.getAttribute("studentid");
	        final Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
	        final int appnId = student.getAdmAppln().getApplnNo();
	        loginForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
	        final Integer academicYear = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
	        final int semNo = loginForm.getTermNo();
	        int year = 0;
	        if (semNo == 1 || semNo == 2) {
	            year = 1;
	        }
	        if (semNo == 3 || semNo == 4) {
	            year = 2;
	        }
	        if (semNo == 5 || semNo == 6) {
	            year = 3;
	        }
	        final int courseId = Integer.parseInt(loginForm.getCourseId());
	        final int categoryId = student.getAdmAppln().getFeeCategory().getId();
	        final String categoryName = student.getAdmAppln().getFeeCategory().getName();
	        final String studentName = student.getAdmAppln().getPersonalData().getFirstName();
	        final String status = "Success";
	        final String mode = "Online";
	        final CandidatePGIDetails pgiDetails = StudentLoginHandler.getInstance().getPaymentStatus(studentName, status, mode);
	        if (pgiDetails != null && !pgiDetails.toString().isEmpty() && pgiDetails.getUniqueId() == null) {
	            if (pgiDetails.getTxnStatus().equalsIgnoreCase("Success")) {
	                loginForm.setIsSuccess(true);
	            }
	            else {
	                loginForm.setIsSuccess(false);
	            }
	        }
	        final FeePayment payment = StudentLoginHandler.getInstance().getPaymentData(appnId);
	        if (payment != null && !payment.toString().isEmpty()) {
	            if (payment != null && !payment.toString().isEmpty() && pgiDetails == null) {
	                if (payment.getPaymentVerified().toString().equalsIgnoreCase("true")) {
	                    loginForm.setIsSuccess(true);
	                }
	                else {
	                    loginForm.setIsSuccess(false);
	                }
	            }
	        }
	        else {
	            loginForm.setIsSuccess(false);
	        }
	        List<Object[]> paymentList = new ArrayList<Object[]>();
	        final boolean isSemesterWise = StudentLoginHandler.getInstance().checkIsSemesterWise(courseId);
	        if (isSemesterWise) {
	            paymentList = (List<Object[]>)StudentLoginHandler.getInstance().getPaymentDetailsSemesterWise(categoryId, courseId, year, semNo, academicYear);
	        }
	        else {
	            paymentList = (List<Object[]>)StudentLoginHandler.getInstance().getPaymentDetails(categoryId, courseId, year, academicYear);
	        }
	        session.setAttribute("paymentList", (Object)paymentList);
	        if (paymentList != null && !paymentList.isEmpty()) {
	            final List<StudentTO> paymentDetailsList = new ArrayList<StudentTO>();
	            final Iterator<Object[]> iterator = paymentList.iterator();
	            while (iterator.hasNext()) {
	                final StudentTO to = new StudentTO();
	                final Object[] obj = iterator.next();
	                final int feeCatId = Integer.parseInt(obj[2].toString());
	                final int feeheading = Integer.parseInt(obj[1].toString());
	                final FeeCategory category = StudentLoginHandler.getInstance().getFeeCat(feeCatId);
	                if (category != null) {
	                    to.setCategoryName(categoryName);
	                }
	                final FeeHeading heading = StudentLoginHandler.getInstance().getHeadings(feeheading);
	                if (heading != null) {
	                    to.setHeadingName(heading.getName());
	                }
	                to.setAssignedAmount(obj[0].toString());
	                final String assignedAmount = obj[0].toString();
	                final List<FeePayment> feePaymentList = (List<FeePayment>)StudentLoginHandler.getInstance().getList(studentid, loginForm.getClassId());
	                if (feePaymentList == null || feePaymentList.size() == 0) {
	                    to.setTotalPaidAmt("0.0");
	                    to.setTotalBalanceAmt("0.0");
	                }
	                else {
	                    for (final FeePayment fPayment : feePaymentList) {
	                        to.setTotalPaidAmt(fPayment.getTotalFeePaid().toString());
	                        to.setTotalBalanceAmt(fPayment.getTotalBalanceAmount().toString());
	                    }
	                }
	                final Iterator<FeePayment> itr = feePaymentList.iterator();
	                session.setAttribute("assignedAmount", (Object)assignedAmount);
	                if (pgiDetails != null) {
	                    if (pgiDetails.getUniqueId() == null) {
	                        if (pgiDetails.getTxnStatus().equalsIgnoreCase("Success")) {
	                            loginForm.setIsDisplayText(true);
	                            loginForm.setAmount("");
	                        }
	                        else {
	                            loginForm.setIsDisplayText(false);
	                            loginForm.setAmount(assignedAmount);
	                        }
	                    }
	                    else {
	                        loginForm.setIsDisplayText(false);
	                        loginForm.setAmount(assignedAmount);
	                    }
	                }
	                else {
	                    loginForm.setIsDisplayText(false);
	                    loginForm.setAmount(assignedAmount);
	                }
	                if (payment != null && !payment.toString().isEmpty()) {
	                    if (payment != null && !payment.toString().isEmpty() && pgiDetails == null) {
	                        if (payment.getPaymentVerified().toString().equalsIgnoreCase("true")) {
	                            loginForm.setIsDisplayText(true);
	                            loginForm.setAmount("");
	                        }
	                        else {
	                            loginForm.setIsDisplayText(false);
	                            loginForm.setAmount(assignedAmount);
	                        }
	                    }
	                }
	                else {
	                    loginForm.setIsDisplayText(false);
	                    loginForm.setAmount(assignedAmount);
	                }
	                paymentDetailsList.add(to);
	            }
	            loginForm.setPaymentListStudent((List)paymentDetailsList);
	        }
	        return mapping.findForward("paymentDetailsStudent");
	    }
	    
	    public ActionForward redirectingToPGI(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final ActionErrors errors = new ActionErrors();
	        final HttpSession session = request.getSession();
	        final String studentid = (String)session.getAttribute("studentid");
	        final Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
	        final int courseId = Integer.parseInt(loginForm.getCourseId());
	        try {
	            if (courseId == 21) {
	                final String hash = StudentLoginHandler.getInstance().getParameterForPGIOnline(loginForm, student);
	                request.setAttribute("user_code", (Object)"BMIML");
	                request.setAttribute("pass_code", (Object)"BMIM1469#");
	                request.setAttribute("tran_id", (Object)("BMIML" + loginForm.getRefNo()));
	                request.setAttribute("amount", (Object)(Double.parseDouble(loginForm.getAmount()) * 100.0));
	                request.setAttribute("charge_code", (Object)'A');
	                request.setAttribute("hash_value", (Object)hash);
	                request.setAttribute("response_url", (Object)CMSConstants.FEDERAL_SUCCESSURL);
	            }
	            else {
	                final String hash = StudentLoginHandler.getInstance().getParameterForPGIOnline(loginForm, student);
	                request.setAttribute("user_code", (Object)"BMCSL");
	                request.setAttribute("pass_code", (Object)"BMCS1469#");
	                request.setAttribute("tran_id", (Object)("BMCSL" + loginForm.getRefNo()));
	                request.setAttribute("amount", (Object)(Double.parseDouble(loginForm.getAmount()) * 100.0));
	                request.setAttribute("charge_code", (Object)'A');
	                request.setAttribute("hash_value", (Object)hash);
	                request.setAttribute("response_url", (Object)CMSConstants.FEDERAL_SUCCESSURL);
	            }
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error in redirectToPGI...", (Throwable)e);
	            throw e;
	        }
	        return mapping.findForward("redirectToPgiStudentLogin");
	    }
	    
	    public ActionForward updatePGIResponseStudentLogin(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final ActionErrors errors = new ActionErrors();
	        final ActionMessages messages = new ActionMessages();
	        try {
	            final HttpSession session = request.getSession();
	            final String studentid = (String)session.getAttribute("studentid");
	            final Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
	            final boolean isUpdated = StudentLoginHandler.getInstance().updateResponse(loginForm, student);
	            System.out.println("***********************************isUpdated====" + isUpdated + "******************************");
	            if (isUpdated) {
	                System.out.println("*************************IsTnxStatusSuccess=============" + loginForm.getIsTnxStatusSuccess() + "********************");
	                if (loginForm.getIsTnxStatusSuccess()) {
	                    final boolean savePayementDetails = StudentLoginHandler.getInstance().savePaymentsDetails(loginForm, request);
	                    if (savePayementDetails) {
	                        final FeeFinancialYear feeFinancialYear = new FeeFinancialYear();
	                        feeFinancialYear.setId((int)FeePaymentHandler.getInstance().getFinancialYearId());
	                        StudentLoginHandler.getInstance().updateBillNo(feeFinancialYear);
	                    }
	                    loginForm.setTxnAmt(String.valueOf(Integer.parseInt(loginForm.getAmount()) / 100));
	                    loginForm.setDisplayPage("paymentsuccess");
	                    messages.add("messages", new ActionMessage("knowledgepro.admission.empty.err.message", (Object)loginForm.getPgiStatus()));
	                    this.saveMessages(request, messages);
	                }
	                else {
	                    loginForm.setPgiStatus("Payment Failure");
	                    errors.add("messages", new ActionMessage("knowledgepro.admission.empty.err.message", (Object)loginForm.getPgiStatus()));
	                    this.saveErrors(request, errors);
	                    loginForm.setDisplayPage("payment");
	                }
	            }
	            else {
	                errors.add("error", new ActionError("knowledgepro.admission.pgi.update.failure"));
	                this.saveErrors(request, errors);
	            }
	        }
	        catch (BusinessException e2) {
	            errors.add("error", new ActionError("knowledgepro.admission.pgi.rejected"));
	            this.saveErrors(request, errors);
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error in updatePGIResponse-AdmissionFormAction...", (Throwable)e);
	            System.out.println("************************ error details in online admission in updatePGIResponse*************************" + e);
	            errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	            this.saveErrors(request, errors);
	        }
	        return mapping.findForward("paymentConformationPage");
	    }
	    
	    public ActionForward getPaymentRecieptStudent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession();
	        final String studentid = (String)session.getAttribute("studentid");
	        final Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
	        final String admAppln = String.valueOf(student.getAdmAppln().getId());
	        final CandidatePGIDetails pgiDetails = StudentLoginHandler.getInstance().getTxnData(admAppln);
	        if (pgiDetails != null && !pgiDetails.toString().isEmpty()) {
	            if (pgiDetails.getTxnAmount() != null && !pgiDetails.getTxnAmount().toString().isEmpty()) {
	                final double amt = pgiDetails.getTxnAmount().doubleValue();
	                loginForm.setTxnAmt(String.valueOf(amt));
	            }
	            if (pgiDetails.getTxnRefNo() != null && !pgiDetails.getTxnRefNo().isEmpty()) {
	                loginForm.setTxnRefNo(pgiDetails.getTxnRefNo());
	            }
	            if (pgiDetails.getTxnDate() != null) {
	                final Date txnDate = pgiDetails.getTxnDate();
	                final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	                final String txnDate2 = format.format(txnDate);
	                loginForm.setTxnDate(txnDate2);
	            }
	        }
	        final FeePayment feePayment = StudentLoginHandler.getInstance().getPaymentRecordForstudent(studentid, loginForm.getClassId());
	        if (feePayment != null && !feePayment.toString().isEmpty()) {
	            loginForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
	            loginForm.setRegNo(student.getRegisterNo());
	            loginForm.setBillNo(feePayment.getBillNo());
	            loginForm.setCourseName(feePayment.getCourse().getName());
	            final Set<FeePaymentDetail> set = (Set<FeePaymentDetail>)feePayment.getFeePaymentDetails();
	            for (final FeePaymentDetail detail : set) {
	                loginForm.setHeading(detail.getFeeHeading().getName());
	            }
	        }
	        if (feePayment != null && !feePayment.toString().isEmpty() && pgiDetails == null) {
	            loginForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
	            loginForm.setRegNo(student.getRegisterNo());
	            loginForm.setBillNo(feePayment.getBillNo());
	            loginForm.setCourseName(feePayment.getCourse().getName());
	            Date paidDate = feePayment.getChallenPrintedDate();
	            final SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
	            final String txnDate3 = format2.format(paidDate);
	            loginForm.setTxnDate(txnDate3);
	            if (feePayment.getTotalFeePaid() != null) {
	                final double amt2 = new BigDecimal(feePayment.getTotalFeePaid().toString()).doubleValue();
	                loginForm.setTxnAmt(String.valueOf(amt2));
	            }
	            loginForm.setTxnRefNo("");
	            loginForm.getTxnAmt();
	            final List<FeePaymentDetail> feePaymentDetail = (List<FeePaymentDetail>)StudentLoginHandler.getInstance().getPaymentDetailsForstudent(studentid, loginForm.getClassId());
	            if (feePaymentDetail != null && !feePaymentDetail.isEmpty()) {
	                final Iterator<FeePaymentDetail> iterator2 = feePaymentDetail.iterator();
	                final LinkedList<BigDecimal> amtpaidList = new LinkedList<BigDecimal>();
	                final LinkedList<Date> dates = new LinkedList<Date>();
	                while (iterator2.hasNext()) {
	                    final FeePaymentDetail detail2 = iterator2.next();
	                    loginForm.setHeading(detail2.getFeeHeading().getName());
	                    amtpaidList.add(detail2.getAmountPaid());
	                    dates.add(detail2.getPaidDate());
	                }
	                final double amtPaid = amtpaidList.getLast().doubleValue();
	                loginForm.setTxnAmt(String.valueOf(amtPaid));
	                paidDate = dates.getLast();
	                final SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
	                final String txnDate4 = format3.format(paidDate);
	                loginForm.setTxnDate(txnDate4);
	            }
	        }
	        return mapping.findForward("printPaymentReciept");
	    }
	    
	    public ActionForward personalDataView(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        try {
	            final String studentid = (String)session.getAttribute("studentid");
	            final Integer courseId = (Integer)session.getAttribute("CurrentClassId");
	            loginForm.setCourseId(String.valueOf(courseId));
	            final Student student = LoginHandler.getInstance().getStudentObj(studentid);
	            final PersonalData personalData = student.getAdmAppln().getPersonalData();
	            final PersonalDataTO personalDataTO = LoginHandler.getInstance().convertBOTO(personalData, loginForm, student);
	            loginForm.setPersonalData(personalDataTO);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("personalDataView");
	    }
	    
	    public ActionForward disciplineAndAchievementView(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        final String type = loginForm.getType();
	        try {
	            session.setAttribute("sLogin", (Object)true);
	            final String studentid = (String)session.getAttribute("studentid");
	            final List<DisciplineAndAchivementTo> dscipToList = (List<DisciplineAndAchivementTo>)LoginHandler.getInstance().getdisciplineAndAchievement(studentid, type);
	            loginForm.setDisciplineAndAchivementToList((List)dscipToList);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("studentDisciplineView");
	    }
	    
	    public ActionForward downloadFile(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        try {
	            String extn = "";
	            final int index = loginForm.getFileName().lastIndexOf(".");
	            extn = loginForm.getFileName().substring(index + 1, loginForm.getFileName().length());
	            response.setContentType(String.valueOf(extn) + "/html");
	            final PrintWriter out = response.getWriter();
	            final String file = loginForm.getFileName();
	            final String filepath = CMSConstants.EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH;
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");
	            final FileInputStream fileInputStream = new FileInputStream(String.valueOf(filepath) + file);
	            int i;
	            while ((i = fileInputStream.read()) != -1) {
	                out.write(i);
	            }
	            fileInputStream.close();
	            out.close();
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error in initStudentEdit...", (Throwable)e);
	            throw e;
	        }
	        request.setAttribute("ACHIEV_IMAGE", (Object)(String.valueOf(CMSConstants.EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH) + "E" + loginForm.getStudentId() + ".jpg" + new Date()));
	        return mapping.findForward("studentDisciplineView");
	    }
	    
	    public ActionForward initDetailMarkConfirmPageClass12(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        StudentLoginAction.log.info((Object)"enter initDetailMarkConfirmPage page...");
	        final LoginForm loginForm = (LoginForm)form;
	        final ActionMessages errors = new ActionMessages();
	        try {
	            final int doctypeId = CMSConstants.CLASS12_DOCTYPEID;
	            final EdnQualificationTO qualTO = loginForm.getEdnQualification();
	            if (qualTO.getDocTypeId() == doctypeId) {
	                final String language = "Language";
	                Map<Integer, String> admsubjectMap = null;
	                Map<Integer, String> admsubjectLangMap = null;
	                if (loginForm.getAdmSubjectMap() == null || loginForm.getAdmSubjectMap().size() == 0) {
	                    admsubjectMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSubjects(qualTO.getDocName(), language);
	                    loginForm.setAdmSubjectMap((Map)admsubjectMap);
	                }
	                if (loginForm.getAdmSubjectLangMap() != null && loginForm.getAdmSubjectLangMap().size() != 0) {
	                    admsubjectLangMap = (Map<Integer, String>)loginForm.getAdmSubjectLangMap();
	                }
	                else {
	                    admsubjectLangMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassLangSubjects(qualTO.getDocName(), language);
	                    loginForm.setAdmSubjectLangMap((Map)admsubjectLangMap);
	                }
	                if (qualTO.getDetailmark() != null) {
	                    loginForm.setDetailMark(qualTO.getDetailmark());
	                }
	                else {
	                    final CandidateMarkTO markTo = new CandidateMarkTO();
	                    final String strValue = "English";
	                    String intKey = null;
	                    for (final Map.Entry entry : admsubjectLangMap.entrySet()) {
	                        if (strValue.equals(entry.getValue())) {
	                            intKey = entry.getKey().toString();
	                            markTo.setSubjectid1(intKey);
	                            break;
	                        }
	                    }
	                    loginForm.setDetailMark(markTo);
	                    qualTO.setDetailmark(markTo);
	                }
	            }
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error initDetailMarkConfirmPage...", (Throwable)e);
	            if (e instanceof ApplicationException) {
	                final String msg = super.handleApplicationException(e);
	                loginForm.setErrorMessage(msg);
	                loginForm.setErrorStack(e.getMessage());
	                System.out.println("************************ error details in online admission in class 12 mark*************************" + e);
	                errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataEdit");
	            }
	            System.out.println("************************ error details in online admission in class 12 mark*************************" + e);
	            errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	            this.saveErrors(request, errors);
	            return mapping.findForward("personalDataEdit");
	        }
	        return mapping.findForward("personalDataDetailMarkPageClass12");
	    }
	    
	    public ActionForward submitDetailMarkConfirmClass12(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        StudentLoginAction.log.info((Object)"enter submitDetailMarkConfirm page...");
	        final LoginForm admForm = (LoginForm)form;
	        final HttpSession session = request.getSession(false);
	        ActionMessages errors = new ActionMessages();
	        try {
	            final CandidateMarkTO detailmark = admForm.getDetailMark();
	            if (detailmark.getTotalMarks() != null && detailmark.getTotalMarks().equalsIgnoreCase("") && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	            }
	            if (detailmark.getTotalMarks() != null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	            }
	            if (detailmark.getTotalMarks() != null && !CommonUtil.isValidDecimal(detailmark.getTotalMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	            }
	            if (detailmark.getTotalObtainedMarks() != null && detailmark.getTotalObtainedMarks().equalsIgnoreCase("") && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	            }
	            if (detailmark.getTotalObtainedMarks() != null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	            }
	            if (detailmark.getTotalObtainedMarks() != null && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	            }
	            if (errors != null && !errors.isEmpty()) {
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataDetailMarkPageClass12");
	            }
	            errors = validateMarksClass12(detailmark);
	            if (errors != null && !errors.isEmpty()) {
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataDetailMarkPageClass12");
	            }
	            final List<EdnQualificationTO> qualifications = (List<EdnQualificationTO>)admForm.getApplicantDetails().getEdnQualificationList();
	            if (qualifications != null) {
	                for (final EdnQualificationTO qualTO : qualifications) {
	                    qualTO.setDetailmark(detailmark);
	                }
	            }
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error in submitDetailMarkConfirm page...", (Throwable)e);
	            if (e instanceof ApplicationException) {
	                final String msg = super.handleApplicationException(e);
	                admForm.setErrorMessage(msg);
	                admForm.setErrorStack(e.getMessage());
	                System.out.println("************************ error details in online admission in class 12 submit mark*************************" + e);
	                errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataDetailMarkPageClass12");
	            }
	            System.out.println("************************ error details in online admission in submit class 12 mark*************************" + e);
	            errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	            this.saveErrors(request, errors);
	            return mapping.findForward("personalDataDetailMarkPageClass12");
	        }
	        StudentLoginAction.log.info((Object)"enter ssubmitDetailMarkConfirm page...");
	        admForm.setDisplayPage("educationaldetail");
	        return mapping.findForward("personalDataEdit");
	    }
	    
	    public ActionForward initDetailMarkConfirmPageDegree(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        StudentLoginAction.log.info((Object)"enter initDetailMarkConfirmPage page...");
	        final LoginForm admForm = (LoginForm)form;
	        final ActionMessages errors = new ActionMessages();
	        try {
	            final HttpSession session = request.getSession(false);
	            final int index = -1;
	            final int doctypeId = CMSConstants.DEGREE_DOCTYPEID;
	            final EdnQualificationTO qualTO = admForm.getEdnQualification();
	            if (qualTO != null && qualTO.getDocTypeId() == doctypeId) {
	                admForm.setPatternofStudy(qualTO.getUgPattern());
	                final String Core = "Core";
	                final String Compl = "Complimentary";
	                final String Common = "Common";
	                final String Open = "Open";
	                Map<Integer, String> admCoreMap = null;
	                Map<Integer, String> admComplMap = null;
	                Map<Integer, String> admCommonMap = null;
	                Map<Integer, String> admopenMap = null;
	                Map<Integer, String> admSubMap = null;
	                if (admForm.getAdmCoreMap() == null || admForm.getAdmCoreMap().size() == 0) {
	                    admCoreMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(), Core);
	                    admForm.setAdmCoreMap((Map)admCoreMap);
	                }
	                if (admForm.getAdmComplMap() == null || admForm.getAdmComplMap().size() == 0) {
	                    admComplMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(), Compl);
	                    admForm.setAdmComplMap((Map)admComplMap);
	                }
	                if (admForm.getAdmCommonMap() != null && admForm.getAdmCommonMap().size() != 0) {
	                    admCommonMap = (Map<Integer, String>)admForm.getAdmCommonMap();
	                }
	                else {
	                    admCommonMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(), Common);
	                    admForm.setAdmCommonMap((Map)admCommonMap);
	                }
	                if (admForm.getAdmMainMap() == null || admForm.getAdmMainMap().size() == 0) {
	                    admopenMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub(qualTO.getDocName(), Open);
	                    admForm.setAdmMainMap((Map)admopenMap);
	                }
	                if (admForm.getAdmSubMap() == null || admForm.getAdmSubMap().size() == 0) {
	                    admSubMap = (Map<Integer, String>)AdmissionFormHandler.getInstance().get12thclassSub1(qualTO.getDocName(), Common);
	                    admForm.setAdmSubMap((Map)admSubMap);
	                }
	                if (qualTO.getDetailmark() != null) {
	                    admForm.setDetailMark(qualTO.getDetailmark());
	                }
	                else {
	                    final CandidateMarkTO markTo = new CandidateMarkTO();
	                    final String strValue = "English";
	                    String intKey = null;
	                    for (final Map.Entry entry : admCommonMap.entrySet()) {
	                        if (strValue.equals(entry.getValue())) {
	                            intKey = entry.getKey().toString();
	                            markTo.setSubjectid6(intKey);
	                            markTo.setSubjectid16(intKey);
	                            break;
	                        }
	                    }
	                    admForm.setDetailMark(markTo);
	                    qualTO.setDetailmark(markTo);
	                }
	            }
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error initDetailMarkConfirmPage...", (Throwable)e);
	            if (e instanceof ApplicationException) {
	                final String msg = super.handleApplicationException(e);
	                admForm.setErrorMessage(msg);
	                admForm.setErrorStack(e.getMessage());
	                System.out.println("************************ error details in online admission in degree mark*************************" + e);
	                errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataEdit");
	            }
	            System.out.println("************************ error details in online admission in degree mark************************" + e);
	            errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	            this.saveErrors(request, errors);
	            return mapping.findForward("personalDataEdit");
	        }
	        return mapping.findForward("personalDataDetailMarkPageClassDegree");
	    }
	    
	    public ActionForward submitDetailMarkConfirmDegree(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        StudentLoginAction.log.info((Object)"enter submitDetailMarkConfirm page...");
	        final LoginForm admForm = (LoginForm)form;
	        final HttpSession session = request.getSession(false);
	        ActionMessages errors = new ActionMessages();
	        try {
	            final CandidateMarkTO detailmark = admForm.getDetailMark();
	            errors = new ActionMessages();
	            if (admForm.getPatternofStudy().equalsIgnoreCase("CBCSS") || admForm.getPatternofStudy().equalsIgnoreCase("CBCSS NEW")) {
	                if (detailmark.getTotalMarksCGPA() != null && detailmark.getTotalMarksCGPA().equalsIgnoreCase("") && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalMarksCGPA() != null && StringUtils.isEmpty(detailmark.getTotalMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarksCGPA()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalMarksCGPA() != null && !CommonUtil.isValidDecimal(detailmark.getTotalMarksCGPA()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalObtainedMarksCGPA() != null && detailmark.getTotalObtainedMarksCGPA().equalsIgnoreCase("") && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalObtainedMarksCGPA() != null && StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarksCGPA()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalObtainedMarksCGPA() != null && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarksCGPA()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	            }
	            else {
	                if (detailmark.getTotalMarks() != null && detailmark.getTotalMarks().equalsIgnoreCase("") && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalMarks() != null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalMarks() != null && !CommonUtil.isValidDecimal(detailmark.getTotalMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalObtainedMarks() != null && detailmark.getTotalObtainedMarks().equalsIgnoreCase("") && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalObtainedMarks() != null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                if (detailmark.getTotalObtainedMarks() != null && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks()) && errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	            }
	            if (errors != null && !errors.isEmpty()) {
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataDetailMarkPageClassDegree");
	            }
	            errors = validateMarksDegree(detailmark, admForm.getPatternofStudy());
	            if (errors != null && !errors.isEmpty()) {
	                this.saveErrors(request, errors);
	                return mapping.findForward("personalDataDetailMarkPageClassDegree");
	            }
	            if (admForm.getEdnQualification() != null) {
	                final EdnQualificationTO qualTO = admForm.getEdnQualification();
	                qualTO.setDetailmark(detailmark);
	                qualTO.setUgPattern(admForm.getPatternofStudy());
	            }
	        }
	        catch (Exception e) {
	            StudentLoginAction.log.error((Object)"error in submitDetailMarkConfirm page...", (Throwable)e);
	            if (e instanceof ApplicationException) {
	                final String msg = super.handleApplicationException(e);
	                admForm.setErrorMessage(msg);
	                admForm.setErrorStack(e.getMessage());
	                System.out.println("************************ error details in online admission in submit degree*************************" + e);
	                errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	                this.saveErrors(request, errors);
	                return mapping.findForward("onlineAppBasicPage");
	            }
	            System.out.println("************************ error details in online admission in submit degree *************************" + e);
	            errors.add("knowledgepro.admission.boardDetails.duplicateEntry", (ActionMessage)new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", (Object)"Error was occured, please login and enter details again"));
	            this.saveErrors(request, errors);
	            return mapping.findForward("onlineAppBasicPage");
	        }
	        StudentLoginAction.log.info((Object)"enter ssubmitDetailMarkConfirm page...");
	        return mapping.findForward("personalDataEdit");
	    }
	    
	    public static ActionMessages validateMarksClass12(final CandidateMarkTO detailmark) {
	        StudentLoginAction.log.info((Object)"enter validateMarks...");
	        final ActionMessages errors = new ActionMessages();
	        if (detailmark != null) {
	            int totalSubjectCount = 0;
	            final Set<String> dupSet = new HashSet<String>();
	            if (detailmark.getSubjectid1() != null && !detailmark.getSubjectid1().equalsIgnoreCase("") && detailmark.getSubject1ObtainedMarks() != null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject1TotalMarks() != null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid1());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid1() != null && !detailmark.getSubjectid1().equalsIgnoreCase("")) || (detailmark.getSubject1ObtainedMarks() != null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject1TotalMarks() != null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid2() != null && !detailmark.getSubjectid2().equalsIgnoreCase("") && detailmark.getSubject2ObtainedMarks() != null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject2TotalMarks() != null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid2());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid2() != null && !detailmark.getSubjectid2().equalsIgnoreCase("")) || (detailmark.getSubject2ObtainedMarks() != null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject2TotalMarks() != null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid3() != null && !detailmark.getSubjectid3().equalsIgnoreCase("") && detailmark.getSubject3ObtainedMarks() != null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject3TotalMarks() != null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid3());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid3() != null && !detailmark.getSubjectid3().equalsIgnoreCase("")) || (detailmark.getSubject3ObtainedMarks() != null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject3TotalMarks() != null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid4() != null && !detailmark.getSubjectid4().equalsIgnoreCase("") && detailmark.getSubject4ObtainedMarks() != null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject4TotalMarks() != null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid4());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid4() != null && !detailmark.getSubjectid4().equalsIgnoreCase("")) || (detailmark.getSubject4ObtainedMarks() != null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject4TotalMarks() != null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid5() != null && !detailmark.getSubjectid5().equalsIgnoreCase("") && detailmark.getSubject5ObtainedMarks() != null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject5TotalMarks() != null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid5());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid5() != null && !detailmark.getSubjectid5().equalsIgnoreCase("")) || (detailmark.getSubject5ObtainedMarks() != null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject5TotalMarks() != null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid6() != null && !detailmark.getSubjectid6().equalsIgnoreCase("") && detailmark.getSubject6ObtainedMarks() != null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject6TotalMarks() != null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid6());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid6() != null && !detailmark.getSubjectid6().equalsIgnoreCase("")) || (detailmark.getSubject6ObtainedMarks() != null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject6TotalMarks() != null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid7() != null && !detailmark.getSubjectid7().equalsIgnoreCase("") && detailmark.getSubject7ObtainedMarks() != null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject7TotalMarks() != null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid7());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid7() != null && !detailmark.getSubjectid7().equalsIgnoreCase("")) || (detailmark.getSubject7ObtainedMarks() != null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject7TotalMarks() != null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getSubjectid8() != null && !detailmark.getSubjectid8().equalsIgnoreCase("") && detailmark.getSubject8ObtainedMarks() != null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject8TotalMarks() != null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase("")) {
	                dupSet.add(detailmark.getSubjectid8());
	                ++totalSubjectCount;
	            }
	            else if ((detailmark.getSubjectid8() != null && !detailmark.getSubjectid8().equalsIgnoreCase("")) || (detailmark.getSubject8ObtainedMarks() != null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject8TotalMarks() != null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase(""))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (totalSubjectCount != dupSet.size()) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"Duplicate Subjects should not select."));
	                return errors;
	            }
	            if (detailmark.getSubject1ObtainedMarks() != null && !detailmark.getSubject1ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject2ObtainedMarks() != null && !detailmark.getSubject2ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject3ObtainedMarks() != null && !detailmark.getSubject3ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject4ObtainedMarks() != null && !detailmark.getSubject4ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject5ObtainedMarks() != null && !detailmark.getSubject5ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject6ObtainedMarks() != null && !detailmark.getSubject6ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject7ObtainedMarks() != null && !detailmark.getSubject7ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject8ObtainedMarks() != null && !detailmark.getSubject8ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.valid", error);
	                return errors;
	            }
	            if (detailmark.getSubject9ObtainedMarks() != null && !detailmark.getSubject9ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?")) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                return errors;
	            }
	            if (detailmark.isSubject1Mandatory() && (detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getTotalMarks() != null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks())) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.getTotalObtainedMarks() != null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if ((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().equalsIgnoreCase("."))) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().equalsIgnoreCase(".")))) {
	                if (errors.get("knowledgepro.admission.detailTotalmark.greater.reqd") != null && !errors.get("knowledgepro.admission.detailTotalmark.greater.reqd").hasNext()) {
	                    final ActionMessage error = new ActionMessage("knowledgepro.admission.detailTotalmark.greater.reqd");
	                    errors.add("knowledgepro.admission.detailTotalmark.greater.reqd", error);
	                }
	                return errors;
	            }
	            if ((((detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks())) && detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) || ((detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks())) && detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) || ((detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks())) && detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) || ((detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks())) && detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) || ((detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks())) && detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) || ((detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks())) && detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) || ((detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks())) && detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) || ((detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks())) && detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) || ((detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks())) && detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) || ((detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks())) && detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) || ((detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks())) && detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) || ((detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks())) && detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) || ((detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks())) && detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) || ((detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks())) && detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) || ((detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks())) && detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) || ((detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks())) && detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) || ((detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks())) && detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) || ((detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks())) && detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) || ((detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks())) && detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) || ((detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks())) && detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) || ((detailmark.getTotalMarks() == null || StringUtils.isEmpty(detailmark.getTotalMarks())) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                errors.add("admissionFormForm.education.obtainMark.larger", error);
	            }
	            if (((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Float.parseFloat(detailmark.getSubject1TotalMarks()) < Float.parseFloat(detailmark.getSubject1ObtainedMarks())) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Float.parseFloat(detailmark.getSubject2TotalMarks()) < Float.parseFloat(detailmark.getSubject2ObtainedMarks())) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Float.parseFloat(detailmark.getSubject3TotalMarks()) < Float.parseFloat(detailmark.getSubject3ObtainedMarks())) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Float.parseFloat(detailmark.getSubject4TotalMarks()) < Float.parseFloat(detailmark.getSubject4ObtainedMarks())) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) && Float.parseFloat(detailmark.getSubject5TotalMarks()) < Float.parseFloat(detailmark.getSubject5ObtainedMarks())) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) && Float.parseFloat(detailmark.getSubject6TotalMarks()) < Float.parseFloat(detailmark.getSubject6ObtainedMarks())) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) && Float.parseFloat(detailmark.getSubject7TotalMarks()) < Float.parseFloat(detailmark.getSubject7ObtainedMarks())) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) && Float.parseFloat(detailmark.getSubject8TotalMarks()) < Float.parseFloat(detailmark.getSubject8ObtainedMarks())) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) && Float.parseFloat(detailmark.getSubject9TotalMarks()) < Float.parseFloat(detailmark.getSubject9ObtainedMarks())) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) && Float.parseFloat(detailmark.getSubject10TotalMarks()) < Float.parseFloat(detailmark.getSubject10ObtainedMarks())) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) && Float.parseFloat(detailmark.getSubject11TotalMarks()) < Float.parseFloat(detailmark.getSubject11ObtainedMarks())) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) && Float.parseFloat(detailmark.getSubject12TotalMarks()) < Float.parseFloat(detailmark.getSubject12ObtainedMarks())) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) && Float.parseFloat(detailmark.getSubject13TotalMarks()) < Float.parseFloat(detailmark.getSubject13ObtainedMarks())) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) && Float.parseFloat(detailmark.getSubject14TotalMarks()) < Float.parseFloat(detailmark.getSubject14ObtainedMarks())) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) && Float.parseFloat(detailmark.getSubject15TotalMarks()) < Float.parseFloat(detailmark.getSubject15ObtainedMarks())) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) && Float.parseFloat(detailmark.getSubject16TotalMarks()) < Float.parseFloat(detailmark.getSubject16ObtainedMarks())) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) && Float.parseFloat(detailmark.getSubject17TotalMarks()) < Float.parseFloat(detailmark.getSubject17ObtainedMarks())) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) && Float.parseFloat(detailmark.getSubject18TotalMarks()) < Float.parseFloat(detailmark.getSubject18ObtainedMarks())) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) && Float.parseFloat(detailmark.getSubject19TotalMarks()) < Float.parseFloat(detailmark.getSubject19ObtainedMarks())) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) && Float.parseFloat(detailmark.getSubject20TotalMarks()) < Float.parseFloat(detailmark.getSubject20ObtainedMarks())) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Float.parseFloat(detailmark.getTotalMarks()) < Float.parseFloat(detailmark.getTotalObtainedMarks()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                final ActionMessage error = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                errors.add("admissionFormForm.education.obtainMark.larger", error);
	            }
	            final int count = 20;
	            int compareCount = 0;
	            if (detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject19ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (compareCount == count) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"Please fill the Marks.."));
	            }
	        }
	        StudentLoginAction.log.info((Object)"exit validateMarks...");
	        return errors;
	    }
	    
	    public static ActionMessages validateMarksDegree(final CandidateMarkTO detailmark, final String CBCSS) {
	        StudentLoginAction.log.info((Object)"enter validateMarks...");
	        final ActionMessages errors = new ActionMessages();
	        if (detailmark != null) {
	            if (CBCSS.equalsIgnoreCase("CBCSS") || CBCSS.equalsIgnoreCase("CBCSS NEW")) {
	                int totalSubjectCount = 0;
	                final Set<String> dupSet = new HashSet<String>();
	                if (detailmark.getSubjectid1() != null && !detailmark.getSubjectid1().equalsIgnoreCase("") && detailmark.getSubject1ObtainedMarks() != null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject1TotalMarks() != null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase("") && detailmark.getSubject1Credit() != null && !detailmark.getSubject1Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid1());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid1() != null && !detailmark.getSubjectid1().equalsIgnoreCase("")) || (detailmark.getSubject1ObtainedMarks() != null && !detailmark.getSubject1ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject1TotalMarks() != null && !detailmark.getSubject1TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject1Credit() != null && !detailmark.getSubject1Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid2() != null && !detailmark.getSubjectid2().equalsIgnoreCase("") && detailmark.getSubject2ObtainedMarks() != null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject2TotalMarks() != null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase("") && detailmark.getSubject2Credit() != null && !detailmark.getSubject2Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid2());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid2() != null && !detailmark.getSubjectid2().equalsIgnoreCase("")) || (detailmark.getSubject2ObtainedMarks() != null && !detailmark.getSubject2ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject2TotalMarks() != null && !detailmark.getSubject2TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject2Credit() != null && !detailmark.getSubject2Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid3() != null && !detailmark.getSubjectid3().equalsIgnoreCase("") && detailmark.getSubject3ObtainedMarks() != null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject3TotalMarks() != null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase("") && detailmark.getSubject3Credit() != null && !detailmark.getSubject3Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid3());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid3() != null && !detailmark.getSubjectid3().equalsIgnoreCase("")) || (detailmark.getSubject3ObtainedMarks() != null && !detailmark.getSubject3ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject3TotalMarks() != null && !detailmark.getSubject3TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject3Credit() != null && !detailmark.getSubject3Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid4() != null && !detailmark.getSubjectid4().equalsIgnoreCase("") && detailmark.getSubject4ObtainedMarks() != null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject4TotalMarks() != null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase("") && detailmark.getSubject4Credit() != null && !detailmark.getSubject4Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid4());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid4() != null && !detailmark.getSubjectid4().equalsIgnoreCase("")) || (detailmark.getSubject4ObtainedMarks() != null && !detailmark.getSubject4ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject4TotalMarks() != null && !detailmark.getSubject4TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject4Credit() != null && !detailmark.getSubject4Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid5() != null && !detailmark.getSubjectid5().equalsIgnoreCase("") && detailmark.getSubject5ObtainedMarks() != null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject5TotalMarks() != null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase("") && detailmark.getSubject5Credit() != null && !detailmark.getSubject5Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid5());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid5() != null && !detailmark.getSubjectid5().equalsIgnoreCase("")) || (detailmark.getSubject5ObtainedMarks() != null && !detailmark.getSubject5ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject5TotalMarks() != null && !detailmark.getSubject5TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject5Credit() != null && !detailmark.getSubject5Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid6() != null && !detailmark.getSubjectid6().equalsIgnoreCase("") && detailmark.getSubject6ObtainedMarks() != null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject6TotalMarks() != null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase("") && detailmark.getSubject6Credit() != null && !detailmark.getSubject6Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid6());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid6() != null && !detailmark.getSubjectid6().equalsIgnoreCase("")) || (detailmark.getSubject6ObtainedMarks() != null && !detailmark.getSubject6ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject6TotalMarks() != null && !detailmark.getSubject6TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject6Credit() != null && !detailmark.getSubject6Credit().equalsIgnoreCase(""))) {
	                    return errors;
	                }
	                if (detailmark.getSubjectid7() != null && !detailmark.getSubjectid7().equalsIgnoreCase("") && detailmark.getSubject7ObtainedMarks() != null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject7TotalMarks() != null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase("") && detailmark.getSubject7Credit() != null && !detailmark.getSubject7Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid7());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid7() != null && !detailmark.getSubjectid7().equalsIgnoreCase("")) || (detailmark.getSubject7ObtainedMarks() != null && !detailmark.getSubject7ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject7TotalMarks() != null && !detailmark.getSubject7TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject7Credit() != null && !detailmark.getSubject7Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid8() != null && !detailmark.getSubjectid8().equalsIgnoreCase("") && detailmark.getSubject8ObtainedMarks() != null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject8TotalMarks() != null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase("") && detailmark.getSubject8Credit() != null && !detailmark.getSubject8Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid8());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid8() != null && !detailmark.getSubjectid8().equalsIgnoreCase("")) || (detailmark.getSubject8ObtainedMarks() != null && !detailmark.getSubject8ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject8TotalMarks() != null && !detailmark.getSubject8TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject8Credit() != null && !detailmark.getSubject8Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid9() != null && !detailmark.getSubjectid9().equalsIgnoreCase("") && detailmark.getSubject9ObtainedMarks() != null && !detailmark.getSubject9ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject9TotalMarks() != null && !detailmark.getSubject9TotalMarks().equalsIgnoreCase("") && detailmark.getSubject9Credit() != null && !detailmark.getSubject9Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid9());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid9() != null && !detailmark.getSubjectid9().equalsIgnoreCase("")) || (detailmark.getSubject9ObtainedMarks() != null && !detailmark.getSubject9ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject9TotalMarks() != null && !detailmark.getSubject9TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject9Credit() != null && !detailmark.getSubject9Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid10() != null && !detailmark.getSubjectid10().equalsIgnoreCase("") && detailmark.getSubject10ObtainedMarks() != null && !detailmark.getSubject10ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject10TotalMarks() != null && !detailmark.getSubject10TotalMarks().equalsIgnoreCase("") && detailmark.getSubject10Credit() != null && !detailmark.getSubject10Credit().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid10());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid10() != null && !detailmark.getSubjectid10().equalsIgnoreCase("")) || (detailmark.getSubject10ObtainedMarks() != null && !detailmark.getSubject10ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject10TotalMarks() != null && !detailmark.getSubject10TotalMarks().equalsIgnoreCase("")) || (detailmark.getSubject10Credit() != null && !detailmark.getSubject10Credit().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (totalSubjectCount != dupSet.size()) {
	                    errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"Duplicate Subjects should not select."));
	                    return errors;
	                }
	            }
	            else {
	                int totalSubjectCount = 0;
	                final Set<String> dupSet = new HashSet<String>();
	                if (detailmark.getSubjectid11() != null && !detailmark.getSubjectid11().equalsIgnoreCase("") && detailmark.getSubject11ObtainedMarks() != null && !detailmark.getSubject11ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject11TotalMarks() != null && !detailmark.getSubject11TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid11());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid11() != null && !detailmark.getSubjectid11().equalsIgnoreCase("")) || (detailmark.getSubject11ObtainedMarks() != null && !detailmark.getSubject11ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject11TotalMarks() != null && !detailmark.getSubject11TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid12() != null && !detailmark.getSubjectid12().equalsIgnoreCase("") && detailmark.getSubject12ObtainedMarks() != null && !detailmark.getSubject12ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject12TotalMarks() != null && !detailmark.getSubject12TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid12());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid12() != null && !detailmark.getSubjectid12().equalsIgnoreCase("")) || (detailmark.getSubject12ObtainedMarks() != null && !detailmark.getSubject12ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject12TotalMarks() != null && !detailmark.getSubject12TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid13() != null && !detailmark.getSubjectid13().equalsIgnoreCase("") && detailmark.getSubject13ObtainedMarks() != null && !detailmark.getSubject13ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject13TotalMarks() != null && !detailmark.getSubject13TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid13());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid13() != null && !detailmark.getSubjectid13().equalsIgnoreCase("")) || (detailmark.getSubject13ObtainedMarks() != null && !detailmark.getSubject13ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject13TotalMarks() != null && !detailmark.getSubject13TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid14() != null && !detailmark.getSubjectid14().equalsIgnoreCase("") && detailmark.getSubject14ObtainedMarks() != null && !detailmark.getSubject14ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject14TotalMarks() != null && !detailmark.getSubject14TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid14());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid14() != null && !detailmark.getSubjectid14().equalsIgnoreCase("")) || (detailmark.getSubject14ObtainedMarks() != null && !detailmark.getSubject14ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject14TotalMarks() != null && !detailmark.getSubject14TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid15() != null && !detailmark.getSubjectid15().equalsIgnoreCase("") && detailmark.getSubject15ObtainedMarks() != null && !detailmark.getSubject15ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject15TotalMarks() != null && !detailmark.getSubject15TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid15());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid15() != null && !detailmark.getSubjectid15().equalsIgnoreCase("")) || (detailmark.getSubject15ObtainedMarks() != null && !detailmark.getSubject15ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject15TotalMarks() != null && !detailmark.getSubject15TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid16() != null && !detailmark.getSubjectid16().equalsIgnoreCase("") && detailmark.getSubject16ObtainedMarks() != null && !detailmark.getSubject16ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject16TotalMarks() != null && !detailmark.getSubject16TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid16());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid16() != null && !detailmark.getSubjectid16().equalsIgnoreCase("")) || (detailmark.getSubject16ObtainedMarks() != null && !detailmark.getSubject16ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject16TotalMarks() != null && !detailmark.getSubject16TotalMarks().equalsIgnoreCase(""))) {
	                    return errors;
	                }
	                if (detailmark.getSubjectid17() != null && !detailmark.getSubjectid17().equalsIgnoreCase("") && detailmark.getSubject17ObtainedMarks() != null && !detailmark.getSubject17ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject17TotalMarks() != null && !detailmark.getSubject17TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid17());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid17() != null && !detailmark.getSubjectid17().equalsIgnoreCase("")) || (detailmark.getSubject17ObtainedMarks() != null && !detailmark.getSubject17ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject17TotalMarks() != null && !detailmark.getSubject17TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid18() != null && !detailmark.getSubjectid18().equalsIgnoreCase("") && detailmark.getSubject18ObtainedMarks() != null && !detailmark.getSubject18ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject18TotalMarks() != null && !detailmark.getSubject18TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid18());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid18() != null && !detailmark.getSubjectid18().equalsIgnoreCase("")) || (detailmark.getSubject18ObtainedMarks() != null && !detailmark.getSubject18ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject18TotalMarks() != null && !detailmark.getSubject18TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid19() != null && !detailmark.getSubjectid19().equalsIgnoreCase("") && detailmark.getSubject19ObtainedMarks() != null && !detailmark.getSubject19ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject19TotalMarks() != null && !detailmark.getSubject19TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid19());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid19() != null && !detailmark.getSubjectid19().equalsIgnoreCase("")) || (detailmark.getSubject19ObtainedMarks() != null && !detailmark.getSubject19ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject19TotalMarks() != null && !detailmark.getSubject19TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (detailmark.getSubjectid20() != null && !detailmark.getSubjectid20().equalsIgnoreCase("") && detailmark.getSubject20ObtainedMarks() != null && !detailmark.getSubject20ObtainedMarks().equalsIgnoreCase("") && detailmark.getSubject20TotalMarks() != null && !detailmark.getSubject20TotalMarks().equalsIgnoreCase("")) {
	                    dupSet.add(detailmark.getSubjectid20());
	                    ++totalSubjectCount;
	                }
	                else if ((detailmark.getSubjectid20() != null && !detailmark.getSubjectid20().equalsIgnoreCase("")) || (detailmark.getSubject20ObtainedMarks() != null && !detailmark.getSubject20ObtainedMarks().equalsIgnoreCase("")) || (detailmark.getSubject20TotalMarks() != null && !detailmark.getSubject20TotalMarks().equalsIgnoreCase(""))) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                    }
	                    return errors;
	                }
	                if (totalSubjectCount != dupSet.size()) {
	                    errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"Duplicate Subjects should not select."));
	                    return errors;
	                }
	            }
	            if (CBCSS.equalsIgnoreCase("CBCSS") || CBCSS.equalsIgnoreCase("CBCSS NEW")) {
	                if (detailmark.getSubjectid1() != null && !detailmark.getSubjectid1().equalsIgnoreCase("") && (detailmark.getSubjectid1().equalsIgnoreCase("0") || detailmark.getSubjectid1().equalsIgnoreCase("0") || detailmark.getSubjectid1().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther1() != null && detailmark.getSubjectOther1().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther1() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid2() != null && !detailmark.getSubjectid2().equalsIgnoreCase("") && (detailmark.getSubjectid2().equalsIgnoreCase("0") || detailmark.getSubjectid2().equalsIgnoreCase("0") || detailmark.getSubjectid2().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther2() != null && detailmark.getSubjectOther2().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther2() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid3() != null && !detailmark.getSubjectid3().equalsIgnoreCase("") && (detailmark.getSubjectid3().equalsIgnoreCase("0") || detailmark.getSubjectid3().equalsIgnoreCase("0") || detailmark.getSubjectid3().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther3() != null && detailmark.getSubjectOther3().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther3() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid4() != null && !detailmark.getSubjectid4().equalsIgnoreCase("") && (detailmark.getSubjectid4().equalsIgnoreCase("0") || detailmark.getSubjectid4().equalsIgnoreCase("0") || detailmark.getSubjectid4().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther4() != null && detailmark.getSubjectOther4().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther4() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid5() != null && !detailmark.getSubjectid5().equalsIgnoreCase("") && (detailmark.getSubjectid5().equalsIgnoreCase("0") || detailmark.getSubjectid5().equalsIgnoreCase("0") || detailmark.getSubjectid5().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther5() != null && detailmark.getSubjectOther5().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther5() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid6() != null && !detailmark.getSubjectid6().equalsIgnoreCase("") && (detailmark.getSubjectid6().equalsIgnoreCase("0") || detailmark.getSubjectid6().equalsIgnoreCase("0") || detailmark.getSubjectid6().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther6() != null && detailmark.getSubjectOther6().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther6() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid7() != null && !detailmark.getSubjectid7().equalsIgnoreCase("") && (detailmark.getSubjectid7().equalsIgnoreCase("0") || detailmark.getSubjectid7().equalsIgnoreCase("0") || detailmark.getSubjectid7().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther7() != null && detailmark.getSubjectOther7().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther7() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid8() != null && !detailmark.getSubjectid8().equalsIgnoreCase("") && (detailmark.getSubjectid8().equalsIgnoreCase("0") || detailmark.getSubjectid8().equalsIgnoreCase("0") || detailmark.getSubjectid8().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther8() != null && detailmark.getSubjectOther8().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther8() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid9() != null && !detailmark.getSubjectid9().equalsIgnoreCase("") && (detailmark.getSubjectid9().equalsIgnoreCase("0") || detailmark.getSubjectid9().equalsIgnoreCase("0") || detailmark.getSubjectid9().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther9() != null && detailmark.getSubjectOther9().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther9() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid10() != null && !detailmark.getSubjectid10().equalsIgnoreCase("") && (detailmark.getSubjectid10().equalsIgnoreCase("0") || detailmark.getSubjectid10().equalsIgnoreCase("0") || detailmark.getSubjectid10().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther10() != null && detailmark.getSubjectOther10().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther10() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	            }
	            else {
	                if (detailmark.getSubjectid11() != null && !detailmark.getSubjectid11().equalsIgnoreCase("") && (detailmark.getSubjectid11().equalsIgnoreCase("0") || detailmark.getSubjectid11().equalsIgnoreCase("0") || detailmark.getSubjectid11().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther11() != null && detailmark.getSubjectOther11().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther11() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid12() != null && !detailmark.getSubjectid12().equalsIgnoreCase("") && (detailmark.getSubjectid12().equalsIgnoreCase("0") || detailmark.getSubjectid12().equalsIgnoreCase("0") || detailmark.getSubjectid12().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther12() != null && detailmark.getSubjectOther12().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther12() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid13() != null && !detailmark.getSubjectid13().equalsIgnoreCase("") && (detailmark.getSubjectid13().equalsIgnoreCase("0") || detailmark.getSubjectid13().equalsIgnoreCase("0") || detailmark.getSubjectid13().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther13() != null && detailmark.getSubjectOther13().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther13() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid14() != null && !detailmark.getSubjectid14().equalsIgnoreCase("") && (detailmark.getSubjectid14().equalsIgnoreCase("0") || detailmark.getSubjectid14().equalsIgnoreCase("0") || detailmark.getSubjectid14().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther14() != null && detailmark.getSubjectOther14().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther14() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid15() != null && !detailmark.getSubjectid15().equalsIgnoreCase("") && (detailmark.getSubjectid15().equalsIgnoreCase("0") || detailmark.getSubjectid15().equalsIgnoreCase("0") || detailmark.getSubjectid15().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther15() != null && detailmark.getSubjectOther15().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther15() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid16() != null && !detailmark.getSubjectid16().equalsIgnoreCase("") && (detailmark.getSubjectid16().equalsIgnoreCase("0") || detailmark.getSubjectid16().equalsIgnoreCase("0") || detailmark.getSubjectid16().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther16() != null && detailmark.getSubjectOther16().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther16() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid17() != null && !detailmark.getSubjectid17().equalsIgnoreCase("") && (detailmark.getSubjectid17().equalsIgnoreCase("0") || detailmark.getSubjectid17().equalsIgnoreCase("0") || detailmark.getSubjectid17().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther17() != null && detailmark.getSubjectOther17().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther17() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid18() != null && !detailmark.getSubjectid18().equalsIgnoreCase("") && (detailmark.getSubjectid18().equalsIgnoreCase("0") || detailmark.getSubjectid18().equalsIgnoreCase("0") || detailmark.getSubjectid18().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther18() != null && detailmark.getSubjectOther18().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther18() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid19() != null && !detailmark.getSubjectid19().equalsIgnoreCase("") && (detailmark.getSubjectid19().equalsIgnoreCase("0") || detailmark.getSubjectid19().equalsIgnoreCase("0") || detailmark.getSubjectid19().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther19() != null && detailmark.getSubjectOther19().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther19() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	                if (detailmark.getSubjectid20() != null && !detailmark.getSubjectid20().equalsIgnoreCase("") && (detailmark.getSubjectid20().equalsIgnoreCase("0") || detailmark.getSubjectid20().equalsIgnoreCase("0") || detailmark.getSubjectid20().equalsIgnoreCase("0"))) {
	                    if (detailmark.getSubjectOther20() != null && detailmark.getSubjectOther20().equalsIgnoreCase("")) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                    if (detailmark.getSubjectOther20() == null) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                        return errors;
	                    }
	                }
	            }
	            if (CBCSS.equalsIgnoreCase("CBCSS") || CBCSS.equalsIgnoreCase("CBCSS NEW")) {
	                if (detailmark.getSubject1ObtainedMarks() != null && !detailmark.getSubject1ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject2ObtainedMarks() != null && !detailmark.getSubject2ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject3ObtainedMarks() != null && !detailmark.getSubject3ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject4ObtainedMarks() != null && !detailmark.getSubject4ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject5ObtainedMarks() != null && !detailmark.getSubject5ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject6ObtainedMarks() != null && !detailmark.getSubject6ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject7ObtainedMarks() != null && !detailmark.getSubject7ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject8ObtainedMarks() != null && !detailmark.getSubject8ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.valid", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject9ObtainedMarks() != null && !detailmark.getSubject9ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject10ObtainedMarks() != null && !detailmark.getSubject10ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getTotalMarksCGPA() != null && StringUtils.isEmpty(detailmark.getTotalMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarksCGPA())) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    }
	                    return errors;
	                }
	                if (detailmark.getTotalObtainedMarksCGPA() != null && StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarksCGPA())) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    }
	                    return errors;
	                }
	            }
	            else {
	                if (detailmark.getSubject11ObtainedMarks() != null && !detailmark.getSubject11ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject12ObtainedMarks() != null && !detailmark.getSubject12ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject13ObtainedMarks() != null && !detailmark.getSubject13ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject14ObtainedMarks() != null && !detailmark.getSubject14ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject15ObtainedMarks() != null && !detailmark.getSubject15ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject16ObtainedMarks() != null && !detailmark.getSubject16ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject17ObtainedMarks() != null && !detailmark.getSubject17ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject18ObtainedMarks() != null && !detailmark.getSubject18ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject19ObtainedMarks() != null && !detailmark.getSubject19ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getSubject20ObtainedMarks() != null && !detailmark.getSubject20ObtainedMarks().matches("\\d{0,4}(\\.\\d{1,3})?")) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.valid");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    return errors;
	                }
	                if (detailmark.getTotalMarks() != null && StringUtils.isEmpty(detailmark.getTotalMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalMarks())) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    }
	                    return errors;
	                }
	                if (detailmark.getTotalObtainedMarks() != null && StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !CommonUtil.isValidDecimal(detailmark.getTotalObtainedMarks())) {
	                    if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                        errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                    }
	                    return errors;
	                }
	            }
	            if (detailmark.isSubject1Mandatory() && (detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error2);
	                }
	                return errors;
	            }
	            if ((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().equalsIgnoreCase("."))) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().equalsIgnoreCase("."))) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().equalsIgnoreCase("."))) || (detailmark.getTotalMarksCGPA() != null && !StringUtils.isEmpty(detailmark.getTotalMarksCGPA()) && (detailmark.getTotalMarksCGPA().equalsIgnoreCase("0") || detailmark.getTotalMarksCGPA().equalsIgnoreCase(".")))) {
	                if (errors.get("knowledgepro.admission.detailTotalmark.greater.reqd") != null && !errors.get("knowledgepro.admission.detailTotalmark.greater.reqd").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("knowledgepro.admission.detailTotalmark.greater.reqd");
	                    errors.add("knowledgepro.admission.detailTotalmark.greater.reqd", error2);
	                }
	                return errors;
	            }
	            if ((((detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks())) && detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && detailmark.getSubject1Credit() != null && !StringUtils.isEmpty(detailmark.getSubject1Credit())) || ((detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks())) && detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && detailmark.getSubject2Credit() != null && !StringUtils.isEmpty(detailmark.getSubject2Credit())) || ((detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks())) && detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && detailmark.getSubject3Credit() != null && !StringUtils.isEmpty(detailmark.getSubject3Credit())) || ((detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks())) && detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && detailmark.getSubject4Credit() != null && !StringUtils.isEmpty(detailmark.getSubject4Credit())) || ((detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks())) && detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) && detailmark.getSubject5Credit() != null && !StringUtils.isEmpty(detailmark.getSubject5Credit())) || ((detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks())) && detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) && detailmark.getSubject6Credit() != null && !StringUtils.isEmpty(detailmark.getSubject6Credit())) || ((detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks())) && detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) && detailmark.getSubject7Credit() != null && !StringUtils.isEmpty(detailmark.getSubject7Credit())) || ((detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks())) && detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) && detailmark.getSubject8Credit() != null && !StringUtils.isEmpty(detailmark.getSubject8Credit())) || ((detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks())) && detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) && detailmark.getSubject9Credit() != null && !StringUtils.isEmpty(detailmark.getSubject9Credit())) || ((detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks())) && detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) && detailmark.getSubject10Credit() != null && !StringUtils.isEmpty(detailmark.getSubject10Credit())) || ((detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks())) && detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) || ((detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks())) && detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) || ((detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks())) && detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) || ((detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks())) && detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) || ((detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks())) && detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) || ((detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks())) && detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) || ((detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks())) && detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) || ((detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks())) && detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) || ((detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks())) && detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) || ((detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks())) && detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) || ((detailmark.getTotalMarks() == null || StringUtils.isEmpty(detailmark.getTotalMarks())) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())) || ((detailmark.getTotalMarks() == null || StringUtils.isEmpty(detailmark.getTotalMarksCGPA())) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                errors.add("admissionFormForm.education.obtainMark.larger", error2);
	            }
	            if (((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Float.parseFloat(detailmark.getSubject1TotalMarks()) < Float.parseFloat(detailmark.getSubject1ObtainedMarks())) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Float.parseFloat(detailmark.getSubject2TotalMarks()) < Float.parseFloat(detailmark.getSubject2ObtainedMarks())) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Float.parseFloat(detailmark.getSubject3TotalMarks()) < Float.parseFloat(detailmark.getSubject3ObtainedMarks())) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Float.parseFloat(detailmark.getSubject4TotalMarks()) < Float.parseFloat(detailmark.getSubject4ObtainedMarks())) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) && Float.parseFloat(detailmark.getSubject5TotalMarks()) < Float.parseFloat(detailmark.getSubject5ObtainedMarks())) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) && Float.parseFloat(detailmark.getSubject6TotalMarks()) < Float.parseFloat(detailmark.getSubject6ObtainedMarks())) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) && Float.parseFloat(detailmark.getSubject7TotalMarks()) < Float.parseFloat(detailmark.getSubject7ObtainedMarks())) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) && Float.parseFloat(detailmark.getSubject8TotalMarks()) < Float.parseFloat(detailmark.getSubject8ObtainedMarks())) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) && Float.parseFloat(detailmark.getSubject9TotalMarks()) < Float.parseFloat(detailmark.getSubject9ObtainedMarks())) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) && Float.parseFloat(detailmark.getSubject10TotalMarks()) < Float.parseFloat(detailmark.getSubject10ObtainedMarks())) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) && Float.parseFloat(detailmark.getSubject11TotalMarks()) < Float.parseFloat(detailmark.getSubject11ObtainedMarks())) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) && Float.parseFloat(detailmark.getSubject12TotalMarks()) < Float.parseFloat(detailmark.getSubject12ObtainedMarks())) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) && Float.parseFloat(detailmark.getSubject13TotalMarks()) < Float.parseFloat(detailmark.getSubject13ObtainedMarks())) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) && Float.parseFloat(detailmark.getSubject14TotalMarks()) < Float.parseFloat(detailmark.getSubject14ObtainedMarks())) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) && Float.parseFloat(detailmark.getSubject15TotalMarks()) < Float.parseFloat(detailmark.getSubject15ObtainedMarks())) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) && Float.parseFloat(detailmark.getSubject16TotalMarks()) < Float.parseFloat(detailmark.getSubject16ObtainedMarks())) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) && Float.parseFloat(detailmark.getSubject17TotalMarks()) < Float.parseFloat(detailmark.getSubject17ObtainedMarks())) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) && Float.parseFloat(detailmark.getSubject18TotalMarks()) < Float.parseFloat(detailmark.getSubject18ObtainedMarks())) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) && Float.parseFloat(detailmark.getSubject19TotalMarks()) < Float.parseFloat(detailmark.getSubject19ObtainedMarks())) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) && Float.parseFloat(detailmark.getSubject20TotalMarks()) < Float.parseFloat(detailmark.getSubject20ObtainedMarks())) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Float.parseFloat(detailmark.getTotalMarks()) < Float.parseFloat(detailmark.getTotalObtainedMarks())) || (detailmark.getTotalMarksCGPA() != null && !StringUtils.isEmpty(detailmark.getTotalMarksCGPA()) && detailmark.getTotalObtainedMarksCGPA() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarksCGPA()) && Float.parseFloat(detailmark.getTotalMarksCGPA()) < Float.parseFloat(detailmark.getTotalObtainedMarksCGPA()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                errors.add("admissionFormForm.education.obtainMark.larger", error2);
	            }
	            final int count = 20;
	            int compareCount = 0;
	            if (detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject19ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (compareCount == count) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"Please fill the Marks.."));
	            }
	        }
	        StudentLoginAction.log.info((Object)"exit validateMarks...");
	        return errors;
	    }
	    
	    private ActionMessages validateEditEducationDetails(ActionMessages errors, final LoginForm admForm) {
	        StudentLoginAction.log.info((Object)"enter validate education...");
	        final int doctypeId12 = CMSConstants.CLASS12_DOCTYPEID;
	        final int doctypeIdDEG = CMSConstants.DEGREE_DOCTYPEID;
	        int year10 = 0;
	        int year11 = 0;
	        int yearDeg = 0;
	        final List<EdnQualificationTO> qualifications = (List<EdnQualificationTO>)admForm.getApplicantDetails().getEdnQualificationList();
	        if (qualifications != null) {
	            for (final EdnQualificationTO qualfTO : qualifications) {
	                if (admForm.getCourseId() != null && !admForm.getCourseId().isEmpty()) {
	                    if ((qualfTO.getUniversityId() == null || qualfTO.getUniversityId().length() == 0 || qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers() == null || qualfTO.getUniversityOthers().trim().length() == 0) && errors.get("admissionFormForm.education.university.required") != null && !errors.get("admissionFormForm.education.university.required").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.university.required");
	                        errors.add("admissionFormForm.education.university.required", error);
	                    }
	                    if ((qualfTO.getOtherInstitute() == null || qualfTO.getOtherInstitute().trim().length() == 0) && errors.get("admissionFormForm.education.instiute.required") != null && !errors.get("admissionFormForm.education.instiute.required").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.instiute.required");
	                        errors.add("admissionFormForm.education.instiute.required", error);
	                    }
	                    if (qualfTO.isExamConfigured() && (qualfTO.getSelectedExamId() == null || StringUtils.isEmpty(qualfTO.getSelectedExamId())) && errors.get("admissionFormForm.education.exam.required") != null && !errors.get("admissionFormForm.education.exam.required").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.exam.required");
	                        errors.add("admissionFormForm.education.exam.required", error);
	                    }
	                    if (qualfTO.getYearPassing() == 0) {
	                        if (errors.get("admissionFormForm.education.passYear.required") != null && !errors.get("admissionFormForm.education.passYear.required").hasNext()) {
	                            final ActionMessage error = new ActionMessage("admissionFormForm.education.passYear.required");
	                            errors.add("admissionFormForm.education.passYear.required", error);
	                        }
	                    }
	                    else {
	                        final boolean valid = CommonUtil.isFutureNotCurrentYear(qualfTO.getYearPassing());
	                        if (valid && errors.get("admissionFormForm.education.passYear.future") != null && !errors.get("admissionFormForm.education.passYear.future").hasNext()) {
	                            final ActionMessage error2 = new ActionMessage("admissionFormForm.education.passYear.future");
	                            errors.add("admissionFormForm.education.passYear.future", error2);
	                        }
	                    }
	                    if (qualfTO.getMonthPassing() == 0 && errors.get("admissionFormForm.education.passMonth.required") != null && !errors.get("admissionFormForm.education.passMonth.required").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.passMonth.required");
	                        errors.add("admissionFormForm.education.passMonth.required", error);
	                    }
	                    if (qualfTO.isLastExam() && (qualfTO.getPreviousRegNo() == null || StringUtils.isEmpty(qualfTO.getPreviousRegNo().trim())) && errors.get("admissionFormForm.education.regNO.required") != null && !errors.get("admissionFormForm.education.regNO.required").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.regNO.required");
	                        errors.add("admissionFormForm.education.regNO.required", error);
	                    }
	                    if (qualfTO.getDocTypeId() == doctypeIdDEG) {
	                        yearDeg = qualfTO.getYearPassing();
	                    }
	                    else if (qualfTO.getDocTypeId() == doctypeId12) {
	                        year11 = qualfTO.getYearPassing();
	                    }
	                    else {
	                        year10 = qualfTO.getYearPassing();
	                    }
	                    if ((qualfTO.getDocTypeId() == doctypeId12 || qualfTO.getDocTypeId() == doctypeIdDEG) && qualfTO.getNoOfAttempts() == 0 && errors.get("admissionFormForm.education.noOfAttempt.required") != null && !errors.get("admissionFormForm.education.noOfAttempt.required").hasNext()) {
	                        final ActionMessage error = new ActionMessage("admissionFormForm.education.noOfAttempt.required");
	                        errors.add("admissionFormForm.education.noOfAttempt.required", error);
	                    }
	                    if (qualfTO.isConsolidated()) {
	                        if (qualfTO.getDocTypeId() == doctypeId12 || qualfTO.getDocTypeId() == doctypeIdDEG) {
	                            if (qualfTO.getTotalMarks() == null || StringUtils.isEmpty(qualfTO.getTotalMarks())) {
	                                if (errors.get("knowledgepro.admission.maxMark.required") != null && !errors.get("knowledgepro.admission.maxMark.required").hasNext()) {
	                                    final ActionMessage error = new ActionMessage("knowledgepro.admission.maxMark.required");
	                                    errors.add("knowledgepro.admission.maxMark.required", error);
	                                }
	                            }
	                            else if (!CommonUtil.isValidDecimal(qualfTO.getTotalMarks())) {
	                                if (errors.get("admissionFormForm.education.totalMark.notNumeric") != null && !errors.get("admissionFormForm.education.totalMark.notNumeric").hasNext()) {
	                                    final ActionMessage error = new ActionMessage("admissionFormForm.education.totalMark.notNumeric");
	                                    errors.add("admissionFormForm.education.totalMark.notNumeric", error);
	                                }
	                            }
	                            else if (((qualfTO.getTotalMarks() != null && qualfTO.getTotalMarks().equalsIgnoreCase("0")) || qualfTO.getTotalMarks().startsWith("0.0")) && errors.get("knowledgepro.admission.maxmark.greater.reqd") != null && !errors.get("knowledgepro.admission.maxmark.greater.reqd").hasNext()) {
	                                final ActionMessage error = new ActionMessage("knowledgepro.admission.maxmark.greater.reqd");
	                                errors.add("knowledgepro.admission.maxmark.greater.reqd", error);
	                            }
	                            if (qualfTO.getMarksObtained() == null || StringUtils.isEmpty(qualfTO.getMarksObtained())) {
	                                if (errors.get("admissionFormForm.education.obtainMark.required") != null && !errors.get("admissionFormForm.education.obtainMark.required").hasNext()) {
	                                    final ActionMessage error = new ActionMessage("admissionFormForm.education.obtainMark.required");
	                                    errors.add("admissionFormForm.education.obtainMark.required", error);
	                                }
	                            }
	                            else if (!CommonUtil.isValidDecimal(qualfTO.getMarksObtained())) {
	                                if (errors.get("admissionFormForm.education.obtainMark.notNumeric") != null && !errors.get("admissionFormForm.education.obtainMark.notNumeric").hasNext()) {
	                                    final ActionMessage error = new ActionMessage("admissionFormForm.education.obtainMark.notNumeric");
	                                    errors.add("admissionFormForm.education.obtainMark.notNumeric", error);
	                                }
	                            }
	                            else if (qualfTO.getMarksObtained() != null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) && CommonUtil.isValidDecimal(qualfTO.getMarksObtained()) && qualfTO.getTotalMarks() != null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) && CommonUtil.isValidDecimal(qualfTO.getTotalMarks()) && Double.parseDouble(qualfTO.getTotalMarks()) < Double.parseDouble(qualfTO.getMarksObtained()) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                                final ActionMessage error = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                                errors.add("admissionFormForm.education.obtainMark.larger", error);
	                            }
	                        }
	                        if (qualfTO.getPercentage() == null || StringUtils.isEmpty(qualfTO.getPercentage())) {
	                            if (errors.get("admissionFormForm.education.percentage.required") == null || errors.get("admissionFormForm.education.percentage.required").hasNext()) {
	                                continue;
	                            }
	                            final ActionMessage error = new ActionMessage("admissionFormForm.education.percentage.required");
	                            errors.add("admissionFormForm.education.percentage.required", error);
	                        }
	                        else if (!CommonUtil.isValidDecimal(qualfTO.getPercentage())) {
	                            if (errors.get("admissionFormForm.education.percentage.notNumeric") == null || errors.get("admissionFormForm.education.percentage.notNumeric").hasNext()) {
	                                continue;
	                            }
	                            final ActionMessage error = new ActionMessage("admissionFormForm.education.percentage.notNumeric");
	                            errors.add("admissionFormForm.education.percentage.notNumeric", error);
	                        }
	                        else {
	                            if (Float.parseFloat(qualfTO.getPercentage()) <= 100.0f) {
	                                continue;
	                            }
	                            errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)" Percentage should not be  more than 100."));
	                        }
	                    }
	                    else {
	                        if (qualfTO.isSemesterWise()) {
	                            if (qualfTO.getSemesterList() == null && errors.get("admissionFormForm.education.semesterMark.required") != null && !errors.get("admissionFormForm.education.semesterMark.required").hasNext()) {
	                                final ActionMessage error = new ActionMessage("admissionFormForm.education.semesterMark.required");
	                                errors.add("admissionFormForm.education.semesterMark.required", error);
	                            }
	                        }
	                        else if (qualfTO.getDetailmark() == null && errors.get("admissionFormForm.education.detailMark.required") != null && !errors.get("admissionFormForm.education.detailMark.required").hasNext()) {
	                            final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.required");
	                            errors.add("admissionFormForm.education.detailMark.required", error);
	                        }
	                        if (qualfTO.getDocTypeId() == doctypeId12 || qualfTO.getDocTypeId() == doctypeIdDEG) {
	                            if ((qualfTO.getDetailmark().getSubjectid1() == null || qualfTO.getDetailmark().getSubjectid1().isEmpty()) && (qualfTO.getDetailmark().getSubjectid2() == null || qualfTO.getDetailmark().getSubjectid2().isEmpty()) && (qualfTO.getDetailmark().getSubjectid3() == null || qualfTO.getDetailmark().getSubjectid3().isEmpty()) && (qualfTO.getDetailmark().getSubjectid4() == null || qualfTO.getDetailmark().getSubjectid4().isEmpty()) && (qualfTO.getDetailmark().getSubjectid5() == null || qualfTO.getDetailmark().getSubjectid5().isEmpty()) && (qualfTO.getDetailmark().getSubjectid6() == null || qualfTO.getDetailmark().getSubjectid6().isEmpty()) && (qualfTO.getDetailmark().getSubjectid7() == null || qualfTO.getDetailmark().getSubjectid7().isEmpty()) && (qualfTO.getDetailmark().getSubjectid8() == null || qualfTO.getDetailmark().getSubjectid8().isEmpty()) && (qualfTO.getDetailmark().getSubjectid9() == null || qualfTO.getDetailmark().getSubjectid9().isEmpty()) && (qualfTO.getDetailmark().getSubjectid10() == null || qualfTO.getDetailmark().getSubjectid10().isEmpty()) && (qualfTO.getDetailmark().getSubjectid11() == null || qualfTO.getDetailmark().getSubjectid11().isEmpty()) && (qualfTO.getDetailmark().getSubjectid12() == null || qualfTO.getDetailmark().getSubjectid12().isEmpty()) && (qualfTO.getDetailmark().getSubjectid13() == null || qualfTO.getDetailmark().getSubjectid13().isEmpty()) && (qualfTO.getDetailmark().getSubjectid14() == null || qualfTO.getDetailmark().getSubjectid14().isEmpty()) && (qualfTO.getDetailmark().getSubjectid15() == null || qualfTO.getDetailmark().getSubjectid15().isEmpty()) && (qualfTO.getDetailmark().getSubjectid16() == null || qualfTO.getDetailmark().getSubjectid16().isEmpty()) && (qualfTO.getDetailmark().getSubjectid17() == null || qualfTO.getDetailmark().getSubjectid17().isEmpty()) && (qualfTO.getDetailmark().getSubjectid18() == null || qualfTO.getDetailmark().getSubjectid18().isEmpty()) && (qualfTO.getDetailmark().getSubjectid19() == null || qualfTO.getDetailmark().getSubjectid19().isEmpty()) && (qualfTO.getDetailmark().getSubjectid20() == null || qualfTO.getDetailmark().getSubjectid20().isEmpty())) {
	                                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.required");
	                                errors.add("admissionFormForm.education.detailMark.required", error);
	                            }
	                            else if ((qualfTO.getDetailmark().getTotalMarks() == null || qualfTO.getDetailmark().getTotalMarks().isEmpty()) && (qualfTO.getDetailmark().getTotalObtainedMarks() == null || qualfTO.getDetailmark().getTotalObtainedMarks().isEmpty()) && (qualfTO.getDetailmark().getTotalMarksCGPA() == null || qualfTO.getDetailmark().getTotalMarksCGPA().isEmpty()) && (qualfTO.getDetailmark().getTotalObtainedMarksCGPA() == null || qualfTO.getDetailmark().getTotalObtainedMarksCGPA().isEmpty())) {
	                                final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.required");
	                                errors.add("admissionFormForm.education.detailMark.required", error);
	                            }
	                            if (errors.size() == 0 && qualfTO.getDocTypeId() == doctypeId12) {
	                                errors = validateMarksClass12(qualfTO.getDetailmark());
	                                if (errors.size() != 0) {
	                                    return errors;
	                                }
	                            }
	                        }
	                        if (qualfTO.getDocTypeId() == doctypeId12 || qualfTO.getDocTypeId() == doctypeIdDEG) {
	                            continue;
	                        }
	                        if ((qualfTO.getDetailmark().getSubject1() == null || qualfTO.getDetailmark().getSubject1().isEmpty()) && (qualfTO.getDetailmark().getSubject2() == null || qualfTO.getDetailmark().getSubject2().isEmpty()) && (qualfTO.getDetailmark().getSubject3() == null || qualfTO.getDetailmark().getSubject3().isEmpty()) && (qualfTO.getDetailmark().getSubject4() == null || qualfTO.getDetailmark().getSubject4().isEmpty()) && (qualfTO.getDetailmark().getSubject5() == null || qualfTO.getDetailmark().getSubject5().isEmpty()) && (qualfTO.getDetailmark().getSubject6() == null || qualfTO.getDetailmark().getSubject6().isEmpty()) && (qualfTO.getDetailmark().getSubject7() == null || qualfTO.getDetailmark().getSubject7().isEmpty()) && (qualfTO.getDetailmark().getSubject8() == null || qualfTO.getDetailmark().getSubject8().isEmpty()) && (qualfTO.getDetailmark().getSubject9() == null || qualfTO.getDetailmark().getSubject9().isEmpty()) && (qualfTO.getDetailmark().getSubject10() == null || qualfTO.getDetailmark().getSubject10().isEmpty()) && (qualfTO.getDetailmark().getSubject11() == null || qualfTO.getDetailmark().getSubject11().isEmpty()) && (qualfTO.getDetailmark().getSubject12() == null || qualfTO.getDetailmark().getSubject12().isEmpty()) && (qualfTO.getDetailmark().getSubject13() == null || qualfTO.getDetailmark().getSubject13().isEmpty()) && (qualfTO.getDetailmark().getSubject14() == null || qualfTO.getDetailmark().getSubject14().isEmpty()) && (qualfTO.getDetailmark().getSubject15() == null || qualfTO.getDetailmark().getSubject15().isEmpty()) && (qualfTO.getDetailmark().getSubject16() == null || qualfTO.getDetailmark().getSubject16().isEmpty()) && (qualfTO.getDetailmark().getSubject17() == null || qualfTO.getDetailmark().getSubject17().isEmpty()) && (qualfTO.getDetailmark().getSubject18() == null || qualfTO.getDetailmark().getSubject18().isEmpty()) && (qualfTO.getDetailmark().getSubject19() == null || qualfTO.getDetailmark().getSubject19().isEmpty()) && (qualfTO.getDetailmark().getSubject20() == null || qualfTO.getDetailmark().getSubject20().isEmpty())) {
	                            final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.required");
	                            errors.add("admissionFormForm.education.detailMark.required", error);
	                        }
	                        else if ((qualfTO.getDetailmark().getTotalMarks() == null || qualfTO.getDetailmark().getTotalMarks().isEmpty()) && (qualfTO.getDetailmark().getTotalObtainedMarks() == null || qualfTO.getDetailmark().getTotalObtainedMarks().isEmpty())) {
	                            final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.required");
	                            errors.add("admissionFormForm.education.detailMark.required", error);
	                        }
	                        if (errors.size() != 0) {
	                            continue;
	                        }
	                        errors = validateMarks(qualfTO.getDetailmark());
	                        if (errors.size() != 0) {
	                            return errors;
	                        }
	                        continue;
	                    }
	                }
	            }
	            if (year10 != 0 && year11 != 0 && year11 <= year10) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)" 10 Class year of passing is more than 12 Class."));
	            }
	            if (year11 != 0 && yearDeg != 0 && yearDeg <= year11) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"12 Class year of passing is more than Degree."));
	            }
	            if (yearDeg != 0 && year10 != 0 && yearDeg <= year10) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"10 Class year of passing is more than Degree."));
	            }
	            StudentLoginAction.log.info((Object)"exit validate education...");
	        }
	        return errors;
	    }
	    
	    public static ActionMessages validateMarks(final CandidateMarkTO detailmark) {
	        StudentLoginAction.log.info((Object)"enter validateMarks...");
	        final ActionMessages errors = new ActionMessages();
	        if (detailmark != null) {
	            if (detailmark.isSubject1Mandatory() && (detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject2Mandatory() && (detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject3Mandatory() && (detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject4Mandatory() && (detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject5Mandatory() && (detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject6Mandatory() && (detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject7Mandatory() && (detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject8Mandatory() && (detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject9Mandatory() && (detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject10Mandatory() && (detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject11Mandatory() && (detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject12Mandatory() && (detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject13Mandatory() && (detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject14Mandatory() && (detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject15Mandatory() && (detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject16Mandatory() && (detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject17Mandatory() && (detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject18Mandatory() && (detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject19Mandatory() && (detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            if (detailmark.isSubject20Mandatory() && (detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.detailMark.mandatory") != null && !errors.get("admissionFormForm.education.detailMark.mandatory").hasNext()) {
	                    final ActionMessage error = new ActionMessage("admissionFormForm.education.detailMark.mandatory");
	                    errors.add("admissionFormForm.education.detailMark.mandatory", error);
	                }
	                return errors;
	            }
	            final int count = 20;
	            int compareCount = 0;
	            if (detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) || detailmark.getSubject1ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) || detailmark.getSubject2ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) || detailmark.getSubject3ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) || detailmark.getSubject4ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) || detailmark.getSubject5ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) || detailmark.getSubject6ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) || detailmark.getSubject7ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) || detailmark.getSubject8ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) || detailmark.getSubject9ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) || detailmark.getSubject10ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) || detailmark.getSubject11ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) || detailmark.getSubject12ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) || detailmark.getSubject13ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) || detailmark.getSubject14ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) || detailmark.getSubject15ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) || detailmark.getSubject16ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) || detailmark.getSubject17ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) || detailmark.getSubject18ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) || detailmark.getSubject19ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) || detailmark.getSubject20ObtainedMarks() == null || StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) {
	                ++compareCount;
	            }
	            if (compareCount == count) {
	                errors.add("error", (ActionMessage)new ActionError("knowledgepro.admission.empty.err.message", (Object)"Please fill the Marks.."));
	            }
	            if ((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1TotalMarks())) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject2TotalMarks())) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject3TotalMarks())) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject4TotalMarks())) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject5TotalMarks())) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject6TotalMarks())) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject7TotalMarks())) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject8TotalMarks())) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject9TotalMarks())) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject10TotalMarks())) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject11TotalMarks())) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject12TotalMarks())) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject13TotalMarks())) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject14TotalMarks())) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject15TotalMarks())) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject16TotalMarks())) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject17TotalMarks())) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject18TotalMarks())) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject19TotalMarks())) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject20TotalMarks())) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && !StringUtils.isNumeric(detailmark.getTotalMarks()))) {
	                if (errors.get("admissionFormForm.education.totalMark.notNumeric") != null && !errors.get("admissionFormForm.education.totalMark.notNumeric").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.totalMark.notNumeric");
	                    errors.add("admissionFormForm.education.totalMark.notNumeric", error2);
	                }
	                return errors;
	            }
	            if ((detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1ObtainedMarks())) || (detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject2ObtainedMarks())) || (detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject3ObtainedMarks())) || (detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject4ObtainedMarks())) || (detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject5ObtainedMarks())) || (detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject6ObtainedMarks())) || (detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject7ObtainedMarks())) || (detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject8ObtainedMarks())) || (detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject9ObtainedMarks())) || (detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject10ObtainedMarks())) || (detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject11ObtainedMarks())) || (detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject12ObtainedMarks())) || (detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject13ObtainedMarks())) || (detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject14ObtainedMarks())) || (detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject15ObtainedMarks())) || (detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject16ObtainedMarks())) || (detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject17ObtainedMarks())) || (detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject18ObtainedMarks())) || (detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject19ObtainedMarks())) || (detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject20ObtainedMarks())) || (detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && !StringUtils.isNumeric(detailmark.getTotalObtainedMarks()))) {
	                if (errors.get("admissionFormForm.education.obtainMark.notNumeric") != null && !errors.get("admissionFormForm.education.obtainMark.notNumeric").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.notNumeric");
	                    errors.add("admissionFormForm.education.obtainMark.notNumeric", error2);
	                }
	                return errors;
	            }
	            if ((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && (detailmark.getSubject1TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject1TotalMarks().startsWith("0"))) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && (detailmark.getSubject2TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject2TotalMarks().startsWith("0"))) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && (detailmark.getSubject3TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject3TotalMarks().startsWith("0"))) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && (detailmark.getSubject4TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject4TotalMarks().startsWith("0"))) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && (detailmark.getSubject5TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject5TotalMarks().startsWith("0"))) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && (detailmark.getSubject6TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject6TotalMarks().startsWith("0"))) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && (detailmark.getSubject7TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject7TotalMarks().startsWith("0"))) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && (detailmark.getSubject8TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject8TotalMarks().startsWith("0"))) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && (detailmark.getSubject9TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject9TotalMarks().startsWith("0"))) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && (detailmark.getSubject10TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject10TotalMarks().startsWith("0"))) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && (detailmark.getSubject11TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject11TotalMarks().startsWith("0"))) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && (detailmark.getSubject12TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject12TotalMarks().startsWith("0"))) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && (detailmark.getSubject13TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject13TotalMarks().startsWith("0"))) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && (detailmark.getSubject14TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject14TotalMarks().startsWith("0"))) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && (detailmark.getSubject15TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject15TotalMarks().startsWith("0"))) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && (detailmark.getSubject16TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject16TotalMarks().startsWith("0"))) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && (detailmark.getSubject17TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject17TotalMarks().startsWith("0"))) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && (detailmark.getSubject18TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject18TotalMarks().startsWith("0"))) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && (detailmark.getSubject19TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject19TotalMarks().startsWith("0"))) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && (detailmark.getSubject20TotalMarks().equalsIgnoreCase("0") || detailmark.getSubject20TotalMarks().startsWith("0"))) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && (detailmark.getTotalMarks().equalsIgnoreCase("0") || detailmark.getTotalMarks().startsWith("0")))) {
	                if (errors.get("knowledgepro.admission.detailTotalmark.greater.reqd") != null && !errors.get("knowledgepro.admission.detailTotalmark.greater.reqd").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("knowledgepro.admission.detailTotalmark.greater.reqd");
	                    errors.add("knowledgepro.admission.detailTotalmark.greater.reqd", error2);
	                }
	                return errors;
	            }
	            if ((((detailmark.getSubject1TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1TotalMarks())) && detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks())) || ((detailmark.getSubject2TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2TotalMarks())) && detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks())) || ((detailmark.getSubject3TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3TotalMarks())) && detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks())) || ((detailmark.getSubject4TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4TotalMarks())) && detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks())) || ((detailmark.getSubject5TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5TotalMarks())) && detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks())) || ((detailmark.getSubject6TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6TotalMarks())) && detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks())) || ((detailmark.getSubject7TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7TotalMarks())) && detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks())) || ((detailmark.getSubject8TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8TotalMarks())) && detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks())) || ((detailmark.getSubject9TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9TotalMarks())) && detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks())) || ((detailmark.getSubject10TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10TotalMarks())) && detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks())) || ((detailmark.getSubject11TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject11TotalMarks())) && detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks())) || ((detailmark.getSubject12TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject12TotalMarks())) && detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks())) || ((detailmark.getSubject13TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject13TotalMarks())) && detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks())) || ((detailmark.getSubject14TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject14TotalMarks())) && detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks())) || ((detailmark.getSubject15TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject15TotalMarks())) && detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks())) || ((detailmark.getSubject16TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject16TotalMarks())) && detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks())) || ((detailmark.getSubject17TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject17TotalMarks())) && detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks())) || ((detailmark.getSubject18TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject18TotalMarks())) && detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks())) || ((detailmark.getSubject19TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject19TotalMarks())) && detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks())) || ((detailmark.getSubject20TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject20TotalMarks())) && detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks())) || ((detailmark.getTotalMarks() == null || StringUtils.isEmpty(detailmark.getTotalMarks())) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                errors.add("admissionFormForm.education.obtainMark.larger", error2);
	            }
	            if (((detailmark.getSubject1TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1TotalMarks()) && detailmark.getSubject1ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1TotalMarks()) < Integer.parseInt(detailmark.getSubject1ObtainedMarks())) || (detailmark.getSubject2TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && detailmark.getSubject2ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2TotalMarks()) < Integer.parseInt(detailmark.getSubject2ObtainedMarks())) || (detailmark.getSubject3TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && detailmark.getSubject3ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3TotalMarks()) < Integer.parseInt(detailmark.getSubject3ObtainedMarks())) || (detailmark.getSubject4TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && detailmark.getSubject4ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4TotalMarks()) < Integer.parseInt(detailmark.getSubject4ObtainedMarks())) || (detailmark.getSubject5TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && detailmark.getSubject5ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5ObtainedMarks()) && Integer.parseInt(detailmark.getSubject5TotalMarks()) < Integer.parseInt(detailmark.getSubject5ObtainedMarks())) || (detailmark.getSubject6TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6TotalMarks()) && detailmark.getSubject6ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6ObtainedMarks()) && Integer.parseInt(detailmark.getSubject6TotalMarks()) < Integer.parseInt(detailmark.getSubject6ObtainedMarks())) || (detailmark.getSubject7TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7TotalMarks()) && detailmark.getSubject7ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7ObtainedMarks()) && Integer.parseInt(detailmark.getSubject7TotalMarks()) < Integer.parseInt(detailmark.getSubject7ObtainedMarks())) || (detailmark.getSubject8TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8TotalMarks()) && detailmark.getSubject8ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8ObtainedMarks()) && Integer.parseInt(detailmark.getSubject8TotalMarks()) < Integer.parseInt(detailmark.getSubject8ObtainedMarks())) || (detailmark.getSubject9TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9TotalMarks()) && detailmark.getSubject9ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9ObtainedMarks()) && Integer.parseInt(detailmark.getSubject9TotalMarks()) < Integer.parseInt(detailmark.getSubject9ObtainedMarks())) || (detailmark.getSubject10TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10TotalMarks()) && detailmark.getSubject10ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10ObtainedMarks()) && Integer.parseInt(detailmark.getSubject10TotalMarks()) < Integer.parseInt(detailmark.getSubject10ObtainedMarks())) || (detailmark.getSubject11TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11TotalMarks()) && detailmark.getSubject11ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject11ObtainedMarks()) && Integer.parseInt(detailmark.getSubject11TotalMarks()) < Integer.parseInt(detailmark.getSubject11ObtainedMarks())) || (detailmark.getSubject12TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12TotalMarks()) && detailmark.getSubject12ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject12ObtainedMarks()) && Integer.parseInt(detailmark.getSubject12TotalMarks()) < Integer.parseInt(detailmark.getSubject12ObtainedMarks())) || (detailmark.getSubject13TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13TotalMarks()) && detailmark.getSubject13ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject13ObtainedMarks()) && Integer.parseInt(detailmark.getSubject13TotalMarks()) < Integer.parseInt(detailmark.getSubject13ObtainedMarks())) || (detailmark.getSubject14TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14TotalMarks()) && detailmark.getSubject14ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject14ObtainedMarks()) && Integer.parseInt(detailmark.getSubject14TotalMarks()) < Integer.parseInt(detailmark.getSubject14ObtainedMarks())) || (detailmark.getSubject15TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15TotalMarks()) && detailmark.getSubject15ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject15ObtainedMarks()) && Integer.parseInt(detailmark.getSubject15TotalMarks()) < Integer.parseInt(detailmark.getSubject15ObtainedMarks())) || (detailmark.getSubject16TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16TotalMarks()) && detailmark.getSubject16ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject16ObtainedMarks()) && Integer.parseInt(detailmark.getSubject16TotalMarks()) < Integer.parseInt(detailmark.getSubject16ObtainedMarks())) || (detailmark.getSubject17TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17TotalMarks()) && detailmark.getSubject17ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject17ObtainedMarks()) && Integer.parseInt(detailmark.getSubject17TotalMarks()) < Integer.parseInt(detailmark.getSubject17ObtainedMarks())) || (detailmark.getSubject18TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18TotalMarks()) && detailmark.getSubject18ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject18ObtainedMarks()) && Integer.parseInt(detailmark.getSubject18TotalMarks()) < Integer.parseInt(detailmark.getSubject18ObtainedMarks())) || (detailmark.getSubject19TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19TotalMarks()) && detailmark.getSubject19ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject19ObtainedMarks()) && Integer.parseInt(detailmark.getSubject19TotalMarks()) < Integer.parseInt(detailmark.getSubject19ObtainedMarks())) || (detailmark.getSubject20TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20TotalMarks()) && detailmark.getSubject20ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject20ObtainedMarks()) && Integer.parseInt(detailmark.getSubject20TotalMarks()) < Integer.parseInt(detailmark.getSubject20ObtainedMarks())) || (detailmark.getTotalMarks() != null && !StringUtils.isEmpty(detailmark.getTotalMarks()) && detailmark.getTotalObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotalObtainedMarks()) && Integer.parseInt(detailmark.getTotalMarks()) < Integer.parseInt(detailmark.getTotalObtainedMarks()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                errors.add("admissionFormForm.education.obtainMark.larger", error2);
	            }
	            if (detailmark.isSemesterMark()) {
	                if ((detailmark.getSubject1_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject1_languagewise_TotalMarks())) || (detailmark.getSubject2_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject2TotalMarks())) || (detailmark.getSubject3_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject3TotalMarks())) || (detailmark.getSubject4_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject4TotalMarks())) || (detailmark.getSubject5_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject5TotalMarks())) || (detailmark.getSubject6_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject6_languagewise_TotalMarks())) || (detailmark.getSubject7_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject7_languagewise_TotalMarks())) || (detailmark.getSubject8_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject8_languagewise_TotalMarks())) || (detailmark.getSubject9_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject9_languagewise_TotalMarks())) || (detailmark.getSubject10_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks()) && !StringUtils.isNumeric(detailmark.getSubject10_languagewise_TotalMarks())) || (detailmark.getTotal_languagewise_Marks() != null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks()) && !StringUtils.isNumeric(detailmark.getTotal_languagewise_Marks()))) {
	                    if (errors.get("admissionFormForm.education.totalMark.notNumeric") != null && !errors.get("admissionFormForm.education.totalMark.notNumeric").hasNext()) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.totalMark.notNumeric");
	                        errors.add("admissionFormForm.education.totalMark.notNumeric", error2);
	                    }
	                    return errors;
	                }
	                if ((detailmark.getSubject1_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject1_languagewise_ObtainedMarks())) || (detailmark.getSubject2_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject2_languagewise_ObtainedMarks())) || (detailmark.getSubject3_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject3_languagewise_ObtainedMarks())) || (detailmark.getSubject4_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject4_languagewise_ObtainedMarks())) || (detailmark.getSubject5_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject5_languagewise_ObtainedMarks())) || (detailmark.getSubject6_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject6_languagewise_ObtainedMarks())) || (detailmark.getSubject7_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject7_languagewise_ObtainedMarks())) || (detailmark.getSubject8_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject8_languagewise_ObtainedMarks())) || (detailmark.getSubject9_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject9_languagewise_ObtainedMarks())) || (detailmark.getSubject10_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getSubject10_languagewise_ObtainedMarks())) || (detailmark.getTotal_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()) && !StringUtils.isNumeric(detailmark.getTotal_languagewise_ObtainedMarks()))) {
	                    if (errors.get("admissionFormForm.education.obtainMark.notNumeric") != null && !errors.get("admissionFormForm.education.obtainMark.notNumeric").hasNext()) {
	                        final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.notNumeric");
	                        errors.add("admissionFormForm.education.obtainMark.notNumeric", error2);
	                    }
	                    return errors;
	                }
	                if ((((detailmark.getSubject1_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks())) && detailmark.getSubject1_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks())) || ((detailmark.getSubject2_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks())) && detailmark.getSubject2_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks())) || ((detailmark.getSubject3_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks())) && detailmark.getSubject3_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks())) || ((detailmark.getSubject4_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks())) && detailmark.getSubject4_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks())) || ((detailmark.getSubject5_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks())) && detailmark.getSubject5_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks())) || ((detailmark.getSubject6_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks())) && detailmark.getSubject6_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks())) || ((detailmark.getSubject7_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks())) && detailmark.getSubject7_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks())) || ((detailmark.getSubject8_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks())) && detailmark.getSubject8_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks())) || ((detailmark.getSubject9_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks())) && detailmark.getSubject9_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks())) || ((detailmark.getSubject10_languagewise_TotalMarks() == null || StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks())) && detailmark.getSubject10_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks())) || ((detailmark.getTotal_languagewise_Marks() == null || StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks())) && detailmark.getTotal_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                    errors.add("admissionFormForm.education.obtainMark.larger", error2);
	                }
	                if (((detailmark.getSubject1_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_TotalMarks()) && detailmark.getSubject1_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject1_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject1_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject1_languagewise_ObtainedMarks())) || (detailmark.getSubject2_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_TotalMarks()) && detailmark.getSubject2_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject2_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject2_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject2_languagewise_ObtainedMarks())) || (detailmark.getSubject3_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_TotalMarks()) && detailmark.getSubject3_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject3_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject3_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject3_languagewise_ObtainedMarks())) || (detailmark.getSubject4_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_TotalMarks()) && detailmark.getSubject4_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject4_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject4_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject4_languagewise_ObtainedMarks())) || (detailmark.getSubject5_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_TotalMarks()) && detailmark.getSubject5_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject5_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject5_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject5_languagewise_ObtainedMarks())) || (detailmark.getSubject6_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_TotalMarks()) && detailmark.getSubject6_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject6_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject6_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject6_languagewise_ObtainedMarks())) || (detailmark.getSubject7_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_TotalMarks()) && detailmark.getSubject7_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject7_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject7_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject7_languagewise_ObtainedMarks())) || (detailmark.getSubject8_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_TotalMarks()) && detailmark.getSubject8_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject8_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject8_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject8_languagewise_ObtainedMarks())) || (detailmark.getSubject9_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_TotalMarks()) && detailmark.getSubject9_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject9_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject9_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject9_languagewise_ObtainedMarks())) || (detailmark.getSubject10_languagewise_TotalMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_TotalMarks()) && detailmark.getSubject10_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getSubject10_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getSubject10_languagewise_TotalMarks()) < Integer.parseInt(detailmark.getSubject10_languagewise_ObtainedMarks())) || (detailmark.getTotal_languagewise_Marks() != null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_Marks()) && detailmark.getTotal_languagewise_ObtainedMarks() != null && !StringUtils.isEmpty(detailmark.getTotal_languagewise_ObtainedMarks()) && Integer.parseInt(detailmark.getTotal_languagewise_Marks()) < Integer.parseInt(detailmark.getTotal_languagewise_ObtainedMarks()))) && errors.get("admissionFormForm.education.obtainMark.larger") != null && !errors.get("admissionFormForm.education.obtainMark.larger").hasNext()) {
	                    final ActionMessage error2 = new ActionMessage("admissionFormForm.education.obtainMark.larger");
	                    errors.add("admissionFormForm.education.obtainMark.larger", error2);
	                }
	            }
	        }
	        StudentLoginAction.log.info((Object)"exit validateMarks...");
	        return errors;
	    }
	    
	    public ActionForward viewProgressCard(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        final String type = loginForm.getType();
	        try {
	            session.setAttribute("sLogin", (Object)true);
	            final String studentid = (String)session.getAttribute("studentid");
	            loginForm.setStudentId(Integer.parseInt(studentid));
	            final List<ProgressCardTo> studentList = (List<ProgressCardTo>)LoginHandler.getInstance().getProgressCard(loginForm);
	            loginForm.setProgressCardList((List)studentList);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("sLoginProgressCard");
	    }
	    
	    public ActionForward printProgressCard(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	        final LoginForm loginForm = (LoginForm)form;
	        final HttpSession session = request.getSession(true);
	        final String type = loginForm.getType();
	        try {
	            session.setAttribute("sLogin", (Object)true);
	            final String studentid = (String)session.getAttribute("studentid");
	            loginForm.setStudentId(Integer.parseInt(studentid));
	            final List<ProgressCardTo> studentList = (List<ProgressCardTo>)LoginHandler.getInstance().getProgressCard(loginForm);
	            loginForm.setProgressCardList((List)studentList);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mapping.findForward("sLoginPrintProgressCard");
	    }
	}
