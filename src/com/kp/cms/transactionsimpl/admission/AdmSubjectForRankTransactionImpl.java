package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IAdmSubjectForRank;
import com.kp.cms.utilities.HibernateUtil;

public class AdmSubjectForRankTransactionImpl implements IAdmSubjectForRank {

	@Override
	public List<AdmSubjectForRank> getSubject() {
		Session session = null;
		List<AdmSubjectForRank> subjectlist = null;
		try {
			session = HibernateUtil.getSession();
			subjectlist = session.createQuery("from AdmSubjectForRank a where a.isActive=1").list();
			
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
		}
		
		return subjectlist;
	}

	@Override
	public boolean add(AdmSubjectForRank admsbjctbo) {
		Session session = null; 
		Transaction transaction = null;
		 try {
			 session = HibernateUtil.getSession();
			 transaction = session.beginTransaction();
				transaction.begin();
				session.save(admsbjctbo);
				transaction.commit();
				session.flush();
				return true;
		} catch (Exception e) {
			 if(transaction != null){
				transaction.rollback(); 
			 }
			 if(session != null){
				 session.flush();
			 }
			 return false;
		}	
	}

	@Override
	public AdmSubjectForRank isDuplicated(AdmSubjectForRank admsbjctbo) throws Exception {
		Session session = null;
		AdmSubjectForRank admsbjctbo1 = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AdmSubjectForRank a where name = :name and  groupName = :groupname and  stream = :stream");
	        query.setString("name", admsbjctbo.getName());
	        query.setString("groupname", admsbjctbo.getGroupName());
	        query.setString("stream", admsbjctbo.getStream());
	        admsbjctbo1 = (AdmSubjectForRank) query.uniqueResult();
	        session.flush();
		} catch (Exception e) {
			session.flush();
			
			throw new ApplicationException(e);
		}
		finally{
			session.flush();
		}
		return admsbjctbo1;
		
	}

	@Override
	public boolean edit(AdmSubjectForRank admsbjctbo) {
		 Session session = null;
		 Transaction transaction = null;
		 try{
			 session = HibernateUtil.getSession();
			 transaction = session.beginTransaction();
			 transaction.begin();
			 session.merge(admsbjctbo);
			 transaction.commit();
			 session.flush();
			 return true;
			
			 }catch (Exception e) {
				 if(transaction != null){
						transaction.rollback(); 
					 }
					 if(session != null){
						 session.flush();
					 }
					 return false;	
			}
		
	}

	@Override
	public boolean delete(int id, String userId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			AdmSubjectForRank admsbjctbo = (AdmSubjectForRank) session.get(AdmSubjectForRank.class,id);
			admsbjctbo.setIsActive(false);
			admsbjctbo.setModifiedBy(userId);
			session.update(admsbjctbo);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback(); 
			 }
			 if(session != null){
				 session.flush();
			 }
			 return false;	
		}
		
	}

	@Override
	public boolean reActivate(AdmSubjectForRank subj, String userId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			subj.setLastModifiedDate(new Date());
			subj.setModifiedBy(userId);
			subj.setIsActive(true);
			session.update(subj);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback(); 
			 }
			 if(session != null){
				 session.flush();
			 }
			 return false;	
		}
	}
	
	

}
