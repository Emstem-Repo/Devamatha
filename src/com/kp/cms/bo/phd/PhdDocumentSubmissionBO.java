package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;

public class PhdDocumentSubmissionBO implements Serializable {
    
	private int id;
	private Student studentId;
	private PhdEmployee guideId;
	private PhdEmployee coGuideId;
	private Date signedOn;
	private DisciplineBo disciplineId;
	private Date vivaVoceDate;
	private String title;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Employee internalGuide;
	private Employee internalCoGuide;
	
	private Set<PhdResearchDescription> researchDescription=new HashSet<PhdResearchDescription>();
	private Set<PhdConference> conference=new HashSet<PhdConference>();
	private Set<PhdStudentPanelMember> synopsisVivaPanel=new HashSet<PhdStudentPanelMember>();

	public PhdDocumentSubmissionBO() {
	}

	public PhdDocumentSubmissionBO(int id,Student studentId,PhdEmployee guideId,PhdEmployee coGuideId,Set<PhdConference> conference,
			Date signedOn,Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate,Set<PhdStudentPanelMember> synopsisVivaPanel,
			Set<PhdResearchDescription> researchDescription,DisciplineBo disciplineId,Date vivaVoceDate,String title,Employee internalGuide,Employee internalCoGuide) 
	{
		
		this.id = id;
		this.studentId=studentId;
		this.guideId=guideId;
		this.coGuideId=coGuideId;
		this.signedOn=signedOn;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.researchDescription=researchDescription;
		this.disciplineId=disciplineId;
		this.vivaVoceDate=vivaVoceDate;
		this.title=title;
		this.synopsisVivaPanel=synopsisVivaPanel;
		this.internalGuide=internalGuide;
		this.internalCoGuide=internalCoGuide;
		this.conference=conference;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	
	public PhdEmployee getGuideId() {
		return guideId;
	}

	public void setGuideId(PhdEmployee guideId) {
		this.guideId = guideId;
	}

	public PhdEmployee getCoGuideId() {
		return coGuideId;
	}

	public void setCoGuideId(PhdEmployee coGuideId) {
		this.coGuideId = coGuideId;
	}

	public Date getSignedOn() {
		return signedOn;
	}

	public void setSignedOn(Date signedOn) {
		this.signedOn = signedOn;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set<PhdResearchDescription> getResearchDescription() {
		return researchDescription;
	}

	public void setResearchDescription(
			Set<PhdResearchDescription> researchDescription) {
		this.researchDescription = researchDescription;
	}

	public DisciplineBo getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(DisciplineBo disciplineId) {
		this.disciplineId = disciplineId;
	}

	public Date getVivaVoceDate() {
		return vivaVoceDate;
	}

	public void setVivaVoceDate(Date vivaVoceDate) {
		this.vivaVoceDate = vivaVoceDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<PhdStudentPanelMember> getSynopsisVivaPanel() {
		return synopsisVivaPanel;
	}

	public void setSynopsisVivaPanel(Set<PhdStudentPanelMember> synopsisVivaPanel) {
		this.synopsisVivaPanel = synopsisVivaPanel;
	}

	public Employee getInternalGuide() {
		return internalGuide;
	}

	public void setInternalGuide(Employee internalGuide) {
		this.internalGuide = internalGuide;
	}

	public Employee getInternalCoGuide() {
		return internalCoGuide;
	}

	public void setInternalCoGuide(Employee internalCoGuide) {
		this.internalCoGuide = internalCoGuide;
	}

	public Set<PhdConference> getConference() {
		return conference;
	}

	public void setConference(Set<PhdConference> conference) {
		this.conference = conference;
	}

}
