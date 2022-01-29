package com.kp.cms.transactions.phd;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;


public interface IPhdGuidesFeesPaymentTransactions {
	
	public List<PhdGuideRemunerations> searchGuidesFeesPayment(Date startDate,Date endDate) throws Exception;
	
	public boolean saveGuideFeesPayment(PhdStudentReminderationForm objForm) throws Exception;

	public List<Object[]> getGuideDetailsById(PhdStudentReminderationForm objForm) throws Exception;

	public boolean updateGuideRemenderation(List<PhdGuideRemunerations> guideBo) throws Exception;

}
