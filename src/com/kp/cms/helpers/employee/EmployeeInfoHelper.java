package com.kp.cms.helpers.employee;

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

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpDependents;
import com.kp.cms.bo.admin.EmpEducation;
import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.bo.admin.EmpImmigration;
import com.kp.cms.bo.admin.EmpJob;
import com.kp.cms.bo.admin.EmpJobAllowance;
import com.kp.cms.bo.admin.EmpLanguage;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpSkills;
import com.kp.cms.bo.admin.EmpWorkExperience;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.forms.employee.EmployeeInfoForm;
import com.kp.cms.helpers.usermanagement.Base64Coder;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpEducationTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmpJobAllowanceTO;
import com.kp.cms.to.admin.EmpJobTO;
import com.kp.cms.to.admin.EmpLanguagesTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.admin.EmpSkillsTO;
import com.kp.cms.to.admin.EmpWorkExperienceTO;
import com.kp.cms.to.admin.EmployeeInformactionTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;
import com.kp.cms.to.employee.EmployeeStreamTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

/**
 * HELPER CLASS FOR EMPLOYEE INFORMATION
 * 
 */
public class EmployeeInfoHelper {
	private static final Log log = LogFactory.getLog(EmployeeInfoHelper.class);
	public static volatile EmployeeInfoHelper self = null;
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";

	public static EmployeeInfoHelper getInstance() {
		if (self == null) {
			self = new EmployeeInfoHelper();
		}
		return self;
	}

	private EmployeeInfoHelper() {

	}

	/**
	 * converts employee bo to TO
	 * 
	 * @param employeeTo
	 * @param employee
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public EmployeeTO convertEmployeeBoToTO(EmployeeTO employeeTo,
			Employee employee, EmployeeInfoForm infoForm) throws Exception {
		if (employeeTo == null)
			employeeTo = infoForm.getEmployeeDetail();
		employeeTo.setOriginalEmployee(employee);
		employeeTo.setId(employee.getId());
		employeeTo.setFirstName(employee.getFirstName());
		employeeTo.setLastName(employee.getLastName());
		//employeeTo.setNickName(employee.getNickName());
		employeeTo.setMiddleName(employee.getMiddleName());
		employeeTo.setCode(employee.getCode());

		employeeTo.setUid(employee.getUid());
		employeeTo.setPanNo(employee.getPanNo());

		/*if (employee.getSmoker() != null && employee.getSmoker()) {
			employeeTo.setSmoker(false);
			employeeTo.setTempsmoker(true);
		} else {
			employeeTo.setSmoker(false);
			employeeTo.setTempsmoker(false);
		}
		employeeTo.setDrivingLicenseNo(employee.getDrivingLicenseNo());
		employeeTo.setMilitaryService(employee.getMilitaryService());*/
		if (employee.getNationality() != null) {
			employeeTo.setNationalityId(String.valueOf(employee
					.getNationality().getId()));
			employeeTo.setNationalityName(employee.getNationality().getName());
		}
		else
		{
			employeeTo.setNationalityId(null);
			employeeTo.setNationalityName(null);
		}

