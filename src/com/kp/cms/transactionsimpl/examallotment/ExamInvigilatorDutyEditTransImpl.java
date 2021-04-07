package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.examallotment.ExamInvigilatorDutyExemption;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.examallotment.IExamInvigilatorDutyEditTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExamInvigilatorDutyEditTransImpl implements IExamInvigilatorDutyEditTransaction{
	private static volatile ExamInvigilatorDutyEditTransImpl examInvigilatorDutyEditTransImpl=null;
	/**
	 * instance()
	 * @return
	 */
	private ExamInvigilatorDutyEditTransImpl(){
		
	}
	public static ExamInvigilatorDutyEditTransImpl getInstance(){
		if(examInvigilatorDutyEditTransImpl == null){
			examInvigilatorDutyEditTransImpl=new ExamInvigilatorDutyEditTransImpl();
		}
		return examInvigilatorDutyEditTransImpl;
	}
	@Override
	public List<ExamInviligatorDuties> getInvigilators(String query)
			throws Exception {
		List<ExamInviligatorDuties> list1=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(query);
			list1=(List<ExamInviligatorDuties>)hqlQuery.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list1;
	}
	@Override
	public boolean updateInvigilator(ExamInviligatorDuties examInviligatorDuties) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.update(examInviligatorDuties);
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
	public ExamInviligatorDuties getExamInviligatorDutiesById(int id)
			throws Exception {
		ExamInviligatorDuties examInviligatorDuties=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery("from ExamInviligatorDuties e where e.id="+id);
			examInviligatorDuties=(ExamInviligatorDuties)hqlQuery.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return examInviligatorDuties;
	}
	@Override
	public List<ExamInviligatorDuties> getExamInvigilators(List<Integer> list)
			throws Exception {
		List<ExamInviligatorDuties> list1=new ArrayList<ExamInviligatorDuties>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamInviligatorDuties h where h.isActive=1  and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", list);
			list1=(List<ExamInviligatorDuties>)query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list1;
	}
	@Override
	public boolean update(List<ExamInviligatorDuties> examInviligatorDuties1,String string)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				Iterator<ExamInviligatorDuties> iterator=examInviligatorDuties1.iterator();
				ExamInviligatorDuties examInvigilatorDuties=null;
				while (iterator.hasNext()) {
					examInvigilatorDuties = (ExamInviligatorDuties) iterator.next();
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
