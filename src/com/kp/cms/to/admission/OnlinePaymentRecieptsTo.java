package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.Date;

public class OnlinePaymentRecieptsTo implements Serializable, Comparable<OnlinePaymentRecieptsTo> {

	private int id;
	private String transactionDate;
	private int recieptNo;
	private String applicationFor;
	private String msg;
	private int count;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(int recieptNo) {
		this.recieptNo = recieptNo;
	}

	public String getApplicationFor() {
		return applicationFor;
	}

	public void setApplicationFor(String applicationFor) {
		this.applicationFor = applicationFor;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int compareTo(OnlinePaymentRecieptsTo temp) {
		/*if(temp.getRecieptNo()>0 && this.recieptNo>0){
			if(this.getRecieptNo()==0 && temp.recieptNo==0)
				return 0;
			else if(this.getRecieptNo()>this.recieptNo)
				return -1;
			else 
				return 1;
		}
		return -1;*/
		if(temp!=null && this!=null){
			if(temp.getRecieptNo() == this.getRecieptNo())
				return 0;
			else if(temp.getRecieptNo() > this.getRecieptNo())
				return -1;
			else
				return 1;
		}
		return 0;
	}

}
