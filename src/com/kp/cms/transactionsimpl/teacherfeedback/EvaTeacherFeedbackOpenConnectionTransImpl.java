package com.kp.cms.transactionsimpl.teacherfeedback;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedbackOpenConnectionForm;
import com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EvaTeacherFeedbackOpenConnectionTransImpl  implements IEvaTeacherFeedbackOpenConnectionTransaction{

	public static volatile EvaTeacherFeedbackOpenConnectionTransImpl connectionTransImpl = null;
	public static EvaTeacherFeedbackOpenConnectionTransImpl getInstance(){
		if(connectionTransImpl == null){
			connectionTransImpl = new EvaTeacherFeedbackOpenConnectionTransImpl();
			return connectionTransImpl;
		}
		return connectionTransImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#checkDuplicate()
	 */
	@Override
	public boolean checkDuplicate( EvaTeacherFeedbackOpenConnectionForm connectionForm)
			throws Exception {
		Session session = null;
		boolean isDuplicate = false;
		try{
			String[] clsId = connectionForm.getClassesId();
			session = HibernateUtil.getSession();
			int i =0;
			for(i=0;i<clsId.length;i++){
				int classId = Integer.parseInt(clsId[i]);
				String str = "from EvaTeacherFeedbackOpenConnection connection where connection.isActive=1 " +
							" and connection.feedbackSession.id= "+connectionForm.getSessionId()+
							" and connection.classesId.id=" +classId;
				if(connectionForm.getSpecializationId()!=null && !connectionForm.getSpecializationId().isEmpty()){
					str = str + " and connection.examSpecializationBO.id = "+Integer.parseInt(connectionForm.getSpecializationId());
				}
				Query query =session.createQuery(str);
				EvaTeacherFeedbackOpenConnection connection = (EvaTeacherFeedbackOpenConnection) query.uniqueResult();
				if(connection!=null && !connection.toString().isEmpty()){
					if(connection.getId() == connectionForm.getId()){
						isDuplicate = false;
					}else{
						isDuplicate = true;
						
					}
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
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#submitOpenConnectionDetails(com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection, java.lang.String)
	 */
	@Override
	public boolean submitOpenConnectionDetails( List<EvaTeacherFeedbackOpenConnection> boList) throws Exception{
	Session session = null;
	Transaction tx = null;
	boolean isAdded = false;
	try{
		session  = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if(boList!=null && !boList.isEmpty()){
			Iterator<EvaTeacherFeedbackOpenConnection> iterator = boList.iterator();
			while (iterator.hasNext()) {
				EvaTeacherFeedbackOpenConnection evaStudentFeedbackOpenConnection = (EvaTeacherFeedbackOpenConnection) iterator
						.next();
				session.save(evaStudentFeedbackOpenConnection);
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
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#getOpenConnectionDetails(int)
	 */
	@Override
	public EvaTeacherFeedbackOpenConnection getOpenConnectionDetails(int id) throws Exception {
		Session session = null;
		EvaTeacherFeedbackOpenConnection connection;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaTeacherFeedbackOpenConnection conn where conn.id="+id+" and conn.isActive = 1";
			Query query = session.createQuery(str);
			connection = (EvaTeacherFeedbackOpenConnection) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return connection;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#getConnectionList()
	 */
	@Override
	public List<EvaTeacherFeedbackOpenConnection> getConnectionList(int year,String sessionId) throws Exception {
		Session session = null;
		List<EvaTeacherFeedbackOpenConnection> connection;
		try{
			session = HibernateUtil.getSession();
			//modified by manu,get record by academic year
			String str = "select conn from EvaTeacherFeedbackOpenConnection conn join conn.classesId cl join cl.classSchemewises cls " +
					" join cls.curriculumSchemeDuration cud where conn.isActive=1 and cl.isActive=1 and cud.academicYear="+year;
			if(sessionId!=null && !sessionId.isEmpty()){
				str=str+" and conn.feedbackSession.id="+sessionId;
			}
			Query query = session.createQuery(str);
			connection = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return connection;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#deleteOpenConnection(com.kp.cms.forms.teacherfeedback.EvaTeacherFeedbackOpenConnectionForm)
	 */
	@Override
	public boolean deleteOpenConnection( EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			EvaTeacherFeedbackOpenConnection connection = (EvaTeacherFeedbackOpenConnection)session.get(EvaTeacherFeedbackOpenConnection.class, connectionForm.getId());
			connection.setLastModifiedDate(new Date());
			connection.setModifiedBy(connectionForm.getUserId());
			connection.setIsActive(false);
			session.update(connection);
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#updateOpenConnection(com.kp.cms.forms.teacherfeedback.EvaTeacherFeedbackOpenConnectionForm)
	 */
	@Override
	public boolean updateOpenConnection( EvaTeacherFeedbackOpenConnectionForm connectionForm) throws Exception {
		Session session = null;
		EvaTeacherFeedbackOpenConnection connection = null;
		Transaction tx = null;
		boolean isUpdated = false;
		try{
			
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			connection = (EvaTeacherFeedbackOpenConnection)session.get(EvaTeacherFeedbackOpenConnection.class, connectionForm.getId());
			Classes classes = new Classes();
			String[] clsId = connectionForm.getClassesId();
			classes.setId(Integer.parseInt(clsId[0]));
			connection.setClassesId(classes);
			EvaluationTeacherFeedbackSession sessionBo = new EvaluationTeacherFeedbackSession();
			sessionBo.setId(Integer.parseInt(connectionForm.getSessionId()));
			connection.setFeedbackSession(sessionBo);
			connection.setStartDate(CommonUtil.ConvertStringToDate(connectionForm.getStartDate()));
			connection.setEndDate(CommonUtil.ConvertStringToDate(connectionForm.getEndDate()));
			connection.setLastModifiedDate(new Date());
			connection.setModifiedBy(connectionForm.getUserId());
			connection.setIsActive(true);
			/* newly added by sudhir */
			if(connectionForm.getSpecializationId()!=null && !connectionForm.getSpecializationId().isEmpty()){
				ExamSpecializationBO bo = new ExamSpecializationBO();
				bo.setId(Integer.parseInt(connectionForm.getSpecializationId()));
				connection.setExamSpecializationBO(bo);
			}else{
				connection.setExamSpecializationBO(null);
			}
			/*-----------------------*/
			session.update(connection);
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
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#getSessionDetails(java.lang.Integer)
	 */
	@Override
	public List<EvaluationTeacherFeedbackSession> getSessionDetails(Integer year) throws Exception {
		Session session = null;
		List<EvaluationTeacherFeedbackSession> sessionsList = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from EvaluationTeacherFeedbackSession session where session.isactive =1  and session.academicYear="+year;
			Query query  = session.createQuery(str);
			sessionsList =query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
				}
			}
		return sessionsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#getSpecializationName(java.lang.String)
	 */
	@Override
	public String getSpecializationName(String specializationId) throws Exception {
		Session session =null;
	String specializationName = null;
		try{
			session = HibernateUtil.getSession();
			String str = "select spec.name from ExamSpecializationBO spec where spec.isActive =1 and spec.id="+Integer.parseInt(specializationId);
			Query query = session.createQuery(str);
			specializationName = (String) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
				}
			}
		return specializationName;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedbackOpenConnectionTransaction#getSpecializationIds(java.lang.String[], java.lang.String)
	 */
	@SuppressWarnings("null")
	@Override
	public Map<Integer, Integer> getSpecializationIds(String[] classesIds, String specializationName) throws Exception {
		Session session = null;
		Map<Integer,Integer> specializationIds = new HashMap<Integer, Integer>();
		try{
			session = HibernateUtil.getSession();
			if(classesIds!=null && classesIds.length !=0 ){
				String[] classesId = classesIds;
				int i =0;
				for(i=0;i<classesId.length;i++){
					Integer classId = Integer.parseInt(classesId[i]);
					String str = "from ExamSpecializationBO exam where exam.courseUtilBO.courseID in (select cls.course.id from Classes cls where cls.id = "+classId+") and exam.isActive=1 and exam.name='"+specializationName+"'";
					Query query = session.createQuery(str); 
					ExamSpecializationBO bo = (ExamSpecializationBO) query.uniqueResult();
					if(bo!=null && !bo.toString().isEmpty()){
						specializationIds.put(classId, bo.getId());
					}
				}
			}
			
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
				}
			}
		return specializationIds;
	}
	

}
