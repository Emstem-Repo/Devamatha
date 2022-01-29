package com.kp.cms.bo.phd;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class PhdStudentPanelMember implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private PhdDocumentSubmissionBO documetSubmission;
	private PhdEmployee synopsisVivaPanel;
	private Employee synopsisVivaEmployee;
	private Boolean synopsisPanel;
	private Boolean vivaPanel;
	private Boolean selectedPanel;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public PhdStudentPanelMember(){
		
	}
	
	/**
	 * @param id
	 * @param researchPublication
	 * @param documetSubmission
	 * @param description
	 * @param researchDate
	 * @param isActive
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public PhdStudentPanelMember(int id,PhdDocumentSubmissionBO documetSubmission,PhdEmployee synopsisVivaPanel,Boolean synopsisPanel,Boolean vivaPanel,
			Boolean selectedPanel,Boolean isActive,String createdBy,Date createdDate,String modifiedBy,Date modifiedDate,Employee synopsisVivaEmployee) {
		super();
		this.id = id;
		this.documetSubmission = documetSubmission;
		this.synopsisVivaPanel=synopsisVivaPanel;
		this.synopsisPanel = synopsisPanel;
		this.vivaPanel = vivaPanel;
		this.selectedPanel = selectedPanel;
		this.synopsisVivaEmployee=synopsisVivaEmployee;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PhdDocumentSubmissionBO getDocumetSubmission() {
		return documetSubmission;
	}

	public void setDocumetSubmission(PhdDocumentSubmissionBO documetSubmission) {
		this.documetSubmission = documetSubmission;
	}

	public PhdEmployee getSynopsisVivaPanel() {
		return synopsisVivaPanel;
	}

	public void setSynopsisVivaPanel(PhdEmployee synopsisVivaPanel) {
		this.synopsisVivaPanel = synopsisVivaPanel;
	}

	public Boolean getSynopsisPanel() {
		return synopsisPanel;
	}

	public void setSynopsisPanel(Boolean synopsisPanel) {
		this.synopsisPanel = synopsisPanel;
	}

	public Boolean getVivaPanel() {
		return vivaPanel;
	}

	public void setVivaPanel(Boolean vivaPanel) {
		this.vivaPanel = vivaPanel;
	}

	public Boolean getSelectedPanel() {
		return selectedPanel;
	}

	public void setSelectedPanel(Boolean selectedPanel) {
		this.selectedPanel = selectedPanel;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Employee getSynopsisVivaEmployee() {
		return synopsisVivaEmployee;
	}

	public void setSynopsisVivaEmployee(Employee synopsisVivaEmployee) {
		this.synopsisVivaEmployee = synopsisVivaEmployee;
	}
	
}
