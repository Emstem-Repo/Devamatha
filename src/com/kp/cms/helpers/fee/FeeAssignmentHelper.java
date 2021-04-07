package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.forms.fee.SemesterWiseCourseEntryForm;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.fee.FeeAccountAssignmentTO;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeCategoryTo;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;
import com.kp.cms.to.fee.FeeTO;
import com.kp.cms.transactions.fee.IFeeAccountTransaction;
import com.kp.cms.transactionsimpl.fee.FeeAccountTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
/**
 * 
 * Date 20/jan/2009
 * This is an helper class for FeeAssingment
 */
public class FeeAssignmentHelper {
	private static final Log log = LogFactory.getLog(FeeAssignmentHelper.class);
	private static FeeAssignmentHelper feeAssignmentHelper= null;
	public static FeeAssignmentHelper getInstance() {
	      if(feeAssignmentHelper == null) {
	    	  feeAssignmentHelper = new FeeAssignmentHelper();
	    	  return feeAssignmentHelper;
	      }
	      return feeAssignmentHelper;
	}
	
	public Fee copyFormDataToBo(FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Helper : Entering copyFormDataToBo ");
		Fee fee = new Fee();
		// setting academic year
		fee.setAcademicYear(Integer.parseInt(feeAssignmentForm.getAcademicYear()));
		// setting creted by,date and modified by,date.
		
		fee.setCreatedDate(new Date());
		fee.setLastModifiedDate(new Date());
		
		ProgramType programType = new ProgramType();
		programType.setId(Integer.parseInt(feeAssignmentForm.getProgramTypeId()));
		fee.setProgramType(programType);
		
		Program program = new Program();
		program.setId(Integer.parseInt(feeAssignmentForm.getProgramId()));
		fee.setProgram(program);
		
		Course course = new Course();
		course.setId(Integer.parseInt(feeAssignmentForm.getCourseId()));
		fee.setCourse(course);
		
		fee.setSemesterNo(Integer.parseInt(feeAssignmentForm.getSemister()));
		fee.setIsActive(true);
		fee.setAidedUnaided(feeAssignmentForm.getAidedUnAided());
		fee.setCreatedBy(feeAssignmentForm.getUserId());
		if(feeAssignmentForm.getSem() != null && !feeAssignmentForm.getSem().isEmpty()){
		fee.setCourseSemNo(feeAssignmentForm.getSem());
		}
		if(feeAssignmentForm.getSem() != null && !feeAssignmentForm.getSem().isEmpty()){
			fee.setIsCourseSemesterWise(true);
		}else {
			fee.setIsCourseSemesterWise(false);
		}
		fee.setCreatedDate(new Date());
		fee.setModifiedBy(feeAssignmentForm.getUserId());
		fee.setLastModifiedDate(new Date());
		
		Set<FeeAccountAssignment> feeAccountAssignments = new HashSet<FeeAccountAssignment>();
		if(feeAssignmentForm.getFeeAssignmentList() !=null && feeAssignmentForm.getFeeAssignmentList().size() != 0) {
		Iterator<FeeAccountAssignmentTO> itr = feeAssignmentForm.getFeeAssignmentList().iterator();
		FeeAccountAssignment feeAccountAssignment;
		FeeAccount feeAccount;
		FeeHeading feeHeading;
		FeeCategory feeCategory;
		FeeAccountAssignmentTO FeeAccountAssignmentTo;
		
		Currency mainCurrency;
		Currency casteCurrency;
		Currency ligCurrency;
		int i=0;
		while(itr.hasNext()) {
			i++;
			feeAccountAssignment = new FeeAccountAssignment();
			feeAccount = new FeeAccount();
			feeHeading = new FeeHeading();
			feeCategory = new FeeCategory();
			mainCurrency= new Currency();
			casteCurrency= new Currency();
			ligCurrency= new Currency();
			
			
			FeeAccountAssignmentTo = itr.next();
			
			feeAccountAssignment.setId(FeeAccountAssignmentTo.getId());
			
			feeAccount.setId(FeeAccountAssignmentTo.getFeeAccountId());
			feeAccountAssignment.setFeeAccount(feeAccount);
			System.out.println("fee heading at "+i+" is "+FeeAccountAssignmentTo.getFeeHeadingId());
			if(FeeAccountAssignmentTo.getFeeHeadingId()> 0){
				feeHeading.setId(FeeAccountAssignmentTo.getFeeHeadingId());
				feeAccountAssignment.setFeeHeading(feeHeading);
			}
			else{
				System.out.println("sdfsdfskjdfiuwstdfiutsiudtfuis");
			}
			
			
			feeCategory.setId(Integer.parseInt(feeAssignmentForm.getFeeCategory()));
			feeAccountAssignment.setFeeCategory(feeCategory);

			feeAccountAssignment.setAmount(new BigDecimal(FeeAccountAssignmentTo.getAmount()));
			
			// add  the currency, caste and lig amount
			if(FeeAccountAssignmentTo.getCurrencyId()!=null 
					&& ! StringUtils.isEmpty(FeeAccountAssignmentTo.getCurrencyId()) 
					&& StringUtils.isNumeric(FeeAccountAssignmentTo.getCurrencyId())){
				mainCurrency.setId(Integer.parseInt(FeeAccountAssignmentTo.getCurrencyId()));
				feeAccountAssignment.setCurrencyByCurrencyId(mainCurrency);
			}
			
			if(FeeAccountAssignmentTo.getCasteCurrencyId()!=null 
					&& ! StringUtils.isEmpty(FeeAccountAssignmentTo.getCasteCurrencyId()) 
					&& StringUtils.isNumeric(FeeAccountAssignmentTo.getCasteCurrencyId())){
				casteCurrency.setId(Integer.parseInt(FeeAccountAssignmentTo.getCasteCurrencyId()));
				feeAccountAssignment.setCurrencyByCasteCurrencyId(casteCurrency);
			}
			
			if(FeeAccountAssignmentTo.getLigCurrencyId()!=null 
					&& ! StringUtils.isEmpty(FeeAccountAssignmentTo.getLigCurrencyId()) 
					&& StringUtils.isNumeric(FeeAccountAssignmentTo.getLigCurrencyId())){
				ligCurrency.setId(Integer.parseInt(FeeAccountAssignmentTo.getLigCurrencyId()));
				feeAccountAssignment.setCurrencyByLigCurrencyId(ligCurrency);
			}
			
			if(FeeAccountAssignmentTo.getCasteAmount()!=null 
					&& ! StringUtils.isEmpty(FeeAccountAssignmentTo.getCasteAmount()) 
					&& CommonUtil.isValidDecimal(FeeAccountAssignmentTo.getCasteAmount())){
				// set the caste amount, as big decimal
				feeAccountAssignment.setCasteAmount(new BigDecimal(FeeAccountAssignmentTo.getCasteAmount()));
			}
			
			if(FeeAccountAssignmentTo.getLigAmount()!=null 
					&& ! StringUtils.isEmpty(FeeAccountAssignmentTo.getLigAmount()) 
					&& CommonUtil.isValidDecimal(FeeAccountAssignmentTo.getLigAmount())){
				// set the lig amount, as big decimal
				feeAccountAssignment.setLigAmount(new BigDecimal(FeeAccountAssignmentTo.getLigAmount()));
			}
			
			feeAccountAssignment.setCreatedBy(feeAssignmentForm.getUserId());
			feeAccountAssignment.setModifiedBy(feeAssignmentForm.getUserId());
			feeAccountAssignment.setCreatedDate(new Date());
			feeAccountAssignment.setLastModifiedData(new Date());
							
			feeAccountAssignments.add(feeAccountAssignment);
		}
		}
		fee.setFeeAccountAssignments(feeAccountAssignments);
		log.debug("Helper : Leaving copyFormDataToBo ");
	return fee;
	}
	
