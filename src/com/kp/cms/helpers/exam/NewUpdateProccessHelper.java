package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamGracingSubjectMarksBo;
import com.kp.cms.bo.exam.ExamStudentPassFail;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalRedoMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.handlers.exam.NewUpdateProccessHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.transactionsimpl.exam.ExamUpdateProcessImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewUpdateProccessTransactionImpl;
import com.kp.cms.transactionsimpl.exam.UpdateExamStudentSGPAImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

@SuppressWarnings("unchecked")
public class NewUpdateProccessHelper {
	
	static DecimalFormat df = new DecimalFormat("#.##");
	public static List<Integer> avoidExamIds=new ArrayList<Integer>();
	IUpdateStudentSGPATxn iUpdateStudentSGPATxn = UpdateExamStudentSGPAImpl.getInstance();
	/**
	 * Singleton object of NewUpdateProccessHelper
	 */
	private static volatile NewUpdateProccessHelper newUpdateProccessHelper = null;
	private NewUpdateProccessHelper() {
		
	}
	/**
	 * return singleton object of NewUpdateProccessHelper.
	 * @return
	 */
	public static NewUpdateProccessHelper getInstance() {
		if (newUpdateProccessHelper == null) {
			newUpdateProccessHelper = new NewUpdateProccessHelper();
		}
		return newUpdateProccessHelper;
	}
	ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();
	

	public String getQueryForBatchYear() throws Exception{
		String query="select c.year" +
	" from ExamDefinition e" +
	" join e.courseSchemeDetails courseDetails" +
	" join courseDetails.course.classes classes" +
	" join classes.classSchemewises classwise" +
	" join classwise.curriculumSchemeDuration.curriculumScheme c" +
	" where e.delIsActive=1 group by c.year order by c.year";
	return query;
	}
	
	
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public String getqueryForSuppliementaryDataCreation(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		String query="select classwise.curriculumSchemeDuration.academicYear," +
				" c.year,classes" +
				" from ExamDefinition e" +
				" join e.courseSchemeDetails courseDetails" +
				" join courseDetails.course.classes classes" +
				" join classes.classSchemewises classwise" +
				" join classwise.curriculumSchemeDuration.curriculumScheme c" +
				" where  e.delIsActive=1 and e.id="+newUpdateProccessForm.getExamId();
		if(newUpdateProccessForm.getBatchYear()!=null && !newUpdateProccessForm.getBatchYear().isEmpty()){
			query=query+" and c.year="+newUpdateProccessForm.getBatchYear();
		}
//		if(newUpdateProccessForm.getProcess().equalsIgnoreCase("3") || newUpdateProccessForm.getProcess().equalsIgnoreCase("9")){
//			query=query+" and classwise.curriculumSchemeDuration.academicYear>=e.examForJoiningBatch" ;
//		}commented by basim
		if(!newUpdateProccessForm.getProcess().equalsIgnoreCase("4")){
			query=query+" and classwise.curriculumSchemeDuration.academicYear=e.academicYear" ;
		}		
		query=query+" and classes.termNumber=courseDetails.schemeNo" +
				" and classes.isActive=1 and courseDetails.isActive=1 order by classes.name";
		return query;
	}
			
