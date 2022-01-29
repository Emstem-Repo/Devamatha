package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;

public class PeersEvaluationGroupsForm extends BaseActionForm{
	private int id;
	private String name;
	List<PeersEvaluationGroupsTO> peersEvaluationGroupList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PeersEvaluationGroupsTO> getPeersEvaluationGroupList() {
		return peersEvaluationGroupList;
	}
	public void setPeersEvaluationGroupList(
			List<PeersEvaluationGroupsTO> peersEvaluationGroupList) {
		this.peersEvaluationGroupList = peersEvaluationGroupList;
	}
	/**
	 * @param mapping
	 * @param request
	 */
	public void clear(ActionMapping mapping, HttpServletRequest request) {
		this.id = 0;
		this.name = null;
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
