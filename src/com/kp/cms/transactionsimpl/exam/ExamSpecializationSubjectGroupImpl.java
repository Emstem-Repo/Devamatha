package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamSpecializationSubjectGroupImpl extends ExamGenImpl {

	public void delete_Specialization(int academicYear, int courseId,
			int schemeId, int specializationId) {
		String HQL_QUERY = "delete from ExamSpecializationSubjectGroupBO e"
				+ " where  e.courseId = :courseId and academicYear = :academicYear and schemeId = :schemeId and specializationId=:specializationId";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("schemeId", schemeId);
		query.setParameter("specializationId", specializationId);

		query.executeUpdate();

	}

	public ArrayList<CurriculumSchemeUtilBO> select_Scheme(String courseId) {

		Session session = null;
		ArrayList<CurriculumSchemeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(CurriculumSchemeUtilBO.class);
			crit.add(Restrictions.eq("courseId", courseId));

			list = new ArrayList(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<CurriculumSchemeUtilBO>();

		}
		return list;

	}

	public List getSpecializationSubject(int academicYear, int courseId,
			int schemeId) {
		String HQL_QUERY = "select e.specializationId, e.subjectGroupId from ExamSpecializationSubjectGroupBO e"
				+ " where  e.courseId = :courseId and academicYear = :academicYear and schemeId = :schemeId order by specializationId";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("schemeId", schemeId);

		return query.list();

	}

	public List getSubjectGroupList(int academicYear, int courseId, int schemeId) {
		String HQL_QUERY =

		"select css.subjectGroupUtilBO.id, css.subjectGroupUtilBO.name"
				+ " from CurriculumSchemeSubjectUtilBO css"
				+ " where css.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
				+ " and css.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeId"
				+ " and css.curriculumSchemeDurationUtilBO.academicYear = :academicYear";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("courseId", courseId);
		query.setParameter("academicYear", academicYear);
		query.setParameter("schemeId", schemeId);

		return query.list();
	}
	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public String getSchemeName(int courseId, int schemeNo, int year) throws Exception {
		Session session = null;
		String schemeName = "";
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			
			Query query = session.createQuery(" select c.courseScheme.name " +
					" from CurriculumScheme c " +
					" inner join c.curriculumSchemeDurations d " +
					" where c.course.id = " + courseId +
					"  and d.semesterYearNo = " + schemeNo +
					" and year = " + year);

/*			
			Query query = session
					.createQuery("from CurriculumScheme c where c.course.id = " + courseId + " and " +
							schemeNo + " <= noScheme and year = " + year );
*/			
			List<String> list = query.list();
			if(list!= null && list.size() > 0){
				schemeName = list.get(0);
			}
			session.flush();
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return schemeName;
	}
}
