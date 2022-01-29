package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.fee.PrintChalanTO;

public class FeeHeadComparator  implements Comparator<PrintChalanTO> {
	public int compare(PrintChalanTO arg0,PrintChalanTO arg1) {
		if(arg0!=null && arg1!=null && arg0.getFeeGroup()!=null && arg1.getFeeGroup()!=null){
			if (arg0.getFeeGroup()!= null && arg1.getFeeGroup()!= null){
				return arg0.getFeeGroup().compareTo(arg1.getFeeGroup());					
			}
		}
		return 0;
	}
}
