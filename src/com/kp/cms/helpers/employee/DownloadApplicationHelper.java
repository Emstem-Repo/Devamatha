package com.kp.cms.helpers.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.forms.employee.DownloadApplicationForm;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.utilities.CommonUtil;

public class DownloadApplicationHelper {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	public static volatile DownloadApplicationHelper helper=null;
	public static DownloadApplicationHelper getInstance(){
		if(helper == null){
			helper = new DownloadApplicationHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param empOnlineResumesUsersList
	 * @return
	 * @throws Exception
	 */
	public List<DownloadEmployeeResumeTO> convertBoToTo( List<EmpOnlineResumeUsers> empOnlineResumesUsersList) throws Exception{
		List<DownloadEmployeeResumeTO> empResumeList = new ArrayList<DownloadEmployeeResumeTO>();
		int count = 0;
		Iterator<EmpOnlineResumeUsers> empOnlineResumes = empOnlineResumesUsersList.iterator();
		while (empOnlineResumes.hasNext()) {
			EmpOnlineResumeUsers empOnlineResumeUsers = (EmpOnlineResumeUsers) empOnlineResumes .next();
			if(empOnlineResumeUsers.getOnlineResume()!=null && !empOnlineResumeUsers.getOnlineResume().toString().isEmpty()){
				DownloadEmployeeResumeTO to = new DownloadEmployeeResumeTO();
				count++;
				to.setCount(count);
				if(empOnlineResumeUsers.getOnlineResume().getId()!=0){
					to.setId(empOnlineResumeUsers.getOnlineResume().getId());
				}
				if(empOnlineResumeUsers.getOnlineResume().getDepartment()!=null){
					if(empOnlineResumeUsers.getOnlineResume().getDepartment().getName()!=null && !empOnlineResumeUsers.getOnlineResume().getDepartment().getName().isEmpty()){
						to.setDepartmentName(empOnlineResumeUsers.getOnlineResume().getDepartment().getName());
					}
				}
				if(empOnlineResumeUsers.getOnlineResume().getPostAppliedDesig()!=null){
					to.setDesignationName(empOnlineResumeUsers.getOnlineResume().getPostAppliedDesig());
				}
				if(empOnlineResumeUsers.getOnlineResume().getName()!=null){
					to.setEmployeeName(empOnlineResumeUsers.getOnlineResume().getName());
				}
				if(empOnlineResumeUsers.getOnlineResume().getApplicationNo()!=null && !empOnlineResumeUsers.getOnlineResume().getApplicationNo().isEmpty()){
					to.setApplicationNO(empOnlineResumeUsers.getOnlineResume().getApplicationNo());
				}
				if(empOnlineResumeUsers.getOnlineResume().getEmpQualificationLevel()!=null){
					if(empOnlineResumeUsers.getOnlineResume().getEmpQualificationLevel().getName()!=null 
							 && !empOnlineResumeUsers.getOnlineResume().getEmpQualificationLevel().getName().isEmpty()){
						to.setQualificationId(empOnlineResumeUsers.getOnlineResume().getEmpQualificationLevel().getName());
					}
				}
				if(empOnlineResumeUsers.getOnlineResume().getEmail()!=null ){
					to.setMailId(empOnlineResumeUsers.getOnlineResume().getEmail());
				}
				if(empOnlineResumeUsers.getOnlineResume().getDesiredPost()!=null){
					to.setPostAppliedFor(empOnlineResumeUsers.getOnlineResume().getDesiredPost());
				}
				if(empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine1() != null && !empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine1().isEmpty()){
					to.setCurrentAddress1(empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine1());
				}
				if(empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine2() != null && !empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine2().isEmpty()){
					to.setCurrentAddress2(empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine2());
				}
				if(empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine3() != null && !empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine3().isEmpty()){
					to.setCurrentAddress3(empOnlineResumeUsers.getOnlineResume().getCurrentAddressLine3());
				}
				if(empOnlineResumeUsers.getOnlineResume().getMobileNo1() != null && !empOnlineResumeUsers.getOnlineResume().getMobileNo1().isEmpty()){
					to.setMobileNumber(empOnlineResumeUsers.getOnlineResume().getMobileNo1());
				}
				if(empOnlineResumeUsers.getOnlineResume().getDateOfSubmission() != null ){
					String subDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResumeUsers.getOnlineResume().getDateOfSubmission()), DownloadApplicationHelper.SQL_DATEFORMAT,DownloadApplicationHelper.FROM_DATEFORMAT);
					to.setSubmitedDate(subDate);
				}
				if(empOnlineResumeUsers.getOnlineResume().getEligibilityTest() != null && !empOnlineResumeUsers.getOnlineResume().getEligibilityTest().isEmpty()){
					to.setEligibilityTest(empOnlineResumeUsers.getOnlineResume().getEligibilityTest());
				}
				if(empOnlineResumeUsers.getOnlineResume().getGender() != null && !empOnlineResumeUsers.getOnlineResume().getGender().isEmpty()){
					to.setGender(empOnlineResumeUsers.getOnlineResume().getGender());
				}
				if(empOnlineResumeUsers.getOnlineResume().getDateOfBirth() != null){
					String dob = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResumeUsers.getOnlineResume().getDateOfBirth()), DownloadApplicationHelper.SQL_DATEFORMAT,DownloadApplicationHelper.FROM_DATEFORMAT);
					to.setDateOfBirth(dob);
					to.setAge(getAge(empOnlineResumeUsers.getOnlineResume().getDateOfBirth()));
				}
				String totalExp="";
				if(empOnlineResumeUsers.getOnlineResume().getTotalExpYear()!=null && empOnlineResumeUsers.getOnlineResume().getTotalExpYear()!=0){
					if(empOnlineResumeUsers.getOnlineResume().getTotalExpYear()!=1)
					    totalExp = String.valueOf(empOnlineResumeUsers.getOnlineResume().getTotalExpYear())+" Year(s)";
					else
						totalExp = String.valueOf(empOnlineResumeUsers.getOnlineResume().getTotalExpYear())+" Year ";
				}
				else
					totalExp = " 0 Year ";
				if(empOnlineResumeUsers.getOnlineResume().getTotalExpMonths()!=null && empOnlineResumeUsers.getOnlineResume().getTotalExpMonths()!=0){
					if(empOnlineResumeUsers.getOnlineResume().getTotalExpMonths()!=1)
					    totalExp = totalExp + String.valueOf(empOnlineResumeUsers.getOnlineResume().getTotalExpMonths())+" Month(s)";
					else
						totalExp = totalExp + String.valueOf(empOnlineResumeUsers.getOnlineResume().getTotalExpMonths())+" Month ";
				}
				else
					totalExp = totalExp + " 0 Month";
				to.setTotalExp(totalExp);
				if(empOnlineResumeUsers.getOnlineResume().getEducationalDetailsSet() != null && !empOnlineResumeUsers.getOnlineResume().getEducationalDetailsSet().isEmpty()){
					StringBuilder course =new StringBuilder();
					//String highQualification="";
					int highNumber=0;
					Map<Integer,String> courseMap=new HashMap<Integer, String>();
					Set<EmpOnlineEducationalDetails> educationalDetailsSet = empOnlineResumeUsers.getOnlineResume().getEducationalDetailsSet();
					Iterator<EmpOnlineEducationalDetails> iterator2 = educationalDetailsSet.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineEducationalDetails empOnlineEducationalDetails = (EmpOnlineEducationalDetails) iterator2.next();
						if(empOnlineEducationalDetails.getCourse()!=null)
						      courseMap.put(empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder(), empOnlineEducationalDetails.getCourse());
						if(empOnlineEducationalDetails.getEmpQualificationLevel() != null && empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder() != null){
							if(highNumber < empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder()){
								highNumber = empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder();
								//highQualification = empOnlineEducationalDetails.getEmpQualificationLevel().getName();
							}
						}
					}
					Iterator<Integer> itr=courseMap.keySet().iterator();
					while(itr.hasNext()){
						course.append(courseMap.get(itr.next())).append(", ");
					}
					int len=course.length()-2;
					if(course.toString().endsWith(", ")){
						course.setCharAt(len, ' ');
					}
					to.setCourseNames(course.toString().trim());
				}
				if(empOnlineResumeUsers.getOnlineResume().getOtherInfo()!=null)
					to.setOtherInfo(empOnlineResumeUsers.getOnlineResume().getOtherInfo());
				if(empOnlineResumeUsers.getOnlineResume().getNoOfPublicationsRefered()!=null)
					to.setNoOfPublicationsRefered(empOnlineResumeUsers.getOnlineResume().getNoOfPublicationsRefered());
				if(empOnlineResumeUsers.getOnlineResume().getDateOfSubmission() != null){
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResumeUsers.getOnlineResume().getDateOfSubmission()), DownloadApplicationHelper.SQL_DATEFORMAT,DownloadApplicationHelper.FROM_DATEFORMAT);
					to.setDateOfApplication(date);
				}
				if(empOnlineResumeUsers.getOnlineResume().getStatus()!=null){
					to.setCurrentStatus(empOnlineResumeUsers.getOnlineResume().getStatus());
				}
				if(empOnlineResumeUsers.getOnlineResume().getStatusDate()!=null){
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResumeUsers.getOnlineResume().getStatusDate()), DownloadApplicationHelper.SQL_DATEFORMAT,DownloadApplicationHelper.FROM_DATEFORMAT);
					to.setCurrentDate(date);
				}
				if(empOnlineResumeUsers.getOnlineResume().getEmpSubject()!=null){
					to.setEmpSubjectName(empOnlineResumeUsers.getOnlineResume().getEmpSubject().getSubjectName());
				}
				empResumeList.add(to);
			}
		}
		return empResumeList;
	}
	/**
	 * @param dateOfBirth
	 * @return
	 */
	public String getAge(Date dateOfBirth){
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		int dobYear=Integer.parseInt(format.format(dateOfBirth));
		int currentYear=Integer.parseInt(format.format(new Date()));
		int age=currentYear-dobYear;
		return String.valueOf(age);
	}
	/**
	 * @param empOnlineResume
	 * @param teachingExperience
	 * @param downloadApplicationForm
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public List<DownloadEmployeeResumeTO> convertBoToToForPrint( EmpOnlineResume empOnlineResume, List<EmpOnlinePreviousExperience> teachingExperience,
			DownloadApplicationForm downloadApplicationForm, HttpSession session)throws Exception {
		
		//Teaching Exp and industry exp
		List<EmpOnlinePreviousExperienceTO> teachingExpTos = new ArrayList<EmpOnlinePreviousExperienceTO>();
		List<EmpOnlinePreviousExperienceTO> industryExpTos = new ArrayList<EmpOnlinePreviousExperienceTO>();
		if(teachingExperience != null && !teachingExperience.isEmpty()){
			Iterator<EmpOnlinePreviousExperience>  iterator = teachingExperience.iterator();
			while (iterator.hasNext()) {
				EmpOnlinePreviousExperience empOnlinePreviousExperience = (EmpOnlinePreviousExperience) iterator.next();
				EmpOnlinePreviousExperienceTO expTo = new EmpOnlinePreviousExperienceTO();
				if(empOnlinePreviousExperience.isTeachingExperience()){
					expTo.setEmpDesignation(empOnlinePreviousExperience.getEmpDesignation());
					expTo.setEmpOrganization(empOnlinePreviousExperience.getEmpOrganization());
					expTo.setTeachingExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					expTo.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					expTo.setFromDate(empOnlinePreviousExperience.getFromDate().toString());
					expTo.setToDate(empOnlinePreviousExperience.getToDate().toString());
				}
				if(empOnlinePreviousExperience.isIndustryExperience()){
					expTo.setDesignation(empOnlinePreviousExperience.getEmpDesignation());
					expTo.setOrganization(empOnlinePreviousExperience.getEmpOrganization());
					expTo.setIndustryExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					expTo.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					expTo.setFromDate(empOnlinePreviousExperience.getFromDate().toString());
					expTo.setToDate(empOnlinePreviousExperience.getToDate().toString());
				}
				teachingExpTos.add(expTo);
				industryExpTos.add(expTo);
			}
		}
		downloadApplicationForm.setTeachingExperience(teachingExpTos);
		downloadApplicationForm.setTeachingExperience(industryExpTos);
		
		
		List<DownloadEmployeeResumeTO> empResumeList = new ArrayList<DownloadEmployeeResumeTO>();
		DownloadEmployeeResumeTO to = new DownloadEmployeeResumeTO();
		
		if(teachingExperience != null && !teachingExperience.isEmpty()){
			Iterator<EmpOnlinePreviousExperience>  iterator = teachingExperience.iterator();
			int teachingExpYears = 0;
			int teachingExpMonths = 0;
			int industryExpYears = 0;
			int industryExpMonths = 0;
			while (iterator.hasNext()) {
				EmpOnlinePreviousExperience empOnlinePreviousExperience = (EmpOnlinePreviousExperience) iterator.next();
				
				if(empOnlinePreviousExperience.isTeachingExperience()){
					
					teachingExpYears =  teachingExpYears + empOnlinePreviousExperience.getExpYears();
					teachingExpMonths = teachingExpMonths + empOnlinePreviousExperience.getExpMonths();
				}
				if(empOnlinePreviousExperience.isIndustryExperience()){
					industryExpYears =  industryExpYears + empOnlinePreviousExperience.getExpYears();
					industryExpMonths = industryExpMonths + empOnlinePreviousExperience.getExpMonths();
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
		
		
		if(empOnlineResume != null ){
			
			if(empOnlineResume.getTotalExpYear() != null && empOnlineResume.getTotalExpMonths() != null){
				String totalExp = empOnlineResume.getTotalExpYear() + " Year(s) " + empOnlineResume.getTotalExpMonths() +" Month(s)";
				to.setTotalExp(totalExp);
			}
			if(empOnlineResume.getName() != null && !empOnlineResume.getName().isEmpty()){
				to.setEmployeeName(empOnlineResume.getName().toUpperCase());
			}
			if(empOnlineResume.getPostAppliedDesig() != null){
				to.setPostAppliedFor(empOnlineResume.getPostAppliedDesig());
			}
			if(empOnlineResume.getDepartment() != null ){
				if(empOnlineResume.getDepartment().getName() != null && !empOnlineResume.getDepartment().getName().isEmpty()){
					to.setDepartmentName(empOnlineResume.getDepartment().getName());
				}
			}
			if(empOnlineResume.getDesignation() != null ){
				if(empOnlineResume.getDesignation().getName() != null && !empOnlineResume.getDesignation().getName().isEmpty()){
					to.setDesignationName(empOnlineResume.getDesignation().getName());
				}
			}
			if(empOnlineResume.getGender() != null && !empOnlineResume.getGender().isEmpty()){
				to.setGender(empOnlineResume.getGender());
			}
			if(empOnlineResume.getJobCode() != null && !empOnlineResume.getJobCode().isEmpty()){
				to.setJobCode(empOnlineResume.getJobCode());
			}
			if(empOnlineResume.getAddressLine1() != null && !empOnlineResume.getAddressLine1().isEmpty()){
				to.setAddressLine1(empOnlineResume.getAddressLine1());
			}
			if(empOnlineResume.getAddressLine2() != null && !empOnlineResume.getAddressLine2().isEmpty()){
				to.setAddressLine2(empOnlineResume.getAddressLine2());
			}
			if(empOnlineResume.getAddressLine3() != null && !empOnlineResume.getAddressLine3().isEmpty()){
				to.setAddressLine3(empOnlineResume.getAddressLine3());
			}
			if(empOnlineResume.getCurrentAddressLine1() != null && !empOnlineResume.getCurrentAddressLine1().isEmpty()){
				to.setCurrentAddress1(empOnlineResume.getCurrentAddressLine1());
			}
			if(empOnlineResume.getCurrentAddressLine2() != null && !empOnlineResume.getCurrentAddressLine2().isEmpty()){
				to.setCurrentAddress2(empOnlineResume.getCurrentAddressLine2());
			}
			if(empOnlineResume.getCurrentAddressLine3() != null && !empOnlineResume.getCurrentAddressLine3().isEmpty()){
				to.setCurrentAddress3(empOnlineResume.getCurrentAddressLine3());
			}
			if(empOnlineResume.getNationality() != null ){
				if(empOnlineResume.getNationality().getName() != null && !empOnlineResume.getNationality().getName().isEmpty()){
					to.setNationality(empOnlineResume.getNationality().getName() );
				}
			}
			if(empOnlineResume.getCurrentCity() != null && !empOnlineResume.getCurrentCity().isEmpty()){
				to.setCurrentLocation(empOnlineResume.getCurrentCity());
			}
			if(empOnlineResume.getMaritalStatus() != null && !empOnlineResume.getMaritalStatus().isEmpty()){
				to.setMarital(empOnlineResume.getMaritalStatus());
			}
			if(empOnlineResume.getEmail() != null && !empOnlineResume.getEmail().isEmpty()){
				to.setEmail(empOnlineResume.getEmail());
			}
			if(empOnlineResume.getDateOfBirth() != null){
				to.setDateOfBirth(CommonUtil.getStringDate(empOnlineResume.getDateOfBirth()));
			}
			if(empOnlineResume.getReservationCategory() != null && !empOnlineResume.getReservationCategory().isEmpty()){
				to.setReservationCategory(empOnlineResume.getReservationCategory());
			}
			if(empOnlineResume.getPhNo1() != null && !empOnlineResume.getPhNo1().isEmpty()){
				to.setContactNumber("+91"+empOnlineResume.getPhNo1());
			}
			if(empOnlineResume.getMobileNo1() != null && !empOnlineResume.getMobileNo1().isEmpty()){
				to.setMobileNumber("+91"+empOnlineResume.getMobileNo1());
			}
			if(empOnlineResume.isCurrentlyWorking()){
				to.setWorkStatus("Yes");
			}else{
				to.setWorkStatus("NO");
			}
			Boolean publicationDetailsPresent=false;
			if(empOnlineResume.getNoOfPublicationsRefered() != null){
				to.setNoOfPublicationsRefered(empOnlineResume.getNoOfPublicationsRefered());
				publicationDetailsPresent=true;
			}
			if(empOnlineResume.getNoOfPublicationsNotRefered() != null){
				to.setNoOfPublicationsNotRefered(empOnlineResume.getNoOfPublicationsNotRefered());
				publicationDetailsPresent=true;
			}
			if(empOnlineResume.getBooks() != null){
				to.setBooks(empOnlineResume.getBooks());
				publicationDetailsPresent=true;
			}
			downloadApplicationForm.setPublicationDetailsPresent(publicationDetailsPresent);
			if(empOnlineResume.getEmpQualificationLevel() != null && empOnlineResume.getEmpQualificationLevel().getName() != null){
				to.setQualificationLevel(empOnlineResume.getEmpQualificationLevel().getName());
			}
			if(empOnlineResume.getEmpSubjectArea() != null && empOnlineResume.getEmpSubjectArea().getName() != null){
				to.setSubjectArea(empOnlineResume.getEmpSubjectArea().getName());
			}
			if(empOnlineResume.getEmpJobType() != null ){
				to.setJobType(empOnlineResume.getEmpJobType());
			}
			if(empOnlineResume.getEmploymentStatus() != null){
				to.setEmpStatus(empOnlineResume.getEmploymentStatus());
			}
			if(empOnlineResume.getExpectedSalaryLakhs() != null && empOnlineResume.getExpectedSalaryThousands() != null){
				String expectedSal = "";
				expectedSal = expectedSal + String.valueOf(empOnlineResume.getExpectedSalaryLakhs()) +" Lakh(s) " + empOnlineResume.getExpectedSalaryThousands() + " Thousand(s)";
				to.setExpectedSalary(expectedSal);
			}
			String eligibilityTest="";
			if(empOnlineResume.getEligibilityTest() != null){
				eligibilityTest=empOnlineResume.getEligibilityTest();
			}
			if(empOnlineResume.getEligibilityTestOther()!=null && !empOnlineResume.getEligibilityTestOther().isEmpty()){
				if(!eligibilityTest.isEmpty())
					eligibilityTest=eligibilityTest+","+empOnlineResume.getEligibilityTestOther();
				else
					eligibilityTest=empOnlineResume.getEligibilityTestOther();
			}
			to.setEligibilityTest(!eligibilityTest.isEmpty()?eligibilityTest:"");
			
			if(empOnlineResume.getOtherInfo() != null && !empOnlineResume.getOtherInfo().isEmpty()){
				to.setOtherInfo(empOnlineResume.getOtherInfo());
			}
			if(empOnlineResume.getIndustryFunctionalArea()!=null && !empOnlineResume.getIndustryFunctionalArea().isEmpty()){
				to.setIndustryFunctionalArea(empOnlineResume.getIndustryFunctionalArea());
			}
			if(empOnlineResume.getBloodGroup()!=null && !empOnlineResume.getBloodGroup().isEmpty()){
				to.setBloodGroup(empOnlineResume.getBloodGroup());
			}
			if(empOnlineResume.getReligion()!=null){
				to.setReligion(empOnlineResume.getReligion().getName()!=null?empOnlineResume.getReligion().getName():"");
			}
			if(empOnlineResume.getApplicationNo()!=null && !empOnlineResume.getApplicationNo().isEmpty()){
				downloadApplicationForm.setApplicationNo(empOnlineResume.getApplicationNo());
			}
			if(empOnlineResume.getDateOfSubmission() != null){
				to.setDateOfApplication(CommonUtil.getStringDate(empOnlineResume.getDateOfSubmission()));
			}
			if(empOnlineResume.getEmpPhoto()!=null){
				byte[] empPhoto = empOnlineResume.getEmpPhoto();
				to.setPhoto(empOnlineResume.getEmpPhoto());
				if(session!=null){
					session.setAttribute("PhotoBytes", empPhoto);
				}
			}
			if(empOnlineResume.getResearchPapersRefereed()!=null){
				to.setResearchPapersRefereed(empOnlineResume.getResearchPapersRefereed().toString());
			}
			if(empOnlineResume.getResearchPapersNonRefereed()!=null){
				to.setResearchPapersNonRefereed(empOnlineResume.getResearchPapersNonRefereed().toString());
			}
			if(empOnlineResume.getResearchPapersProceedings()!=null){
				to.setResearchPapersProceedings(empOnlineResume.getResearchPapersProceedings().toString());
			}
			if(empOnlineResume.getInternationalBookPublications()!=null){
				to.setInternationalBookPublications(empOnlineResume.getInternationalBookPublications().toString());
			}
			if(empOnlineResume.getNationalBookPublications()!=null){
				to.setNationalBookPublications(empOnlineResume.getNationalBookPublications().toString());
			}
			if(empOnlineResume.getLocalBookPublications()!=null){
				to.setLocalBookPublications(empOnlineResume.getLocalBookPublications().toString());
			}
			if(empOnlineResume.getChaptersEditedBooksInternational()!=null){
				to.setChaptersEditedBooksInternational(empOnlineResume.getChaptersEditedBooksInternational().toString());
			}
			if(empOnlineResume.getChaptersEditedBooksNational()!=null){
				to.setChaptersEditedBooksNational(empOnlineResume.getChaptersEditedBooksNational().toString());
			}
			if(empOnlineResume.getMajorSponseredProjects()!=null){
				to.setMajorSponseredProjects(empOnlineResume.getMajorSponseredProjects().toString());
			}
			if(empOnlineResume.getMinorSponseredProjects()!=null){
				to.setMinorSponseredProjects(empOnlineResume.getMinorSponseredProjects().toString());
			}
			if(empOnlineResume.getPhdResearchGuidance()!=null){
				to.setPhdResearchGuidance(empOnlineResume.getPhdResearchGuidance().toString());
			}
			if(empOnlineResume.getMphilResearchGuidance()!=null){
				to.setMphilResearchGuidance(empOnlineResume.getMphilResearchGuidance().toString());
			}
			if(empOnlineResume.getConsultancy1SponseredProjects()!=null){
				to.setConsultancy1SponseredProjects(empOnlineResume.getConsultancy1SponseredProjects().toString());
			}
			if(empOnlineResume.getConsultancy2SponseredProjects()!=null){
				to.setConsultancy2SponseredProjects(empOnlineResume.getConsultancy2SponseredProjects().toString());
			}
			if(empOnlineResume.getTrainingAttendedFdp1Weeks()!=null){
				to.setTrainingAttendedFdp1Weeks(empOnlineResume.getTrainingAttendedFdp1Weeks().toString());
			}
			if(empOnlineResume.getTrainingAttendedFdp2Weeks()!=null){
				to.setTrainingAttendedFdp2Weeks(empOnlineResume.getTrainingAttendedFdp2Weeks().toString());
			}
			if(empOnlineResume.getInternationalConferencePresentaion()!=null){
				to.setInternationalConferencePresentaion(empOnlineResume.getInternationalConferencePresentaion().toString());
			}
			if(empOnlineResume.getNationalConferencePresentaion()!=null){
				to.setNationalConferencePresentaion(empOnlineResume.getNationalConferencePresentaion().toString());
			}
			if(empOnlineResume.getLocalConferencePresentaion()!=null){
				to.setLocalConferencePresentaion(empOnlineResume.getLocalConferencePresentaion().toString());
			}
			if(empOnlineResume.getRegionalConferencePresentaion()!=null){
				to.setRegionalConferencePresentaion(empOnlineResume.getRegionalConferencePresentaion().toString());
			}
			if(empOnlineResume.getFatherName()!=null){
				to.setFatherName(empOnlineResume.getFatherName());
			}
			if(empOnlineResume.getMotherName()!=null){
				to.setMotherName(empOnlineResume.getMotherName());
			}
			if(empOnlineResume.getAlternateMobile()!=null){
				to.setAlternateMobile(empOnlineResume.getAlternateMobile());
			}
			if(empOnlineResume.getEmpSubject()!=null){
				to.setEmpSubjectName(empOnlineResume.getEmpSubject().getSubjectName());
			}
			empResumeList.add(to);
		}
		
		return empResumeList;
	}
	/**
	 * @param eduDetails
	 * @param downloadApplicationForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpOnlineEducationalDetailsTO> convertBoToToForEduDetails( List<EmpOnlineEducationalDetails> eduDetails,
			DownloadApplicationForm downloadApplicationForm)throws Exception {
		
		List<EmpOnlineEducationalDetailsTO> educationalDetails = new ArrayList<EmpOnlineEducationalDetailsTO>();
		if(eduDetails != null && !eduDetails.isEmpty()){
			Iterator<EmpOnlineEducationalDetails> iterator = eduDetails.iterator();
			while (iterator.hasNext()) {
				EmpOnlineEducationalDetails empOnlineEducationalDetails = (EmpOnlineEducationalDetails) iterator.next();
				if(empOnlineEducationalDetails.getEmpQualificationLevel().isFixedDisplay()){
					EmpOnlineEducationalDetailsTO to = new EmpOnlineEducationalDetailsTO();
					if(empOnlineEducationalDetails.getEmpQualificationLevel() != null && empOnlineEducationalDetails.getEmpQualificationLevel().getName() != null && empOnlineEducationalDetails.getCourse() != null){
						to.setCourse(empOnlineEducationalDetails.getEmpQualificationLevel().getName()+" - "+empOnlineEducationalDetails.getCourse());
					}
					if(empOnlineEducationalDetails.getSpecialization() != null && !empOnlineEducationalDetails.getSpecialization().isEmpty()){
						to.setSpecialization(empOnlineEducationalDetails.getSpecialization());
					}
					if(empOnlineEducationalDetails.getYearOfCompletion() != 0){
						to.setYearOfCompletion(empOnlineEducationalDetails.getYearOfCompletion());
					}
					if(empOnlineEducationalDetails.getGrade() != null ){
						to.setGrade(empOnlineEducationalDetails.getGrade());
					}
					if(empOnlineEducationalDetails.getInstitute() != null ){
						to.setInstitute(empOnlineEducationalDetails.getInstitute());
					}
					educationalDetails.add(to);
				}
			}
		}
		
		List<EmpOnlineEducationalDetailsTO> additionalQualification = new ArrayList<EmpOnlineEducationalDetailsTO>();
		if(eduDetails != null && !eduDetails.isEmpty()){
			Iterator<EmpOnlineEducationalDetails> iterator = eduDetails.iterator();
			while (iterator.hasNext()) {
				EmpOnlineEducationalDetails empOnlineEducationalDetails = (EmpOnlineEducationalDetails) iterator.next();
				if(!empOnlineEducationalDetails.getEmpQualificationLevel().isFixedDisplay()){
					EmpOnlineEducationalDetailsTO to = new EmpOnlineEducationalDetailsTO();
					if(empOnlineEducationalDetails.getEmpQualificationLevel() != null && empOnlineEducationalDetails.getEmpQualificationLevel().getName() != null && empOnlineEducationalDetails.getCourse() != null){
						to.setCourse(empOnlineEducationalDetails.getEmpQualificationLevel().getName()+" - "+empOnlineEducationalDetails.getCourse());
					}
					if(empOnlineEducationalDetails.getSpecialization() != null && !empOnlineEducationalDetails.getSpecialization().isEmpty()){
						to.setSpecialization(empOnlineEducationalDetails.getSpecialization());
					}
					if(empOnlineEducationalDetails.getYearOfCompletion() != 0){
						to.setYearOfCompletion(empOnlineEducationalDetails.getYearOfCompletion());
					}
					if(empOnlineEducationalDetails.getGrade() != null ){
						to.setGrade(empOnlineEducationalDetails.getGrade());
					}
					if(empOnlineEducationalDetails.getInstitute() != null ){
						to.setInstitute(empOnlineEducationalDetails.getInstitute());
					}
					additionalQualification.add(to);
				}
			}
			downloadApplicationForm.setAdditionalQualification(additionalQualification);
		}
		return educationalDetails;
	}
	/**
	 * @param teachingExperience
	 * @param empOnlineResume
	 * @param downloadApplicationForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpOnlinePreviousExperienceTO> convertBoToToForExpDetails( List<EmpOnlinePreviousExperience> teachingExperience,
			EmpOnlineResume empOnlineResume, DownloadApplicationForm downloadApplicationForm) throws Exception{
		
		List<EmpOnlinePreviousExperienceTO> tos = new ArrayList<EmpOnlinePreviousExperienceTO>();
		if(teachingExperience != null && !teachingExperience.isEmpty()){
			Iterator<EmpOnlinePreviousExperience>  iterator = teachingExperience.iterator();
			while (iterator.hasNext()) {
				EmpOnlinePreviousExperience empOnlinePreviousExperience = (EmpOnlinePreviousExperience) iterator.next();
				if(empOnlinePreviousExperience.isTeachingExperience()){
					EmpOnlinePreviousExperienceTO to = new EmpOnlinePreviousExperienceTO();
					String teachingExp = "";
					teachingExp = teachingExp + String.valueOf(empOnlinePreviousExperience.getExpYears()) + " Year(s) " + String.valueOf(empOnlinePreviousExperience.getExpMonths()) + " Month(s)";
					to.setTotalTeachingExperience(teachingExp);
					to.setEmpDesignation(empOnlinePreviousExperience.getEmpDesignation());
					to.setEmpOrganization(empOnlinePreviousExperience.getEmpOrganization());
					to.setTeachingExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					to.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					to.setFromDate(empOnlinePreviousExperience.getFromDate().toString());
					to.setToDate(empOnlinePreviousExperience.getToDate().toString());
					//
					tos.add(to);
				}
				
			}
		}
		List<EmpOnlinePreviousExperienceTO> industryExp = new ArrayList<EmpOnlinePreviousExperienceTO>();
		if(teachingExperience != null && !teachingExperience.isEmpty()){
			Iterator<EmpOnlinePreviousExperience>  iterator = teachingExperience.iterator();
			while (iterator.hasNext()) {
				EmpOnlinePreviousExperience empOnlinePreviousExperience = (EmpOnlinePreviousExperience) iterator.next();
				
				if(empOnlinePreviousExperience.isIndustryExperience()){
					EmpOnlinePreviousExperienceTO to = new EmpOnlinePreviousExperienceTO();
					String teachingExp = "";
					teachingExp = teachingExp + String.valueOf(empOnlinePreviousExperience.getExpYears()) + " Year(s) " + String.valueOf(empOnlinePreviousExperience.getExpMonths()) + " Month(s)";
					to.setExperience(teachingExp);
					to.setDesignation(empOnlinePreviousExperience.getEmpDesignation());
					to.setOrganization(empOnlinePreviousExperience.getEmpOrganization());
					to.setIndustryExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					to.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					to.setFromDate(empOnlinePreviousExperience.getFromDate().toString());
					to.setToDate(empOnlinePreviousExperience.getToDate().toString());
					//
					industryExp.add(to);
				}
				
			}
		}
		downloadApplicationForm.setIndustryExperience(industryExp);
		
		return tos;
	}
}
