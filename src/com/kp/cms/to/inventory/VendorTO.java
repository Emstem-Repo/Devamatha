package com.kp.cms.to.inventory;

public class VendorTO  implements Comparable<VendorTO>{
	private int id;
	private String vendorName;
	private String contactPerson;
	private String vendorAddressLine1;
	private String vendorAddressLine2;
	
	public String getVendorName() {
		return vendorName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public String getVendorAddressLine1() {
		return vendorAddressLine1;
	}
	public String getVendorAddressLine2() {
		return vendorAddressLine2;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public void setVendorAddressLine1(String vendorAddressLine1) {
		this.vendorAddressLine1 = vendorAddressLine1;
	}
	public void setVendorAddressLine2(String vendorAddressLine2) {
		this.vendorAddressLine2 = vendorAddressLine2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int compareTo(VendorTO o) {
		return getVendorName().compareTo(o.getVendorName());
	}
	
	
}
