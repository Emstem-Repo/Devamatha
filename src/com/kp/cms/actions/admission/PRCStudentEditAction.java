package com.kp.cms.actions.admission;

import java.io.IOException;
import java.io.InputStream;
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
import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.StudentEditForm;
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
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.StudentEditHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
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
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.to.admission.StudentQualifyExamDetailsTO;
import com.kp.cms.to.admission.StudentRemarksTO;
import com.kp.cms.to.exam.ExamStudentDetentionDetailsTO;
import com.kp.cms.transactions.admission.IStudentEditTransaction;
import com.kp.cms.transactionsimpl.admission.StudentEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class PRCStudentEditAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PRCStudentEditAction.class);

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
	public ActionForward initStudentEdit(ActionMapping mapping,
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
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_INITEDITPAGE);

	}

	/**
	 * gets list of students as per search criteria
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSearchedStudents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentEditForm stForm = (StudentEditForm) form;
		ActionMessages errors = stForm.validate(mapping, request);
		
		try {
			stForm.setExamStudentBiodataId(0);
			stForm.setDetentionId(0);
			stForm.setDisContinuedId(0);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_INITEDITPAGE);
			}
			
			stForm.setTempYear(stForm.getAcademicYear());
			stForm.setApplicationNo(stForm.getApplicationNo());
			stForm.setTempRegNo(stForm.getRegNo());
			stForm.setTempRollNo(stForm.getRollNo());
			stForm.setTempcourseId(stForm.getCourseId());
			stForm.setTempProgId(stForm.getProgramId());
			stForm.setTempFirstName(stForm.getFirstName());
			stForm.setTempSemNo(stForm.getSemister());
			stForm.setTempProgTypeId(stForm.getProgramTypeId());
			
			StudentEditHandler handler = StudentEditHandler.getInstance();
			List<StudentTO> studenttoList = handler.getSearchedStudents(stForm);
			if (studenttoList == null || studenttoList.isEmpty()) {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_INITEDITPAGE);

			}
			stForm.setStudentTOList(studenttoList);
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
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITLISTPAGE);
	}

	/**
	 * validate programtype,course and program
	 * 
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateProgramCourse(ActionMessages errors,
			StudentEditForm stForm) {
		log.info("enter validate program course...");
		if (stForm.getProgramTypeId() == null
				|| stForm.getProgramTypeId().length() == 0) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED,
						error);
			} else {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED,
						error);
			}
		}
		if (stForm.getProgramId() == null
				|| stForm.getProgramId().length() == 0) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			} else {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}
		}
		if (stForm.getCourseId() == null || stForm.getCourseId().length() == 0) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			} else {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			}
		}
		log.info("exit validate program course...");
		return errors;
	}

	/**
	 * cleans up form data from session
	 * 
	 * @param session
	 */
	private void cleanupFormFromSession(StudentEditForm stForm) {
		log.info("enter cleanupFormFromSession...");
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
		
		log.info("exit cleanupFormFromSession...");
	}

	/**
	 * fetches details of student to edit or view
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentEditForm stForm = (StudentEditForm) form;
		try {
			StudentEditHandler handler = StudentEditHandler.getInstance();
			AdmApplnTO applicantDetails = handler.getStudentDetails(stForm);
			stForm.setExamStudentBiodataId(0);
			stForm.setDetentionId(0);
			stForm.setDisContinuedId(0);
			
			List<ExamStudentDetentionDetailsTO> detTOList = handler.getDetentionHistory(stForm.getOriginalStudent().getId(), true);
			List<ExamStudentDetentionDetailsTO> disContinuedTOList = handler.getDetentionHistory(stForm.getOriginalStudent().getId(), false);
			List<ExamStudentDetentionDetailsTO> rejoinHistoryList = handler.studentRejoinHistory(stForm.getOriginalStudent().getId());
			
			stForm.setDetentionDetHistoryList(detTOList);
			stForm.setDiscontinuedDetHistoryList(disContinuedTOList);
			stForm.setRejoinDetHistoryList(rejoinHistoryList);
			
			if (applicantDetails != null) {
				// get states list for edn qualification
				List<StateTO> ednstates = StateHandler.getInstance()
						.getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
				stForm.setEdnStates(ednstates);

				List<EntrancedetailsTO> entrnaceList = EntranceDetailsHandler
						.getInstance().getEntranceDeatilsByProgram(
								applicantDetails.getSelectedCourse()
										.getProgramId());
				stForm.setEntranceList(entrnaceList);

				if (stForm.getOriginalStudent() != null) {
					stForm.setRollNo(stForm.getOriginalStudent().getRollNo());
					stForm.setRegNo(stForm.getOriginalStudent().getRegisterNo());
					if(stForm.getOriginalStudent().getRegisterNo()!=null)
					stForm.setOriginalRegNo(stForm.getOriginalStudent().getRegisterNo());
					else
						stForm.setOriginalRegNo("");
					
					stForm.setExamRegNo(stForm.getOriginalStudent().getExamRegisterNo());
					stForm.setStudentNo(stForm.getOriginalStudent().getStudentNo());
					stForm.setBankAccNo(stForm.getOriginalStudent().getBankAccNo());
					
					if (stForm.getOriginalStudent().getClassSchemewise() != null) {
						stForm.setClassSchemeId(stForm.getOriginalStudent()
								.getClassSchemewise().getId());
						if (stForm.getOriginalStudent().getClassSchemewise()
								.getClasses() != null) {
							stForm.setClassSchemeName(stForm
									.getOriginalStudent().getClassSchemewise()
									.getClasses().getName());
						}
					}
				}
				if (applicantDetails.getPersonalData() != null
						&& applicantDetails.getPersonalData().getDob() != null) {
					applicantDetails.getPersonalData().setDob(
							CommonUtil
									.ConvertStringToDateFormat(applicantDetails
											.getPersonalData().getDob(),
											PRCStudentEditAction.SQL_DATEFORMAT,
											PRCStudentEditAction.FROM_DATEFORMAT));
				}
				if (applicantDetails.getChallanDate() != null) {
					applicantDetails.setChallanDate(CommonUtil
							.ConvertStringToDateFormat(applicantDetails
									.getChallanDate(),
									PRCStudentEditAction.SQL_DATEFORMAT,
									PRCStudentEditAction.FROM_DATEFORMAT));
				}
				if (applicantDetails.getAdmissionDate() != null) {
					applicantDetails.setAdmissionDate(CommonUtil
							.ConvertStringToDateFormat(applicantDetails
									.getAdmissionDate(),
									PRCStudentEditAction.SQL_DATEFORMAT,
									PRCStudentEditAction.FROM_DATEFORMAT));
				}

				stForm.setApplicantDetails(applicantDetails);
				String workExpNeeded = stForm.getApplicantDetails().getCourse()
						.getIsWorkExperienceRequired();
				if (stForm.getApplicantDetails().getCourse() != null
						&& "Yes".equalsIgnoreCase(workExpNeeded)) {
					stForm.setWorkExpNeeded(true);
				} else {
					stForm.setWorkExpNeeded(false);
				}
				stForm.setApplicantDetails(applicantDetails);
				ProgramTypeHandler programtypehandler = ProgramTypeHandler
						.getInstance();
				List<ProgramTypeTO> programtypes = programtypehandler
						.getProgramType();
				CourseTO applicantCourse = applicantDetails.getCourse();
				CourseTO selectedCourse = applicantDetails.getSelectedCourse();
				if (programtypes != null) {
					stForm.setEditProgramtypes(programtypes);
					Iterator<ProgramTypeTO> typeitr = programtypes.iterator();
					while (typeitr.hasNext()) {
						ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
						if (typeTO.getProgramTypeId() == selectedCourse
								.getProgramTypeId()) {
							if (typeTO.getPrograms() != null) {
								stForm.setEditprograms(typeTO.getPrograms());
								Iterator<ProgramTO> programitr = typeTO
										.getPrograms().iterator();
								while (programitr.hasNext()) {
									ProgramTO programTO = (ProgramTO) programitr
											.next();
									if (programTO.getId() == selectedCourse
											.getProgramId()) {
										stForm.setEditcourses(programTO
												.getCourseList());
										// sets program level configs
										if (programTO != null) {
											stForm
													.setDisplayMotherTongue(programTO
															.getIsMotherTongue());
											stForm
													.setDisplayLanguageKnown(programTO
															.getIsDisplayLanguageKnown());
											stForm
													.setDisplayHeightWeight(programTO
															.getIsHeightWeight());
											stForm
													.setDisplayTrainingDetails(programTO
															.getIsDisplayTrainingCourse());
											stForm
													.setDisplayAdditionalInfo(programTO
															.getIsAdditionalInfo());
											stForm
													.setDisplayExtracurricular(programTO
															.getIsExtraDetails());
											stForm
													.setDisplaySecondLanguage(programTO
															.getIsSecondLanguage());
											stForm
													.setDisplayFamilyBackground(programTO
															.getIsFamilyBackground());
											stForm
													.setDisplayTCDetails(programTO
															.getIsTCDetails());
											stForm
													.setDisplayEntranceDetails(programTO
															.getIsEntranceDetails());
											stForm
													.setDisplayLateralDetails(programTO
															.getIsLateralDetails());
											stForm
													.setDisplayTransferCourse(programTO
															.getIsTransferCourse());
										}
									}
								}
							}
						}
					}
				}

				checkExtradetailsDisplay(stForm);
				checkLateralTransferDisplay(stForm);
				
				List<CountryTO> countryList = CountryHandler.getInstance().getCountries();
				
				List<CountryTO> birthCountries = countryList;//CountryHandler.getInstance().getCountries();
				if (/*CountryHandler.getInstance().getCountries()*/birthCountries != null) {
					// birthCountry & states
					/*List<CountryTO> birthCountries = CountryHandler
							.getInstance().getCountries();*/
					if (birthCountries != null) {
						stForm.setCountries(birthCountries);
						Iterator<CountryTO> cntitr = birthCountries.iterator();
						while (cntitr.hasNext()) {
							CountryTO countryTO = (CountryTO) cntitr.next();
							if (stForm.getApplicantDetails().getPersonalData()
									.getBirthCountry() != null
									&& !StringUtils.isEmpty(stForm
											.getApplicantDetails()
											.getPersonalData()
											.getBirthCountry())
									&& StringUtils.isNumeric(stForm
											.getApplicantDetails()
											.getPersonalData()
											.getBirthCountry())
									&& countryTO.getId() == Integer
											.parseInt(stForm
													.getApplicantDetails()
													.getPersonalData()
													.getBirthCountry())
									&& stForm.getApplicantDetails()
											.getPersonalData() != null) {
								List<StateTO> stateList = countryTO
										.getStateList();
								stForm.setEditStates(stateList);
							}
						}
					}

					// permanentCountry & states
					List<CountryTO> permanentCountries =  countryList; /* CountryHandler
							.getInstance().getCountries();*/
					if (permanentCountries != null) {
						stForm.setCountries(permanentCountries);
						Iterator<CountryTO> cntitr = permanentCountries
								.iterator();
						while (cntitr.hasNext()) {
							CountryTO countryTO = (CountryTO) cntitr.next();
							if (countryTO.getId() == stForm
									.getApplicantDetails().getPersonalData()
									.getPermanentCountryId()) {
								List<StateTO> stateList = countryTO
										.getStateList();
								stForm.setEditPermanentStates(stateList);
							}
						}
					}

					// currentCountry & states
					List<CountryTO> currentCountries = countryList;/* CountryHandler
							.getInstance().getCountries();*/
					if (currentCountries != null) {
						stForm.setCountries(currentCountries);
						Iterator<CountryTO> cntitr = currentCountries
								.iterator();
						while (cntitr.hasNext()) {
							CountryTO countryTO = (CountryTO) cntitr.next();
							if (countryTO.getId() == stForm
									.getApplicantDetails().getPersonalData()
									.getCurrentCountryId()) {
								List<StateTO> stateList = countryTO
										.getStateList();
								stForm.setEditCurrentStates(stateList);
							}
						}
					}
					Set<Student> studentSet = applicantDetails.getStudents();
					Iterator<Student> stItr = studentSet.iterator();
					Student newStudent = null;
					while (stItr.hasNext()) {
						Student student = (Student) stItr.next();
						newStudent = student;
					}
					
					/*if (newStudent.getClassSchemewise() != null
							&& newStudent.getClassSchemewise().getId() > 0) {
						int year = newStudent.getClassSchemewise()
								.getCurriculumSchemeDuration()
								.getAcademicYear();
						Map<Integer, String> classesMap = handler
								.getClassSchemeForStudent(selectedCourse
										.getId(), year);
						stForm.setClassesMap(classesMap);
					} else {
						Map<Integer, String> classesMap = handler
								.getClassSchemeForStudent(selectedCourse
										.getId(), applicantDetails
										.getAppliedYear());
						stForm.setClassesMap(classesMap);
					}*/
					
					
					// Code Return BY BALAJI For Classes
					Map<Integer, String> classesMap =StudentEditHandler.getInstance().getClassesBySelectedCourse(applicantDetails.getSelectedCourse().getId(),applicantDetails.getAppliedYear());
					stForm.setClassesMap(classesMap);
					
					Map<Integer, String> rejoinClassesMap = CommonAjaxHandler.getInstance().getClassesBySelectedCourse(selectedCourse.getId(), Integer.parseInt(CommonUtil.getCurrentYear()));
					stForm.setRejoinClassMap(rejoinClassesMap);
										
					// parentCountry & states
					List<CountryTO> parentCountries = countryList;/*CountryHandler
							.getInstance().getCountries();*/
					if (parentCountries != null) {
						stForm.setCountries(parentCountries);
						Iterator<CountryTO> cntitr = parentCountries.iterator();
						while (cntitr.hasNext()) {
							CountryTO countryTO = (CountryTO) cntitr.next();
							if (countryTO.getId() == stForm
									.getApplicantDetails().getPersonalData()
									.getParentCountryId()) {
								stForm.setEditParentStates(null);
								List<StateTO> stateList = countryTO
										.getStateList();
								stForm.setEditParentStates(stateList);
							}
						}
					}
				}

				// guardian states

				List<CountryTO> guardianCountries = countryList;/*CountryHandler
						.getInstance().getCountries();*/
				if (guardianCountries != null) {
					stForm.setCountries(guardianCountries);
					Iterator<CountryTO> cntitr = guardianCountries.iterator();
					while (cntitr.hasNext()) {
						CountryTO countryTO = (CountryTO) cntitr.next();
						if (countryTO.getId() == stForm.getApplicantDetails()
								.getPersonalData()
								.getCountryByGuardianAddressCountryId()) {
							stForm.setEditGuardianStates(null);
							List<StateTO> stateList = countryTO.getStateList();
							stForm.setEditGuardianStates(stateList);
						}
					}
				}
				OrganizationHandler orgHandler = OrganizationHandler.getInstance();
				List<OrganizationTO> tos = orgHandler.getOrganizationDetails();
				if (applicantDetails.getPersonalData() != null) {
					if (tos != null && !tos.isEmpty()) {
						OrganizationTO orgTO = tos.get(0);
						if (orgTO.getExtracurriculars() != null)
							applicantDetails.getPersonalData()
									.setStudentExtracurricularsTos(
											orgTO.getExtracurriculars());
					}

				}

				// Nationality
				AdmissionFormHandler formhandler = AdmissionFormHandler
						.getInstance();
				stForm.setNationalities(formhandler.getNationalities());
				// languages
				LanguageHandler langHandler = LanguageHandler.getHandler();
				stForm.setMothertongues(langHandler.getLanguages());
				stForm.setLanguages(langHandler.getOriginalLanguages());

				if (stForm.isDisplayAdditionalInfo()) {
					/*OrganizationHandler orgHandler = OrganizationHandler
							.getInstance();
					List<OrganizationTO> tos = orgHandler
							.getOrganizationDetails();*/
					if (tos != null && !tos.isEmpty()) {
						OrganizationTO orgTO = tos.get(0);
						stForm.setOrganizationName(orgTO.getOrganizationName());
						stForm.setNeedApproval(orgTO.isNeedApproval());
					}
				}

				// res. catg
				stForm.setResidentTypes(formhandler.getResidentTypes());

				ReligionHandler religionhandler = ReligionHandler.getInstance();
				List<ReligionTO> religionList = religionhandler.getReligion();
				if (/*religionhandler.getReligion() */religionList!= null) {
					/*List<ReligionTO> religionList = religionhandler
							.getReligion();*/
					stForm.setReligions(religionList);
					Iterator<ReligionTO> relItr = religionList.iterator();
					while (relItr.hasNext()) {
						ReligionTO relTO = (ReligionTO) relItr.next();
						if (stForm.getApplicantDetails().getPersonalData()
								.getReligionId() != null
								&& !StringUtils.isEmpty(stForm
										.getApplicantDetails()
										.getPersonalData().getReligionId())
								&& StringUtils.isNumeric(stForm
										.getApplicantDetails()
										.getPersonalData().getReligionId())
								&& relTO.getReligionId() == Integer
										.parseInt(stForm.getApplicantDetails()
												.getPersonalData()
												.getReligionId())) {
							List<ReligionSectionTO> subreligions = relTO
									.getSubreligions();
							stForm.setSubReligions(subreligions);
						}
					}
				}
				// caste category
				List<CasteTO> castelist = CasteHandler.getInstance()
						.getCastes();
				stForm.setCasteList(castelist);

				// Admitted Through
				List<AdmittedThroughTO> admittedList = AdmittedThroughHandler
						.getInstance().getAdmittedThrough();
				stForm.setAdmittedThroughList(admittedList);
				
				
				// subject Group
				/*List<SubjectGroupTO> sujectgroupList = SubjectGroupHandler
						.getInstance().getSubjectGroupDetails(
								selectedCourse.getId());
				stForm.setSubGroupList(sujectgroupList);*/
				
				
				// COde Return By Balaji 
				List<SubjectGroupTO> subjectList=StudentEditHandler.getInstance().getSubjectGroupList(applicantDetails.getSelectedCourse().getId(),applicantDetails.getAppliedYear());
				stForm.setSubGroupList(subjectList);
				String[] subjectgroups = applicantDetails.getSubjectGroupIds();
				if (subjectgroups != null && subjectgroups.length == 0
						&& subjectList != null) {
					subjectgroups = new String[subjectList.size()];
					applicantDetails.setSubjectGroupIds(subjectgroups);
				}
				
				// incomes
				List<IncomeTO> incomeList = AdmissionFormHandler.getInstance()
						.getIncomes();
				stForm.setIncomeList(incomeList);

				// currencies
				List<CurrencyTO> currencyList = AdmissionFormHandler
						.getInstance().getCurrencies();
				stForm.setCurrencyList(currencyList);

				UniversityHandler unhandler = UniversityHandler.getInstance();
				List<UniversityTO> universities = unhandler.getUniversity();
				stForm.setUniversities(universities);

				OccupationTransactionHandler occhandler = OccupationTransactionHandler
						.getInstance();
				stForm.setOccupations(occhandler.getAllOccupation());

				if (applicantDetails.getPersonalData() != null
						&& applicantDetails.getPersonalData()
								.getPassportValidity() != null
						&& !StringUtils.isEmpty(applicantDetails
								.getPersonalData().getPassportValidity()))
					applicantDetails.getPersonalData().setPassportValidity(
							CommonUtil.ConvertStringToDateFormat(
									applicantDetails.getPersonalData()
											.getPassportValidity(),
									PRCStudentEditAction.SQL_DATEFORMAT,
									PRCStudentEditAction.FROM_DATEFORMAT));

				// set photo to session
				if (applicantDetails.getEditDocuments() != null) {
					Iterator<ApplnDocTO> docItr = applicantDetails
							.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if (docTO.getSubmitDate() != null
								&& !StringUtils.isEmpty(docTO.getSubmitDate())) {
							stForm.setSubmitDate(docTO.getSubmitDate());
						}
						if (docTO.getDocName() != null
								&& docTO.getDocName().equalsIgnoreCase("Photo")) {
							HttpSession session = request.getSession(false);
							if (session != null) {
								session.setAttribute(
										PRCStudentEditAction.PHOTOBYTES, docTO
												.getPhotoBytes());
							}
						}
					}
				}

				// preferences
				if (applicantDetails.getPreference() != null) {
					PreferenceTO prefTo = applicantDetails.getPreference();
					List<CandidatePreferenceTO> prefTos = new ArrayList<CandidatePreferenceTO>();
					if (prefTo.getFirstPrefCourseId() != null
							|| !StringUtils.isEmpty(prefTo
									.getFirstPrefCourseId())) {
						CandidatePreferenceTO firstTo = new CandidatePreferenceTO();
						firstTo.setId(prefTo.getFirstPerfId());
						firstTo.setAdmApplnid(applicantDetails.getId());
						firstTo.setCoursId(prefTo.getFirstPrefCourseId());
						firstTo.setCoursName(prefTo.getFirstprefCourseName());
						firstTo.setProgId(prefTo.getFirstPrefProgramId());
						firstTo.setProgramtypeId(prefTo
								.getFirstPrefProgramTypeId());
						firstTo.setPrefNo(1);
						formhandler.populatePreferenceTO(firstTo,
								applicantCourse);
						prefTos.add(firstTo);
					} else {
						CandidatePreferenceTO firstTo = new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,
								applicantCourse);
						firstTo.setPrefNo(1);
						prefTos.add(firstTo);
					}
					if (prefTo.getSecondPrefCourseId() != null
							|| !StringUtils.isEmpty(prefTo
									.getSecondPrefCourseId())) {
						CandidatePreferenceTO secTo = new CandidatePreferenceTO();
						secTo.setId(prefTo.getSecondPerfId());
						secTo.setAdmApplnid(applicantDetails.getId());
						secTo.setCoursId(prefTo.getSecondPrefCourseId());
						secTo.setProgId(prefTo.getSecondPrefProgramId());
						secTo.setProgramtypeId(prefTo
								.getSecondPrefProgramTypeId());
						secTo.setPrefNo(2);
						formhandler
								.populatePreferenceTO(secTo, applicantCourse);
						prefTos.add(secTo);
					} else {
						CandidatePreferenceTO firstTo = new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,
								applicantCourse);
						firstTo.setPrefNo(2);
						prefTos.add(firstTo);
					}
					if (prefTo.getThirdPrefCourseId() != null
							|| !StringUtils.isEmpty(prefTo
									.getThirdPrefCourseId())) {
						CandidatePreferenceTO thirdTo = new CandidatePreferenceTO();
						thirdTo.setId(prefTo.getThirdPerfId());
						thirdTo.setPrefNo(3);
						thirdTo.setAdmApplnid(applicantDetails.getId());
						thirdTo.setCoursId(prefTo.getThirdPrefCourseId());
						thirdTo.setProgId(prefTo.getThirdPrefProgramId());
						thirdTo.setProgramtypeId(prefTo
								.getThirdPrefProgramTypeId());
						formhandler.populatePreferenceTO(thirdTo,
								applicantCourse);
						prefTos.add(thirdTo);
					} else {
						CandidatePreferenceTO firstTo = new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,
								applicantCourse);
						firstTo.setPrefNo(3);
						prefTos.add(firstTo);
					}
					stForm.setPreferenceList(prefTos);
				} else {
					List<CandidatePreferenceTO> prefTos = new ArrayList<CandidatePreferenceTO>();
					CandidatePreferenceTO firstTo = new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(firstTo, applicantCourse);
					firstTo.setPrefNo(1);
					prefTos.add(firstTo);
					CandidatePreferenceTO secTo = new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(secTo, applicantCourse);
					secTo.setPrefNo(2);
					prefTos.add(secTo);
					CandidatePreferenceTO thirdTo = new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo, applicantCourse);
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
					stForm.setPreferenceList(prefTos);
				}

			}
			// set user settings
			handler.setUserSettings(stForm);
			// set specialization 9-elements
			handler.setAllSpecialisationValues(stForm);
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
		if (stForm.isDetailsView())
			return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_VIEWPAGE);
		else
			return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}

	/**
	 * checks extra details block to be displayed or not
	 * 
	 * @param admForm
	 */
	private void checkExtradetailsDisplay(StudentEditForm stForm) {
		boolean isextra = false;
		if (stForm.isDisplayMotherTongue()) {
			isextra = true;
		}
		if (stForm.isDisplayHeightWeight()) {
			isextra = true;
		}
		if (stForm.isDisplayTrainingDetails()) {
			isextra = true;
		}
		if (stForm.isDisplayAdditionalInfo()) {
			isextra = true;
		}
		if (stForm.isDisplayExtracurricular()) {
			isextra = true;
		}
		if (stForm.isDisplayLanguageKnown()) {
			isextra = true;
		}
		stForm.setDisplayExtraDetails(isextra);

	}

	/**
	 * check lateral and trasfer link display
	 * 
	 * @param admForm
	 */
	private void checkLateralTransferDisplay(StudentEditForm stForm) {
		boolean isextra = false;

		if (stForm.isDisplayLateralDetails())
			isextra = true;
		if (stForm.isDisplayTransferCourse())
			isextra = true;
		stForm.setDisplayLateralTransfer(isextra);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDetailMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewDetailMarkPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {

			String indexString = request
					.getParameter(PRCStudentEditAction.EDITCOUNTID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null) {
					session.setAttribute(PRCStudentEditAction.EDITCOUNTID,
							indexString);
					int index = Integer.parseInt(indexString);
					List<EdnQualificationTO> quals = admForm
							.getApplicantDetails().getEdnQualificationList();
					if (quals != null) {
						Iterator<EdnQualificationTO> qualItr = quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
									.next();
							if (qualTO.getId() == index) {
								if (qualTO.getDetailmark() != null) {
									admForm.setDetailMark(qualTO
											.getDetailmark());
								} else {
									admForm
											.setDetailMark(new CandidateMarkTO());
								}
							}
						}
					}
				} else
					session.removeAttribute(PRCStudentEditAction.EDITCOUNTID);
			}

		} catch (Exception e) {
			log.error("error in viewDetailMarkPage...", e);
			throw e;

		}
		log.info("exit viewDetailMarkPage...");

		return mapping
				.findForward(CMSConstants.STUDENTEDIT_VIEW_DETAIL_MARK_PAGE);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSemesterMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter viewSemesterMarkPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {

			String indexString = request
					.getParameter(PRCStudentEditAction.EDITCOUNTID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null) {
					session.setAttribute(PRCStudentEditAction.EDITCOUNTID,
							indexString);
					int index = Integer.parseInt(indexString);
					List<EdnQualificationTO> quals = admForm
							.getApplicantDetails().getEdnQualificationList();

					if (quals != null) {
						Iterator<EdnQualificationTO> qualItr = quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
									.next();
							if (qualTO.getId() == index) {
								if (qualTO.getSemesterList() != null) {
									List<ApplicantMarkDetailsTO> semList = new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize = semList.size();
									int sizeDiff = CMSConstants.MAX_CANDIDATE_SEMESTERS
											- listSize;
									if (sizeDiff > 0) {
										for (int cnt = listSize + 1; cnt <= CMSConstants.MAX_CANDIDATE_SEMESTERS; cnt++) {
											ApplicantMarkDetailsTO markTo = new ApplicantMarkDetailsTO();
											markTo.setSemesterNo(cnt);
											markTo.setSemesterName("Semester"
													+ cnt);
											semList.add(markTo);
										}
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								} else {
									admForm
											.setSemesterList(new ArrayList<ApplicantMarkDetailsTO>());
								}
								admForm.setIsLanguageWiseMarks(String
										.valueOf(qualTO.isLanguage()));

							}
						}
					}
				} else
					session.removeAttribute(PRCStudentEditAction.EDITCOUNTID);
			}
		} catch (Exception e) {
			log.error("error in viewSemesterMarkPage...", e);
			throw e;

		}
		log.info("exit viewSemesterMarkPage...");

		return mapping.findForward(CMSConstants.STUDENTEDIT_VIEW_SEM_MARK_PAGE);
	}

	/**
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
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			if (admForm.getApplicantDetails().getLateralDetails() == null
					|| admForm.getApplicantDetails().getLateralDetails()
							.isEmpty()) {
				List<ApplicantLateralDetailsTO> lateralDetails = new ArrayList<ApplicantLateralDetailsTO>();
				for (int cnt = 0; cnt < CMSConstants.MAX_CANDIDATE_LATERALS; cnt++) {
					ApplicantLateralDetailsTO latTo = new ApplicantLateralDetailsTO();
					latTo.setSemesterNo(cnt);
					lateralDetails.add(latTo);
				}
				Collections.sort(lateralDetails);
				admForm.setLateralDetails(lateralDetails);
			} else {
				if (admForm.getApplicantDetails().getLateralDetails() != null
						&& !admForm.getApplicantDetails().getLateralDetails()
								.isEmpty()) {

					Iterator<ApplicantLateralDetailsTO> latItr = admForm
							.getApplicantDetails().getLateralDetails()
							.iterator();
					while (latItr.hasNext()) {
						ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr
								.next();
						if (lateralTO != null) {
							if (lateralTO.getInstituteAddress() != null
									&& !StringUtils.isEmpty(lateralTO
											.getInstituteAddress()))
								admForm.setLateralInstituteAddress(lateralTO
										.getInstituteAddress());
							if (lateralTO.getStateName() != null
									&& !StringUtils.isEmpty(lateralTO
											.getStateName()))
								admForm.setLateralStateName(lateralTO
										.getStateName());
							if (lateralTO.getUniversityName() != null
									&& !StringUtils.isEmpty(lateralTO
											.getUniversityName()))
								admForm.setLateralUniversityName(lateralTO
										.getUniversityName());
						}

					}
					Collections.sort(admForm.getApplicantDetails()
							.getLateralDetails());
					admForm.setLateralDetails(admForm.getApplicantDetails()
							.getLateralDetails());
				}

			}
		} catch (Exception e) {
			log.error("error in viewLateralEntryPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit viewLateralEntryPage...");

		return mapping
				.findForward(CMSConstants.STUDENTEDIT_VIEW_LATERAL_MARK_PAGE);
	}

	/**
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
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			if (admForm.getApplicantDetails().getTransferDetails() == null
					|| admForm.getApplicantDetails().getTransferDetails()
							.isEmpty()) {
				List<ApplicantTransferDetailsTO> lateralDetails = new ArrayList<ApplicantTransferDetailsTO>();
				for (int cnt = 0; cnt < CMSConstants.MAX_CANDIDATE_TRANSFERS; cnt++) {
					ApplicantTransferDetailsTO trnTo = new ApplicantTransferDetailsTO();
					trnTo.setSemesterNo(cnt);
					lateralDetails.add(trnTo);
				}
				Collections.sort(lateralDetails);
				admForm.setTransferDetails(lateralDetails);
			} else {
				if (admForm.getApplicantDetails().getTransferDetails() != null
						&& !admForm.getApplicantDetails().getTransferDetails()
								.isEmpty()) {

					Iterator<ApplicantTransferDetailsTO> latItr = admForm
							.getApplicantDetails().getTransferDetails()
							.iterator();
					while (latItr.hasNext()) {
						ApplicantTransferDetailsTO lateralTO = (ApplicantTransferDetailsTO) latItr
								.next();
						if (lateralTO != null) {
							if (lateralTO.getInstituteAddr() != null
									&& !StringUtils.isEmpty(lateralTO
											.getInstituteAddr()))
								admForm.setTransInstituteAddr(lateralTO
										.getInstituteAddr());
							if (lateralTO.getRegistationNo() != null
									&& !StringUtils.isEmpty(lateralTO
											.getRegistationNo()))
								admForm.setTransRegistationNo(lateralTO
										.getRegistationNo());
							if (lateralTO.getUniversityAppNo() != null
									&& !StringUtils.isEmpty(lateralTO
											.getUniversityAppNo()))
								admForm.setTransUnvrAppNo(lateralTO
										.getUniversityAppNo());
							if (lateralTO.getArrearDetail() != null
									&& !StringUtils.isEmpty(lateralTO
											.getArrearDetail()))
								admForm.setTransArrearDetail(lateralTO
										.getArrearDetail());
						}

					}
					Collections.sort(admForm.getApplicantDetails()
							.getTransferDetails());
					admForm.setTransferDetails(admForm.getApplicantDetails()
							.getTransferDetails());
				}

			}
		} catch (Exception e) {
			log.error("error in viewTransferEntryPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit viewTransferEntryPage...");

		return mapping
				.findForward(CMSConstants.STUDENTEDIT_VIEW_TRANSFER_MARK_PAGE);
	}

	/**
	 * saves the edited values admission form submit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateApplicationEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered updateApplicationEdit..");
		StudentEditForm admForm = (StudentEditForm) form;
		ActionMessages errors = admForm.validate(mapping, request);
		try {
			validateEditProgramCourse(errors, admForm);
			validateEditPhone1(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			validateEditCommAddress1(admForm, errors);
			validateEditCoursePreferences(admForm, errors);
			validateSubjectGroups(admForm, errors);
			if (admForm.isDisplayTCDetails())
				validateTcDetailsEdit(admForm, errors);
			if (admForm.isDisplayEntranceDetails())
				validateEntanceDetailsEdit(admForm, errors);
			
			if(admForm.getBankAccNo()!=null){
				if(nameValidate(admForm.getBankAccNo())){
					errors.add(CMSConstants.ERROR, new ActionMessage("knowledgepro.admin.special.bankaccNo"));
				}
			}
			if (admForm.getApplicantDetails().getChallanDate() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getChallanDate())) {
				if (CommonUtil.isValidDate(admForm.getApplicantDetails()
						.getChallanDate())) {
					boolean isValid = validatefutureDate(admForm
							.getApplicantDetails().getChallanDate());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
						}
					}
				} else {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// validate Admission Date
			if (admForm.getApplicantDetails().getAdmissionDate() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getAdmissionDate())) {
				if (CommonUtil.isValidDate(admForm.getApplicantDetails()
						.getAdmissionDate())) {
					boolean isValid = validatefutureDate(admForm
							.getApplicantDetails().getAdmissionDate());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
						}
					}
				} else {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
					}
				}
			}
			if (admForm.getApplicantDetails().getPersonalData() != null
					&& admForm.getApplicantDetails().getPersonalData().getDob() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getDob())) {
				if (CommonUtil.isValidDate(admForm.getApplicantDetails()
						.getPersonalData().getDob())) {
					boolean isValid = validatefutureDate(admForm
							.getApplicantDetails().getPersonalData().getDob());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null
								&& !errors.get(
										CMSConstants.ADMISSIONFORM_DOB_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_DOB_LARGE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_DOB_LARGE));
						}
					}
				} else {
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID) != null
							&& !errors.get(
									CMSConstants.ADMISSIONFORM_DOB_INVAID)
									.hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID,
								new ActionError(
										CMSConstants.ADMISSIONFORM_DOB_INVAID));
					}
				}
			}
			if (admForm.getApplicantDetails().getPersonalData()
					.getPassportValidity() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getPassportValidity())) {

				if (CommonUtil.isValidDate(admForm.getApplicantDetails()
						.getPersonalData().getPassportValidity())) {
					boolean isValid = validatePastDate(admForm
							.getApplicantDetails().getPersonalData()
							.getPassportValidity());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_PASSPORT_INVALID)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,
											new ActionError(
													CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
				} else {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
					}
				}
			}
			if (admForm.getApplicantDetails().getPersonalData()
					.getResidentPermitDate() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getResidentPermitDate())
					&& !CommonUtil.isValidDate(admForm.getApplicantDetails()
							.getPersonalData().getResidentPermitDate())) {

				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_PERMITDT_INVAID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}

			List<ApplicantWorkExperienceTO> expList = admForm
					.getApplicantDetails().getWorkExpList();
			if (expList != null) {
				Iterator<ApplicantWorkExperienceTO> toItr = expList.iterator();
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr
							.next();
					validateWorkExperience(expTO, errors);
				}
			}
			validateEditEducationDetails(errors, admForm);
			validateEditDocumentSize(admForm, errors);
			if (admForm.getApplicantDetails().getPersonalData()
					.getTrainingDuration() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getTrainingDuration())
					&& !StringUtils.isNumeric(admForm.getApplicantDetails()
							.getPersonalData().getTrainingDuration())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_DURATION_INVALID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_DURATION_INVALID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}

			// validate height and weight
			if (admForm.getApplicantDetails().getPersonalData().getHeight() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getHeight())
					&& !StringUtils.isNumeric(admForm.getApplicantDetails()
							.getPersonalData().getHeight())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_HEIGHT_INVALID)
								.hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,
							new ActionError(
									CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}

			if (admForm.getApplicantDetails().getPersonalData().getWeight() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getWeight())
					&& !CommonUtil.isValidDecimal(admForm.getApplicantDetails()
							.getPersonalData().getWeight())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_WEIGHT_INVALID)
								.hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,
							new ActionError(
									CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			// check regNo Duplicate or not
			if (admForm.getRegNo() != null
					&& !StringUtils.isEmpty(admForm.getRegNo().trim()) && !admForm.getOriginalRegNo().equals(admForm.getRegNo())) {
				boolean duplicateRegNo = StudentEditHandler.getInstance().checkRegNoUnique(admForm
						.getRegNo(), admForm.getApplicantDetails().getAppliedYear());
				if (!duplicateRegNo) {
					errors.add(CMSConstants.ADDSTUDENT_REGNO_NOTUNIQUE,
							new ActionError(
									CMSConstants.ADDSTUDENT_REGNO_NOTUNIQUE));
				}

			}
			
			if(admForm.getDetentiondetailsDate()!= null && !admForm.getDetentiondetailsDate().trim().isEmpty() &&
					admForm.getDiscontinuedDetailsDate()!= null && !admForm.getDiscontinuedDetailsDate().trim().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.admission.detention.discontinue.details.found"));
				saveErrors(request, errors);
			}
			
			if (errors == null)
				errors = new ActionMessages();

			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(admForm.getApplicantDetails());
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);

			}
			AdmApplnTO applicantDetail = admForm.getApplicantDetails();
			StudentEditHandler handler = StudentEditHandler.getInstance();
			AdmissionFormHandler admHandler = AdmissionFormHandler
					.getInstance();
			// check for seat allocation exceeded for admitted through or not
			boolean exceeded = false;

			if (!admForm.isQuotaCheck()) {
				if (applicantDetail.getAdmittedThroughId() != null
						&& !StringUtils.isEmpty(applicantDetail
								.getAdmittedThroughId())
						&& StringUtils.isNumeric(applicantDetail
								.getAdmittedThroughId())
						&& applicantDetail.getSelectedCourse() != null
						&& applicantDetail.getSelectedCourse().getId() != 0) {
					exceeded = admHandler.checkSeatAllocationExceeded(Integer
							.parseInt(applicantDetail.getAdmittedThroughId()),
							applicantDetail.getSelectedCourse().getId());
				}
			}

			if (exceeded) {
				admForm.setQuotaCheck(true);
				resetHardCopySubmit(applicantDetail);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EDIT_WARN);
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
			} else {
				if (!admForm.isEligibleCheck()) {
					boolean eligible = admHandler
							.checkEligibility(applicantDetail);

					if (!eligible) {
						resetHardCopySubmit(admForm.getApplicantDetails());
						admForm.setQuotaCheck(true);
						admForm.setEligibleCheck(true);
						ActionMessages messages = new ActionMessages();
						ActionMessage message = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ELIGIBILITY_WARN);
						messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
						saveErrors(request, messages);
						return mapping
								.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
					}

				}
			}
			// check regNo Duplicate or not
			if (admForm.getRegNo() != null
					&& !StringUtils.isEmpty(admForm.getRegNo().trim())) {
				boolean duplicateRegNo =true;
				if(admForm.getOriginalRegNo()!=null){
					if(!admForm.getOriginalRegNo().equals(admForm.getRegNo())){
						duplicateRegNo = StudentEditHandler.getInstance().checkRegNoUnique(admForm
								.getRegNo(), applicantDetail.getAppliedYear());
					}
				}else{
					duplicateRegNo = StudentEditHandler.getInstance().checkRegNoUnique(admForm
							.getRegNo(), applicantDetail.getAppliedYear());
				}
				if (!duplicateRegNo) {
					resetHardCopySubmit(admForm.getApplicantDetails());
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADDSTUDENT_REGNO_NOTUNIQUE);
					message.add(CMSConstants.ADDSTUDENT_REGNO_NOTUNIQUE, error);
					saveErrors(request, message);
					// stForm.setApplicationError(true);
					return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
				}
			}
			
			boolean result = handler.updateCompleteApplication(applicantDetail,
					admForm,true);
			//--------------------------------added---------------
			admForm.setApplicationNo(Integer.toString(applicantDetail.getApplnNo()));
			admForm.setAcademicYear(Integer.toString(applicantDetail.getAppliedYear()));
			if (result) {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EDIT_SUCCESS);
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log .error( "Error in  getApplicantDetails application form edit page...", e);
			if (e instanceof DuplicateException) {
				errors.add("error", new ActionError(
				"knowledgepro.admission.detention.details.not.found"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
			}
			else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		cleanupEditSessionData(request);
		log.info("exit updateApplicationEdit..");

		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_CONFIRM_PAGE);

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
			if (session.getAttribute(PRCStudentEditAction.PHOTOBYTES) != null)
				session.removeAttribute(PRCStudentEditAction.PHOTOBYTES);
		}
	}

	/**
	 * validate programtype,course and program
	 * 
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEditProgramCourse(ActionMessages errors,
			StudentEditForm admForm) {
		log.info("enter validate program course...");
		if (admForm.getApplicantDetails().getCourse().getProgramTypeId() == 0) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED,
						error);
			} else {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED,
						error);
			}
		}
		if (admForm.getApplicantDetails().getCourse().getProgramId() == 0) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			} else {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}
		}
		if (admForm.getApplicantDetails().getCourse().getId() == 0) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			} else {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			}
		}
		log.info("exit validate program course...");
		return errors;
	}

	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhone(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionMessages();

//		if ((admForm.getApplicantDetails().getPersonalData().getPhNo1() == null || StringUtils
//				.isEmpty(admForm.getApplicantDetails().getPersonalData()
//						.getPhNo1()))
//				&& (admForm.getApplicantDetails().getPersonalData().getPhNo2() == null || StringUtils
//						.isEmpty(admForm.getApplicantDetails()
//								.getPersonalData().getPhNo2()))
//				&& (admForm.getApplicantDetails().getPersonalData().getPhNo3() == null || StringUtils
//						.isEmpty(admForm.getApplicantDetails()
//								.getPersonalData().getPhNo3()))) {
//			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED) != null
//					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)
//							.hasNext()) {
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
//				errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
//			}
//		}

		if (admForm.getApplicantDetails().getPersonalData().getPhNo1() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPhNo1())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getPhNo1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getPhNo2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPhNo2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getPhNo2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getPhNo3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPhNo3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getPhNo3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}

		// if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null
		// &&
		// !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1())
		// &&
		// !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1())
		// )
		// {
		// if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null &&
		// !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
		// ActionMessage error = new ActionMessage(
		// CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
		// errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
		// }
		// }
		if (admForm.getApplicantDetails().getPersonalData().getMobileNo2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo2())
				&& admForm.getApplicantDetails().getPersonalData()
						.getMobileNo2().trim().length() != 10) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getMobileNo2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getMobileNo3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		log.info("exit validateEditPhone..");
	}

	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditOtherOptions(StudentEditForm admForm,
			ActionMessages errors) throws Exception {
		log.info("enter validateEditOtherOptions..");
		if (errors == null)
			errors = new ActionMessages();
		// if((admForm.getApplicantDetails().getPersonalData().getBirthState()==null
		// ||
		// StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthState())||
		// admForm.getApplicantDetails().getPersonalData().getBirthState().equalsIgnoreCase("other"))&&
		// (admForm.getApplicantDetails().getPersonalData().getStateOthers()==null
		// ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStateOthers())
		// ))
		// {
		// if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null
		// &&
		// !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext())
		// {
		// ActionMessage error = new ActionMessage(
		// CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
		// errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
		// }
		// }
		if ((admForm.getApplicantDetails().getPersonalData().getReligionId() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getReligionId()) || (admForm
				.getApplicantDetails().getPersonalData().getReligionId()
				.equalsIgnoreCase("other")))
				&& (admForm.getApplicantDetails().getPersonalData()
						.getReligionOthers() == null || StringUtils
						.isEmpty(admForm.getApplicantDetails()
								.getPersonalData().getReligionOthers()))) {
			if (errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED) != null
					&& !errors
							.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getReligionId() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getReligionId())
				&& StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getReligionId())) {
			//ISubReligionTransaction relTxn = new SubReligionTransactionImpl();
			// if master entry exists
			//if (relTxn.checkSubReligionExists(Integer.parseInt(admForm
			//		.getApplicantDetails().getPersonalData().getReligionId()))) {
			if(CMSConstants.CASTE_ENABLED){
				if ((admForm.getApplicantDetails().getPersonalData()
						.getSubReligionId() == null
						|| StringUtils.isEmpty(admForm.getApplicantDetails()
								.getPersonalData().getSubReligionId()) || (admForm
						.getApplicantDetails().getPersonalData()
						.getSubReligionId().equalsIgnoreCase("other")))
						&& (admForm.getApplicantDetails().getPersonalData()
								.getReligionSectionOthers() == null || StringUtils
								.isEmpty(admForm.getApplicantDetails()
										.getPersonalData()
										.getReligionSectionOthers()))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED,
										error);
					}
				}
			}
			//}
		}
		/*if ((admForm.getApplicantDetails().getPersonalData().getCasteId() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getCasteId()) || (admForm
				.getApplicantDetails().getPersonalData().getCasteId()
				.equalsIgnoreCase("other")))
				&& (admForm.getApplicantDetails().getPersonalData()
						.getCasteOthers() == null || StringUtils
						.isEmpty(admForm.getApplicantDetails()
								.getPersonalData().getCasteOthers()))) {
			if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
			}
		}*/

	/*	if ((admForm.getApplicantDetails().getPersonalData()
				.getPermanentStateId() == null || StringUtils.isEmpty(admForm
				.getApplicantDetails().getPersonalData().getPermanentStateId()))
				&& ((admForm.getApplicantDetails().getPersonalData()
						.getPermanentAddressStateOthers()) == null || StringUtils
						.isEmpty(admForm.getApplicantDetails()
								.getPersonalData()
								.getPermanentAddressStateOthers()))) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors
						.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED,
								error);
			}
		}*/
		log.info("exit validateEditOtherOptions..");
	}

	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditParentPhone(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditParentPhone..");
		if (errors == null)
			errors = new ActionMessages();

		if (admForm.getApplicantDetails().getPersonalData().getParentPh1() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getParentPh1())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getParentPh1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getParentPh2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getParentPh2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getParentPh2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getParentPh3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getParentPh3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getParentPh3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID,
						error);
			}
		}

		if (admForm.getApplicantDetails().getPersonalData().getParentMob1() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getParentMob1())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getParentMob1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getParentMob2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getParentMob2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getParentMob2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getParentMob3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getParentMob3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getParentMob3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID,
						error);
			}
		}
		log.info("exit validateEditParentPhone..");
	}

	/**
	 * application form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditGuardianPhone(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditGuardianPhone..");
		if (errors == null)
			errors = new ActionMessages();

		if (admForm.getApplicantDetails().getPersonalData().getGuardianPh1() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getGuardianPh1())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getGuardianPh1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getGuardianPh2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getGuardianPh2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getGuardianPh2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getGuardianPh3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getGuardianPh3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getGuardianPh3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID,
						error);
			}
		}

		if (admForm.getApplicantDetails().getPersonalData().getGuardianMob1() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getGuardianMob1())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getGuardianMob1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getGuardianMob2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getGuardianMob2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getGuardianMob2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID,
						error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getGuardianMob3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getGuardianMob3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getGuardianMob3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID,
						error);
			}
		}
		log.info("exit validateEditGuardianPhone..");
	}

	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPassportIfNRI(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditPassportIfNRI..");
		if (errors == null)
			errors = new ActionMessages();
		if (admForm.getApplicantDetails().getPersonalData()
				.getResidentCategory() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getResidentCategory())) {
			if (admForm.getApplicantDetails().getPersonalData()
					.getResidentCategory() != null
					&& StringUtils.isNumeric(admForm.getApplicantDetails()
							.getPersonalData().getResidentCategory())
					&& !CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer
							.valueOf((Integer.parseInt(admForm
									.getApplicantDetails().getPersonalData()
									.getResidentCategory()))))) {
				if (admForm.getApplicantDetails().getPersonalData()
						.getPassportNo() == null
						|| StringUtils.isEmpty(admForm.getApplicantDetails()
								.getPersonalData().getPassportNo())) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED,
							error);
				}
				if (admForm.getApplicantDetails().getPersonalData()
						.getPassportCountry() == 0) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED);
					errors
							.add(
									CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED,
									error);
				}
				if (admForm.getApplicantDetails().getPersonalData()
						.getPassportValidity() == null
						|| StringUtils.isEmpty(admForm.getApplicantDetails()
								.getPersonalData().getPassportValidity())) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED);
					errors
							.add(
									CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED,
									error);
				} else if (admForm.getApplicantDetails().getPersonalData()
						.getPassportValidity() != null
						&& !StringUtils.isEmpty(admForm.getApplicantDetails()
								.getPersonalData().getPassportValidity())) {
					if (CommonUtil.isValidDate(admForm.getApplicantDetails()
							.getPersonalData().getPassportValidity())) {
						boolean isValid = validatePastDate(admForm
								.getApplicantDetails().getPersonalData()
								.getPassportValidity());
						if (!isValid) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_PASSPORT_INVALID)
											.hasNext()) {
								errors
										.add(
												CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,
												new ActionError(
														CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
							}
						}
					} else {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,
											new ActionError(
													CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
				}

			}

		}
		log.info("exit validateEditPassportIfNRI..");
	}

	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCommAddress(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditCommAddress..");
		if (errors == null)
			errors = new ActionMessages();

		if (admForm.getApplicantDetails().getPersonalData()
				.getCurrentAddressLine1() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getCurrentAddressLine1())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED);
			errors
					.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED,
							error);
		}
		if (admForm.getApplicantDetails().getPersonalData()
				.getCurrentCityName() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getCurrentCityName())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED, error);
		}

		if (admForm.getApplicantDetails().getPersonalData()
				.getCurrentCountryId() == 0) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED, error);
		}
		if (admForm.getApplicantDetails().getPersonalData()
				.getCurrentAddressZipCode() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getCurrentAddressZipCode())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED, error);
		} else if (!StringUtils.isNumeric(admForm.getApplicantDetails()
				.getPersonalData().getCurrentAddressZipCode())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID);
			errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID,
					error);
		}
		if ((admForm.getApplicantDetails().getPersonalData()
				.getCurrentStateId() == null || StringUtils.isEmpty(admForm
				.getApplicantDetails().getPersonalData().getCurrentStateId()))
				&& (admForm.getApplicantDetails().getPersonalData()
						.getCurrentAddressStateOthers() == null || StringUtils
						.isEmpty(admForm.getApplicantDetails()
								.getPersonalData()
								.getCurrentAddressStateOthers()))) {
			if (errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED,
						error);
			}
		}

		log.info("exit validateEditCommAddress..");
	}

	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCoursePreferences(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditCoursePreferences..");
		List<CandidatePreferenceTO> prefcourses = admForm.getPreferenceList();
		Iterator<CandidatePreferenceTO> itr = prefcourses.iterator();
		CandidatePreferenceTO candidatePreferenceTO;
		while (itr.hasNext()) {
			candidatePreferenceTO = itr.next();
			if (candidatePreferenceTO.getPrefNo() == 1
					&& candidatePreferenceTO.getCoursId().length() == 0) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
				errors
						.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED,
								error);
			}
		}
		log.info("exit validateEditCoursePreferences..");
	}

	/**
	 * Tc Details edit validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateTcDetailsEdit(StudentEditForm admForm,
			ActionMessages errors) {
		if (admForm.getApplicantDetails() != null
				&& admForm.getApplicantDetails().getTcDate() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getTcDate().trim())) {
			if (CommonUtil.isValidDate(admForm.getApplicantDetails()
					.getTcDate().trim())) {
				if (!validatefutureDate(admForm.getApplicantDetails()
						.getTcDate())) {
					if (errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE) != null
							&& !errors.get(
									CMSConstants.ADMISSIONFORM_TCDATE_FUTURE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_TCDATE_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE,
								error);
					}
				}

			} else {
				if (errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_TCDATE_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_TCDATE_INVALID);
					errors
							.add(CMSConstants.ADMISSIONFORM_TCDATE_INVALID,
									error);
				}
			}
		}

		if (admForm.getApplicantDetails() != null
				&& admForm.getApplicantDetails().getMarkscardDate() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getMarkscardDate().trim())) {
			if (CommonUtil.isValidDate(admForm.getApplicantDetails()
					.getMarkscardDate().trim())) {
				if (!validatefutureDate(admForm.getApplicantDetails()
						.getMarkscardDate().trim())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE);
						errors.add(
								CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE,
								error);
					}
				}

			} else {
				if (errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID,
							error);
				}
			}
		}
	}

	/**
	 * future date validation
	 * 
	 * @param dateString
	 * @return
	 */
	private boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateString, PRCStudentEditAction.FROM_DATEFORMAT,
				PRCStudentEditAction.TO_DATEFORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();
		log.info("exit validatefutureDate..");
		return !(date.compareTo(origdate) == 1);

	}

	/**
	 * entrance details validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEntanceDetailsEdit(StudentEditForm admForm,
			ActionMessages errors) {

		if (admForm.getApplicantDetails() != null
				&& admForm.getApplicantDetails().getEntranceDetail() != null
				&& admForm.getApplicantDetails().getEntranceDetail()
						.getTotalMarks() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getEntranceDetail().getTotalMarks().trim())
				&& !CommonUtil.isValidDecimal(admForm.getApplicantDetails()
						.getEntranceDetail().getTotalMarks().trim())) {
			if (errors
					.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER) != null
					&& !errors
							.get(
									CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER);
				errors
						.add(
								CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER,
								error);
			}
		}

		if (admForm.getApplicantDetails() != null
				&& admForm.getApplicantDetails().getEntranceDetail() != null
				&& admForm.getApplicantDetails().getEntranceDetail()
						.getMarksObtained() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getEntranceDetail().getMarksObtained().trim())
				&& !CommonUtil.isValidDecimal(admForm.getApplicantDetails()
						.getEntranceDetail().getMarksObtained().trim())) {
			if (errors
					.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER) != null
					&& !errors
							.get(
									CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER);
				errors
						.add(
								CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER,
								error);
			}
		}

		if (admForm.getApplicantDetails() != null
				&& admForm.getApplicantDetails().getEntranceDetail() != null
				&& admForm.getApplicantDetails().getEntranceDetail()
						.getMarksObtained() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getEntranceDetail().getMarksObtained().trim())
				&& CommonUtil.isValidDecimal(admForm.getApplicantDetails()
						.getEntranceDetail().getMarksObtained().trim())
				&& admForm.getApplicantDetails().getEntranceDetail()
						.getTotalMarks() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getEntranceDetail().getTotalMarks().trim())
				&& CommonUtil.isValidDecimal(admForm.getApplicantDetails()
						.getEntranceDetail().getTotalMarks().trim())
				&& Double.parseDouble(admForm.getApplicantDetails()
						.getEntranceDetail().getMarksObtained()) > Double
						.parseDouble(admForm.getApplicantDetails()
								.getEntranceDetail().getTotalMarks())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE,
						error);
			}
		}

		// check date of birth cross and present date cross
		if ((admForm.getApplicantDetails() != null
				&& admForm.getApplicantDetails().getEntranceDetail() != null
				&& admForm.getApplicantDetails().getEntranceDetail()
						.getYearPassing() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getEntranceDetail().getYearPassing()) && StringUtils
				.isNumeric(admForm.getApplicantDetails().getEntranceDetail()
						.getYearPassing()))
				&& admForm.getApplicantDetails().getEntranceDetail()
						.getMonthPassing() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getEntranceDetail().getMonthPassing())
				&& StringUtils.isNumeric(admForm.getApplicantDetails()
						.getEntranceDetail().getMonthPassing())) {
			if (admForm.getApplicantDetails().getPersonalData().getDob() != null
					&& !StringUtils.isEmpty(admForm.getApplicantDetails()
							.getPersonalData().getDob())
					&& CommonUtil.isValidDate(admForm.getApplicantDetails()
							.getPersonalData().getDob())) {
				boolean futurethanBirth = isPassYearGreaterThanBirth(Integer
						.parseInt(admForm.getApplicantDetails()
								.getEntranceDetail().getYearPassing()), Integer
						.parseInt(admForm.getApplicantDetails()
								.getEntranceDetail().getMonthPassing()),
						admForm.getApplicantDetails().getPersonalData()
								.getDob());
				if (!futurethanBirth) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE,
										error);
					}
				}
			}
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			boolean futurethantoday = isPassYearGreaterThanToday(Integer
					.parseInt(admForm.getApplicantDetails().getEntranceDetail()
							.getYearPassing()), Integer.parseInt(admForm
					.getApplicantDetails().getEntranceDetail()
					.getMonthPassing()), today);
			if (futurethantoday) {
				if (errors
						.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE);
					errors
							.add(
									CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE,
									error);
				}
			}
		}
	}

	/**
	 * compares curent date with pass year,month
	 * 
	 * @param yearPassing
	 * @param monthPassing
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isPassYearGreaterThanToday(int yearPassing,
			int monthPassing, Date today) {
		boolean result = false;
		if (yearPassing != 0 && monthPassing != 0 && today != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Calendar cal2 = Calendar.getInstance();
			// cal2.setTime(birthdate);
			cal2.set(Calendar.DATE, 1);
			cal2.set(Calendar.MONTH, monthPassing - 1);
			cal2.set(Calendar.YEAR, yearPassing);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			// if pass year larger than birth year
			// if(yearPassing== cal.get(Calendar.YEAR)|| yearPassing>
			// cal.get(Calendar.YEAR))
			if (cal2.getTime().after(cal.getTime()))
				result = true;
		}
		return result;
	}

	/**
	 * past date validation
	 * 
	 * @param dateString
	 * @return
	 */
	private boolean validatePastDate(String dateString) {
		log.info("enter validatePastDate..");
		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateString, PRCStudentEditAction.FROM_DATEFORMAT,
				PRCStudentEditAction.TO_DATEFORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();

		log.info("exit validatePastDate..");
		return !(date.compareTo(origdate) < 0);

	}

	/**
	 * application form validation
	 * 
	 * @param TO
	 * @param errors
	 */
	private void validateWorkExperience(ApplicantWorkExperienceTO workTO,
			ActionMessages errors) {
		log.info("enter validateWorkExperience..");
		if (errors == null)
			errors = new ActionMessages();
		if (workTO != null) {
			boolean validTODate = false;
			boolean validFromDate = false;
			if (workTO.getFromDate() != null
					&& !StringUtils.isEmpty(workTO.getFromDate())
					&& !CommonUtil.isValidDate(workTO.getFromDate())) {
				validFromDate = false;
			} else {
				validFromDate = true;
			}
			if (workTO.getToDate() != null
					&& !StringUtils.isEmpty(workTO.getToDate())
					&& !CommonUtil.isValidDate(workTO.getToDate())) {
				validTODate = false;
			} else {
				validTODate = true;
			}
			if (validTODate && validFromDate) {
				if (workTO.getFromDate() != null
						&& !workTO.getFromDate().isEmpty()
						&& workTO.getToDate() != null
						&& !workTO.getToDate().isEmpty()) {
					String formdate = CommonUtil.ConvertStringToDateFormat(
							workTO.getFromDate(),
							PRCStudentEditAction.FROM_DATEFORMAT,
							PRCStudentEditAction.TO_DATEFORMAT);
					String todate = CommonUtil.ConvertStringToDateFormat(workTO
							.getToDate(), PRCStudentEditAction.FROM_DATEFORMAT,
							PRCStudentEditAction.TO_DATEFORMAT);
					Date startdate = new Date(formdate);
					Date enddate = new Date(todate);

					if (startdate.compareTo(enddate) == 1) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE));
						}
					}
					if (enddate.compareTo(startdate) == 0) {
						if (errors.get(CMSConstants.ADMISSIONFORM_EXP_DATESAME) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_EXP_DATESAME)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_EXP_DATESAME,
											new ActionError(
													CMSConstants.ADMISSIONFORM_EXP_DATESAME));
						}

					}

				}
			} else if (!validTODate) {
				if (errors
						.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID));
				}
			} else if (!validFromDate) {
				if (errors
						.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID));
				}
			}
			if (workTO.getSalary() != null
					&& !StringUtils.isEmpty(workTO.getSalary())
					&& !CommonUtil.isValidDecimal(workTO.getSalary())) {
				if (errors
						.get(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID));
				}
			}
		}
		log.info("exit validateWorkExperience..");
	}

	/**
	 * validate programtype,course and program
	 * 
	 * @param errors
	 * @param admForm
	 * @return
	 *//*
	private ActionMessages validateEditEducationDetails(ActionMessages errors,
			StudentEditForm admForm) {
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications = admForm.getApplicantDetails()
				.getEdnQualificationList();
		if (qualifications != null) {
			Iterator<EdnQualificationTO> qualificationTO = qualifications
					.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if ((qualfTO.getUniversityId() == null
						|| (qualfTO.getUniversityId().length() == 0) || qualfTO
						.getUniversityId().equalsIgnoreCase("Other"))
						&& (qualfTO.getUniversityOthers() == null || (qualfTO
								.getUniversityOthers().trim().length() == 0))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,
								error);
					}
				}
				if ((qualfTO.getInstitutionId() == null
						|| (qualfTO.getInstitutionId().length() == 0) || (qualfTO
						.getInstitutionId().equalsIgnoreCase("Other")))
						&& (qualfTO.getOtherInstitute() == null || (qualfTO
								.getOtherInstitute().trim().length() == 0))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,
								error);
					}
				}
				if (qualfTO.getYearPassing() == 0) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED,
								error);
					}
				} else {
					boolean valid = CommonUtil.isFutureNotCurrentYear(qualfTO
							.getYearPassing());
					if (valid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE);
							errors.add(
									CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE,
									error);
						}
					}

					// if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
					// boolean
					// futurethanBirth=isPassYearGreaterThanBirthYear(qualfTO.getYearPassing(),admForm.getApplicantDetails().getPersonalData().getDob());
					// if(!futurethanBirth){
					// if
					// (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)!=null
					// &&
					// !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE).hasNext())
					// {
					// ActionMessage error = new
					// ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
					// errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,error);
					// }
					// }
					// }
					// // else{
					// // if
					// (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null
					// &&
					// !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext())
					// {
					// // errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new
					// ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
					// // }
					// // }
				}
				if (qualfTO.getMonthPassing() == 0) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED,
								error);
					}
				}
				if (qualfTO.isLastExam()
						&& (qualfTO.getPreviousRegNo() == null || StringUtils
								.isEmpty(qualfTO.getPreviousRegNo().trim()))) {
					if (errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED) != null
							&& !errors.get(
									CMSConstants.ADMISSIONFORM_REGNO_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_REGNO_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED,
								error);
					}
				}
				if (  qualfTO.isExamRequired() qualfTO.isExamConfigured()
						&& (qualfTO.getSelectedExamId() == null || StringUtils
								.isEmpty(qualfTO.getSelectedExamId()))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,
								error);
					}
				}
				if (qualfTO.getNoOfAttempts() == 0) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,
								error);
					}
				}
				if (qualfTO.isConsolidated()) {
					if (qualfTO.getTotalMarks() == null
							|| StringUtils.isEmpty(qualfTO.getTotalMarks())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED,
											error);
						}
					} else if (!CommonUtil.isValidDecimal(qualfTO
							.getTotalMarks())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,
											error);
						}
					}
					if (qualfTO.getMarksObtained() == null
							|| StringUtils.isEmpty(qualfTO.getMarksObtained())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED,
											error);
						}
					} else if (!CommonUtil.isValidDecimal(qualfTO
							.getMarksObtained())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,
											error);
						}
					}
					if (qualfTO.getMarksObtained() != null
							&& !StringUtils.isEmpty(qualfTO.getMarksObtained())
							&& CommonUtil.isValidDecimal(qualfTO
									.getMarksObtained())
							&& qualfTO.getTotalMarks() != null
							&& !StringUtils.isEmpty(qualfTO.getTotalMarks())
							&& CommonUtil.isValidDecimal(qualfTO
									.getTotalMarks())
							&& Double.parseDouble(qualfTO.getTotalMarks()) < Double
									.parseDouble(qualfTO.getMarksObtained())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
											error);
						}
					}

				} else {
					if (qualfTO.isSemesterWise()) {
						if (qualfTO.getSemesterList() == null) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,
												error);
							}
						}
					} else if (qualfTO.getDetailmark() == null) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,
											error);
						}
					}
				}

				if (qualfTO.getYearPassing() != 0
						&& qualfTO.getMonthPassing() != 0) {
					if (CommonUtil.isValidDate(admForm.getApplicantDetails()
							.getPersonalData().getDob())) {
						boolean futurethanBirth = isPassYearGreaterThanBirth(
								qualfTO.getYearPassing(), qualfTO
										.getMonthPassing(), admForm
										.getApplicantDetails()
										.getPersonalData().getDob());
						if (!futurethanBirth) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,
												error);
							}
						}
					}
				}

			}
			log.info("exit validate education...");
		}

		return errors;
	}
*/
	/**
	 * Validate document size
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditDocumentSize(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist = admForm.getApplicantDetails()
				.getEditDocuments();
		InputStream propStream = AdmissionFormAction.class
				.getResourceAsStream("/resources/application.properties");
		int maXSize = 0;
		int maxPhotoSize = 0;
		Properties prop = new Properties();
		try {
			prop.load(propStream);
			maXSize = Integer.parseInt(prop
					.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			maxPhotoSize = Integer.parseInt(prop
					.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		} catch (IOException e) {
			log.error("Error in Reading key from properties file....", e);
		}
		if (uploadlist != null) {
			Iterator<ApplnDocTO> uploaditr = uploadlist.iterator();
			while (uploaditr.hasNext()) {
				ApplnDocTO docTo = (ApplnDocTO) uploaditr.next();
				FormFile file = null;
				if (docTo != null)
					file = docTo.getEditDocument();
				if (file != null) {
					String filename = file.getFileName();
					if (!StringUtils.isEmpty(filename)
							&& filename.length() > 30) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME,
											error);
						}
					}
				}
				if (docTo.isPhoto() && file != null
						&& maxPhotoSize < file.getFileSize()) {
					if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE) != null
							&& !errors.get(
									CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,
								error);
					}
				} else if (file != null && maXSize < file.getFileSize()) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
						errors.add(
								CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,
								error);
					}
				}
				if(docTo.isNeedToProduceSemWiseMC()){
					if(docTo.getSemisterNo()==null || docTo.getSemisterNo().isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionMessage("errors.required","Sem No in Document(s)"));
					}
				}
			}

		}

	}

	/**
	 * reset check option for validation fail
	 * 
	 * @param applicantDetail
	 */
	private void resetHardCopySubmit(AdmApplnTO applicantDetail) {
		log.info("enter resetHardCopySubmit...");
		if (applicantDetail != null
				&& applicantDetail.getEditDocuments() != null) {
			Iterator<ApplnDocTO> docItr = applicantDetail.getEditDocuments()
					.iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				if (docTO.isHardSubmitted()) {
					docTO.setHardSubmitted(false);
					docTO.setTemphardSubmitted(true);
				} else {
					docTO.setHardSubmitted(false);
					docTO.setTemphardSubmitted(false);
				}
				if (docTO.isNotApplicable()) {
					docTO.setNotApplicable(false);
					docTO.setTempNotApplicable(true);
				} else {
					docTO.setNotApplicable(false);
					docTO.setTempNotApplicable(false);
				}
			}
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
	public ActionForward initDetailMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init detail mark page...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			Map<Integer, String> subjectMap = AdmissionFormHandler
					.getInstance().getDetailedSubjectsByCourse(
							admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			String indexString = request
					.getParameter(PRCStudentEditAction.EDITCOUNTID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null) {
					session.setAttribute(PRCStudentEditAction.EDITCOUNTID,
							indexString);
					int index = Integer.parseInt(indexString);
					List<EdnQualificationTO> quals = admForm
							.getApplicantDetails().getEdnQualificationList();
					if (quals != null) {
						Iterator<EdnQualificationTO> qualItr = quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
									.next();
							if (qualTO.getId() == index) {
								if (qualTO.getDetailmark() != null)
									admForm.setDetailMark(qualTO
											.getDetailmark());
								else {
									CandidateMarkTO markTo = new CandidateMarkTO();
									AdmissionFormHelper.getInstance()
											.setDetailedSubjectsFormMap(
													subjectMap, markTo);
									admForm.setDetailMark(markTo);
								}
							}
						}
					}
				} else
					session.removeAttribute(PRCStudentEditAction.EDITCOUNTID);
			}

		} catch (Exception e) {
			log.error("error in init detail mark page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit init detail mark page...");
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_DETAIL_MARK_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMarkEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkEdit page...");
		StudentEditForm admForm = (StudentEditForm) form;
		HttpSession session = request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session
						.getAttribute(PRCStudentEditAction.EDITCOUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = validateMarks(detailmark);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_DETAIL_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm
					.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr = qualifications
						.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
							.next();
					if (qualTO.getId() == detailMarkIndex) {
						qualTO.setDetailmark(detailmark);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submit detail mark page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("enter submitDetailMarkEdit page...");

		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}

	/**
	 * @param detailmark
	 * @return
	 */
	private ActionMessages validateMarks(CandidateMarkTO detailmark) {
		log.info("enter validateMarks...");
		ActionMessages errors = new ActionMessages();
		if (detailmark != null) {
			/* mandatory subject mark check start */
			if (detailmark.isSubject1Mandatory()
					&& (detailmark.getSubject1TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject1TotalMarks())
							|| detailmark.getSubject1ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject1ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}

			if (detailmark.isSubject2Mandatory()
					&& (detailmark.getSubject2TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject2TotalMarks())
							|| detailmark.getSubject2ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject2ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}

			if (detailmark.isSubject3Mandatory()
					&& (detailmark.getSubject3TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject3TotalMarks())
							|| detailmark.getSubject3ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject3ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject4Mandatory()
					&& (detailmark.getSubject4TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject4TotalMarks())
							|| detailmark.getSubject4ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject4ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject5Mandatory()
					&& (detailmark.getSubject5TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject5TotalMarks())
							|| detailmark.getSubject5ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject5ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject6Mandatory()
					&& (detailmark.getSubject6TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject6TotalMarks())
							|| detailmark.getSubject6ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject6ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject7Mandatory()
					&& (detailmark.getSubject7TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject7TotalMarks())
							|| detailmark.getSubject7ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject7ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject8Mandatory()
					&& (detailmark.getSubject8TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject8TotalMarks())
							|| detailmark.getSubject8ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject8ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject9Mandatory()
					&& (detailmark.getSubject9TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject9TotalMarks())
							|| detailmark.getSubject9ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject9ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject10Mandatory()
					&& (detailmark.getSubject10TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject10TotalMarks())
							|| detailmark.getSubject10ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject10ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject11Mandatory()
					&& (detailmark.getSubject11TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject11TotalMarks())
							|| detailmark.getSubject11ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject11ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject12Mandatory()
					&& (detailmark.getSubject12TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject12TotalMarks())
							|| detailmark.getSubject12ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject12ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject13Mandatory()
					&& (detailmark.getSubject13TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject13TotalMarks())
							|| detailmark.getSubject13ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject13ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject14Mandatory()
					&& (detailmark.getSubject14TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject14TotalMarks())
							|| detailmark.getSubject14ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject14ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject15Mandatory()
					&& (detailmark.getSubject15TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject15TotalMarks())
							|| detailmark.getSubject15ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject15ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject16Mandatory()
					&& (detailmark.getSubject16TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject16TotalMarks())
							|| detailmark.getSubject16ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject16ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject17Mandatory()
					&& (detailmark.getSubject17TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject17TotalMarks())
							|| detailmark.getSubject17ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject17ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject18Mandatory()
					&& (detailmark.getSubject18TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject18TotalMarks())
							|| detailmark.getSubject18ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject18ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject19Mandatory()
					&& (detailmark.getSubject19TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject19TotalMarks())
							|| detailmark.getSubject1ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject19ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}
			if (detailmark.isSubject20Mandatory()
					&& (detailmark.getSubject20TotalMarks() == null
							|| StringUtils.isEmpty(detailmark
									.getSubject20TotalMarks())
							|| detailmark.getSubject20ObtainedMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject20ObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY);
					errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_MANDATORY,
							error);
				}
				return errors;
			}

			/* mandatory subject mark check end */
			if (detailmark.getSubject1TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject1TotalMarks())
					|| detailmark.getSubject2TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject2TotalMarks())
					|| detailmark.getSubject3TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject3TotalMarks())
					|| detailmark.getSubject4TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject4TotalMarks())
					|| detailmark.getSubject5TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject5TotalMarks())
					|| detailmark.getSubject6TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject6TotalMarks())
					|| detailmark.getSubject7TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject7TotalMarks())
					|| detailmark.getSubject8TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject8TotalMarks())
					|| detailmark.getSubject9TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject9TotalMarks())
					|| detailmark.getSubject10TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject10TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject10TotalMarks())
					|| detailmark.getSubject11TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject11TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject11TotalMarks())
					|| detailmark.getSubject12TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject12TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject12TotalMarks())
					|| detailmark.getSubject13TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject13TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject13TotalMarks())
					|| detailmark.getSubject14TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject14TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject14TotalMarks())
					|| detailmark.getSubject15TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject15TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject15TotalMarks())
					|| detailmark.getSubject16TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject16TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject16TotalMarks())
					|| detailmark.getSubject17TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject17TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject17TotalMarks())
					|| detailmark.getSubject18TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject18TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject18TotalMarks())
					|| detailmark.getSubject19TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject19TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject19TotalMarks())
					|| detailmark.getSubject20TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject20TotalMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject20TotalMarks())
					|| detailmark.getTotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getTotalMarks())
					&& !StringUtils.isNumeric(detailmark.getTotalMarks())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,
							error);
				}
				return errors;
			}
			if (detailmark.getSubject1ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject1ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject1ObtainedMarks())
					|| detailmark.getSubject2ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject2ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject2ObtainedMarks())
					|| detailmark.getSubject3ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject3ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject3ObtainedMarks())
					|| detailmark.getSubject4ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject4ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject4ObtainedMarks())
					|| detailmark.getSubject5ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject5ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject5ObtainedMarks())
					|| detailmark.getSubject6ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject6ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject6ObtainedMarks())
					|| detailmark.getSubject7ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject7ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject7ObtainedMarks())
					|| detailmark.getSubject8ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject8ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject8ObtainedMarks())
					|| detailmark.getSubject9ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject9ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject9ObtainedMarks())
					|| detailmark.getSubject10ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject10ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject10ObtainedMarks())
					|| detailmark.getSubject11ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject11ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject11ObtainedMarks())
					|| detailmark.getSubject12ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject12ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject12ObtainedMarks())
					|| detailmark.getSubject13ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject13ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject13ObtainedMarks())
					|| detailmark.getSubject14ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject14ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject14ObtainedMarks())
					|| detailmark.getSubject15ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject15ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject15ObtainedMarks())
					|| detailmark.getSubject16ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject16ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject16ObtainedMarks())
					|| detailmark.getSubject17ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject17ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject17ObtainedMarks())
					|| detailmark.getSubject18ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject18ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject18ObtainedMarks())
					|| detailmark.getSubject19ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject19ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject19ObtainedMarks())
					|| detailmark.getSubject20ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject20ObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getSubject20ObtainedMarks())
					|| detailmark.getTotalObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())
					&& !StringUtils.isNumeric(detailmark
							.getTotalObtainedMarks())) {
				if (errors
						.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER) != null
						&& !errors
								.get(
										CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
					errors.add(
							CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,
							error);
				}
				return errors;
			}

			if ((detailmark.getSubject1TotalMarks() == null || StringUtils
					.isEmpty(detailmark.getSubject1TotalMarks()))
					&& (detailmark.getSubject1ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject1ObtainedMarks()))
					|| (detailmark.getSubject2TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject2TotalMarks()))
					&& (detailmark.getSubject2ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject2ObtainedMarks()))
					|| (detailmark.getSubject3TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject3TotalMarks()))
					&& (detailmark.getSubject3ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject3ObtainedMarks()))
					|| (detailmark.getSubject4TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject4TotalMarks()))
					&& (detailmark.getSubject4ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject4ObtainedMarks()))
					|| (detailmark.getSubject5TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject5TotalMarks()))
					&& (detailmark.getSubject5ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject5ObtainedMarks()))
					|| (detailmark.getSubject6TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject6TotalMarks()))
					&& (detailmark.getSubject6ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject6ObtainedMarks()))
					|| (detailmark.getSubject7TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject7TotalMarks()))
					&& (detailmark.getSubject7ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject7ObtainedMarks()))
					|| (detailmark.getSubject8TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject8TotalMarks()))
					&& (detailmark.getSubject8ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject8ObtainedMarks()))
					|| (detailmark.getSubject9TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject9TotalMarks()))
					&& (detailmark.getSubject9ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject9ObtainedMarks()))
					|| (detailmark.getSubject10TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject10TotalMarks()))
					&& (detailmark.getSubject10ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject10ObtainedMarks()))
					|| (detailmark.getSubject11TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject11TotalMarks()))
					&& (detailmark.getSubject11ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject11ObtainedMarks()))
					|| (detailmark.getSubject12TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject12TotalMarks()))
					&& (detailmark.getSubject12ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject12ObtainedMarks()))
					|| (detailmark.getSubject13TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject13TotalMarks()))
					&& (detailmark.getSubject13ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject13ObtainedMarks()))
					|| (detailmark.getSubject14TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject14TotalMarks()))
					&& (detailmark.getSubject14ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject14ObtainedMarks()))
					|| (detailmark.getSubject15TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject15TotalMarks()))
					&& (detailmark.getSubject15ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject15ObtainedMarks()))
					|| (detailmark.getSubject16TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject16TotalMarks()))
					&& (detailmark.getSubject16ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject16ObtainedMarks()))
					|| (detailmark.getSubject17TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject17TotalMarks()))
					&& (detailmark.getSubject17ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject17ObtainedMarks()))
					|| (detailmark.getSubject18TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject18TotalMarks()))
					&& (detailmark.getSubject18ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject18ObtainedMarks()))
					|| (detailmark.getSubject19TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject19TotalMarks()))
					&& (detailmark.getSubject19ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject19ObtainedMarks()))
					|| (detailmark.getSubject20TotalMarks() == null || StringUtils
							.isEmpty(detailmark.getSubject20TotalMarks()))
					&& (detailmark.getSubject20ObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getSubject20ObtainedMarks()))
					|| (detailmark.getTotalMarks() == null || StringUtils
							.isEmpty(detailmark.getTotalMarks()))
					&& (detailmark.getTotalObtainedMarks() != null && !StringUtils
							.isEmpty(detailmark.getTotalObtainedMarks()))) {
				if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
							error);
				}
			}

			if (detailmark.getSubject1TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject1TotalMarks())
					&& detailmark.getSubject1ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject1ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject1TotalMarks()) < Integer
							.parseInt(detailmark.getSubject1ObtainedMarks())
					|| detailmark.getSubject2TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject2TotalMarks())
					&& detailmark.getSubject2ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject2ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject2TotalMarks()) < Integer
							.parseInt(detailmark.getSubject2ObtainedMarks())
					|| detailmark.getSubject3TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject3TotalMarks())
					&& detailmark.getSubject3ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject3ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject3TotalMarks()) < Integer
							.parseInt(detailmark.getSubject3ObtainedMarks())
					|| detailmark.getSubject4TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject4TotalMarks())
					&& detailmark.getSubject4ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject4ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject4TotalMarks()) < Integer
							.parseInt(detailmark.getSubject4ObtainedMarks())
					|| detailmark.getSubject5TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject5TotalMarks())
					&& detailmark.getSubject5ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject5ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject5TotalMarks()) < Integer
							.parseInt(detailmark.getSubject5ObtainedMarks())
					|| detailmark.getSubject6TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject6TotalMarks())
					&& detailmark.getSubject6ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject6ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject6TotalMarks()) < Integer
							.parseInt(detailmark.getSubject6ObtainedMarks())
					|| detailmark.getSubject7TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject7TotalMarks())
					&& detailmark.getSubject7ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject7ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject7TotalMarks()) < Integer
							.parseInt(detailmark.getSubject7ObtainedMarks())
					|| detailmark.getSubject8TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject8TotalMarks())
					&& detailmark.getSubject8ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject8ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject8TotalMarks()) < Integer
							.parseInt(detailmark.getSubject8ObtainedMarks())
					|| detailmark.getSubject9TotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getSubject9TotalMarks())
					&& detailmark.getSubject9ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject9ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject9TotalMarks()) < Integer
							.parseInt(detailmark.getSubject9ObtainedMarks())
					|| detailmark.getSubject10TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject10TotalMarks())
					&& detailmark.getSubject10ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject10ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject10TotalMarks()) < Integer
							.parseInt(detailmark.getSubject10ObtainedMarks())
					|| detailmark.getSubject11TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject11TotalMarks())
					&& detailmark.getSubject11ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject11ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject11TotalMarks()) < Integer
							.parseInt(detailmark.getSubject11ObtainedMarks())
					|| detailmark.getSubject12TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject12TotalMarks())
					&& detailmark.getSubject12ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject12ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject12TotalMarks()) < Integer
							.parseInt(detailmark.getSubject12ObtainedMarks())
					|| detailmark.getSubject13TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject13TotalMarks())
					&& detailmark.getSubject13ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject13ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject13TotalMarks()) < Integer
							.parseInt(detailmark.getSubject13ObtainedMarks())
					|| detailmark.getSubject14TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject14TotalMarks())
					&& detailmark.getSubject14ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject14ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject14TotalMarks()) < Integer
							.parseInt(detailmark.getSubject14ObtainedMarks())
					|| detailmark.getSubject15TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject15TotalMarks())
					&& detailmark.getSubject15ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject15ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject15TotalMarks()) < Integer
							.parseInt(detailmark.getSubject15ObtainedMarks())
					|| detailmark.getSubject16TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject16TotalMarks())
					&& detailmark.getSubject16ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject16ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject16TotalMarks()) < Integer
							.parseInt(detailmark.getSubject16ObtainedMarks())
					|| detailmark.getSubject17TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject17TotalMarks())
					&& detailmark.getSubject17ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject17ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject17TotalMarks()) < Integer
							.parseInt(detailmark.getSubject17ObtainedMarks())
					|| detailmark.getSubject18TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject18TotalMarks())
					&& detailmark.getSubject18ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject18ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject18TotalMarks()) < Integer
							.parseInt(detailmark.getSubject18ObtainedMarks())
					|| detailmark.getSubject19TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject19TotalMarks())
					&& detailmark.getSubject19ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject19ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject19TotalMarks()) < Integer
							.parseInt(detailmark.getSubject19ObtainedMarks())
					|| detailmark.getSubject20TotalMarks() != null
					&& !StringUtils
							.isEmpty(detailmark.getSubject20TotalMarks())
					&& detailmark.getSubject20ObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark
							.getSubject20ObtainedMarks())
					&& Integer.parseInt(detailmark.getSubject20TotalMarks()) < Integer
							.parseInt(detailmark.getSubject20ObtainedMarks())
					|| detailmark.getTotalMarks() != null
					&& !StringUtils.isEmpty(detailmark.getTotalMarks())
					&& detailmark.getTotalObtainedMarks() != null
					&& !StringUtils.isEmpty(detailmark.getTotalObtainedMarks())
					&& Integer.parseInt(detailmark.getTotalMarks()) < Integer
							.parseInt(detailmark.getTotalObtainedMarks())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
					errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
							error);
				}
			}

			if (detailmark.isSemesterMark()) {
				if (detailmark.getSubject1_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject1_languagewise_TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject1_languagewise_TotalMarks())
						|| detailmark.getSubject2_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject2TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject2TotalMarks())
						|| detailmark.getSubject3_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject3TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject3TotalMarks())
						|| detailmark.getSubject4_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject4TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject4TotalMarks())
						|| detailmark.getSubject5_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject5TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject5TotalMarks())
						|| detailmark.getSubject6_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject6_languagewise_TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject6_languagewise_TotalMarks())
						|| detailmark.getSubject7_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject7_languagewise_TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject7_languagewise_TotalMarks())
						|| detailmark.getSubject8_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject8_languagewise_TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject8_languagewise_TotalMarks())
						|| detailmark.getSubject9_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject9_languagewise_TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject9_languagewise_TotalMarks())
						|| detailmark.getSubject10_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject10_languagewise_TotalMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject10_languagewise_TotalMarks())
						|| detailmark.getTotal_languagewise_Marks() != null
						&& !StringUtils.isEmpty(detailmark
								.getTotal_languagewise_Marks())
						&& !StringUtils.isNumeric(detailmark
								.getTotal_languagewise_Marks())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,
										error);
					}
					return errors;
				}
				if (detailmark.getSubject1_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject1_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject1_languagewise_ObtainedMarks())
						|| detailmark.getSubject2_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject2_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject2_languagewise_ObtainedMarks())
						|| detailmark.getSubject3_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject3_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject3_languagewise_ObtainedMarks())
						|| detailmark.getSubject4_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject4_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject4_languagewise_ObtainedMarks())
						|| detailmark.getSubject5_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject5_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject5_languagewise_ObtainedMarks())
						|| detailmark.getSubject6_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject6_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject6_languagewise_ObtainedMarks())
						|| detailmark.getSubject7_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject7_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject7_languagewise_ObtainedMarks())
						|| detailmark.getSubject8_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject8_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject8_languagewise_ObtainedMarks())
						|| detailmark.getSubject9_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject9_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject9_languagewise_ObtainedMarks())
						|| detailmark.getSubject10_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject10_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getSubject10_languagewise_ObtainedMarks())
						|| detailmark.getTotal_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getTotal_languagewise_ObtainedMarks())
						&& !StringUtils.isNumeric(detailmark
								.getTotal_languagewise_ObtainedMarks())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,
										error);
					}
					return errors;
				}

				if ((detailmark.getSubject1_languagewise_TotalMarks() == null || StringUtils
						.isEmpty(detailmark
								.getSubject1_languagewise_TotalMarks()))
						&& (detailmark.getSubject1_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject1_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject2_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject2_languagewise_TotalMarks()))
						&& (detailmark.getSubject2_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject2_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject3_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject3_languagewise_TotalMarks()))
						&& (detailmark.getSubject3_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject3_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject4_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject4_languagewise_TotalMarks()))
						&& (detailmark.getSubject4_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject4_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject5_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject5_languagewise_TotalMarks()))
						&& (detailmark.getSubject5_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject5_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject6_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject6_languagewise_TotalMarks()))
						&& (detailmark.getSubject6_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject6_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject7_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject7_languagewise_TotalMarks()))
						&& (detailmark.getSubject7_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject7_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject8_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject8_languagewise_TotalMarks()))
						&& (detailmark.getSubject8_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject8_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject9_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject9_languagewise_TotalMarks()))
						&& (detailmark.getSubject9_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject9_languagewise_ObtainedMarks()))
						|| (detailmark.getSubject10_languagewise_TotalMarks() == null || StringUtils
								.isEmpty(detailmark
										.getSubject10_languagewise_TotalMarks()))
						&& (detailmark
								.getSubject10_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getSubject10_languagewise_ObtainedMarks()))
						|| (detailmark.getTotal_languagewise_Marks() == null || StringUtils
								.isEmpty(detailmark
										.getTotal_languagewise_Marks()))
						&& (detailmark.getTotal_languagewise_ObtainedMarks() != null && !StringUtils
								.isEmpty(detailmark
										.getTotal_languagewise_ObtainedMarks()))) {
					if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
								error);
					}
				}

				if (detailmark.getSubject1_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject1_languagewise_TotalMarks())
						&& detailmark.getSubject1_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject1_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject1_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject1_languagewise_ObtainedMarks())
						|| detailmark.getSubject2_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject2_languagewise_TotalMarks())
						&& detailmark.getSubject2_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject2_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject2_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject2_languagewise_ObtainedMarks())
						|| detailmark.getSubject3_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject3_languagewise_TotalMarks())
						&& detailmark.getSubject3_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject3_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject3_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject3_languagewise_ObtainedMarks())
						|| detailmark.getSubject4_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject4_languagewise_TotalMarks())
						&& detailmark.getSubject4_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject4_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject4_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject4_languagewise_ObtainedMarks())
						|| detailmark.getSubject5_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject5_languagewise_TotalMarks())
						&& detailmark.getSubject5_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject5_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject5_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject5_languagewise_ObtainedMarks())
						|| detailmark.getSubject6_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject6_languagewise_TotalMarks())
						&& detailmark.getSubject6_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject6_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject6_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject6_languagewise_ObtainedMarks())
						|| detailmark.getSubject7_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject7_languagewise_TotalMarks())
						&& detailmark.getSubject7_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject7_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject7_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject7_languagewise_ObtainedMarks())
						|| detailmark.getSubject8_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject8_languagewise_TotalMarks())
						&& detailmark.getSubject8_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject8_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject8_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject8_languagewise_ObtainedMarks())
						|| detailmark.getSubject9_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject9_languagewise_TotalMarks())
						&& detailmark.getSubject9_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject9_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject9_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject9_languagewise_ObtainedMarks())
						|| detailmark.getSubject10_languagewise_TotalMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject10_languagewise_TotalMarks())
						&& detailmark.getSubject10_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getSubject10_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getSubject10_languagewise_TotalMarks()) < Integer
								.parseInt(detailmark
										.getSubject10_languagewise_ObtainedMarks())
						|| detailmark.getTotal_languagewise_Marks() != null
						&& !StringUtils.isEmpty(detailmark
								.getTotal_languagewise_Marks())
						&& detailmark.getTotal_languagewise_ObtainedMarks() != null
						&& !StringUtils.isEmpty(detailmark
								.getTotal_languagewise_ObtainedMarks())
						&& Integer.parseInt(detailmark
								.getTotal_languagewise_Marks()) < Integer
								.parseInt(detailmark
										.getTotal_languagewise_ObtainedMarks())) {
					if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
								error);
					}
				}
			}
		}
		log.info("exit validateMarks...");
		return errors;
	}

	/**
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
		log.info("enter initSemesterMarkEditPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {

			String indexString = request
					.getParameter(PRCStudentEditAction.EDITCOUNTID);
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null) {
					session.setAttribute(PRCStudentEditAction.EDITCOUNTID,
							indexString);
					int index = Integer.parseInt(indexString);
					List<EdnQualificationTO> quals = admForm
							.getApplicantDetails().getEdnQualificationList();

					if (quals != null) {
						Iterator<EdnQualificationTO> qualItr = quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
									.next();
							if (qualTO.getId() == index) {
								if (qualTO.getSemesterList() != null) {
									List<ApplicantMarkDetailsTO> semList = new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize = semList.size();
									int sizeDiff = CMSConstants.MAX_CANDIDATE_SEMESTERS
											- listSize;
									if (sizeDiff > 0) {
										for (int cnt = listSize + 1; cnt <= CMSConstants.MAX_CANDIDATE_SEMESTERS; cnt++) {
											ApplicantMarkDetailsTO mrkTo = new ApplicantMarkDetailsTO();
											mrkTo.setSemesterNo(cnt);
											mrkTo.setSemesterName("Semester"
													+ cnt);
											semList.add(mrkTo);
										}
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								} else {
									admForm
											.setSemesterList(new ArrayList<ApplicantMarkDetailsTO>());
								}
								admForm.setIsLanguageWiseMarks(String
										.valueOf(qualTO.isLanguage()));

							}
						}
						int totalobtainedMarkWithLanguage=0;
						int totalMarkWithLanguage=0;
						int totalobtainedMarkWithoutLan=0;
						int totalMarkWithoutLan=0;
						for(ApplicantMarkDetailsTO detailsTO:admForm.getSemesterList())
						{
							if(admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
							{
								if(detailsTO.getMarksObtained_languagewise()!=null && !detailsTO.getMarksObtained_languagewise().isEmpty())
								totalobtainedMarkWithLanguage+=Integer.parseInt(detailsTO.getMarksObtained_languagewise());
								if(detailsTO.getMaxMarks_languagewise()!=null && !detailsTO.getMaxMarks_languagewise().isEmpty())
								totalMarkWithLanguage+=Integer.parseInt(detailsTO.getMaxMarks_languagewise());
							}
							if(detailsTO.getMarksObtained()!=null && !detailsTO.getMarksObtained().isEmpty())
							totalobtainedMarkWithoutLan+=Integer.parseInt(detailsTO.getMarksObtained());
							if(detailsTO.getMaxMarks()!=null && !detailsTO.getMaxMarks().isEmpty())
							totalMarkWithoutLan+=Integer.parseInt(detailsTO.getMaxMarks());	
						}
						
						if(admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
						{
							admForm.setTotalobtainedMarkWithLanguage(""+totalobtainedMarkWithLanguage);
							admForm.setTotalMarkWithLanguage(""+totalMarkWithLanguage);
						}
						admForm.setTotalobtainedMarkWithoutLan(""+totalobtainedMarkWithoutLan);
						admForm.setTotalMarkWithoutLan(""+totalMarkWithoutLan);
					}
				} else
					session.removeAttribute(PRCStudentEditAction.EDITCOUNTID);
			}
		} catch (Exception e) {
			log.error("error in initSemesterMarkEditPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initSemesterMarkEditPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_SEM_MARK_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterEditMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitSemesterEditMark...");
		StudentEditForm admForm = (StudentEditForm) form;
		HttpSession session = request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session
						.getAttribute(PRCStudentEditAction.EDITCOUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			List<ApplicantMarkDetailsTO> semesterMarks = admForm
					.getSemesterList();
			ActionMessages errors = validateEditSemesterMarks(semesterMarks);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_SEM_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm
					.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr = qualifications
						.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
							.next();
					if (qualTO.getId() == detailMarkIndex) {
						Set<ApplicantMarkDetailsTO> semDetails = new HashSet<ApplicantMarkDetailsTO>();
						semDetails.addAll(semesterMarks);
						qualTO.setSemesterList(semDetails);
					}
				}
			}
			StudentQualifyExamDetailsTO examTo = prepareStudentQualifyDetail(semesterMarks);
			 admForm.getApplicantDetails().setQualifyDetailsTo(examTo);
		} catch (Exception e) {
			log.error("error in submitSemesterEditMark...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitSemesterEditMark...");

		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}

	/**
	 * validate semester mark in edit page
	 * 
	 * @param semesterMarks
	 * @return
	 */
	private ActionMessages validateEditSemesterMarks(
			List<ApplicantMarkDetailsTO> semesterMarks) {
		log.info("enter validateEditSemesterMarks...");
		ActionMessages errors = new ActionMessages();
		if (semesterMarks != null) {
			Iterator<ApplicantMarkDetailsTO> semItr = semesterMarks.iterator();
			while (semItr.hasNext()) {
				ApplicantMarkDetailsTO semMarkTO = (ApplicantMarkDetailsTO) semItr
						.next();

				if (semMarkTO != null && semMarkTO.getMarksObtained() != null
						&& !StringUtils.isEmpty(semMarkTO.getMarksObtained())
						&& !StringUtils.isNumeric(semMarkTO.getMarksObtained())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,
										error);
					}
				}

				if (semMarkTO != null && semMarkTO.getMaxMarks() != null
						&& !StringUtils.isEmpty(semMarkTO.getMaxMarks())
						&& !StringUtils.isNumeric(semMarkTO.getMaxMarks())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,
										error);
					}
				}
				if (semMarkTO != null
						&& semMarkTO.getMarksObtained() != null
						&& (semMarkTO.getMaxMarks() == null || StringUtils
								.isEmpty(semMarkTO.getMaxMarks()))
						&& !StringUtils.isEmpty(semMarkTO.getMarksObtained())) {
					if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
								error);
					}
				}

				if (semMarkTO != null && semMarkTO.getMarksObtained() != null
						&& semMarkTO.getMaxMarks() != null
						&& !StringUtils.isEmpty(semMarkTO.getMarksObtained())
						&& StringUtils.isNumeric(semMarkTO.getMarksObtained())
						&& !StringUtils.isEmpty(semMarkTO.getMaxMarks())
						&& StringUtils.isNumeric(semMarkTO.getMaxMarks())) {
					if (Integer.parseInt(semMarkTO.getMarksObtained()) > Integer
							.parseInt(semMarkTO.getMaxMarks())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
											error);
						}
					}
				}

				if (semMarkTO != null
						&& semMarkTO.getMarksObtained_languagewise() != null
						&& !StringUtils.isEmpty(semMarkTO
								.getMarksObtained_languagewise())
						&& !StringUtils.isNumeric(semMarkTO
								.getMarksObtained_languagewise())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,
										error);
					}
				}

				if (semMarkTO != null
						&& semMarkTO.getMaxMarks_languagewise() != null
						&& !StringUtils.isEmpty(semMarkTO
								.getMaxMarks_languagewise())
						&& !StringUtils.isNumeric(semMarkTO
								.getMaxMarks_languagewise())) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
						errors
								.add(
										CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,
										error);
					}
				}
				if (semMarkTO != null
						&& semMarkTO.getMarksObtained_languagewise() != null
						&& (semMarkTO.getMaxMarks_languagewise() == null || StringUtils
								.isEmpty(semMarkTO.getMaxMarks_languagewise()))
						&& !StringUtils.isEmpty(semMarkTO
								.getMarksObtained_languagewise())) {
					if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
								error);
					}
				}

				if (semMarkTO != null
						&& semMarkTO.getMarksObtained_languagewise() != null
						&& semMarkTO.getMaxMarks_languagewise() != null
						&& !StringUtils.isEmpty(semMarkTO
								.getMarksObtained_languagewise())
						&& StringUtils.isNumeric(semMarkTO
								.getMarksObtained_languagewise())
						&& !StringUtils.isEmpty(semMarkTO
								.getMaxMarks_languagewise())
						&& StringUtils.isNumeric(semMarkTO
								.getMaxMarks_languagewise())) {
					if (Integer.parseInt(semMarkTO
							.getMarksObtained_languagewise()) > Integer
							.parseInt(semMarkTO.getMaxMarks_languagewise())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
											error);
						}
					}
				}

			}

		}
		log.info("exit validateEditSemesterMarks...");
		return errors;

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initlateralEntryEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initlateralEntryEditPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			if (admForm.getApplicantDetails().getLateralDetails() == null
					|| admForm.getApplicantDetails().getLateralDetails()
							.isEmpty()) {
				List<ApplicantLateralDetailsTO> lateralDetails = new ArrayList<ApplicantLateralDetailsTO>();
				for (int i = 0; i < CMSConstants.MAX_CANDIDATE_LATERALS; i++) {
					ApplicantLateralDetailsTO to = new ApplicantLateralDetailsTO();
					to.setSemesterNo(i);
					lateralDetails.add(to);
				}
				Collections.sort(lateralDetails);
				admForm.setLateralDetails(lateralDetails);
			} else {
				if (admForm.getApplicantDetails().getLateralDetails() != null
						&& !admForm.getApplicantDetails().getLateralDetails()
								.isEmpty()) {

					Iterator<ApplicantLateralDetailsTO> latItr = admForm
							.getApplicantDetails().getLateralDetails()
							.iterator();
					while (latItr.hasNext()) {
						ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr
								.next();
						if (lateralTO != null) {
							if (lateralTO.getInstituteAddress() != null
									&& !StringUtils.isEmpty(lateralTO
											.getInstituteAddress()))
								admForm.setLateralInstituteAddress(lateralTO
										.getInstituteAddress());
							if (lateralTO.getStateName() != null
									&& !StringUtils.isEmpty(lateralTO
											.getStateName()))
								admForm.setLateralStateName(lateralTO
										.getStateName());
							if (lateralTO.getUniversityName() != null
									&& !StringUtils.isEmpty(lateralTO
											.getUniversityName()))
								admForm.setLateralUniversityName(lateralTO
										.getUniversityName());
						}

					}
					Collections.sort(admForm.getApplicantDetails()
							.getLateralDetails());
					admForm.setLateralDetails(admForm.getApplicantDetails()
							.getLateralDetails());
				}

			}
		} catch (Exception e) {
			log.error("error in initlateralEntryEditPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initlateralEntryEditPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_LATERAL_MARK_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitLateralEntryEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitLateralEntryEdit...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			// Validate marks
			validateLateralMarks(admForm, errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_LATERAL_MARK_PAGE);
			}
			// process lateral details
			List<ApplicantLateralDetailsTO> lateralDetails = admForm
					.getLateralDetails();
			if (lateralDetails != null && !lateralDetails.isEmpty()) {
				Set<ApplicantLateralDetails> lateralBos = new HashSet<ApplicantLateralDetails>();
				Iterator<ApplicantLateralDetailsTO> latItr = lateralDetails
						.iterator();
				while (latItr.hasNext()) {
					ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr
							.next();
					if (lateralTO != null) {
						ApplicantLateralDetails bo = new ApplicantLateralDetails();
						bo.setId(lateralTO.getId());
						if (lateralTO.getAdmApplnId() != 0) {
							AdmAppln app = new AdmAppln();
							app.setId(lateralTO.getAdmApplnId());
							bo.setAdmAppln(app);
						}
						bo.setInstituteAddress(admForm
								.getLateralInstituteAddress());
						if (lateralTO.getMarksObtained() != null
								&& !StringUtils.isEmpty(lateralTO
										.getMarksObtained().trim())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMarksObtained().trim()))
							bo.setMarksObtained(new BigDecimal(lateralTO
									.getMarksObtained().trim()));
						if (lateralTO.getMaxMarks() != null
								&& !StringUtils.isEmpty(lateralTO.getMaxMarks()
										.trim())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMaxMarks().trim()))
							bo.setMaxMarks(new BigDecimal(lateralTO
									.getMaxMarks().trim()));
						if (lateralTO.getMinMarks() != null
								&& !StringUtils.isEmpty(lateralTO.getMinMarks()
										.trim())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(lateralTO
									.getMinMarks().trim()));
						if (lateralTO.getMonthPass() != null
								&& !StringUtils.isEmpty(lateralTO
										.getMonthPass().trim())
								&& StringUtils.isNumeric(lateralTO
										.getMonthPass().trim()))
							bo.setMonthPass(Integer.parseInt(lateralTO
									.getMonthPass().trim()));

						if (lateralTO.getYearPass() != null
								&& !StringUtils.isEmpty(lateralTO.getYearPass()
										.trim())
								&& StringUtils.isNumeric(lateralTO
										.getYearPass().trim()))
							bo.setYearPass(Integer.parseInt(lateralTO
									.getYearPass().trim()));
						bo.setSemesterName(lateralTO.getSemesterName());
						bo.setSemesterNo(lateralTO.getSemesterNo());
						bo.setStateName(admForm.getLateralStateName());
						bo
								.setUniversityName(admForm
										.getLateralUniversityName());
						lateralBos.add(bo);
					}

				}

				if (lateralBos != null && !lateralBos.isEmpty()) {
					admForm.getApplicantDetails().setLateralDetailBos(
							lateralBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitLateralEntryEdit...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitLateralEntryEdit...");

		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}

	/**
	 * validate lateral marks
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateLateralMarks(StudentEditForm admForm,
			ActionErrors errors) {
		log.info("enter validateLateralMarks...");
		List<ApplicantLateralDetailsTO> lateralDetails = admForm
				.getLateralDetails();
		if (lateralDetails != null && !lateralDetails.isEmpty()) {
			Iterator<ApplicantLateralDetailsTO> latItr = lateralDetails
					.iterator();
			while (latItr.hasNext()) {
				ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr
						.next();
				if (lateralTO != null) {
					if (lateralTO.getMarksObtained() != null
							&& !StringUtils.isEmpty(lateralTO
									.getMarksObtained().trim())) {
						if (!CommonUtil.isValidDecimal(lateralTO
								.getMarksObtained())) {
							// error in valid mark obtain
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID,
												error);
							}
						}
					}
					if (lateralTO.getMaxMarks() != null
							&& !StringUtils.isEmpty(lateralTO.getMaxMarks()
									.trim())) {
						if (!CommonUtil.isValidDecimal(lateralTO.getMaxMarks())) {
							// error in valid max mark
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID,
												error);
							}
						}
					}
					if (lateralTO.getMinMarks() != null
							&& !StringUtils.isEmpty(lateralTO.getMinMarks()
									.trim())) {
						if (!CommonUtil.isValidDecimal(lateralTO.getMinMarks())) {
							// error in valid min mark
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID,
												error);
							}
						}
					}
					if (lateralTO.getMarksObtained() != null
							&& !StringUtils.isEmpty(lateralTO
									.getMarksObtained().trim())
							&& lateralTO.getMaxMarks() != null
							&& !StringUtils.isEmpty(lateralTO.getMaxMarks()
									.trim())) {

						if (CommonUtil.isValidDecimal(lateralTO
								.getMarksObtained())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMaxMarks())) {
							if (Double
									.parseDouble(lateralTO.getMarksObtained()) > Double
									.parseDouble(lateralTO.getMaxMarks())) {
								// error mark obtain large than max mark
								if (errors
										.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER) != null
										&& !errors
												.get(
														CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER)
												.hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER);
									errors
											.add(
													CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER,
													error);
								}
							}
						}

					}

					if (lateralTO.getMinMarks() != null
							&& !StringUtils.isEmpty(lateralTO.getMinMarks()
									.trim())
							&& lateralTO.getMaxMarks() != null
							&& !StringUtils.isEmpty(lateralTO.getMaxMarks()
									.trim())) {

						if (CommonUtil.isValidDecimal(lateralTO.getMinMarks())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMaxMarks())) {
							if (Double.parseDouble(lateralTO.getMinMarks()) > Double
									.parseDouble(lateralTO.getMaxMarks())) {
								// error min mark large than max mark
								if (errors
										.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER) != null
										&& !errors
												.get(
														CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER)
												.hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER);
									errors
											.add(
													CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER,
													error);
								}
							}
						}

					}

					if (lateralTO.getYearPass() != null
							&& !StringUtils.isEmpty(lateralTO.getYearPass())
							&& StringUtils.isNumeric(lateralTO.getYearPass())) {
						if (CommonUtil.isFutureYear(Integer.parseInt(lateralTO
								.getYearPass()))) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE,
												error);
							}
						}
					}

					if (lateralTO.getMonthPass() != null
							&& !StringUtils.isEmpty(lateralTO.getMonthPass())
							&& StringUtils.isNumeric(lateralTO.getMonthPass())) {
						if (lateralTO.getYearPass() != null
								&& !StringUtils
										.isEmpty(lateralTO.getYearPass())
								&& StringUtils.isNumeric(lateralTO
										.getYearPass())) {
							if (!CommonUtil.isPastYear(Integer
									.parseInt(lateralTO.getYearPass()))) {

								if (CommonUtil
										.isFutureMonth(Integer
												.parseInt(lateralTO
														.getMonthPass()) - 1)) {
									if (errors
											.get(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE) != null
											&& !errors
													.get(
															CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE)
													.hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE);
										errors
												.add(
														CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE,
														error);
									}
								}
							}
						}
					}
				}
			}
		}

		log.info("exit validateLateralMarks...");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTransferEntryEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initTransferEntryEditPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			if (admForm.getApplicantDetails().getTransferDetails() == null
					|| admForm.getApplicantDetails().getTransferDetails()
							.isEmpty()) {
				List<ApplicantTransferDetailsTO> transferDetails = new ArrayList<ApplicantTransferDetailsTO>();
				for (int i = 0; i < CMSConstants.MAX_CANDIDATE_TRANSFERS; i++) {
					ApplicantTransferDetailsTO to = new ApplicantTransferDetailsTO();
					to.setSemesterNo(i);
					transferDetails.add(to);
				}
				Collections.sort(transferDetails);
				admForm.setTransferDetails(transferDetails);
			} else {
				if (admForm.getApplicantDetails().getTransferDetails() != null
						&& !admForm.getApplicantDetails().getTransferDetails()
								.isEmpty()) {

					Iterator<ApplicantTransferDetailsTO> latItr = admForm
							.getApplicantDetails().getTransferDetails()
							.iterator();
					while (latItr.hasNext()) {
						ApplicantTransferDetailsTO tranfrTO = (ApplicantTransferDetailsTO) latItr
								.next();
						if (tranfrTO != null) {
							if (tranfrTO.getInstituteAddr() != null
									&& !StringUtils.isEmpty(tranfrTO
											.getInstituteAddr()))
								admForm.setTransInstituteAddr(tranfrTO
										.getInstituteAddr());
							if (tranfrTO.getRegistationNo() != null
									&& !StringUtils.isEmpty(tranfrTO
											.getRegistationNo()))
								admForm.setTransRegistationNo(tranfrTO
										.getRegistationNo());
							if (tranfrTO.getUniversityAppNo() != null
									&& !StringUtils.isEmpty(tranfrTO
											.getUniversityAppNo()))
								admForm.setTransUnvrAppNo(tranfrTO
										.getUniversityAppNo());
							if (tranfrTO.getArrearDetail() != null
									&& !StringUtils.isEmpty(tranfrTO
											.getArrearDetail()))
								admForm.setTransArrearDetail(tranfrTO
										.getArrearDetail());
						}

					}
					Collections.sort(admForm.getApplicantDetails()
							.getTransferDetails());
					admForm.setTransferDetails(admForm.getApplicantDetails()
							.getTransferDetails());
				}

			}
		} catch (Exception e) {
			log.error("error in initTransferEntryEditPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initTransferEntryEditPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_TRANSFER_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitTransferEditEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitTransferEditEntry...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			// Validate marks
			validateTransferMarks(admForm, errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_TRANSFER_PAGE);
			}
			// process lateral details
			List<ApplicantTransferDetailsTO> transferDetails = admForm
					.getTransferDetails();
			if (transferDetails != null && !transferDetails.isEmpty()) {
				Set<ApplicantTransferDetails> transferBos = new HashSet<ApplicantTransferDetails>();
				Iterator<ApplicantTransferDetailsTO> latItr = transferDetails
						.iterator();
				while (latItr.hasNext()) {
					ApplicantTransferDetailsTO transferTO = (ApplicantTransferDetailsTO) latItr
							.next();
					if (transferTO != null) {
						ApplicantTransferDetails bo = new ApplicantTransferDetails();
						bo.setId(transferTO.getId());
						if (transferTO.getAdmApplnId() != 0) {
							AdmAppln app = new AdmAppln();
							app.setId(transferTO.getAdmApplnId());
							bo.setAdmAppln(app);
						}
						bo.setInstituteAddr(admForm.getTransInstituteAddr());
						if (transferTO.getMarksObtained() != null
								&& !StringUtils.isEmpty(transferTO
										.getMarksObtained().trim())
								&& CommonUtil.isValidDecimal(transferTO
										.getMarksObtained().trim()))
							bo.setMarksObtained(new BigDecimal(transferTO
									.getMarksObtained().trim()));
						if (transferTO.getMaxMarks() != null
								&& !StringUtils.isEmpty(transferTO
										.getMaxMarks().trim())
								&& CommonUtil.isValidDecimal(transferTO
										.getMaxMarks().trim()))
							bo.setMaxMarks(new BigDecimal(transferTO
									.getMaxMarks().trim()));
						if (transferTO.getMinMarks() != null
								&& !StringUtils.isEmpty(transferTO
										.getMinMarks().trim())
								&& CommonUtil.isValidDecimal(transferTO
										.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(transferTO
									.getMinMarks().trim()));
						if (transferTO.getMonthPass() != null
								&& !StringUtils.isEmpty(transferTO
										.getMonthPass().trim())
								&& StringUtils.isNumeric(transferTO
										.getMonthPass().trim()))
							bo.setMonthPass(Integer.parseInt(transferTO
									.getMonthPass().trim()));

						if (transferTO.getYearPass() != null
								&& !StringUtils.isEmpty(transferTO
										.getYearPass().trim())
								&& StringUtils.isNumeric(transferTO
										.getYearPass().trim()))
							bo.setYearPass(Integer.parseInt(transferTO
									.getYearPass().trim()));
						bo.setSemesterName(transferTO.getSemesterName());
						bo.setSemesterNo(transferTO.getSemesterNo());
						bo.setRegistationNo(admForm.getTransRegistationNo());
						bo.setUniversityAppNo(admForm.getTransUnvrAppNo());
						bo.setArrearDetail(admForm.getTransArrearDetail());
						transferBos.add(bo);
					}

				}

				if (transferBos != null && !transferBos.isEmpty()) {
					admForm.getApplicantDetails().setTransferDetailBos(
							transferBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitTransferEditEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitLateralEntryEdit...");

		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}

	/**
	 * validate lateral marks
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateTransferMarks(StudentEditForm admForm,
			ActionErrors errors) {
		log.info("enter validateTransferMarks...");
		List<ApplicantTransferDetailsTO> transferDetails = admForm
				.getTransferDetails();
		if (transferDetails != null && !transferDetails.isEmpty()) {
			Iterator<ApplicantTransferDetailsTO> latItr = transferDetails
					.iterator();
			while (latItr.hasNext()) {
				ApplicantTransferDetailsTO transferTO = (ApplicantTransferDetailsTO) latItr
						.next();
				if (transferTO != null) {
					if (transferTO.getMarksObtained() != null
							&& !StringUtils.isEmpty(transferTO
									.getMarksObtained().trim())) {
						if (!CommonUtil.isValidDecimal(transferTO
								.getMarksObtained())) {
							// error in valid mark obtain
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_INVALID,
												error);
							}
						}
					}
					if (transferTO.getMaxMarks() != null
							&& !StringUtils.isEmpty(transferTO.getMaxMarks()
									.trim())) {
						if (!CommonUtil
								.isValidDecimal(transferTO.getMaxMarks())) {
							// error in valid max mark
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_MAXMARK_INVALID,
												error);
							}
						}
					}
					if (transferTO.getMinMarks() != null
							&& !StringUtils.isEmpty(transferTO.getMinMarks()
									.trim())) {
						if (!CommonUtil
								.isValidDecimal(transferTO.getMinMarks())) {
							// error in valid min mark
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_INVALID,
												error);
							}
						}
					}
					if (transferTO.getMarksObtained() != null
							&& !StringUtils.isEmpty(transferTO
									.getMarksObtained().trim())
							&& transferTO.getMaxMarks() != null
							&& !StringUtils.isEmpty(transferTO.getMaxMarks()
									.trim())) {

						if (CommonUtil.isValidDecimal(transferTO
								.getMarksObtained())
								&& CommonUtil.isValidDecimal(transferTO
										.getMaxMarks())) {
							if (Double.parseDouble(transferTO
									.getMarksObtained()) > Double
									.parseDouble(transferTO.getMaxMarks())) {
								// error mark obtain large than max mark
								if (errors
										.get(CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER) != null
										&& !errors
												.get(
														CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER)
												.hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER);
									errors
											.add(
													CMSConstants.ADMISSIONFORM_LATERAL_OBTAINMARK_LARGER,
													error);
								}
							}
						}

					}

					if (transferTO.getMinMarks() != null
							&& !StringUtils.isEmpty(transferTO.getMinMarks()
									.trim())
							&& transferTO.getMaxMarks() != null
							&& !StringUtils.isEmpty(transferTO.getMaxMarks()
									.trim())) {

						if (CommonUtil.isValidDecimal(transferTO.getMinMarks())
								&& CommonUtil.isValidDecimal(transferTO
										.getMaxMarks())) {
							if (Double.parseDouble(transferTO.getMinMarks()) > Double
									.parseDouble(transferTO.getMaxMarks())) {
								// error min mark large than max mark
								if (errors
										.get(CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER) != null
										&& !errors
												.get(
														CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER)
												.hasNext()) {
									ActionMessage error = new ActionMessage(
											CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER);
									errors
											.add(
													CMSConstants.ADMISSIONFORM_LATERAL_MINMARK_LARGER,
													error);
								}
							}
						}

					}

					if (transferTO.getYearPass() != null
							&& !StringUtils.isEmpty(transferTO.getYearPass())
							&& StringUtils.isNumeric(transferTO.getYearPass())) {
						if (CommonUtil.isFutureYear(Integer.parseInt(transferTO
								.getYearPass()))) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_LATERAL_YEAR_FUTURE,
												error);
							}
						}
					}

					if (transferTO.getMonthPass() != null
							&& !StringUtils.isEmpty(transferTO.getMonthPass())
							&& StringUtils.isNumeric(transferTO.getMonthPass())) {
						if (transferTO.getYearPass() != null
								&& !StringUtils.isEmpty(transferTO
										.getYearPass())
								&& StringUtils.isNumeric(transferTO
										.getYearPass())) {
							if (!CommonUtil.isPastYear(Integer
									.parseInt(transferTO.getYearPass()))) {

								if (CommonUtil
										.isFutureMonth(Integer
												.parseInt(transferTO
														.getMonthPass()) - 1)) {
									if (errors
											.get(CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE) != null
											&& !errors
													.get(
															CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE)
													.hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE);
										errors
												.add(
														CMSConstants.ADMISSIONFORM_LATERAL_MONTH_FUTURE,
														error);
									}
								}
							}
						}
					}
				}
			}
		}

		log.info("exit validateTransferMarks...");
	}

	/**
	 * FORWARDS STUDENT DETAILS EDIT PAGE
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardStudentEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter forwardStudentEditPage...");
		log.info("exit forwardStudentEditPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}

	/**
	 * INIT STUDENT BIODATA ENTRY
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentCreation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initStudentCreation...");
		StudentEditForm stForm = (StudentEditForm) form;
		stForm.setProgramId(null);
		stForm.setCourseId(null);
		stForm.setProgramTypeId(null);
		stForm.setApplicantDetails(null);
		stForm.setDetailMark(null);
		stForm.setRollNo(null);
		stForm.setRegNo(null);
		stForm.setClassSchemeId(0);
		stForm.setDisplayMotherTongue(false);

		stForm.setDisplayLanguageKnown(false);

		stForm.setDisplayHeightWeight(false);

		stForm.setDisplayTrainingDetails(false);

		stForm.setDisplayAdditionalInfo(false);

		stForm.setDisplayExtracurricular(false);

		stForm.setDisplaySecondLanguage(false);

		stForm.setDisplayFamilyBackground(false);

		stForm.setDisplayTCDetails(false);

		stForm.setDisplayEntranceDetails(false);

		stForm.setDisplayLateralDetails(false);

		stForm.setDisplayTransferCourse(false);
		stForm.setSameTempAddr(false);
		log.info("exit initStudentCreation...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_INIT_PAGE);
	}

	/**
	 * INITIALIZES STUDENT DETAILS SELECTION DATA
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentCreationDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initStudentCreationDetail...");
		StudentEditForm stForm = (StudentEditForm) form;
		ActionMessages errors = stForm.validate(mapping, request);

		try {
			validateProgramCourse(errors, stForm);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_INIT_PAGE);
			}
			setUserId(request, stForm);
			StudentEditHandler handler = StudentEditHandler.getInstance();
			AdmApplnTO applicantDetails = handler.getNewStudent(stForm);
			if (applicantDetails != null) {
				// get states list for edn qualification
				List<StateTO> ednstates = StateHandler.getInstance()
						.getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
				stForm.setEdnStates(ednstates);

				stForm.setApplicantDetails(applicantDetails);
				String workExpNeeded = applicantDetails.getCourse()
						.getIsWorkExperienceRequired();
				if (stForm.getApplicantDetails().getCourse() != null
						&& "Yes".equalsIgnoreCase(workExpNeeded)) {
					stForm.setWorkExpNeeded(true);
				} else {
					stForm.setWorkExpNeeded(false);
				}

				CourseTO applicantCourse = applicantDetails.getCourse();
				ProgramTO tempMainProgramTO = applicantCourse.getProgramTo();
				if (tempMainProgramTO != null) {
					applicantCourse.setProgramId(tempMainProgramTO.getId());
					if (tempMainProgramTO.getProgramTypeTo() != null) {
						applicantCourse.setProgramTypeId(tempMainProgramTO
								.getProgramTypeTo().getProgramTypeId());
					}
				}
				CourseTO selectedCourse = applicantDetails.getSelectedCourse();
				ProgramTO tempProgramTO = selectedCourse.getProgramTo();
				if (tempProgramTO != null) {
					selectedCourse.setProgramId(tempProgramTO.getId());
					if (tempProgramTO.getProgramTypeTo() != null) {
						selectedCourse.setProgramTypeId(tempProgramTO
								.getProgramTypeTo().getProgramTypeId());
					}
				}
				applicantDetails.setSelectedCourse(selectedCourse);
				applicantDetails.setCourse(applicantCourse);
				List<EntrancedetailsTO> entrnaceList = EntranceDetailsHandler
						.getInstance().getEntranceDeatilsByProgram(
								selectedCourse.getProgramId());
				stForm.setEntranceList(entrnaceList);
				setSelectedCourseAsPreference(stForm);
				Calendar cal = Calendar.getInstance();
				/*
				 * cal.setTime(new Date()); int year=cal.get(cal.YEAR);
				 */
				int year = Integer.parseInt(stForm.getAcademicYear());
				applicantDetails.setCreatedBy(stForm.getUserId());
				applicantDetails.setCreatedDate(cal.getTime());

//				Map<Integer, String> classesMap = handler
//						.getClassSchemeForStudent(selectedCourse.getId(), year);
//				stForm.setClassesMap(classesMap);
				//Code Has been changed by Balaji
				Map<Integer, String> classesMap =StudentEditHandler.getInstance().getClassesBySelectedCourse(selectedCourse.getId(), year);
				stForm.setClassesMap(classesMap);
				
				Map<Integer, String> rejoinClassesMap = StudentEditHandler.getInstance().getClassesBySelectedCourse(selectedCourse.getId(), Integer.parseInt(CommonUtil.getCurrentYear()));
				stForm.setRejoinClassMap(rejoinClassesMap);
				
				ProgramTO programTO = selectedCourse.getProgramTo();
				if (programTO != null) {
					if (programTO.getIsMotherTongue() == true)
						stForm.setDisplayMotherTongue(true);
					if (programTO.getIsDisplayLanguageKnown() == true)
						stForm.setDisplayLanguageKnown(true);
					if (programTO.getIsHeightWeight() == true)
						stForm.setDisplayHeightWeight(true);
					if (programTO.getIsDisplayTrainingCourse() == true)
						stForm.setDisplayTrainingDetails(true);
					if (programTO.getIsAdditionalInfo() == true)
						stForm.setDisplayAdditionalInfo(true);
					if (programTO.getIsExtraDetails() == true)
						stForm.setDisplayExtracurricular(true);
					if (programTO.getIsSecondLanguage() == true)
						stForm.setDisplaySecondLanguage(true);
					if (programTO.getIsFamilyBackground() == true)
						stForm.setDisplayFamilyBackground(true);
					if (programTO.getIsTCDetails() == true)
						stForm.setDisplayTCDetails(true);
					if (programTO.getIsEntranceDetails() == true)
						stForm.setDisplayEntranceDetails(true);
					if (programTO.getIsLateralDetails() == true)
						stForm.setDisplayLateralDetails(true);
					if (programTO.getIsTransferCourse() == true)
						stForm.setDisplayTransferCourse(true);
				}

				checkExtradetailsDisplay(stForm);
				checkLateralTransferDisplay(stForm);

				if (CountryHandler.getInstance().getCountries() != null) {
					// birthCountry & states
					List<CountryTO> birthCountries = CountryHandler
							.getInstance().getCountries();
					if (!birthCountries.isEmpty()) {
						stForm.setCountries(birthCountries);

					}

				}

				OrganizationHandler orgHandler = OrganizationHandler
						.getInstance();
				List<OrganizationTO> tos = orgHandler.getOrganizationDetails();
				if (tos != null && !tos.isEmpty()) {
					OrganizationTO orgTO = tos.get(0);
					if (orgTO.getExtracurriculars() != null)
						applicantDetails.getPersonalData()
								.setStudentExtracurricularsTos(
										orgTO.getExtracurriculars());
				}

				// Nationality
				AdmissionFormHandler formhandler = AdmissionFormHandler
						.getInstance();
				stForm.setNationalities(formhandler.getNationalities());
				// languages
				LanguageHandler langHandler = LanguageHandler.getHandler();
				stForm.setMothertongues(langHandler.getLanguages());
				stForm.setLanguages(langHandler.getOriginalLanguages());

				if (stForm.isDisplayAdditionalInfo()) {
					List<OrganizationTO> orgtos = orgHandler
							.getOrganizationDetails();
					if (orgtos != null && !orgtos.isEmpty()) {
						OrganizationTO orgTO = orgtos.get(0);
						stForm.setOrganizationName(orgTO.getOrganizationName());
						stForm.setNeedApproval(orgTO.isNeedApproval());
					}
				}

				// res. catg
				stForm.setResidentTypes(formhandler.getResidentTypes());

				ReligionHandler religionhandler = ReligionHandler.getInstance();
				if (religionhandler.getReligion() != null) {
					List<ReligionTO> religionList = religionhandler
							.getReligion();
					stForm.setReligions(religionList);
				}
				// caste category
				List<CasteTO> castelist = CasteHandler.getInstance()
						.getCastes();
				stForm.setCasteList(castelist);

				// Admitted Through
				List<AdmittedThroughTO> admittedList = AdmittedThroughHandler
						.getInstance().getAdmittedThrough();
				stForm.setAdmittedThroughList(admittedList);
				// subject Group
				List<SubjectGroupTO> sujectgroupList=StudentEditHandler.getInstance().getSubjectGroupList(selectedCourse.getId(), year);
				stForm.setSubGroupList(sujectgroupList);
				
//				List<SubjectGroupTO> sujectgroupList = SubjectGroupHandler
//						.getInstance().getSubjectGroupDetails(
//								selectedCourse.getId());
				stForm.setSubGroupList(sujectgroupList);
				String[] subjectgroups = applicantDetails.getSubjectGroupIds();
				if (subjectgroups != null && subjectgroups.length == 0
						&& sujectgroupList != null) {
					subjectgroups = new String[sujectgroupList.size()];
					applicantDetails.setSubjectGroupIds(subjectgroups);
				}

				// incomes
				List<IncomeTO> incomeList = AdmissionFormHandler.getInstance()
						.getIncomes();
				stForm.setIncomeList(incomeList);

				// currencies
				List<CurrencyTO> currencyList = AdmissionFormHandler
						.getInstance().getCurrencies();
				stForm.setCurrencyList(currencyList);

				UniversityHandler unhandler = UniversityHandler.getInstance();
				List<UniversityTO> universities = unhandler.getUniversity();
				stForm.setUniversities(universities);

				OccupationTransactionHandler occhandler = OccupationTransactionHandler
						.getInstance();
				stForm.setOccupations(occhandler.getAllOccupation());

				// set photo to session
				if (applicantDetails.getEditDocuments() != null) {
					Iterator<ApplnDocTO> docItr = applicantDetails
							.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if (docTO.getSubmitDate() != null
								&& !StringUtils.isEmpty(docTO.getSubmitDate())) {
							stForm.setSubmitDate(docTO.getSubmitDate());
						}
					}
				}

				// preferences

				List<CandidatePreferenceTO> prefTos = new ArrayList<CandidatePreferenceTO>();
				CandidatePreferenceTO firstTo = new CandidatePreferenceTO();
				firstTo.setCoursId(String.valueOf(selectedCourse.getId()));
				firstTo.setPrefNo(1);
				prefTos.add(firstTo);

				stForm.setPreferenceList(prefTos);

				stForm.setApplicantDetails(applicantDetails);
				ExamGenHandler genHandler = new ExamGenHandler();
				HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
				stForm.setSecondLanguageList(secondLanguage);

			} else {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						"knowledgepro.admission.NoCourseSelected");
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_INIT_PAGE);
			}
		} catch (Exception e) {
			log.error("Error in  initStudentCreationDetail...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initStudentCreationDetail...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);

	}

	/**
	 * semester mark entry init for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initSemesterMarkPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {

			String indexString = request
					.getParameter(PRCStudentEditAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index = -1;
			if (session != null) {
				if (indexString != null) {
					session
							.setAttribute(PRCStudentEditAction.COUNTID,
									indexString);
					index = Integer.parseInt(indexString);
				} else
					session.removeAttribute(PRCStudentEditAction.COUNTID);
			}
			List<EdnQualificationTO> quals = admForm.getApplicantDetails()
					.getEdnQualificationList();
			admForm.setIsLanguageWiseMarks("false");
			if (quals != null) {

				Iterator<EdnQualificationTO> qualItr = quals.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
							.next();
					if (qualTO.getCountId() == index) {
						if (qualTO.getDetailmark() != null) {
							admForm.setDetailMark(qualTO.getDetailmark());
						}
						if (qualTO.isLanguage()) {
							admForm.setIsLanguageWiseMarks("true");
						}

					}

				}
			}

			if (admForm.getDetailMark() == null)
				admForm.setDetailMark(new CandidateMarkTO());
		} catch (Exception e) {
			log.error("error in initSemesterMarkPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initSemesterMarkPage...");
		return mapping
				.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_SEMESTER_PAGE);
	}

	/**
	 * semester mark entry submit for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit semester mark page...");
		StudentEditForm admForm = (StudentEditForm) form;
		HttpSession session = request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session
						.getAttribute(PRCStudentEditAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			if (admForm.getIsLanguageWiseMarks() != null
					&& admForm.getIsLanguageWiseMarks()
							.equalsIgnoreCase("true"))
				detailmark.setSemesterMark(true);
			else
				detailmark.setSemesterMark(false);
			ActionMessages errors = validateMarks(detailmark);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_SEMESTER_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm
					.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> QualItr = qualifications
						.iterator();
				while (QualItr.hasNext()) {
					EdnQualificationTO qualificationto = QualItr.next();
					if (qualificationto != null
							&& qualificationto.getCountId() == detailMarkIndex) {
						detailmark.setLastExam(qualificationto.isLastExam());
						if (admForm.getIsLanguageWiseMarks() != null
								&& admForm.getIsLanguageWiseMarks()
										.equalsIgnoreCase("true"))
							detailmark.setSemesterMark(true);
						else
							detailmark.setSemesterMark(false);
						qualificationto.setDetailmark(detailmark);
					}
				}
			}
			admForm.setDetailMark(null);
		} catch (Exception e) {
			log.error("error in submit semester mark page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submit semester mark page...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init detail mark page...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {

			String indexString = request
					.getParameter(PRCStudentEditAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index = -1;
			if (session != null) {
				if (indexString != null) {
					index = Integer.parseInt(indexString);
					session
							.setAttribute(PRCStudentEditAction.COUNTID,
									indexString);
				} else
					session.removeAttribute(PRCStudentEditAction.COUNTID);
			}
			List<EdnQualificationTO> quals = admForm.getApplicantDetails()
					.getEdnQualificationList();
			if (quals != null) {

				Iterator<EdnQualificationTO> qualItr = quals.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr
							.next();
					if (qualTO.getCountId() == index) {
						if (qualTO.getDetailmark() != null)
							admForm.setDetailMark(qualTO.getDetailmark());
					}

				}
			}

			Map<Integer, String> subjectMap = AdmissionFormHandler
					.getInstance().getDetailedSubjectsByCourse(
							admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);

			if (admForm.getDetailMark() == null) {
				CandidateMarkTO markTo = new CandidateMarkTO();
				AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(
						subjectMap, markTo);
				admForm.setDetailMark(markTo);
			}

		} catch (Exception e) {
			log.error("error in init detail mark page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit init detail mark page...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_MARK_PAGE);
	}

	/**
	 * detail mark entry submit for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit detail mark page...");
		StudentEditForm admForm = (StudentEditForm) form;
		HttpSession session = request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session
						.getAttribute(PRCStudentEditAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = validateMarks(detailmark);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm
					.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> QualItr = qualifications
						.iterator();
				while (QualItr.hasNext()) {
					EdnQualificationTO qualificationto = QualItr.next();
					if (qualificationto != null
							&& qualificationto.getCountId() == detailMarkIndex)
						qualificationto.setDetailmark(detailmark);
				}

			}
			admForm.setDetailMark(null);
		} catch (Exception e) {
			log.error("error in submit detail mark page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submit detail mark page...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
	}

	/**
	 * FORWARDS NEW STUDENT DETAILS ENTRY PAGE
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardCreateStudentDetailPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter forwardCreateStudentDetailPage...");
		log.info("exit forwardCreateStudentDetailPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
	}

	/**
	 * transfer entry init for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTransferEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initTransferEntryPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			if (admForm.getTransferDetails() == null
					|| admForm.getTransferDetails().isEmpty()) {
				List<ApplicantTransferDetailsTO> transferDetails = new ArrayList<ApplicantTransferDetailsTO>();
				for (int i = 0; i < CMSConstants.MAX_CANDIDATE_TRANSFERS; i++) {
					ApplicantTransferDetailsTO to = new ApplicantTransferDetailsTO();
					to.setSemesterNo(i);
					transferDetails.add(to);
				}
				Collections.sort(transferDetails);
				admForm.setTransferDetails(transferDetails);
			}
		} catch (Exception e) {
			log.error("error in initTransferEntryPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initTransferEntryPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_TRANSFER_PAGE);
	}

	/**
	 * transfer entry submit for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitTransferEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitTransferEntry...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			// Validate marks
			validateTransferMarks(admForm, errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_TRANSFER_PAGE);
			}
			// process lateral details
			List<ApplicantTransferDetailsTO> transferDetails = admForm
					.getTransferDetails();
			if (transferDetails != null && !transferDetails.isEmpty()) {
				Set<ApplicantTransferDetails> transferBos = new HashSet<ApplicantTransferDetails>();
				Iterator<ApplicantTransferDetailsTO> latItr = transferDetails
						.iterator();
				while (latItr.hasNext()) {
					ApplicantTransferDetailsTO trnTO = (ApplicantTransferDetailsTO) latItr
							.next();
					if (trnTO != null) {
						ApplicantTransferDetails bo = new ApplicantTransferDetails();
						bo.setInstituteAddr(admForm.getTransInstituteAddr());
						if (trnTO.getMarksObtained() != null
								&& !StringUtils.isEmpty(trnTO
										.getMarksObtained().trim())
								&& CommonUtil.isValidDecimal(trnTO
										.getMarksObtained().trim()))
							bo.setMarksObtained(new BigDecimal(trnTO
									.getMarksObtained().trim()));
						if (trnTO.getMaxMarks() != null
								&& !StringUtils.isEmpty(trnTO.getMaxMarks()
										.trim())
								&& CommonUtil.isValidDecimal(trnTO
										.getMaxMarks().trim()))
							bo.setMaxMarks(new BigDecimal(trnTO.getMaxMarks()
									.trim()));
						if (trnTO.getMinMarks() != null
								&& !StringUtils.isEmpty(trnTO.getMinMarks()
										.trim())
								&& CommonUtil.isValidDecimal(trnTO
										.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(trnTO.getMinMarks()
									.trim()));
						if (trnTO.getMonthPass() != null
								&& !StringUtils.isEmpty(trnTO.getMonthPass()
										.trim())
								&& StringUtils.isNumeric(trnTO.getMonthPass()
										.trim()))
							bo.setMonthPass(Integer.parseInt(trnTO
									.getMonthPass().trim()));

						if (trnTO.getYearPass() != null
								&& !StringUtils.isEmpty(trnTO.getYearPass()
										.trim())
								&& StringUtils.isNumeric(trnTO.getYearPass()
										.trim()))
							bo.setYearPass(Integer.parseInt(trnTO.getYearPass()
									.trim()));
						bo.setSemesterName(trnTO.getSemesterName());
						bo.setSemesterNo(trnTO.getSemesterNo());
						bo.setArrearDetail(admForm.getTransArrearDetail());
						bo.setUniversityAppNo(admForm.getTransUnvrAppNo());
						bo.setRegistationNo(admForm.getTransRegistationNo());
						transferBos.add(bo);
					}

				}

				if (transferBos != null && !transferBos.isEmpty()) {
					HttpSession session = request.getSession(false);
					if (session == null) {
						return mapping
								.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
					}
					session.setAttribute(CMSConstants.STUDENT_TRANSFERDETAILS,
							transferBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitLateralEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitTransferEntry...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
	}

	/**
	 * latelar entry init for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initlateralEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initlateralEntryPage...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			if (admForm.getLateralDetails() == null
					|| admForm.getLateralDetails().isEmpty()) {
				List<ApplicantLateralDetailsTO> lateralDetails = new ArrayList<ApplicantLateralDetailsTO>();
				for (int i = 0; i < CMSConstants.MAX_CANDIDATE_LATERALS; i++) {
					ApplicantLateralDetailsTO to = new ApplicantLateralDetailsTO();
					to.setSemesterNo(i);
					lateralDetails.add(to);
				}
				Collections.sort(lateralDetails);
				admForm.setLateralDetails(lateralDetails);
			}
		} catch (Exception e) {
			log.error("error in initlateralEntryPage...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit initlateralEntryPage...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_LATERAL_PAGE);
	}

	/**
	 * latelar entry submit for student
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitLateralEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitLateralEntry...");
		StudentEditForm admForm = (StudentEditForm) form;
		try {
			 ActionErrors errors = admForm.validate(mapping, request);
			// Validate marks
			validateLateralMarks(admForm, errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_LATERAL_PAGE);
			}
			// process lateral details
			List<ApplicantLateralDetailsTO> lateralDetails = admForm
					.getLateralDetails();
			if (lateralDetails != null && !lateralDetails.isEmpty()) {
				Set<ApplicantLateralDetails> lateralBos = new HashSet<ApplicantLateralDetails>();
				Iterator<ApplicantLateralDetailsTO> latItr = lateralDetails
						.iterator();
				while (latItr.hasNext()) {
					ApplicantLateralDetailsTO lateralTO = (ApplicantLateralDetailsTO) latItr
							.next();
					if (lateralTO != null) {
						ApplicantLateralDetails bo = new ApplicantLateralDetails();
						bo.setInstituteAddress(admForm
								.getLateralInstituteAddress());
						if (lateralTO.getMarksObtained() != null
								&& !StringUtils.isEmpty(lateralTO
										.getMarksObtained().trim())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMarksObtained().trim()))
							bo.setMarksObtained(new BigDecimal(lateralTO
									.getMarksObtained().trim()));
						if (lateralTO.getMaxMarks() != null
								&& !StringUtils.isEmpty(lateralTO.getMaxMarks()
										.trim())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMaxMarks().trim()))
							bo.setMaxMarks(new BigDecimal(lateralTO
									.getMaxMarks().trim()));
						if (lateralTO.getMinMarks() != null
								&& !StringUtils.isEmpty(lateralTO.getMinMarks()
										.trim())
								&& CommonUtil.isValidDecimal(lateralTO
										.getMinMarks().trim()))
							bo.setMinMarks(new BigDecimal(lateralTO
									.getMinMarks().trim()));
						if (lateralTO.getMonthPass() != null
								&& !StringUtils.isEmpty(lateralTO
										.getMonthPass().trim())
								&& StringUtils.isNumeric(lateralTO
										.getMonthPass().trim()))
							bo.setMonthPass(Integer.parseInt(lateralTO
									.getMonthPass().trim()));

						if (lateralTO.getYearPass() != null
								&& !StringUtils.isEmpty(lateralTO.getYearPass()
										.trim())
								&& StringUtils.isNumeric(lateralTO
										.getYearPass().trim()))
							bo.setYearPass(Integer.parseInt(lateralTO
									.getYearPass().trim()));
						bo.setSemesterName(lateralTO.getSemesterName());
						bo.setSemesterNo(lateralTO.getSemesterNo());
						bo.setStateName(admForm.getLateralStateName());
						bo
								.setUniversityName(admForm
										.getLateralUniversityName());
						lateralBos.add(bo);
					}

				}

				if (lateralBos != null && !lateralBos.isEmpty()) {
					HttpSession session = request.getSession(false);
					if (session == null) {
						return mapping
								.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
					}
					session.setAttribute(CMSConstants.STUDENT_LATERALDETAILS,
							lateralBos);
				}
			}
		} catch (Exception e) {
			log.error("error in submitLateralEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitLateralEntry...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
	}

	/**
	 * creates a student record
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitStudentCreation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitStudentCreation...");
		StudentEditForm stForm = (StudentEditForm) form;

		try {
			ActionMessages errors = stForm.validate(mapping, request);
			if (stForm.getApplicantDetails().getApplnNo() == 0) {

				if (errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED,
									new ActionError(
											CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED));
				}
			}

			if (stForm.isSameTempAddr()) {
				copyCurrToPermAddress(stForm);
			}
			validateEditPhone1(stForm, errors);
			validateEditParentPhone(stForm, errors);
			validateEditGuardianPhone(stForm, errors);
			validateEditPassportIfNRI(stForm, errors);
			validateEditOtherOptions(stForm, errors);

			validateEditCommAddress(stForm, errors);
			validatePermAddress(stForm, errors);
			validateSubjectGroups(stForm, errors);
			if (stForm.isDisplayTCDetails())
				validateTcDetailsEdit(stForm, errors);
			if (stForm.isDisplayEntranceDetails())
				validateEntanceDetailsEdit(stForm, errors);
			if (stForm.getApplicantDetails().getChallanDate() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getChallanDate())) {
				if (CommonUtil.isValidDate(stForm.getApplicantDetails()
						.getChallanDate())) {
					boolean isValid = validatefutureDate(stForm
							.getApplicantDetails().getChallanDate());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
						}
					}
				} else {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// validate Admission Date
			if (stForm.getApplicantDetails().getAdmissionDate() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getAdmissionDate())) {
				if (CommonUtil.isValidDate(stForm.getApplicantDetails()
						.getAdmissionDate())) {
					boolean isValid = validatefutureDate(stForm
							.getApplicantDetails().getAdmissionDate());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
						}
					}
				} else {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
					}
				}
			}

			if (stForm.getApplicantDetails().getPersonalData() != null
					&& stForm.getApplicantDetails().getPersonalData().getDob() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getPersonalData().getDob())) {
				if (CommonUtil.isValidDate(stForm.getApplicantDetails()
						.getPersonalData().getDob())) {
					boolean isValid = validatefutureDate(stForm
							.getApplicantDetails().getPersonalData().getDob());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null
								&& !errors.get(
										CMSConstants.ADMISSIONFORM_DOB_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_DOB_LARGE,
											new ActionError(
													CMSConstants.ADMISSIONFORM_DOB_LARGE));
						}
					}
				} else {
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID) != null
							&& !errors.get(
									CMSConstants.ADMISSIONFORM_DOB_INVAID)
									.hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID,
								new ActionError(
										CMSConstants.ADMISSIONFORM_DOB_INVAID));
					}
				}
			}
			if (stForm.getApplicantDetails().getPersonalData()
					.getPassportValidity() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getPersonalData().getPassportValidity())) {

				if (CommonUtil.isValidDate(stForm.getApplicantDetails()
						.getPersonalData().getPassportValidity())) {
					boolean isValid = validatePastDate(stForm
							.getApplicantDetails().getPersonalData()
							.getPassportValidity());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_PASSPORT_INVALID)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,
											new ActionError(
													CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
				} else {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
					}
				}
			}
			if (stForm.getApplicantDetails().getPersonalData()
					.getResidentPermitDate() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getPersonalData().getResidentPermitDate())
					&& !CommonUtil.isValidDate(stForm.getApplicantDetails()
							.getPersonalData().getResidentPermitDate())) {

				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_PERMITDT_INVAID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}

			/*if (stForm.getSubmitDate() != null
					&& !StringUtils.isEmpty(stForm.getSubmitDate())) {
				if (CommonUtil.isValidDate(stForm.getSubmitDate())) {
					boolean isValid = validatePastDate(stForm.getSubmitDate());
					if (!isValid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)
										.hasNext()) {
							errors
									.add(
											CMSConstants.ADMISSIONFORM_SUBMITDT_PAST,
											new ActionError(
													CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
						}
					}
				} else {
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)
									.hasNext()) {
						errors
								.add(
										CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID,
										new ActionError(
												CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}*/

			List<ApplicantWorkExperienceTO> expList = stForm
					.getApplicantDetails().getWorkExpList();
			if (expList != null) {
				Iterator<ApplicantWorkExperienceTO> toItr = expList.iterator();
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr
							.next();
					validateWorkExperience(expTO, errors);
				}
			}
