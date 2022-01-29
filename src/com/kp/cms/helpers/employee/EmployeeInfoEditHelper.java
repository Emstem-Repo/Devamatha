package com.kp.cms.helpers.employee;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpDependents;
import com.kp.cms.bo.admin.EmpImmigration;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admin.PfGratuityNominees;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EditMyProfile;
import com.kp.cms.bo.employee.EmpEducationalDetails;
import com.kp.cms.bo.employee.EmpFeeConcession;
import com.kp.cms.bo.employee.EmpFinancial;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpIncentives;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpLoan;
import com.kp.cms.bo.employee.EmpPayAllowanceDetails;
import com.kp.cms.bo.employee.EmpPreviousExperience;
import com.kp.cms.bo.employee.EmpRemarks;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.PfGratuityNomineesTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpFeeConcessionTO;
import com.kp.cms.to.employee.EmpFinancialTO;
import com.kp.cms.to.employee.EmpImagesTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpRemarksTO;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EmployeeInfoEditComparator;
import com.kp.cms.utilities.PayAllowance;
public class EmployeeInfoEditHelper {

	private static volatile EmployeeInfoEditHelper instance = null;

	/**
		 * 
		 */
	private EmployeeInfoEditHelper() {

	}

	/**
	 * @return
	 */
	public static EmployeeInfoEditHelper getInstance() {
		if (instance == null) {
			instance = new EmployeeInfoEditHelper();
		}
		return instance;
	}

	IEmployeeInfoEditTransaction txn = new EmployeeInfoEditTransactionImpl();

	/**
	 * @param employeeInfoEditForm
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ApplicationException
	 */
	public Employee convertFormToBo(EmployeeInfoEditForm employeeInfoEditForm) throws Exception{
		Employee employee = new Employee();
		if (employeeInfoEditForm.getEmployeeId() != null
				&& !employeeInfoEditForm.getEmployeeId().isEmpty() && Integer.parseInt(employeeInfoEditForm.getEmployeeId())>0) {
			employee.setId(Integer.parseInt(employeeInfoEditForm
					.getEmployeeId()));

			// Creating EmpLoan Object and setting into EMployee Object
			Set<EmpLoan> empLoan = getEmpLoanBoObjects(employeeInfoEditForm);
			employee.setEmpLoan(empLoan);

			Set<EmpFinancial> empFinancial = getEmpFinancialBoObjects(employeeInfoEditForm);
			employee.setEmpFinancial(empFinancial);

			Set<EmpFeeConcession> empFeeConcession = getEmpFeeConcessionBoObjects(employeeInfoEditForm);
			employee.setEmpFeeConcession(empFeeConcession);

			Set<EmpIncentives> empIncentives = getEmpIncentivesBoObjects(employeeInfoEditForm);
			employee.setEmpIncentives(empIncentives);

			Set<EmpRemarks> empRemarks = getEmpRemarksBoObjects(employeeInfoEditForm);
			employee.setEmpRemarks(empRemarks);

			Set<EmpImmigration> empImmigrations = getEmpImmigrationBoObjects(employeeInfoEditForm);
			employee.setEmpImmigrations(empImmigrations);

			Set<EmpDependents> empDependentses = getEmpDependantsBoObjects(employeeInfoEditForm);
			employee.setEmpDependentses(empDependentses);
			
			Set<PfGratuityNominees> PfGratuityNominees = getEmpPfGratuityNomineesObjects(employeeInfoEditForm);
			employee.setPfGratuityNominees(PfGratuityNominees);

			Set<EmpAcheivement> empAcheivements = getEmpAchievementBoObjects(employeeInfoEditForm);
			employee.setEmpAcheivements(empAcheivements);

			Set<EmpPayAllowanceDetails> empPayAllowance = getPayAllowanceBoObjects(employeeInfoEditForm);
			employee.setEmpPayAllowance(empPayAllowance);

			Set<EmpLeave> empLeave = getLeavesBoObjects(employeeInfoEditForm);
			employee.setEmpLeaves(empLeave);
			
			Set<EmpImages> empImages = getEmpImagesBoObjects(employeeInfoEditForm);
			employee.setEmpImages(empImages);

			Set<EmpPreviousExperience> previousSet = new HashSet<EmpPreviousExperience>();
			Set<EmpEducationalDetails> educationalDeatialSet = new HashSet<EmpEducationalDetails>();
			try {

				if (employeeInfoEditForm.getEmployeeInfoTONew() != null) {
					if (employeeInfoEditForm.getEmployeeInfoTONew()
							.getExperiences() != null) {
						List<EmpPreviousOrgTo> list = employeeInfoEditForm
								.getEmployeeInfoTONew().getExperiences();
						if (list != null) {
							Iterator<EmpPreviousOrgTo> iterator = list
									.iterator();
							while (iterator.hasNext()) {
								EmpPreviousOrgTo empPreviousOrgTo = iterator
										.next();
								EmpPreviousExperience empPreviousExp = new EmpPreviousExperience();
								if (empPreviousOrgTo != null) {
									if ((empPreviousOrgTo
											.getCurrentOrganisation() != null && !empPreviousOrgTo
											.getCurrentOrganisation().isEmpty())
											|| (empPreviousOrgTo
													.getIndustryExpYears() != null && !empPreviousOrgTo
													.getIndustryExpYears()
													.isEmpty())
											|| (empPreviousOrgTo
													.getIndustryExpMonths() != null && !empPreviousOrgTo
													.getIndustryExpMonths()
													.isEmpty())
											|| (empPreviousOrgTo
													.getCurrentDesignation() != null && !empPreviousOrgTo
													.getCurrentDesignation()
													.isEmpty())) {

										if (empPreviousOrgTo.getId() > 0) {
											empPreviousExp
													.setId(empPreviousOrgTo
															.getId());
										}
										if (empPreviousOrgTo
												.getIndustryExpYears() != null
												&& !empPreviousOrgTo
														.getIndustryExpYears()
														.isEmpty()) {
											empPreviousExp
													.setExpYears(Integer
															.parseInt(empPreviousOrgTo
																	.getIndustryExpYears()));
										}
										if (empPreviousOrgTo
												.getIndustryExpMonths() != null
												&& !empPreviousOrgTo
														.getIndustryExpMonths()
														.isEmpty()) {
											empPreviousExp
													.setExpMonths(Integer
															.parseInt(empPreviousOrgTo
																	.getIndustryExpMonths()));
										}

										if (empPreviousOrgTo
												.getCurrentDesignation() != null
												&& !empPreviousOrgTo
														.getCurrentDesignation()
														.isEmpty()) {
											empPreviousExp
													.setEmpDesignation(empPreviousOrgTo
															.getCurrentDesignation());
										}

										if (empPreviousOrgTo
												.getCurrentOrganisation() != null
												&& !empPreviousOrgTo
														.getCurrentOrganisation()
														.isEmpty()) {
											empPreviousExp
													.setEmpOrganization(empPreviousOrgTo
															.getCurrentOrganisation());
										}
										/* code added by sudhir */
										if(empPreviousOrgTo.getIndustryFromDate()!=null
												&& !empPreviousOrgTo.getIndustryFromDate().isEmpty()){
											empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryFromDate()));
										}
										if(empPreviousOrgTo.getIndustryToDate()!=null
												&& !empPreviousOrgTo.getIndustryToDate().isEmpty()){
											empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryToDate()));
										}
										/* code added by sudhir */
										Employee emp = new Employee();
										emp.setId(Integer
												.parseInt(employeeInfoEditForm
														.getEmployeeId()));
										empPreviousExp.setEmployee(emp);
										empPreviousExp
												.setIndustryExperience(true);
										empPreviousExp.setActive(true);
										empPreviousExp
												.setCreatedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp
												.setCreatedDate(new Date());
										empPreviousExp
												.setModifiedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp
												.setModifiedDate(new Date());
										previousSet.add(empPreviousExp);
									}
								}
							}
						}
					}

