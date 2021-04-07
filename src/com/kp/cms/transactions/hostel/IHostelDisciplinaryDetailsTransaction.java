package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;

public interface IHostelDisciplinaryDetailsTransaction {
	public List<HlDisciplinaryType> getHostelDisciplinesList() throws Exception;
	public boolean addHostelStudentDisciplineDetails(HlDisciplinaryDetails hlDisciplinaryDetails)throws Exception;	
	public HlAdmissionBo checkStudentPresent(String year, String regNo)throws Exception;
	public List<HlDisciplinaryDetails> getDisciplineDetailsAcc(HostelDisciplinaryDetailsForm detailsForm)throws Exception;
	public HlDisciplinaryDetails getDetailsonId(int id)throws Exception ;
	public boolean updateHostelStudentDisciplineDetails(HlDisciplinaryDetails hlDisciplinaryDetails)throws Exception;
	public boolean deleteHostelStudentDisciplineDetails(int id, String userId)throws Exception;
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc(BaseActionForm actionForm) throws Exception;
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc1(HostelDisciplinaryDetailsForm byForm) throws Exception;
	public List<HlDisciplinaryDetails> getDisciplinaryDetailsByRegNoAndYear( HostelDisciplinaryDetailsForm hldForm)throws Exception;
}
