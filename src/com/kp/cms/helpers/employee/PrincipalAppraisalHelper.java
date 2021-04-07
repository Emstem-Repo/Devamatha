package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmpAppraisal;
import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.PrincipalAppraisalForm;
import com.kp.cms.to.employee.AppraisalsTO;
import com.kp.cms.to.employee.EmpAttributeTO;

public class PrincipalAppraisalHelper {
	private static final Log log = LogFactory.getLog(PrincipalAppraisalHelper.class);
	public static volatile PrincipalAppraisalHelper principalAppraisalHelper = null;

	private PrincipalAppraisalHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static PrincipalAppraisalHelper getInstance() {
		if (principalAppraisalHelper == null) {
			principalAppraisalHelper = new PrincipalAppraisalHelper();
		}
		return principalAppraisalHelper;
	}
	
	/**
	 * Converts all attribute BOs to TOS
	 */
	public List<EmpAttributeTO> convertAttributeBOToTO(List<EmpAttribute> attBOList)throws Exception{
		log.info("entering into convertAttributeBOToTO PrincipalAppraisalAction");
		List<EmpAttributeTO> empAttList = new ArrayList<EmpAttributeTO>();
		EmpAttributeTO attributeTO = null;
		if(attBOList!=null && !attBOList.isEmpty()){
			Iterator<EmpAttribute> iterator = attBOList.iterator();
			while (iterator.hasNext()) {
				EmpAttribute empAttribute = iterator.next();
				attributeTO = new EmpAttributeTO();
				if(empAttribute.getName()!=null){
					attributeTO.setName(empAttribute.getName());
				}
				attributeTO.setId(empAttribute.getId());
				empAttList.add(attributeTO);
			}
		}
		log.info("Leaving into convertAttributeBOToTO PrincipalAppraisalAction");
		return empAttList;
	}
	
