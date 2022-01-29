package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmpInterview;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.employee.InterviewCommentsTO;
import com.kp.cms.transactions.employee.IInterviewCommentsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InterviewCommentsTransactionImpl implements
		IInterviewCommentsTransaction {
	private static final Log log = LogFactory
			.getLog(InterviewCommentsTransactionImpl.class);

	public static volatile InterviewCommentsTransactionImpl obImpl = null;

	public static InterviewCommentsTransactionImpl getInstance() {
		if (obImpl == null) {
			obImpl = new InterviewCommentsTransactionImpl();
		}
		return obImpl;
	}

	public List<Object[]> getDetails(String name) throws Exception {
		Session session = null;
		List<Object[]> listDetails = null;

		try {
			session = HibernateUtil.getSession();
			String q1 = "select eor.id, eor.name, eor.email,eor.status from EmpOnlineResume eor  where eor.isActive=1 ";
			if (name != null && name.trim().length() > 0) {
				q1 = q1 + "and eor.name like '%" + name + "%'";
			}
			Query query = session.createQuery(q1);
			listDetails = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return listDetails;
	}

	public List<Object[]> getInterviewDetails(int id) throws Exception {
		Session session = null;
		List<Object[]> listDetails = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select eor.name, eor.department.name from EmpOnlineResume eor  where eor.isActive=1 and eor.id="
							+ id);
			listDetails = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return listDetails;
	}

	public boolean save(EmpInterview objBO) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(objBO);
			txn.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveOnlineResume..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveOnlineResume..." + e);
			throw new ApplicationException(e);
		}
		return result;

	}

	public EmpOnlineResume getResumeDetails(int id) throws Exception {
		Session session = null;
		EmpOnlineResume res = null;
		try {
			session = HibernateUtil.getSession();
			Query querey = session
					.createQuery("from EmpOnlineResume e where e.isActive=1 and e.id="
							+ id);
			res = (EmpOnlineResume) querey.uniqueResult();
			if (res != null)
				session.flush();
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return res;
	}

	public List<EmpInterview> getInterviewComments(int id) throws Exception {
		Session session = null;
		List<EmpInterview> listDetails = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpInterview ei where ei.isActive=1 "
							+ "and ei.empOnlineResume.id=" + id);
			listDetails = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return listDetails;
	}
	
	public boolean updateStatus(List<InterviewCommentsTO> listOfDetails)throws Exception
	{
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			EmpOnlineResume onlineResumeObj=null;
			int count = 0;
			for(InterviewCommentsTO details:listOfDetails)
			{
				if(!details.getStatus().equalsIgnoreCase(""))
				{	
					onlineResumeObj=(EmpOnlineResume)session.get(EmpOnlineResume.class, details.getId());
					onlineResumeObj.setStatus(details.getStatus());
					session.update(onlineResumeObj);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}	
			}
			txn.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveOnlineResume..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveOnlineResume..." + e);
			throw new ApplicationException(e);
		}
	}

}
