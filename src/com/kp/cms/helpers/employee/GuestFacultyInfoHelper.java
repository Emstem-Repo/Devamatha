package com.kp.cms.helpers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmpLoan;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.GuestEducationalDetails;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpPreviousExperience;
import com.kp.cms.bo.employee.GuestEducationalDetails;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.GuestImages;
import com.kp.cms.bo.employee.GuestPreviousChristWorkDetails;
import com.kp.cms.bo.employee.GuestPreviousExperience;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;
import com.kp.cms.forms.employee.GuestFacultyInfoForm;
import com.kp.cms.handlers.employee.GuestFacultyInfoHandler;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpEventVacationTo;
import com.kp.cms.to.employee.EmpImagesTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.GuestPreviousExperienceTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmployeeInfoTONew;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.employee.GuestImagesTO;
import com.kp.cms.to.employee.GuestPreviousChristWorkDetailsTO;
import com.kp.cms.to.employee.GuestPreviousExperienceTO;
import com.kp.cms.to.employee.GuestEducationalDetailsTO;
import com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction;
import com.kp.cms.transactionsimpl.employee.GuestFacultyInfoImpl;
import com.kp.cms.utilities.CommonUtil;

public class GuestFacultyInfoHelper {

	private static volatile GuestFacultyInfoHelper instance = null;

	/**
		 * 
		 */
	private GuestFacultyInfoHelper() {

	}

	/**
	 * @return
	 */
	public static GuestFacultyInfoHelper getInstance() {
		if (instance == null) {
			instance = new GuestFacultyInfoHelper();
		}
		return instance;
	}

	IGuestFacultyInfoTransaction txn = new GuestFacultyInfoImpl();

