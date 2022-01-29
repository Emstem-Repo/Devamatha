package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelDamageForm;
import com.kp.cms.transactions.hostel.IHostelDamageTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelDamageTransactionImpl implements IHostelDamageTransaction
{
	private static final Log log = LogFactory.getLog(HostelDamageTransactionImpl.class);
	
	public static volatile HostelDamageTransactionImpl hostelDamageTransactionImpl = null;
	
	public static HostelDamageTransactionImpl getInstance() {
		if (hostelDamageTransactionImpl == null) {
			hostelDamageTransactionImpl = new HostelDamageTransactionImpl();
			return hostelDamageTransactionImpl;
		}
		return hostelDamageTransactionImpl;
	}
	
	public HlDamage getHostelDamageById(int hostelDamageId)
			throws ApplicationException {
	log
	.info("entering into getHostelDamageById of HostelDamageTransactionImpl class.");
	Session session = null;
	HlDamage hostelDamage = null;
	try {
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		//session = sessionFactory.openSession();
		session = HibernateUtil.getSession();
		Query hostelDamageQuery = session
				.createQuery(" from HlDamage hostelDamage "
		+ " where hostelDamage.id = :hostelDamageId");
		hostelDamageQuery.setInteger("hostelDamageId",
					hostelDamageId);
		hostelDamage = (HlDamage) hostelDamageQuery
				.uniqueResult();
	 } catch (Exception e) {
		log.info("error in getHostelDamageById of HostelDamageTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	   log
			.info("exit of getHostelDamageById of HostelDamageTransactionImpl class.");
	   return hostelDamage;
	}
	
	
	public boolean addHostelDamageEntry(HlDamage hostelDamage) throws Exception
	{
		log.info("start of addHostelDamageEntry in HostelDamageTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		Integer billNo=0;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			billNo=(Integer)session.createQuery("select max(d.billNo) from HlDamage d where d.isActive=1").uniqueResult();
			if(billNo==null || billNo==0){
				billNo=1;
			}else{
				billNo=billNo+1;
			}
			hostelDamage.setBillNo(billNo);
			session.save(hostelDamage);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to save hostelDamage" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addHostelDamageEntry in HostelDamageTransactionImpl class.");
		return isAdded;		
	}
	
	//get the details based on query
	public HlApplicationForm getHlapplicationByQuery(String query)
			throws Exception {
		Session session = null;
		HlApplicationForm hlApplicationForm = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query hostelDamageQuery=session.createQuery(query);
			hlApplicationForm =(HlApplicationForm) hostelDamageQuery.uniqueResult();
			return hlApplicationForm;
		} catch (Exception e) {
			log.error("Error while HlapplicationByQuery." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	//based on hostel id and HlApp id getting the damage list
	
	public List<HlDamage> getHostelDamagesByHlappId(int hlAppId, int hostelId)
			throws Exception {
		Session session = null;
		List<HlDamage> damageList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from HlDamage h where h.hlHostel.id=:hostelId and h.hlApplicationForm.id=:hlAppId and h.isActive=1");
			query.setInteger("hostelId", hostelId);
			query.setInteger("hlAppId", hlAppId);
			damageList =query.list();
			return damageList;
		} catch (Exception e) {
			log.error("Error while HlapplicationByQuery." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	public boolean deleteHostelDamage(int id,HostelDamageForm hostelDamageForm) throws Exception
	{
		log.info("call of deleteHostelDamage in HostelDamageTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			HlDamage hlDamage = (HlDamage) session.get(HlDamage.class, id);
			hlDamage.setLastModifiedDate(new Date());
			hlDamage.setModifiedBy(hostelDamageForm.getUserId());
			hlDamage.setIsActive(Boolean.FALSE);
			session.update(hlDamage);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete Hostel Damage Entry" ,e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteHostelDamage in HostelDamageTransactionImpl class.");
		return isDeleted;
	}
}
