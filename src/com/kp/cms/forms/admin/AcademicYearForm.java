package com.kp.cms.forms.admin;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AcademicYearTO;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

@SuppressWarnings("serial")
public class AcademicYearForm extends BaseActionForm
{

    private int id;
    private String academicYear;
    private String academicYearSel;
    private String isCurrent;
    private String isActive;
    private String method;
    private List<AcademicYearTO> academicYearList;
    private String isCurrentForAdmission;
    
    public AcademicYearForm()
    {
        isCurrent = "false";
        isCurrentForAdmission = "false";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAcademicYear()
    {
        return academicYear;
    }

    public void setAcademicYear(String academicYear)
    {
        this.academicYear = academicYear;
    }

    public String getAcademicYearSel()
    {
        return academicYearSel;
    }

    public void setAcademicYearSel(String academicYearSel)
    {
        this.academicYearSel = academicYearSel;
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

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public List<AcademicYearTO> getAcademicYearList()
    {
        return academicYearList;
    }

    public void setAcademicYearList(List<AcademicYearTO> academicYearList)
    {
        this.academicYearList = academicYearList;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors errors = super.validate(mapping, request, formName);
        return errors;
    }

    public void clear()
    {
        academicYear = null;
        isActive = null;
    }

	public void setIsCurrentForAdmission(String isCurrentForAdmission) {
		this.isCurrentForAdmission = isCurrentForAdmission;
	}

	public String getIsCurrentForAdmission() {
		return isCurrentForAdmission;
	}
}
