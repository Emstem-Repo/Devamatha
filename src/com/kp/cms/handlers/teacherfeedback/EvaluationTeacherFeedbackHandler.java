package com.kp.cms.handlers.teacherfeedback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedback;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackAnswer;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackStudent;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackForm;
import com.kp.cms.helpers.teacherfeedback.EvaluationTeacherFeedbackHelper;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedback;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.teacherfeedback.EvaluationTeacherFeedbackTxnImpl;
import com.kp.cms.utilities.CommonUtil;


public class EvaluationTeacherFeedbackHandler {
	public static volatile EvaluationTeacherFeedbackHandler evaluationTeacherFeedbackHandler = null;
	public static EvaluationTeacherFeedbackHandler getInstance(){
		if(evaluationTeacherFeedbackHandler == null){
			evaluationTeacherFeedbackHandler = new EvaluationTeacherFeedbackHandler();
			return evaluationTeacherFeedbackHandler;
		}
		return evaluationTeacherFeedbackHandler;
	}
	
	public List<EvaTeacherFeedbackOpenConnection> getOpenConnections(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm )throws Exception {
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		List<EvaTeacherFeedbackOpenConnection> connectionClasses = itransaction.getConnectionClassesList(evaTeacherFeedbackForm);
		return connectionClasses;
	}
	
	public List<EvaluationTeacherFeedbackTo> getStudentsForFeedback(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		List<Student> studentList = itransaction.getStudentsForFeedBack(evaTeacherFeedbackForm);
		List<Integer> stuIdsAlreadyGivenFeddback = itransaction.getStudentsFeedbackAleardyGiven(evaTeacherFeedbackForm);
		List<EvaluationTeacherFeedbackTo> studentListForFeedbackTosList = EvaluationTeacherFeedbackHelper.getInstance().copyBOToToList(studentList,stuIdsAlreadyGivenFeddback);
		return studentListForFeedbackTosList;
	}

	public void setStudentDetailsToForm(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) {
		List<EvaluationTeacherFeedbackTo> toList = evaTeacherFeedbackForm.getStudentList();
		boolean evaluationCompleted = true;
		if (toList != null && !toList.isEmpty()){
			Iterator<EvaluationTeacherFeedbackTo> iterator = toList.iterator();
			int totalStudents = 0;
			while (iterator.hasNext()) {
				EvaluationTeacherFeedbackTo to = (EvaluationTeacherFeedbackTo) iterator .next();
				if (!to.getDone()) {
					toList.remove(to);
					evaluationCompleted = false;
					evaTeacherFeedbackForm.setStudentId(to.getStudentId());
					evaTeacherFeedbackForm.setNameOfStudent(to.getStudentName());
					evaTeacherFeedbackForm.setRegNo(to.getRegisterNo());
					evaTeacherFeedbackForm.setSubjectName(evaTeacherFeedbackForm.getSubjectMap().get(Integer.parseInt(evaTeacherFeedbackForm.getSubjectId())));
					List<Integer> totStudentsList = to.getTotalStudents();
					Iterator<Integer> iterator2 = totStudentsList.iterator();
					while (iterator2.hasNext()) {
						Integer totalNo = (Integer) iterator2.next();
						totalStudents = totalNo;
					}
					evaTeacherFeedbackForm.setStudentNo(to.getStudentNo());
					evaTeacherFeedbackForm.setTotalStudents(totalStudents);
					to.setDone(true);
					toList.add(to);
					evaTeacherFeedbackForm.setStudentList(toList);
					break;
				}
			}
			if(evaluationCompleted){
				evaTeacherFeedbackForm.setEvaluationCompleted(true);
				
			}
		}
	}
	
	public boolean storeEachStudentRatingDetails(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception{
		boolean isDuplicate = EvaluationTeacherFeedbackHelper.getInstance().tempSaveStudentRatingDetails(evaTeacherFeedbackForm);
		return isDuplicate;
	}

	public boolean saveStudentRatingDetailsInToBO(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception{
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		EvaluationTeacherFeedback existBO = itransaction.getExistsBO(evaTeacherFeedbackForm);
		EvaluationTeacherFeedback evaluationTeacherFeedback= EvaluationTeacherFeedbackHelper.getInstance().convertToListTOBo(evaTeacherFeedbackForm,existBO);
		boolean isAdded = itransaction.saveTeacherEvaluationFeedback(evaluationTeacherFeedback,evaTeacherFeedbackForm);
		return isAdded;
	}
	
	public Map<Integer, String> getClassesByTeacherAndYearForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm )throws Exception {
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		Map<Integer, String> classesForReport = itransaction.getClassesListForReport(evaTeacherFeedbackForm);
		return classesForReport;
	}
	
	public Map<Integer, EvaluationTeacherFeedbackTo> getTecherFeedbackDetailsForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		List<EvaluationTeacherFeedbackAnswer> studentList= itransaction.getTecherFeedbackDetailsForReport(evaTeacherFeedbackForm);
		Map<Integer, EvaluationTeacherFeedbackTo> studentListForFeedbackTosMap = EvaluationTeacherFeedbackHelper.getInstance().copyBOToToFeedbackDetailsList(studentList);
		return studentListForFeedbackTosMap;
	}

