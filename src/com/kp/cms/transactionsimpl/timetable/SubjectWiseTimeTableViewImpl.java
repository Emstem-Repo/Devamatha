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

public class SubjectWiseTimeTableViewImpl extends ExamGenImpl{

	private static Log log = LogFactory.getLog(SubjectWiseTimeTableViewImpl.class);
	private static SubjectWiseTimeTableViewImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private SubjectWiseTimeTableViewImpl() {

	}

	public static SubjectWiseTimeTableViewImpl getInstance() {
		if (impl == null) {
			return new SubjectWiseTimeTableViewImpl();
		}
		return impl;
	}
	
	public List<TimeAllocationBo> getSubjectWiseTimeTable(int subjectId,int academicYear) {
		log
				.info("Entering into getSubjectWiseTimeTable method of SubjectWiseTimeTableViewImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from TimeAllocationBo bo where bo.academicYear=:academicYear "
							+ "and bo.preferredSubjectId= :subjectId order by bo.day");
			query.setInteger("subjectId", subjectId);
			query.setInteger("academicYear", academicYear);
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getSubjectWiseTimeTable method of SubjectWiseTimeTableViewImpl "
							+ e.getMessage());
		}
		return new ArrayList<TimeAllocationBo>();

	}
}
