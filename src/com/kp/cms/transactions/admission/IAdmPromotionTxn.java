package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admission.PromoteSecondLang;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.forms.admission.AdmPromotionForm;

public interface IAdmPromotionTxn {
	public boolean addPromoteSecondLang(List<PromoteSecondLang> list,AdmPromotionForm admPromote)throws Exception;
	public boolean addPromoteSupliMarks(List<PromoteSupliMarks> list,AdmPromotionForm admPromote)throws Exception;
}
