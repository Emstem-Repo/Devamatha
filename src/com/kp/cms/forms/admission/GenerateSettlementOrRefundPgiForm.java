package com.kp.cms.forms.admission;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.GenerateSettlementTo;
import com.kp.cms.to.admission.GenerateRefundPgiTo;
import com.kp.cms.to.admission.GenerateSettlementOrRefundPgiTo;

public class GenerateSettlementOrRefundPgiForm  extends BaseActionForm{
	
	private String fromDate;
	private String toDate;
	private List<GenerateSettlementTo> generateSettlementTo;
	private String refundFileName;
	private String settlementFileName;
	private boolean openRefundLink;
	private boolean opensettementLink;
	private String formatName;
	private List<GenerateRefundPgiTo> generateRefundPgiTo;
	private boolean settlementDownloadFile;
	private boolean refundDownloadFile;
	
	public boolean isOpenRefundLink() {
		return openRefundLink;
	}
	public void setOpenRefundLink(boolean openRefundLink) {
		this.openRefundLink = openRefundLink;
	}
	public boolean isOpensettementLink() {
		return opensettementLink;
	}
	public void setOpensettementLink(boolean opensettementLink) {
		this.opensettementLink = opensettementLink;
	}
	public String getRefundFileName() {
		return refundFileName;
	}
	public void setRefundFileName(String refundFileName) {
		this.refundFileName = refundFileName;
	}
	public String getSettlementFileName() {
		return settlementFileName;
	}
	public void setSettlementFileName(String settlementFileName) {
		this.settlementFileName = settlementFileName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public void resetFields() {
		this.fromDate=null;
		this.toDate=null;
		this.generateSettlementTo=null;
		this.generateRefundPgiTo=null;
		this.settlementDownloadFile=false;
		this.refundDownloadFile=false;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public List<GenerateRefundPgiTo> getGenerateRefundPgiTo() {
		return generateRefundPgiTo;
	}
	public void setGenerateRefundPgiTo(List<GenerateRefundPgiTo> generateRefundPgiTo) {
		this.generateRefundPgiTo = generateRefundPgiTo;
	}
	public List<GenerateSettlementTo> getGenerateSettlementTo() {
		return generateSettlementTo;
	}
	public void setGenerateSettlementTo(
			List<GenerateSettlementTo> generateSettlementTo) {
		this.generateSettlementTo = generateSettlementTo;
	}
	public boolean isSettlementDownloadFile() {
		return settlementDownloadFile;
	}
	public void setSettlementDownloadFile(boolean settlementDownloadFile) {
		this.settlementDownloadFile = settlementDownloadFile;
	}
	public boolean isRefundDownloadFile() {
		return refundDownloadFile;
	}
	public void setRefundDownloadFile(boolean refundDownloadFile) {
		this.refundDownloadFile = refundDownloadFile;
	}
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	
	
	

}
