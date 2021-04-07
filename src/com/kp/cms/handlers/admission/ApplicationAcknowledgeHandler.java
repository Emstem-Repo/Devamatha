package com.kp.cms.handlers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admission.ApplnAcknowledgement;
import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.ApplicationAcknowledgeForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.helpers.admission.ApplicationAcknowledgeHelper;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.ApplnAcknowledgementTo;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IApplicationAcknowledgeTxn;
import com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.ApplicationAcknowledgeTxnImpl;
import com.kp.cms.transactionsimpl.admission.UploadInterviewSelectionTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class ApplicationAcknowledgeHandler {
	private static volatile ApplicationAcknowledgeHandler applnAcknowledge = null;
	IApplicationAcknowledgeTxn transaction = ApplicationAcknowledgeTxnImpl.getInstance();
	
	/**
	 * @return
	 */
	public static ApplicationAcknowledgeHandler getInstance(){
		if(applnAcknowledge == null){
			applnAcknowledge = new ApplicationAcknowledgeHandler();
			return applnAcknowledge;
		}
		return applnAcknowledge;
	}
	
	/**
	 * @param appnAcknowledgementForm
	 * @param request
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	public boolean saveApplnAcknowledge(ApplicationAcknowledgeForm appnAcknowledgementForm,HttpServletRequest request,ActionErrors errors) throws Exception {
		ApplnAcknowledgement acknowledge = ApplicationAcknowledgeHelper.getInstance().convertFormToBO(appnAcknowledgementForm);
		boolean isAdd = transaction.saveAcknowledge(acknowledge,appnAcknowledgementForm);
		return isAdd;
	}
	/**
	 * @param appnAcknowledgementForm
	 * @return
	 * @throws Exception
	 */
	public ApplnAcknowledgementTo getDetails(ApplicationAcknowledgeForm appnAcknowledgementForm) throws Exception {
		ApplnAcknowledgement aplnAcknowledge = transaction.getDetails(appnAcknowledgementForm);
		if(aplnAcknowledge!=null){
			if(aplnAcknowledge.getReceivedThrough()!=null && !aplnAcknowledge.getReceivedThrough().isEmpty()){
			     ReceivedThrough receive = transaction.getslipRequired(aplnAcknowledge.getReceivedThrough());
			     if(receive.getSlipRequired())
			         appnAcknowledgementForm.setSlipRequred("true");
			     else
				     appnAcknowledgementForm.setSlipRequred("false");
			}else
				appnAcknowledgementForm.setSlipRequred("true");
		}
		ApplnAcknowledgementTo applnTo = ApplicationAcknowledgeHelper.getInstance().convertBoToTO(aplnAcknowledge);
		return applnTo;
	}
	/**
	 * @param appnAcknowledgementForm
	 * @throws Exception
	 */
	public void setLogoToForm(ApplicationAcknowledgeForm appnAcknowledgementForm)throws Exception{
		Organisation org=transaction.getOrganizationDetails();
		ApplnAcknowledgement applnAcknowledge = transaction.getApplnAcknowledgement(appnAcknowledgementForm.getAppNo());
		if(applnAcknowledge!=null){
			if(applnAcknowledge.getSlipNo()!=null){
				StringBuilder strSlipNum=new StringBuilder();
				strSlipNum.append("AC");
				for(int i=1;i<=(6-applnAcknowledge.getSlipNo().length());i++){
					strSlipNum.append("0");
				}
				strSlipNum.append(applnAcknowledge.getSlipNo());
				appnAcknowledgementForm.setSlipNo(strSlipNum.toString());
			}
			if(applnAcknowledge.getCourse()!=null){
				appnAcknowledgementForm.setCourseName(applnAcknowledge.getCourse().getName());
			}
		}
		ApplicationAcknowledgeHelper.getInstance().setLogoToForm(org, appnAcknowledgementForm,applnAcknowledge);
	}
	
	
/*	public boolean addUploadInterviewSelectedData(ApplicationAcknowledgeForm admForm,HttpServletRequest request) throws Exception{
		IApplicationAcknowledgeTxn txn= new ApplicationAcknowledgeTxnImpl();
		InterviewCardTO interviewCardTO=txn.getInterviewScheduleDetails(admForm);
		InterviewSchedule interviewSchedule;
		boolean isAdd = false;
		List<InterviewCard> interviewCardsToSave=new ArrayList<InterviewCard>();
	
					 interviewSchedule=txn.getInterviewSchedule(interviewCardTO, admForm);
					if(interviewSchedule!=null){
						InterviewCard interviewCard=new InterviewCard();
						AdmAppln adm=new AdmAppln();
						adm.setId(interviewCardTO.getAdmApplnId());
						interviewCard.setAdmAppln(adm);
						interviewCard.setInterview(interviewSchedule);
						interviewCard.setTime(interviewCardTO.getTime());
						interviewCard.setInterviewer(1);
						interviewCard.setCreatedBy(admForm.getUserId());
						interviewCard.setCreatedDate(new Date());
						interviewCard.setLastModifiedDate(new Date());
						interviewCard.setModifiedBy(admForm.getUserId());
						interviewCardsToSave.add(interviewCard);
					}
			isAdd=txn.addSelectionProcessWorkflowData(interviewCardsToSave,admForm.getUserId(), admForm);
			if(isAdd){
				sendMailAndSMSToStudents(interviewCardsToSave,request);
			}
		return isAdd;
	}*/
	
   public boolean addUploadInterviewSelectedData(ApplicationAcknowledgeForm admForm,HttpServletRequest request) throws Exception{
	   IApplicationAcknowledgeTxn txn= new ApplicationAcknowledgeTxnImpl();
		int a=0;
		
		InterviewCardTO interviewCardTO=txn.getInterviewScheduleDetails(admForm);
		InterviewSchedule interviewSchedule;
		boolean isAdd = false;
		List<InterviewCard> interviewCardsToSave=new ArrayList<InterviewCard>();
	
					 interviewSchedule=txn.getInterviewSchedule(interviewCardTO, admForm);
					if(interviewSchedule!=null){
						InterviewCard interviewCard=new InterviewCard();
						AdmAppln adm=new AdmAppln();
						adm.setId(interviewCardTO.getAdmApplnId());
						interviewCard.setAdmAppln(adm);
						interviewCard.setInterview(interviewSchedule);
						interviewCard.setTime(interviewCardTO.getTime());
						interviewCard.setInterviewer(1);
						interviewCard.setCreatedBy(admForm.getUserId());
						interviewCard.setCreatedDate(new Date());
						interviewCard.setLastModifiedDate(new Date());
						interviewCard.setModifiedBy(admForm.getUserId());
						interviewCardsToSave.add(interviewCard);
					}
			Integer interViewPgmCourse= txn.getInterViewPgmCourse(admForm);
			isAdd=txn.addSelectionProcessWorkflowData(interviewCardsToSave,admForm.getUserId(), admForm,  interViewPgmCourse);
			if(isAdd){
				sendMailAndSMSToStudents(interviewCardsToSave,request);
			}
		return isAdd;
	}
	
	private boolean sendMailAndSMSToStudents(List<InterviewCard> interviewCards,HttpServletRequest request) throws Exception {
		boolean sent = false;
		IUploadInteviewSelectionTxn txn=new UploadInterviewSelectionTxnImpl();
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		Map<Integer, String> templateMap=new HashMap<Integer, String>();
		String desc="";
		byte[] logo=null;
		List<MailTO> mailToList;
		List<MobileMessaging> mobileMessagesList=null;
		if(interviewCards!=null){
			mailToList=new ArrayList<MailTO>();
			mobileMessagesList=new ArrayList<MobileMessaging>();
			Iterator<InterviewCard> itr =interviewCards.iterator();
			HttpSession session = request.getSession(false);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if (organisation != null) {
					logo = organisation.getLogo();
				}
				if (session != null) {
					session.setAttribute("LogoBytes", logo);
				}
			//getting the smsTemplate
				String smsDesc="";
				SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
				List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.SMS_TEMPLATE_E_ADMITCARD);
				if(list != null && !list.isEmpty()) {
					smsDesc = list.get(0).getTemplateDescription();
				}
			while (itr.hasNext()) {
				InterviewCard interviewCard = (InterviewCard) itr.next();
				if(interviewCard!=null && interviewCard.getAdmAppln()!=null && interviewCard.getInterview()!=null){
					//getting the InterviewCard BO for admAppln 
					 interviewCard=txn.getInterviewCardBo(interviewCard.getAdmAppln().getId(),interviewCard.getInterview().getId());
					 if(interviewCard!=null){
							if(templateMap!=null && !templateMap.isEmpty() && templateMap.containsKey(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId())){
								 desc = templateMap.get(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId());
							}
							else{
								List<GroupTemplate> templateList =ITemplatePassword.getGroupTemplate(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId(),
										CMSConstants.INTERVIEW_SCHEDULE_TEMPLATE,  interviewCard.getAdmAppln().getCourseBySelectedCourseId().getProgram().getId());
								if(templateList!=null && !templateList.isEmpty()){
									templateMap.put(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId(), templateList.get(0).getTemplateDescription());
								desc=templateList.get(0).getTemplateDescription();
								}
							}
							if (interviewCard.getAdmAppln().getPersonalData().getEmail() != null) {
								String message = desc;
								message = replaceMessageText(desc, interviewCard.getAdmAppln(), request);
								String interviewDate = "";
								String interviewTime = "";
								String interviewVenue = "";
								String interviewType = "";
								String program = "";
								String academicyear = "";
								if (interviewCard.getInterview()!= null && interviewCard.getInterview().getDate()!=null) {
									interviewDate =interviewCard.getInterview().getDate().toString();
								}

								if (interviewCard.getTime() != null) {
									interviewTime = interviewCard.getTime();
								}
								if (interviewCard.getInterview()!=null && interviewCard.getInterview().getVenue()!= null) {
									interviewVenue = interviewCard.getInterview().getVenue();
								}
								
								if ( interviewCard.getInterview()!=null && interviewCard.getInterview().getInterview()!=null && interviewCard.getInterview().getInterview().getInterviewProgramCourse()!=null && interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName()!=null) {
									interviewType = interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName();
								}

								if (interviewCard.getAdmAppln().getCourseBySelectedCourseId()
												.getProgram().getName() != null && interviewCard.getAdmAppln().getCourseBySelectedCourseId()!=null) {
									program = interviewCard.getAdmAppln().getCourseBySelectedCourseId()
											.getProgram().getName();
								}


							    academicyear = String.valueOf(interviewCard.getAdmAppln().getAppliedYear())
											+ "-"+ (interviewCard.getAdmAppln().getAppliedYear() + 1);
								

								String subject = interviewType + " for " + program + " "
										+ academicyear;
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_DATE, interviewDate);
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_TIME, interviewTime);
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_VENUE, interviewVenue);
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_TYPE, interviewType);
								
		//Sending Mail
								sent= sendMailInterview(interviewCard.getAdmAppln().getPersonalData().getEmail(), subject,message);
							//	mailToList.add(mailTo);
		
							} 
							//send SMS
							
					MobileMessaging	mobileMessage=sendSMSToStudentForAdmitCard(interviewCard,smsDesc);
					if(mobileMessage!=null)
					mobileMessagesList.add(mobileMessage);
					 }
				}
			}
			//sent=CommonUtil.sendMailInBulk(mailToList);	
			txn.sendSmsinBulk(mobileMessagesList);
		}
		
		return sent;
		
	}
	
	private boolean sendMailInterview(String email, String sub, String message) throws Exception {

		boolean sent = false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {	
			return false;
		} catch (IOException e) {
			return false;
		}

//		String adminmail = prop .getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
		String adminmail = CMSConstants.MAIL_USERID;
		String toAddress = email;
		// MAIL TO CONSTRUCTION
		String subject = sub;
		String msg = message;

		MailTO mailto = new MailTO();
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
		sent=CommonUtil.sendMail(mailto);	
		return sent;
	}
	
	private MobileMessaging sendSMSToStudentForAdmitCard(InterviewCard interviewCard,String desc) throws Exception {
		MobileMessaging mob=null;
				if(interviewCard!=null){
					if (interviewCard.getAdmAppln() != null && interviewCard.getAdmAppln().getCourseBySelectedCourseId().getIsApplicationProcessSms()) {
						String mobileNo="";
						if(interviewCard.getAdmAppln().getPersonalData().getMobileNo1()!=null && !interviewCard.getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
							if(interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
									|| interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
								mobileNo = "91";
							else
								mobileNo=interviewCard.getAdmAppln().getPersonalData().getMobileNo1();
						}else{
							mobileNo="91";
						}
						if(interviewCard.getAdmAppln().getPersonalData().getMobileNo2()!=null && !interviewCard.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
							mobileNo=mobileNo+interviewCard.getAdmAppln().getPersonalData().getMobileNo2();
						}
							
							String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
							String senderName=CMSConstants.SMS_SENDER_NAME;
							
							if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
								desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, String.valueOf(interviewCard.getAdmAppln().getApplnNo()));
								mob=new MobileMessaging();
								mob.setDestinationNumber(mobileNo);
								mob.setMessageBody(desc);
								mob.setMessagePriority(3);
								mob.setSenderName(senderName);
								mob.setSenderNumber(senderNumber);
								mob.setMessageEnqueueDate(new Date());
								mob.setIsMessageSent(false);
								//PropertyUtil.getInstance().save(mob);
							}
					}
				}
		return mob;
	}

	private String replaceMessageText(String messageTemplate, AdmAppln admAppln,HttpServletRequest request) throws Exception {

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
		StringBuffer currentAddress = new StringBuffer();
		StringBuffer permanentAddress = new StringBuffer();
		byte[] photo = null;

		String message = messageTemplate;

		HttpSession session = request.getSession(false);
		if (admAppln != null && admAppln.getPersonalData() != null) {
			if (admAppln.getPersonalData() != null) {
				if (admAppln.getPersonalData().getDateOfBirth() != null) {
					dateOfBirth = CommonUtil.getStringDate(admAppln
							.getPersonalData().getDateOfBirth());
				}
				if (admAppln.getPersonalData().getBirthPlace() != null) {
					placeOfBirth = admAppln.getPersonalData().getBirthPlace();
				}
				if (admAppln.getPersonalData().getFirstName() != null
						&& !admAppln.getPersonalData().getFirstName().trim()
								.isEmpty()) {
					applicantName = admAppln.getPersonalData().getFirstName();
				}
				if (admAppln.getPersonalData().getMiddleName() != null
						&& !admAppln.getPersonalData().getMiddleName().trim()
								.isEmpty()) {
					applicantName = applicantName + " "
							+ admAppln.getPersonalData().getMiddleName();
				}
				if (admAppln.getPersonalData().getLastName() != null
						&& !admAppln.getPersonalData().getLastName().trim()
								.isEmpty()) {
					applicantName = applicantName + " "
							+ admAppln.getPersonalData().getLastName();
				}
				if (admAppln.getPersonalData().getNationalityOthers() != null) {
					nationality = admAppln.getPersonalData()
							.getNationalityOthers();
				} else if (admAppln.getPersonalData().getNationality() != null) {
					nationality = admAppln.getPersonalData().getNationality()
							.getName();
				}
				if (admAppln.getPersonalData().getReligionOthers() != null) {
					religion = admAppln.getPersonalData().getReligionOthers();
				} else if (admAppln.getPersonalData().getReligion() != null) {
					religion = admAppln.getPersonalData().getReligion()
							.getName();
				}

				if (admAppln.getPersonalData().getReligionSectionOthers() != null) {
					subreligion = admAppln.getPersonalData()
							.getReligionSectionOthers();
				} else if (admAppln.getPersonalData().getReligionSection() != null) {
					subreligion = admAppln.getPersonalData()
							.getReligionSection().getName();
				}

				if (admAppln.getPersonalData().getGender() != null) {
					gender = admAppln.getPersonalData().getGender();
				}
				if (admAppln.getPersonalData().getEmail() != null) {
					email = admAppln.getPersonalData().getEmail();
				}
				if (admAppln.getPersonalData().getCasteOthers() != null) {
					category = admAppln.getPersonalData().getCasteOthers();
				} else if (admAppln.getPersonalData().getCaste() != null) {
					category = admAppln.getPersonalData().getCaste().getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null) {
					course = admAppln.getCourseBySelectedCourseId().getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null) {
					selectedCourse = admAppln.getCourseBySelectedCourseId()
							.getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null
						&& admAppln.getCourseBySelectedCourseId().getProgram() != null) {
					program = admAppln.getCourseBySelectedCourseId().getProgram().getName();
				}
				applicationNo = String.valueOf(admAppln.getApplnNo());
				academicYear = String.valueOf(admAppln.getAppliedYear());

				if (admAppln.getApplnDocs() != null) {
					Iterator<ApplnDoc> applnDocItr = admAppln.getApplnDocs()
							.iterator();

					while (applnDocItr.hasNext()) {
						ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
						if (applnDoc.getIsPhoto() != null
								&& applnDoc.getIsPhoto()) {
							photo = applnDoc.getDocument();

							if (session != null) {
								contextPath = request.getContextPath();
								contextPath = "<img src="
										+ contextPath
										+ "/PhotoServlet alt='Photo not available' width='150' height='150'>";
								session.setAttribute("PhotoBytes", photo);
							}
						}
					}
				}

			
				logoPath = request.getContextPath();
				logoPath = "<img src="
						+ logoPath
						+ "/LogoServlet alt='Logo not available' width='210' height='100'>";
			}

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
			} else if (admAppln.getPersonalData().getParentAddressStateOthers() != null) {
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
		message = message.replace(CMSConstants.TEMPLATE_SELECTED_COURSE,
				selectedCourse);
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

}
