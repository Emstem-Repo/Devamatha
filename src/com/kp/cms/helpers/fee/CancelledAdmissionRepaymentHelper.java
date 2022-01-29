package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCancellationDetails;
import com.kp.cms.forms.fee.CancelledAdmissionRepaymentForm;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.fee.ICancelledAdmissionRepaymentTransaction;
import com.kp.cms.transactionsimpl.fee.CancelledAdmissionRepaymentTransactionimpl;
import com.kp.cms.utilities.CommonUtil;

public class CancelledAdmissionRepaymentHelper {
	/**
	 * Singleton object of CancelledAdmissionRepaymentHelper
	 */
	private static volatile CancelledAdmissionRepaymentHelper cancelledAdmissionRepaymentHelper = null;
	private static final Log log = LogFactory.getLog(CancelledAdmissionRepaymentHelper.class);
	private CancelledAdmissionRepaymentHelper() {
		
	}
	/**
	 * return singleton object of CancelledAdmissionRepaymentHelper.
	 * @return
	 */
	public static CancelledAdmissionRepaymentHelper getInstance() {
		if (cancelledAdmissionRepaymentHelper == null) {
			cancelledAdmissionRepaymentHelper = new CancelledAdmissionRepaymentHelper();
		}
		return cancelledAdmissionRepaymentHelper;
	}
	/**
	 * converting the student BO to AdmApplnTO
	 * @param stuBo
	 * @return
	 * @throws Exception
	 */
	public AdmApplnTO convertBOtoTO(Student stuBo,CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception {
		AdmApplnTO admTo=null;
		if(stuBo!=null){
			 admTo=new AdmApplnTO();
			 admTo.setId(stuBo.getAdmAppln().getId());
			 admTo.setApplnNo(stuBo.getAdmAppln().getApplnNo());
			 admTo.setRegNo(stuBo.getRegisterNo()!=null?stuBo.getRegisterNo():"");
			 admTo.setStudentName(stuBo.getAdmAppln().getPersonalData().getFirstName()!=null?stuBo.getAdmAppln().getPersonalData().getFirstName():"");
			 admTo.setCourseName(stuBo.getAdmAppln().getCourseBySelectedCourseId()!=null?stuBo.getAdmAppln().getCourseBySelectedCourseId().getName():"");
			 admTo.setAdmissionCancelDate(stuBo.getAdmAppln().getCancelDate()!=null?
					 CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(stuBo.getAdmAppln().getCancelDate()), "dd-MMM-yyyy","dd-MM-yyyy"):"");
			 admTo.setAdmissionCancelRemarks(stuBo.getAdmAppln().getCancelRemarks()!=null?stuBo.getAdmAppln().getCancelRemarks():"");
			 if(stuBo.getAdmAppln().getStudentCancellationDetails()!=null && !stuBo.getAdmAppln().getStudentCancellationDetails().isEmpty()){
				 Set<StudentCancellationDetails> cancelDetails=stuBo.getAdmAppln().getStudentCancellationDetails();
				 Iterator<StudentCancellationDetails> itr=cancelDetails.iterator();
				 while (itr.hasNext()) {
					StudentCancellationDetails studentCancellationDetails = (StudentCancellationDetails) itr
							.next();
					cancelledAdmissionRepaymentForm.setChequeNo(studentCancellationDetails.getChequeNo()!=null?studentCancellationDetails.getChequeNo():"");
					cancelledAdmissionRepaymentForm.setChequeDate(studentCancellationDetails.getChequeDate()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(studentCancellationDetails.getChequeDate()), "dd-MMM-yyyy","dd/MM/yyyy"):"");
					cancelledAdmissionRepaymentForm.setChequeIssuedDate(studentCancellationDetails.getChequeIssuedDate()!=null?CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(studentCancellationDetails.getChequeIssuedDate()), "dd-MMM-yyyy","dd/MM/yyyy"):""); 
					cancelledAdmissionRepaymentForm.setRepaidAmt(studentCancellationDetails.getRepaidAmount()!=null?studentCancellationDetails.getRepaidAmount().toString():"");
				 }
				
			 }
		}
		return admTo;
	}
	/**
	 * Convet form details to BO
	 * @param cancelledAdmissionRepaymentForm
	 * @return
	 * @throws Exception
	 */
	public static StudentCancellationDetails convertTOtoBO(CancelledAdmissionRepaymentForm cancelledAdmissionRepaymentForm) throws Exception {
		ICancelledAdmissionRepaymentTransaction txn=CancelledAdmissionRepaymentTransactionimpl.getInstance();
		StudentCancellationDetails stuCancelledDetailsBO=null;
		if(cancelledAdmissionRepaymentForm.getAdmApplnTo()!=null){
		 stuCancelledDetailsBO=txn.checkDuplicate(cancelledAdmissionRepaymentForm);
		if(stuCancelledDetailsBO==null){
		 stuCancelledDetailsBO=new StudentCancellationDetails();
		 stuCancelledDetailsBO.setCreatedBy(cancelledAdmissionRepaymentForm.getUserId());
		 stuCancelledDetailsBO.setCreatedDate(new Date());
		 AdmAppln admAppln=new AdmAppln();
		 admAppln.setId(cancelledAdmissionRepaymentForm.getAdmApplnTo().getId());
		 stuCancelledDetailsBO.setAdmAppln(admAppln);
		}
		
		stuCancelledDetailsBO.setChequeNo(cancelledAdmissionRepaymentForm.getChequeNo());
		stuCancelledDetailsBO.setChequeDate(CommonUtil.ConvertStringToSQLDate(cancelledAdmissionRepaymentForm.getChequeDate()));
		stuCancelledDetailsBO.setChequeIssuedDate(CommonUtil.ConvertStringToSQLDate(cancelledAdmissionRepaymentForm.getChequeIssuedDate()));
		
		if(cancelledAdmissionRepaymentForm.getRepaidAmt()!=null  && !cancelledAdmissionRepaymentForm.getRepaidAmt().trim().isEmpty())
			stuCancelledDetailsBO.setRepaidAmount(new BigDecimal(cancelledAdmissionRepaymentForm.getRepaidAmt()));
		else
			stuCancelledDetailsBO.setRepaidAmount(new BigDecimal(0));
		
		stuCancelledDetailsBO.setModifiedBy(cancelledAdmissionRepaymentForm.getUserId());
		stuCancelledDetailsBO.setLastModifiedDate(new Date());
		}
		return stuCancelledDetailsBO;
	}
}
