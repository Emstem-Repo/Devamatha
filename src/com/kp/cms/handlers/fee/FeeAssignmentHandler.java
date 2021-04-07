package com.kp.cms.handlers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.forms.fee.SemesterWiseCourseEntryForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.helpers.fee.FeeAssignmentHelper;
import com.kp.cms.to.fee.FeeAccountAssignmentTO;
import com.kp.cms.to.fee.FeeTO;
import com.kp.cms.transactions.fee.IFeeDivisionTransaction;
import com.kp.cms.transactions.fee.IFeeGroupTransaction;
import com.kp.cms.transactions.fee.IFeeTransaction;
import com.kp.cms.transactionsimpl.fee.FeeDivisionTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeeGroupTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeeTransactionTmpl;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * @author microhard
 * Date 17/jan/2009
 * Handler class for FEE and Fee Account Assignment.
 */
public class FeeAssignmentHandler {
	private static final Log log = LogFactory.getLog(FeeAssignmentHandler.class);
	private static FeeAssignmentHandler feeAssignmentHandler= null;
	public static FeeAssignmentHandler getInstance() {
	      if(feeAssignmentHandler == null) {
	    	  feeAssignmentHandler = new FeeAssignmentHandler();
	    	  return feeAssignmentHandler;
	      }
	      return feeAssignmentHandler;
	}
	
	/**
	 * 
	 * @return List of FeeAssingment TO's object.
	 * @throws Exception
	 */
	public List<FeeTO> getAllFees() throws Exception{
		log.debug("Handler : Entering getAllFees ");
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
        List<Fee> feeAssignments = feeAssignmentTransaction.getAllFees();
		List<FeeTO> feeAssignmentsTOs = FeeAssignmentHelper.getInstance().copyFeeBosToTos(feeAssignments);
		log.debug("Handler : Leaving getAllFees ");
		return feeAssignmentsTOs;
	}
		
	/**
	 * 
	 * @param feeAssignmentForm
	 * @return Map containing <key,value> of Addmitted through records <ID,name>
	 *         Contains all Admitted through fetched from DB.
	 * @throws Exception
	 */
	public Map<Integer,String> getFeeAdmittedThrough() throws Exception{
		Map<Integer,String> admittedThroughMap = AdmittedThroughHandler.getInstance().getAdmittedThroughMap();
		return admittedThroughMap;
	}	
	
	
	/**
	 * 
	 * @param feeAssignmentForm
	 * @return boolean true is successfully added.
	 * @throws Exception
	 */
	public boolean saveFeeAssignmentAccount(FeeAssignmentForm feeAssignmentForm) throws ConstraintViolationException,Exception {
		log.debug("Handler : Entering saveFeeAssignmentAccount ");
		Fee fee = FeeAssignmentHelper.getInstance().copyFormDataToBo(feeAssignmentForm);
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		boolean isAdded = false;
		isAdded = feeAssignmentTransaction.addFeeAssignment(fee);
		log.debug("Handler : Leaving saveFeeAssignmentAccount ");
	return isAdded;
	}
	
	/**
	 * 
	 * @param feeAssignmentForm
	 * @return boolean true is successfully added.
	 *         This add the data to FEE and FeeAccountAssignment table.
	 * @throws Exception
	 */
	public boolean updateFeeAssignmentAccount(FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Handler : Entering updateFeeAssignmentAccount ");
		Fee fee = FeeAssignmentHelper.getInstance().copyFormDataToBo(feeAssignmentForm);
		fee.setId(Integer.parseInt(feeAssignmentForm.getId()));
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		boolean isUpdated = false;
		isUpdated = feeAssignmentTransaction.updateFeeAssignment(fee);
		log.debug("Handler : Leaving updateFeeAssignmentAccount ");
	return isUpdated;
	}
	
	/**
	 * 
	 * @param feeAssignmentForm
	 * @return boolean based on deleted status.
	 *         This delete the FEE and FEEACCOUNTASSIGNMENT detail.
	 * @throws Exception
	 */
	public boolean deleteFeeAssignment(FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Handler : Entering deleteFeeAssignment ");
		Fee fee = new Fee();
		fee.setId(Integer.parseInt(feeAssignmentForm.getId())); 
		fee.setModifiedBy(feeAssignmentForm.getUserId());
		fee.setLastModifiedDate(new Date());
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		boolean isDeleted = false;
		isDeleted = feeAssignmentTransaction.deleteFeeAssignment(fee);
		log.debug("Handler : Leaving deleteFeeAssignment ");
	return isDeleted;
	}
	
