package com.kp.cms.forms.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.NewsEventsTO;

public class NewsEventsForm extends BaseActionForm {
	
	private String method;
	private int dupId;
	private String description;
	private int newsEventsId;
	private String required;
	private List<NewsEventsTO> newsEventsList;
	
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNewsEventsId() {
		return newsEventsId;
	}
	public void setNewsEventsId(int newsEventsId) {
		this.newsEventsId = newsEventsId;
	}
	public List<NewsEventsTO> getNewsEventsList() {
		return newsEventsList;
	}
	public void setNewsEventsList(List<NewsEventsTO> newsEventsList) {
		this.newsEventsList = newsEventsList;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.description = null;
		this.required="ALL";
	}
}