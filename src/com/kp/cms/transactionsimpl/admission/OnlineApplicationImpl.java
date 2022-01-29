package com.kp.cms.transactionsimpl.admission;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.bo.admin.StudentSupportRequestBo;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.bo.admission.PrerequisitsYearMonth;
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.constants.KPPropertiesConfiguration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.transactions.admission.IOnlineApplicationTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.kp.cms.utilities.jms.MailTO;

/**
 * 
 * 
 * DAO Class for AdmissionFom Action
 * 
 */
@SuppressWarnings({ "unchecked", "deprecation","rawtypes" })
public class OnlineApplicationImpl implements IOnlineApplicationTxn {

	private static final Log log = LogFactory
			.getLog(OnlineApplicationImpl.class);
	
	private static volatile OnlineApplicationImpl instance=null;
	
	public static IOnlineApplicationTxn getInstance() {
		// TODO Auto-generated method stub
		if(instance==null)
			instance=new OnlineApplicationImpl();
		return instance;
	}

public boolean submitBasicPage(Student std,OnlineApplicationForm admForm) throws Exception{
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(std);
			admForm.setAdmApplnId(String.valueOf(std.getAdmAppln().getId()));
			txn.commit();
			//session.flush();
			//session.close();
			
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................submitBasicPage.........."+ e.getCause().toString());

			throw new BusinessException(e);
		} catch (Exception e) {

			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................submitBasicPage.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		finally{
			session.flush();
			session.close();
		}
		return result;
	}

 public boolean submitCurrentPageDetails(OnlineApplicationForm admForm) throws Exception
 {
	 boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session
			.createQuery("from AdmAppln a where a.isDraftMode=1 and a.isDraftCancelled=0 and a.studentOnlineApplication.id=:UniqueId and a.id=:ApplicationId ");
			query.setInteger("UniqueId", Integer.parseInt(admForm.getUniqueId()));
			query.setInteger("ApplicationId", Integer.parseInt(admForm.getAdmApplnId()));
			AdmAppln appln = (AdmAppln) query.uniqueResult();
			txn = session.beginTransaction();
			appln.setCurrentPageName(admForm.getCurrentPageSaveDatabase());
			session.saveOrUpdate(appln);
			txn.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................submitCurrentPageDetails.........."+ e.getCause().toString());
			throw new BusinessException(e);
		} catch (Exception e) {

			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................submitCurrentPageDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		finally{
			//session.flush();
			session.close();
		}
		return result;
 }
	
