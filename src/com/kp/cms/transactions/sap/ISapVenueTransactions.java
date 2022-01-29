package com.kp.cms.transactions.sap;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.forms.sap.SapVenueForm;

public interface ISapVenueTransactions {
	
	public List<EmployeeWorkLocationBO> getLocationList() throws Exception;
	
	public SapVenue checkForDuplicateonNameAndLoc(String workLocId, String venue)throws Exception;
	
	public boolean addSapVenue(SapVenue sapVenue) throws Exception;
	
	public List<SapVenue> getSapVenueDetails()throws Exception;
	
	public boolean deleteSapVenue(int id, String userId)throws Exception;
	
	public SapVenue getDetailsonId(int id)throws Exception;
	
	public boolean checkForDuplicateonNameUpdate(SapVenueForm sapVenueForm)throws Exception;
	
	public boolean updateSapVenue(SapVenue sapVenue)throws Exception;
	
	public boolean reActivateSapVenue(String venueName, String userId)throws Exception;

	public ExamRegistrationDetails  getstudentDetails(String regNo)throws Exception;

	public boolean updateExamDetails(String regNo, boolean present)throws Exception;

}
