package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.PeersEvaluationFeedback;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackForm;
import com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PeersEvaluationFeedbackTxnImpl implements IPeersEvaluationFeedbackTransaction{
	public static volatile PeersEvaluationFeedbackTxnImpl txnImpl = null;
	public static PeersEvaluationFeedbackTxnImpl getInstance(){
		if(txnImpl == null){
			txnImpl = new PeersEvaluationFeedbackTxnImpl();
			return txnImpl;
		}
		return txnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getAlreadySubmittedDetails(java.lang.String)
	 */
	@Override
	public boolean getAlreadySubmittedDetails(
			PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)
			throws Exception {
		Session session = null;
		boolean isSubmit = false;
		try {
			int teacherId = 0;
			int sessionId = 0;
			if (peersEvaluationFeedbackForm.getUserId() != null
					&& !peersEvaluationFeedbackForm.getUserId().isEmpty()) {
				teacherId = Integer.parseInt(peersEvaluationFeedbackForm
						.getUserId());
			}
			if (peersEvaluationFeedbackForm.getSessionId() != null
					&& !peersEvaluationFeedbackForm.getSessionId().isEmpty()) {
				sessionId = Integer.parseInt(peersEvaluationFeedbackForm
						.getSessionId());
			}
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationFeedback peersEvaluation where peersEvaluation.isActive = 1 "
					+ "and peersEvaluation.teacherId.id="
					+ teacherId
					+ " and peersEvaluation.sessionid=" + sessionId;
			Query query = session.createQuery(str);
			PeersEvaluationFeedback bo = (PeersEvaluationFeedback) query
					.uniqueResult();
			if (bo != null && !bo.toString().isEmpty()) {
				isSubmit = true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isSubmit;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getAssignPeersGroupList(java.lang.String)
	 */
	@Override
	public List<AssignPeersGroups> getEvaluatorDetailsFromAssignPeersGrp( String query) throws Exception {
		Session session = null;
		List<AssignPeersGroups> assignPeersGroupsList;
		try{
			session = HibernateUtil.getSession();
			assignPeersGroupsList =  session.createQuery(query).list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return assignPeersGroupsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getAssignPeersList(java.lang.String)
	 */
	@Override
	public List<AssignPeersGroups> getAssignPeersList( String assignPeersGroupQuery,List<AssignPeersGroups> assignPeersGrpslist,PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception {
		Session session = null;
		Map<Integer, Integer> facultyMap = peersEvaluationFeedbackForm.getFacultyGroupIdsMap();
		if(facultyMap == null ){
			facultyMap = new HashMap<Integer, Integer>();
		}
		try{
			session = HibernateUtil.getSession();
			List<AssignPeersGroups>  list =session.createQuery(assignPeersGroupQuery).list();
			if(list!=null && !list.isEmpty()){
				Iterator<AssignPeersGroups> iterator = list.iterator();
				while (iterator.hasNext()) {
					AssignPeersGroups assignPeersGroups = (AssignPeersGroups) iterator .next();
					if(facultyMap.isEmpty()){
						facultyMap.put(assignPeersGroups.getUsers().getId(), assignPeersGroups.getPeersEvaluationGroups().getId());
						assignPeersGrpslist.add(assignPeersGroups);
					}else{
						if(!facultyMap.containsKey(assignPeersGroups.getUsers().getId())){
							facultyMap.put(assignPeersGroups.getUsers().getId(), assignPeersGroups.getPeersEvaluationGroups().getId());
							assignPeersGrpslist.add(assignPeersGroups);
						}
					}
				}
			}
			peersEvaluationFeedbackForm.setFacultyGroupIdsMap(facultyMap);
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return assignPeersGrpslist;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getCheckAssignPeersGrp(java.lang.String)
	 */
	@Override
	public Map<Integer, Integer> getCheckAssignPeersGrp(String query1)
			throws Exception {
		Session session = null;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(query1);
			List<AssignPeersGroups> list = query.list();
			if (list != null && !list.toString().isEmpty()) {
				Iterator<AssignPeersGroups> iterator = list.iterator();
				while (iterator.hasNext()) {
					AssignPeersGroups assignPeersGroups = (AssignPeersGroups) iterator
							.next();
					if (assignPeersGroups.getUsers() != null
							&& assignPeersGroups.getUsers().getId() != 0
							&& assignPeersGroups.getDepartment() != null
							&& assignPeersGroups.getDepartment().getId() != 0) {
						map.put(assignPeersGroups.getUsers().getId(),
								assignPeersGroups.getDepartment().getId());
					}
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getTeacherListFromEmployee(java.lang.String)
	 */
	@Override
	public Map<Integer,String> getTeacherList(String teachersListQuery,
			Map<Integer,String> users) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(teachersListQuery);
			List<Object[]> usrList = query.list();
			if(usrList!=null && !usrList.isEmpty()){
				Iterator<Object[]> iterator = usrList.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(!users.containsKey(Integer.parseInt(objects[0].toString()))){
						users.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
					}
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return users;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getUserDetails(java.lang.String)
	 */
	@Override
	public Users getUserDetails(String userId) throws Exception {
		Session session =null;
		Users users = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from Users users where users.id="+Integer.parseInt(userId)+" and users.isActive =1";
			Query query = session.createQuery(str);
			users= (Users) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return users;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#peersQuestionsQuery(java.lang.String)
	 */
	@Override
	public List<EvaFacultyFeedbackQuestion> peersQuestionsQuery(String query)throws Exception {
		Session session = null;
		List<EvaFacultyFeedbackQuestion> questionsBo;
		try{
			session = HibernateUtil.getSession();
			Query qry = session.createQuery(query);
			questionsBo = qry.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return questionsBo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#savePeersEvaluationFeedback(com.kp.cms.bo.admin.PeersEvaluationFeedback)
	 */
	@Override
	public boolean savePeersEvaluationFeedback( PeersEvaluationFeedback peersEvaluationFeedback,PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception {
		Session session = null;
		boolean isAdded = false;
		Transaction tx =null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(peersEvaluationFeedback);
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
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getFacultiesAlreadyEvaluationCompleted(com.kp.cms.forms.admin.PeersEvaluationFeedbackForm)
	 */
	@Override
	public List<Integer> getFacultiesAlreadyEvaluationCompleted( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception {
		Session session =null;
		List<Integer> listOfFacultyAlreadyEvaluate = null;
		try{
			int teacherId = Integer.parseInt(peersEvaluationFeedbackForm.getUserId());
			int sessionId = Integer.parseInt(peersEvaluationFeedbackForm.getSessionId());
			session = HibernateUtil.getSession();
			String str = "select faculty.peerId.id from PeersEvaluationFeedback peerEvaluation join peerEvaluation.feedbackFaculty faculty " +
						 " where peerEvaluation.isActive = 1" +
						 " and peerEvaluation.teacherId.id="+teacherId+" and peerEvaluation.sessionid=" + sessionId;
			Query query = session.createQuery(str);
			listOfFacultyAlreadyEvaluate = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return listOfFacultyAlreadyEvaluate;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#getExistsBO(com.kp.cms.forms.admin.PeersEvaluationFeedbackForm)
	 */
	@Override
	public PeersEvaluationFeedback getExistsBO( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception {
		Session session =null;
		PeersEvaluationFeedback evaluationFeedback;
		try{
			/* before saving the bo once again we are checking the duplicate entry */
			int teacherId = Integer.parseInt(peersEvaluationFeedbackForm.getUserId());
			int sessionId = Integer.parseInt(peersEvaluationFeedbackForm.getSessionId());
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationFeedback peersEvaluation where peersEvaluation.isActive = 1 "
				+ "and peersEvaluation.teacherId.id="
				+ teacherId
				+ " and peersEvaluation.sessionid=" + sessionId;
			Query query = session.createQuery(str);
			evaluationFeedback = (PeersEvaluationFeedback) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return evaluationFeedback;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction#checkPeerIsDuplicate(com.kp.cms.forms.admin.PeersEvaluationFeedbackForm)
	 */
	@Override
	public boolean checkPeerIsDuplicate( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception {
		Session session =null;
		boolean isDuplicate = false;
		try{
			int teacherId = Integer.parseInt(peersEvaluationFeedbackForm.getUserId());
			int sessionId = Integer.parseInt(peersEvaluationFeedbackForm.getSessionId());
			int peerId = peersEvaluationFeedbackForm.getPeerId();
			session = HibernateUtil.getSession();
			String str = " from PeersEvaluationFeedback peerEvaluation join peerEvaluation.feedbackFaculty faculty " +
						 " where peerEvaluation.isActive = 1" +
						 " and peerEvaluation.teacherId.id="+teacherId+" and peerEvaluation.sessionid=" + sessionId +
						 " and faculty.peerId ="+peerId+" and faculty.isActive =1";
			Query query = session.createQuery(str);
			PeersEvaluationFeedback bo = (PeersEvaluationFeedback) query.uniqueResult();
			if(bo!=null && !bo.toString().isEmpty()){
				isDuplicate = true;
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDuplicate;
	}
}
