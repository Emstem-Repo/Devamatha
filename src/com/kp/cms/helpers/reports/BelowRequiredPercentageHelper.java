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

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.BelowRequiredPercentageReportForm;
import com.kp.cms.to.attendance.SubjectSummaryTO;
import com.kp.cms.to.reports.BelowRequiredPercentageTO;
import com.kp.cms.utilities.CommonUtil;

public class BelowRequiredPercentageHelper {
	private static final Log log = LogFactory.getLog(BelowRequiredPercentageHelper.class);
	public static volatile BelowRequiredPercentageHelper self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static BelowRequiredPercentageHelper getInstance(){
		if(self==null)
			self= new BelowRequiredPercentageHelper();
		return self;
	}
	/**
	 * Private constructor for a singleton class
	 */
	private BelowRequiredPercentageHelper(){
		
	}
		
	/**
	 * @param BelowRequiredPercentageReportForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(BelowRequiredPercentageReportForm percentageReportForm) throws Exception{
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		if (percentageReportForm.getClassesName().length > 0) {
			String [] classArray = percentageReportForm.getClassesName();
			StringBuilder className =new StringBuilder();
			for(int i=0;i<classArray.length;i++){
				className.append(classArray[i]);
				if(i<(classArray.length-1)){
					className.append(",");
				}
			}
			String classType = " attendanceStudent.student.classSchemewise.classes.id in ("
				+ className +")";
			searchCriteria = searchCriteria + classType;
		}

		if (percentageReportForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ percentageReportForm.getAcademicYear();
			String admApplnStr = " and attendanceStudent.student.admAppln.isCancelled = 0";
			searchCriteria = searchCriteria + appliedYear + admApplnStr;
		}

		if (percentageReportForm.getAttendanceType().length > 0) {
			String [] tempArray = percentageReportForm.getAttendanceType();
			String attType ="";
			for(int i=0;i<tempArray.length;i++){
				attType = attType+tempArray[i];
				if(i<(tempArray.length-1)){
					attType = attType+",";
				}
			}

			String attendanceType = " and attendanceStudent.attendance.attendanceType in ("
				+ attType +")";
			String canclledAttn = " and attendanceStudent.attendance.isCanceled = 0 ";
			String monthlyAttn = " and attendanceStudent.attendance.isMonthlyAttendance = 0 ";
			searchCriteria = searchCriteria + attendanceType + canclledAttn + monthlyAttn;
		}
		if (percentageReportForm.getActivityId().trim().length() > 0) {
			String activityId = " and attendanceStudent.attendance.activity <> null and attendanceStudent.attendance.activity = "
				+ percentageReportForm.getActivityId();
			searchCriteria = searchCriteria + activityId;
		}
		
		if (percentageReportForm.getStartDate().trim().length() > 0) {
			String startDate = " and attendanceStudent.attendance.attendanceDate >= '"
				+ CommonUtil.ConvertStringToSQLDate(percentageReportForm.getStartDate())+"'" ;
			searchCriteria = searchCriteria + startDate;
		}

		if (percentageReportForm.getEndDate().trim().length() > 0) {
			String endDate = " and attendanceStudent.attendance.attendanceDate <= '"
				+ CommonUtil.ConvertStringToSQLDate(percentageReportForm.getEndDate())+"'";
			searchCriteria = searchCriteria + endDate;
		}
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	/**
	 * @param BelowRequiredPercentageReportForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(
			BelowRequiredPercentageReportForm percentageReportForm)throws Exception {
		log.info("entered getSelectionSearchCriteria of BelowRequiredPercentageHelper");
		String statusCriteria = commonSearch(percentageReportForm);

		String searchCriteria= "";

		searchCriteria =  " from AttendanceStudent attendanceStudent "
			+ " where " + statusCriteria ;					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}	
	


	
	/**
	 * @param attenStudent
	 * @param requiredPercentageReportForm
	 * @return
	 * This method will give the Selected Activity Held for particular student
	 */
	private static String getSelectedActivityHeld(AttendanceStudent student,Attendance attenStudent,BelowRequiredPercentageReportForm percentageReportForm)throws Exception{
		log.info("entered getSelectedActivityHeld..");
		String searchCre = "";
		if(percentageReportForm.getStartDate()!=null && percentageReportForm.getEndDate()!=null && percentageReportForm.getActivityId()!=null){
			searchCre = "from AttendanceStudent attendanceStudent"
			+" where attendance.isCanceled = 0 and attendanceStudent.attendance.isActivityAttendance = 1 "			
			+" and attendanceStudent.attendance.attendanceDate >= '"
			+CommonUtil.ConvertStringToSQLDate(percentageReportForm.getStartDate())+"'"
			+" and attendanceStudent.attendance.attendanceDate <='"
			+CommonUtil.ConvertStringToSQLDate(percentageReportForm.getEndDate())+"'"
			+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
			+" and attendanceStudent.attendance.activity.id = '"+Integer.parseInt(percentageReportForm.getActivityId())+"'"
			+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getSelectedActivityHeld..");
		return searchCre;
	}
	
	/**
	 * @param attenStudent
	 * @param requiredPercentageReportForm
	 * @return
	 * This method will give the Activity Attended for particular student
	 */
	private static String getActivityAttended(AttendanceStudent student, Attendance attenStudent,BelowRequiredPercentageReportForm percentageReportForm)throws Exception{
		log.info("entered getActivityAttended..");
		String searchCre = "";
		if(percentageReportForm.getStartDate()!=null && percentageReportForm.getEndDate()!=null && student.getStudent()!=null && student.getStudent().getRegisterNo()!=null){
			searchCre = "from AttendanceStudent attendanceStudent" 
			+" where attendance.isCanceled = 0 and attendanceStudent.isPresent = 1 and attendanceStudent.attendance.activity.id = "
			+ attenStudent.getActivity().getId()+" and attendanceStudent.attendance.isActivityAttendance = 1 "
			+" and attendanceStudent.attendance.attendanceDate >= '"
			+CommonUtil.ConvertStringToSQLDate(percentageReportForm.getStartDate())+"'"
			+" and attendanceStudent.attendance.attendanceDate <='"+CommonUtil.ConvertStringToSQLDate(percentageReportForm.getEndDate())+"'"
			+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"					
			+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getActivityAttended..");
		return searchCre;
	}
	/**
	 * @param attenStudent
	 * @param requiredPercentageReportForm
	 * @return
	 * This method will give the Selected Activity Attended for particular student
	 */
	private static String getSelectedActivityAttended(AttendanceStudent student, Attendance attenStudent,BelowRequiredPercentageReportForm percentageReportForm)throws Exception{
		log.info("entered getSelectedActivityAttended..");
		String searchCre = "";
		if(percentageReportForm.getStartDate()!=null && percentageReportForm.getEndDate()!=null && student.getStudent()!=null && student.getStudent().getRegisterNo()!=null && percentageReportForm.getActivityId()!=null){
			searchCre = "from AttendanceStudent attendanceStudent" 
			+" where attendanceStudent.isPresent = 1 "
			+" and attendance.isCanceled = 0 and attendanceStudent.attendance.isActivityAttendance = 1 "
			+" and attendanceStudent.attendance.attendanceDate >= '"
			+CommonUtil.ConvertStringToSQLDate(percentageReportForm.getStartDate())+"'"
			+" and attendanceStudent.attendance.attendanceDate <='"+CommonUtil.ConvertStringToSQLDate(percentageReportForm.getEndDate())+"'"
			+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
			+" and attendanceStudent.attendance.activity.id = '"+Integer.parseInt(percentageReportForm.getActivityId())+"'"
			+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getSelectedActivityAttended..");
		return searchCre;
	}
	
	/**
	 * Required to check less than the percentage entered in UI
	 */
	private static List<BelowRequiredPercentageTO> checkBelowRequiredPercentage(List<BelowRequiredPercentageTO> searchedList, BelowRequiredPercentageReportForm requiredPercentageReportForm)throws Exception{
		int requiredBelowPercentage = Integer.parseInt(requiredPercentageReportForm.getRequiredPercentage());
		
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
					if(temp==0){
						iterator.remove();
					}
			}
		}
		return searchedList;
	}
		
	/**
	 * @param studentSearchBo
	 * @param BelowRequiredPercentageReportForm
	 * @return
	 * This method will convert BO' to TO's
	 */
	public static List<BelowRequiredPercentageTO> populateBoToTo(List studentSearchBo,BelowRequiredPercentageReportForm requiredPercentageReportForm) throws Exception{
		log.info("entered convertBoToTo..");
		
		BelowRequiredPercentageTO belowRequiredPercentageTO = null;
		Map<Integer, BelowRequiredPercentageTO> finalStudentMap = new HashMap<Integer, BelowRequiredPercentageTO>();
		
		Map<Integer, SubjectSummaryTO> subjectCodeMap = getSubjectCodes(studentSearchBo,requiredPercentageReportForm);
		
		List<SubjectSummaryTO>subjectCodeList = new ArrayList<SubjectSummaryTO>();
		subjectCodeList.addAll(subjectCodeMap.values());
		//Prepares the subject codes as a list 
		if(subjectCodeList!=null && subjectCodeList.size() > 0){
			Collections.sort(subjectCodeList);
		}		
		requiredPercentageReportForm.setSubjectLSummaryist(subjectCodeList);
		//Prepares a subject List with giving position
		Map<String, Integer> codeMap = getSubjectCodePosition(subjectCodeList);
		
		Set<String> subjectSet = null;
		
		Map<Integer, Set<String>> summaryMap = new HashMap<Integer, Set<String>>();
		
		SubjectSummaryTO summaryTO = null;
		Set<Integer> studentIdSet = new HashSet<Integer>();
		Map<Integer, BelowRequiredPercentageTO> studentMap = new HashMap<Integer, BelowRequiredPercentageTO>();
		
		Map<String, SubjectSummaryTO> subMap = null;
		
		Map<String, SubjectSummaryTO> subjectMap =null;
		
		Map<Integer, BelowRequiredPercentageTO> studentSubMap= new HashMap<Integer, BelowRequiredPercentageTO>();
		if (studentSearchBo != null) {
			Iterator it = studentSearchBo.iterator();
			while (it.hasNext()) {				
				AttendanceStudent attendance = (AttendanceStudent)it.next();				
				if(attendance.getStudent()!=null){
					//Works for new students
					if(studentIdSet!=null && !studentIdSet.contains(attendance.getStudent().getId())
					&& attendance.getAttendance()!=null && attendance.getAttendance().getSubject()!=null
					&& attendance.getAttendance().getSubject().getCode()!= null){
						
						belowRequiredPercentageTO = new BelowRequiredPercentageTO();
						if(attendance.getStudent().getRegisterNo()!=null){
							belowRequiredPercentageTO.setRegNo(attendance.getStudent().getRegisterNo());
						}
						if(attendance.getStudent().getRollNo()!=null){
							belowRequiredPercentageTO.setRollNo(attendance.getStudent().getRollNo());
						}
					studentIdSet.add(attendance.getStudent().getId());	
					
					subjectSet = new HashSet<String>();
					summaryTO = new SubjectSummaryTO();
					
					if(attendance.getAttendance()!=null && attendance.getAttendance().getSubject()!=null){
						summaryTO.setSubId(attendance.getAttendance().getSubject().getId());
						if(attendance.getAttendance().getSubject().getCode()!=null){
						summaryTO.setSubjectCode(attendance.getAttendance().getSubject().getCode());
						
						subjectSet.add(attendance.getAttendance().getSubject().getCode());						
						}						
							summaryTO.setHeld(attendance.getAttendance().getHoursHeld());

						if(attendance.getIsPresent()!=null && attendance.getIsPresent()){
							summaryTO.setAttended(attendance.getAttendance().getHoursHeld());
						}

						summaryMap.put(attendance.getStudent().getId(), subjectSet);
					}
					
					subjectMap = new HashMap<String, SubjectSummaryTO>();
					
					if(attendance.getAttendance()!=null && attendance.getAttendance().getSubject()!=null
					&& attendance.getAttendance().getSubject().getCode()!=null){
					subjectMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
					}
					
					belowRequiredPercentageTO.setSubjectMap(subjectMap);					
					
					//Used for duplicate subjects					
					subMap = new HashMap<String, SubjectSummaryTO>();
					
					if(attendance.getAttendance() != null && attendance.getAttendance().getSubject() != null
					&& attendance.getAttendance().getSubject().getCode()!=null){
					subMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
					}
					
					belowRequiredPercentageTO.setSubMap(subMap);
					studentSubMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);					
					
					studentMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);
					
					}
					//Works for existing student with different subject codes
					else{
						belowRequiredPercentageTO = studentMap.get(attendance.getStudent().getId());	
						
						subjectSet = summaryMap.get(attendance.getStudent().getId());
							if(attendance.getAttendance() != null && attendance.getAttendance().getSubject()!=null &&
							attendance.getAttendance().getSubject().getCode() != null && subjectSet!=null &&
							!subjectSet.contains(attendance.getAttendance().getSubject().getCode())){					
								summaryTO = new SubjectSummaryTO();
								summaryTO.setSubId(attendance.getAttendance().getSubject().getId());
								if(attendance.getAttendance().getSubject().getCode()!=null){
								summaryTO.setSubjectCode(attendance.getAttendance().getSubject().getCode());
								}
								
								summaryTO.setHeld(attendance.getAttendance().getHoursHeld());
		
								if(attendance.getIsPresent()!=null && attendance.getIsPresent()){
									summaryTO.setAttended(attendance.getAttendance().getHoursHeld());
								}
														
								belowRequiredPercentageTO.getSummaryMap().put(attendance.getAttendance().getSubject().getId(), summaryTO);
								
								subjectMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
													
								subjectSet.add(attendance.getAttendance().getSubject().getCode());
								summaryMap.put(attendance.getStudent().getId(), subjectSet);
								
								subMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
									
									subjectMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
									belowRequiredPercentageTO.setSubjectMap(subjectMap);
									
									
									belowRequiredPercentageTO.setSubMap(subMap);
									studentSubMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);	
								
									studentMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);					
						}
						
						else if(attendance.getAttendance() != null && attendance.getAttendance().getSubject()!=null &&
								attendance.getAttendance().getSubject().getCode() != null && subjectSet!= null &&
								subjectSet.contains(attendance.getAttendance().getSubject().getCode())){
							
								belowRequiredPercentageTO = studentSubMap.get(attendance.getStudent().getId());

								subjectMap = belowRequiredPercentageTO.getSubjectMap();
								summaryTO = subjectMap.get(attendance.getAttendance().getSubject().getCode());
								
								if(summaryTO!=null){
									double previousHeld = 0;
									double previousAttended =0;
									
									if(summaryTO.getHeld()!= 0){
									previousHeld = summaryTO.getHeld();
									}
									if(summaryTO.getAttended()!=0){
									previousAttended = summaryTO.getAttended();
									}
									
									double newHeld = previousHeld + attendance.getAttendance().getHoursHeld();
									
									if(attendance.getIsPresent()!=null && attendance.getIsPresent()){
										summaryTO.setAttended(previousAttended + attendance.getAttendance().getHoursHeld());
									}
									else{
										summaryTO.setAttended(previousAttended);
									}
									summaryTO.setHeld(newHeld);
									
									subjectMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
									belowRequiredPercentageTO.setSubjectMap(subjectMap);
								}
																
								subMap.put(attendance.getAttendance().getSubject().getCode(), summaryTO);
								
								belowRequiredPercentageTO.setSubMap(subMap);
								studentSubMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);	
							
								studentMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);
								
								subjectSet.add(attendance.getAttendance().getSubject().getCode());
								summaryMap.put(attendance.getStudent().getId(), subjectSet);
							}
					}
				}	
				if(belowRequiredPercentageTO!=null){
					finalStudentMap.put(attendance.getStudent().getId(), belowRequiredPercentageTO);
				}
			}
		}
		
		List<BelowRequiredPercentageTO> studentList = new ArrayList<BelowRequiredPercentageTO>();
			studentList.addAll(finalStudentMap.values());

		
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<BelowRequiredPercentageTO> iterator = studentList.iterator();
			while (iterator.hasNext()) {
				BelowRequiredPercentageTO belowTO = iterator.next();
				List<SubjectSummaryTO> subSummaryList = new ArrayList<SubjectSummaryTO>();
				subSummaryList.addAll(belowTO.getSubjectMap().values());
				belowTO.setSubjectSummaryList(subSummaryList);
			}
		}
		
		List<BelowRequiredPercentageTO> ResultList = getFinalStudentList(studentList,codeMap, requiredPercentageReportForm, subjectCodeList);
		List<BelowRequiredPercentageTO> finalSearchList= checkBelowRequiredPercentage(ResultList, requiredPercentageReportForm); 		
		return finalSearchList;
	}
	
	/**
	 * Used to get all subject Codes
	 * Retrieves Subject Code List
	 */
	private static Map<Integer, SubjectSummaryTO> getSubjectCodes(List studentSearchBo,BelowRequiredPercentageReportForm requiredPercentageReportForm)throws Exception{
		Map<Integer, SubjectSummaryTO>subjectCodeMap = new HashMap<Integer, SubjectSummaryTO>();
		SubjectSummaryTO subjectSummaryTO = null;
		Set<Integer> subjectsIdsSet = new HashSet<Integer>();

		for(String subject : requiredPercentageReportForm.getSubjects()){
			subjectsIdsSet.add(Integer.parseInt(subject));
		}

		
		if (studentSearchBo != null) {
			Iterator it = studentSearchBo.iterator();
			while (it.hasNext()) {				
			
				AttendanceStudent attendance = (AttendanceStudent)it.next();
				if(attendance.getAttendance() != null && attendance.getAttendance().getSubject()!=null && attendance.getAttendance().getSubject().getCode()!=null){
					subjectSummaryTO = new SubjectSummaryTO();
					subjectSummaryTO.setSubId(attendance.getAttendance().getSubject().getId());
					if(attendance.getAttendance().getSubject().getCode()!= null){
					subjectSummaryTO.setSubjectCode(attendance.getAttendance().getSubject().getCode());
					}
					
					if(subjectsIdsSet.contains(attendance.getAttendance().getSubject().getId())){
						subjectCodeMap.put(attendance.getAttendance().getSubject().getId(), subjectSummaryTO);
					}
					
					
				}
			}
		}
		return subjectCodeMap;
	}
	
	/**
	 * Gets the subjectCode as key and keeps the position as values
	 */
	private static Map<String, Integer> getSubjectCodePosition(List<SubjectSummaryTO> subjectCodeList)throws Exception{
		Map<String, Integer> codeMap = new HashMap<String, Integer>();		
		if(subjectCodeList!=null){
			Iterator<SubjectSummaryTO> iterator = subjectCodeList.iterator();
			int position = 1;	
			String subjectCode="";
			while (iterator.hasNext()) {
				SubjectSummaryTO subjectSummaryTO = iterator.next();
				if(subjectSummaryTO.getSubjectCode()!=null){
				subjectCode = subjectSummaryTO.getSubjectCode();
				codeMap.put(subjectCode, position);
				}
				position = position + 1;
				subjectCode="";
			}
		}
		return codeMap;
	}
	
	/**
	 * Used to get the final studentList
	 * After assigning attendance percentages to the subjects
	 */
	private static List<BelowRequiredPercentageTO> getFinalStudentList(List<BelowRequiredPercentageTO> candidateSearchTOList,Map<String, Integer> codeMap, BelowRequiredPercentageReportForm requiredPercentageReportForm, List<SubjectSummaryTO>subjectCodeList)throws Exception{
		List<BelowRequiredPercentageTO> finalStudentList = new ArrayList<BelowRequiredPercentageTO>();
		SubjectSummaryTO subSummaryTO = null;
		List<SubjectSummaryTO> subSummaryList = null;
		
		BelowRequiredPercentageTO requiredPercentageTO = null;
		
		if(candidateSearchTOList!=null && !candidateSearchTOList.isEmpty()){
			Iterator<BelowRequiredPercentageTO> iterator = candidateSearchTOList.iterator();
			while (iterator.hasNext()) {

				BelowRequiredPercentageTO belowRequiredPercentageTO = iterator.next();
				if(belowRequiredPercentageTO.getSubjectSummaryList()!=null){
					List<SubjectSummaryTO> subjetcSummaryList = belowRequiredPercentageTO.getSubjectSummaryList();
					Collections.sort(subjetcSummaryList);					
					
					requiredPercentageTO = new BelowRequiredPercentageTO();
				
						if(belowRequiredPercentageTO.getRegNo()!=null){
							requiredPercentageTO.setRegNo(belowRequiredPercentageTO.getRegNo());
						}
						if(belowRequiredPercentageTO.getRollNo()!=null){
							requiredPercentageTO.setRollNo(belowRequiredPercentageTO.getRollNo());
						}					
					
					Iterator<SubjectSummaryTO> iterator2 = subjetcSummaryList.iterator();
					
					int position =1;
					int currentPosition=1;

					double held = 0;
					double attended = 0;
					
					subSummaryList = new ArrayList<SubjectSummaryTO>();
					
					while (iterator2.hasNext()) {
						SubjectSummaryTO subjectSummaryTO = iterator2.next();
						if(subjectSummaryTO.getSubjectCode() != null && codeMap.containsKey(subjectSummaryTO.getSubjectCode())){
							currentPosition = codeMap.get(subjectSummaryTO.getSubjectCode());
						
							for(; position <currentPosition; position++){
								subSummaryTO = new SubjectSummaryTO();
								subSummaryTO.setPercentage("");										
								SubjectSummaryTO summaryTO = subjectCodeList.get(position-1);								
								subSummaryTO.setSubjectCode(summaryTO.getSubjectCode());									
								subSummaryList.add(subSummaryTO);
							}
							subSummaryTO = new SubjectSummaryTO();
							

							held = subjectSummaryTO.getHeld();

							attended = subjectSummaryTO.getAttended();						
														
							if(held!=0 && attended!=0){
								double result= attended/held*100;
								subjectSummaryTO.setPercentage(String.valueOf(Integer.valueOf((int)result).intValue()));
							}else{
								subjectSummaryTO.setPercentage(String.valueOf(Integer.valueOf((int)0).intValue()));
							}														
							subSummaryTO.setSubjectCode(subjectSummaryTO.getSubjectCode());														
							subSummaryList.add(subjectSummaryTO);
							position = currentPosition + 1;							
						}
					}					
					if(subSummaryList!=null && subjectCodeList!=null && subSummaryList.size() < subjectCodeList.size()){
						int currentListSize = subSummaryList.size();
						int requiredListSize = subjectCodeList.size();
						
						for(; currentListSize < requiredListSize; currentListSize++){
							SubjectSummaryTO summaryTO = new SubjectSummaryTO();
							summaryTO.setPercentage("");
							SubjectSummaryTO summaryTO2 = subjectCodeList.get(currentListSize);
							summaryTO.setSubjectCode(summaryTO2.getSubjectCode());
							subSummaryList.add(summaryTO);
						}
					}					
					requiredPercentageTO.setSubjectSummaryList(subSummaryList);					
					finalStudentList.add(requiredPercentageTO);
				}
			}				
		}
		return finalStudentList;
	}
}
