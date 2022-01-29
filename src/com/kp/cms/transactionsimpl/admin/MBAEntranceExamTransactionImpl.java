package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.MBAEntranceExamForm;
import com.kp.cms.transactions.admin.IMBAEntranceExamTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class MBAEntranceExamTransactionImpl implements IMBAEntranceExamTransaction {

	public static volatile MBAEntranceExamTransactionImpl obj;
	
	private MBAEntranceExamTransactionImpl() {
		
	}
	
	public static MBAEntranceExamTransactionImpl getInstance() {
		if(obj == null) {
			obj = new MBAEntranceExamTransactionImpl();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public List<MBAEntranceExam> getMBAEntranceExams() throws Exception {
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from MBAEntranceExam c where isActive = 1 order by c.name");
			List<MBAEntranceExam> mbaEntranceExams = query.list();
			return mbaEntranceExams;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			if(session != null) {
				session.flush();
			}
			throw new ApplicationException();
		}
	}
	
	public boolean addMBAEntranceExam(MBAEntranceExam mbaEntranceExam, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(mode.equalsIgnoreCase("Add")) {
				session.save(mbaEntranceExam);
			}
			else if(mode.equalsIgnoreCase("Edit")) {
				session.update(mbaEntranceExam);
			}
			tx.commit();
			session.flush();
			return true;
		}
		catch(ConstraintViolationException e) {
			e.printStackTrace();
			if(tx!=null)
				tx.rollback();
			throw new BusinessException(e);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(tx!=null)
			     tx.rollback();
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleteMBAEntranceExam(int dupId, boolean activate, MBAEntranceExamForm mbaEntranceExamsForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			MBAEntranceExam mbaEntranceExam = (MBAEntranceExam) session.get(MBAEntranceExam.class, dupId);
			if(activate) {
				mbaEntranceExam.setIsActive(true);
			}
			else {
				mbaEntranceExam.setIsActive(false);
			}
			mbaEntranceExam.setModifiedBy(mbaEntranceExamsForm.getUserId());
			mbaEntranceExam.setLastModifiedDate(new Date());
			session.update(mbaEntranceExam);
			tx.commit();
			session.flush();
			return true;
		}
		catch(ConstraintViolationException e) {
			e.printStackTrace();
			if(tx!=null)
				tx.rollback();
			throw new BusinessException(e);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(tx!=null)
			     tx.rollback();
			throw new ApplicationException(e);
		}
	}
	
	public MBAEntranceExam isDuplicate(MBAEntranceExam oldConcessionType) throws Exception {
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from MBAEntranceExam where name = :name");
			query.setString("name", oldConcessionType.getName());
			MBAEntranceExam mbaEntranceExam = (MBAEntranceExam) query.uniqueResult();
			session.flush();
			return mbaEntranceExam;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
}
