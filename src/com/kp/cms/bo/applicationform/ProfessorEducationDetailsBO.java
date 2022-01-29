package com.kp.cms.bo.applicationform;

import java.util.HashSet;
import java.util.Set;

public class ProfessorEducationDetailsBO {
	
	private int id;
	private ProfessorPersonalData professorPersonalDataId;
	
	private Boolean isNet;
	private String netDetails;
	private Boolean isJrf;
	private String jrfDetails;
	private Set<ProfessorBooksPublificationDetailsBO> professerBooksPublificationDetails = new HashSet<ProfessorBooksPublificationDetailsBO>(0);
	private Set<ProfessorPostDoctoralExpDetailsBO> professorPostDoctoralExpDetails = new HashSet<ProfessorPostDoctoralExpDetailsBO>(0);
	private Set<ProfessorTeachingExpDetailsBO> professorTeachingExpDetails = new HashSet<ProfessorTeachingExpDetailsBO>(0);
	private Set<ProfessorEducationQualificationDetailsBO> professorEducationQualificationDetails = new HashSet<ProfessorEducationQualificationDetailsBO>(0);
	
	private String additionalInformation;
	private String ugcApprovedInformation;
	private String rankPosition;
	



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProfessorPersonalData getProfessorPersonalDataId() {
		return professorPersonalDataId;
	}
	public void setProfessorPersonalDataId(
			ProfessorPersonalData professorPersonalDataId) {
		this.professorPersonalDataId = professorPersonalDataId;
	}
	
	public Boolean getIsNet() {
		return isNet;
	}
	public void setIsNet(Boolean isNet) {
		this.isNet = isNet;
	}
	public String getNetDetails() {
		return netDetails;
	}
	public void setNetDetails(String netDetails) {
		this.netDetails = netDetails;
	}
	public Boolean getIsJrf() {
		return isJrf;
	}
	public void setIsJrf(Boolean isJrf) {
		this.isJrf = isJrf;
	}
	public String getJrfDetails() {
		return jrfDetails;
	}
	public void setJrfDetails(String jrfDetails) {
		this.jrfDetails = jrfDetails;
	}
	
	public Set<ProfessorBooksPublificationDetailsBO> getProfesserBooksPublificationDetails() {
		return professerBooksPublificationDetails;
	}
	public void setProfesserBooksPublificationDetails(
			Set<ProfessorBooksPublificationDetailsBO> professerBooksPublificationDetails) {
		this.professerBooksPublificationDetails = professerBooksPublificationDetails;
	}
	public Set<ProfessorPostDoctoralExpDetailsBO> getProfessorPostDoctoralExpDetails() {
		return professorPostDoctoralExpDetails;
	}
	public void setProfessorPostDoctoralExpDetails(
			Set<ProfessorPostDoctoralExpDetailsBO> professorPostDoctoralExpDetails) {
		this.professorPostDoctoralExpDetails = professorPostDoctoralExpDetails;
	}
	public Set<ProfessorTeachingExpDetailsBO> getProfessorTeachingExpDetails() {
		return professorTeachingExpDetails;
	}
	public void setProfessorTeachingExpDetails(
			Set<ProfessorTeachingExpDetailsBO> professorTeachingExpDetails) {
		this.professorTeachingExpDetails = professorTeachingExpDetails;
	}
	public Set<ProfessorEducationQualificationDetailsBO> getProfessorEducationQualificationDetails() {
		return professorEducationQualificationDetails;
	}
	public void setProfessorEducationQualificationDetails(
			Set<ProfessorEducationQualificationDetailsBO> professorEducationQualificationDetails) {
		this.professorEducationQualificationDetails = professorEducationQualificationDetails;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	public String getUgcApprovedInformation() {
		return ugcApprovedInformation;
	}
	public void setUgcApprovedInformation(String ugcApprovedInformation) {
		this.ugcApprovedInformation = ugcApprovedInformation;
	}
	
	public String getRankPosition() {
		return rankPosition;
	}
	public void setRankPosition(String rankPosition) {
		this.rankPosition = rankPosition;
	}
	
	

}
