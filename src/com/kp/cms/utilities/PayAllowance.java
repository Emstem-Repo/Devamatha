package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.EmpAllowanceTO;


public class PayAllowance implements Comparator<EmpAllowanceTO>
{
	@Override
	public int compare(EmpAllowanceTO arg0, EmpAllowanceTO arg1) {
	
	if(arg0!=null && arg1!=null){
		if (arg0.getDisplayOrder() != 0 && arg1.getDisplayOrder()!=0){
		if(arg0.getDisplayOrder() > arg1.getDisplayOrder())
			return 1;
		}
	}
	return 0;
}
}