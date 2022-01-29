package com.kp.cms.bo.studentfeedback;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;

// Referenced classes of package com.kp.cms.bo.studentfeedback:
//            EvaStudentFeedbackGroup

public class EvaHiddenSubjectTeacher
    implements Serializable
{

    private int id;
    private ClassSchemewise classId;
    private Users teacherId;
    private Subject subjectId;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;

    public EvaHiddenSubjectTeacher()
    {
    }

    public EvaHiddenSubjectTeacher(int id, ClassSchemewise classId,Users teacherId,Subject subjectId, String createdBy, Date createdDate, String modifiedBy, 
            Date lastModifiedDate, Boolean isActive)
    {
        this.id = id;
        this.classId = classId;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
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
    
	public ClassSchemewise getClassId() {
		return classId;
	}

	public void setClassId(ClassSchemewise classId) {
		this.classId = classId;
	}

	public Users getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Users teacherId) {
		this.teacherId = teacherId;
	}

	public Subject getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Subject subjectId) {
		this.subjectId = subjectId;
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