	public void getTeacherEvaluatedDetails(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm,
			Map<Integer, EvaluationTeacherFeedbackTo> studentFeedBackMap) throws Exception{
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		Map<Integer, Double> stuIntMarksMap = itransaction.getStudentInternalMarks(evaTeacherFeedbackForm);
		Map<Integer, List<EvaTeacherFeedBackQuestionTo>> pointsScoredByTacherMap = itransaction.getPointsScoredByTeacher(evaTeacherFeedbackForm);
		double totalAverage=0;
		double totalPointsScoredByTeacher=0;
		double totalWeightedPoints=0;
		double totalIndexPoints=0;
		for (Map.Entry<Integer, EvaluationTeacherFeedbackTo> entry : studentFeedBackMap.entrySet()) {
			double indexPoints=0;
			double avgIndexInt=0;
			
			EvaluationTeacherFeedbackTo to = entry.getValue();
			List<EvaTeacherFeedBackQuestionTo> queAnsToList = to.getQuestionTosList();
			Iterator<EvaTeacherFeedBackQuestionTo> itr = queAnsToList.iterator();
			while(itr.hasNext()){
				EvaTeacherFeedBackQuestionTo queAnsTo = itr.next();
				indexPoints = indexPoints + Double.parseDouble(queAnsTo.getAnswer());
			}
			indexPoints = (indexPoints/evaTeacherFeedbackForm.getTotalQuestions());
			if(stuIntMarksMap.containsKey(to.getStudentId()))
			{
				avgIndexInt = (indexPoints+stuIntMarksMap.get(to.getStudentId()))/2;
				to.setInternal(String.valueOf(stuIntMarksMap.get(to.getStudentId())));
				to.setAverage(String.valueOf(avgIndexInt));
			}
			if(pointsScoredByTacherMap.containsKey(to.getStudentId())){
				double pointScoreByTeacher=0;
				List<EvaTeacherFeedBackQuestionTo> queList = pointsScoredByTacherMap.get(to.getStudentId());
				if(queList!=null){
					Iterator<EvaTeacherFeedBackQuestionTo> itr1 = queList.iterator();
					while(itr1.hasNext()){
						EvaTeacherFeedBackQuestionTo queTo = itr1.next();
						pointScoreByTeacher = pointScoreByTeacher+Integer.parseInt(queTo.getAnswer());
					}
				}
				to.setPointsScoreByTeacher(String.valueOf(pointScoreByTeacher/queList.size()));
				
			}
			to.setIndexPoints(String.valueOf(indexPoints));
			to.setWeightedPoints(String.valueOf(CommonUtil.Round(indexPoints*(Double.parseDouble(to.getPointsScoreByTeacher())), 2)));
			totalIndexPoints = totalIndexPoints + indexPoints;
			totalAverage = totalAverage + Double.parseDouble(to.getAverage());
			totalPointsScoredByTeacher = totalPointsScoredByTeacher + Double.parseDouble(to.getPointsScoreByTeacher());
			totalWeightedPoints = totalWeightedPoints + Double.parseDouble(to.getWeightedPoints());
		}
		evaTeacherFeedbackForm.setTotalAverage(String.valueOf(CommonUtil.Round(totalAverage,2)));
		evaTeacherFeedbackForm.setTotalPointsScoredByTeacher(String.valueOf(totalPointsScoredByTeacher));
		evaTeacherFeedbackForm.setTotalWeightedPoints(String.valueOf(totalWeightedPoints));
		evaTeacherFeedbackForm.setTotalWeightsAndIndexPoints(String.valueOf(CommonUtil.Round(totalWeightedPoints/totalIndexPoints, 2)));
	}
	
	public Map<Integer, String> getSubjectsByClassForTeacher(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		Map<Integer, String> subjectMap = itransaction.getSubjectsByClassForTeacher(evaTeacherFeedbackForm);
		return subjectMap;
	}
	
	public Map<Integer, String> getTeacherMapByYearForReport(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm )throws Exception {
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		Map<Integer, String> classesForReport = itransaction.getTeacherMapByYearForReport(evaTeacherFeedbackForm);
		return classesForReport;
	}
}
