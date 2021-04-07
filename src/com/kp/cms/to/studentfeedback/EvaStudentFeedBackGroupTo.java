package com.kp.cms.to.studentfeedback;

import java.util.List;


public class EvaStudentFeedBackGroupTo
{

    private int id;
    private String name;
    private String disOrder;
    List<EvaStudentFeedBackQuestionTo> questionList;
    private int tempTotalQuestions;
    public EvaStudentFeedBackGroupTo()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDisOrder()
    {
        return disOrder;
    }

    public void setDisOrder(String disOrder)
    {
        this.disOrder = disOrder;
    }

	public List<EvaStudentFeedBackQuestionTo> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<EvaStudentFeedBackQuestionTo> questionList) {
		this.questionList = questionList;
	}

	public int getTempTotalQuestions() {
		return tempTotalQuestions;
	}

	public void setTempTotalQuestions(int tempTotalQuestions) {
		this.tempTotalQuestions = tempTotalQuestions;
	}
}
