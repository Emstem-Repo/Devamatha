package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamValidationDetailsForm;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.ValuationStatisticsForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamValuationStatusTransaction;
import com.kp.cms.transactions.exam.IValuationStatisticsTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationStatusTxImpl;
import com.kp.cms.transactionsimpl.exam.ValuationStatisticsTxImpl;
import com.kp.cms.utilities.CommonUtil;

public class ValuationStatisticsHelper {
	/**
	 * Singleton object of NewSecuredMarksEntryHelper
	 */
	private static volatile ValuationStatisticsHelper helper = null;
	private static final Log log = LogFactory.getLog(ValuationStatisticsHelper.class);
	
	IValuationStatisticsTransaction transaction = ValuationStatisticsTxImpl.getInstance();
	private ValuationStatisticsHelper() {
		
	}
	
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ValuationStatisticsHelper getInstance() {
		if (helper == null) {
			helper = new ValuationStatisticsHelper();
		}
		return helper;
	}

	public Map<Integer, List<Integer>> convertListTOMap(List<MarksEntryDetails> marksEntryDetails, ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		if(marksEntryDetails != null){
			List<Integer> totalStudents = null;
			
			// map initialization for deanery wise
			Map<Integer, Map<Integer, List<Integer>>> mainMap = new HashMap<Integer, Map<Integer,List<Integer>>>();
			Map<Integer, List<Integer>> subjectsMap = null;
			List<Integer> studentsList = null;
			
			// map initialization for department wise
			Map<Integer, Map<Integer, List<Integer>>> departmentMap = new HashMap<Integer, Map<Integer,List<Integer>>>();
			Map<Integer, List<Integer>> departmentSubjectsMap = null;
			List<Integer> departmentStudentsList = null;
			
			Iterator<MarksEntryDetails> iterator = marksEntryDetails.iterator();
			while (iterator.hasNext()) {
				MarksEntryDetails bo = (MarksEntryDetails) iterator.next();
				if(bo.getSubject() != null && bo.getSubject().getId() >0){
					if(map.containsKey(bo.getSubject().getId())){
						totalStudents = map.remove(bo.getSubject().getId());
					}else{
						totalStudents = new ArrayList<Integer>();
					}
					if(bo.getMarksEntry() != null && bo.getMarksEntry().getStudent() != null)
						totalStudents.add(bo.getMarksEntry().getStudent().getId());
					map.put(bo.getSubject().getId(), totalStudents);
					
					//deanery wise map 
					if(bo.getSubject().getDepartment() != null && bo.getSubject().getDepartment().getEmployeeStreamBO() != null && bo.getSubject().getDepartment().getEmployeeStreamBO().getId() !=0){
						if(mainMap.containsKey(bo.getSubject().getDepartment().getEmployeeStreamBO().getId())){
							subjectsMap = mainMap.remove(bo.getSubject().getDepartment().getEmployeeStreamBO().getId());
						}else{
							subjectsMap = new HashMap<Integer, List<Integer>>();
						}
						if(subjectsMap.containsKey(bo.getSubject().getId())){
							studentsList = subjectsMap.remove(bo.getSubject().getId());
						}else{
							studentsList = new ArrayList<Integer>();
						}
						studentsList.add(bo.getMarksEntry().getStudent().getId());
						subjectsMap.put(bo.getSubject().getId(), studentsList);
						mainMap.put(bo.getSubject().getDepartment().getEmployeeStreamBO().getId(), subjectsMap);
					}
					//department wise map 
					if(bo.getSubject().getDepartment() != null && bo.getSubject().getDepartment() != null){
						if(departmentMap.containsKey(bo.getSubject().getDepartment().getId())){
							departmentSubjectsMap = departmentMap.remove(bo.getSubject().getDepartment().getId());
						}else{
							departmentSubjectsMap = new HashMap<Integer, List<Integer>>();
						}
						if(departmentSubjectsMap.containsKey(bo.getSubject().getId())){
							departmentStudentsList = departmentSubjectsMap.remove(bo.getSubject().getId());
						}else{
							departmentStudentsList = new ArrayList<Integer>();
						}
						departmentStudentsList.add(bo.getMarksEntry().getStudent().getId());
						departmentSubjectsMap.put(bo.getSubject().getId(), departmentStudentsList);
						departmentMap.put(bo.getSubject().getDepartment().getId(), departmentSubjectsMap);
					}
				}
			}
			
			valuationStatisticsForm.setDeaneryWiseMarksEntry(mainMap);
			valuationStatisticsForm.setDepartmentWiseMarksEntry(departmentMap);
		}
		return map;
	}
	/**
	 * @param currentClassStudents
	 * @param previousClassStudents
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<Integer>> convertDataTOMap(List currentClassStudents, List previousClassStudents) throws Exception{
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		if(currentClassStudents != null && !currentClassStudents.isEmpty()){
			Iterator<Object[]> iterator = currentClassStudents.iterator();
			List<Integer> totalStudents = null;
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj[1] != null && !obj[1].toString().trim().isEmpty()){
					if(map.containsKey(Integer.parseInt(obj[1].toString()))){
						totalStudents = map.remove(Integer.parseInt(obj[1].toString()));
					}else{
						totalStudents = new ArrayList<Integer>();
					}
					if(obj[0] != null && !obj[0].toString().trim().isEmpty())
						totalStudents.add(Integer.parseInt(obj[0].toString()));
					map.put(Integer.parseInt(obj[1].toString()), totalStudents);
				}
			}
		}
		if(previousClassStudents != null && !previousClassStudents.isEmpty()){
			Iterator<Object[]> iterator = previousClassStudents.iterator();
			List<Integer> totalStudents = null;
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj[1] != null && !obj[1].toString().trim().isEmpty()){
					if(map.containsKey(Integer.parseInt(obj[1].toString()))){
						totalStudents = map.remove(Integer.parseInt(obj[1].toString()));
					}else{
						totalStudents = new ArrayList<Integer>();
					}
					if(obj[0] != null && !obj[0].toString().trim().isEmpty())
						totalStudents.add(Integer.parseInt(obj[0].toString()));
					map.put(Integer.parseInt(obj[1].toString()), totalStudents);
				}
			}
		}
		return map;
	}

	/**
	 * @param studentSubjectsMap
	 * @param studentsForSubjects
	 * @return
	 */
	public List<Integer> getValuationCompletedSubjects(Map<Integer, List<Integer>> studentSubjectsMap,
			Map<Integer, List<Integer>> studentsForSubjects) throws Exception{
		List<Integer> valuationCompleted = new ArrayList<Integer>();
		if(studentsForSubjects != null && studentSubjectsMap != null){
			Iterator<Entry<Integer, List<Integer>>> iterator = studentsForSubjects.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, List<Integer>> entry = (Map.Entry<Integer, List<Integer>>) iterator.next();
				if(entry.getKey() != null){
					if(studentSubjectsMap.containsKey(entry.getKey())){
						if(entry.getValue().size() == studentSubjectsMap.get(entry.getKey()).size()){
							valuationCompleted.add(entry.getKey());
						}
					}
				}
				
			}
		}
		return valuationCompleted;
	}

	/**
	 * @param valuationMap
	 * @param verifyDetailsMap
	 * @param valuationStatisticsForm
	 * @param deptSubjects 
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationStatusTO> convertValues(Map<Integer, List<ExamValidationDetails>> valuationMap, Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap,
			ValuationStatisticsForm valuationStatisticsForm, List<Integer> deptSubjects) throws Exception {
		List<Object[]> absentDetails;
		if(valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
			absentDetails = transaction.getTotalVerfiedStudentForSupplementary(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),deptSubjects,true);
		}else{
			absentDetails = transaction.getTotalVerfiedStudent(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),deptSubjects,true,valuationStatisticsForm.getExamType());
		}
		Map<String, Integer> absenteesMap = getAbsenteesDetails(absentDetails);
		List<ExamValuationStatusTO> tos= new ArrayList<ExamValuationStatusTO>();
		if(verifyDetailsMap != null){
			int slNo=1;
			Iterator<Entry<Integer, List<ExamValuationStatusTO>>> iterator = verifyDetailsMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>> entry = (Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>>) iterator.next();
				ExamValuationStatusTO to = new ExamValuationStatusTO();
				to.setSlNo(slNo);
				to.setSubjectId(entry.getKey());
				to.setExamId(Integer.parseInt(valuationStatisticsForm.getExamId()));
				if(valuationStatisticsForm.getFirstIssuedDate() != null)
					to.setIssueForValuationDate(valuationStatisticsForm.getFirstIssuedDate().get(entry.getKey()));
				if(valuationStatisticsForm.getExamStartDate() != null)
					to.setExamStratDate(valuationStatisticsForm.getExamStartDate().get(entry.getKey()));
				List<ExamValuationStatusTO> examValuationStatusTOs = entry.getValue();
				if(examValuationStatusTOs != null){
					Iterator<ExamValuationStatusTO> iterator2 = examValuationStatusTOs.iterator();
					List<ExamValuationStatusTO> statusTOs = new ArrayList<ExamValuationStatusTO>();
					Boolean valuationCompleted = null;
					while (iterator2.hasNext()) {
						ExamValuationStatusTO examValuationStatusTO = (ExamValuationStatusTO) iterator2.next();
						to.setSubjectCode(examValuationStatusTO.getSubjectCode());
						to.setSubjectName(examValuationStatusTO.getSubjectName());
						to.setCourseCode(examValuationStatusTO.getCourseCode());
						to.setCourseId(examValuationStatusTO.getCourseId());
						to.setTermNumber(examValuationStatusTO.getTermNumber());
						int totalStudent=examValuationStatusTO.getTotalStudent();
						int totalEntered=examValuationStatusTO.getTotalEntered();
						int totalVerified=examValuationStatusTO.getTotalVerified();
						if(examValuationStatusTO.getEvaluatorTypeId() != null && !examValuationStatusTO.getEvaluatorTypeId().isEmpty()){
							if(absenteesMap.containsKey(String.valueOf(entry.getKey())+"_"+examValuationStatusTO.getEvaluatorTypeId())){
								totalVerified = totalVerified + absenteesMap.get(String.valueOf(entry.getKey())+"_"+examValuationStatusTO.getEvaluatorTypeId());
							}
						}else{
							if(absenteesMap.containsKey(String.valueOf(entry.getKey()))){
								totalVerified = totalVerified + absenteesMap.get(String.valueOf(entry.getKey()));
							}
						}
						if(totalStudent == totalEntered){
							examValuationStatusTO.setValuationCompleted("Yes");
							if(valuationCompleted == null)
								valuationCompleted = true;
						}else{
							valuationCompleted = false;
							examValuationStatusTO.setValuationCompleted("No");
						}
						if(totalStudent == totalEntered && totalEntered <= totalVerified){
							examValuationStatusTO.setVerificationCompleted("Yes");
						}else{
							examValuationStatusTO.setVerificationCompleted("No");
						}
						if(examValuationStatusTO.isCertificateCourse() || examValuationStatusTO.getIsTheory().equalsIgnoreCase("P")){
							examValuationStatusTO.setVerificationCompleted(examValuationStatusTO.getValuationCompleted());
						}
						statusTOs.add(examValuationStatusTO);
					}
					to.setStatusTOs(statusTOs);
					// we need to dispaly valuation end date if valuation is completed.
					if(valuationStatisticsForm.getValuationEndDate() != null && valuationCompleted != null && valuationCompleted)
						to.setValuationLastDate(valuationStatisticsForm.getValuationEndDate().get(entry.getKey()));
				}
				
				if(valuationMap.containsKey(entry.getKey())){
					List<ExamValidationDetails> details = valuationMap.get(entry.getKey());
					Iterator<ExamValidationDetails> examIterator = details.iterator();
					List<ExamValidationDetailsTO> evaluatorDetails = new ArrayList<ExamValidationDetailsTO>();
					while (examIterator.hasNext()) {
						ExamValidationDetails examValidationDetails = (ExamValidationDetails) examIterator.next();
						if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
							if(examValidationDetails.getSubject().getId() == entry.getKey()){
								if((examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getEmployee() != null && examValidationDetails.getUsers().getEmployee().getFirstName() != null) || 
										(examValidationDetails.getExamValuators() != null && examValidationDetails.getExamValuators().getName() != null)
										|| examValidationDetails.getOtherEmployee()!=null || (examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getUserName() != null)){
									ExamValidationDetailsTO examValidationDetailsTO = new ExamValidationDetailsTO();
									if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getEmployee() != null && examValidationDetails.getUsers().getEmployee().getFirstName() != null){
										examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName().toUpperCase());
									}else if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getUserName() != null){
										examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getUserName().toUpperCase());
									}
									if(examValidationDetails.getOtherEmployee() != null && !examValidationDetails.getOtherEmployee().isEmpty()){
										examValidationDetailsTO.setEmployeeName(examValidationDetails.getOtherEmployee().toUpperCase()+" - (External)");
									}
									if(examValidationDetails.getExamValuators() != null && examValidationDetails.getExamValuators().getName() != null){
										examValidationDetailsTO.setEmployeeName(examValidationDetails.getExamValuators().getName().toUpperCase()+" - (External)");
									}
									evaluatorDetails.add(examValidationDetailsTO);
								}
							}
						}
					}
					to.setEvaluatorDetails(evaluatorDetails);
				}
				tos.add(to);
				slNo++;
			}
		}
		return tos;
	}
	/**
	 * @param absentDetails
	 * @return
	 * @throws Exception
	 */
	private Map<String, Integer> getAbsenteesDetails(List<Object[]> absentDetails) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(absentDetails != null){
			Iterator<Object[]> iterator = absentDetails.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[1] != null && objects[1].toString() != null && objects[2] != null && objects[2].toString() != null){
					if(objects[5] != null && objects[5].toString() != null){
						map.put(objects[1].toString()+"_"+objects[5].toString(), Integer.parseInt(objects[2].toString()));
					}else{
						map.put(objects[1].toString(), Integer.parseInt(objects[2].toString()));
					}
				}
			}
		}
		return map;
	}
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudentsNew(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select s.id,subGroup.subject.id from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+valuationStatisticsForm.getExamId()+") " +
		" and subGroup.subject.id in(:subjects) group by s.id";
		return query;
	}
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudentsNew(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select s.id,subSet.subject.id from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0 and subSet.isActive=1 " +
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+valuationStatisticsForm.getExamId()+")" +
		" and subSet.subject.id in(:subjects) and classHis.schemeNo=subjHist.schemeNo group by s.id";
		return query;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudentsNew(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select sup.studentId,sup.subjectUtilBO.id " +
				" from ExamSupplementaryImprovementApplicationBO sup " +
				" join sup.studentUtilBO.classSchemewiseUtilBO cs " +
				" join cs.curriculumSchemeDurationUtilBO cd" +
				" where sup.subjectUtilBO.id in(:subjects)" +
				" and sup.examDefinitionBO.id=" +valuationStatisticsForm.getExamId()+
				" and (sup.isAppearedTheory=1 or sup.isAppearedPractical=1)";
				
		return query;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryPreviousClassStudentsNew(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select s.id,subSet.subject.id from Student s" +
			" join s.studentPreviousClassesHistory classHis" +
			" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
			" join s.studentSubjectGroupHistory subjHist" +
			" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
			" join s.studentSupplementaryImprovements supImp with supImp.subject.id in(:subjects)" +
			" and supImp.examDefinition.id=" +valuationStatisticsForm.getExamId()+
			" where s.admAppln.isCancelled=0 and subSet.isActive=1" +
			" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
			" from ExamDefinitionBO e" +
			" where e.id="+valuationStatisticsForm.getExamId()+") "+
			" and subSet.subject.id in(:subjects)"+
			" and (supImp.isAppearedTheory=1 or supImp.isAppearedPractical=1)";
		return query;
	}
	
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select s from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and (s.isHide=0 or s.isHide is null) and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+valuationStatisticsForm.getExamId()+") " +
		" and subGroup.subject.id=" +valuationStatisticsForm.getSubjectId()+" and s.classSchemewise.classes.termNumber="+valuationStatisticsForm.getTermNumber()+" group by s.id";
		return query;
	}
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select s from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and subSet.isActive=1 " +
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+valuationStatisticsForm.getExamId()+") and classSchemewise.classes.termNumber=" +valuationStatisticsForm.getTermNumber()+
		" and subSet.subject.id="+valuationStatisticsForm.getSubjectId()+" and classHis.schemeNo=subjHist.schemeNo group by s.id";
		return query;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		String query="select sup.studentId " +
				" from ExamSupplementaryImprovementApplicationBO sup " +
				" join sup.studentUtilBO.classSchemewiseUtilBO cs " +
				" join cs.curriculumSchemeDurationUtilBO cd" +
				" where sup.subjectUtilBO.id=" +valuationStatisticsForm.getSubjectId()+
				" and sup.examDefinitionBO.id=" +valuationStatisticsForm.getExamId()+
				" and (sup.isAppearedTheory=1 or sup.isAppearedPractical=1) and sup.schemeNo="+valuationStatisticsForm.getTermNumber();
				
		return query;
	}
	/**
	 * @param studentTos 
	 * @param totalStudents 
	 * @param students
	 * @param examValuationStatusForm 
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetails(List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer,Student> stuMap, ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!stuMap.containsKey(student.getId())){
					StudentTO to = new StudentTO();
					if(student.getRegisterNo() != null){
						to.setRegisterNo(student.getRegisterNo());
					}
					if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
						to.setClassName(student.getClassSchemewise().getClasses().getName());
					}
					if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
						to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					}
					studentTos.add(to);
				}
			}
		}
		return studentTos;
	}
	/**
	 * @param studentTos
	 * @param totalStudents
	 * @param stuMap
	 * @param examValuationStatusForm
	 * @param listOfDetainedStudents
	 * @return
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsForRegular( List<StudentTO> studentTos, List<Student> totalStudents,
			Map<Integer, Student> stuMap,  ValuationStatisticsForm valuationStatisticsForm, List<Integer> listOfDetainedStudents) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!listOfDetainedStudents.contains(student.getId())){
					if(!stuMap.containsKey(student.getId())){
						StudentTO to = new StudentTO();
						if(student.getRegisterNo() != null){
							to.setRegisterNo(student.getRegisterNo());
						}
						if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
							to.setClassName(student.getClassSchemewise().getClasses().getName());
						}
						if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
							to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
						}
						studentTos.add(to);
					}
				}
			}
		}
		return studentTos;
	}
	/**
	 * @param studentTos
	 * @param totalStudents
	 * @param stuMap
	 * @param examValuationStatusForm
	 * @param listOfDetainedStudents 
	 * @return
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsRegular1( List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer, String> stuMap,
			ValuationStatisticsForm valuationStatisticsForm, List<Integer> listOfDetainedStudents) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!listOfDetainedStudents.contains(student.getId())){
					if(!stuMap.containsKey(student.getId())){
						StudentTO to = new StudentTO();
						if(student.getRegisterNo() != null){
							to.setRegisterNo(student.getRegisterNo());
						}
						if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
							to.setClassName(student.getClassSchemewise().getClasses().getName());
						}
						if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
							to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
						}
						studentTos.add(to);
					}
				}
				
			}
		}
		return studentTos;
	}
	/**
	 * @param studentTos 
	 * @param totalStudents 
	 * @param students
	 * @param examValuationStatusForm 
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetails1(List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer,String> stuMap, ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!stuMap.containsKey(student.getId())){
					StudentTO to = new StudentTO();
					if(student.getRegisterNo() != null){
						to.setRegisterNo(student.getRegisterNo());
					}
					if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
						to.setClassName(student.getClassSchemewise().getClasses().getName());
					}
					if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
						to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					}
					studentTos.add(to);
				}
			}
		}
		return studentTos;
	}

	/**
	 * @param verifyDetailsMap
	 * @param valuationStatisticsForm
	 * @param issuedForValuation 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getValuationCompletedSubjectsNew(Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap,
			ValuationStatisticsForm valuationStatisticsForm, List<Integer> issuedForValuation) throws Exception{
		List<Integer> totalValuationCompleted = new ArrayList<Integer>();
		List<Integer> valInProgress = new ArrayList<Integer>();
		if(verifyDetailsMap != null){
			Iterator<Entry<Integer, List<ExamValuationStatusTO>>> iterator = verifyDetailsMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>> entry = (Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>>) iterator.next();
				List<ExamValuationStatusTO> examValuationStatusTOs = entry.getValue();
				Boolean valCompleted = null;
				if(examValuationStatusTOs != null){
					Iterator<ExamValuationStatusTO> iterator2 = examValuationStatusTOs.iterator();
					List<ExamValuationStatusTO> statusTOs = new ArrayList<ExamValuationStatusTO>();
					while (iterator2.hasNext()) {
						ExamValuationStatusTO examValuationStatusTO = (ExamValuationStatusTO) iterator2.next();
						int totalStudent=examValuationStatusTO.getTotalStudent();
						int totalEntered=examValuationStatusTO.getTotalEntered();
						if(examValuationStatusTO.getSubjectId()== 7){
							System.out.println("hi");
						}
						// if total entered is equal to absent it was showing wrong status as valuation not completed
						if(totalEntered != 0 /*&& totalEntered != examValuationStatusTO.getTotalAbsent()*/){
							if(totalStudent == totalEntered){
								if (valCompleted == null){
									valCompleted = true;
								}else if(!valCompleted) {
									valCompleted = false;
								}
							}else{
								valCompleted = false;
							}
						}
						statusTOs.add(examValuationStatusTO);
					}
				}
				if(valCompleted == null && issuedForValuation.contains(entry.getKey())){
					valCompleted = false;
				}
				if(valCompleted != null && valCompleted){
					totalValuationCompleted.add(entry.getKey());
				}else if(valCompleted != null){
					valInProgress.add(entry.getKey());
				}
			}
			valuationStatisticsForm.setValInProgress(valInProgress);
		}
		return totalValuationCompleted;
	}
}
