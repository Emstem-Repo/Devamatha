package com.kp.cms.handlers.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackQuestionForm;
import com.kp.cms.helpers.studentfeedback.EvaFacultyFeedBackQuestionHelpers;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;
import com.kp.cms.transactions.studentfeedback.IEvaFacultyFeedBackQuestionTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.EvaFacultyFeedBackQuestionImpl;

public class EvaFacultyFeedBackQuestionHandler
{

	private static final Log log=LogFactory.getLog(EvaFacultyFeedBackQuestionHandler.class);
	public static volatile EvaFacultyFeedBackQuestionHandler evaFacultyFeedBackQuestionHandler=null;
	
	public static EvaFacultyFeedBackQuestionHandler getInstance()
    {
        if(evaFacultyFeedBackQuestionHandler == null)
        {
            evaFacultyFeedBackQuestionHandler = new EvaFacultyFeedBackQuestionHandler();
            return evaFacultyFeedBackQuestionHandler;
        } else
        {
            return evaFacultyFeedBackQuestionHandler;
        }
    }
    IEvaFacultyFeedBackQuestionTransaction transaction = EvaFacultyFeedBackQuestionImpl.getInstance();
    
    public List<EvaFacultyFeedBackGroupTo> getGroup()throws Exception
    {
        List<EvaFacultyFeedBackGroup> feedBackGroup = transaction.getFacultyFeedBackGroup();
        List<EvaFacultyFeedBackGroupTo> group = EvaFacultyFeedBackQuestionHelpers.getInstance().convertBosToTOs(feedBackGroup);
        return group;
    }

    public List<EvaFacultyFeedBackQuestionTo> getFeedBackQuestionList()throws Exception
    {
        List<EvaFacultyFeedbackQuestion> feedbackQuestion = transaction.getFeedBackQusestionList();
        List<EvaFacultyFeedBackQuestionTo> feedBackQuestionList = EvaFacultyFeedBackQuestionHelpers.getInstance().convertBoToTos(feedbackQuestion);
        return feedBackQuestionList;
    }

    public boolean duplicateCheck(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm, ActionErrors errors, HttpSession session)
        throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(facultyFeedBackQuestionForm, errors, session);
        return duplicate;
    }

    public boolean addFeedBackQuestion(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm, String mode)
        throws Exception
    {
        EvaFacultyFeedbackQuestion facultyFeedbackQuestion = EvaFacultyFeedBackQuestionHelpers.getInstance().convertFormToBos(facultyFeedBackQuestionForm);
        boolean isAdded = transaction.addFeedBackQuestion(facultyFeedbackQuestion, mode);
        return isAdded;
    }

    public void editFeedBackQuestion(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm)
        throws Exception
    {
        EvaFacultyFeedbackQuestion facultyFeedbackQuestion = transaction.getFeedBackQuestionById(facultyFeedBackQuestionForm.getId());
        EvaFacultyFeedBackQuestionHelpers.getInstance().setDataBoToForm(facultyFeedBackQuestionForm, facultyFeedbackQuestion);
    }

    public boolean updateFeedBackQuestion(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm, String mode)
        throws Exception
    {
        EvaFacultyFeedbackQuestion facultyFeedbackQuestion = transaction.getFeedBackQuestionById(facultyFeedBackQuestionForm.getId());
        facultyFeedbackQuestion = EvaFacultyFeedBackQuestionHelpers.getInstance().setFormToBo(facultyFeedbackQuestion, facultyFeedBackQuestionForm);
        boolean isUpdated = transaction.addFeedBackQuestion(facultyFeedbackQuestion, mode);
        return isUpdated;
    }

    public boolean deleteFeedBackQuestion(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm)
        throws Exception
    {
        boolean isDeleted = transaction.deleteFeedBackQuestion(facultyFeedBackQuestionForm.getId());
        return isDeleted;
    }

    public boolean reActivateFeedBackQuestion(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm, String userId)
        throws Exception
    {
        return transaction.reActivateFeedBackQuestion(facultyFeedBackQuestionForm);
    }

}
