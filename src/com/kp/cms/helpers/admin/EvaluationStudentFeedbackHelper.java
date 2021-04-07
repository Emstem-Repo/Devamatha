package com.kp.cms.helpers.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EvaluationStudentFeedback;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackAnswer;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.bo.admin.EvaluationStudentSubjectWiseFeedbackAnswer;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationQuestion;
import com.kp.cms.bo.studentfeedback.ExitEvaluationStudentFeedbackAnswer;
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackForm;
import com.kp.cms.to.admin.EvaluationStudentFeedbackTO;
import com.kp.cms.to.admin.TeacherClassSubjectTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;
import com.kp.cms.transactions.admin.IEvaluationStudentFeedbackTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.admin.EvaluationStudentFeedbackTxnImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;

public class EvaluationStudentFeedbackHelper {
	public static volatile EvaluationStudentFeedbackHelper evaluationStudentFeedbackHelper = null;
	public static EvaluationStudentFeedbackHelper getInstance(){
		if(evaluationStudentFeedbackHelper == null){
			evaluationStudentFeedbackHelper = new EvaluationStudentFeedbackHelper();
			return evaluationStudentFeedbackHelper;
		}
		return evaluationStudentFeedbackHelper;
	}
	IEvaluationStudentFeedbackTransaction transaction = EvaluationStudentFeedbackTxnImpl.getInstance();
	/**
	 * @param teacherClassSubjects
	 * @param evaStudentFeedbackForm 
	 * @param teachersListForStudent 
	 * @param mapList 
	 * @return
	 * @throws Exception
	 */
	public List<TeacherClassSubjectTO> convertBOToTO( List<TeacherClassSubject> teacherClassSubjects, EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<TeacherClassSubjectTO> tos = new ArrayList<TeacherClassSubjectTO>();
		int i = 0;
//		List<Integer> subjectList = new ArrayList<Integer>();
		if(teacherClassSubjects!=null){
			Iterator<TeacherClassSubject> iterator = teacherClassSubjects.iterator();
			while (iterator.hasNext()) {
				TeacherClassSubject teachersSubjectDetails = (TeacherClassSubject)iterator.next();
				TeacherClassSubjectTO teacherClassSubjectTO = null;
					if(teachersSubjectDetails!=null && teachersSubjectDetails.getTeacherId()!=null){
						teacherClassSubjectTO =new TeacherClassSubjectTO();
						i++;
						teacherClassSubjectTO.setId(teachersSubjectDetails.getId());
						if(teachersSubjectDetails.getTeacherId().getId()!=0){
						//  EmployeeId we are setting is a userId.
							teacherClassSubjectTO.setEmployeeId(teachersSubjectDetails.getTeacherId().getId()); 
						}
						if(teachersSubjectDetails.getTeacherId().getEmployee()!=null && !teachersSubjectDetails.getTeacherId().getEmployee().toString().isEmpty()){
							if(teachersSubjectDetails.getTeacherId().getEmployee().getFirstName()!=null && !teachersSubjectDetails.getTeacherId().getEmployee().getFirstName().isEmpty()){
								teacherClassSubjectTO.setEmployeeName(teachersSubjectDetails.getTeacherId().getEmployee().getFirstName());
							}
						}else if (teachersSubjectDetails.getTeacherId().getGuest()!=null && !teachersSubjectDetails.getTeacherId().getGuest().toString().isEmpty()) {
							if(teachersSubjectDetails.getTeacherId().getGuest().getFirstName()!=null && !teachersSubjectDetails.getTeacherId().getGuest().getFirstName().isEmpty()){
								teacherClassSubjectTO.setEmployeeName(teachersSubjectDetails.getTeacherId().getGuest().getFirstName());
							}
						}else if(teachersSubjectDetails.getTeacherId().getUserName()!=null && !teachersSubjectDetails.getTeacherId().getUserName().isEmpty()){
							teacherClassSubjectTO.setEmployeeName(teachersSubjectDetails.getTeacherId().getUserName());
						}
						if(teachersSubjectDetails.getSubject()!=null && teachersSubjectDetails.getSubject().getId()!=0){
							teacherClassSubjectTO.setSubjectId(teachersSubjectDetails.getSubject().getId());
						}
						if( teachersSubjectDetails.getSubject()!=null && !teachersSubjectDetails.getSubject().toString().isEmpty()){
							String subjectName = " ";
							String subjectCode = " ";
							if(teachersSubjectDetails.getSubject().getName()!=null && !teachersSubjectDetails.getSubject().getName().isEmpty()){
								subjectName = teachersSubjectDetails.getSubject().getName();
							}
							if(teachersSubjectDetails.getSubject().getCode()!=null && !teachersSubjectDetails.getSubject().getCode().isEmpty()){
								subjectCode = teachersSubjectDetails.getSubject().getCode();
							}
							teacherClassSubjectTO.setSubject(subjectName.concat("   ("+subjectCode+")"));
						}
						teacherClassSubjectTO.setDone(false);
						teacherClassSubjectTO.setSubjectNo(i);
						/*subjectList.add(teacherClassSubjectTO.getSubjectNo());
						teacherClassSubjectTO.setIntegers(subjectList);*/
					}
					if(teacherClassSubjectTO!=null && !teacherClassSubjectTO.toString().isEmpty()){
						tos.add(teacherClassSubjectTO);
					}
				}
			}
		evaStudentFeedbackForm.setTotalSubjects(i);
		return tos;
	}
	
