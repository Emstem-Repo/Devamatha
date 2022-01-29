package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamSettingCourseBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;
@SuppressWarnings("unchecked")
public class ExamSettingCourseImpl extends ExamGenImpl {
	private static final Log log = LogFactory
			.getLog(ExamSettingCourseImpl.class);

	public void delete(int examSettingsCourseId) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String hql = "delete from ExamSettingCourseRevaluationBO where examSettingsCourseId = :examSettingsCourseId";
			Query query = session.createQuery(hql);
			query.setParameter("examSettingsCourseId", examSettingsCourseId);
			query.executeUpdate();

			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/*
	 * select active objects of Class name passed
	 */
	
	public List<Object> select() throws Exception {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamSettingCourseBO.class);
			crit.add(Restrictions.eq("isActive", true));

			list = crit.list();

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}

	public ExamSettingCourseBO getExamSettingCourseBO(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamSettingCourseBO objBO = (ExamSettingCourseBO) session.get(
					ExamSettingCourseBO.class, id);
			session.flush();
			//session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	// update the row when course id is not present in the ExamSettingCourseBO
	public void updateESC(ExamSettingCourseBO eBO) throws Exception {
		ExamSettingCourseBO tempBO;
		Session session = null;
		List<Object> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			// get the course ID
			Criteria crit = session.createCriteria(ExamSettingCourseBO.class);
			crit.add(Restrictions.eq("id", eBO.getId()));
			crit.setMaxResults(1);
			list = crit.list();
			tempBO = (ExamSettingCourseBO) list.get(0);

			// set course id and save
			eBO.setCourseId(tempBO.getCourseId());
			session.save(eBO);

			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	public boolean isDuplicated(List<Integer> courseIdList) throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		Criteria crit = session.createCriteria(ExamSettingCourseBO.class);
		crit.add(Restrictions.in("courseId", courseIdList));
		List<ExamSettingCourseBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ExamSettingCourseBO objbo = (ExamSettingCourseBO) it.next();

				if (objbo.getIsActive() == true) {
					throw new DuplicateException();
				} else {
					throw new ReActivateException(objbo.getId());
				}
			}
		}
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return duplicate;
	}

	public boolean isDuplicated(int courseId, int id) throws Exception {
		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		Criteria crit = session.createCriteria(ExamSettingCourseBO.class);
		crit.add(Restrictions.eq("courseId", courseId));
		List<ExamSettingCourseBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ExamSettingCourseBO objbo = (ExamSettingCourseBO) it.next();

				if (id != -1 && objbo.getId() != id) {
					if (objbo.getIsActive() == true) {
						throw new DuplicateException();
					} else {
						throw new ReActivateException(objbo.getId());
					}

				}
			}

		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return duplicate;
	}

	/*
	 * Insert a object into respective tables
	 */
	public int insert(ExamSettingCourseBO obj) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {
			session.save(obj);
			tx.commit();
			id = obj.getId();

			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return id;
	}
	
	public List<Object> select_ActiveOnly(Class className,String orderBy) throws Exception {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("isActive", true));
			crit.addOrder(Order.asc(orderBy));

			list = crit.list();
            
			session.flush();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}


}
