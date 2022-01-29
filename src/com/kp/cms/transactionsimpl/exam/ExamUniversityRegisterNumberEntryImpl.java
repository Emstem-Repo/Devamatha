package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamUniversityRegisterNumberEntryImpl extends ExamGenImpl {

	private static final Log log = LogFactory
			.getLog(ExamUniversityRegisterNumberEntryImpl.class);

	public List<ExamProgramUtilBO> select_getProgramByAcademicYear(
			int academicYear) {

		Session session = null;
		ArrayList<ExamProgramUtilBO> listBo;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamProgramUtilBO.class);

			crit.add(Restrictions.eq("academicYear", academicYear));
			crit.add(Restrictions.eq("isActive", true));

			listBo = new ArrayList<ExamProgramUtilBO>(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			listBo = new ArrayList<ExamProgramUtilBO>();

		}
		return listBo;

	}

	public List<ExamProgramUtilBO> select_second_languge_student() {

		Session session = null;
		ArrayList<ExamProgramUtilBO> listBo;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamProgramUtilBO.class);
			crit.add(Restrictions.eq("isActive", true));

			listBo = new ArrayList<ExamProgramUtilBO>(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			listBo = new ArrayList<ExamProgramUtilBO>();

		}

		return listBo;

	}

	public ArrayList<StudentUtilBO> select_student_details(int academicYear,
			int courseId, int schemeId, int schemeNo, String orderBy) {

		ArrayList<StudentUtilBO> listObj = null;
		Session session = null;

		try {

			String HQL = " from StudentUtilBO s "
					+ " where s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId =  :courseId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId = :schemeId "
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear";
			if (orderBy.equalsIgnoreCase("rollNo")) {
				HQL = HQL + " order by s.rollNo";
			} else {
				HQL = HQL
						+ " order by s.admApplnUtilBO.personalDataUtilBO.firstName ";
			}

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query q = session.createQuery(HQL);
			q.setParameter("courseId", courseId);
			q.setParameter("academicYear", academicYear);
			q.setParameter("schemeId", schemeId);
			q.setParameter("schemeNo", schemeNo);

			listObj = new ArrayList<StudentUtilBO>(q.list());

			session.flush();
			// session.close();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			listObj = new ArrayList<StudentUtilBO>();
		}

		return listObj;
	}

	public void isDuplicated(ArrayList<String> regNoList) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL = "select registerNo from StudentUtilBO e where e.registerNo in (:regNo)";

			Query q = session.createQuery(HQL);
			q.setParameterList("regNo", regNoList);
			List<StudentUtilBO> list = q.list();
			if (list.size() > 0) {
				throw new DuplicateException();
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

	}

	public void isDuplicated_Regno(HashMap<String, Integer> map,
			ArrayList<String> regNoList, int count) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL = "select e.id,e.registerNo from StudentUtilBO e where e.registerNo in (:regNoList) ";

			Query q = session.createQuery(HQL);
			q.setParameterList("regNoList", regNoList);

			List<StudentUtilBO> list = new ArrayList<StudentUtilBO>(q.list());
			int stId = 0;
			Integer stId_map = 0;
			String reg = "";
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] row = (Object[]) it.next();
				if (row[0] != null) {
					stId = Integer.parseInt(row[0].toString());
				}
				if (row[1] != null) {
					reg = row[1].toString();
				}
				// stId_map = map.get(reg);
				if (map.containsKey(reg)) {
					stId_map = map.remove(reg);

					if (stId != stId_map) {
						throw new DuplicateException(Integer.toString(count));
					}

				}
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

	}

	public void update(int studentId, String regNo, String userId) {

		String HQL_QUERY = "update StudentUtilBO e set e.registerNo = :regNo"
				+ " where e.id = :studentId";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		query.setParameter("regNo", regNo);
		query.executeUpdate();
		tx.commit();
		session.close();
	}

}
