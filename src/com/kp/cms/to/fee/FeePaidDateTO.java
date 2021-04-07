package com.kp.cms.to.fee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kp.cms.to.pettycash.AccountHeadTO;

public class FeePaidDateTO implements Serializable{
	private String paidDate;
	private List<AccountHeadTO> accountList;
	private Map<Integer, AccountHeadTO> accountMap;
	public Map<Integer, AccountHeadTO> getAccountMap() {
		return accountMap;
	}
	public void setAccountMap(Map<Integer, AccountHeadTO> accountMap) {
		this.accountMap = accountMap;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public List<AccountHeadTO> getAccountList() {
		return accountList;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	public void setAccountList(List<AccountHeadTO> accountList) {
		this.accountList = accountList;
	}


}