//			validateEducationDetails(errors, stForm);
			validateEditDocumentSize(stForm, errors);
			if (stForm.getApplicantDetails().getPersonalData()
					.getTrainingDuration() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getPersonalData().getTrainingDuration())
					&& !StringUtils.isNumeric(stForm.getApplicantDetails()
							.getPersonalData().getTrainingDuration())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_DURATION_INVALID)
								.hasNext()) {
					errors
							.add(
									CMSConstants.ADMISSIONFORM_DURATION_INVALID,
									new ActionError(
											CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}

			// validate height and weight
			if (stForm.getApplicantDetails().getPersonalData().getHeight() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getPersonalData().getHeight())
					&& !StringUtils.isNumeric(stForm.getApplicantDetails()
							.getPersonalData().getHeight())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_HEIGHT_INVALID)
								.hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,
							new ActionError(
									CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}

			if (stForm.getApplicantDetails().getPersonalData().getWeight() != null
					&& !StringUtils.isEmpty(stForm.getApplicantDetails()
							.getPersonalData().getWeight())
					&& !CommonUtil.isValidDecimal(stForm.getApplicantDetails()
							.getPersonalData().getWeight())) {
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null
						&& !errors.get(
								CMSConstants.ADMISSIONFORM_WEIGHT_INVALID)
								.hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,
							new ActionError(
									CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);

			}
			// setUserId(request,stForm);

			AdmApplnTO applicantDetail = stForm.getApplicantDetails();
			/*
			 * Calendar cal= Calendar.getInstance(); cal.setTime(new Date());
			 * int year=cal.get(cal.YEAR);
			 */
			// set current year
			// if(applicantDetail!=null)
			// applicantDetail.setAppliedYear(year);
			applicantDetail.setAppliedYear(Integer.parseInt(stForm
					.getAcademicYear()));
			AdmissionFormHandler admHandler = AdmissionFormHandler
					.getInstance();
			StudentEditHandler stHandler = StudentEditHandler.getInstance();

			// check regNo Duplicate or not
			if (stForm.getRegNo() != null
					&& !StringUtils.isEmpty(stForm.getRegNo().trim())) {
				boolean duplicateRegNo = stHandler.checkRegNoUnique(stForm
						.getRegNo(), applicantDetail.getAppliedYear());
				if (!duplicateRegNo) {
					resetHardCopySubmit(stForm.getApplicantDetails());
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADDSTUDENT_REGNO_NOTUNIQUE);
					message.add(CMSConstants.ADDSTUDENT_REGNO_NOTUNIQUE, error);
					saveErrors(request, message);
					// stForm.setApplicationError(true);
					return mapping
							.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
				}

			}
			// check applns. configured or not
