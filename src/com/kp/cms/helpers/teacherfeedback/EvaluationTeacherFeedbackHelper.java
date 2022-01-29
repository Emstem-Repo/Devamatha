package com.kp.cms.helpers.teacherfeedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.WordUtils;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackQuestion;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedback;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackAnswer;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackStudent;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackTo;
import com.kp.cms.transactions.teacherfeedback.IEvaluationTeacherFeedback;
import com.kp.cms.transactionsimpl.teacherfeedback.EvaluationTeacherFeedbackTxnImpl;


public class EvaluationTeacherFeedbackHelper {
	public static volatile EvaluationTeacherFeedbackHelper evaluationTeacherFeedbackHelper = null;
	public static EvaluationTeacherFeedbackHelper getInstance(){
		if(evaluationTeacherFeedbackHelper == null){
			evaluationTeacherFeedbackHelper = new EvaluationTeacherFeedbackHelper();
			return evaluationTeacherFeedbackHelper;
		}
		return evaluationTeacherFeedbackHelper;
	}

	/**
	 * @param date
	 * @return
	 */
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	
	public List<EvaluationTeacherFeedbackTo> copyBOToToList( List<Student> studentList, List<Integer> stuIdsAlreadyGivenFeddback) throws Exception{
		List<EvaluationTeacherFeedbackTo> list = new ArrayList<EvaluationTeacherFeedbackTo>();
		int i = 0;
		List<Integer> totalStudentList = new ArrayList<Integer>();
		if(studentList!=null && !studentList.toString().isEmpty()){
			Iterator<Student> iterator = studentList.iterator();
			while (iterator.hasNext()) {
				Student bo = (Student) iterator .next();
				EvaluationTeacherFeedbackTo to = new EvaluationTeacherFeedbackTo();
				to.setStudentId(bo.getId());
				to.setStudentName(bo.getAdmAppln().getPersonalData().getFirstName());
				to.setApplicationNo(bo.getAdmAppln().getApplnNo());
				to.setRegisterNo(bo.getRegisterNo());
				to.setRollNo(bo.getRollNo());
				if(stuIdsAlreadyGivenFeddback.contains(bo.getId()))
					to.setDone(true);
				else
					to.setDone(false);
				i++;
				to.setStudentNo(i);
				totalStudentList.add(to.getStudentNo());
				to.setTotalStudents(totalStudentList);

				list.add(to);
			}
		}
		return list;
	}
	
