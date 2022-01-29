package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.forms.exam.ValuationStatusSubjectWiseForm;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ValuationStatusSubjectWiseTO;

public class ValuationStatusSubjectWiseHelper {
	private static final Log log = LogFactory.getLog(ValuationStatusSubjectWiseHelper.class);
	private static volatile ValuationStatusSubjectWiseHelper helper = null;
	/**
	 * return singleton object of ValuationStatusSubjectWiseHandler.
	 * @return
	 */
	public static ValuationStatusSubjectWiseHelper getInstance(){
		if(helper == null){
			helper = new ValuationStatusSubjectWiseHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) throws Exception{
		String query="select s.id from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0" +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+valuationStatusSubjectWiseForm.getExamId()+") " +
		" and subGroup.subject.id=" +valuationStatusSubjectWiseForm.getSubjectId();
		return query;
	}
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) throws Exception{
		String query="select s.id from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0" +
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+valuationStatusSubjectWiseForm.getExamId()+")" +
		" and subSet.subject.id="+valuationStatusSubjectWiseForm.getSubjectId()+" and classHis.schemeNo=subjHist.schemeNo";
		return query;
	}
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @return
	 */
	public String getQueryForSupplementaryCurrentClassStudents( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) {
		String query="select s.id from Student s" +
		" join s.classSchemewise.classes c" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+valuationStatusSubjectWiseForm.getSubjectId()+
		" and supImp.examDefinition.id="+valuationStatusSubjectWiseForm.getExamId()+
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e where e.id="+valuationStatusSubjectWiseForm.getExamId()+")" +
		" and subGroup.subject.id="+valuationStatusSubjectWiseForm.getSubjectId()+
		" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+valuationStatusSubjectWiseForm.getSchemeNo();
		if(valuationStatusSubjectWiseForm.getSubjectType()!=null && !valuationStatusSubjectWiseForm.getSubjectType().isEmpty()){
			if(valuationStatusSubjectWiseForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(valuationStatusSubjectWiseForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and (supImp.isAppearedTheory=1 or supImp.isAppearedPractical=1)";
			}
		}
		return query;
	}
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @return
	 */
	public String getQueryForAbsentStudents( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) {
		String query="select m.marksEntry.student.id from MarksEntryDetails m"+
		" where m.subject.id=" +valuationStatusSubjectWiseForm.getSubjectId()+
		" and m.marksEntry.exam.id="+valuationStatusSubjectWiseForm.getExamId()+
		" and (m.marksEntry.evaluatorType is null or m.marksEntry.evaluatorType = 1)";
		if(valuationStatusSubjectWiseForm.getSubjectType().equalsIgnoreCase("T")){
			query=query+" and m.theoryMarks = 'AA'";
		}else if(valuationStatusSubjectWiseForm.getSubjectType().equalsIgnoreCase("P")){
			query=query+" and m.practicalMarks = 'AA'";
		}
		query = query + " and m.marksEntry.student.admAppln.isCancelled=0" +
				" and (m.marksEntry.student.isHide=0 or m.marksEntry.student.isHide is null) " +
				" and m.marksEntry.student.isActive=1";
	
	return query;
	}
	/**
	 * @param absentStudentIds
	 * @param currentStudentIdsList
	 * @return
	 * @throws Exception
	 */
	public int getTotalAnswerScripts(List<Integer> absentStudentIds, List<Integer> studentIds)throws Exception {
		int totalAnswerScripts = 0;
		if(studentIds!=null && !studentIds.isEmpty()){
			Iterator<Integer> iterator = studentIds.iterator();
			while (iterator.hasNext()) {
				Integer studentId = (Integer) iterator.next();
				if(!absentStudentIds.contains(studentId)){
					totalAnswerScripts = totalAnswerScripts + 1;
				}
			}
		}
		return totalAnswerScripts;
	}
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @return
	 */
	public String getQueryForSupplementaryPreviousClassStudents( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) {
		String query="select s.id from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +valuationStatusSubjectWiseForm.getSubjectId()+
		" and supImp.examDefinition.id=" +valuationStatusSubjectWiseForm.getExamId()+
		" where s.admAppln.isCancelled=0 and subSet.isActive=1" +
		" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e" +
		" where e.id="+valuationStatusSubjectWiseForm.getExamId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+valuationStatusSubjectWiseForm.getSchemeNo()+
		" and subSet.subject.id="+valuationStatusSubjectWiseForm.getSubjectId();
	if(valuationStatusSubjectWiseForm.getSubjectType()!=null && !valuationStatusSubjectWiseForm.getSubjectType().isEmpty()){
		if(valuationStatusSubjectWiseForm.getSubjectType().equalsIgnoreCase("t")){
			query=query+" and supImp.isAppearedTheory=1";
		}else if(valuationStatusSubjectWiseForm.getSubjectType().equalsIgnoreCase("p")){
			query=query+" and supImp.isAppearedPractical=1";
		}else{
			query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
		}
	}
	return query;
}
	/**
	 * @param valuationStatusSubjectWiseForm
	 * @return
	 * @throws Exception
	 */
	public String getValuationDetailsQuery( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) throws Exception{
		String query = "from ExamValidationDetails valuationDetails" +
				" where valuationDetails.exam.id =" +valuationStatusSubjectWiseForm.getExamId()+
				" and valuationDetails.subject.id =" +valuationStatusSubjectWiseForm.getSubjectId()+
				" and valuationDetails.isActive = 1 ";
		return query;
	}
	/**
	 * @param getValuationDetailsQuery
	 * @param valuationStatusSubjectWiseForm 
	 * @param totalAnswerScripts 
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<ExamValidationDetailsTO>> convertValuationDetailsTOMap( List<ExamValidationDetails> valuationDetailsList, int totalAnswerScripts, ValuationStatusSubjectWiseForm valuationSubjectWiseForm) throws Exception{
		 Map<String, List<ExamValidationDetailsTO>> map = new HashMap<String, List<ExamValidationDetailsTO>>();
		List<ExamValidationDetailsTO> internalValuatorList = new ArrayList<ExamValidationDetailsTO>();
		List<ExamValidationDetailsTO> externalValuatorList = new ArrayList<ExamValidationDetailsTO>();
		List<ExamValidationDetailsTO> projectMinorList = new ArrayList<ExamValidationDetailsTO>();
		List<ExamValidationDetailsTO> projectMajorList = new ArrayList<ExamValidationDetailsTO>();
		List<ExamValidationDetailsTO> reviewerList = new ArrayList<ExamValidationDetailsTO>();
		int internalTotal = 0;
		int externalTotal = 0;
		int reviewerTotal = 0;
		int projectMinor = 0;
		int projectMajor = 0;
		 if(valuationDetailsList!=null && !valuationDetailsList.isEmpty()){
			 Iterator<ExamValidationDetails> iterator = valuationDetailsList.iterator();
			 while (iterator.hasNext()) {
				 
				 ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator .next();
				if(examValidationDetails.getIsValuator().equalsIgnoreCase("Valuator1") 
						&& examValidationDetails.getUsers()!=null && !examValidationDetails.getUsers().toString().isEmpty()){
					int totalInternalValuatorAnswerScripts = 0;
					ExamValidationDetailsTO valuatorTO = new ExamValidationDetailsTO();
					if(examValidationDetails.getUsers().getEmployee()!=null && !examValidationDetails.getUsers().getEmployee().toString().isEmpty()){
						valuatorTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName().toUpperCase());
					}else{
						valuatorTO.setEmployeeName(examValidationDetails.getUsers().getUserName().toUpperCase());
					}
					Set<ExamValuationAnswerScript> answerScripts = examValidationDetails.getAnswerScripts();
					if(answerScripts!=null && !answerScripts.isEmpty()){
						Iterator<ExamValuationAnswerScript> iterator1 = answerScripts.iterator();
						while (iterator1.hasNext()) {
							ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator1 .next();
							if(examValuationAnswerScript.getIsActive().equals(true)){
								totalInternalValuatorAnswerScripts = totalInternalValuatorAnswerScripts + examValuationAnswerScript.getNumberOfAnswerScripts();
							}
						}
						internalTotal = internalTotal + totalInternalValuatorAnswerScripts;
						valuationSubjectWiseForm.setTotalInternalValuator(internalTotal);
						valuatorTO.setAnswerScripts(String.valueOf(totalInternalValuatorAnswerScripts)) ;
					}
					if(!valuatorTO.getAnswerScripts().equalsIgnoreCase("0") && valuatorTO.getAnswerScripts()!=null && !valuatorTO.getAnswerScripts().isEmpty()){
					internalValuatorList.add(valuatorTO);
						if(internalValuatorList!=null && !internalValuatorList.isEmpty()){
							map.put("Internal Valuator", internalValuatorList);
						}
					}
				}else if(examValidationDetails.getIsValuator().equalsIgnoreCase("Valuator1") 
						&& examValidationDetails.getExamValuators() != null
						&& !examValidationDetails.getExamValuators().toString() .isEmpty()) {
					int totalInternalValuatorAnswerScripts = 0;

					ExamValidationDetailsTO valuatorTO = new ExamValidationDetailsTO();
					if (examValidationDetails.getExamValuators().getName() != null
							&& !examValidationDetails.getExamValuators()
									.toString().isEmpty()) {
						valuatorTO.setEmployeeName(examValidationDetails
								.getExamValuators().getName().toUpperCase());
					} 
					Set<ExamValuationAnswerScript> answerScripts = examValidationDetails .getAnswerScripts();
					if (answerScripts != null && !answerScripts.isEmpty()) {
						Iterator<ExamValuationAnswerScript> iterator2 = answerScripts .iterator();
						while (iterator2.hasNext()) {
							ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator2 .next();
							if (examValuationAnswerScript.getIsActive().equals( true)) {
								totalInternalValuatorAnswerScripts = totalInternalValuatorAnswerScripts
																		+ examValuationAnswerScript .getNumberOfAnswerScripts();
							}
						}
						externalTotal = externalTotal + totalInternalValuatorAnswerScripts;
						valuationSubjectWiseForm .setTotalExternalValuator(externalTotal);
						valuatorTO.setAnswerScripts(String .valueOf(totalInternalValuatorAnswerScripts));
					}
					if (!valuatorTO.getAnswerScripts().equalsIgnoreCase("0")
							&& valuatorTO.getAnswerScripts() != null
							&& !valuatorTO.getAnswerScripts().isEmpty()) {
						externalValuatorList.add(valuatorTO);
						if (externalValuatorList != null
								&& !externalValuatorList.isEmpty()) {
							map.put("External Valuator", externalValuatorList);
						}
					}
				}else if(examValidationDetails.getIsValuator().equalsIgnoreCase("Reviewer")){
					ExamValidationDetailsTO valuatorTO = new ExamValidationDetailsTO();
					int totalReviewerAnswerScripts =0;
					if(examValidationDetails.getUsers()!=null && !examValidationDetails.getUsers().toString().isEmpty()){
						if(examValidationDetails.getUsers().getEmployee()!=null && !examValidationDetails.getUsers().getEmployee().toString().isEmpty()){
							valuatorTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName().toUpperCase());
						}else{
							valuatorTO.setEmployeeName(examValidationDetails.getUsers().getUserName().toUpperCase());
						}
					}else if(examValidationDetails.getExamValuators()!=null && !examValidationDetails.getExamValuators().toString().isEmpty()){
						if(examValidationDetails.getExamValuators().getName()!=null && !examValidationDetails.getExamValuators().getName().isEmpty()){
							valuatorTO.setEmployeeName(examValidationDetails.getExamValuators().getName().toUpperCase());
						}
					}
					Set<ExamValuationAnswerScript> answerScripts = examValidationDetails .getAnswerScripts();
					if(answerScripts!=null && !answerScripts.isEmpty()){
						Iterator<ExamValuationAnswerScript> iterator3 = answerScripts.iterator();
						while (iterator3.hasNext()) {
							ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator3 .next();
							if(examValuationAnswerScript.getIsActive().equals(true)){
								totalReviewerAnswerScripts = totalReviewerAnswerScripts + examValuationAnswerScript.getNumberOfAnswerScripts();
							}
						}
						reviewerTotal = reviewerTotal + totalReviewerAnswerScripts;
						valuationSubjectWiseForm.setTotalReviewerValuator(reviewerTotal);
						valuatorTO.setAnswerScripts(String.valueOf(totalReviewerAnswerScripts));
						if(!valuatorTO.getAnswerScripts().equalsIgnoreCase("0") 
								&& valuatorTO.getAnswerScripts()!=null 
								&& !valuatorTO.getAnswerScripts().isEmpty()){
							reviewerList.add(valuatorTO);
							if(reviewerList!=null && !reviewerList.isEmpty()){
								map.put("Reviewer", reviewerList);
							}
						}
					}
				}else if(examValidationDetails.getIsValuator().equalsIgnoreCase("Project Major")){
					ExamValidationDetailsTO valuatorTO = new ExamValidationDetailsTO();
					int totalProjectMajorAnswerScripts = 0;
					if(examValidationDetails.getUsers()!=null && !examValidationDetails.getUsers().toString().isEmpty()){
						if(examValidationDetails.getUsers().getEmployee()!=null && !examValidationDetails.getUsers().getEmployee().toString().isEmpty()){
							valuatorTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName().toUpperCase());
						}else{
							valuatorTO.setEmployeeName(examValidationDetails.getUsers().getUserName().toUpperCase());
						}
					}else if(examValidationDetails.getExamValuators()!=null && !examValidationDetails.getExamValuators().toString().isEmpty()){
						if(examValidationDetails.getExamValuators().getName()!=null && !examValidationDetails.getExamValuators().getName().isEmpty()){
							valuatorTO.setEmployeeName(examValidationDetails.getExamValuators().getName().toUpperCase());
						}
					}
						Set<ExamValuationAnswerScript> answerScripts = examValidationDetails .getAnswerScripts();
						if(answerScripts!=null && !answerScripts.isEmpty()){
							Iterator<ExamValuationAnswerScript> iterator4 = answerScripts.iterator();
							while (iterator4.hasNext()) {
								ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator4 .next();
								if(examValuationAnswerScript.getIsActive().equals(true)){
									totalProjectMajorAnswerScripts = totalProjectMajorAnswerScripts + examValuationAnswerScript.getNumberOfAnswerScripts();
								}
							}
							projectMajor = projectMajor + totalProjectMajorAnswerScripts;
							valuationSubjectWiseForm.setTotalMajorValuator(projectMajor);
							valuatorTO.setAnswerScripts(String.valueOf(totalProjectMajorAnswerScripts));
							if(valuatorTO.getAnswerScripts().equalsIgnoreCase("0") 
									&& valuatorTO.getAnswerScripts()!=null && !valuatorTO.getAnswerScripts().isEmpty()){
								projectMajorList.add(valuatorTO);
								if(projectMajorList!=null && !projectMajorList.isEmpty()){
								map.put("Project Major", projectMajorList);
							}
						}
					}
				}else if(examValidationDetails.getIsValuator().equalsIgnoreCase("Project Minor")){
					ExamValidationDetailsTO valuatorTO = new ExamValidationDetailsTO();
					int totalProjectMinorAnswerScripts = 0;
					if(examValidationDetails.getUsers()!=null && !examValidationDetails.getUsers().toString().isEmpty()){
						if(examValidationDetails.getUsers().getEmployee()!=null && !examValidationDetails.getUsers().getEmployee().toString().isEmpty()){
							valuatorTO.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName().toUpperCase());
						}else{
							valuatorTO.setEmployeeName(examValidationDetails.getUsers().getUserName().toUpperCase());
						}
					}else if(examValidationDetails.getExamValuators()!=null && !examValidationDetails.getExamValuators().toString().isEmpty()){
						if(examValidationDetails.getExamValuators().getName()!=null && !examValidationDetails.getExamValuators().getName().isEmpty()){
							valuatorTO.setEmployeeName(examValidationDetails.getExamValuators().getName().toUpperCase());
						}
					}
					Set<ExamValuationAnswerScript> answerScripts = examValidationDetails .getAnswerScripts();
					if(answerScripts!=null && !answerScripts.isEmpty()){
						Iterator<ExamValuationAnswerScript> iterator5 = answerScripts.iterator();
						while (iterator5.hasNext()) {
							ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator5 .next();
							if(examValuationAnswerScript.getIsActive().equals(true)){
								totalProjectMinorAnswerScripts = totalProjectMinorAnswerScripts + examValuationAnswerScript.getNumberOfAnswerScripts();
							}
						}
						projectMinor = projectMinor + totalProjectMinorAnswerScripts;
						valuationSubjectWiseForm.setTotalMinorValuator(projectMinor);
						valuatorTO.setAnswerScripts(String.valueOf(totalProjectMinorAnswerScripts));
						if(valuatorTO.getAnswerScripts().equalsIgnoreCase("0")
								&& valuatorTO.getAnswerScripts()!=null && !valuatorTO.getAnswerScripts().isEmpty()){
							projectMinorList.add(valuatorTO);
							if(projectMinorList!=null && !projectMinorList.isEmpty()){
								map.put("Project Minor", projectMinorList);
							}
						}
					}
				}
			}
		 }
		return map;
	}
}