/*			boolean appNoConfigured = admHandler.checkApplicationNoConfigured(
					applicantDetail.getAppliedYear(), String
							.valueOf(applicantDetail.getSelectedCourse()
									.getId()));
			if (!appNoConfigured) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				ActionMessages message = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED);
				message.add(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED,
						error);
				saveErrors(request, message);
				// stForm.setApplicationError(true);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
			}
			// offline range check
			boolean applnNoInrange = admHandler.checkApplicationNoInRange(
					String.valueOf(applicantDetail.getApplnNo()), false,
					applicantDetail.getAppliedYear(), String
							.valueOf(applicantDetail.getSelectedCourse()
									.getId()));
			if (!applnNoInrange) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				ActionMessages message = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE);
				message.add(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE,
						error);
				saveErrors(request, message);
				// admForm.setApplicationError(true);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
			} */
			// unique check
			boolean uniqueNo = stHandler.checkApplicationNoUniqueForYear(
					applicantDetail.getApplnNo(), applicantDetail
							.getAppliedYear());
			if (!uniqueNo) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				ActionMessages message = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE);
				message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE,
						error);
				saveErrors(request, message);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
			}
			// check for seat allocation exceeded for admitted through or not
			boolean exceeded = false;

			if (!stForm.isQuotaCheck()) {
				if (applicantDetail.getAdmittedThroughId() != null
						&& !StringUtils.isEmpty(applicantDetail
								.getAdmittedThroughId())
						&& StringUtils.isNumeric(applicantDetail
								.getAdmittedThroughId())
						&& applicantDetail.getSelectedCourse() != null
						&& applicantDetail.getSelectedCourse().getId() != 0) {
					exceeded = admHandler.checkSeatAllocationExceeded(Integer
							.parseInt(applicantDetail.getAdmittedThroughId()),
							applicantDetail.getSelectedCourse().getId());
				}
			}

			if (exceeded) {
				stForm.setQuotaCheck(true);
				resetHardCopySubmit(applicantDetail);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EDIT_WARN);
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
			} else {
				if (!stForm.isEligibleCheck()) {
					boolean eligible = admHandler
							.checkEligibility(applicantDetail);

					if (!eligible) {
						resetHardCopySubmit(stForm.getApplicantDetails());
						stForm.setQuotaCheck(true);
						stForm.setEligibleCheck(true);
						ActionMessages messages = new ActionMessages();
						ActionMessage message = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ELIGIBILITY_WARN);
						messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
						saveErrors(request, messages);
						return mapping
								.findForward(CMSConstants.PRC_STUDENTCREATE_DETAIL_PAGE);
					}

				}
			}
			boolean updated = stHandler.createStudent(applicantDetail, stForm,true);

			if (updated) {
				stForm.setQuotaCheck(false);
				stForm.setEligibleCheck(false);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_STUDENT_CREATED);
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log.error("Error in  submitStudentCreation...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitStudentCreation...");
		return mapping.findForward(CMSConstants.PRC_STUDENTCREATE_CONFIRM_PAGE);

	}

	/**
	 * permanent address validation
	 * 
	 * @param stForm
	 * @param errors
	 */
	private void validatePermAddress(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validatePermAddress..");
		if (errors == null)
			errors = new ActionMessages();

		if (admForm.getApplicantDetails().getPersonalData()
				.getPermanentAddressLine1() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPermanentAddressLine1())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED);
			errors
					.add(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED,
							error);
		}
		if (admForm.getApplicantDetails().getPersonalData()
				.getPermanentCityName() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPermanentCityName())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED, error);
		}

		if (admForm.getApplicantDetails().getPersonalData()
				.getPermanentCountryId() == 0) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED, error);
		}
		if (admForm.getApplicantDetails().getPersonalData()
				.getPermanentAddressZipCode() == null
				|| StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPermanentAddressZipCode())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED, error);
		} else if (!StringUtils.isNumeric(admForm.getApplicantDetails()
				.getPersonalData().getPermanentAddressZipCode())) {
			ActionMessage error = new ActionMessage(
					CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID, error);
		}
		if ((admForm.getApplicantDetails().getPersonalData()
				.getPermanentStateId() == null || StringUtils.isEmpty(admForm
				.getApplicantDetails().getPersonalData().getPermanentStateId()))
				&& (admForm.getApplicantDetails().getPersonalData()
						.getPermanentAddressStateOthers() == null || StringUtils
						.isEmpty(admForm.getApplicantDetails()
								.getPersonalData()
								.getPermanentAddressStateOthers()))) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors
						.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED,
								error);
			}
		}

		log.info("exit validatePermAddress..");
	}

	/**
	 * COPIES CURENT ADDRESS TO PERMANENT ADDRESS
	 * 
	 * @param stForm
	 */
	private void copyCurrToPermAddress(StudentEditForm stForm) {
		stForm.getApplicantDetails().getPersonalData()
				.setPermanentAddressLine1(
						stForm.getApplicantDetails().getPersonalData()
								.getCurrentAddressLine1());
		stForm.getApplicantDetails().getPersonalData()
				.setPermanentAddressLine2(
						stForm.getApplicantDetails().getPersonalData()
								.getCurrentAddressLine2());
		stForm.getApplicantDetails().getPersonalData().setPermanentCityName(
				stForm.getApplicantDetails().getPersonalData()
						.getCurrentCityName());
		stForm.getApplicantDetails().getPersonalData().setPermanentCountryId(
				stForm.getApplicantDetails().getPersonalData()
						.getCurrentCountryId());
		if (stForm.getApplicantDetails().getPersonalData().getCurrentStateId() != null
				&& !StringUtils.isEmpty(stForm.getApplicantDetails()
						.getPersonalData().getCurrentStateId().trim()))
			stForm.getApplicantDetails().getPersonalData().setPermanentStateId(
					stForm.getApplicantDetails().getPersonalData()
							.getCurrentStateId());
		if (stForm.getApplicantDetails().getPersonalData()
				.getCurrentAddressStateOthers() != null
				&& !StringUtils.isEmpty(stForm.getApplicantDetails()
						.getPersonalData().getCurrentAddressStateOthers()))
			stForm.getApplicantDetails().getPersonalData()
					.setPermanentAddressStateOthers(
							stForm.getApplicantDetails().getPersonalData()
									.getCurrentAddressStateOthers());
		stForm.getApplicantDetails().getPersonalData()
				.setPermanentAddressZipCode(
						stForm.getApplicantDetails().getPersonalData()
								.getCurrentAddressZipCode());

	}

	/**
	 * sets selected course as default preference
	 * 
	 * @param stForm
	 */
	private void setSelectedCourseAsPreference(StudentEditForm stForm) {
		PreferenceTO to = new PreferenceTO();
		if (stForm.getApplicantDetails() != null
				&& stForm.getApplicantDetails().getSelectedCourse() != null) {

			to.setFirstPrefCourseId(String.valueOf(stForm.getApplicantDetails()
					.getSelectedCourse().getId()));
		}
		stForm.getApplicantDetails().setPreference(to);

	}

	/**
	 * validate subject groups
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateSubjectGroups(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateSubjectGroups...");
		if (errors == null)
			errors = new ActionMessages();

		if ((admForm.getApplicantDetails().getSubjectGroupIds() == null || admForm
				.getApplicantDetails().getSubjectGroupIds().length == 0)) {
			if (errors.get(CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_SUBJECTGROUP_REQUIRED,
						error);
			}
		}
		if ((admForm.getApplicantDetails().getAdmittedThroughId() == null || StringUtils
				.isEmpty(admForm.getApplicantDetails().getAdmittedThroughId()))) {
			if (errors.get(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED) != null
					&& !errors
							.get(
									CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED,
						error);
			}
		}
		if ((admForm.getApplicantDetails().getIsFreeShip() == null)) {
			if (errors.get(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED) != null
					&& !errors
							.get(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED, error);
			}
		}

		if ((admForm.getApplicantDetails().getIsLIG() == null)) {
			if (errors.get(CMSConstants.ADMISSIONFORM_LIG_REQUIRED) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_LIG_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_LIG_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_LIG_REQUIRED, error);
			}
		}

	}

	/**
	 * validate programtype,course and program
	 * 
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEducationDetails(ActionMessages errors,
			StudentEditForm admForm) {
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications = admForm.getApplicantDetails()
				.getEdnQualificationList();
		if (qualifications != null) {
			Iterator<EdnQualificationTO> qualificationTO = qualifications
					.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if ((qualfTO.getUniversityId() == null
						|| (qualfTO.getUniversityId().length() == 0) || qualfTO
						.getUniversityId().equalsIgnoreCase("Other"))
						&& (qualfTO.getUniversityOthers() == null || (qualfTO
								.getUniversityOthers().trim().length() == 0))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,
								error);
					}
				}
				if ((qualfTO.getInstitutionId() == null
						|| (qualfTO.getInstitutionId().length() == 0) || (qualfTO
						.getInstitutionId().equalsIgnoreCase("Other")))
						&& (qualfTO.getOtherInstitute() == null || (qualfTO
								.getOtherInstitute().trim().length() == 0))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,
								error);
					}
				}
				if (qualfTO.getYearPassing() == 0) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED,
								error);
					}
				} else {
					boolean valid = CommonUtil.isFutureNotCurrentYear(qualfTO
							.getYearPassing());
					if (valid) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE);
							errors.add(
									CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE,
									error);
						}
					}
					// if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
					// boolean
					// futurethanBirth=isPassYearGreaterThanBirthYear(qualfTO.getYearPassing(),admForm.getApplicantDetails().getPersonalData().getDob());
					// if(!futurethanBirth){
					// if
					// (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)!=null
					// &&
					// !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE).hasNext())
					// {
					// ActionMessage error = new
					// ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
					// errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,error);
					// }
					// }
					// }
					// // else{
					// // if
					// (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null
					// &&
					// !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext())
					// {
					// // errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new
					// ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
					// // }
					// // }
				}
				if (qualfTO.getMonthPassing() == 0) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED,
								error);
					}
				}
				if (qualfTO.isLastExam()
						&& (qualfTO.getPreviousRegNo() == null || StringUtils
								.isEmpty(qualfTO.getPreviousRegNo().trim()))) {
					if (errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED) != null
							&& !errors.get(
									CMSConstants.ADMISSIONFORM_REGNO_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_REGNO_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED,
								error);
					}
				}
				if (qualfTO.getNoOfAttempts() == 0) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,
								error);
					}
				}
				if (/* qualfTO.isExamRequired() */qualfTO.isExamConfigured()
						&& (qualfTO.getSelectedExamId() == null || StringUtils
								.isEmpty(qualfTO.getSelectedExamId()))) {
					if (errors
							.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED) != null
							&& !errors
									.get(
											CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED);
						errors.add(
								CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,
								error);
					}
				}
				if (qualfTO.isConsolidated()) {
					if (qualfTO.getTotalMarks() == null
							|| StringUtils.isEmpty(qualfTO.getTotalMarks())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_TOTALMARK_REQUIRED,
											error);
						}
					} else if (!CommonUtil.isValidDecimal(qualfTO
							.getTotalMarks())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,
											error);
						}
					}
					if (qualfTO.getMarksObtained() == null
							|| StringUtils.isEmpty(qualfTO.getMarksObtained())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED,
											error);
						}
					} else if (!CommonUtil.isValidDecimal(qualfTO
							.getMarksObtained())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,
											error);
						}
					}
					if (qualfTO.getMarksObtained() != null
							&& !StringUtils.isEmpty(qualfTO.getMarksObtained())
							&& CommonUtil.isValidDecimal(qualfTO
									.getMarksObtained())
							&& qualfTO.getTotalMarks() != null
							&& !StringUtils.isEmpty(qualfTO.getTotalMarks())
							&& CommonUtil.isValidDecimal(qualfTO
									.getTotalMarks())
							&& Double.parseDouble(qualfTO.getTotalMarks()) < Double
									.parseDouble(qualfTO.getMarksObtained())) {
						if (errors
								.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE) != null
								&& !errors
										.get(
												CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors
									.add(
											CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,
											error);
						}
					}

				} else {
					if (qualfTO.getDetailmark() != null
							&& !qualfTO.getDetailmark().isPopulated()) {
						if (!qualfTO.isSemesterWise()) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,
												error);
							}
						} else if (qualfTO.isSemesterWise()) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,
												error);
							}
						}
					}
				}

				if (qualfTO.getYearPassing() != 0
						&& qualfTO.getMonthPassing() != 0) {
					if (CommonUtil.isValidDate(admForm.getApplicantDetails()
							.getPersonalData().getDob())) {
						boolean futurethanBirth = isPassYearGreaterThanBirth(
								qualfTO.getYearPassing(), qualfTO
										.getMonthPassing(), admForm
										.getApplicantDetails()
										.getPersonalData().getDob());
						if (!futurethanBirth) {
							if (errors
									.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE) != null
									&& !errors
											.get(
													CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
								errors
										.add(
												CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,
												error);
							}
						}
					}
				}
			}
			log.info("exit validate education...");
		}

		return errors;
	}

	/**
	 * compares birth date with pass year,month
	 * 
	 * @param yearPassing
	 * @param monthPassing
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isPassYearGreaterThanBirth(int yearPassing,
			int monthPassing, String dateOfBirth) {
		boolean result = false;
		if (yearPassing != 0 && monthPassing != 0 && dateOfBirth != null
				&& !StringUtils.isEmpty(dateOfBirth)) {
			String formattedString = CommonUtil.ConvertStringToDateFormat(
					dateOfBirth, PRCStudentEditAction.FROM_DATEFORMAT,
					PRCStudentEditAction.TO_DATEFORMAT);
			Date birthdate = new Date(formattedString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(birthdate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Calendar cal2 = Calendar.getInstance();
			// cal2.setTime(birthdate);
			cal2.set(Calendar.DATE, 1);
			cal2.set(Calendar.MONTH, monthPassing);
			cal2.set(Calendar.YEAR, yearPassing);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			// if pass year larger than birth year
			// if(yearPassing== cal.get(Calendar.YEAR)|| yearPassing>
			// cal.get(Calendar.YEAR))
			if (cal2.getTime().after(cal.getTime()))
				result = true;
		}
		return result;
	}

	/**
	 * initializes Remark Entry Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRemarkEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentEditForm stForm = (StudentEditForm) form;
		try {
			// initialize remark list
			stForm.setStudentRemarksList(new ArrayList<StudentRemarksTO>());
			// load remark type list
			RemarkTypeHandler remHandler = RemarkTypeHandler.getInstance();
			stForm.setRemarkTypeList(remHandler.getRemarks());
			Set<StudentRemarks> remarkBOs = null;
			// get all remarks
			if (stForm.getOriginalStudent() != null)
				remarkBOs = stForm.getOriginalStudent().getStudentRemarkses();
			StudentEditHelper stHelper = StudentEditHelper.getInstance();
			List<StudentRemarksTO> remarkTos = stHelper
					.convertRemarkBoToTO(remarkBOs);
			stForm.setStudentRemarksList(remarkTos);
			return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_REMARK_PAGE);

		} catch (Exception e) {

			log.error("error in initRemarkEntry page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

	}

	/**
	 * adds Remark Entries
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStudentRemark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentEditForm stForm = (StudentEditForm) form;
		try {
			// validattion if needed
			ActionMessages errors = stForm.validate(mapping, request);
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_REMARK_PAGE);

			}
			stForm.setStudentRemarksList(new ArrayList<StudentRemarksTO>());
			StudentEditHelper stHelper = StudentEditHelper.getInstance();
			int remarkId = 0;
			if (stForm.getRemarkTypeId() != null
					&& !StringUtils.isEmpty(stForm.getRemarkTypeId())
					&& StringUtils.isNumeric(stForm.getRemarkTypeId()))
				remarkId = Integer.parseInt(stForm.getRemarkTypeId());
			Student origStud = stForm.getOriginalStudent();
			String comments = stForm.getRemarkComment();
			// to retain color after re-submit fetch proginal remark type bo
			RemarkTypeHandler remHandle = RemarkTypeHandler.getInstance();
			RemarkType origType = remHandle.getRemarksByID(remarkId);

			// save all details

			Set<StudentRemarks> remarkBOs = stHelper.prepareStudentRemark(
					origType, comments, stForm.getUserId(), origStud
							.getStudentRemarkses());
			// get all updated remark list
			origStud.setStudentRemarkses(remarkBOs);
			stForm.setOriginalStudent(origStud);

			// clean before return
			stForm.setRemarkTypeId(null);
			stForm.setRemarkComment(null);
			return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);

		} catch (Exception e) {
			log.error("error in addStudentRemark page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentEditForm stForm = (StudentEditForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();

		Boolean isDeleted = false;
		if (stForm.getStudentId() != 0) {
			isDeleted = StudentEditHandler.getInstance().deleteStudentDetails(
					stForm.getStudentId());

		}
		StudentEditHandler handler = StudentEditHandler.getInstance();
		List<StudentTO> studenttoList = handler.getSearchedStudents(stForm);
		if (studenttoList == null || studenttoList.isEmpty()) {
			return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_INITEDITPAGE);

		}
		stForm.setStudentTOList(studenttoList);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage(
					"knowledgepro.admission.student.deletesuccess");
			messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
		} else {
			// failure error message.
			errors.add("error", new ActionError(
					"knowledgepro.admission.student.deletefailure"));
			saveErrors(request, errors);
		}

		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITLISTPAGE);

	}
	// /Below methods added by 9-elements

	public ActionForward viewHistory_rejoin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	/*	StudentEditForm stForm = (StudentEditForm) form;
		StudentEditHandler historyHandler = StudentEditHandler.getInstance();
		stForm.setStudentRejoinTO(historyHandler.viewHistory_StuRejoin(Integer
				.parseInt(stForm.getViewStudentId())));*/
		return mapping.findForward(CMSConstants.VIEW_HISTORY_REJOIN_PAGE);

	}

	public ActionForward viewHistory_discontinueDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*StudentEditForm stForm = (StudentEditForm) form;
		StudentEditHandler historyHandler = StudentEditHandler.getInstance();
		stForm.setDiscontinueDetailsTO(historyHandler
				.viewHistory_Discontinuation(Integer.parseInt(stForm
						.getViewStudentId())));*/
		return mapping.findForward(CMSConstants.VIEW_HISTORY_DISCONTINUE_PAGE);

	}

	public ActionForward viewHistory_detentionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
