package com.kp.cms.helpers.employee;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpDependents;
import com.kp.cms.bo.admin.EmpImmigration;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.PfGratuityNominees;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpAcademicQualificationBO;
import com.kp.cms.bo.employee.EmpAwardDetailsBO;
import com.kp.cms.bo.employee.EmpBooksPublishedDetailsBO;
import com.kp.cms.bo.employee.EmpDutiesDetailsBO;
import com.kp.cms.bo.employee.EmpEducationalDetails;
import com.kp.cms.bo.employee.EmpFeeConcession;
import com.kp.cms.bo.employee.EmpFinancial;
import com.kp.cms.bo.employee.EmpGuideshipDetailsBo;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpIncentives;
import com.kp.cms.bo.employee.EmpIntrestsBO;
import com.kp.cms.bo.employee.EmpLoan;
import com.kp.cms.bo.employee.EmpMemeberShipDetailsBO;
import com.kp.cms.bo.employee.EmpPaperPresentationBO;
import com.kp.cms.bo.employee.EmpPayAllowanceDetails;
import com.kp.cms.bo.employee.EmpPreviousExperience;
import com.kp.cms.bo.employee.EmpProfessionalDevelopmentBO;
import com.kp.cms.bo.employee.EmpProjectResearchDetailsBO;
import com.kp.cms.bo.employee.EmpReaserchPublishcationDetailsBO;
import com.kp.cms.bo.employee.EmpRemarks;
import com.kp.cms.bo.employee.EmpResearchBo;
import com.kp.cms.bo.employee.EmpSeminarAttendedDetailsBO;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.forms.employee.EmployeeInfoEditNewForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.PfGratuityNomineesTO;
import com.kp.cms.to.auditorium.EmpPaperPresentationTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpAcademicQualificationTO;
import com.kp.cms.to.employee.EmpAwardTO;
import com.kp.cms.to.employee.EmpBooksPublishedTO;
import com.kp.cms.to.employee.EmpDutiesPerformedTO;
import com.kp.cms.to.employee.EmpFeeConcessionTO;
import com.kp.cms.to.employee.EmpFieldResearchTO;
import com.kp.cms.to.employee.EmpFinancialTO;
import com.kp.cms.to.employee.EmpGuideShipDetailsTO;
import com.kp.cms.to.employee.EmpImagesTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.to.employee.EmpInterestsTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.EmpMemberShipAcademicBodyTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpProfessionalDevelopmentTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpRemarksTO;
import com.kp.cms.to.employee.EmpResearchProjectTO;
import com.kp.cms.to.employee.EmpResearchPublicationTO;
import com.kp.cms.to.employee.EmpResearchTO;
import com.kp.cms.to.employee.EmpSeminarAttendedDetailsTO;
import com.kp.cms.to.employee.EmployeeInfoEditNewTO;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.employee.IEmployeeEditInfoNewTransaction;

import com.kp.cms.transactionsimpl.employee.EmployeeEditInfoNewTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EmployeeInfoEditComparator;
import com.kp.cms.utilities.PayAllowance;

public class EmployeeEditInfoNewHelper {
	
	private static volatile EmployeeEditInfoNewHelper instance = null;

	/**
		 * 
		 */
	private EmployeeEditInfoNewHelper() {

	}

	/**
	 * @return
	 */
	public static EmployeeEditInfoNewHelper getInstance() {
		if (instance == null) {
			instance = new EmployeeEditInfoNewHelper();
		}
		return instance;
	}

	IEmployeeEditInfoNewTransaction txn = new EmployeeEditInfoNewTransactionImpl();

