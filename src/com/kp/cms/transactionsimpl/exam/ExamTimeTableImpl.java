package com.kp.cms.transactionsimpl.exam;

/**
 * Feb 2, 2010 Created By 9Elements Team
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamTimeTableForm;
import com.kp.cms.to.exam.ExamSubjectTimeTableTO;
import com.kp.cms.to.exam.ExamTimeTableTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamTimeTableImpl extends ExamGenImpl {

	public ArrayList<ExamDefinitionBO> select_ExamName(int examTypeID) {
		Session session = null;
		ArrayList<ExamDefinitionBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamDefinitionBO.class);
			crit.addOrder(Order.desc("isCurrent"));
			crit.add(Restrictions.eq("examTypeID", examTypeID));
			crit.add(Restrictions.eq("delIsActive", true));
			crit.add(Restrictions.eq("isActive", true));

			list = new ArrayList(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();

		}
		return list;

	}

	// To get the first page details
	public ArrayList<Object[]> select_details(int examTypeId, int examId, ExamTimeTableForm objform) throws BusinessException {

		Session session = null;
		ArrayList<Object[]> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String examType=getExamType(examTypeId);
			String strQuery="";
			Query query =null;
			if(examType!=null && !examType.contains("Supplementary"))
			{
				/*strQuery = "  select c.id, e.examTypeUtilBO.name, e.examForJoiningBatch ,"
					+ " c.examProgramUtilBO.programName, c.examCourseUtilBO.courseName, c.schemeNo ,"
					+ " c.courseId, c.courseSchemeId, e.academicYear, c.examId"
					+ " from ExamExamCourseSchemeDetailsBO c"
					+ " join c.examDefinitionBO e"
					+ " where e.examTypeID = :examTypeId and e.id = :examId"
					+ " and e.isActive =1  and c.isActive =1 ";*/
				
//added by manu to get all the records at single hit to the database
				
				strQuery = " select EXAM_exam_course_scheme_details.id,exam_type.name as exam_type_name,curriculum_scheme_duration.academic_year as cuacademicYear,program.name as program_name,course.name as course_name,"
					+ " EXAM_exam_course_scheme_details.scheme_no,course.id as course_id,EXAM_exam_course_scheme_details.course_scheme_id,EXAM_definition.academic_year as edYear,EXAM_definition.id as exam_id,"
