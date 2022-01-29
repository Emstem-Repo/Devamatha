package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.transactions.fee.IFeeReportTxn;
import com.kp.cms.utilities.HibernateUtil;


/**
 * @author kalyan.c
 * DAO class for Fee Report
 */
public final class FeeReportTxnImpl implements IFeeReportTxn{
	private static FeeReportTxnImpl self=null;
	private static Log log = LogFactory.getLog(FeeReportTxnImpl.class);
	/**
	 * @return
	 * This method is used to get instance of this class
	 */
	public static FeeReportTxnImpl getInstance(){
		if(self==null){
			self= new FeeReportTxnImpl();}
		return self;
	}
	
	private FeeReportTxnImpl(){
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeReportTxn#getFeePaymentsByRegistrationNoAndSems(java.lang.String)
	 * This method is used to get the candidates by registration number
	 */
	public List getFeePaymentsByRegistrationNoAndSems(String registrationNo) throws Exception {
		 Session session = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					 
					 Query query = session.createQuery("from FeePayment feePayment where feePayment.registrationNo = :registrationNo and feePayment.isFeePaid = 1");
							   
					 query.setString("registrationNo", registrationNo);
					 List<FeePayment> list = query.list();
					 //session.close();
					 //sessionFactory.close();
					 return list;
				 } catch (Exception e) {
					 
					 throw e;
				 }
		 }

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeReportTxn#getFeePaymentsByApplicationNoAndSems(java.lang.String)
	 * This method is used to get candidates by application number
	 */
	public List getFeePaymentsByApplicationNoAndSems(String applicationNo) throws Exception {
		 Session session = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					 Query query = session.createQuery("from FeePayment feePayment where feePayment.applicationNo = :applicationNo and feePayment.isFeePaid = 1");					 								
					 query.setString("applicationNo", applicationNo);
					 List<FeePayment> list = query.list();
					 //session.close();
					 //sessionFactory.close();
					 return list;
				 } catch (Exception e) {
					 
					 throw e;
				 }
	 }
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeReportTxn#getFeePaymentsByStudentDetails(int, int, int)
	 * This method is used to get the candidates based on the search criteria
	 */
	public List getFeePaymentsByStudentDetails(int courseID,int year,int semister,String status) throws Exception {
		 Session session = null;
		 Query query = null;
		 Query query1 = null;
		 List<FeePayment> paymentList = new ArrayList<FeePayment>();
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					 if(status!=null && status.equalsIgnoreCase("1")){
						 query = session.createQuery("from FeePayment feePayment inner join feePayment.feePaymentApplicantDetailses feePaymentDetails with feePaymentDetails.semesterNo = :semister  where feePayment.course.id = :courseID and feePayment.academicYear = :year and feePayment.isCancelChallan = 1 and feePayment.isFeePaid = 1");
					 }else if(status!=null && status.equalsIgnoreCase("0")){
						 query = session.createQuery("from FeePayment feePayment inner join feePayment.feePaymentApplicantDetailses feePaymentDetails with feePaymentDetails.semesterNo = :semister  where feePayment.course.id = :courseID and feePayment.academicYear = :year and feePayment.isCancelChallan = 0 and feePayment.isFeePaid = 1");
					 }else{
						 query = session.createQuery("from FeePayment feePayment inner join feePayment.feePaymentApplicantDetailses feePaymentDetails with feePaymentDetails.semesterNo = :semister  where feePayment.course.id = :courseID and feePayment.academicYear = :year and feePayment.isFeePaid = 1"); 
					 }
					 query.setInteger("courseID", courseID);
					 query.setInteger("year", year);
					 query.setInteger("semister", semister);
					 List list = query.list();
					 
					 
					 if(status!=null && status.equalsIgnoreCase("1")){
						 query1 = session.createQuery("from FeePayment feePayment inner join feePayment.feePaymentOptionalFeeGroups feePaymentOptional with feePaymentOptional.semesterNo = :semister  where feePayment.course.id = :courseID and feePayment.academicYear = :year and feePayment.isCancelChallan = 1 and feePayment.isFeePaid = 1");
					 }else if(status!=null && status.equalsIgnoreCase("0")){
						 query1 = session.createQuery("from FeePayment feePayment inner join feePayment.feePaymentOptionalFeeGroups feePaymentOptional with feePaymentOptional.semesterNo = :semister  where feePayment.course.id = :courseID and feePayment.academicYear = :year and feePayment.isCancelChallan = 0 and feePayment.isFeePaid = 1");
					 }else{
						 query1 = session.createQuery("from FeePayment feePayment inner join feePayment.feePaymentOptionalFeeGroups feePaymentOptional with feePaymentOptional.semesterNo = :semister  where feePayment.course.id = :courseID and feePayment.academicYear = :year and feePayment.isFeePaid = 1"); 
					 }
					 query1.setInteger("courseID", courseID);
					 query1.setInteger("year", year);
					 query1.setInteger("semister", semister);
					 List list1 = query1.list();
					 Set<Integer> tempSet = new HashSet<Integer>();
					 if(list!=null){
						 Iterator itr = list.iterator();
						 while(itr.hasNext()){
							 Object[] feePaymentObject =  (Object[]) itr.next();
							 FeePayment feePayment = (FeePayment) feePaymentObject[0];
							 tempSet.add(feePayment.getId());
							 paymentList.add(feePayment);
						 }
					 }
					 
					 if(list1!=null){
						 Iterator itr1 = list1.iterator();
						 while(itr1.hasNext()){
							 Object[] feePaymentObject1 =  (Object[]) itr1.next();
							 FeePayment feePayment1 = (FeePayment)feePaymentObject1[0];
							 if(!tempSet.contains(feePayment1.getId())) {
								 paymentList.add(feePayment1);
							 }
						 }
					 }
					 //session.close();
					 //sessionFactory.close();
					 return paymentList;
				 } catch (Exception e) {
					
					 throw e;
				 }
	 }
	
}
