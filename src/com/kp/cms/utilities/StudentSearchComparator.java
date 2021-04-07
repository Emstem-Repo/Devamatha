package com.kp.cms.utilities;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.to.admission.StudentSearchTO;

public class StudentSearchComparator implements Comparator<StudentSearchTO> {
	private int compare=0;
	
	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	@Override
	public int compare(StudentSearchTO arg0, StudentSearchTO arg1) {
		if(compare==0){
			if(arg0!=null && arg1!=null 
					&& arg0.getApplicationId()!=null && arg1.getApplicationId()!=null
					&& StringUtils.isNumeric(arg0.getApplicationId()) && StringUtils.isNumeric(arg1.getApplicationId())){
				if(Integer.parseInt(arg0.getApplicationId())>Integer.parseInt(arg1.getApplicationId()))
					return 1;
			}
			return 0;
		}else if(compare==1){

			if(arg0!=null && arg1!=null && arg0.getApplicantTotalWeightage()!=null 
					 && arg1.getApplicantTotalWeightage()!=null){
					return arg0.getApplicantTotalWeightage().compareTo(arg1.getApplicantTotalWeightage());
			}else
			return 0;
		
		}
		return 0;
	}
}
