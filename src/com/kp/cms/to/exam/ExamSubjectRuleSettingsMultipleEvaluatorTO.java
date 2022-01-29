package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsMultipleEvaluatorTO implements Serializable{
	
	private Integer id;
	private String multipleEvaluatorChecked;
	private String noOfEvaluations;
	private String typeOfEvaluation;
	private String evalId;
	private List<KeyValueTO> evaluatorIdList;
	
	private String isTheoryPractical;
	public void setMultipleEvaluatorChecked(String multipleEvaluatorChecked) {
		this.multipleEvaluatorChecked = multipleEvaluatorChecked;
	}
	public String getMultipleEvaluatorChecked() {
		return multipleEvaluatorChecked;
	}
	public void setNoOfEvaluations(String noOfEvaluations) {
		this.noOfEvaluations = noOfEvaluations;
	}
	public String getNoOfEvaluations() {
		return noOfEvaluations;
	}
	public void setTypeOfEvaluation(String typeOfEvaluation) {
		this.typeOfEvaluation = typeOfEvaluation;
	}
	public String getTypeOfEvaluation() {
		return typeOfEvaluation;
	}
	
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setEvaluatorIdList(List<KeyValueTO> evaluatorIdList) {
		this.evaluatorIdList = evaluatorIdList;
	}
	public List<KeyValueTO> getEvaluatorIdList() {
		return evaluatorIdList;
	}
	public void setEvalId(String evalId) {
		this.evalId = evalId;
	}
	public String getEvalId() {
		return evalId;
	}

}
