package com.kp.cms.to.fee;

import java.util.List;

public class PrintChalanTO {
	private String printAccountName;
	private String nonAdditionalAmount;
	private String additionalAMount;
	private String feeDesc;
	private String totalAmount;
	private List<String> descList;
	private String bankInfo;
	private List<String> bankInfoList;
	private String verified;
	private String installmentAmt;
	private String concessionAmt;
	private String netAmount;
	private Boolean isInstallment;
	private Boolean isConcession;
	private String amountInWord;
	private byte[] logoBytes;
	private int count;
	private List<String> desc2List;
	private List<String> verifiedList;
	private List<FeePaymentDetailFeeGroupTO> additionalList;
	private int addlCount;
	private int accId;
	private Boolean isScholarShipAmt;
	private String scholarShipAmt;
	private String feeGroup;
	private Integer amountPaid;
	public String getPrintAccountName() {
		return printAccountName;
	}
	public void setPrintAccountName(String printAccountName) {
		this.printAccountName = printAccountName;
	}
	public String getNonAdditionalAmount() {
		return nonAdditionalAmount;
	}
	public void setNonAdditionalAmount(String nonAdditionalAmount) {
		this.nonAdditionalAmount = nonAdditionalAmount;
	}
	public String getAdditionalAMount() {
		return additionalAMount;
	}
	public void setAdditionalAMount(String additionalAMount) {
		this.additionalAMount = additionalAMount;
	}
	public String getFeeDesc() {
		return feeDesc;
	}
	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<String> getDescList() {
		return descList;
	}
	public void setDescList(List<String> descList) {
		this.descList = descList;
	}
	public String getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	public List<String> getBankInfoList() {
		return bankInfoList;
	}
	public void setBankInfoList(List<String> bankInfoList) {
		this.bankInfoList = bankInfoList;
	}
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}
	public String getInstallmentAmt() {
		return installmentAmt;
	}
	public void setInstallmentAmt(String installmentAmt) {
		this.installmentAmt = installmentAmt;
	}
	public String getConcessionAmt() {
		return concessionAmt;
	}
	public void setConcessionAmt(String concessionAmt) {
		this.concessionAmt = concessionAmt;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public Boolean getIsInstallment() {
		return isInstallment;
	}
	public void setIsInstallment(Boolean isInstallment) {
		this.isInstallment = isInstallment;
	}
	public Boolean getIsConcession() {
		return isConcession;
	}
	public void setIsConcession(Boolean isConcession) {
		this.isConcession = isConcession;
	}
	public String getAmountInWord() {
		return amountInWord;
	}
	public void setAmountInWord(String amountInWord) {
		this.amountInWord = amountInWord;
	}
	public byte[] getLogoBytes() {
		return logoBytes;
	}
	public void setLogoBytes(byte[] logoBytes) {
		this.logoBytes = logoBytes;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getDesc2List() {
		return desc2List;
	}
	public void setDesc2List(List<String> desc2List) {
		this.desc2List = desc2List;
	}
	public List<String> getVerifiedList() {
		return verifiedList;
	}
	public void setVerifiedList(List<String> verifiedList) {
		this.verifiedList = verifiedList;
	}
	public int getAddlCount() {
		return addlCount;
	}
	public void setAddlCount(int addlCount) {
		this.addlCount = addlCount;
	}
	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	public List<FeePaymentDetailFeeGroupTO> getAdditionalList() {
		return additionalList;
	}
	public void setAdditionalList(List<FeePaymentDetailFeeGroupTO> additionalList) {
		this.additionalList = additionalList;
	}
	public Boolean getIsScholarShipAmt() {
		return isScholarShipAmt;
	}
	public void setIsScholarShipAmt(Boolean isScholarShipAmt) {
		this.isScholarShipAmt = isScholarShipAmt;
	}
	public String getScholarShipAmt() {
		return scholarShipAmt;
	}
	public void setScholarShipAmt(String scholarShipAmt) {
		this.scholarShipAmt = scholarShipAmt;
	}
	public Integer getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Integer amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getFeeGroup() {
		return feeGroup;
	}
	public void setFeeGroup(String feeGroup) {
		this.feeGroup = feeGroup;
	}
}
