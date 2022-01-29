package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackQuestionForm;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;

public class EvaStudentFeedBackQuestionHelpers
{

	private static final Log log=LogFactory.getLog(EvaStudentFeedBackQuestionHelpers.class);
	public static volatile EvaStudentFeedBackQuestionHelpers studentFeedBackQuestionHelpers = null;
   

    public static EvaStudentFeedBackQuestionHelpers getInstance()
    {
        if(studentFeedBackQuestionHelpers == null)
        {
            studentFeedBackQuestionHelpers = new EvaStudentFeedBackQuestionHelpers();
            return studentFeedBackQuestionHelpers;
        } else
        {
            return studentFeedBackQuestionHelpers;
        }
    }

    public List<EvaStudentFeedBackGroupTo> convertBosToTOs(List<EvaStudentFeedbackGroup> feedBackGroup)
    {
        List<EvaStudentFeedBackGroupTo> groupList = new ArrayList<EvaStudentFeedBackGroupTo>();
        if(feedBackGroup != null)
        {
        	Iterator <EvaStudentFeedbackGroup> iterator=feedBackGroup.iterator();
			while(iterator.hasNext())
			{
                EvaStudentFeedbackGroup studentFeedbackGroup = (EvaStudentFeedbackGroup)iterator.next();
                EvaStudentFeedBackGroupTo groupTo = new EvaStudentFeedBackGroupTo();
                groupTo.setId(studentFeedbackGroup.getId());
                groupTo.setName(studentFeedbackGroup.getName());
                groupList.add(groupTo);
            }

        }
        return groupList;
    }

    public List<EvaStudentFeedBackQuestionTo> convertBoToTos(List<EvaStudentFeedbackQuestion> feedbackQuestion)
    {
        List<EvaStudentFeedBackQuestionTo> questionList = new ArrayList<EvaStudentFeedBackQuestionTo>();
        if(feedbackQuestion != null)
        {
        	Iterator itr=feedbackQuestion.iterator();
    		while (itr.hasNext()) {
    			EvaStudentFeedbackQuestion feedbackQuestionlist = (EvaStudentFeedbackQuestion)itr.next();
                EvaStudentFeedBackQuestionTo feedBackQuestionTo= new EvaStudentFeedBackQuestionTo();
                feedBackQuestionTo.setId(feedbackQuestionlist.getId());
                feedBackQuestionTo.setOrder(String.valueOf(feedbackQuestionlist.getOrder()));
                feedBackQuestionTo.setQuestion(feedbackQuestionlist.getQuestion());
                feedBackQuestionTo.setGroupId(feedbackQuestionlist.getGroupId().getName());
                questionList.add(feedBackQuestionTo);
            }

        }
        return questionList;
    }

    public EvaStudentFeedbackQuestion convertFormToBos(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
    {
        EvaStudentFeedbackQuestion feedbackQuestion = new EvaStudentFeedbackQuestion();
        EvaStudentFeedbackGroup type = new EvaStudentFeedbackGroup();
        type.setId(Integer.parseInt(studentFeedBackQuestionForm.getGroupId()));
        feedbackQuestion.setGroupId(type);
        feedbackQuestion.setQuestion(studentFeedBackQuestionForm.getQuestion());
        feedbackQuestion.setOrder(Integer.parseInt(studentFeedBackQuestionForm.getOrder()));
        feedbackQuestion.setCreatedBy(studentFeedBackQuestionForm.getUserId());
        feedbackQuestion.setCreatedDate(new Date());
        feedbackQuestion.setLastModifiedDate(new Date());
        feedbackQuestion.setModifiedBy(studentFeedBackQuestionForm.getUserId());
        feedbackQuestion.setIsActive(Boolean.valueOf(true));
        return feedbackQuestion;
    }

    public void setDataBoToForm(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm, EvaStudentFeedbackQuestion studentFeedbackQuestion)
    {
        if(studentFeedbackQuestion != null)
        {
            studentFeedBackQuestionForm.setGroupId(String.valueOf(studentFeedbackQuestion.getGroupId().getId()));
            studentFeedBackQuestionForm.setQuestion(studentFeedbackQuestion.getQuestion());
            studentFeedBackQuestionForm.setOrder(String.valueOf(studentFeedbackQuestion.getOrder()));
        }
    }

    public EvaStudentFeedbackQuestion updateFormToBo(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
    {
        EvaStudentFeedbackQuestion feedbackQuestion = new EvaStudentFeedbackQuestion();
        EvaStudentFeedbackGroup type = new EvaStudentFeedbackGroup();
        type.setId(Integer.parseInt(studentFeedBackQuestionForm.getGroupId()));
        feedbackQuestion.setGroupId(type);
        feedbackQuestion.setQuestion(studentFeedBackQuestionForm.getQuestion());
        feedbackQuestion.setOrder(Integer.parseInt(studentFeedBackQuestionForm.getOrder()));
        feedbackQuestion.setId(studentFeedBackQuestionForm.getId());
        feedbackQuestion.setLastModifiedDate(new Date());
        feedbackQuestion.setModifiedBy(studentFeedBackQuestionForm.getUserId());
        feedbackQuestion.setIsActive(Boolean.valueOf(true));
        return feedbackQuestion;
    }
}
