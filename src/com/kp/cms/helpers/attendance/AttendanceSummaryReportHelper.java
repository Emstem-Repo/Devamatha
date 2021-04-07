package com.kp.cms.helpers.attendance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceSummaryReportForm;
import com.kp.cms.to.attendance.ActivitySummaryTO;
import com.kp.cms.to.attendance.AttendanceSummaryReportTO;
import com.kp.cms.to.attendance.MonthlyAttendanceSummaryTO;
import com.kp.cms.to.attendance.SubjectSummaryTO;

import com.kp.cms.utilities.CommonUtil;

/**
 * This is a helper class for Attendance Summary Report 
 */
public class AttendanceSummaryReportHelper {
	private static final Log log = LogFactory.getLog(AttendanceSummaryReportHelper.class);
	
	/**
	 * @param studentSearchBo
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will converty BO' to TO's
	 * @throws ApplicationException 
	 */
	public static Map<Integer, List<MonthlyAttendanceSummaryTO>> convertMonthlyBoToTo(
			List<AttendanceStudent> studentSearchBo,
			AttendanceSummaryReportForm attendanceSummaryReportForm)
			throws ApplicationException {
		log.info("entered convertBoToTo..");
		MonthlyAttendanceSummaryTO monthlyAttendanceSummaryTO = null;
		float classesHeld = 0;
		float classesAttended = 0;
		float activityHeld = 0;
		float acitvityAttended = 0;	
		
		Map<Integer, MonthlyAttendanceSummaryTO> monthlyAttendanceSummaryMap = new LinkedHashMap<Integer, MonthlyAttendanceSummaryTO>();
		if (studentSearchBo != null) {
			Iterator<AttendanceStudent> itr = studentSearchBo.iterator();
			while (itr.hasNext()) {
				AttendanceStudent attendanceStudent = (AttendanceStudent) itr
						.next();
              if(attendanceStudent.getStudent().getIsHide().equals(false) ||  attendanceStudent.getStudent().getIsHide()==null )
			{
				if (attendanceStudent.getStudent() != null
						&& attendanceStudent.getStudent().getId() != 0) {
					if (monthlyAttendanceSummaryMap
							.containsKey(attendanceStudent.getStudent().getId())) {

						monthlyAttendanceSummaryTO = monthlyAttendanceSummaryMap
								.get(attendanceStudent.getStudent().getId());

						if (attendanceStudent.getAttendance().getSubject() != null) {
							SubjectSummaryTO subjectSummaryTO = null;
							if (monthlyAttendanceSummaryTO.getSummaryMap()
									.containsKey(
											attendanceStudent.getAttendance()
													.getSubject().getId())) {

								subjectSummaryTO = monthlyAttendanceSummaryTO
										.getSummaryMap().get(
												attendanceStudent
														.getAttendance()
														.getSubject().getId());

							} else {
								if(attendanceSummaryReportForm.getIsSecondLanReq() !=null && attendanceSummaryReportForm.getIsSecondLanReq()) {
									subjectSummaryTO = monthlyAttendanceSummaryTO
									.getSummaryMap().get(-1);
								} else {
									subjectSummaryTO = new SubjectSummaryTO();
								}
								

							}
								if ((subjectSummaryTO.getClassesHeld() == null) || (subjectSummaryTO.getClassesHeld() != null && subjectSummaryTO.getClassesHeld().isEmpty())) {
									classesHeld = attendanceStudent
											.getAttendance()
											.getHoursHeldMonthly();
									subjectSummaryTO.setClassesHeld(String
											.valueOf(Float.valueOf(classesHeld)
													.intValue()));

								} else {
									classesHeld = Float.valueOf(subjectSummaryTO.getClassesHeld())
											+ attendanceStudent.getAttendance()
													.getHoursHeldMonthly();
									subjectSummaryTO.setClassesHeld(String
											.valueOf(Float.valueOf(classesHeld)
													.intValue()));

								}
								if ((subjectSummaryTO.getClassesAttended() == null) || (subjectSummaryTO.getClassesAttended() != null && subjectSummaryTO.getClassesAttended().isEmpty())) {
									classesAttended = attendanceStudent
											.getAttendance().getHoursHeld();
									subjectSummaryTO
											.setClassesAttended(String
													.valueOf(Float.valueOf(
															classesAttended)
															.intValue()));

								} else {
									classesAttended = Float.valueOf(subjectSummaryTO.getClassesAttended())
											+ attendanceStudent.getAttendance()
													.getHoursHeld();
									subjectSummaryTO
											.setClassesAttended(String
													.valueOf(Float.valueOf(
															classesAttended)
															.intValue()));

								}
								
								float result = 0;
								if (classesAttended != 0 && classesHeld != 0) {
									result = (classesAttended / classesHeld) * 100;
									subjectSummaryTO.setPercentage(String
											.valueOf(Math.round(result)));
								} else {
									subjectSummaryTO.setPercentage(String
											.valueOf(Math.round(result)));
								}
							

						}

						else {
							// Activity
							if (attendanceStudent.getAttendance().getActivity() != null) {
								if (monthlyAttendanceSummaryTO.getActivityMap()
										.containsKey(
												attendanceStudent
														.getAttendance()
														.getActivity().getId())) {

									ActivitySummaryTO activitySummaryTO = monthlyAttendanceSummaryTO
											.getActivityMap().get(
													attendanceStudent
															.getAttendance()
															.getActivity()
															.getId());

									activitySummaryTO
											.setActivityName(CMSConstants.NEW_LINE
													+ activitySummaryTO
															.getActivityName());

									activityHeld = Float
											.parseFloat(activitySummaryTO
													.getActivityHeld())
											+ attendanceStudent.getAttendance()
													.getHoursHeldMonthly();
									activitySummaryTO
											.setActivityHeld(CMSConstants.NEW_LINE
													+ Double.valueOf(
															activityHeld)
															.intValue());

									acitvityAttended = Float
									.parseFloat(activitySummaryTO
													.getActivityAttended())
											+ attendanceStudent.getAttendance()
													.getHoursHeld();
									activitySummaryTO
											.setActivityAttended(CMSConstants.NEW_LINE
													+ Float.valueOf(
															acitvityAttended)
															.intValue());

									if (activityHeld != 0
											&& acitvityAttended != 0) {
										float activityResult = acitvityAttended
												/ activityHeld * 100;
										activitySummaryTO
												.setActivityPercentage(CMSConstants.NEW_LINE
														+Math.round(activityResult)
																);
									} else {
										activitySummaryTO
												.setActivityPercentage(CMSConstants.NEW_LINE + 0);
									}
								} else {
									ActivitySummaryTO activitySummaryTO = new ActivitySummaryTO();
									if (attendanceStudent.getAttendance()
											.getActivity().getName() != null) {
										activitySummaryTO
												.setActivityName(CMSConstants.NEW_LINE
														+ attendanceStudent
																.getAttendance()
																.getActivity()
																.getName());
									}

									activityHeld = attendanceStudent
											.getAttendance()
											.getHoursHeldMonthly();

									acitvityAttended = attendanceStudent
											.getAttendance().getHoursHeld();

									activitySummaryTO
											.setActivityHeld(CMSConstants.NEW_LINE
													+ activityHeld);
									activitySummaryTO
											.setActivityAttended(CMSConstants.NEW_LINE
													+ acitvityAttended);
									if (acitvityAttended != 0
											&& activityHeld != 0) {
										float result = acitvityAttended
												/ activityHeld * 100;
										activitySummaryTO
												.setActivityPercentage(CMSConstants.NEW_LINE
														+ Math.round(result));
									} else {
										activitySummaryTO
												.setActivityPercentage(CMSConstants.NEW_LINE + 0);
									}

									monthlyAttendanceSummaryTO.getActivityMap()
											.put(
													attendanceStudent
															.getAttendance()
															.getActivity()
															.getId(),
													activitySummaryTO);

								}

							}
						}

					} else {
						monthlyAttendanceSummaryTO = new MonthlyAttendanceSummaryTO();
						monthlyAttendanceSummaryTO.setStudent(attendanceStudent.getStudent());
						String applicantName = " ";

						if (attendanceStudent.getStudent() != null) {
							String rollNo = "";
							if (attendanceStudent.getStudent().getRollNo() != null) {
								rollNo = attendanceStudent.getStudent()
										.getRollNo();
							}
							
							String registerNo = "";
							
							if (attendanceStudent.getStudent().getRegisterNo() != null) {
								registerNo = attendanceStudent.getStudent()
										.getRegisterNo();
							}

							

							if (!registerNo.isEmpty() && !rollNo.isEmpty()) {
								rollNo = rollNo + "\\" + registerNo;
							} else if (rollNo.isEmpty()) {
								 rollNo = registerNo ;
							}
							monthlyAttendanceSummaryTO.setRegNo(rollNo);
						}

						if (attendanceStudent.getStudent() != null
								&& attendanceStudent.getStudent().getAdmAppln() != null
								&& attendanceStudent.getStudent().getAdmAppln()
										.getPersonalData() != null
								&& attendanceStudent.getStudent().getAdmAppln()
										.getPersonalData().getFirstName() != null) {
							applicantName = applicantName
									+ attendanceStudent.getStudent()
											.getAdmAppln().getPersonalData()
											.getFirstName() + " ";
							//							
						}
						if (attendanceStudent.getStudent().getAdmAppln()
								.getPersonalData().getMiddleName() != null) {
							applicantName = applicantName
									+ attendanceStudent.getStudent()
											.getAdmAppln().getPersonalData()
											.getMiddleName() + " ";
						}
						if (attendanceStudent.getStudent().getAdmAppln()
								.getPersonalData().getLastName() != null) {
							applicantName = applicantName
									+ attendanceStudent.getStudent()
											.getAdmAppln().getPersonalData()
											.getLastName() + " ";
						}
						monthlyAttendanceSummaryTO
								.setStudentName(applicantName);
						monthlyAttendanceSummaryTO
						.setClassId(attendanceStudent.getStudent()
								.getClassSchemewise().getClasses()
								.getId());
				monthlyAttendanceSummaryTO
						.setClassName(attendanceStudent
								.getStudent().getClassSchemewise()
								.getClasses().getName());
						
						
						Iterator<Subject> subjectMapIterator = attendanceSummaryReportForm
								.getSubjectMapList().get(
										attendanceStudent.getStudent()
												.getClassSchemewise()
												.getClasses().getId()).iterator();
						
						if (attendanceSummaryReportForm.getIsSecondLanReq() !=null && attendanceSummaryReportForm.getIsSecondLanReq()) {
							SubjectSummaryTO secondLanguageSummaryTO = new SubjectSummaryTO();
							secondLanguageSummaryTO
									.setSubjectName("SecondLanguage");
							monthlyAttendanceSummaryTO.getSummaryMap().put(-1,
									secondLanguageSummaryTO);

							while (subjectMapIterator.hasNext()) {
								Subject subject = (Subject) subjectMapIterator
										.next();
								if (subject.getIsSecondLanguage()) {
									subjectMapIterator.remove();
								} else {
									SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();

									subjectSummaryTO.setSubjectName(subject
											.getCode());

									monthlyAttendanceSummaryTO.getSummaryMap()
											.put(subject.getId(),
													subjectSummaryTO);
								}
							}

						} else {

							while (subjectMapIterator.hasNext()) {
								Subject subject = (Subject) subjectMapIterator
										.next();

								SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();

								subjectSummaryTO.setSubjectName(subject
										.getCode());

								monthlyAttendanceSummaryTO.getSummaryMap().put(
										subject.getId(), subjectSummaryTO);
							}
						}
					
						
					
						Object[] key = monthlyAttendanceSummaryTO.getSummaryMap().keySet().toArray();
						
						Arrays.sort(key); 
					
						if (attendanceStudent.getAttendance() != null
								&& attendanceStudent.getAttendance()
										.getSubject() != null) {
						
							SubjectSummaryTO subjectSummaryTO = null;
							if (monthlyAttendanceSummaryTO.getSummaryMap()
									.containsKey(
											attendanceStudent.getAttendance()
													.getSubject().getId())) {
								 subjectSummaryTO = monthlyAttendanceSummaryTO
										.getSummaryMap().get(
												attendanceStudent
														.getAttendance()
														.getSubject().getId());

							} else {
								 subjectSummaryTO = monthlyAttendanceSummaryTO
									.getSummaryMap().get(-1);
							}
								classesHeld = attendanceStudent.getAttendance()
										.getHoursHeldMonthly();

								classesAttended = attendanceStudent
										.getAttendance().getHoursHeld();

								subjectSummaryTO.setClassesHeld(String
										.valueOf(classesHeld));
								subjectSummaryTO.setClassesAttended(String
										.valueOf(classesAttended));
								float result = 0;
							
								if (classesAttended != 0 && classesHeld != 0) {
									result = (classesAttended / classesHeld) * 100;
									
									subjectSummaryTO.setPercentage(String
											.valueOf(Math.round(result)));
								} else {
									subjectSummaryTO.setPercentage(String
											.valueOf(Math.round(result)));
								}

							}

						

						else {

							// Activity
							if (attendanceStudent.getAttendance() != null
									&& attendanceStudent.getAttendance()
											.getActivity() != null) {

								ActivitySummaryTO activitySummaryTO = new ActivitySummaryTO();

								if (attendanceStudent.getAttendance()
										.getActivity().getName() != null) {
									activitySummaryTO
											.setActivityName(CMSConstants.NEW_LINE
													+ attendanceStudent
															.getAttendance()
															.getActivity()
															.getName());
								}

								activityHeld = attendanceStudent
										.getAttendance().getHoursHeldMonthly();

								acitvityAttended = attendanceStudent
										.getAttendance().getHoursHeld();

								activitySummaryTO
										.setActivityHeld(CMSConstants.NEW_LINE
												+ activityHeld);
								activitySummaryTO
										.setActivityAttended(CMSConstants.NEW_LINE
												+ acitvityAttended);
								if (acitvityAttended != 0 && activityHeld != 0) {
									float resultActivity = acitvityAttended
											/ activityHeld * 100;
									activitySummaryTO
											.setActivityPercentage(CMSConstants.NEW_LINE
													+ Math.round(resultActivity));
								} else {
									activitySummaryTO
											.setActivityPercentage(CMSConstants.NEW_LINE + 0);
								}
								monthlyAttendanceSummaryTO.getActivityMap()
										.put(
												attendanceStudent
														.getAttendance()
														.getActivity().getId(),
												activitySummaryTO);

							}

						}
						monthlyAttendanceSummaryMap.put(attendanceStudent
								.getStudent().getId(),
								monthlyAttendanceSummaryTO);
					}
				}
			}
			}
		}
		
		
		Iterator<MonthlyAttendanceSummaryTO> monthlyAttendanceTOIterator = monthlyAttendanceSummaryMap
				.values().iterator();

		boolean isRequiredpercentage = false;
		boolean isBelowRequiredAggregate = false;
		boolean isBelowrequiredIndividual = false;

		if (attendanceSummaryReportForm.getRequiredPercentage() != null
				&& !attendanceSummaryReportForm.getRequiredPercentage()
						.isEmpty()) {
			isRequiredpercentage = true;
			if (attendanceSummaryReportForm.getReqPercenAggreg() != null
					&& attendanceSummaryReportForm.getReqPercenAggreg()

					&& attendanceSummaryReportForm.getReqPercenIndivi() != null
					&& attendanceSummaryReportForm.getReqPercenIndivi()) {
				isBelowRequiredAggregate = true;
				isBelowrequiredIndividual = true;

			} else if (attendanceSummaryReportForm.getReqPercenAggreg() != null
					&& attendanceSummaryReportForm.getReqPercenAggreg()
					&& (attendanceSummaryReportForm.getReqPercenIndivi() == null || (attendanceSummaryReportForm
							.getReqPercenIndivi() != null && !attendanceSummaryReportForm
							.getReqPercenIndivi()))) {
				isBelowRequiredAggregate = true;
				isBelowrequiredIndividual = false;
			} else if (attendanceSummaryReportForm.getReqPercenIndivi() != null
					&& attendanceSummaryReportForm.getReqPercenIndivi()
					&& (attendanceSummaryReportForm.getReqPercenAggreg() == null || (attendanceSummaryReportForm
							.getReqPercenAggreg() != null && !attendanceSummaryReportForm
							.getReqPercenAggreg()))) {
				isBelowRequiredAggregate = false;
				isBelowrequiredIndividual = true;

			}

		}

		Map<Integer,List<MonthlyAttendanceSummaryTO>> classesStudentsMap = new HashMap<Integer, List<MonthlyAttendanceSummaryTO>>();
			while (monthlyAttendanceTOIterator.hasNext()) {
			MonthlyAttendanceSummaryTO monthlyAttendanceSummaryTO2 = (MonthlyAttendanceSummaryTO) monthlyAttendanceTOIterator
					.next();
			boolean isValied = calculateAggregatePercentage(
					monthlyAttendanceSummaryTO2, attendanceSummaryReportForm,
					isBelowRequiredAggregate, isBelowrequiredIndividual);

			if (isRequiredpercentage) {
				if (isBelowRequiredAggregate && isBelowrequiredIndividual
						&& !isValied) {
					addMonthlyAttendanceDataToMap(classesStudentsMap,
							monthlyAttendanceSummaryTO2);
				} else if (isBelowRequiredAggregate
						&& !isBelowrequiredIndividual && !isValied) {
					addMonthlyAttendanceDataToMap(classesStudentsMap,
							monthlyAttendanceSummaryTO2);
				} else if (!isBelowRequiredAggregate
						&& isBelowrequiredIndividual && !isValied) {
					addMonthlyAttendanceDataToMap(classesStudentsMap,
							monthlyAttendanceSummaryTO2);
				} else if (!isBelowRequiredAggregate
						&& !isBelowrequiredIndividual) {
					addMonthlyAttendanceDataToMap(classesStudentsMap,
							monthlyAttendanceSummaryTO2);
				}
			} else {
				addMonthlyAttendanceDataToMap(classesStudentsMap,
						monthlyAttendanceSummaryTO2);
			}
		}
		return classesStudentsMap;
	}


