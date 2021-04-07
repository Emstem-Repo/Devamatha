package com.kp.cms.forms.fee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class ConcessionSlipBooksForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String finacialYear;
	private String startingNo;
	private String endingNo;
	private String bookNo;
	private int id;
	private int duplId;
	private String origType;
	private String origYear;
	private String origBookNo;
	private String origStartNo;
	private String origEndSlipNo;
	private String startPrefix;
	private String endPrefix;
	private String origPrefix;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFinacialYear() {
		return finacialYear;
	}
	public void setFinacialYear(String finacialYear) {
		this.finacialYear = finacialYear;
	}
	public String getStartingNo() {
		return startingNo;
	}
	public void setStartingNo(String startingNo) {
		this.startingNo = startingNo;
	}
	public String getEndingNo() {
		return endingNo;
	}
	public void setEndingNo(String endingNo) {
		this.endingNo = endingNo;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigType() {
		return origType;
	}
	public void setOrigType(String origType) {
		this.origType = origType;
	}
	public String getOrigYear() {
		return origYear;
	}
	public void setOrigYear(String origYear) {
		this.origYear = origYear;
	}
	public String getOrigBookNo() {
		return origBookNo;
	}
	public void setOrigBookNo(String origBookNo) {
		this.origBookNo = origBookNo;
	}
	public String getOrigStartNo() {
		return origStartNo;
	}
	public void setOrigStartNo(String origStartNo) {
		this.origStartNo = origStartNo;
	}
	public String getOrigEndSlipNo() {
		return origEndSlipNo;
	}
	public void setOrigEndSlipNo(String origEndSlipNo) {
		this.origEndSlipNo = origEndSlipNo;
	}
	public String getStartPrefix() {
		return startPrefix;
	}
	public void setStartPrefix(String startPrefix) {
		this.startPrefix = startPrefix;
	}
	
	public String getEndPrefix() {
		return endPrefix;
	}
	public void setEndPrefix(String endPrefix) {
		this.endPrefix = endPrefix;
	}
	public String getOrigPrefix() {
		return origPrefix;
	}
	public void setOrigPrefix(String origPrefix) {
		this.origPrefix = origPrefix;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
