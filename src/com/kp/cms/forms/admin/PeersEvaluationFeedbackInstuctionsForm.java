package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;

public class PeersEvaluationFeedbackInstuctionsForm extends BaseActionForm{
	private int id;
	private String description;
	private int peersInstructionsId;
	List<StudentFeedbackInstructionsTO> instructionsTOsList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPeersInstructionsId() {
		return peersInstructionsId;
	}
	public void setPeersInstructionsId(int peersInstructionsId) {
		this.peersInstructionsId = peersInstructionsId;
	}
	public List<StudentFeedbackInstructionsTO> getInstructionsTOsList() {
		return instructionsTOsList;
	}
	public void setInstructionsTOsList(
			List<StudentFeedbackInstructionsTO> instructionsTOsList) {
		this.instructionsTOsList = instructionsTOsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.forms.BaseActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.id= 0;
		this.description = null;
	}
}