	/**
	 * 
	 * @param feeAssignmentForm
	 * @return boolean based on deleted status.
	 *         This delete the FEE and FEEACCOUNTASSIGNMENT detail.
	 * @throws Exception
	 */
	public boolean activateFeeAssignment(FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Handler : Entering activateFeeAssignment ");
		Fee fee = new Fee();
		fee.setId(Integer.parseInt(feeAssignmentForm.getId())); 
		fee.setModifiedBy(feeAssignmentForm.getUserId());
		fee.setLastModifiedDate(new Date());
		
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		boolean isActivated = false;
		isActivated = feeAssignmentTransaction.activateFeeAssignment(fee);
		log.debug("Handler : Leaving activateFeeAssignment ");
	return isActivated;
	}
	
	/**
	 * 
	 * @return sets the data back to form.
	 *         Will get FEE and FEEACCOUNTASSIGNMENT  data from DB and setting to form.
	 * @throws Exception
	 */
	public void viewFeeAssignment(FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Handler : Entering viewFeeAssignment ");
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
        Fee fee = feeAssignmentTransaction.getFeeAssignmentById(Integer.parseInt(feeAssignmentForm.getId()));
				
		Map<Integer,String> feeAccountsMap = getFeeAccounts();
		List<FeeGroup> feeGroupList=getFeeGroup();
		Map<String,Map<Integer,String>> feeHeadingsMap = getFeeHeadingsByGroup(feeGroupList);
		Map<Integer,String> admitedThroughMap = FeeAssignmentHandler.getInstance().getFeeAdmittedThrough();
		
	    List<FeeAccountAssignmentTO> feeAssignmentList = FeeAssignmentHelper.getInstance().copyFeeAssignmentBosToTo(fee.getFeeAccountAssignments());
	    feeAssignmentForm.setFeeAssignmentList(feeAssignmentList);
		
		feeAssignmentForm.setFeeAssignmentList(feeAssignmentList);
		feeAssignmentForm.setFeeAccountsMap(feeAccountsMap);
		feeAssignmentForm.setFeeHeadingsMap(feeHeadingsMap);
	    feeAssignmentForm.setAdmitedThroughMap(admitedThroughMap);
	    feeAssignmentForm.setProgramName(fee.getProgram().getName());
	    feeAssignmentForm.setProgramTypeName(fee.getProgramType().getName());
	    feeAssignmentForm.setCourseName(fee.getCourse().getName());
	    feeAssignmentForm.setAcademicYear(fee.getAcademicYear().toString());
	    feeAssignmentForm.setSemister(fee.getSemesterNo().toString());
	    feeAssignmentForm.setAidedUnAided(fee.getAidedUnaided());
	    if(fee.getCourseSemNo() != null && !fee.getCourseSemNo().isEmpty()){
	    feeAssignmentForm.setSem(fee.getCourseSemNo());
	    }
	    log.debug("Handler : Leaving viewFeeAssignment ");
	}
	
