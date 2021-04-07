package com.kp.cms.handlers.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EvaluationStudentFeedback;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackForm;
import com.kp.cms.helpers.admin.EvaluationStudentFeedbackHelper;
import com.kp.cms.helpers.attendance.StudentAttendanceSummaryHelper;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.to.admin.TeacherClassSubjectTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.admin.EvaluationStudentFeedbackTxnImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.ExamCodeComparator;

public class EvaluationStudentFeedbackHandler {
	public static volatile EvaluationStudentFeedbackHandler evaluationStudentFeedbackHandler = null;
	public static EvaluationStudentFeedbackHandler getInstance(){
		if(evaluationStudentFeedbackHandler ==null){
			evaluationStudentFeedbackHandler = new EvaluationStudentFeedbackHandler();
			return evaluationStudentFeedbackHandler;
		}
		return evaluationStudentFeedbackHandler;
	}
	IEvaluationStudentFeedbackTransaction transaction = EvaluationStudentFeedbackTxnImpl.getInstance();
	/**
	 * @throws Exception
	 */
	public void getStuFeedbackInstructions( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<StudentFeedbackInstructionsTO> instructionsList = StudentFeedbackInstructionsHandler.getInstance().getInstructions();
		evaStudentFeedbackForm.setInstructionsList(instructionsList);
	}
	/**
	 * @param userId
	 * @return
	 */
	public List<TeacherClassSubjectTO> getDetails(EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
//		StudentLogin stuLogin = transaction.getStudentLoginDetails(evaStudentFeedbackForm.getUserId());
		List<Integer> subjectIds = transaction.getSubjectIds(evaStudentFeedbackForm.getAdmApplnId());
		List<TeacherClassSubject> teacherClassSubjects = transaction.getTeacherClassSubjects(subjectIds,evaStudentFeedbackForm.getClassSchemewiseId());
		List<TeacherClassSubjectTO> evaFacultyList = EvaluationStudentFeedbackHelper.getInstance().convertBOToTO(teacherClassSubjects,evaStudentFeedbackForm);
		return evaFacultyList;
	}
	/**
	 * @param evaStudentFeedbackForm
	 * @return
	 */
	public void getTeacherNameAndSubjectName( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<TeacherClassSubjectTO> list = evaStudentFeedbackForm.getTeacherClassSubjectToList();
		if(list != null && !list.isEmpty()){
			Iterator<TeacherClassSubjectTO> iterator = list.iterator();
//			int totalSubjects = 0;
			while (iterator.hasNext()) {
				TeacherClassSubjectTO teacherClassSubjectTO = (TeacherClassSubjectTO) iterator .next();
				if(!teacherClassSubjectTO.isDone()){
					list.remove(teacherClassSubjectTO);
					evaStudentFeedbackForm.setTeacherName(teacherClassSubjectTO.getEmployeeName());
					// EmployeeId we are getting and setting to TeacherId is nothing but UserId from Users.
					evaStudentFeedbackForm.setTeacherId(teacherClassSubjectTO.getEmployeeId());  
					//
					evaStudentFeedbackForm.setSubjectName(teacherClassSubjectTO.getSubject());
					evaStudentFeedbackForm.setSubjectId(String.valueOf(teacherClassSubjectTO.getSubjectId()));
					evaStudentFeedbackForm.setSubjectNo(teacherClassSubjectTO.getSubjectNo());
					/*List<Integer> totsubjs= teacherClassSubjectTO.getIntegers();
					Iterator<Integer> iterator2 = totsubjs.iterator();
					while (iterator2.hasNext()) {
						Integer no = (Integer) iterator2.next();
						totalSubjects = no;
					}*/
					teacherClassSubjectTO.setDone(true);
					list.add(teacherClassSubjectTO);
					evaStudentFeedbackForm.setTeacherClassSubjectToList(list);
//					evaStudentFeedbackForm.setTotalSubjects(totalSubjects);
					break;
				}
			}
		}
	}
	/**
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception 
	 */
	public EvaluationStudentFeedbackForm saveEvaluationFacultyDetailsToList(EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		evaStudentFeedbackForm = EvaluationStudentFeedbackHelper.getInstance().saveEvaluationFacultyDetailsToList(evaStudentFeedbackForm);
		return evaStudentFeedbackForm;
	}
	/**
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public boolean submitEvaluationFacultyList( EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		EvaluationStudentFeedback facultyEvaluationFeedback = EvaluationStudentFeedbackHelper.getInstance().populateFacultyEvaluationToTOBo(evaStudentFeedbackForm);
		boolean isAdded = transaction.saveFacultyEvaluationFeedback(facultyEvaluationFeedback);
		return isAdded;
	}
	/**
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkStudentIsAlreadyExist( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		/*StudentLogin stuLogin = transaction.getStudentLoginDetails(evaStudentFeedbackForm.getUserId());
		if(stuLogin!=null && !stuLogin.toString().isEmpty()){
			if(stuLogin.getStudent()!=null && !stuLogin.getStudent().toString().isEmpty()){
				evaStudentFeedbackForm.setStudentId(stuLogin.getStudent().getId());
				if(stuLogin.getStudent().getClassSchemewise()!=null && !stuLogin.getStudent().getClassSchemewise().toString().isEmpty()){
					if(stuLogin.getStudent().getClassSchemewise().getClasses()!=null && !stuLogin.getStudent().getClassSchemewise().getClasses().toString().isEmpty()){
						evaStudentFeedbackForm.setClassId(String.valueOf(stuLogin.getStudent().getClassSchemewise().getClasses().getId()));
					}
				}
			}
		}*/
		boolean isExist =transaction.checkStuIsAlreadyExist(evaStudentFeedbackForm);
		return isExist;
	}
	/**
	 * @param integer
	 * @param evaStudentFeedbackForm
	 * @param courseId
	 * @throws Exception
	 */
	public void calculateStudentAttendance( Integer studentId, EvaluationStudentFeedbackForm evaStudentFeedbackForm, String courseId) 
					throws Exception{
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList=getStudentSubjectWiseAttendanceList(studentId, evaStudentFeedbackForm, courseId);
		classesConducted=classesConducted+Float.parseFloat(evaStudentFeedbackForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(evaStudentFeedbackForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(evaStudentFeedbackForm.getTotalPresent());
		
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummayToList1=getStudentSubjectWiseAttendanceSubjectList(studentId, evaStudentFeedbackForm, courseId);
		
		classesConducted=classesConducted+Float.parseFloat(evaStudentFeedbackForm.getTotalConducted());
		classesAbsent=classesAbsent+Float.parseFloat(evaStudentFeedbackForm.getTotalAbscent());
		classesPresent=classesPresent+Float.parseFloat(evaStudentFeedbackForm.getTotalPresent());
		
		if(studentWiseSubjectSummaryTOList==null){
			studentWiseSubjectSummaryTOList=new ArrayList<StudentWiseSubjectSummaryTO>();
		}
		if(studentWiseSubjectSummayToList1!=null){
			studentWiseSubjectSummaryTOList.addAll(studentWiseSubjectSummayToList1);
		}
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
			percentage=CommonUtil.roundToDecimal(((classesPresent/classesConducted)*100),2);
			DecimalFormat df = new DecimalFormat("#.##");
			evaStudentFeedbackForm.setTotalPercentage(""+df.format(percentage));
			evaStudentFeedbackForm.setTotalConducted(""+classesConducted);
			evaStudentFeedbackForm.setTotalPresent(""+classesPresent);
			evaStudentFeedbackForm.setTotalAbscent(""+classesAbsent);
			}
	}
	/**
	 * @param studentId
	 * @param evaStudentFeedbackForm
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> getStudentSubjectWiseAttendanceList(
			int studentId, EvaluationStudentFeedbackForm evaStudentFeedbackForm, String courseId)throws Exception {
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
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
		
		studentWiseSubjectSummaryTOList = EvaluationStudentFeedbackHelper.getInstance().convertFromAttendanceSummaryMapToList(classesConductedMap,
						classesPresentMap,studentId,evaStudentFeedbackForm);
		
		return studentWiseSubjectSummaryTOList;
		
	}
	/**
	 * @param studentId
	 * @param evaStudentFeedbackForm
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> getStudentSubjectWiseAttendanceSubjectList(
			int studentId, EvaluationStudentFeedbackForm evaStudentFeedbackForm, String courseId)throws Exception {
		float classesConducted=0;
		float classesPresent=0;
		float classesAbsent=0;
		float percentage=0.0f;
		int activityCount=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
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
		
		studentWiseSubjectSummaryTOList = EvaluationStudentFeedbackHelper
		.getInstance().convertFromAttendanceSummaryMapToListAdditional(classesConductedMap,
				classesPresentMap,studentId,evaStudentFeedbackForm);
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
			 }
		
			studentWiseSubjectSummaryWithMarkTOList.add(studentWiseSubjectSummaryTO);
		}
		/*if(isSortRequired){
			Collections.sort(studentWiseSubjectSummaryWithMarkTOList, new AttSubjectCodeComparator());
		}*/
		//Activity attendance
		List<StudentWiseSubjectSummaryTO> studentActivitywiseAttendanceToList=getActivityWiseAttendanceList(studentId);
		if(studentActivitywiseAttendanceToList!=null){
		Iterator<StudentWiseSubjectSummaryTO> studentActivityItr=studentActivitywiseAttendanceToList.iterator();
		classesConducted=classesConducted+Float.parseFloat(evaStudentFeedbackForm.getTotalConducted());
		classesPresent=classesPresent+Float.parseFloat(evaStudentFeedbackForm.getTotalPresent());
		classesAbsent=classesAbsent+Float.parseFloat(evaStudentFeedbackForm.getTotalAbscent());
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
		if(classesPresent!=0 || classesAbsent!=0 || classesConducted!=0){
		percentage=(classesPresent/classesConducted)*100;
		DecimalFormat df = new DecimalFormat("#.##");
		evaStudentFeedbackForm.setTotalPercentage(""+df.format(percentage));
		evaStudentFeedbackForm.setTotalConducted(""+classesConducted);
		evaStudentFeedbackForm.setTotalPresent(""+classesPresent);
		evaStudentFeedbackForm.setTotalAbscent(""+classesAbsent);

		}
		return studentWiseSubjectSummaryWithMarkTOList;
		
	}
	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	private List<StudentWiseSubjectSummaryTO> getActivityWiseAttendanceList(
			int studentId) throws Exception {
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
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public List<EvaStudentFeedBackQuestionTo> getQuestionList( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<EvaStudentFeedbackQuestion> questionListBo = transaction.getfacultyEvalQuestionList();
		List<EvaStudentFeedBackQuestionTo> questionListTo = EvaluationStudentFeedbackHelper.getInstance().populateQuestionListBOToTO(questionListBo,evaStudentFeedbackForm);
		return questionListTo;
	}
	/**
	 * @param studentId
	 * @param classId
	 * @param evaStudentFeedbackForm 
	 * @throws Exception
	 */
	public void getStudentTotalAttendancePercentage(Integer studentId, Integer classId, EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		String attendanceQuery = EvaluationStudentFeedbackHelper.getInstance().getStudentAttendancePercentage(studentId,classId);
		String totalAttendancePercentage = transaction.getAttendancePercentage(attendanceQuery);
		evaStudentFeedbackForm.setTotalPercentage(totalAttendancePercentage);
	}
	public void getProgramTypeId(EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		int programTypeId=transaction.getprogramtypeId(evaStudentFeedbackForm);
		evaStudentFeedbackForm.setProgramTypeId(String.valueOf(programTypeId));
	}
	public Map<String,List<ExitEvaluationQuestionTo>> getExitEvalQuestionMap( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<ExitEvaluationQuestion> questionListBo = transaction.getExitEvalQuestionList();
		Map<String,List<ExitEvaluationQuestionTo>> questionMapTo = EvaluationStudentFeedbackHelper.getInstance().populateExitEvalQuestionListBOToTO(questionListBo,evaStudentFeedbackForm);
		return questionMapTo;
	}
	
	public boolean checkExitEvalIsAlreadyExist( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		boolean isExist =transaction.checkExitEvalIsAlreadyExist(evaStudentFeedbackForm);
		return isExist;
	}
	
	public boolean submitExitEvaluationList( EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		//EvaluationStudentFeedback evalStudentFeedBack=transaction.getEvalFeedBack(evaStudentFeedbackForm);
		EvaluationStudentFeedback evalStudentFeedBack=null;
		//Temporarily commented for avoiding quesetion group.
		//EvaluationStudentFeedback facultyEvaluationFeedback = EvaluationStudentFeedbackHelper.getInstance().populateExitEvaluationToTOBo(evaStudentFeedbackForm,evalStudentFeedBack);
		EvaluationStudentFeedback facultyEvaluationFeedback = EvaluationStudentFeedbackHelper.getInstance().populateExitEvaluationToTOBo1(evaStudentFeedbackForm,evalStudentFeedBack);
		boolean isAdded = transaction.saveFacultyEvaluationFeedback(facultyEvaluationFeedback);
		return isAdded;
	}
	public List<ExitEvaluationQuestionTo> getExitEvalQuestionList( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<ExitEvaluationQuestion> questionListBo = transaction.getExitEvalQuestionList();
		List<ExitEvaluationQuestionTo> questionListTo = EvaluationStudentFeedbackHelper.getInstance().populateExitEvalQuestionListBOToTO1(questionListBo,evaStudentFeedbackForm);
		return questionListTo;
	}
	public void getSubjectName(EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{

		List<TeacherClassSubjectTO> list = evaStudentFeedbackForm.getStudentFeedbackSubjectWise();
		if(list != null && !list.isEmpty()){
			Iterator<TeacherClassSubjectTO> iterator = list.iterator();
			while (iterator.hasNext()) {
				TeacherClassSubjectTO teacherClassSubjectTO = (TeacherClassSubjectTO) iterator .next();
				if(!teacherClassSubjectTO.isDone()){
					list.remove(teacherClassSubjectTO);
					evaStudentFeedbackForm.setSubjectName(teacherClassSubjectTO.getSubject());
					evaStudentFeedbackForm.setSubjectId(String.valueOf(teacherClassSubjectTO.getSubjectId()));
					evaStudentFeedbackForm.setSubjectNo(teacherClassSubjectTO.getSubjectNo());
					teacherClassSubjectTO.setDone(true);
					list.add(teacherClassSubjectTO);
					evaStudentFeedbackForm.setStudentFeedbackSubjectWise(list);
					break;
				}
			}
		}
	
		
	}
	public List<EvaStudentFeedBackQuestionTo> getQuestionListForSubjectEvalution(EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {

		List<EvaStudentFeedbackQuestion> questionListForSubjectWiseBo = transaction.getSubjectWiseEvalQuestionList();
		List<EvaStudentFeedBackQuestionTo> questionListsSubjectEvalTo = EvaluationStudentFeedbackHelper.getInstance().populateQuestionListBOToTO(questionListForSubjectWiseBo,evaStudentFeedbackForm);
		return questionListsSubjectEvalTo;
	
	}
	public EvaluationStudentFeedbackForm saveEvaluationFacultyDetailsSubjectWiseToList(EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		evaStudentFeedbackForm = EvaluationStudentFeedbackHelper.getInstance().saveEvaluationFacultyDetailsSubjectWiseToList(evaStudentFeedbackForm);
		return evaStudentFeedbackForm;
	}
}
