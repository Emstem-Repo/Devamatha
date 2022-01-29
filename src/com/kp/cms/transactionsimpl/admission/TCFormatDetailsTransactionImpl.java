package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ITCFormatDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TCFormatDetailsTransactionImpl implements
		ITCFormatDetailsTransaction {
	/**
	 * Singleton object of TCFormatDetailsTransactionImpl
	 */
	private static volatile TCFormatDetailsTransactionImpl tCFormatDetailsTransactionImpl = null;
	private static final Log log = LogFactory.getLog(TCFormatDetailsTransactionImpl.class);
	private TCFormatDetailsTransactionImpl() {
		
	}
	/**
	 * return singleton object of TCFormatDetailsTransactionImpl.
	 * @return
	 */
	public static TCFormatDetailsTransactionImpl getInstance() {
		if (tCFormatDetailsTransactionImpl == null) {
			tCFormatDetailsTransactionImpl = new TCFormatDetailsTransactionImpl();
		}
		return tCFormatDetailsTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITCDetailsTransaction#getStudentDetails(java.lang.String)
	 */
	@Override
	public List<Student> getStudentDetails(String query) throws Exception {
		Session session = null;
		List<Student> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITCDetailsTransaction#getStudentTCDetails(java.lang.String)
	 */
	@Override
	public Student getStudentTCDetails(String query) throws Exception {
		Session session = null;
		Student student = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			student = (Student)selectedCandidatesQuery.uniqueResult();
			return student;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITCDetailsTransaction#saveStudentTCDetails(com.kp.cms.bo.admin.StudentTCDetails)
	 */
	@Override
	public boolean saveStudentTCDetails(StudentTCDetails bo) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(bo);
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
	/**
	 * 
	 * @param tcDetailsList
	 * @return
	 * @throws Exception
	 */
	public boolean processUpdateTCDetails(List<StudentTCDetails> tcDetailsList) throws Exception {
		log.info("Inside updateSgpa");
		StudentTCDetails tcDetails;
		Transaction tx=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Iterator<StudentTCDetails> itr = tcDetailsList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				tcDetails = itr.next();
				
				String sqlQuey = " delete from StudentTCDetails s where s.student.id = " + tcDetails.getStudent().getId();
				Query query = session.createQuery(sqlQuey);
				query.executeUpdate();
				
				session.save(tcDetails);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			log.info("End of updateSgpa");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			log.error("Error occured in updateSgpa");
			throw new ApplicationException(e);
		}
	}
}
