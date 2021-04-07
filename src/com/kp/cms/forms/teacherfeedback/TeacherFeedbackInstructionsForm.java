package com.kp.cms.forms.teacherfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.teacherfeedback.TeacherFeedbackInstructionsTO;

public class TeacherFeedbackInstructionsForm extends BaseActionForm {
	 private int id;
	 private String description;
	 List<TeacherFeedbackInstructionsTO> teacherFeedbackInsToList;
	 private int teacherFeedbackInsId;
	 public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TeacherFeedbackInstructionsTO> getTeacherFeedbackInsToList() {
		return teacherFeedbackInsToList;
	}

	public void setTeacherFeedbackInsToList(
			List<TeacherFeedbackInstructionsTO> teacherFeedbackInsToList) {
		this.teacherFeedbackInsToList = teacherFeedbackInsToList;
	}

	public int getteacherFeedbackInsId() {
		return teacherFeedbackInsId;
	}

	public void setteacherFeedbackInsId(int teacherFeedbackInsId) {
		this.teacherFeedbackInsId = teacherFeedbackInsId;
	}

	public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter(CMSConstants.FORMNAME);
			ActionErrors actionErrors = super.validate(mapping, request, formName);

			return actionErrors;
		}
		
		@Override
		public void reset(ActionMapping mapping, HttpServletRequest request) {
			super.reset(mapping, request);
			this.description = null;
		}
}
