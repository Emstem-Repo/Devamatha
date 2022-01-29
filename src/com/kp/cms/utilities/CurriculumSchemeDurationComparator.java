package com.kp.cms.utilities;

import java.util.Comparator;


import com.kp.cms.bo.admin.CurriculumSchemeDuration;

public class CurriculumSchemeDurationComparator implements Comparator<CurriculumSchemeDuration>{
	
	public int compare(CurriculumSchemeDuration arg0,CurriculumSchemeDuration arg1) {
		if(arg0!=null && arg1!=null){
			if (arg0.getSemesterYearNo() != 0 && arg1.getSemesterYearNo()!=0){
				if(arg0.getSemesterYearNo() ==  arg1.getSemesterYearNo())
					return 0;
				else if(arg0.getSemesterYearNo() <  arg1.getSemesterYearNo())
					return -1;
				else 
				    return 1;	
			/*if(arg0.getSemesterYearNo() > arg1.getSemesterYearNo())
				return 1;*/
			}
		}
		return 0;
	}
}
