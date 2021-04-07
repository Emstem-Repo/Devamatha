package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import com.kp.cms.bo.exam.OpenInternalMark;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm;
import com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class PeersEvaluationOpenSessionTransactionImpl implements IPeersEvaluationOpenSessionTransaction{
	public static volatile PeersEvaluationOpenSessionTransactionImpl transactionImpl = null;
	public static PeersEvaluationOpenSessionTransactionImpl getInstance(){
		if(transactionImpl == null){
			transactionImpl = new PeersEvaluationOpenSessionTransactionImpl();
			return transactionImpl;
		}
		return transactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#getDepartmentList()
	 */
	@Override
	public List<Department> getDepartmentList() throws Exception {
		Session session = null;
		List<Department> departments = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from Department department where department.isActive = 1";
				Query query = session.createQuery(str);
				departments = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
	finally {
		if (session != null) {
			session.flush();
		}
	}
		return departments;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#getOpenConnectionList()
	 */
	@Override
	public List<PeersEvaluationOpenSession> getOpenConnectionList() throws Exception {
		Session session = null;
		List<PeersEvaluationOpenSession> openSessions = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationOpenSession openSession where openSession.isActive = 1";
			Query query= session.createQuery(str);
			openSessions = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
	finally {
		if (session != null) {
			session.flush();
		}
	}
		return openSessions;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#getSessionsByYear(int)
	 */
	@Override
	public Map<Integer, String> getSessionsByYear(int currentYear) throws Exception {
		Session session = null;
		Map<Integer, String> sessionMap = new HashMap<Integer, String>();
		List<PeerFeedbackSession> feedbackSessions =null;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeerFeedbackSession feedbackSession where feedbackSession.isActive = 1 and feedbackSession.academicYear="+currentYear;
			Query query = session.createQuery(str);
			feedbackSessions = query.list();
			if(feedbackSessions!=null && !feedbackSessions.isEmpty()){
				Iterator<PeerFeedbackSession> iterator = feedbackSessions.iterator();
				while (iterator.hasNext()) {
					PeerFeedbackSession peerFeedbackSession = (PeerFeedbackSession) iterator .next();
					sessionMap.put(peerFeedbackSession.getId(), peerFeedbackSession.getSession());
				}
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
	finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
		return sessionMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#checkDuplicate(com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm)
	 */
	@Override
	public boolean checkDuplicate( int deptId,PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception {
		Session session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
				String str = "from PeersEvaluationOpenSession connection where connection.isActive=1 and connection.departmentId.id=" 
					+deptId+ " and (('"+CommonUtil.ConvertStringToSQLDate(peersEvaluationOpenSessionForm.getStartDate())+"') >= connection.startDate " +
					" and ('"+CommonUtil.ConvertStringToSQLDate(peersEvaluationOpenSessionForm.getEndDate())+"') <= connection.endDate )) ";
				Query query =session.createQuery(str);
				PeersEvaluationOpenSession connection = (PeersEvaluationOpenSession) query.uniqueResult();
				if(connection!=null && !connection.toString().isEmpty()){
					if(connection.getId() == peersEvaluationOpenSessionForm.getId()){
						isDuplicate = false;
					}else{
						isDuplicate = true;
					}
				}
		}catch (Exception e) {
			isDuplicate = false;
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
			}
		}
		return isDuplicate;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#submitOpenSession(java.util.List)
	 */
	@Override
	public boolean submitOpenSession( List<PeersEvaluationOpenSession> openSessions) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			
			session  = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(openSessions!=null && !openSessions.isEmpty()){
				Iterator<PeersEvaluationOpenSession> iterator = openSessions.iterator();
				while (iterator.hasNext()) {
					PeersEvaluationOpenSession peersEvaluationOpenSession = (PeersEvaluationOpenSession) iterator .next();
					String str = "from PeersEvaluationOpenSession connection where connection.isActive=1 and connection.departmentId.id=" 
						+peersEvaluationOpenSession.getDepartmentId().getId();
					PeersEvaluationOpenSession openSession = (PeersEvaluationOpenSession)session.createQuery(str).uniqueResult();
					if(openSession == null){
						session.save(peersEvaluationOpenSession);
					}else{
						PeerFeedbackSession peerFeedbackSession = new PeerFeedbackSession();
						peerFeedbackSession.setId(peersEvaluationOpenSession.getPeerFeedbackSession().getId());
						openSession.setPeerFeedbackSession(peerFeedbackSession);
						openSession.setStartDate(peersEvaluationOpenSession.getStartDate());
						openSession.setEndDate(peersEvaluationOpenSession.getEndDate());
						openSession.setLastModifiedDate(new Date());
						openSession.setModifiedBy(peersEvaluationOpenSession.getModifiedBy());
						session.update(openSession);
					}
				}
			}
			
			tx.commit();
			session.flush();
			isAdded = true;
			}catch (Exception e) {
				isAdded =false;
				
				throw new ApplicationException(e);
			}
			finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		return isAdded;
		}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#updatePeersOpenSession(com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm)
	 */
	@Override
	public boolean updatePeersOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception {
		Session session = null;
		PeersEvaluationOpenSession peersEvaluationOpenSession = null;
		Transaction tx = null;
		boolean isUpdated = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			peersEvaluationOpenSession = (PeersEvaluationOpenSession)session.get(PeersEvaluationOpenSession.class, peersEvaluationOpenSessionForm.getId());
			Department department = new Department();
			String[] deptId = peersEvaluationOpenSessionForm.getDepartmentIds();
			department.setId(Integer.parseInt(deptId[0]));
			peersEvaluationOpenSession.setDepartmentId(department);
			PeerFeedbackSession sessionBo = new PeerFeedbackSession();
			sessionBo.setId(Integer.parseInt(peersEvaluationOpenSessionForm.getSessionId()));
			peersEvaluationOpenSession.setPeerFeedbackSession(sessionBo);
			peersEvaluationOpenSession.setStartDate(CommonUtil.ConvertStringToDate(peersEvaluationOpenSessionForm.getStartDate()));
			peersEvaluationOpenSession.setEndDate(CommonUtil.ConvertStringToDate(peersEvaluationOpenSessionForm.getEndDate()));
			peersEvaluationOpenSession.setLastModifiedDate(new Date());
			peersEvaluationOpenSession.setModifiedBy(peersEvaluationOpenSessionForm.getUserId());
			peersEvaluationOpenSession.setIsActive(true);
			session.update(peersEvaluationOpenSession);
			tx.commit();
			isUpdated = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
				}
			}
		return isUpdated;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationOpenSessionTransaction#deletePeersOpenSession(com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm)
	 */
	@Override
	public boolean deletePeersOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			PeersEvaluationOpenSession peersEvaluationOpenSession = (PeersEvaluationOpenSession)session.get(PeersEvaluationOpenSession.class, peersEvaluationOpenSessionForm.getId());
			peersEvaluationOpenSession.setLastModifiedDate(new Date());
			peersEvaluationOpenSession.setModifiedBy(peersEvaluationOpenSessionForm.getUserId());
			peersEvaluationOpenSession.setIsActive(false);
			session.update(peersEvaluationOpenSession);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			isDeleted = false;
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
				}
			}
		return isDeleted;
	}
	@Override
	public PeersEvaluationOpenSession editPeersEvaluationOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)
			throws Exception {
		Session session = null;
		PeersEvaluationOpenSession evaluationOpenSession = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationOpenSession openSession where openSession.isActive = 1 and openSession.id="+peersEvaluationOpenSessionForm.getId();
			evaluationOpenSession = (PeersEvaluationOpenSession)session.createQuery(str).uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			
				}
			}
		return evaluationOpenSession;
	}
}
