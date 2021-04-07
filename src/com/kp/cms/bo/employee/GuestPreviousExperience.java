package com.kp.cms.bo.employee;
import java.io.Serializable;
import java.util.Date;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
public class GuestPreviousExperience implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String empOrganization;
		private String empDesignation;
		private int expYears;
		private int expMonths;
		private boolean teachingExperience;
		private boolean industryExperience;
		private boolean active;
		private Date createdDate;
		private String createdBy;
		private Date modifiedDate;
		private String modifiedBy;
		private GuestFaculty guest;
		
		
		public GuestPreviousExperience(){
			
		}
		
		/**
		 * @param id
		 * @param empOrganization
		 * @param empDesignation
		 * @param expYears
		 * @param expMonths
		 * @param teachingExperience
		 * @param industryExperience
		 * @param active
		 * @param createdDate
		 * @param createdBy
		 * @param modifiedDate
		 * @param modifiedBy
		 * @param empOnlineResume
		 */
		public GuestPreviousExperience(int id, String empOrganization,
				String empDesignation, int expYears, int expMonths,
				boolean teachingExperience, boolean industryExperience,
				boolean active, Date createdDate, String createdBy,
				Date modifiedDate, String modifiedBy,
				GuestFaculty guest) {
			super();
			this.id = id;
			this.empOrganization = empOrganization;
			this.empDesignation = empDesignation;
			this.expYears = expYears;
			this.expMonths = expMonths;
			this.teachingExperience = teachingExperience;
			this.industryExperience = industryExperience;
			this.active = active;
			this.createdDate = createdDate;
			this.createdBy = createdBy;
			this.modifiedDate = modifiedDate;
			this.modifiedBy = modifiedBy;
			this.guest = guest;
		}

		public int getId() {
			return id;
		}

		public String getEmpOrganization() {
			return empOrganization;
		}

		public String getEmpDesignation() {
			return empDesignation;
		}

		public int getExpYears() {
			return expYears;
		}

		public int getExpMonths() {
			return expMonths;
		}

		public boolean isTeachingExperience() {
			return teachingExperience;
		}

		public boolean isIndustryExperience() {
			return industryExperience;
		}

		public boolean isActive() {
			return active;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public Date getModifiedDate() {
			return modifiedDate;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		

		public void setId(int id) {
			this.id = id;
		}

		public void setEmpOrganization(String empOrganization) {
			this.empOrganization = empOrganization;
		}

		public void setEmpDesignation(String empDesignation) {
			this.empDesignation = empDesignation;
		}

		public void setExpYears(int expYears) {
			this.expYears = expYears;
		}

		public void setExpMonths(int expMonths) {
			this.expMonths = expMonths;
		}

		public void setTeachingExperience(boolean teachingExperience) {
			this.teachingExperience = teachingExperience;
		}

		public void setIndustryExperience(boolean industryExperience) {
			this.industryExperience = industryExperience;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public GuestFaculty getGuest() {
			return guest;
		}

		public void setGuest(GuestFaculty guest) {
			this.guest = guest;
		}
		

		@Override
		public boolean equals(Object obj) {
			GuestPreviousExperience pExp=(GuestPreviousExperience)obj;
			boolean designation=false;
			boolean org=false;
			if(this.empDesignation==null && pExp.getEmpDesignation()==null)
				designation=true;
			else if(this.empDesignation!=null && pExp.getEmpDesignation()!=null){
				if(this.empDesignation.equalsIgnoreCase(pExp.getEmpDesignation()))
					designation=true;
			}
			if(this.empOrganization==null && pExp.getEmpOrganization()==null)
				org=true;
			else if(this.empOrganization!=null && pExp.getEmpOrganization()!=null){
				if(this.empOrganization.equalsIgnoreCase(pExp.getEmpOrganization()))
					org=true;
			}
			if(designation && org && this.expYears==pExp.getExpYears() && this.expMonths==pExp.getExpMonths() && this.guest.getId()==pExp.getGuest().getId()){
				return true;
			}else
				return false;
		}

		
		

	}



