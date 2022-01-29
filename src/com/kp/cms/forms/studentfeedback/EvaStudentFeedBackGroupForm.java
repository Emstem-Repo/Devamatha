package com.kp.cms.forms.studentfeedback;

import com.kp.cms.forms.BaseActionForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class EvaStudentFeedBackGroupForm extends BaseActionForm
{

    private int id;
    private String name;
    private String disOrder;
    private List feedBackGroupList;

    public EvaStudentFeedBackGroupForm()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDisOrder()
    {
        return disOrder;
    }

    public void setDisOrder(String disOrder)
    {
        this.disOrder = disOrder;
    }

    public List getFeedBackGroupList()
    {
        return feedBackGroupList;
    }

    public void setFeedBackGroupList(List feedBackGroupList)
    {
        this.feedBackGroupList = feedBackGroupList;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        name = null;
        disOrder = null;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }
}
