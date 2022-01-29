package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ApplnDocTO;

/**
 * @author Nagarjun
 *
 */
public class DocumentVerificationEntryForm extends BaseActionForm {
	private String registerNo;
	private List<ApplnDocTO> docList;
	private AdmAppln admBO;
	private String submitDate;
	
	public void clearData(){
		this.docList=null;
		this.admBO=null;
		this.submitDate=null;
	}
	
	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public List<ApplnDocTO> getDocList() {
		return docList;
	}

	public void setDocList(List<ApplnDocTO> docList) {
		this.docList = docList;
	}

	public AdmAppln getAdmBO() {
		return admBO;
	}

	public void setAdmBO(AdmAppln admBO) {
		this.admBO = admBO;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	
	
}
