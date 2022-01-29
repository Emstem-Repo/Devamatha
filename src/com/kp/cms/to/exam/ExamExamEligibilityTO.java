package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Jan 9, 2010 Created By 9Elements
 */
@SuppressWarnings("serial")
public class ExamExamEligibilityTO implements Serializable,Comparable<ExamExamEligibilityTO>{
	private Integer id;
	private String display;
	private Integer value;
	private boolean dummyAdditionalFee;
	private boolean dummyAdditionalDisable;
	private Integer detailId;

	public ExamExamEligibilityTO(Integer id, String display, int value) {
		super();
		this.id = id;
		this.display = display;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public ExamExamEligibilityTO() {
		super();
	}

	public void setDummyAdditionalFee(boolean dummyAdditionalFee) {
		this.dummyAdditionalFee = dummyAdditionalFee;
	}

	public boolean getDummyAdditionalFee() {
		return dummyAdditionalFee;
	}

	public void setDummyAdditionalDisable(boolean dummyAdditionalDisable) {
		this.dummyAdditionalDisable = dummyAdditionalDisable;
	}

	public boolean getDummyAdditionalDisable() {
		return dummyAdditionalDisable;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getDetailId() {
		return detailId;
	}
	@Override
	public int compareTo(ExamExamEligibilityTO arg0) {

		if(arg0!=null && this!=null && arg0.getDisplay()!=null
				 && this.getDisplay()!=null){
			return this.getDisplay().compareTo(arg0.getDisplay());
		}else
		return 0;
	
	}
}
