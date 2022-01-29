package com.kp.cms.handlers.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.forms.exam.ValuatorMeetingChargesForm;
import com.kp.cms.helpers.exam.ValuatorMeetingChargesHelper;
import com.kp.cms.to.exam.ValuatorMeetingChargeTo;
import com.kp.cms.transactions.exam.IValuatorMeetingChargesTransaction;
import com.kp.cms.transactionsimpl.exam.ValuatorMeetingChargesImpl;

/**
 * @author Administrator
 *
 */
public class ValuatorMeetingChargesHandler {
	private static final Log log=LogFactory.getLog(ValuatorMeetingChargesHandler.class);
	public static volatile ValuatorMeetingChargesHandler valuatorChargesHandler=null;
	public static ValuatorMeetingChargesHandler getInstance()
	{
		if(valuatorChargesHandler==null)
		{
			valuatorChargesHandler=new ValuatorMeetingChargesHandler();
			return valuatorChargesHandler;
		}
		return valuatorChargesHandler;
	}
	
	IValuatorMeetingChargesTransaction transaction=ValuatorMeetingChargesImpl.getInstance();
	/**
	 * @return
	 */
	public List<ValuatorMeetingChargeTo> getValuatorChargeList() throws Exception{
	   List<ValuatorMeetingChargesBo> valuatorBoList=transaction.getValuatorChargeList();
	   List<ValuatorMeetingChargeTo> valuatorToList=ValuatorMeetingChargesHelper.getInstance().convertBosToTOs(valuatorBoList);
		return valuatorToList;
	}
	public boolean duplicateCheck(ValuatorMeetingChargesForm valuatorMeetingChargesForm,ActionErrors errors, HttpSession session) throws Exception{
		boolean duplicate=transaction.duplicateCheck(valuatorMeetingChargesForm,valuatorMeetingChargesForm.getId(),errors,session);
		return duplicate;
	}
	public boolean addValuatorCharges(ValuatorMeetingChargesForm valuatorMeetingChargesForm,String mode) throws Exception{
		ValuatorMeetingChargesBo valuatorBo=ValuatorMeetingChargesHelper.getInstance().convertFormTOBO(valuatorMeetingChargesForm);
		boolean isAdded=transaction.addValuator(valuatorBo,mode);
		return isAdded;
	}
	public void editValuatorCharges(ValuatorMeetingChargesForm valuatorMeetingChargesForm)throws Exception {
		ValuatorMeetingChargesBo valuatorBo=transaction.getValuatorChargesById(valuatorMeetingChargesForm.getId());
		ValuatorMeetingChargesHelper.getInstance().setBotoForm(valuatorMeetingChargesForm, valuatorBo);
	}
	public boolean updateValuatorCharges(ValuatorMeetingChargesForm valuatorMeetingChargesForm ,String mode) throws Exception{
		ValuatorMeetingChargesBo valuatorBo=transaction.getValuatorChargesById(valuatorMeetingChargesForm.getId());
		valuatorBo=ValuatorMeetingChargesHelper.getInstance().convertFormToBO(valuatorBo,valuatorMeetingChargesForm);
		boolean isUpdated=transaction.addValuator(valuatorBo,mode);
		return isUpdated;
	}
	public boolean deleteValuatorCharges(ValuatorMeetingChargesForm valuatorMeetingChargesForm) throws Exception{
		boolean isDeleted=transaction.deleteValuatorCharges(valuatorMeetingChargesForm.getId());
		return isDeleted;
		
	}
	}
