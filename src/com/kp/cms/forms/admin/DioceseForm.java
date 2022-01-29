package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.to.admin.SubReligionTo;



public class DioceseForm extends BaseActionForm {
	
	private String dioceseName;
	private List<SubReligionTo> subReligionList;
	private int subReligionId;
	private int dioceseId;
	private List<DioceseTo> dioceseList;
	private String origDioceseName;
	private int origDioceseId;
	private int duplId;
	
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		dioceseName = null;
		dioceseId = 0;
		super.setReligionId(null);
	}

	public int getSubReligionId() {
		return subReligionId;
	}

	public void setSubReligionId(int subReligionId) {
		this.subReligionId = subReligionId;
	}

	public List<SubReligionTo> getSubReligionList() {
		return subReligionList;
	}

	public void setSubReligionList(List<SubReligionTo> subReligionList) {
		this.subReligionList = subReligionList;
	}

	public String getDioceseName() {
		return dioceseName;
	}

	public void setDioceseName(String dioceseName) {
		this.dioceseName = dioceseName;
	}

	public int getDioceseId() {
		return dioceseId;
	}

	public void setDioceseId(int dioceseId) {
		this.dioceseId = dioceseId;
	}

	public List<DioceseTo> getDioceseList() {
		return dioceseList;
	}

	public void setDioceseList(List<DioceseTo> dioceseList) {
		this.dioceseList = dioceseList;
	}

	public String getOrigDioceseName() {
		return origDioceseName;
	}

	public void setOrigDioceseName(String origDioceseName) {
		this.origDioceseName = origDioceseName;
	}

	public int getOrigDioceseId() {
		return origDioceseId;
	}

	public void setOrigDioceseId(int origDioceseId) {
		this.origDioceseId = origDioceseId;
	}

	public int getDuplId() {
		return duplId;
	}

	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}

	

}