	/**
	 * 
	 * @param fee
	 * @return
	 */
	public FeeTO copyFeeToFeeTO(Fee fee) {
		log.debug("Helper : Entering copyFeeToFeeTO ");
		ProgramTypeTO programTypeTo = new ProgramTypeTO();
		CourseTO courseTo = new CourseTO();
		ProgramTO programTo = new ProgramTO();
		FeeTO feeTo = new FeeTO();
		
		feeTo.setId(fee.getId());
		// setting programType to FeeTo
		programTypeTo.setProgramTypeName(fee.getProgramType().getName());
		programTypeTo.setProgramTypeId(fee.getProgramType().getId());
		feeTo.setProgramTypeTo(programTypeTo);
		
		// setting program name to FeeTo
		programTo.setName(fee.getProgram().getName());
		programTo.setId(fee.getProgram().getId());
		feeTo.setProgramTo(programTo);
		// setting course code to feeTo
		courseTo.setName(fee.getCourse().getName());
		courseTo.setId(fee.getCourse().getId());
		feeTo.setCourseTo(courseTo);
		// setting subject Group to feeTO
		feeTo.setSemister(fee.getSemesterNo());
		feeTo.setAidedUnaided(fee.getAidedUnaided());
		feeTo.setAcademicYear(fee.getAcademicYear());
		log.debug("Helper : Leaving copyFeeToFeeTO ");
	return feeTo;
	}
	
	
	/**
	 * 
	 * @param feeAssignments
	 * @return
	 * @throws Exception
	 */
	public List<FeeTO> copyFeeBosToTos(List<Fee> feeAssignments) throws Exception{
		log.debug("Helper : Entering copyFeeBosToTos ");
		List<FeeTO> feeAssignmentToList = new ArrayList<FeeTO>();
		Iterator<Fee> itr = feeAssignments.iterator();
		Fee fee;
		FeeTO feeTo;
		ProgramTypeTO programTypeTo;
		CourseTO courseTo;
		ProgramTO programTo;
		while(itr.hasNext()) {
			fee = itr.next();
			feeTo = new FeeTO();
			programTypeTo = new ProgramTypeTO();
			courseTo = new CourseTO();
			programTo = new ProgramTO();
			
			feeTo.setId(fee.getId());
			// setting programType to FeeTo
			programTypeTo.setProgramTypeName(fee.getProgramType().getName());
			feeTo.setProgramTypeTo(programTypeTo);
			
			// setting program name to FeeTo
			programTo.setName(fee.getProgram().getName());
			feeTo.setProgramTo(programTo);
			// setting course code to feeTo
			courseTo.setCode(fee.getCourse().getName());
			feeTo.setCourseTo(courseTo);
			feeTo.setSemister(fee.getSemesterNo());
			
			String year1 = String.valueOf(fee.getAcademicYear() + 1);

			feeTo.setYear(fee.getAcademicYear()+"-"+year1.substring(2));
			feeTo.setAidedUnaided(fee.getAidedUnaided());
			// set admittedThrough or feeCategory
			Set<FeeAccountAssignment> feeAccountAssignments = fee.getFeeAccountAssignments();
			Iterator<FeeAccountAssignment> feeAccountAssignmentsItr = feeAccountAssignments.iterator();
			while(feeAccountAssignmentsItr.hasNext())
			{
				FeeAccountAssignment bo = feeAccountAssignmentsItr.next();
				//feeTo.setAdmittedThrough(bo.getAdmittedThrough().getName());
				feeTo.setFeeCategory(bo.getFeeCategory().getName());
				break;
			}
			feeAssignmentToList.add(feeTo);
		}
		log.debug("Helper : Leaving copyFeeBosToTos ");
	return feeAssignmentToList;
	}
	
