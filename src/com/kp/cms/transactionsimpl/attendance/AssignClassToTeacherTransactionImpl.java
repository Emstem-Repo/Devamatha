
package com.kp.cms.transactionsimpl.attendance;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AssignClassToTeacherForm;
import com.kp.cms.transactions.attandance.IAssignClassToTeacherTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AssignClassToTeacherTransactionImpl implements IAssignClassToTeacherTransaction {
	private static final Log log = LogFactory
	.getLog(AssignClassToTeacherTransactionImpl.class);

	public static AssignClassToTeacherTransactionImpl assignClassToTeacherImpl =null;
	static{
		assignClassToTeacherImpl=new AssignClassToTeacherTransactionImpl();
	}
	public static AssignClassToTeacherTransactionImpl getInstance(){
		return assignClassToTeacherImpl;
	}
	
	/**
	 * @param year
	 * @return
	 */
	public Map<Integer, String> getClassesByYear(int year)throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear order by c.classes.name")
					.setInteger("academicYear", year);
			List<ClassSchemewise> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				classSchemewise = (ClassSchemewise) itr.next();
				classMap.put(classSchemewise.getId(), classSchemewise
						.getClasses().getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAssignClassToTeacherTransaction#addTeachers(com.kp.cms.forms.attendance.AssignClassToTeacherForm)
	 */
	public boolean addTeachers(AssignClassToTeacherForm assignClassTeacherForm) throws Exception {
		log.info("call of addTeachers method in AssignClassToTeacherTransactionImpl class.");
		Session session=null;
		org.hibernate.Transaction transaction=null;
		try{
			TeacherClass teacherClass=new TeacherClass();
			ClassSchemewise classes=new ClassSchemewise();
			Users users=new Users();
			classes.setId(Integer.parseInt(assignClassTeacherForm.getClassesSelected()));
			users.setId(Integer.parseInt(assignClassTeacherForm.getTeachers()));
			teacherClass.setTeacherId(users);
			teacherClass.setClassId(classes);
			teacherClass.setIsActive(true);
			teacherClass.setTeacherType(assignClassTeacherForm.getTeacherType());
			//teacherClass.setAcademicYear(assignClassTeacherForm.getAcademicYear());
			teacherClass.setCreatedBy(assignClassTeacherForm.getUserId());
			teacherClass.setModifiedBy(assignClassTeacherForm.getUserId());
			teacherClass.setCreatedDate(new Date());
			teacherClass.setLastModifiedDate(new Date());
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			session.save(teacherClass);
			transaction.commit();
			session.close();
			return true;
		}catch (ConstraintViolationException e) {
			transaction.rollback();
			log.debug("Error during saving teacher class data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving teacher class data...", e);
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAssignClassToTeacherTransaction#getDuplicates(java.lang.String)
	 */
	
	/* (non-Javadoc)
 * @see com.kp.cms.transactions.attandance.IAssignClassToTeacherTransaction#getDetails(com.kp.cms.forms.attendance.AssignClassToTeacherForm)
 */
public List<TeacherClass> getDetails(AssignClassToTeacherForm assignClassTeacherForm) throws Exception {
	log.debug("inside getDetails");
	Session session=null;
	List<TeacherClass> teacherClass;
		try{
		session=HibernateUtil.getSession();
		String sql="from TeacherClass teacherClass "+
					"where teacherClass.isActive=1 and teacherClass.classId in "+
					"(select c.id from ClassSchemewise c "+
					"inner join c.classes cl "+
                    "where c.curriculumSchemeDuration.academicYear=" +assignClassTeacherForm.getAcademicYear()+")";
		Query query=session.createQuery(sql);
		teacherClass= query.list();
		session.flush();
		log.debug("leaving getDetails");
		return teacherClass;
		}catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

public List<TeacherClass> getDetailsByYear(AssignClassToTeacherForm assignClassTeacherForm,int year) throws Exception {
	log.debug("inside getDetails");
	Session session=null;
	List<TeacherClass> teacherClass;
		try{
		session=HibernateUtil.getSession();
		String sql="from TeacherClass teacherClass "+
					"where teacherClass.isActive=1 and teacherClass.classId in "+
					"(select c.id from ClassSchemewise c "+
					"inner join c.classes cl "+
					"where c.curriculumSchemeDuration.academicYear=" +assignClassTeacherForm.getAcademicYear()+")";
		Query query=session.createQuery(sql);
		teacherClass= query.list();
		session.flush();
		log.debug("leaving getDetails");
		return teacherClass;
		}catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	
	}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.attandance.IAssignClassToTeacherTransaction#deleteTeachers(int)
 */
@Override
	public boolean deleteTeachers(int  id) throws Exception {
	log.debug("inside deleteTeachers");
	Session session=null;
	org.hibernate.Transaction transaction=null;
	
	try{
		
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		TeacherClass teacherClass=new TeacherClass();
		teacherClass.setId(id);
		session.delete(teacherClass);
		transaction.commit();
		session.flush();
		session.close();
		log.debug("leaving deleteTeachers");
		return true;
	}catch (Exception e) {
		log.error("Error in deleteTeachers...", e);
		session.flush();
		throw new ApplicationException(e);
	}
	}

@Override
public TeacherClass getClassEntrydetails(int id) throws Exception {
	Session session=null;
	TeacherClass teacherClass=null;
	try{
		session=HibernateUtil.getSession();
		String str="from TeacherClass tech where tech.isActive=1 and tech.id="+id;
		Query query=session.createQuery(str);
		teacherClass=(TeacherClass)query.uniqueResult();
	}catch (Exception e) {
		log.error("Error during getting TeacherClass by id..." , e);
		session.flush();
		session.close();
	}
	return teacherClass;
}

@Override
public boolean updateClassEntry(TeacherClass teacherClass) throws Exception {
	Session session=null;
	org.hibernate.Transaction transaction=null;
	
	try{
		
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		session.update(teacherClass);
		transaction.commit();
		session.flush();
		session.close();
		log.debug("leaving updateClassEntry");
		return true;
	}catch (Exception e) {
		log.error("Error in updateClassEntry...", e);
		session.flush();
		throw new ApplicationException(e);
	}
}

@Override
public boolean getDuplicates(String query,AssignClassToTeacherForm assignClassTeacherForm) throws Exception {
	
	log.debug("inside getDetails");
	Session session = null;
	boolean flag=false;
	try {
		session = HibernateUtil.getSession();
		TeacherClass teacherClass=(TeacherClass)session.createQuery(query).uniqueResult();
		if(teacherClass!=null){
		if(teacherClass.getId()==assignClassTeacherForm.getId()){
			flag=false;
		}else
			flag=true;
		}else
			flag=false;
		//List<TeacherClass> query1 = session.createQuery(query).list();
		/*if (query1 != null && query1.size()>=1){
			flag=true;
		}*/
		session.flush();
		log.debug("leaving getDetails");
		return flag;
	} catch (Exception e) {
		log.error("Error in getDetails...", e);
		session.flush();
		throw new ApplicationException(e);
	}
}

}
