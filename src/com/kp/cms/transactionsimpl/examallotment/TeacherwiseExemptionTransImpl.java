package com.kp.cms.transactionsimpl.examallotment;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.examallotment.ITeacherwiseExemptionTrans;
import com.kp.cms.utilities.HibernateUtil;

public class TeacherwiseExemptionTransImpl implements ITeacherwiseExemptionTrans {
	private static volatile TeacherwiseExemptionTransImpl teacherwiseExemptionTransImpl=null;
	/**
	 * instance()
	 * @return
	 */
	private TeacherwiseExemptionTransImpl(){
		
	}
	public static TeacherwiseExemptionTransImpl getInstance(){
		if(teacherwiseExemptionTransImpl == null){
			teacherwiseExemptionTransImpl=new TeacherwiseExemptionTransImpl();
		}
		return teacherwiseExemptionTransImpl;
	}
	@Override
	public List<ExamInviligatorExemptionDatewise> getTeachersList(String query)
			throws Exception {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<ExamInviligatorExemptionDatewise> list = query1.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public ExamInviligatorExemptionDatewise getExamInviligatorExemptionDatewise(
			int id) throws Exception {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("From ExamInviligatorExemptionDatewise e where e.isActive=1 and e.id="+id);
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise =(ExamInviligatorExemptionDatewise) query.uniqueResult();
			session.flush();
			return examInviligatorExemptionDatewise;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean update(
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise1)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.update(examInviligatorExemptionDatewise1);
			tx.commit();
			session.flush();
			flag=true;
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return flag;
	}
	@Override
	public boolean update(List<ExamInviligatorExemptionDatewise> list,
			String string) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				Iterator<ExamInviligatorExemptionDatewise> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamInviligatorExemptionDatewise examInvigilatorDuties = (ExamInviligatorExemptionDatewise) iterator.next();
					if(string.equalsIgnoreCase("update")){
						session.merge(examInvigilatorDuties);
					}else if(string.equalsIgnoreCase("add")){
						session.save(examInvigilatorDuties);
					}
				}
				tx.commit();
				session.flush();
				session.close();
				flag = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return flag;
	}
}