	/**
	 * 
	 * @param feeAssignmentList
	 * @return
	 */
	public List<FeeAccountAssignmentTO> copyFeeAssignmentBosToTo(Set<FeeAccountAssignment> feeAssignmentSet) throws Exception {
		log.debug("Helper : Entering copyFeeAssignmentBosToTo ");
		List<FeeAccountAssignmentTO> feeAssignmentList = new ArrayList<FeeAccountAssignmentTO>();
		//getting all active fee accounts
		IFeeAccountTransaction acTxn= new FeeAccountTransactionImpl();
		List<Integer> activeAcounts=acTxn.getAllFeeAccountIds();
		Iterator<FeeAccountAssignment> itr = feeAssignmentSet.iterator();
		FeeAccountAssignment feeAccountAssignment;
		FeeAccountAssignmentTO feeAccountAssignmentTo;
		FeeHeadingTO feeHeadingTo;
		FeeAccountTO feeAccountTo;
		FeeCategoryTo feeCategoryTo;
		while(itr.hasNext()) {
			
			feeAccountAssignmentTo = new FeeAccountAssignmentTO();
			feeHeadingTo = new FeeHeadingTO();
			feeAccountTo = new FeeAccountTO();
			feeCategoryTo = new FeeCategoryTo();
			
			feeAccountAssignment = itr.next();
			if(!feeAccountAssignment.getFeeHeading().getFeeGroup().getIsOptional() && feeAccountAssignment.getFeeHeading().getFeeGroup().getIsActive())
			{	
				//checking if account is active or not
				if(activeAcounts.contains(feeAccountAssignment.getFeeAccount().getId())){
				feeAccountAssignmentTo.setId(feeAccountAssignment.getId());
				
				// setting applicable TO
				feeHeadingTo.setId(feeAccountAssignment.getFeeHeading().getId());
				feeHeadingTo.setName(feeAccountAssignment.getFeeHeading().getName());
				feeAccountAssignmentTo.setFeeHeadingTo(feeHeadingTo);
	            
				// setting fee category TO
				feeCategoryTo.setFeeCategoryId(feeAccountAssignment.getFeeCategory().getId());
				feeCategoryTo.setFeeCategoryName(feeAccountAssignment.getFeeCategory().getName());
	            feeAccountAssignmentTo.setFeeCategoryTo(feeCategoryTo);
	            
	            // setting AccountTo 
	            feeAccountTo.setId(feeAccountAssignment.getFeeAccount().getId());
	            feeAccountTo.setName(feeAccountAssignment.getFeeAccount().getName());
	            feeAccountAssignmentTo.setFeeAccountTo(feeAccountTo);
	            
	            // setting amount.
	            feeAccountAssignmentTo.setAmount(feeAccountAssignment.getAmount().doubleValue());
	            if(feeAccountAssignment.getCasteAmount()!=null)
	            	 feeAccountAssignmentTo.setCasteAmount(String.valueOf(feeAccountAssignment.getCasteAmount().doubleValue()));
	            if(feeAccountAssignment.getLigAmount()!=null)
	           	 feeAccountAssignmentTo.setLigAmount(String.valueOf(feeAccountAssignment.getLigAmount().doubleValue()));
	            
	            if(feeAccountAssignment.getCurrencyByCurrencyId()!=null){
	             	 feeAccountAssignmentTo.setCurrencyId(String.valueOf(feeAccountAssignment.getCurrencyByCurrencyId().getId()));
	            	 feeAccountAssignmentTo.setCurrencyName(feeAccountAssignment.getCurrencyByCurrencyId().getCurrencyCode());
	            }
           
	           if(feeAccountAssignment.getCurrencyByLigCurrencyId()!=null){
	            	 feeAccountAssignmentTo.setLigCurrencyId(String.valueOf(feeAccountAssignment.getCurrencyByLigCurrencyId().getId()));
	            	feeAccountAssignmentTo.setLigCurrencyName(feeAccountAssignment.getCurrencyByLigCurrencyId().getCurrencyCode());
	           }
	           
	           if(feeAccountAssignment.getCurrencyByCasteCurrencyId()!=null){
	           	 feeAccountAssignmentTo.setCasteCurrencyId(String.valueOf(feeAccountAssignment.getCurrencyByCasteCurrencyId().getId()));
	           	 feeAccountAssignmentTo.setCasteCurrencyName(feeAccountAssignment.getCurrencyByCasteCurrencyId().getCurrencyCode());
	           }
				
	            feeAssignmentList.add(feeAccountAssignmentTo);
			}    
		}
		}
		log.debug("Helper : Leaving copyFeeAssignmentBosToTo ");
	return feeAssignmentList;
	}
	
