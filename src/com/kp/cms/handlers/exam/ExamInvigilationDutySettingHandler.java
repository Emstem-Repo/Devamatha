package com.kp.cms.handlers.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.forms.exam.ExamInvigilationDutySettingForm;
import com.kp.cms.helpers.exam.ExamInvigilationDutySettingHelpers;
import com.kp.cms.to.examallotment.ExamInvigilationDutySettingsTo;
import com.kp.cms.to.studentfeedback.BlockBoTo;
import com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction;
import com.kp.cms.transactionsimpl.exam.ExamInvigilationDutySettingImpl;

public class ExamInvigilationDutySettingHandler
{

	private static final Log log=LogFactory.getLog(ExamInvigilationDutySettingHandler.class);
	public static volatile ExamInvigilationDutySettingHandler invigilationHandler=null;
	
	public static ExamInvigilationDutySettingHandler getInstance()
    {
        if(invigilationHandler == null)
        {
        	invigilationHandler = new ExamInvigilationDutySettingHandler();
            return invigilationHandler;
        } else
        {
            return invigilationHandler;
        }
    }
	ExamInvigilationDutySettingTransaction transaction = ExamInvigilationDutySettingImpl.getInstance();
   
    /**
     * @return
     * @throws Exception
     */
    public List<BlockBoTo> getEmpLocation()throws Exception
    {
        List<EmployeeWorkLocationBO> feedBackGroup = transaction.getEmpLocation();
        List<BlockBoTo> group = ExamInvigilationDutySettingHelpers.getInstance().convertBosToTOs(feedBackGroup);
        return group;
    }
   
    /**
     * @param invigilationForm
     * @param errors
     * @param session
     * @return
     * @throws Exception
     */
    public boolean duplicateCheck(ExamInvigilationDutySettingForm invigilationForm, ActionErrors errors, HttpSession session)
        throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(invigilationForm, errors, session);
        return duplicate;
    }

    /**
     * @param invigilationForm
     * @return
     * @throws Exception
     */
    public boolean addInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm) throws Exception
    {
    	ExamInvigilationDutySettings invigilation =ExamInvigilationDutySettingHelpers.getInstance().convertFormToBos(invigilationForm);
        boolean isAdded = transaction.addInvigilation(invigilation);
        return isAdded;
    }
    
    /**
     * @param invigilationForm
     * @return
     * @throws Exception
     */
    public List<ExamInvigilationDutySettingsTo> getInvigilationList(ExamInvigilationDutySettingForm invigilationForm) throws Exception
    {
        List<ExamInvigilationDutySettings> invigilation = transaction.getInvigilationList(invigilationForm);
        List<ExamInvigilationDutySettingsTo> invigilationToList = ExamInvigilationDutySettingHelpers.getInstance().convertBoToTos(invigilation);
        return invigilationToList;
    }
  
    /**
     * @param invigilationForm
     * @throws Exception
     */
    public void editInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm) throws Exception
    {
    	ExamInvigilationDutySettings invigilation = transaction.getInvigilationById(invigilationForm.getId());
    	ExamInvigilationDutySettingHelpers.getInstance().setDataBoToForm(invigilationForm, invigilation);
    }

    /**
     * @param invigilationForm
     * @return
     * @throws Exception
     */
    public boolean updateInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm) throws Exception
    {
    	ExamInvigilationDutySettings invigilation =ExamInvigilationDutySettingHelpers.getInstance().convertFormToBos(invigilationForm);
        boolean isUpdated = transaction.updateInvigilationDuty(invigilation);
        return isUpdated;
    }

    /**
     * @param invigilationForm
     * @return
     * @throws Exception
     */
    public boolean deleteInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm) throws Exception
    {
        boolean isDeleted = transaction.deleteInvigilationDuty(invigilationForm.getId());
        return isDeleted;
    }
	/**
	 * @param invigilationForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm, String userId) throws Exception
	{
		return transaction.reActivateInvigilationDuty(invigilationForm);
	
		
	}
}
