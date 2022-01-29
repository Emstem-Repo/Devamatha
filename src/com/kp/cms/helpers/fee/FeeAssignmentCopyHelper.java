package com.kp.cms.helpers.fee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.forms.fee.FeeAssignmentCopyForm;
import com.kp.cms.utilities.CommonUtil;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

public class FeeAssignmentCopyHelper {

	private FeeAssignmentCopyHelper()
	{
		
	}
	
	private static FeeAssignmentCopyHelper assignmentCopyHelper=null;
	public static FeeAssignmentCopyHelper getInstance() 
	{
		if(assignmentCopyHelper==null)
		{
			assignmentCopyHelper=new FeeAssignmentCopyHelper();
		}
		return assignmentCopyHelper;
	}
	public String getQueryToGetExistingFeesDefinition(FeeAssignmentCopyForm assignmentCopyForm) 
	{
		String strQuery="from Fee f where f.isActive=1 and f.course.id="+assignmentCopyForm.getCourseId()+" and f.academicYear="+assignmentCopyForm.getAcademicYear()+" and f.semesterNo="+assignmentCopyForm.getFromSemester();
		return strQuery;
	}
	public String getQueryToGetFeeDefinitionToBecopiedExists(FeeAssignmentCopyForm assignmentCopyForm) 
	{
		String strQuery="from Fee f where f.isActive=1 and f.course.id="+assignmentCopyForm.getCourseId()+" and f.academicYear="+assignmentCopyForm.getAcademicYear()+" and f.semesterNo="+assignmentCopyForm.getToSemester();
		return strQuery;
	}
	public List<Fee> getFeeListToBeCopied(List<Fee> existingFeeDefinitions,	FeeAssignmentCopyForm assignmentCopyForm) 
	{
		List<Fee>listToBeCopied=new ArrayList<Fee>();
		for(Fee fee:existingFeeDefinitions)
		{
			Fee feeTobeCoipied=new Fee();
			feeTobeCoipied.setSemesterNo(Integer.parseInt(assignmentCopyForm.getToSemester()));
			feeTobeCoipied.setProgramType(fee.getProgramType());
			feeTobeCoipied.setFeeDivision(fee.getFeeDivision());
			feeTobeCoipied.setProgram(fee.getProgram());
			feeTobeCoipied.setCourse(fee.getCourse());
			feeTobeCoipied.setSubjectGroup(null);
			feeTobeCoipied.setAcademicYear(fee.getAcademicYear());
			feeTobeCoipied.setCreatedDate(new Date());
			feeTobeCoipied.setLastModifiedDate(new Date());
			feeTobeCoipied.setCreatedBy(assignmentCopyForm.getUserId());
			feeTobeCoipied.setModifiedBy(assignmentCopyForm.getUserId());
			feeTobeCoipied.setFeeGroup(fee.getFeeGroup());
			feeTobeCoipied.setSection(fee.getSection());
			feeTobeCoipied.setIsActive(true);
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
	public String getQueryToGetExistingFeesDefinitionCourse(FeeAssignmentCopyForm assignmentCopyForm) 
	{
		String strQuery="from Fee f where f.isActive=1 and f.course.id="+assignmentCopyForm.getCourseId()+" and f.academicYear="+assignmentCopyForm.getAcademicYear()+" and f.semesterNo= 1";
		return strQuery;
	}
	public String getQueryToGetFeeDefinitionToBecopiedExistsCourse(FeeAssignmentCopyForm assignmentCopyForm) 
	{
		String strQuery="from Fee f where f.isActive=1 and f.course.id="+assignmentCopyForm.getToCourseId()+" and f.academicYear="+assignmentCopyForm.getAcademicYear()+" and f.semesterNo=1";
		return strQuery;
	}

	public List<Fee> getFeeListToBeCopiedCourse(List<Fee> existingFeeDefinitions,	FeeAssignmentCopyForm assignmentCopyForm) 
	{
		List<Fee>listToBeCopied=new ArrayList<Fee>();
		for(Fee fee:existingFeeDefinitions)
		{
			Fee feeTobeCoipied=new Fee();
			feeTobeCoipied.setSemesterNo(fee.getSemesterNo());
			feeTobeCoipied.setProgramType(fee.getProgramType());
			feeTobeCoipied.setFeeDivision(fee.getFeeDivision());
			feeTobeCoipied.setProgram(fee.getProgram());
			Course course = new Course();
			course.setId(Integer.parseInt(assignmentCopyForm.getToCourseId()));
			feeTobeCoipied.setCourse(course);
			feeTobeCoipied.setSubjectGroup(null);
			feeTobeCoipied.setAcademicYear(fee.getAcademicYear());
			feeTobeCoipied.setCreatedDate(new Date());
			feeTobeCoipied.setLastModifiedDate(new Date());
			feeTobeCoipied.setCreatedBy(assignmentCopyForm.getUserId());
			feeTobeCoipied.setModifiedBy(assignmentCopyForm.getUserId());
			feeTobeCoipied.setFeeGroup(fee.getFeeGroup());
			feeTobeCoipied.setSection(fee.getSection());
			feeTobeCoipied.setIsActive(true);
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
