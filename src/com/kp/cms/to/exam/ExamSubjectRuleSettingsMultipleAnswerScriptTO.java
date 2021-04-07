package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsMultipleAnswerScriptTO implements Serializable,Comparable<ExamSubjectRuleSettingsMultipleAnswerScriptTO>{
	
	private String id;
	private String name;
	private String multipleAnswerScriptTheoryESE;
	private String isTheoryPractical;
	private Integer multipleAnswerScriptId;
	
	public ExamSubjectRuleSettingsMultipleAnswerScriptTO() {
		super();
	}
	public ExamSubjectRuleSettingsMultipleAnswerScriptTO(String id,
			String multipleAnswerScriptTheoryESE, String name,String isTheoryPractical,Integer multipleAnswerScriptId) {
		super();
		this.id = id;
		this.multipleAnswerScriptTheoryESE = multipleAnswerScriptTheoryESE;
		this.name = name;
		this.isTheoryPractical = isTheoryPractical;
		this.multipleAnswerScriptId = multipleAnswerScriptId;
	}
	public ExamSubjectRuleSettingsMultipleAnswerScriptTO(Integer multipleAnswerScriptId, String name) {
		super();
		this.multipleAnswerScriptId = multipleAnswerScriptId;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMultipleAnswerScriptTheoryESE() {
		return multipleAnswerScriptTheoryESE;
	}
	public void setMultipleAnswerScriptTheoryESE(
			String multipleAnswerScriptTheoryESE) {
		this.multipleAnswerScriptTheoryESE = multipleAnswerScriptTheoryESE;
	}
	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}
	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}
	public void setMultipleAnswerScriptId(Integer multipleAnswerScriptId) {
		this.multipleAnswerScriptId = multipleAnswerScriptId;
	}
	public Integer getMultipleAnswerScriptId() {
		return multipleAnswerScriptId;
	}
	@Override
	public int compareTo(ExamSubjectRuleSettingsMultipleAnswerScriptTO arg0) {
		if(arg0!=null && this!=null && arg0.getMultipleAnswerScriptTheoryESE()!=null
				 && this.getMultipleAnswerScriptTheoryESE()!=null){
			return this.getMultipleAnswerScriptTheoryESE().compareTo(arg0.getMultipleAnswerScriptTheoryESE());
		}		
		else
		return 0;
	}
	
	

}