	public List<EmployeeTO> convertEmployeeTOtoBO(List<Employee> employeelist,
			int departmentId, int designationId) throws Exception {
		List<Department> departmentList = txn.getEmployeeDepartment();
		List<Designation> designationList = txn.getEmployeeDesignation();

		List<EmployeeTO> employeeTos = new ArrayList<EmployeeTO>();
		String name = "";
		if (employeelist != null) {
			Iterator<Employee> stItr = employeelist.iterator();
			while (stItr.hasNext()) {
				name = "";
				Employee emp = (Employee) stItr.next();
				EmployeeTO empTo = new EmployeeTO();
				if (emp.getId() > 0) {
					empTo.setId(emp.getId());
				}
				if (emp.getUid() != null) {
					empTo.setDummyUid(emp.getUid());
				}
				if (emp.getCode() != null) {

					empTo.setDummyCode(emp.getCode());
				}
				if (emp.getFingerPrintId() != null) {

					empTo.setDummyFingerPrintId(emp.getFingerPrintId());
				}
				if (emp.getFirstName() != null) {

					empTo.setDummyFirstName(emp.getFirstName().toUpperCase());
				}

				if (emp.getDesignation() != null
						&& emp.getDesignation().getId() > 0) {
					int DesigId = emp.getDesignation().getId();
					String DesigName = null;
					if (designationList != null) {
						Iterator<Designation> desItr = designationList
								.iterator();
						while (desItr.hasNext()) {
							Designation des = (Designation) desItr.next();
							int desigId = des.getId();
							if (desigId == DesigId) {
								DesigName = des.getName();
							}
						}
					}
					empTo.setDummyDesignationName(DesigName);
				}
				if (emp.getDepartment() != null
						&& emp.getDepartment().getId() > 0) {
					int DepId = emp.getDepartment().getId();
					String DepName = null;
					if (departmentList != null) {
						Iterator<Department> depItr = departmentList.iterator();
						while (depItr.hasNext()) {
							Department dep = (Department) depItr.next();
							int depId = dep.getId();
							if (depId == DepId) {
								DepName = dep.getName();
							}
						}
					}
					empTo.setDummyDepartmentName(DepName);
				}
			if (emp.getEmpImages() != null && emp.getEmpImages().size() > 0) {
					Iterator<EmpImages> itr=emp.getEmpImages().iterator();
					while (itr.hasNext()) {
						EmpImages bo =itr.next();
						if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
							empTo.setIsPhoto(true);
							break;
						}
					}
				}
				employeeTos.add(empTo);
			}
		}
		return employeeTos;
	}

	public void convertBoToForm(Employee empApplicantDetails,EmployeeInfoEditNewForm objform) throws Exception {

		if (empApplicantDetails != null) {
		
			if (empApplicantDetails.getId() > 0) {
				objform.setEmployeeId(String.valueOf(empApplicantDetails
						.getId()));
			}
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getTeachingStaff()))) {
				String Value = String.valueOf(empApplicantDetails
						.getTeachingStaff());
				if (Value.equals("true"))
					objform.setTeachingStaff("1");
				else
					objform.setTeachingStaff("0");

			}
			
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getCurrentlyWorking()))) {
				String Value = String.valueOf(empApplicantDetails
						.getCurrentlyWorking());
				if (Value.equals("true"))
					objform.setCurrentlyWorking("YES");
				else
					objform.setCurrentlyWorking("NO");

			}
		
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getActive()))) {
				String Value = String.valueOf(empApplicantDetails.getActive());
				if (Value.equals("true"))
					objform.setActive("1");
				else
					objform.setActive("0");

			}
			
			if (empApplicantDetails.getReligionId() != null
					&& empApplicantDetails.getReligionId().getId() > 0) {
				objform.setReligionId(String.valueOf(empApplicantDetails
						.getReligionId().getId()));
			}
			
			
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getFirstName())
					&& empApplicantDetails.getFirstName() != null) {
				objform.setName(empApplicantDetails.getFirstName().toUpperCase());
			}
			
			
			if (empApplicantDetails.getDepartment() != null
					&& empApplicantDetails.getDepartment().getId() > 0) {
				objform.setDepartmentId(String.valueOf(empApplicantDetails
						.getDepartment().getId()));
			}
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getFingerPrintId())
					&& empApplicantDetails.getFingerPrintId() != null) {
				objform
						.setFingerPrintId(empApplicantDetails
								.getFingerPrintId());
			}
			if (empApplicantDetails.getGender() != null
					&& !empApplicantDetails.getGender().isEmpty()) {
				objform.setGender(empApplicantDetails.getGender());
			}
			
			
			
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getEmail())
					&& empApplicantDetails.getEmail() != null) {
				objform
						.setEmail(String
								.valueOf(empApplicantDetails.getEmail()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(String.valueOf(empApplicantDetails
						.getWorkEmail()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails
					.getCurrentAddressMobile1())
					&& empApplicantDetails.getCurrentAddressMobile1() != null) {
				objform.setMobileNo1(empApplicantDetails
						.getCurrentAddressMobile1());
			}
			
			
			

			if (empApplicantDetails.getReligionId() != null
					&& empApplicantDetails.getReligionId().getId() > 0) {
				objform.setReligionId(String.valueOf(empApplicantDetails
						.getReligionId().getId()));
			}
			if (empApplicantDetails.getEmptype() != null
					&& empApplicantDetails.getEmptype().getId() > 0) {
				objform.setEmpTypeId(String.valueOf(empApplicantDetails
						.getEmptype().getId()));
			}
			
			
			
			if (empApplicantDetails.getCountryByPermanentAddressCountryId() != null
					&& empApplicantDetails
							.getCountryByPermanentAddressCountryId().getId() > 0) {
				objform.setCountryId(String.valueOf(empApplicantDetails
						.getCountryByPermanentAddressCountryId().getId()));
			}
			

			if (StringUtils
					.isNotEmpty(empApplicantDetails.getDesignationName())
					&& empApplicantDetails.getDesignationName() != null) {
				objform
						.setDesignation(empApplicantDetails
								.getDesignationName());
			}
			
			
			if (empApplicantDetails.getDesignation() != null
					&& empApplicantDetails.getDesignation().getId() > 0) {
				objform.setDesignationId(String.valueOf(empApplicantDetails
						.getDesignation().getId()));
			}
			
		
			
			

			if (empApplicantDetails.getEmpQualificationLevel() != null
					&& empApplicantDetails.getEmpQualificationLevel().getId() > 0) {
				objform.setEmpQualificationLevelId(String.valueOf(empApplicantDetails
						.getEmpQualificationLevel().getId()));
			}
			
			
			if (empApplicantDetails.getDob() != null
					&& !empApplicantDetails.getDob().toString().isEmpty()) {
				objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getDob().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
			}
			
			
			if (empApplicantDetails.getDoj() != null
					&& !empApplicantDetails.getDoj().toString().isEmpty()) {
				objform.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getDoj().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
			}
			
			
			if (empApplicantDetails.getTotalExpMonths() != null
					&& !empApplicantDetails.getTotalExpMonths().isEmpty()) {
				objform.setCurrentExpMonths(Integer.parseInt(empApplicantDetails
						.getTotalExpMonths()));
			}
			if (empApplicantDetails.getTotalExpYear() != null
					&& !empApplicantDetails.getTotalExpYear().isEmpty()) {
				objform.setCurrentExpYears(Integer.parseInt(empApplicantDetails
						.getTotalExpYear()));
			}
			
			if (empApplicantDetails.getRelevantExpMonths() != null
					&& !empApplicantDetails.getRelevantExpMonths().isEmpty()) {
				objform.setExpMonths(String.valueOf(empApplicantDetails
						.getRelevantExpMonths()));
			}
			if (empApplicantDetails.getRelevantExpYears() != null
					&& !empApplicantDetails.getTotalExpYear().isEmpty()) {
				objform.setExpYears(String.valueOf(empApplicantDetails
						.getRelevantExpYears()));
			}

			
			

			
			
			
			if (empApplicantDetails.getStateByPermanentAddressStateId() != null
					&& empApplicantDetails.getStateByPermanentAddressStateId()
							.getId() > 0) {
				objform.setStateId(String.valueOf(empApplicantDetails
						.getStateByPermanentAddressStateId().getId()));
			}
			if (empApplicantDetails.getStreamId() != null
					&& empApplicantDetails.getStreamId().getId() > 0) {
				objform.setStreamId(String.valueOf(empApplicantDetails
						.getStreamId().getId()));

			}

			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
			}
			
			if (empApplicantDetails.getEmptype() != null
					&& empApplicantDetails.getEmptype().getId() > 0) {
				objform.setEmpTypeId(String.valueOf(empApplicantDetails
						.getEmptype().getId()));
			}
			
			Map<String,String> designationMap=txn.getDesignationMap();
			 if(designationMap!=null){
				 objform.setDesignationMap(designationMap);
				// employeeInfoEditForm.setPostAppliedMap(designationMap);
			 }
			 
			 Map<String,String> departmentMap=txn.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 objform.setDepartmentMap(departmentMap);
				 
		}
			 
			 if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressLine1())
						&& empApplicantDetails.getPermanentAddressLine1() != null) {
					objform.setAddressLine1(empApplicantDetails
							.getPermanentAddressLine1());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressLine2())
						&& empApplicantDetails.getPermanentAddressLine2() != null) {
					objform.setAddressLine2(empApplicantDetails
							.getPermanentAddressLine2());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressCity())
						&& empApplicantDetails.getPermanentAddressCity() != null) {
					objform.setCity(empApplicantDetails.getPermanentAddressCity());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressZip())
						&& empApplicantDetails.getPermanentAddressZip() != null) {
					objform.setPermanentZipCode(empApplicantDetails
							.getPermanentAddressZip());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressStateOthers())
						&& empApplicantDetails.getPermanentAddressStateOthers() != null) {
					objform.setOtherPermanentState(empApplicantDetails
							.getPermanentAddressStateOthers());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressLine1())
						&& empApplicantDetails.getCommunicationAddressLine1() != null) {
					objform.setCurrentAddressLine1(empApplicantDetails
							.getCommunicationAddressLine1());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressLine2())
						&& empApplicantDetails.getCommunicationAddressLine2() != null) {
					objform.setCurrentAddressLine2(empApplicantDetails
							.getCommunicationAddressLine2());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressCity())
						&& empApplicantDetails.getCommunicationAddressCity() != null) {
					objform.setCurrentCity(empApplicantDetails
							.getCommunicationAddressCity());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressStateOthers())
						&& empApplicantDetails.getCommunicationAddressStateOthers() != null) {
					objform.setOtherCurrentState(empApplicantDetails
							.getCommunicationAddressStateOthers());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressZip())
						&& empApplicantDetails.getCommunicationAddressZip() != null) {
					objform.setCurrentZipCode(empApplicantDetails
							.getCommunicationAddressZip());
				}
				if (empApplicantDetails.getCountryByPermanentAddressCountryId() != null
						&& empApplicantDetails
								.getCountryByPermanentAddressCountryId().getId() > 0) {
					objform.setCountryId(String.valueOf(empApplicantDetails
							.getCountryByPermanentAddressCountryId().getId()));
				}
				if (empApplicantDetails.getCountryByCommunicationAddressCountryId() != null
						&& empApplicantDetails
								.getCountryByCommunicationAddressCountryId()
								.getId() > 0) {
					objform.setCurrentCountryId(String.valueOf(empApplicantDetails
							.getCountryByCommunicationAddressCountryId().getId()));
				}
				if (empApplicantDetails.getCurrentAddressHomeTelephone1() != null
						&& !empApplicantDetails.getCurrentAddressHomeTelephone1()
								.isEmpty()) {
					objform.setHomePhone1(empApplicantDetails
							.getCurrentAddressHomeTelephone1());
				}

				if (empApplicantDetails.getCurrentAddressHomeTelephone2() != null
						&& !empApplicantDetails.getCurrentAddressHomeTelephone2()
								.isEmpty()) {
					objform.setHomePhone2(empApplicantDetails
							.getCurrentAddressHomeTelephone2());
				}

				if (empApplicantDetails.getCurrentAddressHomeTelephone3() != null
						&& !empApplicantDetails.getCurrentAddressHomeTelephone3()
								.isEmpty()) {
					objform.setHomePhone3(empApplicantDetails
							.getCurrentAddressHomeTelephone3());
				}
				if (empApplicantDetails.getCurrentAddressWorkTelephone1() != null
						&& !empApplicantDetails.getCurrentAddressWorkTelephone1()
								.isEmpty()) {
					objform.setWorkPhNo1(empApplicantDetails
							.getCurrentAddressWorkTelephone1());
				}

				if (empApplicantDetails.getCurrentAddressWorkTelephone2() != null
						&& !empApplicantDetails.getCurrentAddressWorkTelephone2()
								.isEmpty()) {
					objform.setWorkPhNo2(empApplicantDetails
							.getCurrentAddressWorkTelephone2());
				}

				if (empApplicantDetails.getCurrentAddressWorkTelephone3() != null
						&& !empApplicantDetails.getCurrentAddressWorkTelephone3()
								.isEmpty()) {
					objform.setWorkPhNo3(empApplicantDetails
							.getCurrentAddressWorkTelephone3());
				}

				if (StringUtils
						.isNotEmpty(empApplicantDetails.getOrganistionName())
						&& empApplicantDetails.getOrganistionName() != null) {
					objform.setOrgAddress(empApplicantDetails.getOrganistionName());
				}
				if (empApplicantDetails.getDesignation() != null
						&& empApplicantDetails.getDesignation().getId() > 0) {
					objform.setDesignationPfId(String.valueOf(empApplicantDetails
							.getDesignation().getId()));
				}
				if (empApplicantDetails.getAlbumDesignation() != null
						&& empApplicantDetails.getAlbumDesignation().getId() > 0) {
					objform.setAlbumDesignation(String.valueOf(empApplicantDetails
							.getAlbumDesignation().getId()));
				}
				
				if (empApplicantDetails.getBooks() != null
						&& !empApplicantDetails.getBooks().isEmpty()) {
					objform
							.setBooks(String
									.valueOf(empApplicantDetails.getBooks()));
				}
				if (empApplicantDetails.getNoOfPublicationsNotRefered() != null
						&& !empApplicantDetails.getNoOfPublicationsNotRefered()
								.isEmpty()) {
					objform.setNoOfPublicationsNotRefered(empApplicantDetails
							.getNoOfPublicationsNotRefered());
				}
				if (empApplicantDetails.getNoOfPublicationsRefered() != null
						&& !empApplicantDetails.getNoOfPublicationsRefered()
								.isEmpty()) {
					objform.setNoOfPublicationsRefered(empApplicantDetails
							.getNoOfPublicationsRefered());
				}

				if (empApplicantDetails.getEmpQualificationLevel() != null
						&& empApplicantDetails.getEmpQualificationLevel().getId() > 0) {
					objform.setQualificationId(String.valueOf(empApplicantDetails
							.getEmpQualificationLevel().getId()));
				}
				
				if (empApplicantDetails.getDateOfLeaving() != null
						&& !empApplicantDetails.getDateOfLeaving().toString()
								.isEmpty()) {
					objform.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(
							empApplicantDetails.getDateOfLeaving().toString(),
							"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if (empApplicantDetails.getDateOfResignation() != null
						&& !empApplicantDetails.getDateOfResignation().toString()
								.isEmpty()) {
					objform.setDateOfResignation(CommonUtil
							.ConvertStringToDateFormat(empApplicantDetails
									.getDateOfResignation().toString(),
									"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				
				if (empApplicantDetails.getRetirementDate() != null
						&& !empApplicantDetails.getRetirementDate().toString()
								.isEmpty()) {
					objform.setRetirementDate(CommonUtil.ConvertStringToDateFormat(
							empApplicantDetails.getRetirementDate().toString(),
							"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getEmergencyContName())
						&& empApplicantDetails.getEmergencyContName() != null) {
					objform.setEmContactName(empApplicantDetails
							.getEmergencyContName());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getEmergencyHomeTelephone())
						&& empApplicantDetails.getEmergencyHomeTelephone() != null) {
					objform.setEmContactHomeTel(empApplicantDetails
							.getEmergencyHomeTelephone());
				}
				if (StringUtils
						.isNotEmpty(empApplicantDetails.getEmergencyMobile())
						&& empApplicantDetails.getEmergencyMobile() != null) {
					objform.setEmContactMobile(empApplicantDetails
							.getEmergencyMobile());
				}
				if (StringUtils
						.isNotEmpty(empApplicantDetails.getEmContactAddress())
						&& empApplicantDetails.getEmContactAddress() != null) {
					objform.setEmContactAddress(empApplicantDetails
							.getEmContactAddress());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getEmergencyWorkTelephone())
						&& empApplicantDetails.getEmergencyWorkTelephone() != null) {
					objform.setEmContactWorkTel(empApplicantDetails
							.getEmergencyWorkTelephone());
				}

				if (StringUtils.isNotEmpty(empApplicantDetails.getGrossPay())
						&& empApplicantDetails.getGrossPay() != null) {
					objform.setGrossPay(empApplicantDetails.getGrossPay());
				}
				
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getScale())
						&& empApplicantDetails.getScale() != null) {
					objform.setScale(empApplicantDetails.getScale());
				}
				if (empApplicantDetails.getStateByCommunicationAddressStateId() != null
						&& empApplicantDetails
								.getStateByCommunicationAddressStateId().getId() > 0) {
					objform.setCurrentState(String.valueOf(empApplicantDetails
							.getStateByCommunicationAddressStateId().getId()));
				}
				if (empApplicantDetails.getStateByPermanentAddressStateId() != null
						&& empApplicantDetails.getStateByPermanentAddressStateId()
								.getId() > 0) {
					objform.setStateId(String.valueOf(empApplicantDetails
							.getStateByPermanentAddressStateId().getId()));
				}
				if (empApplicantDetails.getStreamId() != null
						&& empApplicantDetails.getStreamId().getId() > 0) {
					objform.setStreamId(String.valueOf(empApplicantDetails
							.getStreamId().getId()));

				}

				
				if (empApplicantDetails.getWorkLocationId() != null
						&& empApplicantDetails.getWorkLocationId().getId() > 0) {
					objform.setWorkLocationId(String.valueOf(empApplicantDetails
							.getWorkLocationId().getId()));
				}
				if (empApplicantDetails.getEmptype() != null
						&& empApplicantDetails.getEmptype().getId() > 0) {
					objform.setEmptypeId(String.valueOf(empApplicantDetails
							.getEmptype().getId()));
				}
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
						&& empApplicantDetails.getWorkEmail() != null) {
					objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
				}
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus())
						&& empApplicantDetails.getMaritalStatus() != null) {
					objform.setMaritialStatus(empApplicantDetails.getMaritalStatus());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails.getCode())
						&& empApplicantDetails.getCode() != null) {
					objform.setCode(empApplicantDetails.getCode());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails.getReservationCategory())
						&& empApplicantDetails.getReservationCategory() != null) {
					objform.setReservationCategory(empApplicantDetails.getReservationCategory());
				}
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getIsSCDataDelivered()))) {
					String Value = String.valueOf(empApplicantDetails
							.getIsSCDataDelivered());
					if (Value.equals("true"))
						objform.setIsSmartCardDataDelivered("1");
					else
						objform.setIsSmartCardDataDelivered("0");

				}
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getIsSCDataGenerated()))) {
					String Value = String.valueOf(empApplicantDetails
							.getIsSCDataGenerated());
					if (Value.equals("true"))
						objform.setIsSmartCardDataGenerated("1");
					else
						objform.setIsSmartCardDataGenerated("0");

				}
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getIsSameAddress()))) {
					String Value = String.valueOf(empApplicantDetails
							.getIsSameAddress());
					if (Value.equals("true"))
						objform.setSameAddress("true");
					else
						objform.setSameAddress("false");

				}
				
				if (empApplicantDetails.getNationality() != null
						&& empApplicantDetails.getNationality().getId() > 0) {
					objform.setNationalityId(String.valueOf(empApplicantDetails
							.getNationality().getId()));
				}
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus())
						&& empApplicantDetails.getMaritalStatus() != null) {
					objform.setMaritialStatus(String.valueOf(empApplicantDetails
							.getMaritalStatus()));
				}
				
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getDisplayInWebsite()))) {
					String Value = String.valueOf(empApplicantDetails.getDisplayInWebsite());
					if (Value.equals("true"))
						objform.setDisplayInWebsite("1");
					else
						objform.setDisplayInWebsite("0");

				}
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getActive()))) {
					String Value = String.valueOf(empApplicantDetails.getActive());
					if (Value.equals("true"))
						objform.setActive("1");
					else
						objform.setActive("0");

				}
				
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getIsPunchingExcemption()))) {
					String Value = String.valueOf(empApplicantDetails
							.getIsPunchingExcemption());
					if (Value.equals("true"))
						objform.setIsPunchingExcemption("1");
					else
						objform.setIsPunchingExcemption("0");

				}
		
		if (empApplicantDetails.getEmpAcademicDeatils()  != null) {
			Set<EmpAcademicQualificationBO> empAcademic = empApplicantDetails
					.getEmpAcademicDeatils();
			if (empAcademic != null && !empAcademic.isEmpty()) {
				Iterator<EmpAcademicQualificationBO> iterator = empAcademic.iterator();
				List<EmpAcademicQualificationTO> empAcademicQualificationTOs = new ArrayList<EmpAcademicQualificationTO>();

				while (iterator.hasNext()) {
					EmpAcademicQualificationBO empAcademicbo = iterator.next();
					if (empAcademic != null) {
						EmpAcademicQualificationTO empAcademicTo = new EmpAcademicQualificationTO();

						if (empAcademicTo.getId() > 0) {
							empAcademicTo.setId(empAcademicbo.getId());
						}
						if (StringUtils.isNotEmpty(empAcademicbo
								.getCourseName())) {
							empAcademicTo.setCourseName(empAcademicbo
									.getCourseName());
						}

						if (StringUtils.isNotEmpty(empAcademicbo.getUniversity())) {
							empAcademicTo.setUniversityName(empAcademicbo.getUniversity());
						}
						if (StringUtils.isNotEmpty(empAcademicbo.getYear())) {
							empAcademicTo.setYear(empAcademicbo.getYear());
						}
						if (StringUtils.isNotEmpty(empAcademicbo.getGrade())) {
							empAcademicTo.setGrade(empAcademicbo.getGrade());
						}


						empAcademicQualificationTOs.add(empAcademicTo);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpqualification(empAcademicQualificationTOs);
			} else {

				List<EmpAcademicQualificationTO> flist = new ArrayList<EmpAcademicQualificationTO>();
				EmpAcademicQualificationTO empQualificationTO = new EmpAcademicQualificationTO();
				empQualificationTO.setCourseName("");
				empQualificationTO.setUniversityName("");
				empQualificationTO.setYear("");
				empQualificationTO.setGrade("");
				objform.setEmpQualificationListSize(String.valueOf(flist.size()));
				flist.add(empQualificationTO);
				EmployeeInfoEditNewTO editNewTO = new EmployeeInfoEditNewTO();
				editNewTO.setEmpqualification(flist);
				objform.setEmployeeInfoEditNewTO(editNewTO);
			}
		}
		
		if (empApplicantDetails.getEmpIntrestDetails()  != null) {
			Set<EmpIntrestsBO> empIntrestsBOs =empApplicantDetails.getEmpIntrestDetails();
			if (empIntrestsBOs != null && !empIntrestsBOs.isEmpty()) {
				Iterator<EmpIntrestsBO> iterator = empIntrestsBOs.iterator();
				List<EmpInterestsTO> empInterestsTOs = new ArrayList<EmpInterestsTO>();

				while (iterator.hasNext()) {
					EmpIntrestsBO empIntrestsBO = iterator.next();
					if (empIntrestsBO != null) {
						EmpInterestsTO empInterestsTO = new EmpInterestsTO();

						if (empInterestsTO.getId() > 0) {
							empInterestsTO.setId(empIntrestsBO.getId());
						}
						if (StringUtils.isNotEmpty(empIntrestsBO
								.getTopic())) {
							empInterestsTO.setTopic(empIntrestsBO
									.getTopic());
						}

						empInterestsTOs.add(empInterestsTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setInterest(empInterestsTOs);
			} else {

				List<EmpInterestsTO> flist1 = new ArrayList<EmpInterestsTO>();
				EmpInterestsTO empInterestsTO = new EmpInterestsTO();
				empInterestsTO.setTopic("");
				objform.setEmpIntrestListSize(String.valueOf(flist1.size()));
				flist1.add(empInterestsTO);
				objform.getEmployeeInfoEditNewTO().setInterest(flist1);
			}
		}
		
		
		if (empApplicantDetails.getEmpResearchDetails()  != null) {
			Set<EmpResearchBo> empResearchBos =empApplicantDetails.getEmpResearchDetails();
			if (empResearchBos != null && !empResearchBos.isEmpty()) {
				Iterator<EmpResearchBo> iterator = empResearchBos.iterator();
				List<EmpFieldResearchTO> empResearchTOs = new ArrayList<EmpFieldResearchTO>();

				while (iterator.hasNext()) {
					EmpResearchBo empResearchBo = iterator.next();
					if (empResearchBo != null) {
						EmpFieldResearchTO empResearchTO = new EmpFieldResearchTO();

						if (empResearchTO.getId() > 0) {
							empResearchTO.setId(empResearchBo.getId());
						}
						if (StringUtils.isNotEmpty(empResearchBo
								.getTitle())) {
							empResearchTO.setTopic(empResearchBo
									.getTitle());
						}

						empResearchTOs.add(empResearchTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setResearch(empResearchTOs);
			} else {

				List<EmpFieldResearchTO> flist1 = new ArrayList<EmpFieldResearchTO>();
				EmpFieldResearchTO empFieldResearchTO = new EmpFieldResearchTO();
				empFieldResearchTO.setTopic("");
				objform.setEmpResearchListSize(String.valueOf(flist1.size()));
				flist1.add(empFieldResearchTO);
				objform.getEmployeeInfoEditNewTO().setResearch(flist1);
			}
		}
		
		
		if (empApplicantDetails.getEmpGuideshipDetails()  != null) {
			Set<EmpGuideshipDetailsBo> empGuideshipDetailsBos =empApplicantDetails.getEmpGuideshipDetails();
			if (empGuideshipDetailsBos != null && !empGuideshipDetailsBos.isEmpty()) {
				Iterator<EmpGuideshipDetailsBo> iterator = empGuideshipDetailsBos.iterator();
				List<EmpGuideShipDetailsTO> empGuideShipDetailsTOs = new ArrayList<EmpGuideShipDetailsTO>();

				while (iterator.hasNext()) {
					EmpGuideshipDetailsBo empGuideshipDetailsBo = iterator.next();
					if (empGuideshipDetailsBo != null) {
						EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();

						if (empGuideShipDetailsTO.getId() > 0) {
							empGuideShipDetailsTO.setId(empGuideshipDetailsBo.getId());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getScholarName())) {
							empGuideShipDetailsTO.setPhdScholarName(empGuideshipDetailsBo
									.getScholarName());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getRegistraionYear())) {
							empGuideShipDetailsTO.setRegistrationYear(empGuideshipDetailsBo
									.getRegistraionYear());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getAwarded())) {
							empGuideShipDetailsTO.setAwarded(empGuideshipDetailsBo
									.getAwarded());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getAwardedYear())) {
							empGuideShipDetailsTO.setYear(empGuideshipDetailsBo
									.getAwardedYear());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getAwardedThesisName())) {
							empGuideShipDetailsTO.setThesisName(empGuideshipDetailsBo
									.getAwardedThesisName());
						}

						empGuideShipDetailsTOs.add(empGuideShipDetailsTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setGuideship(empGuideShipDetailsTOs);
			} else {

				List<EmpGuideShipDetailsTO> flist2 = new ArrayList<EmpGuideShipDetailsTO>();
				EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();
				empGuideShipDetailsTO.setAwarded("");
				empGuideShipDetailsTO.setPhdScholarName("");
				empGuideShipDetailsTO.setThesisName("");
				empGuideShipDetailsTO.setYear("");
				empGuideShipDetailsTO.setRegistrationYear("");
				objform.setEmpGuidshipListSize(String.valueOf(flist2.size()));
				flist2.add(empGuideShipDetailsTO);
				objform.getEmployeeInfoEditNewTO().setGuideship(flist2);
			}
		}
		
		if (empApplicantDetails.getEmpDutiesDeatils()  != null) {
			Set<EmpDutiesDetailsBO> empDutiesDetailsBOs =empApplicantDetails.getEmpDutiesDeatils();
			if (empDutiesDetailsBOs != null && !empDutiesDetailsBOs.isEmpty()) {
				Iterator<EmpDutiesDetailsBO> iterator = empDutiesDetailsBOs.iterator();
				List<EmpDutiesPerformedTO> empDutiesPerformedTOs = new ArrayList<EmpDutiesPerformedTO>();

				while (iterator.hasNext()) {
					EmpDutiesDetailsBO empDutiesDetailsBO = iterator.next();
					if (empDutiesDetailsBO != null) {
						EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();

						if (empDutiesPerformedTO.getId() > 0) {
							empDutiesPerformedTO.setId(empDutiesDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empDutiesDetailsBO
								.getPosition())) {
							empDutiesPerformedTO.setPositionName(empDutiesDetailsBO
									.getPosition());
						}
						if (empDutiesDetailsBO
								.getFromDate() != null) {
							empDutiesPerformedTO.setFromDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if (empDutiesDetailsBO.getToDate() != null) {
							empDutiesPerformedTO.setToDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						

						empDutiesPerformedTOs.add(empDutiesPerformedTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setDuties(empDutiesPerformedTOs);
			} else {

				List<EmpDutiesPerformedTO> flist3 = new ArrayList<EmpDutiesPerformedTO>();
				EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();
				empDutiesPerformedTO.setPositionName("");
				empDutiesPerformedTO.setFromDate("");
				empDutiesPerformedTO.setToDate("");
				objform.setEmpDutiesPerformedListSize(String.valueOf(flist3.size()));
				flist3.add(empDutiesPerformedTO);
				objform.getEmployeeInfoEditNewTO().setDuties(flist3);
			}
		}
		
		if (empApplicantDetails.getEmpProjectDetails()  != null) {
			Set<EmpProjectResearchDetailsBO> empProjectResearchDetailsBOs =empApplicantDetails.getEmpProjectDetails();
			if (empProjectResearchDetailsBOs != null && !empProjectResearchDetailsBOs.isEmpty()) {
				Iterator<EmpProjectResearchDetailsBO> iterator = empProjectResearchDetailsBOs.iterator();
				List<EmpResearchTO> empResearchProjectTOs = new ArrayList<EmpResearchTO>();

				while (iterator.hasNext()) {
					EmpProjectResearchDetailsBO empResearchDetailsBO = iterator.next();
					if (empResearchDetailsBO != null) {
						EmpResearchTO empResearchProjectTO = new EmpResearchTO();

						if (empResearchProjectTO.getId() > 0) {
							empResearchProjectTO.setId(empResearchDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle()
								)) {
							empResearchProjectTO.setProjectName(empResearchDetailsBO.getPrjectName());
						}
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle())) {
					       empResearchProjectTO.setTitle(empResearchDetailsBO.getTitle());
				        }
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getFundingAgency())) {
					   empResearchProjectTO.setFindingAgencyName(empResearchDetailsBO.getFundingAgency());
				        }
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getAmount())) {
					      empResearchProjectTO.setAmount(empResearchDetailsBO.getAmount());
				           }
						if (empResearchDetailsBO
								.getFromDate() != null) {
							empResearchProjectTO.setFromDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO
									.getFromDate() .toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if (empResearchDetailsBO.getToDate() != null) {
							empResearchProjectTO.setToDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						

						empResearchProjectTOs.add(empResearchProjectTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setResearchProject(empResearchProjectTOs);
			} else {

				List<EmpResearchTO> flist4 = new ArrayList<EmpResearchTO>();
				EmpResearchTO empResearchTO = new EmpResearchTO();
				empResearchTO.setFindingAgencyName("");
				empResearchTO.setAmount("");
				empResearchTO.setProjectName("");
				empResearchTO.setTitle("");
				empResearchTO.setFromDate1("");
				empResearchTO.setToDate1("");
				objform.setEmpResearchProjectListSize(String.valueOf(flist4.size()));
				flist4.add(empResearchTO);
				objform.getEmployeeInfoEditNewTO().setResearchProject(flist4);
			}
		}
		
		if (empApplicantDetails.getEmpResearchPublication() != null) {
			Set<EmpReaserchPublishcationDetailsBO> empProjePublishcationDetailsBOs =empApplicantDetails.getEmpResearchPublication();
			if (empProjePublishcationDetailsBOs != null && !empProjePublishcationDetailsBOs.isEmpty()) {
				Iterator<EmpReaserchPublishcationDetailsBO> iterator = empProjePublishcationDetailsBOs.iterator();
				List<EmpResearchPublicationTO> empResearchPublicationTOs = new ArrayList<EmpResearchPublicationTO>();

				while (iterator.hasNext()) {
					EmpReaserchPublishcationDetailsBO empReaserchPublishcationDetailsBO = iterator.next();
					if (empReaserchPublishcationDetailsBO != null) {
						EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();

						if (empResearchPublicationTO.getId() > 0) {
							empResearchPublicationTO.setId(empReaserchPublishcationDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getTitle())) {
							empResearchPublicationTO.setPaperTitle(empReaserchPublishcationDetailsBO.getTitle());
						}
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getJournalName())) {
							empResearchPublicationTO.setJournalName(empReaserchPublishcationDetailsBO.getJournalName());
				        }
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getUGC())) {
							empResearchPublicationTO.setUGCNonUGC(empReaserchPublishcationDetailsBO.getUGC());
				        }
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getISBNISSNNo())) {
							empResearchPublicationTO.setISBNISSNNo(empReaserchPublishcationDetailsBO.getISBNISSNNo());
				        }
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getYear())) {
							empResearchPublicationTO.setYear(empReaserchPublishcationDetailsBO.getYear());
				        }
						
						empResearchPublicationTOs.add(empResearchPublicationTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setResearchPubliction(empResearchPublicationTOs);
			} else {

				List<EmpResearchPublicationTO> flist5 = new ArrayList<EmpResearchPublicationTO>();
				EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();
				empResearchPublicationTO.setISBNISSNNo("");
				empResearchPublicationTO.setJournalName("");
				empResearchPublicationTO.setPaperTitle("");
				empResearchPublicationTO.setUGCNonUGC("");
				empResearchPublicationTO.setYear("");
				
				objform.setEmpResearchPublicationListSize(String.valueOf(flist5.size()));
				flist5.add(empResearchPublicationTO);
				objform.getEmployeeInfoEditNewTO().setResearchPubliction(flist5);
			}
		}
		
		if (empApplicantDetails.getEmpBooksPublished() != null) {
			Set<EmpBooksPublishedDetailsBO> empBooksPublishedDetailsBOs =empApplicantDetails.getEmpBooksPublished();
			if (empBooksPublishedDetailsBOs != null && !empBooksPublishedDetailsBOs.isEmpty()) {
				Iterator<EmpBooksPublishedDetailsBO> iterator = empBooksPublishedDetailsBOs.iterator();
				List<EmpBooksPublishedTO> empBooksPublishedTOs = new ArrayList<EmpBooksPublishedTO>();

				while (iterator.hasNext()) {
					EmpBooksPublishedDetailsBO empBooksPublishedDetailsBO = iterator.next();
					if (empBooksPublishedDetailsBO != null) {
						EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();

						if (empBooksPublishedTO.getId() > 0) {
							empBooksPublishedTO.setId(empBooksPublishedDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getBookName())) {
							empBooksPublishedTO.setTitleName(empBooksPublishedDetailsBO.getBookName());
						}
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getPublisherName())) {
							empBooksPublishedTO.setPublisherName(empBooksPublishedDetailsBO.getPublisherName());
				        }
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getContribution())) {
							empBooksPublishedTO.setContibutionName(empBooksPublishedDetailsBO.getContribution());
				        }
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getYear())) {
							empBooksPublishedTO.setYear(empBooksPublishedDetailsBO.getYear());
				        }
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getISBNISSNNo())) {
							empBooksPublishedTO.setISBNISSN(empBooksPublishedDetailsBO.getISBNISSNNo());
				        }
						
						empBooksPublishedTOs.add(empBooksPublishedTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpBooks(empBooksPublishedTOs);
			} else {

				List<EmpBooksPublishedTO> flist6 = new ArrayList<EmpBooksPublishedTO>();
				EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();
				empBooksPublishedTO.setContibutionName("");
				empBooksPublishedTO.setISBNISSN("");
				empBooksPublishedTO.setPublisherName("");
				empBooksPublishedTO.setTitleName("");
				empBooksPublishedTO.setYear("");
				
				objform.setEmpBooksPublishListSize((String.valueOf(flist6.size())));
				flist6.add(empBooksPublishedTO);
				objform.getEmployeeInfoEditNewTO().setEmpBooks(flist6);
			}
		}
		
		if (empApplicantDetails.getEmpPaperPresentaion() != null) {
			Set<EmpPaperPresentationBO> empPaperPresentationBOs =empApplicantDetails.getEmpPaperPresentaion();
			if (empPaperPresentationBOs != null && !empPaperPresentationBOs.isEmpty()) {
				Iterator<EmpPaperPresentationBO> iterator = empPaperPresentationBOs.iterator();
				List<EmpPaperPresentationTO> empPresentationTOs = new ArrayList<EmpPaperPresentationTO>();

				while (iterator.hasNext()) {
					EmpPaperPresentationBO empPaperPresentationBO = iterator.next();
					if (empPaperPresentationBO != null) {
						EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();

						if (empPaperPresentationTO.getId() > 0) {
							empPaperPresentationTO.setId(empPaperPresentationBO.getId());
						}
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getInstitution())) {
							empPaperPresentationTO.setOrganisation(empPaperPresentationBO.getInstitution());
						}
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getInterRegoinal())) {
							empPaperPresentationTO.setRegional(empPaperPresentationBO.getInterRegoinal());
				        }
						if (empPaperPresentationBO.getDate() != null) {
							empPaperPresentationTO.setDate1(CommonUtil.ConvertStringToDateFormat(empPaperPresentationBO.getDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getProceedingsTile())) {
							empPaperPresentationTO.setSeminarName(empPaperPresentationBO.getProceedingsTile());
				        }
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getPaperTile())) {
							empPaperPresentationTO.setPaperName(empPaperPresentationBO.getPaperTile());
				        }
						
						empPresentationTOs.add(empPaperPresentationTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpPaper(empPresentationTOs);
			} else {

				List<EmpPaperPresentationTO> flist7 = new ArrayList<EmpPaperPresentationTO>();
				EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();
				empPaperPresentationTO.setDate1("");
				empPaperPresentationTO.setOrganisation("");
				empPaperPresentationTO.setPaperName("");
				empPaperPresentationTO.setRegional("");
				empPaperPresentationTO.setSeminarName("");
				
				objform.setEmpPaperPrsentationListSize((String.valueOf(flist7.size())));
				flist7.add(empPaperPresentationTO);
				objform.getEmployeeInfoEditNewTO().setEmpPaper(flist7);
			}
		}
		
		if (empApplicantDetails.getEmpSeminarAttended() != null) {
			Set<EmpSeminarAttendedDetailsBO> empSeminarAttendedDetailsBOs =empApplicantDetails.getEmpSeminarAttended();
			if (empSeminarAttendedDetailsBOs != null && !empSeminarAttendedDetailsBOs.isEmpty()) {
				Iterator<EmpSeminarAttendedDetailsBO> iterator = empSeminarAttendedDetailsBOs.iterator();
				List<EmpSeminarAttendedDetailsTO> empSeminarAttendedDetailsTOs = new ArrayList<EmpSeminarAttendedDetailsTO>();

				while (iterator.hasNext()) {
					EmpSeminarAttendedDetailsBO empSeminarAttendedDetailsBO = iterator.next();
					if (empSeminarAttendedDetailsBO != null) {
						EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();

						if (empSeminarAttendedDetailsTO.getId() > 0) {
							empSeminarAttendedDetailsTO.setId(empSeminarAttendedDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInstitution())) {
							empSeminarAttendedDetailsTO.setOrganisation(empSeminarAttendedDetailsBO.getInstitution());
						}
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInterRegional())) {
							empSeminarAttendedDetailsTO.setInterRegional(empSeminarAttendedDetailsBO.getInterRegional());
				        }
						if (empSeminarAttendedDetailsBO.getFromDate2() != null) {
							empSeminarAttendedDetailsTO.setFromDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getFromDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (empSeminarAttendedDetailsBO.getToDate2() != null) {
							empSeminarAttendedDetailsTO.setToDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getToDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getParticipation())) {
							empSeminarAttendedDetailsTO.setParticipation(empSeminarAttendedDetailsBO.getParticipation());
				        }
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getTitle())) {
							empSeminarAttendedDetailsTO.setSeminarName(empSeminarAttendedDetailsBO.getTitle());
				        }
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getWorkShop())) {
							empSeminarAttendedDetailsTO.setSeminar(empSeminarAttendedDetailsBO.getWorkShop());
				        }
						
						empSeminarAttendedDetailsTOs.add(empSeminarAttendedDetailsTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
			} else {

				List<EmpSeminarAttendedDetailsTO> flist8 = new ArrayList<EmpSeminarAttendedDetailsTO>();
				EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();
				empSeminarAttendedDetailsTO.setFromDate2("");
				empSeminarAttendedDetailsTO.setInterRegional("");
				empSeminarAttendedDetailsTO.setOrganisation("");
				empSeminarAttendedDetailsTO.setSeminar("");
				empSeminarAttendedDetailsTO.setSeminarName("");
				empSeminarAttendedDetailsTO.setToDate2("");
				
				
				objform.setEmpSeminarattendedListSize((String.valueOf(flist8.size())));
				flist8.add(empSeminarAttendedDetailsTO);
				objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(flist8);
			}
		}
		
		if (empApplicantDetails.getEmpProfeDevelopment() != null) {
			Set<EmpProfessionalDevelopmentBO> empProfessionalDevelopmentBOs =empApplicantDetails.getEmpProfeDevelopment();
			if (empProfessionalDevelopmentBOs != null && !empProfessionalDevelopmentBOs.isEmpty()) {
				Iterator<EmpProfessionalDevelopmentBO> iterator = empProfessionalDevelopmentBOs.iterator();
				List<EmpProfessionalDevelopmentTO> empProfessionalDevelopmentTOs = new ArrayList<EmpProfessionalDevelopmentTO>();

				while (iterator.hasNext()) {
					EmpProfessionalDevelopmentBO empProfessionalDevelopmentBO = iterator.next();
					if (empProfessionalDevelopmentBO != null) {
						EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();

						if (empProfessionalDevelopmentTO.getId() > 0) {
							empProfessionalDevelopmentTO.setId(empProfessionalDevelopmentBO.getId());
						}
						if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getName())) {
							empProfessionalDevelopmentTO.setName(empProfessionalDevelopmentBO.getName());
						}
						if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getInstitute())) {
							empProfessionalDevelopmentTO.setOrganisation(empProfessionalDevelopmentBO.getInstitute());
				        }
						if (empProfessionalDevelopmentBO.getFromDate() != null) {
							empProfessionalDevelopmentTO.setFromDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (empProfessionalDevelopmentBO.getToDate() != null) {
							empProfessionalDevelopmentTO.setToDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						
						empProfessionalDevelopmentTOs.add(empProfessionalDevelopmentTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
			} else {

				List<EmpProfessionalDevelopmentTO> flist9 = new ArrayList<EmpProfessionalDevelopmentTO>();
				EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();
				empProfessionalDevelopmentTO.setFromDate3("");
				empProfessionalDevelopmentTO.setName("");
				empProfessionalDevelopmentTO.setOrganisation("");
				empProfessionalDevelopmentTO.setToDate3("");
				
				
				objform.setEmpProfessionalDevelopmentListSize((String.valueOf(flist9.size())));
				flist9.add(empProfessionalDevelopmentTO);
				objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(flist9);
			}
		}
		
		if (empApplicantDetails.getEmpAwardDetails() != null) {
			Set<EmpAwardDetailsBO> empAwardDetailsBOs =empApplicantDetails.getEmpAwardDetails();
			if (empAwardDetailsBOs != null && !empAwardDetailsBOs.isEmpty()) {
				Iterator<EmpAwardDetailsBO> iterator = empAwardDetailsBOs.iterator();
				List<EmpAwardTO> empAwardTOs = new ArrayList<EmpAwardTO>();

				while (iterator.hasNext()) {
					EmpAwardDetailsBO empAwardDetailsBO = iterator.next();
					if (empAwardDetailsBO != null) {
						EmpAwardTO empAwardTO = new EmpAwardTO();

						if (empAwardTO.getId() > 0) {
							empAwardTO.setId(empAwardDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getActivityName())) {
							empAwardTO.setActivityname(empAwardDetailsBO.getActivityName());
						}
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getAwardName())) {
							empAwardTO.setAwardName(empAwardDetailsBO.getAwardName());
				        }
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
							empAwardTO.setAwardbodyName(empAwardDetailsBO.getAwardBody());
				        }
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getYear())) {
							empAwardTO.setYear(empAwardDetailsBO.getYear());
				        }
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
							empAwardTO.setRecognitionName(empAwardDetailsBO.getAward());
				        }
						empAwardTOs.add(empAwardTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpAward(empAwardTOs);
			} else {

				List<EmpAwardTO> flist10 = new ArrayList<EmpAwardTO>();
				EmpAwardTO empAwardTO = new EmpAwardTO();
				empAwardTO.setActivityname("");
				empAwardTO.setAwardbodyName("");
				empAwardTO.setAwardName("");
				empAwardTO.setRecognitionName("");
				empAwardTO.setYear("");
				
				
				objform.setEmpAwardListSize(String.valueOf(flist10.size()));
				flist10.add(empAwardTO);
				objform.getEmployeeInfoEditNewTO().setEmpAward(flist10);
			}
		}
		
		if (empApplicantDetails.getEmpMemDetailsBOs() != null) {
			Set<EmpMemeberShipDetailsBO> empMemeberShipDetailsBOs =empApplicantDetails.getEmpMemDetailsBOs();
			if (empMemeberShipDetailsBOs != null && !empMemeberShipDetailsBOs.isEmpty()) {
				Iterator<EmpMemeberShipDetailsBO> iterator = empMemeberShipDetailsBOs.iterator();
				List<EmpMemberShipAcademicBodyTO> empMemberShipAcademicBodyTOs = new ArrayList<EmpMemberShipAcademicBodyTO>();

				while (iterator.hasNext()) {
					EmpMemeberShipDetailsBO empMemeberShipDetailsBO = iterator.next();
					if (empMemeberShipDetailsBO != null) {
						EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();

						if (empMemberShipAcademicBodyTO.getId() > 0) {
							empMemberShipAcademicBodyTO.setId(empMemeberShipDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empMemeberShipDetailsBO.getName())) {
							empMemberShipAcademicBodyTO.setName(empMemeberShipDetailsBO.getName());
						}
						if (empMemeberShipDetailsBO.getFromDate() != null) {
							empMemberShipAcademicBodyTO.setFromDate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (empMemeberShipDetailsBO.getToDate() != null) {
							empMemberShipAcademicBodyTO.setTodate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						
						empMemberShipAcademicBodyTOs.add(empMemberShipAcademicBodyTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpMemberShip(empMemberShipAcademicBodyTOs);
			} else {

				List<EmpMemberShipAcademicBodyTO> flist11 = new ArrayList<EmpMemberShipAcademicBodyTO>();
				EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();
				empMemberShipAcademicBodyTO.setName("");
				empMemberShipAcademicBodyTO.setFromDate4("");
				empMemberShipAcademicBodyTO.setTodate4("");
				
				objform.setEmpMembershipAcademicListSize(String.valueOf(flist11.size()));
				flist11.add(empMemberShipAcademicBodyTO);
				objform.getEmployeeInfoEditNewTO().setEmpMemberShip(flist11);
			}
		}
		
		
		/*----------------------------------- code added by sudhir---------------------------------------*/
		/* calculating Experience in cjc (or) cu from joining Date to present day*/
		Date joiningDate = null;
		String fromDateDay = null;
		String fromDateMonth = null;
		if(objform.getDateOfJoining()!=null && !objform.getDateOfJoining().isEmpty()){
			 joiningDate = CommonUtil.ConvertStringToDate(objform.getDateOfJoining());
			 fromDateDay = objform.getDateOfJoining().substring(0, 2);
			 fromDateMonth = objform.getDateOfJoining().substring(3, 5);
		}
		String todaysDate = CommonUtil.getTodayDate();
		String toDateDay = todaysDate.substring(0, 2);
		String toDateMonth = todaysDate.substring(3, 5);
		Date toDate = CommonUtil.ConvertStringToDate(todaysDate);
		if(joiningDate!=null && toDate!=null){
			
		/*int years = CommonUtil.getYearsDiff(joiningDate,toDate);
		int months = CommonUtil.getMonthsBetweenTwoYears(joiningDate, toDate,fromDate,toDateNumber);*/
			double msPerGregorianYear = 365.25 * 86400 * 1000;
		 	double years1 =(toDate.getTime() - joiningDate.getTime()) / msPerGregorianYear;
		 	int yy = (int) years1;
	        int mm = (int) ((years1 - yy) * 12);
	        if(fromDateDay.equals(toDateDay)){
	        	if(fromDateMonth.equals(toDateMonth)){
	        		mm=0;
	        		yy = (int) Math.round(years1);
	        	}
	        }
	        if(fromDateDay.equals(toDateDay)){
	        	if(!fromDateMonth.equals(toDateMonth)){
	        		mm = (int) Math.round(((years1 - yy) * 12));
	        	}
	        }
		objform.setCurrentExpYears(yy);
		objform.setCurrentExpMonths(mm);
		}
		/* calculating TotalCurrent Experience Years and Months based on Recognised Experience and Experience in cjc (or) cu*/
		/*int totalYears = Integer.parseInt(objform.getRelevantExpYears())+objform.getCurrentExpYears();
		int totalMonths1 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getCurrentExpMonths() % 12;
		int totalMonths2 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getCurrentExpMonths() / 12;
		totalYears = totalYears + totalMonths2;
		objform.setTotalCurrentExpYears(totalYears);
		objform.setTotalCurrentExpMonths(totalMonths1);*/
		
		/*-----------------------------------------------------------------------------------------------*/
	

		
		
	}
	}

	public Employee convertFormToBo(EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception {
		Employee employee = new Employee();
		if (employeeInfoEditNewForm.getEmployeeId() != null
				&& !employeeInfoEditNewForm.getEmployeeId().isEmpty() && Integer.parseInt(employeeInfoEditNewForm.getEmployeeId())>0) {
			employee.setId(Integer.parseInt(employeeInfoEditNewForm
					.getEmployeeId()));
			
			Set<EmpAcademicQualificationBO> eSet = getEmpAcademicObjects(employeeInfoEditNewForm);
			employee.setEmpAcademicDeatils(eSet);
			
			Set<EmpIntrestsBO> eSet2 = getEmpInterestsObjects(employeeInfoEditNewForm);
			employee.setEmpIntrestDetails(eSet2);
			
			Set<EmpResearchBo> eSet3 = getEmpResearchObjects(employeeInfoEditNewForm);
			employee.setEmpResearchDetails(eSet3);
			
			Set<EmpGuideshipDetailsBo> eSet4 = getEmpGuideshipObjects(employeeInfoEditNewForm);
			employee.setEmpGuideshipDetails(eSet4);
			
			Set<EmpDutiesDetailsBO> eSet5 = getEmpDutiesObjects(employeeInfoEditNewForm);
			employee.setEmpDutiesDeatils(eSet5);
			
			Set<EmpProjectResearchDetailsBO> eSet6 = getEmpProjectResearchObjects(employeeInfoEditNewForm);
			employee.setEmpProjectDetails(eSet6);
			
			Set<EmpReaserchPublishcationDetailsBO> eSet7 = getEmpPublicationObjects(employeeInfoEditNewForm);
			employee.setEmpResearchPublication(eSet7);
			
			Set<EmpBooksPublishedDetailsBO> eSet8 = getEmpBooksObjects(employeeInfoEditNewForm);
			employee.setEmpBooksPublished(eSet8);
			
			Set<EmpPaperPresentationBO> eSet9 = getEmpPaperObjects(employeeInfoEditNewForm);
			employee.setEmpPaperPresentaion(eSet9);
			
			Set<EmpSeminarAttendedDetailsBO> eSet10 = getEmpSeminarObjects(employeeInfoEditNewForm);
			employee.setEmpSeminarAttended(eSet10);
			
			Set<EmpProfessionalDevelopmentBO> eSet11 = getEmpProObjects(employeeInfoEditNewForm);
			employee.setEmpProfeDevelopment(eSet11);
			
			Set<EmpAwardDetailsBO> eSet12 = getEmpAwardObjects(employeeInfoEditNewForm);
			employee.setEmpAwardDetails(eSet12);
			
			Set<EmpMemeberShipDetailsBO> eSet13 = getEmpMemebershipObjects(employeeInfoEditNewForm);
			employee.setEmpMemDetailsBOs(eSet13);
			
			
			if (employeeInfoEditNewForm.getDesignationId() != null
					&& !employeeInfoEditNewForm.getDesignationId().isEmpty()) {
				Designation designation = new Designation();
				designation.setId(Integer.parseInt(employeeInfoEditNewForm.getDesignationId()));
				employee.setDesignation(designation);
			}
			
			if (employeeInfoEditNewForm.getDepartmentId() != null
					&& !employeeInfoEditNewForm.getDepartmentId().isEmpty()) {
				Department department = new Department();
				department.setId(Integer.parseInt(employeeInfoEditNewForm
						.getDepartmentId()));
				employee.setDepartment(department);
			}
			
			if (employeeInfoEditNewForm.getName() != null
					&& !employeeInfoEditNewForm.getName().isEmpty()) {
				employee.setFirstName(employeeInfoEditNewForm.getName().toUpperCase());
			}
			
			if (employeeInfoEditNewForm.getDateOfBirth() != null
					&& !employeeInfoEditNewForm.getDateOfBirth().isEmpty()) {
				employee.setDob(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfBirth()));
			}
			
			if (employeeInfoEditNewForm.getDateOfJoining() != null
					&& !employeeInfoEditNewForm.getDateOfJoining().isEmpty()) {
				employee.setDoj(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfJoining()));
			}
			
			if (employeeInfoEditNewForm.getEmail() != null
					&& !employeeInfoEditNewForm.getEmail().isEmpty()) {
				employee.setEmail(employeeInfoEditNewForm.getEmail());
			}
			if (employeeInfoEditNewForm.getTeachingStaff() != null
					&& !employeeInfoEditNewForm.getTeachingStaff().isEmpty()) {
				String Value = employeeInfoEditNewForm.getTeachingStaff();
				if (Value.equals("1"))
					employee.setTeachingStaff(true);
				else
					employee.setTeachingStaff(false);
			}
			
			if (String.valueOf(employeeInfoEditNewForm.getCurrentExpYears()) != null
					&& !String.valueOf(employeeInfoEditNewForm.getCurrentExpYears()).isEmpty()) {
				employee
						.setTotalExpYear(String.valueOf(employeeInfoEditNewForm.getCurrentExpYears()));
			}
			if (String.valueOf(employeeInfoEditNewForm.getCurrentExpMonths()) != null
					&& !String.valueOf(employeeInfoEditNewForm.getCurrentExpMonths()).isEmpty()) {
				employee.setTotalExpMonths(String.valueOf(employeeInfoEditNewForm.getCurrentExpMonths()));
			}
			
			
			if (employeeInfoEditNewForm.getOfficialEmail() != null
					&& !employeeInfoEditNewForm.getOfficialEmail().isEmpty()) {
				employee.setWorkEmail(employeeInfoEditNewForm
						.getOfficialEmail());
			}
			if (employeeInfoEditNewForm.getExpYears() != null
					&& !employeeInfoEditNewForm.getExpYears().isEmpty()) {
				employee.setRelevantExpYears(employeeInfoEditNewForm.getExpYears());
			}
			if (employeeInfoEditNewForm.getExpMonths() != null
					&& !employeeInfoEditNewForm.getExpMonths().isEmpty()) {
				employee.setRelevantExpMonths(employeeInfoEditNewForm.getExpMonths());
			}
			
			
			if (employeeInfoEditNewForm.getOfficialEmail() != null
					&& !employeeInfoEditNewForm.getOfficialEmail().isEmpty()) {
				employee.setWorkEmail(employeeInfoEditNewForm
						.getOfficialEmail());
			}
			if (employeeInfoEditNewForm.getMobileNo1() != null
					&& !employeeInfoEditNewForm.getMobileNo1().isEmpty()) {
				employee.setCurrentAddressMobile1(employeeInfoEditNewForm.getMobileNo1());
			}
			
			if (employeeInfoEditNewForm.getMaritialStatus() != null
					&& !employeeInfoEditNewForm.getMaritialStatus().isEmpty()) {
				employee.setMaritalStatus(employeeInfoEditNewForm
						.getMaritialStatus());
			}
			
			if (employeeInfoEditNewForm.getBloodGroup() != null
					&& !employeeInfoEditNewForm.getBloodGroup().isEmpty()) {
				employee
						.setBloodGroup(employeeInfoEditNewForm.getBloodGroup());
			}
			
			if (employeeInfoEditNewForm.getEmptypeId() != null
					&& !employeeInfoEditNewForm.getEmptypeId().isEmpty()) {
				EmpType emptype = new EmpType();
				emptype.setId(Integer.parseInt(employeeInfoEditNewForm
						.getEmptypeId()));
				employee.setEmptype(emptype);
			}
			if (employeeInfoEditNewForm.getEmContactName() != null
					&& !employeeInfoEditNewForm.getEmContactName().isEmpty()) {
				employee.setEmergencyContName(employeeInfoEditNewForm
						.getEmContactName());
			}
			if (employeeInfoEditNewForm.getOfficialEmail() != null
					&& !employeeInfoEditNewForm.getOfficialEmail().isEmpty()) {
				employee.setWorkEmail(employeeInfoEditNewForm
						.getOfficialEmail());
			}
			if (employeeInfoEditNewForm.getEmail() != null
					&& !employeeInfoEditNewForm.getEmail().isEmpty()) {
				employee.setOtherEmail(employeeInfoEditNewForm.getEmail());
			}
			if (employeeInfoEditNewForm.getEmContactWorkTel() != null
					&& !employeeInfoEditNewForm.getEmContactWorkTel()
							.isEmpty()) {
				employee.setEmergencyWorkTelephone(employeeInfoEditNewForm
						.getEmContactWorkTel());
			}
			if (employeeInfoEditNewForm.getEmContactHomeTel() != null
					&& !employeeInfoEditNewForm.getEmContactHomeTel()
							.isEmpty()) {
				employee.setEmergencyHomeTelephone(employeeInfoEditNewForm
						.getEmContactHomeTel());
			}
			if (employeeInfoEditNewForm.getEmContactMobile() != null
					&& !employeeInfoEditNewForm.getEmContactMobile().isEmpty()) {
				employee.setEmergencyMobile(employeeInfoEditNewForm
						.getEmContactMobile());
			}
			if (employeeInfoEditNewForm.getEmContactAddress() != null
					&& !employeeInfoEditNewForm.getEmContactAddress().isEmpty()) {
				employee.setEmContactAddress(employeeInfoEditNewForm
						.getEmContactAddress());
			}
			
			if (employeeInfoEditNewForm.getReligionId() != null
					&& !employeeInfoEditNewForm.getReligionId().isEmpty()) {
				Religion empReligion = new Religion();
				empReligion.setId(Integer.parseInt(employeeInfoEditNewForm
						.getReligionId()));
				employee.setReligionId(empReligion);
			}
			if (employeeInfoEditNewForm.getOtherInfo() != null
					&& !employeeInfoEditNewForm.getOtherInfo().isEmpty()) {
				employee.setOtherInfo(employeeInfoEditNewForm.getOtherInfo());
			}
			if (employeeInfoEditNewForm.getuId() != null
					&& !employeeInfoEditNewForm.getuId().isEmpty()) {
				employee.setUid(employeeInfoEditNewForm.getuId());
			}
			if (employeeInfoEditNewForm.getSameAddress() != null
					&& !employeeInfoEditNewForm.getSameAddress().isEmpty()) {
				String Value = employeeInfoEditNewForm.getSameAddress();
				if (Value.equals("true"))
					employee.setIsSameAddress(true);
				else
					employee.setIsSameAddress(false);
			}
			if (employeeInfoEditNewForm.getCurrentAddressLine1() != null
					&& !employeeInfoEditNewForm.getCurrentAddressLine1()
							.isEmpty()) {
				employee.setCommunicationAddressLine1(employeeInfoEditNewForm
						.getCurrentAddressLine1());
			}

			if (employeeInfoEditNewForm.getCurrentAddressLine2() != null
					&& !employeeInfoEditNewForm.getCurrentAddressLine2()
							.isEmpty()) {
				employee.setCommunicationAddressLine2(employeeInfoEditNewForm
						.getCurrentAddressLine2());
			}

			if (employeeInfoEditNewForm.getCurrentZipCode() != null
					&& !employeeInfoEditNewForm.getCurrentZipCode().isEmpty()) {
				employee.setCommunicationAddressZip(employeeInfoEditNewForm
						.getCurrentZipCode());
			}

			if (employeeInfoEditNewForm.getCurrentCountryId() != null
					&& !employeeInfoEditNewForm.getCurrentCountryId()
							.isEmpty()) {
				Country currentCountry = new Country();
				currentCountry.setId(Integer.parseInt(employeeInfoEditNewForm
						.getCurrentCountryId()));
				employee
						.setCountryByCommunicationAddressCountryId(currentCountry);
			}

			if (employeeInfoEditNewForm.getCurrentState() != null
					&& !employeeInfoEditNewForm.getCurrentState().isEmpty()) {
				if (employeeInfoEditNewForm.getCurrentState()
						.equalsIgnoreCase("other")) {
					if (employeeInfoEditNewForm.getOtherCurrentState() != null
							&& !employeeInfoEditNewForm.getOtherCurrentState()
									.isEmpty()) {
						employee
								.setCommunicationAddressStateOthers(employeeInfoEditNewForm
										.getOtherCurrentState());
					}
				} else {
					State currentState = new State();
					currentState.setId(Integer
							.parseInt(employeeInfoEditNewForm
									.getCurrentState()));
					employee
							.setStateByCommunicationAddressStateId(currentState);
				}
			}

			if (employeeInfoEditNewForm.getCurrentCity() != null
					&& !employeeInfoEditNewForm.getCurrentCity().isEmpty()) {
				employee.setCommunicationAddressCity(employeeInfoEditNewForm
						.getCurrentCity());
			}

			if (employeeInfoEditNewForm.getSameAddress().equalsIgnoreCase(
					"true")) {
				if (employeeInfoEditNewForm.getCurrentAddressLine1() != null
						&& !employeeInfoEditNewForm.getCurrentAddressLine1()
								.isEmpty()) {
					employee.setPermanentAddressLine1(employeeInfoEditNewForm
							.getCurrentAddressLine1());
				}

				if (employeeInfoEditNewForm.getCurrentAddressLine2() != null
						&& !employeeInfoEditNewForm.getCurrentAddressLine2()
								.isEmpty()) {
					employee.setPermanentAddressLine2(employeeInfoEditNewForm
							.getCurrentAddressLine2());
				}

				if (employeeInfoEditNewForm.getCurrentZipCode() != null
						&& !employeeInfoEditNewForm.getCurrentZipCode()
								.isEmpty()) {
					employee.setPermanentAddressZip(employeeInfoEditNewForm
							.getCurrentZipCode());
				}

				if (employeeInfoEditNewForm.getCurrentCountryId() != null
						&& !employeeInfoEditNewForm.getCurrentCountryId()
								.isEmpty()) {
					Country currentCountry = new Country();
					currentCountry.setId(Integer
							.parseInt(employeeInfoEditNewForm
									.getCurrentCountryId()));
					employee
							.setCountryByPermanentAddressCountryId(currentCountry);
				}

				if (employeeInfoEditNewForm.getCurrentState() != null
						&& !employeeInfoEditNewForm.getCurrentState()
								.isEmpty()) {
					if (employeeInfoEditNewForm.getCurrentState()
							.equalsIgnoreCase("other")) {
						if (employeeInfoEditNewForm.getOtherCurrentState() != null
								&& !employeeInfoEditNewForm
										.getOtherCurrentState().isEmpty()) {
							employee
									.setPermanentAddressStateOthers(employeeInfoEditNewForm
											.getOtherCurrentState());
						}
					} else {
						State currentState = new State();
						currentState.setId(Integer
								.parseInt(employeeInfoEditNewForm
										.getCurrentState()));
						employee
								.setStateByPermanentAddressStateId(currentState);
					}
				}

				if (employeeInfoEditNewForm.getCurrentCity() != null
						&& !employeeInfoEditNewForm.getCurrentCity().isEmpty()) {
					employee.setPermanentAddressCity(employeeInfoEditNewForm
							.getCurrentCity());
				}

			} else {
				if (employeeInfoEditNewForm.getAddressLine1() != null
						&& !employeeInfoEditNewForm.getAddressLine1()
								.isEmpty()) {
					employee.setPermanentAddressLine1(employeeInfoEditNewForm
							.getAddressLine1());
				}

				if (employeeInfoEditNewForm.getAddressLine2() != null
						&& !employeeInfoEditNewForm.getAddressLine2()
								.isEmpty()) {
					employee.setPermanentAddressLine2(employeeInfoEditNewForm
							.getAddressLine2());
				}

				if (employeeInfoEditNewForm.getPermanentZipCode() != null
						&& !employeeInfoEditNewForm.getPermanentZipCode()
								.isEmpty()) {
					employee.setPermanentAddressZip(employeeInfoEditNewForm
							.getPermanentZipCode());
				}

				if (employeeInfoEditNewForm.getCountryId() != null
						&& !employeeInfoEditNewForm.getCountryId().isEmpty()) {
					Country country = new Country();
					country.setId(Integer.parseInt(employeeInfoEditNewForm
							.getCountryId()));
					employee.setCountryByPermanentAddressCountryId(country);
				}

				if (employeeInfoEditNewForm.getStateId() != null
						&& !employeeInfoEditNewForm.getStateId().isEmpty()) {
					if (employeeInfoEditNewForm.getStateId().equalsIgnoreCase(
							"other")) {
						if (employeeInfoEditNewForm.getOtherPermanentState() != null
								&& !employeeInfoEditNewForm
										.getOtherPermanentState().isEmpty()) {
							employee
									.setPermanentAddressStateOthers(employeeInfoEditNewForm
											.getOtherPermanentState());
						}
					} else {
						State state = new State();
						state.setId(Integer.parseInt(employeeInfoEditNewForm
								.getStateId()));
						employee.setStateByPermanentAddressStateId(state);
					}
				}

				if (employeeInfoEditNewForm.getCity() != null
						&& !employeeInfoEditNewForm.getCity().isEmpty()) {
					employee.setPermanentAddressCity(employeeInfoEditNewForm
							.getCity());
				}
			}
			if (employeeInfoEditNewForm.getNationalityId() != null
					&& !employeeInfoEditNewForm.getNationalityId().isEmpty()) {
				Nationality nationality = new Nationality();
				nationality.setId(Integer.parseInt(employeeInfoEditNewForm
						.getNationalityId()));
				employee.setNationality(nationality);
			}

			if (employeeInfoEditNewForm.getGender() != null
					&& !employeeInfoEditNewForm.getGender().isEmpty()) {
				employee.setGender(employeeInfoEditNewForm.getGender());
			}
			
			if (employeeInfoEditNewForm.getDateOfBirth() != null
					&& !employeeInfoEditNewForm.getDateOfBirth().isEmpty()) {
				employee.setDob(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfBirth()));
			}
			if (employeeInfoEditNewForm.getDateOfJoining() != null
					&& !employeeInfoEditNewForm.getDateOfJoining().isEmpty()) {
				employee.setDoj(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfJoining()));
			}
			if (employeeInfoEditNewForm.getDateOfLeaving() != null
					&& !employeeInfoEditNewForm.getDateOfLeaving().isEmpty()) {
				employee.setDateOfLeaving(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfLeaving()));
			}
			if (employeeInfoEditNewForm.getDateOfResignation() != null
					&& !employeeInfoEditNewForm.getDateOfResignation()
							.isEmpty()) {
				employee.setDateOfResignation(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfResignation()));
			}
			if (employeeInfoEditNewForm.getRetirementDate() != null
					&& !employeeInfoEditNewForm.getRetirementDate().isEmpty()) {
				employee.setRetirementDate(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getRetirementDate()));
			}
			if (employeeInfoEditNewForm.getHomePhone1() != null
					&& !employeeInfoEditNewForm.getHomePhone1().isEmpty()) {
				employee
						.setCurrentAddressHomeTelephone1(employeeInfoEditNewForm
								.getHomePhone1());
			}

			if (employeeInfoEditNewForm.getHomePhone2() != null
					&& !employeeInfoEditNewForm.getHomePhone2().isEmpty()) {
				employee
						.setCurrentAddressHomeTelephone2(employeeInfoEditNewForm
								.getHomePhone2());
			}

			if (employeeInfoEditNewForm.getHomePhone3() != null
					&& !employeeInfoEditNewForm.getHomePhone3().isEmpty()) {
				employee
						.setCurrentAddressHomeTelephone3(employeeInfoEditNewForm
								.getHomePhone3());
			}
			if (employeeInfoEditNewForm.getWorkPhNo1() != null
					&& !employeeInfoEditNewForm.getWorkPhNo1().isEmpty()) {
				employee
						.setCurrentAddressWorkTelephone1(employeeInfoEditNewForm
								.getWorkPhNo1());
			}

			if (employeeInfoEditNewForm.getWorkPhNo2() != null
					&& !employeeInfoEditNewForm.getWorkPhNo2().isEmpty()) {
				employee
						.setCurrentAddressWorkTelephone2(employeeInfoEditNewForm
								.getWorkPhNo2());
			}

			if (employeeInfoEditNewForm.getWorkPhNo3() != null
					&& !employeeInfoEditNewForm.getWorkPhNo3().isEmpty()) {
				employee
						.setCurrentAddressWorkTelephone3(employeeInfoEditNewForm
								.getWorkPhNo3());
			}
			if (employeeInfoEditNewForm.getMobileNo1() != null
					&& !employeeInfoEditNewForm.getMobileNo1().isEmpty()) {
				employee.setCurrentAddressMobile1(employeeInfoEditNewForm
						.getMobileNo1());
			}
			
			if (employeeInfoEditNewForm.getNoOfPublicationsRefered() != null
					&& !employeeInfoEditNewForm.getNoOfPublicationsRefered()
							.isEmpty()) {
				employee.setNoOfPublicationsRefered(employeeInfoEditNewForm
						.getNoOfPublicationsRefered());
			}

			if (employeeInfoEditNewForm.getNoOfPublicationsNotRefered() != null
					&& !employeeInfoEditNewForm
							.getNoOfPublicationsNotRefered().isEmpty()) {
				employee.setNoOfPublicationsNotRefered(employeeInfoEditNewForm
						.getNoOfPublicationsNotRefered());
			}

			if (employeeInfoEditNewForm.getBooks() != null
					&& !employeeInfoEditNewForm.getBooks().isEmpty()) {
				employee.setBooks(employeeInfoEditNewForm.getBooks());
			}
			if (employeeInfoEditNewForm.getDateOfResignation() != null
					&& !employeeInfoEditNewForm.getDateOfResignation()
							.isEmpty()) {
				employee.setDateOfResignation(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfResignation()));
			}
			if (employeeInfoEditNewForm.getDateOfLeaving() != null
					&& !employeeInfoEditNewForm.getDateOfLeaving().isEmpty()) {
				employee.setDateOfLeaving(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfLeaving()));
			}
			if (employeeInfoEditNewForm.getScale() != null
					&& !employeeInfoEditNewForm.getScale().isEmpty()) {
				employee.setScale(employeeInfoEditNewForm.getScale());
			}
		/*	if (employeeInfoEditForm.getBasicPay() != null
					&& !employeeInfoEditForm.getBasicPay().isEmpty()) {
				employee.setBasicPay(employeeInfoEditForm.getBasicPay());
			}*/

			if (employeeInfoEditNewForm.getGrossPay() != null
					&& !employeeInfoEditNewForm.getGrossPay().isEmpty()) {
				employee.setGrossPay(employeeInfoEditNewForm.getGrossPay());
			}
			if (employeeInfoEditNewForm.getWorkLocationId() != null
					&& !employeeInfoEditNewForm.getWorkLocationId().isEmpty()) {
				EmployeeWorkLocationBO empworkLoc = new EmployeeWorkLocationBO();
				empworkLoc.setId(Integer.parseInt(employeeInfoEditNewForm
						.getWorkLocationId()));
				employee.setWorkLocationId(empworkLoc);
			}
			if (employeeInfoEditNewForm.getStreamId() != null
					&& !employeeInfoEditNewForm.getStreamId().isEmpty()) {
				EmployeeStreamBO empStream = new EmployeeStreamBO();
				empStream.setId(Integer.parseInt(employeeInfoEditNewForm
						.getStreamId()));
				employee.setStreamId(empStream);
			}
			
			if (employeeInfoEditNewForm.getEmpQualificationLevelId() != null
					&& !employeeInfoEditNewForm.getEmpQualificationLevelId()
							.isEmpty()) {
				QualificationLevelBO empQualificationLevel = new QualificationLevelBO();
				empQualificationLevel.setId(Integer
						.parseInt(employeeInfoEditNewForm
								.getEmpQualificationLevelId()));
				employee.setEmpQualificationLevel(empQualificationLevel);
			}
			
			if (employeeInfoEditNewForm.getReservationCategory() != null
					&& !employeeInfoEditNewForm.getReservationCategory()
							.isEmpty()) {
				employee.setReservationCategory(employeeInfoEditNewForm
						.getReservationCategory());
			}
			
			if (employeeInfoEditNewForm.getIsSmartCardDataDelivered() != null
					&& !employeeInfoEditNewForm.getIsSmartCardDataDelivered()
							.isEmpty()) {
				String Value = employeeInfoEditNewForm
						.getIsSmartCardDataDelivered();
				if (Value.equals("1"))
					employee.setIsSCDataDelivered(true);
				else
					employee.setIsSCDataDelivered(false);
			}
			if (employeeInfoEditNewForm.getIsSmartCardDataGenerated() != null
					&& !employeeInfoEditNewForm.getIsSmartCardDataGenerated()
							.isEmpty()) {
				String Value = employeeInfoEditNewForm
						.getIsSmartCardDataGenerated();
				if (Value.equals("1"))
					employee.setIsSCDataGenerated(true);
				else
					employee.setIsSCDataGenerated(false);
			}
			
			if (employeeInfoEditNewForm.getCode() != null
					&& !employeeInfoEditNewForm.getCode().isEmpty()) {
				employee.setCode(employeeInfoEditNewForm.getCode());
			}
			if (employeeInfoEditNewForm.getFingerPrintId() != null
					&& !employeeInfoEditNewForm.getFingerPrintId().isEmpty()) {
				employee.setFingerPrintId(employeeInfoEditNewForm
						.getFingerPrintId());
			}
			if (employeeInfoEditNewForm.getAlbumDesignation() != null
					&& !employeeInfoEditNewForm.getAlbumDesignation().isEmpty()) {
				Designation designation = new Designation();
				designation.setId(Integer.parseInt(employeeInfoEditNewForm
						.getAlbumDesignation()));
				employee.setAlbumDesignation(designation);
			}
			if (employeeInfoEditNewForm.getActive() != null
					&& !employeeInfoEditNewForm.getActive()
							.isEmpty()) {
				String Value = employeeInfoEditNewForm
						.getActive();
				if (Value.equals("1"))
					employee.setActive(true);
				else
					employee.setActive(false);
			}
			
			if (employeeInfoEditNewForm.getCurrentlyWorking() != null
					&& !employeeInfoEditNewForm.getCurrentlyWorking()
							.isEmpty()
					&& employeeInfoEditNewForm.getCurrentlyWorking()
							.equalsIgnoreCase("YES")) {
				employee.setCurrentlyWorking(true);
			}
			
			employee.setIsActive(true);
			employee.setCreatedBy(employeeInfoEditNewForm.getUserId());
			employee.setCreatedDate(new Date());
			employee.setLastModifiedDate(new Date());
			employee.setModifiedBy(employeeInfoEditNewForm.getUserId());
		}

		return employee;
	}

	private Set<EmpAcademicQualificationBO> getEmpAcademicObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpAcademicQualificationBO> bos = new HashSet<EmpAcademicQualificationBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpqualification() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpqualification()
						.isEmpty()) {
			Iterator<EmpAcademicQualificationTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpqualification().iterator();
			while (itr.hasNext()) {
				EmpAcademicQualificationTO to = (EmpAcademicQualificationTO) itr.next();
				EmpAcademicQualificationBO bo = new EmpAcademicQualificationBO();
				if (to.getCourseName() != null && !to.getCourseName().isEmpty()
						|| to.getGrade() != null && !to.getGrade().isEmpty()
						|| to.getUniversityName() != null && !to.getUniversityName().isEmpty()
						||to.getYear() != null && !to.getYear().isEmpty()) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setUniversity(to.getUniversityName());
					bo.setYear(to.getYear());
					bo.setCourseName(to.getCourseName());
					bo.setGrade(to.getGrade());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	private Set<EmpIntrestsBO> getEmpInterestsObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpIntrestsBO> bos = new HashSet<EmpIntrestsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getInterest() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getInterest()
						.isEmpty()) {
			Iterator<EmpInterestsTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getInterest().iterator();
			while (itr.hasNext()) {
				EmpInterestsTO to = (EmpInterestsTO) itr.next();
				EmpIntrestsBO bo = new EmpIntrestsBO();
				if (to.getTopic() != null && !to.getTopic().isEmpty()) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setTopic(to.getTopic());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	
	private Set<EmpResearchBo> getEmpResearchObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpResearchBo> bos = new HashSet<EmpResearchBo>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearch() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearch()
						.isEmpty()) {
			Iterator<EmpFieldResearchTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearch().iterator();
			while (itr.hasNext()) {
				EmpFieldResearchTO to = (EmpFieldResearchTO) itr.next();
				EmpResearchBo bo = new EmpResearchBo();
				if (to.getTopic() != null && !to.getTopic().isEmpty()) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setTitle(to.getTopic());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	
	}

	private Set<EmpGuideshipDetailsBo> getEmpGuideshipObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {

		Set<EmpGuideshipDetailsBo> bos = new HashSet<EmpGuideshipDetailsBo>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getGuideship() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getGuideship()
						.isEmpty()) {
			Iterator<EmpGuideShipDetailsTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getGuideship().iterator();
			while (itr.hasNext()) {
				EmpGuideShipDetailsTO to = (EmpGuideShipDetailsTO) itr.next();
				EmpGuideshipDetailsBo bo = new EmpGuideshipDetailsBo();
				if (to.getAwarded() != null && !to.getAwarded().isEmpty()
						|| to.getPhdScholarName() != null && !to.getPhdScholarName().isEmpty()
						|| to.getRegistrationYear() != null && !to.getRegistrationYear().isEmpty()
						|| to.getThesisName() != null && !to.getThesisName().isEmpty()
						|| to.getYear() != null && !to.getYear().isEmpty()) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setAwarded(to.getAwarded());
					bo.setAwardedThesisName(to.getThesisName());
					bo.setAwardedYear(to.getRegistrationYear());
					bo.setScholarName(to.getPhdScholarName());
					bo.setRegistraionYear(to.getYear());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	
	private Set<EmpDutiesDetailsBO> getEmpDutiesObjects(
			EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpDutiesDetailsBO> bos = new HashSet<EmpDutiesDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getDuties() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getDuties()
						.isEmpty()) {
			Iterator<EmpDutiesPerformedTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getDuties().iterator();
			while (itr.hasNext()) {
				EmpDutiesPerformedTO to = (EmpDutiesPerformedTO) itr.next();
				EmpDutiesDetailsBO bo = new EmpDutiesDetailsBO();
				if (to.getPositionName() != null && !to.getPositionName().isEmpty()
						|| to.getFromDate() != null && !to.getFromDate().isEmpty()
						|| to.getToDate() != null && !to.getToDate().isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setPosition(to.getPositionName());
					bo.setFromDate(CommonUtil.ConvertStringToDate(to.getFromDate()));
					bo.setToDate(CommonUtil.ConvertStringToDate(to.getToDate()));
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	
	private Set<EmpProjectResearchDetailsBO> getEmpProjectResearchObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpProjectResearchDetailsBO> bos = new HashSet<EmpProjectResearchDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearchProject() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearchProject()
						.isEmpty()) {
			Iterator<EmpResearchTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearchProject().iterator();
			while (itr.hasNext()) {
				EmpResearchTO to = (EmpResearchTO) itr.next();
				EmpProjectResearchDetailsBO bo = new EmpProjectResearchDetailsBO();
				if (to.getAmount() != null && !to.getAmount().isEmpty()
						|| to.getFindingAgencyName() != null && !to.getFindingAgencyName().isEmpty()
						|| to.getFromDate1() != null && !to.getFromDate1().isEmpty()
						|| to.getToDate1() != null && !to.getToDate1().isEmpty()
						|| to.getProjectName() != null && !to.getProjectName().isEmpty()
						|| to.getTitle() != null && !to.getTitle().isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setAmount(to.getAmount());
					bo.setFundingAgency(to.getFindingAgencyName());
					bo.setPrjectName(to.getProjectName());
					bo.setTitle(to.getTitle());
					bo.setFromDate(CommonUtil.ConvertStringToDate(to.getFromDate1()));
					bo.setToDate(CommonUtil.ConvertStringToDate(to.getToDate1()));
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	private Set<EmpReaserchPublishcationDetailsBO> getEmpPublicationObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpReaserchPublishcationDetailsBO> bos = new HashSet<EmpReaserchPublishcationDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearchPubliction() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearchPubliction()
						.isEmpty()) {
			Iterator<EmpResearchPublicationTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getResearchPubliction().iterator();
			while (itr.hasNext()) {
				EmpResearchPublicationTO to = (EmpResearchPublicationTO) itr.next();
				EmpReaserchPublishcationDetailsBO bo = new EmpReaserchPublishcationDetailsBO();
				if (to.getISBNISSNNo() != null && !to.getISBNISSNNo().isEmpty()
						|| to.getJournalName() != null && !to.getJournalName().isEmpty()
						|| to.getPaperTitle() != null && !to.getPaperTitle().isEmpty()
						|| to.getUGCNonUGC() != null && !to.getUGCNonUGC().isEmpty()
						|| to.getYear() != null && !to.getYear().isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setISBNISSNNo(to.getISBNISSNNo());
					bo.setJournalName(to.getJournalName());
					bo.setTitle(to.getPaperTitle());
					bo.setUGC(to.getUGCNonUGC());
					bo.setYear(to.getYear());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	
	private Set<EmpBooksPublishedDetailsBO> getEmpBooksObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpBooksPublishedDetailsBO> bos = new HashSet<EmpBooksPublishedDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpBooks() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpBooks()
						.isEmpty()) {
			Iterator<EmpBooksPublishedTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpBooks().iterator();
			while (itr.hasNext()) {
				EmpBooksPublishedTO to = (EmpBooksPublishedTO) itr.next();
				EmpBooksPublishedDetailsBO bo = new EmpBooksPublishedDetailsBO();
				if (to.getContibutionName() != null && !to.getContibutionName().isEmpty()
						|| to.getISBNISSN() != null && !to.getISBNISSN().isEmpty()
						|| to.getPublisherName() != null && !to.getPublisherName().isEmpty()
						|| to.getTitleName() != null && !to.getTitleName().isEmpty()
						|| to.getYear() != null && !to.getYear().isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setContribution(to.getContibutionName());
					bo.setBookName(to.getTitleName());
					bo.setPublisherName(to.getPublisherName());
					bo.setISBNISSNNo(to.getISBNISSN());
					bo.setYear(to.getYear());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	
	private Set<EmpPaperPresentationBO> getEmpPaperObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpPaperPresentationBO> bos = new HashSet<EmpPaperPresentationBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpPaper() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpPaper()
						.isEmpty()) {
			Iterator<EmpPaperPresentationTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpPaper().iterator();
			while (itr.hasNext()) {
				EmpPaperPresentationTO to = (EmpPaperPresentationTO) itr.next();
				EmpPaperPresentationBO bo = new EmpPaperPresentationBO();
				if (to.getDate1() != null && !to.getDate1().isEmpty()
						|| to.getOrganisation() != null && !to.getOrganisation().isEmpty()
						|| to.getPaperName() != null && !to.getPaperName().isEmpty()
						|| to.getRegional() != null && !to.getRegional().isEmpty()
						|| to.getSeminarName() != null && !to.getSeminarName() .isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setDate(CommonUtil.ConvertStringToDate(to.getDate1()));
					bo.setInstitution(to.getOrganisation());
					bo.setInterRegoinal(to.getRegional());
					bo.setPaperTile(to.getPaperName());
					bo.setProceedingsTile(to.getSeminarName());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	
	private Set<EmpSeminarAttendedDetailsBO> getEmpSeminarObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpSeminarAttendedDetailsBO> bos = new HashSet<EmpSeminarAttendedDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails()
						.isEmpty()) {
			Iterator<EmpSeminarAttendedDetailsTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpSeminarAttendedDetails().iterator();
			while (itr.hasNext()) {
				EmpSeminarAttendedDetailsTO to = (EmpSeminarAttendedDetailsTO) itr.next();
				EmpSeminarAttendedDetailsBO bo = new EmpSeminarAttendedDetailsBO();
				if (to.getInterRegional() != null && !to.getInterRegional().isEmpty()
						|| to.getOrganisation() != null && !to.getOrganisation().isEmpty()
						|| to.getParticipation() != null && !to.getParticipation().isEmpty()
						|| to.getSeminar() != null && !to.getSeminar().isEmpty()
						|| to.getSeminarName() != null && !to.getSeminarName() .isEmpty()
						|| to.getFromDate2() != null && !to.getFromDate2() .isEmpty()
						|| to.getToDate2() != null && !to.getToDate2() .isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBY(employeeInfoEditNewForm.getUserId());
					bo.setCreateddate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setFromDate2(CommonUtil.ConvertStringToDate(to.getFromDate2()));
					bo.setToDate2(CommonUtil.ConvertStringToDate(to.getToDate2()));
					bo.setInstitution(to.getOrganisation());
					bo.setInterRegional(to.getInterRegional());
					bo.setParticipation(to.getParticipation());
					bo.setTitle(to.getSeminar());
					bo.setWorkShop(to.getSeminarName());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	private Set<EmpProfessionalDevelopmentBO> getEmpProObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpProfessionalDevelopmentBO> bos = new HashSet<EmpProfessionalDevelopmentBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment()
						.isEmpty()) {
			Iterator<EmpProfessionalDevelopmentTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpPersonaldevelopment().iterator();
			while (itr.hasNext()) {
				EmpProfessionalDevelopmentTO to = (EmpProfessionalDevelopmentTO) itr.next();
				EmpProfessionalDevelopmentBO bo = new EmpProfessionalDevelopmentBO();
				if (to.getName() != null && !to.getName().isEmpty()
						|| to.getOrganisation() != null && !to.getOrganisation().isEmpty()
						|| to.getFromDate3() != null && !to.getFromDate3() .isEmpty()
						|| to.getToDate3() != null && !to.getToDate3() .isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setFromDate(CommonUtil.ConvertStringToDate(to.getFromDate3()));
					bo.setToDate(CommonUtil.ConvertStringToDate(to.getToDate3()));
					bo.setInstitute(to.getOrganisation());
					bo.setName(to.getName());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	
	private Set<EmpAwardDetailsBO> getEmpAwardObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpAwardDetailsBO> bos = new HashSet<EmpAwardDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpAward() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpAward()
						.isEmpty()) {
			Iterator<EmpAwardTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpAward().iterator();
			while (itr.hasNext()) {
				EmpAwardTO to = (EmpAwardTO) itr.next();
				EmpAwardDetailsBO bo = new EmpAwardDetailsBO();
				if (to.getActivityname() != null && !to.getActivityname().isEmpty()
						|| to.getAwardbodyName() != null && !to.getAwardbodyName().isEmpty()
						|| to.getAwardName() != null && !to.getAwardName() .isEmpty()
						|| to.getRecognitionName() != null && !to.getRecognitionName() .isEmpty()
						|| to.getYear() != null && !to.getYear() .isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setActivityName(to.getActivityname());
					bo.setAward(to.getRecognitionName());
					bo.setAwardBody(to.getAwardbodyName());
					bo.setAwardName(to.getAwardName());
					bo.setYear(to.getYear());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	  
	
	private Set<EmpMemeberShipDetailsBO> getEmpMemebershipObjects(EmployeeInfoEditNewForm employeeInfoEditNewForm) {
		Set<EmpMemeberShipDetailsBO> bos = new HashSet<EmpMemeberShipDetailsBO>();
		if (employeeInfoEditNewForm.getEmployeeInfoEditNewTO() != null
				&& employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpMemberShip() != null
				&& !employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpMemberShip()
						.isEmpty()) {
			Iterator<EmpMemberShipAcademicBodyTO> itr = employeeInfoEditNewForm.getEmployeeInfoEditNewTO().getEmpMemberShip().iterator();
			while (itr.hasNext()) {
				EmpMemberShipAcademicBodyTO to = (EmpMemberShipAcademicBodyTO) itr.next();
				EmpMemeberShipDetailsBO bo = new EmpMemeberShipDetailsBO();
				if (to.getName() != null && !to.getName().isEmpty()
						|| to.getFromDate4() != null && !to.getFromDate4() .isEmpty()
						|| to.getTodate4() != null && !to.getTodate4() .isEmpty()
						) {
					if (to.getId() > 0) {
						bo.setId(to.getId());
					}
					bo.setCreatedBy(employeeInfoEditNewForm.getUserId());
					bo.setCreatedDate(new Date());
					if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditNewForm
							.getEmployeeId()));
					bo.setEmployee(emp);
					}if(employeeInfoEditNewForm.getGuestId() != null && !employeeInfoEditNewForm.getGuestId().isEmpty() &&
							Integer.parseInt(employeeInfoEditNewForm.getGuestId()) > 0){
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditNewForm
								.getGuestId()));
						bo.setGuest(emp);
					}
					bo.setFromDate(CommonUtil.ConvertStringToDate(to.getFromDate4()));
					bo.setToDate(CommonUtil.ConvertStringToDate(to.getTodate4()));
					bo.setName(to.getName());
					bo.setModifiedBy(employeeInfoEditNewForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bos.add(bo);
				}
			}
		}
		return bos;
	}

	public void convertB(GuestFaculty empApplicantDetails,	EmployeeInfoEditNewForm objform)throws Exception {

		if (empApplicantDetails != null) {
		
			if (empApplicantDetails.getId() > 0) {
				objform.setGuestId(String.valueOf(empApplicantDetails
						.getId()));
			}
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getTeachingStaff()))) {
				String Value = String.valueOf(empApplicantDetails
						.getTeachingStaff());
				if (Value.equals("true"))
					objform.setTeachingStaff("1");
				else
					objform.setTeachingStaff("0");

			}
			
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getCurrentlyWorking()))) {
				String Value = String.valueOf(empApplicantDetails
						.getCurrentlyWorking());
				if (Value.equals("true"))
					objform.setCurrentlyWorking("YES");
				else
					objform.setCurrentlyWorking("NO");

			}
		
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getActive()))) {
				String Value = String.valueOf(empApplicantDetails.getActive());
				if (Value.equals("true"))
					objform.setActive("1");
				else
					objform.setActive("0");

			}
			
			if (empApplicantDetails.getReligionId() != null
					&& empApplicantDetails.getReligionId().getId() > 0) {
				objform.setReligionId(String.valueOf(empApplicantDetails
						.getReligionId().getId()));
			}
			
			
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getFirstName())
					&& empApplicantDetails.getFirstName() != null) {
				objform.setName(empApplicantDetails.getFirstName().toUpperCase());
			}
			
			
			if (empApplicantDetails.getDepartment() != null
					&& empApplicantDetails.getDepartment().getId() > 0) {
				objform.setDepartmentId(String.valueOf(empApplicantDetails
						.getDepartment().getId()));
			}
			
			
			if (empApplicantDetails.getGender() != null
					&& !empApplicantDetails.getGender().isEmpty()) {
				objform.setGender(empApplicantDetails.getGender());
			}
			
			
			
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getEmail())
					&& empApplicantDetails.getEmail() != null) {
				objform
						.setEmail(String
								.valueOf(empApplicantDetails.getEmail()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(String.valueOf(empApplicantDetails
						.getWorkEmail()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails
					.getCurrentAddressMobile1())
					&& empApplicantDetails.getCurrentAddressMobile1() != null) {
				objform.setMobileNo1(empApplicantDetails
						.getCurrentAddressMobile1());
			}
			
			
			

			if (empApplicantDetails.getReligionId() != null
					&& empApplicantDetails.getReligionId().getId() > 0) {
				objform.setReligionId(String.valueOf(empApplicantDetails
						.getReligionId().getId()));
			}
			
			
			
			
			if (empApplicantDetails.getCountryByPermanentAddressCountryId() != null
					&& empApplicantDetails
							.getCountryByPermanentAddressCountryId().getId() > 0) {
				objform.setCountryId(String.valueOf(empApplicantDetails
						.getCountryByPermanentAddressCountryId().getId()));
			}
			

			if (StringUtils
					.isNotEmpty(empApplicantDetails.getDesignationName())
					&& empApplicantDetails.getDesignationName() != null) {
				objform
						.setDesignation(empApplicantDetails
								.getDesignationName());
			}
			
			
			if (empApplicantDetails.getDesignation() != null
					&& empApplicantDetails.getDesignation().getId() > 0) {
				objform.setDesignationId(String.valueOf(empApplicantDetails
						.getDesignation().getId()));
			}
			
		
			
			

			if (empApplicantDetails.getEmpQualificationLevel() != null
					&& empApplicantDetails.getEmpQualificationLevel().getId() > 0) {
				objform.setEmpQualificationLevelId(String.valueOf(empApplicantDetails
						.getEmpQualificationLevel().getId()));
			}
			
			
			if (empApplicantDetails.getDob() != null
					&& !empApplicantDetails.getDob().toString().isEmpty()) {
				objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getDob().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
			}
			
			
			
			
			
			if (empApplicantDetails.getTotalExpMonths() != null
					&& !empApplicantDetails.getTotalExpMonths().isEmpty()) {
				objform.setCurrentExpMonths(Integer.parseInt(empApplicantDetails
						.getTotalExpMonths()));
			}
			if (empApplicantDetails.getTotalExpYear() != null
					&& !empApplicantDetails.getTotalExpYear().isEmpty()) {
				objform.setCurrentExpYears(Integer.parseInt(empApplicantDetails
						.getTotalExpYear()));
			}
			
			if (empApplicantDetails.getRelevantExpMonths() != null
					&& !empApplicantDetails.getRelevantExpMonths().isEmpty()) {
				objform.setExpMonths(String.valueOf(empApplicantDetails
						.getRelevantExpMonths()));
			}
			if (empApplicantDetails.getRelevantExpYears() != null
					&& !empApplicantDetails.getTotalExpYear().isEmpty()) {
				objform.setExpYears(String.valueOf(empApplicantDetails
						.getRelevantExpYears()));
			}

			
			

			
			
			
			if (empApplicantDetails.getStateByPermanentAddressStateId() != null
					&& empApplicantDetails.getStateByPermanentAddressStateId()
							.getId() > 0) {
				objform.setStateId(String.valueOf(empApplicantDetails
						.getStateByPermanentAddressStateId().getId()));
			}
			if (empApplicantDetails.getStreamId() != null
					&& empApplicantDetails.getStreamId().getId() > 0) {
				objform.setStreamId(String.valueOf(empApplicantDetails
						.getStreamId().getId()));

			}

			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
			}
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getSubjectSpecilization())
					&& empApplicantDetails.getSubjectSpecilization() != null) {
				objform.setSubjectSpecialisation(empApplicantDetails.getSubjectSpecilization());
			}
			
			if (StringUtils.isNotEmpty(empApplicantDetails.getStaffId())
					&& empApplicantDetails.getStaffId() != null) {
				objform.setStaffId(empApplicantDetails.getStaffId());
			}
			
			
			
			Map<String,String> designationMap=txn.getDesignationMap();
			 if(designationMap!=null){
				 objform.setDesignationMap(designationMap);
				// employeeInfoEditForm.setPostAppliedMap(designationMap);
			 }
			 
			 Map<String,String> departmentMap=txn.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 objform.setDepartmentMap(departmentMap);
				 
		}
			 
			 if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressLine1())
						&& empApplicantDetails.getPermanentAddressLine1() != null) {
					objform.setAddressLine1(empApplicantDetails
							.getPermanentAddressLine1());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressLine2())
						&& empApplicantDetails.getPermanentAddressLine2() != null) {
					objform.setAddressLine2(empApplicantDetails
							.getPermanentAddressLine2());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressCity())
						&& empApplicantDetails.getPermanentAddressCity() != null) {
					objform.setCity(empApplicantDetails.getPermanentAddressCity());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressZip())
						&& empApplicantDetails.getPermanentAddressZip() != null) {
					objform.setPermanentZipCode(empApplicantDetails
							.getPermanentAddressZip());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getPermanentAddressStateOthers())
						&& empApplicantDetails.getPermanentAddressStateOthers() != null) {
					objform.setOtherPermanentState(empApplicantDetails
							.getPermanentAddressStateOthers());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressLine1())
						&& empApplicantDetails.getCommunicationAddressLine1() != null) {
					objform.setCurrentAddressLine1(empApplicantDetails
							.getCommunicationAddressLine1());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressLine2())
						&& empApplicantDetails.getCommunicationAddressLine2() != null) {
					objform.setCurrentAddressLine2(empApplicantDetails
							.getCommunicationAddressLine2());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressCity())
						&& empApplicantDetails.getCommunicationAddressCity() != null) {
					objform.setCurrentCity(empApplicantDetails
							.getCommunicationAddressCity());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressStateOthers())
						&& empApplicantDetails.getCommunicationAddressStateOthers() != null) {
					objform.setOtherCurrentState(empApplicantDetails
							.getCommunicationAddressStateOthers());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getCommunicationAddressZip())
						&& empApplicantDetails.getCommunicationAddressZip() != null) {
					objform.setCurrentZipCode(empApplicantDetails
							.getCommunicationAddressZip());
				}
				if (empApplicantDetails.getCountryByPermanentAddressCountryId() != null
						&& empApplicantDetails
								.getCountryByPermanentAddressCountryId().getId() > 0) {
					objform.setCountryId(String.valueOf(empApplicantDetails
							.getCountryByPermanentAddressCountryId().getId()));
				}
				if (empApplicantDetails.getCountryByCommunicationAddressCountryId() != null
						&& empApplicantDetails
								.getCountryByCommunicationAddressCountryId()
								.getId() > 0) {
					objform.setCurrentCountryId(String.valueOf(empApplicantDetails
							.getCountryByCommunicationAddressCountryId().getId()));
				}
				if (empApplicantDetails.getCurrentAddressHomeTelephone1() != null
						&& !empApplicantDetails.getCurrentAddressHomeTelephone1()
								.isEmpty()) {
					objform.setHomePhone1(empApplicantDetails
							.getCurrentAddressHomeTelephone1());
				}

				if (empApplicantDetails.getCurrentAddressHomeTelephone2() != null
						&& !empApplicantDetails.getCurrentAddressHomeTelephone2()
								.isEmpty()) {
					objform.setHomePhone2(empApplicantDetails
							.getCurrentAddressHomeTelephone2());
				}

				if (empApplicantDetails.getCurrentAddressHomeTelephone3() != null
						&& !empApplicantDetails.getCurrentAddressHomeTelephone3()
								.isEmpty()) {
					objform.setHomePhone3(empApplicantDetails
							.getCurrentAddressHomeTelephone3());
				}
				if (empApplicantDetails.getCurrentAddressWorkTelephone1() != null
						&& !empApplicantDetails.getCurrentAddressWorkTelephone1()
								.isEmpty()) {
					objform.setWorkPhNo1(empApplicantDetails
							.getCurrentAddressWorkTelephone1());
				}

				if (empApplicantDetails.getCurrentAddressWorkTelephone2() != null
						&& !empApplicantDetails.getCurrentAddressWorkTelephone2()
								.isEmpty()) {
					objform.setWorkPhNo2(empApplicantDetails
							.getCurrentAddressWorkTelephone2());
				}

				if (empApplicantDetails.getCurrentAddressWorkTelephone3() != null
						&& !empApplicantDetails.getCurrentAddressWorkTelephone3()
								.isEmpty()) {
					objform.setWorkPhNo3(empApplicantDetails
							.getCurrentAddressWorkTelephone3());
				}

				if (StringUtils
						.isNotEmpty(empApplicantDetails.getOrganistionName())
						&& empApplicantDetails.getOrganistionName() != null) {
					objform.setOrgAddress(empApplicantDetails.getOrganistionName());
				}
				if (empApplicantDetails.getDesignation() != null
						&& empApplicantDetails.getDesignation().getId() > 0) {
					objform.setDesignationPfId(String.valueOf(empApplicantDetails
							.getDesignation().getId()));
				}
				
				
				if (empApplicantDetails.getBooks() != null
						&& !empApplicantDetails.getBooks().isEmpty()) {
					objform
							.setBooks(String
									.valueOf(empApplicantDetails.getBooks()));
				}
				if (empApplicantDetails.getNoOfPublicationsNotRefered() != null
						&& !empApplicantDetails.getNoOfPublicationsNotRefered()
								.isEmpty()) {
					objform.setNoOfPublicationsNotRefered(empApplicantDetails
							.getNoOfPublicationsNotRefered());
				}
				if (empApplicantDetails.getNoOfPublicationsRefered() != null
						&& !empApplicantDetails.getNoOfPublicationsRefered()
								.isEmpty()) {
					objform.setNoOfPublicationsRefered(empApplicantDetails
							.getNoOfPublicationsRefered());
				}

				if (empApplicantDetails.getEmpQualificationLevel() != null
						&& empApplicantDetails.getEmpQualificationLevel().getId() > 0) {
					objform.setQualificationId(String.valueOf(empApplicantDetails
							.getEmpQualificationLevel().getId()));
				}
				
				
				
				
				
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getEmergencyContName())
						&& empApplicantDetails.getEmergencyContName() != null) {
					objform.setEmContactName(empApplicantDetails
							.getEmergencyContName());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getEmergencyHomeTelephone())
						&& empApplicantDetails.getEmergencyHomeTelephone() != null) {
					objform.setEmContactHomeTel(empApplicantDetails
							.getEmergencyHomeTelephone());
				}
				if (StringUtils
						.isNotEmpty(empApplicantDetails.getEmergencyMobile())
						&& empApplicantDetails.getEmergencyMobile() != null) {
					objform.setEmContactMobile(empApplicantDetails
							.getEmergencyMobile());
				}
				if (StringUtils
						.isNotEmpty(empApplicantDetails.getEmContactAddress())
						&& empApplicantDetails.getEmContactAddress() != null) {
					objform.setEmContactAddress(empApplicantDetails
							.getEmContactAddress());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails
						.getEmergencyWorkTelephone())
						&& empApplicantDetails.getEmergencyWorkTelephone() != null) {
					objform.setEmContactWorkTel(empApplicantDetails
							.getEmergencyWorkTelephone());
				}

				
				
				
				
				if (empApplicantDetails.getStateByCommunicationAddressStateId() != null
						&& empApplicantDetails
								.getStateByCommunicationAddressStateId().getId() > 0) {
					objform.setCurrentState(String.valueOf(empApplicantDetails
							.getStateByCommunicationAddressStateId().getId()));
				}
				if (empApplicantDetails.getStateByPermanentAddressStateId() != null
						&& empApplicantDetails.getStateByPermanentAddressStateId()
								.getId() > 0) {
					objform.setStateId(String.valueOf(empApplicantDetails
							.getStateByPermanentAddressStateId().getId()));
				}
				if (empApplicantDetails.getStreamId() != null
						&& empApplicantDetails.getStreamId().getId() > 0) {
					objform.setStreamId(String.valueOf(empApplicantDetails
							.getStreamId().getId()));

				}

				
				if (empApplicantDetails.getWorkLocationId() != null
						&& empApplicantDetails.getWorkLocationId().getId() > 0) {
					objform.setWorkLocationId(String.valueOf(empApplicantDetails
							.getWorkLocationId().getId()));
				}
				
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
						&& empApplicantDetails.getWorkEmail() != null) {
					objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
				}
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus())
						&& empApplicantDetails.getMaritalStatus() != null) {
					objform.setMaritialStatus(empApplicantDetails.getMaritalStatus());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails.getCode())
						&& empApplicantDetails.getCode() != null) {
					objform.setCode(empApplicantDetails.getCode());
				}
				if (StringUtils.isNotEmpty(empApplicantDetails.getReservationCategory())
						&& empApplicantDetails.getReservationCategory() != null) {
					objform.setReservationCategory(empApplicantDetails.getReservationCategory());
				}
				
				
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getIsSameAddress()))) {
					String Value = String.valueOf(empApplicantDetails
							.getIsSameAddress());
					if (Value.equals("true"))
						objform.setSameAddress("true");
					else
						objform.setSameAddress("false");

				}
				
				if (empApplicantDetails.getNationality() != null
						&& empApplicantDetails.getNationality().getId() > 0) {
					objform.setNationalityId(String.valueOf(empApplicantDetails
							.getNationality().getId()));
				}
				
				if (StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus())
						&& empApplicantDetails.getMaritalStatus() != null) {
					objform.setMaritialStatus(String.valueOf(empApplicantDetails
							.getMaritalStatus()));
				}
				
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getDisplayInWebsite()))) {
					String Value = String.valueOf(empApplicantDetails.getDisplayInWebsite());
					if (Value.equals("true"))
						objform.setDisplayInWebsite("1");
					else
						objform.setDisplayInWebsite("0");

				}
				if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
						.getActive()))) {
					String Value = String.valueOf(empApplicantDetails.getActive());
					if (Value.equals("true"))
						objform.setActive("1");
					else
						objform.setActive("0");

				}
				
				
			
		
		
		if (empApplicantDetails.getEmpAcademicDeatils()  != null) {
			Set<EmpAcademicQualificationBO> empAcademic = empApplicantDetails
					.getEmpAcademicDeatils();
			if (empAcademic != null && !empAcademic.isEmpty()) {
				Iterator<EmpAcademicQualificationBO> iterator = empAcademic.iterator();
				List<EmpAcademicQualificationTO> empAcademicQualificationTOs = new ArrayList<EmpAcademicQualificationTO>();

				while (iterator.hasNext()) {
					EmpAcademicQualificationBO empAcademicbo = iterator.next();
					if (empAcademic != null) {
						EmpAcademicQualificationTO empAcademicTo = new EmpAcademicQualificationTO();

						if (empAcademicTo.getId() > 0) {
							empAcademicTo.setId(empAcademicbo.getId());
						}
						if (StringUtils.isNotEmpty(empAcademicbo
								.getCourseName())) {
							empAcademicTo.setCourseName(empAcademicbo
									.getCourseName());
						}

						if (StringUtils.isNotEmpty(empAcademicbo.getUniversity())) {
							empAcademicTo.setUniversityName(empAcademicbo.getUniversity());
						}
						if (StringUtils.isNotEmpty(empAcademicbo.getYear())) {
							empAcademicTo.setYear(empAcademicbo.getYear());
						}
						if (StringUtils.isNotEmpty(empAcademicbo.getGrade())) {
							empAcademicTo.setGrade(empAcademicbo.getGrade());
						}


						empAcademicQualificationTOs.add(empAcademicTo);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpqualification(empAcademicQualificationTOs);
			} else {

				List<EmpAcademicQualificationTO> flist = new ArrayList<EmpAcademicQualificationTO>();
				EmpAcademicQualificationTO empQualificationTO = new EmpAcademicQualificationTO();
				empQualificationTO.setCourseName("");
				empQualificationTO.setUniversityName("");
				empQualificationTO.setYear("");
				empQualificationTO.setGrade("");
				objform.setEmpQualificationListSize(String.valueOf(flist.size()));
				flist.add(empQualificationTO);
				EmployeeInfoEditNewTO editNewTO = new EmployeeInfoEditNewTO();
				editNewTO.setEmpqualification(flist);
				objform.setEmployeeInfoEditNewTO(editNewTO);
			}
		}
		
		if (empApplicantDetails.getEmpIntrestDetails()  != null) {
			Set<EmpIntrestsBO> empIntrestsBOs =empApplicantDetails.getEmpIntrestDetails();
			if (empIntrestsBOs != null && !empIntrestsBOs.isEmpty()) {
				Iterator<EmpIntrestsBO> iterator = empIntrestsBOs.iterator();
				List<EmpInterestsTO> empInterestsTOs = new ArrayList<EmpInterestsTO>();

				while (iterator.hasNext()) {
					EmpIntrestsBO empIntrestsBO = iterator.next();
					if (empIntrestsBO != null) {
						EmpInterestsTO empInterestsTO = new EmpInterestsTO();

						if (empInterestsTO.getId() > 0) {
							empInterestsTO.setId(empIntrestsBO.getId());
						}
						if (StringUtils.isNotEmpty(empIntrestsBO
								.getTopic())) {
							empInterestsTO.setTopic(empIntrestsBO
									.getTopic());
						}

						empInterestsTOs.add(empInterestsTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setInterest(empInterestsTOs);
			} else {

				List<EmpInterestsTO> flist1 = new ArrayList<EmpInterestsTO>();
				EmpInterestsTO empInterestsTO = new EmpInterestsTO();
				empInterestsTO.setTopic("");
				objform.setEmpIntrestListSize(String.valueOf(flist1.size()));
				flist1.add(empInterestsTO);
				objform.getEmployeeInfoEditNewTO().setInterest(flist1);
			}
		}
		
		
		if (empApplicantDetails.getEmpResearchDetails()  != null) {
			Set<EmpResearchBo> empResearchBos =empApplicantDetails.getEmpResearchDetails();
			if (empResearchBos != null && !empResearchBos.isEmpty()) {
				Iterator<EmpResearchBo> iterator = empResearchBos.iterator();
				List<EmpFieldResearchTO> empResearchTOs = new ArrayList<EmpFieldResearchTO>();

				while (iterator.hasNext()) {
					EmpResearchBo empResearchBo = iterator.next();
					if (empResearchBo != null) {
						EmpFieldResearchTO empResearchTO = new EmpFieldResearchTO();

						if (empResearchTO.getId() > 0) {
							empResearchTO.setId(empResearchBo.getId());
						}
						if (StringUtils.isNotEmpty(empResearchBo
								.getTitle())) {
							empResearchTO.setTopic(empResearchBo
									.getTitle());
						}

						empResearchTOs.add(empResearchTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setResearch(empResearchTOs);
			} else {

				List<EmpFieldResearchTO> flist1 = new ArrayList<EmpFieldResearchTO>();
				EmpFieldResearchTO empFieldResearchTO = new EmpFieldResearchTO();
				empFieldResearchTO.setTopic("");
				objform.setEmpResearchListSize(String.valueOf(flist1.size()));
				flist1.add(empFieldResearchTO);
				objform.getEmployeeInfoEditNewTO().setResearch(flist1);
			}
		}
		
		
		if (empApplicantDetails.getEmpGuideshipDetails()  != null) {
			Set<EmpGuideshipDetailsBo> empGuideshipDetailsBos =empApplicantDetails.getEmpGuideshipDetails();
			if (empGuideshipDetailsBos != null && !empGuideshipDetailsBos.isEmpty()) {
				Iterator<EmpGuideshipDetailsBo> iterator = empGuideshipDetailsBos.iterator();
				List<EmpGuideShipDetailsTO> empGuideShipDetailsTOs = new ArrayList<EmpGuideShipDetailsTO>();

				while (iterator.hasNext()) {
					EmpGuideshipDetailsBo empGuideshipDetailsBo = iterator.next();
					if (empGuideshipDetailsBo != null) {
						EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();

						if (empGuideShipDetailsTO.getId() > 0) {
							empGuideShipDetailsTO.setId(empGuideshipDetailsBo.getId());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getScholarName())) {
							empGuideShipDetailsTO.setPhdScholarName(empGuideshipDetailsBo
									.getScholarName());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getRegistraionYear())) {
							empGuideShipDetailsTO.setRegistrationYear(empGuideshipDetailsBo
									.getRegistraionYear());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getAwarded())) {
							empGuideShipDetailsTO.setAwarded(empGuideshipDetailsBo
									.getAwarded());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getAwardedYear())) {
							empGuideShipDetailsTO.setYear(empGuideshipDetailsBo
									.getAwardedYear());
						}
						if (StringUtils.isNotEmpty(empGuideshipDetailsBo
								.getAwardedThesisName())) {
							empGuideShipDetailsTO.setThesisName(empGuideshipDetailsBo
									.getAwardedThesisName());
						}

						empGuideShipDetailsTOs.add(empGuideShipDetailsTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setGuideship(empGuideShipDetailsTOs);
			} else {

				List<EmpGuideShipDetailsTO> flist2 = new ArrayList<EmpGuideShipDetailsTO>();
				EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();
				empGuideShipDetailsTO.setAwarded("");
				empGuideShipDetailsTO.setPhdScholarName("");
				empGuideShipDetailsTO.setThesisName("");
				empGuideShipDetailsTO.setYear("");
				empGuideShipDetailsTO.setRegistrationYear("");
				objform.setEmpGuidshipListSize(String.valueOf(flist2.size()));
				flist2.add(empGuideShipDetailsTO);
				objform.getEmployeeInfoEditNewTO().setGuideship(flist2);
			}
		}
		
		if (empApplicantDetails.getEmpDutiesDeatils()  != null) {
			Set<EmpDutiesDetailsBO> empDutiesDetailsBOs =empApplicantDetails.getEmpDutiesDeatils();
			if (empDutiesDetailsBOs != null && !empDutiesDetailsBOs.isEmpty()) {
				Iterator<EmpDutiesDetailsBO> iterator = empDutiesDetailsBOs.iterator();
				List<EmpDutiesPerformedTO> empDutiesPerformedTOs = new ArrayList<EmpDutiesPerformedTO>();

				while (iterator.hasNext()) {
					EmpDutiesDetailsBO empDutiesDetailsBO = iterator.next();
					if (empDutiesDetailsBO != null) {
						EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();

						if (empDutiesPerformedTO.getId() > 0) {
							empDutiesPerformedTO.setId(empDutiesDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empDutiesDetailsBO
								.getPosition())) {
							empDutiesPerformedTO.setPositionName(empDutiesDetailsBO
									.getPosition());
						}
						if (empDutiesDetailsBO
								.getFromDate() != null) {
							empDutiesPerformedTO.setFromDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if (empDutiesDetailsBO.getToDate() != null) {
							empDutiesPerformedTO.setToDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						

						empDutiesPerformedTOs.add(empDutiesPerformedTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setDuties(empDutiesPerformedTOs);
			} else {

				List<EmpDutiesPerformedTO> flist3 = new ArrayList<EmpDutiesPerformedTO>();
				EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();
				empDutiesPerformedTO.setPositionName("");
				empDutiesPerformedTO.setFromDate("");
				empDutiesPerformedTO.setToDate("");
				objform.setEmpGuidshipListSize(String.valueOf(flist3.size()));
				flist3.add(empDutiesPerformedTO);
				objform.getEmployeeInfoEditNewTO().setDuties(flist3);
			}
		}
		
		if (empApplicantDetails.getEmpProjectDetails()  != null) {
			Set<EmpProjectResearchDetailsBO> empProjectResearchDetailsBOs =empApplicantDetails.getEmpProjectDetails();
			if (empProjectResearchDetailsBOs != null && !empProjectResearchDetailsBOs.isEmpty()) {
				Iterator<EmpProjectResearchDetailsBO> iterator = empProjectResearchDetailsBOs.iterator();
				List<EmpResearchTO> empResearchProjectTOs = new ArrayList<EmpResearchTO>();

				while (iterator.hasNext()) {
					EmpProjectResearchDetailsBO empResearchDetailsBO = iterator.next();
					if (empResearchDetailsBO != null) {
						EmpResearchTO empResearchProjectTO = new EmpResearchTO();

						if (empResearchProjectTO.getId() > 0) {
							empResearchProjectTO.setId(empResearchDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle()
								)) {
							empResearchProjectTO.setProjectName(empResearchDetailsBO.getPrjectName());
						}
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle())) {
					       empResearchProjectTO.setTitle(empResearchDetailsBO.getTitle());
				        }
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getFundingAgency())) {
					   empResearchProjectTO.setFindingAgencyName(empResearchDetailsBO.getFundingAgency());
				        }
						if (StringUtils.isNotEmpty(empResearchDetailsBO.getAmount())) {
					      empResearchProjectTO.setAmount(empResearchDetailsBO.getAmount());
				           }
						if (empResearchDetailsBO
								.getFromDate() != null) {
							empResearchProjectTO.setFromDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO
									.getFromDate() .toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if (empResearchDetailsBO.getToDate() != null) {
							empResearchProjectTO.setToDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						

						empResearchProjectTOs.add(empResearchProjectTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setResearchProject(empResearchProjectTOs);
			} else {

				List<EmpResearchTO> flist4 = new ArrayList<EmpResearchTO>();
				EmpResearchTO empResearchTO = new EmpResearchTO();
				empResearchTO.setFindingAgencyName("");
				empResearchTO.setAmount("");
				empResearchTO.setProjectName("");
				empResearchTO.setTitle("");
				empResearchTO.setFromDate1("");
				empResearchTO.setToDate1("");
				objform.setEmpResearchProjectListSize(String.valueOf(flist4.size()));
				flist4.add(empResearchTO);
				objform.getEmployeeInfoEditNewTO().setResearchProject(flist4);
			}
		}
		
		if (empApplicantDetails.getEmpResearchPublication() != null) {
			Set<EmpReaserchPublishcationDetailsBO> empProjePublishcationDetailsBOs =empApplicantDetails.getEmpResearchPublication();
			if (empProjePublishcationDetailsBOs != null && !empProjePublishcationDetailsBOs.isEmpty()) {
				Iterator<EmpReaserchPublishcationDetailsBO> iterator = empProjePublishcationDetailsBOs.iterator();
				List<EmpResearchPublicationTO> empResearchPublicationTOs = new ArrayList<EmpResearchPublicationTO>();

				while (iterator.hasNext()) {
					EmpReaserchPublishcationDetailsBO empReaserchPublishcationDetailsBO = iterator.next();
					if (empReaserchPublishcationDetailsBO != null) {
						EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();

						if (empResearchPublicationTO.getId() > 0) {
							empResearchPublicationTO.setId(empReaserchPublishcationDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getTitle())) {
							empResearchPublicationTO.setPaperTitle(empReaserchPublishcationDetailsBO.getTitle());
						}
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getJournalName())) {
							empResearchPublicationTO.setJournalName(empReaserchPublishcationDetailsBO.getJournalName());
				        }
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getUGC())) {
							empResearchPublicationTO.setUGCNonUGC(empReaserchPublishcationDetailsBO.getUGC());
				        }
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getISBNISSNNo())) {
							empResearchPublicationTO.setISBNISSNNo(empReaserchPublishcationDetailsBO.getISBNISSNNo());
				        }
						if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getYear())) {
							empResearchPublicationTO.setYear(empReaserchPublishcationDetailsBO.getYear());
				        }
						
						empResearchPublicationTOs.add(empResearchPublicationTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setResearchPubliction(empResearchPublicationTOs);
			} else {

				List<EmpResearchPublicationTO> flist5 = new ArrayList<EmpResearchPublicationTO>();
				EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();
				empResearchPublicationTO.setISBNISSNNo("");
				empResearchPublicationTO.setJournalName("");
				empResearchPublicationTO.setPaperTitle("");
				empResearchPublicationTO.setUGCNonUGC("");
				empResearchPublicationTO.setYear("");
				
				objform.setEmpResearchProjectListSize(String.valueOf(flist5.size()));
				flist5.add(empResearchPublicationTO);
				objform.getEmployeeInfoEditNewTO().setResearchPubliction(flist5);
			}
		}
		
		if (empApplicantDetails.getEmpBooksPublished() != null) {
			Set<EmpBooksPublishedDetailsBO> empBooksPublishedDetailsBOs =empApplicantDetails.getEmpBooksPublished();
			if (empBooksPublishedDetailsBOs != null && !empBooksPublishedDetailsBOs.isEmpty()) {
				Iterator<EmpBooksPublishedDetailsBO> iterator = empBooksPublishedDetailsBOs.iterator();
				List<EmpBooksPublishedTO> empBooksPublishedTOs = new ArrayList<EmpBooksPublishedTO>();

				while (iterator.hasNext()) {
					EmpBooksPublishedDetailsBO empBooksPublishedDetailsBO = iterator.next();
					if (empBooksPublishedDetailsBO != null) {
						EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();

						if (empBooksPublishedTO.getId() > 0) {
							empBooksPublishedTO.setId(empBooksPublishedDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getBookName())) {
							empBooksPublishedTO.setTitleName(empBooksPublishedDetailsBO.getBookName());
						}
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getPublisherName())) {
							empBooksPublishedTO.setPublisherName(empBooksPublishedDetailsBO.getPublisherName());
				        }
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getContribution())) {
							empBooksPublishedTO.setContibutionName(empBooksPublishedDetailsBO.getContribution());
				        }
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getYear())) {
							empBooksPublishedTO.setYear(empBooksPublishedDetailsBO.getYear());
				        }
						if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getISBNISSNNo())) {
							empBooksPublishedTO.setISBNISSN(empBooksPublishedDetailsBO.getISBNISSNNo());
				        }
						
						empBooksPublishedTOs.add(empBooksPublishedTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpBooks(empBooksPublishedTOs);
			} else {

				List<EmpBooksPublishedTO> flist6 = new ArrayList<EmpBooksPublishedTO>();
				EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();
				empBooksPublishedTO.setContibutionName("");
				empBooksPublishedTO.setISBNISSN("");
				empBooksPublishedTO.setPublisherName("");
				empBooksPublishedTO.setTitleName("");
				empBooksPublishedTO.setYear("");
				
				objform.setEmpBooksPublishListSize((String.valueOf(flist6.size())));
				flist6.add(empBooksPublishedTO);
				objform.getEmployeeInfoEditNewTO().setEmpBooks(flist6);
			}
		}
		
		if (empApplicantDetails.getEmpPaperPresentaion() != null) {
			Set<EmpPaperPresentationBO> empPaperPresentationBOs =empApplicantDetails.getEmpPaperPresentaion();
			if (empPaperPresentationBOs != null && !empPaperPresentationBOs.isEmpty()) {
				Iterator<EmpPaperPresentationBO> iterator = empPaperPresentationBOs.iterator();
				List<EmpPaperPresentationTO> empPresentationTOs = new ArrayList<EmpPaperPresentationTO>();

				while (iterator.hasNext()) {
					EmpPaperPresentationBO empPaperPresentationBO = iterator.next();
					if (empPaperPresentationBO != null) {
						EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();

						if (empPaperPresentationTO.getId() > 0) {
							empPaperPresentationTO.setId(empPaperPresentationBO.getId());
						}
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getInstitution())) {
							empPaperPresentationTO.setOrganisation(empPaperPresentationBO.getInstitution());
						}
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getInterRegoinal())) {
							empPaperPresentationTO.setRegional(empPaperPresentationBO.getInterRegoinal());
				        }
						if (empPaperPresentationBO.getDate() != null) {
							empPaperPresentationTO.setDate1(CommonUtil.ConvertStringToDateFormat(empPaperPresentationBO.getDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getProceedingsTile())) {
							empPaperPresentationTO.setSeminarName(empPaperPresentationBO.getProceedingsTile());
				        }
						if (StringUtils.isNotEmpty(empPaperPresentationBO.getPaperTile())) {
							empPaperPresentationTO.setPaperName(empPaperPresentationBO.getPaperTile());
				        }
						
						empPresentationTOs.add(empPaperPresentationTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpPaper(empPresentationTOs);
			} else {

				List<EmpPaperPresentationTO> flist7 = new ArrayList<EmpPaperPresentationTO>();
				EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();
				empPaperPresentationTO.setDate1("");
				empPaperPresentationTO.setOrganisation("");
				empPaperPresentationTO.setPaperName("");
				empPaperPresentationTO.setRegional("");
				empPaperPresentationTO.setSeminarName("");
				
				objform.setEmpBooksPublishListSize((String.valueOf(flist7.size())));
				flist7.add(empPaperPresentationTO);
				objform.getEmployeeInfoEditNewTO().setEmpPaper(flist7);
			}
		}
		
		if (empApplicantDetails.getEmpSeminarAttended() != null) {
			Set<EmpSeminarAttendedDetailsBO> empSeminarAttendedDetailsBOs =empApplicantDetails.getEmpSeminarAttended();
			if (empSeminarAttendedDetailsBOs != null && !empSeminarAttendedDetailsBOs.isEmpty()) {
				Iterator<EmpSeminarAttendedDetailsBO> iterator = empSeminarAttendedDetailsBOs.iterator();
				List<EmpSeminarAttendedDetailsTO> empSeminarAttendedDetailsTOs = new ArrayList<EmpSeminarAttendedDetailsTO>();

				while (iterator.hasNext()) {
					EmpSeminarAttendedDetailsBO empSeminarAttendedDetailsBO = iterator.next();
					if (empSeminarAttendedDetailsBO != null) {
						EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();

						if (empSeminarAttendedDetailsTO.getId() > 0) {
							empSeminarAttendedDetailsTO.setId(empSeminarAttendedDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInstitution())) {
							empSeminarAttendedDetailsTO.setOrganisation(empSeminarAttendedDetailsBO.getInstitution());
						}
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInterRegional())) {
							empSeminarAttendedDetailsTO.setInterRegional(empSeminarAttendedDetailsBO.getInterRegional());
				        }
						if (empSeminarAttendedDetailsBO.getFromDate2() != null) {
							empSeminarAttendedDetailsTO.setFromDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getFromDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (empSeminarAttendedDetailsBO.getToDate2() != null) {
							empSeminarAttendedDetailsTO.setToDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getToDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getParticipation())) {
							empSeminarAttendedDetailsTO.setParticipation(empSeminarAttendedDetailsBO.getParticipation());
				        }
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getTitle())) {
							empSeminarAttendedDetailsTO.setSeminarName(empSeminarAttendedDetailsBO.getTitle());
				        }
						if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getWorkShop())) {
							empSeminarAttendedDetailsTO.setSeminar(empSeminarAttendedDetailsBO.getWorkShop());
				        }
						
						empSeminarAttendedDetailsTOs.add(empSeminarAttendedDetailsTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
			} else {

				List<EmpSeminarAttendedDetailsTO> flist8 = new ArrayList<EmpSeminarAttendedDetailsTO>();
				EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();
				empSeminarAttendedDetailsTO.setFromDate2("");
				empSeminarAttendedDetailsTO.setInterRegional("");
				empSeminarAttendedDetailsTO.setOrganisation("");
				empSeminarAttendedDetailsTO.setSeminar("");
				empSeminarAttendedDetailsTO.setSeminarName("");
				empSeminarAttendedDetailsTO.setToDate2("");
				
				
				objform.setEmpSeminarattendedListSize((String.valueOf(flist8.size())));
				flist8.add(empSeminarAttendedDetailsTO);
				objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(flist8);
			}
		}
		
		if (empApplicantDetails.getEmpProfeDevelopment() != null) {
			Set<EmpProfessionalDevelopmentBO> empProfessionalDevelopmentBOs =empApplicantDetails.getEmpProfeDevelopment();
			if (empProfessionalDevelopmentBOs != null && !empProfessionalDevelopmentBOs.isEmpty()) {
				Iterator<EmpProfessionalDevelopmentBO> iterator = empProfessionalDevelopmentBOs.iterator();
				List<EmpProfessionalDevelopmentTO> empProfessionalDevelopmentTOs = new ArrayList<EmpProfessionalDevelopmentTO>();

				while (iterator.hasNext()) {
					EmpProfessionalDevelopmentBO empProfessionalDevelopmentBO = iterator.next();
					if (empProfessionalDevelopmentBO != null) {
						EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();

						if (empProfessionalDevelopmentTO.getId() > 0) {
							empProfessionalDevelopmentTO.setId(empProfessionalDevelopmentBO.getId());
						}
						if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getName())) {
							empProfessionalDevelopmentTO.setName(empProfessionalDevelopmentBO.getName());
						}
						if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getInstitute())) {
							empProfessionalDevelopmentTO.setOrganisation(empProfessionalDevelopmentBO.getInstitute());
				        }
						if (empProfessionalDevelopmentBO.getFromDate() != null) {
							empProfessionalDevelopmentTO.setFromDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (empProfessionalDevelopmentBO.getToDate() != null) {
							empProfessionalDevelopmentTO.setToDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						
						empProfessionalDevelopmentTOs.add(empProfessionalDevelopmentTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
			} else {

				List<EmpProfessionalDevelopmentTO> flist9 = new ArrayList<EmpProfessionalDevelopmentTO>();
				EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();
				empProfessionalDevelopmentTO.setFromDate3("");
				empProfessionalDevelopmentTO.setName("");
				empProfessionalDevelopmentTO.setOrganisation("");
				empProfessionalDevelopmentTO.setToDate3("");
				
				
				objform.setEmpProfessionalDevelopmentListSize((String.valueOf(flist9.size())));
				flist9.add(empProfessionalDevelopmentTO);
				objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(flist9);
			}
		}
		
		if (empApplicantDetails.getEmpAwardDetails() != null) {
			Set<EmpAwardDetailsBO> empAwardDetailsBOs =empApplicantDetails.getEmpAwardDetails();
			if (empAwardDetailsBOs != null && !empAwardDetailsBOs.isEmpty()) {
				Iterator<EmpAwardDetailsBO> iterator = empAwardDetailsBOs.iterator();
				List<EmpAwardTO> empAwardTOs = new ArrayList<EmpAwardTO>();

				while (iterator.hasNext()) {
					EmpAwardDetailsBO empAwardDetailsBO = iterator.next();
					if (empAwardDetailsBO != null) {
						EmpAwardTO empAwardTO = new EmpAwardTO();

						if (empAwardTO.getId() > 0) {
							empAwardTO.setId(empAwardDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getActivityName())) {
							empAwardTO.setActivityname(empAwardDetailsBO.getActivityName());
						}
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getAwardName())) {
							empAwardTO.setAwardName(empAwardDetailsBO.getAwardName());
				        }
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
							empAwardTO.setAwardbodyName(empAwardDetailsBO.getAwardBody());
				        }
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getYear())) {
							empAwardTO.setYear(empAwardDetailsBO.getYear());
				        }
						if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
							empAwardTO.setRecognitionName(empAwardDetailsBO.getAward());
				        }
						empAwardTOs.add(empAwardTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpAward(empAwardTOs);
			} else {

				List<EmpAwardTO> flist10 = new ArrayList<EmpAwardTO>();
				EmpAwardTO empAwardTO = new EmpAwardTO();
				empAwardTO.setActivityname("");
				empAwardTO.setAwardbodyName("");
				empAwardTO.setAwardName("");
				empAwardTO.setRecognitionName("");
				empAwardTO.setYear("");
				
				
				objform.setEmpAwardListSize(String.valueOf(flist10.size()));
				flist10.add(empAwardTO);
				objform.getEmployeeInfoEditNewTO().setEmpAward(flist10);
			}
		}
		
		if (empApplicantDetails.getEmpMemDetailsBOs() != null) {
			Set<EmpMemeberShipDetailsBO> empMemeberShipDetailsBOs =empApplicantDetails.getEmpMemDetailsBOs();
			if (empMemeberShipDetailsBOs != null && !empMemeberShipDetailsBOs.isEmpty()) {
				Iterator<EmpMemeberShipDetailsBO> iterator = empMemeberShipDetailsBOs.iterator();
				List<EmpMemberShipAcademicBodyTO> empMemberShipAcademicBodyTOs = new ArrayList<EmpMemberShipAcademicBodyTO>();

				while (iterator.hasNext()) {
					EmpMemeberShipDetailsBO empMemeberShipDetailsBO = iterator.next();
					if (empMemeberShipDetailsBO != null) {
						EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();

						if (empMemberShipAcademicBodyTO.getId() > 0) {
							empMemberShipAcademicBodyTO.setId(empMemeberShipDetailsBO.getId());
						}
						if (StringUtils.isNotEmpty(empMemeberShipDetailsBO.getName())) {
							empMemberShipAcademicBodyTO.setName(empMemeberShipDetailsBO.getName());
						}
						if (empMemeberShipDetailsBO.getFromDate() != null) {
							empMemberShipAcademicBodyTO.setFromDate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						if (empMemeberShipDetailsBO.getToDate() != null) {
							empMemberShipAcademicBodyTO.setTodate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				        }
						
						empMemberShipAcademicBodyTOs.add(empMemberShipAcademicBodyTO);

					}
				}
				objform.getEmployeeInfoEditNewTO().setEmpMemberShip(empMemberShipAcademicBodyTOs);
			} else {

				List<EmpMemberShipAcademicBodyTO> flist11 = new ArrayList<EmpMemberShipAcademicBodyTO>();
				EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();
				empMemberShipAcademicBodyTO.setName("");
				empMemberShipAcademicBodyTO.setFromDate4("");
				empMemberShipAcademicBodyTO.setTodate4("");
				
				objform.setEmpMembershipAcademicListSize(String.valueOf(flist11.size()));
				flist11.add(empMemberShipAcademicBodyTO);
				objform.getEmployeeInfoEditNewTO().setEmpMemberShip(flist11);
			}
		}
		
		
		/*----------------------------------- code added by sudhir---------------------------------------*/
		/* calculating Experience in cjc (or) cu from joining Date to present day*/
		Date joiningDate = null;
		String fromDateDay = null;
		String fromDateMonth = null;
		if(objform.getDateOfJoining()!=null && !objform.getDateOfJoining().isEmpty()){
			 joiningDate = CommonUtil.ConvertStringToDate(objform.getDateOfJoining());
			 fromDateDay = objform.getDateOfJoining().substring(0, 2);
			 fromDateMonth = objform.getDateOfJoining().substring(3, 5);
		}
		String todaysDate = CommonUtil.getTodayDate();
		String toDateDay = todaysDate.substring(0, 2);
		String toDateMonth = todaysDate.substring(3, 5);
		Date toDate = CommonUtil.ConvertStringToDate(todaysDate);
		if(joiningDate!=null && toDate!=null){
			
		/*int years = CommonUtil.getYearsDiff(joiningDate,toDate);
		int months = CommonUtil.getMonthsBetweenTwoYears(joiningDate, toDate,fromDate,toDateNumber);*/
			double msPerGregorianYear = 365.25 * 86400 * 1000;
		 	double years1 =(toDate.getTime() - joiningDate.getTime()) / msPerGregorianYear;
		 	int yy = (int) years1;
	        int mm = (int) ((years1 - yy) * 12);
	        if(fromDateDay.equals(toDateDay)){
	        	if(fromDateMonth.equals(toDateMonth)){
	        		mm=0;
	        		yy = (int) Math.round(years1);
	        	}
	        }
	        if(fromDateDay.equals(toDateDay)){
	        	if(!fromDateMonth.equals(toDateMonth)){
	        		mm = (int) Math.round(((years1 - yy) * 12));
	        	}
	        }
		objform.setCurrentExpYears(yy);
		objform.setCurrentExpMonths(mm);
		}
		/* calculating TotalCurrent Experience Years and Months based on Recognised Experience and Experience in cjc (or) cu*/
		/*int totalYears = Integer.parseInt(objform.getRelevantExpYears())+objform.getCurrentExpYears();
		int totalMonths1 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getCurrentExpMonths() % 12;
		int totalMonths2 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getCurrentExpMonths() / 12;
		totalYears = totalYears + totalMonths2;
		objform.setTotalCurrentExpYears(totalYears);
		objform.setTotalCurrentExpMonths(totalMonths1);*/
		
		/*-----------------------------------------------------------------------------------------------*/
	

		
		
	}
	}

	public List<UserInfoTO> convertBoToFormProperty(List<Users> list)  throws Exception{

		List<UserInfoTO> userInfoToList = new ArrayList<UserInfoTO>();
		Iterator<Users> i = list.iterator();
		Users users;
		UserInfoTO userInfoTO;
		EmployeeTO employeeTO;
		GuestFacultyTO guestFacultyTO;
		RolesTO rolesTO;
		DepartmentEntryTO departmentTO;
		while (i.hasNext()) {
			userInfoTO = new UserInfoTO();
			guestFacultyTO = new GuestFacultyTO();
			employeeTO = new EmployeeTO();
			rolesTO = new RolesTO();
			departmentTO = new DepartmentEntryTO();
			users = (Users) i.next();
			if (users.getEmployee() != null) {
				employeeTO.setId((users.getEmployee() == null ? 0 : users .getEmployee().getId()));
				userInfoTO.setEmpOrGuestId(users .getEmployee().getId());
				employeeTO .setFirstName(users.getEmployee().getFirstName() == null ? "" : users.getEmployee().getFirstName());
				employeeTO .setMiddleName(users.getEmployee().getMiddleName() == null ? "" : users.getEmployee().getMiddleName());
				employeeTO .setLastName(users.getEmployee().getLastName() == null ? "" : users.getEmployee().getLastName());
				if (users.getEmployee().getDob() != null) {
					employeeTO.setDob(CommonUtil.formatDate(users.getEmployee() .getDob(), "dd/MM/yyyy"));
					userInfoTO.setDob(CommonUtil.formatDate(users.getEmployee().getDob(), "dd/MM/yyyy"));
				}
				employeeTO.setName(users.getEmployee().getFirstName() + " "
						+ (users.getEmployee().getMiddleName() != null && users.getEmployee().getMiddleName().trim()
										.length() > 0 ? users.getEmployee() .getMiddleName() : "")
						+ " " + (users.getEmployee().getLastName() != null && users.getEmployee().getLastName().trim()
										.length() > 0 ? users.getEmployee() .getLastName() : ""));
				userInfoTO.setName(users.getEmployee().getFirstName());
				if(users.getEmployee().getDepartment() != null ){
					userInfoTO.setDepartmentName(users.getEmployee().getDepartment().getName());
					}
					if(users.getEmployee().getDesignation() != null ){
					userInfoTO.setDesignationName(users.getEmployee().getDesignation().getName());
					}
				
			}
			if(users.getGuest()!=null)
			{
			guestFacultyTO.setId((users.getGuest() == null ? 0 : users .getGuest().getId()));
		
			userInfoTO.setEmpOrGuestId(users .getGuest().getId());
			guestFacultyTO.setFirstName(users.getGuest().getFirstName() == null ? "" : users.getGuest().getFirstName());
			guestFacultyTO.setName(users.getGuest().getFirstName());
			guestFacultyTO.setDepartmentName(users.getGuest().getDepartment().getName());
			
			
			userInfoTO.setName(users.getGuest().getFirstName());
			if(users.getGuest().getDepartment().getName() != null && !users.getGuest().getDepartment().getName().isEmpty()){
			userInfoTO.setDepartmentName(users.getGuest().getDepartment().getName());
			}
			if(users.getGuest().getDesignation().getName() != null && !users.getGuest().getDesignation().getName().isEmpty()){
			userInfoTO.setDesignationName(users.getGuest().getDesignation().getName());
			}
			
			}
			userInfoTO.setGuestFacultyTO(guestFacultyTO);
			userInfoTO.setEmployeeTO(employeeTO);	
			
			
			if (users.getRoles() != null && users.getRoles().getId() != 0) {
				rolesTO.setId(users.getRoles().getId());
				rolesTO.setName(users.getRoles().getName());
				userInfoTO.setRolesTO(rolesTO);
				userInfoTO.setRolesName(users.getRoles().getName());
			}
//			if (users.getDepartment() != null && users.getDepartment().getId() != 0) {
//				departmentTO.setId(users.getDepartment().getId());
//				departmentTO.setName(users.getDepartment().getName());
//				userInfoTO.setDepartment(departmentTO);
//				userInfoTO.setDepartmentName(users.getDepartment().getName());
//			}
			if (users.getUserName() != null && !users.getUserName().trim().isEmpty()) {
				userInfoTO.setUserName(users.getUserName());
			}
			userInfoTO.setId(users.getId());
			userInfoTO.setIsTeachingStaff(users.getIsTeachingStaff());
			if (users.getPwd() != null && !users.getPwd().trim().isEmpty()) {
				userInfoTO.setPwd(users.getPwd());
			}
			if(users.getStaffType()!=null && !users.getStaffType().isEmpty()){
				userInfoTO.setStaffType(users.getStaffType());
			}
			userInfoTO.setRemarksEntry(users.getIsAddRemarks());
			userInfoTO.setViewRemarks(users.getIsViewRemarks());
			userInfoTO.setIsRestrictedUser(users.getIsRestrictedUser());
			userInfoTO.setActive(users.getActive());
			if(users.getTillDate()!=null){
				userInfoTO.setTillDate(CommonUtil.formatDate(users.getTillDate(), "dd/MM/yyyy"));
			}
			
		
			userInfoToList.add(userInfoTO);
	
	}
		return userInfoToList;
	}

	public GuestFaculty convertFormToBoGuest(EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception {
		GuestFaculty employee = new GuestFaculty();
		if (employeeInfoEditNewForm.getGuestId() != null
				&& !employeeInfoEditNewForm.getGuestId().isEmpty() && Integer.parseInt(employeeInfoEditNewForm.getGuestId())>0) {
			employee.setId(Integer.parseInt(employeeInfoEditNewForm
					.getGuestId()));
			
			Set<EmpAcademicQualificationBO> eSet = getEmpAcademicObjects(employeeInfoEditNewForm);
			employee.setEmpAcademicDeatils(eSet);
			
			Set<EmpIntrestsBO> eSet2 = getEmpInterestsObjects(employeeInfoEditNewForm);
			employee.setEmpIntrestDetails(eSet2);
			
			Set<EmpResearchBo> eSet3 = getEmpResearchObjects(employeeInfoEditNewForm);
			employee.setEmpResearchDetails(eSet3);
			
			Set<EmpGuideshipDetailsBo> eSet4 = getEmpGuideshipObjects(employeeInfoEditNewForm);
			employee.setEmpGuideshipDetails(eSet4);
			
			Set<EmpDutiesDetailsBO> eSet5 = getEmpDutiesObjects(employeeInfoEditNewForm);
			employee.setEmpDutiesDeatils(eSet5);
			
			Set<EmpProjectResearchDetailsBO> eSet6 = getEmpProjectResearchObjects(employeeInfoEditNewForm);
			employee.setEmpProjectDetails(eSet6);
			
			Set<EmpReaserchPublishcationDetailsBO> eSet7 = getEmpPublicationObjects(employeeInfoEditNewForm);
			employee.setEmpResearchPublication(eSet7);
			
			Set<EmpBooksPublishedDetailsBO> eSet8 = getEmpBooksObjects(employeeInfoEditNewForm);
			employee.setEmpBooksPublished(eSet8);
			
			Set<EmpPaperPresentationBO> eSet9 = getEmpPaperObjects(employeeInfoEditNewForm);
			employee.setEmpPaperPresentaion(eSet9);
			
			Set<EmpSeminarAttendedDetailsBO> eSet10 = getEmpSeminarObjects(employeeInfoEditNewForm);
			employee.setEmpSeminarAttended(eSet10);
			
			Set<EmpProfessionalDevelopmentBO> eSet11 = getEmpProObjects(employeeInfoEditNewForm);
			employee.setEmpProfeDevelopment(eSet11);
			
			Set<EmpAwardDetailsBO> eSet12 = getEmpAwardObjects(employeeInfoEditNewForm);
			employee.setEmpAwardDetails(eSet12);
			
			Set<EmpMemeberShipDetailsBO> eSet13 = getEmpMemebershipObjects(employeeInfoEditNewForm);
			employee.setEmpMemDetailsBOs(eSet13);
			
			
			if (employeeInfoEditNewForm.getDesignationId() != null
					&& !employeeInfoEditNewForm.getDesignationId().isEmpty()) {
				Designation designation = new Designation();
				designation.setId(Integer.parseInt(employeeInfoEditNewForm.getDesignationId()));
				employee.setDesignation(designation);
			}
			
			if (employeeInfoEditNewForm.getDepartmentId() != null
					&& !employeeInfoEditNewForm.getDepartmentId().isEmpty()) {
				Department department = new Department();
				department.setId(Integer.parseInt(employeeInfoEditNewForm
						.getDepartmentId()));
				employee.setDepartment(department);
			}
			
			if (employeeInfoEditNewForm.getName() != null
					&& !employeeInfoEditNewForm.getName().isEmpty()) {
				employee.setFirstName(employeeInfoEditNewForm.getName().toUpperCase());
			}
			
			if (employeeInfoEditNewForm.getDateOfBirth() != null
					&& !employeeInfoEditNewForm.getDateOfBirth().isEmpty()) {
				employee.setDob(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfBirth()));
			}
			
			
			
			if (employeeInfoEditNewForm.getEmail() != null
					&& !employeeInfoEditNewForm.getEmail().isEmpty()) {
				employee.setEmail(employeeInfoEditNewForm.getEmail());
			}
			if (employeeInfoEditNewForm.getTeachingStaff() != null
					&& !employeeInfoEditNewForm.getTeachingStaff().isEmpty()) {
				String Value = employeeInfoEditNewForm.getTeachingStaff();
				if (Value.equals("1"))
					employee.setTeachingStaff(true);
				else
					employee.setTeachingStaff(false);
			}
			
			if (String.valueOf(employeeInfoEditNewForm.getCurrentExpYears()) != null
					&& !String.valueOf(employeeInfoEditNewForm.getCurrentExpYears()).isEmpty()) {
				employee
						.setTotalExpYear(String.valueOf(employeeInfoEditNewForm.getCurrentExpYears()));
			}
			if (String.valueOf(employeeInfoEditNewForm.getCurrentExpMonths()) != null
					&& !String.valueOf(employeeInfoEditNewForm.getCurrentExpMonths()).isEmpty()) {
				employee.setTotalExpMonths(String.valueOf(employeeInfoEditNewForm.getCurrentExpMonths()));
			}
			
			
			if (employeeInfoEditNewForm.getOfficialEmail() != null
					&& !employeeInfoEditNewForm.getOfficialEmail().isEmpty()) {
				employee.setWorkEmail(employeeInfoEditNewForm
						.getOfficialEmail());
			}
			if (employeeInfoEditNewForm.getExpYears() != null
					&& !employeeInfoEditNewForm.getExpYears().isEmpty()) {
				employee.setRelevantExpYears(employeeInfoEditNewForm.getExpYears());
			}
			if (employeeInfoEditNewForm.getExpMonths() != null
					&& !employeeInfoEditNewForm.getExpMonths().isEmpty()) {
				employee.setRelevantExpMonths(employeeInfoEditNewForm.getExpMonths());
			}
			
			
			if (employeeInfoEditNewForm.getOfficialEmail() != null
					&& !employeeInfoEditNewForm.getOfficialEmail().isEmpty()) {
				employee.setWorkEmail(employeeInfoEditNewForm
						.getOfficialEmail());
			}
			if (employeeInfoEditNewForm.getMobileNo1() != null
					&& !employeeInfoEditNewForm.getMobileNo1().isEmpty()) {
				employee.setCurrentAddressMobile1(employeeInfoEditNewForm.getMobileNo1());
			}
			
			if (employeeInfoEditNewForm.getMaritialStatus() != null
					&& !employeeInfoEditNewForm.getMaritialStatus().isEmpty()) {
				employee.setMaritalStatus(employeeInfoEditNewForm
						.getMaritialStatus());
			}
			
			if (employeeInfoEditNewForm.getBloodGroup() != null
					&& !employeeInfoEditNewForm.getBloodGroup().isEmpty()) {
				employee
						.setBloodGroup(employeeInfoEditNewForm.getBloodGroup());
			}
			
			
			if (employeeInfoEditNewForm.getEmContactName() != null
					&& !employeeInfoEditNewForm.getEmContactName().isEmpty()) {
				employee.setEmergencyContName(employeeInfoEditNewForm
						.getEmContactName());
			}
			if (employeeInfoEditNewForm.getOfficialEmail() != null
					&& !employeeInfoEditNewForm.getOfficialEmail().isEmpty()) {
				employee.setWorkEmail(employeeInfoEditNewForm
						.getOfficialEmail());
			}
			
			if (employeeInfoEditNewForm.getEmContactWorkTel() != null
					&& !employeeInfoEditNewForm.getEmContactWorkTel()
							.isEmpty()) {
				employee.setEmergencyWorkTelephone(employeeInfoEditNewForm
						.getEmContactWorkTel());
			}
			if (employeeInfoEditNewForm.getEmContactHomeTel() != null
					&& !employeeInfoEditNewForm.getEmContactHomeTel()
							.isEmpty()) {
				employee.setEmergencyHomeTelephone(employeeInfoEditNewForm
						.getEmContactHomeTel());
			}
			if (employeeInfoEditNewForm.getEmContactMobile() != null
					&& !employeeInfoEditNewForm.getEmContactMobile().isEmpty()) {
				employee.setEmergencyMobile(employeeInfoEditNewForm
						.getEmContactMobile());
			}
			if (employeeInfoEditNewForm.getEmContactAddress() != null
					&& !employeeInfoEditNewForm.getEmContactAddress().isEmpty()) {
				employee.setEmContactAddress(employeeInfoEditNewForm
						.getEmContactAddress());
			}
			
			if (employeeInfoEditNewForm.getReligionId() != null
					&& !employeeInfoEditNewForm.getReligionId().isEmpty()) {
				Religion empReligion = new Religion();
				empReligion.setId(Integer.parseInt(employeeInfoEditNewForm
						.getReligionId()));
				employee.setReligionId(empReligion);
			}
			if (employeeInfoEditNewForm.getOtherInfo() != null
					&& !employeeInfoEditNewForm.getOtherInfo().isEmpty()) {
				employee.setOtherInfo(employeeInfoEditNewForm.getOtherInfo());
			}
			if (employeeInfoEditNewForm.getuId() != null
					&& !employeeInfoEditNewForm.getuId().isEmpty()) {
				employee.setUid(employeeInfoEditNewForm.getuId());
			}
			if (employeeInfoEditNewForm.getSameAddress() != null
					&& !employeeInfoEditNewForm.getSameAddress().isEmpty()) {
				String Value = employeeInfoEditNewForm.getSameAddress();
				if (Value.equals("true"))
					employee.setIsSameAddress(true);
				else
					employee.setIsSameAddress(false);
			}
			if (employeeInfoEditNewForm.getCurrentAddressLine1() != null
					&& !employeeInfoEditNewForm.getCurrentAddressLine1()
							.isEmpty()) {
				employee.setCommunicationAddressLine1(employeeInfoEditNewForm
						.getCurrentAddressLine1());
			}

			if (employeeInfoEditNewForm.getCurrentAddressLine2() != null
					&& !employeeInfoEditNewForm.getCurrentAddressLine2()
							.isEmpty()) {
				employee.setCommunicationAddressLine2(employeeInfoEditNewForm
						.getCurrentAddressLine2());
			}

			if (employeeInfoEditNewForm.getCurrentZipCode() != null
					&& !employeeInfoEditNewForm.getCurrentZipCode().isEmpty()) {
				employee.setCommunicationAddressZip(employeeInfoEditNewForm
						.getCurrentZipCode());
			}

			if (employeeInfoEditNewForm.getCurrentCountryId() != null
					&& !employeeInfoEditNewForm.getCurrentCountryId()
							.isEmpty()) {
				Country currentCountry = new Country();
				currentCountry.setId(Integer.parseInt(employeeInfoEditNewForm
						.getCurrentCountryId()));
				employee
						.setCountryByCommunicationAddressCountryId(currentCountry);
			}

			if (employeeInfoEditNewForm.getCurrentState() != null
					&& !employeeInfoEditNewForm.getCurrentState().isEmpty()) {
				if (employeeInfoEditNewForm.getCurrentState()
						.equalsIgnoreCase("other")) {
					if (employeeInfoEditNewForm.getOtherCurrentState() != null
							&& !employeeInfoEditNewForm.getOtherCurrentState()
									.isEmpty()) {
						employee
								.setCommunicationAddressStateOthers(employeeInfoEditNewForm
										.getOtherCurrentState());
					}
				} else {
					State currentState = new State();
					currentState.setId(Integer
							.parseInt(employeeInfoEditNewForm
									.getCurrentState()));
					employee
							.setStateByCommunicationAddressStateId(currentState);
				}
			}

			if (employeeInfoEditNewForm.getCurrentCity() != null
					&& !employeeInfoEditNewForm.getCurrentCity().isEmpty()) {
				employee.setCommunicationAddressCity(employeeInfoEditNewForm
						.getCurrentCity());
			}

			if (employeeInfoEditNewForm.getSameAddress().equalsIgnoreCase(
					"true")) {
				if (employeeInfoEditNewForm.getCurrentAddressLine1() != null
						&& !employeeInfoEditNewForm.getCurrentAddressLine1()
								.isEmpty()) {
					employee.setPermanentAddressLine1(employeeInfoEditNewForm
							.getCurrentAddressLine1());
				}

				if (employeeInfoEditNewForm.getCurrentAddressLine2() != null
						&& !employeeInfoEditNewForm.getCurrentAddressLine2()
								.isEmpty()) {
					employee.setPermanentAddressLine2(employeeInfoEditNewForm
							.getCurrentAddressLine2());
				}

				if (employeeInfoEditNewForm.getCurrentZipCode() != null
						&& !employeeInfoEditNewForm.getCurrentZipCode()
								.isEmpty()) {
					employee.setPermanentAddressZip(employeeInfoEditNewForm
							.getCurrentZipCode());
				}

				if (employeeInfoEditNewForm.getCurrentCountryId() != null
						&& !employeeInfoEditNewForm.getCurrentCountryId()
								.isEmpty()) {
					Country currentCountry = new Country();
					currentCountry.setId(Integer
							.parseInt(employeeInfoEditNewForm
									.getCurrentCountryId()));
					employee
							.setCountryByPermanentAddressCountryId(currentCountry);
				}

				if (employeeInfoEditNewForm.getCurrentState() != null
						&& !employeeInfoEditNewForm.getCurrentState()
								.isEmpty()) {
					if (employeeInfoEditNewForm.getCurrentState()
							.equalsIgnoreCase("other")) {
						if (employeeInfoEditNewForm.getOtherCurrentState() != null
								&& !employeeInfoEditNewForm
										.getOtherCurrentState().isEmpty()) {
							employee
									.setPermanentAddressStateOthers(employeeInfoEditNewForm
											.getOtherCurrentState());
						}
					} else {
						State currentState = new State();
						currentState.setId(Integer
								.parseInt(employeeInfoEditNewForm
										.getCurrentState()));
						employee
								.setStateByPermanentAddressStateId(currentState);
					}
				}

				if (employeeInfoEditNewForm.getCurrentCity() != null
						&& !employeeInfoEditNewForm.getCurrentCity().isEmpty()) {
					employee.setPermanentAddressCity(employeeInfoEditNewForm
							.getCurrentCity());
				}

			} else {
				if (employeeInfoEditNewForm.getAddressLine1() != null
						&& !employeeInfoEditNewForm.getAddressLine1()
								.isEmpty()) {
					employee.setPermanentAddressLine1(employeeInfoEditNewForm
							.getAddressLine1());
				}

				if (employeeInfoEditNewForm.getAddressLine2() != null
						&& !employeeInfoEditNewForm.getAddressLine2()
								.isEmpty()) {
					employee.setPermanentAddressLine2(employeeInfoEditNewForm
							.getAddressLine2());
				}

				if (employeeInfoEditNewForm.getPermanentZipCode() != null
						&& !employeeInfoEditNewForm.getPermanentZipCode()
								.isEmpty()) {
					employee.setPermanentAddressZip(employeeInfoEditNewForm
							.getPermanentZipCode());
				}

				if (employeeInfoEditNewForm.getCountryId() != null
						&& !employeeInfoEditNewForm.getCountryId().isEmpty()) {
					Country country = new Country();
					country.setId(Integer.parseInt(employeeInfoEditNewForm
							.getCountryId()));
					employee.setCountryByPermanentAddressCountryId(country);
				}

				if (employeeInfoEditNewForm.getStateId() != null
						&& !employeeInfoEditNewForm.getStateId().isEmpty()) {
					if (employeeInfoEditNewForm.getStateId().equalsIgnoreCase(
							"other")) {
						if (employeeInfoEditNewForm.getOtherPermanentState() != null
								&& !employeeInfoEditNewForm
										.getOtherPermanentState().isEmpty()) {
							employee
									.setPermanentAddressStateOthers(employeeInfoEditNewForm
											.getOtherPermanentState());
						}
					} else {
						State state = new State();
						state.setId(Integer.parseInt(employeeInfoEditNewForm
								.getStateId()));
						employee.setStateByPermanentAddressStateId(state);
					}
				}

				if (employeeInfoEditNewForm.getCity() != null
						&& !employeeInfoEditNewForm.getCity().isEmpty()) {
					employee.setPermanentAddressCity(employeeInfoEditNewForm
							.getCity());
				}
			}
			if (employeeInfoEditNewForm.getNationalityId() != null
					&& !employeeInfoEditNewForm.getNationalityId().isEmpty()) {
				Nationality nationality = new Nationality();
				nationality.setId(Integer.parseInt(employeeInfoEditNewForm
						.getNationalityId()));
				employee.setNationality(nationality);
			}

			if (employeeInfoEditNewForm.getGender() != null
					&& !employeeInfoEditNewForm.getGender().isEmpty()) {
				employee.setGender(employeeInfoEditNewForm.getGender());
			}
			
			if (employeeInfoEditNewForm.getDateOfBirth() != null
					&& !employeeInfoEditNewForm.getDateOfBirth().isEmpty()) {
				employee.setDob(CommonUtil
						.ConvertStringToDate(employeeInfoEditNewForm
								.getDateOfBirth()));
			}
			
			
			
			if (employeeInfoEditNewForm.getHomePhone1() != null
					&& !employeeInfoEditNewForm.getHomePhone1().isEmpty()) {
				employee
						.setCurrentAddressHomeTelephone1(employeeInfoEditNewForm
								.getHomePhone1());
			}

			if (employeeInfoEditNewForm.getHomePhone2() != null
					&& !employeeInfoEditNewForm.getHomePhone2().isEmpty()) {
				employee
						.setCurrentAddressHomeTelephone2(employeeInfoEditNewForm
								.getHomePhone2());
			}

			if (employeeInfoEditNewForm.getHomePhone3() != null
					&& !employeeInfoEditNewForm.getHomePhone3().isEmpty()) {
				employee
						.setCurrentAddressHomeTelephone3(employeeInfoEditNewForm
								.getHomePhone3());
			}
			if (employeeInfoEditNewForm.getWorkPhNo1() != null
					&& !employeeInfoEditNewForm.getWorkPhNo1().isEmpty()) {
				employee
						.setCurrentAddressWorkTelephone1(employeeInfoEditNewForm
								.getWorkPhNo1());
			}

			if (employeeInfoEditNewForm.getWorkPhNo2() != null
					&& !employeeInfoEditNewForm.getWorkPhNo2().isEmpty()) {
				employee
						.setCurrentAddressWorkTelephone2(employeeInfoEditNewForm
								.getWorkPhNo2());
			}

			if (employeeInfoEditNewForm.getWorkPhNo3() != null
					&& !employeeInfoEditNewForm.getWorkPhNo3().isEmpty()) {
				employee
						.setCurrentAddressWorkTelephone3(employeeInfoEditNewForm
								.getWorkPhNo3());
			}
			if (employeeInfoEditNewForm.getMobileNo1() != null
					&& !employeeInfoEditNewForm.getMobileNo1().isEmpty()) {
				employee.setCurrentAddressMobile1(employeeInfoEditNewForm
						.getMobileNo1());
			}
			
			if (employeeInfoEditNewForm.getNoOfPublicationsRefered() != null
					&& !employeeInfoEditNewForm.getNoOfPublicationsRefered()
							.isEmpty()) {
				employee.setNoOfPublicationsRefered(employeeInfoEditNewForm
						.getNoOfPublicationsRefered());
			}

			if (employeeInfoEditNewForm.getNoOfPublicationsNotRefered() != null
					&& !employeeInfoEditNewForm
							.getNoOfPublicationsNotRefered().isEmpty()) {
				employee.setNoOfPublicationsNotRefered(employeeInfoEditNewForm
						.getNoOfPublicationsNotRefered());
			}

			if (employeeInfoEditNewForm.getBooks() != null
					&& !employeeInfoEditNewForm.getBooks().isEmpty()) {
				employee.setBooks(employeeInfoEditNewForm.getBooks());
			}
			
		/*	if (employeeInfoEditForm.getBasicPay() != null
					&& !employeeInfoEditForm.getBasicPay().isEmpty()) {
				employee.setBasicPay(employeeInfoEditForm.getBasicPay());
			}*/

			
			if (employeeInfoEditNewForm.getWorkLocationId() != null
					&& !employeeInfoEditNewForm.getWorkLocationId().isEmpty()) {
				EmployeeWorkLocationBO empworkLoc = new EmployeeWorkLocationBO();
				empworkLoc.setId(Integer.parseInt(employeeInfoEditNewForm
						.getWorkLocationId()));
				employee.setWorkLocationId(empworkLoc);
			}
			if (employeeInfoEditNewForm.getStreamId() != null
					&& !employeeInfoEditNewForm.getStreamId().isEmpty()) {
				EmployeeStreamBO empStream = new EmployeeStreamBO();
				empStream.setId(Integer.parseInt(employeeInfoEditNewForm
						.getStreamId()));
				employee.setStreamId(empStream);
			}
			
			if (employeeInfoEditNewForm.getEmpQualificationLevelId() != null
					&& !employeeInfoEditNewForm.getEmpQualificationLevelId()
							.isEmpty()) {
				QualificationLevelBO empQualificationLevel = new QualificationLevelBO();
				empQualificationLevel.setId(Integer
						.parseInt(employeeInfoEditNewForm
								.getEmpQualificationLevelId()));
				employee.setEmpQualificationLevel(empQualificationLevel);
			}
			
			if (employeeInfoEditNewForm.getReservationCategory() != null
					&& !employeeInfoEditNewForm.getReservationCategory()
							.isEmpty()) {
				employee.setReservationCategory(employeeInfoEditNewForm
						.getReservationCategory());
			}
			
			
			
			if (employeeInfoEditNewForm.getCode() != null
					&& !employeeInfoEditNewForm.getCode().isEmpty()) {
				employee.setCode(employeeInfoEditNewForm.getCode());
			}
			
			
			if (employeeInfoEditNewForm.getSubjectSpecialisation() != null
					&& !employeeInfoEditNewForm.getSubjectSpecialisation().isEmpty()) {
				employee.setSubjectSpecilization(employeeInfoEditNewForm.getSubjectSpecialisation());
			}
			
			if (employeeInfoEditNewForm.getStaffId() != null
					&& !employeeInfoEditNewForm.getStaffId().isEmpty()) {
				employee.setStaffId(employeeInfoEditNewForm.getStaffId());
			}
			
			
			if (employeeInfoEditNewForm.getActive() != null
					&& !employeeInfoEditNewForm.getActive()
							.isEmpty()) {
				String Value = employeeInfoEditNewForm
						.getActive();
				if (Value.equals("1"))
					employee.setActive(true);
				else
					employee.setActive(false);
			}
			
			if (employeeInfoEditNewForm.getCurrentlyWorking() != null
					&& !employeeInfoEditNewForm.getCurrentlyWorking()
							.isEmpty()
					&& employeeInfoEditNewForm.getCurrentlyWorking()
							.equalsIgnoreCase("YES")) {
				employee.setCurrentlyWorking(true);
			}
			
			employee.setIsActive(true);
			employee.setCreatedBy(employeeInfoEditNewForm.getUserId());
			employee.setCreatedDate(new Date());
			employee.setLastModifiedDate(new Date());
			employee.setModifiedBy(employeeInfoEditNewForm.getUserId());
		}

		return employee;
	}
	
}
