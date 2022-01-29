package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admission.ApplnAcknowledgement;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.transactions.admission.IAdmissionStatusTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionStatusTransactionImpl implements IAdmissionStatusTransaction {
	
	private static final Log log = LogFactory.getLog(AdmissionStatusTransactionImpl.class);
	
	/**
	 * Passing application no. and gets the matching records from AdmAppln table which shows the admission status and also checks for the interview status
	 */
	
	public AdmAppln getInterviewResult(String applicationNo,int year) throws Exception{
		log.info("Inside of getInterviewResult of AdmissionStatusTransactionImpl");
		Session session = null;
		AdmAppln admAppln=null;		
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from AdmAppln adm where adm.applnNo=? and adm.appliedYear = " +
			" (select max(appliedYear) from AdmAppln sub where sub.applnNo = " + applicationNo + ")");
			query.setString(0,applicationNo);
			//query.setInteger(1, year);
			admAppln = (AdmAppln)query.uniqueResult();		
			log.info("End of getInterviewResult of AdmissionStatusTransactionImpl");
			return admAppln;	
		} catch (Exception e) {
			log.error("Exception ocured in getInterviewResult of AdmissionStatusTransactionImpl :" + e);
			throw  new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	/**
	 * Passing application no. and gets the admission status from AdmAppln table
	 */
	
	public List<AdmAppln> getDetailsAdmAppln(String applicationNo)throws Exception {
		log.info("Inside of getDetailsAdmAppln of AdmissionStatusTransactionImpl");
		Session session = null;
		List<AdmAppln> admAppln;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AdmAppln adm where adm.applnNo= " + applicationNo +  
					" and appliedYear = (select max(appliedYear) from AdmAppln sub where sub.applnNo = " + applicationNo + ")");
			admAppln = query.list();
			if(!admAppln.isEmpty())
			{
				return admAppln;
			}			
		} catch (Exception e) {
		log.error("Exception ocured in getDetailsAdmAppln of AdmissionStatusTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getDetailsAdmAppln of AdmissionStatusTransactionImpl");
		return admAppln;
	}
	public List<InterviewCard> getStudentsList(int applicationNo) throws Exception {
		log.info("entered getStudentsList.of AdmissionStatusTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession()
				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				 Query query = session.createQuery("from InterviewCard i where i.admAppln.applnNo = :applicationNo");
				 query.setInteger("applicationNo", applicationNo);
				 List<InterviewCard> interviewTypes=  query.list();
				 transaction.commit();
				session.flush();
//				//session.close();
				log.info("End getStudentsList.of AdmissionStatusTransactionImpl");
				return interviewTypes;
		} catch (Exception e) {
			if (session != null)
				transaction.rollback();
				session.flush();
				//session.close();
			log.error("Error while getting Student Details in AdmissionStatusTransactionImpl"+e);
			throw  new ApplicationException(e); 
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionStatusTransaction#getStudentAdmitCardDetails(int, int)
	 */
	public List<InterviewCard> getStudentAdmitCardDetails(int applicationNo, int interviewTypeId) throws Exception {
		log.info("entered getStudentAdmitCardDetails of AdmissionStatusTransactionImpl");
		Session session = null;
		try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession()
				session = HibernateUtil.getSession();
				List<InterviewCard> studentAdmitCardList = null;
				
				if(interviewTypeId != 0){
					Query query = session.createQuery("from InterviewCard interviewCard " +
							" where interviewCard.admAppln.applnNo = :applicationNo " +
							" and interviewCard.interview.interview.interviewProgramCourse.id = :interviewTypeId");
					query.setInteger("applicationNo", applicationNo);
					query.setInteger("interviewTypeId", interviewTypeId);
					studentAdmitCardList = query.list();
				}else{
					Query query = session.createQuery("from InterviewCard interviewCard " +
							" where interviewCard.admAppln.applnNo = :applicationNo");
					query.setInteger("applicationNo", applicationNo);
					studentAdmitCardList = query.list();
				}
				session.flush();
//				//session.close();
				log.info("End getStudentAdmitCardDetails of AdmissionStatusTransactionImpl");
				return studentAdmitCardList;
		} catch (Exception e) {
			if (session != null)
				session.flush();
				//session.close();
			log.error("Error while getting getStudentAdmitCardDetails in AdmissionStatusTransactionImpl"+e);
			throw  new ApplicationException(e); 
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionStatusTransaction#getApplnAcknowledgement(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean getApplnAcknowledgement(String applicationNo, String dateOfBirth) throws Exception {
		Session session=null;
		boolean exists = false;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ApplnAcknowledgement apln where apln.applnNo="+applicationNo+" and apln.dateOfBirth='"+CommonUtil.ConvertStringToSQLDate(dateOfBirth)+"'";
			Query query = session.createQuery(quer);
			ApplnAcknowledgement appln = (ApplnAcknowledgement)query.uniqueResult();
			if(appln!=null)
				exists=true;
		}catch(Exception exp){
			throw new BusinessException(exp);
		}
		return exists;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionStatusTransaction#getCandidateDetails(com.kp.cms.forms.admission.AdmissionStatusForm)
	 */
	@Override
	public CandidatePGIDetails getCandidateDetails(
			AdmissionStatusForm admissionStatusForm) throws Exception {
		Session session = null;
		CandidatePGIDetails details;
		try{
			session = HibernateUtil.getSession();
			String query = "from CandidatePGIDetails c " +
					" where c.txnRefNo is not null and c.admAppln.applnNo=" +admissionStatusForm.getApplicationNo();
			details = (CandidatePGIDetails)session.createQuery(query).uniqueResult();
		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return details;
	}

	@Override
	public List<InterviewCardHistory> getStudentAdmitCardHistoryDetails( int applicationNo) throws Exception {
		Session session = null;
		try {
				session = HibernateUtil.getSession();
				List<InterviewCardHistory> studentAdmitCardList = null;
					Query query = session.createQuery("from InterviewCardHistory interviewCard " +
							" where interviewCard.admAppln.applnNo = :applicationNo order by interviewCard.id desc");
					query.setInteger("applicationNo", applicationNo);
					studentAdmitCardList = query.list();
				session.flush();
//				//session.close();
				return studentAdmitCardList;
		} catch (Exception e) {
			if (session != null)
				session.flush();
				//session.close();
			throw  new ApplicationException(e); 
		}
	}
	
	// vinodha
	public AdmAppln getApplicationDetailsForMemo(AdmissionStatusForm admForm) throws Exception {

	Session session = null;
	AdmAppln applicantDetails = null;
	int appNO = 0;
	if (StringUtils.isNumeric(admForm.getApplicationNo()))
		appNO = Integer.parseInt(admForm.getApplicationNo());
	try {
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		//session = sessionFactory.openSession();
		session = HibernateUtil.getSession();
		String sql = "";

		sql = "select s.admAppln from StudentIndexMark s where s.admAppln.applnNo=:AppNO and s.course.id=:CourseId " +
				" and s.admAppln.appliedYear=:AdmYear";
		Query qr = session.createQuery(sql);
		qr.setInteger("AppNO", appNO);
		qr.setInteger("CourseId", Integer.parseInt(admForm.getCourseId()));
		qr.setInteger("AdmYear", Integer.parseInt(admForm.getAcademicYear()));
		applicantDetails = (AdmAppln) qr.uniqueResult();
	} catch (Exception e) {
		log.error("Error while getting applicant details..." + e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//			session.close();
		}
	}
	return applicantDetails;
}
}