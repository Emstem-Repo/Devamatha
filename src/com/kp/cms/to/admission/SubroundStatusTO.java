package com.kp.cms.to.admission;

public class SubroundStatusTO {

	private boolean isSameNoOfSubround = false;
	private boolean haveSubround = false;

	public void setSameNoOfSubround(boolean isSameNoOfSubround) {
		this.isSameNoOfSubround = isSameNoOfSubround;
	}

	public boolean getIsSameNoOfSubround() {
		return isSameNoOfSubround;
	}

	public void setHaveSubround(boolean haveSubround) {
		this.haveSubround = haveSubround;
	}

	public boolean getIsHaveSubround() {
		return haveSubround;
	}

}
