package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.HolidayDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.transactions.employee.IHolidayDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class HolidayDetailsTxnImpl implements IHolidayDetailsTransaction{
	private static final Log log = LogFactory
	.getLog(HolidayDetailsTxnImpl.class);
	@Override
	public boolean addPayScale(Holidays holidays) {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(holidays);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			if(session!=null){
			session.flush();
			session.close();
			}
		}
		return true;
		
	}
	@Override
	public List<Holidays> getHolidaysList() {
		Session session=null;
		List<Holidays> holidaysList=null;
		try{
			String query="from Holidays holidays where holidays.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			holidaysList=querry.list();
			session.close();
		}catch(Exception exception){
			
		}
		return holidaysList;
	}
	@Override
	public boolean duplicateCheck(Date startDate,Date endDate,HttpSession hSession,ActionErrors errors,HolidayDetailsForm holidaysForm) {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from Holidays holiday where holiday.startDate=:sDate or holiday.endDate= :eDate or holiday.startDate=:eDate or holiday.endDate=:sDate";
			Query query=session.createQuery(quer);
			query.setDate("sDate", startDate);
			query.setDate("eDate", endDate);
			Holidays holidays=(Holidays)query.uniqueResult();
			if(holidays!=null && !holidays.toString().isEmpty()){
				if(holidaysForm.getId()!=0){
			        if(holidays.getId()==holidaysForm.getId()){
				       flag=false;
			        }else if(holidays.getIsActive()){
			           flag=true;
		               errors.add("error", new ActionError("knowledgepro.employee.holidays.duplicate"));
		            }
			        else{
				       flag=true;
				       holidaysForm.setId(holidays.getId());
				       throw new ReActivateException(holidays.getId());
			        }
				}else if(holidays.getIsActive()){
			           flag=true;
		               errors.add("error", new ActionError("knowledgepro.employee.holidays.duplicate"));
		         }
				else{
				     flag=true;
				     holidaysForm.setId(holidays.getId());
				     throw new ReActivateException(holidays.getId());
			    }
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.EMP_HOLIDAY_REACTIVATE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", holidaysForm.getId());
			}
		}
		return flag;
	}
	@Override
	public Holidays getHolidaysListById(int id) {
		Session session=null;
		Holidays holidays=null;
		try{
			session=HibernateUtil.getSession();
			String str="from Holidays holiday where holiday.id="+id;
			Query query=session.createQuery(str);
			holidays=(Holidays)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
			
		}
		return holidays;
		
	}
	@Override
	public boolean updateHolidays(Holidays holidays)
			 {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(holidays);
			transaction.commit();
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating Holidays data...", e);
		}
		return true;
	}
	@Override
	public boolean deleteHolidays(int id) {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from Holidays holiday where holiday.id="+id;
      	  Holidays holidays=(Holidays)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  holidays.setIsActive(false);
      	    session.update(holidays);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting PayScale data...", e);
      	
      }
      return true;
		
	}
	
	public boolean reactivateHolidays(HolidayDetailsForm holidaysForm)throws Exception
	  {
	log.info("Entering into DocumentExamEntryTransactionImpl of reactivateDocExamType");
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
	Session session = null;
	Holidays type = null;
	Transaction transaction = null;
	try { 
	    //session =sessionFactory.openSession();
	    session = HibernateUtil.getSession();
	    transaction=session.beginTransaction();
	    Query query = session.createQuery("from Holidays holiday where holiday.id="+holidaysForm.getId());
	    Holidays holiday=(Holidays)query.uniqueResult();
	    holiday.setIsActive(true);
	    holiday.setModifiedBy(holidaysForm.getUserId());
	    holiday.setLastModifiedDate(new Date());
	    session.update(holiday);
	    transaction.commit();
	    return true;
	}
	catch (Exception e) {	
		if(transaction!=null){
			transaction.rollback();
		}
		log.error("Exception occured while reactivateHolidays in HolidayDetailsTxnImpl :"+e);
		return false;
	} finally {
	if (session != null) {
		//sessionFactory.close();
		session.flush();
		session.close();
	}
	log.info("Leaving into HolidayDetailsTxnImpl of reactivateHolidays");
	}
}
	
}
