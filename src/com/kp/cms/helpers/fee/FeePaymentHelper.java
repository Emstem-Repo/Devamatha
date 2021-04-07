package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeAdditionalAccountAssignment;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentApplicantDetails;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.FeePaymentDetailAmount;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.fee.FeeAccountAssignmentTO;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeAmountTO;
import com.kp.cms.to.fee.FeeDisplayTO;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.to.fee.FeePaymentDetailEditTO;
import com.kp.cms.to.fee.FeePaymentDetailFeeGroupTO;
import com.kp.cms.to.fee.FeePaymentDisplayTO;
import com.kp.cms.to.fee.FeePaymentEditTO;
import com.kp.cms.to.fee.FeeStudentDetailTO;
import com.kp.cms.to.fee.FeeTO;
import com.kp.cms.to.fee.PrintChalanTO;
import com.kp.cms.transactions.fee.IFeeAdditionalTransaction;
import com.kp.cms.transactions.fee.IFeePaymentTransaction;
import com.kp.cms.transactions.fee.IFeeTransaction;
import com.kp.cms.transactionsimpl.fee.FeeAddtionalTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeePaymentTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeeTransactionTmpl;
import com.kp.cms.utilities.AccountGroupComparator;
import com.kp.cms.utilities.AccountNameComparator;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.FeeEditComparator;
import com.kp.cms.utilities.FeeHeadComparator;

/**
 * 
 * @date 20/jan/2009
 * Helper class for FeePayment related activities.
 * 
 */
