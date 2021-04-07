package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Designation;

public interface IDesignationEntryTransaction {
	
	public List<Designation> getDesignationEntry() throws Exception;

	public Designation isNameExist(String name, int id) throws Exception;

	public Designation isorderExist(String order, int id) throws Exception;

	public boolean addDesignationEntry(Designation designation) throws Exception;

	public Designation editDesignationEntry(int id) throws Exception;

	public boolean updatedesignationEntry(Designation designation) throws Exception;

	public boolean deleteDesignationEntry(int id, String UserId) throws Exception;

	public boolean reActivateDesignationEntry(String name, String UserId,int id) throws Exception;

}
