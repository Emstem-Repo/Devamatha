package com.kp.cms.actions.admission;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.InterviewResultEntryHandler;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.RenderYearList;

/**
 * Action class for Interview Result Entry
 */
@SuppressWarnings("deprecation")
public class InterviewResultEntryAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(InterviewResultEntryAction.class);
	private static final String EDIT_COUNT_ID = "editcountID";
	
	/**
	 * Method to set all active Program Types to the form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			interviewResultEntryForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		resetFields(interviewResultEntryForm);
		return mapping.findForward(CMSConstants.INTERVIEW_PROCESSING);
	}
	/**
	 * Method to get applicant details to display in interviewResultEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getApplicantDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered getApplicantDetails of Interview Result Entry");

		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		ActionMessages messages = new ActionMessages();
		
		try{
			ActionErrors errors = interviewResultEntryForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			
			String applicationNumber = interviewResultEntryForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(interviewResultEntryForm.getApplicationYear());
			int courseId = Integer.parseInt(interviewResultEntryForm.getCourseId());
			Map<Integer, String> interviewTypes = null;

			AdmApplnTO applicantDetails = InterviewResultEntryHandler.getInstance().getApplicantDetails(applicationNumber, applicationYear, courseId, request);
			
			if( applicantDetails == null){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INTERVIEW_PROCESSING);
			} 
			
			if(applicantDetails.getCourse()!=null && applicantDetails.getCourse().getId()!= 0 && applicantDetails.getAppliedYear()!= 0){
				interviewTypes = InterviewResultEntryHandler.getInstance().getInterviewTypesByCourse(applicantDetails.getCourse().getId(), applicantDetails.getApplicationId(), applicantDetails.getAppliedYear() );
			}else if(applicantDetails.getCourse()!=null && applicantDetails.getCourse().getProgramId()!=0 && applicantDetails.getAppliedYear()!= 0){
				interviewTypes = InterviewResultEntryHandler.getInstance().getInterviewTypesByProgram(applicantDetails.getCourse().getProgramId(), applicantDetails.getApplicationId(), applicantDetails.getAppliedYear());
			}
			
			if(interviewTypes == null || interviewTypes.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_CARDNOTGENERATED));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INTERVIEW_PROCESSING);
			} else {
				interviewResultEntryForm.setApplicantDetails(applicantDetails);
				interviewResultEntryForm.setInterviewTypes(interviewTypes);

				Map<Integer, String> interviewStatus = InterviewResultEntryHandler.getInstance().getInterviewStatus();
				interviewResultEntryForm.setInterviewStatus(interviewStatus);
				
				Map<Integer, String> referredBy = InterviewResultEntryHandler.getInstance().getReferredBy();
				interviewResultEntryForm.setReferredBy(referredBy);
				
				Map<Integer, String> grades = InterviewResultEntryHandler.getInstance().getGrades();
				interviewResultEntryForm.setGrades(grades);
				resetFields(interviewResultEntryForm);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INTERVIEW_PROCESSING);
		}
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			//log.error("Error occured at convertToExcel of studentDetailsReportHelper ",e);
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.PRG_TYPE);
		if(interviewResultEntryForm.getApplicantDetails().getCourse().getProgramTypeId()==(Integer.parseInt(fileName))){
			interviewResultEntryForm.setViewextradetails(true);
		}
		interviewResultEntryForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
		if(interviewResultEntryForm.getCheckReligionId().equals(interviewResultEntryForm.getApplicantDetails().getPersonalData().getReligionId())){
			interviewResultEntryForm.setViewParish(true);
		}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			log.error("error occured in getApplicantDetails method of InterviewResultEntryAction class..");
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit getApplicantDetails Interview Result Entry");
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_ENTRY);
	}

	/**
	 * Method to add the interview result for the selected candidate
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward interviewResultEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered add Interview Result Entry");
		
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = interviewResultEntryForm.validate(mapping, request);
		errors =  validateDocumentSize(interviewResultEntryForm, errors);

		try {
			if (errors.isEmpty()) {
				
				if(interviewResultEntryForm.getSubroundCount() != null && Integer.parseInt(interviewResultEntryForm.getSubroundCount()) != 0){
					if(interviewResultEntryForm.getInterviewSubroundId() !=null && interviewResultEntryForm.getInterviewSubroundId().length()== 0) {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_REQUIRED));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INTERVIEW_RESULT_ENTRY);
					}	
				}
				setUserId(request, interviewResultEntryForm);
				
				if(InterviewResultEntryHandler.getInstance().addInterviewResult(interviewResultEntryForm)){
						
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_INTERVIEWRESULTADDED);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					resetFields(interviewResultEntryForm);
					return mapping.findForward(CMSConstants.INTERVIEW_PROCESSING);
				}
			} else {
				saveErrors(request, errors);
				saveMessages(request, messages);
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit add Interview Result Entry");
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_ENTRY);
	}
	
	/**
	 * Method to validate the uploaded file size
	 * @param interviewResultEntryForm
	 * @param errors
	 * @return
	 */
	private ActionErrors validateDocumentSize(InterviewResultEntryForm interviewResultEntryForm,
			ActionErrors errors) {
		List<ApplnDocTO> uploadlist = interviewResultEntryForm.getUploadDocs();
		InputStream propStream= RenderYearList.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maxSize = 0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maxSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
		 }catch (IOException e) {
			 log.info("Unable to Read key from properties file....");
		}
		 if(uploadlist!=null){
			Iterator<ApplnDocTO> uploaditr = uploadlist.iterator();
			while (uploaditr.hasNext()) {
				ApplnDocTO docTo = (ApplnDocTO) uploaditr.next();
				FormFile file = null;
				if(docTo!= null){
					file = docTo.getDocument();
				}
				if(file != null && maxSize < file.getFileSize())
				{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE));
				}
			}
		}
		 return errors;
	}
	
	/**
	 * Method to reset the form fields
	 * @param interviewResultEntryForm
	 */
	public void resetFields(InterviewResultEntryForm interviewResultEntryForm) {
		interviewResultEntryForm.setGradeObtainedId(null);
		interviewResultEntryForm.setComments(null);
		interviewResultEntryForm.setInterviewTypeId(null);
		interviewResultEntryForm.setInterviewStatusId(null);
		interviewResultEntryForm.setReferredById(null);
		interviewResultEntryForm.setApplicationNumber(null);
		interviewResultEntryForm.setApplicationYear(null);
		interviewResultEntryForm.setProgramTypeId(null);
		interviewResultEntryForm.setProgramId(null);
		interviewResultEntryForm.setCourseId(null);
		interviewResultEntryForm.setInterviewSubroundId(null);
	}
	
	/**
	 * Method to retrieve the semester mark details of an applicant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init detail mark page...");
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		
		try {
			String indexString = request.getParameter(EDIT_COUNT_ID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null){
					session.setAttribute(EDIT_COUNT_ID, indexString);
					int index=Integer.parseInt(indexString);
					List<EdnQualificationTO> quals=interviewResultEntryForm.getApplicantDetails().getEdnQualificationList();
					
					if(quals!=null ){
						Iterator<EdnQualificationTO> qualItr=quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if(qualTO.getId()==index){
								if(qualTO.getSemesterList()!=null){
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize=semList.size();
									int sizeDiff=CMSConstants.MAX_CANDIDATE_SEMESTERS-listSize;
									if(sizeDiff>0){
										for(int i=listSize+1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
											ApplicantMarkDetailsTO markDetailsTo= new ApplicantMarkDetailsTO();
											markDetailsTo.setSemesterNo(i);
											markDetailsTo.setSemesterName("Semester"+i);
											semList.add(markDetailsTo);
										}
									}
									Collections.sort(semList);
									interviewResultEntryForm.setSemesterList(semList);
								}
								interviewResultEntryForm.setIsLanguageWiseMarks(String.valueOf(qualTO.isLanguage()));
							}
						}
					}
				}
				else
					session.removeAttribute(EDIT_COUNT_ID);
			}
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_SEMESTER_MARK_PAGE);
	}
	
	/**
	 * Method to retrieve the detailed marks of an applicant 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("enter init detail mark page...");
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		try {
			String indexString = request.getParameter(EDIT_COUNT_ID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null){
					session.setAttribute(EDIT_COUNT_ID, indexString);
					int index=Integer.parseInt(indexString);
					List<EdnQualificationTO> quals=interviewResultEntryForm.getApplicantDetails().getEdnQualificationList();
					if(quals!=null ){
						Iterator<EdnQualificationTO> qualItr=quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if(qualTO.getId()==index){
								if(qualTO.getDetailmark()!=null){
									interviewResultEntryForm.setDetailMark(qualTO.getDetailmark());
								} else{
									interviewResultEntryForm.setDetailMark(new CandidateMarkTO());
								}	
							}
						}
					}
				} else {
					session.removeAttribute(EDIT_COUNT_ID);
				}	
			}
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_DETAIL_MARK_PAGE);
	}
	
	/**
	 * Method to retrieve the Lateral detailed marks of an applicant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLateralEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewLateralEntryPage...");
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		try {
			if(interviewResultEntryForm.getApplicantDetails().getLateralDetails()==null || interviewResultEntryForm.getApplicantDetails().getLateralDetails().isEmpty() ){
				List<ApplicantLateralDetailsTO> lateralDetails= new ArrayList<ApplicantLateralDetailsTO>();
				for(int i=0;i<CMSConstants.MAX_CANDIDATE_LATERALS;i++){
					ApplicantLateralDetailsTO lateralDetailsTo= new ApplicantLateralDetailsTO();
					lateralDetails.add(lateralDetailsTo);
				}
				interviewResultEntryForm.setLateralDetails(lateralDetails);
			}else{
				if(interviewResultEntryForm.getApplicantDetails().getLateralDetails()!=null && !interviewResultEntryForm.getApplicantDetails().getLateralDetails().isEmpty()){
					Iterator<ApplicantLateralDetailsTO> latItr=interviewResultEntryForm.getApplicantDetails().getLateralDetails().iterator();
					while (latItr.hasNext()) {
						ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr.next();
						if(lateralTO!=null){
							if(lateralTO.getInstituteAddress()!=null && !StringUtils.isEmpty(lateralTO.getInstituteAddress())){
								interviewResultEntryForm.setLateralInstituteAddress(lateralTO.getInstituteAddress());
							}	
							if(lateralTO.getStateName()!=null && !StringUtils.isEmpty(lateralTO.getStateName())){
								interviewResultEntryForm.setLateralStateName(lateralTO.getStateName());
							}	
							if(lateralTO.getUniversityName()!=null && !StringUtils.isEmpty(lateralTO.getUniversityName())){
								interviewResultEntryForm.setLateralUniversityName(lateralTO.getUniversityName());
							}	
						}
					}
					Collections.sort(interviewResultEntryForm.getApplicantDetails().getLateralDetails());
					interviewResultEntryForm.setLateralDetails(interviewResultEntryForm.getApplicantDetails().getLateralDetails());
				}
			}
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit viewLateralEntryPage...");
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_LATERAL_DETAIL_PAGE);
	}
	
	/**
	 * Method to retrieve the Transfer detailed marks of an applicant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTransferEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewTransferEntryPage...");
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		try {
			if(interviewResultEntryForm.getApplicantDetails().getTransferDetails()==null || interviewResultEntryForm.getApplicantDetails().getTransferDetails().isEmpty() ){
				List<ApplicantTransferDetailsTO> lateralDetails= new ArrayList<ApplicantTransferDetailsTO>();
				for(int i=0;i<CMSConstants.MAX_CANDIDATE_TRANSFERS;i++){
					ApplicantTransferDetailsTO transferDetailsTo= new ApplicantTransferDetailsTO();
					transferDetailsTo.setSemesterNo(i);
					lateralDetails.add(transferDetailsTo);
				}
				Collections.sort(lateralDetails);
				interviewResultEntryForm.setTransferDetails(lateralDetails);
			}else{
				if(interviewResultEntryForm.getApplicantDetails().getTransferDetails()!=null && !interviewResultEntryForm.getApplicantDetails().getTransferDetails().isEmpty()){

					Iterator<ApplicantTransferDetailsTO> latItr=interviewResultEntryForm.getApplicantDetails().getTransferDetails().iterator();
					while (latItr.hasNext()) {
						ApplicantTransferDetailsTO lateralTO = (ApplicantTransferDetailsTO) latItr.next();
						if(lateralTO!=null){
							if(lateralTO.getInstituteAddr()!=null && !StringUtils.isEmpty(lateralTO.getInstituteAddr())){
								interviewResultEntryForm.setTransInstituteAddr(lateralTO.getInstituteAddr());
							}
							if(lateralTO.getRegistationNo()!=null && !StringUtils.isEmpty(lateralTO.getRegistationNo())){
								interviewResultEntryForm.setTransRegistationNo(lateralTO.getRegistationNo());
							}	
							if(lateralTO.getUniversityAppNo()!=null && !StringUtils.isEmpty(lateralTO.getUniversityAppNo())){
								interviewResultEntryForm.setTransUnvrAppNo(lateralTO.getUniversityAppNo());
							}	
							if(lateralTO.getArrearDetail()!=null && !StringUtils.isEmpty(lateralTO.getArrearDetail())){
								interviewResultEntryForm.setTransArrearDetail(lateralTO.getArrearDetail());
							}	
						}
					}
					Collections.sort(interviewResultEntryForm.getApplicantDetails().getTransferDetails());
					interviewResultEntryForm.setTransferDetails(interviewResultEntryForm.getApplicantDetails().getTransferDetails());
				}
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewResultEntryForm.setErrorMessage(msg);
			interviewResultEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit viewTransferEntryPage...");
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_TRANSFER_DETAIL_PAGE);
	}
}