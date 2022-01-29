package com.kp.cms.to.pettycash;


public class AccountHeadTO {
	
	private String id;
	private String accCode;
	private String accName;
	private String accNameWithCode;
	private String amount;
	private String isFixed;
	private String balanceAmount;
	private int accountId;
	private String paidAmount;
	private String totalAmount;
	private String installmentAmount;
	private int accid;
	private String total;
	private int sequence;
	private String bankAccNo;
	private String pcReceiptId;
	private String details;
	private String paidDate;
	private String challanNo;
	
	public String getPcReceiptId() {
		return pcReceiptId;
	}
	public void setPcReceiptId(String pcReceiptId) {
		this.pcReceiptId = pcReceiptId;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public int getAccid() {
		return accid;
	}
	public void setAccid(int accid) {
		this.accid = accid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccNameWithCode() {
		return accNameWithCode;
	}
	public void setAccNameWithCode(String accNameWithCode) {
		this.accNameWithCode = accNameWithCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIsFixed() {
		return isFixed;
	}
	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}
	

	public int getSequence() {
			return sequence;
		}
		public void setSequence(int sequence) {
			this.sequence = sequence;
		}


		public String getBankAccNo() {
			return bankAccNo;
		}
		public void setBankAccNo(String bankAccNo) {
			this.bankAccNo = bankAccNo;
		}
		public String getDetails() {
			return details;
		}
		public void setDetails(String details) {
			this.details = details;
		}
		public String getPaidDate() {
			return paidDate;
		}
		public void setPaidDate(String paidDate) {
			this.paidDate = paidDate;
		}
		public String getChallanNo() {
			return challanNo;
		}
		public void setChallanNo(String challanNo) {
			this.challanNo = challanNo;
		}


	

}
