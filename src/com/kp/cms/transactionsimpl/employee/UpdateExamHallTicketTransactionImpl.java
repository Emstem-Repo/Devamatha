package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.UpdateExamHallTicketForm;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.transactions.exam.IUpdateExamHallTicketTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class UpdateExamHallTicketTransactionImpl implements
		IUpdateExamHallTicketTransaction {

	@Override
	public List<Object> getList(String query)
			throws Exception {
		Session session = null;
		List<Object> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public boolean updatePublishHallTicketList(List<ExamPublishHallTicketTO> finalList,UpdateExamHallTicketForm updateExamHallTicketForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ExamPublishHallTicketTO tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamPublishHallTicketTO> tcIterator = finalList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				ExamPublishHallTicketMarksCardBO examPublishHallTicketMarksCardBO=(ExamPublishHallTicketMarksCardBO)session.get(ExamPublishHallTicketMarksCardBO.class, tcLChecklist.getId());
				examPublishHallTicketMarksCardBO.setDownloadEndDate(CommonUtil.ConvertStringToSQLDate(updateExamHallTicketForm.getToEndDate()));
				if(updateExamHallTicketForm.getToRevEndDate()!=null && !updateExamHallTicketForm.getToRevEndDate().isEmpty())
					examPublishHallTicketMarksCardBO.setRevaluationEndDate(CommonUtil.ConvertStringToSQLDate(updateExamHallTicketForm.getToRevEndDate()));
				examPublishHallTicketMarksCardBO.setModifiedBy(updateExamHallTicketForm.getUserId());
				examPublishHallTicketMarksCardBO.setLastModifiedDate(new Date());
				session.update(examPublishHallTicketMarksCardBO);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

}
