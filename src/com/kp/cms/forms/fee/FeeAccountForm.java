package com.kp.cms.forms.fee;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeAccountTO;

public class FeeAccountForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String  name;
	private List <FeeAccountTO> feeAccountList;
	private String editedcode;
	private String code;
	private String originalcode;
	private String bankInfo;
	private FormFile formFile;
    private String position;
    private String printAccName;
    private byte[] logo;
    private String fileName;
    private String contentType;
	
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
	
	public List<FeeAccountTO> getFeeAccountList() {
		return feeAccountList;
	}
	public void setFeeAccountList(List<FeeAccountTO> feeAccountList) {
		this.feeAccountList = feeAccountList;
	}
	
	public String getEditedcode() {
		return editedcode;
	}
	public void setEditedcode(String editedcode) {
		this.editedcode = editedcode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOriginalcode() {
		return originalcode;
	}
	public void setOriginalcode(String originalcode) {
		this.originalcode = originalcode;
	}
	public String getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	
	public FormFile getFormFile() {
		return formFile;
	}
	public void setFormFile(FormFile formFile) {
		this.formFile = formFile;
	}
	public String getPrintAccName() {
		return printAccName;
	}
	public void setPrintAccName(String printAccName) {
		this.printAccName = printAccName;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the logo
	 */
	public byte[] getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		name = null;
		code=null;
		formFile = null;
		printAccName = null;
		bankInfo = null;
		position=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
