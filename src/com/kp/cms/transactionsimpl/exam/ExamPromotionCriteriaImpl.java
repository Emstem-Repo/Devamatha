package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.exam.ExamPromotionCriteriaBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamPromotionCriteriaImpl extends ExamGenImpl {

	public void isDuplicated(int courseId, String fromSchemeId,
			String toSchemeId, String scheme) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamPromotionCriteriaBO.class);
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("fromSchemeId", Integer
					.parseInt(fromSchemeId)));
			crit.add(Restrictions
					.eq("toSchemeId", Integer.parseInt(toSchemeId)));
			crit.add(Restrictions.eq("scheme", scheme));
			List<ExamPromotionCriteriaBO> list = crit.list();

			if (list.size() > 0) {

				throw new DuplicateException();

			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}

	}

	public void update_ExamPromotionCriteria(int id, int courseId,
			int fromSchemeId, int toSchemeId, String scheme,
			String maxBacklogRadio, String maxBacklogValue) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "update ExamPromotionCriteriaBO p set p.courseId = :courseId, "
				+ " p.fromSchemeId = :fromSchemeId,"
				+ " p.toSchemeId = :toSchemeId,"
				+ " p.scheme = :scheme, p.maxBacklogCountPrcntg = :maxBacklogCountPrcntg,"
				+ " p.maxBacklogNumber=:maxBacklogNumber"
				+ "  where p.id = :id";

		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("courseId", courseId);
		query.setParameter("fromSchemeId", fromSchemeId);
		query.setParameter("toSchemeId", toSchemeId);
		query.setParameter("scheme", scheme);
		if (maxBacklogRadio.equalsIgnoreCase("percentage")) {
			query.setParameter("maxBacklogCountPrcntg", new BigDecimal(
					maxBacklogValue));
			query.setParameter("maxBacklogNumber", 0);
		} else {
			query.setParameter("maxBacklogCountPrcntg", new BigDecimal("0"));
			query.setParameter("maxBacklogNumber", Integer
					.parseInt(maxBacklogValue));
		}
		query.executeUpdate();
		tx.commit();

	}

	public boolean delete_ExamPromotionCriteria(int id) {

		String HQL_QUERY = "delete from ExamPromotionCriteriaBO p"
				+ " where p.id = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		int i = query.executeUpdate();
		tx.commit();
		// session.close();
		return (i > 0) ? true : false;
	}

	public ExamPromotionCriteriaBO loadExamPromotionCriteria(int id)
			throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamPromotionCriteriaBO objBO = (ExamPromotionCriteriaBO) session
					.get(ExamPromotionCriteriaBO.class, id);
			session.flush();
			// session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {

			session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	public void isDuplicated(int courseId, int fromSchemeId, int toSchemeId,
			String schemeStr) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamPromotionCriteriaBO.class);
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("fromSchemeId", fromSchemeId));
			crit.add(Restrictions.eq("toSchemeId", toSchemeId));
			crit.add(Restrictions.eq("scheme", schemeStr));
			List<ExamPromotionCriteriaBO> list = crit.list();

			if (list.size() > 0) {

				throw new DuplicateException();

			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}
	}
	
	public HashMap<Integer, String> getCourseMap() throws Exception {
		Session session=null;
		HashMap<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Course course where course.isActive = 1 and course.program.isActive=1 and course.onlyForApplication=0 order by course.name asc");
			List<Course> list=query.list();
			if(list!=null){
				Iterator<Course> iterator=list.iterator();
				while(iterator.hasNext()){
					Course course=iterator.next();
					if(course.getId() !=0 && course.getName()!=null && !course.getName().isEmpty())
					map.put(course.getId(),course.getName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
}
