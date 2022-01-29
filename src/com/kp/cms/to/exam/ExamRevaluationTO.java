package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamRevaluationTO implements Serializable,Comparable<ExamRevaluationTO> {
	private int id;
	private String revaluationType;
	private String optionValue;
	
	

	public ExamRevaluationTO(int id, String revaluationType, String optionValue) {
		super();
		this.id = id;
		this.revaluationType = revaluationType;
		this.optionValue = optionValue;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getRevaluationType() {
		return revaluationType;
	}

	public void setRevaluationType(String revaluationType) {
		this.revaluationType = revaluationType;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	@Override
	public int compareTo(ExamRevaluationTO arg0) {
		if(arg0!=null && this!=null && arg0.getRevaluationType()!=null
				 && this.getRevaluationType()!=null){
			return this.getRevaluationType().compareTo(arg0.getRevaluationType());
		}else
		return 0;
	}

}
