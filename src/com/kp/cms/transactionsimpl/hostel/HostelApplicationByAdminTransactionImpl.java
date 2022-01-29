package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelApplicationByAdminTransactions;
import com.kp.cms.utilities.HibernateUtil;

	public class HostelApplicationByAdminTransactionImpl implements IHostelApplicationByAdminTransactions{
		
	private static final Log log = LogFactory.getLog(HostelApplicationByAdminTransactionImpl.class);
		
	public static volatile HostelApplicationByAdminTransactionImpl hostelApplicationByAdminTransactionImpl = null;
	
	public static HostelApplicationByAdminTransactionImpl getInstance() {
		if (hostelApplicationByAdminTransactionImpl == null) {
			hostelApplicationByAdminTransactionImpl = new HostelApplicationByAdminTransactionImpl();
			return hostelApplicationByAdminTransactionImpl;
		}
		return hostelApplicationByAdminTransactionImpl;
	}
	
	
	//get the details based on query
	public AdmAppln getAdmApplnByQuery(String query)throws Exception {
		Session session = null;
		AdmAppln admAppln = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query admApplnQuery=session.createQuery(query);
			admAppln =(AdmAppln) admApplnQuery.uniqueResult();
			return admAppln;
		} catch (Exception e) {
			log.error("Error while getAdmApplnByQuery." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	public List<HlRoomType> getRoomTypesForHostel(int hostelId) throws Exception {
		log.info("start of getRoomTypesForHostel of HostelApplicationByAdminTransactionImpl");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlRoomType> roomTypeNameList = session.createQuery("from HlRoomType roomType where roomType.isActive = 1 and roomType.hlHostel.id = "
			+ hostelId + "order by roomType.id").list();
			log.info("End of getRoomTypesForHostel of HostelApplicationTransactionImpl");
			return roomTypeNameList;
		} catch (Exception e) {
			log.error("Error occured in getRoomTypesForHostel of HostelApplicationByAdminTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	public Employee getEmployee(String employeeId)throws Exception{
		
		log.info("entering into getEmployee of HostelApplicationByAdminTransactionImpl class.");
		Session session = null;
		Employee employee = null;
		try {
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = HibernateUtil.getSession();
		Query employeeQuery = session.createQuery(" from Employee employee "
		                                           + " where employee.isActive=1 and  employee.code = :employeeId");
		employeeQuery.setString("employeeId",employeeId);
				
		employee = (Employee) employeeQuery.uniqueResult();
				
		} catch (Exception e) {
		log.info("error in getEmployee of HostelApplicationByAdminTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getEmployee of HostelApplicationByAdminTransactionImpl class.");
		return employee;
	}
	
	public boolean saveHlApplicationForm(HlApplicationForm hlApplicationForm) throws ApplicationException
	{
		log.info("start of saveHlApplicationForm in HostelApplicationByAdminTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(hlApplicationForm);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to save HlApplicationForm" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of saveHlApplicationForm in HostelApplicationByAdminTransactionImpl class.");
		return isAdded;		
	}
	
	public Integer getMaxRequisitionNo() throws Exception {
		log.info("Entering into HostelApplicationByAdminTransactionImpl of getMaxRequisitionNo");
		Session session = null;
		Integer maxRequisitionNo = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query maxRequisitionNoQuery=session.createQuery("select max(requisitionNo) from HlApplicationForm form");
			maxRequisitionNo = (Integer)maxRequisitionNoQuery.uniqueResult();
		} catch (Exception e) {
		log.error("Error occured in getMaxRequisitionNo of HostelApplicationByAdminTransactionImpl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into HostelApplicationTransactionImpl of getMaxReceiptNo");
		return maxRequisitionNo;
	}	
	/*public int getMaxRequisitionNo() throws Exception {
		log.info("Entering into HostelApplicationByAdminTransactionImpl of getMaxRequisitionNo");
		Session session = null;
		List<HlApplicationForm> appForm;
		int maxRequisitionNo = 0;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			appForm = session.createQuery("from HlApplicationForm form").list();
			if(appForm.isEmpty()){
				maxRequisitionNo = 0;
			}else {
				maxRequisitionNo = (Integer) session.createQuery("select max(requisitionNo) from HlApplicationForm form").uniqueResult();
			}
		} catch (Exception e) {
		log.error("Error occured in getMaxRequisitionNo of HostelApplicationByAdminTransactionImpl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into HostelApplicationTransactionImpl of getMaxReceiptNo");
		return maxRequisitionNo;
	}*/
	
	public HlHostel getTermsConditionforHostel(int hostelId)throws Exception {
		
		log.info("Inside of getTermsConditionforHostel of HostelApplicationTransactionImpl");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlHostel> hlist = session.createQuery("from HlHostel h where h.isActive = 1 and h.id=?").setInteger(0,hostelId).list();
			HlHostel doc=null;
			if(hlist!=null && !hlist.isEmpty()){
				doc=hlist.get(0);
			}
			return doc;
		} catch (Exception e) {
			log.error("Error occured in getTermsConditionforHostel of HostelApplicationTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.info("End of getTermsConditionforHostel of HostelApplicationTransactionImpl");
		}
	}
	
	public HlApplicationForm getHlapplicationByQuery(String query)throws Exception {
	
		Session session = null;
		HlApplicationForm hlApplicationForm = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Query hlApplnQuery=session.createQuery(query);
			hlApplicationForm =(HlApplicationForm) hlApplnQuery.uniqueResult();
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
	
    public HlApplicationForm getHlapplicationByStudent(int admApplnId)throws Exception {
		
		log.info("Inside of getHlapplicationByStudent of HostelApplicationTransactionImpl");
		Session session = null;
		HlApplicationForm hlApplicationForm = null;
		try {
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = HibernateUtil.getSession();
		Query hlApplQuery = session.createQuery(" from HlApplicationForm hlApp "
		                                           + " where hlApp.isActive=1 and  admAppln.id = :admApplnId");
		hlApplQuery.setInteger("admApplnId",admApplnId);
				
		hlApplicationForm = (HlApplicationForm)hlApplQuery.uniqueResult();
				
		} catch (Exception e) {
		log.info("error in getEmployee of HostelApplicationByAdminTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		  }
			log.info("End of getHlapplicationByStudent of HostelApplicationTransactionImpl");
			return hlApplicationForm;
		}
	
    public HlApplicationForm getHlapplicationByStaff(int staffId)throws Exception {
		
		log.info("Inside of getHlapplicationByStudent of HostelApplicationTransactionImpl");
		Session session = null;
		HlApplicationForm hlApplicationForm = null;
		try {
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = HibernateUtil.getSession();
		Query hlApplQuery = session.createQuery(" from HlApplicationForm hlApp "
		                                           + " where hlApp.isActive=1 and  employee.id = :staffId");
		hlApplQuery.setInteger("staffId",staffId);
				
		hlApplicationForm = (HlApplicationForm)hlApplQuery.uniqueResult();
				
		} catch (Exception e) {
		log.info("error in getEmployee of HostelApplicationByAdminTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		  }
			log.info("End of getHlapplicationByStudent of HostelApplicationTransactionImpl");
			return hlApplicationForm;
		}
    
    
    public List<HlFees> getRoomTypewiseHostelFeesonHostelId(int hostelId) throws Exception {
		log.info("Inside of getRoomTypesonHostelId of getRoomTypewiseHostelFeesonHostelId");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlFees> roomTypeFeeList = session.createQuery("from HlFees h where h.hlRoomType.isActive = 1 and h.isActive = 1 and h.hlHostel.id = " + hostelId ).list();
			log.info("End of getRoomTypewiseHostelFeesonHostelId of HostelApplicationTransactionImpl");
			return roomTypeFeeList;
		} catch (Exception e) {
			log.error("Error occured in getRoomTypewiseHostelFeesonHostelId of HostelApplicationTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}
