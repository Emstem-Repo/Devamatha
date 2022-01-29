package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelStudentCheckOutForm;
import com.kp.cms.transactions.hostel.IHostelStudentCheckOutTransaction;
import com.kp.cms.utilities.HibernateUtil;

import common.Logger;

public class HostelStudentCheckOutTransactionImpl implements IHostelStudentCheckOutTransaction {
	
	private static final Logger log = Logger.getLogger(HostelStudentCheckOutTransactionImpl.class);

	public static volatile HostelStudentCheckOutTransactionImpl hostelStudentCheckOutTransactionImpl;

	public static HostelStudentCheckOutTransactionImpl getInstance() {
		if (hostelStudentCheckOutTransactionImpl == null) {
			hostelStudentCheckOutTransactionImpl = new HostelStudentCheckOutTransactionImpl();
		}
		return hostelStudentCheckOutTransactionImpl;
	}
	
	public HlAdmissionBo verifyRegisterNumberAndGetDetails(HostelStudentCheckOutForm checkOutForm) throws Exception {
		
		log.debug("inside verifyRegisterNumberAndGetDetails");
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.isCheckedIn=1 and hl.isCancelled=0 and hl.isActive=1";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  checkOutForm.getRegNo());
			HlAdmissionBo hlAdmissionBo = (HlAdmissionBo) query.uniqueResult();
			session.flush();
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
	
	@SuppressWarnings("unchecked")
	public List<HlStudentFacilityAllotted> getStudentFacilitiesAlloted(HostelStudentCheckOutForm checkOutForm) throws Exception {
		
		log.debug("inside getStudentFacilitiesAlloted");
		Session session = null;
		
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlStudentFacilityAllotted hl where hl.hlAdmissionId.studentId.registerNo=:registrationNo and hl.isActive=1");
			query.setString("registrationNo",  checkOutForm.getRegNo());
			List<HlStudentFacilityAllotted> facilityAllotted = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return facilityAllotted;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<HlAdmissionBo> getStudentCheckOutInformation(HostelStudentCheckOutForm checkOutForm) throws Exception {
		
		log.debug("inside getStudentFacilitiesAlloted");
		Session session = null;
		
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.isActive=1");
			query.setString("registrationNo",  checkOutForm.getRegNo());
			List<HlAdmissionBo> facilityAllotted = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return facilityAllotted;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public boolean addCheckOut(HlAdmissionBo hlAdmissionBo) throws Exception
	{
		log.info("Start of addCheckOut of ExternalEvaluator TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(hlAdmissionBo);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in adding CheckOut in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of addCheckOut of HlAdmissionBo TransactionImpl");
		}
	}

	public HlAdmissionBo getDetailsForMerge(HostelStudentCheckOutForm checkOutForm) throws Exception {
		
		log.debug("inside getDetailsForMerge");
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String idd=String.valueOf(checkOutForm.getId());
			String strQuery="from HlAdmissionBo hl where hl.id=:id1 and hl.isCheckedIn=1 and hl.isCancelled=0";
			Query query = session.createQuery(strQuery);
			query.setString("id1",  idd);
			HlAdmissionBo hlAdmissionBo = (HlAdmissionBo) query.uniqueResult();
			session.flush();
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
