package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ManageTimeTableImpl extends ExamGenImpl {

	private static Log log = LogFactory.getLog(ManageTimeTableImpl.class);
	private static ManageTimeTableImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private ManageTimeTableImpl() {

	}

	public static ManageTimeTableImpl getInstance() {
		if (impl == null) {
			return new ManageTimeTableImpl();
		}
		return impl;
	}

	public List<TimeAllocationBo> getBottomGrid(int classes, int academicYear) {
		log.info("Entering into getBottomGrid method of ManageTimeTableImpl");
		// Session session = sessionFactory.openSession();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.classId.id= :classes and bo.academicYear= :academicYear");
			query.setParameter("classes", classes);
			query.setParameter("academicYear", academicYear);
			log.info("exit of getBottomGrid method of ManageTimeTableImpl");
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getBottomGrid method of ManageTimeTableImpl "
							+ e.getMessage());
		}
		return new ArrayList<TimeAllocationBo>();
	}

	public List<Object[]> getTeachingStaff(int academicYear, int classes) {
		log
				.info("Entering into getDetailsOfStaff method of ManageTimeTableImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("select distinct bo.teachingStaffId.id,bo.teachingStaffId.firstName"
							+ " from StaffAllocationBo bo where bo.academicYear= :academicYear and bo.classSchemeWise.id= :classes");
			query.setParameter("classes", classes);
			query.setParameter("academicYear", academicYear);
			log.info("exit of getDetailsOfStaff method of ManageTimeTableImpl");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			log
					.error("Exception in getDetailsOfStaff method of ManageTimeTableImpl "
							+ e.getMessage());
		}
		return new ArrayList<Object[]>();
	}

	public List<Batch> getBatches(String[] classes, int subjectId) {
		log.info("Entering into getBatches method of ManageTimeTableImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery(" from Batch batch where batch.subject.id= :subjectId and batch.classSchemewise.classes.id in( :classes )");
			query.setParameter("subjectId", subjectId);
			query.setParameterList("classes", classes);
			log.info("exit of getBatches method of ManageTimeTableImpl");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in getBatches method of ManageTimeTableImpl "
					+ e.getMessage());
		}
		return new ArrayList<Batch>();
	}

	public List<Object[]> getSubjects(Integer classSchemWiseId,
			Integer academicYear) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("select s.subject.id,s.subject.name from ClassSchemewise cs join "
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

	public Map<Integer, String> getRoomsByClassSchemewiseIds(
			Integer classSchemeWiseId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.classId.id= :classSchemeWiseId");
			query.setParameter("classSchemeWiseId", classSchemeWiseId);
			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
			List list = query.list();
			Iterator<TimeAllocationBo> periodIterator = list.iterator();
			while (periodIterator.hasNext()) {
				TimeAllocationBo bo = (TimeAllocationBo) periodIterator.next();
				ExamRoomMasterBO roomMasterBO = bo.getRoomId();
				periodMap.put(roomMasterBO.getId(), roomMasterBO.getRoomNo());
			}
			return periodMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	public List<Object[]> getPeriodsList(int classes) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("select period.id,period.periodName from Period period "
							+ "where period.classSchemewise.id = :classSchemWiseId "
							+ "and period.isActive=1 order by period.periodName");
			query.setParameter("classSchemWiseId", classes);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object[]>();
	}

	public List<TimeAllocationBo> getTimeAllocatedList(int classes,
			int academicYear) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.academicYear= :academicYear and bo.classId.id= :classSchemWiseId");
			query.setParameter("classSchemWiseId", classes);
			query.setParameter("academicYear", academicYear);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TimeAllocationBo>();
	}

}