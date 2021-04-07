package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.SupplementaryDataCreationForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.to.exam.SupplementaryDataCreationTO;
import com.kp.cms.transactions.exam.ISupplementaryDataCreationTransaction;
import com.kp.cms.transactionsimpl.exam.SupplementaryDataCreationImpl;

public class SupplementaryDataCreationHelper {
	private static volatile SupplementaryDataCreationHelper helper = null;
	public static SupplementaryDataCreationHelper getInstance(){
		if(helper == null){
			helper = new SupplementaryDataCreationHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	ISupplementaryDataCreationTransaction transaction = SupplementaryDataCreationImpl.getInstance();
	public String getQueryForSupplementryDataCreation( SupplementaryDataCreationForm objForm) throws Exception{
		String query="select classwise.curriculumSchemeDuration.academicYear," +
		" classes,c.year" +
		" from ExamDefinition e" +
		" join e.courseSchemeDetails courseDetails" +
		" join courseDetails.course.classes classes" +
		" join classes.classSchemewises classwise" +
		" join classwise.curriculumSchemeDuration.curriculumScheme c" +
		" where  e.delIsActive=1 and e.id="+objForm.getExamId();
		query=query+" and classwise.curriculumSchemeDuration.academicYear>=e.examForJoiningBatch" ;
		query=query+" and classes.termNumber=courseDetails.schemeNo" +
					" and classes.isActive=1 order by classes.name";
		return query;
	}
	/**
	 * @param list
	 * @return
	 */
	public List<ClassesTO> convertBOListToTOList(List list) {
		List<ClassesTO> mainList=new ArrayList<ClassesTO>();
		if(list!=null && !list.isEmpty()){
			Iterator itr=list.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				ClassesTO to=new ClassesTO();
				if(object[0]!=null)
					to.setYear(Integer.parseInt(object[0].toString()));
				if(object[1]!=null){
					Classes c=(Classes)object[1];
					to.setId(c.getId());
					to.setClassName(c.getName());
					to.setTermNo(c.getTermNumber());
					to.setCourseId(c.getCourse().getId());
				}
				if(object[2]!=null){
					to.setBatchYear(Integer.parseInt(object[2].toString()));
				}
				mainList.add(to);	
			}
		}
		return mainList;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentSupplementaryImprovementApplication> getBoListFromToList( SupplementaryDataCreationForm objForm) throws Exception{
		List<StudentSupplementaryImprovementApplication> boList = new ArrayList<StudentSupplementaryImprovementApplication>();
		List<SupplementaryDataCreationTO> totalFailedListOfStudent = new ArrayList<SupplementaryDataCreationTO>();
		List<ClassesTO> classesTOList= objForm.getClassesTOList();					//getting classesList from the form 
		List<String> examAbscentCode = CMSConstants.EXAM_ABSCENT_CODE;				// getting the absent code list from Exam Settings
		String absentCode1 = null;
		String absentCode2 = null;
		if(examAbscentCode!=null && !examAbscentCode.isEmpty()){
			Iterator<String> iterator = examAbscentCode.iterator();
			while (iterator.hasNext()) {
				String str = (String) iterator.next(); 
				if(absentCode1==null){
					 absentCode1 = str;
				}else {
					absentCode2 = str;
				}
			}
		}
		/* iterating the classes list and getting the students from each class which is checked ON */
		if(classesTOList!=null && !classesTOList.isEmpty()){
			Iterator<ClassesTO> iterator = classesTOList.iterator();
			while (iterator.hasNext()) {
				ClassesTO classTO = (ClassesTO) iterator.next();
				if(classTO.getChecked()!=null && !classTO.getChecked().isEmpty() && classTO.getChecked().equalsIgnoreCase("on")){
					/* getting the map of the subjects min and max Marks from SubjectRuleSettings*/
					Map<Integer,SubjectMarksTO> minMarks = transaction.getMinMarksMap(classTO);	
					/* ---------------------------------------------*/		
					/* getting the map of the subjects sectionName from EXAM_sub_definition_coursewise table*/
					Map<Integer,String> subjectSections = transaction.getSubjectSections(classTO.getCourseId(), classTO.getTermNo(),classTO.getYear());
					/* ---------------------------------------------*/	
					List<Integer> excludedList =  transaction.getExcludedFromResultSubjects(classTO.getCourseId(), classTO.getTermNo(),classTO.getYear());
					List<Integer> failureExcludeList = transaction.getExcludedFromTotResultSubjects(classTO.getCourseId(), classTO.getTermNo(),classTO.getYear());
					/* deleting the old records based on EXAM Id*/
					boolean isDeleted=transaction.deleteOldRecords(classTO.getId(),Integer.parseInt(objForm.getExamId()));
					if(isDeleted){
						List<StudentTO> studentList=getStudentListForClass(classTO.getId());
						if(studentList!=null && !studentList.isEmpty()){
							/* Iterating the students list and getting the subjects list based on studentId,classId,SubjectsIdsList*/
							Iterator<StudentTO> iterator2 = studentList.iterator();
							while (iterator2.hasNext()) {
								StudentTO studentTO = (StudentTO) iterator2 .next();
//								if(studentTO.getId() == 19442){ 
								List<Object[]> list= transaction.getDataByStudentIdAndClassId(studentTO.getId(),classTO.getId(),studentTO.getSubjectIdList());
								/* converting the list of objects to map ,here KEY is subjectid and VALUE is list of marks TO*/
								Map<Integer,List<MarksDetailsTO>>  subjectWiseStudentMarksmap = convertFromBOListToMap(list);
								/* ---------------------------------------------*/	
								if(subjectWiseStudentMarksmap!=null && !subjectWiseStudentMarksmap.isEmpty()){
									float totalPart1Subjects = 0 ;
									float totalPart2Subjects = 0;
									/*Iterating the subject wise marks list of the student and checking whether the student is pass or fail*/
									Iterator<Map.Entry<Integer,List<MarksDetailsTO>>> it = subjectWiseStudentMarksmap.entrySet().iterator();
									Map<Integer,MarksDetailsTO> failedSubjectList = new HashMap<Integer, MarksDetailsTO>();
									List<SupplementaryDataCreationTO> totalFinalSubjectListOfStudent = new ArrayList<SupplementaryDataCreationTO>();
									float totalSubjectsMaxMarksOfPart1 = 0;
									float totalSubjectsMaxMarksOfPart2 = 0 ;
									boolean isPart1SubjectFailed = false;
									boolean isPart2SubjectFailed = false;
									while (it.hasNext()) {
										Map.Entry<Integer,List<MarksDetailsTO>> entry = (Map.Entry) it.next();
										/* if map contains subjectId then TRUE ,otherwise FALSE*/
										if(minMarks.containsKey(entry.getKey())){    										
											SubjectMarksTO subjectMinmarksTO = minMarks.get(entry.getKey());
											/* getting the marks details from the value of map*/
											List<MarksDetailsTO> marksDetailsTOList = entry.getValue();
												if(marksDetailsTOList!=null && !marksDetailsTOList.isEmpty()){
													Iterator<MarksDetailsTO> iterator3 = marksDetailsTOList.iterator();
													boolean isTheoryCalc = false;
													boolean isPracticalCalc = false;
													int chanceCount = 0;
													SupplementaryDataCreationTO to = new SupplementaryDataCreationTO();
													while (iterator3.hasNext()) {
														MarksDetailsTO marksDetailsTO = (MarksDetailsTO) iterator3 .next();
														chanceCount++;
														/* taking the latest record of the subject */
														if(!isTheoryCalc){						
															to.setStudentId(studentTO.getId());
															to.setSubjectId(entry.getKey());
															to.setClassId(classTO.getId());
															to.setSchemeNo(String.valueOf(classTO.getTermNo()));
															if(!marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode1)){ 
																if(!marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode2)){
																	to.setTheoryMarks(marksDetailsTO.getTheoryMarks());
																}
															}
															/* if the marks less than MIN marks OR Absent making the TheoryFail property TRUE*/
															if(marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode1)){
																to.setTheoryFail(true);
																failedSubjectList.put(entry.getKey(), marksDetailsTO);			//if subject is failed putting it into map
															}else if(marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode2)){
																	to.setTheoryFail(true);
																	failedSubjectList.put(entry.getKey(), marksDetailsTO);			//if subject is failed putting it into map
															}else if(subjectMinmarksTO.getTheoryRegMin()!=null && !subjectMinmarksTO.getTheoryRegMin().isEmpty()){
																if(Float.parseFloat(marksDetailsTO.getTheoryMarks())< Float.parseFloat(subjectMinmarksTO.getTheoryRegMin())){
																	to.setTheoryFail(true);
																	failedSubjectList.put(entry.getKey(), marksDetailsTO);			//if subject is failed putting it into map
																}
															}
															/* identifying the subject belongs to PART1 Or PART2*/
															if(subjectSections.containsKey(entry.getKey())){
																String subjectSectionName = subjectSections.get(entry.getKey());
																if(subjectSectionName.equalsIgnoreCase("Part1")){
																	if((!marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode1)) && (!marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode2))){
																		/* sum of the part1 subjects Marks*/
																		totalPart1Subjects= totalPart1Subjects + Float.parseFloat(marksDetailsTO.getTheoryMarks());
																		/*---------------------------------------*/
																	}
																	/* sum of the MAXIMUM Marks of  part1 subjects*/
																	if(subjectMinmarksTO.getFinalTheoryMarks()!=null && !subjectMinmarksTO.getFinalTheoryMarks().isEmpty()){
																		totalSubjectsMaxMarksOfPart1 = totalSubjectsMaxMarksOfPart1 + Float.parseFloat(subjectMinmarksTO.getFinalTheoryMarks());
																	}
																	/*---------------------------------------*/
																	to.setSubjectPart1(true);
																	if(to.isTheoryFail()){
																		isPart1SubjectFailed = true;
																	}
																}else if(subjectSectionName.equalsIgnoreCase("Part2")){
																	if((!marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode1)) && (!marksDetailsTO.getTheoryMarks().equalsIgnoreCase(absentCode2))){
																		/* sum of the part2 subjects Marks*/
																		totalPart2Subjects = totalPart2Subjects + Float.parseFloat(marksDetailsTO.getTheoryMarks());
																		/*---------------------------------------*/
																	}
																	/* sum of the MAXIMUM Marks of  part2 subjects*/
																	if(subjectMinmarksTO.getFinalTheoryMarks()!=null && !subjectMinmarksTO.getFinalTheoryMarks().isEmpty()){
																		totalSubjectsMaxMarksOfPart2 = totalSubjectsMaxMarksOfPart2 + Float.parseFloat(subjectMinmarksTO.getFinalTheoryMarks());
																	}
																	/*---------------------------------------*/
																	to.setSubjectPart2(true);
																	if(to.isTheoryFail()){
																		isPart2SubjectFailed = true;
																	}
																}
															}
															/*---------------------------------------*/
																isTheoryCalc = true;
														}
														if(!isPracticalCalc){
															if(marksDetailsTO.getPracticalMarks()!=null && !marksDetailsTO.getPracticalMarks().isEmpty()){
																/* if the marks less than MIN marks OR Absent making the PracticalFail property TRUE
																if(marksDetailsTO.getPracticalMarks().equalsIgnoreCase(absentCode1)){
																	//failedSubjectList.put(entry.getKey(), marksDetailsTO);
																	to.setPracticalFail(true);
																}else if(marksDetailsTO.getPracticalMarks().equalsIgnoreCase(absentCode2)){
																	//failedSubjectList.put(entry.getKey(), marksDetailsTO);
																	to.setPracticalFail(true);
																}else if(subjectMinmarksTO.getPracticalRegMin()!=null && !subjectMinmarksTO.getPracticalRegMin().isEmpty()){
																
																	 if(Float.parseFloat(marksDetailsTO.getPracticalMarks())<Float.parseFloat(subjectMinmarksTO.getPracticalRegMin())){
																	//failedSubjectList.put(entry.getKey(), marksDetailsTO);
																	to.setPracticalFail(true);
																}
																}*/
																
																
																
																/* if practical min marks are defined in Subject Rule Setting then checking practical marks of the student is less than  
																 * with the min marks of Subject Rule Setting ,if it is less than that make isFailedPractical true
																 */
																if(subjectMinmarksTO.getPracticalRegMin()!=null && !subjectMinmarksTO.getPracticalRegMin().isEmpty()){
																	if(marksDetailsTO.getPracticalMarks().equalsIgnoreCase(absentCode1)){
																		failedSubjectList.put(entry.getKey(), marksDetailsTO);
																		to.setPracticalFail(true);
																	}else if(marksDetailsTO.getPracticalMarks().equalsIgnoreCase(absentCode2)){
																		failedSubjectList.put(entry.getKey(), marksDetailsTO);
																		to.setPracticalFail(true);
																	}else if(Float.parseFloat(marksDetailsTO.getPracticalMarks())<Float.parseFloat(subjectMinmarksTO.getPracticalRegMin())){
																		failedSubjectList.put(entry.getKey(), marksDetailsTO);
																		to.setPracticalFail(true);
																	}
																}
																if(!marksDetailsTO.getPracticalMarks().equalsIgnoreCase(absentCode1)){
																	if(!marksDetailsTO.getPracticalMarks().equalsIgnoreCase(absentCode1)){
																		to.setPracticalMarks(marksDetailsTO.getPracticalMarks());
																	}
																}
																if(subjectMinmarksTO.getFinalPracticalMarks()!=null && !subjectMinmarksTO.getFinalPracticalMarks().isEmpty()){
																	if(to.isSubjectPart1()){
																		totalSubjectsMaxMarksOfPart1 = totalSubjectsMaxMarksOfPart1 + Float.parseFloat(subjectMinmarksTO.getFinalPracticalMarks());
																		if(to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty()){
																			totalPart1Subjects = totalPart1Subjects + Float.parseFloat(to.getPracticalMarks());
																			if(to.isPracticalFail()){											//newly added
																				isPart1SubjectFailed = true;
																			}
																		}
																	}else if(to.isSubjectPart2()){
																		 totalSubjectsMaxMarksOfPart2 = totalSubjectsMaxMarksOfPart2 + Float.parseFloat(subjectMinmarksTO.getFinalPracticalMarks());
																		 if(to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty()){
																		 totalPart2Subjects = totalPart2Subjects + Float.parseFloat(to.getPracticalMarks());
																		 	if(to.isPracticalFail()){											//newly added
																				isPart2SubjectFailed = true;
																			}
																		 }
																	}
																}
																
																isPracticalCalc = true;
															}
														}
													}
													/*combining theory and practical marks and checking with Theory and Practical Min marks from Subject Rule Setting 
													 * in case if it is fail ,make theory as fail
													 */
													if(!to.isTheoryFail() || !to.isPracticalFail()){
														float theoryAndPracticalMarks =0 ;
														if(to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()){
															if(to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty()){
																 theoryAndPracticalMarks = Float.parseFloat(to.getTheoryMarks()) + Float.parseFloat(to.getPracticalMarks());
																 to.setTheoryPracticalMarks(String.valueOf(theoryAndPracticalMarks));
															}else{
																theoryAndPracticalMarks = Float.parseFloat(to.getTheoryMarks());
																to.setTheoryPracticalMarks(String.valueOf(theoryAndPracticalMarks));
															}
														}
														if(subjectMinmarksTO.getSubjectFinalMinimum()!=null && !subjectMinmarksTO.getSubjectFinalMinimum().isEmpty()){
															float therotyPracticalMinMarks = Float.parseFloat(subjectMinmarksTO.getSubjectFinalMinimum());
															if(theoryAndPracticalMarks < therotyPracticalMinMarks){
																to.setTheoryFail(true);
															}
														}
													}
													to.setChanceCount(chanceCount) ;
													totalFinalSubjectListOfStudent.add(to);					// adding the TO'S to ArrayList 
												}
										}
									}
									/*Calculating the total percentage of Part 1 */
									String minPercentageOfPartSubjects = CMSConstants.MIN_PERCENTAGE_OF_PART_SUBJECTS;
									float minPercentage = Float.parseFloat(minPercentageOfPartSubjects);
									if(totalPart1Subjects!=0){
										Float totalPercentagePart1 =((totalPart1Subjects * 100) / totalSubjectsMaxMarksOfPart1);
										if(totalPercentagePart1 < minPercentage){
											if(totalFinalSubjectListOfStudent!=null && !totalFinalSubjectListOfStudent.isEmpty()){
												Iterator<SupplementaryDataCreationTO> itr = totalFinalSubjectListOfStudent.iterator();
												while (itr .hasNext()) {
													SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) itr .next();
													if(failedSubjectList.containsKey(to.getSubjectId())){
														if(to.isSubjectPart1()){
															if(to.isTheoryFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																totalFailedListOfStudent.add(to);				// if fail putting into the totalFailedListOfStudent list
																}
															}else if(to.isPracticalFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																totalFailedListOfStudent.add(to);
																}
															}
														}
													}else if(to.isSubjectPart1()){
														if(Float.parseFloat(to.getTheoryPracticalMarks())< minPercentage){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																to.setTheoryFail(true);
															totalFailedListOfStudent.add(to);
															}
														}
													}
												}
											}
										}else {
											if(totalFinalSubjectListOfStudent!=null && !totalFinalSubjectListOfStudent.isEmpty()){
												//boolean isSubjectFailed = false;
												Iterator<SupplementaryDataCreationTO> itr = totalFinalSubjectListOfStudent.iterator();
												while (itr .hasNext()) {
													SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) itr .next();
													if(failedSubjectList.containsKey(to.getSubjectId())){
														if(to.isSubjectPart1()){
															if(to.isTheoryFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																	//isSubjectFailed = true; 				
																	totalFailedListOfStudent.add(to);
																}
															}else if(to.isPracticalFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																	//isSubjectFailed = true; 
																	totalFailedListOfStudent.add(to);
																}
															}
														}
													}else if(to.isSubjectPart1()){
														/*if(Float.parseFloat(to.getTheoryMarks())< minPercentage){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																to.setTheoryFail(true);
																totalFailedListOfStudent.add(to);
															}
														}*/
														if(to.isTheoryFail()){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																//isSubjectFailed = true; 
																totalFailedListOfStudent.add(to);
															}
														}else if(Float.parseFloat(to.getTheoryPracticalMarks())< minPercentage){
															if(isPart1SubjectFailed){        // if any one subject is failed in part 1 ,then we need to check 
																						//remaining subjects which are in the part 1 are less than the total aggregate ,
																						//if any subject is less than the total aggregate ,then consider that subject as a fail subject. 
																to.setTheoryFail(true);
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																	totalFailedListOfStudent.add(to);
																	}
															}
														}
													}
												}
											}
										}
									}// modified code
									else{
										if(totalFinalSubjectListOfStudent!=null && !totalFinalSubjectListOfStudent.isEmpty()){
											Iterator<SupplementaryDataCreationTO> itr = totalFinalSubjectListOfStudent.iterator();
											while (itr .hasNext()) {
												SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) itr .next();
												if(failedSubjectList.containsKey(to.getSubjectId())){
													if(to.isSubjectPart1()){
														if(to.isTheoryFail()){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
															totalFailedListOfStudent.add(to);				// if fail putting into the totalFailedListOfStudent list
															}
														}else if(to.isPracticalFail()){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
															totalFailedListOfStudent.add(to);
															}
														}
													}
												}else if(to.isSubjectPart1()){
													if(Float.parseFloat(to.getTheoryPracticalMarks())< minPercentage){
														if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
															to.setTheoryFail(true);
														totalFailedListOfStudent.add(to);
														}
													}
												}
											}
										
										}
									}
									/*-----------------------------------------------*/
									/*Calculating the total percentage of Part 2 */
									if(totalPart2Subjects!=0){
										float totalPercentagePart2 = ((totalPart2Subjects * 100) / totalSubjectsMaxMarksOfPart2);
										if(totalPercentagePart2 < minPercentage){
											if(totalFinalSubjectListOfStudent!=null && !totalFinalSubjectListOfStudent.isEmpty()){
												Iterator<SupplementaryDataCreationTO> itr1 = totalFinalSubjectListOfStudent.iterator();
												while (itr1 .hasNext()) {
													SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) itr1 .next();
													//if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ //
													if(failedSubjectList.containsKey(to.getSubjectId())){
														if(to.isSubjectPart2()){
															if(to.isTheoryFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																totalFailedListOfStudent.add(to);
																}
															}else if(to.isPracticalFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																totalFailedListOfStudent.add(to);
																}
															}
														}
													}else if(to.isSubjectPart2()){
														if(Float.parseFloat(to.getTheoryPracticalMarks())< minPercentage){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																to.setTheoryFail(true);
																totalFailedListOfStudent.add(to);
															}
														}
													}
												//}
												}
											}
										}else{
											if(totalFinalSubjectListOfStudent!=null && !totalFinalSubjectListOfStudent.isEmpty()){
												//boolean isSubjectFailed = false;
												Iterator<SupplementaryDataCreationTO> itr1 = totalFinalSubjectListOfStudent.iterator();
												while (itr1 .hasNext()) {
													SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) itr1 .next();
													//if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ //
													if(failedSubjectList.containsKey(to.getSubjectId())){
														if(to.isSubjectPart2()){
															if(to.isTheoryFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																	//isSubjectFailed = true;
																	totalFailedListOfStudent.add(to);
																}
															}else if(to.isPracticalFail()){
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																	//isSubjectFailed = true;
																	totalFailedListOfStudent.add(to);
																}
															}
														}
													}else if(to.isSubjectPart2()){
														/*if(Float.parseFloat(to.getTheoryMarks())< minPercentage){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																to.setTheoryFail(true);
																totalFailedListOfStudent.add(to);
															}
														}*/
														if(to.isTheoryFail()){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																//isSubjectFailed = true;
																totalFailedListOfStudent.add(to);
															}
														}else if(Float.parseFloat(to.getTheoryPracticalMarks())< minPercentage){
															if(isPart2SubjectFailed){				// if any one subject is failed in part 2 ,then we need to check 
																								//remaining subjects which are in the part 2 are less than the total aggregate ,
																								//if any subject is less than the total aggregate ,then consider that subject as a fail subject.
																to.setTheoryFail(true);
																if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
																	totalFailedListOfStudent.add(to);
																	}
															}
														}
													
													}
												//}
												}
											}
										}
									}else {
										if(totalFinalSubjectListOfStudent!=null && !totalFinalSubjectListOfStudent.isEmpty()){

											Iterator<SupplementaryDataCreationTO> itr1 = totalFinalSubjectListOfStudent.iterator();
											while (itr1 .hasNext()) {
												SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) itr1 .next();
												//if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ //
												if(failedSubjectList.containsKey(to.getSubjectId())){
													if(to.isSubjectPart2()){
														if(to.isTheoryFail()){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
															totalFailedListOfStudent.add(to);
															}
														}else if(to.isPracticalFail()){
															if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
															totalFailedListOfStudent.add(to);
															}
														}
													}
												}else if(to.isSubjectPart2()){
													if(Float.parseFloat(to.getTheoryPracticalMarks())< minPercentage){
														if((!excludedList.contains(to.getSubjectId())) || (!failureExcludeList.contains(to.getSubjectId()))){ 
															to.setTheoryFail(true);
															totalFailedListOfStudent.add(to);
														}
													}
												}
											//}
											}
										
										}
									}
									/*--------------------------------------------*/
								}
