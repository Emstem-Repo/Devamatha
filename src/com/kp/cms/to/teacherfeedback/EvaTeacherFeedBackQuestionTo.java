package com.kp.cms.to.teacherfeedback;

import java.util.Comparator;

import com.kp.cms.to.admin.ApplnDocTO;

public class EvaTeacherFeedBackQuestionTo implements Comparable<EvaTeacherFeedBackQuestionTo>{

    private int id;
    private String question;
    private String order;
    private String groupId;
    private String answer;
    private String checked;
   
   public EvaTeacherFeedBackQuestionTo()
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
	
	@Override
	public int compareTo(EvaTeacherFeedBackQuestionTo to) {
		if(to != null && this != null && to.getOrder() != null && this.order != null){
			return this.order.compareTo(to.getOrder());
		}else{
			return 0;
		}
	}
}
