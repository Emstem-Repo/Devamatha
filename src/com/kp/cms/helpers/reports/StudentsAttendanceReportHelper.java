package com.kp.cms.helpers.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.StudentsAttendanceReportForm;
import com.kp.cms.helpers.employee.EmployeeReportHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentsAttendanceReportHelper {
	
	private static final Log log = LogFactory.getLog(StudentsAttendanceReportHelper.class);
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	private StringBuffer commonSearch(StudentsAttendanceReportForm studentsAttendanceReportForm) {
		log.info("entered commonSearch..");
		StringBuffer searchCriteria = new StringBuffer();
		
		if (studentsAttendanceReportForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " ac.classSchemewise.curriculumSchemeDuration.academicYear = "
					+ studentsAttendanceReportForm.getAcademicYear();
			String admApplnStr = " and attendanceStudents.student.admAppln.isCancelled = 0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)";
			searchCriteria.append(appliedYear).append(admApplnStr);
		}
		
		if (studentsAttendanceReportForm.getAttendanceType() != null && studentsAttendanceReportForm.getAttendanceType().length > 0) {
				String [] tempArray = studentsAttendanceReportForm.getAttendanceType();
				StringBuffer attType = new StringBuffer();
				for(int i=0;i<tempArray.length;i++){
					 attType = attType.append(tempArray[i]);
					 if(i<(tempArray.length-1)){
						 attType = attType.append(",");
					 }
				}
			
			String attendanceType = " and attendanceStudents.attendance.attendanceType.id in ("+ attType +")";
			String canclledAttn = " and attendanceStudents.attendance.isCanceled = 0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)" +
			 " and attendanceStudents.student.admAppln.isCancelled = 0 and attendanceStudents.student.isActive = 1 ";
			
			searchCriteria.append(attendanceType).append(canclledAttn);
		}
		
		if (studentsAttendanceReportForm.getClassesName() != null && studentsAttendanceReportForm.getClassesName().length > 0) {
			String [] tempClassesArray = studentsAttendanceReportForm.getClassesName();
			StringBuilder classes = new StringBuilder();
			for(int i=0;i<tempClassesArray.length;i++){
				classes.append(tempClassesArray[i]);
				 if(i<(tempClassesArray.length-1)){
					 classes.append(",");
				 }
			}
//		String classCodes = " and attendanceStudents.student.classSchemewise.classes.id in ("+ classes +")";  need to check class scheme id. because when selecting class we r storing class scheme id to form
		String classCodes = " and attendanceStudents.student.classSchemewise.id in ("+ classes +")" +
		 " and ac.classSchemewise.id in ("+ classes +") and attendanceStudents.student.classSchemewise.id=ac.classSchemewise.id ";
		searchCriteria = searchCriteria.append(classCodes);
		}
		
		if (studentsAttendanceReportForm.getActivityId().trim().length() > 0) {
			String activityId = " and attendanceStudents.attendance.activity <> null and attendanceStudents.attendance.activity = "
					+ studentsAttendanceReportForm.getActivityId();
			searchCriteria.append(activityId);
		}
//		if (studentsAttendanceReportForm.getLeaveType()!=null && studentsAttendanceReportForm.getIsMonthlyAttendance()!= null && !studentsAttendanceReportForm.getIsMonthlyAttendance().equalsIgnoreCase("true")) {
//			String attenLeave = " and attendanceStudents.isOnLeave = "
//					+ studentsAttendanceReportForm.getLeaveType();
//			searchCriteria.append(attenLeave);
//		}

		if (studentsAttendanceReportForm.getStartDate().trim().length() > 0 && studentsAttendanceReportForm.getEndDate().trim().length() > 0) {
			String betweenDate = " and attendanceStudents.attendance.attendanceDate between "+
					"'"+ CommonUtil.ConvertStringToSQLDate(studentsAttendanceReportForm.getStartDate())+"'" +
					 " and " +
					 "'" + CommonUtil.ConvertStringToSQLDate(studentsAttendanceReportForm.getEndDate())+"' " ;
			searchCriteria.append(betweenDate);
		}
		
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	public String getClassesHeld(
			StudentsAttendanceReportForm studentsAttendanceReportForm) {
		
		log.info("Entered getSelectionSearchCriteria..");
		StringBuffer statusCriteria = commonSearch(studentsAttendanceReportForm);
		
		String searchCriteria= "";
		
		/*searchCriteria = " select attendanceStudents.student.id, " +
		" attendanceStudents.student.registerNo, " +
		" attendanceStudents.student.rollNo, " +
		" attendanceStudents.student.admAppln.personalData.firstName, " +
		" attendanceStudents.student.admAppln.personalData.middleName, " +
		" attendanceStudents.student.admAppln.personalData.lastName, " +
		" attendanceStudents.student.classSchemewise.classes.id, " +
		" attendanceStudents.student.classSchemewise.classes.name, " +
		" sum(attendanceStudents.attendance.hoursHeld) " +
		"  from AttendanceStudent attendanceStudents " +
		" inner join attendanceStudents.attendance.attendanceClasses ac" +
		" where " +statusCriteria+
		" and attendanceStudents.attendance.isMonthlyAttendance = 0 and attendanceStudents.attendance.isCanceled=0" +
		" group by attendanceStudents.student.classSchemewise.classes.id, attendanceStudents.student.id " +
		" order by attendanceStudents.student.id ";*/
		
		searchCriteria = " select attendanceStudents.student.id, " +
				" attendanceStudents.student.registerNo, " +
				" attendanceStudents.student.rollNo, " +
				" attendanceStudents.student.admAppln.personalData.firstName, " +
				" attendanceStudents.student.admAppln.personalData.middleName, " +
				" attendanceStudents.student.admAppln.personalData.lastName, " +
				" ac.classSchemewise.classes.id, " +
				" ac.classSchemewise.classes.name, " +
				" sum(attendanceStudents.attendance.hoursHeld) " +
				"  from AttendanceStudent attendanceStudents " +
				" inner join attendanceStudents.attendance.attendanceClasses ac" +
				" where " +statusCriteria+
				" and attendanceStudents.attendance.isMonthlyAttendance = 0 and attendanceStudents.attendance.isCanceled=0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)" +
				" group by ac.classSchemewise.classes.id, attendanceStudents.student.id " +
				" order by attendanceStudents.student.id ";
		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	public String getClassesAttended(
			StudentsAttendanceReportForm studentsAttendanceReportForm) {
		
		log.info("Entered getSelectionSearchCriteria..");
		StringBuffer statusCriteria = commonSearch(studentsAttendanceReportForm);
		
		String searchCriteria= "";
		
		/*searchCriteria = " select attendanceStudents.student.id, " +
		" sum(attendanceStudents.attendance.hoursHeld) " +
		" from AttendanceStudent attendanceStudents " +
		" where " +statusCriteria+
		" and attendanceStudents.isPresent = 1 " +
		" and attendanceStudents.attendance.isMonthlyAttendance = 0 and attendanceStudents.attendance.isCanceled=0" +
		" group by attendanceStudents.student.classSchemewise.classes.id, attendanceStudents.student.id " +
		" order by attendanceStudents.student.id "; */
		
		searchCriteria = " select attendanceStudents.student.id, " +
		" sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.student.registerNo " +
		" from AttendanceStudent attendanceStudents " +
		" inner join attendanceStudents.attendance.attendanceClasses ac" +
		" where " +statusCriteria+
		" and attendanceStudents.isPresent = 1 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)" +
		" and attendanceStudents.attendance.isMonthlyAttendance = 0 and attendanceStudents.attendance.isCanceled=0" +
		" group by ac.classSchemewise.classes.id, attendanceStudents.student.id " +
		" order by attendanceStudents.student.id ";
		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	public String getClassesAttendedWithLeave(
			StudentsAttendanceReportForm studentsAttendanceReportForm) {
		
		log.info("Entered getSelectionSearchCriteria..");
		StringBuffer statusCriteria = commonSearch(studentsAttendanceReportForm);
		
		String searchCriteria= "";
		
		searchCriteria = " select attendanceStudents.student.id, " +
		" sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.student.registerNo " +
		" from AttendanceStudent attendanceStudents " +
		" inner join attendanceStudents.attendance.attendanceClasses ac" +
		" where " +statusCriteria+
		" and attendanceStudents.isOnLeave = 1 " +
		" and attendanceStudents.attendance.isMonthlyAttendance = 0 and attendanceStudents.attendance.isCanceled=0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)" +
		" group by ac.classSchemewise.classes.id, attendanceStudents.student.id " +
		" order by attendanceStudents.student.id "; 
		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param classesHeldListBO
	 * @param classesAttendedListBO
	 * @param fromPercentage
	 * @param toPercentage
	 * @return
	 */
	public Map<String, List<StudentsAttendanceReportTO>> convertBoToTo(List<AttendanceStudent> classesHeldListBO, List<AttendanceStudent> classesAttendedListBO, List<AttendanceStudent> classesAttendedWithLeaveListBO, String fromPercentage, String toPercentage,List<AttendanceStudent> classesAttendedWithCoCurricualLeave,List<Integer> detainedStudentList) {
		
		log.info("entered convertBotoTo..");
		Map<Integer,String> coMap=new HashMap<Integer, String>();
		if(classesAttendedWithCoCurricualLeave!=null){
			Iterator itr=classesAttendedWithCoCurricualLeave.iterator();
			while (itr.hasNext()) {
				Object[] bo = (Object[]) itr.next();
				if(bo[0]!=null && bo[1]!=null)
				coMap.put(Integer.parseInt(bo[0].toString()),bo[1].toString());
				
			}
		}
		
		List<StudentsAttendanceReportTO> studentsAttendanceTOList = new ArrayList<StudentsAttendanceReportTO>();
		Map<Integer,StudentsAttendanceReportTO> studentsAttendanceReportMap = new HashMap<Integer,StudentsAttendanceReportTO>();
		StudentsAttendanceReportTO studentsAttendanceReportTO = null;
		String name="";
		if(classesHeldListBO !=null){
			Iterator heldItr = classesHeldListBO.iterator();
			
			while (heldItr.hasNext()) {
				name="";
				Object[] classesHeld = (Object[]) heldItr.next();
				if(!detainedStudentList.contains(classesHeld[1])){
				studentsAttendanceReportTO = new StudentsAttendanceReportTO();

				StringBuffer registerNumber = new StringBuffer();
String str=classesHeld[0].toString();
				if (classesHeld[1] != null && !classesHeld[1].toString().isEmpty()) {
					registerNumber.append(classesHeld[1].toString());
				}

				if (classesHeld[1] != null && !classesHeld[1].toString().isEmpty()
						&& classesHeld[2] != null && !classesHeld[2].toString().isEmpty()) {
					registerNumber.append('/').append(classesHeld[2].toString());
				} else if (classesHeld[2] != null && !classesHeld[2].toString().isEmpty()) {
					registerNumber.append(classesHeld[2].toString());
				}
				studentsAttendanceReportTO.setRegisterNumber(registerNumber.toString());
				if(classesHeld[3]!=null){
					name=name+classesHeld[3].toString();
				}
				if(classesHeld[4]!=null){
					name=name+" "+classesHeld[4].toString();
				}
				if(classesHeld[5]!=null){
					name=name+" "+classesHeld[5].toString();
				}
//				if(classesHeld[3]!=null && classesHeld[4]!=null && classesHeld[5]!=null && !classesHeld[3].toString().isEmpty() && !classesHeld[4].toString().isEmpty() && !classesHeld[5].toString().isEmpty()){
//					studentsAttendanceReportTO.setStudentName(classesHeld[3].toString()+" "+classesHeld[4].toString()+" "+classesHeld[5].toString());
//				}else if(classesHeld[3]!=null && classesHeld[4]!=null && !classesHeld[3].toString().isEmpty() && !classesHeld[4].toString().isEmpty()){
//					studentsAttendanceReportTO.setStudentName(classesHeld[3].toString()+" "+classesHeld[4].toString());
//				}else if(classesHeld[3]!=null && !classesHeld[3].toString().isEmpty()){
//					studentsAttendanceReportTO.setStudentName(classesHeld[3].toString());
//				}
				studentsAttendanceReportTO.setStudentName(name);
				if (classesHeld[6] != null) {
					studentsAttendanceReportTO.setClassId(classesHeld[6].toString());
				}
				if (classesHeld[7] != null) {
					studentsAttendanceReportTO.setClassName(classesHeld[7].toString());
				}
				if (classesHeld[8] != null) {
					studentsAttendanceReportTO.setClassesHeld(classesHeld[8].toString());
				}
				studentsAttendanceReportTO.setClassesAttended(String.valueOf(0));
				studentsAttendanceReportTO.setPercentage(String.valueOf(0));
				
				studentsAttendanceReportMap.put((Integer) classesHeld[0],studentsAttendanceReportTO);
				}
			}
		}
		
		if(classesAttendedListBO != null){
			
			Iterator attenededItr = classesAttendedListBO.iterator();
			float classAttended = 0.0f;
			float percentage = 0.0f;
			
			while (attenededItr.hasNext()) {
				Object[] classesAttended = (Object[]) attenededItr.next();
				if(!detainedStudentList.contains(classesAttended[2])){
				if (studentsAttendanceReportMap.containsKey((Integer) classesAttended[0])) {
					studentsAttendanceReportTO = studentsAttendanceReportMap.get((Integer) classesAttended[0]);
//if(studentsAttendanceReportTO.getRegisterNumber().equalsIgnoreCase("1010863")){
					if (classesAttended[1] != null) {
						studentsAttendanceReportTO.setClassesAttended(classesAttended[1].toString());
					}

					classAttended = Float.parseFloat(studentsAttendanceReportTO.getClassesAttended());
					percentage = CommonUtil.roundToDecimal(((classAttended / Float.parseFloat(studentsAttendanceReportTO.getClassesHeld())) * 100), 2);
					if(classesAttendedWithLeaveListBO==null && classesAttendedWithCoCurricualLeave==null){
						if(percentage>=Float.parseFloat(fromPercentage) && percentage<=Float.parseFloat(toPercentage)){
							studentsAttendanceReportTO.setPercentage(String.valueOf(percentage));
							studentsAttendanceReportMap.put((Integer) classesAttended[0],studentsAttendanceReportTO);
						}else{
							studentsAttendanceReportMap.remove((Integer) classesAttended[0]);
						}
					}else{
						studentsAttendanceReportTO.setPercentage(String.valueOf(percentage));
						studentsAttendanceReportMap.put((Integer) classesAttended[0],studentsAttendanceReportTO);
					}
}
				}
				}
			}
		//}
		
if(classesAttendedWithCoCurricualLeave != null){
			
			Iterator attenededWithCoCurricualLeaveItr = classesAttendedWithCoCurricualLeave.iterator();
			float classAttended = 0.0f;
			float percentage = 0.0f;
			
			while (attenededWithCoCurricualLeaveItr.hasNext()) {
				Object[] classesAttended = (Object[]) attenededWithCoCurricualLeaveItr.next();
				if(!detainedStudentList.contains(classesAttended[2])){
				if (studentsAttendanceReportMap.containsKey((Integer) classesAttended[0])) {
					studentsAttendanceReportTO = studentsAttendanceReportMap.get((Integer) classesAttended[0]);
					//if(studentsAttendanceReportTO.getRegisterNumber().equalsIgnoreCase("1010863")){
					if (classesAttended[1] != null) {
						studentsAttendanceReportTO.setClassesAttended(String.valueOf(Integer.parseInt(studentsAttendanceReportTO.getClassesAttended()) + Integer.parseInt(classesAttended[1].toString())));
					}

					classAttended = Float.parseFloat(studentsAttendanceReportTO.getClassesAttended());
					percentage = CommonUtil.roundToDecimal(((classAttended / Float.parseFloat(studentsAttendanceReportTO.getClassesHeld())) * 100), 2);
					
					if(percentage>=Float.parseFloat(fromPercentage) && percentage<=Float.parseFloat(toPercentage)){
						studentsAttendanceReportTO.setPercentage(String.valueOf(percentage));
						studentsAttendanceReportMap.put((Integer) classesAttended[0],studentsAttendanceReportTO);
					}else{
						studentsAttendanceReportMap.remove((Integer) classesAttended[0]);
					}
					}
				}
				}
			}
	//	}
		
		if(classesAttendedWithLeaveListBO != null){
			
			Iterator attenededWithLeaveItr = classesAttendedWithLeaveListBO.iterator();
			float classAttended = 0.0f;
			float percentage = 0.0f;
			float percentageWithoutLeave=0.0f;
			
			while (attenededWithLeaveItr.hasNext()) {
				Object[] classesAttended = (Object[]) attenededWithLeaveItr.next();
				if(!detainedStudentList.contains(classesAttended[2])){
				boolean leavesAdded=false;
				if (studentsAttendanceReportMap.containsKey((Integer) classesAttended[0])) {
					studentsAttendanceReportTO = studentsAttendanceReportMap.get((Integer) classesAttended[0]);
//					if(studentsAttendanceReportTO.getRegisterNumber().equalsIgnoreCase("1114944")){
					percentageWithoutLeave=Math.round(Float.valueOf(studentsAttendanceReportTO.getPercentage()));
					if (classesAttended[1] != null && (percentageWithoutLeave>=75 && percentageWithoutLeave<=85)) {
						studentsAttendanceReportTO.setClassesAttended(String.valueOf(Integer.parseInt(studentsAttendanceReportTO.getClassesAttended()) + Integer.parseInt(classesAttended[1].toString())));
						classAttended=Float.valueOf(studentsAttendanceReportTO.getClassesAttended());
						leavesAdded=true;
						studentsAttendanceReportTO.setAddLeaves(leavesAdded);
					}
					else{
					classAttended = Float.parseFloat(studentsAttendanceReportTO.getClassesAttended());
					studentsAttendanceReportTO.setAddLeaves(leavesAdded);
					}
					
					percentage = CommonUtil.roundToDecimal(((classAttended / Float.parseFloat(studentsAttendanceReportTO.getClassesHeld())) * 100), 2);
					if(percentage>=Float.parseFloat(fromPercentage) && percentage<=Float.parseFloat(toPercentage)){
						
						if(percentage>=Math.round(85) && leavesAdded)
						studentsAttendanceReportTO.setPercentage(String.valueOf(85)+"*");
						else
							studentsAttendanceReportTO.setPercentage(String.valueOf(percentage));
						studentsAttendanceReportMap.put((Integer) classesAttended[0],studentsAttendanceReportTO);
					}else{
						/*if(classesAttendedWithCoCurricualLeave==null)
							studentsAttendanceReportMap.remove((Integer) classesAttended[0]);
						else{
							if(coMap.containsKey((Integer) classesAttended[0])){
								classAttended = Float.parseFloat(studentsAttendanceReportTO.getClassesAttended())+Float.parseFloat(coMap.get((Integer) classesAttended[0]));
								percentage = CommonUtil.roundToDecimal(((classAttended / Float.parseFloat(studentsAttendanceReportTO.getClassesHeld())) * 100), 2);
								if(!(percentage>=Float.parseFloat(fromPercentage)) && !(percentage<=Float.parseFloat(toPercentage)))
									studentsAttendanceReportMap.remove((Integer) classesAttended[0]);
							}else{
								studentsAttendanceReportMap.remove((Integer) classesAttended[0]);
							}
							//studentsAttendanceReportMap.remove((Integer)classesAttended[0]);
						}*/
						if(percentage>=Math.round(85) && leavesAdded)
							studentsAttendanceReportTO.setPercentage(String.valueOf(85)+"*");
						else
							studentsAttendanceReportMap.remove((Integer)classesAttended[0]);
					}        
//				}
				}
			}
		}
		}
		studentsAttendanceTOList.addAll(studentsAttendanceReportMap.values());
		List<StudentsAttendanceReportTO> finalList = new ArrayList<StudentsAttendanceReportTO>();
		Iterator<StudentsAttendanceReportTO> studentsAttendanceitr = studentsAttendanceTOList.iterator();
		
		while (studentsAttendanceitr.hasNext()) {
			StudentsAttendanceReportTO studentsAttendanceReportTO2 = (StudentsAttendanceReportTO) studentsAttendanceitr.next();
			String per=studentsAttendanceReportTO2.getPercentage();
			if(studentsAttendanceReportTO2.getPercentage().endsWith("*")){
				String percentageNew="";
				String percentages=studentsAttendanceReportTO2.getPercentage();
				percentageNew=percentages.replace("*", "");
				if(Float.parseFloat(percentageNew)>=Float.parseFloat(fromPercentage) && Float.parseFloat(percentageNew)<=Float.parseFloat(toPercentage)){
					finalList.add(studentsAttendanceReportTO2);
				}
			}
			else if(Float.parseFloat(studentsAttendanceReportTO2.getPercentage())>=Float.parseFloat(fromPercentage) && Float.parseFloat(studentsAttendanceReportTO2.getPercentage())<=Float.parseFloat(toPercentage)){
				finalList.add(studentsAttendanceReportTO2);
			}
		}
		
		Map<String, List<StudentsAttendanceReportTO>> studentsAttendanceMap = new HashMap<String, List<StudentsAttendanceReportTO>>();
		List<StudentsAttendanceReportTO> tempStudentAttendanceList = null;
		
		Iterator<StudentsAttendanceReportTO> studentsitr = finalList.iterator();
		
		while(studentsitr.hasNext()){
			StudentsAttendanceReportTO students = (StudentsAttendanceReportTO) studentsitr.next();
			
			if(students.getClassId()!= null && studentsAttendanceMap.containsKey(students.getClassName())){
				tempStudentAttendanceList = studentsAttendanceMap.get(students.getClassName());
				
				tempStudentAttendanceList.add(students);
			}else{
				tempStudentAttendanceList = new ArrayList<StudentsAttendanceReportTO>();
				
				tempStudentAttendanceList.add(students);
			}
			studentsAttendanceMap.put(students.getClassName(), tempStudentAttendanceList);
		}
		
		log.info("exit convertBotoTo..");
		return studentsAttendanceMap;
	}
	
	/**
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	public String getClassesHeldAttended(
			StudentsAttendanceReportForm studentsAttendanceReportForm) {
		
		log.info("Entered getSelectionSearchCriteria..");
		StringBuffer statusCriteria = commonSearch(studentsAttendanceReportForm);
		
		String searchCriteria= "";
		
		searchCriteria = " select attendanceStudents.student.id, " +
				" attendanceStudents.student.registerNo, " +
				" attendanceStudents.student.rollNo, " +
				" attendanceStudents.student.admAppln.personalData.firstName, " +
				" attendanceStudents.student.admAppln.personalData.middleName, " +
				" attendanceStudents.student.admAppln.personalData.lastName, " +
				" attendanceStudents.student.classSchemewise.classes.id, " +
				" attendanceStudents.student.classSchemewise.classes.name, " +
				" sum(attendanceStudents.attendance.hoursHeldMonthly), " +
				" sum(attendanceStudents.attendance.hoursHeld) "+
				" from AttendanceStudent attendanceStudents " +
				" where " +statusCriteria+
				" and attendanceStudents.attendance.isMonthlyAttendance = 1 " +
				" group by attendanceStudents.student.classSchemewise.classes.id, attendanceStudents.student.id " +
				" order by attendanceStudents.student.id ";
		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param classesHeldListBO
	 * @param classesAttendedListBO
	 * @param fromPercentage
	 * @param toPercentage
	 * @return
	 */
	public Map<String, List<StudentsAttendanceReportTO>> convertMonthlyBoToTo(List<AttendanceStudent> classesHeldAttendedListBO, String fromPercentage, String toPercentage) {
		
		log.info("entered convertBotoTo..");
		List<StudentsAttendanceReportTO> monthlyStudentsAttendanceTOList = new ArrayList<StudentsAttendanceReportTO>();
		Map<Integer,StudentsAttendanceReportTO> monthlyStudentsAttendanceReportMap = new HashMap<Integer,StudentsAttendanceReportTO>();
		StudentsAttendanceReportTO studentsAttendanceReportTO = null;
		
		if(classesHeldAttendedListBO !=null){
			Iterator heldItr = classesHeldAttendedListBO.iterator();
			float classHeld = 0.0f;
			float classAttended = 0.0f;
			float percentage = 0.0f;
			
			while (heldItr.hasNext()) {

				Object[] classesHeld = (Object[]) heldItr.next();
				studentsAttendanceReportTO = new StudentsAttendanceReportTO();

				StringBuffer registerNumber = new StringBuffer();

				if (classesHeld[1] != null && !classesHeld[1].toString().isEmpty()) {
					registerNumber.append(classesHeld[1].toString());
				}

				if (classesHeld[1] != null && !classesHeld[1].toString().isEmpty()
						&& classesHeld[2] != null && !classesHeld[2].toString().isEmpty()) {
					registerNumber.append('/').append(classesHeld[2].toString());
				} else if (classesHeld[2] != null && !classesHeld[2].toString().isEmpty()) {
					registerNumber.append(classesHeld[2].toString());
				}
				studentsAttendanceReportTO.setRegisterNumber(registerNumber.toString());

				
				if(classesHeld[3]!=null && classesHeld[4]!=null && classesHeld[5]!=null && !classesHeld[3].toString().isEmpty() && !classesHeld[4].toString().isEmpty() && !classesHeld[5].toString().isEmpty()){
					studentsAttendanceReportTO.setStudentName(classesHeld[3].toString()+" "+classesHeld[4].toString()+" "+classesHeld[5].toString());
				}else if(classesHeld[3]!=null && classesHeld[4]!=null && !classesHeld[3].toString().isEmpty() && !classesHeld[4].toString().isEmpty()){
					studentsAttendanceReportTO.setStudentName(classesHeld[3].toString()+" "+classesHeld[4].toString());
				}else if(classesHeld[3]!=null && !classesHeld[3].toString().isEmpty()){
					studentsAttendanceReportTO.setStudentName(classesHeld[3].toString());
				}
				if (classesHeld[6] != null) {
					studentsAttendanceReportTO.setClassId(classesHeld[6].toString());
				}
				if (classesHeld[7] != null) {
					studentsAttendanceReportTO.setClassName(classesHeld[7].toString());
				}
				if (classesHeld[8] != null) {
					studentsAttendanceReportTO.setClassesHeld(classesHeld[8].toString());
				}
				if (classesHeld[9] != null) {
					studentsAttendanceReportTO.setClassesAttended(classesHeld[9].toString());
				}
				
				classHeld = Float.parseFloat(studentsAttendanceReportTO.getClassesHeld());
				classAttended = Float.parseFloat(studentsAttendanceReportTO.getClassesAttended());
				percentage = CommonUtil.roundToDecimal(((classAttended / classHeld) * 100), 2);
				
				if(percentage>=Float.parseFloat(fromPercentage) && percentage<=Float.parseFloat(toPercentage)){
					studentsAttendanceReportTO.setPercentage(String.valueOf(percentage));
					monthlyStudentsAttendanceReportMap.put((Integer) classesHeld[0],studentsAttendanceReportTO);
				}
			}
		}
		monthlyStudentsAttendanceTOList.addAll(monthlyStudentsAttendanceReportMap.values());
		
		Map<String, List<StudentsAttendanceReportTO>> studentsAttendanceMap = new HashMap<String, List<StudentsAttendanceReportTO>>();
		List<StudentsAttendanceReportTO> tempStudentAttendanceList = null;
		
		Iterator<StudentsAttendanceReportTO> studentsitr = monthlyStudentsAttendanceTOList.iterator();
		
		while(studentsitr.hasNext()){
			StudentsAttendanceReportTO students = (StudentsAttendanceReportTO) studentsitr.next();
			
			if(students.getClassId()!= null && studentsAttendanceMap.containsKey(students.getClassName())){
				tempStudentAttendanceList = studentsAttendanceMap.get(students.getClassName());
				
				tempStudentAttendanceList.add(students);
			}else{
				tempStudentAttendanceList = new ArrayList<StudentsAttendanceReportTO>();
				
				tempStudentAttendanceList.add(students);
			}
			studentsAttendanceMap.put(students.getClassName(), tempStudentAttendanceList);
		}
		
		log.info("exit convertBotoTo..");
		return studentsAttendanceMap;
	}
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	public String getClassesAttendedWithCoCurricalLeave(
			StudentsAttendanceReportForm studentsAttendanceReportForm) 
	{
		log.info("Entered getSelectionSearchCriteria..");
		StringBuffer statusCriteria = commonSearch(studentsAttendanceReportForm);
		
		String searchCriteria= "";
		
		searchCriteria = " select attendanceStudents.student.id, " +
		" sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.student.registerNo " +
		" from AttendanceStudent attendanceStudents " +
		" inner join attendanceStudents.attendance.attendanceClasses ac" +
		" where " +statusCriteria+
		" and attendanceStudents.isCoCurricularLeave = 1 " +
		" and attendanceStudents.attendance.isMonthlyAttendance = 0 and attendanceStudents.attendance.isCanceled=0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)" +
		" group by ac.classSchemewise.classes.id, attendanceStudents.student.id " +
		" order by attendanceStudents.student.id "; 
		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	/**
	 * @param attendanceReportTOList
	 * @param request
	 * @throws Exception
	 * added by mehaboob
	 */
	public void convertToToExcel(List<StudentsAttendanceReportTO> attendanceReportTOList,HttpServletRequest request) throws Exception{
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of EmployeeReportHelper ",e);
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.EMP_EXCEL_REPORT);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		ByteArrayOutputStream bos=null;
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		XSSFCell cell=null;
		
		if(attendanceReportTOList!=null){
			int count = 0;
			Iterator<StudentsAttendanceReportTO> iterator = attendanceReportTOList.iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Attendance Report");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				row.createCell((short)0).setCellValue("Class Name");// added by mehaboob
				row.createCell((short)1).setCellValue("Register No");
				row.createCell((short)2).setCellValue("Student Name");
				row.createCell((short)3).setCellValue("Classes Held");
				row.createCell((short)4).setCellValue("Classes Attended");
				row.createCell((short)5).setCellValue("Percentage");
				row.createCell((short)6).setCellValue(" ");
				// Create cells in the row and put some data in it.
				while (iterator.hasNext()) {
					StudentsAttendanceReportTO attendanceReportTO = (StudentsAttendanceReportTO) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					row.createCell((short)0).setCellValue(attendanceReportTO.getClassName());	// added by sudhir
					row.createCell((short)1).setCellValue(attendanceReportTO.getRegisterNumber());	
					row.createCell((short)2).setCellValue(attendanceReportTO.getStudentName());
					row.createCell((short)3).setCellValue(attendanceReportTO.getClassesHeld());
					row.createCell((short)4).setCellValue(attendanceReportTO.getClassesAttended());
					row.createCell((short)5).setCellValue(attendanceReportTO.getPercentage());
				}
				bos=new ByteArrayOutputStream();
				wb.write(bos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
				fos.flush();
				fos.close();
				
			}catch (Exception e) {
				//throw new ApplicationException();
				// TODO: handle exception
				
			}
		}
		
	}
	
}