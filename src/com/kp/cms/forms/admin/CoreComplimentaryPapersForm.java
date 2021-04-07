package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CoreComplimentaryPapersTo;

public class CoreComplimentaryPapersForm extends BaseActionForm
{
	private static final long serialVersionUID = 1L;
	private int corecompId;
	private String coreSubject;
	private String coreSubject2;
	private String coreSubject3;
	private String complementary1Subject;
	private String complementary2Subject;
	private String complementary3Subject;
	private String openSubject;
	private String orgcourseId;
	private String orgcoresubject;
	private String orgcomp1subject;
	private String orgcomp2subject;
	private int reactivateid;
	private List<CoreComplimentaryPapersTo> corecompList;
	private String orgcorecompId;
	private String courseId;
	
	public CoreComplimentaryPapersForm() {

	}
	public CoreComplimentaryPapersForm(int corecompId, String coreSubject,
			String complementary1Subject, String complementary2Subject,
			String orgcourseId, String orgcoresubject, String orgcomp1subject,
			String orgcomp2subject, int reactivateid,
			List<CoreComplimentaryPapersTo> corecompList, String orgcorecompId,
			String courseId) {
		super();
		this.corecompId = corecompId;
		this.coreSubject = coreSubject;
		this.complementary1Subject = complementary1Subject;
		this.complementary2Subject = complementary2Subject;
		this.orgcourseId = orgcourseId;
		this.orgcoresubject = orgcoresubject;
		this.orgcomp1subject = orgcomp1subject;
		this.orgcomp2subject = orgcomp2subject;
		this.reactivateid = reactivateid;
		this.corecompList = corecompList;
		this.orgcorecompId = orgcorecompId;
		this.courseId = courseId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public int getCorecompId() {
		return corecompId;
	}
	public void setCorecompId(int corecompId) {
		this.corecompId = corecompId;
	}
	public String getCoreSubject() {
		return coreSubject;
	}
	public void setCoreSubject(String coreSubject) {
		this.coreSubject = coreSubject;
	}
	public String getCoreSubject2() {
		return coreSubject2;
	}
	public void setCoreSubject2(String coreSubject2) {
		this.coreSubject2 = coreSubject2;
	}
	public String getCoreSubject3() {
		return coreSubject3;
	}
	public void setCoreSubject3(String coreSubject3) {
		this.coreSubject3 = coreSubject3;
	}
	public String getComplementary1Subject() {
		return complementary1Subject;
	}
	public void setComplementary1Subject(String complementary1Subject) {
		this.complementary1Subject = complementary1Subject;
	}
	public String getComplementary2Subject() {
		return complementary2Subject;
	}
	public void setComplementary2Subject(String complementary2Subject) {
		this.complementary2Subject = complementary2Subject;
	}
	public String getComplementary3Subject() {
		return complementary3Subject;
	}
	public void setComplementary3Subject(String complementary3Subject) {
		this.complementary3Subject = complementary3Subject;
	}
	public String getOpenSubject() {
		return openSubject;
	}
	public void setOpenSubject(String openSubject) {
		this.openSubject = openSubject;
	}
	public String getOrgcourseId() {
		return orgcourseId;
	}
	public void setOrgcourseId(String orgcourseId) {
		this.orgcourseId = orgcourseId;
	}
	public String getOrgcoresubject() {
		return orgcoresubject;
	}
	public void setOrgcoresubject(String orgcoresubject) {
		this.orgcoresubject = orgcoresubject;
	}
	public String getOrgcomp1subject() {
		return orgcomp1subject;
	}
	public void setOrgcomp1subject(String orgcomp1subject) {
		this.orgcomp1subject = orgcomp1subject;
	}
	public String getOrgcomp2subject() {
		return orgcomp2subject;
	}
	public void setOrgcomp2subject(String orgcomp2subject) {
		this.orgcomp2subject = orgcomp2subject;
	}
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}
	public List<CoreComplimentaryPapersTo> getCorecompList() {
		return corecompList;
	}
	public void setCorecompList(List<CoreComplimentaryPapersTo> corecompList) {
		this.corecompList = corecompList;
	}
	public String getOrgcorecompId() {
		return orgcorecompId;
	}
	public void setOrgcorecompId(String orgcorecompId) {
		this.orgcorecompId = orgcorecompId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.coreSubject = null;
		this.coreSubject2 = null;
		this.coreSubject3 = null;
		this.complementary1Subject=null;
		this.complementary2Subject=null;
		this.complementary3Subject=null;
		this.openSubject = null;
		this.courseId="";
	}
	
}
