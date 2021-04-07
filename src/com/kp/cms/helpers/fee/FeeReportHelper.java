package com.kp.cms.helpers.fee;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.to.fee.FeeReportTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author kalyan.c
 * Helper for Fee Report
 */
public class FeeReportHelper {

	
	/**
	 * @param feePaymentBO
	 * @return
	 * This method is used to convert BO's to TO's
	 */
	public static List<FeeReportTO> convertBoToTo(List<FeePayment> feePaymentBO) {
		List feeReportTOList = new ArrayList();
		if(feePaymentBO!=null){
			Iterator<FeePayment> itrFPBO = feePaymentBO.iterator();

			while (itrFPBO.hasNext()) {
				FeePayment feePayment = (FeePayment) itrFPBO.next();
				FeeReportTO feeReportTO = new FeeReportTO();				
				
				if(feePayment.getApplicationNo()!=null){
					feeReportTO.setApplicationNo(feePayment.getApplicationNo());					
				}
				if(feePayment.getRegistrationNo()!=null){
					feeReportTO.setRegistrationNo(feePayment.getRegistrationNo());					
				}
				if(feePayment.getIsCancelChallan()!=null){
					if(!feePayment.getIsCancelChallan()){
						feeReportTO.setStatus("paid");}
					else if(feePayment.getIsCancelChallan()){
						feeReportTO.setStatus("canceled");
					}
				}				
				if(feePayment.getBillNo()!=null){
					feeReportTO.setBillNo(feePayment.getBillNo());
				}
				if(feePayment.getChallenNo()!=null){
					feeReportTO.setChallenNo(feePayment.getChallenNo());
				}
				if(feePayment.getChallenPrintedDate()!=null){
					feeReportTO.setChallenPrintedDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
				}
				if(feePayment.getConcessionVoucherNo()!=null){
					feeReportTO.setConsessionReferenceNo(feePayment.getConcessionVoucherNo());
				}
				if(feePayment.getFeePaidDate()!=null){
					feeReportTO.setFeePaidDate(CommonUtil.getStringDate(feePayment.getFeePaidDate()));
				}
				/*if(feePayment.getInstallmentVoucherNo()!=null ){
					feeReportTO.setInstallmentReferenceNo(feePayment.getInstallmentVoucherNo());
				}*/
				if(feePayment.getIsChallenPrinted()!=null){
					feeReportTO.setIsChallenPrinted(String.valueOf(feePayment.getIsChallenPrinted()));
				}
				if(feePayment.getIsCompletlyPaid()!=null){
					feeReportTO.setIsCompletlyPaid(String.valueOf(feePayment.getIsCompletlyPaid()));
				}
				if(feePayment.getIsFeePaid()!=null){
					feeReportTO.setIsFeePaid(String.valueOf(feePayment.getIsFeePaid()));
				}
				if(feePayment.getFeePaymentMode()!=null){
					feeReportTO.setPaymentMode(feePayment.getFeePaymentMode().getName());
				}
				if(feePayment.getTotalAmount()!=null){
					feeReportTO.setTotalAmount(String.valueOf(feePayment.getTotalAmount()));
				}
				if(feePayment.getTotalBalanceAmount()!=null){
					feeReportTO.setTotalBalanceAmount(String.valueOf(feePayment.getTotalBalanceAmount()));
				}
				if(feePayment.getTotalConcessionAmount()!=null){
					feeReportTO.setTotalConcessionAmount(String.valueOf(feePayment.getTotalConcessionAmount()));
				}
				if(feePayment.getTotalFeePaid()!=null){
					feeReportTO.setTotalFeePaid(String.valueOf(feePayment.getTotalFeePaid()));
				}
				/*if(feePayment.getTotalInstallmentAmount()!=null){
					feeReportTO.setTotalInstallmentAmount(String.valueOf(feePayment.getTotalInstallmentAmount()));
				}*/
				
				if(feePayment.getApplicationNo()!=null){					
						if(feePayment.getFeePaymentDetails()!=null){
							List feeAccountList =  new ArrayList();
							List feeAccountTotalAmountList =  new ArrayList();							
							List concessionamount =  new ArrayList();
							List instalmentamount =  new ArrayList();
							List amountpaid =  new ArrayList();
							List amountbalance =  new ArrayList();
							List excessshortamount =  new ArrayList();
							Set paymentDetailsSet = feePayment.getFeePaymentDetails();
							if(paymentDetailsSet!=null){
								FeePaymentDetail feePaymentDetail = new FeePaymentDetail();
								Iterator itr = paymentDetailsSet.iterator();
								
								while(itr.hasNext()){
									feePaymentDetail = (FeePaymentDetail)itr.next();
									 if( feePaymentDetail.getFeeAccount().getName()!=null){
										 feeAccountList.add(feePaymentDetail.getFeeAccount().getName());
									 }
									 if( feePaymentDetail.getTotalAmount()!=null){
										 feeAccountTotalAmountList.add(feePaymentDetail.getTotalAmount());
									 }
									 if( feePaymentDetail.getConcessionAmount()!=null){
										 concessionamount.add(feePaymentDetail.getConcessionAmount());
									 }
									  if( feePaymentDetail.getAmountPaid()!=null){
										 amountpaid.add(feePaymentDetail.getAmountPaid());
									 }
									 if( feePaymentDetail.getAmountBalance()!=null){
										 amountbalance.add(feePaymentDetail.getAmountBalance());
									 }
									/* if( feePaymentDetail.getInstallmentAmount()!=null){
										 instalmentamount.add(feePaymentDetail.getInstallmentAmount());
									 }
									
									 if( feePaymentDetail.getExcessShortAmount()!=null){
										 excessshortamount.add(feePaymentDetail.getExcessShortAmount());
									 }*/
									
								}
								feeReportTO.setFeeAccountList(feeAccountList);	
								feeReportTO.setFeeAccountTotalAmountList(feeAccountTotalAmountList);
								feeReportTO.setConcessionAmount(concessionamount);
								feeReportTO.setInstalmentAmount(instalmentamount);
								feeReportTO.setAmountPaid(amountpaid);
								feeReportTO.setAmountBalance(amountbalance);
								feeReportTO.setExcessShortAmount(excessshortamount);
							}
						}

						feeReportTOList.add(feeReportTO);
						
					}
				
		}
		
		}
		return feeReportTOList;
	}
	
