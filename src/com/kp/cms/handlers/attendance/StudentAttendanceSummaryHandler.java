package com.kp.cms.handlers.attendance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.handlers.admission.CurriculumSchemeHandler;
import com.kp.cms.handlers.admission.StudentEditHandler;
import com.kp.cms.helpers.attendance.StudentAttendanceSummaryHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.admission.DisciplinaryDetailsImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.ExamCodeComparator;
import com.kp.cms.utilities.SubjectGroupDetailsComparator;

public class StudentAttendanceSummaryHandler {
	
	private static volatile StudentAttendanceSummaryHandler studentAttendanceSummaryHandler = null;
	private static final Log log = LogFactory.getLog(StudentAttendanceSummaryHandler.class);
	private StudentAttendanceSummaryHandler() {
		
	}
	
	public static StudentAttendanceSummaryHandler getInstance() {
		if(studentAttendanceSummaryHandler == null) {
		studentAttendanceSummaryHandler = new StudentAttendanceSummaryHandler();	
		}
		return studentAttendanceSummaryHandler;
	}
	
	/**
	 * Get the StudentWiseSubjectSummaryTO List
	 * @param studentLogin -
	 *                         Represents the studentLogin object.
	 * @return
	 * @throws ApplicationException
	 */
	public List<StudentWiseSubjectSummaryTO> getPreviousSubjectWiseAttendanceList(
			int studentId,int Semester) throws ApplicationException, Exception {
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		//Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		HashMap<Integer, String> subjectCodeMap = new HashMap<Integer, String>();
		
		StudentEditHandler historyHandler = StudentEditHandler.getInstance();
		
		List<Subject> listSubjGrp1 = historyHandler.viewHistory_SubjectGroupDetails1(studentId, Semester);
		
		if(listSubjGrp1!=null && !listSubjGrp1.isEmpty()){
			Iterator<Subject> subIterator = listSubjGrp1.iterator();
			while (subIterator.hasNext()) {
				Subject subject = (Subject) subIterator.next();
				subjectMap.put(subject.getId(), subject.getName());
				subjectCodeMap.put(subject.getId(), subject.getCode());
			}
		}
		//subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupId(String.valueOf(year));
		//subjectCodeMap = studentWiseAttendanceSummaryTransaction.getSubjectCodesBySubjectGroupId(String.valueOf(year));
	
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		int classId = studentWiseAttendanceSummaryTransaction.getClassIdPrevious(studentId, Semester);
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForConducted(studentId,classId));
		
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForPresent(studentId,classId));
		
		List<Object[]> classesOnLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnLeave(
						studentId,classId));
			
		
		List<Object[]> classesOnCocurricularLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnCocurricularLeave(
						studentId,classId));
		log.info("classesOnCocurricularLeaveList.size(): " + classesOnCocurricularLeaveList.size());
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnLeaveListToMap(classesOnLeaveList);
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesConductedList, false);
		Map<Integer, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesPresentList, true);
		
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnCocurricularLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnCoCurricularLeaveListToMap(classesOnCocurricularLeaveList);		
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromMapToList(classesConductedMap,
						classesPresentMap, classesOnLeaveMap, classesOnCocurricularLeaveMap);
		
		List<MarksEntryDetails> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetailsView(studentId, classId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMapView(examMarksEntryDetailsBOList, new ArrayList<Integer>(), false);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			if(subjectCodeMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				String subCodeName = "(" + subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) + ")   " +  studentWiseSubjectSummaryTO.getSubjectName().trim();
				subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				studentWiseSubjectSummaryTO.setSubjectName(subCodeName);
			}
				
			if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		
		return studentWiseSubjectSummaryWithMarkTOList;

	}
	
	
	public List<StudentWiseSubjectSummaryTO> getSubjectWiseAttendanceList(
			int studentId) throws ApplicationException, Exception {
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		HashMap<Integer, String> subjectCodeMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				//int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();
					if(applicantSubjectGroup.getSubjectGroup()!=null){
						subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId());
						count++;
					}
				}
				StringBuilder intType =new StringBuilder();
				if (subjGroupString!= null && subjGroupString.length > 0) {
					String [] tempArray = subjGroupString;
					for(int i=0;i<tempArray.length;i++){
						intType.append(tempArray[i]);
						 if(i<(tempArray.length-1)){
							 intType.append(",");
						 }
					}
				}
				if(!intType.toString().isEmpty()){
					subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupId(intType.toString());
					subjectCodeMap = studentWiseAttendanceSummaryTransaction.getSubjectCodesBySubjectGroupId(intType.toString());
				}
			}
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForConducted(studentId,classId));
		
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForPresent(studentId,classId));
		
		List<Object[]> classesOnLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnLeave(
						studentId,classId));
			
		
		List<Object[]> classesOnCocurricularLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnCocurricularLeave(
						studentId,classId));
		log.info("classesOnCocurricularLeaveList.size(): " + classesOnCocurricularLeaveList.size());
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnLeaveListToMap(classesOnLeaveList);
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesConductedList, false);
		Map<Integer, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesPresentList, true);
		
		//Satish Patruni
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnCocurricularLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnCoCurricularLeaveListToMap(classesOnCocurricularLeaveList);		
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromMapToList(classesConductedMap,
						classesPresentMap, classesOnLeaveMap, classesOnCocurricularLeaveMap);
		
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, new ArrayList<Integer>(), false);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			if(subjectCodeMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				String subCodeName = "(" + subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) + ")   " +  studentWiseSubjectSummaryTO.getSubjectName().trim();
				subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				studentWiseSubjectSummaryTO.setSubjectName(subCodeName);
			}
				
			if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		
		return studentWiseSubjectSummaryWithMarkTOList;

	}

	/**
	 * 
	 * @param valueOf
	 * @returns activity attendance of the student
	 */

	public List<StudentWiseSubjectSummaryTO> getActivityWiseAttendanceList(
			int studentId) throws Exception{
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year!=0){
			currentYear=year;
		}
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getActivityInformationSummaryQueryForConducted(studentId,currentYear));
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getActivityInformationSummaryQueryForPresent(studentId,currentYear));
		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
		.getInstance().convertActivityAttendanceSummaryListToMap(classesConductedList);
	
	Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
	.getInstance().convertActivityAttendanceSummaryListToMap(classesPresentList);
	
	return StudentAttendanceSummaryHelper.getInstance().convertFromActivityAttendanceSummaryMapToList(classesConductedMap,
			classesPresentMap);
	}

	/**
	 * @param studentId
	 * @param attendanceSummaryForm
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> getStudentSubjectWiseAttendanceList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
		/*float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		int activityCount=0;*/
		String mode="Current";
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		attendanceSummaryForm.setMarkPresent(false);
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getStudentAbsenceInformationSummaryQueryForConducted(studentId, classId));
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));
		
		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
		
		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromAttendanceSummaryMapToList(classesConductedMap,classesPresentMap,studentId,attendanceSummaryForm,mode);
		// added for subject order
		Student stu=studentWiseAttendanceSummaryTransaction.getSudentSemAcademicYear(studentId);
		int semNo=0;
		int semAcademicYear=0;
		if(stu!=null){
			if(stu.getClassSchemewise()!=null && stu.getClassSchemewise().getClasses()!=null)
		semNo=stu.getClassSchemewise().getClasses().getTermNumber();
			if(stu.getClassSchemewise()!=null && stu.getClassSchemewise().getCurriculumSchemeDuration()!=null)
				semAcademicYear=stu.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();	
		}
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId),semNo,semAcademicYear);
		
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
		
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		boolean isSortRequired = true;
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}*/
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
			}
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
				isSortRequired = false;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
				attendanceSummaryForm.setMarkPresent(true);
				
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		if(isSortRequired){
			Collections.sort(studentWiseSubjectSummaryWithMarkTOList);
		}
		
		return studentWiseSubjectSummaryWithMarkTOList;
		
		//return studentWiseSubjectSummaryTOList;
	}
	/**
	 * @param studentId
	 * @param attendanceSummaryForm
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
//	priyatham function start
	public List<StudentWiseSubjectSummaryTO> getStudentWiseSubjectsAttendanceList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList=getStudentSubjectWiseAttendanceList(studentId, attendanceSummaryForm, courseId);
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummayToList1=getStudentSubjectWiseAttendanceSubjectList(studentId, attendanceSummaryForm, courseId,attendanceSummaryForm.isMarkPresent());
		
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		if(studentWiseSubjectSummaryTOList!=null){
//			Iterator<StudentWiseSubjectSummaryTO> iterator1=studentWiseSubjectSummaryTOList.iterator();
//			while(iterator1.hasNext()){
//				iterator1.next();
//			}
//			if(studentWiseSubjectSummayToList1!=null){
//				Iterator<StudentWiseSubjectSummaryTO> iterator2=studentWiseSubjectSummayToList1.iterator();
//				while(iterator2.hasNext()){
//					studentWiseSubjectSummaryTOList.add(iterator2.next());
//				}
//			}
//		}
		if(studentWiseSubjectSummaryTOList==null){
			studentWiseSubjectSummaryTOList=new ArrayList<StudentWiseSubjectSummaryTO>();
		}
		if(studentWiseSubjectSummayToList1!=null){
			studentWiseSubjectSummaryTOList.addAll(studentWiseSubjectSummayToList1);
		}
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
			percentage=CommonUtil.roundToDecimal(((classesPresent/classesConducted)*100),2);
			DecimalFormat df = new DecimalFormat("#.##");
			attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
			attendanceSummaryForm.setTotalConducted(""+classesConducted);
			attendanceSummaryForm.setTotalPresent(""+classesPresent);
			attendanceSummaryForm.setTotalAbscent(""+classesAbsent);
			}
		
		float totalWorkingDays = 0;
		float toatalPresentDays = 0;
		float totalAbsentDays = 0;
		float totalPercentages = 0f;
		IStudentWiseAttendanceSummaryTransaction txn = new StudentWiseAttendanceSummaryTransactionImpl();
		int classId = txn.getClassId(studentId);
		CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
		int acadamicYear = currBo.getAcademicYear();
		List<Object[]> totalAttendence = txn.getAttendenceRecord(studentId,courseId,classId,acadamicYear);
		if(totalAttendence != null){
		totalWorkingDays = StudentAttendanceSummaryHelper.getInstance().getTotalWorkingDays(totalAttendence);
		toatalPresentDays = StudentAttendanceSummaryHelper.getInstance().getTotalPresentDays(totalAttendence);
		totalAbsentDays = totalWorkingDays - toatalPresentDays;
		attendanceSummaryForm.setTotalPresentDays(String.valueOf(toatalPresentDays));
		attendanceSummaryForm.setTotalAbsentDays(String.valueOf(totalAbsentDays));
		if(totalWorkingDays != 0 || toatalPresentDays != 0 || totalAbsentDays != 0){
			totalPercentages = CommonUtil.roundToDecimal(((toatalPresentDays/totalWorkingDays)*100), 2);
			}
		}
		attendanceSummaryForm.setTotalWorkingDays(String.valueOf(totalWorkingDays));
		attendanceSummaryForm.setTotalCurrPercentage(String.valueOf(totalPercentages));
		return studentWiseSubjectSummaryTOList;
	}
	
//	priyatham function end
	
	//mary function
	
	public List<StudentWiseSubjectSummaryTO> getStudentSubjectWiseAttendanceSubjectList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId,boolean isMarksPresent)throws Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		int activityCount=0;
		String mode="Current";
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		attendanceSummaryForm.setMarkPresent(isMarksPresent);
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getStudentAbsenceInformationSummaryQueryForConducted(studentId, classId));
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));
		
		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
		
		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromAttendanceSummaryMapToListAdditional(classesConductedMap,
						classesPresentMap,studentId,attendanceSummaryForm,mode);
		// added for subject order
		Student stu=studentWiseAttendanceSummaryTransaction.getSudentSemAcademicYear(studentId);
		int semNo=0;
		int semAcademicYear=0;
		if(stu!=null){
			if(stu.getClassSchemewise()!=null && stu.getClassSchemewise().getClasses()!=null)
		semNo=stu.getClassSchemewise().getClasses().getTermNumber();
			if(stu.getClassSchemewise()!=null && stu.getClassSchemewise().getCurriculumSchemeDuration()!=null)
				semAcademicYear=stu.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();	
		}
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId),semNo,semAcademicYear);
		
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
		
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		boolean isSortRequired = true;
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}*/
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
			}
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
				isSortRequired = false;
			}
		
			
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
				attendanceSummaryForm.setMarkPresent(true);
			 }
		
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		if(isSortRequired){
			Collections.sort(studentWiseSubjectSummaryWithMarkTOList);
		}
		
		//Activity attendance
		
		List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=getActivityWiseAttendanceList(studentId);
		if(studentActivitywiseAttendanceToList!=null){
		Iterator<StudentWiseSubjectSummaryTO> studentActivityItr=studentActivitywiseAttendanceToList.iterator();
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		while(studentActivityItr.hasNext()){
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO=studentActivityItr.next();
			
			if(studentWiseSubjectSummaryTO.getActivityName()!=null){
				studentWiseSubjectSummaryTO.setSubjectName(studentWiseSubjectSummaryTO.getActivityName());
			}else{
				studentWiseSubjectSummaryTO.setSubjectName(null);
			}
			
			AttendanceTypeTO attendanceTypeTO=new AttendanceTypeTO();
			List<AttendanceTypeTO> attendanceTypeTOList=new ArrayList<AttendanceTypeTO>();
			attendanceTypeTO.setFlag(true);
			if(studentWiseSubjectSummaryTO.getStudentId()!=null){
				attendanceTypeTO.setStudentId(studentWiseSubjectSummaryTO.getStudentId());
			}
			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
				attendanceTypeTO.setConductedClasses(studentWiseSubjectSummaryTO.getConductedClasses());
			}
			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
				attendanceTypeTO.setClassesPresent(studentWiseSubjectSummaryTO.getClassesPresent());
			}
			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
				attendanceTypeTO.setClassesAbsent(studentWiseSubjectSummaryTO.getClassesAbsent());
			}
			if(studentWiseSubjectSummaryTO.getPercentageWithoutLeave()!=0.0f){
				attendanceTypeTO.setPercentage(studentWiseSubjectSummaryTO.getPercentageWithoutLeave());
			}
			if(studentWiseSubjectSummaryTO.getActivityID()!=null){
				attendanceTypeTO.setActivityID(studentWiseSubjectSummaryTO.getActivityID());
			}
			if(studentWiseSubjectSummaryTO.getAttendanceTypeName()!=null){
				attendanceTypeTO.setAttendanceTypeName(studentWiseSubjectSummaryTO.getAttendanceTypeName());
			}
			attendanceTypeTOList.add(attendanceTypeTO);
			studentWiseSubjectSummaryTO.setAttendanceTypeList(attendanceTypeTOList);
			
			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
				/*String totalclassesConducted=attendanceSummaryForm.getTotalConducted();
				classesConducted=classesConducted+Float.parseFloat(totalclassesConducted)+studentWiseSubjectSummaryTO.getConductedClasses();*/
				classesConducted=classesConducted+studentWiseSubjectSummaryTO.getConductedClasses();
			}
			
			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
				/*String totalCalssesPresent=attendanceSummaryForm.getTotalPresent();
				classesPresent=classesPresent+Float.parseFloat(totalCalssesPresent)+studentWiseSubjectSummaryTO.getClassesPresent();*/
				classesPresent=classesPresent+studentWiseSubjectSummaryTO.getClassesPresent();
			}
			
			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
				/*String totalClassesAbsent=attendanceSummaryForm.getTotalAbscent();
				classesAbsent=classesAbsent+Float.parseFloat(totalClassesAbsent)+studentWiseSubjectSummaryTO.getClassesAbsent();*/
				classesAbsent=classesAbsent+studentWiseSubjectSummaryTO.getClassesAbsent();
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		}
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
		percentage=(classesPresent/classesConducted)*100;
		DecimalFormat df = new DecimalFormat("#.##");
		attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
		attendanceSummaryForm.setTotalConducted(""+classesConducted);
		attendanceSummaryForm.setTotalPresent(""+classesPresent);
		attendanceSummaryForm.setTotalAbscent(""+classesAbsent);

		}
		return studentWiseSubjectSummaryWithMarkTOList;
		
	}
