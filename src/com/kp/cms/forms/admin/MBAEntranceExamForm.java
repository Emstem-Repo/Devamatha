package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.MBAEntranceExamTO;

@SuppressWarnings("serial")
public class MBAEntranceExamForm extends BaseActionForm {

	private int id;
	private String name;
	private String origName;
	private int dupId;
	private List<MBAEntranceExamTO> entranceExamsTOs;
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
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
	public String getOrigName() {
		return origName;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public List<MBAEntranceExamTO> getEntranceExamsTOs() {
		return entranceExamsTOs;
	}
	public void setEntranceExamsTOs(List<MBAEntranceExamTO> entranceExamsTOs) {
		this.entranceExamsTOs = entranceExamsTOs;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.name = null;
	}
}
