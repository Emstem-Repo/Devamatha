package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.forms.hostel.HostelDamageForm;

public interface IHostelDamageTransaction 
{
	public boolean addHostelDamageEntry(HlDamage hostelDamage) throws Exception;
	public HlDamage getHostelDamageById(int id) throws Exception;
	public boolean deleteHostelDamage(int id,HostelDamageForm hostelDamageForm) throws Exception;
	//based on query get the HlApplicationForm
	public HlApplicationForm getHlapplicationByQuery(String query) throws Exception;
	//based on hostel ID and HlApp id getting the damage list
	public List<HlDamage> getHostelDamagesByHlappId(int hlAppId, int hostelId)throws Exception;	

}
