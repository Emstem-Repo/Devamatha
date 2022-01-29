package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.HonoursCourse;
import com.kp.cms.bo.admin.HonoursCourseApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.HonoursCourseEntryForm;
import com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HonoursCourseEntryTransactionImpl implements IHonoursCourseEntryTransaction{
	public static volatile HonoursCourseEntryTransactionImpl courseEntryTransactionImpl = null;
	public static HonoursCourseEntryTransactionImpl getInstance(){
		if(courseEntryTransactionImpl == null){
			courseEntryTransactionImpl = new HonoursCourseEntryTransactionImpl();
			return courseEntryTransactionImpl;
		}
		return courseEntryTransactionImpl;
	}
	@Override
	public Map<Integer, String> getCourseMapDetails() throws Exception {
		Session session = null;
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from Course course where course.isActive = 1 and course.onlyForApplication=0";
			Query query = session.createQuery(str);
			List<Course> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Course> iterator = list.iterator();
				while (iterator.hasNext()) {
					Course course = (Course) iterator.next();
					courseMap.put(course.getId(), course.getName());
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
			return courseMap;
		}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getHonoursCourseList()
	 */
	@Override
	public List<HonoursCourse> getHonoursCourseList() throws Exception {
		Session session = null;
		List<HonoursCourse> honoursCoursesList;
		try{
			session  = HibernateUtil.getSession();
			String str = "from HonoursCourse honoursCourse where honoursCourse.isActive = 1";
			Query query =  session.createQuery(str);
			honoursCoursesList = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		return honoursCoursesList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getDuplicateResult(com.kp.cms.forms.admin.HonoursCourseEntryForm)
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean getDuplicateResult( HonoursCourseEntryForm honoursCourseEntryForm) throws Exception {
		Session  session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
			String str= "from HonoursCourse honoursCourse where " +
						" honoursCourse.honoursCourse.id="+honoursCourseEntryForm.getHonoursCourseId()+
						" and honoursCourse.eligibleCourse.id="+honoursCourseEntryForm.getEligibleCourseId();
			Query query = session.createQuery(str);
			HonoursCourse honoursCourse = (HonoursCourse) query.uniqueResult();
			if(honoursCourse!=null && !honoursCourse.toString().isEmpty()){
				if(honoursCourse!=null && honoursCourse.getIsActive() == false){
					honoursCourseEntryForm.setDupId(honoursCourse.getId());
					throw new ReActivateException();
				}else if(honoursCourse.getId() == honoursCourseEntryForm.getId()){
					isDuplicate = false;
				}else{
					isDuplicate = true;
				}
			}
		}catch (ConstraintViolationException e) {
			throw new BusinessException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
		return isDuplicate;
	}
	@Override
	public boolean saveHonoursCourse(HonoursCourse honoursCourse,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(honoursCourse);
			}else if(mode.equalsIgnoreCase("Edit")){
				session.update(honoursCourse);
			}
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
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
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#editHonoursCourse(int)
	 */
	@Override
	public HonoursCourse editHonoursCourse(int id) throws Exception {
		Session session = null;
		HonoursCourse honoursCourse;
		try{
			session = HibernateUtil.getSession();
			String str = "from HonoursCourse course where course.isActive=1 and course.id="+id;
			Query query =session.createQuery(str);
			honoursCourse = (HonoursCourse) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return honoursCourse;
	}
	@Override
	public boolean deleteHonoursCourse(int id, boolean activate,
			HonoursCourseEntryForm honoursCourseEntryForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			HonoursCourse honoursCourse = (HonoursCourse)session.get(HonoursCourse.class, id);
			if(activate){
				honoursCourse.setIsActive(true);
			}else{
				honoursCourse.setIsActive(false);
			}
			honoursCourse.setModifiedBy(honoursCourseEntryForm.getUserId());
			honoursCourse.setLastModifiedDate(new Date());
			session.update(honoursCourse);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
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
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getHonoursCourseMap()
	 */
	@Override
	public Map<Integer, String> getHonoursCourseMap(String courseId) throws Exception {
		Session session = null;
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from HonoursCourse course where course.isActive=1 and course.eligibleCourse.id="+courseId;
			Query query = session.createQuery(str);
			List<HonoursCourse> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<HonoursCourse> iterator = list.iterator();
				while (iterator.hasNext()) {
					HonoursCourse course = iterator.next();
					if(course.getHonoursCourse() != null && course.getHonoursCourse().getId() != 0){
						courseMap.put(course.getHonoursCourse().getId(), course.getHonoursCourse().getName());
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
			return courseMap;
		}
	@Override
	public List<Object[]> getDataForQuery(String query) throws Exception {
		Session session = null;
		List<Object[]>  hallTicketList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			 hallTicketList=(List<Object[]>)session.createSQLQuery(query).list();
			return hallTicketList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getSubjectList()
	 */
	@Override
	public List<Integer> getSubjectList(String courseId) throws Exception {
		List<Integer> list = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			list = session.createQuery("select s.id from Subject s where s.eligibleCourse .id="+courseId).list();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getAttendancePercentage(int)
	 */
	@Override
	public List<Object[]> getAttendancePercentage(int studentId) throws Exception {
		Session session = null;
		List<Object[]>  attendanceList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			List<Integer> classIds = session.createQuery("select his.classes.id from StudentPreviousClassHistory his where his.student.id="+studentId).list();
			
			String sqlQuery = "select classes.term_number, classes.name, sum(hours_held) as held, " +
					" sum(if(attendance_student.is_cocurricular_leave =1 or attendance_student.is_present, " +
					" attendance.hours_held, 0)) as attended , " +
					" round((((sum(if(attendance_student.is_cocurricular_leave =1 or attendance_student.is_present, attendance.hours_held, 0)))/sum(hours_held)) * 100), 2) as percent " +
					" from attendance_student " +
					" inner join attendance on attendance_student.attendance_id = attendance.id " +
					" inner join student on attendance_student.student_id = student.id " +
					" inner join attendance_class on attendance_class.attendance_id = attendance.id " +
					" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id " +
					" inner join classes ON class_schemewise.class_id = classes.id " +
					" where student.id =  " +studentId +
					" and classes.id in (:ClassIds) " +
					" and attendance.is_canceled=0 " +
					" group by classes.id order by classes.term_number";
			attendanceList=(List<Object[]>)session.createSQLQuery(sqlQuery).setParameterList("ClassIds", classIds).list();
			return attendanceList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getStudentDetails(java.lang.String)
	 */
	@Override
	public Student getStudentDetails(String studentId) throws Exception {
		Student student = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			student = (Student) session.createQuery("from Student s where s.id="+studentId).uniqueResult();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return student;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#getCertificateMap(java.lang.String)
	 */
	@Override
	public Map<Integer, Boolean> getCertificateMap(String studentId)
			throws Exception {
		Session session = null;
		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		try{
			session = HibernateUtil.getSession();
			String certificateCourseQuery="select s.subject.id,s.isOptional from StudentCertificateCourse s where s.isCancelled=0 " +
			" and  s.student.id=" +studentId;
			List certificateList = session.createQuery(certificateCourseQuery).list();
			if(certificateList != null && !certificateList.isEmpty()){
				Iterator<Object[]> itr=certificateList.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null)
					map.put(Integer.parseInt(objects[0].toString()),(Boolean)objects[1]);
				}
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return map;
	}
	@Override
	public List<String> getSupplimentaryAppeared(int studentId)
			throws Exception {
		Session session = null;
		List<String> list = new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from StudentSupplementaryImprovementApplication s where (s.isAppearedTheory=1 or s.isAppearedPractical=1) and s.student.id ="+studentId);
			List<StudentSupplementaryImprovementApplication> selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<StudentSupplementaryImprovementApplication> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					StudentSupplementaryImprovementApplication bo =itr .next();
					if(bo.getIsAppearedTheory()!=null && bo.getIsAppearedTheory()){
						if(!list.contains(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_T")){
							list.add(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_T");
						}
					}
					if(bo.getIsAppearedPractical()!=null && bo.getIsAppearedPractical()){
						if(!list.contains(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_P")){
							list.add(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_P");
						}
					}
				}
			}
			return list;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#saveAppliedCourse(com.kp.cms.bo.admin.HonoursCourseApplication)
	 */
	@Override
	public void saveAppliedCourse(HonoursCourseApplication bo) throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			String query = "from HonoursCourseApplication a where a.student.id="+bo.getStudent().getId()
							+" and a.course.id="+bo.getCourse().getId();
			HonoursCourseApplication duplicateRecord = (HonoursCourseApplication)session.createQuery(query).uniqueResult(); 
			if(duplicateRecord != null){
				duplicateRecord.setLastModifiedDate(new Date());
				duplicateRecord.setModifiedBy(bo.getModifiedBy());
				session.update(duplicateRecord);
			}else{
				session.save(bo);
			}
			session.flush();
			tx.commit();
		}catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			if(session != null){
				session.close();
			}
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	@Override
	public List<HonoursCourseApplication> getAppliedStudentCourseDetails(String year, String courseId)
			throws Exception {
		Session session = null;
		List<HonoursCourseApplication> list = null;
		try{
			session = HibernateUtil.getSession();
			String query = "from HonoursCourseApplication h where h.isActive=1 and h.academicYear="+year;
			if(courseId != null && !courseId.trim().isEmpty()){
				query = query + " and h.course.id="+courseId;
			}
			list = session.createQuery(query).list();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction#saveDetals(java.util.List)
	 */
	@Override
	public String saveDetals(List<HonorsEntryBo> boList) throws Exception {
		Session session = null;
		Transaction tx = null;
		String errMsg ="";       
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(boList != null){
				Iterator<HonorsEntryBo> iterator = boList.iterator();
				String query = "";
				while (iterator.hasNext()) {
					HonorsEntryBo honorsEntryBo = (HonorsEntryBo) iterator.next();
					query = "from HonorsEntryBo h where h.student.id="+honorsEntryBo.getStudent().getId()
							+" and h.semister="+honorsEntryBo.getSemister();
					HonorsEntryBo bo = (HonorsEntryBo)session.createQuery(query).uniqueResult();
					if(bo == null){
						session.save(honorsEntryBo);
					}else{
						if(errMsg.isEmpty()){
							errMsg = "Course already entered for the student(s): "+ bo.getStudent().getRegisterNo()+", ";
						}else{
							errMsg = errMsg + bo.getStudent().getRegisterNo()+", ";
						}
					}
				}
				if(!errMsg.isEmpty()){
					errMsg = errMsg.substring(0, errMsg.length()-2);
				}
			}
			tx.commit();
			session.flush();
			return errMsg;
		}catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			if(session != null){
				session.close();
			}
		}finally{
			if (session != null) {
				session.flush();
			}
		}
		return errMsg;
	}
	@Override
	public Map<Integer, String> getHonoursCourseMap() throws Exception {
		Session session = null;
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from HonoursCourse course where course.isActive=1";
			Query query = session.createQuery(str);
			List<HonoursCourse> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<HonoursCourse> iterator = list.iterator();
				while (iterator.hasNext()) {
					HonoursCourse course = iterator.next();
					if(course.getHonoursCourse() != null && course.getHonoursCourse().getId() != 0){
						courseMap.put(course.getHonoursCourse().getId(), course.getHonoursCourse().getName());
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
			return courseMap;
		}
	@Override
	public Map<Integer, String> getHonoursApplicationCourseMap()
			throws Exception {
		Session session = null;
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from HonoursCourseApplication course where course.isActive=1";
			Query query = session.createQuery(str);
			List<HonoursCourseApplication> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<HonoursCourseApplication> iterator = list.iterator();
				while (iterator.hasNext()) {
					HonoursCourseApplication course = iterator.next();
					if(course.getCourse() != null && course.getCourse().getId() != 0){
						courseMap.put(course.getCourse().getId(), course.getCourse().getName());
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
			return courseMap;
		}
}

