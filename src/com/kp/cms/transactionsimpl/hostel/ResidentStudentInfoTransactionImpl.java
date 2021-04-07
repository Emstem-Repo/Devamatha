package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IResidentStudentInfo;
import com.kp.cms.utilities.HibernateUtil;

public class ResidentStudentInfoTransactionImpl implements IResidentStudentInfo {
	private static final Log log = LogFactory.getLog(ResidentStudentInfoTransactionImpl.class);
	
	/**
	 * get list of candidates based on query
	 */
	public List<HlApplicationForm> getListOfCandidates(String SearchCriteria) throws Exception{
		Session session = null;
		List<HlApplicationForm> selectedCandidatesList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
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
	/**
	 * get list of candidates based on Student Id
	 */
	public HlApplicationForm getCandidatesById(String SearchCriteria) throws Exception{
		Session session = null;
		HlApplicationForm h = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
			h = (HlApplicationForm)selectedCandidatesQuery.uniqueResult();
			return h;
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
	 * get the attendance list based on student
	 */
	public List<HlAttendance> getAttendanceList(int id) throws Exception {
		Session session = null;
		List<HlAttendance> h = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from HlAttendance att " +
					"where att.isPresent = 0 " +
					"and att.isActive = 1 " +
					"and att.hlGroupStudent.hlApplicationForm.id="+id);
			h = selectedCandidatesQuery.list();
			return h;
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
	//getting by HlApplnID
	public HlApplicationForm getCandidatesByHlAppId(String hlappId)
			throws Exception {
		Session session = null;
		HlApplicationForm h = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from HlApplicationForm ha where ha.id=:hlappId");
			selectedCandidatesQuery.setString("hlappId", hlappId);
			h = (HlApplicationForm)selectedCandidatesQuery.uniqueResult();
			return h;
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
