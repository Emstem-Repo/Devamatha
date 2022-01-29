package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.exam.StudentMarksTO;

public class ExamComparator implements Comparator<StudentMarksTO> {
	private int compare=0;
	
	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	@Override
	public int compare(StudentMarksTO arg0, StudentMarksTO arg1) {
		
		if(arg0!=null && arg1!=null){
			if(compare==0){
				if( arg0.getExamId()!=null && arg1.getExamId()!=null){
					return arg0.getExamId().compareTo(arg1.getExamId());
				}
			}else if(compare==1){
				if( arg0.getExamName()!=null && arg1.getExamName()!=null){
					return arg0.getExamName().compareTo(arg1.getExamName());
				}
			}
		}
		return 0;
	}		
	
}
