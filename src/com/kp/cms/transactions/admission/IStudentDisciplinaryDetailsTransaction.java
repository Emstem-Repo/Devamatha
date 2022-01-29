package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.DisciplineAndAchivement;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;

public interface IStudentDisciplinaryDetailsTransaction {
	public List<DisciplineAndAchivement> getHostelDisciplinesList(StudentEditForm studentEditForm) throws Exception;
	public boolean addHostelStudentDisciplineDetails(DisciplineAndAchivement bo)throws Exception;	
	public HlAdmissionBo checkStudentPresent(String year, String regNo)throws Exception;
	public List<HlDisciplinaryDetails> getDisciplineDetailsAcc(HostelDisciplinaryDetailsForm detailsForm)throws Exception;
	public DisciplineAndAchivement getDetailsonId(int id)throws Exception ;
	public boolean updateHostelStudentDisciplineDetails(DisciplineAndAchivement disciplineAndAchivement)throws Exception;
	public boolean deleteStudentDisciplineDetails(int id, String userId)throws Exception;
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc(BaseActionForm actionForm) throws Exception;
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc1(HostelDisciplinaryDetailsForm byForm) throws Exception;
	public List<HlDisciplinaryDetails> getDisciplinaryDetailsByRegNoAndYear( HostelDisciplinaryDetailsForm hldForm)throws Exception;
}
