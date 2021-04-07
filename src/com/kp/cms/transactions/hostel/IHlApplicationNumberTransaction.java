package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HostelApplicationNumber;


public interface IHlApplicationNumberTransaction {

	Map<Integer, String> getHostelList() throws Exception;

	boolean save(HostelApplicationNumber number, String mode) throws Exception;

	boolean delete(int id) throws Exception;

	List<HostelApplicationNumber> getHostelApplnNumbers() throws Exception;
	
	
}
