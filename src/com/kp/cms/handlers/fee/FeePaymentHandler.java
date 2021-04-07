package com.kp.cms.handlers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeAdditionalAccountAssignment;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.FeePaymentDetailAmount;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.exceptions.ChallanAlreadyPrintedException;
import com.kp.cms.exceptions.CurriculumSchemeNotFoundException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.FreeShipException;
import com.kp.cms.exceptions.RegNumNotExistException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.helpers.fee.FeePaymentHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.fee.FeeDisplayTO;
import com.kp.cms.to.fee.FeePaymentDisplayTO;
import com.kp.cms.to.fee.FeePaymentEditTO;
import com.kp.cms.to.fee.FeeStudentDetailTO;
import com.kp.cms.transactions.admission.ICurriculumSchemeTransaction;
import com.kp.cms.transactions.fee.IFeePaymentTransaction;
import com.kp.cms.transactions.fee.IFeeTransaction;
import com.kp.cms.transactionsimpl.admission.CurriculumSchemeTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeePaymentTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeeTransactionTmpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.FeePaymentListComparator;
/**
 * This is a Handler class for fee Payment
 */
public class FeePaymentHandler {
	private static final Log log = LogFactory.getLog(FeePaymentHandler.class);
	private static FeePaymentHandler feePaymentHandler= null;
	
	/*
	 *  returns the singleton object of FeePaymentHandler.
	 */
	public static FeePaymentHandler getInstance() {
	      if(feePaymentHandler == null) {
	    	  feePaymentHandler = new FeePaymentHandler();
	      }
	      return feePaymentHandler;
	}
	
	/**
	 * 
	 * @param ApplicationNo
	 * @param semsSet
	 * @return List of Fee corresponding to application NO.
	 *         will search based on course, subject Group and admitted through id's
	 * @throws Exception
	 */
	public List<Fee> getFeePaymentDetailsOnApplicationNo(FeePaymentForm feePaymentForm) throws ChallanAlreadyPrintedException,Exception {
		   log.debug("Entering the getFeePaymentDetailsOnApplicationNo");
		   List<Fee> feeBoList = new ArrayList<Fee>(); 	
		   IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		   
		   Set<Integer> courseSet = new HashSet<Integer>();
		   String year;
		   Set<Integer> semSet = new HashSet<Integer>();
		   
		   // setting course Id taken from AdmAppl table.
		   courseSet.add(Integer.valueOf(feePaymentForm.getCourseId()));
		   // setting year Id taken from AdmAppl table.
		  // year = feePaymentForm.getYear();	
		   year = feePaymentForm.getAcademicYear();
		   // getting Admitted through from ApplicationAdmittedThroughTemp table.

		   String semsArray[] = feePaymentForm.getSelectedSems();
		   for(int i =0 ;i<semsArray.length; i++) {
			   semSet.add(Integer.valueOf(semsArray[i]));
		   }
			
		  
		   
		     		   
		   Calendar cal = new GregorianCalendar();
		   int currentYear = cal.get(Calendar.YEAR);  
		   IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		  /* List<Object[]> feePaymentList = feePaymentTransaction.getChallanIdsForApplicant(semSet, feePaymentForm.getApplicationId(), feePaymentForm.getRegistrationNo(), feePaymentForm.getRollNumber(),currentYear);
		   if(feePaymentList != null && !feePaymentList.isEmpty()) {
			   throw new ChallanAlreadyPrintedException(); 
		   }*/
		  int feePayYear=0;
		  Iterator<Integer> itr = semSet.iterator();
		  while (itr.hasNext()){
			 int sem = itr.next();
			 if(sem==1 || sem==2)
				 feePayYear=1;
			 else if(sem==3 || sem==4)
				 feePayYear=2;
			 else if(sem==5 || sem==6)
				 feePayYear=3;
		   }
		   feePaymentForm.setSelectFeePayYear(String.valueOf(feePayYear)); 
		   
		   feeBoList = feeAssignmentTransaction.getFeesPaymentDetailsForApplicationNo(courseSet,year,semSet, feePaymentForm.isAidedStudent(),feePaymentForm);
		   log.debug("Leaving the getFeePaymentDetailsOnApplicationNo");
		   return feeBoList;
	}
	
