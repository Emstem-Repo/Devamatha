package com.kp.cms.to.pettycash;

import java.io.Serializable;

public class CollectionAmntTO implements Serializable,Comparable<CollectionAmntTO> {
	private String amount;
	private int sequence;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	@Override
	public int compareTo(CollectionAmntTO arg0) {
		if(arg0!=null && this!=null){
			if(this.getSequence()> arg0.getSequence()){
				return 1;
			}else if(this.getSequence() < arg0.getSequence()){
				return -1;
			}else
				return 0;
		}
		return 0;
	}
	
}
