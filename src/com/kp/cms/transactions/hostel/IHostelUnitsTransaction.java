package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.forms.hostel.HostelUnitsForm;

public interface IHostelUnitsTransaction {
	
	public HlUnits checkForDuplicateonUnitsName(String blockId, String name)throws Exception;
	
	public boolean addHostelUnits(HlUnits hlUnits) throws Exception;
	
	public List<HlUnits> getHostelUnitsDetails()throws Exception;
	
	public boolean deleteHostelUnits(int id, String userId)throws Exception;
	
	public boolean updateHostelUnits(HlUnits hlUnits)throws Exception;
	
	public boolean reActivateHostelUnits(String name, String userId)throws Exception;
	
	public HlUnits getUnitsDetailsonId(int id)throws Exception;
	
	public boolean checkForDuplicateonNameSameId(String name, HostelUnitsForm hostelUnitsForm)throws Exception;

}
