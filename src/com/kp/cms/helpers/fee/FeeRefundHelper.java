package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.fees.FeeRefund;
import com.kp.cms.forms.fee.FeeRefundForm;
import com.kp.cms.to.fee.FeeRefundTo;
import com.kp.cms.transactions.fee.IFeeRefundTransaction;
import com.kp.cms.transactionsimpl.fee.FeeRefundTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class FeeRefundHelper {
	
	IFeeRefundTransaction transaction = FeeRefundTxnImpl.getInstance();
	private static Log log = LogFactory.getLog(FeeRefundHelper.class);
    public static volatile FeeRefundHelper feeRefundHelper = null;
    
    
    
    /**
     * @return
     */
    public static FeeRefundHelper getInstance() {
		if (feeRefundHelper == null) {
			feeRefundHelper = new FeeRefundHelper();
			return feeRefundHelper;
		}
		return feeRefundHelper;
	}



	public boolean setStudentDataToForm(FeeRefundForm refundForm,List<Object[]> paymentList)throws Exception {
		log.info("setStudentDataToForm method called in helper");
		Map<Integer, FeeRefundTo> refundMap=new HashMap<Integer, FeeRefundTo>();
		String studentId=null;
		String challanDate=null;
		boolean isNext=false;
		List<FeeRefundTo> studentList=new ArrayList<FeeRefundTo>();
		   if(paymentList!=null && !paymentList.isEmpty()){
			   for (Object[] feePayment : paymentList) {
				   FeeRefundTo refundTo=new FeeRefundTo();
				  if(feePayment[0]!=null && !feePayment[0].toString().isEmpty()){
						refundTo.setRegisterNo(feePayment[0].toString().trim());
				}if(feePayment[1]!=null && !feePayment[1].toString().isEmpty()){
					refundTo.setStudentName(feePayment[1].toString().trim());
				}if(feePayment[2]!=null && !feePayment[2].toString().isEmpty()){
					refundTo.setClassName(feePayment[2].toString());
				}if(feePayment[3]!=null && !feePayment[3].toString().isEmpty()){
					refundTo.setChallanAmount(feePayment[3].toString());
				}if(feePayment[4]!=null && !feePayment[4].toString().isEmpty()){
					refundTo.setFeeGroupName(feePayment[4].toString());
				}if(feePayment[5]!=null && !feePayment[5].toString().isEmpty()){
					refundTo.setChallanPrintedDate(CommonUtil.ConvertStringToDate1(feePayment[5].toString()));
					challanDate=CommonUtil.ConvertStringToDate1(feePayment[5].toString());
				}if(feePayment[6]!=null && !feePayment[6].toString().isEmpty()){
					refundTo.setStudentId(Integer.parseInt(feePayment[6].toString()));
					refundMap.put(Integer.parseInt(feePayment[6].toString()), refundTo);
					studentId=feePayment[6].toString();
					studentList.add(refundTo);
				}
			}
			   if(studentList.size()>1){
				   refundForm.setStudentList(studentList);
			   }else{
				   refundForm.setStudentList(null);
				   if(studentId!=null && challanDate!=null){
					   refundForm.setStudentIdForDisplay(studentId);
					   refundForm.setChallanDate(challanDate);
				   }
			   }
			   if(!refundMap.isEmpty()){
				   refundForm.setRefundMap(refundMap);
				   isNext=true;
			   }   
		   }
		return isNext;
	}



	/**
	 * @param refund
	 * @param refundForm
	 */
	public void convertBoToForm(FeeRefund refund, FeeRefundForm refundForm)throws Exception {
	   if(refund!=null){
		  String[] amount=String.valueOf(refund.getRefundAmount()).split("\\.");
		  refundForm.setRefundAmount(amount[0]);
		  refundForm.setRefundDate(CommonUtil.formatDates(refund.getRefundDate()));
		  refundForm.setRefundMode(String.valueOf(refund.getRefundMode().getId()));
		  refundForm.setRefundId(String.valueOf(refund.getId()));
		  refundForm.setStudentIdForDisplay(String.valueOf(refund.getStudent().getId()));
	   }
	}



	/**
	 * @param refundForm
	 * @param mode
	 * @return
	 */
	public FeeRefund convertFormToBo(FeeRefundForm refundForm,String mode)throws Exception {
         FeeRefund refund=new FeeRefund();
         refund.setAcademicYear(Integer.parseInt(refundForm.getAcademicYear()));
         refund.setChallanNo(refundForm.getChallanNo());
         refund.setChallanAmount(new BigDecimal(refundForm.getChallanAmount()));
         refund.setRefundDate(CommonUtil.ConvertStringToDate(refundForm.getRefundDate()));
         refund.setRefundAmount(new BigDecimal(refundForm.getRefundAmount()));
         Student student=new Student();
         student.setId(Integer.parseInt(refundForm.getStudentId()));
         refund.setStudent(student);
         FeePaymentMode paymentMode=new FeePaymentMode();
         paymentMode.setId(Integer.parseInt(refundForm.getRefundMode()));
         refund.setRefundMode(paymentMode);
         refund.setChallanDate(CommonUtil.ConvertStringToDate(refundForm.getChallanDate()));
         if(mode.equalsIgnoreCase("Add")){
	         refund.setCreatedBy(refundForm.getUserId());
	         refund.setCreatedDate(new Date());
         }else{
        	 refund.setId(Integer.parseInt(refundForm.getRefundId().trim()));
        	 refund.setModifiedBy(refundForm.getUserId());
        	 refund.setLastModifiedDate(new Date());
         }
         refund.setIsActive(true);
         return refund;
	}


}