public class FeePaymentHelper {
	private static final Log log = LogFactory.getLog(FeePaymentHelper.class);
	private static FeePaymentHelper feePaymentHelper= null;
	public static FeePaymentHelper getInstance() {
	      if(feePaymentHelper == null) {
	    	  feePaymentHelper = new FeePaymentHelper();
	    	  return feePaymentHelper;
	      }
	      return feePaymentHelper;
	}
	IFeePaymentTransaction transaction = FeePaymentTransactionImpl.getInstance();
	/**
	 * 
	 * @param courseSet
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getFeeGroupByCourse(Set<Integer> courseSet, String year) throws Exception {
			log.debug("Helper : Entering getFeeGroupByCourse");
		   IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		   Map<Integer,String> feeGroupMap =  feeAssignmentTransaction.getFeesGroupDetailsForCourse(courseSet, year);
		   log.debug("Helper : Leaving getFeeGroupByCourse");
		   return feeGroupMap;
	}
	
	/**
	 * 
	 * @param courseSet
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getFeeAdditionalFeeGroup() throws Exception {
		log.debug("Helper : Entering getFeeAdditionalFeeGroup");
		   IFeeAdditionalTransaction feeAdditionalTransaction = FeeAddtionalTransactionImpl.getInstance();
		   Map<Integer,String> feeOptionalGroupMap = feeAdditionalTransaction.getAllFeesGroup();
		   log.debug("Helper : Leaving getFeeAdditionalFeeGroup");
		   return feeOptionalGroupMap;
	}
	
	/**
	 *  returns the Fee List copied from BO's.
	 */
	public List<FeeTO> copyFeeBosToTos(List<Fee> feeBoList,int admittedThroughId) throws Exception {
		log.debug("Helper : Entering copyBotoTo");
		Iterator<Fee> itr = feeBoList.iterator();
		Fee fee;
		FeeTO feeTo;
		List<FeeTO> feeToList = new ArrayList<FeeTO>();

		FeeAccountAssignmentTO  FeeAccountAssignmentTo;
		FeeAccountAssignment feeAccountAssignment;
		FeeAccountTO feeAccountTo;
		FeeHeadingTO feeHeadingTO;
		AdmittedThroughTO admittedThroughTo;
		List<FeeAccountAssignmentTO> feeAssignmentAccountList = new ArrayList<FeeAccountAssignmentTO>();
		Iterator<FeeAccountAssignment> itr1;
		while(itr.hasNext()) {
			fee = itr.next();
			feeTo = new FeeTO();
			feeTo.setId(fee.getId());
			itr1 = fee.getFeeAccountAssignments().iterator();
				
			feeTo.setSemister(fee.getSemesterNo());
			feeAssignmentAccountList = new ArrayList<FeeAccountAssignmentTO>();
			while(itr1.hasNext()) {
				FeeAccountAssignmentTo = new FeeAccountAssignmentTO();
				feeAccountTo = new FeeAccountTO();
				admittedThroughTo = new AdmittedThroughTO();
				feeHeadingTO = new FeeHeadingTO();
				
				feeAccountAssignment = itr1.next();
				if(admittedThroughId == feeAccountAssignment.getAdmittedThrough().getId()) {
					FeeAccountAssignmentTo.setId(feeAccountAssignment.getId());
											
						// setting admitted through TO
						admittedThroughTo.setId(feeAccountAssignment.getAdmittedThrough().getId());
		                FeeAccountAssignmentTo.setAdmittedThroughTO(admittedThroughTo);
		                
		                // setting AccountTo 
		                feeAccountTo.setId(feeAccountAssignment.getFeeAccount().getId());
		                FeeAccountAssignmentTo.setFeeAccountTo(feeAccountTo);
		                
		                // setting Heading To
		                feeHeadingTO.setId(feeAccountAssignment.getFeeHeading().getId());
		                FeeAccountAssignmentTo.setFeeHeadingTo(feeHeadingTO);
		                
		                // setting amount.
		                FeeAccountAssignmentTo.setAmount(feeAccountAssignment.getAmount().doubleValue());
		                if(feeAccountAssignment.getCasteAmount()!=null)
		                	FeeAccountAssignmentTo.setCasteAmount(String.valueOf(feeAccountAssignment.getCasteAmount().doubleValue()));
		               if(feeAccountAssignment.getLigAmount()!=null)
		            	   FeeAccountAssignmentTo.setLigAmount(String.valueOf(feeAccountAssignment.getLigAmount().doubleValue()));
		               
		               if(feeAccountAssignment.getCurrencyByCurrencyId()!=null)
		            	   FeeAccountAssignmentTo.setCurrencyId(String.valueOf(feeAccountAssignment.getCurrencyByCurrencyId().getId()));
		               
		               if(feeAccountAssignment.getCurrencyByLigCurrencyId()!=null)
		            	   FeeAccountAssignmentTo.setLigCurrencyId(String.valueOf(feeAccountAssignment.getCurrencyByLigCurrencyId().getId()));
		              
		              if(feeAccountAssignment.getCurrencyByCasteCurrencyId()!=null)
		            	  FeeAccountAssignmentTo.setCasteCurrencyId(String.valueOf(feeAccountAssignment.getCurrencyByCasteCurrencyId().getId()));
		                //setting feeAccountAssignmentTo to list.
		                feeAssignmentAccountList.add(FeeAccountAssignmentTo);
				}
			}
			feeTo.setFeeAccountAssignments(new HashSet<FeeAccountAssignmentTO>(feeAssignmentAccountList));
			feeToList.add(feeTo);
		}
		log.debug("Helper : Leaving copyFeeBosToTos");
	return feeToList;	
	}

	
	/**
	 * 
	 * @param feePaymentForm
	 * @return feePAymentList copied from feePayment Form.
	 *         this will put ischallonprinted=true and challanprintdate  
	 * @throws Exception
	 */
	public FeePayment copyFeePaymentDataToBo(FeePaymentForm feePaymentForm) throws Exception{
		log.debug("Helper : Entering copyFeePaymentDataToBo");
		   FeePayment feePayment = new FeePayment();
		   List<FeePaymentDetailAmount> feePaymentDetailAmountList = new ArrayList<FeePaymentDetailAmount>();
		   FeePaymentDetailAmount feePaymentDetailAmount;
		  
		   
		   feePayment.setApplicationNo(feePaymentForm.getApplicationId());
		   feePayment.setRegistrationNo(feePaymentForm.getRegistrationNo());
		   feePayment.setRollNo(feePaymentForm.getRollNumber());
		   feePayment.setTotalAmount(new BigDecimal(feePaymentForm.getGrandTotal()));
		   feePayment.setTotalFeePaid(new BigDecimal(feePaymentForm.getNetPayable()));
		   feePayment.setIsChallenPrinted(true);
		   Calendar c=Calendar.getInstance();
		   c.setTime(CommonUtil.ConvertStringToSQLDate(feePaymentForm.getDateTime()));
		   feePayment.setChallenPrintedDate(c.getTime());
		   feePayment.setIsFeePaid(true);
		   FeeFinancialYear y = new FeeFinancialYear();
		   y.setId(Integer.parseInt(feePaymentForm.getFinancialYearId()));
		   feePayment.setAmountFinancialYear(y);
		   
		   if(feePayment.getTotalAmount().equals(feePayment.getTotalFeePaid())) {
			   Date paidDate = new Date();
				if(feePaymentForm.getDateTime()!= null){
					paidDate = CommonUtil.ConvertStringToSQLDate(feePaymentForm.getDateTime());
				}
			   feePayment.setIsCompletlyPaid(true);
			   feePayment.setIsFeePaid(true);
			   feePayment.setFeePaidDate(paidDate);
		   } else if(feePaymentForm.getTotalConcession()>0) { 
//			   Date paidDate = new Date();
//				if(feePaymentForm.getDateTime()!= null){
//					paidDate = CommonUtil.ConvertStringToSQLDate(feePaymentForm.getDateTime());
//				}
			   int con = (int) feePaymentForm.getTotalConcession();
			   int total = Integer.parseInt(feePayment.getTotalAmount().toString());
			   int paid = Integer.parseInt(feePayment.getTotalFeePaid().toString());
			   if(total==(con+paid)){
				   feePayment.setIsCompletlyPaid(true);
				   feePayment.setIsFeePaid(true);
			   }else{
				   feePayment.setIsCompletlyPaid(false);
				   feePayment.setIsFeePaid(false);
			   }
			  
		   } else{
			   feePayment.setIsCompletlyPaid(false);
			   feePayment.setIsFeePaid(false);
		   }
	  
		   //feePayment.setTotalConcessionAmount(new BigDecimal(feePaymentForm.getTotalConcession()));
		   feePayment.setTotalBalanceAmount(new BigDecimal(feePaymentForm.getTotalBalance()));
		   
		   Course course = new Course();
		   if(feePaymentForm.getCourseId() != null) {
			   course.setId(Integer.parseInt(feePaymentForm.getCourseId()));
			   feePayment.setCourse(course);
		   }	   
		   
		   //feePayment.setConcessionVoucherNo(feePaymentForm.getConcessionReferenceNo());
		   feePayment.setTotalConcessionAmount(new BigDecimal(feePaymentForm.getTotalConcession()));
		   feePayment.setTotalBalanceAmount(new BigDecimal(feePaymentForm.getTotalBalance()));
		   feePayment.setAcademicYear(Integer.parseInt(feePaymentForm.getAcademicYear()));
		   feePayment.setStudentName(feePaymentForm.getStudentName());
		   
		   if(feePaymentForm.getStudentId()!=null){
			   Student student = new Student();
			   student.setId(Integer.parseInt(feePaymentForm.getStudentId()));
			   feePayment.setStudent(student);
		   }
		   Classes classes = new Classes();
		  classes.setId(Integer.parseInt(feePaymentForm.getClassId()));
		  feePayment.setClasses(classes);
		   feePayment.setIsCancelChallan(false);
		   feePayment.setChallanCreatedTime(new Date());
		   feePayment.setConcessionVoucherNo(feePaymentForm.getConcessionReferenceNo());
		   if(feePaymentForm.getCurrencyId()!=null && !feePaymentForm.getCurrencyId().trim().isEmpty()){
			   Currency currency = new Currency();
			   currency.setId(Integer.parseInt(feePaymentForm.getCurrencyId()));
			   feePayment.setCurrency(currency);
		   }
		   
		   FeePaymentMode feePaymentMode = new FeePaymentMode();
		   if(feePaymentForm.getPaymentMode()!=null && !feePaymentForm.getPaymentMode().trim().isEmpty()){
			   feePaymentMode.setId(Integer.parseInt(feePaymentForm.getPaymentMode()));
		   }
		   feePayment.setFeePaymentMode(feePaymentMode);
		   if(feePaymentForm.getPaymentMode()!=null && !feePaymentForm.getPaymentMode().trim().isEmpty()){
			   feePaymentMode.setId(Integer.parseInt(feePaymentForm.getPaymentMode()));
			   feePayment.setFeePaymentMode(feePaymentMode);
		   }
		   feePayment.setChallanCreatedTime(new Date());
		   if(feePaymentForm.getManualClassName()!=null && !feePaymentForm.getManualClassName().isEmpty()){
			   feePayment.setManualClassName(feePaymentForm.getManualClassName());
		   }
		   feePayment.setPaymentVerified(false);
		   FeePaymentDetail feePaymentDetail;
		   FeeHeading feeHeading;
		   Set<FeePaymentDetail> feePayDetailSet = new HashSet<FeePaymentDetail>();
		   List<Integer> groupList = new ArrayList<Integer>();
		   Map<Integer, List<Integer>> feeSemMap = new HashMap<Integer, List<Integer>>();
		   FeeAccount feeAccount;		   
		   if(feePaymentForm.getFeePaymentAdditionalList()!= null && feePaymentForm.getFeePaymentAdditionalList().size() > 0){
			   Iterator<FeePaymentDisplayTO> feeAddlItr = feePaymentForm.getFeePaymentAdditionalList().iterator();
			   feeSemMap = new HashMap<Integer, List<Integer>>();
			   //groupList = new ArrayList<Integer>();
			   while (feeAddlItr.hasNext()) {
				   FeePaymentDisplayTO feePaymentDisplayTO = (FeePaymentDisplayTO) feeAddlItr.next();
					
					Iterator<FeeDisplayTO> dispItr =  feePaymentDisplayTO.getFeeDispTOList().iterator();
					groupList = new ArrayList<Integer>();
					while (dispItr.hasNext()) {
						feePaymentDetail = new FeePaymentDetail();
						feePaymentDetailAmount = new FeePaymentDetailAmount();
						FeeDisplayTO feeDisplayTO = (FeeDisplayTO) dispItr.next();
						feeHeading = new FeeHeading();
						feeAccount = new FeeAccount();
						if(feeDisplayTO.getFeeHeadId()!= null){
							feeHeading.setId(Integer.parseInt(feeDisplayTO.getFeeHeadId()));
							feePaymentDetail.setFeeHeading(feeHeading);
						}
						if(feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty()){
							feePaymentDetail.setTotalAmount(new BigDecimal(feeDisplayTO.getTotalAmount()));
						}
						if(feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty()){
							feePaymentDetail.setTotalAdditionalAmount(new BigDecimal(feeDisplayTO.getTotalAmount()));
						}
						if(feeDisplayTO.getPaidAmount()!= null && !feeDisplayTO.getPaidAmount().trim().isEmpty()){
							feePaymentDetail.setAmountPaid(new BigDecimal(feeDisplayTO.getPaidAmount()));
						}
						
						if(feeDisplayTO.getBalanceAmt()!= null && !feeDisplayTO.getBalanceAmt().trim().isEmpty()){
							feePaymentDetail.setAmountBalance(new BigDecimal(feeDisplayTO.getBalanceAmt()));
						}
						if(feeDisplayTO.getConcessionAmt()!= null && !feeDisplayTO.getConcessionAmt().trim().isEmpty() ){
							feePaymentDetail.setConcessionAmount(new BigDecimal(feeDisplayTO.getConcessionAmt()));
						}
						if(feeDisplayTO.getRemarks() != null && !feeDisplayTO.getRemarks().isEmpty()){
							feePayment.setRemarks(feeDisplayTO.getRemarks());
						}
						feeAccount.setId(feeDisplayTO.getAccId());
						feePaymentDetail.setFeeAccount(feeAccount);
						
						if(feePaymentDetail.getTotalAmount() == null || feePaymentDetail.getTotalAmount().doubleValue() <=0){
							continue;
						}
						
						FeeFinancialYear financialYear = new FeeFinancialYear();
						if(feePaymentForm.getIsCurrent().equalsIgnoreCase("yes")){
							financialYear.setId(Integer.parseInt(feePaymentForm.getCurFinId()));
							feePaymentDetail.setFeeFinancialYear(financialYear);
						}else{
							financialYear.setId(Integer.parseInt(feePaymentForm.getPreFinId()));
							feePaymentDetail.setFeeFinancialYear(financialYear);
						}
					    if(feePaymentForm.getPaymentMode()!=null && !feePaymentForm.getPaymentMode().trim().isEmpty()){
						    FeePaymentMode detlFeePaymentMode = new FeePaymentMode();
						   detlFeePaymentMode.setId(Integer.parseInt(feePaymentForm.getPaymentMode()));
						    feePaymentDetail.setFeePaymentMode(detlFeePaymentMode);  
					    }
					    /*if(feeDisplayTO.getCurrencyId() > 0 ){
						       Currency currency = new Currency();
							   currency.setId(feeDisplayTO.getCurrencyId());
							   feePaymentDetail.setCurrency(currency);
						    }*/
					    if(feePayment.getIsCompletlyPaid()!=null && feePayment.getIsCompletlyPaid()){
					    	feePaymentDetail.setPaidDate(new Date());	
					   }
					    feePayDetailSet.add(feePaymentDetail);
					    
					    feePaymentDetailAmount.setAmount(feePaymentDetail.getTotalAmount());
					    feePaymentDetailAmount.setFeeAccount(feeAccount);
					    feePaymentDetailAmount.setIsOptional(true);
					    feePaymentDetailAmount.setFeeHeading(feeHeading);
					    
					    if(feePaymentForm.getStudentId()!=null){
							   Student student = new Student();
							   student.setId(Integer.parseInt(feePaymentForm.getStudentId()));
							   feePaymentDetailAmount.setStudent(student);
						 }
					    
					    feePaymentDetailAmountList.add(feePaymentDetailAmount);
					    /*if(!groupList.contains(Integer.parseInt(feeDisplayTO.getGroupId()))){
					    	groupList.add(Integer.parseInt(feeDisplayTO.getGroupId()));
					    }*/
					}
					/*if(feePaymentDisplayTO.getSemester()!= null){
						feeSemMap.put(Integer.parseInt(feePaymentDisplayTO.getSemester()), groupList);
					}*/
					
			 
			   }
		   }
		   Iterator<FeePaymentDisplayTO> feePaylItr = feePaymentForm.getFeePaymentDisplayTOList().iterator();
		  
		   while (feePaylItr.hasNext()) {
			   FeePaymentDisplayTO feePaymentDisplayTO = (FeePaymentDisplayTO) feePaylItr.next();
				Iterator<FeeDisplayTO> dispItr =  feePaymentDisplayTO.getFeeDispTOList().iterator();
				groupList = new ArrayList<Integer>();
				while (dispItr.hasNext()) {
					FeeDisplayTO feeDisplayTO = (FeeDisplayTO) dispItr.next();
					feePaymentDetail = new FeePaymentDetail();
					feePaymentDetailAmount = new FeePaymentDetailAmount();
					feeHeading = new FeeHeading();
					feeAccount = new FeeAccount();
					if(feeDisplayTO.getFeeHeadId()!= null){
						feeHeading.setId(Integer.parseInt(feeDisplayTO.getFeeHeadId()));
						feePaymentDetail.setFeeHeading(feeHeading);
					}
					if(feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty()){
						feePaymentDetail.setTotalAmount(new BigDecimal(feeDisplayTO.getTotalAmount()));
					}
					if(feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty()){
						feePaymentDetail.setTotalNonAdditionalAmount(new BigDecimal(feeDisplayTO.getTotalAmount()));
					}
					
					if(feeDisplayTO.getPaidAmount()!= null && !feeDisplayTO.getPaidAmount().trim().isEmpty()){
						feePaymentDetail.setAmountPaid(new BigDecimal(feeDisplayTO.getPaidAmount()));
					}
					if(feeDisplayTO.getBalanceAmt()!= null && !feeDisplayTO.getBalanceAmt().trim().isEmpty()){
						feePaymentDetail.setAmountBalance(new BigDecimal(feeDisplayTO.getBalanceAmt()));
					}
					if(feeDisplayTO.getConcessionAmt()!= null && !feeDisplayTO.getConcessionAmt().trim().isEmpty() ){
						feePaymentDetail.setConcessionAmount(new BigDecimal(feeDisplayTO.getConcessionAmt()));
					}
					feeAccount.setId(feeDisplayTO.getAccId());
					feePaymentDetail.setFeeAccount(feeAccount);
					if(feeDisplayTO.getDiscountAmt()!= null && !feeDisplayTO.getDiscountAmt().isEmpty()){
						feePaymentDetail.setDiscountAmt(new BigDecimal(feeDisplayTO.getDiscountAmt()));
					}
					if(feePaymentDetail.getTotalAmount() == null || feePaymentDetail.getTotalAmount().doubleValue() <=0){
						continue;
					}
					FeeFinancialYear financialYear = new FeeFinancialYear();
					if(feePaymentForm.getIsCurrent().equalsIgnoreCase("yes")){
						financialYear.setId(Integer.parseInt(feePaymentForm.getCurFinId()));
						feePaymentDetail.setFeeFinancialYear(financialYear);
					}else{
						financialYear.setId(Integer.parseInt(feePaymentForm.getPreFinId()));
						feePaymentDetail.setFeeFinancialYear(financialYear);
					}
				    if(feePaymentForm.getPaymentMode()!=null && !feePaymentForm.getPaymentMode().trim().isEmpty()){
					    FeePaymentMode detlFeePaymentMode = new FeePaymentMode();
					   detlFeePaymentMode.setId(Integer.parseInt(feePaymentForm.getPaymentMode()));
					    feePaymentDetail.setFeePaymentMode(detlFeePaymentMode);  
				    }
				    if(feeDisplayTO.getCurrencyId() != null && !feeDisplayTO.getCurrencyId().trim().isEmpty()){
				       Currency currency = new Currency();
					   currency.setId(Integer.parseInt(feeDisplayTO.getCurrencyId()));
					   feePaymentDetail.setCurrency(currency);
					 }
				    if(feePayment.getIsCompletlyPaid()!=null && feePayment.getIsCompletlyPaid()){
				    	feePaymentDetail.setPaidDate(new Date());	
				   }
				    feePayDetailSet.add(feePaymentDetail);
				    if(!groupList.contains(Integer.parseInt(feeDisplayTO.getGroupId()))){
				    	groupList.add(Integer.parseInt(feeDisplayTO.getGroupId()));
				    }
				    if(feePaymentDisplayTO.getSemester()!= null){
				    	feePaymentDetail.setSemesterNo(Integer.parseInt(feePaymentDisplayTO.getSemester()));
				    }
				    if(feeDisplayTO.getRemarks() != null && !feeDisplayTO.getRemarks().isEmpty()){
						feePayment.setRemarks(feeDisplayTO.getRemarks());
					}
				    feePaymentDetailAmount.setAmount(feePaymentDetail.getTotalAmount());
				    feePaymentDetailAmount.setFeeAccount(feeAccount);
				    feePaymentDetailAmount.setIsOptional(false);
				    feePaymentDetailAmount.setFeeHeading(feeHeading);
				    feePaymentDetailAmount.setSemesterNo(feePaymentDetail.getSemesterNo());
				    if(feePaymentForm.getStudentId()!=null){
					   Student student = new Student();
					   student.setId(Integer.parseInt(feePaymentForm.getStudentId()));
					   feePaymentDetailAmount.setStudent(student);
					 }
				    feePaymentDetailAmountList.add(feePaymentDetailAmount);
				}
				if(feePaymentDisplayTO.getSemester()!= null){
					feeSemMap.put(Integer.parseInt(feePaymentDisplayTO.getSemester()), groupList);
				}
				
			 
			   
			   

		   }
		   
		   feePayment.setFeePaymentDetails(feePayDetailSet);
		   Set<FeePaymentApplicantDetails> applicantDetailSet = new HashSet<FeePaymentApplicantDetails>();
		   FeePaymentApplicantDetails feePaymentApplicantDetails;
		   FeeGroup feeGroup;
		   Iterator<Integer> appItr = feeSemMap.keySet().iterator();
		   groupList = new ArrayList<Integer>();
		   while (appItr.hasNext()) {
			   Integer sem = (Integer) appItr.next();
			   groupList = feeSemMap.get(sem);
			   Iterator<Integer> grItr = groupList.iterator();
			   while (grItr.hasNext()) {
				   Integer group = (Integer) grItr.next();
				   feePaymentApplicantDetails = new FeePaymentApplicantDetails();
				   feeGroup = new FeeGroup();
				   feeGroup.setId(group);
				   feePaymentApplicantDetails.setSemesterNo(sem);
				   feePaymentApplicantDetails.setFeeGroup(feeGroup);
				   applicantDetailSet.add(feePaymentApplicantDetails);
			   }
			
		   }
		   feePayment.setFeePaymentApplicantDetailses(applicantDetailSet);
		   feePaymentForm.setFeePaymentDetailAmountList(feePaymentDetailAmountList);
		   
		   
		   log.debug("Helper : Leaving copyFeePaymentDataToBo");  
		   
		 return feePayment;
	}
	
	

