package com.kp.cms.transactionsimpl.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.transactions.admission.IDocumentVerificationEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author Nagarjun
 *
 */
public class DocumentVerificationEntryTxImpl implements IDocumentVerificationEntryTransaction {

	/**
	 * Singleton object of AssignClassForStudentTransactionImpl
	 */
	private static volatile DocumentVerificationEntryTxImpl assignClassForStudentTransactionImpl = null;
	private static final Log log = LogFactory.getLog(DocumentVerificationEntryTxImpl.class);
	private DocumentVerificationEntryTxImpl() {
		
	}
	/**
	 * return singleton object of AssignClassForStudentTransactionImpl.
	 * @return
	 */
	public static DocumentVerificationEntryTxImpl getInstance() {
		if (assignClassForStudentTransactionImpl == null) {
			assignClassForStudentTransactionImpl = new DocumentVerificationEntryTxImpl();
		}
		return assignClassForStudentTransactionImpl;
	}
	public Student getStudentDetails(String registerNo) throws Exception{
		Student student=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			if(registerNo != null && !registerNo.trim().isEmpty()){
				student = (Student)session.createQuery("from Student s where s.registerNo='"+registerNo+"'").uniqueResult();
			}
		}catch (Exception e) {
			if(session != null){
				session.close();
			}
		}
		finally {
			if (session != null) {
				session.flush();
			}
		}
		return student;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IDocumentVerificationEntryTransaction#saveStudentDocs(com.kp.cms.bo.admin.AdmAppln)
	 */
	public boolean saveStudentDocs(AdmAppln appBO) throws Exception{
		Session session = null;
		Transaction tx = null;
		boolean save = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(appBO);
			tx.commit();
			session.flush();
			session.close();
			save = true;
		}catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			if(session != null){
				session.close();
			}
			throw new Exception();
		}
		return save;
	}
}
