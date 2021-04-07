package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EmpAgeofRetirement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpFunctionalArea;
import com.kp.cms.bo.admin.EmpIndustryType;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.EmpWorkType;
import com.kp.cms.bo.admin.EmployeeCategory;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlLeaveType;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Region;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Services;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.bo.admin.SubjectType;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.bo.exam.ExamGenBO;
import com.kp.cms.bo.exam.ExamSubjectTypeMasterBO;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.ExamInviligatorExemption;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.LocationBo;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.forms.admin.SingleFieldMasterForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;

public class SingleFieldMasterHelper {
	public static volatile SingleFieldMasterHelper singleFieldMasterHelper = null;
	public static final Log log = LogFactory.getLog(SingleFieldMasterHelper.class);

	public static SingleFieldMasterHelper getInstance() {
		if (singleFieldMasterHelper == null) {
			singleFieldMasterHelper = new SingleFieldMasterHelper();
			return singleFieldMasterHelper;
		}
		return singleFieldMasterHelper;
	}

	public List<SingleFieldMasterTO> copySingleFieldMasterHelper(
			List singfieldlistList, String mastername) {
		//List singleFieldList = new ArrayList();
		Iterator i = singfieldlistList.iterator();
		List<SingleFieldMasterTO> singleFieldMasterList;
		SingleFieldMasterTO singleFieldMasterTO = null;
		if (mastername.equalsIgnoreCase("ExamSubjectTypeMaster")) {
			ExamSubjectTypeMasterBO caste = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();

				caste = (ExamSubjectTypeMasterBO) i.next();
				singleFieldMasterTO.setId(caste.getId());
				singleFieldMasterTO.setName(caste.getName());
				singleFieldMasterTO.setOrderType(String.valueOf(caste.getOrder()));
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		}
		else if (mastername.equalsIgnoreCase("Caste")) {
			Caste caste = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();

				caste = (Caste) i.next();
				singleFieldMasterTO.setId(caste.getId());
				singleFieldMasterTO.setName(caste.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Country")) {
			Country country = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				country = (Country) i.next();
				singleFieldMasterTO.setId(country.getId());
				singleFieldMasterTO.setName(country.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		}else if (mastername.equalsIgnoreCase("Sports")) {
			Sports sports = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				sports = (Sports) i.next();
				singleFieldMasterTO.setId(sports.getId());
				singleFieldMasterTO.setName(sports.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		}else if (mastername.equalsIgnoreCase("AdmissionStatus")) {
			InterviewStatus admissionStatus = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				admissionStatus = (InterviewStatus) i.next();
				singleFieldMasterTO.setId(admissionStatus.getId());
				singleFieldMasterTO.setName(admissionStatus.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("CourseScheme")) {
			CourseScheme courseScheme = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				courseScheme = (CourseScheme) i.next();
				singleFieldMasterTO.setId(courseScheme.getId());
				singleFieldMasterTO.setName(courseScheme.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("PreRequisite")) {
			Prerequisite prerequisite = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				prerequisite = (Prerequisite) i.next();
				singleFieldMasterTO.setId(prerequisite.getId());
				singleFieldMasterTO.setName(prerequisite.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("MotherTongue")) {
			MotherTongue motherTongue = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				motherTongue = (MotherTongue) i.next();
				singleFieldMasterTO.setId(motherTongue.getId());
				singleFieldMasterTO.setName(motherTongue.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Occupation")) {
			Occupation occupation = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				occupation = (Occupation) i.next();
				singleFieldMasterTO.setId(occupation.getId());
				singleFieldMasterTO.setName(occupation.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("DocType")) {
			DocType docType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				docType = (DocType) i.next();
				singleFieldMasterTO.setId(docType.getId());
				singleFieldMasterTO.setName(docType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Region")) {
			Region region = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				region = (Region) i.next();
				singleFieldMasterTO.setId(region.getId());
				singleFieldMasterTO.setName(region.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("ResidentCategory")) {
			ResidentCategory residentCategory = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				residentCategory = (ResidentCategory) i.next();
				singleFieldMasterTO.setId(residentCategory.getId());
				singleFieldMasterTO.setName(residentCategory.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("MeritSet")) {
			MeritSet meritSet = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				meritSet = (MeritSet) i.next();
				singleFieldMasterTO.setId(meritSet.getId());
				singleFieldMasterTO.setName(meritSet.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("ProgramType")) {
			ProgramType programType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				programType = (ProgramType) i.next();
				singleFieldMasterTO.setId(programType.getId());
				singleFieldMasterTO.setName(programType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
				

			}
		} else if (mastername.equalsIgnoreCase("Religion")) {
			Religion religion = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				religion = (Religion) i.next();
				singleFieldMasterTO.setId(religion.getId());
				singleFieldMasterTO.setName(religion.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("FeePaymentMode")) {
			FeePaymentMode feePaymentMode = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				feePaymentMode = (FeePaymentMode) i.next();
				singleFieldMasterTO.setId(feePaymentMode.getId());
				singleFieldMasterTO.setName(feePaymentMode.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("University")) {
			University university = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				university = (University) i.next();
				singleFieldMasterTO.setId(university.getId());
				singleFieldMasterTO.setName(university.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Department")) {
			Department department = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				department = (Department) i.next();
				singleFieldMasterTO.setId(department.getId());
				singleFieldMasterTO.setName(department.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Roles")) {

			Roles roles = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				roles = (Roles) i.next();
				singleFieldMasterTO.setId(roles.getId());
				singleFieldMasterTO.setName(roles.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Designation")) {

			Designation designation = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				designation = (Designation) i.next();
				singleFieldMasterTO.setId(designation.getId());
				singleFieldMasterTO.setName(designation.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("EmployeeCategory")) {
			EmployeeCategory employeeCategory = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				employeeCategory = (EmployeeCategory) i.next();
				singleFieldMasterTO.setId(employeeCategory.getId());
				singleFieldMasterTO.setName(employeeCategory.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("LeaveType")) {
			LeaveType leaveType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				leaveType = (LeaveType) i.next();
				singleFieldMasterTO.setId(leaveType.getId());
				singleFieldMasterTO.setName(leaveType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Nationality")) {
			Nationality nationality = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				nationality = (Nationality) i.next();
				singleFieldMasterTO.setId(nationality.getId());
				singleFieldMasterTO.setName(nationality.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("Income")) {
			Income income = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				income = (Income) i.next();
				singleFieldMasterTO.setId(income.getId());
				singleFieldMasterTO.setName(income.getIncomeRange());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("HlFacility")) {
			HlFacility hlFacility = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				hlFacility = (HlFacility) i.next();
				singleFieldMasterTO.setId(hlFacility.getId());
				singleFieldMasterTO.setName(hlFacility.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("HlComplaintType")) {
			HlComplaintType hlComplaintType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				hlComplaintType = (HlComplaintType) i.next();
				singleFieldMasterTO.setId(hlComplaintType.getId());
				singleFieldMasterTO.setName(hlComplaintType.getType());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("HlLeaveType")) {
			HlLeaveType hlLeaveType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				hlLeaveType = (HlLeaveType) i.next();
				singleFieldMasterTO.setId(hlLeaveType.getId());
				singleFieldMasterTO.setName(hlLeaveType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("InvUom")) {
			InvUom invUom = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				invUom = (InvUom) i.next();
				singleFieldMasterTO.setId(invUom.getId());
				singleFieldMasterTO.setName(invUom.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("InvLocation")) {
			InvLocation invLocation = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				invLocation = (InvLocation) i.next();
				singleFieldMasterTO.setId(invLocation.getId());
				singleFieldMasterTO.setName(invLocation.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("InvItemCategory")) {
			InvItemCategory inItemCategory = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				inItemCategory = (InvItemCategory) i.next();
				singleFieldMasterTO.setId(inItemCategory.getId());
				singleFieldMasterTO.setName(inItemCategory.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("EmpFunctionalArea")) {
			EmpFunctionalArea empFunctionalArea = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				empFunctionalArea = (EmpFunctionalArea) i.next();
				singleFieldMasterTO.setId(empFunctionalArea.getId());
				singleFieldMasterTO.setName(empFunctionalArea.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("EmpLeaveType")) {
			EmpLeaveType empLeaveType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				empLeaveType = (EmpLeaveType) i.next();
				singleFieldMasterTO.setId(empLeaveType.getId());
				singleFieldMasterTO.setName(empLeaveType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("EmpQualificationLevel")) {
			EmpQualificationLevel empQualificationLevel = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				empQualificationLevel = (EmpQualificationLevel) i.next();
				singleFieldMasterTO.setId(empQualificationLevel.getId());
				singleFieldMasterTO.setName(empQualificationLevel.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		} else if (mastername.equalsIgnoreCase("EmpIndustryType")) {
			EmpIndustryType empIndustryType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				empIndustryType = (EmpIndustryType) i.next();
				singleFieldMasterTO.setId(empIndustryType.getId());
				singleFieldMasterTO.setName(empIndustryType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}

		} else if (mastername.equalsIgnoreCase("EmpAgeofRetirement")) {
			EmpAgeofRetirement ageofRetirement = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				ageofRetirement = (EmpAgeofRetirement) i.next();
				singleFieldMasterTO.setId(ageofRetirement.getId());
				singleFieldMasterTO.setName(ageofRetirement.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}

		} else if (mastername.equalsIgnoreCase("EmpAllowance")) {
			EmpAllowance allowance = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				allowance = (EmpAllowance) i.next();
				singleFieldMasterTO.setId(allowance.getId());
				singleFieldMasterTO.setName(allowance.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		} else if (mastername.equalsIgnoreCase("EmpJobType")) {
			EmpJobType jobType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				jobType = (EmpJobType) i.next();
				singleFieldMasterTO.setId(jobType.getId());
				singleFieldMasterTO.setName(jobType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		} else if (mastername.equalsIgnoreCase("EmpWorkType")) {
			EmpWorkType workType = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				workType = (EmpWorkType) i.next();
				singleFieldMasterTO.setId(workType.getId());
				singleFieldMasterTO.setName(workType.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		} else if (mastername.equalsIgnoreCase("PcBankAccNumber")) {
			PcBankAccNumber pcBankAccNumber = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				pcBankAccNumber = (PcBankAccNumber) i.next();
				singleFieldMasterTO.setId(pcBankAccNumber.getId());
				singleFieldMasterTO.setName(pcBankAccNumber.getAccountNo());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		}

		/**
		 * Exams Modules
		 */

		else if (mastername.equalsIgnoreCase("_Exam_")) {
			ExamGenBO examGenBO = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				examGenBO = (ExamGenBO) i.next();
				singleFieldMasterTO.setId(examGenBO.getId());
				singleFieldMasterTO.setName(examGenBO.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		} else if (mastername.equalsIgnoreCase("Stream")) {

			EmployeeStreamBO objBO = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {

				singleFieldMasterTO = new SingleFieldMasterTO();
				objBO = (EmployeeStreamBO) i.next();
				singleFieldMasterTO.setId(objBO.getId());
				singleFieldMasterTO.setName(objBO.getName());
				singleFieldMasterList.add(singleFieldMasterTO);

			}
		}

		else if (mastername.equalsIgnoreCase("EmployeeWorkLocation")) {
			EmployeeWorkLocationBO objBO = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				objBO = (EmployeeWorkLocationBO) i.next();
				singleFieldMasterTO.setId(objBO.getId());
				singleFieldMasterTO.setName(objBO.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		}

		else if (mastername.equalsIgnoreCase("EmployeeType")) {
			EmployeeTypeBO objBO = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				objBO = (EmployeeTypeBO) i.next();
				singleFieldMasterTO.setId(objBO.getId());
				singleFieldMasterTO.setName(objBO.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		}else if (mastername.equalsIgnoreCase("CharacterAndConduct")) {
			CharacterAndConduct objBO = null;
			singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
			while (i.hasNext()) {
				singleFieldMasterTO = new SingleFieldMasterTO();
				objBO = (CharacterAndConduct) i.next();
				singleFieldMasterTO.setId(objBO.getId());
				singleFieldMasterTO.setName(objBO.getName());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
		}else if(mastername.equalsIgnoreCase("SubjectAreaBO")){
				SubjectAreaBO objBO=null;
				singleFieldMasterList=new ArrayList<SingleFieldMasterTO>();
				while(i.hasNext()){
					singleFieldMasterTO =new SingleFieldMasterTO();
					objBO =(SubjectAreaBO) i.next();
					singleFieldMasterTO.setId(objBO.getId());
					singleFieldMasterTO.setName(objBO.getName());
					singleFieldMasterList.add(singleFieldMasterTO);
				}
		}else if(mastername.equalsIgnoreCase("EmpJobTitle")){
			EmpJobTitle objBO=null;
			singleFieldMasterList=new ArrayList<SingleFieldMasterTO>();
			while(i.hasNext()){
				singleFieldMasterTO =new SingleFieldMasterTO();
				objBO =(EmpJobTitle) i.next();
				singleFieldMasterTO.setId(objBO.getId());
				singleFieldMasterTO.setName(objBO.getTitle());
				singleFieldMasterList.add(singleFieldMasterTO);
			}
	}else if (mastername.equalsIgnoreCase("ApplicationStatus")){
		ApplicationStatus appStatus=null;
		singleFieldMasterList=new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO =new SingleFieldMasterTO();
			appStatus = (ApplicationStatus) i.next();
			singleFieldMasterTO.setId(appStatus.getId());
			singleFieldMasterTO.setName(appStatus.getName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if (mastername.equalsIgnoreCase("invCampus")) {
		InvCampus invCampus = null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			invCampus = (InvCampus) i.next();
			singleFieldMasterTO.setId(invCampus.getId());
			singleFieldMasterTO.setName(invCampus.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}else if(mastername.equalsIgnoreCase("CCGroup")){
		CCGroup ccGroup= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {

			singleFieldMasterTO = new SingleFieldMasterTO();

			ccGroup = (CCGroup) i.next();
			singleFieldMasterTO.setId(ccGroup.getId());
			singleFieldMasterTO.setName(ccGroup.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}else if (mastername.equalsIgnoreCase("invCompany")) {
		InvCompany invCompany= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			invCompany = (InvCompany) i.next();
			singleFieldMasterTO.setId(invCompany.getId());
			singleFieldMasterTO.setName(invCompany.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}else if (mastername.equalsIgnoreCase("ApplicantFeedback")) {
		ApplicantFeedback applicantFeedback= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			applicantFeedback = (ApplicantFeedback) i.next();
			singleFieldMasterTO.setId(applicantFeedback.getId());
			singleFieldMasterTO.setName(applicantFeedback.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}else if (mastername.equalsIgnoreCase("Discipline")) {
		DisciplineBo disciplineBo= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			disciplineBo = (DisciplineBo) i.next();
			singleFieldMasterTO.setId(disciplineBo.getId());
			singleFieldMasterTO.setName(disciplineBo.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}else if (mastername.equalsIgnoreCase("PhdResearchPublication")) {
		PhdResearchPublication researchPublication= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			researchPublication = (PhdResearchPublication) i.next();
			singleFieldMasterTO.setId(researchPublication.getId());
			singleFieldMasterTO.setName(researchPublication.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}else if (mastername.equalsIgnoreCase("Location")) {
		LocationBo locationBo= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			locationBo = (LocationBo) i.next();
			singleFieldMasterTO.setId(locationBo.getId());
			singleFieldMasterTO.setName(locationBo.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}/*else if (mastername.equalsIgnoreCase("FineCategory")) {
		FineCategoryBo fineCategoryBo= null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			fineCategoryBo = (FineCategoryBo) i.next();
			singleFieldMasterTO.setId(fineCategoryBo.getId());
			singleFieldMasterTO.setName(fineCategoryBo.getName());
			singleFieldMasterList.add(singleFieldMasterTO);

		}
	}*/
	else if(mastername.equalsIgnoreCase("BlockDetails")){
		BlockDetails blocks=null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while(i.hasNext()){
			singleFieldMasterTO = new SingleFieldMasterTO();
			blocks = (BlockDetails) i.next();
			singleFieldMasterTO.setId(blocks.getId());
			singleFieldMasterTO.setName(blocks.getBlockName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("BookingRequirements")){
		BookingRequirements requirements=null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while(i.hasNext()){
			singleFieldMasterTO = new SingleFieldMasterTO();
			requirements = (BookingRequirements) i.next();
			singleFieldMasterTO.setId(requirements.getId());
			singleFieldMasterTO.setName(requirements.getName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("EventLocation")){
		EventLocation eventLocation=null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while(i.hasNext()){
			singleFieldMasterTO = new SingleFieldMasterTO();
			eventLocation = (EventLocation) i.next();
			singleFieldMasterTO.setId(eventLocation.getId());
			singleFieldMasterTO.setName(eventLocation.getName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("ClassGroup")){
		ClassGroup classGroup=null;
		singleFieldMasterList = new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			classGroup = (ClassGroup) i.next();
			singleFieldMasterTO.setId(classGroup.getId());
			singleFieldMasterTO.setName(classGroup.getName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("ExamInviligatorExemption")){
		ExamInviligatorExemption ExamInviligatorExemption=null;
		singleFieldMasterList= new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			ExamInviligatorExemption = (ExamInviligatorExemption) i.next();
			singleFieldMasterTO.setId(ExamInviligatorExemption.getId());
			singleFieldMasterTO.setName(ExamInviligatorExemption.getExemption());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("SubjectCodeGroup")){
		SubjectCodeGroup subjectCodeGroup=null;
		singleFieldMasterList= new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			subjectCodeGroup = (SubjectCodeGroup) i.next();
			singleFieldMasterTO.setId(subjectCodeGroup.getId());
			singleFieldMasterTO.setName(subjectCodeGroup.getSubjectsGroupName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("EmployeeSubject")){
		EmployeeSubject employeeSubject=null;
		singleFieldMasterList= new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			employeeSubject = (EmployeeSubject) i.next();
			singleFieldMasterTO.setId(employeeSubject.getId());
			singleFieldMasterTO.setName(employeeSubject.getSubjectName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else if(mastername.equalsIgnoreCase("Services")){
		Services services=null;
		singleFieldMasterList= new ArrayList<SingleFieldMasterTO>();
		while (i.hasNext()) {
			singleFieldMasterTO = new SingleFieldMasterTO();
			services = (Services) i.next();
			singleFieldMasterTO.setId(services.getId());
			singleFieldMasterTO.setName(services.getName());
			singleFieldMasterList.add(singleFieldMasterTO);
		}
	}else{
		singleFieldMasterList= new ArrayList<SingleFieldMasterTO>();  
	}
		Collections.sort(singleFieldMasterList);
		log.error("ending of copySingleFieldMasterHelper method in SingleFieldMasterHelper");
		return singleFieldMasterList;

	}

	/**
	 * 
	 * @param AdmittedThroughForm
	 *            creates BO from admittedThroughForm
	 * 
	 * @return AdmittedThrough BO object
	 */

	public AdmittedThrough populateAdmittedThroughDataFormForm(
			AdmittedThroughForm admittedThroughForm) throws Exception {
		AdmittedThrough admittedThrough = new AdmittedThrough();
		admittedThrough.setId(admittedThroughForm.getAdmittedThroughId());
		admittedThrough.setName(admittedThroughForm.getAdmittedThrough());
		admittedThrough.setIsActive(true); // in add and edit we can set this as
		// true
		return admittedThrough;
	}

	public Caste populateFormToCast(SingleFieldMasterForm singleFieldMasterForm)
			throws Exception {
		Caste caste = new Caste();
		caste.setId(singleFieldMasterForm.getId());
		caste.setName(singleFieldMasterForm.getName().trim());
		caste.setIsActive(true); // in add and edit we can set this as true
		return caste;
	}

	public Country populateFormToCountry(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Country country = new Country();
		country.setId(singleFieldMasterForm.getId());
		country.setName(singleFieldMasterForm.getName().trim());
		country.setIsActive(true); // in add and edit we can set this as true
		return country;

	}
	public ExamSubjectTypeMasterBO populateFormToSubjectType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		ExamSubjectTypeMasterBO subjectType = new ExamSubjectTypeMasterBO();
		subjectType.setId(singleFieldMasterForm.getId());
		subjectType.setName(singleFieldMasterForm.getName().trim());
		subjectType.setIsActive(true); // in add and edit we can set this as true
		subjectType.setOrder(Integer.parseInt(singleFieldMasterForm.getOrder()));
		return subjectType;

	}
	public Sports populateFormToSports(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Sports sports = new Sports();
		sports.setId(singleFieldMasterForm.getId());
		sports.setName(singleFieldMasterForm.getName().trim());
		sports.setIsActive(true); // in add and edit we can set this as true
		return sports;

	}

	public InterviewStatus populateFormToAdmissionStatus(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		InterviewStatus admissionStatus = new InterviewStatus();
		admissionStatus.setId(singleFieldMasterForm.getId());
		admissionStatus.setName(singleFieldMasterForm.getName().trim());
		admissionStatus.setIsActive(true); // in add and edit we can set this as
		// true
		return admissionStatus;
	}

	public CourseScheme populateFormToCourseScheme(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		CourseScheme courseScheme = new CourseScheme();
		courseScheme.setId(singleFieldMasterForm.getId());
		courseScheme.setName(singleFieldMasterForm.getName().trim());
		courseScheme.setIsActive(true); // in add and edit we can set this as
		// true
		return courseScheme;
	}

	public Prerequisite populateFormToPrerequisite(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Prerequisite prerequisite = new Prerequisite();
		prerequisite.setId(singleFieldMasterForm.getId());
		prerequisite.setName(singleFieldMasterForm.getName().trim());
		prerequisite.setIsActive(true); // in add and edit we can set this as
		// true

		return prerequisite;
	}

	public MotherTongue populateFormToMotherTongue(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		MotherTongue motherTongue = new MotherTongue();
		motherTongue.setId(singleFieldMasterForm.getId());
		motherTongue.setName(singleFieldMasterForm.getName().trim());
		motherTongue.setIsActive(true); // in add and edit we can set this as
		// true
		return motherTongue;
	}

	public Occupation populateFormToOccupation(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Occupation occupation = new Occupation();
		occupation.setId(singleFieldMasterForm.getId());
		occupation.setName(singleFieldMasterForm.getName().trim());
		occupation.setIsActive(true); // in add and edit we can set this as true
		return occupation;
	}

	public DocType populateFormToDocType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		DocType docType = new DocType();
		docType.setId(singleFieldMasterForm.getId());
		docType.setName(singleFieldMasterForm.getName().trim());
		docType.setIsActive(true); // in add and edit we can set this as true
		return docType;
	}

	public Region populateFormToRegion(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Region region = new Region();
		region.setId(singleFieldMasterForm.getId());
		region.setName(singleFieldMasterForm.getName().trim());
		region.setIsActive(true); // in add and edit we can set this as true
		return region;
	}

	public ResidentCategory populateFormToResidentCategory(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		ResidentCategory residentCategory = new ResidentCategory();
		residentCategory.setId(singleFieldMasterForm.getId());
		residentCategory.setName(singleFieldMasterForm.getName().trim());
		residentCategory.setIsActive(true); // in add and edit we can set this
		// as true
		return residentCategory;
	}

	public MeritSet populateFormToMeritSet(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		MeritSet meritSet = new MeritSet();
		meritSet.setId(singleFieldMasterForm.getId());
		meritSet.setName(singleFieldMasterForm.getName().trim());
		meritSet.setIsActive(true); // in add and edit we can set this as true
		return meritSet;
	}

	public FeePaymentMode populateFormToFeePaymentMode(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		FeePaymentMode feePaymentMode = new FeePaymentMode();
		feePaymentMode.setId(singleFieldMasterForm.getId());
		feePaymentMode.setName(singleFieldMasterForm.getName().trim());
		feePaymentMode.setIsActive(true); // in add and edit we can set this as
		// true
		return feePaymentMode;
	}

	public Religion populateFormToReligion(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Religion religion = new Religion();
		religion.setId(singleFieldMasterForm.getId());
		religion.setName(singleFieldMasterForm.getName().trim());
		religion.setIsActive(true); // in add and edit we can set this as true
		return religion;
	}

	public ProgramType populateFormToProgramType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		ProgramType programType = new ProgramType();
		programType.setId(singleFieldMasterForm.getId());
		programType.setName(singleFieldMasterForm.getName().trim());
		programType.setIsActive(true); // in add and edit we can set this as
		// true
		return programType;
	}

	public University populateFormToUniversity(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		University university = new University();
		university.setId(singleFieldMasterForm.getId());
		university.setName(singleFieldMasterForm.getName().trim());
		university.setIsActive(true); // in add and edit we can set this as true
		return university;
	}

	public Department populateFormToDepartment(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Department department = new Department();
		department.setId(singleFieldMasterForm.getId());
		department.setName(singleFieldMasterForm.getName().trim());
		department.setIsActive(true); // in add and edit we can set this as true
		return department;
	}

	// -----------Roles-------------

	public Roles populateFormToRoles(SingleFieldMasterForm singleFieldMasterForm)
			throws Exception {
		Roles roles = new Roles();
		roles.setId(singleFieldMasterForm.getId());
		roles.setName(singleFieldMasterForm.getName().trim());
		roles.setIsActive(true); // in add and edit we can set this as true
		return roles;
	}

	// -----------Designation-------------

	public Designation populateFormToDesignation(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Designation designation = new Designation();
		designation.setId(singleFieldMasterForm.getId());
		designation.setName(singleFieldMasterForm.getName().trim());
		designation.setIsActive(true); // in add and edit we can set this as
		// true
		return designation;
	}

	// -----------Designation-------------

	public EmployeeCategory populateFormToEmployeeCategory(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmployeeCategory employeeCategory = new EmployeeCategory();
		employeeCategory.setId(singleFieldMasterForm.getId());
		employeeCategory.setName(singleFieldMasterForm.getName().trim());
		employeeCategory.setIsActive(true); // in add and edit we can set this
		// as true
		return employeeCategory;
	}

	/**
	 * create leaveType Bo from Form
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public LeaveType populateFormToLeaveType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		LeaveType leaveType = new LeaveType();
		leaveType.setId(singleFieldMasterForm.getId());
		leaveType.setName(singleFieldMasterForm.getName().trim());
		leaveType.setIsActive(true); // in add and edit we can set this as true
		return leaveType;
	}

	/**
	 * create leaveType Bo from Form
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public Nationality populateFormToNatiionality(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Nationality nationality = new Nationality();
		nationality.setId(singleFieldMasterForm.getId());
		nationality.setName(singleFieldMasterForm.getName().trim());
		nationality.setIsActive(true); // in add and edit we can set this as
		// true
		return nationality;
	}

	/**
	 * income
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public Income populateFormToIncome(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		Income income = new Income();
		income.setId(singleFieldMasterForm.getId());
		income.setIncomeRange(singleFieldMasterForm.getName().trim());
		income.setIsActive(true); // in add and edit we can set this as true
		return income;
	}

	/**
	 * facility
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public HlFacility populateFormToHlFacility(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		HlFacility hlFacility = new HlFacility();
		hlFacility.setId(singleFieldMasterForm.getId());
		hlFacility.setName(singleFieldMasterForm.getName().trim());
		hlFacility.setIsActive(true); // in add and edit we can set this as true
		return hlFacility;
	}

	/**
	 * HlDisciplinaryType
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public HlComplaintType populateFormToHlComplaintType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		HlComplaintType hlComplaintType = new HlComplaintType();
		hlComplaintType.setId(singleFieldMasterForm.getId());
		hlComplaintType.setType(singleFieldMasterForm.getName().trim());
		hlComplaintType.setIsActive(true); // in add and edit we can set this as
		// true
		return hlComplaintType;
	}

	/**
	 * HlDisciplinaryType
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public HlLeaveType populateFormToHlLeaveType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		HlLeaveType hlLeaveType = new HlLeaveType();
		hlLeaveType.setId(singleFieldMasterForm.getId());
		hlLeaveType.setName(singleFieldMasterForm.getName().trim());
		hlLeaveType.setIsActive(true); // in add and edit we can set this as
		// true
		return hlLeaveType;
	}

	/**
	 * InvUom
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public InvUom populateFormToInvUom(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		InvUom invUom = new InvUom();
		invUom.setId(singleFieldMasterForm.getId());
		invUom.setName(singleFieldMasterForm.getName().trim());
		invUom.setIsActive(true); // in add and edit we can set this as true
		return invUom;
	}

	/**
	 * InvLocation
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public InvLocation populateFormToInvLocation(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		InvLocation invLocation = new InvLocation();
		invLocation.setId(singleFieldMasterForm.getId());
		invLocation.setName(singleFieldMasterForm.getName().trim());
		invLocation.setIsActive(true); // in add and edit we can set this as
		// true
		return invLocation;
	}

	/**
	 * InvItemCategory
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public InvItemCategory populateFormToInvItemCategory(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		InvItemCategory invItemCategory = new InvItemCategory();
		invItemCategory.setId(singleFieldMasterForm.getId());
		invItemCategory.setName(singleFieldMasterForm.getName().trim());
		invItemCategory.setIsActive(true); // in add and edit we can set this as
		// true
		return invItemCategory;
	}

	/**
	 * EmpFunctionalArea
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpFunctionalArea populateFormToFuctionalArea(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpFunctionalArea functionalArea = new EmpFunctionalArea();
		functionalArea.setId(singleFieldMasterForm.getId());
		functionalArea.setName(singleFieldMasterForm.getName().trim());
		functionalArea.setIsActive(true); // in add and edit we can set this as
		// true
		return functionalArea;
	}

	/**
	 * EmpLeaveType
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpLeaveType populateFormToEmpLeaveType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpLeaveType empType = new EmpLeaveType();
		empType.setId(singleFieldMasterForm.getId());
		empType.setName(singleFieldMasterForm.getName().trim());
		empType.setIsActive(true); // in add and edit we can set this as true
		empType.setIsLeave(true);
		empType.setisExemption(true);
		return empType;
	}

	/**
	 * EmpQualificationLevel
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpQualificationLevel populateFormToQualificationLevel(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpQualificationLevel empQualificationLevel = new EmpQualificationLevel();
		empQualificationLevel.setId(singleFieldMasterForm.getId());
		empQualificationLevel.setName(singleFieldMasterForm.getName().trim());
		empQualificationLevel.setIsActive(true); // in add and edit we can set
		// this as true
		return empQualificationLevel;
	}

	/**
	 * EmpIndustryType
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpIndustryType populateFormToIndustryType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpIndustryType empIndustryType = new EmpIndustryType();
		empIndustryType.setId(singleFieldMasterForm.getId());
		empIndustryType.setName(singleFieldMasterForm.getName().trim());
		empIndustryType.setIsActive(true); // in add and edit we can set this as
		// true
		return empIndustryType;
	}

	/**
	 * EmpAgeofRetirement
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpAgeofRetirement populateFormToAgeOfRetirement(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpAgeofRetirement ageofRetirement = new EmpAgeofRetirement();
		ageofRetirement.setId(singleFieldMasterForm.getId());
		ageofRetirement.setName(singleFieldMasterForm.getName().trim());
		ageofRetirement.setIsActive(true); // in add and edit we can set this as
		// true
		return ageofRetirement;
	}

	/**
	 * EmpAllowance
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpAllowance populateFormToEmpAllowance(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpAllowance empAllowance = new EmpAllowance();
		empAllowance.setId(singleFieldMasterForm.getId());
		empAllowance.setName(singleFieldMasterForm.getName().trim());
		empAllowance.setIsActive(true); // in add and edit we can set this as
		// true
		return empAllowance;
	}

	/**
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpJobType populateFormToEmpJob(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpJobType empJobType = new EmpJobType();
		empJobType.setId(singleFieldMasterForm.getId());
		empJobType.setName(singleFieldMasterForm.getName().trim());
		empJobType.setIsActive(true); // in add and edit we can set this as true
		return empJobType;
	}

	/**
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public EmpWorkType populateFormToEmpWorkType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpWorkType empWorkType = new EmpWorkType();
		empWorkType.setId(singleFieldMasterForm.getId());
		empWorkType.setName(singleFieldMasterForm.getName().trim());
		empWorkType.setIsActive(true); // in add and edit we can set this as
		// true
		return empWorkType;
	}

	/**
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public PcBankAccNumber populateFormToBankAccountNo(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		PcBankAccNumber bankAccNumber = new PcBankAccNumber();
		bankAccNumber.setId(singleFieldMasterForm.getId());
		bankAccNumber.setAccountNo(singleFieldMasterForm.getName().trim());
		bankAccNumber.setIsActive(true); // in add and edit we can set this as
		// true
		return bankAccNumber;
	}

	public EmployeeStreamBO populateFormToEmployeeStream(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmployeeStreamBO objBO = new EmployeeStreamBO();
		objBO.setId(singleFieldMasterForm.getId());
		objBO.setName(singleFieldMasterForm.getName().trim());
		objBO.setIsActive(true); // in add and edit we can set this as true
		return objBO;
	}

	/**
	 * 
	 * @param singleFieldMasterForm
	 * @return
	 * @throws Exception
	 */
	public ExamGenBO populateFormToExamGenBO(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		ExamGenBO examGenBO = new ExamGenBO();
		examGenBO.setId(singleFieldMasterForm.getId());
		examGenBO.setName(singleFieldMasterForm.getName().trim());
		examGenBO.setIsActive(true); // in add and edit we can set this as true
		return examGenBO;
	}

	public EmployeeWorkLocationBO populateFormToEmployeeWorkLocation(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmployeeWorkLocationBO objBO = new EmployeeWorkLocationBO();
		objBO.setId(singleFieldMasterForm.getId());
		objBO.setName(singleFieldMasterForm.getName().trim());
		objBO.setIsActive(true);
		return objBO;
	}

	public EmployeeTypeBO populateFormToEmployeeType(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmployeeTypeBO objBO = new EmployeeTypeBO();
		objBO.setId(singleFieldMasterForm.getId());
		objBO.setName(singleFieldMasterForm.getName().trim());
		objBO.setIsActive(true);
		return objBO;
	}

	public CharacterAndConduct populateFormToCharacterAndConduct(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception{
		CharacterAndConduct objBO = new CharacterAndConduct();
		objBO.setId(singleFieldMasterForm.getId());
		objBO.setName(singleFieldMasterForm.getName().trim());
		objBO.setIsActive(true);
		return objBO;
	}

	public SubjectAreaBO populateFormToSubjectArea(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		SubjectAreaBO areaBO = new SubjectAreaBO();
		areaBO.setId(singleFieldMasterForm.getId());
		areaBO.setName(singleFieldMasterForm.getName().trim());
		areaBO.setIsActive(true);
		return areaBO;
	}
	public EmpJobTitle populateFormToEmpJobTitle(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EmpJobTitle empJobTitle=new EmpJobTitle();
		empJobTitle.setId(singleFieldMasterForm.getId());
		empJobTitle.setTitle(singleFieldMasterForm.getName().trim());
		empJobTitle.setIsActive(true);
		return empJobTitle;
	}

	public ApplicationStatus populateFormToApplicationStatus(
			SingleFieldMasterForm singleFieldMasterForm) {
		ApplicationStatus applicationStatus =new ApplicationStatus();
		applicationStatus.setId(singleFieldMasterForm.getId());
		applicationStatus.setName(singleFieldMasterForm.getName().trim());
		applicationStatus.setIsActive(true);
		return applicationStatus;
	}

	public InvCampus populateFormToInvCampus(SingleFieldMasterForm singleFieldMasterForm) {
		InvCampus invCampus = new InvCampus();
		invCampus.setId(singleFieldMasterForm.getId());
		invCampus.setName(singleFieldMasterForm.getName().trim());
		invCampus.setIsActive(true); // in add and edit we can set this as true
		return invCampus;
	}

	public CCGroup populateFormToccGroup( SingleFieldMasterForm singleFieldMasterForm) { 
		CCGroup ccGroup = new CCGroup();
		ccGroup.setId(singleFieldMasterForm.getId());
		ccGroup.setName(singleFieldMasterForm.getName().trim());
		ccGroup.setIsActive(true); // in add and edit we can set this as true
		return ccGroup;
	}

	public InvCompany populateFormToInvCompany(SingleFieldMasterForm singleFieldMasterForm) {
		InvCompany invCompany = new InvCompany();
		invCompany.setId(singleFieldMasterForm.getId());
		invCompany.setName(singleFieldMasterForm.getName().trim());
		invCompany.setIsActive(true); // in add and edit we can set this as true
		return invCompany;
	}
	
	public ApplicantFeedback populateFormToApplicantFeedback(
			SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		ApplicantFeedback applicantFeedback = new ApplicantFeedback();
		applicantFeedback.setId(singleFieldMasterForm.getId());
		applicantFeedback.setName(singleFieldMasterForm.getName().trim());
		applicantFeedback.setIsActive(true); // in add and edit we can set this as
		// true
		return applicantFeedback;
	}
	public DisciplineBo populateFormToDisciplineBo(	SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		DisciplineBo disciplineBo = new DisciplineBo();
		disciplineBo.setId(singleFieldMasterForm.getId());
		disciplineBo.setName(singleFieldMasterForm.getName().trim());
		disciplineBo.setIsActive(true); // in add and edit we can set this as
		// true
		return disciplineBo;
	}
	public LocationBo populateFormToLocationBo(SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		LocationBo locationBo = new LocationBo();
		locationBo.setId(singleFieldMasterForm.getId());
		locationBo.setName(singleFieldMasterForm.getName().trim());
		locationBo.setIsActive(true); // in add and edit we can set this as
		// true
		return locationBo;
	}
	/*public FineCategoryBo populateFormToFineCategoryBo(SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		log.debug("inside populateFormToCourseScheme");
		FineCategoryBo fineCategoryBo = new FineCategoryBo();
		fineCategoryBo.setId(singleFieldMasterForm.getId());
		fineCategoryBo.setName(singleFieldMasterForm.getName().trim());
		fineCategoryBo.setIsActive(true); // in add and edit we can set this as
		// true
		log.debug("leaving populateFormToCourseScheme");
		return fineCategoryBo;
	}*/

	public PhdResearchPublication populateFormToPhdresearchPublication(SingleFieldMasterForm singleFieldMasterForm) {
		PhdResearchPublication researchPublication = new PhdResearchPublication();
		researchPublication.setId(singleFieldMasterForm.getId());
		researchPublication.setName(singleFieldMasterForm.getName().trim());
		researchPublication.setIsActive(true); // in add and edit we can set this as
		// true
		return researchPublication;
	}
	public BlockDetails populateFormToBlockDetails( SingleFieldMasterForm singleFieldMasterForm) { 
		BlockDetails block = new BlockDetails();
		block.setId(singleFieldMasterForm.getId());
		block.setBlockName(singleFieldMasterForm.getName().trim());
		block.setIsActive(true); // in add and edit we can set this as true
		return block;
	}
	public BookingRequirements populateFormToBookingRequirements( SingleFieldMasterForm singleFieldMasterForm) { 
		BookingRequirements requirements = new BookingRequirements();
		requirements.setId(singleFieldMasterForm.getId());
		requirements.setName(singleFieldMasterForm.getName().trim());
		requirements.setIsActive(true); // in add and edit we can set this as true
		return requirements;
	}
	public EventLocation populateFormToEventLocationBo(SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		EventLocation eventLocation = new EventLocation();
		eventLocation.setId(singleFieldMasterForm.getId());
		eventLocation.setName(singleFieldMasterForm.getName().trim());
		eventLocation.setIsActive(true); // in add and edit we can set this as
		// true
		return eventLocation;
	}

	public ClassGroup populateFormToClassGroupBO( SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		ClassGroup classGroup = new ClassGroup();
		classGroup.setId(singleFieldMasterForm.getId());
		classGroup.setName(singleFieldMasterForm.getName().trim());
		classGroup.setIsActive(true);
		
		return classGroup;
	}

	public ExamInviligatorExemption populateFormToExamInviligatorExemptionBO( SingleFieldMasterForm singleFieldMasterForm) {
		ExamInviligatorExemption examInviligatorExemption = new ExamInviligatorExemption();
		examInviligatorExemption.setId(singleFieldMasterForm.getId());
		examInviligatorExemption.setExemption(singleFieldMasterForm.getName().trim());
		examInviligatorExemption.setIsActive(true);
		
		return examInviligatorExemption;
	}
	
	public SubjectCodeGroup populateFormToSubjectCodeGroup( SingleFieldMasterForm singleFieldMasterForm) {
		SubjectCodeGroup subjectCodeGroup = new SubjectCodeGroup();
		subjectCodeGroup.setId(singleFieldMasterForm.getId());
		subjectCodeGroup.setSubjectsGroupName(singleFieldMasterForm.getName().trim());
		subjectCodeGroup.setIsActive(true);
		return subjectCodeGroup;
	}
	public EmployeeSubject populateFormToEmployeeSubject(SingleFieldMasterForm singleFieldMasterForm) {
		EmployeeSubject empSubject = new EmployeeSubject();
		empSubject.setId(singleFieldMasterForm.getId());
		empSubject.setSubjectName(singleFieldMasterForm.getName().trim());
		empSubject.setIsActive(true);
		return empSubject;
	}

	public Services populateFormToServices(SingleFieldMasterForm singleFieldMasterForm) {
		Services services = new Services();
		services.setId(singleFieldMasterForm.getId());
		services.setName(singleFieldMasterForm.getName().trim());
		services.setIsActive(true);
		return services;
	}

}
