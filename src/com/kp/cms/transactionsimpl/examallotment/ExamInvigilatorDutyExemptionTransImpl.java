package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.bo.examallotment.ExamInvigilatorDutyExemption;
import com.kp.cms.bo.examallotment.ExamInviligatorExemption;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.examallotment.IExamInvigilatorDutyExemptionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExamInvigilatorDutyExemptionTransImpl implements IExamInvigilatorDutyExemptionTransaction{
	private static volatile ExamInvigilatorDutyExemptionTransImpl examInvigilatorDutyExemptionTransImpl=null;
	/**
	 * instance()
	 * @return
	 */
	public static ExamInvigilatorDutyExemptionTransImpl getInstance(){
		if(examInvigilatorDutyExemptionTransImpl == null){
			examInvigilatorDutyExemptionTransImpl=new ExamInvigilatorDutyExemptionTransImpl();
		}
		return examInvigilatorDutyExemptionTransImpl;
	}
	public Map<Integer, String> getExemptionMap() throws Exception{
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<ExamInviligatorExemption> list=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamInviligatorExemption e where e.isActive=1";
			Query query =  session.createQuery(hqlQuery);
			list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExamInviligatorExemption> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamInviligatorExemption examInviligatorExemption = (ExamInviligatorExemption) iterator.next();
					map.put(examInviligatorExemption.getId(), examInviligatorExemption.getExemption());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.close();
		}
		return map;
	}
	@Override
	public List<ExamInvigilatorDutyExemption> getSearchedInvigilators(
			StringBuilder query) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query.toString());
			List<ExamInvigilatorDutyExemption> list = query1.list();
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
	public List<ExamInvigilatorDutyExemption> getExamInvigilators(
			List<Integer> therecordsExists) throws Exception {
		List<ExamInvigilatorDutyExemption> list1=new ArrayList<ExamInvigilatorDutyExemption>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamInvigilatorDutyExemption h where h.isActive=1  and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", therecordsExists);
			list1=(List<ExamInvigilatorDutyExemption>)query.list();
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
	public boolean updateInvigilators(
			List<ExamInvigilatorDutyExemption> examInvigilatorsToUpdate)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				Iterator<ExamInvigilatorDutyExemption> iterator=examInvigilatorsToUpdate.iterator();
				while (iterator.hasNext()) {
					ExamInvigilatorDutyExemption examInvigilatorDutyExemption = (ExamInvigilatorDutyExemption) iterator.next();
					session.update(examInvigilatorDutyExemption);
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
	@Override
	public boolean saveInvigilators(
			List<ExamInvigilatorDutyExemption> examInvigilatorDutyExemptions1)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<ExamInvigilatorDutyExemption> iterator=examInvigilatorDutyExemptions1.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorDutyExemption examInvigilatorDutyExemption = (ExamInvigilatorDutyExemption) iterator.next();
				session.save(examInvigilatorDutyExemption);
			}
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
}
}
