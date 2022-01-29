package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.fee.FeePaymentDetailEditTO;

public class FeeEditComparator  implements Comparator<FeePaymentDetailEditTO>{
	public int compare(FeePaymentDetailEditTO arg0,FeePaymentDetailEditTO arg1) {
		if(arg0!=null && arg1!=null && arg0.getAccountName() != null && arg1.getAccountName()!=null ){
				return arg0.getAccountName().compareTo(arg1.getAccountName());
		}
		else
		{
			return 0;
		}
		
	}	
}

