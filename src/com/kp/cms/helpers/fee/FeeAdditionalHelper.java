package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeAdditionalAccountAssignment;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.forms.fee.FeeAdditionalForm;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeAdditionalAccountAssignmentTO;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;
/**
 * 
 * Date 20/jan/2009
 * This is an helper class for FeeAssingment
 */
public class FeeAdditionalHelper {
	private static final Log log = LogFactory.getLog(FeeAdditionalHelper.class);
	private static FeeAdditionalHelper feeAssignmentHelper= null;
	public static FeeAdditionalHelper getInstance() {
		      if(feeAssignmentHelper == null) {
		    	  feeAssignmentHelper = new FeeAdditionalHelper();
		    	  return feeAssignmentHelper;
		      }
	return feeAssignmentHelper;
	}
		
	/**
	 * 
	 * @param feeAdditionalForm
	 * @return
	 * @throws Exception
	 */
	public FeeAdditional copyFormDataToBo(FeeAdditionalForm feeAdditionalForm) throws Exception {
		log.debug("Helper : Entering copyFormDataToBo ");
		FeeAdditional feeAdditional = new FeeAdditional();
		// setting creted by,date and modified by,date.
			
		feeAdditional.setCreatedDate(new Date());
		feeAdditional.setLastModifiedDate(new Date());
					
		FeeGroup feeGroup = new FeeGroup();
		feeGroup.setId(Integer.parseInt(feeAdditionalForm.getFeeGroupId()));
		feeAdditional.setFeeGroup(feeGroup);
		
		
		feeAdditional.setIsActive(true);
		
		feeAdditional.setCreatedBy(feeAdditionalForm.getUserId());
		feeAdditional.setCreatedDate(new Date());
		feeAdditional.setModifiedBy(feeAdditionalForm.getUserId());
		feeAdditional.setLastModifiedDate(new Date());
		
		Set<FeeAdditionalAccountAssignment> feeAccountAssignments = new HashSet<FeeAdditionalAccountAssignment>();
		if(feeAdditionalForm.getFeeAssignmentList() !=null && feeAdditionalForm.getFeeAssignmentList().size() != 0) {
			Iterator<FeeAdditionalAccountAssignmentTO> itr = feeAdditionalForm.getFeeAssignmentList().iterator();
			FeeAdditionalAccountAssignment feeAdditionalAccountAssignment;
			FeeAccount feeAccount;
			FeeHeading feeHeading;
			FeeAdditionalAccountAssignmentTO feeAdditionalAccountAssignmentTO;
			while(itr.hasNext()) {
				feeAdditionalAccountAssignment = new FeeAdditionalAccountAssignment();
				feeAccount = new FeeAccount();
				feeHeading = new FeeHeading();
				
				feeAdditionalAccountAssignmentTO = itr.next();
				
				feeAdditionalAccountAssignment.setId(feeAdditionalAccountAssignmentTO.getId());
				
				feeAccount.setId(feeAdditionalAccountAssignmentTO.getFeeAccountId());
				feeAdditionalAccountAssignment.setFeeAccount(feeAccount);
				
				feeHeading.setId(feeAdditionalAccountAssignmentTO.getFeeHeadingId());
				feeAdditionalAccountAssignment.setFeeHeading(feeHeading);
				
				feeAdditionalAccountAssignment.setAmount(new BigDecimal(feeAdditionalAccountAssignmentTO.getAmount()));
				
				feeAdditionalAccountAssignment.setCreatedDate(new Date());
				feeAdditionalAccountAssignment.setLastModifiedDate(new Date());
				
				feeAdditionalAccountAssignment.setIsActive(true);
					
				feeAdditionalAccountAssignment.setCreatedBy(feeAdditionalForm.getUserId());
				feeAdditionalAccountAssignment.setModifiedBy(feeAdditionalForm.getUserId());
				feeAdditionalAccountAssignment.setCreatedDate(new Date());
				feeAdditionalAccountAssignment.setLastModifiedDate(new Date());
	
				
				feeAccountAssignments.add(feeAdditionalAccountAssignment);
			}
		}
		feeAdditional.setFeeAdditionalAccountAssignments(feeAccountAssignments);
		log.debug("Helper : Leaving copyFormDataToBo ");
	return feeAdditional;
	}
		
