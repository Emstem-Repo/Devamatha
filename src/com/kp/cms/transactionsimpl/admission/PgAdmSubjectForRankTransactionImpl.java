package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admission.PgAdmSubjectForRank;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IAdmSubjectForRank;
import com.kp.cms.transactions.admission.IPgAdmSubjectForRank;
import com.kp.cms.utilities.HibernateUtil;

public class PgAdmSubjectForRankTransactionImpl implements IPgAdmSubjectForRank {

	@Override
	public List<PgAdmSubjectForRank> getSubject() {
		Session session = null;
		List<PgAdmSubjectForRank> subjectlist = null;
		try {
			session = HibernateUtil.getSession();
			subjectlist = session.createQuery("from PgAdmSubjectForRank a where a.isActive=1").list();
			
		} catch (Exception e) {
			if (session != null) {
				//session.flush();
			}
		}
		
		return subjectlist;
	}

	@Override
	public boolean add(PgAdmSubjectForRank admsbjctbo) {
		Session session = null; 
		Transaction transaction = null;
		 try {
			 session = HibernateUtil.getSession();
			 transaction = session.beginTransaction();
				transaction.begin();
				session.save(admsbjctbo);
				transaction.commit();
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

	@Override
	public List<PgAdmSubjectForRank> isDuplicated(List<PgAdmSubjectForRank> admsbjctbo) throws Exception {
		Session session = null;
		
		Iterator<PgAdmSubjectForRank> itr=admsbjctbo.iterator();
		List<PgAdmSubjectForRank> ranklist=null;
		int c=0;
		int i=1;
		String selectedids="";
		String programID="";
		String courseID="";
		while(itr.hasNext()){
			PgAdmSubjectForRank admsbjctbo1=itr.next();
			c++; 
			programID=new Integer(admsbjctbo1.getProgram().getId()).toString();
			courseID=new Integer(admsbjctbo1.getCourse().getId()).toString();
			if(c==i){
				selectedids=new Integer(admsbjctbo1.getuGCoursesBO().getId()).toString();
			}
			else{
				selectedids=selectedids+","+new Integer(admsbjctbo1.getuGCoursesBO().getId()).toString();
				
			}
			}
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PgAdmSubjectForRank a where a.program.id = "+programID+" and  a.course.id = "+courseID+" and  a.uGCoursesBO.id in ("+selectedids+")");
	       
			ranklist =  query.list();
	        session.flush();
		} catch (Exception e) {
			session.flush();
			
			throw new ApplicationException(e);
		}
		finally{
			session.flush();
		}
		return ranklist;
		
	}

	@Override
	public boolean edit(PgAdmSubjectForRank admsbjctbo) {
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
			PgAdmSubjectForRank admsbjctbo = (PgAdmSubjectForRank) session.get(PgAdmSubjectForRank.class,id);
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
	public boolean reActivate(PgAdmSubjectForRank subj, String userId) {
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

	
	@Override
	public List<PgAdmSubjectForRank> isDuplicatedforUpdate(List<PgAdmSubjectForRank> admsbjctbo) throws Exception {
		Session session = null;
		
		Iterator<PgAdmSubjectForRank> itr=admsbjctbo.iterator();
		List<PgAdmSubjectForRank> ranklist=null;
		int c=0;
		int i=1;
		String selectedids="";
		String programID="";
		String courseID="";
		int id=0;
		while(itr.hasNext()){
			PgAdmSubjectForRank admsbjctbo1=itr.next();
			id=admsbjctbo1.getId();
			selectedids=new Integer(admsbjctbo1.getuGCoursesBO().getId()).toString();
			c++;
			programID=new Integer(admsbjctbo1.getProgram().getId()).toString();
			courseID=new Integer(admsbjctbo1.getCourse().getId()).toString();
			if(c==i){
				selectedids=new Integer(admsbjctbo1.getuGCoursesBO().getId()).toString();
			}
			else
				selectedids=selectedids+","+new Integer(admsbjctbo1.getuGCoursesBO().getId()).toString();
		}
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PgAdmSubjectForRank a where a.program.id = "+programID+" and  a.course.id = "+courseID+" and  a.uGCoursesBO.id in ("+selectedids+") and a.id!="+id);
	       
			ranklist =  query.list();
	        session.flush();
		} catch (Exception e) {
			session.flush();
			
			throw new ApplicationException(e);
		}
		finally{
			session.flush();
		}
		return ranklist;
		
	}



}
