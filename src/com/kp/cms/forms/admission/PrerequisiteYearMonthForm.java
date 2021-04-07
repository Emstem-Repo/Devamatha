package com.kp.cms.forms.admission;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.PrerequisiteYearMonthTO;

	@SuppressWarnings("serial")
	public class PrerequisiteYearMonthForm extends BaseActionForm
	{

	    private int id;
	    private String year;
	    private String month;
	    private String isActive;
	    private String method;
	    private String yearSel;
	    private List<PrerequisiteYearMonthTO> yearList;
	   
	    
	    public PrerequisiteYearMonthForm()
	    {}

	    public int getId()
	    {
	        return id;
	    }

	    public void setId(int id)
	    {
	        this.id = id;
	    }

	  
	    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	    {
	        String formName = request.getParameter("formName");
	        ActionErrors errors = super.validate(mapping, request, formName);
	        return errors;
	    }

	    public void clear()
	    {
	       year = null;
	       month=null;
	        isActive = null;
	    }

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public List<PrerequisiteYearMonthTO> getYearList() {
			return yearList;
		}

		public void setYearList(List<PrerequisiteYearMonthTO> yearList) {
			this.yearList = yearList;
		}

		public String getYearSel() {
			return yearSel;
		}

		public void setYearSel(String yearSel) {
			this.yearSel = yearSel;
		}

		

		
	}

	
	
	

