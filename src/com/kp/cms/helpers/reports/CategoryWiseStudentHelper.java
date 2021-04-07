package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CategoryWiseStudentForm;
import com.kp.cms.to.admin.CategoryWiseStudentTO;
import com.kp.cms.to.admin.CategoryWithStudentsTO;

public class CategoryWiseStudentHelper {
	private static final Log log = LogFactory.getLog(CategoryWiseStudentHelper.class);
	public static volatile CategoryWiseStudentHelper self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static CategoryWiseStudentHelper getInstance(){
		if(self==null)
			self= new CategoryWiseStudentHelper();
		return self;
	}
	/**
	 * Private constructor for a singleton class
	 */
	private CategoryWiseStudentHelper(){		
	}

	/**
	 * Used to prepare the query to based on the selection
	 */
	public String commonSearch(CategoryWiseStudentForm studentForm)throws Exception{
		log.info("Entering commonSearch of CategoryWiseStudentHelper");
		String searchCriteria = " ";
		
		if(studentForm.getSemister()!= null && !studentForm.getSemister().trim().isEmpty() && studentForm.getSemister().trim().length() > 0){
			if(studentForm.getAcademicYear().trim().length() > 0 && studentForm.getAcademicYear()!= null && !studentForm.getAcademicYear().trim().isEmpty()){
				String semister = " st.classSchemewise.curriculumSchemeDuration.semesterYearNo = " + studentForm.getSemister()+" and st.admAppln.appliedYear = " + Integer.parseInt(studentForm.getAcademicYear());
				searchCriteria = searchCriteria + semister;
			}else{
			Calendar cal= Calendar.getInstance();
			cal.setTime(new Date());
			int year=Integer.valueOf(cal.get(cal.YEAR));
			String semister = " st.classSchemewise.curriculumSchemeDuration.semesterYearNo = " + studentForm.getSemister()+" and st.admAppln.appliedYear="+year;
			searchCriteria = searchCriteria + semister;
			}
		}else
			if(studentForm.getAcademicYear().trim().length() > 0 && studentForm.getAcademicYear()!= null && !studentForm.getAcademicYear().trim().isEmpty()){
				if(!searchCriteria.trim().isEmpty()){
					searchCriteria = searchCriteria + " and ";
				}
				String year = " st.admAppln.appliedYear = " + Integer.parseInt(studentForm.getAcademicYear());
				searchCriteria = searchCriteria + year;
			}
		if(studentForm.getCourseId().trim().length() > 0){
			if(!searchCriteria.trim().isEmpty()){
				searchCriteria = searchCriteria + " and ";
			}
			String course = " st.admAppln.courseBySelectedCourseId.id = " + studentForm.getCourseId();
			searchCriteria = searchCriteria + course;
		}
		if(studentForm.getProgramId().trim().length() > 0){
			if(!searchCriteria.trim().isEmpty()){
				searchCriteria = searchCriteria + " and ";
			}
			String program = " st.admAppln.courseBySelectedCourseId.program.id = " + studentForm.getProgramId();
			searchCriteria = searchCriteria + program;
		}
		if(studentForm.getProgramTypeId().trim().length() > 0){
			if(!searchCriteria.trim().isEmpty()){
				searchCriteria = searchCriteria + " and ";
			}
			String programType = " st.admAppln.courseBySelectedCourseId.program.programType.id = " + studentForm.getProgramTypeId();
			searchCriteria = searchCriteria + programType;
		}
		searchCriteria = searchCriteria + " and st.admAppln.admittedThrough.isActive = 1 and st.admAppln.isCancelled = 0 order by st.admAppln.admittedThrough.id";
		log.info("Leaving commonSearch of CategoryWiseStudentHelper");
		return searchCriteria;
	}
	
	/**
	 * Used to prepare the query
	 */
	
	public String getAdmittedStudents(CategoryWiseStudentForm studentForm)throws Exception{
		log.info("Entering getAdmittedStudents of CategoryWiseStudentHelper");
		String statusCriteria = commonSearch(studentForm);
		String searchCriteria = "";
		
		searchCriteria = "select st.admAppln.admittedThrough.id, " 
		+ " st.admAppln.admittedThrough.name,"
		+ " st.admAppln.applnNo," 
		+ " st.registerNo," + " st.rollNo," 
		+ " st.admAppln.personalData.firstName," 
		+ " st.admAppln.personalData.middleName," 
		+ " st.admAppln.personalData.lastName," 
		+ " st.admAppln.personalData.gender" 
		+ " from Student st" +" where"
		+ statusCriteria;
		log.info("Leaving getAdmittedStudents of CategoryWiseStudentHelper");
		return searchCriteria;
	}
	
