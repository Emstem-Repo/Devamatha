package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.ApplicationReportTO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

/**
 * 
 * @author kshirod.k
 * A formbean for Application Report
 *
 */
public class ApplicationReportForm extends BaseActionForm{
	
	private List<CourseTO> courseList;
	private List<ApplicationReportTO> applicationList;
	private String method;
	
	public List<ApplicationReportTO> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<ApplicationReportTO> applicationList) {
		this.applicationList = applicationList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<CourseTO> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}
