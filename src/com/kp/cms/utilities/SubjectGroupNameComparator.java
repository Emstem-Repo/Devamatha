package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.SubjectGroupTO;

public class SubjectGroupNameComparator implements Comparator<SubjectGroupTO>{
	@Override
	public int compare(SubjectGroupTO arg0, SubjectGroupTO arg1) {
		if(arg0!=null && arg1!=null){
			return arg0.getName().compareTo(arg1.getName());
		}
		return 0;
	}
}