package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.forms.admin.PreferencesForm;

public interface IPreferencesTransaction {
	public boolean addPreferences(Preferences preferences, String mode);
	public List<Preferences> getPreferences(int courseId, Boolean isFirstPageDisplay) throws Exception;
	public boolean deletePreferences(int prefId, Boolean activate, PreferencesForm preferencesForm)throws Exception;
	public boolean deleteAllPreferences(int courseId, PreferencesForm preferencesForm) throws Exception;
	public Preferences isPreferencesDuplcated(
			Preferences oldpreferences) throws Exception;
	public List<Preferences> getPreferencesWithId(int id) throws Exception;	
}
