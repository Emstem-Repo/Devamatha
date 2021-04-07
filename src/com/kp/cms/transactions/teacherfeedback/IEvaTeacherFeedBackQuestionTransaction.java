package com.kp.cms.transactions.teacherfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackQuestion;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackQuestionForm;

public interface IEvaTeacherFeedBackQuestionTransaction {

    public List<EvaTeacherFeedbackGroup> getTeacherFeedBackGroup()throws Exception;

    public List<EvaTeacherFeedbackQuestion> getFeedBackQusestionList()throws Exception;

    public  boolean duplicateCheck(EvaTeacherFeedBackQuestionForm evaTeacherFeedBackQuestionForm, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

    public  boolean addFeedBackQuestion(EvaTeacherFeedbackQuestion evaTeacherFeedbackQuestion)throws Exception;

    public  EvaTeacherFeedbackQuestion getFeedBackQuestionById(int i)throws Exception;

    public  boolean deleteFeedBackQuestion(int i)throws Exception;

    public boolean reActivateFeedBackQuestion(EvaTeacherFeedBackQuestionForm evaTeacherFeedBackQuestionForm)throws Exception;

	public boolean updateFeedBackQuestion(EvaTeacherFeedbackQuestion evaTeacherFeedbackQuestion) throws Exception;

}
