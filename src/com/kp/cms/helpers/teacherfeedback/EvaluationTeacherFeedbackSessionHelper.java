package com.kp.cms.helpers.teacherfeedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackSessionForm;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackSessionTo;
import com.kp.cms.utilities.CommonUtil;

public class EvaluationTeacherFeedbackSessionHelper {
	public static volatile EvaluationTeacherFeedbackSessionHelper evaluationTeacherFeedbackSessionHelper = null;
	public static EvaluationTeacherFeedbackSessionHelper getInstance(){
		if(evaluationTeacherFeedbackSessionHelper == null){
			evaluationTeacherFeedbackSessionHelper = new EvaluationTeacherFeedbackSessionHelper();
			return evaluationTeacherFeedbackSessionHelper;
		}
		return evaluationTeacherFeedbackSessionHelper;
	}
	/**
	 * @param sessionBoList
	 * @return
	 * @throws Exception
	 */
	public List<EvaluationTeacherFeedbackSessionTo> copyFromBoToTO( List<EvaluationTeacherFeedbackSession> sessionBoList)throws Exception {
		List<EvaluationTeacherFeedbackSessionTo> toList = new ArrayList<EvaluationTeacherFeedbackSessionTo>();
		if(sessionBoList!=null && !sessionBoList.isEmpty()){
			Iterator<EvaluationTeacherFeedbackSession>  iterator  = sessionBoList.iterator();
			while (iterator.hasNext()) {
				EvaluationTeacherFeedbackSession evaluationTeacherFeedbackSession = (EvaluationTeacherFeedbackSession) iterator .next();
				EvaluationTeacherFeedbackSessionTo sessionTo = new EvaluationTeacherFeedbackSessionTo();
				if(evaluationTeacherFeedbackSession.getId()!=0){
					sessionTo.setId(evaluationTeacherFeedbackSession.getId());
				}
				if(evaluationTeacherFeedbackSession.getName()!=null && !evaluationTeacherFeedbackSession.getName().isEmpty()){
					sessionTo.setName(evaluationTeacherFeedbackSession.getName());
				}
				if(evaluationTeacherFeedbackSession.getMonth()!=null && !evaluationTeacherFeedbackSession.getMonth().isEmpty()){
					int month = Integer.parseInt(evaluationTeacherFeedbackSession.getMonth());
					String monthName = CommonUtil.getMonthName(month);
					sessionTo.setMonth(monthName);
				}
				if(evaluationTeacherFeedbackSession.getYear()!=null && !evaluationTeacherFeedbackSession.getYear().isEmpty()){
					sessionTo.setYear(evaluationTeacherFeedbackSession.getYear());
				}
				
				if(evaluationTeacherFeedbackSession.getAcademicYear()!=null && !evaluationTeacherFeedbackSession.getAcademicYear().toString().isEmpty()){
					sessionTo.setAcademicYear(String.valueOf(evaluationTeacherFeedbackSession.getAcademicYear()));
				}
				toList.add(sessionTo);
			}
			
		}
		return toList;
	}
	/**
	 * @param evaTeacherFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public EvaluationTeacherFeedbackSessionTo copyFromFormToTO( EvaluationTeacherFeedbackSessionForm evaTeacherFeedbackSessionForm) throws Exception{
		EvaluationTeacherFeedbackSessionTo feedbackSessionTo = new EvaluationTeacherFeedbackSessionTo();
		if(evaTeacherFeedbackSessionForm.getId()!=0){
			feedbackSessionTo.setId(evaTeacherFeedbackSessionForm.getId());
		}
		if(evaTeacherFeedbackSessionForm.getName()!=null && !evaTeacherFeedbackSessionForm.getName().isEmpty()){
			feedbackSessionTo.setName(evaTeacherFeedbackSessionForm.getName());
		}
		if(evaTeacherFeedbackSessionForm.getMonth()!=null && !evaTeacherFeedbackSessionForm.getMonth().isEmpty()){
			feedbackSessionTo.setMonth(evaTeacherFeedbackSessionForm.getMonth());
		}
		if(evaTeacherFeedbackSessionForm.getYear()!=null && !evaTeacherFeedbackSessionForm.getYear().isEmpty()){
			feedbackSessionTo.setYear(evaTeacherFeedbackSessionForm.getYear());
		}
		
		if(evaTeacherFeedbackSessionForm.getAcademicYear()!=null && !evaTeacherFeedbackSessionForm.getAcademicYear().isEmpty()){
			feedbackSessionTo.setAcademicYear(evaTeacherFeedbackSessionForm.getAcademicYear());
		}
		return feedbackSessionTo;
	}
	/**
	 * @param feedbackSession
	 * @return
	 * @throws Exception
	 */
	public EvaluationTeacherFeedbackSessionForm populateBoTOTo( EvaluationTeacherFeedbackSession feedbackSession,EvaluationTeacherFeedbackSessionForm feedbackSessionForm) throws Exception{
		if(feedbackSession.getId()!=0){
			feedbackSessionForm.setId(feedbackSession.getId());
		}
		if(feedbackSession.getName()!=null && !feedbackSession.getName().isEmpty()){
			feedbackSessionForm.setName(feedbackSession.getName());
		}
		if(feedbackSession.getMonth()!=null && !feedbackSession.getMonth().isEmpty()){
			feedbackSessionForm.setMonth(feedbackSession.getMonth());
		}
		if(feedbackSession.getYear()!=null && !feedbackSession.getYear().isEmpty()){
			feedbackSessionForm.setYear(feedbackSession.getYear());
		}
		
		if(feedbackSession.getAcademicYear()!= null && !feedbackSession.getAcademicYear().toString().isEmpty()){
			feedbackSessionForm.setAcademicYear(String.valueOf(feedbackSession.getAcademicYear()));
		}
		return feedbackSessionForm;
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
}
