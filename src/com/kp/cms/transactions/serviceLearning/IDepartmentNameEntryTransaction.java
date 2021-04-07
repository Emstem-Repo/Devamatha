package com.kp.cms.transactions.serviceLearning;

import java.util.List;

import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;

public interface IDepartmentNameEntryTransaction {
	public List<DepartmentNameEntry> getDepartmentNames()throws Exception;
	public DepartmentNameEntry isDuplicateDepartmentNameEntryDetail(
			DepartmentNameEntryForm departmentNameEntryForm, String mode) throws ApplicationException ;
	public boolean addDepartmentNameEntry(DepartmentNameEntry departmentNameEntry ,String mode)
	throws Exception ;
	public boolean deleteDepartmentNameEntry(int id, boolean activate,
			DepartmentNameEntryForm departmentNameEntryForm) throws Exception;
	public DepartmentNameEntry getDepartmentNameEntryDetails(String id) throws Exception ;
	public boolean reActivateDepartmentNameEntry(DepartmentNameEntry departmentNameEntry,String userId) throws Exception;

}