	/**
	 * This method load pre-necessary data while loading new fee account.
	 * @param feeAssignmentForm
	 * @throws Exception
	 */
	public void initAddFeeAssignmentAccount(FeeAssignmentForm feeAssignmentForm) throws ReActivateException,DuplicateException,Exception{
		log.debug("Handler : Entering initAddFeeAssignmentAccount ");
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		Fee duplicateFee = FeeAssignmentHelper.getInstance().copyFormDataToBo(feeAssignmentForm);
		boolean isPresent = FeeAssignmentHandler.getInstance().isSemesterWise(feeAssignmentForm);
		if(isPresent){
			duplicateFee = feeAssignmentTransaction.getFeeByCompositeKeysSemesterWise(duplicateFee,Integer.parseInt(feeAssignmentForm.getFeeCategory()));
		}else {
			duplicateFee = feeAssignmentTransaction.getFeeByCompositeKeys(duplicateFee,Integer.parseInt(feeAssignmentForm.getFeeCategory()));
		}
		if(duplicateFee != null && duplicateFee.getIsActive()){
			 isPresent = FeeAssignmentHandler.getInstance().isSemesterWise(feeAssignmentForm);
			 if(isPresent){
				 
			 }else {
				 throw new DuplicateException();
			 }
		} else if(duplicateFee != null && !duplicateFee.getIsActive()){
			feeAssignmentForm.setId(Integer.valueOf(duplicateFee.getId()).toString());
			throw new ReActivateException();
		}
		
		Map<Integer,String> feeAccountsMap = getFeeAccounts();
	    List<FeeGroup> feeGroupList=getFeeGroup();
		Map<String,Map<Integer,String>> feeHeadingsMap = getFeeHeadingsByGroup(feeGroupList);
		Map<Integer,String> feeCategoryMap = FeeAssignmentHandler.getInstance().getFeeCategories();
		List<FeeAccountAssignmentTO> feeAssignmentList = new ArrayList<FeeAccountAssignmentTO>();
		int feeHeadingsSize=0;
		Iterator it=feeHeadingsMap.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry pairs = (Map.Entry)it.next();
			 Map<Integer,String> feeHeadMap=(Map<Integer,String>)pairs.getValue();
			 feeHeadingsSize=feeHeadingsSize+feeHeadMap.size();
		 }	 
		
