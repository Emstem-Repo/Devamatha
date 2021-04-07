package com.kp.cms.utilities;
import java.util.Comparator;

import com.kp.cms.to.fee.PrintChalanTO;

public class AccountNameComparator  implements Comparator<PrintChalanTO>{
	public int compare(PrintChalanTO arg0,PrintChalanTO arg1) {
		if(arg0!=null && arg1!=null){
			if (arg0.getAccId() != 0 && arg1.getAccId()!=0){
			if(arg0.getAccId() >  arg1.getAccId())
				return 1;
			}
		}
		return 0;
	}
}

