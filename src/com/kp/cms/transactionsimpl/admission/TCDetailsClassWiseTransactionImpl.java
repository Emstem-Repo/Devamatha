package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ITCDetailsClassWiseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TCDetailsClassWiseTransactionImpl implements ITCDetailsClassWiseTransaction {
	/**
	 * Singleton object of TCDetailsTransactionImpl
	 */
	private static volatile TCDetailsClassWiseTransactionImpl tCDetailsTransactionImpl = null;
	private static final Log log = LogFactory.getLog(TCDetailsClassWiseTransactionImpl.class);
	private TCDetailsClassWiseTransactionImpl() {
		
	}
	/**
	 * return singleton object of TCDetailsTransactionImpl.
	 * @return
	 */
	public static TCDetailsClassWiseTransactionImpl getInstance() {
		if (tCDetailsTransactionImpl == null) {
			tCDetailsTransactionImpl = new TCDetailsClassWiseTransactionImpl();
		}
		return tCDetailsTransactionImpl;
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
	public boolean saveStudentTCDetails(List<StudentTCDetails> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			int count=0;
			for(StudentTCDetails bo:boList)
			{	
				StudentTCDetails studentTCDetails=checkDuplicate(bo.getStudent().getId());
				if(studentTCDetails==null)
				{	
					session.save(bo);
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
				}
				else
				{
					if(studentTCDetails.getStudent().getTcNo()==null)
					{
						bo.setId(studentTCDetails.getId());
						bo.setCreatedBy(studentTCDetails.getCreatedBy());
						bo.setCreatedDate(studentTCDetails.getCreatedDate());
						session.update(bo);
					}
				}
			}	
			transaction.commit();
			
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
	private StudentTCDetails checkDuplicate(int studentId)throws Exception
	{
		Session session = null;
		StudentTCDetails studentTCDetails=null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from StudentTCDetails s where s.student.id="+studentId);
			studentTCDetails = (StudentTCDetails)selectedCandidatesQuery.uniqueResult();
			return studentTCDetails;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		} 
	}
	@Override
	public List<CharacterAndConduct> getAllCharacterAndConduct()
			throws Exception {
		Session session = null;
		List<CharacterAndConduct> list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from CharacterAndConduct c where c.isActive=1");
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
}
