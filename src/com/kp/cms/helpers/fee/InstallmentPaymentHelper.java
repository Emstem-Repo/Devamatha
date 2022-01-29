package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.forms.fee.InstallmentPaymentForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.fee.FeePaidDateTO;
import com.kp.cms.to.fee.InstallmentPaymentTO;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.utilities.AccountComparator;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.FeePaymentDetailComparator;

public class InstallmentPaymentHelper {
	private static final Log log = LogFactory.getLog(InstallmentPaymentHelper.class);
	public static InstallmentPaymentHelper installmentPaymentHelper = null;

	private InstallmentPaymentHelper(){
		
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static InstallmentPaymentHelper getInstance() {
		if (installmentPaymentHelper == null) {
			installmentPaymentHelper = new InstallmentPaymentHelper();
		}
		return installmentPaymentHelper;
	}
	/**
	 * 
	 * @param paymentTypeBOList
	 * @return List of Payment Types
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> copyFeePaymentBOToTO(
			List<FeePaymentMode> paymentTypeBOList)throws Exception {
		log.info("Inside of copyFeePaymentBOToTO of InstallmentPaymentHelper");
		List<SingleFieldMasterTO> paymentModeList = new ArrayList<SingleFieldMasterTO>();
		SingleFieldMasterTO singleFieldMasterTO = null;
		if(paymentTypeBOList!=null){
			Iterator<FeePaymentMode> iterator = paymentTypeBOList.iterator();
			while (iterator.hasNext()) {
				FeePaymentMode feePaymentMode = iterator.next();
				singleFieldMasterTO = new SingleFieldMasterTO();
				if(feePaymentMode.getName()!=null){
					singleFieldMasterTO.setName(feePaymentMode.getName());
				}
				singleFieldMasterTO.setId(feePaymentMode.getId());
				paymentModeList.add(singleFieldMasterTO);
			}
		}
		log.info("End of copyFeePaymentBOToTO of InstallmentPaymentHelper");
		return paymentModeList;
	}
	/**
	 * 
	 * @param paymentForm
	 * @returns the selection search criteria
	 */
	public String createSearchCriteria(InstallmentPaymentForm paymentForm) {
		log.info("Inside of createSearchCriteria of InstallmentPaymentHelper");
		String searchCriteria="";
		searchCriteria = "from FeePayment fee where fee.isCompletlyPaid = 0 and fee.isCancelChallan=0 and" +
		" fee.student.admAppln.appliedYear = " + Integer.valueOf(paymentForm.getAcademicYear());
		if(paymentForm.getSearchBy().equals("1")){
			searchCriteria = searchCriteria + " and fee.student.admAppln.applnNo = " + Integer.valueOf(paymentForm.getSearchByValue());
		}
		else if(paymentForm.getSearchBy().equals("2")){
			searchCriteria = searchCriteria + " and fee.student.registerNo = " + "'" + paymentForm.getSearchByValue()+ "'";
		}
		else if(paymentForm.getSearchBy().equals("3")){
			searchCriteria = searchCriteria + " and fee.student.rollNo = " + "'" + paymentForm.getSearchByValue()+ "'";
		}
		log.info("Leaving of createSearchCriteria of InstallmentPaymentHelper");
		return searchCriteria;
	}
	/**
	 * 
	 * @param feePaymentList
	 * @param paymentForm
	 * @returns List of FeePayment TOs
	 * @throws Exception
	 */
	public List<InstallmentPaymentTO> populateFeePaymentBOToTO(List<FeePayment> feePaymentList, InstallmentPaymentForm paymentForm)throws Exception {
		log.info("Inside of populateFeePaymentBOToTO of InstallmentPaymentHelper");
		List<InstallmentPaymentTO> paymentTOList = new ArrayList<InstallmentPaymentTO>(); 
		InstallmentPaymentTO paymentTO = null;
		AccountHeadTO accountHeadTO = null;
		FeePaidDateTO feePaidDateTO = null;
		List<AccountHeadTO>accountNameList = new ArrayList<AccountHeadTO>();
		int count = 0;
			if(feePaymentList!=null){
				Iterator<FeePayment> iterator = feePaymentList.iterator();
				while (iterator.hasNext()) {
					FeePayment feePayment = iterator.next();
					if(count==0 && feePayment.getStudent()!=null && feePayment.getStudent().getAdmAppln()!=null 
					&& feePayment.getStudent().getAdmAppln().getPersonalData()!=null){
						StringBuffer buffer = new StringBuffer();
						if(feePayment.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null){
							buffer.append(feePayment.getStudent().getAdmAppln().getPersonalData().getFirstName());
						}
						if(feePayment.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null){
							buffer.append(" " + feePayment.getStudent().getAdmAppln().getPersonalData().getMiddleName());
						}
						if(feePayment.getStudent().getAdmAppln().getPersonalData().getLastName()!=null){
							buffer.append(" " + feePayment.getStudent().getAdmAppln().getPersonalData().getLastName());
						}
						paymentForm.setStudentName(buffer.toString());
					}					
					paymentTO = new InstallmentPaymentTO();
					paymentTO.setFeePayment(feePayment);
					paymentTO.setFeePaymentId(feePayment.getId());
					//paymentTO.setInstallMentAmount(String.valueOf(feePayment.getTotalInstallmentAmount()));
					
					List<AccountHeadTO> accountList = null;
					List<FeePaidDateTO> feePaidDateTOList = new ArrayList<FeePaidDateTO>();					
					Map<String, FeePaidDateTO> feePaidDateMap = new HashMap<String, FeePaidDateTO>();
					if(feePayment.getFeePaymentDetails()!=null){
						List<FeePaymentDetail> paymentDetailList = new ArrayList<FeePaymentDetail>();
						paymentDetailList.addAll(feePayment.getFeePaymentDetails());
						//Sort based on accountID
						Collections.sort(paymentDetailList, new AccountComparator());
						Iterator<FeePaymentDetail> paymentDetailIterator = paymentDetailList.iterator();
						while (paymentDetailIterator.hasNext()) {
							FeePaymentDetail feePaymentDetail = paymentDetailIterator.next();
							if(feePaymentDetail.getPaidDate()!=null){								
								if(!feePaidDateMap.containsKey(String.valueOf(feePaymentDetail.getPaidDate()))){									
									feePaidDateTO = new FeePaidDateTO();
									accountList = new ArrayList<AccountHeadTO>(); 
									feePaidDateTO.setPaidDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feePaymentDetail.getPaidDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
									accountHeadTO = new AccountHeadTO();
									if(feePaymentDetail.getFeeAccount()!=null){	
										if(feePaymentDetail.getFeeAccount().getName()!=null){
											accountHeadTO.setAccName(feePaymentDetail.getFeeAccount().getName());
										}
										accountHeadTO.setAccountId(feePaymentDetail.getFeeAccount().getId());
									}
									if(feePaymentDetail.getAmountPaid()!=null){
										accountHeadTO.setAmount(String.valueOf(feePaymentDetail.getAmountPaid()));
									}
									accountList.add(accountHeadTO);
									feePaidDateTO.setAccountList(accountList);									
									feePaidDateMap.put(String.valueOf(feePaymentDetail.getPaidDate()), feePaidDateTO);
								}
								else{
									feePaidDateTO = feePaidDateMap.get(String.valueOf(feePaymentDetail.getPaidDate()));
									accountList = feePaidDateTO.getAccountList();
									accountHeadTO = new AccountHeadTO();						
									if(feePaymentDetail.getFeeAccount()!=null){	
										if(feePaymentDetail.getFeeAccount().getName()!=null){
											accountHeadTO.setAccName(feePaymentDetail.getFeeAccount().getName());
										}
										accountHeadTO.setAccountId(feePaymentDetail.getFeeAccount().getId());
										
									}
									if(feePaymentDetail.getAmountPaid()!=null){
										accountHeadTO.setAmount(String.valueOf(feePaymentDetail.getAmountPaid()));
									}
									accountList.add(accountHeadTO);
									feePaidDateTO.setAccountList(accountList);								
									feePaidDateMap.put(String.valueOf(feePaymentDetail.getPaidDate()), feePaidDateTO);
								}
							}
						}
						
						List<FeePaymentDetail> detailsList = new ArrayList<FeePaymentDetail>();
						detailsList.addAll(feePayment.getFeePaymentDetails());
						Collections.sort(detailsList, new FeePaymentDetailComparator());
						accountNameList.addAll(getAccountList(detailsList).values());
					}
					feePaidDateTOList.addAll(feePaidDateMap.values());
					paymentTO.setAccountList(accountNameList);
					paymentTO.setFeePaidDateTOList(feePaidDateTOList);
					paymentTOList.add(paymentTO);
				}
			}
			log.info("End of populateFeePaymentBOToTO of InstallmentPaymentHelper");
		return paymentTOList;
	}
	/**
	 * 
	 * @param detailsList
	 * @returns the Account Lists
	 * With Total, Balance amounts
	 * @throws Exception
	 */
	private Map<Integer, AccountHeadTO> getAccountList(
			List<FeePaymentDetail> detailsList) throws Exception{
		log.info("Inside of getAccountList of InstallmentPaymentHelper");
		Map<Integer, AccountHeadTO> accountMap = new HashMap<Integer, AccountHeadTO>();
		if(detailsList!=null){
			AccountHeadTO headTO = null;
			Iterator<FeePaymentDetail> iterator = detailsList.iterator();		
			while (iterator.hasNext()) {
				FeePaymentDetail feePaymentDetail =iterator.next();
				if(feePaymentDetail.getFeeAccount()!=null){
					if(!accountMap.containsKey(feePaymentDetail.getFeeAccount().getId())){
						headTO = new AccountHeadTO();
						headTO.setAccountId(feePaymentDetail.getFeeAccount().getId());
						headTO.setAccName(feePaymentDetail.getFeeAccount().getName());
						if(feePaymentDetail.getAmountBalance()!=null){
							headTO.setBalanceAmount(String.valueOf(feePaymentDetail.getAmountBalance()));
						}
						if(feePaymentDetail.getTotalAmount()!=null){
							headTO.setTotalAmount(String.valueOf(feePaymentDetail.getTotalAmount()));
						}
						if(feePaymentDetail.getAmountBalance()!=null){
							headTO.setBalanceAmount(String.valueOf(feePaymentDetail.getAmountBalance()));
						}
						/*if(feePaymentDetail.getInstallmentAmount()!=null){
							headTO.setInstallmentAmount(String.valueOf(feePaymentDetail.getInstallmentAmount()));
						}*/
						accountMap.put(feePaymentDetail.getFeeAccount().getId(), headTO);
					}
					else{
						headTO = accountMap.get(feePaymentDetail.getFeeAccount().getId());
						if(feePaymentDetail.getAmountBalance()!=null){
							headTO.setBalanceAmount(String.valueOf(feePaymentDetail.getAmountBalance()));
						}
						if(feePaymentDetail.getTotalAmount()!=null){
							headTO.setTotalAmount(String.valueOf(feePaymentDetail.getTotalAmount()));
						}
						if(feePaymentDetail.getAmountBalance()!=null){
							headTO.setBalanceAmount(String.valueOf(feePaymentDetail.getAmountBalance()));
						}
						/*if(feePaymentDetail.getInstallmentAmount()!=null){
							headTO.setInstallmentAmount(String.valueOf(feePaymentDetail.getInstallmentAmount()));
						}*/
					}
				}
			}			
		}
		log.info("End of getAccountList of InstallmentPaymentHelper");
		return accountMap;
	}

	/**
	 * 
	 * @param paymentForm
	 * @returns the updated List for installment
	 * @throws Exception
	 */
	public List<FeePayment> populateInstallMentPaymentBOS(
			InstallmentPaymentForm paymentForm)throws Exception {
		log.info("Inside of populateInstallMentPaymentBOS of InstallmentPaymentHelper");
		List<FeePayment>installmentPaymentBOList = new ArrayList<FeePayment>();
		FeePayment feePayment = null;
		FeePaymentDetail feePaymentDetail = null;
		FeeAccount feeAccount = null;
		FeePaymentMode feePaymentMode = null;	
		FeeFinancialYear financialYear = null;
		Iterator<InstallmentPaymentTO> iterator = paymentForm.getPaymentTOList().iterator();
		while (iterator.hasNext()) {
			InstallmentPaymentTO installmentPaymentTO = iterator.next();
			feePayment = installmentPaymentTO.getFeePayment();
			List<String> balanceAmountList = new ArrayList<String>();
			if(installmentPaymentTO.getAccountList()!=null){
				Iterator<AccountHeadTO> itr = installmentPaymentTO.getAccountList().iterator();
				while (itr.hasNext()) {
					double paidAmount = 0.0;
					double balanceAmount = 0.0;
					
					AccountHeadTO accountHeadTO = itr.next();
					feePaymentDetail = new FeePaymentDetail();
					feeAccount = new FeeAccount();
					feeAccount.setId(accountHeadTO.getAccountId());
					feePaymentDetail.setFeeAccount(feeAccount);
					if(accountHeadTO.getPaidAmount()!=null && !accountHeadTO.getPaidAmount().isEmpty()){
						paidAmount = Double.valueOf(accountHeadTO.getPaidAmount());
						feePaymentDetail.setAmountPaid(new BigDecimal(paidAmount));
					}
					else{
						feePaymentDetail.setAmountPaid(new BigDecimal(paidAmount));
					}
					if(accountHeadTO.getBalanceAmount()!=null){
						balanceAmount = Double.valueOf(accountHeadTO.getBalanceAmount());
					}
					if(balanceAmount != 0){
						feePaymentDetail.setTotalAmount(new BigDecimal(balanceAmount));
						feePaymentDetail.setAmountBalance(new BigDecimal(balanceAmount-paidAmount));
						//feePaymentDetail.setInstallmentAmount(new BigDecimal(balanceAmount-paidAmount));
					}
					else{
						feePaymentDetail.setTotalAmount(new BigDecimal(0.0));
						feePaymentDetail.setAmountBalance(new BigDecimal(0.0));
						//feePaymentDetail.setInstallmentAmount(new BigDecimal(0.0));
					}	
					if(balanceAmount-paidAmount==0){
						balanceAmountList.add(String.valueOf(balanceAmount-paidAmount));
					}
						
					feePaymentDetail.setPaidDate(CommonUtil.ConvertStringToSQLDate(paymentForm.getDate()));
					financialYear= new FeeFinancialYear();
					financialYear.setId(Integer.parseInt(installmentPaymentTO.getFinancialYear()));
					feePaymentDetail.setFeeFinancialYear(financialYear);
					feePaymentMode = new FeePaymentMode();
					feePaymentMode.setId(Integer.parseInt(installmentPaymentTO.getPayMentTypeId()));
					feePaymentDetail.setFeePaymentMode(feePaymentMode);
					feePaymentDetail.setReferenceNumber(installmentPaymentTO.getReferenceNo());
					//feePaymentDetail.setIsInstallmentPayment(true);
					feePayment.getFeePaymentDetails().add(feePaymentDetail);
				}
			}
			if(installmentPaymentTO.getAccountList()!=null && balanceAmountList!=null){
				if(installmentPaymentTO.getAccountList().size() == balanceAmountList.size()){
					feePayment.setIsCompletlyPaid(true);
				}
			}
			else {
				feePayment.setIsCompletlyPaid(false);
			}
			installmentPaymentBOList.add(feePayment);
		}
		log.info("End of populateInstallMentPaymentBOS of InstallmentPaymentHelper");
		return installmentPaymentBOList;
	}
}
