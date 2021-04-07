package com.kp.cms.to.admin;


public class IncomeTO {
	private int id;
	private CurrencyTO currencyTO;
	private String incomeRange;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public CurrencyTO getCurrencyTO() {
		return currencyTO;
	}
	public void setCurrencyTO(CurrencyTO currencyTO) {
		this.currencyTO = currencyTO;
	}
	public String getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(String incomeRange) {
		this.incomeRange = incomeRange;
	}
	
}
