package com.kp.cms.transactionsimpl.attendance;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Array;

import sun.print.resources.serviceui;

import com.kp.cms.bo.admin.MobileSMSCriteriaBO;
import com.kp.cms.forms.attendance.MobileSmsCriteriaForm;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.attendance.AttendanceEntryHandler;
import com.kp.cms.transactions.attandance.IMobileSMSCriteriaTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class MobileSMSCriteriaTransactionImpl implements IMobileSMSCriteriaTransaction{

	private static Log log = LogFactory.getLog(MobileSMSCriteriaTransactionImpl.class);
	public static volatile MobileSMSCriteriaTransactionImpl smsCriteriaTransactionImpl = null;

	public static MobileSMSCriteriaTransactionImpl getInstance() {
		if (smsCriteriaTransactionImpl == null) {
			smsCriteriaTransactionImpl = new MobileSMSCriteriaTransactionImpl();
			return smsCriteriaTransactionImpl;
		}
		return smsCriteriaTransactionImpl;
	}

	public String[] getSmsClassMap(int currentYear,
			int smsTimehours, int smsMinitue) throws Exception {
		log.debug("call of getSmsClassMap method in MobileSMSCriteriaTransactionImpl.class");
		String[] smsclass=null;
		Session session=null;
		String smsTime="";
		String smsMin = String.format("%02d", smsMinitue);
		Map<Integer,String> classMap=new HashMap<Integer, String>();
		smsTime=""+smsTimehours+ ":"+smsMin+ ":00";
		Date date=new Date();
		
		
		try
		{
			session=HibernateUtil.getSession();
			String dbURL = session.connection().getMetaData().getURL().toString();
			System.out.println("dbname========"+dbURL);
			String subQury="select sm  from " +
			"MobileSMSCriteriaBO sm where sm.isActive=1";
			Query query=session.createQuery(subQury);
			List<MobileSMSCriteriaBO> bolist=query.list();
			Iterator iterator=bolist.iterator();
			int i=0;
			while (iterator.hasNext()) {
				MobileSMSCriteriaBO bo = (MobileSMSCriteriaBO) iterator.next();
				if(bo.getIsSmsBlocked())
				{
					
					//classtime
					SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
					Date d = df.parse(bo.getSmsTime());
					Calendar cal1 = Calendar.getInstance();
					cal1.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
					cal1.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
					cal1.set(Calendar.SECOND, d.getSeconds()); // Your second

					Time classTime = new Time(cal1.getTime().getTime());
					
					//current time
					Date d1= new Date();
					Time curDate = new Time(d1.getTime());
					
					//add 30min to classtime
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
					cal.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
					cal.set(Calendar.SECOND, d.getSeconds()); // Your second

					cal.add(Calendar.MINUTE,30);
					Time classTimeAdd = new Time(cal.getTime().getTime());
					//System.out.println(classTime+""+curDate+""+classTimeAdd);
					
					
					 if (curDate.after(classTime) && curDate.before(classTimeAdd)) {
						
					
					//if(bo.getSmsTime().equalsIgnoreCase(smsTime)){
						
						
						if(date.after(bo.getSmsBlockStartDate()) && date.before(bo.getSmsBlockEndDate()))
						{
							continue;
						}
						else if(date.equals(bo.getSmsBlockStartDate()) || date.equals(bo.getSmsBlockEndDate()))
						{
							continue;
						}
						else
						{
							
								classMap.put(bo.getClassSchemewise().getId(), bo.getClassSchemewise().getClasses().getName());
							
						}
					}
				}
				else
				{
					//classtime
					SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
					Date d = df.parse(bo.getSmsTime());
					Calendar cal1 = Calendar.getInstance();
					cal1.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
					cal1.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
					cal1.set(Calendar.SECOND, d.getSeconds()); // Your second

					Time classTime = new Time(cal1.getTime().getTime());
					
					//current time
					Date d1= new Date();
					Time curDate = new Time(d1.getTime());
					
					//add 30min to classtime
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, d.getHours()); // Your hour
					cal.set(Calendar.MINUTE, d.getMinutes()); // Your Mintue
					cal.set(Calendar.SECOND, d.getSeconds()); // Your second

					cal.add(Calendar.MINUTE,10);
					Time classTimeAdd = new Time(cal.getTime().getTime());
					
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
					String holidayDate = dateFormat.format(date);  
					int day=cal.get(Calendar.DAY_OF_WEEK);
					boolean isHolidayDate=AttendanceEntryHandler.getInstance().checkHolidayDate(holidayDate);
					if((day!=1 || day!=7) && !isHolidayDate){
					 if (curDate.after(classTime) && curDate.before(classTimeAdd)) {
						 classMap.put(bo.getClassSchemewise().getId(), bo.getClassSchemewise().getClasses().getName());
					    }
					}
				}			
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in getting sms class"+ e.toString());
		}
		finally
		{
				session.clear();
				session.close();
		}
		if(classMap.size()>0)
		{
			smsclass=new String[classMap.size()];
			int i=0;
			for (Map.Entry<Integer, String> mapEntry : classMap.entrySet()) {
				smsclass[i] = mapEntry.getKey().toString();
			    i++;
			}
		}
		log.debug("end of getSmsClassMap method in MobileSMSCriteriaTransactionImpl.class");
		return smsclass;
	}

	public boolean isDuplicateClass(MobileSmsCriteriaForm mobileSmsCriteriaForm)
			throws Exception {
		log.debug("call of isDuplicateClass method in MobileSMSCriteriaTransactionImpl.class");
		boolean isDuplicated=false;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String queryString="select msm from MobileSMSCriteriaBO msm where msm.year=:year and msm.classSchemewise.id=:classSchemeId";
			Query query=session.createQuery(queryString);
			query.setString("year", mobileSmsCriteriaForm.getYear());
			query.setString("classSchemeId", mobileSmsCriteriaForm.getClassSchemWiseID());
			List<MobileSMSCriteriaBO> boList=query.list();
			if(boList.size()>0 && boList!=null)
			{
				isDuplicated=true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in checking duplicated  in isDuplicateClass method in MobileSMSCriteriaTransactionImpl.class");
			log.error("Error is:"+e.toString());
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of isDuplicateClass method in MobileSMSCriteriaTransactionImpl.class");
		return isDuplicated;
	}

	public boolean addSMSCriteria(MobileSMSCriteriaBO mobileSMSCriteriaBO)
			throws Exception {
		log.debug("call of addSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		boolean isAdded=false;
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(mobileSMSCriteriaBO);
			transaction.commit();
			isAdded=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in addSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
			log.error("Error is "+e.toString());
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		log.debug("end of addSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		return isAdded;
	}

	public List<MobileSMSCriteriaBO> getAllSMSCriteriaList() throws Exception {
		log.debug("call of getAllSMSCriteriaList in MobileSMSCriteriaTransactionImpl.class");
		List<MobileSMSCriteriaBO> mobileSMSCriteriaBOList=new ArrayList<MobileSMSCriteriaBO>();
		Session session=null;
		try
		{
			session=InitSessionFactory.getInstance().openSession();
			String subQuery="select sms from MobileSMSCriteriaBO sms where sms.isActive=1";
			Query query=session.createQuery(subQuery);
			mobileSMSCriteriaBOList=query.list();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in getAllSMSCriteriaList in MobileSMSCriteriaTransactionImpl.class");
			log.error("Error"+e.toString());
		}
		finally
		{
			if(session!=null)
			{
				
			}
		}
		log.debug("end of getAllSMSCriteriaList in MobileSMSCriteriaTransactionImpl.class");
		return mobileSMSCriteriaBOList;
	}

	public boolean deleteSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm)
			throws Exception {
		log.debug("call of deleteSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		boolean isDeleted=false;
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			MobileSMSCriteriaBO criteriaBO=(MobileSMSCriteriaBO) 
			session.get(MobileSMSCriteriaBO.class, Integer.parseInt(mobileSmsCriteriaForm.getSmsCriteriaId()));
			criteriaBO.setIsActive(Boolean.FALSE);
			criteriaBO.setModifiedBy(mobileSmsCriteriaForm.getUserId());
			criteriaBO.setLastModifiedDate(new Date());
			session.delete(criteriaBO);
			transaction.commit();
			isDeleted=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("Error in deleteSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		log.debug("end of deleteSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		return isDeleted;
	}

	public MobileSMSCriteriaBO getSMScriteriaDetails(String smsCriteriaId)
			throws Exception {
		log.debug("call of getSMScriteriaDetails method in MobileSMSCriteriaTransactionImpl.class");
		Session session=null;
		MobileSMSCriteriaBO criteriaBO=new MobileSMSCriteriaBO();
		try
		{
			session= HibernateUtil.getSession();
			criteriaBO=(MobileSMSCriteriaBO) session.get(MobileSMSCriteriaBO.class, Integer.parseInt(smsCriteriaId));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("Error in getSMScriteriaDetails method in MobileSMSCriteriaTransactionImpl.class");
		}
		finally
		{
			if(session!=null)
			{
				
			}
		}
		log.debug("end of getSMScriteriaDetails method in MobileSMSCriteriaTransactionImpl.class");
		
		return criteriaBO;
	}

	public boolean updateSMSCriteria(MobileSMSCriteriaBO smsCriteBo)
			throws Exception {
		log.debug("call of updateSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		boolean isUpdated=false;
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.update(smsCriteBo);
			transaction.commit();
			isUpdated=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in updateSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		log.debug("end of updateSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		return isUpdated;
	}

	public boolean deleteAllOldSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("call of deleteAllOldSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		boolean isDeleteAll=false;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String subQuery="delete  from mobile_sms_criteria";
			Query query=session.createSQLQuery(subQuery);
			query.executeUpdate();
			isDeleteAll=true;
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in deleteAllOldSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
			
		}
		
		log.debug("end of deleteAllOldSMSCriteria method in MobileSMSCriteriaTransactionImpl.class");
		return isDeleteAll;
	}

	public boolean addSMSCriteriaForAllClass(List<MobileSMSCriteriaBO> boList)
			throws Exception {
		log.debug("call of addSMSCriteriaForAllClass method in MobileSMSCriteriaTransactionImpl.class");
		boolean isAdded=false;
	    Session session=null;
	    Transaction transaction=null;
	    try
	    {
	    	session=HibernateUtil.getSession();
	    	transaction=session.beginTransaction();
	    	transaction.begin();
	    	Iterator  iterator=boList.iterator();
	    	while (iterator.hasNext()) {
				MobileSMSCriteriaBO smsCriteriaBO=new MobileSMSCriteriaBO();
				smsCriteriaBO=(MobileSMSCriteriaBO) iterator.next();
				session.save(smsCriteriaBO);
			}
	    	transaction.commit();
	    	isAdded=true;
	    }catch (Exception e) {
			e.printStackTrace();
			log.error("Error in addSMSCriteriaForAllClass method in MobileSMSCriteriaTransactionImpl.class");
			transaction.rollback();
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		
		log.debug("end of addSMSCriteriaForAllClass method in MobileSMSCriteriaTransactionImpl.class");
		return isAdded;
	}


}
