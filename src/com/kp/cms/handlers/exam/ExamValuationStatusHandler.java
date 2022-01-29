package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.CourseSchemeDetails;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.helpers.exam.ExamValuationStatusHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.transactions.exam.IExamValuationStatusTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationStatusTxImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamValuationStatusHandler {
	/**
	 * Singleton object of NewSecuredMarksEntryHandler
	 */
	private static volatile ExamValuationStatusHandler handler = null;
	private static final Log log = LogFactory.getLog(ExamValuationStatusHandler.class);
	private ExamValuationStatusHandler() {
		
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ExamValuationStatusHandler getInstance() {
		if (handler == null) {
			handler = new ExamValuationStatusHandler();
		}
		return handler;
	}
	/**
	 * @param examValuationStatusForm
	 * @throws Exception 
	 */
	public void getValuationDetails(ExamValuationStatusForm examValuationStatusForm) throws Exception {

		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();		
		List<ExamValidationDetails> valuationList = transaction.getExamValidationList(examValuationStatusForm.getExamId());
		Map<Integer, List<ExamValidationDetails>> valuationMap = new HashMap<Integer, List<ExamValidationDetails>>(); 
		if(valuationList != null){
			Iterator<ExamValidationDetails> iterator = valuationList.iterator();
			List<ExamValidationDetails> vList = new ArrayList<ExamValidationDetails>();
			while (iterator.hasNext()) {
				ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
				if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
					if(valuationMap.containsKey(examValidationDetails.getSubject().getId())){
						vList = valuationMap.remove(examValidationDetails.getSubject().getId());
					}
					vList.add(examValidationDetails);
					valuationMap.put(examValidationDetails.getSubject().getId(), vList);
				}
			}
		}
		List<Object[]> details = transaction.getTotalVerfiedStudent(examValuationStatusForm.getExamId(),examValuationStatusForm.getTermNumber(),examValuationStatusForm.getCourseId(),examValuationStatusForm.getFinalYears(),false,0,examValuationStatusForm.getExamType());
	
		List<ExamValuationStatusTO> valuationStatusTOs = ExamValuationStatusHelper.getInstance().convertBOtoTO(details,valuationMap,examValuationStatusForm);
		examValuationStatusForm.setValuationStatus(valuationStatusTOs);
		
	}
	/**
	 * @param examValuationStatusForm
	 * @throws Exception
	 */
	public void viewValuationDetails(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		List<StudentTO> studentTos = new ArrayList<StudentTO>();
		List<Student> totalStudents = new ArrayList<Student>();
		if(examValuationStatusForm.getExamType().equalsIgnoreCase("Regular")){
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForCurrentClassStudents(examValuationStatusForm));
			List<MarksEntryDetails> students = transaction.getDetailsForView(examValuationStatusForm);
			//code added by sudhir
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			//
			Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,examValuationStatusForm.getExamType());
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsForRegular(studentTos,totalStudents,stuMap,examValuationStatusForm,listOfDetainedStudents,"current");
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForPreviousClassStudents(examValuationStatusForm));
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsForRegular(studentTos,totalStudents,stuMap,examValuationStatusForm,listOfDetainedStudents,"previous");
		}else if(!examValuationStatusForm.getExamType().equalsIgnoreCase("Supplementary")){
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForCurrentClassStudents(examValuationStatusForm));
			List<MarksEntryDetails> students = transaction.getDetailsForView(examValuationStatusForm);
			Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,examValuationStatusForm.getExamType());
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,examValuationStatusForm);
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForPreviousClassStudents(examValuationStatusForm));
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,examValuationStatusForm);
		}else{
			List<Object[]> totalStudent = transaction.getTotalStudents1(ExamValuationStatusHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(examValuationStatusForm));
			List<MarksEntryDetails> students = transaction.getDetailsForView(examValuationStatusForm);
			Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,examValuationStatusForm.getExamType());
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsForSup(studentTos,totalStudent,stuMap,examValuationStatusForm);
//			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(examValuationStatusForm));
//			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,examValuationStatusForm);
		}
		examValuationStatusForm.setStudents(studentTos);
	}
	/**
	 * @param examValuationStatusForm
	 * @throws Exception
	 */
	public void viewVerificationDetails(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		List<StudentTO> studentTos = new ArrayList<StudentTO>();
		List<Student> totalStudents = new ArrayList<Student>();
		if(examValuationStatusForm.getExamType().equalsIgnoreCase("Regular")){
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForCurrentClassStudents(examValuationStatusForm));
			//code added by sudhir
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			//
			Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(examValuationStatusForm);
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsRegular1(studentTos,totalStudents,stuMap,examValuationStatusForm,listOfDetainedStudents,"current");
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForPreviousClassStudents(examValuationStatusForm));
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsRegular1(studentTos,totalStudents,stuMap,examValuationStatusForm,listOfDetainedStudents,"previous");
		}else if(!examValuationStatusForm.getExamType().equalsIgnoreCase("Supplementary")){
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForCurrentClassStudents(examValuationStatusForm));
			Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(examValuationStatusForm);
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,examValuationStatusForm);
			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForPreviousClassStudents(examValuationStatusForm));
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,examValuationStatusForm);
		}else{
			List<Object[]> totalStudent = transaction.getTotalStudents1(ExamValuationStatusHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(examValuationStatusForm));
			Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(examValuationStatusForm);
			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsForSup1(studentTos,totalStudent,stuMap,examValuationStatusForm);
//			totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(examValuationStatusForm));
//			studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,examValuationStatusForm);
		}
		examValuationStatusForm.setStudents(studentTos);
	}
	/**
	 * @return
	 */
	public List<CourseTO> getCourseList() throws Exception{
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		List<Course> courseList = transaction.getCourseList();
		List<CourseTO> courseTos = new ArrayList<CourseTO>();
		if(courseList != null){
			Iterator<Course> iterator = courseList.iterator();
			while (iterator.hasNext()) {
				Course course = (Course) iterator.next();
				CourseTO to = new CourseTO();
				to.setId(course.getId());
				to.setName(course.getName());
				to.setCode(course.getCode());
				courseTos.add(to);
			}
		}
		return courseTos;
	}
	/**
	 * @param examValuationStatusForm
	 * @throws Exception 
	 */
	public void displayValuationDetails(ExamValuationStatusForm examValuationStatusForm) throws Exception {
		
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		Integer internalExamId = transaction.getInternalExamId(examValuationStatusForm.getExamId());
		List<ExamValidationDetails> valuationList = transaction.getExamValidationList(examValuationStatusForm.getExamId());
		Map<Integer, List<ExamValidationDetails>> valuationMap = new HashMap<Integer, List<ExamValidationDetails>>(); 
		if(valuationList != null && !valuationList.isEmpty()){
			Iterator<ExamValidationDetails> iterator = valuationList.iterator();
			List<ExamValidationDetails> vList = new ArrayList<ExamValidationDetails>();
			while (iterator.hasNext()) {
				ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
				if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
					if(valuationMap.containsKey(examValidationDetails.getSubject().getId())){
						vList = valuationMap.remove(examValidationDetails.getSubject().getId());
					}
						vList.add(examValidationDetails);
					   valuationMap.put(examValidationDetails.getSubject().getId(), vList);
				}
			}
		}
		List<Object[]> details;
		if(examValuationStatusForm.getExamType().equalsIgnoreCase("Supplementary")){
			details = transaction.getTotalVerfiedStudentForSupplementary(examValuationStatusForm.getExamId(),examValuationStatusForm.getTermNumber(),examValuationStatusForm.getCourseId(),examValuationStatusForm.getFinalYears(),false,internalExamId);
		}else{
			details = transaction.getTotalVerfiedStudent(examValuationStatusForm.getExamId(),examValuationStatusForm.getTermNumber(),examValuationStatusForm.getCourseId(),examValuationStatusForm.getFinalYears(),false,internalExamId,examValuationStatusForm.getExamType());
		}
		Map<Integer,Map<Integer, List<ExamValuationStatusTO>>> courseMap=new HashMap<Integer, Map<Integer,List<ExamValuationStatusTO>>>();
		Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap = null;
		/*code added by sudhir */
		List<Integer> courseListIds = new ArrayList<Integer>();
		List<Integer> subjectListIds = new ArrayList<Integer>();
		/*---------------------*/
		if(details != null){
			Iterator<Object[]> iterator = details.iterator();
			List<ExamValuationStatusTO> list=null;
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				int id=0;
				if(objects[1] != null && objects[1].toString() != null){
					id = Integer.parseInt(objects[1].toString());
				}
				if(objects[9] != null && !objects[9].toString().isEmpty()){
					if(courseMap.containsKey(Integer.parseInt(objects[9].toString()))){
						verifyDetailsMap=courseMap.get(Integer.parseInt(objects[9].toString()));
					}else
						verifyDetailsMap=new HashMap<Integer, List<ExamValuationStatusTO>>();
					
					if(verifyDetailsMap.containsKey(id)){
						list=verifyDetailsMap.remove(id);
					}else
						list=new ArrayList<ExamValuationStatusTO>();
					ExamValuationStatusTO to = new ExamValuationStatusTO();
					if(objects[0] != null && objects[0].toString() != null){
						to.setSubjectName(objects[0].toString());
					}
					if(objects[14] != null && objects[14].toString() != null && objects[14].toString().equals("1")){
						to.setInternalSubject("Yes");
					}
					if(objects[8] != null && objects[8].toString() != null){
						to.setSubjectCode(objects[8].toString());
					}
					if(objects[1] != null && objects[1].toString() != null){
						to.setSubjectId(Integer.parseInt(objects[1].toString()));
					}
					if(objects[7] != null && objects[7].toString() != null){
						to.setCourseCode(objects[7].toString());
					}
					if(objects[2] != null && objects[2].toString() != null){
						to.setTotalStudent(Integer.parseInt(objects[2].toString()));
					}
					if(objects[3] != null && objects[3].toString() != null){
						to.setTotalEntered(Integer.parseInt(objects[3].toString()));
					}
					if(objects[4] != null && objects[4].toString() != null){
						to.setTotalVerified(Integer.parseInt(objects[4].toString()));
					}
					if(objects[5] != null && objects[5].toString() != null){
						to.setEvaluatorTypeId(objects[5].toString());
					}
					if(objects[10] != null && objects[10].toString() != null){
						to.setTheoryMultiple(objects[10].toString());
					}
					if(objects[12] != null && objects[12].toString() != null){
						to.setIsTheory(objects[12].toString());
					}
					if(objects[13] != null && objects[13].toString() != null){
						if(objects[13].toString().equalsIgnoreCase("0")){
							to.setCertificateCourse(false);
						}else if(objects[13].toString().equalsIgnoreCase("1")){
							to.setCertificateCourse(true);
						}
					}
					list.add(to);
					verifyDetailsMap.put(id, list);
					courseMap.put(Integer.parseInt(objects[9].toString()),verifyDetailsMap);
					courseListIds.add(Integer.parseInt(objects[9].toString()));
					subjectListIds.add(id);
				}
				
			}
		}
		/* code added by sudhir */
		Map<Integer,Map<String,List<StudentTO>>> newMap = null;
		if(courseListIds!=null && !courseListIds.isEmpty()){
			/*CHECKING MISMATCH MARKS FOR STUDENTS BASED ON COURSE*/
			List<Object[]> objectsList = transaction.checkMismatchFoundForStudents(courseListIds,subjectListIds,examValuationStatusForm);
			/*IF MISMATCH FOUND,CONVERTING IT INTO MAP. PUTTING COURSEID AS KEY AND MAP<STRING,LIST<STUDENTTO> AS VALUE ,SUBJECTID AS KEY AND LIST<STUDENTTO> AS VALUE*/
			 newMap = ExamValuationStatusHelper.getInstance().convertBOListToMap(objectsList);
			 examValuationStatusForm.setMisMatchStudentList(newMap);
		}
		/*--------------------*/
		Map<Integer, String> examStartDates = transaction.getExamSubjectDatesMap(subjectListIds,examValuationStatusForm.getExamId());
		List<ExamValuationStatusTO> valuationStatusTOs = ExamValuationStatusHelper.getInstance().convertValues(valuationMap,courseMap,examValuationStatusForm,newMap,internalExamId,examStartDates);
		examValuationStatusForm.setValuationStatus(valuationStatusTOs);
		
	}
	/**
	 * @param valuationStatus
	 * @param examValuationStatusForm
	 * @throws Exception
	 */
	public boolean saveProcessCompletedDetails(List<ExamValuationStatusTO> valuationStatus,ExamValuationStatusForm examValuationStatusForm) throws Exception{
		
		List<ExamValuationProcess> boList = new ArrayList<ExamValuationProcess>();
		if(valuationStatus != null){
			Iterator<ExamValuationStatusTO> iterator = valuationStatus.iterator();
			while (iterator.hasNext()) {
				ExamValuationProcess bo = null;
				ExamValuationStatusTO examValuationStatusTO = (ExamValuationStatusTO) iterator.next();
				if(examValuationStatusTO.getTempvaluationProcess() != null && examValuationStatusTO.getTempoverallProcess() != null){
					
						bo = new ExamValuationProcess();
						bo.setCreatedBy(examValuationStatusForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(examValuationStatusForm.getUserId());
						bo.setLastModifiedDate(new Date());
						ExamDefinition exam = new ExamDefinition();
						exam.setId(Integer.parseInt(examValuationStatusForm.getExamId()));
						bo.setExam(exam);
						Course course = new Course();
						course.setId(examValuationStatusTO.getCourseId());
						bo.setCourse(course);
						Subject subject = new Subject();
						subject.setId(examValuationStatusTO.getSubjectId());
						bo.setSubject(subject);
						if(examValuationStatusTO.getTempvaluationProcess().equalsIgnoreCase("on")){
							bo.setValuationProcessCompleted(true);
						}else{
							bo.setValuationProcessCompleted(false);
						}
						if(examValuationStatusTO.getTempoverallProcess().equalsIgnoreCase("on")){
							bo.setOverallProcessCompleted(true);
						}else{
							bo.setOverallProcessCompleted(false);
						}
						bo.setIsActive(true);
					
				}else if(examValuationStatusTO.getTempvaluationProcess() != null ){
				
						bo = new ExamValuationProcess();
						bo.setCreatedBy(examValuationStatusForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(examValuationStatusForm.getUserId());
						bo.setLastModifiedDate(new Date());
						ExamDefinition exam = new ExamDefinition();
						exam.setId(Integer.parseInt(examValuationStatusForm.getExamId()));
						bo.setExam(exam);
						Course course = new Course();
						course.setId(examValuationStatusTO.getCourseId());
						bo.setCourse(course);
						Subject subject = new Subject();
						subject.setId(examValuationStatusTO.getSubjectId());
						bo.setSubject(subject);
						if(examValuationStatusTO.getTempvaluationProcess().equalsIgnoreCase("on")){
							bo.setValuationProcessCompleted(true);
						}else{
							bo.setValuationProcessCompleted(false);
						}
						bo.setIsActive(true);
				}else if(examValuationStatusTO.getTempoverallProcess() != null){
						bo = new ExamValuationProcess();
						bo.setCreatedBy(examValuationStatusForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setModifiedBy(examValuationStatusForm.getUserId());
						bo.setLastModifiedDate(new Date());
						ExamDefinition exam = new ExamDefinition();
						exam.setId(Integer.parseInt(examValuationStatusForm.getExamId()));
						bo.setExam(exam);
						Course course = new Course();
						course.setId(examValuationStatusTO.getCourseId());
						bo.setCourse(course);
						Subject subject = new Subject();
						subject.setId(examValuationStatusTO.getSubjectId());
						bo.setSubject(subject);
						if(examValuationStatusTO.getTempoverallProcess().equalsIgnoreCase("on")){
							bo.setOverallProcessCompleted(true);
						}else{
							bo.setOverallProcessCompleted(false);
						}
						bo.setIsActive(true);
				}
				if(bo != null){
					boList.add(bo);
				}
			}
		}
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		boolean save = transaction.saveProcessCompletedDetails(boList);
		return save;
	}
	
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseByExamName(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		Map<Integer, String> programmCourseMap = new HashMap<Integer, String>();
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		if(examValuationStatusForm.getExamId()!=null && !examValuationStatusForm.getExamId().isEmpty()){
		List<CourseSchemeDetails> courseSchemeDetailsList=transaction.getCourseByExamNameForStatus(Integer.parseInt(examValuationStatusForm.getExamId()));
		if(courseSchemeDetailsList!=null && !courseSchemeDetailsList.isEmpty()){
			for (CourseSchemeDetails courseSchemeDetails : courseSchemeDetailsList) {
				if(courseSchemeDetails.getCourse()!=null && courseSchemeDetails.getCourse().getId()!=0 && courseSchemeDetails.getCourse().getName()!=null 
						&& !courseSchemeDetails.getCourse().getName().isEmpty() && courseSchemeDetails.getCourse().getProgram()!=null && courseSchemeDetails.getCourse().getProgram().getProgramType()!=null
						&& !courseSchemeDetails.getCourse().getProgram().getProgramType().getName().isEmpty()){
					courseMap.put(courseSchemeDetails.getCourse().getId(), courseSchemeDetails.getCourse().getName());
					programmCourseMap.put(courseSchemeDetails.getCourse().getId(), courseSchemeDetails.getCourse().getProgram().getProgramType().getName());
				}
				
			}
		}
		}
		courseMap = (Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		examValuationStatusForm.setProgrammCourseMap(programmCourseMap);
		return courseMap;
		
	}
}
