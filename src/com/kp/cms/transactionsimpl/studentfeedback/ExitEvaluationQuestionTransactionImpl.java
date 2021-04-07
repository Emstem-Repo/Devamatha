package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.studentfeedback.IExitEvaluationQuestionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExitEvaluationQuestionTransactionImpl implements IExitEvaluationQuestionTransaction {
	
	private static final Log log = LogFactory.getLog(ExitEvaluationQuestionTransactionImpl.class);
	
	
	public boolean addExitEvalQuestion(ExitEvaluationQuestion exitEvalQues) throws Exception {
		log.info("call of addExitEvalQuestion in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(exitEvalQues);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to add ExitEvaluationQuestion" + e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addExitEvalQuestion in ExitEvaluationQuestionTransactionImpl class.");
		return isAdded;
	}
	
	public List<ExitEvaluationQuestion> getAllExitEvalQues() throws Exception {
		log.info("call of getAllExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		List<ExitEvaluationQuestion> list;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ExitEvaluationQuestion fh where fh.isActive= :isActive");
			query.setBoolean("isActive", true);
			list = query.list();
		} catch (Exception e) {
			log.error("Unable to get FeeHeadings" + e);
			throw e;
		} finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
		}
		log.info("end of getAllExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		return list;
	}
	
	public List<ExitEvaluationQuestion> editExitEvalQues(int id) throws Exception {
		log.info("call of editExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		List<ExitEvaluationQuestion> list;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ExitEvaluationQuestion fh where fh.id= :id");
			query.setInteger("id", id);
			list = query.list();
		} catch (Exception e) {
			log.error("Unable to get ExitEvaluationQuestion for edit" + e);
			throw e;
		} finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
		}
		log.info("end of editFeeHeading in ExitEvaluationQuestionTransactionImpl class.");
		return list;
	}
	
	public boolean updateExitEvalQues(ExitEvaluationQuestion exitEvalQues) throws Exception {
		log.info("call of updateExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(exitEvalQues);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to update FeeHeadings" + e);
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of updateExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		return isUpdated;
	}
	public boolean deleteExitEvalQues(int id, String userId) throws Exception {
		log.info("call of deleteExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExitEvaluationQuestion exitEvalQues = (ExitEvaluationQuestion) session.get(ExitEvaluationQuestion.class,
					id);
			exitEvalQues.setModifiedBy(userId);
			exitEvalQues.setLastModifiedDate(new Date());
			exitEvalQues.setIsActive(Boolean.FALSE);
			session.update(exitEvalQues);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete ExitEvaluationQuestion" + e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		return isDeleted;
	}
	public void reActivateExitEvalQues(String feesName, String userId)
	throws Exception {
		log.info("call of reActivateExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
			.createQuery("from ExitEvaluationQuestion fh where fh.name = :name");
			query.setString("name", feesName);
			ExitEvaluationQuestion exitEvalQues = (ExitEvaluationQuestion) query.uniqueResult();
			transaction = session.beginTransaction();
			exitEvalQues.setIsActive(true);
			exitEvalQues.setModifiedBy(userId);
			exitEvalQues.setLastModifiedDate(new Date());
			session.update(exitEvalQues);
			transaction.commit();
		} catch (Exception e) {
			log.error("Unable to restore FeeHeadings" + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
		}
		log.info("end of reActivateExitEvalQues in ExitEvaluationQuestionTransactionImpl class.");
	}
	
	public ExitEvaluationQuestion isExitEvalQuesExist(int topicId, String question)
	throws Exception {
		log.info("call of isExitEvalQuesExist in ExitEvaluationQuestionTransactionImpl class.");
		Session session = null;
		ExitEvaluationQuestion exitEvalQues;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query q = session
			.createQuery("from ExitEvaluationQuestion fh where fh.exitEvalTopic.id= :topicId and fh.question= :question ");
			q.setInteger("topicId", topicId);
			q.setString("question", question);
			exitEvalQues = (ExitEvaluationQuestion) q.uniqueResult();

		} catch (Exception e) {
			log.error("Unable to get subjectgroup" + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
		}	
		log.info("end of isExitEvalQuesExist in ExitEvaluationQuestionTransactionImpl class.");
		return exitEvalQues;
	}
	
}
