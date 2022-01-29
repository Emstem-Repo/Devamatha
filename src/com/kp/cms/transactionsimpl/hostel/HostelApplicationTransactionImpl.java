package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelApplicationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelApplicationTransactionImpl implements IHostelApplicationTransaction{

	private static final Log log = LogFactory.getLog(HostelApplicationTransactionImpl.class);
	/**
	 * Used to get RoomType Names based on the Hostel Id
	 */
	public List<HlRoomType> getRoomTypesonHostelId(int hostelId) throws Exception {
		log.info("Inside of getRoomTypesonHostelId of HostelApplicationTransactionImpl");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlRoomType> roomTypeNameList = session.createQuery("from HlRoomType r where r.isActive = 1 and r.hlHostel.id = "
			+ hostelId + "order by r.id").list();
			log.info("End of getRoomTypesonHostelId of HostelApplicationTransactionImpl");
			return roomTypeNameList;
		} catch (Exception e) {
			log.error("Error occured in getRoomTypesonHostelId of HostelApplicationTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Used to get the AdmApplnID based on the student ID
	 */
	public int getAdmApplnIDOnStudentID(int studentID) throws Exception {
		log.info("Inside of getRoomTypesonHostelId of HostelApplicationTransactionImpl");
		Session session = null;
		int admApplnID = 0;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			admApplnID = (Integer) session.createQuery("select s.admAppln.id from Student s where s.isActive = 1 and s.id = "
			+ studentID ).uniqueResult();
			log.info("End of getRoomTypesonHostelId of HostelApplicationTransactionImpl");
			return admApplnID;
		} catch (Exception e) {
			log.error("Error occured in getRoomTypesonHostelId of HostelApplicationTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Used to get the applied status Id
	 */
	public int getAppliedStatusId() throws Exception {
		log.info("Inside of getAppliedStatusId of HostelApplicationTransactionImpl");
		Session session = null;
		int statusId = 0;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			statusId = (Integer) session.createQuery("select s.id from HlStatus s where s.isActive = 1 and s.statusType =" +" 'applied'").uniqueResult();
			log.info("End of getAppliedStatusId of HostelApplicationTransactionImpl");
			return statusId;
		} catch (Exception e) {
			log.error("Error occured in getAppliedStatusId of HostelApplicationTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * Get Max Requisition No
	 */
	public int getMaxRequisitionNo() throws Exception {
		log.info("Entering into HostelApplicationTransactionImpl of getMaxRequisitionNo");
		Session session = null;
		List<HlApplicationForm> appForm;
		int maxRequisitionNo = 0;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			appForm = session.createQuery("from HlApplicationForm form").list();
			if(appForm.isEmpty()){
				maxRequisitionNo = 0;
			}else {
				maxRequisitionNo = (Integer) session.createQuery("select max(requisitionNo) from HlApplicationForm form").uniqueResult();
			}
		} catch (Exception e) {
		log.error("Error occured in getMaxRequisitionNo of HostelApplicationTransactionImpl");
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
	/**
	 * Used for save
	 */
	public boolean submitApplicationStudentDetails(HlApplicationForm hlApplicationFormBO) throws Exception {
		log.info("Entering into HostelApplicationTransactionImpl of submitApplicationStudentDetails");
		Session session = null;
		Transaction transaction = null;
		try {
//			if(hlApplicationFormBO.getAdmAppln()!= null){
//				updateHlApplication(hlApplicationFormBO.getAdmAppln().getId());
//			}
//			else if(hlApplicationFormBO.getEmployee()!= null)
//			{
//				updateHlApplicationForEmployee(hlApplicationFormBO.getAdmAppln().getId());
//			}
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(hlApplicationFormBO);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while adding Hostel Student Application inHostelApplicationTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into HostelApplicationTransactionImpl of submitApplicationStudentDetails");
		}
	}
	
	/**
	 * Used to get Terms & Condition for the hostel
	 */
	
	public HlHostel getTermsConditionforHostel(int hostelId)
			throws Exception {
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
	/**
	 * Used to get hostel details based on hostelId
	 */
	public HlHostel getHostelRoomTypesByHostelID(int hostelId) throws Exception {
		log.info("Inside of getHostelRoomTypesByHostelID of HostelApplicationTransactionImpl");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();		
			session = HibernateUtil.getSession();
			HlHostel hostel = (HlHostel) session.createQuery("from HlHostel h where h.isActive = 1 and h.id=?").setInteger(0,hostelId).uniqueResult();			
			return hostel;
		} catch (Exception e) {
			log.error("Error occured in getHostelRoomTypesByHostelID of HostelApplicationTransactionImpl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.info("End of getHostelRoomTypesByHostelID of HostelApplicationTransactionImpl");
		}
	}
	/**
	 * Used to get the applied status Id
	 */
	public HlStatus getCheckedInStatus() throws Exception {
		log.info("Inside of getAppliedStatusId of HostelApplicationTransactionImpl");
		String statusType = CMSConstants.KNOWLEDGEPRO_HOSTEL_STATUS_CHECKEDIN;
		Session session = null;
		HlStatus status = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			status = (HlStatus) session.createQuery("from HlStatus s where s.isActive = 1 and s.statusType = '" + statusType +"'").uniqueResult();
			log.info("End of getAppliedStatusId of HostelApplicationTransactionImpl");
			return status;
		} catch (Exception e) {
			log.error("Error occured in getAppliedStatusId of HostelApplicationTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param studentID
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicateApplication(String studentID) throws Exception {
		log.info("Inside isDuplicateApplication");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlApplicationForm> studentList = session.createQuery("from HlApplicationForm h join h.admAppln.students student" +
					" where h.isActive = 1 and student.isActive = 1" +
					" and student.id = " + studentID ).list();
			log.info("End of getRoomTypesonHostelId of HostelApplicationTransactionImpl");
			if(studentList == null || studentList.size()<= 0){
				return false;
			}
			else
			{
				return true;
			}
		} catch (Exception e) {
			log.error("Error occured in isDuplicateApplication of HostelApplicationTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/**
	 * 
	 * @param admAppId
	 * @throws Exception
	 */
	public void updateHlApplication(int admAppId) throws Exception{
		log.debug("TXN Impl : Entering updateHlApplication");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			Query query = session.createQuery("update HlApplicationForm set isActive = 0" +
											 " where admAppln.id = " + admAppId);
			query.executeUpdate();
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving updateHlApplication with success");	
		return;
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving updateHlApplication with Exception");
			throw e;
		}	
	}
	/**
	 * 
	 * @param emplId
	 * @throws Exception
	 */
	public void updateHlApplicationForEmployee(int emplId) throws Exception{
		log.debug("TXN Impl : Entering updateHlApplicationForEmployee");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			Query query = session.createQuery("update HlApplicationForm set isActive = 0" +
											 " where employee.id = " + emplId);
			query.executeUpdate();
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving updateHlApplication with success");	
		return;
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving updateHlApplication with Exception");
			throw e;
		}	
	}
	/**
	 * 
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public List<HlFees> getRoomTypewiseHostelFeesonHostelId(int hostelId) throws Exception {
		log.info("Inside of getRoomTypesonHostelId of getRoomTypewiseHostelFeesonHostelId");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlFees> roomTypeFeeList = session.createQuery("from HlFees h where h.isActive = 1 and h.hlHostel.id = " + hostelId ).list();
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