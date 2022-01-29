package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.forms.employee.AttributeMasterForm;

public interface IAttributeMasterTransaction {
	public List<EmpAttribute> getAttributeDetails() throws Exception;
	public EmpAttribute isAttributeDuplcated(AttributeMasterForm attributeMasterForm) throws Exception;
	public boolean addAttributeMaster(EmpAttribute attribute, String mode) throws Exception;
	public boolean deleteAttribute(int id, Boolean activate, AttributeMasterForm attributeMasterForm) throws Exception;
}
