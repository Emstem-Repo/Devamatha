package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.PreferencesForm;
import com.kp.cms.helpers.admin.PreferencesHelper;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.transactions.admin.IPreferencesTransaction;
import com.kp.cms.transactionsimpl.admin.PreferencesTransactionImpl;

public class PreferencesHandler {
	public static volatile PreferencesHandler preferencesHandler = null;

	public static PreferencesHandler getInstance() {
		if (preferencesHandler == null) {
			preferencesHandler = new PreferencesHandler();
			return preferencesHandler;
		}
		return preferencesHandler;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */
	public boolean addPreferences(PreferencesForm preferencesForm, String mode)	throws Exception {
		IPreferencesTransaction iprePreferencesTransaction = PreferencesTransactionImpl.getInstance();
		boolean isAdded = false;

		Boolean originalNotChanged = false;

		String courseId = preferencesForm.getCourseId();
		String origCourseId = preferencesForm.getOrigCourseId();
		String prefCourseId = preferencesForm.getPrefCourseId();
		String origPrefCourseId = preferencesForm.getOrigPrefCourseid();

		if ((courseId.equalsIgnoreCase(origCourseId))&& (prefCourseId.equalsIgnoreCase(origPrefCourseId))) {
			originalNotChanged = true;
		}

		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original changed
		}

		//duplication checking
		if (!originalNotChanged) {
			Preferences duplPreferences = PreferencesHelper.getInstance().populatePreferencesDataFromForm(preferencesForm);

			duplPreferences = iprePreferencesTransaction.isPreferencesDuplcated(duplPreferences);
			if (duplPreferences != null	&& duplPreferences.getIsActive()) {
				throw new DuplicateException();
			}
		}

		Preferences preferences = PreferencesHelper.getInstance().populatePreferencesDataFromForm(preferencesForm);
		
		if ("Add".equalsIgnoreCase(mode)) {
			preferences.setCreatedDate(new Date());
			preferences.setLastModifiedDate(new Date());
			preferences.setCreatedBy(preferencesForm.getUserId());
			preferences.setModifiedBy(preferencesForm.getUserId());
		} else // edit
		{
			preferences.setLastModifiedDate(new Date());
			preferences.setModifiedBy(preferencesForm.getUserId());

		}

		isAdded = iprePreferencesTransaction.addPreferences(preferences, mode);
		return isAdded;
	}

	/**
	 * 
	 * @return list of PreferencesTO objects, will be used in UI to display.
	 * @throws Exception
	 */
	public List<PreferencesTO> getPreferences(PreferencesForm preferencesForm,	Boolean isFirstPage) throws Exception {
		IPreferencesTransaction iPreferencesTransaction = PreferencesTransactionImpl.getInstance();
		int courseId = 0;

		if (preferencesForm.getCourseId() != null && !preferencesForm.getCourseId().isEmpty()) {
				courseId = Integer.parseInt(preferencesForm.getCourseId());
		}
		List<Preferences> preferencesList = iPreferencesTransaction.getPreferences(courseId, isFirstPage);
		List<PreferencesTO> PreferencesTOList = PreferencesHelper.getInstance().copyPreferencesBosToTos(preferencesList);
		return PreferencesTOList;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deletePreferences(int id, Boolean activate, PreferencesForm preferencesForm) throws Exception {
		IPreferencesTransaction iprePreferencesTransaction = PreferencesTransactionImpl	.getInstance();
		return iprePreferencesTransaction.deletePreferences(id,activate, preferencesForm);
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteAllPreferences(int courseid, PreferencesForm preferencesForm) throws Exception {
		IPreferencesTransaction iprePreferencesTransaction = PreferencesTransactionImpl.getInstance();
		boolean result = iprePreferencesTransaction.deleteAllPreferences(courseid, preferencesForm);
		return result;
	}

	/**
	 * 
	 * @return list of PreferencesTO objects, will be used in UI to display.
	 * @throws Exception
	 */
	public List<PreferencesTO> getPreferenceswithIdForForm(int id) throws Exception {
		IPreferencesTransaction iPreferencesTransaction = PreferencesTransactionImpl.getInstance();
	
		List<Preferences> preferencesList = iPreferencesTransaction.getPreferencesWithId(id);
		List<PreferencesTO> PreferencesTOList = PreferencesHelper.getInstance().copyPreferencesBosToTos(preferencesList);
		return PreferencesTOList;
	}
		
	/**
	 * 
	 * @return list of PreferencesTO objects, will be used in UI to display.
	 * @throws Exception
	 */
	public List<PreferencesTO> getPreferencesForUpdate(PreferencesForm preferencesForm,	Boolean isFirstPage) throws Exception {
		IPreferencesTransaction iPreferencesTransaction = PreferencesTransactionImpl.getInstance();
		int courseId = 0;
		if (preferencesForm.getEditCourse() != null && !preferencesForm.getEditCourse().isEmpty()) {
			courseId = Integer.parseInt(preferencesForm.getEditCourse());
		}

		List<Preferences> preferencesList = iPreferencesTransaction.getPreferences(courseId, isFirstPage);
		List<PreferencesTO> PreferencesTOList = PreferencesHelper.getInstance().copyPreferencesBosToTos(preferencesList);
		return PreferencesTOList;
	}
	
}
