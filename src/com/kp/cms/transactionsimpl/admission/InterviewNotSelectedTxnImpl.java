package com.kp.cms.transactionsimpl.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IInterviewNotSelectedTxn;
import com.kp.cms.utilities.HibernateUtil;


public class InterviewNotSelectedTxnImpl implements IInterviewNotSelectedTxn{
	
	private static final Log log = LogFactory.getLog(InterviewNotSelectedTxnImpl.class);

	@Override
	public List<Object[]> getNotSelectedCandidates(int interviewTypeId) throws Exception {
		
		Session session = null;
		List<Object[]> notSelectedCandidatesList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query notSelectedCandidatesQuery;
					
			notSelectedCandidatesQuery = session.createQuery("select admAppln.applnNo," +
					" admAppln.personalData.firstName," +
					" admAppln.personalData.middleName, " +
					" admAppln.personalData.lastName, " +
					" admAppln.personalData.email, " +
					" interviewResult.interviewProgramCourse.name " +
					" from AdmAppln admAppln " +
					" inner join admAppln.interviewResults interviewResult " +
					" where interviewResult.interviewProgramCourse.id = :interviewTypeId" +
					" and interviewResult.interviewStatus.id = "+CMSConstants.INTERVIEW_FAIL_ID);
					notSelectedCandidatesQuery.setInteger("interviewTypeId", interviewTypeId);
					
			notSelectedCandidatesList = notSelectedCandidatesQuery.list();		
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return notSelectedCandidatesList; 
	}
}
