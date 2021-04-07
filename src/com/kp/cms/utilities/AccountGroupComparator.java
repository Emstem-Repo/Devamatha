package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.fee.FeePaymentDetailFeeGroupTO;

public class AccountGroupComparator  implements Comparator<FeePaymentDetailFeeGroupTO> {
	public int compare(FeePaymentDetailFeeGroupTO arg0,FeePaymentDetailFeeGroupTO arg1) {
		if(arg0!=null && arg1!=null){
			if (arg0.getFeeGroupId() != 0 && arg1.getFeeGroupId()!=0){
			if(arg0.getFeeGroupId() >  arg1.getFeeGroupId())
				return 1;
			}
		}
		return 0;
	}
}
