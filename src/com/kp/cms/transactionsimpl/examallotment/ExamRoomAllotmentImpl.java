package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForm;
import com.kp.cms.transactions.examallotment.IExamRoomAllotment;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRoomAllotmentImpl implements IExamRoomAllotment {

	private static volatile ExamRoomAllotmentImpl impl = null;
	private ExamRoomAllotmentImpl(){
		
	}
	public static IExamRoomAllotment getInstance() {
		if(impl == null){
			impl = new ExamRoomAllotmentImpl();
		}
		return impl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getCourses(java.lang.String)
	 */
	@Override
	public List<Object[]> getCourses(String cycleId,ExamRoomAllotmentForm allotmentForm) throws Exception {
		List<Object[]> courses = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			
			/*String hql1 = "select course.id from EXAM_room_allotment " +
						  " JOIN EXAM_room_allotment_details ON EXAM_room_allotment_details.exam_room_allotment_id = EXAM_room_allotment.id " +
						  " JOIN classes ON EXAM_room_allotment_details.class_id = classes.id " +
						  " JOIN course ON classes.course_id = course.id " +
						  " where EXAM_room_allotment.exam_room_allotment_cycle_id=1 AND EXAM_room_allotment.is_active=1 " +
						  " AND EXAM_room_allotment_details.is_active=1 group by course.id";
			List<Integer> courseIds = session.createSQLQuery(hql1).list();*/
			
			String hql = "select course.id as courseId,concat(course.name,' (',course.code,')') as code,EXAM_room_allotment_settings_poolwise.exam_room_allotment_pool_id as poolId," +
						 " EXAM_room_allotment_settings_poolwise.scheme_no as schemeNo,EXAM_room_allotment_cycle.mid_end,EXAM_room_allotment_cycle.session " +
						 " ,EXAM_sessions.id as examSessionId" +
						 " from EXAM_room_allotment_settings_poolwise " +
						 " INNER JOIN course ON EXAM_room_allotment_settings_poolwise.course_id = course.id " +
						 " INNER JOIN EXAM_room_allotment_cycle_details on EXAM_room_allotment_cycle_details.course_id = course.id " +
						 " AND EXAM_room_allotment_settings_poolwise.scheme_no=EXAM_room_allotment_cycle_details.scheme_no " +
						 " INNER JOIN EXAM_room_allotment_cycle ON EXAM_room_allotment_cycle_details.exam_room_cycle_id = EXAM_room_allotment_cycle.id " +
						 " AND EXAM_room_allotment_settings_poolwise.mid_end=EXAM_room_allotment_cycle.mid_end " +
						 " LEFT JOIN EXAM_sessions ON EXAM_room_allotment_cycle.exam_session_id = EXAM_sessions.id " +
						 " where EXAM_room_allotment_cycle.id= " +cycleId+
						 " AND EXAM_room_allotment_settings_poolwise.is_active=1 " +
						 " AND EXAM_room_allotment_cycle_details.is_active=1 AND course.work_location_id="+allotmentForm.getCampus(); 
			courses = session.createSQLQuery(hql).list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return courses;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getStudentsForCourse(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm)
	 */
	@Override
	public List<Student> getStudentsForCourse(ExamRoomAllotmentForm allotmentForm, Map<String, List<Integer>> semWiseCourses) throws Exception {
		List<Student> students = new ArrayList<Student>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			for (Entry<String, List<Integer>> courses : semWiseCourses.entrySet()) {
				String hql = "select s from Student s where s.isAdmitted=1 and s.isActive=1 and (s.isHide=0 or s.isHide is null) " +
				" and s.admAppln.isCancelled=0  and  s.classSchemewise.classes.course.id in(:totalCourses) " +
				" and s.classSchemewise.classes.termNumber= " +courses.getKey()+
				" and s.classSchemewise.curriculumSchemeDuration.academicYear=" +allotmentForm.getYear()+
				" order by s.registerNo";
				List<Student> tempstudents = session.createQuery(hql).setParameterList("totalCourses", courses.getValue()).list();
				if(tempstudents != null)
					students.addAll(tempstudents);
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return students;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getMostNoOfExamCourses()
	 */
	@Override
	public List<Object[]> getMostNoOfExamCourses(ExamRoomAllotmentForm allotmentForm,Map<String, List<Integer>> semWiseCourses) throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hql = "";
			for (Entry<String, List<Integer>> map : semWiseCourses.entrySet()) {
				if(allotmentForm.getProgramType() != null && allotmentForm.getProgramType().equalsIgnoreCase("Yes")){
					hql = "select EXAM_exam_course_scheme_details.course_id as cId,scheme_no,count(distinct date_starttime) as nos,program_type.id as pId  from EXAM_exam_course_scheme_details " +
					" INNER JOIN EXAM_time_table ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
					" INNER JOIN course ON course.id=EXAM_exam_course_scheme_details.course_id " +
					" INNER JOIN program ON program.id=course.program_id " +
					" INNER JOIN program_type ON program_type.id=program.program_type_id " +
					" INNER JOIN EXAM_sessions ON EXAM_time_table.exam_session_id=EXAM_sessions.id" +
					" where EXAM_exam_course_scheme_details.exam_id= " +allotmentForm.getExamId()+
					" and EXAM_exam_course_scheme_details.course_id in(:CourseIds)";
					if(allotmentForm.getExamSessionId() != null){
						hql = hql + " AND EXAM_sessions.id="+allotmentForm.getExamSessionId();
					}
					hql = hql + " and scheme_no=" +map.getKey()+
					" group by course_id,scheme_no order by nos desc,program_type.id";
				}else{
					hql = "select course_id,scheme_no,count(distinct date_starttime) as nos from EXAM_exam_course_scheme_details " +
					" INNER JOIN EXAM_time_table ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
					" INNER JOIN EXAM_sessions ON EXAM_time_table.exam_session_id=EXAM_sessions.id" +
					" where EXAM_exam_course_scheme_details.exam_id= " +allotmentForm.getExamId()+
					" and EXAM_exam_course_scheme_details.course_id in(:CourseIds) ";
					if(allotmentForm.getExamSessionId() != null){
						hql = hql + " AND EXAM_sessions.id="+allotmentForm.getExamSessionId();
					}
					hql = hql + " and scheme_no=" +map.getKey()+
					" group by course_id,scheme_no order by nos desc";
				}
				List<Object[]> templist = session.createSQLQuery(hql).setParameterList("CourseIds", map.getValue()).list();
				if(templist != null){
					list.addAll(templist);
				}
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getAvailableRooms()
	 */
	@Override
	public List<RoomMaster> getAvailableRooms(ExamRoomAllotmentForm allotmentForm) throws Exception {
		Session session = null;
		List<RoomMaster> rooms = new ArrayList<RoomMaster>();
		try{
			session = HibernateUtil.getSession();
			String hqlquery = "select e.roomMaster from ExamRoomAvailability e where e.roomMaster.blockId.locationId.id= " +allotmentForm.getCampus()+
							" and e.isActive=1 and e.roomMaster.isActive=1 " +
							" and e.roomMaster.id not in (select a.room.id from ExamRoomAllotment a where a.cycle.id="+allotmentForm.getCycleId()+" and a.isActive=1 and a.examDefinition.id="+allotmentForm.getExamId()+")" +
							" order by e.roomMaster.blockId.blockOrder,e.roomMaster.floor,e.roomMaster.roomNo";
//			String hqlquery = "select e.room from ExamRoomsAvailable e where  e.room.id in (3,4,5,6,11,12)";
			Query query = session.createQuery(hqlquery);
			rooms = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return rooms;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getExamRoomCycles()
	 */
	@Override
	public List<ExamRoomAllotmentCycle> getExamRoomCycles() throws Exception {
		Session session = null;
		List<ExamRoomAllotmentCycle> rooms = new ArrayList<ExamRoomAllotmentCycle>();
		try{
			session = HibernateUtil.getSession();
			String hqlquery = "from ExamRoomAllotmentCycle e where  e.isActive=1";
			Query query = session.createQuery(hqlquery);
			rooms = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return rooms;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#saveAllotment(java.util.List)
	 */
	@Override
	public void saveAllotment(List<ExamRoomAllotment> allotmentList,ExamRoomAllotmentForm allotmentForm)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			if(allotmentList != null){
				tx = session.beginTransaction();
				for (ExamRoomAllotment examRoomAllotment : allotmentList) {
					session.save(examRoomAllotment);
				}
				tx.commit();
			}
		}catch (Exception e) {
			if(tx != null)
				tx.rollback();
			if (session != null) {
				session.flush();
				session.close();
			}
		}finally{
			session.flush();
			session.close();
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getCoursesFromGroups(java.lang.String, com.kp.cms.forms.examallotment.ExamRoomAllotmentForm)
	 */
	@Override
	public List<Object[]> getCoursesFromGroups(String cycleId,
			ExamRoomAllotmentForm allotmentForm) throws Exception {
		List<Object[]> courses = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hql = "select course.id as courseId,concat(course.name,' (',course.code,')') as code,EXAM_room_allotment_groupswise.scheme_no," +
					" EXAM_room_allotment_cycle.mid_end,EXAM_room_allotment_cycle.session" +
					" from EXAM_room_allotment_groupswise " +
					" JOIN course ON EXAM_room_allotment_groupswise.course_id = course.id " +
					" JOIN EXAM_room_allotment_cycle_details ON EXAM_room_allotment_cycle_details.course_id = course.id " +
					" AND EXAM_room_allotment_cycle_details.scheme_no=EXAM_room_allotment_groupswise.scheme_no " +
					" JOIN EXAM_room_allotment_cycle ON EXAM_room_allotment_cycle_details.exam_room_cycle_id = EXAM_room_allotment_cycle.id " +
					" AND EXAM_room_allotment_groupswise.mid_end=EXAM_room_allotment_cycle.mid_end " +
					" WHERE EXAM_room_allotment_cycle.id=" +cycleId+
					" AND course.work_location_id=" +allotmentForm.getCampus()+
					" AND EXAM_room_allotment_groupswise.is_active=1 AND EXAM_room_allotment_cycle_details.is_active=1"; 
			courses = session.createSQLQuery(hql).list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return courses;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#removeRoomAllotment(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm)
	 */
	@Override
	public void removeRoomAllotment(ExamRoomAllotmentForm allotmentForm)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			String query = "from ExamRoomAllotment a where a.examDefinition.id="+allotmentForm.getExamId()+
			" and a.isActive=1 and a.room.blockId.locationId.id=" +allotmentForm.getCampus()+
			" and a.midOrEndSem='"+allotmentForm.getMidOrEndSem()+"'"+
			" and a.cycle.id="+allotmentForm.getCycleId();
			List<ExamRoomAllotment> boList = session.createQuery(query).list();
			if(boList != null && !boList.isEmpty()){
				tx = session.beginTransaction();
				for (ExamRoomAllotment examRoomAllotment : boList) {
					if(examRoomAllotment.getRoom().getBlockId().getLocationId().getId() == Integer.parseInt(allotmentForm.getCampus())){
						examRoomAllotment.setIsActive(false);
						examRoomAllotment.setModifiedBy(allotmentForm.getUserId());
						examRoomAllotment.setLastModifiedDate(new Date());
						if(examRoomAllotment.getRoomAllotmentDetails() != null){
							Set<ExamRoomAllotmentDetails> set = new HashSet<ExamRoomAllotmentDetails>();
							for (ExamRoomAllotmentDetails details : examRoomAllotment.getRoomAllotmentDetails()) {
								details.setIsActive(false);
								details.setLastModifiedDate(new Date());
								details.setModifiedBy(allotmentForm.getUserId());
								set.add(details);
							}
							examRoomAllotment.setRoomAllotmentDetails(set);
						}
						session.update(examRoomAllotment);
					}
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

	@Override
	public int getNoOfStudentAllotInSameRoom() throws Exception {
		Session session = null;
		int studentCount = 0;
		try{
			session = HibernateUtil.getSession();
			String hqlquery = "select studentAllotmentCount from Organisation e where  e.isActive=1";
			Query query = session.createQuery(hqlquery);
			studentCount = (Integer) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return studentCount;
	}

	@Override
	public List<StudentClassGroup> getStudentsForGroupCourse(ExamRoomAllotmentForm allotmentForm,	Map<String, List<Integer>> semWiseCourses) throws Exception {
		List<StudentClassGroup> students = new ArrayList<StudentClassGroup>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			for (Entry<String, List<Integer>> courses : semWiseCourses.entrySet()) {
				String hql = "select g from StudentClassGroup g where g.isActive=1 and g.groupClasses.isActive=1 and g.student.isAdmitted=1 and g.student.isActive=1 and (g.student.isHide=0 or g.student.isHide is null) " +
				" and g.student.admAppln.isCancelled=0  and  g.student.classSchemewise.classes.course.id in(:totalCourses) " +
				" and g.student.classSchemewise.classes.termNumber= " +courses.getKey()+
				" and g.student.classSchemewise.curriculumSchemeDuration.academicYear=" +allotmentForm.getYear()+
				" order by g.student.registerNo";
				List<StudentClassGroup> tempstudents = session.createQuery(hql).setParameterList("totalCourses", courses.getValue()).list();
				if(tempstudents != null && !tempstudents.isEmpty())
					students.addAll(tempstudents);
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return students;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getCoursesForSpecialization(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Object[]> getCoursesForSpecialization(String cycleId, String campus) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		try{
			session = HibernateUtil.getSession();
			String sqlQuery= "select course.id as courseId,concat(course.name,course.code) as code, " +
					"spe.id as speId,spe.scheme_no," +
					"EXAM_room_allotment_cycle.mid_end," +
					"EXAM_room_allotment_cycle.session " +
					"from EXAM_room_allotment_settings_specializationwise spe " +
					"JOIN course on spe.course_id= course.id " +
					"JOIN EXAM_room_allotment_cycle_details details on course.id = details.course_id " +
					"AND spe.scheme_no = details.scheme_no " +
					"JOIN EXAM_room_allotment_cycle on EXAM_room_allotment_cycle.id = details.exam_room_cycle_id " +
					"AND spe.mid_end = EXAM_room_allotment_cycle.mid_end where EXAM_room_allotment_cycle.id ="+cycleId+
					" AND spe.is_active=1 AND details.is_active=1" +
					" AND course.work_location_id="+campus;
			list = session.createSQLQuery(sqlQuery).list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getSpecializationForStudentsByCourses(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm, java.util.Map)
	 */
	@Override
	public Map<Integer, String> getSpecializationForStudentsByCourses( ExamRoomAllotmentForm allotmentForm,
			Map<String, List<Integer>> semWiseCourses) throws Exception {
		Session session = null;
		List<Object[]> specializationList = new ArrayList<Object[]>() ;
		Map<Integer,String> specializationMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			for (Entry<String, List<Integer>> courses : semWiseCourses.entrySet()) {
				String sqlQuery = "select student.id as studentId,EXAM_specialization.name as name" +
						" from EXAM_student_bio_data bio " +
						" join EXAM_specialization on bio.specialization_id = EXAM_specialization.id " +
						" join student on bio.student_id = student.id " +
						" join class_schemewise on student.class_schemewise_id = class_schemewise.id " +
						" join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
						" join classes on class_schemewise.class_id= classes.id " +
						" join course on classes.course_id = course.id " +
						" where course.id in (:totalCourses)" +
						" and classes.term_number ="+courses.getKey()+
						" and curriculum_scheme_duration.academic_year= "+allotmentForm.getYear()+
						" and student.is_active=1";
				Query query = session.createSQLQuery(sqlQuery);
				String listString = courses.getValue().toString();
				String c = listString.substring(1,listString.length()-1);
				query.setParameter("totalCourses", c);
				List<Object[]> list = query.list();
				if(list!=null && !list.isEmpty()){
					specializationList.addAll(list);
				}
			}
			if(specializationList!=null && !specializationList.isEmpty()){
				Iterator<Object[]> objectList = specializationList.iterator();
				while (objectList.hasNext()) {
					Object[] obj = (Object[]) objectList.next();
					if(obj[0]!=null && obj[1]!=null){
						specializationMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
					}
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return specializationMap;
	}
	@Override
	public List<ExaminationSessions> getSessionsList() throws Exception {
		Session session = null;
		List<ExaminationSessions> list = new ArrayList<ExaminationSessions>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExaminationSessions sessions where sessions.isActive=1";
			Query query = session.createQuery(hqlQuery);
			list = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getcourseWiseClassesDetails(java.util.List)
	 */
	@Override
	public Map<Integer, List<String>> getcourseWiseClassesDetails(List<Integer> classesList) throws Exception {
		Session session = null;
		Map<Integer,List<String>> courseWiseClassesMap = new HashMap<Integer, List<String>>();
		try{
				session = HibernateUtil.getSession();
				String hqlQuery = "from Classes classes where classes.isActive=1 and classes.id in (:classesIds) order by classes.course.id,classes.id";
				List<Classes> classesBoList = session.createQuery(hqlQuery).setParameterList("classesIds", classesList).list();
				if(classesBoList!=null && !classesBoList.isEmpty()){
					List<String> clsList = null;
					for(Classes cls : classesBoList){
						if(courseWiseClassesMap.containsKey(cls.getCourse().getId())){
							clsList = courseWiseClassesMap.get(cls.getCourse().getId());
							clsList.add(cls.getCourse().getId()+"_"+cls.getId());
						}else{
							clsList = new ArrayList<String>();
							clsList.add(cls.getCourse().getId()+"_"+cls.getId());
						}
						courseWiseClassesMap.put(cls.getCourse().getId(), clsList);
					}
				}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return courseWiseClassesMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getStudentsForSelectedClasses(java.util.List)
	 */
	@Override
	public List<Student> getStudentsForSelectedClasses(List<Integer> classesList) throws Exception {
		Session session = null;
		List<Student> stuList = new ArrayList<Student>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select s from Student s where s.isAdmitted=1 and s.isActive=1 and (s.isHide=0 or s.isHide is null) " +
			" and s.admAppln.isCancelled=0  and  s.classSchemewise.classes.id in(:classesList) " +
			" order by s.classSchemewise.classes.course.id,s.classSchemewise.classes.id,s.registerNo";
			stuList = session.createQuery(hqlQuery).setParameterList("classesList", classesList).list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return stuList;
	}
	@Override
	public void removeRoomAllotmentForDateAndSession( ExamRoomAllotmentForm allotmentForm,List<Integer> roomListIds) throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			String query = "from ExamRoomAllotment a where a.examDefinition.id="+Integer.parseInt(allotmentForm.getExamId())+
			" and a.isActive=1 and a.room.blockId.locationId.id=" +allotmentForm.getCampus()+
			" and a.date='"+CommonUtil.ConvertStringToSQLDate(allotmentForm.getDate())+"'"+
			" and a.examinationSessions.id="+Integer.parseInt(allotmentForm.getSession())+
			" and a.room.id in (:roomsList)";
			List<ExamRoomAllotment> boList = session.createQuery(query).setParameterList("roomsList", roomListIds).list();
			if(boList != null && !boList.isEmpty()){
				tx = session.beginTransaction();
				for (ExamRoomAllotment examRoomAllotment : boList) {
					if(examRoomAllotment.getRoom().getBlockId().getLocationId().getId() == Integer.parseInt(allotmentForm.getCampus())){
						examRoomAllotment.setIsActive(false);
						examRoomAllotment.setModifiedBy(allotmentForm.getUserId());
						examRoomAllotment.setLastModifiedDate(new Date());
						if(examRoomAllotment.getRoomAllotmentDetails() != null){
							Set<ExamRoomAllotmentDetails> set = new HashSet<ExamRoomAllotmentDetails>();
							for (ExamRoomAllotmentDetails details : examRoomAllotment.getRoomAllotmentDetails()) {
								details.setIsActive(false);
								details.setLastModifiedDate(new Date());
								details.setModifiedBy(allotmentForm.getUserId());
								set.add(details);
							}
							examRoomAllotment.setRoomAllotmentDetails(set);
						}
						session.update(examRoomAllotment);
					}
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
	
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getSelectedRoomsDetails(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm)
	 */
	@Override
	public List<RoomMaster> getSelectedRoomsDetails( ExamRoomAllotmentForm allotmentForm,List<Integer> roomListIds) throws Exception {
		Session session = null;
		List<RoomMaster> roomList = new ArrayList<RoomMaster>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from RoomMaster room where room.isActive =1 and room.id in (:roomListIds)" +
								" order by room.blockId.blockOrder,room.floor,room.roomNo";
			roomList = session.createQuery(hqlQuery).setParameterList("roomListIds", roomListIds).list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return roomList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getExamTypeByExamName(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm)
	 */
	@Override
	public void getExamTypeByExamName(ExamRoomAllotmentForm allotmentForm) throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamDefinition def where def.isActive=1 and def.id="+Integer.parseInt(allotmentForm.getExamId());
			Query query= session.createQuery(hqlQuery);
			ExamDefinition examDef = (ExamDefinition) query.uniqueResult();
			if(examDef!=null && examDef.getExamType()!=null){
				if(examDef.getExamType().getId() == 1){
					allotmentForm.setMidOrEndSem("E");
				}else if(examDef.getExamType().getId() == 4){
					allotmentForm.setMidOrEndSem("M");
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getTotalStudentForClasses(java.util.List, java.lang.String)
	 */
	@Override
	public int getTotalStudentForClasses(List<Integer> classList,String hqlQuery) throws Exception {
		Session session =null;
		Integer totalStudentCount = 0;
		try{
			session =HibernateUtil.getSession();
			Query query = session.createQuery(hqlQuery);
			query.setParameterList("classList", classList);
			totalStudentCount = ((Long)query.uniqueResult()).intValue();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return totalStudentCount;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#getDuplicateAllotment(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm, java.util.List)
	 */
	public boolean getDuplicateAllotment (ExamRoomAllotmentForm allotmentForm,List<Integer> classIds)throws Exception{
		Session session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
			String query = "select allotmentDetails.classes.name from ExamRoomAllotment a " +
			" left join a.roomAllotmentDetails allotmentDetails "+
			" where a.examDefinition.id='"+allotmentForm.getExamId()+"'"+
			" and a.isActive=1 and allotmentDetails.isActive=1" +
			" and a.date='"+CommonUtil.ConvertStringToSQLDate(allotmentForm.getDate())+"'"+
			" and a.examinationSessions.id='"+allotmentForm.getSession()+"'"+
			" and allotmentDetails.classes.id in (:classesList)"+
			" group by allotmentDetails.classes.id ";
			List<String> boList = session.createQuery(query).setParameterList("classesList", classIds).list();
			if(boList!=null && !boList.isEmpty()){
				String duplicateClassNames ="";
				for (String obj : boList) {
					if(duplicateClassNames!=null && !duplicateClassNames.isEmpty()){
						duplicateClassNames = duplicateClassNames+","+ obj;
					}else {
						duplicateClassNames = obj;
					}
				}
				allotmentForm.setErrorMessage(duplicateClassNames);
				isDuplicate = true;
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return isDuplicate;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#isTimeTableDefineForClasses(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm)
	 */
	@Override
	public boolean isTimeTableDefineForClasses( ExamRoomAllotmentForm allotmentForm,List<Integer> classList) throws Exception {
		Session session =null;
		boolean isNoTimeTableDef = false;
		try{
			List<Integer> tempList = new ArrayList<Integer>();
			tempList.addAll(classList);
			session = HibernateUtil.getSession();
			String str = "select classes.id from EXAM_time_table t " +
					" left join EXAM_exam_course_scheme_details ON t.exam_course_scheme_id = EXAM_exam_course_scheme_details.id" +
					" left join EXAM_sessions ON t.exam_session_id = EXAM_sessions.id" +
					" left join course ON EXAM_exam_course_scheme_details.course_id = course.id" +
					" left join classes ON classes.course_id = course.id" +
					" where classes.id in (:classList)" +
					" and EXAM_exam_course_scheme_details.exam_id='"+allotmentForm.getExamId()+"'"+
					" and date_format(t.date_starttime,'%Y-%m-%d')='"+CommonUtil.ConvertStringToSQLDate(allotmentForm.getDate())+"'"+
					" and t.exam_session_id ='"+allotmentForm.getSession()+"'"+
					" and t.is_active =1" +
					" and EXAM_sessions.is_active =1";
					Query query = session.createSQLQuery(str).setParameterList("classList", classList);
					List<Integer> list = query.list();
					 if(list!=null && !list.isEmpty()){
						 for (Integer objects : list) {
							if(tempList.contains(objects)){
								tempList.remove(objects);
							}
						}
					 }
					 if(tempList!=null && !tempList.isEmpty()){
						 String hqlQuery = "select classes.name from Classes classes "+
						 " where classes.id in(:classesList)"+
						 " and classes.isActive =1 and classes.isActive =1";
						 Query query1 = session.createQuery(hqlQuery).setParameterList("classesList", tempList);
						 List<String> classListNames = query1.list();
						 if(classListNames!=null && !classListNames.isEmpty()){
							 String className = "";
							 for (String classes : classListNames) {
								 if(className!=null && !className.isEmpty()){
									 className = className +","+classes;
								 }else {
									 className = classes;
								 }
								 
							 }
							 isNoTimeTableDef = true;
							 allotmentForm.setErrorMessage(className);
						 }
					 }
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		session.close();
		return isNoTimeTableDef;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#checkSelectedRoomsAlreadyAlloted(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm, java.util.List)
	 */
	@Override
	public List<Integer> checkSelectedRoomsAlreadyAlloted( ExamRoomAllotmentForm allotmentForm, List<Integer> roomList)
			throws Exception {
		Session session  = null;
		List<Integer> classesList = new ArrayList<Integer>();
		Map<Integer,String> map = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select a.room.roomNo,allotmentDetails.classes.id from ExamRoomAllotment a " +
								" left join a.roomAllotmentDetails allotmentDetails "+
								" where a.isActive=1 and allotmentDetails.isActive=1" +
//								" a.examDefinition.id='"+allotmentForm.getExamId()+"'"+
								" and a.midOrEndSem='"+allotmentForm.getMidOrEndSem()+"'"+
								" and a.room.id in (:roomList)"+
								" and a.date is null"+
								" and a.examinationSessions is null"+
								" group by allotmentDetails.classes.id ";
			 List<Object[]> objList = session.createQuery(hqlQuery).setParameterList("roomList", roomList).list();
			 if(objList!=null && !objList.isEmpty()){
				 for (Object[] objects : objList) {
					 classesList.add(Integer.parseInt(objects[1].toString()));
					 map.put(Integer.parseInt(objects[1].toString()),objects[0].toString());
				}
				 allotmentForm.setCollectionMap(map);
			 }
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		session.close();
		return classesList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotment#isTimeTableDefineForClasses1(com.kp.cms.forms.examallotment.ExamRoomAllotmentForm, java.util.List)
	 */
	@Override
	public boolean isTimeTableDefineForClasses1( ExamRoomAllotmentForm allotmentForm, List<Integer> classList) throws Exception {
		Session session =null;
		List<Integer> list = new ArrayList<Integer>();
		boolean isNotTimeTableDef = false;
		try{
			session = HibernateUtil.getSession();
			String str = "select classes.id from EXAM_time_table t " +
					" inner join EXAM_exam_course_scheme_details ON t.exam_course_scheme_id = EXAM_exam_course_scheme_details.id" +
					" inner join EXAM_sessions ON t.exam_session_id = EXAM_sessions.id" +
					" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id" +
					" inner join classes ON classes.course_id = course.id" +
					" where classes.id in (:classList)" +
					" and EXAM_exam_course_scheme_details.exam_id='"+allotmentForm.getExamId()+"'"+
					" and date_format(t.date_starttime,'%Y-%m-%d')='"+CommonUtil.ConvertStringToSQLDate(allotmentForm.getDate())+"'"+
					" and t.exam_session_id ='"+allotmentForm.getSession()+"'"+
					" and t.is_active =1" +
					" and EXAM_sessions.is_active =1";
					Query query = session.createSQLQuery(str).setParameterList("classList", classList);
					 list = query.list();
					 if(list!=null && !list.isEmpty()){
						 Map<Integer,String> map = allotmentForm.getCollectionMap();
						 String errorMsg = "";
						 Set<String> set = new HashSet<String>();
						 for (Integer classId : list) {
							if(map.containsKey(classId)){
								String roomNo = map.get(classId);
								set.add(roomNo);
							}
						}
						 if(set!=null && !set.isEmpty()){
							 for (String clsId : set) {
								if(errorMsg!=null && !errorMsg.isEmpty()){
									errorMsg = errorMsg+", "+clsId;
								}else{
									errorMsg = clsId;
								}
							}
						 }
						 isNotTimeTableDef = true;
						 allotmentForm.setErrorMessage(errorMsg);
					 }
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		session.close();
		return isNotTimeTableDef;
	}
}
