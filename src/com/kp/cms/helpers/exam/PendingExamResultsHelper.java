package com.kp.cms.helpers.exam;

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
import java.util.Set;
import java.util.Map.Entry;

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

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationProcess;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.PendingExamResultsForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.PendingExamResultsTO;
import com.kp.cms.transactions.admission.IPendingExamResultsTransactions;
import com.kp.cms.transactions.exam.IExamValuationStatusTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationStatusTxImpl;
import com.kp.cms.transactionsimpl.exam.PendingExamResultsImpl;
import com.kp.cms.utilities.CommonUtil;

public class PendingExamResultsHelper {
	private static final Log log = LogFactory.getLog(PendingExamResultsHelper.class);
	public static volatile PendingExamResultsHelper examCceFactorHelper = null;
	private static final String DISPLAY = "display";
	public static PendingExamResultsHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PendingExamResultsHelper();
		}
		return examCceFactorHelper;
	}

	/**
	 * @param pendingResults
	 * @param objForm
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	public List<PendingExamResultsTO> setPendingExamResultsList(List<Object[]> pendingResults, PendingExamResultsForm objForm) throws Exception, Exception {
		List<PendingExamResultsTO> resultList=new ArrayList<PendingExamResultsTO>();
		if(pendingResults!=null && !pendingResults.isEmpty()){
			Iterator iter=pendingResults.iterator();
			while (iter.hasNext()) {
				Object[] object = (Object[]) iter.next();
				PendingExamResultsTO pendingExamResultsTO=new PendingExamResultsTO();
				if(object[0]!=null){
					pendingExamResultsTO.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(object[0].toString()),"ExamDefinitionBO",true,"name"));
				}if(object[3]!=null){
					pendingExamResultsTO.setClassName(object[3].toString());
				}if(object[4]!=null){
					pendingExamResultsTO.setBatch(object[4].toString());
				}if(object[0]!=null){
					pendingExamResultsTO.setExamId(object[0].toString());
				}if(object[1]!=null){
					pendingExamResultsTO.setClassId(object[1].toString());
				}if(objForm.getExamTypeName().equalsIgnoreCase("Regular"))
				{
					pendingExamResultsTO.setStatuses("Regular");
				}else{
					pendingExamResultsTO.setStatuses("Supplementary");
				}
				resultList.add(pendingExamResultsTO);
			}
		}
		return resultList;
	}

	/**
	 * @param objForm
	 * @param request 
	 * @return
	 * @throws Exception 
	 */
	public boolean convertToTOExcel(PendingExamResultsForm objForm, HttpServletRequest request) throws Exception {
		boolean isUpdated=false;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.UPLOAD_ADM_BIODATA);
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
		
		if(objForm.getExamPendingResultList()!=null){
			PendingExamResultsTO to=(PendingExamResultsTO)objForm.getExamPendingResultList().get(0);
			int count = 0;
			wb=new XSSFWorkbook();
			XSSFCellStyle cellStyle=wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Pending Exam Results");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
				row.createCell((short)0).setCellValue("Exam Name");
				row.createCell((short)1).setCellValue("Batch");
				row.createCell((short)2).setCellValue("Class Name");
			Iterator<PendingExamResultsTO> iterator = objForm.getExamPendingResultList().iterator();
			try{
				// Create cells in the row and put some data in it.
				while(iterator.hasNext()) {
				PendingExamResultsTO PendingTO = (PendingExamResultsTO) iterator.next();
				count = count +1;
				row = sheet.createRow(count);
				if(PendingTO.getExamName()!=null && !PendingTO.getExamName().isEmpty()){
					row.createCell((short) 0).setCellValue(PendingTO.getExamName());
				}if(PendingTO.getBatch()!=null && !PendingTO.getBatch().isEmpty()){
					row.createCell((short) 1).setCellValue(PendingTO.getBatch());
				}if(PendingTO.getClassName()!=null && !PendingTO.getClassName().isEmpty()){
					row.createCell((short) 2).setCellValue(PendingTO.getClassName());
				}
				}
				bos=new ByteArrayOutputStream();
				wb.write(bos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
				isUpdated=true;
				fos.flush();
				fos.close();
			}catch (Exception e) {
				//throw new ApplicationException();
				// TODO: handle exception
			}
		}
		return isUpdated;
}

	public String getQueryForCurrentClassStudents(PendingExamResultsForm objForm) {
		String query="select ssa from Student s" +
		" inner join s.studentSupplementaryImprovements ssa"+
		" where ssa.classes.id="+objForm.getClassId()+
		" and ssa.examDefinition.id="+objForm.getExamId()+
		" and ((ssa.isAppearedTheory=1) or (ssa.isAppearedPractical=1))"+
		" group by ssa.student.id"+
		" order by ssa.student.registerNo";
		return query;
	}

	public List<StudentTO> convertBOtoTOStudentDetailsSup(List<StudentTO> studentTos,List<StudentSupplementaryImprovementApplication> totalStudents) {
		if(totalStudents != null){
			Iterator<StudentSupplementaryImprovementApplication> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				StudentSupplementaryImprovementApplication studsent = (StudentSupplementaryImprovementApplication) iterator.next();
				StudentTO to = new StudentTO();
						if(studsent.getStudent().getRegisterNo()!= null){
							to.setRegisterNo(studsent.getStudent().getRegisterNo());
						}
						if(studsent.getClasses().getName() != null && !studsent.getClasses().getName().isEmpty()){
							to.setClassName(studsent.getClasses().getName());
						}
						if(studsent.getStudent().getAdmAppln() != null && studsent.getStudent().getAdmAppln().getPersonalData() != null && studsent.getStudent().getAdmAppln().getPersonalData().getFirstName() != null){
							to.setStudentName(studsent.getStudent().getAdmAppln().getPersonalData().getFirstName());
						}
						studentTos.add(to);
					}
				}	
		return studentTos;
	}

	


	/**
	 * @param pendingResults
	 * @param objForm
	 * @return
//	 */
//	public List<PendingExamResults> convertFormToBos(List<Object[]> pendingResults, PendingExamResultsForm objForm) {
//		List<PendingExamResults> teacherList=new ArrayList<PendingExamResults>();
//		if(pendingResults!=null && !pendingResults.isEmpty()){
//			Iterator iter=pendingResults.iterator();
//			while (iter.hasNext()) {
//				Object[] object = (Object[]) iter.next();
//				PendingExamResults pendingExamResults=new PendingExamResults();
//				if(object[0]!=null){
//					ExamDefinition exam=new ExamDefinition();
//					exam.setId(Integer.parseInt(object[0].toString()));
//					pendingExamResults.setExamId(exam);
//				}if(object[1]!=null){
//					Classes clas=new Classes();
//					clas.setId(Integer.parseInt(object[1].toString()));
//					pendingExamResults.setClassId(clas);
//				}
//				teacherList.add(pendingExamResults);
//			}
//			
//		}
//		return teacherList;
//	}
	
	
	
	/**
	 * @param valuationMap
	 * @param courseMap
	 * @param objForm
	 * @param newMap
	 * @param internalExamId
	 * @param examStartDates
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationStatusTO> convertValues(Map<Integer, List<ExamValidationDetails>> valuationMap,Map<Integer,Map<Integer, List<ExamValuationStatusTO>>> courseMap,PendingExamResultsForm objForm, Map<Integer, Map<String, List<StudentTO>>> newMap, Integer internalExamId, Map<Integer, String> examStartDates) throws Exception {
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		IPendingExamResultsTransactions transaction1=new PendingExamResultsImpl();
		List<ExamValuationProcess> valuationProcesses = transaction.getProcessCompletedDetails(Integer.parseInt(objForm.getExamId()));
		List<Object[]> absentDetails;
		if(objForm.getExamType().equalsIgnoreCase("Supplementary")){
			absentDetails = transaction1.getTotalVerfiedStudentForSupplementary(objForm.getExamId(),objForm.getClassId(),true,internalExamId);
		}else{
			absentDetails = transaction1.getTotalVerfiedStudent(objForm.getExamId(),objForm.getClassId(),objForm.getFinalYears(),true,internalExamId);
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
						to.setClassId(Integer.parseInt(objForm.getClassId()));
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
										if(process.getExam() != null && process.getExam().getId() == Integer.parseInt(objForm.getExamId())){
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
									to.setExamId(Integer.parseInt(objForm.getExamId()));
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
								/*String programType_Name = examValuationStatusForm.getProgrammCourseMap().get(to.getCourseId());
								to.setProgramType(programType_Name);*/
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
	
	/**
	 * @param absentDetails
	 * @return
	 * @throws Exception
	 */
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
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(PendingExamResultsForm objForm) throws Exception{
		String query="select s from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0 and subSet.isActive=1 " +
		" and s.classSchemewise.classes.course.id="+objForm.getCourseId()+
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+objForm.getExamId()+")" +
		" and subSet.subject.id="+objForm.getSubjectId()+" and classHis.schemeNo=subjHist.schemeNo group by s.id";
		return query;
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudentsForverify(PendingExamResultsForm objForm) throws Exception{
		String query="select s from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.classes.course.id="+objForm.getCourseId()+
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+objForm.getExamId()+") " +
		" and subGroup.subject.id=" +objForm.getSubjectId()+" group by s.id";
		return query;
	}
	
	
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(PendingExamResultsForm objForm) throws Exception{
		String query="select sup.studentId,sup.classes.name,sup.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName,sup.studentUtilBO.registerNo " +
				" from ExamSupplementaryImprovementApplicationBO sup " +
				" join sup.studentUtilBO.classSchemewiseUtilBO cs " +
				" join cs.curriculumSchemeDurationUtilBO cd" +
				" where sup.subjectUtilBO.id=" +objForm.getSubjectId()+
				" and sup.examDefinitionBO.id=" +objForm.getExamId()+
				" and sup.classes.id=" +objForm.getClassId()+
				" and (sup.isAppearedTheory=1 or sup.isAppearedPractical=1)";
				
		return query;
	}
	
	
	/**
	 * @param studentTos
	 * @param totalStudents
	 * @param stuMap
	 * @param objForm
	 * @param listOfDetainedStudents
	 * @param currentOrPrevious
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsForRegular( List<StudentTO> studentTos, List<Student> totalStudents,
			Map<Integer, Student> stuMap, PendingExamResultsForm objForm, List<Integer> listOfDetainedStudents, String currentOrPrevious) throws Exception{
		if(totalStudents != null){
			Iterator<Student> iterator = totalStudents.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				if(!listOfDetainedStudents.contains(student.getId())){
					/* code changed by sudhir */
					if(currentOrPrevious.equalsIgnoreCase("current")){
						if(objForm.getTermNumber().equalsIgnoreCase(String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo()))){
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
							if(objForm.getTermNumber().equalsIgnoreCase(String.valueOf(studentPreviousClassHistory.getSchemeNo()))){
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
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetails(List<StudentTO> studentTos, List<Student> totalStudents, Map<Integer,Student> stuMap, PendingExamResultsForm objForm) throws Exception{
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
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsForSup(List<StudentTO> studentTos, List<Object[]> totalStudents, Map<Integer,Student> stuMap, PendingExamResultsForm objForm) throws Exception{
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
	 * @param stuMap
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBOtoTOStudentDetailsForSup1(List<StudentTO> studentTos, List<Object[]> totalStudents, Map<Integer,String> stuMap, PendingExamResultsForm objForm) throws Exception{
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
	
	public Map<Integer, Map<String, List<StudentTO>>> convertBOListToMap( List<Object[]> objectsList) throws Exception{
		Map<Integer, Map<String, List<StudentTO>>> newClassMap = new HashMap<Integer, Map<String,List<StudentTO>>>();
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
				if(objects[14].toString() !=null && !objects[14].toString().isEmpty()){
					if(newClassMap.containsKey(Integer.parseInt(objects[14].toString()))){
						newSubjectMap = newClassMap.get(Integer.parseInt(objects[14].toString()));
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
					newClassMap.put(Integer.parseInt(objects[14].toString()), newSubjectMap);
				}
			}
		}
		return newClassMap;
	}
}
