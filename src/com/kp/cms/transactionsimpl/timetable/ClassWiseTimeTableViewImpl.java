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

public class ClassWiseTimeTableViewImpl extends ExamGenImpl{

	private static Log log = LogFactory.getLog(ClassWiseTimeTableViewImpl.class);
	private static ClassWiseTimeTableViewImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private ClassWiseTimeTableViewImpl() {

	}

	public static ClassWiseTimeTableViewImpl getInstance() {
		if (impl == null) {
			return new ClassWiseTimeTableViewImpl();
		}
		return impl;
	}
	
	public List<TimeAllocationBo> getClassTimeTable(int classId,
			int academicYear) {
		log
				.info("Entering into getClassTimeTable method of ClassWiseTimeTableViewImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.academicYear=:academicYear "
							+ "and bo.classId= :classId order by bo.day");
			query.setInteger("classId", classId);
			query.setInteger("academicYear", academicYear);
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getClassTimeTable method of ClassWiseTimeTableViewImpl "
							+ e.getMessage());
		}
		return new ArrayList<TimeAllocationBo>();

	}
}
