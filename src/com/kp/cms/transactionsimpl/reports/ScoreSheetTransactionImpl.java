package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Grade;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IScoreSheetTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ScoreSheetTransactionImpl implements IScoreSheetTransaction {
	
	private static final Log log = LogFactory.getLog(ScoreSheetTransactionImpl.class);
	
	public List getListOfCandidates(String SearchCriteria) throws Exception{
		Session session = null;
		List selectedCandidatesList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
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
	public List<Grade> getListOfGrades() throws Exception{
		Session session = null;
		List<Grade> gradeList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from Grade grade where grade.isActive=1");
			gradeList = selectedCandidatesQuery.list();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return gradeList;
	}
}