// Mary code ends

	
	/**
	 * @param studentWiseAttendanceSummaryForm
	 * @param studentId
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<ExamMarksEntryDetailsTO> getMidSemResult(StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm, int studentId, String courseId) throws Exception{
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList = StudentAttendanceSummaryHelper.getInstance().convertMarkListBOToMarksTO(examMarksEntryDetailsBOList, examIdList, courseId, studentWiseAttendanceSummaryForm, studentId);
		return examMarksEntryDetailsTOList;
	}
	
//	/**
//	 * @param studentId
//	 * @param attendanceSummaryForm
//	 * @param courseId
//	 * @return
//	 * @throws Exception
//	 */
//	public List<StudentWiseSubjectSummaryTO> getMidSemMarksDetails(
//			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
//		float classesConducted=0;
//		float classesPresent=0;
//		float classesAbsent=0;
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList=getStudentSubjectMarkList(studentId, attendanceSummaryForm, courseId);
//		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
//		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
//		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		
//		
//		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
//		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
//		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		if(studentWiseSubjectSummaryTOList==null){
//			studentWiseSubjectSummaryTOList=new ArrayList<StudentWiseSubjectSummaryTO>();
//		}
//		
//		return studentWiseSubjectSummaryTOList;
//	}
//	/**
//	 * @param studentId
//	 * @param attendanceSummaryForm
//	 * @param courseId
//	 * @return
//	 * @throws Exception
//	 */
//	public List<StudentWiseSubjectSummaryTO> getStudentSubjectMarkList(
//			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
//		
//		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
//		attendanceSummaryForm.setMarkPresent(false);
//		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
//		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper.getInstance().getStudentAbsenceInformationSummaryQueryForConducted(studentId, classId));
//		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper.getInstance().getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
//		
//		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper.getInstance().convertFromAttendanceSummaryMapToList(classesConductedMap,classesPresentMap,studentId,attendanceSummaryForm);
//		
//		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId));
//		
//		
//		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
//		
//		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
//		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMapList(examMarksEntryDetailsBOList, examIdList, true, courseId, studentId);
//		
//		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
//		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
//		boolean isSortRequired = true;
//		while (subjIter.hasNext()) {
//			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
//					.next();
//			examMarksEntryDetailsTOList= new ArrayList<ExamMarksEntryDetailsTO>();
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
//				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
//			}
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
//				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
//				isSortRequired = false;
//			}
//			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
//				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
//				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
//				attendanceSummaryForm.setMarkPresent(true);
//				
//			}
//			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
//		}
//		if(isSortRequired){
//			Collections.sort(studentWiseSubjectSummaryWithMarkTOList, new AttSubjectCodeComparator());
//		}
//		
//		return studentWiseSubjectSummaryWithMarkTOList;
//		
//	}
	
	/**
	 * @param subjectSummaryTOs
	 * @return
	 * @throws Exception
	 */
	public List<ExamDefinitionBO> getSortedExamNames(List<SubjectTO> subjectTOs)throws Exception {
		
		List<ExamDefinitionBO> examDefinitionList = StudentAttendanceSummaryHelper.getInstance().sortExamMarkMap(subjectTOs);
		List<ExamDefinitionBO> examDefinitionBOs = new ArrayList<ExamDefinitionBO>();
		if(examDefinitionList != null && !examDefinitionList.isEmpty()){
			Iterator<ExamDefinitionBO> examIterator = examDefinitionList.iterator();
			while (examIterator.hasNext()) {
				ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) examIterator.next();
				boolean displayName = false;
				if(subjectTOs != null && !subjectTOs.isEmpty()){
					Iterator<SubjectTO> subIterator = subjectTOs.iterator();
					List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOs = new ArrayList<ExamMarksEntryDetailsTO>();
					while (subIterator.hasNext()) {
						SubjectTO subjectTO = (SubjectTO) subIterator.next();
						if(subjectTO.getExamMarksEntryDetailsTOList() != null && !subjectTO.getExamMarksEntryDetailsTOList().isEmpty()){
							examMarksEntryDetailsTOs = subjectTO.getExamMarksEntryDetailsTOList();
						}
						Iterator<ExamMarksEntryDetailsTO> examMarkIterator = examMarksEntryDetailsTOs.iterator(); 
						while (examMarkIterator.hasNext()) {
							ExamMarksEntryDetailsTO examMarksEntryDetailsTO = (ExamMarksEntryDetailsTO) examMarkIterator.next();
							if(examMarksEntryDetailsTO.getExamCode() != null && !examMarksEntryDetailsTO.getExamCode().isEmpty() && examDefinitionBO.getExamCode() != null && !examDefinitionBO.getExamCode().isEmpty()){
								if(examMarksEntryDetailsTO.getExamCode().equalsIgnoreCase(examDefinitionBO.getExamCode())){
									if(examMarksEntryDetailsTO.getTheoryMarks() != null && !examMarksEntryDetailsTO.getTheoryMarks().isEmpty()){
										displayName = true;
									}
								}
							}
						}
					}
				}
				if(displayName){
					examDefinitionBOs.add(examDefinitionBO);
				}
			}
		}
		
		return examDefinitionBOs;
	}

	/**
	 * @param valueOf
	 * @param studentWiseAttendanceSummaryForm
	 * @param courseId
	 * @return
	 * @throws Exception 
	 */
	public List<SubjectTO> getSubjectListWithMarks(Integer studentId, StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm, String courseId) throws Exception {
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<Subject> subjectBOList = studentWiseAttendanceSummaryTransaction.getSubjectsListForStudent(studentId);
		List<SubjectTO> subjectList = StudentAttendanceSummaryHelper.getInstance().convertSubjectBOToSubjectTO(subjectBOList);
		//added for subject order
		Student stu=studentWiseAttendanceSummaryTransaction.getSudentSemAcademicYear(studentId);
		int semNo=0;
		int semAcademicYear=0;
		if(stu!=null){
			if(stu.getClassSchemewise()!=null && stu.getClassSchemewise().getClasses()!=null)
		semNo=stu.getClassSchemewise().getClasses().getTermNumber();
			if(stu.getClassSchemewise()!=null && stu.getClassSchemewise().getCurriculumSchemeDuration()!=null)
				semAcademicYear=stu.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();	
		}
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId),semNo,semAcademicYear);
		
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMapList(examMarksEntryDetailsBOList, examIdList, true, courseId, studentId);
		
		Iterator<SubjectTO> subjIter = subjectList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<SubjectTO> subjectTOList = new ArrayList<SubjectTO>();
		
		boolean isSortRequired = true;
		while (subjIter.hasNext()) {
			SubjectTO subjectTO = (SubjectTO) subjIter.next();
			if(orderMap.get(Integer.parseInt(subjectTO.getSubjectID()))!= null){
				subjectTO.setOrder(orderMap.get(Integer.parseInt(subjectTO.getSubjectID())));
			}
			if(orderMap.get(Integer.parseInt(subjectTO.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(subjectTO.getSubjectID())) == 0){
				isSortRequired = false;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(subjectTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(subjectTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				subjectTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
				studentWiseAttendanceSummaryForm.setMarkPresent(true);
				/*modified by sudhir */
				subjectTOList.add(subjectTO);
			}
			
		}
		if(isSortRequired){
			SubjectGroupDetailsComparator comparator=new SubjectGroupDetailsComparator();
			comparator.setCompare(1);
			Collections.sort(subjectTOList,comparator);
		}
		List<SubjectTO> subjectsTOList = new ArrayList<SubjectTO>();
		if(subjectTOList != null && !subjectTOList.isEmpty()){
			Iterator<SubjectTO> toIterator = subjectTOList.iterator();
			while (toIterator.hasNext()) {
				SubjectTO subjectTO = (SubjectTO) toIterator.next();
				List<ExamMarksEntryDetailsTO> examMarksEntryDetailsList = subjectTO.getExamMarksEntryDetailsTOList();
				List<ExamMarksEntryDetailsTO> examMarksEntryDetails = new ArrayList<ExamMarksEntryDetailsTO>();
				if(examMarksEntryDetailsList != null && !examMarksEntryDetailsList.isEmpty()){
					Iterator<ExamMarksEntryDetailsTO> marksEntryIterator = examMarksEntryDetailsList.iterator();
					boolean displayName = true;
					while (marksEntryIterator.hasNext()) {
						ExamMarksEntryDetailsTO examMarksEntryDetailsTO = (ExamMarksEntryDetailsTO) marksEntryIterator.next();
						if(examMarksEntryDetailsTO.getTheoryMarks() != null && !examMarksEntryDetailsTO.getTheoryMarks().isEmpty()){
							displayName = false;
						}
						if(displayName){
							examMarksEntryDetailsTO.setExamCode(null);
						}
						examMarksEntryDetails.add(examMarksEntryDetailsTO);
					}
				}
				subjectTO.setExamMarksEntryDetailsTOList(examMarksEntryDetails);
				subjectsTOList.add(subjectTO);
			}
		}
		
		return subjectsTOList;
	}
	public List<StudentWiseSubjectSummaryTO> getSubjectWiseAttendanceListView(
			int studentId) throws ApplicationException, Exception {
		//StudentWiseAttendanceSummaryForm attendanceSummaryForm=null;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		HashMap<Integer, String> subjectCodeMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();
					if(applicantSubjectGroup.getSubjectGroup()!=null){
						subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId());
						count++;
					}
				}
				StringBuilder intType =new StringBuilder();
				if (subjGroupString!= null && subjGroupString.length > 0) {
					String [] tempArray = subjGroupString;
					for(int i=0;i<tempArray.length;i++){
						intType.append(tempArray[i]);
						 if(i<(tempArray.length-1)){
							 intType.append(",");
						 }
					}
				}
				if(!intType.toString().isEmpty()){
					if(!CMSConstants.LINK_FOR_CJC)
					{
					subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupId(intType.toString());
					subjectCodeMap = studentWiseAttendanceSummaryTransaction.getSubjectCodesBySubjectGroupId(intType.toString());
					}
					else if(CMSConstants.LINK_FOR_CJC)
					{
						subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdCJC(intType.toString());
						subjectCodeMap = studentWiseAttendanceSummaryTransaction.getSubjectCodesBySubjectGroupIdCJC(intType.toString());	
					}
				}
			}
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		
		/*List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getStudentAbsenceInformationSummaryQueryForConducted(studentId, classId));
      List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));*/
		
		
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForConducted(studentId,classId));
		
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForPresent(studentId,classId));		
		List<Object[]> classesOnLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnLeave(
						studentId,classId));
			
		
		List<Object[]> classesOnCocurricularLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnCocurricularLeave(
						studentId,classId));
		log.info("classesOnCocurricularLeaveList.size(): " + classesOnCocurricularLeaveList.size());
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnLeaveListToMap(classesOnLeaveList);
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesConductedList, false);
		Map<Integer, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesPresentList, true);
		
		//Satish Patruni
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnCocurricularLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnCoCurricularLeaveListToMap(classesOnCocurricularLeaveList);	
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromMapToList(classesConductedMap,
						classesPresentMap, classesOnLeaveMap, classesOnCocurricularLeaveMap);
     /* Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
		.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
	
	Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
	.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
	
	studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
			.getInstance().convertFromAttendanceSummaryMapToListView(classesConductedMap,
					classesPresentMap,studentId,attendanceSummaryForm,studentWiseSubjectSummary);*/

	//Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId));
	
	//List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
		
		List<MarksEntryDetails> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetailsView(studentId, classId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap  = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMapView(examMarksEntryDetailsBOList, new ArrayList<Integer>(), false);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			if(subjectCodeMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				String subCodeName = "(" + subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) + ")   " +  studentWiseSubjectSummaryTO.getSubjectName().trim();
				subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				studentWiseSubjectSummaryTO.setSubjectName(subCodeName);
			}
				
			if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		
		return studentWiseSubjectSummaryWithMarkTOList;

	}
	
	
	
	