		if (employee.getDob() != null) {
			employeeTo.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil
					.getStringDate(employee.getDob()), SQL_DATEFORMAT,
					FROM_DATEFORMAT));
		}
		else
		{
			employeeTo.setDob(null);
		}
		if (employee.getActive()) {
			employeeTo.setActive("Yes");
			employeeTo.setActiveDummy("Yes");
		} else {
			employeeTo.setActive("No");
			employeeTo.setActiveDummy("No");
		}
		if (employee.getFingerPrintId() != null) {
			employeeTo.setFingerPrintId(employee.getFingerPrintId());
		}
		else
		{
			employeeTo.setFingerPrintId(null);
		}
		if (employee.getFatherName() != null) {
			employeeTo.setFatherName(employee.getFatherName());
		}
		else
		{
			employeeTo.setFatherName(null);
		}
		if (employee.getMotherName() != null) {
			employeeTo.setMotherName(employee.getMotherName());
		}
		else
		{
			employeeTo.setMotherName(null);
		}
		if (employee.getEmail() != null) {
			employeeTo.setEmail(employee.getEmail());
		}
		else
		{
			employeeTo.setEmail(null);
		}

		if (employee.getBloodGroup() != null) {
			employeeTo.setBloodGroup(employee.getBloodGroup());
		}
		else
			employeeTo.setBloodGroup(null);	
		employeeTo.setMaritalStatus(employee.getMaritalStatus());
		employeeTo.setGender(employee.getGender());
		/*if (employee.getLicenseExpDate() != null) {
			employeeTo.setLicenseExpDate(CommonUtil.ConvertStringToDateFormat(
					CommonUtil.getStringDate(employee.getLicenseExpDate()),
					SQL_DATEFORMAT, FROM_DATEFORMAT));
		}
		else
			employeeTo.setLicenseExpDate(null);
		employeeTo.setEthinicRace(employee.getEthinicRace());*/
		// communication contact details
		employeeTo.setCommunicationAddressLine1(employee
				.getCommunicationAddressLine1());
		employeeTo.setCommunicationAddressLine2(employee
				.getCommunicationAddressLine2());
		employeeTo.setCommunicationAddressCity(employee
				.getCommunicationAddressCity());
		employeeTo.setCommunicationAddressZip(employee
				.getCommunicationAddressZip());
		
		if (employee.getCountryByCommunicationAddressCountryId() != null) {
			employeeTo.setCommunicationCountryId(String.valueOf(employee
					.getCountryByCommunicationAddressCountryId().getId()));
			employeeTo.setCommunicationCountryName(employee
					.getCountryByCommunicationAddressCountryId().getName());
		}
		else
		{
			employeeTo.setCommunicationCountryId(null);
			employeeTo.setCommunicationCountryName(null);
		}

		if (employee.getStateByCommunicationAddressStateId() != null) {
			employeeTo.setCommunicationStateId(String.valueOf(employee
					.getStateByCommunicationAddressStateId().getId()));
			employeeTo.setCommunicationStateName(employee
					.getStateByCommunicationAddressStateId().getName());
		}
		else
		{
			//employeeTo.setCommunicationStateId(null);
			//employeeTo.setCommunicationStateName(null);
			employeeTo.setCommunicationStateId("Other");
			//employeeTo.setCommunicationStateName(employee.getCommunicationAddressStateOthers());
			employeeTo.setCommunicationAddressStateOthers(employee.getCommunicationAddressStateOthers());
		}

		// permanent contact details
		employeeTo
				.setPermanentAddressLine1(employee.getPermanentAddressLine1());
		employeeTo
				.setPermanentAddressLine2(employee.getPermanentAddressLine2());
		employeeTo.setPermanentAddressCity(employee.getPermanentAddressCity());
		employeeTo.setPermanentAddressZip(employee.getPermanentAddressZip());
		if (employee.getCountryByPermanentAddressCountryId() != null) {
			employeeTo.setPermanentCountryId(String.valueOf(employee
					.getCountryByPermanentAddressCountryId().getId()));
			employeeTo.setPermanentCountryName(employee
					.getCountryByPermanentAddressCountryId().getName());
		}
		else
		{
			employeeTo.setPermanentCountryId(null);
			employeeTo.setPermanentCountryName(null);
		}

		if (employee.getStateByPermanentAddressStateId() != null) {
			employeeTo.setPermanentStateId(String.valueOf(employee
					.getStateByPermanentAddressStateId().getId()));
			employeeTo.setPermanentStateName(employee
					.getStateByPermanentAddressStateId().getName());
		}
		else
		{
			employeeTo.setPermanentStateId("Other");
			//employeeTo.setPermanentStateName("Other");
			employeeTo.setPermanentAddressStateOthers(employee.getPermanentAddressStateOthers());
		}

		employeeTo.setCurrentAddressHomeTelephone1(employee
				.getCurrentAddressHomeTelephone1());
		employeeTo.setCurrentAddressHomeTelephone2(employee
				.getCurrentAddressHomeTelephone2());
		employeeTo.setCurrentAddressHomeTelephone3(employee
				.getCurrentAddressHomeTelephone3());
		employeeTo.setCurrentAddressWorkTelephone1(employee
				.getCurrentAddressWorkTelephone1());
		employeeTo.setCurrentAddressWorkTelephone2(employee
				.getCurrentAddressWorkTelephone2());
		employeeTo.setCurrentAddressWorkTelephone3(employee
				.getCurrentAddressWorkTelephone3());

		employeeTo
				.setCurrentAddressMobile1(employee.getCurrentAddressMobile1());
	/*	employeeTo
				.setCurrentAddressMobile2(employee.getCurrentAddressMobile2());
		employeeTo
				.setCurrentAddressMobile3(employee.getCurrentAddressMobile3());

		employeeTo.setPermanentAddressHomeTelephone1(employee
				.getPermanentAddressHomeTelephone1());
		employeeTo.setPermanentAddressHomeTelephone2(employee
				.getPermanentAddressHomeTelephone2());
		employeeTo.setPermanentAddressHomeTelephone3(employee
				.getPermanentAddressHomeTelephone3());*/

		employeeTo
				.setCurrentAddressMobile1(employee.getCurrentAddressMobile1());
		/*employeeTo
				.setCurrentAddressMobile2(employee.getCurrentAddressMobile2());
		employeeTo
				.setCurrentAddressMobile3(employee.getCurrentAddressMobile3());

		employeeTo.setPermanentAddressMobile1(employee
				.getPermanentAddressMobile1());
		employeeTo.setPermanentAddressMobile2(employee
				.getPermanentAddressMobile2());
		employeeTo.setPermanentAddressMobile3(employee
				.getPermanentAddressMobile3());*/

		employeeTo.setWorkEmail(employee.getWorkEmail());
		employeeTo.setOtherEmail(employee.getOtherEmail());
		// emergency contact details
		employeeTo.setEmergencyContName(employee.getEmergencyContName());
		employeeTo.setRelationship(employee.getRelationship());
		int i=0;
		if(employee.getEmergencyHomeTelephone()!=null){
			String emgPhone= employee.getEmergencyHomeTelephone();
			String emgPhone1[]= emgPhone.split("_");
			for(i=0;i<emgPhone1.length;i++){
				if(i==0){
					if(emgPhone1[0]!=null)
					employeeTo.setEmergencyHomeTelephone1(emgPhone1[0]);
				}
				if(i==1){
					if(emgPhone1[1]!=null)
					employeeTo.setEmergencyHomeTelephone2(emgPhone1[1]);
				}
				if(i==2){
					if(emgPhone1[2]!=null)
					employeeTo.setEmergencyHomeTelephone(emgPhone1[2]);
				}
			}
		}else{
			employeeTo.setEmergencyHomeTelephone1(null);
			employeeTo.setEmergencyHomeTelephone2(null);
			employeeTo.setEmergencyHomeTelephone(null);
		}
		if(employee.getEmergencyMobile()!=null){
			 String emgMPhone =employee.getEmergencyMobile();
			 String emgPhone2[] = emgMPhone.split("_");
			 for(i=0;i<emgPhone2.length;i++){
				 if(i==0){
					 if(emgPhone2[0]!=null)
						employeeTo.setEmergencyMobile1(emgPhone2[0]);
				 }
				if(i==1){
					if(emgPhone2[1]!=null)
					employeeTo.setEmergencyMobile2(emgPhone2[1]);
				}
				if(i==2){
					if(emgPhone2[2]!=null)
					employeeTo.setEmergencyMobile(emgPhone2[2]);
				}
			}
		}else{
			employeeTo.setEmergencyMobile1(null);
			employeeTo.setEmergencyMobile2(null);
			employeeTo.setEmergencyMobile(null);
		}
		if(employee.getEmergencyWorkTelephone()!=null){
			 String emgWPhone=employee.getEmergencyWorkTelephone();
			 String emgPhone3[] = emgWPhone.split("_");
			 for(i=0;i<emgPhone3.length;i++){
				 if(i==0){
				 	 if(emgPhone3[0]!=null)
				 		 employeeTo.setEmergencyWorkTelephone1(emgPhone3[0]);
					}
				 if(i==1){
					 if(emgPhone3[1]!=null)
						 employeeTo.setEmergencyWorkTelephone2(emgPhone3[1]);
					}
				 if(i==2){
					 if(emgPhone3[2]!=null)
						 employeeTo.setEmergencyWorkTelephone(emgPhone3[2]);
				 	}
			 	}
		}else{
			employeeTo.setEmergencyWorkTelephone1(null);
			employeeTo.setEmergencyWorkTelephone2(null);
			employeeTo.setEmergencyWorkTelephone(null);
		}
		// skills
		if (employee.getEmpSkillses() != null
				&& !employee.getEmpSkillses().isEmpty()) {
			convertSkills(employee.getEmpSkillses(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpSkillsTO> skillsTos = new ArrayList<EmpSkillsTO>();
				skillsTos.add(new EmpSkillsTO());
				employeeTo.setSkills(skillsTos);
			}
		}

		// immigrations
		if (employee.getEmpImmigrations() != null
				&& !employee.getEmpImmigrations().isEmpty()) {
			convertImmigrations(employee.getEmpImmigrations(), employeeTo,
					infoForm.isAdminUser());
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpImmigrationTO> immigrationsTos = new ArrayList<EmpImmigrationTO>();
				immigrationsTos.add(new EmpImmigrationTO());
				employeeTo.setImmigrations(immigrationsTos);
			}
		}

		// educations
		if (employee.getEmpEducations() != null
				&& !employee.getEmpEducations().isEmpty()) {
			convertEducations(employee.getEmpEducations(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpEducationTO> educationsTos = new ArrayList<EmpEducationTO>();
				educationsTos.add(new EmpEducationTO());
				employeeTo.setEducations(educationsTos);
			}
		}

		// work experiences
		if (employee.getEmpWorkExperiences() != null
				&& !employee.getEmpWorkExperiences().isEmpty()) {
			convertExperiences(employee.getEmpWorkExperiences(), employeeTo,
					infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpWorkExperienceTO> experiencesTos = new ArrayList<EmpWorkExperienceTO>();
				experiencesTos.add(new EmpWorkExperienceTO());
				employeeTo.setExperiences(experiencesTos);
			}
		}

		// languages
		if (employee.getEmpLanguages() != null
				&& !employee.getEmpLanguages().isEmpty()) {
			convertLanguages(employee.getEmpLanguages(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpLanguagesTO> languageTos = new ArrayList<EmpLanguagesTO>();
				languageTos.add(new EmpLanguagesTO());
				employeeTo.setLanguages(languageTos);
			}
		}
		// Job
		if (employee.getEmpJobs() != null && !employee.getEmpJobs().isEmpty()) {
			convertJobs(employee.getEmpJobs(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpJobTO> jobTos = new ArrayList<EmpJobTO>();
				jobTos.add(new EmpJobTO());
				employeeTo.setJobs(jobTos);
			}
		}
		if (employee.getStreamId() != null) {
			employeeTo.setStream(Integer.toString(employee.getStreamId()
					.getId()));
		}
		else
			employeeTo.setStream(null);

		if (employee.getWorkLocationId() != null) {
			employeeTo.setWorkLocationId(Integer.toString(employee
					.getWorkLocationId().getId()));
		}
		else
			employeeTo.setWorkLocationId(null);
		if (employee.getDepartment() != null
				&& employee.getDepartment().getId() != 0) {
			employeeTo.setDepartmentId(Integer.toString(employee
					.getDepartment().getId()));
		}
		else
			employeeTo.setDepartmentId(null);
		if (employee.getDesignation() != null
				&& employee.getDesignation().getId() != 0) {
			employeeTo.setDesignationId(Integer.toString(employee
					.getDesignation().getId()));
		}
		else
			employeeTo.setDesignationId(null);
		// leave
		convertLeaves(employee.getEmpLeaves(), employeeTo, infoForm);

		// Achievements
		convertAchievements(employee.getEmpAcheivements(), employeeTo, infoForm);

		// dependents
		if (employee.getEmpDependentses() != null
				&& !employee.getEmpDependentses().isEmpty()) {
			convertDependents(employee.getEmpDependentses(), employeeTo,
					infoForm.isAdminUser());
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpDependentsTO> dependentTos = new ArrayList<EmpDependentsTO>();
				dependentTos.add(new EmpDependentsTO());
				employeeTo.setDependents(dependentTos);
			}
		}

		// reporting info
		if (employee.getEmployeeByReportToId() != null) {
			employeeTo.setReportToId(String.valueOf(employee
					.getEmployeeByReportToId().getId()));
			StringBuffer nameBUff = new StringBuffer();
			nameBUff.append(employee.getEmployeeByReportToId().getFirstName());
			if (employee.getEmployeeByReportToId().getLastName() != null) {
				nameBUff.append(" ");
				nameBUff.append(employee.getEmployeeByReportToId()
						.getLastName());
			}
			employeeTo.setReportToName(nameBUff.toString());

		}
		else
		{
			employeeTo.setReportToId(null);
			employeeTo.setReportToName(null);
		}
		if (employee.getEmployeeByLeaveApproveAuthId() != null) {
			employeeTo.setFinalAuthorityId(String.valueOf(employee
					.getEmployeeByLeaveApproveAuthId().getId()));
			StringBuffer nameBUff = new StringBuffer();
			nameBUff.append(employee.getEmployeeByLeaveApproveAuthId()
					.getFirstName());
			if (employee.getEmployeeByLeaveApproveAuthId().getLastName() != null) {
				nameBUff.append(" ");
				nameBUff.append(employee.getEmployeeByLeaveApproveAuthId()
						.getLastName());
			}
			employeeTo.setFinalAuthorityName(nameBUff.toString());

		}
		else
		{
			employeeTo.setFinalAuthorityId(null);
			employeeTo.setFinalAuthorityName(null);
		}
	
		/**
		 * User details
		 */
		
		return employeeTo;
	}

	/**
	 * @param empDependentses
	 * @param employeeTo
	 * @param adminUser
	 */
	private void convertDependents(Set<EmpDependents> empDependentses,
			EmployeeTO employeeTo, boolean adminUser) {

		Iterator<EmpDependents> skillItr = empDependentses.iterator();
		List<EmpDependentsTO> dependentTos = new ArrayList<EmpDependentsTO>();
		while (skillItr.hasNext()) {
			EmpDependents dependent = (EmpDependents) skillItr.next();

			if (dependent.getIsActive() != null && dependent.getIsActive()) {
				EmpDependentsTO dependentto = new EmpDependentsTO();
				dependentto.setOriginalPresent(true);
				dependentto.setOriginalDependent(dependent);
				dependentto.setId(String.valueOf(dependent.getId()));

				dependentto.setActive(dependent.getIsActive());

				dependentto.setCreatedBy(dependent.getCreatedBy());
				dependentto.setCreatedDate(dependent.getCreatedDate());
				dependentto.setEmployeeId(String.valueOf(dependent
						.getEmployee().getId()));
				//dependentto.setDependentName(dependent.getDependentName());
				dependentto.setDependentRelationship(dependent
						.getDependentRelationship());

				dependentto.setChild1Name(dependent.getChild1Name());
				dependentto.setChild2Name(dependent.getChild2Name());

				if (dependent.getChild1DateOfBirth() != null)
					dependentto.setChild1DateOfBirth(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(dependent
											.getChild1DateOfBirth()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				if (dependent.getChild2DateOfBirth() != null)
					dependentto.setChild2DateOfBirth(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(dependent
											.getChild2DateOfBirth()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				dependentTos.add(dependentto);
			}

		}

		employeeTo.setDependents(dependentTos);

	}

	/**
	 * @param empAcheivements
	 * @param employeeTo
	 * @param infoForm
	 */
	private void convertAchievements(Set<EmpAcheivement> empAcheivements,
			EmployeeTO employeeTo, EmployeeInfoForm infoForm) {
		List<EmpAcheivementTO> achieveTos = new ArrayList<EmpAcheivementTO>();
		if (empAcheivements != null && !empAcheivements.isEmpty()) {
			Iterator<EmpAcheivement> alwitr = empAcheivements.iterator();
			while (alwitr.hasNext()) {
				EmpAcheivement achievement = (EmpAcheivement) alwitr.next();
				EmpAcheivementTO achieveTo = new EmpAcheivementTO();
				achieveTo.setId(achievement.getId());
				if (achievement.getIsActive() != null)
					achieveTo.setActive(achievement.getIsActive());
				achieveTo.setCreatedBy(achievement.getCreatedBy());
				achieveTo.setCreatedDate(achievement.getCreatedDate());

				if (achievement.getEmployee() != null)
					achieveTo.setEmployeeId(String.valueOf(achievement
							.getEmployee().getId()));

				achieveTo.setAcheivementName(achievement.getAcheivementName());
				achieveTo.setDetails(achievement.getDetails());
				achieveTo.setStatus(achievement.getStatus());

				achieveTo
						.setLastModifiedDate(achievement.getLastModifiedDate());
				achieveTo.setModifiedBy(achievement.getModifiedBy());
				achieveTos.add(achieveTo);

			}
		}

		infoForm.setAchievementTOs(achieveTos);

	}

	/**
	 * @param empLeaves
	 * @param employeeTo
	 * @param infoForm
	 */
	private void convertLeaves(Set<EmpLeave> empLeaves, EmployeeTO employeeTo,EmployeeInfoForm infoForm) 
	{
		List<EmpLeaveTO> leaveTos = new ArrayList<EmpLeaveTO>();
		if (empLeaves != null && !empLeaves.isEmpty()) 
		{
			
			for(EmpLeaveTypeTO allLeaves:infoForm.getLeaveTypeTOs())
			{
				boolean leavePresent=false;
				String leaveId="";
				String leaveName="";
				leaveId=String.valueOf(allLeaves.getId());
				leaveName=allLeaves.getName();
				Iterator<EmpLeave> alwitr = empLeaves.iterator();
				while (alwitr.hasNext()) 
				{
					EmpLeave leave = (EmpLeave) alwitr.next();
					if(allLeaves.getId()==leave.getEmpLeaveType().getId())
					{
						leavePresent=true;
						break;
					}
				}
				if(!leavePresent)
				{
					EmpLeaveTO leaveTo = new EmpLeaveTO();
					leaveTo.setEmpLeaveTypeId(leaveId);
					leaveTo.setEmpLeaveTypeName(leaveName);
					leaveTos.add(leaveTo);
				}
			}
			Iterator<EmpLeave> alwitr = empLeaves.iterator();
			while (alwitr.hasNext()) 
			{
				EmpLeave leave = (EmpLeave) alwitr.next();
				EmpLeaveTO leaveTo = new EmpLeaveTO();
				if(leave.getEmpLeaveType().getIsActive())
				{	
					leaveTo.setId(leave.getId());
					if (leave.getIsActive() != null)
						leaveTo.setActive(leave.getIsActive());
					leaveTo.setCreatedBy(leave.getCreatedBy());
					leaveTo.setCreatedDate(leave.getCreatedDate());
					if (leave.getEmpLeaveType() != null) 
					{
						leaveTo.setEmpLeaveTypeId(String.valueOf(leave.getEmpLeaveType().getId()));
						leaveTo.setEmpLeaveTypeName(leave.getEmpLeaveType().getName());
					}
					if (leave.getEmployee() != null)
						leaveTo.setEmployeeId(String.valueOf(leave.getEmployee().getId()));
					if (leave.getLeavesAllocated() != null)
						leaveTo.setLeavesAllocated(String.valueOf(leave.getLeavesAllocated()));
					if (leave.getLeavesRemaining() != null)
						leaveTo.setLeavesRemaining(String.valueOf(leave.getLeavesRemaining()));
					if (leave.getLeavesSanctioned() != null)
						leaveTo.setLeavesSanctioned(String.valueOf(leave.getLeavesSanctioned()));
					leaveTo.setLastModifiedDate(leave.getLastModifiedDate());
					leaveTo.setModifiedBy(leave.getModifiedBy());
					leaveTos.add(leaveTo);
				}	
			}
		} 
		else 
		{
			if (infoForm.getLeaveTypeTOs() != null) 
			{
				Iterator<EmpLeaveTypeTO> allItr = infoForm.getLeaveTypeTOs().iterator();
				while (allItr.hasNext()) 
				{
					EmpLeaveTypeTO typeTO = (EmpLeaveTypeTO) allItr.next();
					EmpLeaveTO leaveTo = new EmpLeaveTO();
					leaveTo.setEmpLeaveTypeId(String.valueOf(typeTO.getId()));
					leaveTo.setEmpLeaveTypeName(typeTO.getName());
					leaveTos.add(leaveTo);
				}
			}
		}
		infoForm.setLeaveTOs(leaveTos);

	}

	/**
	 * @param empJobs
	 * @param employeeTo
	 * @param infoForm
	 */
	private void convertJobs(Set<EmpJob> empJobs, EmployeeTO employeeTo,
			EmployeeInfoForm infoForm) {

		Iterator<EmpJob> skillItr = empJobs.iterator();
		List<EmpJobTO> jobTos = new ArrayList<EmpJobTO>();
		while (skillItr.hasNext()) {
			EmpJob job = (EmpJob) skillItr.next();

			if (job.getIsActive() != null && job.getIsActive()) {
				EmpJobTO jobto = new EmpJobTO();
				if (job.getEmployee() != null)
					jobto.setEmployeeId(String.valueOf(job.getEmployee()
							.getId()));
				jobto.setOriginalJob(job);

				jobto.setJobTitle(job.getJobTitle());
				jobto.setEmploymentStatus(job.getEmploymentStatus());
				jobto.setJobSpecification(job.getJobSpecification());
				jobto.setJobDuties(job.getJobDuties());
				if (job.getObjEmployeeTypeBO() != null	&& job.getObjEmployeeTypeBO().getId() != 0) 
				{
					employeeTo.setEmployeeType(Integer.toString(job.getObjEmployeeTypeBO().getId()));
				}
				else
				{
					employeeTo.setEmployeeType("");
				}

				jobto.setDepartmentType(job.getDepartmentType());
				jobto.setJoiningDate(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(job.getJoiningDate()),
						SQL_DATEFORMAT, FROM_DATEFORMAT));
				jobto.setDateOfRetirement(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(job.getDateOfRetirement()),
						SQL_DATEFORMAT, FROM_DATEFORMAT));
				jobto.setDateOfRejoin(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(job.getDateOfRejoin()),
						SQL_DATEFORMAT,FROM_DATEFORMAT));

				if (job.getBasicPay() != null)
					jobto.setBasicPay(String.valueOf(job.getBasicPay()));
				if (job.getGrossPay() != null)
					jobto.setGrossPay(String.valueOf(job.getGrossPay()));

				jobto.setResignationReason(job.getResignationReason());
				jobto.setDateOfResign(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(job.getDateOfResign()),
						SQL_DATEFORMAT, FROM_DATEFORMAT));
				jobto.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(
						CommonUtil.getStringDate(job.getDateOfLeaving()),
						SQL_DATEFORMAT, FROM_DATEFORMAT));

				jobto.setFinancialAssisstance(job.getFinancialAssisstance());

				List<EmpJobAllowanceTO> allowanceTos = new ArrayList<EmpJobAllowanceTO>();
				if (job.getEmpJobAllowances() != null&& !job.getEmpJobAllowances().isEmpty()) 
				{
					Iterator<EmpJobAllowance> alwitr = job.getEmpJobAllowances().iterator();
					while (alwitr.hasNext()) 
					{
						EmpJobAllowance jobAllowance = (EmpJobAllowance) alwitr.next();
						EmpJobAllowanceTO allowanceTo = new EmpJobAllowanceTO();
						allowanceTo.setId(jobAllowance.getId());
						if (jobAllowance.getIsActive() != null)
							allowanceTo.setActive(jobAllowance.getIsActive());
						allowanceTo.setCreatedBy(jobAllowance.getCreatedBy());
						allowanceTo.setCreatedDate(jobAllowance.getCreatedDate());
						if (jobAllowance.getAmount() != null)
							allowanceTo.setAmount(String.valueOf(jobAllowance.getAmount()));
						allowanceTo	.setEmpAllowanceId(String.valueOf(jobAllowance.getEmpAllowance().getId()));
						allowanceTo.setEmpAllowanceName(jobAllowance.getEmpAllowance().getName());
						allowanceTo.setEmpJobId(String.valueOf(job.getId()));
						allowanceTo.setId(jobAllowance.getId());
						allowanceTo.setLastModifiedDate(jobAllowance.getLastModifiedDate());
						allowanceTo.setModifiedBy(jobAllowance.getModifiedBy());
						allowanceTos.add(allowanceTo);

					}
				}
				else
				{
					if (infoForm.getAllowanceTos() != null) 
					{
						Iterator<EmpAllowanceTO> allItr = infoForm.getAllowanceTos().iterator();
						while (allItr.hasNext()) 
						{
							EmpAllowanceTO allowanceTO = (EmpAllowanceTO) allItr.next();
							EmpJobAllowanceTO jobAllwnTo = new EmpJobAllowanceTO();
							jobAllwnTo.setEmpAllowanceId(String.valueOf(allowanceTO.getId()));
							jobAllwnTo.setEmpAllowanceName(allowanceTO.getName());
							allowanceTos.add(jobAllwnTo);
						}
					}
				}

				infoForm.setAllowances(allowanceTos);
				infoForm.setAllowanceSize(allowanceTos.size());

				jobto.setCreatedBy(job.getCreatedBy());
				jobto.setCreatedDate(job.getCreatedDate());

				jobto.setActive(job.getIsActive());
				jobTos.add(jobto);
			}

		}

		employeeTo.setJobs(jobTos);

	}

	/**
	 * @param empLanguages
	 * @param employeeTo
	 * @param adminUser
	 */
	private void convertLanguages(Set<EmpLanguage> empLanguages,
			EmployeeTO employeeTo, EmployeeInfoForm objForm) {

		Iterator<EmpLanguage> skillItr = empLanguages.iterator();
		List<EmpLanguagesTO> languageTos = new ArrayList<EmpLanguagesTO>();
		while (skillItr.hasNext()) {
			EmpLanguage language = (EmpLanguage) skillItr.next();

			if (language.getIsActive() != null && language.getIsActive()) {
				EmpLanguagesTO langto = new EmpLanguagesTO();
				if (language.getEmployee() != null)
					langto.setEmployeeId(String.valueOf(language.getEmployee()
							.getId()));

				langto.setName(language.getName());
				langto.setFluency(language.getFluency());

				langto.setCreatedBy(language.getCreatedBy());
				langto.setCreatedDate(language.getCreatedDate());
				langto.setOriginalPresent(true);
				langto.setOriginalLanguage(language);
				langto.setActive(language.getIsActive());
				languageTos.add(langto);
			}

		}
		if(objForm.getMode()!=null){
		if (objForm.getMode().equalsIgnoreCase("LAddMore")) {
			// add one blank to add extra one
			EmpLanguagesTO langto = new EmpLanguagesTO();
			languageTos.add(langto);
			objForm.setMode(null);
		}
		}
		employeeTo.setLanguages(languageTos);

	}

	/**
	 * @param empWorkExperiences
	 * @param employeeTo
	 */
	private void convertExperiences(Set<EmpWorkExperience> empWorkExperiences,
			EmployeeTO employeeTo, EmployeeInfoForm objForm) {

		Iterator<EmpWorkExperience> skillItr = empWorkExperiences.iterator();
		List<EmpWorkExperienceTO> experienceTos = new ArrayList<EmpWorkExperienceTO>();
		while (skillItr.hasNext()) {
			EmpWorkExperience experience = (EmpWorkExperience) skillItr.next();

			if (experience.getIsActive() != null && experience.getIsActive()) {
				EmpWorkExperienceTO expto = new EmpWorkExperienceTO();
				if (experience.getEmployee() != null)
					expto.setEmployeeId(String.valueOf(experience.getEmployee()
							.getId()));

				expto.setEmployer(experience.getEmployer());
				expto.setJobTitle(experience.getJobTitle());
				expto.setComments(experience.getComments());
				// if(experience.getIsInternal()!=null &&
				// experience.getIsInternal())
				// expto.setInternal(true);
				if (experience.getIsInternal() != null
						&& experience.getIsInternal()) {
					expto.setInternal(false);
					expto.setTempInternal(true);
				} else {
					expto.setInternal(false);
					expto.setTempInternal(false);
				}

				if (experience.getStartDate() != null)
					expto.setStartDate(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(experience.getStartDate()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				if (experience.getEndDate() != null)
					expto.setEndDate(CommonUtil.ConvertStringToDateFormat(
							CommonUtil.getStringDate(experience.getEndDate()),
							SQL_DATEFORMAT, FROM_DATEFORMAT));

				expto.setCreatedBy(experience.getCreatedBy());
				expto.setCreatedDate(experience.getCreatedDate());
				expto.setOriginalPresent(true);
				expto.setOriginalExperience(experience);
				expto.setActive(experience.getIsActive());
				experienceTos.add(expto);
			}

		}
		if(objForm.getMode()!=null){
		if (objForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			EmpWorkExperienceTO expto = new EmpWorkExperienceTO();
			experienceTos.add(expto);
			objForm.setMode(null);
		}
		}
		employeeTo.setExperiences(experienceTos);

	}

	/**
	 * @param empEducations
	 * @param employeeTo
	 */
	private void convertEducations(Set<EmpEducation> empEducations,
			EmployeeTO employeeTo, EmployeeInfoForm infoForm) throws Exception {

		Iterator<EmpEducation> skillItr = empEducations.iterator();
		List<EmpEducationTO> educationTos = new ArrayList<EmpEducationTO>();
		while (skillItr.hasNext()) {
			EmpEducation education = (EmpEducation) skillItr.next();

			if (education.getIsActive() != null && education.getIsActive()) {
				EmpEducationTO edutionto = new EmpEducationTO();
				if (education.getEmployee() != null)
					edutionto.setEmployee(String.valueOf(education
							.getEmployee().getId()));

				if (education.getEmpEducationMaster() != null) {
					edutionto.setEmpEducationMasterId(String.valueOf(education
							.getEmpEducationMaster().getId()));
					edutionto.setEmpEducationMasterName(education
							.getEmpEducationMaster().getName());
				}

				edutionto.setSpecialisation(education.getSpecialisation());
				if (education.getGpaGrade() != null)
					edutionto.setGpaGrade(String.valueOf(education
							.getGpaGrade()));
				if (education.getYear() != null)
					edutionto.setYear(String.valueOf(education.getYear()));

				if (education.getStartDate() != null)
					edutionto.setStartDate(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(education.getStartDate()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				if (education.getEndDate() != null)
					edutionto.setEndDate(CommonUtil.ConvertStringToDateFormat(
							CommonUtil.getStringDate(education.getEndDate()),
							SQL_DATEFORMAT, FROM_DATEFORMAT));

				edutionto.setCreatedBy(education.getCreatedBy());
				edutionto.setCreatedDate(education.getCreatedDate());
				edutionto.setOriginalPresent(true);
				edutionto.setOriginalEducation(education);
				edutionto.setActive(education.getIsActive());
				educationTos.add(edutionto);
			}

		}
		if(infoForm.getMode()!=null){
		if (infoForm.getMode().equalsIgnoreCase("EAddMore")) {
			// add one blank to add extra one
			EmpEducationTO edutionto = new EmpEducationTO();
			educationTos.add(edutionto);
			infoForm.setMode(null);
		}
		}
		employeeTo.setEducations(educationTos);

	}

	/**
	 * @param empImmigrations
	 * @param employeeTo
	 */
	private void convertImmigrations(Set<EmpImmigration> empImmigrations,
			EmployeeTO employeeTo, boolean admin) throws Exception {

		Iterator<EmpImmigration> skillItr = empImmigrations.iterator();
		List<EmpImmigrationTO> immigrationTos = new ArrayList<EmpImmigrationTO>();
		while (skillItr.hasNext()) {
			EmpImmigration immigration = (EmpImmigration) skillItr.next();

			if (immigration.getIsActive() != null && immigration.getIsActive()) {
				EmpImmigrationTO immigrationto = new EmpImmigrationTO();
				immigrationto.setOriginalPresent(true);
				immigrationto.setOriginalimmigration(immigration);
				immigrationto.setId(String.valueOf(immigration.getId()));

				immigrationto.setActive(immigration.getIsActive());

				immigrationto.setCreatedBy(immigration.getCreatedBy());
				immigrationto.setCreatedDate(immigration.getCreatedDate());
				immigrationto.setEmployeeId(String.valueOf(immigration
						.getEmployee().getId()));

				immigrationto.setPassportComments(immigration
						.getPassportComments());
				immigrationto.setPassportNo(immigration.getPassportNo());
				immigrationto.setPassportReviewStatus(immigration
						.getPassportReviewStatus());
				immigrationto
						.setPassportStatus(immigration.getPassportStatus());
				if (immigration.getCountryByPassportCountryId() != null) {
					immigrationto.setPassportCountryId(String
							.valueOf(immigration
									.getCountryByPassportCountryId().getId()));
					immigrationto.setPassportCountryName(immigration
							.getCountryByPassportCountryId().getName());
				}

				if (immigration.getPassportDateOfExpiry() != null)
					immigrationto.setPassportExpiryDate(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(immigration
											.getPassportDateOfExpiry()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				if (immigration.getPassportIssueDate() != null)
					immigrationto.setPassportIssueDate(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(immigration
											.getPassportIssueDate()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				immigrationto.setVisaComments(immigration.getVisaComments());
				immigrationto.setVisaNo(immigration.getVisaNo());
				immigrationto.setVisaReviewStatus(immigration
						.getVisaReviewStatus());
				immigrationto.setVisaStatus(immigration.getVisaStatus());
				if (immigration.getCountryByVisaCountryId() != null) {
					immigrationto.setVisaCountryId(String.valueOf(immigration
							.getCountryByVisaCountryId().getId()));
					immigrationto.setVisaCountryName(immigration
							.getCountryByVisaCountryId().getName());
				}

				if (immigration.getVisaDateOfExpiry() != null)
					immigrationto.setVisaExpiryDate(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(immigration
											.getVisaDateOfExpiry()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				if (immigration.getVisaIssueDate() != null)
					immigrationto.setVisaIssueDate(CommonUtil
							.ConvertStringToDateFormat(CommonUtil
									.getStringDate(immigration
											.getVisaIssueDate()),
									SQL_DATEFORMAT, FROM_DATEFORMAT));

				immigrationTos.add(immigrationto);
			}

		}
		// if(admin){
		// // add one blank to add extra one
		// EmpImmigrationTO immigrationto = new EmpImmigrationTO();
		// immigrationTos.add(immigrationto);
		//		
		// }
		employeeTo.setImmigrations(immigrationTos);

	}

	/**
	 * @param empSkillses
	 * @param employeeTo
	 * @param admin
	 */
	private void convertSkills(Set<EmpSkills> empSkillses,
			EmployeeTO employeeTo, EmployeeInfoForm infoForm) throws Exception {
		Iterator<EmpSkills> skillItr = empSkillses.iterator();
		List<EmpSkillsTO> skillsTos = new ArrayList<EmpSkillsTO>();
		while (skillItr.hasNext()) {
			EmpSkills skill = (EmpSkills) skillItr.next();

			if (skill.getIsActive() != null && skill.getIsActive()) {
				EmpSkillsTO skillto = new EmpSkillsTO();
				skillto.setOriginalPresent(true);
				skillto.setOriginalSkill(skill);
				skillto.setId(String.valueOf(skill.getId()));

				skillto.setActive(skill.getIsActive());
				skillto.setComments(skill.getComments());
				skillto.setCreatedBy(skill.getCreatedBy());
				skillto.setCreatedDate(skill.getCreatedDate());
				skillto.setEmployeeId(String.valueOf(skill.getEmployee()
						.getId()));
				skillto.setName(skill.getName());
				skillto.setYearOfExperience(skill.getYearOfExperience());
				skillsTos.add(skillto);
			}

		}
		if(infoForm.getMode() != null){
		if (infoForm.getMode().equalsIgnoreCase("AddMore")) {
			// add one blank to add extra one
			EmpSkillsTO skillto = new EmpSkillsTO();
			skillsTos.add(skillto);
			infoForm.setMode(null);
		}
		}
		employeeTo.setSkills(skillsTos);
	}

	/**
	 * @param employeeDetail
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeePersonalBO(EmployeeInfoForm infoForm)
			throws Exception {
		EmployeeTO employeeDetail = infoForm.getEmployeeDetail();
		Employee employee = employeeDetail.getOriginalEmployee();
		if (employee != null) {
			employee.setUid(employeeDetail.getUid());
			employee.setPanNo(employeeDetail.getPanNo());

		/*	employee.setSmoker(employeeDetail.isSmoker());
			employee.setDrivingLicenseNo(employeeDetail.getDrivingLicenseNo());
			employee.setMilitaryService(employeeDetail.getMilitaryService());*/
			if (employeeDetail.getNationalityId() != null
					&& !StringUtils.isEmpty(employeeDetail.getNationalityId())
					&& StringUtils.isNumeric(employeeDetail.getNationalityId())) {
				Nationality nation = new Nationality();
				nation.setId(Integer
						.parseInt(employeeDetail.getNationalityId()));
				employee.setNationality(nation);
			}

			if (employeeDetail.getDob() != null
					&& !StringUtils.isEmpty(employeeDetail.getDob())
					&& CommonUtil.isValidDate(employeeDetail.getDob())) {
				employee.setDob(CommonUtil
						.ConvertStringToSQLDate(employeeDetail.getDob()));
			}
			employee.setMaritalStatus(employeeDetail.getMaritalStatus());
			employee.setGender(employeeDetail.getGender());
		/*	if (employeeDetail.getLicenseExpDate() != null
					&& !StringUtils.isEmpty(employeeDetail.getLicenseExpDate())
					&& CommonUtil.isValidDate(employeeDetail
							.getLicenseExpDate())) {
				employee.setLicenseExpDate(CommonUtil
						.ConvertStringToSQLDate(employeeDetail
								.getLicenseExpDate()));
			}
			employee.setEthinicRace(employeeDetail.getEthinicRace());*/
			if (employeeDetail.getStream() != null
					&& employeeDetail.getStream().trim().length() > 0) {
				EmployeeStreamBO sBO = new EmployeeStreamBO();
				sBO.setId(Integer.parseInt(employeeDetail.getStream()));
				employee.setStreamId(sBO);
			} else {
				employee.setStreamId(null);
			}
			if (employeeDetail.getWorkLocationId() != null
					&& employeeDetail.getWorkLocationId().trim().length() > 0) {
				EmployeeWorkLocationBO ewBo = new EmployeeWorkLocationBO();
				ewBo
						.setId(Integer.parseInt(employeeDetail
								.getWorkLocationId()));
				employee.setWorkLocationId(ewBo);
			} else {
				employee.setWorkLocationId(null);
			}
			if (employeeDetail.getActive() != null
					&& employeeDetail.getActive().trim().length() > 0) {
				if (employeeDetail.getActive().equalsIgnoreCase("yes")) {
					employee.setActive(true);
				} else {
					employee.setActive(false);
				}
			}
			if (employeeDetail.getFingerPrintId() != null
					&& employeeDetail.getFingerPrintId().trim().length() > 0) {
				employee.setFingerPrintId(employeeDetail.getFingerPrintId());
			}

			if (employeeDetail.getFatherName() != null
					&& employeeDetail.getFatherName().trim().length() > 0) {
				employee.setFatherName(employeeDetail.getFatherName());
			}

			if (employeeDetail.getMotherName() != null
					&& employeeDetail.getMotherName().trim().length() > 0) {
				employee.setMotherName(employeeDetail.getMotherName());
			}

			if (employeeDetail.getEmail() != null
					&& employeeDetail.getEmail().trim().length() > 0) {
				employee.setEmail(employeeDetail.getEmail());
			}

			if (employeeDetail.getBloodGroup() != null
					&& employeeDetail.getBloodGroup().trim().length() > 0) {
				employee.setBloodGroup(employeeDetail.getBloodGroup());
			}

			if (employeeDetail.getEmpPhotoName() != null
					&& employeeDetail.getEmpPhotoName().getFileData() != null
					&& employeeDetail.getEmpPhotoName().getFileName() != null
					&& !employeeDetail.getEmpPhotoName().getFileName()
							.isEmpty()
					&& employeeDetail.getEmpPhotoName().getContentType() != null
					&& !employeeDetail.getEmpPhotoName().getContentType()
							.isEmpty()) {
//				employee.setEmpPhoto(employeeDetail.getEmpPhotoName()
//						.getFileData());
				
				Set<EmpImages> images=new HashSet<EmpImages>();
				EmpImages image=new EmpImages();
				image.setEmpPhoto(employeeDetail.getEmpPhotoName() .getFileData());
				image.setCreatedBy(infoForm.getUserId());
				image.setCreatedDate(new Date());
				image.setLastModifiedDate(new Date());
				image.setModifiedBy(infoForm.getUserId());
				images.add(image);
				employee.setEmpImages(images);
			}

//			if (userInfoForm.getEmpPhoto() != null
//					&& userInfoForm.getEmpPhoto().getFileData() != null
//					&& userInfoForm.getEmpPhoto().getFileName() != null
//					&& !userInfoForm.getEmpPhoto().getFileName().isEmpty()
//					&& userInfoForm.getEmpPhoto().getContentType() != null
//					&& !userInfoForm.getEmpPhoto().getContentType().isEmpty()) {
//
//				employee.setEmpPhoto(userInfoForm.getEmpPhoto().getFileData());
//			}

			employee.setModifiedBy(infoForm.getUserId());
			employee.setLastModifiedDate(new Date());
		}
		return employee;
	}

	/**
	 * @param employeeDetail
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeContactBO(EmployeeInfoForm infoForm)
			throws Exception {
		EmployeeTO employeeDetail = infoForm.getEmployeeDetail();
		Employee employee = employeeDetail.getOriginalEmployee();
		if (employee != null) {
			employee.setCommunicationAddressLine1(employeeDetail
					.getCommunicationAddressLine1());
			employee.setCommunicationAddressLine2(employeeDetail
					.getCommunicationAddressLine2());
			employee.setCommunicationAddressCity(employeeDetail
					.getCommunicationAddressCity());
			employee.setCommunicationAddressZip(employeeDetail
					.getCommunicationAddressZip());
			if (employeeDetail.getCommunicationCountryId() != null
					&& !StringUtils.isEmpty(employeeDetail
							.getCommunicationCountryId())
					&& StringUtils.isNumeric(employeeDetail
							.getCommunicationCountryId())) {
				Country cntr = new Country();
				cntr.setId(Integer.parseInt(employeeDetail
						.getCommunicationCountryId()));
				employee.setCountryByCommunicationAddressCountryId(cntr);
			} else {
				employee.setCountryByCommunicationAddressCountryId(null);
			}

			if (employeeDetail.getCommunicationStateId() != null
					&& !StringUtils.isEmpty(employeeDetail
							.getCommunicationStateId())
					&& StringUtils.isNumeric(employeeDetail
							.getCommunicationStateId())) {
				State sts = new State();
				sts.setId(Integer.parseInt(employeeDetail
						.getCommunicationStateId()));
				employee.setStateByCommunicationAddressStateId(sts);
			} else {
				employee.setStateByCommunicationAddressStateId(null);
			}

			employee.setPermanentAddressLine1(employeeDetail
					.getPermanentAddressLine1());
			employee.setPermanentAddressLine2(employeeDetail
					.getPermanentAddressLine2());
			employee.setPermanentAddressCity(employeeDetail
					.getPermanentAddressCity());
			employee.setPermanentAddressZip(employeeDetail
					.getPermanentAddressZip());
			if (employeeDetail.getPermanentCountryId() != null
					&& !StringUtils.isEmpty(employeeDetail
							.getPermanentCountryId())
					&& StringUtils.isNumeric(employeeDetail
							.getPermanentCountryId())) {
				Country cntr = new Country();
				cntr.setId(Integer.parseInt(employeeDetail
						.getPermanentCountryId()));
				employee.setCountryByPermanentAddressCountryId(cntr);
			} else {
				employee.setCountryByPermanentAddressCountryId(null);
			}

			if (employeeDetail.getPermanentStateId() != null
					&& !StringUtils.isEmpty(employeeDetail
							.getPermanentStateId())
					&& StringUtils.isNumeric(employeeDetail
							.getPermanentStateId())) {
				State sts = new State();
				sts.setId(Integer
						.parseInt(employeeDetail.getPermanentStateId()));
				employee.setStateByPermanentAddressStateId(sts);
			} else {
				employee.setStateByPermanentAddressStateId(null);
			}

			employee.setCurrentAddressHomeTelephone1(employeeDetail
					.getCurrentAddressHomeTelephone1());
			employee.setCurrentAddressHomeTelephone2(employeeDetail
					.getCurrentAddressHomeTelephone2());
			employee.setCurrentAddressHomeTelephone3(employeeDetail
					.getCurrentAddressHomeTelephone3());
			employee.setCurrentAddressWorkTelephone1(employeeDetail
					.getCurrentAddressWorkTelephone1());
			employee.setCurrentAddressWorkTelephone2(employeeDetail
					.getCurrentAddressWorkTelephone2());
			employee.setCurrentAddressWorkTelephone3(employeeDetail
					.getCurrentAddressWorkTelephone3());

			employee.setCurrentAddressMobile1(employeeDetail
					.getCurrentAddressMobile1());
		/*	employee.setCurrentAddressMobile2(employeeDetail
					.getCurrentAddressMobile2());
			employee.setCurrentAddressMobile3(employeeDetail
					.getCurrentAddressMobile3());*/

			/*employee.setPermanentAddressHomeTelephone1(employeeDetail
					.getPermanentAddressHomeTelephone1());
			employee.setPermanentAddressHomeTelephone2(employeeDetail
					.getPermanentAddressHomeTelephone2());
			employee.setPermanentAddressHomeTelephone3(employeeDetail
					.getPermanentAddressHomeTelephone3());*/

			employee.setCurrentAddressMobile1(employeeDetail
					.getCurrentAddressMobile1());
			/*employee.setCurrentAddressMobile2(employeeDetail
					.getCurrentAddressMobile2());
			employee.setCurrentAddressMobile3(employeeDetail
					.getCurrentAddressMobile3());

			employee.setPermanentAddressMobile1(employeeDetail
					.getPermanentAddressMobile1());
			employee.setPermanentAddressMobile2(employeeDetail
					.getPermanentAddressMobile2());
			employee.setPermanentAddressMobile3(employeeDetail
					.getPermanentAddressMobile3());*/
			if(employeeDetail.getCommunicationAddressStateOthers()!=null){
				employee.setCommunicationAddressStateOthers(employeeDetail.getCommunicationAddressStateOthers());
			}
			if(employeeDetail.getPermanentAddressStateOthers()!=null){
				employee.setPermanentAddressStateOthers(employeeDetail.getPermanentAddressStateOthers());
			}
			employee.setWorkEmail(employeeDetail.getWorkEmail());
			employee.setOtherEmail(employeeDetail.getOtherEmail());
			employee.setModifiedBy(infoForm.getUserId());
			employee.setLastModifiedDate(new Date());
		}
		return employee;
	}

	/**
	 * @param employeeDetail
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeEmergencyBO(EmployeeInfoForm infoForm)
			throws Exception {
		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null) {
			employee.setEmergencyContName(infoForm.getEmployeeDetail()
					.getEmergencyContName());
			employee.setRelationship(infoForm.getEmployeeDetail()
					.getRelationship());
			employee.setEmergencyHomeTelephone(infoForm.getEmployeeDetail().getEmergencyHomeTelephone1()+"_"+infoForm.getEmployeeDetail().getEmergencyHomeTelephone2()+"_"+infoForm.getEmployeeDetail()
					.getEmergencyHomeTelephone());
			employee.setEmergencyMobile(infoForm.getEmployeeDetail().getEmergencyMobile1()+"_"+infoForm.getEmployeeDetail().getEmergencyMobile2()+"_"+infoForm.getEmployeeDetail()
					.getEmergencyMobile());
			employee.setEmergencyWorkTelephone(infoForm.getEmployeeDetail().getEmergencyWorkTelephone1()+"_"+infoForm.getEmployeeDetail().getEmergencyWorkTelephone2()+"_"+infoForm.getEmployeeDetail()
					.getEmergencyWorkTelephone());
			employee.setModifiedBy(infoForm.getUserId());
			employee.setLastModifiedDate(new Date());
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeSkillBO(EmployeeInfoForm infoForm)
			throws Exception {
		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null
				&& infoForm.getEmployeeDetail().getSkills() != null) {
			Set<EmpSkills> skillSet = new HashSet<EmpSkills>();
			Iterator<EmpSkillsTO> skillItr = infoForm.getEmployeeDetail()
					.getSkills().iterator();
			while (skillItr.hasNext()) {
				EmpSkillsTO skillsTO = (EmpSkillsTO) skillItr.next();
				if (skillsTO.isOriginalPresent()) {
					EmpSkills skillBO = skillsTO.getOriginalSkill();
					skillBO.setName(skillsTO.getName());
					skillBO.setComments(skillsTO.getComments());
					skillBO.setYearOfExperience(skillsTO.getYearOfExperience());
					skillBO.setIsActive(skillsTO.isActive());
					skillBO.setModifiedBy(infoForm.getUserId());
					skillBO.setLastModifiedDate(new Date());
					skillSet.add(skillBO);
				} else if (skillsTO.getName() != null
						&& !StringUtils.isEmpty(skillsTO.getName())) {
					EmpSkills skillBO = new EmpSkills();
					skillBO.setName(skillsTO.getName());
					skillBO.setComments(skillsTO.getComments());
					skillBO.setYearOfExperience(skillsTO.getYearOfExperience());
					skillBO.setIsActive(true);
					skillBO.setCreatedBy(infoForm.getUserId());
					skillBO.setCreatedDate(new Date());
					skillSet.add(skillBO);
				}
			}
			employee.setEmpSkillses(skillSet);
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeImmigrationBO(EmployeeInfoForm infoForm)
			throws Exception {
		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null
				&& infoForm.getEmployeeDetail().getImmigrations() != null) {
			Set<EmpImmigration> immigrationSet = new HashSet<EmpImmigration>();
			Iterator<EmpImmigrationTO> skillItr = infoForm.getEmployeeDetail()
					.getImmigrations().iterator();
			while (skillItr.hasNext()) {
				EmpImmigrationTO immigrationTO = (EmpImmigrationTO) skillItr
						.next();
				EmpImmigration immigrationBO = null;
				if (immigrationTO.isOriginalPresent()) {
					immigrationBO = immigrationTO.getOriginalimmigration();
					immigrationBO.setIsActive(immigrationTO.isActive());
					immigrationBO.setCreatedBy(immigrationTO.getCreatedBy());
					immigrationBO
							.setCreatedDate(immigrationTO.getCreatedDate());
					immigrationBO.setModifiedBy(infoForm.getUserId());
					immigrationBO.setLastModifiedDate(new Date());
				} else {

					immigrationBO = new EmpImmigration();
					immigrationBO.setCreatedBy(infoForm.getUserId());
					immigrationBO.setCreatedDate(new Date());
					immigrationBO.setIsActive(true);
				}
				immigrationBO.setPassportNo(immigrationTO.getPassportNo());
				immigrationBO.setPassportComments(immigrationTO
						.getPassportComments());

				immigrationBO.setPassportReviewStatus(immigrationTO
						.getPassportReviewStatus());
				immigrationBO.setPassportStatus(immigrationTO
						.getPassportStatus());

				if (immigrationTO.getPassportCountryId() != null
						&& !StringUtils.isEmpty(immigrationTO
								.getPassportCountryId())
						&& StringUtils.isNumeric(immigrationTO
								.getPassportCountryId())) {
					Country cntr = new Country();
					cntr.setId(Integer.parseInt(immigrationTO
							.getPassportCountryId()));
					immigrationBO.setCountryByPassportCountryId(cntr);
				}

				if (immigrationTO.getPassportExpiryDate() != null
						&& !StringUtils.isEmpty(immigrationTO
								.getPassportExpiryDate())
						&& CommonUtil.isValidDate(immigrationTO
								.getPassportExpiryDate())) {
					immigrationBO.setPassportDateOfExpiry(CommonUtil
							.ConvertStringToSQLDate(immigrationTO
									.getPassportExpiryDate()));
				}

				if (immigrationTO.getPassportIssueDate() != null
						&& !StringUtils.isEmpty(immigrationTO
								.getPassportIssueDate())
						&& CommonUtil.isValidDate(immigrationTO
								.getPassportIssueDate()))
					immigrationBO.setPassportIssueDate(CommonUtil
							.ConvertStringToSQLDate(immigrationTO
									.getPassportIssueDate()));

				immigrationBO.setVisaNo(immigrationTO.getVisaNo());
				immigrationBO.setVisaComments(immigrationTO.getVisaComments());

				immigrationBO.setVisaReviewStatus(immigrationTO
						.getVisaReviewStatus());
				immigrationBO.setVisaStatus(immigrationTO.getVisaStatus());

				if (immigrationTO.getVisaCountryId() != null
						&& !StringUtils.isEmpty(immigrationTO
								.getVisaCountryId())
						&& StringUtils.isNumeric(immigrationTO
								.getVisaCountryId())) {
					Country cntr = new Country();
					cntr.setId(Integer.parseInt(immigrationTO
							.getVisaCountryId()));
					immigrationBO.setCountryByVisaCountryId(cntr);
				}

				if (immigrationTO.getVisaExpiryDate() != null
						&& !StringUtils.isEmpty(immigrationTO
								.getVisaExpiryDate())
						&& CommonUtil.isValidDate(immigrationTO
								.getVisaExpiryDate())) {
					immigrationBO.setVisaDateOfExpiry(CommonUtil
							.ConvertStringToSQLDate(immigrationTO
									.getVisaExpiryDate()));
				}

				if (immigrationTO.getVisaIssueDate() != null
						&& !StringUtils.isEmpty(immigrationTO
								.getVisaIssueDate())
						&& CommonUtil.isValidDate(immigrationTO
								.getVisaIssueDate()))
					immigrationBO.setVisaIssueDate(CommonUtil
							.ConvertStringToSQLDate(immigrationTO
									.getVisaIssueDate()));

				immigrationSet.add(immigrationBO);

				/*
				 * else if(immigrationTO.getPassportVisaNo()!=null &&
				 * !StringUtils.isEmpty(immigrationTO.getPassportVisaNo())){
				 * EmpImmigration immigrationBO=new EmpImmigration();
				 * immigrationBO
				 * .setPassportVisaNo(immigrationTO.getPassportVisaNo());
				 * immigrationBO.setComments(immigrationTO.getComments());
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * immigrationBO.setPassportVisaNo(immigrationTO.getPassportVisaNo
				 * ());
				 * immigrationBO.setReviewStatus(immigrationTO.getReviewStatus
				 * ()); immigrationBO.setStatus(immigrationTO.getStatus());
				 * 
				 * if(immigrationTO.getCountryId()!=null &&
				 * !StringUtils.isEmpty(immigrationTO.getCountryId()) &&
				 * StringUtils.isNumeric(immigrationTO.getCountryId())){ Country
				 * cntr= new Country();
				 * cntr.setId(Integer.parseInt(immigrationTO.getCountryId()));
				 * immigrationBO.setCountry(cntr); }
				 * 
				 * 
				 * if(immigrationTO.getDateOfExpiry()!=null &&
				 * !StringUtils.isEmpty(immigrationTO.getDateOfExpiry()) &&
				 * CommonUtil.isValidDate(immigrationTO.getDateOfExpiry())){
				 * immigrationBO
				 * .setDateOfExpiry(CommonUtil.ConvertStringToSQLDate
				 * (immigrationTO.getDateOfExpiry())); }
				 * 
				 * 
				 * if(immigrationTO.getIssueDate()!=null &&
				 * !StringUtils.isEmpty(immigrationTO.getIssueDate()) &&
				 * CommonUtil.isValidDate(immigrationTO.getIssueDate()))
				 * immigrationBO
				 * .setIssueDate(CommonUtil.ConvertStringToSQLDate(immigrationTO
				 * .getIssueDate()));
				 * 
				 * 
				 * immigrationBO.setIsActive(true);
				 * immigrationBO.setCreatedBy(infoForm.getUserId());
				 * immigrationBO.setCreatedDate(new Date());
				 * immigrationSet.add(immigrationBO); }
				 */
			}
			employee.setEmpImmigrations(immigrationSet);
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeDependentBO(EmployeeInfoForm infoForm)
			throws Exception {
		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null&& infoForm.getEmployeeDetail().getDependents() != null) 
		{
			Set<EmpDependents> dependentSet = new HashSet<EmpDependents>();
			Iterator<EmpDependentsTO> skillItr = infoForm.getEmployeeDetail().getDependents().iterator();
			while (skillItr.hasNext()) 
			{
				//EmpDependentsTO dependentTO = (EmpDependentsTO) skillItr.next();
				//EmpDependents dependentBO = null;
				/*if(!dependentTO.getDependentName().isEmpty() || !dependentTO.getChild1Name().isEmpty() || !dependentTO.getChild2Name().isEmpty())
				{	
					if (dependentTO.isOriginalPresent()) 
					{
						dependentBO = dependentTO.getOriginalDependent();
						dependentBO.setIsActive(dependentTO.isActive());
						dependentBO.setCreatedBy(dependentTO.getCreatedBy());
						dependentBO.setCreatedDate(dependentTO.getCreatedDate());
						dependentBO.setModifiedBy(infoForm.getUserId());
						dependentBO.setLastModifiedDate(new Date());
					}
					else
					{
	
						dependentBO = new EmpDependents();
						dependentBO.setCreatedBy(infoForm.getUserId());
						dependentBO.setCreatedDate(new Date());
						dependentBO.setIsActive(true);
					}
	
					//dependentBO.setDependentName(dependentTO.getDependentName());
					dependentBO.setDependentRelationship(dependentTO
							.getDependentRelationship());
					dependentBO.setChild1Name(dependentTO.getChild1Name());
					dependentBO.setChild2Name(dependentTO.getChild2Name());
					if (dependentTO.getChild1DateOfBirth() != null
							&& !StringUtils.isEmpty(dependentTO
									.getChild1DateOfBirth())
							&& CommonUtil.isValidDate(dependentTO
									.getChild1DateOfBirth()))
						dependentBO.setChild1DateOfBirth(CommonUtil
								.ConvertStringToSQLDate(dependentTO
										.getChild1DateOfBirth()));
	
					if (dependentTO.getChild2DateOfBirth() != null
							&& !StringUtils.isEmpty(dependentTO
									.getChild2DateOfBirth())
							&& CommonUtil.isValidDate(dependentTO
									.getChild2DateOfBirth()))
						dependentBO.setChild2DateOfBirth(CommonUtil
								.ConvertStringToSQLDate(dependentTO
										.getChild2DateOfBirth()));
	
					dependentSet.add(dependentBO);
				}	*/	
			}
			employee.setEmpDependentses(dependentSet);
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeReportingBO(EmployeeInfoForm infoForm)
			throws Exception {
		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null && infoForm.getEmployeeDetail() != null) {
			if (infoForm.getEmployeeDetail().getReportToId() != null
					&& !StringUtils.isEmpty(infoForm.getEmployeeDetail()
							.getReportToId())
					&& StringUtils.isNumeric(infoForm.getEmployeeDetail()
							.getReportToId())) {
				Employee reportingTo = new Employee();
				reportingTo.setId(Integer.parseInt(infoForm.getEmployeeDetail()
						.getReportToId()));
				employee.setEmployeeByReportToId(reportingTo);
			} else {
				employee.setEmployeeByReportToId(null);
			}

			if (infoForm.getEmployeeDetail().getFinalAuthorityId() != null
					&& !StringUtils.isEmpty(infoForm.getEmployeeDetail()
							.getFinalAuthorityId())
					&& StringUtils.isNumeric(infoForm.getEmployeeDetail()
							.getFinalAuthorityId())) {
				Employee reportingTo = new Employee();
				reportingTo.setId(Integer.parseInt(infoForm.getEmployeeDetail()
						.getFinalAuthorityId()));
				employee.setEmployeeByLeaveApproveAuthId(reportingTo);
			} else {
				employee.setEmployeeByLeaveApproveAuthId(null);
			}
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeEducationBO(EmployeeInfoForm infoForm)
			throws Exception {

		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null
				&& infoForm.getEmployeeDetail().getEducations() != null) {
			Set<EmpEducation> educationSet = new HashSet<EmpEducation>();
			Iterator<EmpEducationTO> skillItr = infoForm.getEmployeeDetail()
					.getEducations().iterator();
			while (skillItr.hasNext()) {
				EmpEducationTO educationTO = (EmpEducationTO) skillItr.next();
				if (educationTO.isOriginalPresent()) {
					EmpEducation educationBO = educationTO
							.getOriginalEducation();

					educationBO.setEmployee(employee);

					if (educationTO.getEmpEducationMasterId() != null
							&& !StringUtils.isEmpty(educationTO
									.getEmpEducationMasterId())
							&& StringUtils.isNumeric(educationTO
									.getEmpEducationMasterId())) {
						EmpEducationMaster master = new EmpEducationMaster();
						master.setId(Integer.parseInt(educationTO
								.getEmpEducationMasterId()));
						educationBO.setEmpEducationMaster(master);
					}

					educationBO.setSpecialisation(educationTO
							.getSpecialisation());
					if (educationTO.getGpaGrade() != null
							&& !StringUtils.isEmpty(educationTO.getGpaGrade())
							&& CommonUtil.isValidDecimal(educationTO
									.getGpaGrade()))
						educationBO.setGpaGrade(new BigDecimal(educationTO
								.getGpaGrade()));
					if (educationTO.getYear() != null
							&& !StringUtils.isEmpty(educationTO.getYear())
							&& StringUtils.isNumeric(educationTO.getYear()))
						educationBO.setYear(Integer.parseInt(educationTO
								.getYear()));

					if (educationTO.getStartDate() != null
							&& !StringUtils.isEmpty(educationTO.getStartDate())
							&& CommonUtil.isValidDate(educationTO
									.getStartDate()))
						educationBO.setStartDate(CommonUtil
								.ConvertStringToSQLDate(educationTO
										.getStartDate()));

					if (educationTO.getEndDate() != null
							&& !StringUtils.isEmpty(educationTO.getEndDate())
							&& CommonUtil.isValidDate(educationTO.getEndDate()))
						educationBO.setEndDate(CommonUtil
								.ConvertStringToSQLDate(educationTO
										.getEndDate()));

					educationBO.setCreatedBy(educationTO.getCreatedBy());
					educationBO.setCreatedDate(educationTO.getCreatedDate());

					educationBO.setIsActive(educationTO.isActive());
					educationBO.setModifiedBy(infoForm.getUserId());
					educationBO.setLastModifiedDate(new Date());
					educationSet.add(educationBO);
				} else if (educationTO.getEmpEducationMasterId() != null
						&& !StringUtils.isEmpty(educationTO
								.getEmpEducationMasterId())) {
					EmpEducation educationBO = new EmpEducation();

					educationBO.setEmployee(employee);

					if (educationTO.getEmpEducationMasterId() != null
							&& !StringUtils.isEmpty(educationTO
									.getEmpEducationMasterId())
							&& StringUtils.isNumeric(educationTO
									.getEmpEducationMasterId())) {
						EmpEducationMaster master = new EmpEducationMaster();
						master.setId(Integer.parseInt(educationTO
								.getEmpEducationMasterId()));
						educationBO.setEmpEducationMaster(master);
					}

					educationBO.setSpecialisation(educationTO
							.getSpecialisation());
					if (educationTO.getGpaGrade() != null
							&& !StringUtils.isEmpty(educationTO.getGpaGrade())
							&& CommonUtil.isValidDecimal(educationTO
									.getGpaGrade()))
						educationBO.setGpaGrade(new BigDecimal(educationTO
								.getGpaGrade()));
					if (educationTO.getYear() != null
							&& !StringUtils.isEmpty(educationTO.getYear())
							&& StringUtils.isNumeric(educationTO.getYear()))
						educationBO.setYear(Integer.parseInt(educationTO
								.getYear()));

					if (educationTO.getStartDate() != null
							&& !StringUtils.isEmpty(educationTO.getStartDate())
							&& CommonUtil.isValidDate(educationTO
									.getStartDate()))
						educationBO.setStartDate(CommonUtil
								.ConvertStringToSQLDate(educationTO
										.getStartDate()));

					if (educationTO.getEndDate() != null
							&& !StringUtils.isEmpty(educationTO.getEndDate())
							&& CommonUtil.isValidDate(educationTO.getEndDate()))
						educationBO.setEndDate(CommonUtil
								.ConvertStringToSQLDate(educationTO
										.getEndDate()));

					educationBO.setIsActive(true);
					educationBO.setCreatedBy(infoForm.getUserId());
					educationBO.setCreatedDate(new Date());
					educationSet.add(educationBO);
				}
			}
			employee.setEmpEducations(educationSet);
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeExperienceBO(EmployeeInfoForm infoForm)
			throws Exception {

		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null
				&& infoForm.getEmployeeDetail().getExperiences() != null) {
			Set<EmpWorkExperience> experienceSet = new HashSet<EmpWorkExperience>();
			Iterator<EmpWorkExperienceTO> skillItr = infoForm
					.getEmployeeDetail().getExperiences().iterator();
			while (skillItr.hasNext()) {
				EmpWorkExperienceTO expTO = (EmpWorkExperienceTO) skillItr
						.next();
				if (expTO.isOriginalPresent()) {
					EmpWorkExperience experienceBO = expTO
							.getOriginalExperience();

					experienceBO.setEmployee(employee);
					experienceBO.setEmployer(expTO.getEmployer());
					experienceBO.setJobTitle(expTO.getJobTitle());
					experienceBO.setComments(expTO.getComments());
					experienceBO.setIsInternal(expTO.isInternal());

					if (expTO.getStartDate() != null
							&& !StringUtils.isEmpty(expTO.getStartDate())
							&& CommonUtil.isValidDate(expTO.getStartDate()))
						experienceBO.setStartDate(CommonUtil
								.ConvertStringToSQLDate(expTO.getStartDate()));

					if (expTO.getEndDate() != null
							&& !StringUtils.isEmpty(expTO.getEndDate())
							&& CommonUtil.isValidDate(expTO.getEndDate()))
						experienceBO.setEndDate(CommonUtil
								.ConvertStringToSQLDate(expTO.getEndDate()));

					experienceBO.setCreatedBy(expTO.getCreatedBy());
					experienceBO.setCreatedDate(expTO.getCreatedDate());

					experienceBO.setIsActive(expTO.isActive());
					experienceBO.setModifiedBy(infoForm.getUserId());
					experienceBO.setLastModifiedDate(new Date());
					experienceSet.add(experienceBO);
				} else if (expTO.getEmployer() != null
						&& !StringUtils.isEmpty(expTO.getEmployer())) {
					EmpWorkExperience experienceBO = new EmpWorkExperience();

					experienceBO.setEmployee(employee);
					experienceBO.setEmployee(employee);
					experienceBO.setEmployer(expTO.getEmployer());
					experienceBO.setJobTitle(expTO.getJobTitle());
					experienceBO.setComments(expTO.getComments());
					experienceBO.setIsInternal(expTO.isInternal());

					if (expTO.getStartDate() != null
							&& !StringUtils.isEmpty(expTO.getStartDate())
							&& CommonUtil.isValidDate(expTO.getStartDate()))
						experienceBO.setStartDate(CommonUtil
								.ConvertStringToSQLDate(expTO.getStartDate()));

					if (expTO.getEndDate() != null
							&& !StringUtils.isEmpty(expTO.getEndDate())
							&& CommonUtil.isValidDate(expTO.getEndDate()))
						experienceBO.setEndDate(CommonUtil
								.ConvertStringToSQLDate(expTO.getEndDate()));

					experienceBO.setIsActive(true);
					experienceBO.setCreatedBy(infoForm.getUserId());
					experienceBO.setCreatedDate(new Date());
					experienceSet.add(experienceBO);
				}
			}
			employee.setEmpWorkExperiences(experienceSet);
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeLanguageBO(EmployeeInfoForm infoForm)
			throws Exception {

		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null
				&& infoForm.getEmployeeDetail().getLanguages() != null) {
			Set<EmpLanguage> languageSet = new HashSet<EmpLanguage>();
			Iterator<EmpLanguagesTO> skillItr = infoForm.getEmployeeDetail()
					.getLanguages().iterator();
			while (skillItr.hasNext()) {
				EmpLanguagesTO langTO = (EmpLanguagesTO) skillItr.next();
				if (langTO.isOriginalPresent()) {
					EmpLanguage langBO = langTO.getOriginalLanguage();

					langBO.setEmployee(employee);
					langBO.setName(langTO.getName());
					langBO.setFluency(langTO.getFluency());

					langBO.setCreatedBy(langTO.getCreatedBy());
					langBO.setCreatedDate(langTO.getCreatedDate());

					langBO.setIsActive(langTO.isActive());
					langBO.setModifiedBy(infoForm.getUserId());
					langBO.setLastModifiedDate(new Date());
					languageSet.add(langBO);
				} else if (langTO.getName() != null
						&& !StringUtils.isEmpty(langTO.getName())) {
					EmpLanguage langBO = new EmpLanguage();

					langBO.setEmployee(employee);
					langBO.setName(langTO.getName());
					langBO.setFluency(langTO.getFluency());
					langBO.setIsActive(true);
					langBO.setCreatedBy(infoForm.getUserId());
					langBO.setCreatedDate(new Date());
					languageSet.add(langBO);
				}
			}
			employee.setEmpLanguages(languageSet);
		}
		return employee;
	}

	/**
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeJobBO(EmployeeInfoForm infoForm)
			throws Exception {
		EmployeeTO employeeDetail = infoForm.getEmployeeDetail();
		Employee employee = infoForm.getEmployeeDetail().getOriginalEmployee();
		if (employee != null && infoForm.getEmployeeDetail().getJobs() != null)
		{
			Set<EmpJob> jobSet = new HashSet<EmpJob>();
			Iterator<EmpJobTO> skillItr = infoForm.getEmployeeDetail().getJobs().iterator();
			while (skillItr.hasNext()) 
			{
				EmpJobTO jobTO = (EmpJobTO) skillItr.next();
				EmpJob jobBO = jobTO.getOriginalJob();
				if (jobBO != null) 
				{
					jobBO.setCreatedBy(jobTO.getCreatedBy());
					jobBO.setCreatedDate(jobTO.getCreatedDate());
					jobBO.setModifiedBy(infoForm.getUserId());
					jobBO.setLastModifiedDate(new Date());
					jobBO.setIsActive(true);
				}
				else 
				{
					jobBO = new EmpJob();
					jobBO.setCreatedBy(infoForm.getUserId());
					jobBO.setCreatedDate(new Date());
					jobBO.setIsActive(true);
				}
				jobBO.setJobTitle(jobTO.getJobTitle());
				jobBO.setEmploymentStatus(jobTO.getEmploymentStatus());
				jobBO.setJobSpecification(jobTO.getJobSpecification());
				jobBO.setJobDuties(jobTO.getJobDuties());
				if (employeeDetail.getEmployeeType() != null && employeeDetail.getEmployeeType().trim().length() > 0) 
				{
					EmployeeTypeBO objBO = new EmployeeTypeBO();
					objBO.setId(Integer.parseInt(employeeDetail.getEmployeeType()));
					jobBO.setObjEmployeeTypeBO(objBO);
				} 
				else 
				{
					jobBO.setObjEmployeeTypeBO(null);
				}

				jobBO.setDepartmentType(jobTO.getDepartmentType());

				if (jobTO.getJoiningDate() != null	&& !StringUtils.isEmpty(jobTO.getJoiningDate())&& CommonUtil.isValidDate(jobTO.getJoiningDate()))
					jobBO.setJoiningDate(CommonUtil.ConvertStringToSQLDate(jobTO.getJoiningDate()));
				if (jobTO.getDateOfRetirement() != null	&& !StringUtils.isEmpty(jobTO.getDateOfRetirement())&& CommonUtil.isValidDate(jobTO.getDateOfRetirement()))
					jobBO.setDateOfRetirement(CommonUtil.ConvertStringToSQLDate(jobTO.getDateOfRetirement()));
				if (jobTO.getDateOfRejoin()  != null && !StringUtils.isEmpty(jobTO.getDateOfRejoin()) && CommonUtil.isValidDate(jobTO.getDateOfRejoin()))
					jobBO.setDateOfRejoin(CommonUtil.ConvertStringToSQLDate(jobTO.getDateOfRejoin()));
				if (jobTO.getBasicPay() != null	&& !StringUtils.isEmpty(jobTO.getBasicPay())&& CommonUtil.isValidDecimal(jobTO.getBasicPay()))
					jobBO.setBasicPay(new BigDecimal(jobTO.getBasicPay()));
				if (jobTO.getGrossPay() != null	&& !StringUtils.isEmpty(jobTO.getGrossPay()) && CommonUtil.isValidDecimal(jobTO.getGrossPay()))
					jobBO.setGrossPay(new BigDecimal(jobTO.getGrossPay()));
				jobBO.setResignationReason(jobTO.getResignationReason());
				if (jobTO.getDateOfResign() != null	&& !StringUtils.isEmpty(jobTO.getDateOfResign())&& CommonUtil.isValidDate(jobTO.getDateOfResign()))
					jobBO.setDateOfResign(CommonUtil.ConvertStringToSQLDate(jobTO.getDateOfResign()));
				if (jobTO.getDateOfLeaving() != null && !StringUtils.isEmpty(jobTO.getDateOfLeaving()) && CommonUtil.isValidDate(jobTO.getDateOfLeaving()))
					jobBO.setDateOfLeaving(CommonUtil.ConvertStringToSQLDate(jobTO.getDateOfLeaving()));
				jobBO.setFinancialAssisstance(jobTO.getFinancialAssisstance());
				// jobBO.setIsActive(jobTO.isActive());

				Set<EmpJobAllowance> allowanceSet = new HashSet<EmpJobAllowance>();
				if (infoForm.getAllowances() != null && !infoForm.getAllowances().isEmpty()) 
				{
					Iterator<EmpJobAllowanceTO> alwitr = infoForm.getAllowances().iterator();
					while (alwitr.hasNext()) 
					{
						EmpJobAllowanceTO allowanceTo = (EmpJobAllowanceTO) alwitr.next();
						EmpJobAllowance jobAllowance = new EmpJobAllowance();
						jobAllowance.setId(allowanceTo.getId());
						jobAllowance.setIsActive(allowanceTo.isActive());
						jobAllowance.setCreatedBy(allowanceTo.getCreatedBy());
						jobAllowance.setCreatedDate(allowanceTo.getCreatedDate());
						if (allowanceTo.getAmount() != null	&& !StringUtils.isEmpty(allowanceTo.getAmount())&& CommonUtil.isValidDecimal(allowanceTo.getAmount()))
							jobAllowance.setAmount(new BigDecimal(allowanceTo.getAmount()));
						if (allowanceTo.getEmpAllowanceId() != null	&& !StringUtils.isEmpty(allowanceTo.getEmpAllowanceId())&& StringUtils.isNumeric(allowanceTo.getEmpAllowanceId())) 
						{
							EmpAllowance allowance = new EmpAllowance();
							allowance.setId(Integer.parseInt(allowanceTo.getEmpAllowanceId()));
							jobAllowance.setEmpAllowance(allowance);
						}
						if (allowanceTo.getEmpJobId() != null&& !StringUtils.isEmpty(allowanceTo.getEmpJobId())	&& StringUtils.isNumeric(allowanceTo.getEmpJobId())) 
						{
							EmpJob allowance = new EmpJob();
							allowance.setId(Integer.parseInt(allowanceTo.getEmpJobId()));
							jobAllowance.setEmpJob(allowance);
						}
						if (allowanceTo.getId() != 0) 
						{
							jobAllowance.setId(allowanceTo.getId());
							jobAllowance.setLastModifiedDate(new Date());
							jobAllowance.setModifiedBy(infoForm.getUserId());
						}
						else 
						{
							jobAllowance.setCreatedDate(new Date());
							jobAllowance.setCreatedBy(infoForm.getUserId());
						}
						allowanceSet.add(jobAllowance);
					}
					jobBO.setEmpJobAllowances(allowanceSet);
				}
				jobSet.add(jobBO);
				// leaves

				Set<EmpLeave> leaves = new HashSet<EmpLeave>();
				if (infoForm.getLeaveTOs() != null	&& !infoForm.getLeaveTOs().isEmpty()) 
				{
					Iterator<EmpLeaveTO> alwitr = infoForm.getLeaveTOs().iterator();
					while (alwitr.hasNext()) 
					{
						EmpLeaveTO empleaveTo = (EmpLeaveTO) alwitr.next();
						EmpLeave empleave = new EmpLeave();
						if (empleaveTo.getId() != 0) 
						{
							empleave.setId(empleaveTo.getId());
							empleave.setLastModifiedDate(new Date());
							empleave.setModifiedBy(infoForm.getUserId());
							empleave.setCreatedBy(empleaveTo.getCreatedBy());
							empleave.setCreatedDate(empleaveTo.getCreatedDate());
							empleave.setIsActive(empleaveTo.isActive());
						} 
						else 
						{
							empleave.setCreatedBy(infoForm.getUserId());
							empleave.setCreatedDate(new Date());
							empleave.setIsActive(true);
						}
						if (empleaveTo.getEmployeeId() != null	&& !StringUtils.isEmpty(empleaveTo.getEmployeeId())	&& StringUtils.isNumeric(empleaveTo.getEmployeeId())) 
						{
							Employee emp = new Employee();
							emp.setId(Integer.parseInt(empleaveTo.getEmployeeId()));
							empleave.setEmployee(emp);
						}

						if (empleaveTo.getEmpLeaveTypeId() != null && !StringUtils.isEmpty(empleaveTo.getEmpLeaveTypeId())&& StringUtils.isNumeric(empleaveTo.getEmpLeaveTypeId())) 
						{
							EmpLeaveType leavetype = new EmpLeaveType();
							leavetype.setId(Integer.parseInt(empleaveTo.getEmpLeaveTypeId()));
							empleave.setEmpLeaveType(leavetype);
						}

						/*if (empleaveTo.getLeavesAllocated() != null	&& !StringUtils.isEmpty(empleaveTo.getLeavesAllocated()) && StringUtils.isNumeric(empleaveTo.getLeavesAllocated()))
							empleave.setLeavesAllocated(Integer.parseInt(empleaveTo.getLeavesAllocated()));
						if (empleaveTo.getLeavesRemaining() != null	&& !StringUtils.isEmpty(empleaveTo.getLeavesRemaining()) && StringUtils.isNumeric(empleaveTo.getLeavesRemaining()))
							empleave.setLeavesRemaining(Integer.parseInt(empleaveTo.getLeavesRemaining()));
						if (empleaveTo.getLeavesSanctioned() != null && !StringUtils.isEmpty(empleaveTo.getLeavesSanctioned())&& StringUtils.isNumeric(empleaveTo.getLeavesSanctioned()))
							empleave.setLeavesSanctioned(Integer.parseInt(empleaveTo.getLeavesSanctioned()));*/
						leaves.add(empleave);
					}
				}
				employee.setEmpLeaves(leaves);

				// achievements

				Set<EmpAcheivement> achievements = new HashSet<EmpAcheivement>();
				if (infoForm.getAchievementTOs() != null
						&& !infoForm.getAchievementTOs().isEmpty()) {
					Iterator<EmpAcheivementTO> alwitr = infoForm
							.getAchievementTOs().iterator();
					while (alwitr.hasNext()) {
						EmpAcheivementTO empachieveTo = (EmpAcheivementTO) alwitr
								.next();
						EmpAcheivement empachieve = new EmpAcheivement();
						if (empachieveTo.getId() != 0) {
							empachieve.setId(empachieveTo.getId());
							empachieve.setLastModifiedDate(new Date());
							empachieve.setModifiedBy(infoForm.getUserId());
							empachieve
									.setCreatedBy(empachieveTo.getCreatedBy());
							empachieve.setCreatedDate(empachieveTo
									.getCreatedDate());
							empachieve.setIsActive(empachieveTo.isActive());
						} else {
							empachieve.setCreatedBy(infoForm.getUserId());
							empachieve.setCreatedDate(new Date());
							empachieve.setIsActive(true);
						}

						if (empachieveTo.getEmployeeId() != null
								&& !StringUtils.isEmpty(empachieveTo
										.getEmployeeId())
								&& StringUtils.isNumeric(empachieveTo
										.getEmployeeId())) {
							Employee emp = new Employee();
							emp.setId(Integer.parseInt(empachieveTo
									.getEmployeeId()));
							empachieve.setEmployee(emp);

						}

						empachieve.setAcheivementName(empachieveTo
								.getAcheivementName());
						empachieve.setDetails(empachieveTo.getDetails());
						empachieve.setStatus(empachieveTo.getStatus());

						achievements.add(empachieve);

					}
				}

				employee.setEmpAcheivements(achievements);

				employee.setEmpJobs(jobSet);
				if (infoForm.getEmployeeDetail() != null) {
					if (infoForm.getEmployeeDetail().getStream() != null
							&& infoForm.getEmployeeDetail().getStream().trim()
									.length() > 0) {

						EmployeeStreamBO esBO = new EmployeeStreamBO();
						esBO.setId(Integer.parseInt(infoForm
								.getEmployeeDetail().getStream()));
						employee.setStreamId(esBO);
					}

					if (infoForm.getEmployeeDetail().getDepartmentId() != null
							&& infoForm.getEmployeeDetail().getDepartmentId()
									.trim().length() > 0) {
						Department dbo = new Department();
						dbo.setId(Integer.parseInt(infoForm.getEmployeeDetail()
								.getDepartmentId()));
						employee.setDepartment(dbo);
					}

					if (infoForm.getEmployeeDetail().getDesignationId() != null
							&& infoForm.getEmployeeDetail().getDesignationId()
									.trim().length() > 0) {
						Designation objde = new Designation();
						objde.setId(Integer.parseInt(infoForm
								.getEmployeeDetail().getDesignationId()));
						employee.setDesignation(objde);
					}
					if (infoForm.getEmployeeDetail().getWorkLocationId() != null
							&& infoForm.getEmployeeDetail().getWorkLocationId()
									.trim().length() > 0) {
						EmployeeWorkLocationBO ewlBO = new EmployeeWorkLocationBO();
						ewlBO.setId(Integer.parseInt(infoForm
								.getEmployeeDetail().getWorkLocationId()));
						employee.setWorkLocationId(ewlBO);
					}

				}

			}
		}
		return employee;

	}

	/**
	 * @return
	 */
	public List<EmpAllowanceTO> getAllowanceMasters() throws Exception {
		List<EmpAllowanceTO> allowanceTos = new ArrayList<EmpAllowanceTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		List<EmpAllowance> allowanceBos = txn.getEmpAllowances();
		if (allowanceBos != null) {
			EmpAllowance allowance = null;
			Iterator<EmpAllowance> alwItr = allowanceBos.iterator();
			while (alwItr.hasNext()) {
				EmpAllowanceTO allowanceTo = new EmpAllowanceTO();
				allowance = (EmpAllowance) alwItr.next();
				allowanceTo.setId(allowance.getId());
				allowanceTo.setName(allowance.getName());
				allowanceTos.add(allowanceTo);
			}
		}
		return allowanceTos;
	}

	/*
	 * @return
	 */
	public List<EmpLeaveTypeTO> getLeaveTypes() throws Exception {
		List<EmpLeaveTypeTO> leaveTos = new ArrayList<EmpLeaveTypeTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		List<EmpLeaveType> leaveBos = txn.getEmployeeLeaveType();
		if (leaveBos != null) {
			EmpLeaveType leave = null;
			Iterator<EmpLeaveType> alwItr = leaveBos.iterator();
			while (alwItr.hasNext()) {
				EmpLeaveTypeTO leaveTo = new EmpLeaveTypeTO();
				leave = (EmpLeaveType) alwItr.next();
				leaveTo.setId(leave.getId());
				leaveTo.setName(leave.getName());
				leaveTos.add(leaveTo);
			}
		}
		return leaveTos;
	}

	/**
	 * @param reportingtos
	 * @param allEmployees
	 * @throws Exception
	 */
	public void convertForReportingTos(List<EmployeeTO> reportingtos,
			List<Employee> allEmployees) throws Exception {
		if (allEmployees != null) {
			Iterator<Employee> empItr = allEmployees.iterator();
			while (empItr.hasNext()) {
				Employee employee = (Employee) empItr.next();
				EmployeeTO empTo = new EmployeeTO();
				empTo.setId(employee.getId());
				StringBuffer nameBuff = new StringBuffer();
				if (employee.getFirstName() != null) {
					nameBuff.append(employee.getFirstName());
				}
				if (employee.getLastName() != null) {
					nameBuff.append(" ");
					nameBuff.append(employee.getLastName());
				}
				if(employee.getCode() != null){
					nameBuff.append("(");
					nameBuff.append(employee.getCode());
					nameBuff.append(")");
				}
				empTo.setFirstName(nameBuff.toString());
				reportingtos.add(empTo);
			}
		}

	}

	public ArrayList<EmployeeInformactionTO> convertBoToTO(
			List<Object[]> employeeInfo) {
		ArrayList<EmployeeInformactionTO> list = new ArrayList<EmployeeInformactionTO>();
		if (employeeInfo != null && !employeeInfo.isEmpty()) {
			EmployeeInformactionTO objTO = null;
			Iterator<Object[]> itr = employeeInfo.iterator();
			while (itr.hasNext()) {
				objTO = new EmployeeInformactionTO();
				Object[] obj = (Object[]) itr.next();
				if (obj[0] != null) {
					objTO.setCode(obj[0].toString());
				}
				if (obj[1] != null) {
					objTO.setFirstName(obj[1].toString());
				}
				if (obj[2] != null) {
					objTO.setMiddleName(obj[2].toString());
				}
				if (obj[3] != null) {
					objTO.setLastName(obj[3].toString());
				}
				if (obj[4] != null) {
					objTO.setId(Integer.parseInt(obj[4].toString()));
				}
				list.add(objTO);
			}
		}
		return list;
	}

	public List<EmployeeStreamTO> getEmployeeStreamMasters() throws Exception {
		List<EmployeeStreamTO> objStream = new ArrayList<EmployeeStreamTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		List<EmployeeStreamBO> listBo = txn.getEmployeeStream();
		if (listBo != null) {
			EmployeeStreamBO objBO = null;
			Iterator<EmployeeStreamBO> itr = listBo.iterator();
			while (itr.hasNext()) {
				EmployeeStreamTO objTo = new EmployeeStreamTO();
				objBO = (EmployeeStreamBO) itr.next();
				objTo.setId(objBO.getId());
				objTo.setName(objBO.getName());
				objStream.add(objTo);
			}
		}
		return objStream;
	}

	public List<EmployeeKeyValueTO> getDepartmentMasters() throws Exception {
		List<EmployeeKeyValueTO> listDepartment = new ArrayList<EmployeeKeyValueTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		List<Department> listBo = txn.getDepartmentFields();
		if (listBo != null) {
			Department objBO = null;
			Iterator<Department> itr = listBo.iterator();
			while (itr.hasNext()) {
				EmployeeKeyValueTO objTo = new EmployeeKeyValueTO();
				objBO = (Department) itr.next();
				objTo.setId(objBO.getId());
				objTo.setName(objBO.getName());
				listDepartment.add(objTo);
			}
		}
		return listDepartment;
	}

	public List<EmployeeKeyValueTO> getDesignationMasters() throws Exception {
		List<EmployeeKeyValueTO> listDesignation = new ArrayList<EmployeeKeyValueTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();

		List<Designation> listBo = txn.getDesignationFields();
		if (listBo != null) {
			Designation objBO = null;
			Iterator<Designation> itr = listBo.iterator();
			while (itr.hasNext()) {
				EmployeeKeyValueTO objTo = new EmployeeKeyValueTO();
				objBO = (Designation) itr.next();
				objTo.setId(objBO.getId());
				objTo.setName(objBO.getName());
				listDesignation.add(objTo);
			}
		}
		return listDesignation;
	}

	public List<EmployeeKeyValueTO> getWorkLocationMasters() throws Exception {
		List<EmployeeKeyValueTO> listWorkLocation = new ArrayList<EmployeeKeyValueTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();

		List<EmployeeWorkLocationBO> listBo = txn
				.getEmployeeWorkLocationStream();
		if (listBo != null) {
			EmployeeWorkLocationBO objBO = null;
			Iterator<EmployeeWorkLocationBO> itr = listBo.iterator();
			while (itr.hasNext()) {
				EmployeeKeyValueTO objTo = new EmployeeKeyValueTO();
				objBO = (EmployeeWorkLocationBO) itr.next();
				objTo.setId(objBO.getId());
				objTo.setName(objBO.getName());
				listWorkLocation.add(objTo);
			}
		}
		return listWorkLocation;
	}

	public List<EmployeeKeyValueTO> getEmployeeType() throws Exception {
		List<EmployeeKeyValueTO> listWorkLocation = new ArrayList<EmployeeKeyValueTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();

		List<EmployeeTypeBO> listBo = txn.getEmployeeType();
		if (listBo != null) {
			EmployeeTypeBO objBO = null;
			Iterator<EmployeeTypeBO> itr = listBo.iterator();
			while (itr.hasNext()) {
				EmployeeKeyValueTO objTo = new EmployeeKeyValueTO();
				objBO = (EmployeeTypeBO) itr.next();
				objTo.setId(objBO.getId());
				objTo.setName(objBO.getName());
				listWorkLocation.add(objTo);
			}
		}
		return listWorkLocation;
	}

	public List<EmployeeKeyValueTO> getlistRoles() throws Exception {
		List<EmployeeKeyValueTO> listRoles = new ArrayList<EmployeeKeyValueTO>();
		ISingleFieldMasterTransaction txn = new SingleFieldMasterTransactionImpl();
		List<Roles> listBo = txn.getRolesFields();

		if (listBo != null) {
			Roles objBO = null;
			Iterator<Roles> itr = listBo.iterator();
			while (itr.hasNext()) {
				EmployeeKeyValueTO objTo = new EmployeeKeyValueTO();
				objBO = (Roles) itr.next();
				objTo.setId(objBO.getId());
				objTo.setName(objBO.getName());
				listRoles.add(objTo);
			}
		}

		return listRoles;
	}

	public Users convertFormToUserBO(EmployeeInfoForm objForm) throws Exception {
		Users users = new Users();
		Employee employee = new Employee();
		
		if (objForm.getEmployeeDetail().getMiddleName() != null
				&& !objForm.getEmployeeDetail().getMiddleName().isEmpty()) {

			employee.setMiddleName(objForm.getEmployeeDetail().getMiddleName());
		}
		if (objForm.getEmployeeDetail().getLastName() != null
				&& !objForm.getEmployeeDetail().getLastName().isEmpty()) {
			employee.setLastName(objForm.getEmployeeDetail().getLastName());
		}
		employee.setFirstName(objForm.getEmployeeDetail().getFirstName());
		employee.setCreatedDate(new Date());
		employee.setCode(objForm.getEmployeeDetail().getCode());
		employee.setIsActive(true);
		employee.setCreatedBy(objForm.getUserId());
		employee.setModifiedBy(objForm.getUserId());
	//	employee.setNickName(objForm.getEmployeeDetail().getNickName());
		users.setEmployee(employee);
		users.setCreatedDate(new Date());
		users.setLastModifiedDate(new Date());
		users.setCreatedBy(objForm.getUserId());
		users.setModifiedBy(objForm.getUserId());
		users.setIsActive(true);
		// users.setIsAddRemarks(objForm.getEmployeeDetail().getIsAddRemarks());
		// users.setIsViewRemarks(objForm.getEmployeeDetail().getIsViewRemarks());

		return users;
	}

	public Users convertFormToUserBO(int employeeId, int userId,
			String userName, String password, String roleId,
			boolean isAddRemarks, boolean isViewRemarks,String firstName,String lastName,
			String middleName,String nickName,boolean isTeachingStaff,String code) throws Exception {
		Users users = new Users();
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		
		Employee employee = txn.getEmployeeDetailsByEmployeeId(employeeId);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setMiddleName(middleName);
	//	employee.setNickName(nickName);
		employee.setCode(code);
		users.setId(userId);
		
		users.setEmployee(employee);
		if (userName != null && userName.trim().length() > 0) {
			users.setUserName(userName);
		}

		if (password != null && password.trim().length() > 0) {
			users.setPwd(EncryptUtil.getInstance().encryptDES(password));
			users.setAndroidPwd(Base64Coder.encodeString(password));
		}
		if (roleId != null && roleId.trim().length() > 0) {
			Roles roles = new Roles();
			roles.setId(Integer.parseInt(roleId));
			users.setRoles(roles);
		}

		users.setIsAddRemarks(isAddRemarks);
		users.setIsViewRemarks(isViewRemarks);
		users.setIsTeachingStaff(isTeachingStaff);
		users.setIsActive(true);
		return users;
	}
	
	public EmployeeTO convertUserBoToEmployeeTO(EmployeeTO employeeTo,
			Users user, EmployeeInfoForm infoForm) throws Exception {
		int i=0;
		if (employeeTo == null)
			employeeTo = infoForm.getEmployeeDetail();
		employeeTo.setOriginalEmployee(user.getEmployee());
		if(user.getUserName()!=null && user.getPwd()!=null)
		{	
			employeeTo.setUserName(user.getUserName());
			employeeTo.setPassword(EncryptUtil.getInstance().decryptDES(user.getPwd()));
		}	
		employeeTo.setId(user.getEmployee().getId());
		employeeTo.setFirstName(user.getEmployee().getFirstName());
		employeeTo.setLastName(user.getEmployee().getLastName());
	//	employeeTo.setNickName(user.getEmployee().getNickName());
		employeeTo.setMiddleName(user.getEmployee().getMiddleName());
		employeeTo.setNewFirstName(user.getEmployee().getFirstName());
		employeeTo.setNewLastName(user.getEmployee().getLastName());
	//	employeeTo.setNewNickName(user.getEmployee().getNickName());
		employeeTo.setNewMiddleName(user.getEmployee().getMiddleName());
		employeeTo.setCode(user.getEmployee().getCode());
		employeeTo.setNewCode(user.getEmployee().getCode());
		if(user.getRoles()!=null)
			employeeTo.setRoleId(""+user.getRoles().getId());
		else
			employeeTo.setRoleId(null);
		employeeTo.setUid(user.getEmployee().getUid());
		employeeTo.setPanNo(user.getEmployee().getPanNo());

		/*if (user.getEmployee().getSmoker() != null && user.getEmployee().getSmoker()) {
			employeeTo.setSmoker(false);
			employeeTo.setTempsmoker(true);
		} else {
			employeeTo.setSmoker(false);
			employeeTo.setTempsmoker(false);
		}*/
		employeeTo.setIsTeachingStaff(user.getIsTeachingStaff());
		//employeeTo.setDrivingLicenseNo(user.getEmployee().getDrivingLicenseNo());
	//	employeeTo.setMilitaryService(user.getEmployee().getMilitaryService());
		if (user.getEmployee().getNationality() != null) {
			employeeTo.setNationalityId(String.valueOf(user.getEmployee()
					.getNationality().getId()));
			employeeTo.setNationalityName(user.getEmployee().getNationality().getName());
		}
		else
		{
			employeeTo.setNationalityId(null);
			employeeTo.setNationalityName(null);
		}

		if (user.getEmployee().getDob() != null) {
			employeeTo.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil
					.getStringDate(user.getEmployee().getDob()), SQL_DATEFORMAT,
					FROM_DATEFORMAT));
		}
		else
		{
			employeeTo.setDob(null);
		}
		if (user.getEmployee().getActive()) {
			employeeTo.setActive("Yes");
			employeeTo.setActiveDummy("Yes");
		} else {
			employeeTo.setActive("No");
			employeeTo.setActiveDummy("No");
		}
		if (user.getEmployee().getFingerPrintId() != null) {
			employeeTo.setFingerPrintId(user.getEmployee().getFingerPrintId());
		}
		else
		{
			employeeTo.setFingerPrintId(null);
		}
		if (user.getEmployee().getFatherName() != null) {
			employeeTo.setFatherName(user.getEmployee().getFatherName());
		}
		else
		{
			employeeTo.setFatherName(null);
		}
		if (user.getEmployee().getMotherName() != null) {
			employeeTo.setMotherName(user.getEmployee().getMotherName());
		}
		else
		{
			employeeTo.setMotherName(null);
		}
		if (user.getEmployee().getEmail() != null) {
			employeeTo.setEmail(user.getEmployee().getEmail());
		}
		else
		{
			employeeTo.setEmail(null);
		}

		if (user.getEmployee().getBloodGroup() != null) {
			employeeTo.setBloodGroup(user.getEmployee().getBloodGroup());
		}
		else
			employeeTo.setBloodGroup(null);	
		employeeTo.setMaritalStatus(user.getEmployee().getMaritalStatus());
		employeeTo.setGender(user.getEmployee().getGender());
		/*if (user.getEmployee().getLicenseExpDate() != null) {
			employeeTo.setLicenseExpDate(CommonUtil.ConvertStringToDateFormat(
					CommonUtil.getStringDate(user.getEmployee().getLicenseExpDate()),
					SQL_DATEFORMAT, FROM_DATEFORMAT));
		}
		else
			employeeTo.setLicenseExpDate(null);
		employeeTo.setEthinicRace(user.getEmployee().getEthinicRace());
		*/// communication contact details
		employeeTo.setCommunicationAddressLine1(user.getEmployee()
				.getCommunicationAddressLine1());
		employeeTo.setCommunicationAddressLine2(user.getEmployee()
				.getCommunicationAddressLine2());
		employeeTo.setCommunicationAddressCity(user.getEmployee()
				.getCommunicationAddressCity());
		employeeTo.setCommunicationAddressZip(user.getEmployee()
				.getCommunicationAddressZip());
		if (user.getEmployee().getCountryByCommunicationAddressCountryId() != null) {
			employeeTo.setCommunicationCountryId(String.valueOf(user.getEmployee()
					.getCountryByCommunicationAddressCountryId().getId()));
			employeeTo.setCommunicationCountryName(user.getEmployee()
					.getCountryByCommunicationAddressCountryId().getName());
		}
		else
		{
			employeeTo.setCommunicationCountryId(null);
			employeeTo.setCommunicationCountryName(null);
		}

		if (user.getEmployee().getStateByCommunicationAddressStateId() != null) {
			employeeTo.setCommunicationStateId(String.valueOf(user.getEmployee()
					.getStateByCommunicationAddressStateId().getId()));
			employeeTo.setCommunicationStateName(user.getEmployee()
					.getStateByCommunicationAddressStateId().getName());
		}
		else
		{
			employeeTo.setCommunicationStateId("Other");
			//employeeTo.setCommunicationStateName(user.getEmployee().getCommunicationAddressStateOthers());
			employeeTo.setCommunicationAddressStateOthers(user.getEmployee().getCommunicationAddressStateOthers());
		}

		// permanent contact details
		employeeTo
				.setPermanentAddressLine1(user.getEmployee().getPermanentAddressLine1());
		employeeTo
				.setPermanentAddressLine2(user.getEmployee().getPermanentAddressLine2());
		employeeTo.setPermanentAddressCity(user.getEmployee().getPermanentAddressCity());
		employeeTo.setPermanentAddressZip(user.getEmployee().getPermanentAddressZip());
		if (user.getEmployee().getCountryByPermanentAddressCountryId() != null) {
			employeeTo.setPermanentCountryId(String.valueOf(user.getEmployee()
					.getCountryByPermanentAddressCountryId().getId()));
			employeeTo.setPermanentCountryName(user.getEmployee()
					.getCountryByPermanentAddressCountryId().getName());
		}
		else
		{
			employeeTo.setPermanentCountryId(null);
			employeeTo.setPermanentCountryName(null);
		}

		if (user.getEmployee().getStateByPermanentAddressStateId() != null) {
			employeeTo.setPermanentStateId(String.valueOf(user.getEmployee()
					.getStateByPermanentAddressStateId().getId()));
			employeeTo.setPermanentStateName(user.getEmployee()
					.getStateByPermanentAddressStateId().getName());
		}
		else
		{
			employeeTo.setPermanentStateId("Other");
			//employeeTo.setPermanentStateName(user.getEmployee().getPermanentAddressStateOthers());
			employeeTo.setPermanentAddressStateOthers(user.getEmployee().getPermanentAddressStateOthers());
		}

		employeeTo.setCurrentAddressHomeTelephone1(user.getEmployee()
				.getCurrentAddressHomeTelephone1());
		employeeTo.setCurrentAddressHomeTelephone2(user.getEmployee()
				.getCurrentAddressHomeTelephone2());
		employeeTo.setCurrentAddressHomeTelephone3(user.getEmployee()
				.getCurrentAddressHomeTelephone3());
		employeeTo.setCurrentAddressWorkTelephone1(user.getEmployee()
				.getCurrentAddressWorkTelephone1());
		employeeTo.setCurrentAddressWorkTelephone2(user.getEmployee()
				.getCurrentAddressWorkTelephone2());
		employeeTo.setCurrentAddressWorkTelephone3(user.getEmployee()
				.getCurrentAddressWorkTelephone3());

		employeeTo
				.setCurrentAddressMobile1(user.getEmployee().getCurrentAddressMobile1());
		/*employeeTo
				.setCurrentAddressMobile2(user.getEmployee().getCurrentAddressMobile2());
		employeeTo
				.setCurrentAddressMobile3(user.getEmployee().getCurrentAddressMobile3());

		employeeTo.setPermanentAddressHomeTelephone1(user.getEmployee()
				.getPermanentAddressHomeTelephone1());
		employeeTo.setPermanentAddressHomeTelephone2(user.getEmployee()
				.getPermanentAddressHomeTelephone2());
		employeeTo.setPermanentAddressHomeTelephone3(user.getEmployee()
				.getPermanentAddressHomeTelephone3());
*/
		employeeTo
				.setCurrentAddressMobile1(user.getEmployee().getCurrentAddressMobile1());
		/*employeeTo
				.setCurrentAddressMobile2(user.getEmployee().getCurrentAddressMobile2());
		employeeTo
				.setCurrentAddressMobile3(user.getEmployee().getCurrentAddressMobile3());
*/
		/*employeeTo.setPermanentAddressMobile1(user.getEmployee()
				.getPermanentAddressMobile1());
		employeeTo.setPermanentAddressMobile2(user.getEmployee()
				.getPermanentAddressMobile2());
		employeeTo.setPermanentAddressMobile3(user.getEmployee()
				.getPermanentAddressMobile3());
*/
		employeeTo.setWorkEmail(user.getEmployee().getWorkEmail());
		employeeTo.setOtherEmail(user.getEmployee().getOtherEmail());
		// emergency contact details
		employeeTo.setEmergencyContName(user.getEmployee().getEmergencyContName());
		employeeTo.setRelationship(user.getEmployee().getRelationship());
		if(user.getEmployee().getEmergencyHomeTelephone()!=null){
			String emgPhone= user.getEmployee().getEmergencyHomeTelephone();
			String emgPhone1[]= emgPhone.split("_");
			for(i=0;i<emgPhone1.length;i++){
				if(i==0){
					if(emgPhone1[0]!=null)
					employeeTo.setEmergencyHomeTelephone1(emgPhone1[0]);
				}
				if(i==1){
					if(emgPhone1[1]!=null)
					employeeTo.setEmergencyHomeTelephone2(emgPhone1[1]);
				}
				if(i==2){
					if(emgPhone1[2]!=null)
					employeeTo.setEmergencyHomeTelephone(emgPhone1[2]);
				}
			}
		}
		if(user.getEmployee().getEmergencyMobile()!=null){
			 String emgMPhone =user.getEmployee().getEmergencyMobile();
			 String emgPhone2[] = emgMPhone.split("_");
			 for(i=0;i<emgPhone2.length;i++){
				 if(i==0){
					 if(emgPhone2[0]!=null)
						employeeTo.setEmergencyMobile1(emgPhone2[0]);
				 }
				if(i==1){
					if(emgPhone2[1]!=null)
					employeeTo.setEmergencyMobile2(emgPhone2[1]);
				}
				if(i==2){
					if(emgPhone2[2]!=null)
					employeeTo.setEmergencyMobile(emgPhone2[2]);
				}
			}
		}
		if(user.getEmployee().getEmergencyWorkTelephone()!=null){
			 String emgWPhone=user.getEmployee().getEmergencyWorkTelephone();
			 String emgPhone3[] = emgWPhone.split("_");
			 for(i=0;i<emgPhone3.length;i++){
				 if(i==0){
				 	 if(emgPhone3[0]!=null)
				 		 employeeTo.setEmergencyWorkTelephone1(emgPhone3[0]);
					}
				 if(i==1){
					 if(emgPhone3[1]!=null)
						 employeeTo.setEmergencyWorkTelephone2(emgPhone3[1]);
					}
				 if(i==2){
					 if(emgPhone3[2]!=null)
						 employeeTo.setEmergencyWorkTelephone(emgPhone3[2]);
				 	}
			 	}
		}
		// skills
		if (user.getEmployee().getEmpSkillses() != null
				&& !user.getEmployee().getEmpSkillses().isEmpty()) {
			convertSkills(user.getEmployee().getEmpSkillses(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpSkillsTO> skillsTos = new ArrayList<EmpSkillsTO>();
				skillsTos.add(new EmpSkillsTO());
				employeeTo.setSkills(skillsTos);
			}
		}

		// immigrations
		if (user.getEmployee().getEmpImmigrations() != null
				&& !user.getEmployee().getEmpImmigrations().isEmpty()) {
			convertImmigrations(user.getEmployee().getEmpImmigrations(), employeeTo,
					infoForm.isAdminUser());
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpImmigrationTO> immigrationsTos = new ArrayList<EmpImmigrationTO>();
				immigrationsTos.add(new EmpImmigrationTO());
				employeeTo.setImmigrations(immigrationsTos);
			}
		}

		// educations
		if (user.getEmployee().getEmpEducations() != null
				&& !user.getEmployee().getEmpEducations().isEmpty()) {
			convertEducations(user.getEmployee().getEmpEducations(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpEducationTO> educationsTos = new ArrayList<EmpEducationTO>();
				educationsTos.add(new EmpEducationTO());
				employeeTo.setEducations(educationsTos);
			}
		}

		// work experiences
		if (user.getEmployee().getEmpWorkExperiences() != null
				&& !user.getEmployee().getEmpWorkExperiences().isEmpty()) {
			convertExperiences(user.getEmployee().getEmpWorkExperiences(), employeeTo,infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpWorkExperienceTO> experiencesTos = new ArrayList<EmpWorkExperienceTO>();
				experiencesTos.add(new EmpWorkExperienceTO());
				employeeTo.setExperiences(experiencesTos);
			}
		}

		// languages
		if (user.getEmployee().getEmpLanguages() != null
				&& !user.getEmployee().getEmpLanguages().isEmpty()) {
			convertLanguages(user.getEmployee().getEmpLanguages(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpLanguagesTO> languageTos = new ArrayList<EmpLanguagesTO>();
				languageTos.add(new EmpLanguagesTO());
				employeeTo.setLanguages(languageTos);
			}
		}
		// Job
		if (user.getEmployee().getEmpJobs() != null && !user.getEmployee().getEmpJobs().isEmpty()) {
			convertJobs(user.getEmployee().getEmpJobs(), employeeTo, infoForm);
		} else {
			if (infoForm.isAdminUser()) 
			{
				if (infoForm.getAllowanceTos() != null) 
				{
					List<EmpJobAllowanceTO> allowanceTos = new ArrayList<EmpJobAllowanceTO>();
					Iterator<EmpAllowanceTO> allItr = infoForm.getAllowanceTos().iterator();
					while (allItr.hasNext()) 
					{
						EmpAllowanceTO allowanceTO = (EmpAllowanceTO) allItr.next();
						EmpJobAllowanceTO jobAllwnTo = new EmpJobAllowanceTO();
						jobAllwnTo.setEmpAllowanceId(String.valueOf(allowanceTO.getId()));
						jobAllwnTo.setEmpAllowanceName(allowanceTO.getName());
						allowanceTos.add(jobAllwnTo);
					}
					infoForm.setAllowances(allowanceTos);
				}
				
				List<EmpJobTO> jobTos = new ArrayList<EmpJobTO>();
				jobTos.add(new EmpJobTO());
				employeeTo.setJobs(jobTos);
			}
		}
		if (user.getEmployee().getStreamId() != null) {
			employeeTo.setStream(Integer.toString(user.getEmployee().getStreamId()
					.getId()));
		}
		else
			employeeTo.setStream(null);

		if (user.getEmployee().getWorkLocationId() != null) {
			employeeTo.setWorkLocationId(Integer.toString(user.getEmployee()
					.getWorkLocationId().getId()));
		}
		else
			employeeTo.setWorkLocationId(null);
		if (user.getEmployee().getDepartment() != null
				&& user.getEmployee().getDepartment().getId() != 0) {
			employeeTo.setDepartmentId(Integer.toString(user.getEmployee()
					.getDepartment().getId()));
		}
		else
			employeeTo.setDepartmentId(null);
		if (user.getEmployee().getDesignation() != null
				&& user.getEmployee().getDesignation().getId() != 0) {
			employeeTo.setDesignationId(Integer.toString(user.getEmployee()
					.getDesignation().getId()));
		}
		else
			employeeTo.setDesignationId(null);
		// leave
		convertLeaves(user.getEmployee().getEmpLeaves(), employeeTo, infoForm);

		// Achievements
		convertAchievements(user.getEmployee().getEmpAcheivements(), employeeTo, infoForm);

		// dependents
		if (user.getEmployee().getEmpDependentses() != null
				&& !user.getEmployee().getEmpDependentses().isEmpty()) {
			convertDependents(user.getEmployee().getEmpDependentses(), employeeTo,
					infoForm.isAdminUser());
		} else {
			if (infoForm.isAdminUser()) {
				List<EmpDependentsTO> dependentTos = new ArrayList<EmpDependentsTO>();
				dependentTos.add(new EmpDependentsTO());
				employeeTo.setDependents(dependentTos);
			}
		}

		// reporting info
		if (user.getEmployee().getEmployeeByReportToId() != null) {
			employeeTo.setReportToId(String.valueOf(user.getEmployee()
					.getEmployeeByReportToId().getId()));
			StringBuffer nameBUff = new StringBuffer();
			nameBUff.append(user.getEmployee().getEmployeeByReportToId().getFirstName());
			if (user.getEmployee().getEmployeeByReportToId().getLastName() != null) {
				nameBUff.append(" ");
				nameBUff.append(user.getEmployee().getEmployeeByReportToId()
						.getLastName());
			}
			employeeTo.setReportToName(nameBUff.toString());

		}
		else
		{
			employeeTo.setReportToId(null);
			employeeTo.setReportToName(null);
		}
		if (user.getEmployee().getEmployeeByLeaveApproveAuthId() != null) {
			employeeTo.setFinalAuthorityId(String.valueOf(user.getEmployee()
					.getEmployeeByLeaveApproveAuthId().getId()));
			StringBuffer nameBUff = new StringBuffer();
			nameBUff.append(user.getEmployee().getEmployeeByLeaveApproveAuthId()
					.getFirstName());
			if (user.getEmployee().getEmployeeByLeaveApproveAuthId().getLastName() != null) {
				nameBUff.append(" ");
				nameBUff.append(user.getEmployee().getEmployeeByLeaveApproveAuthId()
						.getLastName());
			}
			employeeTo.setFinalAuthorityName(nameBUff.toString());

		}
		else
		{
			employeeTo.setFinalAuthorityId(null);
			employeeTo.setFinalAuthorityName(null);
		}
		/**
		 * User details
		 */
		if(user.getIsAddRemarks()!=null)
			employeeTo.setIsAddRemarks(user.getIsAddRemarks());
		if(user.getIsViewRemarks()!=null)
			employeeTo.setIsViewRemarks(user.getIsViewRemarks());
		return employeeTo;
	}

}
