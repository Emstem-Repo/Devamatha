package com.kp.cms.transactionsimpl.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.ISectionWiseReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SectionWiseTransactionImpl implements ISectionWiseReportTransaction {

	private static final Log log = LogFactory.getLog(SectionWiseTransactionImpl.class);
	
	public static volatile SectionWiseTransactionImpl self=null;
	
	/**
	 * @return unique instance of SectionWiseTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static SectionWiseTransactionImpl getInstance(){
		if(self==null)
			self= new SectionWiseTransactionImpl();
		return self;
	}
	
	/**
	 *	This method is used to get student details from database. 
	 */
	
	@Override
	public List<Object[]> getSectionWiseReportDetails(String searchCriteria)
			throws Exception {
		log.info("entered into getSectionWiseReportDetails in SectionWiseTransactionImpl class");
		Session session = null;
		List studentDetailsList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			studentDetailsList = session.createQuery(searchCriteria).list();
		
		} catch (Exception e) {
			log.error("error while getting the getSectionWiseReportDetails results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getSectionWiseReportDetails in SectionWiseTransactionImpl class");
		return studentDetailsList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.ISectionWiseReportTransaction#getAllStudentDetentionDetails()
	 */
	@Override
	public Map<Integer, ExamStudentDetentionRejoinDetails> getAllStudentDetentionDetails()
			throws Exception {
		log.info("entered into getSectionWiseReportDetails in SectionWiseTransactionImpl class");
		Session session = null;
		List<ExamStudentDetentionRejoinDetails> studentDetailsList = null;
		Map<Integer,ExamStudentDetentionRejoinDetails> finalMap=new HashMap<Integer, ExamStudentDetentionRejoinDetails>();
		try {
			session = HibernateUtil.getSession();
			String searchCriteria="from ExamStudentDetentionRejoinDetails e";
			studentDetailsList = session.createQuery(searchCriteria).list();
			if(studentDetailsList!=null && !studentDetailsList.isEmpty()){
				Iterator<ExamStudentDetentionRejoinDetails> itr=studentDetailsList.iterator();
				while (itr.hasNext()) {
					ExamStudentDetentionRejoinDetails bo = (ExamStudentDetentionRejoinDetails) itr.next();
					if(bo.getStudent()!=null)
					finalMap.put(bo.getStudent().getId(),bo);
				}
			}
		
		} catch (Exception e) {
			log.error("error while getting the getSectionWiseReportDetails results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("exit of getSectionWiseReportDetails in SectionWiseTransactionImpl class");
		return finalMap;
	}
}