//				}//remove this
							}
						}
					}
				}
			}
		}
		/* Finally Iterating the failed list of Students and putting it into BO*/
		if(totalFailedListOfStudent!=null && !totalFailedListOfStudent.isEmpty()){
			Iterator<SupplementaryDataCreationTO> iterator = totalFailedListOfStudent.iterator();
			while (iterator.hasNext()) {
				SupplementaryDataCreationTO to = (SupplementaryDataCreationTO) iterator .next();
				StudentSupplementaryImprovementApplication suppImpAppBO  = new StudentSupplementaryImprovementApplication();
				ExamDefinition examDefBO = new ExamDefinition();
				examDefBO.setId(Integer.parseInt(objForm.getExamId()));
				suppImpAppBO.setExamDefinition(examDefBO);
				Subject subject = new Subject();
				subject.setId(to.getSubjectId());
				suppImpAppBO.setSubject(subject);
				Classes classes = new Classes();
				classes.setId(to.getClassId());
				suppImpAppBO.setClasses(classes);
				Student student = new Student();
				student.setId(to.getStudentId());
				suppImpAppBO.setStudent(student);
				suppImpAppBO.setSchemeNo(Integer.parseInt(to.getSchemeNo()));
				suppImpAppBO.setIsSupplementary(true);
				suppImpAppBO.setCreatedBy(objForm.getUserId());
				suppImpAppBO.setCreatedDate(new Date());
				if(to.getChanceCount()!=0){
					suppImpAppBO.setChance(to.getChanceCount());
				}
				if(to.isTheoryFail()){
					suppImpAppBO.setIsFailedTheory(true);
				}else{
					suppImpAppBO.setIsFailedTheory(false);
				}
				if(to.isPracticalFail()){
					suppImpAppBO.setIsFailedPractical(true);
				}else{
					suppImpAppBO.setIsFailedPractical(false);
				}
				suppImpAppBO.setIsAppearedTheory(false);
				suppImpAppBO.setIsAppearedPractical(false);
				suppImpAppBO.setIsImprovement(false);
				boList.add(suppImpAppBO);
			}
		}
		
		return boList;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, List<MarksDetailsTO>> convertFromBOListToMap( List<Object[]> list) throws Exception{
		Map<Integer, List<MarksDetailsTO>> subjectWiseStudentMarksMap = new HashMap<Integer, List<MarksDetailsTO>>();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> iterator = list.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(subjectWiseStudentMarksMap.containsKey(Integer.parseInt(obj[0].toString()))){
					List<MarksDetailsTO> list2 = subjectWiseStudentMarksMap.remove(Integer.parseInt(obj[0].toString()));
					MarksDetailsTO marksDetailsTO = new MarksDetailsTO();
					if(obj[0]!=null && !obj[0].toString().isEmpty()){
						marksDetailsTO.setSubjectId(obj[0].toString());
					}
					if(obj[1]!=null && !obj[1].toString().isEmpty()){
						marksDetailsTO.setTheoryMarks(obj[1].toString());
					}
					if(obj[2]!=null && !obj[2].toString().isEmpty()){
						marksDetailsTO.setPracticalMarks(obj[2].toString());
					}
					list2.add(marksDetailsTO);
					subjectWiseStudentMarksMap.put(Integer.parseInt(obj[0].toString()), list2);
				}else {
					MarksDetailsTO marksDetailsTO = new MarksDetailsTO();
					List<MarksDetailsTO> tos = new ArrayList<MarksDetailsTO>();
					if(obj[0]!=null && !obj[0].toString().isEmpty()){
						marksDetailsTO.setSubjectId(obj[0].toString());
					}
					if(obj[1]!=null && !obj[1].toString().isEmpty()){
						marksDetailsTO.setTheoryMarks(obj[1].toString());
					}
					if(obj[2]!=null && !obj[2].toString().isEmpty()){
						marksDetailsTO.setPracticalMarks(obj[2].toString());
					}
					tos.add(marksDetailsTO);
					subjectWiseStudentMarksMap.put(Integer.parseInt(obj[0].toString()), tos);
				}
			}
		}
		return subjectWiseStudentMarksMap;
	}
	/**
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private List<StudentTO> getStudentListForClass(int classId) throws Exception {
		List<StudentTO> studentList=new ArrayList<StudentTO>();
		String query=getCurrentClassQuery(classId);// Getting Current Class Students Query
		List<Student> currentStudentList=transaction.getDataByQuery(query);
		getFinalStudentsForCurrentClass(currentStudentList,studentList);//Adding current Class Students for StudentList
		String preQuery=getPreviousClassQuery(classId);
		List<Object[]> previousStudentList=transaction.getDataByQuery(preQuery);
		getFinalStudentsForPreviousClass(previousStudentList,studentList);
		return studentList;
	}
	/**
	 * @param classId
	 * @return
	 */
	private String getPreviousClassQuery(int classId)throws Exception {
		String query="select s.id,subSet.subject,s.registerNo,s.admAppln.appliedYear from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise" +
		" join classSchemewise.curriculumSchemeDuration cd" +
		" join s.studentSubjectGroupHistory subjHist " +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0 and s.isAdmitted=1 and subSet.isActive=1 and classHis.classes.id=" +classId+
		" and s.classSchemewise.classes.id <> "+classId+
		" and classHis.schemeNo=subjHist.schemeNo" ;
		return query;
}
	/**
	 * @param previousStudentList
	 * @param studentList
	 */
	private void getFinalStudentsForPreviousClass( List<Object[]> previousStudentList, List<StudentTO> studentList) throws Exception{
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
	 * @param currentStudentList
	 * @param studentList
	 */
	private void getFinalStudentsForCurrentClass( List<Student> currentStudentList, List<StudentTO> studentList) throws Exception{
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
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private String getCurrentClassQuery(int classId)throws Exception {
		String query="from Student s" +
		" where s.admAppln.isCancelled=0 and s.isAdmitted=1 " +
		" and s.classSchemewise.classes.id="+classId;
return query;
}
}