	/**
	 * 
	 * @param fee
	 * @return
	 */
	public FeeAdditionalTO copyFeeAdditionalToFeeAdditionalTO(FeeAdditional feeAdditional) {
		log.debug("Helper : Leaving copyFeeAdditionalToFeeAdditionalTO ");	
		FeeGroupTO feeGroupTo = new FeeGroupTO();
		FeeAdditionalTO feeAdditionalTO = new FeeAdditionalTO();
		feeAdditionalTO.setId(feeAdditional.getId());
		feeGroupTo.setName(feeAdditional.getFeeGroup().getName());
		feeGroupTo.setId(feeAdditional.getFeeGroup().getId());
		feeAdditionalTO.setFeeGroupTO(feeGroupTo);
		log.debug("Helper : Enter copyFeeAdditionalToFeeAdditionalTO ");	
	return feeAdditionalTO;
	}
		
		
	/**
	 * 
	 * @param feeAssignments
	 * @return
	 * @throws Exception
	 */
	public List<FeeAdditionalTO> copyFeeAdditionalBosToTos(List<FeeAdditional> feeAssignments) throws Exception{
		log.debug("Helper : Entering copyFeeAdditionalBosToTos ");
		List<FeeAdditionalTO> feeAssignmentToList = new ArrayList<FeeAdditionalTO>();
		Iterator<FeeAdditional> itr = feeAssignments.iterator();
		FeeAdditional feeAdditional;
		FeeAdditionalTO feeAdditionalTo;
		FeeGroupTO feeGroupTo;
		while(itr.hasNext()) {
			feeAdditional = itr.next();
			feeAdditionalTo = new FeeAdditionalTO();
			feeGroupTo = new FeeGroupTO();
			feeAdditionalTo.setId(feeAdditional.getId());
			feeGroupTo.setName(feeAdditional.getFeeGroup().getName());
			feeAdditionalTo.setFeeGroupTO(feeGroupTo);
			
			feeAssignmentToList.add(feeAdditionalTo);
		}
		log.debug("Helper : Leaving copyFeeAdditionalBosToTos ");
	return feeAssignmentToList;
	}
		
	/**
	 * 
	 * @param feeAssignmentList
	 * @return
	 */
	public List<FeeAdditionalAccountAssignmentTO> copyFeeAssignmentBosToTo(Set<FeeAdditionalAccountAssignment> feeAssignmentSet) {
		log.debug("Helper : Entering copyFeeAssignmentBosToTo ");
		List<FeeAdditionalAccountAssignmentTO> feeAssignmentList = new ArrayList<FeeAdditionalAccountAssignmentTO>();
		Iterator<FeeAdditionalAccountAssignment> itr = feeAssignmentSet.iterator();
		FeeAdditionalAccountAssignment feeAccountAssignment;
		FeeAdditionalAccountAssignmentTO feeAdditionalAccountAssignmentTO;
		FeeHeadingTO feeHeadingTo;
		FeeAccountTO feeAccountTo;
		while(itr.hasNext()) {
			
			feeAdditionalAccountAssignmentTO = new FeeAdditionalAccountAssignmentTO();
			feeHeadingTo = new FeeHeadingTO();
			feeAccountTo = new FeeAccountTO();
			
			feeAccountAssignment = itr.next();
			if(feeAccountAssignment.getFeeHeading().getIsActive() && feeAccountAssignment.getFeeAccount().getIsActive())
			{	
				feeAdditionalAccountAssignmentTO.setId(feeAccountAssignment.getId());
				// setting applicable TO
				feeHeadingTo.setId(feeAccountAssignment.getFeeHeading().getId());
				feeAdditionalAccountAssignmentTO.setFeeHeadingTo(feeHeadingTo);
	          
	            // setting AccountTo 
	            feeAccountTo.setId(feeAccountAssignment.getFeeAccount().getId());
	            feeAdditionalAccountAssignmentTO.setFeeAccountTo(feeAccountTo);
	            
	            // setting amount.
	            feeAdditionalAccountAssignmentTO.setAmount(feeAccountAssignment.getAmount().doubleValue());
				
	            feeAssignmentList.add(feeAdditionalAccountAssignmentTO);
			}    
		}
		log.debug("Helper : Leaving copyFeeAssignmentBosToTo ");
	return feeAssignmentList;
	}
		
	/**
	 * 
	 * @param feeTo
	 * @param feeAdditionalForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkForDuplicateWhileUpdate(FeeAdditionalTO feeAdditionalTO,FeeAdditionalForm feeAssignmentForm) throws Exception {
		if(feeAdditionalTO.getFeeGroupTO().getId() == Integer.parseInt(feeAssignmentForm.getFeeGroupId()))
		{
			return false;
		}
		return true;	
	}
}
