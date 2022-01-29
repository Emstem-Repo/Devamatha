package com.kp.cms.transactions.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.forms.exam.ValuatorChargesForm;

public interface IValuatorChargesTransaction {

	public List<ValuatorChargesBo> getValuatorChargeList() throws Exception;

	public boolean duplicateCheck(ValuatorChargesForm valuatorChargesForm, int id)throws Exception;

	public boolean addValuator(ValuatorChargesBo valuatorBo, String mode)throws Exception;

	public ValuatorChargesBo getValuatorChargesById(int id)throws Exception;

	public boolean deleteValuatorCharges(int id)throws Exception;

	public boolean reactivatePayScale(ValuatorChargesForm valuatorChargesForm)throws Exception;

	public boolean duplicateCheck(ValuatorChargesForm valuatorChargesForm,String programTypeId, ActionErrors errors, HttpSession session)throws Exception;

	
}
