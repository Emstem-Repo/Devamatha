package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;

public class ExamSupplementaryImprovementApplicationImpl extends ExamGenImpl {
	private static final Log log = LogFactory.getLog(ExamGenImpl.class);
	private static volatile ExamGenImpl examGenImpl = null;

	public static ExamGenImpl getInstance() {
		if (examGenImpl == null) {
			examGenImpl = new ExamGenImpl();
		}
		return examGenImpl;
	}

	@SuppressWarnings("unchecked")
	public List<ExamDefinitionBO> select_ActiveOnly_ExamName_SI() {

		Session session = null;
		List<ExamDefinitionBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = " from ExamDefinitionBO d"
					+ " where d.examTypeUtilBO.name in ('Supplementary', 'Special Supplementary', 'Regular & Supplementary')"
					+ " and d.delIsActive = 1  and d.isActive=1 group by d.name, d.year, d.month"
					+ " order by d.year desc, d.month desc";
			Query query = session.createQuery(HQL_QUERY);
			list = query.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();
		}
		return list;

	}

	public List<Object[]> select_Subjects(int courseId, int schemeId,
			int semesterYearNo, Integer studentId, Integer examId, int flag) {
		List<Object[]> list = new ArrayList<Object[]>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = null;
		// based on user selection, either supplementary or improvement subject
		// has to come
		try {
			/*HQL_QUERY = " SELECT s.id AS subjectId,"
				+ " s.code,"
				+ " s.name,"
				+ " e.is_failed_theory,"
				+ " e.is_failed_practical,"
				+ " e.fees,"
				+ " e.is_appeared_theory,"
				+ " e.is_appeared_practical,"
				+ " e.id AS eid,"
				+ " max(e.chance)"
				+ " FROM  subject s Left JOIN EXAM_supplementary_improvement_application e"
				+ " ON e.subject_id = s.id and  e.student_id = :studentId and e.exam_id= :examId "
				+ " LEFT JOIN subject_group_subjects sgs"
				+ " ON sgs.subject_id = s.id LEFT JOIN"
				+ " curriculum_scheme_subject css"
				+ " ON css.subject_group_id = sgs.subject_group_id"
				+ " LEFT JOIN curriculum_scheme_duration"
				+ " ON css.curriculum_scheme_duration_id ="
				+ " curriculum_scheme_duration.id"
				+ " LEFT JOIN curriculum_scheme"
				+ " ON curriculum_scheme_duration.curriculum_scheme_id ="
				+ " curriculum_scheme.id WHERE curriculum_scheme.course_id = :courseId"
				+ " AND curriculum_scheme.course_scheme_id = :schemeId"
				+ " AND e.scheme_no = :semesterYearNo";*/
			
					
			
			HQL_QUERY = " select e.subjectUtilBO.id, e.subjectUtilBO.code, e.subjectUtilBO.name, " +
						" e.isFailedTheory, e.isFailedPractical, e.fees, e.isAppearedTheory, e.isAppearedPractical," +
						" e.id, e.chance, e.classes.id,e.isTheoryOverallFailed,e.isPracticalOverallFailed " +
						" FROM ExamSupplementaryImprovementApplicationBO e WHERE e.studentId = :studentId and "	+
						" e.examId = :examId ";
				
			
			if (flag == 1) {
				HQL_QUERY = HQL_QUERY + " and e.isSupplementary = 1 ";
			} else {
				HQL_QUERY = HQL_QUERY + " and e.isImprovement = 1 ";
			}

			//HQL_QUERY = HQL_QUERY +  " group by s.id";
			
			//Query query = session.createSQLQuery(HQL_QUERY);
			Query query = session.createQuery(HQL_QUERY);
			//query.setParameter("courseId", courseId);
			//query.setParameter("schemeId", schemeId);
			query.setParameter("studentId", studentId);
			//query.setParameter("semesterYearNo", semesterYearNo);
			query.setParameter("examId", examId);
			list = query.list();

		} catch (Exception e) {
			session.close();

		}
		return list;

	}

	public List<ExamSupplementaryImprovementApplicationBO> getListOfStudent(
			boolean isSupImp, int courseId, int schemeId, int studentId,
			List<Integer> subjectIdList) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL_QUERY = "";
		List list;
		try {

			HQL_QUERY = "select distinct s.studentUtilBO.classSchemewiseUtilBO.classUtilBO.name, s.studentUtilBO.rollNo,"
					+ " s.studentUtilBO.registerNo, s.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName,"
					+ "  s.studentUtilBO.admApplnUtilBO.personalDataUtilBO.lastName, s.studentUtilBO.id"
					+ " from ExamSupplementaryImprovementApplicationBO s"
					+ " where s.subjectId in (:subjectIdList)";

			if (isSupImp) {
				HQL_QUERY = HQL_QUERY + " and s.isSupplementary = 1";
			} else {
				HQL_QUERY = HQL_QUERY + " and s.isImprovement = 1";
			}
			if (studentId != 0) {
				HQL_QUERY = HQL_QUERY + " and s.studentId = :studentId";
			}

			Query query = session.createQuery(HQL_QUERY);
			query.setParameterList("subjectIdList", subjectIdList,
					Hibernate.INTEGER);
			if (studentId != 0) {
				query.setParameter("studentId", studentId);
			}
			list = query.list();
			session.flush();
		} catch (HibernateException e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList();
		}

		return list;

	}

	public void delete_suppImpr(int studentId, int schemeNo) {

		Session session = null;
		Transaction tx = null;
		try {
			String HQL_QUERY = "delete from ExamSupplementaryImprovementApplicationBO e"
					+ " where e.studentId = :studentId and e.schemeNo= :schemeNo";
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("studentId", studentId);
			query.setParameter("schemeNo", schemeNo);
			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log
					.error("Exception in delete_suppImpr of ExamSupplementaryImprovementApplicationImpl class"
							+ e.getMessage());
			if (session != null) {
				if (tx != null) {
					tx.rollback();
				}
				session.flush();
				HibernateUtil.closeSession();

			}
		}

	}

	public List<Object[]> getStudentDetails(int studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Object[]> list = null;
		Query query;
		try {
			String SQL_QUERY = "select s.registerNo, s.rollNo, s.admApplnUtilBO.personalDataUtilBO.firstName, "
					+ " s.classSchemewiseUtilBO.classUtilBO.sectionName from StudentUtilBO s where s.id= :studentId";
			query = session.createQuery(SQL_QUERY);
			query.setParameter("studentId", studentId);
			list = query.list();
		} catch (Exception e) {
			log
					.error("Exception in getStudentDetails of ExamSupplementaryImprovementApplicationImpl class"
							+ e.getMessage());
			if (session != null) {
				session.flush();
				HibernateUtil.closeSession();

			}

		}

		return (null == list ? new ArrayList<Object[]>() : list);
	}

	public int getChance(int studentId, int subjectId) {
		int chance = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Object obj = null;
		try {
			Query query = session
					.createQuery("select max(sia.chance) from "
							+ "ExamSupplementaryImprovementApplicationBO sia where sia.subjectId="
							+ subjectId + " and sia.studentId=" + studentId);

			obj = query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			session.close();
		}
		if (obj != null) {
			chance = (Integer) obj;
		}

		return chance + 1;
	}

	public List<Object[]> select_student(String rollNo, String registerNo,
			boolean rollNoPresent, boolean regNoPresent, int schemeNo,
			boolean isSupplementary, Integer courseId, String examId) {

		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "select  distinct s.studentId, s.classes.name, "
					+ " s.studentUtilBO.rollNo, s.studentUtilBO.registerNo,"
					+ " s.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName"
					+ " from ExamSupplementaryImprovementApplicationBO s "
					+ " left join s.classes classes "
					+ " where s.examId =:examId";
			if (schemeNo > 0) {
				HQL_QUERY = HQL_QUERY + " and s.schemeNo = :schemeNo ";
			}
			if (courseId != null) {
				HQL_QUERY = HQL_QUERY
						+ "  and s.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId";

			}

			if (rollNoPresent) {
				HQL_QUERY = HQL_QUERY + " and s.studentUtilBO.rollNo = :rollNo";

			}
			if (regNoPresent) {

				HQL_QUERY = HQL_QUERY
						+ " and s.studentUtilBO.registerNo = :registerNo";
			}
			if (isSupplementary) {
				HQL_QUERY = HQL_QUERY + " and s.isSupplementary = 1";
			} else {
				HQL_QUERY = HQL_QUERY + " and s.isImprovement = 1";
			}

			Query query = session.createQuery(HQL_QUERY);
			// query.setParameter("schemeNo", schemeNo);
			if (rollNoPresent) {
				query.setParameter("rollNo", rollNo);
			}
			if (regNoPresent) {
				query.setParameter("registerNo", registerNo);
			}
			if (schemeNo > 0) {
				query.setParameter("schemeNo", schemeNo);
			}
			if (courseId != null) {
				query.setParameter("courseId", courseId);
			}
			if (examId != null && examId.trim().length() > 0)
				query.setParameter("examId", Integer.parseInt(examId));

			session.flush();
			return query.list();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return new ArrayList<Object[]>();
	}

	// To get course of a student
	public Integer getCourseOfStudent(Integer stuId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer courseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId"
				+ " from StudentUtilBO s" + " where s.id = :stuId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			courseId = (Integer) list.get(0);

		}

		return courseId;
	}

	public Integer getSuppSchemeForStudent(int studentId, String examId, String supImprovement) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer schmeNo = 0;
		String HQL_QUERY = "";
		
		HQL_QUERY = " select distinct bo.schemeNo from ExamSupplementaryImprovementApplicationBO bo "
				+ " where bo.examId= :examId and bo.studentId= :studentId ";
		if(supImprovement.equalsIgnoreCase("Supplementary")){
			HQL_QUERY = HQL_QUERY + " and bo.isSupplementary=1";
		}
		else{
			HQL_QUERY = HQL_QUERY + " and bo.isImprovement=1";
		}
						

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("examId", Integer.parseInt(examId));
		List list = query.list();

		if (list != null && list.size() > 0) {
			schmeNo = (Integer) list.get(0);

		}

		return schmeNo;
	}

	public StudentUtilBO getStudentRollORReg(String rollNo, String registerNo,
			boolean rollNoPresent) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "from StudentUtilBO s where";
		if (rollNoPresent) {
			HQL = HQL + " s.rollNo= :rollNo";
		} else {
			HQL = HQL + " s.registerNo= :registerNo";
		}
		Query query = session.createQuery(HQL);
		if (rollNoPresent) {
			query.setParameter("rollNo", rollNo);
		} else {
			query.setParameter("registerNo", registerNo);
		}
		Object obj = query.uniqueResult();
		if (obj != null) {
			return (StudentUtilBO) obj;
		}
		return new StudentUtilBO();

	}
	/**
	 * 
	 * @param registerNo
	 * @param schemeNo
	 * @param rollNo
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getSubjectListForaStudent(String registerNo, int schemeNo, String rollNo) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
	
			String SQL_QUERY = " select sub.id, sub.code, sub.name, stu.class_id from EXAM_student_sub_grp_history grp" +
					" inner join EXAM_student_previous_class_details stu " +
					" on stu.student_id = grp.student_id and stu.scheme_no=grp.scheme_no " +
					" inner join student ON grp.student_id = student.id" +
					" inner join subject_group_subjects sgs on grp.subject_group_id = sgs.subject_group_id " +
					" inner join subject sub on sgs.subject_id = sub.id " +
					" where grp.scheme_no =  " + schemeNo +
					" and sgs.is_active = 1 and sub.is_active = 1";
			if(rollNo!= null && !rollNo.trim().isEmpty()){
				SQL_QUERY = SQL_QUERY  + " and student.roll_no = '" + rollNo.trim() + "'";
			}
			else if(registerNo!= null && !registerNo.trim().isEmpty()){
				SQL_QUERY = SQL_QUERY  + " and student.register_no = '" + registerNo.trim() + "'";
			}
				
			Query query = session.createSQLQuery(SQL_QUERY+" group by sub.id");
			List<Object[]> tempList = query.list();
						
			
			if (session != null) {
				session.flush();
				session.close();
			}
			return tempList;			
		} catch (Exception e) {
			log.error("Error while deleting" + e);
			throw new Exception();
		}

	}
	
	/**
	 * 
	 * @param studentId
	 * @param examId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(int studentId, int examId, int schemeNo) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
	
			String HQL_QUERY = "from  ExamSupplementaryImprovementApplicationBO e where e.schemeNo = " + schemeNo
							 + " and e.studentId = " + studentId + " and examId = " + examId;
		
				
			Query query = session.createQuery(HQL_QUERY);
			List<Object[]> tempList = query.list();
						
			
			if (session != null) {
				session.flush();
				session.close();
			}
			if(tempList!= null && tempList.size() > 0){
				return true;
			}
			else{
				return false;
			}
						
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public List<Object[]> getSubjectListForCurrentClassForAStudent(String regNo, int schemeNo, String rollNo) throws Exception{
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="select subGrpSet.subject.id, " +
					" subGrpSet.subject.code," +
					" subGrpSet.subject.name, " +
					"s.classSchemewise.classes.id" +
					" from Student s join s.admAppln.applicantSubjectGroups sg" +
					" join sg.subjectGroup.subjectGroupSubjectses subGrpSet" +
					" where s.admAppln.isCancelled=0" +
					" and subGrpSet.isActive=1" +
					" and subGrpSet.subject.isActive=1" +
					" and s.classSchemewise.classes.termNumber="+schemeNo;
			if(regNo!=null && !regNo.isEmpty())
					query=query+" and s.registerNo='"+regNo+"'" ;
			if(rollNo!=null && !rollNo.isEmpty())
				query=query+" and s.rollNo='"+rollNo+"'" ;
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	
	
}
