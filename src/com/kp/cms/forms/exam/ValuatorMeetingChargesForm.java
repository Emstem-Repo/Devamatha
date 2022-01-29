package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ValuatorChargeTo;
import com.kp.cms.to.exam.ValuatorMeetingChargeTo;

public class ValuatorMeetingChargesForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String boardMeetingCharges;
	private String conveyanceCharge;
	private List<ValuatorMeetingChargeTo> valuatorMeetingChargeList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset() {
		this.id=0;
		this.boardMeetingCharges=null;
		this.conveyanceCharge=null;
			
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
     
	public String getBoardMeetingCharges() {
		return boardMeetingCharges;
	}

	public void setBoardMeetingCharges(String boardMeetingCharges) {
		this.boardMeetingCharges = boardMeetingCharges;
	}

	

	public String getConveyanceCharge() {
		return conveyanceCharge;
	}

	public void setConveyanceCharge(String conveyanceCharge) {
		this.conveyanceCharge = conveyanceCharge;
	}

	public List<ValuatorMeetingChargeTo> getValuatorMeetingChargeList() {
		return valuatorMeetingChargeList;
	}

	public void setValuatorMeetingChargeList(
			List<ValuatorMeetingChargeTo> valuatorMeetingChargeList) {
		this.valuatorMeetingChargeList = valuatorMeetingChargeList;
	}

	

}
