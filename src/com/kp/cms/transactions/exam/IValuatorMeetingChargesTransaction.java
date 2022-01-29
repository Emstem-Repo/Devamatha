package com.kp.cms.transactions.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.forms.exam.ValuatorMeetingChargesForm;

public interface IValuatorMeetingChargesTransaction {

	public List<ValuatorMeetingChargesBo> getValuatorChargeList() throws Exception;

	public boolean duplicateCheck(ValuatorChargesForm valuatorChargesForm, int id)throws Exception;

	public boolean addValuator(ValuatorMeetingChargesBo valuatorBo, String mode)throws Exception;

	public ValuatorMeetingChargesBo getValuatorChargesById(int id)throws Exception;

	public boolean deleteValuatorCharges(int id)throws Exception;

	public boolean duplicateCheck(ValuatorMeetingChargesForm valuatorMeetingChargesForm,int id, ActionErrors errors, HttpSession session)throws Exception;

	
}
