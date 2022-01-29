package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Language;
import com.kp.cms.bo.admin.MotherTongue;

/**
 * An interface to manage the transactions related to Language
 * 
 */
public interface ILanguageTransaction {
	
	/**
	 * Add language object to the database
	 * 
	 * @param languageName - Represents the language name.
	 * 
	 * @return  - true if the language is added successfully, false otherwise.
	 */
	public boolean addLanguage(String languageName);
	
	/**
	 * Updates the language.
	 * @param languageId - Language id.
	 * @param languageName - edited language name.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editLanguage(int languageId,String languageName);
	
	/**
	 * Deletes the language from the database.
	 * @param languageId - Represents the language id to be deleted.
	 * @return - true, if the language is deleted successfully, false otherwise.
	 */
	public boolean deleteLanguage(int languageId);
	
	
	/**
	 * Get all language list from the database.
	 * 
	 * @return List - Language transaction List object
	 */
	public List<MotherTongue> getLanguages();
	
	/**
	 * Get all language list from the database.
	 * 
	 * @return List - Language transaction List object
	 */
	public List<Language> getOriginalLanguages();
}
