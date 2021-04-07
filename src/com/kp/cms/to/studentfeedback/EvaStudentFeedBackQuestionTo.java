package com.kp.cms.to.studentfeedback;

import java.util.LinkedList;
import java.util.List;
public class EvaStudentFeedBackQuestionTo
{

    private int id;
    private String question;
    private String order;
    private String groupId;
    private String answer;
    private String checked;
	private double totalScoreForEachQue;	
	private int totalStudentsForEachQue;
    private List<FacultyEvaluationReportAnswersTo> answers;
   
   public EvaStudentFeedBackQuestionTo()
    {
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	public List<FacultyEvaluationReportAnswersTo> getAnswers() {
		return answers;
	}
	public void setAnswers(LinkedList<FacultyEvaluationReportAnswersTo> ansList) {
		this.answers = ansList;
	}
	public double getTotalScoreForEachQue() {
		return totalScoreForEachQue;
	}

	public void setTotalScoreForEachQue(double totalScoreForEachQue) {
		this.totalScoreForEachQue = totalScoreForEachQue;
	}

	public int getTotalStudentsForEachQue() {
		return totalStudentsForEachQue;
	}

	public void setTotalStudentsForEachQue(int totalStudentsForEachQue) {
		this.totalStudentsForEachQue = totalStudentsForEachQue;
	}	
}
