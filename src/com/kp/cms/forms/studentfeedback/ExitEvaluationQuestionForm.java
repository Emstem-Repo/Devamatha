package com.kp.cms.forms.studentfeedback;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;

public class ExitEvaluationQuestionForm extends BaseActionForm {
	
	private List<ExitEvaluationTopicTo> exitEvalTopicList;
	private List<ExitEvaluationQuestionTo> exitEvalQuesToList;
	private String method;
	private String question;
	private Map<Integer, String> exitEvalTopicMap;
	private String exitEvalTopic;
	private int exitEvalQuesId;
	private String order;
	public List<ExitEvaluationTopicTo> getExitEvalTopicList() {
		return exitEvalTopicList;
	}
	public void setExitEvalTopicList(List<ExitEvaluationTopicTo> exitEvalTopicList) {
		this.exitEvalTopicList = exitEvalTopicList;
	}
	public List<ExitEvaluationQuestionTo> getExitEvalQuesToList() {
		return exitEvalQuesToList;
	}
	public void setExitEvalQuesToList(
			List<ExitEvaluationQuestionTo> exitEvalQuesToList) {
		this.exitEvalQuesToList = exitEvalQuesToList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Map<Integer, String> getExitEvalTopicMap() {
		return exitEvalTopicMap;
	}
	public void setExitEvalTopicMap(Map<Integer, String> exitEvalTopicMap) {
		this.exitEvalTopicMap = exitEvalTopicMap;
	}
	public String getExitEvalTopic() {
		return exitEvalTopic;
	}
	public void setExitEvalTopic(String exitEvalTopic) {
		this.exitEvalTopic = exitEvalTopic;
	}
	
	public int getExitEvalQuesId() {
		return exitEvalQuesId;
	}
	public void setExitEvalQuesId(int exitEvalQuesId) {
		this.exitEvalQuesId = exitEvalQuesId;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public void clearAll() {
		super.clear();
		this.question = null;
		this.exitEvalTopic = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

}