		//int count = feeAccountsMap.size()* feeHeadingsSize * admitedThroughMap.size();
			int count = feeAccountsMap.size()* feeHeadingsSize;
			FeeAccountAssignmentTO feeAccountAssignmentTo;
		// setting each feeAssignmentTO with default data.
		for(int i=0;i<count;i++){
			feeAccountAssignmentTo = new FeeAccountAssignmentTO(0.0,0,0,0,0,"","0.0","0.0","","",0);
			feeAssignmentList.add(feeAccountAssignmentTo);
		}
		//setting all data to form.
		feeAssignmentForm.setFeeAssignmentList(feeAssignmentList);
		feeAssignmentForm.setFeeAccountsMap(feeAccountsMap);
		feeAssignmentForm.setFeeHeadingsMap(feeHeadingsMap);
	    feeAssignmentForm.setFeeCategoryMap(feeCategoryMap);
	    log.debug("Handler : Leaving initAddFeeAssignmentAccount ");
	}
	
	/**
	 * This method load pre-necessary data while assigning new Fee account amounts means 2 page.
	 * @param feeAssignmentForm
	 * @throws Exception
	 */
	public void initEditFeeAssignmentAccount(FeeAssignmentForm feeAssignmentForm) throws ReActivateException,DuplicateException,Exception{
		log.debug("Handler : Entering initEditFeeAssignmentAccount ");
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		Fee newFee = FeeAssignmentHelper.getInstance().copyFormDataToBo(feeAssignmentForm);
		
 
		FeeTO feeTo = feeAssignmentForm.getFeeTO();
		boolean duplicateCheck = false;
		boolean isPresent = FeeAssignmentHandler.getInstance().isSemesterWise(feeAssignmentForm);
		/*if(isPresent){
			duplicateCheck = true;
		}else {*/
		 duplicateCheck = FeeAssignmentHelper.getInstance().checkForDuplicateWhileUpdate(feeTo,feeAssignmentForm);
		Fee duplicateFee = null;
		if(duplicateCheck) {
			if(isPresent){
				 duplicateFee = feeAssignmentTransaction.getFeeByCompositeKeysSemesterWise(newFee, Integer.parseInt(feeAssignmentForm.getFeeCategory()));
			}else {
				 duplicateFee = feeAssignmentTransaction.getFeeByCompositeKeys(newFee, Integer.parseInt(feeAssignmentForm.getFeeCategory()));
			}
			
			if(duplicateFee != null && duplicateFee.getIsActive()){
				throw new DuplicateException();
			} else if(duplicateFee != null && !duplicateFee.getIsActive()){
				feeAssignmentForm.setId(Integer.valueOf(duplicateFee.getId()).toString());
				throw new ReActivateException();
			}
		}
		
        List<FeeAccountAssignmentTO> feeAssignmentList = feeAssignmentForm.getFeeAssignmentList();
        		
		// following will have selected data selected from fee assignment
		Map<Integer,String> feeAccountsMap = getFeeAccounts();
		List<FeeGroup> feeGroupList=getFeeGroup();
		Map<String,Map<Integer,String>> feeHeadingsMap = getFeeHeadingsByGroup(feeGroupList);
		Map<Integer,String> feeCategoryMap = FeeAssignmentHandler.getInstance().getFeeCategories();

	    // here checking feeassignment account data fetched from database is less than
        // the user requested by selecting accounts, applicables * with admitted through
        // this logic useful when user has selected more accounts and applicable while updating.
        // this will need to more no of TO's in list. for those initializing with default value.
		int feeHeadingsSize=0;
		Iterator it=feeHeadingsMap.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry pairs = (Map.Entry)it.next();
			 Map<Integer,String> feeHeadMap=(Map<Integer,String>)pairs.getValue();
			 feeHeadingsSize=feeHeadingsSize+feeHeadMap.size();
		 }	 
		
		int count = feeAccountsMap.size()* feeHeadingsSize;
		int size = count - feeAssignmentList.size();
		FeeAccountAssignmentTO feeAccountAssignmentTo;
		if(count > feeAssignmentList.size()) {
			for(int i=0;i<size;i++){
				feeAccountAssignmentTo = new FeeAccountAssignmentTO(0.0,0,0,0,0,"","0.0","0.0","","",0);
				feeAssignmentList.add(feeAccountAssignmentTo);
			}
		}
		
		feeAssignmentForm.setFeeAssignmentList(feeAssignmentList);
		feeAssignmentForm.setFeeAccountsMap(feeAccountsMap);
		feeAssignmentForm.setFeeHeadingsMap(feeHeadingsMap);
	    feeAssignmentForm.setFeeCategoryMap(feeCategoryMap);
	  //  feeAssignmentForm.setFeeGroupMap(feeGroupMap);
	    log.debug("Handler : Leaving initEditFeeAssignmentAccount ");
	}
	
	/**
	 * 
	 * @param feeAssignmentForm
	 * @throws Exception
	 */
	public void editFeeAssignment(FeeAssignmentForm feeAssignmentForm) throws Exception {
		log.debug("Handler : Entering editFeeAssignment ");
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
        Fee fee = feeAssignmentTransaction.getFeeAssignmentById(Integer.parseInt(feeAssignmentForm.getId()));
		
        FeeTO feeTO = FeeAssignmentHelper.getInstance().copyFeeToFeeTO(fee);
        
        feeAssignmentForm.setId(Integer.valueOf(fee.getId()).toString());
        feeAssignmentForm.setProgramTypeId(Integer.valueOf(fee.getProgramType().getId()).toString());
        feeAssignmentForm.setProgramId(Integer.valueOf(fee.getProgram().getId()).toString());
        feeAssignmentForm.setCourseId(Integer.valueOf(fee.getCourse().getId()).toString());
        feeAssignmentForm.setAcademicYear(fee.getAcademicYear().toString());
        feeAssignmentForm.setSemister(fee.getSemesterNo().toString());
        feeAssignmentForm.setAidedUnAided(fee.getAidedUnaided().toString());
        feeAssignmentForm.setSem(fee.getCourseSemNo());
        Set<FeeAccountAssignment> feeAccountAssignments = fee.getFeeAccountAssignments();
		Iterator<FeeAccountAssignment> feeAccountAssignmentsItr = feeAccountAssignments.iterator();
		while(feeAccountAssignmentsItr.hasNext())
		{
			FeeAccountAssignment bo = feeAccountAssignmentsItr.next();
			feeAssignmentForm.setFeeCategory(String.valueOf(bo.getFeeCategory().getId()));
			break;
		}
        feeAssignmentForm.setFeeTO(feeTO);
        
        List<FeeAccountAssignmentTO> feeAssignmentList = FeeAssignmentHelper.getInstance().copyFeeAssignmentBosToTo(fee.getFeeAccountAssignments());
        feeAssignmentForm.setFeeAssignmentList(feeAssignmentList);
        log.debug("Handler : Leaving editFeeAssignment ");
	}
	
	public Map<String,Map<Integer,String>> getFeeHeadingsByGroup(List<FeeGroup> feeGroup) throws Exception {
		   log.info("call of getFeeHeadingsByGroup method in FeeAssignmentHandler class.");
		   Map<String,Map<Integer,String>> feeHeadingMap = new HashMap<String,Map<Integer,String>>();
		   for(FeeGroup group:feeGroup)
		   {	   
			   Iterator<FeeHeading> itr = group.getFeeHeadings().iterator();
			   FeeHeading feeHeading;
			   Map<Integer, String>feeHeadingMapTemp=new HashMap<Integer, String>();
			   while(itr.hasNext()) 
			   {
				   feeHeading = itr.next();
				   if(feeHeading.getIsActive() == true) 
				   {
					   feeHeadingMapTemp.put(Integer.valueOf(feeHeading.getId()), feeHeading.getName());
				   }
			   }
			   feeHeadingMap.put(group.getName(),feeHeadingMapTemp);
		   }
		   log.info("end of getFeeHeadingsByGroup method in FeeGroupHandler class.");
	       return feeHeadingMap;
 }
	
	public List<FeeGroup> getFeeGroup() throws Exception 
	{
		   log.info("call of getFeeHeadingsByGroup method in FeeAssignmentHandler class.");
		   IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		   List<FeeGroup> feeGroupList = feeAssignmentTransaction.getFeeGroup();
		   log.info("end of getFeeHeadingsByGroup method in FeeGroupHandler class.");
	       return feeGroupList;
	}
	
	public Map<Integer,String> getFeeAccounts() throws Exception 
	{
	   	  Map<Integer,String> feeAccountsMap = new HashMap<Integer,String>();
	   	  List<FeeAccount> feeAccountList = FeeAccountHandler.getInstance().getAllFeeAccounts();
		  Iterator<FeeAccount> itr = feeAccountList.iterator();
		  FeeAccount feeAccount;
		  while(itr.hasNext()) {
			   feeAccount = itr.next();
			   if(feeAccount.getIsActive() == true) {
				   feeAccountsMap.put(new Integer(feeAccount.getId()), feeAccount.getName());
			   }
		  } 
	      return feeAccountsMap;
 }
	
	public Map<Integer,String> getFeeCategories() throws Exception{
		Map<Integer,String> feeCategoryMap = FeeCategoryHandler.getInstance().getFeeCategoryMap();
		return feeCategoryMap;
	}

	public Map<Integer, String> getCourse(int year)  throws Exception{
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		Map<Integer, String> courseMap = feeAssignmentTransaction.getCourse();
		return courseMap;
	}

	public Fee getduplicateRecord(SemesterWiseCourseEntryForm semesterWiseCourseEntryForm)  throws Exception{
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		Fee fee = feeAssignmentTransaction.getFeeData(semesterWiseCourseEntryForm);
		return fee;
	}

	public Course getCourseObject(String courseId)  throws Exception {
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		Course course = feeAssignmentTransaction.getObject(courseId);
		return course;
	}

	public boolean saveFeeDetails(SemesterWiseCourseEntryForm semesterWiseCourseEntryForm, SemesterWiseCourseBO bo) throws Exception {
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		SemesterWiseCourseBO bo2 = FeeAssignmentHelper.getInstance().convertFormDataToBo(semesterWiseCourseEntryForm,bo);
		boolean isAdded = false;
		isAdded = feeAssignmentTransaction.saveDetails(bo2);
		return isAdded;
	}

	public boolean isSemesterWise(FeeAssignmentForm feeAssignmentForm) throws Exception {
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		SemesterWiseCourseBO bo = feeAssignmentTransaction.isSemesterWise(feeAssignmentForm);
		boolean isSemesterWise = false;
		if(bo != null){
		 isSemesterWise = true;
		}
		return isSemesterWise;
	}

	public SemesterWiseCourseBO getExistingRecord(
			SemesterWiseCourseEntryForm semesterWiseCourseEntryForm) throws Exception {
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		SemesterWiseCourseBO bo = feeAssignmentTransaction.getCourseObject(semesterWiseCourseEntryForm);
		return bo;
	}

	public Fee getRecord(FeeAssignmentForm feeAssignmentForm) throws Exception {
		IFeeTransaction feeAssignmentTransaction = FeeTransactionTmpl.getInstance();
		Fee fee = feeAssignmentTransaction.getExistingObject(feeAssignmentForm);
		return fee;
	}
	
}	
	

