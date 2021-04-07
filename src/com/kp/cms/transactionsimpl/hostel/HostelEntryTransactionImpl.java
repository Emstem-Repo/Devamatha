package com.kp.cms.transactionsimpl.hostel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.HostelEntryForm;
import com.kp.cms.forms.hostel.HostelLeaveForm;
import com.kp.cms.transactions.hostel.IHostelEntryTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelEntryTransactionImpl implements IHostelEntryTransactions{
	public static Log log = LogFactory.getLog(HostelEntryTransactionImpl.class);
	public static volatile HostelEntryTransactionImpl hostelEntryTransactionImpl;
	public static HostelEntryTransactionImpl getInstance(){
		if(hostelEntryTransactionImpl == null){
			hostelEntryTransactionImpl = new HostelEntryTransactionImpl();
			return hostelEntryTransactionImpl;
		}
		return hostelEntryTransactionImpl;
	}
	/**
	 * get hostel details
	 */
	public List<HlHostel> getHostelDeatils() throws Exception{
		log.debug("inside getHostelDeatils");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlHostel h where h.isActive = 1 order by h.name asc");
			List<HlHostel> list = query.list();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getHostelDeatils");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getHostelDeatils...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	/**
	 * save to table
	 * @param hlHostel
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addHostelEntry(HlHostel hlHostel, String mode) throws Exception {
		log.debug("inside addHostelEntry");
		Session session = null;
		Transaction transaction = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if("edit".equalsIgnoreCase(mode)){
				session.update(hlHostel);
			}
			else
			{
				session.save(hlHostel);
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			log.debug("leaving addHostelEntry");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during in addHostelEntry..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addHostelEntry..." , e);
			throw new ApplicationException(e);
		}

	}
	/**
	 * retrieving all the hostel details
	 */
	public List<HlHostel> getHostelDetailsById(int id) throws Exception{
		log.debug("inside getHostelDetailsById");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlHostel h where h.isActive = 1 and id = '" + id + "'");
			List<HlHostel> list = query.list();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getHostelDeatils");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getHostelDeatils...",e);
			 session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/**
	 * duplication checking for Hostel entry
	 */
	public HlHostel isHostelEntryDuplcated(String name, int id) throws Exception {
		log.debug("inside isgetDisciplinaryTypeDuplcated");
		Session session = null;
		HlHostel hostel;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlHostel h where h.name = :tempname ");
			if(id!= 0){
				sqlString.append(" and id != :id");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("tempname",  name);
			if(id!= 0){
				query.setInteger("id", id);
			}
			
			hostel = (HlHostel) query.uniqueResult();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isgetDisciplinaryTypeDuplcated");
		return hostel;
	}
	/**
	 * delete & reactivate
	 */
	public boolean deleteHostelEntry(int id, Boolean activate, HostelEntryForm hForm) throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		HlHostel hostel = (HlHostel) session.get(HlHostel.class, id);
		if (activate) {
			hostel.setIsActive(true);
		}else
		{
			hostel.setIsActive(false);
		}
		hostel.setModifiedBy(hForm.getUserId());
//		hostel.setLastModifiedDate(new Date());
		session.update(hostel);
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelEntryTransactions#getBlocks(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getBlocks(String hostelId) throws Exception {
		log.debug("inside getBlocks");
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
				session = HibernateUtil.getSession();
				if(hostelId!=null && !hostelId.trim().isEmpty()){
				Query query = session.createQuery("from HlBlocks h where h.isActive = 1 and h.hlHostel.id="+hostelId+" order by h.name asc");
				List<HlBlocks> list = query.list();
				if(list != null){
					Iterator<HlBlocks> iterator = list.iterator();
					while (iterator.hasNext()) {
						HlBlocks bo = (HlBlocks) iterator.next();
						if(bo.getId() != 0 && bo.getName() != null)
							map.put(bo.getId(), bo.getName());
					}
				}
				log.debug("leaving getBlocks");
			}
			return map;
		 } catch (Exception e) {
			 log.error("Error in getBlocks...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	@Override
	public Map<Integer, String> getUnits(String blockId) throws Exception {
		log.debug("inside getUnits");
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			if(blockId!=null && !blockId.trim().isEmpty()){
				Query query = session.createQuery("from HlUnits h where h.isActive = 1 and h.blocks.id="+blockId+" order by h.name asc");
				List<HlUnits> list = query.list();
				if(list != null){
					Iterator<HlUnits> iterator = list.iterator();
					while (iterator.hasNext()) {
						HlUnits bo = (HlUnits) iterator.next();
						if(bo.getId() != 0 && bo.getName() != null)
							map.put(bo.getId(), bo.getName());
					}
				}
			}
			log.debug("leaving getUnits");
			return map;
		 } catch (Exception e) {
			 log.error("Error in getUnits...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	@Override
	public HlAdmissionBo verifyRegisterNumberAndGetNameAjaxCall(BaseActionForm actionForm) throws Exception {
		
		log.debug("inside verifyRegisterNumberAndGetName");
		Session session = null;
		HlAdmissionBo hlAdmissionBo=null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="select hl from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.academicYear=:academicYear " +
					"and hl.isActive=1 and hl.isCancelled=0 and hl.isCheckedIn=1 and (hl.checkOut=0 or hl.checkOut is null)";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  actionForm.getRegNo());
			query.setString("academicYear",  actionForm.getAcademicYear());
			hlAdmissionBo = (HlAdmissionBo) query.uniqueResult();
			//session.close();
			//sessionFactory.close();
			return hlAdmissionBo;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public HlAdmissionBo getStudentDetailsByFormCall(HostelLeaveForm actionForm)
			throws Exception {
		log.debug("inside getStudentDetailsByFormCall");
		Session session = null;
		HlAdmissionBo hlAdmissionBo=null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.academicYear=:academicYear and hl.isActive=1 " +
					"and hl.isCancelled=0 and hl.isCheckedIn=1 and (hl.checkOut=0 or hl.checkOut is null)";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  actionForm.getRegisterNo());
			query.setString("academicYear",  actionForm.getAcademicYear1());
			hlAdmissionBo = (HlAdmissionBo) query.uniqueResult();
			//session.close();
			//sessionFactory.close();
			return hlAdmissionBo;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	
		
}
