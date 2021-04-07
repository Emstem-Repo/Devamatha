package com.kp.cms.actions.admission;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
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
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.handlers.admin.LanguageHandler;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.RemarkTypeHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.StudentDisciplinaryDetailsHandler;
import com.kp.cms.handlers.admission.StudentEditHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.fee.FeeCategoryHandler;
import com.kp.cms.handlers.hostel.HostelDisciplinaryDetailsHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ParishTo;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.to.admission.StudentQualifyExamDetailsTO;
import com.kp.cms.to.admission.StudentRemarksTO;
import com.kp.cms.to.exam.ExamStudentDetentionDetailsTO;
import com.kp.cms.to.exam.ExamStudentPreviousClassTo;
import com.kp.cms.to.fee.FeeCategoryTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.transactions.admission.IStudentEditTransaction;
import com.kp.cms.transactionsimpl.admin.DioceseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.ParishTransactionImpl;
import com.kp.cms.transactionsimpl.admission.StudentDisciplinaryDetailsTransactionImpl;
import com.kp.cms.transactionsimpl.admission.StudentEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * 
 * Class implemented to add and edit student biodata
 * 
 */
@SuppressWarnings("deprecation")
public class StudentDiscilpinAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentDiscilpinAction.class);

	private static final String MESSAGE_KEY = "messages";
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static final String COUNTID = "countID";
	private static final String PHOTOBYTES = "PhotoBytes";
	private static final String EDITCOUNTID = "editcountID";

	/**
	 * initializes student edit page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initStudentEditForDiscipline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentEditForm stForm = (StudentEditForm) form;
		try {
			setUserId(request, stForm);
			cleanupFormFromSession(stForm);
			cleanupEditSessionData(request);
			cleanupTempValuesSession(stForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler
					.getInstance().getProgramType();
			stForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		stForm.clearDiscipDetails();
		return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_INITEDITPAGE);
	}
	/**
	 * cleans up form data from session
	 * 
	 * @param session
	 */
	private void cleanupFormFromSession(StudentEditForm stForm) {
		log.info("enter cleanupFormFromSession...");
		stForm.setIsView(null);
		stForm.setApplicationNo(null);
		stForm.setRegNo(null);
		stForm.setRollNo(null);
		stForm.setClassSchemeId(0);
		stForm.setProgramId(null);
		stForm.setCourseId(null);
		stForm.setProgramTypeId(null);
		stForm.setFirstName(null);
		stForm.setSemister(null);
		stForm.setDisplayMotherTongue(false);
		stForm.setDisplayLanguageKnown(false);
		stForm.setDisplayHeightWeight(false);
		stForm.setDisplayTrainingDetails(false);
		stForm.setDisplayAdditionalInfo(false);
		stForm.setDisplayExtracurricular(false);
		stForm.setDisplaySecondLanguage(false);
		stForm.setDisplayFamilyBackground(false);
		stForm.setDisplayTCDetails(false);
		stForm.setTcDetails(false);
		stForm.setDisplayEntranceDetails(false);
		stForm.setDisplayLateralDetails(false);
		stForm.setDisplayTransferCourse(false);
		stForm.setSameTempAddr(false);
		stForm.setQuotaCheck(false);
		stForm.setEligibleCheck(false);
		stForm.setAccessRemarks(true);
		stForm.setEditRemarks(true);
		stForm.setViewRemarks(false);
		stForm.setDetantiondetailReasons(null);
		stForm.setDetentiondetailsDate(null);
		stForm.setDetentionDetailsLink(null);
		stForm.setDetentiondetailsRadio(null);
		stForm.setHidedetailReasons(null);
		stForm.setHidedetailsDate(null);
		stForm.setIsHide(null);
		stForm.setDiscontinuedDetailsDate(null);
		stForm.setDiscontinuedDetailsLink(null);
		stForm.setDiscontinuedDetailsRadio(null);
		stForm.setDiscontinuedDetailsReasons(null);
		stForm.setReadmittedClass(null);
		stForm.setRejoinDetailsDate(null);
		stForm.setRejoinDetailsLink(null);
		stForm.setReMark(null);
		stForm.setApplicantDetails(null);
		stForm.setExamStudentBiodataId(0);
		stForm.setDetentionId(0);
		stForm.setDisContinuedId(0);
		stForm.setIsCjc(false);
		stForm.setYearOfPass(null);
		stForm.setCollegeCode(null);
		log.info("exit cleanupFormFromSession...");
	}
	/**
	 * clean up session after edit done
	 * 
	 * @param request
	 */
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(StudentDiscilpinAction.PHOTOBYTES) != null)
				session.removeAttribute(StudentDiscilpinAction.PHOTOBYTES);
		}
	}
	private void cleanupTempValuesSession(StudentEditForm stForm) {
		stForm.setTempYear(null);
		stForm.setApplicationNo(null);
		stForm.setTempRegNo(null);
		stForm.setTempRollNo(null);
		stForm.setTempcourseId(null);
		stForm.setTempProgId(null);
		stForm.setTempFirstName(null);
		stForm.setTempSemNo(null);
		stForm.setTempProgTypeId(null);
	}
	public ActionForward getSearchedStudentsForDiscipline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentEditForm stForm = (StudentEditForm) form;
		ActionMessages errors = stForm.validate(mapping, request);
		try {
			stForm.setExamStudentBiodataId(0);
			stForm.setDetentionId(0);
			stForm.setDisContinuedId(0);
			stForm.setIsHide(false);
			stForm.setIsView("editView");
			
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.STUDENT_DISCIPLINE_INITEDITPAGE);
			}
			stForm.setTempYear(stForm.getAcademicYear());
			stForm.setTempApplNo(stForm.getApplicationNo());
			stForm.setTempRegNo(stForm.getRegNo());
			stForm.setTempRollNo(stForm.getRollNo());
			stForm.setTempcourseId(stForm.getCourseId());
			stForm.setTempProgId(stForm.getProgramId());
			stForm.setTempFirstName(stForm.getFirstName());
			stForm.setTempSemNo(stForm.getSemister());
			stForm.setTempProgTypeId(stForm.getProgramTypeId());
			StudentEditHandler handler = StudentEditHandler.getInstance();
			// checking whether the student is canceled or not
			boolean isActive = false;
			if((stForm.getApplicationNo()!=null && !stForm.getApplicationNo().isEmpty()) || (stForm.getRegNo()!=null && !stForm.getRegNo().isEmpty()) || (stForm.getRollNo()!=null && !stForm.getRollNo().isEmpty())){
				isActive = handler.checkStudentIsActive(stForm);
			}
			if(isActive){
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_STUDENT_ISACTIVE, stForm.getReMark());
				errors.add(StudentDiscilpinAction.MESSAGE_KEY, message);
				saveErrors(request, errors);
				stForm.setReMark(null);
				return mapping
						.findForward(CMSConstants.STUDENT_DISCIPLINE_INITEDITPAGE);
			}
			List<StudentTO> studenttoList = handler.getSearchedStudents(stForm);
			if (studenttoList == null || studenttoList.isEmpty()) {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(StudentDiscilpinAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.STUDENT_DISCIPLINE_INITEDITPAGE);

			}
			stForm.setStudentTOList(studenttoList);
			//stream map
			if(stForm.getStreamMap()!=null && stForm.getStreamMap().size()!=0 ){
				
			}else{
				Map<Integer,String> streamMap=AdmissionFormHandler.getInstance().getStreamMap();
				if(streamMap.size()!=0){
					stForm.setStreamMap(streamMap);
				}else{
					stForm.setStreamMap(new HashMap<Integer, String>());
				}
				
				
			}
			
			
			
			
		} catch (ApplicationException e) {
			log.error("error in getSearchedStudents...", e);
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			throw e;

		}
		log.info("exit getSearchedStudents..");
		return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDITLISTPAGE);
	}
	public ActionForward StudentEditForDiscipline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentEditForm stForm = (StudentEditForm) form;
		try {
			setUserId(request, stForm);
			cleanupFormFromSession(stForm);
			cleanupEditSessionData(request);
			cleanupTempValuesSession(stForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler
					.getInstance().getProgramType();
			stForm.setProgramTypeList(programTypeList);
			stForm.setStudentId(Integer.parseInt(stForm.getSelectedAppNo()));
			setDisciplinesListToRequest(stForm,request);
			stForm.clearDiscipDetails();
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		request.setAttribute("ACHIEV_IMAGE", CMSConstants.EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH+"E"+stForm.getStudentId()+".jpg"+new Date());
		return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
	}
	public void setDisciplinesListToRequest(StudentEditForm studentEditForm,HttpServletRequest request) throws Exception {
		log.info("Start of setDisciplinesListToRequest in HostelDisciplinaryDetailsAction class");
		List<DisciplineAndAchivementTo> disciplineAndAchivementToList=StudentDisciplinaryDetailsHandler.getInstance().getStudentDisciplinesList(studentEditForm);
		studentEditForm.setDiscipAndAchieve(disciplineAndAchivementToList);
		log.info("End of setDisciplinesListToRequest in HostelDisciplinaryDetailsAction class");
	}
	
	public void validateFile(StudentEditForm stform, ActionErrors errors) {
		try{
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
		int maxPhotoSize=0;
		Properties prop=new Properties();
		prop.load(propStream);
		maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		FormFile file=null;
		if(stform.getAchivementFile()!=null)
			file=stform.getAchivementFile();
		boolean photoError = false;
		if( file!=null && maxPhotoSize< file.getFileSize()){
			if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
				errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
				photoError = true;
			}
		}
		if(file!=null ){
			String extn="";
			int index = file.getFileName().lastIndexOf(".");
			if(index != -1){
				extn = file.getFileName().substring(index, file.getFileName().length());
			}
			if(!extn.isEmpty() && (!extn.equalsIgnoreCase(".jpg") && !extn.equalsIgnoreCase(".pdf"))){
				if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
					photoError = true;
				}
			}
			if (!extn.isEmpty()) {
				if (extn==".jpg" || extn.equalsIgnoreCase(".jpg")) {
					stform.setContentType(".jpg");
				}
				if (extn==".pdf" || extn.equalsIgnoreCase(".pdf")) {
					stform.setContentType(".pdf");
				}
			}
		}}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ActionForward addHostelStudentDisciplineDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("Start of addStudentDisciplineDetails in StudentDiscilpinAction class");
		StudentEditForm studentEditForm=(StudentEditForm) form;
		boolean detialsAdded = false;
		boolean detialsFileAdded = false;
		ActionErrors errors = studentEditForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		addErrors(request,errors);
		validateFile(studentEditForm, errors);
		try {
			if(errors.isEmpty()){
				setUserId(request, studentEditForm);
					detialsFileAdded=StudentDisciplinaryDetailsHandler.getInstance().saveDocument(studentEditForm);
					detialsAdded=StudentDisciplinaryDetailsHandler.getInstance().addHostelStudentDisciplineDetails(studentEditForm);
					if (detialsAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.DISCIPLINARY_DETAILS_ADD_SUCCESS));
						saveMessages(request, messages);
						setDisciplinesListToRequest(studentEditForm,request);
						studentEditForm.clearDiscipDetails();
						return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
					} else {
						errors.add("errors", new ActionError(CMSConstants.DISCIPLINARY_DETAILS_ADD_FAILED));
						saveErrors(request, errors);
					}
					
			} else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);			
			studentEditForm.setErrorMessage(msg);
			studentEditForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDITLISTPAGE);
	}
public ActionForward deleteHostelStudentDisciplineDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("Start of addStudentDisciplineDetails in StudentDiscilpinAction class");
		StudentEditForm studentEditForm=(StudentEditForm) form;
		boolean detialsDeleted = false;
		ActionErrors errors = studentEditForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		addErrors(request,errors);
		try {
			if(errors.isEmpty()){
				setUserId(request, studentEditForm);

				detialsDeleted=StudentDisciplinaryDetailsHandler.getInstance().deleteStudentDisciplineDetails(Integer.parseInt(studentEditForm.getDiscipAndAchivId()), studentEditForm.getUserId());
					if (detialsDeleted) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.DISCIPLINARY_DETAILS_ADD_SUCCESS));
						saveMessages(request, messages);
						setDisciplinesListToRequest(studentEditForm,request);
						studentEditForm.clearDiscipDetails();
						return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
					} else {
						errors.add("errors", new ActionError(CMSConstants.DISCIPLINARY_DETAILS_ADD_FAILED));
						saveErrors(request, errors);
					}
					
			} else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);			
			studentEditForm.setErrorMessage(msg);
			studentEditForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDITLISTPAGE);
	}

public ActionForward editHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("Entering into---- HostelDisciplinaryDetailsAction --- editHostelStudentDisciplineDetails");
	StudentEditForm studentEditForm=(StudentEditForm)form;
	
	try {
		/**
		 * Get the particular row based on the id while clicking edit button
		 */
		DisciplineAndAchivementTo disciplineAndAchivementTo=StudentDisciplinaryDetailsHandler.getInstance().getDetailsonId(Integer.parseInt(studentEditForm.getDiscipAndAchivId()));
			
			if(disciplineAndAchivementTo!=null){
				studentEditForm.setDisciplineDescy(disciplineAndAchivementTo.getDescryption());
				studentEditForm.setStudentId(disciplineAndAchivementTo.getStudentId());
				//studentEditForm.setDiscipDate(disciplineAndAchivementTo.getDate());
				studentEditForm.setDiscipOrAchieve(disciplineAndAchivementTo.getType());
				studentEditForm.setFileName(disciplineAndAchivementTo.getFileName());
			}
			
		//setStudentInfoToRequest(request, hldForm);	
		request.setAttribute("disciplinaryOperation", CMSConstants.EDIT_OPERATION);
		//setDisciplinesListToRequest(request);
	} catch (Exception e) {
		log.error("Error in editing DisciplinaryDetailsAction in Action",e);
			String msg = super.handleApplicationException(e);
			studentEditForm.setErrorMessage(msg);
			studentEditForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	log.info("Leaving from-- DisciplinaryDetailsAction --- editHostelStudentDisciplineDetails");
	return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
}

public ActionForward updateHostelStudentDisciplineDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	log.info("Entering into-- HostelDisciplinaryDetailsAction --- updateHostelStudentDisciplineDetails");
	StudentEditForm studentEditForm=(StudentEditForm)form; 
	 ActionErrors errors = studentEditForm.validate(mapping, request);
	ActionMessages messages = new ActionMessages();
	try {
		if(errors.isEmpty())
		{
			setUserId(request, studentEditForm);
			
			boolean isUpdated;
			validateFile(studentEditForm, errors);
			StudentDisciplinaryDetailsHandler.getInstance().saveDocument(studentEditForm);
					isUpdated=StudentDisciplinaryDetailsHandler.getInstance().updateHostelStudentDisciplineDetails(studentEditForm);
					
					//If update is successful then add the success message else show the error message
					
					if(isUpdated){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_UPDATE_SUCCESS));
						saveMessages(request, messages);
						studentEditForm.clearDiscipDetails();
						//hldForm.clear();
					}
					else {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_DISCIPLINARY_DETAILS_UPDATE_FAILED));
						saveErrors(request, errors);
					}

		}
	else{
		request.setAttribute("disciplineOperation",CMSConstants.EDIT_OPERATION);
	}
	}catch (Exception e) {
		log.error("Error in updating HostelDisciplinaryDetailsAction in updateHostelStudentDisciplineDetails",e);
			String msg = super.handleApplicationException(e);
			studentEditForm.setErrorMessage(msg);
			studentEditForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDisciplinesListToRequest(studentEditForm,request);
	log.info("Leaving from --- HostelDisciplinaryDetailsAction --- updateHostelStudentDisciplineDetails");
	saveErrors(request, errors);
	return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
}


public ActionForward downloadFile(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	StudentEditForm stForm = (StudentEditForm) form;
	try {
		String extn="";
		int index = stForm.getFileName().lastIndexOf(".");
		extn = stForm.getFileName().substring(index+1, stForm.getFileName().length());
		response.setContentType(extn+"/html");
		PrintWriter out = response.getWriter();
		String file = stForm.getFileName();
		String filepath = CMSConstants.EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH;
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file + "\"");
 
		FileInputStream fileInputStream = new FileInputStream(filepath
				+ file);
 
		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.close();
	} catch (Exception e) {
		log.error("error in initStudentEdit...", e);
		throw e;
	}
	request.setAttribute("ACHIEV_IMAGE", CMSConstants.EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH+"E"+stForm.getStudentId()+".jpg"+new Date());
	return mapping.findForward(CMSConstants.STUDENT_DISCIPLINE_EDIT_PAGE);
}
	
}



