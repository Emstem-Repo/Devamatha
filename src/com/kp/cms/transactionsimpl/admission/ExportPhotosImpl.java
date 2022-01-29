package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;

public class ExportPhotosImpl {

	public List getProgram(int year) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String HQL = "select p.id, p.name from Program p where p.isActive=1";
			Query query = session.createQuery(HQL);
			List list = query.list();
			if (list.size() != 0)
				return list;
			else {
				list = new ArrayList();
			}
			session.flush();
			return list;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public int getImages(int year, int programId, String admitOrall,int courseId,int schemeId) throws Exception {
		Session session = null;
		int size=0;
		try {
			session = HibernateUtil.getSession();
			StringBuilder stringBuilder=new StringBuilder();
			
			stringBuilder.append("select count(a) from ApplnDoc a"
					+ " join a.admAppln.students s where a.admAppln.courseBySelectedCourseId.program.id = :programId and "
					+ "a.admAppln.appliedYear = :year and a.isPhoto=1 and a.admAppln.course.id=:courseId and s.classSchemewise.id=:schemeId and  (a.document <> '' or a.document <> null)");
			if(admitOrall.equalsIgnoreCase("admitted")){
				stringBuilder.append(" and s.isAdmitted=1");
			}
			Query query = session.createQuery(stringBuilder.toString());
			query.setInteger("programId", programId);
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			query.setInteger("schemeId", schemeId);
			size=Integer.parseInt(query.uniqueResult().toString()); 
			session.flush();
			return size;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ApplnDoc> getImages(int year, int programId, int page,
			int pagesize, String admitOrall,int courseId,int schemeId) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("select a from ApplnDoc a"
					+ " join a.admAppln.students s where a.admAppln.courseBySelectedCourseId.program.id = :programId and "
					+ "a.admAppln.appliedYear = :year and a.isPhoto=1 and a.admAppln.course.id=:courseId and s.classSchemewise.id=:schemeId and  (a.document <> '' or a.document <> null)");
			if(admitOrall.equalsIgnoreCase("admitted")){
				stringBuilder.append(" and s.isAdmitted=1");
			}
			Query query = session.createQuery(stringBuilder.toString());
			query.setInteger("programId", programId);
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			query.setInteger("schemeId", schemeId);
			query.setFirstResult((page - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List<ApplnDoc> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public int getEmployeeImages() throws Exception {
		Session session = null;
		int size=0;
		try {
			session = HibernateUtil.getSession();
			
//			String HQL = "select count(e) from Employee e where e.empPhoto is not null";
			String HQL="select count(e) from EmpImages e where (e.empPhoto <> null or trim(e.empPhoto) <> '') and e.employee.isActive=1 and e.employee.active=1";
			Query query = session.createQuery(HQL);
			size=Integer.parseInt(query.uniqueResult().toString());
			session.flush();
			return size;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<EmpImages> getEmployeeImages(int page, int pagesize) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String HQL = "from EmpImages e where (e.empPhoto <> null or trim(e.empPhoto) <> '') and e.employee.isActive=1 and e.employee.active=1";
			Query query = session.createQuery(HQL);
			query.setFirstResult((page - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List<EmpImages> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
}