/*		StudentEditForm stForm = (StudentEditForm) form;
		StudentEditHandler historyHandler = StudentEditHandler.getInstance();
		stForm.setDetentionDetailsTO(historyHandler
				.viewHistory_StuDetention(Integer.parseInt(stForm
						.getViewStudentId())));*/
		return mapping.findForward(CMSConstants.VIEW_HISTORY_DETENTION_PAGE);

	}

	public ActionForward viewHistory_SubjectGroupDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentEditForm stForm = (StudentEditForm) form;
		StudentEditHandler historyHandler = StudentEditHandler.getInstance();
		stForm.setSubjectGroupDetailsTo(historyHandler
				.viewHistory_SubjectGroupDetails(Integer.parseInt(stForm
						.getViewStudentId())));
		return mapping
				.findForward(CMSConstants.VIEW_HISTORY_SUBJECT_GROUP_PAGE);

	}
	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhone1(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionMessages();


		if (admForm.getApplicantDetails().getPersonalData().getPhNo1() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPhNo1())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getPhNo1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getPhNo2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPhNo2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getPhNo2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getPhNo3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getPhNo3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getPhNo3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}

		if (admForm.getApplicantDetails().getPersonalData().getMobileNo2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo2())
				&& admForm.getApplicantDetails().getPersonalData()
						.getMobileNo2().trim().length() != 10) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getMobileNo2() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo2())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if (admForm.getApplicantDetails().getPersonalData().getMobileNo3() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo3())
				&& !StringUtils.isNumeric(admForm.getApplicantDetails()
						.getPersonalData().getMobileNo3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		log.info("exit validateEditPhone..");
	}
	/**
	 * validate programtype,course and program
	 * 
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEditEducationDetails(ActionMessages errors,
			StudentEditForm admForm) {
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications=admForm.getApplicantDetails().getEdnQualificationList();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if((qualfTO.getUniversityId()==null ||(qualfTO.getUniversityId().length()==0 )|| qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers()==null ||(qualfTO.getUniversityOthers().trim().length()==0 )))
				{
						if(errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,error);
						}
				}
				if((qualfTO.getInstitutionId()==null ||qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other") && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 ))))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						}
				}
				if(qualfTO.isExamConfigured()&& (qualfTO.getSelectedExamId()==null || StringUtils.isEmpty(qualfTO.getSelectedExamId())))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,error);
						}
				}
				if(qualfTO.getPercentage()==null || StringUtils.isEmpty(qualfTO.getPercentage()))
				{
					errors.add(CMSConstants.ERROR,new ActionMessage("errors.required","Percentage"));
				}else{
					if(!CommonUtil.isValidDecimal(qualfTO.getPercentage())){
						errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admission.totalmarks.numeric"));
					}else{
						double d=Double.parseDouble(qualfTO.getPercentage());
						if(d>100){
							errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admission.percentage.greater"));
						}
					}
				}
				if(qualfTO.getYearPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED,error);
						}
				}else{
					boolean valid=CommonUtil.isFutureNotCurrentYear(qualfTO.getYearPassing());
					if(valid){
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE,error);
						}
					}
					
				}
				if(qualfTO.getMonthPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED,error);
						}
				}
//				if(qualfTO.isLastExam() && (qualfTO.getPreviousRegNo()==null || StringUtils.isEmpty(qualfTO.getPreviousRegNo().trim()) ))
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED).hasNext()) {
//						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED);
//						errors.add(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED,error);
//					}
//				}
//				}
				if(qualfTO.getNoOfAttempts()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
						}
				}
				if(qualfTO.isConsolidated()){
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) && !CommonUtil.isValidDecimal(qualfTO.getTotalMarks())){
						if (errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,error);
						}
					}
//					if(qualfTO.getMarksObtained()==null || StringUtils.isEmpty(qualfTO.getMarksObtained()))
//					{
//							if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED).hasNext()) {
//								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED);
//								errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED,error);
//							}
//					}
					if(qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) && !CommonUtil.isValidDecimal(qualfTO.getMarksObtained())){
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,error);
						}
					}
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) 
							&& CommonUtil.isValidDecimal(qualfTO.getMarksObtained()) &&
							qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) 
							&& CommonUtil.isValidDecimal(qualfTO.getTotalMarks())
							&& Double.parseDouble(qualfTO.getTotalMarks())< Double.parseDouble(qualfTO.getMarksObtained()))
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,error);
						}
					}
					
				}else{
					if(qualfTO.isSemesterWise()){
						if(qualfTO.getSemesterList()==null)
						{
								if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED).hasNext()) {
									ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
									errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,error);
								}
						}
					}else if(qualfTO.getDetailmark()==null)
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
							}
					}
				}
				
				if(qualfTO.getYearPassing()!=0 && qualfTO.getMonthPassing()!=0){
					if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
						boolean futurethanBirth=isPassYearGreaterThanBirth(qualfTO.getYearPassing(),qualfTO.getMonthPassing(),admForm.getApplicantDetails().getPersonalData().getDob());
						if(!futurethanBirth){
							if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
								errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,error);
							}
						}
					}
				}
				
			}
			log.info("exit validate education...");
		}
		return errors;
	}
	/**
	 * admission form validation
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCommAddress1(StudentEditForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditCommAddress..");
		if (errors == null)
			errors = new ActionMessages();
		if (admForm.getApplicantDetails().getPersonalData()
				.getCurrentAddressZipCode() != null
				&& !StringUtils.isEmpty(admForm.getApplicantDetails()
						.getPersonalData().getCurrentAddressZipCode())) {
			if (!StringUtils.isNumeric(admForm.getApplicantDetails()
					.getPersonalData().getCurrentAddressZipCode())) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID,
						error);
			}
		} 
		log.info("exit validateEditCommAddress..");
	}
	
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
	public ActionForward initStudentEditAfterSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentEditForm stForm = (StudentEditForm) form;
		try {
			
			cleanupFormFromSession(stForm);
			cleanupEditSessionData(request);
			
			stForm.setAcademicYear(stForm.getTempYear());
			stForm.setApplicationNo(stForm.getTempApplNo());
			stForm.setRegNo(stForm.getTempRegNo());
			stForm.setRollNo(stForm.getTempRollNo());
			stForm.setCourseId(stForm.getTempcourseId());
			stForm.setProgramId(stForm.getTempProgId());
			stForm.setFirstName(stForm.getTempFirstName());
			stForm.setSemister(stForm.getTempSemNo());
			stForm.setProgramTypeId(stForm.getTempProgTypeId());
			
			
			StudentEditHandler handler = StudentEditHandler.getInstance();
			List<StudentTO> studenttoList = handler.getSearchedStudents(stForm);
			if (studenttoList == null || studenttoList.isEmpty()) {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(PRCStudentEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.PRC_STUDENTEDIT_INITEDITPAGE);

			}
			stForm.setStudentTOList(studenttoList);

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
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITLISTPAGE);

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

	/**
	 * sets percentage of previous exam
	 * 
	 * @param ednQualifications
	 * @return
	 */
	private StudentQualifyExamDetailsTO prepareStudentQualifyDetail(
			List<ApplicantMarkDetailsTO> qualifications) {
		log.info("enter prepareStudentQualifyDetail");
		StudentQualifyExamDetailsTO examTo = new StudentQualifyExamDetailsTO();

		if (qualifications != null) {
			Iterator<ApplicantMarkDetailsTO> iterator = qualifications.iterator();
			double totalmark = 0.0;
			double obtainmark = 0.0;
			double overallTotal = 0.0;
			double overallObtain = 0.0;
			while (iterator.hasNext()) {
				ApplicantMarkDetailsTO marksDetails = iterator.next();
					if (marksDetails.getMarksObtained_languagewise() != null && !marksDetails.getMarksObtained_languagewise().isEmpty()) {
						obtainmark = Double.parseDouble(marksDetails.getMarksObtained_languagewise());
						overallObtain = overallObtain + obtainmark;
					}
					if (marksDetails.getMaxMarks_languagewise() != null && !marksDetails.getMaxMarks_languagewise().isEmpty()) {
						totalmark = Double.parseDouble(marksDetails
								.getMaxMarks_languagewise());
						overallTotal = overallTotal + totalmark;
					}
					if (marksDetails.getMarksObtained() != null && !marksDetails.getMarksObtained().isEmpty()) {
						obtainmark = Double.parseDouble(marksDetails.getMarksObtained());
						overallObtain = overallObtain + obtainmark;
					}
					if (marksDetails.getMaxMarks() != null && !marksDetails.getMaxMarks().isEmpty()) {
						totalmark = Double.parseDouble(marksDetails
								.getMaxMarks());
						overallTotal = overallTotal + totalmark;
					}
				
				
			}
			examTo.setObtainedMarks(overallObtain);
			examTo.setTotalMarks(overallTotal);
			double calperc = 0.0;
			if (overallTotal != 0.0) {
				calperc = (overallObtain / overallTotal) * 100;
			}
			examTo.setPercentage(calperc);

		}
		log.info("exit prepareStudentQualifyDetail");
		return examTo;
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSubjectGroupHistoryDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentEditForm stForm=(StudentEditForm)form;
		ActionErrors errors=new ActionErrors();
		// Subject Group History 
		try{
			int scheme=0;
			if(stForm.getClassSchemeId()==0){
				errors.add("error", new ActionError("errors.required","class"));
			}else{
				IStudentEditTransaction transaction=new StudentEditTransactionImpl();
				int schemeNo =transaction.getSemesterNoByClassSchemeId(stForm.getClassSchemeId());
				scheme=schemeNo;
				if(scheme>0){
					scheme=scheme-1;
				}
			}
			if(scheme==0){
				errors.add("error", new ActionError("knowledgepro.admission.history.class.first.semester"));
			}
			if(errors.isEmpty()){
			List<SubjectGroupTO> sujectgroupHistoryList=StudentEditHandler.getInstance().getSubjectGroupListBySemester(stForm.getApplicantDetails().getSelectedCourse().getId(), stForm.getApplicantDetails().getAppliedYear(),scheme);
			stForm.setSubGroupHistoryList(sujectgroupHistoryList);
			
			List<ExamStudentSubGrpHistoryBO> subHistoryList=StudentEditHelper.getSubHistoryByStudentId(stForm.getOriginalStudent().getId(),scheme,stForm);
			stForm.setStudentSubHistoryList(subHistoryList);
			
			if(subHistoryList!=null && !subHistoryList.isEmpty()){
				String[] subjectgroups1 = new String[subHistoryList.size()];
				Iterator<ExamStudentSubGrpHistoryBO> subItr2 = subHistoryList.iterator();
				for (int i = 0; subItr2.hasNext(); i++) {
					ExamStudentSubGrpHistoryBO appSubGroup=(ExamStudentSubGrpHistoryBO)subItr2.next();
					if (appSubGroup.getSubjectGroupId()>0) {
						subjectgroups1[i] = String.valueOf(appSubGroup.getSubjectGroupId());
					}
				}
				stForm.setSubjGroupHistId(subjectgroups1);
			}else{
				stForm.setSubjGroupHistId(null);
			}
			ExamStudentPreviousClassDetailsBO bo=StudentEditHandler.getInstance().getPreviousClassHistory(stForm.getOriginalStudent().getId(),scheme);
			if(bo!=null){
				stForm.setClassHistId(Integer.toString(bo.getClassId()));
				stForm.setPreviousClassId(Integer.toString(bo.getId()));
			}else{
				stForm.setClassHistId(null);
				stForm.setPreviousClassId(null);
			}
			Map<Integer, String> classesHistMap =StudentEditHandler.getInstance().getClassesMapBySelectedCourse(stForm.getApplicantDetails().getSelectedCourse().getId(),stForm.getApplicantDetails().getAppliedYear());
			stForm.setClassesHistMap(classesHistMap);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
			}
		}catch (Exception e) {
				return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
			}
		return mapping.findForward(CMSConstants.PRC_VIEW_SUBJECT_GROUP_HISTORY_DETAIL_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStudentHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentEditForm stForm=(StudentEditForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try{
//			int schemeNo =StudentEditHandler.getInstance().getSchemeNoOfStudent(stForm.getOriginalStudent().getId());
//			int scheme=schemeNo;
//			if(scheme>0){
//				scheme=scheme-1;
//			}
			IStudentEditTransaction transaction=new StudentEditTransactionImpl();
			int schemeNo =transaction.getSemesterNoByClassSchemeId(stForm.getClassSchemeId());
			int scheme=schemeNo;
			if(scheme>0){
				scheme=scheme-1;
			}
			Classes classes=null;
			if(stForm.getClassHistId()!=null && !stForm.getClassHistId().isEmpty()){
				classes=transaction.getSemesterNoByClassId(Integer.parseInt(stForm.getClassHistId()));
				if(classes!=null && classes.getTermNumber()!=scheme){
					errors.add("error", new ActionError("knowledgepro.admission.history.class"));
				}
			}
			if(errors.isEmpty()){
				boolean isUpdated=StudentEditHandler.getInstance().updateSubjectHistory(stForm,scheme,classes);
				if(isUpdated){
					ActionMessage message = new ActionMessage("knowledgepro.admission.history.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					errors.add("error", new ActionError("knowledgepro.admission.history.failure"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_VIEW_SUBJECT_GROUP_HISTORY_DETAIL_PAGE);
			}
		}catch (Exception e) {
			errors.add("error", new ActionError("knowledgepro.admission.history.failure"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
		}
		return mapping.findForward(CMSConstants.PRC_STUDENTEDIT_EDITPAGE);
	}
	/**
	 * validation for special characters
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern p = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher m = p.matcher(name);
        result = m.find();
        return result;

	}
}
