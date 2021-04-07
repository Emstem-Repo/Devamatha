package com.kp.cms.bo.studentfeedback;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package com.kp.cms.bo.studentfeedback:
//            EvaStudentFeedbackGroup

public class EvaStudentFeedbackQuestion
    implements Serializable
{

    private int id;
    private String question;
    private Integer order;
    private EvaStudentFeedbackGroup groupId;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;

    public EvaStudentFeedbackQuestion()
    {
    }

    public EvaStudentFeedbackQuestion(int id, String question, Integer order, EvaStudentFeedbackGroup groupId, String createdBy, Date createdDate, String modifiedBy, 
            Date lastModifiedDate, Boolean isActive)
    {
        this.id = id;
        this.question = question;
        this.order = order;
        this.groupId = groupId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.isActive = isActive;
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
    public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public EvaStudentFeedbackGroup getGroupId()
    {
        return groupId;
    }

    public void setGroupId(EvaStudentFeedbackGroup groupId)
    {
        this.groupId = groupId;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(Boolean isActive)
    {
        this.isActive = isActive;
    }
}
