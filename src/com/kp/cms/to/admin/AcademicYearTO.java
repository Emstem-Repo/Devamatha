package com.kp.cms.to.admin;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AcademicYearTO
    implements Serializable
{

    private int id;
    private String year;
    private String academicYearSel;
    private String isCurrent;
    private String isActive;
    private String createdBy;
    private String modifiedBy;
    private String createdDate;
    private String lastModifiedDate;
    private String isCurrentForAdmission;
    public AcademicYearTO()
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

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getIsCurrent()
    {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent)
    {
        this.isCurrent = isCurrent;
    }

    public String getIsActive()
    {
        return isActive;
    }

    public void setIsActive(String isActive)
    {
        this.isActive = isActive;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setAcademicYearSel(String academicYearSel)
    {
        this.academicYearSel = academicYearSel;
    }

    public String getAcademicYearSel()
    {
        return academicYearSel;
    }

	public void setIsCurrentForAdmission(String isCurrentForAdmission) {
		this.isCurrentForAdmission = isCurrentForAdmission;
	}

	public String getIsCurrentForAdmission() {
		return isCurrentForAdmission;
	}
}
