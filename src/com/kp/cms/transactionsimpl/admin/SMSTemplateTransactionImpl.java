package com.kp.cms.transactionsimpl.admin;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.ISMSTemplateTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SMSTemplateTransactionImpl implements ISMSTemplateTransaction{


	private static final Log log = LogFactory.getLog(TemplateImpl.class);
	public static volatile SMSTemplateTransactionImpl templateImpl = null;

	public static SMSTemplateTransactionImpl getInstance() {
		if (templateImpl == null) {
			templateImpl = new SMSTemplateTransactionImpl();
			return templateImpl;
		}
		return templateImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#savesmsTemplate(com.kp.cms.bo.admin.smsTemplate)
	 */
	public boolean saveSMSTemplate(SMSTemplate smsTemplate) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 tx = session.beginTransaction();
			 tx.begin();
			 session.saveOrUpdate(smsTemplate);
			 tx.commit();
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 if(tx!=null)
			      tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 if(tx!=null)
			     tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception");
			 throw e;				 
		 }finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		 } 
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getsmsTemplates(int)
	 */
	public List<SMSTemplate> getSMSTemplates(int id) throws Exception {
		Session session = null;
		List<SMSTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (id != 0) {
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.id = :id");
				query.setInteger("id", id);
				list = query.list();
			} else {
				Query query = session.createQuery("from SMSTemplate");
				list = query.list();
			}
			 return list;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			 throw e;
		 }finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		 }
	}
		
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#deletesmsTemplate(com.kp.cms.bo.admin.smsTemplate)
	 */
	public boolean deleteSMSTemplate(SMSTemplate smsTemplate) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.delete(smsTemplate);
			 tx.commit();
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 if(tx!=null)
			     tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 if(tx!=null)
			     tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception");
			 throw e;				 
		 }finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		 }  
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getDuplicateCheckList(int, java.lang.String)
	 */
	public List<SMSTemplate> getDuplicateCheckList(int courseId, String templateName)
			throws Exception {
		Session session = null;
		List<SMSTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.course.id = :courseId and smsTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
				if(list.size() <= 0){
					int programId = getProgrameId(courseId);
					Query query1 = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.program.id = :programId and smsTemplate.templateName = :templateName");
					query1.setInteger("programId", programId);
					query1.setString("templateName", templateName);
					list = query1.list();
					
				}
			} else {
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}
	/**
	 * 
	 */
	public int getProgrameId(int courseId) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Program p where isActive = 1 and programType.isActive = 1 and id = " + courseId);
			Program program = (Program) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			if(program!= null){
				return program.getId();
			}
			else{
				return 0;
			}
		 } catch (Exception e) {
			 log.error("Error during getting program...",e);
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getDuplicateCheckList(int, java.lang.String)
	 */
	public List<SMSTemplate> checkDuplicate(int courseId, String templateName, int programId) throws Exception {
		Session session = null;
		List<SMSTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.course.id = :courseId and smsTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
			 }
			 else if (programId != 0){
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.program.id = :programId and smsTemplate.templateName = :templateName");
				query.setInteger("programId", programId);
				query.setString("templateName", templateName);
				list = query.list();
			} else {
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}
	
	public List<SMSTemplate> getSMSTemplate(int courseId, String templateName, int programId)
			throws Exception {
		Session session = null;
		List<SMSTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.course.id = :courseId and smsTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
				
			} 
			 if(programId!= 0 && (list == null || list.size() <= 0)){
				 Query query1 = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.program.id = :programId and smsTemplate.templateName = :templateName");
					query1.setInteger("programId", programId);
					query1.setString("templateName", templateName);
					list = query1.list();				 
			 }
			 if(list == null || list.size()<= 0){
				Query query = session.createQuery("from SMSTemplate smsTemplate where smsTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}	
	public List<SMSTemplate> getDuplicateCheckList(String templateName)
			throws Exception {
		Session session = null;
		List<SMSTemplate> list = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from SMSTemplate smsTemplate where smsTemplate.templateName ='"+templateName+"'");
			list = query.list();

			return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"
					+ e.getMessage());
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}


}
