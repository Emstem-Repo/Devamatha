package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.OnlineExamSupplementaryApplication;
import com.kp.cms.bo.exam.OnlineBillNumber;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.forms.admin.OnlineExamSuppApplicationForm;
import com.kp.cms.transactions.admin.IOnlineExamSuppApplicationTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class OnlineExamSuppApplicationImpl implements IOnlineExamSuppApplicationTransaction{
	
	private static final Logger log = Logger.getLogger(OnlineExamSuppApplicationImpl.class);
	
	public void updateAndGenerateRecieptNoOnlinePaymentReciept(
			OnlinePaymentReciepts onlinePaymentReciepts) throws Exception {
		Session session = null;
		Transaction transaction = null;
		Transaction tx = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			
			Query query = session.createQuery("from OnlineBillNumber o where o.pcFinancialYear.id = :year and o.isActive = 1").setInteger("year",onlinePaymentReciepts.getPcFinancialYear().getId());
			 if(query.list() == null || query.list().size() == 0) {
				 throw new BillGenerationException();
			 }
			 OnlineBillNumber feeBillNumber = (OnlineBillNumber)query.list().get(0);
			 onlinePaymentReciepts.setRecieptNo(Integer.parseInt(feeBillNumber.getCurrentBillNo()));
			 int feeBillNo = Integer.parseInt(feeBillNumber.getCurrentBillNo());
			 Transaction tx2=session.beginTransaction();
			 feeBillNo=feeBillNo+1;
			 feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
			 session.update(feeBillNumber);
			 tx2.commit();
			 
			boolean isExist=false;
			do{
				List<FeePayment> bos=session.createQuery("from OnlinePaymentReciepts f where f.recieptNo='"+feeBillNo+"' and f.pcFinancialYear.id="+onlinePaymentReciepts.getPcFinancialYear().getId()).list();
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
			//Old Code
			Transaction tx1 = session.beginTransaction();
			tx1 = session.beginTransaction();
			tx1.begin();
			onlinePaymentReciepts.setRecieptNo(Integer.parseInt(feeBillNumber.getCurrentBillNo()));
			 session.merge(onlinePaymentReciepts);
			 tx1.commit();
			 transaction = session.beginTransaction();
				transaction.begin();
			session.merge(onlinePaymentReciepts);
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
	
	public boolean saveCertificate( List<OnlineExamSupplementaryApplication> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(boList!=null && !boList.isEmpty()){
				Iterator<OnlineExamSupplementaryApplication> itr=boList.iterator();
				while (itr.hasNext()) {
					OnlineExamSupplementaryApplication bo = itr .next();
					if(bo.getId()>0)
						session.update(bo);
					else
						session.save(bo);
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	public void getStudentId(int userId,OnlineExamSuppApplicationForm crForm) throws Exception {
		Session session=null;
		String id="";
		try{
			session=HibernateUtil.getSession();
			String quer="select s.id from Student s inner join s.studentLogins log where log.id="+userId;
			Query query=session.createQuery(quer);
			id=query.uniqueResult().toString();
			crForm.setStudentId(Integer.parseInt(id));
		}catch(Exception e){
			log.error("Error while getting getStudentId.." +e);
		}
	}

	@Override
	public boolean CheckStudentAlreadyApplied(OnlineExamSuppApplicationForm crForm) throws Exception {
		Session session=null;
		boolean flag=false;
		OnlineExamSupplementaryApplication obj=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from OnlineExamSupplementaryApplication o where o.studentId.id=" +crForm.getStudentId() 
			+ " and o.dateOfExam >= current_date() and o.isActive=1 and o.studentId.id in (select r.student.id from OnlinePaymentReciepts r where r.isPaymentFailed=0 and r.applicationFor like 'Holistic%')";
			Query query=session.createQuery(quer);
			obj=(OnlineExamSupplementaryApplication) query.uniqueResult();
			if(obj!=null){
				flag= true;
			}
			setOnlineReceiptDetails(crForm);
		}catch(Exception e){
			log.error("Error while getting CheckStudentAlreadyApplied.." +e);
		}
		return flag;
	}
	public void setOnlineReceiptDetails(OnlineExamSuppApplicationForm crForm) throws Exception {
		Session session=null;
		Object [] obj=null;
		try{
			session=HibernateUtil.getSession();
			
			String quer="select online_payment_reciept.id,online_payment_reciept.transaction_date  from online_payment_reciept "+
						"JOIN student ON online_payment_reciept.student_id = student.id "+
						"JOIN online_exam_supplementary_application ON  online_exam_supplementary_application.student_id = student.id "+
						"Where online_exam_supplementary_application.date_of_exam >= curdate() " +
						"and online_payment_reciept.application_for like 'Holistic%' "+
						"and online_payment_reciept.is_payment_failed=0  and online_payment_reciept.is_active=1 " +
						"and date(online_exam_supplementary_application.applied_date)= date(online_payment_reciept.transaction_date) "+
						"and online_exam_supplementary_application.is_active=1 and online_exam_supplementary_application.student_id="+crForm.getStudentId();
					
					/*"from OnlinePaymentReciepts r "+
			"where r.student.id in "+
			"(select o.studentId.id from OnlineExamSupplementaryApplication o where o.studentId.id="+crForm.getStudentId()+" and o.stdClassId.id=" +crForm.getStdClassId()+") "+
			"and r.applicationFor like 'Holistic%' and r.isPaymentFailed=0";*/
			Query query=session.createSQLQuery(quer);
			obj=(Object[]) query.uniqueResult();
			if(obj!=null){
			crForm.setOnlinePaymentId(Integer.parseInt(obj[0].toString()));
			crForm.setTempOnlinePayId(Integer.parseInt(obj[0].toString()));
			crForm.setAppliedDate(CommonUtil.ConvertStringToDateFormat(obj[1].toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
			}
		}catch(Exception e){
		}
	}
	
	public int GetOnlineExamSuppCount(String year, String Venue, String dateOfExam)throws Exception
	{
		Session session=null;
		Integer count=0;
		Long count1= null;
		try{
			Date dateinSQlFormat=CommonUtil.ConvertStringToSQLDate(dateOfExam);
			session=HibernateUtil.getSession();
			String quer="select count(s.id) from OnlineExamSupplementaryApplication s " +
					"where s.academicYear='"+ year+"' and s.isActive=1 and s.venue like '%"+Venue+"%' " +
					"and s.dateOfExam='" +dateinSQlFormat +"'";
			Query query=session.createQuery(quer);
			count1= (Long)query.uniqueResult();
			count = count1.intValue();
		}catch(Exception e){
			log.error("Error while getting GetOnlineExamSuppCount.." +e);
		}
		return count;
	}
	public OnlineExamSupplementaryApplication getOnlineExamSupplementaryApplication (OnlinePaymentReciepts os)throws Exception
	{
		Session session=null;
		OnlineExamSupplementaryApplication record=null;
		
		try{
			session=HibernateUtil.getSession();
			String quer="from OnlineExamSupplementaryApplication o where o.dateOfExam>=current_Date() and o.isActive=1 and o.studentId.id in (select r.student.id from OnlinePaymentReciepts r where r.id="+os.getId()+" and r.applicationFor like 'Holistic%' and r.isActive=1)";
			Query query=session.createQuery(quer);
			record=(OnlineExamSupplementaryApplication) query.uniqueResult();
		}catch(Exception e){
			log.error("Error while getting GetOnlineExamSuppCount.." +e);
		}
		return record;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IOnlineExamSuppApplicationTransaction#getConvocationDetails(java.lang.String)
	 */
	@Override
	public ConvocationSession getConvocationDetails(String courseId) throws Exception {
		Session session = null;
		ConvocationSession convocationSession = null;
		try {
			session = HibernateUtil.getSession();
			convocationSession = (ConvocationSession)session.createQuery("select cou.session from ConvocationCourse cou where cou.session.date>=curDate() and cou.course.id="+courseId).uniqueResult();
			return convocationSession;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return convocationSession;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IOnlineExamSuppApplicationTransaction#checkMaxGuestAvailable(int)
	 */
	@Override
	public boolean checkMaxGuestAvailable(int sessionId,int maxGuest,OnlineExamSuppApplicationForm crForm) throws Exception {
		Session session = null;
		boolean available = true;
		try {
			session = HibernateUtil.getSession();
			List<Integer> courses = session.createQuery("select cou.course.id from ConvocationCourse cou where cou.isActive=1 and cou.session.id="+sessionId).list();
			if(courses != null){
				String query="select sum (cou.guestPassCount) from ConvocationRegistration cou where cou.isActive=1 and cou.student.admAppln.courseBySelectedCourseId.id in (:CourseIds)";
				Long count = (Long)session.createQuery(query).setParameterList("CourseIds", courses).uniqueResult();
				if(count != null && maxGuest<=count){
					available = false;
				}
				if(count != null && maxGuest-count == 1){
					crForm.setOnepassAvailbale(true);
				}else{
					crForm.setOnepassAvailbale(false);
				}
				crForm.setPassAvailable(available);
			}
			return available;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return available;
	}

	
	
	

}
