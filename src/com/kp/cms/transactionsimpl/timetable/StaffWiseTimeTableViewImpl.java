package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class StaffWiseTimeTableViewImpl extends ExamGenImpl {

	private static Log log = LogFactory
			.getLog(StaffWiseTimeTableViewImpl.class);
	private static StaffWiseTimeTableViewImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private StaffWiseTimeTableViewImpl() {

	}

	public static StaffWiseTimeTableViewImpl getInstance() {
		if (impl == null) {
			return new StaffWiseTimeTableViewImpl();
		}
		return impl;
	}

	public List<Object[]> getTeachingStaffList() {

		log
				.info("Entering into TeachingStaffList method of StaffWiseTimeTableViewImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("select distinct bo.teachingStaffId.id,bo.teachingStaffId.firstName from StaffAllocationBo bo");
			log
					.info("exit of TeachingStaffList method of StaffWiseTimeTableViewImpl");
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getTeachingStaffList method of StaffWiseTimeTableViewImpl "
							+ e.getMessage());
		}
		return new ArrayList<Object[]>();
	}

	// select
	// bo.day,bo.classId.classes.name,bo.preferredSubjectId.name,bo.roomId.roomNo,
	// bo.periodId.periodName,bo.periodId.startTime,bo.periodId.endTime
	// from TimeAllocationBo bo where bo.teachingStaffId.id=10
	public List<TimeAllocationBo> getStaffTimeTable(int teachingStaffId,
			int academicYear) {
		log
				.info("Entering into getStaffTimeTable method of StaffWiseTimeTableViewImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.teachingStaffId.id= :teachingStaffId "
							+ "and bo.academicYear= :academicYear order by bo.day");
			query.setParameter("teachingStaffId", teachingStaffId);
			query.setParameter("academicYear", academicYear);
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getStaffTimeTable method of StaffWiseTimeTableViewImpl "
							+ e.getMessage());
		}
		return new ArrayList<TimeAllocationBo>();

	}

}