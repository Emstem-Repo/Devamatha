package com.kp.cms.utilities;

import java.util.Comparator;
import com.kp.cms.to.hostel.HlApplicationFeeTO;

public class FeeTypeComparator implements Comparator<HlApplicationFeeTO> {
	public int compare(HlApplicationFeeTO arg0, HlApplicationFeeTO arg1) {
		if(arg0!=null && arg1!=null){
			if(arg0.getFeeTypeId()>arg1.getFeeTypeId())
				return 1;
		}
		return 0;
	}
}
