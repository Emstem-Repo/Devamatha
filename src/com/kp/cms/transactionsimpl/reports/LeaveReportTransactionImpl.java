package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ILeaveReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class LeaveReportTransactionImpl implements ILeaveReportTransaction {

	
	private static final Log log = LogFactory.getLog(LeaveReportTransactionImpl.class);
	
	public static volatile LeaveReportTransactionImpl self=null;
	
	/**
	 * @return unique instance of LeaveReportTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static LeaveReportTransactionImpl getInstance(){
		if(self==null)
			self= new LeaveReportTransactionImpl();
		return self;
	}
	
	/**
	 *	This method is used to get LeaveReportDetails from database. 
	 */
	
	@Override
	public List<StudentLeave> getLeaveReportDetails(String searchCriteria) throws Exception {
		log.info("entered getLeaveReportDetails..");
		Session session = null;
		List<StudentLeave> leaveList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			leaveList = session.createQuery(searchCriteria).list();
		
		} catch (Exception e) {
			log.error("error while getting the getLeaveReportDetails results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getLeaveReportDetails..");
		
		return leaveList;
	}

}