package com.kp.cms.transactionsimpl.applicationform;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.applicationform.ProfessorPGIDetails;
import com.kp.cms.bo.applicationform.ProfessorPersonalData;
import com.kp.cms.bo.applicationform.ProfessorQualificationBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.applicationform.ApplicationRegistrationForm;
import com.kp.cms.handlers.applicationform.ApplicationRegistrationHandler;
import com.kp.cms.transactions.applicationform.IApplicationRegistrationTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ApplicationRegistrationHandlerTxnImp implements IApplicationRegistrationTransaction{
	
	private static volatile ApplicationRegistrationHandlerTxnImp applicationRegistrationHandlerTxnImp = null;
	private static final Log log = LogFactory.getLog(ApplicationRegistrationHandlerTxnImp.class);
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ApplicationRegistrationHandlerTxnImp getInstance() {
		if (applicationRegistrationHandlerTxnImp == null) {
			applicationRegistrationHandlerTxnImp = new ApplicationRegistrationHandlerTxnImp();
		}
		return applicationRegistrationHandlerTxnImp;
	}
	@Override
	public List<ProfessorQualificationBO> getDocumentList() throws Exception {
		
		Session session=null;
		
		List<ProfessorQualificationBO> documentList = null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from ProfessorQualificationBO q order by q.id");
			documentList = studentQuery.list();

		} catch (Exception e) {
			log.info("error in getDocumentList of ApplicationRegistrationHandlerTxnImp class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getDocumentList of ApplicationRegistrationHandlerTxnImp class.");
		return documentList;
	}
	@Override
	public boolean saveProfesserInformation(ProfessorPersonalData toQuaList) throws Exception {
		Session session=null;
		Transaction txn =null;;
		boolean isSaved = false;
		List<ProfessorPersonalData> professorObject=null;
		try{
			
			session=HibernateUtil.getSession();
			Query query = session
			.createQuery("from ProfessorPersonalData p where p.mobileNo= :mobileNo and p.firstName= :firstName");
			query.setString("mobileNo",toQuaList.getMobileNo());
			query.setString("firstName",toQuaList.getFirstName());
			professorObject=query.list();
			if(professorObject!=null && !professorObject.isEmpty()){
				org.hibernate.Transaction tx = session.beginTransaction();
				Iterator<ProfessorPersonalData> itr=professorObject.iterator();
				while(itr.hasNext()){
					ProfessorPersonalData prodata=itr.next();
				String SQL_QUERY = "delete  from ProfessorPersonalData p where p.mobileNo= :mobileNo and p.firstName= :firstName";
				Query query1 = session.createQuery(SQL_QUERY);
				query1.setString("mobileNo",prodata.getMobileNo());
				query1.setString("firstName",prodata.getFirstName());
				query1.executeUpdate();
				
				}
				tx.commit();
				session.flush();
				session.close();
			}
			
			
			session=HibernateUtil.getSession();
			txn = session.beginTransaction();
			txn.begin();
			session.save(toQuaList);
			txn.commit();
			isSaved = true;
			
		}catch (Exception e) {
			txn.rollback();
			log.info("error in saveProfesserInformation of ApplicationRegistrationHandlerTxnImp class.",e);
			throw new ApplicationException(e);
		}
		return isSaved;
	}
	@Override
	public ProfessorPersonalData getApplicationDetails(ApplicationRegistrationForm registrationForm) throws Exception {
		Session session=null;
		ProfessorPersonalData professorObject=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session
			.createQuery("from ProfessorPersonalData p where p.mobileNo= :mobileNo and p.firstName= :firstName");
			query.setString("mobileNo",registrationForm.getMobileNo());
			query.setString("firstName",registrationForm.getFirstName());
			professorObject=(ProfessorPersonalData) query.uniqueResult();
			session.flush();
			
			if(professorObject!=null){
				
				if(registrationForm.getEditDocument()!=null && registrationForm.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(registrationForm.getEditDocument().getFileName())){
					
					FormFile editDoc=registrationForm.getEditDocument();
					if(professorObject.getId() != 0 && professorObject.getIsPhoto()!=null && professorObject.getIsPhoto()){
						try {
							FileOutputStream fos = new FileOutputStream(CMSConstants.PROFESSOR_ONLINE_APPLICATION_IMAGE_PATH+professorObject.getId()+".jpg");
							fos.write(editDoc.getFileData());
							fos.close();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}catch (Exception e) {
			log.error("Error during getApplicationDetails getting..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return professorObject;
	}
	@Override
	public String generateApplicantRefNoOnline(ProfessorPGIDetails bo,ApplicationRegistrationForm registrationForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		String applicantRefNo="";
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				session.save(bo);
			}
			transaction.commit();
			int savedId=bo.getId();
			if(savedId>0){
				transaction=session.beginTransaction();
				applicantRefNo="BMCAJJ"+String.valueOf(savedId);
				
				System.out.println("$#$#$#$#$#APPLICAENT REFERENCE NUMBER CHANGED$#$#$#$#$#");
				bo.setApplicantRefNo(applicantRefNo);
				
				bo.setTxnRefNo(CMSConstants.PGI_MERCHANT_ID_FEDERAL+applicantRefNo);
				session.update(bo);
				transaction.commit();
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return applicantRefNo;
	}
	@Override
	public boolean updateReceivedStatusProfessort(ProfessorPGIDetails bo,ApplicationRegistrationForm registrationForm,ProfessorPersonalData personalData) throws Exception {

		log.info("Entered into updateReceivedStatusProfessort-AdmissionFormTransactionImpl");
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		registrationForm.setIsTnxStatusSuccess(false);
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				String query=" from ProfessorPGIDetails c where c.txnRefNo='"+bo.getTxnRefNo()
				+"' and c.txnAmount="+bo.getTxnAmount()+" and c.txnStatus='Pending'";
				System.out.println("=====================candidaterefno=="+bo.getTxnRefNo()+"===============");
				System.out.println("=====================applnamount=="+bo.getTxnAmount()+"===============");
				ProfessorPGIDetails applicantPgiBo=(ProfessorPGIDetails)session.createQuery(query).uniqueResult();
				if(applicantPgiBo!=null){
				applicantPgiBo.setTxnRefNo(bo.getTxnRefNo());
				applicantPgiBo.setTxnDate(bo.getTxnDate());
				applicantPgiBo.setTxnStatus(bo.getTxnStatus());
				applicantPgiBo.setTxnAmount(bo.getTxnAmount());
				applicantPgiBo.setMode("Online");
				
				ProfessorPersonalData data=new ProfessorPersonalData();
				data.setId(personalData.getId());
				applicantPgiBo.setProfessorPersonalId(data);
				
				}
				if(bo.getTxnStatus()!=null && bo.getTxnStatus().equalsIgnoreCase("SUCCESS")){
					registrationForm.setIsTnxStatusSuccess(true);
					registrationForm.setPaymentSuccess(true);
				}
				registrationForm.setApplicantRefNo(applicantPgiBo.getApplicantRefNo());
				registrationForm.setTransactionRefNO(bo.getTxnRefNo());
				session.update(applicantPgiBo);
				isUpdated=true;
				}
			
			transaction.commit();
			//session.flush();
			//session.close();
		} catch (Exception e) {
			System.out.println("Error during .................................updateReceivedStatus.........."+ e.getCause().toString());
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				//session.flush();
				session.close();
			}
			log.error("Error in updateReceivedStatusProfessort-ApplicationRegistrationHandlerTxnImp..."+e.getCause().toString());
			throw  new ApplicationException(e);
		}
		
        finally{
			
			//session.flush();
			session.close();
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return isUpdated;
	
	
	}
	@Override
	public Department getDepartmentNameThroughId(int id) throws Exception {

		
		Session session=null;
		
		Department department =null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Department d where d.id="+id);
			department = (Department) studentQuery.uniqueResult();

		} catch (Exception e) {
			log.info("error in getDepartmentNameThroughId of ApplicationRegistrationHandlerTxnImp class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getDocumentList of ApplicationRegistrationHandlerTxnImp class.");
		return department;
	
	}
	@Override
	public Religion getReligionNameThroughId(int id) throws Exception {


		
		Session session=null;
		
		Religion religion =null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Religion r where r.id="+id);
			religion = (Religion) studentQuery.uniqueResult();

		} catch (Exception e) {
			log.info("error in getReligionNameThroughId of ApplicationRegistrationHandlerTxnImp class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getDocumentList of ApplicationRegistrationHandlerTxnImp class.");
		return religion;
	
	
	}
	@Override
	public Caste getCasteNameThroughId(int id) throws Exception {
		
		Session session=null;
		
		Caste caste =null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery("from Caste c where c.id="+id);
			caste = (Caste) studentQuery.uniqueResult();

		} catch (Exception e) {
			log.info("error in getCasteNameThroughId of ApplicationRegistrationHandlerTxnImp class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getDocumentList of ApplicationRegistrationHandlerTxnImp class.");
		return caste;
	
	
	
	}
	@Override
	public ProfessorPersonalData getDetailsForApplicationPrint(ApplicationRegistrationForm registrationForm) throws Exception {
		Session session=null;
		ProfessorPersonalData professorObject=null;
		try{
			java.sql.Date dateOfBirth=CommonUtil.ConvertStringToSQLDate(registrationForm.getDateOfBirth());
			session=HibernateUtil.getSession();
			Query query = session
			.createQuery("select pg.professorPersonalId from ProfessorPGIDetails pg where pg.professorPersonalId.mobileNo=:mobileNo and pg.professorPersonalId.dateOfBirth= :dateOfBirth and pg.txnStatus='Success' ");
			query.setString("mobileNo",registrationForm.getMobileNo());
			query.setDate("dateOfBirth",dateOfBirth);
			professorObject=(ProfessorPersonalData) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			log.error("Error during getApplicationDetails getting..." + e);
			session.flush();
			throw new ApplicationException(e);
		}
		return professorObject;
	}

}
