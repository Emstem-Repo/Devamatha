package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.DioceseBO;
import com.kp.cms.bo.admin.ParishBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DioceseForm;
import com.kp.cms.forms.admin.ParishForm;
import com.kp.cms.helpers.admin.DioceseHelper;
import com.kp.cms.helpers.admin.ParishHelper;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.ParishTo;
import com.kp.cms.transactionsimpl.admin.DioceseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.ParishTransactionImpl;

import common.Logger;

public class ParishHandler {

	private static final Logger log = Logger.getLogger(ParishHandler.class);
	private static volatile ParishHandler parishHandler = null;
	public static ParishHandler getInstance()
    {
        if(parishHandler == null)
        {
        	parishHandler = new ParishHandler();
        }
        return parishHandler;
    }
	
	public Boolean addParishhand(ParishForm dsForm, String mode) throws Exception
	{
		boolean result=false;
		ParishHelper parishhel=ParishHelper.getInstance();
		ParishTransactionImpl transaction=ParishTransactionImpl.getInstance();
		ParishBO parishBo=parishhel.convertFormToBo(dsForm, mode);
		parishBo = transaction.isParishDuplicated(parishBo);
		
		//duplReligionSection is using to check the duplication. if there is duplication then it will return object
		
		if (parishBo != null && parishBo.getIsActive()) {
			throw new DuplicateException();
		} else if (parishBo != null && !parishBo.getIsActive()) {
			dsForm.setDuplId(parishBo.getId());
			throw new ReActivateException();
		}
		
		parishBo=parishhel.convertFormToBo(dsForm, mode);
		result=transaction.addParishtran(parishBo,mode);
		
		
		return result;
	}
	public List<ParishTo> getParishhand(ParishForm pForm) throws Exception
	{
		
		ParishHelper parishhel=ParishHelper.getInstance();
		ParishTransactionImpl transaction=ParishTransactionImpl.getInstance();
		List<ParishBO> parishList=null;
		parishList=transaction.getParishTran();
		List<ParishTo> parishTO=parishhel.convertBOToTo(parishList);
		return parishTO;
	}
	
	public boolean deleteParish(int parishId, Boolean activate, ParishForm pForm) throws Exception {
		ParishTransactionImpl parishTransaction = ParishTransactionImpl.getInstance();
		boolean result = parishTransaction.deleteParish(parishId, activate, pForm);
						//same method is calling for activate & de-activate. so activate param is used
		log.debug("leaving parish in Handler");
		return result;
	}
	public List<ParishTo> getParish() throws Exception{
		ParishTransactionImpl transaction = ParishTransactionImpl.getInstance();
		List<ParishBO> parishList = transaction.getParish();   // parish will have list of parishBO object 
		List<ParishTo> parishToList = ParishHelper.getInstance().copyParishBosToTos(parishList);
		log.debug("leaving getparish in Handler");
		return parishToList;
	}
}
