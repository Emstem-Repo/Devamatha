package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsTheoryESETO implements Serializable
{	
	private String regularTheoryESEChecked;
	private String multipleAnswerScriptsChecked;
	private String multipleEvaluatorChecked;
	private String evaluatorID;
	private String selectTypeOfEvaluation;
	private String noOfEvaluations;
	private String multipleAnswerScriptTheoryESE;
	private String minimumMarksTheoryESE;
	private String maximumEntryMarksTheoryESE;
	private String maximumMarksTheoryESE;
	private String maximumTheoryFinalMarksTheoryESE;
	private String minimumTheoryFinalMarksTheoryESE;
	private List<Integer> multEvalBOIdList;
	private List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList;
	private List<ExamSubjectRuleSettingsMultipleEvaluatorTO> multipleEvaluatorList;
	private int id;
	private boolean isRegularTheoryESEChecked;
	private boolean isMultipleAnswerScriptsChecked;
	private boolean isMultipleEvaluatorChecked;
	
	private String typeOfEvaluation;
	private List<KeyValueTO> evaluatorIdList;
	private String isTheoryPRactical;
	private String evalId1;
	private String evalId2;
	private String evalId3;
	private String evalId4;
	private String evalId5;
	private String reviewer;
	private String id1;
	private String id2;
	private String id3;
	private String id4;
	private String id5;
	private String dupregularTheoryESEChecked;
	private String dupmultipleAnswerScriptsChecked;
	private String dupmultipleEvaluatorChecked;
	private String supplementaryMinMarks;
	private String supplementaryMaxMarks;
	private String supplementaryChecked;
	private String dupSupplementaryChecked;
	
	
	public ExamSubjectRuleSettingsTheoryESETO() {
		super();
	}
	public ExamSubjectRuleSettingsTheoryESETO(
			String maximumEntryMarksTheoryESE, String maximumMarksTheoryESE,
			String maximumTheoryFinalMarksTheoryESE,
			String minimumMarksTheoryESE,
			String minimumTheoryFinalMarksTheoryESE,
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList,
			String multipleAnswerScriptTheoryESE,
			String multipleAnswerScriptsChecked,
			String multipleEvaluatorChecked, String noOfEvaluations,
			String regularTheoryESEChecked, String selectTypeOfEvaluation) {
		super();
		this.maximumEntryMarksTheoryESE = maximumEntryMarksTheoryESE;
		this.maximumMarksTheoryESE = maximumMarksTheoryESE;
		this.maximumTheoryFinalMarksTheoryESE = maximumTheoryFinalMarksTheoryESE;
		this.minimumMarksTheoryESE = minimumMarksTheoryESE;
		this.minimumTheoryFinalMarksTheoryESE = minimumTheoryFinalMarksTheoryESE;
		this.multipleAnswerScriptList = multipleAnswerScriptList;
		this.multipleAnswerScriptTheoryESE = multipleAnswerScriptTheoryESE;
		this.multipleAnswerScriptsChecked = multipleAnswerScriptsChecked;
		this.multipleEvaluatorChecked = multipleEvaluatorChecked;
		this.noOfEvaluations = noOfEvaluations;
		this.regularTheoryESEChecked = regularTheoryESEChecked;
		this.selectTypeOfEvaluation = selectTypeOfEvaluation;
	
	}
	public String getRegularTheoryESEChecked() {
		return regularTheoryESEChecked;
	}
	public void setRegularTheoryESEChecked(String regularTheoryESEChecked) {
		this.regularTheoryESEChecked = regularTheoryESEChecked;
	}
	public String getMultipleAnswerScriptsChecked() {
		return multipleAnswerScriptsChecked;
	}
	public void setMultipleAnswerScriptsChecked(String multipleAnswerScriptsChecked) {
		this.multipleAnswerScriptsChecked = multipleAnswerScriptsChecked;
	}
	public String getMultipleEvaluatorChecked() {
		return multipleEvaluatorChecked;
	}
	public void setMultipleEvaluatorChecked(String multipleEvaluatorChecked) {
		this.multipleEvaluatorChecked = multipleEvaluatorChecked;
	}
	public String getEvaluatorID() {
		return evaluatorID;
	}
	public void setEvaluatorID(String evaluatorID) {
		this.evaluatorID = evaluatorID;
	}
	public String getSelectTypeOfEvaluation() {
		return selectTypeOfEvaluation;
	}
	public void setSelectTypeOfEvaluation(String selectTypeOfEvaluation) {
		this.selectTypeOfEvaluation = selectTypeOfEvaluation;
	}
	
	public String getMultipleAnswerScriptTheoryESE() {
		return multipleAnswerScriptTheoryESE;
	}
	public void setMultipleAnswerScriptTheoryESE(
			String multipleAnswerScriptTheoryESE) {
		this.multipleAnswerScriptTheoryESE = multipleAnswerScriptTheoryESE;
	}
	public String getMinimumMarksTheoryESE() {
		return minimumMarksTheoryESE;
	}
	public void setMinimumMarksTheoryESE(String minimumMarksTheoryESE) {
		this.minimumMarksTheoryESE = minimumMarksTheoryESE;
	}
	public String getMaximumEntryMarksTheoryESE() {
		return maximumEntryMarksTheoryESE;
	}
	public void setMaximumEntryMarksTheoryESE(String maximumEntryMarksTheoryESE) {
		this.maximumEntryMarksTheoryESE = maximumEntryMarksTheoryESE;
	}
	public String getMaximumMarksTheoryESE() {
		return maximumMarksTheoryESE;
	}
	public void setMaximumMarksTheoryESE(String maximumMarksTheoryESE) {
		this.maximumMarksTheoryESE = maximumMarksTheoryESE;
	}
	public String getMaximumTheoryFinalMarksTheoryESE() {
		return maximumTheoryFinalMarksTheoryESE;
	}
	public void setMaximumTheoryFinalMarksTheoryESE(
			String maximumTheoryFinalMarksTheoryESE) {
		this.maximumTheoryFinalMarksTheoryESE = maximumTheoryFinalMarksTheoryESE;
	}
	public String getMinimumTheoryFinalMarksTheoryESE() {
		return minimumTheoryFinalMarksTheoryESE;
	}
	public void setMinimumTheoryFinalMarksTheoryESE(
			String minimumTheoryFinalMarksTheoryESE) {
		this.minimumTheoryFinalMarksTheoryESE = minimumTheoryFinalMarksTheoryESE;
	}
	public void setNoOfEvaluations(String noOfEvaluations) {
		this.noOfEvaluations = noOfEvaluations;
	}
	public String getNoOfEvaluations() {
		return noOfEvaluations;
	}
	public void setMultipleAnswerScriptList(List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList) {
		this.multipleAnswerScriptList = multipleAnswerScriptList;
	}
	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> getMultipleAnswerScriptList() {
		return multipleAnswerScriptList;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeOfEvaluation() {
		return typeOfEvaluation;
	}
	public void setTypeOfEvaluation(String typeOfEvaluation) {
		this.typeOfEvaluation = typeOfEvaluation;
	}
	public List<KeyValueTO> getEvaluatorIdList() {
		return evaluatorIdList;
	}
	public void setEvaluatorIdList(List<KeyValueTO> evaluatorIdList) {
		this.evaluatorIdList = evaluatorIdList;
	}
	
	public void setIsTheoryPRactical(String isTheoryPRactical) {
		this.isTheoryPRactical = isTheoryPRactical;
	}
	public String getIsTheoryPRactical() {
		return isTheoryPRactical;
	}
	public void setMultEvalBOIdList(List<Integer> multEvalBOIdList) {
		this.multEvalBOIdList = multEvalBOIdList;
	}
	public List<Integer> getMultEvalBOIdList() {
		return multEvalBOIdList;
	}
	public String getEvalId1() {
		return evalId1;
	}
	public void setEvalId1(String evalId1) {
		this.evalId1 = evalId1;
	}
	public String getEvalId2() {
		return evalId2;
	}
	public void setEvalId2(String evalId2) {
		this.evalId2 = evalId2;
	}
	public String getEvalId3() {
		return evalId3;
	}
	public void setEvalId3(String evalId3) {
		this.evalId3 = evalId3;
	}
	public String getEvalId4() {
		return evalId4;
	}
	public void setEvalId4(String evalId4) {
		this.evalId4 = evalId4;
	}
	public String getEvalId5() {
		return evalId5;
	}
	public void setEvalId5(String evalId5) {
		this.evalId5 = evalId5;
	}
	
	public void setIsRegularTheoryESEChecked(boolean isRegularTheoryESEChecked) {
		this.isRegularTheoryESEChecked = isRegularTheoryESEChecked;
	}
	public boolean getIsRegularTheoryESEChecked() {
		return isRegularTheoryESEChecked;
	}
	public void setIsMultipleAnswerScriptsChecked(
			boolean isMultipleAnswerScriptsChecked) {
		this.isMultipleAnswerScriptsChecked = isMultipleAnswerScriptsChecked;
	}
	public boolean getIsMultipleAnswerScriptsChecked() {
		return isMultipleAnswerScriptsChecked;
	}
	public void setIsMultipleEvaluatorChecked(boolean isMultipleEvaluatorChecked) {
		this.isMultipleEvaluatorChecked = isMultipleEvaluatorChecked;
	}
	public boolean getIsMultipleEvaluatorChecked() {
		return isMultipleEvaluatorChecked;
	}
	public void setMultipleEvaluatorList(List<ExamSubjectRuleSettingsMultipleEvaluatorTO> multipleEvaluatorList) {
		this.multipleEvaluatorList = multipleEvaluatorList;
	}
	public List<ExamSubjectRuleSettingsMultipleEvaluatorTO> getMultipleEvaluatorList() {
		return multipleEvaluatorList;
	}
	public String getId1() {
		return id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}
	public String getId2() {
		return id2;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	public String getId3() {
		return id3;
	}
	public void setId3(String id3) {
		this.id3 = id3;
	}
	public String getId4() {
		return id4;
	}
	public void setId4(String id4) {
		this.id4 = id4;
	}
	public String getId5() {
		return id5;
	}
	public void setId5(String id5) {
		this.id5 = id5;
	}
	public String getDupregularTheoryESEChecked() {
		return dupregularTheoryESEChecked;
	}
	public void setDupregularTheoryESEChecked(String dupregularTheoryESEChecked) {
		this.dupregularTheoryESEChecked = dupregularTheoryESEChecked;
	}
	public String getDupmultipleAnswerScriptsChecked() {
		return dupmultipleAnswerScriptsChecked;
	}
	public void setDupmultipleAnswerScriptsChecked(
			String dupmultipleAnswerScriptsChecked) {
		this.dupmultipleAnswerScriptsChecked = dupmultipleAnswerScriptsChecked;
	}
	public String getDupmultipleEvaluatorChecked() {
		return dupmultipleEvaluatorChecked;
	}
	public void setDupmultipleEvaluatorChecked(String dupmultipleEvaluatorChecked) {
		this.dupmultipleEvaluatorChecked = dupmultipleEvaluatorChecked;
	}
	public void setRegularTheoryESEChecked(boolean isRegularTheoryESEChecked) {
		this.isRegularTheoryESEChecked = isRegularTheoryESEChecked;
	}
	public void setMultipleAnswerScriptsChecked(
			boolean isMultipleAnswerScriptsChecked) {
		this.isMultipleAnswerScriptsChecked = isMultipleAnswerScriptsChecked;
	}
	public void setMultipleEvaluatorChecked(boolean isMultipleEvaluatorChecked) {
		this.isMultipleEvaluatorChecked = isMultipleEvaluatorChecked;
	}
	public String getSupplementaryMinMarks() {
		return supplementaryMinMarks;
	}
	public void setSupplementaryMinMarks(String supplementaryMinMarks) {
		this.supplementaryMinMarks = supplementaryMinMarks;
	}
	public String getSupplementaryMaxMarks() {
		return supplementaryMaxMarks;
	}
	public void setSupplementaryMaxMarks(String supplementaryMaxMarks) {
		this.supplementaryMaxMarks = supplementaryMaxMarks;
	}
	public String getSupplementaryChecked() {
		return supplementaryChecked;
	}
	public void setSupplementaryChecked(String supplementaryChecked) {
		this.supplementaryChecked = supplementaryChecked;
	}
	public String getDupSupplementaryChecked() {
		return dupSupplementaryChecked;
	}
	public void setDupSupplementaryChecked(String dupSupplementaryChecked) {
		this.dupSupplementaryChecked = dupSupplementaryChecked;
	}
	
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}	
	
	

}
