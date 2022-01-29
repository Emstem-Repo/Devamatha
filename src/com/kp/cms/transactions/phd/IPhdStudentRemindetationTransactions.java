package com.kp.cms.transactions.phd;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;


public interface IPhdStudentRemindetationTransactions {
	
	public List<Object[]> studentDetailsSearch(Date startDate, Date endDate) throws Exception;

	public List<Object[]> generateStudentDetails(PhdStudentReminderationForm objForm) throws Exception;
	
	public boolean saveGuideRemenderation(List<PhdGuideRemunerations> guideBo) throws Exception;
	
	public PhdVoucherNumber getPhdVoucherNumber()  throws Exception;
	
	public void setUpdateGeneratedDate(PhdStudentReminderationForm objForm) throws Exception;
	
	public void setUpdateVoucherNo(PhdStudentReminderationForm objForm) throws Exception;

}
