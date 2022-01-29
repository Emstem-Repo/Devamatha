package com.kp.cms.handlers.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.helpers.exam.ValuatorChargesHelper;
import com.kp.cms.to.exam.ValuatorChargeTo;
import com.kp.cms.transactions.exam.IValuatorChargesTransaction;
import com.kp.cms.transactionsimpl.exam.ValuatorChargesImpl;

/**
 * @author Administrator
 *
 */
public class ValuatorChargesHandler {
	private static final Log log=LogFactory.getLog(ValuatorChargesHandler.class);
	public static volatile ValuatorChargesHandler valuatorChargesHandler=null;
	public static ValuatorChargesHandler getInstance()
	{
		if(valuatorChargesHandler==null)
		{
			valuatorChargesHandler=new ValuatorChargesHandler();
			return valuatorChargesHandler;
		}
		return valuatorChargesHandler;
	}
	
	IValuatorChargesTransaction transaction=ValuatorChargesImpl.getInstance();
	/**
	 * @return
	 */
	public List<ValuatorChargeTo> getValuatorChargeList() throws Exception{
	   List<ValuatorChargesBo> valuatorBoList=transaction.getValuatorChargeList();
	   List<ValuatorChargeTo> valuatorToList=ValuatorChargesHelper.getInstance().convertBosToTOs(valuatorBoList);
		return valuatorToList;
	}
	public boolean duplicateCheck(ValuatorChargesForm valuatorChargesForm,ActionErrors errors, HttpSession session) throws Exception{
		boolean duplicate=transaction.duplicateCheck(valuatorChargesForm,valuatorChargesForm.getProgramTypeId(),errors,session);
		return duplicate;
	}
	public boolean addValuatorCharges(ValuatorChargesForm valuatorChargesForm,String mode) throws Exception{
		ValuatorChargesBo valuatorBo=ValuatorChargesHelper.getInstance().convertFormTOBO(valuatorChargesForm);
		boolean isAdded=transaction.addValuator(valuatorBo,mode);
		return isAdded;
	}
	public void editValuatorCharges(ValuatorChargesForm valuatorChargesForm)throws Exception {
		ValuatorChargesBo valuatorBo=transaction.getValuatorChargesById(valuatorChargesForm.getId());
		ValuatorChargesHelper.getInstance().setBotoForm(valuatorChargesForm, valuatorBo);
	}
	public boolean updateValuatorCharges(ValuatorChargesForm valuatorChargesForm ,String mode) throws Exception{
		ValuatorChargesBo valuatorBo=transaction.getValuatorChargesById(valuatorChargesForm.getId());
		valuatorBo=ValuatorChargesHelper.getInstance().convertFormToBO(valuatorBo,valuatorChargesForm);
		boolean isUpdated=transaction.addValuator(valuatorBo,mode);
		return isUpdated;
	}
	public boolean deleteValuatorCharges(ValuatorChargesForm valuatorChargesForm) throws Exception{
		boolean isDeleted=transaction.deleteValuatorCharges(valuatorChargesForm.getId());
		return isDeleted;
		
	}
	public boolean reactivateValuatorCharges(ValuatorChargesForm valuatorChargesForm, String userId) throws Exception{
	     return transaction.reactivatePayScale(valuatorChargesForm);
	}
	
	}
