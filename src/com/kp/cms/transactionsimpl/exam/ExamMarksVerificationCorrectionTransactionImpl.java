package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.UploadBlockListForHallticketOrMarkscardForm;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExamMarksVerificationCorrectionTransactionImpl implements IExamMarksVerificationCorrectionTransaction{
	private static final Log log = LogFactory.getLog(ExamMarksVerificationCorrectionTransactionImpl.class);
	private static volatile ExamMarksVerificationCorrectionTransactionImpl examMarksVerificationCorrectionTransactionImpl = null;
	
	public static ExamMarksVerificationCorrectionTransactionImpl getInstance() {
		if (examMarksVerificationCorrectionTransactionImpl == null) {
			examMarksVerificationCorrectionTransactionImpl = new ExamMarksVerificationCorrectionTransactionImpl();
		}
		return examMarksVerificationCorrectionTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction#getStudentId(java.lang.String)
	 */
	//get the studentId by student registernumber
	public Integer getStudentId(String regNo) throws Exception {
		Session session = null;
		Integer id = null;
		try {
			session = HibernateUtil.getSession();
			if(regNo!=null && !regNo.isEmpty()){
				String str = "select s.id from Student s where s.isHide=0 and s.isActive=1 and s.admAppln.isCancelled=0 and s.registerNo='"+regNo+"'";
				Query selectedCandidatesQuery=session.createQuery(str);
				id = (Integer)selectedCandidatesQuery.uniqueResult();
			}
			if(id!=null && id!=0){
			return id;
			}else{
				return id=0;
			}
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction#getDataForQuery(java.lang.String)
	 */
	public List<ExamMarksVerificationEntryBO> getDataForQuery(String query) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			 List<ExamMarksVerificationEntryBO> List = selectedCandidatesQuery.list();
			return List;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction#getSubjectName(java.lang.Integer)
	 */
	public String getSubjectName(Integer subjectId) throws Exception {
		Session session = null;
		String subjectName = null;
		try {
			session = HibernateUtil.getSession();
			if(subjectId!=null && subjectId !=0){
				Query selectedCandidatesQuery=session.createQuery("select s.name ||'('|| s.code ||')'  from Subject s where s.id="+subjectId);
				subjectName = (String)selectedCandidatesQuery.uniqueResult();
			}
			return subjectName;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	 /* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction#saveVerificationMarks(java.util.List)
	 */
	public boolean saveVerificationMarks(List<ExamMarksVerificationEntryBO> marksList ) throws Exception {
			log.debug("inside uploadBlockListForHallticketOrMarkscard");
			boolean isSave=false;
			Session session = null;
			Transaction transaction = null;
				try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				if(marksList!=null){
					Iterator<ExamMarksVerificationEntryBO> iterator=marksList.iterator();
					while (iterator.hasNext()) {
						ExamMarksVerificationEntryBO bo = iterator.next();
						if(bo!=null){
							session.update(bo);
							isSave=true;
						}
					}
				}
				transaction.commit();
				session.flush();
				session.close();
				//sessionFactory.close();
			} catch (Exception e) {
				log.error("Error during duplcation checking..." , e);
				session.flush();
				//session.close();
				throw new ApplicationException(e);
			}
			log.debug("leaving isDocTypeDuplcated");
			return isSave;
		}
	
	public String getClassName(Integer sutudentId) throws Exception {
		Session session = null;
		String className = null;
		try {
			session = HibernateUtil.getSession();
			if(sutudentId!=null && sutudentId !=0){
				Query selectedCandidatesQuery=session.createQuery("select classSchemewise.classes.name from Student s where s.id="+sutudentId);
				className = (String)selectedCandidatesQuery.uniqueResult();
			}
			return className;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction#getStudentName(java.lang.Integer)
	 */
	public String getStudentName(Integer sutudentId) throws Exception {
		Session session = null;
		String studentName = null;
		try {
			session = HibernateUtil.getSession();
			if(sutudentId!=null && sutudentId !=0){
				Query selectedCandidatesQuery=session.createQuery("select s.admAppln.personalData.firstName from Student s where s.id="+sutudentId);
				studentName = (String)selectedCandidatesQuery.uniqueResult();
			}
			return studentName;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
}
