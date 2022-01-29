package com.kp.cms.transactionsimpl.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IInterviewScoreSheetTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InterviewScoreSheetTransactionImpl implements
		IInterviewScoreSheetTransaction {
	private static final Log log = LogFactory.getLog(InterviewScoreSheetTransactionImpl.class);
	/**
	 * Singleton object of InterviewScoreSheetTransactionImpl
	 */
	private static volatile InterviewScoreSheetTransactionImpl interviewScoreSheetTransactionImpl = null;
	private InterviewScoreSheetTransactionImpl() {
		
	}
	/**
	 * return singleton object of InterviewScoreSheetTransactionImpl.
	 * @return
	 */
	public static InterviewScoreSheetTransactionImpl getInstance() {
		if (interviewScoreSheetTransactionImpl == null) {
			interviewScoreSheetTransactionImpl = new InterviewScoreSheetTransactionImpl();
		}
		return interviewScoreSheetTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IInterviewScoreSheetTransaction#getListOfCandidates(java.lang.String)
	 */
	@Override
	public List getListOfCandidates(String searchCriteria) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(searchCriteria);
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
	@Override
	public Map<Integer, String> getInterviewProgramCourseMap(String query)
			throws Exception {
		Session session = null;
		List<InterviewProgramCourse> list = null;
		Map<Integer,String> finalMap=new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<InterviewProgramCourse> itr=list.iterator();
				while (itr.hasNext()) {
					InterviewProgramCourse bo = (InterviewProgramCourse) itr.next();
					finalMap.put(bo.getId(),bo.getName()+"("+bo.getCourse().getName()+")");
				}
			}
			return finalMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public Map<Integer, String> getTemplateMap(String query) throws Exception {
		Session session = null;
		List<GroupTemplateInterview> list = null;
		Map<Integer,String> finalMap=new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<GroupTemplateInterview> itr=list.iterator();
				while (itr.hasNext()) {
					GroupTemplateInterview bo = (GroupTemplateInterview) itr.next();
					if(bo.getInterviewProgramCourse()!=null)
					finalMap.put(bo.getInterviewProgramCourse().getId(),bo.getTemplateDescription());
				}
			}
			return finalMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

}
