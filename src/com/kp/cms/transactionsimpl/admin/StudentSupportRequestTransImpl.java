package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentSupportRequestBo;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.StudentSupportRequestForm;
import com.kp.cms.transactions.admin.IStudentSupportRequestTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class StudentSupportRequestTransImpl implements IStudentSupportRequestTransaction{
	public static volatile StudentSupportRequestTransImpl studentSupportRequestTransImpl = null;
	private StudentSupportRequestTransImpl(){
		
	}
	public static StudentSupportRequestTransImpl getInstance() {
		if (studentSupportRequestTransImpl == null) {
			studentSupportRequestTransImpl = new StudentSupportRequestTransImpl();
			return studentSupportRequestTransImpl;
		}
		return studentSupportRequestTransImpl;
	}
	public List<CategoryBo> getCategoryForStudent() throws Exception{
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from CategoryBo a where a.isActive=1 and (a.categoryFor='B' or a.categoryFor='S')");
			List<CategoryBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean saveSupportRequest(
			StudentSupportRequestBo studentSupportRequestBo, HttpServletRequest request) throws Exception {
			Session session = null;
			Transaction tx = null;
			boolean result = false;
			try {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				tx.begin();
				session.save(studentSupportRequestBo);
				tx.commit();
				request.setAttribute("id", studentSupportRequestBo.getId());
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
	public List<StudentSupportRequestBo> getStudentSupportRequest(
			String query1) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery(query1);
			List<StudentSupportRequestBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<StudentSupportRequestBo> getAdminSupportRequest(String userId)
			throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from StudentSupportRequestBo a where a.isActive=1 and a.userId="+userId+" order by a.dateOfSubmission");
			List<StudentSupportRequestBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<CategoryBo> getCategoryForAdmin() throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from CategoryBo a where a.isActive=1 and (a.categoryFor='B' or a.categoryFor='U')");
			List<CategoryBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<StudentSupportRequestBo> getPendingSupportReq(String userId)
			throws Exception {
		Session session = null;
		Object object=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("select u.employee.department.id from Users u where u.isActive=1 and u.id="+userId);
			object=query.uniqueResult();
			if(object==null){
				Query query2 = session.createQuery("select u.guest.department.id from Users u where u.isActive=1 and u.id="+userId);
				object=query2.uniqueResult();
			}
			if(object==null){
				Query query3 = session.createQuery("select u.department.id from Users u where u.isActive=1 and u.id="+userId);
				object=query3.uniqueResult();
			}
			Query query1 = session.createQuery("from StudentSupportRequestBo s where s.isActive=1 and (s.status='Pending' or s.status='In Progress')" +
					" and s.categoryId.departmentId="+Integer.valueOf(object.toString())+" order by s.dateOfSubmission");
			List<StudentSupportRequestBo> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) { 
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<CategoryBo> getSupportCategory() throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from CategoryBo a where a.isActive=1");
			List<CategoryBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean updateCategory(int id, String categoryId,String userId) throws Exception {
			Session session = null;
			Transaction tx = null;
			boolean flag=false;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				Query query=session.createQuery("from StudentSupportRequestBo s where s.isActive=1 and s.id="+id);
				StudentSupportRequestBo studentSupportRequestBo=(StudentSupportRequestBo)query.uniqueResult();
				CategoryBo categoryBo=new CategoryBo();
				categoryBo.setId(Integer.parseInt(categoryId));
				studentSupportRequestBo.setCategoryId(categoryBo);
				studentSupportRequestBo.setModifiedBy(userId);
				studentSupportRequestBo.setLastModifiedDate(new Date());
				tx.begin();
					session.update(studentSupportRequestBo);
				tx.commit();
				flag=true;
				//session.flush();
				return flag;
			} catch (ConstraintViolationException e) {
				tx.rollback();
				throw new BusinessException(e);
			} catch (Exception e) {
				tx.rollback();
				throw new ApplicationException(e);
			}
		}
	@Override
	public boolean updateStatusAndRemarks(int id, String status, String remarks,String userId)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean flag =false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Query query=session.createQuery("from StudentSupportRequestBo s where s.isActive=1 and s.id="+id);
			StudentSupportRequestBo studentSupportRequestBo=(StudentSupportRequestBo)query.uniqueResult();
			studentSupportRequestBo.setStatus(status);
			studentSupportRequestBo.setRemarks(remarks);
			studentSupportRequestBo.setModifiedBy(userId);
			studentSupportRequestBo.setLastModifiedDate(new Date());
			tx.begin();
				session.update(studentSupportRequestBo);
				flag=true;
			tx.commit();
			//session.flush();
			return flag;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
	@Override
	public StudentSupportRequestBo getStudentSupportRequestBoById(int id)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentSupportRequestBo s where s.isActive=1 and s.id="+id);
			StudentSupportRequestBo studentSupportRequestBo =(StudentSupportRequestBo)query.uniqueResult();
			session.flush();
			return studentSupportRequestBo;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<StudentSupportRequestBo> getSearchList(String query)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<StudentSupportRequestBo> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<StudentSupportRequestBo> getNoOfIssuesInOpen(java.sql.Date sqlDate) throws Exception {
		Session session = null;
		List<StudentSupportRequestBo> list=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentSupportRequestBo s where s.isActive=1" +
					" and s.status='Pending' and s.dateOfSubmission<='"+sqlDate+"' order by s.dateOfSubmission, s.categoryId.departmentId.name");
			list=query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public Object[] getAdminSupportRequestId(StringBuilder stringBuilder) throws Exception {
		Session session = null;
		Object[] objects=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(stringBuilder.toString());
			objects=(Object[])query.uniqueResult();
			session.flush();
			return objects;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public List<StudentSupportRequestBo> getPreviousSupportRequests(
			String query) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<StudentSupportRequestBo> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public Student checkRegisterNoAvailable(String regNo) throws Exception {
		Session session = null;
		Student student=null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery("from Student s where s.isActive=1 and s.registerNo='"+regNo+"'");
			student=(Student)query1.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	return student;
	}
}
