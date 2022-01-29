package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CasteTO;

/**
 * A Java Bean associated with CasteAction.
 * 
 * @see ActionForm
 * @author prashanth.mh
 */
public class CasteForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int casteId;
	
	private int origCasteId;
	private String origCasteName;
	private String origisFeeExcemption;
	private String casteName;
	private String feeExemption;
	private int reactivateid;
	private int orgreligionId;
	
	public int getOrigCasteId() {
		return origCasteId;
	}

	public void setOrigCasteId(int origCasteId) {
		this.origCasteId = origCasteId;
	}

	public String getOrigCasteName() {
		return origCasteName;
	}

	public void setOrigCasteName(String origCasteName) {
		this.origCasteName = origCasteName;
	}

	public String getOrigisFeeExcemption() {
		return origisFeeExcemption;
	}

	public void setOrigisFeeExcemption(String origisFeeExcemption) {
		this.origisFeeExcemption = origisFeeExcemption;
	}

	public int getReactivateid() {
		return reactivateid;
	}

	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}

	private List<CasteTO> casteList;

	public String getFeeExemption() {
		return feeExemption;
	}

	public void setFeeExemption(String feeExemption) {
		this.feeExemption = feeExemption;
	}

	public int getCasteId() {
		return casteId;
	}

	public void setCasteId(int casteId) {
		this.casteId = casteId;
	}

	public String getCasteName() {
		return casteName;
	}

	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}

	public List<CasteTO> getCasteList() {
		return casteList;
	}

	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.casteName = null;
		this.feeExemption=null;
	
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void setOrgreligionId(int orgreligionId) {
		this.orgreligionId = orgreligionId;
	}

	public int getOrgreligionId() {
		return orgreligionId;
	}
}
