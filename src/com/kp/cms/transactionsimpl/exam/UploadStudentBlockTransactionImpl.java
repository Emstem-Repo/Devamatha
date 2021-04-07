package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.transactions.exam.IUploadStudentBlockTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadStudentBlockTransactionImpl implements
		IUploadStudentBlockTransaction {
	/**
	 * Singleton object of UploadStudentBlockHelper
	 */
	private static volatile UploadStudentBlockTransactionImpl uploadStudentBlockTransactionImpl = null;
	private static final Log log = LogFactory.getLog(UploadStudentBlockTransactionImpl.class);
	private UploadStudentBlockTransactionImpl() {
		
	}
	/**
	 * return singleton object of UploadStudentBlockHandler.
	 * @return
	 */
	public static UploadStudentBlockTransactionImpl getInstance() {
		if (uploadStudentBlockTransactionImpl == null) {
			uploadStudentBlockTransactionImpl = new UploadStudentBlockTransactionImpl();
		}
		return uploadStudentBlockTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IUploadStudentBlockTransaction#getStudentDetails(java.lang.String)
	 */
	@Override
	public Map<String, String> getStudentDetails(String query)
			throws Exception {
		Session session = null;
		List<Object[]> list = null;
		Map<String, String> studentMap=new HashMap<String, String>();
		
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null && objects[2]!=null){
						studentMap.put(objects[0].toString(),objects[1].toString()+"_"+objects[2].toString());
					}
				}
			}
			return studentMap;
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
	@Override
	public boolean uploadData(List<ExamBlockUnBlockCandidatesTO> results,
			String user) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		ExamBlockUnBlockCandidatesTO to;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamBlockUnBlockCandidatesTO> tcIterator = results.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				to = tcIterator.next();
				List list=session.createQuery("from ExamBlockUnblockHallTicketBO e  where e.classId="+to.getClassId()+" and e.examId="+to.getExamId()+" and e.studentId="+to.getStudentId()+" and e.hallTktOrMarksCard='"+to.getType()+"'").list();
				if(list==null || list.isEmpty()){
					ExamBlockUnblockHallTicketBO bo =new ExamBlockUnblockHallTicketBO();
					bo.setStudentId(to.getStudentId());
					bo.setExamId(to.getExamId());
					bo.setClassId(to.getClassId());
					bo.setBlockReason(to.getReason());
					bo.setIsActive(true);
					bo.setCreatedBy(user);
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(user);
					bo.setLastModifiedDate(new Date());
					bo.setHallTktOrMarksCard(to.getType());
					session.save(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
}
