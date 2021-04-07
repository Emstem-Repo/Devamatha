package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.PayScaleTO;

public class PayScaleDetailsForm extends BaseActionForm{
	private int id;
	private String payScale;
	private String scale;
	private List<PayScaleTO> payScaleToList;
	private String origPayscale;
	private String origScale;
	private String teachingStaff;
	private String origteachingStaff;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPayScale() {
		return payScale;
	}
	public void setPayScale(String payScale) {
		this.payScale = payScale;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public List<PayScaleTO> getPayScaleToList() {
		return payScaleToList;
	}
	public void setPayScaleToList(List<PayScaleTO> payScaleToList) {
		this.payScaleToList = payScaleToList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void reset(){
		this.payScale=null;
		this.scale=null;
		this.teachingStaff="1";
		this.id=0;
	}
	public String getOrigPayscale() {
		return origPayscale;
	}
	public void setOrigPayscale(String origPayscale) {
		this.origPayscale = origPayscale;
	}
	public String getOrigScale() {
		return origScale;
	}
	public void setOrigScale(String origScale) {
		this.origScale = origScale;
	}
	public String getTeachingStaff() {
		return teachingStaff;
	}
	public void setTeachingStaff(String teachingStaff) {
		this.teachingStaff = teachingStaff;
	}
	public String getOrigteachingStaff() {
		return origteachingStaff;
	}
	public void setOrigteachingStaff(String origteachingStaff) {
		this.origteachingStaff = origteachingStaff;
	}
}
