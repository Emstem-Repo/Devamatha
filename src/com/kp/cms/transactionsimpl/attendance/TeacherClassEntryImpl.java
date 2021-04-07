package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.TeacherClassEntryForm;
import com.kp.cms.transactions.attandance.ITeacherClassEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class TeacherClassEntryImpl implements ITeacherClassEntryTransaction {
	private static final Log log = LogFactory
			.getLog(TeacherClassEntryImpl.class);
	public static volatile TeacherClassEntryImpl objImpl = null;

	public static TeacherClassEntryImpl getInstance() {
		if (objImpl == null) {
			objImpl = new TeacherClassEntryImpl();
			return objImpl;
		}
		return objImpl;
	}

	public List getDetails(int year, TeacherClassEntryForm teacherForm) throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String quer="select tcs.id, tcs.year, tcs.subject.name, tcs.classId.classes.name, "
							+ "tcs.teacherId.userName, tcs.teacherId.employee.middleName, "
							+ "tcs.teacherId.employee.lastName,tcs.numericCode,tcs.subject.code,tcs.teacherId.employee.department.name from TeacherClassSubject tcs  where "
							+ "tcs.year="
							+ year
							+ " and tcs.teacherId.isActive=1 "
							+ "and tcs.classId.classes.isActive=1 "
							+ "and tcs.subject.isActive=1 and tcs.isActive=1";
			if (Integer.parseInt(teacherForm.getRoleId())==131) {
				quer=quer+" and tcs.teacherId="+teacherForm.getUserId();
			}
			Query query=session.createQuery(quer);
			List list = query.list();
			session.flush();
			log.debug("leaving getDetails");
			return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}

	}

	public List getUserDetails(int year, TeacherClassEntryForm teacherForm) throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String quer = "select tcs.id, tcs.year, tcs.subject.name, tcs.classId.classes.name,tcs.teacherId.userName, "
					+" tcs.numericCode,tcs.subject.code,tcs.teacherId.department.name from TeacherClassSubject tcs "
					+" where tcs.year="+ year
					+" and tcs.teacherId.isActive=1 "
					+" and tcs.classId.classes.isActive=1 "
					+" and tcs.subject.isActive=1 and tcs.isActive=1";
					if (Integer.parseInt(teacherForm.getRoleId())==131) {
						quer=quer+" and tcs.teacherId="+teacherForm.getUserId();
					}
					Query query=session.createQuery(quer);
			List list = query.list();
			session.flush();
			log.debug("leaving getDetails");
			return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}

	}
	
	public List getGuestDetails(int year, TeacherClassEntryForm teacherForm) throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String quer = "select tcs.id, tcs.year, tcs.subject.name, tcs.classId.classes.name,tcs.teacherId.userName, "
					+" tcs.numericCode,tcs.subject.code,tcs.teacherId.guest.department.name from TeacherClassSubject tcs "
					+" where tcs.year="+ year
					+" and tcs.teacherId.isActive=1 "
					+" and tcs.classId.classes.isActive=1 "
					+" and tcs.subject.isActive=1 and tcs.isActive=1";
			if (Integer.parseInt(teacherForm.getRoleId())==131) {
				quer=quer+" and tcs.teacherId="+teacherForm.getUserId();
			}
			Query query=session.createQuery(quer);
			
			List list = query.list();
			session.flush();
			log.debug("leaving getDetails");
			return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}

	}

	
	public List getTeacherClassDetails(int id) throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select tcs.year, tcs.teacherId.id,  tcs.subject.id, tcs.classId.id,tcs.numericCode from TeacherClassSubject tcs  where tcs.isActive=1 and "
							+ "tcs.id=" + id);
			List list = query.list();
			session.flush();
			log.debug("leaving getDetails");
			return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	public boolean deleteTeacherClassEntry(int id,Boolean activate,TeacherClassEntryForm teacherClassEntryForm) throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		Transaction transaction=null;
		boolean flag = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			 transaction= session.beginTransaction();
