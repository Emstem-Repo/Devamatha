package com.kp.cms.to.exam;

import java.io.Serializable;

public class DisplayValueTO implements Serializable,Comparable<DisplayValueTO> {

	private String display;
	private String value;

	public DisplayValueTO(String display, String value) {
		super();
		this.display = display;
		this.value = value;
	}

	public DisplayValueTO() {
		super();
	}

	public String getDisplay() {
		return display;
	}

	public String getValue() {
		return value;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(DisplayValueTO arg0) {
		if(arg0!=null && this!=null && arg0.getDisplay()!=null
				 && this.getDisplay()!=null){
			return this.getDisplay().compareTo(arg0.getDisplay());
		}else
		return 0;
	}

}
