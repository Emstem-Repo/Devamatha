package com.kp.cms.helpers.teacherfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackQuestion;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackQuestionForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackGroupTo;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;

public class EvaTeacherFeedBackQuestionHelpers {

	private static final Log log=LogFactory.getLog(EvaTeacherFeedBackQuestionHelpers.class);
	public static volatile EvaTeacherFeedBackQuestionHelpers teacherFeedBackQuestionHelpers = null;
   

    public static EvaTeacherFeedBackQuestionHelpers getInstance()
    {
        if(teacherFeedBackQuestionHelpers == null)
        {
        	teacherFeedBackQuestionHelpers = new EvaTeacherFeedBackQuestionHelpers();
            return teacherFeedBackQuestionHelpers;
        } else
        {
            return teacherFeedBackQuestionHelpers;
        }
    }

    public List<EvaTeacherFeedBackGroupTo> convertBosToTOs(List<EvaTeacherFeedbackGroup> feedBackGroup)
    {
        List<EvaTeacherFeedBackGroupTo> groupList = new ArrayList<EvaTeacherFeedBackGroupTo>();
        if(feedBackGroup != null)
        {
        	Iterator <EvaTeacherFeedbackGroup> iterator=feedBackGroup.iterator();
			while(iterator.hasNext())
			{
                EvaTeacherFeedbackGroup teacherFeedbackGroup = (EvaTeacherFeedbackGroup)iterator.next();
                EvaTeacherFeedBackGroupTo groupTo = new EvaTeacherFeedBackGroupTo();
                groupTo.setId(teacherFeedbackGroup.getId());
                groupTo.setName(teacherFeedbackGroup.getName());
                groupList.add(groupTo);
            }

        }
        return groupList;
    }

    public List<EvaTeacherFeedBackQuestionTo> convertBoToTos(List<EvaTeacherFeedbackQuestion> feedbackQuestion)
    {
        List<EvaTeacherFeedBackQuestionTo> questionList = new ArrayList<EvaTeacherFeedBackQuestionTo>();
        if(feedbackQuestion != null)
        {
        	Iterator itr=feedbackQuestion.iterator();
    		while (itr.hasNext()) {
    			EvaTeacherFeedbackQuestion feedbackQuestionlist = (EvaTeacherFeedbackQuestion)itr.next();
    			EvaTeacherFeedBackQuestionTo feedBackQuestionTo= new EvaTeacherFeedBackQuestionTo();
                feedBackQuestionTo.setId(feedbackQuestionlist.getId());
                feedBackQuestionTo.setOrder(String.valueOf(feedbackQuestionlist.getOrder()));
                feedBackQuestionTo.setQuestion(feedbackQuestionlist.getQuestion());
                feedBackQuestionTo.setGroupId(feedbackQuestionlist.getGroupId().getName());
                questionList.add(feedBackQuestionTo);
            }

        }
        return questionList;
    }

    public EvaTeacherFeedbackQuestion convertFormToBos(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
    {
    	EvaTeacherFeedbackQuestion feedbackQuestion = new EvaTeacherFeedbackQuestion();
        EvaTeacherFeedbackGroup type = new EvaTeacherFeedbackGroup();
        type.setId(Integer.parseInt(teacherFeedBackQuestionForm.getGroupId()));
        feedbackQuestion.setGroupId(type);
        feedbackQuestion.setQuestion(teacherFeedBackQuestionForm.getQuestion());
        feedbackQuestion.setOrder(Integer.parseInt(teacherFeedBackQuestionForm.getOrder()));
        feedbackQuestion.setCreatedBy(teacherFeedBackQuestionForm.getUserId());
        feedbackQuestion.setCreatedDate(new Date());
        feedbackQuestion.setLastModifiedDate(new Date());
        feedbackQuestion.setModifiedBy(teacherFeedBackQuestionForm.getUserId());
        feedbackQuestion.setIsActive(Boolean.valueOf(true));
        return feedbackQuestion;
    }

    public void setDataBoToForm(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm, EvaTeacherFeedbackQuestion teacherFeedbackQuestion)
    {
        if(teacherFeedbackQuestion != null)
        {
        	teacherFeedBackQuestionForm.setGroupId(String.valueOf(teacherFeedbackQuestion.getGroupId().getId()));
        	teacherFeedBackQuestionForm.setQuestion(teacherFeedbackQuestion.getQuestion());
        	teacherFeedBackQuestionForm.setOrder(String.valueOf(teacherFeedbackQuestion.getOrder()));
        }
    }

    public EvaTeacherFeedbackQuestion updateFormToBo(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
    {
        EvaTeacherFeedbackQuestion feedbackQuestion = new EvaTeacherFeedbackQuestion();
        EvaTeacherFeedbackGroup type = new EvaTeacherFeedbackGroup();
        type.setId(Integer.parseInt(teacherFeedBackQuestionForm.getGroupId()));
        feedbackQuestion.setGroupId(type);
        feedbackQuestion.setQuestion(teacherFeedBackQuestionForm.getQuestion());
        feedbackQuestion.setOrder(Integer.parseInt(teacherFeedBackQuestionForm.getOrder()));
        feedbackQuestion.setId(teacherFeedBackQuestionForm.getId());
        feedbackQuestion.setLastModifiedDate(new Date());
        feedbackQuestion.setModifiedBy(teacherFeedBackQuestionForm.getUserId());
        feedbackQuestion.setIsActive(Boolean.valueOf(true));
        return feedbackQuestion;
    }
}
