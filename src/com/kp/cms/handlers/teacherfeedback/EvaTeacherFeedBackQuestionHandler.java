package com.kp.cms.handlers.teacherfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackQuestion;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackQuestionForm;
import com.kp.cms.helpers.teacherfeedback.EvaTeacherFeedBackQuestionHelpers;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackGroupTo;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedBackQuestionTransaction;
import com.kp.cms.transactionsimpl.teacherfeedback.EvaTeacherFeedBackQuestionImpl;

public class EvaTeacherFeedBackQuestionHandler {

	private static final Log log=LogFactory.getLog(EvaTeacherFeedBackQuestionHandler.class);
	public static volatile EvaTeacherFeedBackQuestionHandler evaTeacherFeedBackQuestionHandler=null;

	public static EvaTeacherFeedBackQuestionHandler getInstance()
	{
		if(evaTeacherFeedBackQuestionHandler == null)
		{
			evaTeacherFeedBackQuestionHandler = new EvaTeacherFeedBackQuestionHandler();
			return evaTeacherFeedBackQuestionHandler;
		} else
		{
			return evaTeacherFeedBackQuestionHandler;
		}
	}
	IEvaTeacherFeedBackQuestionTransaction transaction = EvaTeacherFeedBackQuestionImpl.getInstance();

	public List<EvaTeacherFeedBackGroupTo> getGroup()throws Exception
	{
		List<EvaTeacherFeedbackGroup> feedBackGroup = transaction.getTeacherFeedBackGroup();
		List<EvaTeacherFeedBackGroupTo> group = EvaTeacherFeedBackQuestionHelpers.getInstance().convertBosToTOs(feedBackGroup);
		return group;
	}

	 public List<EvaTeacherFeedBackQuestionTo> getFeedBackQuestionList()throws Exception
	    {
	        List<EvaTeacherFeedbackQuestion> feedbackQuestion = transaction.getFeedBackQusestionList();
	        List<EvaTeacherFeedBackQuestionTo> feedBackQuestionList = EvaTeacherFeedBackQuestionHelpers.getInstance().convertBoToTos(feedbackQuestion);
	        return feedBackQuestionList;
	    }

	    public boolean duplicateCheck(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm, ActionErrors errors, HttpSession session)
	        throws Exception
	    {
	        boolean duplicate = transaction.duplicateCheck(teacherFeedBackQuestionForm, errors, session);
	        return duplicate;
	    }

	    public boolean addFeedBackQuestion(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
	        throws Exception
	    {
	        EvaTeacherFeedbackQuestion studentFeedbackQuestion = EvaTeacherFeedBackQuestionHelpers.getInstance().convertFormToBos(teacherFeedBackQuestionForm);
	        boolean isAdded = transaction.addFeedBackQuestion(studentFeedbackQuestion);
	        return isAdded;
	    }

	    public void editFeedBackQuestion(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
	        throws Exception
	    {
	        EvaTeacherFeedbackQuestion teacherFeedbackQuestion = transaction.getFeedBackQuestionById(teacherFeedBackQuestionForm.getId());
	        EvaTeacherFeedBackQuestionHelpers.getInstance().setDataBoToForm(teacherFeedBackQuestionForm, teacherFeedbackQuestion);
	    }

	    public boolean updateFeedBackQuestion(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
	        throws Exception
	    {
	        EvaTeacherFeedbackQuestion studentFeedbackQuestion = EvaTeacherFeedBackQuestionHelpers.getInstance().updateFormToBo(teacherFeedBackQuestionForm);
	        boolean isUpdated = transaction.updateFeedBackQuestion(studentFeedbackQuestion);
	        return isUpdated;
	    }

	    public boolean deleteFeedBackQuestion(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
	        throws Exception
	    {
	        boolean isDeleted = transaction.deleteFeedBackQuestion(teacherFeedBackQuestionForm.getId());
	        return isDeleted;
	    }

	    public boolean reActivateFeedBackQuestion(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm, String userId)
	        throws Exception
	    {
	        return transaction.reActivateFeedBackQuestion(teacherFeedBackQuestionForm);
	    }
}