	/**
	 * Coverts Student BO properties to Student TO property
	 * @param studentForm 
	 */
	public List<CategoryWithStudentsTO> copyStudentBOToTO(List<Student> studentBOList, CategoryWiseStudentForm studentForm)throws Exception{
		log.info("Entering copyStudentBOToTO of CategoryWiseStudentHelper");
		List<CategoryWiseStudentTO> studentList =null;
		
		Set<Integer> admittedIdSet = new HashSet<Integer>();
		CategoryWiseStudentTO studentTO = null;
		Map<String, List<CategoryWiseStudentTO>> categoryMapList = new HashMap<String, List<CategoryWiseStudentTO>>();
		int slNo= 1;
		
		if(studentBOList!=null && !studentBOList.isEmpty()){			
			Iterator studentIterator = studentBOList.iterator();
			while (studentIterator.hasNext()) {
				
				int admittedThroughId = 0;
				String admittedThroughName = "";				
				int applicationNo = 0;								
				String registerNo = "";
				String rollNo = "";				
				String firstName ="";
				String middleName = "";
				String lastName = "";
				StringBuffer studentName = new StringBuffer();
				String gender = "";	
				
				Object[] object = (Object[]) studentIterator.next();
				admittedThroughId = (Integer)object[0];
				admittedThroughName = (String) object[1];
				applicationNo = (Integer)object[2];
				registerNo = (String) object[3];
				rollNo = (String) object[4];
				firstName = (String)object[5];
				middleName = (String)object[6];
				lastName = (String)object[7];
				gender = (String) object[8];
				//Constructs the student information based on admittedThrough
				//Works for new admitted through students
				if(!admittedIdSet.contains(admittedThroughId)){
					studentList = new ArrayList<CategoryWiseStudentTO>();
					slNo= 1;
					studentTO = new CategoryWiseStudentTO();
					studentTO.setSlNo(slNo);
					if(admittedThroughName!=null){
						studentTO.setAdmittedThroughCode(admittedThroughName);
					}
					if(applicationNo!=0){
						studentTO.setApplicationNo(applicationNo);
					}
					if(registerNo != null){
						studentTO.setRegisterNo(registerNo);
					}
					if(rollNo != null){
						studentTO.setRollNo(rollNo);
					}
					if(firstName != null){
						studentName.append(firstName);
					}
					if(middleName != null){
						studentName.append(" " + middleName);
					}
					if(lastName != null){
						studentName.append(" " + lastName);
					}
					if(studentName != null){
						studentTO.setStudentName(studentName.toString());
					}
					
					if(gender!=null){
						studentTO.setGender(gender);
					}
					studentList.add(studentTO);
				}
				//Works for old admittedthrough studets
				else{
					studentList = categoryMapList.get(admittedThroughName);
					studentTO = new CategoryWiseStudentTO();
					slNo= slNo + 1;
					studentTO.setSlNo(slNo);
					if(admittedThroughName!=null){
						studentTO.setAdmittedThroughCode(admittedThroughName);
					}
					if(applicationNo!=0){
						studentTO.setApplicationNo(applicationNo);
					}
					if(registerNo != null){
						studentTO.setRegisterNo(registerNo);
					}
					if(rollNo != null){
						studentTO.setRollNo(rollNo);
					}
					if(firstName != null){
						studentName.append(firstName);
					}
					if(middleName != null){
						studentName.append(" " + middleName);
					}
					if(lastName != null){
						studentName.append(" " + lastName);
					}
					if(studentName != null){
						studentTO.setStudentName(studentName.toString());
					}
					if(gender!=null){
						studentTO.setGender(gender);
					}
					studentList.add(studentTO);
				}								
				categoryMapList.put(admittedThroughName, studentList);								
				admittedIdSet.add(admittedThroughId);				
			}
		}
		List<CategoryWiseStudentTO> totalList= new ArrayList<CategoryWiseStudentTO>();
		List<CategoryWithStudentsTO> withStudentList= new ArrayList<CategoryWithStudentsTO>();
		if(categoryMapList!=null && categoryMapList.keySet()!=null && !categoryMapList.keySet().isEmpty()){
			Iterator<String> catMapItr=categoryMapList.keySet().iterator();
			while (catMapItr.hasNext()) {
				String admNm = (String) catMapItr.next();
				CategoryWithStudentsTO stTo= new CategoryWithStudentsTO();
				stTo.setCategoryName(admNm);
				stTo.setStudents(categoryMapList.get(admNm));
				totalList.addAll(categoryMapList.get(admNm));
				withStudentList.add(stTo);
			}
		}
		studentForm.setTotalStudentList(totalList);
		log.info("Leaving copyStudentBOToTO of CategoryWiseStudentHelper");
		return withStudentList;
	}
}
