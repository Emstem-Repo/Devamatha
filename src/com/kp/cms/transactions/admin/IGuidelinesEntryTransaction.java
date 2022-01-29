package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.GuidelinesDoc;


public interface IGuidelinesEntryTransaction {
	
	
	public GuidelinesDoc getGuidelinesEntry(int courseId, int year) throws Exception;
	public boolean isGuidelinesExist(int courseID, int year) throws Exception;
	
	public List<GuidelinesDoc> getGuidelinesEntryAll () throws Exception;
	
	/**
	 * 
	 * @param guidelinesDoc
	 * @return Adds a guidelines entry
	 * @throws Exception
	 */
	public boolean addGuidelinesEntry(GuidelinesDoc guidelinesDoc)throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return Deletes a guidelines entry
	 * @throws Exception
	 */
	public boolean deleteGuidelinesEntry(int id, String userId)throws Exception;

	/**
	 * Check for the duplicate record for the course and year
	 */
	public GuidelinesDoc isGuidelinesExistCourseYear(int courseId, int year)throws Exception;
	
	/**
	 * Updates a record passed updated values from UI
	 */
	public boolean updateGuidelinesEntry(GuidelinesDoc doc) throws Exception;
	
	/**
	 *Downloads the document based on the Id
	 */
	public GuidelinesDoc getGuidelinesEntryonId(int id) throws Exception;
	
	/**
	 * Gets file file details based on Id
	 */
	
	public GuidelinesDoc getFileonId(int id) throws Exception;
	
	
	/**
	 * 
	 * @param courseId
	 * @param year
	 * @return Used in reactivation
	 * @throws Exception
	 */
	
	public boolean reActivateGuidelinesEntry(int courseId, int year, String userId)throws Exception;
}


