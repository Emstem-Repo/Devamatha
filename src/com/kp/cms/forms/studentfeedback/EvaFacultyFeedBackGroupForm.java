package com.kp.cms.forms.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class EvaFacultyFeedBackGroupForm extends BaseActionForm{
	private int id;
    private String name;
    private String order;
    private List facultGroupList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public List getFacultGroupList() {
		return facultGroupList;
	}
	public void setFacultGroupList(List facultGroupList) {
		this.facultGroupList = facultGroupList;
	}
	   public void reset(ActionMapping mapping, HttpServletRequest request)
	    {
	        super.reset(mapping, request);
	        name = null;
	        order = null;
	    }

	    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	    {
	        String formName = request.getParameter("formName");
	        ActionErrors actionErrors = super.validate(mapping, request, formName);
	        return actionErrors;
	    }
    

}
