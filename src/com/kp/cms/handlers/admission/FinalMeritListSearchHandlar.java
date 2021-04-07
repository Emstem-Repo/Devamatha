package com.kp.cms.handlers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.FinalMeritListForm;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.helpers.admission.FinalMeritListSearchHelper;
import com.kp.cms.to.admission.FinalMeritListMailTO;
import com.kp.cms.to.admission.FinalMeritListSearchTo;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactions.admission.IFinalMeritListSearchTransaction;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.transactionsimpl.admission.FinalMeritListTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class FinalMeritListSearchHandlar {

	private static final Log log = LogFactory.getLog(FinalMeritListSearchHandlar.class);
	
	/**
	 * Represents single ton object of the FinalMeritListSearchHandlar
	 */
	private static volatile FinalMeritListSearchHandlar finalMeritListSearchHandler = null;

	private FinalMeritListSearchHandlar() {

	}

	/**
	 * @return single ton object of the FinalMeritListSearchHandlar
	 */
	public static FinalMeritListSearchHandlar getInstanse() {
		if (finalMeritListSearchHandler == null) {
			finalMeritListSearchHandler = new FinalMeritListSearchHandlar();
		}
		return finalMeritListSearchHandler;
	}

	/**
	 * Persists the selected candidates list.
	 * @param selectedCandidatesList
	 * @param isSelected
	 * @param userId
	 * @throws Exception
	 */
	public void updateSelectedCandidates(String[] selectedCandidatesList,
			boolean isApproved,boolean isSelected, String userId,String mode) throws Exception {
		log.info("entering into updateSelectedCandidates in FinalMeritListSearchHandler class.");
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		finalMeritListTransactionImpl.updateSelectCandidates(selectedCandidatesList,isSelected,isApproved,  userId,mode);
		log.info("exit of updateSelectedCandidates in FinalMeritListSearchHandler class.");
	}

	/**
	 * approves the selected candidates list.
	 * @param selectedCandidatesList
	 * @param isSelected
	 * @param userId
	 * @throws Exception
	 */
	public void approveSelectedCandidates(String[] selectedCandidatesList,
			boolean isApproved, String userId) throws Exception {
		log.info("entering into updateSelectedCandidates in FinalMeritListSearchHandler class.");
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		finalMeritListTransactionImpl.approveSelectCandidates(
				selectedCandidatesList, isApproved, userId);
		log.info("exit of updateSelectedCandidates in FinalMeritListSearchHandler class.");
	}
	
	
	/**
	 * Get the maximum intake from the course.
	 * @param courseId
	 * @return
	 * @throws ApplicationException
	 */
	public int getMaxIntakeFromCourse(int courseId) throws ApplicationException {
		log.info("entering into getMaxIntakeFromCourse in FinalMeritListSearchHandler class.");
		IFinalMeritListSearchTransaction finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		log.info("exit of getMaxIntakeFromCourse in FinalMeritListSearchHandler class.");
		return finalMeritListTransactionImpl.getMaxIntakeFromCourse(courseId);
	}
	
	/**
	 * Get the maximum intake from the program.
	 * @param programId
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, Integer> getMaxIntakeFromProgram(int programId) throws ApplicationException {
		log.info("entering into getMaxIntakeFromCourse in FinalMeritListSearchHandler class.");
		IFinalMeritListSearchTransaction finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		log.info("exit of getMaxIntakeFromCourse in FinalMeritListSearchHandler class.");
		return finalMeritListTransactionImpl.getMaxIntakeFromProgram(programId);
	}

	/**
	 * Persists the selected preference.
	 * @param interviewResultEntryForm
	 * @throws ApplicationException
	 */
	public void updateSelectedPreference(
			InterviewResultEntryForm interviewResultEntryForm)
			throws ApplicationException {
		log.info("entering into updateSelectedPreference in FinalMeritListSearchHandler class.");
		IFinalMeritListSearchTransaction finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		finalMeritListTransactionImpl
				.updateSelectedPreference(interviewResultEntryForm);
		log.info("exit of updateSelectedPreference in FinalMeritListSearchHandler class.");
	}

	/**
	 * Get the final merit list search results.
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterViewSelected
	 * @return
	 * @throws Exception
	 */
	public List<FinalMeritListSearchTo> getFinalMeritListSearchResult(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterViewSelected) throws Exception {
		log.info("entering into getFinalMeritListSearchResult in FinalMeritListSearchHandler class.");
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		List<FinalMeritListSearchTo> finalMeritListSearchTO = FinalMeritListSearchHelper
				.getFinalMeritListTOList(finalMeritListTransactionImpl
						.getFinalMeritListSearch(FinalMeritListSearchHelper.getFinalMeritListSearchQuery(finalMeritListForm,
								isSelected, isInterViewSelected)));
		log.info("exit of getFinalMeritListSearchResult in FinalMeritListSearchHandler class.");
		return finalMeritListSearchTO;

	}
	
	/**
	 * Get the final merit list search results for approval.
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterViewSelected
	 * @return
	 * @throws Exception
	 */
	public List<FinalMeritListSearchTo> getFinalMeritListApproveSearchResult(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterViewSelected) throws Exception {
		log.info("entering into getFinalMeritListApproveSearchResult in FinalMeritListSearchHandler class.");
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		List<FinalMeritListSearchTo> finalMeritListSearchTO = FinalMeritListSearchHelper
				.getFinalMeritListTOList(finalMeritListTransactionImpl
						.getFinalMeritListSearch(FinalMeritListSearchHelper.getFinalMeritListApproveSearchQuery(finalMeritListForm,
								isSelected, isInterViewSelected)));
		log.info("exit of getFinalMeritListApproveSearchResult in FinalMeritListSearchHandler class.");
		return finalMeritListSearchTO;

	}
	
	/**
	 * Get the final merit list search results.
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterViewSelected
	 * @return
	 * @throws Exception
	 */
	public List<FinalMeritListSearchTo> getFinalMeritListSelectedResult(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterViewSelected) throws Exception {
		log.info("entering into getFinalMeritListSelectedResult in FinalMeritListSearchHandler class.");
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		List<FinalMeritListSearchTo> finalMeritListSearchTO = FinalMeritListSearchHelper
				.getFinalMeritListTOList(finalMeritListTransactionImpl
						.getFinalMeritListSearch(FinalMeritListSearchHelper.getFinalMeritListSelectedQuery(finalMeritListForm,
								isSelected, isInterViewSelected)));
		log.info("exit of getFinalMeritListSelectedResult in FinalMeritListSearchHandler class.");
		return finalMeritListSearchTO;

	}
	
	/**
	 * Get the final merit list search results.
	 * 
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterViewSelected
	 * @return
	 * @throws Exception
	 */
	public int getFinalMeritListSelectedCount(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterViewSelected) throws Exception {
		int selectedCount = 0;
		log.info("entering into getFinalMeritListSearchResult in FinalMeritListSearchHandler class.");
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		List<PersonalData> selectedList = finalMeritListTransactionImpl
				.getFinalMeritListSearch(FinalMeritListSearchHelper
						.getFinalMeritListSearchQuery(finalMeritListForm,
								isSelected, isInterViewSelected));
		if (selectedList != null) {
			selectedCount = selectedList.size();
		}

		log.info("exit of getFinalMeritListSearchResult in FinalMeritListSearchHandler class.");
		return selectedCount;
	}
	
	/**
	 * Get the final merit list search results.
	 * 
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterViewSelected
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getFinalMeritListSelectedCountCoursewise(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterViewSelected) throws Exception {
		
		FinalMeritListTransactionImpl finalMeritListTransactionImpl = new FinalMeritListTransactionImpl();
		List<PersonalData> selectedList = finalMeritListTransactionImpl
		.getFinalMeritListSearch(FinalMeritListSearchHelper
				.getFinalMeritListSearchQuery(finalMeritListForm,
						isSelected, isInterViewSelected));
		
		Map<Integer, Integer> selectedMap = FinalMeritListSearchHelper.getSelectedCountCoursewise(selectedList) ;
		
		log.info("exit of getFinalMeritListSearchResult in FinalMeritListSearchHandler class.");
		return selectedMap;

	}
	
	/**
	 * Send mail to student after successful submit of application
	 * @param request 
	 * @param admForm
	 * @return
	 */
	public boolean sendMailToStudent(FinalMeritListForm finalMeritListForm, HttpServletRequest request)
			throws Exception {
		boolean sent = false;
		List<GroupTemplate> templateList = getFinalMeritListMailTemplate(finalMeritListForm.getCourseId(), finalMeritListForm.getProgramId());

		List<FinalMeritListSearchTo> finalMeritListToList = finalMeritListForm
				.getFinalMeritList();
		if (templateList != null && !templateList.isEmpty()
				&& finalMeritListToList != null
				&& !finalMeritListToList.isEmpty()) {

			String desc = templateList.get(0).getTemplateDescription();
			// send mail to applicant

			
			Iterator<FinalMeritListSearchTo> finalMeritListIterator = finalMeritListToList
					.iterator();

			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class
						.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
				log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}

			while (finalMeritListIterator.hasNext()) {
				FinalMeritListSearchTo finalMeritListSearchTo = (FinalMeritListSearchTo) finalMeritListIterator
						.next();
				if (finalMeritListSearchTo.getApplicantMail() != null
						&& !finalMeritListSearchTo.getApplicantMail().isEmpty()) {
					StringBuilder program = new StringBuilder();
					StringBuilder academicyear = new StringBuilder();
					
					
					 if (finalMeritListSearchTo.getAdmAppln() != null
								&& finalMeritListSearchTo.getAdmAppln().getCourse() != null
								&& finalMeritListSearchTo.getAdmAppln().getCourse()
										.getProgram() != null
								&& finalMeritListSearchTo.getAdmAppln().getCourse()
										.getProgram().getName() != null) {
							program.append(finalMeritListSearchTo.getAdmAppln().getCourse()
									.getProgram().getName());
						}
						 
						 if (finalMeritListSearchTo.getAdmAppln() != null
								&& finalMeritListSearchTo.getAdmAppln().getAppliedYear() != null) {
							academicyear.append(String.valueOf(finalMeritListSearchTo
									.getAdmAppln().getAppliedYear())).append("-").append(finalMeritListSearchTo.getAdmAppln().getAppliedYear() + 1);
						}
					
						// replace dyna data
				    String subject = "Admission for "+program + " " + academicyear;
					String message = desc;
					message = CommonUtil.replaceMessageText(message, finalMeritListSearchTo.getAdmAppln(), request);
					// send mail
					sendMail(finalMeritListSearchTo.getApplicantMail(),
							subject, message, prop);

				}
			}

		}
		return sent;
	}
	
	/**
	 * Send mail to student after successful submit of application
	 * @param request 
	 * @param admForm
	 * @return
	 */
	public boolean sendMailToApprovedStudent(FinalMeritListForm finalMeritListForm, HttpServletRequest request)
			throws Exception {
		boolean sent = false;
		List<GroupTemplate> templateList = getFinalMeritListMailTemplate(finalMeritListForm.getCourseId(), finalMeritListForm.getProgramId());
		String[] selectedarray=finalMeritListForm.getSelectedCandidates();
		List<String> selectedApplicants=Arrays.asList(selectedarray);
		List<FinalMeritListSearchTo> finalMeritListToList = finalMeritListForm
				.getFinalMeritList();
		if (templateList != null && !templateList.isEmpty()
				&& finalMeritListToList != null
				&& !finalMeritListToList.isEmpty()) {

			String desc = templateList.get(0).getTemplateDescription();
			// send mail to applicant

			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class
						.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
				log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
			List<FinalMeritListMailTO> mailList = finalMeritListForm.getMailList();
			Iterator<FinalMeritListMailTO> mailItr = mailList.iterator();
			FinalMeritListMailTO finalMeritListMailTO;
			while (mailItr.hasNext()) {
				finalMeritListMailTO = mailItr.next();
				if (selectedApplicants.contains(String.valueOf(finalMeritListMailTO.getMeritListId())) 
						&& finalMeritListMailTO.getEmail() != null
						&& !finalMeritListMailTO.getEmail().isEmpty()) {
					//String program = "";
					//String academicyear = "";
					// replace dyna data
					 String subject = "Admission for "+finalMeritListMailTO.getProgram() + " " + finalMeritListMailTO.getAcademicYear();
					
					
					String message = desc;
					message = finalMeritApproveReplaceMessageText(message, finalMeritListMailTO, request);
					// send mail
					sendMail(finalMeritListMailTO.getEmail(),
							subject, message, prop);

				}
			}
			
			/*
			while (finalMeritListIterator.hasNext()) {
				FinalMeritListSearchTo finalMeritListSearchTo = (FinalMeritListSearchTo) finalMeritListIterator
						.next();
				if (selectedApplicants.contains(String.valueOf(finalMeritListSearchTo.getFinalMeritSetId())) 
						&& finalMeritListSearchTo.getApplicantMail() != null
						&& !finalMeritListSearchTo.getApplicantMail().isEmpty()) {
					String program = "";
					String academicyear = "";
					// replace dyna data
					 String subject = "Admission for "+program + " " + academicyear;
					
					 if (finalMeritListSearchTo.getAdmAppln() != null
								&& finalMeritListSearchTo.getAdmAppln().getCourse() != null
								&& finalMeritListSearchTo.getAdmAppln().getCourse()
										.getProgram() != null
								&& finalMeritListSearchTo.getAdmAppln().getCourse()
										.getProgram().getName() != null) {
							program = finalMeritListSearchTo.getAdmAppln().getCourse()
									.getProgram().getName();
						}
						 
						 if (finalMeritListSearchTo.getAdmAppln() != null
								&& finalMeritListSearchTo.getAdmAppln().getAppliedYear() != null) {
							academicyear = String.valueOf(finalMeritListSearchTo
									.getAdmAppln().getAppliedYear())
									+ "-"
									+ (finalMeritListSearchTo.getAdmAppln().getAppliedYear() + 1);
						}
					
					
					String message = desc;
					message = CommonUtil.replaceMessageText(message, finalMeritListSearchTo.getAdmAppln(), request);
					// send mail
					sendMail(finalMeritListSearchTo.getApplicantMail(),
							subject, message, prop);

				}
			}
	*/
		}
		return sent;
	}
	
	
	/**
	 * @return Mail template for final MeritList
	 * @throws Exception 
	 */
	public List<GroupTemplate> getFinalMeritListMailTemplate(String courseId, String programId)
			throws Exception {
		log.debug("Entering getTemplateList ");
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		int tmplcourseId = 0;
		if(courseId!= null && !courseId.trim().isEmpty()){
			tmplcourseId = Integer.parseInt(courseId);
		}
		/*return ITemplatePassword.getDuplicateCheckList(0,
				CMSConstants.FINALMERITlIST_TEMPLATE);
			}*/
			return ITemplatePassword.getGroupTemplate(tmplcourseId,
					CMSConstants.FINALMERITlIST_TEMPLATE, Integer.parseInt(programId));
		}
	
	
	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID, String sub, String message,
			Properties prop) {
		boolean sent = false;

//		String adminmail = prop .getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
		String adminmail = CMSConstants.MAIL_USERID;
		String toAddress = mailID;
		// MAIL TO CONSTRUCTION
		String subject = sub;
		String msg = message;

		MailTO mailto = new MailTO();
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop
				.getProperty("knowledgepro.admission.studentmail.fromName"));
		// uses JMS
		// sent=CommonUtil.postMail(mailto);
		sent = CommonUtil.sendMail(mailto);
		return sent;
	}
	
	public String finalMeritApproveReplaceMessageText(String messageTemplate, FinalMeritListMailTO mailTO,
			HttpServletRequest request) throws Exception {
		
		String program = "";
		String course = "";
		String selectedCourse = "";
		String applicationNo = "";
		String academicYear = "";
		String applicantName = "";
		String dateOfBirth = "";
		String placeOfBirth = "";
		String nationality = "";
		String religion = "";
		String subreligion = "";
		String category = "";
		String gender = "";
		String email = "";
		String contextPath = "";
		String logoPath = "";
		String currentAddress = "";
		String permanentAddress = "";
		byte[] photo = null;
		byte[] logo = null;
		
		String message = messageTemplate;

		HttpSession session = request.getSession(false);
		if (mailTO != null) {
			if (mailTO.getDateOfBirth() != null) {
				dateOfBirth = mailTO.getDateOfBirth();
			}
			if (mailTO.getPlaceOfBirth() != null) {
				placeOfBirth = mailTO.getPlaceOfBirth();
			}
			
			if(mailTO.getApplicantName()!= null){
				applicantName = mailTO.getApplicantName();
			}
			
			if (mailTO.getNationality() != null) {
				nationality = mailTO.getNationality();
			}
			if (mailTO.getReligion() != null) {
				religion = mailTO.getReligion();
			}
			if (mailTO.getSubreligion() != null) {
				subreligion = mailTO.getSubreligion();
			}

			if (mailTO.getGender() != null) {
				gender = mailTO.getGender();
			}
			if (mailTO.getEmail() != null) {
				email = mailTO.getEmail();
			}
			if (mailTO.getCategory() != null) {
				category = mailTO.getCategory();
			}
			if (mailTO.getCourse() != null) {
				course = mailTO.getCourse();
			}
			if (mailTO.getSelectedCourse()!= null) {
				selectedCourse =mailTO.getSelectedCourse();
			}
			if (mailTO.getProgram() != null) {
				program = mailTO.getProgram();
			}
			applicationNo = mailTO.getApplicationNo();
			academicYear = mailTO.getAcademicYear();

			if (mailTO.getPhoto() != null) {
				photo = mailTO.getPhoto();

				if (session != null) {
					contextPath = request.getContextPath();
					contextPath = "<img src="
							+ contextPath
							+ "/PhotoServlet alt='Photo not available' width='150' height='150'>";
					session.setAttribute("PhotoBytes", photo);
				}
			}

			if(mailTO.getLogo()!= null){
				logo = mailTO.getLogo();
			}
			if (session != null) {
				session.setAttribute("LogoBytes", logo);
			}
			logoPath = request.getContextPath();
			logoPath = "<img src="+ logoPath + "/LogoServlet alt='Logo not available' width='210' height='100'>";
		
			currentAddress = mailTO.getCurrentAddress();
			permanentAddress = mailTO.getPermanentAddress();
			
		}

		message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,
				applicantName);
		message = message.replace(CMSConstants.TEMPLATE_DOB, dateOfBirth);
		message = message.replace(CMSConstants.TEMPLATE_POB, placeOfBirth);
		message = message.replace(CMSConstants.TEMPLATE_NATIONALITY,
				nationality);
		message = message.replace(CMSConstants.TEMPLATE_SUBRELIGION,
				subreligion);
		message = message.replace(CMSConstants.TEMPLATE_RELIGION, religion);
		message = message.replace(CMSConstants.TEMPLATE_GENDER, gender);
		message = message.replace(CMSConstants.TEMPLATE_EMAIL, email);
		message = message.replace(CMSConstants.TEMPLATE_CASTE, category);

		message = message.replace(CMSConstants.TEMPLATE_PHOTO, contextPath);
		message = message.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
		message = message.replace(CMSConstants.TEMPLATE_PROGRAM, program);
		message = message.replace(CMSConstants.TEMPLATE_COURSE, course);
		message = message.replace(CMSConstants.TEMPLATE_SELECTED_COURSE, selectedCourse);
		message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,
				applicationNo);
		message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR,
				academicYear);
		message = message.replace(CMSConstants.TEMPLATE_CURRENT_ADDRESS,
				currentAddress);
		message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS,
				permanentAddress);
		return message;

	}	
	public void createFinalMeritListMailTO(FinalMeritListForm finalMeritListForm) throws Exception{
		Iterator<FinalMeritListSearchTo> finalMeritListIterator = finalMeritListForm.getFinalMeritList()
		.iterator();
		FinalMeritListMailTO mailTO;
		List<FinalMeritListMailTO> mailList = new ArrayList<FinalMeritListMailTO>();
		while (finalMeritListIterator.hasNext()) {
			FinalMeritListSearchTo finalMeritListSearchTo = (FinalMeritListSearchTo) finalMeritListIterator
					.next();
			
			mailTO = new FinalMeritListMailTO();
			if(finalMeritListSearchTo.getFinalMeritSetId()!= null){
				mailTO.setMeritListId(finalMeritListSearchTo.getFinalMeritSetId());
			}				
			AdmAppln admAppln = finalMeritListSearchTo.getAdmAppln();
			mailTO.setApplicationProcessSms(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms());
			if (admAppln != null && admAppln.getPersonalData() != null) {
				if (admAppln != null && admAppln.getPersonalData() != null) {
					mailTO.setMobileNo1(admAppln.getPersonalData().getMobileNo1());
					mailTO.setMobileNo2(admAppln.getPersonalData().getMobileNo2());
					if (admAppln.getPersonalData().getDateOfBirth() != null) {
						mailTO.setDateOfBirth(CommonUtil.getStringDate(admAppln
								.getPersonalData().getDateOfBirth()));
					}
					if (admAppln.getPersonalData().getBirthPlace() != null) {
						mailTO.setPlaceOfBirth(admAppln.getPersonalData().getBirthPlace());
					}
					if (admAppln.getPersonalData().getFirstName() != null
							&& !admAppln.getPersonalData().getFirstName().trim()
									.isEmpty()) {
						mailTO.setApplicantName(admAppln.getPersonalData().getFirstName());
					}
					if (admAppln.getPersonalData().getMiddleName() != null
							&& !admAppln.getPersonalData().getMiddleName().trim()
									.isEmpty()) {
						mailTO.setApplicantName(mailTO.getApplicantName() + " "
								+ admAppln.getPersonalData().getMiddleName());
					}
					if (admAppln.getPersonalData().getLastName() != null
							&& !admAppln.getPersonalData().getLastName().trim()
									.isEmpty()) {
						mailTO.setApplicantName(mailTO.getApplicantName() + " "
								+ admAppln.getPersonalData().getLastName());
					}
					if (admAppln.getPersonalData().getNationalityOthers() != null) {
						mailTO.setNationality(admAppln.getPersonalData()
								.getNationalityOthers());
					} else if (admAppln.getPersonalData().getNationality() != null) {
						mailTO.setNationality(admAppln.getPersonalData().getNationality()
								.getName());
					}
					if (admAppln.getPersonalData().getReligionOthers() != null) {
						mailTO.setReligion(admAppln.getPersonalData().getReligionOthers());
					} else if (admAppln.getPersonalData().getReligion() != null) {
						mailTO.setReligion(admAppln.getPersonalData().getReligion()
								.getName());
					}
	
					if (admAppln.getPersonalData().getReligionSectionOthers() != null) {
						mailTO.setSubreligion(admAppln.getPersonalData()
								.getReligionSectionOthers());
					} else if (admAppln.getPersonalData().getReligionSection() != null) {
						mailTO.setSubreligion(admAppln.getPersonalData()
								.getReligionSection().getName());
					}
	
					if (admAppln.getPersonalData().getGender() != null) {
						mailTO.setGender(admAppln.getPersonalData().getGender());
					}
					if (admAppln.getPersonalData().getEmail() != null) {
						mailTO.setEmail(admAppln.getPersonalData().getEmail());
					}
					if (admAppln.getPersonalData().getCasteOthers() != null) {
						mailTO.setCategory(admAppln.getPersonalData().getCasteOthers());
					} else if (admAppln.getPersonalData().getCaste() != null) {
						mailTO.setCategory(admAppln.getPersonalData().getCaste().getName());
					}
					if (admAppln.getCourse() != null) {
						mailTO.setCourse(admAppln.getCourse().getName());
					}
					if (admAppln.getCourseBySelectedCourseId() != null) {
						mailTO.setSelectedCourse(admAppln.getCourseBySelectedCourseId().getName());
					}
					if (admAppln.getCourse() != null
							&& admAppln.getCourse().getProgram() != null) {
						mailTO.setProgram(admAppln.getCourse().getProgram().getName());
					}
					mailTO.setApplicationNo(String.valueOf(admAppln.getApplnNo()));
					mailTO.setAcademicYear(String.valueOf(admAppln.getAppliedYear()));
	
					if (admAppln.getApplnDocs() != null) {
						Iterator<ApplnDoc> applnDocItr = admAppln.getApplnDocs()
								.iterator();
	
						while (applnDocItr.hasNext()) {
							ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
							if (applnDoc.getIsPhoto() != null
									&& applnDoc.getIsPhoto()) {
								mailTO.setPhoto(applnDoc.getDocument());
							}
						}
					}
	
					Organisation organisation = OrganizationHandler.getInstance()
							.getRequiredFile();
					if (organisation != null) {
						mailTO.setLogo(organisation.getLogo());
					}
				}
				
				StringBuffer currentAddress = new StringBuffer();
				
				if (admAppln.getPersonalData().getCurrentAddressLine1() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCurrentAddressLine1());
					currentAddress.append(' ');
				}
	
				if (admAppln.getPersonalData().getCurrentAddressLine2() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCurrentAddressLine2());
					currentAddress.append(' ');
				}
				if (admAppln.getPersonalData().getCityByCurrentAddressCityId() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCityByCurrentAddressCityId());
					currentAddress.append(' ');
				}
				if (admAppln.getPersonalData().getStateByCurrentAddressStateId() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getStateByCurrentAddressStateId().getName());
					currentAddress.append(' ');
				} else if (admAppln.getPersonalData()
						.getCurrentAddressStateOthers() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCurrentAddressStateOthers());
					currentAddress.append(' ');
				}
	
				if (admAppln.getPersonalData()
						.getCountryByCurrentAddressCountryId() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCountryByCurrentAddressCountryId().getName());
					currentAddress.append(' ');
				} else if (admAppln.getPersonalData()
						.getCurrentAddressCountryOthers() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCurrentAddressCountryOthers());
					currentAddress.append(' ');
				}
				if (admAppln.getPersonalData().getCurrentAddressZipCode() != null) {
					currentAddress.append(admAppln.getPersonalData()
							.getCurrentAddressZipCode());
					currentAddress.append(' ');
				}
				mailTO.setCurrentAddress(currentAddress.toString());
				
				StringBuffer permanentAddress = new StringBuffer();
				
				if (admAppln.getPersonalData().getParentAddressLine1() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getParentAddressLine1());
					permanentAddress.append(' ');
				}
	
				if (admAppln.getPersonalData().getParentAddressLine2() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getParentAddressLine2());
					permanentAddress.append(' ');
				}
				if (admAppln.getPersonalData().getCityByPermanentAddressCityId() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getCityByPermanentAddressCityId());
					permanentAddress.append(' ');
				}
				if (admAppln.getPersonalData().getStateByParentAddressStateId() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getStateByParentAddressStateId().getName());
					permanentAddress.append(' ');
				} else if (admAppln.getPersonalData()
						.getParentAddressStateOthers() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getParentAddressStateOthers());
					permanentAddress.append(' ');
				}
	
				if (admAppln.getPersonalData()
						.getCountryByPermanentAddressCountryId() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getCountryByPermanentAddressCountryId().getName());
					permanentAddress.append(' ');
				} else if (admAppln.getPersonalData()
						.getPermanentAddressCountryOthers() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getPermanentAddressCountryOthers());
					permanentAddress.append(' ');
				}
				if (admAppln.getPersonalData().getPermanentAddressZipCode() != null) {
					permanentAddress.append(admAppln.getPersonalData()
							.getPermanentAddressZipCode());
					permanentAddress.append(' ');
				}
				mailTO.setPermanentAddress(permanentAddress.toString());
	
				mailList.add(mailTO);
			}	
			
		}
		finalMeritListForm.setMailList(mailList);
	}

	/**
	 * @param finalMeritListForm
	 * @param request
	 */
	public void sendSMSToApprovedStudent(FinalMeritListForm finalMeritListForm, HttpServletRequest request) throws Exception {
		String[] selectedarray=finalMeritListForm.getSelectedCandidates();
		List<String> selectedApplicants=Arrays.asList(selectedarray);
		List<FinalMeritListMailTO> mailList = finalMeritListForm.getMailList();
		Iterator<FinalMeritListMailTO> mailItr = mailList.iterator();
		FinalMeritListMailTO finalMeritListMailTO;
		while (mailItr.hasNext()) {
			finalMeritListMailTO = mailItr.next();
			if (selectedApplicants.contains(String.valueOf(finalMeritListMailTO.getMeritListId())) 
					&& finalMeritListMailTO.isApplicationProcessSms()) {
				String mobileNo="";
				if(finalMeritListMailTO.getMobileNo1()!=null && !finalMeritListMailTO.getMobileNo1().isEmpty()){
					mobileNo=finalMeritListMailTO.getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(finalMeritListMailTO.getMobileNo2()!=null && !finalMeritListMailTO.getMobileNo2().isEmpty()){
					mobileNo=mobileNo+finalMeritListMailTO.getMobileNo2();
				}
				//Application No Added By Manu	
				if(mobileNo.length()==12){
					ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_E_ADMISSION_CARD,finalMeritListMailTO.getApplicationNo());
				}
			}
		}
		
	}
}