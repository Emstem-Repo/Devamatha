package com.kp.cms.transactions.attandance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;
//AttendanceSlipDetailsTransactionImpl
public class AttendanceSlipDetailsTransactionImpl implements IAttendanceSlipDetailsTransaction {
	private static final Log log = LogFactory.getLog(AttendanceSlipDetailsTransactionImpl.class);
	@Override
	public List<Attendance> getListOfSlipDetails(String searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		log.error("Start of getListofSlipDetails of AttendanceSlipDetailsTransactionImpl");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query=session.createQuery(searchCriteria);
			List<Attendance> slipDetailsList = query.list();
			log.error("End of getListofSlipDetails of AttendanceSlipDetailsTransactionImpl");
			return slipDetailsList;
		} catch (Exception exception) {
			log.error("Error while retrieving attendance slip details.." +exception);
			throw  new ApplicationException(exception);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public List<String> getPeriods(String listPeriods)throws ApplicationException {
		// TODO Auto-generated method stub
		log.error("Start getPeriods of AttendanceSlipDetailsTransactionImpl");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(listPeriods);
			List<String> periodsList = selectedCandidatesQuery.list();
			log.error("End getPeriods of AttendanceSlipDetailsTransactionImpl");
			return periodsList;
		} catch (Exception exception) {
			log.error("Error while retrieving period list details.." +exception);
			throw  new ApplicationException(exception);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public List<String> getClassNames(String classids) throws Exception {
		log.error("Start getClassNames of AttendanceSlipDetailsTransactionImpl");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query classNameQuery=session.createQuery(classids);
			List<String> classnameList = classNameQuery.list();
			log.error("End getPeriods of AttendanceSlipDetailsTransactionImpl");
			return classnameList;
		} catch (Exception exception) {
			log.error("Error while retrieving classNames.." +exception);
			throw  new ApplicationException(exception);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
}