	/**
	 * @param employeeInfoEditForm
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ApplicationException
	 */
	public GuestFaculty convertFormToBo(GuestFacultyInfoForm employeeInfoEditForm) throws Exception{
		GuestFaculty guestFaculty = new GuestFaculty();
	/*	if (employeeInfoEditForm.getGuestId() != null
				&& !employeeInfoEditForm.getGuestId().isEmpty() && Integer.parseInt(employeeInfoEditForm.getGuestId())>0) {
			guestFaculty.setId(Integer.parseInt(employeeInfoEditForm
					.getGuestId()));
		}*/
		
		   Set<GuestPreviousChristWorkDetails> guestworkingDetails = getGuestWorkDatesObjects(employeeInfoEditForm);
		    guestFaculty.setPreviousChristDetails(guestworkingDetails);
			
			Set<GuestImages> empImages = getEmpImagesBoObjects(employeeInfoEditForm);
			guestFaculty.setEmpImages(empImages);

			Set<GuestPreviousExperience> previousSet = new HashSet<GuestPreviousExperience>();
			Set<GuestEducationalDetails> educationalDeatialSet = new HashSet<GuestEducationalDetails>();
			try {
								
				if (employeeInfoEditForm.getEmployeeInfoTONew() != null) {
					if (employeeInfoEditForm.getEmployeeInfoTONew()
							.getExperiences() != null) {
						List<GuestPreviousExperienceTO> list = employeeInfoEditForm
								.getEmployeeInfoTONew().getExperiences();
						if (list != null) {
							Iterator<GuestPreviousExperienceTO> iterator = list
									.iterator();
							while (iterator.hasNext()) {
								GuestPreviousExperienceTO empPreviousOrgTo = iterator
										.next();
								GuestPreviousExperience empPreviousExp = new GuestPreviousExperience();
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
										GuestFaculty emp = new GuestFaculty();
										if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty())
										{
										emp.setId(Integer.parseInt(employeeInfoEditForm.getGuestId()));
										empPreviousExp.setGuest(emp);
										}
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
						List<GuestPreviousExperienceTO> list = employeeInfoEditForm
								.getEmployeeInfoTONew().getTeachingExperience();
						if (list != null) {
							Iterator<GuestPreviousExperienceTO> iterator = list
									.iterator();
							while (iterator.hasNext()) {
								GuestPreviousExperienceTO empPreviousOrgTo = iterator
										.next();
								GuestPreviousExperience empPreviousExp1 = new GuestPreviousExperience();
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
										

										if (empPreviousOrgTo
												.getTeachingExpYears() != null
												&& !empPreviousOrgTo
														.getTeachingExpYears()
														.isEmpty()) {
											empPreviousExp1
													.setExpYears(Integer
															.parseInt(empPreviousOrgTo
																	.getTeachingExpYears()));
										}

										if (empPreviousOrgTo
												.getTeachingExpMonths() != null
												&& !empPreviousOrgTo
														.getTeachingExpMonths()
														.isEmpty()) {
											empPreviousExp1
													.setExpMonths(Integer
															.parseInt(empPreviousOrgTo
																	.getTeachingExpMonths()));
										}

										if (empPreviousOrgTo
												.getCurrentTeachnigDesignation() != null
												&& !empPreviousOrgTo
														.getCurrentTeachnigDesignation()
														.isEmpty()) {
											empPreviousExp1
													.setEmpDesignation(empPreviousOrgTo
															.getCurrentTeachnigDesignation());
										}

										if (empPreviousOrgTo
												.getCurrentTeachingOrganisation() != null
												&& !empPreviousOrgTo
														.getCurrentTeachingOrganisation()
														.isEmpty()) {
											empPreviousExp1
													.setEmpOrganization(empPreviousOrgTo
															.getCurrentTeachingOrganisation());
										}
										GuestFaculty emp = new GuestFaculty();
										if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty())
										{
										emp.setId(Integer
												.parseInt(employeeInfoEditForm
														.getGuestId()));
										empPreviousExp1.setGuest(emp);
										}
										empPreviousExp1
												.setTeachingExperience(true);
										empPreviousExp1.setActive(true);
										empPreviousExp1
												.setCreatedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp1
												.setCreatedDate(new Date());
										empPreviousExp1
												.setModifiedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp1
												.setModifiedDate(new Date());
										previousSet.add(empPreviousExp1);
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
					guestFaculty.setCurrentlyWorking(true);
					if (employeeInfoEditForm.getDesignation() != null
							&& !employeeInfoEditForm.getDesignation().isEmpty()) {
						guestFaculty.setDesignationName(employeeInfoEditForm
								.getDesignation());
					}

					if (employeeInfoEditForm.getOrgAddress() != null
							&& !employeeInfoEditForm.getOrgAddress().isEmpty()) {
						guestFaculty.setOrganistionName(employeeInfoEditForm
								.getOrgAddress());
					}

				} else {
					guestFaculty.setCurrentlyWorking(false);
					guestFaculty.setDesignationName(null);
					guestFaculty.setOrganistionName(null);
				}
				guestFaculty.setPreviousExpSet(previousSet);

				if (employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpQualificationFixedTo() != null) {
					List<EmpQualificationLevelTo> qualificationFixedTo = employeeInfoEditForm
							.getEmployeeInfoTONew().getEmpQualificationFixedTo();
					Iterator<EmpQualificationLevelTo> iterator = qualificationFixedTo
							.iterator();
					while (iterator.hasNext()) {
						EmpQualificationLevelTo qualificationFixed = iterator
								.next();
						GuestEducationalDetails educationalDeatails = null;
						if (qualificationFixed != null) {
							if ((qualificationFixed.getInstitute() != null && !qualificationFixed
									.getInstitute().isEmpty())
									|| (qualificationFixed.getCourse() != null && !qualificationFixed
											.getCourse().isEmpty())
									|| (qualificationFixed.getSpecialization() != null && !qualificationFixed
											.getSpecialization().isEmpty())
									|| (qualificationFixed.getGrade() != null && !qualificationFixed
											.getGrade().isEmpty())) {
								educationalDeatails = new GuestEducationalDetails();

								
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
								GuestFaculty emp = new GuestFaculty();
								if(employeeInfoEditForm.getGuestId() != null ){
									emp.setId(Integer.parseInt(employeeInfoEditForm
											.getGuestId()));
									educationalDeatails.setGuest(emp);
								}
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
						GuestEducationalDetails educationalDetails = null;
						if (levelTo != null) {
							if ((levelTo.getInstitute() != null && !levelTo
									.getInstitute().isEmpty())
									|| (levelTo.getCourse() != null && !levelTo
											.getCourse().isEmpty())
									|| (levelTo.getSpecialization() != null && !levelTo
											.getSpecialization().isEmpty())
									|| (levelTo.getGrade() != null && !levelTo
											.getGrade().isEmpty())) {
								educationalDetails = new GuestEducationalDetails();
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
                                if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty()){
								GuestFaculty emp = new GuestFaculty();
								emp.setId(Integer.parseInt(employeeInfoEditForm
										.getGuestId()));
								educationalDetails.setGuest(emp);
                                }
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

				guestFaculty.setEducationalDetailsSet(educationalDeatialSet);

				if (employeeInfoEditForm.getDesignationPfId() != null
						&& !employeeInfoEditForm.getDesignationPfId().isEmpty()) {
					Designation designation = new Designation();
					designation.setId(Integer.parseInt(employeeInfoEditForm
							.getDesignationPfId()));
					guestFaculty.setDesignation(designation);
				}

				if (employeeInfoEditForm.getDepartmentId() != null
						&& !employeeInfoEditForm.getDepartmentId().isEmpty()) {
					Department department = new Department();
					department.setId(Integer.parseInt(employeeInfoEditForm
							.getDepartmentId()));
					guestFaculty.setDepartment(department);
				}

				if (employeeInfoEditForm.getName() != null
						&& !employeeInfoEditForm.getName().isEmpty()) {
					guestFaculty.setFirstName(employeeInfoEditForm.getName().toUpperCase());
				}

				if (employeeInfoEditForm.getuId() != null
						&& !employeeInfoEditForm.getuId().isEmpty()) {
					guestFaculty.setUid(employeeInfoEditForm.getuId());
				}
				if (employeeInfoEditForm.getCode() != null
						&& !employeeInfoEditForm.getCode().isEmpty()) {
					guestFaculty.setCode(employeeInfoEditForm.getCode());
				}
				
				if (employeeInfoEditForm.getCurrentAddressLine1() != null
						&& !employeeInfoEditForm.getCurrentAddressLine1()
								.isEmpty()) {
					guestFaculty.setCommunicationAddressLine1(employeeInfoEditForm
							.getCurrentAddressLine1());
				}

				if (employeeInfoEditForm.getCurrentAddressLine2() != null
						&& !employeeInfoEditForm.getCurrentAddressLine2()
								.isEmpty()) {
					guestFaculty.setCommunicationAddressLine2(employeeInfoEditForm
							.getCurrentAddressLine2());
				}

				if (employeeInfoEditForm.getCurrentZipCode() != null
						&& !employeeInfoEditForm.getCurrentZipCode().isEmpty()) {
					guestFaculty.setCommunicationAddressZip(employeeInfoEditForm
							.getCurrentZipCode());
				}

				if (employeeInfoEditForm.getCurrentCountryId() != null
						&& !employeeInfoEditForm.getCurrentCountryId()
								.isEmpty()) {
					Country currentCountry = new Country();
					currentCountry.setId(Integer.parseInt(employeeInfoEditForm
							.getCurrentCountryId()));
					guestFaculty
							.setCountryByCommunicationAddressCountryId(currentCountry);
				}

				if (employeeInfoEditForm.getCurrentState() != null
						&& !employeeInfoEditForm.getCurrentState().isEmpty()) {
					if (employeeInfoEditForm.getCurrentState()
							.equalsIgnoreCase("other")) {
						if (employeeInfoEditForm.getOtherCurrentState() != null
								&& !employeeInfoEditForm.getOtherCurrentState()
										.isEmpty()) {
							guestFaculty
									.setCommunicationAddressStateOthers(employeeInfoEditForm
											.getOtherCurrentState());
						}
					} else {
						State currentState = new State();
						currentState.setId(Integer
								.parseInt(employeeInfoEditForm
										.getCurrentState()));
						guestFaculty
								.setStateByCommunicationAddressStateId(currentState);
					}
				}

				if (employeeInfoEditForm.getCurrentCity() != null
						&& !employeeInfoEditForm.getCurrentCity().isEmpty()) {
					guestFaculty.setCommunicationAddressCity(employeeInfoEditForm
							.getCurrentCity());
				}

				if (employeeInfoEditForm.getSameAddress().equalsIgnoreCase(
						"true")) {
					if (employeeInfoEditForm.getCurrentAddressLine1() != null
							&& !employeeInfoEditForm.getCurrentAddressLine1()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine1(employeeInfoEditForm
								.getCurrentAddressLine1());
					}

					if (employeeInfoEditForm.getCurrentAddressLine2() != null
							&& !employeeInfoEditForm.getCurrentAddressLine2()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine2(employeeInfoEditForm
								.getCurrentAddressLine2());
					}

					if (employeeInfoEditForm.getCurrentZipCode() != null
							&& !employeeInfoEditForm.getCurrentZipCode()
									.isEmpty()) {
						guestFaculty.setPermanentAddressZip(employeeInfoEditForm
								.getCurrentZipCode());
					}

					if (employeeInfoEditForm.getCurrentCountryId() != null
							&& !employeeInfoEditForm.getCurrentCountryId()
									.isEmpty()) {
						Country currentCountry = new Country();
						currentCountry.setId(Integer
								.parseInt(employeeInfoEditForm
										.getCurrentCountryId()));
						guestFaculty
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
								guestFaculty
										.setPermanentAddressStateOthers(employeeInfoEditForm
												.getOtherCurrentState());
							}
						} else {
							State currentState = new State();
							currentState.setId(Integer
									.parseInt(employeeInfoEditForm
											.getCurrentState()));
							guestFaculty
									.setStateByPermanentAddressStateId(currentState);
						}
					}

					if (employeeInfoEditForm.getCurrentCity() != null
							&& !employeeInfoEditForm.getCurrentCity().isEmpty()) {
						guestFaculty.setPermanentAddressCity(employeeInfoEditForm
								.getCurrentCity());
					}

				} else {
					if (employeeInfoEditForm.getAddressLine1() != null
							&& !employeeInfoEditForm.getAddressLine1()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine1(employeeInfoEditForm
								.getAddressLine1());
					}

					if (employeeInfoEditForm.getAddressLine2() != null
							&& !employeeInfoEditForm.getAddressLine2()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine2(employeeInfoEditForm
								.getAddressLine2());
					}

					if (employeeInfoEditForm.getPermanentZipCode() != null
							&& !employeeInfoEditForm.getPermanentZipCode()
									.isEmpty()) {
						guestFaculty.setPermanentAddressZip(employeeInfoEditForm
								.getPermanentZipCode());
					}

					if (employeeInfoEditForm.getCountryId() != null
							&& !employeeInfoEditForm.getCountryId().isEmpty()) {
						Country country = new Country();
						country.setId(Integer.parseInt(employeeInfoEditForm
								.getCountryId()));
						guestFaculty.setCountryByPermanentAddressCountryId(country);
					}

					if (employeeInfoEditForm.getStateId() != null
							&& !employeeInfoEditForm.getStateId().isEmpty()) {
						if (employeeInfoEditForm.getStateId().equalsIgnoreCase(
								"other")) {
							if (employeeInfoEditForm.getOtherPermanentState() != null
									&& !employeeInfoEditForm
											.getOtherPermanentState().isEmpty()) {
								guestFaculty
										.setPermanentAddressStateOthers(employeeInfoEditForm
												.getOtherPermanentState());
							}
						} else {
							State state = new State();
							state.setId(Integer.parseInt(employeeInfoEditForm
									.getStateId()));
							guestFaculty.setStateByPermanentAddressStateId(state);
						}
					}

					if (employeeInfoEditForm.getCity() != null
							&& !employeeInfoEditForm.getCity().isEmpty()) {
						guestFaculty.setPermanentAddressCity(employeeInfoEditForm
								.getCity());
					}
				}
				if (employeeInfoEditForm.getNationalityId() != null
						&& !employeeInfoEditForm.getNationalityId().isEmpty()) {
					Nationality nationality = new Nationality();
					nationality.setId(Integer.parseInt(employeeInfoEditForm
							.getNationalityId()));
					guestFaculty.setNationality(nationality);
				}

				if (employeeInfoEditForm.getGender() != null
						&& !employeeInfoEditForm.getGender().isEmpty()) {
					guestFaculty.setGender(employeeInfoEditForm.getGender());
				}

				if (employeeInfoEditForm.getMaritalStatus() != null
						&& !employeeInfoEditForm.getMaritalStatus().isEmpty()) {
					guestFaculty.setMaritalStatus(employeeInfoEditForm
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
									guestFaculty.setEligibilityTestOther(employeeInfoEditForm.getOtherEligibilityTestValue());
								}
							}
						}
					}
					guestFaculty.setEligibilityTest(eligibilityTest);
				}
				
				
				if(employeeInfoEditForm.getIndustryFunctionalArea()!=null && !employeeInfoEditForm.getIndustryFunctionalArea().trim().isEmpty()){
					guestFaculty.setIndustryFunctionalArea(employeeInfoEditForm.getIndustryFunctionalArea());
				}
				if (employeeInfoEditForm.getDateOfBirth() != null
						&& !employeeInfoEditForm.getDateOfBirth().isEmpty()) {
					guestFaculty.setDob(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfBirth()));
				}
				
				
				if (employeeInfoEditForm.getEmail() != null
						&& !employeeInfoEditForm.getEmail().isEmpty()) {
					guestFaculty.setEmail(employeeInfoEditForm.getEmail());
				}

				
				
				if (employeeInfoEditForm.getReservationCategory() != null
						&& !employeeInfoEditForm.getReservationCategory()
								.isEmpty()) {
					guestFaculty.setReservationCategory(employeeInfoEditForm
							.getReservationCategory());
				}
				 if(employeeInfoEditForm.getHandicappedDescription()!=null && !employeeInfoEditForm.getHandicappedDescription().trim().isEmpty())
					 guestFaculty.setHandicappedDescription(employeeInfoEditForm.getHandicappedDescription());	
					

				if (employeeInfoEditForm.getHomePhone1() != null
						&& !employeeInfoEditForm.getHomePhone1().isEmpty()) {
					guestFaculty
							.setCurrentAddressHomeTelephone1(employeeInfoEditForm
									.getHomePhone1());
				}

				if (employeeInfoEditForm.getHomePhone2() != null
						&& !employeeInfoEditForm.getHomePhone2().isEmpty()) {
					guestFaculty
							.setCurrentAddressHomeTelephone2(employeeInfoEditForm
									.getHomePhone2());
				}

				if (employeeInfoEditForm.getHomePhone3() != null
						&& !employeeInfoEditForm.getHomePhone3().isEmpty()) {
					guestFaculty
							.setCurrentAddressHomeTelephone3(employeeInfoEditForm
									.getHomePhone3());
				}
				if (employeeInfoEditForm.getWorkPhNo1() != null
						&& !employeeInfoEditForm.getWorkPhNo1().isEmpty()) {
					guestFaculty
							.setCurrentAddressWorkTelephone1(employeeInfoEditForm
									.getWorkPhNo1());
				}

				if (employeeInfoEditForm.getWorkPhNo2() != null
						&& !employeeInfoEditForm.getWorkPhNo2().isEmpty()) {
					guestFaculty
							.setCurrentAddressWorkTelephone2(employeeInfoEditForm
									.getWorkPhNo2());
				}

				if (employeeInfoEditForm.getWorkPhNo3() != null
						&& !employeeInfoEditForm.getWorkPhNo3().isEmpty()) {
					guestFaculty
							.setCurrentAddressWorkTelephone3(employeeInfoEditForm
									.getWorkPhNo3());
				}
				if (employeeInfoEditForm.getMobileNo1() != null
						&& !employeeInfoEditForm.getMobileNo1().isEmpty()) {
					guestFaculty.setCurrentAddressMobile1(employeeInfoEditForm
							.getMobileNo1());
				}
				if (employeeInfoEditForm.getHighQualifForAlbum() != null
						&& !employeeInfoEditForm.getHighQualifForAlbum()
								.isEmpty()) {
					guestFaculty.setHighQualifForAlbum(employeeInfoEditForm
							.getHighQualifForAlbum());
				}

				if (employeeInfoEditForm.getEmpSubjectAreaId() != null
						&& !employeeInfoEditForm.getEmpSubjectAreaId()
								.isEmpty()) {
					SubjectAreaBO subjectArea = new SubjectAreaBO();
					subjectArea.setId(Integer.parseInt(employeeInfoEditForm
							.getEmpSubjectAreaId()));
					guestFaculty.setEmpSubjectArea(subjectArea);
				}

			

				if (employeeInfoEditForm.getNoOfPublicationsRefered() != null
						&& !employeeInfoEditForm.getNoOfPublicationsRefered()
								.isEmpty()) {
					guestFaculty.setNoOfPublicationsRefered(employeeInfoEditForm
							.getNoOfPublicationsRefered());
				}

				if (employeeInfoEditForm.getNoOfPublicationsNotRefered() != null
						&& !employeeInfoEditForm
								.getNoOfPublicationsNotRefered().isEmpty()) {
					guestFaculty.setNoOfPublicationsNotRefered(employeeInfoEditForm
							.getNoOfPublicationsNotRefered());
				}

				if (employeeInfoEditForm.getBooks() != null
						&& !employeeInfoEditForm.getBooks().isEmpty()) {
					guestFaculty.setBooks(employeeInfoEditForm.getBooks());
				}
				

				if (employeeInfoEditForm.getEmpQualificationLevelId() != null
						&& !employeeInfoEditForm.getEmpQualificationLevelId()
								.isEmpty()) {
					QualificationLevelBO empQualificationLevel = new QualificationLevelBO();
					empQualificationLevel.setId(Integer
							.parseInt(employeeInfoEditForm
									.getEmpQualificationLevelId()));
					guestFaculty.setEmpQualificationLevel(empQualificationLevel);
				}

				
				

				if (employeeInfoEditForm.getFourWheelerNo() != null
						&& !employeeInfoEditForm.getFourWheelerNo().isEmpty()) {
					guestFaculty.setFourWheelerNo(employeeInfoEditForm
							.getFourWheelerNo());
				}
				if (employeeInfoEditForm.getTwoWheelerNo() != null
						&& !employeeInfoEditForm.getTwoWheelerNo().isEmpty()) {
					guestFaculty.setTwoWheelerNo(employeeInfoEditForm
							.getTwoWheelerNo());
				}
			
				if (employeeInfoEditForm.getPfNo() != null
						&& !employeeInfoEditForm.getPfNo().isEmpty()) {
					guestFaculty.setPfNo(employeeInfoEditForm.getPfNo());
				}
				if (employeeInfoEditForm.getBankAccNo() != null
						&& !employeeInfoEditForm.getBankAccNo().isEmpty()) {
					guestFaculty.setBankAccNo(employeeInfoEditForm.getBankAccNo());
				}
				if (employeeInfoEditForm.getStreamId() != null
						&& !employeeInfoEditForm.getStreamId().isEmpty()) {
					EmployeeStreamBO empStream = new EmployeeStreamBO();
					empStream.setId(Integer.parseInt(employeeInfoEditForm
							.getStreamId()));
					guestFaculty.setStreamId(empStream);
				}
				if (employeeInfoEditForm.getWorkLocationId() != null
						&& !employeeInfoEditForm.getWorkLocationId().isEmpty()) {
					EmployeeWorkLocationBO empworkLoc = new EmployeeWorkLocationBO();
					empworkLoc.setId(Integer.parseInt(employeeInfoEditForm
							.getWorkLocationId()));
					guestFaculty.setWorkLocationId(empworkLoc);
				}
				if (employeeInfoEditForm.getActive() != null
						&& !employeeInfoEditForm.getActive().isEmpty()) {
					String Value = employeeInfoEditForm.getActive();
					if (Value.equals("1"))
						guestFaculty.setActive(true);
					else
						guestFaculty.setActive(false);
				}
				
				if (employeeInfoEditForm.getSameAddress() != null
						&& !employeeInfoEditForm.getSameAddress().isEmpty()) {
					String Value = employeeInfoEditForm.getSameAddress();
					if (Value.equals("true"))
						guestFaculty.setIsSameAddress(true);
					else
						guestFaculty.setIsSameAddress(false);
				}
				if (employeeInfoEditForm.getTeachingStaff() != null
						&& !employeeInfoEditForm.getTeachingStaff().isEmpty()) {
					String Value = employeeInfoEditForm.getTeachingStaff();
					if (Value.equals("1"))
						guestFaculty.setTeachingStaff(true);
					else
						guestFaculty.setTeachingStaff(false);
				}
				
				if (employeeInfoEditForm.getPanno() != null
						&& !employeeInfoEditForm.getPanno().isEmpty()) {
					guestFaculty.setPanNo(employeeInfoEditForm.getPanno());
				}
				if (employeeInfoEditForm.getExpYears() != null
						&& !employeeInfoEditForm.getExpYears().isEmpty()) {
					guestFaculty
							.setTotalExpYear(employeeInfoEditForm.getExpYears());
				}
				if (employeeInfoEditForm.getExpMonths() != null
						&& !employeeInfoEditForm.getExpMonths().isEmpty()) {
					guestFaculty.setTotalExpMonths(employeeInfoEditForm
							.getExpMonths());
				}
				if (employeeInfoEditForm.getRelevantExpMonths() != null
						&& !employeeInfoEditForm.getRelevantExpMonths()
								.isEmpty()) {
					guestFaculty.setRelevantExpMonths(employeeInfoEditForm
							.getRelevantExpMonths());
				}
				if (employeeInfoEditForm.getRelevantExpYears() != null
						&& !employeeInfoEditForm.getRelevantExpYears()
								.isEmpty()) {
					guestFaculty.setRelevantExpYears(employeeInfoEditForm
							.getRelevantExpYears());
				}
				if (employeeInfoEditForm.getReligionId() != null
						&& !employeeInfoEditForm.getReligionId().isEmpty()) {
					Religion empReligion = new Religion();
					empReligion.setId(Integer.parseInt(employeeInfoEditForm
							.getReligionId()));
					guestFaculty.setReligionId(empReligion);
				}
				if (employeeInfoEditForm.getOtherInfo() != null
						&& !employeeInfoEditForm.getOtherInfo().isEmpty()) {
					guestFaculty.setOtherInfo(employeeInfoEditForm.getOtherInfo());
				}
				if (employeeInfoEditForm.getuId() != null
						&& !employeeInfoEditForm.getuId().isEmpty()) {
					guestFaculty.setUid(employeeInfoEditForm.getuId());
				}
				if (employeeInfoEditForm.getOfficialEmail() != null
						&& !employeeInfoEditForm.getOfficialEmail().isEmpty()) {
					guestFaculty.setWorkEmail(employeeInfoEditForm
							.getOfficialEmail());
				}
				
				if (employeeInfoEditForm.getEmContactWorkTel() != null
						&& !employeeInfoEditForm.getEmContactWorkTel()
								.isEmpty()) {
					guestFaculty.setEmergencyWorkTelephone(employeeInfoEditForm
							.getEmContactWorkTel());
				}
				if (employeeInfoEditForm.getEmContactHomeTel() != null
						&& !employeeInfoEditForm.getEmContactHomeTel()
								.isEmpty()) {
					guestFaculty.setEmergencyHomeTelephone(employeeInfoEditForm
							.getEmContactHomeTel());
				}
				if (employeeInfoEditForm.getEmContactMobile() != null
						&& !employeeInfoEditForm.getEmContactMobile().isEmpty()) {
					guestFaculty.setEmergencyMobile(employeeInfoEditForm
							.getEmContactMobile());
				}
				if (employeeInfoEditForm.getEmContactAddress() != null
						&& !employeeInfoEditForm.getEmContactAddress().isEmpty()) {
					guestFaculty.setEmContactAddress(employeeInfoEditForm
							.getEmContactAddress());
				}
				if (employeeInfoEditForm.getTitleId() != null
						&& !employeeInfoEditForm.getTitleId().isEmpty()) {
					EmpJobTitle empJobTitle = new EmpJobTitle();
					empJobTitle.setId(Integer.parseInt(employeeInfoEditForm
							.getTitleId()));
					guestFaculty.setTitleId(empJobTitle);
				}
				if (employeeInfoEditForm.getEmContactRelationship() != null
						&& !employeeInfoEditForm.getEmContactRelationship()
								.isEmpty()) {
					guestFaculty.setEmContactRelationship(employeeInfoEditForm
							.getEmContactRelationship());
				}
				if (employeeInfoEditForm.getEmContactName() != null
						&& !employeeInfoEditForm.getEmContactName().isEmpty()) {
					guestFaculty.setEmergencyContName(employeeInfoEditForm
							.getEmContactName());
				}
				
				
				if (employeeInfoEditForm.getMaritalStatus() != null
						&& !employeeInfoEditForm.getMaritalStatus().isEmpty()) {
					guestFaculty.setMaritalStatus(employeeInfoEditForm
							.getMaritalStatus());
				}
				if (employeeInfoEditForm.getBloodGroup() != null
						&& !employeeInfoEditForm.getBloodGroup().isEmpty()) {
					guestFaculty
							.setBloodGroup(employeeInfoEditForm.getBloodGroup());
				}
				if (employeeInfoEditForm.getQualificationId() != null
						&& !employeeInfoEditForm.getQualificationId().isEmpty()) {
					QualificationLevelBO qual = new QualificationLevelBO();
					qual.setId(Integer.parseInt(employeeInfoEditForm
							.getQualificationId()));
					guestFaculty.setEmpQualificationLevel(qual);
				}
				if (employeeInfoEditForm.getReferredBy() != null
						&& !employeeInfoEditForm.getReferredBy().isEmpty()) {
					guestFaculty.setReferredBy(employeeInfoEditForm.getReferredBy());
				}
				if (employeeInfoEditForm.getHonorariumPerHours() != null
						&& !employeeInfoEditForm.getHonorariumPerHours().isEmpty()) {
					guestFaculty.setHonorariumPerHours(employeeInfoEditForm.getHonorariumPerHours());
				}
				if (employeeInfoEditForm.getWorkingHoursPerWeek() != null
						&& !employeeInfoEditForm.getWorkingHoursPerWeek().isEmpty()) {
					guestFaculty.setWorkingHoursPerWeek(employeeInfoEditForm.getWorkingHoursPerWeek());
				}
				if (employeeInfoEditForm.getSubjectSpecilization() != null
						&& !employeeInfoEditForm.getSubjectSpecilization().isEmpty()) {
					guestFaculty.setSubjectSpecilization(employeeInfoEditForm.getSubjectSpecilization());
				}
				if (employeeInfoEditForm.getStaffId() != null
						&& !employeeInfoEditForm.getStaffId().isEmpty()) {
					guestFaculty.setStaffId(employeeInfoEditForm.getStaffId());
				}
				if (employeeInfoEditForm.getHighQualifForWebsite() != null
						&& !employeeInfoEditForm.getHighQualifForWebsite().isEmpty()) {
					guestFaculty.setHighQualifForWebsite(employeeInfoEditForm.getHighQualifForWebsite());
				}
				if (employeeInfoEditForm.getDisplayInWebsite() != null
						&& !employeeInfoEditForm.getDisplayInWebsite().isEmpty()) {
					String ValueNew = employeeInfoEditForm.getDisplayInWebsite();
					if (ValueNew.equals("1"))
						guestFaculty.setDisplayInWebsite(true);
					else
						guestFaculty.setDisplayInWebsite(false);
				}
				if(employeeInfoEditForm.getBankBranch()!=null && !employeeInfoEditForm.getBankBranch().isEmpty()){
					guestFaculty.setBankBranch(employeeInfoEditForm.getBankBranch());
				}
				if(employeeInfoEditForm.getBankIfscCode()!=null && !employeeInfoEditForm.getBankIfscCode().isEmpty()){
					guestFaculty.setBankIfscCode(employeeInfoEditForm.getBankIfscCode());
				}
				
				

			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			guestFaculty.setIsActive(true);
			guestFaculty.setCreatedBy(employeeInfoEditForm.getUserId());
			guestFaculty.setCreatedDate(new Date());
			guestFaculty.setLastModifiedDate(new Date());
			guestFaculty.setModifiedBy(employeeInfoEditForm.getUserId());

		
		return guestFaculty;
	}

	/**
	 * @param employeeInfoEditForm
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ApplicationException
	 */
	public GuestFaculty convertFormToBoEdit(GuestFacultyInfoForm employeeInfoEditForm) throws Exception{
		GuestFaculty guestFaculty = new GuestFaculty();
		if (employeeInfoEditForm.getGuestId() != null
				&& !employeeInfoEditForm.getGuestId().isEmpty() && Integer.parseInt(employeeInfoEditForm.getGuestId())>0) {
			guestFaculty.setId(Integer.parseInt(employeeInfoEditForm
					.getGuestId()));
		}
		
		   Set<GuestPreviousChristWorkDetails> guestworkingDetails = getGuestWorkDatesObjectsEdit(employeeInfoEditForm);
		    guestFaculty.setPreviousChristDetails(guestworkingDetails);
			
			Set<GuestImages> empImages = getEmpImagesBoObjectsEdit(employeeInfoEditForm);
			guestFaculty.setEmpImages(empImages);

			Set<GuestPreviousExperience> previousSet = new HashSet<GuestPreviousExperience>();
			Set<GuestEducationalDetails> educationalDeatialSet = new HashSet<GuestEducationalDetails>();
			try {
								
				if (employeeInfoEditForm.getEmployeeInfoTONew() != null) {
					if (employeeInfoEditForm.getEmployeeInfoTONew()
							.getExperiences() != null) {
						List<GuestPreviousExperienceTO> list = employeeInfoEditForm
								.getEmployeeInfoTONew().getExperiences();
						if (list != null) {
							Iterator<GuestPreviousExperienceTO> iterator = list
									.iterator();
							while (iterator.hasNext()) {
								GuestPreviousExperienceTO empPreviousOrgTo = iterator
										.next();
								GuestPreviousExperience empPreviousExp = new GuestPreviousExperience();
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
										
										if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty())
										{
										GuestFaculty emp = new GuestFaculty();
										emp.setId(Integer.parseInt(employeeInfoEditForm.getGuestId()));
										empPreviousExp.setGuest(emp);
										}
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
						List<GuestPreviousExperienceTO> list = employeeInfoEditForm
								.getEmployeeInfoTONew().getTeachingExperience();
						if (list != null) {
							Iterator<GuestPreviousExperienceTO> iterator = list
									.iterator();
							while (iterator.hasNext()) {
								GuestPreviousExperienceTO empPreviousOrgTo = iterator
										.next();
								GuestPreviousExperience empPreviousExp1 = new GuestPreviousExperience();
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
											empPreviousExp1
													.setId(empPreviousOrgTo
															.getId());
										}

										if (empPreviousOrgTo
												.getTeachingExpYears() != null
												&& !empPreviousOrgTo
														.getTeachingExpYears()
														.isEmpty()) {
											empPreviousExp1
													.setExpYears(Integer
															.parseInt(empPreviousOrgTo
																	.getTeachingExpYears()));
										}

										if (empPreviousOrgTo
												.getTeachingExpMonths() != null
												&& !empPreviousOrgTo
														.getTeachingExpMonths()
														.isEmpty()) {
											empPreviousExp1
													.setExpMonths(Integer
															.parseInt(empPreviousOrgTo
																	.getTeachingExpMonths()));
										}

										if (empPreviousOrgTo
												.getCurrentTeachnigDesignation() != null
												&& !empPreviousOrgTo
														.getCurrentTeachnigDesignation()
														.isEmpty()) {
											empPreviousExp1
													.setEmpDesignation(empPreviousOrgTo
															.getCurrentTeachnigDesignation());
										}

										if (empPreviousOrgTo
												.getCurrentTeachingOrganisation() != null
												&& !empPreviousOrgTo
														.getCurrentTeachingOrganisation()
														.isEmpty()) {
											empPreviousExp1
													.setEmpOrganization(empPreviousOrgTo
															.getCurrentTeachingOrganisation());
										}
										
										if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty())
										{
										GuestFaculty emp = new GuestFaculty();
										emp.setId(Integer
												.parseInt(employeeInfoEditForm
														.getGuestId()));
										empPreviousExp1.setGuest(emp);
										}
										empPreviousExp1
												.setTeachingExperience(true);
										empPreviousExp1.setActive(true);
										empPreviousExp1
												.setCreatedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp1
												.setCreatedDate(new Date());
										empPreviousExp1
												.setModifiedBy(employeeInfoEditForm
														.getUserId());
										empPreviousExp1
												.setModifiedDate(new Date());
										previousSet.add(empPreviousExp1);
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
					guestFaculty.setCurrentlyWorking(true);
					if (employeeInfoEditForm.getDesignation() != null
							&& !employeeInfoEditForm.getDesignation().isEmpty()) {
						guestFaculty.setDesignationName(employeeInfoEditForm
								.getDesignation());
					}

					if (employeeInfoEditForm.getOrgAddress() != null
							&& !employeeInfoEditForm.getOrgAddress().isEmpty()) {
						guestFaculty.setOrganistionName(employeeInfoEditForm
								.getOrgAddress());
					}

				} else {
					guestFaculty.setCurrentlyWorking(false);
					guestFaculty.setDesignationName(null);
					guestFaculty.setOrganistionName(null);
				}
				guestFaculty.setPreviousExpSet(previousSet);

				if (employeeInfoEditForm.getEmployeeInfoTONew()
						.getEmpQualificationFixedTo() != null) {
					List<EmpQualificationLevelTo> qualificationFixedTo = employeeInfoEditForm
							.getEmployeeInfoTONew().getEmpQualificationFixedTo();
					Iterator<EmpQualificationLevelTo> iterator = qualificationFixedTo
							.iterator();
					while (iterator.hasNext()) {
						EmpQualificationLevelTo qualificationFixed = iterator
								.next();
						GuestEducationalDetails educationalDeatails = null;
						if (qualificationFixed != null) {
							if ((qualificationFixed.getInstitute() != null && !qualificationFixed
									.getInstitute().isEmpty())
									|| (qualificationFixed.getCourse() != null && !qualificationFixed
											.getCourse().isEmpty())
									|| (qualificationFixed.getSpecialization() != null && !qualificationFixed
											.getSpecialization().isEmpty())
									|| (qualificationFixed.getGrade() != null && !qualificationFixed
											.getGrade().isEmpty())) {
								educationalDeatails = new GuestEducationalDetails();

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
								GuestFaculty emp = new GuestFaculty();
								if(employeeInfoEditForm.getGuestId() != null ){
									emp.setId(Integer.parseInt(employeeInfoEditForm
											.getGuestId()));
									educationalDeatails.setGuest(emp);
								}
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
						GuestEducationalDetails educationalDetails = null;
						if (levelTo != null) {
							if ((levelTo.getInstitute() != null && !levelTo
									.getInstitute().isEmpty())
									|| (levelTo.getCourse() != null && !levelTo
											.getCourse().isEmpty())
									|| (levelTo.getSpecialization() != null && !levelTo
											.getSpecialization().isEmpty())
									|| (levelTo.getGrade() != null && !levelTo
											.getGrade().isEmpty())) {
								educationalDetails = new GuestEducationalDetails();
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

								GuestFaculty emp = new GuestFaculty();
								emp.setId(Integer.parseInt(employeeInfoEditForm
										.getGuestId()));
								educationalDetails.setGuest(emp);
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

				guestFaculty.setEducationalDetailsSet(educationalDeatialSet);

				if (employeeInfoEditForm.getDesignationPfId() != null
						&& !employeeInfoEditForm.getDesignationPfId().isEmpty()) {
					Designation designation = new Designation();
					designation.setId(Integer.parseInt(employeeInfoEditForm
							.getDesignationPfId()));
					guestFaculty.setDesignation(designation);
				}

				if (employeeInfoEditForm.getDepartmentId() != null
						&& !employeeInfoEditForm.getDepartmentId().isEmpty()) {
					Department department = new Department();
					department.setId(Integer.parseInt(employeeInfoEditForm
							.getDepartmentId()));
					guestFaculty.setDepartment(department);
				}

				if (employeeInfoEditForm.getName() != null
						&& !employeeInfoEditForm.getName().isEmpty()) {
					guestFaculty.setFirstName(employeeInfoEditForm.getName().toUpperCase());
				}

				if (employeeInfoEditForm.getuId() != null
						&& !employeeInfoEditForm.getuId().isEmpty()) {
					guestFaculty.setUid(employeeInfoEditForm.getuId());
				}
				if (employeeInfoEditForm.getCode() != null
						&& !employeeInfoEditForm.getCode().isEmpty()) {
					guestFaculty.setCode(employeeInfoEditForm.getCode());
				}
				
				if (employeeInfoEditForm.getCurrentAddressLine1() != null
						&& !employeeInfoEditForm.getCurrentAddressLine1()
								.isEmpty()) {
					guestFaculty.setCommunicationAddressLine1(employeeInfoEditForm
							.getCurrentAddressLine1());
				}

				if (employeeInfoEditForm.getCurrentAddressLine2() != null
						&& !employeeInfoEditForm.getCurrentAddressLine2()
								.isEmpty()) {
					guestFaculty.setCommunicationAddressLine2(employeeInfoEditForm
							.getCurrentAddressLine2());
				}

				if (employeeInfoEditForm.getCurrentZipCode() != null
						&& !employeeInfoEditForm.getCurrentZipCode().isEmpty()) {
					guestFaculty.setCommunicationAddressZip(employeeInfoEditForm
							.getCurrentZipCode());
				}

				if (employeeInfoEditForm.getCurrentCountryId() != null
						&& !employeeInfoEditForm.getCurrentCountryId()
								.isEmpty()) {
					Country currentCountry = new Country();
					currentCountry.setId(Integer.parseInt(employeeInfoEditForm
							.getCurrentCountryId()));
					guestFaculty
							.setCountryByCommunicationAddressCountryId(currentCountry);
				}

				if (employeeInfoEditForm.getCurrentState() != null
						&& !employeeInfoEditForm.getCurrentState().isEmpty()) {
					if (employeeInfoEditForm.getCurrentState()
							.equalsIgnoreCase("other")) {
						if (employeeInfoEditForm.getOtherCurrentState() != null
								&& !employeeInfoEditForm.getOtherCurrentState()
										.isEmpty()) {
							guestFaculty
									.setCommunicationAddressStateOthers(employeeInfoEditForm
											.getOtherCurrentState());
						}
					} else {
						State currentState = new State();
						currentState.setId(Integer
								.parseInt(employeeInfoEditForm
										.getCurrentState()));
						guestFaculty
								.setStateByCommunicationAddressStateId(currentState);
					}
				}

				if (employeeInfoEditForm.getCurrentCity() != null
						&& !employeeInfoEditForm.getCurrentCity().isEmpty()) {
					guestFaculty.setCommunicationAddressCity(employeeInfoEditForm
							.getCurrentCity());
				}

				if (employeeInfoEditForm.getSameAddress().equalsIgnoreCase(
						"true")) {
					if (employeeInfoEditForm.getCurrentAddressLine1() != null
							&& !employeeInfoEditForm.getCurrentAddressLine1()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine1(employeeInfoEditForm
								.getCurrentAddressLine1());
					}

					if (employeeInfoEditForm.getCurrentAddressLine2() != null
							&& !employeeInfoEditForm.getCurrentAddressLine2()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine2(employeeInfoEditForm
								.getCurrentAddressLine2());
					}

					if (employeeInfoEditForm.getCurrentZipCode() != null
							&& !employeeInfoEditForm.getCurrentZipCode()
									.isEmpty()) {
						guestFaculty.setPermanentAddressZip(employeeInfoEditForm
								.getCurrentZipCode());
					}

					if (employeeInfoEditForm.getCurrentCountryId() != null
							&& !employeeInfoEditForm.getCurrentCountryId()
									.isEmpty()) {
						Country currentCountry = new Country();
						currentCountry.setId(Integer
								.parseInt(employeeInfoEditForm
										.getCurrentCountryId()));
						guestFaculty
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
								guestFaculty
										.setPermanentAddressStateOthers(employeeInfoEditForm
												.getOtherCurrentState());
							}
						} else {
							State currentState = new State();
							currentState.setId(Integer
									.parseInt(employeeInfoEditForm
											.getCurrentState()));
							guestFaculty
									.setStateByPermanentAddressStateId(currentState);
						}
					}

					if (employeeInfoEditForm.getCurrentCity() != null
							&& !employeeInfoEditForm.getCurrentCity().isEmpty()) {
						guestFaculty.setPermanentAddressCity(employeeInfoEditForm
								.getCurrentCity());
					}

				} else {
					if (employeeInfoEditForm.getAddressLine1() != null
							&& !employeeInfoEditForm.getAddressLine1()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine1(employeeInfoEditForm
								.getAddressLine1());
					}

					if (employeeInfoEditForm.getAddressLine2() != null
							&& !employeeInfoEditForm.getAddressLine2()
									.isEmpty()) {
						guestFaculty.setPermanentAddressLine2(employeeInfoEditForm
								.getAddressLine2());
					}

					if (employeeInfoEditForm.getPermanentZipCode() != null
							&& !employeeInfoEditForm.getPermanentZipCode()
									.isEmpty()) {
						guestFaculty.setPermanentAddressZip(employeeInfoEditForm
								.getPermanentZipCode());
					}

					if (employeeInfoEditForm.getCountryId() != null
							&& !employeeInfoEditForm.getCountryId().isEmpty()) {
						Country country = new Country();
						country.setId(Integer.parseInt(employeeInfoEditForm
								.getCountryId()));
						guestFaculty.setCountryByPermanentAddressCountryId(country);
					}

					if (employeeInfoEditForm.getStateId() != null
							&& !employeeInfoEditForm.getStateId().isEmpty()) {
						if (employeeInfoEditForm.getStateId().equalsIgnoreCase(
								"other")) {
							if (employeeInfoEditForm.getOtherPermanentState() != null
									&& !employeeInfoEditForm
											.getOtherPermanentState().isEmpty()) {
								guestFaculty
										.setPermanentAddressStateOthers(employeeInfoEditForm
												.getOtherPermanentState());
							}
						} else {
							State state = new State();
							state.setId(Integer.parseInt(employeeInfoEditForm
									.getStateId()));
							guestFaculty.setStateByPermanentAddressStateId(state);
						}
					}

					if (employeeInfoEditForm.getCity() != null
							&& !employeeInfoEditForm.getCity().isEmpty()) {
						guestFaculty.setPermanentAddressCity(employeeInfoEditForm
								.getCity());
					}
				}
				if (employeeInfoEditForm.getNationalityId() != null
						&& !employeeInfoEditForm.getNationalityId().isEmpty()) {
					Nationality nationality = new Nationality();
					nationality.setId(Integer.parseInt(employeeInfoEditForm
							.getNationalityId()));
					guestFaculty.setNationality(nationality);
				}

				if (employeeInfoEditForm.getGender() != null
						&& !employeeInfoEditForm.getGender().isEmpty()) {
					guestFaculty.setGender(employeeInfoEditForm.getGender());
				}

				if (employeeInfoEditForm.getMaritalStatus() != null
						&& !employeeInfoEditForm.getMaritalStatus().isEmpty()) {
					guestFaculty.setMaritalStatus(employeeInfoEditForm
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
									guestFaculty.setEligibilityTestOther(employeeInfoEditForm.getOtherEligibilityTestValue());
								}
							}
						}
					}
					guestFaculty.setEligibilityTest(eligibilityTest);
				}
				
				
				if(employeeInfoEditForm.getIndustryFunctionalArea()!=null && !employeeInfoEditForm.getIndustryFunctionalArea().trim().isEmpty()){
					guestFaculty.setIndustryFunctionalArea(employeeInfoEditForm.getIndustryFunctionalArea());
				}
				if (employeeInfoEditForm.getDateOfBirth() != null
						&& !employeeInfoEditForm.getDateOfBirth().isEmpty()) {
					guestFaculty.setDob(CommonUtil
							.ConvertStringToDate(employeeInfoEditForm
									.getDateOfBirth()));
				}
				
				
				if (employeeInfoEditForm.getEmail() != null
						&& !employeeInfoEditForm.getEmail().isEmpty()) {
					guestFaculty.setEmail(employeeInfoEditForm.getEmail());
				}

				
				
				if (employeeInfoEditForm.getReservationCategory() != null
						&& !employeeInfoEditForm.getReservationCategory()
								.isEmpty()) {
					guestFaculty.setReservationCategory(employeeInfoEditForm
							.getReservationCategory());
				}
				 if(employeeInfoEditForm.getHandicappedDescription()!=null && !employeeInfoEditForm.getHandicappedDescription().trim().isEmpty())
					 guestFaculty.setHandicappedDescription(employeeInfoEditForm.getHandicappedDescription());	
					

				if (employeeInfoEditForm.getHomePhone1() != null
						&& !employeeInfoEditForm.getHomePhone1().isEmpty()) {
					guestFaculty
							.setCurrentAddressHomeTelephone1(employeeInfoEditForm
									.getHomePhone1());
				}

				if (employeeInfoEditForm.getHomePhone2() != null
						&& !employeeInfoEditForm.getHomePhone2().isEmpty()) {
					guestFaculty
							.setCurrentAddressHomeTelephone2(employeeInfoEditForm
									.getHomePhone2());
				}

				if (employeeInfoEditForm.getHomePhone3() != null
						&& !employeeInfoEditForm.getHomePhone3().isEmpty()) {
					guestFaculty
							.setCurrentAddressHomeTelephone3(employeeInfoEditForm
									.getHomePhone3());
				}
				if (employeeInfoEditForm.getWorkPhNo1() != null
						&& !employeeInfoEditForm.getWorkPhNo1().isEmpty()) {
					guestFaculty
							.setCurrentAddressWorkTelephone1(employeeInfoEditForm
									.getWorkPhNo1());
				}

				if (employeeInfoEditForm.getWorkPhNo2() != null
						&& !employeeInfoEditForm.getWorkPhNo2().isEmpty()) {
					guestFaculty
							.setCurrentAddressWorkTelephone2(employeeInfoEditForm
									.getWorkPhNo2());
				}

				if (employeeInfoEditForm.getWorkPhNo3() != null
						&& !employeeInfoEditForm.getWorkPhNo3().isEmpty()) {
					guestFaculty
							.setCurrentAddressWorkTelephone3(employeeInfoEditForm
									.getWorkPhNo3());
				}
				if (employeeInfoEditForm.getMobileNo1() != null
						&& !employeeInfoEditForm.getMobileNo1().isEmpty()) {
					guestFaculty.setCurrentAddressMobile1(employeeInfoEditForm
							.getMobileNo1());
				}
				if (employeeInfoEditForm.getHighQualifForAlbum() != null
						&& !employeeInfoEditForm.getHighQualifForAlbum()
								.isEmpty()) {
					guestFaculty.setHighQualifForAlbum(employeeInfoEditForm
							.getHighQualifForAlbum());
				}

				if (employeeInfoEditForm.getEmpSubjectAreaId() != null
						&& !employeeInfoEditForm.getEmpSubjectAreaId()
								.isEmpty()) {
					SubjectAreaBO subjectArea = new SubjectAreaBO();
					subjectArea.setId(Integer.parseInt(employeeInfoEditForm
							.getEmpSubjectAreaId()));
					guestFaculty.setEmpSubjectArea(subjectArea);
				}

			

				if (employeeInfoEditForm.getNoOfPublicationsRefered() != null
						&& !employeeInfoEditForm.getNoOfPublicationsRefered()
								.isEmpty()) {
					guestFaculty.setNoOfPublicationsRefered(employeeInfoEditForm
							.getNoOfPublicationsRefered());
				}

				if (employeeInfoEditForm.getNoOfPublicationsNotRefered() != null
						&& !employeeInfoEditForm
								.getNoOfPublicationsNotRefered().isEmpty()) {
					guestFaculty.setNoOfPublicationsNotRefered(employeeInfoEditForm
							.getNoOfPublicationsNotRefered());
				}

				if (employeeInfoEditForm.getBooks() != null
						&& !employeeInfoEditForm.getBooks().isEmpty()) {
					guestFaculty.setBooks(employeeInfoEditForm.getBooks());
				}
				

				if (employeeInfoEditForm.getEmpQualificationLevelId() != null
						&& !employeeInfoEditForm.getEmpQualificationLevelId()
								.isEmpty()) {
					QualificationLevelBO empQualificationLevel = new QualificationLevelBO();
					empQualificationLevel.setId(Integer
							.parseInt(employeeInfoEditForm
									.getEmpQualificationLevelId()));
					guestFaculty.setEmpQualificationLevel(empQualificationLevel);
				}

				
				

				if (employeeInfoEditForm.getFourWheelerNo() != null
						&& !employeeInfoEditForm.getFourWheelerNo().isEmpty()) {
					guestFaculty.setFourWheelerNo(employeeInfoEditForm
							.getFourWheelerNo());
				}
				if (employeeInfoEditForm.getTwoWheelerNo() != null
						&& !employeeInfoEditForm.getTwoWheelerNo().isEmpty()) {
					guestFaculty.setTwoWheelerNo(employeeInfoEditForm
							.getTwoWheelerNo());
				}
			
				if (employeeInfoEditForm.getPfNo() != null
						&& !employeeInfoEditForm.getPfNo().isEmpty()) {
					guestFaculty.setPfNo(employeeInfoEditForm.getPfNo());
				}
				if (employeeInfoEditForm.getBankAccNo() != null
						&& !employeeInfoEditForm.getBankAccNo().isEmpty()) {
					guestFaculty.setBankAccNo(employeeInfoEditForm.getBankAccNo());
				}
				if (employeeInfoEditForm.getStreamId() != null
						&& !employeeInfoEditForm.getStreamId().isEmpty()) {
					EmployeeStreamBO empStream = new EmployeeStreamBO();
					empStream.setId(Integer.parseInt(employeeInfoEditForm
							.getStreamId()));
					guestFaculty.setStreamId(empStream);
				}
				if (employeeInfoEditForm.getWorkLocationId() != null
						&& !employeeInfoEditForm.getWorkLocationId().isEmpty()) {
					EmployeeWorkLocationBO empworkLoc = new EmployeeWorkLocationBO();
					empworkLoc.setId(Integer.parseInt(employeeInfoEditForm
							.getWorkLocationId()));
					guestFaculty.setWorkLocationId(empworkLoc);
				}
				if (employeeInfoEditForm.getActive() != null
						&& !employeeInfoEditForm.getActive().isEmpty()) {
					String Value = employeeInfoEditForm.getActive();
					if (Value.equals("1"))
						guestFaculty.setActive(true);
					else
						guestFaculty.setActive(false);
				}
				
				if (employeeInfoEditForm.getSameAddress() != null
						&& !employeeInfoEditForm.getSameAddress().isEmpty()) {
					String Value = employeeInfoEditForm.getSameAddress();
					if (Value.equals("true"))
						guestFaculty.setIsSameAddress(true);
					else
						guestFaculty.setIsSameAddress(false);
				}
				if (employeeInfoEditForm.getTeachingStaff() != null
						&& !employeeInfoEditForm.getTeachingStaff().isEmpty()) {
					String Value = employeeInfoEditForm.getTeachingStaff();
					if (Value.equals("1"))
						guestFaculty.setTeachingStaff(true);
					else
						guestFaculty.setTeachingStaff(false);
				}
				
				if (employeeInfoEditForm.getPanno() != null
						&& !employeeInfoEditForm.getPanno().isEmpty()) {
					guestFaculty.setPanNo(employeeInfoEditForm.getPanno());
				}
				if (employeeInfoEditForm.getExpYears() != null
						&& !employeeInfoEditForm.getExpYears().isEmpty()) {
					guestFaculty
							.setTotalExpYear(employeeInfoEditForm.getExpYears());
				}
				if (employeeInfoEditForm.getExpMonths() != null
						&& !employeeInfoEditForm.getExpMonths().isEmpty()) {
					guestFaculty.setTotalExpMonths(employeeInfoEditForm
							.getExpMonths());
				}
				if (employeeInfoEditForm.getRelevantExpMonths() != null
						&& !employeeInfoEditForm.getRelevantExpMonths()
								.isEmpty()) {
					guestFaculty.setRelevantExpMonths(employeeInfoEditForm
							.getRelevantExpMonths());
				}
				if (employeeInfoEditForm.getRelevantExpYears() != null
						&& !employeeInfoEditForm.getRelevantExpYears()
								.isEmpty()) {
					guestFaculty.setRelevantExpYears(employeeInfoEditForm
							.getRelevantExpYears());
				}
				if (employeeInfoEditForm.getReligionId() != null
						&& !employeeInfoEditForm.getReligionId().isEmpty()) {
					Religion empReligion = new Religion();
					empReligion.setId(Integer.parseInt(employeeInfoEditForm
							.getReligionId()));
					guestFaculty.setReligionId(empReligion);
				}
				if (employeeInfoEditForm.getOtherInfo() != null
						&& !employeeInfoEditForm.getOtherInfo().isEmpty()) {
					guestFaculty.setOtherInfo(employeeInfoEditForm.getOtherInfo());
				}
				if (employeeInfoEditForm.getuId() != null
						&& !employeeInfoEditForm.getuId().isEmpty()) {
					guestFaculty.setUid(employeeInfoEditForm.getuId());
				}
				if (employeeInfoEditForm.getOfficialEmail() != null
						&& !employeeInfoEditForm.getOfficialEmail().isEmpty()) {
					guestFaculty.setWorkEmail(employeeInfoEditForm
							.getOfficialEmail());
				}
				
				if (employeeInfoEditForm.getEmContactWorkTel() != null
						&& !employeeInfoEditForm.getEmContactWorkTel()
								.isEmpty()) {
					guestFaculty.setEmergencyWorkTelephone(employeeInfoEditForm
							.getEmContactWorkTel());
				}
				if (employeeInfoEditForm.getEmContactHomeTel() != null
						&& !employeeInfoEditForm.getEmContactHomeTel()
								.isEmpty()) {
					guestFaculty.setEmergencyHomeTelephone(employeeInfoEditForm
							.getEmContactHomeTel());
				}
				if (employeeInfoEditForm.getEmContactMobile() != null
						&& !employeeInfoEditForm.getEmContactMobile().isEmpty()) {
					guestFaculty.setEmergencyMobile(employeeInfoEditForm
							.getEmContactMobile());
				}
				if (employeeInfoEditForm.getEmContactAddress() != null
						&& !employeeInfoEditForm.getEmContactAddress().isEmpty()) {
					guestFaculty.setEmContactAddress(employeeInfoEditForm
							.getEmContactAddress());
				}
				if (employeeInfoEditForm.getTitleId() != null
						&& !employeeInfoEditForm.getTitleId().isEmpty()) {
					EmpJobTitle empJobTitle = new EmpJobTitle();
					empJobTitle.setId(Integer.parseInt(employeeInfoEditForm
							.getTitleId()));
					guestFaculty.setTitleId(empJobTitle);
				}
				if (employeeInfoEditForm.getEmContactRelationship() != null
						&& !employeeInfoEditForm.getEmContactRelationship()
								.isEmpty()) {
					guestFaculty.setEmContactRelationship(employeeInfoEditForm
							.getEmContactRelationship());
				}
				if (employeeInfoEditForm.getEmContactName() != null
						&& !employeeInfoEditForm.getEmContactName().isEmpty()) {
					guestFaculty.setEmergencyContName(employeeInfoEditForm
							.getEmContactName());
				}
				
				
				if (employeeInfoEditForm.getMaritalStatus() != null
						&& !employeeInfoEditForm.getMaritalStatus().isEmpty()) {
					guestFaculty.setMaritalStatus(employeeInfoEditForm
							.getMaritalStatus());
				}
				if (employeeInfoEditForm.getBloodGroup() != null
						&& !employeeInfoEditForm.getBloodGroup().isEmpty()) {
					guestFaculty
							.setBloodGroup(employeeInfoEditForm.getBloodGroup());
				}
				if (employeeInfoEditForm.getQualificationId() != null
						&& !employeeInfoEditForm.getQualificationId().isEmpty()) {
					QualificationLevelBO qual = new QualificationLevelBO();
					qual.setId(Integer.parseInt(employeeInfoEditForm
							.getQualificationId()));
					guestFaculty.setEmpQualificationLevel(qual);
				}
				if (employeeInfoEditForm.getReferredBy() != null
						&& !employeeInfoEditForm.getReferredBy().isEmpty()) {
					guestFaculty.setReferredBy(employeeInfoEditForm.getReferredBy());
				}
				if (employeeInfoEditForm.getHonorariumPerHours() != null
						&& !employeeInfoEditForm.getHonorariumPerHours().isEmpty()) {
					guestFaculty.setHonorariumPerHours(employeeInfoEditForm.getHonorariumPerHours());
				}
				if (employeeInfoEditForm.getWorkingHoursPerWeek() != null
						&& !employeeInfoEditForm.getWorkingHoursPerWeek().isEmpty()) {
					guestFaculty.setWorkingHoursPerWeek(employeeInfoEditForm.getWorkingHoursPerWeek());
				}
				if (employeeInfoEditForm.getSubjectSpecilization() != null
						&& !employeeInfoEditForm.getSubjectSpecilization().isEmpty()) {
					guestFaculty.setSubjectSpecilization(employeeInfoEditForm.getSubjectSpecilization());
				}
				if (employeeInfoEditForm.getHighQualifForWebsite() != null
						&& !employeeInfoEditForm.getHighQualifForWebsite().isEmpty()) {
					guestFaculty.setHighQualifForWebsite(employeeInfoEditForm.getHighQualifForWebsite());
				}
				if (employeeInfoEditForm.getStaffId() != null
						&& !employeeInfoEditForm.getStaffId().isEmpty()) {
					guestFaculty.setStaffId(employeeInfoEditForm.getStaffId());
				}
				if (employeeInfoEditForm.getDisplayInWebsite() != null
						&& !employeeInfoEditForm.getDisplayInWebsite().isEmpty()) {
					String ValueNew = employeeInfoEditForm.getDisplayInWebsite();
					if (ValueNew.equals("1"))
						guestFaculty.setDisplayInWebsite(true);
					else
						guestFaculty.setDisplayInWebsite(false);
				}
				if(employeeInfoEditForm.getBankBranch()!=null && !employeeInfoEditForm.getBankBranch().isEmpty())
					guestFaculty.setBankBranch(employeeInfoEditForm.getBankBranch());
				
				if(employeeInfoEditForm.getBankIfscCode()!=null && !employeeInfoEditForm.getBankIfscCode().isEmpty())
					guestFaculty.setBankIfscCode(employeeInfoEditForm.getBankIfscCode());
				

			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			guestFaculty.setIsActive(true);
			guestFaculty.setCreatedBy(employeeInfoEditForm.getUserId());
			guestFaculty.setCreatedDate(new Date());
			guestFaculty.setLastModifiedDate(new Date());
			guestFaculty.setModifiedBy(employeeInfoEditForm.getUserId());

		
		return guestFaculty;
	}

	
	
	/**
	 * @param employeeInfoEditForm
	 * @return
	 */
	private Set<GuestImages> getEmpImagesBoObjects(GuestFacultyInfoForm employeeInfoEditForm) throws Exception {
		Set<GuestImages> images = new HashSet<GuestImages>();
		
		GuestImages img = new GuestImages();
				if (employeeInfoEditForm.getEmpPhoto() != null || employeeInfoEditForm.getPhotoBytes() != null)
					{
					GuestFaculty emp = new GuestFaculty();
					if(employeeInfoEditForm.getGuestId() != null){
						emp.setId(Integer.parseInt(employeeInfoEditForm.getGuestId()));
						img.setGuest(emp);
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
					if(img.getEmpPhoto()!=null && img.getEmpPhoto().length>0)
					{
						images.add(img);
					}
				}
			
		return images;
	}
	
	
	/**
	 * @param employeeInfoEditForm
	 * @return
	 */
	private Set<GuestImages> getEmpImagesBoObjectsEdit(GuestFacultyInfoForm employeeInfoEditForm) throws Exception {
		Set<GuestImages> images = new HashSet<GuestImages>();
		
		GuestImages img = new GuestImages();
				if (employeeInfoEditForm.getEmpPhoto() != null || employeeInfoEditForm.getPhotoBytes() != null)
					{
					GuestFaculty emp = new GuestFaculty();
					if(employeeInfoEditForm.getGuestId() != null){
						emp.setId(Integer.parseInt(employeeInfoEditForm.getGuestId()));
						img.setGuest(emp);
					}
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
					if(img.getEmpPhoto()!=null && img.getEmpPhoto().length>0)
					{
						images.add(img);
					}
				}
			
		return images;
	}
	
	/**
	 * @param employeeInfoEditForm
	 * @return
	 */
	
	private Set<GuestPreviousChristWorkDetails> getGuestWorkDatesObjects(GuestFacultyInfoForm employeeInfoEditForm) throws Exception {
		Set<GuestPreviousChristWorkDetails> gpwsDetails = new HashSet<GuestPreviousChristWorkDetails>();
		
		
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew().getPreviousworkDetails() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew().getPreviousworkDetails()
						.isEmpty()) {
			Iterator<GuestPreviousChristWorkDetailsTO> itr = employeeInfoEditForm.getEmployeeInfoTONew().getPreviousworkDetails().iterator();
			while (itr.hasNext()) {
				GuestPreviousChristWorkDetailsTO to = (GuestPreviousChristWorkDetailsTO) itr.next();
				GuestPreviousChristWorkDetails gpcw = new GuestPreviousChristWorkDetails();
				
				if (to.getStartDate() != null && !to.getStartDate().isEmpty()
						|| to.getEndDate() != null && !to.getEndDate().isEmpty()|| to.getSemester() != null
						&& !to.getSemester().isEmpty() ) {
					   	gpcw.setCreatedBy(employeeInfoEditForm.getUserId());
						gpcw.setCreatedDate(new Date());
						if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty())
						{
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditForm.getGuestId()));
						gpcw.setGuest(emp);
						}
						gpcw.setModifiedBy(employeeInfoEditForm.getUserId());
						gpcw.setLastModifiedDate(new Date());
						gpcw.setIsActive(true);
						gpcw.setStartDate(CommonUtil.ConvertStringToDate(to.getStartDate()));
						gpcw.setEndDate(CommonUtil.ConvertStringToDate(to.getEndDate()));
						//start by giri
						if(to.getDeptId()!=null && !to.getDeptId().isEmpty()){
							Department department=new Department();
								department.setId(Integer.parseInt(to.getDeptId()));
								gpcw.setDeptId(department);
						}
						if(to.getStrmId()!=null && !to.getStrmId().isEmpty()){
							EmployeeStreamBO employeeStreamBO=new EmployeeStreamBO();
								employeeStreamBO.setId(Integer.parseInt(to.getStrmId()));
								gpcw.setStrmId(employeeStreamBO);
						}
						if(to.getWorkLocId()!=null && !to.getWorkLocId().isEmpty()){
							EmployeeWorkLocationBO employeeWorkLocationBO=new EmployeeWorkLocationBO();
								employeeWorkLocationBO.setId(Integer.parseInt(to.getWorkLocId()));
								gpcw.setWorklLocId(employeeWorkLocationBO);
						}
						if(to.getWorkHoursPerWeek()!=null && !to.getWorkHoursPerWeek().isEmpty()){
							gpcw.setWorkHoursPerWeek(to.getWorkHoursPerWeek());
						}
						if(to.getHonorarium()!=null && !to.getHonorarium().isEmpty()){
							gpcw.setHonorarium(to.getHonorarium());
						}
						//end by giri
						gpcw.setSemester(to.getSemester());
						if (to.getIsCurrentWorkingDates() != null) {
							String Value = (String.valueOf(to.getIsCurrentWorkingDates()));
							if (Value.equals("true"))
								gpcw.setIsCurrentWorkingDates(true);
							else
								gpcw.setIsCurrentWorkingDates(false);
						}
					}
					
					gpwsDetails.add(gpcw);
				}
			}
		
		return gpwsDetails;
	}
	
	/**
	 * @param employeeInfoEditForm
	 * @return
	 */
	
	private Set<GuestPreviousChristWorkDetails> getGuestWorkDatesObjectsEdit(GuestFacultyInfoForm employeeInfoEditForm) throws Exception {
		Set<GuestPreviousChristWorkDetails> gpwsDetails = new HashSet<GuestPreviousChristWorkDetails>();
		
		
		if (employeeInfoEditForm.getEmployeeInfoTONew() != null
				&& employeeInfoEditForm.getEmployeeInfoTONew().getPreviousworkDetails() != null
				&& !employeeInfoEditForm.getEmployeeInfoTONew().getPreviousworkDetails()
						.isEmpty()) {
			Iterator<GuestPreviousChristWorkDetailsTO> itr = employeeInfoEditForm.getEmployeeInfoTONew().getPreviousworkDetails().iterator();
			while (itr.hasNext()) {
				GuestPreviousChristWorkDetailsTO to = (GuestPreviousChristWorkDetailsTO) itr.next();
				GuestPreviousChristWorkDetails gpcw = new GuestPreviousChristWorkDetails();
				
				if (to.getStartDate() != null && !to.getStartDate().isEmpty()
						|| to.getEndDate() != null && !to.getEndDate().isEmpty()|| to.getSemester() != null
						&& !to.getSemester().isEmpty()) {
					   if (to.getId() > 0) {
							gpcw.setId(to.getId());
						}
						gpcw.setCreatedBy(employeeInfoEditForm.getUserId());
						gpcw.setCreatedDate(new Date());
						if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty())
						{
						GuestFaculty emp = new GuestFaculty();
						emp.setId(Integer.parseInt(employeeInfoEditForm.getGuestId()));
						gpcw.setGuest(emp);
						}
						//start by giri
						if(to.getDeptId()!=null && !to.getDeptId().isEmpty()){
							Department department=new Department();
								department.setId(Integer.parseInt(to.getDeptId()));
								gpcw.setDeptId(department);
						}
						if(to.getStrmId()!=null && !to.getStrmId().isEmpty()){
							EmployeeStreamBO employeeStreamBO=new EmployeeStreamBO();
								employeeStreamBO.setId(Integer.parseInt(to.getStrmId()));
								gpcw.setStrmId(employeeStreamBO);
						}
						if(to.getWorkLocId()!=null && !to.getWorkLocId().isEmpty()){
							EmployeeWorkLocationBO employeeWorkLocationBO=new EmployeeWorkLocationBO();
								employeeWorkLocationBO.setId(Integer.parseInt(to.getWorkLocId()));
								gpcw.setWorklLocId(employeeWorkLocationBO);
						}
						if(to.getWorkHoursPerWeek()!=null && !to.getWorkHoursPerWeek().isEmpty()){
							gpcw.setWorkHoursPerWeek(to.getWorkHoursPerWeek());
						}
						if(to.getHonorarium()!=null && !to.getHonorarium().isEmpty()){
							gpcw.setHonorarium(to.getHonorarium());
						}
						//end by giri
						gpcw.setModifiedBy(employeeInfoEditForm.getUserId());
						gpcw.setLastModifiedDate(new Date());
						gpcw.setIsActive(true);
						gpcw.setStartDate(CommonUtil.ConvertStringToDate(to.getStartDate()));
						gpcw.setEndDate(CommonUtil.ConvertStringToDate(to.getEndDate()));
						gpcw.setSemester(to.getSemester());
						if (to.getIsCurrentWorkingDates() != null) {
							String Value = (String.valueOf(to.getIsCurrentWorkingDates()));
							if (Value.equals("true"))
								gpcw.setIsCurrentWorkingDates(true);
							else
								gpcw.setIsCurrentWorkingDates(false);
						}
					}
					
					gpwsDetails.add(gpcw);
				}
			}
		
		return gpwsDetails;
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
	
	public void convertResumeBoToForm(EmpOnlineResume empApplicantDetails,
			GuestFacultyInfoForm objform) throws Exception {

	if(empApplicantDetails!=null){
		if(StringUtils.isNotEmpty(empApplicantDetails.getAddressLine1()) && empApplicantDetails.getAddressLine1()!=null ){
			  objform.setAddressLine1(empApplicantDetails.getAddressLine1());
		}
		if(StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getIsSameAddress()))){
			String Value= String.valueOf(empApplicantDetails.getIsSameAddress());
			if(Value.equals("true"))
				objform.setSameAddress("true");
			else
				objform.setSameAddress("false");
			 
		}
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
		}
	
		if(empApplicantDetails.getEligibilityTestOther()!=null && !empApplicantDetails.getEligibilityTestOther().trim().isEmpty()){
			objform.setEligibilityTestOther("OTHER");
			objform.setOtherEligibilityTestValue(empApplicantDetails.getEligibilityTestOther());
		}
		if(empApplicantDetails.getIndustryFunctionalArea()!=null && !empApplicantDetails.getIndustryFunctionalArea().trim().isEmpty()){
			objform.setIndustryFunctionalArea(empApplicantDetails.getIndustryFunctionalArea());
		}
		
		if(StringUtils.isNotEmpty(empApplicantDetails.getAddressLine1()) && empApplicantDetails.getAddressLine1()!=null ){
			  objform.setAddressLine1(empApplicantDetails.getAddressLine1());
		}
		
		if(StringUtils.isNotEmpty(empApplicantDetails.getAddressLine2()) && empApplicantDetails.getAddressLine2()!=null){
			  objform.setAddressLine2(empApplicantDetails.getAddressLine2());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCity()) && empApplicantDetails.getCity()!=null){
			  objform.setCity(empApplicantDetails.getCity());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentZipCode()) && empApplicantDetails.getPermanentZipCode()!=null){
			  objform.setPermanentZipCode(empApplicantDetails.getPermanentZipCode());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentAddressLine1()) && empApplicantDetails.getCurrentAddressLine1()!=null){
			  objform.setCurrentAddressLine1(empApplicantDetails.getCurrentAddressLine1());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentAddressLine2()) && empApplicantDetails.getCurrentAddressLine2()!=null){
			  objform.setCurrentAddressLine2(empApplicantDetails.getCurrentAddressLine2());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentCity()) && empApplicantDetails.getCurrentCity()!=null){
			  objform.setCurrentCity(empApplicantDetails.getCurrentCity());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentCity()) && empApplicantDetails.getCurrentCity()!=null){
			  objform.setCurrentCity(empApplicantDetails.getCurrentCity());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentStateOther()) && empApplicantDetails.getCurrentStateOther()!=null){
			  objform.setCurrentStateOthers(empApplicantDetails.getCurrentStateOther());
		}
		
		if (StringUtils.isNotEmpty(empApplicantDetails.getPermanentStateOther()) && empApplicantDetails.getPermanentStateOther() != null) {
			objform.setOtherPermanentState(empApplicantDetails.getPermanentStateOther());
		}
		if (empApplicantDetails.getCurrentCountry() != null	&& empApplicantDetails.getCurrentCountry().getId() > 0) {
			objform.setCurrentCountryId(String.valueOf(empApplicantDetails.getCurrentCountry().getId()));
		}
		if (empApplicantDetails.getCountry() != null && empApplicantDetails.getCountry().getId() > 0) {
			objform.setCountryId(String.valueOf(empApplicantDetails.getCountry().getId()));
		}
		
		if (empApplicantDetails.getCurrentState() != null	&& empApplicantDetails.getCurrentState().getId() > 0) {
			objform.setCurrentState(String.valueOf(empApplicantDetails.getCurrentState().getId()));
		}
		if (empApplicantDetails.getState() != null && empApplicantDetails.getState().getId() > 0) {
			objform.setStateId(String.valueOf(empApplicantDetails.getState().getId()));
		}
		if(empApplicantDetails.getPhNo1()!=null && !empApplicantDetails.getPhNo1().isEmpty() ){
			objform.setHomePhone1(empApplicantDetails.getPhNo1());
		}
		
		if(empApplicantDetails.getPhNo2()!=null && !empApplicantDetails.getPhNo2().isEmpty()){
			objform.setHomePhone2(empApplicantDetails.getPhNo2());
		}
		
		if(empApplicantDetails.getPhNo3()!=null && !empApplicantDetails.getPhNo3().isEmpty()){
			objform.setHomePhone3(empApplicantDetails.getPhNo3());
		}
		if(empApplicantDetails.getWorkPhNo1()!=null && !empApplicantDetails.getWorkPhNo1().isEmpty()){
			objform.setWorkPhNo1(empApplicantDetails.getWorkPhNo1());
		}
		
		if(empApplicantDetails.getWorkPhNo2()!=null && !empApplicantDetails.getWorkPhNo2().isEmpty()){
			objform.setWorkPhNo2(empApplicantDetails.getWorkPhNo2());
		}
		
		if(empApplicantDetails.getWorkPhNo3()!=null && !empApplicantDetails.getWorkPhNo3().isEmpty()){
			objform.setWorkPhNo3(empApplicantDetails.getWorkPhNo3());
		}

		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentDesignation()) && empApplicantDetails.getCurrentDesignation()!=null){
			  objform.setDesignation(empApplicantDetails.getCurrentDesignation());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentOrganization()) && empApplicantDetails.getCurrentOrganization()!=null){
			  objform.setOrgAddress(empApplicantDetails.getCurrentOrganization());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getMobileNo1()) && empApplicantDetails.getMobileNo1()!=null){
			  objform.setMobileNo1(empApplicantDetails.getMobileNo1());
		}
		
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentStateOther()) && empApplicantDetails.getCurrentStateOther()!=null){
			  objform.setOtherCurrentState(empApplicantDetails.getCurrentStateOther());
	    }
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentZipCode()) && empApplicantDetails.getCurrentZipCode()!=null){
			  objform.setCurrentZipCode(empApplicantDetails.getCurrentZipCode());
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentDesignation())){
			  objform.setDesignation(empApplicantDetails.getCurrentDesignation());
		}
		if(empApplicantDetails.getDesignation()!=null && empApplicantDetails.getDesignation().getId()>0){
			  objform.setDesignation(empApplicantDetails.getDesignation().getName());
		}
		if(empApplicantDetails.getState()!=null && empApplicantDetails.getState().getId()>0){
			  objform.setStateId(String.valueOf(empApplicantDetails.getState().getId()));
		}
		if(empApplicantDetails.getCurrentState()!=null && empApplicantDetails.getCurrentState().getId()>0){
			  objform.setCurrentState(String.valueOf(empApplicantDetails.getCurrentState().getId()));
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getEmail()) && empApplicantDetails.getEmail()!=null){
			  objform.setEmail(empApplicantDetails.getEmail());
		}
		if(empApplicantDetails.getEmpQualificationLevel()!=null && empApplicantDetails.getEmpQualificationLevel().getId()>0){
			  objform.setQualificationId(String.valueOf(empApplicantDetails.getEmpQualificationLevel().getId()));
		}
		if(empApplicantDetails.getEmpSubjectArea()!=null && empApplicantDetails.getEmpSubjectArea().getId()>0){
			  objform.setEmpSubjectAreaId(String.valueOf(empApplicantDetails.getEmpSubjectArea().getId()));
		}
		if(empApplicantDetails.getGender()!=null){
			  objform.setGender(empApplicantDetails.getGender());
		}
		if(empApplicantDetails.getBooks()!=null && empApplicantDetails.getBooks()>0){
			  objform.setBooks(String.valueOf(empApplicantDetails.getBooks()));
		}
		if(empApplicantDetails.getNoOfPublicationsNotRefered()!=null && empApplicantDetails.getNoOfPublicationsNotRefered()>0){
			  objform.setNoOfPublicationsNotRefered(String.valueOf(empApplicantDetails.getNoOfPublicationsNotRefered()));
		}
		if(empApplicantDetails.getNoOfPublicationsRefered()!=null && empApplicantDetails.getNoOfPublicationsRefered()>0){
			  objform.setNoOfPublicationsRefered(String.valueOf(empApplicantDetails.getNoOfPublicationsRefered()));
		}
		if(StringUtils.isNotEmpty(empApplicantDetails.getName()) && empApplicantDetails.getName()!=null){
			  objform.setName(empApplicantDetails.getName().toUpperCase());
		}
		if(empApplicantDetails.getNationality()!=null && empApplicantDetails.getNationality().getId()>0){
			  objform.setNationalityId(String.valueOf(empApplicantDetails.getNationality().getId()));
		}
		
		if(StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus()) && empApplicantDetails.getMaritalStatus()!=null){
			  objform.setMaritalStatus(String.valueOf(empApplicantDetails.getMaritalStatus()));
		}
		if(empApplicantDetails.getDateOfBirth()!=null){
			objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getDateOfBirth().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
			
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
		if(empApplicantDetails.getTotalExpMonths()!=null && empApplicantDetails.getTotalExpMonths()>0){
			  objform.setExpMonths(String.valueOf(empApplicantDetails.getTotalExpMonths()));
		}
		if(empApplicantDetails.getTotalExpYear()!=null && empApplicantDetails.getTotalExpYear()>0){
			  objform.setExpYears(String.valueOf(empApplicantDetails.getTotalExpYear()));
		}
		
		if(empApplicantDetails.getBloodGroup()!=null && !empApplicantDetails.getBloodGroup().isEmpty()){
			objform.setBloodGroup(empApplicantDetails.getBloodGroup());
		}
		if(empApplicantDetails.getReligion()!=null && empApplicantDetails.getReligion().getId()>0){
			objform.setReligionId(String.valueOf(empApplicantDetails.getReligion().getId()));
		}
		if(empApplicantDetails.getEducationalDetailsSet()!=null){
			List<EmpQualificationLevelTo> fixed=null;
			if(objform.getEmployeeInfoTONew()!=null){
				if(objform.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
					fixed=objform.getEmployeeInfoTONew().getEmpQualificationFixedTo();
				}
				List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
				Set<EmpOnlineEducationalDetails> empOnlineEducationalDetailsSet=empApplicantDetails.getEducationalDetailsSet();
				Iterator<EmpOnlineEducationalDetails> iterator=empOnlineEducationalDetailsSet.iterator();
				while(iterator.hasNext()){
					EmpOnlineEducationalDetails empOnlineEducationalDetails=iterator.next();
					if(empOnlineEducationalDetails!=null){
						boolean flag=false;
						if(empOnlineEducationalDetails.getEmpQualificationLevel()!=null 
								&& empOnlineEducationalDetails.getEmpQualificationLevel().isFixedDisplay()!=null){
							flag=empOnlineEducationalDetails.getEmpQualificationLevel().isFixedDisplay();
							if(flag && fixed!=null){
								Iterator<EmpQualificationLevelTo> iterator2=fixed.iterator();
								while(iterator2.hasNext()){
									EmpQualificationLevelTo empQualificationLevelTo=iterator2.next();
									if(empQualificationLevelTo!=null && StringUtils.isNotEmpty(empQualificationLevelTo.getEducationId())){
										if(empOnlineEducationalDetails.getEmpQualificationLevel().getId()>0)
											if(empQualificationLevelTo.getEducationId().equalsIgnoreCase(String.valueOf(empOnlineEducationalDetails.getEmpQualificationLevel().getId()))){
												if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getCourse())){
													empQualificationLevelTo.setCourse(empOnlineEducationalDetails.getCourse());
												}
												
												if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getSpecialization())){
													empQualificationLevelTo.setSpecialization(empOnlineEducationalDetails.getSpecialization());
												}
												
												if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getGrade())){
													empQualificationLevelTo.setGrade(empOnlineEducationalDetails.getGrade());
												}
												
												if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getInstitute())){
													empQualificationLevelTo.setInstitute(empOnlineEducationalDetails.getInstitute());
												}
												
												if(empOnlineEducationalDetails.getYearOfCompletion()>0){
													empQualificationLevelTo.setYearOfComp(String.valueOf(empOnlineEducationalDetails.getYearOfCompletion()));
												}
											}
									}
								}
							}else{
								EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
									if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getCourse())){
										empQualificationLevelTo.setCourse(empOnlineEducationalDetails.getCourse());
									}
									
									if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getSpecialization())){
										empQualificationLevelTo.setSpecialization(empOnlineEducationalDetails.getSpecialization());
									}
									
									if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getGrade())){
										empQualificationLevelTo.setGrade(empOnlineEducationalDetails.getGrade());
									}
									
									if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getInstitute())){
										empQualificationLevelTo.setInstitute(empOnlineEducationalDetails.getInstitute());
									}
									
									if(empOnlineEducationalDetails.getYearOfCompletion()>0){
										empQualificationLevelTo.setYear(String.valueOf(empOnlineEducationalDetails.getYearOfCompletion()));
									}
									if(empOnlineEducationalDetails.getEmpQualificationLevel().getId()>0){
										empQualificationLevelTo.setEducationId(String.valueOf(empOnlineEducationalDetails.getEmpQualificationLevel().getId()));
									}
								level.add(empQualificationLevelTo);
							}
								
							}
						}
				}
				objform.getEmployeeInfoTONew().setEmpQualificationLevelTos(level);
			}
		}
	}
	//Code for photo display
	if (empApplicantDetails.getEmpPhoto() != null && empApplicantDetails.getEmpPhoto().length > 0) {
			byte[] myFileBytes = empApplicantDetails.getEmpPhoto();
			objform.setPhotoBytes(myFileBytes);
		}
		
	if(empApplicantDetails.getPreviousExpSet()!=null){
		int teachingFlag=0;
		int industryFlag=0;
		Set<EmpOnlinePreviousExperience> empOnlinePreviousExperiencesSet=empApplicantDetails.getPreviousExpSet();
		if(empOnlinePreviousExperiencesSet != null && !empOnlinePreviousExperiencesSet.isEmpty())
		{
		Iterator<EmpOnlinePreviousExperience> iterator=empOnlinePreviousExperiencesSet.iterator();
		List<GuestPreviousExperienceTO> industryExp=new ArrayList<GuestPreviousExperienceTO>();
		List<GuestPreviousExperienceTO> teachingExp=new ArrayList<GuestPreviousExperienceTO>();
		while(iterator.hasNext()){
			EmpOnlinePreviousExperience empOnlinePreviousExperiences=iterator.next();
			if(empOnlinePreviousExperiences!=null){
				GuestPreviousExperienceTO empOnliPreviousExperienceTO=new GuestPreviousExperienceTO();
			if(empOnlinePreviousExperiences.isIndustryExperience())
			{
				if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
					empOnliPreviousExperienceTO.setCurrentDesignation(empOnlinePreviousExperiences.getEmpDesignation());
				}
				
				if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
					empOnliPreviousExperienceTO.setCurrentOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
				}
				
				if( empOnlinePreviousExperiences.getExpMonths()>0 ){
					empOnliPreviousExperienceTO.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
				}
				
				if(empOnlinePreviousExperiences.getExpYears()>0){
					empOnliPreviousExperienceTO.setIndustryExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
				}
				industryFlag=1;
				industryExp.add(empOnliPreviousExperienceTO);
			}else if(empOnlinePreviousExperiences.isTeachingExperience())
				{
					if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
						empOnliPreviousExperienceTO.setCurrentTeachnigDesignation(empOnlinePreviousExperiences.getEmpDesignation());
					}
					
					if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
						empOnliPreviousExperienceTO.setCurrentTeachingOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
					}
					
					if( empOnlinePreviousExperiences.getExpMonths()>0 ){
						empOnliPreviousExperienceTO.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
					}
					
					if(empOnlinePreviousExperiences.getExpYears()>0){
						empOnliPreviousExperienceTO.setTeachingExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
					}
					teachingFlag=1;
					teachingExp.add(empOnliPreviousExperienceTO);
				}
		}
	}
		if(objform.getEmployeeInfoTONew()!=null){
			if(industryExp!=null)
			objform.getEmployeeInfoTONew().setExperiences(industryExp);
			if(teachingExp!=null)
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
	}
	
	
}
		else {
			List<GuestPreviousExperienceTO> list=new ArrayList<GuestPreviousExperienceTO>();
			GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setIndustryExpYears("");
			empPreviousOrgTo.setIndustryExpMonths("");
			empPreviousOrgTo.setCurrentDesignation("");
			empPreviousOrgTo.setCurrentOrganisation("");
			objform.setIndustryExpLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			
			List<GuestPreviousExperienceTO> teachingList=new ArrayList<GuestPreviousExperienceTO>();
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setTeachingExpYears("");
			empPreviousOrgTo.setTeachingExpMonths("");
			empPreviousOrgTo.setCurrentTeachingOrganisation("");
			empPreviousOrgTo.setCurrentTeachnigDesignation("");
			objform.setTeachingExpLength(String.valueOf(teachingList.size()));
			teachingList.add(empPreviousOrgTo);
			objform.getEmployeeInfoTONew().setExperiences(list);
			objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
		}
		if(teachingFlag==1 && industryFlag==0)
		{
			List<GuestPreviousExperienceTO> list=new ArrayList<GuestPreviousExperienceTO>();
			GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setIndustryExpYears("");
			empPreviousOrgTo.setIndustryExpMonths("");
			empPreviousOrgTo.setCurrentDesignation("");
			empPreviousOrgTo.setCurrentOrganisation("");
			objform.setIndustryExpLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			objform.getEmployeeInfoTONew().setExperiences(list);
		}
		if(teachingFlag==0 && industryFlag==1)
		{
			List<GuestPreviousExperienceTO> teachingList=new ArrayList<GuestPreviousExperienceTO>();
			GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setTeachingExpYears("");
			empPreviousOrgTo.setTeachingExpMonths("");
			empPreviousOrgTo.setCurrentTeachingOrganisation("");
			empPreviousOrgTo.setCurrentTeachnigDesignation("");
			objform.setTeachingExpLength(String.valueOf(teachingList.size()));
			teachingList.add(empPreviousOrgTo);
			objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
		}
		if(teachingFlag==1 && industryFlag==0)
		{
			List<GuestPreviousExperienceTO> list=new ArrayList<GuestPreviousExperienceTO>();
			GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setIndustryExpYears("");
			empPreviousOrgTo.setIndustryExpMonths("");
			empPreviousOrgTo.setCurrentDesignation("");
			empPreviousOrgTo.setCurrentOrganisation("");
			objform.setIndustryExpLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			objform.getEmployeeInfoTONew().setExperiences(list);
		}
		if(teachingFlag==0 && industryFlag==0)
		{
			List<GuestPreviousExperienceTO> teachingList=new ArrayList<GuestPreviousExperienceTO>();
			GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setTeachingExpYears("");
			empPreviousOrgTo.setTeachingExpMonths("");
			empPreviousOrgTo.setCurrentTeachingOrganisation("");
			empPreviousOrgTo.setCurrentTeachnigDesignation("");
			objform.setTeachingExpLength(String.valueOf(teachingList.size()));
			teachingList.add(empPreviousOrgTo);
			objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
			
			List<GuestPreviousExperienceTO> list=new ArrayList<GuestPreviousExperienceTO>();
			
			empPreviousOrgTo.setId(1);
			empPreviousOrgTo.setIndustryExpYears("");
			empPreviousOrgTo.setIndustryExpMonths("");
			empPreviousOrgTo.setCurrentDesignation("");
			empPreviousOrgTo.setCurrentOrganisation("");
			objform.setIndustryExpLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			objform.getEmployeeInfoTONew().setExperiences(list);
		}
	}
	
	
}
	
	
	
	

	/**
	 * @param empApplicantDetails
	 * @param objform
	 * @throws Exception
	 */
	public void convertBoToForm(GuestFaculty empApplicantDetails,
			GuestFacultyInfoForm objform) throws Exception {
		objform.setGuestId(String.valueOf(empApplicantDetails.getId()));
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
				Iterator<GuestImages> itr=empApplicantDetails.getEmpImages().iterator();
				while (itr.hasNext()) {
					GuestImages bo =itr.next();
					
					if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
						objform.setPhotoBytes(bo.getEmpPhoto());
						objform.setEmpImageId(String.valueOf(bo.getId()));
						break;
					}
				}
				
			}
			// //...............................................................Photo.....................................................
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
					.getActive()))) {
				String Value = String.valueOf(empApplicantDetails.getActive());
				if (Value.equals("true"))
					objform.setActive("1");
				else
					objform.setActive("0");

			}
		/*	if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getIsCurrentWorkingDates()))) {
				String Value = String.valueOf(empApplicantDetails.getIsCurrentWorkingDates());
				if (Value.equals("true"))
					objform.setIsCurrentWorkingDates("1");
				else
					objform.setIsCurrentWorkingDates("0");

			}*/
			
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
			
			if (empApplicantDetails.getEmpQualificationLevel() != null
					&& empApplicantDetails.getEmpQualificationLevel().getId() > 0) {
				objform.setQualificationId(String.valueOf(empApplicantDetails
						.getEmpQualificationLevel().getId()));
			}
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
			if (empApplicantDetails.getDepartment() != null
					&& empApplicantDetails.getDepartment().getId() > 0) {
				objform.setDepartmentId(String.valueOf(empApplicantDetails
						.getDepartment().getId()));
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
			
			/*if (empApplicantDetails.getEndDate() != null
					&& !empApplicantDetails.getEndDate().toString().isEmpty()) {
				objform.setEndDate(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getEndDate().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
			}
			if (empApplicantDetails.getStartDate() != null
					&& !empApplicantDetails.getStartDate().toString().isEmpty()) {
				objform.setStartDate(CommonUtil.ConvertStringToDateFormat(
						empApplicantDetails.getStartDate().toString(), "yyyy-mm-dd",
						"dd/mm/yyyy"));
			}*/
			if (empApplicantDetails.getHonorariumPerHours() != null
					&& !empApplicantDetails.getHonorariumPerHours().isEmpty()) {
				objform.setHonorariumPerHours(String.valueOf(empApplicantDetails
						.getHonorariumPerHours()));
			}
		/*	if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails
					.getIsCurrentWorkingDates()))) {
				String Value = String.valueOf(empApplicantDetails
						.getIsCurrentWorkingDates());
				if (Value.equals("true"))
					objform.setIsCurrentWorkingDates("YES");
				else
					objform.setIsCurrentWorkingDates("NO");

			}
			if (empApplicantDetails.getSemester() != null
					&& !empApplicantDetails.getSemester().isEmpty()) {
				objform.setSemester(String.valueOf(empApplicantDetails
						.getSemester()));
			}*/
			if (empApplicantDetails.getReferredBy() != null
					&& !empApplicantDetails.getReferredBy().isEmpty()) {
				objform.setReferredBy(String.valueOf(empApplicantDetails
						.getReferredBy()));
			}
			if (empApplicantDetails.getWorkingHoursPerWeek() != null
					&& !empApplicantDetails.getWorkingHoursPerWeek().isEmpty()) {
				objform.setWorkingHoursPerWeek(String.valueOf(empApplicantDetails
						.getWorkingHoursPerWeek()));
			}
			if (empApplicantDetails.getSubjectSpecilization() != null && !empApplicantDetails.getSubjectSpecilization().isEmpty()) {
				objform.setSubjectSpecilization(empApplicantDetails.getSubjectSpecilization());
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
			

			
			if (StringUtils.isNotEmpty(empApplicantDetails.getEmContactRelationship())
					&& empApplicantDetails.getEmContactRelationship() != null) {
				objform.setEmContactRelationship(empApplicantDetails
						.getEmContactRelationship());
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

			

			if (StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail())
					&& empApplicantDetails.getWorkEmail() != null) {
				objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
			}
			if (empApplicantDetails.getWorkLocationId() != null
					&& empApplicantDetails.getWorkLocationId().getId() > 0) {
				objform.setWorkLocationId(String.valueOf(empApplicantDetails
						.getWorkLocationId().getId()));
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
					Set<GuestEducationalDetails> empEducationalDetailsSet = empApplicantDetails
							.getEducationalDetailsSet();
					Iterator<GuestEducationalDetails> iterator = empEducationalDetailsSet
							.iterator();
					while (iterator.hasNext()) {
						GuestEducationalDetails empEducationalDetails = iterator
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
		}
		//start by giri
		Map<String,String> workMap=GuestFacultyInfoImpl.getInstance().getWorkLocationMap();
		Map<String,String> streamMap=GuestFacultyInfoImpl.getInstance().getStreamMap();
		 
		//end by giri
		if (empApplicantDetails.getPreviousChristDetails() != null) {
			
			Set<GuestPreviousChristWorkDetails> PreviousExperiences = empApplicantDetails.getPreviousChristDetails();
			if (PreviousExperiences != null
					&& !PreviousExperiences.isEmpty()) {

				Iterator<GuestPreviousChristWorkDetails> iterator = PreviousExperiences
						.iterator();
				List<GuestPreviousChristWorkDetailsTO> list = new ArrayList<GuestPreviousChristWorkDetailsTO>();
				while (iterator.hasNext()) {
					GuestPreviousChristWorkDetails empPreviousExperiences = iterator
							.next();
					if (empPreviousExperiences != null) {
						GuestPreviousChristWorkDetailsTO empOnliPreviousExperienceTO = new GuestPreviousChristWorkDetailsTO();
												
							if (empPreviousExperiences.getId() > 0) {
								empOnliPreviousExperienceTO.setId(empPreviousExperiences.getId());
							}
							if (empPreviousExperiences.getIsCurrentWorkingDates()!=null) {
								empOnliPreviousExperienceTO.setIsCurrentWorkingDates(empPreviousExperiences.getIsCurrentWorkingDates());
							}
							if (empPreviousExperiences.getStartDate()!=null) {
								empOnliPreviousExperienceTO.setStartDate(CommonUtil
										.ConvertStringToDateFormat(empPreviousExperiences.getStartDate().toString(),
												"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empPreviousExperiences.getEndDate()!=null) {
								empOnliPreviousExperienceTO.setEndDate(CommonUtil
										.ConvertStringToDateFormat(empPreviousExperiences.getEndDate().toString(),
												"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							if (StringUtils
									.isNotEmpty(empPreviousExperiences.getSemester()
											)) {
								empOnliPreviousExperienceTO
										.setSemester(empPreviousExperiences
												.getSemester());
							}
							//start by giri
							if(empPreviousExperiences.getDeptId()!=null){
								empOnliPreviousExperienceTO.setDeptId(String.valueOf(empPreviousExperiences.getDeptId().getId()));
							}
							if(empPreviousExperiences.getStrmId()!=null){
								empOnliPreviousExperienceTO.setStrmId(String.valueOf(empPreviousExperiences.getStrmId().getId()));
							}
							if(empPreviousExperiences.getWorklLocId()!=null){
								empOnliPreviousExperienceTO.setWorkLocId(String.valueOf(empPreviousExperiences.getWorklLocId().getId()));
							}
							if(empPreviousExperiences.getHonorarium()!=null && !empPreviousExperiences.getHonorarium().isEmpty()){
								empOnliPreviousExperienceTO.setHonorarium(empPreviousExperiences.getHonorarium());
							}
							if(empPreviousExperiences.getWorkHoursPerWeek()!=null && !empPreviousExperiences.getWorkHoursPerWeek().isEmpty()){
								empOnliPreviousExperienceTO.setWorkHoursPerWeek(empPreviousExperiences.getWorkHoursPerWeek());
							}
							if(workMap!=null){
								empOnliPreviousExperienceTO.setWorkMap(workMap);
							 }
							if(streamMap!=null){
								empOnliPreviousExperienceTO.setStrmMap(streamMap);
							 }
							String staffId=null;
							Map<String,String> departmentMap = GuestFacultyInfoHandler.getInstance().getFilteredDepartmentsStreamNames(empOnliPreviousExperienceTO.getStrmId(),staffId);
							 if(departmentMap!=null){
								 empOnliPreviousExperienceTO.setDeptMap(departmentMap);
							 }
							//end by giri
							list.add(empOnliPreviousExperienceTO);	
						}
						
						}
					objform.getEmployeeInfoTONew().setPreviousworkDetails(list);
					}
			
				}
			
		
	
		if (empApplicantDetails.getPreviousExpSet() != null) {
			int teachingFlag = 0;
			int industryFlag = 0;
			Set<GuestPreviousExperience> empPreviousExperiencesSet = empApplicantDetails.getPreviousExpSet();
			if (empPreviousExperiencesSet != null
					&& !empPreviousExperiencesSet.isEmpty()) {

				Iterator<GuestPreviousExperience> iterator = empPreviousExperiencesSet
						.iterator();
				List<GuestPreviousExperienceTO> industryExp = new ArrayList<GuestPreviousExperienceTO>();
				List<GuestPreviousExperienceTO> teachingExp = new ArrayList<GuestPreviousExperienceTO>();
				while (iterator.hasNext()) {
					GuestPreviousExperience empPreviousExperiences = iterator
							.next();
					if (empPreviousExperiences != null) {
						GuestPreviousExperienceTO empOnliPreviousExperienceTO = new GuestPreviousExperienceTO();
						if (empPreviousExperiences.isIndustryExperience()) {
							if (empPreviousExperiences.getId() > 0) {
								empOnliPreviousExperienceTO
										.setId(empPreviousExperiences
												.getId());
							}
							if (StringUtils
									.isNotEmpty(empPreviousExperiences
											.getEmpDesignation())) {
								empOnliPreviousExperienceTO
										.setCurrentDesignation(empPreviousExperiences
												.getEmpDesignation());
							}

							if (StringUtils
									.isNotEmpty(empPreviousExperiences
											.getEmpOrganization())) {
								empOnliPreviousExperienceTO
										.setCurrentOrganisation(empPreviousExperiences
												.getEmpOrganization());
							}

							if (empPreviousExperiences.getExpMonths() > 0) {
								empOnliPreviousExperienceTO
										.setIndustryExpMonths(String
												.valueOf(empPreviousExperiences
														.getExpMonths()));
							}

							if (empPreviousExperiences.getExpYears() > 0) {
								empOnliPreviousExperienceTO
										.setIndustryExpYears(String
												.valueOf(empPreviousExperiences
														.getExpYears()));
							}
							industryFlag = 1;
							industryExp.add(empOnliPreviousExperienceTO);
						} else if (empPreviousExperiences
								.isTeachingExperience()) {
							if (empPreviousExperiences.getId() > 0) {
								empOnliPreviousExperienceTO
										.setId(empPreviousExperiences
												.getId());
							}
							if (StringUtils
									.isNotEmpty(empPreviousExperiences
											.getEmpDesignation())) {
								empOnliPreviousExperienceTO
										.setCurrentTeachnigDesignation(empPreviousExperiences
												.getEmpDesignation());
							}

							if (StringUtils
									.isNotEmpty(empPreviousExperiences
											.getEmpOrganization())) {
								empOnliPreviousExperienceTO
										.setCurrentTeachingOrganisation(empPreviousExperiences
												.getEmpOrganization());
							}

							if (empPreviousExperiences.getExpMonths() > 0) {
								empOnliPreviousExperienceTO
										.setTeachingExpMonths(String
												.valueOf(empPreviousExperiences
														.getExpMonths()));
							}

							if (empPreviousExperiences.getExpYears() > 0) {
								empOnliPreviousExperienceTO
										.setTeachingExpYears(String
												.valueOf(empPreviousExperiences
														.getExpYears()));
							}
							teachingFlag = 1;
							teachingExp.add(empOnliPreviousExperienceTO);

						}

					}
				}
				GuestPreviousExperienceTO empPreviousOrgTo = new GuestPreviousExperienceTO();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(industryExp.size()));
				industryExp.add(empPreviousOrgTo);
				
				GuestPreviousExperienceTO empPreviousOrgTo1 = new GuestPreviousExperienceTO();
				empPreviousOrgTo1.setIfEmpty("2");
				empPreviousOrgTo1.setIndustryExpYears("");
				empPreviousOrgTo1.setIndustryExpMonths("");
				empPreviousOrgTo1.setCurrentDesignation("");
				empPreviousOrgTo1.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(industryExp.size()));
				industryExp.add(empPreviousOrgTo1);
				
				objform.getEmployeeInfoTONew().setExperiences(industryExp);
				
				GuestPreviousExperienceTO empPreviousOrgTo6 = new GuestPreviousExperienceTO();
				empPreviousOrgTo6.setIfEmpty("4");
				empPreviousOrgTo6.setTeachingExpYears("");
				empPreviousOrgTo6.setTeachingExpMonths("");
				empPreviousOrgTo6.setCurrentTeachingOrganisation("");
				empPreviousOrgTo6.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingExp.size()));
				teachingExp.add(empPreviousOrgTo6);
				
				GuestPreviousExperienceTO empPreviousOrgTo7 = new GuestPreviousExperienceTO();
				empPreviousOrgTo7.setIfEmpty("5");
				empPreviousOrgTo7.setTeachingExpYears("");
				empPreviousOrgTo7.setTeachingExpMonths("");
				empPreviousOrgTo7.setCurrentTeachingOrganisation("");
				empPreviousOrgTo7.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingExp.size()));
				teachingExp.add(empPreviousOrgTo7);
				
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
			} else {
				List<GuestPreviousExperienceTO> list = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo = new GuestPreviousExperienceTO();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				GuestPreviousExperienceTO empPreviousOrgTo1 = new GuestPreviousExperienceTO();
				empPreviousOrgTo1.setIfEmpty("2");
				empPreviousOrgTo1.setIndustryExpYears("");
				empPreviousOrgTo1.setIndustryExpMonths("");
				empPreviousOrgTo1.setCurrentDesignation("");
				empPreviousOrgTo1.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo1);
				
				GuestPreviousExperienceTO empPreviousOrgTo2 = new GuestPreviousExperienceTO();
				empPreviousOrgTo2.setIfEmpty("3");
				empPreviousOrgTo2.setIndustryExpYears("");
				empPreviousOrgTo2.setIndustryExpMonths("");
				empPreviousOrgTo2.setCurrentDesignation("");
				empPreviousOrgTo2.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo2);
				
				/*GuestPreviousExperienceTO empPreviousOrgTo3 = new GuestPreviousExperienceTO();
				empPreviousOrgTo3.setIfEmpty("4");
				list.add(empPreviousOrgTo3);*/

				List<GuestPreviousExperienceTO> teachingList = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo4 = new GuestPreviousExperienceTO();
				empPreviousOrgTo4.setIfEmpty("1");
				empPreviousOrgTo4.setTeachingExpYears("");
				empPreviousOrgTo4.setTeachingExpMonths("");
				empPreviousOrgTo4.setCurrentTeachingOrganisation("");
				empPreviousOrgTo4.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo4);
				
				GuestPreviousExperienceTO empPreviousOrgTo8 = new GuestPreviousExperienceTO();
				empPreviousOrgTo8.setIfEmpty("2");
				empPreviousOrgTo8.setTeachingExpYears("");
				empPreviousOrgTo8.setTeachingExpMonths("");
				empPreviousOrgTo8.setCurrentTeachingOrganisation("");
				empPreviousOrgTo8.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo8);
				
				GuestPreviousExperienceTO empPreviousOrgTo5 = new GuestPreviousExperienceTO();
				empPreviousOrgTo5.setIfEmpty("3");
				empPreviousOrgTo5.setTeachingExpYears("");
				empPreviousOrgTo5.setTeachingExpMonths("");
				empPreviousOrgTo5.setCurrentTeachingOrganisation("");
				empPreviousOrgTo5.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo5);
				
				GuestPreviousExperienceTO empPreviousOrgTo6 = new GuestPreviousExperienceTO();
				empPreviousOrgTo6.setIfEmpty("4");
				empPreviousOrgTo6.setTeachingExpYears("");
				empPreviousOrgTo6.setTeachingExpMonths("");
				empPreviousOrgTo6.setCurrentTeachingOrganisation("");
				empPreviousOrgTo6.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo6);
				
				GuestPreviousExperienceTO empPreviousOrgTo7 = new GuestPreviousExperienceTO();
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
				List<GuestPreviousExperienceTO> list = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo = new GuestPreviousExperienceTO();
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
				List<GuestPreviousExperienceTO> teachingList = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo = new GuestPreviousExperienceTO();
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
				List<GuestPreviousExperienceTO> list = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo = new GuestPreviousExperienceTO();
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
				List<GuestPreviousExperienceTO> teachingList = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo = new GuestPreviousExperienceTO();
				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String
						.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				
				
			

				//List<GuestPreviousExperienceTO> teachingList = new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo4 = new GuestPreviousExperienceTO();
				empPreviousOrgTo4.setIfEmpty("1");
				empPreviousOrgTo4.setTeachingExpYears("");
				empPreviousOrgTo4.setTeachingExpMonths("");
				empPreviousOrgTo4.setCurrentTeachingOrganisation("");
				empPreviousOrgTo4.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo4);
				
				GuestPreviousExperienceTO empPreviousOrgTo8 = new GuestPreviousExperienceTO();
				empPreviousOrgTo8.setIfEmpty("2");
				empPreviousOrgTo8.setTeachingExpYears("");
				empPreviousOrgTo8.setTeachingExpMonths("");
				empPreviousOrgTo8.setCurrentTeachingOrganisation("");
				empPreviousOrgTo8.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo8);
				
				GuestPreviousExperienceTO empPreviousOrgTo5 = new GuestPreviousExperienceTO();
				empPreviousOrgTo5.setIfEmpty("3");
				empPreviousOrgTo5.setTeachingExpYears("");
				empPreviousOrgTo5.setTeachingExpMonths("");
				empPreviousOrgTo5.setCurrentTeachingOrganisation("");
				empPreviousOrgTo5.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo5);
				
				GuestPreviousExperienceTO empPreviousOrgTo6 = new GuestPreviousExperienceTO();
				empPreviousOrgTo6.setIfEmpty("4");
				empPreviousOrgTo6.setTeachingExpYears("");
				empPreviousOrgTo6.setTeachingExpMonths("");
				empPreviousOrgTo6.setCurrentTeachingOrganisation("");
				empPreviousOrgTo6.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo6);
				
				/*GuestPreviousExperienceTO empPreviousOrgTo7 = new GuestPreviousExperienceTO();
				empPreviousOrgTo7.setIfEmpty("5");
				empPreviousOrgTo7.setTeachingExpYears("");
				empPreviousOrgTo7.setTeachingExpMonths("");
				empPreviousOrgTo7.setCurrentTeachingOrganisation("");
				empPreviousOrgTo7.setCurrentTeachnigDesignation("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo7);*/
				
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);

				List<GuestPreviousExperienceTO> list = new ArrayList<GuestPreviousExperienceTO>();

				empPreviousOrgTo.setIfEmpty("1");
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				GuestPreviousExperienceTO empPreviousOrgTo1 = new GuestPreviousExperienceTO();
				empPreviousOrgTo1.setIfEmpty("2");
				empPreviousOrgTo1.setIndustryExpYears("");
				empPreviousOrgTo1.setIndustryExpMonths("");
				empPreviousOrgTo1.setCurrentDesignation("");
				empPreviousOrgTo1.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo1);
				
				GuestPreviousExperienceTO empPreviousOrgTo2 = new GuestPreviousExperienceTO();
				empPreviousOrgTo2.setIfEmpty("3");
				empPreviousOrgTo2.setIndustryExpYears("");
				empPreviousOrgTo2.setIndustryExpMonths("");
				empPreviousOrgTo2.setCurrentDesignation("");
				empPreviousOrgTo2.setCurrentOrganisation("");
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo2);
				
				/*GuestPreviousExperienceTO empPreviousOrgTo3 = new GuestPreviousExperienceTO();
				empPreviousOrgTo3.setIfEmpty("4");
				list.add(empPreviousOrgTo3);*/
				
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
		}
		if (empApplicantDetails.getEmpImages() != null && !empApplicantDetails.getEmpImages().isEmpty()) {
			Set<GuestImages> empImages = empApplicantDetails.getEmpImages();
			if (empImages != null && !empImages.isEmpty()) {
				Iterator<GuestImages> iterator = empImages.iterator();
				List<GuestImagesTO> empImagesTOs = new ArrayList<GuestImagesTO>();

				while (iterator.hasNext()) {
					GuestImages eImages = iterator.next();
					if (eImages != null) {
						GuestImagesTO eImagesTO = new GuestImagesTO();
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
		if(empApplicantDetails.getStaffId()!=null && !empApplicantDetails.getStaffId().isEmpty()){
			objform.setStaffId(empApplicantDetails.getStaffId());
			objform.setPreviousStaffId(empApplicantDetails.getStaffId());
		}
		if(empApplicantDetails.getHighQualifForWebsite()!=null && !empApplicantDetails.getHighQualifForWebsite().isEmpty()){
			objform.setHighQualifForWebsite(empApplicantDetails.getHighQualifForWebsite());
		}
		if (StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getDisplayInWebsite()))) {
			String ValueNew = String.valueOf(empApplicantDetails.getDisplayInWebsite());
			if (ValueNew.equals("true"))
				objform.setDisplayInWebsite("1");
			else
				objform.setDisplayInWebsite("0");

		}
		if(empApplicantDetails.getBankBranch()!=null && !empApplicantDetails.getBankBranch().isEmpty()){
			objform.setBankBranch(empApplicantDetails.getBankBranch());
		}
		if(empApplicantDetails.getBankIfscCode()!=null && !empApplicantDetails.getBankIfscCode().isEmpty()){
			objform.setBankIfscCode(empApplicantDetails.getBankIfscCode());
		}
		
	}

	public List<GuestFacultyTO> convertEmployeeTOtoBO(List<GuestFaculty> employeelist,
			int departmentId, int designationId) throws Exception {

		List<Department> departmentList = txn.getEmployeeDepartment();
		List<Designation> designationList = txn.getEmployeeDesignation();

		List<GuestFacultyTO> employeeTos = new ArrayList<GuestFacultyTO>();
		//String name = "";
		if (employeelist != null) {
			Iterator<GuestFaculty> stItr = employeelist.iterator();
			while (stItr.hasNext()) {
				//name = "";
				GuestFaculty emp = (GuestFaculty) stItr.next();
				GuestFacultyTO empTo = new GuestFacultyTO();
				if (emp.getId() > 0) {
					empTo.setId(emp.getId());
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
					
					Iterator<GuestImages> itr=emp.getEmpImages().iterator();
					while (itr.hasNext()) {
						GuestImages bo =itr.next();
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

	
	
	public List<GuestFacultyTO> convertBoToToForPrint(GuestFaculty guest, List<GuestPreviousExperience> teachingExperience, GuestFacultyInfoForm guestForm,HttpSession session
	) throws Exception{
	
	
	
	List<GuestPreviousExperienceTO> teachingExpTos = new ArrayList<GuestPreviousExperienceTO>();
	List<GuestPreviousExperienceTO> industryExpTos = new ArrayList<GuestPreviousExperienceTO>();
	if(teachingExperience != null && !teachingExperience.isEmpty()){
		Iterator<GuestPreviousExperience>  iterator = teachingExperience.iterator();
		while (iterator.hasNext()) {
			GuestPreviousExperience guestPrevExp = (GuestPreviousExperience) iterator.next();
			GuestPreviousExperienceTO expTo = new GuestPreviousExperienceTO();
			if(guestPrevExp.isTeachingExperience()){
				expTo.setCurrentTeachnigDesignation(guestPrevExp.getEmpDesignation());
				expTo.setCurrentTeachingOrganisation(guestPrevExp.getEmpOrganization());
				
				expTo.setTeachingExpYears(String.valueOf(guestPrevExp.getExpYears()));
				expTo.setTeachingExpMonths(String.valueOf(guestPrevExp.getExpMonths()));
				
			}
			if(guestPrevExp.isIndustryExperience()){
				expTo.setCurrentDesignation(guestPrevExp.getEmpDesignation());
				expTo.setCurrentOrganisation(guestPrevExp.getEmpOrganization());
				
				expTo.setIndustryExpYears(String.valueOf(guestPrevExp.getExpYears()));
				expTo.setIndustryExpMonths(String.valueOf(guestPrevExp.getExpMonths()));
				
			}
			teachingExpTos.add(expTo);
			industryExpTos.add(expTo);
		}
		guestForm.setTeachingExperience(teachingExpTos);
		guestForm.setIndustryExperience(industryExpTos);
	}
	
	List<GuestFacultyTO> empResumeList = new ArrayList<GuestFacultyTO>();
	GuestFacultyTO to = new GuestFacultyTO();
	
	if(teachingExperience != null && !teachingExperience.isEmpty()){
		Iterator<GuestPreviousExperience>  iterator = teachingExperience.iterator();
		int teachingExpYears = 0;
		int teachingExpMonths = 0;
		int industryExpYears = 0;
		int industryExpMonths = 0;
		while (iterator.hasNext()) {
			GuestPreviousExperience guestPrevExp = (GuestPreviousExperience) iterator.next();
			
			if(guestPrevExp.isTeachingExperience()){
				
				teachingExpYears =  teachingExpYears + guestPrevExp.getExpYears();
				teachingExpMonths = teachingExpMonths + guestPrevExp.getExpMonths();
			}
			if(guestPrevExp.isIndustryExperience()){
				industryExpYears =  industryExpYears + guestPrevExp.getExpYears();
				industryExpMonths = industryExpMonths + guestPrevExp.getExpMonths();
			}
		}
		
		if(teachingExpMonths >=12){
			int result = teachingExpMonths%12;
			int result1 = teachingExpMonths/12;
			teachingExpYears = teachingExpYears +result1;
			teachingExpMonths = result;
		}
		if(industryExpMonths >=12){
			int result = industryExpMonths%12;
			int result1 = industryExpMonths/12;
			industryExpYears = industryExpYears +result1;
			industryExpMonths = result;
		}
		String teachingExp = "";
		teachingExp = teachingExp + String.valueOf(teachingExpYears) + " Year(s) " + String.valueOf(teachingExpMonths) + " Month(s)";
		to.setTotalTeachingExperience(teachingExp);
		String industryExp = "";
		industryExp = industryExp + String.valueOf(industryExpYears) + " Year(s) " + String.valueOf(industryExpMonths) + " Month(s)";
		to.setIndustryExperience(industryExp);
	}
	
	
	if(guest != null ){
		
		if(guest.getTotalExpYear() != null && guest.getTotalExpMonths() != null){
			String totalExp = guest.getTotalExpYear() + " Year(s) " + guest.getTotalExpMonths() +" Month(s)";
			to.setTotalExp(totalExp);
		}
		if(guest.getFirstName() != null && !guest.getFirstName().isEmpty()){
			to.setFirstName(guest.getFirstName().toUpperCase());
		}
		if(guest.getHonorariumPerHours() != null && !guest.getHonorariumPerHours().isEmpty()){
			to.setHonorariumPerHours(guest.getHonorariumPerHours());
		}
		if(guest.getReferredBy() != null && !guest.getReferredBy().isEmpty()){
			to.setReferredBy(guest.getReferredBy());
		}
		
		if(guest.getWorkingHoursPerWeek() != null && !guest.getWorkingHoursPerWeek().isEmpty()){
			to.setWorkingHoursPerWeek(guest.getWorkingHoursPerWeek());
		}
		if(guest.getDepartment() != null ){
			if(guest.getDepartment().getName() != null && !guest.getDepartment().getName().isEmpty()){
				to.setDepartmentName(guest.getDepartment().getName());
			}
		}
		if(guest.getDesignation() != null ){
			if(guest.getDesignation().getName() != null && !guest.getDesignation().getName().isEmpty()){
				to.setDesignationName(guest.getDesignation().getName());
			}
		}
		if(guest.getGender() != null && !guest.getGender().isEmpty()){
			to.setGender(guest.getGender());
		}
		if(guest.getPermanentAddressLine1() != null && !guest.getPermanentAddressLine1().isEmpty()){
			to.setPermanentAddressLine1(guest.getPermanentAddressLine1());
		}
		if(guest.getPermanentAddressLine2() != null && !guest.getPermanentAddressLine2().isEmpty()){
			to.setPermanentAddressLine2(guest.getPermanentAddressLine2());
		}
		
		if(guest.getCommunicationAddressLine1() != null && !guest.getCommunicationAddressLine1().isEmpty()){
			to.setCommunicationAddressLine1(guest.getCommunicationAddressLine1());
		}
		if(guest.getCommunicationAddressLine2() != null && !guest.getCommunicationAddressLine2().isEmpty()){
			to.setCommunicationAddressLine2(guest.getCommunicationAddressLine2());
		}
		if(guest.getStateByCommunicationAddressStateId() != null && guest.getStateByCommunicationAddressStateId().getId()>0){
			to.setCommunicationStateName(guest.getStateByCommunicationAddressStateId().getName());
		}
		if(guest.getStateByPermanentAddressStateId() != null && guest.getStateByPermanentAddressStateId().getId()>0){
			to.setPermanentStateName(guest.getStateByPermanentAddressStateId().getName());
		}
		if(guest.getCountryByCommunicationAddressCountryId() != null && guest.getCountryByCommunicationAddressCountryId().getId()>0){
			to.setCommunicationCountryName(guest.getCountryByCommunicationAddressCountryId().getName());
		}
		if(guest.getCountryByPermanentAddressCountryId() != null && guest.getCountryByPermanentAddressCountryId().getId()>0){
			to.setPermanentCountryName(guest.getCountryByPermanentAddressCountryId().getName());
		}
		if(guest.getCommunicationAddressCity() != null && !guest.getCommunicationAddressCity().isEmpty()){
			to.setCommunicationAddressCity(guest.getCommunicationAddressCity());
		}
		if(guest.getPermanentAddressCity() != null && !guest.getPermanentAddressCity().isEmpty()){
			to.setPermanentAddressCity(guest.getPermanentAddressCity());
		}
		if(guest.getNationality() != null ){
			if(guest.getNationality().getName() != null && !guest.getNationality().getName().isEmpty()){
				to.setNationalityName(guest.getNationality().getName() );
			}
		}
		if(guest.getCommunicationAddressCity() != null && !guest.getCommunicationAddressCity().isEmpty()){
			to.setCommunicationAddressCity(guest.getCommunicationAddressCity());
		}
		if(guest.getMaritalStatus() != null && !guest.getMaritalStatus().isEmpty()){
			to.setMaritalStatus(guest.getMaritalStatus());
		}
		if(guest.getEmail() != null && !guest.getEmail().isEmpty()){
			to.setEmail(guest.getEmail());
		}
		if(guest.getPanNo() != null && !guest.getPanNo().isEmpty()){
			to.setPanNo(guest.getPanNo());
		}
		if(guest.getDepartment() != null && guest.getDepartment().getId()>0){
			to.setDepartmentName(guest.getDepartment().getName());
		}
		if(guest.getDob()!= null){
			to.setDob(CommonUtil.getStringDate(guest.getDob()));
		}
		if(guest.getReservationCategory() != null && !guest.getReservationCategory().isEmpty()){
			to.setReservationCatagory(guest.getReservationCategory());
		}
		if(guest.getCurrentAddressHomeTelephone3() != null && !guest.getCurrentAddressHomeTelephone3().isEmpty()){
			to.setCurrentAddressHomeTelephone3("+91"+guest.getCurrentAddressHomeTelephone3());
		}
		if(guest.getCurrentAddressMobile1() != null && !guest.getCurrentAddressMobile1().isEmpty()){
			to.setCurrentAddressMobile1("+91"+guest.getCurrentAddressMobile1());
		}
		
		/*Boolean publicationDetailsPresent=false;
		if(guest.getNoOfPublicationsRefered() != null){
			to.setNoOfPublicationsRefered(guest.getNoOfPublicationsRefered());
			publicationDetailsPresent=true;
		}
		if(guest.getNoOfPublicationsNotRefered() != null){
			to.setNoOfPublicationsNotRefered(guest.getNoOfPublicationsNotRefered());
			publicationDetailsPresent=true;
		}
		if(guest.getBooks() != null){
			to.setBooks(guest.getBooks());
			publicationDetailsPresent=true;
		}
		downloadEmployeeResumeForm.setPublicationDetailsPresent(publicationDetailsPresent);*/
		if(guest.getEmpQualificationLevel() != null && guest.getEmpQualificationLevel().getName() != null){
			to.setQualificationLevel(guest.getEmpQualificationLevel().getName());
		}
		if(guest.getEmpSubjectArea() != null && guest.getEmpSubjectArea().getName() != null){
			to.setSubjectArea(guest.getEmpSubjectArea().getName());
		}
		
		String eligibilityTest="";
		if(guest.getEligibilityTest() != null){
			eligibilityTest=guest.getEligibilityTest();
		}
		if(guest.getEligibilityTestOther()!=null && !guest.getEligibilityTestOther().isEmpty()){
			if(!eligibilityTest.isEmpty())
				eligibilityTest=eligibilityTest+","+guest.getEligibilityTestOther();
			else
				eligibilityTest=guest.getEligibilityTestOther();
		}
		to.setEligibilityTest(!eligibilityTest.isEmpty()?eligibilityTest:"");
		
		if(guest.getOtherInfo() != null && !guest.getOtherInfo().isEmpty()){
			to.setOtherInfo(guest.getOtherInfo());
		}
		
		if(guest.getIndustryFunctionalArea()!=null && !guest.getIndustryFunctionalArea().isEmpty()){
			to.setIndustryFunctionalArea(guest.getIndustryFunctionalArea());
		}
	
		if(guest.getBloodGroup()!=null && !guest.getBloodGroup().isEmpty()){
			to.setBloodGroup(guest.getBloodGroup());
		}
		if(guest.getReligionId()!=null){
			to.setReligion(guest.getReligionId().getName()!=null?guest.getReligionId().getName():"");
		}
	/*	if(guest.getSemester()!=null && !guest.getSemester().isEmpty()){
			to.setSemester(guest.getSemester());
		}*/
		if(guest.getHonorariumPerHours()!=null && !guest.getHonorariumPerHours().isEmpty()){
			to.setHonorariumPerHours(guest.getHonorariumPerHours());
		}
		if(guest.getWorkingHoursPerWeek()!=null && !guest.getWorkingHoursPerWeek().isEmpty()){
			to.setWorkingHoursPerWeek(guest.getWorkingHoursPerWeek());
		}
		if(guest.getSubjectSpecilization()!=null && !guest.getSubjectSpecilization().isEmpty()){
			to.setSubjectSpecilization(guest.getSubjectSpecilization());
		}
		/*if(guest.getStartDate()!=null){
			to.setStartDate(CommonUtil.getStringDate(guest.getStartDate()));
		}
		if(guest.getEndDate()!=null){
			to.setEndDate(CommonUtil.getStringDate(guest.getEndDate()));
		}*/
	/*	if(guest.getgetEmpPhoto()!=null){
			byte[] empPhoto = guest.getEmpPhoto();
			to.setPhoto(guest.getEmpPhoto());
			if(session!=null){
				session.setAttribute("PhotoBytes", empPhoto);
			}
		}*/
		if (guest.getPreviousChristDetails() != null) {
			Set<GuestPreviousChristWorkDetails> PreviousExperiences = guest.getPreviousChristDetails();
			if (PreviousExperiences != null
					&& !PreviousExperiences.isEmpty()) {
				Iterator<GuestPreviousChristWorkDetails> iterator = PreviousExperiences
						.iterator();
				while (iterator.hasNext()) {
					GuestPreviousChristWorkDetails empPreviousExperiences = iterator
							.next();
					if (empPreviousExperiences != null) {
							if (empPreviousExperiences.getIsCurrentWorkingDates()!=null && empPreviousExperiences.getIsCurrentWorkingDates()) {
															
							if (empPreviousExperiences.getStartDate()!=null) {
								to.setStartDate(CommonUtil
										.ConvertStringToDateFormat(empPreviousExperiences.getStartDate().toString(),
												"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empPreviousExperiences.getEndDate()!=null) {
								to.setEndDate(CommonUtil
										.ConvertStringToDateFormat(empPreviousExperiences.getEndDate().toString(),
												"yyyy-mm-dd", "dd/mm/yyyy"));
							}
							
							}

						}
						
						}
			
					}
			
				}
		
		
		
		
		
		
		if (guest.getEmpImages() != null && !guest.getEmpImages().isEmpty()) {
			Set<GuestImages> empImages = guest.getEmpImages();
			if (empImages != null && !empImages.isEmpty()) {
				Iterator<GuestImages> iterator = empImages.iterator();
				while (iterator.hasNext()) {
					GuestImages eImages = iterator.next();
					if (eImages != null) {
						
						if (eImages.getEmpPhoto() != null && eImages.getEmpPhoto().length > 0)
						 {							
							byte[] myFileBytes = eImages.getEmpPhoto();
							to.setPhotoBytes(myFileBytes);
							if(session!=null){
								session.setAttribute("PhotoBytes", myFileBytes);
							}
							
						}
						

					}
				}			
				
			}
		}
		
		empResumeList.add(to);
	}
	
	return empResumeList;
}
	
	
public List<GuestEducationalDetailsTO> convertBoToToForEduDetails(List<GuestEducationalDetails> eduDetails, GuestFacultyInfoForm guestFacultyForm) {
		
		List<GuestEducationalDetailsTO> educationalDetails = new ArrayList<GuestEducationalDetailsTO>();
		if(eduDetails != null && !eduDetails.isEmpty()){
			Iterator<GuestEducationalDetails> iterator = eduDetails.iterator();
			while (iterator.hasNext()) {
				GuestEducationalDetails guestEdu = (GuestEducationalDetails) iterator.next();
				if(guestEdu.getEmpQualificationLevel().isFixedDisplay()){
					GuestEducationalDetailsTO to = new GuestEducationalDetailsTO();
					if(guestEdu.getEmpQualificationLevel() != null && guestEdu.getEmpQualificationLevel().getName() != null && guestEdu.getCourse() != null){
						to.setCourse(guestEdu.getEmpQualificationLevel().getName()+" - "+guestEdu.getCourse());
					}
					if(guestEdu.getSpecialization() != null && !guestEdu.getSpecialization().isEmpty()){
						to.setSpecialization(guestEdu.getSpecialization());
					}
					if(guestEdu.getYearOfCompletion() != 0){
						to.setYearOfComp(String.valueOf(guestEdu.getYearOfCompletion()));
					}
					if(guestEdu.getGrade() != null ){
						to.setGrade(guestEdu.getGrade());
					}
					if(guestEdu.getInstitute() != null ){
						to.setInstitute(guestEdu.getInstitute());
					}
					educationalDetails.add(to);
				}
			}
		}
		
		List<GuestEducationalDetailsTO> additionalQualification = new ArrayList<GuestEducationalDetailsTO>();
		if(eduDetails != null && !eduDetails.isEmpty()){
			Iterator<GuestEducationalDetails> iterator = eduDetails.iterator();
			while (iterator.hasNext()) {
				GuestEducationalDetails guestEdu = (GuestEducationalDetails) iterator.next();
				if(!guestEdu.getEmpQualificationLevel().isFixedDisplay()){
					GuestEducationalDetailsTO to = new GuestEducationalDetailsTO();
					if(guestEdu.getEmpQualificationLevel() != null && guestEdu.getEmpQualificationLevel().getName() != null && guestEdu.getCourse() != null){
						to.setCourse(guestEdu.getEmpQualificationLevel().getName()+" - "+guestEdu.getCourse());
					}
					if(guestEdu.getSpecialization() != null && !guestEdu.getSpecialization().isEmpty()){
						to.setSpecialization(guestEdu.getSpecialization());
					}
					if(guestEdu.getYearOfCompletion() != 0){
						to.setYearOfComp(String.valueOf(guestEdu.getYearOfCompletion()));
					}
					if(guestEdu.getGrade() != null ){
						to.setGrade(guestEdu.getGrade());
					}
					if(guestEdu.getInstitute() != null ){
						to.setInstitute(guestEdu.getInstitute());
					}
					additionalQualification.add(to);
				}
			}
			guestFacultyForm.setAdditionalQualification(additionalQualification);
		}
		return educationalDetails;
	}
	

public List<GuestPreviousExperienceTO> convertBoToToForExpDetails(List<GuestPreviousExperience> teachingExperience, GuestFaculty guest, GuestFacultyInfoForm guestFacultyForm) {
	
	List<GuestPreviousExperienceTO> tos = new ArrayList<GuestPreviousExperienceTO>();
	if(teachingExperience != null && !teachingExperience.isEmpty()){
		Iterator<GuestPreviousExperience>  iterator = teachingExperience.iterator();
		while (iterator.hasNext()) {
			GuestPreviousExperience guestPrevExp = (GuestPreviousExperience) iterator.next();
			if(guestPrevExp.isTeachingExperience()){
				GuestPreviousExperienceTO to = new GuestPreviousExperienceTO();
				String teachingExp = "";
				teachingExp = teachingExp + String.valueOf(guestPrevExp.getExpYears()) + " Year(s) " + String.valueOf(guestPrevExp.getExpMonths()) + " Month(s)";
				to.setTotalTeachingExperience(teachingExp);
				to.setCurrentTeachnigDesignation(guestPrevExp.getEmpDesignation());
				to.setCurrentOrganisation(guestPrevExp.getEmpOrganization());
				
				to.setTeachingExpYears(String.valueOf(guestPrevExp.getExpYears()));
				to.setTeachingExpMonths(String.valueOf(guestPrevExp.getExpMonths()));
				
				tos.add(to);
			}
			
		}
	}
	List<GuestPreviousExperienceTO> industryExp = new ArrayList<GuestPreviousExperienceTO>();
	if(teachingExperience != null && !teachingExperience.isEmpty()){
		Iterator<GuestPreviousExperience>  iterator = teachingExperience.iterator();
		while (iterator.hasNext()) {
			GuestPreviousExperience guestPrevExp = (GuestPreviousExperience) iterator.next();
			
			if(guestPrevExp.isIndustryExperience()){
				GuestPreviousExperienceTO to = new GuestPreviousExperienceTO();
				String indExp = "";
				indExp = indExp + String.valueOf(guestPrevExp.getExpYears()) + " Year(s) " + String.valueOf(guestPrevExp.getExpMonths()) + " Month(s)";
				to.setExperience(indExp);
				to.setEmpDesignation(guestPrevExp.getEmpDesignation());
				to.setEmpOrganization(guestPrevExp.getEmpOrganization());
				
				to.setIndustryExpYears(String.valueOf(guestPrevExp.getExpYears()));
				to.setIndustryExpMonths(String.valueOf(guestPrevExp.getExpMonths()));
			
				industryExp.add(to);
			}
			
		}
		guestFacultyForm.setIndustryExperience(industryExp);
	}
	return tos;
}

public Map<String, String> convertBoToForm(List<Department> list) {
	Map<String, String> map = new LinkedHashMap<String, String>();
	if(list!=null){
		Iterator<Department> iterator = list.iterator();
		while (iterator.hasNext()) {
			Department department = (Department) iterator.next();
			EmpEventVacationTo vacationTo= new EmpEventVacationTo();
			if(department!=null && department.getId() != 0){
					vacationTo.setDepartmentId(department.getId());
			}
			if(department!=null && department.getName() != null){
					vacationTo.setDepartmentName(department.getName());
			}
			if(department!=null && department.getEmployeeStreamBO() != null && department.getEmployeeStreamBO().getName() != null){
					vacationTo.setStreamName(department.getEmployeeStreamBO().getName());
			}else{
				vacationTo.setStreamName("-");
			}
			vacationTo.setDepartmentStreamNames(vacationTo.getDepartmentName().concat("("+vacationTo.getStreamName()+")"));
			map.put(String.valueOf(vacationTo.getDepartmentId()), vacationTo.getDepartmentStreamNames());
		}
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}
public GuestFacultyTO convertGuestBoToGuestTo(GuestFaculty guestFaculty,GuestFacultyInfoForm guestFacultyInfoForm){
	GuestFacultyTO facultyTo =new GuestFacultyTO();
	if(guestFaculty!=null){
		facultyTo.setName(guestFaculty.getFirstName());
		facultyTo.setMobile(guestFaculty.getCurrentAddressMobile1());
		facultyTo.setEmail(guestFaculty.getEmail());
		facultyTo.setCommunicationAddress(guestFaculty.getCommunicationAddressLine1()+","+guestFaculty.getCommunicationAddressLine2()+","+guestFaculty.getCommunicationAddressCity());
		facultyTo.setBankAccNo(guestFaculty.getBankAccNo());
		facultyTo.setPanNo(guestFaculty.getPanNo());
		facultyTo.setBankBranch(guestFaculty.getBankBranch());
		facultyTo.setBankIfscCode(guestFaculty.getBankIfscCode());
		guestFacultyInfoForm.setOriginalBankAccountNo(guestFaculty.getBankAccNo());
		guestFacultyInfoForm.setOriginalBankBranch(guestFaculty.getBankBranch());
		guestFacultyInfoForm.setOriginalIfscCode(guestFaculty.getBankIfscCode());
		guestFacultyInfoForm.setOriginalPanNo(guestFaculty.getPanNo());
	}
	return facultyTo;
}
}