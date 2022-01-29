package com.kp.cms.transactionsimpl.hostel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;
import com.kp.cms.transactions.hostel.IHostelDisciplinaryDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import common.Logger;

public class HostelDisciplinaryDetailsTransactionImpl implements
		IHostelDisciplinaryDetailsTransaction {
	private static final Logger log = Logger
			.getLogger(HostelDisciplinaryDetailsTransactionImpl.class);

	public static volatile HostelDisciplinaryDetailsTransactionImpl hostelDisciplinaryDetailsTransactionImpl;

	public static HostelDisciplinaryDetailsTransactionImpl getInstance() {
		if (hostelDisciplinaryDetailsTransactionImpl == null) {
			hostelDisciplinaryDetailsTransactionImpl = new HostelDisciplinaryDetailsTransactionImpl();
		}
		return hostelDisciplinaryDetailsTransactionImpl;
	}

	public List<HlDisciplinaryType> getHostelDisciplinesList() throws Exception {
		
		log.info("Start of getHostelDisciplinesList of HostelDisciplinaryDetailsTransactionImpl");
		Session sess = null;
		try {
			// SessionFactory sessFactory=InitSessionFactory.getInstance();
			// sess=sessFactory.openSession();
			sess = HibernateUtil.getSession();
			Query query = sess
					.createQuery("from HlDisciplinaryType hdt where hdt.isActive = 1");
			List<HlDisciplinaryType> disciplineList = query.list();
			sess.flush();
			sess.close();
			// sessFactory.close();
			return disciplineList;
		} catch (Exception e) {
			sess.flush();
			sess.close();
			throw new ApplicationException(e);
		}
	}

	public boolean addHostelStudentDisciplineDetails(HlDisciplinaryDetails hlDisciplinaryDetails) 
	throws Exception {
		
		log.info("Start of addHostelStudentDisciplineDetails of HostelDisciplinaryDetailsTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			// SessionFactory sessFactory = InitSessionFactory.getInstance();
			// session = sessFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (hlDisciplinaryDetails != null) {
				session.save(hlDisciplinaryDetails);
			}
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}


	public HlAdmissionBo checkStudentPresent(String year, String regNo)throws Exception
	{
		log.info("Start of checkStudentPresent of HostelDisciplinary TransactionImpl");
		Session session = null;
		HlAdmissionBo hlAdmissionBo = null;
		
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from HlAdmissionBo h where h.academicYear='"+year+"' and h.studentId.registerNo='"+regNo+"' and h.isCheckedIn=true and h.isCancelled=false");
			hlAdmissionBo = (HlAdmissionBo)query.uniqueResult();
		}catch (Exception e) {
		log.error("Exception occured in checking duplicate records for HostelDisciplinary in IMPL :"+e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			}
			log.info("End of checkStudentPresent of HostelDisciplinary TransactionImpl");
		return hlAdmissionBo;
	}
	
	public List<HlDisciplinaryDetails> getDisciplineDetailsAcc(HostelDisciplinaryDetailsForm detailsForm)throws Exception{
		log.info("Start of getDisciplineDetails of HlDisciplinaryDetails TransactionImpl");
		Session session = null;
		try {
			if(detailsForm.getRegisterNo()!=null && !detailsForm.getRegisterNo().isEmpty()){
			session = HibernateUtil.getSession();
			List<HlDisciplinaryDetails> disciplineList = session.createQuery(" from HlDisciplinaryDetails h where h.isActive = 1 " +
					                            " and h.hlAdmissionBo.studentId.registerNo='"+detailsForm.getRegisterNo()+"'"+
					                            " and h.hlAdmissionBo.academicYear="+detailsForm.getYear()+
					                             " order by h.hlAdmissionBo.id").list();
		 return disciplineList;	
		  }else{
			  return null;	
		  }
			}catch (Exception e) {			
			log.error("Exception occured in getDisciplineDetails in HlDisciplinaryDetailsIMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
	}
	
	public HlDisciplinaryDetails getDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of HlDisciplinaryDetails TransactionImpl");
		Session session = null;
		HlDisciplinaryDetails hlDisciplinaryDetails = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from HlDisciplinaryDetails e where e.id= :row");
			query.setInteger("row", id);
			hlDisciplinaryDetails = (HlDisciplinaryDetails)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of HlDisciplinaryDetails TransactionImpl");
		return hlDisciplinaryDetails;	
	}
	
	public boolean updateHostelStudentDisciplineDetails(HlDisciplinaryDetails hldDetails)throws Exception
	{
		log.info("Start of updateHostelStudentDisciplineDetails of HlDisciplinaryDetails TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(hldDetails);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating HostelStudentDisciplineDetails in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		log.info("End of updateHostelStudentDisciplineDetails of HlDisciplinaryDetails TransactionImpl");
		}
	}
	
	public boolean deleteHostelStudentDisciplineDetails(int id, String userId) throws Exception{
		log.info("Start of deleteHostelStudentDisciplineDetails of HlDisciplinaryDetails TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlDisciplinaryDetails hldDetails=(HlDisciplinaryDetails)session.get(HlDisciplinaryDetails.class,id);
			hldDetails.setIsActive(false);
			hldDetails.setLastModifiedDate(new Date());
			hldDetails.setModifiedBy(userId);
			session.update(hldDetails);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting HlDisciplinaryDetails in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of deleteHostelStudentDisciplineDetails of HlDisciplinaryDetails TransactionImpl");
		}
	}
	
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc(BaseActionForm actionForm) throws Exception {
		
		log.debug("inside verifyRegisterNumberAndGetNameAcc");
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.academicYear=:academicYear and hl.isCancelled=0";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  actionForm.getRegNo());
			query.setString("academicYear",  actionForm.getAcademicYear());
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
	
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc1(HostelDisciplinaryDetailsForm byForm) throws Exception {
		
		log.debug("inside verifyRegisterNumberAndGetNameAcc");
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.academicYear=:academicYear and hl.isCheckedIn=1 and hl.isCancelled=0";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  byForm.getRegisterNo());
			query.setString("academicYear",  byForm.getYear());
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelDisciplinaryDetailsTransaction#getDisciplinaryDetailsByRegNoAndYear(com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm)
	 */
	@Override
	public List<HlDisciplinaryDetails> getDisciplinaryDetailsByRegNoAndYear( HostelDisciplinaryDetailsForm hldForm) throws Exception {
		Session session = null;
		List<HlDisciplinaryDetails> disciplinaryDetailsList;
		try{
			session= HibernateUtil.getSession();
			String hqlQuery = 	"from HlDisciplinaryDetails h where h.isActive = 1 " +
								" and h.hlAdmissionBo.studentId.registerNo='"+hldForm.getRegisterNo()+"'"+
								" and h.hlAdmissionBo.isActive=1";
								if(hldForm.getYear()!=null && !hldForm.getYear().isEmpty()){
									hqlQuery=hqlQuery +" and h.hlAdmissionBo.academicYear="+hldForm.getYear();
								}
			Query query = session.createQuery(hqlQuery);
			disciplinaryDetailsList = query.list();
		}catch (Exception e) {
			log.error("Error during getDisciplinaryDetailsByRegNoAndYear checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return disciplinaryDetailsList;
	}
	public boolean saveDocument(StudentEditForm studentEditForm){
		try{
		if (studentEditForm.getAchivementFile() != null) {
			FileOutputStream fos = new FileOutputStream(CMSConstants.EMPLOYEE_ACHIEVEMENT_FILE_FOLDER_PATH+"E"+studentEditForm.getStudentId()+".jpg");
			fos.write(studentEditForm.getAchivementFile());
			fos.close();
			studentEditForm.setAchivementFile(null);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