//	public List<StudentWiseSubjectSummaryTO> getStudentViewAdditionalSubjectAttendance(
//			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId,boolean isMarksPresent)throws Exception {
//		float classesConducted=0;
//		float classesPresent=0;
//		float classesAbsent=0;
//		float percentage=0.0f;
//		int activityCount=0;
//		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
//		attendanceSummaryForm.setMarkPresent(isMarksPresent);
//		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
//		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
//				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
//						.getInstance()
//						.getStudentAbsenceInfoAdditionalQueryForConducted(studentId, classId));
//		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
//				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
//						.getInstance()
//						.getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
//			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
//		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
//		
//		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
//				.getInstance().convertFromAttendanceSummaryMapToListAdditional(classesConductedMap,
//						classesPresentMap,studentId,attendanceSummaryForm);
//
//		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId));
//		
//		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
//		
//		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
//		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
//		
//		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
//		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
//		boolean isSortRequired = true;
//		while (subjIter.hasNext()) {
//			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
//					.next();
//			examMarksEntryDetailsTOList= new ArrayList<ExamMarksEntryDetailsTO>();
//			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				continue;
//			}*/
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
//				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
//			}
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
//				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
//				isSortRequired = false;
//			}
//		
//			
//			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
//				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
//				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
//				attendanceSummaryForm.setMarkPresent(true);
//			 }
//		
//			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
//		}
//		if(isSortRequired){
//			Collections.sort(studentWiseSubjectSummaryWithMarkTOList, new AttSubjectCodeComparator());
//		}
//		
//		//Activity attendance
//		
//		List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=getActivityWiseAttendanceList(studentId);
//		if(studentActivitywiseAttendanceToList!=null){
//		Iterator<StudentWiseSubjectSummaryTO> studentActivityItr=studentActivitywiseAttendanceToList.iterator();
//		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
//		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
//		while(studentActivityItr.hasNext()){
//			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO=studentActivityItr.next();
//			
//			if(studentWiseSubjectSummaryTO.getActivityName()!=null){
//				studentWiseSubjectSummaryTO.setSubjectName(studentWiseSubjectSummaryTO.getActivityName());
//			}else{
//				studentWiseSubjectSummaryTO.setSubjectName(null);
//			}
//			
//			AttendanceTypeTO attendanceTypeTO=new AttendanceTypeTO();
//			List<AttendanceTypeTO> attendanceTypeTOList=new ArrayList<AttendanceTypeTO>();
//			attendanceTypeTO.setFlag(true);
//			if(studentWiseSubjectSummaryTO.getStudentId()!=null){
//				attendanceTypeTO.setStudentId(studentWiseSubjectSummaryTO.getStudentId());
//			}
//			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
//				attendanceTypeTO.setConductedClasses(studentWiseSubjectSummaryTO.getConductedClasses());
//			}
//			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
//				attendanceTypeTO.setClassesPresent(studentWiseSubjectSummaryTO.getClassesPresent());
//			}
//			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
//				attendanceTypeTO.setClassesAbsent(studentWiseSubjectSummaryTO.getClassesAbsent());
//			}
//			if(studentWiseSubjectSummaryTO.getPercentageWithoutLeave()!=0.0f){
//				attendanceTypeTO.setPercentage(studentWiseSubjectSummaryTO.getPercentageWithoutLeave());
//			}
//			if(studentWiseSubjectSummaryTO.getActivityID()!=null){
//				attendanceTypeTO.setActivityID(studentWiseSubjectSummaryTO.getActivityID());
//			}
//			if(studentWiseSubjectSummaryTO.getAttendanceTypeName()!=null){
//				attendanceTypeTO.setAttendanceTypeName(studentWiseSubjectSummaryTO.getAttendanceTypeName());
//			}
//			attendanceTypeTOList.add(attendanceTypeTO);
//			studentWiseSubjectSummaryTO.setAttendanceTypeList(attendanceTypeTOList);
//			
//			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
//				/*String totalclassesConducted=attendanceSummaryForm.getTotalConducted();
//				classesConducted=classesConducted+Float.parseFloat(totalclassesConducted)+studentWiseSubjectSummaryTO.getConductedClasses();*/
//				classesConducted=classesConducted+studentWiseSubjectSummaryTO.getConductedClasses();
//			}
//			
//			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
//				/*String totalCalssesPresent=attendanceSummaryForm.getTotalPresent();
//				classesPresent=classesPresent+Float.parseFloat(totalCalssesPresent)+studentWiseSubjectSummaryTO.getClassesPresent();*/
//				classesPresent=classesPresent+studentWiseSubjectSummaryTO.getClassesPresent();
//			}
//			
//			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
//				/*String totalClassesAbsent=attendanceSummaryForm.getTotalAbscent();
//				classesAbsent=classesAbsent+Float.parseFloat(totalClassesAbsent)+studentWiseSubjectSummaryTO.getClassesAbsent();*/
//				classesAbsent=classesAbsent+studentWiseSubjectSummaryTO.getClassesAbsent();
//			}
//			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
//		}
//		}
//		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
//		percentage=(classesPresent/classesConducted)*100;
//		DecimalFormat df = new DecimalFormat("#.##");
//		attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
//		attendanceSummaryForm.setTotalConducted(""+classesConducted);
//		attendanceSummaryForm.setTotalPresent(""+classesPresent);
//		attendanceSummaryForm.setTotalAbscent(""+classesAbsent);
//
//		}
//		return studentWiseSubjectSummaryWithMarkTOList;
//		
//	}

	
	
	public List<StudentWiseSubjectSummaryTO> getAdditionalSubjectWiseAttendanceListView(
			int studentId) throws ApplicationException, Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		int activityCount=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		HashMap<Integer, String> subjectCodeMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				//int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();
					if(applicantSubjectGroup.getSubjectGroup()!=null){
						subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId());
						count++;
					}
				}
				StringBuilder intType =new StringBuilder();
				if (subjGroupString!= null && subjGroupString.length > 0) {
					String [] tempArray = subjGroupString;
					for(int i=0;i<tempArray.length;i++){
						 intType.append(tempArray[i]);
						 if(i<(tempArray.length-1)){
							 intType.append(",");
						 }
					}
				}
				if(!intType.toString().isEmpty()){
					
					if(CMSConstants.LINK_FOR_CJC)
					{
						subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdCJCAdditional(intType.toString());
						subjectCodeMap = studentWiseAttendanceSummaryTransaction.getSubjectCodesBySubjectGroupIdCJCAdditional(intType.toString());	
					}
				}
			}
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForConducted(studentId,classId));
		
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getAbsenceInformationSummaryQueryForPresent(studentId,classId));
		
		List<Object[]> classesOnLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnLeave(
						studentId,classId));
			
		
		List<Object[]> classesOnCocurricularLeaveList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getAbsenceInformationSummaryQueryIsOnCocurricularLeave(
						studentId,classId));
		log.info("classesOnCocurricularLeaveList.size(): " + classesOnCocurricularLeaveList.size());
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnLeaveListToMap(classesOnLeaveList);
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesConductedList, false);
		Map<Integer, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
				.getInstance().convertListToMap(classesPresentList, true);
		
		
		Map<Integer, StudentWiseSubjectSummaryTO> classesOnCocurricularLeaveMap = StudentAttendanceSummaryHelper
		.getInstance().convertclassesOnCoCurricularLeaveListToMap(classesOnCocurricularLeaveList);		
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromMapToList(classesConductedMap,
						classesPresentMap, classesOnLeaveMap, classesOnCocurricularLeaveMap);
		
		List<MarksEntryDetails> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetailsView(studentId, classId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap  = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMapView(examMarksEntryDetailsBOList, new ArrayList<Integer>(), false);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			if(subjectCodeMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				String subCodeName = "(" + subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) + ")   " +  studentWiseSubjectSummaryTO.getSubjectName().trim();
				subjectCodeMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				studentWiseSubjectSummaryTO.setSubjectName(subCodeName);
			}
				
			if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
