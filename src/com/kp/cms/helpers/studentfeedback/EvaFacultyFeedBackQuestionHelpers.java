package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackQuestionForm;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;

public class EvaFacultyFeedBackQuestionHelpers
{

	private static final Log log=LogFactory.getLog(EvaFacultyFeedBackQuestionHelpers.class);
	public static volatile EvaFacultyFeedBackQuestionHelpers facultyFeedBackQuestionHelpers = null;
   

    public static EvaFacultyFeedBackQuestionHelpers getInstance()
    {
        if(facultyFeedBackQuestionHelpers == null)
        {
            facultyFeedBackQuestionHelpers = new EvaFacultyFeedBackQuestionHelpers();
            return facultyFeedBackQuestionHelpers;
        } else
        {
            return facultyFeedBackQuestionHelpers;
        }
    }

    public List<EvaFacultyFeedBackGroupTo> convertBosToTOs(List<EvaFacultyFeedBackGroup> feedBackGroup)
    {
        List<EvaFacultyFeedBackGroupTo> groupList = new ArrayList<EvaFacultyFeedBackGroupTo>();
        if(feedBackGroup != null)
        {
        	Iterator <EvaFacultyFeedBackGroup> iterator=feedBackGroup.iterator();
			while(iterator.hasNext())
			{
                EvaFacultyFeedBackGroup facultyFeedbackGroup = (EvaFacultyFeedBackGroup)iterator.next();
                EvaFacultyFeedBackGroupTo groupTo = new EvaFacultyFeedBackGroupTo();
                groupTo.setId(facultyFeedbackGroup.getId());
                groupTo.setName(facultyFeedbackGroup.getName());
                groupList.add(groupTo);
            }

        }
        return groupList;
    }

    public List<EvaFacultyFeedBackQuestionTo> convertBoToTos(List<EvaFacultyFeedbackQuestion> feedbackQuestion)
    {
        List<EvaFacultyFeedBackQuestionTo> questionList = new ArrayList<EvaFacultyFeedBackQuestionTo>();
        if(feedbackQuestion != null)
        {
        	Iterator itr=feedbackQuestion.iterator();
    		while (itr.hasNext()) {
    			EvaFacultyFeedbackQuestion feedbackQuestionlist = (EvaFacultyFeedbackQuestion)itr.next();
                EvaFacultyFeedBackQuestionTo feedBackQuestionTo= new EvaFacultyFeedBackQuestionTo();
                feedBackQuestionTo.setId(feedbackQuestionlist.getId());
                feedBackQuestionTo.setOrder(String.valueOf(feedbackQuestionlist.getOrder()));
                feedBackQuestionTo.setQuestion(feedbackQuestionlist.getQuestion());
                feedBackQuestionTo.setGroupId(feedbackQuestionlist.getGroupId().getName());
                questionList.add(feedBackQuestionTo);
            }

        }
        return questionList;
    }

    public EvaFacultyFeedbackQuestion convertFormToBos(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm)
    {
        EvaFacultyFeedbackQuestion feedbackQuestion = new EvaFacultyFeedbackQuestion();
        EvaFacultyFeedBackGroup type = new EvaFacultyFeedBackGroup();
        type.setId(Integer.parseInt(facultyFeedBackQuestionForm.getGroupId()));
        feedbackQuestion.setGroupId(type);
        feedbackQuestion.setQuestion(facultyFeedBackQuestionForm.getQuestion());
        feedbackQuestion.setOrder(Integer.parseInt(facultyFeedBackQuestionForm.getOrder()));
        feedbackQuestion.setCreatedBy(facultyFeedBackQuestionForm.getUserId());
        feedbackQuestion.setCreatedDate(new Date());
        feedbackQuestion.setLastModifiedDate(new Date());
        feedbackQuestion.setModifiedBy(facultyFeedBackQuestionForm.getUserId());
        feedbackQuestion.setIsActive(Boolean.valueOf(true));
        return feedbackQuestion;
    }

    public void setDataBoToForm(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm, EvaFacultyFeedbackQuestion facultyFeedbackQuestion)
    {
        if(facultyFeedbackQuestion != null)
        {
            facultyFeedBackQuestionForm.setGroupId(String.valueOf(facultyFeedbackQuestion.getGroupId().getId()));
            facultyFeedBackQuestionForm.setQuestion(facultyFeedbackQuestion.getQuestion());
            facultyFeedBackQuestionForm.setOrder(String.valueOf(facultyFeedbackQuestion.getOrder()));
        }
    }

    public EvaFacultyFeedbackQuestion setFormToBo(EvaFacultyFeedbackQuestion facultyFeedbackQuestion, EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm)
    {
        EvaFacultyFeedbackQuestion feedbackQuestion = new EvaFacultyFeedbackQuestion();
        EvaFacultyFeedBackGroup type = new EvaFacultyFeedBackGroup();
        type.setId(Integer.parseInt(facultyFeedBackQuestionForm.getGroupId()));
        feedbackQuestion.setGroupId(type);
        feedbackQuestion.setQuestion(facultyFeedBackQuestionForm.getQuestion());
        feedbackQuestion.setOrder(Integer.parseInt(facultyFeedBackQuestionForm.getOrder()));
        feedbackQuestion.setId(facultyFeedBackQuestionForm.getId());
        feedbackQuestion.setLastModifiedDate(new Date());
        feedbackQuestion.setModifiedBy(facultyFeedBackQuestionForm.getUserId());
        feedbackQuestion.setIsActive(Boolean.valueOf(true));
        return feedbackQuestion;
    }

}
