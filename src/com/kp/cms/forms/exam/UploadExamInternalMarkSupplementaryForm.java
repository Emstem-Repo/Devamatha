package com.kp.cms.forms.exam;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

	public class UploadExamInternalMarkSupplementaryForm extends BaseActionForm{
		
		private FormFile thefile;
		private String method;
		
		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public FormFile getThefile() {
			return thefile;
		}

		public void setThefile(FormFile thefile) {
			this.thefile = thefile;
		}

		public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			
			return actionErrors;
		}
		
		public void resetFields(){
			this.thefile=null;
		}
		
}
