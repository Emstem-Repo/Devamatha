package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.HostelEntryForm;
import com.kp.cms.forms.hostel.HostelLeaveForm;

public interface IHostelEntryTransactions {
	public List<HlHostel> getHostelDeatils() throws Exception;
	public boolean addHostelEntry(HlHostel hlHostel, String mode) throws Exception;
	public List<HlHostel> getHostelDetailsById(int id) throws Exception;
	public HlHostel isHostelEntryDuplcated(String name, int id) throws Exception;	
	public boolean deleteHostelEntry(int id, Boolean activate, HostelEntryForm hForm) throws Exception;
	public Map<Integer, String> getBlocks(String hostelId) throws Exception;
	public Map<Integer, String> getUnits(String blockId) throws Exception;
	public HlAdmissionBo verifyRegisterNumberAndGetNameAjaxCall(BaseActionForm actionForm) throws Exception;
	public HlAdmissionBo getStudentDetailsByFormCall(HostelLeaveForm actionForm) throws Exception;

}
