package com.kp.cms.transactionsimpl.attendance;

import java.util.Date;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.HolidayNameForm;
import com.kp.cms.transactions.attandance.IHolidayTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


public class HolidayTransactionImp implements IHolidayTransaction {
	public static volatile HolidayTransactionImp holidayTransactionImp=null;
	public static final Log log=LogFactory.getLog(HolidayTransactionImp.class);

	public static HolidayTransactionImp getInstance() {
		if(holidayTransactionImp == null){
			holidayTransactionImp=new HolidayTransactionImp();
		}
	   return holidayTransactionImp;
	}

	@Override
	public List<HolidayBO> getHolidays() throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HolidayBO h where h.isActive=1");
			query.setFlushMode(FlushMode.COMMIT);
			List<HolidayBO> list=query.list();
			return list;
		}catch (Exception e) {
			log.error("Error during getting Holiday..." ,e);
			throw new ApplicationException(e);
		}
		
	}

	@Override
	public HolidayBO isHolidayDuplicate(HolidayBO oldHolidayBO) throws Exception {
		log.debug("impl: inside isHolidayDuplcated");
		Session session=null;
		HolidayBO holidayBO;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HolidayBO h where h.holidayDate ='"+oldHolidayBO.getHolidayDate()+"' and h.holidayName='"+oldHolidayBO.getHolidayName()+"'");
			holidayBO= (HolidayBO) query.uniqueResult();
			session.flush();
			log.debug("impl: leaving isHolidayDuplcated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		
		return holidayBO;

	}
	
	@Override
	public boolean addHoliday(HolidayBO holidayBO1) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(holidayBO1);
			transaction.commit();
			session.flush();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}
	}

	@Override
	public boolean updateHoliday(HolidayBO holidayBO1) throws Exception {
	    Session session=null;
	    Transaction transaction=null;
	    try{
	    	session=HibernateUtil.getSession();
	    	transaction = session.beginTransaction();
			transaction.begin();
			session.merge(holidayBO1);
			transaction.commit();
			session.flush();
			session.close();
			return true;
	    }catch (Exception e) {
	    	if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
		}
		return false;
	}
  
  }

	@Override
	public boolean deleteHoliday(int holidayId, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			HolidayBO holidayBO=(HolidayBO) session.get(HolidayBO.class, holidayId);
			holidayBO.setIsActive(false);
			holidayBO.setLastModifiedDate(new Date());
			holidayBO.setModifiedBy(userId);
			session.update(holidayBO);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}

	@Override
	public boolean reActivateHoliday(HolidayBO holidayBO, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			holidayBO.setIsActive(true);
			holidayBO.setLastModifiedDate(new Date());
			holidayBO.setModifiedBy(userId);
			session.update(holidayBO);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}

	@Override
	public HolidayBO isHolidayDuplicate1(HolidayNameForm holidayForm) throws Exception {
		log.debug("impl: inside isHolidayDuplcated");
		Session session=null;
		HolidayBO holidayBO;
		try{
			
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HolidayBO h where  h.isActive=1 and h.holidayDate ='"+CommonUtil.ConvertStringToSQLDate(holidayForm.getHolidayDate())+"'");
			holidayBO= (HolidayBO) query.uniqueResult();
			session.flush();
			log.debug("impl: leaving isHolidayDuplcated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		
		return holidayBO;

	}

	
}