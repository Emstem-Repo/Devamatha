package com.kp.cms.transactionsimpl.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.smartcard.ScLostCorrectionForm;
import com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import common.Logger;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionTransactionImpl implements IScLostCorrectionTransaction {
	
	private static final Logger log = Logger.getLogger(ScLostCorrectionTransactionImpl.class);

	public static volatile ScLostCorrectionTransactionImpl scLostCorrectionTransactionImpl;

	public static ScLostCorrectionTransactionImpl getInstance() {
		if (scLostCorrectionTransactionImpl == null) {
			scLostCorrectionTransactionImpl = new ScLostCorrectionTransactionImpl();
		}
		return scLostCorrectionTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#verifyRegisterNumberAndGetDetails(com.kp.cms.forms.smartcard.ScLostCorrectionForm)
	 */
	public Student verifyRegisterNumberAndGetDetails(ScLostCorrectionForm scForm) throws Exception {
		
		log.debug("inside verifyRegisterNumberAndGetDetails");
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.registerNo=:registrationNo";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  scForm.getRegNo());
			Student student = (Student) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return student;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#addScLostCorrection(com.kp.cms.bo.smartcard.ScLostCorrection)
	 */
	public boolean addScLostCorrection(ScLostCorrection scCorrection) throws Exception
	{
		log.info("Start of addScLostCorrection of ScLostCorrectionTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(scCorrection);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding ExternalEvaluator in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of addScLostCorrection of ScLostCorrectionTransactionImpl");
		}
	}
		
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#checkForDuplicate(com.kp.cms.forms.smartcard.ScLostCorrectionForm)
	 */
	public ScLostCorrection checkForDuplicate(ScLostCorrectionForm scForm)throws Exception
	{
		log.info("Start of checkForDuplicate of ScLostCorrection TransactionImpl");
		Session session = null;
		Query query = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
				String strQuery="from ScLostCorrection s where s.isActive=1 and s.studentId.registerNo=:registrationNo " +
					"and (s.status='Applied' or s.status='Approved' or s.status='Processed' or s.status='Received')";
				query = session.createQuery(strQuery);
				query.setString("registrationNo",  scForm.getRegNo());
			}
			else{
				String strQuery="from ScLostCorrection s where s.isActive=1 and s.employeeId.fingerPrintId=:empId " +
					"and (s.status='Applied' or s.status='Approved' or s.status='Processed' or s.status='Received')";
				query = session.createQuery(strQuery);
				query.setString("empId",  scForm.getRegNo());
			}
			ScLostCorrection scLostCorrection = (ScLostCorrection) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return scLostCorrection;
		} catch (Exception e) {
			log.error("Error during getScDetails..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#getScHistory(com.kp.cms.forms.smartcard.ScLostCorrectionForm)
	 */
	public List<ScLostCorrection> getScHistory(ScLostCorrectionForm scForm)throws Exception{
		Session session = null;
		List<ScLostCorrection> scHistoryList = null;
		try {
		//	session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
				scHistoryList = session.createQuery("from ScLostCorrection s where s.studentId="+scForm.getStuId()+" order by s.dateOfSubmission").list();
			}
			else{
				scHistoryList = session.createQuery("from ScLostCorrection s where s.employeeId="+scForm.getEmpId()+" order by s.dateOfSubmission").list();
			}
		} catch (Exception e) {			
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
		return scHistoryList;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#checkForPresent(com.kp.cms.forms.smartcard.ScLostCorrectionForm)
	 */
	public ScLostCorrection checkForPresent(ScLostCorrectionForm scForm)throws Exception
	{
		log.info("Start of checkForDuplicate of ScLostCorrection TransactionImpl");
		Session session = null;
		String strQuery = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
				strQuery="from ScLostCorrection s where s.isActive=1 and s.studentId.registerNo=:registrationNo "+
				"and (s.status='Applied' or s.status='Approved' or s.status='Processed' or s.status='Received')";
			}
			else{
				strQuery="from ScLostCorrection s where s.isActive=1 and s.employeeId.fingerPrintId=:registrationNo "+
				"and (s.status='Applied' or s.status='Approved' or s.status='Processed' or s.status='Received')";
			}
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  scForm.getRegNo());
			ScLostCorrection scLostCorrection = (ScLostCorrection) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return scLostCorrection;
		} catch (Exception e) {
			log.error("Error during getScDetails..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#cancelScLostCorrection(int, java.lang.String)
	 */
	public boolean cancelScLostCorrection(int id, String userId) throws Exception{
		
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			ScLostCorrection scLostCorrection=(ScLostCorrection)session.get(ScLostCorrection.class,id);
			scLostCorrection.setStatus("Cancelled");
			scLostCorrection.setLastModifiedDate(new Date());
			scLostCorrection.setModifiedBy(userId);
			session.merge(scLostCorrection);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#verifyRegisterNumberAndGetDetailsAcc(java.lang.String)
	 */
	public Student verifyRegisterNumberAndGetDetailsAcc(String searchCriteria)
	throws Exception{
		
		Session session = null;
		Student student = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery(searchCriteria);
			student = (Student) query.uniqueResult();

		} catch (Exception e) {

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		return student;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction#verifyEmployeeIdAndGetEmployeeDetails(com.kp.cms.forms.smartcard.ScLostCorrectionForm)
	 */
	public Employee verifyEmployeeIdAndGetEmployeeDetails(ScLostCorrectionForm scForm) throws Exception {
		
		log.debug("inside verifyRegisterNumberAndGetDetails");
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			//String strQuery="from Employee e where e.active=1 and e.isActive=1 and e.fingerPrintId=:empId";
			Query query = session.createQuery("from Employee e where e.active=1 and e.isActive=1 and e.fingerPrintId=:empId");
			query.setString("empId",  scForm.getRegNo());
			Employee employee = (Employee) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return employee;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}


}