	/**
	 * Loads all the Fee payments.
	 * @return Map
	 * @throws Exception
	 */
	public Map<Integer,String> getAllFeePaymentMode() throws Exception {
		log.debug("Entering the getAllFeePaymentMode");
		Map<Integer,String> paymentModeMap = new HashMap<Integer,String>();
		
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		List<FeePaymentMode> paymentModesList = feePaymentTransaction.getAllPaymentMode();
		Iterator<FeePaymentMode> itr = paymentModesList.iterator();
		FeePaymentMode feePaymentMode;
		while(itr.hasNext()){
			feePaymentMode = itr.next();
			paymentModeMap.put(feePaymentMode.getId(),feePaymentMode.getName());
		}
		log.debug("Leaving the getAllFeePaymentMode");
		return paymentModeMap;
	}
	
	
	/**
	 * This method called when user Enters applicationNo and year.
	 * after clicking search
	 * @param feePaymentForm
	 * @return
	 * @throws Exception
	 */
	public void setSemistersAndFeeGroup(FeePaymentForm feePaymentForm ) throws SubjectGroupNotDefinedException,DataNotFoundException,CurriculumSchemeNotFoundException,FreeShipException,Exception {
		log.debug("Entering the setSemistersAndFeeGroup");
		Map<Integer,Integer> semisterMap = new HashMap<Integer,Integer>();
		Map<Integer,String> feeGroupMap = new HashMap<Integer,String>();
		
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		Student student = feePaymentTransaction.getApplicantDetails(feePaymentForm.getApplicationId(),feePaymentForm.getYear(), feePaymentForm.getRegistrationNo(), feePaymentForm.getRollNumber());
		
		/*if(admAppln != null && admAppln.getApplnNo() == 0 ) {
			throw new DataNotFoundException();
		}
		*/
		if( student !=null && student.getAdmAppln() == null) {
			if(feePaymentForm.getApplicationId() != null && !feePaymentForm.getApplicationId().trim().isEmpty()){
				throw new DataNotFoundException("knowledgepro.fee.application.notpresent");
			}
			else if(feePaymentForm.getRegistrationNo() != null && !feePaymentForm.getRegistrationNo().trim().isEmpty()){
				throw new DataNotFoundException("knowledgepro.fee.register.no.not.present");
			}
			else if(feePaymentForm.getRollNumber() != null && !feePaymentForm.getRollNumber().trim().isEmpty()){
				throw new DataNotFoundException("knowledgepro.fee.roll.no.not.present");
			}
		}
		
		if(student !=null && student.getAdmAppln() != null && student.getAdmAppln().getIsFreeShip()) {
			throw new FreeShipException();
		}
		
		if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getIsLig()!=null && student.getAdmAppln().getIsLig()){
			feePaymentForm.setLigExemption(true);
		}else{
			feePaymentForm.setLigExemption(false);
		}
		
		if(student != null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getCaste() != null && student.getAdmAppln().getPersonalData().getCaste().getIsFeeExcemption()){
			feePaymentForm.setCasteExemption(true);
		}else{
			feePaymentForm.setCasteExemption(false);
		}
		
		// vinodha applied year of student
		
		feePaymentForm.setSemAcademicYear(String.valueOf(student.getAdmAppln().getAppliedYear()));
		// Reading the no of semester from Curriculum scheme. 
		ICurriculumSchemeTransaction curriculumSchemeTransaction = new CurriculumSchemeTransactionImpl();
//		List<CurriculumScheme> curriculumSchemeList = curriculumSchemeTransaction.getCurriculumSchemeCourseByCourse(student.getAdmAppln().getCourse().getId(),student.getAdmAppln().getAppliedYear());
		List<CurriculumSchemeDuration> list = curriculumSchemeTransaction.getScheme(student.getAdmAppln().getCourse().getId(),student.getAdmAppln().getAppliedYear(),student);
		List<Integer> semList = new ArrayList<Integer>();
		if(list!=null){
			Iterator<CurriculumSchemeDuration> itr = list.iterator();
			while (itr.hasNext()) {
				CurriculumSchemeDuration curriculumSchemeDuration = (CurriculumSchemeDuration) itr.next();
				semList.add(curriculumSchemeDuration.getSemesterYearNo());
				feePaymentForm.setCurYear(curriculumSchemeDuration.getAcademicYear().toString());
				feePaymentForm.setPreYear(Integer.toString(curriculumSchemeDuration.getAcademicYear()-1));
				//feePaymentForm.setSemAcademicYear(feePaymentForm.getYear());
			}
		}
		if(feePaymentForm.getIsCurrent().equalsIgnoreCase("yes")){
			for(Integer i:semList){
				semisterMap.put(i.intValue(), i.intValue());
			}
		}else{
			int i = Collections.min(semList);
			for(int j=0;j<2;j++){
				if((i-1)!=0){
					semisterMap.put(i-1, i-1);
					i--;
				}
				if(i==0){
					break;
				}
			}
			
		}
		FeePaidHandler.getInstance().setFinancialYearId(feePaymentForm);
		if(feePaymentForm.getIsCurrent().equalsIgnoreCase("yes")){
		List<FeePayment> payList =  FeePaidHandler.getInstance().checkPreviousBalance(student,feePaymentForm);
		boolean isCompletelyPaid=false;
		if(payList!=null && !payList.isEmpty()){
			for(FeePayment f:payList){
				if(f.getIsCompletlyPaid()){
					isCompletelyPaid=true;
				}
			}
			if(!isCompletelyPaid){
				throw new ApplicationException();
			}
		}
		}
//		if(feePaymentForm.getIsCurrent().equalsIgnoreCase("yes")){
//			feePaymentForm.setFinancialYearId(feePaymentForm.getCurFinId());
//		}else{
//			feePaymentForm.setFinancialYearId(feePaymentForm.getPreFinId());
//		}
//		if(curriculumSchemeList == null || curriculumSchemeList.isEmpty()) {
//			throw new CurriculumSchemeNotFoundException();
//		}
//		
//		int year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
////		int noOfScheme = ((CurriculumScheme)curriculumSchemeList.get(0)).getNoScheme();
//		// putting all semister no in to MAP.
//		if(feePaymentForm.getIsCurrent().equalsIgnoreCase("no")){
//			year = year-1;
//		}
//		Set<CurriculumSchemeDuration> setCurr = curriculumSchemeList.get(0).getCurriculumSchemeDurations();
//		for(CurriculumSchemeDuration d:setCurr){
//			if(d.getAcademicYear()==year){
//				semisterMap.put(d.getSemesterYearNo(), d.getSemesterYearNo());
//			}
//		}
////		for(int i=1;i<=noOfScheme;i++) {
////			semisterMap.put(Integer.valueOf(i), Integer.valueOf(i));
////		}
		
