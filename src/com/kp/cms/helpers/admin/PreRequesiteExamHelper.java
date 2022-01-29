package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.to.admin.PrerequisiteTO;

public class PreRequesiteExamHelper {
	
	public static volatile PreRequesiteExamHelper preRequesiteExamHelper = null;

	public static PreRequesiteExamHelper getInstance() {
		if (preRequesiteExamHelper == null) {
			preRequesiteExamHelper = new PreRequesiteExamHelper();
			return preRequesiteExamHelper;
		}
		return preRequesiteExamHelper;
	}
	public List<PrerequisiteTO> getTOFromBO(List<Prerequisite>  prerequisiteList)
	{
		List<PrerequisiteTO> preTo=new ArrayList<PrerequisiteTO>();
		Iterator iterator=prerequisiteList.iterator();
		PrerequisiteTO prerequisiteTO=null;
		while(iterator.hasNext())
		{
			prerequisiteTO=new PrerequisiteTO();
			Prerequisite prerequisite=(Prerequisite)iterator.next();
			prerequisiteTO.setId(prerequisite.getId());
			prerequisiteTO.setName(prerequisite.getName());
			preTo.add(prerequisiteTO);
		}
		return preTo;
	}
	

}
