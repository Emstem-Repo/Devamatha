package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IDocumentExamEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class DocumentExamEntryTransactionImpl implements
		IDocumentExamEntryTransaction {
	private static final Log log = LogFactory.getLog(DocumentExamEntryTransactionImpl.class);
	/*fetching the data from database which are active
	 *  (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IDocumentExamEntryTransaction#getDocTypeExams()
	 */
	public List<DocTypeExams> getDocTypeExams() throws Exception {
		Session session = null;
		List<DocTypeExams> docTypeExamList=null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			docTypeExamList = session.createQuery("from DocTypeExams d where d.isActive=1").list();
			session.flush();
			//session.close();
			return docTypeExamList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return docTypeExamList;
		}
	}
	
	/* checking the duplicate Entry
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IDocumentExamEntryTransaction#checkDuplicate(int, java.lang.String)
	 */
	public DocTypeExams checkDuplicate(int docId, String examName)
			throws Exception {
		Session session = null;
		DocTypeExams docTypeExam=null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			docTypeExam =(DocTypeExams)session.createQuery("from DocTypeExams d where d.docType.id=:docId and d.name=:examName").setInteger("docId", docId).setString("examName", examName).uniqueResult();
			session.flush();
			//session.close();
			return docTypeExam;
		}
		catch (RuntimeException  runtimeException){
			log.error("Error during duplicate checking..." , runtimeException);
			return docTypeExam;
		}
		catch (Exception e) {
			log.error("Error during duplicate checking..." , e);
			return docTypeExam;
		}
	}

	/* adding to DocTypeExam to database
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IDocumentExamEntryTransaction#addDocExam(com.kp.cms.bo.admin.DocTypeExams)
	 */
	public boolean addDocExam(DocTypeExams docTypeExams) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(docTypeExams);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (RuntimeException  runtimeException){
			if(transaction!=null)
				transaction.rollback();
			log.error("Error during duplicate checking..." , runtimeException);
			return false;
		}catch (Exception e) {
			log.error("Error during duplicate checking..." , e);
			return false;
		}
	}
	
	/* deleting the docExamType
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IDocumentExamEntryTransaction#deleteDocExamType(int, java.lang.String)
	 */
	public boolean deleteDocExamType(int docTypeExamId, String userId)
			throws Exception {
		log.info("Entering into DocumentExamEntryTransactionImpl of deleteDocExamType");
		Session session = null;
		Transaction transaction = null;
		try {
//			session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			DocTypeExams docTypeExams=(DocTypeExams)session.get(DocTypeExams.class,docTypeExamId);
			docTypeExams.setIsActive(false);
			docTypeExams.setModifiedBy(userId);
			docTypeExams.setLastModifiedDate(new Date());
			session.update(docTypeExams);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
				return false;
			}
			log.error("Exception occured while delete docTypeExams in DocumentExamEntryTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into DocumentExamEntryTransactionImpl of deleteDocExamType");
		}
	}
	
	/* updating the docExamType in database
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IDocumentExamEntryTransaction#updateDocExamType(com.kp.cms.bo.admin.DocTypeExams)
	 */
	public boolean updateDocExamType(DocTypeExams docTypeExams)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(docTypeExams);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (RuntimeException runtime) {	
			if(transaction != null){
				transaction.rollback();
				return false;
			}
			log.error("Exception occured while delete docTypeExams in DocumentExamEntryTransactionImpl :"+runtime);
			throw  new ApplicationException(runtime);
		}catch (Exception e) {
			log.error("Exception occured while delete docTypeExams in DocumentExamEntryTransactionImpl :"+e);
			return false;
		}
	}
	/**
	 * Used to reactivate DocExamType
	 */
	public boolean reactivateDocExamType(int olddocTypeId, String oldExamName,
			String userId) throws Exception {
		log.info("Entering into DocumentExamEntryTransactionImpl of reactivateDocExamType");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		DocTypeExams type = null;
		Transaction transaction = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from DocTypeExams d where d.docType.id=:docTypeId and d.name=:examName");				                          
		 query.setInteger("docTypeId", olddocTypeId);
		 query.setString("examName",oldExamName);
		 type = (DocTypeExams) query.uniqueResult();	
		 transaction = session.beginTransaction();
		 type.setIsActive(true);
		 type.setModifiedBy(userId);
		 type.setLastModifiedDate(new Date());
		 session.update(type);
		 transaction.commit();
		 return true;
		}
		catch (Exception e) {	
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Exception occured while reactivateDocExamType in DocumentExamEntryTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			session.close();
		}
		log.info("Leaving into DocumentExamEntryTransactionImpl of reactivateDocExamType");
		}
	}

	/* edit the docType Exam using docType Exam Id
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IDocumentExamEntryTransaction#editDocTypeExam(java.lang.String)
	 */
	public DocTypeExams editDocTypeExam(String docTypeExamId) throws Exception {
		log.info("Entering into DocumentExamEntryTransactionImpl of deleteDocExamType");
		Session session = null;
		Transaction transaction = null;
		DocTypeExams docTypeExams=null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			int docId=Integer.parseInt(docTypeExamId);
			docTypeExams=(DocTypeExams)session.get(DocTypeExams.class,docId);
			transaction.commit();
			return docTypeExams;
		} catch (RuntimeException runtime) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while delete docTypeExams in DocumentExamEntryTransactionImpl :"+runtime);
			throw  new ApplicationException(runtime);
		}catch (Exception e) {	
			log.error("Exception occured while delete docTypeExams in DocumentExamEntryTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		log.info("Leaving into DocumentExamEntryTransactionImpl of deleteDocExamType");
		}
	}
}
