package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.kp.cms.forms.reports.StudentForUniversityForm;
import com.kp.cms.to.reports.CourseWithStudentTO;
import com.kp.cms.to.reports.CourseWithStudentUniversityTO;
import com.kp.cms.to.reports.SecondLanguageReportTO;
import com.kp.cms.to.reports.StudentForUniversityTO;
import com.kp.cms.to.reports.StudentVehicleTO;


public class StudentForUniversityHelper {
	/**
	 * Singleton object of ScoreSheetHandler
	 */
	private static volatile StudentForUniversityHelper studentForUniversityHelper = null;
	private static final Log log = LogFactory.getLog(StudentForUniversityHelper.class);
	private StudentForUniversityHelper() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static StudentForUniversityHelper getInstance() {
		if (studentForUniversityHelper == null) {
			studentForUniversityHelper = new StudentForUniversityHelper();
		}
		return studentForUniversityHelper;
	}
	/**
	 * creating query
	 * @param offlineIssueForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(StudentForUniversityForm studentForUniversityForm) throws Exception{
		String searchCriteria="select st.registerNo,st.admAppln.personalData.firstName," +
				"st.admAppln.personalData.middleName," +
				"st.admAppln.personalData.lastName," +
				"st.admAppln.personalData.gender," +
				"st.admAppln.personalData.category.name," +
				"st.admAppln.personalData.secondLanguage," +
				"st.admAppln.courseBySelectedCourseId.name," +
				"st.admAppln.courseBySelectedCourseId.program.name," +
				"st.admAppln.courseBySelectedCourseId.maxIntake," +
				"st.admAppln.courseBySelectedCourseId.id" +
				" from Student st " +
				" left join st.admAppln.personalData.category" ;
				
		if(studentForUniversityForm.getProgramTypeId()!=null&& !StringUtils.isEmpty(studentForUniversityForm.getProgramTypeId())){
			searchCriteria=searchCriteria+"  where st.admAppln.courseBySelectedCourseId.program.programType.id = "+studentForUniversityForm.getProgramTypeId();
		}
		if(studentForUniversityForm.getProgramId()!=null&& !StringUtils.isEmpty(studentForUniversityForm.getProgramId())){
			searchCriteria=searchCriteria+"  and st.admAppln.courseBySelectedCourseId.program.id = "+studentForUniversityForm.getProgramId();
		}
		if(studentForUniversityForm.getCourseId()!=null&& !StringUtils.isEmpty(studentForUniversityForm.getCourseId())){
			searchCriteria=searchCriteria+"  and st.admAppln.courseBySelectedCourseId.id = "+studentForUniversityForm.getCourseId();
		}
		if(studentForUniversityForm.getSemister()!=null&& !StringUtils.isEmpty(studentForUniversityForm.getSemister())){
			searchCriteria=searchCriteria+"  and st.classSchemewise.classes.termNumber = "+studentForUniversityForm.getSemister();
		}
		if(studentForUniversityForm.getYear()!=null&& !StringUtils.isEmpty(studentForUniversityForm.getYear())){
			searchCriteria=searchCriteria+" and st.admAppln.appliedYear="+studentForUniversityForm.getYear();
		}
		
		searchCriteria=searchCriteria+" and st.isAdmitted=1 order by st.admAppln.courseBySelectedCourseId.id";
		return searchCriteria;
	}
	/**
	 * converting the list of object into List of TO's
	 * @param listofStudents
	 * @return
	 * @throws Exception
	 */
	/*public List<StudentForUniversityTO> convertBotoTo(List listofStudents) throws Exception {
		List<StudentForUniversityTO> applicantDetailsList = new ArrayList<StudentForUniversityTO>();
		
		if (listofStudents != null) {
			Iterator listofStudentsItr = listofStudents.iterator();
			while (listofStudentsItr.hasNext()) {
				Object[] searchResults = (Object[]) listofStudentsItr.next();
				StudentForUniversityTO studentForUniversityTO = new StudentForUniversityTO();
				
				if(searchResults[1] == null && searchResults[2] ==null){
					studentForUniversityTO.setApplicantName(searchResults[0].toString());
				}else if(searchResults[2] == null){
					studentForUniversityTO.setApplicantName(searchResults[0].toString() +" "+ searchResults[1].toString());
				}else if(searchResults[1] == null){
					studentForUniversityTO.setApplicantName(searchResults[0].toString() +" "+ searchResults[2].toString());
				}
				else{
					studentForUniversityTO.setApplicantName(searchResults[0].toString() +" "+ searchResults[1].toString() +" "+ searchResults[2].toString());
				}
				if(searchResults[3]!=null){
					studentForUniversityTO.setGender(searchResults[3].toString());
				}
				if(searchResults[4]!=null){
					studentForUniversityTO.setCategoryName(searchResults[4].toString());
				}
				if(searchResults[5]!=null){
					studentForUniversityTO.setSecondLanguage(searchResults[5].toString());
				}
				if(searchResults[6]!=null){
					studentForUniversityTO.setCourseName((searchResults[6].toString()));
				}
				if(searchResults[7]!=null){
					studentForUniversityTO.setProgramName((searchResults[7].toString()));
				}
				if(searchResults[8]!=null){
					studentForUniversityTO.setMaxIntake((searchResults[8].toString()));
				}
				if(searchResults[9]!=null){
					studentForUniversityTO.setCourseId((searchResults[9].toString()));
				}
				applicantDetailsList.add(studentForUniversityTO);
			}
		}	
		return applicantDetailsList;
	}*/
	/**
	 * converting the list of object into List of TO's
	 * @param listofStudents
	 * @return
	 * @throws Exception
	 */
	public List<CourseWithStudentUniversityTO> convertBotoTo(List listofStudents,StudentForUniversityForm studentForUniversityForm,List<Integer> detainedStudentList) throws Exception {
		List<StudentForUniversityTO> applicantDetailsList = null;
			
		Set<Integer> courseIdSet = new HashSet<Integer>();
		StudentForUniversityTO studentForUniversityTO = null;
		int slNo= 1;
		Map<String, List<StudentForUniversityTO>> secondLanguageMapList = new HashMap<String, List<StudentForUniversityTO>>();
		if (listofStudents != null) {
			
			Iterator listofStudentsItr = listofStudents.iterator();
			
			while (listofStudentsItr.hasNext()) {
				
				Object[] searchResults = (Object[]) listofStudentsItr.next();
				if(!detainedStudentList.contains(searchResults[0])){
				String registerNo="";
				String firstName="";
				String middleName="";
				String lastName="";
				String gender="";
				String categoryName="";
				String courseName="";
				int courseId=0;
				String maxIntake="";
				String programName="";
				String secondLanguage="";
				StringBuffer applicantName = new StringBuffer();
				if(searchResults[0] !=null){
					registerNo=(String)searchResults[0];
				}
				if(searchResults[1] != null ){
					firstName=(String)searchResults[1];
				}if(searchResults[2] != null){
					middleName=(String)searchResults[2];
				}if(searchResults[3] != null){
					lastName=(String)searchResults[3];
				}
				if(searchResults[4]!=null){
					gender=(String)searchResults[4];
				}
				if(searchResults[5]!=null){
					categoryName=(String)searchResults[5];
				}
				if(searchResults[6]!=null){
					secondLanguage=(String)searchResults[6];
				}
				if(searchResults[7]!=null){
					courseName=(String)searchResults[7];
				}
				if(searchResults[8]!=null){
					programName=(String)searchResults[8];
				}
				if(searchResults[9]!=null){
					maxIntake=searchResults[9].toString();
				}
				if(searchResults[10]!=null){
					courseId=(Integer)searchResults[10];
				}
				
				//Constructs the student information based on course
				//Works for new course students
				
				if(!courseIdSet.contains(courseId)){
					applicantDetailsList = new ArrayList<StudentForUniversityTO>();
					slNo= 1;
					studentForUniversityTO = new StudentForUniversityTO();
					studentForUniversityTO.setSlNo(slNo);					
					studentForUniversityTO.setCourseName(courseName);
					studentForUniversityTO.setProgramName(programName);
					applicantName.append(firstName);
					applicantName.append(" " + middleName);
					applicantName.append(" " + lastName);
					if(!applicantName.toString().isEmpty()){
						studentForUniversityTO.setApplicantName(applicantName.toString());
					}
					studentForUniversityTO.setGender(gender);
					studentForUniversityTO.setCategoryName(categoryName);
					studentForUniversityTO.setMaxIntake(maxIntake);
					studentForUniversityTO.setSecondLanguage(secondLanguage);
					studentForUniversityTO.setRegisterNo(registerNo);
					applicantDetailsList.add(studentForUniversityTO);
					++slNo;
				}
				//Works for old course studets
				else{
					applicantDetailsList = secondLanguageMapList.get(courseName);
					studentForUniversityTO = new StudentForUniversityTO();
					studentForUniversityTO.setSlNo(slNo);			
					studentForUniversityTO.setCourseName(courseName);
					studentForUniversityTO.setProgramName(programName);
					applicantName.append(firstName);
					applicantName.append(" " + middleName);
					applicantName.append(" " + lastName);
					if(!applicantName.toString().isEmpty()){
						studentForUniversityTO.setApplicantName(applicantName.toString());
					}
					studentForUniversityTO.setGender(gender);
					studentForUniversityTO.setCategoryName(categoryName);
					studentForUniversityTO.setMaxIntake(maxIntake);
					studentForUniversityTO.setSecondLanguage(secondLanguage);
					studentForUniversityTO.setRegisterNo(registerNo);
					applicantDetailsList.add(studentForUniversityTO);
					++slNo;
				}
				secondLanguageMapList.put(courseName, applicantDetailsList);								
				courseIdSet.add(courseId);
				}
			}
		}
		List<StudentForUniversityTO> totalList= new ArrayList<StudentForUniversityTO>();
		List<CourseWithStudentUniversityTO> withStudentList= new ArrayList<CourseWithStudentUniversityTO>();
		
		if(secondLanguageMapList!=null && secondLanguageMapList.keySet()!=null && !secondLanguageMapList.keySet().isEmpty()){
			Iterator<String> secondLangMapItr=secondLanguageMapList.keySet().iterator();
			while (secondLangMapItr.hasNext()) {
				String courseName = secondLangMapItr.next();
				CourseWithStudentUniversityTO stTo= new CourseWithStudentUniversityTO();
				stTo.setCourseName(courseName);
				stTo.setStudents(secondLanguageMapList.get(courseName));
				StudentForUniversityTO universityTO = secondLanguageMapList.get(courseName).get(0);
				if(universityTO.getMaxIntake()!=null){
					stTo.setMaxIntake(universityTO.getMaxIntake());
				}
				totalList.addAll(secondLanguageMapList.get(courseName));
				withStudentList.add(stTo);
			}
		}
		studentForUniversityForm.setTotalList(totalList);
		log.info("Leaving copyStudentBOToTO of CategoryWiseStudentHelper");
		return withStudentList;
		
	}
}
