package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.AdmSubjectForRankTo;

public class AdmSubjectForRankForm extends BaseActionForm {
	
	private int id;
	private String subjectname;
	private String stream;
	private String groupname;
    private boolean isactive;
    private String origsubjectname;
    private String origstream;
	private String origgroupname;
    public String getOrigstream() {
		return origstream;
	}
	public void setOrigstream(String origstream) {
		this.origstream = origstream;
	}
	public String getOriggroupname() {
		return origgroupname;
	}
	public void setOriggroupname(String origgroupname) {
		this.origgroupname = origgroupname;
	}
	private List<AdmSubjectForRankTo> subjecttolist;
    
	public String getOrigsubjectname() {
		return origsubjectname;
	}
	public void setOrigsubjectname(String origsubjectname) {
		this.origsubjectname = origsubjectname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		this.subjectname = null;
		this.stream = null;
		this.groupname = null;
		super.reset(mapping, request);
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		return super.validate(mapping, request, formName);
	}
	public void setSubjecttolist(List<AdmSubjectForRankTo> subjecttolist) {
		this.subjecttolist = subjecttolist;
	}
	public List<AdmSubjectForRankTo> getSubjecttolist() {
		return subjecttolist;
	} 
	
	

}
