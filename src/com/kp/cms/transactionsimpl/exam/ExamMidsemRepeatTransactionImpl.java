package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidSemRepeatExemption;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.bo.exam.ExamMidsemExemptionDetails;
import com.kp.cms.bo.exam.ExamMidsemRepeat;
import com.kp.cms.bo.exam.ExamMidsemRepeatDetails;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.OnlineBillNumber;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.forms.admin.RepeatMidSemAppForm;
import com.kp.cms.forms.exam.ExamMidsemRepeatForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemRepeatTransactionImpl implements IExamMidsemRepeatTransaction{
	
	private static volatile ExamMidsemRepeatTransactionImpl examMidsemExemptionTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ExamMidsemRepeatTransactionImpl.class);
	private ExamMidsemRepeatTransactionImpl() {
		
	}
	/**
	 * return singleton object of ExamMidsemExemptionTransactionImpl.
	 * @return
	 */
	public static ExamMidsemRepeatTransactionImpl getInstance() {
		if (examMidsemExemptionTransactionImpl == null) {
			examMidsemExemptionTransactionImpl = new ExamMidsemRepeatTransactionImpl();
		}
		return examMidsemExemptionTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getExamMidsemByYear(int)
	 */
	@Override
	public ArrayList<KeyValueTO> getExamMidsemByYear(int year) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		
		String SQL_QUERY = " from ExamDefinition e where e.examType.id='4' " +
				"and e.delIsActive = 1 and e.isActive = 1 and academicYear="+year+" order by e.id";

		Query query = session.createQuery(SQL_QUERY);

		List<ExamDefinition> list = query.list();
		Iterator<ExamDefinition> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinition row = (ExamDefinition) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		return listTO;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getExamByYear(int)
	 */
	public ArrayList<KeyValueTO> getExamByYear(int academicYear) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		
		String SQL_QUERY = "from ExamDefinition e where e.examType.id='5'  " +
				"and e.delIsActive = 1 and e.isActive = 1 and academicYear="+academicYear+" order by e.id";

		Query query = session.createQuery(SQL_QUERY);

		List<ExamDefinition> list = query.list();
		Iterator<ExamDefinition> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinition row = (ExamDefinition) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		return listTO;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getRunSetDataToTable(com.kp.cms.forms.exam.ExamMidsemRepeatForm)
	 */
	@Override
	public List<Object[]> getRunSetDataToTable(ExamMidsemRepeatForm exemptionForm) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String SQL_QUERY="";
		List<Object[]> listTO=null;
		if(exemptionForm.getIsTheory().equalsIgnoreCase("Yes")){
		SQL_QUERY = " Select student.id as stId,classes.id as clId,EXAM_definition.id as meid,subject.id as suId" +
				" from EXAM_marks_entry inner join EXAM_marks_entry_details on EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id " +
				" inner join student ON EXAM_marks_entry.student_id = student.id inner join subject on EXAM_marks_entry_details.subject_id = subject.id " +
				" inner join EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id inner join classes ON EXAM_marks_entry.class_id = classes.id " +
				" where EXAM_definition.id="+exemptionForm.getMidSemExamId() +
				" and student.is_hide=0 and subject.is_active=1 and EXAM_definition.del_is_active=1 and EXAM_marks_entry_details.theory_marks='AA'";
		}else{
			SQL_QUERY = " Select student.id as stId,classes.id as clId,EXAM_definition.id as meid,subject.id as suId" +
			" from EXAM_marks_entry inner join EXAM_marks_entry_details on EXAM_marks_entry_details.marks_entry_id = EXAM_marks_entry.id " +
			" inner join student ON EXAM_marks_entry.student_id = student.id inner join subject on EXAM_marks_entry_details.subject_id = subject.id " +
			" inner join EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id inner join classes ON EXAM_marks_entry.class_id = classes.id " +
			" where EXAM_definition.id="+exemptionForm.getMidSemExamId() +
			" and student.is_hide=0 and subject.is_active=1 and EXAM_definition.del_is_active=1 and ((EXAM_marks_entry_details.theory_marks='AA') or ( EXAM_marks_entry_details.practical_marks='AA'))";
	   }
		Query query = session.createSQLQuery(SQL_QUERY);
		listTO= query.list();
		return listTO;

	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#saveData(java.util.List)
	 */
	@Override
	public boolean saveData(List<ExamMidsemRepeat> allData) throws Exception {
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			Iterator<ExamMidsemRepeat>  itr=allData.iterator();
			while (itr.hasNext()) {
				ExamMidsemRepeat examMidsemBo = (ExamMidsemRepeat) itr.next();
				session.save(examMidsemBo);
				result = true;
			}
			txn.commit();
			session.flush();
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveSubjects..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getExamName(com.kp.cms.forms.exam.ExamMidsemExemptionForm)
	 */
	public ExamDefinitionBO getExamName(ExamMidsemRepeatForm exemptionForm) throws Exception {
		
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="from ExamDefinitionBO e where e.id="+exemptionForm.getExamId() ;
			Query query = session.createQuery(strQuery);
			
			ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) query.uniqueResult();
			session.flush();
			session.close();
			//sessionFactory.close();
			return examDefinitionBO;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public List<ExamMidsemRepeat> getpreviousData(ExamMidsemRepeatForm exemptionForm) throws Exception {
		
		Session session = null;
		List<ExamMidsemRepeat> repeatList=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery=" from ExamMidsemRepeat e where isActive=1 and e.examId.id="+exemptionForm.getExamId() ;
			Query query = session.createQuery(strQuery);
			repeatList=query.list();
			return repeatList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#deleteAllData(java.util.List)
	 */
	@Override
	public boolean deleteAllData(List<ExamMidsemRepeat> previousData,ExamMidsemRepeatForm exemptionForm)
			throws Exception {
		boolean result = false;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			if(previousData != null && !previousData.isEmpty()){
				tx = session.beginTransaction();
					for (ExamMidsemRepeat rep : previousData) {
							rep.setIsActive(false);
							rep.setModifiedBy(exemptionForm.getUserId());
							rep.setLastModifiedDate(new Date());
							if(rep.getExamMidsemRepeatDetails() != null){
								Set<ExamMidsemRepeatDetails> set = new HashSet<ExamMidsemRepeatDetails>();
								for (ExamMidsemRepeatDetails details : rep.getExamMidsemRepeatDetails()) {
									details.setIsActive(false);
									details.setLastModifiedDate(new Date());
									details.setModifiedBy(exemptionForm.getUserId());
									set.add(details);
								}
								rep.setExamMidsemRepeatDetails(set);
							}
						session.update(rep);
					}
					tx.commit();
				}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session.isOpen()) {
				tx.rollback();
			}
			throw new ApplicationException(e);
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getDataForForm(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public ExamMidsemRepeat getDataForForm(LoginForm loginForm) throws Exception {
		
		Session session = null;
		ExamMidsemRepeat repeatList=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery=" from ExamMidsemRepeat e where isActive=1 and e.id="+loginForm.getMidSemRepeatId();
			Query query = session.createQuery(strQuery);
			repeatList=(ExamMidsemRepeat)query.uniqueResult();
			return repeatList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#updateOneData(com.kp.cms.bo.exam.ExamMidsemRepeat)
	 */
	@Override
	public boolean updateOneData(ExamMidsemRepeat oneData) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=true;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(oneData);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				flag=false;
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getCoeDataForForm(com.kp.cms.forms.exam.ExamMidsemRepeatForm)
	 */
	@Override
	public List<ExamMidsemRepeat> getCoeDataForForm(ExamMidsemRepeatForm loginForm) throws Exception {
		
		Session session = null;
		List<ExamMidsemRepeat> repeatList=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery=" from ExamMidsemRepeat e where isActive=1 and e.studentId.registerNo='"+loginForm.getMidSemRepeatRegNo()+
			                 "' and e.examId.id="+loginForm.getExamId();
			Query query = session.createQuery(strQuery);
			repeatList=query.list();
			return repeatList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getApprovedDataForForm(com.kp.cms.forms.exam.ExamMidsemRepeatForm)
	 */
	@Override
	public ExamMidsemRepeat getApprovedDataForForm(ExamMidsemRepeatForm loginForm) throws Exception {
		
		Session session = null;
		ExamMidsemRepeat repeatList=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery=" from ExamMidsemRepeat e where isActive=1 and e.id="+loginForm.getMidSemRepeatId();
			Query query = session.createQuery(strQuery);
			repeatList=(ExamMidsemRepeat) query.uniqueResult();
			return repeatList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#checkForRepeatApplicationExam(int, java.lang.Integer, com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public String checkForRepeatApplicationExam (int id,Integer examId,LoginForm loginForm) throws Exception {
		Session session = null;
		boolean isApproved=false;
		String flag="";
		ExamMidsemRepeat bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from ExamMidsemRepeat stId "+
											   " where stId.isActive=1  and stId.isFeePaid=0 and stId.studentId.id="+id +" and stId.examId.id="+examId);
			 bo=(ExamMidsemRepeat)query.uniqueResult();
			 if(bo!=null){
				loginForm.setMidSemExamId(Integer.toString(bo.getMidsemExamId().getId()));
				loginForm.setMidSemRepeatExamId(Integer.toString(examId));
				loginForm.setMidSemRepeatId(Integer.toString(bo.getId()));
				loginForm.setRepeatExamName(bo.getExamId().getName());
				loginForm.setMidSemStudentId(Integer.toString(id));
				Iterator<ExamMidsemRepeatDetails> itrr=bo.getExamMidsemRepeatDetails().iterator();
				while (itrr.hasNext()) {
					ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
					if(examMidDetails.getIsApproved()!=null && examMidDetails.getIsApproved()){
						isApproved=true;
					}
				}
			
			if(bo!=null){
				if(isApproved)
				 {
					flag="isApproved";
					
				 }else{
					 flag="validData";
				 }
			}else{
				flag="notValidData";
			}
		}else{
			flag="notValidData";
		}
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
		 return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#checkForRepeatFeesPaymentExam(int, java.lang.Integer, com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public String checkForRepeatFeesPaymentExam(int id, Integer examId,	LoginForm loginForm) throws Exception {
		Session session = null;
		boolean isApproved=false;
		boolean isfeePaid=false;
		String flag="";
		ExamMidsemRepeat bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from ExamMidsemRepeat stId "+
											   " where stId.isActive=1  and stId.studentId.id="+id +" and stId.examId.id="+examId);
			 bo=(ExamMidsemRepeat)query.uniqueResult();
			 if(bo!=null){
				loginForm.setMidSemExamId(Integer.toString(bo.getMidsemExamId().getId()));
				loginForm.setMidSemRepeatExamId(Integer.toString(examId));
				loginForm.setMidSemRepeatId(Integer.toString(bo.getId()));
				loginForm.setRepeatExamName(bo.getExamId().getName());
				loginForm.setMidSemStudentId(Integer.toString(id));
				Iterator<ExamMidsemRepeatDetails> itrr=bo.getExamMidsemRepeatDetails().iterator();
				while (itrr.hasNext()) {
					ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
					if(examMidDetails.getIsApproved()!=null && examMidDetails.getIsApproved()){
						isApproved=true;
					}
				}
				if(bo.getIsFeePaid()!=null && bo.getIsFeePaid()){
					isfeePaid=true;
				}
			if(bo!=null){
			  if(isApproved)
				{
				  if(isfeePaid){
					  flag="hallTicket";
				  } else{
				   flag="isApproved";
				    }
				 }else{
				  flag="notValid";
				 	}
			    }else{
				  flag="notValid";
			    }
			 }else{
				 flag="notValid";
			 }
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
		 return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getValidExamId(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public ExamMidsemRepeatSetting getValidExamId(LoginForm loginForm) throws Exception {
		Session session = null;
		ExamMidsemRepeatSetting bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select stId from ExamMidsemRepeatSetting stId "+
					 						   "inner join stId.midSemExamId ed "+
					 						   "inner join ed.examExamCourseSchemeDetailsBOSet courseDef "+
											   " where stId.isActive=1 and courseDef.courseId="+loginForm.getCourseId()
											  +" and CURDATE() between stId.applnStartDate and stId.applnEndDate");
			 bo=(ExamMidsemRepeatSetting)query.uniqueResult();
			 if(bo!=null){
					loginForm.setMidSemAmount(bo.getFeesPerSubject());
					loginForm.setMidSemRepeatExamId(Integer.toString(bo.getMidSemExamId().getId()));
					loginForm.setMidSemAmount(bo.getFeesPerSubject());
					loginForm.setFeeEndDate(CommonUtil.ConvertStringToDateFormat(bo.getFeesEndDate().toString(), "yyyy-mm-dd",
					"dd/mm/yyyy"));
			 }
			return bo;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getValidExamIdForFees(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public ExamMidsemRepeatSetting getValidExamIdForFees(LoginForm loginForm) throws Exception {
		Session session = null;
		ExamMidsemRepeatSetting bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select stId from ExamMidsemRepeatSetting stId "+
					 						   "inner join stId.midSemExamId ed "+
					 						   "inner join ed.examExamCourseSchemeDetailsBOSet courseDef "+
											   " where stId.isActive=1 and courseDef.courseId="+loginForm.getCourseId()
											  +" and CURDATE() between stId.applnStartDate and stId.applnEndDate");
			 		
			 bo=(ExamMidsemRepeatSetting)query.uniqueResult();
			 if(bo!=null){
					loginForm.setMidSemAmount(bo.getFeesPerSubject());
					loginForm.setMidSemRepeatExamId(Integer.toString(bo.getMidSemExamId().getId()));
					loginForm.setMidSemAmount(bo.getFeesPerSubject());
					loginForm.setFeeEndDate(CommonUtil.ConvertStringToDateFormat(bo.getFeesEndDate().toString(), "yyyy-mm-dd",
					"dd/mm/yyyy"));
			 }
			return bo;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#saveOnlinePaymentBo(com.kp.cms.bo.exam.OnlinePaymentReciepts)
	 */
	@Override
	public int saveOnlinePaymentBo(OnlinePaymentReciepts onlinePaymentReciepts)throws Exception {
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
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#updateAndGenerateRecieptNoOnlinePaymentReciept(com.kp.cms.bo.exam.OnlinePaymentReciepts)
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
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#getValidExamIdForFeesPayment(com.kp.cms.forms.usermanagement.LoginForm)
	 */
	@Override
	public ExamMidsemRepeatSetting getValidExamIdForFeesPayment(LoginForm loginForm) throws Exception {
		Session session = null;
		ExamMidsemRepeatSetting bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select stId from ExamMidsemRepeatSetting stId "+
					 						   "inner join stId.midSemExamId ed "+
					 						   "inner join ed.examExamCourseSchemeDetailsBOSet courseDef "+
											   " where stId.isActive=1 and courseDef.courseId="+loginForm.getCourseId()
											   +" and CURDATE()<=stId.feesEndDate group by stId");
			 bo=(ExamMidsemRepeatSetting)query.uniqueResult();
			 if(bo!=null){
					loginForm.setMidSemAmount(bo.getFeesPerSubject());
					loginForm.setMidSemRepeatExamId(Integer.toString(bo.getMidSemExamId().getId()));
					loginForm.setMidSemAmount(bo.getFeesPerSubject());
					loginForm.setFeeEndDate(CommonUtil.ConvertStringToDateFormat(bo.getFeesEndDate().toString(), "yyyy-mm-dd",
					"dd/mm/yyyy"));
			 }
			return bo;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemRepeatTransaction#ReminderMailForFeePayment()
	 */
	@Override
	public ExamMidsemRepeatSetting ReminderMailForFeePayment() throws Exception {
		Session session = null;
		ExamMidsemRepeatSetting bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from ExamMidsemRepeatSetting stId "+
											   " where stId.isActive=1 and DATE_FORMAT(stId.feesEndDate,'%Y-%m-%d')= ADDDATE(DATE_FORMAT(CURDATE(),'%Y-%m-%d'),0)");
			 bo=(ExamMidsemRepeatSetting)query.uniqueResult();
			return bo;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public float getClassHeld(String midSemStudentId, int midSemClassId, int subid)
			throws Exception {
		Session session = null;
		float held=0;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery(" select sum(attendance.hours_held) as held from student "
                     +" inner join attendance_student on attendance_student.student_id = student.id "
                     +" inner join attendance ON attendance_student.attendance_id = attendance.id "
                     +" inner join attendance_class on attendance_class.attendance_id = attendance.id "
                     +" and attendance_class.class_schemewise_id=student.class_schemewise_id "
                     +" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id "
                     +" and student.class_schemewise_id = class_schemewise.id "
                     +" inner join classes on class_schemewise.class_id = classes.id "
                     +" where attendance.subject_id ="+subid 
                     +" and classes.id="+midSemClassId 
                     +" and student.id="+midSemStudentId 
                     +" and attendance.is_activity_attendance=0 " 
                     +" and attendance.is_canceled=0 "
                     +" and attendance_student.is_cocurricular_leave=0");
			 
			 BigDecimal helds=(BigDecimal) query.uniqueResult();
			 held=helds.floatValue();
			return held;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public float getClassPresent(String midSemStudentId, int midSemClassId, int subid)
			throws Exception {
		Session session = null;
		float present=0;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery(" select sum(attendance.hours_held) as held from student "
                                              +" inner join attendance_student on attendance_student.student_id = student.id "
                                              +" inner join attendance ON attendance_student.attendance_id = attendance.id "
                                              +" inner join attendance_class on attendance_class.attendance_id = attendance.id "
                                              +" and attendance_class.class_schemewise_id=student.class_schemewise_id "
                                              +" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id "
                                              +" and student.class_schemewise_id = class_schemewise.id "
                                              +" inner join classes on class_schemewise.class_id = classes.id "
                                              +" where attendance.subject_id ="+subid 
                                              +" and classes.id="+midSemClassId 
                                              +" and student.id="+midSemStudentId 
                                              +" and attendance_student.is_present=1 "
                                              +" and attendance.is_monthly_attendance=0"
                                              +" and attendance.is_activity_attendance=0 "
                                              +" and attendance.is_canceled=0 "
                                              +" and attendance_student.is_cocurricular_leave=0");
			 BigDecimal presents=(BigDecimal) query.uniqueResult();
			 present=presents.floatValue();
			return present;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#repeatMidSemExamReminder(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamMidsemRepeat> repeatMidSemExamReminder(Integer examId) throws Exception {
		Session session = null;
		 session = HibernateUtil.getSession();
		List<ExamMidsemRepeat> bo=new ArrayList<ExamMidsemRepeat>();
		try {
			Query query = session.createQuery("select rep from ExamMidsemRepeat rep "+
			 		"inner join rep.examMidsemRepeatDetails repdetails " +
			 		"where repdetails.isActive=1 and rep.isActive=1 and repdetails.isApproved=1 " +
			 		"and repdetails.isApplied=1 and (rep.isFeePaid=0 or rep.isFeePaid is null) " +
			 		"and (rep.isFeeExempted=0 or rep.isFeeExempted is null) and rep.examId= "+examId 
			 		+" group by rep.studentId");
			 bo=query.list(); 
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
		 return bo;
	}
	
	public List<Object[]> getClassHeldandPresent(String midSemStudentId, int midSemClassId,List<Integer> subjectIdList)throws Exception{
		Session session = null;
		List<Object[]> object=null;
		String subjectList="";
		try {
			
			if(subjectIdList!=null && !subjectIdList.isEmpty()){
				for (Integer subId : subjectIdList) {
					subjectList=subjectList+","+String.valueOf(subId);
				}
			}
			subjectList=subjectList.substring(1);
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select sum(attendance.hours_held) as held," +
			 		" sum(if((attendance_student.is_present =1 or is_cocurricular_leave=1),attendance.hours_held,0)) as totalAttended," +
			 		" attendance.subject_id from student " +
			 		" inner join attendance_student on attendance_student.student_id = student.id " +
			 		" inner join attendance ON attendance_student.attendance_id = attendance.id" +
			 		" inner join attendance_class on attendance_class.attendance_id = attendance.id" +
			 		" and attendance_class.class_schemewise_id=student.class_schemewise_id" +
			 		" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id" +
			 		" inner join classes on class_schemewise.class_id = classes.id" +
			 		" where attendance.subject_id in ("+subjectList+") " +
			 		" and classes.id="+midSemClassId 
			 		+" and student.id="+midSemStudentId
			 		+" and attendance.is_activity_attendance=0 and attendance.attendance_type_id=1 "+
			 		" and attendance.is_canceled=0 group by attendance.subject_id ");
			 object= query.list();
			return object;
		 } catch (Exception e) {
			 throw e;
		 } 
	}
	
	public Object[] getAggregatePercentage(String midSemStudentId, int midSemClassId)throws Exception
	{
		Session session = null;
		Object[] object=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select sum(attendance.hours_held) as held," +
			 		" sum(if((attendance_student.is_present =1 or is_cocurricular_leave=1),attendance.hours_held,0)) as totalAttended" +
			 		" from student" +
			 		" inner join attendance_student on attendance_student.student_id = student.id" +
			 		" inner join attendance ON attendance_student.attendance_id = attendance.id" +
			 		" inner join attendance_class on attendance_class.attendance_id = attendance.id" +
			 		" and attendance_class.class_schemewise_id=student.class_schemewise_id" +
			 		" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id" +
			 		" inner join classes on class_schemewise.class_id = classes.id" +
			 		" where classes.id="+midSemClassId  
			 		+" and student.id="+midSemStudentId
			 		+" and attendance.is_canceled=0 ");
				
			 object= (Object[]) query.uniqueResult();
			return object;
		 } catch (Exception e) {
			 throw e;
		 }                                                                                                     
	}
	
	public Object[] getNoOfAttempts(String courseId, int studentId)throws Exception {
		Session session = null;
		Object[] obj=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select course.no_of_attempts_midsem,count(distinct student.id, rep.exam_id),course.id from EXAM_midsem_repeat rep "
					 							+" inner join EXAM_midsem_repeat_details on EXAM_midsem_repeat_details.midsem_repeat_exam_id =rep.id "
					 							+" inner join student ON rep.student_id = student.id "
					 							+" inner join adm_appln ON student.adm_appln_id = adm_appln.id "
					 							+" inner join course ON adm_appln.selected_course_id = course.id "
					 							+" where EXAM_midsem_repeat_details.is_approved=1 and EXAM_midsem_repeat_details.is_applied=1 and rep.is_fee_paid=1"
					 							+" and rep.is_active=1 and EXAM_midsem_repeat_details.is_active=1 and rep.is_fee_exempted=0"
					 							+" and student.id="+studentId
					 							+" group by rep.student_id");
			 obj= (Object[]) query.uniqueResult();
			 return obj;
			 } catch (Exception e) {
				 throw e;
			 }           	
			 		
	}
	
	public Object[] getNoOfAttemptsCOE(String registerNo)throws Exception {
		Session session = null;
		Object[] obj=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select course.no_of_attempts_midsem,count(distinct student.id, rep.exam_id),course.id from EXAM_midsem_repeat rep "
					 							+" inner join EXAM_midsem_repeat_details on EXAM_midsem_repeat_details.midsem_repeat_exam_id =rep.id "
					 							+" inner join student ON rep.student_id = student.id "
					 							+" inner join adm_appln ON student.adm_appln_id = adm_appln.id "
					 							+" inner join course ON adm_appln.selected_course_id = course.id "
					 							+" where EXAM_midsem_repeat_details.is_approved=1 and EXAM_midsem_repeat_details.is_applied=1 and rep.is_fee_paid=1"
					 							+" and rep.is_active=1 and EXAM_midsem_repeat_details.is_active=1 and rep.is_fee_exempted=0"
					 							+" and student.register_no="+registerNo
					 							+" group by rep.student_id");
			 obj= (Object[]) query.uniqueResult();
			 return obj;
			 } catch (Exception e) {
				 throw e;
			 }           	
			 		
	}
	
	public int getAttemptsByCourseId(String courseId)throws Exception
	{
		Session session = null;
		int count=0;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select no_of_attempts_midsem from course where id="+courseId);
			 count= (Integer) query.uniqueResult();
			 return count;
			 } catch (Exception e) {
				 throw e;
			 }     
	}
	
	public int getAttemptsByRegisterNo(String registerNo)throws Exception
	{
		Session session = null;
		int count=0;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select no_of_attempts_midsem "+
					 							 "from course c "+
					 							 "inner join adm_appln a ON a.selected_course_id = c.id "+
					 							 "inner join student s on s.adm_appln_id = a.id "+
					 							 "where s.register_no= " +registerNo);
			 count= (Integer) query.uniqueResult();
			 return count;
			 } catch (Exception e) {
				 throw e;
			 }     
	}
			 
