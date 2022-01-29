package com.kp.cms.to.exam;

public class ExamSchemeTO {
	private String selected = "off";
	private String scheme;
	private int schemeNo;

	public ExamSchemeTO(String scheme, int schemeNo, String selected) {
		super();
		this.scheme = scheme;
		this.schemeNo = schemeNo;
		this.selected = selected;

	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getSelected() {
		return selected;
	}

}
