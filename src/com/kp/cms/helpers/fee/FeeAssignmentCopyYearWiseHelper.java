package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.forms.fee.FeeAssignmentCopyForm;
import com.kp.cms.forms.fee.FeeAssignmentCopyYearWiseForm;

public class FeeAssignmentCopyYearWiseHelper {

	private FeeAssignmentCopyYearWiseHelper()
	{
		
	}
	
	private static FeeAssignmentCopyYearWiseHelper assignmentCopyHelper=null;
	public static FeeAssignmentCopyYearWiseHelper getInstance() 
	{
		if(assignmentCopyHelper==null)
		{
			assignmentCopyHelper=new FeeAssignmentCopyYearWiseHelper();
		}
		return assignmentCopyHelper;
	}
	public String getQueryToGetExistingFeesDefinition(FeeAssignmentCopyYearWiseForm assignmentCopyForm) 
	{
		String strQuery="from Fee f where f.isActive=1 and f.course.id="+assignmentCopyForm.getCourseId()+" and f.academicYear="+assignmentCopyForm.getFromYear()+" and f.semesterNo="+assignmentCopyForm.getSchemeNo();
		return strQuery;
	}
	public String getQueryToGetFeeDefinitionToBecopiedExists(FeeAssignmentCopyYearWiseForm assignmentCopyForm) 
	{
		String strQuery="from Fee f where f.isActive=1 and f.course.id="+assignmentCopyForm.getCourseId()+" and f.academicYear="+assignmentCopyForm.getToYear()+" and f.semesterNo="+assignmentCopyForm.getSchemeNo();
		return strQuery;
	}
	public List<Fee> getFeeListToBeCopied(List<Fee> existingFeeDefinitions,	FeeAssignmentCopyYearWiseForm assignmentCopyForm) 
	{
		List<Fee>listToBeCopied=new ArrayList<Fee>();
		for(Fee fee:existingFeeDefinitions)
		{
			Fee feeTobeCoipied=new Fee();
			feeTobeCoipied.setSemesterNo(1);
			feeTobeCoipied.setProgramType(fee.getProgramType());
			feeTobeCoipied.setFeeDivision(fee.getFeeDivision());
			feeTobeCoipied.setProgram(fee.getProgram());
			feeTobeCoipied.setCourse(fee.getCourse());
			feeTobeCoipied.setSubjectGroup(fee.getSubjectGroup());
			feeTobeCoipied.setAcademicYear(Integer.parseInt(assignmentCopyForm.getToYear()));
			feeTobeCoipied.setCreatedDate(new Date());
			feeTobeCoipied.setLastModifiedDate(new Date());
			feeTobeCoipied.setCreatedBy(assignmentCopyForm.getUserId());
			feeTobeCoipied.setModifiedBy(assignmentCopyForm.getUserId());
			feeTobeCoipied.setFeeGroup(fee.getFeeGroup());
			feeTobeCoipied.setSection(fee.getSection());
			feeTobeCoipied.setIsActive(true);
			feeTobeCoipied.setSemesterNo(fee.getSemesterNo());
			feeTobeCoipied.setAidedUnaided(fee.getAidedUnaided());
			Set<FeeAccountAssignment> feeAccountAssignments = new HashSet<FeeAccountAssignment>();
			Iterator<FeeAccountAssignment>itr =fee.getFeeAccountAssignments().iterator();
			while(itr.hasNext())
			{

				FeeAccountAssignment feeAccountAssignment = new FeeAccountAssignment();
				
				FeeAccountAssignment assignment = itr.next();
				
				feeAccountAssignment.setFeeAccount(assignment.getFeeAccount());
				
				feeAccountAssignment.setFeeHeading(assignment.getFeeHeading());
				
				feeAccountAssignment.setAdmittedThrough(assignment.getAdmittedThrough());
				
				feeAccountAssignment.setAmount(assignment.getAmount());
				
				feeAccountAssignment.setCurrencyByCurrencyId(assignment.getCurrencyByCurrencyId());
				
				feeAccountAssignment.setCurrencyByCasteCurrencyId(assignment.getCurrencyByCasteCurrencyId());
				
				feeAccountAssignment.setCurrencyByLigCurrencyId(assignment.getCurrencyByLigCurrencyId());
				
				feeAccountAssignment.setCasteAmount(assignment.getCasteAmount());
				
				feeAccountAssignment.setLigAmount(assignment.getLigAmount());
				
				feeAccountAssignment.setCreatedBy(assignmentCopyForm.getUserId());
				feeAccountAssignment.setModifiedBy(assignmentCopyForm.getUserId());
				feeAccountAssignment.setCreatedDate(new Date());
				feeAccountAssignment.setLastModifiedData(new Date());

				feeAccountAssignments.add(feeAccountAssignment);
			
			}
			feeTobeCoipied.setFeeAccountAssignments(feeAccountAssignments);
			listToBeCopied.add(feeTobeCoipied);
		}
		return listToBeCopied;
	}

}
