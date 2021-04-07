package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.DurationAllocationBo;
import com.kp.cms.bo.admin.GenerateTimeTableBo;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class GenerateTimeTableImpl extends ExamGenImpl {

	private static Log log = LogFactory.getLog(GenerateTimeTableImpl.class);
	private static GenerateTimeTableImpl impl;

	private GenerateTimeTableImpl() {

	}

	public static GenerateTimeTableImpl getInstance() {
		if (impl == null) {
			return new GenerateTimeTableImpl();
		}
		return impl;
	}

	public Query getQuery(String HQL) throws ApplicationException {
		Session session;

		session = HibernateUtil.getSession();
		Query query = session.createQuery(HQL);
		return query;

	}

	public Object[] getSchemeDuration(String classes) {
		try {
			Query query = getQuery("select csd.startDate,csd.endDate,csd.semesterYearNo from ClassSchemewise cs "
					+ "join cs.curriculumSchemeDuration csd where cs.id= :curicullumSchemeId");
			if (query != null) {

				query.setParameter("curicullumSchemeId", Integer
						.parseInt(classes));
				return (Object[]) query.uniqueResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Object[2];
	}

	public List<DurationAllocationBo> getAllocatedHoursForSubjects(
			ArrayList<Integer> subjectIds) {
		try {
			Query query = getQuery("from DurationAllocationBo bo "
					+ "where bo.subjectId.id in( :subjectIds)");
			if (query != null) {

				query.setParameterList("subjectIds", subjectIds);
				return query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<DurationAllocationBo>();
	}

	public List<TimeAllocationBo> getEnterehoursPerWeek(
			ArrayList<Integer> subjectIds, Integer academicYear) {
		try {
			Query query = getQuery("from TimeAllocationBo bo where "
					+ "bo.preferredSubjectId.id in( :subjectIds) "
					+ "and bo.academicYear= :academicYear)");
			if (query != null) {
				query.setParameterList("subjectIds", subjectIds);
				query.setParameter("academicYear", academicYear);
				return query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TimeAllocationBo>();
	}

	public List<Object[]> getSubjects(Integer classSchemWiseId,
			Integer academicYear) {
		try {
			Query query = getQuery("select s.subject.id,s.subject.name from ClassSchemewise cs join "
					+ "cs.curriculumSchemeDuration.curriculumSchemeSubjects css join css.subjectGroup.subjectGroupSubjectses s "
					+ "where cs.curriculumSchemeDuration.academicYear= :academicYear and cs.id= :classSchemWiseId");
			if (query != null) {
				query.setParameter("classSchemWiseId", classSchemWiseId);
				query.setParameter("academicYear", academicYear);
				return query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object[]>();
	}
}