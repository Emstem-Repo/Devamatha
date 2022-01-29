package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.List;

public class HostelPaymentSlipTO implements Serializable {

	private int hlApplicationFormId;
	private int hostelId;
	private String hostelName;
	private String requisitionNo;
	private String slipNo;
	private String feeTotalAmount;

	private List<HostelFeeDetailsTO> feeDetailsTO;

	public int getHlApplicationFormId() {
		return hlApplicationFormId;
	}

	public void setHlApplicationFormId(int hlApplicationFormId) {
		this.hlApplicationFormId = hlApplicationFormId;
	}

	public int getHostelId() {
		return hostelId;
	}

	public void setHostelId(int hostelId) {
		this.hostelId = hostelId;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getRequisitionNo() {
		return requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public String getFeeTotalAmount() {
		return feeTotalAmount;
	}

	public void setFeeTotalAmount(String feeTotalAmount) {
		this.feeTotalAmount = feeTotalAmount;
	}

	public List<HostelFeeDetailsTO> getFeeDetailsTO() {
		return feeDetailsTO;
	}

	public void setFeeDetailsTO(List<HostelFeeDetailsTO> feeDetailsTO) {
		this.feeDetailsTO = feeDetailsTO;
	}

}
