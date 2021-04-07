package com.kp.cms.forms.hostel;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.VisitorInfoTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class VisitorInfoForm extends BaseActionForm
{

    private String visitorFor;
    private String registerNo;
    private String staffId;
    private String name;
    private List<VisitorInfoTO> list;
    private VisitorInfoTO vto;
    private String firstName;
    private String lastName;
    private String visitorId;
    private String dateIn;
    private String dateOut;
    private String relationShip;
    private FormFile photoFile;
    private String hostelId;

    public String getVisitorFor()
    {
        return visitorFor;
    }

    public void setVisitorFor(String visitorFor)
    {
        this.visitorFor = visitorFor;
    }

    public String getRegisterNo()
    {
        return registerNo;
    }

    public void setRegisterNo(String registerNo)
    {
        this.registerNo = registerNo;
    }

    public String getStaffId()
    {
        return staffId;
    }

    public void setStaffId(String staffId)
    {
        this.staffId = staffId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public List<VisitorInfoTO> getList() {
		return list;
	}

	public void setList(List<VisitorInfoTO> list) {
		this.list = list;
	}

	public VisitorInfoTO getVto()
    {
        return vto;
    }

    public void setVto(VisitorInfoTO vto)
    {
        this.vto = vto;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getVisitorId()
    {
        return visitorId;
    }

    public void setVisitorId(String visitorId)
    {
        this.visitorId = visitorId;
    }

    public String getDateIn()
    {
        return dateIn;
    }

    public void setDateIn(String dateIn)
    {
        this.dateIn = dateIn;
    }

    public String getDateOut()
    {
        return dateOut;
    }

    public void setDateOut(String dateOut)
    {
        this.dateOut = dateOut;
    }

    public String getRelationShip()
    {
        return relationShip;
    }

    public void setRelationShip(String relationShip)
    {
        this.relationShip = relationShip;
    }

    public FormFile getPhotoFile()
    {
        return photoFile;
    }

    public void setPhotoFile(FormFile photoFile)
    {
        this.photoFile = photoFile;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }

    public void resetFields()
    {
        name = null;
        staffId = null;
        visitorFor = null;
        registerNo = null;
        vto = null;
        list = null;
        hostelId=null;
    }

    public void clear()
    {
        firstName = null;
        lastName = null;
        relationShip = null;
        visitorId = null;
        dateIn = null;
        dateOut = null;
    }

	public String getHostelId() {
		return hostelId;
	}

	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
    
    
}
