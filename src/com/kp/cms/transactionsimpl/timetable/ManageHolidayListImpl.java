package com.kp.cms.transactionsimpl.timetable;

import java.util.ArrayList;
import java.util.Date;
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

import com.kp.cms.bo.admin.ManageHolidayListBo;
import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ManageHolidayListImpl extends ExamGenImpl {

	private static Log log = LogFactory.getLog(ManageHolidayListImpl.class);
	private static ManageHolidayListImpl impl;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private ManageHolidayListImpl() {

	}

	public static ManageHolidayListImpl getInstance() {
		if (impl == null) {
			return new ManageHolidayListImpl();
		}
		return impl;
	}

	public List<ManageHolidayListBo> getBottomGrid() {
		log.info("Entering into getBottomGrid method of ManageHolidayListImpl");
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session
					.createCriteria(ManageHolidayListBo.class);
			criteria.add(Restrictions.eq("isActive", true));
			log.info("exit of getBottomGrid method of ManageHolidayListImpl");
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.close();
			}
			log
					.error("Exception in getBottomGrid method of ManageHolidayListImpl "
							+ e.getMessage());
		}
		return new ArrayList<ManageHolidayListBo>();
	}

	public ManageHolidayListBo getEditDetails(Integer id) {
		log.info("Entering into edit method of ManageHolidayListImpl");
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session
					.createCriteria(ManageHolidayListBo.class);
			criteria.add(Restrictions.eq("id", id));
			return (ManageHolidayListBo) criteria.uniqueResult();
		} catch (Exception e) {
			log.error("Exception in edit method of ManageHolidayListImpl "
					+ e.getMessage());
		}
		return new ManageHolidayListBo();
	}

	public int delete(Integer id) {
		log.info("Entering into delete method of ManageHolidayListImpl");
		Session session = sessionFactory.openSession();
		try {
			Transaction tr = session.getTransaction();
			tr.begin();
			Query query = session
					.createQuery("delete from ManageHolidayListBo bo where bo.id= :id");
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

	public int isDuplicated(Date startDate, Date endDate) {
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(ManageHolidayListBo.class);
		crit.add(Restrictions.eq("isActive", true));
		List<ManageHolidayListBo> list = crit.list();
		if (list != null) {
			Iterator<ManageHolidayListBo> it = list.iterator();
			while (it.hasNext()) {
				ManageHolidayListBo bo = (ManageHolidayListBo) it.next();

			}
		}
		return 0;
	}

}