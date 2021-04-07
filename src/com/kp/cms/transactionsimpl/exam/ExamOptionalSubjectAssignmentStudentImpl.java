package com.kp.cms.transactionsimpl.exam;

/**
 * Feb 18, 2010 Created By 9Elements
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.exam.ExamStudentOptionalSubjectGroupBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamOptionalSubjectAssignmentStudentImpl extends ExamGenImpl {

	// To get the main list

	public List<Object[]> select_students_spec(ArrayList<Integer> classIdList)
			throws HibernateException {

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			String HQL_QUERY = "select spec.id, stu.id as studentId , stu.rollNo as rollno,"
					+ " stu.registerNo as regno ,"
					+ " stu.admApplnUtilBO.personalDataUtilBO.firstName  as firstName ,"
					+ " stu.admApplnUtilBO.personalDataUtilBO.lastName as lastName ,"
					+ " spec3.name as specName , 0 as optsubgrp,"
					+ " 0 as checkId, spec.specializationId as specId, stu.admApplnId"
					+ " from StudentUtilBO stu"
					+ " left join stu.examStudentBioDataBOSet spec"
					+ " left join spec.examSpecializationBO spec3"
					+ " where stu.classSchemewiseUtilBO.classId in (:classIdList)";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameterList("classIdList", classIdList);
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}

	// To get the main list
	public List<Object[]> select_specId_subGrpId(ArrayList<Integer> classIdList, String academicYear)
			throws HibernateException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Object[]> l = new ArrayList<Object[]>();
		try {
			/*String HQL_QUERY = "select e.id, grpSet.subjectGroupId as optSubGroupId "
					+ " from ExamSpecializationBO e"
					+ " left join e.examSpecSubGrpBOset grpSet "
					+ " where e.id in (select spec.specializationId as specId "
					+ "  from StudentUtilBO stu "
					+ " left join stu.examOptSubjAssignStudentBOset spec "
					+ "  left join spec.examStudentOptionalSubjectGroupBOSet spec2 "
					+ "  left join spec2.subjectGroupUtilBO subgrp "
					+ "  where stu.classSchemewiseUtilBO.classId in (:classIdList) "
					+ "  and spec.examSpecializationBO.id is not null) ";
			 */
			
			String HQL_QUERY = " select  spec.id specId,  sg.id as subjectGroupId" +
			" from student stu left join adm_appln appln on stu.adm_appln_id = appln.id " +
			" left join EXAM_student_bio_data bio on bio.student_id = stu.id " +
			" left join class_schemewise cs on stu.class_schemewise_id = cs.id " +
			" left join classes cls on cs.class_id = cls.id " +
			" left join course course on cls.course_id = course.id  " +
			" left join EXAM_specialization_subject_group grp on grp.course_id = course.id and grp.specialization_id = bio.specialization_id " +
			" left join EXAM_specialization spec on grp.specialization_id = spec.id " +
			" left join subject_group sg on grp.subject_group_id = sg.id " +
			" where bio.specialization_id is not null and cls.id in (:classIdList)  " +
			" and grp.scheme_no = cls.term_number  and grp.academic_year =  " + academicYear +
			" and grp.course_id = course.id  " +
			" group by subjectGroupId, specId ";

			
			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameterList("classIdList", classIdList);
			l = query.list();
		} catch (HibernateException e) {
			throw e;
		}
		return l;
	}

	// returns subjectgroup id and name
	public List<Object[]> select_subGrpId_SubGrpName(
			ArrayList<Integer> classIdList, String academicYear) throws HibernateException {

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			/*String HQL_QUERY = "select s.id, s.name"
					+ " from SubjectGroupUtilBO s"
					+ " where s.id in (select grpSet.subjectGroupId  "
					+ " from ExamSpecializationBO e"
					+ " left join e.examSpecSubGrpBOset grpSet "
					+ " where e.id in (select spec.specializationId as specId "
					+ "  from StudentUtilBO stu "
					+ "  left join stu.examOptSubjAssignStudentBOset spec "
					// +
					// "  left join spec.examStudentOptionalSubjectGroupBOSet spec2 "
					// + "  left join spec2.subjectGroupUtilBO subgrp "
					+ "  where stu.classSchemewiseUtilBO.classId in (:classIdList) "
					+ "  and spec.examSpecializationBO.id is not null))";*/
			
			
			String HQL_QUERY = " select sg.id sgId, sg.name sgName, spec.id specId " +
								" from student stu left join adm_appln appln on stu.adm_appln_id = appln.id " +
								" left join EXAM_student_bio_data bio on bio.student_id = stu.id " +
								" left join class_schemewise cs on stu.class_schemewise_id = cs.id " +
								" left join classes cls on cs.class_id = cls.id " +
								" left join course course on cls.course_id = course.id  " +
								" left join EXAM_specialization_subject_group grp on " +
								" grp.course_id = course.id and grp.specialization_id = bio.specialization_id " +
								" left join EXAM_specialization spec on grp.specialization_id = spec.id " +
								" left join subject_group sg on grp.subject_group_id = sg.id " +
								" where bio.specialization_id is not null and cls.id in (:classIdList) " +
								" and grp.scheme_no = cls.term_number and grp.academic_year =  " + academicYear +
								" and grp.course_id = course.id " +
								" group by sgId, specId ";


			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameterList("classIdList", classIdList);
			return query.list();
		} catch (HibernateException e) {
			throw e;
		}
	}

	// To delete
	public void delete_optSubGrp(Integer id, Integer optSubGroupId)
			throws HibernateException {
		try {
			String HQL_QUERY = "delete from ExamStudentOptionalSubjectGroupBO e"
					+ " where e.specializationOptionalSubjectId = :id and e.subjectGroupId = :optSubGroupId";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.setParameter("optSubGroupId", optSubGroupId);
			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException e) {
			throw e;
		}
	}

	// Duplicate Check
	public boolean isDuplicated(Integer specId, Integer optSubGroupId)
			throws Exception {
		Session session = null;
		boolean var = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamStudentOptionalSubjectGroupBO.class);
			crit.add(Restrictions.eq("subjectGroupId", optSubGroupId));
			crit
					.add(Restrictions.eq("specializationOptionalSubjectId",
							specId));

			List<ExamStudentOptionalSubjectGroupBO> list = crit.list();

			if (list.size() > 0) {
				var = true;
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}
		return var;

	}

	/**
	 * 
	 * @param admApplnIds
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Map<Integer, Integer>> getApplicantSubjectGroup(
			String admApplnIds) throws Exception {
		Session session = null;
		Map<Integer, Map<Integer, Integer>> admMap = new HashMap<Integer, Map<Integer, Integer>>();
		if (admApplnIds != null && admApplnIds.trim().length() > 0) {
			try {
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery("from AdmAppln a where a.id in ( "
								+ admApplnIds + ")");

				List<AdmAppln> admApplnList = (List<AdmAppln>) query.list();

				Iterator<AdmAppln> itr = admApplnList.iterator();
				while (itr.hasNext()) {
					AdmAppln admAppln = (AdmAppln) itr.next();
					Map<Integer, Integer> applcantSubGroupMap = new HashMap<Integer, Integer>();
					if (admAppln.getApplicantSubjectGroups() != null
							&& admAppln.getApplicantSubjectGroups().size() > 0) {
						Iterator<ApplicantSubjectGroup> itr1 = admAppln
								.getApplicantSubjectGroups().iterator();
						while (itr1.hasNext()) {
							ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) itr1
									.next();
							applcantSubGroupMap.put(applicantSubjectGroup
									.getSubjectGroup().getId(),
									applicantSubjectGroup.getSubjectGroup()
											.getId());
						}
						admMap.put(admAppln.getId(), applcantSubGroupMap);
					}
					/*
					 * else{ admMap = new HashMap<Integer,
					 * Map<Integer,Integer>>(); }
					 */

				}

				session.flush();
				
			} catch (Exception e) {
				if (session != null) {
					session.flush();
				}
				throw new ApplicationException(e);
			}
		}
		return admMap;
	}

	/**
	 * 
	 * @param subGroupMap
	 * @return
	 * @throws Exception
	 */
	public boolean deleteApplicantSubjectGroup(Integer applnId, Integer subId)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("delete from ApplicantSubjectGroup a where a.admAppln.id = "
							+ applnId + " and a.subjectGroup.id = " + subId);
			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
}
