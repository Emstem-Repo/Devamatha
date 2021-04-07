package com.kp.cms.transactions.inventory;

import com.kp.cms.bo.admin.InvPOTermsAndConditions;
import com.kp.cms.forms.inventory.POTermsConditionsForm;

public interface IPOTermsConditionsTransaction {

	InvPOTermsAndConditions getTCBo() throws Exception;

	boolean addTermsAndConditions(InvPOTermsAndConditions bo,POTermsConditionsForm poForm) throws Exception;

	boolean deleteTermsConditions(int id, boolean activate, String userId) throws Exception;

}
