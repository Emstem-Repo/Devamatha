package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.StudentLeaveDetails;
import com.kp.cms.forms.reports.LeaveReportForm;
import com.kp.cms.to.reports.LeaveReportTO;
import com.kp.cms.utilities.CommonUtil;

public class LeaveReportHelper {

	
private static final Log log = LogFactory.getLog(LeaveReportHelper.class);
private static final String DATE_FORMAT = "dd-MM-yyyy";
	
	/**
	 * @param leaveReportForm
	 * @return
	 * This method will build dynamic query
	 */

	private static String commonSearch(LeaveReportForm leaveReportForm) {
		log.info("entered commonSearch method in LeaveReportHelper class");
		
		String query = "";
//		if (leaveReportForm.getProgramTypeId().trim().length() > 0 && !StringUtils.isEmpty(leaveReportForm.getProgramTypeId())) {
//			String programType = " studentLeave.classSchemewise.classes.course.program.programType.id = "+ leaveReportForm.getProgramTypeId();
//				query = query + programType;
//		}
//		if(leaveReportForm.getProgramId().trim().length() > 0 && !StringUtils.isEmpty(leaveReportForm.getProgramId())){
//			String program = " and studentLeave.classSchemewise.classes.course.program.id = "+ leaveReportForm.getProgramId();	
//				query = query + program;
//		}	
//		if(leaveReportForm.getCourseId().trim().length() > 0 && !StringUtils.isEmpty(leaveReportForm.getCourseId())){
//			String course = " and studentLeave.classSchemewise.classes.course.id = "+leaveReportForm.getCourseId();	
//				query = query + course;
//		}	
//		if(leaveReportForm.getSemister().trim().length() > 0 && !StringUtils.isEmpty(leaveReportForm.getSemister())){
//			String semister = " and studentLeave.classSchemewise.curriculumSchemeDuration.semesterYearNo = "+ leaveReportForm.getSemister();
//				query = query + semister;
//		}
		if (leaveReportForm.getClassesName() != null && leaveReportForm.getClassesName().length > 0) {
			String [] tempClassesArray = leaveReportForm.getClassesName();
			StringBuilder classes = new StringBuilder();
			for(int i=0;i<tempClassesArray.length;i++){
				classes.append(tempClassesArray[i]);
				 if(i<(tempClassesArray.length-1)){
					 classes.append(",");
				 }
			}
		//String classCodes = " studentLeave.classSchemewise.classes.id in ("+ classes +")";
		String classCodes = " studentLeave.classSchemewise.id in ("+ classes +")";
		query = query + classCodes;
		}
		if (leaveReportForm.getAcademicYear().trim().length() > 0) {
			
			String appliedYear = " and studentLeave.classSchemewise.curriculumSchemeDuration.academicYear = "+ leaveReportForm.getAcademicYear();
				query = query + appliedYear;
		}
	
		if (leaveReportForm.getStartDate().trim().length() > 0) {
			String startDate = " and studentLeave.startDate >= '"
					+ CommonUtil.ConvertStringToSQLDate(leaveReportForm.getStartDate())+"'" ;
			query = query + startDate;
		}

		if (leaveReportForm.getEndDate().trim().length() > 0) {
			String endDate = " and studentLeave.endDate <= '"
					+ CommonUtil.ConvertStringToSQLDate(leaveReportForm.getEndDate())+"'";
			query = query + endDate;
		}
		log.info("exit of commonSearch method in LeaveReportHelper class");
		return query;
	}

	/**
	 * @param leaveReportForm
	 * @return value of type string.
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(LeaveReportForm leaveReportForm) {
		log.info("entered getSelectionSearchCriteria method in LeaveReportHelper class");
		String statusCriteria = commonSearch(leaveReportForm);
		
		String searchCriteria= "";
		searchCriteria = " from StudentLeave studentLeave"
				+ " where " + statusCriteria;					
		log.info("exit of getSelectionSearchCriteria method in LeaveReportHelper class");
		return searchCriteria;

	}
	
	/**
	 * This method is used to convert BOs to TOs from list
	 * @param list
	 * @param leaveReportForm
	 * @return
	 */
	
