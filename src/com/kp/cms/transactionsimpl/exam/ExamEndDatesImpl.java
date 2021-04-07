package com.kp.cms.transactionsimpl.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.exam.ExamEndDate;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamEndDateForm;
import com.kp.cms.transactions.exam.IExamEndDates;
import com.kp.cms.utilities.HibernateUtil;

public class ExamEndDatesImpl implements  IExamEndDates{
		private static volatile ExamEndDatesImpl examEndDatesImpl = null;
		private static final Log log = LogFactory.getLog(ExamEndDatesImpl.class);
		private ExamEndDatesImpl() {
			
		}
		/**
		 * return singleton object of PublishSupplementaryImpApplicationHelper.
		 * @return
		 */
		public static ExamEndDatesImpl getInstance() {
			if (examEndDatesImpl == null) {
				examEndDatesImpl = new ExamEndDatesImpl();
			}
			return examEndDatesImpl;
		}
		public List getDataForQuery(String query) throws Exception {
			Session session = null;
			List list = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(query);
				list = selectedCandidatesQuery.list();
				return list;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
		public List<ExamEndDate> getDataFromTable(String query) throws Exception {
			Session session = null;
			List list = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(query);
				list = selectedCandidatesQuery.list();
				return list;
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