	public Map<Integer, String> convertBatchBoListToTOList(List list) throws Exception {
		
		Map<Integer, String> batchmap=new HashMap<Integer, String>();
		int batch=0;
		if(list!=null && !list.isEmpty()){
			Iterator itr=list.iterator();
			while (itr.hasNext()) {
				Object object = (Object) itr.next();
				if(object != null)
					batch=(Integer.parseInt(object.toString()));
					
				batchmap.put(batch, String.valueOf(batch));
			}
		}
		batchmap = (Map<Integer, String>) CommonUtil.sortMapByValue(batchmap);
		return batchmap;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<ClassesTO> convertBoListToTOList(List<Object[]> list) throws Exception {
		List<ClassesTO> mainList=new ArrayList<ClassesTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] object = itr.next();
				ClassesTO to=new ClassesTO();
				if(object[0]!=null)
					to.setYear(Integer.parseInt(object[0].toString()));
				if(object[1]!=null)
					to.setBatchYear(Integer.parseInt(object[1].toString()));
				if(object[2]!=null){
					Classes c=(Classes)object[2];
					to.setId(c.getId());
					to.setClassName(c.getName());
					to.setTermNo(c.getTermNumber());
					to.setCourseId(c.getCourse().getId());
					to.setProgramId(c.getCourse().getProgram().getId());
					to.setProgramTypeId(c.getCourse().getProgram().getProgramType().getId());
				}
				mainList.add(to);	
			}
		}
		return mainList;
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentSupplementaryImprovementApplication> getBoListFromToList(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
		List<StudentSupplementaryImprovementApplication> boList=new ArrayList<StudentSupplementaryImprovementApplication>();
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		boolean isImprovement=false;
		String programTypeQuery=" select c.program.programType.name from Course c ";
		String programType=null;
		if(classList!=null && !classList.isEmpty()){
			Iterator<ClassesTO> itr=classList.iterator();
			while (itr.hasNext()) {
				ClassesTO to = (ClassesTO) itr.next();
				if(programType==null){
					programTypeQuery = programTypeQuery+"where c.id = "+to.getCourseId();
					List list = transaction.getDataByQuery(programTypeQuery);
					programType = (String) list.get(0);
				}
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
					Map<String,SubjectMarksTO> minMarks=transaction.getMinMarksMap(to);
					List<Integer> excludedList =  impl.getExcludedFromResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
					List<Integer> failureExcludeList = impl.getExcludedFromTotResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
					boolean isDelted=transaction.deleteOldRecords(to.getId(),newUpdateProccessForm.getExamId());
					Map<String, Boolean> stuImpMap = transaction.getSuppImpExamDetails(to.getId());
					if(isDelted){
						boolean isMaxRecord=false;
						if(to.getCourseId()!=18){
							isMaxRecord=true;
						}
						List<StudentTO> studentList=getStudentListForClass(to.getId());
						if(studentList!=null && !studentList.isEmpty()){
							// The Real Code Here Only Starts
							Iterator<StudentTO> stuItr=studentList.iterator();
							while (stuItr.hasNext()) {
								StudentTO studentTO = (StudentTO) stuItr.next();
//								System.out.println("Student Id:"+studentTO.getId());
								//if(studentTO.getId()==3013){//remove this
								Map<Integer,StudentMarkDetailsTO> totSubMap=new HashMap<Integer, StudentMarkDetailsTO>();//subject and marks details to verify max or latest and keep in the map at last
								BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(to.getCourseId());
								boolean checkTotal=true;
								double stuTotalMarks=0;
								double subTotalMarks=0;
								List<Object[]> list=transaction.getDataByStudentAndClassId(studentTO.getId(),to.getId(),studentTO.getSubjectIdList(),studentTO.getAppliedYear());
								if(list!=null && !list.isEmpty()){
									Iterator<Object[]> marksItr=list.iterator();
									
									while (marksItr.hasNext()) {
										
										Object[] objects = (Object[]) marksItr.next();
										//subject id check
										//if(711==(Integer.parseInt(objects[9].toString()))){
											
										
										if(objects[9]!=null && minMarks.containsKey(objects[9].toString())){
											boolean isfalse=false;
											boolean isAddTotal=true;
											if(excludedList.contains(Integer.parseInt(objects[9].toString()))){
												isAddTotal=false;
											}
											SubjectMarksTO subTo=minMarks.get(objects[9].toString());
											StudentMarkDetailsTO markDetailsTO= new StudentMarkDetailsTO();
											if(objects[4]!=null)
												markDetailsTO.setStudentId(Integer.parseInt(objects[4].toString()));
											if(objects[9]!=null)
												markDetailsTO.setSubjectId(Integer.parseInt(objects[9].toString()));
											double theoryRegMark=0;
											boolean isTheoryAlpha=false;
											if (objects[15] != null || objects[16] != null) {
												if(objects[15]!=null){
													if(!StringUtils.isAlpha(objects[15].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[15].toString());
													else
														isTheoryAlpha=true;
												}
												if(objects[16]!=null){
													if(!StringUtils.isAlpha(objects[16].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[16].toString());
													else
														isTheoryAlpha=true;
												}
											}else{
												if(objects[11]!=null){
													if(!StringUtils.isAlpha(objects[11].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[11].toString());
													else
														isTheoryAlpha=true;
												}
												if(objects[12]!=null){
													if(!StringUtils.isAlpha(objects[12].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[12].toString());
													else
														isTheoryAlpha=true;
												}
											}
											if(!isTheoryAlpha){
												if(programType.equalsIgnoreCase("UG"))
													markDetailsTO.setStuTheoryIntMark(String.valueOf(Math.ceil(theoryRegMark)));
												else
													markDetailsTO.setStuTheoryIntMark(String.valueOf(Math.round(theoryRegMark)));
											}
												
											else
												markDetailsTO.setStuTheoryIntMark("AA");
											double practicalRegMark=0;
											boolean isPracticalAlpha=false;
											if (objects[17] != null || objects[18] != null) {
												if(objects[17]!=null){
													if(!StringUtils.isAlpha(objects[17].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[17].toString());
													else
														isPracticalAlpha=true;
												}if(objects[18]!=null){
													if(!StringUtils.isAlpha(objects[18].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[18].toString());
													else
														isPracticalAlpha=true;
												}
											}else{
												if(objects[13]!=null){
													if(!StringUtils.isAlpha(objects[13].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[13].toString());
													else
														isPracticalAlpha=true;
												}
												if(objects[14]!=null){
													if(!StringUtils.isAlpha(objects[14].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[14].toString());
													else
														isPracticalAlpha=true;
												}
											}
											if(!isPracticalAlpha){

												if(programType.equalsIgnoreCase("UG"))
													markDetailsTO.setStuPracIntMark(String.valueOf(Math.ceil(practicalRegMark)));
												else
													markDetailsTO.setStuPracIntMark(String.valueOf(Math.round(practicalRegMark)));
											
											}
											else
												markDetailsTO.setStuPracIntMark("AA");
											
											if (objects[19] != null) {
												markDetailsTO.setStuTheoryRegMark(objects[19].toString());
											}
											if (objects[20] != null) {
												markDetailsTO.setStuPracRegMark(objects[20].toString());
											}
											if (objects[22] != null) {
												markDetailsTO.setIs_theory_practical(objects[22].toString());
											}
											isImprovement=false;
											if (objects[23] != null) {
												isImprovement=Boolean.parseBoolean(objects[23].toString());
											}
											if(isMaxRecord){
												if(totSubMap.containsKey(markDetailsTO.getSubjectId())){
													StudentMarkDetailsTO markDetailsTO2=totSubMap.remove(markDetailsTO.getSubjectId());
													StudentMarkDetailsTO maxMarks=checkMaxBetweenTOs(markDetailsTO,markDetailsTO2,subTo,subTotalMarks,stuTotalMarks,isAddTotal,isImprovement);
													totSubMap.put(markDetailsTO.getSubjectId(),maxMarks);
												}else{
													totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
												}
											}else{
												if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
													totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
												}
											}
										}
										
									//}
									}
									
									// The Real Logic comes now ( New Logic has Implemented)
									List<StudentMarkDetailsTO> totalList = new ArrayList<StudentMarkDetailsTO>(totSubMap.values());

									if(!totalList.isEmpty()){
										Iterator<StudentMarkDetailsTO> totalitr=totalList.iterator();
										while (totalitr.hasNext()) {
											StudentMarkDetailsTO markDetailsTO = totalitr .next();
											if(minMarks.containsKey(String.valueOf(markDetailsTO.getSubjectId()))){
												boolean isAddTotal=true;
												boolean isfalse=false;
												if(excludedList.contains(markDetailsTO.getSubjectId())){
													isAddTotal=false;
												}
												SubjectMarksTO subTo=minMarks.get(String.valueOf(markDetailsTO.getSubjectId()));
												// The Real Logic has to implement Here
												if(subTo.getTheoryIntMin()!=null && !subTo.getTheoryIntMin().isEmpty()){
													if (markDetailsTO.getStuTheoryIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
															isfalse=true;
															checkTotal=false;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double
																.parseDouble(subTo.getTheoryIntMin())) {
															isfalse=true;
															checkTotal=false;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
													else{
														isfalse=true;
														checkTotal=false;
														markDetailsTO.setIsTheoryFailed(true);
													}
												}
												if(subTo.getTheoryRegMin()!=null && !subTo.getTheoryRegMin().isEmpty()){
													if (markDetailsTO.getStuTheoryRegMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark()) < Double
																.parseDouble(subTo.getTheoryRegMin())) {
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
													else{
														isfalse=true;	
														markDetailsTO.setIsTheoryFailed(true);
													}
												}
												if(subTo.getPracticalIntMin()!=null && !subTo.getPracticalIntMin().isEmpty()){
													if (markDetailsTO.getStuPracIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
																.parseDouble(subTo.getPracticalIntMin())) {
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
													else{
														isfalse=true;
														markDetailsTO.setIsPracticalFailed(true);
													}
												}
												if(subTo.getPracticalRegMin()!=null && !subTo.getPracticalRegMin().isEmpty()){
													if (markDetailsTO.getStuPracRegMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuPracRegMark()) < Double
																.parseDouble(subTo.getPracticalRegMin())) {
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
													else{
														isfalse=true;	
														markDetailsTO.setIsPracticalFailed(true);
													}
												}

												if (subTo.getFinalTheoryMin() != null) {
													if(isAddTotal){
														if(subTo.getFinalTheoryMarks()!=null)
															subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
														if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
															stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
														}
														if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
															stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
														}
													}
													if (markDetailsTO.getStuTheoryRegMark() != null
															&& markDetailsTO.getStuTheoryIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim()) ){
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark())+ 
																Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double.parseDouble(subTo.getFinalTheoryMin())) {
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
												 }
												if (subTo.getFinalPracticalMin() != null) {
													if(isAddTotal){
														if(subTo.getFinalPracticalMarks()!=null)
															subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
														if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
															stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
														}
														if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
															stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
														}
													}
													if (markDetailsTO.getStuPracRegMark() != null && markDetailsTO.getStuPracIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuPracRegMark())
																+ Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
																.parseDouble(subTo.getFinalPracticalMin())) {
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
												}
												if(((markDetailsTO.getIsTheoryFailed()!=null && markDetailsTO.getIsTheoryFailed()) || (markDetailsTO.getIsPracticalFailed()!=null && markDetailsTO.getIsPracticalFailed())) && !failureExcludeList.contains(markDetailsTO.getSubjectId())){
													checkTotal=false;
												}
											}
										}
									}
									
									if(!totSubMap.isEmpty()){
										if(checkTotal){
											// percentage Calculation should be done here
											boolean isAverage=false;
											Double studentPercentage =Double.valueOf(0);
											if(subTotalMarks>0)
											studentPercentage= Double.valueOf((stuTotalMarks * 100) / subTotalMarks);
											if (requiredAggrePercentage != null	&& studentPercentage != null) {
												if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
													isAverage = true;	
												}
											}
											if(isAverage){
												Iterator it = totSubMap.entrySet().iterator();
												StudentMarkDetailsTO sto=null; 
											    while (it.hasNext()) {/*
											        Map.Entry pairs = (Map.Entry)it.next();
											        sto=(StudentMarkDetailsTO)pairs.getValue();
										        	StudentSupplementaryImprovementApplication bo=new StudentSupplementaryImprovementApplication();
										        	Student student=new Student();
										        	student.setId(studentTO.getId());
										        	bo.setStudent(student);
										        	bo.setIsSupplementary(false);
										        	bo.setIsAppearedTheory(false);
										        	bo.setIsAppearedPractical(false);
										        	bo.setIsImprovement(true);
									        		bo.setIsFailedTheory(false);
									        		bo.setIsFailedPractical(false);
										        	ExamDefinition exam=new ExamDefinition();
										        	exam.setId(Integer.parseInt(newUpdateProccessForm.getExamId()));
										        	bo.setExamDefinition(exam);
										        	Subject subject=new Subject();
										        	subject.setId(sto.getSubjectId());
										        	bo.setSubject(subject);
										        	Classes classes=new Classes();
										        	classes.setId(to.getId());
										        	bo.setClasses(classes);
										        	bo.setSchemeNo(to.getTermNo());
										        	bo.setCreatedBy(newUpdateProccessForm.getUserId());
										        	bo.setCreatedDate(new Date());
										        	bo.setModifiedBy(newUpdateProccessForm.getUserId());
										        	bo.setLastModifiedDate(new Date());
										        	if(sto.getIs_theory_practical().equalsIgnoreCase("t") || sto.getIs_theory_practical().equalsIgnoreCase("b"))
										        		bo.setIsTheoryOverallFailed(true);
										        	if(sto.getIs_theory_practical().equalsIgnoreCase("p") || sto.getIs_theory_practical().equalsIgnoreCase("b"))
										        		bo.setIsPracticalOverallFailed(true);
										        	boList.add(bo);
											    */}
											}
										}else{
											Iterator it = totSubMap.entrySet().iterator();
											StudentMarkDetailsTO sto=null; 
										    while (it.hasNext()) {
										        Map.Entry pairs = (Map.Entry)it.next();
										        sto=(StudentMarkDetailsTO)pairs.getValue();
										        if( stuImpMap.size()==0 || (stuImpMap.containsKey(studentTO.getId()+"_"+sto.getSubjectId())&& !stuImpMap.get(studentTO.getId()+"_"+sto.getSubjectId()))|| !stuImpMap.containsKey(studentTO.getId()+"_"+sto.getSubjectId())){
									        		
										        if(((sto.getIsTheoryFailed()!=null && sto.getIsTheoryFailed()) || (sto.getIsPracticalFailed()!=null && sto.getIsPracticalFailed())) && !failureExcludeList.contains(sto.getSubjectId())){
										        	StudentSupplementaryImprovementApplication bo=new StudentSupplementaryImprovementApplication();
										        	Student student=new Student();
										        	student.setId(studentTO.getId());
										        	bo.setStudent(student);
										        	if(sto.getIsTheoryFailed()!=null && sto.getIsTheoryFailed()){
										        		bo.setIsFailedTheory(true);
										        	}else{
										        		bo.setIsFailedTheory(false);
										        	}
										        	if(sto.getIsPracticalFailed()!=null && sto.getIsPracticalFailed()){
										        		bo.setIsFailedPractical(true);
										        	}else{
										        		bo.setIsFailedPractical(false);
										        	}
										        	bo.setIsSupplementary(true);
										        	bo.setIsAppearedTheory(false);
										        	bo.setIsAppearedPractical(false);
										        	bo.setIsImprovement(false);
										        	ExamDefinition exam=new ExamDefinition();
										        	exam.setId(Integer.parseInt(newUpdateProccessForm.getExamId()));
										        	bo.setExamDefinition(exam);
										        	Subject subject=new Subject();
										        	subject.setId(sto.getSubjectId());
										        	bo.setSubject(subject);
										        	Classes classes=new Classes();
										        	classes.setId(to.getId());
										        	bo.setClasses(classes);
										        	bo.setSchemeNo(to.getTermNo());
										        	bo.setCreatedBy(newUpdateProccessForm.getUserId());
										        	bo.setCreatedDate(new Date());
										        	bo.setModifiedBy(newUpdateProccessForm.getUserId());
										        	bo.setLastModifiedDate(new Date());
										        	bo.setIsOnline(false);
										        	boList.add(bo);
										        }
										    
										}
										    }
										}
									}
								}
						//	}//remove this
						}
						}
					}
				}
			}
		}
		return boList;
	}

	/**
	 * @param markDetailsTO1
	 * @param markDetailsTO2
	 * @throws Exception
	 */
	public StudentMarkDetailsTO checkMaxBetweenTOs(StudentMarkDetailsTO to1,StudentMarkDetailsTO to2,SubjectMarksTO subTo,double subTotalMarks,double stuTotalMarks,boolean isAddTotal,boolean isImprovement) throws Exception {
		
		StudentMarkDetailsTO  markDetailsTO =new StudentMarkDetailsTO();
		if(isAddTotal){
			//everything two times we have to deticate in subTotalMarks  because we are adding two times for two TO'S
			if(subTo.getFinalPracticalMarks()!=null && !subTo.getFinalPracticalMarks().isEmpty()){
				subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalPracticalMarks());// For To1 subraction
				subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalPracticalMarks());// For To2 subraction
				if (to1.getStuPracIntMark() != null && !StringUtils.isAlpha(to1.getStuPracIntMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuPracIntMark());
				}
				if (to2.getStuPracIntMark() != null && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuPracIntMark());
				}
				if(to1.getStuPracRegMark() != null && !StringUtils.isAlpha(to1.getStuPracRegMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuPracRegMark());
				}
				if(to2.getStuPracRegMark() != null && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuPracRegMark());
				}
			}
			if(subTo.getFinalTheoryMarks()!=null && !subTo.getFinalTheoryMarks().isEmpty()){
				subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalTheoryMarks());// For To1 subraction
				subTotalMarks=subTotalMarks-Double.parseDouble(subTo.getFinalTheoryMarks());// For To2 subraction
				if (to1.getStuTheoryIntMark() != null && !StringUtils.isAlpha(to1.getStuTheoryIntMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuTheoryIntMark());
				}
				if (to2.getStuTheoryIntMark() != null && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuTheoryIntMark());
				}
				if(to1.getStuTheoryRegMark() != null && !StringUtils.isAlpha(to1.getStuTheoryRegMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuTheoryRegMark());
				}
				if(to2.getStuTheoryRegMark() != null && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
					stuTotalMarks=stuTotalMarks-Double.parseDouble(to2.getStuTheoryRegMark());
				}
			}
		}
		// The Real Logic has to implement Here
		if(to1!=null && to2!=null){
			markDetailsTO.setSubjectId(to1.getSubjectId());
			markDetailsTO.setIs_theory_practical(to1.getIs_theory_practical());
			if(isImprovement){
				// If it is improvement then we have to get max between the two records
				// Theory Int Marks
				if(to1.getStuTheoryIntMark()!=null && !to1.getStuTheoryIntMark().isEmpty()){
					if(StringUtils.isAlpha(to1.getStuTheoryIntMark().trim())){
						if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
							markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
						}else{
							markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
						}
					}else{
						if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
							if(Double.parseDouble(to1.getStuTheoryIntMark()) < Double.parseDouble(to2.getStuTheoryIntMark()))
								markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
							else
								markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
						}else{
							markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
						}
					}
				}else{
					if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty()){
						markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
					}
				}
				
				// Practical Int Mark
				if(to1.getStuPracIntMark()!=null && !to1.getStuPracIntMark().isEmpty()){
					if(StringUtils.isAlpha(to1.getStuPracIntMark().trim())){
						if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
							markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
						}else{
							markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
						}
					}else{
						if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
							if(Double.parseDouble(to1.getStuPracIntMark()) < Double.parseDouble(to2.getStuPracIntMark()))
								markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
							else
								markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
						}else{
							stuTotalMarks=stuTotalMarks-Double.parseDouble(to1.getStuPracIntMark());
							markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
						}
					}
				}else{
					if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty()){
						markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
					}
				}
				
				// Theory Reg Mark
				if(to1.getStuTheoryRegMark()!=null && !to1.getStuTheoryRegMark().isEmpty()){
					if(StringUtils.isAlpha(to1.getStuTheoryRegMark().trim())){
						if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
							markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
						}else{
							markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
						}
					}else{
						if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
							if(Double.parseDouble(to1.getStuTheoryRegMark()) < Double.parseDouble(to2.getStuTheoryRegMark()))
								markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
							else
								markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
						}else{
							markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
						}
					}
				}else{
					if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty()){
						markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
					}
				}
				
				
				// practical Reg Mark
				if(to1.getStuPracRegMark()!=null && !to1.getStuPracRegMark().isEmpty()){
					if(StringUtils.isAlpha(to1.getStuPracRegMark().trim())){
						if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
							markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
						}else{
							markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
						}
					}else{
						if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
							if(Double.parseDouble(to1.getStuPracRegMark()) < Double.parseDouble(to2.getStuPracRegMark()))
								markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
							else
								markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
						}else{
							markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
						}
					}
				}else{
					if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty()){
						markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
					}
				}
			}else{
				// If it is supplementary then we have to get latest availablity data
				
				// Theory Int Marks
				if(to2.getStuTheoryIntMark()==null || to2.getStuTheoryIntMark().isEmpty()){
					markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
				}else{
					markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
				}
				
				// Practical Int Mark
				if(to2.getStuPracIntMark()==null || to2.getStuPracIntMark().isEmpty()){
					markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
				}else{
					markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
				}
				
				// Theory Reg Mark
				if(to2.getStuTheoryRegMark()==null || to2.getStuTheoryRegMark().isEmpty() || StringUtils.isAlpha(to2.getStuTheoryRegMark())){
					markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
				}else if(to1.getStuTheoryRegMark()==null || to1.getStuTheoryRegMark().isEmpty() || StringUtils.isAlpha(to1.getStuTheoryRegMark()))
				{
					markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
				}
				else
				{
					if(Double.parseDouble((to1.getStuTheoryRegMark()))>=Double.parseDouble((to2.getStuTheoryRegMark()))){
						markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
					}
					else
						markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
					
				}
				
				
				// practical Reg Mark
				if(to2.getStuPracRegMark()==null || to2.getStuPracRegMark().isEmpty() || StringUtils.isAlpha(to2.getStuPracRegMark())){
					markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
				}else if(to1.getStuPracRegMark()==null || to1.getStuPracRegMark().isEmpty() || StringUtils.isAlpha(to1.getStuPracRegMark())){
					markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
				}
				else
				{
					if(Double.parseDouble((to1.getStuPracRegMark()))>=Double.parseDouble((to2.getStuPracRegMark()))){
						markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
					}
					else
						markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
					
				}
				
			}
			if(subTo.getTheoryIntMin()!=null && !subTo.getTheoryIntMin().isEmpty()){
				if (markDetailsTO.getStuTheoryIntMark() != null){
					if(StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
						markDetailsTO.setIsTheoryFailed(true);
					}
					else if (Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double
							.parseDouble(subTo.getTheoryIntMin())) {
						markDetailsTO.setIsTheoryFailed(true);
					}
				}
				else{
					markDetailsTO.setIsTheoryFailed(true);
				}
			}
			if(subTo.getTheoryRegMin()!=null && !subTo.getTheoryRegMin().isEmpty()){
				if (markDetailsTO.getStuTheoryRegMark() != null){
					if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
						markDetailsTO.setIsTheoryFailed(true);
					}
					else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark()) < Double
							.parseDouble(subTo.getTheoryRegMin())) {
						markDetailsTO.setIsTheoryFailed(true);
					}
				}
				else{
					markDetailsTO.setIsTheoryFailed(true);
				}
			}
			if(subTo.getPracticalIntMin()!=null && !subTo.getPracticalIntMin().isEmpty()){
				if (markDetailsTO.getStuPracIntMark() != null){
					if(StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
						markDetailsTO.setIsPracticalFailed(true);
					}
					else if (Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
							.parseDouble(subTo.getPracticalIntMin())) {
						markDetailsTO.setIsPracticalFailed(true);
					}
				}
				else{
					markDetailsTO.setIsPracticalFailed(true);
				}
			}
			if(subTo.getPracticalRegMin()!=null && !subTo.getPracticalRegMin().isEmpty()){
				if (markDetailsTO.getStuPracRegMark() != null){
					if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
						markDetailsTO.setIsPracticalFailed(true);
					}
					else if (Double.parseDouble(markDetailsTO.getStuPracRegMark()) < Double
							.parseDouble(subTo.getPracticalRegMin())) {
						markDetailsTO.setIsPracticalFailed(true);
					}
				}
				else{
					markDetailsTO.setIsPracticalFailed(true);
				}
			}
			
			if (subTo.getFinalTheoryMin() != null) {
				if(isAddTotal){
					if(subTo.getFinalTheoryMarks()!=null)
						subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
					if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
						stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
					}
					if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
						stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
					}
				}
				if (markDetailsTO.getStuTheoryRegMark() != null
						&& markDetailsTO.getStuTheoryIntMark() != null){
					if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim()) ){
						markDetailsTO.setIsTheoryFailed(true);
					}
					else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark())+ 
							Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double.parseDouble(subTo.getFinalTheoryMin())) {
						markDetailsTO.setIsTheoryFailed(true);
					}
				}
			 }
			if (subTo.getFinalPracticalMin() != null) {
				if(isAddTotal){
					if(subTo.getFinalPracticalMarks()!=null)
						subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
					if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
						stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
					}
					if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
						stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
					}
				}
				if (markDetailsTO.getStuPracRegMark() != null && markDetailsTO.getStuPracIntMark() != null){
					if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
						markDetailsTO.setIsPracticalFailed(true);
					}
					else if (Double.parseDouble(markDetailsTO.getStuPracRegMark())
							+ Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
							.parseDouble(subTo.getFinalPracticalMin())) {
						markDetailsTO.setIsPracticalFailed(true);
					}
				}
			}
		}
		markDetailsTO.setTempStuTotal(stuTotalMarks);
		markDetailsTO.setTempSubTotal(subTotalMarks);
		return markDetailsTO;
	}
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private List<StudentTO> getStudentListForClass(int classId) throws Exception {
		List<StudentTO> studentList=new ArrayList<StudentTO>();
		String query=getCurrentClassQuery(classId);// Getting Current Class Students Query
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Student> currentStudentList=transaction.getDataForQuery(query);
		getFinalStudentsForCurrentClass(currentStudentList,studentList);//Adding current Class Students for StudentList
		String preQuery=getPreviousClassQuery(classId);
		List<Object[]> previousStudentList=transaction.getDataForQuery(preQuery);
		getFinalStudentsForPreviousClass(previousStudentList,studentList);
		return studentList;
	}
	private List<StudentTO> getStudentListForClassExamId(int classId) throws Exception {
		List<StudentTO> studentList=new ArrayList<StudentTO>();
		String query=getCurrentClassQuery(classId);// Getting Current Class Students Query
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Student> currentStudentList=transaction.getDataForQuery(query);
		getFinalStudentsForCurrentClass(currentStudentList,studentList);//Adding current Class Students for StudentList
		String preQuery=getPreviousClassQuery(classId);
		List<Object[]> previousStudentList=transaction.getDataForQuery(preQuery);
		getFinalStudentsForPreviousClass(previousStudentList,studentList);
		return studentList;
	}
	/**
	 * @param previousStudentList
	 * @param studentList
	 * @throws Exception
	 */
	private void getFinalStudentsForPreviousClass(List<Object[]> previousStudentList, List<StudentTO> studentList) throws Exception{
		Map<Integer,StudentTO> studentMap=new HashMap<Integer, StudentTO>();
		if(previousStudentList!=null && !previousStudentList.isEmpty()){
			Iterator<Object[]> preItr=previousStudentList.iterator();
			while (preItr.hasNext()) {
				Object[] obj = (Object[]) preItr.next();
				if(obj[0]!=null && obj[1]!=null){
					if(studentMap.containsKey(Integer.parseInt(obj[0].toString()))){
						StudentTO to=studentMap.remove(Integer.parseInt(obj[0].toString()));
						to.setId(Integer.parseInt(obj[0].toString()));
						if(obj[3]!=null)
						to.setAppliedYear(Integer.parseInt(obj[3].toString()));
						if(obj[2]!=null)
						to.setRegisterNo(obj[2].toString());
						List<SubjectTO> subList=to.getSubjectList();
						List<Integer> subIdList=to.getSubjectIdList();
						Subject subject=(Subject)obj[1];
						SubjectTO subTo=new SubjectTO();
						subTo.setId(subject.getId());
						subTo.setName(subject.getName());
						subList.add(subTo);
						subIdList.add(subject.getId());
						to.setSubjectList(subList);
						to.setSubjectIdList(subIdList);
						studentMap.put(to.getId(),to);
					}else{
						StudentTO to=new StudentTO();
						to.setId(Integer.parseInt(obj[0].toString()));
						if(obj[2]!=null)
							to.setRegisterNo(obj[2].toString());
						if(obj[3]!=null)
							to.setAppliedYear(Integer.parseInt(obj[3].toString()));
						List<SubjectTO> subList=new ArrayList<SubjectTO>();
						List<Integer> subIdList=new ArrayList<Integer>();
						Subject subject=(Subject)obj[1];
						SubjectTO subTo=new SubjectTO();
						subTo.setId(subject.getId());
						subTo.setName(subject.getName());
						subList.add(subTo);
						subIdList.add(subject.getId());
						to.setSubjectIdList(subIdList);
						to.setSubjectList(subList);
						studentMap.put(to.getId(),to);
					}
				}
			}
			studentList.addAll(studentMap.values());
		}
	}
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private String getPreviousClassQuery(int classId)  throws Exception{
		String query="select s.id,subSet.subject,s.registerNo,s.admAppln.appliedYear from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise" +
				" join classSchemewise.curriculumSchemeDuration cd" +
				" join  cd.curriculumSchemeSubjects curSubjects" +
				" join s.studentSubjectGroupHistory subjHist " +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
				" where s.admAppln.isCancelled=0 and s.isAdmitted=1 and subSet.isActive = 1 and subSet.subjectGroup.isActive = 1 and subSet.subject.isActive = 1 " +
				" and classHis.classes.id=" +classId+
				" and s.classSchemewise.classes.id <> "+classId+
				" and classHis.schemeNo=subjHist.schemeNo" +
				" and curSubjects.subjectGroup.id = subjHist.subjectGroup.id";
		return query;
	}

	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private String getCurrentClassQuery(int classId) throws Exception{
		String query="from Student s" +
				" where s.admAppln.isCancelled=0 and s.isAdmitted=1 " +
				" and s.classSchemewise.classes.id="+classId;
		return query;
	}
	/**
	 * @param currentStudentList
	 * @param studentList
	 * @throws Exception
	 */
	private void getFinalStudentsForCurrentClass(List<Student> currentStudentList, List<StudentTO> studentList) throws Exception{
		if(currentStudentList!=null && !currentStudentList.isEmpty()){
			Iterator<Student> itr=currentStudentList.iterator();
			while (itr.hasNext()) {
				Student bo = (Student) itr.next();
				StudentTO to=new StudentTO();
				to.setId(bo.getId());
				to.setAppliedYear(bo.getAdmAppln().getAppliedYear());
				to.setRegisterNo(bo.getRegisterNo());
				Set<ApplicantSubjectGroup> subSet=bo.getAdmAppln().getApplicantSubjectGroups();
				List<SubjectTO> subList=new ArrayList<SubjectTO>();
				List<Integer> subIdList=new ArrayList<Integer>();
				if(subSet!=null && !subSet.isEmpty()){
					Iterator<ApplicantSubjectGroup> subItr=subSet.iterator();
					while (subItr.hasNext()) {
						ApplicantSubjectGroup subGrp = (ApplicantSubjectGroup) subItr.next();
						Set<SubjectGroupSubjects> sub=subGrp.getSubjectGroup().getSubjectGroupSubjectses();
						if (sub!=null && !sub.isEmpty()) {
							Iterator<SubjectGroupSubjects> subGrpSubItr=sub.iterator();
							while (subGrpSubItr.hasNext()) {
								SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subGrpSubItr.next();
								if(subjectGroupSubjects.getIsActive()){
									SubjectTO subTo=new SubjectTO();
									subTo.setId(subjectGroupSubjects.getSubject().getId());
									subTo.setName(subjectGroupSubjects.getSubject().getName());
									subList.add(subTo);
									subIdList.add(subjectGroupSubjects.getSubject().getId());
								}
							}
						}
						
					}
				}
				to.setSubjectList(subList);
				to.setSubjectIdList(subIdList);
				studentList.add(to);
			}
		}
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 */
	public boolean calculateInternalOverAllAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		ExamDefinition exam=new ExamDefinition();
		exam.setId(Integer.parseInt(newUpdateProccessForm.getExamId()));
		List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
		List<StudentOverallInternalMarkDetails> boList=new ArrayList<StudentOverallInternalMarkDetails>();
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		List<Integer> intExamId=transaction.getInternalExamId(Integer.parseInt(newUpdateProccessForm.getExamId()));
		String programTypeQuery=" select c.program.programType.name from Course c ";
		String programType=null;
		if(classList!=null && !classList.isEmpty()){
			Iterator<ClassesTO> itr=classList.iterator();
			while (itr.hasNext()) {
				ClassesTO to = (ClassesTO) itr.next();
				if(programType==null){
					programTypeQuery = programTypeQuery+"where c.id = "+to.getCourseId();
					List list = transaction.getDataByQuery(programTypeQuery);
					programType = (String) list.get(0);
				}
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
					boolean isDeleted = NewUpdateProccessHandler.getInstance().deleteOldInternalOverAllMarks(to.getId(), Integer.parseInt(newUpdateProccessForm.getExamId()));
					if(isDeleted){
						Map<Integer,SubjectRuleSettingsTO> subRuleMap=transaction.getSubjectRuleSettings(to.getCourseId(),to.getYear(),to.getTermNo());
						List<StudentTO> studentList=getStudentListForClass(to.getId());
						if(studentList!=null && !studentList.isEmpty()){
							Iterator<StudentTO> sitr=studentList.iterator();
							while (sitr.hasNext()) {
								StudentTO sto = (StudentTO) sitr.next();
								//if(sto.getId()==7361){//remove this
								if(sto.getSubjectIdList().size()>0){
									Map<String,StudentAttendanceTO> stuAttMap=transaction.getAttendanceForStudent(to.getId(),sto.getId(),sto.getSubjectIdList(),newUpdateProccessForm.getBatchYear(),to.getTermNo(),newUpdateProccessForm.getAcademicYear());								
									Map<Integer,StudentAttendanceTO> stuAssMap=transaction.getAssignmentMarksForStudent(to.getCourseId(),sto.getId(),sto.getSubjectIdList());
									if(sto.getSubjectIdList()!=null && !sto.getSubjectIdList().isEmpty()){
										Iterator<Integer> subItr=sto.getSubjectIdList().iterator();
										while (subItr.hasNext()) {
											Integer subId = (Integer) subItr.next();
											//if(subId==1717){//remove this
											StudentOverallInternalMarkDetails bo=new StudentOverallInternalMarkDetails();
											Student student=new Student();
											student.setId(sto.getId());
											bo.setStudent(student);
											Classes classes=new Classes();
											classes.setId(to.getId());
											bo.setClasses(classes);
											bo.setExam(exam);
											Subject subject=new Subject();
											subject.setId(subId);
											bo.setSubject(subject);
											double theoryAttendance=0;
											double theoryAssignMarks=0;
											double theoryMarks=0;
											double practicalAttendance=0;
											double practicalAssignMarks=0;
											double practicalMarks=0;
											if(subRuleMap.containsKey(subId)){
												SubjectRuleSettingsTO subTo=subRuleMap.get(subId);
												if(subTo.getIsTheoryPractical().equalsIgnoreCase("T")){
													if(subTo.isTheoryAttendanceCheck()){
														theoryAttendance=getAttendanceMarksForSubject(subId,stuAttMap,subTo.getTheoryAttTypeId(),subTo.isTheoryCoLeaveCheck(),subTo.isTheoryLeaveCheck(),to,transaction);
														bo.setTheoryTotalAttendenceMarks(String.valueOf(theoryAttendance));
													}
													if(subTo.isTheoryAssignmentCheck()){
														theoryAssignMarks=getAssignMarksForSubject(subId,stuAssMap,"t");
														bo.setTheoryTotalAssignmentMarks(String.valueOf(theoryAssignMarks));
													}
													String totmarks="";
													List<MarksEntryDetails> marksEntryDetails = getStudentMarksForSubjectNew(subId,sto.getId(),intExamId,to,"t",transaction,subTo.getTheoryBest(),subTo.isTheoryIndCheck(),exam.getId(),newUpdateProccessForm);
													if(marksEntryDetails!=null && marksEntryDetails.size()!=0){														
														for (Iterator iterator = marksEntryDetails.iterator(); iterator.hasNext();) {
															Object[] obj = (Object[]) iterator.next();
															if(obj[1].toString().equalsIgnoreCase("WithHeld"))
																totmarks=obj[1].toString();
														}
													}													
													
													if(subTo.isTheoryInteralCheck() && !totmarks.equalsIgnoreCase("WithHeld")){
														theoryMarks=getStudentMarksForSubject(subId,sto.getId(),intExamId,to,"t",transaction,subTo.getTheoryBest(),subTo.isTheoryIndCheck(),exam.getId(),newUpdateProccessForm);
													}
													double totalTheoryMark=0;
													totalTheoryMark = theoryAttendance+theoryAssignMarks+theoryMarks;
													if(!totmarks.equalsIgnoreCase("WithHeld")){
														bo.setTheoryTotalSubInternalMarks(String.valueOf(theoryMarks));
													}else {
														bo.setTheoryTotalSubInternalMarks(totmarks);
													}
													if(programType.equalsIgnoreCase("UG"))
														bo.setTheoryTotalMarks(String.valueOf(Math.ceil(totalTheoryMark)));
													else
														bo.setTheoryTotalMarks(String.valueOf(Math.round(totalTheoryMark)));
													bo.setIsGracing(newUpdateProccessForm.getIsGracing());
												} 
												else if(subTo.getIsTheoryPractical().equalsIgnoreCase("P")){
													if(subTo.isPracticalAttendanceCheck()){
														practicalAttendance=getAttendanceMarksForSubject(subId,stuAttMap,subTo.getPracticalAttTypeId(),subTo.isPracticalCoLeaveCheck(),subTo.isPracticalLeaveCheck(),to,transaction);
														bo.setPracticalTotalAttendenceMarks(String.valueOf(practicalAttendance));
													}
													if(subTo.isPracticalAssigntmentCheck()){
														practicalAssignMarks=getAssignMarksForSubject(subId,stuAssMap,"p");
														bo.setPracticalTotalAssignmentMarks(String.valueOf(practicalAssignMarks));
													}
													if(subTo.isPracticalInternalCheck()){
														practicalMarks=getStudentMarksForSubject(subId,sto.getId(),intExamId,to,"p",transaction,subTo.getPracticalBest(),subTo.isPracticalIndCheck(),exam.getId(),newUpdateProccessForm);
													}
													double totalPractical=0;
													totalPractical = practicalAttendance+practicalAssignMarks+practicalMarks;
													bo.setPracticalTotalSubInternalMarks(String.valueOf(practicalMarks));
													if(programType.equalsIgnoreCase("PG"))
													bo.setPracticalTotalMarks(String.valueOf(Math.round(totalPractical)));
													else
														bo.setPracticalTotalMarks(String.valueOf(Math.ceil(totalPractical)));
													bo.setIsGracing(newUpdateProccessForm.getIsGracing());
												}
												else if(subTo.getIsTheoryPractical().equalsIgnoreCase("B")){
													if(subTo.isTheoryAttendanceCheck()){
														theoryAttendance=getAttendanceMarksForSubject(subId,stuAttMap,subTo.getTheoryAttTypeId(),subTo.isTheoryCoLeaveCheck(),subTo.isTheoryLeaveCheck(),to,transaction);
														bo.setTheoryTotalAttendenceMarks(String.valueOf(theoryAttendance));
													}
													if(subTo.isTheoryAssignmentCheck()){
														theoryAssignMarks=getAssignMarksForSubject(subId,stuAssMap,"t");
														bo.setTheoryTotalAssignmentMarks(String.valueOf(theoryAssignMarks));
													}
													if(subTo.isTheoryInteralCheck()){
														theoryMarks=getStudentMarksForSubject(subId,sto.getId(),intExamId,to,"t",transaction,subTo.getTheoryBest(),subTo.isTheoryIndCheck(),exam.getId(),newUpdateProccessForm);
														bo.setIsGracing(newUpdateProccessForm.getIsGracing());
													}
													bo.setTheoryTotalSubInternalMarks(String.valueOf(theoryMarks));
													if(subTo.isPracticalAttendanceCheck()){
														practicalAttendance=getAttendanceMarksForSubject(subId,stuAttMap,subTo.getPracticalAttTypeId(),subTo.isPracticalCoLeaveCheck(),subTo.isPracticalLeaveCheck(),to,transaction);
														bo.setPracticalTotalAttendenceMarks(String.valueOf(practicalAttendance));
													}
													if(subTo.isPracticalAssigntmentCheck()){
														practicalAssignMarks=getAssignMarksForSubject(subId,stuAssMap,"p");
														bo.setPracticalTotalAssignmentMarks(String.valueOf(practicalAssignMarks));
													}
													if(subTo.isPracticalInternalCheck()){
														practicalMarks=getStudentMarksForSubject(subId,sto.getId(),intExamId,to,"p",transaction,subTo.getPracticalBest(),subTo.isPracticalIndCheck(),exam.getId(),newUpdateProccessForm);
														if(bo.getIsGracing()){
															bo.setIsGracing(true);
														}else{
															bo.setIsGracing(newUpdateProccessForm.getIsGracing());
														}
													}
													bo.setPracticalTotalSubInternalMarks(String.valueOf(practicalMarks));
													// new  modification
													
													double totalTheoryMark=0;
													double totalPractical=0;
													totalTheoryMark = theoryAttendance+theoryAssignMarks+theoryMarks;
													totalPractical = practicalAttendance+practicalAssignMarks+practicalMarks;
													if(programType.equalsIgnoreCase("PG"))
														bo.setTheoryTotalMarks(String.valueOf(Math.round(totalTheoryMark)));
													else
														bo.setTheoryTotalMarks(String.valueOf(Math.ceil(totalTheoryMark)));
													if(programType.equalsIgnoreCase("PG"))
														bo.setPracticalTotalMarks(String.valueOf(Math.round(totalPractical)));
													else
														bo.setPracticalTotalMarks(String.valueOf(Math.ceil(totalPractical)));
												}
												boList.add(bo);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return transaction.saveInternalOverAll(boList);
	}
	/**
	 * @param subId
	 * @param studentId
	 * @param intExamId
	 * @param to
	 * @param subType
	 * @param transaction
	 * @return
	 * @throws Exception
	 */
	private double getStudentMarksForSubject(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,INewUpdateProccessTransaction transaction,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		
		return transaction.getStudentMarksForSubject(subId,studentId,intExamId,to,subType,limit,isInd,examId,newUpdateProccessForm);
	}
	
	
private List<MarksEntryDetails> getStudentMarksForSubjectNew(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,INewUpdateProccessTransaction transaction,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		
	List<MarksEntryDetails> bo= transaction.getStudentMarksForSubjectNew(subId,studentId,intExamId,to,subType,limit,isInd,examId,newUpdateProccessForm);
	return bo;
	}
	/**
	 * @param subId
	 * @param stuAssMap
	 * @param subType
	 * @return
	 * @throws Exception
	 */
	private double getAssignMarksForSubject(Integer subId,Map<Integer, StudentAttendanceTO> stuAssMap, String subType) throws Exception {
		double assMarks=0;
		if(stuAssMap.containsKey(subId)){
			StudentAttendanceTO to=stuAssMap.get(subId);
			if(subType.equalsIgnoreCase("t")){
				assMarks=to.getTheoryAssMarks();
			}
			if(subType.equalsIgnoreCase("p")){
				assMarks=to.getPracticalAssMarks();
			}
		}
		return assMarks;
	}
	/**
	 * @param subId
	 * @param stuAttMap
	 * @param theoryAttTypeId
	 * @param theoryCoLeaveCheck
	 * @param theoryLeaveCheck
	 * @param to
	 * @return
	 * @throws Exception
	 */
	private double getAttendanceMarksForSubject(Integer subId, Map<String, StudentAttendanceTO> stuAttMap, 
			int attTypeId, boolean coLeaveCheck, boolean leaveCheck, ClassesTO to,INewUpdateProccessTransaction transaction) throws Exception {
		double points=0;
		if(stuAttMap.containsKey(subId+"_"+attTypeId)){
			StudentAttendanceTO sto=stuAttMap.get(subId+"_"+attTypeId);
			float hoursHeld=sto.getSubjectHoursHeld();
			float hoursAtt=sto.getPresentHoursAtt();
			if(coLeaveCheck)
				hoursAtt=hoursAtt+sto.getCoLeaveHoursAtt();
			if(leaveCheck)
				hoursAtt=hoursAtt+sto.getLeaveHoursAtt();
			float percentage=(hoursAtt/hoursHeld)*100;
			percentage=CommonUtil.roundToDecimal(percentage, 2);
			points=transaction.getAttendanceMarksForPercentage(to.getCourseId(),subId,percentage);
		}
		return points;
	}
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public boolean calculateRegularOverAllAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		
		try {
			ExamDefinition exam=new ExamDefinition();
			int examId=Integer.parseInt(newUpdateProccessForm.getExamId());
			exam.setId(examId);
			List<String> errorList=new ArrayList<String>();
			List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
			List<StudentFinalMarkDetails> boList=new ArrayList<StudentFinalMarkDetails>();
			INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
			if(classList!=null && !classList.isEmpty()){
				Iterator<ClassesTO> itr=classList.iterator();
				while (itr.hasNext()) {
					ClassesTO to = (ClassesTO) itr.next();
					Classes classes=new Classes();
					classes.setId(to.getId());
					//int SemNo=to.getTermNo();
					if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
					//if(to.getId()==441){// remove this
						
						Map<Integer,SubjectRuleSettingsTO> subRuleMap=transaction.getSubjectRuleSettingsForRegularOverAll(to.getCourseId(),to.getYear(),to.getTermNo());
						//raghu
						//boolean isPGStudent = transaction.isPgStudent(to.getCourseId());
						
						List<StudentTO> studentList=getStudentListForClass(to.getId());
//						System.out.println("class Id:"+to.getId());
						if(studentList!=null && !studentList.isEmpty()){
							Iterator<StudentTO> sitr=studentList.iterator();
							while (sitr.hasNext()) {
								StudentTO sto=sitr.next();
								int stuId= sto.getId();
								//if(sto.getId()==2265){
								//System.out.println("student Id"+sto.getId());
								//}
								
								if(sto.getSubjectIdList()!=null && !sto.getSubjectIdList().isEmpty()){
									Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), newUpdateProccessForm);
									if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty()){
										Iterator<Integer> subItr=sto.getSubjectIdList().iterator();
										while (subItr.hasNext()) {
											Integer subId = (Integer) subItr.next();
											//if(subId== 91){
												
												//bcz of both subject
												String theoryMarks=null;
												String practicalMarks=null;
												String result=null;
												StudentFinalMarkDetails bo=null; 
												
											if(subRuleMap.containsKey(subId) && stuMarksMap.containsKey(subId)){
												SubjectRuleSettingsTO subSetTo=subRuleMap.get(subId);
												List<StudentMarksTO> marksList=stuMarksMap.get(subId);
												if(subSetTo.isTheoryRegularCheck() || subSetTo.isPracticalRegularCheck()){
													bo=getRegularFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,newUpdateProccessForm.getUserId());
													bo.setExam(exam);
													theoryMarks=bo.getStudentTheoryMarks();
													practicalMarks=bo.getStudentPracticalMarks();
													result=bo.getPassOrFail();
													//boList.add(bo);
												}else if((subSetTo.isTheoryAnsCheck() && subSetTo.isTheoryEvalCheck()) || (subSetTo.isPracticalAnsCheck() && subSetTo.isPracticalEvalCheck())){
													int noOfAns=0;
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfAns=noOfAns+subSetTo.getTheoryNoOfAns();
													}
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfAns=noOfAns+subSetTo.getPracticalNoOfEval();
													}
													int noOfEvaluation=0;
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfEvaluation=noOfEvaluation+subSetTo.getTheoryNoOfEval();
													}
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfEvaluation=noOfEvaluation+subSetTo.getPracticalNoOfEval();
													}
													
													boolean updateMark = true;
													//raghu
													/*if(isPGStudent){
														if(noOfEvaluation<3){
															if(!(marksList.size()== noOfAns*noOfEvaluation)){
																//updateMark = false;
															}
														}
														else{
															if((marksList.size()< noOfAns*2)){
																//updateMark = false;
															}
														}
													}*/
													
													if(updateMark/*marksList.size()==(noOfAns*noOfEvaluation)*/){
														bo=getRegularEvalAndAnsFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,newUpdateProccessForm.getUserId());
														bo.setExam(exam);
														//boList.add(bo);
													}else{
														errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
													}
													
												}else if(subSetTo.isTheoryAnsCheck() || subSetTo.isPracticalAnsCheck()){
													int noOfAns=0;
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfAns=noOfAns+subSetTo.getTheoryNoOfAns();
													}
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfAns=noOfAns+subSetTo.getPracticalNoOfEval();
													}
													if(marksList.size()==noOfAns){
														bo=getRegularAnsFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,newUpdateProccessForm.getUserId());
														bo.setExam(exam);
														//boList.add(bo);
													}else{
														errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
													}
												}
												
												
												//bcz of both subject
													
												if(subSetTo.isTheoryEvalCheck() || subSetTo.isPracticalEvalCheck()){
													
													int noOfEvaluation=0;
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfEvaluation=noOfEvaluation+subSetTo.getTheoryNoOfEval();
													}
													if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
														noOfEvaluation=noOfEvaluation+subSetTo.getPracticalNoOfEval();
													}
													boolean updateMark = true;
													
													//raghu
													/*if(isPGStudent){
														if(noOfEvaluation<3){
															if(!(marksList.size()== noOfEvaluation)){
																//updateMark = false;
															}
														}
														else{
															if(marksList.size() < 2){
																//updateMark = false;
															}
														}
													}*/
													
													if(updateMark/*marksList.size()==noOfEvaluation*/){
														bo=getRegularEvalFinalMarkDetailsBo(subSetTo,marksList,to,sto.getId(),exam,subId,classes,newUpdateProccessForm.getUserId());
														bo.setExam(exam);
														
														if(bo.getStudentTheoryMarks()==null && theoryMarks!=null){
															bo.setStudentTheoryMarks(theoryMarks);
														}
														if(bo.getStudentPracticalMarks()==null && practicalMarks!=null){
															bo.setStudentPracticalMarks(practicalMarks);
														}
														
														if(result!=null && result.equalsIgnoreCase("fail")){
															bo.setPassOrFail("fail");
														}
														//boList.add(bo);
													}else{
														errorList.add(sto.getRegisterNo()+"-"+subSetTo.getSubjectName());
													}
													
												}
													
													
												//bcz of both subject
													boList.add(bo);
												
												
											}
										}
									}
									}	
								}
							//}//remove this stu id
							//}//remove this
							}
						}
					//}//remove THis
				//	}
					
				}
			}
			if(errorList.isEmpty() && (newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())){
				return transaction.saveRegularOverAll(boList);
			}else{
				newUpdateProccessForm.setErrorList(errorList);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * @param subSetTo
	 * @param marksList
	 * @param to
	 * @param studentId
	 * @param exam
	 * @param subId
	 * @param classes
	 * @param userId
	 * @return
	 */
	private StudentFinalMarkDetails getRegularEvalAndAnsFinalMarkDetailsBo(
			SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList,
			ClassesTO to, int studentId, ExamDefinition exam, Integer subId,
			Classes classes, String userId) {
		StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
		StudentMarksTO mto=new StudentMarksTO();
		Map<String,Double> marksMap=new HashMap<String, Double>();
		boolean isPass=true;
		String theoryAlphaMarks="";
		String practicalAlphaMarks="";
		double reviewerTheoryMark = 0;
		double reviewePracticalMark = 0;
		
		boolean isGracingForEvl1=false;
		boolean isGracingForEvl2=false;
		
		if(marksList!=null && !marksList.isEmpty()){
			Iterator<StudentMarksTO> itr=marksList.iterator();
			while (itr.hasNext()) {
				StudentMarksTO sto = (StudentMarksTO) itr.next();
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					double theoryMarks=0;
					if(marksMap.containsKey(sto.getEvaId()+"_t")){
						theoryMarks=marksMap.remove(sto.getEvaId()+"_t");
					}
					if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
							isPass=false;
							if(theoryAlphaMarks.isEmpty()){
								theoryAlphaMarks=sto.getTheoryMarks();
							}
						}else{
							theoryMarks= theoryMarks+Double.parseDouble(sto.getTheoryMarks());
						}
					}
					marksMap.put(sto.getEvaId()+"_t",theoryMarks);
					if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty()){
						if(subSetTo.getReviewerId() == Integer.parseInt(sto.getEvaId())){
							if(theoryMarks > 0){
								reviewerTheoryMark = theoryMarks;
							}
						}
					}
				}
				if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
					double practicalMarks=0;
					if(marksMap.containsKey(sto.getEvaId()+"_p")){
						practicalMarks=marksMap.remove(sto.getEvaId()+"_p");
					}
					if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty()){
						if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
							isPass=false;
							if(practicalAlphaMarks.isEmpty()){
								practicalAlphaMarks=sto.getPracticalMarks();
							}
						}else{
							practicalMarks= practicalMarks+Double.parseDouble(sto.getPracticalMarks());
						}
					}
					if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty()){
						if(subSetTo.getReviewerId() == Integer.parseInt(sto.getEvaId())){
							if(practicalMarks > 0){
								reviewePracticalMark = practicalMarks;
							}
						}
					}
					marksMap.put(sto.getEvaId()+"_p",practicalMarks);
				}
				// /* code added by chandra
				if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("1")){
					if(sto.getIsGracing() != null && sto.getIsGracing())
						isGracingForEvl1=true;
				}else if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("2")){
					if(sto.getIsGracing() != null && sto.getIsGracing())
						isGracingForEvl2=true;
				}
				// */
			}
		}
		List<Double> theoryList=new ArrayList<Double>();
		List<Double> practicalList=new ArrayList<Double>();
		for (Map.Entry<String, Double> entry : marksMap.entrySet()) {
			if(entry.getKey().contains("_t")){
				theoryList.add(entry.getValue());
			}
			if(entry.getKey().contains("_p")){
				practicalList.add(entry.getValue());
			}
		}
		
		double theoryMarks=0;
		double practicalMarks=0;
		if(isPass){
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(reviewerTheoryMark > 0){
					theoryMarks = reviewerTheoryMark;
				}
				else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Highest")){
					theoryMarks=Collections.max(theoryList);
				}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Lowest")){
					theoryMarks=Collections.min(theoryList);
				}else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Average")){
					int count=0;
					double tMarks=0;
					Iterator<Double> thItr=theoryList.iterator();
					while (thItr.hasNext()) {
						tMarks+= (Double) thItr.next();
						count+=1;
					}
					theoryMarks=tMarks/count;
				}
			}
			if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
				if(reviewePracticalMark > 0){
					practicalMarks = reviewePracticalMark; 
				}
				else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Highest")){
					practicalMarks=Collections.max(practicalList);
				}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Lowest")){
					practicalMarks=Collections.min(practicalList);
				}else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Average")){
					int count=0;
					double pMarks=0;
					Iterator<Double> thItr=practicalList.iterator();
					while (thItr.hasNext()) {
						pMarks+= (Double) thItr.next();
						count+=1;
					}
					practicalMarks=pMarks/count;
				}
			}
		}
		mto.setTheoryMarks(String.valueOf(theoryMarks));
		mto.setPracticalMarks(String.valueOf(practicalMarks));
		Student student=new Student();
		student.setId(studentId);
		bo.setStudent(student);
		bo.setClasses(classes);
		Subject subject=new Subject();
		subject.setId(subId);
		bo.setSubject(subject);
		bo.setCreatedBy(userId);
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(userId);
		bo.setLastModifiedDate(new Date());
		
		if(subSetTo.getTheoryEseMinMarks()>0)
			bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
		if(subSetTo.getPracticalEseMinMarks()>0)
			bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
		if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
			if(!isPass){
				bo.setPassOrFail("fail");
				bo.setStudentTheoryMarks(theoryAlphaMarks);
			}else{
				double tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
				if(tMarks<subSetTo.getTheoryEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
				if(!avoidExamIds.contains(exam.getId())){
					bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
				}else{
					bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
				}
			}
		}
		if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
			if(!isPass){
				bo.setPassOrFail("fail");
				bo.setStudentPracticalMarks(practicalAlphaMarks);
			}else{
				double pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
				if(pMarks<subSetTo.getPracticalEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
//				
				if(!avoidExamIds.contains(exam.getId()))
					bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
				else
					bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
			}
		}
		// /* code added by chandra
		if(isGracingForEvl1==true || isGracingForEvl2==true){
			bo.setIsGracing(true);
		}else{
			bo.setIsGracing(false);
		}
		// */
		return bo;
	}
	/**
	 * @param subSetTo
	 * @param marksList
	 * @param to
	 * @param id
	 * @param exam
	 * @param subId
	 * @param classes
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private StudentFinalMarkDetails getRegularAnsFinalMarkDetailsBo(SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList, ClassesTO to, int studentId, ExamDefinition exam, 
			Integer subId, Classes classes, String userId) throws Exception {
		StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
		StudentMarksTO mto=new StudentMarksTO();
		double theoryMarks=0;
		double practicalMarks=0;
		boolean isPass=true;
		String theoryAlphaMarks="";
		String practicalAlphaMarks="";
		
		boolean isGracingForEvl1=false;
		boolean isGracingForEvl2=false;
		
		if(marksList!=null && !marksList.isEmpty()){
			Iterator<StudentMarksTO> itr=marksList.iterator();
			while (itr.hasNext()) {
				StudentMarksTO sto = (StudentMarksTO) itr.next();
				if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()){
					if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
						isPass=false;
						if(theoryAlphaMarks.isEmpty())
							theoryAlphaMarks=sto.getTheoryMarks();
					}else{
						theoryMarks=theoryMarks+Double.parseDouble(sto.getTheoryMarks());
					}
				}
				if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty()){
					if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
						isPass=false;
						if(practicalAlphaMarks.isEmpty())
							practicalAlphaMarks=sto.getPracticalMarks();
					}else{
						practicalMarks=practicalMarks+Double.parseDouble(sto.getPracticalMarks());
					}
				}
				// /* code added by chandra
				if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("1")){
					if(sto.getIsGracing() != null && sto.getIsGracing())
						isGracingForEvl1=true;
				}else if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("2")){
					if(sto.getIsGracing() != null && sto.getIsGracing())
						isGracingForEvl2=true;
				}
				// */
			}
		}
		mto.setTheoryMarks(String.valueOf(theoryMarks));
		mto.setPracticalMarks(String.valueOf(practicalMarks));
		Student student=new Student();
		student.setId(studentId);
		bo.setStudent(student);
		bo.setClasses(classes);
		Subject subject=new Subject();
		subject.setId(subId);
		bo.setSubject(subject);
		bo.setCreatedBy(userId);
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(userId);
		bo.setLastModifiedDate(new Date());
		
		if(subSetTo.getTheoryEseMinMarks()>0)
			bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
		if(subSetTo.getPracticalEseMinMarks()>0)
			bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
		if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
			if(!isPass){
				bo.setPassOrFail("fail");
				bo.setStudentTheoryMarks(theoryAlphaMarks);
			}else{
				double tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
				if(tMarks<subSetTo.getTheoryEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
//				bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
				if(!avoidExamIds.contains(exam.getId()))
					bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
				else
					bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
			}
		}
		if(subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")){
			if(!isPass){
				bo.setPassOrFail("fail");
				bo.setStudentPracticalMarks(practicalAlphaMarks);
			}else{
				double pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
				if(pMarks<subSetTo.getPracticalEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
//				bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
				if(!avoidExamIds.contains(exam.getId()))
					bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
				else
					bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
					
			}
		}
		// /* code added by chandra
		if(isGracingForEvl1==true || isGracingForEvl2==true){
			bo.setIsGracing(true);
		}else{
			bo.setIsGracing(false);
		}
		// */
		return bo;
	}
	/**
	 * @param subSetTo
	 * @param marksList
	 * @param to
	 * @param studentId
	 * @param exam
	 * @param subId
	 * @param classes
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private StudentFinalMarkDetails getRegularEvalFinalMarkDetailsBo(SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList, ClassesTO to, int studentId, ExamDefinition exam, Integer subId, Classes classes, String userId) throws Exception {
		StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
		StudentMarksTO mto=new StudentMarksTO();
		double theoryMarks=0;
		double practicalMarks=0;
		
		double theoryMarks1=0;
		double practicalMarks1=0;
		double theoryMarks2=0;
		double practicalMarks2=0;
		double theoryMarks3=0;
		double practicalMarks3=0;
		double dif1=0;
		double dif2=0;
		
		boolean isPassT=true;
		boolean isPassP=true;
		List<Double> theoryList=new ArrayList<Double>();
		List<Double> practicalList=new ArrayList<Double>();
		double reviewerTheoryMark = 0;
		double reviewerPracticalMark = 0;
		int count=0;
		int countP=0;
		int countT=0;
		String theoryAlphaMarks="";
		String practicalAlphaMarks="";
		
		boolean isGracingForEvl1=false;
		boolean isGracingForEvl2=false;
		
		boolean isThirdEvalT=false;
		boolean isThirdEvalP=false;
		
		//if(studentId==5441){
		if(marksList!=null && !marksList.isEmpty()){
			Iterator<StudentMarksTO> itr=marksList.iterator();
			while (itr.hasNext()) {
				StudentMarksTO sto = (StudentMarksTO) itr.next();
				count+=1;
				if(sto.getTheoryMarks()!=null && !sto.getTheoryMarks().isEmpty()  && subSetTo.isTheoryEvalCheck() && sto.getEvaId()!=null){
					if(!CommonUtil.isValidDecimal(sto.getTheoryMarks())){
						isPassT=false;
						if(theoryAlphaMarks.isEmpty()){
							theoryAlphaMarks=sto.getTheoryMarks();
						}
					}else {
						
						//raghu
						countT+=1;
						if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==1){
							theoryMarks1=theoryMarks1+Double.parseDouble(sto.getTheoryMarks());
							
						}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==2){
							theoryMarks2=theoryMarks2+Double.parseDouble(sto.getTheoryMarks());
							
						}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==3){
							isThirdEvalT=true;
							theoryMarks3=theoryMarks3+Double.parseDouble(sto.getTheoryMarks());
							
						}
						theoryMarks=theoryMarks+Double.parseDouble(sto.getTheoryMarks());
						theoryList.add(Double.parseDouble(sto.getTheoryMarks()));
						if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty()){
							if(subSetTo.getReviewerId() == Integer.parseInt(sto.getEvaId())){
								/*if(Double.parseDouble(sto.getTheoryMarks()) > 0){
									reviewerTheoryMark = Double.parseDouble(sto.getTheoryMarks());
								}*/
								isThirdEvalT=true;
							}
						}
					}
				}
				
				
				if(sto.getPracticalMarks()!=null && !sto.getPracticalMarks().isEmpty() && subSetTo.isPracticalEvalCheck() && sto.getEvaId()!=null){
					if(!CommonUtil.isValidDecimal(sto.getPracticalMarks())){
						isPassP=false;
						if(practicalAlphaMarks.isEmpty()){
							practicalAlphaMarks=sto.getPracticalMarks();
						}
					}else {
						//raghu
						countP+=1;
						if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==1){
							practicalMarks1=practicalMarks1+Double.parseDouble(sto.getPracticalMarks());
							
						}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==2){
							practicalMarks2=practicalMarks2+Double.parseDouble(sto.getPracticalMarks());
							
						}else if(sto.getEvaId()!=null && Integer.parseInt(sto.getEvaId())==3){
							isThirdEvalP=true;
							practicalMarks3=practicalMarks3+Double.parseDouble(sto.getPracticalMarks());
							
						}
						practicalMarks=practicalMarks+Double.parseDouble(sto.getPracticalMarks());
						practicalList.add(Double.parseDouble(sto.getPracticalMarks()));
						if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty()){
							if(subSetTo.getReviewerId() == Integer.parseInt(sto.getEvaId())){
								/*if(Double.parseDouble(sto.getPracticalMarks()) > 0){
									reviewerPracticalMark = Double.parseDouble(sto.getPracticalMarks());
								}*/
								isThirdEvalP=true;
							}
						}
					}
				}
				// /* code added by chandra
				if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("1")){
					if(sto.getIsGracing() != null && sto.getIsGracing())
						isGracingForEvl1=true;
				}else if(sto.getEvaId()!=null && !sto.getEvaId().isEmpty() && sto.getEvaId().equalsIgnoreCase("2")){
					if(sto.getIsGracing() != null && sto.getIsGracing())
						isGracingForEvl2=true;
				}
				// */
			}
		}
		
		
		if(isPassT){
			
			//bcz of both subject
			if((subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")) && (subSetTo.isTheoryEvalCheck())){
				//if(reviewerTheoryMark > 0){
				if(isThirdEvalT){	
					//theoryMarks = reviewerTheoryMark;
					//raghu code changed
					
					if(theoryMarks3<theoryMarks1){
						dif1=theoryMarks1-theoryMarks3;
					}else{
						dif1=theoryMarks3-theoryMarks1;
					}					
					if(theoryMarks3<theoryMarks2){
						dif2=theoryMarks2-theoryMarks3;
					}else{
						dif2=theoryMarks3-theoryMarks2;
					}
					
					if(dif1==dif2){
						if(theoryMarks2<=theoryMarks1){
							theoryMarks=(theoryMarks3+theoryMarks1)/2;
						}else{
							theoryMarks=(theoryMarks3+theoryMarks2)/2;
						}
					}else if(dif1<dif2){						
						theoryMarks=(theoryMarks3+theoryMarks1)/2;
					}else{						
						theoryMarks=(theoryMarks2+theoryMarks3)/2;
					}
				}
				else if(subSetTo.getTheoryTypeOfEval()!=null  ){
					if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Highest"))
						theoryMarks=Collections.max(theoryList);
					else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Lowest"))
						theoryMarks=Collections.min(theoryList);
					else if(subSetTo.getTheoryTypeOfEval().equalsIgnoreCase("Average")){
						//raghu

						if(count==countT){
							if(count>0 )
								theoryMarks=theoryMarks/count;
						}else{
							if(countT>0 )
								theoryMarks=theoryMarks/countT;
						}
					}

				}
				
			}
			
			
		}
		
		
		if(isPassP){
			//bcz of both subject
			if((subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")) && (subSetTo.isPracticalEvalCheck())){
				//if(reviewerPracticalMark > 0){
				if(isThirdEvalP){
					//practicalMarks = reviewerPracticalMark;
					//raghu code changed
					
					if(practicalMarks3<practicalMarks1){
						dif1=practicalMarks1-practicalMarks3;
						System.out.println("dif1=first"+dif1);
					}else{
						dif1=practicalMarks3-practicalMarks1;
						System.out.println("dif1=second"+dif1);
					}
					
					
					if(practicalMarks3<practicalMarks2){
						dif2=practicalMarks2-practicalMarks3;
						System.out.println("dif2=first"+dif2);
					}else{
						dif2=practicalMarks3-practicalMarks2;
						System.out.println("dif2=second"+dif2);
					}
					
					

					if(dif1==dif2){
						if(practicalMarks2<=practicalMarks1){
							practicalMarks=(practicalMarks3+practicalMarks1)/2;
						}else{
							practicalMarks=(practicalMarks3+practicalMarks2)/2;
						}
						System.out.println("practicalMarks=first"+practicalMarks);
					}else if(dif1<dif2){
						
							practicalMarks=(practicalMarks3+practicalMarks1)/2;
						
						System.out.println("practicalMarks=first"+practicalMarks);
					}else{
							practicalMarks=(practicalMarks2+practicalMarks3)/2;
						
						System.out.println("practicalMarks=second"+practicalMarks);
					}
					
				}
				
				else if(subSetTo.getPracticalTypeOfEval()!=null){
					if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Highest"))
						practicalMarks=Collections.max(practicalList);
					else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Lowest"))
						practicalMarks=Collections.min(practicalList);
					else if(subSetTo.getPracticalTypeOfEval().equalsIgnoreCase("Average")){
						//raghu
						if(count==countP){
							if(count>0)
							practicalMarks=practicalMarks/count;
						}else{
							if(countP>0)
							practicalMarks=practicalMarks/countP;
						}
					}

				}
				
				
				
			}
		}
		
		if(CMSConstants.CLASS_IDS_2014.contains(classes.getId())){
			if(isThirdEvalT)
				theoryMarks = theoryMarks3;
			if(isThirdEvalP)	
				practicalMarks=practicalMarks3;
		}
		mto.setTheoryMarks(String.valueOf(theoryMarks));
		mto.setPracticalMarks(String.valueOf(practicalMarks));
		Student student=new Student();
		student.setId(studentId);
		bo.setStudent(student);
		bo.setClasses(classes);
		Subject subject=new Subject();
		subject.setId(subId);
		bo.setSubject(subject);
		bo.setCreatedBy(userId);
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(userId);
		bo.setLastModifiedDate(new Date());
		
		if(subSetTo.getTheoryEseMinMarks()>0)
			bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
		if(subSetTo.getPracticalEseMinMarks()>0)
			bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
		
		if((subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")) && (subSetTo.isTheoryEvalCheck())){
			if(!isPassT){
				bo.setPassOrFail("fail");
				bo.setStudentTheoryMarks(theoryAlphaMarks);
			}else{
				double tMarks =0;
				if(subSetTo.getTheoryEseEnteredMaxMarks()>0 && subSetTo.getTheoryEseMaxMarks()>0)
				 tMarks= (Double.parseDouble(mto.getTheoryMarks())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
				if(tMarks<subSetTo.getTheoryEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
//				bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
				if(!avoidExamIds.contains(exam.getId()))
					if(!String.valueOf(tMarks).equalsIgnoreCase("NaN"))
					bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(tMarks)))));
				else
					if(!String.valueOf(tMarks).equalsIgnoreCase("NaN"))
					bo.setStudentTheoryMarks(String.valueOf(Math.round(tMarks)));
					
			}
		}
		
		if((subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")) && (subSetTo.isPracticalEvalCheck())){
			if(!isPassP){
				bo.setPassOrFail("fail");
				bo.setStudentPracticalMarks(practicalAlphaMarks);
			}else{
				double pMarks=0;
				if(subSetTo.getPracticalEseEnteredMaxMarks()>0 && subSetTo.getPracticalEseMaxMarks()>0)
				pMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
				if(pMarks<subSetTo.getPracticalEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
//				bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
				if(!avoidExamIds.contains(exam.getId()))
					bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(pMarks)))));
				else
					bo.setStudentPracticalMarks(String.valueOf(Math.round(pMarks)));
					
			}
		}
		// /* code added by chandra
		if(isGracingForEvl1==true || isGracingForEvl2==true){
			bo.setIsGracing(true);
		}else{
			bo.setIsGracing(false);
		}
		    bo.setIsRevaluationModeration(false);
		//}
		// */
		return bo;
	}
	/**
	 * @param subSetTo
	 * @param marksList
	 * @param to
	 * @param id
	 * @param exam
	 * @param subId
	 * @return
	 * @throws Exception
	 */
	private StudentFinalMarkDetails getRegularFinalMarkDetailsBo( SubjectRuleSettingsTO subSetTo, List<StudentMarksTO> marksList, ClassesTO to, int studentId, ExamDefinition exam, Integer subId,Classes classes,String userId) throws Exception{
		StudentFinalMarkDetails bo=new StudentFinalMarkDetails();
		//StudentMarksTO mto=null;
		//mto=marksList.get(0);
		Student student=new Student();
		student.setId(studentId);
		bo.setStudent(student);
		bo.setClasses(classes);
		Subject subject=new Subject();
		subject.setId(subId);
		bo.setSubject(subject);
		bo.setCreatedBy(userId);
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(userId);
		bo.setLastModifiedDate(new Date());
		
		
		
		//if(studentId== 5441){
		if(subSetTo.getTheoryEseMinMarks()>0)
			bo.setSubjectTheoryMark(String.valueOf(subSetTo.getTheoryEseMinMarks()));
		if(subSetTo.getPracticalEseMinMarks()>0)
			bo.setSubjectPracticalMark(String.valueOf(subSetTo.getPracticalEseMinMarks()));
		
		//raghu
		if(marksList!=null && !marksList.isEmpty()){
			Iterator<StudentMarksTO> itr=marksList.iterator();
			while (itr.hasNext()) {
				StudentMarksTO mto = (StudentMarksTO) itr.next();
		
		
		//bcz of both subject
		if((subSetTo.getIsTheoryPractical().equalsIgnoreCase("t") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")) && (!subSetTo.isTheoryEvalCheck()) && mto.getEvaId()==null){
			if(StringUtils.isAlpha(mto.getTheoryMarks())){
				bo.setPassOrFail("fail");
				bo.setStudentTheoryMarks(mto.getTheoryMarks());
			}else{
				double theoryMarks=0;
				if(mto.getTheoryMarks()!=null){
				 theoryMarks= (Double.parseDouble(mto.getTheoryMarks().trim())/subSetTo.getTheoryEseEnteredMaxMarks())*subSetTo.getTheoryEseMaxMarks();
//				 bo.setStudentTheoryMarks(String.valueOf(Math.round(theoryMarks)));
				 if(!avoidExamIds.contains(exam.getId()))
					 bo.setStudentTheoryMarks(String.valueOf(Math.round(Double.parseDouble(df.format(theoryMarks)))));
				 else
					 bo.setStudentTheoryMarks(String.valueOf(Math.round(theoryMarks)));
				 if(theoryMarks<subSetTo.getTheoryEseMinMarks())
					 bo.setPassOrFail("fail");
				 else
					 bo.setPassOrFail("pass");
				}else{
					bo.setPassOrFail("pass");
				}
			}
			
		}
		
		//bcz of both subject
		if((subSetTo.getIsTheoryPractical().equalsIgnoreCase("p") || subSetTo.getIsTheoryPractical().equalsIgnoreCase("b")) && (!subSetTo.isPracticalEvalCheck()) && mto.getEvaId()==null){
			if(StringUtils.isAlpha(mto.getPracticalMarks())){
				bo.setPassOrFail("fail");
				bo.setStudentPracticalMarks(mto.getPracticalMarks());
			}else{
				double PracticalMarks=0;
				if(mto.getPracticalMarks()!=null){
					if(subSetTo.getPracticalEseEnteredMaxMarks()>0 && subSetTo.getPracticalEseMaxMarks()>0){
						PracticalMarks= (Double.parseDouble(mto.getPracticalMarks())/subSetTo.getPracticalEseEnteredMaxMarks())*subSetTo.getPracticalEseMaxMarks();
					}if(PracticalMarks==0 || PracticalMarks<subSetTo.getPracticalEseMinMarks())
					bo.setPassOrFail("fail");
				else
					bo.setPassOrFail("pass");
				if(!avoidExamIds.contains(exam.getId()))
					bo.setStudentPracticalMarks(String.valueOf(Math.round(Double.parseDouble(df.format(PracticalMarks)))));
				else
					bo.setStudentPracticalMarks(String.valueOf(Math.round(PracticalMarks)));
					
				}else{
					bo.setPassOrFail("pass");
				}
			}
		}
		
		// /* code added by chandra for checking the gracing
		if(mto.getIsGracing() != null && mto.getIsGracing())
			bo.setIsGracing(true);
		else
			bo.setIsGracing(false);
		// */
		
		
		
			}//while close
		}//if close
		
		
		
		bo.setIsRevaluationModeration(false);
		//}
		return bo;
	}
	/**
	 * @param studentId
	 * @param subjectIdList
	 * @param examId
	 * @param transaction
	 * @param newUpdateProccessForm 
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, List<StudentMarksTO>> getStudentRegularMarks(int studentId, List<Integer> subjectIdList, 
			int examId, INewUpdateProccessTransaction transaction, int classId, NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		return transaction.getStudentRegularMarks(studentId,subjectIdList,examId, classId, newUpdateProccessForm);
	}
	
	/**
	 * new changes are made accourding to requirement by balaji (The Complete code has changed)
	 * @param newUpdateProccessForm
	 * @throws Exception
	 */
	public void updatePassOrFail(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		try {
			List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
			List<String> errorList=new ArrayList<String>();
			List<ExamStudentPassFail> boList=new ArrayList<ExamStudentPassFail>();
			INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
			boolean isImprovement=false;
			if(classList!=null && !classList.isEmpty()){
				Iterator<ClassesTO> itr=classList.iterator();
				while (itr.hasNext()) {
					ClassesTO to = (ClassesTO) itr.next();
					if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
						Classes classes=new Classes();
						classes.setId(to.getId());
						Map<String,SubjectMarksTO> minMarks=transaction.getMinMarksMap(to);
						List<Integer> excludedList =  impl.getExcludedFromResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
						List<Integer> failureExcludeList = impl.getExcludedFromTotResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
						
						boolean isMaxRecord=false;
				
						List<StudentTO> studentList=getStudentListForClass(to.getId());
						if(studentList!=null && !studentList.isEmpty()){
							// The Real Code Here Only Starts
							Iterator<StudentTO> stuItr=studentList.iterator();
							while (stuItr.hasNext()) {
								StudentTO studentTO = (StudentTO) stuItr.next();
//								System.out.println("pass or fail:"+studentTO.getId());
//					if(studentTO.getId()==2052){// remove this
								Map<Integer,StudentMarkDetailsTO> totSubMap=new HashMap<Integer, StudentMarkDetailsTO>();//subject and marks details to verify max or latest and keep in the map at last
								BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(to.getCourseId());
								boolean checkTotal=true;
								double stuTotalMarks=0;
								double subTotalMarks=0;
								
								List<Object[]> list=transaction.getDataByStudentAndClassId(studentTO.getId(),to.getId(),studentTO.getSubjectIdList(),studentTO.getAppliedYear());
								if(list!=null && !list.isEmpty()){
									Iterator<Object[]> marksItr=list.iterator();
									while (marksItr.hasNext()) {
										Object[] objects = (Object[]) marksItr.next();
										System.out.println(studentTO.getId());
										if(objects[9]!=null && minMarks.containsKey(objects[9].toString())){
											boolean isAddTotal=true;
											if(excludedList.contains(Integer.parseInt(objects[9].toString()))){
												isAddTotal=false;
											}
											SubjectMarksTO subTo=minMarks.get(objects[9].toString());
											StudentMarkDetailsTO markDetailsTO= new StudentMarkDetailsTO();
											markDetailsTO.setAddTotal(isAddTotal);
											if(objects[4]!=null)
												markDetailsTO.setStudentId(Integer.parseInt(objects[4].toString()));
											if(objects[9]!=null)
												markDetailsTO.setSubjectId(Integer.parseInt(objects[9].toString()));
											double theoryRegMark=0;
											boolean isTheoryAlpha=false;
											if (objects[15] != null || objects[16] != null) {
												if(objects[15]!=null){
													if(!StringUtils.isAlpha(objects[15].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[15].toString());
													else
														isTheoryAlpha=true;
												}
												if(objects[16]!=null){
													if(!StringUtils.isAlpha(objects[16].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[16].toString());
													else
														isTheoryAlpha=true;
												}
											}else{
												if(objects[11]!=null){
													if(!StringUtils.isAlpha(objects[11].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[11].toString());
													else
														isTheoryAlpha=true;
												}
												if(objects[12]!=null){
													if(!StringUtils.isAlpha(objects[12].toString()))
														theoryRegMark=theoryRegMark+Double.parseDouble(objects[12].toString());
													else
														isTheoryAlpha=true;
												}
											}
											if(!isTheoryAlpha)
												markDetailsTO.setStuTheoryIntMark(String.valueOf(theoryRegMark));
											else
												markDetailsTO.setStuTheoryIntMark("AA");
											double practicalRegMark=0;
											boolean isPracticalAlpha=false;
											if (objects[17] != null || objects[18] != null) {
												if(objects[17]!=null){
													if(!StringUtils.isAlpha(objects[17].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[17].toString());
													else
														isPracticalAlpha=true;
												}if(objects[18]!=null){
													if(!StringUtils.isAlpha(objects[18].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[18].toString());
													else
														isPracticalAlpha=true;
												}
											}else{
												if(objects[13]!=null){
													if(!StringUtils.isAlpha(objects[13].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[13].toString());
													else
														isPracticalAlpha=true;
												}
												if(objects[14]!=null){
													if(!StringUtils.isAlpha(objects[14].toString()))
														practicalRegMark=practicalRegMark+Double.parseDouble(objects[14].toString());
													else
														isPracticalAlpha=true;
												}
											}
											if(!isPracticalAlpha)
												markDetailsTO.setStuPracIntMark(String.valueOf(practicalRegMark));
											else
												markDetailsTO.setStuPracIntMark("AA");
											
											if (objects[19] != null) {
												markDetailsTO.setStuTheoryRegMark(objects[19].toString());
											}
											if (objects[20] != null) {
												markDetailsTO.setStuPracRegMark(objects[20].toString());
											}
											if (objects[22] != null) {
												markDetailsTO.setIs_theory_practical(objects[22].toString());
											}
											
											//if the student has written improvement for the subject then we should check max
											isImprovement=false;
											if (objects[23] != null) {
												isImprovement=Boolean.parseBoolean(objects[23].toString());
											}
											if(isMaxRecord){
												if(totSubMap.containsKey(markDetailsTO.getSubjectId())){
													StudentMarkDetailsTO markDetailsTO2=totSubMap.remove(markDetailsTO.getSubjectId());
													StudentMarkDetailsTO maxMarks=checkMaxBetweenTOs(markDetailsTO,markDetailsTO2,subTo,subTotalMarks,stuTotalMarks,isAddTotal,isImprovement);
													totSubMap.put(markDetailsTO.getSubjectId(),maxMarks);
												}else{
													totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
												}
											}else{
												if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
													totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
												}
											}
										}
									}
									// The Real Logic comes now ( New Logic has Implemented)
									List<StudentMarkDetailsTO> totalList = new ArrayList<StudentMarkDetailsTO>(totSubMap.values());

									if(!totalList.isEmpty()){
										Iterator<StudentMarkDetailsTO> totalitr=totalList.iterator();
										while (totalitr.hasNext()) {
											StudentMarkDetailsTO markDetailsTO = totalitr .next();
											if(minMarks.containsKey(String.valueOf(markDetailsTO.getSubjectId()))){
												boolean isAddTotal=true;
												boolean isfalse=false;
												if(excludedList.contains(markDetailsTO.getSubjectId())){
													isAddTotal=false;
												}
												SubjectMarksTO subTo=minMarks.get(String.valueOf(markDetailsTO.getSubjectId()));
												// The Real Logic has to implement Here
												if(subTo.getTheoryIntMin()!=null && !subTo.getTheoryIntMin().isEmpty()){
													if (markDetailsTO.getStuTheoryIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
															isfalse=true;
															checkTotal=false;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double
																.parseDouble(subTo.getTheoryIntMin())) {
															isfalse=true;
															checkTotal=false;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
													else{
														isfalse=true;
														checkTotal=false;
														markDetailsTO.setIsTheoryFailed(true);
													}
												}
												if(subTo.getTheoryRegMin()!=null && !subTo.getTheoryRegMin().isEmpty()){
													if (markDetailsTO.getStuTheoryRegMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark()) < Double
																.parseDouble(subTo.getTheoryRegMin())) {
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
													else{
														isfalse=true;	
														markDetailsTO.setIsTheoryFailed(true);
													}
												}
												if(subTo.getPracticalIntMin()!=null && !subTo.getPracticalIntMin().isEmpty()){
													if (markDetailsTO.getStuPracIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
																.parseDouble(subTo.getPracticalIntMin())) {
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
													else{
														isfalse=true;
														markDetailsTO.setIsPracticalFailed(true);
													}
												}
												if(subTo.getPracticalRegMin()!=null && !subTo.getPracticalRegMin().isEmpty()){
													if (markDetailsTO.getStuPracRegMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuPracRegMark()) < Double
																.parseDouble(subTo.getPracticalRegMin())) {
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
													else{
														isfalse=true;	
														markDetailsTO.setIsPracticalFailed(true);
													}
												}
												
												if (subTo.getFinalTheoryMin() != null) {
													if(isAddTotal){
//														if(!isMaxRecord){
															if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
																if(subTo.getFinalTheoryMarks()!=null)
																	subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
																if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
																}
																if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
																}
//															}
														}else{
															if(subTo.getFinalTheoryMarks()!=null)
																subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalTheoryMarks());
															if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
																stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
															}
															if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
																stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
															}
														}
													}
													if (markDetailsTO.getStuTheoryRegMark() != null
															&& markDetailsTO.getStuTheoryIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim()) ){
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuTheoryRegMark())+ 
																Double.parseDouble(markDetailsTO.getStuTheoryIntMark()) < Double.parseDouble(subTo.getFinalTheoryMin())) {
															isfalse=true;
															markDetailsTO.setIsTheoryFailed(true);
														}
													}
												}
												if (subTo.getFinalPracticalMin() != null) {
													if(isAddTotal){
//														if(!isMaxRecord){
															if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
																if(subTo.getFinalPracticalMarks()!=null)
																	subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
																if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
																}
																if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
																	stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
																}
//															}
														}else{
															if(subTo.getFinalPracticalMarks()!=null)
																subTotalMarks=subTotalMarks+Double.parseDouble(subTo.getFinalPracticalMarks());
															if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
																stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
															}
															if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
																stuTotalMarks=stuTotalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
															}
														}
													}
													if (markDetailsTO.getStuPracRegMark() != null && markDetailsTO.getStuPracIntMark() != null){
														if(StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim()) || StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
														else if (Double.parseDouble(markDetailsTO.getStuPracRegMark())
																+ Double.parseDouble(markDetailsTO.getStuPracIntMark()) < Double
																.parseDouble(subTo.getFinalPracticalMin())) {
															isfalse=true;
															markDetailsTO.setIsPracticalFailed(true);
														}
													}
												}
												if(((markDetailsTO.getIsTheoryFailed()!=null && markDetailsTO.getIsTheoryFailed()) || (markDetailsTO.getIsPracticalFailed()!=null && markDetailsTO.getIsPracticalFailed())) && !failureExcludeList.contains(markDetailsTO.getSubjectId())){
													checkTotal=false;
												}
											}
										}
									}
									if(!totSubMap.isEmpty()){
										Double studentPercentage =Double.valueOf(0);
										if(subTotalMarks>0 && stuTotalMarks>0)
										 studentPercentage = Double.valueOf((stuTotalMarks * 100) / subTotalMarks);
										if (requiredAggrePercentage != null	&& studentPercentage != null) {
											if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
												checkTotal =false;	
											}
										}
										ExamStudentPassFail examStudentPassFail=new ExamStudentPassFail();
										Student student=new Student();
										student.setId(studentTO.getId());
										examStudentPassFail.setStudent(student);
										examStudentPassFail.setSchemeNo(to.getTermNo());
										examStudentPassFail.setClasses(classes);
										if(checkTotal)
											examStudentPassFail.setPassFail('P');
										else
											examStudentPassFail.setPassFail('F'); // modified by Nagarjun
										examStudentPassFail.setPercentage(new BigDecimal(studentPercentage));
										if(examStudentPassFail.getPassFail().equals('F')){
											examStudentPassFail.setResult("Fail");
										}else{
											String result = iUpdateStudentSGPATxn.getResultClass(to.getCourseId(), Double.valueOf(studentPercentage),studentTO.getAppliedYear(),studentTO.getId());
											examStudentPassFail.setResult(result);
										}
										boList.add(examStudentPassFail);
									}
								}
