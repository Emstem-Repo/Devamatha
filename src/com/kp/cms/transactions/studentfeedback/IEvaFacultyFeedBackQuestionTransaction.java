package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackQuestionForm;

public interface IEvaFacultyFeedBackQuestionTransaction
{

    public List<EvaFacultyFeedBackGroup> getFacultyFeedBackGroup()
        throws Exception;

    public List<EvaFacultyFeedbackQuestion> getFeedBackQusestionList()
        throws Exception;

    public boolean duplicateCheck(EvaFacultyFeedBackQuestionForm evafacultyfeedbackquestionform, ActionErrors actionerrors, HttpSession httpsession)
        throws Exception;

    public boolean addFeedBackQuestion(EvaFacultyFeedbackQuestion evafacultyfeedbackquestion, String s)
        throws Exception;

    public EvaFacultyFeedbackQuestion getFeedBackQuestionById(int i)
        throws Exception;

    public boolean deleteFeedBackQuestion(int i)
        throws Exception;

    public boolean reActivateFeedBackQuestion(EvaFacultyFeedBackQuestionForm evafacultyfeedbackquestionform)
        throws Exception;
}
