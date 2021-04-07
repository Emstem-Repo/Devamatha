package com.kp.cms.helpers.employee;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.actions.employee.DownloadEmployeeResumeAction;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.transactionsimpl.employee.DownloadEmployeeResumeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class DownloadEmployeeResumeHelper {
	/**
	 * Singleton object of EmployeeApplyLeaveHelper
	 */
	private static volatile DownloadEmployeeResumeHelper downloadEmployeeResumeHelper = null;
	private static final Log log = LogFactory.getLog(DownloadEmployeeResumeHelper.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private DownloadEmployeeResumeHelper() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHelper.
	 * @return
	 */
	public static DownloadEmployeeResumeHelper getInstance() {
		if (downloadEmployeeResumeHelper == null) {
			downloadEmployeeResumeHelper = new DownloadEmployeeResumeHelper();
		}
		return downloadEmployeeResumeHelper;
	}
	public List<DownloadEmployeeResumeTO> convertBoToTo( List<EmpOnlineResume> empOnlineResumes, Map<Integer, String> usersMap)throws Exception {
		List<DownloadEmployeeResumeTO> empResumeList = new ArrayList<DownloadEmployeeResumeTO>();
		int count=1;
		if(empOnlineResumes != null && !empOnlineResumes.isEmpty()){
			Iterator<EmpOnlineResume> iterator = empOnlineResumes.iterator();
			while (iterator.hasNext()) {
				EmpOnlineResume empOnlineResume = (EmpOnlineResume) iterator.next();
				DownloadEmployeeResumeTO to = new DownloadEmployeeResumeTO();
				to.setCount(count);
				if(empOnlineResume.getId() != 0){
					to.setId(empOnlineResume.getId());
				}
				if(empOnlineResume.getDepartment() != null ){
					if(empOnlineResume.getDepartment().getName() != null && !empOnlineResume.getDepartment().getName().isEmpty()){
						to.setDepartmentName(empOnlineResume.getDepartment().getName());
					}
				}
				if(empOnlineResume.getPostAppliedDesig() != null ){
					to.setDesignationName(empOnlineResume.getPostAppliedDesig());
				}
				if(empOnlineResume.getName() != null && !empOnlineResume.getName().isEmpty()){
					to.setEmployeeName(empOnlineResume.getName());
				}
				if(empOnlineResume.getApplicationNo() != null && !empOnlineResume.getApplicationNo().isEmpty()){
					to.setApplicationNO(empOnlineResume.getApplicationNo());
				}
				if(empOnlineResume.getEmpQualificationLevel() != null){
					if(empOnlineResume.getEmpQualificationLevel().getName() != null && !empOnlineResume.getEmpQualificationLevel().getName().isEmpty()){
						to.setQualificationId(empOnlineResume.getEmpQualificationLevel().getName());
					}
				}
				if(empOnlineResume.getEmail() != null && !empOnlineResume.getEmail().isEmpty()){
					to.setMailId(empOnlineResume.getEmail());
				}
				if(empOnlineResume.getDesiredPost() != null && !empOnlineResume.getDesiredPost().isEmpty()){
					to.setPostAppliedFor(empOnlineResume.getDesiredPost());
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
				if(empOnlineResume.getMobileNo1() != null && !empOnlineResume.getMobileNo1().isEmpty()){
					to.setMobileNumber(empOnlineResume.getMobileNo1());
				}
				if(empOnlineResume.getDateOfSubmission() != null ){
					String subDate = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResume.getDateOfSubmission()), DownloadEmployeeResumeHelper.SQL_DATEFORMAT,DownloadEmployeeResumeHelper.FROM_DATEFORMAT);
					to.setSubmitedDate(subDate);
				}
				if(empOnlineResume.getEligibilityTest() != null && !empOnlineResume.getEligibilityTest().isEmpty()){
					to.setEligibilityTest(empOnlineResume.getEligibilityTest());
				}
				if(empOnlineResume.getGender() != null && !empOnlineResume.getGender().isEmpty()){
					to.setGender(empOnlineResume.getGender());
				}
				if(empOnlineResume.getDateOfBirth() != null){
					String dob = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResume.getDateOfBirth()), DownloadEmployeeResumeHelper.SQL_DATEFORMAT,DownloadEmployeeResumeHelper.FROM_DATEFORMAT);
					to.setDateOfBirth(dob);
					to.setAge(getAge(empOnlineResume.getDateOfBirth()));
				}
				String totalExp="";
				if(empOnlineResume.getTotalExpYear()!=null && empOnlineResume.getTotalExpYear()!=0){
					if(empOnlineResume.getTotalExpYear()!=1)
					    totalExp = String.valueOf(empOnlineResume.getTotalExpYear())+" Year(s)";
					else
						totalExp = String.valueOf(empOnlineResume.getTotalExpYear())+" Year ";
				}
				else
					totalExp = " 0 Year ";
				if(empOnlineResume.getTotalExpMonths()!=null && empOnlineResume.getTotalExpMonths()!=0){
					if(empOnlineResume.getTotalExpMonths()!=1)
					    totalExp = totalExp + String.valueOf(empOnlineResume.getTotalExpMonths())+" Month(s)";
					else
						totalExp = totalExp + String.valueOf(empOnlineResume.getTotalExpMonths())+" Month ";
				}
				else
					totalExp = totalExp + " 0 Month";
				to.setTotalExp(totalExp);
				/*if(empOnlineResume.getTotalExpMonths() != null && empOnlineResume.getTotalExpYear() != null && empOnlineResume.getTotalExpYear()!=0 && empOnlineResume.getTotalExpMonths()!=0){
					to.setTotalExp(String.valueOf(empOnlineResume.getTotalExpYear())+" Year(s) "+String.valueOf(empOnlineResume.getTotalExpMonths())+" Month(s)");
				}else{
					to.setTotalExp(String.valueOf(empOnlineResume.getTotalExpYear())+" Year "+String.valueOf(empOnlineResume.getTotalExpMonths())+" Month");
				}*/
				if(empOnlineResume.getEducationalDetailsSet() != null && !empOnlineResume.getEducationalDetailsSet().isEmpty()){
					StringBuilder course = new StringBuilder();
					String highQualification="";
					int highNumber=0;
					Map<Integer,String> courseMap=new HashMap<Integer, String>();
					Set<EmpOnlineEducationalDetails> educationalDetailsSet = empOnlineResume.getEducationalDetailsSet();
					Iterator<EmpOnlineEducationalDetails> iterator2 = educationalDetailsSet.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineEducationalDetails empOnlineEducationalDetails = (EmpOnlineEducationalDetails) iterator2.next();
						if(empOnlineEducationalDetails.getCourse()!=null)
						      courseMap.put(empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder(), empOnlineEducationalDetails.getCourse());
						if(empOnlineEducationalDetails.getEmpQualificationLevel() != null && empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder() != null){
							if(highNumber < empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder()){
								highNumber = empOnlineEducationalDetails.getEmpQualificationLevel().getDisplayOrder();
								highQualification = empOnlineEducationalDetails.getEmpQualificationLevel().getName();
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
				if(empOnlineResume.getOtherInfo()!=null)
					to.setOtherInfo(empOnlineResume.getOtherInfo());
				if(empOnlineResume.getNoOfPublicationsRefered()!=null)
					to.setNoOfPublicationsRefered(empOnlineResume.getNoOfPublicationsRefered());
				/*if(empOnlineResume.getAge() != null){
					to.setAge(getAge(empOnlineResume.getDateOfBirth()));
				}*/
				if(empOnlineResume.getDateOfSubmission() != null){
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResume.getDateOfSubmission()), DownloadEmployeeResumeHelper.SQL_DATEFORMAT,DownloadEmployeeResumeHelper.FROM_DATEFORMAT);
					to.setDateOfApplication(date);
				}
				if(empOnlineResume.getStatus()!=null){
					to.setCurrentStatus(empOnlineResume.getStatus());
				}
				if(empOnlineResume.getStatusDate()!=null){
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResume.getStatusDate()), DownloadEmployeeResumeHelper.SQL_DATEFORMAT,DownloadEmployeeResumeHelper.FROM_DATEFORMAT);
					to.setCurrentDate(date);
				}
				if(usersMap!=null && !usersMap.isEmpty()){
					if(usersMap.containsKey(to.getId())){
						String userNames = usersMap.get(to.getId());
						String[] a = userNames.split(",");
						String name="<table><th align='left'>Forwarded To:</th><tr height='5'></tr>";
						int count1 =0;
						for(int i=0;i<a.length;i++){
							count1=count1+1;
							name=name+"<tr><td align='left'>"+count1+".&nbsp;&nbsp;"+a[i]+"</td><tr>";
						}
						name = name + "</table>";
						to.setForwardedUsers(name);
					}
				}
				if(empOnlineResume.getEmpSubject()!=null){
					to.setEmpSubjectName(empOnlineResume.getEmpSubject().getSubjectName());
				}
				empResumeList.add(to);
				count++;
			}
		}
		return empResumeList;
	}
	/**
	 * @param empOnlineResume
	 * @param teachingExperience 
	 * @param downloadEmployeeResumeForm 
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public List<DownloadEmployeeResumeTO> convertBoToToForPrint(EmpOnlineResume empOnlineResume, List<EmpOnlinePreviousExperience> teachingExperience, DownloadEmployeeResumeForm downloadEmployeeResumeForm,HttpSession session
		) throws Exception{
		
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
					// code added by sudhir
					expTo.setTeachingExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					expTo.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					if(empOnlinePreviousExperience.getFromDate()!=null)
					     expTo.setFromDate(empOnlinePreviousExperience.getFromDate().toString());
					if(empOnlinePreviousExperience.getFromDate()!=null)
					     expTo.setToDate(empOnlinePreviousExperience.getToDate().toString());
					//
				}
				if(empOnlinePreviousExperience.isIndustryExperience()){
					expTo.setDesignation(empOnlinePreviousExperience.getEmpDesignation());
					expTo.setOrganization(empOnlinePreviousExperience.getEmpOrganization());
					// code added by sudhir
					expTo.setIndustryExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					expTo.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					if(empOnlinePreviousExperience.getFromDate()!=null)
					     expTo.setFromDate(empOnlinePreviousExperience.getFromDate().toString());
					if(empOnlinePreviousExperience.getFromDate()!=null)
					     expTo.setToDate(empOnlinePreviousExperience.getToDate().toString());
					//
				}
				teachingExpTos.add(expTo);
				industryExpTos.add(expTo);
			}
		}
		downloadEmployeeResumeForm.setTeachingExperience(teachingExpTos);
		downloadEmployeeResumeForm.setTeachingExperience(industryExpTos);
		
		
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
			downloadEmployeeResumeForm.setPublicationDetailsPresent(publicationDetailsPresent);
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
			/*String eligibilityTest="";
			if(empOnlineResume.getEligibilityTest() != null){
				eligibilityTest=empOnlineResume.getEligibilityTest();
			}
			if(empOnlineResume.getEligibilityTestOther()!=null && !empOnlineResume.getEligibilityTestOther().isEmpty()){
				if(!eligibilityTest.isEmpty())
					eligibilityTest=eligibilityTest+","+eligibilityTest.replace("OTHER", empOnlineResume.getEligibilityTestOther());
				else
					eligibilityTest=empOnlineResume.getEligibilityTestOther();
			}
			to.setEligibilityTest(!eligibilityTest.isEmpty()?eligibilityTest:"");*/
			
			if(!empOnlineResume.getEligibilityTest().equalsIgnoreCase("None")){
				String eligibilityTest = "";
				if(empOnlineResume.getEligibilityTest().contains("OTHER")){
					if(empOnlineResume.getEligibilityTestOther()!=null && !empOnlineResume.getEligibilityTestOther().isEmpty()){
						if(empOnlineResume.getEligibilityTest().contains("None")){
							eligibilityTest = empOnlineResume.getEligibilityTest();
						}
					eligibilityTest = eligibilityTest+ empOnlineResume.getEligibilityTest().replace("OTHER", empOnlineResume.getEligibilityTestOther());
					}else{
						eligibilityTest = empOnlineResume.getEligibilityTest().replace(",OTHER", "");
					}
				}else{
					if(empOnlineResume.getEligibilityTest().contains("None")){
						eligibilityTest = empOnlineResume.getEligibilityTest().replace("None,", "");
					}else{
						eligibilityTest = empOnlineResume.getEligibilityTest();
					}
					
				}
				to.setEligibilityTest(eligibilityTest);
			}else{
				to.setEligibilityTest("None");
			}
			
			if(empOnlineResume.getOtherInfo() != null && !empOnlineResume.getOtherInfo().isEmpty()){
				to.setOtherInfo(empOnlineResume.getOtherInfo());
			}
			//new additions by Smitha
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
				downloadEmployeeResumeForm.setApplicationNo(empOnlineResume.getApplicationNo());
			}
			if(empOnlineResume.getDateOfSubmission() != null){
//				String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empOnlineResume.getDateOfSubmission()), DownloadEmployeeResumeHelper.SQL_DATEFORMAT,DownloadEmployeeResumeHelper.FROM_DATEFORMAT);
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
			if(empOnlineResume.getConsultancy1SponseredProjects()!=null){
				to.setConsultancy1SponseredProjects(empOnlineResume.getConsultancy1SponseredProjects().toString());
			}
			if(empOnlineResume.getConsultancy2SponseredProjects()!=null){
				to.setConsultancy2SponseredProjects(empOnlineResume.getConsultancy2SponseredProjects().toString());
			}
			if(empOnlineResume.getPhdResearchGuidance()!=null){
				to.setPhdResearchGuidance(empOnlineResume.getPhdResearchGuidance().toString());
			}
			if(empOnlineResume.getMphilResearchGuidance()!=null){
				to.setMphilResearchGuidance(empOnlineResume.getMphilResearchGuidance().toString());
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
				to.setEmpSubject(empOnlineResume.getEmpSubject().getSubjectName());
			}
			empResumeList.add(to);
		}
		
		return empResumeList;
	}
	/**
	 * @param eduDetails
	 * @param downloadEmployeeResumeForm
	 * @return
	 */
	public List<EmpOnlineEducationalDetailsTO> convertBoToToForEduDetails(List<EmpOnlineEducationalDetails> eduDetails, DownloadEmployeeResumeForm downloadEmployeeResumeForm) {
		
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
			downloadEmployeeResumeForm.setAdditionalQualification(additionalQualification);
		}
		return educationalDetails;
	}
	/**
	 * @param teachingExperience
	 * @param empOnlineResume
	 * @param downloadEmployeeResumeForm
	 * @return
	 */
	public List<EmpOnlinePreviousExperienceTO> convertBoToToForExpDetails(List<EmpOnlinePreviousExperience> teachingExperience, EmpOnlineResume empOnlineResume, DownloadEmployeeResumeForm downloadEmployeeResumeForm) {
		
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
					//code added by sudhir
					to.setTeachingExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					to.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					if(empOnlinePreviousExperience.getFromDate()!=null)
						to.setFromDate(CommonUtil.formatSqlDate1(empOnlinePreviousExperience.getFromDate().toString()));
					if(empOnlinePreviousExperience.getToDate()!=null)
						to.setToDate(CommonUtil.formatSqlDate1(empOnlinePreviousExperience.getToDate().toString()));
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
					//code added by sudhir
					to.setIndustryExpYears(String.valueOf(empOnlinePreviousExperience.getExpYears()));
					to.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperience.getExpMonths()));
					if(empOnlinePreviousExperience.getFromDate()!=null)
						to.setFromDate(CommonUtil.formatSqlDate1(empOnlinePreviousExperience.getFromDate().toString()));
					if(empOnlinePreviousExperience.getToDate()!=null)
						to.setToDate(CommonUtil.formatSqlDate1(empOnlinePreviousExperience.getToDate().toString()));
					//
					industryExp.add(to);
				}
				
			}
		}
		downloadEmployeeResumeForm.setIndustryExperience(industryExp);
		
		return tos;
	}
	public String getAge(Date dateOfBirth){
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		int dobYear=Integer.parseInt(format.format(dateOfBirth));
		int currentYear=Integer.parseInt(format.format(new Date()));
		int age=currentYear-dobYear;
		/*Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dateOfBirth);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		int age=(int)daysBetween/365;
*/		return String.valueOf(age);
	}
	
	public DownloadEmployeeResumeTO convertOnlineResumeToTO(EmpOnlineResume empOnlineResume){
		DownloadEmployeeResumeTO empResumeTO=null;
		if(empOnlineResume!=null){
			empResumeTO = new DownloadEmployeeResumeTO();
		    if(empOnlineResume.getName()!=null){
			   empResumeTO.setEmployeeName(empOnlineResume.getName());
		    }
		    if(empOnlineResume.getPostAppliedDesig()!=null){
			   empResumeTO.setPostAppliedFor(empOnlineResume.getPostAppliedDesig());
		    }
		    if(empOnlineResume.getDepartment()!=null){
			   empResumeTO.setDepartmentName(empOnlineResume.getDepartment().getName());
		    }
		    if(empOnlineResume.getStatus()!=null){
			   empResumeTO.setCurrentStatus(empOnlineResume.getStatus());
		    }
		}
		return empResumeTO;
	}
	/**
	 * @param usersDetailsList
	 * @param downloadEmployeeResumeForm 
	 * @return
	 * @throws Exception
	 */
	public void getUsersMap(List<Object[]> usersDetailsList, DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception{
		Map<Integer,String> usersMap = new HashMap<Integer, String>();
		Map<Integer,String> usersMapWithEmails = new HashMap<Integer, String>();
		if(usersDetailsList!=null && !usersDetailsList.isEmpty()){
			Iterator<Object[]> iterator  = usersDetailsList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				Users users = (Users) objects[1];
				if(users.getEmployee() != null && users.getEmployee().getFirstName() != null && users.getEmployee().getDepartment() != null && users.getEmployee().getDepartment().getName() != null){
					usersMap.put(users.getId(), users.getEmployee().getFirstName()+"("+users.getEmployee().getDepartment().getName()+")");
				}else if(users.getEmployee() != null && users.getEmployee().getFirstName() != null){
					usersMap.put(users.getId(), users.getEmployee().getFirstName());
				}else if(users.getDepartment() != null && users.getDepartment().getName() != null ){
					usersMap.put(users.getId(), users.getUserName().toUpperCase()+"("+users.getDepartment().getName()+")");
				}else{
					usersMap.put(users.getId(), users.getUserName().toUpperCase());
				}
				/*putting users mailIds in another Map*/
				if(users.getEmployee()!=null && users.getEmployee().getWorkEmail()!=null){
					usersMapWithEmails.put(users.getId(), users.getEmployee().getWorkEmail());
				}else if(users.getGuest()!=null && users.getGuest().getEmail()!=null){
					usersMapWithEmails.put(users.getId(),users.getGuest().getEmail());
				}else {
					usersMapWithEmails.put(users.getId(), null);
				}
			}
			if(usersMap!=null && !usersMap.isEmpty()){
				usersMap = (Map<Integer, String>) CommonUtil.sortMapByValue(usersMap);
				downloadEmployeeResumeForm.setUsersMap(usersMap);
			}
			if(usersMapWithEmails!=null && !usersMapWithEmails.isEmpty()){
				downloadEmployeeResumeForm.setUsersMapWithEmails(usersMapWithEmails);
			}
		  }
		}
	/**
	 * @param downloadEmployeeResumeForm
	 * @param userIds
	 * @return
	 */
	public boolean saveForwardedEmpDetails( DownloadEmployeeResumeForm downloadEmployeeResumeForm, String[] userIds) throws Exception{
		boolean isSent = false;
		List<DownloadEmployeeResumeTO> tos = downloadEmployeeResumeForm.getDownloadEmployeeResumeTOs();
		if(tos!=null && !tos.isEmpty()){
			Iterator<DownloadEmployeeResumeTO> iterator = tos.iterator();
			while (iterator.hasNext()) {
				DownloadEmployeeResumeTO downloadEmployeeResumeTO = (DownloadEmployeeResumeTO) iterator .next();
				if(downloadEmployeeResumeTO.getChecked() != null && !downloadEmployeeResumeTO.getChecked().isEmpty()){
					if(downloadEmployeeResumeTO.getChecked().equalsIgnoreCase("on")){
						IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
						int empOnlineResumeId = downloadEmployeeResumeTO.getId();
						String appNo=downloadEmployeeResumeTO.getApplicationNO();
						isSent=transaction.saveEmpOnlineResumeUsersDetails(userIds,empOnlineResumeId,downloadEmployeeResumeForm);
						transaction.setStatus(Integer.parseInt(appNo));
					}
				}
			}
		}
		return isSent;
	}
	/**
	 * @param empOnlineResumeUsers
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> populateBOListToMap( List<EmpOnlineResumeUsers> empOnlineResumeUsers) throws Exception{
		Map<Integer, String> map = new HashMap<Integer, String>();
		if(empOnlineResumeUsers!=null && !empOnlineResumeUsers.isEmpty()){
			Iterator<EmpOnlineResumeUsers> iterator = empOnlineResumeUsers.iterator();
			while (iterator.hasNext()) {
				EmpOnlineResumeUsers bo = (EmpOnlineResumeUsers) iterator .next();
				if(map.containsKey(bo.getOnlineResume().getId())){
					String userNames = map.get(bo.getOnlineResume().getId());
					 if(bo.getUsers().getEmployee() != null && bo.getUsers().getEmployee().getFirstName() != null){
						 userNames = userNames +","+bo.getUsers().getEmployee().getFirstName()+" ("+CommonUtil.formatDates(bo.getCreatedDate())+")";
					}else{
						 userNames = userNames +","+bo.getUsers().getUserName().toUpperCase()+" ("+CommonUtil.formatDates(bo.getCreatedDate())+")";
					}
					 map.put(bo.getOnlineResume().getId(), userNames);
				}else{
					String userNames = "";
					if(bo.getUsers().getEmployee() != null && bo.getUsers().getEmployee().getFirstName() != null){
						 userNames =bo.getUsers().getEmployee().getFirstName()+" ("+CommonUtil.formatDates(bo.getCreatedDate())+")";
					}else{
						 userNames =bo.getUsers().getUserName().toUpperCase()+" ("+CommonUtil.formatDates(bo.getCreatedDate())+")";
					}
					 map.put(bo.getOnlineResume().getId(), userNames);
				}
			}
		}
		return map;
		}
	}
