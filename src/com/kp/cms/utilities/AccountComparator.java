package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.bo.admin.FeePaymentDetail;

public class AccountComparator implements Comparator<FeePaymentDetail>{
	private int compare=0;

	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	public int compare(FeePaymentDetail arg0,FeePaymentDetail arg1) {
		if(compare==0){
			if(arg0!=null && arg1!=null && arg0.getFeeAccount()!=null && arg1.getFeeAccount()!=null){
				if (arg0.getFeeAccount().getId() != 0 && arg1.getFeeAccount().getId()!=0){
				if(arg0.getFeeAccount().getId() >  arg1.getFeeAccount().getId())
					return 1;
				}
			}
		}else if(compare==1){
			if (arg0.getId() != 0 && arg1.getId()!=0){
			if(arg0.getId() > arg1.getId())
				return 1;
			}
		}
		return 0;
	}
}