	/**
	 * 
	 * @param feeTo
	 * @param feeAssignmentForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkForDuplicateWhileUpdate(FeeTO feeTo,FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Helper : Entering checkForDuplicateWhileUpdate ");
		if(feeTo.getProgramTypeTo().getProgramTypeId() == Integer.parseInt(feeAssignmentForm.getProgramTypeId())
		   && feeTo.getProgramTo().getId() == Integer.parseInt(feeAssignmentForm.getProgramId())
		   && feeTo.getCourseTo().getId() == Integer.parseInt(feeAssignmentForm.getCourseId())  
		   && feeTo.getAcademicYear().toString().equals(feeAssignmentForm.getAcademicYear())
		   && feeTo.getSemister().toString().equals(feeAssignmentForm.getSemister())
		  ) {
			return false;
		}
		log.debug("Helper : Leaving checkForDuplicateWhileUpdate ");
	return true;	
	}

	public SemesterWiseCourseBO convertFormDataToBo(
			SemesterWiseCourseEntryForm semesterWiseCourseEntryForm, SemesterWiseCourseBO bo) throws Exception{
		Fee fee = new Fee();
		SemesterWiseCourseBO bo2 = new SemesterWiseCourseBO();
		if(bo!= null){
			if(bo.getId() != 0){
			bo2.setId(bo.getId());
			}
		}
		Course course = new Course();
		course.setId(Integer.parseInt(semesterWiseCourseEntryForm.getCourseId()));
		bo2.setCourse(course);
		bo2.setIsSemesterWise(semesterWiseCourseEntryForm.getIsSemesterWise());
		bo2.setCreatedBy(semesterWiseCourseEntryForm.getUserId());
		bo2.setCreatedDate(new Date());
		bo2.setModifiedBy(semesterWiseCourseEntryForm.getUserId());
		bo2.setLastModifiedDate(new Date());
		bo2.setIsActive(true);
		
		return bo2;
	}

	public boolean checkForDuplicateWhileUpdateForSemesterWise(FeeTO feeTo,
			FeeAssignmentForm feeAssignmentForm)  throws Exception{
		log.debug("Helper : Entering checkForDuplicateWhileUpdate ");
		if(feeTo.getProgramTypeTo().getProgramTypeId() == Integer.parseInt(feeAssignmentForm.getProgramTypeId())
		   && feeTo.getProgramTo().getId() == Integer.parseInt(feeAssignmentForm.getProgramId())
		   && feeTo.getCourseTo().getId() == Integer.parseInt(feeAssignmentForm.getCourseId())  
		   && feeTo.getAcademicYear().toString().equals(feeAssignmentForm.getAcademicYear())
		   && feeTo.getSemister().toString().equals(feeAssignmentForm.getSemister())
		  ) {
			return false;
		}
		log.debug("Helper : Leaving checkForDuplicateWhileUpdate ");
	return true;
	}

}
