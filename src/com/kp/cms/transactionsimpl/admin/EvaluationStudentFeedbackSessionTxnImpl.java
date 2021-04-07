package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;
import com.kp.cms.transactions.admin.IEvaluationStudentFeedbackSessionTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EvaluationStudentFeedbackSessionTxnImpl implements IEvaluationStudentFeedbackSessionTransaction{
	public static volatile EvaluationStudentFeedbackSessionTxnImpl impl = null;
	public static EvaluationStudentFeedbackSessionTxnImpl getInstance(){
		if(impl == null){
			impl = new EvaluationStudentFeedbackSessionTxnImpl();
			return impl;
		}
		return impl;
	}
	@Override
	public List<EvaluationStudentFeedbackSession> getFeedbackSessionList() throws Exception {
		Session session = null;
		List<EvaluationStudentFeedbackSession> boList;
		try{
			
			session = HibernateUtil.getSession();
			boList = session.createQuery("from EvaluationStudentFeedbackSession evaSession where evaSession.isactive = 1").list();
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
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackSessionTransaction#addFeedbackSession(com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo)
	 */
	@Override
	public boolean addFeedbackSession( EvaluationStudentFeedbackSessionTo sessionTo,EvaluationStudentFeedbackSessionForm form,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("add")){
				EvaluationStudentFeedbackSession bo = new EvaluationStudentFeedbackSession();
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
				EvaluationStudentFeedbackSession bo = (EvaluationStudentFeedbackSession)session.get(EvaluationStudentFeedbackSession.class, form.getId());
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
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackSessionTransaction#editStuFeedbackSession(com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm)
	 */
	@Override
	public EvaluationStudentFeedbackSession editStuFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm)
			throws Exception {
		Session session = null;
		EvaluationStudentFeedbackSession feedbackSession;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaluationStudentFeedbackSession bo where bo.id="+evaStuFeedbackSessionForm.getId();
			Query query = session.createQuery(str);
			feedbackSession = (EvaluationStudentFeedbackSession) query.uniqueResult();
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
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackSessionTransaction#deleteEvalStudentFeedbackSession(com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm)
	 */
	@Override
	public boolean deleteEvalStudentFeedbackSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm)
			throws Exception {
		Session session = null;
		boolean isDeleted = false;
		Transaction tx = null;
		try{
			session  = HibernateUtil.getSession();
			tx =session.beginTransaction();
			tx.begin();
			EvaluationStudentFeedbackSession feedbackSession = (EvaluationStudentFeedbackSession)session.get(EvaluationStudentFeedbackSession.class, evaStuFeedbackSessionForm.getId());
			feedbackSession.setLastModifiedDate(new Date());
			feedbackSession.setModifiedBy(evaStuFeedbackSessionForm.getUserId());
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
	 * @see com.kp.cms.transactions.admin.IEvaluationStudentFeedbackSessionTransaction#checkDuplicateSession(com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm)
	 */
	@Override
	public boolean checkDuplicateSession( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm)
			throws Exception {
		Session session = null;
		boolean isExist = false;
		int i;
		try{
			session = HibernateUtil.getSession();
			String sessions = evaStuFeedbackSessionForm.getName().replaceAll("[']","");
			String month = evaStuFeedbackSessionForm.getMonth();
			String year = evaStuFeedbackSessionForm.getYear();
			Integer academicYear = Integer.parseInt(evaStuFeedbackSessionForm.getAcademicYear());
			String str = "from EvaluationStudentFeedbackSession session where session.isactive=1 and session.name='"+sessions+"' and session.month='"+month+"' and session.year='"+year +"' and session.academicYear="+academicYear;
			Query query = session.createQuery(str);
			EvaluationStudentFeedbackSession feedbackSession = (EvaluationStudentFeedbackSession) query.uniqueResult();
			if(feedbackSession!= null && !feedbackSession.toString().isEmpty()){
				if(feedbackSession.getId() == evaStuFeedbackSessionForm.getId()){
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
