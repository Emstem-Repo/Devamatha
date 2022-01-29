package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.bo.admin.HlGroupStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.HostelGroupForm;
import com.kp.cms.transactions.hostel.IHostelGroupTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelGroupTransactionImpl implements IHostelGroupTransactions {
	public static Log log = LogFactory.getLog(HostelGroupTransactionImpl.class);
	public static volatile HostelGroupTransactionImpl hostelGroupTransactionImpl;
	
	public static HostelGroupTransactionImpl getInstance(){
		if(hostelGroupTransactionImpl == null){
			hostelGroupTransactionImpl = new HostelGroupTransactionImpl();
			return hostelGroupTransactionImpl;
		}
		return hostelGroupTransactionImpl;
	}
	/**
	 * get all student
	 */
	public List<HlApplicationForm> getStudentDeatils(String query) throws Exception{
		log.debug("inside getStudentDeatils");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query);
			List<HlApplicationForm> list = query1.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getStudentDeatils");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getStudentDeatils...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	

	
	/* add method
	 * @param hlHostel
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addHostelGroup(HlGroup hlGroup, String mode) throws Exception {
		log.debug("inside addHostelGroup");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(hlGroup);
			}
			else
			{
				session.save(hlGroup);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addHostelGroup");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addHostelGroup..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addHostelGroup..." , e);
			throw new ApplicationException(e);
		}

	}
	/**
	 * getting all hostel groups
	 */
	public List<HlGroup> getHostelGroup(String searchQuery) throws Exception{
		log.debug("inside getStudentDeatils");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(searchQuery);
			List<HlGroup> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getHostelGroup");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getHostelGroup...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * getting all hostel groups by id
	 */
	public HlGroup getHostelGroupById(int id) throws Exception{
		log.debug("inside getStudentDeatils");
		Session session = null;
		HlGroup hlGroup;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlGroup h where h.isActive = 1 and id = " + "'" + id + "'");
			hlGroup = (HlGroup) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getHostelGroupById");
			return hlGroup;
		 } catch (Exception e) {
			 log.error("Error in getHostelGroupById...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * delete & reactivate
	 */
	public boolean deleteHostelGroup(int id, Boolean activate, HostelGroupForm hForm) throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();
		//session = sessionFactory.openSession();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		HlGroup hlGroup = (HlGroup) session.get(HlGroup.class, id);
		if (activate) {
			hlGroup.setIsActive(true);
		}else
		{
			hlGroup.setIsActive(false);
		}
		hlGroup.setModifiedBy(hForm.getUserId());
		hlGroup.setLastModifiedDate(new Date());
		session.update(hlGroup);
		tx.commit();
		session.flush();
		session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		tx.rollback();
		log.error("Error in deleteHostelEntry..." , e);
		throw new BusinessException(e);
	} catch (Exception e) {
		tx.rollback();
		log.error("Error in deleteHostelEntry.." , e);
		throw new ApplicationException(e);
	}
	return result;
	}
	

	/**
	 * duplication checking for Hostel group
	 */
	public HlGroup isHostelGroupDuplcated(String groupName, int hostelId, int id) throws Exception {
		log.debug("inside isHostelGroupDuplcated");
		Session session = null;
		HlGroup hlGroup;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlGroup h where h.name = :groupName and  h.hlHostel.id =" + "'" + hostelId + "'");
			if(id!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("groupName",  groupName);
			if(id!= 0){
				query.setInteger("id", id);
			}
			
			hlGroup = (HlGroup) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isHostelGroupDuplcated");
		return hlGroup;
	}
	
	/**
	 * duplication checking for Hostel group entry
	 */
	public boolean isHostelGroupStudentsDuplcated(int appFormId, int id) throws Exception {
		log.debug("inside isHostelGroupDuplcated");
		Session session = null;
		Boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlGroupStudent h where h.isActive = 1 and h.hlGroup.isActive = 1 and h.hlApplicationForm.id = " +  "'" + appFormId + "'");
			if(id!= 0){
				sqlString.append(" and h.hlGroup.id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			if(id!= 0){
				query.setInteger("id", id);
			}
			
			List<HlGroupStudent> hlStudentList = (List<HlGroupStudent>) query.list();
			if(hlStudentList!= null && hlStudentList.size() > 0){
				Iterator<HlGroupStudent> itr = hlStudentList.iterator();
				while(itr.hasNext()){
					HlGroupStudent hStudent = itr.next();
					if(hStudent.getHlGroup() == null){
						continue;
					}
					result = true;
				}
			}
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isHostelGroupDuplcated");
		return result;
	}	
	
}
