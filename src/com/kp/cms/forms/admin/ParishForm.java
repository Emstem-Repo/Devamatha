package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.ParishTo;

public class ParishForm extends BaseActionForm
{
	private String parishName;
	private List<DioceseTo> dioceseList;
	private int dioceseId;
	private int parishId;
	private List<ParishTo> parishList;
	private String origParishName;
	private int origParishId;
	private int duplId;
	private String subReligionId;
	public String getParishName() {
		return parishName;
	}
	public void setParishName(String parishName) {
		this.parishName = parishName;
	}
	public List<DioceseTo> getDioceseList() {
		return dioceseList;
	}
	public void setDioceseList(List<DioceseTo> dioceseList) {
		this.dioceseList = dioceseList;
	}
	public int getDioceseId() {
		return dioceseId;
	}
	public void setDioceseId(int dioceseId) {
		this.dioceseId = dioceseId;
	}
	public int getParishId() {
		return parishId;
	}
	public void setParishId(int parishId) {
		this.parishId = parishId;
	}
	public List<ParishTo> getParishList() {
		return parishList;
	}
	public void setParishList(List<ParishTo> parishList) {
		this.parishList = parishList;
	}
	public String getOrigParishName() {
		return origParishName;
	}
	public void setOrigParishName(String origParishName) {
		this.origParishName = origParishName;
	}
	public int getOrigParishId() {
		return origParishId;
	}
	public void setOrigParishId(int origParishId) {
		this.origParishId = origParishId;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getSubReligionId() {
		return subReligionId;
	}
	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		parishName = null;
		dioceseId = 0;
		
	}
	
}
