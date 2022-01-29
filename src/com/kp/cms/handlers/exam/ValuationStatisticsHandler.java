package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.forms.exam.ValuationStatisticsForm;
import com.kp.cms.helpers.exam.ExamValuationStatusHelper;
import com.kp.cms.helpers.exam.ValuationStatisticsHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.ValuationStatisticsTO;
import com.kp.cms.transactions.exam.IExamValuationStatusTransaction;
import com.kp.cms.transactions.exam.IValuationStatisticsTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationStatusTxImpl;
import com.kp.cms.transactionsimpl.exam.ValuationStatisticsTxImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ValuationStatisticsHandler {
	/**
	 * Singleton object of NewSecuredMarksEntryHandler
	 */
	private static volatile ValuationStatisticsHandler handler = null;
	private static final Log log = LogFactory.getLog(ValuationStatisticsHandler.class);
	private ValuationStatisticsHandler() {
		
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ValuationStatisticsHandler getInstance() {
		if (handler == null) {
			handler = new ValuationStatisticsHandler();
		}
		return handler;
	}
	IValuationStatisticsTransaction transaction = ValuationStatisticsTxImpl.getInstance();
	
	/**
	 * @param valuationStatisticsForm
	 * @return
	 * @throws Exception
	 */
	public ValuationStatisticsTO getStatistics(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		ValuationStatisticsTO statisticsTO = new ValuationStatisticsTO();
		
		// total subjects for selected exam 
		
		List<Integer> subjects = transaction.getTotalSubjects(valuationStatisticsForm);
		if(subjects != null && !subjects.isEmpty()){
			valuationStatisticsForm.setTotalSubjects(subjects);
			statisticsTO.setTotalSubjects(subjects.size());
			List<Object[]> details;
			if(valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
				details = transaction.getTotalVerfiedStudentForSupplementaryForDashBord(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),subjects,false);
			}else{
				details = transaction.getTotalVerfiedStudentForDashBord(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),subjects,false,valuationStatisticsForm.getExamType());
			}
			if(details != null){
				Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap = new HashMap<Integer, List<ExamValuationStatusTO>>();
				Iterator<Object[]> iterator = details.iterator();
				List<ExamValuationStatusTO> list=null;
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					int id=0;
					if(objects[1] != null && objects[1].toString() != null){
						id = Integer.parseInt(objects[1].toString());
					}
					if(verifyDetailsMap.containsKey(id)){
						list=verifyDetailsMap.remove(id);
					}else
						list=new ArrayList<ExamValuationStatusTO>();
					ExamValuationStatusTO to = new ExamValuationStatusTO();
					if(objects[9] != null && objects[9].toString() != null){
						to.setCourseId(Integer.parseInt(objects[9].toString()));
					}
					if(objects[0] != null && objects[0].toString() != null){
						to.setSubjectName(objects[0].toString());
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
					if(objects[15] != null && objects[15].toString() != null){
						to.setTotalAbsent(Integer.parseInt(objects[15].toString()));
					}
					list.add(to);
					verifyDetailsMap.put(id, list);
					
				}
				List<Integer> issuedForValuation = transaction.getIssuedForValuationSubjects(valuationStatisticsForm);
				List<Integer> valuationCompletedSubjects = ValuationStatisticsHelper.getInstance().getValuationCompletedSubjectsNew(verifyDetailsMap,valuationStatisticsForm,issuedForValuation);
				valuationStatisticsForm.setTotalValCompletedSubjects(valuationCompletedSubjects);
				statisticsTO.setValuationCompleted(valuationCompletedSubjects.size());
				statisticsTO.setValuationInProcess(valuationStatisticsForm.getValInProgress().size());
				if(subjects.size()-valuationCompletedSubjects.size()-valuationStatisticsForm.getValInProgress().size() >0){
					statisticsTO.setValuationNotStarted(subjects.size()-valuationCompletedSubjects.size()-valuationStatisticsForm.getValInProgress().size());
				}else{
					statisticsTO.setValuationNotStarted(0);
				}
			}
		}
		HibernateUtil.closeSession();
		return statisticsTO;
	}
	/**
	 * @param valuationStatisticsForm
	 * @return
	 * @throws Exception
	 */
	public List<ValuationStatisticsTO> getStatisticsDeaneryWise(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		List<Subject> subjectList = transaction.getTotalSubjectsForExam(valuationStatisticsForm);
		List<ValuationStatisticsTO> deaneryWiseValuationStatistics = new ArrayList<ValuationStatisticsTO>();
		if(subjectList != null){
			
			// deanery wise total subjects
			Map<Integer, List<Subject>> subjectsMapDeaneryWise = new HashMap<Integer, List<Subject>>();
			List<Subject> subjectListDeaneryWise = null;
			
			Map<Integer, List<Integer>> subjectIdsMapDeaneryWise = new HashMap<Integer, List<Integer>>();
			List<Integer> subjectIdListDeaneryWise = null;
			
			// deanery wise valuation completed subject list
			Map<Integer, List<Integer>> valCompletedDeaneryWise = new HashMap<Integer, List<Integer>>();
			List<Integer> valCompletedListDeaneryWise = null;
			
			// deanery wise valuation In progress subject list
			Map<Integer, List<Integer>> valInprogressDeaneryWise = new HashMap<Integer, List<Integer>>();
			List<Integer> valInprogressListDeaneryWise = null;
			
			
			Iterator<Subject> iterator = subjectList.iterator();
			while (iterator.hasNext()) {
				Subject subject = (Subject) iterator.next();
				 if(subject.getDepartment() != null && subject.getDepartment().getEmployeeStreamBO() != null && subject.getDepartment().getEmployeeStreamBO().getId()!=0){
					 
					 // deanery wise total subjects 
					 if(subjectsMapDeaneryWise.containsKey(subject.getDepartment().getEmployeeStreamBO().getId())){
						 subjectListDeaneryWise = subjectsMapDeaneryWise.remove(subject.getDepartment().getEmployeeStreamBO().getId());
					 }else{
						 subjectListDeaneryWise = new ArrayList<Subject>();
					 }
					 subjectListDeaneryWise.add(subject);
					 subjectsMapDeaneryWise.put(subject.getDepartment().getEmployeeStreamBO().getId(), subjectListDeaneryWise);
					 if(subjectIdsMapDeaneryWise.containsKey(subject.getDepartment().getEmployeeStreamBO().getId())){
						 subjectIdListDeaneryWise = subjectIdsMapDeaneryWise.remove(subject.getDepartment().getEmployeeStreamBO().getId());
					 }else{
						 subjectIdListDeaneryWise = new ArrayList<Integer>();
					 }
					 subjectIdListDeaneryWise.add(subject.getId());
					 subjectIdsMapDeaneryWise.put(subject.getDepartment().getEmployeeStreamBO().getId(), subjectIdListDeaneryWise);
					 
					 // deanery wise valuation completed subjects
					 
					 if(valuationStatisticsForm.getTotalValCompletedSubjects() != null 
							 && valuationStatisticsForm.getTotalValCompletedSubjects().contains(subject.getId())){
						 if(valCompletedDeaneryWise.containsKey(subject.getDepartment().getEmployeeStreamBO().getId())){
							 valCompletedListDeaneryWise = valCompletedDeaneryWise.remove(subject.getDepartment().getEmployeeStreamBO().getId());
						 }else{
							 valCompletedListDeaneryWise = new ArrayList<Integer>();
						 }
						 valCompletedListDeaneryWise.add(subject.getId());
						 valCompletedDeaneryWise.put(subject.getDepartment().getEmployeeStreamBO().getId(), valCompletedListDeaneryWise);
					 }
					 
					// deanery wise valuation In progress subject list
					 
					 if(valuationStatisticsForm.getValInProgress() != null 
							 && valuationStatisticsForm.getValInProgress().contains(subject.getId())){
						 if(valInprogressDeaneryWise.containsKey(subject.getDepartment().getEmployeeStreamBO().getId())){
							 valInprogressListDeaneryWise = valInprogressDeaneryWise.remove(subject.getDepartment().getEmployeeStreamBO().getId());
						 }else{
							 valInprogressListDeaneryWise = new ArrayList<Integer>();
						 }
						 valInprogressListDeaneryWise.add(subject.getId());
						 valInprogressDeaneryWise.put(subject.getDepartment().getEmployeeStreamBO().getId(), valInprogressListDeaneryWise);
					 }
				 }
			}
			
			if(subjectIdsMapDeaneryWise != null){
				Iterator<Entry<Integer, List<Integer>>> iterator2 = subjectIdsMapDeaneryWise.entrySet().iterator();
				while (iterator2.hasNext()) {
					Map.Entry<Integer, List<Integer>> entry = (Map.Entry<Integer, List<Integer>>) iterator2.next();
					if(subjectsMapDeaneryWise.get(entry.getKey()) != null){
						ValuationStatisticsTO to = new ValuationStatisticsTO();
						to.setStreamId(entry.getKey());
						to.setDeaneryName(subjectsMapDeaneryWise.get(entry.getKey()).get(0).getDepartment().getEmployeeStreamBO().getName());
						to.setTotalSubjects(entry.getValue().size());
						if(valCompletedDeaneryWise != null && valCompletedDeaneryWise.get(entry.getKey()) != null){
							to.setValuationCompleted(valCompletedDeaneryWise.get(entry.getKey()).size());
						}
						if(valInprogressDeaneryWise != null && valInprogressDeaneryWise.get(entry.getKey()) != null){
							to.setValuationInProcess(valInprogressDeaneryWise.get(entry.getKey()).size());
						}
						
						if(to.getTotalSubjects()-to.getValuationCompleted()-to.getValuationInProcess() > 0){
							to.setValuationNotStarted(to.getTotalSubjects()-to.getValuationCompleted()-to.getValuationInProcess());
						}else{
							to.setValuationNotStarted(0);
						}
						deaneryWiseValuationStatistics.add(to);
					}
				}
			}
		}
		return deaneryWiseValuationStatistics;
	}
	/**
	 * @param valuationStatisticsForm
	 * @return
	 * @throws Exception
	 */
	public List<ValuationStatisticsTO> getStatisticsDepartmentWise(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		List<Subject> subjectList = transaction.getTotalSubjectsForExam(valuationStatisticsForm);
		
		List<ValuationStatisticsTO> departmentWiseValuationStatistics = new ArrayList<ValuationStatisticsTO>();
		if(subjectList != null){
			// department wise total subjects
			Map<Integer, List<Subject>> subjectsMapDepartmentWise = new HashMap<Integer, List<Subject>>();
			List<Subject> subjectListDeaneryWise = null;
			Map<Integer, List<Integer>> subjectIdsMapDepartmentWise = new HashMap<Integer, List<Integer>>();
			List<Integer> subjectIdListDeaneryWise = null;
			
			//department wise valuation completed subjects only
			
			Map<Integer, List<Integer>> valCompMapDepartmentWise = new HashMap<Integer, List<Integer>>();
			List<Integer> valCompListDepartmentWise = null;
			
			//department wise valuation In progress subjects only
			
			Map<Integer, List<Integer>> valInProgressDepartmentWise = new HashMap<Integer, List<Integer>>();
			List<Integer> valInprogressDepartmentWise = null;
			
			
			Iterator<Subject> iterator = subjectList.iterator();
			while (iterator.hasNext()) {
				Subject subject = (Subject) iterator.next();
				 if(subject.getDepartment() != null && subject.getDepartment().getEmployeeStreamBO() != null 
						 && subject.getDepartment().getEmployeeStreamBO().getId()!=0 && valuationStatisticsForm.getDeptStreamId() == subject.getDepartment().getEmployeeStreamBO().getId()){
					 if(subjectsMapDepartmentWise.containsKey(subject.getDepartment().getId())){
						 subjectListDeaneryWise = subjectsMapDepartmentWise.remove(subject.getDepartment().getId());
					 }else{
						 subjectListDeaneryWise = new ArrayList<Subject>();
					 }
					 subjectListDeaneryWise.add(subject);
					 subjectsMapDepartmentWise.put(subject.getDepartment().getId(), subjectListDeaneryWise);
					 if(subjectIdsMapDepartmentWise.containsKey(subject.getDepartment().getId())){
						 subjectIdListDeaneryWise = subjectIdsMapDepartmentWise.remove(subject.getDepartment().getId());
					 }else{
						 subjectIdListDeaneryWise = new ArrayList<Integer>();
					 }
					 subjectIdListDeaneryWise.add(subject.getId());
					 subjectIdsMapDepartmentWise.put(subject.getDepartment().getId(), subjectIdListDeaneryWise);
					 
					 // valuation completed subjects
					 if(valuationStatisticsForm.getTotalValCompletedSubjects() != null 
							 && valuationStatisticsForm.getTotalValCompletedSubjects().contains(subject.getId())){
						 if(valCompMapDepartmentWise.containsKey(subject.getDepartment().getId())){
							 valCompListDepartmentWise = valCompMapDepartmentWise.remove(subject.getDepartment().getId());
						 }else{
							 valCompListDepartmentWise = new ArrayList<Integer>();
						 }
						 valCompListDepartmentWise.add(subject.getId());
						 valCompMapDepartmentWise.put(subject.getDepartment().getId(), valCompListDepartmentWise);
					 }
					//department wise valuation In progress subjects only
					 if(valuationStatisticsForm.getValInProgress() != null 
							 && valuationStatisticsForm.getValInProgress().contains(subject.getId())){
						 if(valInProgressDepartmentWise.containsKey(subject.getDepartment().getId())){
							 valInprogressDepartmentWise = valInProgressDepartmentWise.remove(subject.getDepartment().getId());
						 }else{
							 valInprogressDepartmentWise = new ArrayList<Integer>();
						 }
						 valInprogressDepartmentWise.add(subject.getId());
						 valInProgressDepartmentWise.put(subject.getDepartment().getId(), valInprogressDepartmentWise);
					 }
				 }
			}
			if(subjectIdsMapDepartmentWise != null){
				Iterator<Entry<Integer, List<Integer>>> iterator2 = subjectIdsMapDepartmentWise.entrySet().iterator();
				while (iterator2.hasNext()) {
					Map.Entry<Integer, List<Integer>> entry = (Map.Entry<Integer, List<Integer>>) iterator2.next();
					if(subjectsMapDepartmentWise.get(entry.getKey()) != null){
						ValuationStatisticsTO to = new ValuationStatisticsTO();
						to.setDepartmentId(entry.getKey());
						to.setDepartmentName(subjectsMapDepartmentWise.get(entry.getKey()).get(0).getDepartment().getName());
						to.setTotalSubjects(entry.getValue().size());
						if(valCompMapDepartmentWise != null && valCompMapDepartmentWise.get(entry.getKey()) != null){
							to.setValuationCompleted(valCompMapDepartmentWise.get(entry.getKey()).size());
						}
						if(valInProgressDepartmentWise != null && valInProgressDepartmentWise.get(entry.getKey()) != null){
							to.setValuationInProcess(valInProgressDepartmentWise.get(entry.getKey()).size());
						}
						if(to.getTotalSubjects()-to.getValuationCompleted()-to.getValuationInProcess() >0){
							to.setValuationNotStarted(to.getTotalSubjects()-to.getValuationCompleted()-to.getValuationInProcess());
						}else{
							to.setValuationNotStarted(0);
						}
						departmentWiseValuationStatistics.add(to);
					}
				}
			}
		}
		return departmentWiseValuationStatistics;
	}
	/**
	 * @param valuationStatisticsForm
	 * @return
	 * @throws Exception
	 */
	public List<ValuationStatisticsTO> getStatisticsSubjectWise(ValuationStatisticsForm valuationStatisticsForm) throws Exception {
		List<Subject> subjectList = transaction.getTotalSubjectsForExam(valuationStatisticsForm);
		List<ValuationStatisticsTO> departmentWiseValuationStatistics = new ArrayList<ValuationStatisticsTO>();
		if(subjectList != null){
			Map<Integer, List<Subject>> subjectsMapDepartmentWise = new HashMap<Integer, List<Subject>>();
			List<Subject> subjectListDepartmentWise = null;
			Map<Integer, List<Integer>> subjectIdsMapDepartmentWise = new HashMap<Integer, List<Integer>>();
			List<Integer> subjectIdListDepartmentWise = null;
			Iterator<Subject> iterator = subjectList.iterator();
			while (iterator.hasNext()) {
				Subject subject = (Subject) iterator.next();
				 if(subject.getDepartment() != null && subject.getDepartment() != null 
						 && subject.getDepartment().getId()!=0 && valuationStatisticsForm.getDeptId() == subject.getDepartment().getId()){
					 if(subjectsMapDepartmentWise.containsKey(subject.getDepartment().getId())){
						 subjectListDepartmentWise = subjectsMapDepartmentWise.remove(subject.getDepartment().getId());
					 }else{
						 subjectListDepartmentWise = new ArrayList<Subject>();
					 }
					 subjectListDepartmentWise.add(subject);
					 subjectsMapDepartmentWise.put(subject.getDepartment().getId(), subjectListDepartmentWise);
					 if(subjectIdsMapDepartmentWise.containsKey(subject.getDepartment().getId())){
						 subjectIdListDepartmentWise = subjectIdsMapDepartmentWise.remove(subject.getDepartment().getId());
					 }else{
						 subjectIdListDepartmentWise = new ArrayList<Integer>();
					 }
					 subjectIdListDepartmentWise.add(subject.getId());
					 subjectIdsMapDepartmentWise.put(subject.getDepartment().getId(), subjectIdListDepartmentWise);
				 }
			}
			if(subjectIdsMapDepartmentWise != null){
				List<Integer> deptSubjects = subjectIdsMapDepartmentWise.get(valuationStatisticsForm.getDeptId());
				List<ExamValuationStatusTO> valuationStatusSubjectWise = getSubjectWiseValuationStatistics(deptSubjects,valuationStatisticsForm);
				valuationStatisticsForm.setValuationStatusSubjectWise(valuationStatusSubjectWise);
			}
		}
		return departmentWiseValuationStatistics;
	}
	/**
	 * @param deptSubjects
	 * @param valuationStatisticsForm 
	 * @return
	 * @throws Exception
	 */
	private List<ExamValuationStatusTO> getSubjectWiseValuationStatistics(
			List<Integer> deptSubjects, ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		List<ExamValuationStatusTO> tos = new ArrayList<ExamValuationStatusTO>();
		
		List<ExamValidationDetails> valuationList = transaction.getExamValidationList(valuationStatisticsForm.getExamId());
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
		transaction.setExamSubjectDatesToForm(deptSubjects,valuationStatisticsForm);
		List<Object[]> details;
		if(valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
			details = transaction.getTotalVerfiedStudentForSupplementary(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),deptSubjects,false);
		}else{
			details = transaction.getTotalVerfiedStudent(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),deptSubjects,false,valuationStatisticsForm.getExamType());
		}
		if(details != null){
			Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap = new HashMap<Integer, List<ExamValuationStatusTO>>();
			Iterator<Object[]> iterator = details.iterator();
			List<ExamValuationStatusTO> list=null;
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				int id=0;
				if(objects[1] != null && objects[1].toString() != null){
					id = Integer.parseInt(objects[1].toString());
					if(verifyDetailsMap.containsKey(id)){
						list=verifyDetailsMap.remove(id);
					}else
						list=new ArrayList<ExamValuationStatusTO>();
					ExamValuationStatusTO to = new ExamValuationStatusTO();
					if(objects[9] != null && objects[9].toString() != null){
						to.setCourseId(Integer.parseInt(objects[9].toString()));
					}
					if(objects[0] != null && objects[0].toString() != null){
						to.setSubjectName(objects[0].toString());
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
					if(objects[14] != null && objects[14].toString() != null){
						to.setTermNumber(Integer.parseInt(objects[14].toString()));
					}
					list.add(to);
					verifyDetailsMap.put(id, list);
				}
			}
			tos = ValuationStatisticsHelper.getInstance().convertValues(valuationMap,verifyDetailsMap,valuationStatisticsForm,deptSubjects);
			Collections.sort(tos);
		}
		HibernateUtil.closeSession();
		return tos;
	}
	/**
	 * @param examValuationStatusForm
	 * @throws Exception
	 */
	public void viewValuationDetails(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		List<StudentTO> studentTos = new ArrayList<StudentTO>();
		List<Student> totalStudents = null;
		if(valuationStatisticsForm.getExamType().equalsIgnoreCase("Regular")){
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForCurrentClassStudents(valuationStatisticsForm));
			List<MarksEntryDetails> students = transaction.getDetailsForView(valuationStatisticsForm);
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,valuationStatisticsForm.getExamType());
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetailsForRegular(studentTos,totalStudents,stuMap,valuationStatisticsForm,listOfDetainedStudents);
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForPreviousClassStudents(valuationStatisticsForm));
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetailsForRegular(studentTos,totalStudents,stuMap,valuationStatisticsForm,listOfDetainedStudents);
		}else if(!valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForCurrentClassStudents(valuationStatisticsForm));
			List<MarksEntryDetails> students = transaction.getDetailsForView(valuationStatisticsForm);
			Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,valuationStatisticsForm.getExamType());
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,valuationStatisticsForm);
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForPreviousClassStudents(valuationStatisticsForm));
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,valuationStatisticsForm);
		}else{
			totalStudents = transaction.getTotalStudents1(ValuationStatisticsHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(valuationStatisticsForm));
			List<MarksEntryDetails> students = transaction.getDetailsForView(valuationStatisticsForm);
			Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,valuationStatisticsForm.getExamType());
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,valuationStatisticsForm);
		}
		valuationStatisticsForm.setStudents(studentTos);
	}
	/**
	 * @param examValuationStatusForm
	 * @throws Exception
	 */
	public void viewVerificationDetails(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		List<StudentTO> studentTos = new ArrayList<StudentTO>();
		List<Student> totalStudents = null;
		if(valuationStatisticsForm.getExamType().equalsIgnoreCase("Regular")){
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForCurrentClassStudents(valuationStatisticsForm));
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(valuationStatisticsForm);
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetailsRegular1(studentTos,totalStudents,stuMap,valuationStatisticsForm,listOfDetainedStudents);
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForPreviousClassStudents(valuationStatisticsForm));
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetailsRegular1(studentTos,totalStudents,stuMap,valuationStatisticsForm,listOfDetainedStudents);
		}else if(!valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForCurrentClassStudents(valuationStatisticsForm));
			Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(valuationStatisticsForm);
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,valuationStatisticsForm);
			totalStudents = transaction.getTotalStudents(ValuationStatisticsHelper.getInstance().getQueryForPreviousClassStudents(valuationStatisticsForm));
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,valuationStatisticsForm);
		}else{
			totalStudents = transaction.getTotalStudents1(ValuationStatisticsHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(valuationStatisticsForm));
			Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(valuationStatisticsForm);
			studentTos = ValuationStatisticsHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,valuationStatisticsForm);
		}
		valuationStatisticsForm.setStudents(studentTos);
	}
	/**
	 * @param valuationStatisticsForm
	 * @return
	 * @throws Exception
	 */
	public List<ValuationStatisticsTO> getStatisticsDepartmentWiseForUser(ValuationStatisticsForm valuationStatisticsForm) throws Exception{
		
		List<ValuationStatisticsTO> departmentWiseValuationStatistics = new ArrayList<ValuationStatisticsTO>();
		List<Integer> subjects = transaction.getTotalSubjectsForUser(valuationStatisticsForm);
		if(subjects != null && !subjects.isEmpty()){
			valuationStatisticsForm.setTotalSubjects(subjects);
			List<Object[]> details;
			if(valuationStatisticsForm.getExamType().equalsIgnoreCase("Supplementary")){
				details = transaction.getTotalVerfiedStudentForSupplementaryForDashBord(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),subjects,false);
			}else{
				details = transaction.getTotalVerfiedStudentForDashBord(valuationStatisticsForm.getExamId(),valuationStatisticsForm.getSchemeNoList(),subjects,false,valuationStatisticsForm.getExamType());
			}
			
			if(details != null){
				Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap = new HashMap<Integer, List<ExamValuationStatusTO>>();
				Iterator<Object[]> iterator = details.iterator();
				List<ExamValuationStatusTO> list=null;
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					int id=0;
					if(objects[1] != null && objects[1].toString() != null){
						id = Integer.parseInt(objects[1].toString());
					}
					if(verifyDetailsMap.containsKey(id)){
						list=verifyDetailsMap.remove(id);
					}else
						list=new ArrayList<ExamValuationStatusTO>();
					ExamValuationStatusTO to = new ExamValuationStatusTO();
					if(objects[9] != null && objects[9].toString() != null){
						to.setCourseId(Integer.parseInt(objects[9].toString()));
					}
					if(objects[0] != null && objects[0].toString() != null){
						to.setSubjectName(objects[0].toString());
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
					if(objects[15] != null && objects[15].toString() != null){
						to.setTotalAbsent(Integer.parseInt(objects[15].toString()));
					}
					list.add(to);
					verifyDetailsMap.put(id, list);
					
				}
				List<Integer> issuedForValuation = transaction.getIssuedForValuationSubjects(valuationStatisticsForm);
				List<Integer> valuationCompletedSubjects = ValuationStatisticsHelper.getInstance().getValuationCompletedSubjectsNew(verifyDetailsMap,valuationStatisticsForm,issuedForValuation);
				valuationStatisticsForm.setTotalValCompletedSubjects(valuationCompletedSubjects);
			}
			
			
			List<Subject> subjectList = transaction.getTotalSubjectsForExam(valuationStatisticsForm);
			if(subjectList != null){
				// total subjectList
				List<Integer> totalSubjectList = new ArrayList<Integer>(); 
				// department wise total subjects
				Map<Integer, List<Subject>> subjectsMapDepartmentWise = new HashMap<Integer, List<Subject>>();
				List<Subject> subjectListDeaneryWise = null;
				Map<Integer, List<Integer>> subjectIdsMapDepartmentWise = new HashMap<Integer, List<Integer>>();
				List<Integer> subjectIdListDeaneryWise = null;
				
				//department wise valuation completed subjects only
				
				Map<Integer, List<Integer>> valCompMapDepartmentWise = new HashMap<Integer, List<Integer>>();
				List<Integer> valCompListDepartmentWise = null;
				
				//department wise valuation In progress subjects only
				
				Map<Integer, List<Integer>> valInProgressDepartmentWise = new HashMap<Integer, List<Integer>>();
				List<Integer> valInprogressDepartmentWise = null;
				
				
				Iterator<Subject> iterator = subjectList.iterator();
				while (iterator.hasNext()) {
					Subject subject = (Subject) iterator.next();
					totalSubjectList.add(subject.getId());
					if(subject.getDepartment() != null && subject.getDepartment().getId() != 0){
						if(subjectsMapDepartmentWise.containsKey(subject.getDepartment().getId())){
							subjectListDeaneryWise = subjectsMapDepartmentWise.remove(subject.getDepartment().getId());
						}else{
							subjectListDeaneryWise = new ArrayList<Subject>();
						}
						subjectListDeaneryWise.add(subject);
						subjectsMapDepartmentWise.put(subject.getDepartment().getId(), subjectListDeaneryWise);
						if(subjectIdsMapDepartmentWise.containsKey(subject.getDepartment().getId())){
							subjectIdListDeaneryWise = subjectIdsMapDepartmentWise.remove(subject.getDepartment().getId());
						}else{
							subjectIdListDeaneryWise = new ArrayList<Integer>();
						}
						subjectIdListDeaneryWise.add(subject.getId());
						subjectIdsMapDepartmentWise.put(subject.getDepartment().getId(), subjectIdListDeaneryWise);
						
						// valuation completed subjects
						if(valuationStatisticsForm.getTotalValCompletedSubjects() != null 
								&& valuationStatisticsForm.getTotalValCompletedSubjects().contains(subject.getId())){
							if(valCompMapDepartmentWise.containsKey(subject.getDepartment().getId())){
								valCompListDepartmentWise = valCompMapDepartmentWise.remove(subject.getDepartment().getId());
							}else{
								valCompListDepartmentWise = new ArrayList<Integer>();
							}
							valCompListDepartmentWise.add(subject.getId());
							valCompMapDepartmentWise.put(subject.getDepartment().getId(), valCompListDepartmentWise);
						}
						//department wise valuation In progress subjects only
						if(valuationStatisticsForm.getValInProgress() != null 
								&& valuationStatisticsForm.getValInProgress().contains(subject.getId())){
							if(valInProgressDepartmentWise.containsKey(subject.getDepartment().getId())){
								valInprogressDepartmentWise = valInProgressDepartmentWise.remove(subject.getDepartment().getId());
							}else{
								valInprogressDepartmentWise = new ArrayList<Integer>();
							}
							valInprogressDepartmentWise.add(subject.getId());
							valInProgressDepartmentWise.put(subject.getDepartment().getId(), valInprogressDepartmentWise);
						}
					}
				}
				
				if(subjectIdsMapDepartmentWise != null){
					Iterator<Entry<Integer, List<Integer>>> iterator2 = subjectIdsMapDepartmentWise.entrySet().iterator();
					while (iterator2.hasNext()) {
						Map.Entry<Integer, List<Integer>> entry = (Map.Entry<Integer, List<Integer>>) iterator2.next();
						if(subjectsMapDepartmentWise.get(entry.getKey()) != null){
							ValuationStatisticsTO to = new ValuationStatisticsTO();
							to.setDepartmentId(entry.getKey());
							to.setDepartmentName(subjectsMapDepartmentWise.get(entry.getKey()).get(0).getDepartment().getName());
							to.setTotalSubjects(entry.getValue().size());
							if(valCompMapDepartmentWise != null && valCompMapDepartmentWise.get(entry.getKey()) != null){
								to.setValuationCompleted(valCompMapDepartmentWise.get(entry.getKey()).size());
							}
							if(valInProgressDepartmentWise != null && valInProgressDepartmentWise.get(entry.getKey()) != null){
								to.setValuationInProcess(valInProgressDepartmentWise.get(entry.getKey()).size());
							}
							if(to.getTotalSubjects()-to.getValuationCompleted()-to.getValuationInProcess() >0){
								to.setValuationNotStarted(to.getTotalSubjects()-to.getValuationCompleted()-to.getValuationInProcess());
							}else{
								to.setValuationNotStarted(0);
							}
							departmentWiseValuationStatistics.add(to);
						}
					}
				}
			}
		}
		
		return departmentWiseValuationStatistics;
	}
}