	public boolean tempSaveStudentRatingDetails(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception{
		IEvaluationTeacherFeedback itransaction = EvaluationTeacherFeedbackTxnImpl.getInstance();
		boolean isDuplicate = itransaction.checkStudentIsDuplicate(evaTeacherFeedbackForm);
		if(!isDuplicate){
			EvaluationTeacherFeedbackTo evaluationTeacherFeedbackTo = new EvaluationTeacherFeedbackTo();
			evaluationTeacherFeedbackTo.setStudentId(evaTeacherFeedbackForm.getStudentId());
			if(evaTeacherFeedbackForm.getRemarks()!=null && !evaTeacherFeedbackForm.getRemarks().isEmpty()){
				evaluationTeacherFeedbackTo.setRemarks(evaTeacherFeedbackForm.getRemarks());
			}
			// getting the questionTolist from formbean and setting it to the EvaluationTeacherFeedbackTo to
			List<EvaTeacherFeedBackQuestionTo> questionList = evaTeacherFeedbackForm.getEvaTeacherQuestionsToList();
			evaluationTeacherFeedbackTo.setQuestionTosList(questionList);
			evaTeacherFeedbackForm.setTempTeachersEvaList(evaluationTeacherFeedbackTo);
		}
		return isDuplicate;
	}
	
	public EvaluationTeacherFeedback convertToListTOBo(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm, EvaluationTeacherFeedback existBO)throws Exception {
		EvaluationTeacherFeedback evaluationFeedbackBo = null;
		if(existBO!=null && !existBO.toString().isEmpty()){
			evaluationFeedbackBo = existBO;
			EvaluationTeacherFeedbackTo feedbackTO = evaTeacherFeedbackForm.getTempTeachersEvaList();
			Set<EvaluationTeacherFeedbackStudent> feedbackStudentsSet = new HashSet<EvaluationTeacherFeedbackStudent>();
			EvaluationTeacherFeedbackStudent feedbackStudents = new EvaluationTeacherFeedbackStudent();
				if(evaTeacherFeedbackForm.getUserId()!=null && !evaTeacherFeedbackForm.getUserId().isEmpty()){
					Users users = new Users();
					users.setId(Integer.parseInt(evaTeacherFeedbackForm.getUserId()));
					evaluationFeedbackBo.setTeacher(users);
				}
				if(feedbackTO.getRemarks()!=null && !feedbackTO.getRemarks().isEmpty()){
					feedbackStudents.setRemarks(feedbackTO.getRemarks());
				}
				feedbackStudents.setEvaTeacherFeedback(evaluationFeedbackBo);
				feedbackStudents.setCreatedBy(evaTeacherFeedbackForm.getUserId());
				feedbackStudents.setCreatedDate(new Date());
				feedbackStudents.setLastModifiedDate(new Date());
				feedbackStudents.setModifiedBy(evaTeacherFeedbackForm.getUserId());
				feedbackStudents.setIsActive(true);
				
				if(feedbackTO.getQuestionTosList()!=null && !feedbackTO.getQuestionTosList().isEmpty()){
					List<EvaTeacherFeedBackQuestionTo> questionTosList = feedbackTO.getQuestionTosList();
					Set<EvaluationTeacherFeedbackAnswer> feedbackAnswersSet =new HashSet<EvaluationTeacherFeedbackAnswer>();
					Iterator<EvaTeacherFeedBackQuestionTo> iterator2 = questionTosList.iterator();
					while (iterator2.hasNext()) {
						EvaTeacherFeedBackQuestionTo evaTeacherFeedBackQuestionTo = (EvaTeacherFeedBackQuestionTo) iterator2 .next();
						EvaluationTeacherFeedbackAnswer answersBo = new EvaluationTeacherFeedbackAnswer();
						answersBo.setFeedbackStudent(feedbackStudents);
						if(evaTeacherFeedBackQuestionTo.getId()!=0){
							EvaTeacherFeedbackQuestion question = new EvaTeacherFeedbackQuestion();
							question.setId(evaTeacherFeedBackQuestionTo.getId());
							answersBo.setQuestionId(question);
						}
						if(evaTeacherFeedBackQuestionTo.getAnswer()!=null && !evaTeacherFeedBackQuestionTo.getAnswer().isEmpty()){
							answersBo.setAnswer(evaTeacherFeedBackQuestionTo.getAnswer());
						}
						feedbackAnswersSet.add(answersBo);
					}
					feedbackStudents.setFeedbackAnswer(feedbackAnswersSet);
				}
					feedbackStudentsSet.add(feedbackStudents);
			evaluationFeedbackBo.setFeedbackStudent(feedbackStudentsSet);
		}else{
			evaluationFeedbackBo= new EvaluationTeacherFeedback();
			if(evaTeacherFeedbackForm.getUserId()!=null && !evaTeacherFeedbackForm.getUserId().isEmpty()){
				Users users = new Users();
				users.setId(Integer.parseInt(evaTeacherFeedbackForm.getUserId()));
				evaluationFeedbackBo.setTeacher(users);
			}
			
			if(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0]!=null && !evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0].isEmpty()){
				Classes cls = new Classes();
				cls.setId(Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[0]));
				evaluationFeedbackBo.setClasses(cls);
			}
			