public boolean getStudentDataForExempt(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception {
		
		Session session = null;
		Object[] repeatList=null;
		boolean dataSet=false;
		try {
			session = HibernateUtil.getSession();
			String strQuery=("select s.admAppln.personalData.firstName,s.admAppln.personalData.middleName, "+
							"s.admAppln.personalData.lastName,rep.examId.name, s.id  "+
							"from ExamMidsemRepeat rep  "+
							"inner join rep.studentId s where s.registerNo="+ repeatMidSemAppForm.getRegisterNo()
							+" and rep.examId.id="+repeatMidSemAppForm.getMidSemExamId() +" and rep.isActive=1 and s.isActive=1");
					Query query = session.createQuery(strQuery);
			repeatList=(Object[])query.uniqueResult();
			if(repeatList!=null && repeatList.length>0)
			{
				String studentName=null;
				if(repeatList[0]!=null && repeatList[0].toString()!=null)
					studentName=repeatList[0].toString();
				if(repeatList[1]!=null && repeatList[1].toString()!=null)
					studentName=studentName+repeatList[1].toString();
				if(repeatList[2]!=null && repeatList[2].toString()!=null)
					studentName=studentName+repeatList[2].toString();
				repeatMidSemAppForm.setStudentName(studentName);
				if(repeatList[3]!=null && repeatList[3].toString()!=null)
				repeatMidSemAppForm.setMidsemExamName(repeatList[3].toString());
				if(repeatList[4]!=null && repeatList[4].toString()!=null)
				repeatMidSemAppForm.setStudentId(repeatList[4].toString());
				
				repeatMidSemAppForm.setChecked("false");
				repeatMidSemAppForm.setTempChecked("false");
				repeatMidSemAppForm.setDataAvailable("true");
				dataSet=true;
			}else
			{
				dataSet=false;
			}
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return dataSet;
	}


public boolean getStudentAlreadyExempted(RepeatMidSemAppForm repeatMidSemAppForm) throws Exception {
	
	Session session = null;
	ExamMidSemRepeatExemption repeatList=null;
	boolean flag=false;
	try {
		session = HibernateUtil.getSession();
		String strQuery=("select e from ExamMidSemRepeatExemption e " +
				"inner join e.studentId s where e.midsemExamId='"+repeatMidSemAppForm.getMidSemExamId()
				+"' and e.isActive=1 and s.registerNo='"+repeatMidSemAppForm.getRegisterNo()+"'");
		Query query = session.createQuery(strQuery);
		repeatList=(ExamMidSemRepeatExemption)query.uniqueResult();
		if(repeatList!=null){
			flag=true;
		}
		return flag;
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
}

public boolean getStudentAlreadyExempted(LoginForm loginForm) throws Exception {
	
	Session session = null;
	ExamMidSemRepeatExemption repeatList=null;
	boolean flag=false;
	try {
		session = HibernateUtil.getSession();
		String strQuery=("select e from ExamMidSemRepeatExemption e " +
				"inner join e.studentId s where e.midsemExamId='"+loginForm.getMidSemRepeatExamId()
				+"' and e.isActive=1 and s.registerNo='"+loginForm.getRegNo()+"'");
		Query query = session.createQuery(strQuery);
		repeatList=(ExamMidSemRepeatExemption)query.uniqueResult();
		if(repeatList!=null){
			flag=true;
		}
		return flag;
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
}

	public boolean updateRepeatMidExemption(ExamMidSemRepeatExemption oneData)  throws Exception{
		
		Session session=null;
		Transaction transaction=null;
		boolean flag=true;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(oneData);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				flag=false;
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
		
	}
	
	public List<ExamMidsemExemption> getStudentExemptedOrNot(LoginForm loginForm) throws Exception
	{
		Session session = null;
		List<ExamMidsemExemption> empt=null;
		boolean flag=false;
		try {
			session = HibernateUtil.getSession();
			String strQuery=("from ExamMidsemExemption empt where empt.examId='"+loginForm.getMidSemRepeatExamId()
					+"' and empt.studentId='"+loginForm.getStudentId()+"' and empt.isActive=1");
					
			Query query = session.createQuery(strQuery);
			empt=query.list();
			
			return empt;
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}
	
	public String checkForRepeatHallticket(int id, LoginForm loginForm) throws Exception{
		Session session = null;
		boolean isApproved=false;
		boolean isfeePaid=false;
		String flag="";
		ExamMidsemRepeat bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from ExamMidsemRepeat stId "+
											   " where stId.isActive=1  and stId.studentId.id="+id +" and stId.examId.id="+loginForm.getMidSemRepeatExamId());
			 bo=(ExamMidsemRepeat)query.uniqueResult();
			 if(bo!=null){
				loginForm.setMidSemExamId(Integer.toString(bo.getMidsemExamId().getId()));
				loginForm.setMidSemRepeatId(Integer.toString(bo.getId()));
				loginForm.setRepeatExamName(bo.getExamId().getName());
				loginForm.setMidSemStudentId(Integer.toString(id));
				Iterator<ExamMidsemRepeatDetails> itrr=bo.getExamMidsemRepeatDetails().iterator();
				while (itrr.hasNext()) {
					ExamMidsemRepeatDetails examMidDetails = (ExamMidsemRepeatDetails) itrr.next();
					if(examMidDetails.getIsApproved()!=null && examMidDetails.getIsApproved()){
						isApproved=true;
					}
				}
				if(bo.getIsFeePaid()!=null && bo.getIsFeePaid()){
					isfeePaid=true;
				}
			if(bo!=null){
			  if(isApproved)
				{
				  if(isfeePaid){
					  flag="hallTicket";
				  } else{
				   flag="isApproved";
				    }
				 }
			    }else{
				  flag="notValid";
			 }
			 }else{
				 flag="notValid";
			 }
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
		 return flag;
	}
	
	public ExamMidsemRepeatSetting GetExamRepeatSettings(ExamMidsemRepeatForm loginForm) throws Exception {
		Session session = null;
		ExamMidsemRepeatSetting bo=null;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from ExamMidsemRepeatSetting stId "+
											   " where stId.isActive=1 and stId.midSemExamId.id="+loginForm.getRepeatExamId());
			 bo=(ExamMidsemRepeatSetting)query.uniqueResult();
			return bo;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
			}
		}
	}
}
