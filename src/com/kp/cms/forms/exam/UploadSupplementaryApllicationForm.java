package com.kp.cms.forms.exam;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

	public class UploadSupplementaryApllicationForm extends BaseActionForm{
		private int id;
		private FormFile thefile;
		private String method;
		private String downloadExcel;
		private String mode;
		private String downloadCSV;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public FormFile getThefile() {
			return thefile;
		}
		public void setThefile(FormFile thefile) {
			this.thefile = thefile;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getDownloadExcel() {
			return downloadExcel;
		}
		public void setDownloadExcel(String downloadExcel) {
			this.downloadExcel = downloadExcel;
		}
		
		public String getMode() {
			return mode;
		}
		public void setMode(String mode) {
			this.mode = mode;
		}
		public String getDownloadCSV() {
			return downloadCSV;
		}
		public void setDownloadCSV(String downloadCSV) {
			this.downloadCSV = downloadCSV;
		}
		public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);

			return actionErrors;
		}
		
		public void resetFields() {
			this.downloadExcel=null;
			this.method=null;
			this.thefile=null;
			this.id=0;
			this.downloadCSV=null;
			this.mode=null;
		}
}
