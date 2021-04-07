package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.transactions.admin.IInterviewTemplate;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class InterviewTemplateTransactionImpl implements IInterviewTemplate {
	
	private static final Log log = LogFactory.getLog(InterviewTemplateTransactionImpl.class);
	public static volatile InterviewTemplateTransactionImpl templateImpl = null;

	public static InterviewTemplateTransactionImpl getInstance() {
		if (templateImpl == null) {
			templateImpl = new InterviewTemplateTransactionImpl();
			return templateImpl;
		}
		return templateImpl;
	}

	@Override
	public List<GroupTemplateInterview> checkDuplicate(int courseId, String templateName,
			int programId,int interviewId,int subroundId,int year) throws Exception {
		Session session = null;
		List<GroupTemplateInterview> list = null;
		try {
		 	session = HibernateUtil.getSession();
		 	String que="from GroupTemplateInterview g where g.course.id = :courseId and g.templateName = :templateName and g.interviewProgramCourse.id=:interviewId and g.year=:year";
		 	if(subroundId>0){
		 		que=que+" and g.interviewSubRounds.id="+subroundId;
		 	}else{
		 		que=que+" and g.interviewSubRounds.id = null";
		 	}
			Query query = session.createQuery(que);
			query.setInteger("courseId", courseId);
			query.setString("templateName", templateName);
			query.setInteger("interviewId", interviewId);
			query.setInteger("year",year);
			list = query.list();
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

	@Override
	public boolean deleteGroupTemplateInterview(
			GroupTemplateInterview groupTemplate) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.delete(groupTemplate);
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

	@Override
	public List<GroupTemplateInterview> getGroupTemplateInterviews(
			int templateId) throws Exception {
		Session session = null;
		List<GroupTemplateInterview> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (templateId != 0) {
				Query query = session.createQuery("from GroupTemplateInterview g where g.id= :id");
				query.setInteger("id", templateId);
				list = query.list();
			} else {
				Query query = session.createQuery("from GroupTemplateInterview");
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

	@Override
	public boolean saveGroupTemplateInterview(GroupTemplateInterview groupTemplate)
			throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = sessionFactory.openSession();
			 
			 tx = session.beginTransaction();
			 tx.begin();
			 session.saveOrUpdate(groupTemplate);
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
}
