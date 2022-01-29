package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.fees.FeeChallanVerificationDetails;
import com.kp.cms.forms.admission.ChallanVerificationForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.fee.FeeChallanVerificationTO;
import com.kp.cms.utilities.CommonUtil;

public class ChallanVerificationHelper {


	/**
	 * Singleton object of ChallanVerificationHelper
	 */
	private static volatile ChallanVerificationHelper challanVerificationHelper = null;
	private static final Log log = LogFactory.getLog(ChallanVerificationHelper.class);
	private ChallanVerificationHelper() {
		
	}
	/**
	 * return singleton object of ChallanVerificationHelper.
	 * @return
	 */
	public static ChallanVerificationHelper getInstance() {
		if (challanVerificationHelper == null) {
			challanVerificationHelper = new ChallanVerificationHelper();
		}
		return challanVerificationHelper;
	}
	/**
	 * returning the query to get the challan generated student list for input selected
	 * @param challanForm
	 * @return
	 * @throws Exception
	 */
	public String getQuery(ChallanVerificationForm challanForm) throws Exception{
		String query=null;
		if(challanForm.getChallanDate()!=null && !challanForm.getChallanDate().trim().isEmpty()){
			Date dateinSQlFormat=CommonUtil.ConvertStringToSQLDate(challanForm.getChallanDate());
		 /*query= " select f from FeePayment f " +
				" join f.feePaymentDetails fds join fds.feePaymentDetailFeegroups fd " +
				" join f.feePaymentApplicantDetailses fas where f.isCancelChallan=0 and f.student.isActive=1 " +
				" and f.student.isAdmitted=1 and (f.student.isHide=null or f.student.isHide=0)  and f.student.admAppln.isCancelled=0" +
				" and fas.semesterNo=1 and fd.isOptional=0 and f.challenPrintedDate='" +dateinSQlFormat+ "'";*/
			
			query= " select f from FeePayment f " +
			" join f.feePaymentDetails fds " +
			" where f.isCancelChallan=0 and f.student.isActive=1 " +
			" and f.student.isAdmitted=1 and (f.student.isHide=null or f.student.isHide=0)  and f.student.admAppln.isCancelled=0" +
			" and f.academicYear = f.student.admAppln.appliedYear "+
			" and f.challenPrintedDate='" +dateinSQlFormat+ "' group by f.billNo ";
				
		}
		else if (challanForm.getApplicationNo()!=null && !challanForm.getApplicationNo().trim().isEmpty()){
			 query= " select f from FeePayment f " +
				" join f.feePaymentDetails fds " +
				" where f.isCancelChallan=0 and f.student.isActive=1 " +
				" and f.student.isAdmitted=1 and (f.student.isHide=null or f.student.isHide=0)  and f.student.admAppln.isCancelled=0" +
				" and f.academicYear = f.student.admAppln.appliedYear " +
				" and f.student.admAppln.applnNo=" +challanForm.getApplicationNo()+
				" group by f.billNo ";
		}
		return query;
	}
	/**
	 * converting FeePaymentBO to StudentTO to set in form
	 * @param feeList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBostoTO(List<FeePayment> feeList) throws Exception {
		List<StudentTO> stuList=null;
		List<FeeChallanVerificationTO> chTolist=null;
		if(feeList!=null && !feeList.isEmpty()){
			Iterator<FeePayment> itr=feeList.iterator();
			stuList= new ArrayList<StudentTO>();
			chTolist= new ArrayList<FeeChallanVerificationTO>();
			while (itr.hasNext()) {
				FeePayment feePayment = (FeePayment) itr.next();
				StudentTO stuTo=new StudentTO();
				stuTo.setApplicationNo(feePayment.getStudent()!=null?feePayment.getStudent().getAdmAppln().getApplnNo():0);
				if(feePayment.getStudent()!=null)
				stuTo.setStudentName(feePayment.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null?feePayment.getStudent().getAdmAppln().getPersonalData().getFirstName():"");
				stuTo.setCourseName(feePayment.getStudent()!=null?feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getName():"");
				stuTo.setChallanNo(feePayment.getBillNo()!=null?feePayment.getBillNo():"");
				stuTo.setId(feePayment.getStudent().getId());
				if(feePayment.getStudent()!=null && feePayment.getStudent().getAdmAppln().getIsChallanVerified()!=null){
					if(feePayment.getStudent().getAdmAppln().getIsChallanVerified()){
					//	stuTo.setChecked1("on");
					}
					
				}
				stuTo.setAdmApplnId(feePayment.getStudent().getAdmAppln().getId());
				stuTo.setFeePaymentId(feePayment.getId());
				Iterator<FeePaymentDetail> itr1=feePayment.getFeePaymentDetails().iterator();
				while (itr1.hasNext()) {
					FeePaymentDetail feePayDet = (FeePaymentDetail) itr1.next();
					if(feePayDet!=null){
					if(feePayDet.getFeePayment().getId()==feePayment.getId()){
						if(feePayDet.getTotalNonAdditionalAmount()!=null)
						stuTo.setTotalNonAdditionalfees(feePayDet.getTotalNonAdditionalAmount().intValue());
						else
						stuTo.setTotalNonAdditionalfees(0);
					}
				}
				}
			/*	if(feePayment.getFeeChallanVerifydetails()!=null){
				Iterator<FeeChallanVerificationDetails> itr2=feePayment.getFeeChallanVerifydetails().iterator();
				while (itr2.hasNext()) {
					FeeChallanVerificationDetails feeChVerDet = (FeeChallanVerificationDetails) itr2.next();
					if(feeChVerDet!=null){
					FeeChallanVerificationTO chTo=new FeeChallanVerificationTO();
					if((feeChVerDet.getFeePayId().getId()==feePayment.getId()) && feeChVerDet.getAdmApplnId().getId()>0){
						chTo.setId(feeChVerDet.getId());
						chTo.setAdmApplnId(String.valueOf(feeChVerDet.getAdmApplnId().getId()));
						chTo.setFeesPayId(String.valueOf(feeChVerDet.getFeePayId().getId()));
						chTo.setIsVerified(String.valueOf(feeChVerDet.getIsVerified()));
						chTolist.add(chTo);
						if(feeChVerDet.getIsVerified()){
						stuTo.setChecked1("on");
						stuTo.setTempChecked(true);
						}
					}
				}
				}
				}*/
				stuTo.setChTolist(chTolist);
				stuList.add(stuTo);
			}
		}
		return stuList;
	}
}