	/**
	 * 
	 * @param paymentsBoList
	 *        copies the feepayment BO's List to TO's list.
	 * @return
	 */
	public List<FeePaymentDetail> copyFeePaymentsAccountWiseAmount(FeePaymentForm feePaymentForm) {
		log.debug("Helper : Entering copyFeePaymentsAccountWiseAmount");
		List<FeePaymentDetail> feePaymentDetailList = new ArrayList<FeePaymentDetail>();
		
	    List<FeeAmountTO> feeAccountWiseAmountList = feePaymentForm.getFeeAccountWiseAmountList();
		List<FeeAmountTO> feeAccountWiseConcessionList  = feePaymentForm.getFeeAccountWiseConcessionList();
		List<FeeAmountTO> feeAccountWiseInstallmentAmountBalanceList= feePaymentForm.getFeeAccountWiseInstallmentAmountPaidList();
		List<FeeAmountTO> feeAccountWiseExcessShortList = feePaymentForm.getFeeAccountWiseExcessShortList();
		List<FeeAmountTO> feeAccountWiseScholarshipAmountList= feePaymentForm.getFeeAccountWiseScholarshipAmountPaidList();
		
		Map<Integer,Double> feeAccountWiseAmountMap = new HashMap<Integer,Double>();
		Map<Integer,Double> feeAccountWiseExemptionAmountMap = feePaymentForm.getFullAccountWiseExemptionTotal();
		Map<Integer,Double> feeAccountWiseNonAdditionalMap = feePaymentForm.getAccountWiseNonOptionalAmount();
		Map<Integer,Double> feeAccountWiseAdditionalMap = feePaymentForm.getAccountWiseOptionalAmount();
		Map<Integer,Double> feeAccountWiseConcessionMap = new HashMap<Integer,Double>();
		Map<Integer,Double> feeAccountWiseInstallmentAmountBalanceMap = new HashMap<Integer,Double>();
		Map<Integer,Double> feeAccountWiseScholarshipMap = new HashMap<Integer,Double>();
		Map<Integer,Double> feeAccoutnWiseExcessShortMap = new HashMap<Integer,Double>();
		
		Iterator<FeeAmountTO> itr = feeAccountWiseAmountList.iterator();
		FeeAmountTO feeAmountTO ;
		boolean isInstalmentDone = false;
		
		while(itr.hasNext()){
			feeAmountTO = itr.next();
			feeAccountWiseAmountMap.put(Integer.valueOf(feeAmountTO.getFeeAccountId()),feeAmountTO.getAmount());
		}
			
		itr = feeAccountWiseConcessionList.iterator();
		while(itr.hasNext()){
			feeAmountTO = itr.next();
			feeAccountWiseConcessionMap.put(Integer.valueOf(feeAmountTO.getFeeAccountId()),feeAmountTO.getAmount());
		}

		itr = feeAccountWiseScholarshipAmountList.iterator();
		while(itr.hasNext()){
			feeAmountTO = itr.next();
			feeAccountWiseScholarshipMap.put(Integer.valueOf(feeAmountTO.getFeeAccountId()),feeAmountTO.getAmount());
		}
		
		itr = feeAccountWiseInstallmentAmountBalanceList.iterator();
		while(itr.hasNext()){
			feeAmountTO = itr.next();
			if(feeAmountTO.getAmount() != 0) {
				isInstalmentDone = true;
			}
			feeAccountWiseInstallmentAmountBalanceMap.put(Integer.valueOf(feeAmountTO.getFeeAccountId()),feeAmountTO.getAmount());
		}
		
		itr = feeAccountWiseExcessShortList.iterator();
		while(itr.hasNext()){
			feeAmountTO = itr.next();
			feeAccoutnWiseExcessShortMap.put(Integer.valueOf(feeAmountTO.getFeeAccountId()),feeAmountTO.getAmount());
		}
	
		Set<Integer> feeAccountSet = feePaymentForm.getAllFeeAccountMap().keySet();
		Iterator<Integer> itr1 = feeAccountSet.iterator();
		FeeAccount feeAccount;
		FeePaymentDetail feePaymentDetail;
		Double totalAmount;
		Double totalExemptionAmount = 0.0;
		Double concessionAmount;	 
		Double amountBalance; 
		Double installmentAmount;
		Double excessShortAmount; 
		Double amountPaid;
		Double totalNonAdditionalAmount;
		Double totalAdditionalAmount;
		Double scholarshipAmount;
		
	    Integer feeAccountId;
		while(itr1.hasNext()) {
			  feeAccountId = itr1.next();
			  
			  feeAccount = new FeeAccount();
			  feeAccount.setId(feeAccountId);
			  
			 
			  
			  feePaymentDetail = new FeePaymentDetail();
			  totalAmount = feeAccountWiseAmountMap.get(feeAccountId);
			  if(feeAccountWiseExemptionAmountMap.get(feeAccountId)!=null){
				  totalExemptionAmount = feeAccountWiseExemptionAmountMap.get(feeAccountId);
			  }
			  totalNonAdditionalAmount = feeAccountWiseNonAdditionalMap.get(feeAccountId);
			  totalAdditionalAmount =  feeAccountWiseAdditionalMap.get(feeAccountId);
				  
			  concessionAmount = feeAccountWiseConcessionMap.get(feeAccountId);
			  installmentAmount =  feeAccountWiseInstallmentAmountBalanceMap.get(feeAccountId);
			  excessShortAmount = feeAccoutnWiseExcessShortMap.get(feeAccountId);
			  scholarshipAmount = feeAccountWiseScholarshipMap.get(feeAccountId);
			  
			  if(isInstalmentDone) {
				  amountPaid = totalAmount - (concessionAmount + installmentAmount + scholarshipAmount);
				  amountBalance = installmentAmount;
			  } else { 
				  amountPaid = totalAmount;
				  amountBalance = 0.0;
			  }
			  
			  FeePaymentMode feePaymentMode = new FeePaymentMode();
			   if(feePaymentForm.getPaymentMode()!=null && !feePaymentForm.getPaymentMode().trim().isEmpty()){
				   feePaymentMode.setId(Integer.parseInt(feePaymentForm.getPaymentMode()));
			   }
			  
			  if(feePaymentForm.getCurrencyId()!=null && !feePaymentForm.getCurrencyId().trim().isEmpty()){
			     Currency currency = new Currency();
				 currency.setId(Integer.parseInt(feePaymentForm.getCurrencyId()));
				 feePaymentDetail.setCurrency(currency);
			  }
			  
			  FeeFinancialYear financialYear = new FeeFinancialYear();
			  if(feePaymentForm.getFinancialYearId()!=null && !feePaymentForm.getFinancialYearId().trim().isEmpty()){
				  financialYear.setId(Integer.parseInt(feePaymentForm.getFinancialYearId()));
				  feePaymentDetail.setFeeFinancialYear(financialYear);
			  }
			 
			  feePaymentDetail.setFeePaymentMode(feePaymentMode);
			  feePaymentDetail.setFeeAccount(feeAccount);
			  feePaymentDetail.setTotalAmount(new BigDecimal(totalAmount));
			  feePaymentDetail.setConcessionAmount(new BigDecimal(concessionAmount));
			  feePaymentDetail.setAmountPaid(new BigDecimal(amountPaid));
			  feePaymentDetail.setAmountBalance(new BigDecimal(amountBalance));
			  feePaymentDetail.setDiscountAmt(new BigDecimal(totalExemptionAmount));
			  if(totalNonAdditionalAmount != null) {
				  feePaymentDetail.setTotalNonAdditionalAmount(new BigDecimal(totalNonAdditionalAmount));
			  }
			  if(totalAdditionalAmount != null) {
				  feePaymentDetail.setTotalAdditionalAmount(new BigDecimal(totalAdditionalAmount));
			  }
			  
			  feePaymentDetailList.add(feePaymentDetail);
		}
		log.debug("Helper : Leaving copyFeePaymentsAccountWiseAmount");
		return feePaymentDetailList;
	}
	
