package com.kp.cms.helpers.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackSessionForm;
import com.kp.cms.to.admin.EvaluationStudentFeedbackSessionTo;
import com.kp.cms.utilities.CommonUtil;

public class EvaluationStudentFeedbackSessionHelper {
	public static volatile EvaluationStudentFeedbackSessionHelper evaluationStudentFeedbackSessionHelper = null;
	public static EvaluationStudentFeedbackSessionHelper getInstance(){
		if(evaluationStudentFeedbackSessionHelper == null){
			evaluationStudentFeedbackSessionHelper = new EvaluationStudentFeedbackSessionHelper();
			return evaluationStudentFeedbackSessionHelper;
		}
		return evaluationStudentFeedbackSessionHelper;
	}
	/**
	 * @param sessionBoList
	 * @return
	 * @throws Exception
	 */
	public List<EvaluationStudentFeedbackSessionTo> copyFromBoToTO( List<EvaluationStudentFeedbackSession> sessionBoList)throws Exception {
		List<EvaluationStudentFeedbackSessionTo> toList = new ArrayList<EvaluationStudentFeedbackSessionTo>();
		if(sessionBoList!=null && !sessionBoList.isEmpty()){
			Iterator<EvaluationStudentFeedbackSession>  iterator  = sessionBoList.iterator();
			while (iterator.hasNext()) {
				EvaluationStudentFeedbackSession evaluationStudentFeedbackSession = (EvaluationStudentFeedbackSession) iterator .next();
				EvaluationStudentFeedbackSessionTo sessionTo = new EvaluationStudentFeedbackSessionTo();
				if(evaluationStudentFeedbackSession.getId()!=0){
					sessionTo.setId(evaluationStudentFeedbackSession.getId());
				}
				if(evaluationStudentFeedbackSession.getName()!=null && !evaluationStudentFeedbackSession.getName().isEmpty()){
					sessionTo.setName(evaluationStudentFeedbackSession.getName());
				}
				if(evaluationStudentFeedbackSession.getMonth()!=null && !evaluationStudentFeedbackSession.getMonth().isEmpty()){
					int month = Integer.parseInt(evaluationStudentFeedbackSession.getMonth());
					String monthName = CommonUtil.getMonthName(month);
					sessionTo.setMonth(monthName);
				}
				if(evaluationStudentFeedbackSession.getYear()!=null && !evaluationStudentFeedbackSession.getYear().isEmpty()){
					sessionTo.setYear(evaluationStudentFeedbackSession.getYear());
				}
				
				if(evaluationStudentFeedbackSession.getAcademicYear()!=null && !evaluationStudentFeedbackSession.getAcademicYear().toString().isEmpty()){
					sessionTo.setAcademicYear(String.valueOf(evaluationStudentFeedbackSession.getAcademicYear()));
				}
				toList.add(sessionTo);
			}
			
		}
		return toList;
	}
	/**
	 * @param evaStuFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public EvaluationStudentFeedbackSessionTo copyFromFormToTO( EvaluationStudentFeedbackSessionForm evaStuFeedbackSessionForm) throws Exception{
		EvaluationStudentFeedbackSessionTo feedbackSessionTo = new EvaluationStudentFeedbackSessionTo();
		if(evaStuFeedbackSessionForm.getId()!=0){
			feedbackSessionTo.setId(evaStuFeedbackSessionForm.getId());
		}
		if(evaStuFeedbackSessionForm.getName()!=null && !evaStuFeedbackSessionForm.getName().isEmpty()){
			feedbackSessionTo.setName(evaStuFeedbackSessionForm.getName());
		}
		if(evaStuFeedbackSessionForm.getMonth()!=null && !evaStuFeedbackSessionForm.getMonth().isEmpty()){
			feedbackSessionTo.setMonth(evaStuFeedbackSessionForm.getMonth());
		}
		if(evaStuFeedbackSessionForm.getYear()!=null && !evaStuFeedbackSessionForm.getYear().isEmpty()){
			feedbackSessionTo.setYear(evaStuFeedbackSessionForm.getYear());
		}
		/*if(evaStuFeedbackSessionForm.getStartDate()!=null && !evaStuFeedbackSessionForm.getStartDate().isEmpty()){
			feedbackSessionTo.setStartDate(evaStuFeedbackSessionForm.getStartDate());
		}
		if(evaStuFeedbackSessionForm.getEndDate()!=null && !evaStuFeedbackSessionForm.getEndDate().isEmpty()){
			feedbackSessionTo.setEndDate(evaStuFeedbackSessionForm.getEndDate());
		}*/
		if(evaStuFeedbackSessionForm.getAcademicYear()!=null && !evaStuFeedbackSessionForm.getAcademicYear().isEmpty()){
			feedbackSessionTo.setAcademicYear(evaStuFeedbackSessionForm.getAcademicYear());
		}
		return feedbackSessionTo;
	}
	/**
	 * @param feedbackSession
	 * @return
	 * @throws Exception
	 */
	public EvaluationStudentFeedbackSessionForm populateBoTOTo( EvaluationStudentFeedbackSession feedbackSession,EvaluationStudentFeedbackSessionForm feedbackSessionForm) throws Exception{
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
		/*if(feedbackSession.getStartDate()!=null && !feedbackSession.getStartDate().toString().isEmpty()){
			feedbackSessionForm.setStartDate(formatDate(feedbackSession.getStartDate()));
		}
		if(feedbackSession.getEndDate()!=null && !feedbackSession.getEndDate().toString().isEmpty()){
			feedbackSessionForm.setEndDate(formatDate(feedbackSession.getEndDate()));
		}*/
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