					if (employeeInfoEditForm.getEmployeeInfoTONew()
							.getTeachingExperience() != null) {
						List<EmpPreviousOrgTo> list = employeeInfoEditForm
								.getEmployeeInfoTONew().getTeachingExperience();
						if (list != null) {
							Iterator<EmpPreviousOrgTo> iterator = list
									.iterator();
							while (iterator.hasNext()) {
								EmpPreviousOrgTo empPreviousOrgTo = iterator
										.next();
								EmpPreviousExperience empPreviousExp = new EmpPreviousExperience();
								if (empPreviousOrgTo != null) {
									if ((empPreviousOrgTo
											.getCurrentTeachingOrganisation() != null && !empPreviousOrgTo
											.getCurrentTeachingOrganisation()
											.isEmpty())
											|| (empPreviousOrgTo
													.getTeachingExpYears() != null && !empPreviousOrgTo
													.getTeachingExpYears()
													.isEmpty())
											|| (empPreviousOrgTo
													.getTeachingExpMonths() != null && !empPreviousOrgTo
													.getTeachingExpMonths()
													.isEmpty())
											|| (empPreviousOrgTo
													.getCurrentTeachnigDesignation() != null && !empPreviousOrgTo
													.getCurrentTeachnigDesignation()
													.isEmpty())) {
										if (empPreviousOrgTo.getId() > 0) {
											empPreviousExp
													.setId(empPreviousOrgTo
															.getId());
										}

										if (empPreviousOrgTo
												.getTeachingExpYears() != null
												&& !empPreviousOrgTo
														.getTeachingExpYears()
														.isEmpty()) {
											empPreviousExp
													.setExpYears(Integer
															.parseInt(empPreviousOrgTo
																	.getTeachingExpYears()));
										}

										if (empPreviousOrgTo
												.getTeachingExpMonths() != null
												&& !empPreviousOrgTo
														.getTeachingExpMonths()
														.isEmpty()) {
											empPreviousExp
													.setExpMonths(Integer
															.parseInt(empPreviousOrgTo
																	.getTeachingExpMonths()));
										}

										if (empPreviousOrgTo
												.getCurrentTeachnigDesignation() != null
												&& !empPreviousOrgTo
														.getCurrentTeachnigDesignation()
														.isEmpty()) {
											empPreviousExp
													.setEmpDesignation(empPreviousOrgTo
															.getCurrentTeachnigDesignation());
										}

										if (empPreviousOrgTo
												.getCurrentTeachingOrganisation() != null
												&& !empPreviousOrgTo
														.getCurrentTeachingOrganisation()
														.isEmpty()) {
											empPreviousExp
													.setEmpOrganization(empPreviousOrgTo
															.getCurrentTeachingOrganisation());
										}
										/* code added by sudhir */
										if(empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()){
											empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingFromDate()));
										}
										if(empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()){
											empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingToDate()));
										}
										/*-------------------------*/
										Employee emp = new Employee();
										emp.setId(Integer
												.parseInt(employeeInfoEditForm
														.getEmployeeId()));
										empPreviousExp.setEmployee(emp);
										empPreviousExp
												.setTeachingExperience(true);
										empPreviousExp.setActive(true);
										empPreviousExp
												.setCreatedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp
												.setCreatedDate(new Date());
										empPreviousExp
												.setModifiedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp
												.setModifiedDate(new Date());
										previousSet.add(empPreviousExp);
									}
								}
							}
						}

					}

				}
				if (employeeInfoEditForm.getCurrentlyWorking() != null
						&& !employeeInfoEditForm.getCurrentlyWorking()
								.isEmpty()
						&& employeeInfoEditForm.getCurrentlyWorking()
								.equalsIgnoreCase("YES")) {
					employee.setCurrentlyWorking(true);
					if (employeeInfoEditForm.getDesignation() != null
							&& !employeeInfoEditForm.getDesignation().isEmpty()) {
						employee.setDesignationName(employeeInfoEditForm
								.getDesignation());
					}

					if (employeeInfoEditForm.getOrgAddress() != null
							&& !employeeInfoEditForm.getOrgAddress().isEmpty()) {
						employee.setOrganistionName(employeeInfoEditForm
								.getOrgAddress());
					}

				} else {
					employee.setCurrentlyWorking(false);
					employee.setDesignationName(null);
					employee.setOrganistionName(null);
				}
				employee.setPreviousExpSet(previousSet);

				if (employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpQualificationFixedTo() != null) {
					List<EmpQualificationLevelTo> qualificationFixedTo = employeeInfoEditForm
							.getEmployeeInfoTONew()
							.getEmpQualificationFixedTo();
					Iterator<EmpQualificationLevelTo> iterator = qualificationFixedTo
							.iterator();
					while (iterator.hasNext()) {
						EmpQualificationLevelTo qualificationFixed = iterator
								.next();
						EmpEducationalDetails educationalDeatails = null;
						if (qualificationFixed != null) {
							if ((qualificationFixed.getInstitute() != null && !qualificationFixed
									.getInstitute().isEmpty())
									|| (qualificationFixed.getCourse() != null && !qualificationFixed
											.getCourse().isEmpty())
									|| (qualificationFixed.getSpecialization() != null && !qualificationFixed
											.getSpecialization().isEmpty())
									|| (qualificationFixed.getGrade() != null && !qualificationFixed
											.getGrade().isEmpty())) {
								educationalDeatails = new EmpEducationalDetails();

								if (qualificationFixed.getEducationDetailsID() > 0) {
									educationalDeatails
											.setId(qualificationFixed
													.getEducationDetailsID());
								}
								if (qualificationFixed.getEducationId() != null
										&& !qualificationFixed.getEducationId()
												.isEmpty()) {
									QualificationLevelBO level = new QualificationLevelBO();
									level.setId(Integer
											.parseInt(qualificationFixed
													.getEducationId()));
									educationalDeatails
											.setEmpQualificationLevel(level);
								}

								if (qualificationFixed.getCourse() != null
										&& !qualificationFixed.getCourse()
												.isEmpty()) {
									educationalDeatails
											.setCourse(qualificationFixed
													.getCourse());
								}

								if (qualificationFixed.getSpecialization() != null
										&& !qualificationFixed
												.getSpecialization().isEmpty()) {
									educationalDeatails
											.setSpecialization(qualificationFixed
													.getSpecialization());
								}

								if (qualificationFixed.getYearOfComp() != null
										&& !qualificationFixed.getYearOfComp().trim()
												.isEmpty()) {
									educationalDeatails
											.setYearOfCompletion(Integer
													.valueOf(qualificationFixed
															.getYearOfComp()));
								}

								if (qualificationFixed.getGrade() != null
										&& !qualificationFixed.getGrade()
												.isEmpty()) {
									educationalDeatails
											.setGrade(qualificationFixed
													.getGrade());
								}

								if (qualificationFixed.getInstitute() != null
										&& !qualificationFixed.getInstitute()
												.isEmpty()) {
									educationalDeatails
											.setInstitute(qualificationFixed
													.getInstitute());
								}
								Employee emp = new Employee();
								emp.setId(Integer.parseInt(employeeInfoEditForm
										.getEmployeeId()));
								educationalDeatails.setEmployee(emp);
								educationalDeatails.setActive(true);
								educationalDeatails
										.setCreatedBy(employeeInfoEditForm
												.getUserId());
								educationalDeatails.setCreatedDate(new Date());
								educationalDeatails
										.setModifiedBy(employeeInfoEditForm
												.getUserId());
								educationalDeatails.setModifiedDate(new Date());
								educationalDeatialSet.add(educationalDeatails);
							}
						}
					}
				}

				if (employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpQualificationLevelTos() != null) {
					Iterator<EmpQualificationLevelTo> iterator = employeeInfoEditForm
							.getEmployeeInfoTONew()
							.getEmpQualificationLevelTos().iterator();
					while (iterator.hasNext()) {
						EmpQualificationLevelTo levelTo = iterator.next();
						EmpEducationalDetails educationalDetails = null;
						if (levelTo != null) {
							if ((levelTo.getInstitute() != null && !levelTo
									.getInstitute().isEmpty())
									|| (levelTo.getCourse() != null && !levelTo
											.getCourse().isEmpty())
									|| (levelTo.getSpecialization() != null && !levelTo
											.getSpecialization().isEmpty())
									|| (levelTo.getGrade() != null && !levelTo
											.getGrade().isEmpty())) {
								educationalDetails = new EmpEducationalDetails();
								if (levelTo.getEducationId() != null
										&& !levelTo.getEducationId().isEmpty()) {
									QualificationLevelBO level = new QualificationLevelBO();
									level.setId(Integer.parseInt(levelTo
											.getEducationId()));
									educationalDetails
											.setEmpQualificationLevel(level);
								}

								if (levelTo.getEducationDetailsID() > 0) {
									educationalDetails.setId(levelTo
											.getEducationDetailsID());
								}
								if (levelTo.getCourse() != null
										&& !levelTo.getCourse().isEmpty()) {
									educationalDetails.setCourse(levelTo
											.getCourse());
								}

								if (levelTo.getSpecialization() != null
										&& !levelTo.getSpecialization()
												.isEmpty()) {
									educationalDetails
											.setSpecialization(levelTo
													.getSpecialization());
								}

								if (levelTo.getGrade() != null
										&& !levelTo.getGrade().isEmpty()) {
									educationalDetails.setGrade(levelTo
											.getGrade());
								}

								if (levelTo.getYear() != null
										&& !levelTo.getYear().trim().isEmpty()) {
									educationalDetails
											.setYearOfCompletion(Integer
													.parseInt(levelTo.getYear()));
								}

								if (levelTo.getInstitute() != null
										&& !levelTo.getInstitute().isEmpty()) {
									educationalDetails.setInstitute(levelTo
											.getInstitute());
								}

								Employee emp = new Employee();
								emp.setId(Integer.parseInt(employeeInfoEditForm
										.getEmployeeId()));
								educationalDetails.setEmployee(emp);
								educationalDetails.setActive(true);
								educationalDetails
										.setCreatedBy(employeeInfoEditForm
												.getUserId());
								educationalDetails.setCreatedDate(new Date());
								educationalDetails
										.setModifiedBy(employeeInfoEditForm
												.getUserId());
								educationalDetails.setModifiedDate(new Date());
								educationalDeatialSet.add(educationalDetails);
							}
						}
					}
				}

				employee.setEducationalDetailsSet(educationalDeatialSet);

				if (employeeInfoEditForm.getDesignationPfId() != null
						&& !employeeInfoEditForm.getDesignationPfId().isEmpty()) {
					Designation designation = new Designation();
					designation.setId(Integer.parseInt(employeeInfoEditForm
							.getDesignationPfId()));
					employee.setDesignation(designation);
				}
				if (employeeInfoEditForm.getAlbumDesignation() != null
						&& !employeeInfoEditForm.getAlbumDesignation().isEmpty()) {
					Designation designation = new Designation();
					designation.setId(Integer.parseInt(employeeInfoEditForm
							.getAlbumDesignation()));
					employee.setAlbumDesignation(designation);
				}

				if (employeeInfoEditForm.getDepartmentId() != null
						&& !employeeInfoEditForm.getDepartmentId().isEmpty()) {
					Department department = new Department();
					department.setId(Integer.parseInt(employeeInfoEditForm
							.getDepartmentId()));
					employee.setDepartment(department);
				}

				if (employeeInfoEditForm.getName() != null
						&& !employeeInfoEditForm.getName().isEmpty()) {
					employee.setFirstName(employeeInfoEditForm.getName().toUpperCase());
				}
				
				if(employeeInfoEditForm.getLicGratuityNo()!=null && !employeeInfoEditForm.getLicGratuityNo().isEmpty()){
					employee.setLicGratuityNo(employeeInfoEditForm.getLicGratuityNo());
				}
				if(employeeInfoEditForm.getLicGratuityDate()!=null && !employeeInfoEditForm.getLicGratuityDate().isEmpty()){
					employee.setLicGratuityDate(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getLicGratuityDate()));
				}
				if(employeeInfoEditForm.getNomineePfNo()!=null && !employeeInfoEditForm.getNomineePfNo().isEmpty()){
					employee.setNomineePfNo(employeeInfoEditForm.getNomineePfNo());
				}
				if(employeeInfoEditForm.getNomineePfDate()!=null && !employeeInfoEditForm.getNomineePfDate().isEmpty()){
					employee.setNomineePfDate(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getNomineePfDate()));
				}
				if (employeeInfoEditForm.getuId() != null
						&& !employeeInfoEditForm.getuId().isEmpty()) {
					employee.setUid(employeeInfoEditForm.getuId());
				}
				if (employeeInfoEditForm.getCode() != null
						&& !employeeInfoEditForm.getCode().isEmpty()) {
					employee.setCode(employeeInfoEditForm.getCode());
				}
				if (employeeInfoEditForm.getFingerPrintId() != null
						&& !employeeInfoEditForm.getFingerPrintId().isEmpty()) {
					employee.setFingerPrintId(employeeInfoEditForm
							.getFingerPrintId());
				}
				if (employeeInfoEditForm.getCurrentAddressLine1() != null
						&& !employeeInfoEditForm.getCurrentAddressLine1()
								.isEmpty()) {
					employee.setCommunicationAddressLine1(employeeInfoEditForm
							.getCurrentAddressLine1());
				}

				if (employeeInfoEditForm.getCurrentAddressLine2() != null
						&& !employeeInfoEditForm.getCurrentAddressLine2()
								.isEmpty()) {
					employee.setCommunicationAddressLine2(employeeInfoEditForm
							.getCurrentAddressLine2());
				}

				if (employeeInfoEditForm.getCurrentZipCode() != null
						&& !employeeInfoEditForm.getCurrentZipCode().isEmpty()) {
					employee.setCommunicationAddressZip(employeeInfoEditForm
							.getCurrentZipCode());
				}

				if (employeeInfoEditForm.getCurrentCountryId() != null
						&& !employeeInfoEditForm.getCurrentCountryId()
								.isEmpty()) {
					Country currentCountry = new Country();
					currentCountry.setId(Integer.parseInt(employeeInfoEditForm
							.getCurrentCountryId()));
					employee
							.setCountryByCommunicationAddressCountryId(currentCountry);
				}

				if (employeeInfoEditForm.getCurrentState() != null
						&& !employeeInfoEditForm.getCurrentState().isEmpty()) {
					if (employeeInfoEditForm.getCurrentState()
							.equalsIgnoreCase("other")) {
						if (employeeInfoEditForm.getOtherCurrentState() != null
								&& !employeeInfoEditForm.getOtherCurrentState()
										.isEmpty()) {
							employee
									.setCommunicationAddressStateOthers(employeeInfoEditForm
											.getOtherCurrentState());
						}
					} else {
						State currentState = new State();
						currentState.setId(Integer
								.parseInt(employeeInfoEditForm
										.getCurrentState()));
						employee
								.setStateByCommunicationAddressStateId(currentState);
					}
				}

				if (employeeInfoEditForm.getCurrentCity() != null
						&& !employeeInfoEditForm.getCurrentCity().isEmpty()) {
					employee.setCommunicationAddressCity(employeeInfoEditForm
							.getCurrentCity());
				}

				if (employeeInfoEditForm.getSameAddress().equalsIgnoreCase(
						"true")) {
					if (employeeInfoEditForm.getCurrentAddressLine1() != null
							&& !employeeInfoEditForm.getCurrentAddressLine1()
									.isEmpty()) {
						employee.setPermanentAddressLine1(employeeInfoEditForm
								.getCurrentAddressLine1());
					}

					if (employeeInfoEditForm.getCurrentAddressLine2() != null
							&& !employeeInfoEditForm.getCurrentAddressLine2()
									.isEmpty()) {
						employee.setPermanentAddressLine2(employeeInfoEditForm
								.getCurrentAddressLine2());
					}

					if (employeeInfoEditForm.getCurrentZipCode() != null
							&& !employeeInfoEditForm.getCurrentZipCode()
									.isEmpty()) {
						employee.setPermanentAddressZip(employeeInfoEditForm
								.getCurrentZipCode());
					}

					if (employeeInfoEditForm.getCurrentCountryId() != null
							&& !employeeInfoEditForm.getCurrentCountryId()
									.isEmpty()) {
						Country currentCountry = new Country();
						currentCountry.setId(Integer
								.parseInt(employeeInfoEditForm
										.getCurrentCountryId()));
						employee
								.setCountryByPermanentAddressCountryId(currentCountry);
					}

					if (employeeInfoEditForm.getCurrentState() != null
							&& !employeeInfoEditForm.getCurrentState()
									.isEmpty()) {
						if (employeeInfoEditForm.getCurrentState()
								.equalsIgnoreCase("other")) {
							if (employeeInfoEditForm.getOtherCurrentState() != null
									&& !employeeInfoEditForm
											.getOtherCurrentState().isEmpty()) {
								employee
										.setPermanentAddressStateOthers(employeeInfoEditForm
												.getOtherCurrentState());
							}
						} else {
							State currentState = new State();
							currentState.setId(Integer
									.parseInt(employeeInfoEditForm
											.getCurrentState()));
							employee
									.setStateByPermanentAddressStateId(currentState);
						}
					}

					if (employeeInfoEditForm.getCurrentCity() != null
							&& !employeeInfoEditForm.getCurrentCity().isEmpty()) {
						employee.setPermanentAddressCity(employeeInfoEditForm
								.getCurrentCity());
					}

				} else {
					if (employeeInfoEditForm.getAddressLine1() != null
							&& !employeeInfoEditForm.getAddressLine1()
									.isEmpty()) {
						employee.setPermanentAddressLine1(employeeInfoEditForm
								.getAddressLine1());
					}

					if (employeeInfoEditForm.getAddressLine2() != null
							&& !employeeInfoEditForm.getAddressLine2()
									.isEmpty()) {
						employee.setPermanentAddressLine2(employeeInfoEditForm
								.getAddressLine2());
					}

					if (employeeInfoEditForm.getPermanentZipCode() != null
							&& !employeeInfoEditForm.getPermanentZipCode()
									.isEmpty()) {
						employee.setPermanentAddressZip(employeeInfoEditForm
								.getPermanentZipCode());
					}

					if (employeeInfoEditForm.getCountryId() != null
							&& !employeeInfoEditForm.getCountryId().isEmpty()) {
						Country country = new Country();
						country.setId(Integer.parseInt(employeeInfoEditForm
								.getCountryId()));
						employee.setCountryByPermanentAddressCountryId(country);
					}

					if (employeeInfoEditForm.getStateId() != null
							&& !employeeInfoEditForm.getStateId().isEmpty()) {
						if (employeeInfoEditForm.getStateId().equalsIgnoreCase(
								"other")) {
							if (employeeInfoEditForm.getOtherPermanentState() != null
									&& !employeeInfoEditForm
											.getOtherPermanentState().isEmpty()) {
								employee
										.setPermanentAddressStateOthers(employeeInfoEditForm
												.getOtherPermanentState());
							}
						} else {
							State state = new State();
							state.setId(Integer.parseInt(employeeInfoEditForm
									.getStateId()));
							employee.setStateByPermanentAddressStateId(state);
						}
					}

					if (employeeInfoEditForm.getCity() != null
							&& !employeeInfoEditForm.getCity().isEmpty()) {
						employee.setPermanentAddressCity(employeeInfoEditForm
								.getCity());
					}
				}
				if (employeeInfoEditForm.getNationalityId() != null
						&& !employeeInfoEditForm.getNationalityId().isEmpty()) {
					Nationality nationality = new Nationality();
					nationality.setId(Integer.parseInt(employeeInfoEditForm
							.getNationalityId()));
					employee.setNationality(nationality);
				}

				if (employeeInfoEditForm.getGender() != null
						&& !employeeInfoEditForm.getGender().isEmpty()) {
					employee.setGender(employeeInfoEditForm.getGender());
				}

				if (employeeInfoEditForm.getMaritalStatus() != null
						&& !employeeInfoEditForm.getMaritalStatus().isEmpty()) {
					employee.setMaritalStatus(employeeInfoEditForm
							.getMaritalStatus());
				}
				
				//
				if (employeeInfoEditForm.getEligibilityList() != null && !employeeInfoEditForm.getEligibilityList().isEmpty()) {
					String eligibilityTest="";
				
					Iterator<EligibilityTestTO> itr = employeeInfoEditForm.getEligibilityList().iterator();
					while (itr.hasNext()) {
						EligibilityTestTO to = (EligibilityTestTO) itr.next();
						if (to.getChecked() != null && !to.getChecked().isEmpty())
						{
							if(!eligibilityTest.trim().isEmpty())
								eligibilityTest=eligibilityTest+","+to.getChecked();
							else eligibilityTest=to.getChecked();
							
						  if(to.getChecked().equalsIgnoreCase("OTHER")){
							  if(employeeInfoEditForm.getOtherEligibilityTestValue()!=null && !employeeInfoEditForm.getOtherEligibilityTestValue().trim().isEmpty()){
									employee.setEligibilityTestOther(employeeInfoEditForm.getOtherEligibilityTestValue());
								}
							}
						}
					}
					employee.setEligibilityTest(eligibilityTest);
				}
				
				
				if(employeeInfoEditForm.getIndustryFunctionalArea()!=null && !employeeInfoEditForm.getIndustryFunctionalArea().trim().isEmpty()){
					employee.setIndustryFunctionalArea(employeeInfoEditForm.getIndustryFunctionalArea());
				}
				if (employeeInfoEditForm.getDateOfBirth() != null
						&& !employeeInfoEditForm.getDateOfBirth().isEmpty()) {
					employee.setDob(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfBirth()));
				}
				if (employeeInfoEditForm.getDateOfJoining() != null
						&& !employeeInfoEditForm.getDateOfJoining().isEmpty()) {
					employee.setDoj(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfJoining()));
				}
				if (employeeInfoEditForm.getDateOfLeaving() != null
						&& !employeeInfoEditForm.getDateOfLeaving().isEmpty()) {
					employee.setDateOfLeaving(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfLeaving()));
				}
				if (employeeInfoEditForm.getDateOfResignation() != null
						&& !employeeInfoEditForm.getDateOfResignation()
								.isEmpty()) {
					employee.setDateOfResignation(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfResignation()));
				}
				if (employeeInfoEditForm.getRetirementDate() != null
						&& !employeeInfoEditForm.getRetirementDate().isEmpty()) {
					employee.setRetirementDate(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getRetirementDate()));
				}
				if (employeeInfoEditForm.getEmail() != null
						&& !employeeInfoEditForm.getEmail().isEmpty()) {
					employee.setEmail(employeeInfoEditForm.getEmail());
				}

				if (employeeInfoEditForm.getIsSmartCardDataDelivered() != null
						&& !employeeInfoEditForm.getIsSmartCardDataDelivered()
								.isEmpty()) {
					String Value = employeeInfoEditForm
							.getIsSmartCardDataDelivered();
					if (Value.equals("1"))
						employee.setIsSCDataDelivered(true);
					else
						employee.setIsSCDataDelivered(false);
				}
				if (employeeInfoEditForm.getIsSmartCardDataGenerated() != null
						&& !employeeInfoEditForm.getIsSmartCardDataGenerated()
								.isEmpty()) {
					String Value = employeeInfoEditForm
							.getIsSmartCardDataGenerated();
					if (Value.equals("1"))
						employee.setIsSCDataGenerated(true);
					else
						employee.setIsSCDataGenerated(false);
				}

				if (employeeInfoEditForm.getSmartCardNo() != null
						&& !employeeInfoEditForm.getSmartCardNo().isEmpty()) {
					employee.setSmartCardNo(employeeInfoEditForm
							.getSmartCardNo());
				}
				/*
				 * if(employeeInfoEditForm.getReservationCategory()!=null &&
				 * employeeInfoEditForm.getReservationCategory().length>0){
				 * String reservationCategory=""; String[]
				 * categories=employeeInfoEditForm.getReservationCategory();
				 * for(int i=0;i<categories.length;){
				 * reservationCategory=reservationCategory+categories[i]; i++;
				 * if(i<categories.length)
				 * reservationCategory=reservationCategory+","; }
				 * employee.setReservationCategory(reservationCategory); }
				 */
				if (employeeInfoEditForm.getReservationCategory() != null
						&& !employeeInfoEditForm.getReservationCategory()
								.isEmpty()) {
					employee.setReservationCategory(employeeInfoEditForm
							.getReservationCategory());
				}
				 if(employeeInfoEditForm.getHandicappedDescription()!=null && !employeeInfoEditForm.getHandicappedDescription().trim().isEmpty())
					 employee.setHandicappedDescription(employeeInfoEditForm.getHandicappedDescription());	
					

				if (employeeInfoEditForm.getHomePhone1() != null
						&& !employeeInfoEditForm.getHomePhone1().isEmpty()) {
					employee
							.setCurrentAddressHomeTelephone1(employeeInfoEditForm
									.getHomePhone1());
				}

				if (employeeInfoEditForm.getHomePhone2() != null
						&& !employeeInfoEditForm.getHomePhone2().isEmpty()) {
					employee
							.setCurrentAddressHomeTelephone2(employeeInfoEditForm
									.getHomePhone2());
				}

				if (employeeInfoEditForm.getHomePhone3() != null
						&& !employeeInfoEditForm.getHomePhone3().isEmpty()) {
					employee
							.setCurrentAddressHomeTelephone3(employeeInfoEditForm
									.getHomePhone3());
				}
				if (employeeInfoEditForm.getWorkPhNo1() != null
						&& !employeeInfoEditForm.getWorkPhNo1().isEmpty()) {
					employee
							.setCurrentAddressWorkTelephone1(employeeInfoEditForm
									.getWorkPhNo1());
				}

				if (employeeInfoEditForm.getWorkPhNo2() != null
						&& !employeeInfoEditForm.getWorkPhNo2().isEmpty()) {
					employee
							.setCurrentAddressWorkTelephone2(employeeInfoEditForm
									.getWorkPhNo2());
				}

				if (employeeInfoEditForm.getWorkPhNo3() != null
						&& !employeeInfoEditForm.getWorkPhNo3().isEmpty()) {
					employee
							.setCurrentAddressWorkTelephone3(employeeInfoEditForm
									.getWorkPhNo3());
				}
				if (employeeInfoEditForm.getMobileNo1() != null
						&& !employeeInfoEditForm.getMobileNo1().isEmpty()) {
					employee.setCurrentAddressMobile1(employeeInfoEditForm
							.getMobileNo1());
				}
				if (employeeInfoEditForm.getHighQualifForAlbum() != null
						&& !employeeInfoEditForm.getHighQualifForAlbum()
								.isEmpty()) {
					employee.setHighQualifForAlbum(employeeInfoEditForm
							.getHighQualifForAlbum());
				}

				if (employeeInfoEditForm.getEmpSubjectAreaId() != null
						&& !employeeInfoEditForm.getEmpSubjectAreaId()
								.isEmpty()) {
					SubjectAreaBO subjectArea = new SubjectAreaBO();
					subjectArea.setId(Integer.parseInt(employeeInfoEditForm
							.getEmpSubjectAreaId()));
					employee.setEmpSubjectArea(subjectArea);
				}

			/*	if (employeeInfoEditForm.getEmpJobTypeId() != null
						&& !employeeInfoEditForm.getEmpJobTypeId().isEmpty()) {
					EmpJobType empJobType = new EmpJobType();
					empJobType.setId(Integer.parseInt(employeeInfoEditForm
							.getEmpJobTypeId()));
					employee.setEmpJobType(empJobType);
				}*/

				if (employeeInfoEditForm.getNoOfPublicationsRefered() != null
						&& !employeeInfoEditForm.getNoOfPublicationsRefered()
								.isEmpty()) {
					employee.setNoOfPublicationsRefered(employeeInfoEditForm
							.getNoOfPublicationsRefered());
				}

				if (employeeInfoEditForm.getNoOfPublicationsNotRefered() != null
						&& !employeeInfoEditForm
								.getNoOfPublicationsNotRefered().isEmpty()) {
					employee.setNoOfPublicationsNotRefered(employeeInfoEditForm
							.getNoOfPublicationsNotRefered());
				}

				if (employeeInfoEditForm.getBooks() != null
						&& !employeeInfoEditForm.getBooks().isEmpty()) {
					employee.setBooks(employeeInfoEditForm.getBooks());
				}
				if (employeeInfoEditForm.getDateOfResignation() != null
						&& !employeeInfoEditForm.getDateOfResignation()
								.isEmpty()) {
					employee.setDateOfResignation(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfResignation()));
				}
				if (employeeInfoEditForm.getDateOfLeaving() != null
						&& !employeeInfoEditForm.getDateOfLeaving().isEmpty()) {
					employee.setDateOfLeaving(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfLeaving()));
				}
				if (employeeInfoEditForm.getReasonOfLeaving() != null
						&& !employeeInfoEditForm.getReasonOfLeaving().isEmpty()) {
					employee.setReasonOfLeaving(employeeInfoEditForm
							.getReasonOfLeaving());
				}

				if (employeeInfoEditForm.getEmpQualificationLevelId() != null
						&& !employeeInfoEditForm.getEmpQualificationLevelId()
								.isEmpty()) {
					QualificationLevelBO empQualificationLevel = new QualificationLevelBO();
					empQualificationLevel.setId(Integer
							.parseInt(employeeInfoEditForm
									.getEmpQualificationLevelId()));
					employee.setEmpQualificationLevel(empQualificationLevel);
				}

				if (employeeInfoEditForm.getPayScaleId() != null
						&& !employeeInfoEditForm.getPayScaleId().isEmpty()) {
					PayScaleBO payScaleBo = new PayScaleBO();
					payScaleBo.setId(Integer.parseInt(employeeInfoEditForm
							.getPayScaleId()));
					employee.setPayScaleId(payScaleBo);
				}
				if (employeeInfoEditForm.getScale() != null
						&& !employeeInfoEditForm.getScale().isEmpty()) {
					employee.setScale(employeeInfoEditForm.getScale());
				}
			/*	if (employeeInfoEditForm.getBasicPay() != null
						&& !employeeInfoEditForm.getBasicPay().isEmpty()) {
					employee.setBasicPay(employeeInfoEditForm.getBasicPay());
				}*/

				if (employeeInfoEditForm.getGrossPay() != null
						&& !employeeInfoEditForm.getGrossPay().isEmpty()) {
					employee.setGrossPay(employeeInfoEditForm.getGrossPay());
				}
				/*if (employeeInfoEditForm.getGrade() != null
						&& !employeeInfoEditForm.getGrade().isEmpty()) {
					employee.setGrade(employeeInfoEditForm.getGrade());
				}*/
				if (employeeInfoEditForm.getRetirementDate() != null
						&& !employeeInfoEditForm.getRetirementDate().isEmpty()) {
					employee.setRetirementDate(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getRetirementDate()));
				}
				if (employeeInfoEditForm.getRejoinDate() != null
						&& !employeeInfoEditForm.getRejoinDate().isEmpty()) {
					employee.setRejoinDate(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getRejoinDate()));
				}
				if (employeeInfoEditForm.getTimeIn() != null
						&& !employeeInfoEditForm.getTimeIn().isEmpty()
						&& employeeInfoEditForm.getTimeInMin() != null
						&& !employeeInfoEditForm.getTimeInMin().isEmpty()) {
					employee.setTimeIn(employeeInfoEditForm.getTimeIn() + ":"
							+ employeeInfoEditForm.getTimeInMin());
				}

				if (employeeInfoEditForm.getTimeInEnds() != null
						&& !employeeInfoEditForm.getTimeInEnds().isEmpty()
						&& employeeInfoEditForm.getTimeInEndMIn() != null
						&& !employeeInfoEditForm.getTimeInEndMIn().isEmpty()) {
					employee.setTimeInEnds(employeeInfoEditForm.getTimeInEnds()
							+ ":" + employeeInfoEditForm.getTimeInEndMIn());
				}

				if (employeeInfoEditForm.getTimeOut() != null
						&& !employeeInfoEditForm.getTimeOut().isEmpty()
						&& employeeInfoEditForm.getTimeOutMin() != null
						&& !employeeInfoEditForm.getTimeOutMin().isEmpty()) {
					employee.setTimeOut(employeeInfoEditForm.getTimeOut() + ":"
							+ employeeInfoEditForm.getTimeOutMin());
				}

				if (employeeInfoEditForm.getSaturdayTimeOut() != null
						&& !employeeInfoEditForm.getSaturdayTimeOut().isEmpty()
						&& employeeInfoEditForm.getSaturdayTimeOutMin() != null
						&& !employeeInfoEditForm.getSaturdayTimeOutMin()
								.isEmpty()) {
					employee.setSaturdayTimeOut(employeeInfoEditForm
							.getSaturdayTimeOut()
							+ ":"
							+ employeeInfoEditForm.getSaturdayTimeOutMin());
				}

				if (employeeInfoEditForm.getHalfDayStartTime() != null
						&& !employeeInfoEditForm.getHalfDayStartTime()
								.isEmpty()
						&& employeeInfoEditForm.getHalfDayStartTimeMin() != null
						&& !employeeInfoEditForm.getHalfDayStartTimeMin()
								.isEmpty()) {
					employee.setHalfDayStartTime(employeeInfoEditForm
							.getHalfDayStartTime()
							+ ":"
							+ employeeInfoEditForm.getHalfDayStartTimeMin());
				}

				if (employeeInfoEditForm.getHalfDayEndTime() != null
						&& !employeeInfoEditForm.getHalfDayEndTime().isEmpty()
						&& employeeInfoEditForm.getHalfDayEndTimeMin() != null
						&& !employeeInfoEditForm.getHalfDayEndTimeMin()
								.isEmpty()) {
					employee
							.setHalfDayEndTime(employeeInfoEditForm
									.getHalfDayEndTime()
									+ ":"
									+ employeeInfoEditForm
											.getHalfDayEndTimeMin());
				}

				if (employeeInfoEditForm.getFourWheelerNo() != null
						&& !employeeInfoEditForm.getFourWheelerNo().isEmpty()) {
					employee.setFourWheelerNo(employeeInfoEditForm
							.getFourWheelerNo());
				}
				if (employeeInfoEditForm.getTwoWheelerNo() != null
						&& !employeeInfoEditForm.getTwoWheelerNo().isEmpty()) {
					employee.setTwoWheelerNo(employeeInfoEditForm
							.getTwoWheelerNo());
				}
			/*	if (employeeInfoEditForm.getVehicleNo() != null
						&& !employeeInfoEditForm.getVehicleNo().isEmpty()) {
					employee.setVehicleNo(employeeInfoEditForm.getVehicleNo());
				}*/
				if (employeeInfoEditForm.getPfNo() != null
						&& !employeeInfoEditForm.getPfNo().isEmpty()) {
					employee.setPfNo(employeeInfoEditForm.getPfNo());
				}
				if (employeeInfoEditForm.getBankAccNo() != null
						&& !employeeInfoEditForm.getBankAccNo().isEmpty()) {
					employee.setBankAccNo(employeeInfoEditForm.getBankAccNo());
				}
				if (employeeInfoEditForm.getStreamId() != null
						&& !employeeInfoEditForm.getStreamId().isEmpty()) {
					EmployeeStreamBO empStream = new EmployeeStreamBO();
					empStream.setId(Integer.parseInt(employeeInfoEditForm
							.getStreamId()));
					employee.setStreamId(empStream);
				}
				if (employeeInfoEditForm.getWorkLocationId() != null
						&& !employeeInfoEditForm.getWorkLocationId().isEmpty()) {
					EmployeeWorkLocationBO empworkLoc = new EmployeeWorkLocationBO();
					empworkLoc.setId(Integer.parseInt(employeeInfoEditForm
							.getWorkLocationId()));
					employee.setWorkLocationId(empworkLoc);
				}
				if (employeeInfoEditForm.getActive() != null
						&& !employeeInfoEditForm.getActive().isEmpty()) {
					String Value = employeeInfoEditForm.getActive();
					if (Value.equals("1"))
						employee.setActive(true);
					else
						employee.setActive(false);
				}
				if (employeeInfoEditForm.getSameAddress() != null
						&& !employeeInfoEditForm.getSameAddress().isEmpty()) {
					String Value = employeeInfoEditForm.getSameAddress();
					if (Value.equals("true"))
						employee.setIsSameAddress(true);
					else
						employee.setIsSameAddress(false);
				}
				if (employeeInfoEditForm.getTeachingStaff() != null
						&& !employeeInfoEditForm.getTeachingStaff().isEmpty()) {
					String Value = employeeInfoEditForm.getTeachingStaff();
					if (Value.equals("1"))
						employee.setTeachingStaff(true);
					else
						employee.setTeachingStaff(false);
				}
				if (employeeInfoEditForm.getIsPunchingExcemption() != null
						&& !employeeInfoEditForm.getIsPunchingExcemption().isEmpty()) {
					String Value = employeeInfoEditForm.getIsPunchingExcemption();
					if (Value.equals("1"))
						employee.setIsPunchingExcemption(true);
					else
						employee.setIsPunchingExcemption(false);
				}
				/*
				 * if(employeeInfoEditForm.getTeachingStaff()!=null &&
				 * !employeeInfoEditForm.getTeachingStaff().isEmpty()){
				 * employee.
				 * setTeachingStaff(Boolean.parseBoolean(employeeInfoEditForm
				 * .getTeachingStaff()));
				 * 
				 * }
				 */
				if (employeeInfoEditForm.getPanno() != null
						&& !employeeInfoEditForm.getPanno().isEmpty()) {
					employee.setPanNo(employeeInfoEditForm.getPanno());
				}
				if (employeeInfoEditForm.getExpYears() != null
						&& !employeeInfoEditForm.getExpYears().isEmpty()) {
					employee
							.setTotalExpYear(employeeInfoEditForm.getExpYears());
				}
				if (employeeInfoEditForm.getExpMonths() != null
						&& !employeeInfoEditForm.getExpMonths().isEmpty()) {
					employee.setTotalExpMonths(employeeInfoEditForm
							.getExpMonths());
				}
				if (employeeInfoEditForm.getRelevantExpMonths() != null
						&& !employeeInfoEditForm.getRelevantExpMonths()
								.isEmpty()) {
					employee.setRelevantExpMonths(employeeInfoEditForm
							.getRelevantExpMonths());
				}
				if (employeeInfoEditForm.getRelevantExpYears() != null
						&& !employeeInfoEditForm.getRelevantExpYears()
								.isEmpty()) {
					employee.setRelevantExpYears(employeeInfoEditForm
							.getRelevantExpYears());
				}
				if (employeeInfoEditForm.getReligionId() != null
						&& !employeeInfoEditForm.getReligionId().isEmpty()) {
					Religion empReligion = new Religion();
					empReligion.setId(Integer.parseInt(employeeInfoEditForm
							.getReligionId()));
					employee.setReligionId(empReligion);
				}
				if (employeeInfoEditForm.getOtherInfo() != null
						&& !employeeInfoEditForm.getOtherInfo().isEmpty()) {
					employee.setOtherInfo(employeeInfoEditForm.getOtherInfo());
				}
				if (employeeInfoEditForm.getuId() != null
						&& !employeeInfoEditForm.getuId().isEmpty()) {
					employee.setUid(employeeInfoEditForm.getuId());
				}
				if (employeeInfoEditForm.getOfficialEmail() != null
						&& !employeeInfoEditForm.getOfficialEmail().isEmpty()) {
					employee.setWorkEmail(employeeInfoEditForm
							.getOfficialEmail());
				}
				if (employeeInfoEditForm.getEmail() != null
						&& !employeeInfoEditForm.getEmail().isEmpty()) {
					employee.setOtherEmail(employeeInfoEditForm.getEmail());
				}
				if (employeeInfoEditForm.getEmContactWorkTel() != null
						&& !employeeInfoEditForm.getEmContactWorkTel()
								.isEmpty()) {
					employee.setEmergencyWorkTelephone(employeeInfoEditForm
							.getEmContactWorkTel());
				}
				if (employeeInfoEditForm.getEmContactHomeTel() != null
						&& !employeeInfoEditForm.getEmContactHomeTel()
								.isEmpty()) {
					employee.setEmergencyHomeTelephone(employeeInfoEditForm
							.getEmContactHomeTel());
				}
				if (employeeInfoEditForm.getEmContactMobile() != null
						&& !employeeInfoEditForm.getEmContactMobile().isEmpty()) {
					employee.setEmergencyMobile(employeeInfoEditForm
							.getEmContactMobile());
				}
				if (employeeInfoEditForm.getEmContactAddress() != null
						&& !employeeInfoEditForm.getEmContactAddress().isEmpty()) {
					employee.setEmContactAddress(employeeInfoEditForm
							.getEmContactAddress());
				}
				if (employeeInfoEditForm.getTitleId() != null
						&& !employeeInfoEditForm.getTitleId().isEmpty() &&
						employeeInfoEditForm.getTitleId() != "Other"
						&& !employeeInfoEditForm.getTitleId().equalsIgnoreCase("Other")) {
					EmpJobTitle empJobTitle = new EmpJobTitle();
					empJobTitle.setId(Integer.parseInt(employeeInfoEditForm
							.getTitleId()));
					employee.setTitleId(empJobTitle);
				}
				else if (employeeInfoEditForm.getTitleOthers() != null
						&& !employeeInfoEditForm.getTitleOthers().isEmpty()) {
					employee.setTitleOthers(employeeInfoEditForm.getTitleOthers());;
				}
				if (employeeInfoEditForm.getEmContactRelationship() != null
						&& !employeeInfoEditForm.getEmContactRelationship()
								.isEmpty()) {
					employee.setRelationship(employeeInfoEditForm
							.getEmContactRelationship());
				}
				if (employeeInfoEditForm.getEmContactName() != null
						&& !employeeInfoEditForm.getEmContactName().isEmpty()) {
					employee.setEmergencyContName(employeeInfoEditForm
							.getEmContactName());
				}
				if (employeeInfoEditForm.getReportToId() != null
						&& !employeeInfoEditForm.getReportToId().isEmpty()) {
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getReportToId()));
					employee.setEmployeeByReportToId(emp);
				}
				if (employeeInfoEditForm.getEmptypeId() != null
						&& !employeeInfoEditForm.getEmptypeId().isEmpty()) {
					EmpType emptype = new EmpType();
					emptype.setId(Integer.parseInt(employeeInfoEditForm
							.getEmptypeId()));
					employee.setEmptype(emptype);
				}

				if (employeeInfoEditForm.getMaritalStatus() != null
						&& !employeeInfoEditForm.getMaritalStatus().isEmpty()) {
					employee.setMaritalStatus(employeeInfoEditForm
							.getMaritalStatus());
				}
				if (employeeInfoEditForm.getBloodGroup() != null
						&& !employeeInfoEditForm.getBloodGroup().isEmpty()) {
					employee
							.setBloodGroup(employeeInfoEditForm.getBloodGroup());
				}
				if (employeeInfoEditForm.getQualificationId() != null
						&& !employeeInfoEditForm.getQualificationId().isEmpty() &&
						employeeInfoEditForm.getQualificationId() != "Other"
						&& !employeeInfoEditForm.getQualificationId().equalsIgnoreCase("Other")) {
					QualificationLevelBO qual = new QualificationLevelBO();
					qual.setId(Integer.parseInt(employeeInfoEditForm
							.getQualificationId()));
					employee.setEmpQualificationLevel(qual);
				}else if(employeeInfoEditForm.getQualificationId() == "Other"
						&& employeeInfoEditForm.getQualificationId().equalsIgnoreCase("Other") &&
						employeeInfoEditForm.getHighestQualification()!= null
						&& !employeeInfoEditForm.getQualificationId().isEmpty()
						){
							employee.setOtherQualification(employeeInfoEditForm.getQualificationOthers());
							employee.setHighestOtherQualification(employeeInfoEditForm.getHighestQualification());
				}

				/*if (employeeInfoEditForm.getEmpPhoto() == null
						|| employeeInfoEditForm.getPhotoBytes() != null) {

					// employeeInfoEditForm.getPhotoBytes();
					byte[] data = employeeInfoEditForm.getPhotoBytes();
					if (data.length > 0) {
						employee.setEmpPhoto(data);
					}

				}
				if (employeeInfoEditForm.getEmpPhoto() != null) {

					FormFile file = employeeInfoEditForm.getEmpPhoto();
					byte[] data = file.getFileData();
					if (data.length > 0) {
						employee.setEmpPhoto(data);
					}

				}*/

			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			/* code added by sudhir */
			if(employeeInfoEditForm.getExtensionNumber()!=null && !employeeInfoEditForm.getExtensionNumber().isEmpty()){
				employee.setExtensionNumber(employeeInfoEditForm.getExtensionNumber());
			}
			if(employeeInfoEditForm.getDisplayInWebsite().equalsIgnoreCase("1")){
				employee.setDisplayInWebsite(true);
			}else if(employeeInfoEditForm.getDisplayInWebsite().equalsIgnoreCase("0")){
				employee.setDisplayInWebsite(false);
			}
			// added by venkat
			if(employeeInfoEditForm.getResearchPapersRefereed()!=null && !employeeInfoEditForm.getResearchPapersRefereed().isEmpty()){
				employee.setResearchPapersRefereed(Integer.parseInt(employeeInfoEditForm.getResearchPapersRefereed()));
			}
			if(employeeInfoEditForm.getResearchPapersNonRefereed()!=null && !employeeInfoEditForm.getResearchPapersNonRefereed().isEmpty()){
				employee.setResearchPapersNonRefereed(Integer.parseInt(employeeInfoEditForm.getResearchPapersNonRefereed()));
			}
			if(employeeInfoEditForm.getResearchPapersProceedings()!=null && !employeeInfoEditForm.getResearchPapersProceedings().isEmpty()){
				employee.setResearchPapersProceedings(Integer.parseInt(employeeInfoEditForm.getResearchPapersProceedings()));
			}
			if(employeeInfoEditForm.getInternationalPublications()!=null && !employeeInfoEditForm.getInternationalPublications().isEmpty()){
				employee.setInternationalBookPublications(Integer.parseInt(employeeInfoEditForm.getInternationalPublications()));
			}
			if(employeeInfoEditForm.getNationalPublications()!=null && !employeeInfoEditForm.getNationalPublications().isEmpty()){
				employee.setNationalBookPublications(Integer.parseInt(employeeInfoEditForm.getNationalPublications()));
			}
			if(employeeInfoEditForm.getLocalPublications()!=null && !employeeInfoEditForm.getLocalPublications().isEmpty()){
				employee.setLocalBookPublications(Integer.parseInt(employeeInfoEditForm.getLocalPublications()));
			}
			if(employeeInfoEditForm.getInternational()!=null && !employeeInfoEditForm.getInternational().isEmpty()){
				employee.setChaptersEditedBooksInternational(Integer.parseInt(employeeInfoEditForm.getInternational()));
			}
			if(employeeInfoEditForm.getNational()!=null && !employeeInfoEditForm.getNational().isEmpty()){
				employee.setChaptersEditedBooksNational(Integer.parseInt(employeeInfoEditForm.getNational()));
			}
			if(employeeInfoEditForm.getMajorProjects()!=null && !employeeInfoEditForm.getMajorProjects().isEmpty()){
				employee.setMajorSponseredProjects(Integer.parseInt(employeeInfoEditForm.getMajorProjects()));
			}
			if(employeeInfoEditForm.getMinorProjects()!=null && !employeeInfoEditForm.getMinorProjects().isEmpty()){
				employee.setMinorSponseredProjects(Integer.parseInt(employeeInfoEditForm.getMinorProjects()));
			}
			if(employeeInfoEditForm.getConsultancyPrjects1()!=null && !employeeInfoEditForm.getConsultancyPrjects1().isEmpty()){
				employee.setConsultancy1SponseredProjects(Integer.parseInt(employeeInfoEditForm.getConsultancyPrjects1()));
			}
			if(employeeInfoEditForm.getConsultancyProjects2()!=null && !employeeInfoEditForm.getConsultancyProjects2().isEmpty()){
				employee.setConsultancy2SponseredProjects(Integer.parseInt(employeeInfoEditForm.getConsultancyProjects2()));
			}
			if(employeeInfoEditForm.getPhdResearchGuidance()!=null && !employeeInfoEditForm.getPhdResearchGuidance().isEmpty()){
				employee.setPhdResearchGuidance(Integer.parseInt(employeeInfoEditForm.getPhdResearchGuidance()));
			}
			if(employeeInfoEditForm.getMphilResearchGuidance()!=null && !employeeInfoEditForm.getMphilResearchGuidance().isEmpty()){
				employee.setMphilResearchGuidance(Integer.parseInt(employeeInfoEditForm.getMphilResearchGuidance()));
			}
			if(employeeInfoEditForm.getFdp1Training()!=null && !employeeInfoEditForm.getFdp1Training().isEmpty()){
				employee.setTrainingAttendedFdp1Weeks(Integer.parseInt(employeeInfoEditForm.getFdp1Training()));
			}
			if(employeeInfoEditForm.getFdp2Training()!=null && !employeeInfoEditForm.getFdp2Training().isEmpty()){
				employee.setTrainingAttendedFdp2Weeks(Integer.parseInt(employeeInfoEditForm.getFdp2Training()));
			}
			if(employeeInfoEditForm.getInternationalConference()!=null && !employeeInfoEditForm.getInternationalConference().isEmpty()){
				employee.setInternationalConferencePresentaion(Integer.parseInt(employeeInfoEditForm.getInternationalConference()));
			}
			if(employeeInfoEditForm.getNationalConference()!=null && !employeeInfoEditForm.getNationalConference().isEmpty()){
				employee.setNationalConferencePresentaion(Integer.parseInt(employeeInfoEditForm.getNationalConference()));
			}
			if(employeeInfoEditForm.getLocalConference()!=null && !employeeInfoEditForm.getLocalConference().isEmpty()){
				employee.setLocalConferencePresentaion(Integer.parseInt(employeeInfoEditForm.getLocalConference()));
			}
			if(employeeInfoEditForm.getRegionalConference()!=null && !employeeInfoEditForm.getRegionalConference().isEmpty()){
				employee.setRegionalConferencePresentaion(Integer.parseInt(employeeInfoEditForm.getRegionalConference()));
			}
			if(employeeInfoEditForm.getFatherName()!=null && !employeeInfoEditForm.getFatherName().isEmpty()){
				employee.setFatherName(employeeInfoEditForm.getFatherName());
			}
			if(employeeInfoEditForm.getMotherName()!=null && !employeeInfoEditForm.getMotherName().isEmpty()){
				employee.setMotherName(employeeInfoEditForm.getMotherName());
			}
			if(employeeInfoEditForm.getDeputationDepartmentId()!=null && !employeeInfoEditForm.getDeputationDepartmentId().isEmpty()){
				Department department = new Department();
				department.setId(Integer.parseInt(employeeInfoEditForm.getDeputationDepartmentId()));
				employee.setDeputationToDepartmentId(department);
			}
			if(employeeInfoEditForm.getAppointmentLetterDate()!=null && !employeeInfoEditForm.getAppointmentLetterDate().isEmpty()){
				employee.setAppointmentLetterDate(CommonUtil.ConvertStringToSQLDate(employeeInfoEditForm.getAppointmentLetterDate()));
			}
			if(employeeInfoEditForm.getReferenceNumberForAppointment()!=null && !employeeInfoEditForm.getReferenceNumberForAppointment().isEmpty()){
				employee.setReferenceNumberForAppointment(employeeInfoEditForm.getReferenceNumberForAppointment());
			}
			if(employeeInfoEditForm.getReleavingOrderDate()!=null && !employeeInfoEditForm.getReleavingOrderDate().isEmpty()){
				employee.setReleavingOrderDate(CommonUtil.ConvertStringToSQLDate(employeeInfoEditForm.getReleavingOrderDate()));
			}
			if(employeeInfoEditForm.getReferenceNumberForReleaving()!=null && !employeeInfoEditForm.getReferenceNumberForReleaving().isEmpty()){
				employee.setReferenceNubmerforReleaving(employeeInfoEditForm.getReferenceNumberForReleaving());
			}
			if(employeeInfoEditForm.getAdditionalRemarks()!=null && !employeeInfoEditForm.getAdditionalRemarks().isEmpty()){
				employee.setAdditionalRemarks(employeeInfoEditForm.getAdditionalRemarks());
			}
			if (employeeInfoEditForm.getRefresherCourse()!=null && !employeeInfoEditForm.getRefresherCourse().isEmpty()) {
				employee.setRefresherCourse(employeeInfoEditForm.getRefresherCourse());
			}
			if (employeeInfoEditForm.getOrientationCourse()!=null && !employeeInfoEditForm.getOrientationCourse().isEmpty()) {
				employee.setOrientationCourse(employeeInfoEditForm.getOrientationCourse());
			}
			/*---------------------*/
			employee.setIsActive(true);
			employee.setCreatedBy(employeeInfoEditForm.getUserId());
			employee.setCreatedDate(new Date());
			employee.setLastModifiedDate(new Date());
			employee.setModifiedBy(employeeInfoEditForm.getUserId());

		}
		return employee;
	}

	private Set<PfGratuityNominees> getEmpPfGratuityNomineesObjects(EmployeeInfoEditForm employeeInfoEditForm) {
		Set<PfGratuityNominees> pfGratuity =new HashSet<PfGratuityNominees>();
		if(employeeInfoEditForm.getEmployeeInfoTONew()!=null && employeeInfoEditForm.getEmployeeInfoTONew().getPfGratuityNominee()!=null
				&& !employeeInfoEditForm.getEmployeeInfoTONew().getPfGratuityNominee().isEmpty()){
			Iterator<PfGratuityNomineesTO> itr=employeeInfoEditForm.getEmployeeInfoTONew().getPfGratuityNominee().iterator();
			while (itr.hasNext()) {
				PfGratuityNomineesTO pfTo = (PfGratuityNomineesTO) itr.next();
				PfGratuityNominees pfBo=new PfGratuityNominees();
				if(pfTo.getId()!=null){
					pfBo.setId(Integer.parseInt(pfTo.getId()));
				}
				if(pfTo.getNameAdressNominee()!=null && !pfTo.getNameAdressNominee().isEmpty())
				pfBo.setNameAdressNominee(pfTo.getNameAdressNominee());
				if(pfTo.getMemberRelationship()!=null && !pfTo.getMemberRelationship().isEmpty())
				pfBo.setMemberRelationship(pfTo.getMemberRelationship());
				if(pfTo.getDobMember()!=null && !pfTo.getDobMember().isEmpty())
				pfBo.setDobMember(CommonUtil.ConvertStringToDate(pfTo.getDobMember()));
				if(pfTo.getShare()!=null && !pfTo.getShare().isEmpty())
				pfBo.setShare(pfTo.getShare());
				if(pfTo.getNameAdressGuardian()!=null && !pfTo.getNameAdressGuardian().isEmpty())
				pfBo.setNameAdressGuardian(pfTo.getNameAdressGuardian());
				
				pfBo.setCreatedBy(employeeInfoEditForm.getUserId());
				pfBo.setCreatedDate(new Date());
				pfBo.setModifiedBy(employeeInfoEditForm.getUserId());
				pfBo.setLastModifiedDate(new Date());
				pfBo.setIsActive(true);
				pfGratuity.add(pfBo);
		}}
		return pfGratuity;
	}

	/**
	 * @param employeeInfoEditForm
	 * @return
	 */
	private Set<EmpLoan> getEmpLoanBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpLoan> loans = new HashSet<EmpLoan>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan()
						.isEmpty()) {
			Iterator<EmpLoanTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpLoan().iterator();
			while (itr.hasNext()) {
				EmpLoanTO to = (EmpLoanTO) itr.next();
				EmpLoan loan = new EmpLoan();
				if (to.getLoanAmount() != null && !to.getLoanAmount().isEmpty()
						|| to.getLoanDetails() != null
						&& !to.getLoanDetails().isEmpty()) {
					if (to.getId() > 0) {
						loan.setId(to.getId());
					}
					loan.setCreatedBy(employeeInfoEditForm.getUserId());
					loan.setCreatedDate(new Date());
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					loan.setEmployee(emp);
					loan.setModifiedBy(employeeInfoEditForm.getUserId());
					loan.setLastModifiedDate(new Date());
					loan.setIsActive(true);
					loan.setLoanAmount(to.getLoanAmount());
					loan.setLoanDate(CommonUtil.ConvertStringToDate(to
							.getLoanDate()));
					loan.setLoanDetails(to.getLoanDetails());
					loans.add(loan);
				}
			}
		}
		return loans;
	}

	private Set<EmpFinancial> getEmpFinancialBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpFinancial> financial = new HashSet<EmpFinancial>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpFinancial() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpFinancial().isEmpty()) {
			Iterator<EmpFinancialTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpFinancial().iterator();
			while (itr.hasNext()) {
				EmpFinancialTO to = (EmpFinancialTO) itr.next();
				EmpFinancial fin = new EmpFinancial();
				if (to.getFinancialAmount() != null
						&& !to.getFinancialAmount().isEmpty()
						|| to.getFinancialDetails() != null
						&& !to.getFinancialDetails().isEmpty()) {
					if (to.getId() > 0) {
						fin.setId(to.getId());
					}

					fin.setCreatedBy(employeeInfoEditForm.getUserId());
					fin.setCreatedDate(new Date());
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					fin.setEmployee(emp);
					fin.setModifiedBy(employeeInfoEditForm.getUserId());
					fin.setLastModifiedDate(new Date());
					fin.setIsActive(true);
					fin.setFinancialAmount(to.getFinancialAmount());
					fin.setFinancialDate(CommonUtil.ConvertStringToDate(to
							.getFinancialDate()));
					fin.setFinancialDetails(to.getFinancialDetails());

					financial.add(fin);
				}
			}
		}
		return financial;
	}

	private Set<EmpFeeConcession> getEmpFeeConcessionBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpFeeConcession> feeConcession = new HashSet<EmpFeeConcession>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpFeeConcession() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpFeeConcession().isEmpty()) {
			Iterator<EmpFeeConcessionTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpFeeConcession().iterator();
			while (itr.hasNext()) {
				EmpFeeConcessionTO to = (EmpFeeConcessionTO) itr.next();
				EmpFeeConcession fin = new EmpFeeConcession();
				if ((to.getFeeConcessionAmount() != null && !to
						.getFeeConcessionAmount().isEmpty())
						|| (to.getFeeConcessionDetails() != null && !to
								.getFeeConcessionDetails().isEmpty())) {
					if (to.getId() > 0) {
						fin.setId(to.getId());
					}
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					fin.setEmployee(emp);
					fin.setCreatedBy(employeeInfoEditForm.getUserId());
					fin.setCreatedDate(new Date());
					fin.setModifiedBy(employeeInfoEditForm.getUserId());
					fin.setLastModifiedDate(new Date());
					fin.setIsActive(true);
					fin.setFeeConcessionAmount(to.getFeeConcessionAmount());
					fin.setFeeConcessionDate(CommonUtil.ConvertStringToDate(to
							.getFeeConcessionDate()));
					fin.setFeeConcessionDetails(to.getFeeConcessionDetails());

					feeConcession.add(fin);
				}
			}
		}
		return feeConcession;
	}

	private Set<EmpIncentives> getEmpIncentivesBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpIncentives> incentives = new HashSet<EmpIncentives>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpIncentives() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpIncentives().isEmpty()) {
			Iterator<EmpIncentivesTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpIncentives().iterator();
			while (itr.hasNext()) {
				EmpIncentivesTO to = (EmpIncentivesTO) itr.next();
				EmpIncentives fin = new EmpIncentives();
				if ((to.getIncentivesAmount() != null && !to
						.getIncentivesAmount().isEmpty())
						|| (to.getIncentivesDetails() != null && !to
								.getIncentivesDetails().isEmpty())) {
					if (to.getId() > 0) {
						fin.setId(to.getId());
					}
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					fin.setEmployee(emp);
					fin.setCreatedBy(employeeInfoEditForm.getUserId());
					fin.setCreatedDate(new Date());
					fin.setModifiedBy(employeeInfoEditForm.getUserId());
					fin.setLastModifiedDate(new Date());
					fin.setIsActive(true);
					fin.setIncentivesAmount(to.getIncentivesAmount());
					fin.setIncentivesDate(CommonUtil.ConvertStringToDate(to
							.getIncentivesDate()));
					fin.setIncentivesDetails(to.getIncentivesDetails());

					incentives.add(fin);
				}
			}
		}
		return incentives;
	}
	
	/**
	 * @param employeeInfoEditForm
	 * @return
	 */
	private Set<EmpImages> getEmpImagesBoObjects(EmployeeInfoEditForm employeeInfoEditForm) throws Exception {
		Set<EmpImages> images = new HashSet<EmpImages>();
		
				EmpImages img = new EmpImages();
				if (employeeInfoEditForm.getEmpPhoto() != null || employeeInfoEditForm.getPhotoBytes() != null)
					{
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
					img.setEmployee(emp);
					if(employeeInfoEditForm.getEmpImageId()!=null && !employeeInfoEditForm.getEmpImageId().isEmpty() && Integer.parseInt(employeeInfoEditForm.getEmpImageId())>0)
					{
					img.setId(Integer.parseInt(employeeInfoEditForm.getEmpImageId()));
					}
					img.setCreatedBy(employeeInfoEditForm.getUserId());
					img.setCreatedDate(new Date());
					img.setModifiedBy(employeeInfoEditForm.getUserId());
					img.setLastModifiedDate(new Date());
					if (employeeInfoEditForm.getEmpPhoto() == null	|| employeeInfoEditForm.getPhotoBytes() != null) {
						
						byte[] data = employeeInfoEditForm.getPhotoBytes();
						if (data.length > 0) {
							img.setEmpPhoto(data);
						}
					}
					if (employeeInfoEditForm.getEmpPhoto() != null && employeeInfoEditForm.getEmpPhoto().getFileSize()>0) {
						FormFile file = employeeInfoEditForm.getEmpPhoto();
						byte[] data = file.getFileData();
						if (data.length > 0) {
							img.setEmpPhoto(data);
						}
					}
					
					images.add(img);
				}
			
		return images;
	}
	
	private Set<EmpRemarks> getEmpRemarksBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpRemarks> remarks = new HashSet<EmpRemarks>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks()
						.isEmpty()) {
			Iterator<EmpRemarksTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpRemarks().iterator();
			while (itr.hasNext()) {
				EmpRemarksTO to = (EmpRemarksTO) itr.next();
				EmpRemarks fin = new EmpRemarks();
				if ((to.getRemarkDetails() != null && !to.getRemarkDetails()
						.isEmpty())
						|| (to.getEnteredBy() != null && !to.getEnteredBy()
								.isEmpty())) {
					if (to.getId() > 0) {
						fin.setId(to.getId());
					}
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					fin.setEmployee(emp);
					fin.setCreatedBy(employeeInfoEditForm.getUserId());
					fin.setCreatedDate(new Date());
					fin.setModifiedBy(employeeInfoEditForm.getUserId());
					fin.setLastModifiedDate(new Date());
					fin.setIsActive(true);
					fin.setRemarksDate(CommonUtil.ConvertStringToDate(to
							.getRemarkDate()));
					fin.setRemarksDetails(to.getRemarkDetails());
					fin.setRemarksEnteredBy(to.getEnteredBy());
					remarks.add(fin);
				}
			}
		}
		return remarks;
	}

	private Set<EmpImmigration> getEmpImmigrationBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpImmigration> empImmigrations = new HashSet<EmpImmigration>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null) {
			if (employeeInfoEditForm.getEmployeeInfoTONew().getEmpImmigration() != null) {

				Iterator<EmpImmigrationTO> iterator = employeeInfoEditForm
						.getEmployeeInfoTONew().getEmpImmigration().iterator();
				while (iterator.hasNext()) {
					EmpImmigrationTO empImmigrationTO = iterator.next();
					EmpImmigration empEmg = new EmpImmigration();
					if (empImmigrationTO != null) {
						if ((empImmigrationTO.getPassportComments() != null && !empImmigrationTO
								.getPassportComments().isEmpty())
								|| (empImmigrationTO.getPassportNo() != null && !empImmigrationTO
										.getPassportNo().isEmpty())
								|| (empImmigrationTO.getPassportReviewStatus() != null && !empImmigrationTO
										.getPassportReviewStatus().isEmpty())
								|| (empImmigrationTO.getVisaComments() != null && !empImmigrationTO
										.getVisaComments().isEmpty())
								|| (empEmg.getPassportStatus() != null && !empImmigrationTO
										.getPassportStatus().isEmpty())
								|| (empEmg.getVisaComments() != null && !empImmigrationTO
										.getVisaComments().isEmpty())
								|| (empImmigrationTO.getVisaNo() != null && !empImmigrationTO
										.getVisaNo().isEmpty())
								|| (empImmigrationTO.getVisaReviewStatus() != null && !empImmigrationTO
										.getVisaReviewStatus().isEmpty())
								|| (empImmigrationTO.getVisaStatus() != null && !empImmigrationTO
										.getVisaStatus().isEmpty())) {
							if (empImmigrationTO.getId() != null) {
								empEmg.setId(Integer.parseInt(empImmigrationTO
										.getId()));
							}

							if (empImmigrationTO.getPassportComments() != null
									&& !empImmigrationTO.getPassportComments()
											.isEmpty()) {
								empEmg.setPassportComments(empImmigrationTO
										.getPassportComments());
							}
							if (empImmigrationTO.getPassportCountryId() != null
									&& !empImmigrationTO.getPassportCountryId()
											.isEmpty()) {

								Country country = new Country();
								country.setId(Integer.parseInt(empImmigrationTO
										.getPassportCountryId()));
								empEmg.setCountryByPassportCountryId(country);
							}

							if (empImmigrationTO.getPassportExpiryDate() != null
									&& !empImmigrationTO
											.getPassportExpiryDate().isEmpty()) {
								empEmg.setPassportDateOfExpiry(CommonUtil
										.ConvertStringToDate(empImmigrationTO
												.getPassportExpiryDate()));
							}
							if (empImmigrationTO.getPassportIssueDate() != null
									&& !empImmigrationTO.getPassportIssueDate()
											.isEmpty()) {
								empEmg.setPassportIssueDate(CommonUtil
										.ConvertStringToDate(empImmigrationTO
												.getPassportIssueDate()));
							}
							if (empImmigrationTO.getPassportNo() != null
									&& !empImmigrationTO.getPassportNo()
											.isEmpty()) {
								empEmg.setPassportNo(empImmigrationTO
										.getPassportNo());
							}
							if (empImmigrationTO.getPassportReviewStatus() != null
									&& !empImmigrationTO
											.getPassportReviewStatus()
											.isEmpty()) {
								empEmg.setPassportReviewStatus(empImmigrationTO
										.getPassportReviewStatus());
							}
							if (empImmigrationTO.getPassportStatus() != null
									&& !empImmigrationTO.getPassportStatus()
											.isEmpty()) {
								empEmg.setPassportStatus(empImmigrationTO
										.getPassportStatus());
							}
							if (empImmigrationTO.getVisaComments() != null
									&& !empImmigrationTO.getVisaComments()
											.isEmpty()) {
								empEmg.setVisaComments(empImmigrationTO
										.getVisaComments());
							}
							if (empImmigrationTO.getVisaCountryId() != null
									&& !empImmigrationTO.getVisaCountryId()
											.isEmpty()) {

								Country country = new Country();
								country.setId(Integer.parseInt(empImmigrationTO
										.getVisaCountryId()));
								empEmg.setCountryByVisaCountryId(country);
							}

							if (empImmigrationTO.getVisaExpiryDate() != null
									&& !empImmigrationTO.getVisaExpiryDate()
											.isEmpty()) {
								empEmg.setVisaDateOfExpiry(CommonUtil
										.ConvertStringToDate(empImmigrationTO
												.getVisaExpiryDate()));
							}
							if (empImmigrationTO.getVisaIssueDate() != null
									&& !empImmigrationTO.getVisaIssueDate()
											.isEmpty()) {
								empEmg.setVisaIssueDate(CommonUtil
										.ConvertStringToDate(empImmigrationTO
												.getVisaIssueDate()));
							}
							if (empImmigrationTO.getVisaNo() != null
									&& !empImmigrationTO.getVisaNo().isEmpty()) {
								empEmg.setVisaNo(empImmigrationTO.getVisaNo());
							}
							if (empImmigrationTO.getVisaReviewStatus() != null
									&& !empImmigrationTO.getVisaReviewStatus()
											.isEmpty()) {
								empEmg.setVisaReviewStatus(empImmigrationTO
										.getVisaReviewStatus());
							}
							if (empImmigrationTO.getVisaStatus() != null
									&& !empImmigrationTO.getVisaStatus()
											.isEmpty()) {
								empEmg.setVisaStatus(empImmigrationTO
										.getVisaStatus());
							}
							Employee emp = new Employee();
							emp.setId(Integer.parseInt(employeeInfoEditForm
									.getEmployeeId()));
							empEmg.setEmployee(emp);
							empEmg.setIsActive(true);
							empEmg.setCreatedBy(employeeInfoEditForm
									.getUserId());
							empEmg.setCreatedDate(new Date());
							empEmg.setModifiedBy(employeeInfoEditForm
									.getUserId());
							empEmg.setLastModifiedDate(new Date());
							empImmigrations.add(empEmg);

						}
					}
				}
			}
		}
		return empImmigrations;
	}

	private Set<EmpDependents> getEmpDependantsBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpDependents> depen = new HashSet<EmpDependents>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpDependentses() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpDependentses().isEmpty()) {
			Iterator<EmpDependentsTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpDependentses().iterator();
			while (itr.hasNext()) {
				EmpDependentsTO to = (EmpDependentsTO) itr.next();
				EmpDependents dep = new EmpDependents();
				if ((to.getDependantName() != null && !to.getDependantName()
						.isEmpty())
						|| (to.getDependentRelationship() != null && !to
								.getDependentRelationship().isEmpty())) {
					if (to.getId() != null) {
						dep.setId(Integer.parseInt(to.getId()));
					}
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					dep.setEmployee(emp);
					dep.setCreatedBy(employeeInfoEditForm.getUserId());
					dep.setCreatedDate(new Date());
					dep.setModifiedBy(employeeInfoEditForm.getUserId());
					dep.setLastModifiedDate(new Date());
					dep.setIsActive(true);
					dep.setDependantDOB(CommonUtil.ConvertStringToDate(to
							.getDependantDOB()));
					dep.setDependentName(to.getDependantName());
					dep.setDependentRelationship(to.getDependentRelationship());
					depen.add(dep);
				}
			}
		}

		return depen;
	}

	private Set<EmpAcheivement> getEmpAchievementBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpAcheivement> depen = new HashSet<EmpAcheivement>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpAcheivements() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpAcheivements().isEmpty()) {
			Iterator<EmpAcheivementTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpAcheivements().iterator();
			while (itr.hasNext()) {
				EmpAcheivementTO to = (EmpAcheivementTO) itr.next();
				EmpAcheivement dep = new EmpAcheivement();
				if ((to.getAcheivementName() != null && !to
						.getAcheivementName().isEmpty())
						|| (to.getDetails() != null && !to.getDetails()
								.isEmpty())) {
					if (to.getId() > 0) {
						dep.setId(to.getId());
					}
					Employee emp = new Employee();
					emp.setId(Integer.parseInt(employeeInfoEditForm
							.getEmployeeId()));
					dep.setEmployee(emp);
					dep.setCreatedBy(employeeInfoEditForm.getUserId());
					dep.setCreatedDate(new Date());
					dep.setModifiedBy(employeeInfoEditForm.getUserId());
					dep.setLastModifiedDate(new Date());
					dep.setIsActive(true);
					dep.setAcheivementName(to.getAcheivementName());
					dep.setDetails(to.getDetails());
					depen.add(dep);
				}
			}
		}

		return depen;
	}

	private Set<EmpPayAllowanceDetails> getPayAllowanceBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpPayAllowanceDetails> empPayScalteDetailsSet = new HashSet<EmpPayAllowanceDetails>();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getPayscaleFixedTo() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getPayscaleFixedTo().isEmpty()) {
			Iterator<EmpAllowanceTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getPayscaleFixedTo().iterator();
			while (itr.hasNext()) {
				EmpAllowanceTO to = (EmpAllowanceTO) itr.next();
				EmpPayAllowanceDetails payScaleDetails = new EmpPayAllowanceDetails();
			
				if (to.getEmpPayAllowanceId() > 0) {
					payScaleDetails.setId(to.getEmpPayAllowanceId());
				}
				if (to.getId() > 0) {
					EmpAllowance empAllowance = new EmpAllowance();
					empAllowance.setId(to.getId());
					payScaleDetails.setEmpAllowance(empAllowance);
				}
				Employee emp = new Employee();
				emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
				payScaleDetails.setEmployee(emp);
				payScaleDetails.setAllowanceValue(to.getAllowanceName());
				payScaleDetails.setCreatedBy(employeeInfoEditForm.getUserId());
				payScaleDetails.setCreatedDate(new Date());
				payScaleDetails.setModifiedBy(employeeInfoEditForm.getUserId());
				payScaleDetails.setModifiedDate(new Date());
				payScaleDetails.setIsActive(true);
				empPayScalteDetailsSet.add(payScaleDetails);
			}
			
		}

		return empPayScalteDetailsSet;
	}

	private Set<EmpLeave> getLeavesBoObjects(
			EmployeeInfoEditForm employeeInfoEditForm) {
		Set<EmpLeave> depen = new HashSet<EmpLeave>();
		List<EmpLeaveAllotTO> leaveAllotTo = employeeInfoEditForm
				.getEmpLeaveAllotTo();
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpLeaveToList() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpLeaveToList().isEmpty()) {
			Iterator<EmpLeaveAllotTO> itr = employeeInfoEditForm
					.getEmployeeInfoTONew().getEmpLeaveToList().iterator();

			while (itr.hasNext()) {
				EmpLeaveAllotTO to = (EmpLeaveAllotTO) itr.next();
				EmpLeave dep = setLeaveToTOBO(to, employeeInfoEditForm);
				depen.add(dep);
			}
		}
		if (leaveAllotTo != null && !leaveAllotTo.isEmpty()) {
			Iterator<EmpLeaveAllotTO> itr = leaveAllotTo.iterator();
			while (itr.hasNext()) {
				EmpLeaveAllotTO to = (EmpLeaveAllotTO) itr.next();
				EmpLeave dep = setLeaveToTOBO(to, employeeInfoEditForm);
				depen.add(dep);
			}
		}
		employeeInfoEditForm.setEmpLeaveAllotTo(null);
		return depen;
	}

	public String getQueryByselectedEmpTypeId(String empTypeId)
			throws Exception {
		String query = "from EmpType e where e.isActive=true and e.id= "
				+ empTypeId;
		return query;
	}

	public String getLeaveByEmpTypeId(String empTypeId) throws Exception {
		String query = "from EmpLeaveAllotment r where r.isActive=true and r.empType.id="+ empTypeId+" order by r.empLeaveType.name";
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public List<EmpLeaveAllotTO> getLeaveByEmpId(String empTypeId, int year, int currentMonth, int month, EmployeeInfoEditForm empForm, int oldMonth) throws Exception {
		int year1=0;
		String query=null;
		String Query1=null;
		List<EmpLeaveAllotTO> leaveAllot=null;
		
		if(month==6 && currentMonth < month )
			{
		     year1=year-1;
			     Query1="select emp_leave_type.id, emp_leave_type.name, ifnull(emp_leave_allotment.allotted_leave, 0) as allotted_leave from emp_leave_type "+
					    "left join emp_leave_allotment on emp_leave_allotment.leave_type_id = emp_leave_type.id where (emp_leave_allotment.emp_type_id="+empTypeId+" or emp_leave_allotment.emp_type_id is null) "+ 
					    "and emp_leave_type.is_active=1 and emp_leave_allotment.is_active=1"; 
				     
			     query = "select emp.emp_leave_type_id, sum(emp.no_of_days),emp_leave_type.name as leaves_taken " +
			 		"from emp_apply_leave emp INNER JOIN  emp_leave_type emp_leave_type  ON (emp.emp_leave_type_id = emp_leave_type.id)" +
			 		" where emp.employee_id="+empForm.getEmployeeId()+" and emp.year="+year1+" and emp.is_active=1 and emp.is_canceled=0 and emp_leave_type.is_active=1 group by emp.emp_leave_type_id"; 
		        List<Object[]> list1=txn.getDataForQuery(Query1);
				List<Object[]> list2=txn.getDataForQuery(query);
			    leaveAllot=convertBotoTo(list1,list2,year1,month);
			}
		else
			{
			 Query1= "select emp_leave_type.id, emp_leave_type.name, ifnull(emp_leave_allotment.allotted_leave, 0) as allotted_leave from emp_leave_type "+
				    "left join emp_leave_allotment on emp_leave_allotment.leave_type_id = emp_leave_type.id where (emp_leave_allotment.emp_type_id="+empTypeId+" or emp_leave_allotment.emp_type_id is null) "+ 
				    "and emp_leave_type.is_active=1 "; 
			
			 query = "select emp.emp_leave_type_id, sum(emp.no_of_days),emp_leave_type.name as leaves_taken " +
			 		"from emp_apply_leave emp INNER JOIN  emp_leave_type emp_leave_type  ON (emp.emp_leave_type_id = emp_leave_type.id)" +
			 		" where emp.employee_id="+empForm.getEmployeeId()+" and emp.year="+year+" and emp.is_active=1 and emp.is_canceled=0 group by emp.emp_leave_type_id"; 
			 	List<Object[]> list1=txn.getDataForQuery(Query1);
				List<Object[]> list2=txn.getDataForQuery(query);
				leaveAllot=convertBotoTo(list1,list2,year,month);
			}
		Collections.sort(leaveAllot);
		return leaveAllot;
	}
	
	public String getQueryByselectedPayscaleId(String payScaleId)
			throws Exception {
		String query = "select p.scale from PayScaleBO p where p.isActive=true and p.id="
				+ payScaleId;
		return query;
	}

	

	/**
	 * @param empApplicantDetails
	 * @param objform
	 * @throws Exception
	 */
	public void convertBoToForm(Employee empApplicantDetails,
			EmployeeInfoEditForm objform) throws Exception {
		if (empApplicantDetails != null) {
			
			if (empApplicantDetails.getEligibilityTest() != null
					&& !empApplicantDetails.getEligibilityTest().isEmpty()) {
				 String input = empApplicantDetails.getEligibilityTest();
				
				 List<String> eligibilityList = new ArrayList<String>();
				  String[] splittArray = null;
				    if (input != null || !input.equalsIgnoreCase("")){
				         splittArray = input.split(",");
				         System.out.println(input + " " + splittArray);
				    }
				    for (int i = 0; i < splittArray.length; i++) {
				    	eligibilityList.add(splittArray[i]);
				    }
				    List<EligibilityTestTO> list = new ArrayList<EligibilityTestTO>();
				    EligibilityTestTO  to1 = new EligibilityTestTO();
				    to1.setEligibilityTest("None");
				    if(eligibilityList.contains("None")){
				    	to1.setTempChecked("on");
				    }
				    list.add(to1);
				    EligibilityTestTO  to2 = new EligibilityTestTO();
				    to2.setEligibilityTest("NET");
				    if(eligibilityList.contains("NET")){
				    	to2.setTempChecked("on");
				    }
				    list.add(to2);
				    EligibilityTestTO  to3 = new EligibilityTestTO();
				    to3.setEligibilityTest("SLET");
				    if(eligibilityList.contains("SLET")){
				    	to3.setTempChecked("on");
				    }
				    list.add(to3);
				    EligibilityTestTO  to4 = new EligibilityTestTO();
				    to4.setEligibilityTest("SET");
				    if(eligibilityList.contains("SET")){
				    	to4.setTempChecked("on");
				    }
				    list.add(to4);
				    EligibilityTestTO  to5 = new EligibilityTestTO();
				    to5.setEligibilityTest("OTHER");
				    if(eligibilityList.contains("OTHER")){
				    	to5.setTempChecked("on");
				    }
				    list.add(to5);
				    objform.setEligibilityList(list);
				  /*  for (int i = 0; i < splittArray.length; i++) {
				    	if(splittArray[i].equalsIgnoreCase("NET"))
				    	{
				    	objform.setEligibilityTestNET(splittArray[i]);
				    	}
				    	if(splittArray[i].equalsIgnoreCase("SLET"))
				    	{
				    	objform.setEligibilityTestSLET(splittArray[i]);
				    	 	}
				    	if(splittArray[i].equalsIgnoreCase("SET"))
				    	{
				    	objform.setEligibilityTestSET(splittArray[i]);
				    	}
				    	if(splittArray[i].equalsIgnoreCase("None"))
				    	{
				    	objform.setEligibilityTestNone(splittArray[i]);
				    	}
				    }*/
				
			}
			if(empApplicantDetails.getEligibilityTestOther()!=null && !empApplicantDetails.getEligibilityTestOther().trim().isEmpty()){
				objform.setEligibilityTestOther("OTHER");
				objform.setOtherEligibilityTestValue(empApplicantDetails.getEligibilityTestOther());
			}
			if(empApplicantDetails.getIndustryFunctionalArea()!=null && !empApplicantDetails.getIndustryFunctionalArea().trim().isEmpty()){
				objform.setIndustryFunctionalArea(empApplicantDetails.getIndustryFunctionalArea());
			}
			
			if (empApplicantDetails.getReservationCategory() != null
					&& !empApplicantDetails.getReservationCategory().isEmpty()) {
				if ("GM".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("SC".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("ST".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("OBC".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("Minority".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("Person With Disability"
						.equalsIgnoreCase(empApplicantDetails
								.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
					 if(empApplicantDetails.getHandicappedDescription()!=null && !empApplicantDetails.getHandicappedDescription().trim().isEmpty())
						 objform.setHandicappedDescription(empApplicantDetails.getHandicappedDescription());	
					
				}
			}

			// //...............................................................Photo.....................................................

			if (empApplicantDetails.getEmpImages() != null && !empApplicantDetails.getEmpImages().isEmpty()) {
				Iterator<EmpImages> itr=empApplicantDetails.getEmpImages().iterator();
				while (itr.hasNext()) {
					EmpImages bo =itr.next();
					
					if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
						objform.setPhotoBytes(bo.getEmpPhoto());
						objform.setEmpImageId(String.valueOf(bo.getId()));
						break;
					}
				}
				
			}
			// //...............................................................Photo.....................................................
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
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getIsPunchingExcemption()))) {
				String Value = String.valueOf(empApplicantDetails
						.getIsPunchingExcemption());
				if (Value.equals("true"))
					objform.setIsPunchingExcemption("1");
				else
					objform.setIsPunchingExcemption("0");

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

			if (StringUtils.isNotEmpty(empApplicantDetails.getSmartCardNo())
					&& empApplicantDetails.getSmartCardNo() != null) {
				objform.setSmartCardNo(empApplicantDetails.getSmartCardNo());
			}

			Map<String, String> payScaleMap = txn.getPayScaleMap(objform);
			if (payScaleMap != null) {
				objform.setPayScaleMap(payScaleMap);
			}

			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getActive()))) {
				String Value = String.valueOf(empApplicantDetails.getActive());
				if (Value.equals("true"))
					objform.setActive("1");
				else
					objform.setActive("0");

			}
			// giri
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getDisplayInWebsite()))) {
				String Value = String.valueOf(empApplicantDetails.getDisplayInWebsite());
				if (Value.equals("true"))
					objform.setDisplayInWebsite("1");
				else
					objform.setDisplayInWebsite("0");

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
			if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getIsSameAddress()))) {
				String Value = String.valueOf(empApplicantDetails
						.getIsSameAddress());
				if (Value.equals("true"))
					objform.setSameAddress("true");
				else
					objform.setSameAddress("false");

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
			if (StringUtils.isNotEmpty(empApplicantDetails.getFirstName())
					&& empApplicantDetails.getFirstName() != null) {
				objform.setName(empApplicantDetails.getFirstName().toUpperCase());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getUid())
					&& empApplicantDetails.getUid() != null) {
				objform.setuId(empApplicantDetails.getUid());
			}
			if (empApplicantDetails.getTitleId() != null
					&& empApplicantDetails.getTitleId().getId() > 0) {
				objform.setTitleId(String.valueOf(empApplicantDetails
						.getTitleId().getId()));
			}
			if (empApplicantDetails.getDepartment() != null
					&& empApplicantDetails.getDepartment().getId() > 0) {
				objform.setDepartmentId(String.valueOf(empApplicantDetails
						.getDepartment().getId()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getCode())
					&& empApplicantDetails.getCode() != null) {
				objform.setCode(empApplicantDetails.getCode());
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
			if (empApplicantDetails.getNationality() != null
					&& empApplicantDetails.getNationality().getId() > 0) {
				objform.setNationalityId(String.valueOf(empApplicantDetails
						.getNationality().getId()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus())
					&& empApplicantDetails.getMaritalStatus() != null) {
				objform.setMaritalStatus(String.valueOf(empApplicantDetails
						.getMaritalStatus()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getBloodGroup())
					&& empApplicantDetails.getBloodGroup() != null) {
				objform.setBloodGroup(String.valueOf(empApplicantDetails
						.getBloodGroup()));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getPanNo())
					&& empApplicantDetails.getPanNo() != null) {
				objform
						.setPanno(String
								.valueOf(empApplicantDetails.getPanNo()));
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
			if (StringUtils.isNotEmpty(empApplicantDetails.getBankAccNo())
					&& empApplicantDetails.getBankAccNo() != null) {
				objform.setBankAccNo(empApplicantDetails.getBankAccNo());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getPfNo())
					&& empApplicantDetails.getPfNo() != null) {
				objform.setPfNo(empApplicantDetails.getPfNo());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getFourWheelerNo())
					&& empApplicantDetails.getFourWheelerNo() != null) {
				objform
						.setFourWheelerNo(empApplicantDetails
								.getFourWheelerNo());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getTwoWheelerNo())
					&& empApplicantDetails.getTwoWheelerNo() != null) {
				objform.setTwoWheelerNo(empApplicantDetails.getTwoWheelerNo());
			}

			if (empApplicantDetails.getReligionId() != null
					&& empApplicantDetails.getReligionId().getId() > 0) {
				objform.setReligionId(String.valueOf(empApplicantDetails
						.getReligionId().getId()));
			}
			if (empApplicantDetails.getEmptype() != null
					&& empApplicantDetails.getEmptype().getId() > 0) {
				objform.setEmptypeId(String.valueOf(empApplicantDetails
						.getEmptype().getId()));
			}
			if (empApplicantDetails.getEmpQualificationLevel() != null
					&& empApplicantDetails.getEmpQualificationLevel().getId() > 0) {
				objform.setQualificationId(String.valueOf(empApplicantDetails
						.getEmpQualificationLevel().getId()));
			}
			/* added by sudhir */
			if(empApplicantDetails.getExtensionNumber()!=null
					&& !empApplicantDetails.getExtensionNumber().isEmpty()){
				objform.setExtensionNumber(empApplicantDetails.getExtensionNumber());
			}
			/* ----------------*/
			// Modification
			// ........................................................

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
					.isNotEmpty(empApplicantDetails.getDesignationName())
					&& empApplicantDetails.getDesignationName() != null) {
				objform
						.setDesignation(empApplicantDetails
								.getDesignationName());
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
			if (empApplicantDetails.getDepartment() != null
					&& empApplicantDetails.getDepartment().getId() > 0) {
				objform.setDepartmentId(String.valueOf(empApplicantDetails
						.getDepartment().getId()));
			}
			if (empApplicantDetails.getEmployeeByReportToId() != null
					&& empApplicantDetails.getEmployeeByReportToId().getId() > 0) {
				objform.setReportToId(String.valueOf(empApplicantDetails
						.getEmployeeByReportToId().getId()));
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
			if (empApplicantDetails.getDesignation() != null
					&& empApplicantDetails.getDesignation().getId() > 0) {
				objform.setDesignationPfId(String.valueOf(empApplicantDetails
						.getDesignation().getId()));
			}
			if (empApplicantDetails.getEmpSubjectArea() != null
					&& empApplicantDetails.getEmpSubjectArea().getId() > 0) {
				objform.setEmpSubjectAreaId(String.valueOf(empApplicantDetails
						.getEmpSubjectArea().getId()));
			}
			if (empApplicantDetails.getDob() != null
					&& !empApplicantDetails.getDob().toString().isEmpty()) {
				objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getDob().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
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
			if (empApplicantDetails.getDoj() != null
					&& !empApplicantDetails.getDoj().toString().isEmpty()) {
				objform.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getDoj().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
			}
			if (empApplicantDetails.getRejoinDate() != null
					&& !empApplicantDetails.getRejoinDate().toString()
							.isEmpty()) {
				objform.setRejoinDate(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getRejoinDate().toString(),
						"yyyy-mm-dd", "dd/mm/yyyy"));
			}
			if (empApplicantDetails.getRetirementDate() != null
					&& !empApplicantDetails.getRetirementDate().toString()
							.isEmpty()) {
				objform.setRetirementDate(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getRetirementDate().toString(),
						"yyyy-mm-dd", "dd/mm/yyyy"));
			}
			if (empApplicantDetails.getTotalExpMonths() != null
					&& !empApplicantDetails.getTotalExpMonths().isEmpty()) {
				objform.setExpMonths(String.valueOf(empApplicantDetails
						.getTotalExpMonths()));
			}
			if (empApplicantDetails.getTotalExpYear() != null
					&& !empApplicantDetails.getTotalExpYear().isEmpty()) {
				objform.setExpYears(String.valueOf(empApplicantDetails
						.getTotalExpYear()));
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
			/*
			 * if(StringUtils.isNotEmpty(empApplicantDetails.getHalfDayEndTime())
			 * ){
			 * objform.setHalfDayEndTime(empApplicantDetails.getHalfDayEndTime
			 * ()); }
			 * if(StringUtils.isNotEmpty(empApplicantDetails.getHalfDayStartTime
			 * ())){
			 * objform.setHalfDayStartTime(empApplicantDetails.getHalfDayStartTime
			 * ()); }
			 */
			if (StringUtils.isNotEmpty(empApplicantDetails
					.getHighQualifForAlbum())
					&& empApplicantDetails.getHighQualifForAlbum() != null) {
				objform.setHighQualifForAlbum(empApplicantDetails
						.getHighQualifForAlbum());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getOtherInfo())
					&& empApplicantDetails.getOtherInfo() != null) {
				objform.setOtherInfo(empApplicantDetails.getOtherInfo());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getPanNo())
					&& empApplicantDetails.getPanNo() != null) {
				objform.setPanno(empApplicantDetails.getPanNo());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getLicGratuityNo())
					&& empApplicantDetails.getLicGratuityNo() != null) {
				objform.setLicGratuityNo(empApplicantDetails.getLicGratuityNo());
			}
			if (empApplicantDetails.getLicGratuityDate() != null) {
				objform.setLicGratuityDate(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getLicGratuityDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getNomineePfNo())
					&& empApplicantDetails.getNomineePfNo() != null) {
				objform.setNomineePfNo(empApplicantDetails.getNomineePfNo());
			}
			if (empApplicantDetails.getNomineePfDate() != null) {
				objform.setNomineePfDate(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getNomineePfDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
			}
			if (empApplicantDetails.getPayScaleId() != null
					&& empApplicantDetails.getPayScaleId().getId() > 0) {
				objform.setPayScaleId(String.valueOf(empApplicantDetails
						.getPayScaleId().getId()));
			}

			if (StringUtils
					.isNotEmpty(empApplicantDetails.getReasonOfLeaving())
					&& empApplicantDetails.getReasonOfLeaving() != null) {
				objform.setReasonOfLeaving(empApplicantDetails
						.getReasonOfLeaving());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails.getRelationship())
					&& empApplicantDetails.getRelationship() != null) {
				objform.setEmContactRelationship(empApplicantDetails
						.getRelationship());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails
					.getRelevantExpMonths())
					&& empApplicantDetails.getRelevantExpMonths() != null) {
				objform.setRelevantExpMonths(empApplicantDetails
						.getRelevantExpMonths());
			}
			if (StringUtils.isNotEmpty(empApplicantDetails
					.getRelevantExpYears())
					&& empApplicantDetails.getRelevantExpYears() != null) {
				objform.setRelevantExpYears(empApplicantDetails
						.getRelevantExpYears());
			}
			/*
			 * if(StringUtils.isNotEmpty(empApplicantDetails.getSaturdayTimeOut()
			 * )){
			 * objform.setSaturdayTimeOut(empApplicantDetails.getSaturdayTimeOut
			 * ()); }
			 */
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

			if (empApplicantDetails.getTimeIn() != null
					&& !empApplicantDetails.getTimeIn().isEmpty()) {
				objform.setTimeIn(empApplicantDetails.getTimeIn().substring(0,
						2));
				objform.setTimeInMin(empApplicantDetails.getTimeIn().substring(
						3, 5));
			}

			if (empApplicantDetails.getTimeInEnds() != null
					&& !empApplicantDetails.getTimeInEnds().isEmpty()) {
				objform.setTimeInEnds(empApplicantDetails.getTimeInEnds()
						.substring(0, 2));
				objform.setTimeInEndMIn(empApplicantDetails.getTimeInEnds()
						.substring(3, 5));
			}

			if (empApplicantDetails.getTimeOut() != null
					&& !empApplicantDetails.getTimeOut().isEmpty()) {
				objform.setTimeOut(empApplicantDetails.getTimeOut().substring(
						0, 2));
				objform.setTimeOutMin(empApplicantDetails.getTimeOut()
						.substring(3, 5));
			}

			if (empApplicantDetails.getSaturdayTimeOut() != null
					&& !empApplicantDetails.getSaturdayTimeOut().isEmpty()) {
				objform.setSaturdayTimeOut(empApplicantDetails
						.getSaturdayTimeOut().substring(0, 2));
				objform.setSaturdayTimeOutMin(empApplicantDetails
						.getSaturdayTimeOut().substring(3, 5));
			}

			if (empApplicantDetails.getHalfDayStartTime() != null
					&& !empApplicantDetails.getHalfDayStartTime().isEmpty()) {
				objform.setHalfDayStartTime(empApplicantDetails
						.getHalfDayStartTime().substring(0, 2));
				objform.setHalfDayStartTimeMin(empApplicantDetails
						.getHalfDayStartTime().substring(3, 5));
			}

			if (empApplicantDetails.getHalfDayEndTime() != null
					&& !empApplicantDetails.getHalfDayEndTime().isEmpty()) {
				objform.setHalfDayEndTime(empApplicantDetails
						.getHalfDayEndTime().substring(0, 2));
				objform.setHalfDayEndTimeMin(empApplicantDetails
						.getHalfDayEndTime().substring(3, 5));
			}

			/*
			 * if(empApplicantDetails.getTimeIn()!=null &&
			 * !empApplicantDetails.getTimeIn().isEmpty()){
			 * objform.setTimeIn(empApplicantDetails
			 * .getTimeIn().substring(0,2));
			 * objform.setTimeInMin(empApplicantDetails
			 * .getTimeIn().substring(3,5)); }
			 * 
			 * if(empApplicantDetails.getTimeInEnds()!=null &&
			 * !empApplicantDetails.getTimeInEnds().isEmpty()){
			 * objform.setTimeInEnds
			 * (empApplicantDetails.getTimeInEnds().substring(0,2));
			 * objform.setTimeInEndMIn
			 * (empApplicantDetails.getTimeInEnds().substring(3,5)); }
			 * 
			 * if(empApplicantDetails.getTimeOut()!=null &&
			 * !empApplicantDetails.getTimeOut().isEmpty()){
			 * objform.setTimeOut(empApplicantDetails
			 * .getTimeOut().substring(0,2));
			 * objform.setTimeOutMin(empApplicantDetails
			 * .getTimeOut().substring(3,5)); }
			 * 
			 * if(empApplicantDetails.getSaturdayTimeOut()!=null &&
			 * !empApplicantDetails.getSaturdayTimeOut().isEmpty()){
			 * objform.setSaturdayTimeOut
			 * (empApplicantDetails.getSaturdayTimeOut().substring(0,2));
			 * objform
			 * .setSaturdayTimeOutMin(empApplicantDetails.getSaturdayTimeOut
			 * ().substring(3,5)); }
			 * 
			 * if(empApplicantDetails.getHalfDayStartTime()!=null &&
			 * !empApplicantDetails.getHalfDayStartTime().isEmpty()){
			 * objform.setHalfDayStartTime
			 * (empApplicantDetails.getHalfDayStartTime().substring(0,2));
			 * objform
			 * .setHalfDayStartTimeMin(empApplicantDetails.getHalfDayStartTime
			 * ().substring(3,5)); }
			 * 
			 * if(empApplicantDetails.getHalfDayEndTime()!=null &&
			 * !empApplicantDetails.getHalfDayEndTime().isEmpty()){
			 * objform.setHalfDayEndTime
			 * (empApplicantDetails.getHalfDayEndTime().substring(0,2));
			 * objform.
			 * setHalfDayEndTimeMin(empApplicantDetails.getHalfDayEndTime
			 * ().substring(3,5)); }
			 */

			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
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
			if (empApplicantDetails.getEducationalDetailsSet() != null) {
				List<EmpQualificationLevelTo> fixed = null;
				if (objform.getEmployeeInfoTONew() != null) {
					if (objform.getEmployeeInfoTONew()
							.getEmpQualificationFixedTo() != null) {
						fixed = objform.getEmployeeInfoTONew()
								.getEmpQualificationFixedTo();
					}
					List<EmpQualificationLevelTo> level = new ArrayList<EmpQualificationLevelTo>();
					Set<EmpEducationalDetails> empEducationalDetailsSet = empApplicantDetails
							.getEducationalDetailsSet();
					Iterator<EmpEducationalDetails> iterator = empEducationalDetailsSet
							.iterator();
					while (iterator.hasNext()) {
						EmpEducationalDetails empEducationalDetails = iterator
								.next();
						if (empEducationalDetails != null) {
							boolean flag = false;
							if (empEducationalDetails
									.getEmpQualificationLevel() != null
									&& empEducationalDetails
											.getEmpQualificationLevel()
											.isFixedDisplay() != null) {
								flag = empEducationalDetails
										.getEmpQualificationLevel()
										.isFixedDisplay();
								if (flag && fixed != null) {
									Iterator<EmpQualificationLevelTo> iterator2 = fixed
											.iterator();
									while (iterator2.hasNext()) {
										EmpQualificationLevelTo empQualificationLevelTo = iterator2
												.next();
										if (empQualificationLevelTo != null
												&& StringUtils
														.isNotEmpty(empQualificationLevelTo
																.getEducationId())) {
											if (empEducationalDetails
													.getEmpQualificationLevel()
													.getId() > 0)
												if (empQualificationLevelTo
														.getEducationId()
														.equalsIgnoreCase(
																String.valueOf(empEducationalDetails
																				.getEmpQualificationLevel()
																				.getId()))) {

													if (empEducationalDetails
															.getId() > 0) {
														empQualificationLevelTo
																.setEducationDetailsID(empEducationalDetails
																		.getId());
													}
													
													
													if (StringUtils
															.isNotEmpty(empEducationalDetails
																	.getCourse())) {
														empQualificationLevelTo
																.setCourse(empEducationalDetails
																		.getCourse());
													}

													if (StringUtils
															.isNotEmpty(empEducationalDetails
																	.getSpecialization())) {
														empQualificationLevelTo
																.setSpecialization(empEducationalDetails
																		.getSpecialization());
													}

													if (StringUtils
															.isNotEmpty(empEducationalDetails
																	.getGrade())) {
														empQualificationLevelTo
																.setGrade(empEducationalDetails
																		.getGrade());
													}

													if (StringUtils
															.isNotEmpty(empEducationalDetails
																	.getInstitute())) {
														empQualificationLevelTo
																.setInstitute(empEducationalDetails
																		.getInstitute());
													}

													if (empEducationalDetails
															.getYearOfCompletion() > 0) {
														empQualificationLevelTo
																.setYearOfComp(String
																		.valueOf(empEducationalDetails
																				.getYearOfCompletion()));
													}
												}
										}
									}
								} else {
									EmpQualificationLevelTo empQualificationLevelTo = new EmpQualificationLevelTo();

									if (empEducationalDetails.getId() > 0) {
										empQualificationLevelTo
												.setEducationDetailsID(empEducationalDetails
														.getId());
									}
									if (StringUtils
											.isNotEmpty(empEducationalDetails
													.getCourse())) {
										empQualificationLevelTo
												.setCourse(empEducationalDetails
														.getCourse());
									}

									if (StringUtils
											.isNotEmpty(empEducationalDetails
													.getSpecialization())) {
										empQualificationLevelTo
												.setSpecialization(empEducationalDetails
														.getSpecialization());
									}

									if (StringUtils
											.isNotEmpty(empEducationalDetails
													.getGrade())) {
										empQualificationLevelTo
												.setGrade(empEducationalDetails
														.getGrade());
									}

									if (StringUtils
											.isNotEmpty(empEducationalDetails
													.getInstitute())) {
										empQualificationLevelTo
												.setInstitute(empEducationalDetails
														.getInstitute());
									}

									if (empEducationalDetails
											.getYearOfCompletion() > 0) {
										empQualificationLevelTo
												.setYear(String
														.valueOf(empEducationalDetails
																.getYearOfCompletion()));
									}
									if (empEducationalDetails
											.getEmpQualificationLevel().getId() > 0) {
										empQualificationLevelTo
												.setEducationId(String
														.valueOf(empEducationalDetails
																.getEmpQualificationLevel()
																.getId()));
									}
									level.add(empQualificationLevelTo);
								}

							}
						}
					}
					
					objform.getEmployeeInfoTONew().setEmpQualificationLevelTos(
							level);
				}
				else
				{
					if(objform.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
						Iterator<EmpQualificationLevelTo> iterator=objform.getEmployeeInfoTONew().getEmpQualificationFixedTo().iterator();
						while(iterator.hasNext()){
							EmpQualificationLevelTo empQualificationLevelTo=iterator.next();
							if(empQualificationLevelTo!=null){
								empQualificationLevelTo.setCourse("");
								empQualificationLevelTo.setSpecialization("");
								empQualificationLevelTo.setGrade("");
								empQualificationLevelTo.setInstitute("");
								empQualificationLevelTo.setYearOfComp("");
							}
						}
						
					}
				}
			}
			if(empApplicantDetails.getResearchPapersRefereed()!=null){
				objform.setResearchPapersRefereed(empApplicantDetails.getResearchPapersRefereed().toString());
			}
			if(empApplicantDetails.getResearchPapersNonRefereed()!=null){
				objform.setResearchPapersNonRefereed(empApplicantDetails.getResearchPapersNonRefereed().toString());
			}
			if(empApplicantDetails.getResearchPapersProceedings()!=null){
				objform.setResearchPapersProceedings(empApplicantDetails.getResearchPapersProceedings().toString());
			}
			if(empApplicantDetails.getInternationalBookPublications()!=null){
				objform.setInternationalPublications(empApplicantDetails.getInternationalBookPublications().toString());
			}
			if(empApplicantDetails.getNationalBookPublications()!=null){
				objform.setNationalPublications(empApplicantDetails.getNationalBookPublications().toString());
			}
			if(empApplicantDetails.getLocalBookPublications()!=null){
				objform.setLocalPublications(empApplicantDetails.getLocalBookPublications().toString());
			}
			if(empApplicantDetails.getChaptersEditedBooksInternational()!=null){
				objform.setInternational(empApplicantDetails.getChaptersEditedBooksInternational().toString());
			}
			if(empApplicantDetails.getChaptersEditedBooksNational()!=null){
				objform.setNational(empApplicantDetails.getChaptersEditedBooksNational().toString());
			}
			if(empApplicantDetails.getMajorSponseredProjects()!=null){
				objform.setMajorProjects(empApplicantDetails.getMajorSponseredProjects().toString());
			}
			if(empApplicantDetails.getMinorSponseredProjects()!=null){
				objform.setMinorProjects(empApplicantDetails.getMinorSponseredProjects().toString());
			}
			if(empApplicantDetails.getConsultancy1SponseredProjects()!=null){
				objform.setConsultancyPrjects1(empApplicantDetails.getConsultancy1SponseredProjects().toString());
			}
			if(empApplicantDetails.getConsultancy2SponseredProjects()!=null){
				objform.setConsultancyProjects2(empApplicantDetails.getConsultancy2SponseredProjects().toString());
			}
			if(empApplicantDetails.getPhdResearchGuidance()!=null){
				objform.setPhdResearchGuidance(empApplicantDetails.getPhdResearchGuidance().toString());
			}
			if(empApplicantDetails.getMphilResearchGuidance()!=null){
				objform.setMphilResearchGuidance(empApplicantDetails.getMphilResearchGuidance().toString());
			}
			if(empApplicantDetails.getTrainingAttendedFdp1Weeks()!=null){
				objform.setFdp1Training(empApplicantDetails.getTrainingAttendedFdp1Weeks().toString());
			}
			if(empApplicantDetails.getTrainingAttendedFdp2Weeks()!=null){
				objform.setFdp2Training(empApplicantDetails.getTrainingAttendedFdp2Weeks().toString());
			}
			if(empApplicantDetails.getInternationalConferencePresentaion()!=null){
				objform.setInternationalConference(empApplicantDetails.getInternationalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getNationalConferencePresentaion()!=null){
				objform.setNationalConference(empApplicantDetails.getNationalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getLocalConferencePresentaion()!=null){
				objform.setLocalConference(empApplicantDetails.getLocalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getRegionalConferencePresentaion()!=null){
				objform.setRegionalConference(empApplicantDetails.getRegionalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getFatherName()!=null){
				objform.setFatherName(empApplicantDetails.getFatherName());
			}
			if(empApplicantDetails.getMotherName()!=null){
				objform.setMotherName(empApplicantDetails.getMotherName());
			}
			if(empApplicantDetails.getDeputationToDepartmentId()!=null){
				objform.setDeputationDepartmentId(String.valueOf(empApplicantDetails.getDeputationToDepartmentId().getId()));
			}
			if(empApplicantDetails.getAppointmentLetterDate()!=null){
				objform.setAppointmentLetterDate(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getAppointmentLetterDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
			}
			if(empApplicantDetails.getReferenceNumberForAppointment()!=null){
				objform.setReferenceNumberForAppointment(empApplicantDetails.getReferenceNumberForAppointment());
			}
			if(empApplicantDetails.getReleavingOrderDate()!=null){
				objform.setReleavingOrderDate(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getReleavingOrderDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
			}
			if(empApplicantDetails.getReferenceNubmerforReleaving()!=null){
				objform.setReferenceNumberForReleaving(empApplicantDetails.getReferenceNubmerforReleaving());
			}
			if(empApplicantDetails.getAdditionalRemarks()!=null){
				objform.setAdditionalRemarks(empApplicantDetails.getAdditionalRemarks());
			}
			if (empApplicantDetails.getRefresherCourse()!=null) {
				objform.setRefresherCourse(empApplicantDetails.getRefresherCourse());
			}
			if (empApplicantDetails.getOrientationCourse()!=null) {
				objform.setOrientationCourse(empApplicantDetails.getOrientationCourse());
			}
		}
		if (empApplicantDetails.getEmpAcheivements() != null) {
			Set<EmpAcheivement> empAcheivements = empApplicantDetails
					.getEmpAcheivements();
			if (empAcheivements != null && !empAcheivements.isEmpty()) {
				Iterator<EmpAcheivement> iterator = empAcheivements.iterator();
				List<EmpAcheivementTO> empAcheivementTOs = new ArrayList<EmpAcheivementTO>();

				while (iterator.hasNext()) {
					EmpAcheivement empAcheiv = iterator.next();
					if (empAcheiv != null) {
						EmpAcheivementTO empAcheivementTO = new EmpAcheivementTO();

						if (empAcheiv.getId() > 0) {
							empAcheivementTO.setId(empAcheiv.getId());
						}
						if (StringUtils.isNotEmpty(empAcheiv
								.getAcheivementName())) {
							empAcheivementTO.setAcheivementName(empAcheiv
									.getAcheivementName());
						}

						if (StringUtils.isNotEmpty(empAcheiv.getDetails())) {
							empAcheivementTO.setDetails(empAcheiv.getDetails());
						}

						empAcheivementTOs.add(empAcheivementTO);

					}
				}
				objform.getEmployeeInfoTONew().setEmpAcheivements(
						empAcheivementTOs);
			} else {

				List<EmpAcheivementTO> flist = new ArrayList<EmpAcheivementTO>();
				EmpAcheivementTO empAcheivementTO = new EmpAcheivementTO();
				empAcheivementTO.setAcheivementName("");
				empAcheivementTO.setDetails("");
				objform.setAchievementListSize(String.valueOf(flist.size()));
				flist.add(empAcheivementTO);
				objform.getEmployeeInfoTONew().setEmpAcheivements(flist);
			}
		}

		if (empApplicantDetails.getEmpDependentses() != null) {
			Set<EmpDependents> empDependents = empApplicantDetails
					.getEmpDependentses();
			if (empDependents != null && !empDependents.isEmpty()) {
				Iterator<EmpDependents> iterator = empDependents.iterator();
				List<EmpDependentsTO> empDependentsTOs = new ArrayList<EmpDependentsTO>();

				while (iterator.hasNext()) {
					EmpDependents empDepen = iterator.next();
					if (empDepen != null) {
						EmpDependentsTO empDepenTOs = new EmpDependentsTO();

						if (empDepen.getId() > 0) {
							empDepenTOs.setId(String.valueOf(empDepen.getId()));
						}
						if (StringUtils.isNotEmpty(empDepen.getDependentName())) {
							empDepenTOs.setDependantName(empDepen
									.getDependentName());
						}

						if (StringUtils.isNotEmpty(empDepen
								.getDependentRelationship())) {
							empDepenTOs.setDependentRelationship(empDepen
									.getDependentRelationship());
						}

						if (empDepen.getDependantDOB() != null) {
							empDepenTOs.setDependantDOB(CommonUtil
									.ConvertStringToDateFormat(empDepen
											.getDependantDOB().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
						}

						empDependentsTOs.add(empDepenTOs);

					}
				}
				objform.getEmployeeInfoTONew().setEmpDependentses(
						empDependentsTOs);
			} else {
				List<EmpDependentsTO> empDependentses = new ArrayList<EmpDependentsTO>();
				EmpDependentsTO empDependentsTO = new EmpDependentsTO();
				empDependentsTO.setDependantDOB("");
				empDependentsTO.setDependantName("");
				empDependentsTO.setDependentRelationship("");
				objform.setDependantsListSize(String.valueOf(empDependentses
						.size()));
				empDependentses.add(empDependentsTO);

				objform.getEmployeeInfoTONew().setEmpDependentses(
						empDependentses);

			}
		}
		
		if (empApplicantDetails.getPfGratuityNominees() != null) {
			Set<PfGratuityNominees> pfGratuityNominees = empApplicantDetails.getPfGratuityNominees();
			if (pfGratuityNominees != null && !pfGratuityNominees.isEmpty()) {
				Iterator<PfGratuityNominees> iterator = pfGratuityNominees.iterator();
				List<PfGratuityNomineesTO> PfGratuityTOs = new ArrayList<PfGratuityNomineesTO>();

				while (iterator.hasNext()) {
					PfGratuityNominees pfGratuitys = iterator.next();
					if (pfGratuitys != null) {
						PfGratuityNomineesTO pfGratuityNomineesTO = new PfGratuityNomineesTO();

						if (pfGratuitys.getId() > 0) {
							pfGratuityNomineesTO.setId(String.valueOf(pfGratuitys.getId()));
						}
						if (StringUtils.isNotEmpty(pfGratuitys.getNameAdressNominee()) && pfGratuitys.getNameAdressNominee()!=null) {
							pfGratuityNomineesTO.setNameAdressNominee(pfGratuitys.getNameAdressNominee());
						}
						if (StringUtils.isNotEmpty(pfGratuitys.getMemberRelationship()) && pfGratuitys.getMemberRelationship()!=null) {
							pfGratuityNomineesTO.setMemberRelationship(pfGratuitys.getMemberRelationship());
						}
						if (pfGratuitys.getDobMember() != null) {
							pfGratuityNomineesTO.setDobMember(CommonUtil.ConvertStringToDateFormat(pfGratuitys.getDobMember().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if (StringUtils.isNotEmpty(pfGratuitys.getShare()) && pfGratuitys.getShare()!=null) {
							pfGratuityNomineesTO.setShare(pfGratuitys.getShare());
						}
						if (StringUtils.isNotEmpty(pfGratuitys.getNameAdressGuardian()) && pfGratuitys.getNameAdressGuardian()!=null) {
							pfGratuityNomineesTO.setNameAdressGuardian(pfGratuitys.getNameAdressGuardian());
						}
						PfGratuityTOs.add(pfGratuityNomineesTO);

					}
				}
				objform.getEmployeeInfoTONew().setPfGratuityNominee(
						PfGratuityTOs);
			} else {
				List<PfGratuityNomineesTO> pfGratuityNomineesTOs = new ArrayList<PfGratuityNomineesTO>();
				PfGratuityNomineesTO pfGratuityNomineesTO = new PfGratuityNomineesTO();
				pfGratuityNomineesTO.setNameAdressNominee("");
				pfGratuityNomineesTO.setMemberRelationship("");
				pfGratuityNomineesTO.setDobMember("");
				pfGratuityNomineesTO.setShare("");
				pfGratuityNomineesTO.setNameAdressGuardian("");
				objform.setDependantsListSize(String.valueOf(pfGratuityNomineesTOs.size()));
				pfGratuityNomineesTOs.add(pfGratuityNomineesTO);
				objform.getEmployeeInfoTONew().setPfGratuityNominee(pfGratuityNomineesTOs);
			}
		}
		

		if (empApplicantDetails.getEmpFeeConcession() != null) {
			Set<EmpFeeConcession> empFeeConcession = empApplicantDetails
					.getEmpFeeConcession();
			if (empFeeConcession != null && !empFeeConcession.isEmpty()) {
				Iterator<EmpFeeConcession> iterator = empFeeConcession
						.iterator();
				List<EmpFeeConcessionTO> empFeeConcessionTOs = new ArrayList<EmpFeeConcessionTO>();

				while (iterator.hasNext()) {
					EmpFeeConcession empFeeConc = iterator.next();
					if (empFeeConc != null) {
						EmpFeeConcessionTO empFeeConcTO = new EmpFeeConcessionTO();
						if (empFeeConc.getId() > 0) {
							empFeeConcTO.setId(empFeeConc.getId());
						}
						if (StringUtils.isNotEmpty(empFeeConc
								.getFeeConcessionDetails())) {
							empFeeConcTO.setFeeConcessionDetails(empFeeConc
									.getFeeConcessionDetails());
						}

						if (StringUtils.isNotEmpty(empFeeConc
								.getFeeConcessionAmount())) {
							empFeeConcTO.setFeeConcessionAmount(empFeeConc
									.getFeeConcessionAmount());
						}

						if (empFeeConc.getFeeConcessionDate() != null) {
							empFeeConcTO.setFeeConcessionDate(CommonUtil
									.ConvertStringToDateFormat(empFeeConc
											.getFeeConcessionDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
						}

						empFeeConcessionTOs.add(empFeeConcTO);

					}
				}
				objform.getEmployeeInfoTONew().setEmpFeeConcession(
						empFeeConcessionTOs);
			} else {
				List<EmpFeeConcessionTO> list = new ArrayList<EmpFeeConcessionTO>();
				EmpFeeConcessionTO empFeeConcessionTO = new EmpFeeConcessionTO();
				empFeeConcessionTO.setFeeConcessionAmount("");
				empFeeConcessionTO.setFeeConcessionDate("");
				empFeeConcessionTO.setFeeConcessionDetails("");
				objform.setFeeListSize(String.valueOf(list.size()));
				list.add(empFeeConcessionTO);
				objform.getEmployeeInfoTONew().setEmpFeeConcession(list);

			}
		}

		if (empApplicantDetails.getEmpFinancial() != null) {
			Set<EmpFinancial> empFinancial = empApplicantDetails
					.getEmpFinancial();
			if (empFinancial != null && !empFinancial.isEmpty()) {
				Iterator<EmpFinancial> iterator = empFinancial.iterator();
				List<EmpFinancialTO> empFinancialTOs = new ArrayList<EmpFinancialTO>();

				while (iterator.hasNext()) {
					EmpFinancial empFinancial2 = iterator.next();
					if (empFinancial2 != null) {
						EmpFinancialTO empFinancialTO = new EmpFinancialTO();
						if (empFinancial2.getId() > 0) {
							empFinancialTO.setId(empFinancial2.getId());
						}
						if (StringUtils.isNotEmpty(empFinancial2
								.getFinancialAmount())) {
							empFinancialTO.setFinancialAmount(empFinancial2
									.getFinancialAmount());
						}

						if (StringUtils.isNotEmpty(empFinancial2
								.getFinancialDetails())) {
							empFinancialTO.setFinancialDetails(empFinancial2
									.getFinancialDetails());
						}

						if (empFinancial2.getFinancialDate() != null) {
							empFinancialTO.setFinancialDate(CommonUtil
									.ConvertStringToDateFormat(empFinancial2
											.getFinancialDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
						}

						empFinancialTOs.add(empFinancialTO);

					}
				}
				objform.getEmployeeInfoTONew().setEmpFinancial(empFinancialTOs);
			} else {
				List<EmpFinancialTO> flist = new ArrayList<EmpFinancialTO>();
				EmpFinancialTO empFinancialTO = new EmpFinancialTO();
				empFinancialTO.setFinancialAmount("");
				empFinancialTO.setFinancialDate("");
				empFinancialTO.setFinancialDetails("");
				objform.setFinancialListSize(String.valueOf(flist.size()));
				flist.add(empFinancialTO);
				objform.getEmployeeInfoTONew().setEmpFinancial(flist);

			}
		}

		if (empApplicantDetails.getEmpIncentives() != null) {
			Set<EmpIncentives> empIncentives = empApplicantDetails
					.getEmpIncentives();
			if (empIncentives != null && !empIncentives.isEmpty()) {
				Iterator<EmpIncentives> iterator = empIncentives.iterator();
				List<EmpIncentivesTO> empIncentivesTOs = new ArrayList<EmpIncentivesTO>();

				while (iterator.hasNext()) {
					EmpIncentives empIncen = iterator.next();
					if (empIncen != null) {
						EmpIncentivesTO empIncentivesTO = new EmpIncentivesTO();

						if (empIncen.getId() > 0) {
							empIncentivesTO.setId(empIncen.getId());
						}
						if (StringUtils.isNotEmpty(empIncen
								.getIncentivesAmount())) {
							empIncentivesTO.setIncentivesAmount(empIncen
									.getIncentivesAmount());
						}

						if (StringUtils.isNotEmpty(empIncen
								.getIncentivesDetails())) {
							empIncentivesTO.setIncentivesDetails(empIncen
									.getIncentivesDetails());
						}

						if (empIncen.getIncentivesDate() != null) {
							empIncentivesTO.setIncentivesDate(CommonUtil
									.ConvertStringToDateFormat(empIncen
											.getIncentivesDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
						}

						empIncentivesTOs.add(empIncentivesTO);

					}
				}
				objform.getEmployeeInfoTONew().setEmpIncentives(
						empIncentivesTOs);
			} else {
				List<EmpIncentivesTO> list = new ArrayList<EmpIncentivesTO>();
				EmpIncentivesTO empIncentivesTO = new EmpIncentivesTO();
				empIncentivesTO.setIncentivesAmount("");
				empIncentivesTO.setIncentivesDate("");
				empIncentivesTO.setIncentivesDetails("");
				objform.setIncentivesListSize(String.valueOf(list.size()));
				list.add(empIncentivesTO);
				objform.getEmployeeInfoTONew().setEmpIncentives(list);
			}
		}
		if (empApplicantDetails.getEmpLoan() != null) {
			Set<EmpLoan> empLoan = empApplicantDetails.getEmpLoan();
			if (empLoan != null && !empLoan.isEmpty()) {
				Iterator<EmpLoan> iterator = empLoan.iterator();
				List<EmpLoanTO> empLoanTOs = new ArrayList<EmpLoanTO>();

				while (iterator.hasNext()) {
					EmpLoan eLoan = iterator.next();
					if (eLoan != null) {
						EmpLoanTO eLoanTO = new EmpLoanTO();
						if (eLoan.getId() > 0) {
							eLoanTO.setId(eLoan.getId());
						}

						if (StringUtils.isNotEmpty(eLoan.getLoanAmount())) {
							eLoanTO.setLoanAmount(eLoan.getLoanAmount());
						}

						if (StringUtils.isNotEmpty(eLoan.getLoanDetails())) {
							eLoanTO.setLoanDetails(eLoan.getLoanDetails());
						}

						if (eLoan.getLoanDate() != null) {
							eLoanTO.setLoanDate(CommonUtil
									.ConvertStringToDateFormat(eLoan
											.getLoanDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
						}

						empLoanTOs.add(eLoanTO);

					}
				}
				objform.getEmployeeInfoTONew().setEmpLoan(empLoanTOs);
			} else {
				List<EmpLoanTO> list = new ArrayList<EmpLoanTO>();
				EmpLoanTO emploanTo = new EmpLoanTO();
				emploanTo.setLoanAmount("");
				emploanTo.setLoanDate("");
				emploanTo.setLoanDetails("");
				objform.setLoanListSize(String.valueOf(list.size()));
				list.add(emploanTo);
				objform.getEmployeeInfoTONew().setEmpLoan(list);
			}
		}

		if (empApplicantDetails.getEmpRemarks() != null) {
			Set<EmpRemarks> empRemarks = empApplicantDetails.getEmpRemarks();
			if (empRemarks != null && !empRemarks.isEmpty()) {
				Iterator<EmpRemarks> iterator = empRemarks.iterator();
				List<EmpRemarksTO> empRemarkTOs = new ArrayList<EmpRemarksTO>();

				while (iterator.hasNext()) {
					EmpRemarks eRemarks = iterator.next();
					if (eRemarks != null) {
						EmpRemarksTO eRemarksTO = new EmpRemarksTO();
						if (eRemarks.getId() > 0) {
							eRemarksTO.setId(eRemarks.getId());
						}
						if (StringUtils
								.isNotEmpty(eRemarks.getRemarksDetails())) {
							eRemarksTO.setRemarkDetails(eRemarks
									.getRemarksDetails());
						}

						if (StringUtils.isNotEmpty(eRemarks
								.getRemarksEnteredBy())) {
							eRemarksTO.setEnteredBy(eRemarks
									.getRemarksEnteredBy());
						}

						if (eRemarks.getRemarksDate() != null) {
							eRemarksTO.setRemarkDate(CommonUtil
									.ConvertStringToDateFormat(eRemarks
											.getRemarksDate().toString(),
											"yyyy-mm-dd", "dd/mm/yyyy"));
						}

						empRemarkTOs.add(eRemarksTO);

					}
				}
				objform.getEmployeeInfoTONew().setEmpRemarks(empRemarkTOs);
			} else {
				List<EmpRemarksTO> flist = new ArrayList<EmpRemarksTO>();
				EmpRemarksTO empRemarksTO = new EmpRemarksTO();
				empRemarksTO.setEnteredBy("");
				empRemarksTO.setRemarkDate("");
				empRemarksTO.setRemarkDetails("");
				objform.setRemarksListSize(String.valueOf(flist.size()));
				flist.add(empRemarksTO);

				objform.getEmployeeInfoTONew().setEmpRemarks(flist);

			}
		}

		if (empApplicantDetails.getEmpLeaves() != null) {
			int month = txn.getInitializationMonth(empApplicantDetails
					.getEmptype().getId());
			int currentMonth = currentMonth();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int year1 = 0;
			Set<EmpLeave> empLeaves = empApplicantDetails.getEmpLeaves();
			List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
			if (empLeaves != null && !empLeaves.isEmpty()) {
				Iterator<EmpLeave> iterator = empLeaves.iterator();
				List<EmpLeaveAllotTO> empLeaveTOs = new ArrayList<EmpLeaveAllotTO>();

				while (iterator.hasNext()) {
					EmpLeave eLeave = iterator.next();
					EmpLeaveAllotTO eLeaveTO = new EmpLeaveAllotTO();
					if (eLeave != null) {
						if (month == 6 && currentMonth < month
								&& year > eLeave.getYear()) {
							year1 = year - 1;
						} else {
							year1 = year;
						}
						if (eLeave.getYear() == year1) {
							if (eLeave.getId() > 0) {
								eLeaveTO.setEmpLeaveId(eLeave.getId());
							}
							if (eLeave.getEmpLeaveType() !=null && eLeave.getEmpLeaveType().getId() > 0) {
								EmpLeaveType leavetype = new EmpLeaveType();
								leavetype.setId(eLeave.getEmpLeaveType()
										.getId());
								eLeaveTO.setEmpLeaveType(leavetype);
							}
							
							if (eLeave.getEmpLeaveType() !=null &&  StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
									.getName())) {
								eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
										.getName());
							}
							if (eLeave.getEmpLeaveType() !=null &&  StringUtils.isNotEmpty(String.valueOf(eLeave
									.getLeavesAllocated()))) {
								eLeaveTO.setAllottedLeave(String.valueOf(eLeave
										.getLeavesAllocated()));
							}
							if (eLeave.getEmpLeaveType() !=null && StringUtils.isNotEmpty(String.valueOf(eLeave
									.getLeavesSanctioned()))) {
								eLeaveTO.setSanctionedLeave(String
										.valueOf(eLeave.getLeavesSanctioned()));
							}
							if (eLeave.getEmpLeaveType() !=null && StringUtils.isNotEmpty(String.valueOf(eLeave
									.getLeavesRemaining()))) {
								eLeaveTO.setRemainingLeave(String
										.valueOf(eLeave.getLeavesRemaining()));
							}
							if (eLeave.getEmpLeaveType() !=null && StringUtils.isNotEmpty(String.valueOf(eLeave
									.getYear()))) {
								eLeaveTO.setYear(eLeave.getYear());
							}
							String monthString = new DateFormatSymbols()
									.getMonths()[month - 1];

							if (eLeave.getEmpLeaveType() !=null && StringUtils.isNotEmpty(monthString)) {
								eLeaveTO.setMonth(monthString);
							}
							empLeaveTOs.add(eLeaveTO);
						} else {
							if (eLeave.getId() > 0) {
								eLeaveTO.setEmpLeaveId(eLeave.getId());
							}

							if (eLeave.getEmpLeaveType().getId() > 0) {
								EmpLeaveType leavetype = new EmpLeaveType();
								leavetype.setId(eLeave.getEmpLeaveType()
										.getId());
								eLeaveTO.setEmpLeaveType(leavetype);
							}

							if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
									.getName())) {
								eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
										.getName());
							}

							if (StringUtils.isNotEmpty(String.valueOf(eLeave
									.getLeavesAllocated()))) {
								eLeaveTO.setAllottedLeave(String.valueOf(eLeave
										.getLeavesAllocated()));
							}
							if (StringUtils.isNotEmpty(String.valueOf(eLeave
									.getLeavesSanctioned()))) {
								eLeaveTO.setSanctionedLeave(String
										.valueOf(eLeave.getLeavesSanctioned()));
							}
							if (StringUtils.isNotEmpty(String.valueOf(eLeave
									.getLeavesRemaining()))) {
								eLeaveTO.setRemainingLeave(String
										.valueOf(eLeave.getLeavesRemaining()));
							}
							if (StringUtils.isNotEmpty(String.valueOf(eLeave
									.getYear()))) {
								eLeaveTO.setYear(eLeave.getYear());
							}
							String monthString = new DateFormatSymbols()
									.getMonths()[month - 1];

							if (StringUtils.isNotEmpty(monthString)) {
								eLeaveTO.setMonth(monthString);
							}
							extEmpLeaveTos.add(eLeaveTO);
						}

					}
				}
				//Collections.sort(empLeaveTOs);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				objform.setEmpLeaveAllotTo(extEmpLeaveTos);
			} else {
				String empTypeId = null;
				if (objform.getEmptypeId() != null
						&& !objform.getEmptypeId().isEmpty()) {
					empTypeId = objform.getEmptypeId();
					List<EmpLeaveAllotTO> empLeaveToList;
					try {
						empLeaveToList = EmployeeInfoEditHandler.getInstance()
								.getEmpLeaveList(empTypeId, objform);
						Collections.sort(empLeaveToList);
						objform.getEmployeeInfoTONew().setEmpLeaveToList(
								empLeaveToList);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		if (empApplicantDetails.getEmpImmigrations() != null) {
			Set<EmpImmigration> empImmigration = empApplicantDetails
					.getEmpImmigrations();
			if(empImmigration!=null)
			{
			Iterator<EmpImmigration> iterator = empImmigration.iterator();
			List<EmpImmigrationTO> empImmigrationTOs = new ArrayList<EmpImmigrationTO>();

			while (iterator.hasNext()) {
				EmpImmigration eImmigration = iterator.next();
				if (eImmigration != null) {
					EmpImmigrationTO eImmigrationTO = new EmpImmigrationTO();

					if (eImmigration.getId() > 0) {
						eImmigrationTO.setId(String.valueOf(eImmigration
								.getId()));
					}
					if (StringUtils.isNotEmpty(eImmigration
							.getPassportComments())) {
						eImmigrationTO.setPassportComments(eImmigration
								.getPassportComments());
					}

					if (StringUtils.isNotEmpty(eImmigration.getPassportNo())) {
						eImmigrationTO.setPassportNo(eImmigration
								.getPassportNo());
					}
					if (StringUtils.isNotEmpty(eImmigration
							.getPassportReviewStatus())) {
						eImmigrationTO.setPassportReviewStatus(eImmigration
								.getPassportReviewStatus());
					}
					if (StringUtils
							.isNotEmpty(eImmigration.getPassportStatus())) {
						eImmigrationTO.setPassportStatus(eImmigration
								.getPassportStatus());
					}
					if (eImmigration.getCountryByPassportCountryId() != null
							&& eImmigration.getCountryByPassportCountryId()
									.getId() > 0) {

						eImmigrationTO.setPassportCountryId(String
								.valueOf(eImmigration
										.getCountryByPassportCountryId()
										.getId()));
					}
					if (eImmigration.getPassportDateOfExpiry() != null) {
						eImmigrationTO.setPassportExpiryDate(CommonUtil
								.ConvertStringToDateFormat(eImmigration
										.getPassportDateOfExpiry().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (eImmigration.getPassportIssueDate() != null) {
						eImmigrationTO.setPassportIssueDate(CommonUtil
								.ConvertStringToDateFormat(eImmigration
										.getPassportIssueDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (StringUtils.isNotEmpty(eImmigration.getVisaComments())) {
						eImmigrationTO.setVisaComments(eImmigration
								.getVisaComments());
					}
					if (StringUtils.isNotEmpty(eImmigration.getVisaNo())) {
						eImmigrationTO.setVisaNo(eImmigration.getVisaNo());
					}
					if (StringUtils.isNotEmpty(eImmigration
							.getVisaReviewStatus())) {
						eImmigrationTO.setVisaReviewStatus(eImmigration
								.getVisaReviewStatus());
					}
					if (StringUtils.isNotEmpty(eImmigration.getVisaStatus())) {
						eImmigrationTO.setVisaStatus(eImmigration
								.getVisaStatus());
					}
					if (eImmigration.getCountryByVisaCountryId() != null
							&& eImmigration.getCountryByVisaCountryId().getId() > 0) {

						eImmigrationTO.setVisaCountryId(String
								.valueOf(eImmigration
										.getCountryByVisaCountryId().getId()));
					}
					if (eImmigration.getVisaDateOfExpiry() != null) {
						eImmigrationTO.setVisaExpiryDate(CommonUtil
								.ConvertStringToDateFormat(eImmigration
										.getVisaDateOfExpiry().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					if (eImmigration.getVisaIssueDate() != null) {
						eImmigrationTO.setVisaIssueDate(CommonUtil
								.ConvertStringToDateFormat(eImmigration
										.getVisaIssueDate().toString(),
										"yyyy-mm-dd", "dd/mm/yyyy"));
					}
					empImmigrationTOs.add(eImmigrationTO);

				}
				objform.getEmployeeInfoTONew().setEmpImmigration(
						empImmigrationTOs);
			}

		}else
		{
			List<EmpImmigrationTO> empImigrations=new ArrayList<EmpImmigrationTO>();
			EmpImmigrationTO empImmigrationTO=new EmpImmigrationTO();
			empImmigrationTO.setPassportComments(" ");
			empImmigrationTO.setPassportCountryId("");
			empImmigrationTO.setPassportExpiryDate("");
			empImmigrationTO.setPassportIssueDate("");
			empImmigrationTO.setPassportNo("");
			empImmigrationTO.setPassportReviewStatus("");
			empImmigrationTO.setPassportStatus("");
			
			empImmigrationTO.setVisaComments(" ");
			empImmigrationTO.setVisaCountryId("");
			empImmigrationTO.setVisaExpiryDate("");
			empImmigrationTO.setVisaIssueDate("");
			empImmigrationTO.setVisaNo("");
			empImmigrationTO.setVisaReviewStatus("");
			empImmigrationTO.setVisaStatus("");
			objform.setImmigrationListSize(String.valueOf(empImigrations.size()));	
			empImigrations.add(empImmigrationTO);
			objform.getEmployeeInfoTONew().setEmpImmigration(empImigrations);
		}
			
		}
		// Payscale .............

		if (empApplicantDetails.getEmpPayAllowance() != null) {
			Set<EmpPayAllowanceDetails> empPayAllowanceDetails = empApplicantDetails
					.getEmpPayAllowance();
			if (empPayAllowanceDetails != null
					&& !empPayAllowanceDetails.isEmpty()) {
				Iterator<EmpPayAllowanceDetails> iterator = empPayAllowanceDetails
						.iterator();
				// List<EmpPayAllowanceTO> empPayAllowanceTOs=new
				// ArrayList<EmpPayAllowanceTO>();
				List<EmpAllowanceTO> empPayAllowanceTOs = new ArrayList<EmpAllowanceTO>();

				List<EmpAllowanceTO> fixed = null;
				if (objform.getEmployeeInfoTONew() != null) {
					if (objform.getEmployeeInfoTONew().getPayscaleFixedTo() != null) {
						fixed = objform.getEmployeeInfoTONew()
								.getPayscaleFixedTo();
					}

					//
					while (iterator.hasNext()) {
						// EmpPayAllowanceTO ePayAllotTO=new
						// EmpPayAllowanceTO();
						EmpPayAllowanceDetails ePayAlloDet = iterator.next();
						EmpAllowanceTO eAllotTO = new EmpAllowanceTO();
						if (ePayAlloDet != null) {

							if (fixed != null) {
								Iterator<EmpAllowanceTO> iterator2 = fixed
										.iterator();
								while (iterator2.hasNext()) {
									EmpAllowanceTO empAllTO = iterator2.next();
									if (empAllTO != null
											&& (empAllTO.getId() > 0)) {
										if (ePayAlloDet.getEmpAllowance()!= null && ePayAlloDet.getEmpAllowance()
												.getId() > 0)
											if (empAllTO.getId() == (ePayAlloDet
													.getEmpAllowance().getId())) {
												eAllotTO.setId(ePayAlloDet
														.getEmpAllowance()
														.getId());
												// check the above line......
												if (ePayAlloDet.getId() > 0) {
													// ePayAllotTO.setId(ePayAlloDet.getId());
													eAllotTO
															.setEmpPayAllowanceId(ePayAlloDet
																	.getId());
												}

												if (StringUtils
														.isNotEmpty(ePayAlloDet
																.getAllowanceValue())) {
													eAllotTO
															.setAllowanceName(ePayAlloDet
																	.getAllowanceValue());
													// ePayAllotTO.setAllowanceValue(eAllotTO.getAllowanceName());
												}

												if (StringUtils
														.isNotEmpty(String
																.valueOf(empAllTO
																		.getName()))) {
													eAllotTO.setName(empAllTO
															.getName());

												}
												eAllotTO.setDisplayOrder(empAllTO.getDisplayOrder());
												empPayAllowanceTOs
														.add(eAllotTO);
											}
									}
								}
							}
						}
					}
				}
				Collections.sort(empPayAllowanceTOs,new PayAllowance());
				objform.getEmployeeInfoTONew().setPayscaleFixedTo(empPayAllowanceTOs);
			} else {
				try {
					List<EmpAllowanceTO> payscaleFixedTo = txn.getPayAllowanceFixedMap();

					if (payscaleFixedTo != null) {
						objform.getEmployeeInfoTONew().setPayscaleFixedTo(
								payscaleFixedTo);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//

		if (empApplicantDetails.getPreviousExpSet() != null) {
			int teachingFlag = 0;
			int industryFlag = 0;
			Set<EmpPreviousExperience> empOnlinePreviousExperiencesSet = empApplicantDetails
					.getPreviousExpSet();
			if (empOnlinePreviousExperiencesSet != null
					&& !empOnlinePreviousExperiencesSet.isEmpty()) {

				Iterator<EmpPreviousExperience> iterator = empOnlinePreviousExperiencesSet
						.iterator();
				List<EmpPreviousOrgTo> industryExp = new ArrayList<EmpPreviousOrgTo>();
				List<EmpPreviousOrgTo> teachingExp = new ArrayList<EmpPreviousOrgTo>();
				while (iterator.hasNext()) {
					EmpPreviousExperience empOnlinePreviousExperiences = iterator
							.next();
					if (empOnlinePreviousExperiences != null) {
						EmpPreviousOrgTo empOnliPreviousExperienceTO = new EmpPreviousOrgTo();
						if (empOnlinePreviousExperiences.isIndustryExperience()) {
							if (empOnlinePreviousExperiences.getId() > 0) {
								empOnliPreviousExperienceTO
										.setId(empOnlinePreviousExperiences
												.getId());
							}
							if (StringUtils
									.isNotEmpty(empOnlinePreviousExperiences
											.getEmpDesignation())) {
								empOnliPreviousExperienceTO
										.setCurrentDesignation(empOnlinePreviousExperiences
												.getEmpDesignation());
							}

							if (StringUtils
									.isNotEmpty(empOnlinePreviousExperiences
											.getEmpOrganization())) {
								empOnliPreviousExperienceTO
										.setCurrentOrganisation(empOnlinePreviousExperiences
												.getEmpOrganization());
							}

							if (empOnlinePreviousExperiences.getExpMonths() > 0) {
								empOnliPreviousExperienceTO
										.setIndustryExpMonths(String
												.valueOf(empOnlinePreviousExperiences
														.getExpMonths()));
							}

							if (empOnlinePreviousExperiences.getExpYears() > 0) {
								empOnliPreviousExperienceTO
										.setIndustryExpYears(String
												.valueOf(empOnlinePreviousExperiences
														.getExpYears()));
							}
							/* code added by sudhir */
							if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
								empOnliPreviousExperienceTO.setIndustryFromDate(formatDate(empOnlinePreviousExperiences.getFromDate()));
							}
							if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
								empOnliPreviousExperienceTO.setIndustryToDate(formatDate(empOnlinePreviousExperiences.getToDate()));
							}
							/*-----------------------*/
							industryFlag = 1;
							industryExp.add(empOnliPreviousExperienceTO);
							Collections.sort(industryExp,new EmployeeInfoEditComparator());
						} else if (empOnlinePreviousExperiences
								.isTeachingExperience()) {
							if (empOnlinePreviousExperiences.getId() > 0) {
								empOnliPreviousExperienceTO
										.setId(empOnlinePreviousExperiences
												.getId());
							}
							if (StringUtils
									.isNotEmpty(empOnlinePreviousExperiences
											.getEmpDesignation())) {
								empOnliPreviousExperienceTO
										.setCurrentTeachnigDesignation(empOnlinePreviousExperiences
												.getEmpDesignation());
							}

							if (StringUtils
									.isNotEmpty(empOnlinePreviousExperiences
											.getEmpOrganization())) {
								empOnliPreviousExperienceTO
										.setCurrentTeachingOrganisation(empOnlinePreviousExperiences
												.getEmpOrganization());
							}

							if (empOnlinePreviousExperiences.getExpMonths() > 0) {
								empOnliPreviousExperienceTO
										.setTeachingExpMonths(String
												.valueOf(empOnlinePreviousExperiences
														.getExpMonths()));
							}

							if (empOnlinePreviousExperiences.getExpYears() > 0) {
								empOnliPreviousExperienceTO
										.setTeachingExpYears(String
												.valueOf(empOnlinePreviousExperiences
														.getExpYears()));
							}
							/* code added by sudhir */
							if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
								empOnliPreviousExperienceTO.setTeachingFromDate(formatDate(empOnlinePreviousExperiences.getFromDate()));
							}
							if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
								empOnliPreviousExperienceTO.setTeachingToDate(formatDate(empOnlinePreviousExperiences.getToDate()));
							}
							/*-----------------------*/
							teachingFlag = 1;
							teachingExp.add(empOnliPreviousExperienceTO);

						}

					}
				}
				EmpPreviousOrgTo empPreviousOrgTo = new EmpPreviousOrgTo();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(industryExp.size()));
				industryExp.add(empPreviousOrgTo);
				
				EmpPreviousOrgTo empPreviousOrgTo1 = new EmpPreviousOrgTo();
				empPreviousOrgTo1.setIfEmpty("2");
				empPreviousOrgTo1.setIndustryExpYears("");
				empPreviousOrgTo1.setIndustryExpMonths("");
				empPreviousOrgTo1.setCurrentDesignation("");
				empPreviousOrgTo1.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(industryExp.size()));
				industryExp.add(empPreviousOrgTo1);
				
				objform.getEmployeeInfoTONew().setExperiences(industryExp);
				
				EmpPreviousOrgTo empPreviousOrgTo6 = new EmpPreviousOrgTo();
				empPreviousOrgTo6.setIfEmpty("4");
				empPreviousOrgTo6.setTeachingExpYears("");
				empPreviousOrgTo6.setTeachingExpMonths("");
				empPreviousOrgTo6.setCurrentTeachingOrganisation("");
				empPreviousOrgTo6.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingExp.size()));
				teachingExp.add(empPreviousOrgTo6);
				
				EmpPreviousOrgTo empPreviousOrgTo7 = new EmpPreviousOrgTo();
				empPreviousOrgTo7.setIfEmpty("5");
				empPreviousOrgTo7.setTeachingExpYears("");
				empPreviousOrgTo7.setTeachingExpMonths("");
				empPreviousOrgTo7.setCurrentTeachingOrganisation("");
				empPreviousOrgTo7.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingExp.size()));
				teachingExp.add(empPreviousOrgTo7);
				
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
			} else {
				List<EmpPreviousOrgTo> list = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo = new EmpPreviousOrgTo();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				EmpPreviousOrgTo empPreviousOrgTo1 = new EmpPreviousOrgTo();
				empPreviousOrgTo1.setIfEmpty("2");
				empPreviousOrgTo1.setIndustryExpYears("");
				empPreviousOrgTo1.setIndustryExpMonths("");
				empPreviousOrgTo1.setCurrentDesignation("");
				empPreviousOrgTo1.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo1);
				
				EmpPreviousOrgTo empPreviousOrgTo2 = new EmpPreviousOrgTo();
				empPreviousOrgTo2.setIfEmpty("3");
				empPreviousOrgTo2.setIndustryExpYears("");
				empPreviousOrgTo2.setIndustryExpMonths("");
				empPreviousOrgTo2.setCurrentDesignation("");
				empPreviousOrgTo2.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo2);
				
				/*EmpPreviousOrgTo empPreviousOrgTo3 = new EmpPreviousOrgTo();
				empPreviousOrgTo3.setIfEmpty("4");
				list.add(empPreviousOrgTo3);*/

				List<EmpPreviousOrgTo> teachingList = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo4 = new EmpPreviousOrgTo();
				empPreviousOrgTo4.setIfEmpty("1");
				empPreviousOrgTo4.setTeachingExpYears("");
				empPreviousOrgTo4.setTeachingExpMonths("");
				empPreviousOrgTo4.setCurrentTeachingOrganisation("");
				empPreviousOrgTo4.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo4);
				
				EmpPreviousOrgTo empPreviousOrgTo8 = new EmpPreviousOrgTo();
				empPreviousOrgTo8.setIfEmpty("2");
				empPreviousOrgTo8.setTeachingExpYears("");
				empPreviousOrgTo8.setTeachingExpMonths("");
				empPreviousOrgTo8.setCurrentTeachingOrganisation("");
				empPreviousOrgTo8.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo8);
				
				EmpPreviousOrgTo empPreviousOrgTo5 = new EmpPreviousOrgTo();
				empPreviousOrgTo5.setIfEmpty("3");
				empPreviousOrgTo5.setTeachingExpYears("");
				empPreviousOrgTo5.setTeachingExpMonths("");
				empPreviousOrgTo5.setCurrentTeachingOrganisation("");
				empPreviousOrgTo5.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo5);
				
				EmpPreviousOrgTo empPreviousOrgTo6 = new EmpPreviousOrgTo();
				empPreviousOrgTo6.setIfEmpty("4");
				empPreviousOrgTo6.setTeachingExpYears("");
				empPreviousOrgTo6.setTeachingExpMonths("");
				empPreviousOrgTo6.setCurrentTeachingOrganisation("");
				empPreviousOrgTo6.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo6);
				
				EmpPreviousOrgTo empPreviousOrgTo7 = new EmpPreviousOrgTo();
				empPreviousOrgTo7.setIfEmpty("5");
				empPreviousOrgTo7.setTeachingExpYears("");
				empPreviousOrgTo7.setTeachingExpMonths("");
				empPreviousOrgTo7.setCurrentTeachingOrganisation("");
				empPreviousOrgTo7.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo7);
				
				objform.getEmployeeInfoTONew().setExperiences(list);
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
			}
			if (teachingFlag == 1 && industryFlag == 0) {
				List<EmpPreviousOrgTo> list = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo = new EmpPreviousOrgTo();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
			if (teachingFlag == 0 && industryFlag == 1) {
				List<EmpPreviousOrgTo> teachingList = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo = new EmpPreviousOrgTo();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String
						.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setTeachingExperience(
						teachingList);
			}
			if (teachingFlag == 1 && industryFlag == 0) {
				List<EmpPreviousOrgTo> list = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo = new EmpPreviousOrgTo();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
			if (teachingFlag == 0 && industryFlag == 0) {
				List<EmpPreviousOrgTo> teachingList = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo = new EmpPreviousOrgTo();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String
						.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				
				
			

				//List<EmpPreviousOrgTo> teachingList = new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo4 = new EmpPreviousOrgTo();
				empPreviousOrgTo4.setIfEmpty("1");
				empPreviousOrgTo4.setTeachingExpYears("");
				empPreviousOrgTo4.setTeachingExpMonths("");
				empPreviousOrgTo4.setCurrentTeachingOrganisation("");
				empPreviousOrgTo4.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo4);
				
				EmpPreviousOrgTo empPreviousOrgTo8 = new EmpPreviousOrgTo();
				empPreviousOrgTo8.setIfEmpty("2");
				empPreviousOrgTo8.setTeachingExpYears("");
				empPreviousOrgTo8.setTeachingExpMonths("");
				empPreviousOrgTo8.setCurrentTeachingOrganisation("");
				empPreviousOrgTo8.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo8);
				
				EmpPreviousOrgTo empPreviousOrgTo5 = new EmpPreviousOrgTo();
				empPreviousOrgTo5.setIfEmpty("3");
				empPreviousOrgTo5.setTeachingExpYears("");
				empPreviousOrgTo5.setTeachingExpMonths("");
				empPreviousOrgTo5.setCurrentTeachingOrganisation("");
				empPreviousOrgTo5.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo5);
				
				EmpPreviousOrgTo empPreviousOrgTo6 = new EmpPreviousOrgTo();
				empPreviousOrgTo6.setIfEmpty("4");
				empPreviousOrgTo6.setTeachingExpYears("");
				empPreviousOrgTo6.setTeachingExpMonths("");
				empPreviousOrgTo6.setCurrentTeachingOrganisation("");
				empPreviousOrgTo6.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo6);
				
				/*EmpPreviousOrgTo empPreviousOrgTo7 = new EmpPreviousOrgTo();
				empPreviousOrgTo7.setIfEmpty("5");
				empPreviousOrgTo7.setTeachingExpYears("");
				empPreviousOrgTo7.setTeachingExpMonths("");
				empPreviousOrgTo7.setCurrentTeachingOrganisation("");
				empPreviousOrgTo7.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo7);*/
				
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);

				List<EmpPreviousOrgTo> list = new ArrayList<EmpPreviousOrgTo>();

				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				EmpPreviousOrgTo empPreviousOrgTo1 = new EmpPreviousOrgTo();
				empPreviousOrgTo1.setIfEmpty("2");
				empPreviousOrgTo1.setIndustryExpYears("");
				empPreviousOrgTo1.setIndustryExpMonths("");
				empPreviousOrgTo1.setCurrentDesignation("");
				empPreviousOrgTo1.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo1);
				
				EmpPreviousOrgTo empPreviousOrgTo2 = new EmpPreviousOrgTo();
				empPreviousOrgTo2.setIfEmpty("3");
				empPreviousOrgTo2.setIndustryExpYears("");
				empPreviousOrgTo2.setIndustryExpMonths("");
				empPreviousOrgTo2.setCurrentDesignation("");
				empPreviousOrgTo2.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo2);
				
				/*EmpPreviousOrgTo empPreviousOrgTo3 = new EmpPreviousOrgTo();
				empPreviousOrgTo3.setIfEmpty("4");
				list.add(empPreviousOrgTo3);*/
				
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
		}
		if (empApplicantDetails.getEmpImages() != null && !empApplicantDetails.getEmpImages().isEmpty()) {
			Set<EmpImages> empImages = empApplicantDetails.getEmpImages();
			if (empImages != null && !empImages.isEmpty()) {
				Iterator<EmpImages> iterator = empImages.iterator();
				List<EmpImagesTO> empImagesTOs = new ArrayList<EmpImagesTO>();

				while (iterator.hasNext()) {
					EmpImages eImages = iterator.next();
					if (eImages != null) {
						EmpImagesTO eImagesTO = new EmpImagesTO();
						if (eImages.getId() > 0) {
							eImagesTO.setId(eImages.getId());
						}
						if (eImages.getEmpPhoto() != null && eImages.getEmpPhoto().length > 0)
						 {							
							byte[] myFileBytes = eImages.getEmpPhoto();
							objform.setPhotoBytes(myFileBytes);
							//eImagesTO.setEmpPhoto(eImages.getEmpPhoto());
						}
						empImagesTOs.add(eImagesTO);

					}
				}			
				objform.getEmployeeInfoTONew().setEmpImages(empImagesTOs);
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

	public static int currentMonth() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		return month + 1;
	}

	public EmpLeave setLeaveToTOBO(EmpLeaveAllotTO to,
			EmployeeInfoEditForm employeeInfoEditForm) {
		EmpLeave dep = new EmpLeave();
		if (to.getEmpLeaveId() > 0) {
			dep.setId(to.getEmpLeaveId());
		}
		if (to.getEmpLeaveType().getId() > 0) {
			EmpLeaveType leavetype = new EmpLeaveType();
			leavetype.setId(to.getEmpLeaveType().getId());
			dep.setEmpLeaveType(leavetype);
		}

		if (to.getAllottedLeave() != null && !to.getAllottedLeave().isEmpty()) {
			dep.setLeavesAllocated(Double.parseDouble(to.getAllottedLeave()));
		} else {
			dep.setLeavesAllocated(0.0);
		}
		// dep.setLeavesRemaining((0.0));

		if (to.getSanctionedLeave() != null
				&& !to.getSanctionedLeave().isEmpty()) {
			dep.setLeavesSanctioned(Double.parseDouble(to.getSanctionedLeave()));
		} else {
			dep.setLeavesSanctioned(0.0);
		}
		if (dep.getLeavesAllocated() > 0) {
			dep.setLeavesRemaining(dep.getLeavesAllocated()
					- dep.getLeavesSanctioned());
		} else {
			dep.setLeavesRemaining(0.0);
		}
		if (to.getYear() != 0) {
			dep.setYear(to.getYear());
		}
		if (to.getMonth() != null && !to.getMonth().isEmpty()) {
			dep.setMonth(to.getMonth());
		}
		Employee emp = new Employee();
		emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
		dep.setEmployee(emp);
		dep.setCreatedBy(employeeInfoEditForm.getUserId());
		dep.setCreatedDate(new Date());
		dep.setModifiedBy(employeeInfoEditForm.getUserId());
		dep.setLastModifiedDate(new Date());
		dep.setIsActive(true);
		return dep;
	}
	
	/**
	 * @param AllotmentList1
	 * @param Applylist2
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveAllotTO> convertBotoTo(List<Object[]> allotmentList, List<Object[]> applylist, int year,int month) throws Exception {
		Map<Integer,EmpLeaveTypeTO> applyMap=new HashMap<Integer, EmpLeaveTypeTO>();
		List<EmpLeaveAllotTO> tos=new ArrayList<EmpLeaveAllotTO>();
		EmpLeaveTypeTO leaveTO;
		EmpLeaveAllotTO allotTO;
		if(applylist!=null && !applylist.isEmpty()){
			Iterator<Object[]> itr=applylist.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				leaveTO = new EmpLeaveTypeTO();
				leaveTO.setId(Integer.parseInt(obj[0].toString()));
				leaveTO.setName(obj[2].toString());
				leaveTO.setSanctionedLeave(obj[1].toString());
				applyMap.put(Integer.parseInt(obj[0].toString()), leaveTO);
			}
		}
		
		if(allotmentList!=null && !allotmentList.isEmpty()){
			Iterator<Object[]> itr=allotmentList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				allotTO=new EmpLeaveAllotTO();
				double allotedLeave=Double.parseDouble(obj[2].toString());
				if(applyMap.containsKey(Integer.parseInt(obj[0].toString()))){
					leaveTO = applyMap.remove(Integer.parseInt(obj[0].toString()));
					double sanctionLeaves = 0.0;
					if(leaveTO.getSanctionedLeave() != null && !leaveTO.getSanctionedLeave().isEmpty()){
						sanctionLeaves	= Double.parseDouble(leaveTO.getSanctionedLeave());
					}
					allotTO.setLeaveType(obj[1].toString());
					
					if(allotedLeave>0 && sanctionLeaves>0 )	{
						allotTO.setAllottedLeave(String.valueOf(allotedLeave));
						allotTO.setSanctionedLeave(leaveTO.getSanctionedLeave());
						allotTO.setRemainingLeave(String.valueOf(allotedLeave - sanctionLeaves));
						EmpLeaveType emp=new EmpLeaveType();
						emp.setId(leaveTO.getId());
						allotTO.setEmpLeaveType(emp);
						allotTO.setLeaveType(obj[1].toString());
						String monthString = new DateFormatSymbols().getMonths()[month-1];
						allotTO.setMonth(monthString);
						allotTO.setYear(year);
					}else if(allotedLeave==0 && sanctionLeaves>0 ){
						EmpLeaveType emp=new EmpLeaveType();
						emp.setId(leaveTO.getId());
						allotTO.setEmpLeaveType(emp);
						allotTO.setAllottedLeave("0.0");
						allotTO.setSanctionedLeave(String.valueOf(sanctionLeaves));
						allotTO.setRemainingLeave("0.0");
						allotTO.setLeaveType(obj[1].toString());
						String monthString = new DateFormatSymbols().getMonths()[month-1];
						allotTO.setMonth(monthString);
						allotTO.setYear(year);
					}
					tos.add(allotTO);
				}else{
					 if(allotedLeave>0){
						 EmpLeaveType emp=new EmpLeaveType();
						 emp.setId(Integer.parseInt(obj[0].toString()));
						 allotTO.setEmpLeaveType(emp);
						 allotTO.setAllottedLeave(String.valueOf(allotedLeave));
						 allotTO.setSanctionedLeave("0.0");
						 allotTO.setRemainingLeave(String.valueOf(allotedLeave));
						 allotTO.setLeaveType(obj[1].toString());
						 String monthString = new DateFormatSymbols().getMonths()[month-1];
						 allotTO.setMonth(monthString);
						 allotTO.setYear(year);
						 tos.add(allotTO);
						}
				}
			}
		}
		if(!applyMap.isEmpty()){
			for (Map.Entry<Integer, EmpLeaveTypeTO> entry : applyMap.entrySet()) {
			    leaveTO =entry.getValue();
			    if(leaveTO.getSanctionedLeave()!=null &&  Double.parseDouble(leaveTO.getSanctionedLeave())>0){
			    	allotTO=new EmpLeaveAllotTO();
			    	EmpLeaveType emp=new EmpLeaveType();
					emp.setId(leaveTO.getId());
					allotTO.setEmpLeaveType(emp);
					allotTO.setAllottedLeave("0.0");
					allotTO.setSanctionedLeave(leaveTO.getSanctionedLeave());
					allotTO.setRemainingLeave("0.0");
					allotTO.setLeaveType(leaveTO.getName());
					String monthString = new DateFormatSymbols().getMonths()[month-1];
					allotTO.setMonth(monthString);
					allotTO.setYear(year);
					tos.add(allotTO);
				}
			}
		}
		return tos;
	}
	/**
	 * @param employeeInfoEditForm
	 * @param session
	 * @param myProfile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public Employee convertFormToBoOfMyProfile(EmployeeInfoEditForm employeeInfoEditForm,HttpSession session,EditMyProfile myProfile) throws FileNotFoundException, IOException, ApplicationException {
		Employee employee=new Employee();
		Employee empApplicantDetails=(Employee)session.getAttribute("applicationDeatils");
	    if(employeeInfoEditForm.getEmployeeId()!=null && !employeeInfoEditForm.getEmployeeId().isEmpty())
	    {
	       employee.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
		   myProfile.setEmpId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
		// Creating EmpLoan Object and setting into EMployee Object
		Set<EmpLoan> empLoan = getEmpLoanBoObjects(employeeInfoEditForm);     
		employee.setEmpLoan(empLoan);
		
		Set<EmpFinancial> empFinancial = getEmpFinancialBoObjects(employeeInfoEditForm);   
		employee.setEmpFinancial(empFinancial);
		
		Set<EmpFeeConcession> empFeeConcession = getEmpFeeConcessionBoObjects(employeeInfoEditForm);    
		employee.setEmpFeeConcession(empFeeConcession);
		
		Set<EmpIncentives> empIncentives = getEmpIncentivesBoObjects(employeeInfoEditForm);        
		employee.setEmpIncentives(empIncentives); 
		
		Set<EmpRemarks> empRemarks = getEmpRemarksBoObjects(employeeInfoEditForm);       
		employee.setEmpRemarks(empRemarks);

		Set<EmpImmigration> empImmigrations = getEmpImmigrationBoObjects(employeeInfoEditForm);
		employee.setEmpImmigrations(empImmigrations);
		
		Set<EmpDependents> empDependentses = getEmpDependantsBoObjects(employeeInfoEditForm);
		employee.setEmpDependentses(empDependentses);
		
		Set<EmpAcheivement> empAcheivements= getEmpAchievementBoObjects(employeeInfoEditForm);
		employee.setEmpAcheivements(empAcheivements);
		
		Set<EmpPayAllowanceDetails> empPayAllowance = getPayAllowanceBoObjects(employeeInfoEditForm);
		employee.setEmpPayAllowance(empPayAllowance);
		
		Set<EmpLeave> empLeave = getLeavesBoObjects(employeeInfoEditForm);
		employee.setEmpLeaves(empLeave);
		
	    // Comparing EmpLoanSet
		Set<EmpLoan> sessionEmpLoan=empApplicantDetails.getEmpLoan();
		if((sessionEmpLoan==null && empLoan==null)){
		}
		else if((!sessionEmpLoan.isEmpty() && empLoan.isEmpty()) || (!empLoan.isEmpty() && sessionEmpLoan.isEmpty()) || (!sessionEmpLoan.isEmpty() && !empLoan.isEmpty())){
			int count=1;
			if(!sessionEmpLoan.isEmpty() && empLoan.isEmpty()){
			    count++; 
			}else if(!empLoan.isEmpty() && sessionEmpLoan.isEmpty()){
				count++;
			}else{
				Iterator<EmpLoan> itr=sessionEmpLoan.iterator();
				Iterator<EmpLoan> itr1=empLoan.iterator();
			while(itr.hasNext()){
				EmpLoan sessionLoan=itr.next();
				while(itr1.hasNext()){
				   EmpLoan presentLoan=itr1.next();
				   if(sessionLoan.getId()==presentLoan.getId()){
				     if(!sessionLoan.equals(presentLoan)){
					   count++;
				     }
				     break;
				   }
				}
			}}
			if(count>1){
				myProfile.setEmpLoan(myProfile.getEmpLoan()+1);
			}
		}
		// Ending EmpLoan comparison
		
		// Comparing EmpFinancialSet
		Set<EmpFinancial> sessionFinancial=empApplicantDetails.getEmpFinancial();
		if(sessionFinancial==null && empFinancial==null){
			
		}
		else if((!sessionFinancial.isEmpty() && empFinancial.isEmpty()) || (!empFinancial.isEmpty() && sessionFinancial.isEmpty()) || (!sessionFinancial.isEmpty() && !empFinancial.isEmpty())){
			int count=1;
			if(!sessionFinancial.isEmpty() && empFinancial.isEmpty()){
				count++;
			}else if(!empFinancial.isEmpty() && sessionFinancial.isEmpty()){
				count++;
			}else{
			Iterator<EmpFinancial> itr=sessionFinancial.iterator();
			Iterator<EmpFinancial> itr1=empFinancial.iterator();
			while(itr.hasNext()){
				EmpFinancial sessFinancial=(EmpFinancial)itr.next();
				while(itr1.hasNext()){
				   EmpFinancial preFinancial=(EmpFinancial)itr1.next();
				   if(sessFinancial.getId()==preFinancial.getId()){
				      if(!sessFinancial.equals(preFinancial)){
					     count++;
				      }
				      break;
				   }
				}
			}}
			if(count>1){
				myProfile.setEmpFinancial(myProfile.getEmpFinancial()+1);
			}
		}
		// Ending of EmpFinancial
		
		// Comparing of EmpFeeConcession
		Set<EmpFeeConcession> sesionFee=empApplicantDetails.getEmpFeeConcession();
		if(sesionFee==null && empFeeConcession==null){
			
		}
		else if((!sesionFee.isEmpty() && empFeeConcession.isEmpty()) || (!empFeeConcession.isEmpty() && sesionFee.isEmpty()) || (!sesionFee.isEmpty() && !empFeeConcession.isEmpty())){
			int count=1;
			if(!sesionFee.isEmpty() && empFeeConcession.isEmpty()){
				count++;
			}else if(!empFeeConcession.isEmpty() && sesionFee.isEmpty())
				count++;
			else{
			Iterator<EmpFeeConcession> itr=sesionFee.iterator();
			Iterator<EmpFeeConcession> itr1=empFeeConcession.iterator();
			while(itr.hasNext()){
				EmpFeeConcession sesionFees=(EmpFeeConcession)itr.next();
				while(itr1.hasNext()){
				    EmpFeeConcession presFee=(EmpFeeConcession)itr1.next();
				    if(sesionFees.getId()==presFee.getId()){
				       if(!sesionFees.equals(presFee)){
					     count++;
				       }
				       break;
				    }
				}
			}}
			if(count>1){
				myProfile.setEmpFeeConcession(myProfile.getEmpFeeConcession()+1);
			}
		}
			// Ending of EmpFeeConcession
		
		// Comparing EmpIncentives
		Set<EmpIncentives> sesIncentives=empApplicantDetails.getEmpIncentives();
		if(sesIncentives==null && empIncentives==null){
			
		}
		else if((!sesIncentives.isEmpty() && empIncentives.isEmpty()) || (!empIncentives.isEmpty() && sesionFee.isEmpty()) || (!sesionFee.isEmpty() && !empIncentives.isEmpty())){
			int count=1;
			if(!sesIncentives.isEmpty() && empIncentives.isEmpty())
				count++;
			else if(!empIncentives.isEmpty() && sesIncentives.isEmpty())
				count++;
			else{
			Iterator<EmpIncentives> itr=sesIncentives.iterator();
			Iterator<EmpIncentives> itr1=empIncentives.iterator();
			while(itr.hasNext()){
				EmpIncentives sesIncentive=(EmpIncentives)itr.next();
				while(itr1.hasNext()){
				    EmpIncentives preIncentive=(EmpIncentives)itr1.next();
				    if(sesIncentive.getId()==preIncentive.getId()){
				       if(!sesIncentive.equals(preIncentive)){
					     count++;
				       }
				       break;
				    }
				}
			}}
			if(count>1){
				myProfile.setEmpIncentives(myProfile.getEmpIncentives()+1);
			}
		}
			// Ending of EmpIncentives
		
		// comparing EmpRemarks
		Set<EmpRemarks> sesRemarks=empApplicantDetails.getEmpRemarks();
		if(sesRemarks==null && empRemarks==null){
			
		}
		else if((!sesRemarks.isEmpty() && empRemarks.isEmpty()) || (!empRemarks.isEmpty() && sesRemarks.isEmpty()) || (!empRemarks.isEmpty() && !sesRemarks.isEmpty())){
			int count=1;
			if(!sesRemarks.isEmpty() && empRemarks.isEmpty())
				count++;
			else if(!empRemarks.isEmpty() && sesRemarks.isEmpty())
				count++;
			else{
			Iterator<EmpRemarks> itr=sesRemarks.iterator();
			Iterator<EmpRemarks> itr1=empRemarks.iterator();
			while(itr.hasNext()){
				EmpRemarks sesRemark=(EmpRemarks)itr.next();
				while(itr1.hasNext()){
				    EmpRemarks preRemark=(EmpRemarks)itr1.next();
				    if(sesRemark.getId()==preRemark.getId()){
				       if(!sesRemark.equals(preRemark)){
					      count++;
				       }
				       break;
				    }
				}
			}}
			if(count>1){
				myProfile.setEmpRemarks(myProfile.getEmpRemarks()+1);
			}
		}
			// Ending of EmpRemarks
		
		//comparing EmpImmigrations
		Set<EmpImmigration> sesImmigrations=empApplicantDetails.getEmpImmigrations();
		if(sesImmigrations==null && empImmigrations==null){
			
		}
		else if((!sesImmigrations.isEmpty() && empImmigrations.isEmpty()) || (!empImmigrations.isEmpty() && sesImmigrations.isEmpty()) || (!empImmigrations.isEmpty() && !sesImmigrations.isEmpty())){
			int count=1;
			if(!sesImmigrations.isEmpty() && empImmigrations.isEmpty())
				count++;
			else if(!empImmigrations.isEmpty() && sesImmigrations.isEmpty())
				count++;
			else{
			Iterator<EmpImmigration> itr=sesImmigrations.iterator();
			Iterator<EmpImmigration> itr1=empImmigrations.iterator();
			while(itr.hasNext()){
				 EmpImmigration sesImmigration=(EmpImmigration)itr.next();
				while(itr1.hasNext()){
				    EmpImmigration preImmigration=(EmpImmigration)itr1.next();
				    if(sesImmigration.getId()==preImmigration.getId()){
				       if(!sesImmigration.equals(preImmigration)){
					      count++;
				       }
				       break;
				    }
				}
			}}
			if(count>1){
				myProfile.setEmpImmigrations(myProfile.getEmpImmigrations()+1);
			}
		} 
			// Ending of EmpImmigration
		
		//Comparing EmpDependents
		Set<EmpDependents> sesDependents=empApplicantDetails.getEmpDependentses();
		if(sesDependents==null && empDependentses==null){
			
		}
		else if((!sesDependents.isEmpty() && empDependentses.isEmpty()) || (!empDependentses.isEmpty() && sesDependents.isEmpty()) || (!empDependentses.isEmpty() && !sesDependents.isEmpty())){
			int count=1;
			if(!sesDependents.isEmpty() && empDependentses.isEmpty())
				count++;
			else if(!empDependentses.isEmpty() && sesDependents.isEmpty())
				count++;
			else{
			Iterator<EmpDependents> itr=sesDependents.iterator();
			Iterator<EmpDependents> itr1=empDependentses.iterator();
			while(itr.hasNext()){
				EmpDependents sesDepend=(EmpDependents)itr.next();
				while(itr1.hasNext()){
				    EmpDependents preDepend=(EmpDependents)itr1.next();
				    if(sesDepend.getId()==preDepend.getId()){
				       if(!sesDepend.equals(preDepend)){
					     count++;
				       }
				       break;
				    }
				}}
			}if(count>1){
				myProfile.setEmpDependents(myProfile.getEmpDependents()+1);
			}
		}
			// Ending of EmpDependents
		
		//Comparing EmpAcheivements
		Set<EmpAcheivement> sesAcheive=empApplicantDetails.getAcheivementSet();
		if(sesAcheive==null && empAcheivements==null){
			
		}
		else if((!sesAcheive.isEmpty() && empAcheivements.isEmpty()) || (!empAcheivements.isEmpty() && sesAcheive.isEmpty()) || (!empAcheivements.isEmpty() && !sesAcheive.isEmpty())){
			int count=1;
			if(!sesAcheive.isEmpty() && empAcheivements.isEmpty())
				count++;
			else if(!empAcheivements.isEmpty() && sesAcheive.isEmpty())
				count++;
			else{
			Iterator<EmpAcheivement> itr=sesAcheive.iterator();
			Iterator<EmpAcheivement> itr1=empAcheivements.iterator();
			while(itr.hasNext()){
				EmpAcheivement sesAcheivement=(EmpAcheivement)itr.next();
				while(itr1.hasNext()){
				    EmpAcheivement preAcheivement=(EmpAcheivement)itr1.next();
				    if(sesAcheivement.getId()==preAcheivement.getId()){
				       if(!sesAcheivement.equals(preAcheivement)){
					       count++;
				       }
				    break;
				    }
				}}
			}if(count>1){
				myProfile.setEmpAcheivements(myProfile.getEmpAcheivements()+1);
			}
		}
			//Ending of EmpAcheivements
		
		//Comparing EmpPayAllowances
		Set<EmpPayAllowanceDetails> sesPayAllowances=empApplicantDetails.getEmpPayAllowance();
		if(sesPayAllowances==null && empPayAllowance==null){
			
		}
		else if((!sesPayAllowances.isEmpty() && empPayAllowance.isEmpty()) || (!empPayAllowance.isEmpty() && sesPayAllowances.isEmpty()) || (!empPayAllowance.isEmpty() && !sesPayAllowances.isEmpty())){
			int count=1;
			if(!sesPayAllowances.isEmpty() && empPayAllowance.isEmpty())
				count++;
			else if(!empPayAllowance.isEmpty() && sesPayAllowances.isEmpty())
				count++;
			else{
			Iterator<EmpPayAllowanceDetails> itr=sesPayAllowances.iterator();
			Iterator<EmpPayAllowanceDetails> itr1=empPayAllowance.iterator();
			while(itr.hasNext()){
				EmpPayAllowanceDetails sesPayAllowance=(EmpPayAllowanceDetails)itr.next();
				while(itr1.hasNext()){
				    EmpPayAllowanceDetails prePayAllowance=(EmpPayAllowanceDetails)itr1.next();
				    if(sesPayAllowance.getId()==prePayAllowance.getId()){
				        if(!sesPayAllowance.equals(prePayAllowance)){
					       count++;
				        }
				        break;
				    }
				}}
			}if(count>1){
				myProfile.setEmpPayAllowances(myProfile.getEmpPayAllowances()+1);
			}
		}
			//Ending of EmpPayAllowances
		
		//Comparing EmpLeave
 		Set<EmpLeave> sesLeaves=empApplicantDetails.getEmpLeaves();
 		if(sesLeaves==null && empLeave==null){
 			
 		}
 		else if((!sesLeaves.isEmpty() && empLeave.isEmpty()) || (!empLeave.isEmpty() && sesLeaves.isEmpty()) || (!empLeave.isEmpty() && !sesLeaves.isEmpty())){
			int count=1;
			if(!sesLeaves.isEmpty() && empLeave.isEmpty())
				count++;
			else if(!empLeave.isEmpty() && sesLeaves.isEmpty())
				count++;
			else{
			Iterator<EmpLeave> itr=sesLeaves.iterator();
			Iterator<EmpLeave> itr1=empLeave.iterator();
			while(itr.hasNext()){
				EmpLeave sesLeave=(EmpLeave)itr.next();
				while(itr1.hasNext()){
				EmpLeave preLeave=(EmpLeave)itr1.next();
				   if(sesLeave.getId()==preLeave.getId()){
				      if(!sesLeave.equals(preLeave)){
					     count++;
				      }
				      break;
				      
				   }
				}}
			}if(count>1){
				myProfile.setEmpLeave(myProfile.getEmpLeave()+1);
			}
		}  
		// Ending of EmpLeave
		Set<EmpPreviousExperience> previousSet=new HashSet<EmpPreviousExperience>();
		Set<EmpEducationalDetails> educationalDeatialSet=new HashSet<EmpEducationalDetails>();
		try{
		
		if(employeeInfoEditForm.getEmployeeInfoTONew()!=null){
			if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null){
				List<EmpPreviousOrgTo> list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
				
				
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext() ){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						EmpPreviousExperience empPreviousExp=new EmpPreviousExperience();
						if(empPreviousOrgTo!=null){
								if((empPreviousOrgTo.getCurrentOrganisation()!=null && !empPreviousOrgTo.getCurrentOrganisation().isEmpty())
										||(empPreviousOrgTo.getIndustryExpYears()!=null && !empPreviousOrgTo.getIndustryExpYears().isEmpty())
										|| (empPreviousOrgTo.getIndustryExpMonths()!=null && !empPreviousOrgTo.getIndustryExpMonths().isEmpty())
										|| (empPreviousOrgTo.getCurrentDesignation()!=null && !empPreviousOrgTo.getCurrentDesignation().isEmpty())){
							
								if(empPreviousOrgTo.getId()>0){
									empPreviousExp.setId(empPreviousOrgTo.getId());
								}
								if(empPreviousOrgTo.getIndustryExpYears()!=null && !empPreviousOrgTo.getIndustryExpYears().isEmpty()){
									empPreviousExp.setExpYears(Integer.parseInt(empPreviousOrgTo.getIndustryExpYears()));
								}
								if(empPreviousOrgTo.getIndustryExpMonths()!=null && !empPreviousOrgTo.getIndustryExpMonths().isEmpty()){
									empPreviousExp.setExpMonths(Integer.parseInt(empPreviousOrgTo.getIndustryExpMonths()));
								}
								
								if(empPreviousOrgTo.getCurrentDesignation()!=null && !empPreviousOrgTo.getCurrentDesignation().isEmpty()){
									empPreviousExp.setEmpDesignation(empPreviousOrgTo.getCurrentDesignation());
								}
								
								if(empPreviousOrgTo.getCurrentOrganisation()!=null && !empPreviousOrgTo.getCurrentOrganisation().isEmpty()){
									empPreviousExp.setEmpOrganization(empPreviousOrgTo.getCurrentOrganisation());
								}
								Employee emp = new Employee();
								emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
								empPreviousExp.setEmployee(emp);
								empPreviousExp.setIndustryExperience(true);
								empPreviousExp.setActive(true);
								empPreviousExp.setCreatedBy(employeeInfoEditForm.getUserId());
								empPreviousExp.setCreatedDate(new Date());
								empPreviousExp.setModifiedBy(employeeInfoEditForm.getUserId());
								empPreviousExp.setModifiedDate(new Date());
								previousSet.add(empPreviousExp);
								
							}
						}
					}
				}
			}
			
			if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null){
				List<EmpPreviousOrgTo> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						EmpPreviousExperience empPreviousExp=new EmpPreviousExperience();
						if(empPreviousOrgTo!=null){
						if((empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && !empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty())
										||(empPreviousOrgTo.getTeachingExpYears()!=null && !empPreviousOrgTo.getTeachingExpYears().isEmpty())
										|| (empPreviousOrgTo.getTeachingExpMonths()!=null && !empPreviousOrgTo.getTeachingExpMonths().isEmpty())
										|| (empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && !empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty())){	
								if(empPreviousOrgTo.getId()>0){
									empPreviousExp.setId(empPreviousOrgTo.getId());
								}
								
								if(empPreviousOrgTo.getTeachingExpYears()!=null && !empPreviousOrgTo.getTeachingExpYears().isEmpty()){
									empPreviousExp.setExpYears(Integer.parseInt(empPreviousOrgTo.getTeachingExpYears()));
								}
								
								if(empPreviousOrgTo.getTeachingExpMonths()!=null && !empPreviousOrgTo.getTeachingExpMonths().isEmpty()){
									empPreviousExp.setExpMonths(Integer.parseInt(empPreviousOrgTo.getTeachingExpMonths()));
								}
								
								if(empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && !empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty()){
									empPreviousExp.setEmpDesignation(empPreviousOrgTo.getCurrentTeachnigDesignation());
								}
								
								if(empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && !empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty()){
									empPreviousExp.setEmpOrganization(empPreviousOrgTo.getCurrentTeachingOrganisation());
								}
								Employee emp = new Employee();
								emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
								empPreviousExp.setEmployee(emp);
								empPreviousExp.setTeachingExperience(true);
								empPreviousExp.setActive(true);
								empPreviousExp.setCreatedBy(employeeInfoEditForm.getUserId());
								empPreviousExp.setCreatedDate(new Date());
								empPreviousExp.setModifiedBy(employeeInfoEditForm.getUserId());
								empPreviousExp.setModifiedDate(new Date());
								previousSet.add(empPreviousExp);
							}
						}
					}
				}
				
			}
			
		}
	
		if(employeeInfoEditForm.getCurrentlyWorking()!=null && !employeeInfoEditForm.getCurrentlyWorking().isEmpty()
				&& employeeInfoEditForm.getCurrentlyWorking().equalsIgnoreCase("YES")){
		    employee.setCurrentlyWorking(true);
			if(employeeInfoEditForm.getDesignation()!=null && !employeeInfoEditForm.getDesignation().isEmpty()){
				employee.setDesignationName(employeeInfoEditForm.getDesignation());
			}
			
			if(employeeInfoEditForm.getOrgAddress()!=null && !employeeInfoEditForm.getOrgAddress().isEmpty()){
				employee.setOrganistionName(employeeInfoEditForm.getOrgAddress());
			}
		
		}else{
			employee.setCurrentlyWorking(false);
			employee.setDesignationName(null);
			employee.setOrganistionName(null);
		}
		employee.setPreviousExpSet(previousSet);
		//comparing previousExpSet 
		Set<EmpPreviousExperience> prevExperience=empApplicantDetails.getPreviousExpSet();
		if(prevExperience==null && previousSet==null){
			
		}
		else if((!prevExperience.isEmpty() && previousSet.isEmpty()) || (!previousSet.isEmpty() && prevExperience.isEmpty()) || (!previousSet.isEmpty() && !prevExperience.isEmpty())){
		     int count=1;
		     if(!prevExperience.isEmpty() && previousSet.isEmpty())
		    	 count++;
		     else if(!previousSet.isEmpty() && prevExperience.isEmpty())
		    	 count++;
		     else{
		    	 Iterator<EmpPreviousExperience> itr=prevExperience.iterator();
			     Iterator<EmpPreviousExperience> itr1=previousSet.iterator();
		     while(itr.hasNext()){
		    	 EmpPreviousExperience prevExpFromSession=itr.next();
		    	 while(itr1.hasNext()){
			      EmpPreviousExperience presentPrevExp=itr1.next();
			      if(prevExpFromSession.getId()==presentPrevExp.getId()){
			    	  if(prevExpFromSession.isIndustryExperience() && presentPrevExp.isIndustryExperience() && presentPrevExp.getId()==prevExpFromSession.getId()){
			    		  if(!prevExpFromSession.equals(presentPrevExp)){
			    			  count++;
			    		  }
			    	  }else if(prevExpFromSession.isTeachingExperience() && presentPrevExp.isTeachingExperience()&& presentPrevExp.getId()==prevExpFromSession.getId()){
			    		  if(!prevExpFromSession.equals(presentPrevExp)){
			    			  count++;
			    		  }
			    	  }
			    	  break;
			      }
		    	 }
		     }}
		     if(count>1){
		    	 myProfile.setEmpPreviousExp(myProfile.getEmpPreviousExp()+1);
		     }
		} 
			// Endint of comparing PreviousExpSet
		if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
			List<EmpQualificationLevelTo> qualificationFixedTo=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationFixedTo();
			Iterator< EmpQualificationLevelTo> iterator=qualificationFixedTo.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo qualificationFixed=iterator.next();
				EmpEducationalDetails educationalDeatails=null;
				if(qualificationFixed!=null){
					if((qualificationFixed.getInstitute()!=null && !qualificationFixed.getInstitute().isEmpty())
							|| (qualificationFixed.getCourse()!=null && !qualificationFixed.getCourse().isEmpty())
							|| (qualificationFixed.getSpecialization()!=null && !qualificationFixed.getSpecialization().isEmpty())
							|| (qualificationFixed.getGrade()!=null && !qualificationFixed.getGrade().isEmpty())){
						educationalDeatails=new EmpEducationalDetails();
						
						if(qualificationFixed.getEducationDetailsID()>0){
							educationalDeatails.setId(qualificationFixed.getEducationDetailsID());
							}
						if(qualificationFixed.getEducationId()!=null && !qualificationFixed.getEducationId().isEmpty()){
							QualificationLevelBO level=new QualificationLevelBO();
							level.setId(Integer.parseInt(qualificationFixed.getEducationId()));
							educationalDeatails.setEmpQualificationLevel(level);
						}
						
						if(qualificationFixed.getCourse()!=null && !qualificationFixed.getCourse().isEmpty()){
							educationalDeatails.setCourse(qualificationFixed.getCourse());
						}
						
						if(qualificationFixed.getSpecialization()!=null && !qualificationFixed.getSpecialization().isEmpty()){
							educationalDeatails.setSpecialization(qualificationFixed.getSpecialization());
						}
						
						if(qualificationFixed.getYearOfComp()!=null && !qualificationFixed.getYearOfComp().isEmpty()){
							educationalDeatails.setYearOfCompletion(Integer.valueOf(qualificationFixed.getYearOfComp()));
						}
						
						if(qualificationFixed.getGrade()!=null && !qualificationFixed.getGrade().isEmpty()){
							educationalDeatails.setGrade(qualificationFixed.getGrade());
						}
						
						if(qualificationFixed.getInstitute()!=null && !qualificationFixed.getInstitute().isEmpty()){
							educationalDeatails.setInstitute(qualificationFixed.getInstitute());
						}
						Employee emp = new Employee();
						emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
						educationalDeatails.setEmployee(emp);
						educationalDeatails.setActive(true);
						educationalDeatails.setCreatedBy(employeeInfoEditForm.getUserId());
						educationalDeatails.setCreatedDate(new Date());
						educationalDeatails.setModifiedBy(employeeInfoEditForm.getUserId());
						educationalDeatails.setModifiedDate(new Date());
						educationalDeatialSet.add(educationalDeatails);
					}
				}
			}
		}
		
		if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
			Iterator<EmpQualificationLevelTo> iterator=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo levelTo=iterator.next();
				EmpEducationalDetails educationalDetails=null;
				if(levelTo!=null){
					if((levelTo.getInstitute()!=null && !levelTo.getInstitute().isEmpty())
							|| (levelTo.getCourse()!=null && !levelTo.getCourse().isEmpty())
							|| (levelTo.getSpecialization()!=null && !levelTo.getSpecialization().isEmpty())
							|| (levelTo.getGrade()!=null && !levelTo.getGrade().isEmpty())){
						educationalDetails=new EmpEducationalDetails();
						if(levelTo.getEducationId()!=null && !levelTo.getEducationId().isEmpty()){
							QualificationLevelBO level=new QualificationLevelBO();
							level.setId(Integer.parseInt(levelTo.getEducationId()));
							educationalDetails.setEmpQualificationLevel(level);
						}
						
						if(levelTo.getEducationDetailsID()>0){
							educationalDetails.setId(levelTo.getEducationDetailsID());
							}
						if(levelTo.getCourse()!=null && !levelTo.getCourse().isEmpty()){
							educationalDetails.setCourse(levelTo.getCourse());
						}
						
						if(levelTo.getSpecialization()!=null && !levelTo.getSpecialization().isEmpty()){
							educationalDetails.setSpecialization(levelTo.getSpecialization());
						}
						
						if(levelTo.getGrade()!=null && !levelTo.getGrade().isEmpty()){
							educationalDetails.setGrade(levelTo.getGrade());
						}
						
						if(levelTo.getYear()!=null && !levelTo.getYear().isEmpty()){
							educationalDetails.setYearOfCompletion(Integer.parseInt(levelTo.getYear()));
						}
						
						if(levelTo.getInstitute()!=null && !levelTo.getInstitute().isEmpty()){
							educationalDetails.setInstitute(levelTo.getInstitute());
						}
						
						Employee emp = new Employee();
						emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
						educationalDetails.setEmployee(emp);
						educationalDetails.setActive(true);
						educationalDetails.setCreatedBy(employeeInfoEditForm.getUserId());
						educationalDetails.setCreatedDate(new Date());
						educationalDetails.setModifiedBy(employeeInfoEditForm.getUserId());
						educationalDetails.setModifiedDate(new Date());
						educationalDeatialSet.add(educationalDetails);
					}
				}
			}
		}
		
		employee.setEducationalDetailsSet(educationalDeatialSet);
		// comparing EducationalDetails **
		Set<EmpEducationalDetails> eduDetails=empApplicantDetails.getEducationalDetailsSet();
		if(eduDetails==null && educationalDeatialSet==null){
			
		}
		else if((!eduDetails.isEmpty() && educationalDeatialSet.isEmpty()) || (!educationalDeatialSet.isEmpty() && eduDetails.isEmpty()) || (!eduDetails.isEmpty() && !educationalDeatialSet.isEmpty())){
			int count=1;
			if(!eduDetails.isEmpty() && educationalDeatialSet.isEmpty())
				count++;
			else if(!educationalDeatialSet.isEmpty() && eduDetails.isEmpty())
				count++;
			else{
				Iterator<EmpEducationalDetails> itr=eduDetails.iterator();
				Iterator<EmpEducationalDetails> itr1=educationalDeatialSet.iterator();
			while(itr.hasNext()){
				EmpEducationalDetails sessionDetails=itr.next();    
				while(itr1.hasNext()){
				   EmpEducationalDetails presentDetails=itr1.next();
				   if(sessionDetails.getId()==presentDetails.getId()){
				      if(sessionDetails.getEmpQualificationLevel().isFixedDisplay()!=null && sessionDetails.getEmpQualificationLevel().isFixedDisplay() && presentDetails.getEmpQualificationLevel().isFixedDisplay()!=null && presentDetails.getEmpQualificationLevel().isFixedDisplay()){
					     if(!sessionDetails.equals(presentDetails)){
						   count++;
					     }
				      }else{
					     if(!sessionDetails.equals(presentDetails)){
						    count++;
					     }
				      }
				      break;
				   }
				}
			}}
			if(employeeInfoEditForm.getHighQualifForAlbum()!=null && !employeeInfoEditForm.getHighQualifForAlbum().isEmpty()){
				employee.setHighQualifForAlbum(employeeInfoEditForm.getHighQualifForAlbum());
				if(empApplicantDetails.getHighQualifForAlbum()!=null){
					if(empApplicantDetails.getHighQualifForAlbum().equalsIgnoreCase(employeeInfoEditForm.getHighQualifForAlbum()))
						count++;
				}
			}else if(empApplicantDetails.getHighQualifForAlbum()!=null)
				count++;
			if(count>1){
				myProfile.setEmpEducationDetails(myProfile.getEmpEducationDetails()+1);
			}
		}// Ending of EducationalDetails
		
		if(employeeInfoEditForm.getDesignationPfId()!=null && !employeeInfoEditForm.getDesignationPfId().isEmpty()){  
			Designation designation=new Designation();
			designation.setId(Integer.parseInt(employeeInfoEditForm.getDesignationPfId()));
			employee.setDesignation(designation);
			if(empApplicantDetails.getDesignation().getId()!=Integer.parseInt(employeeInfoEditForm.getDesignationPfId())){
				myProfile.setDesignation(myProfile.getDesignation()+1);
			}
		}else if(empApplicantDetails.getDesignation()!=null)
			myProfile.setDesignation(myProfile.getDesignation()+1);
		if(employeeInfoEditForm.getDepartmentId()!=null && !employeeInfoEditForm.getDepartmentId().isEmpty()){  
			Department department=new Department();
			department.setId(Integer.parseInt(employeeInfoEditForm.getDepartmentId()));
			employee.setDepartment(department);
			if(empApplicantDetails.getDepartment().getId()!=Integer.parseInt(employeeInfoEditForm.getDepartmentId())){
				myProfile.setDepartment(myProfile.getDepartment()+1);
			}
		}else if(empApplicantDetails.getDepartment()!=null)
			myProfile.setDepartment(myProfile.getDepartment()+1);
		if(employeeInfoEditForm.getName()!=null && !employeeInfoEditForm.getName().isEmpty()){   
			employee.setFirstName(employeeInfoEditForm.getName().toUpperCase());
			if(!empApplicantDetails.getFirstName().equalsIgnoreCase(employeeInfoEditForm.getName())){
				myProfile.setName(myProfile.getName()+1);
			}
		}else if(empApplicantDetails.getFirstName()!=null && !empApplicantDetails.getFirstName().isEmpty())
			myProfile.setName(myProfile.getName()+1);
		if(employeeInfoEditForm.getuId()!=null && !employeeInfoEditForm.getuId().isEmpty()){     
			employee.setUid(employeeInfoEditForm.getuId());
			if(empApplicantDetails.getUid()!=null){
				if(!empApplicantDetails.getUid().equalsIgnoreCase(employeeInfoEditForm.getuId())){
					myProfile.setUid(myProfile.getUid()+1);
				}
			}else{
				myProfile.setUid(myProfile.getUid()+1);
			}
			
		}else if(empApplicantDetails.getUid()!=null && !empApplicantDetails.getUid().isEmpty())
			myProfile.setUid(myProfile.getUid()+1);
		if(employeeInfoEditForm.getCode()!=null && !employeeInfoEditForm.getCode().isEmpty()){    
			employee.setCode(employeeInfoEditForm.getCode());
			if(empApplicantDetails.getCode()!=null){
				if(!empApplicantDetails.getCode().equalsIgnoreCase(employeeInfoEditForm.getCode())){
					myProfile.setCode(myProfile.getCode()+1);
				}
			}else{
				myProfile.setCode(myProfile.getCode()+1);
			}
		}else if(empApplicantDetails.getCode()!=null && !empApplicantDetails.getCode().isEmpty())
			myProfile.setCode(myProfile.getCode()+1);
		if(employeeInfoEditForm.getFingerPrintId()!=null && !employeeInfoEditForm.getFingerPrintId().isEmpty()){  
			employee.setFingerPrintId(employeeInfoEditForm.getFingerPrintId());
			if(empApplicantDetails.getFingerPrintId()!=null){
				if(!empApplicantDetails.getFingerPrintId().equalsIgnoreCase(employeeInfoEditForm.getFingerPrintId())){
					myProfile.setFingerPrintId(myProfile.getFingerPrintId()+1);
				}
			}else{
				myProfile.setFingerPrintId(myProfile.getFingerPrintId());
			}
			
		}else if(empApplicantDetails.getFingerPrintId()!=null && !empApplicantDetails.getFingerPrintId().isEmpty())
			myProfile.setFingerPrintId(myProfile.getFingerPrintId()+1);
		{
			int count=1;
		if(employeeInfoEditForm.getCurrentAddressLine1()!=null && !employeeInfoEditForm.getCurrentAddressLine1().isEmpty()){
			employee.setCommunicationAddressLine1(employeeInfoEditForm.getCurrentAddressLine1());
			if(empApplicantDetails.getCommunicationAddressLine1()!=null){
				if(!empApplicantDetails.getCommunicationAddressLine1().equalsIgnoreCase(employeeInfoEditForm.getCurrentAddressLine1())){
					count++;
				}
			}else
				count++;
			
		}else if(empApplicantDetails.getCommunicationAddressLine1()!=null && !empApplicantDetails.getCommunicationAddressLine1().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getCurrentAddressLine2()!=null && !employeeInfoEditForm.getCurrentAddressLine2().isEmpty()){
			employee.setCommunicationAddressLine2(employeeInfoEditForm.getCurrentAddressLine2());
			if(empApplicantDetails.getCommunicationAddressLine2()!=null){
				if(!empApplicantDetails.getCommunicationAddressLine2().equalsIgnoreCase(employeeInfoEditForm.getCurrentAddressLine2())){
					count++;
				}
			}else
				count++;
		}else if(empApplicantDetails.getCommunicationAddressLine2()!=null && !empApplicantDetails.getCommunicationAddressLine2().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getCurrentZipCode()!=null && !employeeInfoEditForm.getCurrentZipCode().isEmpty()){
			employee.setCommunicationAddressZip(employeeInfoEditForm.getCurrentZipCode());
			if(empApplicantDetails.getCommunicationAddressZip()!=null){
				if(!empApplicantDetails.getCommunicationAddressZip().equalsIgnoreCase(employeeInfoEditForm.getCurrentZipCode())){
					count++;
				}
			}else
				count++;
		}else if(empApplicantDetails.getCommunicationAddressZip()!=null && !empApplicantDetails.getCommunicationAddressZip().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getCurrentCountryId()!=null && !employeeInfoEditForm.getCurrentCountryId().isEmpty()){
			Country currentCountry=new Country();
			currentCountry.setId(Integer.parseInt(employeeInfoEditForm.getCurrentCountryId()));
			employee.setCountryByCommunicationAddressCountryId(currentCountry);
			if(empApplicantDetails.getCountryByCommunicationAddressCountryId()!=null){
				if(empApplicantDetails.getCountryByCommunicationAddressCountryId().getId()!=Integer.parseInt(employeeInfoEditForm.getCurrentCountryId()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getCountryByCommunicationAddressCountryId()!=null)
			count++;
		
		if(employeeInfoEditForm.getCurrentState()!=null && !employeeInfoEditForm.getCurrentState().isEmpty()){
			if(employeeInfoEditForm.getCurrentState().equalsIgnoreCase("other"))
			{
				if(employeeInfoEditForm.getOtherCurrentState()!=null && !employeeInfoEditForm.getOtherCurrentState().isEmpty()){
					employee.setCommunicationAddressStateOthers(employeeInfoEditForm.getOtherCurrentState());
					if(empApplicantDetails.getCommunicationAddressStateOthers()!=null){
						if(empApplicantDetails.getCommunicationAddressStateOthers().equalsIgnoreCase(employeeInfoEditForm.getOtherCurrentState()))
							count++;
					}else
						count++;
				}else if(empApplicantDetails.getCommunicationAddressStateOthers()!=null && !empApplicantDetails.getCommunicationAddressStateOthers().isEmpty())
					count++;
			}
			else
			{
			State currentState=new State();
			currentState.setId(Integer.parseInt(employeeInfoEditForm.getCurrentState()));
			employee.setStateByCommunicationAddressStateId(currentState);
			if(empApplicantDetails.getStateByCommunicationAddressStateId()!=null){
				if(empApplicantDetails.getStateByCommunicationAddressStateId().getId()!=Integer.parseInt(employeeInfoEditForm.getCurrentState()))
					count++;
			}else
				count++;
			}
		}else if(empApplicantDetails.getStateByCommunicationAddressStateId()!=null)
			count++;
		
		if(employeeInfoEditForm.getCurrentCity()!=null && !employeeInfoEditForm.getCurrentCity().isEmpty()){
			employee.setCommunicationAddressCity(employeeInfoEditForm.getCurrentCity());
			if(empApplicantDetails.getCommunicationAddressCity()!=null){
				if(!empApplicantDetails.getCommunicationAddressCity().trim().equalsIgnoreCase(employeeInfoEditForm.getCurrentCity().trim()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getCommunicationAddressCity()!=null && !empApplicantDetails.getCommunicationAddressCity().isEmpty())
			count++;
		if(count>1){
			myProfile.setCurrentAddress(myProfile.getCurrentAddress()+1);
		}
		}
		if(employeeInfoEditForm.getSameAddress().equalsIgnoreCase("true")){
			int count=1;
			if(!empApplicantDetails.getIsSameAddress().toString().equalsIgnoreCase(employeeInfoEditForm.getSameAddress())){
				myProfile.setSameAddress(myProfile.getSameAddress()+1);
			}
			if(employeeInfoEditForm.getCurrentAddressLine1()!=null && !employeeInfoEditForm.getCurrentAddressLine1().isEmpty()){
				employee.setPermanentAddressLine1(employeeInfoEditForm.getCurrentAddressLine1());
				if(empApplicantDetails.getPermanentAddressLine1()!=null){
					if(!empApplicantDetails.getPermanentAddressLine1().equalsIgnoreCase(employeeInfoEditForm.getCurrentAddressLine1())){
						count++;
					}
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressLine1()!=null && !empApplicantDetails.getPermanentAddressLine1().isEmpty())
				count++;
			if(employeeInfoEditForm.getCurrentAddressLine2()!=null && !employeeInfoEditForm.getCurrentAddressLine2().isEmpty()){
				employee.setPermanentAddressLine2(employeeInfoEditForm.getCurrentAddressLine2());
				if(empApplicantDetails.getPermanentAddressLine2()!=null){
				    if(!empApplicantDetails.getPermanentAddressLine2().equalsIgnoreCase(employeeInfoEditForm.getCurrentAddressLine2()))
					    count++;
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressLine2()!=null && !empApplicantDetails.getPermanentAddressLine2().isEmpty())
				count++;
			
						
			if(employeeInfoEditForm.getCurrentZipCode()!=null && !employeeInfoEditForm.getCurrentZipCode().isEmpty()){
				employee.setPermanentAddressZip(employeeInfoEditForm.getCurrentZipCode());
				if(empApplicantDetails.getPermanentAddressZip()!=null){
					if(!empApplicantDetails.getPermanentAddressZip().equalsIgnoreCase(employeeInfoEditForm.getCurrentZipCode()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressZip()!=null && !empApplicantDetails.getPermanentAddressZip().isEmpty())
				count++;
			
			if(employeeInfoEditForm.getCurrentCountryId()!=null && !employeeInfoEditForm.getCurrentCountryId().isEmpty()){
				Country currentCountry=new Country();
				currentCountry.setId(Integer.parseInt(employeeInfoEditForm.getCurrentCountryId()));
				employee.setCountryByPermanentAddressCountryId(currentCountry);
				if(empApplicantDetails.getCountryByPermanentAddressCountryId()!=null){
					if(empApplicantDetails.getCountryByPermanentAddressCountryId().getId()!=Integer.parseInt(employeeInfoEditForm.getCurrentCountryId()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getCountryByPermanentAddressCountryId()!=null)
				count++;
			
			if(employeeInfoEditForm.getCurrentState()!=null && !employeeInfoEditForm.getCurrentState().isEmpty()){
				if(employeeInfoEditForm.getCurrentState().equalsIgnoreCase("other"))
				{
					if(employeeInfoEditForm.getOtherCurrentState()!=null && !employeeInfoEditForm.getOtherCurrentState().isEmpty()){
						employee.setPermanentAddressStateOthers(employeeInfoEditForm.getOtherCurrentState());
						if(empApplicantDetails.getPermanentAddressStateOthers()!=null){
							if(!empApplicantDetails.getPermanentAddressStateOthers().equalsIgnoreCase(employeeInfoEditForm.getOtherCurrentState()))
								count++;
						}else
							count++;
					}else if(empApplicantDetails.getPermanentAddressStateOthers()!=null && !empApplicantDetails.getPermanentAddressStateOthers().isEmpty())
						count++;
				}
				else
				{
				State currentState=new State();
				currentState.setId(Integer.parseInt(employeeInfoEditForm.getCurrentState()));
				employee.setStateByPermanentAddressStateId(currentState);
				if(empApplicantDetails.getStateByPermanentAddressStateId()!=null){
					if(empApplicantDetails.getStateByPermanentAddressStateId().getId()!=Integer.parseInt(employeeInfoEditForm.getCurrentState()))
						count++;
				}else
					count++;
				}
			}else if(empApplicantDetails.getStateByPermanentAddressStateId()!=null)
				count++;
			
			if(employeeInfoEditForm.getCurrentCity()!=null && !employeeInfoEditForm.getCurrentCity().isEmpty()){
				employee.setPermanentAddressCity(employeeInfoEditForm.getCurrentCity());
				if(empApplicantDetails.getPermanentAddressCity()!=null){
					if(!empApplicantDetails.getPermanentAddressCity().equalsIgnoreCase(employeeInfoEditForm.getCurrentCity()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressCity()!=null && !empApplicantDetails.getPermanentAddressCity().isEmpty())
				count++;
			if(count>1){
				myProfile.setPermanentAddress(myProfile.getPermanentAddress()+1);
			}
			
		}else{
			int count=1;
			if(employeeInfoEditForm.getAddressLine1()!=null && !employeeInfoEditForm.getAddressLine1().isEmpty()){
				employee.setPermanentAddressLine1(employeeInfoEditForm.getAddressLine1());
				if(empApplicantDetails.getPermanentAddressLine1()!=null){
					if(!empApplicantDetails.getPermanentAddressLine1().equalsIgnoreCase(employeeInfoEditForm.getAddressLine1())){
						count++;
					}
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressLine1()!=null && !empApplicantDetails.getPermanentAddressLine1().isEmpty())
				count++;
			
			if(employeeInfoEditForm.getAddressLine2()!=null && !employeeInfoEditForm.getAddressLine2().isEmpty()){
				employee.setPermanentAddressLine2(employeeInfoEditForm.getAddressLine2());
				if(empApplicantDetails.getPermanentAddressLine2()!=null){
				    if(!empApplicantDetails.getPermanentAddressLine2().equalsIgnoreCase(employeeInfoEditForm.getCurrentAddressLine2()))
					    count++;
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressLine2()!=null && !empApplicantDetails.getPermanentAddressLine2().isEmpty())
				count++;
			
			if(employeeInfoEditForm.getPermanentZipCode()!=null && !employeeInfoEditForm.getPermanentZipCode().isEmpty()){
				employee.setPermanentAddressZip(employeeInfoEditForm.getPermanentZipCode());
				if(empApplicantDetails.getPermanentAddressZip()!=null){
					if(!empApplicantDetails.getPermanentAddressZip().equalsIgnoreCase(employeeInfoEditForm.getCurrentZipCode()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressZip()!=null && !empApplicantDetails.getPermanentAddressZip().isEmpty())
				count++;
			
			if(employeeInfoEditForm.getCountryId()!=null && !employeeInfoEditForm.getCountryId().isEmpty()){
				Country country=new Country();
				country.setId(Integer.parseInt(employeeInfoEditForm.getCountryId()));
				employee.setCountryByPermanentAddressCountryId(country);
				if(empApplicantDetails.getCountryByPermanentAddressCountryId()!=null){
					if(empApplicantDetails.getCountryByPermanentAddressCountryId().getId()!=Integer.parseInt(employeeInfoEditForm.getCountryId()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getCountryByPermanentAddressCountryId()!=null)
				count++;
			
			if(employeeInfoEditForm.getStateId()!=null && !employeeInfoEditForm.getStateId().isEmpty()){
				if(employeeInfoEditForm.getStateId().equalsIgnoreCase("other"))
				{
					if(employeeInfoEditForm.getOtherPermanentState()!=null && !employeeInfoEditForm.getOtherPermanentState().isEmpty()){
						employee.setPermanentAddressStateOthers(employeeInfoEditForm.getOtherPermanentState());
						if(empApplicantDetails.getPermanentAddressStateOthers()!=null){
							if(!empApplicantDetails.getPermanentAddressStateOthers().equalsIgnoreCase(employeeInfoEditForm.getOtherPermanentState()))
								count++;
						}else
							count++;
					}else if(empApplicantDetails.getPermanentAddressStateOthers()!=null && !empApplicantDetails.getPermanentAddressStateOthers().isEmpty())
						count++;
				}
				else
				{
				State state=new State();
				state.setId(Integer.parseInt(employeeInfoEditForm.getStateId()));
				employee.setStateByPermanentAddressStateId(state);
				if(empApplicantDetails.getStateByPermanentAddressStateId()!=null){
					if(empApplicantDetails.getStateByPermanentAddressStateId().getId()!=Integer.parseInt(employeeInfoEditForm.getStateId()))
						count++;
				}else
					count++;
				
			    }
			}else if(empApplicantDetails.getStateByPermanentAddressStateId()!=null)
				count++;
			if(employeeInfoEditForm.getCity()!=null && !employeeInfoEditForm.getCity().isEmpty()){
				employee.setPermanentAddressCity(employeeInfoEditForm.getCity());
				if(empApplicantDetails.getPermanentAddressCity()!=null){
					if(!empApplicantDetails.getPermanentAddressCity().equalsIgnoreCase(employeeInfoEditForm.getCity()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getPermanentAddressCity()!=null && !empApplicantDetails.getPermanentAddressCity().isEmpty())
				count++;
			if(count>1){
				myProfile.setPermanentAddress(myProfile.getPermanentAddress()+1);
			}
		}
		if(employeeInfoEditForm.getNationalityId()!=null && !employeeInfoEditForm.getNationalityId().isEmpty()){    
			Nationality nationality=new Nationality();
			nationality.setId(Integer.parseInt(employeeInfoEditForm.getNationalityId()));
			employee.setNationality(nationality);
			if(empApplicantDetails.getNationality()!=null){
				if(empApplicantDetails.getNationality().getId()!=Integer.parseInt(employeeInfoEditForm.getNationalityId())){
					myProfile.setNationality(myProfile.getNationality()+1);
				}
			}else{
				myProfile.setNationality(myProfile.getNationality()+1);
			}
		}else if(empApplicantDetails.getNationality()!=null)
			myProfile.setNationality(myProfile.getNationality()+1);
		 
		if(employeeInfoEditForm.getGender()!=null && !employeeInfoEditForm.getGender().isEmpty()){     
			employee.setGender(employeeInfoEditForm.getGender());
			if(empApplicantDetails.getGender()!=null){
				if(!empApplicantDetails.getGender().equalsIgnoreCase(employeeInfoEditForm.getGender())){
					myProfile.setGender(myProfile.getGender()+1);
				}
			}else{
				myProfile.setGender(myProfile.getGender()+1);
			}
		}else if(empApplicantDetails.getGender()!=null && !empApplicantDetails.getGender().isEmpty())
			myProfile.setGender(myProfile.getGender()+1);
		
		if(employeeInfoEditForm.getMaritalStatus()!=null && !employeeInfoEditForm.getMaritalStatus().isEmpty()){     
			employee.setMaritalStatus(employeeInfoEditForm.getMaritalStatus());
			if(empApplicantDetails.getMaritalStatus()!=null){
				if(!empApplicantDetails.getMaritalStatus().equalsIgnoreCase(employeeInfoEditForm.getMaritalStatus())){
					myProfile.setMaritalStatus(myProfile.getMaritalStatus()+1);
				}
			}else{
				myProfile.setMaritalStatus(myProfile.getMaritalStatus()+1);
			}
		}else if(empApplicantDetails.getMaritalStatus()!=null && !empApplicantDetails.getMaritalStatus().isEmpty())
			myProfile.setMaritalStatus(myProfile.getMaritalStatus()+1);
	
		if(employeeInfoEditForm.getDateOfBirth()!=null && !employeeInfoEditForm.getDateOfBirth().isEmpty()){  
			employee.setDob(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfBirth()));
			if(empApplicantDetails.getDob()!=null){
				if(!CommonUtil.formatSqlDate1(empApplicantDetails.getDob().toString()).equalsIgnoreCase(employeeInfoEditForm.getDateOfBirth())){
					myProfile.setDateOfBirth(myProfile.getDateOfBirth()+1);
				}
			}else{
				myProfile.setDateOfBirth(myProfile.getDateOfBirth()+1);
			}
		}else if(empApplicantDetails.getDob()!=null)
			myProfile.setDateOfBirth(myProfile.getDateOfBirth()+1);
		if(employeeInfoEditForm.getDateOfJoining()!=null && !employeeInfoEditForm.getDateOfJoining().isEmpty()){   // 
			employee.setDoj(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfJoining()));
			if(empApplicantDetails.getDoj()!=null){
				if(!CommonUtil.formatSqlDate1(empApplicantDetails.getDoj().toString()).equalsIgnoreCase(employeeInfoEditForm.getDateOfJoining())){
					myProfile.setDateOfJoin(myProfile.getDateOfJoin()+1);
				}
			}else{
				myProfile.setDateOfJoin(myProfile.getDateOfJoin()+1);
			}
		}else if(empApplicantDetails.getDoj()!=null)
			myProfile.setDateOfJoin(myProfile.getDateOfJoin()+1);
		if(employeeInfoEditForm.getDateOfLeaving()!=null && !employeeInfoEditForm.getDateOfLeaving().isEmpty()){    
			employee.setDateOfLeaving(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfLeaving()));
		}
		if(employeeInfoEditForm.getDateOfResignation()!=null && !employeeInfoEditForm.getDateOfResignation().isEmpty()){    
			employee.setDateOfResignation(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfResignation()));
		} 
		if(employeeInfoEditForm.getRetirementDate()!=null && !employeeInfoEditForm.getRetirementDate().isEmpty()){     
			employee.setRetirementDate(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getRetirementDate()));
			if(empApplicantDetails.getRetirementDate()!=null){
				if(!CommonUtil.formatSqlDate1(empApplicantDetails.getRetirementDate().toString()).equalsIgnoreCase(employeeInfoEditForm.getRetirementDate())){
					myProfile.setDateOfRetirement(myProfile.getDateOfRetirement()+1);
				}
			}else{
				myProfile.setDateOfRetirement(myProfile.getDateOfRetirement()+1);
			}
		}else if(empApplicantDetails.getRetirementDate()!=null)
			myProfile.setDateOfRetirement(myProfile.getDateOfRetirement()+1);
		if(employeeInfoEditForm.getEmail()!=null && !employeeInfoEditForm.getEmail().isEmpty()){       
			employee.setEmail(employeeInfoEditForm.getEmail());
			if(empApplicantDetails.getEmail()!=null){
				if(!empApplicantDetails.getEmail().equalsIgnoreCase(employeeInfoEditForm.getEmail())){
					myProfile.setOtherEmail(myProfile.getOtherEmail()+1);
				}
			}else{
				myProfile.setOtherEmail(myProfile.getOtherEmail()+1);
			}
		}else if(empApplicantDetails.getOtherEmail()!=null && !empApplicantDetails.getOtherEmail().isEmpty())
			myProfile.setOtherEmail(myProfile.getOtherEmail()+1);
		
			
		if(employeeInfoEditForm.getIsSmartCardDataDelivered()!=null && !employeeInfoEditForm.getIsSmartCardDataDelivered().isEmpty()){
			String Value= employeeInfoEditForm.getIsSmartCardDataDelivered();     
			if(Value.equals("1"))
			employee.setIsSCDataDelivered(true);
			else
			employee.setIsSCDataDelivered(false);
			if(empApplicantDetails.getIsSCDataDelivered()!=null){
				if(!empApplicantDetails.getIsSCDataDelivered().toString().equalsIgnoreCase(employee.getIsSCDataDelivered().toString())){
					myProfile.setIsSmartCardDelivered(myProfile.getIsSmartCardDelivered()+1);
				}
			}else{
				myProfile.setIsSmartCardDelivered(myProfile.getIsSmartCardDelivered()+1);
			}
		}else if(empApplicantDetails.getIsSCDataDelivered()!=null)
			myProfile.setIsSmartCardDelivered(myProfile.getIsSmartCardDelivered()+1);
		if(employeeInfoEditForm.getIsSmartCardDataGenerated()!=null && !employeeInfoEditForm.getIsSmartCardDataGenerated().isEmpty()){
			String Value= employeeInfoEditForm.getIsSmartCardDataGenerated();      
			if(Value.equals("1"))
			employee.setIsSCDataGenerated(true);
			else
			employee.setIsSCDataGenerated(false);
			if(empApplicantDetails.getIsSCDataGenerated()!=null){
				if(!empApplicantDetails.getIsSCDataGenerated().toString().equalsIgnoreCase(employee.getIsSCDataGenerated().toString())){
					myProfile.setIsSCDataGenerated(myProfile.getIsSCDataGenerated()+1);
				}
			}else{
				myProfile.setIsSCDataGenerated(myProfile.getIsSCDataGenerated()+1);
			}
		}else if(empApplicantDetails.getIsSCDataGenerated()!=null)
			myProfile.setIsSCDataGenerated(myProfile.getIsSCDataGenerated()+1);
		
		if(employeeInfoEditForm.getSmartCardNo()!=null && !employeeInfoEditForm.getSmartCardNo().isEmpty()){
			employee.setSmartCardNo(employeeInfoEditForm.getSmartCardNo());    
			if(empApplicantDetails.getSmartCardNo()!=null){
				if(!empApplicantDetails.getSmartCardNo().equalsIgnoreCase(employeeInfoEditForm.getSmartCardNo())){
						myProfile.setSmartCardNo(myProfile.getSmartCardNo()+1);
				}
			}else{
				myProfile.setSmartCardNo(myProfile.getSmartCardNo()+1);
			}
		}else if(empApplicantDetails.getSmartCardNo()!=null && !empApplicantDetails.getSmartCardNo().isEmpty())
			myProfile.setSmartCardNo(myProfile.getSmartCardNo()+1);
		/*if(employeeInfoEditForm.getReservationCategory()!=null && employeeInfoEditForm.getReservationCategory().length>0){
			String reservationCategory="";
			String[] categories=employeeInfoEditForm.getReservationCategory();
			for(int i=0;i<categories.length;){
				reservationCategory=reservationCategory+categories[i];
				i++;
				if(i<categories.length)
					reservationCategory=reservationCategory+",";
			}
			employee.setReservationCategory(reservationCategory);
		}*/
		if(employeeInfoEditForm.getReservationCategory()!=null && !employeeInfoEditForm.getReservationCategory().isEmpty()){
			employee.setReservationCategory(employeeInfoEditForm.getReservationCategory());    
			if(empApplicantDetails.getReservationCategory()!=null){
				if(!empApplicantDetails.getReservationCategory().equalsIgnoreCase(employeeInfoEditForm.getReservationCategory())){
					myProfile.setReservationCategory(myProfile.getReservationCategory()+1);
				}
			}else{
				myProfile.setReservationCategory(myProfile.getReservationCategory()+1);
			}
		}else if(empApplicantDetails.getReservationCategory()!=null && !empApplicantDetails.getReservationCategory().isEmpty())
			myProfile.setReservationCategory(myProfile.getReservationCategory()+1);
		{
			int count=1;
		if(employeeInfoEditForm.getHomePhone1()!=null && !employeeInfoEditForm.getHomePhone1().isEmpty()){
			employee.setCurrentAddressHomeTelephone1(employeeInfoEditForm.getHomePhone1());
			if(empApplicantDetails.getCurrentAddressHomeTelephone1()!=null){
				if(!empApplicantDetails.getCurrentAddressHomeTelephone1().equalsIgnoreCase(employeeInfoEditForm.getHomePhone1()))
					count++;
			}
		}else if(empApplicantDetails.getCurrentAddressHomeTelephone1()!=null && !empApplicantDetails.getCurrentAddressHomeTelephone1().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getHomePhone2()!=null && !employeeInfoEditForm.getHomePhone2().isEmpty()){
			employee.setCurrentAddressHomeTelephone2(employeeInfoEditForm.getHomePhone2());
			if(empApplicantDetails.getCurrentAddressHomeTelephone2()!=null){
				if(!empApplicantDetails.getCurrentAddressHomeTelephone2().equalsIgnoreCase(employeeInfoEditForm.getHomePhone2()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getCurrentAddressHomeTelephone2()!=null && !empApplicantDetails.getCurrentAddressHomeTelephone2().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getHomePhone3()!=null && !employeeInfoEditForm.getHomePhone3().isEmpty()){
			employee.setCurrentAddressHomeTelephone3(employeeInfoEditForm.getHomePhone3());
			if(empApplicantDetails.getCurrentAddressHomeTelephone3()!=null){
				if(!empApplicantDetails.getCurrentAddressHomeTelephone3().equalsIgnoreCase(employeeInfoEditForm.getHomePhone3()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getCurrentAddressHomeTelephone3()!=null && !empApplicantDetails.getCurrentAddressHomeTelephone3().isEmpty())
			count++;
		if(employeeInfoEditForm.getWorkPhNo1()!=null && !employeeInfoEditForm.getWorkPhNo1().isEmpty()){
			employee.setCurrentAddressWorkTelephone1(employeeInfoEditForm.getWorkPhNo1());
			if(empApplicantDetails.getCurrentAddressWorkTelephone1()!=null){
				if(!empApplicantDetails.getCurrentAddressWorkTelephone1().equalsIgnoreCase(employeeInfoEditForm.getWorkPhNo1()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getCurrentAddressWorkTelephone1()!=null && !empApplicantDetails.getCurrentAddressWorkTelephone1().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getWorkPhNo2()!=null && !employeeInfoEditForm.getWorkPhNo2().isEmpty()){
			employee.setCurrentAddressWorkTelephone2(employeeInfoEditForm.getWorkPhNo2());
			if(empApplicantDetails.getCurrentAddressWorkTelephone2()!=null){
				if(!empApplicantDetails.getCurrentAddressWorkTelephone2().equalsIgnoreCase(employeeInfoEditForm.getWorkPhNo2()))
					count++;
			}
		}else if(empApplicantDetails.getCurrentAddressWorkTelephone2()!=null && !empApplicantDetails.getCurrentAddressWorkTelephone2().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getWorkPhNo3()!=null && !employeeInfoEditForm.getWorkPhNo3().isEmpty()){
			employee.setCurrentAddressWorkTelephone3(employeeInfoEditForm.getWorkPhNo3());
			if(empApplicantDetails.getCurrentAddressWorkTelephone3()!=null){
				if(!empApplicantDetails.getCurrentAddressWorkTelephone3().equalsIgnoreCase(employeeInfoEditForm.getWorkPhNo3()))
					count++;
			}
		}else if(empApplicantDetails.getCurrentAddressWorkTelephone3()!=null && !empApplicantDetails.getCurrentAddressWorkTelephone3().isEmpty())
			count++;
		if(count>1){
			myProfile.setHomeTelephone(myProfile.getHomeTelephone()+1);
		}
		}
		if(employeeInfoEditForm.getMobileNo1()!=null && !employeeInfoEditForm.getMobileNo1().isEmpty()){
			employee.setCurrentAddressMobile1(employeeInfoEditForm.getMobileNo1());
			if(empApplicantDetails.getCurrentAddressMobile1()!=null){
				if(!empApplicantDetails.getCurrentAddressMobile1().equalsIgnoreCase(employeeInfoEditForm.getMobileNo1())){
					myProfile.setMobile(myProfile.getMobile()+1);
				}
			}else{
				myProfile.setMobile(myProfile.getMobile()+1);
			}
		}else if(empApplicantDetails.getCurrentAddressMobile1()!=null && !empApplicantDetails.getCurrentAddressMobile1().isEmpty())
			myProfile.setMobile(myProfile.getMobile()+1);
		
		
		if(employeeInfoEditForm.getEmpSubjectAreaId()!=null && !employeeInfoEditForm.getEmpSubjectAreaId().isEmpty()){
			SubjectAreaBO subjectArea=new SubjectAreaBO();        
			subjectArea.setId(Integer.parseInt(employeeInfoEditForm.getEmpSubjectAreaId()));
			employee.setEmpSubjectArea(subjectArea);
			if(empApplicantDetails.getEmpSubjectArea()!=null){
				if(empApplicantDetails.getEmpSubjectArea().getId()!=Integer.parseInt(employeeInfoEditForm.getEmpSubjectAreaId())){
					myProfile.setSubjectArea(myProfile.getSubjectArea()+1);
				}
			}else{
				myProfile.setSubjectArea(myProfile.getSubjectArea()+1);
			}
		}else if(empApplicantDetails.getEmpSubjectArea()!=null)
			myProfile.setSubjectArea(myProfile.getSubjectArea()+1);
		
	/*	if(employeeInfoEditForm.getEmpJobTypeId()!=null && !employeeInfoEditForm.getEmpJobTypeId().isEmpty()){
			EmpJobType empJobType=new EmpJobType();
			empJobType.setId(Integer.parseInt(employeeInfoEditForm.getEmpJobTypeId()));
			employee.setEmpJobType(empJobType);
			if(empApplicantDetails.getEmpJobType()!=null){
				if(empApplicantDetails.getEmpJobType().getId()!=Integer.parseInt(employeeInfoEditForm.getEmpJobTypeId())){
					myProfile.setEmpJobType(myProfile.getEmpJobType()+1);
				}
			}else{
				myProfile.setEmpJobType(myProfile.getEmpJobType()+1);
			}
		}else if(empApplicantDetails.getEmpJobType()!=null)
			myProfile.setEmpJobType(myProfile.getEmpJobType()+1);*/
		
		if(employeeInfoEditForm.getNoOfPublicationsRefered()!=null && !employeeInfoEditForm.getNoOfPublicationsRefered().isEmpty()){
			employee.setNoOfPublicationsRefered(employeeInfoEditForm.getNoOfPublicationsRefered());
			if(empApplicantDetails.getNoOfPublicationsRefered()!=null){
			     if(!empApplicantDetails.getNoOfPublicationsRefered().equalsIgnoreCase(employeeInfoEditForm.getNoOfPublicationsRefered())){
			    	 myProfile.setNoOfPublicationsRefered(myProfile.getNoOfPublicationsRefered()+1);
			     }
			}else{
				myProfile.setNoOfPublicationsRefered(myProfile.getNoOfPublicationsRefered()+1);
			}
		}else if(empApplicantDetails.getNoOfPublicationsRefered()!=null && !empApplicantDetails.getNoOfPublicationsRefered().isEmpty())
			myProfile.setNoOfPublicationsRefered(myProfile.getNoOfPublicationsRefered()+1);
		
		if(employeeInfoEditForm.getNoOfPublicationsNotRefered()!=null && !employeeInfoEditForm.getNoOfPublicationsNotRefered().isEmpty()){
			employee.setNoOfPublicationsNotRefered(employeeInfoEditForm.getNoOfPublicationsNotRefered());
			if(empApplicantDetails.getNoOfPublicationsNotRefered()!=null){
				if(!empApplicantDetails.getNoOfPublicationsNotRefered().equalsIgnoreCase(employeeInfoEditForm.getNoOfPublicationsNotRefered())){
					myProfile.setNoOfPublicationsNotRefered(myProfile.getNoOfPublicationsNotRefered()+1);
				}
			}else{
				myProfile.setNoOfPublicationsNotRefered(myProfile.getNoOfPublicationsNotRefered()+1);
			}
		}else if(empApplicantDetails.getNoOfPublicationsNotRefered()!=null && !empApplicantDetails.getNoOfPublicationsNotRefered().isEmpty())
			myProfile.setNoOfPublicationsNotRefered(myProfile.getNoOfPublicationsNotRefered()+1);
				
		if(employeeInfoEditForm.getBooks()!=null && !employeeInfoEditForm.getBooks().isEmpty()){
			employee.setBooks(employeeInfoEditForm.getBooks());
			if(empApplicantDetails.getBooks()!=null){
				if(!empApplicantDetails.getBooks().equalsIgnoreCase(employeeInfoEditForm.getBooks())){
					myProfile.setBooks(myProfile.getBooks()+1);
				}
			}else{
				myProfile.setBooks(myProfile.getBooks()+1);
			}
		}else if(empApplicantDetails.getBooks()!=null && !empApplicantDetails.getBooks().isEmpty())
			myProfile.setBooks(myProfile.getBooks()+1);
		{ 
			int count=1;
			if(employeeInfoEditForm.getDateOfResignation()!=null && !employeeInfoEditForm.getDateOfResignation().isEmpty()){
				employee.setDateOfResignation(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfResignation()));
				if(empApplicantDetails.getDateOfResignation()!=null){
					if(!empApplicantDetails.getDateOfResignation().toString().equalsIgnoreCase(employeeInfoEditForm.getDateOfResignation()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getDateOfResignation()!=null)
				count++;
			if(employeeInfoEditForm.getDateOfLeaving()!=null && !employeeInfoEditForm.getDateOfLeaving().isEmpty()){
				employee.setDateOfLeaving(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfLeaving()));
				if(empApplicantDetails.getDateOfLeaving()!=null){
					if(!empApplicantDetails.getDateOfLeaving().toString().equalsIgnoreCase(employeeInfoEditForm.getDateOfLeaving()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getDateOfLeaving()!=null)
				count++;
			if(employeeInfoEditForm.getReasonOfLeaving()!=null && !employeeInfoEditForm.getReasonOfLeaving().isEmpty()){
				employee.setReasonOfLeaving(employeeInfoEditForm.getReasonOfLeaving());
				if(empApplicantDetails.getReasonOfLeaving()!=null){
					if(!empApplicantDetails.getReasonOfLeaving().equalsIgnoreCase(employeeInfoEditForm.getReasonOfLeaving()))
						count++;
				}else
					count++;
			}else if(empApplicantDetails.getReasonOfLeaving()!=null && !empApplicantDetails.getReasonOfLeaving().isEmpty())
				count++;
			if(count>1)
				myProfile.setResignationDetails(myProfile.getResignationDetails()+1);
			
		}
		
		if(employeeInfoEditForm.getEmpQualificationLevelId()!=null && !employeeInfoEditForm.getEmpQualificationLevelId().isEmpty()){
			QualificationLevelBO empQualificationLevel=new QualificationLevelBO();
			empQualificationLevel.setId(Integer.parseInt(employeeInfoEditForm.getEmpQualificationLevelId()));
			employee.setEmpQualificationLevel(empQualificationLevel);
			
		}
		
		{
			int count=1;
		if(employeeInfoEditForm.getPayScaleId()!=null && !employeeInfoEditForm.getPayScaleId().isEmpty()){
			PayScaleBO payScaleBo=new PayScaleBO();
			payScaleBo.setId(Integer.parseInt(employeeInfoEditForm.getPayScaleId()));
			employee.setPayScaleId(payScaleBo);
			if(empApplicantDetails.getPayScaleId()!=null){
				if(empApplicantDetails.getPayScaleId().getId()!=Integer.parseInt(employeeInfoEditForm.getPayScaleId()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getPayScaleId()!=null)
			count++;
		if(employeeInfoEditForm.getScale()!=null && !employeeInfoEditForm.getScale().isEmpty()){
			employee.setScale(employeeInfoEditForm.getScale());
			if(empApplicantDetails.getScale()!=null){
				if(!empApplicantDetails.getScale().equalsIgnoreCase(employeeInfoEditForm.getScale()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getScale()!=null && !empApplicantDetails.getScale().isEmpty())
			count++;
		/*if(employeeInfoEditForm.getBasicPay()!=null && !employeeInfoEditForm.getBasicPay().isEmpty()){
			employee.setBasicPay(employeeInfoEditForm.getBasicPay());
			if(empApplicantDetails.getBasicPay()!=null){
				if(!empApplicantDetails.getBasicPay().equalsIgnoreCase(employeeInfoEditForm.getBasicPay()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getBasicPay()!=null && !empApplicantDetails.getBasicPay().isEmpty())
			count++;*/
		
		if(employeeInfoEditForm.getGrossPay()!=null && !employeeInfoEditForm.getGrossPay().isEmpty()){
			employee.setGrossPay(employeeInfoEditForm.getGrossPay());
			if(empApplicantDetails.getGrossPay()!=null){
				if(!empApplicantDetails.getGrossPay().equalsIgnoreCase(employeeInfoEditForm.getGrossPay()))
					count++;
			}else
				count++;
		}else if(empApplicantDetails.getGrossPay()!=null && !empApplicantDetails.getGrossPay().isEmpty())
			count++;
		if(count>1){
			myProfile.setPayScale(myProfile.getPayScale()+1);
		}
		}
	/*	if(employeeInfoEditForm.getGrade()!=null && !employeeInfoEditForm.getGrade().isEmpty()){
			employee.setGrade(employeeInfoEditForm.getGrade());
			if(empApplicantDetails.getGrade()!=null){
				if(!empApplicantDetails.getGrade().equalsIgnoreCase(employeeInfoEditForm.getGrade())){
					myProfile.setGrade(myProfile.getGrade()+1);
				}
			}else{
				myProfile.setGrade(myProfile.getGrade()+1);
			}
		}else if(empApplicantDetails.getGrade()!=null && !empApplicantDetails.getGrade().isEmpty())
			myProfile.setGrade(myProfile.getGrade()+1);*/
		if(employeeInfoEditForm.getRetirementDate()!=null && !employeeInfoEditForm.getRetirementDate().isEmpty()){
			employee.setRetirementDate(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getRetirementDate()));
		}
		if(employeeInfoEditForm.getRejoinDate()!=null && !employeeInfoEditForm.getRejoinDate().isEmpty()){
			employee.setRejoinDate(CommonUtil.ConvertStringToDate(employeeInfoEditForm.getRejoinDate()));
		}
		{
			int count=1;
		if(employeeInfoEditForm.getTimeIn()!=null && !employeeInfoEditForm.getTimeIn().isEmpty() && employeeInfoEditForm.getTimeInMin()!=null && !employeeInfoEditForm.getTimeInMin().isEmpty()){
			employee.setTimeIn(employeeInfoEditForm.getTimeIn()+":"+employeeInfoEditForm.getTimeInMin());
				if(!empApplicantDetails.getTimeIn().equalsIgnoreCase(employeeInfoEditForm.getTimeIn()))
					count++;
		}else if(empApplicantDetails.getTimeIn()!=null && !empApplicantDetails.getTimeIn().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getTimeInEnds()!=null && !employeeInfoEditForm.getTimeInEnds().isEmpty()&& employeeInfoEditForm.getTimeInEndMIn()!=null && !employeeInfoEditForm.getTimeInEndMIn().isEmpty()){
			employee.setTimeInEnds(employeeInfoEditForm.getTimeInEnds()+":"+employeeInfoEditForm.getTimeInEndMIn());
				if(!empApplicantDetails.getTimeInEnds().equalsIgnoreCase(employeeInfoEditForm.getTimeInEnds()))
					count++;
		}else if(empApplicantDetails.getTimeInEnds()!=null && !empApplicantDetails.getTimeInEnds().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getTimeOut()!=null && !employeeInfoEditForm.getTimeOut().isEmpty() && employeeInfoEditForm.getTimeOutMin()!=null && !employeeInfoEditForm.getTimeOutMin().isEmpty()){
			employee.setTimeOut(employeeInfoEditForm.getTimeOut()+":"+employeeInfoEditForm.getTimeOutMin());
				if(!empApplicantDetails.getTimeOut().equalsIgnoreCase(employeeInfoEditForm.getTimeOut()))
					count++;
		}else if(empApplicantDetails.getTimeOut()!=null && !empApplicantDetails.getTimeOut().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getSaturdayTimeOut()!=null && !employeeInfoEditForm.getSaturdayTimeOut().isEmpty() && employeeInfoEditForm.getSaturdayTimeOutMin()!=null && !employeeInfoEditForm.getSaturdayTimeOutMin().isEmpty()){
			employee.setSaturdayTimeOut(employeeInfoEditForm.getSaturdayTimeOut()+":"+employeeInfoEditForm.getSaturdayTimeOutMin());
				if(!empApplicantDetails.getSaturdayTimeOut().equalsIgnoreCase(employeeInfoEditForm.getSaturdayTimeOut()))
					count++;
		}else if(empApplicantDetails.getSaturdayTimeOut()!=null && !empApplicantDetails.getSaturdayTimeOut().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getHalfDayStartTime()!=null && !employeeInfoEditForm.getHalfDayStartTime().isEmpty() && employeeInfoEditForm.getHalfDayStartTimeMin()!=null && !employeeInfoEditForm.getHalfDayStartTimeMin().isEmpty()){
			employee.setHalfDayStartTime(employeeInfoEditForm.getHalfDayStartTime()+":"+employeeInfoEditForm.getHalfDayStartTimeMin());
				if(!empApplicantDetails.getHalfDayStartTime().equalsIgnoreCase(employeeInfoEditForm.getHalfDayStartTime()))
					count++;
		}else if(empApplicantDetails.getHalfDayStartTime()!=null && !empApplicantDetails.getHalfDayStartTime().isEmpty())
			count++;
		
		if(employeeInfoEditForm.getHalfDayEndTime()!=null && !employeeInfoEditForm.getHalfDayEndTime().isEmpty() && employeeInfoEditForm.getHalfDayEndTimeMin()!=null && !employeeInfoEditForm.getHalfDayEndTimeMin().isEmpty()){
			employee.setHalfDayEndTime(employeeInfoEditForm.getHalfDayEndTime()+":"+employeeInfoEditForm.getHalfDayEndTimeMin());
				if(!empApplicantDetails.getHalfDayEndTime().equalsIgnoreCase(employeeInfoEditForm.getHalfDayEndTime()))
					count++;
		}else if(empApplicantDetails.getHalfDayEndTime()!=null && !empApplicantDetails.getHalfDayEndTime().isEmpty())
			count++;
		if(count>1){
			myProfile.setWorkTimeEntry(myProfile.getWorkTimeEntry()+1);
		}
		}
		if(employeeInfoEditForm.getFourWheelerNo()!=null && !employeeInfoEditForm.getFourWheelerNo().isEmpty()){
			employee.setFourWheelerNo(employeeInfoEditForm.getFourWheelerNo());
			if(empApplicantDetails.getFourWheelerNo()!=null){
				if(!empApplicantDetails.getFourWheelerNo().equalsIgnoreCase(employeeInfoEditForm.getFourWheelerNo())){
					myProfile.setFourWheelerNo(myProfile.getFourWheelerNo()+1);
				}
			}else{
				myProfile.setFourWheelerNo(myProfile.getFourWheelerNo()+1);
			}
		}else if(empApplicantDetails.getFourWheelerNo()!=null && !empApplicantDetails.getFourWheelerNo().isEmpty())
			myProfile.setFourWheelerNo(myProfile.getFourWheelerNo()+1);
		if(employeeInfoEditForm.getTwoWheelerNo()!=null && !employeeInfoEditForm.getTwoWheelerNo().isEmpty()){
			employee.setTwoWheelerNo(employeeInfoEditForm.getTwoWheelerNo());
			if(empApplicantDetails.getTwoWheelerNo()!=null){
				if(!empApplicantDetails.getTwoWheelerNo().equalsIgnoreCase(employeeInfoEditForm.getTwoWheelerNo())){
					myProfile.setTwoWheelerNo(myProfile.getTwoWheelerNo()+1);
				}
			}else{
				myProfile.setTwoWheelerNo(myProfile.getTwoWheelerNo()+1);
			}
		}else if(empApplicantDetails.getTwoWheelerNo()!=null && !empApplicantDetails.getTwoWheelerNo().isEmpty())
			myProfile.setTwoWheelerNo(myProfile.getTwoWheelerNo()+1);
		/*if(employeeInfoEditForm.getVehicleNo()!=null && !employeeInfoEditForm.getVehicleNo().isEmpty()){
			employee.setVehicleNo(employeeInfoEditForm.getVehicleNo());
		}*/
		if(employeeInfoEditForm.getPfNo()!=null && !employeeInfoEditForm.getPfNo().isEmpty()){
			employee.setPfNo(employeeInfoEditForm.getPfNo());
			if(empApplicantDetails.getPfNo()!=null){
				if(!empApplicantDetails.getPfNo().equalsIgnoreCase(employeeInfoEditForm.getPfNo())){
					myProfile.setPfNo(myProfile.getPfNo()+1);
				}
			}else{
				myProfile.setPfNo(myProfile.getPfNo()+1);
			}
		}else if(empApplicantDetails.getPfNo()!=null && !empApplicantDetails.getPfNo().isEmpty())
			myProfile.setPfNo(myProfile.getPfNo()+1);
		if(employeeInfoEditForm.getBankAccNo()!=null && !employeeInfoEditForm.getBankAccNo().isEmpty()){
			employee.setBankAccNo(employeeInfoEditForm.getBankAccNo());
			if(empApplicantDetails.getBankAccNo()!=null){
				if(!empApplicantDetails.getBankAccNo().equalsIgnoreCase(employeeInfoEditForm.getBankAccNo())){
					myProfile.setBankAccountNo(myProfile.getBankAccountNo()+1);
				}
			}else{
				myProfile.setBankAccountNo(myProfile.getBankAccountNo()+1);
			}
		}else if(empApplicantDetails.getBankAccNo()!=null && !empApplicantDetails.getBankAccNo().isEmpty())
			myProfile.setBankAccountNo(myProfile.getBankAccountNo()+1);
		if(employeeInfoEditForm.getStreamId()!=null && !employeeInfoEditForm.getStreamId().isEmpty()){
			EmployeeStreamBO empStream=new EmployeeStreamBO();
			empStream.setId(Integer.parseInt(employeeInfoEditForm.getStreamId()));
			employee.setStreamId(empStream);
			if(empApplicantDetails.getStreamId()!=null){
				if(empApplicantDetails.getStreamId().getId()!=Integer.parseInt(employeeInfoEditForm.getStreamId())){
					myProfile.setEmpStream(myProfile.getEmpStream()+1);
				}
			}else{
				myProfile.setEmpStream(myProfile.getEmpStream()+1);
			}
		}else if(empApplicantDetails.getStreamId()!=null)
			myProfile.setEmpStream(myProfile.getEmpStream()+1);
		if(employeeInfoEditForm.getWorkLocationId()!=null && !employeeInfoEditForm.getWorkLocationId().isEmpty()){
			EmployeeWorkLocationBO empworkLoc=new EmployeeWorkLocationBO();
			empworkLoc.setId(Integer.parseInt(employeeInfoEditForm.getWorkLocationId()));
			employee.setWorkLocationId(empworkLoc);
			if(empApplicantDetails.getWorkLocationId()!=null){
				if(empApplicantDetails.getWorkLocationId().getId()!=Integer.parseInt(employeeInfoEditForm.getWorkLocationId())){
					myProfile.setWorkLocation(myProfile.getWorkLocation()+1);
				}
			}else{
				myProfile.setWorkLocation(myProfile.getWorkLocation()+1);
			}
		}else if(empApplicantDetails.getWorkLocationId()!=null)
			myProfile.setWorkLocation(myProfile.getWorkLocation()+1);
		if(employeeInfoEditForm.getActive()!=null && !employeeInfoEditForm.getActive().isEmpty()){
			String Value= employeeInfoEditForm.getActive();
			if(Value.equals("1"))
			employee.setActive(true);
			else
			employee.setActive(false);
			if(empApplicantDetails.getActive()!=employee.getActive()){
				myProfile.setActive(myProfile.getActive()+1);
			}
		}
		if(employeeInfoEditForm.getSameAddress()!=null && !employeeInfoEditForm.getSameAddress().isEmpty()){
			String Value= employeeInfoEditForm.getSameAddress();
			if(Value.equals("true"))
			employee.setIsSameAddress(true);
			else
			employee.setIsSameAddress(false);
			if(empApplicantDetails.getIsSameAddress()!=null){
				if(!empApplicantDetails.getIsSameAddress().toString().equalsIgnoreCase(employee.getIsSameAddress().toString()))
					myProfile.setSameAddress(myProfile.getSameAddress()+1);
			}else
				myProfile.setSameAddress(myProfile.getSameAddress()+1);
			//if(empApplicantDetails.getIsSameAddress()!=null){
				
		}
		if(employeeInfoEditForm.getTeachingStaff()!=null && !employeeInfoEditForm.getTeachingStaff().isEmpty()){
			String Value= employeeInfoEditForm.getTeachingStaff();
			if(Value.equals("1"))
			employee.setTeachingStaff(true);
			else
			employee.setTeachingStaff(false);
			if(empApplicantDetails.getTeachingStaff()!=employee.getTeachingStaff()){
				myProfile.setIsTeachingStaff(myProfile.getIsTeachingStaff()+1);
			}
		}
		/*if(employeeInfoEditForm.getTeachingStaff()!=null && !employeeInfoEditForm.getTeachingStaff().isEmpty()){
			employee.setTeachingStaff(Boolean.parseBoolean(employeeInfoEditForm.getTeachingStaff()));
		
		}*/
		if(employeeInfoEditForm.getPanno()!=null && !employeeInfoEditForm.getPanno().isEmpty()){
			employee.setPanNo(employeeInfoEditForm.getPanno());
			if(empApplicantDetails.getPanNo()!=null){
				if(!empApplicantDetails.getPanNo().equalsIgnoreCase(employeeInfoEditForm.getPanno())){
					myProfile.setPanNo(myProfile.getPanNo()+1);
				}
			}else{
				myProfile.setPanNo(myProfile.getPanNo()+1);
			}
		}else if(empApplicantDetails.getPanNo()!=null && !empApplicantDetails.getPanNo().isEmpty())
			myProfile.setPanNo(myProfile.getPanNo()+1);
		if(employeeInfoEditForm.getExpYears()!=null && !employeeInfoEditForm.getExpYears().isEmpty()){
			employee.setTotalExpYear(employeeInfoEditForm.getExpYears());
		}
		if(employeeInfoEditForm.getExpMonths()!=null && !employeeInfoEditForm.getExpMonths().isEmpty()){
			employee.setTotalExpMonths(employeeInfoEditForm.getExpMonths());
		}
		if(employeeInfoEditForm.getRelevantExpMonths()!=null && !employeeInfoEditForm.getRelevantExpMonths().isEmpty()){
			employee.setRelevantExpMonths(employeeInfoEditForm.getRelevantExpMonths());
		}
		if(employeeInfoEditForm.getRelevantExpYears()!=null && !employeeInfoEditForm.getRelevantExpYears().isEmpty()){
			employee.setRelevantExpYears(employeeInfoEditForm.getRelevantExpYears());
		}
		if(employeeInfoEditForm.getReligionId()!=null && !employeeInfoEditForm.getReligionId().isEmpty()){
			Religion empReligion=new Religion();
			empReligion.setId(Integer.parseInt(employeeInfoEditForm.getReligionId()));
			employee.setReligionId(empReligion);
			if(empApplicantDetails.getReligionId()!=null){
				if(empApplicantDetails.getReligionId().getId()!=Integer.parseInt(employeeInfoEditForm.getReligionId())){
					myProfile.setReligion(myProfile.getReligion()+1);
				}
			}else{
				myProfile.setReligion(myProfile.getReligion()+1);
			}
		}else if(empApplicantDetails.getReligionId()!=null)
			myProfile.setReligion(myProfile.getReligion()+1);
		if(employeeInfoEditForm.getOtherInfo()!=null && !employeeInfoEditForm.getOtherInfo().isEmpty()){
			employee.setOtherInfo(employeeInfoEditForm.getOtherInfo());
		}
		if(employeeInfoEditForm.getuId()!=null && !employeeInfoEditForm.getuId().isEmpty()){
			employee.setUid(employeeInfoEditForm.getuId());
		}
		if(employeeInfoEditForm.getOfficialEmail()!=null && !employeeInfoEditForm.getOfficialEmail().isEmpty()){
			employee.setWorkEmail(employeeInfoEditForm.getOfficialEmail());
			if(empApplicantDetails.getWorkEmail()!=null){
				if(!empApplicantDetails.getWorkEmail().equalsIgnoreCase(employeeInfoEditForm.getOfficialEmail())){
					myProfile.setWorkEmail(myProfile.getWorkEmail()+1);
				}
			}else{
				myProfile.setWorkEmail(myProfile.getWorkEmail()+1);
			}
		}else if(empApplicantDetails.getWorkEmail()!=null && !empApplicantDetails.getWorkEmail().isEmpty())
			myProfile.setWorkEmail(myProfile.getWorkEmail()+1);
		if(employeeInfoEditForm.getEmail()!=null && !employeeInfoEditForm.getEmail().isEmpty()){
			employee.setOtherEmail(employeeInfoEditForm.getEmail());
		}
		if(employeeInfoEditForm.getTitleId()!=null && !employeeInfoEditForm.getTitleId().isEmpty()){
			EmpJobTitle empJobTitle=new EmpJobTitle();
			empJobTitle.setId(Integer.parseInt(employeeInfoEditForm.getTitleId()));
			employee.setTitleId(empJobTitle);
			if(empApplicantDetails.getTitleId()!=null){
				if(empApplicantDetails.getTitleId().getId()!=Integer.parseInt(employeeInfoEditForm.getTitleId()))
						myProfile.setTitle(myProfile.getTitle()+1);
			}else
				myProfile.setTitle(myProfile.getTitle()+1);
		}else if(empApplicantDetails.getTitleId()!=null)
			myProfile.setTitle(myProfile.getTitle()+1);
		{
            int count=1;		
		    if(employeeInfoEditForm.getEmContactWorkTel()!=null && !employeeInfoEditForm.getEmContactWorkTel().isEmpty()){
			   employee.setEmergencyWorkTelephone(employeeInfoEditForm.getEmContactWorkTel());
			   if(empApplicantDetails.getEmergencyWorkTelephone()!=null){
				   if(!empApplicantDetails.getEmergencyWorkTelephone().equalsIgnoreCase(employeeInfoEditForm.getEmContactWorkTel()))
					   count++;
			   }else
				   count++;
		    }else if(empApplicantDetails.getEmergencyWorkTelephone()!=null && !empApplicantDetails.getEmergencyWorkTelephone().isEmpty())
		    	count++;
		    if(employeeInfoEditForm.getEmContactHomeTel()!=null && !employeeInfoEditForm.getEmContactHomeTel().isEmpty()){
			   employee.setEmergencyHomeTelephone(employeeInfoEditForm.getEmContactHomeTel());
			   if(empApplicantDetails.getEmergencyHomeTelephone()!=null){
				   if(!empApplicantDetails.getEmergencyHomeTelephone().equalsIgnoreCase(employeeInfoEditForm.getEmContactHomeTel()))
					   count++;
			   }else
				   count++;
		    }else if(empApplicantDetails.getEmergencyHomeTelephone()!=null && !empApplicantDetails.getEmergencyHomeTelephone().isEmpty())
		    	count++;
		    if(employeeInfoEditForm.getEmContactMobile()!=null && !employeeInfoEditForm.getEmContactMobile().isEmpty()){
			   employee.setEmergencyMobile(employeeInfoEditForm.getEmContactMobile());
			   if(empApplicantDetails.getEmergencyMobile()!=null){
				   if(!empApplicantDetails.getEmergencyMobile().equalsIgnoreCase(employeeInfoEditForm.getEmContactMobile()))
					   count++;
			   }else
				   count++;
		    }else if(empApplicantDetails.getEmergencyMobile()!=null && !empApplicantDetails.getEmergencyMobile().isEmpty())
		    	count++;
		
		    if(employeeInfoEditForm.getEmContactRelationship()!=null && !employeeInfoEditForm.getEmContactRelationship().isEmpty()){
			   employee.setRelationship(employeeInfoEditForm.getEmContactRelationship());
			   if(empApplicantDetails.getRelationship()!=null){
				   if(!empApplicantDetails.getRelationship().equalsIgnoreCase(employeeInfoEditForm.getEmContactRelationship()))
					   count++;
			   }else
				   count++;
		    }else if(empApplicantDetails.getRelationship()!=null && !empApplicantDetails.getRelationship().isEmpty())
		    	count++;
		    if(employeeInfoEditForm.getEmContactName()!=null && !employeeInfoEditForm.getEmContactName().isEmpty()){
			   employee.setEmergencyContName(employeeInfoEditForm.getEmContactName());
			   if(empApplicantDetails.getEmergencyContName()!=null){
				   if(!empApplicantDetails.getEmergencyContName().equalsIgnoreCase(employeeInfoEditForm.getEmContactName()))
					   count++;
			   }else
				   count++;
		    }else if(empApplicantDetails.getEmergencyContName()!=null && !empApplicantDetails.getEmergencyContName().isEmpty())
		    	count++;
		    if(count>1){
		    	myProfile.setEmpContactDetails(myProfile.getEmpContactDetails()+1);
		    }
		}
		if(employeeInfoEditForm.getReportToId()!=null && !employeeInfoEditForm.getReportToId().isEmpty()){
			Employee emp=new Employee();
			emp.setId(Integer.parseInt(employeeInfoEditForm.getReportToId()));
			employee.setEmployeeByReportToId(emp);
			if(empApplicantDetails.getEmployeeByReportToId()!=null){
				if(empApplicantDetails.getEmployeeByReportToId().getId()!=Integer.parseInt(employeeInfoEditForm.getReportToId()))
					myProfile.setReportTo(myProfile.getReportTo()+1);
			}else
				myProfile.setReportTo(myProfile.getReportTo()+1);
		}else if(empApplicantDetails.getEmployeeByReportToId()!=null)
			myProfile.setReportTo(myProfile.getReportTo()+1);
		if(employeeInfoEditForm.getEmptypeId()!=null && !employeeInfoEditForm.getEmptypeId().isEmpty()){
			EmpType emptype=new EmpType();
			emptype.setId(Integer.parseInt(employeeInfoEditForm.getEmptypeId()));
			employee.setEmptype(emptype);
			if(empApplicantDetails.getEmptype()!=null){
				if(empApplicantDetails.getEmptype().getId()!=Integer.parseInt(employeeInfoEditForm.getEmptypeId())){
					myProfile.setEmpType(myProfile.getEmpType()+1);
				}
			}else{
				myProfile.setEmpType(myProfile.getEmpType()+1);
			}
		}else if(empApplicantDetails.getEmptype()!=null)
			myProfile.setEmpType(myProfile.getEmpType()+1);
		
		if(employeeInfoEditForm.getMaritalStatus()!=null && !employeeInfoEditForm.getMaritalStatus().isEmpty()){
			employee.setMaritalStatus(employeeInfoEditForm.getMaritalStatus());
		}
		if(employeeInfoEditForm.getBloodGroup()!=null && !employeeInfoEditForm.getBloodGroup().isEmpty()){
			employee.setBloodGroup(employeeInfoEditForm.getBloodGroup());
			if(empApplicantDetails.getBloodGroup()!=null){
				if(!empApplicantDetails.getBloodGroup().equalsIgnoreCase(employeeInfoEditForm.getBloodGroup())){
					myProfile.setBloodGroup(myProfile.getBloodGroup()+1);
				}
			}else{
				myProfile.setBloodGroup(myProfile.getBloodGroup()+1);
			}
		}else if(empApplicantDetails.getBloodGroup()!=null && !empApplicantDetails.getBloodGroup().isEmpty())
			myProfile.setBloodGroup(myProfile.getBloodGroup()+1);
		if(employeeInfoEditForm.getQualificationId()!=null && !employeeInfoEditForm.getQualificationId().isEmpty()){
			QualificationLevelBO qual=new QualificationLevelBO();
			qual.setId(Integer.parseInt(employeeInfoEditForm.getQualificationId()));
			employee.setEmpQualificationLevel(qual);
			if(empApplicantDetails.getEmpQualificationLevel()!=null){
			     if(empApplicantDetails.getEmpQualificationLevel().getId()!=Integer.parseInt(employeeInfoEditForm.getQualificationId()))
				     myProfile.setQualificationLevel(myProfile.getQualificationLevel()+1);
		         }else
			         myProfile.setQualificationLevel(myProfile.getQualificationLevel()+1);
		}else if(empApplicantDetails.getEmpQualificationLevel()!=null)
		myProfile.setQualificationLevel(myProfile.getQualificationLevel()+1);
		
		
		/*if(employeeInfoEditForm.getEmpPhoto()== null || employeeInfoEditForm.getPhotoBytes()!=null) {
			
			//employeeInfoEditForm.getPhotoBytes();
			byte[] data=employeeInfoEditForm.getPhotoBytes();
			if(data.length>0){
				employee.setEmpPhoto(data);
				}
			
			}
		 if(employeeInfoEditForm.getEmpPhoto()!=null) {
			
			FormFile file=employeeInfoEditForm.getEmpPhoto();
			byte[] data=file.getFileData();
			if(data.length>0){
				employee.setEmpPhoto(data);
				}
			if(empApplicantDetails.getEmpPhoto()!=null){
				byte[] data1=empApplicantDetails.getEmpPhoto();
				int count=1;
				if(data.length!=0 && data1.length!=0){
				   if(data1.length==data.length){
				      for(int i=0;i<data.length;i++){
					     if(data[i]!=data1[i])
						   count++;
				      } 
				   }else
					 count++;
				}
				if(count>1)
					myProfile.setPhoto(myProfile.getPhoto()+1);
			}else
				myProfile.setPhoto(myProfile.getPhoto()+1);
			}*/
		 employeeInfoEditForm.setEditMyProfile(myProfile);
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		employee.setIsActive(true);
		employee.setCreatedBy(employeeInfoEditForm.getUserId());
		employee.setCreatedDate(new Date());
		employee.setLastModifiedDate(new Date());
		employee.setModifiedBy(employeeInfoEditForm.getUserId());
		
	    }
		return employee;
	}
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	

}