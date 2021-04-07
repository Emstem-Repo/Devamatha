package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamInvigilationDutySettingsTo;

public class ExamInvigilationDutySettingForm extends BaseActionForm
{
	private int id;
	private String endMid;
	private String noOfSessionsOnSameDay;
	private String maxNoOfStudentsPerTeacher;
	private String noOfRoomsPerReliever;
	private String workLocationId; 
    private List<ExamInvigilationDutySettingsTo> invigilationList;
        
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }
    public void resetClear()
    {
        id = 0;
        endMid =null;
        noOfSessionsOnSameDay ="1";
        maxNoOfStudentsPerTeacher=null;
        noOfRoomsPerReliever = null;
        workLocationId = null;
        invigilationList = null;
        
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEndMid() {
		return endMid;
	}
	public void setEndMid(String endMid) {
		this.endMid = endMid;
	}
	public String getNoOfSessionsOnSameDay() {
		return noOfSessionsOnSameDay;
	}
	public void setNoOfSessionsOnSameDay(String noOfSessionsOnSameDay) {
		this.noOfSessionsOnSameDay = noOfSessionsOnSameDay;
	}
	public String getMaxNoOfStudentsPerTeacher() {
		return maxNoOfStudentsPerTeacher;
	}
	public void setMaxNoOfStudentsPerTeacher(String maxNoOfStudentsPerTeacher) {
		this.maxNoOfStudentsPerTeacher = maxNoOfStudentsPerTeacher;
	}
	public String getNoOfRoomsPerReliever() {
		return noOfRoomsPerReliever;
	}
	public void setNoOfRoomsPerReliever(String noOfRoomsPerReliever) {
		this.noOfRoomsPerReliever = noOfRoomsPerReliever;
	}
	public String getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(String workLocationId) {
		this.workLocationId = workLocationId;
	}
	public List<ExamInvigilationDutySettingsTo> getInvigilationList() {
		return invigilationList;
	}
	public void setInvigilationList(
			List<ExamInvigilationDutySettingsTo> invigilationList) {
		this.invigilationList = invigilationList;
	}
}
