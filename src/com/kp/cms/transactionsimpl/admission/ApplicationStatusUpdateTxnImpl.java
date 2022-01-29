package com.kp.cms.transactionsimpl.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.ApplicationStatusUpdate;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ApplicationStatusUpdateForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.transactions.admission.IApplicationStatusUpdateTxn;
import com.kp.cms.transactions.admission.IStudentSearchTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.jms.MailTO;

public class ApplicationStatusUpdateTxnImpl implements IApplicationStatusUpdateTxn{
	private static final Log log = LogFactory
	.getLog(ApplicationStatusUpdateTxnImpl.class);
	@Override
	public List<ApplicationStatus> getApplicationStatus() throws Exception {
		
			Session session=null;
			List<ApplicationStatus> applicationStatus=null;
			try{
				String query="from ApplicationStatus status where status.isActive=1";
				session=HibernateUtil.getSession();
				Query quer=session.createQuery(query);
				applicationStatus=quer.list();
			}catch(Exception e){
				log.debug("Exception" + e.getMessage());
			}
				return applicationStatus;
	}
	@Override
	public List<ApplicationStatusUpdate> getApplicationStatusUpdate()
			throws Exception {
		Session session=null;
		List<ApplicationStatusUpdate> statusUpdate=null;
		try{
			String query="from ApplicationStatusUpdate";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			statusUpdate=querry.list();
		}catch(Exception exception){
			log.error("Error during getting applicationStatusUpdate list..." , exception);
		}
		finally{
			session.flush();
		}
		return statusUpdate;
	}
	@Override
	public boolean duplicateCheck(
			ApplicationStatusUpdateForm applicationStatusUpdateForm,int applnNO)
			throws Exception {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from ApplicationStatusUpdate addUpdate where addUpdate.admApplnNO ="+applnNO+" and addUpdate.applicationStatus.id="+applicationStatusUpdateForm.getApplicationStatus();
			Query query=session.createQuery(quer);
			ApplicationStatusUpdate statusUpdate=(ApplicationStatusUpdate)query.uniqueResult();
			if(statusUpdate!=null){
				flag=true;
			}else{
				flag=false;
			}
		}catch(Exception e){
			log.error("Error during duplicate check..." , e);
		}
		return flag;
	}
	@Override
	public boolean addApplicationStatusUpdate(
			ApplicationStatusUpdate statusUpdate,ApplicationStatusUpdateForm applicationStatusUpdateForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		boolean isSent=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			AdmAppln appln=(AdmAppln)session.get(AdmAppln.class, statusUpdate.getAdmApplnNO().getId());
			ApplicationStatus status=(ApplicationStatus)session.get(ApplicationStatus.class, statusUpdate.getApplicationStatus().getId());
			appln.setAdmStatus(status.getName());
			String email=appln.getPersonalData().getEmail();
			if(email!=null){
				isSent=sendMailToStudent(status,appln.getApplnNo(),appln,applicationStatusUpdateForm);
				if(!isSent){
					applicationStatusUpdateForm.setMailNotSentId(appln.getApplnNo());
				}
				if(appln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
					String mobileNo="";
					if(appln.getPersonalData().getMobileNo1()!=null && !appln.getPersonalData().getMobileNo1().isEmpty()){
						if(appln.getPersonalData().getMobileNo1().trim().equals("0091") || appln.getPersonalData().getMobileNo1().trim().equals("+91")
								|| appln.getPersonalData().getMobileNo1().trim().equals("091") || appln.getPersonalData().getMobileNo1().trim().equals("0"))
							mobileNo = "91";
						else
							mobileNo=appln.getPersonalData().getMobileNo1();
					}else{
						mobileNo="91";
					}
					if(appln.getPersonalData().getMobileNo2()!=null && !appln.getPersonalData().getMobileNo2().isEmpty()){
						mobileNo=mobileNo+appln.getPersonalData().getMobileNo2();
					}
					//Application No Added By Manu	
					if(mobileNo.length()==12){
						ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_MISSING_DOCUMENT,Integer.toString(appln.getApplnNo()));
					}
				}
			}
			session.save(statusUpdate);
			session.update(appln);
			transaction.commit();
			flag=true;
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			flag=false;
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
	}
	@Override
	public List<ApplicationStatusUpdate> getApplicationStatusUpdateByAppNo(
			ApplicationStatusUpdateForm applicationStatusUpdateForm) throws Exception {
		Session session=null;
		List<ApplicationStatusUpdate> statusUpdate=null;
		try{
			session=HibernateUtil.getSession();
			String quer="";
			if(applicationStatusUpdateForm.getApplicationNo()!=null && !applicationStatusUpdateForm.getApplicationNo().isEmpty()){
				quer="from ApplicationStatusUpdate up where up.admApplnNO.applnNo= "+applicationStatusUpdateForm.getApplicationNo();
			}
			/*else{
				quer=" from ApplicationStatusUpdate up where up.admApplnNO.appliedYear="+applicationStatusUpdateForm.getYear();
			}*/
			 
			Query query=session.createQuery(quer);
			statusUpdate=query.list();
		}catch(Exception e){
			log.debug("Error during getting data...", e);
		}
		return statusUpdate;
	}
	@Override
	public Map<String, Integer> getAdmApplnMap() throws Exception {
		Session session=null;
		Map<String,Integer> admApplnMap=new HashMap<String,Integer>();
		List<AdmAppln> admAppln=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from AdmAppln";
			Query query=session.createQuery(quer);
			admAppln=query.list();
			Iterator<AdmAppln> itr=admAppln.iterator();
			while (itr.hasNext()) {
				AdmAppln admAppln1 = (AdmAppln) itr.next();
				if(admAppln1.getApplnNo()!=0)
					admApplnMap.put(String.valueOf(admAppln1.getApplnNo()),admAppln1.getId());
			}
		}catch(Exception e){
			log.debug("Error during getting data...", e);
		}
		return admApplnMap;
	}
	@Override
	public int getAdmApplnId(String applicationNo) throws Exception {
		Session session=null;
		int admApplnId=0;
		try{
			session=HibernateUtil.getSession();
			String quer="select ap.id from AdmAppln ap where (ap.isSelected=false or ap.isSelected=null) " +
					" and (ap.isApproved=false or ap.isApproved=null) and (ap.isCancelled=false or ap.isCancelled=null) and (ap.isFinalMeritApproved=false or ap.isFinalMeritApproved=null)" +
					" and ap.applnNo="+applicationNo;
			Query query=session.createQuery(quer);
			admApplnId=(Integer)query.uniqueResult();
			
		}catch(Exception e){
			log.debug("Error during getting data...", e);
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return admApplnId;
	}
	@Override
	public Map<String, String> getApplicationStatusMap() throws Exception {
		Session session=null;
		List<ApplicationStatus> list=null;
		Map<String,String> statusMap=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" from ApplicationStatus status where status.isActive=true");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<ApplicationStatus> iterator=list.iterator();
			while(iterator.hasNext()){
				ApplicationStatus applnStatus=iterator.next();
				if(applnStatus.getId()>0){
					statusMap.put(applnStatus.getShortName(), String.valueOf(applnStatus.getId()));
				}
			}
		}
		
		return statusMap;
		
	}
	@Override
	public boolean saveApplicationStatusUpdate(
			List<ApplicationStatusUpdate> list,ApplicationStatusUpdateForm applicationStatusUpdateForm) throws Exception {
		int count=0;
		Session session=null;
		Transaction transaction=null;
		List<Integer> mailNotSentIds=new ArrayList<Integer>();
		boolean flag=false;
		boolean isSent=false;
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(list!=null){
				Iterator<ApplicationStatusUpdate> iterator=list.iterator();
				while(iterator.hasNext()){
					ApplicationStatusUpdate statusUpdate=iterator.next();
					AdmAppln appln=(AdmAppln)session.get(AdmAppln.class, statusUpdate.getAdmApplnNO().getId());
					ApplicationStatus status=(ApplicationStatus)session.get(ApplicationStatus.class, statusUpdate.getApplicationStatus().getId());
					appln.setAdmStatus(status.getName());
					String email=appln.getPersonalData().getEmail();
					if(email!=null){
						isSent=sendMailToStudent(status,appln.getApplnNo(),appln,applicationStatusUpdateForm);
						if(!isSent){
							mailNotSentIds.add(appln.getApplnNo());
						}
						if(appln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
							String mobileNo="";
							if(appln.getPersonalData().getMobileNo1()!=null && !appln.getPersonalData().getMobileNo1().isEmpty()){
								if(appln.getPersonalData().getMobileNo1().trim().equals("0091") || appln.getPersonalData().getMobileNo1().trim().equals("+91")
										|| appln.getPersonalData().getMobileNo1().trim().equals("091") || appln.getPersonalData().getMobileNo1().trim().equals("0"))
									mobileNo = "91";
								else
									mobileNo=appln.getPersonalData().getMobileNo1();
							}else{
								mobileNo="91";
							}
							if(appln.getPersonalData().getMobileNo2()!=null && !appln.getPersonalData().getMobileNo2().isEmpty()){
								mobileNo=mobileNo+appln.getPersonalData().getMobileNo2();
							}
							//Application No Added By Manu	
							if(mobileNo.length()==12){
								ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_MISSING_DOCUMENT,Integer.toString(appln.getApplnNo()));
							}
						}
					}
					if(statusUpdate!=null){
						session.saveOrUpdate(statusUpdate);
						session.update(appln);
						flag=true;
					}
					if(++count%20==0){
						session.flush();
						session.clear();
					}
				}
				applicationStatusUpdateForm.setMailNotSentIds(mailNotSentIds);
				transaction.commit();
			}
			
		} catch (Exception e) {
			if(transaction!=null)
			transaction.rollback();
			flag=false;
			log.debug("Error during saving data...", e);
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	public boolean sendMailToStudent(ApplicationStatus status,int applnNO,AdmAppln appln,ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception {
		boolean sent=false;
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_APPLICATION_STATUS_MAIL);
		if(list != null && !list.isEmpty()) {

			String desc = list.get(0).getTemplateDescription();
			PersonalData pData=appln.getPersonalData();
			//send mail to applicant
				if(pData!=null){
					if(pData.getEmail()!=null && !pData.getEmail().isEmpty()){
						//replace dyna data
						String subject= status.getShortName();
						String statusName= status.getName();
						String message =desc;
						String date=CommonUtil.formatSqlDate1(pData.getDateOfBirth().toString());
						message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,pData.getFirstName());
						message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,String.valueOf(applnNO));
						message = message.replace(CMSConstants.TEMPLATE_DOB,date);
						message = message.replace(CMSConstants.TEMPLATE_COURSE,appln.getCourse().getName());
						message=  message.replace(CMSConstants.APPLICATION_STATUS,statusName);
						message=  message.replace(CMSConstants.TEMP_PROGRAM,appln.getCourse().getProgram().getName());
//						send mail
						MailTO mailTo=sendMail(pData.getEmail(),subject,message);
						if(mailTo!=null){
							sent=CommonUtil.sendMail(mailTo);
							if(!sent){
								applicationStatusUpdateForm.setReason(" because of SMTP server not connected");
							}
						}else
							return sent;
					}
				}
		} else{
			applicationStatusUpdateForm.setReason(" because of Group Template not defined:");
		}
		return sent;
	}
	public MailTO sendMail(String email, String sub, String message)throws Exception {
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return null;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return null;
		}
