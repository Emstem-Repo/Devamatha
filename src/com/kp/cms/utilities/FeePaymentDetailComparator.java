package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.bo.admin.FeePaymentDetail;

public class FeePaymentDetailComparator implements Comparator<FeePaymentDetail>{
	public int compare(FeePaymentDetail arg0,FeePaymentDetail arg1) {
		if(arg0!=null && arg1!=null){
			if (arg0.getId() != 0 && arg1.getId()!=0){
			if(arg0.getId() > arg1.getId())
				return 1;
			}
		}
		return 0;
	}
}
