package com.kp.cms.transactionsimpl.exam;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ConsolidateMarksCardSiNo;
import com.kp.cms.bo.exam.MarksCardSiNo;
import com.kp.cms.forms.exam.ConsolidateMarksCardSiNoForm;
import com.kp.cms.transactions.exam.IConsolidateMarksCardSiNoTransaction;
import com.kp.cms.transactions.exam.IMarksCardSiNoTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ConsolidateMarksCardSiNoTransactionImpl implements IConsolidateMarksCardSiNoTransaction {
	
	
	public boolean save(ConsolidateMarksCardSiNo bo)throws Exception{
		Session session = null;
		Transaction tx=null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(bo);
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}
	}
	
	public List<ConsolidateMarksCardSiNo> getData()throws Exception{
		Session session = null;
		List<ConsolidateMarksCardSiNo> list=null;
		
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			list = session.createQuery("from ConsolidateMarksCardSiNo bo where bo.isActive=1").list();
			if(list!=null){
				return list;
			}
//			session.flush();
//			session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return null;
	}
	
	public boolean getDataAvailable(ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm)throws Exception{
		ConsolidateMarksCardSiNo bo = null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ConsolidateMarksCardSiNo bo where bo.isActive=1 and bo.academicYear="+consolidateMarksCardSiNoForm.getYear()+" and bo.programType.id="+consolidateMarksCardSiNoForm.getProgramTypeId());
			bo=(ConsolidateMarksCardSiNo)query.uniqueResult();
			if(bo!=null){
				return true;
			}
//			session.flush();
//			session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}
		}
		return false;
	}
	
}