	public List<AdmAppln> getApplicantSavedDetails(OnlineApplicationForm admForm) throws Exception {
		Session session = null;
		List<AdmAppln> appln=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AdmAppln a where a.isDraftMode=1 and a.isDraftCancelled=0  and a.studentOnlineApplication.id=:UniqueId order by a.createdDate desc");
			query.setInteger("UniqueId", Integer.parseInt(admForm.getUniqueId()));
			//query.setString("mode", admForm.getMode());
			appln=query.list();
			return appln;
		} catch (Exception e) {
			if (session != null) {
				//session.flush();
			}
			System.out.println("Error during .................................getApplicantSavedDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
	
	}
	public AdmAppln getAppliedApplnDetails(OnlineApplicationForm admForm) throws Exception {
		Session session = null;
		AdmAppln appln=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AdmAppln a where  a.isDraftCancelled=0 " +
							"and a.studentOnlineApplication.id=:UniqueId and a.id=:ApplicationId");
			query.setInteger("UniqueId", Integer.parseInt(admForm.getUniqueId()));
			query.setInteger("ApplicationId", Integer.parseInt(admForm.getAdmApplnId()));
			//query.setString("mode", admForm.getMode());
			appln=(AdmAppln) query.uniqueResult();
			return appln;
		} catch (Exception e) {
			if (session != null) {
				//session.flush();
			}
			System.out.println("Error during .................................getAppliedApplnDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
	
	}
	
	public Student getStudentDetailsFromAdmAppln(int applnId)throws Exception{
		Session session = null;
		Student st=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Student s where s.admAppln.id=:ApplicationId and s.isActive=1 ");
			query.setInteger("ApplicationId", applnId);
			st=(Student) query.uniqueResult();
			return st;
		} catch (Exception e) {
			if (session != null) {
				//session.flush();
			}
			System.out.println("Error during .................................getStudentDetailsFromAdmAppln.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
	}
	
	public boolean createNewApplicant(Student admBO, OnlineApplicationForm admForm,String saveMode) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			admBO.setIsActive(true);
			/*if(saveMode!=null && saveMode.equalsIgnoreCase("Submit")){
				admBO.getAdmAppln().setCurrentPageName("details");
			}
			else
			{*/
				//admBO.getAdmAppln().setCurrentPageName(admForm.getCurrentPageNo());
				admBO.getAdmAppln().setCurrentPageName(admForm.getDisplayPage());
			//}
				
			
				//this code for save  application wtih edit
			if(admForm.isOnlineApply()){
				admBO.getAdmAppln().setIsDraftMode(true);
				admBO.getAdmAppln().setIsDraftCancelled(false);
			}/*else{
				if (saveMode.equalsIgnoreCase("Draft")) {
					admBO.getAdmAppln().setIsDraftMode(true);
					admBO.getAdmAppln().setIsDraftCancelled(false);
				}else{
					
					admBO.getAdmAppln().setIsDraftCancelled(false);
					admBO.getAdmAppln().setAdmStatus(admForm.getAppStatusOnSubmition());
				}
			}
			*/
			
			//this code for save complete application no more edit
			if(saveMode!=null && saveMode.equalsIgnoreCase("SaveApplicationNoMoreEdit")){
				admBO.getAdmAppln().setIsDraftMode(false);
				admBO.getAdmAppln().setCurrentPageName("SaveApplicationNoMoreEdit");
		      }
			
			txn=session.beginTransaction();
			if((admForm.getAutoSave()!=null && !admForm.getAutoSave().isEmpty()) && admForm.getAutoSave().equalsIgnoreCase("autoSave")){
				//session.merge(admBO);
				session.saveOrUpdate(admBO);
			}
			else
			{
				session.saveOrUpdate(admBO);
			}
			txn.commit();
			//session.flush();
			//session.close();
			admForm.setStudentId(admBO.getId());
			admForm.getApplicantDetails().setApplnNo(admBO.getAdmAppln().getApplnNo());
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				//raghu
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
			session.close();
			}
			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................createNewApplicant.........."+ e.getCause().toString());
			throw new BusinessException(e);
		} catch (Exception e) {
			//raghu
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
		//	session.close();
			}
			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................createNewApplicant.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		finally{
			//session.flush();
			session.close();
		}
		return result;
	}
	
	public boolean savePayment(AdmAppln admAppln,OnlineApplicationForm admForm,ActionMessages errors) throws Exception
	{
		boolean result = false;
		try {
			boolean stored = this.saveChallanDetail(admAppln,admForm);
			if(stored){
				
				admForm.setCurrentPageNo("payment");
				admForm.setDisplayPage("paymentsuccess");
				
				admForm.setDataSaved(true);
				result = true;
			}else
			{
				result = false;
			}
		} catch (ConstraintViolationException e) {
			log.error("Error during updating savePayment SBI...", e);
			System.out.println("Error during .................................savePayment.........."+ e.getCause().toString());
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during updating savePayment SBI...", e);
			System.out.println("Error during .................................savePayment.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		return result;
	}
	
	
	public String saveOnlineAppNoSBI(int courseID, int appliedyear,
			boolean isOnline,AdmAppln admAppln,OnlineApplicationForm admForm, ActionMessages errors) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null,txn1=null;//transaction=null;
		String onlineFrom = null;
		String onlineTo = null;
		int onlineStart = 0;
		int onlineEnd = 0;
		Integer maxNo =null;
		int generatedInt = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn=session.beginTransaction();
			Query query = session.createQuery("select c from CourseApplicationNumber a " +
							" join a.applicationNumber c " +
							" where a.applicationNumber.year= :year and a.course.id= :courseID " +
							" and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setLockMode("c", LockMode.UPGRADE);
			ApplicationNumber appNo =(ApplicationNumber) query.uniqueResult();
		if(appNo!=null){
			if (isOnline && appNo!= null) {
				onlineFrom = appNo.getOnlineApplnNoFrom();
				onlineTo = appNo.getOnlineApplnNoTo();
				onlineStart = Integer.parseInt(onlineFrom);
				onlineEnd = Integer.parseInt(onlineTo);
				maxNo = Integer.parseInt(appNo.getCurrentOnlineApplicationNo());
			}
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
				maxNo =onlineStart;
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
				maxNo=null;
			} else {
				generatedInt = maxNo;
				generatedInt=generatedInt+1;
				maxNo=maxNo+1;
			}
			boolean isExist=false;
			
			do{
				List<AdmAppln> bos=session.createQuery("from AdmAppln a where a.applnNo="+generatedInt+" and a.appliedYear="+appliedyear).list();
				if(bos!=null && !bos.isEmpty()){
					isExist=true;
					generatedInt=generatedInt+1;
					maxNo=maxNo+1;
				}else{
					isExist=false;
				}
			}while(isExist);
			generatedNo = String.valueOf(generatedInt);
			appNo.setCurrentOnlineApplicationNo(String.valueOf(maxNo));
			session.update(appNo);
			txn.commit();
			txn1=session.beginTransaction();
			 	admAppln.setApplnNo(Integer.parseInt(generatedNo));
			 	admAppln.setCurrentPageName("payment");
			 	admAppln.setAdmStatus(admForm.getAppStatusOnSubmition());
			 	admAppln.setIsDraftCancelled(false);
			 	admAppln.setIsDraftMode(false);
				session.merge(admAppln);
			txn1.commit();
				//admForm.setStudentId(admAppln.getStudents().getId());
		//	session.flush();
			//session.close();
			
			
		}else{
			//sendMailToAdmin(admForm,appliedyear);
		//	ActionMessages message = new ActionMessages();
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL));
				}	
			}
		return generatedNo;
		} catch (Exception e) {
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				if(txn1!=null)
				txn1.rollback();
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
		//	session.close();

			}
			System.out.println("Error during .................................saveOnlineAppNoSBI.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
	}
	
	
	
	//raghu write new
	public boolean saveChallanDetail(AdmAppln admAppln,OnlineApplicationForm admForm) throws Exception {
		Session session = null;
		
		Transaction txn=null;//transaction=null;
		
		
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn=session.beginTransaction();
		
			 	admAppln.setCurrentPageName("payment");
			 	admAppln.setAdmStatus(admForm.getAppStatusOnSubmition());
			 	admAppln.setIsDraftCancelled(false);
			 	admAppln.setIsDraftMode(true);
			 	admAppln.setLastModifiedDate(new java.util.Date());

				session.merge(admAppln);
			txn.commit();
				//admForm.setStudentId(admAppln.getStudents().getId());
			//session.flush();
			//session.close();
			
		return true;
		} catch (Exception e) {
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				
				
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
		//	session.close();
			}
			System.out.println("Error during .................................saveChallanDetail.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
	}
	
	
	//raghu write new
	public boolean savePaymentSuccessPage(AdmAppln admAppln,OnlineApplicationForm admForm) throws Exception {
		Session session = null;
		
		Transaction txn=null;//transaction=null;
		
		
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn=session.beginTransaction();
		
			 	admAppln.setCurrentPageName("paymentsuccess");
			 	admAppln.setAdmStatus(admForm.getAppStatusOnSubmition());
			 	admAppln.setIsDraftCancelled(false);
			 	admAppln.setIsDraftMode(true);
			 	admAppln.setLastModifiedDate(new java.util.Date());

				session.merge(admAppln);
			txn.commit();
				//admForm.setStudentId(admAppln.getStudents().getId());
			//session.flush();
			//session.close();
			
		return true;
		} catch (Exception e) {
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				System.out.println("Error during .................................savePaymentSuccessPage.........."+ e.getCause().toString());
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
		//	session.close();
			}
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
	}
	
	//raghu write new
	
	public synchronized String saveCompleteApplicationGenerateNo(int courseID, int appliedyear,
			boolean isOnline,AdmAppln admAppln,OnlineApplicationForm admForm, ActionMessages errors) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null,txn1=null;//transaction=null;
		String onlineFrom = null;
		String onlineTo = null;
		int onlineStart = 0;
		int onlineEnd = 0;
		Integer maxNo =null;
		int generatedInt = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn=session.beginTransaction();
			Query query = session.createQuery("select c from CourseApplicationNumber a " +
							" join a.applicationNumber c " +
							" where a.applicationNumber.year= :year and a.course.id= :courseID " +
							" and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setLockMode("c", LockMode.UPGRADE);
			ApplicationNumber appNo =(ApplicationNumber) query.uniqueResult();
		if(appNo!=null){
			if (isOnline && appNo!= null) {
				onlineFrom = appNo.getOnlineApplnNoFrom();
				onlineTo = appNo.getOnlineApplnNoTo();
				onlineStart = Integer.parseInt(onlineFrom);
				onlineEnd = Integer.parseInt(onlineTo);
				maxNo = Integer.parseInt(appNo.getCurrentOnlineApplicationNo());
			}
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
				maxNo =onlineStart;
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
				maxNo=null;
			} else {
				generatedInt = maxNo;
				generatedInt=generatedInt+1;
				maxNo=maxNo+1;
			}
			boolean isExist=false;
			
			do{
				List<AdmAppln> bos=session.createQuery("from AdmAppln a where a.applnNo="+generatedInt+" and a.appliedYear="+appliedyear).list();
				if(bos!=null && !bos.isEmpty()){
					isExist=true;
					generatedInt=generatedInt+1;
					maxNo=maxNo+1;
				}else{
					isExist=false;
				}
			}while(isExist);
			generatedNo = String.valueOf(generatedInt);
			appNo.setCurrentOnlineApplicationNo(String.valueOf(maxNo));
			session.update(appNo);
			txn.commit();
			txn1=session.beginTransaction();
			 	admAppln.setApplnNo(Integer.parseInt(generatedNo));
			 	admAppln.setCurrentPageName(admForm.getDisplayPage());
			 	admAppln.setAdmStatus(admForm.getAppStatusOnSubmition());
			 	admAppln.setIsDraftCancelled(false);
			 	admAppln.setIsDraftMode(true);
				session.merge(admAppln);
			txn1.commit();
				//admForm.setStudentId(admAppln.getStudents().getId());
			
			
			
		}else{
			//sendMailToAdmin(admForm,appliedyear);
		//	ActionMessages message = new ActionMessages();
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL));
				}	
			}
		
		
		return generatedNo;
		} catch (Exception e) {
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				if(txn1!=null)
				txn1.rollback();
				generatedNo=null;
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
		//	session.close();
			}
			System.out.println("Error during .................................saveCompleteApplicationGenerateNo.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
	}
	
	
	
	
	
	public synchronized String saveCompleteApplicationGenerateNoWithNoMoreEdit(int courseID, int appliedyear,
			boolean isOnline,AdmAppln admAppln,OnlineApplicationForm admForm, ActionMessages errors) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null,txn1=null;//transaction=null;
		String onlineFrom = null;
		String onlineTo = null;
		int onlineStart = 0;
		int onlineEnd = 0;
		Integer maxNo =null;
		int generatedInt = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn=session.beginTransaction();
			Query query = session.createQuery("select c from CourseApplicationNumber a " +
							" join a.applicationNumber c " +
							" where a.applicationNumber.year= :year and a.course.id= :courseID " +
							" and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setLockMode("c", LockMode.UPGRADE);
			ApplicationNumber appNo =(ApplicationNumber) query.uniqueResult();
		if(appNo!=null){
			if (isOnline && appNo!= null) {
				onlineFrom = appNo.getOnlineApplnNoFrom();
				onlineTo = appNo.getOnlineApplnNoTo();
				onlineStart = Integer.parseInt(onlineFrom);
				onlineEnd = Integer.parseInt(onlineTo);
				maxNo = Integer.parseInt(appNo.getCurrentOnlineApplicationNo());
			}
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
				maxNo =onlineStart;
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
				maxNo=null;
			} else {
				generatedInt = maxNo;
				generatedInt=generatedInt+1;
				maxNo=maxNo+1;
			}
			boolean isExist=false;
			
			do{
				List<AdmAppln> bos=session.createQuery("from AdmAppln a where a.applnNo="+generatedInt+" and a.appliedYear="+appliedyear).list();
				if(bos!=null && !bos.isEmpty()){
					isExist=true;
					generatedInt=generatedInt+1;
					maxNo=maxNo+1;
				}else{
					isExist=false;
				}
			}while(isExist);
			generatedNo = String.valueOf(generatedInt);
			appNo.setCurrentOnlineApplicationNo(String.valueOf(maxNo));
			session.update(appNo);
			txn.commit();
			txn1=session.beginTransaction();
			 	admAppln.setApplnNo(Integer.parseInt(generatedNo));
			 	admAppln.setCurrentPageName("SaveApplicationNoMoreEdit");
			 	admAppln.setAdmStatus(admForm.getAppStatusOnSubmition());
			 	admAppln.setIsDraftCancelled(false);
			 	admAppln.setIsDraftMode(false);
				session.merge(admAppln);
				admForm.getApplicantDetails().setApplnNo(maxNo);
			txn1.commit();
				//admForm.setStudentId(admAppln.getStudents().getId());
			
			
			
		}else{
			//sendMailToAdmin(admForm,appliedyear);
		//	ActionMessages message = new ActionMessages();
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL));
				}	
			}
		
		
		return generatedNo;
		} catch (Exception e) {
			if (session.isOpen()){
				if(txn!=null)
				txn.rollback();
				if(txn1!=null)
				txn1.rollback();
				generatedNo=null;
				System.out.println("Error during .................................saveCompleteApplicationGenerateNoWithNoMoreEdit.........."+ e.getCause().toString());
			//	if(transaction!=null)
			//	transaction.rollback();
		//	session.flush();
		//	session.close();
			}
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
	}
	
	
	/*public boolean savePrerequisitesPage(OnlineApplicationForm admForm,List<CandidatePrerequisiteMarks> prerequisitesList) throws Exception{
		boolean result = false;
		Session session = null;
		Transaction txn=null;
		try {
				session = HibernateUtil.getSession();
				txn = session.beginTransaction();
				txn.begin();
				if(prerequisitesList!=null && !prerequisitesList.isEmpty()){
					for(CandidatePrerequisiteMarks bo:prerequisitesList){
						session.saveOrUpdate(bo);
					}
				}	
				txn.commit();
				//session.flush();
				//session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error during updating savePrerequisitesPage...", e);
			System.out.println("Error during savePrerequisitesPage..."+e.getCause().toString());
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during updating savePrerequisitesPage...", e);
			System.out.println("Error during savePrerequisitesPage..."+e.getCause().toString());
			throw new ApplicationException(e);
		}
		finally{
			//session.flush();
			session.close();
		}
		return result;
	}*/
	
	@Override
	public boolean persistCompleteApplicationData(Student newStudent, OnlineApplicationForm admForm)
			throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.save(newStudent);
			txn.commit();
			//session.flush();
			//session.close();
//			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			txn.rollback();
			System.out.println("Error during .................................persistCompleteApplicationData.........."+ e.getCause().toString());
			log.error("Error during saving complete application data...", e);
			System.out.println("Error during persistCompleteApplicationData..."+e.getCause().toString());
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();
			log.error("Error during saving complete application data...", e);
			System.out.println("Error during .................................persistCompleteApplicationData.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
		return result;
	}

	/*
	 * (non-Javadoc) fetches all active nationalities
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IOnlineApplicationTxn#getNationalities
	 * ()
	 */
	
	@Override
	public List<Nationality> getNationalities() throws Exception {
		Session session = null;

		try {
/*			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Nationality n where n.isActive=1");
			query.setReadOnly(true);
			List<Nationality> list = query.list();

//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting nationalities...", e);
			if(session!= null){
				//session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) get all active currency types
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IOnlineApplicationTxn#getCurrencies
	 * ()
	 */
	
	@Override
	public List<Currency> getCurrencies() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select c from Currency c where c.isActive=1");
			query.setReadOnly(true);
			List<Currency> list = query.list();

//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);
			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) fetches all active incomes
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IOnlineApplicationTxn#getIncomes()
	 */
	
	@Override
	public List<Object[]> getIncomes() throws Exception {
		Session session = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select i.id, i.incomeRange from Income i where i.isActive=1 ");
			List<Object[]> list = query.list();
			return list;
		} catch (Exception e) {
			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) get all active resident categories
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IOnlineApplicationTxn#getResidentTypes
	 * ()
	 */
	
	@Override
	public List<ResidentCategory> getResidentTypes() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ResidentCategory c where c.isActive=1");
			List<ResidentCategory> list = query.list();

//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting resident categories..." + e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}

	}
	
	
	
	

	/*
	 * (non-Javadoc) get all markcard types for course
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IOnlineApplicationTxn#getExamtypes
	 * ()
	 */
	
	public List<DocChecklist> getExamtypes(int courseId, int year)
			throws Exception {
		List<DocChecklist> docchecklist = new ArrayList<DocChecklist>();
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.year=:year and d.isActive=1 and d.docType.isActive=1");
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			List<DocChecklist> listofdocs = query.list();
			if (listofdocs != null && !listofdocs.isEmpty()) {
				Iterator<DocChecklist> itr = listofdocs.iterator();
				DocChecklist checkdocs;
				while (itr.hasNext()) {
					checkdocs = (DocChecklist) itr.next();
					if (checkdocs != null && checkdocs.getIsMarksCard()) {
						docchecklist.add(checkdocs);

					}
				}

			}

		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}

			log.error("Error during getting doc checklists...", e);
			throw new ApplicationException(e);
		}