		Set<Integer> courseSet = new HashSet<Integer>();
		courseSet.add(student.getAdmAppln().getCourse().getId());
		
		feeGroupMap = FeePaymentHelper.getInstance().getFeeGroupByCourse(courseSet,student.getAdmAppln().getAppliedYear().toString());
		
		
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
		feePaymentForm.setApplicationId(String.valueOf(student.getAdmAppln().getApplnNo()));
		feePaymentForm.setCourseName(student.getAdmAppln().getCourse().getName());
		feePaymentForm.setStudentName(studentName.toString());
		feePaymentForm.setAdmittedThroughName(student.getAdmAppln().getAdmittedThrough().getName());
		feePaymentForm.setAdmittedThrough(String.valueOf(student.getAdmAppln().getAdmittedThrough().getId()));
		if(student.getAdmAppln().getPersonalData()!=null && student.getAdmAppln().getPersonalData().getSecondLanguage()!=null)
		{
			feePaymentForm.setSecondLanguage(student.getAdmAppln().getPersonalData().getSecondLanguage());
		}

		feePaymentForm.setSemMap(semisterMap);
		feePaymentForm.setFeeGroupMap(feeGroupMap);
		feePaymentForm.setCourseId(String.valueOf(student.getAdmAppln().getCourse().getId()));
		feePaymentForm.setRegistrationNo(student.getRegisterNo());
		feePaymentForm.setRollNumber(student.getRollNo());
		feePaymentForm.setStudentId(String.valueOf(student.getId()));
		feePaymentForm.setAidedStudent(student.getAdmAppln().getIsAided());
		feePaymentForm.setAdmApplnId(String.valueOf(student.getAdmAppln().getId()));
		if(student.getAdmAppln().getPersonalData().getCaste()!= null){
			feePaymentForm.setCasteId(Integer.toString(student.getAdmAppln().getPersonalData().getCaste().getId()));
		}
		if(student.getAdmAppln().getPersonalData()!= null){
			feePaymentForm.setPersonalDataId(Integer.toString(student.getAdmAppln().getPersonalData().getId()));
		}
		
		String className = feePaymentTransaction.getClassNameByStudentId(Integer.parseInt(feePaymentForm.getStudentId()));
		if(className != null && !className.trim().isEmpty()){
			feePaymentForm.setClassName(className);
		}
		
		feePaymentForm.setFeeCategoryName(student.getAdmAppln().getFeeCategory().getName());
		feePaymentForm.setFeeCategory(String.valueOf(student.getAdmAppln().getFeeCategory().getId()));

