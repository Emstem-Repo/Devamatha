package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.forms.exam.ExamInvigilationDutySettingForm;
import com.kp.cms.to.examallotment.ExamInvigilationDutySettingsTo;
import com.kp.cms.to.studentfeedback.BlockBoTo;

public class ExamInvigilationDutySettingHelpers
{

	private static final Log log=LogFactory.getLog(ExamInvigilationDutySettingHelpers.class);
	public static volatile ExamInvigilationDutySettingHelpers invigilationHelpers = null;
   

    public static ExamInvigilationDutySettingHelpers getInstance()
    {
        if(invigilationHelpers == null)
        {
        	invigilationHelpers = new ExamInvigilationDutySettingHelpers();
            return invigilationHelpers;
        } else
        {
            return invigilationHelpers;
        }
    }
    /**
     * @param invigilationForm
     * @return
     */
    public ExamInvigilationDutySettings convertFormToBos(ExamInvigilationDutySettingForm invigilationForm){
    	ExamInvigilationDutySettings roomMaster = new ExamInvigilationDutySettings();
    	
    	if(invigilationForm.getId()>0){
    		roomMaster.setId(invigilationForm.getId());
    	}
    	EmployeeWorkLocationBO type = new EmployeeWorkLocationBO();
        type.setId(Integer.parseInt(invigilationForm.getWorkLocationId()));
       
        roomMaster.setWorkLocationId(type);
        roomMaster.setEndMid(invigilationForm.getEndMid());
        roomMaster.setNoOfSessionsOnSameDay(Integer.parseInt(invigilationForm.getNoOfSessionsOnSameDay()));
        roomMaster.setMaxNoOfStudentsPerTeacher(Integer.parseInt(invigilationForm.getMaxNoOfStudentsPerTeacher()));
        roomMaster.setNoOfRoomsPerReliever(Integer.parseInt(invigilationForm.getNoOfRoomsPerReliever()));
        
        roomMaster.setCreatedBy(invigilationForm.getUserId());
        roomMaster.setCreatedDate(new Date());
        roomMaster.setLastModifiedDate(new Date());
        roomMaster.setModifiedBy(invigilationForm.getUserId());
        roomMaster.setIsActive(Boolean.valueOf(true));
        return roomMaster;
    }
    /**
     * @param locationBos
     * @return
     */
    public List<BlockBoTo> convertBosToTOs(List<EmployeeWorkLocationBO> locationBos)
    {
        List<BlockBoTo> locationList = new ArrayList<BlockBoTo>();
        if(locationBos != null)
        {
        	Iterator <EmployeeWorkLocationBO> iterator=locationBos.iterator();
			while(iterator.hasNext())
			{
				EmployeeWorkLocationBO locationBo = (EmployeeWorkLocationBO)iterator.next();
				BlockBoTo locationTo = new BlockBoTo();
				locationTo.setEmpLocationId(locationBo.getId());
				locationTo.setEmpLocationName(locationBo.getName());
				locationList.add(locationTo);
            }

        }
        return locationList;
    }
    /**
     * @param invigilation
     * @return
     */
    public List<ExamInvigilationDutySettingsTo> convertBoToTos(List<ExamInvigilationDutySettings> invigilation) {
        List<ExamInvigilationDutySettingsTo> invigilationToList= new ArrayList<ExamInvigilationDutySettingsTo>();
        if(invigilation != null)
        {
        	Iterator<ExamInvigilationDutySettings> itr=invigilation.iterator();
    		while (itr.hasNext()) {
    			ExamInvigilationDutySettings invigilationBo = (ExamInvigilationDutySettings)itr.next();
    			ExamInvigilationDutySettingsTo invigilationTo= new ExamInvigilationDutySettingsTo();
    			
    			invigilationTo.setId(invigilationBo.getId());
    			if(invigilationBo.getWorkLocationId()!=null && invigilationBo.getWorkLocationId().getId()>0)
    			    invigilationTo.setLocationName(String.valueOf(invigilationBo.getWorkLocationId().getName()));
    			    invigilationTo.setWorkLocationId(String.valueOf(invigilationBo.getWorkLocationId().getId()));
    			if(invigilationBo.getEndMid()!=null && !invigilationBo.getEndMid().isEmpty() )
    				invigilationTo.setEndMid(invigilationBo.getEndMid().equalsIgnoreCase("E") ? "End Sem":"Mid Sem");
    			if(invigilationBo.getNoOfSessionsOnSameDay()>0)
    				invigilationTo.setNoOfSessionsOnSameDay(Integer.toString(invigilationBo.getNoOfSessionsOnSameDay()));
    			if(invigilationBo.getMaxNoOfStudentsPerTeacher()>0)
    				invigilationTo.setMaxNoOfStudentsPerTeacher(Integer.toString(invigilationBo.getMaxNoOfStudentsPerTeacher()));
    			if(invigilationBo.getNoOfRoomsPerReliever()>0)
    				invigilationTo.setNoOfRoomsPerReliever(Integer.toString(invigilationBo.getNoOfRoomsPerReliever()));
    			invigilationToList.add(invigilationTo);
            }
        }
        return invigilationToList;
    }
    /**
     * @param invigilationForm
     * @param invigilation
     */
    public void setDataBoToForm(ExamInvigilationDutySettingForm invigilationForm, ExamInvigilationDutySettings invigilation)
    {
        if(invigilation != null)
        {
        	invigilationForm.setId(invigilation.getId());
            invigilationForm.setWorkLocationId(String.valueOf(invigilation.getWorkLocationId().getId()));
            invigilationForm.setEndMid(invigilation.getEndMid());
            invigilationForm.setNoOfSessionsOnSameDay(Integer.toString(invigilation.getNoOfSessionsOnSameDay()));
            invigilationForm.setMaxNoOfStudentsPerTeacher(Integer.toString(invigilation.getMaxNoOfStudentsPerTeacher()));
            invigilationForm.setNoOfRoomsPerReliever(Integer.toString(invigilation.getNoOfRoomsPerReliever()));
        }
    }
}