//Activity attendance
		
		List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=getActivityWiseAttendanceList(studentId);
		if(studentActivitywiseAttendanceToList!=null){
		Iterator<StudentWiseSubjectSummaryTO> studentActivityItr=studentActivitywiseAttendanceToList.iterator();
		//classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
	//	classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
	//	classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		while(studentActivityItr.hasNext()){
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO=studentActivityItr.next();
			
			if(studentWiseSubjectSummaryTO.getActivityName()!=null){
				studentWiseSubjectSummaryTO.setSubjectName(studentWiseSubjectSummaryTO.getActivityName());
			}else{
				studentWiseSubjectSummaryTO.setSubjectName(null);
			}
			
			AttendanceTypeTO attendanceTypeTO=new AttendanceTypeTO();
			List<AttendanceTypeTO> attendanceTypeTOList=new ArrayList<AttendanceTypeTO>();
			attendanceTypeTO.setFlag(true);
			if(studentWiseSubjectSummaryTO.getStudentId()!=null){
				attendanceTypeTO.setStudentId(studentWiseSubjectSummaryTO.getStudentId());
			}
			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
				attendanceTypeTO.setConductedClasses(studentWiseSubjectSummaryTO.getConductedClasses());
			}
			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
				attendanceTypeTO.setClassesPresent(studentWiseSubjectSummaryTO.getClassesPresent());
			}
			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
				attendanceTypeTO.setClassesAbsent(studentWiseSubjectSummaryTO.getClassesAbsent());
			}
			if(studentWiseSubjectSummaryTO.getPercentageWithoutLeave()!=0.0f){
				attendanceTypeTO.setPercentage(studentWiseSubjectSummaryTO.getPercentageWithoutLeave());
			}
			if(studentWiseSubjectSummaryTO.getActivityID()!=null){
				attendanceTypeTO.setActivityID(studentWiseSubjectSummaryTO.getActivityID());
			}
			if(studentWiseSubjectSummaryTO.getAttendanceTypeName()!=null){
				attendanceTypeTO.setAttendanceTypeName(studentWiseSubjectSummaryTO.getAttendanceTypeName());
			}
			attendanceTypeTOList.add(attendanceTypeTO);
			studentWiseSubjectSummaryTO.setAttendanceTypeList(attendanceTypeTOList);
			
			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
				classesConducted=classesConducted+studentWiseSubjectSummaryTO.getConductedClasses();
			}
			
			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
				classesPresent=classesPresent+studentWiseSubjectSummaryTO.getClassesPresent();
			}
			
			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
				classesAbsent=classesAbsent+studentWiseSubjectSummaryTO.getClassesAbsent();
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		}
	/*	if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
			percentage=(classesPresent/classesConducted)*100;
			DecimalFormat df = new DecimalFormat("#.##");
			attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
			attendanceSummaryForm.setTotalConducted(""+classesConducted);
			attendanceSummaryForm.setTotalPresent(""+classesPresent);
			attendanceSummaryForm.setTotalAbscent(""+classesAbsent);

			}*/
		
		return studentWiseSubjectSummaryWithMarkTOList;

	}
	

