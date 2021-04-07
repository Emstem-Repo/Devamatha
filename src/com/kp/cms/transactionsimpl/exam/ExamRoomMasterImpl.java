package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamRoomMasterImpl extends ExamGenImpl {

	/**
	 *checking for duplication && Reactivation
	 * 
	 *Room No for a particular room type cannot be duplicated
	 */
	public boolean isDuplicated_add(String roomNo, int roomTypeId)
			throws Exception {
		Session session = null;
		boolean duplicate = false;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(ExamRoomMasterBO.class);
		crit.add(Restrictions.eq("roomNo", roomNo));
		crit.add(Restrictions.eq("roomTypeId", roomTypeId));
		crit.setMaxResults(1);
		List<ExamRoomMasterBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ExamRoomMasterBO objbo = (ExamRoomMasterBO) it.next();
				if (objbo.getIsActive() == true) {
					throw new DuplicateException();
				} else {
					throw new ReActivateException(objbo.getId());
				}
			}
		}

		session.flush();
		session.close();

		return duplicate;
	}

	public boolean isDuplicated(String roomNo, int roomTypeId, int id)
			throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(ExamRoomMasterBO.class);
		crit.add(Restrictions.eq("roomNo", roomNo));
		crit.add(Restrictions.eq("roomTypeId", roomTypeId));
		crit.setMaxResults(1);
		List<ExamRoomMasterBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamRoomMasterBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamRoomMasterBO) it.next();
				if (objbo.getId() != id) {
					if (objbo.getIsActive() == true) {
						throw new DuplicateException();
					} else {
						throw new ReActivateException(objbo.getId());
					}
				}
			}
		}
		session.flush();
		session.close();
		return duplicate;
	}

	public ExamRoomMasterBO loadRoomMaster(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamRoomMasterBO objERMBO = (ExamRoomMasterBO) session.get(
					ExamRoomMasterBO.class, id);
			session.flush();
			// session.close();
			return objERMBO != null ? objERMBO : null;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean update(ExamRoomMasterBO objERMBO) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(objERMBO);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	public ExamRoomMasterBO select_ByRoomNo(int roomNo) throws Exception {
		Session session = null;
	
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(ExamRoomMasterBO.class);
		crit.add(Restrictions.eq("roomNo", roomNo));
		crit.setMaxResults(1);
		List<ExamRoomMasterBO> list = crit.list();

		session.flush();
		//session.close();
		return (ExamRoomMasterBO) list.get(0);
	}

}