//					}//remove this std id
							}
						}
					}
				}
			}
			impl.updatePassFail(boList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
	}
}
//	public boolean calculateRevaluationModerationAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
//		
//				ExamDefinition exam=new ExamDefinition();
//				int examId=Integer.parseInt(newUpdateProccessForm.getExamId());
//				exam.setId(examId);
//				List<String> errorList=new ArrayList<String>();
//				List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
//				List<StudentFinalMarkDetails> boList=new ArrayList<StudentFinalMarkDetails>();
//				INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
//				if(classList!=null && !classList.isEmpty()){
//					Iterator<ClassesTO> itr=classList.iterator();
//					while (itr.hasNext()) {
//						ClassesTO to = (ClassesTO) itr.next();
//						Classes classes=new Classes();
//						classes.setId(to.getId());
//						//int SemNo=to.getTermNo();
//						if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
////						if(to.getId()==620){// remove this
//							
//							//Map<Integer,SubjectRuleSettingsTO> subRuleMap=transaction.getSubjectRuleSettingsForRegularOverAll(to.getCourseId(),to.getYear(),to.getTermNo());
//							//raghu
//							//boolean isPGStudent = transaction.isPgStudent(to.getCourseId());
//							List<Integer> students = getStudentIdsForModeration(to.getId(),examId);
//							//List<StudentTO> studentList=getStudentListForClass(to.getId());
////							System.out.println("class Id:"+to.getId());
//							if(students!=null && !students.isEmpty()){
//								Iterator<Integer> sitr=students.iterator();
//								while (sitr.hasNext()) {
//									Integer stdnt=sitr.next();
//									//if(stdnt==2264){//remove this
//									System.out.println("student Id"+stdnt);
//									//}
//									List<Integer> subjects = getStubjectIdsForModeration(to.getId(),examId,stdnt);
//									if(subjects!=null && !subjects.isEmpty()){
//										Map<Integer,StudentFinalMarkDetails> finalMarkMap = getFinalMarkMap(to.getId(),examId,stdnt);
//										//Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), newUpdateProccessForm);
//										if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty()){
//											Iterator<Integer> subItr=subjects.iterator();
//											while (subItr.hasNext()) { 
//												boolean change = false;
//												Integer subId = (Integer) subItr.next();
//												//if(subId==1176){
//												if(finalMarkMap.containsKey(subId)){
//													
//													StudentFinalMarkDetails sfm = finalMarkMap.get(subId);
//													List obj = getMaxModeration(to.getId(),examId,stdnt,subId);
//													Double theory ;
//													Double practical ;
//													if(obj!=null && obj.size()!=0){
//														Iterator<Object[]> itr2 = obj.iterator();
//														while (itr2.hasNext()) {
//															Object[] objct = itr2.next();
//															if(objct[0]!=null && !StringUtils.isEmpty(objct[0].toString())){
//																if(Double.parseDouble(objct[0].toString())>Double.parseDouble(sfm.getStudentTheoryMarks())){
//																	sfm.setStudentTheoryMarks(objct[0].toString());
//																	change=true;
//																}
//															}
//															if(objct[1]!=null && !StringUtils.isEmpty(objct[1].toString())){
//																if(Double.parseDouble(objct[1].toString())>Double.parseDouble(sfm.getStudentPracticalMarks())){
//																	sfm.setStudentPracticalMarks(objct[1].toString());
//																	change=true;
//																}
//															}
//														}
//													}
//												 if(change){
//													 sfm.setModifiedBy(newUpdateProccessForm.getUserId());
//													 sfm.setLastModifiedDate(new Date());
//													 sfm.setIsRevaluationModeration(true);
//													 boList.add(sfm);
//												 }
//												}
//												
//											}
//											}
//										//}// remove
//										}
//									}
//								//}//remove this stu id
//								}//remove this
//								}
//							}
////						}//remove THis
//					//	}
//					}//azr
//				if(errorList.isEmpty() && (newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())){
//					if(boList == null || boList.isEmpty()) {
//						System.gc();
//						throw new DataNotFoundException();
//					}
//					return transaction.saveRegularOverAllAfterRevaluation(boList);
//				}else{
//					newUpdateProccessForm.setErrorList(errorList);
//					return false;
//				}
//			}

	private List getMaxModeration(int id, int examId, Integer stdnt,Integer subId) throws ApplicationException {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getMaxModeration(id,examId,stdnt,subId);
	}
	private Map<Integer, StudentFinalMarkDetails> getFinalMarkMap(int id,int examId, Integer stdnt) throws ApplicationException {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getgetFinalMarkMap(id,examId,stdnt);
	}
	private List<Integer> getStubjectIdsForModeration(int id, int examId,Integer stdnt) throws ApplicationException {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getSubjectIds(id,examId,stdnt);
	}
	private List<Integer> getStudentIdsForModeration(int id, int examId) throws ApplicationException {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getStudentIds(id,examId);
		
	}
	public boolean calculateRegularGraceAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws ApplicationException {
		
		try {
			ExamDefinition exam=new ExamDefinition();
			int examId=Integer.parseInt(newUpdateProccessForm.getExamId());
			exam.setId(examId);
			List<String> errorList=new ArrayList<String>();
			List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
			List<StudentFinalMarkDetails> boList=new ArrayList<StudentFinalMarkDetails>();
			INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
			if(classList!=null && !classList.isEmpty()){
				Iterator<ClassesTO> itr=classList.iterator();
				while (itr.hasNext()) {
					ClassesTO to = (ClassesTO) itr.next();
					Classes classes=new Classes();
					classes.setId(to.getId());
					//int SemNo=to.getTermNo();
					if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
//					if(to.getId()==620){// remove this
						
						//Map<Integer,SubjectRuleSettingsTO> subRuleMap=transaction.getSubjectRuleSettingsForRegularOverAll(to.getCourseId(),to.getYear(),to.getTermNo());
						//raghu
						//boolean isPGStudent = transaction.isPgStudent(to.getCourseId());
						List<Integer> students = getStudentIdsForGracing(to.getId(),examId);
						//List<StudentTO> studentList=getStudentListForClass(to.getId());
//						System.out.println("class Id:"+to.getId());
						if(students!=null && !students.isEmpty()){
							Iterator<Integer> sitr=students.iterator();
							while (sitr.hasNext()) {
								Integer stdnt=sitr.next();
								//if(stdnt==3107) {	//remove this
								List<Integer> subjects = getStubjectIdsForGracing(to.getId(),examId,stdnt, Integer.parseInt(newUpdateProccessForm.getBatchYear()), to.getProgramTypeId());
								if(subjects!=null && !subjects.isEmpty()){
									Map<Integer,StudentFinalMarkDetails> finalMarkMap = getFinalMarkMap(to.getId(),examId,stdnt);
									//Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), newUpdateProccessForm);
									if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty()){
										Iterator<Integer> subItr=subjects.iterator();
										while (subItr.hasNext()) { 
											Integer subId = (Integer) subItr.next();
											//if(subId==882) {	//remove this
											if(finalMarkMap.containsKey(subId)){
												StudentFinalMarkDetails sfm = finalMarkMap.get(subId);
												ExamGracingSubjectMarksBo graceBo = getGraceMarks(to.getId(),examId,stdnt,subId);
												Double theory = 0.0 ;	
												if(graceBo!=null && !sfm.getIsGracing() && graceBo.getTheoryEseGrace()>0 && !StringUtils.isAlpha(sfm.getStudentTheoryMarks())){
													if(sfm.getStudentTheoryMarks()!=null ){
														theory = Double.parseDouble(sfm.getStudentTheoryMarks()) + graceBo.getTheoryEseGrace();
														/**
													     * by Arun Sudhakaran
													     * comparing marks of supplementary exam with regular exam
													     * if supplementary marks after grace is greater than that of regular marks after its grace 
													     * 		then set old marks to regular marks in final mark table
													     * 		and update supplementary record
													     * else
													     * 		don't update supplementary record
													     */
														boolean isSupplementaryGreater = false;
														boolean isSupplementaryExam = false;
														StudentFinalMarkDetails finalMarkDetails = getRegularMarksForComparison(to.getId(), stdnt, subId);
														if(examId > finalMarkDetails.getExam().getId()) {	// gracing process for supplementary exam.
															isSupplementaryExam = true;
															if(CommonUtil.isValidDecimal(finalMarkDetails.getStudentTheoryMarks()) && theory > Double.parseDouble(finalMarkDetails.getStudentTheoryMarks())) {
																finalMarkDetails.setIsGracing(false);
																finalMarkDetails.setStudentTheoryMarks(finalMarkDetails.getStudentTheoryMarksBeforeGrace());
																boList.add(finalMarkDetails);	//	retained old marks regarding regular and adding to list for updation																
																isSupplementaryGreater = true;
															}
															else if(StringUtils.isAlpha(finalMarkDetails.getStudentTheoryMarks())) {
																isSupplementaryGreater = true;
															}
														}
														if(!isSupplementaryExam || (isSupplementaryExam && isSupplementaryGreater)) {
															sfm.setStudentTheoryMarksBeforeGrace(sfm.getStudentTheoryMarks());
															sfm.setStudentTheoryMarks(theory.toString());
														}
													}
/*												    if(sfm.getStudentPracticalMarks()!=null){
													 practical = Double.parseDouble(sfm.getStudentPracticalMarks()) + graceBo.getPracticalEseGrace();			
													sfm.setStudentPracticalMarks(practical.toString());
												    }*/
											        sfm.setModifiedBy(newUpdateProccessForm.getUserId());
												    sfm.setLastModifiedDate(new Date());
												    sfm.setIsGracing(true);
												    boList.add(sfm);												    												    
												}											
											}											
										}
									}
								}
							}
						}
					}
				}	
			}
			
			if(errorList.isEmpty() && (newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())){
				return transaction.saveRegularOverAll(boList);
			}else{
				newUpdateProccessForm.setErrorList(errorList);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}		
	}
	private ExamGracingSubjectMarksBo getGraceMarks(int id, int examId,
			Integer stdnt, Integer subId) throws Exception {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getGraceMarks(id,examId,stdnt,subId);
	}
	private List<Integer> getStubjectIdsForGracing(int id, int examId, Integer stdnt, int batchYear, int programTypeId) throws Exception {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getSubjectIdsForGracing(id,examId,stdnt,batchYear, programTypeId);
	}
	private List<Integer> getStudentIdsForGracing(int id, int examId) throws Exception {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getStudentIdsForGracing(id,examId);
		
	}
	public boolean calculateInternalGraceAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws ApplicationException {
		
		try {
			ExamDefinition exam=new ExamDefinition();
			int examId=Integer.parseInt(newUpdateProccessForm.getExamId());
			exam.setId(examId);
			List<String> errorList=new ArrayList<String>();
			List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
			List<StudentOverallInternalMarkDetails> boList=new ArrayList<StudentOverallInternalMarkDetails>();
			INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
			if(classList!=null && !classList.isEmpty()){
				Iterator<ClassesTO> itr=classList.iterator();
				while (itr.hasNext()) {
					ClassesTO to = (ClassesTO) itr.next();
					Classes classes=new Classes();
					classes.setId(to.getId());
					//int SemNo=to.getTermNo();
					if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
//					if(to.getId()==620){// remove this
						
						//Map<Integer,SubjectRuleSettingsTO> subRuleMap=transaction.getSubjectRuleSettingsForRegularOverAll(to.getCourseId(),to.getYear(),to.getTermNo());
						//raghu
						//boolean isPGStudent = transaction.isPgStudent(to.getCourseId());
						List<Integer> students = getStudentIdsForGracing(to.getId(),examId);
						//List<StudentTO> studentList=getStudentListForClass(to.getId());
//						System.out.println("class Id:"+to.getId());
						if(students!=null && !students.isEmpty()){
							Iterator<Integer> sitr=students.iterator();
							while (sitr.hasNext()) {
								Integer stdnt=sitr.next();
//								if(sto.getId()==2052){//remove this
								//}
								List<Integer> subjects = getStubjectIdsForGracing(to.getId(),examId,stdnt, Integer.parseInt(newUpdateProccessForm.getBatchYear()), to.getProgramTypeId());
								if(subjects!=null && !subjects.isEmpty()){
									Map<Integer,StudentOverallInternalMarkDetails> finalMarkMap = getFinalInternalMarkMap(to.getId(),examId,stdnt);
									//Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), newUpdateProccessForm);
									if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty()){
										Iterator<Integer> subItr=subjects.iterator();
										while (subItr.hasNext()) { 
											Integer subId = (Integer) subItr.next();
											if(finalMarkMap.containsKey(subId)){
												StudentOverallInternalMarkDetails sfm = finalMarkMap.get(subId);
												ExamGracingSubjectMarksBo graceBo = getGraceMarks(to.getId(),examId,stdnt,subId);
												Double theory = 0.0 ;
												Double practical = 0.0 ;
												Double totalTheory = 0.0 ;
												Double totalPractical = 0.0 ;
												if(graceBo!=null && !sfm.getIsGracing() && graceBo.getTheoryInternalGrace()>0){
													if(sfm.getTheoryTotalSubInternalMarks()!=null){
														theory = Double.parseDouble(sfm.getTheoryTotalSubInternalMarks()) + graceBo.getTheoryInternalGrace();
														sfm.setTheoryTotalSubInternalMarks(theory.toString());
													}
													if(sfm.getTheoryTotalMarks()!=null){
														totalTheory = Double.parseDouble(sfm.getTheoryTotalMarks()) + graceBo.getTheoryInternalGrace();
														sfm.setTheoryBeforeGracing(sfm.getTheoryTotalMarks());
														sfm.setTheoryTotalMarks(totalTheory.toString());
													}
/*													if(sfm.getPracticalTotalSubInternalMarks()!=null){
													   practical = Double.parseDouble(sfm.getPracticalTotalSubInternalMarks()) + graceBo.getPracticalInternalGrace();
													   sfm.setPracticalTotalSubInternalMarks(practical.toString());
													 }													 
													 if(sfm.getPracticalTotalMarks()!=null){
													 totalPractical = Double.parseDouble(sfm.getPracticalTotalMarks()) + graceBo.getPracticalInternalGrace();
													 sfm.setPracticalTotalMarks(totalPractical.toString());
													 }*/
												    sfm.setLastModifiedDate(new Date());
												    sfm.setIsGracing(true);
												    boList.add(sfm);
												}											
											}											
										}
									}
								}
							}
//							}//remove this stu id
						}//remove this
					}
				}
//					}//remove THis
				//	}					
			}
			
			if(errorList.isEmpty() && (newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())){
				return transaction.saveInternalOverAll(boList);
			}else{
				newUpdateProccessForm.setErrorList(errorList);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}		
	}
	private Map<Integer, StudentOverallInternalMarkDetails> getFinalInternalMarkMap(
			int id, int examId, Integer stdnt) throws Exception {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getFinalInternalMarkMap(id,examId,stdnt);
	}

	public boolean calculateRevaluationScrutinyAndSaveData(NewUpdateProccessForm newUpdateProccessForm) throws Exception {

			ExamDefinition exam=new ExamDefinition();
			int examId=Integer.parseInt(newUpdateProccessForm.getExamId());
			exam.setId(examId);
			List<String> errorList=new ArrayList<String>();
			List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
			List<StudentFinalMarkDetails> boList=new ArrayList<StudentFinalMarkDetails>();
			INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
			if(classList!=null && !classList.isEmpty()){
				Iterator<ClassesTO> itr=classList.iterator();
				while (itr.hasNext()) {
					ClassesTO to = (ClassesTO) itr.next();
					Classes classes=new Classes();
					classes.setId(to.getId());
					//int SemNo=to.getTermNo();
					if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
						//					if(to.getId()==620){// remove this

						boolean isPGStudent = transaction.isPgStudent(to.getCourseId());
						// for challenge valuation
						if(isPGStudent){
							List<Integer> students = transaction.getStudentIds(to.getId(),examId);
							if(students!=null && !students.isEmpty()){
								Iterator<Integer> sitr=students.iterator();
								while (sitr.hasNext()) {
									Integer stdnt=sitr.next();
									//if(stdnt==2264){//remove this
										System.out.println("student Id"+stdnt);
										//}
										List<Integer> subjects = getStubjectIdsForModeration(to.getId(),examId,stdnt);
										if(subjects!=null && !subjects.isEmpty()){
											Map<Integer,StudentFinalMarkDetails> finalMarkMap = getFinalMarkMap(to.getId(),examId,stdnt);
											//Map<Integer,List<StudentMarksTO>> stuMarksMap=getStudentRegularMarks(sto.getId(),sto.getSubjectIdList(),examId,transaction, to.getId(), newUpdateProccessForm);
											if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty()){
												Iterator<Integer> subItr=subjects.iterator();
												while (subItr.hasNext()) { 
													boolean change = false;
													Integer subId = (Integer) subItr.next();
													//if(subId==1176){
														if(finalMarkMap.containsKey(subId)){

															StudentFinalMarkDetails sfm = finalMarkMap.get(subId);
															List obj = getMaxModeration(to.getId(),examId,stdnt,subId);
															Double theory ;
															Double practical ;
															if(obj!=null && obj.size()!=0){
																Iterator<Object[]> itr2 = obj.iterator();
																while (itr2.hasNext()) {
																	Object[] objct = itr2.next();
																	if(objct[0]!=null && !StringUtils.isEmpty(objct[0].toString())){
																			sfm.setStudentTheoryMarks(objct[0].toString());
																			change=true;
																	}
																	if(objct[1]!=null && !StringUtils.isEmpty(objct[1].toString())){
																			sfm.setStudentPracticalMarks(objct[1].toString());
																			change=true;
																	}


																}
															}
															if(change){
																sfm.setModifiedBy(newUpdateProccessForm.getUserId());
																sfm.setLastModifiedDate(new Date());
																sfm.setIsRevaluationModeration(true);
																boList.add(sfm);
															}
														}

													//}
												}
											}
										//}
									}
								}//remove this stu id
							}//remove this
						}
						
						// for revaluation/scrutiny
						else{

							List<Integer> students =getStudentIdsForModeration(to.getId(),examId);
							if(students!=null && !students.isEmpty()){
								Iterator<Integer> sitr=students.iterator();
								while (sitr.hasNext()) {
									Integer stdnt=sitr.next();
									//if(stdnt==2264){//remove this
										System.out.println("student Id"+stdnt);
										//}
										List<Integer> subjects = getStubjectIdsForModeration(to.getId(),examId,stdnt);
										if(subjects!=null && !subjects.isEmpty()){
											Map<Integer,StudentFinalMarkDetails> finalMarkMap = getFinalMarkMap(to.getId(),examId,stdnt);
											if(newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty()){
												Iterator<Integer> subItr=subjects.iterator();
												while (subItr.hasNext()) { 
													boolean change = false;
													Integer subId = (Integer) subItr.next();
												//	if(subId==1176){
														if(finalMarkMap.containsKey(subId)){

															StudentFinalMarkDetails sfm = finalMarkMap.get(subId);
															List obj = getMaxModeration(to.getId(),examId,stdnt,subId);
															Double theory ;
															Double practical ;
															if(obj!=null && obj.size()!=0){
																Iterator<Object[]> itr2 = obj.iterator();
																while (itr2.hasNext()) {
																	Object[] objct = itr2.next();
																	if(objct[0]!=null && !StringUtils.isEmpty(objct[0].toString())){
																		if(Double.parseDouble(objct[0].toString())>Double.parseDouble(sfm.getStudentTheoryMarks())){
																			sfm.setStudentTheoryMarks(objct[0].toString());
																			change=true;
																		}
																	}
																	if(objct[1]!=null && !StringUtils.isEmpty(objct[1].toString())){
																		if(Double.parseDouble(objct[1].toString())>Double.parseDouble(sfm.getStudentPracticalMarks())){
																			sfm.setStudentPracticalMarks(objct[1].toString());
																			change=true;
																		}
																	}


																}
															}
															if(change){
																sfm.setModifiedBy(newUpdateProccessForm.getUserId());
																sfm.setLastModifiedDate(new Date());
																sfm.setIsRevaluationModeration(true);
																boList.add(sfm);
															}
														}

													}
												}
											//}// remove
										}
									}
								//}//remove this stu id
							}//remove this
					

						}
					}
				}
				//					}//remove THis
				//	}

			}//azr
			if(errorList.isEmpty() && (newUpdateProccessForm.getErrorMessage() == null || newUpdateProccessForm.getErrorMessage().isEmpty())){
				if(boList == null || boList.isEmpty()) {
					System.gc();
					throw new DataNotFoundException();
				}
				return transaction.saveRegularOverAll(boList);
			}else{
				newUpdateProccessForm.setErrorList(errorList);
				return false;
			}
		}
	public List<StudentSupplementaryImprovementApplication> getBoListFromToListForInternalRedo(NewUpdateProccessForm newUpdateProccessForm) throws Exception{
		
		List<ClassesTO> classList = newUpdateProccessForm.getClassesList();
		List<StudentSupplementaryImprovementApplication> boList = new ArrayList<StudentSupplementaryImprovementApplication>();
		INewUpdateProccessTransaction transaction = NewUpdateProccessTransactionImpl.getInstance();
		
		int examId = Integer.parseInt(newUpdateProccessForm.getExamId());
		
		if(classList!=null && !classList.isEmpty()){
			
			Iterator<ClassesTO> itr=classList.iterator();
			while (itr.hasNext()) {
				ClassesTO to = (ClassesTO) itr.next();
				
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
					
					/**
					 * Getting the minimum marks for subjects from Subject Definition Coursewise.
					 * Listing all Subjects whose pass/fail is not to be considered.
					 * Deleting all existing records for this exam.
					 */
					Map<Integer,SubjectRuleSettingsTO> subRuleMap=transaction.getSubjectRuleSettings(to.getCourseId(),to.getYear(),to.getTermNo());
					List<Integer> excludedList = impl.getExcludedFromResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
					List<Integer> failureExcludeList = impl.getExcludedFromTotResultSubjects(to.getCourseId(), to.getTermNo(),to.getYear());
					boolean isDeleted=transaction.deleteOldRecords(to.getId(),newUpdateProccessForm.getExamId());
					
					if(isDeleted){
						
						List<StudentOverallInternalMarkDetails> internalMarkList = transaction.getInternalMarksForGivenExamAndClass(examId, to.getId());
						Iterator<StudentOverallInternalMarkDetails> internalOverAllItr = internalMarkList.iterator();
						
						while(internalOverAllItr.hasNext()) {
							
							StudentOverallInternalMarkDetails studentInternalMark = internalOverAllItr.next();
							
							if(!excludedList.contains(String.valueOf(studentInternalMark.getSubject().getId())) && 
							   !failureExcludeList.contains(String.valueOf(studentInternalMark.getSubject().getId()))) {
								
								if(subRuleMap.containsKey(studentInternalMark.getSubject().getId())) {
									
									SubjectRuleSettingsTO sto = subRuleMap.get(studentInternalMark.getSubject().getId());																		
																	
									if(studentInternalMark.getSubject().getIsTheoryPractical() != null && 
									   "T".equalsIgnoreCase(studentInternalMark.getSubject().getIsTheoryPractical()) && 
									   studentInternalMark.getTheoryTotalMarks() != null && 
									   !studentInternalMark.getTheoryTotalMarks().isEmpty() &&
									   CommonUtil.isValidDecimal(studentInternalMark.getTheoryTotalMarks())) {
										
										double studentTheoryMark = Double.parseDouble(studentInternalMark.getTheoryTotalMarks());
										double theoryMinimumMark = sto.getTheoryCiaMinMark();
										
										/**
										 * Checking whether the student internal theory mark is less than provided minimum mark
										 */
										if(studentTheoryMark < theoryMinimumMark) {																				
											
											StudentSupplementaryImprovementApplication supplementaryImprovementApplication = createObjectForFailedSubject(studentInternalMark, newUpdateProccessForm, to);											
											supplementaryImprovementApplication.setIsCIAFailedTheory(true);
											supplementaryImprovementApplication.setIsCIAFailedPractical(false);
											
											boList.add(supplementaryImprovementApplication);
										}
									}
									
									else if(studentInternalMark.getSubject().getIsTheoryPractical() != null && 
											"P".equalsIgnoreCase(studentInternalMark.getSubject().getIsTheoryPractical()) &&
											studentInternalMark.getPracticalTotalMarks() != null && 
											!studentInternalMark.getPracticalTotalMarks().isEmpty() &&
											CommonUtil.isValidDecimal(studentInternalMark.getPracticalTotalMarks())) {
										
										double studentPracticalMark = Double.parseDouble(studentInternalMark.getPracticalTotalMarks());
										double practicalMinimumMark = sto.getPracticalCiaMinMark();
										
										/**
										 * Checking whether the student internal practical mark is less than provided minimum mark
										 */
										if(studentPracticalMark < practicalMinimumMark) {									
											
											StudentSupplementaryImprovementApplication supplementaryImprovementApplication = createObjectForFailedSubject(studentInternalMark, newUpdateProccessForm, to);
											supplementaryImprovementApplication.setIsCIAFailedPractical(true);
											supplementaryImprovementApplication.setIsCIAFailedTheory(false);
											
											boList.add(supplementaryImprovementApplication);
										}
									}
									
									else if(studentInternalMark.getSubject().getIsTheoryPractical() != null && 
											"B".equalsIgnoreCase(studentInternalMark.getSubject().getIsTheoryPractical()) &&
											studentInternalMark.getTheoryTotalMarks() != null && !studentInternalMark.getTheoryTotalMarks().isEmpty() &&
											studentInternalMark.getPracticalTotalMarks() != null && !studentInternalMark.getPracticalTotalMarks().isEmpty()) {
										
										double studentTheoryMark = Double.parseDouble(studentInternalMark.getTheoryTotalMarks());
										double theoryMinimumMark = sto.getTheoryCiaMinMark();
										double studentPracticalMark = Double.parseDouble(studentInternalMark.getPracticalTotalMarks());
										double practicalMinimumMark = sto.getPracticalCiaMinMark();

										if((studentTheoryMark < theoryMinimumMark) || (studentPracticalMark < practicalMinimumMark)) {
											
											StudentSupplementaryImprovementApplication supplementaryImprovementApplication = createObjectForFailedSubject(studentInternalMark, newUpdateProccessForm, to);
											supplementaryImprovementApplication.setIsCIAFailedTheory(studentTheoryMark < theoryMinimumMark);
											supplementaryImprovementApplication.setIsCIAFailedPractical(studentPracticalMark < practicalMinimumMark);
											
											boList.add(supplementaryImprovementApplication);
										}										
									}
								}
							}
						}
					}
				}
			}
		}
		System.gc();
		if(boList.isEmpty()) {
			throw new DataNotFoundException();
		}
		return boList;
	}
	
	private StudentSupplementaryImprovementApplication createObjectForFailedSubject(StudentOverallInternalMarkDetails studentInternalMark,
																					NewUpdateProccessForm newUpdateProccessForm,
																					ClassesTO classesTO) {
		
		StudentSupplementaryImprovementApplication supplementaryImprovementApplication = new StudentSupplementaryImprovementApplication();
		
		/**
		 * Many to one mappings
		 */
		
		Student student = new Student();
		student.setId(studentInternalMark.getStudent().getId());
		supplementaryImprovementApplication.setStudent(student);
		
		ExamDefinition exam = new ExamDefinition();
		exam.setId(Integer.parseInt(newUpdateProccessForm.getExamId()));
		supplementaryImprovementApplication.setExamDefinition(exam);
		
		Subject subject = new Subject();
		subject.setId(studentInternalMark.getSubject().getId());
		supplementaryImprovementApplication.setSubject(subject);
		
		Classes classes = new Classes();
		classes.setId(classesTO.getId());
		supplementaryImprovementApplication.setClasses(classes);
		
		supplementaryImprovementApplication.setIsSupplementary(true);
		supplementaryImprovementApplication.setCiaExam(true);										
		supplementaryImprovementApplication.setIsCIAAppearedTheory(false);
		supplementaryImprovementApplication.setIsCIAAppearedPractical(false);
		supplementaryImprovementApplication.setIsImprovement(false);
		supplementaryImprovementApplication.setIsFailedTheory(false);
		supplementaryImprovementApplication.setIsFailedPractical(false);
		supplementaryImprovementApplication.setIsAppearedTheory(false);
		supplementaryImprovementApplication.setIsAppearedPractical(false);
		supplementaryImprovementApplication.setCreatedDate(new Date());
		supplementaryImprovementApplication.setCreatedBy(newUpdateProccessForm.getUserId());
		supplementaryImprovementApplication.setLastModifiedDate(new Date());
		supplementaryImprovementApplication.setModifiedBy(newUpdateProccessForm.getUserId());
		supplementaryImprovementApplication.setFees(null);
		supplementaryImprovementApplication.setIsOnline(false);
		supplementaryImprovementApplication.setSchemeNo(classesTO.getTermNo());
		
		return supplementaryImprovementApplication;
	}
	
	private StudentFinalMarkDetails getRegularMarksForComparison(int classId, int studentId, int subjectId) throws Exception {
		INewUpdateProccessTransaction transaction=NewUpdateProccessTransactionImpl.getInstance();
		return transaction.getRegularMarksForComparison(classId,studentId,subjectId);
	}
	
	public boolean updateInternalRedoMarks(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		
		/**		Description of process in short
		 * 
		 * Fetch Regular Exam by passing Internal Redo Exam id and class id selected from front end.
		 * Using the retrieved Regular Exam id fetching the records from Overall Internal table.
		 * Get a data from Marks Entry table as a Map, with key as Exam Id + _ + Student Id + _ + Class Id + _ + Subject Id, and value as Marks Entry BO.
		 * Make a key just like in the above format from the data retrieved from Overall Internal table, and check for matching keys.
		 * If key is present then store previous mark in previous marks column (newly added), make is internal redo column (newly added) as true, and update final mark.
		 * So no new records are created, only existing records are updated.
		 */

		INewUpdateProccessTransaction transaction = NewUpdateProccessTransactionImpl.getInstance();
		int examId = Integer.parseInt(newUpdateProccessForm.getExamId());
		List<ClassesTO> classList = newUpdateProccessForm.getClassesList();
		List<StudentOverallInternalMarkDetails> modifiedStudentOverallInternalMarkDetailsBoList =new ArrayList<StudentOverallInternalMarkDetails>();		
		if(classList!=null && !classList.isEmpty()){

			Iterator<ClassesTO> itr=classList.iterator();
			while (itr.hasNext()) {

				ClassesTO to = (ClassesTO) itr.next();
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){

					List<MarksEntry> studentList=getStudentListForClass(to.getId(),examId);

					if(studentList!=null && !studentList.isEmpty()) {
						
						// updating overall internal marks table with updated marks						
						Iterator marksEntryDetailsSetIter = null;
						Iterator<MarksEntry> stuMarksEntryIter = studentList.iterator();
						while(stuMarksEntryIter.hasNext()){
							MarksEntry marksEntry = stuMarksEntryIter.next();						
							Map<Integer, StudentOverallInternalMarkDetails> studentInternalMarkMap = transaction.getFinalInternalMarkMap(to.getId(), marksEntry.getExam().getRegularExamId(), marksEntry.getStudent().getId());
							Set<MarksEntryDetails> marksEntryDetailsSet=marksEntry.getMarksDetails();
							marksEntryDetailsSetIter = marksEntryDetailsSet.iterator();
							while(marksEntryDetailsSetIter.hasNext()){
								MarksEntryDetails marksEntryDetails = (MarksEntryDetails) marksEntryDetailsSetIter.next();
								if(studentInternalMarkMap.containsKey(marksEntryDetails.getSubject().getId())) {
									StudentOverallInternalMarkDetails studentMark = studentInternalMarkMap.get(marksEntryDetails.getSubject().getId());
									boolean hasBetterMarks = false;
									if(marksEntryDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")) {
										if(marksEntryDetails.getTheoryMarks() != null &&
										   !marksEntryDetails.getTheoryMarks().isEmpty() &&
										   CommonUtil.isValidDecimal(marksEntryDetails.getTheoryMarks())) {
											if(Double.parseDouble(marksEntryDetails.getTheoryMarks()) > Double.parseDouble(studentMark.getTheoryTotalMarks())) {
												studentMark.setTheoryTotalMarks(marksEntryDetails.getTheoryMarks());
												hasBetterMarks = true;
											}
										}										
									}
									else if(marksEntryDetails.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")) {
										if(marksEntryDetails.getPracticalMarks() != null &&
										   !marksEntryDetails.getPracticalMarks().isEmpty() &&
										   CommonUtil.isValidDecimal(marksEntryDetails.getPracticalMarks())) {
											if(Double.parseDouble(marksEntryDetails.getPracticalMarks()) > Double.parseDouble(studentMark.getPracticalTotalMarks())) {
												studentMark.setPracticalTotalMarks(marksEntryDetails.getPracticalMarks());
												hasBetterMarks = true;
											}
										}										
									}
									else {
										if(marksEntryDetails.getTheoryMarks() != null &&
										   !marksEntryDetails.getTheoryMarks().isEmpty() &&
										   CommonUtil.isValidDecimal(marksEntryDetails.getTheoryMarks())) {
											if(Double.parseDouble(marksEntryDetails.getTheoryMarks()) > Double.parseDouble(studentMark.getTheoryTotalMarks())) {
												studentMark.setTheoryTotalMarks(marksEntryDetails.getTheoryMarks());
												hasBetterMarks = true;
											}
										}
										if(marksEntryDetails.getPracticalMarks() != null &&
										   !marksEntryDetails.getPracticalMarks().isEmpty() &&
										   CommonUtil.isValidDecimal(marksEntryDetails.getPracticalMarks())) {
											if(Double.parseDouble(marksEntryDetails.getPracticalMarks()) > Double.parseDouble(studentMark.getPracticalTotalMarks())) {
												studentMark.setPracticalTotalMarks(marksEntryDetails.getPracticalMarks());
												hasBetterMarks = true;
											}
										}
									}
									if(hasBetterMarks) {
										ExamDefinition exam = new ExamDefinition();
										exam.setId(examId);
										studentMark.setExamForRedo(exam);
									}
									studentMark.setLastModifiedDate(new Date());
									modifiedStudentOverallInternalMarkDetailsBoList.add(studentMark);
								}
							}
						}
					}
				}
			}
		}
		if(modifiedStudentOverallInternalMarkDetailsBoList.isEmpty()) {
			throw new DataNotFoundException();
		}
		return transaction.updateOverAllInternalmarksDetails(modifiedStudentOverallInternalMarkDetailsBoList);
	}
		
	private List<MarksEntry> getStudentListForClass(int classId,int examId) throws Exception {
		List<MarksEntry> marksEntryList=new ArrayList<MarksEntry>();
		String query= "from MarksEntry m where m.exam.id="+examId+" and m.classes.id=" +classId;
		marksEntryList = PropertyUtil.getInstance().getListOfData(query);
		return marksEntryList;
	}
	
	private Map<Integer,List<StudentOverallInternalMarkDetails>> getStudentOverallMarks(Map<Integer,List<Integer>> stuMapWithSubjects,INewUpdateProccessTransaction transaction,int classId) throws Exception {
		return transaction.getStudentOverallMarks(stuMapWithSubjects,classId);
	}
}
