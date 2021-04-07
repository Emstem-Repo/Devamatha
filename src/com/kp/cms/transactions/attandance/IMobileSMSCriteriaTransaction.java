package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.MobileSMSCriteriaBO;
import com.kp.cms.forms.attendance.MobileSmsCriteriaForm;

public interface IMobileSMSCriteriaTransaction {

	String[] getSmsClassMap(int currentYear, int smsTimehours,int smsMinitue)throws Exception;
	boolean isDuplicateClass(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception;
	boolean addSMSCriteria(MobileSMSCriteriaBO mobileSMSCriteriaBO) throws Exception;
	List<MobileSMSCriteriaBO> getAllSMSCriteriaList()throws Exception;
	boolean deleteSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm)throws Exception;
	MobileSMSCriteriaBO getSMScriteriaDetails(String smsCriteriaId) throws Exception;
	boolean updateSMSCriteria(MobileSMSCriteriaBO smsCriteBo)throws Exception;
	boolean deleteAllOldSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception;
	boolean addSMSCriteriaForAllClass(List<MobileSMSCriteriaBO> boList)throws Exception;

}
