package com.kp.cms.transactionsimpl.admin;

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

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.transactions.admin.ICourseTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CourseTransactionImpl implements ICourseTransaction {
	public static volatile CourseTransactionImpl courseTransactionImpl = null;
	private static final Log log = LogFactory.getLog(CourseTransactionImpl.class);

	public static CourseTransactionImpl getInstance() {
		if (courseTransactionImpl == null) {
			courseTransactionImpl = new CourseTransactionImpl();
			return courseTransactionImpl;
		}
		return courseTransactionImpl;
	}

	/**
	 * This method add new Course to Database.
	 * 
	 * @return true / false based on result.
	 * @throws Exception
	 */

	public boolean addCourse(Course course, String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if ("Add".equalsIgnoreCase(mode)) {
				session.save(course);
			} else {
				session.update(course);
			}
			transaction.commit();
			session.close();
			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	/*
	 * This method returns the subjectsgroups based on courseId
	 */
	public Map getSubjectGroupsByCourse(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * retrieving all/particular course from the table
	 */
	public List<Course> getCourse(int id) throws Exception {
		Session session = null;
		List<Course> result;
		try {
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();//sessionFactory.openSession();
			if (id != 0) {
				 Query query = session
				.createQuery("from Course where id = :courseId and isActive=1 and program.isActive=1 and program.programType.isActive = 1 order by program.programType.name asc, program.name, name" );
				query.setInteger("courseId", id);
				List<Course> list = query.list();
				session.flush();
//				session.close();
//				sessionFactory.close();
				result = list;
			} else {
				 Query query = session.createQuery("from Course where isActive=1 and program.isActive=1 and program.programType.isActive = 1 order by program.programType.name asc, program.name, name");
				List<Course> list = query.list();
				session.flush();
//				session.close();
//				sessionFactory.close();
				result = list;
			}
		} catch (Exception e) {
			log.error("Error during getting courses..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will delete a single Course from database.
	 * 
	 * @return true/false based on the result
	 * @throws Exception
	 */
	public boolean deleteCourse(int courseId, Boolean activate, CourseForm courseForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Course course = (Course) session.get(Course.class, courseId);
			if(activate){
				course.setIsActive(true);
			}
			else
			{
				course.setIsActive(false);
			}
			course.setModifiedBy(courseForm.getUserId());
			course.setLastModifiedDate(new Date());
			session.update(course);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			   transaction.rollback();
			log.error("Error during deleting course data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
		     	transaction.rollback();
			log.error("Error during deleting course data..." , e);
			throw new ApplicationException(e);
		}

	}
	/**
	 * checking for name duplication
	 */
	public boolean isCourseNameDuplcated(CourseForm courseForm) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from Course a where name = :courseName and program.id = :pgmId and program.programType.id = :progTypeId  and isActive=1 order by program.programType.name asc, name");
			query.setString("courseName", courseForm.getCourseName());
			query.setString("progTypeId", courseForm.getProgramTypeId());
			query.setString("pgmId", courseForm.getProgramId());
			List<Course> list = query.list();
			session.flush();
			session.close();
			sessionFactory.close();
			if (!list.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error during duplication checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return false;
	}

	/**
	 * checking for code duplication
	 */
	public boolean isCourseCodeDuplcated(String courseCode) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from Course a where code = :courseCode and isActive=1");
			query.setString("courseCode", courseCode);
			List<Course> list = query.list();
			session.flush();
			session.close();
			sessionFactory.close();
			if (!list.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error during duplication checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return false;
	}

	/**
	 * method for reactivate
	 */
	public Course isCourseToBeActivated(CourseForm courseForm) throws Exception {
		Session session = null;
		Course course;
		Course result = course = new Course(); 
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("from Course a where code = :courseCode and name = :coursemName and  program.id = :pgmId and program.programType.id = :progTypeId and isActive=0 order by program.programType.name asc, program.name, name");
			query.setString("courseCode", courseForm.getCourseCode());
			query.setString("coursemName", courseForm.getCourseName());
			query.setString("progTypeId", courseForm.getProgramTypeId());
			query.setString("pgmId", courseForm.getProgramId());

			course = (Course) query.uniqueResult();
				
			session.flush();
			session.close();
			sessionFactory.close();
			if (course!= null) {
				result = course;
			}
		} catch (Exception e) {
			log.error("Error in Re activate checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return result;
	}	
	
	/**
	 * Get all the active courses
	 */
	
	public List<Course> getActiveCourses()throws Exception
	{
		Session session = null;
		List<Course> courseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			courseBoList = session.createQuery("from Course course where course.isActive = 1 and course.program.isActive=1 order by program.programType.name asc, program.name, name").list();
			} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				session.close();
				}
			}
			return courseBoList;
	}
	
	public List<Course> getCourses()throws Exception
	{
		Session session = null;
		List<Course> courseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			courseBoList = session.createQuery("from Course course where course.isActive = 1 and course.program.isActive=1 and course.onlyForApplication=0 order by program.programType.name asc, program.name, name").list();
			} catch (Exception e) {
			log.error("Error in getCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				session.close();
				}
			}
			return courseBoList;
	}
	
	
	public HashMap<Integer, String> getCourseMap() throws Exception {
		Session session=null;
		HashMap<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Course course where course.isActive = 1 and course.program.isActive=1 and course.onlyForApplication=0 order by program.programType.name asc, program.name, name");
			List<Course> list=query.list();
			if(list!=null){
				Iterator<Course> iterator=list.iterator();
				while(iterator.hasNext()){
					Course course=iterator.next();
					if(course.getId() !=0 && course.getName()!=null && !course.getName().isEmpty())
					map.put(course.getId(),course.getName());
				}
			}
		}
		catch(RuntimeException runtime)
        {
            log.error("In getCourseMap", runtime);
            throw runtime;
        }catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	public List<Department> getDepartment() throws Exception {
        List<Department> boList=null;
        Session session = null;
        try
        {
            //session = HibernateUtil.getSession();
        	session = InitSessionFactory.getInstance().openSession();
            boList = session.createQuery("from Department d where d.isActive = 1 and d.isAcademic=1").list();
        }
        catch(Exception e)
        {
            log.error("Exception In AssignFees", e);
            throw e;
        }
        
        return boList;
    }
	public boolean saveDepts(List<CourseToDepartment> bolist) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bolist!=null){
				Iterator<CourseToDepartment> iterator=bolist.iterator();
				while(iterator.hasNext()){
					CourseToDepartment cd=(CourseToDepartment)iterator.next();
				session.saveOrUpdate(cd);
				result = true;
				}
			}
			transaction.commit();
			session.close();
			
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			      transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	
	public Map<Integer,Integer> getCourseDepartmentList(CourseForm courseForm) throws Exception {
        Session session = null;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
       // List<CertificateDetailsRoles> AssignList = new ArrayList<CertificateDetailsRoles>();
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("select cd.department.id,cd.id from CourseToDepartment cd where cd.isActive=1 and  cd.course.id="+courseForm.getCourseid());
           List<Object[]> list = query.list();
            if(list!=null && !list.isEmpty()){
            	Iterator<Object[]> iterator = list.iterator();
            	while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects[0].toString()), Integer.parseInt(objects[1].toString()));
					}
				}
            }
        }
        catch(RuntimeException runtime)
        {
            log.error("In AssignFees", runtime);
            throw runtime;
        }
        catch(Exception e)
        {
            log.error("In AssignFees", e);
            throw e;
        }
        
        return map;
    }
	
	
	
	
	
	//raghu added newly
	public List<Course> getCourses(int programTypeId)throws Exception
	{
		Session session = null;
		List<Course> courseBoList;
		try {
			session = InitSessionFactory.getInstance().openSession();
			courseBoList = session.createQuery("from Course course where course.isActive = 1 and course.program.isActive=1 and course.onlyForApplication=0 and program.programType.id="+programTypeId+" order by program.programType.name asc, program.name, name").list();
			} catch (Exception e) {
			log.error("Error in getCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
				}
			}
			return courseBoList;
	}

	public List<Course> getCoursesForAdmission(int programTypeId, boolean isAided) throws Exception {
		Session session = null;
		List<Course> courseBoList;
		try {
			session = HibernateUtil.getSession();
			StringBuilder hql = new StringBuilder("from Course course");
			hql.append(" where course.isActive = 1");
			hql.append(" and course.program.isActive=1");
			hql.append(" and course.isAppearInOnline=1");
			//hql.append(" and course.isAided=:isAided");
			hql.append(" and course.program.programType.id=:programTypeId");
			hql.append(" order by program.programType.name asc, program.name, name");
			Query query = session.createQuery(hql.toString())
								 .setInteger("programTypeId", programTypeId)
								 /*.setBoolean("isAided", isAided)*/;
			courseBoList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return courseBoList;
	}
	
}
