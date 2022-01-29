package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.DocTypeTO;

public class DocumentExamEntryForm extends BaseActionForm {
	private String examName;
	private String docTypeId;
	private String oldExamName;
	private String olddocTypeId;
	private String docTypeExamId;
	private List<DocTypeTO> docList;
	private List<DocTypeExamsTO> docTypeExamsList;
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}
	public List<DocTypeTO> getDocList() {
		return docList;
	}
	public void setDocList(List<DocTypeTO> docList) {
		this.docList = docList;
	}
	public List<DocTypeExamsTO> getDocTypeExamsList() {
		return docTypeExamsList;
	}
	public void setDocTypeExamsList(List<DocTypeExamsTO> docTypeExamsList) {
		this.docTypeExamsList = docTypeExamsList;
	}
	public String getOldExamName() {
		return oldExamName;
	}
	public void setOldExamName(String oldExamName) {
		this.oldExamName = oldExamName;
	}
	public String getOlddocTypeId() {
		return olddocTypeId;
	}
	public void setOlddocTypeId(String olddocTypeId) {
		this.olddocTypeId = olddocTypeId;
	}
	public String getDocTypeExamId() {
		return docTypeExamId;
	}
	public void setDocTypeExamId(String docTypeExamId) {
		this.docTypeExamId = docTypeExamId;
	}
	/* to reset the field in form
	 * (non-Javadoc)
	 * @see com.kp.cms.forms.BaseActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset() {
		this.examName=null;
		this.docTypeId=null;
	}


	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
