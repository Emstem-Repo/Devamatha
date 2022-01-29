package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.exam.KeyValueTO;
//Mary
public class ExamGradeDefinitionComparator implements Comparator<KeyValueTO>{
	public int compare(KeyValueTO arg0, KeyValueTO arg1) {
		if(arg0!=null && arg1!=null && arg0.getDisplay()!=null
				 && arg1.getDisplay()!=null){
			return arg0.getDisplay().compareTo(arg1.getDisplay());
		}else
		return 0;
	}

}
