package com.kp.cms.transactions.exam;

import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;

public interface INewSecuredMarksEntryTransaction {
	public String getPropertyData(int id, String boName, boolean isActive,String property)throws Exception;

	public boolean saveMarks(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception;
}