	/**
	 * @param feePaymentBO
	 * @return
	 * This method is used to convert BO's to TO's
	 */
	public static List<FeeReportTO> convertBoToToObject(List feePaymentBO) {
		List feeReportTOList = new ArrayList();
		
		if(feePaymentBO!=null){
		
			
			Iterator itrFPBO = feePaymentBO.iterator();

			while (itrFPBO.hasNext()) {
//				Object[] feePaymentObject =  (Object[]) it.next();
				
				FeePayment feePayment = (FeePayment) itrFPBO.next();
				
				FeeReportTO feeReportTO = new FeeReportTO();				
				
				if(feePayment.getApplicationNo()!=null){
					feeReportTO.setApplicationNo(feePayment.getApplicationNo());					
				}
				if(feePayment.getRegistrationNo()!=null){
					feeReportTO.setRegistrationNo(feePayment.getRegistrationNo());					
				}
				if(feePayment.getIsCancelChallan()!=null){
					if(!feePayment.getIsCancelChallan()){
						feeReportTO.setStatus("paid");}
					else if(feePayment.getIsCancelChallan()){
						feeReportTO.setStatus("canceled");
					}
				}			
				if(feePayment.getBillNo()!=null){
					feeReportTO.setBillNo(feePayment.getBillNo());
				}
				if(feePayment.getChallenNo()!=null){
					feeReportTO.setChallenNo(feePayment.getChallenNo());
				}
				if(feePayment.getChallenPrintedDate()!=null){
					feeReportTO.setChallenPrintedDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
				}
				if(feePayment.getConcessionVoucherNo()!=null){
					feeReportTO.setConsessionReferenceNo(feePayment.getConcessionVoucherNo());
				}
				if(feePayment.getFeePaidDate()!=null){
					feeReportTO.setFeePaidDate(CommonUtil.getStringDate(feePayment.getFeePaidDate()));
				}
				
				if(feePayment.getIsChallenPrinted()!=null){
					feeReportTO.setIsChallenPrinted(String.valueOf(feePayment.getIsChallenPrinted()));
				}
				if(feePayment.getIsCompletlyPaid()!=null){
					feeReportTO.setIsCompletlyPaid(String.valueOf(feePayment.getIsCompletlyPaid()));
				}
				if(feePayment.getIsFeePaid()!=null){
					feeReportTO.setIsFeePaid(String.valueOf(feePayment.getIsFeePaid()));
				}
				if(feePayment.getFeePaymentMode()!=null){
					feeReportTO.setPaymentMode(feePayment.getFeePaymentMode().getName());
				}
				if(feePayment.getTotalAmount()!=null){
					feeReportTO.setTotalAmount(String.valueOf(feePayment.getTotalAmount()));
				}
				if(feePayment.getTotalBalanceAmount()!=null){
					feeReportTO.setTotalBalanceAmount(String.valueOf(feePayment.getTotalBalanceAmount()));
				}
				if(feePayment.getTotalConcessionAmount()!=null){
					feeReportTO.setTotalConcessionAmount(String.valueOf(feePayment.getTotalConcessionAmount()));
				}
				if(feePayment.getTotalFeePaid()!=null){
					feeReportTO.setTotalFeePaid(String.valueOf(feePayment.getTotalFeePaid()));
				}
				/*if(feePayment.getInstallmentVoucherNo()!=null ){
					feeReportTO.setInstallmentReferenceNo(feePayment.getInstallmentVoucherNo());
				}
				if(feePayment.getTotalInstallmentAmount()!=null){
					feeReportTO.setTotalInstallmentAmount(String.valueOf(feePayment.getTotalInstallmentAmount()));
				}*/
				if(feePayment.getApplicationNo()!=null){
					
						if(feePayment.getFeePaymentDetails()!=null){
							List feeAccountList =  new ArrayList();
							List feeAccountTotalAmountList =  new ArrayList();							
							List concessionamount =  new ArrayList();
							List instalmentamount =  new ArrayList();
							List amountpaid =  new ArrayList();
							List amountbalance =  new ArrayList();
							List excessshortamount =  new ArrayList();
							Set paymentDetailsSet = feePayment.getFeePaymentDetails();
							if(paymentDetailsSet!=null){								
								FeePaymentDetail feePaymentDetail = new FeePaymentDetail();
								Iterator itr = paymentDetailsSet.iterator();
								
								while(itr.hasNext()){
									feePaymentDetail = (FeePaymentDetail)itr.next();
									 if( feePaymentDetail.getFeeAccount().getName()!=null){
										 feeAccountList.add(feePaymentDetail.getFeeAccount().getName());
									 }
									 if( feePaymentDetail.getTotalAmount()!=null){
										 feeAccountTotalAmountList.add(feePaymentDetail.getTotalAmount());
									 }
									 if( feePaymentDetail.getConcessionAmount()!=null){
										 concessionamount.add(feePaymentDetail.getConcessionAmount());
									 }
									 if( feePaymentDetail.getAmountPaid()!=null){
										 amountpaid.add(feePaymentDetail.getAmountPaid());
									 }
									 if( feePaymentDetail.getAmountBalance()!=null){
										 amountbalance.add(feePaymentDetail.getAmountBalance());
									 }
									 /*if( feePaymentDetail.getExcessShortAmount()!=null){
										 excessshortamount.add(feePaymentDetail.getExcessShortAmount());
									 }
									 if( feePaymentDetail.getInstallmentAmount()!=null){
										 instalmentamount.add(feePaymentDetail.getInstallmentAmount());
									 }*/
									 
									
								}
								feeReportTO.setFeeAccountList(feeAccountList);	
								feeReportTO.setFeeAccountTotalAmountList(feeAccountTotalAmountList);
								feeReportTO.setConcessionAmount(concessionamount);
								feeReportTO.setInstalmentAmount(instalmentamount);
								feeReportTO.setAmountPaid(amountpaid);
								feeReportTO.setAmountBalance(amountbalance);
								feeReportTO.setExcessShortAmount(excessshortamount);
							}
						}

						feeReportTOList.add(feeReportTO);
						
					
				}
		}
		
		}
		return feeReportTOList;
	}
	
}
