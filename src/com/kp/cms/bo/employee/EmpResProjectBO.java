package com.kp.cms.bo.employee;

	import java.io.Serializable;

	public class EmpResProjectBO implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private EmpResearchPublicMaster empResPubMasterId;
		private int id;
		private String title;
		private String investigators;
		private String sponsors;
		private String abstractObjectives;
		private Boolean isActive;
		private Boolean isResearchProject;
		
		public EmpResProjectBO() {
		}

		public EmpResProjectBO(int id) {
			this.id = id;
		}

			
		

		
		public EmpResProjectBO(EmpResearchPublicMaster empResPubMasterId, String title,
				String investigators, String sponsors,
				String abstractObjectives, Boolean isActive,
				Boolean isResearchProject) {
			super();
			this.empResPubMasterId = empResPubMasterId;
			this.title = title;
			this.investigators = investigators;
			this.sponsors = sponsors;
			this.abstractObjectives = abstractObjectives;
			this.isActive = isActive;
			this.isResearchProject = isResearchProject;
		}
		
		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getInvestigators() {
			return investigators;
		}
		public void setInvestigators(String investigators) {
			this.investigators = investigators;
		}
		public String getSponsors() {
			return sponsors;
		}
		public void setSponsors(String sponsors) {
			this.sponsors = sponsors;
		}
		public String getAbstractObjectives() {
			return abstractObjectives;
		}
		public void setAbstractObjectives(String abstractObjectives) {
			this.abstractObjectives = abstractObjectives;
		}
		
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
		public Boolean getIsResearchProject() {
			return isResearchProject;
		}
		public void setIsResearchProject(Boolean isResearchProject) {
			this.isResearchProject = isResearchProject;
		}

		public EmpResearchPublicMaster getEmpResPubMasterId() {
			return empResPubMasterId;
		}

		public void setEmpResPubMasterId(EmpResearchPublicMaster empResPubMasterId) {
			this.empResPubMasterId = empResPubMasterId;
		}
		

	}