//			String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
			String adminmail=CMSConstants.MAIL_USERID;
			String toAddress=email;
			// MAIL TO CONSTRUCTION
			String subject=sub;
			String msg=message;
		
			MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
		return mailto;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IApplicationStatusUpdateTxn#checkIfCardGeneratedForStudent(java.lang.String)
	 */
	@Override
	public boolean checkIfCardGeneratedForStudent(String applicationNo) throws Exception {
		Session session=null;
		boolean isCardGenerated=false;
		String[] selectedCandidatesList=new String[1];
		try{
			session=HibernateUtil.getSession();
			String quer=" from InterviewSelected i where i.admAppln.applnNo="+applicationNo;
			Query query=session.createQuery(quer);
			List<InterviewSelected> interviewSelectedList=query.list();
			if(interviewSelectedList!=null && !interviewSelectedList.isEmpty()){
				for (InterviewSelected interviewSelected : interviewSelectedList) {
					if(interviewSelected.getIsCardGenerated()){
						isCardGenerated=true;
						break;
					}
				}
				if(!isCardGenerated){
					int i=0;
					for (InterviewSelected interviewSelected : interviewSelectedList) {
						if(interviewSelected.getInterviewProgramCourse()!=null && interviewSelected.getInterviewProgramCourse().getSequence().equalsIgnoreCase("1")
								&& !interviewSelected.getIsCardGenerated()){
							 selectedCandidatesList[i]=String.valueOf(interviewSelected.getId());
							 break;
						}
					}
					IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
					studentSearchTransactionImpl.removeSelectedCandidate(selectedCandidatesList);
				}
			}
		}catch(Exception e){
			log.debug("Error during getting data...", e);
			throw new ApplicationException(e);
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return isCardGenerated;
	}

}
