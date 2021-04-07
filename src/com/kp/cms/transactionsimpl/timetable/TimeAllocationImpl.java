package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.StaffAllocationBo;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class TimeAllocationImpl extends ExamGenImpl {

	private static Log log = LogFactory.getLog(TimeAllocationImpl.class);
	private static TimeAllocationImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private TimeAllocationImpl() {

	}

	public static TimeAllocationImpl getInstance() {
		if (impl == null) {
			return new TimeAllocationImpl();
		}
		return impl;
	}

	public List<TimeAllocationBo> getBottomGrid(int teachingStaffId,int academicYear) {
		log.info("Entering into getBottomGrid method of TimeAllocationImpl");
		// Session session = sessionFactory.openSession();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.teachingStaffId.id= :teachingStaffId and bo.academicYear= :academicYear");
			 query.setParameter("teachingStaffId", teachingStaffId);
			 query.setParameter("academicYear", academicYear);
			log.info("exit of getBottomGrid method of TimeAllocationImpl");
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getBottomGrid method of TimeAllocationImpl "
							+ e.getMessage());
		}
		return new ArrayList<TimeAllocationBo>();
	}

	public List<Object[]> getTeachingStaffList() {

		log
				.info("Entering into TeachingStaffList method of TimeAllocationImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("select distinct bo.teachingStaffId.id,bo.teachingStaffId.firstName from StaffAllocationBo bo");
			log.info("exit of TeachingStaffList method of TimeAllocationImpl");
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getTeachingStaffList method of TimeAllocationImpl "
							+ e.getMessage());
		}
		return new ArrayList<Object[]>();
	}

	public List<StaffAllocationBo> getDetailsOfStaff(int academicYear,
			int teachingStaffId) {
		log
				.info("Entering into getDetailsOfStaff method of TimeAllocationImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from StaffAllocationBo bo where bo.academicYear= :academicYear and bo.teachingStaffId.id= :teachingStaffId");
			query.setParameter("teachingStaffId", teachingStaffId);
			query.setParameter("academicYear", academicYear);
			log.info("exit of getDetailsOfStaff method of TimeAllocationImpl");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			log
					.error("Exception in getDetailsOfStaff method of TimeAllocationImpl "
							+ e.getMessage());
		}
		return new ArrayList<StaffAllocationBo>();
	}

	public List<Batch> getBatches(String[] classes, int subjectId) {
		log.info("Entering into getBatches method of TimeAllocationImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery(" from Batch batch where batch.subject.id= :subjectId and batch.classSchemewise.classes.id in( :classes )");
			query.setParameter("subjectId", subjectId);
			query.setParameterList("classes", classes);
			log.info("exit of getBatches method of TimeAllocationImpl");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in getBatches method of TimeAllocationImpl "
					+ e.getMessage());
		}
		return new ArrayList<Batch>();
	}

}