//		session.close();
		return docchecklist;
	}

	/*
	 * (non-Javadoc) get list of docs needed for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getNeededDocumentList(java.lang.String)
	 */
	
	@Override
	public List<DocChecklist> getNeededDocumentList(String courseID)
			throws Exception {
		Session session = null;

		List<DocChecklist> listofdocs = null;
		// check consolidated needed or not
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.isActive=1");
			query.setInteger("courseId", Integer.parseInt(courseID));
			listofdocs = query.list();

//			session.close();
		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}
			log.error(
					"Error during getting needed documents for attachment...",
					e);
			throw new ApplicationException(e);
		}
		return listofdocs;
	}

	/*
	 * (non-Javadoc) fetches all active preferences for the course
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getCourseForPreference(java.lang.String)
	 */
	
	@Override
	public List<Course> getCourseForPreference(String courseId)
			throws Exception {
		Session session = null;
//		Preferences preference = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.courseByPrefCourseId from Preferences c where c.courseByCourseId.id = :courseId and c.isActive = 1");
	        query.setInteger("courseId", Integer.parseInt(courseId));
	        courseList=query.list();
			
/*Commented By Manu,Iteration is not required,directly get list of CourseByPrefCourseId()			
			Query query = session.createQuery(" from Preferences c where courseByCourseId.id = :courseId and isActive = 1");
			query.setInteger("courseId", Integer.parseInt(courseId));
			// boolean isDefaultCourseAdded = false;
			for (Iterator iterator = query.iterate(); iterator.hasNext();) {
				preference = (Preferences) iterator.next();
				// if(!isDefaultCourseAdded){
				// Course prefCourse1=preference.getCourseByCourseId();
				// courseList.add(prefCourse1);
				// isDefaultCourseAdded = true;
				// }
				Course prefCourse = preference.getCourseByPrefCourseId();
				courseList.add(prefCourse);
			}
*/
//			session.close();
			return courseList;
		} catch (Exception e) {
			if (session != null) {
				//session.flush();
			}
			log.error("Error during getting program type for preference...", e);
			System.out.println("Error during .................................persistCompleteApplicationData.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) checks appln.no exists or not for year
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * checkApplicationNoUniqueForYear(int, int)
	 */
	
	@Override
	public boolean checkApplicationNoUniqueForYear(int applnNo, int year)
			throws Exception {
		Session session = null;
		boolean unique = true;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select a.applnNo from AdmAppln a where a.applnNo= :applnNo and a.appliedYear= :appliedYear ");
			query.setInteger("applnNo", applnNo);
			query.setInteger("appliedYear", year);
			List<AdmAppln> list = query.list();
			if (list != null && !list.isEmpty()) {
				unique = false;
			}
//			session.close();
		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}
			log.error("Error during getting program type for preference...", e);
			throw new ApplicationException(e);
		}
		return unique;
	}

	/*
	 * (non-Javadoc) get latest challan no from challan-ref table
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getCurrentChallanNo()
	 */
	/*@Override
	public String getCurrentChallanNo() throws Exception {
		Session session = null;
		String refNO = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select a.currentNo from ChallanReference a where a.id=1");
			refNO = (String) query.uniqueResult();
//			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			log.error("Error during getting current challanNO...", e);
			throw new ApplicationException(e);
		}
		return refNO;
	}*/

	/*
	 * (non-Javadoc) update challan sequence
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * updateLatestChallanNo(java.lang.String)
	 */
	/*@Override
	public boolean updateLatestChallanNo(String latestRefNo) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();

			Query updateWeigh = session
					.createQuery("update ChallanReference a set a.currentNo=:latestRefNo where a.id=:id");
			updateWeigh.setString("latestRefNo", latestRefNo);
			updateWeigh.setInteger("id", CMSConstants.CHALLAN_REF_ID);
			updateWeigh.executeUpdate();
			txn.commit();
			//session.flush();
			//session.close();
//			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			txn.rollback();
			log.error("Error during updating latest challan no...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();

			log.error("Error during updating latest challan no...", e);
			throw new ApplicationException(e);
		}
		finally{
			//session.flush();
			//session.close();
		}
		return result;
	}*/

	/*
	 * (non-Javadoc) gets all pre requisites for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getCoursePrerequisites(int)
	 */
	
	/*@Override
	public List<CoursePrerequisite> getCoursePrerequisites(int courseID)
			throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CoursePrerequisite c where c.course.id= :courseID and c.isActive=1");
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			List<CoursePrerequisite> list = query.list();
//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting CoursePrerequisite...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}*/

	/*
	 * (non-Javadoc) checks appln no range for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * checkApplicationNoInRange(int, boolean, int, java.lang.String)
	 */
	
	@Override
	public boolean checkApplicationNoInRange(int applicationNumber,
			boolean onlineApply, int appliedyear, String courseId)
			throws Exception {
		Session session = null;
		boolean inRange = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setString("courseID", courseId);
			query.setReadOnly(true);
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber crsAppNo = (CourseApplicationNumber) appItr
							.next();
					if (onlineApply && crsAppNo.getApplicationNumber() != null) {
						String onlineFrom = crsAppNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						String onlineTo = crsAppNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						if (StringUtils.isNumeric(onlineFrom)
								&& StringUtils.isNumeric(onlineTo)) {
							int onlineStart = Integer.parseInt(onlineFrom);
							int onlineEnd = Integer.parseInt(onlineTo);
							if (applicationNumber >= onlineStart
									&& applicationNumber <= onlineEnd) {
								return true;
							}
						} else {
							return false;
						}
					} else if (!onlineApply
							&& crsAppNo.getApplicationNumber() != null) {
						String offlineFrom = crsAppNo.getApplicationNumber()
								.getOfflineApplnNoFrom();
						String offlineTo = crsAppNo.getApplicationNumber()
								.getOfflineApplnNoTo();
						if (offlineFrom != null
								&& !StringUtils.isEmpty(offlineFrom)
								&& offlineTo != null
								&& !StringUtils.isEmpty(offlineTo)
								&& StringUtils.isNumeric(offlineFrom)
								&& StringUtils.isNumeric(offlineTo)) {
							int offlineStart = Integer.parseInt(offlineFrom);
							int offlineEnd = Integer.parseInt(offlineTo);
							if (applicationNumber >= offlineStart
									&& applicationNumber <= offlineEnd) {
								return true;
							}
						} else {
							return false;
						}
					}
				}
			}

