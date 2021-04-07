package com.kp.cms.to.fee;

public class FeeVoucherTO {
	private int id;
	private String bookNo;
	private String type;
	private String startingNo;
	private String endingNo;
	private String financialYear;
	private int finId;
	private String startingPrefix;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public int getFinId() {
		return finId;
	}
	public void setFinId(int finId) {
		this.finId = finId;
	}
	public String getStartingPrefix() {
		return startingPrefix;
	}
	public void setStartingPrefix(String startingPrefix) {
		this.startingPrefix = startingPrefix;
	}
	
}
