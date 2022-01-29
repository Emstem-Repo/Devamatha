package com.kp.cms.transactionsimpl.sap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.sap.ExamScheduleDate;
import com.kp.cms.bo.sap.ExamScheduleUsers;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.ExamScheduleForm;
import com.kp.cms.transactions.sap.IExamScheduleTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamScheduleTransactionImpl implements IExamScheduleTransaction{
	private static volatile ExamScheduleTransactionImpl examScheduleTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ExamScheduleTransactionImpl.class);
	
	/**
	 * return singleton object of TimeTableForClassHandler.
	 * @return
	 */
	public static ExamScheduleTransactionImpl getInstance() {
		if (examScheduleTransactionImpl == null) {
			examScheduleTransactionImpl = new ExamScheduleTransactionImpl();
		}
		return examScheduleTransactionImpl;
	}

	
	
	/* get the work location details from employee work location  (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#getWorkLocation()
	 */
	public Map<Integer,String> getWorkLocation() throws Exception {
		Session session = null;
		List<EmployeeWorkLocationBO> boList = null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from EmployeeWorkLocationBO bo where bo.isActive = 1";
			Query query = session.createQuery(str);
			boList = query.list();
			if(boList!= null && boList.size() > 0){
				Iterator<EmployeeWorkLocationBO> itr = boList.iterator();
				while (itr.hasNext()) {
					EmployeeWorkLocationBO bo = (EmployeeWorkLocationBO) itr.next();
					map.put(bo.getId(), bo.getName());
				}
			}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return map;
	}
	
	
	
	/* (non-Javadoc) save the venue and Invigilators details
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#saveVenueAndInvigilators(com.kp.cms.bo.sap.ExamScheduleDate)
	 */
	public boolean saveVenueAndInvigilators(ExamScheduleDate bo) throws Exception {
		log.debug("inside addDocType");
		Session session = null;
		Transaction transaction = null;
		boolean isSaved=false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bo!=null){
			session.save(bo);
			isSaved=true;
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			
			log.debug("leaving saveVenueAndInvigilators");
			return isSaved;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving saveVenueAndInvigilators..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving saveVenueAndInvigilators data..." , e);
			throw new ApplicationException(e);
		}

	}
	
	/*  Get  venue and  Invigilators in edit mode(non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#getVenueAndInvigilatorDetails(com.kp.cms.forms.admin.ExamScheduleForm)
	 */
	public ExamScheduleDate getVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm) throws Exception {
		Session session = null;
		ExamScheduleDate bo = null;
		try{
			session = HibernateUtil.getSession();
			if(examScheduleForm.getExamScheduleDateId()!=0){
					
			String str = "select bo from ExamScheduleDate bo where bo.isActive = 1 and bo.id="+examScheduleForm.getExamScheduleDateId() ;
					
			Query query = session.createQuery(str);
			bo = (ExamScheduleDate)query.uniqueResult();
			}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
	
	/*  Get  venue and  Invigilators in search mode (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#getVenueAndInvigilatorsByMonthAndYear(com.kp.cms.forms.admin.ExamScheduleForm)
	 */
	public List<ExamScheduleDate> getVenueAndInvigilatorsByMonthAndYear(ExamScheduleForm examScheduleForm) throws Exception {
		Session session = null;
		List<ExamScheduleDate> boList = null;
		try{
			session = HibernateUtil.getSession();
			if((examScheduleForm.getMonth()!=null && !examScheduleForm.getMonth().isEmpty()) 
					&& (examScheduleForm.getYear()!=null && !examScheduleForm.getYear().isEmpty())){
			String str = "select bo from ExamScheduleDate bo where isActive = 1 " +
					" and year(examDate)="+ examScheduleForm.getYear()+" and month(examDate)="+examScheduleForm.getMonth()+
					" order by examDate,sessionOrder";
			Query query = session.createQuery(str);
			boList = query.list();
			}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return boList;
	}
	
	/*  Delete   venue and  Invigilators (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#deleteVenueAndInvigilatorDetails(com.kp.cms.forms.admin.ExamScheduleForm)
	 */
	public boolean deleteVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ExamScheduleDate bo = null;
		boolean isDeleted=false;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(examScheduleForm.getExamScheduleDateId()!=0){
					
			String str = "select bo from ExamScheduleDate bo where bo.isActive = 1 and bo.id="+examScheduleForm.getExamScheduleDateId() ;
					
			Query query = session.createQuery(str);
			bo = (ExamScheduleDate)query.uniqueResult();
			}
			if(bo!=null){
				if(bo.getId()!=0){
						bo.setIsActive(false);
						Set<ExamScheduleVenue> venueBoSet=bo.getExamScheduleVenue();
						if(venueBoSet!=null && !venueBoSet.isEmpty() ){
						Iterator<ExamScheduleVenue> venueItr=venueBoSet.iterator();
						while (venueItr.hasNext()) {
							ExamScheduleVenue venueBo= venueItr .next();
							if(venueBo!=null){
								if(venueBo.getId()!=0){
									venueBo.setIsActive(false);
									Set<ExamScheduleUsers> userBoSet=venueBo.getExamScheduleUsers();
									
									if(userBoSet!=null && !userBoSet.isEmpty()){
										Iterator<ExamScheduleUsers> userItr=userBoSet.iterator();
										while (userItr.hasNext()) {
											ExamScheduleUsers userBo= userItr .next();
											if(userBo!=null ){
												if(userBo.getId()!=0){
													userBo.setIsActive(false);
													}
												}
											}
										}
								}
							}
						}
					}
						session.update(bo);
						isDeleted=true;
						transaction.commit();
				}	
				}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isDeleted;
	}
	
	
	
	
	/*  delete ExamScheduleVenue Details (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#deleteVenue(int)
	 */
	public boolean deleteVenue(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ExamScheduleVenue bo = null;
		boolean isDeleted=false;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(id!=0){
					
			String str = "select bo from ExamScheduleVenue bo where bo.isActive = 1 and bo.id="+id ;
					
			Query query = session.createQuery(str);
			bo = (ExamScheduleVenue)query.uniqueResult();
			}
			if(bo!=null){
				if(bo.getId()!=0){
						bo.setIsActive(false);
									Set<ExamScheduleUsers> userBoSet=bo.getExamScheduleUsers();
									
									if(userBoSet!=null && !userBoSet.isEmpty()){
										Iterator<ExamScheduleUsers> userItr=userBoSet.iterator();
										while (userItr.hasNext()) {
											ExamScheduleUsers userBo= userItr .next();
											if(userBo!=null ){
												if(userBo.getId()!=0){
													userBo.setIsActive(false);
													}
												}
											}
										}
					
						session.update(bo);
						isDeleted=true;
						transaction.commit();
				}	
				}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isDeleted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#getVenueList(com.kp.cms.forms.admin.ExamScheduleForm)
	 */
	@SuppressWarnings("unchecked")
	public Set<ExamScheduleVenue> getVenueList(ExamScheduleForm examScheduleForm)throws Exception {

		Session session = null;
		Set<ExamScheduleVenue> boList = null;
		try{
			session = HibernateUtil.getSession();
			if(examScheduleForm.getExamScheduleDateId()!=0){
					
			String str = "select bo from ExamScheduleVenue bo where bo.isActive = 0 " +
					"and bo.examScheduleDate.id="+examScheduleForm.getExamScheduleDateId() ;
					
			Query query = session.createQuery(str);
			boList =new HashSet(query.list()); 
			}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return boList;
	
	}
	
	/* Update venue and Invigilator Details(non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamScheduleTransaction#updateVenueAndInvigilators(com.kp.cms.bo.sap.ExamScheduleDate)
	 */
	public boolean updateVenueAndInvigilators(ExamScheduleDate bo) throws Exception {
		log.debug("inside addDocType");
		Session session = null;
		Transaction transaction = null;
		boolean isSaved=false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bo!=null){
			session.saveOrUpdate(bo);
			isSaved=true;
			}
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			
			log.debug("leaving saveVenueAndInvigilators");
			return isSaved;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving saveVenueAndInvigilators..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving saveVenueAndInvigilators data..." , e);
			throw new ApplicationException(e);
		}

	}
	
	public ExamScheduleDate validateSessionAndSessionorder(ExamScheduleForm examScheduleForm,String str) throws Exception {
		Session session = null;
		ExamScheduleDate bo=null;
		try{
			session = HibernateUtil.getSession();
				Query query = session.createQuery(str);
				 bo = (ExamScheduleDate)query.uniqueResult();
		}catch (Exception e) {
			 throw new DuplicateException();
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
	
	
	public ExamScheduleUsers getexamScheduleUsersData(int id) throws Exception {
		Session session = null;
		ExamScheduleUsers bo = null;
		try{
			session = HibernateUtil.getSession();
			if(id!=0){
					
			String str = "select bo from ExamScheduleUsers bo where bo.isActive = 1 and bo.id="+id ;
					
			Query query = session.createQuery(str);
			bo = (ExamScheduleUsers)query.uniqueResult();
			}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
	
	public ExamScheduleUsers getInActiveExamScheduleUsersData(int id) throws Exception {
		Session session = null;
		ExamScheduleUsers bo = null;
		try{
			session = HibernateUtil.getSession();
			if(id!=0){
					
			String str = "select bo from ExamScheduleUsers bo where bo.isActive = 0 and bo.id="+id ;
					
			Query query = session.createQuery(str);
			bo = (ExamScheduleUsers)query.uniqueResult();
			}
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return bo;
	}
}
