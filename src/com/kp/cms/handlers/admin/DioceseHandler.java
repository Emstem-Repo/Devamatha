package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.admin.DioceseBO;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DioceseForm;
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.helpers.admin.DioceseHelper;
import com.kp.cms.helpers.admin.SubReligionHelper;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.transactionsimpl.admin.DioceseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SubReligionTransactionImpl;

import common.Logger;

public class DioceseHandler {

	private static final Logger log = Logger.getLogger(DioceseHandler.class);
	private static volatile DioceseHandler dioceseHandler = null;
	public static DioceseHandler getInstance()
    {
        if(dioceseHandler == null)
        {
        	dioceseHandler = new DioceseHandler();
        }
        return dioceseHandler;
    }

	public Boolean addDiocesehand(DioceseForm dsForm, String mode) throws Exception
	{
		boolean result=false;
		DioceseHelper diocesehel=DioceseHelper.getInstance();
		DioceseTransactionImpl transaction=DioceseTransactionImpl.getInstance();
		DioceseBO dioceseBo=diocesehel.convertFormToBo(dsForm, mode);
		dioceseBo = transaction.isDioceseDuplicated(dioceseBo);
		
		//duplReligionSection is using to check the duplication. if there is duplication then it will return object
		
		if (dioceseBo != null && dioceseBo.getIsActive()) {
			throw new DuplicateException();
		} else if (dioceseBo != null && !dioceseBo.getIsActive()) {
			dsForm.setDuplId(dioceseBo.getId());
			throw new ReActivateException();
		}
		
		dioceseBo=diocesehel.convertFormToBo(dsForm, mode);
		result=transaction.addDiocesetran(dioceseBo,mode);
		
		
		return result;
	}
	public List<DioceseTo> getDiocesehand(DioceseForm dsForm) throws Exception
	{
		
		DioceseHelper diocesehel=DioceseHelper.getInstance();
		DioceseTransactionImpl transaction=DioceseTransactionImpl.getInstance();
		List<DioceseBO> dioceseList=null;
		dioceseList=transaction.getDioceseTran();
		List<DioceseTo> dioceseTO=diocesehel.convertBOToTo(dioceseList);
		return dioceseTO;
	}
	
	public boolean deleteDiocese(int dioceseId, Boolean activate, DioceseForm dsForm) throws Exception {
	 DioceseTransactionImpl dioceseTransaction = DioceseTransactionImpl.getInstance();
		boolean result = dioceseTransaction.deleteDiocese(dioceseId, activate, dsForm);
						//same method is calling for activate & de-activate. so activate param is used
		log.debug("leaving deleteSubReligion in Handler");
		return result;
	}
	public List<DioceseTo> getDiocese() throws Exception{
		DioceseTransactionImpl transaction = DioceseTransactionImpl.getInstance();
		List<DioceseBO> dioceseList = transaction.getDiocese();   // diocese will have list of diocesebo object 
		List<DioceseTo> dioceseToList = DioceseHelper.getInstance().copySubReligionBosToTos(dioceseList);
		log.debug("leaving getSubReligion in Handler");
		return dioceseToList;
	}
	
}
