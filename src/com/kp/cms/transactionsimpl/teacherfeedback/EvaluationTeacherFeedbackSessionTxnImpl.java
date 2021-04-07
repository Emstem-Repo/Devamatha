package com.kp.cms.transactionsimpl.teacherfeedback;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackSessionForm;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo;
import com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedbackSessionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EvaluationTeacherFeedbackSessionTxnImpl implements IEvaluationTeacherFeedbackSessionTransaction {
	public static volatile EvaluationTeacherFeedbackSessionTxnImpl impl = null;
	public static EvaluationTeacherFeedbackSessionTxnImpl getInstance(){
		if(impl == null){
			impl = new EvaluationTeacherFeedbackSessionTxnImpl();
			return impl;
		}
		return impl;
	}
	@Override
	public List<EvaluationTeacherFeedbackSession> getFeedbackSessionList() throws Exception {
		Session session = null;
		List<EvaluationTeacherFeedbackSession> boList;
		try{
			
			session = HibernateUtil.getSession();
			boList = session.createQuery("from EvaluationTeacherFeedbackSession evaSession where evaSession.isactive = 1").list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
	finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return boList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedbackSessionTransaction#addFeedbackSession(com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo)
	 */
	@Override

	public boolean addFeedbackSession( EvaluationTeacherFeedbackSessionTo sessionTo, EvaluationTeacherFeedbackSessionForm form,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("add")){
				EvaluationTeacherFeedbackSession bo = new EvaluationTeacherFeedbackSession();
				bo.setName(sessionTo.getName().replaceAll("[']",""));
			//	bo.setName(sessionTo.getName());
				bo.setMonth(sessionTo.getMonth());
				bo.setYear(sessionTo.getYear());
				bo.setCreatedBy(form.getUserId());
				bo.setCreatedDate(new Date());
				bo.setIsactive(true);
				bo.setAcademicYear(Integer.parseInt(sessionTo.getAcademicYear()));
				session.save(bo);
			}
			else if(mode.equalsIgnoreCase("edit")){
				EvaluationTeacherFeedbackSession bo = (EvaluationTeacherFeedbackSession)session.get(EvaluationTeacherFeedbackSession.class, form.getId());
				if(sessionTo.getName()!=null && !sessionTo.getName().isEmpty()){
				//	bo.setName(sessionTo.getName());
					bo.setName(sessionTo.getName().replaceAll("[']",""));
				}
				if(sessionTo.getMonth()!=null && !sessionTo.getMonth().isEmpty()){
					bo.setMonth(sessionTo.getMonth());
				}
				if(sessionTo.getYear()!=null && !sessionTo.getYear().isEmpty()){
					bo.setYear(sessionTo.getYear());
				}
				if(sessionTo.getAcademicYear()!=null && !sessionTo.getAcademicYear().isEmpty()){
					bo.setAcademicYear(Integer.parseInt(sessionTo.getAcademicYear()));
				}
				bo.setModifiedBy(form.getUserId());
				bo.setLastModifiedDate(new Date());
				session.update(bo);
			}
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
			isAdded = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedbackSessionTransaction#editTeacherFeedbackSession(com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo)
	 */
	@Override
	public EvaluationTeacherFeedbackSession editTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm)
			throws Exception {
		Session session = null;
		EvaluationTeacherFeedbackSession feedbackSession;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaluationTeacherFeedbackSession bo where bo.id="+evaTeacherFeedbackSessionForm.getId();
			Query query = session.createQuery(str);
			feedbackSession = (EvaluationTeacherFeedbackSession) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return feedbackSession;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedbackSessionTransaction#deleteEvalTeacherFeedbackSession(com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo)
	 */
	@Override
	public boolean deleteEvalTeacherFeedbackSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm)
			throws Exception {
		Session session = null;
		boolean isDeleted = false;
		Transaction tx = null;
		try{
			session  = HibernateUtil.getSession();
			tx =session.beginTransaction();
			tx.begin();
			EvaluationTeacherFeedbackSession feedbackSession = (EvaluationTeacherFeedbackSession)session.get(EvaluationTeacherFeedbackSession.class, evaTeacherFeedbackSessionForm.getId());
			feedbackSession.setLastModifiedDate(new Date());
			feedbackSession.setModifiedBy(evaTeacherFeedbackSessionForm.getUserId());
			feedbackSession.setIsactive(false);
			session.update(feedbackSession);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDeleted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedbackSessionTransaction#checkDuplicateSession(com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo)
	 */
	@Override
	
	public boolean checkDuplicateSession( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm) throws Exception {
		Session session = null;
		boolean isExist = false;
		int i;
		try{
			session = HibernateUtil.getSession();
			String sessions = evaTeacherFeedbackSessionForm.getName().replaceAll("[']","");
			String month = evaTeacherFeedbackSessionForm.getMonth();
			String year = evaTeacherFeedbackSessionForm.getYear();
			Integer academicYear = Integer.parseInt(evaTeacherFeedbackSessionForm.getAcademicYear());
			String str = "from EvaluationTeacherFeedbackSession session where session.isactive=1 and session.name='"+sessions+"' and session.month='"+month+"' and session.year='"+year +"' and session.academicYear="+academicYear;
			Query query = session.createQuery(str);
			EvaluationTeacherFeedbackSession feedbackSession = (EvaluationTeacherFeedbackSession) query.uniqueResult();
			if(feedbackSession!= null && !feedbackSession.toString().isEmpty()){
				if(feedbackSession.getId() == evaTeacherFeedbackSessionForm.getId()){
					isExist = false;
				}else{
					isExist = true;
				}
				 
			}
			
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isExist;
	}
}
