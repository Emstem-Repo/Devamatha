package com.kp.cms.utilities;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.jms.MailTO;

public class StudentSendingMailJob implements Job{
	private static final Log log = LogFactory.getLog(StudentSendingMailJob.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into StudentSendingMailJob"+Calendar.getInstance().getTimeInMillis());
		try {
			List<CandidatePGIDetails> mailIds = AdmissionFormTransactionImpl.getInstance().getStudentMails();
			if(mailIds != null){
				Iterator<CandidatePGIDetails> iterator = mailIds.iterator();
				TemplateHandler temphandle=TemplateHandler.getInstance();
				List<GroupTemplate> list= temphandle.getDuplicateCheckList("Online Application Remainder Mail");
				if(list != null && !list.isEmpty()) {
					while (iterator.hasNext()) {
						CandidatePGIDetails candidatePGIDetails = (CandidatePGIDetails) iterator.next();
						if(candidatePGIDetails.getEmail() != null && !candidatePGIDetails.getEmail().trim().isEmpty()){
							String desc ="";
							if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
								desc = list.get(0).getTemplateDescription();
							}
							desc = desc.replace("[Applicant_Name]", candidatePGIDetails.getCandidateName());
							desc = desc.replace("[COURSE]", candidatePGIDetails.getCourse().getName());
							desc = desc.replace("[Transaction_Date]", CommonUtil.getStringDate(candidatePGIDetails.getTxnDate()));
							desc = desc.replace("[Resident_Category]", candidatePGIDetails.getResidentCategory().getName());
							desc = desc.replace("[Candidate_Ref_number]", candidatePGIDetails.getCandidateRefNo());
							desc = desc.replace("[DOB]", CommonUtil.getStringDate(candidatePGIDetails.getCandidateDob()));
						
							if(CommonUtil.validateEmailID(candidatePGIDetails.getEmail())){
								MailTO mailTO = new MailTO();
								mailTO.setFromName("ChristUniversity");
								mailTO.setFromAddress("appadmin@christuniversity.in");
								mailTO.setMessage(desc);
								mailTO.setSubject("Applicaion Submission Reminder - Christ University");
								mailTO.setToAddress(candidatePGIDetails.getEmail());
								CommonUtil.sendMail(mailTO);
								int count = 0;
								if(candidatePGIDetails.getMailCount() != null){
									count = candidatePGIDetails.getMailCount();
								}
								count = count + 1;
								candidatePGIDetails.setMailCount(count);
								PropertyUtil.getInstance().update(candidatePGIDetails);
								// Set to true if no errors encountered
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error in StudentSendingMailJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from StudentSendingMailJob"+Calendar.getInstance().getTimeInMillis());
	}

}
