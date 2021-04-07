package com.kp.cms.transactionsimpl.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IValuationStatusSubjectWiseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ValuationStatusSubjectWiseImpl implements IValuationStatusSubjectWiseTransaction{
	private static final Log log = LogFactory.getLog(ValuationStatusSubjectWiseImpl.class);
	private static volatile ValuationStatusSubjectWiseImpl impl = null;
	public static ValuationStatusSubjectWiseImpl getInstance(){
		if(impl == null){
			impl = new ValuationStatusSubjectWiseImpl();
			return impl;
		}
		return impl;
	}
	@Override
	public List getDataForQuery(String query)
			throws Exception {
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
	@Override
	public List<Integer> getAbsentStudentIds(String absentStudentQuery)
			throws Exception {
		Session session = null;
		List<Integer> absentStudentList = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(absentStudentQuery);
			absentStudentList = query.list();
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return absentStudentList;
	}
	@Override
	public List<ExamValidationDetails> getvaluationDetailsData( String valuationDetailsQuery) throws Exception {
		Session session = null;
		List<ExamValidationDetails> validationDetails =null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(valuationDetailsQuery);
			validationDetails = query.list();
			
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return validationDetails;
	}
}
