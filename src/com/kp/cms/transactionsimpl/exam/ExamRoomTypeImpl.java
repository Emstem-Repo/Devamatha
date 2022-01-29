package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamRoomTypeImpl extends ExamGenImpl {

	public void updateExamRoomType(int ertid, String name, String desc,
			String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamRoomTypeBO objBO = (ExamRoomTypeBO) session.get(
					ExamRoomTypeBO.class, ertid);
			objBO.setName(name);
			objBO.setDesc(desc);
			objBO.setModifiedBy(userId);
			objBO.setLastModifiedDate(new Date());
			session.update(objBO);
			transaction.commit();
			session.flush();
			session.close();

		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	public boolean isDuplicated(String name, int id) throws Exception {
		boolean notDuplicate = true;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(ExamRoomTypeBO.class);
		crit.add(Restrictions.eq("name", name));
		crit.setMaxResults(1);
		List<ExamRoomTypeBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamRoomTypeBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamRoomTypeBO) it.next();
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
		return notDuplicate;
	}

}
