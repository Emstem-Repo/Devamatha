package com.kp.cms.handlers.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackQuestionForm;
import com.kp.cms.helpers.studentfeedback.EvaStudentFeedBackQuestionHelpers;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.transactions.studentfeedback.IEvaStudentFeedBackQuestionTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.EvaStudentFeedBackQuestionImpl;

public class EvaStudentFeedBackQuestionHandler
{

	private static final Log log=LogFactory.getLog(EvaStudentFeedBackQuestionHandler.class);
	public static volatile EvaStudentFeedBackQuestionHandler evaStudentFeedBackQuestionHandler=null;
	
	public static EvaStudentFeedBackQuestionHandler getInstance()
    {
        if(evaStudentFeedBackQuestionHandler == null)
        {
            evaStudentFeedBackQuestionHandler = new EvaStudentFeedBackQuestionHandler();
            return evaStudentFeedBackQuestionHandler;
        } else
        {
            return evaStudentFeedBackQuestionHandler;
        }
    }
    IEvaStudentFeedBackQuestionTransaction transaction = EvaStudentFeedBackQuestionImpl.getInstance();
    
    public List<EvaStudentFeedBackGroupTo> getGroup()throws Exception
    {
        List<EvaStudentFeedbackGroup> feedBackGroup = transaction.getStudentFeedBackGroup();
        List<EvaStudentFeedBackGroupTo> group = EvaStudentFeedBackQuestionHelpers.getInstance().convertBosToTOs(feedBackGroup);
        return group;
    }

    public List<EvaStudentFeedBackQuestionTo> getFeedBackQuestionList()throws Exception
    {
        List<EvaStudentFeedbackQuestion> feedbackQuestion = transaction.getFeedBackQusestionList();
        List<EvaStudentFeedBackQuestionTo> feedBackQuestionList = EvaStudentFeedBackQuestionHelpers.getInstance().convertBoToTos(feedbackQuestion);
        return feedBackQuestionList;
    }

    public boolean duplicateCheck(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm, ActionErrors errors, HttpSession session)
        throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(studentFeedBackQuestionForm, errors, session);
        return duplicate;
    }

    public boolean addFeedBackQuestion(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
        throws Exception
    {
        EvaStudentFeedbackQuestion studentFeedbackQuestion = EvaStudentFeedBackQuestionHelpers.getInstance().convertFormToBos(studentFeedBackQuestionForm);
        boolean isAdded = transaction.addFeedBackQuestion(studentFeedbackQuestion);
        return isAdded;
    }

    public void editFeedBackQuestion(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
        throws Exception
    {
        EvaStudentFeedbackQuestion studentFeedbackQuestion = transaction.getFeedBackQuestionById(studentFeedBackQuestionForm.getId());
        EvaStudentFeedBackQuestionHelpers.getInstance().setDataBoToForm(studentFeedBackQuestionForm, studentFeedbackQuestion);
    }

    public boolean updateFeedBackQuestion(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
        throws Exception
    {
        EvaStudentFeedbackQuestion studentFeedbackQuestion = EvaStudentFeedBackQuestionHelpers.getInstance().updateFormToBo(studentFeedBackQuestionForm);
        boolean isUpdated = transaction.updateFeedBackQuestion(studentFeedbackQuestion);
        return isUpdated;
    }

    public boolean deleteFeedBackQuestion(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
        throws Exception
    {
        boolean isDeleted = transaction.deleteFeedBackQuestion(studentFeedBackQuestionForm.getId());
        return isDeleted;
    }

    public boolean reActivateFeedBackQuestion(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm, String userId)
        throws Exception
    {
        return transaction.reActivateFeedBackQuestion(studentFeedBackQuestionForm);
    }

}
