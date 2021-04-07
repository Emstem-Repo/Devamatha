package com.kp.cms.forms.studentfeedback;

import com.kp.cms.forms.BaseActionForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class EvaStudentFeedBackQuestionForm extends BaseActionForm
{

    private int id;
    private String question;
    private String order;
    private String groupId;
    private List feedBackQuestionList;

    public EvaStudentFeedBackQuestionForm()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public List getFeedBackQuestionList()
    {
        return feedBackQuestionList;
    }

    public void setFeedBackQuestionList(List feedBackQuestionList)
    {
        this.feedBackQuestionList = feedBackQuestionList;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        question = null;
        order = null;
        groupId = null;
        id = 0;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }
}
