package com.kp.cms.transactions.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.forms.exam.ExamInvigilationDutySettingForm;

public interface ExamInvigilationDutySettingTransaction
{

    public List<EmployeeWorkLocationBO> getEmpLocation()throws Exception;

    public  boolean duplicateCheck(ExamInvigilationDutySettingForm invigilationForm, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

    public  boolean addInvigilation(ExamInvigilationDutySettings invigilation)throws Exception;
    
    public List<ExamInvigilationDutySettings> getInvigilationList(ExamInvigilationDutySettingForm invigilationForm)throws Exception;

    public  ExamInvigilationDutySettings getInvigilationById(int id)throws Exception;
    
    public boolean updateInvigilationDuty(ExamInvigilationDutySettings invigilation) throws Exception;

    public  boolean deleteInvigilationDuty(int id)throws Exception;

	public boolean reActivateInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm) throws Exception;

}
