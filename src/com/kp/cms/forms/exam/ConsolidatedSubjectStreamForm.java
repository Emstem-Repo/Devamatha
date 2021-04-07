package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ConsolidatedSubjectStreamTO;

@SuppressWarnings("serial")
public class ConsolidatedSubjectStreamForm extends BaseActionForm
{
	private int id;
	private String streamName;
	private String origStreamName;
	private int dupId;
	private FormFile studentPhoto;
	private List<ConsolidatedSubjectStreamTO> subjectStreams;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset()
	{
		this.streamName = null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	public String getOrigStreamName() {
		return origStreamName;
	}
	public void setOrigStreamName(String origStreamName) {
		this.origStreamName = origStreamName;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public List<ConsolidatedSubjectStreamTO> getSubjectStreams() {
		return subjectStreams;
	}
	public void setSubjectStreams(List<ConsolidatedSubjectStreamTO> subjectStreams) {
		this.subjectStreams = subjectStreams;
	}

	public FormFile getStudentPhoto() {
		return studentPhoto;
	}

	public void setStudentPhoto(FormFile studentPhoto) {
		this.studentPhoto = studentPhoto;
	}
	
}
