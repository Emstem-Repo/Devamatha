package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.StudentAttendanceForExamForm;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.to.exam.StudentMarksTO;

public class StudentAttendanceForExamHelper {
	/**
	 * Singleton object of StudentAttendanceForExamHelper
	 */
	private static volatile StudentAttendanceForExamHelper studentAttendanceForExamHelper = null;
	private static final Log log = LogFactory.getLog(StudentAttendanceForExamHelper.class);
	private StudentAttendanceForExamHelper() {
		
	}
	/**
	 * return singleton object of StudentAttendanceForExamHelper.
	 * @return
	 */
	public static StudentAttendanceForExamHelper getInstance() {
		if (studentAttendanceForExamHelper == null) {
			studentAttendanceForExamHelper = new StudentAttendanceForExamHelper();
		}
		return studentAttendanceForExamHelper;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		String query=" from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0" +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+studentAttendanceForExamForm.getExamId()+") " +
		" and subGroup.subject.id=" +studentAttendanceForExamForm.getSubjectId();
		return query;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception{
		String query=" from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0" +
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+studentAttendanceForExamForm.getExamId()+")" +
		" and subSet.subject.id="+studentAttendanceForExamForm.getSubjectId()+" and classHis.schemeNo=subjHist.schemeNo";
		return query;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForExistStudentMarks(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		String query="select m.marksEntry.student.registerNo from MarksEntryDetails m" +
		" where m.subject.id=" +studentAttendanceForExamForm.getSubjectId()+
		" and m.marksEntry.exam.id="+studentAttendanceForExamForm.getExamId();
		if(studentAttendanceForExamForm.getSubjectType()!=null && !studentAttendanceForExamForm.getSubjectType().isEmpty()){
			if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and m.theoryMarks is not null ";
			}else if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and m.practicalMarks is not null ";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
			}
		}
		return query;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		String query=" from Student s" +
		" join s.classSchemewise.classes c" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+studentAttendanceForExamForm.getSubjectId()+
		" and supImp.examDefinition.id="+studentAttendanceForExamForm.getExamId()+
		" where s.admAppln.isCancelled=0" +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e where e.id="+studentAttendanceForExamForm.getExamId()+")" +
		" and subGroup.subject.id="+studentAttendanceForExamForm.getSubjectId()+
		" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+studentAttendanceForExamForm.getSchemeNo();
		if(studentAttendanceForExamForm.getSubjectType()!=null && !studentAttendanceForExamForm.getSubjectType().isEmpty()){
			if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and (supImp.isAppearedTheory=1 or supImp.isAppearedPractical=1)";
			}
		}
		return query;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryPreviousClassStudents(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		String query=" from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +studentAttendanceForExamForm.getSubjectId()+
		" and supImp.examDefinition.id=" +studentAttendanceForExamForm.getExamId()+
		" where s.admAppln.isCancelled=0" +
		" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e" +
		" where e.id="+studentAttendanceForExamForm.getExamId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+studentAttendanceForExamForm.getSchemeNo()+
		" and subSet.subject.id="+studentAttendanceForExamForm.getSubjectId()+" and (subjHist.schemeNo = classHis.schemeNo)";
	if(studentAttendanceForExamForm.getSubjectType()!=null && !studentAttendanceForExamForm.getSubjectType().isEmpty()){
		if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
			query=query+" and supImp.isAppearedTheory=1";
		}else if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
			query=query+" and supImp.isAppearedPractical=1";
		}else{
			query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
		}
	}
	return query;
	}
	/**
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentIdQuery(StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		String query="select s.registerNo,s.id from Student s where s.isActive=1 ";
		return query;
	}
	public Map<String, Integer> convertBotoMap(List studentIdList) throws Exception {
		Map<String, Integer> map=new HashMap<String, Integer>();
		if(studentIdList!=null && !studentIdList.isEmpty()){
			Iterator<Object[]> itr=studentIdList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					map.put(obj[0].toString(),Integer.parseInt(obj[1].toString()));
			}
		}
		
		return map;
	}
	/**
	 * @param registerNoList
	 * @param idMap
	 * @param studentAttendanceForExamForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> convertFormToTO(List<String> registerNoList,Map<String, Integer> idMap,StudentAttendanceForExamForm studentAttendanceForExamForm,Map<Integer,Integer> classIdMap) throws Exception {
		List<StudentMarksTO> stuList=new ArrayList<StudentMarksTO>();
		Iterator<String> itr=registerNoList.iterator();
		int evaSize=1;
		int ansSize=1;
		
		
		int examId = Integer.parseInt(studentAttendanceForExamForm.getExamId());
		int subjectId=Integer.parseInt(studentAttendanceForExamForm.getSubjectId());
		int subjectTypeId = 0;
		
		if (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t"))
			subjectTypeId = 1;
		else if (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p"))
			subjectTypeId = 0;
		ExamSecuredMarksEntryHandler securedhandler =ExamSecuredMarksEntryHandler.getInstance();
		List<Integer> evList=new ArrayList<Integer>();
		Map<Integer, String>  evaluatorTypeMap = securedhandler.getEvaluatorType(subjectId,subjectTypeId, examId);
		if(evaluatorTypeMap!=null && !evaluatorTypeMap.isEmpty() && !studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Internal")){
			evList=new ArrayList<Integer>(evaluatorTypeMap.keySet());
			evaSize=evList.size();
		}
		List<Integer> ansList=new ArrayList<Integer>();
		Map<Integer, String> answerScriptTypeMap = securedhandler.get_answerScript_type(subjectId, subjectTypeId, examId);
		if(!answerScriptTypeMap.isEmpty()  && !studentAttendanceForExamForm.getExamType().equalsIgnoreCase("Internal")){
			ansList=new ArrayList<Integer>(answerScriptTypeMap.keySet());
			ansSize=ansList.size();
		}
		while (itr.hasNext()) {
			String regNo = (String) itr.next();
			if(idMap.containsKey(regNo)){
				if(classIdMap.containsKey(idMap.get(regNo))){

					for (int i = 0; i < evaSize; i++) {
						for (int j = 0; j < ansSize; j++) {
							StudentMarksTO to=new StudentMarksTO();
							to.setStudentId(idMap.get(regNo));
							to.setClassId(classIdMap.get(idMap.get(regNo)));
							if (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
								to.setIsTheory(true);
								if(studentAttendanceForExamForm.getIsAbscent().equalsIgnoreCase("yes")){
									to.setTheoryMarks("AA");
								}else{
									to.setTheoryMarks("NP");
								}
							}else if (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
								to.setIsPractical(true);
								if(studentAttendanceForExamForm.getIsAbscent().equalsIgnoreCase("yes")){
									to.setPracticalMarks("AA");
								}else{
									to.setPracticalMarks("NP");
								}
							}
							if(evList!=null && !evList.isEmpty()){
								to.setEvaId(String.valueOf(evList.get(i)));
							}
							if(!ansList.isEmpty()){
								to.setAnsId(String.valueOf(ansList.get(j)));
							}
							stuList.add(to);
						}
					}
				}
			}else if(studentAttendanceForExamForm.getOldRegNOMap() != null && studentAttendanceForExamForm.getOldRegNOMap().containsKey(regNo)){
				if(classIdMap.containsKey(studentAttendanceForExamForm.getOldRegNOMap().get(regNo))){

					for (int i = 0; i < evaSize; i++) {
						for (int j = 0; j < ansSize; j++) {
							StudentMarksTO to=new StudentMarksTO();
							to.setStudentId(studentAttendanceForExamForm.getOldRegNOMap().get(regNo));
							to.setClassId(classIdMap.get(studentAttendanceForExamForm.getOldRegNOMap().get(regNo)));
							if (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
								to.setIsTheory(true);
								if(studentAttendanceForExamForm.getIsAbscent().equalsIgnoreCase("yes")){
									to.setTheoryMarks("AA");
								}else{
									to.setTheoryMarks("NP");
								}
							}else if (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
								to.setIsPractical(true);
								if(studentAttendanceForExamForm.getIsAbscent().equalsIgnoreCase("yes")){
									to.setPracticalMarks("AA");
								}else{
									to.setPracticalMarks("NP");
								}
							}
							if(evList!=null && !evList.isEmpty()){
								to.setEvaId(String.valueOf(evList.get(i)));
							}
							if(!ansList.isEmpty()){
								to.setAnsId(String.valueOf(ansList.get(j)));
							}
							stuList.add(to);
						}
					}
				}
			}
			
		}
		return stuList;
	}
	/**
	 * @param classesList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> convertListBotoMap(List classesList) throws Exception{
		Map<Integer, Integer> map=new HashMap<Integer, Integer>();
		if(classesList!=null && !classesList.isEmpty()){
			Iterator<Object[]> itr=classesList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					map.put(Integer.parseInt(obj[0].toString()),Integer.parseInt(obj[1].toString()));
			}
		}
		
		return map;
	}
	public Map<String, Integer> getOldRegMap(List oldRegList) throws Exception {
		Map<String, Integer> map=new HashMap<String, Integer>();
		if(oldRegList!=null && !oldRegList.isEmpty()){
			Iterator<Object[]> itr=oldRegList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					map.put(obj[1].toString(),Integer.parseInt(obj[0].toString()));
			}
		}
		return map;
	}
}
