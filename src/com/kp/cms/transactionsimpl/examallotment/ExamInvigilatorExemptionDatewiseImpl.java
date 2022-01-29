package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExamInvigilatorExcemptionDatewiseForm;
import com.kp.cms.transactions.examallotment.IExamInvigilatorExemptionDatewise;
import com.kp.cms.utilities.HibernateUtil;

public class ExamInvigilatorExemptionDatewiseImpl implements IExamInvigilatorExemptionDatewise{
private static volatile ExamInvigilatorExemptionDatewiseImpl TransImpl=null;
	 
	
	/**
	 * instance()
	 * @return
	 */
	public static ExamInvigilatorExemptionDatewiseImpl getInstance(){
		if(TransImpl == null){
			TransImpl=new ExamInvigilatorExemptionDatewiseImpl();
		}
		return TransImpl;
	}


	@Override
	public Map<Integer, String> getExam(String academicYear) throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<ExamDefinitionBO> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamDefinitionBO e where e.isActive=1 and e.academicYear="+Integer.parseInt(academicYear);
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExamDefinitionBO> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) iterator.next();
					map.put(examDefinitionBO.getId(), examDefinitionBO.getName());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		return map;
	}


	@Override
	public Map<Integer, String> geDeanaryMap() throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<EmployeeStreamBO> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from EmployeeStreamBO e where e.isActive=1";
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<EmployeeStreamBO> iterator=list.iterator();
				while (iterator.hasNext()) {
					EmployeeStreamBO employeeStreamBO = (EmployeeStreamBO) iterator.next();
					map.put(employeeStreamBO.getId(), employeeStreamBO.getName());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		return map;
	}


	@Override
	public Map<Integer, String> getDepartmentMap() throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<Department> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from Department e where e.isActive=1";
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Department> iterator=list.iterator();
				while (iterator.hasNext()) {
					Department department = (Department) iterator.next();
					map.put(department.getId(), department.getName());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		return map;
	}


	@Override
	public List<Users> getInvigilatorsList(String query) throws Exception {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<Users> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	
	public List<ExamInviligatorExemptionDatewise> getInvListAlreadyExempted(String query) throws Exception {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<ExamInviligatorExemptionDatewise> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}

	

	@Override
	public boolean saveInvigilators(List<ExamInviligatorExemptionDatewise> invigilatorsForExamBos)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<ExamInviligatorExemptionDatewise> iterator=invigilatorsForExamBos.iterator();
			while (iterator.hasNext()) {
				ExamInviligatorExemptionDatewise invigilatorsForExamBo = (ExamInviligatorExemptionDatewise) iterator.next();
				session.save(invigilatorsForExamBo);
			}
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
}


	@Override
	public List<ExamInviligatorExemptionDatewise> getSearchedInvigilators(String query)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<ExamInviligatorExemptionDatewise> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}


	@Override
	public boolean updateInvigilators(List<Integer> list) throws Exception {
		List<ExamInviligatorExemptionDatewise> list1=new ArrayList<ExamInviligatorExemptionDatewise>();
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamInviligatorExemptionDatewise h where h.isActive=1  and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", list);
			list1=(List<ExamInviligatorExemptionDatewise>)query.list();
			tx = session.beginTransaction();
			tx.begin();
			if(list1!=null && !list1.isEmpty()){
				Iterator<ExamInviligatorExemptionDatewise> iterator=list1.iterator();
				while (iterator.hasNext()) {
					ExamInviligatorExemptionDatewise invigilatorsForExamBo = (ExamInviligatorExemptionDatewise) iterator.next();
					invigilatorsForExamBo.setIsActive(false);
					session.update(invigilatorsForExamBo);
				}
				tx.commit();
				session.flush();
				session.close();
				flag = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return flag;
	}
	
	public boolean deleteExemptedEntry(int exemptId,
			boolean activate, ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm)
			throws Exception {
	Session session=null;
	Transaction transaction=null;
	boolean isDeleted= false;
	try{
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		transaction.begin();
		ExamInviligatorExemptionDatewise invigilatorsForExamBo= (ExamInviligatorExemptionDatewise)session.get(ExamInviligatorExemptionDatewise.class, exemptId);
		if(activate){
			invigilatorsForExamBo.setIsActive(true);
		}else{
			invigilatorsForExamBo.setIsActive(false);
		}
		invigilatorsForExamBo.setLastModifiedDate(new Date());
		invigilatorsForExamBo.setModifiedBy(invigilatorsForExamForm.getUserId());
		session.update(invigilatorsForExamBo);
		transaction.commit();
		session.flush();
		 isDeleted = true;
	}catch (ConstraintViolationException e) {
		if(transaction!=null)
		     transaction.rollback();
		throw new BusinessException(e);
	} catch (Exception e) {
		if(transaction!=null)
		     transaction.rollback();
		throw new ApplicationException(e);
	}
	return isDeleted;
		
	}
	public List<Integer> getExamInvigilatorsAvailableWhoAreExemted()
	throws Exception {
		Session session = null;
		List<Integer> list=null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery("select e.teacherId.id from ExamInvigilatorDutyExemption e where e.isActive=1");
			list =query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}


	@Override
	public Map<Integer, String> getsessionMap() throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<ExaminationSessions> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExaminationSessions e where e.isActive=1";
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExaminationSessions> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExaminationSessions examinationSessions = (ExaminationSessions) iterator.next();
					map.put(examinationSessions.getId(), examinationSessions.getSession());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		return map;
	}
}
