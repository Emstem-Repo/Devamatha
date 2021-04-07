package com.kp.cms.to.employee;
		import java.util.HashSet;
		import java.util.List;
		import java.util.Set;

		import com.kp.cms.bo.admin.EmpDependents;
		import com.kp.cms.to.admin.EmpAcheivementTO;
		import com.kp.cms.to.admin.EmpAllowanceTO;
		import com.kp.cms.to.admin.EmpDependentsTO;
		import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.PfGratuityNomineesTO;
	
	public class EmployeeInfoEditTO {
						
				private List<EmpPreviousOrgTo> experiences;
				private List<EmpPreviousOrgTo> teachingExperience;
				private List<EmpQualificationLevelTo> empQualificationLevelTos;
				private List<EmpQualificationLevelTo> empQualificationFixedTo;
				private List<EmpLeaveTO> empLeaveTo;
				private List<EmpTypeTo> empTypeToList;
				private List<EmpAcheivementTO> empAcheivements;
				
				private List<EmpLoanTO> empLoan;
				private List<EmpFinancialTO> empFinancial;
				private List<EmpFeeConcessionTO> empFeeConcession;
				private List<EmpRemarksTO> empRemarks;
				private List<EmpIncentivesTO> empIncentives;
				private List<EmpImmigrationTO> empImmigration;
				private List<EmpLeaveAllotTO> empLeaveToList;
				private List<EmpDependentsTO> empDependentses;
				private List<EmpAllowanceTO> payscaleFixedTo;	
				private List<EmpPayAllowanceTO> payAllowanceTo;	
				private List<EmpImagesTO> empImages;	
				private List<PfGratuityNomineesTO> pfGratuityNominee;	
				
					
				public List<EmpAcheivementTO> getEmpAcheivements() {
					return empAcheivements;
				}
				public void setEmpAcheivements(List<EmpAcheivementTO> empAcheivements) {
					this.empAcheivements = empAcheivements;
				}
				
				public List<EmpDependentsTO> getEmpDependentses() {
					return empDependentses;
				}
				public void setEmpDependentses(List<EmpDependentsTO> empDependentses) {
					this.empDependentses = empDependentses;
				}
				public List<EmpLeaveAllotTO> getEmpLeaveToList() {
					return empLeaveToList;
				}
				public void setEmpLeaveToList(List<EmpLeaveAllotTO> empLeaveToList) {
					this.empLeaveToList = empLeaveToList;
				}
				public List<EmpPreviousOrgTo> getExperiences() {
					return experiences;
				}
				public List<EmpPreviousOrgTo> getTeachingExperience() {
					return teachingExperience;
				}
				public List<EmpQualificationLevelTo> getEmpQualificationLevelTos() {
					return empQualificationLevelTos;
				}
				public List<EmpQualificationLevelTo> getEmpQualificationFixedTo() {
					return empQualificationFixedTo;
				}
				public void setExperiences(List<EmpPreviousOrgTo> experiences) {
					this.experiences = experiences;
				}
				public void setTeachingExperience(List<EmpPreviousOrgTo> teachingExperience) {
					this.teachingExperience = teachingExperience;
				}
				public void setEmpQualificationLevelTos(
						List<EmpQualificationLevelTo> empQualificationLevelTos) {
					this.empQualificationLevelTos = empQualificationLevelTos;
				}
				public void setEmpQualificationFixedTo(
						List<EmpQualificationLevelTo> empQualificationFixedTo) {
					this.empQualificationFixedTo = empQualificationFixedTo;
				}
				public List<EmpLeaveTO> getEmpLeaveTo() {
					return empLeaveTo;
				}
				public void setEmpLeaveTo(List<EmpLeaveTO> empLeaveTo) {
					this.empLeaveTo = empLeaveTo;
				}
				
				public List<EmpLoanTO> getEmpLoan() {
					return empLoan;
				}
				public void setEmpLoan(List<EmpLoanTO> empLoan) {
					this.empLoan = empLoan;
				}
				public List<EmpFinancialTO> getEmpFinancial() {
					return empFinancial;
				}
				public void setEmpFinancial(List<EmpFinancialTO> empFinancial) {
					this.empFinancial = empFinancial;
				}
				public List<EmpFeeConcessionTO> getEmpFeeConcession() {
					return empFeeConcession;
				}
				public void setEmpFeeConcession(List<EmpFeeConcessionTO> empFeeConcession) {
					this.empFeeConcession = empFeeConcession;
				}
				public List<EmpRemarksTO> getEmpRemarks() {
					return empRemarks;
				}
				public void setEmpRemarks(List<EmpRemarksTO> empRemarks) {
					this.empRemarks = empRemarks;
				}
				public List<EmpIncentivesTO> getEmpIncentives() {
					return empIncentives;
				}
				public void setEmpIncentives(List<EmpIncentivesTO> empIncentives) {
					this.empIncentives = empIncentives;
				}
				public List<EmpImmigrationTO> getEmpImmigration() {
					return empImmigration;
				}
				public void setEmpImmigration(List<EmpImmigrationTO> empImmigration) {
					this.empImmigration = empImmigration;
				}
				
				public List<EmpTypeTo> getEmpTypeToList() {
					return empTypeToList;
				}
				public void setEmpTypeToList(List<EmpTypeTo> empTypeToList) {
					this.empTypeToList = empTypeToList;
				}
				public List<EmpAllowanceTO> getPayscaleFixedTo() {
					return payscaleFixedTo;
				}
				public void setPayscaleFixedTo(List<EmpAllowanceTO> payscaleFixedTo) {
					this.payscaleFixedTo = payscaleFixedTo;
				}
				public List<EmpPayAllowanceTO> getPayAllowanceTo() {
					return payAllowanceTo;
				}
				public void setPayAllowanceTo(List<EmpPayAllowanceTO> payAllowanceTo) {
					this.payAllowanceTo = payAllowanceTo;
				}
				public List<EmpImagesTO> getEmpImages() {
					return empImages;
				}
				public void setEmpImages(List<EmpImagesTO> empImages) {
					this.empImages = empImages;
				}
				public List<PfGratuityNomineesTO> getPfGratuityNominee() {
					return pfGratuityNominee;
				}
				public void setPfGratuityNominee(List<PfGratuityNomineesTO> pfGratuityNominee) {
					this.pfGratuityNominee = pfGratuityNominee;
				}
				
				

			}



