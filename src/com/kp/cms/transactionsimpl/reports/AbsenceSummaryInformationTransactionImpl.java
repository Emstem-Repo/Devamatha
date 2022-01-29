package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IAbsenceInformationSummaryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AbsenceSummaryInformationTransactionImpl  implements IAbsenceInformationSummaryTransaction{

	private static Log log = LogFactory.getLog(AbsenceSummaryInformationTransactionImpl.class);
	/**
	 * @see com.kp.cms.transactions.reports.IAbsenceInformationSummaryTransaction#getAbsenceSummaryInformation(java.lang.String)
	 */
	@Override
	public List<Object[]> getAbsenceSummaryInformation(
			String absenceInformationQuery) throws ApplicationException {

		log.info("entering into getAbsenceSummaryInformation of AbsenceSummaryInformationTransactionImpl class.");
		Session session = null;
		List<Object[]> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery(absenceInformationQuery);
			studentSearchResult = studentQuery.list();

		} catch (Exception e) {
			log.info("error in getAbsenceSummaryInformation of AbsenceSummaryInformationTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getAbsenceSummaryInformation of AbsenceSummaryInformationTransactionImpl class.");
		return studentSearchResult;
	
	}
	
	
	/**
	 * @see com.kp.cms.transactions.reports.IAbsenceInformationSummaryTransaction#getSubjectInformation(java.lang.String)
	 */
	public List<String> getSubjectInformation(
			String absenceInformationQuery) throws ApplicationException {
		log.info("entering into getSubjectInformation of AbsenceSummaryInformationTransactionImpl class.");

		Session session = null;
		List<String> studentSearchResult = new ArrayList<String>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery(absenceInformationQuery);
			List<Object[]> obj = studentQuery.list();
			if(obj!=null && !obj.isEmpty()){
				Iterator<Object[]> itr=obj.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					studentSearchResult.add(objects[0]+"("+objects[1]+")");
				}
			}

		} catch (Exception e) {
			log.info("error in getSubjectInformation of AbsenceSummaryInformationTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getSubjectInformation of AbsenceSummaryInformationTransactionImpl class.");
		return studentSearchResult;
	}
}