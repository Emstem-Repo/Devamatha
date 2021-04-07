package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.employee.OnlineLeaveAppInstructions;
import com.kp.cms.forms.employee.OnlineLeaveAppInstructionForm;

public interface IOnlineLeaveAppInstructionTransaction {

	List<OnlineLeaveAppInstructions> getOnlineLeaveInstructions()throws Exception;

	boolean checkDuplicateNewsEvents(String descMessage)throws Exception;

	boolean saveLeaveInstructions(OnlineLeaveAppInstructions appInstructions)throws Exception;

	public OnlineLeaveAppInstructions getleaveAppInstructions( OnlineLeaveAppInstructionForm appInstructionForm)throws Exception;

	public boolean deleteLeaveAppIns(int leaveAppInsId)throws Exception;

	public boolean updateLeaveInstructions(OnlineLeaveAppInstructionForm appInstructionForm)throws Exception;

}
