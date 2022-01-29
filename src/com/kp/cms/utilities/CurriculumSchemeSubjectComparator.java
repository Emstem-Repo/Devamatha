package com.kp.cms.utilities;

import java.util.Comparator;


import com.kp.cms.bo.admin.CurriculumSchemeSubject;

public class CurriculumSchemeSubjectComparator implements Comparator<CurriculumSchemeSubject>{

	public int compare(CurriculumSchemeSubject arg0,CurriculumSchemeSubject arg1) {
		if(arg0!=null && arg1!=null){
			if (arg0.getId() != 0 && arg1.getId()!=0){
			if(arg0.getId() > arg1.getId())
				return 1;
			}
		}
		return 0;
	}
}
