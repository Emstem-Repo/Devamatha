package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DocumentTypeForm;
import com.kp.cms.transactions.admin.IDocumentTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class DocumentTypeTransactionImpl  implements IDocumentTypeTransaction{
	public static volatile DocumentTypeTransactionImpl documentTypeTransactionImpl = null;
	private static final Log log = LogFactory.getLog(DocumentTypeTransactionImpl.class);

	public static DocumentTypeTransactionImpl getInstance() {
		if (documentTypeTransactionImpl == null) {
			documentTypeTransactionImpl = new DocumentTypeTransactionImpl();
			return documentTypeTransactionImpl;
		}
		return documentTypeTransactionImpl;
	}
	
	/**
	 * This will retrieve all the Document Type from database.
	 * 
	 * @return all DocTypes
	 * @throws Exception
	 */

	public List<DocType> getDocTypes() throws Exception {
		log.debug("inside getDocTypes");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from DocType g where g.isActive = 1 order by g.displayOrder");
			List<DocType> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getDocTypes");
			return list;
		 } catch (Exception e) {
			 log.error("Error during getDocTypes...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * checking doc type duplication 
	 */
	public DocType isDocTypeDuplcated(DocType duplDocType) throws Exception {
		log.debug("inside isDocTypeDuplcated");
		Session session = null;
		DocType docType;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from DocType g where g.name = :tempname and g.displayOrder= :tempDisplayOrder");
			if(duplDocType.getId()!= 0){
				sqlString = sqlString.append(" and id != :tempId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("tempname", duplDocType.getName());
			query.setInteger("tempDisplayOrder", duplDocType.getDisplayOrder());
			if(duplDocType.getId()!= 0){
				query.setInteger("tempId", duplDocType.getId());
			}
			docType = (DocType) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return docType;
	}

	/**
	 * This method add a single DocType to Database.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addDocType(DocType docType) throws Exception {
		log.debug("inside addDocType");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(docType);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addDocType");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving docType..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving docType data..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This method update the DocType to Database.
	 * 
	 * @return true / false based on result.
	 */
	public boolean updateDocType(DocType docType) {
		log.debug("inside updateDocType");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(docType);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving updateDocType");
			return true;
		} catch (Exception e) {
			log.debug("error in updateDocType: impl",e);
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}
	}

	/**
	 * This method delete docType from table.
	 * 
	 * @return true / false based on result.
	 */
	public boolean deleteDocType(int docId, Boolean activate, DocumentTypeForm doForm) throws Exception {
		log.debug("inside deleteDocType");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			DocType docType = (DocType) session.get(DocType.class, docId);
			if (activate) {
				docType.setIsActive(true);
		    } else {
			docType.setIsActive(false);
            }
			//docType.setIsActive(activate);
			docType.setModifiedBy(doForm.getUserId());
			docType.setLastModifiedDate(new Date());
			session.update(docType);
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving deleteDocType");
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during deleting docType data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during deleting docType data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/**
	 * doc short name duplication checking
	 */
	public DocType isDocShortNameDuplcated(DocType duplDocType) throws Exception {
		log.debug("inside isDocShortNameDuplcated");
		Session session = null;
		DocType docType;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from DocType g where g.docShortName = :tempShortName");
			if(duplDocType.getId()!= 0){
				sqlString = sqlString.append(" and id != :tempId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("tempShortName", duplDocType.getDocShortName());
			if(duplDocType.getId()!= 0){
				query.setInteger("tempId", duplDocType.getId());
			}
				docType = (DocType) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocShortNameDuplcated");
		return docType;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public TreeMap<String, String> getDocTypesMap() throws Exception {
		log.debug("inside getDocTypesMap");
		Session session = null;
		TreeMap<String, String> docTypeMap = new TreeMap<String, String>();
		//DocType docType;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from DocType g where g.isActive = 1");
			List<DocType> list = query.list();
			//docType = new DocType();
			if(list!= null && list.size() > 0){
				Iterator<DocType> itr = list.iterator();
				while (itr.hasNext()) {
					DocType docType = (DocType) itr.next();
					docTypeMap.put(docType.getName().trim(), null );
				}
			}
			
			session.flush();
			
			log.debug("leaving getDocTypesMap");
			return docTypeMap;
		 } catch (Exception e) {
			 log.error("Error during getDocTypes...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	public List<DocTypeExams> getDocTypeExamsList(int docTypeId)throws Exception
	{
		Session session = null;
		//DocType docType;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from DocTypeExams d where d.docType.id="+docTypeId);
			List<DocTypeExams> examsList = query.list();
			session.flush();
			log.debug("leaving getDocTypesMap");
			return examsList;
		 } catch (Exception e) {
			 log.error("Error during getDocTypes...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	public DocType isDisplayOrderDuplcated(DocType duplDocType) throws Exception {
		log.debug("inside isDocShortNameDuplcated");
		Session session = null;
		DocType docType;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from DocType g where g.displayOrder = :tempDiplayOrder");
			if(duplDocType.getId()!= 0){
				sqlString = sqlString.append(" and id != :tempId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setInteger("tempDiplayOrder", duplDocType.getDisplayOrder());
			if(duplDocType.getId()!= 0){
				query.setInteger("tempId", duplDocType.getId());
			}
				docType = (DocType) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocShortNameDuplcated");
		return docType;
	}
}
