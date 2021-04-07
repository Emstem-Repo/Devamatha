package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.StaffAllocationBo;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class StaffAllocationImpl extends ExamGenImpl {

	private static Log log = LogFactory.getLog(StaffAllocationImpl.class);
	private static StaffAllocationImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private StaffAllocationImpl() {

	}

	public static StaffAllocationImpl getInstance() {
		if (impl == null) {
			return new StaffAllocationImpl();
		}
		return impl;
	}

	public List<StaffAllocationBo> getBottomGrid() {
		log.info("Entering into getBottomGrid method of StaffAllocationImpl");
		// Session session = sessionFactory.openSession();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session.createQuery("from StaffAllocationBo bo");
			log.info("exit of getBottomGrid method of StaffAllocationImpl");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getBottomGrid method of StaffAllocationImpl "
							+ e.getMessage());
		}
		return new ArrayList<StaffAllocationBo>();
	}

	public List<Users> getTeachingStaffList() {

		log
				.info("Entering into TeachingStaffList method of StaffAllocationImpl");
		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("from Users u where u.isTeachingStaff=1 and u.isActive=1");
			log.info("exit of TeachingStaffList method of StaffAllocationImpl");
			return query.list();
		} catch (Exception e) {
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getTeachingStaffList method of StaffAllocationImpl "
							+ e.getMessage());
		}
		return new ArrayList<Users>();
	}

	public int delete(int id) {
		log.info("Entering into Delete method of StaffAllocationImpl");
		Session session = sessionFactory.openSession();
		try {
			Transaction tr = session.getTransaction();
			tr.begin();
			Query query = session
					.createQuery("delete from StaffAllocationBo bo where bo.id= :id");
			query.setParameter("id", id);
			int deleted = query.executeUpdate();
			tr.commit();
			return deleted;
		} catch (Exception e) {
			log.error("Exception in Deleting Record of StaffAllocationImpl "
					+ e.getMessage());
		}
		return 0;
	}

	public boolean checkDuplicateValue(Employee emp, int academicYear,
			Subject subject) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(StaffAllocationBo.class);
		criteria.add(Restrictions.eq("academicYear", academicYear));
		criteria.add(Restrictions.eq("teachingStaffId", emp));
		criteria.add(Restrictions.eq("preferredSubjectId", subject));
		List list = criteria.list();
		if (list != null && list.size() > 0)
			return false;
		return true;
	}

}