		Map<Integer,String> feeOptionalGroupMap = FeePaymentHelper.getInstance().getFeeAdditionalFeeGroup();
		feePaymentForm.setFeeOptionalGroupMap(feeOptionalGroupMap);
		log.debug("Leaving the setSemistersAndFeeGroup");
	}
		
	/**
	 * 
	 * @param feePaymentForm
	 * @throws Exception
	 */
	public void initSecondPageFeePayment(FeePaymentForm feePaymentForm) throws RegNumNotExistException,Exception {
		log.debug("Entering the initSecondPageFeePayment");
		if((feePaymentForm.getApplicationId() != null && feePaymentForm.getApplicationId().length() != 0) ||
				feePaymentForm.getRegistrationNo()!= null && feePaymentForm.getRegistrationNo().length()!= 0 ||
				feePaymentForm.getRollNumber()!= null && feePaymentForm.getRollNumber().length()!= 0) { 
			setSemistersAndFeeGroup(feePaymentForm);
		} else {
			throw new RegNumNotExistException();
		}
	}
	
	/**
	 * This method will loads the fee payment details for particular Id
	 * @param feePaymentForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void getFeePaymentDetails(FeePaymentForm feePaymentForm) throws ChallanAlreadyPrintedException,DataNotFoundException,Exception {
		log.debug("Entering the getFeePaymentDetails");
		List<Fee> feesViewList = new ArrayList<Fee>();
		String semArray[] = feePaymentForm.getSelectedSems();
		Set<Integer> semSet = new HashSet<Integer>();
		for(int i=0 ;i<semArray.length;i++) {
			semSet.add(Integer.valueOf(semArray[i]));
		}
		
		Map<Integer,String> paymentModeMap = getAllFeePaymentMode();
		feePaymentForm.setPaymentModeMap(paymentModeMap);
		
		List<FeeDisplayTO> feeHeadList = new ArrayList<FeeDisplayTO>();

		List<FeePaymentDisplayTO> feeDispList = new ArrayList<FeePaymentDisplayTO>();
		
	//	Calendar cal = new GregorianCalendar();
	//	int year = cal.get(Calendar.YEAR);  
		int year = getSemAcademicYear(semSet,feePaymentForm);
		feePaymentForm.setAcademicYear(String.valueOf(year));
		
		int feeCategory = Integer.parseInt(feePaymentForm.getFeeCategory());
		feesViewList = getFeePaymentDetailsOnApplicationNo(feePaymentForm);

		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		
		feePaymentForm.setConsAplha(feePaymentTransaction.getConcessionAlpha(feePaymentForm.getFinancialYearId()));
		
		if(feePaymentForm.getCasteId()!= null && !feePaymentForm.getCasteId().trim().isEmpty()){
			feePaymentForm.setCasteExemption(feePaymentTransaction.isCasteExemption(Integer.parseInt(feePaymentForm.getCasteId())));
			
		}
		// Written by vinoth A
		String finYear = feePaymentTransaction.getFinancialYear(feePaymentForm.getFinancialYearId());
		feePaymentForm.setCurFinId(feePaymentForm.getFinancialYearId());
		int prefinYearId = feePaymentTransaction.getFinancialYearByYear((Integer.parseInt(finYear)-1)+"-"+finYear);
		feePaymentForm.setPreFinId(String.valueOf(prefinYearId));
		
		List<FeePayment> oldFeePaymentList = new ArrayList<FeePayment>();
		if(feePaymentForm.getIsCurrent().equalsIgnoreCase("yes")){
			oldFeePaymentList = feePaymentTransaction.getOldFeePaymentDetails(feePaymentForm.getApplicationId(), feePaymentForm.getCurFinId()/*feePaymentForm.getYear()*/);
			
		}else{
			oldFeePaymentList = feePaymentTransaction.getOldFeePaymentDetails(feePaymentForm.getApplicationId(), feePaymentForm.getPreFinId()/*feePaymentForm.getYear()*/);
		}
		if(oldFeePaymentList == null || oldFeePaymentList.size() <= 0){
			feePaymentForm.setOldReceiptFound(false);
		}
		else{
			feePaymentForm.setOldReceiptFound(true);
		}
		Map<String, Double> balanceAmtMap = new HashMap<String, Double>();
		
		boolean paid = false;
		Iterator<FeePayment> oldItr = oldFeePaymentList.iterator();
		while (oldItr.hasNext()) {
			FeePayment feePayment = (FeePayment) oldItr.next();
			Set<FeePaymentDetail> feePaymentDetailsSet =  feePayment.getFeePaymentDetails();
			Iterator<FeePaymentDetail> detlSetItr = feePaymentDetailsSet.iterator();
			while (detlSetItr.hasNext()) {
				FeePaymentDetail feePaymentDetail = (FeePaymentDetail) detlSetItr
						.next();
				Double amt;
				if(feePaymentDetail.getFeeHeading()!= null){
				
					double amtPaid = 0;
					if(feePaymentDetail.getAmountPaid()!= null){
						amtPaid = feePaymentDetail.getAmountPaid().doubleValue();
					}
					double concAmt = 0;
					if(feePaymentDetail.getConcessionAmount()!= null){
						concAmt = feePaymentDetail.getConcessionAmount().doubleValue();
					}
					String key = "";
					if(feePaymentDetail.getSemesterNo() != null && feePaymentDetail.getSemesterNo() > 0){
						if(feePaymentDetail.getFeeAccount() != null){
						key = Integer.toString(feePaymentDetail.getFeeHeading().getId()).trim() + Integer.toString(feePaymentDetail.getFeeAccount().getId()).trim() + Integer.toString(feePaymentDetail.getSemesterNo());
					}else {
						key = Integer.toString(feePaymentDetail.getFeeHeading().getId()).trim()  + Integer.toString(feePaymentDetail.getSemesterNo());
					}
					}
					else
					{
						if(feePaymentDetail.getFeeAccount() != null){
						key = Integer.toString(feePaymentDetail.getFeeHeading().getId()).trim() + Integer.toString(feePaymentDetail.getFeeAccount().getId()).trim();
						}else {
							key = Integer.toString(feePaymentDetail.getFeeHeading().getId()).trim() ;
						}
					}
					if(balanceAmtMap.containsKey(key)){
						amt = new Double(balanceAmtMap.get(key));
						balanceAmtMap.put(key, amt + amtPaid + concAmt);
					}
					else{
						balanceAmtMap.put(key,  amtPaid + concAmt);
					}
				}
			}
			
		}
		
		
		Iterator<Fee> feeItr = feesViewList.iterator();
		FeeDisplayTO feeDispTO;
		FeePaymentDisplayTO feePaymentDisplayTO;
		double grandTotal = 0;
		double amount = 0;
		double exemptionAmt = 0;
		double totalValue = 0.0;
		while (feeItr.hasNext()) {
			Fee fee = (Fee) feeItr.next();
			feeDispTO = new FeeDisplayTO();
			feePaymentDisplayTO = new FeePaymentDisplayTO();
			Set<FeeAccountAssignment> feeAcountAssignmentsSet = fee.getFeeAccountAssignments();
			//Iterator<FeeAccountAssignment> itr1 = fee.getFeeAccountAssignments().iterator();
			Iterator<FeeAccountAssignment> itr1 = feeAcountAssignmentsSet.iterator();
			feeHeadList = new ArrayList<FeeDisplayTO>();
			if(fee.getSemesterNo()!= null){
				feePaymentDisplayTO.setSemester(fee.getSemesterNo().toString());
			}
			while (itr1.hasNext()) {
				FeeAccountAssignment feeAccountAssignment = (FeeAccountAssignment) itr1.next();
				if(fee.getAidedUnaided().equalsIgnoreCase("Aided")){
					if(feeAccountAssignment.getFeeCategory()!= null && feeCategory != feeAccountAssignment.getFeeCategory().getId()){
						continue;
					}
					if(feeAccountAssignment.getAmount() == null || feeAccountAssignment.getAmount().doubleValue() <= 0){
						continue;
					}
					
					feeDispTO = new FeeDisplayTO();
					feeDispTO.setFeeHeadName(feeAccountAssignment.getFeeHeading().getName());
					feeDispTO.setAccName(feeAccountAssignment.getFeeAccount().getCode());
					feeDispTO.setAccId(feeAccountAssignment.getFeeAccount().getId());
					feeDispTO.setFeeHeadId(Integer.toString(feeAccountAssignment.getFeeHeading().getId()));
					feeDispTO.setGroupId(Integer.toString(feeAccountAssignment.getFeeHeading().getFeeGroup().getId()));
					
					
					if(feePaymentForm.getIsFeeExemption().equalsIgnoreCase("true")){
						if(feePaymentForm.isCasteExemption() && feeAccountAssignment.getCasteAmount()!= null){
							amount = feeAccountAssignment.getCasteAmount().doubleValue();
							exemptionAmt =  feeAccountAssignment.getAmount().doubleValue() - feeAccountAssignment.getCasteAmount().doubleValue(); 
						}
						else if(feePaymentForm.isLigExemption() && feeAccountAssignment.getLigAmount()!= null ){
							amount = feeAccountAssignment.getLigAmount().doubleValue();
							exemptionAmt =  feeAccountAssignment.getAmount().doubleValue() - feeAccountAssignment.getLigAmount().doubleValue(); 
						}
					}
					else{
						if(feeAccountAssignment.getAmount()!= null){
							amount = feeAccountAssignment.getAmount().doubleValue();
						}
					}
					String key = "";
					if(fee.getSemesterNo()!= null && fee.getSemesterNo() > 0){
						key =  Integer.toString(feeAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAccountAssignment.getFeeAccount().getId()).trim() + fee.getSemesterNo().toString();
					}else{
						key = Integer.toString(feeAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAccountAssignment.getFeeAccount().getId()).trim(); 
					}

					if(feeAccountAssignment.getAmount()!= null){
						if(balanceAmtMap.get(key) != null){
							feeDispTO.setTotalAmount(String.valueOf(amount - balanceAmtMap.get(key)));
						}
						else{
							feeDispTO.setTotalAmount(String.valueOf(amount));
						}
					}

					
					feeDispTO.setDiscountAmt(String.valueOf(exemptionAmt));
					if(balanceAmtMap.get(key) != null){
						feeDispTO.setPaidAmount(String.valueOf(amount - balanceAmtMap.get(key)));
					}else{
						feeDispTO.setPaidAmount(String.valueOf(amount));
					}
					if(feeDispTO.getTotalAmount()!= null && !feeDispTO.getTotalAmount().trim().isEmpty()){
						grandTotal = grandTotal + new BigDecimal(feeDispTO.getTotalAmount()).doubleValue();
					}
					if(feeAccountAssignment.getCurrencyByCurrencyId()!= null){
						feeDispTO.setCurrencyId(Integer.toString(feeAccountAssignment.getCurrencyByCurrencyId().getId()));
					}
					feeDispTO.setConcessionAmt("0.00");
					feeDispTO.setBalanceAmt("0.00");
					feeHeadList.add(feeDispTO);
				}else{
					//if(feeAccountAssignment.getFeeCategory().getId()!=1){
						//continue;
					//}
					if(feeAccountAssignment.getAmount() == null || feeAccountAssignment.getAmount().doubleValue() <= 0){
						continue;
					}
					
					feeDispTO = new FeeDisplayTO();
					feeDispTO.setFeeHeadName(feeAccountAssignment.getFeeHeading().getName());
					feeDispTO.setAccName(feeAccountAssignment.getFeeAccount().getCode());
					feeDispTO.setAccId(feeAccountAssignment.getFeeAccount().getId());
					feeDispTO.setFeeHeadId(Integer.toString(feeAccountAssignment.getFeeHeading().getId()));
					feeDispTO.setGroupId(Integer.toString(feeAccountAssignment.getFeeHeading().getFeeGroup().getId()));
					
					
					if(feePaymentForm.getIsFeeExemption().equalsIgnoreCase("true")){
						if(feePaymentForm.isCasteExemption() && feeAccountAssignment.getCasteAmount()!= null){
							amount = feeAccountAssignment.getCasteAmount().doubleValue();
							exemptionAmt =  feeAccountAssignment.getAmount().doubleValue() - feeAccountAssignment.getCasteAmount().doubleValue(); 
						}
						else if(feePaymentForm.isLigExemption() && feeAccountAssignment.getLigAmount()!= null ){
							amount = feeAccountAssignment.getLigAmount().doubleValue();
							exemptionAmt =  feeAccountAssignment.getAmount().doubleValue() - feeAccountAssignment.getLigAmount().doubleValue(); 
						}
					}
					else{
						if(feeAccountAssignment.getAmount()!= null){
							amount = feeAccountAssignment.getAmount().doubleValue();
						}
					}
					String key = "";
					if(fee.getSemesterNo()!= null && fee.getSemesterNo() > 0){
						
						key =  Integer.toString(feeAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAccountAssignment.getFeeAccount().getId()).trim() + fee.getSemesterNo().toString();
					}else{
						key = Integer.toString(feeAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAccountAssignment.getFeeAccount().getId()).trim(); 
					}

					if(feeAccountAssignment.getAmount()!= null){
						
							feeDispTO.setTotalAmount(String.valueOf(amount));
						
					}
					FeePayment feePayment = getFeeDetailsForApplication(feePaymentForm);
					feeDispTO.setDiscountAmt(String.valueOf(exemptionAmt));
//					if(balanceAmtMap.get(key) != null){
//						feeDispTO.setPaidAmount(String.valueOf(amount - balanceAmtMap.get(key)));
//					}else{
//						feeDispTO.setPaidAmount(String.valueOf(amount));
//					}
					if(feePayment != null){
						if(feePayment.getRemarks() != null && !feePayment.getRemarks().isEmpty()){
						feeDispTO.setRemarks(feePayment.getRemarks());
						}
					if(feePayment.getTotalFeePaid() != null){
						feeDispTO.setPaidAmount(String.valueOf(feePayment.getTotalFeePaid().doubleValue()));
					}
					}else {
						feeDispTO.setPaidAmount("0.00");
					}
					if(feeDispTO.getTotalAmount()!= null && !feeDispTO.getTotalAmount().trim().isEmpty()){
						grandTotal = grandTotal + new BigDecimal(feeDispTO.getTotalAmount()).doubleValue();
						totalValue = new BigDecimal(feeDispTO.getTotalAmount()).doubleValue();
					}
					if(feeAccountAssignment.getCurrencyByCurrencyId()!= null){
						feeDispTO.setCurrencyId(Integer.toString(feeAccountAssignment.getCurrencyByCurrencyId().getId()));
					}
					feeDispTO.setConcessionAmt("0.00");
					if(feePayment != null){
					if(feePayment.getTotalBalanceAmount() != null){
					feeDispTO.setBalanceAmt(String.valueOf(feePayment.getTotalBalanceAmount().doubleValue()));
					}
					}else {
						feeDispTO.setBalanceAmt("0.00");
					}
					if(feeDispTO.getTotalAmount().equals(feeDispTO.getPaidAmount())){
						paid = true;
					}
					feeHeadList.add(feeDispTO);
				}
			}
			Collections.sort(feeHeadList, new FeePaymentListComparator());
			feePaymentDisplayTO.setFeeDispTOList(feeHeadList);
			feeDispList.add(feePaymentDisplayTO);
		}
		feePaymentForm.setFeePaymentDisplayTOList(feeDispList);
		feePaymentForm.setGrandTotal(totalValue);
		Set<Integer> feeOptionalGroupSet = new HashSet<Integer>();
		String feeOptional[] = feePaymentForm.getSelectedfeeOptionalGroup();
		
		List<FeeAdditional> feeAdditionalList = new ArrayList<FeeAdditional>();
		if(feeOptional != null) {
			for(int j=0;j<feeOptional.length;j++) {
				feeOptionalGroupSet.add(Integer.valueOf(feeOptional[j]));
			}
			feeAdditionalList = FeeAdditionalHandler.getInstance().getFeeAdditionalByGroup(feeOptionalGroupSet);
		}
		
		if(feeAdditionalList.size() > 0){
			feeDispList = new ArrayList<FeePaymentDisplayTO>();
			Iterator<FeeAdditional> addItr = feeAdditionalList.iterator();
			while (addItr.hasNext()) {
				FeeAdditional additional = (FeeAdditional) addItr.next();
				feeDispTO = new FeeDisplayTO();
				feePaymentDisplayTO = new FeePaymentDisplayTO();
				
				Iterator<FeeAdditionalAccountAssignment> addItr1 = additional.getFeeAdditionalAccountAssignments().iterator();
				feeHeadList = new ArrayList<FeeDisplayTO>();
				
				while (addItr1.hasNext()) {
					FeeAdditionalAccountAssignment feeAdditionalAccountAssignment = (FeeAdditionalAccountAssignment) addItr1.next();
				
					feeDispTO = new FeeDisplayTO();
					feeDispTO.setFeeHeadName(feeAdditionalAccountAssignment.getFeeHeading().getName());
					feeDispTO.setAccName(feeAdditionalAccountAssignment.getFeeAccount().getCode());
					feeDispTO.setAccId(feeAdditionalAccountAssignment.getFeeAccount().getId());
					feeDispTO.setFeeHeadId(Integer.toString(feeAdditionalAccountAssignment.getFeeHeading().getId()));
					feeDispTO.setGroupId(Integer.toString(feeAdditionalAccountAssignment.getFeeHeading().getFeeGroup().getId()));
					
					if(feeAdditionalAccountAssignment.getAmount()!= null){
						amount = feeAdditionalAccountAssignment.getAmount().doubleValue();
						if(balanceAmtMap.get(Integer.toString(feeAdditionalAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAdditionalAccountAssignment.getFeeAccount().getId()).trim()) != null){
							feeDispTO.setTotalAmount(String.valueOf(amount - balanceAmtMap.get(Integer.toString(feeAdditionalAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAdditionalAccountAssignment.getFeeAccount().getId()).trim())));
						}else{
							feeDispTO.setTotalAmount(feeAdditionalAccountAssignment.getAmount().toString());
						}
					
						if(balanceAmtMap.get(Integer.toString(feeAdditionalAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAdditionalAccountAssignment.getFeeAccount().getId()).trim()) != null){
							feeDispTO.setPaidAmount(String.valueOf(amount - balanceAmtMap.get(Integer.toString(feeAdditionalAccountAssignment.getFeeHeading().getId()).trim() + Integer.toString(feeAdditionalAccountAssignment.getFeeAccount().getId()).trim())));
						}
						else{
							feeDispTO.setPaidAmount(feeAdditionalAccountAssignment.getAmount().toString());
						}
					}
					
					if(feeDispTO.getTotalAmount()!= null){
						grandTotal = grandTotal +  new BigDecimal(feeDispTO.getTotalAmount()).doubleValue();
						totalValue = new BigDecimal(feeDispTO.getTotalAmount()).doubleValue();
					}
					feeDispTO.setConcessionAmt("0.00");
					feeDispTO.setBalanceAmt("0.00");
					feeHeadList.add(feeDispTO);
				}
				feePaymentDisplayTO.setFeeDispTOList(feeHeadList);
				feeDispList.add(feePaymentDisplayTO);
			}
			feePaymentForm.setFeePaymentAdditionalList(feeDispList);
		}
		feePaymentForm.setGrandTotal(totalValue);
		
		FeePayment feePayment = getFeeDetailsForApplication(feePaymentForm);
		if(feePayment != null){
		if(feePayment.getTotalFeePaid() != null){
		feePaymentForm.setTotalPaidAmt(new BigDecimal(feePayment.getTotalFeePaid().toString()).doubleValue());
		}
		}else {
			feePaymentForm.setTotalPaidAmt(0.00);
		}
		if(feePayment != null){
		if(feePayment.getTotalBalanceAmount() != null ){
			if(feePayment.getTotalBalanceAmount().doubleValue() != 0.0){
			feePaymentForm.setTotalBalance(feePayment.getTotalBalanceAmount().doubleValue());
			}else {
				feePaymentForm.setTotalBalance(feePayment.getTotalBalanceAmount().doubleValue());
			}
		}
		}else {
			feePaymentForm.setTotalBalance(0.00);
		}
		if(feePayment != null){
			if(feePayment.getTotalAmount() != null){
			feePaymentForm.setGrandTotal(new BigDecimal(feePayment.getTotalAmount().toString()).doubleValue());
			}
			}else {
				feePaymentForm.setGrandTotal(totalValue);
			}
		
		if(paid || feePaymentForm.getTotalPaidAmt() == 0.00){
			feePaymentForm.setNetPayable(0.00);
		}else{
		feePaymentForm.setNetPayable(feePayment.getTotalBalanceAmount().doubleValue());
		}
		log.debug("Leaving the getFeePaymentDetails");
	}

	
	private int getSemAcademicYear(Set<Integer> semSet, FeePaymentForm feePaymentForm) throws Exception {
		
		// TODO Auto-generated method stub
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		int semYear = feePaymentTransaction.getSemAcademicYear(semSet,feePaymentForm);
		return semYear;
	}

	/**
	 * This method will print the challan.
	 * @param feePaymentForm
	 * @return boolean
	 * @throws Exception
	 * @throws BillGenerationException
	 */
	public boolean printChallen(FeePaymentForm feePaymentForm) throws Exception,BillGenerationException {
		log.debug("Entering the printChallen");
		FeePayment feePayment = new FeePayment();
		boolean isSaved = false;
		// setting the main fee payment details
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		feePaymentForm.setFeePaymentDetailAmountList(null);		
		feePayment = FeePaymentHelper.getInstance().copyFeePaymentDataToBo(feePaymentForm);
		 
		
		// vinodha for pending payment verification for same amount;
		
		List<FeePayment> pendingPaymentVerification = feePaymentTransaction.getPendingPaymentVerification(feePayment);
		if(pendingPaymentVerification.size()>0)
		{
			   throw new ChallanAlreadyPrintedException(); 
		}
		isSaved =  feePaymentTransaction.saveFeePaymentDetails(feePaymentForm);
//		List<FeePaymentDetailAmount> feePaymentDetailAmounts = new ArrayList<FeePaymentDetailAmount>();
//		if(!feePaymentForm.isOldReceiptFound()){
//			feePaymentDetailAmounts = feePaymentForm.getFeePaymentDetailAmountList();
//			feePaymentTransaction.addFeePaymentDetailAmounts(feePaymentDetailAmounts);
//		}
		// setting fee payment applicant details like-> semesterNo & fee group
		// seting the fee details like accountwise details to feeePayment table.
		//List<FeePaymentDetail> feePaymentDetailList = FeePaymentHelper.getInstance().copyFeePaymentsAccountWiseAmount(feePaymentForm);
				
		//feePayment.setFeePaymentDetails(new HashSet<FeePaymentDetail>(feePaymentDetailList));
		/*if(feePaymentForm.getFeePaymentDisplayList() != null ) {
			List<FeePaymentApplicantDetails> feePaymentApplicantList = FeePaymentHelper.getInstance().copyFeePaymentsApplicantDetails(feePaymentForm);
			feePayment.setFeePaymentApplicantDetailses(new HashSet<FeePaymentApplicantDetails>(feePaymentApplicantList));
		}*/
		
		
//		boolean isBillNoSave = true;
//		if(feePaymentForm.getNetPayable() <= 0){
//			isBillNoSave = false;
//		}
		
//		if(feePaymentForm.getIsCurrent().equalsIgnoreCase("no")){
//			int billNo = feePaymentTransaction.addNewPayment(feePayment, Integer.parseInt(feePaymentForm.getCurFinId()), isBillNoSave);
//			feePaymentForm.setBillNo(String.valueOf(billNo));
//		}else{
//			int billNo = feePaymentTransaction.addNewPayment(feePayment, Integer.parseInt(feePaymentForm.getFinancialYearId()), isBillNoSave);
//			feePaymentForm.setBillNo(String.valueOf(billNo));
//		}
		
		
		/*if(feePayment!= null && feePayment.getStudent()!= null && feePaymentForm.getAdmApplnId()!= null){
			boolean updated = feePaymentTransaction.UpdateAdmittedThrough(Integer.parseInt(feePaymentForm.getAdmApplnId()), Integer.parseInt(feePaymentForm.getAdmittedThrough()));
			if(feePaymentForm.getPersonalDataId()!= null && !feePaymentForm.getPersonalDataId().trim().isEmpty()){
				feePaymentTransaction.UpdatePersonalData(Integer.parseInt(feePaymentForm.getPersonalDataId()), Integer.parseInt(feePaymentForm.getCasteId()), feePaymentForm.getSecondLanguage());
			}
		}*/
		
		log.debug("Leaving the printChallen");
		return isSaved;
	}
	/**
	 * 
	 * @param feePaymentForm
	 * @throws Exception
	 */
	public void copyPrintChallenData(FeePaymentForm feePaymentForm) throws Exception{
		log.debug("Entering the copyPrintChallenData");
		feePaymentForm.setChallanPrintDate(CommonUtil.getStringDate(new Date()));
		
	}
	/**
	 * This method will loads challan data from database.
	 * @param feePaymentForm
	 * @throws Exception
	 */
	public void getChallanData(FeePaymentForm feePaymentForm,HttpServletRequest request) throws DataNotFoundException,Exception {
		log.debug("Entering the getChallanData");
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		int financialYear = 0;
//		if(feePaymentForm.getRePrint()!=null && feePaymentForm.getRePrint().equalsIgnoreCase("yes")){
//			financialYear =Integer.parseInt(feePaymentForm.getFinancialYearId());
//		}else if(feePaymentForm.getCurFinId()!= null){
			financialYear = Integer.parseInt(feePaymentForm.getFinancialYearId());
//		}
		FeePayment feePayment = feePaymentTransaction.getFeePaymentDetailsForEdit(Integer.parseInt(feePaymentForm.getBillNo()), financialYear);
		
		if(feePayment == null) {
			throw new DataNotFoundException();
		}
		FeePaymentHelper.getInstance().copyFeeChallanPrintData(feePayment,feePaymentForm);
		log.debug("Leaving the getChallanData");
	}
	
	/**
	 * 
	 * @param billNo
	 * @param financialYearId
	 * @return
	 * @throws Exception
	 */
	public FeePaymentEditTO getFeePaymentDetailsForEdit(int billNo, int financialYearId) throws Exception {
	
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		FeePaymentEditTO feePaymentEdit = null;
		
		FeePayment feePayment = feePaymentTransaction.getFeePaymentDetailsForEdit(billNo, financialYearId);
		if(feePayment !=null){
			feePaymentEdit = FeePaymentHelper.getInstance().convertFeePaymentForEditBOToTO(feePayment);
		}
		return feePaymentEdit;
	}
	
	public boolean feePaymentDetailsUpdate(FeePaymentForm feePaymentForm) throws Exception{
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		
		boolean isUpdated = feePaymentTransaction.feePaymentDetailsUpdate(feePaymentForm);
		
		return isUpdated;
	}

	public Integer getFinancialYearId() throws Exception
	{
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		int financialYear = feePaymentTransaction.getFinancialYear();
		//feePaymentForm.setFinancialYearId(String.valueOf(financialYear));
		return financialYear;
		
	}
	
	public List<FeeVoucher> getFeeVoucherList(Integer financialYearId)throws Exception
	{
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		List<FeeVoucher> feeVoucherList=feePaymentTransaction.getFeevoucherList(financialYearId);
		return feeVoucherList;
	}

	public long getNoOfChallansForcourse(FeePaymentForm feePaymentForm) throws Exception{
		String query=FeePaymentHelper.getInstance().getQueryForNoOfChallanForCourse(feePaymentForm);
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		return feePaymentTransaction.getNoOfChallansForcourse(query);
	}	
	/**
	 * 
	 * @param studentName
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public List<FeeStudentDetailTO> getStudentList(String studentName, String year)throws Exception
	{
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		List<Student> studentList =feePaymentTransaction.getApplicantDetailsByName(studentName, year);
		List<FeeStudentDetailTO> feeStudentList = FeePaymentHelper.getInstance().studentDetailstoTO(studentList);
		return feeStudentList;
	}
	
	public List<FeeStudentDetailTO> getStudentListByClass(FeePaymentForm feePayForm)throws Exception
	{
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		List<Integer> detainedOrDiscontinuedStudents = ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();

		List<Student> studentList =feePaymentTransaction.getStudentsByClassName(feePayForm, detainedOrDiscontinuedStudents);
		List<FeeStudentDetailTO> feeStudentList = FeePaymentHelper.getInstance().studentDetailstoTO(studentList);
		return feeStudentList;
	}

	public FeePayment getFeeDetailsForApplication(FeePaymentForm feePaymentForm) throws Exception {
		IFeePaymentTransaction feePaymentTransaction = FeePaymentTransactionImpl.getInstance();
		FeePayment payment = feePaymentTransaction.getPaymentDetails(feePaymentForm);
		return payment;
	}
}
