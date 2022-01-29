package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class ExamCodeComparator implements Comparator<ExamMarksEntryDetailsTO> {
	private int compare=0;
	
	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	@Override
	public int compare(ExamMarksEntryDetailsTO arg0, ExamMarksEntryDetailsTO arg1) {
		
		if(arg0!=null && arg1!=null){
			if(compare==0){
				if( arg0.getExamCode()!=null && arg1.getExamCode()!=null){
					return arg0.getExamCode().compareTo(arg1.getExamCode());
				}
			}else if(compare==1){
				if(arg0.getIsTheoryPractical()!=null && arg1.getIsTheoryPractical()!=null){
					return arg0.getIsTheoryPractical().compareTo(arg1.getIsTheoryPractical());
				}
			}else if(compare==2){
				if(arg0.getRegNo()!=null 
						 && arg1.getRegNo()!=null){
					if(arg0.getRegNo().equalsIgnoreCase(arg1.getRegNo()))
						return arg0.getId().compareTo(arg1.getId());
					else
						return arg0.getRegNo().compareTo(arg1.getRegNo());
				}
			}
		}
		return 0;
	}		
	
}