	public List<FeePaymentApplicantDetails> copyFeePaymentsApplicantDetails(FeePaymentForm feePaymentForm) {
		log.debug("Helper : Entering copyFeePaymentsApplicantDetails");
		List<FeePaymentApplicantDetails> feePaymentApplicantList = new ArrayList<FeePaymentApplicantDetails>();
		
		List<FeePaymentDisplayTO> feeList = feePaymentForm.getFeePaymentDisplayList();
		Iterator<FeePaymentDisplayTO> itr = feeList.iterator();

		String[] selectedSems = new String[feeList.size()];
		int count = 0;
		while(itr.hasNext()) {
			selectedSems[count++] = itr.next().getSemester();
		}
		
		String[] selFeeGroups = feePaymentForm.getSelectedfeeGroup();
		
		FeePaymentApplicantDetails feePaymentApplicantDetail;
		FeeGroup feeGroup;
		for(int i=0;i<selectedSems.length;i++) {
			if(!selectedSems[i].equals("0")) {
				for(int j=0;j<selFeeGroups.length;j++) {
					feePaymentApplicantDetail = new FeePaymentApplicantDetails();
					feeGroup = new FeeGroup();
					
					feePaymentApplicantDetail.setSemesterNo(Integer.valueOf(selectedSems[i]));
	 				feeGroup.setId(Integer.parseInt(selFeeGroups[j]));
					feePaymentApplicantDetail.setFeeGroup(feeGroup);
					feePaymentApplicantList.add(feePaymentApplicantDetail);
				}
			}
		}
		log.debug("Helper : Leaving copyFeePaymentsApplicantDetails");
		return feePaymentApplicantList;
	}
	
