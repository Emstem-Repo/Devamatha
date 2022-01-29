package com.kp.cms.transactionsimpl.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.chart.integrate.SimpleDataRowExpressionEvaluator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.PasswordMobileMessaging;
import com.kp.cms.bo.exam.ChallanUploadDataExam;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.admission.DDStatusForm;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.handlers.admission.UniqueIdRegistrationHandler;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.DDStatusTO;
import com.kp.cms.transactions.admission.IDDStatusTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class DDStatusTransactionImpl implements IDDStatusTransaction {
	/**
	 * Singleton object of DDStatusTransactionImpl
	 */
	private static DDStatusTransactionImpl dDStatusTransactionImpl = null;
	private static final Log log = LogFactory.getLog(DDStatusTransactionImpl.class);
	private DDStatusTransactionImpl() {
		
	}
	/**
	 * return singleton object of DDStatusTransactionImpl.
	 * @return
	 */
	public static DDStatusTransactionImpl getInstance() {
		if (dDStatusTransactionImpl == null) {
			dDStatusTransactionImpl = new DDStatusTransactionImpl();
		}
		return dDStatusTransactionImpl;
	}
	@Override
	public boolean getAlreadyEntered(String query) throws Exception {
		Session session = null;
		AdmAppln bo=null;
		boolean isAlready=false;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(AdmAppln) selectedCandidatesQuery.uniqueResult();
			if(bo!=null){
				if(bo.getRecievedDDNo()!=null && !bo.getRecievedDDNo().isEmpty()){
					isAlready=true;
				}
			}
			return isAlready;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public boolean checkStudent(String query) throws Exception {
		Session session = null;
		AdmAppln bo=null;
		boolean isAlready=false;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(AdmAppln) selectedCandidatesQuery.uniqueResult();
			if(bo!=null){
					isAlready=true;
			}
			return isAlready;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public AdmAppln updateStatus(String query,DDStatusForm dDStatusForm) throws Exception {
		Session session = null;
		AdmAppln bo=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Transaction tx=session.beginTransaction();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(AdmAppln) selectedCandidatesQuery.uniqueResult();
			if(bo!=null){
				bo.setRecievedDate(CommonUtil.ConvertStringToSQLDate(dDStatusForm.getRecievedDDDate()));
				bo.setRecievedDDNo(dDStatusForm.getRecievedDDNo());
				bo.setModifiedBy(dDStatusForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setIsDDRecieved(true);
				session.update(bo);
			}
			tx.commit();
			return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	
	
	//raghu
	
	@Override
	public boolean getAlreadyEntered1(String query) throws Exception {
		Session session = null;
		AdmAppln bo=null;
		boolean isAlready=false;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(AdmAppln) selectedCandidatesQuery.uniqueResult();
			if(bo!=null){
				if(bo.getRecievedChallanNo()!=null && !bo.getRecievedChallanNo().isEmpty()){
					isAlready=true;
				}
			}
			return isAlready;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	
	
	
	@Override
	public AdmAppln updateStatus1(String query,DDStatusForm dDStatusForm) throws Exception {
		Session session = null;
		AdmAppln bo=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Transaction tx=session.beginTransaction();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(AdmAppln) selectedCandidatesQuery.uniqueResult();
			if(bo!=null){
				bo.setRecievedChallanDate(CommonUtil.ConvertStringToSQLDate(dDStatusForm.getRecievedChallanDate()));
				bo.setRecievedChallanNo(dDStatusForm.getRecievedChallanNo());
				bo.setModifiedBy(dDStatusForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setIsChallanRecieved(true);
				session.update(bo);
			}
			tx.commit();
			return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	
	
	@Override
	public List<AdmAppln> getStudentsChallanStatusOnCourse(DDStatusForm ddForm) throws Exception {
		Session session = null;
		Query hqlQuery = null;
		List<AdmAppln> list=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			if (ddForm.getRecievedChallanDate()!=null && !ddForm.getRecievedChallanDate().isEmpty()) {
				hqlQuery = session.createQuery(" from AdmAppln a where  a.studentOnlineApplication.programTypeId="+ddForm.getProgramTypeId()+" and a.appliedYear="+Integer.parseInt(ddForm.getAcademicYear())+" and a.date=:cdate and (a.mode='Challan' or a.mode='NEFT') and (a.isChallanRecieved=0 or a.isChallanRecieved is null)");
				hqlQuery.setString("cdate",CommonUtil.ConvertStringToSQLDate(ddForm.getRecievedChallanDate()).toString());
			}else {
				hqlQuery=session.createQuery(" from AdmAppln a where  a.studentOnlineApplication.programTypeId="+ddForm.getProgramTypeId()+" and a.appliedYear="+Integer.parseInt(ddForm.getAcademicYear())+" and (a.mode='Challan' or a.mode='NEFT') and (a.isChallanRecieved=0 or a.isChallanRecieved is null)");
			}
			
			//neft check
			
			
			list=hqlQuery.list();
			
			Iterator<AdmAppln> i=list.iterator();
			//while(i.hasNext()){
			//	AdmAppln a=i.next();
			//	m.put(a.getId(), a.getPersonalData().getFirstName());
				
			//}
			 
			ddForm.setStudentMap(m);
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	

	
	
	@Override
	public boolean updateChallanStatusOnCourse(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			AdmAppln bo=null;
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<DDStatusTO> itr = list.iterator();
				
					int count = 0;
					Date d1=new SimpleDateFormat("dd/MM/yyyy").parse(ddForm.getRecievedChallanDate());
					while (itr.hasNext()) {
						
						DDStatusTO  ddTo = (DDStatusTO) itr.next();
					
						
						if (ddTo.getChecked()!=null && ddTo.getChecked().equalsIgnoreCase("on")){
													
							bo =(AdmAppln) session.get(AdmAppln.class,ddTo.getAdmId() );
							if (ddForm.getRecievedChallanDate() != null) {
								bo.setRecievedChallanDate(d1);
							}else	
							bo.setRecievedChallanDate(new Date());
							bo.setRecievedChallanNo(ddTo.getEnterdChallanNo());
							bo.setModifiedBy(ddForm.getUserId());
							bo.setLastModifiedDate(new Date());
							bo.setIsChallanRecieved(true);
							
							transaction.begin();
							session.update(bo);	
							transaction.commit();
							
							//if (++count % 20 == 0) {
							//session.flush();
							//session.clear();
							//}
							isAdded = true;
							try {
								if(isAdded){
									send_sms_new(bo);
									}
								
							}
							catch (Exception e) {
								e.printStackTrace();
								if (transaction != null) {
									transaction.rollback();
								}
							}						
						}
						
					}
					
				}
					
			
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	private void send_sms_new(AdmAppln bo) throws Exception{

		Properties prop = new Properties();

		try {
			InputStream in = UniqueIdRegistrationHandler.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			
		}
		
		try {
			
			String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
			String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
			
			String temp = "";
			temp=temp+URLEncoder.encode("Dear " + bo.getPersonalData().getFirstName()+ ", your challan("+bo.getJournalNo()+")is verified successfully.Please keep this message for future reference."
										,"UTF-8");		
			
			String candidateMobileNumber = bo.getPersonalData().getMobileNo2();
			//String[] array = candidateMobileNumber.split(" ");
			//candidateMobileNumber = array[0] + array[1];
			
			PasswordMobileMessaging mob=new PasswordMobileMessaging();						
			mob.setDestinationNumber(candidateMobileNumber);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);									
			mob.setMessageBody(temp);
			
			PropertyUtil.getInstance().save(mob);
			SMSUtil s=new SMSUtil();
			SMSUtils smsUtils=new SMSUtils();

			ConverationUtil converationUtil=new ConverationUtil();
			List<SMS_Message> listSms=converationUtil.convertBotoTOPassword(s.getListOfSMSPassword());
			List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
			s.updateSMSPassword(converationUtil.convertTotoBOPassword(mobList));
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}
	

	
	
	
	@Override
	public List<AdmAppln> getStudentsDDStatusOnCourse(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<AdmAppln> list=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from AdmAppln a where  a.course.program.programType.id="+ddForm.getProgramTypeId()+" and a.appliedYear="+Integer.parseInt(ddForm.getAcademicYear())+" and a.date=:cdate and a.mode='DD' and (a.isDDRecieved=0 or a.isDDRecieved is null)");
			hqlQuery.setString("cdate",CommonUtil.ConvertStringToSQLDate(ddForm.getRecievedChallanDate()).toString());
			list=hqlQuery.list();
			
			Iterator<AdmAppln> i=list.iterator();
			//while(i.hasNext()){
			//	AdmAppln a=i.next();
			//	m.put(a.getId(), a.getPersonalData().getFirstName());
				
			//}
			 
			ddForm.setStudentMap(m);
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	

	
	
	@Override
	public boolean updateDDStatusOnCourse(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			AdmAppln bo=null;
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<DDStatusTO> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						DDStatusTO  ddTo = (DDStatusTO) itr.next();
					
						
						if (ddTo.getChecked()!=null && ddTo.getChecked().equalsIgnoreCase("on")){
							
						
						bo =(AdmAppln) session.get(AdmAppln.class,ddTo.getAdmId() );
							
							bo.setRecievedDate(new Date());
							bo.setRecievedDDNo(ddTo.getEnterdChallanNo());
							bo.setModifiedBy(ddForm.getUserId());
							bo.setLastModifiedDate(new Date());
							bo.setIsDDRecieved(true);
							
						
						transaction.begin();
						session.update(bo);	
						transaction.commit();
						
						//if (++count % 20 == 0) {
						//session.flush();
						//session.clear();
						//}
						isAdded = true;
						
						}
						
					}
					
				}
					
			
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	
	
	public List<AdmAppln> getStudentsChallanDtailsOnDate(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<AdmAppln> list=null;
		
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("	from AdmAppln a where   a.appliedYear=:cyear  and (a.mode='Challan' or a.mode='NEFT') and (a.isChallanRecieved=0 or a.isChallanRecieved is null) "+
			" and a.journalNo in ( select cd.challanNo from ChallanUploadData cd where cd.year=:cyear and cd.challanDate=:cdate)");
			//neft check
			
			hqlQuery.setString("cyear",ddForm.getAcademicYear());		
			hqlQuery.setString("cdate",CommonUtil.ConvertStringToSQLDate(ddForm.getRecievedChallanDate()).toString());
			list=hqlQuery.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	
	@Override
	public boolean updateChallanUploadProcess(DDStatusForm ddForm)	throws Exception {
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			AdmAppln bo=null;

			if (list != null && !list.isEmpty()) {

				Iterator<DDStatusTO> itr = list.iterator();				
				int count = 0;

				while (itr.hasNext()) {

					DDStatusTO  ddTo = (DDStatusTO) itr.next();

					bo =(AdmAppln) session.get(AdmAppln.class,ddTo.getAdmId() );

					bo.setRecievedChallanDate(new Date());
					bo.setRecievedChallanNo(ddTo.getEnterdChallanNo());
					bo.setModifiedBy(ddForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setIsChallanRecieved(true);
							
					transaction.begin();
					session.update(bo);	
					transaction.commit();
												
					/*if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}*/						
					isAdded = true;
					try {
						if(isAdded){
							send_sms_new(bo);
						}							
					}
					catch (Exception e) {
						e.printStackTrace();
						if (transaction != null) {
							transaction.rollback();
						}
					}					
						
				}										
			}
					
			
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	public Integer ChallanVerifiedCount(DDStatusForm ddform) throws Exception
	{
		Session session=null;
		Integer count;
		try
		{
			session=HibernateUtil.getSession();
			String s1="select count(a.id) from AdmAppln a where a.date=:challandate and a.isChallanRecieved=1";
			Query query=session.createQuery(s1);
			query.setString("challandate", CommonUtil.ConvertStringToSQLDate(ddform.getRecievedChallanDate()).toString());
			Long count1=(Long)query.uniqueResult();
			count=(int)(long)count1;
		}
		catch(Exception e)
		{
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		return count;
	}
	
	public Integer ChallanNotVerifiedCount(DDStatusForm ddform) throws Exception
	{
		Session session=null;
		Integer count;
		try
		{
			session=HibernateUtil.getSession();
			String s1="select count(a.id) from AdmAppln a where a.date=:challandate and a.isChallanRecieved=0";
			Query query=session.createQuery(s1);
			query.setString("challandate", CommonUtil.ConvertStringToSQLDate(ddform.getRecievedChallanDate()).toString());
			Long count1=(Long)query.uniqueResult();
			count=(int)(long)count1;
			
		}
		catch(Exception e)
		{
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		return count;
	}
	
	@Override
	public List<ExamRegularApplication> getStudentsChallanStatusOnCourseForExam(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<ExamRegularApplication> list=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			//Query hqlQuery = session.createQuery(" from AdmAppln a where  a.course.program.programType.id="+ddForm.getProgramTypeId()+" and a.appliedYear="+Integer.parseInt(ddForm.getAcademicYear())+" and a.date=:cdate and a.mode='CHALLAN' and (a.isChallanRecieved=0 or a.isChallanRecieved is null)");
			Query hqlQuery = session.createQuery("from ExamRegularApplication e where  e.classes.id="+Integer.parseInt(ddForm.getClasses())+" and e.exam.id="+ddForm.getExamid()+" and e.isChallanVerified=0");
			//neft check
			list=hqlQuery.list();
			
			Iterator<ExamRegularApplication> i=list.iterator();
			//while(i.hasNext()){
			//	AdmAppln a=i.next();
			//	m.put(a.getId(), a.getPersonalData().getFirstName());
				
			//}
			 
			ddForm.setStudentMap(m);
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	@Override
	public boolean updateChallanStatusOnCourseForExam(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			ExamRegularApplication bo=null;
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<DDStatusTO> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						DDStatusTO  ddTo = (DDStatusTO) itr.next();
					
						
						if (ddTo.getChecked()!=null && ddTo.getChecked().equalsIgnoreCase("on")){
							
						
						bo =(ExamRegularApplication) session.get(ExamRegularApplication.class,ddTo.getAdmId() );
						
							bo.setModifiedBy(ddForm.getUserId());
							bo.setLastModifiedDate(new Date());
							bo.setIsChallanVerified(true);
							
						
						transaction.begin();
						session.update(bo);	
						transaction.commit();
						
						//if (++count % 20 == 0) {
						//session.flush();
						//session.clear();
						//}
						isAdded = true;
						
						}
						
					}
					
				}
					
			
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	public List<ExamRegularApplication> getStudentsChallanDtailsOnDateForExam(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<ExamRegularApplication> list=null;
		
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("	from ExamRegularApplication e where e.isChallanVerified=0 "+
			" and e.challanNo in ( select cd.challanNo from ChallanUploadDataExam cd where cd.challanDate=:cdate)");
			//neft check		
			hqlQuery.setString("cdate",CommonUtil.ConvertStringToSQLDate(ddForm.getRecievedChallanDate()).toString());
			list=hqlQuery.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	public boolean updateChallanUploadProcessForExam(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		SessionFactory sessionFactory = null;
		try {
			
			session = HibernateUtil.getSession();
			session.close();
			sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			ExamRegularApplication bo=null;
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<DDStatusTO> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						DDStatusTO  ddTo = (DDStatusTO) itr.next();
					
						bo =(ExamRegularApplication) session.get(ExamRegularApplication.class,ddTo.getStuRegAppId() );
							
							bo.setModifiedBy(ddForm.getUserId());
							bo.setLastModifiedDate(new Date());
							bo.setIsChallanVerified(true);
							//bo.setAmount(new BigDecimal( ddTo.getTotal()));
							
						
						transaction.begin();
						session.update(bo);	
						
						
						if (++count % 20 == 0) {
						session.flush();
						session.clear();
						}
						
						
						
						
					}
					
					transaction.commit();
					isAdded = true;
				}
					
			
		} catch (Exception exception) {
			exception.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			session.close();
			sessionFactory.close();

		} finally {
			if (session != null) {
				//session.flush();
				session.close();
				sessionFactory.close();
				 
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;
		
	}
	
	@Override
	public List<ExamSupplementaryImprovementApplicationBO> getSupplementaryStudentWise(ExamRegularApplication examRegApp) throws Exception {
		Session session = null;
		List<ExamSupplementaryImprovementApplicationBO> list=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from ExamSupplementaryImprovementApplicationBO supplBo where supplBo.examId="+examRegApp.getExam().getId()+
					" and supplBo.studentId="+examRegApp.getStudent().getId()+" and supplBo.classes.id="+examRegApp.getClasses().getId());
			list=hqlQuery.list();
			
			//while(i.hasNext()){
			//	AdmAppln a=i.next();
			//	m.put(a.getId(), a.getPersonalData().getFirstName());
				
			//}
			 
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	
	@Override
	public List<ExamSupplementaryImprovementApplicationBO> getSupplementaryFees(ExamRegularApplication examRegApp) throws Exception {
		Session session = null;
		List<ExamSupplementaryImprovementApplicationBO> list=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from SupplementaryFees supplBo where suplBo.examDefinitionBO.id="+examRegApp.getExam().getId()+
					" and suplBo.studentId="+examRegApp.getStudent().getId()+" and suplBo.classes.id="+examRegApp.getClasses().getId());
			list=hqlQuery.list();
			
			//while(i.hasNext()){
			//	AdmAppln a=i.next();
			//	m.put(a.getId(), a.getPersonalData().getFirstName());
				
			//}
			 
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	@Override
	public Double getFeesUpload(ExamRegularApplication examRegApp) throws Exception {
		Session session = null;
		Double fees=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("select ch.amount from ChallanUploadDataExam ch where ch.challanNo='"+examRegApp.getChallanNo()+"'");
			fees=(Double)hqlQuery.uniqueResult();
			
			 
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return fees;

		
		
	}
	
	public List<ExamRevaluationApp> getStudentsChallanStatusOnCourseForRevaluation(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<ExamRevaluationApp> list=null;
		Map<Integer,String> m=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String query = "from ExamRevaluationApp e where  e.classes.id="+Integer.parseInt(ddForm.getClasses())+" and e.exam.id="+ddForm.getExamid()+" and e.isChallanVerified=0";
			if(ddForm.getFlagType()!=null && ddForm.getFlagType().equalsIgnoreCase("isRevaluation"))
				query += "and e.isRevaluation=1";
			else if(ddForm.getFlagType()!=null && ddForm.getFlagType().equalsIgnoreCase("isScrutiny"))
				query += "and e.isScrutiny=1";
			else{
				query += "and e.isChallengeValuation=1";
			}
			
			Query hqlQuery = session.createQuery(query);
			list=hqlQuery.list();
			
			Iterator<ExamRevaluationApp> i=list.iterator();
			
			 
			ddForm.setStudentMap(m);
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	public boolean updateChallanStatusOnCourseForRevaluation(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		List<String> mobNoList =new ArrayList<String>();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			ExamRevaluationApp bo=null;
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<DDStatusTO> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						DDStatusTO  ddTo = (DDStatusTO) itr.next();
					
						
						if (ddTo.getChecked()!=null && ddTo.getChecked().equalsIgnoreCase("on")){
							
						
						bo =(ExamRevaluationApp) session.get(ExamRevaluationApp.class,ddTo.getAdmId() );
						
							bo.setModifiedBy(ddForm.getUserId());
							bo.setLastModifiedDate(new Date());
							bo.setIsChallanVerified(true);
							
						
						transaction.begin();
						session.update(bo);	
						transaction.commit();
						isAdded = true;
						if(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2().equalsIgnoreCase(""))
							mobNoList.add(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2());
						else if(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo3()!=null && !bo.getStudent().getAdmAppln().getPersonalData().getMobileNo3().equalsIgnoreCase(""))
							mobNoList.add(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo3());
						
						}
						
					}
					
				}
					
			
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}

		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		
		ddForm.setMobNoList(mobNoList);
		return isAdded;
		
	}
	
	public List<ExamRevaluationApp> getStudentsChallanDtailsOnDateForRevaluation(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<ExamRevaluationApp> list=null;
		
		try{
			session = HibernateUtil.getSession();
			String query = "	from ExamRevaluationApp e where e.isChallanVerified=0 "+
			" and e.challanNo in ( select cd.challanNo from ChallanUploadDataExam cd where cd.challanDate=:cdate)";
			if(ddForm.getFlagType()!=null && ddForm.getFlagType().equalsIgnoreCase("isRevaluation"))
				query += "and e.isRevaluation=1";
			else if(ddForm.getFlagType()!=null && ddForm.getFlagType().equalsIgnoreCase("isScrutiny"))
				query += "and e.isScrutiny=1";
			else{
				query += "and e.isChallengeValuation=1";
			}
			Query hqlQuery = session.createQuery(query);

			//neft check		
			hqlQuery.setString("cdate",CommonUtil.ConvertStringToSQLDate(ddForm.getRecievedChallanDate()).toString());
			list=hqlQuery.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	public boolean updateChallanUploadProcessForRevaluation(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub

		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		SessionFactory sessionFactory = null;
		List<String> mobNoList =new ArrayList<String>();


		try {

			session = HibernateUtil.getSession();
			sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			ExamRevaluationApp bo=null;

			if (list != null && !list.isEmpty()) {

				Iterator<DDStatusTO> itr = list.iterator();

				int count = 0;

				while (itr.hasNext()) {

					DDStatusTO  ddTo = (DDStatusTO) itr.next();

					bo =(ExamRevaluationApp) session.get(ExamRevaluationApp.class,ddTo.getStuRegAppId() );

					bo.setModifiedBy(ddForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setIsChallanVerified(true);
					if(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2().equalsIgnoreCase(""))
						mobNoList.add(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2());
					else if(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo3()!=null && !bo.getStudent().getAdmAppln().getPersonalData().getMobileNo3().equalsIgnoreCase(""))
						mobNoList.add(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo3());

					transaction.begin();
					session.update(bo);	


					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}




				}

				transaction.commit();
				isAdded = true;
				ddForm.setMobNoList(mobNoList);

			}


		} catch (Exception exception) {
			exception.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			session.close();
			sessionFactory.close();

		} finally {
			if (session != null) {
				//session.flush();
				session.close();
				sessionFactory.close();

			}
		}
		log.info("end of addSubjectGroups method in SubjectGroupDetailsTransactionImpl class.");
		return isAdded;

	}
	
	public List<FeePayment> getStudentsChallanDtailsOnDateForFee(DDStatusForm ddForm) throws Exception {
		Session session = null;
		List<FeePayment> list=null;
		
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("	from FeePayment f where f.paymentVerified=0 "+
			" and f.billNo in ( select cd.challanNo from ChallanUploadDataFee cd where cd.challanDate=:cdate)");
			//neft check		
			hqlQuery.setString("cdate",CommonUtil.ConvertStringToSQLDate(ddForm.getRecievedChallanDate()).toString());
			list=hqlQuery.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return list;

		
		
	}
	
	public boolean updateChallanUploadProcessForFee(DDStatusForm ddForm)	throws Exception {
		// TODO Auto-generated method stub
		
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		SessionFactory sessionFactory = null;
		try {
			
			session = HibernateUtil.getSession();
			session.close();
			sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			List<DDStatusTO> list = ddForm.getDdStatusList();
			FeePayment bo=null;
			
			if (list != null && !list.isEmpty()) {
				
					Iterator<DDStatusTO> itr = list.iterator();
				
					int count = 0;
					
					while (itr.hasNext()) {
						
						DDStatusTO  ddTo = (DDStatusTO) itr.next();
					
						bo =(FeePayment) session.get(FeePayment.class,ddTo.getStuFeePaymentId() );
						bo.setPaymentVerified(true);
						
						transaction.begin();
						session.update(bo);	
						
						if (++count % 20 == 0) {
						session.flush();
						session.clear();
						}
					}
					transaction.commit();
					isAdded = true;
				}
		} catch (Exception exception) {
			exception.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			session.close();
			sessionFactory.close();

		} finally {
			if (session != null) {
				//session.flush();
				session.close();
				sessionFactory.close();
				 
			}
		}
		log.info("end of updateChallanUploadProcessForFee method in DDStatusTransactionImpl class.");
		return isAdded;
		
	}
}