	/**
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public EvaluationStudentFeedbackForm saveEvaluationFacultyDetailsToList( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		List<EvaluationStudentFeedbackTO> feedbackTOList  = evaStudentFeedbackForm.getFacultyEvaluationTo();
//		feedbackTOList = evaStudentFeedbackForm.getFacultyEvaluationTo();
		EvaluationStudentFeedbackTO feedbackTO = new EvaluationStudentFeedbackTO();
		// here we are setting id of users to the employeeId.
		feedbackTO.setEmployeeId(evaStudentFeedbackForm.getTeacherId()); 
		//
		feedbackTO.setSubjectId(Integer.parseInt(evaStudentFeedbackForm.getSubjectId()));
		if(evaStudentFeedbackForm.getRemarks()!=null && !evaStudentFeedbackForm.getRemarks().isEmpty()){
			feedbackTO.setRemarks(evaStudentFeedbackForm.getRemarks());
		}
		if(evaStudentFeedbackForm.getAdditionalInfo()!=null && !evaStudentFeedbackForm.getAdditionalInfo().isEmpty()){
			feedbackTO.setAdditionalInfo(evaStudentFeedbackForm.getAdditionalInfo());
		}
		List<EvaStudentFeedBackQuestionTo> questionListTo = evaStudentFeedbackForm.getQuestionListTo();
		// setting questionTolist to the EvaluationStudentFeedbackTO 
		feedbackTO.setQuestionTosList(questionListTo);
		if(feedbackTOList == null){
			feedbackTOList = new ArrayList<EvaluationStudentFeedbackTO>();
		}
		feedbackTOList.add(feedbackTO);
		evaStudentFeedbackForm.setFacultyEvaluationTo(feedbackTOList);
		
		return evaStudentFeedbackForm;
	}
	/**
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public EvaluationStudentFeedback populateFacultyEvaluationToTOBo( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		EvaluationStudentFeedback facultyEvaluationFeedback = new EvaluationStudentFeedback();
		Student student = new Student();
		student.setId(evaStudentFeedbackForm.getStudentId());
		facultyEvaluationFeedback.setStudent(student);
		Classes classes = new Classes();
		classes.setId(Integer.parseInt(evaStudentFeedbackForm.getClassId()));
		facultyEvaluationFeedback.setClasses(classes);
		facultyEvaluationFeedback.setCreatedBy(evaStudentFeedbackForm
				.getUserId());
		facultyEvaluationFeedback.setCreatedDate(new Date());
		facultyEvaluationFeedback.setModifiedBy(evaStudentFeedbackForm
				.getUserId());
		facultyEvaluationFeedback.setLastModifiedDate(new Date());
		facultyEvaluationFeedback.setIsActive(true);
		facultyEvaluationFeedback.setIsExitEval(true);
		
		EvaluationStudentFeedbackSession session = new EvaluationStudentFeedbackSession();
		if(evaStudentFeedbackForm.getSessionId()!= 0){
			session.setId(evaStudentFeedbackForm.getSessionId());
			facultyEvaluationFeedback.setFacultyEvaluationSession(session);
		}
		facultyEvaluationFeedback.setCancel(false);
		
		List<EvaluationStudentFeedbackTO> feedbackList = evaStudentFeedbackForm .getFacultyEvaluationTo();
		Set<EvaluationStudentFeedbackFaculty> facultiesSet = new HashSet<EvaluationStudentFeedbackFaculty>();
		if (feedbackList != null && !feedbackList.isEmpty()) {
			Iterator<EvaluationStudentFeedbackTO> iterator = feedbackList .iterator();
			while (iterator.hasNext()) {
				EvaluationStudentFeedbackTO evaluationStudentFeedbackTO = (EvaluationStudentFeedbackTO) iterator .next();
				EvaluationStudentFeedbackFaculty feedbackFaculty = new EvaluationStudentFeedbackFaculty();
				
				Users users = new Users();
				//here employeeId we are getting is a userId.
				users.setId(evaluationStudentFeedbackTO.getEmployeeId());
				//
				feedbackFaculty.setFaculty(users);
				
				Subject subject = new Subject();
				subject.setId(evaluationStudentFeedbackTO.getSubjectId());
				feedbackFaculty.setSubject(subject);
				feedbackFaculty.setEvaStuFeedback(facultyEvaluationFeedback);
				feedbackFaculty .setCreatedBy(evaStudentFeedbackForm.getUserId());
				feedbackFaculty.setCreatedDate(new Date());
				feedbackFaculty.setModifiedBy(evaStudentFeedbackForm .getUserId());
				feedbackFaculty.setLastModifiedDate(new Date());
				feedbackFaculty.setIsActive(true);
				//newly added 
				if(evaluationStudentFeedbackTO.getRemarks()!=null && !evaluationStudentFeedbackTO.getRemarks().isEmpty()){
					feedbackFaculty.setRemarks(evaluationStudentFeedbackTO.getRemarks());
				}
				if(evaluationStudentFeedbackTO.getAdditionalInfo()!=null && !evaluationStudentFeedbackTO.getAdditionalInfo().isEmpty()){
					feedbackFaculty.setAdditionalRemarks(evaluationStudentFeedbackTO.getAdditionalInfo());
				}
				/* Getting batchId by passing subjectId,classId and studentId */
				String batchId = transaction.getBatchId(facultyEvaluationFeedback.getStudent().getId(),facultyEvaluationFeedback.getClasses().getId(),feedbackFaculty.getSubject().getId());
				if(!batchId.equalsIgnoreCase("null") && !batchId.isEmpty()){
					Batch batch = new Batch();
					batch.setId(Integer.parseInt(batchId));
					feedbackFaculty.setBatch(batch);
				}
				//
				List<EvaStudentFeedBackQuestionTo> questionList = evaluationStudentFeedbackTO
						.getQuestionTosList();
				Set<EvaluationStudentFeedbackAnswer> questionSet = new HashSet<EvaluationStudentFeedbackAnswer>();
				if (questionList != null && !questionList.isEmpty()) {
					Iterator<EvaStudentFeedBackQuestionTo> iterator2 = questionList
							.iterator();
					while (iterator2.hasNext()) {
						EvaStudentFeedBackQuestionTo evaStudentFeedBackQuestionTo = (EvaStudentFeedBackQuestionTo) iterator2
								.next();
						EvaluationStudentFeedbackAnswer feedbackAnswer = new EvaluationStudentFeedbackAnswer();
						feedbackAnswer.setFeedbackFaculty(feedbackFaculty);
						EvaStudentFeedbackQuestion feedbackQuestion = new EvaStudentFeedbackQuestion();
						feedbackQuestion.setId(evaStudentFeedBackQuestionTo
								.getId());
						feedbackAnswer.setQuestionId(feedbackQuestion);
						feedbackAnswer.setAnswer(evaStudentFeedBackQuestionTo
								.getChecked());
						questionSet.add(feedbackAnswer);
					}
					feedbackFaculty.setFeedbackAnswer(questionSet);
				}
				facultiesSet.add(feedbackFaculty);
			}
		}
		facultyEvaluationFeedback.setFeedbackFaculty(facultiesSet);
		//
		List<EvaluationStudentFeedbackTO> subjectWiseFeedbackList = evaStudentFeedbackForm .getSubjectWiseEvalutionEvaluationTo();
		Set<EvaluationStudentSubjectWiseFeedbackAnswer> subjectWiseSet = new HashSet<EvaluationStudentSubjectWiseFeedbackAnswer>();
		
		if (subjectWiseFeedbackList != null && !subjectWiseFeedbackList.isEmpty()) {
			Iterator<EvaluationStudentFeedbackTO> iterator2 = subjectWiseFeedbackList.iterator();
			while (iterator2.hasNext()) {
				EvaluationStudentFeedbackTO evaluationStudentFeedbackTO = (EvaluationStudentFeedbackTO) iterator2
						.next();
				//
				List<EvaStudentFeedBackQuestionTo> questionList = evaluationStudentFeedbackTO
				.getQuestionListSubjectWiseTo();
		       Set<EvaluationStudentSubjectWiseFeedbackAnswer> subjectWiseQuestionSet = new HashSet<EvaluationStudentSubjectWiseFeedbackAnswer>();
	    	   if (questionList != null && !questionList.isEmpty()) {
		  	   Iterator<EvaStudentFeedBackQuestionTo> iterator3 = questionList
					.iterator();
		  	  
			       while (iterator3.hasNext()) {
				   EvaStudentFeedBackQuestionTo evaStudentFeedBackQuestionTo = (EvaStudentFeedBackQuestionTo) iterator3
						.next();
				   EvaluationStudentSubjectWiseFeedbackAnswer evaluationStudentSubjectWiseFeedbackAnswer = new EvaluationStudentSubjectWiseFeedbackAnswer();
				   EvaStudentFeedbackQuestion subjectFeedbackQuestion = new EvaStudentFeedbackQuestion();
				   evaluationStudentSubjectWiseFeedbackAnswer.setEvaStuFeedback(facultyEvaluationFeedback);
					Subject subject1  = new Subject();
					subject1.setId(evaluationStudentFeedbackTO
							.getSubjectId());
					evaluationStudentSubjectWiseFeedbackAnswer.setSubject(subject1);
					
				   subjectFeedbackQuestion.setId(evaStudentFeedBackQuestionTo
						.getId());
				   evaluationStudentSubjectWiseFeedbackAnswer.setQuestionId(subjectFeedbackQuestion);
				   evaluationStudentSubjectWiseFeedbackAnswer.setAnswer(evaStudentFeedBackQuestionTo
						.getChecked());
				subjectWiseQuestionSet.add(evaluationStudentSubjectWiseFeedbackAnswer);
				subjectWiseSet.add(evaluationStudentSubjectWiseFeedbackAnswer);  
			     }
			       
		      }
	    	   	
				
			}
			
		}
		facultyEvaluationFeedback.setFeedbackSubjectWiseAnswer(subjectWiseSet);
		return facultyEvaluationFeedback;
	}
	/**
	 * @param classesConductedMap
	 * @param classesPresentMap
	 * @param studentId
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToList(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap,
			int studentId, EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;	
		float totalPercentage=0;
		float totalConducted = 0;
		float totalPresent = 0;
		float totalAbscent=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();

					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					

					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdLogin(subjectString.toString());
			}
		}
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			
			if(!subjectMap.isEmpty() && subjectMap.containsKey(Integer.parseInt(conductedSubjectsummaryTO.getSubjectID()))){
				if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
					if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						typeList = new ArrayList<AttendanceTypeTO>();					
						summaryTO = new StudentWiseSubjectSummaryTO();
						
						if(conductedSubjectsummaryTO.getSubjectName()!=null){
							summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
							summaryTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						}
						//-------
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						//-----------
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(
								conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						totalPercentage=totalPercentage+percentage;
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
						summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
					}
					
					else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(0);
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
					else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
								presentSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
				}
			}
			
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		totalPercentage=(totalPresent/totalConducted * 100);
		 DecimalFormat df = new DecimalFormat("#.##");
		 String s=df.format(totalPercentage);
		 evaStudentFeedbackForm.setTotalPercentage(s);
		 evaStudentFeedbackForm.setTotalConducted(String.valueOf(totalConducted));
		 evaStudentFeedbackForm.setTotalPresent(String.valueOf(totalPresent));
		 evaStudentFeedbackForm.setTotalAbscent(String.valueOf(totalAbscent));
		return studentWiseSubjectSummaryTOList;
	}
	/**
	 * @param classesConductedMap
	 * @param classesPresentMap
	 * @param studentId
	 * @param evaStudentFeedbackForm
	 * @return
	 * @throws Exception 
	 */
	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToListAdditional(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap,
			int studentId, EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception {
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;	
		float totalPercentage=0;
		float totalConducted = 0;
		float totalPresent = 0;
		float totalAbscent=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();

					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					

					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdAdditional(subjectString.toString());
			}
		}
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			
			if(!subjectMap.isEmpty() && subjectMap.containsKey(Integer.parseInt(conductedSubjectsummaryTO.getSubjectID()))){
				if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
					if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						typeList = new ArrayList<AttendanceTypeTO>();					
						summaryTO = new StudentWiseSubjectSummaryTO();
						
						if(conductedSubjectsummaryTO.getSubjectName()!=null){
							summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
							summaryTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						}
						//-------
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						//-----------
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						attendanceTypeTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(
								conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						totalPercentage=totalPercentage+percentage;
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
						summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
					}
					
					else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(0);
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
					else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
								presentSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
				}
			}
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		totalPercentage=(totalPresent/totalConducted * 100);
		 DecimalFormat df = new DecimalFormat("#.##");
		 String s=df.format(totalPercentage);
		 evaStudentFeedbackForm.setTotalPercentage(s);
		 evaStudentFeedbackForm.setTotalConducted(String.valueOf(totalConducted));
		 evaStudentFeedbackForm.setTotalPresent(String.valueOf(totalPresent));
		 evaStudentFeedbackForm.setTotalAbscent(String.valueOf(totalAbscent));
		return studentWiseSubjectSummaryTOList;
	}
	/**
	 * @param questionListBo
	 * @param evaStudentFeedbackForm 
	 * @return
	 * @throws Exception
	 */
	public List<EvaStudentFeedBackQuestionTo> populateQuestionListBOToTO( List<EvaStudentFeedbackQuestion> questionListBo, EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		List<EvaStudentFeedBackQuestionTo> listTo = new ArrayList<EvaStudentFeedBackQuestionTo>();
		int totalQuestions = 0;
		if(questionListBo!=null && !questionListBo.isEmpty()){
			Iterator<EvaStudentFeedbackQuestion> iterator = questionListBo.iterator();
			while (iterator.hasNext()) {
				EvaStudentFeedbackQuestion questionBo = (EvaStudentFeedbackQuestion) iterator .next();
				EvaStudentFeedBackQuestionTo questionTo = new EvaStudentFeedBackQuestionTo();
				if(questionBo.getId()!=0){
					questionTo.setId(questionBo.getId());
				}
				if(questionBo.getQuestion()!=null && !questionBo.getQuestion().isEmpty()){
					questionTo.setQuestion(questionBo.getQuestion());
				}
				listTo.add(questionTo);
				totalQuestions++;
			}
			evaStudentFeedbackForm.setTotalQuestions(totalQuestions);
		}
		return listTo;
	}

	/**
	 * @param studentId
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public String getStudentAttendancePercentage(Integer studentId, Integer classId) throws Exception{
	String attendanceTotalPercentage =  " select round((((sum(if(attendance_student.is_cocurricular_leave =1 or attendance_student.is_present, attendance.hours_held, 0)))/sum(hours_held)) * 100), 2) as percent " +
										" from attendance_student " +
										" inner join attendance on attendance_student.attendance_id = attendance.id " +
										" inner join student on attendance_student.student_id = student.id " +
										" inner join attendance_class on attendance_class.attendance_id = attendance.id " +
										" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id " +
										" inner join classes ON class_schemewise.class_id = classes.id " +
										" where student.id =  " +studentId +
										" and classes.id =  " +classId +
										" and attendance.is_canceled=0 " +
										" group by classes.id order by classes.term_number";
		return attendanceTotalPercentage;
	}
	
	public Map<String,List<ExitEvaluationQuestionTo>> populateExitEvalQuestionListBOToTO( List<ExitEvaluationQuestion> questionListBo, EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		List<ExitEvaluationQuestionTo> listTo = new ArrayList<ExitEvaluationQuestionTo>();
		Map<String,List<ExitEvaluationQuestionTo>> map=new LinkedHashMap<String, List<ExitEvaluationQuestionTo>>();
		int totalQuestions = 0;
		if(questionListBo!=null && !questionListBo.isEmpty()){
			Iterator<ExitEvaluationQuestion> iterator = questionListBo.iterator();
			while (iterator.hasNext()) {
				ExitEvaluationQuestion questionBo = (ExitEvaluationQuestion) iterator .next();
				ExitEvaluationQuestionTo questionTo = new ExitEvaluationQuestionTo();
				if(questionBo.getId()!=0){
					questionTo.setId(questionBo.getId());
				}
				if(map.containsKey(questionBo.getExitEvalTopic().getName())){
					map.remove(questionBo.getExitEvalTopic().getName());
				}
				if(questionBo.getQuestion()!=null && !questionBo.getQuestion().isEmpty()){
					questionTo.setQuestion(questionBo.getQuestion());
				}
				ExitEvaluationTopicTo exitEvalTopicTo=new ExitEvaluationTopicTo();
				if(questionBo.getExitEvalTopic()!=null){
					exitEvalTopicTo.setId(questionBo.getExitEvalTopic().getId());
					exitEvalTopicTo.setName(questionBo.getExitEvalTopic().getName());
					questionTo.setExitEvalTopicTo(exitEvalTopicTo);
				}
				listTo.add(questionTo);
				totalQuestions++;
				map.put(questionBo.getExitEvalTopic().getName(), listTo);
			}
			evaStudentFeedbackForm.setTotalQuestions(totalQuestions);
		}
		return map;
	}
	
	public EvaluationStudentFeedbackForm saveExitEvaluationFacultyDetailsToList( EvaluationStudentFeedbackForm evaStudentFeedbackForm) throws Exception{
		Map<String,List<ExitEvaluationQuestionTo>> map=evaStudentFeedbackForm.getExitEvalMap();
		if(map!=null){
			Set<Entry<String, List<ExitEvaluationQuestionTo>>> entryMap=map.entrySet();
			Iterator<Entry<String, List<ExitEvaluationQuestionTo>>> itr=entryMap.iterator();
			while(itr.hasNext()){
				Entry<String, List<ExitEvaluationQuestionTo>> entry=itr.next();
				List<EvaluationStudentFeedbackTO> feedbackTOList  = evaStudentFeedbackForm.getFacultyEvaluationTo();
//				feedbackTOList = evaStudentFeedbackForm.getFacultyEvaluationTo();
				EvaluationStudentFeedbackTO feedbackTO = new EvaluationStudentFeedbackTO();
				// here we are setting id of users to the employeeId.
				feedbackTO.setEmployeeId(evaStudentFeedbackForm.getTeacherId()); 
				//
				feedbackTO.setSubjectId(Integer.parseInt(evaStudentFeedbackForm.getSubjectId()));
				if(evaStudentFeedbackForm.getRemarks()!=null && !evaStudentFeedbackForm.getRemarks().isEmpty()){
					feedbackTO.setRemarks(evaStudentFeedbackForm.getRemarks());
				}
				if(evaStudentFeedbackForm.getAdditionalInfo()!=null && !evaStudentFeedbackForm.getAdditionalInfo().isEmpty()){
					feedbackTO.setAdditionalInfo(evaStudentFeedbackForm.getAdditionalInfo());
				}
				List<EvaStudentFeedBackQuestionTo> questionListTo = evaStudentFeedbackForm.getQuestionListTo();
				// setting questionTolist to the EvaluationStudentFeedbackTO 
				feedbackTO.setQuestionTosList(questionListTo);
				if(feedbackTOList == null){
					feedbackTOList = new ArrayList<EvaluationStudentFeedbackTO>();
				}
				feedbackTOList.add(feedbackTO);
				evaStudentFeedbackForm.setFacultyEvaluationTo(feedbackTOList);
			}
		}
		
		return evaStudentFeedbackForm;
	}
	
	public EvaluationStudentFeedback populateExitEvaluationToTOBo( EvaluationStudentFeedbackForm evaStudentFeedbackForm,EvaluationStudentFeedback bo) throws Exception {
		
		bo.setIsExitEval(true);
		Map<String,List<ExitEvaluationQuestionTo>> map=evaStudentFeedbackForm.getExitEvalMap();
		if(map!=null){
			Set<Entry<String, List<ExitEvaluationQuestionTo>>> entryMap=map.entrySet();
			Iterator<Entry<String, List<ExitEvaluationQuestionTo>>> iterator2=entryMap.iterator();
			Set<ExitEvaluationStudentFeedbackAnswer> questionSet = new HashSet<ExitEvaluationStudentFeedbackAnswer>();
					while (iterator2.hasNext()) {
						Entry<String, List<ExitEvaluationQuestionTo>> entry=iterator2.next();
						List<ExitEvaluationQuestionTo> evaStudentFeedBackQuestionToList =entry.getValue();
						for(ExitEvaluationQuestionTo evaStudentFeedBackQuestionTo:evaStudentFeedBackQuestionToList){
						ExitEvaluationStudentFeedbackAnswer feedbackAnswer = new ExitEvaluationStudentFeedbackAnswer();
						feedbackAnswer.setStudentFeedBack(bo);
						ExitEvaluationQuestion feedbackQuestion = new ExitEvaluationQuestion();
						feedbackQuestion.setId(evaStudentFeedBackQuestionTo
								.getId());
						feedbackAnswer.setQuestionId(feedbackQuestion);
						ExitEvaluationTopic exitEvalTopic=new ExitEvaluationTopic();
						if(evaStudentFeedBackQuestionTo.getExitEvalTopicTo()!=null)
						exitEvalTopic.setId(evaStudentFeedBackQuestionTo.getExitEvalTopicTo().getId());
						feedbackAnswer.setTopicId(exitEvalTopic);
						feedbackAnswer.setAnswer(evaStudentFeedBackQuestionTo
								.getChecked());
						questionSet.add(feedbackAnswer);
						}
					}
					bo.setExitEvalFeedbackAnswer(questionSet);
				}
		return bo;
	}
	
	public List<ExitEvaluationQuestionTo> populateExitEvalQuestionListBOToTO1( List<ExitEvaluationQuestion> questionListBo, EvaluationStudentFeedbackForm evaStudentFeedbackForm)throws Exception {
		List<ExitEvaluationQuestionTo> listTo = new ArrayList<ExitEvaluationQuestionTo>();
		Map<String,List<ExitEvaluationQuestionTo>> map=new LinkedHashMap<String, List<ExitEvaluationQuestionTo>>();
		int totalQuestions = 0;
		if(questionListBo!=null && !questionListBo.isEmpty()){
			Iterator<ExitEvaluationQuestion> iterator = questionListBo.iterator();
			while (iterator.hasNext()) {
				ExitEvaluationQuestion questionBo = (ExitEvaluationQuestion) iterator .next();
				ExitEvaluationQuestionTo questionTo = new ExitEvaluationQuestionTo();
				if(questionBo.getId()!=0){
					questionTo.setId(questionBo.getId());
				}
				if(map.containsKey(questionBo.getExitEvalTopic().getName())){
					map.remove(questionBo.getExitEvalTopic().getName());
				}
				if(questionBo.getQuestion()!=null && !questionBo.getQuestion().isEmpty()){
					questionTo.setQuestion(questionBo.getQuestion());
				}
				ExitEvaluationTopicTo exitEvalTopicTo=new ExitEvaluationTopicTo();
				if(questionBo.getExitEvalTopic()!=null){
					exitEvalTopicTo.setId(questionBo.getExitEvalTopic().getId());
					exitEvalTopicTo.setName(questionBo.getExitEvalTopic().getName());
					questionTo.setExitEvalTopicTo(exitEvalTopicTo);
				}
				listTo.add(questionTo);
				totalQuestions++;
				//map.put(questionBo.getExitEvalTopic().getName(), listTo);
			}
			evaStudentFeedbackForm.setTotalQuestions(totalQuestions);
		}
		return listTo;
	}
public EvaluationStudentFeedback populateExitEvaluationToTOBo1( EvaluationStudentFeedbackForm evaStudentFeedbackForm,EvaluationStudentFeedback bo) throws Exception {
	EvaluationStudentFeedback facultyEvaluationFeedback = new EvaluationStudentFeedback();
	Student student = new Student();
	student.setId(evaStudentFeedbackForm.getStudentId());
	facultyEvaluationFeedback.setStudent(student);
	Classes classes = new Classes();
	classes.setId(Integer.parseInt(evaStudentFeedbackForm.getClassId()));
	facultyEvaluationFeedback.setClasses(classes);
	facultyEvaluationFeedback.setCreatedBy(evaStudentFeedbackForm
			.getUserId());
	facultyEvaluationFeedback.setCreatedDate(new Date());
	facultyEvaluationFeedback.setModifiedBy(evaStudentFeedbackForm
			.getUserId());
	facultyEvaluationFeedback.setLastModifiedDate(new Date());
	facultyEvaluationFeedback.setIsActive(true);
	facultyEvaluationFeedback.setIsExitEval(true);
	facultyEvaluationFeedback.setExitEvalRemarks(evaStudentFeedbackForm.getExitEvalRemarks());
	
	EvaluationStudentFeedbackSession session = new EvaluationStudentFeedbackSession();
	if(evaStudentFeedbackForm.getSessionId()!= 0){
		session.setId(evaStudentFeedbackForm.getSessionId());
		facultyEvaluationFeedback.setFacultyEvaluationSession(session);
	}
	facultyEvaluationFeedback.setCancel(false);
	
	List<EvaluationStudentFeedbackTO> feedbackList = evaStudentFeedbackForm .getFacultyEvaluationTo();
	Set<EvaluationStudentFeedbackFaculty> facultiesSet = new HashSet<EvaluationStudentFeedbackFaculty>();
	if (feedbackList != null && !feedbackList.isEmpty()) {
		Iterator<EvaluationStudentFeedbackTO> iterator = feedbackList .iterator();
		while (iterator.hasNext()) {
			EvaluationStudentFeedbackTO evaluationStudentFeedbackTO = (EvaluationStudentFeedbackTO) iterator .next();
			EvaluationStudentFeedbackFaculty feedbackFaculty = new EvaluationStudentFeedbackFaculty();
			
			Users users = new Users();
			//here employeeId we are getting is a userId.
			users.setId(evaluationStudentFeedbackTO.getEmployeeId());
			//
			feedbackFaculty.setFaculty(users);
			
			Subject subject = new Subject();
			subject.setId(evaluationStudentFeedbackTO.getSubjectId());
			feedbackFaculty.setSubject(subject);
			feedbackFaculty.setEvaStuFeedback(facultyEvaluationFeedback);
			feedbackFaculty .setCreatedBy(evaStudentFeedbackForm.getUserId());
			feedbackFaculty.setCreatedDate(new Date());
			feedbackFaculty.setModifiedBy(evaStudentFeedbackForm .getUserId());
			feedbackFaculty.setLastModifiedDate(new Date());
			feedbackFaculty.setIsActive(true);
			//newly added 
			if(evaluationStudentFeedbackTO.getRemarks()!=null && !evaluationStudentFeedbackTO.getRemarks().isEmpty()){
				feedbackFaculty.setRemarks(evaluationStudentFeedbackTO.getRemarks());
			}
			if(evaluationStudentFeedbackTO.getAdditionalInfo()!=null && !evaluationStudentFeedbackTO.getAdditionalInfo().isEmpty()){
				feedbackFaculty.setAdditionalRemarks(evaluationStudentFeedbackTO.getAdditionalInfo());
			}
			/* Getting batchId by passing subjectId,classId and studentId */
			String batchId = transaction.getBatchId(facultyEvaluationFeedback.getStudent().getId(),facultyEvaluationFeedback.getClasses().getId(),feedbackFaculty.getSubject().getId());
			if(!batchId.equalsIgnoreCase("null") && !batchId.isEmpty()){
				Batch batch = new Batch();
				batch.setId(Integer.parseInt(batchId));
				feedbackFaculty.setBatch(batch);
			}
			//
			List<EvaStudentFeedBackQuestionTo> questionList = evaluationStudentFeedbackTO
					.getQuestionTosList();
			Set<EvaluationStudentFeedbackAnswer> questionSet = new HashSet<EvaluationStudentFeedbackAnswer>();
			if (questionList != null && !questionList.isEmpty()) {
				Iterator<EvaStudentFeedBackQuestionTo> iterator2 = questionList
						.iterator();
				while (iterator2.hasNext()) {
					EvaStudentFeedBackQuestionTo evaStudentFeedBackQuestionTo = (EvaStudentFeedBackQuestionTo) iterator2
							.next();
					EvaluationStudentFeedbackAnswer feedbackAnswer = new EvaluationStudentFeedbackAnswer();
					feedbackAnswer.setFeedbackFaculty(feedbackFaculty);
					EvaStudentFeedbackQuestion feedbackQuestion = new EvaStudentFeedbackQuestion();
					feedbackQuestion.setId(evaStudentFeedBackQuestionTo
							.getId());
					feedbackAnswer.setQuestionId(feedbackQuestion);
					feedbackAnswer.setAnswer(evaStudentFeedBackQuestionTo
							.getChecked());
					questionSet.add(feedbackAnswer);
				}
				feedbackFaculty.setFeedbackAnswer(questionSet);
			}
			facultiesSet.add(feedbackFaculty);
		}
	}
		List<ExitEvaluationQuestionTo> list=evaStudentFeedbackForm.getExitEvalQuestionList();
		if(list!=null && !list.isEmpty()){
			Iterator<ExitEvaluationQuestionTo> iterator2=list.iterator();
			Set<ExitEvaluationStudentFeedbackAnswer> questionSet = new HashSet<ExitEvaluationStudentFeedbackAnswer>();
					while (iterator2.hasNext()) {
						ExitEvaluationQuestionTo evaStudentFeedBackQuestionTo =(ExitEvaluationQuestionTo)iterator2.next();
						ExitEvaluationStudentFeedbackAnswer feedbackAnswer = new ExitEvaluationStudentFeedbackAnswer();
						feedbackAnswer.setStudentFeedBack(facultyEvaluationFeedback);
						ExitEvaluationQuestion feedbackQuestion = new ExitEvaluationQuestion();
						feedbackQuestion.setId(evaStudentFeedBackQuestionTo
								.getId());
						feedbackAnswer.setQuestionId(feedbackQuestion);
						ExitEvaluationTopic exitEvalTopic=new ExitEvaluationTopic();
						if(evaStudentFeedBackQuestionTo.getExitEvalTopicTo()!=null)
						exitEvalTopic.setId(evaStudentFeedBackQuestionTo.getExitEvalTopicTo().getId());
						feedbackAnswer.setTopicId(exitEvalTopic);
						feedbackAnswer.setAnswer(evaStudentFeedBackQuestionTo
								.getChecked());
						feedbackAnswer.setRemark(evaStudentFeedBackQuestionTo.getRemark());
						questionSet.add(feedbackAnswer);
						
					}
					facultyEvaluationFeedback.setExitEvalFeedbackAnswer(questionSet);
				}
		facultyEvaluationFeedback.setFeedbackFaculty(facultiesSet);
		return facultyEvaluationFeedback;
	}

 public EvaluationStudentFeedbackForm saveEvaluationFacultyDetailsSubjectWiseToList(EvaluationStudentFeedbackForm evaStudentFeedbackForm) {

	List<EvaluationStudentFeedbackTO> feedbackTOList  = evaStudentFeedbackForm.getSubjectWiseEvalutionEvaluationTo();
	EvaluationStudentFeedbackTO feedbackTO = new EvaluationStudentFeedbackTO();
	feedbackTO.setSubjectId(Integer.parseInt(evaStudentFeedbackForm.getSubjectId()));
	if(evaStudentFeedbackForm.getRemarks()!=null && !evaStudentFeedbackForm.getRemarks().isEmpty()){
		feedbackTO.setRemarks(evaStudentFeedbackForm.getRemarks());
	}
	if(evaStudentFeedbackForm.getAdditionalInfo()!=null && !evaStudentFeedbackForm.getAdditionalInfo().isEmpty()){
		feedbackTO.setAdditionalInfo(evaStudentFeedbackForm.getAdditionalInfo());
	}
	List<EvaStudentFeedBackQuestionTo> questionListTo = evaStudentFeedbackForm.getQuestionListSubjectWiseTo();
	// setting questionTolist to the EvaluationStudentFeedbackTO 
	feedbackTO.setQuestionListSubjectWiseTo(questionListTo);
	if(feedbackTOList == null){
		feedbackTOList = new ArrayList<EvaluationStudentFeedbackTO>();
	}
	feedbackTOList.add(feedbackTO);
	evaStudentFeedbackForm.setSubjectWiseEvalutionEvaluationTo(feedbackTOList);
	
	return evaStudentFeedbackForm;

 }
}
