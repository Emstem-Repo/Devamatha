package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.PeersFeedbackSessionForm;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;
import com.kp.cms.transactions.admin.IPeersFeedbackSessionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PeersFeedbackSessionTxnImpl implements IPeersFeedbackSessionTransaction{
	public static volatile PeersFeedbackSessionTxnImpl feedbackSessionTxnImpl = null;
	public static PeersFeedbackSessionTxnImpl getInstance(){
		if(feedbackSessionTxnImpl == null){
			feedbackSessionTxnImpl =new PeersFeedbackSessionTxnImpl();
			return feedbackSessionTxnImpl;
		}
		return feedbackSessionTxnImpl;

	}
	@Override
	public List<PeerFeedbackSession> getPeersFeedbackSessionDetails() throws Exception {
		Session session = null;
		List<PeerFeedbackSession> feedbackSessions;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeerFeedbackSession peerFeedbackSession where peerFeedbackSession.isActive = 1";
			Query query = session.createQuery(str);
			feedbackSessions = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return feedbackSessions;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersFeedbackSessionTransaction#checkDuplicatePeersFeedback(com.kp.cms.forms.admin.PeersFeedbackSessionForm)
	 */
	@Override
	public boolean checkDuplicatePeersFeedback( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception {
		Session session = null;
		boolean isExist = false;
		int i;
		try{
			session = HibernateUtil.getSession();
			String sessions = peersFeedbackSessionForm.getSessionName().replaceAll("[']","");
			String month = peersFeedbackSessionForm.getMonth();
			String year = peersFeedbackSessionForm.getYear();
			Integer academicYear = Integer.parseInt(peersFeedbackSessionForm.getAcademicYear());
			String str = "from PeerFeedbackSession session where session.isActive=1 and session.session='"+sessions+"' and session.month='"+month+"' and session.year='"+year +"' and session.academicYear="+academicYear;
			Query query = session.createQuery(str);
			PeerFeedbackSession feedbackSession = (PeerFeedbackSession) query.uniqueResult();
			if(feedbackSession!= null && !feedbackSession.toString().isEmpty()){
				if(feedbackSession.getId() == peersFeedbackSessionForm.getId()){
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersFeedbackSessionTransaction#savePeersFeedbackSession(com.kp.cms.to.admin.PeersFeedbackSessionTo, java.lang.String)
	 */
	@Override
	public boolean savePeersFeedbackSession( PeersFeedbackSessionTo peersFeedbackSessionTo, String mode,PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				PeerFeedbackSession feedbackSession = new PeerFeedbackSession();
				feedbackSession.setSession(peersFeedbackSessionTo.getSessionName().replaceAll("[']",""));
				feedbackSession.setMonth(peersFeedbackSessionTo.getMonth());
				feedbackSession.setYear(peersFeedbackSessionTo.getYear());
				feedbackSession.setAcademicYear(peersFeedbackSessionTo.getAcademicYear());
				feedbackSession.setCreatedBy(peersFeedbackSessionForm.getUserId());
				feedbackSession.setCreatedDate(new Date());
				feedbackSession.setIsActive(true);
				session.save(feedbackSession);
			}else if(mode.equalsIgnoreCase("Edit")){
				PeerFeedbackSession feedbackSession = (PeerFeedbackSession)session.get(PeerFeedbackSession.class, peersFeedbackSessionTo.getId());
				if(peersFeedbackSessionTo.getSessionName()!=null && !peersFeedbackSessionTo.getSessionName().isEmpty()){
					feedbackSession.setSession(peersFeedbackSessionTo.getSessionName().replaceAll("[']",""));
				}
				if(peersFeedbackSessionTo.getMonth()!=null && !peersFeedbackSessionTo.getMonth().isEmpty()){
					feedbackSession.setMonth(peersFeedbackSessionTo.getMonth());
				}
				if(peersFeedbackSessionTo.getYear()!=null && !peersFeedbackSessionTo.getYear().isEmpty()){
					feedbackSession.setYear(peersFeedbackSessionTo.getYear());
				}
				if(peersFeedbackSessionTo.getAcademicYear()!=null && !peersFeedbackSessionTo.getAcademicYear().isEmpty()){
					feedbackSession.setAcademicYear(peersFeedbackSessionTo.getAcademicYear());
				}
				feedbackSession.setLastModifiedBy(peersFeedbackSessionForm.getUserId());
				feedbackSession.setLastModifiedDate(new Date());
				feedbackSession.setIsActive(true);
				session.update(feedbackSession);
			}
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
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
	 * @see com.kp.cms.transactions.admin.IPeersFeedbackSessionTransaction#getEditPeersFeedbackSession(com.kp.cms.forms.admin.PeersFeedbackSessionForm)
	 */
	@Override
	public PeerFeedbackSession getEditPeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception {
		Session session = null;
		PeerFeedbackSession feedbackSession = null;
		try{
			int id = peersFeedbackSessionForm.getId();
			session = HibernateUtil.getSession();
			String str = "from PeerFeedbackSession peerFeedbackSession where peerFeedbackSession.isActive = 1 and peerFeedbackSession.id="+id;
			Query query = session.createQuery(str);
			feedbackSession = (PeerFeedbackSession) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				
			}
		}
		return feedbackSession;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersFeedbackSessionTransaction#deletePeersFeedbackSession(com.kp.cms.forms.admin.PeersFeedbackSessionForm)
	 */
	@Override
	public boolean deletePeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception {
		Session session = null;
		boolean isDeleted = false;
		Transaction tx = null;
		try{
			int id = peersFeedbackSessionForm.getId();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			PeerFeedbackSession feedbackSession = (PeerFeedbackSession)session.get(PeerFeedbackSession.class, id);
			feedbackSession.setIsActive(false);
			feedbackSession.setLastModifiedBy(peersFeedbackSessionForm.getUserId());
			feedbackSession.setLastModifiedDate(new Date());
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
	
}
