package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;



public interface IAddressTransaction {

	public List<Object[]> getAddressDetails(String query) throws Exception;
}