	public List<Fee> copyFeeAdditionalToFee(List<FeeAdditional> feeAdditionalList,int admittedThroughId) {
		log.debug("Helper : Entering copyFeeAdditionalToFee");
		List<Fee> copiedFeeList = new ArrayList<Fee>();
		Iterator<FeeAdditional> itr = feeAdditionalList.iterator();
		Fee fee;
		FeeAccountAssignment feeAccountAssignment;
		FeeAdditional feeAdditional;
		FeeAdditionalAccountAssignment feeAdditionalAccountAssignment;
		AdmittedThrough admittedThrough;
		while(itr.hasNext()) {
			feeAdditional = itr.next();
			fee = new Fee();

			fee.setSemesterNo(0);
			fee.setFeeGroup(feeAdditional.getFeeGroup());
			
			Iterator<FeeAdditionalAccountAssignment> itr2 = feeAdditional.getFeeAdditionalAccountAssignments().iterator();
			Set<FeeAccountAssignment> feeAccountAssignmentSet = new HashSet<FeeAccountAssignment>();
			while(itr2.hasNext()) {
				feeAdditionalAccountAssignment = itr2.next();
				feeAccountAssignment = new FeeAccountAssignment();
				
				admittedThrough = new AdmittedThrough();
				
				admittedThrough.setId(admittedThroughId);
				feeAccountAssignment.setAdmittedThrough(admittedThrough);
				
				feeAccountAssignment.setFeeAccount(feeAdditionalAccountAssignment.getFeeAccount());
				feeAccountAssignment.setFeeHeading(feeAdditionalAccountAssignment.getFeeHeading());
				feeAccountAssignment.setAmount(feeAdditionalAccountAssignment.getAmount());

				feeAccountAssignmentSet.add(feeAccountAssignment);
			}
			
			fee.setFeeAccountAssignments(feeAccountAssignmentSet);
			copiedFeeList.add(fee);
		}
		log.debug("Helper : Leaving copyFeeAdditionalToFee");
		return copiedFeeList;
	}
	
	
	public void copyFeeChallanPrintData(FeePayment feePayment,FeePaymentForm feePaymentForm, HttpServletRequest request) throws Exception {
		log.debug("Helper : Entering copyFeeChallanPrintData");
		if(feePayment.getStudent().getAdmAppln() != null){
			feePaymentForm.setApplicationId(String.valueOf(feePayment.getStudent().getAdmAppln().getApplnNo()));
		}
		
		feePaymentForm.setBillNo(feePayment.getBillNo());
		feePaymentForm.setChallanPrintDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
	//	feePaymentForm.setStudentName(feePayment.getStudentName());
		if(feePayment.getStudent()!=null){
			Student student = feePayment.getStudent();
			StringBuffer studentName = new StringBuffer(); 
			if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
				studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
				studentName.append(" ");
			} 
			if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
				studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
				studentName.append(" ");
			}
			if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
				studentName.append(student.getAdmAppln().getPersonalData().getLastName());
				studentName.append(" ");
			}
			feePaymentForm.setStudentName(studentName.toString());
		}
		feePaymentForm.setConcessionReferenceNo(feePayment.getConcessionVoucherNo());
		if(feePayment.getCurrency()!= null && feePayment.getCurrency().getCurrencyCode()!= null){
			feePaymentForm.setCurrencyCode(feePayment.getCurrency().getCurrencyCode());
		}
		if(feePayment.getManualClassName()== null || feePayment.getManualClassName().isEmpty()){
		if(feePayment.getStudent()!= null && feePayment.getStudent().getAdmAppln()!= null && feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId()!= null &&
				feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode()!= null){
			feePaymentForm.setClassName(feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode());
		}
		}else{
			feePaymentForm.setClassName(feePayment.getManualClassName());
		}
		
		if(feePayment.getChallanCreatedTime()!= null){
			feePaymentForm.setChalanCreatedTime(feePayment.getChallanCreatedTime().toString());
		}
		if(feePayment.getStudent()!= null && feePayment.getStudent().getAdmAppln()!= null && feePayment.getStudent().getAdmAppln().getCourse()!= null && feePayment.getStudent().getAdmAppln().getCourse().getProgram() != null && feePayment.getStudent().getAdmAppln().getCourse().getProgram().getProgramType() != null )
		{
			feePaymentForm.setProgramType(feePayment.getStudent().getAdmAppln().getCourse().getProgram().getProgramType().getName());
		}
		List<FeePaymentDetailFeeGroupTO> additionalList = new ArrayList<FeePaymentDetailFeeGroupTO>();
		
		HttpSession session = request.getSession(false);
		if(feePayment.getFeePaymentMode()!= null && feePayment.getFeePaymentMode().getName()!= null){
			feePaymentForm.setPaymentMode(feePayment.getFeePaymentMode().getName());
		}
		Set<Integer> sems = new HashSet<Integer>();
		String schemeNo = "";
		StringBuffer schemeBuf = new StringBuffer();
		
		int length = schemeBuf.length();
		if(length>0)
			schemeNo = schemeBuf.substring(0, length-1);
		feePaymentForm.setSemesterData(schemeNo);
		
		Set<FeePaymentDetail> feePaymentList = feePayment.getFeePaymentDetails();
		Iterator<FeePaymentDetail> itr = feePaymentList.iterator();
		Map<Integer,Double> accountWiseNonOptionalAmount = new HashMap<Integer,Double>();
		Map<Integer,Double> accountWiseOptionalAmount = new HashMap<Integer,Double>();
		Map<Integer,Double> accountWiseTotalAmount = new HashMap<Integer,Double>();
		Map<Integer,Double> accountWiseConcessionAmount = new HashMap<Integer,Double>();
		Map<Integer,String> allFeeAccountMap = new HashMap<Integer,String>();
		List<PrintChalanTO> printChalanList = new ArrayList<PrintChalanTO>();
		PrintChalanTO printChalanTO;
		List<String> descList = new ArrayList<String>();
		List<String> bankInfoList = new ArrayList<String>();
		int totalAmt = 0;
		FeePaymentDetail feePaymentDetail;
		int count = 1;
		StringBuffer accwiseTotalString = new StringBuffer();
		Double grandTotal = 0.00;
		while(itr.hasNext()) {
			totalAmt = 0;
			feePaymentDetail = itr.next();
			
			allFeeAccountMap.put(feePaymentDetail.getFeeAccount().getId(), (feePaymentDetail.getFeeAccount().getName()+"("+feePaymentDetail.getFeeAccount().getCode()+")"));
			if(feePaymentDetail.getTotalNonAdditionalAmount() != null) {
				accountWiseNonOptionalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalNonAdditionalAmount().doubleValue());
			}	
			if(feePaymentDetail.getTotalAdditionalAmount() != null) {
				accountWiseOptionalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalAdditionalAmount().doubleValue());
			}	
			accountWiseTotalAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getTotalAmount().doubleValue());
			accountWiseConcessionAmount.put(feePaymentDetail.getFeeAccount().getId(), feePaymentDetail.getConcessionAmount().doubleValue());

			printChalanTO = new PrintChalanTO();
			printChalanTO.setPrintAccountName(feePaymentDetail.getFeeAccount().getPrintAccountName());
			printChalanTO.setAccId(feePaymentDetail.getFeeAccount().getId());
			if(feePaymentDetail.getTotalNonAdditionalAmount() != null) {
				printChalanTO.setNonAdditionalAmount(Integer.toString(feePaymentDetail.getTotalNonAdditionalAmount().intValue()));
				totalAmt = feePaymentDetail.getTotalNonAdditionalAmount().intValue(); 
			}
			if(feePaymentDetail.getTotalAdditionalAmount() != null) {
				printChalanTO.setAdditionalAMount(Integer.toString(feePaymentDetail.getTotalAdditionalAmount().intValue()));
				totalAmt = totalAmt + feePaymentDetail.getTotalAdditionalAmount().intValue(); 
			}
			if(feePaymentDetail.getTotalAmount()!= null) {
				printChalanTO.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
			}
			if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getBankInformation()!= null){
				printChalanTO.setBankInfo(feePaymentDetail.getFeeAccount().getBankInformation());
			}
			Collections.sort(additionalList, new AccountGroupComparator());
			printChalanTO.setAdditionalList(additionalList);
			
			String verified = "";
			if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getVerifiedBy()!= null){
				printChalanTO.setVerified(feePaymentDetail.getFeeAccount().getVerifiedBy());
				verified = feePaymentDetail.getFeeAccount().getVerifiedBy();
				StringTokenizer token = new StringTokenizer(verified, " "); 
				List<String> verifiedList = new ArrayList<String>();
				while(token.hasMoreTokens()) {
					String val = token.nextToken(); 
					verifiedList.add(val);
				}
				printChalanTO.setVerifiedList(verifiedList);
			}
			
			
			int deducationAmt = 0; 

			if(feePaymentDetail.getConcessionAmount()!= null && feePaymentDetail.getConcessionAmount().doubleValue() > 0){
				printChalanTO.setConcessionAmt(Integer.toString(feePaymentDetail.getConcessionAmount().intValue()));
				deducationAmt = feePaymentDetail.getConcessionAmount().intValue(); 
				printChalanTO.setIsConcession(true);
			}
			
			
			int netAmt = totalAmt - deducationAmt;
			printChalanTO.setNetAmount(Integer.toString(netAmt));
			String amtInWord = CommonUtil.numberToWord(netAmt) + " ONLY ";
			if(feePaymentDetail.getFeePayment().getCurrency()!= null && feePaymentDetail.getFeePayment().getCurrency().getCurrencyCode()!= null){
				amtInWord = amtInWord + "(IN " + feePaymentDetail.getFeePayment().getCurrency().getCurrencyCode() + ")";	
			}
			printChalanTO.setAmountInWord(amtInWord);
			printChalanTO.setLogoBytes(feePaymentDetail.getFeeAccount().getLogo());
			printChalanTO.setCount(count);
			if(netAmt!= 0){
				if(accwiseTotalString!= null && !accwiseTotalString.toString().trim().isEmpty()){
					accwiseTotalString.append(" + ");
				}
				accwiseTotalString.append(Integer.toString(netAmt));
			}
			grandTotal = grandTotal + netAmt;
			descList = new ArrayList<String>();
			bankInfoList = new ArrayList<String>();
		    char slashN='\n';
		    char slashR='\r';
			if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getDescription1()!= null){
				printChalanTO.setFeeDesc(feePaymentDetail.getFeeAccount().getDescription1());
				String desc = String.valueOf(feePaymentDetail.getFeeAccount().getDescription1());
				char[] descCharArray = desc.toCharArray();
			    	
			    StringBuffer descString = new StringBuffer();  
			    if(descCharArray!= null){
			    	for(int i = 0; i< descCharArray.length;i++){
			    		char descChar = descCharArray[i];
			    		if(descChar == slashR){
			    			descString.append("$");
			    		}
			    		else if(descChar != slashN){
			    			descString.append(descCharArray[i]);
			    		}
			    	}
			    }
			    if(descString!= null){
				 	StringTokenizer st = new StringTokenizer(descString.toString(), "$"); 
					
					while(st.hasMoreTokens()) {
						String val = st.nextToken(); 
						descList.add(val);
					}
			    }
			    printChalanTO.setDescList(descList);
			}
			descList = new ArrayList<String>();
			if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getDescription2()!= null){
				printChalanTO.setFeeDesc(feePaymentDetail.getFeeAccount().getDescription2());
				String desc = String.valueOf(feePaymentDetail.getFeeAccount().getDescription2());
				char[] descCharArray = desc.toCharArray();
			    	
			    StringBuffer descString = new StringBuffer();  
			    if(descCharArray!= null){
			    	for(int i = 0; i< descCharArray.length;i++){
			    		char descChar = descCharArray[i];
			    		if(descChar == slashR){
			    			descString.append("$");
			    		}
			    		else if(descChar != slashN){
			    			descString.append(descCharArray[i]);
			    		}
			    	}
			    }
			    if(descString!= null){
				 	StringTokenizer st = new StringTokenizer(descString.toString(), "$"); 
					
					while(st.hasMoreTokens()) {
						String val = st.nextToken(); 
						descList.add(val);
					}
			    }
			    printChalanTO.setDesc2List(descList);
			}			
			
			if(feePaymentDetail.getFeeAccount()!= null && feePaymentDetail.getFeeAccount().getBankInformation()!= null){
				String bankInfo = String.valueOf(feePaymentDetail.getFeeAccount().getBankInformation());
				char[] bankCharArray = bankInfo.toCharArray();
			    	
			    StringBuffer bankInfoString = new StringBuffer();  
			    if(bankCharArray!= null){
			    	for(int i = 0; i< bankCharArray.length;i++){
			    		char bankCharChar = bankCharArray[i];
			    		if(bankCharChar == slashR){
			    			bankInfoString.append("$");
			    		}
			    		else if(bankCharChar != slashN){
			    			bankInfoString.append(bankCharArray[i]);
			    		}
			    	}
			    }
			    if(bankInfoString!= null){
				 	StringTokenizer st = new StringTokenizer(bankInfoString.toString(), "$"); 
					
					while(st.hasMoreTokens()) {
						String bankVal = st.nextToken(); 
						bankInfoList.add(bankVal);
					}
			    }
			    printChalanTO.setBankInfoList(bankInfoList);
				
			}
			session.setAttribute("image_" + count, printChalanTO);
			count++;
			
		    printChalanList.add(printChalanTO);
		}
		feePaymentForm.setAccwiseTotalPrintString(accwiseTotalString.toString() + " = " + Integer.toString(grandTotal.intValue()));
		feePaymentForm.setAccountWiseNonOptionalAmount(accountWiseNonOptionalAmount);
		feePaymentForm.setAccountWiseOptionalAmount(accountWiseOptionalAmount);
		feePaymentForm.setAllFeeAccountMap(allFeeAccountMap);
		feePaymentForm.setFullAccountWiseTotal(accountWiseTotalAmount);
		feePaymentForm.setPrintChalanList(printChalanList);
		if(printChalanList!= null ){
			if(printChalanList.size() == 1){
				feePaymentForm.setIsSinglePrint(true);
			}
			else
			{
				feePaymentForm.setIsSinglePrint(false);
			}
				
		}
		if(printChalanList == null || printChalanList.size() <= 0){
			throw new DataNotFoundException();
		}
		Collections.sort(printChalanList, new AccountNameComparator());
		if(printChalanList!=null){
			feePaymentForm.setLastNo(printChalanList.size()-1);
		} 
		log.debug("Helper : Leaving copyFeeChallanPrintData");
 	
	}
	
	/**
	 * 
	 * @param feePayment
	 * @return
	 * @throws Exception
	 */
	public FeePaymentEditTO convertFeePaymentForEditBOToTO(FeePayment feePayment) throws Exception {
		FeePaymentEditTO feePaymentEdit = null;
		List<FeePaymentDetailEditTO> feePaymentDetailsForEditList = null;
		Map<Integer,String> allFeeAccountMap = null;
		if(feePayment !=null){
			feePaymentDetailsForEditList = new ArrayList<FeePaymentDetailEditTO>();
			allFeeAccountMap = new HashMap<Integer,String>();
			
			feePaymentEdit = new FeePaymentEditTO();
			
			feePaymentEdit.setFeePaymentId(feePayment.getId());
			feePaymentEdit.setFeePaid(feePayment.getIsFeePaid());
			if(feePayment.getChallenPrintedDate() !=null){
				feePaymentEdit.setChalanDate(CommonUtil.formatDate(feePayment.getChallenPrintedDate(), "dd/MM/yyyy"));
			}

			if(feePayment.getStudent().getAdmAppln()!=null){
				feePaymentEdit.setApplnNo(String.valueOf(feePayment.getStudent().getAdmAppln().getApplnNo()));
			}
			if(feePayment.getCourse()!=null && feePayment.getCourse().getName()!=null){
				feePaymentEdit.setCourseName(feePayment.getCourse().getName());
			}
			if(feePayment.getStudent()!=null && feePayment.getStudent().getClassSchemewise()!=null && feePayment.getStudent().getClassSchemewise().getClasses()!=null && feePayment.getStudent().getClassSchemewise().getClasses().getName()!=null){
				feePaymentEdit.setClassName(feePayment.getStudent().getClassSchemewise().getClasses().getName());
			}
			if(feePayment.getFeePaidDate() !=null){
				feePaymentEdit.setDateTime(CommonUtil.formatDate(feePayment.getFeePaidDate(), "dd/MM/yyyy"));
			}
			if(feePayment.getStudent()!=null){
				Student student = feePayment.getStudent();
				StringBuffer studentName = new StringBuffer(); 
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
					studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
					studentName.append(" ");
				} 
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
					studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
					studentName.append(" ");
				}
				if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
					studentName.append(student.getAdmAppln().getPersonalData().getLastName());
					studentName.append(" ");
				}
				feePaymentEdit.setStudentName(studentName.toString());
			}
			if(feePayment.getStudent()!=null && feePayment.getStudent().getAdmAppln()!=null && feePayment.getStudent().getAdmAppln().getAdmittedThrough()!=null){
				feePaymentEdit.setAdmittedThrough(feePayment.getStudent().getAdmAppln().getAdmittedThrough().getName());
			}
			if(feePayment.getBillNo()!=null && !feePayment.getBillNo().isEmpty()){
				feePaymentEdit.setFeeBillNo(feePayment.getBillNo());
			}
			if(feePayment.getFeePaymentMode()!=null){
				feePaymentEdit.setFeePaymentModeId(feePayment.getFeePaymentMode().getId());
			}
			if(feePayment.getTotalAmount()!=null){
				feePaymentEdit.setTotalAmount(feePayment.getTotalAmount().toString());
			}
			if(feePayment.getTotalConcessionAmount()!=null){
				feePaymentEdit.setTotalConcessionAmount(feePayment.getTotalConcessionAmount().toString());
			}
			if(feePayment.getConcessionVoucherNo()!=null){
				feePaymentEdit.setConcessionVoucherNo(feePayment.getConcessionVoucherNo());
			}
			if(feePayment.getFeePaymentDetails()!=null){
				Iterator<FeePaymentDetail> feePaymentDetailItr =  feePayment.getFeePaymentDetails().iterator();
				
				while (feePaymentDetailItr.hasNext()) {
					FeePaymentDetail feePaymentDetail = (FeePaymentDetail) feePaymentDetailItr
							.next();
					
					FeePaymentDetailEditTO feePaymentDetailEdit = new FeePaymentDetailEditTO();
					if(feePaymentDetail.getCurrency()!=null && feePaymentDetail.getCurrency().getCurrencyCode()!=null){
						feePaymentEdit.setCurrencyCode(feePaymentDetail.getCurrency().getCurrencyCode());
					}
					if(feePaymentDetail.getFeeAccount()!=null){
						allFeeAccountMap.put(Integer.valueOf(feePaymentDetail.getFeeAccount().getId()), (feePaymentDetail.getFeeAccount().getName()+"("+feePaymentDetail.getFeeAccount().getCode()+")" +" | Exemption"));
						if(feePaymentDetail.getFeeAccount().getFeeDivision()!=null && feePaymentDetail.getFeeAccount().getFeeDivision().getName()!=null){
							feePaymentEdit.setFeeDivisionName(feePaymentDetail.getFeeAccount().getFeeDivision().getName());
						}
						feePaymentDetailEdit.setAccountName((feePaymentDetail.getFeeAccount().getName()+"("+feePaymentDetail.getFeeAccount().getCode()+")" +" | Exemption"));
					}
					
					if(feePaymentDetail.getFeeAccount()!=null){
						feePaymentDetailEdit.setFeePaymentDetailId(feePaymentDetail.getId());
					}
					if(feePaymentDetail.getTotalAmount() !=null){
						feePaymentDetailEdit.setTotalAmount(feePaymentDetail.getTotalAmount().toString());
					}
					if(feePaymentDetail.getConcessionAmount() !=null){
						feePaymentDetailEdit.setConcessionAmount(feePaymentDetail.getConcessionAmount().toString());
					}
					if(feePaymentDetail.getDiscountAmt() !=null){
						feePaymentDetailEdit.setDiscountAmount(feePaymentDetail.getDiscountAmt().toString());
					}
					feePaymentDetailsForEditList.add(feePaymentDetailEdit);
				}
			}
			feePaymentEdit.setAllFeeAccountMap(allFeeAccountMap);
			feePaymentEdit.setFeePaymentDetailEditList(feePaymentDetailsForEditList);
			Collections.sort(feePaymentDetailsForEditList, new FeeEditComparator());
		}
		return feePaymentEdit;
	}
	
	public String getQueryForNoOfChallanForCourse(FeePaymentForm feePaymentForm) throws Exception {
		String query ="select count(*) " +
				" from FeePaymentApplicantDetails f " +
				"where f.feePayment.isCancelChallan=0 " ;
			if(feePaymentForm.getSelectedSems()!=null)	{
				  String semsArray[] = feePaymentForm.getSelectedSems();
				  String intType ="";
				   for(int i =0 ;i<semsArray.length; i++) {
						 intType = intType+semsArray[i];
						 if(i<(semsArray.length-1)){
							 intType = intType+",";
						 }
				   }
					
				   query=query+" and f.semesterNo in ("+intType+")";
			}
			if(feePaymentForm.getCourseId()!=null){
				query=query+" and f.feePayment.course.id = "+feePaymentForm.getCourseId();
			}
			if(feePaymentForm.getYear()!=null){
				query=query+" and f.feePayment.academicYear = "+feePaymentForm.getYear();
			}
				
		return query;
	}
	
	
	public void copyFeeChallanPrintData(FeePayment feePayment,FeePaymentForm feePaymentForm) throws Exception {
		log.debug("Helper : Entering copyFeeChallanPrintData");
		if(feePayment.getStudent().getAdmAppln() != null)
		{
			feePaymentForm.setApplicationId(String.valueOf(feePayment.getStudent().getAdmAppln().getApplnNo()));
		}

		
		feePaymentForm.setBillNo(feePayment.getBillNo());
//		feePaymentForm.setChallanPrintDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
		feePaymentForm.setChallanPrintDate(CommonUtil.ConvertStringToDateFormat(feePayment.getChallenPrintedDate().toString(),"yyyy-MM-dd", "dd/MM/yyyy").toString());

		if(feePayment.getStudent()!=null)
		{
			Student student = feePayment.getStudent();
			StringBuffer studentName = new StringBuffer(); 
			if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) 
			{
				studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
				studentName.append(" ");
			} 
			if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) 
			{
				studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
				studentName.append(" ");
			}
			if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) 
			{
				studentName.append(student.getAdmAppln().getPersonalData().getLastName());
				studentName.append(" ");
			}
			feePaymentForm.setStudentName(studentName.toString());
			if(feePaymentForm.getSecondLanguage() == null || feePaymentForm.getSecondLanguage().trim().isEmpty()){
				if(student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getSecondLanguage()!=null)
				{
					feePaymentForm.setSecondLanguage(student.getAdmAppln().getPersonalData().getSecondLanguage());
				}
			}
			if(student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getFatherName()!=null)
			{
				feePaymentForm.setFatherName(student.getAdmAppln().getPersonalData().getFatherName());
			}
			if(student.getAdmAppln().getPersonalData()!=null)
			{
				String address="";
				address=student.getAdmAppln().getPersonalData().getPermanentAddressLine1();
				if(student.getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null && student.getAdmAppln().getPersonalData().getPermanentAddressLine2().length()!=0)
					address=address+","+student.getAdmAppln().getPersonalData().getPermanentAddressLine2();
				if(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!=null && student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().length()!=0)	
					address=address+","+student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId();
				if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!=null)
					address=address+","+student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName();
				if(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!=null)	
					address=address+","+student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName();
				
				feePaymentForm.setAddress(address);
			}
			
			if(student.getRegisterNo()!= null && !student.getRegisterNo().isEmpty() && student.getAdmAppln().getApplnNo()!=0){
				feePaymentForm.setApplicationId(student.getRegisterNo()+" & "+student.getAdmAppln().getApplnNo());
			}
			else if(student.getRegisterNo()!= null && !student.getRegisterNo().isEmpty()){
				feePaymentForm.setApplicationId(student.getRegisterNo());
			}
			if(student.getRegisterNo()!= null && !student.getRegisterNo().isEmpty() && student.getAdmAppln().getApplnNo()!=0){
				feePaymentForm.setRegistrationNo(student.getRegisterNo());
			}
			else if(student.getAdmAppln().getApplnNo()!=0)
			{
				feePaymentForm.setApplicationId(Integer.toString(student.getAdmAppln().getApplnNo()));
			}
			if(student.getAdmAppln().getFeeCategory()!=null)
			{
				feePaymentForm.setFeeCategoryName(student.getAdmAppln().getFeeCategory().getName());
			}
		}
		if(feePayment.getManualClassName()==null || feePayment.getManualClassName().isEmpty()){ 
		if(feePayment.getStudent()!= null && feePayment.getStudent().getClassSchemewise()!= null && feePayment.getStudent().getClassSchemewise().getClasses()!= null && feePayment.getStudent().getClassSchemewise().getClasses().getName()!= null)
		{
			feePaymentForm.setClassName(feePayment.getStudent().getClassSchemewise().getClasses().getName());
		}
		else
		if(feePayment.getStudent()!= null && feePayment.getStudent().getAdmAppln()!= null && feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId()!= null && feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode()!= null)
		{
			feePaymentForm.setClassName(feePayment.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode());
		}
		}else{
			feePaymentForm.setClassName(feePayment.getManualClassName());
		}
		
		if(feePayment.getStudent()!= null && feePayment.getStudent().getAdmAppln()!= null && feePayment.getStudent().getAdmAppln().getCourse()!= null && feePayment.getStudent().getAdmAppln().getCourse().getProgram() != null && feePayment.getStudent().getAdmAppln().getCourse().getProgram().getProgramType() != null )
		{
			feePaymentForm.setProgramType(feePayment.getStudent().getAdmAppln().getCourse().getProgram().getProgramType().getName());
		}
		
		if(feePayment.getCourse()!=null)
		{
			feePaymentForm.setCourseName(feePayment.getCourse().getName());
		}
		
		PrintChalanTO printChalanTO;
		FeePaymentDetail feePaymentDetail;
		Map<Integer,List<PrintChalanTO>> feePaymentDetailsMap=new TreeMap<Integer, List<PrintChalanTO>>();
		Set<FeePaymentDetail> feePaymentList = feePayment.getFeePaymentDetails();
		Iterator<FeePaymentDetail> itr = feePaymentList.iterator();
		List<PrintChalanTO> feeList=null;
		double total=0.0;
		double totalAmount = 0.0;
		while(itr.hasNext()) 
		{
			
			feePaymentDetail = itr.next();
			if(feePaymentDetail.getAmountPaid() == null || feePaymentDetail.getAmountPaid().intValue() <= 0){
				continue;
			}
			feePaymentForm.setSelectFeePayYear(String.valueOf(feePaymentDetail.getSemesterNo()));

			if(feePaymentDetailsMap.get(feePaymentDetail.getFeeAccount().getPrintPosition())!=null)
			{
				feeList=feePaymentDetailsMap.get(feePaymentDetail.getFeeAccount().getPrintPosition());
			}
			else
			{
				feeList=new ArrayList<PrintChalanTO>();
				feePaymentDetailsMap.put(feePaymentDetail.getFeeAccount().getPrintPosition(),feeList);
			}
			
			String feeGroupName="";
			boolean feeGroupFound=false;
			if(feePaymentDetail.getFeeHeading().getFeeGroup().getName()!=null)
			{
				feeGroupName=feePaymentDetail.getFeeHeading().getFeeGroup().getName();
				for(PrintChalanTO chalan:feeList)
				{
					if(chalan.getFeeGroup().equals(feeGroupName))
					{
						feeGroupFound=true;
						if(feePaymentDetail.getAmountPaid()!=null)
						{
							chalan.setAmountPaid(chalan.getAmountPaid()+feePaymentDetail.getAmountPaid().intValue());
							total=total+feePaymentDetail.getAmountPaid().intValue();
						}
						if(feePaymentDetail.getTotalAmount()!=null)
						{
							chalan.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
							totalAmount=totalAmount+feePaymentDetail.getTotalAmount().intValue();
						}
						break;
					}
				}
				if((!feeGroupFound)&&(feeGroupName.equalsIgnoreCase("Extra Curicular Activities") || feeGroupName.equalsIgnoreCase("Miscellaneous Fees") || feeGroupName.equalsIgnoreCase("Specific Fees")))
				{
					for(PrintChalanTO chalan:feeList)
					{
						if(chalan.getFeeGroup().equals("Specific Fees"))
						{
							feeGroupFound=true;
							if(feePaymentDetail.getAmountPaid()!=null)
							{
								chalan.setAmountPaid(chalan.getAmountPaid()+feePaymentDetail.getAmountPaid().intValue());
								total=total+feePaymentDetail.getAmountPaid().intValue();
							}
							if(feePaymentDetail.getTotalAmount()!=null)
							{
								chalan.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
								
								totalAmount=totalAmount+feePaymentDetail.getTotalAmount().intValue();
							}
							break;
						}
					}
					if(!feeGroupFound)
					{
						feeGroupFound=true;
						printChalanTO = new PrintChalanTO();
						printChalanTO.setFeeGroup("Specific Fees");
						if(feePaymentDetail.getFeeAccount()!= null){
							printChalanTO.setPrintAccountName(feePaymentDetail.getFeeAccount().getPrintAccountName());
						}
						if(feePaymentDetail.getAmountPaid()!=null)
						{
							printChalanTO.setAmountPaid(feePaymentDetail.getAmountPaid().intValue());
							total=total+printChalanTO.getAmountPaid();
						}
						if(feePaymentDetail.getTotalAmount()!=null)
						{
							printChalanTO.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
							
							totalAmount=totalAmount+feePaymentDetail.getTotalAmount().intValue();
						}

						feeList.add(printChalanTO);
					}
				}
				if(!feeGroupFound)
				{
					printChalanTO = new PrintChalanTO();
					printChalanTO.setFeeGroup(feeGroupName);
					if(feePaymentDetail.getFeeAccount()!= null){
						printChalanTO.setPrintAccountName(feePaymentDetail.getFeeAccount().getPrintAccountName());
					}
					if(feePaymentDetail.getFeeAccount()!= null){
						printChalanTO.setBankInfo(feePaymentDetail.getFeeAccount().getBankInformation());
					}
					if(feePaymentDetail.getAmountPaid()!=null)
					{
						printChalanTO.setAmountPaid(feePaymentDetail.getAmountPaid().intValue());
						total=total+printChalanTO.getAmountPaid();
					}
					if(feePaymentDetail.getTotalAmount()!=null)
					{
						printChalanTO.setTotalAmount(Integer.toString(feePaymentDetail.getTotalAmount().intValue()));
						totalAmount=totalAmount+feePaymentDetail.getTotalAmount().intValue();
					}

					feeList.add(printChalanTO);
				}
			}
			
		Collections.sort(feeList, new FeeHeadComparator());	
			
		}
		feePaymentForm.setTotalPaidAmt(total);
		feePaymentForm.setFeePaymentDetails(feePaymentDetailsMap);
		log.debug("Helper : Leaving copyFeeChallanPrintData");
 	
	}
	
	/**
	 * 
	 * @param studentList
	 * @return
	 */
	public List<FeeStudentDetailTO> studentDetailstoTO(List<Student> studentList) {
		log.debug("inside copyGradeBosToTos");
		List<FeeStudentDetailTO> studentTOList = new ArrayList<FeeStudentDetailTO>();
		Iterator<Student> iterator = studentList.iterator();
		Student student;
		FeeStudentDetailTO feeStudentDetailTO;
		while (iterator.hasNext()) {
			feeStudentDetailTO = new FeeStudentDetailTO();
			student = (Student) iterator.next();
			feeStudentDetailTO.setApplnNo(Integer.toString(student.getAdmAppln().getApplnNo()));
			feeStudentDetailTO.setName(student.getAdmAppln().getPersonalData().getFirstName().trim());
			feeStudentDetailTO.setCourse(student.getAdmAppln().getCourse().getName().trim());
			feeStudentDetailTO.setRegNo(student.getRegisterNo());
			feeStudentDetailTO.setRollNo(student.getRollNo());
			feeStudentDetailTO.setStudentId(Integer.toString(student.getId()));
			feeStudentDetailTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			//feeStudentDetailTO.setProgramType(student.getAdmAppln().getCourse().getProgram().getProgramType().getName());
			studentTOList.add(feeStudentDetailTO);
		}
		log.debug("leaving copyGradeBosToTos");
		return studentTOList;
	}
}