package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.fee.FeeDisplayTO;



public class FeePaymentListComparator  implements Comparator<FeeDisplayTO> {
	public int compare(FeeDisplayTO arg0,FeeDisplayTO arg1) {
		if(arg0!=null && arg1!=null && arg0.getAccName()!=null && arg1.getAccName()!=null){
			if (arg0.getAccName()!= null && arg1.getAccName()!= null){
				return arg0.getAccName().compareTo(arg1.getAccName());					
			}
		}
		return 0;
	}
}
