package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.forms.exam.ValuatorMeetingChargesForm;
import com.kp.cms.to.exam.ValuatorMeetingChargeTo;

public class ValuatorMeetingChargesHelper {

	private static final Log log=LogFactory.getLog(ValuatorMeetingChargesHelper.class);
	public static volatile ValuatorMeetingChargesHelper valuatorChargesHelper = null;

	public static ValuatorMeetingChargesHelper getInstance() {
		if (valuatorChargesHelper == null) {
			valuatorChargesHelper = new ValuatorMeetingChargesHelper();
			return valuatorChargesHelper;
		}
		return valuatorChargesHelper;
	}

	public List<ValuatorMeetingChargeTo> convertBosToTOs(List<ValuatorMeetingChargesBo> valuatorBoList) {
		List<ValuatorMeetingChargeTo> ValuatorChargeToList=new ArrayList<ValuatorMeetingChargeTo>();
		Iterator itr=valuatorBoList.iterator();
		while (itr.hasNext()) {
			ValuatorMeetingChargesBo valuatorBo=(ValuatorMeetingChargesBo)itr.next();
			ValuatorMeetingChargeTo  valuatorTo=new ValuatorMeetingChargeTo();
			valuatorTo.setId(valuatorBo.getId());
			valuatorTo.setConveyanceCharge(valuatorBo.getConveyanceCharge().toString());
			ValuatorChargeToList.add(valuatorTo);
		}
		return ValuatorChargeToList;
	}

	public ValuatorMeetingChargesBo convertFormTOBO(ValuatorMeetingChargesForm valuatorMeetingChargesForm) {
		ValuatorMeetingChargesBo valuatorBo=new ValuatorMeetingChargesBo();
		valuatorBo.setConveyanceCharge(new BigDecimal(valuatorMeetingChargesForm.getConveyanceCharge()));
		valuatorBo.setCreatedby(valuatorMeetingChargesForm.getUserId());
		valuatorBo.setCreatedDate(new Date());
		valuatorBo.setLastModifiedDate(new Date());
		valuatorBo.setModifiedBy(valuatorMeetingChargesForm.getUserId());
		valuatorBo.setIsActive(true);
		return valuatorBo;
	}

	public void setBotoForm(ValuatorMeetingChargesForm valuatorMeetingChargesForm,ValuatorMeetingChargesBo valuatorBo) 
	{
		if(valuatorBo!=null){
			valuatorMeetingChargesForm.setConveyanceCharge(String.valueOf(valuatorBo.getConveyanceCharge()));
			}
	}

	public ValuatorMeetingChargesBo convertFormToBO(ValuatorMeetingChargesBo valuatorBo, ValuatorMeetingChargesForm valuatorMeetingChargesForm) {
		valuatorBo.setConveyanceCharge(new BigDecimal(valuatorMeetingChargesForm.getConveyanceCharge()));
		valuatorBo.setLastModifiedDate(new Date());
		valuatorBo.setModifiedBy(valuatorMeetingChargesForm.getUserId());
		valuatorBo.setIsActive(true);
		return valuatorBo;
	}

}
