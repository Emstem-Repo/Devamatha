package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.forms.hostel.DisciplinaryTypeForm;

public interface IDisciplinaryTypeTransactions {
	public List<HlDisciplinaryType> getDisciplinary() throws Exception;
	public HlDisciplinaryType isDisciplinaryTypeDuplcated(String name, int id) throws Exception;
	public boolean addDisciplinaryType(HlDisciplinaryType disciplinaryType, String mode) throws Exception;
	public boolean updateDisciplinaryType(HlDisciplinaryType disciplinaryType);	
	public boolean deleteDisciplinaryType(int id, Boolean activate, DisciplinaryTypeForm disForm) throws Exception;	

}
