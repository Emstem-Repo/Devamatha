package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdMailBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.jms.MailTO;

public class DocumentDueMail implements Job{
	private static final Log log = LogFactory.getLog(DocumentPendingMailJob.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into DocumentDueMailJob"+Calendar.getInstance().getTimeInMillis());
		try {
			Properties prop = new Properties();
			InputStream in = DocumentDueMail.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        try
	        {
	        	prop.load(in);
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			}
	        String adminmail=CMSConstants.MAIL_USERID;
			String officeId=prop.getProperty("knowledgepro.phd.office.mail.toaddress");
			String mode="DueMail";
			List<PhdDocumentSubmissionSchedule> mailIds = AdmissionFormTransactionImpl.getInstance().DocumentPendingMail(mode);
			if(mailIds != null){
				Iterator<PhdDocumentSubmissionSchedule> iterator = mailIds.iterator();
				TemplateHandler temphandle=TemplateHandler.getInstance();
				List<GroupTemplate> list= temphandle.getDuplicateCheckList("Phd- Document Pending Due mails");
				if(list != null && !list.isEmpty()) {
					while (iterator.hasNext()) {
						PhdDocumentSubmissionSchedule pendingDetails = (PhdDocumentSubmissionSchedule) iterator.next();
						MailTO mailto = new MailTO();
						if(pendingDetails.getStudentId().getAdmAppln().getPersonalData().getEmail() != null && !pendingDetails.getStudentId().getAdmAppln().getPersonalData().getEmail().trim().isEmpty()){
						PhdMailBo mail=	new PhdMailBo(pendingDetails.getId(),pendingDetails.getStudentId(),pendingDetails.getDocumentId(),new Date(),pendingDetails.getAssignDate(),"Document Due Mail",pendingDetails.getCreatedBy(),pendingDetails.getCreatedDate());					
						PropertyUtil.getInstance().save(mail);
						String desc ="";
						if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
							desc = list.get(0).getTemplateDescription();
						}
						desc = desc.replace("[CERTIFICATE_NAMES]", pendingDetails.getDocumentId().getDocumentName());
						desc = desc.replace("[RegisterNo]", pendingDetails.getStudentId().getRegisterNo());
						desc = desc.replace("[NAME]", pendingDetails.getStudentId().getAdmAppln().getPersonalData().getFirstName());
						desc = desc.replace("[BATCH]", Integer.toString(pendingDetails.getStudentId().getAdmAppln().getAppliedYear()));
						desc = desc.replace("[COURSE]", pendingDetails.getCourseId().getName());
						desc = desc.replace("[SUBMISSION_DATE]", CommonUtil.getStringDate(pendingDetails.getAssignDate()));
						mailto.setFromAddress(adminmail);
						mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
						mailto.setMessage(desc);
						mailto.setSubject("Document Pending Due mails - Christ University");
						
						String mails=pendingDetails.getStudentId().getAdmAppln().getPersonalData().getEmail()+","+officeId;
						String[] mail1=mails.split(",");
						for(int i=0;i<mail1.length;i++){
						mailto.setToAddress(mail1[i]);
						CommonUtil.sendMail(mailto);
						}
					}
				  }
				}
			}
		} catch (Exception e) {
			log.error("Error in DocumentDueMailJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from DocumentDueMailJob"+Calendar.getInstance().getTimeInMillis());
	}
}
