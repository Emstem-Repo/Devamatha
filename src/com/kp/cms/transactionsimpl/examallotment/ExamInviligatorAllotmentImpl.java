package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExamInviligatorAllotmentForm;
import com.kp.cms.transactions.examallotment.IExamInviligatorAllotmentTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamInviligatorAllotmentImpl implements IExamInviligatorAllotmentTransaction{
	private static final Log log = LogFactory.getLog(ExamInviligatorAllotmentImpl.class);
	/**
	 * 
	 */
	public static volatile ExamInviligatorAllotmentImpl self=null;
	/**
	 * @return
	 * This method will return instance of this class
	 */
	public static ExamInviligatorAllotmentImpl getInstance(){
		if(self==null)
			self= new ExamInviligatorAllotmentImpl();
		return self;
	}
	/**
	 * 
	 */
	private ExamInviligatorAllotmentImpl(){
	}
	
	public Map<String, String> getExamMap( ExamInviligatorAllotmentForm invForm) throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ExamDefinition d where d.isActive=true and d.examType.id not in (2,3,6) and d.academicYear='"+invForm.getAcademicYear()+"'");
			List<ExamDefinition> list=query.list();
			if(list!=null){
				Iterator<ExamDefinition> iterator=list.iterator();
				while(iterator.hasNext()){
					ExamDefinition exam=iterator.next();
					if(exam.getId()!=0 && exam.getName()!=null && !exam.getName().isEmpty())
					map.put(String.valueOf(exam.getId()),exam.getName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	public Map<String, String> getWorkLocationMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmployeeWorkLocationBO b where b.isActive=true");
			List<EmployeeWorkLocationBO> list=query.list();
			if(list!=null){
				Iterator<EmployeeWorkLocationBO> iterator=list.iterator();
				while(iterator.hasNext()){
					EmployeeWorkLocationBO loc=iterator.next();
					if(loc.getId()!=0 && loc.getName()!=null && !loc.getName().isEmpty())
					map.put(String.valueOf(loc.getId()),loc.getName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTeacherList(ExamInviligatorAllotmentForm InvForm,String query)
	throws Exception {
		Session session = null;
		List<Object[]>  teacherList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			teacherList=(List<Object[]>)session.createSQLQuery(query).list();
			return teacherList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public ExamInvigilationDutySettings getSettingsList(ExamInviligatorAllotmentForm InvForm)
	throws Exception {
		Session session = null;
		ExamInvigilationDutySettings settingList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = null;
			HQL= "from ExamInvigilationDutySettings e where e.isActive=1 and e.workLocationId=" +InvForm.getWorkLocationId()+" and e.endMid='" +InvForm.getExamType() +"'";
			Query query = session.createQuery(HQL);
			settingList=(ExamInvigilationDutySettings) query.uniqueResult();
			return settingList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> getSessionList(ExamInviligatorAllotmentForm InvForm,StringBuffer query)
	throws Exception {
		Session session = null;
		List<Object[]>  SessionList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		//	session = HibernateUtil.getSession();
			SessionList=(List<Object[]>)session.createSQLQuery(query.toString()).list();
			return SessionList;
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
	public List<Object[]> getPmSessionList(ExamInviligatorAllotmentForm InvForm,String query)
	throws Exception {
		Session session = null;
		List<Object[]>  pmSessionList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		//	session = HibernateUtil.getSession();
			pmSessionList=(List<Object[]>)session.createSQLQuery(query).list();
			return pmSessionList;
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
	public List<Object[]> getStudentList(ExamInviligatorAllotmentForm InvForm,String query)	throws Exception {
		Session session = null;
		List<Object[]>  studentList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		//	session = HibernateUtil.getSession();
			studentList=(List<Object[]>)session.createSQLQuery(query).list();
			return studentList;
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
	
	public String getExamType(ExamInviligatorAllotmentForm InvForm)throws Exception
	{
		Session session = null;
		ExamDefinition exam=null;
		String examType = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = null;
			HQL= "from ExamDefinition e where e.id="+InvForm.getExamId();
			Query query = session.createQuery(HQL);
			exam=(ExamDefinition) query.uniqueResult();
			if(exam.getExamType().getId()>0 && exam.getExamType().getId()==1){
				examType="E";
			}else if(exam.getExamType().getId()>0 && (exam.getExamType().getId()==4 ||exam.getExamType().getId()==4)){
				examType="M";
			}
			return examType;
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
	
	public boolean update(List<ExamInviligatorDuties> InvDutyList)throws Exception
	{
		boolean result = false;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Transaction txn = null;
		try {
			for (ExamInviligatorDuties obj : InvDutyList) {
				// get id for examid stuid classid subid
				//deleteIfExists(obj.getExamId(), obj.getStudentId(), obj
				//		.getClassId(), obj.getSubjectId());
			//	flag = 1;
				session = sessionFactory.openSession();
				txn = session.beginTransaction();
				session.save(obj);
				txn.commit();
				session.flush();
				session.close();
			}
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
	
	
public int insert_List(ArrayList listBO) {
		
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
				for (Object obj : listBO) {
					flag = 1;
					session = sessionFactory.openSession();
					tx = session.beginTransaction();
					session.save(obj);
					tx.commit();
					session.flush();
					session.close();
					}
			
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	public void removeTeacherAllotment(ExamInviligatorAllotmentForm InvForm)
		throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
		session = HibernateUtil.getSession();//)" +
		String query="from ExamInviligatorDuties a where a.examDate='" +CommonUtil.ConvertStringToSQLDate(InvForm.getAllotmentDate())+"' and a.examinationSessions.id="+InvForm.getExamSessionId()+" and a.examId="+InvForm.getExamId()+" and a.workLocationId ="+InvForm.getWorkLocationId()+" and a.isActive=1";
		List<ExamInviligatorDuties> boList = session.createQuery(query).list();
		if(boList != null && !boList.isEmpty()){
			tx = session.beginTransaction();
			for (ExamInviligatorDuties examthrAllotment : boList) {
					examthrAllotment.setIsActive(false);
					examthrAllotment.setModifiedBy(InvForm.getUserId());
					examthrAllotment.setLastModifiedDate(new Date());
					session.update(examthrAllotment);
			}
			tx.commit();
		}
		session.flush();
		session.close();
		}catch (Exception e) {
		if(tx != null)
			tx.rollback();
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		}
	
	public List<ExamInvigilatorAvailable> setAvailableTeacher(StringBuilder query)
	throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query.toString());
			List<ExamInvigilatorAvailable> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	 public List<ExamInviligatorExemptionDatewise> setExemptedTeacher(StringBuilder query)throws Exception
	 {
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				Query query1 = session.createQuery(query.toString());
				List<ExamInviligatorExemptionDatewise> list = query1.list();
				session.flush();
				return list;
			} catch (Exception e) {
				if (session != null) {
					session.flush();
				}
				throw new ApplicationException(e);
			}
	}
	 
	 
	 
	 public void removeTeacherAllotmentRollback(ExamInviligatorAllotmentForm InvForm)
		throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
		session = HibernateUtil.getSession();//)" +
		String query="from ExamInviligatorDuties a where a.examId="+InvForm.getExamId()+" and a.workLocationId ="+InvForm.getWorkLocationId()+" and a.isActive=1";
		List<ExamInviligatorDuties> boList = session.createQuery(query).list();
		if(boList != null && !boList.isEmpty()){
			tx = session.beginTransaction();
			for (ExamInviligatorDuties examthrAllotment : boList) {
					examthrAllotment.setIsActive(false);
					examthrAllotment.setModifiedBy(InvForm.getUserId());
					examthrAllotment.setLastModifiedDate(new Date());
					session.update(examthrAllotment);
			}
			tx.commit();
		}
		session.flush();
		session.close();
		}catch (Exception e) {
		if(tx != null)
			tx.rollback();
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		}
}
