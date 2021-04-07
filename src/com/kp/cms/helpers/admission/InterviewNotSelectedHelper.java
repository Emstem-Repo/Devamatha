package com.kp.cms.helpers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewNotSelectedForm;
import com.kp.cms.to.admission.InterviewNotSelectedTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class InterviewNotSelectedHelper {
	
	public static volatile InterviewNotSelectedHelper interviewNotSelectedHelper = null;
	
	private InterviewNotSelectedHelper() {

	}

	public static InterviewNotSelectedHelper getInstance() {
		if (interviewNotSelectedHelper == null) {
			interviewNotSelectedHelper = new InterviewNotSelectedHelper();
		}
		return interviewNotSelectedHelper;
	}
	
	/**
	 * 
	 * @param notSelectedCandidatesList
	 * @return
	 */
	public List<InterviewNotSelectedTO> convertBotoTo(List<Object[]> notSelectedCandidatesList) {
		List<InterviewNotSelectedTO> notSelectedCandidatesTOList = new ArrayList<InterviewNotSelectedTO>();
		if (notSelectedCandidatesList != null) {
			Iterator<Object[]> notSelectedCandidatesItr = notSelectedCandidatesList.iterator();

			while (notSelectedCandidatesItr.hasNext()) {
				Object[] searchResults = (Object[]) notSelectedCandidatesItr.next();
				
				InterviewNotSelectedTO notSelectedCandidates = new InterviewNotSelectedTO();
				if(searchResults[0]!=null){
					notSelectedCandidates.setApplicationNo(searchResults[0].toString());
				}
				if(searchResults[2] == null && searchResults[3] ==null){
					notSelectedCandidates.setApplicantName(searchResults[1].toString());
				}else if(searchResults[2] == null){
					notSelectedCandidates.setApplicantName(searchResults[1].toString() +" "+ searchResults[3].toString());
				}else{
					notSelectedCandidates.setApplicantName(searchResults[1].toString() +" "+ searchResults[2].toString() +" "+ searchResults[3].toString());
				}
				if(searchResults[4]!=null){
					notSelectedCandidates.setEmail(searchResults[4].toString());
				}
				if(searchResults[5]!=null){
					notSelectedCandidates.setInterviewType(searchResults[5].toString());
				}
				notSelectedCandidates.setSendMailSelected(false);
				
				notSelectedCandidatesTOList.add(notSelectedCandidates);
			}
		}	
		return notSelectedCandidatesTOList;
	}

	/**
	 * Send the mail for selected students.
	 * @param form
	 * @throws ApplicationException
	 */
	public boolean sendInterviewNotSelectedMail(InterviewNotSelectedForm interviewNotSelectedForm) throws ApplicationException {
		boolean mailSend = true;
		String template = "";
		String toAddress = "";
		Properties prop = new Properties();
		try {
			InputStream in = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);

		} catch (IOException e) {
			throw new ApplicationException(e);
		}
//		String fromaddress = prop.getProperty("knowledgepro.admin.mail");
		String fromaddress = CMSConstants.MAIL_USERID;
		String fromname = prop.getProperty("knowledgepro.admin.mailfrom");
		
		if(interviewNotSelectedForm.getNotSelectedCandidatesList()!=null){
			Iterator<InterviewNotSelectedTO> sendMailIterator = interviewNotSelectedForm.getNotSelectedCandidatesList().iterator();
			
			while (sendMailIterator.hasNext()) {
				InterviewNotSelectedTO sendMailDetails = (InterviewNotSelectedTO) sendMailIterator.next();
		
				if(sendMailDetails.isSendMailSelected()){
					if(interviewNotSelectedForm.getTemplateDescription()!=null){
						 template = interviewNotSelectedForm.getTemplateDescription();
					}
					if(sendMailDetails.getApplicationNo()!= null){
						template = template.replace(CMSConstants.TEMPLATE_APPLICATION_NO, sendMailDetails.getApplicationNo());
					}	
					if(sendMailDetails.getApplicantName()!= null){
						template = template.replace(CMSConstants.TEMPLATE_APPLICANT_NAME, sendMailDetails.getApplicantName());
					}
					if(sendMailDetails.getInterviewType()!= null){
						template = template.replace(CMSConstants.TEMPLATE_INTERVIEW_TYPE, sendMailDetails.getInterviewType());
					}
					if(sendMailDetails.getEmail()!= null){
						toAddress = sendMailDetails.getEmail();
					}	
					
					MailTO mailto = new MailTO();
					mailto.setFromAddress(fromaddress);
					mailto.setFromName(fromname);
					mailto.setToAddress(toAddress);
					mailto.setMessage(template);
					mailto.setSubject(CMSConstants.TEMPLATE_INTERVIEW_MAIL_SUBJECT);
					mailSend = CommonUtil.sendMail(mailto);
					if (!mailSend) {
						String subject = "Problem occured while sending mail to "
								+ toAddress;
						String message = "There was an error while sending mail to "
								+ toAddress
								+ ", So i am afraid i couldn't send this mail.";
						MailTO errorMail = new MailTO();
						errorMail.setFromAddress(fromaddress);
						errorMail.setFromName(fromname);
						errorMail.setToAddress(fromaddress);
						errorMail.setSubject(subject);
						errorMail.setMessage(message);
						mailSend = CommonUtil.sendMail(mailto);

						mailSend = false;
					}
				}
			}
		}
		return mailSend;
	}
}
