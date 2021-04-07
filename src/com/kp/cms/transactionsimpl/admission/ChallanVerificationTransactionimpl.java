package com.kp.cms.transactionsimpl.admission;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.fees.FeeChallanVerificationDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ChallanVerificationForm;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.fee.FeeChallanVerificationTO;
import com.kp.cms.transactions.admission.IChallanVerificationTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ChallanVerificationTransactionimpl implements IChallanVerificationTransaction {

	/**
	 * Singleton object of ChallanVerificationTransactionimpl
	 */
	private static volatile ChallanVerificationTransactionimpl challanVerificationTransactionimpl = null;
	private static final Log log = LogFactory.getLog(ChallanVerificationTransactionimpl.class);
	private ChallanVerificationTransactionimpl() {
		
	}
	/**
	 * return singleton object of ChallanVerificationTransactionimpl.
	 * @return
	 */
	public static ChallanVerificationTransactionimpl getInstance() {
		if (challanVerificationTransactionimpl == null) {
			challanVerificationTransactionimpl = new ChallanVerificationTransactionimpl();
		}
		return challanVerificationTransactionimpl;
	}
	/* (non-Javadoc)
	 * querying the database to get the challan generated student list according to the input
	 * @see com.kp.cms.transactions.admission.IChallanVerificationTransaction#getStudentListBO(java.lang.String)
	 */
	@Override
	public List<FeePayment> getStudentListBO(String query) throws Exception {

		Session session = null;
		List<FeePayment> feePaymentBOList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			feePaymentBOList = session.createQuery(query).list();
			return feePaymentBOList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * setting the challanVerified falg to true for selected students
	 * @see com.kp.cms.transactions.admission.IChallanVerificationTransaction#updateVerifyFlag(com.kp.cms.forms.admission.ChallanVerificationForm)
	 */
	@SuppressWarnings("null")
	@Override
	public boolean updateVerifyFlag(ChallanVerificationForm challanForm) throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(challanForm.getStudentInfoList()!=null && !challanForm.getStudentInfoList().isEmpty()){
				Iterator<StudentTO> itr=challanForm.getStudentInfoList().iterator();
				while (itr.hasNext()) {
					StudentTO studentTO = (StudentTO) itr.next();
				
					if(studentTO.isTempChecked() && studentTO.getChecked1()!=null && studentTO.getChecked1().equalsIgnoreCase("On")){
						Student st= (Student)session.get(Student.class,studentTO.getId());
						AdmAppln admAppln= st.getAdmAppln();
						if(studentTO.getTotalNonAdditionalfees()>0)
						{
						if(studentTO.getChecked1()!=null && studentTO.getChecked1().equalsIgnoreCase("On"))
						{
						 admAppln.setIsChallanVerified(true);
						}
						else 
						{
						 admAppln.setIsChallanVerified(false);
						}
						admAppln.setLastModifiedDate(new Date());
						admAppln.setModifiedBy(challanForm.getUserId());
						session.update(admAppln);
						
						if(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
							String mobileNo="";
							if(admAppln.getPersonalData().getMobileNo1()!=null && !admAppln.getPersonalData().getMobileNo1().isEmpty()){
								if(admAppln.getPersonalData().getMobileNo1().trim().equals("0091") || admAppln.getPersonalData().getMobileNo1().trim().equals("+91")
										|| admAppln.getPersonalData().getMobileNo1().trim().equals("091") || admAppln.getPersonalData().getMobileNo1().trim().equals("0"))
									mobileNo = "91";
								else
									mobileNo=admAppln.getPersonalData().getMobileNo1();
							}else{
								mobileNo="91";
							}
							if(admAppln.getPersonalData().getMobileNo2()!=null && !admAppln.getPersonalData().getMobileNo2().isEmpty()){
								mobileNo=mobileNo+admAppln.getPersonalData().getMobileNo2();
							}
							if(mobileNo.length()==12){
								UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_ADMISSION_CONFIRMATION,admAppln);
							}
						
						}
						}
						
				
				String quer="from FeeChallanVerificationDetails f where f.admApplnId.id=" + admAppln.getId() +" and f.feePayId.id="+studentTO.getFeePaymentId();
				Query query=session.createQuery(quer);
				FeeChallanVerificationDetails FC=(FeeChallanVerificationDetails)query.uniqueResult();
				if(FC!=null && !FC.toString().isEmpty()){
					FC.setIsVerified(true);
					FC.setIsActive(true);
					FC.setLastModifiedDate(new Date());
					FC.setModifiedBy(challanForm.getUserId());
					session.update(FC);
				}
				else
				{
					FeeChallanVerificationDetails fcvd= new FeeChallanVerificationDetails();
					AdmAppln appln = new AdmAppln();
					appln.setId(admAppln.getId());
					fcvd.setAdmApplnId(appln);
					FeePayment f=new FeePayment();
					f.setId(studentTO.getFeePaymentId());
					fcvd.setFeePayId(f);
					fcvd.setIsVerified(true);
					fcvd.setIsActive(true);
					fcvd.setCreatedBy(challanForm.getUserId());
					fcvd.setCreatedDate(new Date());
					fcvd.setLastModifiedDate(new Date());
					fcvd.setModifiedBy(challanForm.getUserId());
					session.save(fcvd);
			}
		}else if(!studentTO.isTempChecked() && studentTO.getChecked1()!=null && studentTO.getChecked1().equalsIgnoreCase("On"))
		{		
				Student st= (Student)session.get(Student.class,studentTO.getId());
				AdmAppln admAppln= st.getAdmAppln();
				if(studentTO.getTotalNonAdditionalfees()>0)
				{
				 admAppln.setIsChallanVerified(false);
				
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(challanForm.getUserId());
				session.update(admAppln);
				
				if(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
					String mobileNo="";
					if(admAppln.getPersonalData().getMobileNo1()!=null && !admAppln.getPersonalData().getMobileNo1().isEmpty()){
						if(admAppln.getPersonalData().getMobileNo1().trim().equals("0091") || admAppln.getPersonalData().getMobileNo1().trim().equals("+91")
								|| admAppln.getPersonalData().getMobileNo1().trim().equals("091") || admAppln.getPersonalData().getMobileNo1().trim().equals("0"))
							mobileNo = "91";
						else
							mobileNo=admAppln.getPersonalData().getMobileNo1();
					}else{
						mobileNo="91";
					}
					if(admAppln.getPersonalData().getMobileNo2()!=null && !admAppln.getPersonalData().getMobileNo2().isEmpty()){
						mobileNo=mobileNo+admAppln.getPersonalData().getMobileNo2();
					}
					if(mobileNo.length()==12){
						UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_ADMISSION_CONFIRMATION,admAppln);
					}
				
				}
				}
				
		
		String quer="from FeeChallanVerificationDetails f where f.admApplnId.id=" + admAppln.getId() +" and f.feePayId.id="+studentTO.getFeePaymentId();
		Query query=session.createQuery(quer);
		FeeChallanVerificationDetails FC=(FeeChallanVerificationDetails)query.uniqueResult();
		if(FC!=null && !FC.toString().isEmpty()){
			FC.setIsVerified(false);
			FC.setIsActive(true);
			FC.setLastModifiedDate(new Date());
			FC.setModifiedBy(challanForm.getUserId());
			session.update(FC);
		}
		else
		{
			FeeChallanVerificationDetails fcvd= new FeeChallanVerificationDetails();
			AdmAppln appln = new AdmAppln();
			appln.setId(admAppln.getId());
			fcvd.setAdmApplnId(appln);
			FeePayment f=new FeePayment();
			f.setId(studentTO.getFeePaymentId());
			fcvd.setFeePayId(f);
			fcvd.setIsVerified(false);
			fcvd.setIsActive(true);
			fcvd.setCreatedBy(challanForm.getUserId());
			fcvd.setCreatedDate(new Date());
			fcvd.setLastModifiedDate(new Date());
			fcvd.setModifiedBy(challanForm.getUserId());
			session.save(fcvd);
		}
		}
		
		}
		}
			flagSet=true;
			txn.commit();
			return flagSet;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while updating selected candidates data.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
			
		}
	}
}
