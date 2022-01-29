package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.ComplaintsForm;
import com.kp.cms.transactions.hostel.IComplaintsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ComplaintsTransactionImpl implements IComplaintsTransaction {

private static final Log log = LogFactory.getLog(ComplaintsTransactionImpl.class);
	
	public static volatile ComplaintsTransactionImpl self = null;
	
	/**
	 * @return unique instance of LeaveReportTransactionImpl class.
	 * This method gives instance of this method
	 */
	public static ComplaintsTransactionImpl getInstance(){
		if(self==null)
			self = new  ComplaintsTransactionImpl();
		return self ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HlComplaintType> getComplaintsList()  throws Exception {
		
		 log.info("entered getComplaintsList in ComplaintsTransactionImpl class.");
		 
		 Session  session = null;
		 
		 List<HlComplaintType>  complaintsList =  null;
		 
		 try {
			
			 //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			 //session = sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 complaintsList =  session.createQuery("from HlComplaintType hlc where hlc.isActive = 1").list() ;
		
		} catch (Exception e) {
			
			log.error("error while getting the getComplaintsList results ",e);
			throw new  ApplicationException(e);
		} finally {
			
			if (session != null) {
				  session.flush();
				  //session.close();
			}
		}
		log.info("exit getComplaintsList in ComplaintsTransactionImpl class.");
		 return   complaintsList;
	}
	
	public boolean saveComplaintDetails(HlComplaint complaint) throws Exception {
		log.info("call of saveComplaintDetails in ComplaintsTransactionImpl class.");
		 Session session = null;
		 Transaction transaction = null;
		 boolean isAdded = false;
		 try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(complaint);
			transaction.commit();
			log.debug("leaving addGrades");
			return true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to saveComplaintDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		//log.info("end of saveComplaintDetails in ComplaintsTransactionImpl class.");
		//return isAdded;
	}
	
	public HlApplicationForm StudentCheck(ComplaintsForm complaintsForm)throws Exception
	{
		Session session = null;
		
		try
		{
			session = HibernateUtil.getSession();
			List<HlApplicationForm> hlApplicationFormBo = session.createQuery("from HlApplicationForm h where h.isActive = 1 and h.requisitionNo ='" +complaintsForm.getRequisitionNo()+"' and h.hlHostelByHlApprovedHostelId ='"+complaintsForm.getHostelName()+"' and h.hlStatus.id=2").list();
			if(hlApplicationFormBo != null && !hlApplicationFormBo.isEmpty())
				return hlApplicationFormBo.get(0);
			else 
				return null;
		}
		catch(Exception e)
		{
			
			log.error("give proper  requisition No" , e);
			throw new ApplicationException(e);
		}
		finally
		{
			if (session != null) {
				
				 session.flush();
				 session.close();
			}
			
		}
				
	}
	public List<HlComplaint> getComplaintsDetails() throws Exception
	{
		Session session = null;
		List<HlComplaint> complaintsListBo = null;
		try
		{
			session = HibernateUtil.getSession();
			complaintsListBo = session.createQuery("from HlComplaint h where h.isActive = 1").list();
			
		}
		catch(Exception e)
		{
			log.error("error while getting the getComplaintsDetails results ",e);
			throw new  ApplicationException(e);
		}
		finally
		{
			if (session != null) {
				
				 session.flush();
				 //session.close();
			}
		}
		return complaintsListBo;
	}
	
	/**
	 * used to delete the Complaints Details
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteComplaintsDetails(int id, String userId)
		throws Exception {
		log.info("inside the deleteComplaintsDetails of ComplaintsTransactionImpl");
			Session session = null;
			Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlComplaint complaintDetailsBo = (HlComplaint) session
					.get(HlComplaint.class, id);
			complaintDetailsBo.setIsActive(false);
			complaintDetailsBo.setModifiedBy(userId);
			complaintDetailsBo.setLastModifiedDate(new Date());
			session.update(complaintDetailsBo);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("error occured deleteComplaintsDetails of ComplaintsDetailsTransactionImpl"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	public boolean duplicateStudentCheck(ComplaintsForm complaintsForm)throws Exception
	{
		Session session = null;
		boolean isDup=false;
		try
		{
			session = HibernateUtil.getSession();
			List<HlComplaint> hlComplaintBo = session.createQuery("from HlComplaint h where h.isActive=1 and h.hlApplicationForm.hlStatus.id = 2 and h.hlApplicationForm.requisitionNo ='"+complaintsForm.getRequisitionNo()+"' and h.hlComplaintType.id ='"+complaintsForm.getComplaintType()+"' and h.hlHostel.id='"+complaintsForm.getHostelName()+"' and h.date='"+CommonUtil.ConvertStringToSQLDate(complaintsForm.getLogDate())+"'").list();
			if(hlComplaintBo != null && !hlComplaintBo.isEmpty())
				isDup= true;
		}
		catch(Exception e)
		{
			
			log.error("give proper  requisition No" , e);
			throw new ApplicationException(e);
		}
		finally
		{
			if (session != null) {
				
				 session.flush();
				 session.close();
			}
			
		}
		return isDup;
				
	}
}