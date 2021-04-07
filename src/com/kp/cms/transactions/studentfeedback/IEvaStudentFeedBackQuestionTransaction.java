package com.kp.cms.transactions.studentfeedback;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackQuestionForm;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;

public interface IEvaStudentFeedBackQuestionTransaction
{

    public List<EvaStudentFeedbackGroup> getStudentFeedBackGroup()throws Exception;

    public List<EvaStudentFeedbackQuestion> getFeedBackQusestionList()throws Exception;

    public  boolean duplicateCheck(EvaStudentFeedBackQuestionForm evastudentfeedbackquestionform, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

    public  boolean addFeedBackQuestion(EvaStudentFeedbackQuestion evastudentfeedbackquestion)throws Exception;

    public  EvaStudentFeedbackQuestion getFeedBackQuestionById(int i)throws Exception;

    public  boolean deleteFeedBackQuestion(int i)throws Exception;

    public boolean reActivateFeedBackQuestion(EvaStudentFeedBackQuestionForm evastudentfeedbackquestionform)throws Exception;

	public boolean updateFeedBackQuestion(EvaStudentFeedbackQuestion studentFeedbackQuestion) throws Exception;
}