	/**
	 * Generates appraisal BO 
	 * Used both for principal appraisal and HOD appraisal
	 */
	public EmpAppraisal generateAppraisalBO(PrincipalAppraisalForm appraisalForm, 
		boolean isPrincipal, String loginUserEmpId)throws Exception{
		log.info("entering into generateAppraisalBO PrincipalAppraisalAction");
			EmpAppraisal appraisal = new EmpAppraisal();
			Department department = null;
			Employee apraiseremployee = null;
			Employee apraiseeEmployee = new Employee();
			Set<EmpAppraisalDetails> empAppraisalDetailset = new HashSet<EmpAppraisalDetails>();
			EmpAppraisalDetails appraisalDetails = null;
			EmpAttribute empAttribute = null;
			
			appraisal.setIsPrincipal(isPrincipal);
			if(appraisalForm.getRecommendation()!=null){
				appraisal.setRecommendation(appraisalForm.getRecommendation());
			}
			appraisal.setIsActive(true);
			appraisal.setCreatedBy(appraisalForm.getUserId());
			appraisal.setModifiedBy(appraisalForm.getUserId());
			appraisal.setCreatedDate(new Date());
			appraisal.setLastModifiedDate(new Date());
			if(isPrincipal){
				department = new Department();
				department.setId(Integer.valueOf(appraisalForm.getDepartmentId()));
			}
			appraisal.setDepartment(department);
			apraiseeEmployee.setId(Integer.valueOf(appraisalForm.getEmployeeId()));
			appraisal.setEmployeeByAppraiseEmpId(apraiseeEmployee);
			if(!StringUtils.isEmpty(loginUserEmpId)){
				apraiseremployee = new Employee();
				apraiseremployee.setId(Integer.valueOf(loginUserEmpId));
			}
			appraisal.setEmployeeByAppraiserEmpId(apraiseremployee);
			
			//Populate the data for child table i.e. empappraisal details
			Iterator<EmpAttributeTO> attribueItr = appraisalForm.getAttriButeList().iterator();
			while (attribueItr.hasNext()) {
				EmpAttributeTO empAttributeTO = attribueItr.next();
				appraisalDetails = new EmpAppraisalDetails();
				empAttribute = new EmpAttribute();
				empAttribute.setId(empAttributeTO.getId());
				appraisalDetails.setEmpAttribute(empAttribute);
				if(!StringUtils.isEmpty(empAttributeTO.getValue())){
					appraisalDetails.setAttributeValue(Integer.valueOf(empAttributeTO.getValue()));
				}
				appraisalDetails.setCreatedBy(appraisalForm.getUserId());
				appraisalDetails.setModifiedBy(appraisalForm.getUserId());
				appraisalDetails.setCreatedDate(new Date());
				appraisalDetails.setLastModifiedDate(new Date());
				appraisalDetails.setIsActive(true);
				empAppraisalDetailset.add(appraisalDetails);
			}
			appraisal.setEmpAppraisalDetailses(empAppraisalDetailset);
		log.info("Leaving into generateAppraisalBO PrincipalAppraisalAction");
		return appraisal;
	}

public AppraisalsTO convertBOToTO(EmpAppraisal empDetails){
		
		AppraisalsTO appraisals=new AppraisalsTO();
		//appraisals.setAttributeName(empDetails.getEmpAttribute().getName());
		//appraisals.setDetailsId(empDetails.);
		//appraisals.setAttributeValue(empDetails.getAttributeValue());
		appraisals.setRecomand(empDetails.getRecommendation());
		appraisals.setDepartmentName(empDetails.getDepartment().getName());
		//appraisals.setCreatedBy(empDetails.getCreatedBy());
		//appraisals.setCreatedDate(empDetails.getCreatedDate());
		//appraisals.setLastModifiedDate(empDetails.getLastModifiedDate());
		//appraisals1.add(empDetails.getEmpAttribute().getName());
		appraisals.setDepartmentId(empDetails.getDepartment().getId());
		//appraisals.setIsEmployee(empDetails.getEmpAttribute().getIsEmployee());
		//appraisals.setAttributeCreatedDate(empDetails.getEmpAttribute().getCreatedDate());
		//appraisals.setEmpAppraiseId(empDetails.getEmpAppraisal().getEmployeeByAppraiseEmpId().getId());
		appraisals.setEmpAppraiseId(empDetails.getId());
		//appraisals.setAttributeId(empDetails.getEmpAttribute().getId());
		//appraisals.setAppraisalId(empDetails.getEmpAppraisal().getId());
		appraisals.setEmpAppraisalDetails(empDetails.getEmpAppraisalDetailses());
		appraisals.setAppraiseCreatedDate(empDetails.getCreatedDate());
		appraisals.setEmployeeName(empDetails.getEmployeeByAppraiseEmpId().getFirstName());
		return appraisals;
		
	}

public List<AppraisalsTO> convertBlistTOTlist(List<EmpAppraisal> empAppraisalList){
	List<AppraisalsTO> appraisalList=new ArrayList<AppraisalsTO>();
	EmpAppraisal appraisalDetails=null;
	Iterator it=empAppraisalList.iterator();
	 while(it.hasNext()){
		AppraisalsTO appraisal=new AppraisalsTO();
		
		 appraisalDetails=(EmpAppraisal) it.next();
		 //appraisalDetails1=(EmpAppraisalDetails) it.next();
		//appraisal.setAttributeName(appraisalDetails.getEmpAttribute().getName());
		//appraisal.setAttributeValue(appraisalDetails.getAttributeValue());
		//appraisal.setDepartmentName(appraisalDetails.getEmpAppraisal().getDepartment().getName());
		//appraisal.setCreatedBy(appraisalDetails.getCreatedBy());
		//appraisal.setRecomand(appraisalDetails.getEmpAppraisal().getRecommendation());
		//appraisal.setDetailsId(appraisalDetails.getId());
		appraisal.setAppraisalId(appraisalDetails.getId());
		appraisal.setDepartmentName(appraisalDetails.getDepartment().getName());
		appraisal.setEmployeeName(appraisalDetails.getEmployeeByAppraiseEmpId().getFirstName());
		appraisalList.add(appraisal);
	 }
	 
	return appraisalList;
}

}