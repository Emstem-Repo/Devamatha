package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.StudentBioDataTO;

public class UploadAddlBiodataInfoForm extends BaseActionForm{
	
	private int id;
	private FormFile thefile;
	private  List<StudentBioDataTO> stuBioDataTOList;
	private String method;
	private String downloadExcel;
	
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
	public List<StudentBioDataTO> getStuBioDataTOList() {
		return stuBioDataTOList;
	}
	public void setStuBioDataTOList(List<StudentBioDataTO> stuBioDataTOList) {
		this.stuBioDataTOList = stuBioDataTOList;
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
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.downloadExcel=null;
		this.method=null;
		this.stuBioDataTOList=null;
		this.thefile=null;
		this.id=0;
	}
}
