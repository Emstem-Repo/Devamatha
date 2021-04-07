package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.SubjectTO;

public class SubjectGroupDetailsComparator implements Comparator<SubjectTO>{
	
	private int compare=0;
	
	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}

	@Override
	public int compare(SubjectTO arg0, SubjectTO arg1) {
		if(arg0!=null && arg1!=null){
		if(compare==0){
		if(arg0.getCode()!=null && arg1.getCode()!=null){
			return arg0.getCode().compareTo(arg1.getCode());
		}
		}else if(compare==1){
			if (arg0 != null && arg0.getSubOrder()!= 0 && arg1 != null && arg1.getSubOrder()!=0){
				if(arg0.getSubOrder() ==  arg1.getSubOrder())
					return 0;
				else if(arg0.getSubOrder() <  arg1.getSubOrder())
					return -1;
				else 
				    return 1;
			}
		}
		}
	return 0;
	}
}