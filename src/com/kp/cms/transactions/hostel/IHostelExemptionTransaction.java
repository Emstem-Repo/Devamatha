package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HostelExemptionBo;
import com.kp.cms.bo.hostel.HostelExemptionDetailsBo;
import com.kp.cms.forms.hostel.HostelExemptionForm;

public interface IHostelExemptionTransaction {
	public List<HlAdmissionBo> getHostelStudentData(HostelExemptionForm hostelExemptionForm)throws Exception;
	public boolean saveHostelExemptionDetails(HostelExemptionBo bo)throws Exception;
	public Map<Integer,Integer> getHostelExemptionData(HostelExemptionForm hostelExemptionForm)throws Exception;
	public HostelExemptionDetailsBo getInActiveHlExData(int id)throws Exception;
 }
