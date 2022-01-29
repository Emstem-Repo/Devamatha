package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.PhdSettingTO;

public class PhdSettingForm extends BaseActionForm {
    
	private int id;
	private String reminderMailBefore;
	private String dueMailAfter;
	private int maxGuidesAssign;
	private List<PhdSettingTO> settingDetails;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() 
	     {
		this.id=0;
        this.reminderMailBefore=null;
        this.dueMailAfter=null;
        this.maxGuidesAssign=0;
         }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReminderMailBefore() {
		return reminderMailBefore;
	}

	public void setReminderMailBefore(String reminderMailBefore) {
		this.reminderMailBefore = reminderMailBefore;
	}

	public String getDueMailAfter() {
		return dueMailAfter;
	}

	public void setDueMailAfter(String dueMailAfter) {
		this.dueMailAfter = dueMailAfter;
	}

	public List<PhdSettingTO> getSettingDetails() {
		return settingDetails;
	}

	public void setSettingDetails(List<PhdSettingTO> settingDetails) {
		this.settingDetails = settingDetails;
	}

	public int getMaxGuidesAssign() {
		return maxGuidesAssign;
	}

	public void setMaxGuidesAssign(int maxGuidesAssign) {
		this.maxGuidesAssign = maxGuidesAssign;
	}

	
	
}
