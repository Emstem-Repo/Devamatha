package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;
import com.kp.cms.bo.admin.TeacherDepartment;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.TeacherDepartmentEntryForm;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class TeacherDepartmentEntryTransactionImpl implements ITeacherDepartmentEntryTransaction{
	private static final Log log = LogFactory
	.getLog(TeacherDepartmentEntryTransactionImpl.class);
	@Override
	public boolean addTeacherDepartment(
			TeacherDepartment teacherDepartment)throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			session.save(teacherDepartment);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving teacher Department data...", e);
			throw new ApplicationException(e);
		}
		return true;
	}
	public List<Object[]> getTeacherDepartmentsName() throws Exception{
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
		String query="select u.id, " +
					 "		 e.department.name, " +
					 "		 e.firstName," +
					 "		 u.guest.firstName" +
					 " from Users u" +
					 " left join u.employee e with (e.active=1 and e.isActive=1)" +
					 " left join u.guest g with (g.active=1 and g.isActive=1)" +
					 " left join e.department d" +
					 " where  u.isActive=1" +
					 " and u.active=1" +
					 " and u.isTeachingStaff=1" +
					 " and u.userName is not null " ;
//		code added by sudhir	
		if(!CMSConstants.LINK_FOR_CJC){
			query = query + " and (u.employee.id is not null or u.guest.id is not null)";
		}
		
//		code ends here
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
	public List<Object[]> getTeacherDepartmentsNameFromUser(int userIdEmpl) throws Exception{
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id, u.department.name, u.userName from Users u left join u.department d where u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null and u.id="+userIdEmpl;
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
	public List<Object[]> getTeacherDepartmentsNameFromGuest(int userIdEmpl) throws Exception{
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id, g.department.name, u.userName from Users u left join u.guest g with (g.active=1 and g.isActive=1) left join g.department d where u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null and u.id="+userIdEmpl;
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
	@Override
	public List<TeacherDepartment> getTeacherDepartments() throws Exception{
		List<TeacherDepartment> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="from TeacherDepartment td ";
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
	@Override
	public boolean checkDuplicate(TeacherDepartmentEntryForm teacherDepartmentForm) throws Exception {
		Session session=null;
		boolean isDuplicate=false;
		try{
			session=HibernateUtil.getSession();
			if(teacherDepartmentForm.getDepartment()!=null && teacherDepartmentForm.getTeacher()!=null && !teacherDepartmentForm.getDepartment().isEmpty() && !teacherDepartmentForm.getTeacher().isEmpty()){
			String str="from TeacherDepartment td where td.departmentId="+teacherDepartmentForm.getDepartment()+" and td.teacherId="+teacherDepartmentForm.getTeacher();
			Query query=session.createQuery(str);
			List records=query.list();
			if(records!=null && !records.isEmpty()){
				isDuplicate=true;
			}
			}
		}catch(Exception e){
			log.error("Error during getting TeacherDepartments..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return isDuplicate;
	}
	@Override
	public TeacherDepartment getTeacherDepartmentById(int id) throws Exception {
		Session session=null;
		TeacherDepartment teacherDepartment=null;
		try{
			session=HibernateUtil.getSession();
			String str="from TeacherDepartment td where td.id="+id;
			Query query=session.createQuery(str);
			 teacherDepartment=(TeacherDepartment)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return teacherDepartment;
	}
	@Override
	public boolean updateTeacherDepartment(TeacherDepartment teacherDep)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			session.update(teacherDep);
			transaction.commit();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating teacher Department data...", e);
			throw new ApplicationException(e);
		}
		return true;
	}
	public boolean deleteTeacherDepartment(int id)
	throws Exception {
            Session session=null;
            Transaction transaction=null;
            try{
          	    session=InitSessionFactory.getInstance().openSession();
          	    String str="from TeacherDepartment td where td.id="+id;
          	    TeacherDepartment teacherDep=(TeacherDepartment)session.createQuery(str).uniqueResult();
          	    transaction=session.beginTransaction();
          	    session.delete(teacherDep);
          	    transaction.commit();
          	    session.close();
              }catch(Exception e){
          	    if (transaction != null)
          		   transaction.rollback();
          	log.debug("Error during deleting teacher Department data...", e);
          	throw new ApplicationException(e);
          }
return true;
}
	
	public List<Object[]> getSearchedTeacherDepartmentsName(String deptId) throws Exception{
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id, e.department.name, e.firstName from Users u join u.employee e with (e.active=1 and e.isActive=1) where u.employee.id = e.id and u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null and e.department.id = '"+deptId+"'";
//			code added  by sudhir 
			if(!CMSConstants.LINK_FOR_CJC){
				query=query+" and (u.employee.id is not null or u.guest.id is not null)";
			}
//			added code ends here
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

	public List<Object[]> getSearchedTeacherDepartmentsNameFrmUsers(String deptId) throws Exception{
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id, u.department.name, u.userName from Users u where u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null and u.department.id = '"+deptId+"'";
//			code added  by sudhir 
			if(!CMSConstants.LINK_FOR_CJC){
				query=query+" and (u.employee.id is not null or u.guest.id is not null)";
			}
//			added code ends here
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
	public List<Integer> getUsersId(int rid)throws Exception{
		List list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query quer=session.createQuery("select t.usersId.id from TeacherToGroup t where t.isActive=1 and t.rolesId.id="+rid);
			 list=quer.list();
			}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public List<Object[]> getTeacherDepartmentsNameNew(List<Integer> list) throws Exception{
		List<Object[]> teacherDepartmentList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
		String query="select u.id, e.department.name, e.firstName from Users u left join u.employee e with (e.active=1 and e.isActive=1) left join e.department d  where  u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null and u.id in (:list) " ;
				//		code added by sudhir	
		if(!CMSConstants.LINK_FOR_CJC){
			query=query+" and (u.employee.id is not null or u.guest.id is not null)";
		}
				//		code ends here		
		//query.setParameter(0,rid);	
		Query quer=session.createQuery(query);
		quer.setParameterList("list", list);
			teacherDepartmentList=quer.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return teacherDepartmentList;
	}
}
