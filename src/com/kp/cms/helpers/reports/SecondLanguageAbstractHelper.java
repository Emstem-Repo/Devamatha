package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.to.reports.SecLanguageAbstractTO;

public class SecondLanguageAbstractHelper {
	public static volatile SecondLanguageAbstractHelper secondLanguageAbstractHelper = null;

	public static SecondLanguageAbstractHelper getInstance() {
		if (secondLanguageAbstractHelper == null) {
			secondLanguageAbstractHelper = new SecondLanguageAbstractHelper();
			return secondLanguageAbstractHelper;
		}
		return secondLanguageAbstractHelper;
	}

	/**
	 * 
	 * @param studList
	 * @return
	 * @throws Exception
	 */
	public List<SecLanguageAbstractTO> copyBosToList(List<Object[]> studList) throws Exception {
		Iterator<Object[]> iterator = studList.iterator();
		List<SecLanguageAbstractTO> secLanguageToList = new ArrayList<SecLanguageAbstractTO>();
		SecLanguageAbstractTO studentTO;
		
		while (iterator.hasNext()) {
			studentTO = new SecLanguageAbstractTO();
			Object[] student = iterator.next();
			if(student[0] == null){
				continue;
			}
			        
			studentTO.setClassName(student[0].toString());
			studentTO.setSeclanguageCount(student[1].toString());
			
			secLanguageToList.add(studentTO);	
		}
		return secLanguageToList;
	}

}