	private static void addMonthlyAttendanceDataToMap(
			Map<Integer, List<MonthlyAttendanceSummaryTO>> classesStudentsMap,
			MonthlyAttendanceSummaryTO monthlyAttendanceSummaryTO2) {
		List<MonthlyAttendanceSummaryTO> monthlyAttendanceTOSummaryList;
		if (classesStudentsMap
				.containsKey(monthlyAttendanceSummaryTO2
						.getClassId())) {
			monthlyAttendanceTOSummaryList = classesStudentsMap
					.get(monthlyAttendanceSummaryTO2.getClassId());
			monthlyAttendanceTOSummaryList
					.add(monthlyAttendanceSummaryTO2);
		} else {
			monthlyAttendanceTOSummaryList = new ArrayList<MonthlyAttendanceSummaryTO>();
			monthlyAttendanceTOSummaryList
					.add(monthlyAttendanceSummaryTO2);
		}
		classesStudentsMap.put(monthlyAttendanceSummaryTO2
						.getClassId(), monthlyAttendanceTOSummaryList);
	}	
	

	
	
	/**
	 * Calculates Aggregate percentage
	 * @param monthlyAttendanceSummaryTO2
	 * @param attendanceSummaryReport
	 * @param isBelowRequiredAggregate
	 * @param isBelowrequiredIndividual
	 * @return
	 */
	private static boolean calculateAggregatePercentage(
			MonthlyAttendanceSummaryTO monthlyAttendanceSummaryTO2,
			AttendanceSummaryReportForm attendanceSummaryReport,
			boolean isBelowRequiredAggregate, boolean isBelowrequiredIndividual) {

		Iterator<ApplicantSubjectGroup> applicantSubjectGroupIterator = monthlyAttendanceSummaryTO2
				.getStudent().getAdmAppln().getApplicantSubjectGroups()
				.iterator();
		StringBuilder secondLanguage=new StringBuilder();
		List<String> subjectCodeList = new ArrayList<String>();
		while (applicantSubjectGroupIterator.hasNext()) {
			ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) applicantSubjectGroupIterator
					.next();
			
			Iterator<SubjectGroupSubjects> subjectGroupSubject = applicantSubjectGroup
					.getSubjectGroup().getSubjectGroupSubjectses()
					.iterator();
			while (subjectGroupSubject.hasNext()) {
				SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subjectGroupSubject
						.next();
				
				if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getCode() !=null) {
					subjectCodeList.add(subjectGroupSubjects.getSubject().getCode());
					
				}
				
				if (subjectGroupSubjects.getSubject() != null && subjectGroupSubjects.getSubject().getIsSecondLanguage()) {
					if (subjectGroupSubjects.getSubject().getCode() != null
							&& !secondLanguage.toString()
									.contains(subjectGroupSubjects
											.getSubject().getCode())) {
						if (secondLanguage.toString().trim().length() > 0) {
							secondLanguage.append(",");
						}
						secondLanguage.append(subjectGroupSubjects.getSubject()
								.getCode());
					}

				}

			}
		}
	if (attendanceSummaryReport.getIsSecondLanReq() != null
				&& attendanceSummaryReport.getIsSecondLanReq()) {

			monthlyAttendanceSummaryTO2.setSecondLanguage(secondLanguage.toString());
		}
		Iterator<Integer> subjectSummaryIterator = monthlyAttendanceSummaryTO2
				.getSummaryMap().keySet().iterator();
		
		Float aggrigatepercentagecalculated = new Float(0.0);
		boolean isValied = true;
		float aggregatePercentage = 0;
		if (attendanceSummaryReport.getRequiredPercentage() != null
				&& !attendanceSummaryReport.getRequiredPercentage().isEmpty()) {
			aggregatePercentage = Float.valueOf(attendanceSummaryReport
					.getRequiredPercentage());
		}

		int noOfSubjects = 0;

		while (subjectSummaryIterator.hasNext()) {
			Integer key = (Integer) subjectSummaryIterator.next();

			SubjectSummaryTO subjectSummaryTO = monthlyAttendanceSummaryTO2
					.getSummaryMap().get(key);

			float individualPercentage = 0;
			if (subjectSummaryTO.getPercentage() != null
					&& !subjectSummaryTO.getPercentage().isEmpty()) {
				individualPercentage = +Float.parseFloat(subjectSummaryTO
						.getPercentage());
				noOfSubjects = noOfSubjects + 1;
				if (isBelowrequiredIndividual
						&& (individualPercentage < aggregatePercentage)) {
					isValied = false;
				}
			} else if(!subjectCodeList.contains(subjectSummaryTO.getSubjectName())) {
				subjectSummaryTO.setPercentage("-");
			}
			aggrigatepercentagecalculated = aggrigatepercentagecalculated
					+ individualPercentage;

		}
		float percentage = 0;
		if (noOfSubjects > 0) {
			percentage = (aggrigatepercentagecalculated / noOfSubjects);
			if (isBelowRequiredAggregate && (percentage < aggregatePercentage)) {
				isValied = false;
			}else if(isBelowRequiredAggregate && percentage > aggregatePercentage ) {
				isValied = true;
			}
		}

		monthlyAttendanceSummaryTO2.setAggrigatePercentage(String.valueOf(Math
				.round(percentage)));
		monthlyAttendanceSummaryTO2.getSubjectSummaryList().addAll(
				monthlyAttendanceSummaryTO2.getSummaryMap().values());
		monthlyAttendanceSummaryTO2.getActivitySummaryTOlist().addAll(
				monthlyAttendanceSummaryTO2.getActivityMap().values());

		Iterator<Subject> subjectIterator = attendanceSummaryReport
				.getSubjectMap().values().iterator();
		while (subjectIterator.hasNext()) {
			Subject subject = (Subject) subjectIterator.next();

			StringBuffer subjectCodeName = new StringBuffer();
			if (subject.getCode() != null) {
				subjectCodeName.append(subject.getCode());
			}
			subjectCodeName.append("--");
			if (subject.getName() != null) {
				subjectCodeName.append(subject.getName());
			}
			monthlyAttendanceSummaryTO2.getSubjectCodeNameList().add(
					subjectCodeName.toString().trim());

		}

		return isValied;
	}

	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String monthlyCommonSearch(AttendanceSummaryReportForm attendanceSummaryReportForm) {
		log.info("entered monthlyCommonSearch..");
		String searchCriteria = "";

		if (attendanceSummaryReportForm.getClassesName().length > 0) {
			String [] classArray = attendanceSummaryReportForm.getClassesName();
			StringBuilder className=new StringBuilder();
			for(int i=0;i<classArray.length;i++){
				className.append(classArray[i]);
				if(i<(classArray.length-1)){
					className.append(",");
				}
			}
			String classType = " attendanceStudent.student.classSchemewise.id in ("
				+ className +")";
			searchCriteria = searchCriteria + classType;
		}
		

		if (attendanceSummaryReportForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ attendanceSummaryReportForm.getAcademicYear();
			String admApplnStr = " and attendanceStudent.student.admAppln.isCancelled = 0";
			searchCriteria = searchCriteria + appliedYear + admApplnStr;
		}

		if (attendanceSummaryReportForm.getAttendanceType().length > 0) {
			String [] tempArray = attendanceSummaryReportForm.getAttendanceType();
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
			String monthlyAttn = " and attendanceStudent.attendance.isMonthlyAttendance = 1 ";
			searchCriteria = searchCriteria + attendanceType + canclledAttn + monthlyAttn;
		}
		

		if (attendanceSummaryReportForm.getStartDate().trim().length() > 0) {
			String startDate = " and attendanceStudent.attendance.attendanceDate >= '"
				+ CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'" ;
			searchCriteria = searchCriteria + startDate;
		}

		if (attendanceSummaryReportForm.getEndDate().trim().length() > 0) {
			String endDate = " and attendanceStudent.attendance.attendanceDate <= '"
				+ CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'";
			searchCriteria = searchCriteria + endDate;
		}
		log.info("exit monthlyCommonSearch..");
		return searchCriteria;
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getMonthlySelectionSearchCriteria(
			AttendanceSummaryReportForm studentSearchForm) {
		log.info("entered getMonthlySelectionSearchCriteria..");
		String statusCriteria = monthlyCommonSearch(studentSearchForm);

		String searchCriteria= "";

		//		String ednJoin = "";
		searchCriteria =  " from AttendanceStudent attendanceStudent "
			+ " where " + statusCriteria + " order by attendanceStudent.student.classSchemewise.classes.id";					
		log.info("exit getMonthlySelectionSearchCriteria..");
		return searchCriteria;

	}



	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(AttendanceSummaryReportForm attendanceSummaryReportForm) {
		log.info("entered commonSearch..");
		String searchCriteria = "";

		if (attendanceSummaryReportForm.getClassesName().length > 0) {
			String [] classArray = attendanceSummaryReportForm.getClassesName();
			StringBuilder className = new StringBuilder();
			for(int i=0;i<classArray.length;i++){
				className.append(classArray[i]);
				if(i<(classArray.length-1)){
					className.append(",");
				}
			}
			String classType = " attendanceStudent.student.classSchemewise.classes.id in ("
				+ className +")";
			searchCriteria = searchCriteria + classType;
			searchCriteria = searchCriteria + " and ac.classSchemewise.classes.id in ("  +  className + ")";
		}

		if (attendanceSummaryReportForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ attendanceSummaryReportForm.getAcademicYear();
			String admApplnStr = " and attendanceStudent.student.admAppln.isCancelled = 0 and attendanceStudent.student.isActive = 1";
			searchCriteria = searchCriteria + appliedYear + admApplnStr;
		}

		if (attendanceSummaryReportForm.getAttendanceType().length > 0) {
			String [] tempArray = attendanceSummaryReportForm.getAttendanceType();
			String attType ="";
			for(int i=0;i<tempArray.length;i++){
				attType = attType+tempArray[i];
				if(i<(tempArray.length-1)){
					attType = attType+",";
				}
			}
            attendanceSummaryReportForm.setAttType(attType);
            if(attType.contains("2"))
            	attendanceSummaryReportForm.setIsPractical(true);
            else
            	attendanceSummaryReportForm.setIsPractical(false);
            if(attType.contains("1"))
            	attendanceSummaryReportForm.setIsTheory(true);
            else
            	attendanceSummaryReportForm.setIsTheory(false);
            if(attType.contains("1,2"))
            	attendanceSummaryReportForm.setIsTheoryNPractical(true);
            else
            	attendanceSummaryReportForm.setIsTheoryNPractical(false);
			String attendanceType = " and attendanceStudent.attendance.attendanceType in ("
				+ attType +")";
			String canclledAttn = " and attendanceStudent.attendance.isCanceled = 0 ";
			String monthlyAttn = " and attendanceStudent.attendance.isMonthlyAttendance = 0 ";
			searchCriteria = searchCriteria + attendanceType + canclledAttn + monthlyAttn;
			
			searchCriteria=searchCriteria+" and ac.classSchemewise.id=attendanceStudent.student.classSchemewise.id ";
		}
//		if (attendanceSummaryReportForm.getActivityId().trim().length() > 0) {
//			String activityId = " and attendanceStudent.attendance.activity <> null and attendanceStudent.attendance.activity = "
//				+ attendanceSummaryReportForm.getActivityId();
//			searchCriteria = searchCriteria + activityId;
//		}
		//		if (attendanceSummaryReportForm.getLeaveType()!=null ) {
		//			String attenLeave = " and attendanceStudents.isOnLeave = "
		//					+ attendanceSummaryReportForm.getLeaveType();
		//			searchCriteria = searchCriteria + attenLeave;
		//		}

		if (attendanceSummaryReportForm.getStartDate().trim().length() > 0) {
			String startDate = " and attendanceStudent.attendance.attendanceDate >= '"
				+ CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'" ;
			searchCriteria = searchCriteria + startDate;
		}

		if (attendanceSummaryReportForm.getEndDate().trim().length() > 0) {
			String endDate = " and attendanceStudent.attendance.attendanceDate <= '"
				+ CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'";
			searchCriteria = searchCriteria + endDate;
		}
		if (attendanceSummaryReportForm.getStartRegisterNo()!=null && attendanceSummaryReportForm.getStartRegisterNo().trim().length() > 0 && attendanceSummaryReportForm.getEndRegisterNo()!=null && attendanceSummaryReportForm.getEndRegisterNo().trim().length() > 0) {
			String betweenRegisterNo = " and attendanceStudent.student.registerNo between "+
					"'"+ attendanceSummaryReportForm.getStartRegisterNo()+"'" +
					 " and " +
					 "'" + attendanceSummaryReportForm.getEndRegisterNo()+"' " ;
			searchCriteria = searchCriteria + betweenRegisterNo;
		}else if (attendanceSummaryReportForm.getStartRegisterNo()!=null && attendanceSummaryReportForm.getStartRegisterNo().trim().length() > 0) {
			String fromRegisterNo = " and attendanceStudent.student.registerNo >= "+
					"'"+ attendanceSummaryReportForm.getStartRegisterNo()+"'";
			searchCriteria = searchCriteria + fromRegisterNo;
		}else if(attendanceSummaryReportForm.getStartRollNo()!=null && attendanceSummaryReportForm.getStartRollNo().trim().length() > 0 && attendanceSummaryReportForm.getEndRollNo()!=null && attendanceSummaryReportForm.getEndRollNo().trim().length() > 0){
			String betweenRollNo = " and attendanceStudent.student.rollNo between "+
			"'"+ attendanceSummaryReportForm.getStartRollNo()+"'" +
			 " and " +
			 "'" + attendanceSummaryReportForm.getEndRollNo()+"' " ;
			searchCriteria = searchCriteria + betweenRollNo;
		}else if(attendanceSummaryReportForm.getStartRollNo()!=null && attendanceSummaryReportForm.getStartRollNo().trim().length() > 0) {
			String fromRollNo = " and attendanceStudent.student.rollNo >= "+
			"'"+ attendanceSummaryReportForm.getStartRollNo()+"'";
			searchCriteria = searchCriteria + fromRollNo;
		}
		
		log.info("exit commonSearch..");
		return searchCriteria;
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(
			AttendanceSummaryReportForm studentSearchForm) {
 		log.info("entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(studentSearchForm);

		String searchCriteria= "";

		//		String ednJoin = "";
		searchCriteria =  "select attendanceStudent from AttendanceStudent attendanceStudent" +
				" inner join attendanceStudent.attendance att " +
				" inner join att.attendanceClasses ac"
			+ " where " + statusCriteria + " order by attendanceStudent.student.classSchemewise.classes.id";					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}
	/**
	 * @param studentSearchBo
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will converty BO' to TO's
	 * @throws ApplicationException 
	 */
	/**
	 * @param studentSearchBo
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will converty BO' to TO's
	 * @throws ApplicationException 
	 */
	public static  Map<Integer, List<AttendanceSummaryReportTO>> convertBoToTo(List<Object[]> studentSearchBo,AttendanceSummaryReportForm attendanceSummaryReportForm,List<Integer> detainedStudentList) throws ApplicationException {
		//		DecimalFormat decimalFormat = new DecimalFormat("#,###,###,##0.00");
		Map<Integer, AttendanceSummaryReportTO> attendanceSummaryMap = new LinkedHashMap<Integer, AttendanceSummaryReportTO>();
		attendanceSummaryReportForm.setIsActivity("false");
		if (studentSearchBo != null) 
		{
			Iterator itr = studentSearchBo.iterator();
		
  			while (itr.hasNext()) 
			{
 				AttendanceSummaryReportTO attendanceSummaryReportTO = new AttendanceSummaryReportTO();
				AttendanceStudent attendanceStudent = (AttendanceStudent) itr.next();
			//	if(attendanceStudent.getStudent().getRegisterNo().equalsIgnoreCase("1214819")){
			//	if(attendanceStudent.getStudent().getRegisterNo()!=null && !attendanceStudent.getStudent().getRegisterNo().isEmpty() && attendanceStudent.getStudent().getRegisterNo().equalsIgnoreCase("1214819")||attendanceStudent.getStudent().getRegisterNo().equalsIgnoreCase("1214844")||attendanceStudent.getStudent().getRegisterNo().equalsIgnoreCase("1214849")||attendanceStudent.getStudent().getRegisterNo().equalsIgnoreCase("1214842")){
				if(detainedStudentList!=null){
					Boolean isHide=false;
					if(attendanceStudent.getStudent().getIsHide()!=null){
						isHide=attendanceStudent.getStudent().getIsHide();
					}
				if((isHide.equals(false) || attendanceStudent.getStudent().getIsHide() == null)&& !detainedStudentList.contains(attendanceStudent.getStudent().getRegisterNo()))
				{
				if (attendanceStudent.getStudent() != null	&& attendanceStudent.getStudent().getId() != 0) 
				{	
					
					if (attendanceSummaryMap.containsKey(attendanceStudent.getStudent().getId()))   
					
					{
						float activityHeld = 0;
						float acitvityAttended = 0;
						float activityWithLeaveAttended=0;
						//attendanceSummaryReportTO=new AttendanceSummaryReportTO();
						attendanceSummaryReportTO = attendanceSummaryMap.get(attendanceStudent.getStudent().getId());
						if (attendanceStudent.getAttendance().getSubject() != null ) 
						{
							String name=attendanceStudent.getAttendance().getSubject().getName();
							//Subject start
							SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();
							if (attendanceSummaryReportTO.getSummaryMap().containsKey(attendanceStudent.getAttendance().getSubject().getId()) && attendanceSummaryReportTO.getClassId()==attendanceStudent.getStudent().getClassSchemewise().getClasses().getId()) 
							{
								subjectSummaryTO = attendanceSummaryReportTO.getSummaryMap().get(	attendanceStudent.getAttendance().getSubject().getId());
							}
							else
							{							
								if(attendanceSummaryReportForm.getIsSecondLanReq() !=null && attendanceSummaryReportForm.getIsSecondLanReq()) 
								{
									subjectSummaryTO = attendanceSummaryReportTO.getSummaryMap().get(-1);
								}
								else 
								{
									continue;
									
								}
							}
							
							
							subjectSummaryTO=setAttendance(subjectSummaryTO,attendanceStudent,attendanceSummaryReportForm,attendanceSummaryReportTO);
//							attendanceSummaryReportForm.setIsActivity("false");
						}
							
							
							/*int id=attendanceStudent.getAttendance().getAttendanceType().getId();
							if((subjectSummaryTO.getClassesHeld() == null) || (subjectSummaryTO.getClassesHeld() != null && subjectSummaryTO.getClassesHeld().isEmpty())) 
							{
								classesHeld = attendanceStudent.getAttendance().getHoursHeld();
								subjectSummaryTO.setClassesHeld(String.valueOf(Math.round(classesHeld)));
							}
							else
							{
								classesHeld = Float.parseFloat(subjectSummaryTO.getClassesHeld())+ attendanceStudent.getAttendance().getHoursHeld();
								subjectSummaryTO.setClassesHeld(String.valueOf(Math.round(classesHeld)));
							}	
							
							if(subjectSummaryTO.getClassesAttended()== null) 
							{
								if(attendanceStudent.getIsPresent())
								{								
									classesAttended = attendanceStudent.getAttendance().getHoursHeld();
								}
								else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
								{
									//classesAttended = attendanceStudent.getAttendance().getHoursHeld();
									classesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
									attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
								}
								else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
								{
									classesAttended = attendanceStudent.getAttendance().getHoursHeld();
									//attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
								}
								else
								{
									if(!attendanceStudent.getIsPresent())
									{
										classesAttended = 0;
									}
								}
							}
							else
							{
								if(attendanceStudent.getIsPresent())
								{								
									classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
									classesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
								}
								else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
								{
									//classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
									classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended());
									classesWithLeaveAttended = Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave())+ attendanceStudent.getAttendance().getHoursHeld();
									attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
								}
								else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
								{
									classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
									classesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
									//classesWithLeaveAttended = Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave())+ attendanceStudent.getAttendance().getHoursHeld();
									//attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
								}
								else
								{
									if(!attendanceStudent.getIsPresent())
									{
										classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ 0;
										classesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
									}
								}
								
							}	
							subjectSummaryTO.setClassesAttended(String.valueOf(Math.round(classesAttended)));
							subjectSummaryTO.setClassesAttendedWithLeave(String.valueOf(classesWithLeaveAttended));
							if (classesAttended != 0 && classesHeld != 0) 
							{
								float  result = classesAttended/ classesHeld * 100;
								subjectSummaryTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
							} 
							else 
							{
								subjectSummaryTO.setPercentage(String.valueOf(0));
							}
						}
						*/
						else
						{
							//Activity Start
							if (attendanceStudent.getAttendance() != null)
							{
								if (attendanceSummaryReportTO.getActivityMap().containsKey(	attendanceStudent.getStudent().getId()))
								{
									ActivitySummaryTO activitySummaryTO = attendanceSummaryReportTO.getActivityMap().get(attendanceStudent.getStudent().getId());
									if(attendanceStudent.getAttendance().getHoursHeld()!=0)
									{
										activityHeld = Float.parseFloat(activitySummaryTO.getActivityHeld())+ attendanceStudent.getAttendance().getHoursHeld();
										if(attendanceStudent.getIsPresent())
										{	
											float acitvityAtt = 0;
											if(activitySummaryTO.getActivityAttended() != null && !activitySummaryTO.getActivityAttended().isEmpty()){
												acitvityAtt = Float.parseFloat(activitySummaryTO.getActivityAttended());
											}
											acitvityAttended = acitvityAtt + attendanceStudent.getAttendance().getHoursHeld();
											activityWithLeaveAttended=Float.parseFloat(activitySummaryTO.getActivityWithLeaveAttended());
										}
										else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
										{
											float acitvityAtt = 0;
											if(activitySummaryTO.getActivityAttended() != null && !activitySummaryTO.getActivityAttended().isEmpty()){
												acitvityAtt = Float.parseFloat(activitySummaryTO.getActivityAttended());
											}
											acitvityAttended = acitvityAtt;
											float acitvityAttWithLeave = 0;
											if(activitySummaryTO.getActivityWithLeaveAttended() != null && !activitySummaryTO.getActivityWithLeaveAttended().isEmpty()){
												acitvityAttWithLeave = Float.parseFloat(activitySummaryTO.getActivityWithLeaveAttended());
											}
											activityWithLeaveAttended = acitvityAttWithLeave + attendanceStudent.getAttendance().getHoursHeld();
											attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
										}
										else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
										{
											float acitvityAtt = 0;
											if(activitySummaryTO.getActivityAttended() != null && !activitySummaryTO.getActivityAttended().isEmpty()){
												acitvityAtt = Float.parseFloat(activitySummaryTO.getActivityAttended());
											}
											acitvityAttended = acitvityAtt + attendanceStudent.getAttendance().getHoursHeld();
											float acitvityAttWithLeave = 0;
											if(activitySummaryTO.getActivityWithLeaveAttended() != null && !activitySummaryTO.getActivityWithLeaveAttended().isEmpty()){
												acitvityAttWithLeave = Float.parseFloat(activitySummaryTO.getActivityWithLeaveAttended());
											}
											activityWithLeaveAttended=acitvityAttWithLeave;
										}
										else
										{
											if(!attendanceStudent.getIsPresent())
											{
												float acitvityAtt = 0;
												if(activitySummaryTO.getActivityAttended() != null && !activitySummaryTO.getActivityAttended().isEmpty()){
													acitvityAtt = Float.parseFloat(activitySummaryTO.getActivityAttended());
												}
												acitvityAttended = acitvityAtt + 0;
												activityWithLeaveAttended = Float.parseFloat(activitySummaryTO.getActivityWithLeaveAttended());
											}
											
										}	
									}
									activitySummaryTO.setActivityHeld(String.valueOf(Math.round(activityHeld)));
									activitySummaryTO.setActivityAttended(String.valueOf(Math.round(acitvityAttended)));
									activitySummaryTO.setActivityWithLeaveAttended(String.valueOf(Math.round(activityWithLeaveAttended)));
//									attendanceSummaryReportForm.setIsActivity("true");
									if (acitvityAttended != 0 && activityHeld != 0) 
									{
										float  result = acitvityAttended/ activityHeld * 100;
										activitySummaryTO.setActivityPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
									}
									else 
									{
										activitySummaryTO.setActivityPercentage(String.valueOf(0));
									}
								}
								else
								{
									ActivitySummaryTO activitySummaryTO = new ActivitySummaryTO();
									/*if (attendanceStudent.getAttendance().getActivity().getName() != null) 
									{
										activitySummaryTO.setActivityName(attendanceStudent.getAttendance().getActivity().getName());
									}*/
									if (attendanceStudent.getAttendance().getHoursHeld() != 0) 
									{
										activityHeld = attendanceStudent.getAttendance().getHoursHeld();
										if(attendanceStudent.getIsPresent())
										{
											acitvityAttended = attendanceStudent.getAttendance().getHoursHeld();
										}
										else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
										{
											float activityWithLeave = 0;
											if(activitySummaryTO.getActivityWithLeaveAttended() != null && !activitySummaryTO.getActivityWithLeaveAttended().isEmpty()){
												activityWithLeave = Float.parseFloat(activitySummaryTO.getActivityWithLeaveAttended());
											}
											activityWithLeaveAttended = activityWithLeave + attendanceStudent.getAttendance().getHoursHeld();
											attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
										}
										else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
										{
											float activityAtt = 0;
											if(activitySummaryTO.getActivityAttended() != null && !activitySummaryTO.getActivityAttended().isEmpty()){
												activityAtt = Float.parseFloat(activitySummaryTO.getActivityAttended());
											}
											acitvityAttended = activityAtt + attendanceStudent.getAttendance().getHoursHeld();
										}
										else
										{
											if(!attendanceStudent.getIsPresent())
											{
												acitvityAttended =  0;
											}
										}
									}
//									else{
//										activityHeld = 1;
//
//										if(attendanceStudent.getIsPresent()){
//											acitvityAttended = 1;
//										}else{
//											acitvityAttended = 0;
//										}
//
//									}
									activitySummaryTO.setActivityHeld(String.valueOf(Math.round(activityHeld)));
									activitySummaryTO.setActivityAttended(String.valueOf(Math.round(acitvityAttended)));
									activitySummaryTO.setActivityWithLeaveAttended(String.valueOf(activityWithLeaveAttended));
//									attendanceSummaryReportForm.setIsActivity("true");
									if (acitvityAttended != 0 && activityHeld != 0) 
									{
										float  result = acitvityAttended/ activityHeld * 100;
										//activitySummaryTO.setActivityPercentage(String.valueOf(Math.round(result)));
										activitySummaryTO.setActivityPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
									}
									else
									{
										activitySummaryTO.setActivityPercentage(String.valueOf(0));
									}
									//attendanceSummaryReportTO.getActivityMap().put(attendanceStudent.getAttendance().getActivity().getId(),activitySummaryTO);
									attendanceSummaryReportTO.getActivityMap().put(attendanceStudent.getStudent().getId(),activitySummaryTO);
								}	
							}
						//Activity End
					}  }                                   //
					else 
					{
						float classesHeld = 0;
						float classesAttended = 0;
						float classesWithLeaveAttended = 0;
						float activityHeld = 0;
						float acitvityAttended = 0;
						float activityWithLeaveAttended=0;
						//attendanceSummaryReportTO = new AttendanceSummaryReportTO();
						if(attendanceStudent.getStudent()!=null)
						{
							attendanceSummaryReportTO.setStudent(attendanceStudent.getStudent());
						}
						String applicantName = "";
						if (attendanceStudent.getStudent() != null) 
						{
							if(attendanceSummaryReportForm.isKjcReport())
							{
								String rollNo = "";
								if (attendanceStudent.getStudent().getRollNo() != null && !attendanceStudent.getStudent().getRollNo().isEmpty()) 
								{
									rollNo = attendanceStudent.getStudent().getRollNo();
								}
								attendanceSummaryReportTO.setRollNo(rollNo);
								String registerNo = "";
								
								if (attendanceStudent.getStudent().getRegisterNo() != null && !attendanceStudent.getStudent().getRegisterNo().isEmpty()) 
								{
									registerNo = attendanceStudent.getStudent().getRegisterNo();
								}
								attendanceSummaryReportTO.setRegNo(registerNo);
								
							}
							else
							{
								String rollNo = "";
								if (attendanceStudent.getStudent().getRollNo() != null && !attendanceStudent.getStudent().getRollNo().isEmpty()) 
								{
									rollNo = attendanceStudent.getStudent().getRollNo();
								}
								
								String registerNo = "";
								
								if (attendanceStudent.getStudent().getRegisterNo() != null && !attendanceStudent.getStudent().getRegisterNo().isEmpty()) 
								{
									registerNo = attendanceStudent.getStudent().getRegisterNo();
								}
								
								if (!registerNo.isEmpty() && !rollNo.isEmpty()) 
								{
									rollNo = rollNo + "\\" + registerNo;
								} 
								else if (rollNo.isEmpty()) 
								{
									rollNo = registerNo ;
								}
								attendanceSummaryReportTO.setRegNo(rollNo);
							}
						}
						if (attendanceStudent.getStudent() != null	&& attendanceStudent.getStudent().getAdmAppln() != null && attendanceStudent.getStudent().getAdmAppln().getPersonalData() != null && (attendanceStudent.getStudent().getAdmAppln().getPersonalData().getFirstName() != null && !attendanceStudent.getStudent().getAdmAppln().getPersonalData().getFirstName().isEmpty())) 
						{
							applicantName = applicantName + attendanceStudent.getStudent().getAdmAppln().getPersonalData().getFirstName()+" ";
							//							
						}
						if (attendanceStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName() != null) 
						{
							applicantName = applicantName+ attendanceStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName() + " ";
						}					if (attendanceStudent.getStudent().getAdmAppln().getPersonalData().getLastName() != null) 
						{
							applicantName = applicantName+ attendanceStudent.getStudent().getAdmAppln().getPersonalData().getLastName()+ " ";
						}
						attendanceSummaryReportTO.setStudentName(applicantName);
						if(attendanceStudent.getStudent()!=null && attendanceStudent.getStudent().getClassSchemewise()!=null && attendanceStudent.getStudent().getClassSchemewise().getClasses()!=null && attendanceStudent.getStudent().getClassSchemewise().getClasses().getId()!=0)
						{
							attendanceSummaryReportTO.setClassId(attendanceStudent.getStudent().getClassSchemewise().getClasses().getId());
						}
						if(attendanceStudent.getStudent()!=null && attendanceStudent.getStudent().getClassSchemewise()!=null && attendanceStudent.getStudent().getClassSchemewise().getClasses()!=null && attendanceStudent.getStudent().getClassSchemewise().getClasses().getName()!=null)
						{
							attendanceSummaryReportTO.setClassName(attendanceStudent.getStudent().getClassSchemewise().getClasses().getName());
						}
						Iterator<Subject> subjectMapIterator=null;
						if(attendanceSummaryReportForm.getSubjectMapList()!=null && !attendanceSummaryReportForm.getSubjectMapList().isEmpty()){
							List<Subject> list = attendanceSummaryReportForm.getSubjectMapList().get(attendanceStudent.getStudent().getClassSchemewise().getClasses().getId());
							if(list != null){
								subjectMapIterator = list.iterator();
								
//						while (subjectMapIterator.hasNext()) {
//							Subject subject = (Subject) subjectMapIterator.next();
//
//							SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();
//							if(attendanceSummaryReportForm.getIsSecondLanReq()!=null && attendanceSummaryReportForm.getIsSecondLanReq() && subject.getIsSecondLanguage()) {
//
//								subjectSummaryTO.setSubjectName("Second Language \n"+subject.getCode());
//							} else {
//								subjectSummaryTO.setSubjectName(subject.getCode());
//							}
//							attendanceSummaryReportTO.getSummaryMap().put(subject.getId(), subjectSummaryTO);
//
//						}// end of Iterator
								
								//
								if (attendanceSummaryReportForm.getIsSecondLanReq() !=null && attendanceSummaryReportForm.getIsSecondLanReq()) 
								{
									SubjectSummaryTO secondLanguageSummaryTO = new SubjectSummaryTO();
									secondLanguageSummaryTO.setSubjectName("SecondLanguage");
									attendanceSummaryReportTO.getSummaryMap().put(-1,secondLanguageSummaryTO);
									while (subjectMapIterator.hasNext()) 
									{
										Subject subject = (Subject) subjectMapIterator.next();
										if (subject.getIsSecondLanguage()) 
										{
											subjectMapIterator.remove();
										} 
										else 
										{
											SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();
											subjectSummaryTO.setSubjectName(subject.getCode());
											attendanceSummaryReportTO.getSummaryMap().put(subject.getId(),subjectSummaryTO);
										}
									}
								} 
								else 
								{
									while (subjectMapIterator.hasNext()) 
									{
										Subject subject = (Subject) subjectMapIterator.next();
										SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();
										subjectSummaryTO.setSubjectName(subject.getCode());
										attendanceSummaryReportTO.getSummaryMap().put(subject.getId(), subjectSummaryTO);
									}
								}
							}
					}
						Object[] key = attendanceSummaryReportTO.getSummaryMap().keySet().toArray();
						Arrays.sort(key);
						if (attendanceStudent.getAttendance() != null && attendanceStudent.getAttendance().getSubject() != null) 
						{
							// Set subjects in map
							SubjectSummaryTO subjectSummaryTO = null;
							if (attendanceSummaryReportTO.getSummaryMap().containsKey(attendanceStudent.getAttendance().getSubject().getId())) 
							{
								subjectSummaryTO = attendanceSummaryReportTO.getSummaryMap().get(attendanceStudent.getAttendance().getSubject().getId());
							}
							else 
							if(attendanceSummaryReportForm.getIsSecondLanReq() !=null && attendanceSummaryReportForm.getIsSecondLanReq())
							{
								 subjectSummaryTO = attendanceSummaryReportTO.getSummaryMap().get(-1);
							}
							//Subject Start
							//							SubjectSummaryTO subjectSummaryTO = new SubjectSummaryTO();
							//							if (attendanceStudent.getAttendance().getSubject().getName() != null) {
							//								subjectSummaryTO.setSubjectName(attendanceStudent.getAttendance().getSubject().getName());
							//							}
						if (attendanceSummaryReportTO.getSummaryMap().containsKey(attendanceStudent.getAttendance().getSubject().getId())  ||  attendanceSummaryReportForm.getIsSecondLanReq() !=null && attendanceSummaryReportForm.getIsSecondLanReq()   ) 
							{
							if(attendanceSummaryReportForm.getIsPractical() && attendanceStudent.getAttendance().getAttendanceType().getId()==2){
								float practicalClassesHeld =0;
								float practicalClassesAttended =0;
								float practicalClassesWithLeaveAttended =0;
								if (attendanceStudent.getAttendance().getHoursHeld() != 0) 
								{
									practicalClassesHeld = attendanceStudent.getAttendance().getHoursHeld();
								}
								if (attendanceStudent.getAttendance().getHoursHeld() != 0) 
								{
									if(attendanceStudent.getIsPresent())
									{
										practicalClassesAttended = attendanceStudent.getAttendance().getHoursHeld();
									}
									
									else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
									{
										//classesAttended = attendanceStudent.getAttendance().getHoursHeld();
										
										practicalClassesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
										attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
									}
									else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
									{
										practicalClassesAttended = attendanceStudent.getAttendance().getHoursHeld();
										
										//classesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
										//attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
									}
									else
									{
										if(!attendanceStudent.getIsPresent())
										{
											practicalClassesAttended = 0;
										}	
									}
									
								}
								subjectSummaryTO.setPracticalClassesHeld(String.valueOf(Math.round(practicalClassesHeld)));
								subjectSummaryTO.setPracticalClassesAttended(String.valueOf(Math.round(practicalClassesAttended)));
								subjectSummaryTO.setPracticalClassesWithLeaveAttended(String.valueOf(Math.round(practicalClassesWithLeaveAttended)));
//								attendanceSummaryReportForm.setIsActivity("false");
								//subjectSummaryTO.setIsPractical(true);
								//subjectSummaryTO.setIsTheory(false);
								if (practicalClassesAttended != 0 && practicalClassesHeld != 0) {
									float  result = practicalClassesAttended/ practicalClassesHeld * 100;
									//subjectSummaryTO.setPercentage(String.valueOf(Math.round((result))));
									subjectSummaryTO.setPracticalPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
								} else {
									subjectSummaryTO.setPracticalPercentage(String.valueOf(0));
								}
							}else{
								if (attendanceStudent.getAttendance().getHoursHeld() != 0) 
								{
									classesHeld = attendanceStudent.getAttendance().getHoursHeld();
								}
								if (attendanceStudent.getAttendance().getHoursHeld() != 0) 
								{
									if(attendanceStudent.getIsPresent())
									{
										classesAttended = attendanceStudent.getAttendance().getHoursHeld();
									}
									
									else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
									{
										//classesAttended = attendanceStudent.getAttendance().getHoursHeld();
										
										classesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
										attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
									}
									else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
									{
										classesAttended = attendanceStudent.getAttendance().getHoursHeld();
										
										//classesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
										//attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
									}
									else
									{
										if(!attendanceStudent.getIsPresent())
										{
											classesAttended = 0;
										}	
									}
									
								}							
								subjectSummaryTO.setClassesHeld(String.valueOf(Math.round(classesHeld)));
								subjectSummaryTO.setClassesAttended(String.valueOf(Math.round(classesAttended)));
								subjectSummaryTO.setClassesAttendedWithLeave(String.valueOf(Math.round(classesWithLeaveAttended)));
//								attendanceSummaryReportForm.setIsActivity("false");
								//subjectSummaryTO.setIsTheory(true);
								if (classesAttended != 0 && classesHeld != 0) {
									float  result = classesAttended/ classesHeld * 100;
									//subjectSummaryTO.setPercentage(String.valueOf(Math.round((result))));
									subjectSummaryTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
								} else {
									subjectSummaryTO.setPercentage(String.valueOf(0));
								}
							}
								
								
							}
								
								//attendanceSummaryReportTO.getSummaryMap().put(attendanceStudent.getAttendance().getSubject().getId(),subjectSummaryTO);
								//Subject End
//							}//end of subject contains check 
						}
						
						
						else
						{
							if (attendanceStudent.getAttendance() != null )
							{
								ActivitySummaryTO activitySummaryTO = new ActivitySummaryTO();
								if (attendanceStudent.getAttendance().getHoursHeld() != 0) 
								{
									activityHeld = attendanceStudent.getAttendance().getHoursHeld();
									if(attendanceStudent.getIsPresent())
									{
										acitvityAttended = attendanceStudent.getAttendance().getHoursHeld();
									}
									else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
									{
										activityWithLeaveAttended =  attendanceStudent.getAttendance().getHoursHeld();
										attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
									}
									else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
									{
										acitvityAttended =  attendanceStudent.getAttendance().getHoursHeld();
									}
									else
									{
										if(!attendanceStudent.getIsPresent())
										{
											acitvityAttended =  0;
										}
										
									}	
									
								}
								activitySummaryTO.setActivityHeld(String.valueOf(Math.round(activityHeld)));
								activitySummaryTO.setActivityAttended(String.valueOf(Math.round(acitvityAttended)));
								activitySummaryTO.setActivityWithLeaveAttended(String.valueOf(Math.round(activityWithLeaveAttended)));
								//attendanceSummaryReportForm.setIsActivity("true");
								if (acitvityAttended != 0 && activityHeld != 0) 
								{
									float  result = acitvityAttended/ activityHeld * 100;
									activitySummaryTO.setActivityPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
								}
								else
								{
									activitySummaryTO.setActivityPercentage(String.valueOf(0));
								}
								//attendanceSummaryReportTO.getActivityMap().put(attendanceStudent.getAttendance().getActivity().getId(),activitySummaryTO);
								attendanceSummaryReportTO.getActivityMap().put(attendanceStudent.getStudent().getId(),activitySummaryTO);
							}
						}
						Object[] key1 = attendanceSummaryReportTO.getSummaryMap().keySet().toArray();
						Arrays.sort(key1); 
						attendanceSummaryMap.put(attendanceStudent.getStudent().getId(),attendanceSummaryReportTO);
					}
				}
			}
		}
		}
  			//}
  			//}
	}
//		List<AttendanceSummaryReportTO> searchTOList = new ArrayList<AttendanceSummaryReportTO>();
 		Iterator<AttendanceSummaryReportTO> attendanceTOIterator = attendanceSummaryMap.values().iterator();
		attendanceSummaryMap.values().size();
		boolean isRequiredpercentage = false;
		boolean isBelowRequiredAggregate = false;
		boolean isBelowrequiredIndividual = false;
		if (attendanceSummaryReportForm.getRequiredPercentage() != null && !attendanceSummaryReportForm.getRequiredPercentage().isEmpty()) 
		{
			isRequiredpercentage = true;
			if (attendanceSummaryReportForm.getReqPercenAggreg() != null && attendanceSummaryReportForm.getReqPercenAggreg() && attendanceSummaryReportForm.getReqPercenIndivi() != null && attendanceSummaryReportForm.getReqPercenIndivi()) 
			{
				isBelowRequiredAggregate = true;
				isBelowrequiredIndividual = true;
			} 
			else if (attendanceSummaryReportForm.getReqPercenAggreg() != null && attendanceSummaryReportForm.getReqPercenAggreg() && (attendanceSummaryReportForm.getReqPercenIndivi() == null) ||(attendanceSummaryReportForm.getReqPercenIndivi() != null && !attendanceSummaryReportForm.getReqPercenIndivi())) 
			{
				isBelowRequiredAggregate = true;
				isBelowrequiredIndividual = false;
			}
			else if (attendanceSummaryReportForm.getReqPercenIndivi() != null && attendanceSummaryReportForm.getReqPercenIndivi() && (attendanceSummaryReportForm.getReqPercenAggreg() == null || (attendanceSummaryReportForm.getReqPercenAggreg() != null && !attendanceSummaryReportForm.getReqPercenAggreg()))) 
			{
				isBelowRequiredAggregate = false;
				isBelowrequiredIndividual = true;
			} 
		} 
		Map<Integer,List<AttendanceSummaryReportTO>> classesStudentsMap = new HashMap<Integer, List<AttendanceSummaryReportTO>>();
		
		while (attendanceTOIterator.hasNext()) 
		{
			AttendanceSummaryReportTO attendanceSummaryTO2 = (AttendanceSummaryReportTO) attendanceTOIterator.next();
			
			Iterator<Integer> subjectSummaryIterator = attendanceSummaryTO2.getSummaryMap().keySet().iterator();
			
			/*Iterator<ActivitySummaryTO> activity= attendanceSummaryTO2.getActivityMap().values().iterator();
			
			while(activity.hasNext()){
				ActivitySummaryTO activit=(ActivitySummaryTO)activity.next();
				String attend=activit.getActivityAttended();
			}*/
			boolean isValied = calculateAttenAggregatePercentage(attendanceSummaryTO2, attendanceSummaryReportForm, isBelowRequiredAggregate, isBelowrequiredIndividual);
			if (isRequiredpercentage) 
			{
				if (isBelowRequiredAggregate && isBelowrequiredIndividual && !isValied) 
				{
					addAttendanceDataToMap(classesStudentsMap,attendanceSummaryTO2);
				} 
				else if (isBelowRequiredAggregate && !isBelowrequiredIndividual && !isValied) 
				{
					addAttendanceDataToMap(classesStudentsMap,attendanceSummaryTO2);
				} 
				else if (!isBelowRequiredAggregate && isBelowrequiredIndividual && !isValied) 
				{
					addAttendanceDataToMap(classesStudentsMap,attendanceSummaryTO2);
				}
				else if (!isBelowRequiredAggregate && !isBelowrequiredIndividual) 
				{
					addAttendanceDataToMap(classesStudentsMap,attendanceSummaryTO2);
				}
			}
			else 
			{
				addAttendanceDataToMap(classesStudentsMap,attendanceSummaryTO2);
			}
		}
		return classesStudentsMap;
	}

	private static void addAttendanceDataToMap(Map<Integer, List<AttendanceSummaryReportTO>> classesStudentsMap,AttendanceSummaryReportTO attendanceSummaryTO2) 
	{
		List<AttendanceSummaryReportTO> attendanceTOSummaryList;
		if (classesStudentsMap.containsKey(attendanceSummaryTO2.getClassId())) 
		{
			attendanceTOSummaryList = classesStudentsMap.get(attendanceSummaryTO2.getClassId());
			attendanceTOSummaryList.add(attendanceSummaryTO2);
		} 
		else 
		{
			attendanceTOSummaryList = new ArrayList<AttendanceSummaryReportTO>();
			attendanceTOSummaryList.add(attendanceSummaryTO2);
		}
		classesStudentsMap.put(attendanceSummaryTO2.getClassId(), attendanceTOSummaryList);
	}	

	
	private static boolean calculateAttenAggregatePercentage(
			AttendanceSummaryReportTO attendanceSummaryTO2,
			AttendanceSummaryReportForm attendanceSummaryReport,
			boolean isBelowRequiredAggregate, boolean isBelowrequiredIndividual) {
		StringBuilder secondLanguage =new StringBuilder();
		boolean leave=false;
		float activityHeld=0;
		float activityAttended=0;
		float activityWithLeave=0;
		List<String> subjectCodeList = new ArrayList<String>();
		Iterator<ApplicantSubjectGroup> applicantSubjectGroupIterator = attendanceSummaryTO2
				.getStudent().getAdmAppln().getApplicantSubjectGroups()
				.iterator();
		while (applicantSubjectGroupIterator.hasNext()) {
			ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) applicantSubjectGroupIterator
					.next();
			if(applicantSubjectGroup.getSubjectGroup()!=null && applicantSubjectGroup
					.getSubjectGroup().getSubjectGroupSubjectses()!=null){
				
				Iterator<SubjectGroupSubjects> subjectGroupSubject = applicantSubjectGroup
				.getSubjectGroup().getSubjectGroupSubjectses()
				.iterator();
				while (subjectGroupSubject.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subjectGroupSubject
					.next();
					if(subjectGroupSubjects.getSubject() != null && subjectGroupSubjects.getSubject().getCode() != null) {
						subjectCodeList.add(subjectGroupSubjects.getSubject().getCode());
					}
					
					if (subjectGroupSubjects.getSubject() != null && subjectGroupSubjects.getSubject().getIsSecondLanguage()) {
						if (subjectGroupSubjects.getSubject().getCode() != null
								&& !secondLanguage.toString()
								.contains(subjectGroupSubjects
										.getSubject().getCode())) {
							if (secondLanguage.toString().trim().length() > 0) {
								secondLanguage.append(",");
							}
							secondLanguage.append(subjectGroupSubjects.getSubject()
							.getCode());
						}
						
					}
					
				}
			}
		}
	if (attendanceSummaryReport.getIsSecondLanReq() !=null && attendanceSummaryReport.getIsSecondLanReq()) {
			attendanceSummaryTO2.setSecondLanguage(secondLanguage.toString());
		}
		//Iterator<Integer> subjectSummaryIterator =attendanceSummaryTO2.getSummaryMap().keySet().iterator();
	Iterator<Integer> subjectSummaryIterator=attendanceSummaryTO2.getSummaryMap().keySet().iterator();
		boolean isValied = true;		
		float aggregatePercentage = 0;
		if(attendanceSummaryReport
				.getRequiredPercentage()!=null && !attendanceSummaryReport
				.getRequiredPercentage().isEmpty()){
		aggregatePercentage = Float.valueOf(attendanceSummaryReport
				.getRequiredPercentage());
		}
		int noOfSubjects = 0;
		float classHeld = 0;
		float classAttended = 0;
		float classesWithLeaveAttended=0;
		float practicalClassesHeld = 0;
		float practicalClassesAttended = 0;
		float practicalClassesWithLeaveAttended = 0;
		SubjectSummaryTO subjectSummaryTO=null;
		while (subjectSummaryIterator.hasNext()) {
			Integer key = (Integer) subjectSummaryIterator.next();
				 subjectSummaryTO = attendanceSummaryTO2
						.getSummaryMap().get(key);				
				float individualPercentage = 0;
				subjectSummaryTO.setIsPractical(false);
				subjectSummaryTO.setIsTheory(false);
				if (subjectSummaryTO.getPercentage() != null  && !subjectSummaryTO.getPercentage().isEmpty()) {
					individualPercentage = +Float.parseFloat(subjectSummaryTO
							.getPercentage());
					noOfSubjects= noOfSubjects+1;
					if(subjectSummaryTO.getClassesHeld()!=null && subjectSummaryTO.getClassesAttended()!=null && subjectSummaryTO.getClassesAttendedWithLeave()!=null){
					classHeld = classHeld + Float.parseFloat(subjectSummaryTO
							.getClassesHeld());
					classAttended = classAttended + Float.parseFloat(subjectSummaryTO
							.getClassesAttended());
					classesWithLeaveAttended=classesWithLeaveAttended+Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
					subjectSummaryTO.setIsTheory(true);
					}
					if (isBelowrequiredIndividual
							&& individualPercentage < aggregatePercentage) {

						isValied = false;
					}
				} else if(!subjectCodeList.contains(subjectSummaryTO.getSubjectName())) {
					subjectSummaryTO.setPercentage("-");
					subjectSummaryTO.setIsTheory(false);
				}
				if(subjectSummaryTO.getPracticalClassesHeld()!=null && subjectSummaryTO.getPracticalClassesAttended()!=null && subjectSummaryTO.getPracticalClassesWithLeaveAttended()!=null){
					practicalClassesHeld = practicalClassesHeld + Float.parseFloat(subjectSummaryTO
							.getPracticalClassesHeld());
					practicalClassesAttended = practicalClassesAttended + Float.parseFloat(subjectSummaryTO.getPracticalClassesAttended());
					practicalClassesWithLeaveAttended = practicalClassesWithLeaveAttended + Float.parseFloat(subjectSummaryTO.getPracticalClassesWithLeaveAttended());
					subjectSummaryTO.setIsPractical(true);
				}else if(!subjectCodeList.contains(subjectSummaryTO.getSubjectName())){
					subjectSummaryTO.setPracticalPercentage("-");
					subjectSummaryTO.setIsPractical(false);
				}
/*				aggrigatepercentagecalculated = aggrigatepercentagecalculated
						+ individualPercentage;
*///			}
		}
		if(!attendanceSummaryTO2.getActivityMap().isEmpty()&& attendanceSummaryTO2.getActivityMap()!=null){
		Iterator<ActivitySummaryTO> activity= attendanceSummaryTO2.getActivityMap().values().iterator();
		while(activity.hasNext()){
			ActivitySummaryTO activityTo=(ActivitySummaryTO)activity.next();
			if(activityTo.getActivityHeld() != null && !activityTo.getActivityHeld().isEmpty()){
				activityHeld=activityHeld+Float.parseFloat(activityTo.getActivityHeld());
			}
			if(activityTo.getActivityAttended() != null && !activityTo.getActivityAttended().isEmpty()){
				activityAttended=activityAttended+Float.parseFloat(activityTo.getActivityAttended());
			}
			if(activityTo.getActivityWithLeaveAttended() != null && !activityTo.getActivityWithLeaveAttended().isEmpty()){
				activityWithLeave=activityWithLeave+Float.parseFloat(activityTo.getActivityWithLeaveAttended());
			}
			
		}
		attendanceSummaryReport.setIsActivity("true");
		}
		classHeld=classHeld+activityHeld+practicalClassesHeld;
		classAttended=classAttended+activityAttended+practicalClassesAttended;
		Float aggrigatepercentagecalculated = (classAttended)/(classHeld) * 100;
		
		float percentage = 0;
		/*if(aggrigatepercentagecalculated>85){
			aggrigatepercentagecalculated=85.0f;
			
		}*/
		/*if (noOfSubjects >0) {
			percentage = (aggrigatepercentagecalculated / noOfSubjects);
			if (isBelowRequiredAggregate && (percentage < aggregatePercentage)) {
				isValied = false;
			}else if(isBelowRequiredAggregate && percentage > aggregatePercentage ) {
				isValied = true;
			}
		}*/
		
		percentage =CommonUtil.roundToDecimal(aggrigatepercentagecalculated, 2);
		/*if(percentage<85 && percentage>=Math.round(75)&&attendanceSummaryTO2.isLeaveStatus()){
		Iterator<Integer> subjectSummaryIterator1=attendanceSummaryTO2.getSummaryMap().keySet().iterator();
		while(subjectSummaryIterator1.hasNext()){
			float classesAttended1=0;
			float classesHeld1=0;
			float classesWithLeave=0;
			float percentage1=0;
			Integer key = (Integer) subjectSummaryIterator1.next();
			subjectSummaryTO = attendanceSummaryTO2
			.getSummaryMap().get(key);
			if(subjectSummaryTO.getClassesHeld()!=null && subjectSummaryTO.getClassesAttended()!=null){
			classesHeld1=Float.parseFloat(subjectSummaryTO.getClassesHeld());
			classesAttended1=Float.parseFloat(subjectSummaryTO.getClassesAttended());
			percentage1=classesAttended1 / classesHeld1 * 100;
			
			if(attendanceSummaryReport.getRequiredPercentage()!=null && !attendanceSummaryReport.getRequiredPercentage().isEmpty() && attendanceSummaryReport.getLeaveType()!=null && attendanceSummaryReport.getLeaveType()&&attendanceSummaryTO2.isLeaveStatus()&&percentage1<Float.parseFloat(attendanceSummaryReport.getRequiredPercentage()) && percentage1>=Math.round(75)&&(percentage>=Math.round(75)&& percentage<85)){
				
					classesAttended1=classesAttended1+Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
					percentage1=classesAttended1 / classesHeld1 * 100;
					subjectSummaryTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(percentage1,2)));
					//subjectSummaryTO.setClassesAttended(String.valueOf(Math.round(classesAttended1)));
					
					//subjectSummaryTO.setClassesAttended(String.valueOf(classesAttended1));
				}
				else if(attendanceSummaryReport.getLeaveType()!=null && attendanceSummaryReport.getLeaveType() &&percentage1>=75 &&percentage1<Float.parseFloat(attendanceSummaryReport.getRequiredPercentage())&& attendanceSummaryTO2.isLeaveStatus()&&(percentage>=Math.round(75)&& percentage<85)){
					
						classesAttended1=classesAttended1+Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
						percentage1=classesAttended1 / classesHeld1 * 100;
					subjectSummaryTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(percentage1,2)));
					subjectSummaryTO.setClassesAttended(String.valueOf(Math.round(classesAttended1)));
						//subjectSummaryTO.setClassesAttended(String.valueOf(classesAttended1));
				}
			
			}
		}}*/
		if(Math.round(percentage)>=75&&percentage<85 && attendanceSummaryTO2.isLeaveStatus())
		{
			classAttended=classAttended+classesWithLeaveAttended+activityWithLeave+practicalClassesWithLeaveAttended;
			percentage=CommonUtil.roundToDecimal((classAttended/classHeld * 100),2);
			leave=true;
			attendanceSummaryReport.setLeave(leave);
		}else{
			attendanceSummaryReport.setLeave(leave);
		}
		if (isBelowRequiredAggregate && (percentage < aggregatePercentage)) {
			isValied = false;
		}else if(isBelowRequiredAggregate && percentage > aggregatePercentage ) {
			isValied = true;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		//subjectSummaryTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(percentage, 2)));
		
		//attendanceSummaryTO2.setAggrigatePercentage(String.valueOf(Math.round(aggrigatepercentagecalculated)));
		
			if(attendanceSummaryReport.getRequiredPercentage()!=null && !attendanceSummaryReport.getRequiredPercentage().isEmpty()
					&& attendanceSummaryReport.getLeaveType()!=null && attendanceSummaryReport.getLeaveType() 
					&& attendanceSummaryReport.getConsiderLeave()!=null && attendanceSummaryReport.getConsiderLeave() && attendanceSummaryTO2.isLeaveStatus() && attendanceSummaryReport.isLeave()){
					if(Math.round(percentage)>aggregatePercentage){
						attendanceSummaryTO2.setAggrigatePercentage(df.format(aggregatePercentage)+"*");
					}else{
						attendanceSummaryTO2.setAggrigatePercentage(df.format(percentage));
					}
					
			}
			else if(attendanceSummaryReport.getLeaveType()!=null && attendanceSummaryReport.getLeaveType() && attendanceSummaryReport.getRequiredPercentage()!=null && !attendanceSummaryReport.getRequiredPercentage().isEmpty() && attendanceSummaryTO2.isLeaveStatus()&&attendanceSummaryReport.isLeave()){
				if(percentage>aggregatePercentage){
					attendanceSummaryTO2.setAggrigatePercentage(df.format(aggregatePercentage)+"*");
				}
				else{
					attendanceSummaryTO2.setAggrigatePercentage(df.format(percentage));
				}
			}else if(attendanceSummaryReport.getLeaveType()!=null && attendanceSummaryReport.getLeaveType() &&attendanceSummaryTO2.isLeaveStatus()&&attendanceSummaryTO2.isLeaveStatus()){
				attendanceSummaryTO2.setAggrigatePercentage(df.format(percentage));
			}else{
				attendanceSummaryTO2.setAggrigatePercentage(df.format(percentage));
			}
		
		attendanceSummaryTO2.getSubjectSummaryList().addAll(attendanceSummaryTO2.getSummaryMap().values());
		attendanceSummaryTO2.getActivitySummaryTOlist().addAll(attendanceSummaryTO2.getActivityMap().values());

		Iterator<Subject> subjectIterator = attendanceSummaryReport.getSubjectMap().values().iterator();
		while (subjectIterator.hasNext()) {
			Subject subject = (Subject) subjectIterator.next();
			StringBuffer subjectCodeName = new StringBuffer();
			if(subject.getCode() != null) {
				subjectCodeName.append(subject.getCode());
			}
			subjectCodeName.append("--");
			if(subject.getName() != null) {
				subjectCodeName.append(subject.getName());
			}
			attendanceSummaryTO2.getSubjectCodeNameList().add(subjectCodeName.toString().trim());
			
		}
		
		return isValied;
	}

	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the classes held for a particular subject
	 */
	private static String getClassesHeld(AttendanceStudent student,Attendance attenStudent,AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getClassesHeld..");
		String searchCre = "";
		if(attenStudent.getSubject()!=null && attenStudent.getSubject().getId()!=0 && attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null){
			searchCre = "select sum(attendanceStudent.attendance.hoursHeld) from AttendanceStudent attendanceStudent"
				+" where attendance.isCanceled = 0 and attendance.isMonthlyAttendance = 0 and attendanceStudent.attendance.subject.id = "
				+ attenStudent.getSubject().getId()
				+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
				+" group by attendanceStudent.student.id ";
		}
		log.info("exit getClassesHeld..");
		return searchCre;
	}

	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the classes attended for particular subject by particular student
	 * 	 */
	private static String getClassesAttended(AttendanceStudent student, Attendance attenStudent,AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getClassesAttended..");
		String searchCre = "";
		if(attenStudent.getSubject()!=null && attenStudent.getSubject().getId()!=0 && attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null && student.getStudent()!=null && student.getStudent().getRegisterNo()!=null){
			searchCre = "select sum(attendanceStudent.attendance.hoursHeld) from AttendanceStudent attendanceStudent" 
				+" where attendance.isCanceled = 0 and attendance.isMonthlyAttendance = 0 and attendanceStudent.isPresent = 1 and attendanceStudent.attendance.subject.id = "
				+ attenStudent.getSubject().getId()+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
				+" group by attendanceStudent.student.id";
		}
		log.info("exit getClassesAttended..");
		return searchCre;
	}

	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the classes attended for particular subject by particular student
	 * 	 */
	private static String getClassesAttendedWithLeave(AttendanceStudent student, Attendance attenStudent,AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getClassesAttendedWithLeave..");
		String searchCre = "";
		if(attenStudent.getSubject()!=null && attenStudent.getSubject().getId()!=0 && attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null && student.getStudent()!=null && student.getStudent().getRegisterNo()!=null){
			searchCre = "select sum(attendanceStudent.attendance.hoursHeld) from AttendanceStudent attendanceStudent" 
				+" where attendance.isCanceled = 0 and attendance.isMonthlyAttendance = 0 and attendanceStudent.isPresent = 0 and attendanceStudent.isOnLeave = 1 and attendanceStudent.attendance.subject.id = "
				+ attenStudent.getSubject().getId()+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
				+" group by attendanceStudent.student.id";
		}
		log.info("exit getClassesAttendedWithLeave..");
		return searchCre;
	}

	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the Activity Held for particular student
	 */
	private static String getActivityHeld(AttendanceStudent student,Attendance attenStudent,AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getActivityHeld..");
		String searchCre = "";
		if(attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null){
			searchCre = "from AttendanceStudent attendanceStudent"
				+" where attendanceStudent.attendance.isCanceled = 0 and attendanceStudent.attendance.isMonthlyAttendance = 0 and attendanceStudent.attendance.isActivityAttendance = 1  and attendanceStudent.attendance.activity.id = "			
				+ attenStudent.getActivity().getId()+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"			
				+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getActivityHeld..");
		return searchCre;
	}


	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the Selected Activity Held for particular student
	 */
	private static String getSelectedActivityHeld(AttendanceStudent student,AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getSelectedActivityHeld..");
		String searchCre = "";
		if(attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null && attendanceSummaryReportForm.getActivityId()!=null){
			searchCre = "from AttendanceStudent attendanceStudent"
				+" where attendanceStudent.attendance.isCanceled = 0 and attendanceStudent.attendance.isMonthlyAttendance = 0  and attendanceStudent.attendance.isActivityAttendance = 1 "			
				+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
				+" and attendanceStudent.attendance.activity.id = '"+Integer.parseInt(attendanceSummaryReportForm.getActivityId())+"'"
				+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getSelectedActivityHeld..");
		return searchCre;
	}

	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the Activity Attended for particular student
	 */
	private static String getActivityAttended(AttendanceStudent student, Attendance attenStudent,AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getActivityAttended..");
		String searchCre = "";
		if(attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null && student.getStudent()!=null && student.getStudent().getRegisterNo()!=null){
			searchCre = "from AttendanceStudent attendanceStudent" 
				+" where attendanceStudent.attendance.isCanceled = 0 and attendanceStudent.attendance.isMonthlyAttendance = 0 and attendanceStudent.isPresent = 1 and attendanceStudent.attendance.activity.id = "
				+ attenStudent.getActivity().getId()+" and attendanceStudent.attendance.isActivityAttendance = 1 "
				+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"					
				+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getActivityAttended..");
		return searchCre;
	}
	/**
	 * @param attenStudent
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will give the Selected Activity Attended for particular student
	 */
	private static String getSelectedActivityAttended(AttendanceStudent student, AttendanceSummaryReportForm attendanceSummaryReportForm){
		log.info("entered getSelectedActivityAttended..");
		String searchCre = "";
		if(attendanceSummaryReportForm.getStartDate()!=null && attendanceSummaryReportForm.getEndDate()!=null && student.getStudent()!=null && student.getStudent().getRegisterNo()!=null && attendanceSummaryReportForm.getActivityId()!=null){
			searchCre = "from AttendanceStudent attendanceStudent" 
				+" where attendanceStudent.isPresent = 1 "
				+" and attendanceStudent.attendance.isCanceled = 0 attendanceStudent.attendance.isMonthlyAttendance = 0 and attendanceStudent.attendance.isActivityAttendance = 1 "
				+" and attendanceStudent.attendance.attendanceDate >= '"
				+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getStartDate())+"'"
				+" and attendanceStudent.attendance.attendanceDate <='"+CommonUtil.ConvertStringToSQLDate(attendanceSummaryReportForm.getEndDate())+"'"
				+" and attendanceStudent.student.registerNo = '"+student.getStudent().getRegisterNo()+"'"
				+" and attendanceStudent.attendance.activity.id = '"+Integer.parseInt(attendanceSummaryReportForm.getActivityId())+"'"
				+" group by attendanceStudent.attendance.id";
		}
		log.info("exit getSelectedActivityAttended..");
		return searchCre;
	}
/**
 * @param attendanceSummaryReportForm
 * @return
 */
public static List<Integer> ConvertStrToList(AttendanceSummaryReportForm attendanceSummaryReportForm){
	String[] classes=attendanceSummaryReportForm.getClassesName();
	List<Integer> classList=new ArrayList<Integer>();
	for(int i=0;i<classes.length;i++){
		classList.add(Integer.parseInt(classes[i]));
	}
	return classList;
}
@SuppressWarnings("unchecked")
private static String commonSearchForTeacherView(AttendanceSummaryReportForm attendanceSummaryReportForm,List attTypes,String sDate) {
	log.info("entered commonSearch..");
	String searchCriteria = "";

	if (attendanceSummaryReportForm.getClassesName().length > 0) {
		String [] classArray = attendanceSummaryReportForm.getClassesName();
		StringBuilder className = new StringBuilder();
		for(int i=0;i<classArray.length;i++){
			className.append(classArray[i]);
			if(i<(classArray.length-1)){
				className.append(",");
			}
		}
		String classType = " attendanceStudent.student.classSchemewise.classes.id in ("
			+ className +")";
		searchCriteria = searchCriteria + classType;
		searchCriteria = searchCriteria + " and ac.classSchemewise.classes.id in ("  +  className + ")";
	}

	if (attendanceSummaryReportForm.getAcademicYear().trim().length() > 0) {
		String appliedYear = " and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
			+ attendanceSummaryReportForm.getAcademicYear();
		String admApplnStr = " and attendanceStudent.student.admAppln.isCancelled = 0 and attendanceStudent.student.isActive = 1";
		searchCriteria = searchCriteria + appliedYear + admApplnStr;
	}

	if (attTypes!=null && !attTypes.isEmpty()) {
		Iterator itr= attTypes.iterator();
		String attType ="";
		int i=0;
		while(itr.hasNext()){
			attType = attType+(itr.next().toString());
			if(i<(attTypes.size()-1)){
 				attType = attType+",";
			}
			i++;
		}
		
	/*	if (attendanceSummaryReportForm.getAttendanceType().length > 0) {
			String [] tempArray = attendanceSummaryReportForm.getAttendanceType();
			String attType ="";
			for(int i=0;i<tempArray.length;i++){
				attType = attType+tempArray[i];
				if(i<(tempArray.length-1)){
					attType = attType+",";
				}
			}*/
            attendanceSummaryReportForm.setAttType(attType);
            if(attType.contains("2"))
            	attendanceSummaryReportForm.setIsPractical(true);
            else
            	attendanceSummaryReportForm.setIsPractical(false);
            if(attType.contains("1"))
            	attendanceSummaryReportForm.setIsTheory(true);
            else
            	attendanceSummaryReportForm.setIsTheory(false);
            if(attType.contains("1,2"))
            	attendanceSummaryReportForm.setIsTheoryNPractical(true);
            else
            	attendanceSummaryReportForm.setIsTheoryNPractical(false);
		
		
		
		
		

		String attendanceType = " and attendanceStudent.attendance.attendanceType in ("
			+ attType +")";
		String canclledAttn = " and attendanceStudent.attendance.isCanceled = 0 ";
		String monthlyAttn = " and attendanceStudent.attendance.isMonthlyAttendance = 0 ";
		searchCriteria = searchCriteria + attendanceType + canclledAttn + monthlyAttn;
		
		searchCriteria=searchCriteria+" and ac.classSchemewise.id=attendanceStudent.student.classSchemewise.id ";
	}
	
	if (sDate!=null && sDate.length()>0) {
		String startDate = " and attendanceStudent.attendance.attendanceDate >= '"
			+ CommonUtil.ConvertStringToDateNew(sDate)+"'" ;
		searchCriteria = searchCriteria + startDate;
		//attendanceSummaryReportForm.setStartDate(CommonUtil.ConvertStringToDate1(sDate));
		attendanceSummaryReportForm.setStartDate(CommonUtil.ConvertStringToDateFormat(sDate,"yyyy-mm-dd", "dd/mm/yyyy"));
	}
	java.util.Date eDate=new java.util.Date();
   Date endDate1=new Date(eDate.getTime());
	
		String endDate = " and attendanceStudent.attendance.attendanceDate <= '"
			+ endDate1 +"'";
		searchCriteria = searchCriteria + endDate;
		attendanceSummaryReportForm.setEndDate(CommonUtil.ConvertStringToDateFormat(endDate1.toString(),
				"yyyy-mm-dd", "dd/mm/yyyy"));
		//attendanceSummaryReportForm.setEndDate(CommonUtil.ConvertStringToDate1(endDate1.toString()));
	log.info("exit commonSearch..");
	return searchCriteria;
}
public static String getSelectionSearchCriteriaForTeacherView(
		AttendanceSummaryReportForm studentSearchForm,List attendanceType,String startDate) {
	log.info("entered getSelectionSearchCriteria..");
	String statusCriteria = commonSearchForTeacherView(studentSearchForm,attendanceType,startDate);

	String searchCriteria= "";

	//		String ednJoin = "";
	searchCriteria =  "select attendanceStudent from AttendanceStudent attendanceStudent" +
			" inner join attendanceStudent.attendance att " +
			" inner join att.attendanceClasses ac"
		+ " where " + statusCriteria + " order by attendanceStudent.student.classSchemewise.classes.id";					
	log.info("exit getSelectionSearchCriteria..");
	return searchCriteria;

	
}
public static SubjectSummaryTO setAttendance(SubjectSummaryTO subjectSummaryTO,AttendanceStudent attendanceStudent,AttendanceSummaryReportForm attendanceSummaryReportForm,AttendanceSummaryReportTO attendanceSummaryReportTO){
	float classesHeld = 0;
	float classesAttended = 0;
	float classesWithLeaveAttended = 0;
	float practicalClassesHeld =0;
	float practicalClassesAttended =0;
	float practicalClassesWithLeaveAttended =0;
	if(attendanceSummaryReportForm.getIsPractical() && attendanceStudent.getAttendance().getAttendanceType().getId()==2){
		if((subjectSummaryTO.getPracticalClassesHeld() == null) || (subjectSummaryTO.getPracticalClassesHeld() != null && subjectSummaryTO.getPracticalClassesHeld().isEmpty())) 
		   {
			practicalClassesHeld = attendanceStudent.getAttendance().getHoursHeld();
			  subjectSummaryTO.setPracticalClassesHeld(String.valueOf(Math.round(practicalClassesHeld)));
		   }
		   else
		   {
			   practicalClassesHeld = Float.parseFloat(subjectSummaryTO.getPracticalClassesHeld())+ attendanceStudent.getAttendance().getHoursHeld();
			  subjectSummaryTO.setPracticalClassesHeld(String.valueOf(Math.round(practicalClassesHeld)));
		   }
		if(subjectSummaryTO.getPracticalClassesAttended()== null) 
		   {
			  if(attendanceStudent.getIsPresent())
			  {								
				  practicalClassesAttended = attendanceStudent.getAttendance().getHoursHeld();
			  }
		      else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
		      {
				//classesAttended = attendanceStudent.getAttendance().getHoursHeld();
		    	  practicalClassesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
			    attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
		      }
			  else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
			  {
				  practicalClassesAttended = attendanceStudent.getAttendance().getHoursHeld();
				  //attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
			  }
			  else
			  {
				  if(!attendanceStudent.getIsPresent())
				  {
					  practicalClassesAttended = 0;
				  }
			  }
		  }
		else
		  {
			if(attendanceStudent.getIsPresent())
			{								
				practicalClassesAttended = Float.parseFloat(subjectSummaryTO.getPracticalClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
				practicalClassesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getPracticalClassesWithLeaveAttended());
			}
			else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
			{
				//classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
				practicalClassesAttended = Float.parseFloat(subjectSummaryTO.getPracticalClassesAttended());
				practicalClassesWithLeaveAttended = Float.parseFloat(subjectSummaryTO.getPracticalClassesWithLeaveAttended())+ attendanceStudent.getAttendance().getHoursHeld();
				attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
			}
			else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
			{
				practicalClassesAttended = Float.parseFloat(subjectSummaryTO.getPracticalClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
				practicalClassesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getPracticalClassesWithLeaveAttended());
				//classesWithLeaveAttended = Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave())+ attendanceStudent.getAttendance().getHoursHeld();
				//attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
			}
			else
			{
				if(!attendanceStudent.getIsPresent())
				{
					practicalClassesAttended = Float.parseFloat(subjectSummaryTO.getPracticalClassesAttended())+ 0;
					practicalClassesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getPracticalClassesWithLeaveAttended());
				}
			}
			
		  }
		   subjectSummaryTO.setPracticalClassesAttended(String.valueOf(Math.round(practicalClassesAttended)));
			  subjectSummaryTO.setPracticalClassesWithLeaveAttended(String.valueOf(practicalClassesWithLeaveAttended));
			  //subjectSummaryTO.setIsPractical(true);
			  //subjectSummaryTO.setIsTheory(false);
			  if (practicalClassesAttended != 0 && practicalClassesHeld != 0) 
			  {
				 float  result = practicalClassesAttended/ practicalClassesHeld * 100;
				 subjectSummaryTO.setPracticalPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
			  } 
			  else 
			  {
				 subjectSummaryTO.setPracticalPercentage(String.valueOf(0));
			  }
	}
	else
	{
	   if((subjectSummaryTO.getClassesHeld() == null) || (subjectSummaryTO.getClassesHeld() != null && subjectSummaryTO.getClassesHeld().isEmpty())) 
	   {
		  classesHeld = attendanceStudent.getAttendance().getHoursHeld();
		  subjectSummaryTO.setClassesHeld(String.valueOf(Math.round(classesHeld)));
	   }
	   else
	   {
		  classesHeld = Float.parseFloat(subjectSummaryTO.getClassesHeld())+ attendanceStudent.getAttendance().getHoursHeld();
		  subjectSummaryTO.setClassesHeld(String.valueOf(Math.round(classesHeld)));
	   }	
	
	   if(subjectSummaryTO.getClassesAttended()== null) 
	   {
		  if(attendanceStudent.getIsPresent())
		  {								
			  classesAttended = attendanceStudent.getAttendance().getHoursHeld();
		  }
	      else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
	      {
			//classesAttended = attendanceStudent.getAttendance().getHoursHeld();
		    classesWithLeaveAttended=attendanceStudent.getAttendance().getHoursHeld();
		    attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
	      }
		  else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
		  {
			  classesAttended = attendanceStudent.getAttendance().getHoursHeld();
			  //attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
		  }
		  else
		  {
			  if(!attendanceStudent.getIsPresent())
			  {
				 classesAttended = 0;
			  }
		  }
	  }
	  else
	  {
		if(attendanceStudent.getIsPresent())
		{								
			classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
			classesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
		}
		else if(attendanceStudent.getIsOnLeave() && attendanceSummaryReportForm.getLeaveType()!=null && attendanceSummaryReportForm.getLeaveType())
		{
			//classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
			classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended());
			classesWithLeaveAttended = Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave())+ attendanceStudent.getAttendance().getHoursHeld();
			attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
		}
		else if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave() && attendanceSummaryReportForm.getCoCurricularLeave()!=null && attendanceSummaryReportForm.getCoCurricularLeave())
		{
			classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ attendanceStudent.getAttendance().getHoursHeld();
			classesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
			//classesWithLeaveAttended = Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave())+ attendanceStudent.getAttendance().getHoursHeld();
			//attendanceSummaryReportTO.setLeaveStatus(Boolean.TRUE);
		}
		else
		{
			if(!attendanceStudent.getIsPresent())
			{
				classesAttended = Float.parseFloat(subjectSummaryTO.getClassesAttended())+ 0;
				classesWithLeaveAttended=Float.parseFloat(subjectSummaryTO.getClassesAttendedWithLeave());
			}
		}
		
	  }
	   subjectSummaryTO.setClassesAttended(String.valueOf(Math.round(classesAttended)));
		  subjectSummaryTO.setClassesAttendedWithLeave(String.valueOf(classesWithLeaveAttended));
          //subjectSummaryTO.setIsTheory(true);
		  if (classesAttended != 0 && classesHeld != 0) 
		  {
			 float  result = classesAttended/ classesHeld * 100;
			 subjectSummaryTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(result, 2)));
		  } 
		  else 
		  {
			 subjectSummaryTO.setPercentage(String.valueOf(0));
		  }
	}
	  
	  return subjectSummaryTO;
   }
}


