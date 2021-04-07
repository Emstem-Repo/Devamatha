package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;

public class OfflineIssueForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	
	private List<CourseTO> courseList;

	public List<CourseTO> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public void clearWhileReset(){
				super.clear();
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
