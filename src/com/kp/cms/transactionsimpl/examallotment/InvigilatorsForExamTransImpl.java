package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
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
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.examallotment.IInvigilatorsForExamTrans;
import com.kp.cms.utilities.HibernateUtil;

public class InvigilatorsForExamTransImpl implements IInvigilatorsForExamTrans{
private static volatile InvigilatorsForExamTransImpl invigilatorsForExamTransImpl=null;
	 
	
	/**
	 * instance()
	 * @return
	 */
private InvigilatorsForExamTransImpl(){
	
}
	public static InvigilatorsForExamTransImpl getInstance(){
		if(invigilatorsForExamTransImpl == null){
			invigilatorsForExamTransImpl=new InvigilatorsForExamTransImpl();
		}
		return invigilatorsForExamTransImpl;
	}


	@Override
	public Map<Integer, String> getExam(String academicYear) throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<ExamDefinition> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamDefinition e where e.isActive=1 and e.academicYear="+Integer.parseInt(academicYear);
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExamDefinition> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamDefinition examDefinition = (ExamDefinition) iterator.next();
					map.put(examDefinition.getId(), examDefinition.getName());
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
	public List<Users> getInvigilatorsList(StringBuilder query) throws Exception {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query.toString());
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


	@Override
	public boolean saveInvigilators(
			List<ExamInvigilatorAvailable> examInvigilatorAvailables)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<ExamInvigilatorAvailable> iterator=examInvigilatorAvailables.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
				session.save(examInvigilatorAvailable);
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
	public List<ExamInvigilatorAvailable> getSearchedInvigilators(StringBuilder query)
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
				// session.close();
			}
			throw new ApplicationException(e);
		}
}


	@Override
	public boolean updateInvigilators(List<ExamInvigilatorAvailable> list) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				Iterator<ExamInvigilatorAvailable> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
					session.update(examInvigilatorAvailable);
				}
				tx.commit();
				session.flush();
				session.close();
				flag = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return flag;
	}


	@Override
	public List<ExamInvigilatorAvailable> getExamInvigilators(
			List<Integer> therecordsExists) throws Exception {
		List<ExamInvigilatorAvailable> list1=new ArrayList<ExamInvigilatorAvailable>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamInvigilatorAvailable h where h.isActive=1  and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", therecordsExists);
			list1=(List<ExamInvigilatorAvailable>)query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list1;
	}


	@Override
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
	
	public Map<Integer, String> getExamSession() throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<ExaminationSessions> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = ("select e from ExaminationSessions e where e.isActive=1 ");
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExaminationSessions> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExaminationSessions examDefinition = (ExaminationSessions) iterator.next();
					map.put(examDefinition.getId(), examDefinition.getSession()+"("+examDefinition.getSessionDescription()+")");
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
