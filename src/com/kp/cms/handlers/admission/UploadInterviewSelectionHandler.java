package com.kp.cms.handlers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.UploadBypassInterviewForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.transactionsimpl.admission.UploadInterviewSelectionTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class UploadInterviewSelectionHandler {
private static final Log log = LogFactory.getLog(UploadInterviewResultHandler.class);
	
	/**
	 * This method will return a unique instance of UploadInterviewResultHandler.
	 */
	private static volatile UploadInterviewSelectionHandler uploadInteviewSelectionHandler = null;
	private UploadInterviewSelectionHandler() {
	}

	public static UploadInterviewSelectionHandler getInstance() {
		
		if (uploadInteviewSelectionHandler == null) {
			uploadInteviewSelectionHandler = new UploadInterviewSelectionHandler();
		}
		return uploadInteviewSelectionHandler;
	}
	
	/**
	 * This is used to get AdmAppln details(appno, appid) in key-value pair from UploadInterviewResultTransactionImpl.
	 * @param year
	 * @param courseId
	 * @return map.
	 * @throws Exception
	 */
	
	public Map<Integer, Integer> getAdmAppDetails(int year, UploadBypassInterviewForm bypassInterviewForm) throws Exception {
		log.info("call of getAdmAppDetails method in UploadInterviewResultHandler class.");
		IUploadInteviewSelectionTxn transaction = new UploadInterviewSelectionTxnImpl();
		Map<Integer,Integer> map = transaction.getAdmApplnDetails(year, bypassInterviewForm);
		log.info("end of getAdmAppDetails method in UploadInterviewResultHandler class.");
		return map;
	}
	/**
	 * 
	 * @param interviewSelectedList
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadInterviewSelectedData(List<InterviewSelected> interviewSelectedList, String user, List<SingleFieldMasterTO> notSelectedList,
			List<InterviewCardTO> interviewCardToList, Map<Integer, Integer> intPrgCourseMap,HttpServletRequest request) throws Exception{
		log.info("call of addUploadBypassInterviewData method in addUploadInterviewSelectedData class.");
		boolean isAdd = false;
		IUploadInteviewSelectionTxn transaction = new UploadInterviewSelectionTxnImpl();
		isAdd = transaction.addUploadedData(interviewSelectedList, user, notSelectedList);
		/*To upload the selection process workflow date, time and venue*/
		if(isAdd && interviewCardToList!=null && !interviewCardToList.isEmpty()){
			InterviewSchedule interviewSchedule;
			Interview interview;
			Map<String, InterviewSchedule> interviewMap=new HashMap<String, InterviewSchedule>();
			List<InterviewCard> interviewCardsToSave=new ArrayList<InterviewCard>();
			Iterator<InterviewCardTO> itr=interviewCardToList.iterator();
			while (itr.hasNext()) {
				InterviewCardTO interviewCardTO = (InterviewCardTO) itr.next();
				if(interviewCardTO!=null){
					if(interviewMap!=null && interviewMap.containsKey(interviewCardTO.getInterviewDate()+"_"+interviewCardTO.getTime()+"_"+interviewCardTO.getInterviewPrgCrsId())){
						interviewSchedule=interviewMap.remove(interviewCardTO.getInterviewDate()+"_"+interviewCardTO.getTime()+"_"+interviewCardTO.getInterviewPrgCrsId());
					if(interviewSchedule!=null){
							InterviewCard interviewCard=new InterviewCard();
							AdmAppln adm=new AdmAppln();
							adm.setId(interviewCardTO.getAdmApplnId());
							interviewCard.setAdmAppln(adm);
							interviewCard.setInterview(interviewSchedule);
							interviewCard.setTime(interviewCardTO.getTime());
							interviewCard.setInterviewer(1);
							interviewCard.setCreatedBy(user);
							interviewCard.setCreatedDate(new Date());
							interviewCard.setLastModifiedDate(new Date());
							interviewCard.setModifiedBy(user);
							
						interviewCardsToSave.add(interviewCard);
						interviewMap.put(interviewCardTO.getInterviewDate()+"_"+interviewCardTO.getTime()+"_"+interviewCardTO.getInterviewPrgCrsId(), interviewSchedule);
					}
				}else{
					 interviewSchedule=transaction.getInterviewSchedule(interviewCardTO,user);
					if(interviewSchedule!=null){
						InterviewCard interviewCard=new InterviewCard();
						AdmAppln adm=new AdmAppln();
						adm.setId(interviewCardTO.getAdmApplnId());
						interviewCard.setAdmAppln(adm);
						interviewCard.setInterview(interviewSchedule);
						interviewCard.setTime(interviewCardTO.getTime());
						interviewCard.setInterviewer(1);
						interviewCard.setCreatedBy(user);
						interviewCard.setCreatedDate(new Date());
						interviewCard.setLastModifiedDate(new Date());
						interviewCard.setModifiedBy(user);
						interviewCardsToSave.add(interviewCard);
						interviewMap.put(interviewCardTO.getInterviewDate()+"_"+interviewCardTO.getTime()+"_"+interviewCardTO.getInterviewPrgCrsId(), interviewSchedule);
					}
				}
				
				}
			}
			
			isAdd=transaction.addSelectionProcessWorkflowData(interviewCardsToSave,user,intPrgCourseMap);
			if(isAdd){
				sendMailAndSMSToStudents(interviewCardsToSave,request);
			}
		}
		log.info("end of addUploadBypassInterviewData method in addUploadInterviewSelectedData class.");
		return isAdd;
	}
	/**
	 * @param interviewCardToList
	 */
	private MobileMessaging sendSMSToStudentForAdmitCard(InterviewCard interviewCard,String desc) throws Exception {
		log.info("entered sendSMSToStudentForAdmitCard in UPloadInterviewSelectionHandler");
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
													//Application No Added By Manu	
							desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, Integer.toString(interviewCard.getAdmAppln().getApplnNo()));
							if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
								 mob=new MobileMessaging();
								mob.setDestinationNumber(mobileNo);
								mob.setMessageBody(desc);
								mob.setMessagePriority(3);
								mob.setSenderName(senderName);
								mob.setSenderNumber(senderNumber);
								mob.setMessageEnqueueDate(new Date());
								mob.setIsMessageSent(false);
							//	PropertyUtil.getInstance().save(mob);
							}
					}
				}
		return mob;
	}

	/**
	 * sending mail to students from whom card is generated
	 * @param interviewCardToList
	 * @throws Exception
	 */
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
								if (interviewCard.getInterview().getVenue()!= null) {
									interviewVenue = interviewCard.getInterview().getVenue();
								}
								
								if (interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName()!=null) {
									interviewType = interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName();
								}

								if (interviewCard.getAdmAppln().getCourseBySelectedCourseId()
												.getProgram().getName() != null) {
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
								MailTO mailTo= sendMail(interviewCard.getAdmAppln().getPersonalData().getEmail(), subject,message);
								mailToList.add(mailTo);
		
							} 
							//send SMS
							
					MobileMessaging	mobileMessage=sendSMSToStudentForAdmitCard(interviewCard,smsDesc);
					if(mobileMessage!=null)
					mobileMessagesList.add(mobileMessage);
					 }
				}
			}
			sent=CommonUtil.sendMailInBulk(mailToList);	
			txn.sendSmsinBulk(mobileMessagesList);
		}
		
		return sent;
		
	}

	/**
	 * @param desc
	 * @param admAppln
	 * @param request
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * @param email
	 * @param subject
	 * @param message
	 * @throws Exception
	 */
	private MailTO sendMail(String email, String sub, String message) throws Exception {

		boolean sent = false;

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
		mailto.setFromName(CMSConstants.STUDENTMAIL_FROMNAME);
		// uses JMS
		// sent=CommonUtil.postMail(mailto);
	//	sent = CommonUtil.sendMail(mailto);
		return mailto;
	}

	/**
	 * 
	 * @param year
	 * @param bypassInterviewForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getAdmAppDetailsForNotSelected(int year, UploadBypassInterviewForm bypassInterviewForm) throws Exception {
		log.info("call of getAdmAppDetailsForNotSelected method in UploadInterviewResultHandler class.");
		IUploadInteviewSelectionTxn transaction = new UploadInterviewSelectionTxnImpl();
		Map<Integer,Integer> map = transaction.getAdmApplnDetailsForNotSelected(year, bypassInterviewForm);
		log.info("end of getAdmAppDetailsForNotSelected method in UploadInterviewResultHandler class.");
		return map;
	}

	/**
	 * @param mobileNo
	 * @param smsTemplateApplicationRecieved
	 * @throws Exception
	 */
	public void sendSMSToStudent(String mobileNo, String templateName,AdmAppln admAppln) throws Exception{
		/*Properties prop = new Properties();
        InputStream in1 = UploadInterviewSelectionHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);*/
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMS_SENDER_NAME;
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		if(admAppln!=null && admAppln.getAdmStatus()!=null)
		desc=desc.replace(CMSConstants.SMS_TEMPLATE_STATUS,admAppln.getAdmStatus());
		//Application No Added By Manu	
		if(admAppln!=null && admAppln.getApplnNo()>0 && (Integer.toString(admAppln.getApplnNo()))!=null && !(Integer.toString(admAppln.getApplnNo())).isEmpty())
		desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO,Integer.toString(admAppln.getApplnNo()));
		//
		if(admAppln!=null && admAppln.getCourseBySelectedCourseId()!=null)
		desc=desc.replace(CMSConstants.TEMPLATE_COURSE,admAppln.getCourseBySelectedCourseId().getName());
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && !desc.isEmpty() && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			PropertyUtil.getInstance().save(mob);
		}
		
	}	

}
