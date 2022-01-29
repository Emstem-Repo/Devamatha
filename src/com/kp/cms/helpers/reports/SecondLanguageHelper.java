package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.SecondLanguageForm;
import com.kp.cms.to.reports.CourseWithStudentTO;
import com.kp.cms.to.reports.SecondLanguageReportTO;

public class SecondLanguageHelper {
	private static final Log log = LogFactory.getLog(SecondLanguageHelper.class);
	public static volatile SecondLanguageHelper secondLanguageHelper = null;

	private SecondLanguageHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static SecondLanguageHelper getInstance() {
		if (secondLanguageHelper == null) {
			secondLanguageHelper = new SecondLanguageHelper();
		}
		return secondLanguageHelper;
	}

	/**
	 * 
	 * @param languageForm
	 * @returns query for second language report
	 */
	public String getSearchCriteria(SecondLanguageForm languageForm) throws Exception{
		log.info("entered getSearchCriteria. of SecondLanguageHelper");	
		String searchCriteria = 
		"select st.admAppln.courseBySelectedCourseId.id, "
		+ "st.admAppln.courseBySelectedCourseId.name, "
		+ "st.admAppln.applnNo, "
		+ "st.rollNo, "
		+ "st.registerNo, "
		+ "st.admAppln.personalData.firstName, "
		+ "st.admAppln.personalData.middleName, "
		+ "st.admAppln.personalData.lastName, "
		+ "st.admAppln.personalData.secondLanguage "
		+ "from FeePayment fm, Student st "
		+ "where st.admAppln.applnNo = fm.applicationNo ";
		searchCriteria = searchCriteria + commonSearch(languageForm); 
		log.info("Leaving getSearchCriteria. of SecondLanguageHelper");	
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param languageForm
	 * @returns the search Criteria
	 */
	private String commonSearch(SecondLanguageForm languageForm)throws Exception {
		log.info("entered commonSearch. of SecondLanguageHelper");	
		String commonSearch = "";
		commonSearch = commonSearch + " and st.admAppln.courseBySelectedCourseId.program.programType.id = " + languageForm.getProgramTypeId() +
					" and st.admAppln.isCancelled = 0 ";
		if(languageForm.getProgramId().length()>0){
			commonSearch = commonSearch + " and st.admAppln.courseBySelectedCourseId.program.id = " + languageForm.getProgramId();
		}
		if(languageForm.getCourseId().length()>0){
			commonSearch = commonSearch + " and st.admAppln.courseBySelectedCourseId.id = " + languageForm.getCourseId();
		}
		if(languageForm.getSemister()!= null && !languageForm.getSemister().trim().isEmpty()){
			commonSearch = commonSearch + " and st.classSchemewise.classes.termNumber = " + languageForm.getSemister();
		}
		
		if(!languageForm.getSecondLanguage().equals("All")){
			commonSearch = commonSearch + " and st.admAppln.personalData.secondLanguage = " + "'" + languageForm.getSecondLanguage() + "'";
		}
		if(languageForm.getFeeStatus().equals("1")){
			commonSearch = commonSearch + " and fm.isFeePaid = 1 ";
		}
		else if(languageForm.getFeeStatus().equals("2")){
			commonSearch = commonSearch + " and fm.isChallenPrinted = 1 and fm.isFeePaid = 0 ";
		}
		else if(languageForm.getFeeStatus().equals("3")){
			commonSearch = commonSearch + " and fm.isFeePaid = 1 ";
		}
		commonSearch = commonSearch + " order by st.admAppln.courseBySelectedCourseId.id";
		log.info("Leaving commonSearch. of SecondLanguageHelper");	
		return commonSearch;
	}
	
	/**
	 * 
	 * @param studentList
	 * @returns student List
	 */
	
	public List<CourseWithStudentTO> copyStudentBOsToTO(List<Object[]> studentBOList, SecondLanguageForm languageForm) throws Exception{
		log.info("entered copyStudentBOsToTO. of SecondLanguageHelper");	
		List<SecondLanguageReportTO> studentList =null;
		List<String> dupList=new ArrayList<String>();
		
		Set<Integer> courseIdSet = new HashSet<Integer>();
		SecondLanguageReportTO studentTO = null;
		Map<String, List<SecondLanguageReportTO>> secondLanguageMapList = new HashMap<String, List<SecondLanguageReportTO>>();
		int slNo= 1;
		
		if(studentBOList!=null && !studentBOList.isEmpty()){			
			Iterator<Object[]> studentIterator = studentBOList.iterator();
			while (studentIterator.hasNext()) {
				
				int courseId = 0;
				String courseName = "";				
				int applicationNo = 0;								
				String rollNo = "";				
				String registerNo = "";
				String firstName ="";
				String middleName = "";
				String lastName = "";
				String secondLanguage = "";
				StringBuffer studentName = new StringBuffer();
				
				Object[] object = studentIterator.next();
				if(object[0]!=null){
					courseId = (Integer)object[0];
				}
				if(object[1]!=null){
					courseName = (String) object[1];
				}
				if(object[2]!=null){
					applicationNo = (Integer)object[2];
				}
				if(object[3]!=null){
					rollNo = (String) object[3];
				}
				if(object[4]!=null){
					registerNo = (String) object[4];
				}
				if(object[5]!=null){
					firstName = (String)object[5];
				}
				if(object[6]!=null){
					middleName = (String)object[6];
				}
				if(object[7]!=null){
					lastName = (String)object[7];
				}
				if(object[8]!=null){
					secondLanguage = (String) object[8];
				}
				//Constructs the student information based on course
				//Works for new course students
				if(!courseIdSet.contains(courseId)){
					studentList = new ArrayList<SecondLanguageReportTO>();
					slNo= 1;
					studentTO = new SecondLanguageReportTO();
					studentTO.setSlNo(slNo);					
					studentTO.setCourseName(courseName);
					studentTO.setApplicationNo(String.valueOf(applicationNo));
					studentTO.setRegisterNo(registerNo);
					studentTO.setRollNo(rollNo);
					studentName.append(firstName);
					studentName.append(" " + middleName);
					studentName.append(" " + lastName);
					if(studentName != null){
						studentTO.setStudentName(studentName.toString());
					}
					studentTO.setSecondLanguage(secondLanguage);
					if(!dupList.contains(String.valueOf(applicationNo))){
						dupList.add(String.valueOf(applicationNo));
						studentList.add(studentTO);
						++slNo;
					}
				}
				//Works for old course studets
				else{
					studentList = secondLanguageMapList.get(courseName);
					studentTO = new SecondLanguageReportTO();
					studentTO.setSlNo(slNo);
					studentTO.setSlNo(slNo);					
					studentTO.setCourseName(courseName);
					studentTO.setApplicationNo(String.valueOf(applicationNo));
					studentTO.setRegisterNo(registerNo);
					studentTO.setRollNo(rollNo);
					studentName.append(firstName);
					studentName.append(" " + middleName);
					studentName.append(" " + lastName);
					if(studentName != null){
						studentTO.setStudentName(studentName.toString());
					}
					studentTO.setSecondLanguage(secondLanguage);
					if(!dupList.contains(String.valueOf(applicationNo))){
						dupList.add(String.valueOf(applicationNo));
						studentList.add(studentTO);
						++slNo;
					}
				}								
				secondLanguageMapList.put(courseName, studentList);								
				courseIdSet.add(courseId);				
			}
		}
		List<SecondLanguageReportTO> totalList= new ArrayList<SecondLanguageReportTO>();
		List<CourseWithStudentTO> withStudentList= new ArrayList<CourseWithStudentTO>();
		
		if(secondLanguageMapList!=null && secondLanguageMapList.keySet()!=null && !secondLanguageMapList.keySet().isEmpty()){
			Iterator<String> secondLangMapItr=secondLanguageMapList.keySet().iterator();
			while (secondLangMapItr.hasNext()) {
				String courseName = secondLangMapItr.next();
				CourseWithStudentTO stTo= new CourseWithStudentTO();
				stTo.setCourseName(courseName);
				stTo.setStudents(secondLanguageMapList.get(courseName));
				totalList.addAll(secondLanguageMapList.get(courseName));
				withStudentList.add(stTo);
			}
		}
		languageForm.setTotalStudentList(totalList);
		log.info("Leaving copyStudentBOToTO of CategoryWiseStudentHelper");
		return withStudentList;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param languageForm
	 * @returns query for second language report
	 */
	public String getSearchCriteria1(SecondLanguageForm languageForm) throws Exception{
		log.info("entered getSearchCriteria. of SecondLanguageHelper");	
		String searchCriteria = 
		"select st.admAppln.courseBySelectedCourseId.id, "
		+ "st.admAppln.courseBySelectedCourseId.name, "
		+ "st.admAppln.applnNo, "
		+ "st.rollNo, "
		+ "st.registerNo, "
		+ "st.admAppln.personalData.firstName, "
		+ "st.admAppln.personalData.middleName, "
		+ "st.admAppln.personalData.lastName, "
		+ "st.admAppln.personalData.secondLanguage "
		+ "from Student st ";
		if(languageForm.getFeeStatus().equals("1")){
			searchCriteria = searchCriteria + " left join st.feePayments fm with fm.isFeePaid=1 ";
		}
		else if(languageForm.getFeeStatus().equals("2")){
			searchCriteria = searchCriteria + " left join st.feePayments fm with fm.isChallenPrinted = 1 and fm.isFeePaid = 0 ";
		}
//		else if(languageForm.getFeeStatus().equals("3")){
//			searchCriteria = searchCriteria + " and fm.isFeePaid = 1 ";
//		}
		searchCriteria = searchCriteria + commonSearch1(languageForm); 
		log.info("Leaving getSearchCriteria. of SecondLanguageHelper");	
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param languageForm
	 * @returns the search Criteria
	 */
	private String commonSearch1(SecondLanguageForm languageForm)throws Exception {
		log.info("entered commonSearch. of SecondLanguageHelper");	
		String commonSearch = "";
		commonSearch = commonSearch + " where st.admAppln.courseBySelectedCourseId.program.programType.id = " + languageForm.getProgramTypeId() +
					" and st.admAppln.isCancelled = 0 and st.admAppln.appliedYear='"+languageForm.getYear()+"'";
		if(languageForm.getProgramId().length()>0){
			commonSearch = commonSearch + " and st.admAppln.courseBySelectedCourseId.program.id = " + languageForm.getProgramId();
		}
		if(languageForm.getCourseId().length()>0){
			commonSearch = commonSearch + " and st.admAppln.courseBySelectedCourseId.id = " + languageForm.getCourseId();
		}
		if(languageForm.getSemister()!= null && !languageForm.getSemister().trim().isEmpty()){
			commonSearch = commonSearch + " and st.classSchemewise.classes.termNumber = " + languageForm.getSemister();
		}
		
		if(!languageForm.getSecondLanguage().equals("All")){
			commonSearch = commonSearch + " and st.admAppln.personalData.secondLanguage = " + "'" + languageForm.getSecondLanguage() + "'";
		}
		commonSearch = commonSearch + " order by st.admAppln.courseBySelectedCourseId.id";
		log.info("Leaving commonSearch. of SecondLanguageHelper");	
		return commonSearch;
	}
}