//	public List<StudentWiseSubjectSummaryTO> getStudentActivityAttendanceForStudentDetailView(int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId,boolean isMarksPresent)throws Exception {
//		float classesConducted=0;
//		float classesPresent=0;
//		float classesAbsent=0;
//		float percentage=0.0f;
//		int activityCount=0;
//		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
//		attendanceSummaryForm.setMarkPresent(isMarksPresent);
//		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
//		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
//				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
//						.getInstance()
//						.getStudentAbsenceInformationSummaryQueryForConducted(studentId, classId));
//		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
//				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
//						.getInstance()
//						.getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
//			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
//		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
//		
//		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
//				.getInstance().convertFromAttendanceSummaryMapToListAdditional(classesConductedMap,
//						classesPresentMap,studentId,attendanceSummaryForm);
//
//		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId));
//		
//		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
//		
//		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
//		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
//		
//		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
//		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
//		boolean isSortRequired = true;
//		while (subjIter.hasNext()) {
//			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
//					.next();
//			examMarksEntryDetailsTOList= new ArrayList<ExamMarksEntryDetailsTO>();
//			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				continue;
//			}*/
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
//				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
//			}
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
//				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
//				isSortRequired = false;
//			}
//		
//			
//			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
//				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
//				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
//				attendanceSummaryForm.setMarkPresent(true);
//			 }
//		
//			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
//		}
//		if(isSortRequired){
//			Collections.sort(studentWiseSubjectSummaryWithMarkTOList, new AttSubjectCodeComparator());
//		}
//		
//		//Activity attendance
//		
//		List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=getActivityWiseAttendanceList(studentId);
//		if(studentActivitywiseAttendanceToList!=null){
//		Iterator<StudentWiseSubjectSummaryTO> studentActivityItr=studentActivitywiseAttendanceToList.iterator();
//		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
//		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
//		while(studentActivityItr.hasNext()){
//			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO=studentActivityItr.next();
//			
//			if(studentWiseSubjectSummaryTO.getActivityName()!=null){
//				studentWiseSubjectSummaryTO.setSubjectName(studentWiseSubjectSummaryTO.getActivityName());
//			}else{
//				studentWiseSubjectSummaryTO.setSubjectName(null);
//			}
//			
//			AttendanceTypeTO attendanceTypeTO=new AttendanceTypeTO();
//			List<AttendanceTypeTO> attendanceTypeTOList=new ArrayList<AttendanceTypeTO>();
//			attendanceTypeTO.setFlag(true);
//			if(studentWiseSubjectSummaryTO.getStudentId()!=null){
//				attendanceTypeTO.setStudentId(studentWiseSubjectSummaryTO.getStudentId());
//			}
//			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
//				attendanceTypeTO.setConductedClasses(studentWiseSubjectSummaryTO.getConductedClasses());
//			}
//			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
//				attendanceTypeTO.setClassesPresent(studentWiseSubjectSummaryTO.getClassesPresent());
//			}
//			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
//				attendanceTypeTO.setClassesAbsent(studentWiseSubjectSummaryTO.getClassesAbsent());
//			}
//			if(studentWiseSubjectSummaryTO.getPercentageWithoutLeave()!=0.0f){
//				attendanceTypeTO.setPercentage(studentWiseSubjectSummaryTO.getPercentageWithoutLeave());
//			}
//			if(studentWiseSubjectSummaryTO.getActivityID()!=null){
//				attendanceTypeTO.setActivityID(studentWiseSubjectSummaryTO.getActivityID());
//			}
//			if(studentWiseSubjectSummaryTO.getAttendanceTypeName()!=null){
//				attendanceTypeTO.setAttendanceTypeName(studentWiseSubjectSummaryTO.getAttendanceTypeName());
//			}
//			attendanceTypeTOList.add(attendanceTypeTO);
//			studentWiseSubjectSummaryTO.setAttendanceTypeList(attendanceTypeTOList);
//			
//			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
//				/*String totalclassesConducted=attendanceSummaryForm.getTotalConducted();
//				classesConducted=classesConducted+Float.parseFloat(totalclassesConducted)+studentWiseSubjectSummaryTO.getConductedClasses();*/
//				classesConducted=classesConducted+studentWiseSubjectSummaryTO.getConductedClasses();
//			}
//			
//			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
//				/*String totalCalssesPresent=attendanceSummaryForm.getTotalPresent();
//				classesPresent=classesPresent+Float.parseFloat(totalCalssesPresent)+studentWiseSubjectSummaryTO.getClassesPresent();*/
//				classesPresent=classesPresent+studentWiseSubjectSummaryTO.getClassesPresent();
//			}
//			
//			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
//				/*String totalClassesAbsent=attendanceSummaryForm.getTotalAbscent();
//				classesAbsent=classesAbsent+Float.parseFloat(totalClassesAbsent)+studentWiseSubjectSummaryTO.getClassesAbsent();*/
//				classesAbsent=classesAbsent+studentWiseSubjectSummaryTO.getClassesAbsent();
//			}
//			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
//		}
//		}
//		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
//		percentage=(classesPresent/classesConducted)*100;
//		DecimalFormat df = new DecimalFormat("#.##");
//		attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
//		attendanceSummaryForm.setTotalConducted(""+classesConducted);
//		attendanceSummaryForm.setTotalPresent(""+classesPresent);
//		attendanceSummaryForm.setTotalAbscent(""+classesAbsent);
//
//		}
//		return studentWiseSubjectSummaryWithMarkTOList;
//		
//	}
	
	
	
	
	
	
//	public List<StudentWiseSubjectSummaryTO> getStudentSubjectWiseAttendanceListView(
//			Integer studentId, DisciplinaryDetailsForm objForm, int courseId)throws Exception {
//		/*float classesConducted=0;
//		float classesPresent=0;
//		float classesAbsent=0;
//		float percentage=0.0f;
//		int activityCount=0;*/
//		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
//		objForm.setMarkPresent(false);
//		int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
//		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
//				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
//						.getInstance()
//						.getStudentAbsenceInformationSummaryQueryForConducted(studentId, classId));
//		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
//				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
//						.getInstance()
//						.getStudentAbsenceInformationSummaryQueryForPresent1(studentId, classId));
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
//			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
//		
//		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
//		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
//		
//		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
//				.getInstance().convertFromAttendanceSummaryMapToListView(classesConductedMap,
//						classesPresentMap,studentId,objForm);
//
//		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(courseId);
//		
//		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(classId);
//		
//		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
//		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
//		
//		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
//		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
//		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
//		boolean isSortRequired = true;
//		while (subjIter.hasNext()) {
//			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
//					.next();
//			examMarksEntryDetailsTOList= new ArrayList<ExamMarksEntryDetailsTO>();
//			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				continue;
//			}*/
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
//				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
//			}
//			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
//				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
//				isSortRequired = false;
//			}
//			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
//				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
//				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
//				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
//				objForm.setMarkPresent(true);
//				
//			}
//			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
//		}
//		if(isSortRequired){
//			Collections.sort(studentWiseSubjectSummaryWithMarkTOList, new AttSubjectCodeComparator());
//		}
//		
//		return studentWiseSubjectSummaryWithMarkTOList;
//		
//		//return studentWiseSubjectSummaryTOList;
//	}
	
	/**
	 * @param studentId
	 * @param attendanceSummaryForm
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> getPreviousStudentWiseSubjectsAttendanceList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList=getPreviousStudentSubjectWiseAttendanceList(studentId, attendanceSummaryForm, courseId);
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummayToList1=getPreviousStudentSubjectWiseAttendanceSubjectList(studentId, attendanceSummaryForm, courseId,attendanceSummaryForm.isMarkPresent());
		
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		if(studentWiseSubjectSummaryTOList==null){
			studentWiseSubjectSummaryTOList=new ArrayList<StudentWiseSubjectSummaryTO>();
		}
		if(studentWiseSubjectSummayToList1!=null){
			studentWiseSubjectSummaryTOList.addAll(studentWiseSubjectSummayToList1);
		}
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
			percentage=CommonUtil.roundToDecimal(((classesPresent/classesConducted)*100),2);
			DecimalFormat df = new DecimalFormat("#.##");
			attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
			attendanceSummaryForm.setTotalConducted(""+classesConducted);
			attendanceSummaryForm.setTotalPresent(""+classesPresent);
			attendanceSummaryForm.setTotalAbscent(""+classesAbsent);
			}
		return studentWiseSubjectSummaryTOList;
	}
	/**
	 * @param studentId
	 * @param attendanceSummaryForm
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> getPreviousStudentSubjectWiseAttendanceList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		attendanceSummaryForm.setMarkPresent(false);
		String mode="Previous";
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getPreviousStudentAbsenceQueryForConducted(studentId, Integer.parseInt(attendanceSummaryForm.getClassesId())));
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getPreviousStudentAbsenceQueryForPresent1(studentId,Integer.parseInt(attendanceSummaryForm.getClassesId())));
		
		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
		
		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromAttendanceSummaryMapToList(classesConductedMap,classesPresentMap,studentId,attendanceSummaryForm,mode);
		// added for subject order
		ClassSchemewise classScheme=studentWiseAttendanceSummaryTransaction.getClassSchemeSemAcademicYear(Integer.parseInt(attendanceSummaryForm.getClassesId()));
		int semNo=0;
		int semAcademicYear=0;
		if(classScheme!=null){
			if(classScheme.getClasses()!=null)
		     semNo=classScheme.getClasses().getTermNumber();
			if(classScheme.getCurriculumSchemeDuration()!=null)
				semAcademicYear=classScheme.getCurriculumSchemeDuration().getAcademicYear();	
		}
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId),semNo,semAcademicYear);
		
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(Integer.parseInt(attendanceSummaryForm.getClassesId()));
		
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		boolean isSortRequired = true;
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}*/
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
			}
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
				isSortRequired = false;
			}
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
				attendanceSummaryForm.setMarkPresent(true);
				
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		if(isSortRequired){
			Collections.sort(studentWiseSubjectSummaryWithMarkTOList);
		}
		
		return studentWiseSubjectSummaryWithMarkTOList;
		
	}
	/**
	 * @param studentId
	 * @param attendanceSummaryForm
	 * @param courseId
	 * @param isMarksPresent
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> getPreviousStudentSubjectWiseAttendanceSubjectList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId,boolean isMarksPresent)throws Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		int activityCount=0;
		String mode="Previous";
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		attendanceSummaryForm.setMarkPresent(isMarksPresent);
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = null;
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getPreviousStudentAbsenceQueryForConducted(studentId, Integer.parseInt(attendanceSummaryForm.getClassesId())));
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
				.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
						.getInstance()
						.getPreviousStudentAbsenceQueryForPresent1(studentId, Integer.parseInt(attendanceSummaryForm.getClassesId())));
		
		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
			.getInstance().convertAttendanceSummaryListToMap(classesConductedList);
		
		Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
		.getInstance().convertAttendanceSummaryListToMap(classesPresentList);
		
		studentWiseSubjectSummaryTOList = StudentAttendanceSummaryHelper
				.getInstance().convertFromAttendanceSummaryMapToListAdditional(classesConductedMap,classesPresentMap,studentId,attendanceSummaryForm,mode);
		// added for subject order
		ClassSchemewise classScheme=studentWiseAttendanceSummaryTransaction.getClassSchemeSemAcademicYear(Integer.parseInt(attendanceSummaryForm.getClassesId()));
		int semNo=0;
		int semAcademicYear=0;
		if(classScheme!=null){
			if(classScheme.getClasses()!=null)
		semNo=classScheme.getClasses().getTermNumber();
			if(classScheme.getCurriculumSchemeDuration()!=null)
				semAcademicYear=classScheme.getCurriculumSchemeDuration().getAcademicYear();	
		}
		Map<Integer, Integer> orderMap = studentWiseAttendanceSummaryTransaction.getSubjectOrder(Integer.parseInt(courseId),semNo,semAcademicYear);
		
		List<Integer> examIdList = studentWiseAttendanceSummaryTransaction.getExamPublishedIds(Integer.parseInt(attendanceSummaryForm.getClassesId()));
		
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = studentWiseAttendanceSummaryTransaction.getStudentWiseExamMarkDetails(studentId);
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarksEntryDetailsTOMap = StudentAttendanceSummaryHelper.getInstance().convertMarkListToMap(examMarksEntryDetailsBOList, examIdList, true);
		
		Iterator<StudentWiseSubjectSummaryTO> subjIter = studentWiseSubjectSummaryTOList.iterator();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryWithMarkTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		boolean isSortRequired = true;
		while (subjIter.hasNext()) {
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjIter
					.next();
			/*if(!subjectMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				continue;
			}*/
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))!= null){
				studentWiseSubjectSummaryTO.setOrder(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())));
			}
			if(orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == null || 
				orderMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID())) == 0){
				isSortRequired = false;
			}
		
			
			if(examMarksEntryDetailsTOMap.containsKey(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()))){
				examMarksEntryDetailsTOList = examMarksEntryDetailsTOMap.get(Integer.parseInt(studentWiseSubjectSummaryTO.getSubjectID()));
				Collections.sort(examMarksEntryDetailsTOList, new ExamCodeComparator());
				studentWiseSubjectSummaryTO.setExamMarksEntryDetailsTOList(examMarksEntryDetailsTOList);
				attendanceSummaryForm.setMarkPresent(true);
			 }
		
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		if(isSortRequired){
			Collections.sort(studentWiseSubjectSummaryWithMarkTOList);
		}
		
		//Activity attendance
		
		List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=getPreviousActivityWiseAttendanceList(studentId,semAcademicYear,Integer.parseInt(attendanceSummaryForm.getClassesId()));
		if(studentActivitywiseAttendanceToList!=null){
		Iterator<StudentWiseSubjectSummaryTO> studentActivityItr=studentActivitywiseAttendanceToList.iterator();
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		while(studentActivityItr.hasNext()){
			StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO=studentActivityItr.next();
			
			if(studentWiseSubjectSummaryTO.getActivityName()!=null){
				studentWiseSubjectSummaryTO.setSubjectName(studentWiseSubjectSummaryTO.getActivityName());
			}else{
				studentWiseSubjectSummaryTO.setSubjectName(null);
			}
			
			AttendanceTypeTO attendanceTypeTO=new AttendanceTypeTO();
			List<AttendanceTypeTO> attendanceTypeTOList=new ArrayList<AttendanceTypeTO>();
			attendanceTypeTO.setFlag(true);
			if(studentWiseSubjectSummaryTO.getStudentId()!=null){
				attendanceTypeTO.setStudentId(studentWiseSubjectSummaryTO.getStudentId());
			}
			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
				attendanceTypeTO.setConductedClasses(studentWiseSubjectSummaryTO.getConductedClasses());
			}
			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
				attendanceTypeTO.setClassesPresent(studentWiseSubjectSummaryTO.getClassesPresent());
			}
			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
				attendanceTypeTO.setClassesAbsent(studentWiseSubjectSummaryTO.getClassesAbsent());
			}
			if(studentWiseSubjectSummaryTO.getPercentageWithoutLeave()!=0.0f){
				attendanceTypeTO.setPercentage(studentWiseSubjectSummaryTO.getPercentageWithoutLeave());
			}
			if(studentWiseSubjectSummaryTO.getActivityID()!=null){
				attendanceTypeTO.setActivityID(studentWiseSubjectSummaryTO.getActivityID());
			}
			if(studentWiseSubjectSummaryTO.getAttendanceTypeName()!=null){
				attendanceTypeTO.setAttendanceTypeName(studentWiseSubjectSummaryTO.getAttendanceTypeName());
			}
			attendanceTypeTOList.add(attendanceTypeTO);
			studentWiseSubjectSummaryTO.setAttendanceTypeList(attendanceTypeTOList);
			
			if(studentWiseSubjectSummaryTO.getConductedClasses()!=0){
				/*String totalclassesConducted=attendanceSummaryForm.getTotalConducted();
				classesConducted=classesConducted+Float.parseFloat(totalclassesConducted)+studentWiseSubjectSummaryTO.getConductedClasses();*/
				classesConducted=classesConducted+studentWiseSubjectSummaryTO.getConductedClasses();
			}
			
			if(studentWiseSubjectSummaryTO.getClassesPresent()!=0){
				/*String totalCalssesPresent=attendanceSummaryForm.getTotalPresent();
				classesPresent=classesPresent+Float.parseFloat(totalCalssesPresent)+studentWiseSubjectSummaryTO.getClassesPresent();*/
				classesPresent=classesPresent+studentWiseSubjectSummaryTO.getClassesPresent();
			}
			
			if(studentWiseSubjectSummaryTO.getClassesAbsent()!=0){
				/*String totalClassesAbsent=attendanceSummaryForm.getTotalAbscent();
				classesAbsent=classesAbsent+Float.parseFloat(totalClassesAbsent)+studentWiseSubjectSummaryTO.getClassesAbsent();*/
				classesAbsent=classesAbsent+studentWiseSubjectSummaryTO.getClassesAbsent();
			}
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		}
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
		percentage=(classesPresent/classesConducted)*100;
		DecimalFormat df = new DecimalFormat("#.##");
		attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
		attendanceSummaryForm.setTotalConducted(""+classesConducted);
		attendanceSummaryForm.setTotalPresent(""+classesPresent);
		attendanceSummaryForm.setTotalAbscent(""+classesAbsent);

		}
		return studentWiseSubjectSummaryWithMarkTOList;
		
	}
	/**
	 * 
	 * @param semAcademicYear 
	 * @param valueOf
	 * @returns activity attendance of the student
	 */

	public List<StudentWiseSubjectSummaryTO> getPreviousActivityWiseAttendanceList(
			int studentId, int currentYear,int classId) throws Exception{
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		List<Object[]> classesConductedList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getPreviousActivityInformationSummaryQueryForConducted(studentId,currentYear,classId));
		List<Object[]> classesPresentList = studentWiseAttendanceSummaryTransaction
		.getStudentWiseAttendanceSummaryInformation(StudentAttendanceSummaryHelper
				.getInstance()
				.getPreviousActivityInformationSummaryQueryForPresent(studentId,currentYear,classId));
		Map<String, StudentWiseSubjectSummaryTO> classesConductedMap = StudentAttendanceSummaryHelper
		.getInstance().convertActivityAttendanceSummaryListToMap(classesConductedList);
	
	Map<String, StudentWiseSubjectSummaryTO> classesPresentMap = StudentAttendanceSummaryHelper
	.getInstance().convertActivityAttendanceSummaryListToMap(classesPresentList);
	
	return StudentAttendanceSummaryHelper.getInstance().convertFromActivityAttendanceSummaryMapToList(classesConductedMap,
			classesPresentMap);
	}
	/**
	 * @param studentid
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> setPreviousClassId(String studentid) throws Exception{
		IStudentWiseAttendanceSummaryTransaction transection= new StudentWiseAttendanceSummaryTransactionImpl();
		return transection.setPreviousClassId(studentid);
	}

	public String getClassesName (String classesId) throws Exception{
		IStudentWiseAttendanceSummaryTransaction transection= new StudentWiseAttendanceSummaryTransactionImpl();
		return transection.getClassesName(classesId);
	}
	
	
	
	
	
	//raghu
	
	public void getStudentWiseSessionAttendanceList(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
		/*float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList=getStudentSubjectWiseAttendanceList(studentId, attendanceSummaryForm, courseId);
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummayToList1=getStudentSubjectWiseAttendanceSubjectList(studentId, attendanceSummaryForm, courseId,attendanceSummaryForm.isMarkPresent());
		
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		if(studentWiseSubjectSummaryTOList!=null){
//			Iterator<StudentWiseSubjectSummaryTO> iterator1=studentWiseSubjectSummaryTOList.iterator();
//			while(iterator1.hasNext()){
//				iterator1.next();
//			}
//			if(studentWiseSubjectSummayToList1!=null){
//				Iterator<StudentWiseSubjectSummaryTO> iterator2=studentWiseSubjectSummayToList1.iterator();
//				while(iterator2.hasNext()){
//					studentWiseSubjectSummaryTOList.add(iterator2.next());
//				}
//			}
//		}
		if(studentWiseSubjectSummaryTOList==null){
			studentWiseSubjectSummaryTOList=new ArrayList<StudentWiseSubjectSummaryTO>();
		}
		if(studentWiseSubjectSummayToList1!=null){
			studentWiseSubjectSummaryTOList.addAll(studentWiseSubjectSummayToList1);
		}
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
			percentage=CommonUtil.roundToDecimal(((classesPresent/classesConducted)*100),2);
			DecimalFormat df = new DecimalFormat("#.##");
			attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
			attendanceSummaryForm.setTotalConducted(""+classesConducted);
			attendanceSummaryForm.setTotalPresent(""+classesPresent);
			attendanceSummaryForm.setTotalAbscent(""+classesAbsent);
			}
		return studentWiseSubjectSummaryTOList;*/
		
		
		//raghu
		IStudentWiseAttendanceSummaryTransaction transection= new StudentWiseAttendanceSummaryTransactionImpl();
		DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
		int classId = transection.getClassId(studentId);
		List dateList=impl.getDateList(studentId, classId);
		List sessionAttendanceList=impl.getSessionAttendanceList(studentId, classId);
		StudentAttendanceSummaryHelper.getInstance().convertBoToSessionAttendance(dateList,sessionAttendanceList,attendanceSummaryForm);
		
		
	}
	
	
	
	public void getTeacherNotTakenAttendanceSummary(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
				
		//raghu
		IStudentWiseAttendanceSummaryTransaction transection= new StudentWiseAttendanceSummaryTransactionImpl();
		int classId = transection.getClassId(studentId);
		List<Object[]> attList=transection.getAttendanceDateAndDayOnClass(classId);
		List<Object[]> dateAttendanceList=transection.getTeacherNotTakenAttendanceSummary(classId, studentId, attList);
		StudentAttendanceSummaryHelper.getInstance().getTeacherNotTakenAttendanceSummary(dateAttendanceList,attendanceSummaryForm);
		
		
	}
	
	
	public void getTeacherClassWiseAttendanceNotTakenSummary(StudentWiseAttendanceSummaryForm attendanceSummaryForm)throws Exception {
				
		//raghu
		IStudentWiseAttendanceSummaryTransaction transection= new StudentWiseAttendanceSummaryTransactionImpl();
		List<StudentAttendanceTO> list=new LinkedList<StudentAttendanceTO>();
		int classId=0;
		int userId=0;
		if(attendanceSummaryForm.getIsFaculty()){
			userId=Integer.parseInt(attendanceSummaryForm.getUserId());
		}
		if(attendanceSummaryForm.getClassesName()!= null && attendanceSummaryForm.getClassesName().length != 0) {
			for(String str :attendanceSummaryForm.getClassesName()) {
				if(str != null){
					
					classId=Integer.parseInt(str);
					
					List<Object[]> attList=transection.getAttendanceDateAndDayOnClassTeacher(classId);
					List<Object[]> dateAttendanceList=transection.getClassTeacherNotTakenAttendanceSummary(classId, userId,attList);
					
					
					if(dateAttendanceList!=null && dateAttendanceList.size()!=0){
						Iterator  subItr = dateAttendanceList.iterator();
						while (subItr.hasNext()) {
							StudentAttendanceTO to=new StudentAttendanceTO();
							Object[] obj = (Object[]) subItr.next();
							
							to.setDay(obj[0].toString());
							to.setTeacherName(obj[1].toString());
							to.setSubjectName(obj[2].toString());
							to.setPeriodName(obj[3].toString());
							to.setDate(obj[4].toString());
							to.setClassName(obj[5].toString());
							list.add(to);
						}
						
					}
					
					
					}
				}
			}
		
		
		attendanceSummaryForm.setAttDateList(list);
		
	}
	
	
	
	
	
	public float getStudentWiseSubjectsAttendancePercentage(
			int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String courseId)throws Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList=getStudentSubjectWiseAttendanceList(studentId, attendanceSummaryForm, courseId);
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
		
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummayToList1=getStudentSubjectWiseAttendanceSubjectList(studentId, attendanceSummaryForm, courseId,attendanceSummaryForm.isMarkPresent());
		
		classesConducted=classesConducted+Float.parseFloat(attendanceSummaryForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(attendanceSummaryForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(attendanceSummaryForm.getTotalPresent());
//		if(studentWiseSubjectSummaryTOList!=null){
//			Iterator<StudentWiseSubjectSummaryTO> iterator1=studentWiseSubjectSummaryTOList.iterator();
//			while(iterator1.hasNext()){
//				iterator1.next();
//			}
//			if(studentWiseSubjectSummayToList1!=null){
//				Iterator<StudentWiseSubjectSummaryTO> iterator2=studentWiseSubjectSummayToList1.iterator();
//				while(iterator2.hasNext()){
//					studentWiseSubjectSummaryTOList.add(iterator2.next());
//				}
//			}
//		}
		if(studentWiseSubjectSummaryTOList==null){
			studentWiseSubjectSummaryTOList=new ArrayList<StudentWiseSubjectSummaryTO>();
		}
		if(studentWiseSubjectSummayToList1!=null){
			studentWiseSubjectSummaryTOList.addAll(studentWiseSubjectSummayToList1);
		}
		DecimalFormat df = new DecimalFormat("#.##");
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
			percentage=CommonUtil.roundToDecimal(((classesPresent/classesConducted)*100),2);
			
			attendanceSummaryForm.setTotalPercentage(""+df.format(percentage));
			attendanceSummaryForm.setTotalConducted(""+classesConducted);
			attendanceSummaryForm.setTotalPresent(""+classesPresent);
			attendanceSummaryForm.setTotalAbscent(""+classesAbsent);
			}
		return Float.parseFloat(df.format(percentage));
	}
	
	
}
	
	
	
	