			if(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[1]!=null && !evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[1].isEmpty()){
				EvaluationTeacherFeedbackSession session = new EvaluationTeacherFeedbackSession();
				session.setId(Integer.parseInt(evaTeacherFeedbackForm.getClassSchemewiseId().split("_")[1]));
				evaluationFeedbackBo.setFacultyEvaluationSession(session);
			}
			evaluationFeedbackBo.setCancel(false);
			evaluationFeedbackBo.setCreatedBy(evaTeacherFeedbackForm.getUserId());
			evaluationFeedbackBo.setModifiedBy(evaTeacherFeedbackForm.getUserId());
			evaluationFeedbackBo.setCreatedDate(new Date());
			evaluationFeedbackBo.setLastModifiedDate(new Date());
			evaluationFeedbackBo.setIsActive(true);
			if(evaTeacherFeedbackForm.getTempTeachersEvaList()!=null && !evaTeacherFeedbackForm.getTempTeachersEvaList().toString().isEmpty()){
				EvaluationTeacherFeedbackTo feedbackTO = evaTeacherFeedbackForm.getTempTeachersEvaList();
				Set<EvaluationTeacherFeedbackStudent> feedbackStudentsSet = new HashSet<EvaluationTeacherFeedbackStudent>();
				EvaluationTeacherFeedbackStudent feedbackStudents = new EvaluationTeacherFeedbackStudent();
					if(evaTeacherFeedbackForm.getStudentId()!=0){
						Student student = new Student();
						student.setId(evaTeacherFeedbackForm.getStudentId());
						feedbackStudents.setStudent(student);
					}
					if(Integer.parseInt(evaTeacherFeedbackForm.getSubjectId())!=0){
						Subject subject = new Subject();
						subject.setId(Integer.parseInt(evaTeacherFeedbackForm.getSubjectId()));
						feedbackStudents.setSubject(subject);
					}
					if(feedbackTO.getRemarks()!=null && !feedbackTO.getRemarks().isEmpty()){
						feedbackStudents.setRemarks(feedbackTO.getRemarks());
					}
					feedbackStudents.setEvaTeacherFeedback(evaluationFeedbackBo);
					feedbackStudents.setCreatedBy(evaTeacherFeedbackForm.getUserId());
					feedbackStudents.setCreatedDate(new Date());
					feedbackStudents.setLastModifiedDate(new Date());
					feedbackStudents.setModifiedBy(evaTeacherFeedbackForm.getUserId());
					feedbackStudents.setIsActive(true);
					if(feedbackTO.getQuestionTosList()!=null && !feedbackTO.getQuestionTosList().isEmpty()){
						List<EvaTeacherFeedBackQuestionTo> questionTosList = feedbackTO.getQuestionTosList();
						Set<EvaluationTeacherFeedbackAnswer> feedbackAnswersSet = new HashSet<EvaluationTeacherFeedbackAnswer>();
						Iterator<EvaTeacherFeedBackQuestionTo> iterator2 = questionTosList.iterator();
						while (iterator2.hasNext()) {
							EvaTeacherFeedBackQuestionTo evaTeacherFeedBackQuestionTo = (EvaTeacherFeedBackQuestionTo) iterator2 .next();
							EvaluationTeacherFeedbackAnswer answersBo = new EvaluationTeacherFeedbackAnswer();
							answersBo.setFeedbackStudent(feedbackStudents);
							if(evaTeacherFeedBackQuestionTo.getId()!=0){
								EvaTeacherFeedbackQuestion question = new EvaTeacherFeedbackQuestion();
								question.setId(evaTeacherFeedBackQuestionTo.getId());
								answersBo.setQuestionId(question);
							}
							if(evaTeacherFeedBackQuestionTo.getAnswer()!=null && !evaTeacherFeedBackQuestionTo.getAnswer().isEmpty()){
								answersBo.setAnswer(evaTeacherFeedBackQuestionTo.getAnswer());
							}
							feedbackAnswersSet.add(answersBo);
						}
						feedbackStudents.setFeedbackAnswer(feedbackAnswersSet);
					}
					feedbackStudentsSet.add(feedbackStudents);
				evaluationFeedbackBo.setFeedbackStudent(feedbackStudentsSet);
			}
		}
		return evaluationFeedbackBo;
	}
	
	public Map<Integer, EvaluationTeacherFeedbackTo> copyBOToToFeedbackDetailsList( List<EvaluationTeacherFeedbackAnswer> studentList) throws Exception{
		Map<Integer, EvaluationTeacherFeedbackTo> studentMap = new HashMap<Integer, EvaluationTeacherFeedbackTo>();
		if(studentList!=null && !studentList.toString().isEmpty()){
			Iterator<EvaluationTeacherFeedbackAnswer> iterator = studentList.iterator();
			while (iterator.hasNext()) {
				EvaluationTeacherFeedbackAnswer bo = (EvaluationTeacherFeedbackAnswer) iterator .next();
				if(studentMap.containsKey(bo.getFeedbackStudent().getStudent().getId()))
				{
					EvaluationTeacherFeedbackTo to = studentMap.get(bo.getFeedbackStudent().getStudent().getId());
					EvaTeacherFeedBackQuestionTo queAnsTo = new EvaTeacherFeedBackQuestionTo();
					queAnsTo.setQuestion(bo.getQuestionId().getQuestion());
					queAnsTo.setOrder(String.valueOf(bo.getQuestionId().getOrder()));
					queAnsTo.setAnswer(bo.getAnswer());
					List<EvaTeacherFeedBackQuestionTo> existingQuesAnsList = to.getQuestionTosList();
					existingQuesAnsList.add(queAnsTo);
					Collections.sort(existingQuesAnsList);
					to.setQuestionTosList(existingQuesAnsList);
				}else{
					EvaluationTeacherFeedbackTo to = new EvaluationTeacherFeedbackTo();
					to.setStudentId(bo.getFeedbackStudent().getStudent().getId());
					to.setStudentName(WordUtils.capitalizeFully(bo.getFeedbackStudent().getStudent().getAdmAppln().getPersonalData().getFirstName()));
					to.setApplicationNo(bo.getFeedbackStudent().getStudent().getAdmAppln().getApplnNo());
					to.setRegisterNo(bo.getFeedbackStudent().getStudent().getRegisterNo());
					to.setRollNo(bo.getFeedbackStudent().getStudent().getRollNo());
					EvaTeacherFeedBackQuestionTo queAnsTo = new EvaTeacherFeedBackQuestionTo();
					queAnsTo.setQuestion(bo.getQuestionId().getQuestion());
					queAnsTo.setOrder(String.valueOf(bo.getQuestionId().getOrder()));
					queAnsTo.setAnswer(bo.getAnswer());
					List<EvaTeacherFeedBackQuestionTo> quesAnsList = new ArrayList<EvaTeacherFeedBackQuestionTo>();
					quesAnsList.add(queAnsTo);
					Collections.sort(quesAnsList);
					to.setQuestionTosList(quesAnsList);
					studentMap.put(bo.getFeedbackStudent().getStudent().getId(), to);
				}
			}
		}
		return studentMap;
	}	
}