//			TeacherClassSubject query = (TeacherClassSubject)session
//					.createQuery(" from TeacherClassSubject tcs  where tcs.id ="+id).uniqueResult();
//			if(query!=null){
////				query.setIsActive(false);
//				session.delete(query);
//				flag=true;
//			}
			TeacherClassSubject teacherClassSubject=(TeacherClassSubject)session.get(TeacherClassSubject.class, id);
			if(activate){
				teacherClassSubject.setIsActive(true);
			}else{
				teacherClassSubject.setIsActive(false);
			}
			teacherClassSubject.setLastModifiedDate(new Date());
			teacherClassSubject.setModifiedBy(teacherClassEntryForm.getUserId());
			session.update(teacherClassSubject);
			transaction.commit();
			session.flush();
			session.close();
			flag = true;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return flag;
	}

	public boolean update(TeacherClassSubject tcs) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(tcs);
			transaction.commit();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.debug("Error during saving teacher class data...", e);
			throw new DuplicateException("duplicateEntry");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving teacher class data...", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<TeacherClassSubject> getDuplicateList(String query1)
			throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(query1);
			List<TeacherClassSubject> list = query.list();
			session.flush();
			log.debug("leaving getDetails");
			return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	public boolean getDuplicates(TeacherClassEntryForm teacherForm)
		throws Exception {
		log.debug("inside getDetails");
		Session session = null;
		boolean flag=false;
		try {
			session = HibernateUtil.getSession();
			String selectedClasses[] = teacherForm.getSelectedClasses();
			List<Integer> classesIdsList = new ArrayList<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				if(isNumeric(selectedClasses[i])){
				 classesIdsList.add(Integer.parseInt(selectedClasses[i]));
				}
			}
			Query query = session.createQuery("from TeacherClassSubject tcs where tcs.isActive=1 and tcs.teacherId.isActive=1  and tcs.subject='"
			           												+teacherForm.getSubjectId()+"' and tcs.teacherId='"+teacherForm.getTeachers()+
			           												"' and tcs.classId.id in (:classIds)");
			query.setParameterList("classIds", classesIdsList);
			List<TeacherClassSubject> boList=query.list();
			if (boList != null && boList.size()>=1){
				flag=true;
			}
			session.flush();
			log.debug("leaving getDetails");
			return flag;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Object[]> getTeacherDepartmentNames()throws Exception {
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id, e.department.name, u.userName from Users u left join u.employee e  with (e.active=1 and e.isActive=1) left join e.department d  where  u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null";
			//String query="select u.id, e.department.name, u.userName from Users u join u.employee e where u.employee.id = e.id and u.isActive=1 and u.isTeachingStaff=1 and u.userName is not null";
			Query quer=session.createQuery(query);
			teacherDepartmentList=quer.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return teacherDepartmentList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.ITeacherClassEntryTransaction#getReactivate(com.kp.cms.forms.attendance.TeacherClassEntryForm)
	 */
	@Override
	public TeacherClassSubject getReactivate(TeacherClassEntryForm teacherForm)
			throws Exception {
		Session session= null;
		TeacherClassSubject teacherClassSubject=null;
		try{
			session=HibernateUtil.getSession();
			String query1="from TeacherClassSubject tcs where tcs.isActive=0 and tcs.teacherId.isActive=1  and tcs.subject='"
		           +teacherForm.getSubjectId()+"' and tcs.teacherId='"+teacherForm.getTeachers()+"' and tcs.classId.id="+teacherForm.getSelectedClasses();
			Query query=session.createQuery(query1);
			teacherClassSubject=(TeacherClassSubject) query.uniqueResult();
			session.flush();
			session.close();
		}catch (Exception exception) {
			log
			.error("Error during  reactivate checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
		return teacherClassSubject;
	}
	
	public  boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
