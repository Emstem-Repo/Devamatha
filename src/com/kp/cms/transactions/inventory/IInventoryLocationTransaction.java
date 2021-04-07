package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.forms.inventory.InventoryLocationForm;

public interface IInventoryLocationTransaction {
	public List<Employee> getEmployee() throws Exception;
	public InvLocation isLocationDuplcated(InventoryLocationForm locationForm) throws Exception;
	public boolean addInventoryLocation(InvLocation invLocation, String mode) throws Exception;
	public List<InvLocation> getInventoryLocations() throws Exception;
	public boolean deleteInvLocation(int id, Boolean activate, InventoryLocationForm locationForm) throws Exception;
	public List<InvCampus> getCampus () throws Exception;
}
