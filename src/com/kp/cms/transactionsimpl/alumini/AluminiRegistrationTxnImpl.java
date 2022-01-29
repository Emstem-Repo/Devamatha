package com.kp.cms.transactionsimpl.alumini;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.alumini.AlumniRegistrationDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.alumini.IAluminiRegistrationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AluminiRegistrationTxnImpl implements IAluminiRegistrationTransaction {

	private static volatile AluminiRegistrationTxnImpl obj;
	
	private AluminiRegistrationTxnImpl() {
		
	}
	
	public static AluminiRegistrationTxnImpl getInstance() {
		if(obj == null) {
			obj = new AluminiRegistrationTxnImpl();
		}
		return obj;
	}
	
	public boolean saveAlimniRegistartion(AlumniRegistrationDetails alumniRegistrationDetails) throws Exception {
		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.save(alumniRegistrationDetails);
			tx.commit();
			session.flush();
			session.close();
			return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if(tx != null) {
				tx.rollback();
			}
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
	
	public boolean isPhoneNumberDuplicate(String mobileNumber) throws Exception {
		
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from AlumniRegistrationDetails reg where reg.mobileNumber = :mobileNumber")
								 .setString("mobileNumber", mobileNumber);
			return !(query.list().isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
}
