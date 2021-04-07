package com.kp.cms.handlers.admission;

import java.io.File;
import java.util.List;

import com.kp.cms.bo.admission.PromoteSecondLang;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.forms.admission.AdmPromotionForm;
import com.kp.cms.helpers.admission.AdmPromotionHelper;
import com.kp.cms.transactions.admission.IAdmPromotionTxn;
import com.kp.cms.transactionsimpl.admission.AdmPromotionTxnImpl;

public class AdmPromotionHandler {
	public static volatile AdmPromotionHandler admPromotion = null;
	 public static AdmPromotionHandler getInstance(){
		 if(admPromotion == null){
			 admPromotion = new AdmPromotionHandler();
			 return admPromotion;
		 }
		 return admPromotion;
	 }
	 
	 public boolean uploadSecondLang(File file,AdmPromotionForm admPromote)throws Exception{
		 IAdmPromotionTxn transaction=AdmPromotionTxnImpl.getInstance();
		 List<PromoteSecondLang> languageList=AdmPromotionHelper.getInstance().convertExcelTOBOForSecondLang(file, admPromote);
		 boolean isAdd=transaction.addPromoteSecondLang(languageList, admPromote);
		 return isAdd;
	 }
	 
	 public boolean uploadSupliMarks(File file,AdmPromotionForm admPromote)throws Exception{
		 IAdmPromotionTxn transaction=AdmPromotionTxnImpl.getInstance();
		 List<PromoteSupliMarks> supliMarksList=AdmPromotionHelper.getInstance().convertExcelTOBOForSupliMarks(file, admPromote);
		 boolean isAdd=transaction.addPromoteSupliMarks(supliMarksList, admPromote);
		 return isAdd;
	 }
}
