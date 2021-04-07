package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.DurationAllocationBo;
import com.kp.cms.bo.admin.ManageHolidayListBo;
import com.kp.cms.bo.admin.StaffAllocationBo;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class DurationAllocationImpl extends ExamGenImpl {

	private static Log log = LogFactory.getLog(DurationAllocationImpl.class);
	private static DurationAllocationImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private DurationAllocationImpl() {

	}

	public static DurationAllocationImpl getInstance() {
		if (impl == null) {
			return new DurationAllocationImpl();
		}
		return impl;
	}

	public ArrayList<Integer> selectSubjectsByYearAndCourse(int academicYear,
			int courseId) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("select s.id from SubjectUtilBO s"
							+ " where s.id in (select sgs.subjectUtilBO.id from SubjectGroupSubjectsUtilBO sgs"
							+ " where sgs.subjectGroupUtilBO.id in"
							+ " (select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css where"
							+ " css.curriculumSchemeDurationId in"
							+ " (select csd.id from CurriculumSchemeDurationUtilBO csd where csd.curriculumSchemeId in"
							+ " (select cs.id from CurriculumSchemeUtilBO cs where cs.courseId = :courseId"
							+ " and csd.academicYear = :year))))");
			query.setParameter("courseId", courseId);
			query.setParameter("year", academicYear);
			List list = query.list();
			if (list != null && list.size() > 0)
				return new ArrayList<Integer>(list);
		} catch (Exception e) {
			log.error("Exception in Fetching Record of StaffAllocationImpl "
					+ e.getMessage());
		}
		return new ArrayList<Integer>();
	}

	public List<Object[]> getAllSubjectsData(
			ArrayList<Integer> listOfSubjectIds) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from DurationAllocationBo bo"
							+ " right join bo.subjectId s where s.id in( :listOfSubjectIds)");
			query.setParameterList("listOfSubjectIds", listOfSubjectIds);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object[]>();
	}

}