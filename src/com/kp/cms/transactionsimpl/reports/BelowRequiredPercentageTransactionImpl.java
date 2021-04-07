package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IBelowRequiredPercentageTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class BelowRequiredPercentageTransactionImpl implements IBelowRequiredPercentageTransaction{


	private static final Log log = LogFactory.getLog(BelowRequiredPercentageTransactionImpl.class);
	
	/**
	 * 
	 */
	public static volatile BelowRequiredPercentageTransactionImpl self=null;
	/**
	 * @return
	 * This method will return instance of this class
	 */
	public static BelowRequiredPercentageTransactionImpl getInstance(){
		if(self==null)
			self= new BelowRequiredPercentageTransactionImpl();
		return self;
	}
	/**
	 * 
	 */
	private BelowRequiredPercentageTransactionImpl(){
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getStudentAttendance(java.lang.String)
	 * This method will get students based on the search criteria
	 */
	public List getStudentAttendance(String searchCriteria) throws ApplicationException {
		log.info("entered getStudentAttendance..");
		Session session = null;
		List studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			 studentSearchResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentAttendance..");
		return studentSearchResult;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getClassesHeld(java.lang.String)
	 * This method will give the classes held for a particular subject
	 */
	public int getClassesHeld(String searchCriteria) throws ApplicationException {
		log.info("entered getClassesHeld..");
		Session session = null;
		int classesHeld = 0;
		List classHld = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			classHld = session.createQuery(searchCriteria).list();
			
			//------------------------------------   modify start modified to fix sum of hours held
			
			if(classHld!=null && classHld.size() >0 && !classHld.get(0).toString().equals("")){
				classesHeld = Integer.valueOf(classHld.get(0).toString());
			}
			//------------------------------------   modify end ------------------
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getClassesHeld..");
		return classesHeld;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getClassesAttended(java.lang.String)
	 * This method will give the classes attended for particular subject by particular student
	 */
	public int getClassesAttended(String searchCriteria) throws ApplicationException {
		log.info("entered getClassesAttended..");
		Session session = null;
		int classesAtten = 0;
		List classAttd = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			classAttd = session.createQuery(searchCriteria).list();			
			if(classAttd!=null && classAttd.size() >0 && !classAttd.get(0).toString().equals("")){
				classesAtten = Integer.valueOf(classAttd.get(0).toString());
			}
			//------------------------------------   modify end ------------------
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getClassesAttended..");
		return classesAtten;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getActivityAttended(java.lang.String)
	 * This method will give the Activity Attended for particular student
	 */
	@Override
	public int getActivityAttended(String searchCriteria)
			throws ApplicationException {
		log.info("entered getActivityAttended..");
		Session session = null;
		int activityAtten = 0;
		List activityAttd = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			activityAttd = session.createQuery(searchCriteria).list();			
			if(activityAttd!=null){
				activityAtten = activityAttd.size();
			}
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getActivityAttended..");
		return activityAtten;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getActivityHeld(java.lang.String)
	 * This method will give the Activity Held for particular student
	 */
	@Override
	public int getActivityHeld(String searchCriteria)
			throws ApplicationException {
		log.info("entered getActivityHeld..");
		Session session = null;
		int activityHeld = 0;
		List activityHld = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			activityHld = session.createQuery(searchCriteria).list();			
			if(activityHld!=null){
				activityHeld = activityHld.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getActivityHeld..");
		return activityHeld;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getSelectedActivityAttended(java.lang.String)
	 * This method will give the Selected Activity Attended for particular student
	 */
	@Override
	public int getSelectedActivityAttended(String searchCriteria)
			throws ApplicationException {
		log.info("entered getSelectedActivityAttended..");
		Session session = null;
		int selectedActAttend = 0;
		List selectedActAttd = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			selectedActAttd = session.createQuery(searchCriteria).list();			
			if(selectedActAttd!=null){
				selectedActAttend = selectedActAttd.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getSelectedActivityAttended..");
		return selectedActAttend;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getSelectedActivityHeld(java.lang.String)
	 * This method will give the Selected Activity Held for particular student
	 */
	@Override
	public int getSelectedActivityHeld(String searchCriteria)
			throws ApplicationException {
		log.info("entered getSelectedActivityHeld..");
		Session session = null;
		int selectedActHld = 0;
		List selectedActHeld = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			selectedActHeld = session.createQuery(searchCriteria).list();			
			if(selectedActHeld!=null){
				selectedActHld = selectedActHeld.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getSelectedActivityHeld..");
		return selectedActHld;
	}
}
