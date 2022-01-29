package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.forms.inventory.VendorForm;

public interface IVendorTransactions {
	public boolean addVendor(InvVendor invVendor, String mode) throws  Exception;
	public List<InvVendor> getVendorDetails() throws Exception;
	public InvVendor getVendor(int id) throws Exception;
	public InvVendor isVendorNameDuplcated(String name, int id) throws Exception;
	public boolean deleteVendor(int id, Boolean activate, VendorForm vendorForm) throws Exception;	
}
