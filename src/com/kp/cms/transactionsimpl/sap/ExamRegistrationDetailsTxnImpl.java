package com.kp.cms.transactionsimpl.sap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.OnlineBillNumber;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.ExamRegistrationFeeAmount;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamRegistrationDetailsTxnImpl implements IExamRegistrationDetailsTransaction{
private static volatile ExamRegistrationDetailsTxnImpl impl = null;
private static final Log log = LogFactory.getLog(ExamRegistrationDetailsTxnImpl.class);
public static ExamRegistrationDetailsTxnImpl getInstance(){
	if(impl == null){
		impl = new ExamRegistrationDetailsTxnImpl();
		return impl;
	}
	return impl;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getWorkLocationDetailsList()
 */
@Override
public Map<Integer,String>  getWorkLocationDetailsList(ExamRegistrationDetailsForm objForm) throws Exception {
	Map<Integer,String> workLocationMap = new HashMap<Integer, String>();
	Session session = null;
	try{
		session = HibernateUtil.getSession();
		Query query =session.createQuery("select examVenue.workLocationId.id, examVenue.workLocationId.name from SapVenue examVenue" +
										 " where examVenue.isActive=1 group by examVenue.workLocationId");
		List<Object[]> boList = query.list();
		if(boList!=null && !boList.isEmpty()){
			Iterator<Object[]> iterator = boList.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				workLocationMap.put(Integer.valueOf(obj[0].toString()), obj[1].toString());
			}
		}
	}catch (Exception e) {
		if (session != null){
			session.flush();
			//session.close();
		}	
		throw  new ApplicationException(e);
	}
	return CommonUtil.sortMapByValue(workLocationMap);
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getDateSessionDetailsList(int)
 */
@Override
public List<Object[]> getDateSessionDetailsOfWorkLocation(int workLocationId) throws Exception {
	List<Object[]> examScheduleVenuesList;
	Session session = null;
	try{
		session = HibernateUtil.getSession();
		Query query = session.createSQLQuery("select exam_schedule_date.id as sessionid,exam_schedule_date.exam_date,exam_schedule_date.session, " +
											 " sum(exam_venue.capacity) from exam_schedule_venue" +
											 " left join exam_schedule_date on exam_schedule_venue.exam_schedule_date_id = exam_schedule_date.id" +
											 " left join exam_venue on exam_schedule_venue.exam_venue_id = exam_venue.id" +
											 " where exam_venue.work_location_id ="+workLocationId+" and exam_schedule_date.is_active=1" +
											 " and exam_schedule_venue.is_active=1 and exam_venue.is_active=1 " +
											 " and  exam_schedule_date.exam_date>=curdate() " +
											 " group by exam_schedule_date.id " +
											 " order by exam_schedule_date.exam_date asc,exam_schedule_date.session_order");
		examScheduleVenuesList= query.list();
	}catch (Exception e) {
		if (session != null){
			session.flush();
			//session.close();
		}	
		throw  new ApplicationException(e);
	}
	return examScheduleVenuesList;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getPreviousDetailsOfWorkLocation(int)
 */
@Override
public List<Object[]> getPreviousDetailsOfWorkLocation( int workLocationId) throws Exception {
	List<Object[]> obj;
	Session session =null;
	try{
		session = HibernateUtil.getSession();
		Query query = session.createSQLQuery("select exam_schedule_date.id as sessionId,count(exam_venue.id) from exam_registration_details " +
				" left join exam_schedule_date ON exam_registration_details.exam_schedule_date_id = exam_schedule_date.id" +
				" left join exam_venue ON exam_registration_details.venue_id = exam_venue.id" +
				" where exam_venue.work_location_id ="+workLocationId+" and exam_registration_details.is_active=1 " +
				" and exam_registration_details.is_cancelled=0 and exam_registration_details.is_payment_failed=0 " +
				" group by exam_schedule_date.id");
		obj =query.list();
	}catch (Exception e) {
		if (session != null){
			session.flush();
			//session.close();
		}	
		throw  new ApplicationException(e);
	}
	return obj;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getVenueDetails(int, int)
 */
@Override
public List<ExamScheduleVenue> getVenueDetails(int workLocationId, int examSessionId) throws Exception {
	List<ExamScheduleVenue> list = null;
	Session session =null;
	try{
		session = HibernateUtil.getSession();
		String hqlQuery= "from ExamScheduleVenue eVenue where eVenue.venue.workLocationId="+workLocationId+
										 " and eVenue.examScheduleDate.id="+examSessionId+ " and eVenue.isActive=1"+
				 						 " and eVenue.examScheduleDate.isActive =1 and eVenue.venue.isActive=1" +
				 						 " order by eVenue.priority";
		Query query = session.createQuery(hqlQuery);
		list = query.list();
	}catch (Exception e) {
		if (session != null){
			session.flush();
			//session.close();
		}	
		throw  new ApplicationException(e);
	}
	return list;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getAllotedVenuesForSession(int, int)
 */
@Override
public  Map<Integer, Integer> getAllotedVenuesForSession( int workLocationId, int examSessionId) throws Exception {
	 Map<Integer, Integer> allotedVenueMap = new HashMap<Integer, Integer>();
	Session session =null;
	try{
		session = HibernateUtil.getSession();
		Query sqlQuery = session.createSQLQuery("select exam_venue.id,count(exam_venue.id) " +
												" from exam_registration_details left join exam_schedule_date " +
												" ON exam_registration_details.exam_schedule_date_id = exam_schedule_date.id" +
												" left join exam_venue ON exam_registration_details.venue_id = exam_venue.id" +
												" where exam_venue.work_location_id ="+workLocationId+" and exam_registration_details.is_active=1 " +
												" and exam_registration_details.is_cancelled=0 and exam_registration_details.is_payment_failed=0" +
												" and exam_schedule_date.id ="+examSessionId+" group by exam_registration_details.venue_id");
		List<Object[]> objList = sqlQuery.list();
		if(objList!=null && !objList.isEmpty()){
			Iterator<Object[]> iterator = objList.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				allotedVenueMap.put(Integer.valueOf(obj[0].toString()), Integer.valueOf(obj[1].toString()));
			}
		}
	}catch (Exception e) {
		if (session != null){
			session.flush();
			//session.close();
		}	
		throw  new ApplicationException(e);
	}
	return allotedVenueMap;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getWorkLocationAndVenueByIds(int)
 */
/*@Override
public SapVenue getWorkLocationAndVenueByIds(int venueId) throws Exception {
	Session session =null;
	SapVenue venue = new SapVenue();
	try{
		session=HibernateUtil.getSession();
		Query query = session.createQuery("from SapVenue examVenue where examVenue.id="+venueId+"and  examVenue.isActive=1");
		venue = (SapVenue) query.uniqueResult();
	}catch (Exception e) {
		if (session != null){
			session.flush();
			//session.close();
		}	
		throw  new ApplicationException(e);
	}
	return venue;
}*/
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#saveRegistrationDetails(com.kp.cms.bo.sap.ExamRegistrationDetails)
 */
@Override
public int saveRegistrationDetails(ExamRegistrationDetails registrationDetails) throws Exception {
	Session session = null;
	Transaction tx = null;
	try{
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		session.saveOrUpdate(registrationDetails);
		tx.commit();
		session.flush();
		session.close();
	}catch (Exception e) {
		if ( tx != null){
			tx.rollback();
		}
		if (session != null){
			session.flush();
			session.close();
		}
		return 0;
	}
	return registrationDetails.getId();
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#saveOnlinePaymentReciepts(com.kp.cms.bo.exam.OnlinePaymentReciepts)
 */
@Override
public int saveOnlinePaymentReciepts(OnlinePaymentReciepts onlinePaymentReciepts)
		throws Exception {
	Session session = null;
	Transaction tx = null;
	try{
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		session.save(onlinePaymentReciepts);
		tx.commit();
		session.flush();
		session.close();
	}catch (Exception e) {
		if ( tx != null){
			tx.rollback();
		}
		if (session != null){
			session.flush();
			session.close();
		}
		return 0;
	}
	return onlinePaymentReciepts.getId();
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#updateAndGenerateRecieptNoOnlinePaymentReciept(com.kp.cms.bo.exam.OnlinePaymentReciepts)
 */
@Override
public void updateAndGenerateRecieptNoOnlinePaymentReciept( OnlinePaymentReciepts paymentReciepts) throws Exception {
	Session session = null;
	Transaction transaction = null;
	Transaction tx = null;
	try {
		SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();
		
		Query query = session.createQuery("from OnlineBillNumber o where o.pcFinancialYear.id = :year and o.isActive = 1").setInteger("year",paymentReciepts.getPcFinancialYear().getId());
		 if(query.list() == null || query.list().size() == 0) {
			 throw new BillGenerationException();
		 }
		 OnlineBillNumber feeBillNumber = (OnlineBillNumber)query.list().get(0);
		 paymentReciepts.setRecieptNo(Integer.parseInt(feeBillNumber.getCurrentBillNo()));
		 int feeBillNo = Integer.parseInt(feeBillNumber.getCurrentBillNo());
		 Transaction tx2=session.beginTransaction();
		 feeBillNo=feeBillNo+1;
		 feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
		 session.update(feeBillNumber);
		 tx2.commit();
		boolean isExist=false;
		do{
			List<FeePayment> bos=session.createQuery("from OnlinePaymentReciepts f where f.recieptNo='"+feeBillNo+"' and f.pcFinancialYear.id="+paymentReciepts.getPcFinancialYear().getId()).list();
			if(bos!=null && !bos.isEmpty()){
				isExist=true;
				feeBillNo=feeBillNo+1;
				feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
			}else{
				isExist=false;
			}
		}while(isExist);
		tx=session.beginTransaction();
		tx.begin();
		feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
		session.merge(feeBillNumber);
		tx.commit();
		Transaction tx1 = session.beginTransaction();
		tx1 = session.beginTransaction();
		tx1.begin();
		paymentReciepts.setRecieptNo(Integer.parseInt(feeBillNumber.getCurrentBillNo()));
		 session.merge(paymentReciepts);
		 tx1.commit();
		 transaction = session.beginTransaction();
			transaction.begin();
		session.merge(paymentReciepts);
		transaction.commit();
		session.flush();
		session.close();
	} catch (Exception e) {
		if ( transaction != null){
			transaction.rollback();
		}
		if (session != null){
			session.flush();
			session.close();
		}
	}
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getFeeAmount()
 */
@Override
public Double getFeeAmount(String examType) throws Exception {
	Double feeAmount =null;
	Session session = null;
	try{
		session = HibernateUtil.getSession();
		Query query = null;
		if(examType.equalsIgnoreCase("supplementry")){
			 query = session.createQuery("select feeAmount.amount from ExamRegistrationFeeAmount feeAmount where feeAmount.isActive=1 and feeAmount.description='SAPSUPPLEMENTRY'");
		}else if(examType.equalsIgnoreCase("regular")){
			 query = session.createQuery("select feeAmount.amount from ExamRegistrationFeeAmount feeAmount where feeAmount.isActive=1 and feeAmount.description='SAP'");
		}
		BigDecimal amount = (BigDecimal) query.uniqueResult();
		feeAmount = amount.doubleValue();
	}catch (Exception e) {
		log.error("Error in getFeeAmount...",e);
		 session.flush();
		 session.close();
		throw new ApplicationException(e);
	}
	return feeAmount;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getCheckIsAlreadyRegisteredForExam(int)
 */
@Override
public ExamRegistrationDetails getCheckIsAlreadyRegisteredForExam(int studentId) throws Exception {
	ExamRegistrationDetails details = null;
	Session session = null;
	try{
		 String todayDate = CommonUtil.getTodayDate();
		 java.util.Date currentDate = CommonUtil.ConvertStringToSQLDate(todayDate);
		session = HibernateUtil.getSession();
		Query query= session.createQuery("from ExamRegistrationDetails examDetails where examDetails.isActive=1 " +
										" and examDetails.isCancelled=0 and examDetails.isPaymentFailed =0" +
										" and examDetails.studentId.id="+studentId+" and examDetails.examScheduleDateId.examDate>='"+currentDate+"'");
//										" group by examDetails.studentId.id");
		details = (ExamRegistrationDetails) query.uniqueResult();
	}catch (Exception e) {
		log.error("Error in getCheckIsAlreadyRegisteredForExam...",e);
		 session.flush();
		 session.close();
		throw new Exception(e);
	}
	return details;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#checkIsAlreadyCancelledRegistration(int)
 */
@Override
public ExamRegistrationDetails checkIsAlreadyCancelledRegistration(int studentId) throws Exception {
	ExamRegistrationDetails details = null;
	Session session = null;
	try{
		String todayDate = CommonUtil.getTodayDate();
		 java.util.Date currentDate = CommonUtil.ConvertStringToSQLDate(todayDate);
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from ExamRegistrationDetails examDetails where examDetails.isActive=1 " +
											" and examDetails.studentId.id="+studentId+" and examDetails.isCancelled=1" +
											" and examDetails.isPaymentFailed =1 and examDetails.examScheduleDateId.examDate>='"+currentDate+"'");
		List<ExamRegistrationDetails> list =  query.list();
		if(list!=null && !list.isEmpty()){
			details = list.get(0);
		}else{
			Query query1 = session.createQuery("from ExamRegistrationDetails examDetails where examDetails.isActive=1 " +
					                           " and examDetails.studentId.id="+studentId+" and examDetails.isCancelled=0" +
					                           " and examDetails.isPaymentFailed =1 and examDetails.examScheduleDateId.examDate>='"+currentDate+"'");
			List<ExamRegistrationDetails> list1 =  query1.list();
			if(list1!=null && !list1.isEmpty()){
				details = list1.get(0);
			}
		}
	}catch (Exception e) {
		log.error("Error in checkIsAlreadyCancelledRegistration...",e);
		 session.flush();
		 session.close();
		throw new ApplicationException(e);
	}
	return details;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.sap.IExamRegistrationDetailsTransaction#getReceiptNumber(com.kp.cms.forms.sap.ExamRegistrationDetailsForm)
 */
@Override
public int getReceiptNumber(ExamRegistrationDetailsForm objForm) throws Exception {
	Session session = null;
	Transaction transaction = null;
	int receiptNumber=0;
	// 1. Try to fetch current id for that year from pc_receipt_id_generator
	// 2. If no record then...make an entry for that financial year and the generated id is used rcpt number (id, year)
	// 3. If entry present then ++ the entry and then use the new id
	try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction=session.beginTransaction();
			Query query1 = session.createQuery("select p from PcReceiptNumber p where p.pcFinancialYear.id="+objForm.getFinancialYear());
			query1.setLockMode("p", LockMode.UPGRADE);
			PcReceiptNumber pcg = (PcReceiptNumber) query1.uniqueResult();
			receiptNumber=pcg.getCurrentNo();
			receiptNumber=receiptNumber+1;
			pcg.setCurrentNo(receiptNumber);
			session.update(pcg);
			transaction.commit();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} 
		finally{
			if (session != null){
				session.flush();
				session.close();
			}
		}
	return receiptNumber;
	
  }
@Override
public int getSAPMarksOfStudent(String hqlQuery) throws Exception {
	Session session =null;
	Integer sapMarks =null;
	int marks= 0;
	try{
		session = HibernateUtil.getSession();
		Query query = session.createSQLQuery(hqlQuery);
		sapMarks= (Integer) query.uniqueResult();
	}catch (Exception e) {
		throw new ApplicationException(e);
	} 
	finally{
		if (session != null){
			session.flush();
			session.close();
		}
	}
	if(sapMarks==null || sapMarks.toString().isEmpty()){
		marks =0;
	}
	return marks;
}
}
