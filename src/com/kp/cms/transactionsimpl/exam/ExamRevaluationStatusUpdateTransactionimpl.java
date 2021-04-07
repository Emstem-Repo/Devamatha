package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamRevaluationStatusUpdateForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.transactions.exam.IExamRevaluationStatusUpdateTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRevaluationStatusUpdateTransactionimpl implements
		IExamRevaluationStatusUpdateTransaction {
	/**
	 * Singleton object of ExamRevaluationStatusUpdateHandler
	 */
	private static volatile ExamRevaluationStatusUpdateTransactionimpl examRevaluationStatusUpdateTransactionimpl = null;
	private static final Log log = LogFactory.getLog(ExamRevaluationStatusUpdateTransactionimpl.class);
	private ExamRevaluationStatusUpdateTransactionimpl() {
		
	}
	/**
	 * return singleton object of ExamRevaluationStatusUpdateTransactionimpl.
	 * @return
	 */
	public static ExamRevaluationStatusUpdateTransactionimpl getInstance() {
		if (examRevaluationStatusUpdateTransactionimpl == null) {
			examRevaluationStatusUpdateTransactionimpl = new ExamRevaluationStatusUpdateTransactionimpl();
		}
		return examRevaluationStatusUpdateTransactionimpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationStatusUpdateTransaction#getRevaluationAppDetailsList(java.lang.String)
	 */
	@Override
	public List<ExamRevaluationAppDetails> getRevaluationAppDetailsList(String registerNo) throws Exception {
		Session session = null;
		List<ExamRevaluationAppDetails> revaluationAppDetailsList = null;
		try {
			session = HibernateUtil.getSession();
			String query= "from ExamRevaluationAppDetails e where e.isActive=1 and e.examRevApp.isActive=1 and e.examRevApp.student.registerNo='"+registerNo+"'";
			revaluationAppDetailsList = session.createQuery(query).list();
			return revaluationAppDetailsList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationStatusUpdateTransaction#saveNewStatus(java.util.List)
	 */
	@Override
	public boolean saveNewStatus(ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm)
			throws Exception {
		Session session = null;
		boolean isUpdated=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(examRevaluationStatusUpdateForm.getRevAppToList()!=null && !examRevaluationStatusUpdateForm.getRevAppToList().isEmpty()){
				Iterator<ExamRevaluationApplicationTO> itr=examRevaluationStatusUpdateForm.getRevAppToList().iterator();
				while (itr.hasNext()) {
					ExamRevaluationApplicationTO to=(ExamRevaluationApplicationTO)itr.next();
					if(to.getNewStatus()!=null && !to.getNewStatus().isEmpty()){
						ExamRevaluationAppDetails bo=(ExamRevaluationAppDetails)session.get(ExamRevaluationAppDetails.class,to.getId());
						bo.setStatus(to.getNewStatus());
						bo.setLastModifiedDate(new Date());
						bo.setModifiedBy(examRevaluationStatusUpdateForm.getUserId());
						session.update(bo);
						isUpdated=true;
					}
				}
			}
			txn.commit();
			return isUpdated;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
}