//					+ " (select count( distinct subject.id) from EXAM_time_table "
//                    + " inner join subject on EXAM_time_table.subject_id = subject.id "
//                    + " inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " 
//                    + " and subject_group_subjects.is_active=1"
//                    + " inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id "
//                    + " inner join curriculum_scheme_subject on curriculum_scheme_subject.subject_group_id = subject_group.id "
//                    + " inner join curriculum_scheme_duration ON curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id "
//                    + " where subject.is_certificate_course=0 "
//                    + " and EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id "
//                    + " and EXAM_time_table.is_active=1 ) as examSubCount, "
					+ " count(distinct EXAM_time_table.subject_id) as examSubCount, "
                    + " count(distinct subject.id) as totalcount,curriculum_scheme.year" 
					+ " from EXAM_exam_course_scheme_details " 
					+ " inner join EXAM_definition on EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " 
					+ " inner join curriculum_scheme on curriculum_scheme.course_id=EXAM_exam_course_scheme_details.course_id " 
					+ " inner join curriculum_scheme_duration on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" 
					+ " and curriculum_scheme_duration.semester_year_no=EXAM_exam_course_scheme_details.scheme_no" 
					+ " and curriculum_scheme_duration.academic_year=EXAM_definition.academic_year"
					+ " inner join curriculum_scheme_subject on curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id" 
					+ " inner join subject_group ON curriculum_scheme_subject.subject_group_id = subject_group.id" 
					+ " inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id" 
					+ " and subject_group_subjects.is_active=1"
					+ " inner join subject ON subject_group_subjects.subject_id = subject.id" 
					+ " and subject.is_certificate_course=0" 
					+ " inner join course ON curriculum_scheme.course_id = course.id" 
					+ " inner join program ON course.program_id = program.id" 
					+ " inner join program_type on program.program_type_id = program_type.id"
					+ " inner join exam_type on EXAM_definition.exam_type_id = exam_type.id" 
					+ " left join EXAM_time_table on EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " 
					+ " and EXAM_time_table.subject_id = subject.id" 
					+ " and EXAM_time_table.is_active=1" 
					+ " where EXAM_definition.id= :examId" 
					+ " and exam_type.id = :examTypeId" 
					+ " and EXAM_exam_course_scheme_details.is_active=1 and course.is_active=1 " 
					+ " and EXAM_definition.del_is_active=1 and program.is_active=1 " ;
                
				if(objform.getProgramTypeId()!=null && !objform.getProgramTypeId().isEmpty()){
                	 strQuery=strQuery+" and program_type.id="+objform.getProgramTypeId();
                 }
                 if(objform.getProgramId()!=null && !objform.getProgramId().isEmpty()){
                	 strQuery=strQuery+" and program.id="+objform.getProgramId(); 
                 }
                 strQuery=strQuery+" group by curriculum_scheme.year, course.id, EXAM_exam_course_scheme_details.scheme_no" +
                 		         " order by curriculum_scheme_duration.academic_year,course.code,EXAM_exam_course_scheme_details.scheme_no"; 
                 
				query = session.createSQLQuery(strQuery);
				query.setParameter("examTypeId", examTypeId);
				query.setParameter("examId", examId);
			}
			else
			{
				/*strQuery="SELECT eecsd.id, "
					+"et.name,"
					+"csd.academic_year,"
					+"pro.name as program_name,"
					+"co.name as course_name,"
					+"eecsd.scheme_no,"
					+"co.id as course_id,"
					+"eecsd.course_scheme_id,"
					+"ed.academic_year as exam_academic_year,"
					+"ed.id as exam_id "
					+"FROM " +
							" course co " +
							" INNER JOIN " +
							" program pro " +
							" ON (co.program_id = pro.id) " +
							" INNER JOIN " +
							" EXAM_exam_course_scheme_details eecsd " +
							" ON (eecsd.program_id = pro.id) " +
							" AND (eecsd.course_id = co.id) " +
							" INNER JOIN " +
							" EXAM_definition ed " +
							" ON (eecsd.exam_id =ed.id) " +
							" INNER JOIN " +
							" curriculum_scheme cs " +
							" ON (cs.course_id = co.id) " +
							" INNER JOIN " +
							" curriculum_scheme_duration csd " +
							" ON (csd.curriculum_scheme_id =cs.id) " +
							" INNER JOIN " +
							" exam_type et " +
							" ON (ed.exam_type_id=et.id) " +
							" where ed.del_is_active=1 and pro.is_active=1 " +
							" and eecsd.is_active=1 and co.is_active=1 " +
							" and ed.id="+examId+" and csd.academic_year>=ed.exam_for_joining_batch " +
							" and csd.academic_year<=ed.academic_year group by cs.course_id,eecsd.scheme_no,csd.academic_year";*/
				
//added by manu to get all the records at single hit to the database
				strQuery=" SELECT EXAM_exam_course_scheme_details.id,exam_type.name as exam_type_name,curriculum_scheme_duration.academic_year as cuYear,program.name as program_name,course.name as course_name, "
					+" EXAM_exam_course_scheme_details.scheme_no,course.id as course_id,EXAM_exam_course_scheme_details.course_scheme_id,EXAM_definition.academic_year as exam_academic_year,EXAM_definition.id as exam_id ,"
                    + " count(distinct EXAM_time_table.subject_id) as examSubCount," 
                    + " count(distinct subject.id) as totalcount,curriculum_scheme.year" 
					+ " from EXAM_exam_course_scheme_details " 
					+ " inner join EXAM_definition on EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " 
					+ " inner join curriculum_scheme on curriculum_scheme.course_id=EXAM_exam_course_scheme_details.course_id " 
					+ " and curriculum_scheme.year >= EXAM_definition.exam_for_joining_batch " 
					+ " inner join curriculum_scheme_duration on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" 
					+ " and curriculum_scheme_duration.semester_year_no=EXAM_exam_course_scheme_details.scheme_no" 
					+ " and curriculum_scheme_duration.academic_year<=EXAM_definition.academic_year"
					+ " inner join curriculum_scheme_subject on curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id" 
					+ " inner join subject_group ON curriculum_scheme_subject.subject_group_id = subject_group.id" 
					+ " inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id" 
					+ " and subject_group_subjects.is_active=1"
					+ " inner join subject ON subject_group_subjects.subject_id = subject.id" 
					+ " and subject.is_certificate_course=0" 
					+ " inner join course ON curriculum_scheme.course_id = course.id" 
					+ " inner join program ON course.program_id = program.id"
					+ " inner join program_type on program.program_type_id = program_type.id"
					+ " inner join exam_type on EXAM_definition.exam_type_id = exam_type.id" 
					+ " left join EXAM_time_table on EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " 
					+ " and EXAM_time_table.subject_id = subject.id" 
					+ " and EXAM_time_table.is_active=1" 
					+ " where EXAM_definition.del_is_active=1 and program.is_active=1 " 
					+ " and EXAM_exam_course_scheme_details.is_active=1 and course.is_active=1 " 
					+ " and EXAM_definition.id="+examId;
				
				if(objform.getProgramTypeId()!=null && !objform.getProgramTypeId().isEmpty()){
               	 strQuery=strQuery+" and program_type.id="+objform.getProgramTypeId();
                }
                if(objform.getProgramId()!=null && !objform.getProgramId().isEmpty()){
               	 strQuery=strQuery+" and program.id="+objform.getProgramId(); 
                }
                strQuery=strQuery+" group by curriculum_scheme.year, course.id, EXAM_exam_course_scheme_details.scheme_no " +
                		         " order by curriculum_scheme_duration.academic_year,course.code,EXAM_exam_course_scheme_details.scheme_no"; 
				
				query = session.createSQLQuery(strQuery);
			}
			list = (ArrayList<Object[]>) query.list();

			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object[]>();
		}
		return list;
	}

	private String getExamType(int examTypeId) throws Exception
	{
		Session session=null;
		String examType=null;
		try
		{
			session=HibernateUtil.getSession();
			ExamTypeUtilBO type=(ExamTypeUtilBO)session.get(ExamTypeUtilBO.class, examTypeId);
			if(type!=null)
				examType=type.getName();
		}
		catch (Exception e) {
			throw new ApplicationException(e);
		}
		
		
		return examType;
	}

	// To get the subjects for a particular course and scheme, for second page
	public List<Object[]> select_Subjects(int courseId, int schemeId,
			int schemeNo, Integer examId) throws BusinessException, ApplicationException {

		Session session = null;
		Query query = null;
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		String SQL_QUERY = " SELECT sub.id as subid,"
				+ "  sub.name,"
				+ "  sub.code,"
				+ "  et.date_endtime,"
				+ "  et.date_starttime,"
				+ "  et.id"
				+ "  FROM subject sub"
				+ "  LEFT JOIN EXAM_time_table et ON et.subject_id = sub.id"
				+ "  AND et.exam_course_scheme_id IN"
				+ "  (SELECT ecs.id"
				+ "  FROM EXAM_exam_course_scheme_details ecs"
				+ "  WHERE ecs.course_id = :courseId"
				+ "  AND ecs.course_scheme_id = :schemeId"
				+ "  AND ecs.scheme_no = :schemeNo and ecs.exam_id=:examId)"
				+ "  WHERE sub.id IN"
				+ "  (SELECT DISTINCT sgs.subject_id"
				+ "  FROM subject_group_subjects sgs"
				+ "  WHERE sgs.subject_group_id IN"
				+ "  (SELECT css.subject_group_id"
				+ "  FROM curriculum_scheme_subject css"
				+ "  WHERE css.curriculum_scheme_duration_id IN"
				+ "  (SELECT csd.id"
				+ "  FROM curriculum_scheme_duration csd "
				+ "  WHERE csd.curriculum_scheme_id IN"
				+ "  (SELECT cs.id"
				+ "  FROM curriculum_scheme cs"
				+ "  WHERE cs.course_id = :courseId"
				+ "  AND cs.course_scheme_id = :schemeId)"
				+ "  AND csd.semester_year_no = :schemeNo and csd.academic_year in (select ed.academic_year from EXAM_definition ed where ed.id = :examId))))";

		query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeId", schemeId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return query.list();

	}

	// To get subjects from time table
	public int select_TimeTableSubj(Integer examId) throws BusinessException, ApplicationException {
		Session session = null;
		ArrayList list = null;
		String retVal = null;
		Query q;
		int value = 0;
		try{
		String HQL = "select count(*)"
				+ " from ExamTimeTableBO t"
				+ " where t.subjectUtilBO.isCertificateCourse=0 and t.subjectUtilBO.id in (select s.id from SubjectUtilBO s) and"
				+ " t.examId = :examId";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		q = session.createQuery(HQL);
		q.setParameter("examId", examId);
		list = new ArrayList(q.list());
		if (list.size() > 0) {
			retVal = list.get(0).toString();
		}
		if (retVal != null)
			value = Integer.parseInt(retVal);
		session.flush();
		// session.close();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return value;
	}

	// To get subjects for a course, scheme & year
	public int select_course_scheme_year(Integer courseId, Integer shemeId,
			Integer schemeNo, Integer academicYear) throws BusinessException, ApplicationException {

		Session session = null;
		ArrayList list = null;
		String retVal = null;
		int value = 0;
		try{
		String HQL = "select count(*)"
				+ " from SubjectUtilBO sub"
				+ " where sub.id in"
				+ " (select distinct sgs.subjectUtilBO.id"
				+ " from SubjectGroupSubjectsUtilBO sgs"
				+ " where sgs.subjectUtilBO.isCertificateCourse=0 and sgs.subjectGroupUtilBO.id in ("
				+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
				+ " where css.curriculumSchemeDurationId in ("
				+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
				+ " where csd.curriculumSchemeId in ( "
				+ " select cs.id from CurriculumSchemeUtilBO cs"
				+ " where cs.courseId = :courseId and cs.courseSchemeId = :shemeId)"
				+ " and csd.semesterYearNo = :schemeNo))) ";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query q = session.createQuery(HQL);
		q.setParameter("courseId", courseId);
		q.setParameter("shemeId", shemeId);
		q.setParameter("schemeNo", schemeNo);
		list = new ArrayList(q.list());
		if (list.size() > 0) {
			retVal = list.get(0).toString();
		}
		if (retVal != null)
			value = Integer.parseInt(retVal);
		session.flush();
		// session.close();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return value;
	}

	// To update
	public void updateExamTimeTable(int id, Date dateStarttime, Date dateEndtime, ExaminationSessions sessions) throws BusinessException, ApplicationException {
		Session session = null;
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String HQL_QUERY = "update ExamTimeTableBO e set"
			+ " e.dateStarttime = :dateStarttime, e.dateEndtime = :dateEndtime";
		if(sessions != null && sessions.getId() != 0){
			HQL_QUERY = HQL_QUERY + ",e.examinationSessions=:sessions";
		}
		HQL_QUERY = HQL_QUERY + " where e.id = :id ";
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("dateStarttime", dateStarttime);
		query.setParameter("dateEndtime", dateEndtime);
		if(sessions != null && sessions.getId() != 0){
			query.setParameter("sessions", sessions);
		}
		query.executeUpdate();
		tx.commit();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public ExamTimeTableTO getSubjects(int id, String joingBatch, String year) throws BusinessException {
		ExamTimeTableTO to = new ExamTimeTableTO();
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "select ecs.examDefinitionBO.examForJoiningBatch||'-'||ecs.examDefinitionBO.examForJoiningBatch+1, "
					+ " ecs.courseId, ecs.examCourseUtilBO.courseName, ecs.courseSchemeId, ecs.courseSchemeUtilBO.name,  ecs.schemeNo,"
					+ "  ecs.examProgramUtilBO.programName,"
					+ " ecs.examDefinitionBO.examTypeUtilBO.name, ecs.examId , ecs.id "
					+ " from ExamExamCourseSchemeDetailsBO ecs ,ExamDefinitionProgramBO ep where ecs.id = :id  and ep.examDefnId = ecs.examId ";
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);

			Iterator<Object[]> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();

				if (obj[0] != null) {
					if (obj[0].toString().equalsIgnoreCase("0-1"))
						to.setBatch("");
					else
						if(joingBatch!=null && !joingBatch.isEmpty())
						to.setBatch(joingBatch+'-'+Integer.toString((Integer.parseInt(joingBatch)+1)));
						else
							to.setBatch(obj[0].toString());
				}
				if (obj[1] != null) {
					to.setCourseId(Integer.parseInt(obj[1].toString()));
				}
				if (obj[2] != null) {
					to.setCourse(obj[2].toString());
				}
				if (obj[3] != null) {
					to.setSchemeId(Integer.parseInt(obj[3].toString()));
				}
				if (obj[4] != null) {
					to.setScheme(obj[4].toString());
				}
				if (obj[5] != null) {
					to.setSchemeNo(Integer.parseInt(obj[5].toString()));
				}
				if (obj[6] != null) {
					to.setProgram(obj[6].toString());
				}
				if (obj[7] != null) {
					to.setExamType(obj[7].toString());
				}
				if (obj[8] != null) {
					to.setExamId(Integer.parseInt(obj[8].toString()));
				}
				if (obj[9] != null) {
					to.setId(Integer.parseInt(obj[9].toString()));
				}
				if (year != null && !year.isEmpty()) {
					to.setAcademicyear(Integer.parseInt(year));
				}
			}
			session.flush();
			session.close();
			return to;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return to;
	}

	public void delete_timetable(Integer id) throws BusinessException, ApplicationException {
		Session session = null;
		try{
		String HQL_QUERY = "delete from ExamTimeTableBO e"
				+ " where e.id = :id ";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.executeUpdate();
		tx.commit();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public ArrayList<ExamSubjectTimeTableTO> getTimeForSubjectIds(
			ArrayList<ExamSubjectTimeTableTO> emptyDateSubjects, Integer examId) throws BusinessException, ApplicationException {
		ArrayList<ExamSubjectTimeTableTO> subjects = new ArrayList<ExamSubjectTimeTableTO>();
		for (ExamSubjectTimeTableTO to : emptyDateSubjects) {
			Session session = null;
			try{
			String HQL_QUERY = "select max(et.dateStarttime), max(et.dateEndtime)"
					+ " from ExamTimeTableBO et"
					+ " where et.isActive=1 and et.subjectId = :subjectId and et.examExamCourseSchemeDetailsBO.examId=:examId";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", to.getSubid());
			query.setParameter("examId", examId);
			Iterator<Object[]> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[0] != null) {
					String startTime = obj[0].toString().substring(0,
							obj[0].toString().indexOf("."));
					// 2010-02-27 05:05:00
					String formatDate = CommonUtil.ConvertStringToDateFormat(
							startTime, "yyyy-MM-dd hh:mm:ss",
							"M/d/yyyy h:mm:ss a");
					Date d = new Date(formatDate);
					StringTokenizer s = new StringTokenizer(formatDate);
					String amOrPm = "";
					int addAmorPm = 0;
					while (s.hasMoreElements()) {
						amOrPm = (String) s.nextElement();

					}

					SimpleDateFormat pattern = new SimpleDateFormat(
							"dd/MM/yyyy");
					SimpleDateFormat pattern1 = new SimpleDateFormat("hh");
					SimpleDateFormat pattern2 = new SimpleDateFormat("mm");

					String date1 = pattern.format(d);
					String hr = pattern1.format(d);
					String min = pattern2.format(d);
					if (amOrPm.equalsIgnoreCase("PM")) {
						if (hr != null && hr.trim().length() > 0)
							addAmorPm = Integer.parseInt(hr) + 12;
					}
					to.setDate(date1);
					if (addAmorPm > 0) {
						to.setStartTimeHour(Integer.toString(addAmorPm));
					} else {
						to.setStartTimeHour(hr);
					}
					to.setStartTimeMin(min);
				}
				if (obj[1] != null) {
					String endTime = obj[1].toString().substring(0,
							obj[1].toString().indexOf("."));
					String formatDate = CommonUtil.ConvertStringToDateFormat(
							endTime, "yyyy-MM-dd hh:mm:ss",
							"M/d/yyyy h:mm:ss a");
					Date d = new Date(formatDate);
					StringTokenizer s = new StringTokenizer(formatDate);
					String amOrPm = "";
					int addAmorPm = 0;
					while (s.hasMoreElements()) {
						amOrPm = (String) s.nextElement();

					}
					SimpleDateFormat pattern = new SimpleDateFormat(
							"dd/MM/yyyy");
					SimpleDateFormat pattern1 = new SimpleDateFormat("hh");
					SimpleDateFormat pattern2 = new SimpleDateFormat("mm");

					String date1 = pattern.format(d);
					String hr = pattern1.format(d);
					String min = pattern2.format(d);
					if (amOrPm.equalsIgnoreCase("PM")) {
						if (hr != null && hr.trim().length() > 0)
							addAmorPm = Integer.parseInt(hr) + 12;
					}
					to.setDate(date1);
					if (addAmorPm > 0) {
						to.setEndTimeHour(Integer.toString(addAmorPm));
					} else {
						to.setEndTimeHour(hr);
					}
					to.setEndTimeMin(min);

				}
			}
			tx.commit();
			session.flush();
			subjects.add(to);
			}catch (Exception e) {
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		}

		return subjects;

	}

	/**
	 * @param courseId
	 * @param schemeNo
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public List<Subject> getSubjectsByCourseYearAndSemesterNo(int courseId,
			int schemeNo,String year,Integer examId,Integer examTypeId) throws Exception{
		Session session = null;
		List<Subject> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String examType=getExamType(examTypeId);
			String extarQuery="";
			if(examType!=null && examType.contains("Supplementary"))
				extarQuery=year;
			else
				extarQuery="(select e.academicYear from ExamDefinitionBO e where e.id="+examId+")";
			String searchCriteria="select s.subject from CurriculumSchemeDuration csd" +
					" join csd.curriculumSchemeSubjects cs" +
					" join cs.subjectGroup.subjectGroupSubjectses s" +
					" where s.subject.isActive=1 and s.subject.isCertificateCourse=0 and csd.academicYear="+extarQuery+" and s.isActive=1  and csd.curriculumScheme.course.id=" +courseId+
					" and csd.semesterYearNo=" +schemeNo+
					" group by s.subject.id";
			Query selectedCandidatesQuery=session.createQuery(searchCriteria);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}

	public Map<Integer,ExamTimeTableBO> getExamTimeTableByExamIdAndSchemeId(
			Integer examId, int schemeId, int schemeNo, int courseId) throws Exception {
		Session session = null;
		List<ExamTimeTableBO> selectedCandidatesList = null;
		Map<Integer,ExamTimeTableBO> finalMap=new HashMap<Integer,ExamTimeTableBO>();
		try {
			session = HibernateUtil.getSession();
			String searchCriteria="from ExamTimeTableBO et" +
					" where et.examExamCourseSchemeDetailsBO.courseId=" +courseId+
					" and et.examExamCourseSchemeDetailsBO.courseSchemeId=" +schemeId+
					" and et.examExamCourseSchemeDetailsBO.schemeNo=" +schemeNo+
					" and et.examExamCourseSchemeDetailsBO.examId=" +examId;
			Query selectedCandidatesQuery=session.createQuery(searchCriteria);
			selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<ExamTimeTableBO> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					ExamTimeTableBO bo = (ExamTimeTableBO) itr.next();
					if(bo.getSubjectId()>0){
						if(!finalMap.containsKey(bo.getSubjectId()))
						finalMap.put(bo.getSubjectId(), bo);
					}
				}
			}
			return finalMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
}
