package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.Language;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.helpers.admin.LanguageHelper;
import com.kp.cms.to.admin.LanguageTO;
import com.kp.cms.transactions.admin.ILanguageTransaction;
import com.kp.cms.transactionsimpl.admin.LanguageTransactionImpl;

/**
 * Manages the operations of add,edit, delete of merit set.
 * @version 1.0 12 Jan 2009
 */
public class LanguageHandler {
	
	/** Represents LanguageHandler object.*/
	private static volatile LanguageHandler handler = null;

	private LanguageHandler() {
		// An empty constructor.
	}
	
	/**
	 * Returns the single instance object of LanguageHandler.
	 * @return LanguageHandler - LanguageHandler object.
	 */
	public static LanguageHandler getHandler() {
		if (handler == null)
			handler = new LanguageHandler();
		return handler;
	}
	
	/**
	 * Get all language list from the database.
	 * 
	 * @return List - Language transaction List object
	 */
	public List<LanguageTO> getLanguages() {
		ILanguageTransaction txn = new LanguageTransactionImpl();
		List<MotherTongue> languagebos = txn.getLanguages();
		return LanguageHelper.convertBosToTOs(languagebos);
	}
	/**
	 * Get all language list from the database.
	 * 
	 * @return List - Language transaction List object
	 */
	public List<LanguageTO> getOriginalLanguages() {
		ILanguageTransaction txn = new LanguageTransactionImpl();
		List<Language> languagebos = txn.getOriginalLanguages();
		return LanguageHelper.convertLanguageBosToTOs(languagebos);
	}
	
	/**
	 * Add Language to the database.
	 * @param languageName - Represents the languageName.
	 * @return - true for successful addition, false otherwise.
	 */
	public boolean addLanguage(String languageName) {
		boolean isMeritsetAdded = false;
		LanguageTransactionImpl languageTransactionImpl = new LanguageTransactionImpl();
		if(languageTransactionImpl.addLanguage(languageName)) {
			isMeritsetAdded = true;
		}
		return isMeritsetAdded;
	}
	
	
	/**
	 * Updates the language.
	 * @param languageId - Language id.
	 * @param languageName - edited language name.
	 * @return - true , if the data is updated successfully in the database , false otherwise.
	 */
	public boolean editLanguage(int languageId,String languageName) {
		boolean isMeritsetedited = false;
		LanguageTransactionImpl languageTransactionImpl = new LanguageTransactionImpl();
		if(languageTransactionImpl.editLanguage(languageId,languageName)) {
			isMeritsetedited = true;
		}
		return isMeritsetedited;
	}
	
	
	
	/**
	 * Deletes the language from the database.
	 * @param languageId - Represents the language id to be deleted.
	 * @return - true, if the language is deleted successfully, false otherwise.
	 */
	public boolean deleteLanguage(int languageId) {
		LanguageTransactionImpl languageTransactionImpl = new LanguageTransactionImpl();
		boolean	islanguagedeleted =	languageTransactionImpl.deleteLanguage(languageId);
		return islanguagedeleted;		
	}
}
