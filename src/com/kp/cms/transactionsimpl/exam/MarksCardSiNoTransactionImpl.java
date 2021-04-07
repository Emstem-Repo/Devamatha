package com.kp.cms.transactionsimpl.exam;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.MarksCardSiNo;
import com.kp.cms.bo.exam.MarksCardSlNo;
import com.kp.cms.forms.exam.MarksCardSiNoForm;
import com.kp.cms.transactions.exam.IMarksCardSiNoTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class MarksCardSiNoTransactionImpl implements IMarksCardSiNoTransaction {
	
	
	public boolean save(MarksCardSlNo bo)throws Exception{
		Session session = null;
		Transaction tx=null;
		try {
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
				session.close();
			}
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MarksCardSlNo> getData()throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<MarksCardSlNo> l =session.createQuery("from MarksCardSlNo bo where bo.isActive=1 order by bo.academicYear, bo.scheme, bo.programType.id").list();
			session.flush();
			return l;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return null;
	}
	
	public boolean getDataAvailable(MarksCardSiNoForm cardSiNoForm)throws Exception{
		MarksCardSlNo bo = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			bo = (MarksCardSlNo)session.createQuery("from MarksCardSlNo bo where bo.isActive=1 and bo.academicYear="+cardSiNoForm.getAcademicYear()+" and bo.programType.id="+cardSiNoForm.getProgramTypeId()+" and bo.scheme="+cardSiNoForm.getSemister()).uniqueResult();
			session.flush();
			if(bo!=null){
				return true;
			}
		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return false;
	}
	
}