	public static List<LeaveReportTO> convertBOstoTOs(List<StudentLeave> studentLeaveList) {
		log.info("entered convertBOstoTOs method in LeaveReportHelper class");
		List<LeaveReportTO> leaveReportToList = new ArrayList<LeaveReportTO>();
		if(studentLeaveList != null) {
			Iterator<StudentLeave> studentIterator = studentLeaveList.iterator();
			Map<Integer,LeaveReportTO> leaveReportTOMap = new HashMap<Integer, LeaveReportTO>();
			while (studentIterator.hasNext()) {
			  
				StudentLeave studentLeave = (StudentLeave) studentIterator
						.next();
				
				Iterator<StudentLeaveDetails> studentLeaveSetIterator = studentLeave
						.getStudentLeaveDetails().iterator();
				
				while (studentLeaveSetIterator.hasNext()) {
					
					StudentLeaveDetails studentLeaveDetails = (StudentLeaveDetails) studentLeaveSetIterator
							.next();
				    Student student =	studentLeaveDetails.getStudent();
				   
					 LeaveReportTO leaveReportTO = null;
				    if(leaveReportTOMap.containsKey(student.getId())) {
				    	if(leaveReportTOMap != null){
				    		leaveReportTO = leaveReportTOMap.get(student.getId());
				    	}
				    	List<String> classesName = leaveReportTO.getClassesNameList();
				    
						List<String> startPeriodName = leaveReportTO.getStartPeriodList();
						
						List<String> endPeriodName = leaveReportTO.getEndPeriodList();
						
						List<String> startDateList = leaveReportTO.getStartDateList();
						
						List<String> endDateList = leaveReportTO.getEndDateList();
						
						if(student.getClassSchemewise().getClasses().getName()!= null){
							classesName.add("\n"+student.getClassSchemewise().getClasses().getName());
						}
						
						if(studentLeaveDetails.getStudentLeave().getStartDate() != null){
							startDateList.add("\n"+CommonUtil.formatDate(studentLeaveDetails.getStudentLeave().getStartDate(),LeaveReportHelper.DATE_FORMAT));
						}
						
						if(studentLeaveDetails.getStudentLeave().getEndDate() != null){
							endDateList.add("\n"+CommonUtil.formatDate(studentLeaveDetails.getStudentLeave().getEndDate(),LeaveReportHelper.DATE_FORMAT));
						}
						
						if(studentLeaveDetails.getStudentLeave().getStartPeriod().getPeriodName() != null){
							startPeriodName.add("\n"+studentLeaveDetails.getStudentLeave().getStartPeriod().getPeriodName());
						}
						
						if(studentLeaveDetails.getStudentLeave().getEndPeriod().getPeriodName() != null){
							endPeriodName.add("\n"+studentLeaveDetails.getStudentLeave().getEndPeriod().getPeriodName());
						}
				    	
						leaveReportTO.setClassesNameList(classesName);
						leaveReportTO.setEndDateList(endDateList);
						leaveReportTO.setStartDateList(startDateList);
						leaveReportTO.setStartPeriodList(startPeriodName);
						leaveReportTO.setEndPeriodList(endPeriodName);
				    	
				    	
				    } else {
				    	leaveReportTO = new LeaveReportTO();
				    	
				    	AdmAppln admAppln = student.getAdmAppln();
						String applicantName = "";
						if (admAppln.getPersonalData().getFirstName() != null) {
							applicantName = applicantName
									+ admAppln.getPersonalData().getFirstName()
									+ " ";
						}
						if (admAppln.getPersonalData().getMiddleName() != null) {
							applicantName = applicantName
									+ admAppln.getPersonalData().getMiddleName()
									+ " ";
						}
						if (admAppln.getPersonalData().getLastName() != null) {
							applicantName = applicantName
									+ admAppln.getPersonalData().getLastName()
									+ " ";
						}

						leaveReportTO.setStudentName(applicantName);
						leaveReportTO.setRegisterNo(student.getRegisterNo());
						leaveReportTO.setRollNo("/"+student.getRollNo());
						
						List<String> classesName = new ArrayList<String>();
						List<String> startPeriodName = new ArrayList<String>();
						
						List<String> endPeriodName = new ArrayList<String>();
						
						List<String> startDateList = new ArrayList<String>();
						
						List<String> endDateList = new ArrayList<String>();
						if(student.getClassSchemewise().getClasses().getName()!= null){
							classesName.add(student.getClassSchemewise().getClasses().getName());
						}
						
						if(studentLeaveDetails.getStudentLeave().getStartDate() != null){
							startDateList.add(CommonUtil.formatDate(studentLeaveDetails.getStudentLeave().getStartDate(),LeaveReportHelper.DATE_FORMAT));
						}
						
						if(studentLeaveDetails.getStudentLeave().getEndDate() != null){
							endDateList.add(CommonUtil.formatDate(studentLeaveDetails.getStudentLeave().getEndDate(),LeaveReportHelper.DATE_FORMAT));
						}
						
						if(studentLeaveDetails.getStudentLeave().getStartPeriod().getPeriodName() != null){
							startPeriodName.add(studentLeaveDetails.getStudentLeave().getStartPeriod().getPeriodName());
						}
						
						if(studentLeaveDetails.getStudentLeave().getEndPeriod().getPeriodName() != null){
							endPeriodName.add(studentLeaveDetails.getStudentLeave().getEndPeriod().getPeriodName());
						}
				    	
						leaveReportTO.setClassesNameList(classesName);
						leaveReportTO.setEndDateList(endDateList);
						leaveReportTO.setStartDateList(startDateList);
						leaveReportTO.setStartPeriodList(startPeriodName);
						leaveReportTO.setEndPeriodList(endPeriodName);
						
				    }
				
				    leaveReportTOMap.put(student.getId(), leaveReportTO);
				}
				
			}
			leaveReportToList.addAll(leaveReportTOMap.values());
		}
		log.info("exit of convertBOstoTOs method in LeaveReportHelper class.");
		return leaveReportToList;
	}			
}