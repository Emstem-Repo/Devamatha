package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.transactions.exam.IExamValuationStatusTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationStatusTxImpl;

public class ExamValuationStatusHelper {
	/**
	 * Singleton object of NewSecuredMarksEntryHelper
	 */
	private static volatile ExamValuationStatusHelper newSecuredMarksEntryHelper = null;
	private static final Log log = LogFactory.getLog(ExamValuationStatusHelper.class);
	
	private ExamValuationStatusHelper() {
		
	}
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ExamValuationStatusHelper getInstance() {
		if (newSecuredMarksEntryHelper == null) {
			newSecuredMarksEntryHelper = new ExamValuationStatusHelper();
		}
		return newSecuredMarksEntryHelper;
	}
	/**
	 * @param examValuationStatusForm
	 * @param details 
	 * @throws Exception
	 */
	public void setDetailsToForm(ExamValuationStatusForm examValuationStatusForm, List<Object[]> details) throws Exception{
		
		if(details != null){
			Iterator<Object[]> iterator = details.iterator();
			List<ExamValuationStatusTO> tos = new ArrayList<ExamValuationStatusTO>();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				ExamValuationStatusTO to = new ExamValuationStatusTO();
				if(objects[0] != null && objects[0].toString() != null){
					to.setSubjectCode(objects[0].toString());
				}
				if(objects[1] != null && objects[1].toString() != null){
					to.setSubjectName(objects[1].toString());
				}
				if(objects[2] != null && objects[2].toString() != null){
					to.setCourseName(objects[2].toString());
				}
				if(objects[3] != null && objects[3].toString() != null){
					to.setExamName(objects[3].toString());
				}
				if(objects[4] != null && objects[4].toString() != null){
					to.setEmployeeName(objects[4].toString());
				}
				if(objects[5] != null && objects[5].toString() != null){
					to.setEmployeeName(objects[5].toString());
				}
				if(objects[6] != null && objects[6].toString() != null){
					to.setIssueDate(objects[6].toString());
				}
				tos.add(to);
			}
			examValuationStatusForm.setValuationStatus(tos);
		}
	}
	/**
	 * @param subjectList
	 * @param valuationMap
	 * @param verifyDetailsMap 
	 * @param examValuationStatusForm 
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationStatusTO> convertBOtoTO(List<Object[]> details , Map<Integer, List<ExamValidationDetails>> valuationMap,ExamValuationStatusForm examValuationStatusForm) throws Exception{
		List<ExamValuationStatusTO> tos= new ArrayList<ExamValuationStatusTO>();
		if(details != null){
			Iterator<Object[]> iterator = details.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				ExamValuationStatusTO to = new ExamValuationStatusTO();
				if(objects[7] != null && objects[7].toString() != null){
					to.setCourseCode(objects[7].toString());
				}
				if(objects[0] != null && objects[0].toString() != null){
					to.setSubjectName(objects[0].toString());
				}
				if(objects[8] != null && objects[8].toString() != null){
					to.setSubjectCode(objects[8].toString());
				}
				int totalStudent=0;
				int totalEntered=0;
				int totalVerified=0;
				
				if(objects[2] != null && objects[2].toString() != null){
					totalStudent= Integer.parseInt(objects[2].toString());
				}
				if(objects[3] != null && objects[3].toString() != null){
					totalEntered = Integer.parseInt(objects[3].toString());
				}
				if(objects[4] != null && objects[4].toString() != null){
					totalVerified = Integer.parseInt(objects[4].toString());
				}
				
				if(totalStudent == totalEntered){
					to.setValuationCompleted("Yes");
				}else{
					to.setValuationCompleted("No");
				}
				if(totalStudent == totalEntered && totalEntered == totalVerified){
					to.setVerificationCompleted("Yes");
				}else{
					to.setVerificationCompleted("No");
				}
				
				
				if(objects[5] != null && objects[5].toString() != null){
					to.setEvaluatorTypeId(objects[5].toString());
				}
				
				int subjectId = 0;
				if(objects[1] != null && objects[1].toString() != null){
					subjectId=Integer.parseInt(objects[1].toString());
				}
				if(valuationMap.containsKey(subjectId)){
					List<ExamValidationDetails> valuatorDetails = valuationMap.get(subjectId);
					Iterator<ExamValidationDetails> examIterator = valuatorDetails.iterator();
					List<ExamValidationDetailsTO> evaluatorDetails = new ArrayList<ExamValidationDetailsTO>();
					while (examIterator.hasNext()) {
						ExamValidationDetails examValidationDetails = (ExamValidationDetails) examIterator.next();
						if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
							if(examValidationDetails.getSubject().getId() == subjectId){
								ExamValidationDetailsTO examValidationDetailsTO = new ExamValidationDetailsTO();
								if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getEmployee() != null && examValidationDetails.getUsers().getEmployee().getFirstName() != null){
									examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName());
								}else if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getUserName() != null){
									examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getUserName());
								}
								if(examValidationDetails.getOtherEmployee() != null){
									examValidationDetailsTO.setEmployeeName(examValidationDetails.getOtherEmployee());
								}
								evaluatorDetails.add(examValidationDetailsTO);
							}
						}
					}
					to.setEvaluatorDetails(evaluatorDetails);
				}
			}
		}
		
		return tos;
	}
	/**
	 * @param studentTos 
	 * @param totalStudents 
	 * @param students
	 * @param examValuationStatusForm 
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetails(List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer,Student> stuMap, ExamValuationStatusForm examValuationStatusForm) throws Exception{
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
	 * @param students
	 * @param examValuationStatusForm 
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetails1(List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer,String> stuMap, ExamValuationStatusForm examValuationStatusForm) throws Exception{
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
	 * @param valuationMap
	 * @param verifyDetailsMap 
	 * @param examValuationStatusForm 
	 * @param newMap 
	 * @param internalExamId 
	 * @param examStartDates 
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public List<ExamValuationStatusTO> convertValues(Map<Integer, List<ExamValidationDetails>> valuationMap,Map<Integer,Map<Integer, List<ExamValuationStatusTO>>> courseMap,ExamValuationStatusForm examValuationStatusForm, Map<Integer, Map<String, List<StudentTO>>> newMap, Integer internalExamId, Map<Integer, String> examStartDates) throws Exception {
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		List<ExamValuationProcess> valuationProcesses = transaction.getProcessCompletedDetails(Integer.parseInt(examValuationStatusForm.getExamId()));
		List<Object[]> absentDetails;
		if(examValuationStatusForm.getExamType().equalsIgnoreCase("Supplementary")){
			absentDetails = transaction.getTotalVerfiedStudentForSupplementary(examValuationStatusForm.getExamId(),examValuationStatusForm.getTermNumber(),examValuationStatusForm.getCourseId(),examValuationStatusForm.getFinalYears(),true,internalExamId);
		}else{
			absentDetails = transaction.getTotalVerfiedStudent(examValuationStatusForm.getExamId(),examValuationStatusForm.getTermNumber(),examValuationStatusForm.getCourseId(),examValuationStatusForm.getFinalYears(),true,internalExamId,examValuationStatusForm.getExamType());
		}
		Map<Integer, Map<Integer, Integer>> absenteesMap = getAbsenteesDetails(absentDetails);
		List<ExamValuationStatusTO> tos= new ArrayList<ExamValuationStatusTO>();
		List<ExamValuationStatusTO> cerificatetos= new ArrayList<ExamValuationStatusTO>();
		if(courseMap!=null && !courseMap.isEmpty()){
			Iterator<Entry<Integer, Map<Integer, List<ExamValuationStatusTO>>>> itr=courseMap.entrySet().iterator();
			int slNo=1;
			while (itr.hasNext()) {
				Map.Entry<Integer, Map<Integer,List<ExamValuationStatusTO>>> entry1 =itr .next();
				Map<Integer,List<ExamValuationStatusTO>> verifyDetailsMap=entry1.getValue();
				if(verifyDetailsMap != null){
					Iterator<Entry<Integer, List<ExamValuationStatusTO>>> iterator = verifyDetailsMap.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>> entry = (Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>>) iterator.next();
						ExamValuationStatusTO to = new ExamValuationStatusTO();
						to.setSlNo(slNo);
						to.setSubjectId(entry.getKey());
						List<ExamValuationStatusTO> examValuationStatusTOs = entry.getValue();
						if(examValuationStatusTOs != null){
							Iterator<ExamValuationStatusTO> iterator2 = examValuationStatusTOs.iterator();
							List<ExamValuationStatusTO> statusTOs = new ArrayList<ExamValuationStatusTO>();
							while (iterator2.hasNext()) {
								ExamValuationStatusTO examValuationStatusTO = (ExamValuationStatusTO) iterator2.next();
								if(valuationProcesses != null){
									Iterator<ExamValuationProcess> it = valuationProcesses.iterator();
									while (it.hasNext()) {
										ExamValuationProcess process = (ExamValuationProcess) it.next();
										if(process.getExam() != null && process.getExam().getId() == Integer.parseInt(examValuationStatusForm.getExamId())){
											if(process.getCourse() != null && process.getCourse().getId() != 0){
												if(process.getCourse().getId() == entry1.getKey()){
													if(process.getSubject() != null && process.getSubject().getId() != 0){
														if(process.getSubject().getId() == examValuationStatusTO.getSubjectId()){
															if(process.getValuationProcessCompleted() != null && process.getValuationProcessCompleted()){
																to.setValuationProcess("on");
															}
															if(process.getOverallProcessCompleted() != null && process.getOverallProcessCompleted()){
																to.setOverallProcess("on");
															}
														}
													}
												}
											}
										}
									}
								}
								if(examValuationStatusTO.getInternalSubject() != null && internalExamId != null && examValuationStatusTO.getInternalSubject().equalsIgnoreCase("Yes"))
									to.setExamId(internalExamId);
								else
									to.setExamId(Integer.parseInt(examValuationStatusForm.getExamId()));
								to.setSubjectCode(examValuationStatusTO.getSubjectCode());
								to.setSubjectName(examValuationStatusTO.getSubjectName());
								to.setCourseCode(examValuationStatusTO.getCourseCode());
								to.setInternalSubject(examValuationStatusTO.getInternalSubject());
								to.setCourseId(entry1.getKey());
								to.setCertificateCourse(examValuationStatusTO.isCertificateCourse());
								to.setExamStratDate(examStartDates.get(entry.getKey()));
								/*code added by sudhir*/
								//String programType_Name = transaction.getProgramTypeByCourseId(to.getCourseId());
								//to.setProgramType(programType_Name);
								/*------------------- */
								//start code added by mehaboob
								String programType_Name = examValuationStatusForm.getProgrammCourseMap().get(to.getCourseId());
								to.setProgramType(programType_Name);
								//end code by mehaboob
								int totalStudent=examValuationStatusTO.getTotalStudent();
								int totalEntered=examValuationStatusTO.getTotalEntered();
								int totalVerified=examValuationStatusTO.getTotalVerified();
								if(absenteesMap.containsKey(entry1.getKey())){
									Map<Integer,Integer> subMap = absenteesMap.get(entry1.getKey());
									if(subMap != null && subMap.containsKey(entry.getKey())){
										totalVerified = totalVerified + subMap.get(entry.getKey());
									}
								}
								if(totalStudent == totalEntered){
									examValuationStatusTO.setValuationCompleted("Yes");
								}else{
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
								if(examValuationStatusTO.getInternalSubject() != null && internalExamId != null && examValuationStatusTO.getInternalSubject().equalsIgnoreCase("Yes")){
									examValuationStatusTO.setVerificationCompleted(examValuationStatusTO.getValuationCompleted());
								}
								/* checking whether the student marks are mismatch or not */
								/* code added by sudhir*/
						 
								String subjectId_EvaluatorType = null;
								if(examValuationStatusTO.getEvaluatorTypeId()!=null && !examValuationStatusTO.getEvaluatorTypeId().isEmpty()){
									subjectId_EvaluatorType = String.valueOf(to.getSubjectId())+"_"+examValuationStatusTO.getEvaluatorTypeId();
								}else{
									subjectId_EvaluatorType = String.valueOf(to.getSubjectId());
								}
								/*if map contains the courseid getting the value */
								Map<String,List<StudentTO>> subMap= newMap.get(entry1.getKey());
								if(subMap!=null && !subMap.isEmpty()){
									/*if map contains the subjectid and evaluatorid ,getting the map.If it is not null setting YES to mismatchfound propery */
									List<StudentTO> studentTOs = subMap.get(subjectId_EvaluatorType);
									if(studentTOs!=null && !studentTOs.isEmpty()){
										examValuationStatusTO.setMisMatchFound("Yes");
									}else{
										examValuationStatusTO.setMisMatchFound("No");
									}
								}else{
									examValuationStatusTO.setMisMatchFound("No");
								}
								
								/*--------------------------------------------------*/
								statusTOs.add(examValuationStatusTO);
							}
							to.setStatusTOs(statusTOs);
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
//											if(examValidationDetails.getDate() != null){
//												examValidationDetailsTO.setIssueDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(examValidationDetails.getDate()), ExamValuationStatusHelper.SQL_DATEFORMAT,ExamValuationStatusHelper.FROM_DATEFORMAT));
//											}
											if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getEmployee() != null && examValidationDetails.getUsers().getEmployee().getFirstName() != null){
												examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName());
											}else if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getUserName() != null){
												examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getUserName());
											}
											if(examValidationDetails.getOtherEmployee() != null && !examValidationDetails.getOtherEmployee().isEmpty()){
												examValidationDetailsTO.setEmployeeName(examValidationDetails.getOtherEmployee());
											}
											if(examValidationDetails.getExamValuators() != null && examValidationDetails.getExamValuators().getName() != null){
												examValidationDetailsTO.setEmployeeName(examValidationDetails.getExamValuators().getName());
											}
											evaluatorDetails.add(examValidationDetailsTO);
										}
									}
								}
							}
							to.setEvaluatorDetails(evaluatorDetails);
						}
						if(!to.isCertificateCourse()){
							tos.add(to);
						}else{
							cerificatetos.add(to);
						}
						slNo++;
					}
				}
			}
		}
		tos.addAll(cerificatetos);
		return tos;
	}
	private Map<Integer, Map<Integer, Integer>> getAbsenteesDetails(List<Object[]> absentDetails) throws Exception{
		Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer,Integer>>();
		if(absentDetails != null){
			Iterator<Object[]> iterator = absentDetails.iterator();
			Map<Integer, Integer> subMap =null;
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[9] != null && !objects[9].toString().isEmpty()){
					if(map.containsKey(Integer.parseInt(objects[9].toString()))){
						subMap = map.get(Integer.parseInt(objects[9].toString()));
					}else{
						subMap = new HashMap<Integer, Integer>();
					}
					if(objects[1] != null && objects[1].toString() != null && objects[2] != null && objects[2].toString() != null){
						subMap.put(Integer.parseInt(objects[1].toString()), Integer.parseInt(objects[2].toString()));
					}
					map.put(Integer.parseInt(objects[9].toString()), subMap);
				}
			}
		}
		return map;
	}
	/**
	 * @param valuationMap
	 * @param detailsMap
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationStatusTO> convertValuesTOto(Map<Integer, List<ExamValidationDetails>> valuationMap, Map<String, Map<Integer, List<ExamValuationStatusTO>>> detailsMap, ExamValuationStatusForm examValuationStatusForm) throws Exception{
		
		List<ExamValuationStatusTO> tos= new ArrayList<ExamValuationStatusTO>();
		if(detailsMap != null){
			Iterator<Entry<String, Map<Integer, List<ExamValuationStatusTO>>>> it = detailsMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<java.lang.String, java.util.Map<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>>> entry = (Map.Entry<java.lang.String, java.util.Map<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>>>) it
						.next();
				ExamValuationStatusTO to = new ExamValuationStatusTO();
				to.setCourseCode(entry.getKey());
				Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap = entry.getValue();
				if(verifyDetailsMap != null){
					Iterator<Entry<Integer, List<ExamValuationStatusTO>>> iterator = verifyDetailsMap.entrySet().iterator();
					List<ExamValuationStatusTO> subjectList= new ArrayList<ExamValuationStatusTO>();
					while (iterator.hasNext()) {
						Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>> entry1 = (Map.Entry<java.lang.Integer, java.util.List<com.kp.cms.to.exam.ExamValuationStatusTO>>) iterator.next();
						ExamValuationStatusTO to1 = new ExamValuationStatusTO();
						
						to1.setSubjectId(entry1.getKey());
						to1.setExamId(Integer.parseInt(examValuationStatusForm.getExamId()));
						List<ExamValuationStatusTO> examValuationStatusTOs = entry1.getValue();
						if(examValuationStatusTOs != null){
							Iterator<ExamValuationStatusTO> iterator2 = examValuationStatusTOs.iterator();
							List<ExamValuationStatusTO> statusTOs = new ArrayList<ExamValuationStatusTO>();
							while (iterator2.hasNext()) {
								ExamValuationStatusTO examValuationStatusTO = (ExamValuationStatusTO) iterator2.next();
								to1.setSubjectCode(examValuationStatusTO.getSubjectCode());
								to1.setSubjectName(examValuationStatusTO.getSubjectName());
								int totalStudent=examValuationStatusTO.getTotalStudent();
								int totalEntered=examValuationStatusTO.getTotalEntered();
								int totalVerified=examValuationStatusTO.getTotalVerified();
								if(totalStudent == totalEntered){
									examValuationStatusTO.setValuationCompleted("Yes");
								}else{
									examValuationStatusTO.setValuationCompleted("No");
								}
								if(totalStudent == totalEntered && totalEntered == totalVerified){
									examValuationStatusTO.setVerificationCompleted("Yes");
								}else{
									examValuationStatusTO.setVerificationCompleted("No");
								}
								statusTOs.add(examValuationStatusTO);
							}
							to1.setStatusTOs(statusTOs);
						}
						
						
						if(valuationMap.containsKey(entry.getKey())){
							List<ExamValidationDetails> details = valuationMap.get(entry.getKey());
							Iterator<ExamValidationDetails> examIterator = details.iterator();
							List<ExamValidationDetailsTO> evaluatorDetails = new ArrayList<ExamValidationDetailsTO>();
							while (examIterator.hasNext()) {
								ExamValidationDetails examValidationDetails = (ExamValidationDetails) examIterator.next();
								if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
									if(examValidationDetails.getSubject().getId() == entry1.getKey()){
										ExamValidationDetailsTO examValidationDetailsTO = new ExamValidationDetailsTO();
										
										if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getEmployee() != null && examValidationDetails.getUsers().getEmployee().getFirstName() != null){
											examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName());
										}else if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getUserName() != null){
											examValidationDetailsTO.setEmployeeName(examValidationDetails.getUsers().getUserName());
										}else{
											examValidationDetailsTO.setEmployeeName(examValidationDetails.getOtherEmployee());
										}
										
										evaluatorDetails.add(examValidationDetailsTO);
									}
								}
							}
							to1.setEvaluatorDetails(evaluatorDetails);
						}
						subjectList.add(to1);
						to.setExamValuationList(subjectList);
					}
				}
				tos.add(to);
			}
			
		}
		return tos;
	}
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		String query="select s from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.classes.course.id="+examValuationStatusForm.getCourseId()+
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+examValuationStatusForm.getExamId()+") " +
		" and subGroup.subject.id=" +examValuationStatusForm.getSubjectId()+" group by s.id";
		return query;
	}
	/**
	 * @param examValuationStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		String query="select s from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and subSet.isActive=1 " +
		" and s.classSchemewise.classes.course.id="+examValuationStatusForm.getCourseId()+
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+examValuationStatusForm.getExamId()+")" +
		" and subSet.subject.id="+examValuationStatusForm.getSubjectId()+" and classHis.schemeNo=subjHist.schemeNo group by s.id";
		return query;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		String query="select sup.studentId,sup.classes.name,sup.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName,sup.studentUtilBO.registerNo " +
				" from ExamSupplementaryImprovementApplicationBO sup " +
				" join sup.studentUtilBO.classSchemewiseUtilBO cs " +
				" join cs.curriculumSchemeDurationUtilBO cd" +
				" where sup.subjectUtilBO.id=" +examValuationStatusForm.getSubjectId()+
				" and sup.examDefinitionBO.id=" +examValuationStatusForm.getExamId()+
				" and sup.classes.course.id=" +examValuationStatusForm.getCourseId()+
				" and (sup.isAppearedTheory=1 or sup.isAppearedPractical=1)";
				
		return query;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryPreviousClassStudents(ExamValuationStatusForm examValuationStatusForm) throws Exception{
		String query="select s,classHis.classes.id,classHis.classes.course.id,classHis.classes.termNumber,cd.academicYear from Student s" +
			" join s.studentPreviousClassesHistory classHis" +
			" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
			" join s.studentSubjectGroupHistory subjHist" +
			" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
			" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +examValuationStatusForm.getSubjectId()+
			" and supImp.examDefinition.id=" +examValuationStatusForm.getExamId()+
			" where s.admAppln.isCancelled=0 and subSet.isActive=1" +
			" and s.classSchemewise.classes.course.id="+examValuationStatusForm.getCourseId()+
			" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
			" from ExamDefinitionBO e" +
			" where e.id="+examValuationStatusForm.getExamId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+examValuationStatusForm.getSchemeNo()+
			" and subSet.subject.id="+examValuationStatusForm.getSubjectId();
		if(examValuationStatusForm.getSubjectType()!=null && !examValuationStatusForm.getSubjectType().isEmpty()){
			if(examValuationStatusForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(examValuationStatusForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1 group by s.id";
			}
		}
		return query;
	}
	/**
	 * @param students
	 * @param examType 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Student> getstudentMap(List<MarksEntryDetails> students, String examType) throws Exception{
		Map<Integer,Student> stuMap = new HashMap<Integer, Student>();
		if(students != null){
			Iterator<MarksEntryDetails> iterator = students.iterator();
			while (iterator.hasNext()) {
				MarksEntryDetails marksEntryDetails = (MarksEntryDetails) iterator.next();
				if(!examType.equalsIgnoreCase("Internal")){
					if(marksEntryDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
						if(marksEntryDetails.getPracticalMarks() != null && !marksEntryDetails.getPracticalMarks().trim().isEmpty() && marksEntryDetails.getTheoryMarks() != null && !marksEntryDetails.getTheoryMarks().trim().isEmpty()){
							if(marksEntryDetails.getMarksEntry() != null && marksEntryDetails.getMarksEntry().getStudent() != null ){
								stuMap.put(marksEntryDetails.getMarksEntry().getStudent().getId(), marksEntryDetails.getMarksEntry().getStudent());
							}
						}
					}else if(marksEntryDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
						if(marksEntryDetails.getTheoryMarks() != null && !marksEntryDetails.getTheoryMarks().trim().isEmpty()){
							if(marksEntryDetails.getMarksEntry() != null && marksEntryDetails.getMarksEntry().getStudent() != null ){
								stuMap.put(marksEntryDetails.getMarksEntry().getStudent().getId(), marksEntryDetails.getMarksEntry().getStudent());
							}
						}
					}else if(marksEntryDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")){
						if(marksEntryDetails.getPracticalMarks() != null && !marksEntryDetails.getPracticalMarks().trim().isEmpty()){
							if(marksEntryDetails.getMarksEntry() != null && marksEntryDetails.getMarksEntry().getStudent() != null ){
								stuMap.put(marksEntryDetails.getMarksEntry().getStudent().getId(), marksEntryDetails.getMarksEntry().getStudent());
							}
						}
					}
				}else{
					if(marksEntryDetails.getTheoryMarks() != null && !marksEntryDetails.getTheoryMarks().trim().isEmpty()){
						if(marksEntryDetails.getMarksEntry() != null && marksEntryDetails.getMarksEntry().getStudent() != null ){
							stuMap.put(marksEntryDetails.getMarksEntry().getStudent().getId(), marksEntryDetails.getMarksEntry().getStudent());
						}
					}
				}
			}
		}
		return stuMap;
	}
	/**
	 * @param studentTos
	 * @param totalStudents
	 * @param stuMap
	 * @param examValuationStatusForm
	 * @param listOfDetainedStudents
	 * @param currentOrPrevious 
	 * @return
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsForRegular( List<StudentTO> studentTos, List<Student> totalStudents,
			Map<Integer, Student> stuMap, ExamValuationStatusForm examValuationStatusForm, List<Integer> listOfDetainedStudents, String currentOrPrevious) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!listOfDetainedStudents.contains(student.getId())){
					/* code changed by sudhir */
					if(currentOrPrevious.equalsIgnoreCase("current")){
						if(examValuationStatusForm.getTermNumber().equalsIgnoreCase(String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo()))){
							/*-------------------*/
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
						}/* code added by sudhir*/
					}else if(currentOrPrevious.equalsIgnoreCase("previous")){
						Set<StudentPreviousClassHistory> previousClassHisSet = student.getStudentPreviousClassesHistory();
						Iterator<StudentPreviousClassHistory> iterator2 = previousClassHisSet.iterator();
						while (iterator2.hasNext()) {
							StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) iterator2 .next();
							if(examValuationStatusForm.getTermNumber().equalsIgnoreCase(String.valueOf(studentPreviousClassHistory.getSchemeNo()))){
								if(!stuMap.containsKey(student.getId())){
									StudentTO to = new StudentTO();
									if(student.getRegisterNo() != null){
										to.setRegisterNo(student.getRegisterNo());
									}
									if(studentPreviousClassHistory.getClasses() != null && studentPreviousClassHistory.getClasses().getName()!=null){
										to.setClassName(studentPreviousClassHistory.getClasses().getName());
									}
									if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
										to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
									}
									studentTos.add(to);
									break;
								}
							}
						}
					}
					/* code added by sudhir*/
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
	 * @param currentOrPrevious 
	 * @return
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsRegular1( List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer, String> stuMap,
			ExamValuationStatusForm examValuationStatusForm, List<Integer> listOfDetainedStudents, String currentOrPrevious) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!listOfDetainedStudents.contains(student.getId())){
					/* code changed by sudhir */
					if(currentOrPrevious.equalsIgnoreCase("current")){
						if(examValuationStatusForm.getTermNumber().equalsIgnoreCase(String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo()))){
							/*-------------------*/
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
					}else if(currentOrPrevious.equalsIgnoreCase("previous")){
						Set<StudentPreviousClassHistory> previousClassHisSet = student.getStudentPreviousClassesHistory();
						Iterator<StudentPreviousClassHistory> iterator2 = previousClassHisSet.iterator();
						while (iterator2.hasNext()) {
							StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) iterator2 .next();
							if(examValuationStatusForm.getTermNumber().equalsIgnoreCase(String.valueOf(studentPreviousClassHistory.getSchemeNo()))){
								if(!stuMap.containsKey(student.getId())){
									StudentTO to = new StudentTO();
									if(student.getRegisterNo() != null){
										to.setRegisterNo(student.getRegisterNo());
									}
									if(studentPreviousClassHistory.getClasses() != null && studentPreviousClassHistory.getClasses().getName()!=null){
										to.setClassName(studentPreviousClassHistory.getClasses().getName());
									}
									if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
										to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
									}
									studentTos.add(to);
								}
							}
						}
					}
				}
				
			}
		}
		return studentTos;
	}
	/**
	 * @param objectsList
	 * @return
	 */
	public Map<Integer, Map<String, List<StudentTO>>> convertBOListToMap( List<Object[]> objectsList) throws Exception{
		Map<Integer, Map<String, List<StudentTO>>> newCourseMap = new HashMap<Integer, Map<String,List<StudentTO>>>();
		Map<String,List<StudentTO>> newSubjectMap = null;
		if(objectsList!=null && !objectsList.isEmpty()){
			Iterator<Object[]> iterator = objectsList.iterator();
			List<StudentTO> newList = null;
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				int subjectId=0;
				if(objects[0].toString() != null && !objects[0].toString().isEmpty()){
					subjectId = Integer.parseInt(objects[0].toString());
				}
				String subjectId_EvaluatorTypeId =null;
				if(objects[3]!=null){
					subjectId_EvaluatorTypeId = String.valueOf(subjectId)+"_"+objects[3].toString();
				}else{
					subjectId_EvaluatorTypeId =String.valueOf(subjectId);
				}
				if(objects[11].toString() !=null && !objects[11].toString().isEmpty()){
					if(newCourseMap.containsKey(Integer.parseInt(objects[11].toString()))){
						newSubjectMap = newCourseMap.get(Integer.parseInt(objects[11].toString()));
					}else{
						newSubjectMap = new HashMap<String, List<StudentTO>>();
					}
					if(newSubjectMap.containsKey(subjectId_EvaluatorTypeId)){
						newList = newSubjectMap.remove(subjectId_EvaluatorTypeId);
					}else{
						newList = new ArrayList<StudentTO>();
					}
					StudentTO studentTO = new StudentTO();
					if(objects[0].toString() != null && !objects[0].toString().isEmpty()){
						studentTO.setSubjectId(objects[0].toString());
					}
					if(objects[7].toString()!=null && !objects[7].toString().isEmpty()){
					  if(objects[9].toString()!=null && !objects[9].toString().isEmpty()){
						studentTO.setSubjectName(objects[7].toString()+"("+objects[9].toString()+")");
					}else{
						studentTO.setSubjectName(objects[7].toString());
					}
					}
					if(objects[8].toString()!=null && !objects[8].toString().isEmpty()){
						studentTO.setRegisterNo(objects[8].toString());
					}
					if(objects[10].toString()!=null && !objects[10].toString().isEmpty()){
						studentTO.setId(Integer.parseInt(objects[10].toString()));
					}
					if(objects[12].toString()!=null && !objects[12].toString().isEmpty()){
						studentTO.setClassName(objects[12].toString());
					}
					
					if(objects[13].toString()!=null && !objects[13].toString().isEmpty()){
						studentTO.setStudentName(objects[13].toString());
					}
					newList.add(studentTO);
					newSubjectMap.put(subjectId_EvaluatorTypeId, newList);
					newCourseMap.put(Integer.parseInt(objects[11].toString()), newSubjectMap);
				}
			}
		}
		return newCourseMap;
	}
	/**
	 * @param studentTos 
	 * @param totalStudents 
	 * @param students
	 * @param examValuationStatusForm 
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsForSup(List<StudentTO> studentTos, List<Object[]> totalStudents, Map<Integer,Student> stuMap, ExamValuationStatusForm examValuationStatusForm) throws Exception{
		if(totalStudents != null){
			Iterator<Object[]> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[0] != null && objects[0].toString() != null && !objects[0].toString().isEmpty()){
					if(!stuMap.containsKey(Integer.parseInt(objects[0].toString()))){
						StudentTO to = new StudentTO();
						if(objects[3] != null){
							to.setRegisterNo(objects[3].toString());
						}
						if(objects[1] != null){
							to.setClassName(objects[1].toString());
						}
						if(objects[2] != null){
							to.setStudentName(objects[2].toString());
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
	public List<StudentTO> convertBOtoTOStudentDetailsForSup1(List<StudentTO> studentTos, List<Object[]> totalStudents, Map<Integer,String> stuMap, ExamValuationStatusForm examValuationStatusForm) throws Exception{
		if(totalStudents != null){
			Iterator<Object[]> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				
				Object[] objects = (Object[]) iterator.next();
				if(objects[0] != null && objects[0].toString() != null && !objects[0].toString().isEmpty()){
					if(!stuMap.containsKey(Integer.parseInt(objects[0].toString()))){
						StudentTO to = new StudentTO();
						if(objects[3] != null){
							to.setRegisterNo(objects[3].toString());
						}
						if(objects[1] != null){
							to.setClassName(objects[1].toString());
						}
						if(objects[2] != null){
							to.setStudentName(objects[2].toString());
						}
						studentTos.add(to);
					}
				}
			}
		}
		return studentTos;
	}
}
