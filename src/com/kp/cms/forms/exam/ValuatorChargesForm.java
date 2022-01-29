package com.kp.cms.forms.exam;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ValuatorChargeTo;

public class ValuatorChargesForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String programTypeId;
	private String valuatorcharge;
	private String reviewercharge;
	private String projectevaluationminor;
	private String projectevaluationmajor;
	private List<ValuatorChargeTo> valuatorChargeList;
	private String boardMeetingCharge;
	private String minimumScripts;
	private String minimumvaluatorcharge;
	private String minimumreviewercharge;
	private String minimumprojectevaluationminor;
	private String minimumprojectevaluationmajor;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset() {
		this.programTypeId=null;
		this.valuatorcharge=null;
		this.reviewercharge=null;
		this.projectevaluationminor=null;
		this.projectevaluationmajor=null;
		this.boardMeetingCharge=null;
		this.minimumScripts=null;
		this.minimumvaluatorcharge=null;
		this.minimumreviewercharge=null;
		this.minimumprojectevaluationmajor=null;
		this.minimumprojectevaluationminor=null;
	}
	
	public String getValuatorcharge() {
		return valuatorcharge;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public void setValuatorcharge(String valuatorcharge) {
		this.valuatorcharge = valuatorcharge;
	}

	public String getReviewercharge() {
		return reviewercharge;
	}

	public void setReviewercharge(String reviewercharge) {
		this.reviewercharge = reviewercharge;
	}

	public String getProjectevaluationminor() {
		return projectevaluationminor;
	}

	public void setProjectevaluationminor(String projectevaluationminor) {
		this.projectevaluationminor = projectevaluationminor;
	}

	public String getProjectevaluationmajor() {
		return projectevaluationmajor;
	}

	public void setProjectevaluationmajor(String projectevaluationmajor) {
		this.projectevaluationmajor = projectevaluationmajor;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ValuatorChargeTo> getValuatorChargeList() {
		return valuatorChargeList;
	}

	public void setValuatorChargeList(List<ValuatorChargeTo> valuatorChargeList) {
		this.valuatorChargeList = valuatorChargeList;
	}

	public String getBoardMeetingCharge() {
		return boardMeetingCharge;
	}

	public void setBoardMeetingCharge(String boardMeetingCharge) {
		this.boardMeetingCharge = boardMeetingCharge;
	}


	

	public String getMinimumScripts() {
		return minimumScripts;
	}

	public void setMinimumScripts(String minimumScripts) {
		this.minimumScripts = minimumScripts;
	}

	public String getMinimumvaluatorcharge() {
		return minimumvaluatorcharge;
	}

	public void setMinimumvaluatorcharge(String minimumvaluatorcharge) {
		this.minimumvaluatorcharge = minimumvaluatorcharge;
	}

	public String getMinimumreviewercharge() {
		return minimumreviewercharge;
	}

	public void setMinimumreviewercharge(String minimumreviewercharge) {
		this.minimumreviewercharge = minimumreviewercharge;
	}

	public String getMinimumprojectevaluationminor() {
		return minimumprojectevaluationminor;
	}

	public void setMinimumprojectevaluationminor(
			String minimumprojectevaluationminor) {
		this.minimumprojectevaluationminor = minimumprojectevaluationminor;
	}

	public String getMinimumprojectevaluationmajor() {
		return minimumprojectevaluationmajor;
	}

	public void setMinimumprojectevaluationmajor(
			String minimumprojectevaluationmajor) {
		this.minimumprojectevaluationmajor = minimumprojectevaluationmajor;
	}

}
