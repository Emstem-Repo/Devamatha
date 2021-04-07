package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.MonthlyBelowRequiredPercentageReportForm;
import com.kp.cms.to.attendance.SubjectSummaryTO;
import com.kp.cms.to.reports.BelowRequiredPercentageTO;
import com.kp.cms.utilities.CommonUtil;

public class MonthlyBelowRequiredPercentageHelper {
	private static final Log log = LogFactory.getLog(MonthlyBelowRequiredPercentageHelper.class);
	public static volatile MonthlyBelowRequiredPercentageHelper self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static MonthlyBelowRequiredPercentageHelper getInstance(){
		if(self==null)
			self= new MonthlyBelowRequiredPercentageHelper();
		return self;
	}
	/**
	 * 
	 */
	private MonthlyBelowRequiredPercentageHelper(){
		
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	private String commonSearch(MonthlyBelowRequiredPercentageReportForm monthlyPercentageForm) {
		log.info("entered commonSearch of MonthlyBelowRequiredPercentageHelper");
		String searchCriteria = "";		
		
		if (monthlyPercentageForm.getClassesName().length > 0) {
			String [] classArray = monthlyPercentageForm.getClassesName();
			StringBuilder className = new StringBuilder();
			for(int i=0;i<classArray.length;i++){
				className.append(classArray[i]);
				if(i<(classArray.length-1)){
					className.append(",");
				}
			}
			String classType = " attendanceStudents.student.classSchemewise.classes.id in ("
				+ className +")";
			searchCriteria = searchCriteria + classType;
		}

		if (monthlyPercentageForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " and attendanceStudents.student.classSchemewise.curriculumSchemeDuration.academicYear = "
					+ monthlyPercentageForm.getAcademicYear();
			String admApplnStr = " and attendanceStudents.student.admAppln.isCancelled = 0";
			searchCriteria = searchCriteria + appliedYear + admApplnStr;
		}
		
		if (monthlyPercentageForm.getAttendanceType() != null && monthlyPercentageForm.getAttendanceType().length > 0) {
				String [] tempArray = monthlyPercentageForm.getAttendanceType();
				String attType ="";
				for(int i=0;i<tempArray.length;i++){
					 attType = attType+tempArray[i];
					 if(i<(tempArray.length-1)){
						 attType = attType+",";
					 }
				}
			
			String attendanceType = " and attendanceStudents.attendance.attendanceType.id in ("
					+ attType +")";
			String canclledAttn = " and attendanceStudents.attendance.isCanceled = 0";
			searchCriteria = searchCriteria + attendanceType + canclledAttn;
		}

		if (monthlyPercentageForm.getStartDate().trim().length() > 0 && monthlyPercentageForm.getEndDate().trim().length() > 0) {
			String betweenDate = " and attendanceStudents.attendance.attendanceDate between "+
					"'"+ CommonUtil.ConvertStringToSQLDate(monthlyPercentageForm.getStartDate())+"'" +
					 " and " +
					 "'" + CommonUtil.ConvertStringToSQLDate(monthlyPercentageForm.getEndDate())+"' " ;
			searchCriteria = searchCriteria + betweenDate;
		}		
		log.info("exit commonSearch of MonthlyBelowRequiredPercentageHelper");
		return searchCriteria;
	}
	/**
	 * @param monthlyPercentageForm
	 * @return
	 */
	public String getClassesHeldAttended(
			MonthlyBelowRequiredPercentageReportForm monthlyPercentageForm) {
		
		log.info("Entered getSelectionSearchCriteria of MonthlyBelowRequiredPercentageHelper");
		String statusCriteria = commonSearch(monthlyPercentageForm);
		
		String searchCriteria= "";
				
		searchCriteria = " select attendanceStudents.student, " +
				" attendanceStudents.attendance.subject.id, " +
				" attendanceStudents.attendance.subject.code, " +
				" sum(attendanceStudents.attendance.hoursHeldMonthly), " +
				" sum(attendanceStudents.attendance.hoursHeld) " +
				" from AttendanceStudent attendanceStudents " +
				" where " +statusCriteria+
				" and attendanceStudents.attendance.isMonthlyAttendance = 1 " +
				" group by attendanceStudents.attendance.subject.id, attendanceStudents.student.id " +
				" order by attendanceStudents.student.id, attendanceStudents.attendance.subject.id";
		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}

	/**
	 * 
	 * @param classesHeldListBO
	 * @param classesAttendedListBO
	 * @param Below the percentage
	 * @return Students with attendance percentage in respective subject
	 */
	public List<BelowRequiredPercentageTO> convertMonthlyBoToTo(List<AttendanceStudent> classesHeldAttendedListBO, String requiredPercentage,
	MonthlyBelowRequiredPercentageReportForm monthlyPercentageForm)throws Exception {		
		log.info("entered convertBotoTo of MonthlyBelowRequiredPercentageHelper");		
		List<BelowRequiredPercentageTO> studentList = new ArrayList<BelowRequiredPercentageTO>();
		
		//Gets the subjectMap and also list of subject codes and sort those based on subject ID. Needed to display in table heading
		Map<Integer, SubjectSummaryTO> subjectCodeMap = getSubjectCodes(classesHeldAttendedListBO);	
		List<SubjectSummaryTO>subjectCodeList = new ArrayList<SubjectSummaryTO>();
		subjectCodeList.addAll(subjectCodeMap.values());
		
		if(subjectCodeList!=null && subjectCodeList.size() > 0){
			Collections.sort(subjectCodeList);
		}	
		Map<String, Integer> codeMap = getSubjectCodePosition(subjectCodeList);		
		Set<Integer> studentIdSet = new HashSet<Integer>();
		
		BelowRequiredPercentageTO percentageTO = null;
		SubjectSummaryTO subjectSummaryTO = null;
		List<SubjectSummaryTO> subjectSummaryList = null;
				
		Map<Integer, BelowRequiredPercentageTO> studentInfoMap = new HashMap<Integer, BelowRequiredPercentageTO>();
		
		if(classesHeldAttendedListBO !=null){
			Iterator iterator = classesHeldAttendedListBO.iterator();
			while (iterator.hasNext()) {
				String subjectCode ="";
				double classesHeld;
				double classesAttended;				
				Object[] object = (Object[]) iterator.next();
				Student student = (Student)object[0];
				subjectCode = (String)object[2];
				classesHeld = Integer.valueOf(object[3].toString());
				classesAttended = Integer.valueOf(object[4].toString());
				//Works for new students.
				if(student!=null && !studentIdSet.contains(student.getId())){					
					subjectSummaryList = new ArrayList<SubjectSummaryTO>();					
					percentageTO = new BelowRequiredPercentageTO();
					if(student.getRegisterNo()!=null){
					percentageTO.setRegNo(student.getRegisterNo());
					}
					if(student.getRollNo()!=null){
						percentageTO.setRollNo(student.getRollNo());
					}					
					int subCodePosition = codeMap.get(subjectCode);				
					int position=1;
					//Only creates blank TOs and sets percentage as blank. Needed for the position of the subject
						for(;position < subCodePosition; position++){
							subjectSummaryTO = new SubjectSummaryTO();							
							SubjectSummaryTO summaryTO = subjectCodeList.get(position-1);
							subjectSummaryTO.setSubjectCode(summaryTO.getSubjectCode());
							subjectSummaryTO.setPercentage("");
							subjectSummaryList.add(subjectSummaryTO);
						}					
						subjectSummaryTO = new SubjectSummaryTO();
						if(subjectCode!=null){
							subjectSummaryTO.setSubjectCode(subjectCode);
						}
						if(classesHeld!=0 && classesAttended !=0){
							
						double result = (classesAttended/classesHeld) * 100;
						subjectSummaryTO.setPercentage(String.valueOf(Integer.valueOf((int)result).intValue()));
						}
						else{
							subjectSummaryTO.setPercentage(String.valueOf(Integer.valueOf((int)0).intValue()));
						}
						subjectSummaryList.add(subjectSummaryTO);
						studentIdSet.add(student.getId());	
						position = subCodePosition + 1;
						monthlyPercentageForm.setSubjectCodePosition(position);						
						percentageTO.setSubjectSummaryList(subjectSummaryList);
						studentInfoMap.put(student.getId(), percentageTO);
					}
				//Only gets the percentage of old student in respective subject
				else{
					percentageTO = studentInfoMap.get(student.getId());
					subjectSummaryList = percentageTO.getSubjectSummaryList();
					
					int currentPosition = monthlyPercentageForm.getSubjectCodePosition()+1;
					int subCodePosition = codeMap.get(subjectCode);
					////Only creates blank TOs and sets percentage as blank. Needed for the position of the subject
					for(;currentPosition < subCodePosition; currentPosition++){
						subjectSummaryTO = new SubjectSummaryTO();
						subjectSummaryTO.setSubjectCode(subjectCode);
						subjectSummaryTO.setPercentage("");
						subjectSummaryList.add(subjectSummaryTO);
					}
					
					subjectSummaryTO = new SubjectSummaryTO();
					if(subjectCode!=null){
						subjectSummaryTO.setSubjectCode(subjectCode);
					}
					if(classesHeld!=0 && classesAttended !=0){
					double result = (classesAttended/classesHeld) * 100;
					subjectSummaryTO.setPercentage(String.valueOf(Integer.valueOf((int)result).intValue()));
					}
					else{
						subjectSummaryTO.setPercentage(String.valueOf(Integer.valueOf((int)0).intValue()));
					}
					subjectSummaryList.add(subjectSummaryTO);					
					percentageTO.setSubjectSummaryList(subjectSummaryList);
				}
				studentInfoMap.put(student.getId(), percentageTO);
			}			
		}
		studentList.addAll(studentInfoMap.values());
		//Below condition is required to create the subject codes which are needed to display dynamically. For the every percentage list will add all subject codes to each TO
		if(studentList!=null && !studentList.isEmpty()){
			percentageTO = studentList.get(0);
			subjectSummaryList = percentageTO.getSubjectSummaryList();
			if(subjectSummaryList!=null && !subjectSummaryList.isEmpty() 
			&& subjectSummaryList.size() < subjectCodeList.size()){
							int requiredListSize = subjectCodeList.size();
							int currentListSize = subjectSummaryList.size();
							for(;currentListSize < requiredListSize; currentListSize++){
								SubjectSummaryTO summaryTO = subjectCodeList.get(currentListSize);
								subjectSummaryTO = new SubjectSummaryTO();
								subjectSummaryTO.setSubjectCode(summaryTO.getSubjectCode());
								subjectSummaryTO.setPercentage("");
								percentageTO.getSubjectSummaryList().add(subjectSummaryTO);
							}			
			}
		}
		List<BelowRequiredPercentageTO> finalSearchList= checkBelowRequiredPercentage(studentList, requiredPercentage);
		log.info("exit convertBotoTo of MonthlyBelowRequiredPercentageHelper");
		return finalSearchList;
	}
	
	/**
	 * Used to get all subject Codes
	 * Retrieves Subject Code List from retrieved BO List
	 */
	private static Map<Integer, SubjectSummaryTO> getSubjectCodes(List classesHeldAttendedListBO)throws Exception{
		log.info("Enters into getSubjectCodes of MonthlyBelowRequiredPercentageHelper");
		Map<Integer, SubjectSummaryTO>subjectCodeMap = new HashMap<Integer, SubjectSummaryTO>();
		SubjectSummaryTO summaryTO = null;
		if (classesHeldAttendedListBO != null) {
			Iterator it = classesHeldAttendedListBO.iterator();
			while (it.hasNext()) {
				int subjectId =0;
				String subjectCode ="";
				Object[] object = (Object[]) it.next();
				subjectId = (Integer)object[1];
				subjectCode = (String)object[2];
				summaryTO = new SubjectSummaryTO();
				summaryTO.setSubId(subjectId);
				if(subjectCode!=null){
					summaryTO.setSubjectCode(subjectCode);
				}
				subjectCodeMap.put(subjectId, summaryTO);
			}
		}
		log.info("Leaving into getSubjectCodes of MonthlyBelowRequiredPercentageHelper");
		return subjectCodeMap;
	}
	/**
	 * Gets the subjectCode as key and keeps the position as values
	 */
	private static Map<String, Integer> getSubjectCodePosition(List<SubjectSummaryTO> subjectCodeList)throws Exception{
		log.info("Enters into getSubjectCodePosition of MonthlyBelowRequiredPercentageHelper");
		Map<String, Integer> codeMap = new HashMap<String, Integer>();		
		if(subjectCodeList!=null){
			Iterator<SubjectSummaryTO> iterator = subjectCodeList.iterator();
			int position = 1;
			while (iterator.hasNext()) {
				SubjectSummaryTO subjectSummaryTO = iterator.next();
				codeMap.put(subjectSummaryTO.getSubjectCode(), position);
				position = position + 1;
			}
		}
		log.info("Leaving into getSubjectCodePosition of MonthlyBelowRequiredPercentageHelper");
		return codeMap;
	}
	
	/**
	 * Required to check less than the percentage entered in UI
	 */
	private static List<BelowRequiredPercentageTO> checkBelowRequiredPercentage(List<BelowRequiredPercentageTO> searchedList, String requiredPercentage)throws Exception{
		log.info("Enters into checkBelowRequiredPercentage of MonthlyBelowRequiredPercentageHelper");
		int requiredBelowPercentage = Integer.parseInt(requiredPercentage);
		
		Iterator<BelowRequiredPercentageTO> iterator = searchedList.iterator();
		while (iterator.hasNext()) {
			BelowRequiredPercentageTO belowRequiredPercentageTO = (BelowRequiredPercentageTO) iterator.next();
			if(belowRequiredPercentageTO.getSubjectSummaryList()!=null){
				int temp = 0;
				Iterator<SubjectSummaryTO> iterator2 = belowRequiredPercentageTO.getSubjectSummaryList().iterator();				
				while (iterator2.hasNext()) {
					SubjectSummaryTO subjectSummaryTO = (SubjectSummaryTO) iterator2.next();
					if(subjectSummaryTO!=null && !subjectSummaryTO.getPercentage().trim().equals("") && (Integer.parseInt(subjectSummaryTO.getPercentage().trim()))< requiredBelowPercentage){
						subjectSummaryTO.setPercentage(CMSConstants.NEW_LINE+ subjectSummaryTO.getPercentage());
						temp++;
					}
					}
				//If the student is not having less than entered percentage in any of the subject then delete the parent TO
				if(temp==0){
					iterator.remove();
				}
			}
		}
		log.info("Leaving into checkBelowRequiredPercentage of MonthlyBelowRequiredPercentageHelper");
		return searchedList;
	}
}