//			session.close();
//			sessionFactory.close();
			return inRange;
		} catch (Exception e) {
			log.error("Error during getting checkApplicationNoInRange...", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) checks appln no configured for course or not
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * checkApplicationNoConfigured(int, java.lang.String)
	 */
	
	public boolean checkApplicationNoConfigured(int appliedyear, String courseId)
			throws Exception {
		Session session = null;
		boolean inRange = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setString("courseID", courseId);
			query.setReadOnly(true);
			List<CourseApplicationNumber> list = query.list();
			if (list != null && !list.isEmpty()) {
				inRange = true;
			}
			return inRange;
		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) gets generated appln no for online
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getGeneratedOnlineAppNo(int, int, boolean)
	 */
	
	@Override
	public String getGeneratedOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline) throws Exception {
		Session session = null;
		String generatedNo = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			// get the online range for course
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}
//			session.close();
//			sessionFactory.close();
			
			return generatedNo;
		} catch (Exception e) {
			log.error("Error during auto generated online appl.no...", e);
			System.out.println("Error during .................................getGeneratedOnlineAppNo.........."+ e.getCause().toString());
			//session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) updates edit data
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * updateCompleteApplication(com.kp.cms.bo.admin.AdmAppln, boolean)
	 */
	@Override
	public boolean updateCompleteApplication(AdmAppln admBO,
			boolean admissionForm) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(admBO);
			// if admission, set student admitted
			if (admissionForm) {

				if (admBO.getIsApproved()) {
					Integer maxNo = 1;
					Query query = session
							.createQuery("select max(student.programTypeSlNo)"
									+ " from Student student "
									+ " where student.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId "
									+ " and student.admAppln.appliedYear = :year");
					query.setInteger("programTypeId", admBO.getCourse()
							.getProgram().getProgramType().getId());
					query.setInteger("year", admBO.getAppliedYear().intValue());
					if (query.uniqueResult() != null
							&& !query.uniqueResult().equals("0")) {
						maxNo = (Integer) query.uniqueResult();
						maxNo = maxNo + 1;
					}
					query = session
							.createQuery("update Student st set st.isAdmitted= :sele,st.isActive= :act, st.programTypeSlNo = :maxNo, st.isSCDataGenerated=:gene, st.isSCDataDelivered=:deli, st.isHide=:hide where st.admAppln.id= :admId");
					query.setBoolean("sele", true);
					query.setBoolean("act", true);
					query.setBoolean("gene", false);
					query.setBoolean("deli", false);
					query.setBoolean("hide", false);
					query.setInteger("maxNo", maxNo.intValue());
					query.setInteger("admId", admBO.getId());
					query.executeUpdate();
					admBO.setIsSelected(true);
					session.update(admBO);
				} else {
					Query query = session
							.createQuery("update Student st set st.isAdmitted= :sele, st.isHide=:hide where st.admAppln.id= :admId");
					query.setBoolean("sele", false);
					query.setBoolean("hide", false);
					query.setInteger("admId", admBO.getId());
					query.executeUpdate();
				}
			}
			txn.commit();
			//session.flush();
			//session.close();
			//sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during updating complete application data..." + e);
			System.out.println("Error during .................................updateCompleteApplication.........."+ e.getCause().toString());
			
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during updating complete application data..." + e);
			System.out.println("Error during .................................updateCompleteApplication.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		
		finally{
			//session.flush();
			session.close();
		}
		return result;
	}

	public int getStudentId(int appNumber, int admissionYear) throws Exception {
		Session session = null;
		Transaction transaction = null;
		int stid = 0;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("select id from Student s where s.isActive=1 and s.admAppln.applnNo= "
							+ appNumber
							+ " and s.admAppln.appliedYear= "
							+ admissionYear);
			if (query.uniqueResult() != null) {
				stid = (Integer) query.uniqueResult();
			}
		} catch (Exception e) {
			log.error("Error during getStudentId...", e);

			throw new ApplicationException(e);
		} 
		return stid;
	}

	/**
	 * This method is used to update the remarks for cancellation and isCanceled
	 * field to true.
	 */

	/*public boolean updateApplicationNumberCancel(int appNumber,
			int admissionYear, String remarks,String cancelDate,String lastModifiedBy) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			java.util.Date lastModifiedDate = new java.util.Date();
			Query query = session
					.createQuery("update AdmAppln adm set adm.isSelected= :sele, adm.isCancelled= :cancel, adm.cancelRemarks= :cancelRemarks,adm.cancelDate =:cancelDate,adm.modifiedBy = :lastModifiedBy,adm.lastModifiedDate =:lastModifiedDate where adm.applnNo= :appno and adm.appliedYear= :year ");
			query.setBoolean("sele", false);
			query.setBoolean("cancel", true);
			query.setInteger("appno", appNumber);
			query.setInteger("year", admissionYear);
			query.setString("cancelRemarks", remarks);
			query.setDate("cancelDate", CommonUtil.ConvertStringToSQLDate(cancelDate));
			query.setDate("lastModifiedDate", lastModifiedDate);
			query.setString("lastModifiedBy", lastModifiedBy);
			query.executeUpdate();
			txn.commit();
			AdmAppln admAppln=(AdmAppln)session .createQuery("from AdmAppln adm where adm.applnNo= :appno and adm.appliedYear= :year ").setInteger("appno", appNumber).setInteger("year", admissionYear).uniqueResult();
			if(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
				String mobileNo="";
				if(admAppln.getPersonalData().getMobileNo1()!=null && !admAppln.getPersonalData().getMobileNo1().isEmpty()){
					if(admAppln.getPersonalData().getMobileNo1().trim().equals("0091") || admAppln.getPersonalData().getMobileNo1().trim().equals("+91")
							|| admAppln.getPersonalData().getMobileNo1().trim().equals("091") || admAppln.getPersonalData().getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
					mobileNo=admAppln.getPersonalData().getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(admAppln.getPersonalData().getMobileNo2()!=null && !admAppln.getPersonalData().getMobileNo2().isEmpty()){
					mobileNo=mobileNo+admAppln.getPersonalData().getMobileNo2();
				}
				if(mobileNo.length()==12){
					UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_ADMISSION_CANCELLATION,admAppln);
				}
			}
			isUpdated = true;
		} catch (Exception e) {
			log.error("Error during updateApplicationNumberCancel..." + e);
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return isUpdated;
	}*/

	/*
	 * (non-Javadoc) updates student record
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * updateStudentRecord(int)
	 */
	/*public boolean updateStudentRecord(int studentId,Boolean removeRegNo) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			Student student = (Student) session.get(Student.class, studentId);
			student.setIsAdmitted(false);
			if(removeRegNo)
			student.setRegisterNo(null);
			session.update(student);
			//code added by sudhir
			StudentLogin studentLogin = (StudentLogin) session.createQuery("from StudentLogin st where st.student.id="+studentId).uniqueResult();
			if(studentLogin!=null){
				studentLogin.setIsActive(false);
				session.update(studentLogin);
			}
			List<StudentSupportRequestBo> stSupportReq=new ArrayList<StudentSupportRequestBo>();
			stSupportReq =  session.createQuery("from StudentSupportRequestBo st where st.studentId.id="+studentId).list();
			if(stSupportReq!=null && !stSupportReq.isEmpty()){
			Iterator<StudentSupportRequestBo> itr = stSupportReq.iterator();
			while (itr.hasNext()) {
				StudentSupportRequestBo crsAppNo = (StudentSupportRequestBo) itr
						.next();
				if (crsAppNo != null) {
			
					crsAppNo.setIsActive(false);
					session.update(crsAppNo);
				}
			}
			}
			//
			txn.commit();
			isUpdated = true;
		} catch (Exception e) {
			log.error("Error during updateStudentRecord..." + e);
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}*/
	
	
	
	/*public boolean removeSupportRequestBo(int studentId) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		List<StudentSupportRequestBo> stSupportReq=new ArrayList<StudentSupportRequestBo>();
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			Student student = (Student) session.get(Student.class, studentId);
			student.setIsAdmitted(false);
			StudentLogin studentLogin = (StudentLogin) session.createQuery("from StudentLogin st where st.student.id="+studentId).uniqueResult();
			if(studentLogin!=null){
				studentLogin.setIsActive(false);
				session.update(studentLogin);
			}
			stSupportReq =  session.createQuery("from StudentSupportRequestBo st where st.studentId.id="+studentId).list();
			if(stSupportReq!=null && !stSupportReq.isEmpty()){
			Iterator<StudentSupportRequestBo> itr = stSupportReq.iterator();
			while (itr.hasNext()) {
				StudentSupportRequestBo crsAppNo = (StudentSupportRequestBo) itr
						.next();
				if (crsAppNo != null) {
			
					crsAppNo.setIsActive(false);
				session.update(crsAppNo);
				}
			}
			}
			//
			txn.commit();
			isUpdated = true;
		} catch (Exception e) {
			log.error("Error during updateStudentRecord..." + e);
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}*/

	/*
	 * (non-Javadoc) fetches applicant details for appln no.
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getApplicantDetails(java.lang.String, int, boolean)
	 */
	@Override
	public AdmAppln getApplicantDetails(String applicationNumber,
			int applicationYear, boolean admissionForm) throws Exception {

		Session session = null;
		AdmAppln applicantDetails = null;
		int appNO = 0;
		if (StringUtils.isNumeric(applicationNumber))
			appNO = Integer.parseInt(applicationNumber);
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";
			if (admissionForm) {
				sql = "from AdmAppln a where a.applnNo="+appNO+" and a.appliedYear="
						+ applicationYear
						+ " and a.isFinalMeritApproved=1 and a.isCancelled=0";
			} else {
				sql = "from AdmAppln a where a.applnNo="+appNO+" and a.appliedYear="
						+ applicationYear + "and a.isCancelled=0";
			}
			Query qr = session.createQuery(sql);
			applicantDetails = (AdmAppln) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			System.out.println("Error during getApplicantDetails..."+e.getCause().toString());
			throw new ApplicationException(e);
		} 
		return applicantDetails;
	}

	/*@Override
	public boolean checkDuplicatePrerequisite(int examYear, String rollNo)
			throws Exception {
		boolean duplicate = false;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CandidatePrerequisiteMarks c where c.examYear= :examYear  and c.rollNo= :rollNo ");
			query.setInteger("examYear", examYear);
			query.setString("rollNo", rollNo);
			List<CandidatePrerequisiteMarks> bos = query.list();
			if (bos != null && !bos.isEmpty()) {
				duplicate = true;
			}

		} catch (Exception e) {
			log.error("Error during checkDuplicatePrerequisite..." + e);
			duplicate = false;
			throw new ApplicationException(e);
		} 
		return duplicate;
	}*/

	/*
	 * (non-Javadoc) checks duplicates payment info
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * checkPaymentDetailsDuplicate(java.lang.String, java.lang.String,
	 * java.sql.Date, int, java.lang.String)
	 */
	
	@Override
	public boolean checkPaymentDetailsDuplicate(String challanNo,
			String journalNo, Date applicationDate, int year) throws Exception {
		boolean duplicate = false;
		Session session = null;
		String quer="";
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			/*if(!challanNo.equalsIgnoreCase("SelectedDDPayment"))
			 quer="from AdmAppln adm where adm.challanRefNo= :challanRefNo  and adm.journalNo= :journalNo and adm.date= :date and adm.appliedYear= :appliedYear";
			else */
				quer="from AdmAppln adm where  adm.journalNo= :journalNo  and adm.appliedYear= :appliedYear";	
			Query query = session.createQuery(quer);
			query.setInteger("appliedYear", year);
			//if(!challanNo.equalsIgnoreCase("SelectedDDPayment"))
			//query.setString("challanRefNo", challanNo);
			query.setString("journalNo", journalNo);
			//query.setDate("date", applicationDate);
			// query.setString("name",firstName);
			List<AdmAppln> bos = query.list();
			if (bos != null && !bos.isEmpty()) {
				duplicate = true;
			}

		} catch (Exception e) {
			log.error("Error during checkPaymentDetailsDuplicate..." + e);
			System.out.println("Error during .................................checkPaymentDetailsDuplicate.........."+ e.getCause().toString());
			duplicate = false;
			throw new ApplicationException(e);
		} 
		return duplicate;
	}

	/*
	 * (non-Javadoc) checks valid offline appln. no or not
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * checkValidOfflineNumber(int, int)
	 */
	
	/*@Override
	public boolean checkAdmittedOrNot(int applnNo, int courseid,
			Integer appliedYear) throws Exception {
		boolean admitted = false;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Student s where s.admAppln.courseBySelectedCourseId.id= :courseid and s.admAppln.appliedYear= :YEAR and s.admAppln.applnNo= :AppNo and s.isAdmitted=1");
			query.setInteger("courseid", courseid);
			query.setInteger("YEAR", appliedYear);
			query.setInteger("AppNo", applnNo);

			List<Student> students = query.list();
			if (students != null && !students.isEmpty())
				admitted = true;
		} catch (Exception e) {
			log.error("Error during checkAdmittedOrNot..." + e);
			throw new ApplicationException(e);
		} 
		return admitted;
	}*/

	/*
	 * (non-Javadoc) fetches application details
	 * 
	 * @seecom.kp.cms.transactions.admission.IOnlineApplicationTxn#
	 * getApprovalApplicantDetails(java.lang.String, int, boolean)
	 */
	@Override
	public AdmAppln getApplicationDetails(String applicationNumber,
			int applicationYear,String regNo) throws Exception {

		Session session = null;
		AdmAppln applicantDetails = null;
		int appNO = 0;
		if(applicationNumber!=null && !applicationNumber.isEmpty()){
			if (StringUtils.isNumeric(applicationNumber))
				appNO = Integer.parseInt(applicationNumber);
		}
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			/*String sql = "from AdmAppln a where a.applnNo="+appNO+" and a.appliedYear="
					+ applicationYear;*/
			
			StringBuffer sql = new StringBuffer();
			sql .append("select a from AdmAppln a ");
			if(appNO!=0){
				sql.append(" where a.applnNo="+appNO+"");
			}else if(regNo!=null && !regNo.isEmpty()){
				sql.append(" inner join a.students stu where stu.registerNo='"+regNo+"'");
			}
			if(applicationYear!=0){
				sql.append(" and a.appliedYear=" + applicationYear);
			}
			Query qr = session.createQuery(sql.toString());
			applicantDetails = (AdmAppln) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			System.out.println("Error during .................................getApplicationDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		} 
		return applicantDetails;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IOnlineApplicationTxn#getDocExamsByID
	 * (int)
	 */
	
	@Override
	public List<DocTypeExams> getDocExamsByID(int id) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from DocTypeExams c where c.isActive=1 and c.docType.isActive=1 and c.docType.id= :docID");
			query.setInteger("docID", id);
			query.setReadOnly(true);
			List<DocTypeExams> list = query.list();

//			session.close();
//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);
			System.out.println("Error during .................................getDocExamsByID.........."+ e.getCause().toString());
			//session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	@Override
	public int getAppliedYearForCourse(int courseId) throws Exception {
		Session session = null;
		Integer year=null;
		try {
			session = HibernateUtil.getSession();
			year= (Integer) session.createSQLQuery("select academic_year from course where id=:CourseId and is_active=1")
					.setInteger("CourseId", courseId).uniqueResult();
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
		return year;
	}


/*	public String saveOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline, Student admBO) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			int islock=0;
			//session = HibernateUtil.getSession();
			
			txn=session.beginTransaction();
			
			admBO.getAdmAppln().setApplnNo(0);
			session.saveOrUpdate(admBO);
			
			session.lock(admBO, LockMode.READ);
			// get the online range for course
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			Query q2=null;
			while(islock==0){
				q2=session.createSQLQuery("update applock set applock.islock=1 where applock.islock=0");
				islock=q2.executeUpdate();
				if(islock==0){
					Thread.sleep(100);
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}	
			admBO.getAdmAppln().setApplnNo(Integer.parseInt(generatedNo));
			session.update(admBO);
			Query q1=session.createSQLQuery("update applock set applock.islock=0");
			q1.executeUpdate();
			txn.commit();
			session.flush();
			session.close();
			sessionFactory.close();
			
			return generatedNo;
		} catch (Exception e) {
			log.error("ERROR IN FINAL SAVE", e);
			if (session.isOpen()){
				txn.rollback();
			session.flush();
			session.close();
			}
			throw new ApplicationException(e);
		}
	}

	public String saveOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline, Student admBO) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
		
			txn=session.beginTransaction();
			
			admBO.getAdmAppln().setApplnNo(0);
			session.saveOrUpdate(admBO);
			
			session.lock(admBO, LockMode.UPGRADE);
			// get the online range for course
			// 1. Create the query on application_number table
			// 2. Lock the query with the application_number object
			// 3. If isonline = true then Retrieve the current_online_application_no value else r
			// retrieve current_offline_application_no. 
			// New application id = 1 + the value from step 3
			// 4. update the value of the current app and commit the transaction
			// 
			
			
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}	
			admBO.getAdmAppln().setApplnNo(Integer.parseInt(generatedNo));
			session.update(admBO);
			txn.commit();
			session.flush();
			session.close();
			//sessionFactory.close();
			
			return generatedNo;
		} catch (Exception e) {
			log.error("ERROR IN FINAL SAVE", e);
			if (session.isOpen()){
				txn.rollback();
			session.flush();
			session.close();
			}
			throw new ApplicationException(e);
		}
	}*/
	

	
	@Override
	public Map<String, Integer> getCourseMapByInputId(String searchQuery)
			throws Exception {
		
		Session session = null;
		Map<String, Integer> courseMap=new HashMap<String, Integer>();
		
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery(searchQuery);
			List<Course> list = query.list();
			Iterator<Course> itr=list.iterator();
			while (itr.hasNext()) {
				Course course = (Course) itr.next();
				if(course.getCode()!=null)
				courseMap.put(course.getCode(),course.getId());
			}

			return courseMap;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/***
	 * 
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughIdForInst(int instId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.university.id is null and college.id = " + instId + " and nationality.id is null and residentCategory.id is null");
			
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForInst...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param natId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughIdForNationality(int natId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.nationality.id="+natId + " and f.college.id is null and f.university.id is null and f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForNationality...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	/**
	 * 
	 * @param natId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughIdForUniveristy(int uniId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.university.id="+uniId + " and college.id is null and nationality.id is null and residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForUniveristy...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughResidentCategory(int resId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.residentCategory.id="+resId + " and f.college.id is null and f.nationality.id is null and f.university.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	
	/**
	 * 
	 * @param instId
	 * @param natId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNationality(int instId, int natId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + "and" +
					" f.university.id is null and " +
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	
	/**
	 * 
	 * @param instId
	 * @param natId
	 * @param uniId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNationalityUni(int instId, int natId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id =" + natId + " and " +
					" f.university.id  =" + uniId + " and " + 
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		
	
	/**
	 * 
	 * @param instId
	 * @param natId
	 * @param uniId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNationalityUniRes(int instId, int natId, int uniId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id =" + resId);
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForOtherThanIndia() throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" nationality.name = 'Other'");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForOtherThanIndia...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}			
	
	/**
	 * 
	 */
	public int getAdmittedThroughForInstUni(int instId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id =" + uniId + " and " + 
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstUni...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		
		
	/**
	 * 
	 * @param instId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstRes(int instId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id  is null and " + 
					" f.residentCategory.id =" + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		
	/***
	 * 
	 */
	public int getAdmittedThroughForNatUni(int natId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " +
					" f.residentCategory.id is null " );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		
	
	
	public int getAdmittedThroughForNatRes(int natId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  is null and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param resId
	 * @param uniId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForResUni(int resId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id is null and " +
					" f.university.id  = " + uniId + " and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param instId
	 * @param natId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNatRes(int instId, int natId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  is null and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	/**
	 * 
	 * @param natId
	 * @param uniId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForNatUniRes(int natId, int uniId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	
	/**
	 * 
	 * @param uniId
	 * @param resId
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForUniResInst(int uniId, int resId, int instId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	
	public int getProgrameIdForCourse(int courseId) throws Exception {
		log.debug("inside getProgrameId");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.program.id from Course c where c.isActive = 1 and c.id = "+courseId);
			Integer programId = (Integer) query.uniqueResult();
			//sessionFactory.close();
			log.debug("leaving getPrograme");
			if(programId!= null){
				return programId.intValue();
			}
			else{
				return 0;
			}
			
		 } catch (Exception e) {
			 log.error("Error during getting program...",e);
			 //session.flush();
			 //session.close();
			 throw new ApplicationException(e);
		 }
	}

	
	@Override
	public List<DocChecklist> getRequiredDocsForCourseAndProgram(int courseId,
			int programId, int applicationYear) {
		// TODO Auto-generated method stub
		Session session = null;
		List<DocChecklist> list = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from DocChecklist doc where doc.isActive = 1 and doc.program.id= :programId and doc.course.id= :courseId and doc.year= :applicationYear");
			query.setParameter("programId", programId);
			query.setParameter("courseId", courseId);
			query.setParameter("applicationYear", applicationYear);
			list = query.list(); 
		} catch (Exception e) {
			System.out.println("Error during .................................getRequiredDocsForCourseAndProgram.........."+ e.getCause().toString());
			log.error(e.getMessage());
			if (session != null) {
				//session.flush();
				session.close();
			}
		}
		if (list == null || list.size() < 1) {
			return new ArrayList<DocChecklist>();
		}
		return list;
	}

	
	@Override
	public List<ApplnDoc> getListOfDocuments(String applicationNumber,
			int applicationYear) {
		Session session = null;
		List<ApplnDoc> list=new ArrayList<ApplnDoc>();
		try {
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = sessionFactory.openSession();
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ApplnDoc a where a.admAppln.applnNo ="+applicationNumber +
					" and a.admAppln.appliedYear="+applicationYear);
			list= query.list();
//			session.close();
		} catch (Exception e) {
			log.error("Error in getListOfDocuments...", e);
			if (session != null) {
				
				//session.flush();
			}
			System.out.println("Error during .................................getListOfDocuments.........."+ e.getCause().toString());
		}
		return list;
	}
	/**
	 * This method is used to check existence of application number, courseId
	 * and admission year.
	 */

	
	/*@Override
	public AdmAppln checkApplicationNumberForCancel(String searchCriteria)
			throws Exception {
		Session session = null;

		AdmAppln admAppln=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<AdmAppln> list = session.createQuery(searchCriteria).list();
			if(list!=null && !list.isEmpty()){
				admAppln=list.get(0);
			}
		} catch (Exception e) {
			log.error("Error during checkApplicationNumberCancel...", e);
			throw new ApplicationException(e);
		} 
		return admAppln;
	}*/

	
	@Override
	public Map<String, Integer> getCourseMap() throws Exception {
		Session session = null;
		Map<String, Integer> courseMap=new HashMap<String, Integer>();
		
		try {
			String quer="from Course c where c.isActive=1";
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery(quer);
			List<Course> list = query.list();
			Iterator<Course> itr=list.iterator();
			while (itr.hasNext()) {
				Course course = (Course) itr.next();
				if(course.getCode()!=null)
				courseMap.put(course.getCode(),course.getId());
			}

			return courseMap;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	
	/*public AdmAppln getApplicantStatusDetails(String applicationNumber,int applicationYear,int courseId) throws Exception{

		Session session = null;
		AdmAppln applicantDetails = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			applicantDetails = (AdmAppln) session.createQuery("from AdmAppln a where a.applnNo="+applicationNumber+" and a.appliedYear="+applicationYear+" and a.course.id="+courseId).uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return applicantDetails;
	}
}*/


/*public List getApplicantStatusDetails(String applicationNumber,int applicationYear,int courseId) throws Exception {
	Session session = null;
	List dataList = null;
	try {
		session = HibernateUtil.getSession();
		
		String dataQuery="select a.adm_status, mid(a.adm_status,1,50) from adm_appln a where a.appln_no="+applicationNumber+" and a.course_id="+courseId+" and a.applied_year="+applicationYear;
		Query selCandidatesQuery=session.createSQLQuery(dataQuery);
		dataList = selCandidatesQuery.list();
		return dataList;
	} catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} 
}*/


public Map<String, String> getMonthMap() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from PrerequisitsYearMonth pre where pre.isActive=true group by pre.month");
		List<PrerequisitsYearMonth> list=query.list();
		if(list!=null){
			Iterator<PrerequisitsYearMonth> iterator=list.iterator();
			while(iterator.hasNext()){
				PrerequisitsYearMonth pre=iterator.next();
				if(pre!=null && pre.getMonth()!=null && pre.getMonth()>0 && pre.getId()>0)
				{
				if(pre.getMonth()==1)
					pre.setMonthName("JAN");
				if(pre.getMonth()==2)
					pre.setMonthName("FEB");
				if(pre.getMonth()==3)
					pre.setMonthName("MAR");
				if(pre.getMonth()==4)
					pre.setMonthName("APR");
				if(pre.getMonth()==5)
					pre.setMonthName("MAY");
				if(pre.getMonth()==6)
					pre.setMonthName("JUN");
				if(pre.getMonth()==7)
					pre.setMonthName("JUL");
				if(pre.getMonth()==8)
					pre.setMonthName("AUG");
				if(pre.getMonth()==9)
					pre.setMonthName("SEP");
				if(pre.getMonth()==10)
					pre.setMonthName("OCT");
				if(pre.getMonth()==11)
					pre.setMonthName("NOV");
				if(pre.getMonth()==12)
					pre.setMonthName("DEC");
				map.put(String.valueOf(pre.getMonth()),pre.getMonthName());
				}
			}
		}
	}catch (Exception exception) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}



public Map<String, String> getYearByMonth(String Month) throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from PrerequisitsYearMonth pre where pre.isActive=true and pre.month="+ Month +" group by  pre.year");
		List<PrerequisitsYearMonth> list=query.list();
		if(list!=null){
			Iterator<PrerequisitsYearMonth> iterator=list.iterator();
			while(iterator.hasNext()){
				PrerequisitsYearMonth pre=iterator.next();
				if(pre!=null && pre.getYear()!=null && pre.getYear()>0 && pre.getId()>0)
				map.put(String.valueOf(pre.getYear()),String.valueOf(pre.getYear()));
			}
		}
	}catch (Exception exception) {
		// TODO: handle exception
		throw new ApplicationException();
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}

public Map<String, String> getYear() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from PrerequisitsYearMonth pre where pre.isActive=true group by pre.year");
		List<PrerequisitsYearMonth> list=query.list();
		if(list!=null){
			Iterator<PrerequisitsYearMonth> iterator=list.iterator();
			while(iterator.hasNext()){
				PrerequisitsYearMonth pre=iterator.next();
				if(pre!=null && pre.getYear()!=null && pre.getYear()>0 && pre.getId()>0)
				map.put(String.valueOf(pre.getYear()),String.valueOf(pre.getYear()));
			}
		}
	}catch (Exception exception) {
		// TODO: handle exception
		throw new ApplicationException();
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getMails()
	 */
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#generateCandidateRefNo(com.kp.cms.bo.admin.CandidatePGIDetails)
	 */
	@Override
	public synchronized String generateCandidateRefNo(CandidatePGIDetails bo) throws Exception {
		log.info("Entered into generateCandidateRefNo-OnlineApplicationImpl");
		Session session=null;
		Transaction transaction=null;
		String candidateRefNo="";
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				session.save(bo);
			}
			transaction.commit();
			int savedId=bo.getId();
			if(savedId>0){
				transaction=session.beginTransaction();
				candidateRefNo="APPLN"+String.valueOf(savedId);
				bo.setCandidateRefNo(candidateRefNo);
				session.update(bo);
				transaction.commit();
			}
			session.close();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				//session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-OnlineApplicationImpl..."+e);
			System.out.println("Error during .................................generateCandidateRefNo.........."+ e.getCause().toString());
			throw  new ApplicationException(e);
		}
		
		finally{
			
			//session.flush();
			session.close();
		}
		
		
		
		log.info("Exit generateCandidateRefNo-OnlineApplicationImpl");
		return candidateRefNo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#updateReceivedStatus(com.kp.cms.bo.admin.CandidatePGIDetails)
	 */
	@Override
	public boolean updateReceivedStatus(CandidatePGIDetails bo,OnlineApplicationForm admForm)
			throws Exception {

		log.info("Entered into generateCandidateRefNo-AdmissionFormTransactionImpl");
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		String txnStatus = admForm.isByPass() ? "success" : "Pending";
		admForm.setIsTnxStatusSuccess(false);
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				String query=" from CandidatePGIDetails c where c.candidateRefNo='"+bo.getCandidateRefNo()
				+"' and c.txnAmount="+admForm.getApplicationAmount()+" and c.txnStatus='" + txnStatus + "'";
				CandidatePGIDetails candidatePgiBo=(CandidatePGIDetails)session.createQuery(query).uniqueResult();
				if(candidatePgiBo!=null){
					candidatePgiBo.setTxnRefNo(bo.getTxnRefNo());
					candidatePgiBo.setBankRefNo(bo.getBankRefNo());
					candidatePgiBo.setTxnDate(bo.getTxnDate());
					candidatePgiBo.setTxnStatus(bo.getTxnStatus());
					
					//raghu
					candidatePgiBo.setMode(bo.getMode());
					candidatePgiBo.setUnmappedStatus(bo.getUnmappedStatus());
					candidatePgiBo.setMihpayId(bo.getMihpayId());
					candidatePgiBo.setPgType(bo.getPgType());
					candidatePgiBo.setAdditionalCharges(bo.getAdditionalCharges());
					if(bo.getTxnStatus()!=null && bo.getTxnStatus().equalsIgnoreCase("Success")){
						AdmAppln adm=new AdmAppln();
						adm.setId(Integer.parseInt(admForm.getAdmApplnId()));
						candidatePgiBo.setAdmAppln(adm);
					}
	
					session.update(candidatePgiBo);
					if(bo.getTxnStatus()!=null && bo.getTxnStatus().equalsIgnoreCase("Success")){
						admForm.setIsTnxStatusSuccess(true);
						admForm.setPaymentSuccess(true);
					}
					admForm.setCandidateRefNo(candidatePgiBo.getCandidateRefNo());
					admForm.setTransactionRefNO(bo.getTxnRefNo());
					isUpdated=true;
				}
			}
			transaction.commit();
			//session.flush();
			//session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				//session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e.getCause().toString());
			throw  new ApplicationException(e);
		}
		
        finally{
			//session.flush();
			session.close();
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return isUpdated;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getPaidCandidateDetails(java.lang.String)
	 */
	
	@Override
	public List<CandidatePGIDetails> getPaidCandidateDetails(String query) throws Exception {
		log.info("Entered into getPaidCandidateDetails-OnlineApplicationImpl");
		Session session=null;
		/* modified by sudhir 
		 * changed the return type from CandidatePGIDetails to List<CandidatePGIDetails>
		 * */
		List<CandidatePGIDetails> candidatePGIDetails;
		try {
			session=HibernateUtil.getSession();
			candidatePGIDetails=session.createQuery(query).list();
			
		} catch (Exception e) {
			if (session != null){
				//session.flush();
				session.close();
			}
			log.error("Error in getPaidCandidateDetails-OnlineApplicationImpl..."+e.getCause().toString());
			System.out.println("Error during .................................getPaidCandidateDetails.........."+ e.getCause().toString());
			throw  new ApplicationException(e);
		}
		log.info("Exit getPaidCandidateDetails-OnlineApplicationImpl");
		return candidatePGIDetails;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getApplicantFeedback()
	 */
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#checkWorkExperianceMandatory(java.lang.String)
	 */
	/*@Override
	public boolean checkWorkExperianceMandatory(String courseId)
			throws Exception {
		boolean isMandatory = false;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			isMandatory = (Boolean)session.createQuery("select c.isWorkExperienceMandatory from Course c where c.id = "+courseId).uniqueResult();
		}catch (Exception e) {
			if(session != null){
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
		return isMandatory;
	}*/

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getPrerequisiteMarks(java.lang.String)
	 */
	
	/*@Override
	public List<CandidatePrerequisiteMarks> getPrerequisiteMarks(
			String applicationNumber) throws Exception {
		Session session = null;
		List<CandidatePrerequisiteMarks> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from CandidatePrerequisiteMarks c where c.admAppln.applnNo="+applicationNumber);
			list = query.list();
		} catch (Exception e) {
			if(session != null){
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
		return list;
	}*/
	
	public String getCandidatePGIDetails(int admApplnId) throws Exception
	{
	Session session = null;
	String txnRefNo = null;
	
	try {
		session = HibernateUtil.getSession();
		String query = "";
		query="select cp.txnRefNo from CandidatePGIDetails cp " +
				"where cp.admAppln.id is not null " +
				"and cp.admAppln.id="+ admApplnId +" and cp.txnRefNo is not null";
		Query qr = session.createQuery(query);
		txnRefNo = (String) qr.uniqueResult();
	} catch (Exception e) {
		log.error("Error while getting applicant details..." + e.getCause().toString());
		throw new ApplicationException(e);
	} 
	return txnRefNo;
}

	public CandidatePGIDetails getCandidateDetails(int admApplnId) throws Exception {
		Session session = null;
		CandidatePGIDetails details;
		try{
			session = HibernateUtil.getSession();
			String query = "from CandidatePGIDetails c where c.txnRefNo is not null and c.admAppln.id="+ admApplnId;
			details = (CandidatePGIDetails)session.createQuery(query).uniqueResult();
		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e.getCause().toString());
			System.out.println("Error during .................................getCandidatePGIDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		return details;
	}

	
	
	
	
	
	
	
	

	
	
	
	

	
	

		
	
	public String getAdmApplnId(OnlineApplicationForm admForm) throws Exception
	{
		String admapplnId=null;
		AdmAppln appln=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from AdmAppln a where a.appliedYear="+admForm.getApplicationYear()+" and a.applnNo="+admForm.getApplicationNumber()+" and a.isCancelled=0 ");
			appln = (AdmAppln)query.uniqueResult();
			if(appln!=null){
				admapplnId=String.valueOf(appln.getId());
			}
		} catch (Exception e) {
			System.out.println("Error during .................................getAdmApplnId.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		} 
		return admapplnId;
		
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getCurrentChallanNo()
	 */
	public String getCouseNameByCourseId(String courseId) throws Exception {
		Session session = null;
		String courseName = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select c.name from Course c where c.isActive=1 and c.id="+courseId);
			courseName = (String) query.uniqueResult();
//			session.close();
		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}
			System.out.println("Error during .................................getCouseNameByCourseId.........."+ e.getCause().toString());
			log.error("Error during getting current challanNO...", e);
			throw new ApplicationException(e);
		}
		return courseName;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getResidenceNameByResidanceId(java.lang.String)
	 */
	public String getResidenceNameByResidanceId(String residenceId) throws Exception {
		Session session = null;
		String residenceName = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select c.name from ResidentCategory c where c.isActive=1 and c.id="+residenceId);
			residenceName = (String) query.uniqueResult();
//			session.close();
		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}
			System.out.println("Error during .................................getResidenceNameByResidanceId.........."+ e.getCause().toString());
			log.error("Error during getting current challanNO...", e);
			throw new ApplicationException(e);
		}
		return residenceName;
	}
	
	public Map<Integer,String> getApplicationNumRageMap(OnlineApplicationForm admForm)throws Exception
	{
	 Session session = null;
	 Map<Integer,String> totalInstalmentMap=new HashMap<Integer,String>();
	try {
		 
		//session =InitSessionFactory.getInstance().openSession();
		session = HibernateUtil.getSession();
		List<Object[]> boList = session.createQuery(" select crsAppNo.course.id,appNum.offlineApplnNoFrom,appNum.offlineApplnNoTo "+
				" from ApplicationNumber appNum"+
				" inner join appNum.courseApplicationNumbers crsAppNo where crsAppNo.isActive=1 and appNum.isActive=1"+
				" and appNum.year='"+admForm.getYear()+"'").list();
		Iterator<Object[]> iterator = boList.iterator();
		while(iterator.hasNext()){
			
			Object[] obj=(Object[])iterator.next();
			if(obj[0]!=null && obj[1]!=null && obj[2]!=null){
				
				totalInstalmentMap.put(Integer.parseInt(obj[0].toString()), (obj[1].toString())+" "+(obj[2].toString()));
			
			}
		}
		
	} catch (Exception e) {
		System.out.println("Error during .................................getApplicationNumRageMap.........."+ e.getCause().toString());
		throw  e;
	} 

	return totalInstalmentMap;
  }
	
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getAdmApplnList(java.lang.String)
	 */
	@Override
	public LinkedList<AdmAppln> getAdmApplnList(String uniqueId) throws Exception {
		Session session = null;
		LinkedList<AdmAppln> admApplnsList =null;
		try{
			session = HibernateUtil.getSession();
			if(uniqueId!=null && !uniqueId.isEmpty()){/*
				String hqlQuery = "from AdmAppln admAppln where admAppln.studentOnlineApplication.isActive=1" +
				" and  admAppln.studentOnlineApplication.id="+Integer.parseInt(uniqueId)+ " and admAppln.currentPageName='payment' order by admAppln.id";
				Query query = session.createQuery(hqlQuery);
				List list =  query.list();
				if (list==null || list.isEmpty()) {
					hqlQuery = "from AdmAppln admAppln where admAppln.studentOnlineApplication.isActive=1" +
							" and  admAppln.studentOnlineApplication.id="+Integer.parseInt(uniqueId)+" and admAppln.currentPageName='details' order by admAppln.id";
					query = session.createQuery(hqlQuery);
					list =  query.list();
				}
				if (list==null || list.isEmpty()) {
					hqlQuery = "from AdmAppln admAppln where admAppln.studentOnlineApplication.isActive=1" +
							" and  admAppln.studentOnlineApplication.id="+Integer.parseInt(uniqueId)+" and admAppln.currentPageName='terms' order by admAppln.id";
					query = session.createQuery(hqlQuery);
					list =  query.list();
				}
				admApplnsList= new LinkedList<AdmAppln>(list);
			*/
				
				String hqlQuery = "from AdmAppln admAppln where admAppln.studentOnlineApplication.isActive=1" +
				" and  admAppln.studentOnlineApplication.id="+Integer.parseInt(uniqueId)+ " and admAppln.isDraftCancelled=0 order by admAppln.id";
				Query query = session.createQuery(hqlQuery);
				List list =  query.list();
				admApplnsList= new LinkedList<AdmAppln>(list);
			
			}
		}catch (Exception e) {
			System.out.println("Error during .................................getAdmApplnList.........."+ e.getCause().toString());
			throw e;
		}
		return admApplnsList;
	}

	@Override
	public StudentOnlineApplication getUniqueId(OnlineApplicationForm admForm,String academicYear) throws Exception {
		Session session = null;
		StudentOnlineApplication uniqueIdBO = null;
		try{
			
			session = HibernateUtil.getSession();
			StringBuffer hqlQuery = new StringBuffer();
			  hqlQuery.append("from StudentOnlineApplication onlineApp where ");
			if(admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !admForm.getApplicantDetails().getPersonalData().getEmail().isEmpty()){
				hqlQuery.append(" onlineApp.emailId='"+admForm.getApplicantDetails().getPersonalData().getEmail()+"' and");
			}
			hqlQuery.append(" onlineApp.mobileNo='"+admForm.getApplicantDetails().getPersonalData().getMobileNo2()+"'");
			hqlQuery.append(" and onlineApp.dateOfBirth='"+CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDetails().getPersonalData().getDob())+"'");
			hqlQuery.append(" and onlineApp.year="+Integer.parseInt(academicYear)+ " and onlineApp.isActive=1 order by onlineApp.id desc");
			Query query = session.createQuery(hqlQuery.toString());
			List<StudentOnlineApplication> boList = query.list();
			if(boList!=null && !boList.isEmpty()){
				uniqueIdBO = boList.get(0);
			}
			
		}catch (Exception e) {
			System.out.println("Error during .................................getUniqueId.........."+ e.getCause().toString());
			throw e;
		}
		return uniqueIdBO;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getAdmApplnDetails(java.lang.String)
	 */
	@Override
	public AdmAppln getAdmApplnDetails(String applnNo) throws Exception {
		Session session = null;
		AdmAppln admAppln =null;
		try{
			session = HibernateUtil.getSession();
			if(applnNo!=null && StringUtils.isNumeric(applnNo)){
				String str = "from AdmAppln admAppln where admAppln.applnNo="+Integer.parseInt(applnNo);
				Query query = session.createQuery(str);
				admAppln = (AdmAppln) query.uniqueResult();
			}
		}catch (Exception e) {
			System.out.println("Error during .................................getAdmApplnDetails.........."+ e.getCause().toString());
			throw e;
		}
		return admAppln;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getCandiadatePGIDetailsById(int)
	 */
	@Override
	public CandidatePGIDetails getCandiadatePGIDetailsById(int candidatePGIId) throws Exception {
		Session session = null;
		CandidatePGIDetails candidatePGIDetails = new CandidatePGIDetails();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from CandidatePGIDetails candidatePGIDetails where candidatePGIDetails.id="+candidatePGIId;
			Query query = session.createQuery(hqlQuery);
			candidatePGIDetails = (CandidatePGIDetails) query.uniqueResult();
		}catch (Exception e) {
			System.out.println("Error during .................................getCandiadatePGIDetailsById.........."+ e.getCause().toString());
			throw e;
		}
		return candidatePGIDetails;
	}
	
	public OnlineApplicationForm getUniqueId(OnlineApplicationForm admForm)throws Exception{
		Session session = null;
		//int uniqueId =0;
		StudentOnlineApplication unique=null;
		try{
			session = HibernateUtil.getSession();
				String str = "from StudentOnlineApplication onlineApp where onlineApp.id="+Integer.parseInt(admForm.getUniqueId());
				Query query = session.createQuery(str);
				unique = (StudentOnlineApplication) query.uniqueResult();
				if(unique!=null)
				{
					admForm.setDisplayEmail(unique.getEmailId());
					admForm.setDisplayMobile(unique.getMobileNo());
					admForm.setDisplayUniqueId(unique.getUniqueId());
				}
		}catch (Exception e) {
			System.out.println("Error during .................................getUniqueId.........."+ e.getCause().toString());
			throw e;
		}
		return admForm;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#checkUniqueIdIsExists(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentOnlineApplication checkUniqueIdIsExists(String mobile,
			String dob, String academicYear) throws Exception {
		Session session = null;
		StudentOnlineApplication uniqueIdBO = null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer hqlQuery = new StringBuffer();
			hqlQuery.append("from StudentOnlineApplication onlineApp where ");
			hqlQuery.append(" onlineApp.mobileNo='" + mobile + "'");
			hqlQuery.append(" and onlineApp.dateOfBirth='" + CommonUtil.ConvertStringToSQLDate(dob) + "'");
			hqlQuery.append(" and onlineApp.year=" + Integer.parseInt(academicYear) + " and onlineApp.isActive=1 order by onlineApp.id desc");
			Query query = session.createQuery(hqlQuery.toString());
			List<StudentOnlineApplication> boList = query.list();
			if (boList != null && !boList.isEmpty()) {
				uniqueIdBO = boList.get(0);
			}
		} catch (Exception e) {
			System.out.println("Error during .................................checkUniqueIdIsExists.........."+ e.getCause().toString());
			throw e;
		}
		return uniqueIdBO;
	}
	
	/*public boolean sendMailToAdmin(OnlineApplicationForm admForm, int appliedyear) {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			String adminmail=KPPropertiesConfiguration.USER_NAME;
			String toAddress=adminmail;
			String subject="Please configure online application No. range";
			String msg="Hi!,<br>" +
					"Please configure online application No. range for" +
					admForm.getCourseName()+" for academic year " +appliedyear+".<br>Thanks,<br>"+CMSConstants.ORGANISATION_COLLEGE_NAME+" Admin.<br>";
			sent=sendMail(toAddress, subject, msg);
		return sent;
}*/
	
	/*public boolean sendMail(String mailID,String sub,String message) {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			String adminmail=KPPropertiesConfiguration.USER_NAME;
			String toAddress=mailID;
			String subject=sub;
			String msg=message;
		
			MailTO mailto=new MailTO();
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(KPPropertiesConfiguration.STUDENT_MAIL_FROM_NAME);
			sent=CommonUtil.sendMail(mailto);
		return sent;
}*/
	
	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String getAdmissionAcademicYear() throws Exception {
		Session session = null;
		String  academicYear = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select academicYear.year from AcademicYear academicYear where academicYear.isActive=1 and academicYear.isCurrentForAdmission=1";
			Query query = session.createQuery(hqlQuery);
			Integer year = (Integer) query.uniqueResult();
			academicYear = year.toString();
			
		}catch (Exception e) {
			System.out.println("Error during .................................getAdmissionAcademicYear.........."+ e.getCause().toString());
			throw e;
		}
		return academicYear;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#getCandidatePreRequisiteMarks(int)
	 */
	/*@Override
	public CandidatePrerequisiteMarks getCandidatePreRequisiteMarks(int admAppId)throws Exception {
		Session session = null;
		CandidatePrerequisiteMarks  prerequisiteMarks = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from CandidatePrerequisiteMarks c where c.admAppln.id=:AdmAppId  and c.isActive=1";
			Query query = session.createQuery(hqlQuery);
			query.setInteger("AdmAppId", admAppId);
			prerequisiteMarks =(CandidatePrerequisiteMarks) query.uniqueResult();
		}catch (Exception e) {
			throw e;
		}
		return prerequisiteMarks;
	}*/

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IOnlineApplicationTxn#checkCourseInDraftMode(java.lang.String, int)
	 */
	@Override
	public boolean checkCourseInDraftMode(String uniqueId, int courseId) throws Exception {
		Session session = null;
		boolean isCourseDraftMode=false;
		try{
			session = HibernateUtil.getSession();
			Query query=null;
			if(courseId!=0){
				query = session.createSQLQuery("select adm_appln.id from adm_appln where unique_id=:uniqueId and course_id=:courseId and is_draft_mode=1 and is_cancelled=0 and is_draft_cancelled=0");
				
			}else{
				query = session.createSQLQuery("select adm_appln.id from adm_appln where unique_id=:uniqueId  and is_draft_mode=1 and is_cancelled=0 and is_draft_cancelled=0");
				
			}
			if(courseId!=0){
				query.setInteger("courseId", courseId);
			}
			query.setString("uniqueId", uniqueId);
			
			List<Integer> list =query.list();
			if (list!=null && !list.isEmpty()) {
				isCourseDraftMode=true;
			}
		}catch (Exception e) {
			System.out.println("Error during .................................checkCourseInDraftMode.........."+ e.getCause().toString());
			throw e;
		}
		return isCourseDraftMode;
	}
	
	
	@Override
	public Timestamp getDateofApp(String uniqueId) throws Exception {
		
		
		java.sql.Timestamp date=null;
		Session session=HibernateUtil.getSession();
		try{
		Query query=session.createQuery("select s.createdDate from StudentOnlineApplication s where s.id=:uid");
		query.setInteger("uid", Integer.parseInt(uniqueId));
		
		date=(java.sql.Timestamp)query.uniqueResult();
		
		}catch (Exception e) {
			System.out.println("Error during .................................getDateofApp.........."+ e.getCause().toString());
			throw e;
		}
		return date;
	}

	public List<CandidatePGIDetails> getAllPendingTransactionStatausOfCandidate(OnlineApplicationForm admForm) throws Exception {
		
		Session session = null;
		List<CandidatePGIDetails> pendingStatusList = new ArrayList<CandidatePGIDetails>();
		String appName=admForm.getApplicantName();
		if(admForm.getApplicantName().contains("'")){
			appName = admForm.getApplicantName().replaceAll("'", "''");
		}
		
		try {
			
			session = HibernateUtil.getSession();
			String hqlQuery = " from CandidatePGIDetails c where c.candidateName='" +appName+ "'" +
							  " and c.candidateDob='" +CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob())+ "'" +
							  " and c.uniqueId.id="+admForm.getUniqueId()+
							  " and c.mobileNo1='" +admForm.getMobileNo1()+ "'" +
							  " and c.mobileNo2='" +admForm.getMobileNo2()+ "'" +
							  " and c.email='" +admForm.getEmail()+ "'" +
							  " and c.residentCategory.id=" +admForm.getResidentCategoryForOnlineAppln()+ 
							  " and c.admAppln.id is null and c.txnStatus = 'Pending'" +
							  " and c.uniqueId.id not in (select uniqueId.id from CandidatePGIDetails cin " + 
							  							 " where (c.txnStatus = 'Success' or c.txnStatus = 'S'))";
			Query query = session.createQuery(hqlQuery);
			pendingStatusList = query.list();
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
			if(session != null) {
				session.flush();
				session.close();
			}
		}
		
		return pendingStatusList;
	}
	
	public boolean updatePendingOnlineApp(OnlineApplicationForm admForm, String transactionId, String transactionAmount, String paymentMode, String transactionDate, String newTransactionStatus) throws Exception {		
		
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			
				
				String query=" from CandidatePGIDetails c where c.txnRefNo=:txnRefNo and c.txnStatus='Pending'";
				CandidatePGIDetails candidatePgiBo=(CandidatePGIDetails)session.createQuery(query).setString("txnRefNo", admForm.getVerificationRefNo()).uniqueResult();
					
				if(candidatePgiBo!=null){
					
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					java.util.Date transDate = formatter.parse(transactionDate);
					
					String queryForAdmAppln = " from AdmAppln appln where appln.studentOnlineApplication = " + candidatePgiBo.getUniqueId().getId();
					AdmAppln admAppln = (AdmAppln) session.createQuery(queryForAdmAppln).uniqueResult();
					admAppln.setCurrentPageName("payment");
					admAppln.setJournalNo(transactionId);
					admAppln.setDate(transDate);
					admAppln.setAmount(new BigDecimal(transactionAmount));
					
					admForm.setEmailId(admAppln.getStudentOnlineApplication().getEmailId());
					
					candidatePgiBo.setTxnStatus("S");
					candidatePgiBo.setTxnDate(transDate);
					candidatePgiBo.setTxnRefNo(transactionId);
					candidatePgiBo.setModifiedBy(admForm.getUserId());
					candidatePgiBo.setLastModifiedDate(new java.util.Date());
					session.update(admAppln);
					session.update(candidatePgiBo);
				
					transaction.commit();
					isUpdated=true;

				}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}			
			throw  new ApplicationException(e);
		}
		return isUpdated;
	}
	
	public String getcandidatepgi(String uniqueId)throws Exception {
		
		Session session = null;
		String candidateRefNo = null;
		try {
			session = HibernateUtil.getSession();
			StringBuilder hql = new StringBuilder("select c.candidateRefNo from CandidatePGIDetails c where c.uniqueId.id = :uniqueId and c.txnStatus = 'success'");
			Query query = session.createQuery(hql.toString()).setInteger("uniqueId", Integer.parseInt(uniqueId));
			candidateRefNo = (String) query.uniqueResult();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return candidateRefNo;
	}

	@Override
	public List<Object[]> getAllCourses(int ptype) throws ApplicationException {
		Session session = null;
		List<Object[]> courseList=null;
		try{
			session = HibernateUtil.getSession();
			String query = "select c.name,c.isAided,c.id from Course c where c.program.programType.id="+ ptype;
			courseList =session.createQuery(query).list();
			return courseList;
		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e.getCause().toString());
			System.out.println("Error during .................................getCandidatePGIDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Object[]> getAllCourses() throws ApplicationException {
		Session session = null;
		List<Object[]> courseList=null;
		try{
			session = HibernateUtil.getSession();
			String query = "select c.name,c.isAided,c.id from Course c";
			courseList =session.createQuery(query).list();
			return courseList;
		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e.getCause().toString());
			System.out.println("Error during .................................getCandidatePGIDetails.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
	}
	

	@Override
	public boolean updateReceivedStatus1(OnlineApplicationForm admForm) throws Exception {

		
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		String txnStatus ="Pending";
		admForm.setApplicationAmount(admForm.getTxnAmt());
		admForm.setIsTnxStatusSuccess(false);
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
				System.out.println("inside txnImpl -> before geting Bo");
				String query=" from CandidatePGIDetails c where c.candidateRefNo='"+admForm.getCandidateRefNo()
				+"' and c.txnStatus='" + txnStatus + "'";
				
				System.out.println(query);
				CandidatePGIDetails candidatePgiBo=(CandidatePGIDetails)session.createQuery(query).uniqueResult();
				System.out.println("inside txnImpl -> after geting Bo id="+candidatePgiBo.getId());
				if(candidatePgiBo!=null){
					candidatePgiBo.setTxnRefNo(admForm.getTxnRefNo());
					candidatePgiBo.setBankRefNo(admForm.getBank_ref_num());
					candidatePgiBo.setTxnDate(CommonUtil.ConvertsqlStringToDate(admForm.getTxnDate()));
					if(admForm.getStatus()!=null && admForm.getStatus().equalsIgnoreCase("S")){
						candidatePgiBo.setTxnStatus("success");
					}else{
						candidatePgiBo.setTxnStatus("failure");
					}
					
					//candidatePgiBo.setAuthzCode(admForm.getAuthzCode());
					//candidatePgiBo.setResponseCode(admForm.getResponceCode());
					//raghu
					//candidatePgiBo.setMode(bo.getMode());
					//candidatePgiBo.setUnmappedStatus(bo.getUnmappedStatus());
				//	candidatePgiBo.setMihpayId(bo.getMihpayId());
				//	candidatePgiBo.setPgType(bo.getPgType());
				//	candidatePgiBo.setAdditionalCharges(bo.getAdditionalCharges());
					if(admForm.getStatus()!=null && admForm.getStatus().equalsIgnoreCase("S")){
						System.out.println("inside txnImpl ->Before admappln id"+admForm.getAdmApplnId());
						AdmAppln adm=new AdmAppln();
						adm.setId(Integer.parseInt(admForm.getAdmApplnId()));
						candidatePgiBo.setAdmAppln(adm);
					}
	
					session.update(candidatePgiBo);
					if(admForm.getStatus()!=null && admForm.getStatus().equalsIgnoreCase("S")){
						System.out.println("inside txnImpl ->after Updaate success");
						admForm.setIsTnxStatusSuccess(true);
						admForm.setPaymentSuccess(true);
						admForm.setPgiStatus("Payment Successful");
					}
					admForm.setCandidateRefNo(candidatePgiBo.getCandidateRefNo());
					admForm.setTransactionRefNO(admForm.getTxnRefNo());
					isUpdated=true;
				}
			
			transaction.commit();
			//session.flush();
			//session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				//session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e.getCause().toString());
			throw  new ApplicationException(e);
		}
		
        finally{
			//session.flush();
			session.close();
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return isUpdated;
	}

	@Override
	public String getIsCommunityQuota(String admApplnId) throws ApplicationException {
		Session session = null;
		String query=null;
		try {
			session=HibernateUtil.getSession();
			query="select s.studentOnlineApplication.isCommuQuota from AdmAppln s where s.id="+admApplnId;
			
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e.getCause().toString());
			System.out.println("Error during ..........................................."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		return (String) session.createQuery(query).uniqueResult();
	}

	@Override
	public String getIsMngQuota(String admApplnId) throws ApplicationException {

		Session session = null;
		String query=null;
		String rtResult=null;
		Boolean result=false;
		try {
			session=HibernateUtil.getSession();
			query="select s.studentOnlineApplication.mngQuota from AdmAppln s where s.id="+admApplnId;
			
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e.getCause().toString());
			System.out.println("Error during ..........................................."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		result=(Boolean) session.createQuery(query).uniqueResult();
		if (result==true) {
			rtResult="1";
		}
		else {
			rtResult="0";
		}
		return rtResult; 
	
	}

	@Override
	public AdmAppln getApplicantDet(OnlineApplicationForm admForm) throws Exception {
		Session session = null;
		AdmAppln admAppln =null;
		try{
			session = HibernateUtil.getSession();
			if(admForm.getCandidateRefNo()!=null && !admForm.getCandidateRefNo().isEmpty()){
				String str = "from AdmAppln a where a.studentOnlineApplication.id=(select b.uniqueId.id from CandidatePGIDetails b where b.candidateRefNo='"+admForm.getCandidateRefNo()+"')";
				System.out.println("get manual admappln Query "+str);
				Query query = session.createQuery(str);
				admAppln = (AdmAppln) query.uniqueResult();
			}
		}catch (Exception e) {
			System.out.println("Error during .................................getAdmApplnDetails.........."+ e.getCause().toString());
			throw e;
		}
		return admAppln;
	}
}
