package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Language;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.to.admin.LanguageTO;

public class LanguageHelper {
	
	/**
	 * Converts Business Objects to Transaction object of the language.
	 * 
	 * @param languageBo - Represents the Language Business objects.
	 * 
	 * @return List - Language list Transaction object.
	 */
	public static List<LanguageTO> convertBosToTOs(List<MotherTongue> languageBo){
		List<LanguageTO> languageList= new ArrayList<LanguageTO>();
		if(languageBo!=null){
			Iterator<MotherTongue> languageIteratot= languageBo.iterator();
			while (languageIteratot.hasNext()) {
				MotherTongue language = (MotherTongue) languageIteratot.next();
				LanguageTO languageTO= new LanguageTO();
				languageTO.setLanguageId(language.getId());
				languageTO.setLanguageName(language.getName());
				languageList.add(languageTO);
				
			}
		}
		return languageList;
	}
	/**
	 * Converts Business Objects to Transaction object of the language.
	 * 
	 * @param languageBo - Represents the Language Business objects.
	 * 
	 * @return List - Language list Transaction object.
	 */
	public static List<LanguageTO> convertLanguageBosToTOs(List<Language> languageBo){
		List<LanguageTO> languageList= new ArrayList<LanguageTO>();
		if(languageBo!=null){
			Iterator<Language> languageIteratot= languageBo.iterator();
			while (languageIteratot.hasNext()) {
				Language language = (Language) languageIteratot.next();
				LanguageTO languageTO= new LanguageTO();
				languageTO.setLanguageId(language.getId());
				languageTO.setLanguageName(language.getName());
				languageList.add(languageTO);
				
			}
		}
		return languageList;
	}
}
