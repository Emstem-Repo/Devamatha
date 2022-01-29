package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.forms.hostel.HostelStudentCheckOutForm;

public interface IHostelStudentCheckOutTransaction {
	
	public HlAdmissionBo verifyRegisterNumberAndGetDetails(HostelStudentCheckOutForm checkOutForm) throws Exception;
	
	public List<HlStudentFacilityAllotted> getStudentFacilitiesAlloted(HostelStudentCheckOutForm checkOutForm) throws Exception;
	
	public List<HlAdmissionBo> getStudentCheckOutInformation(HostelStudentCheckOutForm checkOutForm) throws Exception;
	
	public boolean addCheckOut(HlAdmissionBo hlAdmissionBo) throws Exception;

	public HlAdmissionBo getDetailsForMerge(HostelStudentCheckOutForm checkOutForm) throws Exception;

}
