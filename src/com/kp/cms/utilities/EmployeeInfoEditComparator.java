package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.employee.EmpPreviousOrgTo;

public class EmployeeInfoEditComparator implements
		Comparator<EmpPreviousOrgTo> {

	@Override
	public int compare(EmpPreviousOrgTo arg0, EmpPreviousOrgTo arg1) {
		if(arg0!=null && arg1!=null){
			if(arg0.getIndustryFromDate()!=null && arg1.getIndustryToDate()!=null){
				return arg0.getIndustryFromDate().compareTo(arg1.getIndustryToDate());
			}
			if(arg0.getTeachingFromDate()!=null && arg1.getTeachingToDate()!=null){
				return arg0.getTeachingFromDate().compareTo(arg1.getTeachingToDate());
				}
		}
		return 0;
	}

}
