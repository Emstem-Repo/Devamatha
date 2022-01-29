package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Organisation;

public interface IOrganizationTransaction {
	
	/**
	 * Used for inserting an organization details
	 */
	
	public boolean saveOrganizationDetails(Organisation organisation)throws Exception;
	
	/**
	 * Used while getting all organization details from DB
	 */
	
	public List<Organisation> getOrganizationDetails() throws Exception;
	
	/**
	 * Used for deleting an organization details
	 */
	
	public boolean deleteOrganizationDetails(Organisation organisation)throws Exception;
	
	/**
	 * Used for File downloading
	 */
	
	public Organisation getRequiredFile(int id)throws Exception;
	
	public Organisation getRequiredFile()throws Exception;
	/**
	 * Used to update Organization Details
	 */
	public boolean updateOrganizationDetails(Organisation newOrganizationBO)throws Exception;

}
