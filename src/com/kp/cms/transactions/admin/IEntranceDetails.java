package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Entrance;
import com.kp.cms.forms.admin.EntranceDetailsForm;

public interface IEntranceDetails {
	public List<Entrance> getEntranceDetails() throws Exception;
	public boolean addEntranceDetails(Entrance entrance, String mode) throws Exception;	
	public List<Entrance> getEntranceDetailsById(int id) throws Exception;
	public Entrance isEntranceNameDuplcated(Entrance duplEntrance) throws Exception;
	public boolean deleteEntranceDetails(int id,EntranceDetailsForm enForm, Boolean activate) throws Exception;	
	public List<Entrance> getEntranceDetailsByProgram(int programId) throws Exception;
}
