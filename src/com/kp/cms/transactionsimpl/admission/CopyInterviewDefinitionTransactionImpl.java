package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ICopyInterviewDefinitionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CopyInterviewDefinitionTransactionImpl implements
		ICopyInterviewDefinitionTransaction {

	/**
	 * Singleton object of CopyInterviewDefinitionTransactionImpl
	 */
	private static volatile CopyInterviewDefinitionTransactionImpl copyInterviewDefinitionTransactionImpl = null;
	private static final Log log = LogFactory.getLog(CopyInterviewDefinitionTransactionImpl.class);
	private CopyInterviewDefinitionTransactionImpl() {
		
	}
	/**
	 * return singleton object of CopyInterviewDefinitionTransactionImpl.
	 * @return
	 */
	public static CopyInterviewDefinitionTransactionImpl getInstance() {
		if (copyInterviewDefinitionTransactionImpl == null) {
			copyInterviewDefinitionTransactionImpl = new CopyInterviewDefinitionTransactionImpl();
		}
		return copyInterviewDefinitionTransactionImpl;
	}
	/* (non-Javadoc)
	 * getting the interviewProgramCourses for a particular year
	 * @see com.kp.cms.transactions.admission.ICopyInterviewDefinitionTransaction#getInterviewDefinitionByYear(int)
	 */
	@Override
	public List<InterviewProgramCourse> getInterviewDefinitionByYear(int year) throws Exception {
		Session session=null;
		List<InterviewProgramCourse> intProgramCourse; 
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			intProgramCourse= session.createQuery("from InterviewProgramCourse i where i.isActive=1 and i.year=:fromYear").setInteger("fromYear", year).list();
			//session.flush();
		} catch (Exception e) {
			log.error("Error while getting interviewProgramCourse..."+e);
			throw  new ApplicationException(e);
		}finally{
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return intProgramCourse;
	}
	/**
	 * checking for duplicate interview definition before saving to database
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(String query) throws Exception {
		Session session = null;
		List<InterviewProgramCourse> intPrgCourse=null;
		try{
			session = HibernateUtil.getSession();
			intPrgCourse=session.createQuery(query).list();
			if(intPrgCourse!=null && !intPrgCourse.isEmpty()){
				return true;
			}
			return false;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
	}
	@Override
	public boolean copyInterviewDefinition(List<InterviewProgramCourse> intPrgCourse) throws Exception {

		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<InterviewProgramCourse> interviewList = intPrgCourse.iterator();
			while (interviewList.hasNext()) {
				InterviewProgramCourse iPC = (InterviewProgramCourse) interviewList.next();
				session.save(iPC);
			}
			transaction.commit();
			session.flush();
		}catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return true;
		
	
	}
	

}
